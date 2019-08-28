package com.hututu.game.reversegravity;

public class Object {
	float x,y;
	float vx;
	void set(float _x,float _y,float _vx)
	{
		x = _x;
		y = _y;
		vx=_vx;
	}
	void update()
	{
		x+=vx;
		y+=M.BGSPEED;
		if(x>1.5)
			x = -1.5f;
		if(x<-1.5f)
			x = 1.5f;
	}
}
