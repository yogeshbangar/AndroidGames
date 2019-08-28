package com.hututu.game.minehero;

public class Chackra {
	float y = 0;
	float z = 1, vz = .01f;

	void set(float _y) {
		this.y = _y;
	}

	void update(float _spd,int Timer) {
//		if(y<1.4f)
		if(Timer >0){
			if(y<1.0f)
				y += .01f;
		} else {
			y += _spd;
			if (y < .65)
				y -= .01f;
			else
				y -= .03f;
		}
		z += vz;
		if (z > 1.2f)
			vz = -.01f;
		if (z < 0.9f)
			vz = 0.01f;
	}
}
