package com.hututu.dont.tap.white.tile.piano.donttap;

public class Tile {
	int clr = 0;
	int mul = 0;
	
	float y = 0;
	void set(int color, float _y) {
		clr = color;
		y = _y;
		mul = M.mRand.nextInt(8);
	}
	void update(float spd) {
		y += spd;
	}
}
