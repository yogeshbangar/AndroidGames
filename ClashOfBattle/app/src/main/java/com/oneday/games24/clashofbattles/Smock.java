package com.oneday.games24.clashofbattles;

public class Smock {
	float x,y,z;
	void set(float _x,float _y){
		x = _x;
		y =_y;
		z = 1;
	}
	boolean IsIn() {
		if (x < 1 && x > -1 && y < 1 && y > -1 && z > 0) {
			return true;
		}
		return false;
	}
}
