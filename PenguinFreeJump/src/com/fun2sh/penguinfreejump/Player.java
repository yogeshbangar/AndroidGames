package com.fun2sh.penguinfreejump;

public class Player {
	
	boolean isWin = false;
	
	int img = 0;
	int row = 0;
	int mCond = -1;
	
	float x,y,z;
	float vx,vy,vz;
	
	void set(float _x,float _y){
		x = _x;
		y = _y;
		z = 1;
		vx =0;
		vy =0;
		vz = .1f;
		mCond = -1;
		row = 0;
		isWin = false;
	}

	void update() {
		if (vy > 0) {
			vy += M.SPD;
			if (img < 10) {
				img++;
				if (img == 8){
					M.sound9(R.drawable.landing);
				}
			}
			z += vz;
			vz -= .11f;
			x += vx;
		} else {
			vy = 0;
			img = 0;
			vz = .05f;
			z = 1;
		}
	}
	void Jump(float _vy,int jump){
		vy =_vy;
		img = 1;
		vz = .5f;
		z = 1;
		vx = (-M.DIS + jump * M.DIS - x)*.1f;
	}
	void Survival(float _y,int jump){
		vy = (_y - y + M.SPD * 3) * .1f;
//		x =-M.DIS + jump * M.DIS;
		img = 1;
		vz = .5f;
		z = 1;
		vx = (-M.DIS + jump * M.DIS - x)*.11f;
	}
	void reset(float _y){
		y =_y;
		vy = 0;
		img = 0;
		vz = .05f;
		z = 1;
		vx =0;
	}
	void servUpdate(){
		y+=vy;
		x+=vx;
		if (img < 10) {
			img++;
			if (img == 8){
				M.sound9(R.drawable.landing);
			}
		}
		z += vz;
		vz -= .11f;
	}
}
