package com.oneday.games24.zombiesnak;

public class Ball {

	byte Over = 0;
	float x, y;
	float vy;
	boolean isTuch = false;
	boolean isStart = false;
	boolean isRandom = true;
	int img;
	void set(float _x, float _y , int _img) {
		x = _x;
		y = _y;
		if(isRandom)
			img = _img;
		vy = 0;
		isTuch = false;
		isStart = false;
		Over = 0;
	}

	void update() {
		y += vy;
		if (isTuch)
			vy += 0.001f;
		else
			vy -= 0.001f;

		if (y > 1 || y < -1) {
			Over = 1;//set(x, 0);
		}
	}
}
