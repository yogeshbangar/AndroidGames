package com.hututu.games.highwayracing;

public class Sky {
	int type;
	float x,y;
	Coin mCar[];
	public Sky()
	{
		mCar = new Coin[2];
		mCar[0] =new Coin();
		mCar[1] =new Coin();
	}
	void set(float _x,float _y,int _type)
	{
		x = _x;
		y = _y;
		type = _type;
		mCar[0].set(1,-.0f);
		mCar[1].set(-1,0.0f);
	}
}
