package com.hututu.game.skydiving;

public class Vector {
	float x,y,vx;
	int type;
	public Vector()
	{
		x =0;y=0;
	}
	public Vector(float _x,float _y)
	{
		x = _x;
		y = _y;
	}
	void set(float _x,float _y,int _type)
	{
		x = _x;
		y = _y;
		vx = 0;
		type =_type;
	}
	void set(float _y)
	{
		x = M.mRand.nextFloat();
		y = _y;
		vx= -.03f*M.mRand.nextFloat();
	}
	
}
