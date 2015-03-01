package com.zafcoding.zachscott.tt.game;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.zafcoding.zachscott.Info;
import com.zafcoding.zachscott.PlayerProfile;
import com.zafcoding.zachscott.TT;

public class PowerUp {

	TT tt = TT.tt;
	Game game = TT.game;
	Info info = TT.info;

	public void addPowerUp(Player po) {
		PlayerProfile pp = info.getPP(po);
		if(info.isPronePlayer(pp.getPlayer())){
			pp.setPower(false);
			pp.setPowerTime(0);
		}
		if (pp.getPowerTime() == 60 || pp.getPowerTime() == 0) {
			if (info.isPronePlayer(pp.getPlayer())) {

			} else {
				pp.setPower(true);
				pp.enablePower(0);
			}
			int ii = game.getRandom(1, 14);
			if (ii == 1) {
				tt.debugMsg("(" + ii
						+ ") A power has been added to the player "
						+ po.getDisplayName().toString());
				po.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, tt
						.getPotionTime(), 1));
				po.sendMessage(ChatColor.LIGHT_PURPLE
						+ "You got a speed potion!");
			}
			if (ii == 2) {
				tt.debugMsg("(" + ii
						+ ") A power has been added to the player "
						+ po.getDisplayName().toString());
				po.addPotionEffect(new PotionEffect(
						PotionEffectType.DAMAGE_RESISTANCE, tt.getPotionTime(),
						1));
				po.sendMessage(ChatColor.LIGHT_PURPLE
						+ "You got a damage resistance potion!");
			}
			if (ii == 3) {
				tt.debugMsg("(" + ii
						+ ") A power has been added to the player "
						+ po.getDisplayName().toString());
				po.getInventory().addItem(new ItemStack(Material.ENDER_PEARL));
				po.sendMessage(ChatColor.LIGHT_PURPLE
						+ "You got an ender pearl!");
			}
			if (ii == 4) {
				tt.debugMsg("(" + ii
						+ ") A power has been added to the player "
						+ po.getDisplayName().toString());
				po.addPotionEffect(new PotionEffect(
						PotionEffectType.INCREASE_DAMAGE, tt.getPotionTime(), 1));
				po.sendMessage(ChatColor.LIGHT_PURPLE
						+ "You got a strengh potion!");
			}
			if (ii == 5) {
				tt.debugMsg("(" + ii
						+ ") A power has been added to the player "
						+ po.getDisplayName().toString());
				po.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
				po.sendMessage(ChatColor.LIGHT_PURPLE
						+ "You got an iron sword!");
			}
			if (ii == 6) {
				tt.debugMsg("(" + ii
						+ ") A power has been added to the player "
						+ po.getDisplayName().toString());
				ItemStack is = new ItemStack(Material.STONE_SWORD);
				is.addEnchantment(Enchantment.DAMAGE_ALL, 1);
				po.getInventory().addItem(is);
				po.sendMessage(ChatColor.LIGHT_PURPLE + "You got a new sword!");
			}
			if (ii == 7) {
				tt.debugMsg("(" + ii
						+ ") A power has been added to the player "
						+ po.getDisplayName().toString());
				po.getInventory().setChestplate(
						new ItemStack(Material.IRON_CHESTPLATE));
				po.sendMessage(ChatColor.LIGHT_PURPLE
						+ "You got an iron chestplate!");
			}
			if (ii == 8) {
				tt.debugMsg("(" + ii
						+ ") A power has been added to the player "
						+ po.getDisplayName().toString());
				po.getInventory().setLeggings(
						new ItemStack(Material.IRON_LEGGINGS));
				po.sendMessage(ChatColor.LIGHT_PURPLE
						+ "You got an iron legging!");
			}
			if (ii == 9) {
				tt.debugMsg("(" + ii
						+ ") A power has been added to the player "
						+ po.getDisplayName().toString());
				po.getInventory()
						.setHelmet(new ItemStack(Material.IRON_HELMET));
				po.sendMessage(ChatColor.LIGHT_PURPLE
						+ "You got an iron helment!");
			}
			if (ii == 10) {
				tt.debugMsg("(" + ii
						+ ") A power has been added to the player "
						+ po.getDisplayName().toString());
				po.addPotionEffect(new PotionEffect(
						PotionEffectType.REGENERATION, tt.getPotionTime(), 1));
				po.sendMessage(ChatColor.LIGHT_PURPLE
						+ "You got a regeneration potion!");
			}
			if (ii == 11) {
				tt.debugMsg("(" + ii
						+ ") A power has been added to the player "
						+ po.getDisplayName().toString());
				po.addPotionEffect(new PotionEffect(
						PotionEffectType.INVISIBILITY, tt.getPotionTime(), 1));
				po.sendMessage(ChatColor.LIGHT_PURPLE
						+ "You got an invisibility potion!");
			}
			if (ii == 12) {
				tt.debugMsg("(" + ii
						+ ") A power has been added to the player "
						+ po.getDisplayName().toString());
				ItemStack is = new ItemStack(Material.CAKE);
				is.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
				po.getInventory().addItem(is);
				po.sendMessage(ChatColor.LIGHT_PURPLE + "You got a lying cake!");
			}
			if (ii == 13) {
				tt.debugMsg("(" + ii
						+ ") A power has been added to the player "
						+ po.getDisplayName().toString());
				po.getInventory().addItem(new ItemStack(Material.ARROW, 20));
				po.sendMessage(ChatColor.LIGHT_PURPLE + "You got some arrows!");
			}
			if (ii == 14) {
				tt.debugMsg("(" + ii
						+ ") A power has been added to the player "
						+ po.getDisplayName().toString());
				ItemStack is = new ItemStack(Material.COOKIE);
				is.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
				is.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 1);
				po.getInventory().addItem(is);
				po.sendMessage(ChatColor.LIGHT_PURPLE
						+ "You got a slap cookie!");
			}

		} else {
			po.sendMessage(ChatColor.LIGHT_PURPLE + "You have to wait "
					+ (60 - info.getPP(po).getPowerTime())
					+ " seconds before getting another powerup!");
		}
	}
}
