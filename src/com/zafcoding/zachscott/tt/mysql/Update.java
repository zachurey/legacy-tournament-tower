package com.zafcoding.zachscott.tt.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.entity.Player;

import com.zafcoding.zachscott.Info;
import com.zafcoding.zachscott.PlayerProfile;
import com.zafcoding.zachscott.TT;

public class Update {

	TT tt = TT.tt;
	Info info = TT.info;

	public boolean isPlayer(Player player) {
		try {
			return aisPlayer(player);
		} catch (SQLException e) {
			return false;
		}
	}

	public boolean gotConnection() {
		return tt.c != null;
	}

	private void Connect() {
		if (!tt.mysql) {
			return;
		}
		try {
			tt.c = tt.MySQL.openConnection();
		} catch (ClassNotFoundException e) {
			tt.mysql = false;
			System.out.println("Zach you screwed something up in the MySQL at "
					+ e.getCause());
			e.printStackTrace();
		} catch (SQLException e) {
			tt.mysql = false;
			System.out.println("Zach you screwed something up in the MySQL at "
					+ e.getCause());
			e.printStackTrace();
		}
	}

	private void Close() {
		
	}

	public void updateStats() throws SQLException{
		for(Player p : info.getPlayers()){
			PlayerProfile pp = info.getPP(p);
			setKills(p, getKill(p) + pp.getTotalKill());
			setDeath(p, getDeath(p) + pp.getTotalDeaths());
		}
		tt.c.close();
	}
	
	private boolean aisPlayer(Player player) throws SQLException {
		boolean is = false;
		if (!tt.isMySQL()) {
			return false;
		}
		if (!gotConnection()) {
			Connect();
		}
		Statement statement = tt.c.createStatement();
		ResultSet res = statement
				.executeQuery("SELECT * FROM TTPlayer WHERE UUID = '"
						+ player.getUniqueId() + "';");
		res.next();
		tt.debugMsg("The player " + player.getDisplayName()
				+ " has a profile: " + (res.getString("DisplayName")));
		if (res.getString("DisplayName") != null) {
			is = true;
		} else {
			is = false;
		}
		Close();
		return is;
	}

	public void addPlayer(Player player) throws SQLException {
		if (!tt.isMySQL()) {
			return;
		}
		if (!gotConnection()) {
			Connect();
		}
		tt.debugMsg("Creating profile for " + player.getDisplayName() + "...");
		Statement statement = tt.c.createStatement();
		statement
				.executeUpdate("INSERT INTO TTPlayer (`UUID`, `DisplayName`, `Kills`, `Deaths`, `Wins`, `MatchStart`, `MatchFinish`) VALUES ('"
						+ player.getUniqueId()
						+ "', '"
						+ player.getDisplayName().toString()
						+ "', 0, 0, 0, 0, 0);");
		tt.debugMsg("INSERT INTO TTPlayer (`UUID`, `DisplayName`, `Kills`, `Deaths`, `Wins`, `MatchStart`, `MatchFinish`) VALUES ('"
				+ player.getUniqueId()
				+ "', '"
				+ player.getDisplayName().toString() + "', 0, 0, 0, 0, 0);");
		Close();
	}

	public void updateDisplayName(Player player) throws SQLException {
		if (!tt.isMySQL()) {
			return;
		}
		if (!gotConnection()) {
			Connect();
		}
		Statement statement = tt.c.createStatement();
		statement.executeUpdate("UPDATE `TTPlayer` SET `DisplayName`='"
				+ player.getDisplayName() + "' WHERE UUID = '"
				+ player.getUniqueId() + "'");
		tt.debugMsg("UPDATE `TTPlayer` SET `DisplayName`='"
				+ player.getDisplayName() + "' WHERE UUID = '"
				+ player.getUniqueId() + "'");
		Close();
	}

	private int getKill(Player player) throws SQLException {
		int get = 0;
		if (!tt.isMySQL()) {
			return 0;
		}
		if (!gotConnection()) {
			Connect();
		}
		Statement statement = tt.c.createStatement();
		ResultSet res = statement
				.executeQuery("SELECT * FROM TTPlayer WHERE UUID = '"
						+ player.getUniqueId() + "';");
		res.next();
		get = res.getInt("Kills");
		Close();
		return get;
	}

	private void setKills(Player player, int i) throws SQLException {
		if (!tt.isMySQL()) {
			return;
		}
		if (!gotConnection()) {
			Connect();
		}
		Statement statement = tt.c.createStatement();
		statement.executeUpdate("UPDATE `TTPlayer` SET `Kills`=" + i
				+ " WHERE UUID = '" + player.getUniqueId() + "'");
		tt.debugMsg("UPDATE `TTPlayer` SET `Kills`=" + i + " WHERE UUID = '"
				+ player.getUniqueId() + "'");
		Close();
	}

	private int getDeath(Player player) throws SQLException {
		int get = 0;
		if (!tt.isMySQL()) {
			return 0;
		}
		if (!gotConnection()) {
			Connect();
		}
		Statement statement = tt.c.createStatement();
		ResultSet res = statement
				.executeQuery("SELECT * FROM TTPlayer WHERE UUID = '"
						+ player.getUniqueId() + "';");
		res.next();
		get = res.getInt("Deaths");
		Close();
		return get;
	}

	private void setDeath(Player player, int i) throws SQLException {
		if (!tt.isMySQL()) {
			return;
		}
		if (!gotConnection()) {
			Connect();
		}
		Statement statement = tt.c.createStatement();
		statement.executeUpdate("UPDATE `TTPlayer` SET `Deaths`=" + i
				+ " WHERE UUID = '" + player.getUniqueId() + "'");
		tt.debugMsg("UPDATE `TTPlayer` SET `Deaths`=" + i + " WHERE UUID = '"
				+ player.getUniqueId() + "'");
		Close();
	}

	private int getWin(Player player) throws SQLException {
		int get = 0;
		if (!tt.isMySQL()) {
			return 0;
		}
		if (!gotConnection()) {
			Connect();
		}
		Statement statement = tt.c.createStatement();
		tt.debugMsg("SELECT * FROM TTPlayer WHERE UUID = '"
				+ player.getUniqueId() + "';");
		ResultSet res = statement
				.executeQuery("SELECT * FROM TTPlayer WHERE UUID = '"
						+ player.getUniqueId() + "';");
		res.next();
		get = res.getInt("Wins");
		Close();
		return get;
	}

	private void setWin(Player player, int i) throws SQLException {
		if (!tt.isMySQL()) {
			return;
		}
		if (!gotConnection()) {
			Connect();
		}
		Statement statement = tt.c.createStatement();
		statement.executeUpdate("UPDATE `TTPlayer` SET `Wins`=" + i
				+ " WHERE UUID = '" + player.getUniqueId() + "'");
		tt.debugMsg("UPDATE `TTPlayer` SET `Wins`=" + i + " WHERE UUID = '"
				+ player.getUniqueId() + "'");
		Close();
	}

	private int getMatchStart(Player player) throws SQLException {
		int get = 0;
		if (!tt.isMySQL()) {
			return 0;
		}
		if (!gotConnection()) {
			Connect();
		}
		Statement statement = tt.c.createStatement();
		ResultSet res = statement
				.executeQuery("SELECT * FROM TTPlayer WHERE UUID = '"
						+ player.getUniqueId() + "';");
		res.next();
		get = res.getInt("MatchStart");
		Close();
		return get;
	}

	private void setMatchStart(Player player, int i) throws SQLException {
		if (!tt.isMySQL()) {
			return;
		}
		if (!gotConnection()) {
			Connect();
		}
		Statement statement = tt.c.createStatement();
		statement.executeUpdate("UPDATE `TTPlayer` SET `MatchStart`=" + i
				+ " WHERE UUID = '" + player.getUniqueId() + "'");
		tt.debugMsg("UPDATE `TTPlayer` SET `MatchStart`=" + i
				+ " WHERE UUID = '" + player.getUniqueId() + "'");
		Close();
	}

	private int getMatchFinish(Player player) throws SQLException {
		int get = 0;
		if (!tt.isMySQL()) {
			return 0;
		}
		if (!gotConnection()) {
			Connect();
		}
		Statement statement = tt.c.createStatement();
		ResultSet res = statement
				.executeQuery("SELECT * FROM TTPlayer WHERE UUID = '"
						+ player.getUniqueId() + "';");
		res.next();
		get = res.getInt("MatchFinish");
		Close();
		return get;
	}

	private void setMatchFinish(Player player, int i) throws SQLException {
		if (!tt.isMySQL()) {
			return;
		}
		if (!gotConnection()) {
			Connect();
		}
		Statement statement = tt.c.createStatement();
		statement.executeUpdate("UPDATE `TTPlayer` SET `MatchFinish`=" + i
				+ " WHERE UUID = '" + player.getUniqueId() + "'");
		tt.debugMsg("UPDATE `TTPlayer` SET `MatchFinish`=" + i
				+ " WHERE UUID = '" + player.getUniqueId() + "'");
		Close();
	}

}
