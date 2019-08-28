package com.Oneday.games24.peguinadventure;

public class Twelve {
	boolean woter;
	boolean coliition;
	byte candy;
	byte c;
	float x,y,vx,vy;
	void set(){
		x = -1.10f;
		y =  0.60f;
		vx = 0.012f + c*.001f;
		vy = 0.010f;
		vx *=1.8f;
		vy *=2;
		c = (byte)(M.mRand.nextInt(16));
		woter = false;
		
		coliition = false;
	}
	void update(){
		x += vx;
		y += vy;
		if(!coliition && !woter)
			vy-=.003f;
	}
	void woterset()
	{
		x = M.mRand.nextFloat() - .5f;
		y = 1.1f;
		vy =-.05f;
		woter = false;
	}
	void reset()
	{
		coliition = true;
		float xx = -M.world2screenX(x) +M.world2screenX(.74f);
		float yy = -M.world2screenX(y) +M.world2screenX(.48f);
		double ang = M.GetAngle(yy,xx);
		vx = (float)Math.sin(ang)*.08f;
		vy = (float)Math.cos(ang)*.08f;
	}
}
