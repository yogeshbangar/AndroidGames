package com.hututu.game.penaltyshootout;

public class Target {
	float x,y;
	int m;
	boolean isMoving = false; // Change 4/12 
	float vx,vy;// Change 4/12
	void set(float x,float y,int m)
	{
		this.x = x;
		this.y = y;
		this.m = m;
		isMoving = M.mRand.nextBoolean();// Change 4/12
		vx=.001f;// Change 4/12
		vy=.001f;// Change 4/12
		
	}
	// Change 4/12
	void update()
	{
		if(x > .3)
			vx = -.001f;
		if(x < -.3)
			vx = .001f;
		
		if(y > .8)
			vy = -.001f;
		if(y < .6)
			vy = .001f;
		
		x += vx;
		y += vy;
	}
	// Change 4/12
}
