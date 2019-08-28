package com.oneday.games24.shadowninjas;

public class Opponent {
	float x,y;
	float ay;
	float vy;
	int act = 0;
	int hit = 0;
	int count;
	void set(float _x,float _y,int _act){
		x = _x;
		y = _y;
		ay = y;
		if(_act==2){
			y = _y-.008f;
			ay = y-.008f;
		}
		act = _act;
		hit = 0;
		count =0;
	}

	void update(int c) {
		if (x < -1.5)
			return;
		if ((hit == 1 && count < 7) || (act == 2 && M.spd !=0)) {
			if (c % 3 == 0)
				count++;
		}
		if (hit == 2 && count < 5 && vy<0) {
			if (c % 3 == 0)
				count++;
		}
		if (x > -1.5)
			x += M.spd;
		if (x < -.25f && hit == 0 && act ==0) {
			hit = 1;
		}
		if (act == 0 && x < -.8) {
			M.spd = -.003f;
			vy = .1f;
			hit = 2;
			act = 1;
			count = 0;
		}
		if (act == 1) {
			if(GameRenderer.mStart.mGR.mPlyer.act <4)
				x += .005f;
			y += vy;
			vy -= .01f;
			if (y < ay){
				y = ay;
				vy = 0;
				GameRenderer.mStart.mGR.mPlyer.Die(5);
			}
		}
		
		if (act == 5) {
			y += vy;
			vy -= .01f;
			if (count < 3) {
				if (c % 3 == 0)
					count++;
			}
			if (y < ay-.05){
				y = ay-.05f;
				vy = 0;
			}
			if (x < -.5) {
				x += .006f;
			}
		}
	}
}
