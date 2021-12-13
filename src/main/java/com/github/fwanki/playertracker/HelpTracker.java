package com.github.fwanki.playertracker;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpTracker implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("helptracker") && sender instanceof Player) { // Literally copied straight from plugin.yml
			Player player = (Player) sender;
			
			player.sendMessage("Haha imagine having to use help lololol. Anyways here you go nub" +
					"Commands:" +
					"  track:\n" +
					"    description: It tracks a specified player\n" +
					"    usage: /<command> [Player]\n" +
					"  addhunter:\n" +
					"    description: Adds players to internal team of Hunters\n" +
					"    usage: /<command> [Player] [Player]...\n" +
					"  removehunter:\n" +
					"    description: Removes players from team \"hunters\".\n" +
					"    usage: /<command> [Player] [Player]...\n" +
					"  helptracker:\n" +
					"    description: Gives advice on how to execute commands\n" +
					"    usage: /<command>\n" +
					"  start:\n" +
					"    description: Starts the game\n" +
					"    usage: /<command>\n" +
					"  removespeedrunner:\n" +
					"    description: Makes Speedrunner null (no speedrunner)\n" +
					"    usage: /<command>\n" +
					"  addspeedrunner:\n" +
					"    description: Adds players to internal team of Speedrunners\n" +
					"    usage: /<command> [Player] [Player]...\n" +
					"  setup:\n" +
					"    description: Creates hunters and speedrunners team & makes keepInventory true; first command to run\n" +
					"    usage: /<command>\n" +
					"  listhunters:\n" +
					"    description: Lists players in team Hunters\n" +
					"    usage: /<command>\n" +
					"  listspeedrunners:\n" +
					"    description: Lists players in team Speedrunners\n" +
					"    usage: /<command>");
			
			return true;
		}
		return false;
	}

}
