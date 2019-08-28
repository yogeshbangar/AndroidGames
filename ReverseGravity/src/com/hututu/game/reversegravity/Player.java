package com.hututu.game.reversegravity;

public class Player {
	float x,y,vx,vy;
	int mtap,mLife;
	float x1[],y1[];
	int counter=0;
	public Player()
	{
		x1 = new float[8];
		y1 = new float[8];
	}
	void set(float _x,float _y,float _vx,float _vy,int life)
	{
		x = _x;
		y = _y;
		vx=_vx;
		vy=_vy;
		mLife = life ; 
		mtap =100;
		for(int i =0;i<x1.length;i++)
		{
			x1[i] = x;
			y1[i] = y-.01f;
		}
	}
	void update()
	{
		for(int i =0;i<x1.length;i++)
		{
			x1[i]=x+(vx*i);
			y1[i]=y-(vy*i);
		}
	}
}
