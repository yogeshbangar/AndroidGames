package com.httgames.amazingfreerunner;


public class Block {
	boolean isUp = false;
	
	float x,y;
	float sx;
	
	void setSx(){
		sx = C.mRand.nextInt(120)*.01f + .5f;
	}
	void set(float _x) {
		x = _x;
		y = (float)(C.mRand.nextInt(70)*.01-.35);
		isUp = true;
	}
	void start(float _x) {
		x = _x;
		y = 0;
		isUp = false;
		sx =0.63f;
	}
}
