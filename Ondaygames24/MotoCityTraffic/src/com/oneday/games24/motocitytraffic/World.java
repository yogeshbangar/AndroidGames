package com.oneday.games24.motocitytraffic;

public class World {
	float x,z,mDollar;
	static float VX;
	static int Count=0;
	int mWorldCoin;
	boolean isUnLock;
	
	public World(){
		isUnLock =false;
	}
	void set(float _x,int _Coin,float _Dollar)
	{
		x          = _x;
		mWorldCoin = _Coin;
		mDollar    = _Dollar;
	}
	void update(float vx)
	{
		x  +=vx;
		z = 1f-(float)Math.abs(x*.85f-0);
		Count++;
	}

}
