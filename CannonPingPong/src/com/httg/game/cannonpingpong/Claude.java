package com.httg.game.cannonpingpong;

public class Claude {
	float x,y;
	void set(float _x){
		x = _x;
		y = M.mRand.nextInt(50)*.01f+.3f;
	}
}
