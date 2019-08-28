package com.hututu.game.galaxyhunt;

public class Bullete {
	float x,y,vx,vy;
	int color =0,mPower;
	void set(float _x,float _y,float _vx,float _vy,int Power)
	{
		x = _x;
		y = _y;
		vx=_vx;
		vy=_vy;
		color = M.mRand.nextInt(4);
		mPower = Power;
	}
	void update()
	{
		x+=vx;
		y+=vy;
	}
}
