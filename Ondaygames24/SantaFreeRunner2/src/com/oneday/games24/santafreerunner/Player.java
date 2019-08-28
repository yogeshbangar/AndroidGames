package com.oneday.games24.santafreerunner;

public class Player {
	float x,y,vy;
	int tap;
	int ani = 0;
	boolean OnAir =false;
	void set(float _x,float _y)
	{
		x = _x;
		y = _y;
		
	}
	void setZero()
	{
		tap =0;
		vy = 0;
		ani = 0;
		OnAir =false;
	}
}
