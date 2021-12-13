package com.github.fwanki.playertracker;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddSpeedrunner implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("addspeedrunner") && sender instanceof Player) {
			if(args.length >= 1) {
				for (String arg : args) { // Loops through each argument and remove each player from Hunters in the program (still need to remove from team)

					try { // Making sure the argument given is a player + one that is in the team
						Player player = Bukkit.getPlayer(arg);

						if (!(PlayerTracker.Hunters.contains(player) || PlayerTracker.Speedrunners.contains(player))) {
							PlayerTracker.Speedrunners.add(player);
							Bukkit.broadcast(
									player.displayName().append( Component.text(" has been added to the Speedrunners!"))
											.color(NamedTextColor.BLUE)
											.decoration(TextDecoration.BOLD, true)
							);
						} else sender.sendMessage("The player cannot be added! They are a Hunter or are already a Speedrunner!");
					} catch (Exception e) {
						sender.sendMessage(e.toString());
						sender.sendMessage(
								Component.text("The player you specified is either not a player, or they are already in the team!")
										.color(NamedTextColor.DARK_RED)
						);
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}

}
