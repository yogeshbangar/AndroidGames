package com.hututu.game.trafficracing;

public class Bullet {
	float x,y;
	int co = -1;
	void set(float _x,float _y)
	{
		x =_x;
		y =_y;
		co = -1;
	}
	void setAni(float _x,float _y)
	{
		x =_x;
		y =_y;
		co = 0;
	}
}
