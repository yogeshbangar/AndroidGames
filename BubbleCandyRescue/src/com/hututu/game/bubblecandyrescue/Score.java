package com.hututu.game.bubblecandyrescue;

public class Score {
	float x, y;
	float vy;
	int score;

	void set(float _x, float _y, int _score) {
		x = _x;
		y = _y;
		score = _score;
		vy = (M.mRand.nextInt(5)+5)*.001f;
	}
	void update(){
		y += vy;
		vy+=.001f;
	}
}
