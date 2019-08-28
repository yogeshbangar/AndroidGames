package com.oneday.game24.frongjumpingjump;

import java.util.Random;

public class StarAnimation {
	float x,y,vx,vy,scal;
	float r,g,b;
	int star,Cnt;
	Random rand = new Random();
	public StarAnimation()
	{
		x=y=scal=-2;
		vx=vy=star = 0;
	}
	void setAni(float _x,float _y,float _vx,float _vy,int _star,int _cnt)
	{
		x = _x;
		y = _y;
		vx=_vx;
		vy=_vy;
		star=Math.abs(_star);
		scal = 1.5f;
		Cnt = _cnt;
		r   = M.randomRangeInt(0,255);
		g   = M.randomRangeInt(0,255);
		b   = M.randomRangeInt(0,255);
	}
	void UpdateAni()
	{
		Cnt++;
		if(Cnt>15)
		{
		  x += vx;
		  vy -=.005f;  
		}
		else
		  vy +=.002f;
		y += vy;
		scal-=.01f;
	}
}
