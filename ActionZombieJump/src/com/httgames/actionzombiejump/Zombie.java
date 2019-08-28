package com.httgames.actionzombiejump;

public class Zombie {
	int gOver = 0; 
	int jump =0;
	
	float x,y;
	float vy;
	
	void set(){
		x = -.65f;
		y = -.35f;
		vy = 0;
		gOver = 0;
	}
	void Jump(){
		vy = .1f;
		jump =1;
	}
	void Update(){
		y+=vy;
		vy-=.016f;
		if(y < -.35f && vy < 0){
			y = -.35f;
			vy=0;
			jump = 0;
		}
	}
}
