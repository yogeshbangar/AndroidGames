package com.oneday.games24.highwayextreamracing;

public class Player {
	boolean mPower[] = new boolean[3]; 
	
	int dir;
	int len;
	int blast = -1;
	int life = 0;
	int collide;
	
	float x,y;
	float vx;
	
	
	void set(int _len,float _y,float _vx,int _life)
	{
		x = -0.93f+0.13f*_len;
		y = _y;
		vx=_vx;
		dir =0;
		len =_len;
		blast = -1;
		life = _life;
		collide = 3;
		for(int i=0;i<mPower.length;i++)
			mPower[i] = false;
		
	}
}
