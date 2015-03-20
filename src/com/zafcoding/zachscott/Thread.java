package com.zafcoding.zachscott;

import javax.swing.text.AbstractDocument.BranchElement;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.Vector;

import com.zafcoding.zachscott.Info.ServerState;
import com.zafcoding.zachscott.tt.game.Game;
import com.zafcoding.zachscott.tt.lobby.LobbyTime;

public class Thread implements Runnable {

	LobbyTime lt = new LobbyTime();
	TT tt = TT.tt;
	Info info = TT.info;
	Game game = TT.game;
	boolean rel = false;
	boolean done1 = false;
	PlayerProfile pp1 = null;
	boolean done2 = false;
	PlayerProfile pp2 = null;
	boolean done3 = false;
	PlayerProfile pp3 = null;

	@Override
	public void run() {
		lt.LobbyHeartBeat();
		for (World world : Bukkit.getWorlds()) {
			for (Entity chunk : world.getEntities()) {
				if (chunk instanceof Item & !(chunk instanceof Arrow)) {
					chunk.remove();
				}
			}
		}
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (!info.profiles.contains(info.getPP(player))) {
				player.kickPlayer(ChatColor.RED + "Please rejoin the game!");
				break;
			}
			if (info.getState() == ServerState.Pre_Game) {
				player.setHealth(40);
			}
			PlayerProfile pp = info.getPP(player);
			if (pp.isPower()) {
				pp.setPowerTime(pp.getPowerTime() + 1);
				TT.tt.debugMsg("Increased power time! (" + pp.getPowerTime()
						+ ")");
			}
			if (pp.getPowerTime() >= 60) {
				TT.tt.debugMsg("Resetted power time!");
				pp.reset();
			}
		}
		if (info.getState() == ServerState.In_Game) {
			for (Player player : info.getPlayers()) {
				PlayerProfile pp = info.getPP(player);
				pp.getPlayer().setExp(0.0f);
				pp.getPlayer().setFoodLevel(20);
				if (pp.isPower()) {
					player.setLevel(60 - pp.getPowerTime());
				} else {
					player.setLevel(pp.getLevel());
				}
			}
		}
		if (info.getWinner() == null) {

		} else {
			launchFirework(info.getWinner());
		}
		if (info.getState() == ServerState.In_Game
				|| info.getState() == ServerState.Resetting) {
			if (info.getPlayerCount() <= 0) {
				if (!rel) {
					Bukkit.getServer().reload();
					rel = true;
					return;
				}
			} else {
				if (Bukkit.getOnlinePlayers().length <= 0) {
					if (!rel) {
						Bukkit.getServer().reload();
						rel = true;
						return;
					}
				}
				rel = false;
			}
		}
		if (info.p1 == 1) {
			if ((info.p2 > 0) && pp1 == null) {

				for (Player pa : info.players) {
					if (info.getPP(pa).getLevel() == 1) {
						pp1 = info.getPP(pa);
					}
				}
				pp1.getPlayer()
						.sendMessage(
								ChatColor.GREEN
										+ "Since you are the only one on this level, I will let you advance...in 10 seconds! bahahahah!");
				Bukkit.getServer().getScheduler()
						.scheduleSyncRepeatingTask(tt, new Runnable() {

							@Override
							public void run() {
								if (!done1) {
									done1 = true;
									pp1.setKills(5);
									game.killCheck(pp1);
								}
							}
						}, 200, 200);
			}
		}
		if (info.p2 == 1) {
			if (!(info.p3 == 0) && info.p1 == 0 && pp2 == null) {

				for (Player pa : info.players) {
					if (info.getPP(pa).getLevel() == 2) {
						pp2 = info.getPP(pa);
					}
				}
				pp2.getPlayer()
						.sendMessage(
								ChatColor.GREEN
										+ "Since you are the only one on this level, I will let you advance...in 10 seconds! bahahahah!");
				Bukkit.getServer().getScheduler()
						.scheduleSyncRepeatingTask(tt, new Runnable() {

							@Override
							public void run() {
								if (!done2) {
									done2 = true;
									pp2.setKills(5);
									game.killCheck(pp2);
								}
							}
						}, 200, 200);
			}
		}
		if (info.p3 == 1) {
			if (!(info.p4 == 0) && info.p2 == 0 && pp3 == null) {

				for (Player pa : info.players) {
					if (info.getPP(pa).getLevel() == 3) {
						pp3 = info.getPP(pa);
					}
				}
				pp3.getPlayer()
						.sendMessage(
								ChatColor.GREEN
										+ "Since you are the only one on this level, I will let you advance...in 10 seconds! bahahahah!");
				Bukkit.getServer().getScheduler()
						.scheduleSyncRepeatingTask(tt, new Runnable() {

							@Override
							public void run() {
								if (!done3) {
									done3 = true;
									pp3.setKills(5);
									game.killCheck(pp3);
								}
							}
						}, 200, 200);
			}
		}
	}

	public void launchFirework(Player p) {
		Firework fw = (Firework) p.getWorld().spawn(p.getLocation(),
				Firework.class);
		FireworkMeta meta = fw.getFireworkMeta();
		Vector vv = new Vector(0, 3, 0);
		fw.setVelocity(vv);
	}

}
