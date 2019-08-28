package com.hututu.game.bubblecandyrescue;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;


import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.view.MotionEvent;
import android.widget.Toast;

public class Group extends Mesh {
	GameRenderer mGR = null;
	int Counter = 0;
	float sx, sy;
	public void setting() { float ud = .01f; switch (GameRenderer.mStart._keyCode) { case 1: sy -= ud; break; case 2: sy += ud; break; case 3: sx -= ud; break; case 4: sx += ud; break; } System.out.println(M.GameScreen+ "~~~~~~~~~~~~~~~      " + sx + "~~~~~~~~~~~~       " + sy); }
	public boolean Handle(MotionEvent event) { if (CircRectsOverlap(-.8f, 0.0f, .1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f)) GameRenderer.mStart._keyCode = 3;
		if (CircRectsOverlap(0.8f, 0.0f, .1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f)) GameRenderer.mStart._keyCode = 4;
		if (CircRectsOverlap(-.0f, -.8f, .1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f)) GameRenderer.mStart._keyCode = 1;
		if (CircRectsOverlap(0.0f, 0.8f, .1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f)) GameRenderer.mStart._keyCode = 2;
		if (event.getAction() == MotionEvent.ACTION_UP) GameRenderer.mStart._keyCode = 0; return true;
	}
	public Group(GameRenderer _GameRenderer) {
		mGR = _GameRenderer;
	}
	@Override
	public void draw(GL10 gl) {
//		M.GameScreen = M.GAMEACHIV;
		switch (M.GameScreen) {
		case M.GAMELOGO:
			DrawTexture(gl, mGR.mTex_logo, 0, 0);
			if(Counter >60){
				M.GameScreen = M.GAMEADD;
				Counter = 0;
				if(mGR.addFree){
					M.play(GameRenderer.mContext, R.drawable.splash_theme);
					M.GameScreen = M.GAMEMENU;
				}
			}
			break;
		case M.GAMEADD:
			if(Counter>100)
				DrawTransScal(gl,mGR.mTex_Cross, .80f,0.85f,mGR.mSel == 1?1.1f:1,mGR.mSel == 1?0.5f:1);
			else 
			{
				mGR.mTex_Ling.drawSS(gl   ,-.24f,.87f	, .7f, 1.0f);//10
				mGR.mTex_LingBar.drawSS(gl,-.20f,.835f, Counter*.198f, 1.3f);//10
			}
			break;
		case M.GAMEMENU:
			Draw_Menu(gl);
			break;
		
		case M.GAMEABOUT:
			Draw_About(gl);
			break;
		case M.GAMECONTROL:
			Draw_Control(gl);
			break;
		case M.GAMEPLAY:
			Draw_Gameplay(gl);
			break;
		case M.GAMEOVER:
		case M.GAMEPAUSE:
		case M.GAMECONG:
		case M.GAMEWIN:
			Draw_PWOC(gl);
			break;
		case M.GAMELEVEL:
		case M.GAMEJOIN:
			Draw_Level(gl);
			break;
		}
//		Drow_Join(gl);
		Counter++;
//		 setting();
//		drawScore(gl, 4444, 0, 0, 1);
	}

	public boolean TouchEvent(MotionEvent event) {
		switch (M.GameScreen) {
		case M.GAMELOGO:
			break;
		case M.GAMEADD:
			mGR.mSel = 0;
			if(CirCir(0.85f,0.85f, .11f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				mGR.mSel = 1;
			}
			if((MotionEvent.ACTION_UP == event.getAction())&&(Counter>100)&&
					CirCir(0.85f,0.85f, .11f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
//				GameRenderer.mStart.show();
				M.GameScreen = M.GAMEMENU;
				mGR.mSel = 0;
				M.play(GameRenderer.mContext, R.drawable.splash_theme);
				M.sound10(GameRenderer.mContext,R.drawable.click);
//				M.sound1(GameRenderer.mContext, R.drawable.click);
			}
			break;
		case M.GAMEMENU:
			Handle_Menu(event);
			break;
		case M.GAMEPLAY:
			Handle_Gameplay(event);
			break;
		case M.GAMEABOUT:
			Handle_About(event);
			break;
		case M.GAMECONTROL:
			Handle_Control(event);
			break;
		case M.GAMEOVER:
		case M.GAMEPAUSE:
		case M.GAMEWIN:
		case M.GAMECONG:
			Handle_PWOC(event);
			break;
		case M.GAMELEVEL:
			Handle_Level(event);
			break;
		case M.GAMEJOIN:
			Handle_Join(event);
			break;
		}
		
//		Handle(event);
		return true;
	}
	
	/*************************Level End**********************************************/
	
	boolean Handle_Join(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(-.3f, -.3f, mGR.mTex_Jstr[1].width()*.4f, mGR.mTex_Jstr[1].Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 51;//Join
		}
		if(CircRectsOverlap(.3f, -.3f, mGR.mTex_Jstr[1].width()*.4f, mGR.mTex_Jstr[1].Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 52;//later
		}
		
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mGR.mSel) {
			case 51://Join
				GameRenderer.mStart.beginUserInitiatedSignIn();
				M.GameScreen = M.GAMELEVEL;	
				break;
			case 52://later
			M.GameScreen = M.GAMELEVEL;
				break;
			
			}
			if(mGR.mSel != 0)
				M.sound10(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	/*************************Join End**********************************************/
	
	
	/*************************Level Start**********************************************/
	int page = 0;
	float sd,sv,svx;
	boolean isDown = false;
	float lAnim = .1f;
	void Draw_Level(GL10 gl) {
		DrawTexture(gl, mGR.mTex_BG, 0, 0);
		
		if (lAnim < 1) {
			for (int i = 0; i < 15; i++) {
				if((page * 15 + i)<mGR.mULvl){
					DrawTransScal(gl, mGR.mTex_Button[1], -.62f + .63f * (i % 3) + sv, .8f - .28f * (i / 3),lAnim, 1);
					drawNumberScal(gl, page * 15 + i + 1, -.60f + .63f * (i % 3) + sv, .8f - .28f * (i / 3), 1,lAnim, 1);
				} else {
					DrawTransScal(gl, mGR.mTex_Button[0], -.62f + .63f * (i % 3) + sv, .8f - .28f * (i / 3),lAnim, 1);
				}
			}lAnim*=1.3f;
		}else{
			for (int i = 0; i < 15; i++) {
				if((page * 15 + i)<mGR.mULvl){
					if((page * 15 + i)==mGR.mLvl){
						mGR.mTex_LAnim[1].drawRotet2(gl,(Counter+20)%360, -.62f + .63f * (i % 3) + sv, .79f - .28f * (i / 3),1,0);
						DrawTransScal(gl, mGR.mTex_LAnim[0], -.62f + .63f * (i % 3) + sv, .79f - .28f * (i / 3),.7f+(Counter%20)*.01f, (20-(Counter%20))*.05f);
					}
					DrawTransScal(gl, mGR.mTex_Button[1], -.62f + .63f * (i % 3) + sv, .8f - .28f * (i / 3),mGR.mSel==i+1?1.1f:1, mGR.mSel==i+1?0.5f:1);
					drawNumber(gl, page * 15 + i + 1, -.60f + .63f * (i % 3) + sv, .8f - .28f * (i / 3), 1);
				}
				else
				{
					DrawTransScal(gl, mGR.mTex_Button[0], -.62f + .63f * (i % 3) + sv, .8f - .28f * (i / 3),mGR.mSel==i+1?1.1f:1, mGR.mSel==i+1?0.5f:1);
				}
			}
		}
	for (int i = 0; i < 7; i++) {
			if (page == i)
				DrawTexture(gl, mGR.mTex_Dot[1], -.35f + .13f * i, -.55f);
			else
				DrawTexture(gl, mGR.mTex_Dot[0], -.35f + .13f * i, -.55f);
		}
		
		
		if (sv != 0 && !isDown) {
			sv += svx;
			if (Math.abs(sv) > 1.8f) {
				if (sv > 0) {
					if (page > 0)
						page--;
				} else {
					if (page < 6)
						page++;
				}
				svx = sv = 0;lAnim =.2f;
			}
			if ((sv > 0 && page == 0) || (sv < 0 && page == 6)) {
				svx = sv = 0;
			}
		}
		DrawTransScal(gl, mGR.mTex_back	,.84f,-.60f,mGR.mSel==50?1.1f:1, mGR.mSel==50?0.5f:1);
		if(M.GameScreen == M.GAMEJOIN)
		{
			if (lAnim < 1)
				DrawTextureS(gl, mGR.mTex_Board, 0, 0,lAnim);
			else
			{
				DrawTexture(gl, mGR.mTex_Board, 0, 0);
				DrawTexture(gl, mGR.mTex_Jstr[0], 0, 0.1f);
			
				DrawTransScal(gl, mGR.mTex_Jstr[1]	,-.3f,-.30f,mGR.mSel==51?1.1f:1, mGR.mSel==51?0.5f:1);
				DrawTransScal(gl, mGR.mTex_Jstr[2]	,0.3f,-.30f,mGR.mSel==52?1.1f:1, mGR.mSel==52?0.5f:1);
			}
			
		}
	}
	boolean Handle_Level(MotionEvent event)
	{
		mGR.mSel = 0;
		for (int i = 0; i < 15; i++){
			if(CircRectsOverlap(-.62f + .63f * (i % 3) + sv, .8f - .28f * (i / 3), mGR.mTex_setting.width()*.5f, mGR.mTex_setting.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
			{
				mGR.mSel = i+1;
			}
		}
		if(CircRectsOverlap(.84f,-.60f, mGR.mTex_setting.width()*.5f, mGR.mTex_setting.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 50;
		}
		if(MotionEvent.ACTION_DOWN == event.getAction()){
			sd = screen2worldX(event.getX());
			isDown = true;
		}
		sv = screen2worldX(event.getX())-sd;
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			isDown = false;
			if(Math.abs(sv)<.1){
				sv = 0;
				switch (mGR.mSel) {
				case 50:
					M.GameScreen = M.GAMEMENU;
					M.play(GameRenderer.mContext, R.drawable.splash_theme);
					break;
				case 0:break;
				default:
					if ((page * 15 + (mGR.mSel-1)) < mGR.mULvl) {
						M.stop(GameRenderer.mContext);
						mGR.gameReset(page * 15 + mGR.mSel - 1);
						M.GameScreen = M.GAMEPLAY;
						M.play(GameRenderer.mContext, R.drawable.gameplay_theme);
					}
					break;
				}
				if(mGR.mSel != 0)
					M.sound10(GameRenderer.mContext, R.drawable.click);
			}
			else
				svx = (sv > 0) ? 0.2f : -0.2f;
			
				
			mGR.mSel = 0;
		}
		return true;
	}
	/*************************Level End************************************************/
	
	
	/*************************PWOC Start**********************************************/
	void Draw_PWOC(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_Splash	, 0, 0);
		DrawTexture(gl, mGR.mTex_Board	, 0, .20f);
		DrawTexture(gl, mGR.mTex_Board0	, 0,-.10f);
		DrawTexture(gl, mGR.mTex_Line	, 0,0.40f);
		DrawTexture(gl, mGR.mTex_Sharebar,0.0f,-.5f);
		DrawTransScal(gl, mGR.mTex_Freegame,0.0f,-.5f, mGR.mSel==4?1.1f:1, mGR.mSel==4?0.5f:1);
//		DrawTransScal(gl,mGR.mTex_setting	,-.80f,-.22f, mGR.mSel==5?1.1f:1, mGR.mSel==5?0.5f:1);
		
		DrawTexture(gl, mGR.mTex_Score		,0.0f,.55f);
		drawNumber(gl, mGR.score, -.0f,.45f,1);
		DrawTexture(gl, mGR.mTex_BestScore	,0.0f,.30f);
		if(mGR.IsEndless)
			drawNumber(gl, mGR.mHEScore, -.0f,.20f,1);
		else
			drawNumber(gl, mGR.BScore[mGR.mLvl%105], -.0f,.20f,1);
		
		if(mGR.IsEndless)
			DrawTransScal(gl,mGR.mTex_home	,-.35f,-.10f, mGR.mSel==1?1.1f:1, mGR.mSel==1?0.5f:1);
		else
			DrawTransScal(gl,mGR.mTex_Level	,-.35f,-.10f, mGR.mSel==1?1.1f:1, mGR.mSel==1?0.5f:1);
		DrawTransScal(gl,mGR.mTex_retry, .35f,-.10f, mGR.mSel==3?1.1f:1, mGR.mSel==3?0.5f:1);
		
		if(M.GameScreen == M.GAMEOVER) {
			DrawTexture(gl, mGR.mTex_Gameover	,0.00f, .75f);
			DrawTransScal(gl,mGR.mTex_leader,0.00f,-.12f, mGR.mSel==2?1.1f:1, mGR.mSel==2?0.5f:1);
		}
		if(M.GameScreen == M.GAMEPAUSE) {
			DrawTexture(gl, mGR.mTex_Gamepaus,0.00f, .75f);
			DrawTransScal(gl,mGR.mTex_play,0.00f,-.12f, mGR.mSel==2?1.1f:1, mGR.mSel==2?0.5f:1);
		}
		if(M.GameScreen == M.GAMECONG) {
			DrawTexture(gl, mGR.mTex_Cong	,0.00f, .75f);
			DrawTransScal(gl,mGR.mTex_Achiv,0.00f,-.12f, mGR.mSel==2?1.1f:1, mGR.mSel==2?0.5f:1);
		}
		if (M.GameScreen == M.GAMEWIN && !mGR.IsEndless) {
			DrawTexture(gl, mGR.mTex_LvlW	,0.00f, .75f);
			DrawTransScal(gl,mGR.mTex_conticn,0.00f,-.12f, mGR.mSel==2?1.1f:1, mGR.mSel==2?0.5f:1);
		}
	}
	boolean Handle_PWOC(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(-.35f,-.10f, mGR.mTex_setting.width()*.5f, mGR.mTex_setting.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 1;//Home
		}
		if(CircRectsOverlap(-.00f,-.10f, mGR.mTex_setting.width()*.5f, mGR.mTex_setting.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 2;//Common
		}
		if(CircRectsOverlap(.35f,-.10f, mGR.mTex_setting.width()*.5f, mGR.mTex_setting.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 3;//Retry
		}
		if(CircRectsOverlap(-.00f,-.5f, mGR.mTex_setting.width()*1.5f, mGR.mTex_setting.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 4;//More Games
		}
//		if(CircRectsOverlap(-.80f,-.22f, mGR.mTex_setting.width()*.5f, mGR.mTex_setting.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
//		{
//			mGR.mSel = 5;//Setting
//		}
//		if(CircRectsOverlap(0.50f,-.5f, mGR.mTex_setting.width()*1.5f, mGR.mTex_setting.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
//		{
//			mGR.mSel = 6;//Back
//		}
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mGR.mSel) {
			case 1://Home
				if (mGR.IsEndless) {
					M.GameScreen = M.GAMEMENU;
					M.play(GameRenderer.mContext, R.drawable.splash_theme);
				} else{
					M.GameScreen = M.GAMELEVEL;
					page = mGR.mLvl/15;
				}
				break;
			case 2:////Common
				if (M.GameScreen == M.GAMEWIN && !mGR.IsEndless) {
					if (mGR.mLvl < 105){
						M.stop(GameRenderer.mContext);
						
						mGR.gameReset(mGR.mLvl + 1);
						M.GameScreen = M.GAMEPLAY;
						M.play(GameRenderer.mContext,R.drawable.gameplay_theme);
					}
				} else if (M.GameScreen == M.GAMEOVER) {
					GameRenderer.mStart.onShowLeaderboardsRequested();//Leader Board
				} else if (M.GameScreen == M.GAMEPAUSE) {
					M.GameScreen = M.GAMEPLAY;
					M.play(GameRenderer.mContext,R.drawable.gameplay_theme);
				}else if (M.GameScreen == M.GAMECONG) {
					GameRenderer.mStart.onShowAchievementsRequested();//Achievement
				}
				break;
			case 3:// Retry
				if (mGR.IsEndless) {
					mGR.gameEndless();
					M.GameScreen = M.GAMEPLAY;
					M.play(GameRenderer.mContext,R.drawable.gameplay_theme);
				} else {
					mGR.gameReset(mGR.mLvl);
					M.GameScreen = M.GAMEPLAY;
					M.play(GameRenderer.mContext,R.drawable.gameplay_theme);
				}
				break;
			case 4://Facebook
				MoreGame();
				break;
//			case 5://Setting
//				M.GameScreen = M.GAMECONTROL;
//				break;
//			case 6://Back
//				M.GameScreen = M.GAMEMENU;
//				break;
			}
			if(mGR.mSel != 0)
				M.sound10(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	/*************************PWOC End************************************************/
	
	
	/*************************Control Start**********************************************/
	void Draw_Control(GL10 gl) {
		DrawTexture(gl, mGR.mTex_Splash	, 0, 0);
		DrawTexture(gl, mGR.mTex_Board	, 0, .20f);
		DrawTexture(gl, mGR.mTex_Board0	, 0,-.10f);
		DrawTexture(gl, mGR.mTex_Line	, 0,0.54f);
		
		DrawTexture(gl, mGR.mTex_control,0.00f, .78f);
		DrawTransScal(gl, mGR.mTex_adsbar	,0.00f, .63f, mGR.mSel==10?1.03f:1f, mGR.mSel==10?0.5f:1);
		DrawTexture(gl, mGR.mTex_controlT,-.21f,.27f);
		
		
		DrawTransScal(gl,mGR.mTex_Sound[M.setValue?1:0]	,0.21f,0.42f, mGR.mSel==7?0.9f:.8f, mGR.mSel==7?0.5f:1);
		DrawTransScal(gl,mGR.mTex_Music[M.setMusic?1:0]	,0.21f,0.26f, mGR.mSel==8?0.9f:.8f, mGR.mSel==8?0.5f:1);
		DrawTransScal(gl,mGR.mTex_share   				,0.21f,0.10f, mGR.mSel==9?0.9f:.8f, mGR.mSel==9?0.5f:1);
		
		
		DrawTransScal(gl,mGR.mTex_rateus,-.35f,-.10f, mGR.mSel==1?1.1f:1, mGR.mSel==1?0.5f:1);
		DrawTransScal(gl,mGR.mTex_Achiv	,0.00f,-.12f, mGR.mSel==2?1.1f:1, mGR.mSel==2?0.5f:1);
		DrawTransScal(gl,mGR.mTex_leader, .35f,-.10f, mGR.mSel==3?1.1f:1, mGR.mSel==3?0.5f:1);
		
		DrawTexture(gl, mGR.mTex_Sharebar,0.0f,-.5f);
		DrawTransScal(gl,mGR.mTex_fb	,-.50f,-.5f, mGR.mSel==4?1.1f:1, mGR.mSel==4?0.5f:1);
		DrawTransScal(gl,mGR.mTex_gp	,0.00f,-.5f, mGR.mSel==5?1.1f:1, mGR.mSel==5?0.5f:1);
		DrawTransScal(gl,mGR.mTex_back	,0.50f,-.5f, mGR.mSel==6?1.1f:1, mGR.mSel==6?0.5f:1);
	}
	boolean Handle_Control(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(-.35f,-.10f, mGR.mTex_setting.width()*.5f, mGR.mTex_setting.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 1;//Rate Us
		}
		if(CircRectsOverlap(-.00f,-.10f, mGR.mTex_setting.width()*.5f, mGR.mTex_setting.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 2;//Achievement
		}
		if(CircRectsOverlap(.35f,-.10f, mGR.mTex_setting.width()*.5f, mGR.mTex_setting.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 3;//Lead-board
		}
		if(CircRectsOverlap(-.50f,-.5f, mGR.mTex_setting.width()*.5f, mGR.mTex_setting.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 4;//Facebook
		}
		if(CircRectsOverlap(-.00f,-.5f, mGR.mTex_setting.width()*.5f, mGR.mTex_setting.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 5;//G Plus
		}
		if(CircRectsOverlap(0.50f,-.5f, mGR.mTex_setting.width()*.5f, mGR.mTex_setting.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 6;//Back
		}
		if(CircRectsOverlap(0.21f,0.42f, mGR.mTex_setting.width()*.4f, mGR.mTex_setting.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 7;//Sound
		}
		if(CircRectsOverlap(0.21f,0.26f, mGR.mTex_setting.width()*.4f, mGR.mTex_setting.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 8;//Music
		}
		if(CircRectsOverlap(0.21f,0.16f, mGR.mTex_setting.width()*.4f, mGR.mTex_setting.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 9;//Share
		}
		if(CircRectsOverlap(0.00f, .63f, mGR.mTex_adsbar.width()*.25f, mGR.mTex_setting.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 10;//Ads
		}
		if(MotionEvent.ACTION_UP == event.getAction())
		{
//			System.out.println(mGR.mSel);
			switch (mGR.mSel) {
			case 1://RateUs
				RateUs();
				break;
			case 2://Leader-board
				GameRenderer.mStart.onShowLeaderboardsRequested();;
				break;
			case 3://Achievement
				GameRenderer.mStart.onShowAchievementsRequested();
				break;
			case 4://Facebook
				facebook();
				break;
			case 5://GPlus
				Gplus();
				break;
			case 6://Back
				M.GameScreen = M.GAMEMENU;
				M.play(GameRenderer.mContext, R.drawable.splash_theme);
				break;
			case 7://Sound
				M.setValue=!M.setValue;
				break;
			case 8://Music
				M.setMusic=!M.setMusic;
				break;
			case 9://Share
				share2friend();
				break;
			case 10:
				mGR.mMainActivity.onBuyGold50000(null);
				break;
			}
			if(mGR.mSel != 0)
				M.sound10(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	/*************************Control End************************************************/
	
	
	/*************************About Start**********************************************/
	void Draw_About(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_Splash	, 0, 0);
		DrawTexture(gl, mGR.mTex_Board	, 0, .20f);
		DrawTexture(gl, mGR.mTex_Board0	, 0,-.10f);
		DrawTexture(gl, mGR.mTex_Line	, 0,0.54f);
		
		DrawTexture(gl, mGR.mTex_AboutF	,0.00f, .75f);
		DrawTexture(gl, mGR.mTex_AboutTxt,.00f, .35f);
		DrawTransScal(gl,mGR.mTex_share	,-.35f,-.10f, mGR.mSel==1?1.1f:1, mGR.mSel==1?0.5f:1);
		DrawTransScal(gl,mGR.mTex_hututu,0.00f,-.12f, mGR.mSel==2?1.1f:1, mGR.mSel==2?0.5f:1);
		DrawTransScal(gl,mGR.mTex_ads	, .35f,-.10f, mGR.mSel==3?1.1f:1, mGR.mSel==3?0.5f:1);
		
		DrawTexture(gl, mGR.mTex_Sharebar,0.0f,-.5f);
		DrawTransScal(gl,mGR.mTex_fb	,-.50f,-.5f, mGR.mSel==4?1.1f:1, mGR.mSel==4?0.5f:1);
		DrawTransScal(gl,mGR.mTex_gp	,0.00f,-.5f, mGR.mSel==5?1.1f:1, mGR.mSel==5?0.5f:1);
		DrawTransScal(gl,mGR.mTex_back	,0.50f,-.5f, mGR.mSel==6?1.1f:1, mGR.mSel==6?0.5f:1);
	}
	boolean Handle_About(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(-.35f,-.10f, mGR.mTex_setting.width()*.5f, mGR.mTex_setting.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 1;//Share
		}
		if(CircRectsOverlap(-.00f,-.10f, mGR.mTex_setting.width()*.5f, mGR.mTex_setting.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 2;//More Games
		}
		if(CircRectsOverlap(.35f,-.10f, mGR.mTex_setting.width()*.5f, mGR.mTex_setting.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 3;//Ads Free
		}
		if(CircRectsOverlap(-.50f,-.5f, mGR.mTex_setting.width()*.5f, mGR.mTex_setting.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 4;//Facebook
		}
		if(CircRectsOverlap(-.00f,-.5f, mGR.mTex_setting.width()*.5f, mGR.mTex_setting.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 5;//G Plus
		}
		if(CircRectsOverlap(0.50f,-.5f, mGR.mTex_setting.width()*.5f, mGR.mTex_setting.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 6;//Back
		}
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mGR.mSel) {
			case 1://Share
				share2friend();
				break;
			case 2://More Game
				MoreGame();
				break;
			case 3://Ads Free
				mGR.mMainActivity.onBuyGold50000(null);
				break;
			case 4://Facebook
				facebook();
				break;
			case 5://G Plus
				Gplus();
				break;
			case 6://Back
				M.GameScreen = M.GAMEMENU;
				M.play(GameRenderer.mContext, R.drawable.splash_theme);
				break;
			}
			if(mGR.mSel != 0)
				M.sound10(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	/*************************About End************************************************/
	
	
	/*************************Menu Start**********************************************/
	float cx = -.63f,cy = .48f;
	float ang = 180,vang = .1f;
	void Draw_Menu(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_Splash, 0, 0);
		
		ang+=vang;if(ang>184)vang=-.1f;
		if(ang<176)vang=.1f;
		cx = .70f;cy = .27f;
		mGR.mTex_HangS.drawRotet2(gl, ang, cx, cy, 1, .3f);
		DrawTransScal(gl, mGR.mTex_cont, (cx - (float) Math.sin(Math.toRadians(ang)) * 0.385f*3.5f),
				(cy + (float) Math.cos(Math.toRadians(ang)) * .231f*3.5f), mGR.mSel==3?1.1f:1, mGR.mSel==3?0.5f:1);
		
		
		
		cx = -.63f;cy = .48f;
		mGR.mTex_HangB.drawRotet2(gl, ang, cx, cy, 1, .3f);
		DrawTransScal(gl, mGR.mTex_newgame, (cx - (float) Math.sin(Math.toRadians(ang)) * 0.385f*3.84f),
				(cy + (float) Math.cos(Math.toRadians(ang)) * .231f*3.84f), mGR.mSel==2?1.1f:1, mGR.mSel==2?0.5f:1);
		DrawTextureS(gl, mGR.mTex_Name, 0, .45f,1+(ang-180)*.005f);
		DrawTransScal(gl,mGR.mTex_More	, .82f, .88f, mGR.mSel==1?1.1f:1, mGR.mSel==1?0.5f:1);
		DrawTransScal(gl,mGR.mTex_setting,-.83f,-.9f, mGR.mSel==4?1.1f:1, mGR.mSel==4?0.5f:1);
		DrawTransScal(gl,mGR.mTex_About	,-.51f,-.90f, mGR.mSel==5?1.1f:1, mGR.mSel==5?0.5f:1);
		DrawTransScal(gl,mGR.mTex_leader, .51f,-.90f, mGR.mSel==6?1.1f:1, mGR.mSel==6?0.5f:1);
		DrawTransScal(gl,mGR.mTex_Achiv	, .83f,-.90f, mGR.mSel==7?1.1f:1, mGR.mSel==7?0.5f:1);
	}
	boolean Handle_Menu(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(.82f, .88f, mGR.mTex_setting.width()*.5f, mGR.mTex_setting.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 1;//More
		}
		if(CircRectsOverlap(-.61f, -.37f, mGR.mTex_setting.width()*.5f, mGR.mTex_setting.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 2;//New Game
		}
		if(CircRectsOverlap(.7f, -.51f, mGR.mTex_setting.width()*.5f, mGR.mTex_setting.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 3;//EndLess
		}
		if(CircRectsOverlap(-.83f,-.9f, mGR.mTex_setting.width()*.5f, mGR.mTex_setting.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 4;//Setting
		}
		if(CircRectsOverlap(-.51f,-.9f, mGR.mTex_setting.width()*.5f, mGR.mTex_setting.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 5;//About
		}
		if(CircRectsOverlap(0.51f,-.9f, mGR.mTex_setting.width()*.5f, mGR.mTex_setting.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 6;//Leader-board
		}
		if(CircRectsOverlap(0.83f,-.9f, mGR.mTex_setting.width()*.5f, mGR.mTex_setting.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 7;//Achievement
		}
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mGR.mSel) {
			case 1://More
				MoreGame();
				break;
			case 2://New Game
				lAnim = .1f;
				M.GameScreen = M.GAMELEVEL;
				page = mGR.mLvl/15;
				if(!GameRenderer.mStart.isSignedIn())
					M.GameScreen = M.GAMEJOIN;
				M.stop(GameRenderer.mContext);
				break;
			case 3://EndLess
				mGR.gameEndless();
				M.GameScreen = M.GAMEPLAY;
				M.play(GameRenderer.mContext,R.drawable.gameplay_theme);
				break;
			case 4://Setting
				M.GameScreen = M.GAMECONTROL;
				M.stop(GameRenderer.mContext);
				break;
			case 5://About
				M.GameScreen = M.GAMEABOUT;
				M.stop(GameRenderer.mContext);
				break;
			case 6://Leader-board
				GameRenderer.mStart.onShowLeaderboardsRequested();;
				break;
			case 7://Achievement
				GameRenderer.mStart.onShowAchievementsRequested();
				break;
			}
			if(mGR.mSel != 0)
				M.sound10(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	/*************************Menu End************************************************/
	
	
	
	/*************************Game Start**********************************************/
	int count1;
	boolean[][] visited;
	int NOCOLOR = -1;

	
	void gameUpdate()
	{
		for(int i=0;i<mGR.Achi.length;i++)
		{
			if(!mGR.Achi[i] && GameRenderer.Candy[i]>10){
				mGR.Achi[i] = true;
				GameRenderer.mStart.UnlockAchievement(mGR.achiment[i]);
			}
		}
		if (!mGR.IsEndless) {
			int scr = 0;
			for (int i = 0; i < mGR.BScore.length; i++)
				scr += mGR.BScore[i];
			GameRenderer.mStart.Submitscore(R.string.leaderboard_puzzle_score, scr);
		}else{
			GameRenderer.mStart.Submitscore(R.string.leaderboard_arcade_score, mGR.mHEScore);
		}
		if (mGR.ads % 3 == 1) {
			GameRenderer.mStart.show();
		}
		mGR.ads++;
	}
	
	
	int setNewBall() {
		if(mGR.IsEndless){
			mGR.mPlayer.ResColor = M.mRand.nextInt(7);
			return mGR.mPlayer.ResColor;
		}
		int setNew = 0;
		for (int i = 0; i < mGR.matrix.length; i++) {
			for (int j = 0; j < mGR.matrix[i].length; j++) {
				if (mGR.matrix[i][j].Color > emp) {
					setNew++;
				}
			}
		}
		if (setNew > 0) {
			setNew = M.mRand.nextInt(setNew);
			if(setNew==0)
				setNew=1;
			int new1 = 0;
			for (int i = 0; i < mGR.matrix.length && new1 < setNew; i++) {
				for (int j = 0; j < mGR.matrix[i].length && new1 < setNew; j++) {
					if (mGR.matrix[i][j].Color > emp) {
						mGR.mPlayer.ResColor = mGR.matrix[i][j].Color % 7;
						new1++;
					}
				}
			}
		}
		setNew = mGR.mPlayer.ResColor;
		mx =-.10f;
		my = .02f;
		
		return setNew;
	}
	
	Point currentPosition() {
		int posX = (int) Math.floor((mGR.by - mGR.mPlayer.y) / bdy);
		int posY = (int) Math.floor(((-bx + mGR.mPlayer.x) / bdx) + (bdx/2) * (posX % 2));
		
//		System.out.println(posX+"  mm  "+posY);
		if (posY > M.C-1)
			posY = M.C-1;
		if (posX > M.Row-1)
			posX = M.Row-1;
		if (posY < 0) 
			posY = 0;
		if (posX < 0) 
			posX = 0;
		return new Point(posX, posY);
	}

	ArrayList<Ball> getNeighbors(Point p) {
		Ball[][] grid = mGR.matrix;
		ArrayList<Ball> list = new ArrayList<Ball>();
		if ((p.x % 2) == 0) {
			if (p.y > 0) {
				list.add(grid[p.x][p.y - 1]);
			}
			if (p.y < M.C-1) {
				list.add(grid[p.x][p.y + 1]);
				if (p.x > 0) {
					list.add(grid[p.x - 1][p.y]);
					list.add(grid[p.x - 1][p.y + 1]);
				}
				if (p.x < M.Row-1) {
					list.add(grid[p.x + 1][p.y]);
					list.add(grid[p.x + 1][p.y + 1]);
				}
			} else {
				if (p.x > 0) {
					list.add(grid[p.x - 1][p.y]);
				}
				if (p.x < M.Row-1) {
					list.add(grid[p.x + 1][p.y]);
				}
			}
		} else {
			if (p.y < M.C-1) {
				list.add(grid[p.x][p.y + 1]);
			}
			if (p.y > 0) {
				list.add(grid[p.x][p.y - 1]);
				if (p.x > 0) {
					list.add(grid[p.x - 1][p.y]);
					list.add(grid[p.x - 1][p.y - 1]);
				}
				if (p.x < M.Row-1) {
					list.add(grid[p.x + 1][p.y]);
					list.add(grid[p.x + 1][p.y - 1]);
				}
			} else {
				if (p.x > 0) {
					list.add(grid[p.x - 1][p.y]);
				}

				if (p.x < M.Row-1) {
					list.add(grid[p.x + 1][p.y]);
				}
			}
		}

		return list;
	}

	void checkJump(ArrayList<Ball> jump, Ball compare) {
		if (compare.visited) {
			return;
		}
		mGR.matrix[compare.i][compare.j].visited = true;
		mGR.matrix[compare.i][compare.j].anim();
		if (mGR.mPlayer.fireColor == compare.Color) {
			checkJump(jump, this.getNeighbors(new Point(compare.i, compare.j)),compare);
		}
	}

	void checkJump(ArrayList<Ball> jump, ArrayList<Ball> neighbors,Ball ball) {
		jump.add(ball);
		for (int i = 0; i < neighbors.size(); i++) {
			Ball current = (Ball) neighbors.get(i);//elementAt(i);
			if (current != null) {
				checkJump(jump, current);
			}
		}
	}

	int score(int color) {
		int scr = 321 + mGR.mPlayer.fireColor;
		
		if (color == mGR.mPlayer.fireColor)
			scr = 123 + mGR.mPlayer.fireColor;
		
		mGR.score += scr;
		
		if (mGR.IsEndless) {
			if (mGR.mHEScore < mGR.score) {
				mGR.mHEScore = mGR.score;
				mGR.playBest = true;
			}
		} else {
			if (mGR.BScore[mGR.mLvl] < mGR.score) {
				mGR.BScore[mGR.mLvl] = mGR.score;
				mGR.playBest = true;
			}
		}
		return scr;
	}
	final byte emp = -1;
	
	
	void forFall()
	{
		int k =0;
		int cnt2 =0;
		for (int i = 0; i < M.C; i++) {
			if (mGR.matrix[0][i].Color > emp) {
				checkFall(mGR.matrix[0][i]);
			}
		}

		for (int i = 0; i < mGR.matrix.length; i++) {
			for (int j = 0; j < mGR.matrix[i].length; j++) {
				if (mGR.matrix[i][j].Color > emp) {
					if (!mGR.matrix[i][j].checkFall) {
						mGR.mRBall[k++].set((bx + (i % 2==0?1:0)*(bdx/2)) + j * bdx, mGR.by - i * bdy,
								mGR.matrix[i][j].Color,10000+(cnt2+=1),mGR.matrix[i][j].candy,score(mGR.matrix[i][j].Color));
						mGR.matrix[i][j].reset();
					}
				}
			}
		}
		for (int j = 0; j < M.Row; j++)
			for (int i = 0; i < M.C; i++) {
				{
					mGR.matrix[j][i].checkFall = false;
				}
			}
	
	}
	
	void checkJump(int x, int y, int color) {
		int k =1;
		int cnt =10;
		int cnt2 =0;
		mGR.matrix[x][y].Color = color;
//		mGR.bubbleFace.copy(mGR.matrix[x][y]);
		ArrayList<Ball> checkJump = new ArrayList<Ball>();
		checkJump(checkJump, mGR.matrix[x][y]);
		if (checkJump.size() >= 3) {
			for (int i = 0; i < checkJump.size(); i++) {
				Ball current = (Ball) checkJump.get(i);
				mGR.mRBall[k++].set((bx + (current.i % 2==0?1:0)*(bdx/2)) + current.j * bdx, mGR.by - current.i * bdy,current.Color,(color==current.Color)?cnt+=1:10000+(cnt2+=1),current.candy,score(current.Color));
				mGR.matrix[current.i][current.j].reset();
			}
			for (int i = 0; i < M.C; i++) {
				if (mGR.matrix[0][i].Color > emp) {
					checkFall(mGR.matrix[0][i]);
				}
			}

			for (int i = 0; i < mGR.matrix.length; i++) {
				for (int j = 0; j < mGR.matrix[i].length; j++) {
					if (mGR.matrix[i][j].Color > emp) {
						if (!mGR.matrix[i][j].checkFall) {
							mGR.mRBall[k++].set((bx + (i % 2==0?1:0)*(bdx/2)) + j * bdx, mGR.by - i * bdy,mGR.matrix[i][j].Color,(color==mGR.matrix[i][j].Color)?cnt+=1:10000+(cnt2+=1),mGR.matrix[i][j].candy,score(mGR.matrix[i][j].Color));
							
							mGR.matrix[i][j].reset();
						}
					}
				}
			}

		} else if (mGR.IsEndless) {
			if (mGR.hitBall > 1)
				mGR.hitBall--;
			else {
				mGR.hitBall = 5;
				for (int j = M.Row - 1; j > 0; j--) {// 13
					for (int i = 0; i < M.C; i++) {// 9
						mGR.matrix[j][i].Color = mGR.matrix[j - 1][i].Color;
					}
				}
				{
					boolean zero = true;
					for (int i = 0; i < M.C; i++) {
						if (mGR.matrix[0][i].Color > emp)
							zero = false;
					}
					for (int i = 0; i < M.C; i++) {// 9
						mGR.matrix[0][i].set(M.mRand.nextInt(8), 0, i);
					}
					if(zero)
						for (int i = 0; i < M.C; i++) {// 9
							mGR.matrix[1][i].set(M.mRand.nextInt(8), 1, i);
						}
				}
				
				forFall();
			}
		}
		if (checkJump.size() < 3)
		{
			mGR.mJoin.set((bx + (x % 2 == 0 ? 1 : 0) * (bdx/2)) + y * bdx, mGR.by - x * bdy, 10, 0);
		}
		for (int i = 0; i < mGR.matrix.length; i++) {
			for (int j = 0; j < mGR.matrix[i].length; j++) {
				mGR.matrix[i][j].visited = false;
				mGR.matrix[i][j].checkFall = false;
			}
		}
	}

	public void checkFall(Ball check) {
		if (check.checkFall) {
			return;
		}
		mGR.matrix[check.i][check.j].checkFall = true;

		ArrayList<Ball> v = getNeighbors(new Point(check.i,check.j));

		for (int i = 0; i < v.size(); i++) {
			Ball current = (Ball) v.get(i);

			if (current.Color > emp) {
				checkFall(current);
			}
		}
	}
	
	boolean checkCollision()
	{
		if (mGR.by + bdy < mGR.mPlayer.y) {
			int j = currentPosition().y;
			if (mGR.matrix[0][j].Color != -1 && (j > 0 && j < 8)) {
				float d1 = distance((bx + (bdx / 2)) + (j - 1) * bdx, mGR.by, mGR.mPlayer.x, mGR.mPlayer.x);
				float d2 = distance((bx + (bdx / 2)) + (j + 1) * bdx, mGR.by, mGR.mPlayer.x, mGR.mPlayer.x);
				if (mGR.matrix[0][j - 1].Color == -1
						&& mGR.matrix[0][j + 1].Color == -1) {
					if (d1 < d2) {
						j = j - 1;
					} else
						j = j + 1;
				} else if (mGR.matrix[0][j - 1].Color == -1) {
					j = j - 1;
				} else if (mGR.matrix[0][j + 1].Color == -1) {
					j = j + 1;
				}
			}
			
			mGR.matrix[0][j].Color = mGR.mPlayer.fireColor;
			checkJump(0, j, mGR.mPlayer.fireColor);
			mGR.mPlayer.set(0, false);
			mGR.mPlayer.fireColor = mGR.mPlayer.ResColor;
			if (delete() > emp)
				mGR.mPlayer.fireColor = delete();
			setNewBall();
		} else {
			for (int i = 0; i < mGR.matrix.length; i++) {
				for (int j = 0; j < mGR.matrix[i].length; j++) {
					if (mGR.matrix[i][j].Color > emp) {
						if (CirCir((bx + (i % 2 == 0 ? 1 : 0) * (bdx/2)) + j * bdx, mGR.by - i * bdy, redius, mGR.mPlayer.x, mGR.mPlayer.y, redius))
						//if(Rect2RectIntersection((bx + (i % 2 == 0 ? 1 : 0) * (bdx/2)) + j * bdx, by - i * bdy, (bdx/2),(bdy/2), mGR.mPlayer.x, mGR.mPlayer.y, (bdx/2),(bdy/2)))
						{
							if(mGR.matrix[i][j].Color == 7)
							{
								M.sound6(GameRenderer.mContext, R.drawable.bubble_candy_blast);
								forFall();
								mGR.mRBall[0].set((bx + (i % 2==0?1:0)*(bdx/2)) + j * bdx, mGR.by - i * bdy,mGR.matrix[i][j].Color,10000,mGR.matrix[i][j].candy,score(mGR.matrix[i][j].Color));
//								mGR.mRBall[k+.set((bx + (i % 2==0?1:0)*(bdx/2)) + j * bdx, mGR.by - i * bdy,mGR.matrix[i][j].Color,(color==mGR.matrix[i][j].Color)?cnt+=1:10000+(cnt2+=1),mGR.matrix[i][j].candy,score(mGR.matrix[i][j].Color));
								mGR.matrix[i][j].Color = emp;
								return false;
							}
							int p = 0; float dis = 10;
							Point point = currentPosition();
							ArrayList<Ball> b = getNeighbors(point);
							
							for (int k = 0; k < b.size(); k++) {
								if(mGR.matrix[b.get(k).i][b.get(k).j].Color==-1) {
									p=k; break;
								}
							}
							
							for (int k = 0; k < b.size(); k++) {
								float d = distance(mGR.mPlayer.x, mGR.mPlayer.y, (bx + (b.get(k).i % 2 == 0 ? 1 : 0) * (bdx/2)) + b.get(k).j * bdx, mGR.by - b.get(k).i * bdy);
//								System.out.println(k+" -->  ["+b.get(k).i+","+b.get(k).j+"]"+mGR.matrix[b.get(k).i][b.get(k).j].Color);
								if (dis > d && mGR.matrix[b.get(k).i][b.get(k).j].Color==-1) {
									dis = d;
									p = k;
								}
							}
							for (int k = 0; k < mGR.mRBall.length; k++) {
								mGR.mRBall[k].reset();
								}
							score(mGR.mPlayer.fireColor);
//							System.out.println("[" + point.x + " , " + point.y+ "] ["
//									+ b.get(p).i + " , " + b.get(p).j
//									+ "]  ~~~~~~~~~~~~~~~~~~~~~~~~>  "
//									+ mGR.matrix[b.get(p).i][b.get(p).j].Color
//									+ "  " + mGR.matrix[i][j].i + "   "
//									+ mGR.matrix[i][j].j);
							
							
							ArrayList<Ball> yogesh = getNeighbors(new Point(b.get(p).i,b.get(p).j));
//							if (yogesh.size() == 6) 
							{
								int new1 =0;
								for (int k = 0; k < yogesh.size(); k++) {
//									System.out.println("~~[" + yogesh.get(k).i + " , " + yogesh.get(k).j + "]  "
//											+ mGR.matrix[yogesh.get(k).i][yogesh.get(k).j].Color);
									if(mGR.matrix[yogesh.get(k).i][yogesh.get(k).j].Color==emp || mGR.matrix[yogesh.get(k).i][yogesh.get(k).j].Color==7)
										new1++;
								}
								if (yogesh.size() == new1)
									return false;
							}
							M.sound7(GameRenderer.mContext, R.drawable.bubble_colusion);
							mGR.matrix[b.get(p).i][b.get(p).j].Color = mGR.mPlayer.fireColor;
							checkJump(b.get(p).i, b.get(p).j, b.get(p).Color);
							mGR.mPlayer.set(0, false);
							mGR.mPlayer.fireColor = mGR.mPlayer.ResColor;
							if (delete() > emp)
								mGR.mPlayer.fireColor = delete();
							setNewBall();
							return true;
						}
					}
				}
			}
			
		}
		return false;
	}
	float distance(float cx1,float cy1,float cx2,float cy2)
	{
		return (float)Math.sqrt(((cx1 - cx2) * (cx1 - cx2)) + ((cy1 - cy2) * (cy1 - cy2)));
	}

	int delete() {
		int color = emp;
		for (int i = 0; i < mGR.matrix.length; i++) {
			for (int j = 0; j < mGR.matrix[i].length; j++) {
				if (mGR.matrix[i][j].Color > emp) {
					if (mGR.matrix[i][j].Color == mGR.mPlayer.fireColor) {
						return emp;
					} else if(mGR.matrix[i][j].Color!=7){
						color = mGR.matrix[i][j].Color;
					}
				}
			}
		}
		return color;
	}

	void gameLogic() {
		if (!mGR.IsEndless)
			mGR.by -= .0002f;
		
		if (mGR.mPlayer.move) {
			mGR.mPlayer.update();
			if(checkCollision())
				mGR.mPlayer.vx = mGR.mPlayer.vy =0;
			
			
			for (int i = 0,k=0; i < mGR.mAnim.length &&k<2; i++) {
				if(mGR.mAnim[i].anim<=0){
					mGR.mAnim[i].set(mGR.mPlayer.x-mGR.mPlayer.vx*k*.5f, mGR.mPlayer.y-mGR.mPlayer.vy*k*.5f,10,mGR.mPlayer.fireColor);
					k++;
				}
			}
		}
		
		
		boolean isGO = true;
		for (int i = mGR.matrix.length-1; i >=0 && isGO; i--) {
			for (int j = 0; j < mGR.matrix[i].length && isGO; j++) {
				if (mGR.matrix[i][j].Color > emp) {
					isGO = false;
					dis = ((mGR.by - i * bdy) - (M.BY - (M.Row - 1) * bdy));
//					System.out.println(i+"~~~~"+j+" "+dis);
				}
			}
		}

		for (int i = 0; i < mGR.mRBall.length && isGO; i++) {
			if(mGR.mRBall[i].count>0)
				isGO = false;
		}

		if (isGO && !mGR.IsEndless) {
			if (mGR.win > 20) {
				M.stop(GameRenderer.mContext);
				if(mGR.mULvl<mGR.mLvl+2)
					mGR.mULvl=mGR.mLvl+2;
				if (mGR.mLvl < 104)
					M.GameScreen = M.GAMEWIN;
				else
					M.GameScreen = M.GAMECONG;
				M.sound12(GameRenderer.mContext, R.drawable.level_complete);
//				if(mGR.playBest)
//					M.sound11(GameRenderer.mContext, R.drawable.cracker);
				gameUpdate();
				
			} else
				mGR.win++;
		}
//		for (int j = 0; j < mGR.matrix[12].length ; j++) 
		{
			if (dis < bdy*.4f)
			{
				if (mGR.win > 20){
					gameUpdate();
					M.stop(GameRenderer.mContext);
					M.GameScreen = M.GAMEOVER;
					M.sound13(GameRenderer.mContext, R.drawable.game_over);
//					if(mGR.playBest)
//						M.sound11(GameRenderer.mContext, R.drawable.cracker);
					
				}
				else
					mGR.win++;
				
			}
		}
		
		
	}
	float dis = 10;
	final float redius = .06f;
	final float bdx=.21f ,bdy = .11f;
	final float bx=-.90f ;
	float mx,my;
	void Draw_Gameplay(GL10 gl) {
		DrawTexture(gl, mGR.mTex_BG, 0, 0);
		if(Counter%2==0)
			count1++;
		DrawTexture(gl, mGR.mTex_Jar[count1%8], 0,-.91f);
		
		DrawTexture(gl, mGR.mTex_House[0], -.62f,-.545f);
		for(int i=5-mGR.hitBall;i<5;i++){
			DrawTexture(gl, mGR.mTex_House[1], mx-.86f+i*.10f, my-.61f-i*.020f);
		}
		
		if(Counter>10)
			DrawTexture  (gl, mGR.mTex_Ball[mGR.mPlayer.ResColor], mx-.86f+5*.10f, my-.61f-5*.020f);
		else
			DrawTransScal(gl, mGR.mTex_Ball[mGR.mPlayer.ResColor], mx-.86f+5*.10f, my-.61f-5*.020f,(10-Counter)*.01f+1,Counter*.1f);

		
		DrawTexture(gl, mGR.mTex_House[2], -.62f,-.545f);
		if (mx < 0f) {
			mx += .010f;
			my -= .002f;
		}
		
		
		DrawTexture(gl, mGR.mTex_Exchenge[2],-.83f,-.75f);
		DrawTexture(gl, mGR.mTex_Exchenge[mGR.mSel%2],-.83f,-.74f);
		
		for (int i = 0; i < mGR.matrix.length; i++) {
			for (int j = 0; j < mGR.matrix[i].length; j++) {
				if (mGR.matrix[i][j].Color == 7)
					DrawTextureS(gl, mGR.mTex_Candy[mGR.matrix[i][j].candy], (bx + (i % 2==0?1:0)*(bdx/2)) + j * bdx, mGR.by - i * bdy,.6f);
				if (mGR.matrix[i][j].Color > emp) {
					if (mGR.matrix[i][j].anim > 0){
						DrawTextureS(gl, mGR.mTex_Ball[mGR.matrix[i][j].Color], (bx + (i % 2==0?1:0)*(bdx/2)) + j * bdx, mGR.by - i * bdy,mGR.matrix[i][j].anim*.02f+1);
						mGR.matrix[i][j].anim--;
					}
					else
						DrawTexture(gl, mGR.mTex_Ball[mGR.matrix[i][j].Color], (bx + (i % 2==0?1:0)*(bdx/2)) + j * bdx, mGR.by - i * bdy);
					
				}
			}
		}
		
		if(mGR.mJoin.anim>0){
			DrawTransScal(gl, mGR.mTex_Join, mGR.mJoin.x, mGR.mJoin.y,(10-mGR.mJoin.anim)*.02f+1,mGR.mJoin.anim*.1f);
			mGR.mJoin.anim--;
		}
		for (int i = 0; i < mGR.mAnim.length; i++) {
			if (mGR.mAnim[i].anim > 0) {
				mGR.mTex_smoke.drawSRGB(gl, mGR.mAnim[i].x, mGR.mAnim[i].y,
						.4f + (mGR.mAnim[i].anim) * .1f,
						M.COLOR[mGR.mAnim[i].color][0] / 255f,
						M.COLOR[mGR.mAnim[i].color][1] / 255f,
						M.COLOR[mGR.mAnim[i].color][2] / 255f,
						mGR.mAnim[i].anim * .1f);
				mGR.mAnim[i].anim--;
			}
		}
		mGR.mTex_Fire[0].drawRotet2(gl, mGR.mPlayer.ang, 0,-.85f, 1, .22f);
		DrawTexture(gl, mGR.mTex_Fire[1], 0, -.70f);
		if(mGR.mPlayer.anim<9){
			DrawTexture(gl, mGR.mTex_Hit[mGR.mPlayer.anim%9], 0, -.80f);
			mGR.mPlayer.anim++;
		}
		
		if(Counter>10)
			DrawTexture(gl, mGR.mTex_Ball[mGR.mPlayer.fireColor], mGR.mPlayer.x, mGR.mPlayer.y);
		else
			DrawTransScal(gl, mGR.mTex_Ball[mGR.mPlayer.fireColor], mGR.mPlayer.x, mGR.mPlayer.y,(10-Counter)*.05f+1,Counter*.1f);
			
		for(int i=0;i<mGR.mRBall.length;i++) {
			if(mGR.mRBall[i].count>0) {
				if(mGR.mRBall[i].color>=0){
					if(mGR.mRBall[i].count > 2999){
						if(mGR.mRBall[i].color==7)
							DrawTexture(gl, mGR.mTex_Candy[mGR.mRBall[i].candy%25], mGR.mRBall[i].x, mGR.mRBall[i].y);
						else
							DrawTexture(gl, mGR.mTex_Ball[mGR.mRBall[i].color%8], mGR.mRBall[i].x, mGR.mRBall[i].y);
					} else if (mGR.mRBall[i].count > 0 && mGR.mRBall[i].count < 11) {
						 mGR.mTex_Blast[0].drawSRGB(gl, mGR.mRBall[i].x, mGR.mRBall[i].y, mGR.mRBall[i].count*.1f,M.COLOR[mGR.mRBall[i].color][0]/255f,M.COLOR[mGR.mRBall[i].color][1]/255f,M.COLOR[mGR.mRBall[i].color][2]/255f ,1);
						mGR.mTex_Blast[1].drawSRGB(gl, mGR.mRBall[i].x, mGR.mRBall[i].y,.4f+ (10-mGR.mRBall[i].count)*.1f, M.COLOR[mGR.mRBall[i].color][0]/255f,M.COLOR[mGR.mRBall[i].color][1]/255f,M.COLOR[mGR.mRBall[i].color][2]/255f ,mGR.mRBall[i].count*.1f);
						mGR.mTex_Blast[2].drawSRGB(gl, mGR.mRBall[i].x, mGR.mRBall[i].y,.4f+ (10-mGR.mRBall[i].count)*.1f, M.COLOR[mGR.mRBall[i].color][0]/255f,M.COLOR[mGR.mRBall[i].color][1]/255f,M.COLOR[mGR.mRBall[i].color][2]/255f ,mGR.mRBall[i].count*.1f);
						if(mGR.mRBall[i].count == 8)
							M.sound1(GameRenderer.mContext, R.drawable.bubble_blast3);
					}
					else if(mGR.mRBall[i].count>10){
						if(mGR.mRBall[i].color==7)
							DrawTexture(gl, mGR.mTex_Candy[mGR.mRBall[i].candy%25], mGR.mRBall[i].x, mGR.mRBall[i].y);
						else
							DrawTexture(gl, mGR.mTex_Ball[mGR.mRBall[i].color], mGR.mRBall[i].x, mGR.mRBall[i].y);
					}
				}
				mGR.mRBall[i].update();
			}
		}
		DrawTexture(gl, mGR.mTex_Topline, 0, mGR.by+.08f);
		if(dis <.2)
			DrawTexture(gl, mGR.mTex_Botline[3+Counter%2], 0, M.BY - (M.Row-1) * bdy);
		else if(dis <.4)
			DrawTexture(gl, mGR.mTex_Botline[1+Counter%2], 0, M.BY - (M.Row-1) * bdy);
		else
			DrawTexture(gl, mGR.mTex_Botline[0], 0, M.BY - (M.Row-1) * bdy);
		
		
		for (int i = 0; i < mGR.mScore.length; i++) {
			if(mGR.mScore[i].x > -1 && mGR.mScore[i].x<1 && mGR.mScore[i].y>-1&& mGR.mScore[i].y<1){
				drawScore(gl, mGR.mScore[i].score, mGR.mScore[i].x, mGR.mScore[i].y, 1);
				mGR.mScore[i].update();
			}
		}
		
		
		DrawTexture(gl, mGR.mTex_SBar, 0, .912f);
		
		
		DrawTransScal(gl,mGR.mTex_Pause	, -.88f, .94f, mGR.mSel==2?1.4f:1.2f, mGR.mSel==2?0.5f:1);
		drawNumber(gl, mGR.score, -.38f, .91f, 1);
		if(mGR.IsEndless){
			DrawTexture(gl, mGR.mTex_BScore, .44f, .965f);
			drawNumberScal(gl, mGR.mHEScore, 0.35f, .91f,0, .7f,1);
		}
		else{
			DrawTexture(gl, mGR.mTex_Ltet, .44f, .965f);
			drawNumberScal(gl, mGR.mLvl+1, .40f, .91f,0, .7f,1);
		}
		
//		DrawTexture(gl, mGR.mTex_Jar[1], mGR.movex0,-.95f);
//		DrawTexture(gl, mGR.mTex_Jar[2], mGR.movex1,-.95f);
		
		
		for(int i=0;i<mGR.mRBall.length;i++)
		{
			if(mGR.mRBall[i].water>0)
			{
				if (Counter % 2 == 0){
					mGR.mRBall[i].water--;
					if(mGR.mRBall[i].water == 10){
						M.sound13(GameRenderer.mContext, R.drawable.water1);
						for (int m = 0; m < mGR.mScore.length; m++) {
							if(mGR.mScore[m].x < -1 || mGR.mScore[m].x>1 || mGR.mScore[m].y< -1 ||  mGR.mScore[m].y>=1){
								mGR.mScore[m].set(mGR.mRBall[i].x,mGR.mRBall[i].ny, mGR.mRBall[i].score);
								break;
							}
						}
					}
				}
				DrawTexture(gl, mGR.mTex_Water[mGR.mRBall[i].water%12], mGR.mRBall[i].x,mGR.mRBall[i].ny);
				
			}
		}
		

//		if (mGR.movex0 < -mGR.mTex_Jar[1].width())
//			mGR.movex0 = mGR.movex1 + mGR.mTex_Jar[1].width();
//		if (mGR.movex1 < -mGR.mTex_Jar[1].width())
//			mGR.movex1 = mGR.movex0 + mGR.mTex_Jar[1].width();
//		mGR.movex0 -= .01f;
//		mGR.movex1 -= .01f;
		gameLogic();
		
	}
	

	boolean Handle_Gameplay(MotionEvent event) {
		mGR.mSel =0;
		if(CircRectsOverlap(-.83f,-.75f, mGR.mTex_setting.width()*.5f, mGR.mTex_setting.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 1;//Exchange
		}
		if(CircRectsOverlap(-.88f, .94f, mGR.mTex_setting.width()*.5f, mGR.mTex_setting.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f))
		{
			mGR.mSel = 2;//Pause
		}
		
		if (mGR.mSel == 0)
		{
			float dx = M.ScreenWidth / 2;
			float dy = M.ScreenHieght * .9f;
			double ang = (GetAngle(-(dy - event.getY()), -(dx - event.getX())));
			if (ang > 1.9f && ang < 4.3f && !mGR.mPlayer.move) {
				mGR.mPlayer.setAngle((float)ang);
			}
			
		}
		
		if (MotionEvent.ACTION_UP == event.getAction()) {
			if (mGR.mSel != 0) {
				switch (mGR.mSel) {
				case 1:
					int temp = mGR.mPlayer.fireColor;
					mGR.mPlayer.fireColor = mGR.mPlayer.ResColor;
					mGR.mPlayer.ResColor = temp;
					Counter = 0;
					break;
				case 2:
					M.GameScreen = M.GAMEPAUSE;
					M.stop(GameRenderer.mContext);
					break;
				}
				mGR.mSel = 0;
			} else {
				float dx = M.ScreenWidth / 2;
				float dy = M.ScreenHieght * .9f;
				double ang = (GetAngle(-(dy - event.getY()), -(dx - event.getX())));
				if (ang > 1.9f && ang < 4.3f && !mGR.mPlayer.move) {
					mGR.mPlayer.set(ang, true);
					mGR.mPlayer.anim = 0;
					M.sound8(GameRenderer.mContext, R.drawable.bubble_hit);
				}
			}
		}
		return true;
	}
	/*************************Game End**********************************************/
	void DrawTexture(GL10 gl, SimplePlane Tex, float x, float y) {
		Tex.drawPos(gl, x, y);
	}

	void DrawTextureR(GL10 gl, SimplePlane Tex, float x, float y, float angle) {
		Tex.drawRotet(gl, 0, 0, angle, x, y);
	}

	void DrawTextureS(GL10 gl, SimplePlane Tex, float x, float y, float scal) {
		Tex.drawScal(gl, x, y, scal, scal);
	}

	void DrawFlip(GL10 gl, SimplePlane Tex, float x, float y) {
		Tex.drawFilp(gl, x, y);
	}

	void DrawTransScal(GL10 gl, SimplePlane Tex, float x, float y, float z,
			float t) {
		Tex.drawTransprentScal(gl, x, y, z, t);
	}

	boolean CircRectsOverlap(double CRX, double CRY, double CRDX, double CRDY,
			double centerX, double centerY, double radius) {
		if ((Math.abs(centerX - CRX) <= (CRDX + radius))
				&& (Math.abs(centerY - CRY) <= (CRDY + radius)))
			return true;
		return false;

	}

	float screen2worldX(float a) {
		float c = ((a / M.ScreenWidth) - 0.5f) * 2;
		return c;
	}

	float screen2worldY(float a) {
		float c = ((a / M.ScreenHieght) - 0.5f) * (-2);
		return c;
	}
	
	float world2screenX(float a) {
		float c = ((a / 2) + 0.5f) * M.ScreenWidth;
		return c;
	}

	float worldscreenY(float a) {
		float c = (.5f-(a / 2)) * (M.ScreenHieght);
		return c;
	}
	

	boolean Rect2RectIntersection(float ax, float ay, float adx, float ady,
			float bx, float by, float bdx, float bdy) {
		ax -= adx / 2;
		ay += ady / 2;
		bx -= bdx / 2;
		by += bdy / 2;
		if (ax + adx > bx && ay - ady < by && bx + bdx > ax && by - bdy < ay) {
			return true;
		}
		return false;
	}

	boolean CirCir(double cx1, double cy1, double r1, double cx2, double cy2,
			double r2) {
		float bVectMag = (float) Math.sqrt(((cx1 - cx2) * (cx1 - cx2))
				+ ((cy1 - cy2) * (cy1 - cy2)));
		if (bVectMag < (r1 + r2))
			return true;
		return false;

	}

	double GetAngle(double d, double e) {
		if (d == 0)
			return e >= 0 ? Math.PI / 2 : -Math.PI / 2;
		else if (d > 0)
			return Math.atan(e / d);
		else
			return Math.atan(e / d) + Math.PI;
	}

	
	void drawScore(GL10 gl,int no,float x, float y,int align)
	{
		float dx = mGR.mTex_SFont[0].width() * .5f;
		String strs = "" + no;
		if(align ==1)
			x-=strs.length()*dx*.5f;
		for (int i = 0; i < strs.length(); i++) {
			int k = ((int) strs.charAt(i)) - 48;
			if (k >= 0 && k < mGR.mTex_SFont.length)
				mGR.mTex_SFont[k].drawPos(gl, x + i * dx, y);
		}
	}
	
	void drawNumber(GL10 gl,int no,float x, float y,int align)
	{
		float dx = mGR.mTex_Font[0].width() * .5f;
		String strs = "" + no;
		if(align ==1)
			x-=strs.length()*dx*.5f;
		for (int i = 0; i < strs.length(); i++) {
			int k = ((int) strs.charAt(i)) - 48;
			if (k >= 0 && k < mGR.mTex_Font.length)
				mGR.mTex_Font[k].drawPos(gl, x + i * dx, y);
		}
	}
	void drawNumberScal(GL10 gl,int no,float x, float y,float scal,float r,float g,float b)
	{
		float dx = mGR.mTex_Font[0].width()*scal*.5f;
		 String strs = ""+no;
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i))-48;
			if(k>=0 && k<mGR.mTex_Font.length)
				mGR.mTex_Font[k].drawSRGB(gl,x+i*dx,y,scal,r,g,b,1);
		}
	}
	void drawNumberScal(GL10 gl,int no,float x, float y,int align,float scal,float t)
	{
		float dx = mGR.mTex_Font[0].width()*scal*.5f;
		 String strs = ""+no;
		 if(align ==1)
				x-=strs.length()*dx*.5f;
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i))-48;
			if(k>=0 && k<mGR.mTex_Font.length)
				mGR.mTex_Font[k].drawSRGB(gl,x+i*dx,y,scal,t,t,t,t);
		}
	}

	void RateUs() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.LINK + getClass().getPackage().getName()));
		GameRenderer.mContext.startActivity(mIntent);
	}

	void MoreGame() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.MARKET));
		GameRenderer.mContext.startActivity(mIntent);
	}
	void facebook() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://www.facebook.com/HututuGames"));
		GameRenderer.mContext.startActivity(mIntent);
	}
	void Gplus() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://plus.google.com/+Hututugames"));
		GameRenderer.mContext.startActivity(mIntent);
	}

	void share2friend() {
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("text/plain");
		i.putExtra(Intent.EXTRA_SUBJECT,"Roking new Game '"+ GameRenderer.mContext.getResources().getString(R.string.app_name) + "'");
		i.putExtra(Intent.EXTRA_TEXT,"Let the battle commence!!! Download it now and let’s ROCK!!!!  "+ M.SHARELINK);
		try {
			GameRenderer.mContext.startActivity(Intent.createChooser(i,"Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {Toast.makeText(GameRenderer.mStart,"There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
}
