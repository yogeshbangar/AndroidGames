package com.hututu.games.ninjajumper;

public class Power {
	int powerNo;
	float x, y;

	void set(int no) {
		powerNo = no;
		x = (M.mRand.nextInt(140) - 70) * .01f;
		y = (M.mRand.nextInt(120) - 40) * .01f;
	}
}
