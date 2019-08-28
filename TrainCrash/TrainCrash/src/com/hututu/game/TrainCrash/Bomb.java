package com.hututu.game.TrainCrash;

public class Bomb {
	
	float x,y;
	int mBombCnt=0;
	boolean isBlast;
	float xx[]={0,.02f,.02f,-.02f,-.02f},yy[] ={0,.02f,-.02f,.02f,-.02f};
	float bx[],by[],bvy[],bvx[];
	public Bomb() {
		bx  = new float[5];
		by  = new float[5];
		bvx = new float[5];
		bvy = new float[5];
	}
	void Set(float _x,float _y)
	{
		x      = _x;
		y      = _y;
	   isBlast = false;
	   for(int i=0;i<bx.length;i++)
	   {
		   bx[i]=x+xx[i];
		   by[i]=y+yy[i];
	   }
	}
	void ActiveBomb()
	{
	  isBlast  = true;
	  mBombCnt = 0;
	  for(int i=0;i<bvx.length;i++)
	  {
		 float r = (float)Math.toRadians(20+i*M.randomRangeInt(45,65));
		 bvx[i] = (float)Math.cos(r)*.02f;
		 bvy[i] = (float)Math.sin(r)*.035f;
	  }
	}
	void update()
	{
		for(int i=0;i<bvx.length;i++)
		  {
			bx[i]+=bvx[i];
			by[i]+=bvy[i];
		  }
	}
	void reset()
	{
		x=y=100;
		isBlast = false;
		mBombCnt = 0;
	}

}
