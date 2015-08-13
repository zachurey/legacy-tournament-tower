package com.zafcoding.zachscott.tt.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.entity.Player;

import com.zafcoding.zachscott.PlayerProfile;
import com.zafcoding.zachscott.TT;

public class Update {

	TT tt = TT.tt;

	int hello = 0;
	HashMap<Integer, Boolean> returnis = new HashMap<Integer, Boolean>();

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
		Thread ii = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					tt.c = tt.MySQL.openConnection();
				} catch (ClassNotFoundException e) {
					tt.mysql = false;
					System.out
							.println("Zach you screwed something up in the MySQL at "
									+ e.getCause());
					e.printStackTrace();
				} catch (SQLException e) {
					tt.mysql = false;
					System.out
							.println("Zach you screwed something up in the MySQL at "
									+ e.getCause());
					e.printStackTrace();
				}
				return;
			}
		});
		ii.start();
	}

	private void close() {

	}

	private boolean aisPlayer(final Player player) throws SQLException {
		int f;
		if (!tt.isMySQL()) {
			return false;
		}
		if (!gotConnection()) {
			Connect();
		}
		f = new Random().nextInt(10000);
		while (returnis.get(f) != null) {
			f = new Random().nextInt(10000);
		}
		returnis.put(f, false);
		final int ff = f;
		Thread ii = new Thread(new Runnable() {

			@Override
			public void run() {
				Statement statement;
				try {
					statement = tt.c.createStatement();
					ResultSet res = statement
							.executeQuery("SELECT * FROM TTPlayer WHERE UUID = '"
									+ player.getUniqueId() + "';");
					res.next();
					tt.debugMsg("The player " + player.getDisplayName()
							+ " has a profile: "
							+ (res.getString("DisplayName")));
					if (res.getString("DisplayName") != null) {
						returnis.put(ff, true);
					} else {
						returnis.put(ff, false);
					}
					close();
					return;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		ii.start();

		boolean is = returnis.get(f);
		returnis.remove(f);

		return is;
	}

	public void addPlayer(final Player player) throws SQLException {
		if (!tt.isMySQL()) {
			return;
		}
		if (!gotConnection()) {
			Connect();
		}
		Thread ii = new Thread(new Runnable() {

			@Override
			public void run() {
				tt.debugMsg("Creating profile for " + player.getDisplayName()
						+ "...");
				Statement statement;
				try {
					statement = tt.c.createStatement();
					statement
							.executeUpdate("INSERT INTO TTPlayer (`UUID`, `DisplayName`, `Tokens`, `Kills`, `Deaths`, `Wins`, `MatchStart`, `MatchFinish`) VALUES ('"
									+ player.getUniqueId()
									+ "', '"
									+ player.getDisplayName().toString()
									+ "', 0, 0, 0, 0, 0);");
					tt.debugMsg("INSERT INTO TTPlayer (`UUID`, `DisplayName`, `Tokens`, `Kills`, `Deaths`, `Wins`, `MatchStart`, `MatchFinish`) VALUES ('"
							+ player.getUniqueId()
							+ "', '"
							+ player.getDisplayName().toString()
							+ "', 0, 0, 0, 0, 0);");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return;
			}
		});
		ii.start();
	}

	public void updateDisplayName(final Player player) throws SQLException {
		if (!tt.isMySQL()) {
			return;
		}
		if (!gotConnection()) {
			Connect();
		}
		Thread ii = new Thread(new Runnable() {
			@Override
			public void run() {
				Statement statement;
				try {
					statement = tt.c.createStatement();

					statement
							.executeUpdate("UPDATE `TTPlayer` SET `DisplayName`='"
									+ player.getDisplayName()
									+ "' WHERE UUID = '"
									+ player.getUniqueId()
									+ "'");
					tt.debugMsg("UPDATE `TTPlayer` SET `DisplayName`='"
							+ player.getDisplayName() + "' WHERE UUID = '"
							+ player.getUniqueId() + "'");
					close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
			}
		});
		ii.start();
	}

	public int getKill(Player player) throws SQLException {
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
		close();
		return get;
	}

	public int getTokens(Player player) throws SQLException {
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
		get = res.getInt("Tokens");
		return get;
	}

	/**
	 * 
	 * @param player
	 *            The Player
	 * @param i
	 *            The amount of tokens to be added
	 * @param bool
	 *            Weather to change PlayerProfile's amount too
	 * @throws SQLException
	 */
	public void setTokens(final Player player, final int i, boolean bool)
			throws SQLException {
		if (!tt.isMySQL()) {
			return;
		}
		if (!gotConnection()) {
			Connect();
		}
		if (bool) {
			TT.info.getPP(player).setCoins(i);
		}
		Thread ii = new Thread(new Runnable() {
			@Override
			public void run() {
				Statement statement;
				try {
					statement = tt.c.createStatement();
					statement.executeUpdate("UPDATE `TTPlayer` SET `Tokens`="
							+ i + " WHERE UUID = '" + player.getUniqueId()
							+ "'");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				tt.debugMsg("UPDATE `TTPlayer` SET `Tokens`=" + i
						+ " WHERE UUID = '" + player.getUniqueId() + "'");
				return;
			}
		});
		ii.start();
	}

	/**
	 * 
	 * @param player
	 *            The Player
	 * @param i
	 *            The amount of tokens to be added
	 * @param bool
	 *            Weather to change PlayerProfile's amount too
	 * @throws SQLException
	 */
	public void addTokens(final Player player, final int i, boolean bool)
			throws SQLException {
		if (!tt.isMySQL()) {
			return;
		}
		if (!gotConnection()) {
			Connect();
		}
		if (bool) {
			TT.info.getPP(player)
					.setCoins(TT.info.getPP(player).getCoins() + i);
		}
		Thread ii = new Thread(new Runnable() {
			@Override
			public void run() {
				Statement statement;
				try {
					statement = tt.c.createStatement();
					statement.executeUpdate("UPDATE `TTPlayer` SET `Tokens`="
							+ TT.info.getPP(player).getCoins() + i
							+ " WHERE UUID = '" + player.getUniqueId() + "'");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				tt.debugMsg("UPDATE `TTPlayer` SET `Tokens`="
						+ TT.info.getPP(player).getCoins() + i
						+ " WHERE UUID = '" + player.getUniqueId() + "'");
				return;
			}
		});
		ii.start();
	}

	public void setKills(Player player, int i) throws SQLException {
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
		close();
	}

	public int getDeath(Player player) throws SQLException {
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
		close();
		return get;
	}

	public void setDeath(Player player, int i) throws SQLException {
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
		close();
	}

	public int getWin(Player player) throws SQLException {
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
		get = res.getInt("Wins");
		close();
		return get;
	}

	public void setWin(Player player, int i) throws SQLException {
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
		close();
	}

	public int getLose(Player player) throws SQLException {
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
		get = res.getInt("Lost");
		close();
		return get;
	}

	public void setLose(Player player, int i) throws SQLException {
		if (!tt.isMySQL()) {
			return;
		}
		if (!gotConnection()) {
			Connect();
		}
		Statement statement = tt.c.createStatement();
		statement.executeUpdate("UPDATE `TTPlayer` SET `Lost`=" + i
				+ " WHERE UUID = '" + player.getUniqueId() + "'");
		tt.debugMsg("UPDATE `TTPlayer` SET `Lost`=" + i + " WHERE UUID = '"
				+ player.getUniqueId() + "'");
		close();
	}

	public void playerJoin(final Player player) {
		Thread ii = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					if (isPlayer(player) == true) {
						tt.debugMsg("(1) MySQL player join for player "
								+ player.getDisplayName() + " called!");
						updateDisplayName(player);
						tt.debugMsg("Updated the username for "
								+ player.getDisplayName());
						return;
					}
					tt.debugMsg("(2) MySQL player join for player "
							+ player.getDisplayName() + " called!");
					addPlayer(player);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		ii.start();
	}

	public void syncornizeStats(final Player player) {
		Thread ii = new Thread(new Runnable() {

			@Override
			public void run() {

				PlayerProfile pp = TT.info.getPP(player);
				try {
					setKills(player, getKill(player) + pp.getTotalKill());
					setDeath(player, getDeath(player) + pp.getTotalDeaths());
					setTokens(player, getTokens(player) + pp.getCoins(), true);

					pp.setKills(getKill(player));
					pp.setDeath(getDeath(player));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
			}
		});
		ii.start();
	}

}