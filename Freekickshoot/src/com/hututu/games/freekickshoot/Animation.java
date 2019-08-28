package com.hututu.games.freekickshoot;

public class Animation {
	float x,y,vx,vy;
	int no;
	void set(float _x,float _y)
	{
		x =_x;
		y =_y;
		vx = (M.mRand.nextBoolean()?-.0002f:.0002f)*(M.mRand.nextInt(90)+10);
		vy = (M.mRand.nextBoolean()?-.0002f:.0002f)*(M.mRand.nextInt(90)+10);
		no = 40;
	}
	void update()
	{
		no --;
		x+=vx;
		y+=vy;
	}
}
