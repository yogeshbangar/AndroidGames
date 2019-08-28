package com.Oneday.games24.peguinadventure;

public class Thirteen {
	float x,y;
	float vx,vy;
	void set(float _x,float _y,float _vx,float _vy)
	{
		x  = _x;
		y  = _y;
		vx =_vx;
		vy =_vy;
	}
	void update()
	{
		x +=vx;
		y +=vy;
		if(vy!=0)
			vy-=.01f;
		
		if(x < -.7)
			vx = Math.abs(vx);
		if(x > 0.7)
			vx = -Math.abs(vx);
		if(y < -.6){
			vy = Math.abs(vy)*.5f;
			vx = vx*.5f;
			if(vy<.006f){
				vx = 0;
				vy = 0;
				y =-.6f;
			}
		}
		if(y > 0.7)
			vy = -Math.abs(vy);
		if(x < -1.2 || x > 1.2 || y < -1.2 ||y > 1.2)
		{
			x  = 0;
			y  = 0;
			vx = 0;
			vy = 0;
		}
	}
}
