package com.hututu.game.archeryking;

public class Tree {
	float x,y;//-.24f
	int ID;
	public Tree(float _x,int _ID)
	{
		set(_x,_ID);
	}
	void set(float _x,int _ID)
	{
		x =_x;
		ID =_ID;
		if(M.mRand.nextInt(3)==0 && ID!=5)
			ID =0;
		y = -.24f;
		if(ID == 1)
			y = -.0f;
		y-=M.mRand.nextInt(60)/1000f;
		if(ID == 5)
			y=M.mRand.nextFloat()%.4f+.4f;
	}
	void setxyI(float _x,float _y)
	{
		x =_x;
		y =_y;
		ID =M.mRand.nextInt(50)+50;
	}
}
