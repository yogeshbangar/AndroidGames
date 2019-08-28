package com.hututu.game.BoomBaseball;

public class Ball
{
	float x,y,z;
	float vx,vy,grav=-.00295421f;
	boolean isHome,isTouch,isSky =false;
	int Reset;
	int TapCount=0;
	GameRenderer mGR;
	public Ball(GameRenderer _mGR)
	{
         mGR = _mGR;
	}
	public void Set(float _x,float _y,float _vx,float _vy)
	{
		x  = _x;
		y  = _y; 
		vx = _vx;
		vy = _vy;
		isHome   = false;
		isTouch  = false;
		isSky 	 = false;
		grav=-.00295421f;
		TapCount=0;
	}
	void reset()
	{
		vx =0;vy=0;
		grav=0;
		TapCount =0;
	    y=-100;
	}
	void update()
	{
	 	
	  if(mGR.isHit)
	  {
	    if(mGR.vy>.05f)// Check home
	    {
		   isHome = true;
           if(y>.75f || vy <-.03f)
        	  isSky = true;
           if(mGR.vy>.075f && vy>.02f)
  	    	  M.BallSwingSound(GameRenderer.mContext,R.drawable.ballswing);
           if(vy<-.04f)
        	   y=-100;
           if(z>=.45f)
 	          z -=.025f;
 	       vy  += grav;
 	       y   += vy;
 	       
	    }
	    if(!isHome && mGR.vy<=.05f) // Check Middle
	    {
	    	if(isTouch)
	    	{
    		    if(y>-.51f)
    		    {
    		      if(vy<-0.018f/(1+TapCount))
    		      {
   		              vy =.016f;
//   		              System.out.println("vy  ==========iffffff==========     "+vy);
   		             TapCount++;    		             
    		      }
    			   vy += grav;
    	           y  += vy;
    	           if(TapCount>1)
       		    	{
    	        	   reset();
       		    	}
    		    }
	    	}
	    	else
			   {
	    		 if(vy<-0.025f/(1+TapCount))
	    		 {
	    		   TapCount++;
				   vy  = 0.035f;
//				   System.out.println("vy  ===========elseeeeee=========     "+vy);
				 }
	    		 if(TapCount>2)
				   {
					   reset();
				   }
	    		 vy  += grav;
	             y  += vy;
			   }
	        if(z>=.45f)
	          z -=.025f;
	        
    	 }
	     if(mGR.vx<=-0.005f ||mGR.vx>=0.005f)
	     {
	    	 vx -=(mGR.vx)/50.0f;
	         if(mGR.vx>0)
	         {
	          if(vx<=0)
	        	 vx+=(mGR.vx)/30.0f;	
	         }
	         if(mGR.vx<0)
	         {
	        	if(vx>=0)
	        	 vx+=(mGR.vx)/30.0f;	
	         }
	     }
	     x  += vx;
	   }
     else
	    {
		   z   = Math.abs(-1+(y))*.82f;
		   vy  +=grav;
		   x   +=vx;
		   y   +=vy;
	    }
	}
	

}














//if(/*!isHome && mGR.vy>.05f ||*/ isTouch) // Check Touch
//{
//	if(vy<=-(0.02f/(1+TapCount)))
//	   {
//		  TapCount++;
//		  vy   =(.02f/TapCount)+.02f;
//		  vx   = vx<0?-vy/2:vy/2;
//		  y   -=vy*2.0f;
//		  if(TapCount>2)
//		   {
//			  reset();
//			  y=-100;
//			  TapCount =0;
//		   }
//	   }
//	
//}
// 