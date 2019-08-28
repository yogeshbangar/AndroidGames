package com.oneday.games24.speeddragracer;

public class Animation {
	float y,vy;
	int counter = 0;
	int No;
	public Animation()
	{
		y=100;
	}
	void update()
	{
		counter --;
		if(counter<0)
		{
			y+=vy;
			vy+=.001f;
		}
	}
	void set(float _y, int no)
	{
		y =_y;
		counter = 10;
		No = no;
		vy = 0;
	}
}
