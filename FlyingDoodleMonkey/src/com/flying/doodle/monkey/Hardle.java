package com.flying.doodle.monkey;

public class Hardle {
	float x, y, z;
	float vz;
	void set(float _y) {
		x = (M.mRand.nextInt(24)-12)*.05f;
		y = _y;
		z = 1;
		vz = .01f;
	}

	void update() {
		y += M.SPD;
		z+=vz;
		if(z > 1.2)
			vz = -.01f;
		if(z < .8)
			vz = 0.01f;
	}
}
