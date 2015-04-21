package com.zafcoding.zachscott;

import org.bukkit.entity.Player;

public class ShopProfile {

	TT tt = TT.tt;
	Player p;
	public int coin = 0;
	public boolean stonesword = false;
	public boolean redhat = false;
	public boolean orangehat = false;
	public boolean yellowhat = false;
	public boolean greenhat = false;
	public boolean bluehat = false;
	public boolean purplehat = false;
	public boolean blackhat = false;
	public boolean whitehat = false;
	public boolean greyhat = false;
	public boolean redact = false;
	public boolean orangeact = false;
	public boolean yellowact = false;
	public boolean greenact = false;
	public boolean blueact = false;
	public boolean purpleact = false;
	public boolean blackact = false;
	public boolean whiteact = false;
	public boolean greyact = false;

	public ShopProfile(String uuid) {
		if (tt.getConfig().getString("Player." + uuid + ".coin") == null) {
			tt.getConfig().set("Player." + uuid + ".coin", 0);
			tt.getConfig().set("Player." + uuid + ".stonesword.h", false);
			tt.getConfig().set("Player." + uuid + ".stonesword.a", false);
			tt.getConfig().set("Player." + uuid + ".redhat.h", false);
			tt.getConfig().set("Player." + uuid + ".redhat.a", false);
			tt.getConfig().set("Player." + uuid + ".orangehat.h", false);
			tt.getConfig().set("Player." + uuid + ".yellowhat.h", false);
			tt.getConfig().set("Player." + uuid + ".greenhat.h", false);
			tt.getConfig().set("Player." + uuid + ".bluehat.h", false);
			tt.getConfig().set("Player." + uuid + ".purplehat.h", false);
			tt.getConfig().set("Player." + uuid + ".blackhat.h", false);
			tt.getConfig().set("Player." + uuid + ".whitehat.h", false);
			tt.getConfig().set("Player." + uuid + ".greyhat.h", false);

			tt.getConfig().set("Player." + uuid + ".orangehat.a", false);
			tt.getConfig().set("Player." + uuid + ".yellowhat.a", false);
			tt.getConfig().set("Player." + uuid + ".greenhat.a", false);
			tt.getConfig().set("Player." + uuid + ".bluehat.a", false);
			tt.getConfig().set("Player." + uuid + ".purplehat.a", false);
			tt.getConfig().set("Player." + uuid + ".blackhat.a", false);
			tt.getConfig().set("Player." + uuid + ".whitehat.a", false);
			tt.getConfig().set("Player." + uuid + ".greyhat.a", false);
			tt.saveConfig();
			tt.reloadConfig();
		} else {
			coin = tt.getConfig().getInt("Player." + uuid + ".stonesword.h");
			stonesword = tt.getConfig().getBoolean(
					"Player." + uuid + ".stonesword");
			redhat = tt.getConfig().getBoolean("Player." + uuid + ".redhat.h");
			orangehat = tt.getConfig().getBoolean(
					"Player." + uuid + ".orangehat.h");
			yellowhat = tt.getConfig().getBoolean(
					"Player." + uuid + ".yellowhat.h");
			greenhat = tt.getConfig().getBoolean(
					"Player." + uuid + ".greenhat.h");
			bluehat = tt.getConfig()
					.getBoolean("Player." + uuid + ".bluehat.h");
			purplehat = tt.getConfig().getBoolean(
					"Player." + uuid + ".purplehat.h");
			blackhat = tt.getConfig().getBoolean(
					"Player." + uuid + ".blackhat.h");
			whitehat = tt.getConfig().getBoolean(
					"Player." + uuid + ".whitehat.h");
			greyhat = tt.getConfig()
					.getBoolean("Player." + uuid + ".greyhat.h");
		}
	}

	public void save() {
		tt.getConfig().set("Player." + p.getUniqueId().toString() + ".coin",
				coin);
		tt.getConfig().set(
				"Player." + p.getUniqueId().toString() + ".stonesword.h",
				stonesword);
		tt.getConfig().set(
				"Player." + p.getUniqueId().toString() + ".redhat.h", redhat);
		tt.getConfig().set(
				"Player." + p.getUniqueId().toString() + ".orangehat.h",
				orangehat);
		tt.getConfig().set(
				"Player." + p.getUniqueId().toString() + ".yellowhat.h",
				yellowhat);
		tt.getConfig().set(
				"Player." + p.getUniqueId().toString() + ".greenhat.h",
				greenhat);
		tt.getConfig().set(
				"Player." + p.getUniqueId().toString() + ".bluehat.h", bluehat);
		tt.getConfig().set(
				"Player." + p.getUniqueId().toString() + ".purplehat.h",
				purplehat);
		tt.getConfig().set(
				"Player." + p.getUniqueId().toString() + ".blackhat.h",
				blackhat);
		tt.getConfig().set(
				"Player." + p.getUniqueId().toString() + ".whitehat.h",
				whitehat);
		tt.getConfig().set(
				"Player." + p.getUniqueId().toString() + ".greyhat.h", greyhat);
		tt.saveConfig();
		tt.reloadConfig();
	}

}
