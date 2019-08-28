package com.htt.game.monkeyeatbananas;

public class Takkar {
	float x, y;
	float vx, vy;
	public Takkar(){
		x = -100;y = 0;
	}
	void set(float _x, float _y) {
		x = _x;
		y = _y;
		vx = (M.mRand.nextBoolean() ? 1 : -1) * (M.mRand.nextInt(50) * .002f);
		vy = (M.mRand.nextInt(50) * .0015f);
	}

	boolean update() {
		if (y > -1.1) {
			x += vx;
			y += vy;
			vy -= .01f;
			return true;
		}
		return false;
	}
}
