package com.hututu.game.minehero;


public class AnimCoin {
	float x, y;
	float vx, vy;

	void set(float _x, float _y,float _x1, float _y1) {
		x = _x;
		y = _y;
		if (isin()) {
			double _ang = (GameRenderer.mStart.mGR.root.GetAngle((y - _y1), -(x - _x1)));
			vx = (float) Math.sin(_ang) * .16f;
			vy = -(float) Math.cos(_ang) * .14f;
		}
		
		
	}

	boolean update() {
		if (x > -1 && x < 1 && y > -1 && y < 1) {
			x +=vx;
			y +=vy;
			if (vy > 0 && y > .1f) {
				y = -10;
			}
			if (vy < 0 && y < .1f) {
				y = -10;
			}
			return true;
		}
		return false;
	}
	boolean isin() {
		if (x > -1 && x < 1 && y > -1 && y < 1) {
			return true;
		}
		return false;
	}
}
