package com.Oneday.games24.peguinadventure;

public class Fruit {
	int n;
	float x,y;
	float vy,vx;
	void set(float _x,float _y)
	{
		x = _x;
		y = _y;
		n = M.mRand.nextInt(5);
		vy = 0;
		vx =-.07f;
	}
	void update()
	{
		x+=vx;
		y+=vy;
	}
}
