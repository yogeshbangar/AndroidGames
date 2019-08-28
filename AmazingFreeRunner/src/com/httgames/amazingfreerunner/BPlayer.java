package com.httgames.amazingfreerunner;

public class BPlayer {
	boolean isStart = false;
	
	int Jump =0;
	int die =0;
	
	float x, y;
	float vy = 0;
	
	void set(float _x, float _y) {
		x = _x;
		y = _y;
		vy = 0;
		Jump =0;
		die =0;
		isStart = false;
	}

	void update(int counter) {
		y += vy;
		vy -= .006f;
	}

	void reset(float _y) {
		if (Jump != 0)
			C.sound3(R.drawable.land);
		Jump = 0;
		y = _y;
		vy = 0;
	}

}
