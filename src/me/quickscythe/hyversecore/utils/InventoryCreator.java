package me.quickscythe.hyversecore.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
@SuppressWarnings("deprecation")
public class InventoryCreator {

	String name;
	Player holder;
	Inventory inv;
	List<ItemStack> items = new LinkedList<>();
	Map<Character, ItemStack> identifier = new LinkedHashMap<>();

	public InventoryCreator(String name, Player holder, int size) {
		this.name = name;
		this.holder = holder;
		inv = Bukkit.createInventory(holder, size, CoreUtils.colorize(name));
	}

	
	public void addItem(ItemStack item, String name, char identifier, List<String> lore, short value) {

		if (item != null && (item.getType() != Material.AIR)) {
			item.setDurability(value);
			ItemMeta im = item.getItemMeta();
			im.setDisplayName(CoreUtils.colorize(name));
			if (lore != null) {
				try{
					im.setLore(CoreUtils.colorizeStringList(lore));
				} catch(NullPointerException ex){
				}
			}
			item.setItemMeta(im);
		}
		this.identifier.put(identifier, item);
	}
	
	
	public void addItem(ItemStack item, String name, char identifier, String[] lore) {

		if (item != null && (item.getType() != Material.AIR)) {
			ItemMeta im = item.getItemMeta();
			im.setDisplayName(CoreUtils.colorize(name));
			if (lore != null) {
				try{
					im.setLore(CoreUtils.colorizeStringList(lore));
				} catch(NullPointerException ex){
				}
			}
			item.setItemMeta(im);
			Bukkit.getConsoleSender().sendMessage(identifier + " : " + item.getItemMeta().getDisplayName());
			
		}
		this.identifier.put(identifier, item);
	}
	
	

	public void addItem(ItemStack item, String name, char identifier, List<String> lore, short value, boolean showValues) {

		if (item != null && (item.getType() != Material.AIR)) {
			item.setDurability(value);
			ItemMeta im = item.getItemMeta();
			im.setDisplayName(CoreUtils.colorize(name));
			if (lore != null) {
				try{
					im.setLore(CoreUtils.colorizeStringList(lore));
				} catch(NullPointerException ex){
				}
			}
			if(!showValues){
				im.spigot().setUnbreakable(true);
				im.addItemFlags(ItemFlag.values());
			}
			
			item.setItemMeta(im);
			
			
		}
		this.identifier.put(identifier, item);
	}
	
	public void addItem(ItemStack item, String name, char identifier, String[] lore, short value, boolean showValues) {

		if (item != null && (item.getType() != Material.AIR)) {
			item.setDurability(value);
			ItemMeta im = item.getItemMeta();
			im.setDisplayName(CoreUtils.colorize(name));
			if (lore != null) {
				try{
					im.setLore(CoreUtils.colorizeStringList(lore));
				} catch(NullPointerException ex){
				}
			}
			if(!showValues){
				im.spigot().setUnbreakable(true);
				im.addItemFlags(ItemFlag.values());
			}
			
			item.setItemMeta(im);
			
			
		}
		this.identifier.put(identifier, item);
	}

	public void addItem(ItemStack item, String name, char identifier, List<String> lore, short value, boolean unbreakable, boolean showValues) {

		if (item != null && (item.getType() != Material.AIR)) {
			item.setDurability(value);
			ItemMeta im = item.getItemMeta();
			im.setDisplayName(CoreUtils.colorize(name));
			if (lore != null) {

				im.setLore(CoreUtils.colorizeStringList(lore));
			}
			im.spigot().setUnbreakable(unbreakable);
			if(!showValues) im.addItemFlags(ItemFlag.values());
			item.setItemMeta(im);
		}
		this.identifier.put(identifier, item);
	}
	public void addItem(ItemStack item, String name, char identifier, String[] lore, short value, boolean unbreakable, boolean showValues) {

		if (item != null && (item.getType() != Material.AIR)) {
			item.setDurability(value);
			ItemMeta im = item.getItemMeta();
			im.setDisplayName(CoreUtils.colorize(name));
			if (lore != null) {

				im.setLore(CoreUtils.colorizeStringList(lore));
			}
			im.spigot().setUnbreakable(unbreakable);
			if(!showValues) im.addItemFlags(ItemFlag.values());
			item.setItemMeta(im);
		}
		this.identifier.put(identifier, item);
	}


	
	public void editItem(char identifier, ItemStack item, String name, List<String> lore, short value) {

		if (item != null && (item.getType() != Material.AIR)) {
			item.setDurability(value);
			ItemMeta im = item.getItemMeta();
			im.setDisplayName(CoreUtils.colorize(name));
			if (lore != null) {
				im.setLore(CoreUtils.colorizeStringList(lore));
			}
			item.setItemMeta(im);

		}

		this.identifier.put(identifier, item);
	}
	public void editItem(char identifier, ItemStack item, String name, String[] lore, short value) {

		if (item != null && (item.getType() != Material.AIR)) {
			item.setDurability(value);
			ItemMeta im = item.getItemMeta();
			im.setDisplayName(CoreUtils.colorize(name));
			if (lore != null) {
				im.setLore(CoreUtils.colorizeStringList(lore));
			}
			item.setItemMeta(im);

		}

		this.identifier.put(identifier, item);
	}

	public void editItem(char identifier, String name) {
		ItemStack item = this.identifier.get(identifier);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(CoreUtils.colorize(name));
		item.setItemMeta(meta);
		this.identifier.put(identifier, item);
	}

	public void editItem(char identifier, ItemStack item) {
		ItemMeta meta = this.identifier.get(identifier).getItemMeta();
		item.setItemMeta(meta);
		this.identifier.put(identifier, item);
	}

	public void setConfiguration(char[] c) {
		for (Character ch : c)
			items.add(identifier.get(ch));
	}

	public Inventory getInventory() {
		int a = 0;
		for (ItemStack i : items) {
			
			inv.setItem(a, i);
			a = a + 1;
		}
		return inv;
	}

	public String getName() {
		return name;
	}

	public Player getHolder() {
		return holder;
	}

	public void setConfiguration(ArrayList<Character> ids) {
		for (Character ch : ids){
			items.add(identifier.get(ch));
			Bukkit.getConsoleSender().sendMessage(identifier.get(ch).getItemMeta().getDisplayName() + " : " + ch);
		}
	}

}