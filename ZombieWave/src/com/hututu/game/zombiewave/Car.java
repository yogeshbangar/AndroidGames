package com.hututu.game.zombiewave;

public class Car {
	boolean Engine[];
	boolean Gear[];
	boolean Wheels[];
	boolean Kit[];
	
//	WEEKEND40
	int sEngine = 0;
	int sGear = 0;
	int sWheel = 0;
	int sKit = 0;
	int sGun = 0;
	int Boost;
	int Petrol;
	public Car(){
		Engine = new boolean [3];
		Gear = new boolean[3];
		Wheels = new boolean[3];
		Kit = new boolean [2];
		Engine[0] = true;
		Gear[0] = true;
		Wheels[0] = true;
		sEngine = 0;
		sGear = 0;
		sWheel = 0;
		sKit = 0;
		sGun = 0;
		Boost =0;
		Petrol = 1;
	}
}
