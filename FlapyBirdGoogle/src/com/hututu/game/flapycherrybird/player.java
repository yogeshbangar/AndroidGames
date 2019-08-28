package com.hututu.game.flapycherrybird;

public class player
{
   float x,y,vy,vx;
   float gravity=-.006f;
   public static float mPlyerang=0;
   public static float VY=0.05f; 
   public void set(float _x,float _y,float _vy)
   {
	  x  = _x;   
	  y  = _y;
	  vy = _vy;
	  mPlyerang=0;
   }
   
   public void update()
   {
	   y  +=vy;
	   vy +=gravity;
       if(M.GameScreen == M.GAMEPLAY)
	   {
		   if(vy<0)
		   {
             if(mPlyerang>=-25.0f)  			   
			     mPlyerang -=2.55f;
		   }
		   if(vy>0)
		   {
		     if(mPlyerang<=25.0f)
			     mPlyerang +=2.55f;
		   }
		   
	   }
   }
}
