package com.zafcoding.zachscott.tt.lobby;

import java.sql.SQLException;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;

import com.zafcoding.zachscott.Info;
import com.zafcoding.zachscott.Info.ServerState;
import com.zafcoding.zachscott.PlayerProfile;
import com.zafcoding.zachscott.TT;
import com.zafcoding.zachscott.tt.mysql.Update;

public class LobbyListiners implements Listener {

	Info info = TT.info;
	TT tt = TT.tt;
	Update update = TT.update;

	@EventHandler
	public void PlayerPre(AsyncPlayerPreLoginEvent e) {
		tt.debugMsg("The PreLoginEvent has been called!");
		if (tt.bannedplayers.contains(e.getName())) {
			e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED,
					ChatColor.YELLOW + "You have been banned!");
			return;
		}
		if (tt.mods.containsKey(e.getName())
				|| e.getName().equalsIgnoreCase("legostarwarszach")) {
			return;
		}
		if (info.getState() == ServerState.In_Game
				|| info.getState() == ServerState.Post_Game
				|| info.getState() == ServerState.Resetting) {
			tt.debugMsg("The PreLoginEvent has been called!");
			e.disallow(
					AsyncPlayerPreLoginEvent.Result.KICK_BANNED,
					ChatColor.RED
							+ "You can not join the game while the game state is: "
							+ info.getState());
			tt.debugMsg("The player " + e.getName()
					+ " has been kicked due to the wrong state!");
			// pp.kickPlayer(ChatColor.RED +
			// "You can not join the game while the game state is: " +
			// info.getState());
			return;
		}
		if (info.getPlayerCount() >= tt.getMaxPlayer()) {
			tt.debugMsg("Too many players! " + e.getName()
					+ " has been disallowed!");
			tt.debugMsg("Max players: " + tt.getMaxPlayer() + " Min players: "
					+ tt.getMinPlayer());
			e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED,
					ChatColor.RED + "The game is currently full!");
			return;
		}
	}

	@EventHandler
	public void PlayerChat(AsyncPlayerChatEvent e) {
		if (e.getPlayer().getDisplayName().equalsIgnoreCase("zackscott")) {
			e.setFormat(ChatColor.WHITE
					+ "["
					+ ChatColor.AQUA
					+ ChatColor.BOLD
					+ "BlameMe"
					+ ChatColor.RESET
					+ ChatColor.WHITE
					+ "]<"
					+ e.getPlayer().getDisplayName()
					+ "> "
					+ ChatColor.translateAlternateColorCodes('&',
							e.getMessage()));
			return;
		}
		if (tt.mods.containsKey(e.getPlayer().getDisplayName())) {
			if (tt.mods.get(e.getPlayer().getDisplayName()).equalsIgnoreCase(
					"admin")) {
				e.setFormat(ChatColor.WHITE
						+ "["
						+ ChatColor.DARK_AQUA
						+ "Admin"
						+ ChatColor.WHITE
						+ "]<"
						+ e.getPlayer().getDisplayName()
						+ "> "
						+ ChatColor.translateAlternateColorCodes('&',
								e.getMessage()));
				return;
			}
			if (tt.mods.get(e.getPlayer().getDisplayName()).equalsIgnoreCase(
					"mod")) {
				e.setFormat(ChatColor.WHITE
						+ "["
						+ ChatColor.DARK_AQUA
						+ "Mod"
						+ ChatColor.WHITE
						+ "]<"
						+ e.getPlayer().getDisplayName()
						+ "> "
						+ ChatColor.translateAlternateColorCodes('&',
								e.getMessage()));
				return;
			}
			e.setFormat(ChatColor.WHITE
					+ "["
					+ ChatColor.DARK_AQUA
					+ ""
					+ tt.mods.get(e.getPlayer().getDisplayName())
					+ ChatColor.WHITE
					+ "]<"
					+ e.getPlayer().getDisplayName()
					+ "> "
					+ ChatColor.translateAlternateColorCodes('&',
							e.getMessage()));
			return;
		}
		if (tt.vips.contains(e.getPlayer().getDisplayName())) {
			e.setFormat(ChatColor.WHITE
					+ "["
					+ ChatColor.GREEN
					+ "VIP"
					+ ChatColor.WHITE
					+ "]<"
					+ e.getPlayer().getDisplayName()
					+ "> "
					+ ChatColor.translateAlternateColorCodes('&',
							e.getMessage()));
			return;
		}
		e.setFormat(ChatColor.GRAY + "<" + e.getPlayer().getDisplayName()
				+ "> " + e.getMessage());
	}

	@EventHandler
	public void PlayerJoin(PlayerJoinEvent e) {
		e.getPlayer().setMaxHealth(40);
		e.getPlayer().setLevel(0);
		info.addPlayer(e.getPlayer());
		e.setJoinMessage(ChatColor.GOLD + "" + e.getPlayer().getDisplayName()
				+ "" + ChatColor.WHITE + " has joined the game!"
				+ ChatColor.YELLOW + " (" + info.getPlayerCount() + "/"
				+ tt.getMaxPlayer() + ")");
		e.getPlayer().setGameMode(GameMode.ADVENTURE);
		e.getPlayer().getInventory().setHelmet(new ItemStack(Material.AIR));
		e.getPlayer().getInventory().setChestplate(new ItemStack(Material.AIR));
		e.getPlayer().getInventory().setLeggings(new ItemStack(Material.AIR));
		e.getPlayer().getInventory().setBoots(new ItemStack(Material.AIR));
		try {
			sendSpawn(e.getPlayer());
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			if (update.isPlayer(e.getPlayer()) == true) {
				tt.debugMsg("(1) MySQL player join for player " + e.getPlayer()
						+ " called!");
				update.updateDisplayName(e.getPlayer());
				tt.debugMsg("Updated the username for "
						+ e.getPlayer().getDisplayName());
				return;
			}
			tt.debugMsg("(2) MySQL player join for player " + e.getPlayer()
					+ " called!");
			update.addPlayer(e.getPlayer());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private void sendSpawn(Player player) throws InterruptedException {
		Thread.sleep(4);
		player.teleport(player.getWorld().getSpawnLocation());
	}

	@EventHandler
	public void onPlayerLeavae(PlayerQuitEvent e) {
		if (info.getPP(e.getPlayer()) != null) {
			Player p = e.getPlayer();
			info.removePlayer(p);
			info.setCount(info.getPlayerCount() - 1);
			PlayerProfile hp = info.getPP(p);
			if (hp.getLevel() == 1) {
				info.p1--;
			}
			if (hp.getLevel() == 2) {
				info.p2--;
			}
			if (hp.getLevel() == 3) {
				info.p3--;
			}
			if (hp.getLevel() == 4) {
				info.p4--;
			}
			if (hp.getEffect() != null) {
				hp.getEffect().cancel();
				hp.setEffect(null);
			}
		}
		e.setQuitMessage(ChatColor.GRAY + "" + e.getPlayer().getDisplayName()
				+ " has quit!");
	}

	@EventHandler
	public void PickupItem(PlayerPickupItemEvent e) {
		Player p = e.getPlayer();
		if (info.isPronePlayer(p)) {
			return;
		}
		e.setCancelled(true);
	}

	@EventHandler
	public void DropItem(PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		if (info.isPronePlayer(p)) {
			return;
		}
		e.setCancelled(true);

	}

	@EventHandler
	public void BlockPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		if (info.isPronePlayer(p)) {
			return;
		}
		e.setCancelled(true);

	}

	@EventHandler
	public void BlockBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if (info.isPronePlayer(p)) {
			return;
		}
		e.setCancelled(true);

	}

	@EventHandler
	public void BlockBreak(EntityDamageByEntityEvent e) {
		Entity en = e.getEntity();
		if (en instanceof Player) {
			if (info.getState() == ServerState.In_Game) {
				if (e.getDamager() instanceof Player) {
					Player pp = (Player) e.getDamager();
					if (pp.getItemInHand().getType() == Material.COOKIE) {
						((Player) en)
								.sendMessage(ChatColor.DARK_AQUA + "SLAP!");
					}
				}
			}
			if (info.isPronePlayer((Player) en)) {
				return;
			}
			if (!(info.getState() == ServerState.In_Game) || info.pvp == false) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void CraftItem(CraftItemEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (info.isPronePlayer(p)) {
			return;
		}
		e.setResult(Result.DENY);
	}

	@EventHandler
	public void ServerPing(ServerListPingEvent e) {
		e.setMaxPlayers(tt.getMaxPlayer());
		if (info.getState() == ServerState.Pre_Game) {
			e.setMotd("[" + ChatColor.GOLD + ChatColor.BOLD
					+ "Tournament Tower" + ChatColor.RESET + "]\n"
					+ ChatColor.GREEN
					+ "The server is currently in the lobby! \n"
					+ ChatColor.WHITE);
		}
		if (info.getState() == ServerState.In_Game) {
			e.setMotd("[" + ChatColor.BOLD + ChatColor.GOLD
					+ "Tournament Tower" + ChatColor.RESET + "]\n"
					+ ChatColor.AQUA + "The server is currently in game!");
		}
		if (info.getState() == ServerState.Post_Game) {
			e.setMotd("[" + ChatColor.BOLD + ChatColor.GOLD
					+ "Tournament Tower" + ChatColor.RESET + "]\n"
					+ ChatColor.GOLD
					+ "The game is over! Play again in a minute!");
		}
		if (info.getState() == ServerState.Resetting) {
			e.setMotd("[" + ChatColor.BOLD + ChatColor.GOLD
					+ "Tournament Tower" + ChatColor.RESET + "]\n"
					+ ChatColor.RED + "The game is resetting!");
		}
	}

	@EventHandler
	public void onMobTarget(EntityTargetLivingEntityEvent e) {
		if (e.getTarget() instanceof Player) {
			Player p = (Player) e.getTarget();
			if (info.getPlayers().contains(p)) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onBlockForm(BlockFormEvent e) {
		if (e.getBlock().getType() == Material.ICE) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockForm(EntityDamageEvent e) {
		if (!(info.getState() == ServerState.In_Game)) {
			e.setCancelled(true);
		}
		if (e.getEntity() instanceof Player) {
			Player pl = (Player) e.getEntity();
			if (info.nopvp.contains(pl)) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onBlockFade(BlockFadeEvent e) {
		if (e.getBlock().getType() == Material.ICE
				|| e.getBlock().getType() == Material.SNOW
				|| e.getBlock().getType() == Material.SNOW_BLOCK) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void WeatherChange(WeatherChangeEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void PlayerRegenerate(EntityRegainHealthEvent e) {
		if (!(e.getRegainReason() == RegainReason.CUSTOM
				|| e.getRegainReason() == RegainReason.MAGIC || e
					.getRegainReason() == RegainReason.MAGIC_REGEN)) {
			e.setCancelled(true);
		}
	}
}