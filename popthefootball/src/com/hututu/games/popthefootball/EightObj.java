package com.hututu.games.popthefootball;


public class EightObj {
	int i,j;
	int counter =0;
	float x,y;
	public void set(float _x,float _y,int _i,int _j)
	{
		x = _x;
		y = _y;
		i = _i;
		j = _j;
		counter = 0;
	}
	boolean setAnim(int _i,int _j)
	{
		if(_i>2 || _i<0 ||_j>3 || _j<0 || Math.abs(i-_i)>1 || Math.abs(j-_j)>1||(i==_i&&j==_j))
			return false;
		i =_i;j =_j;
		x = -.56f+j*.39f;
		y=   .41f-i*.58f;
		counter = 0;
		M.sound10(GameRenderer.mContext, R.drawable.l_4_blast);
		return true;
	}
	
}
