package com.hututu.game.empiresglory;

public class Power {
	float x, y;
	int count;
	void set(float _x, float _y, int _count) {
		x = _x;
		y = _y;
		count = _count;
	}

	boolean isScrean() {
		if (count < 37)
			return true;
		return false;
	}

	boolean update() {
		if (count < 37) {
			if (count < 20) {
				y -= .1f;
				x += .1f;
			}
			count++;
			return true;
		}
		return false;
	}

}
