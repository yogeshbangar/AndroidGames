package com.oneday.games24.crisisofrail;

public class Arrow {
	
	float x,y,ang,origAng;
	float mudna=0;
	int Open=0,mdir;
	boolean isMove,isNegative;
	
	void set(float _x,float _y,float _ang,boolean _isNeg)
	{
		x	= _x;
		y 	= _y;
		origAng = ang = _ang;
		mudna = 0;
		Open  = 0;
//		mMove = 0;
		isNegative = _isNeg;
		isMove  = false;
		mdir    = 0;
	}
	void setMudna(float _mudna, int _dir)
	{
		mudna  = ang+_mudna;
		mdir   = _dir;
		isMove = false;
	}
	void update()
	{
		  if(mdir==1) //On
		  {
			if(ang<mudna)
			{
			   ang+=8;
			   isMove =true;
			}
			else
			{
			  ang    = mudna;
			  isMove = false;
			}
			  
		  }
		  if(mdir==2) //OFF
		  {
			  if(ang>mudna)
			  {
				  ang-=8;
				  isMove =true;
			  }
		  	  else
		  	  {
				 ang    = mudna;
				 isMove = false;
		  	  }
		  }
	}
	void Reset()
	{
		x =y=100;
		mdir=Open/*mMove*/=0;
		isMove =false;
		ang=0;
	}

}
