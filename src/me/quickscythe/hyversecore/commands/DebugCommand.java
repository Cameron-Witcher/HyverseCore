package me.quickscythe.hyversecore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import me.quickscythe.hyversecore.Main;
import me.quickscythe.hyversecore.utils.CoreUtils;
import me.quickscythe.hyversecore.utils.pets.PetManager;

public class DebugCommand implements CommandExecutor {

	public DebugCommand(Main plugin, String cmd){
		plugin.getCommand(cmd).setExecutor(this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(sender.hasPermission("hyverse.core.cmd.debug")){
			sender.sendMessage(CoreUtils.colorize("&eDebug &7>&f " + CoreUtils.toggleDebug()));
		}
		return false;
	}
}
