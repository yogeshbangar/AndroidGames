package com.hututu.game.stuntracingcar;

public class Strip {
	float x,y,z,vx,vy,vz;
	void Set(float _y,float _z,float _vy,float _vz)
	{
		x 	= 0;
		y  	=_y;
		z  	=_z;
		vx 	= .0001f;;
		vy 	=-.0001f;
		vz 	= .0001f;
		
	}
	void update()
	{
		if(M.speed > 1)
		{
			x+=vx;
			y+=vy;
			z+=vz;
			vx*=(M.speed);
			vy*=(M.speed);
			vz*=(M.speed);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
