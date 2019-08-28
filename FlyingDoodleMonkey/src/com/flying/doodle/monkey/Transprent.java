package com.flying.doodle.monkey;

public class Transprent {
	float x,y;
	void set(float _y){
		x = (M.mRand.nextInt(28)-14)*.05f;
		y = _y;
	}
}
