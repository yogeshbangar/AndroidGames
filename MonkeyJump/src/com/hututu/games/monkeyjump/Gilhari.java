package com.hututu.games.monkeyjump;

public class Gilhari {
	boolean isgildhari = true;
	float x, y, vx;
	void set(float _y) {
		x = (M.mRand.nextBoolean() ? 1 : -1) * .7f;
		y = _y;
		isgildhari = true;
		if (x > 0)
			vx = -1.1f;
		else
			vx = 1.1f;
	}

	boolean update(float speed) {
		if (y > -1.6) {
			if (y < 0.9) {
				if (x > .7f)
					vx = -1.1f;
				if (x < -.7f)
					vx = 1.1f;
				if(speed > 0)
					x += speed * vx;
				else
					x -= speed * vx;
			}
			y -= speed;
			return isgildhari;
		}
		return false;
	}
}
