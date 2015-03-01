package com.zafcoding.zachscott.tt.game;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import com.zafcoding.zachscott.Info;
import com.zafcoding.zachscott.Info.ServerState;
import com.zafcoding.zachscott.PlayerProfile;
import com.zafcoding.zachscott.TT;
import com.zafcoding.zachscott.tt.mysql.Update;

public class GameLisitner implements Listener {

	boolean wer = false;
	int it = 0;
	TT tt = TT.tt;
	Info info = TT.info;
	Game game = TT.game;
	Update update = TT.update;
	PowerUp pu = TT.power;

	@EventHandler
	public void onPlayerDie(PlayerDeathEvent e) {
		if (info.getState() == ServerState.In_Game) {
			if (e.getEntity().getKiller() != null) {
				PlayerProfile pp = info.getPP(e.getEntity());
				pp.reset();
				pp.getPlayer().setExp(0f);
				pp.getPlayer().getInventory().clear();
				pp.setDeath(pp.getDeaths() + 1);
				pp.setTotalDeath(pp.getTotalDeaths() + 1);
				e.setDeathMessage(ChatColor.GOLD + ""
						+ e.getEntity().getDisplayName() + ChatColor.WHITE
						+ " Was " + ChatColor.RED + "Butchered"
						+ ChatColor.WHITE + " By " + ChatColor.GOLD
						+ e.getEntity().getKiller().getDisplayName());
				e.setDroppedExp(0);
				PlayerProfile tp = info.getPP(e.getEntity().getKiller());
				tp.setKills(tp.getKills() + 1);
				tp.setTotalKill(tp.getTotalKill() + 1);
				game.killCheck(tp);
				return;
			} else {
				tt.debugMsg("The instance of the killer is "
						+ e.getEntity().getKiller());
				/*
				 * PlayerProfile pp = info.getPP(e.getEntity());
				 * pp.setPower(false); pp.getPlayer().getInventory().clear();
				 * pp.setDeath(pp.getDeaths() + 1);
				 * pp.setTotalDeath(pp.getTotalDeaths() + 1);
				 * e.setDeathMessage(ChatColor.GOLD + "" +
				 * e.getEntity().getDisplayName() + ChatColor.WHITE + " Was " +
				 * ChatColor.RED + "Butchered" + ChatColor.WHITE + " By " +
				 * ChatColor.GOLD + "Nature!"); return;
				 */
			}
		} else {
			e.setDeathMessage(e.getEntity().getDisplayName()
					+ " somehow managed to kill himself, but do not worry! He did not die!");
			e.getEntity().setHealth(40);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		tt.debugMsg("PlayerRespawn!");
		final Player pp = e.getPlayer();
		PlayerProfile tp = info.getPP(pp);
		// pp.getInventory().getHelmet().setType(Material.LEATHER_HELMET);
		// pp.getInventory().getChestplate().setType(Material.LEATHER_CHESTPLATE);
		// pp.getInventory().getLeggings().setType(Material.LEATHER_LEGGINGS);
		// pp.getInventory().getBoots().setType(Material.LEATHER_BOOTS);
		pp.getInventory().setHelmet(new ItemStack(Material.AIR));
		pp.getInventory().setChestplate(new ItemStack(Material.AIR));
		pp.getInventory().setLeggings(new ItemStack(Material.AIR));
		pp.getInventory().setBoots(new ItemStack(Material.AIR));
		pp.getInventory().addItem(new ItemStack(Material.WOOD_SWORD));
		pp.getInventory().addItem(new ItemStack(Material.BOW));
		pp.getInventory().addItem(new ItemStack(Material.ARROW, 8));
		e.setRespawnLocation(tt.getSpawn(info.world, tp.getLevel(),
				info.getNext(tp.getLevel())));
		Bukkit.getServer().getScheduler()
				.scheduleSyncRepeatingTask(tt, new Runnable() {

					@Override
					public void run() {
						if (it > 4) {
							wer = true;
						}
						if (!wer) {
							pp.setHealth(40);
							pp.setFireTicks(0);
							it++;
						}
					}
				}, 10, 10);
	}

	@EventHandler
	public void PlayerInteract(PlayerInteractEvent e) {
		if (e.getAction() == Action.PHYSICAL) {
			if (e.getClickedBlock().getRelative(BlockFace.DOWN).getType() == Material.BEDROCK) {
				pu.addPowerUp(e.getPlayer());
			}
		}
		if (info.isPronePlayer(e.getPlayer())) {
			return;
		}
	}

}
