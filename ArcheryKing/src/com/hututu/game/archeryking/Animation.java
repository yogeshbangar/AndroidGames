package com.hututu.game.archeryking;

public class Animation {
	float x,y,vx,vy;
	float z,vz;
	float t,vt; 
	void set(float _x,float _y)
	{
		x  =_x;
		y  =_y;
		z  = t = 1;
		vx =(M.mRand.nextBoolean()?-.0001f:.0001f)*(M.mRand.nextInt(500)+50);
		vy =(M.mRand.nextBoolean()?-.0001f:.0001f)*(M.mRand.nextInt(500)+50);
		vz = M.mRand.nextFloat()%.08f;
		vt = M.mRand.nextFloat()%.08f;
	}
	void update()
	{
		x+=vx;
		y+=vy;
		z-=vz;
		t-=vt;
	}
}
