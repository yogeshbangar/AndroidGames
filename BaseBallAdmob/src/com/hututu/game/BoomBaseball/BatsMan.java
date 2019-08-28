package com.hututu.game.BoomBaseball;

public class BatsMan {
	float x,y;
	boolean isBatmanHit;
	float BatAngle=0,angleCount;
	int mBatsManAni=0;//,StrikeCount
	GameRenderer mGR;
	
	public BatsMan(GameRenderer _mGR){
	
		mGR = _mGR;
	}
	public void Set(float _x,float _y)
	{
		x = _x;
		y = _y;
		mBatsManAni=0;
		isBatmanHit = false;
		angleCount =0;
	}
	void Update()
	{
		  if(isBatmanHit)
		  {
		    if(mBatsManAni<mGR.mTex_Player.length-1)
			      mBatsManAni++;
		   else
		     {
			    isBatmanHit =false;
			    mBatsManAni=0;
 		     }
		  }
		  if(mBatsManAni<4)
		  {
 			 BatAngle =-30;
 			 angleCount=0;
		  }
		  else
		  {
			  if(BatAngle<=120.0f)
			  {
			    angleCount++;
			    BatAngle +=angleCount*9f;
			  }
		  }


	}

}
