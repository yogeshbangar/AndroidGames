package com.hututu.game.TrainCrash;

public class Smoke {
	
	float x,y,vx,vy,z,t;
	float VY;
	int Cnt,no;
	public Smoke() 
	{
		x=y=100;
	}
	void Set(float _x,float _y,float _vy)
	{
		x = _x;
		y = _y;
		z = .65f;
		t = 1.2f;
		VY =_vy;
		vx = (M.mRand.nextInt(4))*(M.mRand.nextBoolean()?-.001f:0.001f);
		if(VY>0)
		  vy = (M.mRand.nextInt(5)+15)*-.0006f;
		else
		  vy = (M.mRand.nextInt(5)+15)*.0006f;
		Cnt=0;
	}
	void update()
	{
		x+=vx;
		y+=vy;
		z+=.05f;
		t-=.06f;
		if(t<.05f)
		{
		  x=y=100;
		}
	}
	float r,g,b,sy;
	void SetParticle(float _x,float _y)
	{
		x  = _x;
		y  = _y;
		z  = 1;
		t  = 1f;
		vx = (M.mRand.nextInt(5)+1)*(M.mRand.nextBoolean()?-.0015f:0.0015f);
	    vy = (M.mRand.nextInt(10)+1)*.001f;
		Cnt=0;
		no = M.mRand.nextInt(11);
		r  = M.mRand.nextInt(255)/255f;
		g  = M.mRand.nextInt(255)/255f;
		b  = M.mRand.nextInt(255)/255f;
	}
	void updateParticle()
	{
		if(Cnt<15)
		{
		   y+=vy;
		   vy+=.001f;
		}
		else
		{
		  x+=vx;
		  y+=vy;
		  vy-=.001f;
		  z-=.02f;
//		  t-=.03f;
		  if(z<0f)
		    x=y=100;
		}
		Cnt++;
	}
	void SetFlag(float _x,float _y,float _r,float _g,float _b)
	{ 
		 x =_x;
		 y =_y;
		 r = _r/255f;
		 g = _g/255f;
		 b = _b/255f;
		 Cnt=0;
	}
}
