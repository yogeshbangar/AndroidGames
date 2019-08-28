package com.hututu.game.BoomBaseball;

public class Window {
	float x,y,gayab;
	int No,Count;
//	boolean isBreak=false;
	void Set(float _x,float _y,int _no)
	{
		x  = _x;
		y  = _y;
		No = _no;
		
	}
	void SetChar(float _x,float _y)
	{
		x     = _x;
		y     = _y;
		gayab = 1;
		No    = M.mRand.nextInt(4);
		if(x>-1 && x<1)
		{
			if(M.mRand.nextBoolean())
				M.FemaleSound(GameRenderer.mStart,R.drawable.femalevoice);
			else
				M.FemaleSound2(GameRenderer.mStart,R.drawable.femalevoice1);
		}
		Count =0;
		
	}
	void UpdateChar()
	{
		
		Count++;
		if(Count>50)
		  gayab -=.05f;
		if(gayab<.2f)
		{
			x = y=-100;
			Count=0;
		}
	}

}
