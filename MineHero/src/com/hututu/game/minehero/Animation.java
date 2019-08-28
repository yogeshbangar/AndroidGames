package com.hututu.game.minehero;

public class Animation {
	float x, y;
	float vx =.01f, vy=.01f;

	void set(float _x, float _y) {
		vx = (M.mRand.nextInt(20) + 5) * (M.mRand.nextBoolean() ? .005f : -.005f);
		vy = (M.mRand.nextInt(20) + 5) * (0.005f);
		x = _x;
		y = _y;
	}

	boolean update() {
		if (x > -1.1f && x < 1.1f && y > -1.1f && y < 1.1f) {
			x += vx;
			y += vy;
			if(vy>-.05f)
				vy-=.005f;
			return true;
		}
		return false;
	}
	boolean isin() {
		if (x > -1.1f && x < 1.1f && y > -1.1f && y < 1.1f) {
			return true;
		}
		return false;
	}
	
	void setDust(float _x, float _y) {
		x = _x;
		y = _y;
		vy =1;
	}
	boolean forDust() {
		if (vy > .1f) {
			y += .003f;
			vy-=.05f;
			return true;
		}
		return false;
	}
	void manu() {
		x+=vx;
		y+=vy;
		if(x>.1)
			vx = -.009f;
		if(x<-.1)
			vx = 0.008f;
		if(y>.1)
			vy = -.007f;
		if(y<-.1)
			vy = 0.006f;
	}
}
