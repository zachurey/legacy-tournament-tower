package com.zafcoding.zachscott.tt.game;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Random;

import org.bukkit.ChatColor;

import com.zafcoding.zachscott.Info;
import com.zafcoding.zachscott.Info.ServerState;
import com.zafcoding.zachscott.PlayerProfile;
import com.zafcoding.zachscott.TT;
import com.zafcoding.zachscott.UpdatePlayer;
import com.zafcoding.zachscott.tt.mysql.Update;
//import lilypad.client.connect.api.request.RequestException;
//import lilypad.client.connect.api.request.impl.RedirectRequest;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

public class Game {

	TT tt = TT.tt;
	Info info = TT.info;
	Update update = TT.update;
	UpdatePlayer up = TT.updatePlayer;
	int i1 = 0;
	int ii1 = 0;
	boolean done1 = false;
	public boolean didi = false;
	boolean did2 = false;
	int smallcount = 15;

	public void clear() {
		i1 = 0;
		ii1 = 0;
		done1 = false;
		didi = false;
		did2 = false;
		smallcount = 15;
	}

	public Game() {
		clear();
	}

	public void start() {
		info.cangl = true;
		info.broadCast(ChatColor.GREEN + "Do /gl to wish everyone good luck!");
		i1 = Bukkit.getServer().getScheduler()
				.scheduleSyncRepeatingTask(tt, new Runnable() {

					@Override
					public void run() {
						if (did2) {
							return;
						}
						if (smallcount == 0) {
							did2 = true;
							starterGame();
							return;
						}
						info.broadCast(ChatColor.GOLD
								+ "The game is starting in " + smallcount);
						info.broadCast("");
						smallcount--;

					}
				}, 20, 20);

	}

	public int getRandom(int small, int large) {
		Random rand = new Random();
		return rand.nextInt(large) + small;
	}

	private void starterGame() {
		info.setState(ServerState.In_Game);
		info.cangg = false;
		info.cangl = false;
		for (Player pp : info.getPlayers()) {
			/*
			 * try { update.setMatchStart(pp, update.getMatchStart(pp) + 1); }
			 * catch (SQLException e) { e.printStackTrace(); }
			 */
			info.p1++;
			pp.setFlying(false);
			Location l = tt.getSpawn(info.world, 1, info.getNext(1));
			if (!l.getChunk().isLoaded()) {
				l.getChunk().load();
			}
			pp.teleport(l);
			if (tt.gunmode) {
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
						"crackshot give " + pp.getDisplayName() + " Ak-47");
			} else {
				pp.getInventory().setHelmet(new ItemStack(Material.AIR));
				pp.getInventory().setChestplate(new ItemStack(Material.AIR));
				pp.getInventory().setLeggings(new ItemStack(Material.AIR));
				pp.getInventory().setBoots(new ItemStack(Material.AIR));
				pp.getInventory().addItem(new ItemStack(Material.WOOD_SWORD));
				pp.getInventory().addItem(new ItemStack(Material.BOW));
				pp.getInventory().addItem(new ItemStack(Material.ARROW, 8));
			}
		}
		info.broadCast(ChatColor.LIGHT_PURPLE
				+ "Tournament Tower has begun! Good luck!");
		info.broadCast(ChatColor.DARK_GREEN
				+ "You have a 10 second period to run around!");
		info.pvp = false;
		i1 = Bukkit.getServer().getScheduler()
				.scheduleSyncRepeatingTask(tt, new Runnable() {

					@Override
					public void run() {
						if (didi) {
							return;
						}
						info.pvp = true;
						info.broadCast(ChatColor.DARK_RED
								+ "The grace period has ended! You may now fight!");
						Bukkit.getScheduler().cancelTask(i1);
						didi = true;
					}
				}, 200, 200);
	}

	public void endGame(Player winner) {
		info.setState(ServerState.In_Game);
		info.cangg = true;
		PlayerProfile mostk = null;
		PlayerProfile mostd = null;
		tt.debugMsg("The game is ending!");
		/*
		 * try { update.updateStats(); } catch (SQLException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
		/*
		 * try { int wincount = update.getWin(winner); update.setWin(winner,
		 * wincount + 1); } catch (SQLException e1) { // TODO Auto-generated
		 * catch block e1.printStackTrace(); }
		 */
		info.setState(ServerState.Post_Game);
		info.broadCast(tt.pre + " Tournament Tower is over!");
		info.broadCast(ChatColor.YELLOW + "============");
		info.broadCast(tt.pre + ChatColor.YELLOW + " Congrats to the winner "
				+ ChatColor.GREEN + winner.getDisplayName() + ChatColor.GOLD
				+ "!");
		info.broadCast(ChatColor.GREEN + "Do /gg to be a good sport!");
		for (Player ppp : info.getPlayers()) {
			/*
			 * try { update.setMatchFinish(ppp, update.getMatchFinish(ppp) + 1);
			 * } catch (SQLException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); }
			 */
			if (ppp == winner) {
				PlayerProfile pq = info.getPP(ppp);
				pq.setWinner(true);
				ppp.teleport(tt.getSpawn(info.world, 5, 1));
			} else {
				ppp.teleport(ppp.getWorld().getSpawnLocation());
			}
			PlayerProfile pq = info.getPP(ppp);
			PlayerProfile ppq = null;
			if (mostk == null) {
				mostk = pq;
			}
			if (mostd == null) {
				mostd = pq;
			}
			if (mostk.getTotalKill() < pq.getTotalKill()) {
				mostk = pq;
			}
			if (mostd.getTotalDeaths() < pq.getTotalDeaths()) {
				mostd = pq;
			}
		}
		info.broadCast(tt.pre + ChatColor.YELLOW + " Most kills: "
				+ ChatColor.AQUA + mostk.getPlayer().getDisplayName() + " ("
				+ mostk.getTotalKill() + ")");
		info.broadCast(tt.pre + ChatColor.YELLOW + " Most deaths: "
				+ ChatColor.AQUA + mostd.getPlayer().getDisplayName() + " ("
				+ mostd.getTotalDeaths() + ")");
		info.broadCast(ChatColor.YELLOW + "============");
		try {
			up.saveScores();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ii1 = Bukkit.getServer().getScheduler()
				.scheduleSyncRepeatingTask(tt, new Runnable() {
					@Override
					public void run() {
						if (done1 == true) {
							for (Player p1 : Bukkit.getOnlinePlayers()) {
								p1.chat("/hub");
							}
							info.setState(ServerState.Resetting);
							for (Player ooo : Bukkit.getOnlinePlayers()) {
								ooo.kickPlayer(ChatColor.RED
										+ "The Game is over! Rejoin in a moment to play again!");
							}
							try {
								update.updateStats();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Bukkit.getScheduler().cancelTask(ii1);
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
									"restart");
						} else {
							info.broadCast(tt.pre
									+ ChatColor.GREEN
									+ " The server is restarting in 10 seconds!");
							done1 = true;
						}
					}
				}, 200, 200);
	}

	// Took me 42 minutes to remember to put the return in there
	public void killCheck(PlayerProfile tp) {
		tt.debugMsg("killCheck called!");
		if (tp.getKills() >= 5) {
			tt.debugMsg("The player has " + tp.getKills());
			if (tp.getLevel() == 1) {
				tt.debugMsg("The player be level 1!");
				info.p1--;
				info.p2++;
				tp.setKills(0);
				tp.setLevel(2);
				tp.getPlayer().teleport(
						tt.getSpawn(info.world, 2, info.getNext(2)));
				tp.getPlayer().sendMessage(
						ChatColor.GREEN + "You have leveled up to level 2!");
				tp.getPlayer().playSound(tp.getPlayer().getLocation(),
						Sound.LEVEL_UP, 1, 1);
				info.broadCast(ChatColor.GREEN + ""
						+ tp.getPlayer().getDisplayName()
						+ " has leveled up to level 2!");
				// pe.HAPPY_VILLAGER.display(tp.getPlayer().getLocation(), 0f,
				// 1f,
				// 0f, 1f, 10, aa);
				return;
			}
			if (tp.getLevel() == 2) {
				tt.debugMsg("The player be level 2!");
				tp.setKills(0);
				tp.setLevel(3);
				info.p2--;
				info.p3++;
				tp.getPlayer().teleport(
						tt.getSpawn(info.world, 3, info.getNext(3)));
				tp.getPlayer().sendMessage(
						ChatColor.GREEN + "You have leveled up to level 3!");
				tp.getPlayer().playSound(tp.getPlayer().getLocation(),
						Sound.LEVEL_UP, 1, 1);
				info.broadCast(ChatColor.GREEN + ""
						+ tp.getPlayer().getDisplayName()
						+ " has leveled up to level 3!");
				// pe.HAPPY_VILLAGER.display(tp.getPlayer().getLocation(), 0f,
				// 1f,
				// 0f, 1f, 10, aa);
				return;
			}
			if (tp.getLevel() == 3) {
				tt.debugMsg("The player be level 3!");
				tp.setKills(0);
				tp.setLevel(4);
				info.p3--;
				info.p4++;
				tp.getPlayer().teleport(
						tt.getSpawn(info.world, 4, info.getNext(4)));
				tp.getPlayer().sendMessage(
						ChatColor.GREEN + "You have leveled up to level 4!");
				tp.getPlayer().playSound(tp.getPlayer().getLocation(),
						Sound.LEVEL_UP, 1, 1);
				info.broadCast(ChatColor.GREEN + ""
						+ tp.getPlayer().getDisplayName()
						+ " has leveled up to level 4!");
				// pe.HAPPY_VILLAGER.display(tp.getPlayer().getLocation(), 0f,
				// 1f,
				// 0f, 1f, 10, aa);
				return;
			}
			if (tp.getLevel() == 4) {
				tt.debugMsg("The player be level 4!");
				tp.setKills(0);
				tp.setLevel(5);
				info.p4--;
				tp.getPlayer().teleport(tt.getSpawn(info.world, 5, 0));
				tp.getPlayer().sendMessage(
						ChatColor.GREEN + "You have leveled up to level 5!");
				tp.getPlayer().playSound(tp.getPlayer().getLocation(),
						Sound.LEVEL_UP, 1, 1);
				info.broadCast(ChatColor.GREEN + ""
						+ tp.getPlayer().getDisplayName()
						+ " has leveled up to level 5!");
				// pe.HAPPY_VILLAGER.display(tp.getPlayer().getLocation(), 0f,
				// 1f,
				// 0f, 1f, 10, aa);
				killCheck(tp);
			}
			if (tp.getLevel() == 5) {
				tt.debugMsg("The player be level 5!");
				tp.getPlayer().playSound(tp.getPlayer().getLocation(),
						Sound.LEVEL_UP, 1, 1);
				endGame(tp.getPlayer());
				// pe.HAPPY_VILLAGER.display(tp.getPlayer().getLocation(), 0f,
				// 1f,
				// 0f, 1f, 10, aa);
				return;
			}
		} else {
			tp.getPlayer().sendMessage(
					ChatColor.YELLOW + "You need " + ChatColor.GOLD + ""
							+ (5 - tp.getKills()) + " kill(s) to advance!");
		}
	}

	public void deathCheck(PlayerProfile pp) {
		if (pp.getLevel() == 1) {
			if (pp.getDeaths() == 5) {
				pp.setDeath(0);
				pp.getPlayer().playSound(pp.getPlayer().getLocation(),
						Sound.ANVIL_USE, 1, 1);
				info.broadCast(ChatColor.GREEN + ""
						+ pp.getPlayer().getDisplayName()
						+ "has leveled down for cheating!");
				return;
			}
		} else {
			if (pp.getLevel() == 2) {
				if (pp.getDeaths() == 5) {
					pp.setDeath(0);
					pp.setLevel(1);
					pp.getPlayer()
							.teleport(tt.getSpawn("", 1, info.getNext(1)));
					pp.getPlayer().playSound(pp.getPlayer().getLocation(),
							Sound.ANVIL_USE, 1, 1);
					pp.getPlayer().sendMessage(
							ChatColor.GREEN
									+ "You have leveled down to level 1!");
					return;
				}
			}
			if (pp.getLevel() == 3) {
				if (pp.getDeaths() == 5) {
					pp.setDeath(0);
					pp.setLevel(2);
					pp.getPlayer()
							.teleport(tt.getSpawn("", 2, info.getNext(2)));
					pp.getPlayer().playSound(pp.getPlayer().getLocation(),
							Sound.ANVIL_USE, 1, 1);
					pp.getPlayer().sendMessage(
							ChatColor.GREEN
									+ "You have leveled down to level 2!");
					return;
				}
			}
			if (pp.getLevel() == 4) {
				if (pp.getDeaths() == 5) {
					pp.setDeath(0);
					pp.setLevel(3);
					pp.getPlayer()
							.teleport(tt.getSpawn("", 3, info.getNext(3)));
					pp.getPlayer().playSound(pp.getPlayer().getLocation(),
							Sound.ANVIL_USE, 1, 1);
					pp.getPlayer().sendMessage(
							ChatColor.GREEN
									+ "You have leveled down to level 3!");
					return;
				}
			}
		}
	}

}