package me.quickscythe.hyversecore.commands;

import java.sql.SQLException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.quickscythe.hyversecore.Main;
import me.quickscythe.hyversecore.utils.CoreUtils;

public class RegisterCommand implements CommandExecutor {

	public RegisterCommand(Main plugin, String cmd){
		plugin.getCommand(cmd).setExecutor(this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(sender instanceof Player){
			if(args.length!=1) {
				sender.sendMessage(CoreUtils.colorize("&e/register <web username>"));
				return true;
			}
			try {
				switch(CoreUtils.registerPlayer(args[0], ((Player)sender))) {
				case 1:
					sender.sendMessage(CoreUtils.colorize("&aFound your web account! Please log in and click the Link with Minecraft link on your profile page to complete this process."));
					break;
				case 0:
					sender.sendMessage(CoreUtils.colorize("&cError 0"));
					break;
				case -1:
					sender.sendMessage(CoreUtils.colorize("&cError -1"));
					break;
				case -100:
					sender.sendMessage(CoreUtils.colorize("&cYou have already registered!"));
					break;
				default:
					break;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} else {
			sender.sendMessage(CoreUtils.colorize("&c/register <web username>"));
		}
		return true;
	}
}
