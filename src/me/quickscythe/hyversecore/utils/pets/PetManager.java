package me.quickscythe.hyversecore.utils.pets;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import me.quickscythe.hyversecore.Main;
import me.quickscythe.hyversecore.utils.CoreUtils;
import me.quickscythe.hyversecore.utils.InventoryCreator;

public class PetManager {

	static List<PetType> types = new ArrayList<>();

	static List<Pet> pets = new ArrayList<>();
	static List<Pet> rpets = new ArrayList<>();

	public static void registerPets() {
		try {

			for (File file : getAllFiles()) {
				FileConfiguration fc = YamlConfiguration.loadConfiguration(file);
				PetType t = new PetType(file.getName().replace(".yml", ""));
				if (fc.isSet("IdleItem")) {
					ItemStack i = new ItemStack(Material.PUMPKIN);
					if (fc.getString("IdleItem").contains("CustomItem")) {
						i = CoreUtils.getItem(fc.getString("IdleItem").split(":")[1]);
					} else {
						i = CoreUtils.decryptItemStack(fc.getString("IdleItem"));
					}
					t.setIdleItem(i);
				} else {
					t.setIdleItem(new ItemStack(Material.PUMPKIN));
				}
				if (fc.isSet("MovingItem")) {
					ItemStack i = new ItemStack(Material.STONE);
					if (fc.getString("MovingItem").contains("CustomItem")) {
						i = CoreUtils.getItem(fc.getString("MovingItem").split(":")[1]);
					} else {
						i = CoreUtils.decryptItemStack(fc.getString("MovingItem"));
					}
					t.setMovingItem(i);
				} else {
					t.setIdleItem(new ItemStack(Material.JACK_O_LANTERN));
				}
				if (fc.isSet("CanFly")) {
					t.setCanFly(fc.getBoolean("CanFly"));
				} else {
					t.setCanFly(false);
				}

				types.add(t);

				Bukkit.getConsoleSender()
						.sendMessage(CoreUtils.colorize("&e&lPets &f>&7 Registered pet &f" + t.getName() + "&7."));

			}
		} catch (NullPointerException e) {
			createDemoFile();
		}

	}

	private static File[] getAllFiles() {
		return new File(Main.getPlugin().getDataFolder() + "/pets").listFiles();
	}

	public static void createFiles() {
		new File(Main.getPlugin().getDataFolder() + "/pets").mkdir();
	}

	public static void createDemoFile() {
		createFiles();
		File demo = new File(Main.getPlugin().getDataFolder() + "/pets/demo.yml");

		try {
			demo.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileConfiguration fc = YamlConfiguration.loadConfiguration(demo);
		fc.set("IdleItem", "PUMPKIN:1:0");
		fc.set("MovingItem", "CustomItem:Shovel");
//		fc.set("CanFly", false);

		try {
			fc.save(demo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Bukkit.getConsoleSender().sendMessage(CoreUtils.colorize("&e&lPets &f>&7 Created Demo Pet."));

		registerPets();

	}

	public static void removeAllPets() {
		for (Pet pet : pets) {
			rpets.add(pet);
		}
		removePets();
	}

	public static void removePets() {
		for (Pet rpet : rpets) {
			pets.remove(rpet);
			((ArmorStand)rpet).remove();
		}
		rpets.clear();

	}

	public static void removePets(Player owner) {
		for (Pet pet : pets) {
			if (pet.getOwner() == owner.getUniqueId()) {
				rpets.add(pet);
			}
		}
		removePets();
	}
	
	public static boolean hasPet(Player player) {
		for(Pet pet : pets) {
			if(pet.getOwner() == player.getUniqueId()) {
				return true;
			}
		}
		return false;
	}

	public static Pet spawnPet(Player owner, String type, Location loc) {

		if (hasPet(owner)) {
			if (owner.hasPermission("hyverse.pets.multipets")) {
				Pet p = (Pet) CoreUtils.spawnEntity(new Pet(((CraftWorld) loc.getWorld()).getHandle()), loc);
				p.setOwner(owner.getUniqueId());
				p.setType(getPetType(type));
				CoreUtils.debug(getPetType(type) + " - ");
				ArmorStand pb = (ArmorStand) p.getBukkitEntity();
				pb.setBasePlate(false);
				pb.setMetadata("pet", new FixedMetadataValue(Main.getPlugin(), "true"));

				pb.setSmall(true);
				pb.setInvulnerable(true);
				pets.add(p);
				return p;
			} else {
				owner.sendMessage(CoreUtils.colorize("&ePets &7>&f You can only have 1 pet at a time sorry."));
			}
		} else {

			Pet p = (Pet) CoreUtils.spawnEntity(new Pet(((CraftWorld) loc.getWorld()).getHandle()), loc);
			p.setOwner(owner.getUniqueId());
			p.setType(getPetType(type));
			CoreUtils.debug(getPetType(type) + " - ");
			ArmorStand pb = (ArmorStand) p.getBukkitEntity();
			pb.setBasePlate(false);
			pb.setMetadata("pet", new FixedMetadataValue(Main.getPlugin(), "true"));

			pb.setSmall(true);
			pb.setInvulnerable(true);
			pets.add(p);
			return p;
		}
		return null;
	}

	public static PetType getPetType(String type) {
		for (PetType t : types) {
			if (t.getName().equals(type))
				return t;
		}
		return null;
	}

	public static Inventory getGUI(Player player) {

		InventoryCreator inv = new InventoryCreator("Pets", (player), ((types.size() / 9) + 1) * 9);
		inv.addItem(new ItemStack(Material.GRAY_STAINED_GLASS_PANE), "&eComing Soon", 'X', (String[]) null);
		ArrayList<Character> c = new ArrayList<Character>();
		for (int i = 0; i != (((int) (types.size() / 9)) + 1) * 9; i++) {
			if (i < types.size()) {
				if (player.hasPermission("stratos.pet." + types.get(i).getName())) {
					inv.addItem(new ItemStack(types.get(i).getIdleItem()), types.get(i).getName(), (char) i,
							(String[]) null, types.get(i).getIdleItem().getDurability(), false);
				} else {
					inv.addItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE), types.get(i).getName(), (char) i,
							new String[] { "&cLocked..." }, (short) 15, false);
				}
				c.add((char) i);
			} else {
				c.add('X');
			}

		}
		inv.setConfiguration(c);
		c.clear();
		c = null;
		return inv.getInventory();

	}

}
