package com.fun2sh.games.amazingboatracing3d;
import rajawali.Object3D;
public class Coin {
	
	float x,y,z;
	float s;
	Object3D mD2;
	int Cnt=0;
	void Set(float _x,float _y,float _z,float _s)
	{
		x = _x;
		y = _y;
		z = _z;
		s = _s;
		mD2.setVisible(false);
		mD2.setPosition(x,y,z);
		mD2.setScale(s);
		Cnt=M.mRand.nextInt()%360;
	}
	void updateCoin(float spd)
	{
		Cnt+=6;
		Cnt%=360;
	    mD2.setPosition(x,y-=spd,z);
	    mD2.setRotation(0,0,Cnt);
	    x = (float)mD2.getX();
	    y = (float)mD2.getY();
	    z = (float)mD2.getZ();
	    s = (float)mD2.getScaleX();
	}
	void DrawCoin(boolean isShow)
	{
		if(y<80 && y>0)
		   mD2.setVisible(isShow);
		else
		   mD2.setVisible(false);
	}
	

}
