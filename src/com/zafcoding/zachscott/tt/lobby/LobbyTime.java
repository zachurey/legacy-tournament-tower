package com.zafcoding.zachscott.tt.lobby;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.zafcoding.zachscott.Info;
import com.zafcoding.zachscott.Info.ServerState;
import com.zafcoding.zachscott.TT;
import com.zafcoding.zachscott.tt.game.Game;

public class LobbyTime {

	TT tt = TT.tt;
	Info info = TT.info;
	Game game = TT.game;

	public void LobbyHeartBeat() {
		if (info.getState() == ServerState.Pre_Game) {
			for (Player pp : info.getPlayers()) {
				pp.setHealth(40);
				pp.setCanPickupItems(false);
				pp.setFoodLevel(20);
				pp.setFireTicks(0);
			}
			tt.debugMsg("The Heart Beat has run! (1)");
			if (info.getPlayerCount() <= tt.getMaxPlayer()
					&& !(info.getPlayerCount() <= 0)) {
				tt.debugMsg("There are less than or more than 30 players on!");
				if (info.getTime() <= tt.getLobbyTime()) {
					tt.debugMsg("The time is less than or equal 120!");
					dotime();
				}
			}
		}
	}

	private void dotime() {
		tt.debugMsg("The time has run! [" + info.getTime() + "]");
		if(info.getPlayerCount() >= 15 && info.getTime() > 60){
			info.setTime(60);
			info.broadCast(ChatColor.BLUE + "Time skipping to 60 seconds!");
		}
		if (tt.isBroadCastTime(info.getTime())) {
			info.broadCast(ChatColor.AQUA + "" + info.getTime()
					+ " seconds till the game starts!");
			info.broadCast(ChatColor.AQUA + "Map: " + info.world);
			info.broadCast(ChatColor.AQUA + "Creator: " + info.worldcreate);
			info.broadCast(ChatColor.DARK_AQUA + "" + info.getPlayerCount()
					+ ChatColor.AQUA + "/" + tt.getMaxPlayer()
					+ " players in the lobby.");
			info.broadCast(ChatColor.AQUA + "(At least " + tt.getMinPlayer()
					+ " players are needed to start the game)");
			info.broadCast("");
		}
		if (info.getTime() == 0) {
			if (info.getPlayerCount() >= tt.getMinPlayer()) {
				info.setState(ServerState.In_Game);
				game.start();
			} else {
				info.broadCast("");
				info.broadCast("");
				info.broadCast("");
				info.broadCast(ChatColor.BLUE
						+ "No enough players! That game needs at least "
						+ tt.getMinPlayer() + " players to start!");
				info.broadCast(ChatColor.BLUE + "Restarting da clock!");
				info.broadCast("");
				info.setTime(tt.getLobbyTime());
				return;
			}
		}
		info.setTime(info.getTime() - 1);
		for (Player pl : info.getPlayers()) {
			pl.playSound(pl.getLocation(), Sound.NOTE_PIANO, 1, 1);
			pl.setLevel(info.getTime());
		}
	}

}
