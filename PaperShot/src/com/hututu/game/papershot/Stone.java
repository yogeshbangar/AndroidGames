package com.hututu.game.papershot;

public class Stone {
	
	float x,y,z,vx,vy,vz;
	int counter =0;
	boolean tuch = false;
	boolean inside = false;
	boolean isNeg = false;
	
	int mScore = 0;
	GameRenderer mGR;
	public Stone(GameRenderer _GR)
	{
		mGR = _GR;
	}
	void set(float _x,float _y,float _z,float _vx,float _vy,float _vz)
	{
		x =_x;
		y =_y;
		z =_z;
		vx=_vx;
		vy=_vy;
		vz=_vz;
		tuch 	= false;
		inside 	= false;
		isNeg 	= false;
		counter =0;
		
	}
	void update()
	{
		if(x>-1.6 && x<1.6 && y>-1 && y<1 && counter < 1)
		{
			x +=vx;
			y +=vy;
			if(!tuch && !inside && vx !=0 && vy!=0)
			{
				z +=vz;
			}
			if(vy<0)
			{
				if(!isNeg)
				{
					isNeg = true;
					vx =0;
				}
				if(!tuch)
					vx+=M.mAir/700;
			}
			vy -=.004f;
			if((vy < 0 && y < mGR.mPos[mGR.mLevel].c)||x>1.5||x<-1.5||(inside&& y  < mGR.mPos[mGR.mLevel].c+.06f))
			{
//				y =-100;
				M.mAir = (float)(M.mRand.nextInt()%500)/100f;
				vx = vy = 0;
				counter = 10;
			}
		}
		counter--;
		if(counter==0)
		{
			set(0, -100, 1, 0, 0,0);
		}
	}
	boolean checkInside()
	{
		if(x>-1 && x<1 && y>-1 && y<1)
			return true;
		return false;
	}
}
