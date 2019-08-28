package com.hututu.game.ScarecrowvsBirds;

public class Arrow {

	float x,y,vx,vy,rx,ry;
	int Power=-1;
	double ang;
	boolean isFire =false;
	boolean isGoal = false;
	int No=0,mHattrickCnt=0;
	GameRenderer mGR;
	public Arrow(GameRenderer _mGR)
	{
		mGR =_mGR;
		x = 0; 
	    y = vx = vy	=0;
 		ang=Math.toRadians(180);
   		isFire = false;
   		
	}
	public void set(float _x,float _y,float _vx,float _vy)
	{
	  if(!isGoal)
		mHattrickCnt = 0;
		x  = _x;
		y  = _y;
		vx = _vx;
		vy = _vy;
		isGoal = false;
	}
	public void setBow(float _x,float _y,int _no)
	{
		x  = _x;
		y  = _y;
		No = _no;
		ang=Math.toRadians(180);
	}
	public void ResetArrow()
	{
		System.out.println("ResetArrow  "+isGoal);
		set(-.5f,-.25f,0,0);
		ang=Math.toRadians(180);
		isFire = false; 
	}
	public void Update()
	{
		x   += vx;
		y   += vy;
		vy  += M.Gravity;
	    if(vx>0)	
		 ang = (float)Math.atan(vy/vx)-Math.toRadians(180);
	    else
    	 ang = (float)Math.atan(vy/vx);
		rx = (float)(.12f*Math.sin(ang-Math.toRadians(90)));
		ry = (float)(.2f*Math.cos(ang-Math.toRadians(90)));
		if(x>1.2f||x<-1.2f||y>1.2f||y<-1.2f)
		{
			ResetArrow();
		  if(mGR.mArrow.mHattrickCnt>=3)
			 mGR.mNoArrow++;
		  if(mGR.mNoArrow<=0)
		  {
			 M.GameScreen = M.GAMEOVER;
			 M.BGStop();
		  }
		}
	}


}
