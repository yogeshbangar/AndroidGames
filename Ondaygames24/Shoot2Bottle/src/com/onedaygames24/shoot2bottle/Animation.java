package com.onedaygames24.shoot2bottle;

public class Animation {
	
	float x,y,vx,vy;
	int img =0;
	int rotetion = 1;
	void set(float _x,float _y,float _vx,float _vy,int _img)
	{
		x =_x;
		y =_y;
		vx =_vx;
		vy=_vy;
		img =_img;
		rotetion = M.mRand.nextBoolean()?M.mRand.nextInt(10)+5:-(M.mRand.nextInt(10)+5);
	}
	void update()
	{
		x +=vx;
		y +=vy;
		vy -= .002f;
		if(y < .25f)
		{
			vx = vy = rotetion = 0;
		}
	}
	void updateT()
	{
		x +=vx;
		y +=vy;
		vy += .002f;
	}

}
