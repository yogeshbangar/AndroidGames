package com.hututu.games.monkeyjump;

public class Villain {
	float x,y;
	float vy;
	void set(float _x,float _y){
		x = _x;
		y = _y;
		vy = .02f;
	}
	boolean update(float speed){
		if(y > -1.6){
			y -=(speed+vy);
			return true;
		}
		return false;
	}
}
