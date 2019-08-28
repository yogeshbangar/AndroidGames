package com.hututu.game.clashofbattle;
import javax.microedition.khronos.opengles.GL10;
import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.widget.Toast;

public class Group extends Mesh {

	GameRenderer mGR = null;
	int mSel = 0;
	int Counter = 0;
	float sx, sy;
	float fade =0;
	public void setting() {float ud = .01f;switch (GameRenderer.mStart._keyCode) {case 1:sy -= ud;break;case 2:sy += ud;break;case 3:sx -= ud;break;case 4:sx += ud;break;}System.out.println(M.GameScreen + "  ~~  " + sx + "  ~~  " + sy);}

	public boolean Handle(MotionEvent event) {
		if (CircRectsOverlap(-.8f, 0.0f, .1f, .1f, screen2worldX(event.getX()),screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 3;
		if (CircRectsOverlap(0.8f, 0.0f, .1f, .1f, screen2worldX(event.getX()),screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 4;
		if (CircRectsOverlap(-.0f, -.8f, .1f, .1f, screen2worldX(event.getX()),screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 1;
		if (CircRectsOverlap(0.0f, 0.8f, .1f, .1f, screen2worldX(event.getX()),screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 2;
		if (event.getAction() == MotionEvent.ACTION_UP)GameRenderer.mStart._keyCode = 0;
		return true;
	}

	public Group(GameRenderer _GameRenderer) {
		mGR = _GameRenderer;
	}

	@Override
	public void draw(GL10 gl) {
		
		switch (M.GameScreen) {
		case M.GAMELOGO:
			DrawTexture(gl, mGR.mTex_Hututu, 0, 0);
			if(Counter >= 100){
				M.GameScreen = M.GAMEMENU;
				fade = 1;
				M.play(R.drawable.splash);
			}
			break;
		case M.GAMEMENU:
			Draw_Menu(gl);
			break;
		case M.GAMEPLAY:
			Draw_GamePlay(gl);
			break;
		case M.GAMESHOP:
			Draw_Shop(gl);
			break;
		case M.GAMEABUT:
			Draw_About(gl);
			break;
		case M.GAMEOPT:
			Draw_Option(gl);
			break;
		case M.GAMEOVER:
		case M.GAMECONG:
			Draw_Gameover(gl);
			break;
		case M.GAMEBUY:
			Draw_Buy(gl);
			break;
		case M.GAMEPAUS:
			Draw_Pause(gl);
			break;
		}
		if(fade > 0){
			mGR.mTex_Sky.drawRGBTS(gl, 0, 0, .1f,.1f,.1f,fade, 1.9f, 1.9f);
			fade-=.05f;
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
			Handle_Gameplay(event);
			break;
		case M.GAMESHOP:
			Handle_Shop(event);
			break;
		case M.GAMEABUT:
			Handle_About(event);
			break;
		case M.GAMEOPT:
			Handle_Option(event);
			break;
		case M.GAMEOVER:
		case M.GAMECONG:
			Handle_Gameover(event);
			break;
		case M.GAMEBUY:
			Handle_Buy(event);
			break;
		case M.GAMEPAUS:
			Handle_Pause(event);
			break;
		}
		Handle(event);
		return true;
	}
	
	/*******************Pause Start***********************************/
	void Draw_Pause(GL10 gl){
		DrawBG(gl);
		
		DrawTexture(gl, mGR.mTex_Pause	, 0.00f, 0.82f);
		
		DrawTransScal(gl, mGR.mTex_Resume	, 0.00f, 0.22f,mSel==8?1.1f:1,mSel==8?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Again	, 0.00f, -.22f,mSel==6?1.1f:1,mSel==6?0.5f:1);

		DrawTransScal(gl, mGR.mTex_Icon[M.setValue?1:0], 0.90f,0.82f,mSel==1?1.1f:1,mSel==1?0.5f:1);// Sound
		DrawTransScal(gl, mGR.mTex_Icon[M.setMusic?5:4], 0.70f,0.82f,mSel==2?1.1f:1,mSel==2?0.5f:1);// Music
		
		
		DrawTransScal(gl, mGR.mTex_Icon[12]	,-.50f,-.82f,mSel==3?1.1f:1,mSel==3?0.5f:1);// Achievement
		DrawTransScal(gl, mGR.mTex_Icon[8]	,-.70f,-.82f,mSel==4?1.1f:1,mSel==4?0.5f:1);// Leader-Board
		DrawTransScal(gl, mGR.mTex_Icon[6]	,-.90f,-.82f,mSel==5?1.1f:1,mSel==5?0.5f:1);// MORE
//		DrawTransScal(gl, mGR.mTex_Icon[3]  ,0.80f,-.82f,mSel==6?1.1f:1,mSel==6?0.5f:1);// Play
		DrawTransScal(gl, mGR.mTex_Icon[2]	,-.30f,-.82f,mSel==7?1.1f:1,mSel==7?0.5f:1);// share
		
	}
	boolean Handle_Pause(MotionEvent event){
		mSel = 0;
		if(CircRectsOverlap(0.90f,0.82f,  mGR.mTex_Icon[11].width()*.2f,  mGR.mTex_Icon[11].Height()*.5f, screen2worldX(event.getX()),screen2worldY(event.getY()), .05f)){
			mSel = 1;//sound
		}
		if(CircRectsOverlap(0.70f,0.82f,  mGR.mTex_Icon[11].width()*.2f,  mGR.mTex_Icon[11].Height()*.5f, screen2worldX(event.getX()),screen2worldY(event.getY()), .05f)){
			mSel = 2;//music
		}
		if(CircRectsOverlap(-.50f,-.82f,  mGR.mTex_Icon[11].width()*.2f,  mGR.mTex_Icon[11].Height()*.5f, screen2worldX(event.getX()),screen2worldY(event.getY()), .05f)){
			mSel = 3;//More
		}
		if(CircRectsOverlap(-.70f,-.82f,  mGR.mTex_Icon[11].width()*.2f,  mGR.mTex_Icon[11].Height()*.5f, screen2worldX(event.getX()),screen2worldY(event.getY()), .05f)){
			mSel = 4;//leader board
		}
		if(CircRectsOverlap(-.90f,-.82f,  mGR.mTex_Icon[11].width()*.2f,  mGR.mTex_Icon[11].Height()*.5f, screen2worldX(event.getX()),screen2worldY(event.getY()), .05f)){
			mSel = 5;//achievement
		}
		if(CircRectsOverlap(0.00f, -.22f,  mGR.mTex_Icon[11].width()*.5f,  mGR.mTex_Icon[11].Height()*.5f, screen2worldX(event.getX()),screen2worldY(event.getY()), .05f)){
			mSel = 6;//play
		}
		if(CircRectsOverlap(-.30f,-.82f,  mGR.mTex_Icon[11].width()*.2f,  mGR.mTex_Icon[11].Height()*.5f, screen2worldX(event.getX()),screen2worldY(event.getY()), .05f)){
			mSel = 7;//share
		}
		if(CircRectsOverlap( 0.00f, 0.22f,  mGR.mTex_Icon[11].width()*.2f,  mGR.mTex_Icon[11].Height()*.5f, screen2worldX(event.getX()),screen2worldY(event.getY()), .05f)){
			mSel = 8;//Continue
		}
		if(MotionEvent.ACTION_UP == event.getAction()&& mSel > 0){
			switch (mSel) {
			case 1:
				M.setValue =!M.setValue;
				break;

			case 2:
				M.setMusic = !M.setMusic;
				break;
			case 3:
				GameRenderer.mStart.onShowAchievementsRequested();//Achievement
				break;
			case 4:
				GameRenderer.mStart.onShowLeaderboardsRequested();//leader board
				break;
			case 5:
				MoreGame();
				break;
			case 6:
				M.GameScreen = M.GAMESHOP;
				fade = 1;
				break;
			case 7:
				share2friend();
				break;
			case 8:
				M.GameScreen = M.GAMEPLAY;
				M.play(R.drawable.gameplay_theme);
				fade = 1;
				break;
			}
			M.sound10(R.drawable.click);
			mSel =0;
		}
		return true;
	}
	/*******************Pause End***********************************/
	
	/*******************Buy Start*********************************/
	void Draw_Buy(GL10 gl){
		DrawBG(gl);
		mGR.mTex_Sky.drawRGBTS(gl, 0, 0, .1f,.1f,.1f,.5f, 1.3f, 1.3f);
		DrawTexture(gl, mGR.mTex_BuyW, 0, .82f);
		for(int i=0;i<4;i++){
			drawNumber(gl, ":"+(i+1),-.55f, .51f-.34f*i, 1.5f, 4, 0);
			DrawTransScal(gl, mGR.mTex_BuyBtn, .47f, .51f-.34f*i,mSel==(i+1)?1.1f:1,mSel==(i+1)?0.5f:1);
			DrawTexture(gl, mGR.mTex_Ads, .55f, .51f-.34f*i);
			DrawTextureS(gl, mGR.mTex_Coin[Counter % 10], .06f, .51f-.34f*i, 1.4f);// Play
		}
		
		drawNumber(gl, "100000",-.19f, .51f-.34f*0, 1.5f, 3, 0);
		drawNumber(gl, "300000",-.19f, .51f-.34f*1, 1.5f, 3, 0);
		drawNumber(gl, "700000",-.19f, .51f-.34f*2, 1.5f, 3, 0);
		drawNumber(gl,"1500000",-.23f, .51f-.34f*3, 1.5f, 3, 0);
		
		DrawTransScal(gl, mGR.mTex_Icon[11], -.88f,-.86f,mSel==16?1.1f:1,mSel==16?0.5f:1);// Back
		
	}
	boolean Handle_Buy(MotionEvent event){
		mSel = 0;
		for(int i=0;i<4;i++){
			if(CircRectsOverlap(.47f, .51f-.34f*i,  mGR.mTex_Icon[11].width()*.5f,  mGR.mTex_Icon[11].Height()*.3f, screen2worldX(event.getX()),screen2worldY(event.getY()), .05f)){
				mSel = i+1;
			}
		}
		if(CircRectsOverlap(-.88f,-.86f,  mGR.mTex_Icon[11].width()*.5f,  mGR.mTex_Icon[11].Height()*.5f, screen2worldX(event.getX()),screen2worldY(event.getY()), .05f)){
			mSel = 16;
		}
		if(MotionEvent.ACTION_UP == event.getAction()&&mSel > 0){
			switch (mSel) {
			case 1:
				mGR.mInApp.onBuyGold10000(null);
				break;
			case 2:
				mGR.mInApp.onBuyGold20000(null);
				break;
			case 3:
				mGR.mInApp.onBuyGold30000(null);
				break;
			case 4:
				mGR.mInApp.onBuyGold40000(null);
				break;
			case 16:
				fade = 1;
				M.GameScreen = M.GAMESHOP;
				break;
			}
			
			M.sound10(R.drawable.click);
			mSel =0;
		}
		return true;
	}
	/*******************Buy End*********************************/
	/*******************Gameover Start*********************************/
	void DrawBG(GL10 gl){
		mGR.spd = -.01f;
		for (int i = 0; i < mGR.mTop.length; i++) {
			mGR.mTop[i].update(mGR.spd);
			mGR.mCenter[i].update(mGR.spd * .3f);
			mGR.mDown[i].update(mGR.spd * .1f);
		}
		for (int i = 0; i < mGR.mTop.length; i++) {
			if (mGR.mTop[i].x < -2.5f) {
				mGR.mTop[i].set(mGR.mTop[i == 0 ? 1 : 0].x + mGR.mTex_Road[0].width(),mGR.mTop[i].y);
				if (i == 0) {
					mGR.topCount--;
					if (mGR.topCount <= 0)
						mGR.topCount = (byte) M.mRand.nextInt(8);
				}
			}
			if (mGR.mCenter[i].x < -2.5f)
				mGR.mCenter[i].set(mGR.mCenter[i == 0 ? 1 : 0].x+ mGR.mTex_Road[0].width(), mGR.mCenter[i].y);
			if (mGR.mDown[i].x < -2.5f)
				mGR.mDown[i].set(mGR.mDown[i == 0 ? 1 : 0].x + mGR.mTex_Road[0].width(),mGR.mDown[i].y);
		}
		DrawTextureS(gl, mGR.mTex_Sky, 0, 0, 1.9f);
		for (int i = 0; i < mGR.mDown.length; i++) {
			DrawTexture(gl, mGR.mTex_Road[2], mGR.mDown[i].x, mGR.mDown[i].y);
		}
		for (int i = 0; i < mGR.mCenter.length; i++) {
			DrawTexture(gl, mGR.mTex_Road[1], mGR.mCenter[i].x,mGR.mCenter[i].y);
		}
		for (int i = 0; i < mGR.mTop.length; i++) {
			DrawTexture(gl, mGR.mTex_Road[0], mGR.mTop[i].x, mGR.mTop[i].y);
		}
		if (mGR.topCount == 2) {
			DrawTexture(gl, mGR.mTex_Road[3], mGR.mTop[0].x, mGR.mTop[0].y-.11f);
		}
		DrawTexture(gl, mGR.mTex_Meter,0,-.74f);
		
		mGR.mTex_Sky.drawRGBTS(gl, 0, 0, .2f,.2f,.2f,.5f, 1.9f, 1.9f);
		mGR.mTex_Sky.drawRGBTS(gl, 0, 0, .4f,.5f,.5f,.6f, 1.3f, 1.3f);
	}
	void Draw_Gameover(GL10 gl){
		DrawBG(gl);
		DrawTexture(gl, mGR.mTex_Result	, 0.00f, 0.60f);

		DrawTexture(gl, mGR.mTex_Dist	, -.57f, 0.35f);
		DrawTexture(gl, mGR.mTex_Coln	, -.39f, 0.35f);
		
		DrawTexture(gl, mGR.mTex_miles	, -.08f, .46f);
		drawNumber(gl, ""+ (int)(mGR.distance*100), 0,.46f, 1.2f, 3,0);
		mGR.mTex_Fill.drawRGBTS(gl, 0.045f, .35f,0,0,0,1,21,4.5f);
		mGR.mTex_Fill.drawSS(gl, -.33f, .35f, mGR.distance, 4.5f);// 20z
		DrawTexture(gl,mGR.mTex_Bar	, -.33f+.37f, .35f); 
		drawNumber(gl, ":"+ (int)(mGR.distance*100), 0.47f,0.35f, 1.3f, 3,0);
		
		DrawTexture(gl, mGR.mTex_Kill	, -.61f, 0.12f);
		DrawTexture(gl, mGR.mTex_Coln	, -.48f, 0.12f);
		drawNumber(gl, ""+ (mGR.kill)	, 0.00f, 0.12f, 1.3f, 3,0);
		drawNumber(gl, ":"+mGR.killVal	, 0.47f, 0.12f, 1.3f, 3,0);
		
		
		DrawTexture(gl, mGR.mTex_Wall	, -.61f, -.11f);
		DrawTexture(gl, mGR.mTex_Coln	, -.48f, -.11f);
		drawNumber(gl, ""+ (mGR.Wall)	, 0.00f, -.11f, 1.3f, 3,0);
		drawNumber(gl, ":"+mGR.Wall*150	, 0.47f, -.11f, 1.3f, 3,0);
		
		if (mGR.Rewrd > mGR.tcalcu) {
//			M.sound17(R.drawable.upgrade);
			mGR.tcalcu += countmeter;
			if (mGR.Rewrd< mGR.tcalcu) {
				mGR.tcalcu = mGR.Rewrd;
			}
		}
		DrawTexture(gl, mGR.mTex_Reward	, 0.00f, -.34f);
		DrawTexture(gl, mGR.mTex_Coln	, 0.16f, -.34f);
		drawNumber(gl, ":"+(mGR.tcalcu)	, 0.40f, -.34f, 2.0f, 3,0);
		
		
		
		DrawTexture(gl, mGR.mTex_Total	, 0.00f, -.57f);
		DrawTexture(gl, mGR.mTex_Coln	, 0.12f, -.57f);
		drawNumber(gl, ":"+(mGR.mCoin)	, 0.40f, -.57f, 2.0f, 3,0);
		
		

		DrawTransScal(gl, mGR.mTex_Icon[M.setValue?1:0], 0.90f,0.82f,mSel==1?1.1f:1,mSel==1?0.5f:1);// Sound
		DrawTransScal(gl, mGR.mTex_Icon[M.setMusic?5:4], 0.70f,0.82f,mSel==2?1.1f:1,mSel==2?0.5f:1);// Music
		
		
		DrawTransScal(gl, mGR.mTex_Icon[12]	,-.50f,-.82f,mSel==3?1.1f:1,mSel==3?0.5f:1);// Achievement
		DrawTransScal(gl, mGR.mTex_Icon[8]	,-.70f,-.82f,mSel==4?1.1f:1,mSel==4?0.5f:1);// Leader-Board
		DrawTransScal(gl, mGR.mTex_Icon[6]	,-.90f,-.82f,mSel==5?1.1f:1,mSel==5?0.5f:1);// MORE
		DrawTransScal(gl, mGR.mTex_Icon[3]  ,0.80f,-.82f,mSel==6?1.1f:1,mSel==6?0.5f:1);// Play
		DrawTransScal(gl, mGR.mTex_Icon[2]	,-.30f,-.82f,mSel==7?1.1f:1,mSel==7?0.5f:1);// share
		if(M.GameScreen == M.GAMECONG){
			DrawTexture(gl, mGR.mTex_Cong	, 0.00f, 0.82f);
			DrawTransScal(gl, mGR.mTex_Reset	,0.40f,-.82f,mSel==8?1.1f:1,mSel==8?0.5f:1);// reset
		}
		else{
			DrawTexture(gl, mGR.mTex_Over	, 0.00f, 0.82f);
		}
	}
	boolean Handle_Gameover(MotionEvent event){
		mSel = 0;
		if(CircRectsOverlap(0.90f,0.82f,  mGR.mTex_Icon[11].width()*.2f,  mGR.mTex_Icon[11].Height()*.5f, screen2worldX(event.getX()),screen2worldY(event.getY()), .05f)){
			mSel = 1;//sound
		}
		if(CircRectsOverlap(0.70f,0.82f,  mGR.mTex_Icon[11].width()*.2f,  mGR.mTex_Icon[11].Height()*.5f, screen2worldX(event.getX()),screen2worldY(event.getY()), .05f)){
			mSel = 2;//music
		}
		if(CircRectsOverlap(-.50f,-.82f,  mGR.mTex_Icon[11].width()*.2f,  mGR.mTex_Icon[11].Height()*.5f, screen2worldX(event.getX()),screen2worldY(event.getY()), .05f)){
			mSel = 3;//More
		}
		if(CircRectsOverlap(-.70f,-.82f,  mGR.mTex_Icon[11].width()*.2f,  mGR.mTex_Icon[11].Height()*.5f, screen2worldX(event.getX()),screen2worldY(event.getY()), .05f)){
			mSel = 4;//leader board
		}
		if(CircRectsOverlap(-.90f,-.82f,  mGR.mTex_Icon[11].width()*.2f,  mGR.mTex_Icon[11].Height()*.5f, screen2worldX(event.getX()),screen2worldY(event.getY()), .05f)){
			mSel = 5;//achievement
		}
		if(CircRectsOverlap(0.80f,-.82f,  mGR.mTex_Icon[11].width()*.5f,  mGR.mTex_Icon[11].Height()*.5f, screen2worldX(event.getX()),screen2worldY(event.getY()), .05f)){
			mSel = 6;//play
		}
		if(CircRectsOverlap(-.30f,-.82f,  mGR.mTex_Icon[11].width()*.2f,  mGR.mTex_Icon[11].Height()*.5f, screen2worldX(event.getX()),screen2worldY(event.getY()), .05f)){
			mSel = 7;//share
		}
		if(M.GameScreen == M.GAMECONG && 
				CircRectsOverlap(0.40f,-.82f,  mGR.mTex_Icon[11].width()*.2f,  mGR.mTex_Icon[11].Height()*.5f, screen2worldX(event.getX()),screen2worldY(event.getY()), .05f)){
			mSel = 8;//Reset
		}
		if(MotionEvent.ACTION_UP == event.getAction()&& mSel > 0){
			switch (mSel) {
			case 1:
				M.setValue =!M.setValue;
				break;

			case 2:
				M.setMusic = !M.setMusic;
				break;
			case 3:
				GameRenderer.mStart.onShowAchievementsRequested();//Achievement
				break;
			case 4:
				GameRenderer.mStart.onShowLeaderboardsRequested();//leader board
				break;
			case 5:
				MoreGame();
				break;
			case 6:
				M.GameScreen = M.GAMESHOP;
				fade = 1;
				break;
			case 7:
				share2friend();
				break;
			case 8:
				GameRenderer.mStart.resetGame();//Reset
				break;
			}
			M.sound10(R.drawable.click);
			mSel = 0;
		}
		return true;
	}
	/*******************Gameover End***********************************/
	/*******************About Start*********************************/
	void Draw_About(GL10 gl){
		DrawBG(gl);
		DrawTexture(gl, mGR.mTex_AbutScr, 0, 0);
		DrawTransScal(gl, mGR.mTex_Icon[11], -.88f,-.86f,mSel==16?1.1f:1,mSel==16?0.5f:1);// Back
	}
	boolean Handle_About(MotionEvent event){
		mSel = 0;
		if(CircRectsOverlap(-.88f,-.86f,  mGR.mTex_Icon[11].width()*.5f,  mGR.mTex_Icon[11].Height()*.5f, screen2worldX(event.getX()),screen2worldY(event.getY()), .05f)){
			mSel = 16;
		}
		if(MotionEvent.ACTION_UP == event.getAction()&& mSel > 0){
			M.GameScreen = M.GAMEMENU;
			M.play(R.drawable.splash);
			fade = 1;
			mSel = 0;
			M.sound10(R.drawable.click);
		}
		return true;
	}
	/*******************About End***********************************/
	
	/*******************Option Start*********************************/
	void Draw_Option(GL10 gl){
		DrawBG(gl);
		mGR.mTex_Sky.drawRGBTS(gl, 0, 0, .2f,.2f,.2f, .6f, 1.2f, 1.2f);
		
		DrawTransScal(gl, mGR.mTex_Icon[11], -.88f,-.86f,mSel==16?1.1f:1,mSel==16?0.5f:1);// Back
		
		DrawTransScal(gl, mGR.mTex_Icon[M.setValue?1:0], -.51f+ (0%4)*.34f,.25f- (0/4)*.5f,mSel==1?1.1f:1,mSel==1?0.5f:1);// Sound
		DrawTransScal(gl, mGR.mTex_Icon[M.setMusic?5:4], -.51f+ (1%4)*.34f,.25f- (1/4)*.5f,mSel==2?1.1f:1,mSel==2?0.5f:1);// Music
		DrawTransScal(gl, mGR.mTex_Icon[ 8], -.51f+ (2%4)*.34f,.25f- (2/4)*.5f,mSel==3?1.1f:1,mSel==3?0.5f:1);// Leader board
		DrawTransScal(gl, mGR.mTex_Icon[12], -.51f+ (3%4)*.34f,.25f- (3/4)*.5f,mSel==4?1.1f:1,mSel==4?0.5f:1);// Achievement
		DrawTransScal(gl, mGR.mTex_Icon[ 9], -.51f+ (4%4)*.34f,.25f- (4/4)*.5f,mSel==5?1.1f:1,mSel==5?0.5f:1);// Google+
		DrawTransScal(gl, mGR.mTex_Icon[10], -.51f+ (5%4)*.34f,.25f- (5/4)*.5f,mSel==6?1.1f:1,mSel==6?0.5f:1);// facebook
		DrawTransScal(gl, mGR.mTex_Icon[13], -.51f+ (6%4)*.34f,.25f- (6/4)*.5f,mSel==7?1.1f:1,mSel==7?0.5f:1);// twitter
		DrawTransScal(gl, mGR.mTex_Icon[ 2], -.51f+ (7%4)*.34f,.25f- (7/4)*.5f,mSel==8?1.1f:1,mSel==8?0.5f:1);// share
		
		
	}
	boolean Handle_Option(MotionEvent event){
		mSel = 0;
		for(int i = 0;i<8;i++){
			if(CircRectsOverlap(-.51f+ (i%4)*.34f,.25f- (i/4)*.5f,  mGR.mTex_Icon[11].width()*.5f,  mGR.mTex_Icon[11].Height()*.5f, screen2worldX(event.getX()),screen2worldY(event.getY()), .05f)){
				mSel = i+1;
			}
		}
		if(CircRectsOverlap(-.88f,-.86f,  mGR.mTex_Icon[11].width()*.5f,  mGR.mTex_Icon[11].Height()*.5f, screen2worldX(event.getX()),screen2worldY(event.getY()), .05f)){
			mSel = 16;
		}
		if(MotionEvent.ACTION_UP == event.getAction() && mSel > 0){
			switch (mSel) {
			case 1:
				M.setValue=!M.setValue;
				break;
			case 2:
				M.setMusic=!M.setMusic;
				break;
			case 3:
				GameRenderer.mStart.onShowLeaderboardsRequested();//leader board
				break;
			case 4:
				GameRenderer.mStart.onShowAchievementsRequested();//Achievement
				break;
			case 5:
				google();
				break;
			case 6:
				facebook();
				break;
			case 7:
				Twitter();
				break;
			case 8:
				share2friend();
				break;
			case 16:
				M.GameScreen = M.GAMEMENU;
				M.play(R.drawable.splash);
				fade = 1;
				break;
			}
			M.sound10(R.drawable.click);
			mSel =0;
		}
		return true;
	}
	/*******************Option End***********************************/
	
	/*******************Shop Start*********************************/
	void Draw_Shop(GL10 gl) {
		DrawTexture(gl, mGR.mTex_BGBox, 0.00f, .0f);
		if (mGR.carSel == 0)
			DrawTexture(gl, mGR.mTex_Tab[2], -.680f, .88f);
		if (mGR.carSel == 1)
			DrawTexture(gl, mGR.mTex_Tab[1], -.340f, .88f);
		if (mGR.carSel == 2)
			DrawTexture(gl, mGR.mTex_Tab[1], 0.005f, .88f);
		if (mGR.carSel == 3)
			DrawTexture(gl, mGR.mTex_Tab[1], 0.345f, .88f);
		if (mGR.carSel == 4)
			DrawTexture(gl, mGR.mTex_Tab[0], 0.700f, .88f);

		for (int i = 0; i < 5; i++) {
			DrawTexture(gl, mGR.mTex_Scar[i * 3], -.68f + .34f * i, .91f);
		}
		
		for (int i = 0; i < 3; i++) {
			DrawTexture(gl, mGR.mTex_Scar[mGR.carSel * 3 + i], -.5f + .67f * i,0.63f);
			DrawTexture(gl, mGR.mTex_SName[mGR.carSel * 3 + i], -.83f + .67f* i, .60f);
			for (int k = 0; k < 3; k++) {
				for (int j = 0; j < mGR.mCarValue[mGR.carSel * 3 + i].upgred[k]; j++) {
					DrawTexture(gl, mGR.mTex_Dot, i * .678f - .79f + .053f * j,.335f - .295f * k);
				}
				drawNumber(gl, ":", -.45f + i * .678f, .42f - .295f * k, 1.3f,2,0);
				drawNumber(gl, ""+ ((mGR.mCarValue[mGR.carSel * 3 + i].upgred[k])*
						(mGR.carSel * 3 + i+1)*(mGR.carSel+1)*100)
						,-.43f + i * .678f, .335f - .295f * k, 1, 0,1);
			}

			

			switch (mGR.carSel) {
			case 0:
				if (i == 0) {
					mGR.mTex_Gun[0].drawRotet2(gl, -.9f, .38f, 0, 0);
					DrawTexture(gl, mGR.mTex_PName[1], -.68f, .42f);
					mGR.mTex_Gun[6].drawRotet2(gl, -.9f, .10f, 0, 0);
					DrawTexture(gl, mGR.mTex_PName[2], -.68f, 0.14f);
				}
				if (i == 1) {
					mGR.mTex_Gun[0].drawRotet2(gl, -.22f, .38f, 0, 0);
					DrawTexture(gl, mGR.mTex_PName[1], 0, .42f);
					DrawTexture(gl, mGR.mTex_Timer, -.22f, .10f);
					DrawTexture(gl, mGR.mTex_PName[4], 0, .14f);
				}
				if (i == 2) {
					mGR.mTex_Gun[1].drawRotet2(gl, .46f, .38f, 0, 0);
					DrawTexture(gl, mGR.mTex_PName[1], 0.67f, .42f);
					DrawTexture(gl, mGR.mTex_ShopSield, .46f, 0.10f);
					DrawTexture(gl, mGR.mTex_PName[3], 0.67f, .14f);
				}
				break;
			case 1:
				if (i == 0) {
					mGR.mTex_Gun[1].drawRotet2(gl, -.9f, .38f, 0, 0);
					DrawTexture(gl, mGR.mTex_PName[1], -.68f, .42f);
					mGR.mTex_Gun[6].drawRotet2(gl, -.9f, .10f, 0, 0);
					DrawTexture(gl, mGR.mTex_PName[2], -.68f, 0.14f);
				}
				if (i == 1) {
					mGR.mTex_Gun[0].drawRotet2(gl, -.22f, .38f, 0, 0);
					DrawTexture(gl, mGR.mTex_PName[1], 0, .42f);
					DrawTexture(gl, mGR.mTex_Timer, -.22f, .10f);
					DrawTexture(gl, mGR.mTex_PName[4], 0, .14f);
				}
				if (i == 2) {
					mGR.mTex_Gun[2].drawRotet2(gl, .46f, .38f, 0, 0);
					DrawTexture(gl, mGR.mTex_PName[1], 0.67f, .42f);
					DrawTexture(gl, mGR.mTex_ShopSield, .46f, 0.10f);
					DrawTexture(gl, mGR.mTex_PName[3], 0.67f, .14f);
				}
				break;
			case 2:
				if (i == 0) {
					mGR.mTex_Gun[2].drawRotet2(gl, -.9f, .38f, 0, 0);
					DrawTexture(gl, mGR.mTex_PName[1], -.68f, .42f);
					mGR.mTex_Gun[6].drawRotet2(gl, -.9f, .10f, 0, 0);
					DrawTexture(gl, mGR.mTex_PName[2], -.68f, 0.14f);
				}
				if (i == 1) {
					mGR.mTex_Gun[0].drawRotet2(gl, -.22f, .38f, 0, 0);
					DrawTexture(gl, mGR.mTex_PName[1], 0, .42f);
					DrawTexture(gl, mGR.mTex_Timer, -.22f, .10f);
					DrawTexture(gl, mGR.mTex_PName[4], 0, .14f);
				}
				if (i == 2) {
					mGR.mTex_Gun[3].drawRotet2(gl, .46f, .38f, 0, 0);
					DrawTexture(gl, mGR.mTex_PName[1], 0.67f, .42f);
					DrawTexture(gl, mGR.mTex_ShopSield, .46f, 0.10f);
					DrawTexture(gl, mGR.mTex_PName[3], 0.67f, .14f);
				}
				break;
			case 3:
				if (i == 0) {
					mGR.mTex_Gun[4].drawRotet2(gl, -.9f, .38f, 0, 0);
					DrawTexture(gl, mGR.mTex_PName[1], -.68f, .42f);
					mGR.mTex_Gun[6].drawRotet2(gl, -.9f, .10f, 0, 0);
					DrawTexture(gl, mGR.mTex_PName[2], -.68f, 0.14f);
				}
				if (i == 1) {
					mGR.mTex_Gun[1].drawRotet2(gl, -.22f, .38f, 0, 0);
					DrawTexture(gl, mGR.mTex_PName[1], 0, .42f);
					DrawTexture(gl, mGR.mTex_Timer, -.22f, .10f);
					DrawTexture(gl, mGR.mTex_PName[4], 0, .14f);
				}
				if (i == 2) {
					mGR.mTex_Gun[0].drawRotet2(gl, .46f, .38f, 0, 0);
					DrawTexture(gl, mGR.mTex_PName[1], 0.67f, .42f);
					DrawTexture(gl, mGR.mTex_ShopSield, .46f, 0.10f);
					DrawTexture(gl, mGR.mTex_PName[3], 0.67f, .14f);
				}
				break;
			case 4:
				if (i == 0) {
					mGR.mTex_Gun[2].drawRotet2(gl, -.9f, .38f, 0, 0);
					DrawTexture(gl, mGR.mTex_PName[1], -.68f, .42f);
					mGR.mTex_Gun[6].drawRotet2(gl, -.9f, .10f, 0, 0);
					DrawTexture(gl, mGR.mTex_PName[2], -.68f, 0.14f);
				}
				if (i == 1) {
					mGR.mTex_Gun[4].drawRotet2(gl, -.22f, .38f, 0, 0);
					DrawTexture(gl, mGR.mTex_PName[1], 0, .42f);
					DrawTexture(gl, mGR.mTex_Timer, -.22f, .10f);
					DrawTexture(gl, mGR.mTex_PName[4], 0, .14f);
				}
				if (i == 2) {
					mGR.mTex_Gun[3].drawRotet2(gl, .46f, .38f, 0, 0);
					DrawTexture(gl, mGR.mTex_PName[1], 0.67f, .42f);
					DrawTexture(gl, mGR.mTex_ShopSield, .46f, 0.10f);
					DrawTexture(gl, mGR.mTex_PName[3], 0.67f, .14f);
				}
				break;
			}
			if (mGR.carDrive[i] >= 0) {
				DrawTexture(gl, mGR.mTex_Scar[mGR.carDrive[i]], -.41f + .31f* i, -.83f);
			}
			if (!mGR.mCarValue[mGR.carSel * 3 + i].Buy) {
				mGR.mTex_Sky.drawRGBTS(gl, -.68f + i * .68f, .08f, 0.1f, 0.1f, 0.1f, .8f, .53f,1.21f);
				DrawTransScal(gl, mGR.mTex_BuyBtn, -.66f + i * .678f, -.43f,mSel==i+6?1.1f:1,mSel==i+6?0.5f:1);
				drawNumber(gl, ""+ ((mGR.carSel * 3 + i+1)*(mGR.carSel+1)*325),-.66f + i * .678f, -.43f, 1, 0,0);
			} else {
				DrawTransScal(gl, mGR.mTex_SelectBtn, -.66f + i * .678f, -.43f,mSel==i+6?1.1f:1,mSel==i+6?0.5f:1);
			}
		}
		if (mGR.carSel * 300 + 400 > mGR.Tdistance) {
			mGR.mTex_Sky.drawRGBTS(gl, .68f, .08f, 0.1f, 0.1f, 0.1f, .8f, .53f,1.21f);
			DrawTexture(gl, mGR.mTex_Lock, .68f, .1f);
			drawNumber(gl, ""+(mGR.carSel * 300 + 400), .5f+(mGR.carSel<2?.013f:0), -.01f, 1, 0, 0);
		}
		DrawTransScal(gl, mGR.mTex_Icon[11], -.88f,-.86f,mSel==15?1.1f:1,mSel==16?0.5f:1);// Back
		DrawTransScal(gl, mGR.mTex_Icon[6], -.66f, -.86f,mSel==16?1.1f:1,mSel==16?0.5f:1);// More
		DrawTransScal(gl, mGR.mTex_Icon[3], 0.76f, -.82f,mSel==17?1.1f:1,mSel==17?0.5f:1);// Play

		DrawTextureS(gl, mGR.mTex_Coin[Counter % 10], -.95f, -.65f, 1.4f);// Play
		drawNumber(gl, "" + mGR.mCoin, -.90f, -.65f, 1.4f, 2,0);
		
		if(mGR.shopCarDrive){
			mGR.mTex_BGBox.drawRGBTS(gl, 0, 0, 0, 0, 0, .7f, 1,1);
//			DrawTexture(gl, mGR.mTex_BGBox, 0.005f, .0f);
			for(int i=0;i<3;i++)
			if (mGR.carDrive[i] >= 0) {
				DrawTexture(gl, mGR.mTex_Scar[mGR.carDrive[i]], -.41f + .31f* i, -.83f);
			}
		}
		
	}
	byte Dummy = -1;
	boolean Handle_Shop(MotionEvent event){
		mSel = 0;
		if(CircRectsOverlap(-.680f, .88f, mGR.mTex_Tab[2].width()*.3f, mGR.mTex_Tab[2].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mSel = 1;
		}
		if(CircRectsOverlap(-.340f, .88f, mGR.mTex_Tab[2].width()*.3f, mGR.mTex_Tab[2].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mSel = 2;
		}
		if(CircRectsOverlap(0.005f, .88f, mGR.mTex_Tab[2].width()*.3f, mGR.mTex_Tab[2].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mSel = 3;
		}
		if(CircRectsOverlap(0.345f, .88f, mGR.mTex_Tab[2].width()*.3f, mGR.mTex_Tab[2].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mSel = 4;
		}
		if(CircRectsOverlap(0.700f, .88f, mGR.mTex_Tab[2].width()*.3f, mGR.mTex_Tab[2].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mSel = 5;
		}
		for(int i=0;i<3;i++)
			if(CircRectsOverlap(-.66f + i * .678f, -.43f, mGR.mTex_Tab[2].width()*.3f, mGR.mTex_Tab[2].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
				mSel = i+6;
			}
		
		
		for(int i=0;i<3 && mGR.shopCarDrive;i++){
			if(CircRectsOverlap(-.41f + .31f* i, -.83f, mGR.mTex_Tab[2].width()*.2f, mGR.mTex_Tab[2].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
				mSel = i+10;
			}
		}
		
		
		if(CircRectsOverlap(-.88f,-.86f, mGR.mTex_Icon[2].width()*.3f, mGR.mTex_Tab[2].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mSel = 15;// Back
		}
		if(CircRectsOverlap(-.66f, -.86f, mGR.mTex_Icon[2].width()*.3f, mGR.mTex_Tab[2].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mSel = 16;// more
		}
		if(CircRectsOverlap( 0.76f, -.82f, mGR.mTex_Icon[2].width()*.3f, mGR.mTex_Tab[2].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mSel = 17;// Play
		}
		if(MotionEvent.ACTION_UP == event.getAction()){
			for (int i = 0; i < 3; i++) {
				for (int k = 0; k < 3; k++) {
					if(CircRectsOverlap(-.43f + i * .678f, .335f - .295f * k, mGR.mTex_Tab[2].width()*.2f, mGR.mTex_Tab[2].Height()*.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
						if (mGR.mCarValue[mGR.carSel * 3 + i].upgred[k] < 5 &&
								((mGR.carSel * 300 + 400 <= mGR.Tdistance && i == 2) || i < 2))
						{
							if (mGR.mCarValue[mGR.carSel * 3 + i].Buy) {
								int reqMoney = ((mGR.mCarValue[mGR.carSel * 3+ i].upgred[k])
										* (mGR.carSel * 3 + i + 1)
										* (mGR.carSel + 1) * 100);
								if (mGR.mCoin >= reqMoney) {
									mGR.mCarValue[mGR.carSel * 3 + i].upgred[k]++;
									mGR.mCoin -= reqMoney;
									M.sound17(R.drawable.upgrade);
								} else {
									GameRenderer.mStart.Donot();
								}
							}else{
								GameRenderer.mStart.Shop();
							}
						}
					}
				}
			}
			
			switch (mSel) {
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
				mGR.carSel = (byte) (mSel - 1);
				M.sound10(R.drawable.click);
				break;
			case 6:
			case 7:
			case 8:
				if (mGR.shopCarDrive) {
					mGR.shopCarDrive = false;
				} else {
					final int i = mSel - 6;
					
					if ((mGR.carSel * 300 + 400 <= mGR.Tdistance && i == 2) || i < 2) {
						Dummy = (byte) (mGR.carSel * 3 + i);
						if (!mGR.mCarValue[mGR.carSel * 3 + i].Buy) {
							int reqMoney = ((mGR.carSel * 3 + i + 1) * (mGR.carSel + 1) * 325);
							if (mGR.mCoin >= reqMoney) {
								mGR.mCarValue[mGR.carSel * 3 + i].Buy = true;// Buy
								mGR.mCoin -= reqMoney;
							} else {
								GameRenderer.mStart.Donot();
							}
						}
						if (mGR.mCarValue[mGR.carSel * 3 + i].Buy)
						{
							int m = 0;
							for (int k = 0; k < mGR.mCarValue.length; k++) {
								if (mGR.mCarValue[k].Buy) {
									m++;
								}
							}
							for (int j = 0; j < 3 && m > 1; j++) {
								if (mGR.carDrive[j] == (mGR.carSel * 3 + i)) {
									mGR.shopCarDrive = true;
									M.sound13(R.drawable.select_car);
									break;
								}
							}
							if (mGR.carDrive[0] >= 0 && mGR.carDrive[1] >= 0 && mGR.carDrive[2] >= 0) {
								mGR.shopCarDrive = true;
								M.sound13(R.drawable.select_car);
							}
							for (int j = 0; j < 3 && !mGR.shopCarDrive && m>1; j++) {
								if (mGR.carDrive[j] < 0) {
									mGR.carDrive[j] = (byte) (mGR.carSel * 3 + i);
									M.sound13(R.drawable.select_car);
									break;
								}
							}

						}
					}
				}
				break;
			case 10:case 11:case 12:
				if(mGR.shopCarDrive){
					mGR.shopCarDrive = false;
					int j;
					for (j = 0; j < 3; j++) {
						if (mGR.carDrive[j] == Dummy) {
							break;
						}
					}
					if(j<3)
						mGR.carDrive[j] = mGR.carDrive[mSel -10];
					mGR.carDrive[mSel -10] = Dummy;
					M.sound13(R.drawable.select_car);
				}
				break;
			case 15://back;
				M.GameScreen = M.GAMEMENU;
				M.play(R.drawable.splash);
				fade = 1;
				M.sound10(R.drawable.click);
				break;
			case 16://more;
				MoreGame();
				break;
			case 17://Play;
				mGR.gameReset();
				M.GameScreen= M.GAMEPLAY;
				M.play(R.drawable.gameplay_theme);
				fade = 1;
				M.sound10(R.drawable.click);
				break;
			}
			mSel = 0;
		}
		return true;
	}
	/*******************Shop End*********************************/
	/*******************Menu Start*********************************/
	float zoom =1;
	float zb =.001f;
	byte p =100;
	byte vp =2;

	void Draw_Menu(GL10 gl) {
		DrawTexture(gl, mGR.mTex_Splash, 0, 0);
		DrawTransScal(gl, mGR.mTex_SText, .52f, .52f, zoom, 1);
		if (zoom > 1.05f)
			zb = -.001f;
		if (zoom < .95f)
			zb = .001f;
		zoom += zb;
		if (p > 110)
			vp = -1;
		if (p < 90)
			vp = 1;
		p += vp;

		DrawTransScal(gl, mGR.mTex_More, -.82f, .77f, mSel == 1 ? 1.1f : 1, mSel == 1 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_About, -.90f, -.83f, mSel == 2 ? 1.1f : 1, mSel == 2 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_Play, .70f, -.47f, mSel == 3 ? 1.1f : p * .01f, mSel == 3 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_Optin, .68f, -.82f, mSel == 4 ? 1.1f : 1, mSel == 4 ? 0.5f : 1);
	}
	boolean Handle_Menu(MotionEvent event){
		mSel = 0;
		if(CircRectsOverlap(-.82f, .77f, mGR.mTex_More.width()*.4f, mGR.mTex_More.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mSel = 1;
		}
		if(CircRectsOverlap(-.90f,-.83f, mGR.mTex_About.width()*.4f, mGR.mTex_About.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mSel = 2;
		}
		if(CircRectsOverlap(.70f,-.47f, mGR.mTex_Play.width()*.4f, mGR.mTex_Play.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mSel = 3;
		}
		if(CircRectsOverlap(.68f,-.82f, mGR.mTex_Optin.width()*.4f, mGR.mTex_Optin.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mSel = 4;
		}
		if(MotionEvent.ACTION_UP == event.getAction()){
			switch (mSel) {
			case 1:
				MoreGame();
				break;
			case 2:
				M.GameScreen = M.GAMEABUT;
				M.stop();
				fade = 1;
				break;
			case 3:
				M.GameScreen = M.GAMESHOP;
				M.stop();
				fade = 1;
				p = 110;
				break;
			case 4:
				M.GameScreen = M.GAMEOPT;
				M.stop();
				fade = 1;
				break;
			}
			if(mSel!=0)
				M.sound10(R.drawable.click);
			mSel = 0;
		}
		return true;
	}
	/*******************Menu End*********************************/
	
	void setPlayerMisiile(float x, float y) {
		int img = -1;
		for (int i = 0; i < mGR.mDusman.length; i++) {
			if (!mGR.mDusman[i].isIn()) {
				if (img == -1)
					img = i;
				if (mGR.mDusman[i].img == 1 || mGR.mDusman[i].img == 3 || mGR.mDusman[i].img == 5){
					img = i;
					break;
				}
			}
		}

		for (int i = 0; i < mGR.mPMissile.length && img >= 0; i++) {
			if (!mGR.mPMissile[i].IsIn()) {
				mGR.mPMissile[i].set(x, y, img,false);
				break;
			}
		}
	}
	void setSmock(float x,float y){
		for (int i = 0; i < mGR.mSmock.length; i++) {
			if (!mGR.mSmock[i].IsIn()) {
				mGR.mSmock[i].set(x, y);
				break;
			}
		}
	}
	
	void setNewOpp(float x, float y, int img) {
		for (int i = 0; i < mGR.mDusman.length; i++) {
			if (!mGR.mDusman[i].isIn()) 
			{
				mGR.mDusman[i].set(x, y, (byte) img);
				break;
			}
		}
//		mGR.mDusman[del].set(x, y, (byte) img);
	}

	void setOppMissile(float x, float y, int img) {
		for (int i = 0; i < mGR.mMissile.length; i++) {
			if (!mGR.mMissile[i].IsIn()) {
				for (int j = 0; j < mGR.mPlayCar.length; j++){
					if(mGR.mPlayCar[j].IsIn()){
						mGR.mMissile[i].set(x, y, mGR.mPlayCar[j].x, mGR.mPlayCar[j].y, img);
						break;
					}
				}
				break;
			}
		}
	}

	void setBullate(float x, float vx, float y, float vy, int ang,int _img) {
		for (int i = 0; i < mGR.mBulltet.length; i++) {
			if (!mGR.mBulltet[i].IsIn()) {
				mGR.mBulltet[i].set(x, y, vx, vy, ang,_img);
				break;
			}
		}
	}
	
	void setBlast(float x, float y,int sound) {
		for (int i = 0; i < mGR.mBlast.length; i++) {
			if (mGR.mBlast[i].img>14) {
				mGR.mBlast[i].set(x, y, 0);
				switch (sound) {
				case 0:
					M.sound9(R.drawable.blast_bike);
					break;
				case 1:
					M.BlastAir();
					break;
				case 2:
					M.BlastDusman();
					break;
				default:
					break;
				}
				break;
			}
		}
	}
	void playerLoss(int no,float loss){
		if(mGR.mPlayCar[no].iSAlive){
			mGR.mPlayCar[no].lifeLoss(loss);
		}else{
			for (int i = 0; i < mGR.mPlayCar.length; i++) {
				if (mGR.mPlayCar[i].update(i)) {
					mGR.mPlayCar[i].lifeLoss(loss);
					break;
				}
			}
		}
	}
	void setDushman(int i,int number,int Dum){
		int k = M.mRand.nextInt(number);
		if(mGR.oppseq[i][k]>0){
			mGR.oppseq[i][k]--;
			mGR.mDusman[Dum].set(1.1f, -.28f, (byte)k);
		}else{
			boolean get = false;
			for(int j=0;j<mGR.oppseq[i].length && !get;j++){
				if(mGR.oppseq[i][j]>0){
					mGR.oppseq[i][j]--;
					get = true;
					mGR.mDusman[Dum].set(1.5f, -.28f, (byte)j);
				}
			}
			if(!get){
				mGR.mDusman[Dum].set(1.5f, -.28f, (byte)M.mRand.nextInt(number));
			}
		}
	}
	short countmeter = 10;
	void gameOver(){
		GameRenderer.mStart.Achivment();
		M.stop();
		mGR.Rewrd =(int)(mGR.distance*100);
		mGR.Rewrd+=mGR.killVal;
		mGR.Rewrd+=mGR.Wall*150;
		countmeter = (short)(mGR.Rewrd/40);
		if(countmeter<5)
			countmeter =5;
		mGR.mCoin+= mGR.Rewrd;
		fade = 1;
		GameRenderer.mStart.ShowInterstitial();
		M.sound18(R.drawable.game_over);
	}
	void gamelogic() {
		M.Bullate(mGR.Bullate);
		
		mGR.distance-=mGR.spd*.0093f;
		if(mGR.distance*100>mGR.Tdistance){
			mGR.Tdistance=mGR.distance*100;
		}
		if(mGR.spd>-.1f)
			mGR.spd-=.01f;
		for (int i = 0; i < mGR.mTop.length; i++) {
			mGR.mTop[i].update(mGR.spd);
			mGR.mCenter[i].update(mGR.spd * .3f);
			mGR.mDown[i].update(mGR.spd * .1f);
		}
		for (int i = 0; i < mGR.mTop.length; i++) {
			if (mGR.mTop[i].x < -2.5f) {
				mGR.mTop[i].set(mGR.mTop[i == 0 ? 1 : 0].x + mGR.mTex_Road[0].width(),mGR.mTop[i].y);
				if (i == 0) {
					mGR.topCount--;
					if (mGR.topCount <= 0)
						mGR.topCount = (byte) M.mRand.nextInt(8);
				}
			}
			if (mGR.mCenter[i].x < -2.5f)
				mGR.mCenter[i].set(mGR.mCenter[i == 0 ? 1 : 0].x+ mGR.mTex_Road[0].width(), mGR.mCenter[i].y);
			if (mGR.mDown[i].x < -2.5f)
				mGR.mDown[i].set(mGR.mDown[i == 0 ? 1 : 0].x + mGR.mTex_Road[0].width(),mGR.mDown[i].y);
		}
		for (int i = 0; i < mGR.mDusman.length; i++) {
			if(mGR.mDusman[i].update(Counter) && mGR.mDusman[i].x < 1){
				for (int j = 0; j < mGR.mBulltet.length; j++) {
					if(mGR.mBulltet[j].IsIn()){
						if(CircRectsOverlap(mGR.mDusman[i].x, mGR.mDusman[i].y, 
								mGR.mTex_OppCar[mGR.mDusman[i].img].width()*.2f, mGR.mTex_OppCar[mGR.mDusman[i].img].Height()*.2f,
								mGR.mBulltet[j].x, mGR.mBulltet[j].y, .05f)){
							mGR.mDusman[i].lifeLoss(mGR.mBulltet[j].img,1,mGR.mCarValue[mGR.mBulltet[j].img].upgred[0]);
							mGR.mBulltet[j].x =-10;
						}
					}
				}
				for (int j = 0; j < mGR.mPMissile.length; j++) {
					if(mGR.mPMissile[j].IsIn()){
						if(CircRectsOverlap(mGR.mDusman[i].x, mGR.mDusman[i].y, 
								mGR.mTex_OppCar[mGR.mDusman[i].img].width()*.9f, mGR.mTex_OppCar[mGR.mDusman[i].img].Height()*.9f,
								mGR.mPMissile[j].x, mGR.mPMissile[j].y, .15f)){
							setBlast(mGR.mDusman[i].x, mGR.mDusman[i].y,M.mRand.nextInt(3));
							mGR.mDusman[i].lifeLoss(100,0,1);
							mGR.mPMissile[j].x =-10;
						}
					}
				}
				{

					switch (mGR.mDusman[i].img) {
					case 0:
						playerLoss(0, .001f);
						break;
					case 1:
						playerLoss(0, .002f);
						break;
					case 2:
						playerLoss(2, .001f);
						break;
					case 3:
						break;
					case 4:
						playerLoss(2, .001f);
						break;
					case 5:
						playerLoss(0, .005f);
						break;
					case 6:
						playerLoss(2, .001f);
						break;
					case 7:
						playerLoss(2, .002f);
						break;
					case 8:
						playerLoss(2, .002f);
						break;
					}
				
				}
			}
			
		}
		if (mGR.distance < 20) {
			for (int i = 0; i < mGR.mDusman.length && Counter % (int) (51 - mGR.distance * 2.5) == 0; i++) {
				if (!mGR.mDusman[i].isIn()) {
					if (mGR.distance < .5f)
						setDushman(0, 2, i);
					else if (mGR.distance < 1.5f)
						setDushman(1, 3, i);
					else if (mGR.distance < 4.0f)
						setDushman(2, 4, i);
					else if (mGR.distance < 7.5f)
						setDushman(3, 5, i);
					else if (mGR.distance < 10.5f)
						setDushman(4, 6, i);
					else if (mGR.distance < 13.5f)
						setDushman(5, 7, i);
					else if (mGR.distance < 16.5f)
						setDushman(6, 8, i);
					else
						setDushman(7, 9, i);
					break;
				}
			}
		}
		boolean Out = true;
		for (int i = 0; i < mGR.mPlayCar.length; i++) {
			if (mGR.mPlayCar[i].update(i)) {
				Out = false;
				for (int j = 0; j < mGR.mMissile.length; j++) {
					if (mGR.mMissile[j].IsIn()) {
						if(CircRectsOverlap(mGR.mMissile[j].x, mGR.mMissile[j].y, 
								mGR.mTex_OppMis[0].width(), mGR.mTex_OppMis[0].Height(),
								mGR.mPlayCar[i].x, mGR.mPlayCar[i].y, .1f)){
						mGR.mPlayCar[i].lifeLoss(.1f);
						setBlast(mGR.mMissile[j].x, mGR.mMissile[j].y,M.mRand.nextInt(3));
						mGR.mMissile[j].reset();
						}
//						dsf
					}
				}
			}
		}
		if (Out) {
			if (mGR.overCount > 100) {
				M.GameScreen = M.GAMEOVER;
				gameOver();
			}
			mGR.overCount++;
		}
		if(mGR.distance>20){
			boolean win = true;
			for (int i = 0; i < mGR.mDusman.length; i++) {
				if (mGR.mDusman[i].isIn()){
					win = false;
					break;
				}
			}
			if(win){
				M.GameScreen = M.GAMECONG;
				gameOver();
			}
		}
	}

	void Draw_Car(GL10 gl) {
		for(int i=0;i<mGR.mPlayCar.length;i++){
			if(mGR.mPlayCar[i].iSAlive){
				switch (mGR.mPlayCar[i].img) {
				case 0:
					DrawTexture(gl, mGR.mTex_Car[mGR.mPlayCar[i].img], mGR.mPlayCar[i].x, mGR.mPlayCar[i].y);
					mGR.mTex_CTyre[0].drawRotet2(gl, .06f + mGR.mPlayCar[i].x,mGR.mPlayCar[i].y - .07f, -Counter * 5, 0);
					mGR.mTex_CTyre[0].drawRotet2(gl, -.05f + mGR.mPlayCar[i].x,mGR.mPlayCar[i].y - .07f, -Counter * 5, 0);
					mGR.mTex_Gun[0].drawRotet2(gl, mGR.mPlayCar[i].x - .02f,mGR.mPlayCar[i].y + .09f, mGR.mPlayCar[i].ang - 90, .25f);
//					if(Counter%(11-mGR.mCarValue[mGR.mPlayCar[i].img].upgred[0]*2)==0)
						setBullate(mGR.mPlayCar[i].x, mGR.mPlayCar[i].vx , .1f + mGR.mPlayCar[i].y, mGR.mPlayCar[i].vy , (int)mGR.mPlayCar[i].ang + 90,0);
					break;
				case 1:
					DrawTexture(gl, mGR.mTex_Car[mGR.mPlayCar[i].img], mGR.mPlayCar[i].x, mGR.mPlayCar[i].y);
					mGR.mTex_CTyre[0].drawRotet2(gl, mGR.mPlayCar[i].x + .06f, -.1f+ mGR.mPlayCar[i].y, -Counter * 5, 0);
					mGR.mTex_CTyre[0].drawRotet2(gl, mGR.mPlayCar[i].x - .05f, -.1f+ mGR.mPlayCar[i].y, -Counter * 5, 0);
					mGR.mTex_Gun[0].drawRotet2(gl, mGR.mPlayCar[i].x + .05f,0 + mGR.mPlayCar[i].y, mGR.mPlayCar[i].ang - 90, .25f);
//					if(Counter%(11-mGR.mCarValue[mGR.mPlayCar[i].img].upgred[0]*2)==0)
						setBullate(mGR.mPlayCar[i].x+ .05f, mGR.mPlayCar[i].vx , .0f + mGR.mPlayCar[i].y, mGR.mPlayCar[i].vy , (int)mGR.mPlayCar[i].ang + 90,mGR.mPlayCar[i].img);
					break;
				case 2:
					DrawTexture(gl, mGR.mTex_Car[mGR.mPlayCar[i].img], mGR.mPlayCar[i].x, mGR.mPlayCar[i].y);
					mGR.mTex_CTyre[0].drawRotet2(gl, mGR.mPlayCar[i].x + .03f,-.07f+ mGR.mPlayCar[i].y, -Counter * 5, 0);
					mGR.mTex_CTyre[0].drawRotet2(gl, mGR.mPlayCar[i].x - .07f,-.07f+ mGR.mPlayCar[i].y, -Counter * 5, 0);
					mGR.mTex_Gun[1].drawRotet2(gl, mGR.mPlayCar[i].x - .04f,.08f + mGR.mPlayCar[i].y, mGR.mPlayCar[i].ang - 90, .25f);
					if(Counter%8==0)
						setBullate(mGR.mPlayCar[i].x-.04f, mGR.mPlayCar[i].vx , .08f + mGR.mPlayCar[i].y, mGR.mPlayCar[i].vy , (int)mGR.mPlayCar[i].ang + 90,mGR.mPlayCar[i].img);
					break;
				case 3:
					mGR.mTex_Gun[6].drawRotet2(gl, mGR.mPlayCar[i].x - .14f,0.03f+ mGR.mPlayCar[i].y, 40, .25f);
					DrawTexture(gl, mGR.mTex_Car[mGR.mPlayCar[i].img], mGR.mPlayCar[i].x, mGR.mPlayCar[i].y);
					mGR.mTex_CTyre[1].drawRotet2(gl, mGR.mPlayCar[i].x + .06f,-.08f+ mGR.mPlayCar[i].y, -Counter * 5, 0);
					mGR.mTex_CTyre[1].drawRotet2(gl, mGR.mPlayCar[i].x - .06f,-.08f+ mGR.mPlayCar[i].y, -Counter * 5, 0);
					mGR.mTex_Gun[1].drawRotet2(gl, mGR.mPlayCar[i].x - .04f,.08f + mGR.mPlayCar[i].y, mGR.mPlayCar[i].ang - 90, .25f);
					if(Counter%5==0)
						setBullate(mGR.mPlayCar[i].x- .04f, mGR.mPlayCar[i].vx , .08f + mGR.mPlayCar[i].y, mGR.mPlayCar[i].vy , (int)mGR.mPlayCar[i].ang + 90,mGR.mPlayCar[i].img);
					break;
				case 4:
					DrawTexture(gl, mGR.mTex_Car[mGR.mPlayCar[i].img], mGR.mPlayCar[i].x, mGR.mPlayCar[i].y);
					mGR.mTex_CTyre[1].drawRotet2(gl, mGR.mPlayCar[i].x + .06f,-.08f+ mGR.mPlayCar[i].y, -Counter * 5, 0);
					mGR.mTex_CTyre[1].drawRotet2(gl, mGR.mPlayCar[i].x - .07f,-.08f+ mGR.mPlayCar[i].y, -Counter * 5, 0);
					mGR.mTex_Gun[0].drawRotet2(gl, mGR.mPlayCar[i].x - .04f,.08f + mGR.mPlayCar[i].y, mGR.mPlayCar[i].ang - 90, .25f);
//					if(Counter%(11-mGR.mCarValue[mGR.mPlayCar[i].img].upgred[0]*2)==0)
						setBullate(mGR.mPlayCar[i].x- .04f, mGR.mPlayCar[i].vx , .08f + mGR.mPlayCar[i].y, mGR.mPlayCar[i].vy , (int)mGR.mPlayCar[i].ang + 90,mGR.mPlayCar[i].img);
					break;
				case 5:
					DrawTexture(gl, mGR.mTex_Car[mGR.mPlayCar[i].img], mGR.mPlayCar[i].x, mGR.mPlayCar[i].y);
					mGR.mTex_CTyre[1].drawRotet2(gl, mGR.mPlayCar[i].x + .03f,-.08f+ mGR.mPlayCar[i].y, -Counter * 5, 0);
					mGR.mTex_CTyre[1].drawRotet2(gl, mGR.mPlayCar[i].x - .09f,-.08f+ mGR.mPlayCar[i].y, -Counter * 5, 0);
					mGR.mTex_Gun[2].drawRotet2(gl, mGR.mPlayCar[i].x - .04f,.08f + mGR.mPlayCar[i].y, mGR.mPlayCar[i].ang - 90, .25f);
//					if(Counter%(8-mGR.mCarValue[mGR.mPlayCar[i].img].upgred[0])==0)
						setBullate(mGR.mPlayCar[i].x- .04f, mGR.mPlayCar[i].vx , .08f + mGR.mPlayCar[i].y, mGR.mPlayCar[i].vy , (int)mGR.mPlayCar[i].ang + 90,mGR.mPlayCar[i].img);
					break;
				case 6:
					DrawTexture(gl, mGR.mTex_Car[mGR.mPlayCar[i].img], mGR.mPlayCar[i].x, mGR.mPlayCar[i].y);
					mGR.mTex_CTyre[2].drawRotet2(gl, mGR.mPlayCar[i].x + 0.08f,-.11f+ mGR.mPlayCar[i].y, -Counter * 5, 0);
					mGR.mTex_CTyre[2].drawRotet2(gl, mGR.mPlayCar[i].x - 0.06f,-.11f+ mGR.mPlayCar[i].y, -Counter * 5, 0);
					mGR.mTex_Gun[2].drawRotet2(gl, mGR.mPlayCar[i].x + 0.01f,0.1f + mGR.mPlayCar[i].y, mGR.mPlayCar[i].ang - 90, .25f);
//					if(Counter%(8-mGR.mCarValue[mGR.mPlayCar[i].img].upgred[0])==0)
						setBullate(mGR.mPlayCar[i].x+ 0.01f, mGR.mPlayCar[i].vx , .1f + mGR.mPlayCar[i].y, mGR.mPlayCar[i].vy , (int)mGR.mPlayCar[i].ang + 90,mGR.mPlayCar[i].img);
					break;
				case 7:
					DrawTexture(gl, mGR.mTex_Car[mGR.mPlayCar[i].img], mGR.mPlayCar[i].x, mGR.mPlayCar[i].y);
					mGR.mTex_CTyre[2].drawRotet2(gl, mGR.mPlayCar[i].x + 0.08f,-.11f+ mGR.mPlayCar[i].y, -Counter * 5, 0);
					mGR.mTex_CTyre[2].drawRotet2(gl, mGR.mPlayCar[i].x - 0.06f,-.11f+ mGR.mPlayCar[i].y, -Counter * 5, 0);
					mGR.mTex_Gun[0].drawRotet2(gl, mGR.mPlayCar[i].x + 0.0f,.09f+ mGR.mPlayCar[i].y, mGR.mPlayCar[i].ang - 90, .25f);
//					if(Counter%(11-mGR.mCarValue[mGR.mPlayCar[i].img].upgred[0]*2)==0)
					setBullate(mGR.mPlayCar[i].x, mGR.mPlayCar[i].vx , .09f + mGR.mPlayCar[i].y, mGR.mPlayCar[i].vy , (int)mGR.mPlayCar[i].ang + 90,mGR.mPlayCar[i].img);
					break;
				case 8:
					DrawTexture(gl, mGR.mTex_Car[mGR.mPlayCar[i].img], mGR.mPlayCar[i].x, mGR.mPlayCar[i].y);
					mGR.mTex_CTyre[2].drawRotet2(gl, mGR.mPlayCar[i].x + 0.05f,-.10f+ mGR.mPlayCar[i].y, -Counter * 5, 0);
					mGR.mTex_CTyre[2].drawRotet2(gl, mGR.mPlayCar[i].x - 0.08f,-.10f+ mGR.mPlayCar[i].y, -Counter * 5, 0);
					mGR.mTex_Gun[3].drawRotet2(gl, mGR.mPlayCar[i].x -.03f,.1f+ mGR.mPlayCar[i].y, mGR.mPlayCar[i].ang - 90, .25f);
					setBullate(mGR.mPlayCar[i].x-.03f, mGR.mPlayCar[i].vx , .1f + mGR.mPlayCar[i].y, mGR.mPlayCar[i].vy , (int)mGR.mPlayCar[i].ang + 90,mGR.mPlayCar[i].img);
					break;
				case 9:
					DrawTexture(gl, mGR.mTex_Car[mGR.mPlayCar[i].img], mGR.mPlayCar[i].x, mGR.mPlayCar[i].y);
					mGR.mTex_CTyre[2].drawRotet2(gl, mGR.mPlayCar[i].x + .08f,-.13f+ mGR.mPlayCar[i].y, -Counter * 5, 0);
					mGR.mTex_CTyre[2].drawRotet2(gl, mGR.mPlayCar[i].x - .08f,-.13f+ mGR.mPlayCar[i].y, -Counter * 5, 0);
					mGR.mTex_Gun[4].drawRotet2(gl, mGR.mPlayCar[i].x -.01f,.14f+ mGR.mPlayCar[i].y, mGR.mPlayCar[i].ang - 90, .25f);
//					if(Counter%(7-mGR.mCarValue[mGR.mPlayCar[i].img].upgred[0])==0)
						setBullate(mGR.mPlayCar[i].x-.01f, mGR.mPlayCar[i].vx , .14f + mGR.mPlayCar[i].y, mGR.mPlayCar[i].vy , (int)mGR.mPlayCar[i].ang + 90,mGR.mPlayCar[i].img);
					break;
				case 10:
					DrawTexture(gl, mGR.mTex_Car[mGR.mPlayCar[i].img], mGR.mPlayCar[i].x, mGR.mPlayCar[i].y);
					mGR.mTex_CTyre[2].drawRotet2(gl, mGR.mPlayCar[i].x + .08f,-.18f+ mGR.mPlayCar[i].y, -Counter * 5, 0);
					mGR.mTex_CTyre[2].drawRotet2(gl, mGR.mPlayCar[i].x - .08f,-.18f+ mGR.mPlayCar[i].y, -Counter * 5, 0);
					mGR.mTex_Gun[1].drawRotet2(gl, mGR.mPlayCar[i].x +0.08f,.06f+ mGR.mPlayCar[i].y, mGR.mPlayCar[i].ang - 90, .25f);
					if(Counter%3==0)
						setBullate(mGR.mPlayCar[i].x+.08f, mGR.mPlayCar[i].vx , .06f + mGR.mPlayCar[i].y, mGR.mPlayCar[i].vy , (int)mGR.mPlayCar[i].ang + 90,mGR.mPlayCar[i].img);
					break;
				case 11:
					DrawTexture(gl, mGR.mTex_Car[mGR.mPlayCar[i].img], mGR.mPlayCar[i].x, mGR.mPlayCar[i].y);
					mGR.mTex_CTyre[2].drawRotet2(gl, mGR.mPlayCar[i].x + .08f,-.13f+ mGR.mPlayCar[i].y, -Counter * 5, 0);
					mGR.mTex_CTyre[2].drawRotet2(gl, mGR.mPlayCar[i].x - .12f,-.13f+ mGR.mPlayCar[i].y, -Counter * 5, 0);
					mGR.mTex_Gun[0].drawRotet2(gl, mGR.mPlayCar[i].x +.02f,.15f+ mGR.mPlayCar[i].y, mGR.mPlayCar[i].ang - 90, .25f);
//					if(Counter%(11-mGR.mCarValue[mGR.mPlayCar[i].img].upgred[0]*2)==0)
						setBullate(mGR.mPlayCar[i].x+.02f, mGR.mPlayCar[i].vx , .15f + mGR.mPlayCar[i].y, mGR.mPlayCar[i].vy , (int)mGR.mPlayCar[i].ang + 90,mGR.mPlayCar[i].img);
					break;
				case 12:
					DrawTexture(gl, mGR.mTex_Car[mGR.mPlayCar[i].img], mGR.mPlayCar[i].x, mGR.mPlayCar[i].y);
					mGR.mTex_CTyre[3].drawRotet2(gl, mGR.mPlayCar[i].x + .08f,-.13f+ mGR.mPlayCar[i].y, -Counter * 5, 0);
					mGR.mTex_CTyre[3].drawRotet2(gl, mGR.mPlayCar[i].x - .12f,-.13f+ mGR.mPlayCar[i].y, -Counter * 5, 0);
					mGR.mTex_Gun[0].drawRotet2(gl, mGR.mPlayCar[i].x +.08f,.15f+ mGR.mPlayCar[i].y, mGR.mPlayCar[i].ang - 90, .25f);
//					if(Counter%(8-mGR.mCarValue[mGR.mPlayCar[i].img].upgred[0])==0)
						setBullate(mGR.mPlayCar[i].x+.08f, mGR.mPlayCar[i].vx , .15f + mGR.mPlayCar[i].y, mGR.mPlayCar[i].vy , (int)mGR.mPlayCar[i].ang + 90,mGR.mPlayCar[i].img);
					break;
				case 13:
					DrawTexture(gl, mGR.mTex_Car[mGR.mPlayCar[i].img], mGR.mPlayCar[i].x, mGR.mPlayCar[i].y);
					mGR.mTex_CTyre[4].drawRotet2(gl, mGR.mPlayCar[i].x +.15f,-.15f+ mGR.mPlayCar[i].y, -Counter * 5, 0);
					mGR.mTex_CTyre[4].drawRotet2(gl, mGR.mPlayCar[i].x -.13f,-.15f+ mGR.mPlayCar[i].y, -Counter * 5, 0);
					mGR.mTex_Gun[4].drawRotet2(gl, mGR.mPlayCar[i].x +.06f,0.15f+ mGR.mPlayCar[i].y, mGR.mPlayCar[i].ang - 90, .25f);
//					if(Counter%(8-mGR.mCarValue[mGR.mPlayCar[i].img].upgred[0])==0)
						setBullate(mGR.mPlayCar[i].x+.06f, mGR.mPlayCar[i].vx , .15f + mGR.mPlayCar[i].y, mGR.mPlayCar[i].vy , (int)mGR.mPlayCar[i].ang + 90,mGR.mPlayCar[i].img);
					break;
				case 14:
					mGR.mTex_Gun[6].drawRotet2(gl, mGR.mPlayCar[i].x -.2f,.05f+ mGR.mPlayCar[i].y, 40, .25f);
					DrawTexture(gl, mGR.mTex_Car[mGR.mPlayCar[i].img], mGR.mPlayCar[i].x, mGR.mPlayCar[i].y);
					mGR.mTex_CTyre[4].drawRotet2(gl, mGR.mPlayCar[i].x +.11f,-.11f+ mGR.mPlayCar[i].y, -Counter * 5, 0);
					mGR.mTex_CTyre[4].drawRotet2(gl, mGR.mPlayCar[i].x -.18f,-.11f+ mGR.mPlayCar[i].y, -Counter * 5, 0);
					mGR.mTex_Gun[4].drawRotet2(gl, mGR.mPlayCar[i].x +0.00f,.21f+ mGR.mPlayCar[i].y, mGR.mPlayCar[i].ang - 90, .25f);
					setBullate(mGR.mPlayCar[i].x+0.01f+mGR.mPlayCar[i].vx, mGR.mPlayCar[i].vx , .21f + mGR.mPlayCar[i].y+mGR.mPlayCar[i].vy, mGR.mPlayCar[i].vy , (int)mGR.mPlayCar[i].ang + 90,mGR.mPlayCar[i].img);
					break;
				}
				if(mGR.mPlayCar[i].shild>0){
					DrawTexture(gl, mGR.mTex_Shiled, mGR.mPlayCar[i].x, mGR.mPlayCar[i].y);
					mGR.mPlayCar[i].shild--;
				}
			}
		}
	}
//sx,sy
	void Draw_Dushman(GL10 gl) {
		for (int i = 0; i < mGR.mDusman.length; i++) {
			if(mGR.mDusman[i].isIn()){
				
				if (mGR.mDusman[i].anim > 0 && Counter	 % 2 == 0)
					mGR.mTex_OppCar[mGR.mDusman[i].img].drawRGBTS(gl, mGR.mDusman[i].x,mGR.mDusman[i].y,0,0,1,1,1,1);
				else
					DrawTexture(gl, mGR.mTex_OppCar[mGR.mDusman[i].img], mGR.mDusman[i].x,mGR.mDusman[i].y);
				switch (mGR.mDusman[i].img) {
				case 0:
					mGR.mTex_OppTyre[0].drawRotet2(gl, mGR.mDusman[i].x + .05f,-.07f + mGR.mDusman[i].y, -Counter * 5, 0);
					mGR.mTex_OppTyre[0].drawRotet2(gl, mGR.mDusman[i].x - .05f,-.07f + mGR.mDusman[i].y, -Counter * 5, 0);
//					if (mGR.mDusman[i].anim > 0 && mGR.mDusman[i].anim % 2 == 0)
//						mGR.mTex_OppCar[0].drawRGBTS(gl, mGR.mDusman[i].x,mGR.mDusman[i].y,0,0,1,1,1,1);
//					else
//						DrawTexture(gl, mGR.mTex_OppCar[0], mGR.mDusman[i].x,mGR.mDusman[i].y);
					mGR.mTex_Hand.drawRotet2(gl, mGR.mDusman[i].x - .02f,0.06f + mGR.mDusman[i].y, mGR.mDusman[i].ang, -.2f);// 180-360
					if (mGR.mDusman[i].ang >= 350 && mGR.mDusman[i].counter % 2 != 0)
						mGR.mTex_Fire[0].drawRotet2(gl, mGR.mDusman[i].x - .09f,0.07f + mGR.mDusman[i].y, 180, 0);
					break;
				case 1:
//					DrawTexture(gl, mGR.mTex_OppCar[1], mGR.mDusman[i].x,mGR.mDusman[i].y);
					if (Counter % 2 != 0)
						mGR.mTex_Fire[0].drawRotet2(gl, mGR.mDusman[i].x - .09f,0.055f + mGR.mDusman[i].y, 180, 0);
					break;
				case 2:
//					DrawTexture(gl, mGR.mTex_OppCar[2], mGR.mDusman[i].x,mGR.mDusman[i].y);
					mGR.mTex_OppTyre[1].drawRotet2(gl, mGR.mDusman[i].x + .08f,-.14f + mGR.mDusman[i].y, -Counter * 5, 0);
					mGR.mTex_OppTyre[1].drawRotet2(gl, mGR.mDusman[i].x - .09f,-.14f + mGR.mDusman[i].y, -Counter * 5, 0);
					break;
				case 3:
//					DrawTexture(gl, mGR.mTex_OppCar[3], mGR.mDusman[i].x,mGR.mDusman[i].y);
					break;
				case 4:
//					DrawTexture(gl, mGR.mTex_OppCar[4], mGR.mDusman[i].x,mGR.mDusman[i].y);
					mGR.mTex_OppTyre[2].drawRotet2(gl, mGR.mDusman[i].x + .14f,-.13f + mGR.mDusman[i].y, -Counter * 5, 0);
					mGR.mTex_OppTyre[2].drawRotet2(gl, mGR.mDusman[i].x - .14f,-.13f + mGR.mDusman[i].y, -Counter * 5, 0);
					break;
				case 5:
//					DrawTexture(gl, mGR.mTex_OppCar[5], mGR.mDusman[i].x,mGR.mDusman[i].y);
					DrawTexture(gl, mGR.mTex_Fan[Counter % 2],mGR.mDusman[i].x + .015f, .15f + mGR.mDusman[i].y);
					if (Counter % 2 != 0)
						mGR.mTex_Fire[1].drawRotet2(gl, mGR.mDusman[i].x - .19f,-.21f + mGR.mDusman[i].y, -150, 0);
					break;
				case 6:
//					DrawTexture(gl, mGR.mTex_OppCar[6], mGR.mDusman[i].x,mGR.mDusman[i].y);
					mGR.mTex_OppTyre[3].drawRotet2(gl, mGR.mDusman[i].x + .09f,-.14f + mGR.mDusman[i].y, -Counter * 5, 0);
					mGR.mTex_OppTyre[3].drawRotet2(gl, mGR.mDusman[i].x - .08f,-.14f + mGR.mDusman[i].y, -Counter * 5, 0);
					break;
				case 7:
//					DrawTexture(gl, mGR.mTex_OppCar[7], mGR.mDusman[i].x,mGR.mDusman[i].y);
					mGR.mTex_OppTyre[4].drawRotet2(gl, mGR.mDusman[i].x + .13f,-.06f + mGR.mDusman[i].y, -Counter * 5, 0);
					mGR.mTex_OppTyre[4].drawRotet2(gl, mGR.mDusman[i].x - .04f,-.06f + mGR.mDusman[i].y, -Counter * 5, 0);
					break;
				case 8:
//					DrawTexture(gl, mGR.mTex_OppCar[8], mGR.mDusman[i].x,mGR.mDusman[i].y);
					mGR.mTex_OppTyre[4].drawRotet2(gl, mGR.mDusman[i].x + .11f,-.11f + mGR.mDusman[i].y, -Counter * 5, 0);
					mGR.mTex_OppTyre[4].drawRotet2(gl, mGR.mDusman[i].x - .06f,-.11f + mGR.mDusman[i].y, -Counter * 5, 0);
					if (Counter % 2 != 0)
						mGR.mTex_Fire[1].drawRotet2(gl, mGR.mDusman[i].x - .19f,.05f + mGR.mDusman[i].y, -180, 0);
					break;
				}
			}
		}
	}

	void Draw_GamePlay(GL10 gl) {
		DrawTextureS(gl, mGR.mTex_Sky, 0, 0, 1.9f);
		for (int i = 0; i < mGR.mDown.length; i++) {
			DrawTexture(gl, mGR.mTex_Road[2], mGR.mDown[i].x, mGR.mDown[i].y);
		}
		for (int i = 0; i < mGR.mCenter.length; i++) {
			DrawTexture(gl, mGR.mTex_Road[1], mGR.mCenter[i].x,mGR.mCenter[i].y);
		}
		for (int i = 0; i < mGR.mTop.length; i++) {
			DrawTexture(gl, mGR.mTex_Road[0], mGR.mTop[i].x, mGR.mTop[i].y);
		}
		if (mGR.topCount == 2) {
			DrawTexture(gl, mGR.mTex_Road[3], mGR.mTop[0].x, mGR.mTop[0].y-.11f);
		}
		Draw_Car(gl);
		Draw_Dushman(gl);
		for (int i = 0; i < mGR.mMissile.length; i++) {
			if (mGR.mMissile[i].update()) {
				if (mGR.mMissile[i].img == 0)
					mGR.mTex_OppMis[0].drawRotet2(gl, mGR.mMissile[i].x,mGR.mMissile[i].y, mGR.mMissile[i].angle, 0);
				else
					DrawTexture(gl, mGR.mTex_OppMis[mGR.mMissile[i].img],mGR.mMissile[i].x, mGR.mMissile[i].y);
			}
		}
		for (int i = 0; i < mGR.mBulltet.length; i++) {
			if (mGR.mBulltet[i].update()) {
				switch (mGR.mBulltet[i].img) {
				case 2:case 3:case 10:
					DrawTexture(gl, mGR.mTex_Bullet[1],mGR.mBulltet[i].x,mGR.mBulltet[i].y);
					break;
				case 5:case 6:case 12:
					mGR.mTex_Bullet[2].drawRotet2(gl, mGR.mBulltet[i].x,mGR.mBulltet[i].y, mGR.mBulltet[i].angle, 0);
					break;
				case 9:case 13:
					mGR.mTex_Bullet[3].drawRotet2(gl, mGR.mBulltet[i].x,mGR.mBulltet[i].y, mGR.mBulltet[i].angle, 0);
					break;
				default:
					mGR.mTex_Bullet[0].drawRotet2(gl, mGR.mBulltet[i].x,mGR.mBulltet[i].y, mGR.mBulltet[i].angle, 0);
					break;
				}
				
				
			}
		}
		for (int i = 0; i < mGR.mBlast.length; i++) {
			if (mGR.mBlast[i].img<15) {
				DrawTexture(gl, mGR.mTex_Blast[mGR.mBlast[i].img], mGR.mBlast[i].x, mGR.mBlast[i].y);
				mGR.mBlast[i].img++;
			}
		}
		for (int i = 0; i < mGR.mPMissile.length; i++) {
			if(mGR.mPMissile[i].img<mGR.mDusman.length){
				if (mGR.mPMissile[i].update(mGR.mDusman[mGR.mPMissile[i].img].x, mGR.mDusman[mGR.mPMissile[i].img].y)) {
					mGR.mTex_Gun[6].drawRotet2(gl, mGR.mPMissile[i].x ,mGR.mPMissile[i].y, (int)Math.toDegrees(mGR.mPMissile[i].radin)-90, .25f);
				}
			}
		}
		for (int i = 0; i < mGR.mSmock.length; i++) {
			if (mGR.mSmock[i].IsIn()) {
				DrawTransScal(gl, mGR.mTex_Smock, mGR.mSmock[i].x, mGR.mSmock[i].y,(1.1f-mGR.mSmock[i].z),mGR.mSmock[i].z);
				mGR.mSmock[i].z-=.1f;
			}
		}
		DrawTexture(gl, mGR.mTex_Meter,0,-.74f);
		mGR.mTex_Fill.drawSS(gl, .22f, -.60f, mGR.distance, 4.5f);//20
		DrawTexture(gl, mGR.mTex_Bar,.59f,-.60f);
		DrawTexture(gl, mGR.mTex_KmBar,.35f,-.77f);
		DrawTexture(gl, mGR.mTex_miles,.28f,-.77f);
		drawNumber(gl, "  "+ (int)(mGR.distance*100), .31f,-.77f, 1, 0,0);
		DrawTexture(gl, mGR.mTex_KmBar,.35f,-.91f);
		drawNumber(gl, ": "+ mGR.mCoin, .25f,-.91f, 1, 0,0);
//		DrawTransScal(gl, mGR.mTex_Icon[M.setValue?1:0]	, .57f,-.84f,mSel==1?1.1f:1,mSel==1?0.5f:1);// Sound
//		DrawTransScal(gl, mGR.mTex_Icon[M.setMusic?5:4]	, .74f,-.84f,mSel==2?1.1f:1,mSel==2?0.5f:1);// Music
		DrawTransScal(gl, mGR.mTex_Icon[7]				, .91f,-.84f,mSel==3?1.1f:1,mSel==3?0.5f:1);// Leader board
		mGR.mTex_Kata.drawRotet2(gl, -.03f, -.98f, mGR.spd*500+Counter%3, -.34f);
		DrawTexture(gl, mGR.mTex_Yellow,-.025f,-.98f);
		
		for(int i=0;i<3;i++){
			if (mGR.mPlayCar[i].iSAlive) {
				switch (mGR.mPlayCar[i].img) {
				default:
					if(mGR.mPlayCar[i].NoBomb>0){
						DrawTransScal(gl, mGR.mTex_Icon[14], -.88f + i * .23f, -.84f,mSel == i+4 ? 1.1f : 1, mSel == i+4 ? 0.5f : (mGR.mPlayCar[i].count>300?(Counter%2):mGR.mPlayCar[i].count*.001f));//
						drawNumber(gl, ""+mGR.mPlayCar[i].NoBomb, -.94f + i * .23f, -.87f	, 1.2f, 0, 0);
					}else{
						DrawTransScal(gl, mGR.mTex_Icon[14], -.88f + i * .23f, -.84f,mSel == i+4 ? 1.1f : 1, mSel == i+4 ? 0.5f : .5f);//
						drawNumber(gl, ""+mGR.mPlayCar[i].NoBomb, -.94f + i * .23f, -.87f	, 1.2f, 0, 0);
					}
					break;
				case 3:
					DrawTransScal(gl, mGR.mTex_Icon[14], -.88f + i * .23f, -.84f,mSel == i+4 ? 1.1f : 1, mSel == i+4 ? 0.5f : (mGR.mPlayCar[i].count%100)*.003f);// 	
					break;
				case 14:
					DrawTransScal(gl, mGR.mTex_Icon[14], -.88f + i * .23f, -.84f,mSel == i+4 ? 1.1f : 1, mSel == i+4 ? 0.5f : (mGR.mPlayCar[i].count%30)*.01f);// 	
					break;
				}
				
				mGR.mTex_KmBar.drawScal(gl, -.88f + i * .23f, -.66f, 0.58f,0.60f);
				mGR.mTex_Red.drawSS(gl, -.95f + i * .23f, -.662f,(mGR.mPlayCar[i].life/mGR.mPlayCar[i].Totallife)*7.5f, 1);
				if(mGR.mPlayCar[i].img%3 ==0){
					mGR.mTex_Gun[6].drawRotet2(gl, -.86f + i * .23f, -.84f, 0, 0);
				}
				if (mGR.mPlayCar[i].img % 3 == 1) {
					DrawTexture(gl, mGR.mTex_Timer, -.86f + i * .23f, -.84f);
				}
				if (mGR.mPlayCar[i].img % 3 == 2) {
					DrawTexture(gl, mGR.mTex_ShopSield, -.86f + i * .23f, -.84f);
				}
			}else{
				DrawTexture(gl, mGR.mTex_Icon[15], -.88f + i * .23f, -.84f);
			}
		}
		gamelogic();
	}

	boolean Handle_Gameplay(MotionEvent event) {
		mSel = 0;
		{
			for (int i = 0; i < mGR.mPlayCar.length; i++) {
				float dx = world2screenX(mGR.mPlayCar[i].x - .02f);
				float dy = world2screenY(mGR.mPlayCar[i].y + .09f);
				double ang = (GetAngle(-(dy - event.getY()),-(dx - event.getX())));
				mGR.mPlayCar[i].setAngle(ang);
			}
		}
//		if(CircRectsOverlap(.57f,-.84f,  mGR.mTex_Icon[11].width()*.2f,  mGR.mTex_Icon[11].Height()*.3f, screen2worldX(event.getX()),screen2worldY(event.getY()), .05f)){
//			mSel = 1;
//		}
//		if(CircRectsOverlap(.74f,-.84f,  mGR.mTex_Icon[11].width()*.2f,  mGR.mTex_Icon[11].Height()*.3f, screen2worldX(event.getX()),screen2worldY(event.getY()), .05f)){
//			mSel = 2;
//		}
		if(CircRectsOverlap(.91f,-.84f,  mGR.mTex_Icon[11].width()*.2f,  mGR.mTex_Icon[11].Height()*.3f, screen2worldX(event.getX()),screen2worldY(event.getY()), .05f)){
			mSel = 3;
		}
		for(int i=0;i<3;i++){
			if(CircRectsOverlap(-.88f + i * .23f, -.84f,  mGR.mTex_Icon[11].width()*.2f,  mGR.mTex_Icon[11].Height()*.3f, screen2worldX(event.getX()),screen2worldY(event.getY()), .05f)){
				mSel = i+4;
			}
		}
		if (MotionEvent.ACTION_UP == event.getAction()) {
			switch (mSel) {
			case 1:
				M.setValue=!M.setValue;
				break;
			case 2:
				M.setMusic=!M.setMusic;
				break;
			case 3:
				M.GameScreen = M.GAMEPAUS;
				M.stop();
				fade = 1;
				break;
			case 4:case 5:case 6:
			{
				int i =mSel -4;
				if (mGR.mPlayCar[i].iSAlive) {
					switch (mGR.mPlayCar[i].img%3) {
					case 0:
						if (mGR.mPlayCar[i].NoBomb > 0 && mGR.mPlayCar[i].img != 3) {
							if(mGR.mPlayCar[i].count>300){
								mGR.mPlayCar[i].NoBomb--;
								mGR.mPlayCar[i].count=0;
								int k=0;
								for(int j=mGR.mPMissile.length-1;j>=0&&k<10;j--){
									if(!mGR.mPMissile[j].IsIn()){
										mGR.mPMissile[j].set(-1+k*.17f, 1.1f,true);
										k++;
									}
								}
								M.sound12(R.drawable.multipal_missile);
							}
						}
						break;
					case 1:
						if (mGR.mPlayCar[i].NoBomb > 0) {
							if(mGR.mPlayCar[i].count>300){
								mGR.mPlayCar[i].NoBomb--;
								mGR.mPlayCar[i].count=0;
								mGR.mPlayCar[i].life+=mGR.mPlayCar[i].Totallife*.2f;
								if(mGR.mPlayCar[i].life>mGR.mPlayCar[i].Totallife)
									mGR.mPlayCar[i].life=mGR.mPlayCar[i].Totallife;
							}
						}
						break;
					case 2:
						if (mGR.mPlayCar[i].NoBomb > 0) {
							if(mGR.mPlayCar[i].count>300){
								mGR.mPlayCar[i].NoBomb--;
								mGR.mPlayCar[i].count=0;
								mGR.mPlayCar[i].shild = 200;
							}
						}
						break;
					}
				}
			
			}
			break;
			}
			mSel = 0;
		}
		return true;
	}

	void DrawTexture(GL10 gl, SimplePlane Tex, float x, float y) {
		Tex.drawPos(gl, x, y);
	}

	void DrawTextureR(GL10 gl, SimplePlane Tex, float x, float y, float angle) {
		Tex.drawRotet(gl, x, y, angle);
	}

	void DrawTextureS(GL10 gl, SimplePlane Tex, float x, float y, float scal) {
		Tex.drawScal(gl, x, y, scal, scal);
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
		return ((a / M.ScreenWidth) - 0.5f) * 2f;
	}

	float screen2worldY(float a) {
		return (0.5f - (a / M.ScreenHieght)) * 2f;
	}

	float world2screenX(float a) {
		return (a + 1f) * M.ScreenWidth * .5f;
	}

	float world2screenY(float a) {
		return (1 - a) * M.ScreenHieght * .5f;
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
	void drawNumber(GL10 gl, String strs, float x, float y,float scal,float color,int aling) {
		float dx = mGR.mTex_Font[0].width()*.7f*scal;
		
		if(aling==1)
			x -= strs.length()*dx*.5f+dx*.5f;
		for (int i = 0; i < strs.length(); i++) {
			int k = ((int) strs.charAt(i))-48;
			if (k >= 0 && k < mGR.mTex_Font.length){
				if(color == 4){
					mGR.mTex_Font[k].drawRGBTS(gl, x + i * dx, y,1,1,1,1,scal+.1f,scal+.1f);
					mGR.mTex_Font[k].drawRGBTS(gl, x + i * dx, y,1,0,0,1,scal-.05f,scal-.05f);
				}
				else if(color == 3){
					mGR.mTex_Font[k].drawRGBTS(gl, x + i * dx, y,0,0,0,1,scal+.1f,scal+.1f);
					mGR.mTex_Font[k].drawRGBTS(gl, x + i * dx, y,1,1,1,1,scal-.05f,scal-.05f);
				}
				else if(color == 2)
					mGR.mTex_Font[k].drawRGBTS(gl, x + i * dx, y,1,1,0,1,scal,scal);
				else if(color == 1)
					mGR.mTex_Font[k].drawRGBTS(gl, x + i * dx, y,1,.55f,0,1,scal,scal);
				else
					mGR.mTex_Font[k].drawRGBTS(gl, x + i * dx, y,1,1,1,1,scal,scal);
			}
		}
	}
	/*void drawNumber(GL10 gl, String strs, float x, float y) {
		float dx = mGR.mTex_Font[0].width()*.7f;
		for (int i = 0; i < strs.length(); i++) {
			int k = ((int) strs.charAt(i));
			if(k > 47 && k <58)
				k -=48; 
			else if(k > 64 && k <91)
				k -=55;
			else if(k > 96 && k <123)
				k -=87;
			else if(k == 58)
				k =37;
			if (k >= 0 && k < mGR.mTex_Font.length)
				mGR.mTex_Font[k].drawPos(gl, x + i * dx, y);
		}
	}*/

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
	void Twitter() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://twitter.com/hututu_games"));
		GameRenderer.mContext.startActivity(mIntent);
	}
	void google() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://plus.google.com/+Hututugames"));
		GameRenderer.mContext.startActivity(mIntent);
	}
	void facebook() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://www.facebook.com/HututuGames"));
		GameRenderer.mContext.startActivity(mIntent);
	}

	void share2friend() {
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("text/plain");
		i.putExtra(
				Intent.EXTRA_SUBJECT,
				"Roking new Game '"
						+ GameRenderer.mContext.getResources().getString(
								R.string.app_name) + "'");
		// i.putExtra(Intent.EXTRA_TEXT,"Let the battle commence!!! Download it now and let’s ROCK!!!!  "+M.PUBLICLINK+getClass().getPackage().getName());
		i.putExtra(Intent.EXTRA_TEXT,
				"Let the battle commence!!! Download it now and let’s ROCK!!!!  "
						+ M.SHARELINK+getClass().getPackage().getName());
		try {
			GameRenderer.mContext.startActivity(Intent.createChooser(i,
					"Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(GameRenderer.mStart,
					"There are no email clients installed.", Toast.LENGTH_SHORT)
					.show();
		}
	}
}
