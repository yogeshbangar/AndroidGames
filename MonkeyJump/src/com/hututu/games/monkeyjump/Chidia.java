package com.hututu.games.monkeyjump;

public class Chidia {
	int counter =0;
	float x,y,vx;
	
	void set(float _x,float _y){
		x =_x;
		y =_y;
		
		if(x>0)
			vx = -1.5f;
		else
			vx = 1.5f;
		counter = 0;
	}
	boolean update(float speed){
		if(y > -1.6){
			if(y > .5f || counter>10){
				y -=speed;
				if(counter>8)
					x +=speed*vx;
			}
			else{
				counter++;
			}
			return true;
		}
		return false;
	}
}
