package com.hututu.game.archeryking;

public class Target {
	int Color,Power;
	float x,y,hit,ty,tvy;
	
	public Target()
	{
		ty = 100;
	}
	
	void set(float _x,float _y)
	{
		x	=_x;
		y	=_y;
	}
	void setColor(float _hit)
	{
		hit=_hit;
		if(Math.abs(hit)<.060f)
			Color =0;
		else if(Math.abs(hit)<.120f)
			Color =1;
		else if(Math.abs(hit)<.180f)
			Color =2;
		else if(Math.abs(hit)<.240f)
			Color =3;
		else if(Math.abs(hit)<.300f)
			Color =4;
		else
			Color =5;
//		Color =0;
		if(Color!=5)
		{
			Power++;
			if(Power<4)
				Power = 4;
			if(Power>13)
				Power = 4;
		}
		else
		{
			Power--;
			if(Power>3)
				Power=3;
			if(Power<0)
				Power = 0;
			
		}
		ty = 0f;
		tvy=.001f;
	}
	void update()
	{
		ty+=tvy;
		tvy+=.001f;
	}
}
