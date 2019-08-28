package com.onedaygames24.stickpowerheroes;

public class Danda {
	float x,y,s,sv;
	float vx,width;
 	float ang=-90,angInc;
 	int mStop=0;
 	int mAction=-1,mDelay;
 	GameRenderer mGR;
 	public Danda(GameRenderer _mGR) {
	
 		mGR = _mGR;
	}
 	void Set(float _x,float _sv)
 	{
 		x   = _x;
 		y   = -.419f;
 		s   =  0;
 		ang = -90;
 		sv  = _sv;
 		angInc=0;
 		vx  = 0;
 		width =0;
 		mStop =0;
 		mDelay=0;
 	}
 	void update()
 	{
 		switch(mAction)
 		{
 		  case 0:
 			     s +=sv;
 			     M.LineDrawSound(GameRenderer.mContext,R.drawable.linedraw);
 			   break;
 		  case 1:
 			   mDelay++;
			   if(mDelay>10)
			   {
			     ang   +=angInc;
			     angInc+=1.2f;
	 		     if(ang>0)
		    	    ang=0;
			   }
 			  break;
 		  case 2:
 			   x +=vx;
 			  break;
 		  case 3:
 			  	ang   +=angInc;
			    angInc+=2.5f;
	 		    if(ang>90)
		    	  ang=90;
 			  break;
 		}
 		float ratio=M.ScreenWidth/M.ScreenHieght;
		final float conR  =.608f-ratio; 
		final float conSv =.459f+conR;
 		if(ang==0)
		  width = s*conSv;
 	}
 	void reset()
 	{
 		mAction=-1;
 		angInc=s=vx=sv=0;
 		width=0;
 		ang=-90;
 	}
}
