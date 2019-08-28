package com.hututu.game.BoomBaseball;

public class Animation {
	float x,y,vx,vy,z;
	float t,inc=.06f;
	int No=0,AniCount;
	boolean isVisible;
	GameRenderer mGR;
	public Animation(GameRenderer _mGR)
	{
		mGR  = _mGR;
		y= x = -100;
	}
	void set(float _x,float _y,float _vx,float _vy)
	{
		z  = 1f;
		x  = _x;
		y  = _y;
		t  = 1;
		vx = _vx;
		vy =_vy;
		No = M.mRand.nextInt(7);
	}
	void update()
	{
		x  +=vx;
		vy -=.002043f;
		y  +=vy;
		z  -=.002f;
		if(z<.1f)
		{
			x=y=-100;
			vy=0;
		}
	}
	void SetEffect(float _x,float _y)
	{
		x  = _x;
		y  = _y;
		AniCount =0;
		No = 0; 
	}
	void updateEffect()
	{
		AniCount++;
		if(AniCount%3==0)
			No++;
		if(No>4)
		{
	      x =y=-100;
	      No =0;
		}
	}
	void SetBallon(float _x,float _y)
	{
		x  = _x;
		y  = _y;
	 	isVisible=false;
	}
	void updateBallon()
	{
	  No++;
	  if(No%2==0)
	    AniCount++;
	  if(AniCount<20)
	  {
		if(AniCount>18)  
		{
		   float x[] = {-.609f,-.319f,-.04f,.429f,.679f};
		   int no    = M.mRand.nextInt(x.length);
//		   System.out.println("No=============  "+no);
		   SetBallon(x[no],-1);
		}
	  }
	  if(AniCount>50)
	  {
		 if(y<=.48f-mGR.mTex_Ballon.Height()*.45f)
		    y+=.005f;
		 else
		 	 isVisible =true;
		   t +=inc;
		  if(t>10)
		    inc =-.06f;
		  if(t<-10)
		    inc =.06f;
	  }
	   	
	}
	void settail(float _x,float _y,float _z)
	{
		x	= _x;
		y	= _y;
		z = _z;
	}
	void updatetail()
	{
		if(z>.2f)
			z-=.1f;
		else
			x=y=-100;
	}
	
}
