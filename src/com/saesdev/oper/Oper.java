package com.saesdev.oper;

import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Oper extends JavaPlugin implements Listener {
	
	public static List<String> logged;
	private static Logger log;
	private static boolean isLogging = true;
	
	private FileHandler fh;
	private SimpleFormatter form;
	
	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		log = Logger.getLogger("Oper");
		getServer().getPluginManager().registerEvents(this, this);
		getCommand("oper").setExecutor(new OperCommand(this));
		if(getConfig().getBoolean("settings.log") == false) {
			isLogging = false;
		}
		
		if(isLogging) {
			String path = this.getDataFolder() + "log.txt";
			try {
				fh = new FileHandler(path);
				form = new SimpleFormatter();
				fh.setFormatter(form);
				log.addHandler(fh);
			} catch(Exception ex) {
				ex.printStackTrace();
				getServer().getPluginManager().disablePlugin(this);
			}
		}
	}

	@Override
	public void onDisable() {
		
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if(e.getPlayer().isOp()) {
			e.getPlayer().setOp(false);
			e.getPlayer().sendMessage(ChatColor.GRAY + "[Oper] " + ChatColor.RED + "You have been logged out. Please do /oper <pass> to regain operator status.");
		}
	}
	
	public boolean isLoggedIn(String player) {
		if(logged.contains(player)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static Logger getOperLogger() {
		return log;
	}
}