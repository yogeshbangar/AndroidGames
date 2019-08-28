package com.oneday.games24.gravityracerboy;

public class Guy {
	byte down;
	int colNo;
	float x, y;
	float spd;

	void set() {
		down = 1;
		colNo = 0;
		x = -.0f;
		y = -.46f;
		spd = -.00f;
	}

	void Oppset() {
		down = 1;
		colNo = 0;
		x = -.9f;
		y = -.4f;
		spd = -.05f;
	}

	void update() {
		if (GameRenderer.mStart.mGR.OverCunt == 0 && GameRenderer.mStart.mGR.go
				&& spd > -.04f) {
			spd -= .001f;
		}
		if (down == 0)
			y += spd * 1.3f;
		else
			y -= spd * 1.3f;
	}

	void change() {
		if (down == 0)
			down = 1;
		else
			down = 0;
		M.Jump();
	}
}
