package com.httg.game.cannonpingpong;

public class Pad {
	boolean isMove = false;
	boolean Colide = false;
	
	float x,y;
	float vx;
	
	void set(float _x){
		x =_x;
		y =-.62f;
		vx = -.05f;
		isMove = false;
		Colide = false;
	}
	void update(){
		if(isMove || x >.9)
			x += vx;
		if(x < -1.4){
			isMove =false;
		}
	}
}
