package com.hututu.game.MotoTrafficControl;

public class Animation {
	float x,y,z,t;
	float vx,vy,vz;
	float dt=.1f;
	float ang; 
	GameRenderer mGR;
	public Animation(GameRenderer _mGR)
	{
		mGR  = _mGR;
		y    = x = -100;
	}
	void SetBoost(float _x,float _y,float _vx,float _vy,float _ang)
	{
		x	= _x;
		y	= _y;
		vx  = _vx;
		vy  = _vy;
		z   = .75f;
		t   = 1f;
//		vx  = vx*.01f;
//	    vy  = vy*.01f;
		dt  =.0625f;
		ang = _ang;
//		System.out.println("vx=============== "+vx+" vy=================  "+vy);
	}
	void UpdateBoost()
	{
//		x  -=vx/2f;
//		y  -=vy/2f;
		
//		if(ang !=90 && ang!=270)
//		{
//		  if(vx>0)
//	        vx +=.002f/2f;
//	      else
//		    vx -=.002f/2f;
//		}
//		if(ang !=0 && ang!=180)
//		{
//		  if(vy>0)
//		    vy +=(.002f*1.56f)/2f;
//		  else
//		    vy -=(.002f*1.56f)/2f;
//		}
		
		if(M.GameScreen == M.GAMEPLAY)
		{
	       z*=1.08f;
//	       z+=.025f; 
	      if(t>=0f)
	      {
		    t-=dt;
//		    t-=.025f;
			dt+=.01f;
	      }
	      else
	    	x=y=-100;
		}
		else
		{
  		   z*=1.035f;
	      if(t>=0f)
		    t-=dt;
	      else
	  	   x=y=-100;
		  dt+=.003f;
		}

	}
	void setAchie(float _x,float _y,float Cnt)
	{
	   x    = _x;
	   y    = _y;
	   z    = 1f;
	   vz   =  1.2f;
	   ang   = Cnt/2;
	}
	
	void updateAchie()
	{
		if(y>=.01f)
		  y*=.85f;
		ang--;
		if(ang<=0)
		{
		   z*=vz;
		   if(z>2)
			vz =.8f;
		   if(z<=.01f)
		    setAchie(100,100,0);
		}
	}
	void SetTap(float _x,float _y)
	{
	   x    = _x;
	   y    = _y;
	   z    =  1f;
	   vz   = .9f;
	   
	}
	
	void UpdateTap()
	{
	   z*=vz;
	   if(z<0)
	    SetTap(100,100);
	}
}
