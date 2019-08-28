package com.hututu.game.clashofbattle;

public class Bulltet {
	
	int img;
	int angle = 0;
	
	float x, y;
	float vx, vy;


	void set(float _x, float _y, float _ang) {
		x = _x;
		y = _y;
		vx = (float) Math.sin(_ang) * .07f;
		vy = -(float) Math.cos(_ang) * .08f;
		angle = (int) Math.toDegrees(_ang) + 90;
	}

	void set(float _x, float _y,float _vx, float _vy, int _ang,int _img) {
		float n = M.mRand.nextFloat();
		x = _x+_vx*n;
		y = _y+_vy*n;
		vx = _vx*1.5f;
		vy = _vy*1.5f;
		angle = _ang;
		img = _img;
	}
	
	boolean update() {
		if (x < 1.0f && x > -1 && y < 1.5f && y > -.4) {
			x+=vx;
			y+=vy;
			if (img == 2 || img == 3 || img == 10) {
				vy-=.03f;
				if(y<-.30){
					vy = .01f;
					vx = .05f;
				}
			}
			return true;
		}
		return false;
	}

	boolean IsIn() {
		if (x < 1.0f && x > -1 && y < 1.5f && y > -.4) {
			return true;
		}
		return false;
	}
}
