package com.hututu.game.deathwell;

public class Crash {
	float x,y;
	float vx,vy;
	float z;
	byte img;
	void set(float _x,float _y,int _img){
		x = _x;
		y = _y;
		vx = ((M.mRand.nextInt(20)))*(M.mRand.nextBoolean()?-0.001f:.001f);
		vy = ((M.mRand.nextInt(20)))*(M.mRand.nextBoolean()?-0.001f:.001f);
		z =1;
		img = (byte)_img;
	}
	void reset(){
		x = -100;
		y = -100;
		vx = 0;
		vy = 0;
		img = 0;
	}

	boolean update() {
		if (x > -1 && x < 1 && y > -1 && y < 1 && z > 0) {
			x += vx;
			y += vy;
			z -= .05f;
			return true;
		}
		return false;
	}
}
