package com.Oneday.games24.peguinadventure;

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
	void update()
	{
		if(vy !=0){
			y+=vy;
			if(vy>0)
			{
				if(.28-no*.28f<y)
				{
					y = .28f-no*.28f; 
				}
			}
			if(vy<0)
			{
				if(.28-no*.28f>y)
				{
					y = .28f-no*.28f; 
				}
			}
		}
	}
}
