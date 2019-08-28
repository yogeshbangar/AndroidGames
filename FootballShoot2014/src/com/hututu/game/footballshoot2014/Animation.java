package com.hututu.game.footballshoot2014;

public class Animation {
	int no;
	float x,y;
	
	void reset()
	{
		x =-100;
		y=-100;
		no=-1;
	}
	void set(float _x,float _y)
	{
		x =_x;
		y=_y;
		no=20;
	}
	void set(float _x,float _y,int _no)
	{
		x =_x;
		y=_y;
		no=_no;
	}
}
