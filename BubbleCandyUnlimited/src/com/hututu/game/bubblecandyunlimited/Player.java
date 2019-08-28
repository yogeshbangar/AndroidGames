package com.hututu.game.bubblecandyunlimited;

public class Player {
	
	boolean move = false;
	byte ball = 0;
	byte fireColor;
	byte ResColor;
	byte anim;
	float x;
	float y;
	float vx;
	float vy;
	float ang;

	void set(double _ang, boolean _move) {
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
		
		double rd = Math.toRadians(ang);
		x = (float)(((-6+rd)*.1f)-(.585f*Math.sin(rd)));
		y =-.95f+.342f*(float)Math.cos(rd);
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
		
		
		double rd = Math.toRadians(ang);
		float mx = (float)(-6+rd)*.1f;
		x = mx	-.585f*(float)Math.sin(rd);
		y =-.95f+.342f*(float)Math.cos(rd);
		
	}
}
