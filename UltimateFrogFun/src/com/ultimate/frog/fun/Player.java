package com.ultimate.frog.fun;

public class Player {
	boolean iStart = false;
	boolean gone2 = false; 
	
	int cj = 2;
	int Pos = 0;
	int End = 0;
	int img = 0;
	int rot =0;
	
	float x,y,z;
	float vx,vy;
	void start(float _x,float _y,int _pos){
		x = _x;
		y = _y;
		Pos = _pos;
		cj = 2;
		iStart = false;
		End = 0;
		img = 0;
		gone2 = false;
		rot =0;
	}
	
	
	void set(float _x,float _y,int _pos){
		x = _x;
		y = _y;
		Pos = _pos;
		img = 5;
	}
	void setvx(int j){
		vx = (j - cj)*.1f;
		cj = j; 
	}
	void update(){
		if(img > 0){
			img -=1;
			if(img == 0 && !gone2)
				M.sound4(R.drawable.right_jump );
		}
		/*if (vx > 0 && (-.75f + cj * .5f) > x) {
			x +=vx;
			if((-.75f + cj * .5f) < x){
				x =-.75f + cj * .5f;
			}
		}
		if (vx < 0 && (-.75f + cj * .5f) < x) {
			x +=vx;
			if((-.75f + cj * .5f) > x){
				x =-.75f + cj * .5f;
			}
		}*/
	}
}
