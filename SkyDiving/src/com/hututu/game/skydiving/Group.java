package com.hututu.game.skydiving;
import javax.microedition.khronos.opengles.GL10;

import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.widget.Toast;
public class Group extends Mesh 
{
	//Anta soft
	GameRenderer mGR = null;
	int Counter =0;
	float sx,sy,sz;
	public void setting(){float ud=.01f;switch (GameRenderer.mStart._keyCode) {case 1:sy-=ud;break;case 2:sy+=ud;break;case 3:sx-=ud;break;case 4:sx+=ud;break;case 5:sz-=ud;break;case 6:sz+=ud;break;}System.out.println(GameRenderer.mStart._keyCode+"~~~~~~~~~~~~~~~      "+sx+"~~~~~~~~~~~~       "+sy+"~~~~~~~~~~~       "+sz);}
	public boolean Handle(MotionEvent event){
		if(CircRectsOverlap(-.8f,0.0f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 3;
		if(CircRectsOverlap(0.8f,0.0f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 4;
		if(CircRectsOverlap(-.0f,-.8f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 1;
		if(CircRectsOverlap(0.0f,0.8f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 2;
		if(event.getAction()== MotionEvent.ACTION_UP)GameRenderer.mStart._keyCode = 0;
		return true;
	}
	
//	public boolean Handle(MotionEvent event){
//		if(CircRectsOverlap(-.5f,0.5f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 3;
//		if(CircRectsOverlap(0.5f,0.5f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 4;
//		if(CircRectsOverlap(-.5f,-.0f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 1;
//		if(CircRectsOverlap(0.5f,0.0f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 2;
//		if(CircRectsOverlap(-.5f,-.5f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 5;
//		if(CircRectsOverlap(0.5f,-.5f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 6;
//		if(event.getAction()== MotionEvent.ACTION_UP)GameRenderer.mStart._keyCode = 0;
//		return true;
//	}
//	
	
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
			
			//Draw_SignIn(gl);
			
			DrawTexture(gl,mGR.mTex_Logo, 0,0);
			if(Counter>60){
				M.GameScreen = M.GAMEADD;
				Counter =0;
//				M.play(mGR.mContext, R.drawable.gamebg0);
			}
			break;
		case M.GAMEADD:
			if(Counter>100)
				DrawTexture(gl,mGR.mTex_Exit, .9f,.9f);
			else{
				DrawTextureR(gl, mGR.mTex_Hightbar,0.2f,0.9f,90);
				DrawTextureR(gl, mGR.mTex_Pointer,.2f+Counter*(mGR.mTex_Hightbar.Height()/100f)-.32f,0.9f,180);
			}
			break;
		case M.GAMEMENU:
			Draw_Menu(gl);
			break;
		case M.GAMELEVEL:
			Draw_Level(gl);
			break;
		case M.GAMEA:
			Draw_About(gl);
			break;
		default:
			Draw_Game(gl);
			drawAnimation(gl);
			if(M.GameScreen == M.GAMEWIN||M.GameScreen ==M.GAMEPAUSE||M.GameScreen ==M.GAMEOVER||M.GameScreen == M.GAMECONG)
				Draw_WPO(gl);
			break;
		
		}
		//BG(gl);
//		setting();
	}
	public boolean TouchEvent(MotionEvent event) 
	{
		switch (M.GameScreen) {
		case M.GAMELOGO:
//			M.GameScreen = M.GAMEPLAY;
//			Handle_SignIn(event);
			break;
		case M.GAMEADD:
			if((Counter>100)&&CirCir(0.9f,0.9f, .11f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				M.GameScreen = M.GAMEMENU;
				M.sound2(mGR.mContext,R.drawable.button_click);
				M.play(mGR.mContext, R.drawable.splash_2);
			}
			break;
		case M.GAMEMENU:
			Handle_Menu(event);
			break;
		case M.GAMELEVEL:
			Handle_Level(event);
			break;
		case M.GAMEWIN:
		case M.GAMEPAUSE:
		case M.GAMEOVER:
		case M.GAMECONG:
			Handle_WPO(event);
		break;
		case M.GAMEA:
			Handle_About(event);
			break;
		default:
			Handle_Game(event);
			break;
		}
		Handle(event);
		
		
		return true;
	}
	void blast()
	{
		for(int i=0;i<mGR.mChar.length;i++)
			for(int j=0;j<mGR.mChar[i].length;j++)
			{
				if(mGR.mChar[i][j].Pl != 0)
				{
					mGR.mChar[i][j].blast = 1;
					mGR.mChar[i][j].Pl =1;
				}
			}
	}
	boolean chack(float x, float y)
	{
		boolean tap = false;
		for(int i=0;i<mGR.mChar.length;i++)
			for(int j=0;j<mGR.mChar[i].length;j++)
			{
				if(CircRectsOverlap(mGR.mChar[i][j].x, mGR.mChar[i][j].y, mGR.mTex_Boy[0].width()*.4, mGR.mTex_Boy[0].Height()*.4, x, y, .07))
				{
					tap = true;
					if(mGR.mChar[i][j].Pl == 3){
						setPoint(mGR.mChar[i][j].x, mGR.mChar[i][j].y,5);
					}
					if(mGR.mChar[i][j].Pl == 2){
						blast();
						M.sound1(mGR.mContext,R.drawable.bob_blast);
					}
					if(tapCount>3){
						setPoint(mGR.mChar[i][j].x, mGR.mChar[i][j].y,0);
						mGR.mScore+=87;
					}
					if(mGR.mChar[i][j].Pl == 1){
						M.sound4(mGR.mContext,R.drawable.parachute_opne);
					}
					mGR.mChar[i][j].Collide();
					mGR.mScore+=87;
					if(mGR.mScore > mGR.mHScore)
						mGR.mHScore = mGR.mScore;
				}
			}
		if(CircRectsOverlap(mGR.mPower.x, mGR.mPower.y, mGR.mTex_Boy[0].width(), mGR.mTex_Boy[0].Height(), x, y, .07))
		{
			if(mGR.mPower.power!=5)
			{
				setAnimation(mGR.mPower.x, mGR.mPower.y);
				M.sound7(mGR.mContext,R.drawable.power_click);
			}
			switch(mGR.mPower.power) {
			case 0:
				mGR.mScore+=87;
				mGR.mPower.timer = 200;
				mGR.mPower.x = mGR.mPower.y = -100;
				break;
			case 1:
				mGR.mScore+=500;
				setPoint(mGR.mPower.x,mGR.mPower.y,mGR.mPower.power);
				mGR.mPower.x = mGR.mPower.y = -100;
				break;
			case 2:
				mGR.mScore+=100;
				setPoint(mGR.mPower.x,mGR.mPower.y,mGR.mPower.power);
				mGR.mPower.x = mGR.mPower.y = -100;
				break;
			case 3:
				mGR.mScore+=200;
				setPoint(mGR.mPower.x,mGR.mPower.y,mGR.mPower.power);
				mGR.mPower.x = mGR.mPower.y = -100;
				break;
			case 4:
				mGR.mScore+=100;
				setPoint(mGR.mPower.x,mGR.mPower.y,mGR.mPower.power);
				for(int i=0;i<mGR.mChar.length;i++)
					for(int j=0;j<mGR.mChar[i].length;j++)
					{
						if(mGR.mChar[i][j].Pl != 0){
							if(mGR.mChar[i][j].Pl == 2)
								mGR.mChar[i][j].Pl =1;
							mGR.mChar[i][j].Collide();
							mGR.mScore+=87;
							if(mGR.mScore > mGR.mHScore)
								mGR.mHScore = mGR.mScore;
							}
							
					}
				mGR.mPower.x = mGR.mPower.y = -100;
				break;
			case 5:
				mGR.mScore+=50;
				setPoint(mGR.mPower.x,mGR.mPower.y,mGR.mPower.power);
				M.sound8(mGR.mContext,R.drawable.puppet_score_power);
				break;
				}
		}
		return tap;
	}
	void setPoint(float x,float y,int Power)
	{
		for(int k=0;k<mGR.mPoint.length;k++){
			if(mGR.mPoint[k].x<-1||mGR.mPoint[k].x>1||mGR.mPoint[k].y<-1||mGR.mPoint[k].y>1){
				mGR.mPoint[k].set(x, y-.1f,Power);
				break;
			}
		}
	}
	float bg1 =0,bg2=2;
	void BG(GL10 gl)
	{
		mGR.r = mGR.g = mGR.b=1;
		DrawTrans(gl, mGR.mTex_GameBG, 0, bg1,mGR.r,mGR.g,mGR.b);
		DrawTrans(gl, mGR.mTex_GameBG, 0, bg2,mGR.r,mGR.g,mGR.b);

		bg1+=.01f;
		bg2+=.01f;
		if(bg1>2)
			bg1=bg2-2;
		if(bg2>2)
			bg2=bg1-2;
		
		if(mGR.mCh.y<-1.2f){
			mGR.mCh.x = M.mRand.nextFloat()*(M.mRand.nextBoolean()?.9f:-.9f);
			mGR.mCh.y = 1.2f;
			mGR.mCh.vx = -.05f;
		}
		mGR.mCh.y+=mGR.mCh.vx;
		DrawTexture(gl, mGR.mTex_Boy[Counter%mGR.mTex_Boy.length]	, mGR.mCh.x, mGR.mCh.y);
/*		DrawTexture(gl, mGR.mTex_cUp	, 0.5f, .5f);
		DrawTexture(gl, mGR.mTex_cDown	, -.5f, .5f);
		
		DrawTexture(gl, mGR.mTex_cUp	, 0.5f, .0f);
		DrawTexture(gl, mGR.mTex_cDown	, -.5f, .0f);
		
		DrawTexture(gl, mGR.mTex_cUp	, 0.5f,-.5f);
		DrawTexture(gl, mGR.mTex_cDown	, -.5f,-.5f);
		
		if(sx>1)
			sx=1;
		if(sx<0)
			sx=0;
		if(sy>1)
			sy=1;
		if(sy<0)
			sy=0;
		if(sz>1)
			sz=1;
		if(sz<0)
			sz=0;*/
		
	}
	void Draw_About(GL10 gl)
	{
		BG(gl);
		DrawTexture(gl, mGR.mTex_Splash, 0, .22f);
		DrawTexture(gl, mGR.mTex_scrAbout, 0, -.4f);
	}
	boolean Handle_About(MotionEvent event)
	{
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			M.GameScreen= M.GAMEMENU;
			if(mGR.mSel !=0)
				M.sound2(mGR.mContext,R.drawable.button_click);
		}
		return true;
	}
	void Draw_WPO(GL10 gl){
//		BG(gl);
		if(M.GameScreen == M.GAMEPAUSE)
		{
			DrawTexture(gl, mGR.mTex_GPaused, 0.00f,-.26f);
			DrawTexture(gl, mGR.mTex_Score	, 0.00f,-.47f);
			DrawTexture(gl, mGR.mTex_Continue,-.35f,-.7f);
			DrawTexture(gl, mGR.mTex_Retry	, 0.35f,-.7f);
			DrawTexture(gl, mGR.mTex_Menu	, -.55f,-.9f);
			DrawTexture(gl, mGR.mTex_Rate	, 0.00f,-.9f);
			DrawTexture(gl, M.setValue?mGR.mTex_Soundon:mGR.mTex_Soundoff,0.55f,-.9f);
		}
		else if(M.GameScreen == M.GAMEWIN)
		{
			DrawTexture(gl, mGR.mTex_Nextlevel	, 0.00f,-.26f);
			DrawTexture(gl, mGR.mTex_Score		, 0.00f,-.47f);
			DrawTexture(gl, mGR.mTex_Next		, -.35f,-.7f);
			DrawTexture(gl, mGR.mTex_Retry		, 0.35f,-.7f);
			DrawTexture(gl, mGR.mTex_Menu		, -.55f,-.9f);
			DrawTexture(gl, mGR.mTex_Share		, 0.00f,-.9f);
			DrawTexture(gl, M.setValue?mGR.mTex_Soundon:mGR.mTex_Soundoff,0.55f,-.9f);
		}
		else if(M.GameScreen == M.GAMECONG)
		{
			DrawTexture(gl, mGR.mTex_Cong		, 0.00f,-.26f);
			DrawTexture(gl, mGR.mTex_Score		, 0.00f,-.47f);
			DrawTexture(gl, mGR.mTex_More		, 0.00f,-.7f);
			DrawTexture(gl, mGR.mTex_Menu		, -.55f,-.9f);
			DrawTexture(gl, mGR.mTex_Share		, 0.00f,-.9f);
			DrawTexture(gl, M.setValue?mGR.mTex_Soundon:mGR.mTex_Soundoff,0.55f,-.9f);
		}
		else
		{
			DrawTexture(gl, mGR.mTex_Gameover,0.00f,-.26f);
			DrawTexture(gl, mGR.mTex_Score		, 0.00f,-.47f);
			DrawTexture(gl, mGR.mTex_bScore	, 0.00f,-.7f);
			DrawTexture(gl, mGR.mTex_Menu	, -.55f,-.9f);
			DrawTexture(gl, mGR.mTex_Retry	, 0.00f,-.9f);
			DrawTexture(gl, mGR.mTex_Like	, 0.55f,-.9f);
			drawNumber(gl, mGR.mHScore, -.1f, -.7f, 0);
		}
		drawNumber(gl, mGR.mScore, -.29f, -.51f, 0);
		
		switch (mGR.mSel) {
		case 1:
			DrawTexture(gl, mGR.mTex_Playse	, -.35f,-.7f);
			break;
		case 2:
			DrawTexture(gl, mGR.mTex_Playse	, 0.35f,-.7f);
			break;
		case 3:
			DrawTexture(gl, mGR.mTex_LSel	, -.55f,-.9f);
			break;
		case 4:
			if(M.GameScreen == M.GAMEOVER)
				DrawTexture(gl, mGR.mTex_Playse	, -.00f,-.9f);
			else
				DrawTexture(gl, mGR.mTex_LSel	, -.00f,-.9f);
			break;
		case 5:
			DrawTexture(gl, mGR.mTex_LSel	, 0.55f,-.9f);
			break;
		case 7:
			DrawTexture(gl, mGR.mTex_Playse	,-.6f,-.51f);
			break;
		case 8:
			DrawTexture(gl, mGR.mTex_Playse	,0.6f,-.51f);
			break;
			
			}
	
	}
	boolean Handle_WPO(MotionEvent event)
	{
		if(CircRectsOverlap(-.35f,-.7f, mGR.mTex_Continue.width()*.4f, mGR.mTex_Continue.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			if(M.GameScreen != M.GAMEOVER)
				mGR.mSel =1;
		}
		if(CircRectsOverlap( 0.35f,-.7f, mGR.mTex_Continue.width()*.4f, mGR.mTex_Continue.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			if(M.GameScreen != M.GAMEOVER)
				mGR.mSel =2;
		}
		
		if(CircRectsOverlap( -.55f,-.9f, mGR.mTex_Menu.width()*.4f, mGR.mTex_Menu.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
				mGR.mSel =3;
		}
		if(CircRectsOverlap(  -.00f,-.9f, mGR.mTex_Menu.width()*.6f, mGR.mTex_Menu.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
				mGR.mSel =4;
		}
		if(CircRectsOverlap( 0.55f,-.9f, mGR.mTex_Menu.width()*.4f, mGR.mTex_Menu.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
				mGR.mSel =5;
		}
		if(CircRectsOverlap( 0.0f,-.7f, mGR.mTex_More.width()*.4f, mGR.mTex_More.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			if(M.GameScreen == M.GAMECONG)
				mGR.mSel =6;
		}
		if(CircRectsOverlap(-.6f,-.51f, mGR.mTex_More.width()*.4f, mGR.mTex_More.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel =7;
		}
		if(CircRectsOverlap(0.6f,-.51f, mGR.mTex_More.width()*.4f, mGR.mTex_More.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel =8;
		}
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mGR.mSel) {
			case 1:
				if(M.GameScreen == M.GAMECONG)
				{
					MoreGame();
				}
				else if(M.GameScreen == M.GAMEWIN)
				{
//					M.loopStop();
//					M.play1(mGR.mContext,R.drawable.gameplay);
					M.GameScreen = M.GAMEPLAY;
					mGR.mLevel++;
					if(mGR.mLevel>49)
						mGR.mLevel =0;
					mGR.gameReset();
				}
				else{
					M.GameScreen = M.GAMEPLAY;
//					M.loopStop();
//					M.play1(mGR.mContext,R.drawable.gameplay);
				}
				break;
			case 2:
				if(M.GameScreen == M.GAMECONG)
				{
					MoreGame();
				}
				else {
//					M.loopStop();
//					M.play1(mGR.mContext,R.drawable.gameplay);
				M.GameScreen = M.GAMEPLAY;
				mGR.gameReset();
				}
				break;
			case 3:
				M.GameScreen = M.GAMEMENU;
				break;
			case 4:
				if(M.GameScreen == M.GAMEOVER)
				{
//					M.loopStop();
//					M.play1(mGR.mContext,R.drawable.gameplay);
					M.GameScreen = M.GAMEPLAY;
					mGR.mScore =0;
					mGR.gameReset();
				}
				else if(M.GameScreen == M.GAMEWIN||M.GameScreen == M.GAMECONG)
					share2friend();
				else
					RateUs();
				break;
			case 5:
				if(M.GameScreen == M.GAMEOVER)
					facebook();
				else{
					M.setValue =!M.setValue;
					if(!M.setValue)
						M.loopStop();
					else
						M.play(mGR.mContext, R.drawable.splash_2);
				}
				break;
			case 6:
				MoreGame();
				break;
			case 7:
				GameRenderer.mStart.onShowLeaderboardsRequested();
				break;
			case 8:
				GameRenderer.mStart.Submitscore(R.string.leaderboard_score,mGR.mScore);
				break;
				
			}
			if(mGR.mSel !=0)
				M.sound2(mGR.mContext,R.drawable.button_click);
			mGR.mSel=0;
		}
		return true;
	}
	void Draw_Menu(GL10 gl){
		BG(gl);
		DrawTexture(gl, mGR.mTex_Splash, 0, .22f);
		DrawTexture(gl, mGR.mTex_MBoy[Counter%mGR.mTex_Boy.length], 0, -.31f);
		DrawTexture(gl, mGR.mTex_Playde, 0,-.73f);
		
		DrawTexture(gl, mGR.mTex_Google, .83f,-.9f);
		DrawTexture(gl, mGR.mTex_Leader, .83f,-.7f);
		
		DrawTexture(gl, mGR.mTex_Cntbg	,-.83f,0.00f+mGR.my);
		DrawTexture(gl, mGR.mTex_About	,-.83f,0.23f+mGR.my);
		DrawTexture(gl, M.setValue?mGR.mTex_Soundon:mGR.mTex_Soundoff,-.83f,0.03f+mGR.my);
		DrawTexture(gl, mGR.mTex_Facebok,-.83f,-.18f+mGR.my);
		if(mGR.my>-1)
			DrawTexture(gl, mGR.mTex_cDown, -.83f,-.9f);
		else
			DrawTexture(gl, mGR.mTex_cUp, -.83f,-.9f);
		
		
		switch (mGR.mSel) {
		case 1:
			DrawTexture(gl, mGR.mTex_CSel, -.83f,-.9f);
			break;
		case 2:
			DrawTexture(gl, mGR.mTex_Playse, 0,-.73f);
			break;
		case 3:
			DrawTexture(gl, mGR.mTex_CSel, .83f,-.9f);
			break;
		case 4:
			DrawTexture(gl, mGR.mTex_LSel	,-.83f,0.23f+mGR.my);
			break;
		case 5:
			DrawTexture(gl, mGR.mTex_LSel	,-.83f,0.03f+mGR.my);
			break;
		case 6:
			DrawTexture(gl, mGR.mTex_LSel	,-.83f,-.18f+mGR.my);
			break;
		case 7:
			DrawTexture(gl, mGR.mTex_CSel, .83f,-.7f);
			break;
		
		}
		
		if(mGR.mMenu!=0)
		{
			mGR.my+=mGR.mMenu;
			if(mGR.mMenu>0 && mGR.my>-.55){
				mGR.mMenu=0;
				mGR.my = -.55f;
			}
			if(mGR.mMenu<0 && mGR.my<-1.5){
				mGR.mMenu=0;
				mGR.my = -1.5f;
			}
		}
	}
	boolean Handle_Menu(MotionEvent event){
		mGR.mSel = 0;
		if(CirCir(-.83f,-.9f, .11f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			if(mGR.mMenu==0)
				mGR.mSel =1;
		}
		if(CircRectsOverlap(0,-.73f, mGR.mTex_Playde.width()*.4f, mGR.mTex_Playde.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel =2;
		}
		if(CircRectsOverlap( .83f,-.9f, mGR.mTex_Google.width()*.4f, mGR.mTex_Google.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel =3;
		}
		if(CircRectsOverlap( .83f,-.7f, mGR.mTex_Google.width()*.4f, mGR.mTex_Google.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel =7;
		}


		if(CirCir(-.83f,0.23f+mGR.my, .10f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			if(mGR.mMenu==0 && mGR.my>-1)
				mGR.mSel =4;
		}
		if(CirCir(-.83f,0.03f+mGR.my, .10f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			if(mGR.mMenu==0 && mGR.my>-1)
				mGR.mSel =5;
		}
		if(CirCir(-.83f,-.18f+mGR.my, .10f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			if(mGR.mMenu==0 && mGR.my>-1)
				mGR.mSel =6;
		}
		
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mGR.mSel) {
			case 1:
				if(mGR.my>-1)
					mGR.mMenu = -.05f;
				else
					mGR.mMenu = 0.05f;
				break;
			case 2:
				M.GameScreen = M.GAMELEVEL;
				break;
			case 3:
				Google();
				break;
			case 4:
				M.GameScreen = M.GAMEA;
				break;
			case 5:
				M.setValue =!M.setValue;
				if(!M.setValue)
					M.loopStop();
				else
					M.play(mGR.mContext, R.drawable.splash_2);
				break;
			case 6:
				facebook();
				break;
			case 7:
				GameRenderer.mStart.onShowLeaderboardsRequested();
				break;
			
			}
			if(mGR.mSel !=0)
				M.sound2(mGR.mContext,R.drawable.button_click);
			mGR.mSel =0;
		}
		return true;
	}
	void Draw_Level(GL10 gl)
	{
		BG(gl);
		for(int i=0;i<12 && mGR.move!=0;i++)
		{
			if(i!=8&&i!=11)
			{
				DrawTexture(gl, mGR.mTex_LevelPar, -.76f+((i%4)*.5f),.2f+.3f-((i/4)*.53f)+mGR.y2);
				DrawTexture(gl, mGR.mTex_LevelBox, -.76f+((i%4)*.5f),.0f+.3f-((i/4)*.53f)+mGR.y2);
			}
		}
		for(int i=0;i<12;i++)
		{
			if(i==8)
			{
				if(mGR.mPage <4)
				{
					DrawTexture(gl, mGR.mTex_uparrow, -.76f+((i%4)*.5f),.0f+.3f-((i/4)*.53f));
					if(mGR.mSel==i+1)
						DrawTexture(gl, mGR.mTex_CSel, -.76f+((i%4)*.5f),.0f+.3f-((i/4)*.53f));
						
				}
			}
			else if(i==11)
			{
				if(mGR.mPage >0)
				{
					DrawTexture(gl, mGR.mTex_Downarrow, -.76f+((i%4)*.5f),.0f+.3f-((i/4)*.53f));
					if(mGR.mSel==i+1)
						DrawTexture(gl, mGR.mTex_CSel, -.76f+((i%4)*.5f),.0f+.3f-((i/4)*.53f));
				}
			}
			else
			{
				DrawTexture(gl, mGR.mTex_LevelPar, -.76f+((i%4)*.5f),.2f+.3f-((i/4)*.53f)+mGR.y1);
				DrawTexture(gl, mGR.mTex_LevelBox, -.76f+((i%4)*.5f),.0f+.3f-((i/4)*.53f)+mGR.y1);
				if(mGR.move == 0)
				{
					if(mGR.mUnLevel<(i>7?-1:0)+mGR.mPage*10+i+1)
						DrawTexture(gl, mGR.mTex_Lock, -.76f+((i%4)*.5f),.0f+.3f-((i/4)*.53f)+mGR.y1);
					else
						drawNumber(gl, (i>7?-1:0)+mGR.mPage*10+i+1, -.76f+((i%4)*.5f),.0f+.3f-((i/4)*.53f)+mGR.y1,1);
				}
				if(mGR.mSel==i+1)
					DrawTexture(gl, mGR.mTex_LSel, -.76f+((i%4)*.5f),.0f+.3f-((i/4)*.53f));
			}
		}
		
		if(mGR.move!=0)
		{
			mGR.y1+=mGR.move;
			mGR.y2+=mGR.move;
			if(mGR.move>0)
			{
				if(mGR.y2>=0)
				{
					mGR.move =0;
					mGR.y2 = 0;
					mGR.y1 = 0;
				}
			}
			if(mGR.move<0)
			{
				if(mGR.y2<=0)
				{
					mGR.move =0;
					mGR.y2 = 0;
					mGR.y1 = 0;
				}
			}
		}
	}
	boolean Handle_Level(MotionEvent event)
	{
		mGR.mSel=0;
		for(int i=0;i<12;i++)
		{
			if(CirCir(-.76f+((i%4)*.5f),.2f+.3f-((i/4)*.53f), .21f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				mGR.mSel = i+1;
			}
		}
		if(MotionEvent.ACTION_UP == event.getAction()){
			switch (mGR.mSel) {
			case 1:case 2:case 3:case 4:case 5:
			case 6:case 7:case 8:case 10:case 11:
				if(mGR.mUnLevel>=(mGR.mSel>7?-1:0)+mGR.mPage*10+mGR.mSel)
				{
//					M.loopStop();
//					M.play1(mGR.mContext,R.drawable.gameplay);
					M.GameScreen = M.GAMEPLAY;
					mGR.mLevel=(mGR.mSel>7?-2:-1)+mGR.mPage*10+mGR.mSel;
					mGR.mScore =0; 
					mGR.gameReset();
				
				}
				break;
			case 9:
				if( mGR.move==0 && mGR.mPage <4){
					mGR.move = 0.1f;
					mGR.y1=0;
					mGR.y2=-2;
					mGR.mPage++;
				}
				break;
			case 12:
				if( mGR.move==0 && mGR.mPage >0){
					mGR.move = -.1f;
					mGR.y1=0;
					mGR.y2=2;
					mGR.mPage--;
					}
				break;
				}
			if(mGR.mSel !=0)
				M.sound2(mGR.mContext,R.drawable.button_click);
			mGR.mSel=0;
		}
		return true;
		
	}
	void gamelogic()
	{
		int i;
		mGR.mBG[0].y +=M.SPEED;
		mGR.mBG[1].y +=M.SPEED;
		if(mGR.mBG[1].y>2)
			mGR.mBG[1].y = mGR.mBG[0].y -2;
		if(mGR.mBG[0].y>2)
			mGR.mBG[0].y = mGR.mBG[1].y -2;
		
		for(i=0;i<mGR.mCluod.length;i++){
			mGR.mCluod[i].y+=M.SPEED*3;
			mGR.mCluod[i].x+=mGR.mCluod[i].vx;
			if(mGR.mCluod[i].vx>0&& mGR.mCluod[i].x >1.5f)
				mGR.mCluod[i].x=-1.5f;
			if(mGR.mCluod[i].vx<0&& mGR.mCluod[i].x <-1.5f)
				mGR.mCluod[i].x=01.5f;
				
			if(mGR.mCluod[i].y>1.5 && mGR.mDistance>100)
			{
				mGR.mCluod[i].set(mGR.mCluod[i==0?(mGR.mCluod.length-1):(i-1)].y-.5f); 
			}
		}
		mGR.mDown+=M.SPEED;
		boolean check4Next = true;
		boolean check4Loose = false;
		boolean allGo= true;
		for(i=0;i<mGR.mChar.length;i++)
			for(int j=0;j<mGR.mChar[i].length;j++)
			{
				mGR.mChar[i][j].update();
				if(mGR.mDown>=0 && mGR.mBase.go<0 && mGR.mChar[i][j].go<0)
				{
					mGR.mChar[i][j].vy=-M.SPEED;
				}
				if(mGR.mChar[i][j].go<40)
					check4Next =false;
				if(mGR.mChar[i][j].blast>0)
					check4Loose = true;
				if(mGR.mChar[i][j].go==-1 && mGR.mChar[i][j].Pl != 2)
				{
					allGo = false;
				}
			}
		if(allGo)
		{
			for(i=0;i<mGR.mChar.length;i++)
				for(int j=0;j<mGR.mChar[i].length;j++)
				{
					if(mGR.mChar[i][j].Pl == 2){
						mGR.mChar[i][j].Pl = 0;
						mGR.mChar[i][j].x =mGR.mChar[i][j].y =-100;
					}
					
				}
		}
		if(check4Next && M.GameScreen == M.GAMEPLAY)
		{
			mGR.mWave++;
			if(Map.levelWave[mGR.mLevel].length>mGR.mWave)
			{
				mGR.setWave(Map.levelWave[mGR.mLevel][mGR.mWave]);
				M.sound11(mGR.mContext, R.drawable.wav_coming);
			}
			else
			{
//				M.loopStop();
//				M.play(mGR.mContext,R.drawable.splash_2);
				M.GameScreen = M.GAMEWIN;
				if(mGR.mLevel>=49)
					M.GameScreen = M.GAMECONG;
				System.out.print("~~~~~~~~~~~~~          "+M.GAMECONG+"      ~~~~~~~~~~~~~~");
				if(mGR.mUnLevel-2<mGR.mLevel)
					mGR.mUnLevel= mGR.mLevel+2;
			}
		}
		if(check4Loose && M.GameScreen == M.GAMEPLAY)
		{
			M.GameScreen = M.GAMEOVER;
//			M.loopStop();
//			M.play(mGR.mContext,R.drawable.splash_2);
			M.sound3Play(mGR.mContext,R.drawable.crash_landing);
		}
		if(mGR.mDistance==100)
		{
			M.SPEED*=2;
			mGR.mBase.go=0;
			mGR.mBase.vy=M.SPEED;
			mGR.mBG[1].y = -.34f;
			mGR.mBG[0].y = mGR.mBG[1].y +2;
		}
		if(mGR.mBase.go==0)
		{
			mGR.mBase.y+=mGR.mBase.vy;
			if(mGR.mBase.y>=-.43f){
				mGR.mBase.vy = M.SPEED = 0;
				for(i=0;i<mGR.mChar.length;i++)
					for(int j=0;j<mGR.mChar[i].length;j++){
					if(mGR.mChar[i][j].go<0)
					{
						mGR.mChar[i][j].vy*=1.2f;
					}
				}
			}
		}
		if(mGR.mDistance>0 && (mGR.mPower.timer<0 || mGR.mPower.timer%2==0))
		{
			mGR.mDistance--;
			
		}
		
		
		if(Counter%200==0 && M.GameScreen == M.GAMEPLAY)
		{
			mGR.mPower.set(0, 1.2f, M.mRand.nextInt(6),mGR.mPower.timer);
			M.sound10(mGR.mContext, R.drawable.power_coming);
		}
		
	}
	int c2;
	void Draw_Game(GL10 gl)
	{
		int i =0;
//		DrawTexture(gl, mGR.mTex_GameBG, mGR.mBG[0].x, mGR.mBG[0].y);
//		DrawTexture(gl, mGR.mTex_GameBG, mGR.mBG[1].x, mGR.mBG[1].y);
		DrawTrans(gl, mGR.mTex_GameBG, mGR.mBG[0].x, mGR.mBG[0].y,mGR.r,mGR.g,mGR.b);
		DrawTrans(gl, mGR.mTex_GameBG, mGR.mBG[1].x, mGR.mBG[1].y,mGR.r,mGR.g,mGR.b);
		for(i=0;i<mGR.mCluod.length;i++){
			DrawTrans(gl, mGR.mTex_Cluod[i%2], mGR.mCluod[i].x, mGR.mCluod[i].y,mGR.r*1.3f,mGR.g*1.3f,mGR.b*1.3f);
		}
//		for(i=0;i<mGR.mCluod.length;i++){
//			DrawTexture(gl, mGR.mTex_Cluod[i%2], mGR.mCluod[i].x, mGR.mCluod[i].y);
//		}
		if(mGR.mBase.go==0)
		{
//			DrawTexture(gl, mGR.mTex_GameBase, mGR.mBase.x, mGR.mBase.y);
			DrawTrans(gl, mGR.mTex_GameBase, mGR.mBase.x, mGR.mBase.y,mGR.r,mGR.g,mGR.b);
		}
		
//		System.out.println(mGR.mBase.go+"~~~~~~~~~~~~~~~~~~~  "+M.SPEED+"  "+mGR.mBase.x+"   "+mGR.mBase.y);
		for(i=0;i<mGR.mChar.length;i++)
			for(int j=0;j<mGR.mChar[i].length;j++)
		{
			if((mGR.mChar[i][j].go==-1 && mGR.mChar[i][j].y<-.85f)||mGR.mChar[i][j].blast>0)
			{
				if(M.SPEED==0){
					DrawTexture(gl, mGR.mTex_Rip, mGR.mChar[i][j].x,-.75f);
					
				}
				else{
					DrawTexture(gl, mGR.mTex_Angel, mGR.mChar[i][j].x,mGR.mChar[i][j].y);
					if(mGR.mChar[i][j].blast<mGR.mTex_Blast.length)
						DrawTexture(gl, mGR.mTex_Blast[mGR.mChar[i][j].blast], mGR.mChar[i][j].x,mGR.mChar[i][j].y);
				}
			}
		}
		
		if(Counter%3==0)
			c2++;
		for(i=0;i<mGR.mChar.length;i++)
			for(int j=0;j<mGR.mChar[i].length;j++){
				if(mGR.mChar[i][j].Pl==1)
				{
					if(mGR.mChar[i][j].blast<=0){
						if(mGR.mChar[i][j].go>-1){
							if(mGR.mChar[i][j].y<-.85f)
								DrawTexture(gl, mGR.mTex_Safe[c2%mGR.mTex_Safe.length], mGR.mChar[i][j].x,-.80f);
							else{
								if(mGR.mChar[i][j].p<1){
									mGR.mTex_LevelPar.drawSS(gl, mGR.mChar[i][j].x,mGR.mChar[i][j].y+.02f, mGR.mChar[i][j].p,0);
									DrawTexture(gl, mGR.mTex_BoyL, mGR.mChar[i][j].x,mGR.mChar[i][j].y);
								}
								else
									DrawTextureR(gl, mGR.mTex_Fall, mGR.mChar[i][j].x,mGR.mChar[i][j].y+.1f,mGR.mChar[i][j].rotate);
							}
						}
						else
							DrawTexture(gl, mGR.mTex_Boy[Counter%mGR.mTex_Boy.length], mGR.mChar[i][j].x,mGR.mChar[i][j].y);
					}
				}
				else if(mGR.mChar[i][j].Pl==2)
				{
					DrawTexture(gl, mGR.mTex_Bomb,mGR.mChar[i][j].x,mGR.mChar[i][j].y);
					DrawTexture(gl, mGR.mTex_bBlast[Counter%mGR.mTex_bBlast.length], mGR.mChar[i][j].x+.05f,mGR.mChar[i][j].y+.07f);
					
				}
				else if(mGR.mChar[i][j].Pl==3)
				{
					DrawTexture(gl, mGR.mTex_bBoy[Counter%mGR.mTex_Boy.length], mGR.mChar[i][j].x,mGR.mChar[i][j].y);
				}
		}
		for(i=0;i<mGR.mPoint.length;i++){
			if(mGR.mPoint[i].x>-1&&mGR.mPoint[i].x<1&&mGR.mPoint[i].y>-1&&mGR.mPoint[i].y<1){
				DrawTexture(gl, mGR.mTex_Point[mGR.mPoint[i].type%mGR.mTex_Point.length],mGR.mPoint[i].x,mGR.mPoint[i].y);
				mGR.mPoint[i].y+=mGR.mPoint[i].vx;
				mGR.mPoint[i].vx+=.002f;
			}
		}
			
		if(mGR.mPower.y>-1.2)
		{
			if(mGR.mPower.power != 5)
				DrawTexture(gl, mGR.mTex_Power[mGR.mPower.power%5], mGR.mPower.x,mGR.mPower.y);
			else
				DrawTexture(gl, mGR.mTex_bBoy[Counter%mGR.mTex_Boy.length], mGR.mPower.x,mGR.mPower.y);
			mGR.mPower.update();
		}
		mGR.mPower.timer--;
		DrawTexture(gl, mGR.mTex_Level	,-.8f, 0.70f);
		drawNumber (gl, mGR.mLevel+1	,-.5f, 0.70f,0);
		if(M.GameScreen == M.GAMEPLAY){
			DrawTexture(gl, mGR.mTex_Jumphight	,-.5f, -.93f);
			drawNumber (gl, mGR.mDistance		,-.44f,-.92f,0);
			DrawTexture(gl, mGR.mTex_Pause		,0.88f,-.94f);
			DrawTexture(gl, mGR.mTex_Hightbar,-.9f,0);
			DrawTexture(gl, mGR.mTex_Pointer,-.85f,mGR.mDistance*mGR.Inc-.32f);
		}
		if(M.GAMEPAUSE!=M.GameScreen)
			gamelogic();	
	}
	int tapCount;
	boolean Handle_Game(MotionEvent event)
	{
		if(MotionEvent.ACTION_UP== event.getAction())
		{
			if(chack(screen2worldX(event.getX()), screen2worldY(event.getY())))
			{
				tapCount++;
				if(tapCount>10)
					tapCount =0;
			}
			else
				tapCount =0;
			if(CirCir(0.88f,-.92f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				M.GameScreen = M.GAMEPAUSE;
//				M.loopStop();
//				M.play(mGR.mContext,R.drawable.splash_2);
				if(mGR.mSel !=0)
					M.sound2(mGR.mContext,R.drawable.button_click);
			}
		}
		return true;
	}
	
	
	boolean is = false; 
	void Draw_SignIn(GL10 gl)
	{
		if(is)
		{
			DrawTexture(gl, mGR.mTex_Downarrow,0,0);
		}
		else
		{
			DrawTexture(gl, mGR.mTex_cUp,0,0);
		}
		
		DrawTexture(gl, mGR.mTex_cUp,0,-.8f);
		DrawTexture(gl, mGR.mTex_cUp,0,0.8f);
		DrawTexture(gl, mGR.mTex_cUp,.8f,.8f);
		
//		System.out.println(Counter+"     "+is);
	}
	
	boolean Handle_SignIn(MotionEvent event)
	{
		if(MotionEvent.ACTION_UP== event.getAction())
		{
			if(CirCir(0,0, .2f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				is = GameRenderer.mStart.isSignedIn();
				if(!is)
				{
					GameRenderer.mStart.beginUserInitiatedSignIn();
				}
			}
			if(CirCir(0,0.8f, .2f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				is = GameRenderer.mStart.isSignedIn();
				if(is)
				{
					GameRenderer.mStart.Submitscore(R.string.leaderboard_score,10);
				}
			}
			if(CirCir(0.8f,0.8f, .2f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				is = GameRenderer.mStart.isSignedIn();
				if(is)
				{
					GameRenderer.mStart.Submitscore(R.string.leaderboard_high_score,100);
				}
			}
			if(CirCir(0,-.8f, .2f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				is = GameRenderer.mStart.isSignedIn();
				if(is)
				{
					GameRenderer.mStart.onShowLeaderboardsRequested();
				}
			}
		}
		return true;
	}
	
	void DrawTexture(GL10 gl,SimplePlane Tex,float x,float y)
	{
		Tex.drawPos(gl, x, y);
	}
	
	void DrawTextureR(GL10 gl,SimplePlane Tex,float x,float y,float angle)
	{
		Tex.drawRotet(gl,angle, x, y);
	}
	void DrawTextureS(GL10 gl,SimplePlane Tex,float x,float y,float scal)
	{
		Tex.drawScal(gl, x, y,scal,scal);
	}
	void DrawTrans(GL10 gl,SimplePlane Tex,float x,float y, float r,float g,float b)
	{
		Tex.drawTransprent(gl, x, y, r, g,b);
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
	void drawNumber(GL10 gl,int no,float x, float y,int al)
	{
		float dx = mGR.mTex_Font[0].width()*.7f;
		String strs = ""+no;
		if(al==1)
			x-=strs.length()*dx/4;
		for(int i =0;i<strs.length();i++){
			int k = ((int)strs.charAt(i))-48;
			if(k>=0 && k<mGR.mTex_Font.length)
				mGR.mTex_Font[k].drawPos(gl,x+i*dx,y);
		}
	}
	void RateUs()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.LINK+getClass().getPackage().getName()));
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
		    Toast.makeText(GameRenderer.mStart, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
	void Google()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://plus.google.com/101161010890539846728"));
	    mGR.mContext.startActivity(mIntent);
	}
	void facebook()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://www.facebook.com/HututuGames"));
	    mGR.mContext.startActivity(mIntent);
	}
	void setAnimation(float x,float y)
	{
		for(int i=0;i<mGR.ani.length;i++)
		{
			if(mGR.ani[i].x<-1.1||mGR.ani[i].x>1.1||mGR.ani[i].y<-1.1||mGR.ani[i].y>1.1)
			{
				mGR.ani[i].set(x, y);
			}
		}
	}
	void drawAnimation(GL10 gl)
	{
		for(int i=0;i<mGR.ani.length;i++)
		{
			if(mGR.ani[i].x>-1.2&&mGR.ani[i].x<1.2&&mGR.ani[i].y>-1.2&&mGR.ani[i].y<1.2)
			{
				mGR.mTex_8x8.drawRGBS(gl, mGR.ani[i].x, mGR.ani[i].y, mGR.ani[i].r, mGR.ani[i].g, mGR.ani[i].b, mGR.ani[i].scal);
				mGR.ani[i].update();
			}
		}
	}
}
