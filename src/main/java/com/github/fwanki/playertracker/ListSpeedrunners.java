package com.github.fwanki.playertracker;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListSpeedrunners implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("listspeedrunners") && sender instanceof Player) {
			Player player = (Player) sender;

			if(!( PlayerTracker.Speedrunners.isEmpty() ) ) {
				player.sendMessage(
						Component.text("Speedrunners: ")
								.color(NamedTextColor.BLUE)
				);

				PlayerTracker.Speedrunners.forEach( speedrunner ->
						player.sendMessage( speedrunner.displayName().color(NamedTextColor.BLUE) )
				);

				return true;
			}
			player.sendMessage(
					Component.text("There are no speedrunners!")
							.color(NamedTextColor.DARK_RED)
			);
			return true;
		}
		return false;
	}

}
