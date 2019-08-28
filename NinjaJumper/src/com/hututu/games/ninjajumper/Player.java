package com.hututu.games.ninjajumper;

public class Player {
	int power = 0;
	int counter = 0;
	
	float x, y;
	float vx, vy;
	float x1[],y1[],z1[];
	
	public Player()
	{
		x1 = new float[14];
		y1 = new float[14];
		z1 = new float[14];
	}
	public void set() {
		x = 0;
		y = 0;
		vx = 0.01f;
		vy = 0.01f;
		power = 0;
		for(int i=0;i<x1.length;i++){
			z1[i] =1;x1[i] = y1[i] = -10;
		}
	}

	boolean update() {
		boolean val = false;
		if (y > -.46f) {
			x += vx * (power == 1 ? 1.5f : 3f);
			y += vy * (power == 1 ? 1.5f : 3f);
			if (x > .95) {
				vx = -.01f;
				val = true;
			}
			if (x < -.95) {
				vx = 0.01f;
				val = true;
			}
			vy -= .001f * (power == 1 ? 1 : 2f);
		}
		 
		if(power == 1)
		{
			x1[counter%x1.length] = x;
			y1[counter%y1.length] = y;
			z1[counter%z1.length] =1;
			for(int i=0;i<x1.length;i++){
				z1[i] -= .05f;
			}
			counter++;
		}
		
		return val;
	}
	void powerSet(){
		for(int i=0;i<x1.length;i++){
			z1[i] =1;x1[i] = y1[i] = -10;
		}
	}
}
