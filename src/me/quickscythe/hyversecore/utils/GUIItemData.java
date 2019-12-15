package me.quickscythe.hyversecore.utils;

import org.bukkit.Material;

import net.minecraft.server.v1_15_R1.World;

public class GUIItemData {
	
	Material item;
	String name;
	int id;
	
	public GUIItemData(Material item, String name, int id) {
		this.item = item;
		this.name = name;
		this.id = id;
		
	}

}
