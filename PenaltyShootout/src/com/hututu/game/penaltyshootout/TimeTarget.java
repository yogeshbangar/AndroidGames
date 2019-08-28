package com.hututu.game.penaltyshootout;

public class TimeTarget {
	float x,y;
	float vx,vy;
	int no;
	boolean isCollide;
	int inc;
	void set(){
		x = (M.mRand.nextBoolean()?-1:1)*(M.mRand.nextFloat()%.2f);
		y = (M.mRand.nextFloat()%.2f)+.6f;
		no = M.mRand.nextInt(3);
		vx = .001f;
		vy =-.001f;
		isCollide = false;
	}
	void update()
	{
		if(isCollide){
			y += vy;
			vy += .001f;
		}else{
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
	}
}
