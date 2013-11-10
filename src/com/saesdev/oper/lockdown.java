package com.saesdev.oper;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class lockdown implements CommandExecutor {
	public boolean lockdownstatus = false;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length == 0) {
			Bukkit.getServer().broadcastMessage(ChatColor.GRAY + "[LockDown] " + ChatColor.RED + "The Server is now under lockdown. Please standby.");
			Oper oper = new Oper();
			oper.setLockdownMode(true);
		}
		return false;
	}

}
