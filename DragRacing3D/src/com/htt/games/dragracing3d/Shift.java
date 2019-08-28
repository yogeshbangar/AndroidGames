package com.htt.games.dragracing3d;

public class Shift {
	float y, vy;
	int type;

	void set(float _y, int _t) {
		y = _y;
		vy = 0;
		type = _t;
	}

	void update() {
		y += vy;
		vy += 0.001f;
	}
}
