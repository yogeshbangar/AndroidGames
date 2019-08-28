package com.hututu.game.parkingchamp;

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
		vx	= ((M.mRand.nextFloat()+.1f)*(M.mRand.nextBoolean()?-.03f:.03f));
		vy	= (M.mRand.nextFloat()+.1f)*.06f;
		r = M.mRand.nextFloat();
		g = M.mRand.nextFloat();
		b = M.mRand.nextFloat();
		scal = 2.0f;
	}
	void update()
	{
		x+=vx;
		y+=vy;
		vy-=.002f;
		if(scal>.1f)
			scal-=Math.abs(vx*2);
		else
			x=y=-100;
	}
}
