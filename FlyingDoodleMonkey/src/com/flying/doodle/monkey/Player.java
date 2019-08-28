package com.flying.doodle.monkey;

public class Player {
	boolean iStart;

	float x, y;
	float vx, vy;
	float mDist = 2;
	int rot =0;
	void set() {
		x = 0;
		y = 0;
		vx = 0;
		vy = 0;
		iStart = false;
		mDist = 2;
		rot =0;
	}

	void update() {
		if (iStart) {
			x += vx;
			y += vy;
			vy -= .004f;
			if(vx > 0){
				rot-=40;
			}else{
				rot+=40;
			}
			if(x <-.9 || x > .9||y <-.9 || y > .95){
//				M.GameScreen = M.GAMEOVER;
				GameRenderer.mStart.mGR.root.Gameover();
			}
			
		}
	}
}
