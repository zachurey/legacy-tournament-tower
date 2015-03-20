package com.zafcoding.zachscott;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Info {

	TT tt = TT.tt;
	ArrayList<Player> players = new ArrayList<Player>();
	int lobbytime = 0;
	int playerc = 0;
	int maxplay = 0;
	int minplay = 0;
	public static int p1 = 0;
	public static int p2 = 0;
	public static int p3 = 0;
	public static int p4 = 0;
	public boolean pvp = false;
	public boolean cangl = false;
	public String world = "";
	public String worldcreate = "";
	ServerState state = ServerState.Pre_Game;
	ArrayList<Player> comelist = new ArrayList<Player>();
	ArrayList<PlayerProfile> profiles = new ArrayList<PlayerProfile>();
	HashMap<Integer, Integer> num = new HashMap<Integer, Integer>();
	Player winner = null;
	public boolean cangg = false;

	public Info() {
		maxplay = tt.getMaxPlayer();
		minplay = tt.getMinPlayer();
		lobbytime = tt.getLobbyTime();
		num.put(1, 1);
		num.put(2, 1);
		num.put(3, 1);
		num.put(4, 1);
	}

	public boolean isVIP(Player player) {
		return tt.getConfig().getList("Vips").contains(player.getUniqueId());
	}

	public void convertVIP() {

	}

	public void setWinner(Player win) {
		winner = win;
	}

	public Player getWinner() {
		return winner;
	}

	public void addPlayerPro(Player player) {
		profiles.add(new PlayerProfile(player));
	}

	public void removePlayerPro(Player player) {
		for (Object pp : profiles.toArray()) {
			if (pp instanceof PlayerProfile) {
				if (((PlayerProfile) pp).getPlayer().equals(player)) {
					profiles.remove(pp);
				}
			}
		}
	}

	public PlayerProfile getPP(Player pl) {
		for (Object pp : profiles.toArray()) {
			if (pp instanceof PlayerProfile) {
				if (((PlayerProfile) pp).getPlayer().equals(pl)) {
					return (PlayerProfile) pp;
				}
			}
		}
		return null;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void addPlayer(Player p) {
		if (getState() == ServerState.Pre_Game) {
			if (getPlayerCount() >= maxplay) {
				p.kickPlayer(ChatColor.RED + "The server is full!");
				return;
			}
			playerc++;
			players.add(p);
			p.getInventory().clear();
			PlayerProfile pp = new PlayerProfile(p);
			addPlayerPro(p);
			p.sendMessage(ChatColor.YELLOW + "You have joined the game!");
			return;
		} else {
			p.kickPlayer(ChatColor.RED
					+ "You can not join the game while the game state is: "
					+ getState());
		}

	}

	public void removePlayer(Player p) {
		if (players.contains(p)) {
			players.remove(p);
		}
	}

	public int getTime() {
		return lobbytime;
	}

	public void setTime(Integer time) {
		lobbytime = time;
	}

	public enum ServerState {
		Pre_Game, In_Game, Post_Game, Resetting
	}

	public ServerState getState() {
		return state;
	}

	public void setState(ServerState server) {
		state = server;
	}

	public int getPlayerCount() {
		return playerc;
	}

	public void setCount(Integer in) {
		playerc = in;
	}

	public void broadCast(String message) {
		for (Player play : players) {
			play.sendMessage(message);
		}
	}

	public ArrayList<Player> getPronePlayers() {
		return comelist;
	}

	public boolean isPronePlayer(Player player) {
		return comelist.contains(player);
	}

	public void addPronePlayer(Player player) {
		comelist.add(player);
	}

	public void removePronePlayer(Player player) {
		comelist.remove(player);
	}

	public int getNext(Integer qwe) {
		int w1 = num.get(qwe);
		if (w1 == 8) {
			num.put(qwe, 1);
		} else {
			num.put(qwe, num.get(qwe) + 1);
		}
		return w1;
	}

}