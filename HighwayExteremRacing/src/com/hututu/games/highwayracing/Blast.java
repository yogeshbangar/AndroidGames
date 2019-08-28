package com.hututu.games.highwayracing;

public class Blast {
	int img;
	float x,y,z,vy,vx;
	
	void set(float _x,float _y)
	{
		x = _x;
		y = _y;
		vy = (M.mRand.nextBoolean()?.0001f:-.0001f)*(M.mRand.nextInt(200)+100);
		vx = (M.mRand.nextBoolean()?.0001f:-.0001f)*(M.mRand.nextInt(200)+100);
		z =1;
		img =0;
	}
	void update()
	{
		img++;
		y+=vy;
		x+=vx;
		z-=.11f+Math.abs(vx);
	}
}
