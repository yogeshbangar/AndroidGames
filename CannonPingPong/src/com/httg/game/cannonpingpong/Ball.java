package com.httg.game.cannonpingpong;

public class Ball {
	boolean hand = true;
	
	int shoot = -1;
	
	float x,y;
	float vx,vy;
	
	void set(float _x,float _y){
		x =_x;
		y =_y;
		vx = .017f;
		vy = .1f;
		shoot = -1;
		hand = true;
	}
	void update(){
		if(shoot >= 0){
			x += vx;
			y += vy;
			vy-=.005f;
		}
	}
	
	void setnew() {
		if (x > 0)
			vx = -M.mRand.nextInt(50) * .0002f;
		else
			vx = M.mRand.nextInt(50) * .0002f;
		vy = .08f + M.mRand.nextInt(9) * .005f;
	}
}
