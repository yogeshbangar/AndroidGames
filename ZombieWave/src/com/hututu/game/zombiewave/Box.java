package com.hututu.game.zombiewave;

public class Box {
	float x, y;
	float vy;
	boolean down;

	void set(float _x, float _y) {
		x = _x;
		y = _y;
		vy = 0;
		down = false;
	}

	void update(float spd) {
		x += spd;
		if (y > -.61f) {
			if (down)
				vy -= .005f;
			y += vy;
		}
	}
}
