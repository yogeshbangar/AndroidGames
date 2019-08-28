package com.hututu.game.empiresglory;

public class Sparkal {
	float x, y;
	int count;
	void set(float _x,float _y,int _count){
		x = _x;
		y = _y;
		count = _count;
	}
	boolean isScrean() {
		if (count < 26)
			return true;
		return false;
	}

	boolean update() {
		if (count < 26) {
			count++;
			return true;
		}
		return false;
	}
}
