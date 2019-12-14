package me.quickscythe.hyversecore.utils.pets;

public class MovePet implements Runnable {

	
	float sideMot = 0;
	float forMot = 0;
	Pet v;
	
	public MovePet(Pet v, float sideMot, float forMot){
		this.sideMot = sideMot;
		this.forMot = forMot;
		this.v = v;
	}
	@Override
	public void run() {
		v.go(sideMot, forMot);
	}

}
