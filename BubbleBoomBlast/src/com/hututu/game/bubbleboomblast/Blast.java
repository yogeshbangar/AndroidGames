package com.hututu.game.bubbleboomblast;

public class Blast {
	float x,y;
	int blast;
	void set(float _x,float _y,int Blast)
	{
		x = _x;
		y =_y;
		blast = Blast;
	}
	void uodate()
	{
		y-=.04f;
	}
}
