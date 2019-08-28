package com.Oneday.games24.peguinadventure;

public class Animation {
	int i =0;
	float x,y,z;
	float t;
	float vx,vy;
	public Animation()
	{
		y=x = -100;
		z =0;
		i =100;
	}
	void update()
	{
		i++;
	}
	void set(float _x,float _y)
	{
		x = _x;
		y = _y;
		i = 0;
	}
	void setEffect(float _x,float _y)
	{
		x = _x;
		y = _y;
		i = 0;
		z = 1f;
		t = 1;
		vx = (M.mRand.nextBoolean()?.03f:-.03f)*M.mRand.nextFloat();
		vy = (M.mRand.nextBoolean()?.04f:-.04f)*M.mRand.nextFloat();
	}
	void updateEffect()
	{
		x +=vx;
		y +=vy;
		if(z>0.1)
			z-=.08f; 
		t-=.04f;
		i++;
	}
	
}
