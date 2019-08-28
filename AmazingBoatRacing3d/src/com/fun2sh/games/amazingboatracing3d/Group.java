package com.fun2sh.games.amazingboatracing3d;
import rajawali.primitives.Plane;
import android.content.Intent;
import android.graphics.Paint.Align;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
public class Group {
	HTTRenderer mGR;
	public Group(HTTRenderer GR) {mGR =GR;}
	int _keyCode =0;
	float sx,sy,sz=0;
	final int Check=100;
	int   Counter=0,AdCount;
	public void setting() {float ud = .01f;switch (_keyCode) {case 1:sy -= ud;break;case 2:sy += ud;break;case 3:sx -= ud;break;case 4:sx += ud;break;case 5:sz -= ud;break;case 6:sz += ud;break;}
	System.out.println("~~~~~~~  " + sx + "f,  " + sy + "f,  "+ sz + "f");}
	public boolean Handle(MotionEvent event){
		int val = 170;
		if(event.getX() <val && event.getY() <val){_keyCode = 1;}
		if(event.getX() >M.ScreenWidth - val && event.getY() <val){_keyCode = 2;}
		if(event.getX() < val && event.getY() >M.ScreenHieght-val){_keyCode = 3;}
		if(event.getX() >M.ScreenWidth - val  && event.getY() >M.ScreenHieght-val){_keyCode = 4;}
		if(event.getX() < val && event.getY() >(M.ScreenHieght-val*2)/2 && event.getY() <(M.ScreenHieght+val)/2){_keyCode = 5;}
		if(event.getX() >M.ScreenWidth - val  && event.getY() >(M.ScreenHieght-val*2)/2 && event.getY() <(M.ScreenHieght+val)/2){_keyCode = 6;}
		if(event.getAction()== MotionEvent.ACTION_UP)_keyCode = 0;
		return true;
	}
	void DrawTexture(Plane plan,float x,float y,float z,boolean isVisible)
	{
		plan.setVisible(isVisible);
		plan.setPosition(x,y,z);
	}
	void DrawTextureS(Plane plan,float x,float y,float z,float sx,float sy,float sz,boolean isVisible)
	{
		plan.setVisible(isVisible);
		plan.setScale(sx,sy,sz);
		plan.setPosition(x,y,z);
		
	}
	void DrawTextureSS(Plane plan,float x,float y,float z,float scal,boolean isVisible)
	{
		plan.setVisible(isVisible);
		plan.setScale(scal,scal,scal);
		plan.setPosition(x,y,z);
	}
	void DrawTextureR(Plane plan,float x,float y,float z,float rx,float ry,float rz,boolean isVisible)
	{
		plan.setVisible(isVisible);
		plan.setPosition(x,y,z);
		plan.setRotation(rx,ry,rz);
		
	}
	
	void Draw(){
		
		switch (M.GameScreen){
		case M.GAMELOGO:
			 DrawTexture(mGR.mTex_Logo,0,0,0,true);
			 if(Counter==10)
			 {
				mGR.LoadGameObj();
				mGR.AddUiChild();
			 }
	        if(Counter>40)
			{
	        	if(!mGR.isResume)
				{
					HTTRenderer.mStart.resume();
					mGR.isResume =true;
				}
		  	   DrawTexture(mGR.mTex_Logo,0,0,0,false);
			   Counter=0;
			   M.GameScreen = M.GAMEMENU;
			}
			break;
		case Check:
			break;
		case M.GAMEMENU:
			DrawMenu(true);
			break;
		case M.GAMEOVER:
		case M.GAMEPAUSE:
			DrawGameOver(true);
			break;
		 case M.GAMEPLAY:
		 case M.GAMESTART:
			DrawGamePlay(true);
			break;
		}
		Counter++;
//		setting();
	}
	
	public boolean onTouch(View v, MotionEvent event) {
		switch (M.GameScreen) 
		{
			case M.GAMEMENU:
				HandleMenu(event);
				break;
			case M.GAMEOVER:
			case M.GAMEPAUSE:
				HandleOver(event);
				break;
			case M.GAMEPLAY:
				break;
		}
		if(event.getAction() == MotionEvent.ACTION_DOWN && mGR.mSel!=0)
		{
			M.clickSound(HTTRenderer.mStart,R.drawable.click);
		}
//		System.out.println("    Touch	xxxx        "+screen2worldX(event.getX())+"       touchyyy       "+screen2worldY(event.getY()));
		Handle(event);
		return true;
	}
	void DrawMenu(boolean isVisible)
	{
		if(M.GameScreen == M.GAMEMENU)
			isVisible=true;
		else
			isVisible=false;
		
		DrawTexture(mGR.mTex_Splash ,0  ,0 ,-.435f,isVisible);
		DrawTexture(mGR.mTex_Pattern[0],0  ,-1.409f,0f,isVisible);
		DrawTextureSS(mGR.mTex_Button1,0,-.769f,0f,mGR.mSel==10?1.2f:1,isVisible);
		DrawTextureSS(mGR.mTex_Play,0,-.769f,0f,mGR.mSel==10?1.2f:1,isVisible);
		for(int i=0;i<mGR.mTex_UiBtn.length ;i++)
		   DrawTextureSS(mGR.mTex_UiBtn[i],-2.019f+i*.799f,-1.399f,0,mGR.mSel==i+1?1.2f:1,isVisible);
		
		DrawTextureSS(mGR.mTex_SoundOff[0] ,-2.019f+3*.799f,-1.399f,0,2f,M.SetBG?false:isVisible);
		DrawTextureSS(mGR.mTex_SoundOff[1] ,-2.019f+4*.799f,-1.399f,0,2f,M.setValue?false:isVisible);
		
	}
	
	boolean HandleMenu(MotionEvent event )
	{
		mGR.mSel=0;
		for(int i=0;i<mGR.mTex_UiBtn.length;i++)
		{
			if(CircRectsOverlap(-.725f+i*.28f,-.85f,mGR.mTex_UiBtn[0].getWidth()*.4f,mGR.mTex_UiBtn[0].getHeight()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()) ,.05f))
				mGR.mSel=i+1;
		}
		if(CircRectsOverlap(0f,-.5f,mGR.mTex_Button1.getWidth()*.4f,mGR.mTex_Button1.getHeight()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()) ,.05f))
			mGR.mSel=10;
		
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			switch(mGR.mSel)
			{
			   case 1:
				   RateUs();
				   break;
			   case 2:
				   share2friend(); 
				   break;
			   case 3:
				   HTTRenderer.mStart.onShowAchievementsRequested();
				   break;
			   case 4:
				   M.SetBG =!M.SetBG;
				   break;
			   case 5:
				   M.setValue =!M.setValue;
				   break;
			   case 6:
				   HTTRenderer.mStart.onShowLeaderboardsRequested();
				   break;
			   case 10:
				   mGR.GameReset();
				   SetCamera();
				   DrawMenu(false);
				   break;
			}
			mGR.mSel=0;
		}
		return true;
	}
	

	void DrawGameOver(boolean isVisible)
	{
		if(M.GameScreen ==M.GAMEOVER || M.GameScreen ==M.GAMEPAUSE)
			isVisible =true;
		else
			isVisible=false;
		
		DrawTexture(mGR.mTex_Splash  ,0,0,-.435f,isVisible);
		DrawTexture(mGR.mTex_TransBG ,0,0,-.435f,isVisible);
		DrawTexture(mGR.mTex_GameOver,0,1.419f,0f,M.GameScreen==M.GAMEOVER?isVisible:false);
		DrawTexture(mGR.mTex_Pause   ,0,1.419f,0f,M.GameScreen==M.GAMEPAUSE?isVisible:false);
		
		DrawTexture(mGR.mTex_Pattern[0]  ,0    ,1.409f,0f,isVisible);
		DrawTextureSS(mGR.mTex_Button1,2.169f,1.409f,0f,mGR.mSel==10?1.2f:1,isVisible);
		DrawTextureSS(mGR.mTex_Play   ,2.169f,1.409f,0f,mGR.mSel==10?1.2f:1,isVisible);
		
		
		DrawTexture(mGR.mTex_ScoreTxt,0,.689f,0,isVisible);
		DrawTexture(mGR.mTex_ScoreBox[0],0,.349f,0,isVisible);
		mGR.mTex_ScoreBox[0].setRotation(0,180,0);
		mGR.DrawFont(mGR.mScore+"", -.04f,.28f, 0,0,1, Align.CENTER,isVisible);
		
		
		DrawTexture(mGR.mTex_BestTxt    ,0,-.27f,0,isVisible);
		DrawTexture(mGR.mTex_ScoreBox[1],0,-.609f,0,isVisible);
		mGR.DrawFont2(mGR.mBestScore+"" ,-.04f,-.679f, 0,0,1, Align.CENTER,isVisible);
		
		
		DrawTexture(mGR.mTex_Pattern[1],0  ,-1.409f,0f,isVisible);
		for(int i=0;i<mGR.mTex_UiBtn.length ;i++)
			DrawTextureSS(mGR.mTex_UiBtn[i],-2.019f+i*.799f,-1.399f,0,mGR.mSel==i+1?1.2f:1,isVisible);
		
		DrawTextureSS(mGR.mTex_SoundOff[0] ,-2.019f+3*.799f,-1.399f,0,2f,M.SetBG?false:isVisible);
		DrawTextureSS(mGR.mTex_SoundOff[1] ,-2.019f+4*.799f,-1.399f,0,2f,M.setValue?false:isVisible);
		
	}
	boolean HandleOver(MotionEvent event )
	{
		mGR.mSel=0;
		for(int i=0;i<mGR.mTex_UiBtn.length;i++)
		{
			if(CircRectsOverlap(-.725f+i*.28f,-.85f,mGR.mTex_UiBtn[0].getWidth()*.4f,mGR.mTex_UiBtn[0].getHeight()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()) ,.05f))
				mGR.mSel=i+1;
		}
		if(CircRectsOverlap(.71f,.833f,mGR.mTex_Play.getWidth()*.4f,mGR.mTex_Play.getHeight()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()) ,.05f))
			mGR.mSel=10;
		
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			switch(mGR.mSel)
			{
			   case 1:
				   RateUs();
				   break;
			   case 2:
				   share2friend(); 
				   break;
			   case 3:
				   HTTRenderer.mStart.onShowAchievementsRequested();
				   break;
			   case 4:
				   M.SetBG =!M.SetBG;
				   break;
			   case 5:
				   M.setValue =!M.setValue;
				   break;
			   case 6:
				   HTTRenderer.mStart.onShowLeaderboardsRequested();
				   break;
			   case 10:
				   if(M.GameScreen == M.GAMEOVER)
				       mGR.GameReset();
				   else
				   {
					  if(M.SetBG) 
					     M.BGPlay(HTTRenderer.mContext,R.drawable.boat); 
					  M.GameScreen = M.GAMEPLAY;
					  mGR.mGameTime+=System.currentTimeMillis();
				   }
				    SetCamera();
				    DrawGameOver(false);
				   break;
			}
			mGR.mSel=0;
		}
		return true;
	}
	void SetCamera()
	{
		mGR.getCurrentCamera().setPosition(0,-2.15f,1.031f);
		mGR.getCurrentCamera().setRotation(-86.4f,0,0);
	}
	void ResetCamera()
	{
		mGR.getCurrentCamera().setPosition(0,0,4f);
		mGR.getCurrentCamera().setRotation(0,0,0);
	}
	
	int Achievements[]={
		R.string.achievement_cross_30_boats,
		R.string.achievement_cross_100_boats,
		R.string.achievement_cross_500_boats,
		R.string.achievement_500_coins,
		R.string.achievement_1000_coins,
	};
	
	void UpdateAchieve()
	{	
		int Ach[] = {30,100,500,500,1000};
		for(int i=0;i<mGR.isAchieve.length;i++)
		{
			if(i<3)
			{
				if(mGR.mCrossBoat>=Ach[i] && !mGR.isAchieve[i])
				{
					mGR.isAchieve[i]= true;
					HTTRenderer.mStart.UnlockAchievement(Achievements[i]);
				}
			}
			else
			{
				if(mGR.mCoinCollect>=Ach[i] && !mGR.isAchieve[i])
				{
					mGR.isAchieve[i]= true;
					HTTRenderer.mStart.UnlockAchievement(Achievements[i]);
				}
			}
		}
	   	
	}
	float spd=1.5f;
	void SpeedUp()
	{
	  if(Counter%2==0)
		mGR.mPlayer.mSpd+=.02f;
	   if(mGR.mPlayer.mSpd>spd)
		 mGR.mPlayer.mSpd=spd;
	}
	void GameLogic()
	{
		if(Counter%2==0)
			mGR.mSideNo++;
		
		SpeedUp();
		for(int i=0;i<mGR.mWater.length;i++)
		{
		  float  y =(float)mGR.mWater[i].getY();
		  mGR.mWater[i].setY(y-=mGR.mPlayer.mSpd*.3f);
		}
		for(int i=0;i<mGR.mWater.length;i++)
		{
		   float y=(float)mGR.mWater[i].getY();
		   if(y<-5.29f)
		   {
			 mGR.mWater[i].setY(mGR.mWater[i==0?mGR.mWater.length-1:i-1].getY()+18.461f);
		   }
		}
		for(int i=0;i<mGR.mWaterTrans.length;i++)
		{
		  float  y =(float)mGR.mWaterTrans[i].getY();
		  mGR.mWaterTrans[i].setY(y-=mGR.mPlayer.mSpd);
		}
		for(int i=0;i<mGR.mWaterTrans.length;i++)
		{
		   float y=(float)mGR.mWaterTrans[i].getY();
		   if(y<-5.29f)
		   {
			 mGR.mWaterTrans[i].setY(mGR.mWaterTrans[i==0?mGR.mWaterTrans.length-1:i-1].getY()+18.431f);
		   }
		}

		for(int i=0;i<mGR.mOpp.length;i++)
		{
		  mGR.mOpp[i].updateOpp(mGR.mCrashCnt==0?mGR.mPlayer.mSpd*mGR.mOpp[i].mSpd:-.5f);
		}
		for(int i=0;i<mGR.mCloud.length;i++)
	    {
	    	mGR.mCloud[i].updateCloud(mGR.mPlayer.mSpd*.002f);
	    }
	    for(int i=0;i<mGR.mCloud.length;i++)
	    {
	    	if(mGR.mCloud[i].z>-.491f)
	    	{
    		  float z = mGR.mCloud[i==0?1:0].z-1.08f;
    		  mGR.mCloud[i].SetCloud(0,1.899f,z,1.129f,1f,.541f);//-.491
    		}
	    }
		if(mGR.mStone.x>-3 && mGR.mStone.x<3)	
		    mGR.mStone.Update(mGR.mPlayer.mSpd);
	    for(int i=0;i<mGR.mCoin.length;i++)
		{
		  mGR.mCoin[i].updateCoin(mGR.mPlayer.mSpd);
		  if(mGR.mCoin[i].y<-2)
		  {
			 mGR.mCoin[i].y = 80;
			 mGR.mCoin[i].x = (M.mRand.nextBoolean()?M.mRand.nextInt()%20:-M.mRand.nextInt()%20)*.1f;
		  }
		}
	    for(int i=0;i<mGR.mOpp.length;i++)
		{
			if(Rect2RectIntersection(mGR.mOpp[i].x,mGR.mOpp[i].y,1,1f,mGR.mPlayer.x,mGR.mPlayer.y,1,1f))
		    {
			   if(mGR.mCrashCnt==0)
			   {
			      mGR.mCrashCnt=1;
			      mGR.mOpp[i].isCollide =true;
			      M.crashSound(HTTRenderer.mContext,R.drawable.crash);
			   }
//			  System.out.println("in Player Opppp"); 
		    }
		}
	    for(int i=0;i<mGR.mCoin.length;i++)
		{
		   if(mGR.mCrashCnt<1 && CircRectsOverlap(mGR.mPlayer.x,mGR.mPlayer.y,1,1f,mGR.mCoin[i].x,mGR.mCoin[i].y,.2f))
		   {
			   mGR.mCoinCollect++;
			   mGR.mCoin[i].x=100;
			   M.coinSound(HTTRenderer.mContext,R.drawable.coin);
//			   System.out.println("in Coinnnnn"); 
		   }
		}
	    if(mGR.mStone.y<40 && mGR.mStone.y>-.2f)
		 {
		    if(Rect2RectIntersection(mGR.mStone.x,mGR.mStone.y,1.1f,1f,mGR.mPlayer.x,mGR.mPlayer.y,1,1))
		    {
			  if(mGR.mCrashCnt==0)
			  {
			      mGR.mCrashCnt=1;
			      M.crashSound(HTTRenderer.mContext,R.drawable.crash);
			  }
		    }
		}
		if(mGR.mOppGenCnt>mGR.mGenTime && mGR.mPlayer.mSpd>0)
		{
			if(mGR.mGenTime>mGR.mGenDelay)
			{
				mGR.mGenTime-=mGR.mGenSpeed;
				mGR.mGenSpeed+=2;
				if(mGR.mGenTime%5==1)
				  mGR.mGenDelay--;
				if(mGR.mGenDelay<40)
				   mGR.mGenDelay=40;
			}
		   mGR.CreateBoat();
		   mGR.mOppGenCnt=0;
		}
		mGR.mOppGenCnt++;
		if(mGR.mCrashCnt>0)
		{
		   M.BgStop();
		   mGR.mCrashCnt++;
		   mGR.mPlayer.mSpd*=.95f;
		   if(mGR.mScore>mGR.mBestScore)
		   {
			 mGR.mBestScore=mGR.mScore;
			 HTTRenderer.mStart.Submitscore(R.string.leaderboard_best_score,mGR.mBestScore);
		   }
		  if(mGR.mCrashCnt>80)
		  {
			 Counter=0;
			 ResetCamera(); 
			 M.GameScreen = M.GAMEOVER;
			 DrawGamePlay(false);
			 AdCount++;
	 		 HTTRenderer.mStart.ShowHandle();
		  }
		}
		UpdateAchieve();
	}
	void Updatedesert()
	{
		for(int i=0;i<mGR.mDesert.length;i++)
		{
		  for(int j=0;j<mGR.mDesert[0].length;j++)
		    mGR.mDesert[i][j].UpdateObj(-mGR.mPlayer.mSpd*.75f);
		}
		for(int i=0;i<mGR.mDesert.length;i++)
		{
			for(int j=0;j<mGR.mDesert[0].length;j++)
			{
			   if(mGR.mDesert[i][j].y<-17.29f)
			   {
				 mGR.mDesert[i][j].y = mGR.mDesert[i][j==0?mGR.mDesert.length-1:j-1].y+45f;
				 if(i==0)
		          {
//					 mGR.mDesert[i][j].setVisible(false);
		        	  int c   = mGR.mSideNo/300;
		        	  c      %=mGR.mDesert.length;
	        	      mGR.mSide[j]=(byte)c;
	        	     
	        	  }
			   }
			}
		}
	}
	void Drawdesert()
	{
		for(int i=0;i<mGR.mDesert.length;i++)
		{
		 for(int j=0;j<mGR.mDesert[0].length;j++)
		    mGR.mDesert[i][j].DrawObj(mGR.mSide[j]==i);
		}
		if(M.GameScreen == M.GAMEPLAY)
		   Updatedesert();
	}
	void DrawGamePlay(boolean isVisible)
	{
		if(M.GameScreen == M.GAMEPLAY || M.GameScreen == M.GAMESTART)
			isVisible = true;
		else
			isVisible=false;
		for(int i=0;i<mGR.mCloud.length;i++)
		{
			mGR.mCloud[i].DrawCloud(isVisible);
		}
		DrawTextureS(mGR.mTex_BackBg,0,.49f,-1.88f,.659f,0,.489f,isVisible);
	    mGR.mTex_BackBg.setRotation(-100.9f,180,0);
	    mGR.getCurrentScene().setBackgroundColor(1,1,1,1);
		for(int i=0;i<mGR.mWater.length;i++)
		{
		  mGR.mWater[i].setVisible(isVisible);
		}
		for(int i=0;i<mGR.mWaterTrans.length;i++)
		{
			mGR.mWaterTrans[i].setVisible(isVisible);
		}
		
		if(isVisible)
		  Drawdesert();
		
		mGR.mPlayer.DrawPlayer(isVisible);
		for(int i=0;i<mGR.mOpp.length;i++)
	    {
		    mGR.mOpp[i].DrawOpp(isVisible);
		    mGR.mOppAni[i].DrawOppAni(mGR.mOppAni[i].mAniCnt,mGR.mOpp[i].x,mGR.mOpp[i].y,mGR.mOpp[i].z,(mGR.mPlayer.mSpd<=0 || mGR.mOpp[i].isCollide)?false:isVisible);
		    if(Counter%2==0)
		    {
		    	mGR.mOppAni[i].mAniCnt++;
		    	mGR.mOppAni[i].mAniCnt%=mGR.mOppAni[i].mobj.length;
		    }
	    }
		for(int i=0;i<mGR.mCoin.length;i++)
		{
	        mGR.mCoin[i].DrawCoin(isVisible);
		}
		mGR.mStone.DrawObj(isVisible);
		
		if(M.GameScreen == M.GAMESTART)
		{
			String  str[] ={3+"",2+"",1+"","Go"};
			mGR.DrawFont2(str[mGR.mStartCnt%str.length],0,0,-2.239f,-87f,1.58f,Align.CENTER,isVisible);
			M.BeepSound(HTTRenderer.mContext,R.drawable.count);
			if(Counter%20==0)
			  mGR.mStartCnt++;
			BikeStartSound();
			if(mGR.mStartCnt>str.length-1)
			{
				M.StopSound();
				mGR.mGameTime = System.currentTimeMillis();
				if(M.SetBG)
				  M.BGPlay(HTTRenderer.mContext,R.drawable.boat);
				M.GameScreen = M.GAMEPLAY;
				mGR.DrawFont2(str[0],.709f,.109f,0,-87f,.449f,Align.CENTER,isVisible);
			}
		}
	    DrawTexture(mGR.mTex_ScoreBox[0],-1.839f,3.09f,-.869f,isVisible);
	    mGR.mTex_ScoreBox[0].setRotation(-87f,180,0);
	    mGR.DrawFont(mGR.mScore+"",-.559f,.109f,-1.749f,-87f,.449f,Align.CENTER,isVisible);
	    
	    mGR.mAni.DrawAni(mGR.mAni.mAniCnt,mGR.mPlayer.x*.95f,(mGR.mPlayer.mSpd<=0 || mGR.mCrashCnt>1)?false:isVisible);
	    if(Counter%2==0)
	    {
	    	mGR.mAni.mAniCnt++;
	    	mGR.mAni.mAniCnt%=mGR.mAni.mobj.length;
	    }
	    
	    if(M.GameScreen == M.GAMEPLAY)
	    {
	    	float t=0;
	    	if(mGR.mCrashCnt<1)
	    	{
		  	 t             = (System.currentTimeMillis()-mGR.mGameTime);
			 String strflt  = (t/1000f)+"";
		  	 strflt         = strflt.substring(0,strflt.indexOf('.')+2);
		  	 mGR.mPlayTime  = Float.parseFloat(strflt);
	    	}
		  	mGR.mScore     = (int)(mGR.mCoinCollect*mGR.mPlayTime);
		    GameLogic();
	    }
	}
	void BikeSound()
	{
//		switch(mGR.mPlayer.mBikeNo){
//		 case 0:
//			M.Bike1Sound(HTTRenderer.mContext,R.drawable.bike_01);
//			break;
//		 case 1:
//			M.Bike2Sound(HTTRenderer.mContext,R.drawable.bike_02);
//			break;
//		 case 2:
//			M.Bike3Sound(HTTRenderer.mContext,R.drawable.bike_03);
//			break;
//
//		}
	}
	void BikeStartSound()
	{
		
//		switch(mGR.mPlayer.mBikeNo){
//		 case 0:
//			M.Bikestart1Sound(HTTRenderer.mContext,R.drawable.bike_start_01);
//			break;
//		 case 1:
//			 M.Bikestart2Sound(HTTRenderer.mContext,R.drawable.bike_start_02);
//			break;
//		 case 2:
//			 M.Bikestart3Sound(HTTRenderer.mContext,R.drawable.bike_start_03);
//			break;
//
//		}
	}
	boolean HandleGameplay(MotionEvent event) {
		
		 mGR.mSel=0;
		 
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			mGR.mSel=0;
		}
		return true;
	}
	boolean CircRectsOverlap(double CRX,  double CRY,double CRDX,double CRDY ,double centerX,double centerY, double radius)
    {
        if ((Math.abs(centerX - CRX) <= (CRDX + radius)) && (Math.abs(centerY - CRY) <= (CRDY + radius)))
           return true;
        return false ;

    }
	boolean Rect2RectIntersection(double ax,double ay,double adx,double ady,double bx,double by,double bdx,double bdy){
		ax -= adx/2;
		ay += ady/2;
		bx -= bdx/2;
		by += bdy/2;
		if( ax+adx > bx  && ay-ady < by && bx+bdx > ax && by-bdy< ay)
		{
			return true;
		}
		return false;
	}
	boolean CirCir(double cx1,double cy1, double r1,double cx2,double cy2, double r2)
    {
		float bVectMag = (float) Math.sqrt(((cx1-cx2)*(cx1-cx2)) + ((cy1-cy2)*(cy1-cy2)));
		if (bVectMag<(r1+r2))
           return true;
        return false ;

    }
//	float screen2worldX(float a) {
//		float c = ((a - M.ScreenWidth / 2) / M.ScreenHieght) * 2;
//		return c;
//	}
//
//	float screen2worldY(float a) {
//		float c = ((a / M.ScreenHieght) - 0.5f) * (-2);
//		return c;
//	}
	float screen2worldX(float a)
	{
		float c = ((a / M.ScreenWidth)- 0.5f)*2;
		return c;
	}
	float screen2worldY(float a)
	{
		float c = ((a / M.ScreenHieght)- 0.5f)*(-2);
		return c;
	}

	int XPos(float x){
	        return (int)(((1+x)*M.ScreenWidth)/2);
	}

	int YPos(float y){
	    return (int)(((1-(y))*M.ScreenHieght)/2);
	}
	
	double GetAngle(double d,double e)
	{
	  if(d==0)
		  return e>=0 ? Math.PI/2 : -Math.PI/2;
	  else if (d > 0)
		  return Math.atan(e/d);
	  else
		  return Math.atan(e/d) + Math.PI;
	}
	void RateUs() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.LINK + getClass().getPackage().getName()));
		Start.mContext.startActivity(mIntent);
	}

	void MoreGame() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.MORE));
		Start.mContext.startActivity(mIntent);
	}
	void Twitter() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://twitter.com/hututu_games"));
		Start.mContext.startActivity(mIntent);
	}
	void google() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://plus.google.com/+Hututugames"));
		Start.mContext.startActivity(mIntent);
	}
	void facebook() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://www.facebook.com/HututuGames"));
		Start.mContext.startActivity(mIntent);
	}
	
	void share2friend()
	{
		shareImage();
	}

	void shareTxt()
	{
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("text/plain");
		i.putExtra(Intent.EXTRA_SUBJECT,"New Game '"+HTTRenderer.mContext.getResources().getString(R.string.app_name)+"'");
		i.putExtra(Intent.EXTRA_TEXT,"Let the battle commence!!! Download it now and let’s ROCK!!!!  "+(M.SHARELINK+getClass().getPackage().getName()));
		try {
			HTTRenderer.mContext.startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(HTTRenderer.mStart, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}

	  void shareImage(){
		  Uri uri = Uri.parse("android.resource://"+getClass().getPackage().getName()+"/drawable/share");
	      Intent shareIntent = new Intent();
	      shareIntent.setType("image/png");
	      shareIntent.setAction(Intent.ACTION_SEND);
	      shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
	      shareIntent.putExtra(Intent.EXTRA_TEXT,"My Score is"+" "+mGR.mBestScore+" "+"in  "+HTTRenderer.mContext.getResources().getString(R.string.app_name)+"  Can you beat me..."+"Download Latest new racing game & enjoy!!!! "+"     "+(M.SHARELINK+getClass().getPackage().getName()));
	      try{      
	    	  HTTRenderer.mContext.startActivity(Intent.createChooser(shareIntent,"Share from"));
	       }catch (android.content.ActivityNotFoundException ex){
	  	    Toast.makeText(HTTRenderer.mStart, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
	  	  }
	 }
}
