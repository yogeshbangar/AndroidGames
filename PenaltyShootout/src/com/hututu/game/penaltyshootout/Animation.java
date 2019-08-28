package com.hututu.game.penaltyshootout;

public class Animation {

	float x,y,vx,vy,scal;
	float r,g,b;
	public Animation()
	{
		x	= -100;
		y	= -100;
		vx	= 0;
		vy	= 0;
	}
	void set(float _x,float _y)
	{
		x	= _x;
		y	= _y;
		vx	= 0;
		vy	= 0;
		r = M.mRand.nextFloat();
		g = M.mRand.nextFloat();
		b = M.mRand.nextFloat();
		scal = 2.0f;
	}
	void update()
	{
		//x+=vx;
		//y+=vy;
		if(scal>.1f)
			scal-=.1f;
		else
			x=y=-100;
	}
}
