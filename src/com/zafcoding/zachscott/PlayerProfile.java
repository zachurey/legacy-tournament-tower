package com.zafcoding.zachscott;

import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.slikey.effectlib.Effect;

public class PlayerProfile {

	TT tt = TT.tt;
	Info info = TT.info;
	Player p;
	int deaths = 0;
	int kills = 0;
	int totalkill = 0;
	int totaldeath = 0;
	int level = 1;
	Effect ef;
	boolean ggg = false;
	boolean gll = false;
	boolean powerdid = false;
	ArrayList<DebugTag> debuger = new ArrayList<DebugTag>();
	int powertime = 0;
	boolean spec = false;
	boolean winner = false;
	public int killinrow = 0;
	int coins = 0;
	public boolean killbrag = false;
	public ShopProfile sp;

	public PlayerProfile(Player player) {
		p = player;
		sp = new ShopProfile(player.getUniqueId().toString());
	}

	public void setEffect(Effect eff) {
		ef = eff;
	}

	public Effect getEffect() {
		return ef;
	}

	public void setLevel(int num) {
		tt.debugMsg("setLevel() has been rung! (" + num + ")");
		level = num;
	}

	public int getPowerTime() {
		return powertime;
	}

	public void setPower(boolean bool) {
		powerdid = bool;
	}

	public void setPowerTime(int tt) {
		powertime = tt;
	}

	public void reset() {
		powerdid = false;
		killbrag = false;
		powertime = 0;
		killinrow = 0;
	}

	public void enablePower(int i) {
		powertime = i;
		powerdid = true;
	}

	public boolean isPower() {
		return powerdid;
	}

	public int getLevel() {
		return level;
	}

	public Player getPlayer() {
		return p;
	}

	public int getDeaths() {
		return deaths;
	}

	public int getKills() {
		return kills;
	}

	public int getTotalDeaths() {
		return totaldeath;
	}

	public int getTotalKill() {
		return totalkill;
	}

	public void setDeath(int num) {
		deaths = num;
	}

	public void setKills(int num) {
		kills = num;
	}

	public void setTotalKill(int num) {
		totalkill = num;
	}

	public void setTotalDeath(int num) {
		totaldeath = num;
	}

	public boolean hasDebugtag(DebugTag dt) {
		return debuger.contains(dt);
	}

	public void addDebugTag(DebugTag dt) {
		debuger.add(dt);
	}

	public void removeDugtag(DebugTag dt) {
		if (hasDebugtag(dt)) {
			debuger.remove(dt);
		}
	}

	public enum DebugTag {
		kill, death, level
	}

	public void setGG() {
		ggg = true;
	}

	public boolean isGG() {
		return ggg;
	}

	public void setGL() {
		gll = true;
	}

	public boolean isGL() {
		return gll;
	}

	public void spectMode() {
		spec = true;
		p.teleport(tt.getSpawn(info.world, 1, 1));
		p.setGameMode(GameMode.SPECTATOR);
	}

	public boolean isSpect() {
		return spec;
	}

	public void setWinner(boolean b) {
		winner = b;
	}

	public boolean isWinner() {
		return winner;
	}

	public int getCoins() {
		return coins;
	}

	public void setCoins(int i) {
		coins = i;
	}

	public void updateCoin() {
		p.getInventory()
				.addItem(new ItemStack(Material.GOLD_INGOT, getCoins()));
	}

}
