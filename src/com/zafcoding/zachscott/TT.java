package com.zafcoding.zachscott;

import java.awt.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.zafcoding.zachscott.Info.ServerState;
import com.zafcoding.zachscott.tt.game.Game;
import com.zafcoding.zachscott.tt.game.GameLisitner;
import com.zafcoding.zachscott.tt.game.PowerUp;
import com.zafcoding.zachscott.tt.lobby.LobbyListiners;
import com.zafcoding.zachscott.tt.mysql.MySQL;
import com.zafcoding.zachscott.tt.mysql.Update;
/*import lilypad.client.connect.api.Connect;
 import lilypad.client.connect.api.ConnectSettings;
 import lilypad.client.connect.api.request.impl.RedirectRequest;
 import lilypad.client.connect.api.result.FutureResultListener;
 import lilypad.client.connect.api.result.Result;
 import lilypad.client.connect.api.result.StatusCode;
 import lilypad.client.connect.api.result.impl.RedirectResult;*/
import java.util.Random;

public class TT extends JavaPlugin {

	double version = 2.6;
	public String pre = ChatColor.GOLD + "[TT]";
	public static TT tt;
	public static Info info;
	public static Game game;
	public static PowerUp power;
	public static Update update;
	public static UpdatePlayer updatePlayer;
	public static Thread thread;
	public static GameLisitner gle;
	// public static EffectLib lib;
	// public static EffectManager man;
	boolean debug = false;
	public boolean mysql = false;
	public static ArrayList<String> vips = new ArrayList<String>();
	public static ArrayList<String> bannedplayers = new ArrayList<String>();
	public static HashMap<String, String> mods = new HashMap<String, String>();
	// public static Connect connect;
	// public static String server = "hub";
	// ConnectSettings settings;
	String username;
	String password;
	InetSocketAddress outboundAddress;
	int xxx = 1;
	public MySQL MySQL = new MySQL(this, "23.229.139.232", "3306", "Server",
			"ttPlugin", "DoubleTT!");
	public java.sql.Connection c = null;

	@Override
	public void onEnable() {
		System.out.print("[TT] Enabling Tornament Tower v." + version);
		tt = this;
		info = new Info();
		game = new Game();
		power = new PowerUp();
		update = new Update();
		updatePlayer = new UpdatePlayer();
		thread = new Thread();
		gle = new GameLisitner();
		// lib = EffectLib.instance();
		// man = new EffectManager(lib);
		/*
		 * this.connect = ((Connect) getServer().getServicesManager()
		 * .getRegistration(Connect.class).getProvider());
		 */
		// settings = connect.getSettings();
		// username = settings.getUsername();
		// password = settings.getPassword();
		// outboundAddress = settings.getOutboundAddress();
		try {
			updatePlayer.updateMods();
			// updatePlayer.updateVIPs();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		getServer().getPluginManager().registerEvents(new LobbyListiners(),
				this);
		getServer().getPluginManager().registerEvents(gle, this);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, thread, 20, 20);
		loadConfiguration();
		Random rand = new Random();
		// int map = rand.nextInt(3);
		int map = 0;
		System.out.println("Rand is " + map);
		if (map == 0) {
			info.world = "IceTemple";
			info.worldcreate = "SpinTown";
			System.out.println("Set to IceTemple");
		}
		if (map == 1) {
			info.world = "Splinterz";
			info.worldcreate = "8_BitHer0 & ChopChop237";
			System.out.println("Set to Splinterz");
		}
		if (map == 2) {
			info.world = "Villiage";
			info.worldcreate = "ChopChop27";
			System.out.println("Set to Villiage");
		}
		if (info.world.equals("") || info.worldcreate.equals("")) {
			info.world = "IceTemple";
			info.worldcreate = "SpinTown";
			System.out.println("Set to IceTemple");
		}
		startDebugcheck();
		System.out.print("[TT] Enabled Tornament Tower v." + version);
	}

	@Override
	public void onDisable() {
		System.out.print("[TT] Disabling Tornament Tower v." + version);
		Bukkit.getScheduler().cancelAllTasks();
		// man.disposeOnTermination();
		TT tt = null;
		Info info = null;
		Game game = null;
		PowerUp power = null;
		Update update = null;
		UpdatePlayer updatePlayer = null;
		Thread thread = null;
		GameLisitner gle = null;
		System.out.print("[TT] Disabled Tornament Tower v." + version);
	}

	private void loadConfiguration() {
		if (this.getConfig().get("Config.exsits") == null) {
			String[] list = { "120", "90", "60", "30", "15", "10", "9", "8",
					"7", "6", "5", "4", "3", "2", "1" };
			List ll = new List();
			this.getConfig().set("Config.exsits", true);
			this.getConfig().set("MinPlayers", 8);
			this.getConfig().set("MaxPlayers", 30);
			this.getConfig().set("PotionTime", 25);
			this.getConfig().set("LobbyTime", 120);
			this.getConfig().set("Worlds", "IceTemple");
			this.getConfig().set("Goodluck1",
					"%player% wishes all a good game and all a good night!");
			this.getConfig().set("Goodluck2",
					"%player% hopes all a good, fair, non-hack, unoped game!");
			this.getConfig()
					.set("Goodluck3",
							"%player% has given everyone an old Chinese Good Luck token!");
			this.getConfig().set("Goodluck4",
					"%player% hopes all a good, fair, non-hack, unoped game!");
			this.getConfig().set("Goodluck5",
					"%player% simply says: Good luck!");
			this.getConfig().set("Goodluck6",
					"%player%, the Good Luck, Wood Duck says: Good luck!");
			this.getConfig().set("Goodgame1",
					"%player% says good game, stay fresh'");
			this.getConfig()
					.set("Goodgame2",
							"%player% squashed 14 bugs while running around in the game! ");
			this.getConfig()
					.set("Goodgame3",
							"%player% thinks the minigame is so good he is going to burst with sparkes!");
			this.getConfig().set("Goodgame4",
					"%player% thinks Lego is amazing and made the game fair!");
			this.getConfig().set("Goodgame5",
					"%player% wants to play another game!");
			this.getConfig().set("Goodgame6",
					"%player%, wants to play another game with everyone!");
			this.getConfig().set("KillSwordMessage1",
					"%player% was slauged by the visious %killer%");
			this.getConfig()
					.set("KillSwordMessage2",
							"%player% was trying to kill Zack, but was interupted by %killer%");
			this.getConfig().set("KillSwordMessage3",
					"%player% was killed by THE %killer%");
			this.getConfig().set("BroadcastTime", list);
			this.getConfig().set("Spawn.level1.x.1", "");
			this.getConfig().set("Spawn.level1.y.1", "");
			this.getConfig().set("Spawn.level1.z.1", "");
			this.getConfig().set("Spawn.level1.world", "");
			this.getConfig().set("Spawn.level1.x.2", "");
			this.getConfig().set("Spawn.level1.y.2", "");
			this.getConfig().set("Spawn.level1.z.2", "");
			this.getConfig().set("Spawn.level1.x.3", "");
			this.getConfig().set("Spawn.level1.y.3", "");
			this.getConfig().set("Spawn.level1.z.3", "");
			this.getConfig().set("Spawn.level1.x.4", "");
			this.getConfig().set("Spawn.level1.y.4", "");
			this.getConfig().set("Spawn.level1.z.4", "");
			this.getConfig().set("Spawn.level1.x.5", "");
			this.getConfig().set("Spawn.level1.y.5", "");
			this.getConfig().set("Spawn.level1.z.5", "");
			this.getConfig().set("Spawn.level1.x.6", "");
			this.getConfig().set("Spawn.level1.y.6", "");
			this.getConfig().set("Spawn.level1.z.6", "");
			this.getConfig().set("Spawn.level1.x.7", "");
			this.getConfig().set("Spawn.level1.y.7", "");
			this.getConfig().set("Spawn.level1.z.7", "");
			this.getConfig().set("Spawn.level1.x.8", "");
			this.getConfig().set("Spawn.level1.y.8", "");
			this.getConfig().set("Spawn.level1.z.8", "");
			this.getConfig().set("Spawn.level2.x.1", "");
			this.getConfig().set("Spawn.level2.y.1", "");
			this.getConfig().set("Spawn.level2.z.1", "");
			this.getConfig().set("Spawn.level2.x.2", "");
			this.getConfig().set("Spawn.level2.y.2", "");
			this.getConfig().set("Spawn.level2.z.2", "");
			this.getConfig().set("Spawn.level2.x.3", "");
			this.getConfig().set("Spawn.level2.y.3", "");
			this.getConfig().set("Spawn.level2.z.3", "");
			this.getConfig().set("Spawn.level2.x.4", "");
			this.getConfig().set("Spawn.level2.y.4", "");
			this.getConfig().set("Spawn.level2.z.4", "");
			this.getConfig().set("Spawn.level2.x.5", "");
			this.getConfig().set("Spawn.level2.y.5", "");
			this.getConfig().set("Spawn.level2.z.5", "");
			this.getConfig().set("Spawn.level2.x.6", "");
			this.getConfig().set("Spawn.level2.y.6", "");
			this.getConfig().set("Spawn.level2.z.6", "");
			this.getConfig().set("Spawn.level2.x.7", "");
			this.getConfig().set("Spawn.level2.y.7", "");
			this.getConfig().set("Spawn.level2.z.7", "");
			this.getConfig().set("Spawn.level2.x.8", "");
			this.getConfig().set("Spawn.level2.y.8", "");
			this.getConfig().set("Spawn.level2.z.8", "");
			this.getConfig().set("Spawn.level2.world", "");
			this.getConfig().set("Spawn.level3.x.1", "");
			this.getConfig().set("Spawn.level3.y.1", "");
			this.getConfig().set("Spawn.level3.z.1", "");
			this.getConfig().set("Spawn.level3.x.2", "");
			this.getConfig().set("Spawn.level3.y.2", "");
			this.getConfig().set("Spawn.level3.z.2", "");
			this.getConfig().set("Spawn.level3.x.3", "");
			this.getConfig().set("Spawn.level3.y.3", "");
			this.getConfig().set("Spawn.level3.z.3", "");
			this.getConfig().set("Spawn.level3.x.4", "");
			this.getConfig().set("Spawn.level3.y.4", "");
			this.getConfig().set("Spawn.level3.z.4", "");
			this.getConfig().set("Spawn.level3.x.5", "");
			this.getConfig().set("Spawn.level3.y.5", "");
			this.getConfig().set("Spawn.level3.z.5", "");
			this.getConfig().set("Spawn.level3.x.6", "");
			this.getConfig().set("Spawn.level3.y.6", "");
			this.getConfig().set("Spawn.level3.z.6", "");
			this.getConfig().set("Spawn.level3.x.7", "");
			this.getConfig().set("Spawn.level3.y.7", "");
			this.getConfig().set("Spawn.level3.z.7", "");
			this.getConfig().set("Spawn.level3.x.8", "");
			this.getConfig().set("Spawn.level3.y.8", "");
			this.getConfig().set("Spawn.level3.z.8", "");
			this.getConfig().set("Spawn.level3.world", "");
			this.getConfig().set("Spawn.level4.x.1", "");
			this.getConfig().set("Spawn.level4.y.1", "");
			this.getConfig().set("Spawn.level4.z.1", "");
			this.getConfig().set("Spawn.level4.x.2", "");
			this.getConfig().set("Spawn.level4.y.2", "");
			this.getConfig().set("Spawn.level4.z.2", "");
			this.getConfig().set("Spawn.level4.x.3", "");
			this.getConfig().set("Spawn.level4.y.3", "");
			this.getConfig().set("Spawn.level4.z.3", "");
			this.getConfig().set("Spawn.level4.x.4", "");
			this.getConfig().set("Spawn.level4.y.4", "");
			this.getConfig().set("Spawn.level4.z.4", "");
			this.getConfig().set("Spawn.level4.x.5", "");
			this.getConfig().set("Spawn.level4.y.5", "");
			this.getConfig().set("Spawn.level4.z.5", "");
			this.getConfig().set("Spawn.level4.x.6", "");
			this.getConfig().set("Spawn.level4.y.6", "");
			this.getConfig().set("Spawn.level4.z.6", "");
			this.getConfig().set("Spawn.level4.x.7", "");
			this.getConfig().set("Spawn.level4.y.7", "");
			this.getConfig().set("Spawn.level4.z.7", "");
			this.getConfig().set("Spawn.level4.x.8", "");
			this.getConfig().set("Spawn.level4.y.8", "");
			this.getConfig().set("Spawn.level4.z.8", "");
			this.getConfig().set("Spawn.level4.world", "");
			this.getConfig().set("Spawn.level5.x", "");
			this.getConfig().set("Spawn.level5.y", "");
			this.getConfig().set("Spawn.level5.z", "");
			this.getConfig().set("Spawn.level5.world", "");
			this.getConfig().set("Spawn.lobby.x", "");
			this.getConfig().set("Spawn.lobby.y", "");
			this.getConfig().set("Spawn.lobby.z", "");
			this.getConfig().set("Spawn.lobby.world", "");
			this.getConfig().options().copyDefaults(true);
			this.saveConfig();
			this.reloadConfig();
		} else {
			this.saveConfig();
			this.reloadConfig();
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (label.equalsIgnoreCase("gl")) {
				if (info.isPronePlayer(p)) {
					int rand = game.getRandom(1, 6);
					String mess = getConfig().getString("Goodluck" + rand)
							.replace("%player%", p.getDisplayName());
					info.broadCast(ChatColor.AQUA + "" + mess);
					return true;
				}
				if (!info.cangl) {
					p.sendMessage(ChatColor.GREEN
							+ "You can not wish good luck until THE FINAL COUNTDOWN!");
					return true;
				}
				if (info.getPP(p).isGL()) {
					p.sendMessage(ChatColor.GREEN + "Silly "
							+ p.getDisplayName()
							+ "! You already wished everyone good luck!");
					return true;
				}
				int rand = game.getRandom(1, 6);
				String mess = getConfig().getString("Goodluck" + rand).replace(
						"%player%", p.getDisplayName());
				info.broadCast(ChatColor.AQUA + "" + mess);
				info.getPP(p).setGL();
				return true;
			}
			if (label.equalsIgnoreCase("spawn")) {
				if (info.getState() == ServerState.Pre_Game
						|| info.getState() == ServerState.Post_Game) {
					p.teleport(p.getWorld().getSpawnLocation());
					return true;
				}
			}

			if (label.equalsIgnoreCase("start")) {
				if (p.isOp() || p.hasPermission("tt.forcestart")) {
					p.sendMessage(ChatColor.GRAY + "Starting the game!");
					info.setState(ServerState.In_Game);
					info.setTime(0);
					game.start();
					return true;
				}
			}
			if (label.equalsIgnoreCase("stop")) {
				if (p.isOp() || p.hasPermission("tt.forceend")) {
					p.sendMessage(ChatColor.GRAY + "Stopping the game!");
					debugMsg("End player is " + (p != null));
					game.endGame(p);
					return true;
				}
			}
			if (label.equalsIgnoreCase("stats")) {
				p.sendMessage(ChatColor.GOLD + "===Tournament Tower:"
						+ ChatColor.WHITE + p.getDisplayName() + ChatColor.GOLD
						+ "===");
				/*
				 * try { p.sendMessage(ChatColor.AQUA + "Win Played Ratio: " +
				 * update.getWin(p) / update.getMatchFinish(p)); } catch
				 * (SQLException e) { p.sendMessage(ChatColor.AQUA +
				 * "Kill Death Ratio: 0.0"); e.printStackTrace(); }
				 */
			}
			if (label.equalsIgnoreCase("bane")) {
				if (!mods.containsKey(p.getDisplayName())) {
					p.sendMessage(ChatColor.RED
							+ "Please don't use Zach's commands without permission from Zack!");
					return true;
				}
				if (args.length > 2) {
					String playerName = args[0];
					String reason = "";
					for (int i = 1; i < args.length; i++)
						reason = reason + args[i] + " ";
					String formatted = "\n"
							+ playerName
							+ ":"
							+ Bukkit.getOfflinePlayer(playerName).getUniqueId()
									.toString()
							+ ":"
							+ ((sender instanceof ConsoleCommandSender) ? "Console"
									: sender.getName()) + ":" + reason;
					p.sendMessage(ChatColor.AQUA + "Banning...");
					FTPClient ftp = new FTPClient();
					ftp.setHost("pieman.zafcoding.com");
					ftp.setPassword("Gonat$");
					ftp.setUser("ttplugin");
					ftp.setRemoteFile("ban.txt");
					if (ftp.connect()) {
						if (ftp.downloadFile(new File(getDataFolder(),
								"banlist.txt").getPath()))
							System.out.println(ftp.getLastSuccessMessage());
						else
							System.out.println(ftp.getLastErrorMessage());
					} else
						System.out.println(ftp.getLastErrorMessage());

					try {
						PrintWriter out = new PrintWriter(new BufferedWriter(
								new FileWriter(new File(getDataFolder(),
										"banlist.txt"), true)));
						out.println(formatted);
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					if (Bukkit.getPlayer(playerName) != null) {
						Bukkit.getPlayer(playerName).kickPlayer(
								ChatColor.RED + "You Have Been "
										+ ChatColor.RED + ChatColor.BOLD
										+ ChatColor.UNDERLINE + "BANNED\n"
										+ ChatColor.YELLOW + "Reason: "
										+ ChatColor.DARK_AQUA + reason);
					}

					FTPClient ftp1 = new FTPClient();
					ftp1.setHost("pieman.zafcoding.com");
					ftp1.setPassword("Gonat$");
					ftp1.setUser("ttplugin");
					ftp1.setRemoteFile("ban.txt");
					if (ftp1.connect()) {
						if (ftp1.uploadFile(new File(getDataFolder(),
								"banlist.txt").getPath())) {
							System.out.println(ftp1.getLastSuccessMessage());
							p.sendMessage(ChatColor.AQUA + "It worked! (Prob)");
							System.out.println(ftp1.getLastErrorMessage());
						}
					} else {
						p.sendMessage(ChatColor.AQUA
								+ "It broke, go tell Zach.");
						p.sendMessage(ChatColor.AQUA + "("
								+ ftp.getLastErrorMessage() + ")");
					}
				} else {
					sender.sendMessage(ChatColor.RED
							+ "Correct usage: /bane [player] [reason]");
				}
			}
			if (label.equalsIgnoreCase("setspawn")) {
				if (p.hasPermission("tt.admin")) {
					if (args.length == 3) {
						if (args[1].equalsIgnoreCase("0")) {
							xxx = Integer.parseInt(args[2]);
							setLocation("", 0, xxx, p.getLocation());
							p.sendMessage(pre
									+ ChatColor.LIGHT_PURPLE
									+ " The Lobby Spawn has been set to your currecnt location!");
							return true;
						}
						int ii = Integer.parseInt(args[1]);
						int iui = Integer.parseInt(args[2]);
						setLocation(args[0], ii, iui, p.getLocation());
						p.sendMessage(pre + ChatColor.LIGHT_PURPLE + " Level "
								+ ii
								+ " has been set to your current location! ("
								+ iui + ")");
						return true;
					} else {
						p.sendMessage(ChatColor.RED
								+ "Correct usage: /setspawn <world> <level> <which>");
						return true;
					}
				} else {
					p.sendMessage("You don't got the permissions!");
					return true;
				}
			}

			if (label.equalsIgnoreCase("gg")) {
				if (info.isPronePlayer(p)) {
					int rand = game.getRandom(1, 6);
					String mess = getConfig().getString("Goodgame" + rand)
							.replace("%player%", p.getDisplayName());
					info.broadCast(ChatColor.LIGHT_PURPLE + "" + mess);
					return true;
				}
				if (!info.cangg) {
					p.sendMessage(ChatColor.GREEN
							+ "Do not make the game end yet! Wait till the end!");
					return true;
				}
				if (info.getPP(p).isGG()) {
					p.sendMessage(ChatColor.GREEN
							+ "We admit you are a good sport, but you do not need to brag!");
					return true;
				}
				int rand = game.getRandom(1, 6);
				String mess = getConfig().getString("Goodgame" + rand).replace(
						"%player%", p.getDisplayName());
				info.broadCast(ChatColor.LIGHT_PURPLE + "" + mess);
				info.getPP(p).setGG();
				return true;
			}
			if (label.equalsIgnoreCase("tt")) {
				if (args.length == 0) {
					if (p.hasPermission("tt.admin")) {
						p.sendMessage(pre + " Admin commands:");
						p.sendMessage(pre + " /tt force");
						p.sendMessage(pre
								+ " /tt setspawn <world> <level> <spawn #>"
								+ ChatColor.GRAY
								+ " (0 = Lobby, 1 = Level 1, 2 = Level 2, 3 = Level 3, 4 = Level 4, 5 = Level 5)");
						p.sendMessage(pre + " /tt prone");
						p.sendMessage(pre + " /tt playercount");
						p.sendMessage(pre + " /tt playerprofile");
						p.sendMessage(pre + " /tt resetkit");
						p.sendMessage(pre + " /tt setcount <integer>");
						p.sendMessage(pre + " /tt setkill <integer>");
						p.sendMessage(pre + " /tt setdeath <integer>");
						p.sendMessage(pre + " /tt finish");
						return true;
					}
				}
				if (args.length == 1) {
					if (p.hasPermission("tt.admin")) {
						if (args[0].equalsIgnoreCase("playercount")) {
							p.sendMessage(pre + " There are currently "
									+ info.getPlayerCount() + " out of "
									+ getMaxPlayer() + " players online!");
							p.sendMessage(pre + ChatColor.GRAY + " ("
									+ info.getPlayers() + ")");
							return true;
						}
					}
					if (p.hasPermission("tt.admin")) {
						if (args[0].equalsIgnoreCase("help")) {
							p.sendMessage(pre + " Admin commands:");
							p.sendMessage(pre
									+ " /tt setspawn <world> <level> <spawn #>"
									+ ChatColor.GRAY
									+ " (0 = Lobby, 1 = Level 1, 2 = Level 2, 3 = Level 3, 4 = Level 4, 5 = Level 5)");
							p.sendMessage(pre + " /tt prone");
							p.sendMessage(pre + " /tt playercount");
							p.sendMessage(pre + " /tt playerprofile <player>");
							p.sendMessage(pre + " /tt resetkit");
							p.sendMessage(pre + " /tt setcount <integer>");
							p.sendMessage(pre + " /tt setkill <integer>");
							p.sendMessage(pre + " /tt setdeath <integer>");
							p.sendMessage(pre + " /start");
							p.sendMessage(pre + " /stop");
							return true;
						}
						if (args[0].equalsIgnoreCase("force")) {
							if (p.isOp() || p.hasPermission("tt.forcestart")) {
								p.sendMessage(ChatColor.GRAY
										+ "Starting the game!");
								info.setState(ServerState.In_Game);
								info.setTime(0);
								game.start();
								return true;
							}
						}
						if (args[0].equalsIgnoreCase("skip")) {
							if (p.hasPermission("tt.forceskip")) {
								Random rand = new Random();
								int map = rand.nextInt(3);
								System.out.println("Rand is " + map);
								if (map == 0) {
									info.world = "IceTemple";
									info.worldcreate = "SpinTown";
									System.out.println("Set to IceTemple");
								}
								if (map == 1) {
									info.world = "Splinterz";
									info.worldcreate = "8_BitHer0 & ChopChop237";
									System.out.println("Set to Splinterz");
								}
								if (map >= 2) {
									info.world = "Villiage";
									info.worldcreate = "ChopChop27";
									System.out.println("Set to Villiage");
								}
								if (info.world.equals("")
										|| info.worldcreate.equals("")) {
									info.world = "IceTemple";
									info.worldcreate = "SpinTown";
									System.out.println("Set to IceTemple");
								}
								p.sendMessage(ChatColor.GRAY
										+ "Map has been set to " + info.world
										+ " (Made by " + info.worldcreate + ")");
								return true;
							}
						}
						if (args[0].equalsIgnoreCase("finish")) {
							if (p.isOp() || p.hasPermission("tt.forceend")) {
								p.sendMessage(ChatColor.GRAY
										+ "Stopping the game!");
								debugMsg("End player is " + (p != null));
								game.endGame(p);
								return true;
							}
						}
						if (args[0].equalsIgnoreCase("prone")) {
							if (p.isOp() || p.hasPermission("tt.prone")) {
								if (info.isPronePlayer(p)) {
									info.removePronePlayer(p);
									p.sendMessage(pre + " Prone mode has been "
											+ ChatColor.RED + "disabled!");
									return true;
								} else {
									info.addPronePlayer(p);
									p.sendMessage(pre + " Prone mode has been "
											+ ChatColor.GREEN + "enabled!!");
									return true;
								}
							} else {
								p.sendMessage("Do not got the perms!");
							}
						}
						if (args[0].equalsIgnoreCase("playerprofile")) {
							PlayerProfile pp = info.getPP(p);
							p.sendMessage("======PlayerProfile:"
									+ p.getDisplayName() + "======");
							p.sendMessage(pre + " Level: " + ChatColor.GRAY
									+ pp.getLevel());
							p.sendMessage(pre + " Kills: " + ChatColor.GRAY
									+ pp.getKills());
							p.sendMessage(pre + " Deaths: " + ChatColor.GRAY
									+ pp.getDeaths());
							p.sendMessage(pre + " Total Kills: "
									+ ChatColor.GRAY + pp.getTotalKill());
							p.sendMessage(pre + " Total Deaths: "
									+ ChatColor.GRAY + pp.getTotalDeaths());
							return true;
						}
						if (args[0].equalsIgnoreCase("reload")) {
							if (p.isOp() || p.hasPermission("tt.reload")) {
								p.sendMessage(ChatColor.GRAY
										+ "Reloading the game!");
								safeReload();
								return true;
							}
						}
						if (args[0].equalsIgnoreCase("resetkit")) {
							p.getInventory().clear();
							int i = game.getRandom(1, 2);
							if (i == 1) {
								p.getInventory().addItem(
										new ItemStack(Material.WOOD_SWORD));
							} else {
								p.getInventory().addItem(
										new ItemStack(Material.BOW));
								p.getInventory().addItem(
										new ItemStack(Material.ARROW, 64));
							}
							p.sendMessage(pre
									+ " Your kit has been reset! (The int was "
									+ i + ")");
							return true;
						}
					}
				}
				if (args.length == 2) {
					if (p.hasPermission("tt.admin")) {
						if (args[0].equalsIgnoreCase("playerprofile")) {
							if (Bukkit.getPlayer(args[1]) == null) {
								p.sendMessage(ChatColor.RED
										+ "Could not find player '" + args[1]
										+ "'");
								return true;
							}
							PlayerProfile pp = info.getPP(Bukkit
									.getPlayer(args[1]));
							p.sendMessage("======PlayerProfile:"
									+ p.getDisplayName() + "======");
							p.sendMessage(pre + " Level: " + ChatColor.GRAY
									+ pp.getLevel());
							p.sendMessage(pre + " Kills: " + ChatColor.GRAY
									+ pp.getKills());
							p.sendMessage(pre + " Deaths: " + ChatColor.GRAY
									+ pp.getDeaths());
							p.sendMessage(pre + " Total Kills: "
									+ ChatColor.GRAY + pp.getTotalKill());
							p.sendMessage(pre + " Total Deaths: "
									+ ChatColor.GRAY + pp.getTotalDeaths());
							return true;
						}
						if (args[0].equalsIgnoreCase("setcount")) {
							info.setCount(Integer.parseInt(args[1]));
							p.sendMessage(pre + ChatColor.LIGHT_PURPLE
									+ " The player count has been set to "
									+ info.getPlayerCount());
							return true;
						}
						if (args[0].equalsIgnoreCase("setkill")) {
							PlayerProfile pp = info.getPP(p);
							pp.setKills(Integer.parseInt(args[1]));
							pp.setTotalKill(pp.getTotalKill()
									+ Integer.parseInt(args[1]));
							p.sendMessage(pre + ChatColor.LIGHT_PURPLE
									+ " The kills for the player "
									+ pp.getPlayer().getDisplayName()
									+ " has been set to " + pp.getKills());
							game.killCheck(pp);
							return true;
						}
						if (args[0].equalsIgnoreCase("setdeath")) {
							PlayerProfile pp = info.getPP(p);
							pp.setDeath(Integer.parseInt(args[1]));
							pp.setTotalDeath(pp.getTotalDeaths()
									+ Integer.parseInt(args[1]));
							p.sendMessage(pre + ChatColor.LIGHT_PURPLE
									+ " The deaths for the player "
									+ pp.getPlayer().getDisplayName()
									+ " has been set to " + pp.getDeaths());
							return true;
						}
					}
				}
			}
			p.sendMessage(pre
					+ " The command you just did does not exist! Do /tt to see all commands!");
		} else {
			if (args[0].equalsIgnoreCase("ip")) {
				System.out.println("IP: " + Bukkit.getIp() + ":"
						+ Bukkit.getPort());
				return true;
			}
			if (args[0].equalsIgnoreCase("setstate")) {
				if (args.length == 1) {
					System.out
							.print("The state directory is: 0 = Pre_Game, 1 = In_Game, 2 = Post_Game, 3 = Resetting");
					return true;
				}

				if (args.length == 2) {
					int arg = Integer.parseInt(args[1]);
					if (arg == 0) {
						System.out.print("The server state has been set to "
								+ arg + " (" + ServerState.Pre_Game.toString()
								+ ")");
						info.setState(ServerState.Pre_Game);
						return true;
					}
					if (arg == 1) {
						System.out.print("The server state has been set to "
								+ arg + " (" + ServerState.In_Game.toString()
								+ ")");
						info.setState(ServerState.In_Game);
						return true;
					}
					if (arg == 2) {
						System.out.print("The server state has been set to "
								+ arg + " (" + ServerState.Post_Game.toString()
								+ ")");
						info.setState(ServerState.Post_Game);
						return true;
					}
					if (arg == 3) {
						System.out.print("The server state has been set to "
								+ arg + " (" + ServerState.Resetting.toString()
								+ ")");
						info.setState(ServerState.Resetting);
						return true;
					}
				}
			}
			if (args[0].equalsIgnoreCase("settime")) {
				if (args.length == 2) {
					info.lobbytime = Integer.parseInt(args[1]);
					return true;
				}
			}
			if (args[0].equalsIgnoreCase("power")) {
				Player po = Bukkit.getPlayer(args[1]);
				power.addPowerUp(po);
				return true;
			}
			System.out.print("The command you did cannot be found!");
		}
		return false;
	}

	public Location getSpawn(String world, Integer in, Integer which) {
		if (in == 0) {
			return new Location(Bukkit.getWorld(getConfig().getString(
					"Spawn.lobby.world")), getConfig().getInt("Spawn.lobby.x"),
					getConfig().getInt("Spawn.lobby.y"), getConfig().getInt(
							"Spawn.lobby.z"));
		}
		if (in == 1) {
			return new Location(Bukkit.getWorld("TTLobby"), getConfig().getInt(
					"Spawn." + world + ".level1.x." + which), getConfig()
					.getInt("Spawn." + world + ".level1.y." + which),
					getConfig().getInt("Spawn." + world + ".level1.z." + which));
		}
		if (in == 2) {
			return new Location(Bukkit.getWorld("TTLobby"), getConfig().getInt(
					"Spawn." + world + ".level2.x." + which), getConfig()
					.getInt("Spawn." + world + ".level2.y." + which),
					getConfig().getInt("Spawn." + world + ".level2.z." + which));
		}
		if (in == 3) {
			return new Location(Bukkit.getWorld("TTLobby"), getConfig().getInt(
					"Spawn." + world + ".level3.x." + which), getConfig()
					.getInt("Spawn." + world + ".level3.y." + which),
					getConfig().getInt("Spawn." + world + ".level3.z." + which));
		}
		if (in == 4) {
			return new Location(Bukkit.getWorld("TTLobby"), getConfig().getInt(
					"Spawn." + world + ".level4.x." + which), getConfig()
					.getInt("Spawn." + world + ".level4.y." + which),
					getConfig().getInt("Spawn." + world + ".level4.z." + which));
		}
		if (in == 5) {
			return new Location(Bukkit.getWorld("TTLobby"), getConfig().getInt(
					"Spawn." + world + ".level5.x"), getConfig().getInt(
					"Spawn." + world + ".level5.y"), getConfig().getInt(
					"Spawn." + world + ".level5.z"));
		}
		return null;
	}

	private void startDebugcheck() {
		if (tt.mods.containsKey("done")) {
			Bukkit.getServer().reload();
		}
	}

	public void setLocation(String world, Integer spawn, Integer which,
			Location loc) {
		if (spawn == 0) {
			getConfig().set("Spawn.lobby.world", loc.getWorld().getName());
			getConfig().set("Spawn.lobby.x", loc.getBlockX());
			getConfig().set("Spawn.lobby.y", loc.getBlockY());
			getConfig().set("Spawn.lobby.z", loc.getBlockZ());
			saveAll();
		}
		if (spawn == 1) {
			getConfig().set("Spawn." + world + ".level1.world",
					loc.getWorld().getName());
			getConfig().set("Spawn." + world + ".level1.x." + which,
					loc.getBlockX());
			getConfig().set("Spawn." + world + ".level1.y." + which,
					loc.getBlockY());
			getConfig().set("Spawn." + world + ".level1.z." + which,
					loc.getBlockZ());
			saveAll();
		}
		if (spawn == 2) {
			getConfig().set("Spawn." + world + ".level2.world",
					loc.getWorld().getName());
			getConfig().set("Spawn." + world + ".level2.x." + which,
					loc.getBlockX());
			getConfig().set("Spawn." + world + ".level2.y." + which,
					loc.getBlockY());
			getConfig().set("Spawn." + world + ".level2.z." + which,
					loc.getBlockZ());
			saveAll();
		}
		if (spawn == 3) {
			getConfig().set("Spawn." + world + ".level3.world",
					loc.getWorld().getName());
			getConfig().set("Spawn." + world + ".level3.x." + which,
					loc.getBlockX());
			getConfig().set("Spawn." + world + ".level3.y." + which,
					loc.getBlockY());
			getConfig().set("Spawn." + world + ".level3.z." + which,
					loc.getBlockZ());
			saveAll();
		}
		if (spawn == 4) {
			getConfig().set("Spawn." + world + ".level4.world",
					loc.getWorld().getName());
			getConfig().set("Spawn." + world + ".level4.x." + which,
					loc.getBlockX());
			getConfig().set("Spawn." + world + ".level4.y." + which,
					loc.getBlockY());
			getConfig().set("Spawn." + world + ".level4.z." + which,
					loc.getBlockZ());
			saveAll();
		}
		if (spawn == 5) {
			getConfig().set("Spawn." + world + ".level5.world",
					loc.getWorld().getName());
			getConfig().set("Spawn." + world + ".level5.x", loc.getBlockX());
			getConfig().set("Spawn." + world + ".level5.y", loc.getBlockY());
			getConfig().set("Spawn." + world + ".level5.z", loc.getBlockZ());
			saveAll();
		}
	}

	public void debugMsg(String message) {
		if (debug) {
			System.out.print("[DEBUG] " + message);
		}
	}

	public void saveAll() {
		saveConfig();
		reloadConfig();
	}

	public int getMinPlayer() {
		return getConfig().getInt("MinPlayers");
	}

	public int getLobbyTime() {
		return getConfig().getInt("LobbyTime");
	}

	public boolean isBroadCastTime(int i) {
		/*
		 * for (Object l : getConfig().getList("BroadcastTime")) { if (l
		 * instanceof Integer) { if (Integer.parseInt((String) l) == i) { return
		 * true; } } }
		 */
		if (i == 120 || i == 60 || i == 30 || i == 15 || i == 90 || i == 10
				|| i == 9 || i == 8 || i == 7 || i == 6 || i == 5 || i == 4
				|| i == 3 || i == 2 || i == 1) {
			return true;
		}
		return false;
	}

	public int getMaxPlayer() {
		return getConfig().getInt("MaxPlayers");
	}

	public int getPotionTime() {
		return getConfig().getInt("PotionTime") * 20;
	}

	public boolean isMySQL() {
		return mysql;
	}

	public void updateVIP() {

	}

	/*
	 * public static void sendPlayerToServer(String server, Player player) {
	 * redirectRequest(server, player); }
	 */

	/*
	 * @SuppressWarnings("unchecked") public static void redirectRequest(String
	 * server, final Player player) { try { connect.request(new
	 * RedirectRequest(server, player.getName())) .registerListener(new
	 * FutureResultListener() {
	 * 
	 * @Override public void onResult(Result e) { if (e.getStatusCode() ==
	 * StatusCode.SUCCESS) { player.sendMessage("*woosh*"); return; } else {
	 * player.kickPlayer(ChatColor.RED +
	 * "The server is restarting... join again in a minute!"); } } }); } catch
	 * (Exception exception) { player.kickPlayer(ChatColor.RED +
	 * "The server is restarting... join again in a minute!"); } }
	 */

	public void safeReload() {
		onDisable();
		onEnable();
		return;
		/*info.clear();
		thread.clear();
		game.clear();
		gle.clear();
		try {
			updatePlayer.updateMods();
			// updatePlayer.updateVIPs();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		loadConfiguration();
		Random rand = new Random();
		// int map = rand.nextInt(3);
		int map = 0;
		System.out.println("Rand is " + map);
		if (map == 0) {
			info.world = "IceTemple";
			info.worldcreate = "SpinTown";
			System.out.println("Set to IceTemple");
		}
		if (map == 1) {
			info.world = "Splinterz";
			info.worldcreate = "8_BitHer0 & ChopChop237";
			System.out.println("Set to Splinterz");
		}
		if (map == 2) {
			info.world = "Villiage";
			info.worldcreate = "ChopChop27";
			System.out.println("Set to Villiage");
		}
		if (info.world.equals("") || info.worldcreate.equals("")) {
			info.world = "IceTemple";
			info.worldcreate = "SpinTown";
			System.out.println("Set to IceTemple");
		}
		startDebugcheck();
		reloadConfig();*/
	}

}
