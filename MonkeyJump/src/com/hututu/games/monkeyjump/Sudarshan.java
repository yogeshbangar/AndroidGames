package com.hututu.games.monkeyjump;

public class Sudarshan {
	boolean isRokect = false;
	float x, y;
	float vx;
	float vy;
	

	void set(float _x, float _y, float _vx, float _vy, boolean rocket) {
		x = _x;
		y = _y;
		vy = _vy;
//		vy = .9f;
		if (x > 0)
			vx = -_vx;
		else
			vx = _vx;
		isRokect = rocket;
	}

	boolean update(float speed) {
		if (y > -1.6) {
			y -= speed * vy;
			if(speed > 0){
				x += speed * vx;
			}else
				x -= speed * vx;
			return true;
		}
		return false;
	}
}
