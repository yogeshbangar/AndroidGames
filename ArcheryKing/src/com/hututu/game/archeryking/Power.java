package com.hututu.game.archeryking;

public class Power {
	int Power,time,rPower,counter=1000;
	float x,y;
	
	void set(float _x,float _y)
	{
		this.x =_x;
		this.y =_y;
		rPower = M.mRand.nextInt(4);
		Power =0;
	}
	void update(){
		y-=.01f;
	}
}
