package com.hututu.game.kingpenguin;

public class SevenObj {
	int obj[];
	int move = 0;
	float x,y;
	public SevenObj()
	{
		obj = new int[8];
		for(int i=0;i<obj.length;i++)
			obj[i] = i;
		move = 0;
	}
	void set(float _x,float _y,int no,int _move)
	{
		x = _x;
		y = _y;
		move =_move;
		for(int i=0;i<obj.length;i++)
		{
			if(i<no)
				obj[i] = i;
			else
				obj[i] = -1;
		}
	}
}
