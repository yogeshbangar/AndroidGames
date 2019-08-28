package com.fun2sh.games.amazingboatracing3d;
import rajawali.Object3D;
public class Opponent {
	
	float x,y,vx,z;
	float s,sx,mSpd;
	boolean isCollide;
	Object3D mO3d;
	int mTurn=0,mTurnCnt,mCnt;
	HTTRenderer mGR;
	public Opponent(HTTRenderer _mGR) {
		// TODO Auto-generated constructor stub
		mGR =_mGR;
	}
	void Set(float _x,float _y,float _z,float _s)
	{
		sx = x = _x;
		y = _y;
		z = _z;
		s = _s;
		mO3d.setVisible(false);
		mO3d.setPosition(x,y,z);
		mO3d.setScale(s);
		mSpd  =  .4f;//M.randomRangeInt(3,5)*.1f;
		mTurn =  M.randomRangeInt(0,250);
		vx=0;
		mTurnCnt =0;
		isCollide =false;
		mO3d.setRotation(0,0,0);
		mCnt =M.mRand.nextInt()%360;
//		System.out.println("                "+mTurn+"           "+mSpd);
	}
	void updateOpp(float spd)
	{
	    if(mTurn<=50)
	    {
	       if(y<22 && mTurnCnt==0)
    	   {
	    	   if(x<0)
    			 vx = .06f*mGR.mPlayer.mSpd;
    	       else if(x>0)
	    	     vx =-.06f*mGR.mPlayer.mSpd;
    	       else
    	       {
    	    	   if(mGR.mPlayer.x>x)
    	    		   vx = .06f*mGR.mPlayer.mSpd;
    	    	   else
    	    		   vx = -.06f*mGR.mPlayer.mSpd;
    	       }
	    	   mTurnCnt++;
	       }
	       if(Math.abs(sx-x)>2.5f)
	       {
	    	 vx=0;
	    	 mTurn=100;
	       }
	       x+=vx;
	       mO3d.setRotZ(vx*100);
	       mO3d.setRotY(-vx*50);
	    }
	    if(isCollide)
		{
	    	mO3d.setRotation(mGR.mCrashCnt*8f,mGR.mCrashCnt*3,-mGR.mCrashCnt);
		}
	    mCnt+=5;
	    mCnt%=360;
	    float rad = (float)Math.toRadians(mCnt);
	    mO3d.setPosition(x,y-=(!isCollide?spd:-.1f),z-=(float)(Math.sin(rad))*.018f);
	    x = (float)mO3d.getX();
	    y = (float)mO3d.getY();
	    z = (float)mO3d.getZ();
	}
	void DrawOpp(boolean isShow)
	{
		if(y<75 && y>-1)
		   mO3d.setVisible(isShow);
		else
		   mO3d.setVisible(false);
	}

}
