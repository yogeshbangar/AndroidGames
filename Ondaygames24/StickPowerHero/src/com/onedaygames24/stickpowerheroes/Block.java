package com.onedaygames24.stickpowerheroes;

public class Block{
	
	float x,y,s,width;
	float vx,dis;
	boolean isStop,isDisCmp;
	GameRenderer mGR;
	public Block(GameRenderer _mGR) {
		mGR =_mGR;
	}
	void Set(float _x,float _s,float _dis)
	{
		x 	     =  _x;
		y 	     =  -.709f;
		s 	     =  _s;
		width    =  (mGR.mTex_Block.width()*s)*.409f;
		dis      =  _dis;
		isStop   =  false;
		isDisCmp =  false;
		if(s>1)
		   s=1;
	}
	void update()
	{
  	    x += vx;
	}
	void reset()
	{
		vx=0;
		isStop=isDisCmp=false;
		width=0;
	}
}
