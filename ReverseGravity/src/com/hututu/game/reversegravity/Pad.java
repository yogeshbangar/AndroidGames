package com.hututu.game.reversegravity;

public class Pad {
	float x,y;
	float vy;
	int Gift = -1;
	void set(float _x,float _y)
	{
		x = _x;
		y = _y;
		vy = 0;
		Gift = -1;
	}
	void Update()
	{
		y+=M.SPEED;
		if(vy!=0)
		{
			vy-=.0041f;
			if(vy<-.02)
			{
				vy=0;
			}
			y+=vy;
		}
	}
}
