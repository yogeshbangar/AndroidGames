package com.hututu.game.ScarecrowvsBirds;

public class Point {
	float x,y,vy,fad;
	int no;
	public void set(float _x,float _y,float _vy,float _fad)
	{
		x   = _x;
		y   = _y;
		vy  = _vy;
		fad = _fad;
	}
	public void update()
	{
		 y+=vy;
		 fad -=.01f;
		 if(fad<.1f)
		 {
			 x=y=100;
			 vy=0;
			 fad =1;
		 }
	}

}
