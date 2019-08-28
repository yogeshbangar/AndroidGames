package com.hututu.game.penaltyshootout;

public class Point {
	float x,y,vy;
	int i;
	void set(float _x,float _y){
		x = _x;
		y = _y;
		vy =.001f;
		i = M.mRand.nextInt(7);
	}
	void update()
	{
		y+=vy;
		vy+=.001f;
	}
	
}
