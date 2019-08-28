package com.Oneday.games24.peguinadventure;

public class ObjectF {
	float x,y;
	float vx;
	void set(float _x,float _y,float _vx)
	{
		x = _x;
		y = _y;
		vx=_vx;
	}
	void update(float vy)
	{
		y+=vy;
		x+=vx;
		if(x>1.5)
			x = -1.5f;
		if(x<-1.5f)
			x = 1.5f;
	}
}
