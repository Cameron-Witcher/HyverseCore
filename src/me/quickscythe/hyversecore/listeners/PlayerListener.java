package me.quickscythe.hyversecore.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.quickscythe.hyversecore.Main;
import me.quickscythe.hyversecore.utils.CoreUtils;
import me.quickscythe.hyversecore.utils.pets.Pet;
import me.quickscythe.hyversecore.utils.pets.PetManager;

public class PlayerListener implements Listener {

	public PlayerListener(Main plugin){
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		if(PetManager.hasPet(e.getPlayer())) {
			PetManager.removePets(e.getPlayer());
		}
	}
	
	
	
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		e.getPlayer().setPlayerListName(CoreUtils.colorize(CoreUtils.getPlayerPrefix(e.getPlayer()) + e.getPlayer().getName()));
		CoreUtils.playerparticles.put(e.getPlayer().getUniqueId(), true);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if(e.getInventory().getHolder() == null) {
			
			if(e.getCurrentItem().getType().equals(Material.DIAMOND)) {
				CoreUtils.toggleParticles((Player)e.getWhoClicked());
			}
			e.setCancelled(true);
		}
		if (e.getWhoClicked().hasMetadata("petinv")) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null)
				return;
			if (PetManager.getPetType(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())) == null)
				return;
			Pet p = PetManager.spawnPet(((Player) e.getWhoClicked()),
					ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()),
					((Player) e.getWhoClicked()).getLocation());
			e.getWhoClicked().closeInventory();
			e.getWhoClicked().removeMetadata("petinv", Main.getPlugin());
			p.setInvisible(true);
		}
	}
	
	

	

}
