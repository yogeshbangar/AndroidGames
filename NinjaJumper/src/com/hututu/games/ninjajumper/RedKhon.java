package com.hututu.games.ninjajumper;

public class RedKhon {
	float x,y;
	float vx,vy;
	void set(float _x,float _y){
		x =_x;
		y =_y;
		vx = (M.mRand.nextInt(100)-50)*.0001f;
		vy = (M.mRand.nextInt(100)-50)*.0001f;
	}
	void update()
	{
		x += vx;
		y += vy;
		vy -=.001f;
	}
}
