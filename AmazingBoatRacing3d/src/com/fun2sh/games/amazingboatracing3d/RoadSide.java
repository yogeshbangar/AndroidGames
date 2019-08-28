package com.fun2sh.games.amazingboatracing3d;

import rajawali.Object3D;

public class RoadSide {
	float x,y,z,s;
	Object3D mObj;
	void Set(float _x,float _y,float _z,float _s)
	{
		x = _x;
		y = _y;
		z = _z;
		s = _s;
		mObj.setPosition(x,y,z);
		mObj.setScale(s);
		mObj.setVisible(false);
	}
	void DrawObj(boolean isVisible)
	{
		  mObj.setVisible(isVisible);
	}
    void UpdateObj(float spd)
    {
   	    mObj.setPosition(x,y+=spd,z);
   	    x = (float)mObj.getX(); 
        y = (float)mObj.getY();
    	z = (float)mObj.getZ();
    }
	
}
