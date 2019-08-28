package com.hututu.game.stuntracingcar;

public class Player {
	float x,y,vx,vy,initY;
	int collide = 0;
	int pos;
	boolean isOnair = false;
	void set(float _x,float _y,float _vx,float _vy)
	{
		x =_x;
		y =_y;
		vx=_vx;
		vy=_vy;
		initY = y;
		isOnair = false;
		pos =1;
	}
	void Uodate()
	{
		if(collide>0)
		{
			M.speed-=.01f;
		}
		collide-=1;
		x+=vx;
		y+=vy;
		if(y<=initY)
		{
			vy	= 0;
			y 	= initY;
			isOnair = false;
		}
		else
		{
			vy-=.04f;
		}
		if(x <-.5f)
		{
			vx = 0;
			x =-.5f;
		}
		if(x >.5f)
		{
			vx = 0;
			x =0.5f;
		}
		if(x <.1f &&x >-.1f && pos == 1)
		{
			vx = 0;
			x  = 0;
		}
//		System.out.println(pos+"  "+x);
	}
}
