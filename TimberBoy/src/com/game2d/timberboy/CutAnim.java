package com.game2d.timberboy;

public class CutAnim {
	float x,y;
	float vx,vy;
	int rotat;
	int branch = 0;
	
	void reset(){
		x = y = vx = vy = rotat = -100;
	}
	void set(float _x,float _y,int _tree,boolean isRight){
		x = _x;
		y = _y;
		if(isRight)
			vx = -.15f;
		else
			vx = 0.15f;
		vy = .1f;
		rotat = 0;
		branch =_tree;
	}

	boolean update() {
		if (x > -1.2 && x < 1.2 && y > -1.2 && y < 1.2) {
			x += vx;
			y += vy;
			vy-=.02f;
			if(vx > 0)
				rotat+=10;
			else
				rotat-=10;
			return true;
		}
		return false;
	}
	boolean isIn() {
		if (x > -1.2 && x < 1.2 && y > -1.2 && y < 1.2) {
			return true;
		}
		return false;
	}
}
