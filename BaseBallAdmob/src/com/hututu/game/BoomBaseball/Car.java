package com.hututu.game.BoomBaseball;

public class Car{
	float x,y,vx;
	int   No;
	GameRenderer mGR;
	public Car(GameRenderer _mGr){
	  mGR = _mGr;
	}
	void set(float _x,float _vx,int _No)
	{
		x     =  _x;
		vx    = _vx;
		y     = -.321f;
		No = _No;
	}
	void Update()
	{
		x +=vx;
	}
	void SetPlaneBird(float _x,float _y,float _vx,int _No)
	{
		x     =  _x;
		vx    = _vx;
		y     = _y;
		No    = _No;
	}
	void UpdatePlaneBird()
	{
		x +=vx;
	}

}
