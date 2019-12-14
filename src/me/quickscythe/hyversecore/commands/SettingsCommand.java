package me.quickscythe.hyversecore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.quickscythe.hyversecore.Main;
import me.quickscythe.hyversecore.utils.CoreUtils;

public class SettingsCommand implements CommandExecutor {

	public SettingsCommand(String cmd, Main plugin) {
		plugin.getCommand(cmd).setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = ((Player)sender);
			if(args.length == 0) {
				player.openInventory(CoreUtils.getSettingsMenu());
			}
		}
		return true;
	}
}
