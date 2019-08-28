package com.onedaygames24.stickpowerheroes;

public class Animation {
	
	float s=1,t=0,sv,st;
	float x,y,vx,bx,by;
	int mStop=0;
	void set(float _sv)
	{
		s = 1;
	    sv =_sv;
	}
	void update()
	{
		s+=sv;
		if(s>1.8f)
		  sv=-sv;
		if(s<1)
		{
		  sv=0;
		  s=1;
		}
	}
	void set2(float _st,float _sv)
	{
		s  =  1;
		t  =  1;
	    st = _st;
	    sv = _sv;
	}
	void update2()
	{
		s+=sv;
		if(s>1.8f)
		{
	  	  sv=0;
	  	  t-=st;
	  	  if(t<.02f)
  		    t=0;
		}
	}
	
	void SetDia(float _x,float _y,float _vx)
	{
	   x  = _x;	
	   y  = _y;
	   vx = _vx;
	   mStop=0;
	   bx=by=10;
	}
	void updateDia()
	{
	    x+=vx;
		if(x<-1.2f)
		{
		  x=100;
		  y=100;
		  vx=0;
		}
	}

}
