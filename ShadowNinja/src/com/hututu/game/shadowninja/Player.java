package com.hututu.game.shadowninja;

public class Player {
	int act = 0;
	int count = 0;
	int Jump =0;
	
	float x, y;
	float vy = 0;
	void set(float _x, float _y) {
		x = _x;
		y = _y;
		vy = 0;
		act = 0;
		count = 0;
	}

	void update(int counter) {
		y += vy;
		vy -= .0045f;
		if (act >= 4) {
			if (act == 5) {
				if (x < -.5) {
					x += .006f;
				}
			} else {
				if (x > -.7) {
					x -= .006f;
				}
			}
			if (counter % 3 == 0 && count < 3) {
				count++;
			}
		} else if (counter % 3 == 0) {
			if(M.spd !=0)
				count++;
		}
	}

	void reset(float _y) {
		if (Jump != 0)
			M.sound3(R.drawable.land);
		Jump = 0;
		if (act >= 4) {
			if (y < _y - .05f) {
				y = _y - .05f;
				vy = 0;
			}

		} else {
			y = _y;
			vy = 0;
			if (act > 1) {
				if (count > 5) {
					count = 0;
					act = 0;
				}
			} else {
				act = 0;
			}
		}
	}

	void Die(int _act) {
		if (act < 4) {
			act = _act;
			count = 0;
			vy = .02f;
			M.spd =0;
			if(GameRenderer.mStart.mGR.gameOver ==0)
				GameRenderer.mStart.mGR.gameOver =1;
		}
	}
}
