package com.htt.game.monkeyeatbananas;

public class Pratidwandi {
	int Obj = 0;
	float x,y;
	
	void set(float _x,float _y,float val){
		x = _x;
		y = _y;
		if(val <-12)
			Obj = M.mRand.nextInt(6)+6;
		else
			Obj = M.mRand.nextInt(10);
	}
}
