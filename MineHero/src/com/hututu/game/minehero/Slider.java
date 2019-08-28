package com.hututu.game.minehero;

public class Slider {
	float x,y;
	float vx;
	void set(float _x, float _y) {
		x = _x;
		y = _y;
		vx = M.mRand.nextBoolean()?.03f:-.03f;
	}

	boolean update(float _spd) {
		if (x > -1.1f && x < 1.1f && y > -1.3f && y < 1.1f) {
			x += vx;
			if(x > .8f)
				vx = -.03f;
			if(x < -.8f)
				vx = 0.03f;
			y += _spd;
			return true;
		}
		return false;
	}
	boolean isin() {
		if (x > -1.1f && x < 1.1f && y > -1.3f && y < 1.1f) {
			return true;
		}
		return false;
	}
}
