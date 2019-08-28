package com.hututu.game.minehero;

public class Monster {
	
	boolean tuch;
	
	float x,y;
	float vx;
	public void set(float _x,float _y){
		x = _x;
		y = _y;
		vx = M.mRand.nextBoolean()?.02f:-.02f;
		tuch = false;
	}

	public boolean update() {
		if (x > -1.1f && x < 1.1f && y > -1.1f && y < 1.1f) {
			x += vx;
			if(x > 1.05f)
				x = -1.05f;
			if(x < -1.05f)
				x = 1.05f;
			if(tuch){
				tuch = false;
			}else{
				y -= .02f;
			}
			return true;
		}
		return false;
	}
	public boolean check(){
		if (x > -1.1f && x < 1.1f && y > -1.1f && y < 1.1f && !tuch) {
			return true;
		}
		return false;
	}
	public boolean isin(){
		if (x > -1.1f && x < 1.1f && y > -1.1f && y < 1.1f) {
			return true;
		}
		return false;
	}
}
