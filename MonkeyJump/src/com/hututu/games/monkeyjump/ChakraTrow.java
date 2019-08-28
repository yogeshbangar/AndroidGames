package com.hututu.games.monkeyjump;

public class ChakraTrow {
	boolean isTrow = false;
	float x,y;
	void set(float _x,float _y){
		x = _x;
		y = _y;
		isTrow = false;
	}
	boolean update(float speed){
		if(y > -1.6){
			y -=speed;
			return true;
		}
		return false;
	}
}
