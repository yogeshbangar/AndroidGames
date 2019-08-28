package com.hututu.game.reversegravity;
import javax.microedition.khronos.opengles.GL10;


import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
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
//		M.GameScreen = M.GAMEOVER;
		switch (M.GameScreen) {
		case M.GAMELOGO:
			DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if(Counter>80){
				M.GameScreen = M.GAMEMENU;
				M.GameScreen = M.GAMEADD;//AdHouse
				Counter=0;//AdHouse
			}
			break;
			/*AdHouse*/
		case M.GAMEADD:
			if(Counter>100)
				DrawTexture(gl,mGR.mTex_Skip, .9f,0.9f);
			else{
				DrawTexture(gl, mGR.mTex_Hightbar,0.2f,0.9f);
				DrawTexture(gl, mGR.mTex_Pointer,Counter*(mGR.mTex_Hightbar.width()/100f)-.32f,0.9f);
			}
			break;
			/*AdHouse*/
		case M.GAMEMENU:
			DrawMenu(gl);
			break;
		case M.GAMEPLAY:
			Draw_GamePlay(gl);
			break;
		case M.GAMEPAUSE:
		case M.GAMEOVER:
			DrawPauseOrGover(gl);
			break;
		case M.GAMEHELP:
		case M.GAMEINFO:
			DrawHelpInfo(gl);
			break;
		case M.GAMEHIGH:
			DrawHighScore(gl);
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
			Handle_GamePlay(event);
			break;
		case M.GAMEPAUSE:
		case M.GAMEOVER:
			HandlePauseOrGameover(event);
			break;	
		case M.GAMEHELP:
		case M.GAMEINFO:
			HandleHelpInfo(event);
			break;
		case M.GAMEHIGH:
			HandleHigh(event);
			break;
		
		}
		return true;
		
	}
	void DrawHighScore(GL10 gl)
	{
		DrawTexture(gl,mGR.mTex_StarBG,0,0);
 	   	DrawTexture(gl,mGR.mTex_PopUp,0,0);
 	   	DrawTexture(gl,mGR.mTex_HighScoreBtn,0,0.3f);
 	   	String str1 = mGR.mHScore+"";
		float dx1   = (str1.length()*mGR.mTex_Font[0].width()/2)/2;
	 	drawNumber(gl, mGR.mHScore,-dx1,.0f);
	 	DrawTexture(gl,mGR.mTex_backBtn,.75f,0.95f);
	}
	
	boolean HandleHigh(MotionEvent event)
	{
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			if(CircRectsOverlap(.75f,0.95f,mGR.mTex_backBtn.width()/2,mGR.mTex_backBtn.Height()/2,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
			{
				M.GameScreen = mGR.hss;
			}
		}
		return true;
	}
	void setAnimation(float x,float y)
	{
		for(int i=0,j=0;i<mGR.mAni.length&&j<50;i++)
		{
			if(mGR.mAni[i].x<-10)
			{
				mGR.mAni[i].set(x, y, 1, (M.mRand.nextBoolean()?M.mRand.nextFloat():-M.mRand.nextFloat())*.02f, (M.mRand.nextBoolean()?M.mRand.nextFloat():-M.mRand.nextFloat())*.02f, 1f);
				j++;
			}
		}
	}
	void GameLogic()
	{
		int i;
		mGR.mPlayer.x-=mGR.Ax/50f;
		mGR.mPlayer.vx=mGR.Ax/250f;
//		if(mGR.Ax>0)
//			mGR.mPlayer.x-=mGR.Ax/50f;
//		else
//			mGR.mPlayer.x+=.04f;
//		
		mGR.mPlayer.mtap++;
		mGR.mPlayer.y += mGR.mPlayer.vy;
		mGR.mPlayer.update();
		if(mGR.mPlayer.vy < .06f)
			mGR.mPlayer.vy+=.002f;
		
//		System.out.println("mGR.mPlayer.vy = "+mGR.mPlayer.vy);
		for(i=0;i<mGR.mPad.length;i++)
		{
			mGR.mPad[i].Update();
			if(mGR.mPad[i].Gift !=-3 && CircRectsOverlap(mGR.mPad[i].x, mGR.mPad[i].y-mGR.mTex_Pad.Height()*.1f, mGR.mTex_Pad.width()*.5f, mGR.mTex_Pad.Height()*.4f, mGR.mPlayer.x, mGR.mPlayer.y, mGR.mTex_Ball[3].Height()*.5f))
			{
				if(mGR.mPlayer.vy > 0.007)
				{
					mGR.mPlayer.mtap = 0;
					mGR.mPlayer.vy = -.03f;
					mGR.mPlayer.y = mGR.mPad[i].y-mGR.mTex_Pad.Height()*1.6f;
					mGR.mPad[i].vy=.02f;
					
					if(mGR.mPad[i].Gift == -1)
					{
						mGR.mScore+=5;
						mGR.mPad[i].Gift =-2;
						if(mGR.mScore > mGR.mHScore)
							mGR.mHScore = mGR.mScore;
						M.sound1(mGR.mContext, R.drawable.tap);
						
					}
				}
				else
				{
					mGR.mPlayer.y = mGR.mPad[i].y-mGR.mTex_Pad.Height()*.8f;
					mGR.mPlayer.vy = 0;
				}
				if(mGR.mPad[i].Gift == 8)
					mGR.mPad[i].Gift = -3;
				if(mGR.mPad[i].x > mGR.mPlayer.x-.2f && mGR.mPad[i].x < mGR.mPlayer.x+.2f && mGR.mPad[i].Gift >-1)
				{
					for(int j=0;j<mGR.mPoint.length && mGR.mPad[i].Gift >-1 && mGR.mPad[i].Gift < 7;j++)
					{
						if(mGR.mPoint[j].y>1.0)
						{
							mGR.mPoint[j].set(mGR.mPad[i].x, mGR.mPad[i].y,-.01f, mGR.mPad[i].Gift);
							setAnimation(mGR.mPlayer.x, mGR.mPlayer.y);
							if(mGR.mPad[i].Gift < 3)
							{
								mGR.mScore+=(mGR.mPad[i].Gift+1)*50;
								M.sound2(mGR.mContext, R.drawable.gift1);
							}
							else if(mGR.mPad[i].Gift < 6)
							{
								mGR.mScore+=((mGR.mPad[i].Gift+1)*100)-50;
								if(mGR.mPad[i].Gift == 5)
								{
									mGR.mSpeedC = 300;
									mGR.mSpeedN	= 0;
								}
								M.sound3Play(mGR.mContext, R.drawable.gift2);
							}
							else
							{
								mGR.mScore+=((mGR.mPad[i].Gift+1)*100);
								mGR.mSpeedC = 300;
								mGR.mSpeedN	= 1;
								M.sound4(mGR.mContext, R.drawable.gift3);
							}
//							System.out.println(mGR.mPad[i].x+" ~~~~~~~~~ "+mGR.mPad[i].y +"   "+mGR.mPoint[j].x+" ~~~ "+mGR.mPoint[j].y);
							break;
						}
					}
					if(mGR.mPad[i].Gift == 7)
					{
						setAnimation(mGR.mPad[i].x, mGR.mPad[i].y);
						mGR.mPlayer.mLife--;
						M.sound5(mGR.mContext, R.drawable.blast);
					}
					if(mGR.mScore > mGR.mHScore)
						mGR.mHScore = mGR.mScore;
					mGR.mPad[i].Gift = -2;
				}
			}
		}
		for(i=0;i<mGR.mPad.length;i++)
		{
			if(mGR.mPad[i].y < - 0.5f)
			{
				if(i == 0)
					mGR.mPad[i].set((M.mRand.nextInt()%80)/100f, mGR.mPad[mGR.mPad.length-1].y + (M.DIFF));
				else
					mGR.mPad[i].set((M.mRand.nextInt()%80)/100f, mGR.mPad[i-1].y + (M.DIFF));
				if(M.mRand.nextInt(4)==0)
				{
					mGR.mPad[i].Gift = M.mRand.nextInt(8); 
				}
				if(M.mRand.nextInt(5)==0 && mGR.mBGY < -7)
				{
					mGR.mPad[i].Gift = 8;
				}
			}
		}
		mGR.mBGY+=M.BGSPEED;
		if(1+Math.abs((int)mGR.mBGY) > mGR.mLevel)
		{
			mGR.mLevel ++;
			mGR.levelUp = 100;
		}
		
		if(mGR.mBGY<-7)
		{
			mGR.mBGSY1+=M.BGSPEED/2;
			mGR.mBGSY2+=M.BGSPEED/2;
			if(mGR.mBGSY2<-2)
			{
				mGR.mBGSY2=mGR.mBGSY1+2;
			}
			if(mGR.mBGSY1<-2)
			{
				mGR.mBGSY1=mGR.mBGSY2+2;
			}
		}
		for(i=0;i<mGR.mObject.length;i++)
		{
			mGR.mObject[i].update();
		}
		for(i=0;i<mGR.mPoint.length;i++)
		{
			if(mGR.mPoint[i].y<1.1)
				mGR.mPoint[i].update();
		}
		for(i= 0;i<mGR.mAni.length;i++)
		{
			if(mGR.mAni[i].y>-1.5f && mGR.mAni[i].y<1.5f && mGR.mAni[i].x>-1.5f && mGR.mAni[i].x<1.5f)
				mGR.mAni[i].update();
		}
		if(mGR.mSpeedC > -1)
			mGR.mSpeedC --;
		if(mGR.levelUp > -1)
			mGR.levelUp --;
		if(mGR.mSpeedC > 0 && mGR.mSpeedN == 0)
		{
			M.SPEED	= M.SSPEED;
		}
		if(mGR.mSpeedC > 0 && mGR.mSpeedN == 1)
		{
			M.SPEED	= M.FSPEED;
		}
		if(mGR.mSpeedC <= 0)
		{
			M.SPEED	= M.NSPEED;
		}
		if(mGR.mPlayer.mLife < 1 || mGR.mPlayer.y <-1.2f || mGR.mPlayer.y >1.2)
		{
			M.GameScreen = M.GAMEOVER;
		}
	}
	
	void Draw_GamePlay(GL10 gl)
	{
		int i =0;
		for(i=0;i<mGR.mTex_BG.length;i++)
		{
			if((i*2+mGR.mBGY)>-2.1 && (i*2+mGR.mBGY)<2.1)
			{
				if(i==0)
					DrawTexture(gl, mGR.mTex_BG[i], 0, i*2+mGR.mBGY);
				else
					DrawTextureS(gl, mGR.mTex_BG[i], 0, i*2+mGR.mBGY,15,1);
			}
		}
		if(mGR.mBGSY1 < 2)
			DrawTexture(gl, mGR.mTex_StarBG, 0, mGR.mBGSY1);
		if(mGR.mBGSY2 < 2)
			DrawTexture(gl, mGR.mTex_StarBG, 0, mGR.mBGSY2);
		
		for(i=0;i<mGR.mObject.length;i++)
		{
			if(mGR.mObject[i].y>-1.5f && mGR.mObject[i].y < 1.5f)
				DrawTexture(gl, mGR.mTex_Object[i], mGR.mObject[i].x, mGR.mObject[i].y);
		}
		
		for(i=0;i<mGR.mPad.length;i++)
		{
			if(mGR.mPad[i].Gift > -3 && mGR.mPad[i].Gift != 8)
				DrawTexture(gl, mGR.mTex_Pad, mGR.mPad[i].x, mGR.mPad[i].y);
			if(mGR.mPad[i].Gift == 8)
				DrawTransScal(gl, mGR.mTex_Pad, mGR.mPad[i].x, mGR.mPad[i].y,1,.5f);
			if(mGR.mPad[i].Gift >= 0 && mGR.mPad[i].Gift < 8)
			{
				DrawTexture(gl, mGR.mTex_Gift[mGR.mPad[i].Gift], mGR.mPad[i].x, mGR.mPad[i].y-.09f);
			}
		}
		for(i=0;i<mGR.mPlayer.x1.length && mGR.mContinue;i++)
			DrawTransScal(gl, mGR.mTex_Ball[4], mGR.mPlayer.x1[i], mGR.mPlayer.y1[i],1-i*.02f,1-i*.1f);
		if(mGR.mPlayer.mtap/3 < 3)
			DrawTexture(gl, mGR.mTex_Ball[mGR.mPlayer.mtap/3], mGR.mPlayer.x, mGR.mPlayer.y);
		else
			DrawTexture(gl, mGR.mTex_Ball[3], mGR.mPlayer.x, mGR.mPlayer.y);
		
		
		for(i=0;i<mGR.mPoint.length;i++)
		{
			if(mGR.mPoint[i].y<1.1)
				DrawTexture(gl, mGR.mTex_Point[mGR.mPoint[i].mPoint], mGR.mPoint[i].x, mGR.mPoint[i].y);
		}
		for(i= 0;i<mGR.mAni.length;i++)
		{
			if(mGR.mAni[i].x>-1.5 &&mGR.mAni[i].x<1.5&&mGR.mAni[i].y>-1.5 &&mGR.mAni[i].y<1.5)
				DrawTransScal(gl, mGR.mTex_Smok[mGR.mAni[i].cl], mGR.mAni[i].x, mGR.mAni[i].y, mGR.mAni[i].z,mGR.mAni[i].vz);
		}
		if(mGR.mSpeedC > 200)
			DrawTexture(gl, mGR.mTex_Speed[mGR.mSpeedN], 0, 0);
		if(mGR.levelUp >0)
			DrawTexture(gl, mGR.mTex_LevelUp, 0, 0.2f);
		for(i = 0;i<5;i++)
		{
			DrawTexture(gl, mGR.mTex_Life[0], .4f+(i*.14f), .8f);
			if(mGR.mPlayer.mLife > (i))
				DrawTexture(gl, mGR.mTex_Life[1], .4f+(i*.14f), .8f);
		}
		
		DrawTexture(gl, mGR.mTex_Score	, -.82f,0.94f);
		drawNumber(gl , mGR.mScore		, -.6f,0.94f);
		
		DrawTexture(gl, mGR.mTex_Level	, -.82f,0.85f);
		drawNumber(gl , mGR.mLevel		, -.6f,0.85f);
		
		if(mGR.mContinue)
		{
			DrawTexture(gl, mGR.mTex_Pause	, 0.78f,0.92f);
			if(mGR.mSel == 1)
				 DrawTexture(gl,mGR.mTex_SmallSel,0.78f,0.92f);
			GameLogic();
		}
		else
		{
			DrawTexture(gl, mGR.mTex_Continue	, 0.0f,0.0f);
		}
	}
	boolean Handle_GamePlay(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(0.78f,0.92f,  mGR.mTex_Pause.width()*.4f,  mGR.mTex_Pause.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 1;
		}
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			mGR.mContinue =true;
			switch (mGR.mSel) {
			case 1:
				M.GameScreen = M.GAMEPAUSE;
				break;
			}
			mGR.mSel = 0;
		}
		return true;
	}
	
	
	void DrawMenu(GL10 gl)
	{
	   float x  =-.55f;	
	   float x1 =+.75f;
	   float y  =0.20f;	
	   float y1 =0.20f;
	   DrawTexture(gl,mGR.mTex_Splash,0,0);
	   
	   DrawTexture(gl,mGR.mTex_NewGameBtn	,x,-.45f+y);
	   DrawTexture(gl,mGR.mTex_HelpBtn		,x,-.60f+y);
	   DrawTexture(gl,mGR.mTex_AbtUsBtn		,x,-.75f+y);
	   DrawTexture(gl,mGR.mTex_HighScoreBtn	,x,-.90f+y);
	   
//	   DrawTexture(gl,mGR.mTex_RateUsBtn	,x1,-.45f+y1);
	   DrawTexture(gl,mGR.mTex_ShareBtn		,x1,-.60f+y1);
	   if(M.setValue)
	     DrawTexture(gl,mGR.mTex_SoundBtn[0],x1,-.75f+y1);
	   else
		 DrawTexture(gl,mGR.mTex_SoundBtn[1],x1,-.75f+y1);
	   DrawTexture(gl,mGR.mTex_Exit			,x1,-.90f+y1);
	   
	   switch(mGR.mSel)
	   {
	     case 1:
	    	 DrawTexture(gl,mGR.mTex_Selector,x,-.45f+y);
			 break;
		 case 2:
			 DrawTexture(gl,mGR.mTex_Selector,x,-.60f+y);
			 break;
		 case 3:
			 DrawTexture(gl,mGR.mTex_Selector,x,-.75f+y);
			 break;
		 case 4:
			 DrawTexture(gl,mGR.mTex_Selector,x,-.90f+y);
			break;
		 case 5:
			 DrawTexture(gl,mGR.mTex_SmallSel,x1,-.45f+y1);
			break;
		 case 6:
			 DrawTexture(gl,mGR.mTex_SmallSel,x1,-.60f+y1);
			break;
		 case 7:
			 DrawTexture(gl,mGR.mTex_SmallSel,x1,-.75f+y1);
			break;		
		 case 8:
			 DrawTexture(gl,mGR.mTex_SmallSel,x1,-.90f+y1);
			break;
	    		
	   } 
       		   
	}
	
	boolean HandleMenu(MotionEvent event)
	{
		mGR.mSel=0;
		float x  =-.55f;	
		float x1 =+.75f;
		float y  =0.20f;	
		float y1 =0.20f;
		float dx = mGR.mTex_NewGameBtn.width()/2;
		float dy = mGR.mTex_NewGameBtn.Height()/2;

		if(CircRectsOverlap(x,-.45f+y,dx,dy,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
			mGR.mSel=1; // New Game
		if(CircRectsOverlap(x,-.60f+y,dx,dy,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
			mGR.mSel=2; // Help
		if(CircRectsOverlap(x,-.75f+y,dx,dy,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
			mGR.mSel=3; // AboutUs
		if(CircRectsOverlap(x,-.90f+y,dx,dy,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
			mGR.mSel=4; // HighScore
//		if(CircRectsOverlap(x1,-.45f+y1,dx/2,dy,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
//			mGR.mSel=5; // RateUs
		if(CircRectsOverlap(x1,-.60f+y1,dx/2,dy,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
			mGR.mSel=6; // Share
		if(CircRectsOverlap(x1,-.75f+y1,dx/2,dy,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
			mGR.mSel=7; // Sound
		if(CircRectsOverlap(x1,-.90f+y1,dx/2,dy,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
			mGR.mSel=8; // Exit

		if(event.getAction() == MotionEvent.ACTION_UP)
		{

			switch(mGR.mSel)
			{
			case 1:
				//					 M.play(mGR.mContext, R.drawable.bgsound);
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
				//					 mGR.hss = M.GameScreen;
									 M.GameScreen = M.GAMEHIGH;
//				GameRenderer.mStart.CallLeaderBord();
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
//		DrawTexture(gl,mGR.mTex_UIbg,0,0);
		DrawTexture(gl,mGR.mTex_StarBG,0,0.0f);
		
		float oy=-.55f;
		
		
		DrawTexture(gl,mGR.mTex_PopUp,0,0.0f+oy);

		if(M.GameScreen == M.GAMEPAUSE)
			DrawTexture(gl,mGR.mTex_GamePauseTitle,0,0.35f+oy);
		else
			DrawTexture(gl,mGR.mTex_GameOverTitle,0,0.35f+oy);

		String str = mGR.mScore+"";
		float dx   = (str.length()*mGR.mTex_Font[0].width()/2)/2;
		DrawTexture(gl,mGR.mTex_NewScore	,-0.55f	 ,.22f+oy);
		DrawTexture(gl,mGR.mTex_ScoreBox	, 0.2f 	 ,.20f+oy);
		drawNumber(gl, mGR.mScore			,0.20f-dx,.20f+oy);


		
		String str1 = mGR.mHScore+"";
		float dx1   = (str1.length()*mGR.mTex_Font[0].width()/2)/2;
		DrawTexture(gl,mGR.mTex_BestScore	,-.55f		,.07f+oy);
		DrawTexture(gl,mGR.mTex_ScoreBox	,0.20f		,.05f+oy);
		drawNumber(gl, mGR.mHScore			,0.20f-dx1	,.05f+oy);


		DrawTexture(gl,mGR.mTex_HighScoreBtn,-0.45f,-0.1f+oy);
		DrawTexture(gl,mGR.mTex_HututTitle,0.45f,-0.1f+oy);

		if(M.setValue)
			DrawTexture(gl,mGR.mTex_SoundBtn[0],-0.6f,-0.35f+oy);
		else
			DrawTexture(gl,mGR.mTex_SoundBtn[1],-0.6f,-0.35f+oy);

		DrawTexture(gl,mGR.mTex_MenuBtn,0,-0.35f+oy);
		if(M.GameScreen == M.GAMEPAUSE)
			DrawTexture(gl,mGR.mTex_Continue,0.6f,-0.35f+oy);
		else
			DrawTexture(gl,mGR.mTex_Replay,0.6f,-0.35f+oy);

//		DrawTexture(gl,mGR.mTex_ShareBtn,-0.4f,-0.62f);
//		DrawTexture(gl,mGR.mTex_RateUsBtn,0.4f,-0.62f);
		switch(mGR.mSel)
		{
		case 1:
			DrawTexture(gl,mGR.mTex_Selector,-0.45f,-0.1f+oy); 
			break;
		case 2:
			DrawTexture(gl,mGR.mTex_Selector,0.45f,-0.1f+oy);
			break;
		case 3:
			DrawTexture(gl,mGR.mTex_SmallSel,-0.6f,-0.35f+oy);
			break;
		case 4:
			DrawTexture(gl,mGR.mTex_SmallSel,0,-0.35f+oy);
			break;
		case 5:
			DrawTexture(gl,mGR.mTex_SmallSel,0.6f,-0.35f+oy);
			break;
		case 6:
			DrawTexture(gl,mGR.mTex_SmallSel,-0.4f,-0.62f+oy);
			break;	    
		case 7:
			DrawTexture(gl,mGR.mTex_SmallSel,0.4f,-0.62f+oy);
			break;
		case 8:	  
			DrawTexture(gl,mGR.mTex_Submitt[1],0.75f,.32f+oy);
			break;
		}

	}
	boolean HandlePauseOrGameover(MotionEvent event)
	{
		mGR.mSel=0;
		float oy=-.55f;
		float dx =mGR.mTex_HighScoreBtn.width()/2;
		float dy =mGR.mTex_HighScoreBtn.Height()/2;
		if(CircRectsOverlap(-.45f,-0.1f+oy,dx,dy,screen2worldX(event.getX()),screen2worldY(event.getY()), .05f))
			mGR.mSel=1; //HighScore
		if(CircRectsOverlap(0.45f,-0.1f+oy,dx,dy,screen2worldX(event.getX()),screen2worldY(event.getY()), .05f))
			mGR.mSel=2; //MoreGame
		if(CircRectsOverlap(-0.6f,-0.35f+oy,dx/2,dy,screen2worldX(event.getX()),screen2worldY(event.getY()), .05f))
			mGR.mSel=3; //Sound
		if(CircRectsOverlap(0f,-0.35f+oy,dx/2,dy,screen2worldX(event.getX()),screen2worldY(event.getY()), .05f))
			mGR.mSel=4; //Menu
		if(CircRectsOverlap(0.6f,-0.35f+oy,dx/2,dy,screen2worldX(event.getX()),screen2worldY(event.getY()), .05f))
			mGR.mSel=5; //Continue //Replay
//		if(CircRectsOverlap(-0.4f,-0.62f,dx/2,dy,screen2worldX(event.getX()),screen2worldY(event.getY()), .05f))
//			mGR.mSel=6; //Share
//		if(CircRectsOverlap(0.4f,-0.62f,dx/2,dy,screen2worldX(event.getX()),screen2worldY(event.getY()), .05f))
//			mGR.mSel=7; //RateUs
//		if(CircRectsOverlap(0.75f,.32f,dx/2,dy,screen2worldX(event.getX()),screen2worldY(event.getY()), .05f))
//			mGR.mSel=8; //Submitt
		
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			switch(mGR.mSel)
			{
			 case 1:
//				 mGR.hss = M.GameScreen;
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
//				 M.play(mGR.mContext, R.drawable.bgsound);
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
//		DrawTexture(gl,mGR.mTex_UIbg,0,0);
		DrawTexture(gl,mGR.mTex_StarBG,0,0);
		DrawTexture(gl,mGR.mTex_PopUp,0,0.0f);
		if(M.GameScreen == M.GAMEHELP)
		{
			DrawTexture(gl,mGR.mTex_HelpTitle,0,0.55f);
			DrawTexture(gl,mGR.mTex_HelpTxt,0,0);
		}
		if(M.GameScreen == M.GAMEINFO)
		{
			DrawTexture(gl,mGR.mTex_AbtTitle,0,0.55f);
			DrawTexture(gl,mGR.mTex_AbtUsTxt,0,0f);
		}
		DrawTexture(gl,mGR.mTex_ShareBtn,-0.5f,-0.6f);
//		DrawTexture(gl,mGR.mTex_RateUsBtn,0.5f,-0.6f);
		//DrawTexture(gl,mGR.mTex_backBtn,.75f,-.90f);
		
		//if(mGR.mSel==100)
			//DrawTexture(gl,mGR.mTex_SmallSel,.85f,-.85f);
		switch(mGR.mSel)
		{
		case 1:
			DrawTexture(gl,mGR.mTex_SmallSel,-0.5f,-0.6f);
			break;
		case 2:
			DrawTexture(gl,mGR.mTex_SmallSel,0.5f,-0.6f);
			break;
		}
	}
	boolean HandleHelpInfo(MotionEvent event)
	{
		mGR.mSel=0;
		if(CircRectsOverlap(-.5f,-.6f,mGR.mTex_ShareBtn.width()/2,mGR.mTex_ShareBtn.Height()/2,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
			mGR.mSel=1;//Share
//		if(CircRectsOverlap(.5f,-.6f,mGR.mTex_RateUsBtn.width()/2,mGR.mTex_RateUsBtn.Height()/2,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
//			mGR.mSel=2;//RateUs
		//if(CircRectsOverlap(.85f,-.85f,mGR.mTex_backBtn.width()/2,mGR.mTex_backBtn.Height()/2,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
			//mGR.mSel=100;
		
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
	void DrawTextureS(GL10 gl,SimplePlane Tex,float x,float y,float scalx,float scaly)
	{
		Tex.drawScal(gl, x, y,scalx,scaly);
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
		//i.putExtra(Intent.EXTRA_TEXT,"Let the battle commence!!! Download it now and let’s ROCK!!!!  "+M.PUBLICLINK+getClass().getPackage().getName());
		i.putExtra(Intent.EXTRA_TEXT,"Let the battle commence!!! Download it now and let’s ROCK!!!!  "+M.SHARELINK);
		try {
		    mGR.mContext.startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    //Toast.makeText(GameRenderer.mStart, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
}
