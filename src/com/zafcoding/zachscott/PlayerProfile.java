package com.zafcoding.zachscott;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.slikey.effectlib.Effect;

public class PlayerProfile {

	TT tt = TT.tt;
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

	public PlayerProfile(Player player) {
		p = player;
	}

	public void setEffect(Effect eff){
		ef = eff;
	}
	
	public Effect getEffect(){
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
		powertime = 0;
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

}
