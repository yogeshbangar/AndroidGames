package com.hututu.game.zombiewave;

public class Tukde {
	float x, y;
	float vx, vy;
	int img;
	int no;

	void set(float _x, float __y, int _Zom, int _no,float spd) {
		if(_Zom == 4){
			setR(_x, __y, _Zom, _no);
			return;
		}
		x = _x;
		y = __y;
		img = _Zom;
		no = _no;
		if(M.mRand.nextInt(5)!=3)
			vx = -spd*1.5f;
		else
			vx = -.001f;
		vy = .003f*(M.mRand.nextInt(5)+5);
	}
	
	void setR(float _x, float __y, int _Zom, int _no) {
		x = _x;
		y = __y;
		img = _Zom;
		no = _no;
		vx = (M.mRand.nextBoolean()?1:-1)*.003f*(M.mRand.nextInt(15)+5);
		vy = .003f*(M.mRand.nextInt(10)+5);
	}

	void update(float spd) {
		x += (vx + spd);
		y += vy;
		if (vy <= 0 && y <= -.61f && no !=10) {
			vx = vy = 0;
			y = -.61f;
		} else {
			vy -= .001f;
		}
	}
}
