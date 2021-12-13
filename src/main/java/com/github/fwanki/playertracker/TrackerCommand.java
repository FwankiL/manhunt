package com.github.fwanki.playertracker;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TrackerCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("track") && sender instanceof Player) { // Convieniently also sets as speedrunner
			Player player = (Player) sender;
			
			if(args.length >= 1) {
				try {
					if(PlayerTracker.Hunters.contains(player) && PlayerTracker.toTrack.get(player) == null) {
						Player playerToTrack = Bukkit.getPlayer(args[0]); // Find a player at the first argument after command
						
						if(playerToTrack.isOnline()) {
							PlayerTracker.toTrack.put(player, playerToTrack);
							Bukkit.broadcast(
									Component.text("You are now tracking: ")
											.append(playerToTrack.displayName())
											.decoration(TextDecoration.BOLD, true)
							);
							return true;
						}
						
					} else player.sendMessage("You need to be a Hunter!");
				}
				catch(Exception e) { // If exception, usually no player found at args[0]
					player.sendMessage(e.toString());
					player.sendMessage(
							Component.text("Please enter a person to track!")
									.color(NamedTextColor.DARK_RED)
					);
				}
			}
		}
		return false;
	}
}
