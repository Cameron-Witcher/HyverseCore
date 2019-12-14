package me.quickscythe.hyversecore.commands;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.quickscythe.hyversecore.Main;
import me.quickscythe.hyversecore.utils.CoreUtils;

public class SQLCommand implements CommandExecutor {

	public SQLCommand(String cmd, Main plugin) {
		plugin.getCommand(cmd).setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("xelvoria.core.cmd.sql")) {
			sender.sendMessage(CoreUtils.colorize("&e[SQL] &f> &7Error: You don't have permission to use that command."));
			return true;
		}
		
		if (args.length == 0) {
			sender.sendMessage(CoreUtils.colorize("&e[SQL] &f> &7Error. Usage: /sql [query]"));
			return true;
		}
		
		if(args[0].equalsIgnoreCase("SELECT")) {
			ResultSet rs = CoreUtils.sendQuery(CoreUtils.toString(args));
			int a = 0;
			try {
				while (rs.next()) {
					a = a + 1;
					sender.sendMessage("");
					sender.sendMessage(CoreUtils.colorize("&6-------Row " + a + "-------"));
					for (int i = 1; i != rs.getMetaData().getColumnCount() + 1; i++) {
						sender.sendMessage(CoreUtils.colorize("&6" + rs.getMetaData().getColumnName(i) + ": " + rs.getString(i)));
					}
					sender.sendMessage(CoreUtils.colorize("&6--------------------"));
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		if(args[0].equalsIgnoreCase("INSERT")) {
			sender.sendMessage(CoreUtils.colorize("&6Result: " + CoreUtils.sendInsert(CoreUtils.toString(args))));
		}
		if(args[0].equalsIgnoreCase("UPDATE") || args[0].equalsIgnoreCase("DELETE")) {
			sender.sendMessage(CoreUtils.colorize("&6Result: " + CoreUtils.sendUpdate(CoreUtils.toString(args))));
		}
		
		return true;
	}
}
