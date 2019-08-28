package com.htt.game.monkeyeatbananas;

public class Banana {
	float y;
	int img = 0;
	boolean show = true;
	void set(float _y){
		y =_y;
		img =M.mRand.nextInt(4);
		show = true;
	}
}
