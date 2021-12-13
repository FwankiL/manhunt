package com.github.fwanki.playertracker;

import java.util.ArrayList;
import java.util.List;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class StartGame implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("start") && sender instanceof Player) {
			if(sender.isOp()) {
				
				/* Items to give */
				
				ItemStack blindMeat = new ItemStack(Material.BEEF, 1);

				List<Component> blindMeatLore = new ArrayList<Component>();
				blindMeatLore.add(Component.text("Thou shalt blind thou enemies"));
				
				ItemMeta blindMeatMeta = blindMeat.getItemMeta();
				blindMeatMeta.lore(blindMeatLore);

				blindMeatMeta.displayName(
						Component.text("blindMeat")
						.decoration(TextDecoration.BOLD, true)
						.decoration(TextDecoration.ITALIC, false)
						.color(NamedTextColor.BLUE)
				);

				blindMeat.setItemMeta(blindMeatMeta);
				
				ItemStack weakenMeat = new ItemStack(Material.PORKCHOP, 1);
				
				List<Component> weakenMeatLore = new ArrayList<Component>();
				weakenMeatLore.add(Component.text("Thou shalt make thou enemies weak"));
				
				ItemMeta weakenMeatMeta = weakenMeat.getItemMeta();
				weakenMeatMeta.lore(weakenMeatLore);

				weakenMeatMeta.displayName(
						Component.text("weakenMeat")
								.decoration(TextDecoration.BOLD, true)
								.decoration(TextDecoration.ITALIC, false)
								.color(NamedTextColor.RED)
				);

				weakenMeat.setItemMeta(weakenMeatMeta);
				
				ItemStack playerTracker = new ItemStack(Material.COMPASS, 1);
				
				List<Component> playerTrackerLore = new ArrayList<Component>();
				playerTrackerLore.add(Component.text("Player Tracker"));
				
				ItemMeta playerTrackerMeta = playerTracker.getItemMeta();
				playerTrackerMeta.lore(playerTrackerLore);

				playerTrackerMeta.displayName(
						Component.text("Player Tracker")
								.decoration(TextDecoration.BOLD, true)
								.decoration(TextDecoration.ITALIC, false)
								.color(NamedTextColor.RED)
				);

				playerTracker.setItemMeta(playerTrackerMeta);
				
				
				Bukkit.broadcast( Component.text("Game starting!").decoration(TextDecoration.BOLD, true) );

				ArrayList<PotionEffect> huntersPotions = new ArrayList<PotionEffect> ();
				huntersPotions.add( new PotionEffect(PotionEffectType.BLINDNESS, 90*PlayerTracker.ticksToSeconds, 0) );
				huntersPotions.add( new PotionEffect(PotionEffectType.SLOW, 90*PlayerTracker.ticksToSeconds, 255) );
				huntersPotions.add( new PotionEffect(PotionEffectType.JUMP, 90*PlayerTracker.ticksToSeconds, 128) );
				huntersPotions.add( new PotionEffect(PotionEffectType.SLOW_DIGGING, 90*PlayerTracker.ticksToSeconds, 255 ) );
				
				ItemStack speedrunnerItem = new ItemStack(Material.IRON_AXE, 1);

				List<Component> speedrunnerItemLore = new ArrayList<Component>();
				speedrunnerItemLore.add(Component.text("May break lol"));

				ItemMeta speedrunnerItemMeta = speedrunnerItem.getItemMeta();
				speedrunnerItemMeta.lore(speedrunnerItemLore);

				speedrunnerItemMeta.displayName(
						Component.text("Trusty Axe")
								.decoration(TextDecoration.BOLD, true)
								.decoration(TextDecoration.ITALIC, false)
								.color(NamedTextColor.BLUE)
				);

				speedrunnerItem.setItemMeta(speedrunnerItemMeta);
				
				for(Player plr : Bukkit.getOnlinePlayers()) { // Loops through each online player and makes them execute their own commands
					plr.getInventory().clear();
					plr.setFoodLevel(20);
					
					if( PlayerTracker.Hunters.contains(plr) ) {
						plr.playSound(plr.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1, 0);
						plr.setWalkSpeed(0.25f);
						plr.addPotionEffects(huntersPotions);
						plr.getInventory().addItem(weakenMeat);
						plr.getInventory().addItem(playerTracker);
						plr.sendMessage(Component.text(
								"You've been given 'weakenMeat' and a player tracker.\n"
								+ "The weakenMeat can be used to inflict slowness for 30 seconds and weakness for 15 on all speedrunners.\n"
								+ "The player tracker lets you track a player with the command /track [player].\n"
								+ "You have also been given a slight speed boost over the speedrunner(s). Good luck!")
								.color(NamedTextColor.RED));

					}
					
					if( PlayerTracker.Speedrunners.contains(plr) ) {
						plr.playSound(plr.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1, 0);
						plr.setWalkSpeed(0.2f);
						plr.addPotionEffect( new PotionEffect( PotionEffectType.SPEED, 90*PlayerTracker.ticksToSeconds, 2) );
						plr.getInventory().addItem(speedrunnerItem);
						plr.getInventory().addItem(blindMeat);
						plr.sendMessage(Component.text(
								"You've been given 'blindMeat' and an iron axe.\n"
								+ "blindMeat makes all hunters blind for 15 seconds with a 300 cooldown. Good luck!")
								.color(NamedTextColor.BLUE));
					}
					
					if ( !( PlayerTracker.Speedrunners.contains(plr) ) && !( PlayerTracker.Hunters.contains(plr) ) ) {
						plr.sendMessage("You are not in a team and will therefore be placed in spectator mode."); plr.setGameMode(GameMode.SPECTATOR);
					}

					/* changes time to day */

					Bukkit.getWorlds().get(0).setTime(0);
				}
				return true;
			}
			else sender.sendMessage("You do not have permissions!"); return true;
		}
		return false;
	}

}
