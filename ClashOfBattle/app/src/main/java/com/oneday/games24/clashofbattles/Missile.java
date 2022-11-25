package com.oneday.games24.clashofbattles;

public class Missile {
	
	byte img = 0;
	
	int angle = 0;
	
	float x, y;
	float vx, vy;
	

	void set(float _x, float _y, float x1, float y1, final int _img) {
		x = _x;
		y = _y;
		if (_img == 3 || _img == 6) {
			double ang = (GameRenderer.mStart.mGR.root.GetAngle((_y - y1),-(_x - x1)));
			vx = (float) Math.sin(ang) * .07f ;
			vy = -(float) Math.cos(ang) * .08f;
			angle = (int) Math.toDegrees(ang) + 90;
			img = 0;
		} else if (_img == 4) {
			vx = -.03f;
			vy = 0.10f;
			img = 1;
		} else {
			vx = vy = 0;
		}
//		System.out.println("[x= "+x + "][y= " + y + "][vx = " + vx + "][vy= " + vy + "][ang = " + angle+"][img ="+_img+"]");
	}

	
	
	boolean update() {
		if (x < 2 && x > -2 && y < 2 && y > -2) {
			x += vx;
			y += vy;
			if (img == 1) {
				if (y < -.34f) {
					vy = 0;
				} else {
					vy -= .03f;
				}

			} else if (y < -.3f){
				GameRenderer.mStart.mGR.root.setBlast(x, y,M.mRand.nextInt(3));
				reset();
			}
			return true;
		}
		return false;
	}

	boolean IsIn() {
		if (x < 2 && x > -2 && y < 2 && y > -2) {
			return true;
		}
		return false;
	}

	void reset() {
		y = x = -10;
		vy = vx = 0;
	}
}
