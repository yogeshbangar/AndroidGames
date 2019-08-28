package com.fun2sh.games.amazingboatracing3d;
import rajawali.Object3D;
public class Player{
	float x,y,z,s;
	float vx;
	float mSpd=0;
	int   mCnt;
	Object3D mO3d;
	HTTRenderer mGR;
	public Player(HTTRenderer _mGR) {
		// TODO Auto-generated constructor stub
		mGR =_mGR; 
	}
	void Set(float _x,float _y,float _z,float _s)
	{
		x = _x;
		y = _y;
		z = _z;
		s = _s;
	    mO3d.setPosition(x,y,z);
	    mO3d.setScale(s);
	    mO3d.setRotation(0,0,0);
        mO3d.setVisible(false);
		mCnt=0;
		mSpd=0;
	}
	void DrawPlayer(boolean isShow)
	{
	  if(M.GameScreen == M.GAMEPLAY)	
	  {
			if(mGR.mCrashCnt<1)
			{
			  mO3d.setRotZ(vx*100);
			  mO3d.setRotY(-vx*50); 
			  mCnt+=5;
			  mCnt%=360;
			  mO3d.setX(x+=mGR.mCrashCnt<1?(vx):0);
		      float rad = (float)Math.toRadians(mCnt);
		      mO3d.setZ(z-=(float)(Math.sin(rad))*.015f);
		       if(x<-4.3f || x>4.3f)
				{
				   if(mGR.mCrashCnt==0)
				   {
					   mGR.mCrashCnt=1;
					   M.crashSound(HTTRenderer.mContext,R.drawable.crash);
				   }
				}
			}
			else
			{
				mO3d.setPosition(x,y+=.05f,z);
				mO3d.setRotation(mGR.mCrashCnt*10,mGR.mCrashCnt*4,mGR.mCrashCnt);
			}
	  }
	   mO3d.setVisible(isShow);
	   x = (float)mO3d.getX();
	   y = (float)mO3d.getY();
	   z = (float)mO3d.getZ();
	}
	
}
