package com.hututu.game.shootthebottle;

public class Stone {
	float x,y,z,vx,vy,vz;
	boolean isUsed;
	void set(float _x,float _y,float _z,float _vx,float _vy,float _vz)
	{
		x =_x;
		y =_y;
		z =_z;
		vx=_vx;
		vy=_vy;
		vz=_vz;
		isUsed = false;
	}
	void update()
	{
		if(x>-1 && x<1 && y>-1 && y<1)
		{
			x +=vx;
			y +=vy;
			z +=vz;
			vy -=.004f;
			if(vy < 0 && y <-.22f)
				y =-100;
		}
	}
	boolean checkInside()
	{
		if(x>-1 && x<1 && y>-1 && y<1 && !isUsed)
			return true;
		return false;
	}
}
