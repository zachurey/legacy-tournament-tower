package com.zafcoding.zachscott;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class UpdatePlayer {

	TT tt = TT.tt;

	public void updateVIPs() throws IOException {
		if (!new File(tt.getDataFolder(), "vips.txt").exists())
			new File(tt.getDataFolder(), "vips.txt").createNewFile();
		tt.vips.clear();
		URL website = new URL("http://www.zafcoding.com/pieman/vips.txt");
		ReadableByteChannel rbc = Channels.newChannel(website.openStream());

		FileOutputStream fos = new FileOutputStream(new File(
				tt.getDataFolder(), "vips.txt"));
		fos.getChannel().transferFrom(rbc, 0L, 16777216L);

		BufferedReader br = new BufferedReader(new FileReader(new File(
				tt.getDataFolder(), "vips.txt")));
		try {
			String line;
			while ((line = br.readLine()) != null) {
				tt.vips.add(line);
				System.out.println(line);
			}
		} finally {
			br.close();
		}
	}

	public void updateMods() throws IOException {
		if (!new File(tt.getDataFolder(), "mods.txt").exists())
			new File(tt.getDataFolder(), "mods.txt").createNewFile();
		URL website = new URL("http://www.zafcoding.com/pieman/mods.txt");
		ReadableByteChannel rbc = Channels.newChannel(website.openStream());

		FileOutputStream fos = new FileOutputStream(new File(
				tt.getDataFolder(), "mods.txt"));
		fos.getChannel().transferFrom(rbc, 0L, 16777216L);

		BufferedReader br = new BufferedReader(new FileReader(new File(
				tt.getDataFolder(), "mods.txt")));
		try {
			String line = br.readLine();
			while (line != null) {
				if (line != null) {
					tt.mods.put(line.split("~")[0].trim(),
							line.split("~")[1].trim());
					System.out.println("MOD:" + line.trim());
				}
				line = br.readLine();
			}
		} finally {
			br.close();
		}
	}

	/*
	 * public void updateScores(boolean wipe) throws IOException { FTPClient ftp
	 * = new FTPClient(); ftp.setHost("ftp.zafcoding.com");
	 * ftp.setPassword("Gonat%24"); ftp.setUser("ttplugin%40zafcoding.com");
	 * ftp.setRemoteFile("scores.txt"); if (ftp.connect()) { if
	 * (ftp.downloadFile(new File(tt.getDataFolder(), "scores.txt") .getPath()))
	 * System.out.println(ftp.getLastSuccessMessage()); else
	 * System.out.println(ftp.getLastErrorMessage()); } else
	 * System.out.println(ftp.getLastErrorMessage());
	 * 
	 * BufferedReader br = new BufferedReader(new FileReader(new File(
	 * tt.getDataFolder(), "scores.txt"))); try { if (wipe){
	 * tt.playerScores.clear(); } String line = br.readLine(); while (line !=
	 * null) { if (!tt.playerScores.containsKey(line.split(",")[0]))
	 * tt.playerScores.put(line.split(",")[0],
	 * Integer.valueOf(line.split(",")[1])); line = br.readLine(); } } finally {
	 * br.close(); } }
	 * 
	 * public void saveScores() throws IOException { for (Player p :
	 * Bukkit.getOnlinePlayers()) { if
	 * (tt.playerScores.containsKey(p.getName())) { removeLineFromFile( new
	 * File(tt.getDataFolder(), "scores.txt").getPath(), p.getName()); } } for
	 * (Player p : Bukkit.getOnlinePlayers()) { if
	 * (tt.playerScores.containsKey(p.getName())) { PrintWriter out = new
	 * PrintWriter(new BufferedWriter( new FileWriter(new
	 * File(tt.getDataFolder(), "scores.txt"), true))); out.println(p.getName()
	 * + "," + (((Integer) tt.tt.playerScores.get(p.getName())) .intValue() < 0
	 * ? 0 : ((Integer) tt.playerScores.get(p.getName())) .intValue()));
	 * out.close(); } } FTPClient ftp = new FTPClient();
	 * ftp.setHost("ftp.zafcoding.com"); ftp.setPassword("Gonat%24");
	 * ftp.setUser("ttplugin%40zafcoding.com"); ftp.setRemoteFile("scores.txt");
	 * if (ftp.connect()) { if (ftp.uploadFile(new File(tt.getDataFolder(),
	 * "scores.txt") .getPath()))
	 * System.out.println(ftp.getLastSuccessMessage()); else
	 * System.out.println(ftp.getLastErrorMessage()); } else
	 * System.out.println(ftp.getLastErrorMessage()); }
	 */

	public void removeLineFromFile(String file, String playerName) {
		try {
			File inFile = new File(file);

			if (!inFile.isFile()) {
				System.out.println("Parameter is not an existing file");
				return;
			}

			File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

			BufferedReader br = new BufferedReader(new FileReader(file));
			PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

			String line = null;

			while ((line = br.readLine()) != null) {
				if (!line.trim().contains(playerName + ",")) {
					pw.println(line);
					pw.flush();
				}
			}
			pw.close();
			br.close();

			if (!inFile.delete()) {
				System.out.println("Could not delete file");
				return;
			}

			if (!tempFile.renameTo(inFile))
				System.out.println("Could not rename file");
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * public void subtract(Player player) { int org =
	 * tt.playerScores.get(player.getName()); if (org != 0) {
	 * tt.playerScores.put(player.getName(), org - 1); } }
	 * 
	 * public void add(Player player) { int org =
	 * tt.playerScores.get(player.getName());
	 * tt.playerScores.put(player.getName(), org + 1); }
	 */

}
