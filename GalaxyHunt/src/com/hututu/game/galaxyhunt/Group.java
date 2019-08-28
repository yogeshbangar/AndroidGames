package com.hututu.game.galaxyhunt;
import javax.microedition.khronos.opengles.GL10;


import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.widget.Toast;
public class Group extends Mesh 
{
	GameRenderer mGR = null;
	int Counter =0;
	public Group(GameRenderer _GameRenderer)
	{
		mGR = _GameRenderer;
	}
	
	@Override
	public void draw(GL10 gl) 
	{
		Counter++;
		switch (M.GameScreen) {
		case M.GAMELOGO:
			DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if(mGR.resumeCounter>80){
				M.GameScreen = M.GAMEMENU;
				M.GameScreen = M.GAMEADD;//AdHouse
				Counter=0;//AdHouse
			}
			break;
			/*AdHouse*/
		case M.GAMEADD:
			if(Counter>100)
				DrawTexture(gl,mGR.mTex_Skip, .9f,.9f);
			else{
				DrawTexture(gl, mGR.mTex_Hightbar,0.2f,0.9f);
				DrawTexture(gl, mGR.mTex_Pointer,.2f+Counter*(mGR.mTex_Hightbar.width()/100f)-.32f,0.9f);
			}
			break;
			/*AdHouse*/
		case M.GAMEMENU:
			DrawMenu(gl);
			break;
		case M.GAMEPLAY:
			Draw_Gameplay(gl);
			break;
		case M.GAMEPAUSE:
		case M.GAMEOVER:
			DrawPauseOrGover(gl);
			break;
		case M.GAMEHELP:
		case M.GAMEINFO:
		case M.GAMEHIGH:
			DrawHelpInfo(gl);
			break;
		}
	}
	public boolean TouchEvent(MotionEvent event) 
	{
		switch (M.GameScreen){
		case M.GAMELOGO:
			break;
			/*AdHouse*/
		case M.GAMEADD:
			if((MotionEvent.ACTION_UP == event.getAction())&&(Counter>100)&&
					CirCir(0.9f,0.9f, .11f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				M.GameScreen = M.GAMEMENU;
			}
			break;
			/*AdHouse*/
		case M.GAMEMENU:
			HandleMenu(event);
			break;
		case M.GAMEPLAY:
			Handle_Gameplay(event);
			break;
		case M.GAMEPAUSE:
		case M.GAMEOVER:
			HandlePauseOrGameover(event);
			break;	
		case M.GAMEHELP:
		case M.GAMEINFO:
		case M.GAMEHIGH:
			HandleHelpInfo(event);
			break;
		}
		return true;
		
	}
	
	void setAnimation(float x,float y)
	{
		for(int i=0,j=0;i<mGR.mAni.length&&j<30;i++)
		{
			if(mGR.mAni[i].x<-10)
			{
				mGR.mAni[i].set(x, y, 1, (M.mRand.nextBoolean()?M.mRand.nextFloat():-M.mRand.nextFloat())*.01f, (M.mRand.nextBoolean()?M.mRand.nextFloat():-M.mRand.nextFloat())*.01f, 1f);
				j++;
			}
		}
	}
	
	
	void setNew()
	{
		int i=0;
		for(i= 0;i<mGR.mObject.length;i++)
		{
			if(mGR.mObject[i].y<-1.5)
			{
				mGR.mObject[i].set(M.mRand.nextBoolean()?M.mRand.nextFloat():-M.mRand.nextFloat(), 1.5f, 0,-.008f,Counter);
				break;
			}
		}
	}
	void GameLogic()
	{
		int  i,j;
		for(i= 0;i<mGR.mObject.length;i++)
		{
			if(mGR.mObject[i].y>-1.5f)
				mGR.mObject[i].update();
		}
		if(Counter%3000 == 0)
		{
			mGR.mLevel++;
			mGR.mLevelC = 50;
		}
		mGR.mLevelC--;
		if(Counter%1000 == 0)
		{
			mGR.mPower.set(M.mRand.nextBoolean()?M.mRand.nextFloat():-M.mRand.nextFloat(), 1.2f, (5+M.mRand.nextInt(5))/1000f,-.008f,0);
			mGR.mPower.color = M.mRand.nextInt(4);
			System.out.println(mGR.mPower.color+"  ~~~!!!!!!~~~~~~~ "+mGR.mPower.x+"  ~~~~~!!!!!!!1~~~~~ "+ mGR.mPower.y);
		}
		if(Counter%20 == 0)
		{
			setNew();
		}
		for(i= 0;i<mGR.mAni.length;i++)
		{
			if(mGR.mAni[i].y>-1.5f && mGR.mAni[i].y<1.5f && mGR.mAni[i].x>-1.5f && mGR.mAni[i].x<1.5f)
				mGR.mAni[i].update();
		}
		
		for(j=i=0;i<mGR.mBullete.length;i++)
		{
			if(mGR.mBullete[i].y>-1.5f && mGR.mBullete[i].y<1.1f && mGR.mBullete[i].x>-1.5f && mGR.mBullete[i].x<1.5f)
				mGR.mBullete[i].update();
			else if(j==0 && Counter%2==0)
			{
				mGR.mBullete[i].set(mGR.mPlayer.x, mGR.mPlayer.y, mGR.mPlayer.vx, mGR.mPlayer.vy,mGR.mPlayer.ActivePower);j++;
			}
		}
		for(i= 0;i<mGR.mObject.length;i++)
		{
			if(mGR.mObject[i].y>-1.5f&&mGR.mObject[i].y<1.5f&&mGR.mObject[i].x>-1.5f&&mGR.mObject[i].x<1.5f)
			{
				for(j=0;j<mGR.mBullete.length;j++)
				{
					if(mGR.mBullete[j].y>-1.5f&&mGR.mBullete[j].y<1.0f&&mGR.mBullete[j].x>-1.5f&&mGR.mBullete[j].x<1.5f)
					{
						float dx = mGR.mTex_OppPlan[mGR.mObject[i].pNo][0].width()*.4f;
						if(mGR.mObject[i].pNo < 5)
							dx = mGR.mTex_OppPlan[0][0].Height()*.9f;
						if(CircRectsOverlap(mGR.mBullete[j].x, mGR.mBullete[j].y, mGR.mTex_Bullet[0].width()*.4f, mGR.mTex_Bullet[0].width()*.4f, mGR.mObject[i].x, mGR.mObject[i].y,  dx*2))
						{
							mGR.mObject[i].mLife-=(mGR.mBullete[j].mPower+1);
							mGR.mScore+=(mGR.mBullete[j].mPower+1);
							if(mGR.mObject[i].mLife <= 0)
							{
								setAnimation( mGR.mObject[i].x, mGR.mObject[i].y);
								mGR.mObject[i].x  = mGR.mObject[i].y = -100;
								mGR.mObject[i].vx = mGR.mObject[i].vy = 0;
								M.sound1(mGR.mContext, R.drawable.blast1);
							}
							mGR.mBullete[j].x =-10;
						}
					}
				}
			}
		}
		
		if(mGR.mPower.y>-1.5f&&mGR.mPower.y<1.5f&&mGR.mPower.x>-1.5f&&mGR.mPower.x<1.5f)
		{
			if(CircRectsOverlap(mGR.mPlayer.x, mGR.mPlayer.y, mGR.mTex_Player[0].width()*.4f, mGR.mTex_Player[0].width()*.4f, mGR.mPower.x, mGR.mPower.y,  mGR.mTex_Power[0].width()))
			{
				mGR.mPower.y = mGR.mPower.x = -100;
				if(mGR.mPower.color<3)
					mGR.mPlayer.Power[mGR.mPower.color]++ ;
				else{
					M.Plife +=5;
					if(M.Plife>M.Max_Plife)
						M.Plife=M.Max_Plife;
				}
				
			}
		}
		{
			mGR.mPower.x+=mGR.mPower.vx;
			mGR.mPower.y+=mGR.mPower.vy;
			if(mGR.mPower.x>1)
			{
				mGR.mPower.vx = -Math.abs(mGR.mPower.vx);
			}
			if(mGR.mPower.x<-1)
			{
				mGR.mPower.vx = Math.abs(mGR.mPower.vx);
			}
		}
		mGR.mElectric.update();
		if(mGR.mElectric.y<1.2f)
		{
			mGR.mElectric.update();
			for(i= 0;i<mGR.mObject.length;i++)
			{
				if(mGR.mObject[i].y>-1.5f&&mGR.mObject[i].y<1.5f&&mGR.mObject[i].x>-1.5f&&mGR.mObject[i].x<1.5f)
				{
					if(CircRectsOverlap(mGR.mElectric.x, mGR.mElectric.y, mGR.mTex_shield[0].width()*.5f, mGR.mTex_shield[0].width()*.4f, mGR.mObject[i].x, mGR.mObject[i].y,  mGR.mTex_OppPlan[mGR.mObject[i].pNo][0].width()*.4f))
					{
						mGR.mScore+=mGR.mObject[i].mLife;
						mGR.mObject[i].mLife=0;
						setAnimation( mGR.mObject[i].x, mGR.mObject[i].y);
						mGR.mObject[i].x  = mGR.mObject[i].y = -100;
						mGR.mObject[i].vx = mGR.mObject[i].vy = 0;
						M.sound5(mGR.mContext, R.drawable.electric);
					}
				}
			}
		}
		if(mGR.mPlayer.ActiveCounter>0)
			mGR.mPlayer.ActiveCounter--;
		else
			mGR.mPlayer.ActivePower = 0;
		if(mGR.mScore>mGR.mHighScore)
		{
			mGR.mHighScore = mGR.mScore;
		}
	}
	
	void Draw_Gameplay(GL10 gl)
	{
		int i =0;
		for(i= 0;i<mGR.mObject.length;i++)
		{
			if(mGR.mObject[i].y>-1.5 &&mGR.mObject[i].y<1.5)
				DrawTransScal(gl, mGR.mTex_OppPlan[mGR.mObject[i].pNo][Counter%2], mGR.mObject[i].x, mGR.mObject[i].y,2,1);
		}
		for(i= 0;i<mGR.mAni.length;i++)
		{
			if(mGR.mAni[i].x>-1.5 &&mGR.mAni[i].x<1.5&&mGR.mAni[i].y>-1.5 &&mGR.mAni[i].y<1.5)
				DrawTransScal(gl, mGR.mTex_Smok[mGR.mAni[i].cl], mGR.mAni[i].x, mGR.mAni[i].y, mGR.mAni[i].z,mGR.mAni[i].vz);
		}
		for(i= 0;i<mGR.mBullete.length;i++)
		{
			if(mGR.mBullete[i].x>-1.5 &&mGR.mBullete[i].x<1.5&&mGR.mBullete[i].y>-1.5 &&mGR.mBullete[i].y<1.5)
			{
				if(mGR.mBullete[i].mPower == 1)
				{
					DrawTransScal(gl, mGR.mTex_Bullet[mGR.mBullete[i].color], mGR.mBullete[i].x-.005f, mGR.mBullete[i].y,2,1);
					DrawTransScal(gl, mGR.mTex_Bullet[mGR.mBullete[i].color], mGR.mBullete[i].x+.005f, mGR.mBullete[i].y,2,1);
				}
				else if(mGR.mBullete[i].mPower == 2)
					DrawTexture(gl, mGR.mTex_PBullet, mGR.mBullete[i].x, mGR.mBullete[i].y);
				else
					DrawTransScal(gl, mGR.mTex_Bullet[mGR.mBullete[i].color], mGR.mBullete[i].x, mGR.mBullete[i].y,2,1);
				
			}
		}
		if(mGR.mPower.y<1&&mGR.mPower.y>-1)
		{
			DrawTransScal(gl, mGR.mTex_Power[mGR.mPower.color], mGR.mPower.x, mGR.mPower.y,1,1);
		}
		
		DrawTexture(gl, mGR.mTex_Player[Counter%2], mGR.mPlayer.x, mGR.mPlayer.y);
		if(mGR.mElectric.y < 1.2)
			DrawTexture(gl, mGR.mTex_shield[Counter%2], mGR.mElectric.x, mGR.mElectric.y);
		
		DrawTexture(gl, mGR.mTex_Score,-.9f, .9f);
//		DrawTexture(gl, mGR.mTex_ScoreBox,-.60f, .9f);
		drawNumber (gl, mGR.mScore		 ,-.75f, .9f);
		
		DrawTexture(gl, mGR.mTex_Level,-.90f, .75f);
		drawNumber (gl, mGR.mLevel	  ,-.75f, .75f);
		if(mGR.mLevelC>0)
			DrawTexture(gl, mGR.mTex_LevelUp,0, 0);
		DrawTexture(gl, mGR.mTex_Pause,0.85f, .85f);
		
		DrawTexture(gl, mGR.mTex_lifebaar,0, .9f);
		mGR.mTex_Bullet[1].drawSS(gl,-.28f, .9f, M.Plife, 2);
		
		
		for(i=0;i<3;i++)
		{
			if(mGR.mPlayer.Power[i]>0)
				DrawTexture(gl, mGR.mTex_Power[i],0.9f,0.0f-(i*.3f));
			else
				DrawTransScal(gl, mGR.mTex_Power[i],0.9f,0.0f-(i*.3f),1,.4f);
		}
		
		
		GameLogic();
	}
	boolean Handle_Gameplay(MotionEvent event)
	{
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			if(CircRectsOverlap(0.85f, .85f, mGR.mTex_Pause.width()*.5f, mGR.mTex_Pause.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				M.GameScreen = M.GAMEPAUSE;
				M.stop();
			}
			for(int i=0;i<3;i++)
			{
				if(mGR.mPlayer.Power[i]>0)
				{
					if(CircRectsOverlap(0.9f,0.0f-(i*.3f), mGR.mTex_Power[0].width()*.5f, mGR.mTex_Power[0].Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
					{
						if(i==0 && mGR.mPlayer.ActivePower!=1)
						{
							mGR.mPlayer.Power[i] --;
							mGR.mPlayer.ActivePower = i+1;
							mGR.mPlayer.ActiveCounter = 500;
						}
						if(i==1 && mGR.mPlayer.ActivePower!=2)
						{
							mGR.mPlayer.Power[i] --;
							mGR.mPlayer.ActivePower = i+1;
							mGR.mPlayer.ActiveCounter = 500;
						}
						if(mGR.mElectric.y > 1.1 && i == 2)
						{
							mGR.mPlayer.Power[i] --;
							mGR.mElectric.set(0, -1.1f, 0, .05f,0);
						}
					}
				}
			}
		}
		
		if(screen2worldY(event.getY())<.8f)
		{
			mGR.mPlayer.x = screen2worldX(event.getX()); mGR.mPlayer.y = screen2worldY(event.getY());
		}
		return true;
	}
	
	
	
	void DrawMenu(GL10 gl)
	{
	   float x  =-.75f;	
	   float x1 =+.85f;
	   float y  =-.20f;	
	   float y1 =+.29f;
	   DrawTexture(gl,mGR.mTex_Splash,0,0);
	   
	   DrawTexture(gl,mGR.mTex_NewGameBtn	,x,0.30f+y);
	   DrawTexture(gl,mGR.mTex_HelpBtn		,x,0.05f+y);
	   DrawTexture(gl,mGR.mTex_AbtUsBtn		,x,-.20f+y);
	   DrawTexture(gl,mGR.mTex_HighScoreBtn	,x,-.45f+y);
	   
//	   DrawTexture(gl,mGR.mTex_RateUsBtn	,x1,-.35f+y1);
	   DrawTexture(gl,mGR.mTex_ShareBtn		,x1,-.6f+y1);
	   if(M.setValue)
	     DrawTexture(gl,mGR.mTex_SoundBtn[0],x1,-.85f+y1);
	   else
		 DrawTexture(gl,mGR.mTex_SoundBtn[1],x1,-.85f+y1);
	   
	   DrawTexture(gl,mGR.mTex_Exit,.85f,-.85f);
	   
	   switch(mGR.mSel)
	   {
	     case 1:
	    	 DrawTexture(gl,mGR.mTex_Selector,x ,0.30f+y);
			 break;
		 case 2:
			 DrawTexture(gl,mGR.mTex_Selector,x ,0.05f+y);
			 break;
		 case 3:
			 DrawTexture(gl,mGR.mTex_Selector,x ,-.20f+y);
			 break;
		 case 4:
			 DrawTexture(gl,mGR.mTex_Selector,x ,-.45f+y);
			break;
		 case 5:
			 DrawTexture(gl,mGR.mTex_SmallSel,x1,-.35f+y1);
			break;
		 case 6:
			 DrawTexture(gl,mGR.mTex_SmallSel,x1,-.60f+y1);
			break;
		 case 7:
			 DrawTexture(gl,mGR.mTex_SmallSel,x1,-.85f+y1);
			break;		
		 case 8:
			 DrawTexture(gl,mGR.mTex_SmallSel,.85f,-.85f);
			break;
	    		
	   } 
       		   
	}
	
	boolean HandleMenu(MotionEvent event)
	{
		mGR.mSel=0;
		float x  =-.75f;	
		float x1 =+.85f;
		float y  =-.20f;	
		float y1 =+.29f;
	    float dx = mGR.mTex_NewGameBtn.width()/2;
	    float dy = mGR.mTex_NewGameBtn.Height()/2;
	    
	    if(CircRectsOverlap(x,.3f+y,dx,dy,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	    	mGR.mSel=1; // New Game
	    if(CircRectsOverlap(x,.05f+y,dx,dy,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	    	mGR.mSel=2; // Help
	    if(CircRectsOverlap(x,-.2f+y,dx,dy,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	    	mGR.mSel=3; // AboutUs
	    if(CircRectsOverlap(x,-.45f+y,dx,dy,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	    	mGR.mSel=4; // HighScore
//	    if(CircRectsOverlap(x1,-.35f+y1,dx/2,dy,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
//	    	mGR.mSel=5; // RateUs
	    if(CircRectsOverlap(x1,-.6f+y1,dx/2,dy,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	    	mGR.mSel=6; // Share
	    if(CircRectsOverlap(x1,-.85f+y1,dx/2,dy,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	    	mGR.mSel=7; // Sound
	    if(CircRectsOverlap(.85f,-.85f,dx/2,dy,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	    	mGR.mSel=8; // Exit
		
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			
			switch(mGR.mSel)
			{
				 case 1:
					 M.play(mGR.mContext, R.drawable.bgsound);
					 M.GameScreen = M.GAMEPLAY;
					 mGR.gameReset();
					 break;
				 case 2:
					  M.GameScreen = M.GAMEHELP;
					 break;
				 case 3:
					  M.GameScreen = M.GAMEINFO;
					 break;
				 case 4:
					 M.GameScreen = M.GAMEHIGH;
//					 GameRenderer.mStart.CallLeaderBord();
					 break;	
				 case 5:
					 RateUs();
					 break;
				 case 6:
					 share2friend();
					 break;
				 case 7:
					 M.setValue =!M.setValue;
					 break;	 
				 case 8:
					 GameRenderer.mStart.get();
					 break;
			}
			mGR.mSel =0;
		}
		return true;
	}
	
	void DrawPauseOrGover(GL10 gl)
	{
		DrawTexture(gl,mGR.mTex_UIbg,0,0);
	 	DrawTexture(gl,mGR.mTex_PopUp,0,-0.25f);
	 	if(M.GameScreen == M.GAMEPAUSE)
	 	    DrawTexture(gl,mGR.mTex_GamePauseTitle,0,0.55f);
	 	else
	 		DrawTexture(gl,mGR.mTex_GameOverTitle,0,0.55f);
	 	
	 	
	 	DrawTexture(gl,mGR.mTex_NewScore,-0.3f,.32f);
	 	DrawTexture(gl,mGR.mTex_ScoreBox,0.25f,.30f);
	 	String str = mGR.mScore+"";
		float dx   = (str.length()*mGR.mTex_Font[0].width()/2)/2;
	 	drawNumber(gl, mGR.mScore,0.25f-dx,.30f);
	 	
//	 	DrawTexture(gl,mGR.mTex_Submitt[0],0.65f,.32f);
	 	
	 	DrawTexture(gl,mGR.mTex_BestScore,-0.3f,.12f);
	 	DrawTexture(gl,mGR.mTex_ScoreBox,0.25f,.1f);
	 	String str1 = mGR.mHighScore+"";
		float dx1   = (str1.length()*mGR.mTex_Font[0].width()/2)/2;
	 	drawNumber(gl, mGR.mHighScore,0.25f-dx1,.1f);
	 	
	 	DrawTexture(gl,mGR.mTex_HighScoreBtn,-0.25f,-0.1f);
	 	DrawTexture(gl,mGR.mTex_HututTitle,0.25f,-0.1f);
	 	
	 	if(M.setValue)
	 	  DrawTexture(gl,mGR.mTex_SoundBtn[0],-0.35f,-0.35f);
	 	else
	  	  DrawTexture(gl,mGR.mTex_SoundBtn[1],-0.35f,-0.35f);
	 	
	 	DrawTexture(gl,mGR.mTex_MenuBtn,0,-0.35f);
	 	if(M.GameScreen == M.GAMEPAUSE)
	 	  DrawTexture(gl,mGR.mTex_Continue,0.35f,-0.35f);
	 	else
	 	  DrawTexture(gl,mGR.mTex_Replay,0.35f,-0.35f);
	 	
	 	DrawTexture(gl,mGR.mTex_ShareBtn,-0.2f,-0.62f);
//	 	DrawTexture(gl,mGR.mTex_RateUsBtn,0.2f,-0.62f);
	 	switch(mGR.mSel)
	 	{
	 	  case 1:
	 		   DrawTexture(gl,mGR.mTex_Selector,-0.25f,-0.1f); 
	 		  break;
	 	  case 2:
	 		   DrawTexture(gl,mGR.mTex_Selector,0.25f,-0.1f);
	 		  break;
	 	  case 3:
	 		   DrawTexture(gl,mGR.mTex_SmallSel,-0.35f,-0.35f);
	 		  break;
	 	  case 4:
	 		   DrawTexture(gl,mGR.mTex_SmallSel,0,-0.35f);
	 		  break;
	 	   case 5:
	 		   DrawTexture(gl,mGR.mTex_SmallSel,0.35f,-0.35f);
	 		  break;
	 	   case 6:
	 		    DrawTexture(gl,mGR.mTex_SmallSel,-0.2f,-0.62f);
	 		  break;	    
	 	  case 7:
	 		    DrawTexture(gl,mGR.mTex_SmallSel,0.2f,-0.62f);
	 		  break;
	 	   case 8:	  
	 		    DrawTexture(gl,mGR.mTex_Submitt[1],0.65f,.32f);
	 		 break;
	 	}
	 	
	}
	boolean HandlePauseOrGameover(MotionEvent event)
	{
		mGR.mSel=0;
		float dx =mGR.mTex_HighScoreBtn.width()/2;
		float dy =mGR.mTex_HighScoreBtn.Height()/2;
		if(CircRectsOverlap(-0.25f,-0.1f,dx,dy,screen2worldX(event.getX()),screen2worldY(event.getY()), .05f))
			mGR.mSel=1; //HighScore
		if(CircRectsOverlap(0.25f,-0.1f,dx,dy,screen2worldX(event.getX()),screen2worldY(event.getY()), .05f))
			mGR.mSel=2; //MoreGame
		if(CircRectsOverlap(-0.35f,-0.35f,dx/2,dy,screen2worldX(event.getX()),screen2worldY(event.getY()), .05f))
			mGR.mSel=3; //Sound
		if(CircRectsOverlap(0f,-0.35f,dx/2,dy,screen2worldX(event.getX()),screen2worldY(event.getY()), .05f))
			mGR.mSel=4; //Menu
		if(CircRectsOverlap(0.35f,-0.35f,dx/2,dy,screen2worldX(event.getX()),screen2worldY(event.getY()), .05f))
			mGR.mSel=5; //Continue //Replay
		if(CircRectsOverlap(-0.2f,-0.62f,dx/2,dy,screen2worldX(event.getX()),screen2worldY(event.getY()), .05f))
			mGR.mSel=6; //Share
//		if(CircRectsOverlap(0.2f,-0.62f,dx/2,dy,screen2worldX(event.getX()),screen2worldY(event.getY()), .05f))
//			mGR.mSel=7; //RateUs
//		if(CircRectsOverlap(0.65f,0.32f,dx/2,dy,screen2worldX(event.getX()),screen2worldY(event.getY()), .05f))
//			mGR.mSel=8; //Submitt
		
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			switch(mGR.mSel)
			{
			 case 1:
				 M.GameScreen = M.GAMEHIGH;
//				  GameRenderer.mStart.CallLeaderBord();
				 break;
			 case 2:
				  MoreGame();
				 break;
			 case 3:
				  M.setValue =!M.setValue;
				 break;
			 case 4:
				  M.GameScreen = M.GAMEMENU;
				 break;
			 case 5:
				 if(M.GameScreen == M.GAMEPAUSE)
				 {
					 M.GameScreen = M.GAMEPLAY;
				 }
				 else
				 {
					 mGR.gameReset();
					 M.GameScreen = M.GAMEPLAY;
				 }
				 M.play(mGR.mContext, R.drawable.bgsound);
				 break;
			 case 6:
				  share2friend();
				 break;
			 case 7:
				  RateUs();
				 break;
			 case 8:
//				 GameRenderer.mStart.SubmitScore();
				 break;
				 
			}
			mGR.mSel=0;
		}
		
		
		return true;
	}
	
	
	void DrawHelpInfo(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_UIbg, 0, 0);
		DrawTexture(gl, mGR.mTex_PopUp, 0, -0.25f);
		if (M.GameScreen == M.GAMEHELP) {
			DrawTexture(gl, mGR.mTex_HelpTitle, 0, 0.55f);
			DrawTexture(gl, mGR.mTex_HelpTxt, 0, 0);
		}
		if (M.GameScreen == M.GAMEINFO) {
			DrawTexture(gl, mGR.mTex_AbtTitle, 0, 0.55f);
			DrawTexture(gl, mGR.mTex_AbtUsTxt, 0, 0f);
		}
		if (M.GameScreen == M.GAMEHIGH) {
			DrawTexture(gl, mGR.mTex_HighScoreBtn, 0, 0.55f);
			drawNumber(gl, mGR.mHighScore, -.1f, 0);
//			DrawTexture(gl, mGR.mTex_AbtUsTxt, 0, 0f);
		}
		DrawTexture(gl, mGR.mTex_ShareBtn, -0.2f, -0.6f);
//		DrawTexture(gl, mGR.mTex_RateUsBtn, 0.2f, -0.6f);
		DrawTexture(gl, mGR.mTex_backBtn, .85f, -.85f);
		if (mGR.mSel == 100)
			DrawTexture(gl, mGR.mTex_SmallSel, .85f, -.85f);
		switch (mGR.mSel) {
		case 1:
			DrawTexture(gl, mGR.mTex_SmallSel, -0.2f, -0.6f);
			break;
		case 2:
			DrawTexture(gl, mGR.mTex_SmallSel, 0.2f, -0.6f);
			break;
		}
	}
	boolean HandleHelpInfo(MotionEvent event)
	{
		mGR.mSel=0;
		if(CircRectsOverlap(-.2f,-.6f,mGR.mTex_ShareBtn.width()/2,mGR.mTex_ShareBtn.Height()/2,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
			mGR.mSel=1;//Share
//		if(CircRectsOverlap(.2f,-.6f,mGR.mTex_RateUsBtn.width()/2,mGR.mTex_RateUsBtn.Height()/2,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
//			mGR.mSel=2;//RateUs
		if(CircRectsOverlap(.85f,-.85f,mGR.mTex_backBtn.width()/2,mGR.mTex_backBtn.Height()/2,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
			mGR.mSel=100;
		
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			
			switch(mGR.mSel)
			{
			  case 1:
				   share2friend();
				   break;
			  case 2:
                    RateUs();
				   break;
			  case 100:
				   M.GameScreen = M.GAMEMENU;
				   break;
			}
			mGR.mSel=0;
		}
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	void DrawTexture(GL10 gl,SimplePlane Tex,float x,float y)
	{
		Tex.drawPos(gl, x, y);
	}
	
	void DrawTextureR(GL10 gl,SimplePlane Tex,float x,float y,float angle)
	{
		Tex.drawRotet(gl, 0,0,angle, x, y);
	}
	void DrawTextureS(GL10 gl,SimplePlane Tex,float x,float y,float scal)
	{
		Tex.drawScal(gl, x, y,scal,scal);
	}
	void DrawFlip(GL10 gl,SimplePlane Tex,float x,float y)
	{
		Tex.drawFilp(gl, x, y);
	}
	void DrawTransScal(GL10 gl,SimplePlane Tex,float x,float y, float z,float t)
	{
		Tex.drawTransprentScal(gl, x, y, z, t);
	}
	boolean CircRectsOverlap(double CRX,  double CRY,double CRDX,double CRDY ,double centerX,double centerY, double radius)
    {
        if ((Math.abs(centerX - CRX) <= (CRDX + radius)) && (Math.abs(centerY - CRY) <= (CRDY + radius)))
           return true;
        return false ;

    }
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
	boolean Rect2RectIntersection(float ax,float ay,float adx,float ady,float bx,float by,float bdx,float bdy)
	{
		if( ax+adx > bx  && ay+ady > by && bx+bdx > ax && by+bdy> ay)
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
	double GetAngle(double d,double e)
	{
	  if(d==0)
		  return e>=0 ? Math.PI/2 : -Math.PI/2;
	  else if (d > 0)
		  return Math.atan(e/d);
	  else
		  return Math.atan(e/d) + Math.PI;
	}
	void drawNumber(GL10 gl,int no,float x, float y)
	{
		float dx = mGR.mTex_Font[0].width();
		 String strs = ""+no;
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i))-48;
			if(k>=0 && k<mGR.mTex_Font.length)
				mGR.mTex_Font[k].drawPos(gl,x+i*dx,y);
		}
	}
	void RateUs()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
//		mIntent.setData(Uri.parse(M.LINK+getClass().getPackage().getName()));
		mIntent.setData(Uri.parse(M.MARKET));
	    mGR.mContext.startActivity(mIntent);
	}
	void MoreGame()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.MARKET));
		mGR.mContext.startActivity(mIntent);
	}
	void share2friend()
	{
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("text/plain");
		i.putExtra(Intent.EXTRA_SUBJECT,"Roking new Game '"+mGR.mContext.getResources().getString(R.string.app_name)+"'");
		i.putExtra(Intent.EXTRA_TEXT,"Let the battle commence!!! Download it now and let’s ROCK!!!!  https://play.google.com/store/apps/details?id="+getClass().getPackage().getName());
		try {
		    mGR.mContext.startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(GameRenderer.mStart, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
}
