package com.hututu.games.monkeyjump;

public class Power {
	float x,y;

	void set(float _x, float _y) {
		x = _x;
		y = _y;
	}
	boolean update(float speed){
		if(y > -1.2f){
			y -=speed;
			return true;
		}
		return false;
	}
}
