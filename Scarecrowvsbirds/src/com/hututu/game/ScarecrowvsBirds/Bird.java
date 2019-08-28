package com.hututu.game.ScarecrowvsBirds;

public class Bird {
	float x,y,vx,vy,scal,fad;
	float ang=0,angV;
	int no;
	int type=0;
	public void set(float _x,float _y,float _vx,float _vy,float _scal,int _no)
	{
		x 	 = _x;
		y 	 = _y;
		vx   = _vx;
		vy 	 = _vy;
		scal = _scal;
		no   = _no;
		if(scal<.7f)
		  type=1;
		else if(scal>=.7f&& scal<.9f)
		  type=2;
		else
		  type =3;
	}
	public void updateBird()
	{
		y   += vy*M.Speed;
		x   += vx*M.Speed;
	}
	public void setblast(float _x,float _y,float _vy,float _scal,int _no)
	{
		x 	 = _x;
		y 	 = _y;
		vy 	 = _vy;
		scal = _scal;
		no   = _no;
		ang  = 0;
		angV = M.mRand.nextBoolean()?M.mRand.nextInt(10)+5:(-M.mRand.nextInt(10))-5;
	}
	public void updateBlast()
	{
		y   += vy*M.Speed;
		x   += vx*M.Speed;
		vy  += M.Gravity;
		ang += angV;
		
	}
	public void setPankh(float _x,float _y,float _vy,float _scal,int _no)
	{
		x   = _x;
		y   = _y;
		vy  = _vy;
		scal = _scal;
		no   = _no;
		ang  = 0;
		angV = M.mRand.nextBoolean()?M.mRand.nextInt(15)+5:(-M.mRand.nextInt(15))-5;
		fad  =1; 
	}
	public void updatePankh()
	{
		 y+=vy;
		 fad -=.025f;
		 ang += angV;
		 if(fad<.2f)
		 {
			 x=y=100;
			 vy=0;
			 fad =1;
		 }
	}
	public void reset()
	{
		x=y=-10;
		vy=0;
	}

}
