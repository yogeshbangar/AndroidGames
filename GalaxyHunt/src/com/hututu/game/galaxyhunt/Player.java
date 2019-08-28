package com.hututu.game.galaxyhunt;

public class Player {
	float x,y,vx,vy;
	int Power[] = new int[3];
	int ActivePower;
	int ActiveCounter =0;
	void set(float _x,float _y,float _vx,float _vy)
	{
		x = _x;
		y = _y;
		vx=_vx;
		vy=_vy;
		for(int i =0;i<Power.length;i++)
		{
			Power[i] =0;
		}
		ActivePower =0;
		ActiveCounter =0;
	}
	void update()
	{
		x+=vx;
		y+=vy;
	}
}
