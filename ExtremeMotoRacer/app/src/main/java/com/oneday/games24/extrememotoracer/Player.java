package com.oneday.games24.extrememotoracer;


public class Player
{
	float ax,x,y,vx,vy,MinSpeed;
	float Bost;
	
	int forKeyPress = 0;
	int Speed;
	int Distance;
	int Crosscar = 0;
	int CollectCoin =0;
	int ChallengeCom = 0;
	int Strenth = 0;
	int Distroy = 0;

	boolean mTuchScr = false;
	
	long forDistance = 0;
	static long mTotalTime;
	static int mTotalDistance,mTotalCrossCar,mTotalCoin;
	
	void set(float _x,float _y,float _vx,float _vy,float _MinSpeed,int _Strenth)
	{
		ax 	=_x;
		x	=_x;
		y	=_y;
		vx	=_vx;
		vy	=_vy;
		MinSpeed 	= _MinSpeed;
		Speed 		= 30;
		Bost 		= M.BOOST;
		mTuchScr	= false;
		ChallengeCom = 0;
		forDistance = System.currentTimeMillis();
		Distance =0;
		Crosscar = 0;
		Strenth = _Strenth;
		Distroy	= 0;
	}
	void update()
	{
		if(Math.abs(ax-x)>.2f)
		{
			ax = x;
			vx = 0;
		}
		x +=vx;
		Speed = -(int)(500*(MinSpeed+vy));
		if(mTuchScr && Bost > 1)
		{
			if(vy<.1f)
				vy += .005f;
			Bost-=.2f;
		}
		else if(vy>.0f)
			vy -= .005f;
		if(Bost <= M.BOOST){
			Bost +=.01f;
		}
		
		forDistance =  System.currentTimeMillis()-forDistance;
		Distance+=Speed*forDistance;
		mTotalDistance+=((Speed*forDistance)/(-60*1000));
		forDistance = System.currentTimeMillis();
		vx=0.00f;
		float move = .02f;
		switch (GameRenderer.mGR.mCarSel) {
		case 0:
			move = .01f;
			break;
		case 1:
			move = .01f;
			break;
		case 2:
			move = .01f;
			break;
		case 3:
			move = .015f;
			break;
		case 4:
			move = .02f;
			break;
		}
		if(forKeyPress==2)
			vx=move;
		if(forKeyPress==1)
			vx=-move;
	}
}
