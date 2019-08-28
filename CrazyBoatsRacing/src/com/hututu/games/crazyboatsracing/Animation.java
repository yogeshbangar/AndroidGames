package com.hututu.games.crazyboatsracing;

public class Animation {
	float x,y;
	float vx,vy;
	float tran;
	void reset()
	{
		x =y =-10000;
		vx =vy =0;
		tran =1;
	}
	void set(float _x,float _y)
	{
		x =_x;
		y =_y;
		vx = (M.mRand.nextBoolean()?-1:1)*(M.mRand.nextInt(180)+20)/10000f;
		vy = (M.mRand.nextBoolean()?-1:1)*(M.mRand.nextInt(180)+20)/10000f;
		tran =1;
	}
	void update()
	{
		x += vx;
		y += vy;
		tran -=.05f;
		if(tran<.1)
			reset();
	}
}
