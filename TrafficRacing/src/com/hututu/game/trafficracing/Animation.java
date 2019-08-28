package com.hututu.game.trafficracing;

public class Animation {
	float x,y,z;
	float t;
	public Animation()
	{
		y=x = -100;
	}
	void update()
	{
		if(z<2.5)
			z+=.3f; 
		else
			x=y=-100;
		t-=.05f;
	}
	void set(float _x,float _y)
	{
		z = .1f;
		x = _x;
		y = _y;
		t = 1;
	}
}
