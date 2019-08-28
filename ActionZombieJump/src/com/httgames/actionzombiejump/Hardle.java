package com.httgames.actionzombiejump;

public class Hardle {
	float x;
	final float y = -.35f;
	int lvl =0;
	boolean isCoin = true;
	void set(float _x,int _lvl){
		x =_x;
//		lvl = _lvl;
		lvl=M.mRand.nextInt(Level.Lvl.length);
		isCoin = true;
	}
	
	void set(Hardle hrd){
		x =hrd.x+2.5f;
//		lvl =hrd.lvl+1;
//		lvl%=Level.Lvl.length;
		lvl=M.mRand.nextInt(Level.Lvl.length);
		isCoin = true;
	}
}
