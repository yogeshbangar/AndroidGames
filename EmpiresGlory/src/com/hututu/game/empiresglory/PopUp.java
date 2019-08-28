package com.hututu.game.empiresglory;

public class PopUp {
	float y = 1.3f;
	int pop = 0;
	void update(){
		if(y > .9f && pop == 1){
			y-=.1f;
			if(y < .9f)
				y =.9f;
		}
		if(y < 1.32f && pop == 2){
			y+=.1f;
			if(y > 1.32f)
				pop = 0;
		}
	}
}
