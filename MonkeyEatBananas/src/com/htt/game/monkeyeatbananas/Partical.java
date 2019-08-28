package com.htt.game.monkeyeatbananas;

public class Partical {
	float x, y;
	float vx, vy;
	public Partical(){
		vx = vy = x = y = 0;
	}
	void set() {
		if(M.mRand.nextBoolean()){
			x = (M.mRand.nextBoolean() ? 1 : -1);
			y = (M.mRand.nextInt(200)-100)*.01f;
		}else{
			x = (M.mRand.nextInt(200)-100)*.01f;
			y = (M.mRand.nextBoolean() ? 1 : -1);
		}
		double k =0;
		k = M.GetAngle(-x, -y);
		vx = (float)Math.cos(k)*.1f;
		vy = (float)Math.sin(k)*.1f;
	}

	void update() {
		
		if(vx > 0 && x > 0){
			vx =0;
			vy =0;
		}
		if(vx < 0 && x < 0){
			vx =0;
			vy =0;
		}
		{
			x += vx;
			y += vy;
		}
		
	}
}
