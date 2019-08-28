package com.hututu.game.ScarecrowvsBirds;

public class menu {
	float x,y,vy,ang=0;
	int Open=0;
	public void set(float _x,float _y,float _vy,int _open)
	{ 
		x  = _x;
		y  = _y;
		vy = _vy;
		Open = _open;
	}
	void update()
	{
		if(Open ==1)
		{
		  if(y<-.15f)
		  {
		     y+=vy;
		     ang+=6.5f;  
		  }
		  
		}
		if(Open ==0)
		{
		  if(y>-1.5f)
		  {
		     y +=vy;
		     ang-=6.5f;
		  }
		}
	}

}
