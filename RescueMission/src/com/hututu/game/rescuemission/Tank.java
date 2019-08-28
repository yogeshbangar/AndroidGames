package com.hututu.game.rescuemission;

public class Tank {
	float x,y;
	float cx1,cy1;
	float cx2,cy2;
	float cx3,cy3;
	byte isB1;
	byte isB2;
	byte isB3;
	public Tank()
	{
		cx1 =.05f;
		cy1 =.32f;
		
		cx2 =.38f;
		cy2 =.50f;
		
		cx3 =-.36f;
		cy3 = .32f;
	}
	void set(float _x,float _y)
	{
		x =_x;
		y =_y;
		isB3 =isB2 = isB1 = 0;
	}
}
