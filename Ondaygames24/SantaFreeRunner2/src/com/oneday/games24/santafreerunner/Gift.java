package com.oneday.games24.santafreerunner;

public class Gift {
	float x,y;
	int no;
	void set(float _x,float _y,int _no)
	{
		x =_x;
		y =_y;
		no = _no;
		if(y>.8)
			y=.8f;
	}
}
