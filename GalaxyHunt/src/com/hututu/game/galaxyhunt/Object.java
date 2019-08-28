package com.hututu.game.galaxyhunt;

public class Object {
	float x,y,vx,vy;
	int pNo;
	int mLife;
	void set(float _x,float _y,float _vx,float _vy,int counter)
	{
		x = _x;
		y = _y;
		vx=_vx;
		vy=_vy;
		
		if(counter<1000)
			pNo = M.mRand.nextInt(4);
		else if(counter<2000)
			pNo = M.mRand.nextInt(8);
		else if(counter<3000)
			pNo = M.mRand.nextInt(4)+4;
		else if(counter<5000)
		{
			pNo = M.mRand.nextInt(12);
		}
		else 
			pNo = M.mRand.nextInt(15);
			
		switch (pNo) {
		case 4:case 5:case 6:case 7:
			mLife = 2;
			vy *=.90f;
			break;
		case 8:case 9:case 10:case 11:
			mLife = 5;
			vy *=.80f;
			vx =.01f;
			break;
		case 12:
			mLife = 8;
			vy *=.70f;
			break;
		case 13:
			mLife = 13;
			vy *=.40f;
		case 14:
			mLife = 25;
			vy *=.20f;
			break;
		default:
			mLife = 1;
			vy *=0.90f;
			break;
		}
	}
	void update()
	{
		x+=vx;
		y+=vy;
		if(x>.9)
			vx = -Math.abs(vx);
		if(x<-.9)
			vx = Math.abs(vx);
		if(y < -1.4f)
		{
			y = -100;
			M.Plife -=mLife;
			mLife =0;
			if(M.Plife<0)
			{
				M.GameScreen = M.GAMEOVER;
				M.stop();
			}
		}
	}
}
