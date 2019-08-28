package com.oneday.games24.crazyboatracing;

public class Fire {
	int i,count;
	float x,y,z,vx,vy,vz;
	float ang=1.57f;
	void update()
	{
		x+=vx;
		y+=vy;
	}
	void reset()
	{
		x = y =vx =vy=0;
		i =-1;
		count = 0;
		ang = 1.57f;
	}
	void set(float _x,float _y)
	{
		x = _x;
		y = _y;
		z = 1;
		vz = .5f;
		count = 1;
		ang = 1.57f;
	}
}
