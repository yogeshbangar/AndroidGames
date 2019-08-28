package com.onedaygames24.stickpowerheroes;

public class Player {
	float x,y,vx,vy;
	int mAction=0,mCrash=0,mHeroNo=0;
	int mVal[] = {0,50,75,150,250,300};
	boolean isUlta;
	boolean isUnLock[] = new boolean[6];
	
	public Player(){
	  isUnLock[0]=true;
	}
	void set(float _x)
	{
		x  = _x;
		y  = -.389f;
		vx = vy=0;
		mAction=0;
		mCrash=0;
		isUlta = false;
	}
	void update()
	{
		x +=vx;
		y +=vy;
		if(vy!=0)
		  vy-=.003f;
	}
	void update2(float _x)
	{
		x =_x;
	}
	void reset()
	{
		vx=0;
		mAction=0;
		mCrash=0;
		isUlta =false;
	}

}
