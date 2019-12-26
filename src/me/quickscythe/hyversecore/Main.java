package me.quickscythe.hyversecore;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.quickscythe.hyversecore.commands.*;
import me.quickscythe.hyversecore.listeners.PlayerListener;
import me.quickscythe.hyversecore.runnables.DateChecker;
import me.quickscythe.hyversecore.utils.CoreUtils;
import me.quickscythe.hyversecore.utils.pets.v1_15_R1.PetManager;

public class Main extends JavaPlugin {
	
	static Main plugin;
	public void onEnable() {
		plugin = this;
		
		CoreUtils.start();
		
		
		new PlayerListener(this);
		
		new SQLCommand("sql", this);
		new SettingsCommand("settings", this);
		new PetCommand(this, "pet");
		new DebugCommand(this, "debug");
		new ItemCommand(this, "item");
		new GRLCommand(this, "grl");
		new RegisterCommand(this, "register");
		
		startDateChecker();
		
		for(Player player : Bukkit.getOnlinePlayers()) {
			player.setPlayerListName(CoreUtils.colorize(CoreUtils.getPlayerPrefix(player) + player.getName()));
			CoreUtils.playerparticles.put(player.getUniqueId(), true);
		}
		
		
		
	}
	
	public void onDisable() {
		PetManager.removeAllPets();
	}
	
	private static void startDateChecker(){
		
		Bukkit.getScheduler().runTaskLater(getPlugin(), new DateChecker(), 1);
	}
	
	
	
	public static Main getPlugin(){
		return plugin;
	}

}
