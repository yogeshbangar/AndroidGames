package com.hututu.games.popthefootball;

public class TenP {
	int no;
	float x,y;
	float vy;
	
	void set(float _x,float _y,int _no)
	{
		x = _x;
		y = _y;
		no=_no;
		vy =0;
	}
	void update(float sy,float sdx)
	{
		if(vy !=0){
			y+=vy;
			if(vy>0)
			{
				if(sy-no*sdx<y)
				{
					y = sy-no*sdx; 
				}
			}
			if(vy<0)
			{
				if(sy-no*sdx>y)
				{
					y = sy-no*sdx; 
				}
			}
		}
	}
}
