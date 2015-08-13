package com.zafcoding.zachscott;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Shop implements Listener {

	TT tt = TT.tt;

	/*public void oi(Player pl) {
		ShopProfile sp = tt.info.getPP(pl).sp;
		Inventory inv = Bukkit
				.createInventory(null, 18, ChatColor.RED + "Shop");
		List<String> sword = new ArrayList<String>();
		sword.add(ChatColor.LIGHT_PURPLE
				+ "Start with a stone sword each game!");
		if (!sp.stonesword) {
			sword.add(ChatColor.GREEN + "100 tokens");
		} else {
			sword.add(ChatColor.RED + "Active");
		}
		setName(inv, Material.STONE_SWORD, ChatColor.DARK_RED + "Stone Sword",
				sword, 1);
	}*/
	
	public void oi(Player player){
		
	}

	private void setName(Inventory inv, Material ma, String name,
			List<String> lore, int slot) {
		ItemStack is = new ItemStack(ma);
		ItemMeta m = is.getItemMeta();
		if (name != null)
			m.setDisplayName(name);
		if (lore != null)
			m.setLore(lore);
		is.setItemMeta(m);
		inv.setItem(slot, is);
	}

	@EventHandler
	public void onPlayerClick(InventoryClickEvent e) {
		if (ChatColor.stripColor(e.getInventory().getName()).contains("Shop")) {

		}
	}

}
