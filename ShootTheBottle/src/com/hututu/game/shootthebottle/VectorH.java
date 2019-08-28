package com.hututu.game.shootthebottle;

public class VectorH {
	float x,y,vx,vy;
	
	void set(float _x,float _y,float _vx,float _vy)
	{
		x = _x;
		y = _y;
		vx=_vx;
		vy=_vy;
	}
	void update()
	{
		x+=vx;
		y+=vy;
	}

}
