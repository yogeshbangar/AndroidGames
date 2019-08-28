package com.oneday.games24.shadowninjas;

public class BaseWay {
	float x, y;
	float r, g, b;
	float sx = 1;

	void setSx(){
		sx = M.mRand.nextInt(450)*.01f + .5f;
	}
	void set(float _x) {
		x = _x;
		y = -.56f-M.mRand.nextInt(20)*.01f;
		r = M.mRand.nextInt(255) / 255f;
		g = M.mRand.nextInt(255) / 255f;
		b = M.mRand.nextInt(255) / 255f;
	}

	void update() {
		x += M.spd;
	}
}
