package com.onedaygames24.pokernpoker;

public class Card {
	
	byte mState = 0;
	byte mBig;
	
	int mBat	= 0;
	int mTotalCoin = M.MAXAMOUNT;
	
	float x,y;
	float x1,y1;
	float vx,vy;
	float vx1,vy1;
	
	public Card()
	{
		mState = M.NOTHING;
		mTotalCoin = 200;
	}
	public void reset(float _x,float _y,float _vx,float _vy,float _vx1,float _vy1)
	{
		x1 = x =_x;
		y1 = y =_y;
		vx	=_vx;
		vy	=_vy;
		vx1	=_vx1;
		vy1	=_vy1;
	}
	void set(int TotalCoin)
	{
		mTotalCoin = TotalCoin;
		if(mTotalCoin<=0)
			mState = M.GAMEOUT;
		else
			mState = M.NOTHING;
		mBig = -1;
		mBat	= 0;
	}
	void update(boolean turn)
	{
		if(turn){
			x+=vx;
			y+=vy;
		}
		else
		{
			x1+=vx1;
			y1+=vy1;
		}
	}
}
