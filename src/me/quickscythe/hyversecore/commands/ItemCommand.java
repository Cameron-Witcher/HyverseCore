package me.quickscythe.hyversecore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.quickscythe.hyversecore.Main;
import me.quickscythe.hyversecore.utils.CoreUtils;

public class ItemCommand implements CommandExecutor {

	public ItemCommand(Main plugin, String cmd) {
		plugin.getCommand(cmd).setExecutor(this);
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 1) {
			if(sender instanceof Player && sender.hasPermission("hyverse.item." + args[0].toLowerCase())){
				Player player = (Player) sender;
				player.getInventory().addItem(CoreUtils.getItem(args[0]));
			}
		}
		if(args.length == 2){
			if(sender instanceof Player && sender.hasPermission("hyverse.item." + args[0].toLowerCase())){
				Player player = (Player) sender;
				ItemStack i = CoreUtils.getItem(args[0]);
				i.setAmount(Integer.parseInt(args[1]));
				player.getInventory().addItem(i);
			}
		}
		if(args.length == 3){
			if(sender.hasPermission("hyverse.item." + args[0].toLowerCase()) && sender.hasPermission("hyverse.item.give")){
				if(Bukkit.getPlayer(args[1]) == null){
					if(args[1].equalsIgnoreCase("all") || args[1].equalsIgnoreCase("*")){
						for(Player player : Bukkit.getOnlinePlayers()){
							ItemStack i = CoreUtils.getItem(args[0]);
							i.setAmount(Integer.parseInt(args[1]));
							player.getInventory().addItem(i);
						}
						return false;
					}
					sender.sendMessage(CoreUtils.colorize("&eHyverse &7>&f That player is not online."));
					return false;
				}
				ItemStack i = CoreUtils.getItem(args[0]);
				i.setAmount(Integer.parseInt(args[1]));
				Bukkit.getPlayer(args[1]).getInventory().addItem(i);
			}
		}

		return false;
	}
}
