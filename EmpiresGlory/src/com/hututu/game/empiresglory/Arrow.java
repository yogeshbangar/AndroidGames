package com.hututu.game.empiresglory;

public class Arrow {
	float x, y;
	float vx;
	float vy;
	float radian;

	void set(float _x, float _y, double rad) {
		x = _x;
		y = _y;
		radian = (float) rad;
		vx = (float) Math.cos(radian) * .17f * .5f; 
		vy = (float) Math.sin(radian) * .28f * .5f;
	}

	boolean update() {
		if (x > -1 && x < 1.5 && y > -1.5f && y < 1.5) {
			x += vx;
			y += vy;
			return true;
		}
		return false;
	}
	public float cx(){
		return x+(float) Math.cos(radian) * .17f * .54f;
	}
	public float cy(){
		return y+(float) Math.sin(radian) * .28f * .54f;
	}
	boolean isScreen() {
		if (x > -1 && x < 1.3 && y > -1.2f && y < 1.2) {
			return true;
		}
		return false;
	}
	void reset(){
		x = -10;
		y = -10;
	}
}

//sx = .5f
//DrawTexture(gl,mGR.mTex_Play,
//mGR.mArrow[i].x+ (float) Math.cos(mGR.mArrow[i].radian) * .17f* sx,
//mGR.mArrow[i].y+ (float) Math.sin(mGR.mArrow[i].radian) * .28f* sx);