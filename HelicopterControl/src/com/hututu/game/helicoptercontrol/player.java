package com.hututu.game.helicoptercontrol;

public class player
{
   float x,y,vy,vx;
   float gravity;
   public static float mPlyerang=0;
   int Time;
   float scal,fadeVal;
   public static float VY=0.004f; 
   public void set(float _x,float _y,float _vy)
   {
	 x  = _x;   
	 y  = _y;
	 vy = _vy;
	 gravity=0;
	 mPlyerang=0;
   }
   public void setObject(float _x,float _y,float _vx)
   {
	 x  = _x;   
	 y  = _y;
	 vx = _vx;
   }
   void SetSmoke(float _x,float _y,float _scal,float _fadeVal,int time)
    {
		x    = _x;
		y    =  _y;
		Time = time;
		scal = _scal;
		fadeVal = _fadeVal;

	}
   public void update()
   {
	   if(vy>0)
	     gravity =.0035f;
	   if(vy<0)
	     gravity =-.0035f;
	   
	   y  +=vy;
	   vy +=gravity;
       if(M.GameScreen == M.GAMEPLAY)
	   {
		   if(vy>0)
		   {
             if(mPlyerang>=-25.0f)  			   
			    mPlyerang -=2.55f;
		   }
		   if(vy<0)
		   {
		     if(mPlyerang<=25.0f)
			    mPlyerang +=2.55f;
		   }
		   
	   }
   }
   public void Reset()
   {
	   y=-100;
	   x=-100;
	   vx=0;
   }
}
