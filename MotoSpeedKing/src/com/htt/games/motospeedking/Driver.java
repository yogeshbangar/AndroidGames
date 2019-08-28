package com.htt.games.motospeedking;

public class Driver {
	float x,y;
	float vx;
	byte blast = -1;
	void set(float _x){
		x =_x;
		y =-.6f;
		vx =0;
		blast = -2;
	}
	void update(){
		if(vx > 0){
			x -=.09f;
			vx-=.09f;
			if(vx < 0){
				x-=vx;
				vx =0;
			}
		}
		if(vx < 0){
			x +=.09f;
			vx+=.09f;
			if(vx > 0){
				x-=vx;
				vx =0;
			}
		}
	}
}
