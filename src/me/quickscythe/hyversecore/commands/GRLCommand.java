package me.quickscythe.hyversecore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.quickscythe.hyversecore.Main;

public class GRLCommand implements CommandExecutor {

	public GRLCommand(Main plugin, String cmd){
		plugin.getCommand(cmd).setExecutor(this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(sender.hasPermission("hyverse.core.cmd.grl")){
			for(Player pl : Bukkit.getOnlinePlayers()) {
				pl.kickPlayer("GRL");
			}
			
			Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new Runnable() {

				@Override
				public void run() {
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "reload");
				}
				
			}, 60);
		}
		return false;
	}
}
