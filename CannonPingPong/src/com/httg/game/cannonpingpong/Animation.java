package com.httg.game.cannonpingpong;

public class Animation {
	float x,y;
	float sz;
	void set(float _x,float _y){
		x = _x;
		y = _y;
		sz =3;
	}
	boolean update(){
		if(sz > 0){
			sz -=.1f;
			return true;
		}
		return false;
	}
}
