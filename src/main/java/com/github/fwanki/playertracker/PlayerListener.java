package com.github.fwanki.playertracker;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class PlayerListener implements Listener {
	public PlayerListener(PlayerTracker plugin) { // Starts listener
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onMovement(PlayerMoveEvent e) {
		Player player = e.getPlayer();
		
		if( (!( PlayerTracker.Speedrunners.isEmpty() || PlayerTracker.Hunters.isEmpty() ) && PlayerTracker.toTrack.containsKey(player) ) ) {
			if( PlayerTracker.Hunters.contains(player) ) {

				int x = (int) PlayerTracker.toTrack.get(player).getLocation().getBlockX();
				int y = (int) PlayerTracker.toTrack.get(player).getLocation().getBlockY();
				int z = (int) PlayerTracker.toTrack.get(player).getLocation().getBlockZ();

				Component trackingMsg = Component.text("Tracking: " + PlayerTracker.toTrack.get(player).getDisplayName() + " §lX§r: " + String.valueOf(x) + ", §lY§r: " + String.valueOf(y) + ", §lZ§r: " + String.valueOf(z));

				ItemStack itemOH = player.getInventory().getItemInOffHand();
				ItemStack itemMH = player.getInventory().getItemInMainHand();


				if (itemOH.getItemMeta() != null) {
					if (itemOH.getItemMeta().hasLore() && itemOH.getType() == Material.COMPASS) {
						player.sendActionBar(trackingMsg);
						player.setCompassTarget(PlayerTracker.toTrack.get(player).getLocation());
					}
				}

				if (itemMH.getItemMeta() != null) {
					if (itemMH.getItemMeta().hasLore() && itemMH.getType() == Material.COMPASS) {
						player.sendActionBar(trackingMsg);
						player.setCompassTarget(PlayerTracker.toTrack.get(player).getLocation());
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player player = e.getEntity();
		
		if(PlayerTracker.Speedrunners.contains(player)) { // Checks if dead player is a Speedrunner
			Component deathMsg = player.displayName().append(Component.text(" has been slain!"))
					.color(NamedTextColor.RED);

			Bukkit.broadcast(deathMsg);
			PlayerTracker.Speedrunners.remove(player);
			player.setGameMode(GameMode.SPECTATOR);
			
			if(PlayerTracker.Speedrunners.isEmpty()) { // If all speedrunners died
				Component winMsg = Component.text("The hunters have won!")
						.color(NamedTextColor.DARK_RED)
						.decoration(TextDecoration.BOLD, true);

				Bukkit.broadcast(winMsg);

				for( Player plr : Bukkit.getOnlinePlayers() ) {
					plr.showTitle(Title.title(winMsg, winMsg));
					plr.playSound(plr.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1, 1);
					plr.setGameMode(GameMode.SPECTATOR);
				}
			}
		}
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {
		Entity entity = e.getEntity();
		
		if( !( entity instanceof Player ) ) {
			EntityType entityType = entity.getType();
			if( entityType.equals(EntityType.ENDER_DRAGON) ) {
				Component winMsg = Component.text("The Ender Dragon has been slain! The speedrunners win!")
						.color(NamedTextColor.GREEN)
						.decoration(TextDecoration.BOLD, true);

				Bukkit.broadcast(winMsg);
				for( Player plr : Bukkit.getOnlinePlayers() ) {
					plr.showTitle(Title.title(winMsg, winMsg));
					plr.playSound(plr.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1, 1);
					plr.setGameMode(GameMode.SPECTATOR);
				}
			}
		}
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		ItemStack droppedItem = e.getItemDrop().getItemStack();
		Player player = e.getPlayer();
		
		if( droppedItem.getType().equals(Material.BEEF) || droppedItem.getType().equals(Material.PORKCHOP) ) {
			if(droppedItem.getItemMeta().hasLore()) {
				player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
				player.sendMessage(
						Component.text("You're not allowed to do that, sir!")
						.color(NamedTextColor.DARK_RED)
						.decoration(TextDecoration.BOLD, true)
				);
				e.setCancelled(true);
			}
		}
	}


	@EventHandler
	public void onEat(PlayerItemConsumeEvent e) {
		Player player = e.getPlayer();
		ItemStack eatenItem = e.getItem();
		int delaySpeedrunner = 300;
		int delayHunters = 300;
		long tickToSeconds = 20L; // Converts ticks to seconds in .runTaskLater
		
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
		
		ItemStack blindMeat = new ItemStack(Material.BEEF, 1);

		ArrayList<Component> blindMeatLore = new ArrayList<Component>();
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
		
        /* Speedrunner's Effect */
		
		if(PlayerTracker.Speedrunners.contains(player) && eatenItem.getType() == Material.BEEF) {
			if(eatenItem.getItemMeta().hasLore()) {
				if(eatenItem.getItemMeta().lore().contains(Component.text("Thou shalt blind thou enemies"))) { // If you are a speedrunner and you eat invis meat
					for(Player plr : PlayerTracker.Hunters) plr.addPotionEffect( new PotionEffect( PotionEffectType.BLINDNESS, 15*PlayerTracker.ticksToSeconds, 0 ) );
					player.sendActionBar(
							Component.text("You've inflicted blindness on the Hunters!")
									.color(NamedTextColor.GREEN)
					);
					player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 1, 1);

					Bukkit.getScheduler().scheduleSyncDelayedTask(PlayerTracker.getPlugin(), new Runnable() {
						// Sets a delay, then gives the player back the meat, because Idk how to cancel the event after x time
		                @Override
		                public void run() {
		                	player.getInventory().addItem(blindMeat);
							player.sendActionBar(
									Component.text("Your blindMeat is back!")
											.color(NamedTextColor.BLUE)
											.decoration(TextDecoration.BOLD, true)
							);
							player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
						}
		            }, delaySpeedrunner*tickToSeconds);
				}
			}
			
			else if(eatenItem.getType() == Material.PORKCHOP) { // If you eat the opposing team's meat
				if(eatenItem.getItemMeta().hasLore()) {
					if(eatenItem.getItemMeta().lore().contains(Component.text("Thou shalt make thou enemies weak"))) {
						player.sendMessage(
								Component.text("You can't eat this!")
										.color(NamedTextColor.DARK_RED)
						);
						e.setCancelled(true);
					}
				}
			}
		}
        
		/* Hunter's Effect */
		
		else if(PlayerTracker.Hunters.contains(player) && eatenItem.getType() == Material.PORKCHOP) { // If you are a Hunter and you eat glow meat
			if(eatenItem.getItemMeta().hasLore()) {
				if(eatenItem.getItemMeta().lore().contains(Component.text("Thou shalt make thou enemies weak"))) {
					for(Player plr : PlayerTracker.Speedrunners) {
						plr.addPotionEffect( new PotionEffect( PotionEffectType.SLOW, 30*PlayerTracker.ticksToSeconds, 0 ) );
						plr.addPotionEffect( new PotionEffect ( PotionEffectType.WEAKNESS, 15*PlayerTracker.ticksToSeconds, 0 ) );
					}

					player.sendActionBar(
							Component.text("You have now inflicted weakness and slowness on your enemies!")
									.color(NamedTextColor.GREEN)
					);
					player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 1, 1);
						
					Bukkit.getScheduler().scheduleSyncDelayedTask(PlayerTracker.getPlugin(), new Runnable() { // Sets a delay, then gives the player back the meat, because Idk how to cancel the event after x time
		                @Override
		                public void run() {
		                	player.getInventory().addItem(weakenMeat);
							player.sendActionBar(
									Component.text("Your weakenMeat is back!")
											.color(NamedTextColor.RED)
											.decoration(TextDecoration.BOLD, true)
							);
							player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);

		                }
		            }, delayHunters*tickToSeconds);
				}
			}
			
			else if(eatenItem.getType() == Material.BEEF) { // If you eat the opposing team's meat
				if(eatenItem.getItemMeta().hasLore()) {
					if(eatenItem.getItemMeta().lore().contains(Component.text("Thou shalt blind thou enemies"))) {
						player.sendMessage(
								Component.text("You can't eat this!")
								.color(NamedTextColor.DARK_RED)
						);
						e.setCancelled(true);
					}
				}
			}
		}
		
		else { // No team, so no eating any team's meat
			if(eatenItem.getType() == Material.PORKCHOP || eatenItem.getType() == Material.BEEF) {
				if(eatenItem.getItemMeta().hasLore()) {
					if(eatenItem.getItemMeta().lore() != null) {
						player.sendMessage(
								Component.text("You need to be in the right team to eat this and get its effect!")
										.color(NamedTextColor.DARK_RED)
						);
						e.setCancelled(true);
					}
				}
			}
		}
	}
}
