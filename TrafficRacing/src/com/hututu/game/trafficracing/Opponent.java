package com.hututu.game.trafficracing;

public class Opponent {
	float x,y;
	float r,g,b,t;
	int no;
	void set(float _x,float _y)
	{
		x = _x;
		y = _y;
		r = M.mRand.nextFloat();
		g = M.mRand.nextFloat();
		b = M.mRand.nextFloat();
		no =  M.mRand.nextInt(10);
	}
	void update()
	{
		t-=.09f;
		x+=M.SPEED*.5f;
		if(t<.3f)
			x=-3f;
	}
	void cSet(Opponent obj)
	{
		x = obj.x;
		y = obj.y;
		r = obj.r;
		g = obj.g;
		b = obj.b;
		t = 1;
		no =  obj.no;
	}
}
