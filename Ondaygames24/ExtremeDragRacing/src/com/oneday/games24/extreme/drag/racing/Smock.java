package com.oneday.games24.extreme.drag.racing;

public class Smock {
	
	void setanimation(float _x,float _y,int i){
		x =_x;
		y =_y;
		z = .1f;
		t = 1;
		vx = 0.001f;
		vy = (M.mRand.nextBoolean()?0.001f:.001f)*(M.mRand.nextInt(10)+1);
	}
	float x,y,z,t;
	float vx = 0.1f;
	float vy = 0;
	public Smock()
	{
		x =-100;
		y=-100;
	}
	void update(){
		x -=vx;
		y +=vy;
		vy+=.0002f;
		t -=.03f;
		z +=.11f;
		vx+=.001f;
		if(t <=.0f){
			x = -1100;
			y = -1100;
		}
	}
}
