package com.github.fwanki.playertracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerTracker extends JavaPlugin {
	protected static ArrayList<Player> Speedrunners = new ArrayList<Player>();
	protected static ArrayList<Player> Hunters = new ArrayList<Player>();
	protected static Map<Player, Player> toTrack = new HashMap<Player, Player>();
	protected static int ticksToSeconds = 20;
	private static Plugin plugin;


	@Override
	public void onEnable() {
		plugin = this;
		// Enabling PlayerListener
		new PlayerListener(this);
		
		
		// Getting all commands
		getCommand("track").setExecutor(new TrackerCommand());
		getCommand("addhunter").setExecutor(new AddHunter());
		getCommand("addspeedrunner").setExecutor(new AddSpeedrunner());
		getCommand("removehunter").setExecutor(new RemoveHunter());
		getCommand("helptracker").setExecutor(new HelpTracker());
		getCommand("start").setExecutor(new StartGame());
		getCommand("removespeedrunner").setExecutor(new RemoveSpeedrunner());
		getCommand("listhunters").setExecutor(new ListHunters());
		getCommand("listspeedrunners").setExecutor(new ListSpeedrunners());
		getCommand("setup").setExecutor(new Setup());
	}


	@Override
	public void onDisable() {
		
	}
	
	// We need this for your PlayerListener to use .runTaskLater
	public static Plugin getPlugin() {
		return plugin;
	}
}
