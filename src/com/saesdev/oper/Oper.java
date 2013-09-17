package com.saesdev.oper;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Oper extends JavaPlugin implements Listener {
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		this.saveDefaultConfig();
	}

	@Override
	public void onDisable() {
		
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerJoinEvent e) {
		if(e.getPlayer().isOp()) {
			Player p = e.getPlayer();
			p.sendMessage(ChatColor.GRAY + "[Oper] " + ChatColor.AQUA + "You have been deopped, please authenticate.");
			p.setOp(false);
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
		String user = sender.getName();
		if(label.equalsIgnoreCase("oper")) {
			if(this.getConfig().contains("passwords." + user)) {
				if(args.length == 1) {
					if(args[0].equalsIgnoreCase("reload")) {
						if(sender.isOp()) {
							this.reloadConfig();
							sender.sendMessage(ChatColor.GRAY + "[Oper] " + ChatColor.WHITE + "The Configuration has been reloaded.");
						} else {
							sender.sendMessage(ChatColor.GRAY + "[Oper] " + ChatColor.RED + "You don't have permission to preform that action.");
						}
					} else if(args[0].equalsIgnoreCase("help")) {
						sender.sendMessage(ChatColor.GRAY + "[Oper] " + ChatColor.WHITE + "Oper is a security Plugin for Bukkit");
						sender.sendMessage(ChatColor.GRAY + "[Oper] " + ChatColor.AQUA + "It will deop Opped users on login, and make them use a password!");
						sender.sendMessage(ChatColor.GRAY + "[Oper] " + ChatColor.WHITE + "/oper <password> :: Authenticate yourself with <password>");
						sender.sendMessage(ChatColor.GRAY + "[Oper] " + ChatColor.WHITE + "/oper reload :: Reload the Oper Config");
					} else {
					if(args[0].equals(this.getConfig().getString("passwords." + user))) {
						sender.sendMessage(ChatColor.GRAY + "[Oper] " + ChatColor.GREEN + "You have successfully authed yourself. You now have OP!");
						sender.setOp(true);
						sendOpMessage("&7[Oper] &a" + user + " Has just successfully authenticated!");
					} else {
						sendOpMessage("&7[Oper] &4" + user + " &c has just attemted to authenticate!");
						sender.sendMessage(ChatColor.GRAY + "[Oper] "  + ChatColor.RED + "That password is invalid!");
					}
					}
				} else if(args.length == 2) {
					if(args[0].equalsIgnoreCase("Changepass") || args[0].equalsIgnoreCase("Cp")) {
						if (args[1].length() >= 5) {
							this.getConfig().set(sender.getName().toLowerCase(), args[1]);
							sender.sendMessage(ChatColor.GRAY + "[Oper] " + ChatColor.WHITE + "Your password has been reset to " + args[1]);
						} else {
							sender.sendMessage(ChatColor.GRAY + "[Oper] " + ChatColor.RED + "Your password needs to be longer than 5 characters");
						}
					}
				} else {
					sender.sendMessage(ChatColor.GRAY + "[Oper] " + ChatColor.RED + "invalid arguments, please do /oper help");
				}
			} else {
				sender.sendMessage(ChatColor.GRAY + "[Oper] " + ChatColor.RED + "You don't have an Oper Password, Contact the server administrators for help!");
			}
		} else if(label.equalsIgnoreCase("isop")) {
			if(args.length != 1) {
				sender.sendMessage(ChatColor.GRAY + "[Oper] " + ChatColor.RED + "Invalid arguments, please do /isop <player>");
			} else {
				Player check = Bukkit.getServer().getPlayer(args[0]);
				if(check.isOp()) {
					sender.sendMessage(ChatColor.GRAY + "[Oper] " + ChatColor.AQUA + args[0] + " Is an Operator");
				} else {
					sender.sendMessage(ChatColor.GRAY + "[Oper] " + ChatColor.AQUA + args[0] + " Is NOT an operator");
				}
			}
		}
		return false;
	}
	
	public void sendOpMessage(String message) {
		for(Player p : Bukkit.getServer().getOnlinePlayers()) {
			if(p.isOp() || p.hasPermission("oper.messages")) {
				p.sendMessage(colorize(message));
			}
		}
	}
	
	String colorize(String m) {
		m = m.replaceAll("&", "�");
		return m;
	}
}
