package com.zafcoding.zachscott.tt.lobby;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.zafcoding.zachscott.Info;
import com.zafcoding.zachscott.TT;

public class Shop {

	TT tt = TT.tt;
	Info info = TT.info;
	//Items to buy: Start with better sword; More arrows;  

	public void openShop(Player player) {
		Inventory inv = Bukkit.createInventory(null, 18, ChatColor.GOLD
				+ "Shop");
		//TODO: Add method for naming items
		inv.addItem(new ItemStack(Material.STONE_SWORD));
		inv.addItem(new ItemStack(Material.ARROW));
		player.openInventory(inv);
	}

}
