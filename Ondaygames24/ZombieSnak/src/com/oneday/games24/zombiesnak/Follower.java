package com.oneday.games24.zombiesnak;

public class Follower {
	float x, y;

	void set(float _x, float _y) {
		x = _x;
		y = _y;
	}

	void Update() {
		x += M.SPD;
	}
}
