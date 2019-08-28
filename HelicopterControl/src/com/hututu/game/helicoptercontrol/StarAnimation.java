package com.hututu.game.helicoptercontrol;

import java.util.Random;

public class StarAnimation {
	float x,y,vx,vy,scal;
	int ImgNo;
	Random rand = new Random();
	public StarAnimation()
	{
		x=y=scal=-2;
		vx=vy=ImgNo = 0;
	}
	void set(float _x,float _y,float _vx,float _vy,int _imgno)
	{
		x     = _x;
		y     = _y;
		vx    =_vx;
		vy    =_vy;
		ImgNo = Math.abs(_imgno);
		scal  = 1;
	}
	void Update()
	{
		x  += vx;
		y  += vy;
		vy -=.002f;
		scal -=.01f;
	}
	
}
