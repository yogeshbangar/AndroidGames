package com.oneday.games24.clashofbattles;

public class Background {
	float x,y;
	void set(float _x,float _y){
		x =_x;
		y = _y;
	}
	void update(float spd){
		x+=spd;
	}
}

