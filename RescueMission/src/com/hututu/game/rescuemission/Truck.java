package com.hututu.game.rescuemission;

public class Truck {
	float x,y;
	float vx;
	float r,g,b;
	int type = 0;
	void set(float _x,float _vx)
	{
		x =_x;
		y = -.7f;
		r = M.mRand.nextFloat();
		g = M.mRand.nextFloat();
		b = M.mRand.nextFloat();
		type = M.mRand.nextInt(3);
		if(type !=1)
			type = 0;
		vx=_vx;
	}
	void update()
	{
		x +=vx;
	}
}
