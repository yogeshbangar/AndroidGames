package com.hututu.game.TrainCrash;

public class Train {
	
	float x,y,vx,vy,ang,rad;
	float mudna=0,speed=1f;
	float velo=.006f,rotVal;
	boolean isMove=false,isCollide;
	int dir=0,rCnt=0,no;
	void set(float _x,float _y,float _turn,int _no)
	{
		x       =  _x;
		y       =  _y;
		ang     = _turn;
		mudna   =  0;
	    rad     = (float)Math.toRadians(ang);
		vx      = (float)Math.cos(rad)*(velo*1f);
		vy      = -(float)Math.sin(rad)*(velo*1.77f);
		isMove  = false;
		speed	= 1f; 
		no 		= _no;
		rCnt    =  0;
		isCollide = false;
	}
	
	void update()
	{
		rad 	= (float)Math.toRadians(ang);
		vx      = (float)Math.cos(rad)*(velo*1);
		vy      = -(float)Math.sin(rad)*(velo*1.77f);
		String s ="";
		s  = vx+"";
   	   if(s.contains("E"))
  	     vx=0;
	 	s = vy+"";
	  if(s.contains("E"))
 	     vy=0;
	  
		x 		+=(vx*speed);
		y 		+=(vy*speed);
		if(isMove)
		{
		  TrainTurn();
		}

	}
	void setMove(boolean _move,float _mudna,float val)
	{
		if(speed>0)
		{
			isMove  = _move; 
			mudna   = ang+_mudna;
//			dir     = _dir;
			if(_mudna>0)
				dir=1;
			else
				dir=2;
			if(val==3.85f)
				val=4.75f;
			if(val==7f)
				val=7.5f;
			rotVal  = val;
			
		}
	}
	void TrainTurn()
	{
		if(speed>0)
		{
			if(dir==1)
			{
				if(ang<mudna)//Positive
				{
				   ang+=(rotVal*speed);
				}
			}
			if(dir==2) //Negative
			{
				if(ang>mudna)
				{
				   ang-=(rotVal*speed);
				}
			}
		}
		
	}
	void SlowSpeed(float slw)
	{
		speed -=slw;
		if(speed<0)
		  speed=0;
	}
	void resetMudna()
	{
		if(speed>0)
		{
			ang    = mudna;
		    isMove = false;
		    mudna  = 0;
		    dir    = 0;
		    rCnt   = 0;
		}
	}
	void ResetTrain()
	{
		x=y=100;
		speed=0;
		vx=vy=0;
		dir =rCnt=0; 
	}
}
