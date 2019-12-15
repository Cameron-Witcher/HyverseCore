package me.quickscythe.hyversecore.utils.pets;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import me.quickscythe.hyversecore.Main;
import me.quickscythe.hyversecore.utils.CoreUtils;
import net.minecraft.server.v1_15_R1.EntityArmorStand;
import net.minecraft.server.v1_15_R1.World;

public class Pet extends EntityArmorStand {

	double speed = 2;
	double dx = 0;
	double dz = 0;

	double dx2 = 0;
	double dz2 = 0;

	double speedmod = 0.075;

	double distance = 0;

	double dy = 0;
	double dd = 0;
	double p = 0;
	double pp = 0;
	UUID owner = null;
	PetType type = null;

	public Pet(World world) {
		super(world, 0.0, 0.0, 0.0);

		go(0, 0);
	}

	public void setType(PetType type) {
		this.type = type;
	}

	public void turnOnVehicle() {
		go(0, 0);
	}

	@SuppressWarnings("deprecation")
	public void go(float sideMot, float forMot) {

//		setMot(0, 0, 0.1);

//		
//		
//		
		if (type == null) {
			Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new MovePet(this, sideMot, forMot), 1);
			return;
		}

		if (owner == null) {
			for (org.bukkit.entity.Entity e : getBukkitEntity().getNearbyEntities(5, 5, 5)) {
				if (e instanceof Player) {
					setOwner(((Player) e).getUniqueId());
					Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new MovePet(this, sideMot, forMot), 1);
					return;
				}
			}
			Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new MovePet(this, sideMot, forMot), 1);
			return;
		}
		if (Bukkit.getPlayer(owner) == null) {
			Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new MovePet(this, sideMot, forMot), 1);
			return;
		}
		distance = Math.sqrt(Math.pow(Bukkit.getPlayer(owner).getLocation().getX() - locX(), 2)
				+ Math.pow(Bukkit.getPlayer(owner).getLocation().getZ() - locX(), 2));

		if (distance > 4) {

		}
//		
//		forMot = 0.8F;
//	    
////	    
////	    
////
////	    
//////	    SOHCAHTOA
//////	    
//////	       /|
//////	   H  / |
//////	     /  | O
//////	    /___|
//////	      A
////	    

		// sin& = o/h
////	    
////	    tan& = o/a
////		& = atan(o/a)
////	    
////		
////		
////		tan& = o/a
////		
////		a(tan&) = o
////		    
////		            
////		1/(tan&) = a/o               
////		
////		o/(tan&) = a
////		
//////	    
//////	    
//////	    
//////	    
//////	    
////	    
//////	    forMot = (float) Math.sqrt(Math.pow(passenger.motX, 2) + Math.pow(passenger.motZ, 2));
////	    
////	    
////	    
////	    
////	    
////	 
////	    
////	       // Apply the speed
//	   
////	    
////	    I=(float) 1.0;
////	    
////	    CoreUtils.debug("\nDX:" + dx + "\nDZ:" + dz + "\nDX2:" + dx2 + "\nDZ2:" + dz2);
////		
////		
////		
////		sin = y/r
////		cos = x/r
////		tan = y/x
////		
////	    
////	    
//		
//	    

		dx = 0;
		dz = 0;
		this.yaw = (float) Math.toDegrees(Math.atan2(Bukkit.getPlayer(owner).getLocation().getZ() - locZ(),
				Bukkit.getPlayer(owner).getLocation().getX() - locX())) - 90;
		if ((new Location(getWorld().getWorld(), locX() + 1, locY(), locZ()).getBlock().getType() != Material.AIR)
				|| (new Location(getWorld().getWorld(), locX(), locY(), locZ() + 1).getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR)
				|| (new Location(getWorld().getWorld(), locX() - 1, locY(), locZ()).getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR)
				|| new Location(getWorld().getWorld(), locX(), locY(), locZ() - 1).getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR) {
			if (new Location(getWorld().getWorld(), locX(), locY() - 1, locZ()).getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR) {
				this.setLocation(locX(), locY()+1.1, locZ(), 0, 0);
			}
		}
		if (distance > 2) {

			dx = (distance * (Math.sin(yaw))) / 100;
			dz = -(distance * (Math.cos(yaw))) / 100;

			CoreUtils.debug("DX: " + dx);
			CoreUtils.debug("DZ: " + dz);

//			dx = (Bukkit.getPlayer(owner).getLocation().getX() - locX > 0) ? 0.1 : -0.1;
//			dy = (Bukkit.getPlayer(owner).getLocation().getZ() - locZ > 0) ? 0.1 : -0.1;

			setMot(dx, 0, dz);
		}

		if (dx != 0 && dz != 0) {
			if (!getEntity().getHelmet().equals(type.getMovingItem()))
				getEntity().setHelmet(type.getMovingItem());
		} else {
			if (!getEntity().getHelmet().equals(type.getIdleItem()))
				getEntity().setHelmet(type.getIdleItem());
		}
//	    
//		
//		
//		
//		
//		
//		if(distance > 30){
//			
//			setLocation(Bukkit.getPlayer(owner).getLocation().getX(), Bukkit.getPlayer(owner).getLocation().getY(), Bukkit.getPlayer(owner).getLocation().getZ(), 0, 0);
//			Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new MovePet(this, sideMot, forMot), 1);
//			return;
//		}
//		
//		
//		
//	    dx = -forMot * Math.cos(Math.toRadians(yaw-180))*(speed*speedmod);
//	    dz = -forMot * Math.sin(Math.toRadians(yaw-180))*(speed*speedmod);
//	    
//	    
//	    
////	    
////	    motX = dx;
////	    motZ = dz;
////	    
//	    if(distance < 2.5){
////			motX = 0;
////			motZ = 0;
//		}
////	    if(motX != 0 && motZ != 0){
////	    	if(!getEntity().getHelmet().equals(type.getMovingItem()))getEntity().setHelmet(type.getMovingItem());
////	    } else {
////	    	if(!getEntity().getHelmet().equals(type.getIdleItem()))getEntity().setHelmet(type.getIdleItem());
////	    }
// 
//	    
////	    
////	                 vvvvvvvvvvvvvvvvv,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,l
////	     From the cat /\
////	    
////	    
////	    
////	             
////	    motX = (dx*(speed*0.097)) + (dx2*(speed));
////	    motZ = (dz*(speed*0.097)) + (dx2*(speed));
////	    
//	    
//	    
//	   
//	    
//	    if(getBukkitEntity().getLocation().add(dx, 0, dz).getBlock().getType().isSolid() && !getBukkitEntity().getLocation().add(dx, 1, dz).getBlock().getType().isSolid()){
////	    	motY=0.4;
//	    }
//	    
//	    try{
		Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new MovePet(this, sideMot, forMot), 1);
//    	} catch (IllegalPluginAccessException ex){}
//	 
	}

	public void setOwner(UUID uid) {
		if (Bukkit.getPlayer(uid) != null) {
			String name = "";
			String owner = Bukkit.getPlayer(uid).getName();
			if (owner.endsWith("s"))
				name = owner + "' Pet";
			else
				name = owner + "'s Pet";

//			setCustomName(name);
//			setCustomNameVisible(true);

		}
		owner = uid;
	}

	public UUID getOwner() {
		return owner;
	}

	private ArmorStand getEntity() {
		return ((ArmorStand) getBukkitEntity());
	}

}