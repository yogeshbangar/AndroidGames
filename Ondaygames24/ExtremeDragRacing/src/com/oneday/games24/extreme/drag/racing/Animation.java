package com.oneday.games24.extreme.drag.racing;

public class Animation {
	

	
	void set() {
		switch (n) {
		case 0:
			x = -2;
			y = 2;
			break;
		case 1:
			x = 0;
			y = 2;
			break;
		case 2:
			x = 2;
			y = 2;
			break;
		case 3:
			x = 2;
			y = 0;
			break;
		case 4:
			x = 2;
			y = -2;
			break;
		case 5:
			x = 0;
			y = -2;
			break;
		case 6:
			x = -2;
			y = -2;
			break;
		case 7:
			x = -2;
			y = 0;
			break;
		}
		y -= .2f;
		counter = M.mRand.nextInt(20);
	}
	float x, y;
	float vx, vy;
	float ang;
	final int n;
	int counter = 0;
	
	void update() {
		if (counter < 0) {
			x += vx;
			y += vy;
			switch (n) {
			case 0:
				if (x > 0)
					set();
				break;
			case 1:
				if (y < 0)
					set();
				break;
			case 2:
				if (x < 0)
					set();
				break;
			case 3:
				if (x < 0)
					set();
				break;
			case 4:
				if (x < 0)
					set();
				break;
			case 5:
				if (y > 0)
					set();
				break;
			case 6:
				if (x > 0)
					set();
				break;
			case 7:
				if (x > 0)
					set();
				break;
			}
		} else
			counter--;
	}
	public Animation(int n) {
		this.n = n;
		float dx = 0, dy = 0;
		switch (n) {
		case 0:
			dx = 0;
			dy = 0;
			x = -2;
			y = 2;
			break;
		case 1:
			dx = M.mMaxX / 2;
			dy = 0;
			x = 0;
			y = 2;
			break;
		case 2:
			dx = M.mMaxX;
			dy = 0;
			x = 2;
			y = 2;
			break;
		case 3:
			dx = M.mMaxX;
			dy = M.mMaxY / 2;
			x = 2;
			y = 0;
			break;
		case 4:
			dx = M.mMaxX;
			dy = M.mMaxY;
			x = 2;
			y = -2;
			break;
		case 5:
			dx = M.mMaxX / 2;
			dy = M.mMaxY;
			x = 0;
			y = -2;
			break;
		case 6:
			dx = 0;
			dy = M.mMaxY;
			x = -2;
			y = -2;
			break;
		case 7:
			dx = 0;
			dy = M.mMaxY / 2;
			x = -2;
			y = 0;
			break;
		}

		ang = (float) (M.GetAngle(-(dy - M.mMaxY * .5f), -(dx - M.mMaxX / 2)));
		vx = (float) (Math.sin(ang) * .12);
		vy = -(float) (Math.cos(ang) * .20);
		ang = (float) Math.toDegrees(ang) + 90;
	}

	

}
