package com.hututu.game.bubblecandyrescue;

public class Player {
	
	boolean move = false;
	
	int fireColor;
	int ResColor;
	int anim;
	
	float x;
	float y;
	float vx;
	float vy;
	float ang;

	void set(double _ang, boolean _move) {
		x = 0;
		y = -.80f;
		vx = (float) Math.sin(_ang) * .08f*1.5f;
		vy =-(float) Math.cos(_ang) * .05f*1.5f;
		if(_ang!=0){
			ang = (float) Math.toDegrees(_ang);
			if(ang>180)
				ang-=ang*0.03;
			else
				ang+=(180-ang)*0.25;
			ang +=180;
		}
		anim =100;
		move = _move;
	}

	void update() {
		x += vx;
		y += vy;
		if (x > .8) {
			vx = -Math.abs(vx);
		}
		if (x < -.8) {
			vx = Math.abs(vx);
		}
	}
	void setAngle(double _ang)
	{
		ang = (float) Math.toDegrees(_ang);
		if(ang>180)
			ang-=ang*0.03;
		else
			ang+=(180-ang)*0.25;
		ang +=180;
	}
}
