package com.oneday.games24.crisisofrail;

public class MidPoint {
	float x,y,vx,vy;
	void Set(float _x1,float _y1,float _x2,float _y2,float _vx1,float _vy1,float _vx2,float _vy2)
	{
		x  = (_x1+_x2)/2f;
		y  = (_y1+_y2)/2f;
		vx = (_vx1+_vx2)/2f;
		vy = (_vy1+_vy2)/2f;
	}
	void Set2(float _x1,float _y1,float _vx1,float _vy1)
	{
		x  = _x1;
		y  = _y1;
		vx = _vx1;
		vy = _vy1;
	}

}
