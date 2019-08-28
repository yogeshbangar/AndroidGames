package com.hututu.game.archeryking;

public class Point {
	float x,y;
	float vy,t;
	int ID;
	public Point()
	{
		y = 100;
	}
	void set(float _x,float _y,int _ID)
	{
		x = _x;
		y = _y;
		ID=_ID;
		vy=.001f;
		t = 1;
	}
	void update()
	{
		y+=vy;
		vy+=.001f;
		t -= .03;
		if(t<0)
			y = 100;
	}
}
