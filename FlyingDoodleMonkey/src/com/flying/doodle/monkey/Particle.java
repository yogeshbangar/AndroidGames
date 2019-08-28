package com.flying.doodle.monkey;

public class Particle {
	float x,y;
	float sx;
	void set(float _x,float _y){
		x = _x;
		y = _y;
		sx = 1;
	}
	void update(){
		sx -=.013f;
	}
}
