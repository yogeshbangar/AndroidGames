package com.hututu.game.cricket.premier.league;

public class Gulianim {
	float x,y;
	float vx,vy;
	void set(float _x,float _y){
		x = _x;
		y =_y;
		vx = (M.mRand.nextBoolean()?.004f:-.004f)*M.mRand.nextInt(10);
		vy = (M.mRand.nextInt(5)+15)*.005f;
	}
	float update(){
		x+=vx;
		y+=vy;
		vy-=.01f;
		return x;
	}
}
