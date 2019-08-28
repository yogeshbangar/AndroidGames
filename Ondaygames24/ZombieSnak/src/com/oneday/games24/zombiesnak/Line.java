package com.oneday.games24.zombiesnak;

public class Line {
	float x,y;
	float r,g,b;
	boolean sound = false;
	void set(float _x){
		r = M.mRand.nextFloat();
		g = M.mRand.nextFloat();
		b = M.mRand.nextFloat();
		x =_x;
		y = (M.mRand.nextInt(70)-35)*.01f;
		sound = false;
	}
	
}
