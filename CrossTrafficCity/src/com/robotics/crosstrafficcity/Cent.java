package com.robotics.crosstrafficcity;

public class Cent {
	float vx, vy;
//	float vari =.005f;
	void reset() {
		vx = (M.mRand.nextInt(10) + 5) * (M.mRand.nextBoolean() ? -.005f : .005f);
		vy = (M.mRand.nextInt(10) + 5) * (M.mRand.nextBoolean() ? -.005f : .005f);
	}

	void update() {
		if (vx > 0) {
			vx -= .005;
			if (vx < 0) {
				vx = 0;
			}
		}
		if (vx < 0) {
			vx += .005;
			if (vx > 0) {
				vx = 0;
			}
		}
		// ~~~~~~~~~~~~~~~~~~~~~`
		if (vy > 0) {
			vy -= .005;
			if (vy < 0) {
				vy = 0;
			}
		}
		if (vy < 0) {
			vy += .005f;
			if (vy > 0) {
				vy = 0;
			}
		}
	}
}
