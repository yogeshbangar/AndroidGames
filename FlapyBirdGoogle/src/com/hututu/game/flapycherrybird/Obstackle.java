package com.hututu.game.flapycherrybird;

public class Obstackle {
	float x,y;
	int No;
	
	void Set(float _x,float _y,int _no)
	{
		x   =  _x;
		y   = _y;
		No = _no; 
	}
	void update()
	{
		x+=M.speed;
	}

}
