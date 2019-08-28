package com.htt.games.chase2camera;

public class Camerar {
	float x,y,z;
	float vx,vy;
	float ange;
	HTTRenderer mGR;
	int com =10;
	float spd = .1f;
	
	public Camerar(HTTRenderer gr){
		mGR = gr;
		vx = 0;
		vy = spd;
	}
	
	boolean update() {
		mGR.getCurrentCamera().setPosition(mGR.getCurrentCamera().getX() + vx, mGR.getCurrentCamera().getY() + vy, 50);
		if (mGR.getCurrentCamera().getY() > com) {
			vx = 0;
			vy = 0;
			mGR.getCurrentCamera().setY(com);
			mGR.getCurrentCamera().setRotation(0, 0, 90);
			return true;
		}
		if (mGR.getCurrentCamera().getX() > com) {
			vx = 0;
			vy = -spd;
//			vy =0;
			mGR.getCurrentCamera().setX(com);
			mGR.getCurrentCamera().setRotation(0, 0, 180);
			return true;
		}
		if (mGR.getCurrentCamera().getY() < -com) {
			vx = -spd;
			vy = 0.0f;
//			vx = 0;
			mGR.getCurrentCamera().setY(-com);
			mGR.getCurrentCamera().setRotation(0, 0, 270);
			return true;
		}
		if (mGR.getCurrentCamera().getX() < -com) {
			vx = 0;
			vy = spd;
			mGR.getCurrentCamera().setX(-com);
			mGR.getCurrentCamera().setRotation(0, 0, 0);
			return true;
		}
		return false;
	}
	
	
}
