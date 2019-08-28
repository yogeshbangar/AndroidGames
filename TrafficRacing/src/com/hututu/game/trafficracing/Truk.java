package com.hututu.game.trafficracing;

public class Truk {
	float x,y,z;
	float vx,vy,vz;
	float sx,vsx;
	int no=0;
	public Truk(int i)
	{
		set();
		if(i ==1)
		{
			y	=-1.49f;
			vy	= -.010f;
		}
	}
	void update()
	{
		x+=vx;
		y+=vy;
		z+=vz;
		vx*=sx;
		vy*=sx;
		vz*=sx;
		if(sx < 1.2){
			sx+=.001f;
			vsx+=.0001f;
		}
		if(y<-2.5){
			set();
			if(M.mRand.nextBoolean())
				M.sound3Play(GameRenderer.mContext, R.drawable.car_pass);
			else
				M.sound4(GameRenderer.mContext, R.drawable.car_pass1);
		}
	}
	void set()
	{
		sx = 1;
		vsx = 0;
		x = -.005f;
		y = 0.450f;
		z = 0.050f;
		vx= -.0009f;
		vy= -.0010f;
		vz= 0.0016f;
		no = M.mRand.nextInt(7);
	}
}
