package com.onedaygames24.shoot2bottle;
import javax.microedition.khronos.opengles.GL10;

import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.widget.Toast;
public class Group extends Mesh 
{
	GameRenderer mGR = null;
	float dx,dy;
	float time,scal;
	int NikAni;
	int Counter =0;
	int co =0;
	public Group(GameRenderer _GameRenderer)
	{
		mGR = _GameRenderer;
	}
	
	boolean HandleHelp(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(.9, -.9, .3f, .3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))
		{
			mGR.mSel = 1;
		}
		if(MotionEvent.ACTION_UP == event.getAction() && mGR.mSel == 1)
		{
			M.GameScreen = M.GAMEMENU;
			mGR.mSel = 0;
		}
		return true;
	}
	void DrawInfo(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_AllBack	, 0, 0);
		DrawTexture(gl, mGR.mTex_InfoScr	, 0, 0);
		DrawTexture(gl, mGR.mTex_Back	, .89f, -.94f);
	}
	boolean HandleInfo(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(.9, -.9, .3f, .3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))
		{
			mGR.mSel = 1;
		}
		if(MotionEvent.ACTION_UP == event.getAction() && mGR.mSel == 1)
		{
			M.GameScreen = M.GAMEMENU;
			mGR.mSel = 0;
		}
		return true;
	}
	void DrawGameOver(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_AllBack	, 0, 0);
		DrawTexture(gl, mGR.mTex_ScoreBoard	, 0, -.28f);
		if(M.GAMEWIN == M.GameScreen)
		{
			DrawTexture(gl, mGR.mTex_LevelCleared	, 0, .48f);
			if(mGR.mSel!=1)
				DrawTexture(gl, mGR.mTex_Nextlvl[0]	, 0, -.20f);
		}
		else
		{
			DrawTexture(gl, mGR.mTex_LevelFailed	, 0, .48f);
			if(mGR.mSel!=1)
				DrawTexture(gl, mGR.mTex_Retry[0]	, 0, -.20f);
				
		}
		if(mGR.mSel!=2)
			DrawTexture(gl, mGR.mTex_Menu[0]	, 0, -.35f);
//		if(mGR.mSel!=3)
//			DrawTexture(gl, mGR.mTex_Rateus[0]	, 0, -.50f);
//		DrawTexture(gl, mGR.mTex_Submit[0]	, .78f,-.06f);
		if(mGR.mSel!=5)
			DrawTexture(gl, mGR.mTex_HighScore[0]	, .0f,-.51f);
			
			
		drawNumber(gl, mGR.BreakingBottle	, .23f, .10f);
		drawNumber(gl, mGR.HighScore		, .20f,-.06f);
		
		
		switch (mGR.mSel) {
		case 1:
			if(M.GAMEOVER == M.GameScreen)
			{
				if(NikAni<5)
					DrawTexture(gl, mGR.mTex_Retry[1]	, 0, -.20f);
				else
					DrawTexture(gl, mGR.mTex_Retry[2]	, 0, -.20f);
			}
			else{
				if(NikAni<5)
					DrawTexture(gl, mGR.mTex_Nextlvl[1]	, 0, -.20f);
				else
					DrawTexture(gl, mGR.mTex_Nextlvl[2]	, 0, -.20f);
			}
			break;
		case 2:
			if(NikAni<5)
				DrawTexture(gl, mGR.mTex_Menu[1]	, 0, -.35f);
			else
				DrawTexture(gl, mGR.mTex_Menu[2]	, 0, -.35f);
			break;
		case 3:
//			if(NikAni<5)
//				DrawTexture(gl, mGR.mTex_Rateus[1]	, 0, -.50f);
//			else
//				DrawTexture(gl, mGR.mTex_Rateus[2]	, 0, -.50f);
			break;
		case 4:
//			DrawTexture(gl, mGR.mTex_Submit[1]	, .78f,-.06f);
//			if(NikAni<5)
//				DrawTexture(gl, mGR.mTex_Submit[1]	, 0, -.65f);
//			else
//				DrawTexture(gl, mGR.mTex_Submit[2]	, 0, -.65f);
			break;
		case 5:
			DrawTexture(gl, mGR.mTex_HighScore[2]	, .0f,-.51f);
			break;
		
		}
			
	}
	boolean HandleGameOver(MotionEvent event)
	{
		if(Counter<30)
			return false;
		int temp =mGR.mSel;
		mGR.mSel =0;
		if(CircRectsOverlap(0, -.20f, mGR.mTex_Contine[0].width()/2, mGR.mTex_Contine[0].Height()/3, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			if(temp!=1)
				NikAni = 0;
			mGR.mSel = 1;
//			Log.d("   "+mGR.mSel,"  "+mGR.mSel);
		}
		if(CircRectsOverlap(0, -.35f, mGR.mTex_Newgame[0].width()/2, mGR.mTex_Newgame[0].Height()/3, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			if(temp!=2)
				NikAni = 0;
			mGR.mSel = 2;
//			Log.d("   "+mGR.mSel,"  "+mGR.mSel);
		}
		if(CircRectsOverlap(0, -.50f, mGR.mTex_Help[0].width()/2, mGR.mTex_Help[0].Height()/3, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
//			if(temp!=3)
//				NikAni = 0;
//			mGR.mSel = 3;
//			Log.d("   "+mGR.mSel,"  "+mGR.mSel);
		}
		if(CircRectsOverlap(.74f,-.06f, mGR.mTex_Submit[0].width()/2, mGR.mTex_Submit[0].Height()/2, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
//			if(temp!=4)
//				NikAni = 0;
			mGR.mSel = 4;
//			Log.d("   "+mGR.mSel,"  "+mGR.mSel);
		}
		if(CircRectsOverlap(.0f,-.51f, mGR.mTex_Submit[0].width()/2, mGR.mTex_Submit[0].Height()/2, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 5;
		}
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mGR.mSel) {
			case 1:
				if(M.GameScreen == M.GAMEOVER)
					mGR.Level = 1;
				else
					mGR.Level++;
				mGR.gameReset();
				M.GameScreen = M.GAMEPLAY;
				break;
			case 2:
				M.GameScreen = M.GAMEMENU;
				break;
			case 3:
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(M.MARKET));
				mGR.mContext.startActivity(intent);//mTex_Rate
				break;
			case 4:
//				GameRenderer.mStart.Submitscore(R.string.leaderboard_high_score);
				break;
			case 5:
				GameRenderer.mStart.onShowLeaderboardsRequested();
				break;
				
			}
			mGR.mSel = 0;
		}
		return true;
	}
	void DrawMenu(GL10 gl)
	{
		NikAni++;
		DrawTexture(gl, mGR.mTex_Splash, 0, 0);
		
		if(mGR.mSel!=1 && mGR.isPause)
			DrawTexture(gl, mGR.mTex_Contine[0]	, 0, -.20f);
		if(mGR.mSel!=2)
			DrawTexture(gl, mGR.mTex_Newgame[0]	, 0, -.35f);
		if(mGR.mSel!=3)
			DrawTexture(gl, mGR.mTex_Help[0]	, 0, -.50f);
		if(mGR.mSel!=4)
			DrawTexture(gl, mGR.mTex_Info[0]	, 0, -.65f);
		if(mGR.mSel!=5)
			DrawTexture(gl, mGR.mTex_HighScore[0], 0, -.80f);
		
		
		switch (mGR.mSel) {
		case 1:
			if(mGR.isPause)
			{
				if(NikAni<5)
					DrawTexture(gl, mGR.mTex_Contine[1]	, 0, -.20f);
				else
					DrawTexture(gl, mGR.mTex_Contine[2]	, 0, -.20f);
			}	
			break;
		case 2:
			if(NikAni<5)
				DrawTexture(gl, mGR.mTex_Newgame[1]	, 0, -.35f);
			else
				DrawTexture(gl, mGR.mTex_Newgame[2]	, 0, -.35f);
			break;
		case 3:
			if(NikAni<5)
				DrawTexture(gl, mGR.mTex_Help[1]	, 0, -.50f);
			else
				DrawTexture(gl, mGR.mTex_Help[2]	, 0, -.50f);
			break;
		case 4:
			if(NikAni<5)
				DrawTexture(gl, mGR.mTex_Info[1]	, 0, -.65f);
			else
				DrawTexture(gl, mGR.mTex_Info[2]	, 0, -.65f);
			break;
		case 5:
			if(NikAni<5)
				DrawTexture(gl, mGR.mTex_HighScore[1]	, 0, -.80f);
			else
				DrawTexture(gl, mGR.mTex_HighScore[2]	, 0, -.80f);
			break;
		case 6:
			break;
		}
	}
	
	boolean HandleMenu(MotionEvent event)
	{
		int temp =mGR.mSel;
		mGR.mSel =0;
		if(CircRectsOverlap(0, -.20f, mGR.mTex_Contine[0].width()/2, mGR.mTex_Contine[0].Height()/3, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			if(mGR.isPause)
			{
				if(temp!=1)
					NikAni = 0;
				mGR.mSel = 1;
			}
		}
		if(CircRectsOverlap(0, -.35f, mGR.mTex_Newgame[0].width()/2, mGR.mTex_Newgame[0].Height()/3, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			if(temp!=2)
				NikAni = 0;
			mGR.mSel = 2;
		}
		if(CircRectsOverlap(0, -.50f, mGR.mTex_Help[0].width()/2, mGR.mTex_Help[0].Height()/3, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			if(temp!=3)
				NikAni = 0;
			mGR.mSel = 3;
		}
		if(CircRectsOverlap(0, -.65f, mGR.mTex_Info[0].width()/2, mGR.mTex_Info[0].Height()/3, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			if(temp!=4)
				NikAni = 0;
			mGR.mSel = 4;
		}
		if(CircRectsOverlap(0, -.80f, mGR.mTex_HighScore[0].width()/2, mGR.mTex_HighScore[0].Height()/3, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			if(temp!=5)
				NikAni = 0;
			mGR.mSel = 5;
		}
//		if(CircRectsOverlap(0, -.95f, mGR.mTex_Exit[0].width()/2, mGR.mTex_Exit[0].Height()/3, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
//		{
//			if(temp!=6)
//				NikAni = 0;
//			mGR.mSel = 6;
//		}
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mGR.mSel) {
			case 1:
				mGR.isPause = false;
				mGR.startGTime = System.currentTimeMillis()-mGR.startGTime;
				M.GameScreen = M.GAMEPLAY;
				break;
			case 2:
				mGR.Level = 1;
				mGR.gameReset();
				M.GameScreen = M.GAMEPLAY;
				break;
			case 3:
				M.GameScreen = M.GAMEHELP;
				break;
			case 4:
				share2friend();
//				M.GameScreen = M.GAMEINFO;
//				GameRenderer.mStart.Submitscore(R.string.leaderboard_high_score);
//				GameRenderer.mStart.SubmitScore();
				break;
			case 5:
//				M.GameScreen = M.GAMEHIGH;
				GameRenderer.mStart.onShowLeaderboardsRequested();
				break;
			case 6:
				GameRenderer.mStart.Exit();
				break;
			}
			mGR.mSel = 0;
		}
		return true;
	}
	void gameLogic()
	{
		int i,k=0;
		time = (System.currentTimeMillis() - mGR.startGTime)/1500f;
		for(i =0;i<mGR.mStone.length;i++)
		{
			mGR.mStone[i].update();
		}
		for(i =0;i<mGR.mStone.length;i++)
		{
			if(mGR.mStone[i].checkInside())
			{
				for(int j =0;j<mGR.mBottle.length;j++)
				{
					if(!mGR.mBottle[j].isBlast)
					{
						if(CircRectsOverlap(mGR.mBottle[j].x, mGR.mBottle[j].y, mGR.mTex_Yog[0].width()/4, mGR.mTex_Yog[0].Height()/4  
								, mGR.mStone[i].x, mGR.mStone[i].y, .051f))
						{
							if(mGR.mBottle[j].Sheld>0)
							{
//								mGR.mBottle[j].isBlast =  true;
								mGR.mStone[i].vy =  Math.abs(mGR.mStone[i].vy);
								mGR.mStone[i].vz =  Math.abs(mGR.mStone[i].vz);
								M.sound5(mGR.mContext, R.drawable.shellreflect);
								mGR.mBottle[j].shieled = 3;
//								setParticle(mGR.mBottle[j].x, mGR.mBottle[j].y);
							}
							else
							{
								co =0;
//								System.out.println(mGR.throughtC+"    "+mGR.throughtL+"    "+mGR.incCounter);
								if(mGR.throughtL+1 == mGR.throughtC)
								{
									mGR.incCounter++;
								}
								else
									mGR.incCounter =1;
								if(mGR.incCounter == 3 || mGR.incCounter == 5||mGR.incCounter > 6)
									SetTitle(mGR.mBottle[j].x,1);
								else
									SetTitle(mGR.mBottle[j].x,0);
								mGR.throughtL = mGR.throughtC;
								mGR.mBottle[j].spark++;
								mGR.mBottle[j].isBlast 	=  true;
								mGR.mStone[i].isUsed 	=  true;
								mGR.mBottle[j].dvy = .04f;
								setParticle(mGR.mBottle[j].x, mGR.mBottle[j].y,mGR.mBottle[j].type);
								M.sound1(mGR.mContext, R.drawable.g1);
								mGR.BreakingBottle+=mGR.incCounter;
								if(mGR.HighScore<mGR.BreakingBottle)
								{
									mGR.HighScore = mGR.BreakingBottle;
								}
							}
						}
					}
				}
			}
		}
		for(i =0;i<mGR.mBottle.length;i++)
		{
			if(mGR.mBottle[i].Sheld>0)
				mGR.mBottle[i].Sheld --;
			if(mGR.mBottle[i].isBlast && mGR.mBottle[i].WakeUpCounter ==0)
				k++;
		}
		
		if(k==6)
		{
			for(i =0;i<mGR.mBottle.length;i++)
			{
//				mGR.throughtL =0;
				mGR.mBottle[i].WakeUpCounter = M.mRand.nextInt(10)+10;
			}
			mGR.starcounter = 10;
			for(i =0;i<mGR.mStars.length;i++)
				mGR.mStars[i].set((M.mRand.nextBoolean()?M.mRand.nextFloat():-M.mRand.nextFloat())%.9f, 
						(M.mRand.nextInt(25)+30)/100f, 0,0, M.mRand.nextInt(4));
			M.sound12(mGR.mContext, R.drawable.apear);
			reSetParticle();
		}
		for(i =0;i<mGR.mBottle.length;i++)
		{
			if(k==5)
				mGR.mBottle[i].Sheld = 0;
			if(mGR.mBottle[i].WakeUpCounter > 0)
				mGR.mBottle[i].update();
		}
		if(Counter%200 == 0)
		{
			i = M.mRand.nextInt(mGR.mBottle.length);
			if(!mGR.mBottle[i].isBlast)
			{
				mGR.mBottle[i].Sheld = 100;
			}
		}
		if(time>27 && !mGR.isPause)
		{
			GameRenderer.mStart.Submitscore(R.string.leaderboard_high_score);
			Counter =0;
			mGR.startGTime = System.currentTimeMillis();
//			Log.d(mGR.TargetBottle+ "   ############################## "+mGR.BreakingBottle,"   "+M.GameScreen);
			if(mGR.TargetBottle>mGR.BreakingBottle)
				M.GameScreen = M.GAMEOVER;
			else
				M.GameScreen = M.GAMEWIN;
//			M.GameScreen = M.GAMEADD;
			GameRenderer.mStart.show();//Smart();
			mGR.isPause = false;
//			Log.d(mGR.TargetBottle+ "   ############################## "+mGR.BreakingBottle,"   "+M.GameScreen);
		}
		
	}
	
	void DrawGamePlay(GL10 gl)
	{
		int i =0;
		DrawTexture(gl, mGR.mTex_BgBar, 0.12f, .95f);
		mGR.mTex_Color.drawSS(gl, 1, .80f, time, 1);
		DrawTexture(gl, mGR.mTex_BG[0], 0, 1-mGR.mTex_BG[0].Height()/2);
		DrawTexture(gl, mGR.mTex_BG[1], 0,-1+mGR.mTex_BG[1].Height()/2);
		
		
		for(i =0;i<mGR.mStone.length;i++)
		{
			if(mGR.mStone[i].x>-1 && mGR.mStone[i].x<1 && mGR.mStone[i].y>-1 && mGR.mStone[i].y<1)
			{
				if(mGR.mStone[i].vy<0)
					DrawTextureS(gl, mGR.mTex_Stone[Counter%mGR.mTex_Stone.length], mGR.mStone[i].x, mGR.mStone[i].y, mGR.mStone[i].z);
			}
		}
		
		for(i =0;i<mGR.mBottle.length;i++)
		{
//			DrawTexture(gl, mGR.mTex_Shadow, mGR.mBottle[i].x, mGR.mBottle[i].y-sy);
			if(mGR.mBottle[i].isBlast)
			{
				DrawTexture(gl, mGR.mTex_Break[mGR.mBottle[i].type], mGR.mBottle[i].x, mGR.mBottle[i].y);
				DrawTexture(gl, mGR.mTex_BottleD[mGR.mBottle[i].type], mGR.mBottle[i].x, mGR.mBottle[i].y+mGR.mBottle[i].dy);
				if(mGR.mBottle[i].dy>-.25f)
				{
					mGR.mBottle[i].dvy-=.003f;
					mGR.mBottle[i].dy+=mGR.mBottle[i].dvy;
				}
			}
			else
				DrawTexture(gl, mGR.mTex_Yog[mGR.mBottle[i].type], mGR.mBottle[i].x, mGR.mBottle[i].y);
			if(mGR.mBottle[i].Sheld>0)
			{
				DrawTexture(gl, mGR.mTex_Shield, mGR.mBottle[i].x, mGR.mBottle[i].y);
				if(mGR.mBottle[i].shieled>0)
				{
					mGR.mBottle[i].shieled -=1;
					DrawTexture(gl, mGR.mTex_Shield, mGR.mBottle[i].x, mGR.mBottle[i].y);
				}
			}
			if(mGR.mBottle[i].spark>0&&mGR.mBottle[i].spark<3)
			{
				mGR.mBottle[i].spark++;
				DrawTexture(gl, mGR.mTex_Spark, mGR.mBottle[i].x, mGR.mBottle[i].y-.1f);
			}
		}
		
		
		
		
		DrawTexture(gl, mGR.mTex_BG[1], 0,-1+mGR.mTex_BG[1].Height()/2);
		for(i =0;i<mGR.mStone.length;i++)
		{
			if(mGR.mStone[i].x>-1 && mGR.mStone[i].x<1 && mGR.mStone[i].y>-1 && mGR.mStone[i].y<1)
			{
				if(mGR.mStone[i].vy>=0 || mGR.mStone[i].vz > 0)
					DrawTextureS(gl, mGR.mTex_Stone[Counter%mGR.mTex_Stone.length], mGR.mStone[i].x, mGR.mStone[i].y, mGR.mStone[i].z);
			}
		}
		drawNumber(gl, mGR.BreakingBottle	, -.52f, .735f);
		drawNumber(gl, mGR.TargetBottle		, -.52f, .670f);
		drawNumber(gl, mGR.Level			, 0.78f, .745f);
		
		if(!M.setValue)
			DrawTexture(gl, mGR.mTex_SoundOff	, 0.88f, .65f);
		
		
		DrawTextureS(gl, mGR.mTex_Stone[Counter%mGR.mTex_Stone.length], 0, -.9f,2);
		
		ParticleUpdate(gl);
		gameLogic();
		if(mGR.starcounter>0)
		{
			for(i =0;i<mGR.mStars.length;i++)
			{
				DrawTextureR(gl, mGR.mTex_Star[mGR.mStars[i].img%mGR.mTex_Star.length], mGR.mStars[i].x, mGR.mStars[i].y,M.mRand.nextInt(360));
			}
			mGR.starcounter--;
		}
		DrawTexture(gl, mGR.mTex_Back	, .89f, -.94f);
		if(time>26.2)
		{
			DrawTextureS(gl, mGR.mTex_TimesUp, 0, 0,scal);
			if(scal<1.3f)
				scal+=.05f;
			
		}
//		if(mGR.incCounter>1 && co<20)
		{
			for(i=0;i<mGR.mTitle.length;i++)
			{
				if(mGR.mTitle[i].y<1.5)
				{
					if(mGR.mTitle[i].rotetion == 1)
						DrawTexture(gl, mGR.mTex_Title[mGR.mTitle[i].img], .0f, mGR.mTitle[i].y);
					drawNumberB(gl, mGR.incCounter		,mGR.mTitle[i].x, mGR.mTitle[i].y-.1f);
					mGR.mTitle[i].updateT();
				}
			}
			co++;
		}
	}
	boolean HandleGame(MotionEvent event)
	{
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			dx = event.getX();
			dy = event.getY();
			break;
		case MotionEvent.ACTION_UP:
			if(CirCir(0.83f, .7f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))
			{
				M.setValue = !M.setValue;
			}
			else if(CirCir(0.9f,-.9f, .2f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))
			{
				M.GameScreen = M.GAMEMENU;
				mGR.isPause = true;
				mGR.mSel = 0;
				mGR.startGTime = System.currentTimeMillis()-mGR.startGTime;
				
			}
			else if(time<26.2)
			{
				double ang = (GetAngle(-(dy-event.getY()),-(dx-event.getX())));
				//for(int i =0;i<mGR.mStone.length;i++)
				{
					//if(mGR.mStone[i].x<-1 || mGR.mStone[i].x>1 || mGR.mStone[i].y<-1 || mGR.mStone[i].y>1)
					{
						mGR.throughtC++;
						mGR.mStone[mGR.throughtC%mGR.mStone.length].set(0,-.9f, 1, (float)Math.sin(ang)*.08f, -(float)Math.cos(ang)*.105f, -.02f);
						M.sound6(mGR.mContext, R.drawable.shoot);
						//Log.d("[X = "+mGR.mStone[i].x+"] [Y = " + mGR.mStone[i].y+"]", "[vx = "+mGR.mStone[i].vx+"] [ vy " + mGR.mStone[i].vy+"]~~~~~~~~~~~~~ "+i+" ~~~~"+Math.toDegrees(ang));
						break;
					}
				}
			}
			break;
		}
		return true;
	}
	
	
	void setParticle(float x,float y,int type)
	{
		for(int i =0,k=0;i<mGR.mAnimation.length&&k<16;i++)
		{
			if(mGR.mAnimation[i].x<-1 || mGR.mAnimation[i].x>1 || mGR.mAnimation[i].y<-1 || mGR.mAnimation[i].y>1)
			{
				float vx = M.mRand.nextBoolean()?((M.mRand.nextInt(30)))/1000f:-(((M.mRand.nextInt(30)))/1000f);
				float vy = ((M.mRand.nextInt(50)))/1000f;
				mGR.mAnimation[i].set(x, y, vx, vy, ((type*13)+k) % mGR.mTex_Particle.length);
				k++;
			}
		}
	}
	
	void ParticleUpdate(GL10 gl)
	{
		for(int i =0;i<mGR.mAnimation.length;i++)
		{
			if(mGR.mAnimation[i].x>-1 && mGR.mAnimation[i].x<1 && mGR.mAnimation[i].y>-1 && mGR.mAnimation[i].y<1)
			{
				DrawTextureR(gl, mGR.mTex_Particle[mGR.mAnimation[i].img], mGR.mAnimation[i].x, mGR.mAnimation[i].y, Counter*mGR.mAnimation[i].rotetion);
				mGR.mAnimation[i].update();
			}
		}
	}
	
	void reSetParticle()
	{
		for(int i =0;i<mGR.mAnimation.length;i++)
			mGR.mAnimation[i].set(-110, -100, 0,0, 0);	
	}
	
	void SetTitle(float x,int type)
	{
		for(int i =0;i<mGR.mTitle.length;i++){
			if(mGR.mTitle[i].y>1||mGR.mTitle[i].y<-1)
			{
				mGR.mTitle[i].set(x, .5f, 0,0.0f, M.mRand.nextInt(mGR.mTex_Title.length));
				mGR.mTitle[i].rotetion = type;
				break;
			}
		}
	}
	
	void DrawTexture(GL10 gl,SimplePlane Tex,float x,float y)
	{
		Tex.drawTransprent(gl, x, y);
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
	
	void drawNumberB(GL10 gl,int no,float x, float y)
	{
		float dx = mGR.mTex_FontB[0].width();
		 String strs = ""+no;
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i))-48;
			if(k>=0 && k<mGR.mTex_FontB.length)
				mGR.mTex_FontB[k].drawPos(gl,x+i*dx,y);
		}
	}

	void DrawHigh(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_AllBack	, 0, 0);
		DrawTexture(gl, mGR.mTex_ScoreBoard	, 0, -.28f);
		drawNumber(gl, mGR.HighScore		, .23f,-.06f);
		DrawTexture(gl, mGR.mTex_Back	, .89f, -.94f);
	}
	void DrawHelp(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_HelpScr	, 0, 0);
		DrawTexture(gl, mGR.mTex_Back	, .89f, -.94f);
	}
	@Override
	public void draw(GL10 gl) 
	{
//		M.GameScreen = M.GAMEOVER;
		Counter++;
		switch (M.GameScreen) {
		case M.GAMELOGO:
			DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if(Counter>120){
				M.GameScreen = M.GAMEMENU;
//				M.GameScreen = M.GAMEADD1;//AdHouse
				Counter=0;//AdHouse
			}
			break;
			/*AdHouse*/
		case M.GAMEADD1:
			if(Counter>100)
				DrawTexture(gl,mGR.mTex_Skip, .9f,-.9f);
			else{
				DrawTexture(gl, mGR.mTex_Hightbar,0.2f,-.9f);
				DrawTexture(gl, mGR.mTex_Pointer,Counter*(mGR.mTex_Hightbar.width()/100f)-.32f,-.9f);
			}
			break;
			/*AdHouse*/
		case M.GAMEMENU:
//		case M.GAMEPAUSE:
			DrawMenu(gl);
			break;
		case M.GAMEPLAY:
			DrawGamePlay(gl);
			break;
		case M.GAMEOVER:
		case M.GAMEWIN:
			DrawGameOver(gl);
			break;
		case M.GAMEHIGH:
			DrawHigh(gl);
			break;
		case M.GAMEHELP:
			DrawHelp(gl);
			break;
		case M.GAMEINFO:
			DrawInfo(gl);
			break;
		case M.GAMEADD:
			DrawTexture(gl, mGR.mTex_Exit_icon,-.8f, .9f);
			break;
		}
//		setting();
//		DrawGamePlay(gl);
//		DrawGameOver(gl);
	}
	public boolean TouchEvent(MotionEvent event) 
	{
//		Log.d("  "+M.GameScreen,"   "+M.GameScreen);
		switch (M.GameScreen) {
		case M.GAMELOGO:
			break;
			/*AdHouse*/
		case M.GAMEADD1:
			if((MotionEvent.ACTION_UP == event.getAction())&&(Counter>100)&&
					CirCir(0.9f,-.9f, .11f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				M.GameScreen = M.GAMEMENU;
			}
			break;
			/*AdHouse*/
//		case M.GAMEPAUSE:
		case M.GAMEMENU:
			HandleMenu(event);
			break;
		case M.GAMEPLAY:
			HandleGame(event);
			break;
		case M.GAMEOVER:
		case M.GAMEWIN:
			HandleGameOver(event);
			break;
		case M.GAMEHIGH:
		case M.GAMEINFO:
			HandleInfo(event);
			break;
		case M.GAMEHELP:
			HandleHelp(event);
			break;
		
		case M.GAMEADD:
			if(MotionEvent.ACTION_UP == event.getAction() &&
					CircRectsOverlap(-.8f, .9f, .2f, .2f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))
			{
				if(mGR.TargetBottle>mGR.BreakingBottle)
					M.GameScreen = M.GAMEOVER;
				else
					M.GameScreen = M.GAMEWIN;
			}
			break;
		}
		return true;
		
	}
	void share2friend() {
		Uri uri = Uri.parse("android.resource://"+getClass().getPackage().getName()+"/drawable/share");
		Intent shareIntent = new Intent();
		shareIntent.setType("image/png");
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
		shareIntent.putExtra(Intent.EXTRA_TEXT,"My Score is"+ " " + mGR.BreakingBottle+ " "
								+ " and Best Score is "+mGR.HighScore+ " "
								+ "in  "+ mGR.mContext.getResources().getString(R.string.app_name)
								+ ". Can you beat me..."
								+ "Let the battle commence!!! Download it now and let’s ROCK!!!!  "
								+ "https://play.google.com/store/apps/details?id=" + getClass().getPackage().getName());
		try {
			mGR.mContext.startActivity(Intent.createChooser(
					shareIntent, "Share from"));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(GameRenderer.mStart,
					"There are no email clients installed.", Toast.LENGTH_SHORT)
					.show();
		}
	}
}
