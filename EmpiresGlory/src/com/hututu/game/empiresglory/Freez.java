package com.hututu.game.empiresglory;

public class Freez {
	float x,y;
	int count;
	void set(float _x,float _y,int cou){
		x =_x;
		y = _y;
		count =cou;
	}
	boolean isScreen(){
		if(count < 8)
			return true;
		return false;
	}
	boolean update(){
		if(count < 8){
			count++;
			return true;
		}
		return false;
	}
}
