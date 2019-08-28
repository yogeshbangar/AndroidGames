package com.hututu.game.galaxyhunt;

public class Animation {
	float x,y,z,vx,vy,vz;
	int cl;
	void set(float _x,float _y,float _z,float _vx,float _vy,float _vz)
	{
		x = _x;
		y = _y;
		z = _z;
		vx=_vx;
		vy=_vy;
		vz=_vz;
		cl = M.mRand.nextInt(3);
	}
	void update()
	{
		x+=vx;
		y+=vy;
		z+=.03f;
		vz-=.03f;
		if(x>1||x<-1||y>1||y<-1||vz<.2f)
		{
			y = x = -100;
			vx=vy=vz=0;
		}
	}
}
