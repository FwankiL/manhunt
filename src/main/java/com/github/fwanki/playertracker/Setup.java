package com.github.fwanki.playertracker;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.bukkit.ChatColor;
import org.bukkit.GameRule;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;


public class Setup implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("setup") && sender instanceof Player) {
			Player player = (Player) sender;
			
			try {
				ScoreboardManager manager = Bukkit.getScoreboardManager();
				Scoreboard board = manager.getMainScoreboard();
				Team huntersTeam = board.registerNewTeam("H");
				Team speedrunnersTeam = board.registerNewTeam("S");
				
				huntersTeam.displayName( Component.text("Hunters") );
				huntersTeam.color(NamedTextColor.RED);
				huntersTeam.setAllowFriendlyFire(false);
				huntersTeam.setOption(Option.NAME_TAG_VISIBILITY, OptionStatus.ALWAYS);
				huntersTeam.prefix( Component.text("[H] ").color(NamedTextColor.RED) );
				
				
				speedrunnersTeam.displayName( Component.text("Speedrunners") );
				speedrunnersTeam.color(NamedTextColor.BLUE);
				speedrunnersTeam.setAllowFriendlyFire(false);
				speedrunnersTeam.setOption(Option.NAME_TAG_VISIBILITY, OptionStatus.FOR_OWN_TEAM);
				speedrunnersTeam.prefix( Component.text("[S] ").color(NamedTextColor.BLUE) );

				for(Player plr : PlayerTracker.Hunters) huntersTeam.addEntry( plr.getDisplayName() );
				for(Player plr : PlayerTracker.Speedrunners) speedrunnersTeam.addEntry( plr.getDisplayName() );

				World world = Bukkit.getWorlds().get(0);
				
				world.setGameRule(GameRule.KEEP_INVENTORY, true);
				world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
				
				Bukkit.broadcast(
						Component.text("Setup complete!")
								.decoration(TextDecoration.BOLD, true)
				);
				return true;

			} catch(IllegalArgumentException e) {
				player.sendMessage(e.toString());
				player.sendMessage(
						Component.text("Setup has already been executed! It should only be used once!")
								.color(NamedTextColor.DARK_RED)
				);
				return true;
			}
		}
		
		return false;
	}

}
