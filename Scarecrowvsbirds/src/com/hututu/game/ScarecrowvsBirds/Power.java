package com.hututu.game.ScarecrowvsBirds;

public class Power
{
	float x,y,vx,vy;
	boolean isFire;
	double ang=Math.toRadians(180);
	int PowerCount,PowerType;
	public void set(float _x,float _y,float _vy,int _type)
	{
	  x  = _x;
	  y  = _y;
	  vy = _vy;
	  PowerType = _type;
	  PowerCount=0;
	}
   public void update()
   {
	   y += vy;
      if(y<-1.2f)
      {
    	  y=100;
    	  vy=0;
      }
   }
   public void setPower(float _x,float _y,float _vx,float _vy)
	{
		x  = _x;
		y  = _y;
		vx = _vx;
		vy = _vy;
	}
   public void updatePowerArrow()
	{
		x   += vx;
		y   += vy;
		vy  += M.Gravity;
		ang = (float)Math.atan(vy/vx)-Math.toRadians(180);
		if(x>1.5||x<-1.5f||y>2f||y<-2f)
		{
			ResetArrow();
		}
	}
   public void ResetArrow()
  	{
  	    setPower(10,10,0,0);
  		ang=Math.toRadians(180);
  		isFire = false; 
  	}
   public void updatePower()
   {
	    x +=vx;
	    if(x<=0)
	     vx =-	vx;
	    if(x>=.8f)
	      vx =-vx;
		y+= vy;
		if(y>.5f)
		  vy=-vy;
		if(y<=-.8f)
	 	 vy =-vy;
   }
  
}
