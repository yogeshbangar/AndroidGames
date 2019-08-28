package com.hututu.game.zombiewave;

public class Zombie {
	float x, y;
	int no;

	void set(float _x, float _y, int _no) {
		x = _x;
		y = _y;
		no = _no;
	}
	void update(float spd){
		x+=spd;
		x-=(.003f+no*.001f);
	}
}
