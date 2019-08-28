package com.hututu.game.zombiewave;

public class Background {
	float x, y;

	void set(float _x, float _y) {
		x = _x;
		y = _y;
	}

	void update(float speed) {
		x += speed;
	}
}
