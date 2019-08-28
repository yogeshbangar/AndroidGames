package com.game2d.timberboy;

public class Player {
	boolean isRight;
	boolean cut = false;
	boolean tap = false;
	
	int hit =0;
	int OverCont =0;
	int BG =0;
	float fill = 3.0f;
	void set(){
		isRight = false;
		cut = false;
		tap = false;
		hit =0;
		fill = 3.0f;
		OverCont =-1;
		BG =M.mRand.nextInt(5);
//		BG++;
//		BG %=5;
//		System.out.println("!!!!!!!!!!!!!!!!!!!111    "+BG);
	}
}
