package com.github.fwanki.playertracker;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveSpeedrunner implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("removespeedrunner") && sender instanceof Player) {
			if( !( PlayerTracker.Speedrunners.isEmpty() ) ) {
				if(args.length >= 1) {
					for (String arg : args) { // Loops through each argument and remove each player from Hunters in the program (still need to remove from team)
						try { // Making sure the argument given is a player
							Player player = Bukkit.getPlayer(arg);
							PlayerTracker.Speedrunners.remove(player);
							Bukkit.broadcast(
									player.displayName().append( Component.text(" has been removed from Speedrunners!") )
											.decoration(TextDecoration.BOLD, true)
							);
						} catch (Exception e) {
							sender.sendMessage(e.toString());
							sender.sendMessage(
									Component.text("The player you specified is either not a player, or they are not in the team!")
											.color(NamedTextColor.DARK_RED)
							);
						}
					}
					return true;
				}
			} else sender.sendMessage("There are no players in Speedrunners");
		}
		return false;
	}

}
