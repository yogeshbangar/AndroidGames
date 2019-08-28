package com.hututu.game.skydiving;

public class Power {
	float x, y, vx;
	int power;
	int timer =0;
	public Power()
	{
		x =-100;
		y =-100;
		power = 0;
		vx = .03f;
		timer = 0;
	}
	void set(float _x, float _y, int _power,int t) {
		x = _x;
		y = _y;
		power = _power;
		timer = t;
	}
	void update()
	{
		x+=vx;
		y-=.015f;
		if(x>.8)
			vx =-.03f;
		if(x<-.8)
			vx =.03f;
	}
}
