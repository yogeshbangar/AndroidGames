package com.hututu.game.empiresglory;

public class Fire {
	
	int blast =-1;
	int img = 0;
	
	float x, y;
	float vx, vy;
	
	void set(float _x, float _y, float _vx, float _vy,int _img) {
		x = _x;
		y = _y;
		vx = _vx;
		vy = _vy;
		blast =-1;
		img=_img;
	}

	boolean isScrean() {
		if(x>-1.1f && x < 1)
			return true;
		return false;
	}
	boolean update() {
		if(x>-1.1f && x < 1){
			if (x > -.8f) {
				x += vx;
				if(img ==0)
					y += vy;
				vy -= .008f;
			}else{
				blast ++;
				if(blast > 7){
					x =-10;
					return false;
				}
			}
			return true;
		}
		return false;
	}
}
