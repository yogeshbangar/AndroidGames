package com.onedaygames24.shoot2bottle;

public class Bottle {
	
	boolean isBlast;
	
	int type = 0,Sheld=0;
	int WakeUpCounter;
	int spark = 0;
	int shieled = 0;
	
	float x,y;
	float dy,dvy;
	void set(float _x,float _y,int _type)
	{
		x = _x;
		y = _y;
		isBlast = false;
		Sheld = 0;
		type=_type;
		WakeUpCounter = 0;
		dy=dvy=spark=shieled= 0;
	}
	void update()
	{
		WakeUpCounter --;
		if(WakeUpCounter == 0)
		{
			isBlast =false;
			dy=dvy=spark = 0;
			type = M.mRand.nextInt(GameRenderer.mStart.mGR.mTex_Yog.length);
		}
	}

}
