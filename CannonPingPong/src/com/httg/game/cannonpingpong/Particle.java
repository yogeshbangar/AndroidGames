package com.httg.game.cannonpingpong;

public class Particle {
	float x,y;
	float vx,vy;
	int rot;
	void set(float _x,float _y){
		x =_x;
		y =_y;
		vx = (M.mRand.nextBoolean()?1:-1)*(M.mRand.nextInt(50)*.001f+.01f);
		vy = (M.mRand.nextInt(50)*.002f+.05f);
		rot = M.mRand.nextInt(20) - 10;
	}
	boolean update(){
		if(x>-1 && x < 1 && y >-1 && y<1){
			x+=vx;
			y+=vy;
			vy-=.01f;
			return true;
		}
		return false;
	}
}
