package com.hututu.game.reversegravity;

public class Point {
	float x,y;
	float vy;
	int mPoint = 0;
	void set(float _x,float _y,float _vy,int Point)
	{
		x = _x;
		y = _y;
		vy=_vy;
		mPoint = Point;
	}
	void update()
	{
		y+=vy;
		vy+=.001f;
	}
}
