package com.zafcoding.zachscott;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

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

}
