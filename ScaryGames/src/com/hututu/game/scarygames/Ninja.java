package com.hututu.game.scarygames;


public class Ninja 
{
	boolean tuch = false;
	boolean b = false;
	
	int pos;
	int counter = 0;
	
	float x,y;
	float vy =.01f,vx = 0;
	void setNinja(float _x, float _y,float vy,int _pos,boolean _tuch)
	{
		x=_x;
		y=_y;
		pos=_pos;
		tuch = _tuch;
	}
	
}
