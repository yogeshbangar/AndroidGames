package com.oneday.game24.frongjumpingjump;

public class Bubble {
	
	float x=10,y=10,z,vz, trans;
	int mAnim =0,Cnt;
	boolean isShow;
	void set(float _x,float _y)
	{
		x  	 = _x;
		y  	 = _y;
		z    = 1f;
		trans = 1;
		vz = 1.01f;
		isShow =false;
		Cnt=0;
		mAnim =M.randomRangeInt(0,60);
	}
	void updateBubble()
	{
		Cnt++;
		if(Cnt> mAnim)
		{
		  z    *= vz;
		  isShow =true;
		}
		
		if(z>5f)
		{
		  set(x,y);
		}
	}
	
	

}
