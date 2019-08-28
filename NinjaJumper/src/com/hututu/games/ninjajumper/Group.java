package com.hututu.games.ninjajumper;
import javax.microedition.khronos.opengles.GL10;
import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.widget.Toast;
 

public class Group extends Mesh 
{
	
	GameRenderer mGR = null;
	
	int Counter =0;
	float sx,sy;
	public void setting(){float ud=.01f;switch (GameRenderer.mStart._keyCode) {case 1:sy-=ud;break;case 2:sy+=ud;break;case 3:sx-=ud;break;case 4:sx+=ud;break;}System.out.println(M.GameScreen+"~~~~~~~~~~~~~~~      "+sx+"~~~~~~~~~~~~       "+sy);}
	public boolean Handle(MotionEvent event){
		if(CircRectsOverlap(-.8f,0.0f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 3;
		if(CircRectsOverlap(0.8f,0.0f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 4;
		if(CircRectsOverlap(-.0f,-.8f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 1;
		if(CircRectsOverlap(0.0f,0.8f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 2;
		if(event.getAction()== MotionEvent.ACTION_UP)GameRenderer.mStart._keyCode = 0;
		return true;
	}
	
	public Group(GameRenderer _GameRenderer){
		mGR = _GameRenderer;
	}
	
	@Override
	public void draw(GL10 gl) {
//		M.GameScreen = M.GAMEPAUSE;
		switch (M.GameScreen) {
		case M.GAMELOGO:
			DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if(Counter > 60)
				M.GameScreen = M.GAMEMENU;
			break;
		case M.GAMEMENU:
			Draw_Menu(gl);
			break;
		case M.GAMEPLAY:
			Draw_GamePlay(gl);
			break;
		case M.GAMEOVER :
			Draw_Over(gl);
			break;
		case M.GAMEPOWER:
			Draw_Power(gl);
			break;
		case M.GAMEOPTION:
			Draw_Option(gl);
			break;
		case M.GAMEHELP:
		case M.GAMEABOUT:
			Draw_HelpAbout(gl);
			break;
		case M.GAMEPAUSE:
			Draw_Pause(gl);
			break;
		}
//		setting();
		Counter++;
	}

	public boolean TouchEvent(MotionEvent event) {
		switch (M.GameScreen) {
		case M.GAMELOGO:
			break;
		case M.GAMEMENU:
			Handle_Menu(event);
			break;
		case M.GAMEPLAY:
			Handle_GamePlay(event);
			break;
		case M.GAMEOVER :
			Handle_Over(event);
			break;
		case M.GAMEPOWER:
			Handle_Power(event);
			break;
		case M.GAMEOPTION:
			Handle_Option(event);
			break;
		case M.GAMEHELP:
		case M.GAMEABOUT:
			Handle_HelpAbout(event);
			break;
		case M.GAMEPAUSE:
			Handle_Pause(event);
			break;
		}
//		Handle(event);
		return true;
	}
	
	
	/******************Pause End*******************************/
	void Draw_Pause(GL10 gl) {
		DrawTexture(gl, mGR.mTex_BG[mGR.bgNo][0], 0, 0);
		DrawTexture(gl, mGR.mTex_BG[mGR.bgNo][1], 0, -.70f);
		
		DrawTexture(gl, mGR.mTex_OverBox, 0, .62f);
		DrawTexture(gl, mGR.mTex_PauseTex, 0, .78f);
		DrawTexture(gl, mGR.mTex_ScoreTxt, -.50f,.62f);
		drawNumber(gl, ""+mGR.mScore, .25f,.63f, 5, 0);
		DrawTexture(gl, mGR.mTex_Best, -.34f, .48f);
		drawNumber(gl, ""+mGR.mHScore, .25f,.49f, 5, 0);
		
		for(int i =0;i<3;i++){
			DrawTransScal(gl, mGR.mTex_Button, -.20f, .17f-.22f*i ,mGR.mSel == i+1 ? 1.1f:1,mGR.mSel == i+1 ? .5f:1);
		}
		
		
		DrawTransScal(gl, mGR.mTex_Play		, -.20f, 0.17f-.22f*0,mGR.mSel == 1 ? 1.1f:1,mGR.mSel == 1 ? .5f:1);
		DrawTransScal(gl, mGR.mTex_PlayIcn	, 0.54f, 0.17f-.22f*0,mGR.mSel == 1 ? 1.1f:1,mGR.mSel == 1 ? .5f:1);
		
		DrawTransScal(gl, mGR.mTex_Menu		, -.20f, 0.17f-.22f*1,mGR.mSel == 2 ? 1.1f:1,mGR.mSel == 2 ? .5f:1);
		DrawTransScal(gl, mGR.mTex_MIcn		, 0.54f, 0.17f-.22f*1,mGR.mSel == 2 ? 1.1f:1,mGR.mSel == 2 ? .5f:1);
		
		DrawTransScal(gl, mGR.mTex_Power	, -.20f, 0.17f-.22f*2,mGR.mSel == 3 ? 1.1f:1,mGR.mSel == 3 ? .5f:1);
		DrawTransScal(gl, mGR.mTex_buyIcn 	, 0.54f, 0.17f-.22f*2,mGR.mSel == 3 ? 1.1f:1,mGR.mSel == 3 ? .5f:1);
		
		if(GameRenderer.mStart.isSignedIn()){
			DrawTransScal(gl, mGR.mTex_Leader, -.22f, -.5f,mGR.mSel == 4 ? 1.1f:1,mGR.mSel == 4 ? .5f:1);
			DrawTransScal(gl, mGR.mTex_Achive, 0.22f, -.5f,mGR.mSel == 5 ? 1.1f:1,mGR.mSel == 5 ? .5f:1);
		}
	}

	boolean Handle_Pause(MotionEvent event) {
		mGR.mSel = 0;
		for (int i = 0; i < 3; i++){
			if(CircRectsOverlap(-.20f, .17f-.22f*i, mGR.mTex_Button.width()*.7f, mGR.mTex_Button.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
				mGR.mSel = i+1;
			}	
		}
		if(GameRenderer.mStart.isSignedIn())
		{
			if(CircRectsOverlap(-.22f, -.5f, mGR.mTex_Leader.width()*.4f, mGR.mTex_Leader.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
				mGR.mSel = 4;
			}
			if(CircRectsOverlap(0.22f, -.5f, mGR.mTex_Leader.width()*.4f, mGR.mTex_Leader.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
				mGR.mSel = 5;
			}
		}
		
		
		if(MotionEvent.ACTION_UP == event.getAction()){
			switch (mGR.mSel) {
			case 1:
				M.GameScreen = M.GAMEPLAY;
				mGR.gameStart = false;
				M.play(GameRenderer.mContext, R.drawable.theme);
				break;
			case 2:
				M.GameScreen = M.GAMEMENU;
				break;
			case 3:
				M.GameScreen = M.GAMEPOWER;
				break;
			case 4:
				GameRenderer.mStart.onShowLeaderboardsRequested();
				break;
			case 5:
				GameRenderer.mStart.onShowAchievementsRequested();
				break;
			}
			if (mGR.mSel > 0)
				M.sound1(GameRenderer.mContext, R.drawable.button_click);
			mGR.mSel = 0;
		}
		return true;
	}
	/******************Pause End*******************************/
	
	
	/******************HelpAbout End*******************************/
	void Draw_HelpAbout(GL10 gl) {
		DrawTexture(gl, mGR.mTex_BG[mGR.bgNo][0], 0, 0);
		DrawTexture(gl, mGR.mTex_BG[mGR.bgNo][1], 0, -.70f);
		DrawTexture(gl, mGR.mTex_Board, 0, .1f);
		DrawTransScal(gl, mGR.mTex_Button	, -.17f, -.70f ,mGR.mSel == 5 ? 1.1f:1,mGR.mSel == 5 ? .5f:1);
		DrawTransScal(gl, mGR.mTex_Back		, -.17f, -.70f ,mGR.mSel == 5 ? 1.1f:1,mGR.mSel == 5 ? .5f:1);
		DrawTransScal(gl, mGR.mTex_BackIcn	, 0.53f, -.70f ,mGR.mSel == 5 ? 1.1f:1,mGR.mSel == 5 ? .5f:1);
		if(M.GameScreen == M.GAMEABOUT){
			DrawTexture(gl, mGR.mTex_AbtScr, 0, .1f);
		}else{
			DrawTexture(gl, mGR.mTex_HelpTex, 0, .1f);
		}
	}

	boolean Handle_HelpAbout(MotionEvent event) {
		mGR.mSel = 0;
		if(CircRectsOverlap(0, -.70f, mGR.mTex_Button.width()*.4f, mGR.mTex_Button.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 5;
		}
		if(MotionEvent.ACTION_UP == event.getAction()){
			switch (mGR.mSel) {
			case 5:
				M.GameScreen = M.GAMEOPTION;
				break;
			}
			if (mGR.mSel > 0)
				M.sound1(GameRenderer.mContext, R.drawable.button_click);
			mGR.mSel = 0;
		}
		return true;
	}
	/******************HelpAbout End*******************************/
	
	/******************Option End*******************************/
	void Draw_Option(GL10 gl) {
		DrawTexture(gl, mGR.mTex_BG[mGR.bgNo][0], 0, 0);
		DrawTexture(gl, mGR.mTex_BG[mGR.bgNo][1], 0, -.70f);
		DrawTexture(gl, mGR.mTex_Board, 0, 0.00f);
		for(int i =0;i<5;i++){
			DrawTransScal(gl, mGR.mTex_Button, -.17f, .47f-.23f*i ,mGR.mSel == i+1 ? 1.1f:1,mGR.mSel == i+1 ? .5f:1);
		}
		
		DrawTransScal(gl, mGR.mTex_Sound[M.setValue ? 1:0], -.17f, .47f-.23f*0 ,mGR.mSel == 1 ? 1.1f:1,mGR.mSel == 1 ? .5f:1);
		DrawTransScal(gl, mGR.mTex_SoundIcn[M.setValue ? 1:0], .53f, .47f-.23f*0,mGR.mSel == 1 ? 1.1f:1,mGR.mSel == 1 ? .5f:1);
		
		DrawTransScal(gl, mGR.mTex_Help, -.17f, .47f-.23f*1 ,mGR.mSel == 2 ? 1.1f:1,mGR.mSel == 2 ? .5f:1);
		DrawTransScal(gl, mGR.mTex_HelpIcn, .53f, .47f-.23f*1,mGR.mSel == 2 ? 1.1f:1,mGR.mSel == 2 ? .5f:1);
		
		DrawTransScal(gl, mGR.mTex_About, -.17f, .47f-.23f*2 ,mGR.mSel == 3 ? 1.1f:1,mGR.mSel == 3 ? .5f:1);
		DrawTransScal(gl, mGR.mTex_AboutIcn, .53f, .47f-.23f*2,mGR.mSel == 3 ? 1.1f:1,mGR.mSel == 3 ? .5f:1);
		
		DrawTransScal(gl, mGR.mTex_Power, -.17f, .47f-.23f*3 ,mGR.mSel == 4 ? 1.1f:1,mGR.mSel == 4 ? .5f:1);
		DrawTransScal(gl, mGR.mTex_buyIcn, .53f, .47f-.23f*3,mGR.mSel == 4 ? 1.1f:1,mGR.mSel == 4 ? .5f:1);
		
		DrawTransScal(gl, mGR.mTex_Back, -.17f, .47f-.23f*4 ,mGR.mSel == 5 ? 1.1f:1,mGR.mSel == 5 ? .5f:1);
		DrawTransScal(gl, mGR.mTex_BackIcn, .53f, .47f-.23f*4,mGR.mSel == 5 ? 1.1f:1,mGR.mSel == 5 ? .5f:1);
	}

	boolean Handle_Option(MotionEvent event) {
		mGR.mSel = 0;
		for(int i = 0;i<5;i++){
			if(CircRectsOverlap(0, .47f-.23f*i, mGR.mTex_Button.width()*.7f, mGR.mTex_Button.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
				mGR.mSel = i+1;
			}
		}
		if(MotionEvent.ACTION_UP == event.getAction()){
			switch (mGR.mSel) {
			case 1:
				M.setValue = !M.setValue;
				break;
			case 2:
				M.GameScreen = M.GAMEHELP;
				break;
			case 3:
				M.GameScreen = M.GAMEABOUT;
				break;
			case 4:
				M.GameScreen = M.GAMEPOWER;
				break;
			case 5:
				M.GameScreen = M.GAMEMENU;
				break;
			}
			if (mGR.mSel > 0)
				M.sound1(GameRenderer.mContext, R.drawable.button_click);
			mGR.mSel = 0;
		}
		return true;
	}
	/******************Option End*******************************/
	/******************Power End*******************************/
	void Draw_Power(GL10 gl) {
		DrawTexture(gl, mGR.mTex_BG[mGR.bgNo][0], 0, 0);
		DrawTexture(gl, mGR.mTex_BG[mGR.bgNo][1], 0, -.70f);
		DrawTexture(gl, mGR.mTex_Board, 0, 0.21f);
		
		DrawTexture(gl, mGR.mTex_BuyTex, 0, .68f);
		
		for(int i =1;i<5;i++){
			DrawTransScal(gl, mGR.mTex_Button, -.23f, .68f-.23f*i ,mGR.mSel == i+1 ? 1.1f:1,mGR.mSel == i+1 ? .5f:1);
			DrawTransScal(gl, mGR.mTex_BuyBtn, 0.56f, .68f-.23f*i,mGR.mSel == i+1 ? 1.1f:1,mGR.mSel == i+1 ? .5f:1);
		}
		
		drawNumber(gl,  "10= $0.99", -.18f, .68f-.23f*1 , 1,1);
		drawNumber(gl,  "25= $1.99", -.18f, .68f-.23f*2 , 2,1);
		drawNumber(gl,  "50= $2.99", -.18f, .68f-.23f*3 , 3,1);
		drawNumber(gl, "100= $3.99", -.18f, .68f-.23f*4 , 4,1);
		
		DrawTransScal(gl, mGR.mTex_Button	, -.20f, -.6f,mGR.mSel == 6 ? 1.1f:1,mGR.mSel == 6 ? .5f:1);
		DrawTransScal(gl, mGR.mTex_Back		, -.20f, -.6f,mGR.mSel == 6 ? 1.1f:1,mGR.mSel == 6 ? .5f:1);
		DrawTransScal(gl, mGR.mTex_BackIcn	,  .54f, -.6f,mGR.mSel == 6 ? 1.1f:1,mGR.mSel == 6 ? .5f:1);
		
	}

	boolean Handle_Power(MotionEvent event) {
		mGR.mSel = 0;
		for(int i = 1;i<5;i++){
			if(CircRectsOverlap(0, .68f-.23f*i, mGR.mTex_Button.width()*.7f, mGR.mTex_Button.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
				mGR.mSel = i+1;
			}
		}
		if(CircRectsOverlap(0, -.6f, mGR.mTex_Button.width()*.7f, mGR.mTex_Button.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 6;
		}
		if(MotionEvent.ACTION_UP == event.getAction()){
			switch (mGR.mSel) {
			case 2:
				mGR.mInApp.onBuyGold10000(null);//1 $
				break;
			case 3:
				mGR.mInApp.onBuyGold20000(null);//2 $
				break;
			case 4:
				mGR.mInApp.onBuyGold30000(null);//5 $
				break;
			case 5:
				mGR.mInApp.onBuyGold40000(null);//10 $
				break;
			case 6:
				M.GameScreen = M.GAMEMENU;
				break;
			}
			if (mGR.mSel > 0)
				M.sound1(GameRenderer.mContext, R.drawable.button_click);
			mGR.mSel = 0;
		}
		return true;
	}
	/******************Power End*******************************/
	
	/******************Menu End*******************************/
	void Draw_Menu(GL10 gl) {
		DrawTexture(gl, mGR.mTex_Splash, 0, 0);
		for(int i = 0;i<3;i++){
			DrawTransScal(gl, mGR.mTex_Button, -.20f, -.45f-.22f*i ,mGR.mSel == i+1 ? 1.1f:1,mGR.mSel == i+1 ? .5f:1);
		}
		DrawTransScal(gl, mGR.mTex_Play, -.20f, -.45f-.22f*0,mGR.mSel == 1 ? 1.1f:1,mGR.mSel == 1 ? .5f:1);
		DrawTransScal(gl, mGR.mTex_PlayIcn, .50f, -.45f-.22f*0,mGR.mSel == 1 ? 1.1f:1,mGR.mSel == 1 ? .5f:1);
		DrawTransScal(gl, mGR.mTex_Option, -.20f, -.45f-.22f*1,mGR.mSel == 2 ? 1.1f:1,mGR.mSel == 2 ? .5f:1);
		DrawTransScal(gl, mGR.mTex_optionIcn, .50f, -.45f-.22f*1,mGR.mSel == 2 ? 1.1f:1,mGR.mSel == 2 ? .5f:1);
		DrawTransScal(gl, mGR.mTex_Power, -.20f, -.45f-.22f*2,mGR.mSel == 3 ? 1.1f:1,mGR.mSel == 3 ? .5f:1);
		DrawTransScal(gl, mGR.mTex_buyIcn, .50f, -.45f-.22f*2,mGR.mSel == 3 ? 1.1f:1,mGR.mSel == 3 ? .5f:1);
	}

	boolean Handle_Menu(MotionEvent event) {
		mGR.mSel = 0;
		for(int i = 0;i<3;i++){
			if(CircRectsOverlap(0, -.45f-.22f*i, mGR.mTex_Button.width()*.7f, mGR.mTex_Button.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
				mGR.mSel = i+1;
			}
		}
		if(MotionEvent.ACTION_UP == event.getAction()){
			switch (mGR.mSel) {
			case 1:
				mGR.gameReset();
				M.GameScreen = M.GAMEPLAY;
				M.play(GameRenderer.mContext, R.drawable.theme);
				break;
			case 2:
				M.GameScreen = M.GAMEOPTION;
				break;
			case 3:
				M.GameScreen = M.GAMEPOWER;
				break;
			}
			if (mGR.mSel > 0)
				M.sound1(GameRenderer.mContext, R.drawable.button_click);
			mGR.mSel = 0;
		}
		return true;
	}
	/******************Menu End*******************************/
	/******************Over End*******************************/
	void Draw_Over(GL10 gl) {
		DrawTexture(gl, mGR.mTex_BG[mGR.bgNo][0], 0, 0);
		DrawTexture(gl, mGR.mTex_BG[mGR.bgNo][1], 0, -.70f);
		
		DrawTexture(gl, mGR.mTex_OverBox, 0, .62f);
		DrawTexture(gl, mGR.mTex_OverTxt, 0, .78f);
		DrawTexture(gl, mGR.mTex_ScoreTxt, -.50f,.62f);
		drawNumber(gl, ""+mGR.mScore, .25f,.63f, 5, 0);
		DrawTexture(gl, mGR.mTex_Best, -.34f, .48f);
		drawNumber(gl, ""+mGR.mHScore, .25f,.49f, 5, 0);
		
		DrawTexture(gl, mGR.mTex_JoinBox, 0, .07f);
		if(!GameRenderer.mStart.isSignedIn())
			DrawTransScal(gl, mGR.mTex_JoinTxt, 0, .07f,mGR.mSel == 5 ? 1.1f:1,mGR.mSel == 5 ? .5f:1);
		else{
			DrawTransScal(gl, mGR.mTex_Leader, -.22f, .07f,mGR.mSel == 1 ? 1.1f:1,mGR.mSel == 1 ? .5f:1);
			DrawTransScal(gl, mGR.mTex_Achive, 0.22f, .07f,mGR.mSel == 2 ? 1.1f:1,mGR.mSel == 2 ? .5f:1);
		}
		
		DrawTransScal(gl, mGR.mTex_Button, -.20f, -.28f,mGR.mSel == 3 ? 1.1f:1,mGR.mSel == 3 ? .5f:1);
		DrawTransScal(gl, mGR.mTex_Retry, -.20f, -.28f,mGR.mSel == 3 ? 1.1f:1,mGR.mSel == 3 ? .5f:1);
		DrawTransScal(gl, mGR.mTex_RetryIcn, .50f, -.28f,mGR.mSel == 3 ? 1.1f:1,mGR.mSel == 3 ? .5f:1);
		
		DrawTransScal(gl, mGR.mTex_Button, -.20f, -.49f,mGR.mSel == 4 ? 1.1f:1,mGR.mSel == 4 ? .5f:1);
		DrawTransScal(gl, mGR.mTex_Power, -.20f, -.49f,mGR.mSel == 4 ? 1.1f:1,mGR.mSel == 4 ? .5f:1);
		DrawTransScal(gl, mGR.mTex_buyIcn, .50f, -.49f,mGR.mSel == 4 ? 1.1f:1,mGR.mSel == 4 ? .5f:1);
		
		for (int i = 0; i < 4; i++)
			DrawTransScal(gl, mGR.mTex_Icn[i], -.62f + .42f * i, -.70f,mGR.mSel == i+10 ? 1.1f:1,mGR.mSel == i+10 ? .5f:1);
	}

	boolean Handle_Over(MotionEvent event) {
		mGR.mSel = 0;
		if(!GameRenderer.mStart.isSignedIn()){
		if(CircRectsOverlap(0, .07f, mGR.mTex_JoinTxt.width()*.4f, mGR.mTex_JoinTxt.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 5;
		}}else{
		if(CircRectsOverlap(-.22f, .07f, mGR.mTex_Leader.width()*.4f, mGR.mTex_Leader.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 1;
		}
		if(CircRectsOverlap(0.22f, .07f, mGR.mTex_Leader.width()*.4f, mGR.mTex_Leader.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 2;
		}}
		
		if(CircRectsOverlap(0, -.28f, mGR.mTex_Button.width()*.7f, mGR.mTex_Button.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 3;
		}
		if(CircRectsOverlap(0, -.49f, mGR.mTex_Button.width()*.7f, mGR.mTex_Button.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 4;
		}
		
		for (int i = 0; i < 4; i++){
			if(CircRectsOverlap(-.62f + .42f * i, -.70f, mGR.mTex_Icn[i].width()*.45f, mGR.mTex_Icn[i].Height()*.45f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
				mGR.mSel = 10+i;
			}	
		}
		if(MotionEvent.ACTION_UP == event.getAction()){
			switch (mGR.mSel) {
			case 1:
				GameRenderer.mStart.onShowLeaderboardsRequested();
				break;
			case 2:
				GameRenderer.mStart.onShowAchievementsRequested();
				break;
			case 3:
				mGR.gameReset();
				M.GameScreen = M.GAMEPLAY;
				M.play(GameRenderer.mContext, R.drawable.theme);
				break;
			case 4:
				M.GameScreen = M.GAMEPOWER;
				break;
			case 5:
				GameRenderer.mStart.beginUserInitiatedSignIn();
				break;
			case 10:
				MoreGame();
				break;
			case 11:
				Google();
				break;
			case 12:
				facebook();
				break;
			case 13:
				Twitter();
				break;
			}
			if (mGR.mSel > 0)
				M.sound1(GameRenderer.mContext, R.drawable.button_click);
			mGR.mSel = 0;
		}
		return true;
	}
	/******************Over End*******************************/
	/******************Game play Start*****************************/
	void gameOver(float x,float y) {
		// M.GameScreen = M.GAMEOVER;
		for (int i = 0; i < mGR.mRed.length; i++)
			mGR.mRed[i].set(x,y);
		if (mGR.OverCounter < 1)
			mGR.OverCounter = 1;
		switch (M.mRand.nextInt(3)) {
		case 1:
			M.sound4(GameRenderer.mContext, R.drawable.ninja_hit1);
			break;
		case 2:
			M.sound5(GameRenderer.mContext, R.drawable.ninja_hit2);
			break;
		default:
			M.sound6(GameRenderer.mContext, R.drawable.ninja_hit0);
			break;
		}
	}
	void reset(int j)
	{
		int no = 1;
		if (mGR.mScore < 5)
			no = M.mRand.nextBoolean() ? 1 : 2;
		if (mGR.mScore >= 5 && mGR.mScore < 12)
			no = 2;
		if (mGR.mScore >= 12 && mGR.mScore < 20)
			no = M.mRand.nextBoolean() ? 2 : 3;
		if (mGR.mScore >= 20)
			no = 3;
		
		byte random[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		for (int i = 0; i < random.length && i < no; i++) {
			random[i] = 1;
		}
		for(int i =0;i<50;i++){
			int k = M.mRand.nextInt(random.length);
			byte temp = random[k];
			random[k] = random[i%random.length];
			random[i%random.length] = temp;
		}
		
		for (int i = 0; i < random.length ; i++) {
			if(random[i] == 1){
				mGR.mSpike[j][i].Reset(true);
			}else{
				mGR.mSpike[j][i].Reset(false);
			}
//			System.out.print(","+random[i]);
		}
//		System.out.println("```"+no+"=== ");
	}
	void Achivment(){
		if(mGR.mScore > 49 && !mGR.mAchiUnlock[0]){
			mGR.mAchiUnlock[0] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[0]);
		}
		if(mGR.mScore > 99 && !mGR.mAchiUnlock[1]){
			mGR.mAchiUnlock[1] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[1]);
		}
		if(mGR.mScore > 199 && !mGR.mAchiUnlock[2]){
			mGR.mAchiUnlock[2] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[2]);
		}
		if(mGR.mTotal > 999 && !mGR.mAchiUnlock[3]){
			mGR.mAchiUnlock[3] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[3]);
		}
		if(mGR.mTotal > 4999 && !mGR.mAchiUnlock[4]){
			mGR.mAchiUnlock[4] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[4]);
		}
	}
	int ads = 0;
	void gameLogic() {
		if (mGR.mPlayer.update()) {
			mGR.mScore++;
			mGR.mTotal++;
			Achivment();
			if(mGR.mScore>mGR.mHScore){
				mGR.mHScore = mGR.mScore;
				GameRenderer.mStart.Submitscore(R.string.leaderboard_best_score, mGR.mHScore);
				if(mGR.NewHigh.powerNo == 0)
					mGR.NewHigh.powerNo = 100;
			}
			if (mGR.mPlayer.vx > 0) {
				reset(1);
			} else {
				reset(0);
			}
			for (int i = 0; i < mGR.mSpike[0].length; i++) {
				if (mGR.mPlayer.vx > 0) {
					mGR.mSpike[0][i].Reset(false);
				} else {
					mGR.mSpike[1][i].Reset(false);
				}
			}
			M.impact(GameRenderer.mContext);
		}
		for (int i = 0; i < mGR.mSpike[0].length; i++) {
			mGR.mSpike[0][i].update();
			mGR.mSpike[1][i].update();
		}
		
		if(mGR.OverCounter > 50){
			M.GameScreen = M.GAMEOVER;
			M.stop(GameRenderer.mContext);
			if(ads%2 == 0)
				GameRenderer.mStart.ShowInterstitial();
			ads++;
		}
		if (mGR.OverCounter > 0){
			mGR.OverCounter++;
			if(mGR.OverCounter == 3)
				M.sound3(GameRenderer.mContext, R.drawable.ninja_fall0);
			if(mGR.OverCounter == 30)
				M.sound2(GameRenderer.mContext, R.drawable.gameover);
		}else{
			for (int i = 0; i < 9; i++) {
				if(CirCir(-.88f + .22f * i, .95f,mGR.mTex_BG[mGR.bgNo][2].width()*.2f, mGR.mPlayer.x, mGR.mPlayer.y, mGR.mTex_Player.width()*.2f)){
					if(mGR.mPlayer.power == 2){
						mGR.mPlayer.y = .7f;
						mGR.mPlayer.vy =0.00f;
						mGR.mPlayer.power = 0;
					}
					else
						gameOver(mGR.mPlayer.x, .95f);
					
				}
				if(CirCir(-.88f + .22f * i, -.56f,mGR.mTex_BG[mGR.bgNo][2].width()*.4f, mGR.mPlayer.x, mGR.mPlayer.y, mGR.mTex_Player.width()*.4f)
						){
					if(mGR.mPlayer.power == 2){
							mGR.mPlayer.y = -.2f;
							mGR.mPlayer.vy =0.01f;
							mGR.mPlayer.power = 0;
						}
					else
						gameOver(mGR.mPlayer.x, -.56f);
//						gameOver(-.88f + .22f * i, -.56f);
				}
			}
			for (int i = 0; i < mGR.mSpike[0].length; i++) {
				if(CircRectsOverlap(-mGR.mSpike[0][i].x, -.28f + .12f * i,mGR.mTex_BG[mGR.bgNo][2].width()*.4f,mGR.mTex_BG[mGR.bgNo][2].Height()*.3f, 
						mGR.mPlayer.x, mGR.mPlayer.y, mGR.mTex_Player.width()*.2f)
						&& mGR.mSpike[0][i].rShow ){
					if(mGR.mPlayer.power == 2){
						mGR.mPlayer.x = -.7f;
						mGR.mPlayer.vx = -mGR.mPlayer.vx;
						mGR.mPlayer.power = 0;
					}
					else
						gameOver(-mGR.mSpike[0][i].x, mGR.mPlayer.y);
				}
				if(CircRectsOverlap( mGR.mSpike[1][i].x, -.28f + .12f * i,mGR.mTex_BG[mGR.bgNo][2].width()*.4f,mGR.mTex_BG[mGR.bgNo][2].Height()*.3f,
						mGR.mPlayer.x, mGR.mPlayer.y, mGR.mTex_Player.width()*.2f)
						&& mGR.mSpike[1][i].rShow){
					if(mGR.mPlayer.power == 2){
						mGR.mPlayer.x = .7f;
						mGR.mPlayer.vx = -mGR.mPlayer.vx; 
						mGR.mPlayer.power = 0;
					}
					else
						gameOver(mGR.mSpike[1][i].x,mGR.mPlayer.y);
				}
			}
		}
		
		if(CirCir(mGR.mPower.x, mGR.mPower.y,mGR.mTex_SPower.width()*.2f, mGR.mPlayer.x, mGR.mPlayer.y, mGR.mTex_Player.width()*.4f)
				&& mGR.mPower.powerNo>0){
			if(mGR.mPower.powerNo==1){
				mGR.mPlayer.powerSet();
				M.sound7(GameRenderer.mContext, R.drawable.slowpower_gain);
			}else{
				M.sound8(GameRenderer.mContext, R.drawable.shield_gain);
			}
			mGR.mPlayer.power = mGR.mPower.powerNo;
			mGR.mPower.powerNo = 0;
		}
		
		if(Counter%500 == 0 && mGR.mPlayer.power ==0){
			mGR.mPower.set(M.mRand.nextInt(2)+1);
		}
		if(Counter%500 == 100 && mGR.mPlayer.power ==0){
			mGR.mPower.powerNo =0;
		}
		if(Counter%500 == 300 && mGR.mPlayer.power ==1){
			mGR.mPlayer.power =0;
		}
	}

	void Draw_GamePlay(GL10 gl) {
		DrawTexture(gl, mGR.mTex_BG[mGR.bgNo][0], 0, 0);
		for (int i = 0; i < 9; i++) {
			DrawTexture(gl, mGR.mTex_BG[mGR.bgNo][2], -.88f + .22f * i, .95f);
			DrawTexturR(gl, mGR.mTex_BG[mGR.bgNo][2], -.88f + .22f * i, -.56f, 180);
		}
		for (int i = 0; i < mGR.mSpike[0].length; i++) {
			DrawTexture(gl, mGR.mTex_BG[mGR.bgNo][3], -mGR.mSpike[0][i].x, -.28f + .12f * i);
			DrawTexturR(gl, mGR.mTex_BG[mGR.bgNo][3], mGR.mSpike[1][i].x, -.28f + .12f * i, 180);
		}
		if(mGR.mPlayer.power == 2)
			mGR.mTex_Sheald.drawRotet2(gl, mGR.mPlayer.x, mGR.mPlayer.y, Counter*10);
		
		
		for(int i=0;i<mGR.mPlayer.x1.length && mGR.gameStart && mGR.mPlayer.power ==1 ;i++)
			DrawTransScal(gl, mGR.mTex_Player, mGR.mPlayer.x1[i], mGR.mPlayer.y1[i],mGR.mPlayer.z1[i],mGR.mPlayer.z1[i]);
		DrawTexture(gl, mGR.mTex_Player, mGR.mPlayer.x, mGR.mPlayer.y);
		DrawTexture(gl, mGR.mTex_BG[mGR.bgNo][1], 0, -.70f);
		DrawTexture(gl, mGR.mTex_Shiled, -.64f, -.70f);
		drawNumber(gl, mGR.NoPower+"", -.51f, -.70f, 1, 1);
//		DrawTexture(gl, mGR.mTex_PoseIcn, 0, -.70f);
		DrawTexture(gl, mGR.mTex_ScorBar, .71f, -.70f);
		drawNumberS(gl, mGR.mScore+"", 0.73f, -.73f, 5, 1);
		
		if(mGR.mPower.powerNo == 1 )
			mGR.mTex_SPower.drawRotet2(gl,mGR.mPower.x, mGR.mPower.y, Counter*10);
		if(mGR.mPower.powerNo == 2 )
			mGR.mTex_Sheald.drawRotet2(gl,mGR.mPower.x, mGR.mPower.y, Counter*10);
		
		if (mGR.OverCounter > 0){
			for (int i = 0; i < mGR.mRed.length; i++){
				if(mGR.mRed[i].x > -1 && mGR.mRed[i].x < 1 && mGR.mRed[i].y > -1 && mGR.mRed[i].y <1){
					mGR.mTex_Red.drawColor(gl, mGR.mRed[i].x, mGR.mRed[i].y,1, 255, 0, 0);
					mGR.mRed[i].update();
				}
			}
			mGR.mTex_Splash.drawDark(gl, 0, 0, mGR.OverCounter*.025f);
		}
		if(mGR.NewHigh.powerNo > 2){
			DrawTexture(gl, mGR.mTex_NScr, 0, mGR.NewHigh.y);
			mGR.NewHigh.y+=mGR.NewHigh.x;
			mGR.NewHigh.x+=.0005f;
//			System.out.println(mGR.NewHigh.x+"   "+mGR.NewHigh.y+"   "+mGR.NewHigh.powerNo);
			mGR.NewHigh.powerNo --;
		}
		if (mGR.gameStart)
			gameLogic();
	}

	boolean Handle_GamePlay(MotionEvent event) {
		if (MotionEvent.ACTION_DOWN == event.getAction()) {
			if (!mGR.gameStart)
				mGR.gameStart = true;
			if (mGR.OverCounter < 1) {
				mGR.mPlayer.vy = .016f;
				M.Tap(GameRenderer.mContext);
				if (CircRectsOverlap(-.64f, -.70f,
						mGR.mTex_Shiled.width() * .25f,
						mGR.mTex_Shiled.Height() * .4f,
						screen2worldX(event.getX()),
						screen2worldY(event.getY()), .02f)
						&& mGR.mPlayer.power != 2 && mGR.NoPower > 0) {
					mGR.mPlayer.power = 2;
					mGR.NoPower--;
				}
			}
		}
		return true;
	}
	/******************Game play End*******************************/
	
	
	
	
	
	void DrawTexture(GL10 gl,SimplePlane Tex,float x,float y)
	{
		Tex.drawPos(gl, x, y);
	}
	
	void DrawTexturR(GL10 gl,SimplePlane Tex,float x,float y,float angle)
	{
		Tex.drawRotet(gl, x, y,angle);
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
	double GetAngle(double d,double e)
	{
	  if(d==0)
		  return e>=0 ? Math.PI/2 : -Math.PI/2;
	  else if (d > 0)
		  return Math.atan(e/d);
	  else
		  return Math.atan(e/d) + Math.PI;
	}

	void drawNumber(GL10 gl, String strs, float x, float y,int color,int aling) {
		float dx = mGR.mTex_Font[0].width() * .6f;
		
		if(aling == 1)
			x -=strs.length()*.5f*dx;
		for (int i = 0; i < strs.length(); i++) {
			int k = ((int) strs.charAt(i));
			if (k == 36)
				k -= 26;
			else if (k == 61) {
				k -= 50;
			}else if (k == 46) {
				k -= 34;
			} else {
				k -= 48;
			}
			if (k >= 0 && k < mGR.mTex_Font.length){
				mGR.mTex_Font[k].drawColor(gl, .01f + x + i * dx, -.01f + y,1, 64, 57, 38);
				switch (color) {
				case 0:
					mGR.mTex_Font[k].drawColor(gl, x + i * dx, y,1, 252, 178, 255);
					break;
				case 1:
					mGR.mTex_Font[k].drawColor(gl, x + i * dx, y, 1,240, 255, 72);
					break;
				case 2:
					mGR.mTex_Font[k].drawColor(gl, x + i * dx, y,1, 215, 129, 255);
					break;
				case 3:
					mGR.mTex_Font[k].drawColor(gl, x + i * dx, y,1, 231, 160, 23);
					break;
				case 4:
					mGR.mTex_Font[k].drawColor(gl, x + i * dx, y,1, 72, 249, 255);
					break;
				case 5:
					mGR.mTex_Font[k].drawColor(gl, x + i * dx, y,1, 172, 226, 66);
					break;
				}
			}
		}
	}
	
	void drawNumberS(GL10 gl, String strs, float x, float y,int color,int aling) {
		float dx = mGR.mTex_Font[0].width() * .7f * .75f;
		if(aling == 1)
			x -=strs.length()*.5f*dx;
		for (int i = 0; i < strs.length(); i++) {
			int k = ((int) strs.charAt(i));
			if (k == 36)
				k -= 26;
			else if (k == 61) {
				k -= 50;
			}else if (k == 46) {
				k -= 34;
			} else {
				k -= 48;
			}
			if (k >= 0 && k < mGR.mTex_Font.length){
				mGR.mTex_Font[k].drawColor(gl, x + i * dx, y,.75f, 72, 249, 255);
			}
		}
	}
	
	void RateUs()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.LINK+getClass().getPackage().getName()));
		GameRenderer.mContext.startActivity(mIntent);
	}
	void MoreGame()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.MARKET));
		GameRenderer.mContext.startActivity(mIntent);
	}
	void Google()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://plus.google.com/+Hututugames"));
		GameRenderer.mContext.startActivity(mIntent);
	}
	void facebook()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://www.facebook.com/HututuGames"));
		GameRenderer.mContext.startActivity(mIntent);
	}
	void Twitter()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://twitter.com/hututu_games"));
		GameRenderer.mContext.startActivity(mIntent);
	}
	void share2friend()
	{
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("text/plain");
		i.putExtra(Intent.EXTRA_SUBJECT,"Roking new Game '"+GameRenderer.mContext.getResources().getString(R.string.app_name)+"'");
		//i.putExtra(Intent.EXTRA_TEXT,"Let the battle commence!!! Download it now and let’s ROCK!!!!  "+M.PUBLICLINK+getClass().getPackage().getName());
		i.putExtra(Intent.EXTRA_TEXT,"Let the battle commence!!! Download it now and let’s ROCK!!!!  "+M.SHARELINK);
		try {
			GameRenderer.mContext.startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(GameRenderer.mStart, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
}
