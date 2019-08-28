package com.hututu.game.flapycherrybird;

public class Animation {
	float x,y,vx,vy,z;
	float t;
	int No=0;
	GameRenderer mGR;
	public Animation(GameRenderer _mGR)
	{
		mGR  = _mGR;
		y= x = -100;
	}
	void set(float _x,float _y,float _vx,float _vy,int _no)
	{
		z  = 1f;
		x  = _x;
		y  = _y;
		t  = 1;
		vx = _vx;
		vy =_vy;
		No = _no;
	}
	void update()
	{
		x  +=vx;
		vy -=.0005043f;
		y  +=vy;
		z  -=.002f;
		if(z<.1f)
		{
		   x=y=-100;
		   vy=0;
		}
	}
}
