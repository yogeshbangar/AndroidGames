package com.hututu.game.kingpenguin;

public class NineFrog {
	float x,y;
	float vx,vy;
	void set(float _x,float _y)
	{
		x = _x;
		y = _y;
	}
	void update()
	{
		x += vx;
		y += vy;
		vy-=.045f;
	}
}
