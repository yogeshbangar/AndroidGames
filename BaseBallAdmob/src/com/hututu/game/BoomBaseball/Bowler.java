package com.hututu.game.BoomBaseball;

public class Bowler {
	float x,y;
	boolean isThrough =false;
	int Count,mBowlerAni=0;
	GameRenderer mGR;
	
	public Bowler(GameRenderer _mGR){
	
		mGR = _mGR;
	}
	public void Set(float _x,float _y)
	{
		x = _x;
		y = _y; 
		isThrough = false;
		mBowlerAni=0;
		Count=0;
	}
	
	void Update()
	{
		Count++;
		if(Count%2==0)
		{
		  if(mBowlerAni<mGR.mTex_Bowler.length-1)
	    	 mBowlerAni++;
		  if(mBowlerAni ==25)
		   { 
			isThrough =true;
			mGR.mBall.Set(-0.04f,-.28f,0,.015f);
			mGR.mTotalBall--;
		   }
		}
	}
	void reset()
	{
		Count =0;
		mBowlerAni =0;
		isThrough =false;
	}
}
