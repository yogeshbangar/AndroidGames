package com.oneday.games24.extrememotoracer;

public class Vehicle {
	float ax,x,y,vx,vy;
	int no = 0;
	void set(float _x,float _y,float _vx,float _vy,int _no)
	{
		ax 	=_x;
		x	=_x;
		y	=_y;
		vx	=_vx;
		vy	=_vy;
		no =_no;
	}
	void update()
	{
		if(Math.abs(ax-x)>.2f)
		{
			ax = x;
			vx = 0;
		}
		x +=vx;
		y +=(vy-GameRenderer.mGR.mPlayer.vy);
	}
}
