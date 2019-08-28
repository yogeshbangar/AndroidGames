package com.hututu.game.BoomBaseball;

public class TextAni {
	float x,y,t,s;
	float Scal[] = {.88f,.95f,.92f,.94f,.88f,.88f,1.5f,1.1f,.7f,.3f};
	int StartCnt,Ani=0,No;
	
	int    FinalScore;
	float  NoScal[]	 = {.9f,1.2f,1f,1.6f,.9f};
	int    Score;
	GameRenderer mGR;
	public TextAni(){
	  x = y=-100;	
	}
	public TextAni(GameRenderer mGr){
		  	mGR = mGr;
		}
	void Set(float _x,float _y,int _no)
	{
	    x =_x;
	    y =_y;
	    StartCnt=0;
		Ani=0;
		t =1;
		No = _no;
		s  = 1.4f;
	}
	void SetScore(float _x,float _y,int _fScore)
	{
	    x =_x;
	    y =_y;
	    StartCnt=0;
		Ani=0;
		No=0;
		FinalScore = _fScore;
		Score =0;
		s=1.1f;
	}
	void update()
	{
		StartCnt++;
		if(StartCnt%3==0)
		{
	 	  if(Ani<=Scal.length-1)
	 	  {
	 		 s =Scal[Ani];
	 		 Ani++;
		  }
		}
		if(Ani>5)
		{
		   t -=.035f;
		  if(t<.3f)
		    x= y=-100;
		}
	}
	void updateBoom()
	{
		t -=.025f;
	   if(t<.3f)
	    x= y=-100;
	}
	int dcr=0;
	void updateScoreBoard()
	{
//		if(FinalScore<5000)
//			dcr =50;
//		if(FinalScore>5000)
//			dcr =100;
//		if(FinalScore>10000)
//			dcr =500;
//		if(FinalScore>50000)
//			dcr =1000;
		dcr = FinalScore/20;
		if(Score<FinalScore)
		{
			Score+=dcr;
			M.CountingSound(GameRenderer.mContext,R.drawable.counting);
		}
		else
		{
			Score=FinalScore;
			if(No<NoScal.length-1)
			{
			  if(mGR.root.Counter%4==0)
			  {
				 No++;
				 s = NoScal[No];
			  }
			}
			else
				mGR.NoCount++;
		}
	}

}
