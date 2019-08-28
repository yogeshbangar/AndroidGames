package com.hututu.game.TrainCrash;

public class Signal {
	float x,y;
	boolean isRedSignal=false;
	int mSignalTime=0,no,mLimit;
	
	void set(float _x,float _y,int _no)
	{
		x = _x;
		y = _y;
		isRedSignal =false;
		no = _no;
		mLimit=0;
	}
	void update()
	{
		mSignalTime++;
		if(mSignalTime==150)
		  M.bellSound(GameRenderer.mContext,R.drawable.bell);
		if(mSignalTime>200)
		{
		  isRedSignal =false;
		  mSignalTime=0;
		}
	}
	void ActiveSignal()
	{
		if(mLimit<3)
		{
		  mLimit++;
		  mSignalTime =0;
		  isRedSignal =true;
		}
	}
	void ResetSignal()
	{
		x=y=100;
		isRedSignal =false;
		mSignalTime =0;
	}

}
