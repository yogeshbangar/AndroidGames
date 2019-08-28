package com.fun2sh.games.amazingboatracing3d;
import rajawali.Object3D;
import rajawali.primitives.Plane;
public class Object 
{
	float x,y,z;
	float s,sy,sz;
	Object3D mObj3d;
	Plane mImg;
	void Set(float _x,float _y,float _z,float _s)
	{
		x = _x;
		y = _y;
		z = _z;
		s = _s;
		mObj3d.setVisible(false);
		mObj3d.setPosition(x,y,z);
		mObj3d.setScale(s);
		
	}
	void DrawObj(boolean isVisible)
	{
		if(y<80 && y>-1f)
			mObj3d.setVisible(isVisible);
		else
			mObj3d.setVisible(false); 
	}
	void Update(float spd)
	{
	    mObj3d.setPosition(x,y-=spd,z);
		x = (float)mObj3d.getX();
	    y = (float)mObj3d.getY();
	    z = (float)mObj3d.getZ();
	    if(y<-2)
	      x=y=100; 	
	}
	
	void SetCloud(float _x,float _y,float _z,float _sx,float _sy,float _sz)
	{
		x  = _x;
		y  = _y;
		z  = _z;
		s  = _sx;
		sy = _sy;
		sz = _sz;
		mImg.setPosition(x,y,z);
		mImg.setScale(s,sy,sz);
		mImg.setRotation(90,0,180);
	}
	void DrawCloud(boolean isVisible)
	{
		  mImg.setVisible(isVisible);
	}
	void updateCloud(float spd)
	{
		mImg.setPosition(x,y,z+=spd);
        mImg.setScaleX(s+=spd*.4f);
		x   = (float)mImg.getX();
	    y   = (float)mImg.getY();
	    z   = (float)mImg.getZ();
	    s   = (float)mImg.getScaleX();
	}
}
