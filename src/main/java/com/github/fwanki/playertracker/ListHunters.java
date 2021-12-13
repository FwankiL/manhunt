package com.github.fwanki.playertracker;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListHunters implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("listhunters") && sender instanceof Player) {
			Player player = (Player) sender;
			
			if(!( PlayerTracker.Hunters.isEmpty() ) ) {
				player.sendMessage(
						Component.text("Hunters: ")
								.color(NamedTextColor.RED)
				);

				PlayerTracker.Hunters.forEach( hunter ->
						player.sendMessage( hunter.displayName().color(NamedTextColor.RED) )
				);

				return true;
			}
			player.sendMessage(
					Component.text("There are no Hunters!")
							.color(NamedTextColor.DARK_RED)
			);
			return true;
		}
		return false;
	}
}
