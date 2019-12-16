package me.quickscythe.hyversecore.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server.Spigot;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import me.quickscythe.hyversecore.Main;
import me.quickscythe.hyversecore.utils.pets.PetManager;
import me.quickscythe.hyversecore.utils.reflection.PackageType;
import me.quickscythe.hyversecore.utils.reflection.ReflectionUtils;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class CoreUtils {

	// TODO Make it read this info from a file for christ sakes!!
	private static IDatabase db = new IDatabase("localhost", "Minecraft", 3306, "mysql", "v4pob8LW");
	private static boolean connected = false;
	private static Holiday holiday = Holiday.NONE;
	private static Date date = new Date();
	private static Inventory settingsMenu = null;
	public static Map<UUID, Boolean> playerparticles = new HashMap<>();
	
	private static boolean debug = false;
	
	private static File itemfile = new File(Main.getPlugin().getDataFolder() + "/Items");
	private static List<FileConfiguration> itemFiles = new ArrayList<>();
	private static Map<String, ItemStack> items = new HashMap<>();
	private static Map<String, FoodInfo> food = new HashMap<>();

	public static void start() {
		if (!connected) {
			if (testSQLConnection()) {
				connected = true;
				Bukkit.getConsoleSender().sendMessage(colorize("&e[SQL] &7>&f Successfully connected to SQL."));
			} else {
				connected = false;
				Bukkit.getConsoleSender().sendMessage(colorize("&e[SQL] &7>&f Error connecting to SQL."));
			}
		}
		if (!itemfile.exists()) {
			itemfile.mkdirs();
		}
		try {

			for (File file : itemfile.listFiles()) {
				if (file.getName().startsWith("item") && file.getName().endsWith(".yml")) {
					itemFiles.add(YamlConfiguration.loadConfiguration(file));
				}
			}
			itemFiles.size();

		} catch (NullPointerException ex) {

			File file = new File(itemfile.getPath() + "/itemDefault.yml");
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			FileConfiguration fc = YamlConfiguration.loadConfiguration(file);

			fc.set("SuperSword.Id", "DIAMOND_SWORD");
			fc.set("SuperSword.Data", "570");
			fc.set("SuperSword.Display", "&6Super Sword");

			try {
				fc.save(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			itemFiles.add(fc);

		}

		if (itemFiles.size() == 0) {

			File file = new File(itemfile.getPath() + "/itemDefault.yml");
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			FileConfiguration fc = YamlConfiguration.loadConfiguration(file);

			fc.set("SuperSword.Id", "DIAMOND_SWORD");
			fc.set("SuperSword.Data", "570");
			fc.set("SuperSword.Options.Display", "&6Super Sword");

			try {
				fc.save(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			itemFiles.add(fc);

		}
		PetManager.registerPets();
	}
	


	public static void sendPluginMessage(Player player, String channel, String... arguments) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		for (String s : arguments) {
			out.writeUTF(s);
		}
		player.sendPluginMessage(Main.getPlugin(), channel, out.toByteArray());
	}

	private static boolean testSQLConnection() {
		return (db.init());

	}

	public static String colorize(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	@SuppressWarnings("deprecation")
	public static String getTime() {
		
		String min = date.getMinutes() + "";
		String hr = date.getHours() + "";
		if (min.length() == 1)
			min = "0" + min;
		if (Integer.parseInt(hr) > 12) {
			return (Integer.parseInt(hr) - 12) + ":" + min + " PM";
		} else {
			return hr + ":" + min + " AM";
		}
	}

	public static ResultSet sendQuery(String query) {
		if (connected) {
			return db.query(query);
		} else {
			if (testSQLConnection()) {
				return db.query(query);
			}
			return null;
		}
	}

	public static Integer sendUpdate(String query) {
		if (connected) {
			return db.update(query);
		} else {
			if (testSQLConnection()) {
				return db.update(query);
			}
			return null;
		}
	}

	public static boolean sendInsert(String query) {
		if (connected) {
			return db.input(query);
		} else {
			if (testSQLConnection()) {
				return db.input(query);
			}
			return false;
		}
	}

	public static String toString(String[] args) {
		String s = "";
		for (String a : args) {
			s = s + " " + a;
		}
		return s;
	}

	@SuppressWarnings("deprecation")
	public static int getMonth() {
		return date.getMonth();
	}

	@SuppressWarnings("deprecation")
	public static int getDay() {
		return date.getDay();
	}

	public static void setHoliday(Holiday hol) {
		holiday = hol;
	}

	public static Holiday getHoliday() {
		return holiday;
	}

	public static List<String> colorizeStringList(List<String> stringList) {
		List<String> ret = new ArrayList<>();
		for (String s : stringList) {
			ret.add(colorize(s));
		}
		return ret;
	}

	public static List<String> colorizeStringList(String[] stringList) {
		List<String> ret = new ArrayList<>();
		for (String s : stringList) {
			ret.add(colorize(s));
		}
		return ret;
	}

	public static Random getRandom() {
		return new Random();
	}

	public static Inventory createSettingsMenu() {
		InventoryCreator inv = new InventoryCreator("&6Settings", null, 9);
		inv.addItem(new ItemStack(Material.DIAMOND), "&eParticles", 'A', new String[] {});
		inv.setConfiguration(new char[] { 'A', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' });
		settingsMenu = inv.getInventory();
		return settingsMenu;
	}

	public static Inventory getSettingsMenu() {
		if (settingsMenu == null) {
			createSettingsMenu();

		}
		return settingsMenu;
	}

	public static void toggleParticles(Player player) {
		if (playerparticles.get(player.getUniqueId())) {
			player.sendMessage(colorize("&e[Settings] &f> &7Particles turned off"));
			playerparticles.put(player.getUniqueId(), false);
		} else {
			player.sendMessage(colorize("&e[Settings] &f> &7Particles turned on"));
			playerparticles.put(player.getUniqueId(), true);
		}
	}

	@SuppressWarnings("deprecation")
	public static String getPlayerPrefix(Player player) {
		if (PermissionsEx.getUser(player).getGroups().length > 0) {

			String prefix = "";
			for (PermissionGroup group : PermissionsEx.getUser(player).getGroups()) {

				prefix = prefix + group.getPrefix();
			}
			return colorize(prefix);
		}
		return "";
	}
	@SuppressWarnings("deprecation")
	public static String getPlayerSuffix(Player player) {
		if (PermissionsEx.getUser(player).getGroups().length > 0) {

			String suffix = "";
			for (PermissionGroup group : PermissionsEx.getUser(player).getGroups()) {

				suffix = suffix + group.getSuffix();
			}
			return colorize(suffix);
		}
		return "";
	}
	
	public static boolean debugOn() {
		return debug;
	}
	
	public static boolean toggleDebug() {
		debug = !debug;
		return debug;
	}
	
	public static void setDebug(boolean status) {
		debug = status;
	}
	
	public static void debug(String message) {
		if(debug) Bukkit.broadcastMessage(colorize("&eDebug &7> &f" + message));
	}
	
	public static String encryptItemStack(ItemStack i) {
		try {
			return i.getType() + ":" + i.getAmount() + ":" + i.getDurability();
		} catch (NullPointerException ex) {
			return "AIR:1:0";
		}

	}

	@Deprecated
	public static ItemStack decryptItemStack(String s) {
		if (s.contains(":")) {
			String[] d = s.split(":");
			if (d.length == 2) {
				return new ItemStack(Material.valueOf(d[0]), 1, Short.parseShort(d[1]));
			}
			if (d.length == 3) {
				return new ItemStack(Material.valueOf(d[0]), Integer.parseInt(d[1]), Short.parseShort(d[2]));
			}
			return new ItemStack(Material.valueOf(d[0]));

		} else {
			return new ItemStack(Material.valueOf(s));
		}

	}
	
	public static ItemStack getItem(String name) {
		debug("Loading Item " + name);
		

		
		ItemStack i = new ItemStack(Material.STONE);
		int amount = 1;
		if (name.contains("-")) {
			amount = Integer.parseInt(name.split("-")[1]);
			name = name.split("-")[0];
		}
		if (items.containsKey(name)) {

			i = items.get(name).clone();
			i.setAmount(amount);
			return i;
		}
		debug("Couldn't find item " + name + ". Searching in Items folder...");
		boolean food = false;
		FoodInfo info = new FoodInfo(name);
		ItemMeta a = i.getItemMeta();
		for (FileConfiguration item : itemFiles) {
			if (item.isSet(name + ".Id")) {

				i.setType(Material.valueOf(item.getString(name + ".Id")));
				debug(item.getString(name + ".Id") + "");

			}

			if (item.isSet(name + ".Data"))
				i.setDurability(Short.parseShort(item.getString(name + ".Data")));
			if (item.isSet(name + ".Options.Display"))
				a.setDisplayName(colorize(item.getString(name + ".Options.Display")));
			if (item.isSet(name + ".Options.Hide")) {
				for (String s : item.getStringList(name + ".Options.Hide")) {
					a.addItemFlags(ItemFlag.valueOf("HIDE_" + s.toUpperCase()));
				}
			}

			if (item.isSet(name + ".Options.Unbreakable")) {
				a.setUnbreakable(Boolean.parseBoolean(item.getString(name + ".Options.Unbreakable")));
			}
			List<String> lore = new ArrayList<>();
			if (item.isSet(name + ".Lore")) {
				for (String l : item.getStringList(name + ".Lore")) {
					lore.add(colorize(l));
				}
				a.setLore(lore);
			}

			if (item.isSet(name + ".Food.Hunger")) {
				food = true;
				info.setHungerLevel(item.getInt(name + ".Food.Hunger"));
			}
			if (item.isSet(name + ".Food.Potion")) {
				food = true;
				for (String s : item.getStringList(name + ".Food.Potion")) {
					String[] effect = s.split("-");

					info.addPotionEffect(new PotionEffect(PotionEffectType.getByName(effect[0].toUpperCase()),
							Integer.parseInt(effect[1]), Integer.parseInt(effect[2])));
				}
			}
			if (item.isSet(name + ".Food.Health")) {
				food = true;
				info.setHealingFactor(item.getInt(name + ".Food.Health"));
			}
			if (food) {
				lore.add(ChatColor.DARK_GRAY + "Food:" + name);
				a.setLore(lore);
			}
			i.setItemMeta(a);
			i.setAmount(amount);

			if (item.isSet(name + ".Attributes.MainHand.Damage")) {
				try {
					Object stack = ReflectionUtils.instantiateObject("ItemStack", PackageType.MINECRAFT_SERVER,
							ReflectionUtils.instantiateObject("Item", PackageType.MINECRAFT_SERVER));
					Method convert1 = ReflectionUtils.getMethod("CraftItemStack", PackageType.CRAFTBUKKIT_INVENTORY,
							"asNMSCopy", i.getClass());
					convert1.setAccessible(true);
					stack = convert1.invoke(null, i);
					Object compound = (boolean) (ReflectionUtils.invokeMethod(stack, "hasTag"))
							? ReflectionUtils.invokeMethod(stack, "getTag")
							: ReflectionUtils.instantiateObject("NBTTagCompound", PackageType.MINECRAFT_SERVER);
					Object modifiers = ReflectionUtils.instantiateObject("NBTTagList", PackageType.MINECRAFT_SERVER);
					Object damage = ReflectionUtils.instantiateObject("NBTTagCompound", PackageType.MINECRAFT_SERVER);
					ReflectionUtils.invokeMethod(damage, "set", "AttributeName", ReflectionUtils
							.instantiateObject("NBTTagString", PackageType.MINECRAFT_SERVER, "generic.attackDamage"));
					ReflectionUtils.invokeMethod(damage, "set", "Name", ReflectionUtils
							.instantiateObject("NBTTagString", PackageType.MINECRAFT_SERVER, "generic.attackDamage"));
					ReflectionUtils.invokeMethod(damage, "set", "Amount", ReflectionUtils.instantiateObject("NBTTagInt",
							PackageType.MINECRAFT_SERVER, item.getInt(name + ".Attributes.MainHand.Damage")));
					ReflectionUtils.invokeMethod(damage, "set", "Operation",
							ReflectionUtils.instantiateObject("NBTTagInt", PackageType.MINECRAFT_SERVER, 0));
					ReflectionUtils.invokeMethod(damage, "set", "UUIDLeast",
							ReflectionUtils.instantiateObject("NBTTagInt", PackageType.MINECRAFT_SERVER, 894654));
					ReflectionUtils.invokeMethod(damage, "set", "UUIDMost",
							ReflectionUtils.instantiateObject("NBTTagInt", PackageType.MINECRAFT_SERVER, 2872));
					ReflectionUtils.invokeMethod(damage, "set", "Slot", ReflectionUtils
							.instantiateObject("NBTTagString", PackageType.MINECRAFT_SERVER, "mainhand"));
					ReflectionUtils.invokeMethod(modifiers, "add", damage);
					ReflectionUtils.invokeMethod(compound, "set", "AttributeModifiers", modifiers);
					ReflectionUtils.invokeMethod(stack, "setTag", compound);

					Method convert2 = ReflectionUtils.getMethod("CraftItemStack", PackageType.CRAFTBUKKIT_INVENTORY,
							"asBukkitCopy", stack.getClass());
					convert2.setAccessible(true);
					i = (ItemStack) convert2.invoke(null, stack);

				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
					e.printStackTrace();
				}

			}

			if (item.isSet(name + ".Attributes.MainHand.AttackSpeed")) {
				try {
					Object stack = ReflectionUtils.instantiateObject("ItemStack", PackageType.MINECRAFT_SERVER,
							ReflectionUtils.instantiateObject("Item", PackageType.MINECRAFT_SERVER));
					Method convert1 = ReflectionUtils.getMethod("CraftItemStack", PackageType.CRAFTBUKKIT_INVENTORY,
							"asNMSCopy", i.getClass());
					convert1.setAccessible(true);
					stack = convert1.invoke(null, i);
					Object compound = (boolean) (ReflectionUtils.invokeMethod(stack, "hasTag"))
							? ReflectionUtils.invokeMethod(stack, "getTag")
							: ReflectionUtils.instantiateObject("NBTTagCompound", PackageType.MINECRAFT_SERVER);
					Object modifiers = ReflectionUtils.instantiateObject("NBTTagList", PackageType.MINECRAFT_SERVER);
					Object damage = ReflectionUtils.instantiateObject("NBTTagCompound", PackageType.MINECRAFT_SERVER);
					ReflectionUtils.invokeMethod(damage, "set", "AttributeName", ReflectionUtils
							.instantiateObject("NBTTagString", PackageType.MINECRAFT_SERVER, "generic.attackSpeed"));
					ReflectionUtils.invokeMethod(damage, "set", "Name", ReflectionUtils
							.instantiateObject("NBTTagString", PackageType.MINECRAFT_SERVER, "generic.attackSpeed"));
					ReflectionUtils.invokeMethod(damage, "set", "Amount", ReflectionUtils.instantiateObject("NBTTagInt",
							PackageType.MINECRAFT_SERVER, item.getInt(name + ".Attributes.MainHand.AttackSpeed")));
					ReflectionUtils.invokeMethod(damage, "set", "Operation",
							ReflectionUtils.instantiateObject("NBTTagInt", PackageType.MINECRAFT_SERVER, 0));
					ReflectionUtils.invokeMethod(damage, "set", "UUIDLeast",
							ReflectionUtils.instantiateObject("NBTTagInt", PackageType.MINECRAFT_SERVER, 894654));
					ReflectionUtils.invokeMethod(damage, "set", "UUIDMost",
							ReflectionUtils.instantiateObject("NBTTagInt", PackageType.MINECRAFT_SERVER, 2872));
					ReflectionUtils.invokeMethod(damage, "set", "Slot", ReflectionUtils
							.instantiateObject("NBTTagString", PackageType.MINECRAFT_SERVER, "mainhand"));
					ReflectionUtils.invokeMethod(modifiers, "add", damage);
					ReflectionUtils.invokeMethod(compound, "set", "AttributeModifiers", modifiers);
					ReflectionUtils.invokeMethod(stack, "setTag", compound);

					Method convert2 = ReflectionUtils.getMethod("CraftItemStack", PackageType.CRAFTBUKKIT_INVENTORY,
							"asBukkitCopy", stack.getClass());
					convert2.setAccessible(true);
					i = (ItemStack) convert2.invoke(null, stack);

				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
					e.printStackTrace();
				}

			}
			// Helmet | head, Chestplate | chest, Legs | legs, Boots | feet
			if (item.isSet(name + ".Attributes.Protection.Helmet")) {
				try {
					Object stack = ReflectionUtils.instantiateObject("ItemStack", PackageType.MINECRAFT_SERVER,
							ReflectionUtils.instantiateObject("Item", PackageType.MINECRAFT_SERVER));
					Method convert1 = ReflectionUtils.getMethod("CraftItemStack", PackageType.CRAFTBUKKIT_INVENTORY,
							"asNMSCopy", i.getClass());
					convert1.setAccessible(true);
					stack = convert1.invoke(null, i);
					Object compound = (boolean) (ReflectionUtils.invokeMethod(stack, "hasTag"))
							? ReflectionUtils.invokeMethod(stack, "getTag")
							: ReflectionUtils.instantiateObject("NBTTagCompound", PackageType.MINECRAFT_SERVER);
					Object modifiers = ReflectionUtils.instantiateObject("NBTTagList", PackageType.MINECRAFT_SERVER);
					Object damage = ReflectionUtils.instantiateObject("NBTTagCompound", PackageType.MINECRAFT_SERVER);
					ReflectionUtils.invokeMethod(damage, "set", "AttributeName", ReflectionUtils
							.instantiateObject("NBTTagString", PackageType.MINECRAFT_SERVER, "generic.armor"));
					ReflectionUtils.invokeMethod(damage, "set", "Name", ReflectionUtils
							.instantiateObject("NBTTagString", PackageType.MINECRAFT_SERVER, "generic.armor"));
					ReflectionUtils.invokeMethod(damage, "set", "Amount", ReflectionUtils.instantiateObject("NBTTagInt",
							PackageType.MINECRAFT_SERVER, item.getInt(name + ".Attributes.Protection.Helmet")));
					ReflectionUtils.invokeMethod(damage, "set", "Operation",
							ReflectionUtils.instantiateObject("NBTTagInt", PackageType.MINECRAFT_SERVER, 0));
					ReflectionUtils.invokeMethod(damage, "set", "UUIDLeast",
							ReflectionUtils.instantiateObject("NBTTagInt", PackageType.MINECRAFT_SERVER, 894654));
					ReflectionUtils.invokeMethod(damage, "set", "UUIDMost",
							ReflectionUtils.instantiateObject("NBTTagInt", PackageType.MINECRAFT_SERVER, 2872));
					ReflectionUtils.invokeMethod(damage, "set", "Slot",
							ReflectionUtils.instantiateObject("NBTTagString", PackageType.MINECRAFT_SERVER, "head"));
					ReflectionUtils.invokeMethod(modifiers, "add", damage);
					ReflectionUtils.invokeMethod(compound, "set", "AttributeModifiers", modifiers);
					ReflectionUtils.invokeMethod(stack, "setTag", compound);

					Method convert2 = ReflectionUtils.getMethod("CraftItemStack", PackageType.CRAFTBUKKIT_INVENTORY,
							"asBukkitCopy", stack.getClass());
					convert2.setAccessible(true);
					i = (ItemStack) convert2.invoke(null, stack);

				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
					e.printStackTrace();
				}

			}

			if (item.isSet(name + ".Attributes.Protection.Chestplate")) {
				try {
					Object stack = ReflectionUtils.instantiateObject("ItemStack", PackageType.MINECRAFT_SERVER,
							ReflectionUtils.instantiateObject("Item", PackageType.MINECRAFT_SERVER));
					Method convert1 = ReflectionUtils.getMethod("CraftItemStack", PackageType.CRAFTBUKKIT_INVENTORY,
							"asNMSCopy", i.getClass());
					convert1.setAccessible(true);
					stack = convert1.invoke(null, i);
					Object compound = (boolean) (ReflectionUtils.invokeMethod(stack, "hasTag"))
							? ReflectionUtils.invokeMethod(stack, "getTag")
							: ReflectionUtils.instantiateObject("NBTTagCompound", PackageType.MINECRAFT_SERVER);
					Object modifiers = ReflectionUtils.instantiateObject("NBTTagList", PackageType.MINECRAFT_SERVER);
					Object damage = ReflectionUtils.instantiateObject("NBTTagCompound", PackageType.MINECRAFT_SERVER);
					ReflectionUtils.invokeMethod(damage, "set", "AttributeName", ReflectionUtils
							.instantiateObject("NBTTagString", PackageType.MINECRAFT_SERVER, "generic.armor"));
					ReflectionUtils.invokeMethod(damage, "set", "Name", ReflectionUtils
							.instantiateObject("NBTTagString", PackageType.MINECRAFT_SERVER, "generic.armor"));
					ReflectionUtils.invokeMethod(damage, "set", "Amount", ReflectionUtils.instantiateObject("NBTTagInt",
							PackageType.MINECRAFT_SERVER, item.getInt(name + ".Attributes.Protection.Chestplate")));
					ReflectionUtils.invokeMethod(damage, "set", "Operation",
							ReflectionUtils.instantiateObject("NBTTagInt", PackageType.MINECRAFT_SERVER, 0));
					ReflectionUtils.invokeMethod(damage, "set", "UUIDLeast",
							ReflectionUtils.instantiateObject("NBTTagInt", PackageType.MINECRAFT_SERVER, 894654));
					ReflectionUtils.invokeMethod(damage, "set", "UUIDMost",
							ReflectionUtils.instantiateObject("NBTTagInt", PackageType.MINECRAFT_SERVER, 2872));
					ReflectionUtils.invokeMethod(damage, "set", "Slot",
							ReflectionUtils.instantiateObject("NBTTagString", PackageType.MINECRAFT_SERVER, "chest"));
					ReflectionUtils.invokeMethod(modifiers, "add", damage);
					ReflectionUtils.invokeMethod(compound, "set", "AttributeModifiers", modifiers);
					ReflectionUtils.invokeMethod(stack, "setTag", compound);

					Method convert2 = ReflectionUtils.getMethod("CraftItemStack", PackageType.CRAFTBUKKIT_INVENTORY,
							"asBukkitCopy", stack.getClass());
					convert2.setAccessible(true);
					i = (ItemStack) convert2.invoke(null, stack);

				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
					e.printStackTrace();
				}

			}

			if (item.isSet(name + ".Attributes.Protection.Legs")) {
				try {
					Object stack = ReflectionUtils.instantiateObject("ItemStack", PackageType.MINECRAFT_SERVER,
							ReflectionUtils.instantiateObject("Item", PackageType.MINECRAFT_SERVER));
					Method convert1 = ReflectionUtils.getMethod("CraftItemStack", PackageType.CRAFTBUKKIT_INVENTORY,
							"asNMSCopy", i.getClass());
					convert1.setAccessible(true);
					stack = convert1.invoke(null, i);
					Object compound = (boolean) (ReflectionUtils.invokeMethod(stack, "hasTag"))
							? ReflectionUtils.invokeMethod(stack, "getTag")
							: ReflectionUtils.instantiateObject("NBTTagCompound", PackageType.MINECRAFT_SERVER);
					Object modifiers = ReflectionUtils.instantiateObject("NBTTagList", PackageType.MINECRAFT_SERVER);
					Object damage = ReflectionUtils.instantiateObject("NBTTagCompound", PackageType.MINECRAFT_SERVER);
					ReflectionUtils.invokeMethod(damage, "set", "AttributeName", ReflectionUtils
							.instantiateObject("NBTTagString", PackageType.MINECRAFT_SERVER, "generic.armor"));
					ReflectionUtils.invokeMethod(damage, "set", "Name", ReflectionUtils
							.instantiateObject("NBTTagString", PackageType.MINECRAFT_SERVER, "generic.armor"));
					ReflectionUtils.invokeMethod(damage, "set", "Amount", ReflectionUtils.instantiateObject("NBTTagInt",
							PackageType.MINECRAFT_SERVER, item.getInt(name + ".Attributes.Protection.Legs")));
					ReflectionUtils.invokeMethod(damage, "set", "Operation",
							ReflectionUtils.instantiateObject("NBTTagInt", PackageType.MINECRAFT_SERVER, 0));
					ReflectionUtils.invokeMethod(damage, "set", "UUIDLeast",
							ReflectionUtils.instantiateObject("NBTTagInt", PackageType.MINECRAFT_SERVER, 894654));
					ReflectionUtils.invokeMethod(damage, "set", "UUIDMost",
							ReflectionUtils.instantiateObject("NBTTagInt", PackageType.MINECRAFT_SERVER, 2872));
					ReflectionUtils.invokeMethod(damage, "set", "Slot",
							ReflectionUtils.instantiateObject("NBTTagString", PackageType.MINECRAFT_SERVER, "legs"));
					ReflectionUtils.invokeMethod(modifiers, "add", damage);
					ReflectionUtils.invokeMethod(compound, "set", "AttributeModifiers", modifiers);
					ReflectionUtils.invokeMethod(stack, "setTag", compound);

					Method convert2 = ReflectionUtils.getMethod("CraftItemStack", PackageType.CRAFTBUKKIT_INVENTORY,
							"asBukkitCopy", stack.getClass());
					convert2.setAccessible(true);
					i = (ItemStack) convert2.invoke(null, stack);

				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
					e.printStackTrace();
				}

			}

			if (item.isSet(name + ".Attributes.Protection.Boots")) {
				try {
					Object stack = ReflectionUtils.instantiateObject("ItemStack", PackageType.MINECRAFT_SERVER,
							ReflectionUtils.instantiateObject("Item", PackageType.MINECRAFT_SERVER));
					Method convert1 = ReflectionUtils.getMethod("CraftItemStack", PackageType.CRAFTBUKKIT_INVENTORY,
							"asNMSCopy", i.getClass());
					convert1.setAccessible(true);
					stack = convert1.invoke(null, i);
					Object compound = (boolean) (ReflectionUtils.invokeMethod(stack, "hasTag"))
							? ReflectionUtils.invokeMethod(stack, "getTag")
							: ReflectionUtils.instantiateObject("NBTTagCompound", PackageType.MINECRAFT_SERVER);
					Object modifiers = ReflectionUtils.instantiateObject("NBTTagList", PackageType.MINECRAFT_SERVER);
					Object damage = ReflectionUtils.instantiateObject("NBTTagCompound", PackageType.MINECRAFT_SERVER);
					ReflectionUtils.invokeMethod(damage, "set", "AttributeName", ReflectionUtils
							.instantiateObject("NBTTagString", PackageType.MINECRAFT_SERVER, "generic.armor"));
					ReflectionUtils.invokeMethod(damage, "set", "Name", ReflectionUtils
							.instantiateObject("NBTTagString", PackageType.MINECRAFT_SERVER, "generic.armor"));
					ReflectionUtils.invokeMethod(damage, "set", "Amount", ReflectionUtils.instantiateObject("NBTTagInt",
							PackageType.MINECRAFT_SERVER, item.getInt(name + ".Attributes.Protection.Boots")));
					ReflectionUtils.invokeMethod(damage, "set", "Operation",
							ReflectionUtils.instantiateObject("NBTTagInt", PackageType.MINECRAFT_SERVER, 0));
					ReflectionUtils.invokeMethod(damage, "set", "UUIDLeast",
							ReflectionUtils.instantiateObject("NBTTagInt", PackageType.MINECRAFT_SERVER, 894654));
					ReflectionUtils.invokeMethod(damage, "set", "UUIDMost",
							ReflectionUtils.instantiateObject("NBTTagInt", PackageType.MINECRAFT_SERVER, 2872));
					ReflectionUtils.invokeMethod(damage, "set", "Slot",
							ReflectionUtils.instantiateObject("NBTTagString", PackageType.MINECRAFT_SERVER, "feet"));
					ReflectionUtils.invokeMethod(modifiers, "add", damage);
					ReflectionUtils.invokeMethod(compound, "set", "AttributeModifiers", modifiers);
					ReflectionUtils.invokeMethod(stack, "setTag", compound);

					Method convert2 = ReflectionUtils.getMethod("CraftItemStack", PackageType.CRAFTBUKKIT_INVENTORY,
							"asBukkitCopy", stack.getClass());
					convert2.setAccessible(true);
					i = (ItemStack) convert2.invoke(null, stack);

				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
					e.printStackTrace();
				}

			}
		}
		debug("Item loaded from config, and saved to cache.");
		if (food)
			debug("Item " + name + " was food.");
		items.put(name, i.clone());
		if (food)
			CoreUtils.food.put(name, info);
		return i.clone();
	}
	
	public static net.minecraft.server.v1_15_R1.Entity spawnEntity(net.minecraft.server.v1_15_R1.Entity entity,
			Location loc) {

		try {
			// Object craftEntity =
			// PackageType.MINECRAFT_SERVER.getClass("Entity").cast(entity);
			// Object mEntity = ReflectionUtils.invokeMethod(craftEntity,
			// "getHandle");

			ReflectionUtils.invokeMethod(entity, "setLocation", loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(),
					loc.getPitch());

			ReflectionUtils
					.invokeMethod(
							ReflectionUtils.invokeMethod(
									PackageType.CRAFTBUKKIT.getClass("CraftWorld").cast(loc.getWorld()), "getHandle"),
							"addEntity", entity, SpawnReason.CUSTOM);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return entity;
	}

}
