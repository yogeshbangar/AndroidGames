package com.hututu.game.rescuemission;

public class Animation {
	float x,y,vx,vy;
	int obj=0;
	void set(float _x,float _y)
	{
		x =_x;
		y =_y;
		vx = (M.mRand.nextBoolean()?-1:1)*M.mRand.nextInt(1000)*.0001f;
		vy = (M.mRand.nextBoolean()?-1:1)*M.mRand.nextInt(1000)*.0001f;
		obj= M.mRand.nextInt(4);
	}
	void update()
	{
		x+=vx;
		y+=vy;
		vy-=.01f;
	}
	void ufo()
	{
		float i = .5f;
		if(x>.8 && vy == 0 && vx > 0){
			vx = -.10f*i;
			vy = -.04f*i;
		}
		if(x<-.8 && vy < 0 && vx <0 ){
			vx = 0.10f*i;
			vy = 0.04f*i;
		}
		x+=vx;
		y+=vy;
	}
}
