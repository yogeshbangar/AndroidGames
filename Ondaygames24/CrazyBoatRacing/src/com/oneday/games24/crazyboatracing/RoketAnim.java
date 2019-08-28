package com.oneday.games24.crazyboatracing;

public class RoketAnim {
	int img;
	float x,y,z,vy;
	
	void set(float _x,float _y)
	{
		x =_x;
		y = _y;
		z = 1;
		img =0;
		vy = (M.mRand.nextInt(2)+3)*.01f;
		y -=vy;
	}
	void update()
	{
		img++;
		y-=vy;
		z-=.1f;
		if(z<.1){
			x = -100;
		}
	}
	void update2()
	{
		img++;
		y+=vy;
		z-=.1f;
		if(z<.1){
			x = -100;
		}
	}
}
