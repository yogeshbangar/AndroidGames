package com.hututu.games.monkeyjump;

import javax.microedition.khronos.opengles.GL10;
import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.widget.Toast;

public class Group extends Mesh {

	GameRenderer mGR = null;

	int Counter = 0;
	int C2 = 0;
	float sx, sy;
	public void setting(){float ud=.01f;switch (GameRenderer.mStart._keyCode) {case 1:sy-=ud;break;case 2:sy+=ud;break;case 3:sx-=ud;break;case 4:sx+=ud;break;}System.out.println(mGR.mPlayer.distance+"~~~~~~~~~~~~~~~      "+sx+"~~~~~~~~~~~~       "+sy);}
	public boolean Handle(MotionEvent event){
		if(CircRectsOverlap(-.8f,0.0f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 3;
		if(CircRectsOverlap(0.8f,0.0f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 4;
		if(CircRectsOverlap(-.0f,-.8f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 1;
		if(CircRectsOverlap(0.0f,0.8f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 2;
		if(event.getAction()== MotionEvent.ACTION_UP)GameRenderer.mStart._keyCode = 0;
		return true;
	}

	public Group(GameRenderer _GameRenderer) {
		mGR = _GameRenderer;
	}

	@Override
	public void draw(GL10 gl) {
//		M.GameScreen = M.GAMEOPTION;
		switch (M.GameScreen) {
		case M.GAMELOGO:
			DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if(Counter > 60){
				M.GameScreen = M.GAMEMENU;
				M.sound17(GameRenderer.mContext, R.drawable.start);
//				GameRenderer.mStart.init();
			}
			break;
		case M.GAMEABOUT:
		case M.GAMEHELP:
			Draw_About(gl);
			break;
		case M.GAMEBUY:
			Draw_Buy(gl);
			break;
		case M.GAMEOVER:
			Draw_GameOver(gl);
			break;
		case M.GAMEPAUSE:
			Draw_Pause(gl);
			break;
		case M.GAMEOPTION:
			Draw_Option(gl);
			break;
		case M.GAMEMENU:
			Draw_Menu(gl);
			break;
		case M.GAMEPLAY:
			Draw_Gameplay(gl);
			break;
		}
		Counter++;
//		setting();
	}

	public boolean TouchEvent(MotionEvent event) {

		switch (M.GameScreen) {
		case M.GAMELOGO:
			break;
		case M.GAMEABOUT:
		case M.GAMEHELP:
			Handle_About(event);
			break;
		case M.GAMEBUY:
			Handle_Buy(event);
			break;
		case M.GAMEOVER:
			Handle_GameOver(event);
			break;
		case M.GAMEPAUSE:
			Handle_Pause(event);
			break;
		case M.GAMEOPTION:
			Handle_Option(event);
			break;
		case M.GAMEMENU:
			Handle_Menu(event);
			break;
		case M.GAMEPLAY:
			Handle_Gameplay(event);
			break;
		}
		Handle(event);
		return true;
	}

	/*************************About Start***********************************/
	void Draw_About(GL10 gl){
		DrawTexture(gl, mGR.mTex_BG, 0, 0);
		for(int i =0; i<9;i++){
			DrawTexture(gl, mGR.mTex_Tile[i%3 == 0?1:0],-(1-mGR.mTex_Tile[0].width()*.5f), -1+mGR.mTex_Tile[0].Height()*i);
			DrawTextureR(gl, mGR.mTex_Tile[i%3 == 0?1:0], (1-mGR.mTex_Tile[0].width()*.5f), -1+mGR.mTex_Tile[0].Height()*i,180,false);
		}
		DrawTexture(gl, mGR.mTex_CBG, 0, 0);
		if(M.GameScreen == M.GAMEHELP){
			DrawTexture(gl, mGR.mTex_Help, 0, 0);
			DrawTransScal(gl, mGR.mTex_Back[0], -.82f,.89f,mGR.mSel == 1?1.1f:1,mGR.mSel == 1?.5f:1);
		}else{
			DrawTexture(gl, mGR.mTex_About, 0, 0);
			DrawTransScal(gl, mGR.mTex_Back[0], -.56f, 0.75f,mGR.mSel == 1?1.1f:1,mGR.mSel == 1?.5f:1);
		}
		
	}
	boolean Handle_About(MotionEvent event){
		mGR.mSel = 0;
		if(CircRectsOverlap(-.6f, 0.8f, mGR.mTex_Back[0].width()*.6f, mGR.mTex_Back[0].Height()*.6f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05f)){
			mGR.mSel = 1;
		}
		if(MotionEvent.ACTION_UP == event.getAction()&& mGR.mSel == 1){
			M.GameScreen = M.GAMEOPTION;
			if(mGR.mSel != 0)
				M.sound6(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	/*************************About End*************************************/
	/*************************Menu Start***********************************/
	void Draw_Menu(GL10 gl){
		DrawTexture(gl, mGR.mTex_Splash, 0, 0);
		DrawTransScal(gl, mGR.mTex_Play, .64f, -.32f,mGR.mSel == 1?1.1f:1,mGR.mSel == 1?.5f:1);
		DrawTransScal(gl, mGR.mTex_OptiontBtn, .64f, -.55f,mGR.mSel == 2?1.1f:1,mGR.mSel == 2?.5f:1);
		
	}
	boolean Handle_Menu(MotionEvent event){
		mGR.mSel = 0;
		if(CircRectsOverlap(.64f, -.32f, mGR.mTex_Back[0].width()*.5f, mGR.mTex_Back[0].Height()*.5f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05f)){
			mGR.mSel = 1;
		}
		if(CircRectsOverlap(.64f, -.55f, mGR.mTex_Back[0].width()*.5f, mGR.mTex_Back[0].Height()*.5f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05f)){
			mGR.mSel = 2;
		}
		if(MotionEvent.ACTION_UP == event.getAction()){
			mGR.fromGame = false;
			switch (mGR.mSel) {
			case 1:
				mGR.gameReset();
				M.GameScreen = M.GAMEPLAY;
				M.play(GameRenderer.mContext, R.drawable.gamplay);
				break;
			case 2:
				M.GameScreen = M.GAMEOPTION;
				break;
			}
			M.sound17Pause();
			if(mGR.mSel != 0)
				M.sound6(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	/*************************About End*************************************/
	/*************************Option Start***********************************/
	void Draw_Option(GL10 gl){
		DrawTexture(gl, mGR.mTex_BG, 0, 0);
		for(int i =0; i<9;i++){
			DrawTexture(gl, mGR.mTex_Tile[i%3 == 0?1:0],-(1-mGR.mTex_Tile[0].width()*.5f), -1+mGR.mTex_Tile[0].Height()*i);
			DrawTextureR(gl, mGR.mTex_Tile[i%3 == 0?1:0], (1-mGR.mTex_Tile[0].width()*.5f), -1+mGR.mTex_Tile[0].Height()*i,180,false);
		}
		DrawTexture(gl, mGR.mTex_CBG, 0, 0.08f);
		DrawTexture(gl, mGR.mTex_Option, 0, .65f);
		
		DrawTransScal(gl, mGR.mTex_Sound[0], -.16f, .40f,mGR.mSel == 1?1.1f:1,mGR.mSel == 1?.5f:1);
		DrawTransScal(gl, mGR.mTex_Sound[M.setValue?2:1], .38f, .40f,mGR.mSel == 1?1.1f:1,mGR.mSel == 1?.5f:1);
		
		DrawTransScal(gl, mGR.mTex_Music[0], -.16f, .15f,mGR.mSel == 2?1.1f:1,mGR.mSel == 2?.5f:1);
		DrawTransScal(gl, mGR.mTex_Music[M.setMusic?2:1], .38f, .15f,mGR.mSel == 2?1.1f:1,mGR.mSel == 2?.5f:1);
//		
		DrawTransScal(gl, mGR.mTex_how2play, 0, -.10f,mGR.mSel == 3?1.1f:1,mGR.mSel == 3?.5f:1);
		DrawTransScal(gl, mGR.mTex_Aboutus , 0, -.35f,mGR.mSel == 4?1.1f:1,mGR.mSel == 4?.5f:1);
		DrawTransScal(gl, mGR.mTex_BuyPower, 0, -.60f,mGR.mSel == 5?1.1f:1,mGR.mSel == 5?.5f:1);
		DrawTransScal(gl, mGR.mTex_Back[0],-.6f, .82f,mGR.mSel == 6?1.1f:1,mGR.mSel == 6?.5f:1);
		
	}
	boolean Handle_Option(MotionEvent event){
		mGR.mSel = 0;
		if(CircRectsOverlap(-.0f, 0.40f, mGR.mTex_how2play.width()*.5f, mGR.mTex_how2play.Height()*.3f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05f)){
			mGR.mSel = 1;
		}
		if(CircRectsOverlap(-.0f, 0.15f, mGR.mTex_how2play.width()*.5f, mGR.mTex_how2play.Height()*.3f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05f)){
			mGR.mSel = 2;
		}
		if(CircRectsOverlap(-.0f, -.10f, mGR.mTex_how2play.width()*.5f, mGR.mTex_how2play.Height()*.3f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05f)){
			mGR.mSel = 3;
		}
		if(CircRectsOverlap(-.0f, -.35f, mGR.mTex_how2play.width()*.5f, mGR.mTex_how2play.Height()*.3f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05f)){
			mGR.mSel = 4;
		}
		if(CircRectsOverlap(-.0f, -.60f, mGR.mTex_how2play.width()*.5f, mGR.mTex_how2play.Height()*.3f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05f)){
			mGR.mSel = 5;
		}
		if(CircRectsOverlap(-.6f, 0.82f, mGR.mTex_Back[0].width()*.5f, mGR.mTex_Back[0].Height()*.3f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05f)){
			mGR.mSel = 6;
		}
		if(MotionEvent.ACTION_UP == event.getAction()){
			mGR.fromGame = false;
			switch (mGR.mSel) {
			case 1:
				M.setValue = !M.setValue;
				break;
			case 2:
				M.setMusic = !M.setMusic;
				break;
			case 3:
				M.GameScreen = M.GAMEHELP;
				break;
			case 4:
				M.GameScreen = M.GAMEABOUT;
				break;
			case 5:
				M.GameScreen = M.GAMEBUY;
				break;
			case 6:
				M.GameScreen = M.GAMEMENU;
				M.sound17(GameRenderer.mContext, R.drawable.start);
				break;
			}
			if(mGR.mSel != 0)
				M.sound6(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	/*************************Option End*************************************/
	/*************************Pause Start***********************************/
	void Draw_Pause(GL10 gl){
		DrawTexture(gl, mGR.mTex_BG, 0, 0);
		for(int i =0; i<9;i++){
			DrawTexture(gl, mGR.mTex_Tile[i%3 == 0?1:0],-(1-mGR.mTex_Tile[0].width()*.5f), -1+mGR.mTex_Tile[0].Height()*i);
			DrawTextureR(gl, mGR.mTex_Tile[i%3 == 0?1:0], (1-mGR.mTex_Tile[0].width()*.5f), -1+mGR.mTex_Tile[0].Height()*i,180,false);
		}
		DrawTexture(gl, mGR.mTex_CBG, 0, .08f);
		
		DrawTexture(gl, mGR.mTex_Pause, 0.06f, .80f);

		DrawTexture(gl, mGR.mTex_score, 0, .60f);
		DrawTexture(gl, mGR.mTex_SBar, 0, .48f);
		drawNumber(gl, ""+((int)(mGR.mPlayer.distance*10)), -.08f, 0.48f, 1);
		
		DrawTransScal(gl, mGR.mTex_continue	,0.00f,0.25f,mGR.mSel == 1?1.1f:1,mGR.mSel == 1?.5f:1);
		DrawTransScal(gl, mGR.mTex_menu 	,0.00f,-.03f,mGR.mSel == 2?1.1f:1,mGR.mSel == 2?.5f:1);
		DrawTransScal(gl, mGR.mTex_Power_up ,0.00f,-.31f,mGR.mSel == 3?1.1f:1,mGR.mSel == 3?.5f:1);
		DrawTransScal(gl, mGR.mTex_Leader	,-.32f,-.64f,mGR.mSel == 4?1.1f:1,mGR.mSel == 4?.5f:1);
		DrawTransScal(gl, mGR.mTex_Achiev	,0.32f,-.64f,mGR.mSel == 5?1.1f:1,mGR.mSel == 5?.5f:1);
		
		
	}
	boolean Handle_Pause(MotionEvent event){
		mGR.mSel = 0;
		if(CircRectsOverlap(0.00f,0.25f, mGR.mTex_Power_up.width()*.5f, mGR.mTex_Power_up.Height()*.3f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05f)){
			mGR.mSel = 1;//Continue
		}
		if(CircRectsOverlap(0.00f,-.03f, mGR.mTex_Power_up.width()*.4f, mGR.mTex_Power_up.Height()*.3f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05f)){
			mGR.mSel = 2;//Menu
		}
		if(CircRectsOverlap(0.00f,-.31f, mGR.mTex_Power_up.width()*.4f, mGR.mTex_Power_up.Height()*.3f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05f)){
			mGR.mSel = 3;//Power Up
		}
		if(CircRectsOverlap(-.32f,-.64f, mGR.mTex_Leader.width()*.4f, mGR.mTex_Leader.Height()*.3f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05f)){
			mGR.mSel = 4;//Leader Board
		}
		if(CircRectsOverlap(0.32f,-.64f, mGR.mTex_Leader.width()*.4f, mGR.mTex_Leader.Height()*.3f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05f)){
			mGR.mSel = 5;//Achievement
		}
		if(MotionEvent.ACTION_UP == event.getAction()){
			switch (mGR.mSel) {
			case 1:
				M.GameScreen = M.GAMEPLAY;
				M.play(GameRenderer.mContext, R.drawable.gamplay);
				break;
			case 2:
				M.GameScreen = M.GAMEMENU;
				M.sound17(GameRenderer.mContext, R.drawable.start);
				break;
			case 3:
				M.GameScreen = M.GAMEBUY;
				break;
			case 4:
				GameRenderer.mStart.onShowLeaderboardsRequested();//Leader-Board
				break;
			case 5:
				GameRenderer.mStart.onShowAchievementsRequested();//Achievement
				break;
			
			
			}
			if(mGR.mSel != 0)
				M.sound6(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	/*************************Pause End*************************************/
	
	
	/*************************GameOver Start***********************************/
	void Draw_GameOver(GL10 gl){
		if(Counter == 100){
			M.play(GameRenderer.mContext, R.drawable.gameover_1);
		}
		DrawTexture(gl, mGR.mTex_BG, 0, 0);
		for(int i =0; i<9;i++){
			DrawTexture(gl, mGR.mTex_Tile[i%3 == 0?1:0],-(1-mGR.mTex_Tile[0].width()*.5f), -1+mGR.mTex_Tile[0].Height()*i);
			DrawTextureR(gl, mGR.mTex_Tile[i%3 == 0?1:0], (1-mGR.mTex_Tile[0].width()*.5f), -1+mGR.mTex_Tile[0].Height()*i,180,false);
		}
		DrawTexture(gl, mGR.mTex_CBG, 0, .08f);
		
		DrawTexture(gl, mGR.mTex_gameover, 0.06f, .80f);
		DrawTexture(gl, mGR.mTex_highscore, 0, .60f);
		DrawTexture(gl, mGR.mTex_SBar, 0, .48f);
		DrawTexture(gl, mGR.mTex_Bestscore, 0, .35f);
		DrawTexture(gl, mGR.mTex_SBar, 0, .22f);
		
		drawNumber(gl, ""+((int)(mGR.mPlayer.distance*10)), 0.02f, 0.48f, 1);
		drawNumber(gl, ""+((int)(mGR.mPlayer.Bestdistance*10)), 0.02f, 0.22f, 1);
		
		
		if (GameRenderer.mStart.isSignedIn()) {
			DrawTransScal(gl, mGR.mTex_Leader, -.32f, -.00f,mGR.mSel == 1?1.1f:1,mGR.mSel == 1?.5f:1);
			DrawTransScal(gl, mGR.mTex_Achiev, .32f, -.00f,mGR.mSel == 2?1.1f:1,mGR.mSel == 2?.5f:1);
		} else
			DrawTransScal(gl, mGR.mTex_join, 0, -.00f,mGR.mSel == 3?1.1f:1,mGR.mSel == 3?.5f:1);
			
		DrawTransScal(gl, mGR.mTex_Power_up , 0, -.25f,mGR.mSel == 4?1.1f:1,mGR.mSel == 4?.5f:1);
		DrawTransScal(gl, mGR.mTex_JumpAgain, 0, -.47f,mGR.mSel == 5?1.1f:1,mGR.mSel == 5?.5f:1);
		
		DrawTransScal(gl, mGR.mTex_ratus, -.5f, -.70f,mGR.mSel == 6?1.1f:1,mGR.mSel == 6?.5f:1);
		DrawTransScal(gl, mGR.mTex_More	, 0.0f, -.70f,mGR.mSel == 7?1.1f:1,mGR.mSel == 7?.5f:1);
		DrawTransScal(gl, mGR.mTex_fb	, 0.5f, -.70f,mGR.mSel == 8?1.1f:1,mGR.mSel == 8?.5f:1);
		
		DrawTransScal(gl, mGR.mTex_Back[0], -.59f, .83f,mGR.mSel == 9?1.1f:1,mGR.mSel == 9?.5f:1);
		
	}
	boolean Handle_GameOver(MotionEvent event){
		mGR.mSel = 0;
		if (GameRenderer.mStart.isSignedIn()){
			if(CircRectsOverlap(-.32f, -.00f, mGR.mTex_Leader.width()*.5f, mGR.mTex_Leader.Height()*.5f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05f)){
				mGR.mSel = 1;//Leader board
			}
			if(CircRectsOverlap( .32f, -.00f, mGR.mTex_Leader.width()*.5f, mGR.mTex_Leader.Height()*.5f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05f)){
				mGR.mSel = 2;//Achievement
			}
		}else{
			if(CircRectsOverlap(0, -.00f, mGR.mTex_join.width()*.3f, mGR.mTex_join.Height()*.45f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05f)){
				mGR.mSel = 3;//Join
			}
		}
		if(CircRectsOverlap(-.0f, -.25f, mGR.mTex_Power_up.width()*.5f, mGR.mTex_Power_up.Height()*.5f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05f)){
			mGR.mSel = 4;//Power Up
		}
		if(CircRectsOverlap(-.0f, -.47f, mGR.mTex_JumpAgain.width()*.5f, mGR.mTex_JumpAgain.Height()*.5f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05f)){
			mGR.mSel = 5;//Jump Again
		}
		if(CircRectsOverlap(-.5f, -.70f, mGR.mTex_Leader.width()*.4f, mGR.mTex_Leader.Height()*.3f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05f)){
			mGR.mSel = 6;//Rate Us
		}
		if(CircRectsOverlap(-.0f, -.70f, mGR.mTex_Leader.width()*.4f, mGR.mTex_Leader.Height()*.3f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05f)){
			mGR.mSel = 7;//More
		}
		if(CircRectsOverlap(0.5f, -.70f, mGR.mTex_Leader.width()*.4f, mGR.mTex_Leader.Height()*.3f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05f)){
			mGR.mSel = 8;//Facebook
		}
		if(CircRectsOverlap(-.59f, .83f, mGR.mTex_Leader.width()*.4f, mGR.mTex_Leader.Height()*.3f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05f)){
			mGR.mSel = 9;//Menu
		}
		if(MotionEvent.ACTION_UP == event.getAction()){
			switch (mGR.mSel) {
			case 1:
				GameRenderer.mStart.onShowLeaderboardsRequested();//Leader-Board
				break;
			case 2:
				GameRenderer.mStart.onShowAchievementsRequested();//Achievement
				break;
			case 3:
				GameRenderer.mStart.beginUserInitiatedSignIn();//Join
				break;
			case 4:
				M.GameScreen = M.GAMEBUY;
				M.stop();
				break;
			case 5:
				M.stop();
				mGR.gameReset();
				M.GameScreen = M.GAMEPLAY;
				M.play(GameRenderer.mContext, R.drawable.gamplay);
				break;
			case 6:
				RateUs();
				break;
			case 7:
				MoreGame();
				break;
			case 8:
				facebook();
				break;
			case 9:
				M.stop();
				M.GameScreen = M.GAMEMENU;
				M.sound17(GameRenderer.mContext, R.drawable.start);
				break;
			}
			if(mGR.mSel != 0)
				M.sound6(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	/*************************GameOver End*************************************/
	/*************************Buy Start***********************************/
	void Draw_Buy(GL10 gl){
		DrawTexture(gl, mGR.mTex_BG, 0, 0);
		for(int i =0; i<9;i++){
			DrawTexture(gl, mGR.mTex_Tile[i%3 == 0?1:0],-(1-mGR.mTex_Tile[0].width()*.5f), -1+mGR.mTex_Tile[0].Height()*i);
			DrawTextureR(gl, mGR.mTex_Tile[i%3 == 0?1:0], (1-mGR.mTex_Tile[0].width()*.5f), -1+mGR.mTex_Tile[0].Height()*i,180,false);
		}
		DrawTexture(gl, mGR.mTex_CBG, 0, 0);
		DrawTexture(gl, mGR.mTex_AnyPur, .19f, .76f);
		for(int i =0; i<4;i++){
			DrawTexture(gl, mGR.mTex_BuyBar,0, .51f-.28f*i);
			DrawTransScal(gl, mGR.mTex_BuyBtn,.56f, .51f-.28f*i,mGR.mSel == i+2?1.1f:1,mGR.mSel == i+2?.5f:1);
		}
		drawNumber(gl, "25   :0<99", -.06f, .51f-.28f*0,1);
		drawNumber(gl, "75   :1<99", -.06f, .51f-.28f*1,1);
		drawNumber(gl, "150  :2<99", -.08f, .51f-.28f*2,1);
		drawNumber(gl, "300  :3<99", -.08f, .51f-.28f*3,1);
		
		DrawTransScal(gl, mGR.mTex_Back[1], -.6f, .76f,mGR.mSel == 1?1.1f:1,mGR.mSel == 1?.5f:1);
		
		DrawTexture(gl, mGR.mTex_free,0, -.6f);
		
	}
	boolean Handle_Buy(MotionEvent event){
		mGR.mSel = 0;
		if(CircRectsOverlap(-.56f, 0.75f, mGR.mTex_Back[0].width()*.5f, mGR.mTex_Back[0].Height()*.5f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05f)){
			mGR.mSel = 1;
		}
		for(int i =0; i<4;i++){
			if(CircRectsOverlap(.56f, .51f-.28f*i, mGR.mTex_BuyBtn.width()*.5f, mGR.mTex_BuyBtn.Height()*.5f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05f)){
				mGR.mSel = i+2;
			}
		}
		if(MotionEvent.ACTION_UP == event.getAction()){
			switch (mGR.mSel ) {
			case 1:
				if(mGR.fromGame)
					M.GameScreen = M.GAMEPLAY;
				else
					M.GameScreen = M.GAMEOPTION;
				break;
			case 2:
				mGR.mInApp.onBuyGold10000(null);
				break;
			case 3:
				mGR.mInApp.onBuyGold20000(null);
				break;
			case 4:
				mGR.mInApp.onBuyGold30000(null);
				break;
			case 5:
				mGR.mInApp.onBuyGold40000(null);
				break;
			}
			if(mGR.mSel != 0)
				M.sound6(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	/*************************Buy End*************************************/
	/*************************Game play Start***********************************/
	void setAnimFal(float _x,float _y ,int no,int img){
		for (int i = 0, k = 0; i < mGR.mAnim.length && k < no; i++) {
			if(!mGR.mAnim[i].isIn()){
				mGR.mAnim[i].setFal(_x, _y,img);
				k++;
			}
		}
	}
	
	void setAnim(float _x,float _y ,int no,int img){
		for (int i = 0, k = 0; i < mGR.mAnim.length && k < no; i++) {
			if(!mGR.mAnim[i].isIn()){
				switch (img) {
				case 0:
					mGR.mAnim[i].setOne(_x, _y);
					break;
				case 20:
					mGR.mAnim[i].set(_x, _y,img+M.mRand.nextInt(6));
					break;
				default:
					mGR.mAnim[i].set(_x, _y,img);
					break;
				}
				
				k++;
			}
		}
	}
	void gameover(int type,int i){
		switch (type) {
		case 0://Tile
			if (mGR.mPlayer.Actoin > 0) {
				setAnim(mGR.mPlayer.x, mGR.mPlayer.y, 30, 1);
				mGR.mTile[i].hardle = 0;
			} else {
				if(mGR.mPlayer.Power){
					setAnim(mGR.mPlayer.x, mGR.mPlayer.y, 30, 1);
					mGR.mTile[i].hardle = 0;
					mGR.mPlayer.Power= false;
				}else{
					mGR.mPlayer.speed = -.03f;
					mGR.mPlayer.vx = .05f;
				}
			}
			M.sound11(GameRenderer.mContext, R.drawable.maar_tile);			
			break;
		case 1:
		case 4:// Roket:Sudarshan
			if (mGR.mPlayer.Actoin > 0 || mGR.mPlayer.vx != 0) {
				setAnim(mGR.mPlayer.x, mGR.mPlayer.y, 30,mGR.mSudarshan[i].isRokect ? 4 : 3);
				if(mGR.mPlayer.Actoin <= 0)
					mGR.mPlayer.setOpp(mGR.mSudarshan[i].isRokect ? 4 : 1);
					mGR.mSudarshan[i].set(0, -10, 0, 0, false);
			} else {
				if(mGR.mPlayer.Power){
					setAnim(mGR.mPlayer.x, mGR.mPlayer.y, 30,mGR.mSudarshan[i].isRokect ? 4 : 3);
					mGR.mPlayer.setOpp(0);
					mGR.mSudarshan[i].set(0, -10, 0, 0, false);
					mGR.mPlayer.Power= false;
				}else{
					mGR.mPlayer.speed = -.03f;
					mGR.mPlayer.vx = .05f;
					mGR.mSudarshan[i].set(0, -10, 0, 0, false);
				}
			}
			if(mGR.mSudarshan[i].isRokect)
				M.sound12(GameRenderer.mContext, R.drawable.maar_rock);
			else
				M.sound16(GameRenderer.mContext, R.drawable.maar_sudarshan);

			break;
		case 2://Gilhari
			if(mGR.mPlayer.Actoin > 0 || mGR.mPlayer.vx !=0){
				setAnim(mGR.mPlayer.x, mGR.mPlayer.y, 30, 1);
				if(mGR.mPlayer.Actoin <= 0)
					mGR.mPlayer.setOpp(2);
					mGR.mGilhari[i].isgildhari = false;
			} else {
				if(mGR.mPlayer.Power){
					setAnim(mGR.mPlayer.x, mGR.mPlayer.y, 30, 1);
					mGR.mPlayer.setOpp(0);
					mGR.mGilhari[i].isgildhari = false;
					mGR.mPlayer.Power= false;
				}else{
					setAnim(mGR.mPlayer.x, mGR.mPlayer.y, 30, 1);
					mGR.mGilhari[i].isgildhari = false;
					mGR.mPlayer.speed = -.03f;
					mGR.mPlayer.vx = .05f;
				}
			}
			M.sound13(GameRenderer.mContext, R.drawable.maar_gilahari);
			break;
		case 3: //chidiya
			if (mGR.mPlayer.Actoin > 0 || mGR.mPlayer.vx != 0) {
				setAnim(mGR.mPlayer.x, mGR.mPlayer.y, 30, 2);
				if(mGR.mPlayer.Actoin <= 0)
					mGR.mPlayer.setOpp(3);
					mGR.mChidia[i].set(0, -10);
			} else {
				if(mGR.mPlayer.Power){
					setAnim(mGR.mPlayer.x, mGR.mPlayer.y, 30, 2);
					mGR.mPlayer.setOpp(0);
					mGR.mChidia[i].set(0, -10);
					mGR.mPlayer.Power= false;
				}else{
					mGR.mPlayer.speed = -.03f;
					mGR.mPlayer.vx = .05f;
				}
			}
			M.sound14(GameRenderer.mContext, R.drawable.maar_chidiya);
			break;
		case 5://Villain
			if(mGR.mPlayer.Actoin > 0 || mGR.mPlayer.vx !=0){
				setAnim(mGR.mPlayer.x, mGR.mPlayer.y, 30, 1);
				mGR.mVillain[i].set(0, -10);
			} else {
				if(mGR.mPlayer.Power){
					setAnim(mGR.mPlayer.x, mGR.mPlayer.y, 30, 1);
					mGR.mVillain[i].set(0, -10);
					mGR.mPlayer.setOpp(0);
					mGR.mPlayer.Power= false;
				}else{
					mGR.mPlayer.speed = -.03f;
					mGR.mPlayer.vx = .05f;
				}
			}
			M.sound15(GameRenderer.mContext, R.drawable.maar_villan);
			break;
		case 6://ChakraThrough
			if (mGR.mPlayer.Actoin > 0) {
				setAnim(mGR.mPlayer.x, mGR.mPlayer.y, 30, 5);
				mGR.mChakraTrow[i].set(0, -10);
			} else {
				if(mGR.mPlayer.Power){
					setAnim(mGR.mPlayer.x, mGR.mPlayer.y, 30, 1);
					mGR.mChakraTrow[i].set(0, -10);
					mGR.mPlayer.setOpp(0);
					mGR.mPlayer.Power= false;
				}else{
					mGR.mPlayer.speed = -.03f;
					mGR.mPlayer.vx = .05f;
				}
			}
			M.sound16(GameRenderer.mContext, R.drawable.maar_sudarshan);
			break;
		}
		if(mGR.mPlayer.speed < 0){
			M.stop();
			M.sound7(GameRenderer.mContext, R.drawable.gira);
		}
		GameRenderer.mStart.Achivement();
	}
	void gamelogic(){
		if(mGR.mPlayer.Actoin == 0 && mGR.mPlayer.vx == 0){
			M.sound8(GameRenderer.mContext, R.drawable.chala);
		}else{
			M.sound8Pause();
		}
		if(mGR.mPlayer.speed < 0 && mGR.mPlayer.cout > 40){
			M.GameScreen = M.GAMEOVER;
			M.sound1(GameRenderer.mContext, R.drawable.gameover);
			Counter = 0;
			GameRenderer.mStart.ShowInterstitial();
		}
		for(int i =0;i<4;i++){
			if(mGR.BGy[i] > -2)
				mGR.BGy[i]-=mGR.mPlayer.speed*i*.001f;
		}
		if(mGR.BGy[4] > -2)
			mGR.BGy[4]-= mGR.mPlayer.speed;
		
//		setAnim(mGR.mPlayer.x, mGR.mPlayer.y, 1,0);
		if(mGR.mPlayer.opp > 0 && mGR.mPlayer.speed>0 && mGR.mPlayer.opp != 4 && mGR.mPlayer.vx==0){
			setAnim(mGR.mPlayer.x, mGR.mPlayer.y, 1,0);
		}
		if(mGR.mPlayer.Actoin > 0 && mGR.mPlayer.opp != 4){
			setAnimFal(mGR.mPlayer.x, mGR.mPlayer.y, 1,0);
			setAnimFal(mGR.mPlayer.x, mGR.mPlayer.y, 1,0);
		}
		if(mGR.mPlayer.speed >0){
			mGR.mPlayer.distance+=mGR.mPlayer.speed;
			mGR.mPlayer.TDistance+=mGR.mPlayer.speed;
		}		
		if(mGR.mPlayer.distance > mGR.mPlayer.Bestdistance){
			mGR.mPlayer.Bestdistance = mGR.mPlayer.distance;
			if(mGR.mPlayer.NewScore == 0){
				mGR.mPlayer.NewScore =1;
			}
		}
		if(Counter%2==0)
			C2++;
		mGR.mPlayer.update();
		for(int i =0; i<mGR.mTile.length;i++){
			mGR.mTile[i].y-=mGR.mPlayer.speed;
		}
		for(int i =0; i<mGR.mTile.length;i++){
			if(mGR.mTile[i].hardle>0 && mGR.mTile[i].hardle<5){
				if(CircRectsOverlap(-(mGR.mTile[i].x-.20f), mGR.mTile[i].y, mGR.mTex_Gilhari[0][0].width()*.4f, mGR.mTex_Gilhari[0][0].Height()*.4f, mGR.mPlayer.x, mGR.mPlayer.y, mGR.mTex_Monkey[0][0].Height()*.4f)){
					gameover(0,i);
				}
			}
			if(mGR.mTile[i].hardle>4 && mGR.mTile[i].hardle<9){
				if(CircRectsOverlap((mGR.mTile[i].x-.20f), mGR.mTile[i].y, mGR.mTex_Hardle[0][0].width()*.3f, mGR.mTex_Hardle[0][0].Height()*.3f,
						mGR.mPlayer.x, mGR.mPlayer.y, mGR.mTex_Monkey[0][0].Height()*.4f)){
					gameover(0,i);
				}
			}
		}
		if (mGR.mPower.update(mGR.mPlayer.speed)) {
			if (CircRectsOverlap(mGR.mPower.x, mGR.mPower.y,
					mGR.mTex_Shild[0].width() * .4f,
					mGR.mTex_Shild[0].Height() * .4f, mGR.mPlayer.x,
					mGR.mPlayer.y, mGR.mTex_Monkey[0][0].Height() * .4f)) {
				mGR.mPower.set(0, -10);
				mGR.mPlayer.Power = true;
				M.sound22(GameRenderer.mContext, R.drawable.kavach);
			}
		}
		for(int i =0; i<mGR.mGilhari.length;i++){
			if(mGR.mGilhari[i].update(mGR.mPlayer.speed)){
				if(CircRectsOverlap(mGR.mGilhari[i].x, mGR.mGilhari[i].y, mGR.mTex_Gilhari[0][0].width()*.4f, mGR.mTex_Gilhari[0][0].Height()*.4f, mGR.mPlayer.x, mGR.mPlayer.y, mGR.mTex_Monkey[0][0].Height()*.4f)){
					gameover(2,i);
				}
			}
		}
		for(int i =0; i<mGR.mChidia.length;i++){
			if(mGR.mChidia[i].update(mGR.mPlayer.speed)){
				if(CircRectsOverlap(mGR.mChidia[i].x, mGR.mChidia[i].y, mGR.mTex_Chidiya[0][0].width()*.4f, mGR.mTex_Chidiya[0][0].Height()*.4f, mGR.mPlayer.x, mGR.mPlayer.y, mGR.mTex_Monkey[0][0].Height()*.4f)){
					gameover(3,i);
				}
			}
		}
		for(int i =0; i<mGR.mSudarshan.length;i++){
			if(mGR.mSudarshan[i].update(mGR.mPlayer.speed)){
				if(mGR.mSudarshan[i].isRokect)
					setAnim(mGR.mSudarshan[i].x, mGR.mSudarshan[i].y+.09f, 1,10);
				if(CircRectsOverlap(mGR.mSudarshan[i].x, mGR.mSudarshan[i].y, mGR.mTex_Bomb[1].width()*.4f, mGR.mTex_Bomb[1].Height()*.4f, mGR.mPlayer.x, mGR.mPlayer.y, mGR.mTex_Monkey[0][0].Height()*.4f)){
					gameover(mGR.mSudarshan[i].isRokect ? 4: 1,i);//4:1
				}
			}
		}
		
		for(int i =0; i<mGR.mVillain.length;i++){
			if(mGR.mVillain[i].update(mGR.mPlayer.speed)){
				if(CircRectsOverlap(mGR.mVillain[i].x, mGR.mVillain[i].y, mGR.mTex_Chidiya[0][0].width()*.4f, mGR.mTex_Chidiya[0][0].Height()*.4f, mGR.mPlayer.x, mGR.mPlayer.y, mGR.mTex_Monkey[0][0].Height()*.4f)){
					gameover(5,i);
				}
			}
		}
		
		for(int i =0; i<mGR.mChakraTrow.length;i++){
			if(mGR.mChakraTrow[i].update(mGR.mPlayer.speed)){
				if(CircRectsOverlap(mGR.mChakraTrow[i].x, mGR.mChakraTrow[i].y, mGR.mTex_Chidiya[0][0].width()*.4f, mGR.mTex_Chidiya[0][0].Height()*.4f, mGR.mPlayer.x, mGR.mPlayer.y, mGR.mTex_Monkey[0][0].Height()*.4f)){
					gameover(6,i);
				}
			}
			
			
			if(!mGR.mChakraTrow[i].isTrow && mGR.mChakraTrow[i].y <.4f &&mGR.mChakraTrow[i].y >-.4f){
				mGR.mChakraTrow[i].isTrow = true;
				int no = 2;
				if(mGR.mPlayer.distance > 500)
				{
					if(M.mRand.nextBoolean()){
						no = 1;
					}
				}
				if(no == 1)
					M.sound9(GameRenderer.mContext, R.drawable.tartadi);
				else
					M.sound10(GameRenderer.mContext, R.drawable.feak);
				for(int j =0,k=0; j<mGR.mSudarshan.length&&k<no;j++){
					if(mGR.mSudarshan[j].y < -1.3f || mGR.mSudarshan[j].x > 1.1f || mGR.mSudarshan[j].x < -1.1f){
						if(no ==1)
							mGR.mSudarshan[j].set(mGR.mChakraTrow[i].x, mGR.mChakraTrow[i].y,1.2f,.80f,no==1);
						else
							mGR.mSudarshan[j].set(mGR.mChakraTrow[i].x, mGR.mChakraTrow[i].y,1.2f,0.80f+(k==0?-.25f:0.10f),no==1);
//						mGR.mSudarshan[j].set(mGR.mChakraTrow[i].x, mGR.mChakraTrow[i].y,(k==0?1.3f:1.2f),(k==0?0.99f:80f),no==1);
						
						k++;
					}
				}
			}
		}
		if (mGR.mPlayer.distance > 5 && mGR.mPlayer.Actoin <= 0) {
			//if (Counter % 50 == 0)
			if ((mGR.mPlayer.distance > 250 && Counter % 25 == 0)||(mGR.mPlayer.distance < 250 && Counter % (int)(56-(mGR.mPlayer.distance*.1f)) == 0))
			{
				
				
				
				if (mGR.mPlayer.resetPower) {
					mGR.mPower.set((M.mRand.nextBoolean() ? -1: 1) * .7f, 1.5f);
					mGR.mPlayer.resetPower = false;
				} else {
					int hard = 5;
					if (mGR.mPlayer.distance > 100)
						hard = 5;
					if (mGR.mPlayer.distance > 200)
						hard = 6;
					if (mGR.mPlayer.resetCount <= 0) {
						int  k =mGR.mPlayer.Hardle;
						if (mGR.mPlayer.distance > 100)
							mGR.mPlayer.resetCount = M.mRand.nextInt(3) + 1;
						else
							mGR.mPlayer.resetCount = M.mRand.nextInt(4) + 1;
						mGR.mPlayer.Hardle = M.mRand.nextInt(hard);
						if(k == mGR.mPlayer.Hardle)
							mGR.mPlayer.Hardle = (mGR.mPlayer.Hardle+1)%hard;
						
						System.out
						.println("[Dis= "
								+ mGR.mPlayer.distance
								+ "][count= "
								+ Counter
								+ "][c2= "
								+ (56 - mGR.mPlayer.distance)
								+ "]["
								+ (mGR.mPlayer.distance > 250 && Counter % 25 == 0)
								+ "]["
								+ (mGR.mPlayer.distance < 250 && Counter
										% (int) (56 - (mGR.mPlayer.distance * .1f)) == 0)
								+ "][a=>"
								+mGR.mPlayer.Hardle
								+ "][b=>"+hard
								+ "][c->"+mGR.mPlayer.resetCount
								+ "][");
					}
					mGR.mPlayer.resetCount--;
//					mGR.mPlayer.Hardle =0;
					switch (mGR.mPlayer.Hardle) {
					case 0:
						for (int i = 0; i < mGR.mGilhari.length; i++) {
							if (mGR.mGilhari[i].y < -1.3f) {
								mGR.mGilhari[i].set(1.5f);
								break;
							}
						}
						break;
					case 1:
						for (int i = 0; i < mGR.mChidia.length; i++) {
							if (mGR.mChidia[i].y < -1.3f) {
								mGR.mChidia[i].set((M.mRand.nextBoolean() ? -1 : 1) * .7f, 1.5f);
								break;
							}
						}
						break;
					case 2:
						for (int i = 0; i < mGR.mChakraTrow.length; i++) {
							if (mGR.mChakraTrow[i].y < -1.3f) {
								mGR.mChakraTrow[i].set(
										(M.mRand.nextBoolean() ? -1 : 1) * .7f,
										1.5f);
								break;
							}
						}
						break;
					case 3:
					case 4:
						mGR.mPlayer.Hardle = 3;
						break;
					case 5:
						for (int i = 0; i < mGR.mVillain.length; i++) {
							if (mGR.mVillain[i].y < -1.3f) {
								mGR.mVillain[i].set((M.mRand.nextBoolean() ? -1
										: 1) * .7f, 1.5f);
								break;
							}
						}
						System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
						break;

					}
				}
			}
		}
		for (int i = 0; i < mGR.mTile.length; i++) {
			if (mGR.mPlayer.speed > 0) {
				if (mGR.mTile[i].y < -1.3f) {
					mGR.mTile[i].setNew(mGR.mTile[i == 0 ? mGR.mTile.length - 1 : i - 1].y + mGR.mTex_Tile[0].Height(),mGR.mPlayer.Hardle == 3 ? M.mRand.nextInt(8) + 1 : 0);
					if(mGR.mPlayer.Hardle ==3)
						mGR.mPlayer.Hardle =4;
				}
			}else{
				if (mGR.mTile[i].y > 1.3f) {
					mGR.mTile[i].setNew(mGR.mTile[i == mGR.mTile.length - 1 ?0: i + 1].y - mGR.mTex_Tile[0].Height(),0);
				}
			}
		}
		
	}
	void Draw_Gameplay(GL10 gl){
		DrawTexture(gl, mGR.mTex_BG,0,0);
		for(int i =0;i<4;i++){
			if(mGR.BGy[i] > -2)
				DrawTexture(gl, mGR.mTex_GameBG[i],0,mGR.BGy[i]);
		}
		for(int i =0; i<mGR.mTile.length;i++){
			DrawTexture(gl, mGR.mTex_Tile[i == 0?0:1],-mGR.mTile[i].x, mGR.mTile[i].y);
			DrawTextureR(gl, mGR.mTex_Tile[i == 0?0:1], mGR.mTile[i].x, mGR.mTile[i].y,180,false);
			
			if(mGR.mTile[i].hardle>0 && mGR.mTile[i].hardle<5)
			{
				if(mGR.mTile[i].hardle == 1)
					DrawTexture(gl, mGR.mTex_Hardle[0][0],-(mGR.mTile[i].x-.25f), mGR.mTile[i].y);
				if(mGR.mTile[i].hardle == 2)
					DrawTexture(gl, mGR.mTex_Hardle[0][1],-(mGR.mTile[i].x-.25f), mGR.mTile[i].y);
				if(mGR.mTile[i].hardle == 3)
					DrawTexture(gl, mGR.mTex_Hardle[0][2],-(mGR.mTile[i].x-.15f), mGR.mTile[i].y);
				if(mGR.mTile[i].hardle == 4)
					DrawTexture(gl, mGR.mTex_Hardle[0][3],-(mGR.mTile[i].x-.08f), mGR.mTile[i].y);
			}
			if(mGR.mTile[i].hardle>4 && mGR.mTile[i].hardle<9){
				if(mGR.mTile[i].hardle == 5)
					DrawTexture(gl, mGR.mTex_Hardle[1][0],(mGR.mTile[i].x-.25f), mGR.mTile[i].y);
				if(mGR.mTile[i].hardle == 6)
					DrawTexture(gl, mGR.mTex_Hardle[1][1], (mGR.mTile[i].x-.25f), mGR.mTile[i].y);
				if(mGR.mTile[i].hardle == 7)
					DrawTexture(gl, mGR.mTex_Hardle[1][2],(mGR.mTile[i].x-.15f), mGR.mTile[i].y);
				if(mGR.mTile[i].hardle == 8)
					DrawTexture(gl, mGR.mTex_Hardle[1][3],(mGR.mTile[i].x-.08f), mGR.mTile[i].y);
			}
		}
		
		for(int i =0; i<mGR.mGilhari.length;i++){
			if(mGR.mGilhari[i].y>-1.3f){
				DrawTexture(gl, mGR.mTex_kapde,0, mGR.mGilhari[i].y-.13f);
				if(mGR.mGilhari[i].isgildhari){
					if(mGR.mGilhari[i].vx > 0)
						DrawTexture(gl, mGR.mTex_Gilhari[1][Counter%mGR.mTex_Gilhari[0].length],(mGR.mGilhari[i].x), mGR.mGilhari[i].y);
					else
						DrawTexture(gl, mGR.mTex_Gilhari[0][Counter%mGR.mTex_Gilhari[0].length],(mGR.mGilhari[i].x), mGR.mGilhari[i].y);
				}
			}
		}
		for(int i =0; i<mGR.mChidia.length;i++){
			if(mGR.mChidia[i].y>-1.3f){
				if(mGR.mChidia[i].vx < 0)
					DrawTexture(gl, mGR.mTex_Chidiya[1][C2%mGR.mTex_Chidiya[0].length],(mGR.mChidia[i].x), mGR.mChidia[i].y);
				else
					DrawTexture(gl, mGR.mTex_Chidiya[0][C2%mGR.mTex_Chidiya[0].length],(mGR.mChidia[i].x), mGR.mChidia[i].y);
			}
		}
		
		for (int i = 0; i < mGR.mChakraTrow.length; i++) {
			if (mGR.mChakraTrow[i].y > -1.3f) {
				DrawTexture(gl, mGR.mTex_Char[mGR.mChakraTrow[i].x < 0 ? 1 : 0],mGR.mChakraTrow[i].x, mGR.mChakraTrow[i].y);
			}
		}
		
		for (int i = 0; i < mGR.mVillain.length; i++) {
			if (mGR.mVillain[i].y > -1.3f) {
				DrawTexture(gl, mGR.mTex_Villain[mGR.mChakraTrow[i].x < 0 ? 1 : 0][C2%mGR.mTex_Villain[0].length],mGR.mVillain[i].x, mGR.mVillain[i].y);
			}
		}
		
		for (int i = 0; i < mGR.mSudarshan.length; i++) {
			if (mGR.mSudarshan[i].y > -1.3f) {
				if(mGR.mSudarshan[i].isRokect)
					DrawTexture(gl, mGR.mTex_Bomb[1],mGR.mSudarshan[i].x, mGR.mSudarshan[i].y);
				else
					DrawTextureR(gl, mGR.mTex_Bomb[0],mGR.mSudarshan[i].x, mGR.mSudarshan[i].y,-(Counter*20)%360,true);
			}
		}
		
		for (int i = 0; i < mGR.mAnim.length; i++) {
			if (mGR.mAnim[i].update()) {
				if(mGR.mAnim[i].img !=0){
					switch (mGR.mAnim[i].img) {
					case 3://Chakra
						mGR.mTex_Partical[0].drawRTS(gl, mGR.mAnim[i].x, mGR.mAnim[i].y,mGR.mAnim[i].z,-(Counter*20)%360,1,1,1,1);
						break;
					case 4://Rokect
						mGR.mTex_Partical[0].drawRTS(gl, mGR.mAnim[i].x, mGR.mAnim[i].y,mGR.mAnim[i].z,-(Counter*20)%360,1,.69f,.00f,1);//255,175,0
						break;
					case 10://Rokect through
						mGR.mTex_Partical[3].drawRTS(gl, mGR.mAnim[i].x, mGR.mAnim[i].y,mGR.mAnim[i].z,-(Counter*20)%360,1,1,1,1);
						break;
					case 1://1 Gilahari & Chidiya
						mGR.mTex_Partical[mGR.mAnim[i].img].drawRTS(gl, mGR.mAnim[i].x, mGR.mAnim[i].y,mGR.mAnim[i].z,-(Counter*20)%360,1,1,1,1);
						break;
					case 2://1 Gilahari & Chidiya
						mGR.mTex_Partical[mGR.mAnim[i].img].drawRTS(gl, mGR.mAnim[i].x, mGR.mAnim[i].y,mGR.mAnim[i].z,-(Counter*20)%360,1,1,1,1);
						break;
					case 5://Rokect through
						mGR.mTex_Partical[4].drawRTS(gl, mGR.mAnim[i].x, mGR.mAnim[i].y,mGR.mAnim[i].z,-(mGR.mAnim[i].rotate*20)%360,1,1,1,1);
						break;
					
					case 20:
						mGR.mTex_Partical[0].drawRTS(gl, mGR.mAnim[i].x,mGR.mAnim[i].y, mGR.mAnim[i].z,
								-(mGR.mAnim[i].rotate * 20), 1, 0, 0, 1);
						break;
					case 21:
						mGR.mTex_Partical[0].drawRTS(gl, mGR.mAnim[i].x,mGR.mAnim[i].y, mGR.mAnim[i].z,
								-(mGR.mAnim[i].rotate * 20), 0, 1, 0, 1);
						break;
					case 22:
						mGR.mTex_Partical[0].drawRTS(gl, mGR.mAnim[i].x,mGR.mAnim[i].y, mGR.mAnim[i].z,
								-(mGR.mAnim[i].rotate * 20), 0, 0, 1, 1);
						break;

					case 23:
						mGR.mTex_Partical[0].drawRTS(gl, mGR.mAnim[i].x,mGR.mAnim[i].y, mGR.mAnim[i].z,
								-(mGR.mAnim[i].rotate * 20), 1, 1, 0, 1);
						break;
					case 24:
						mGR.mTex_Partical[0].drawRTS(gl, mGR.mAnim[i].x,mGR.mAnim[i].y, mGR.mAnim[i].z,
								-(mGR.mAnim[i].rotate * 20), 1, 0, 1, 1);
						break;
					default:
						mGR.mTex_Partical[0].drawRTS(gl, mGR.mAnim[i].x,mGR.mAnim[i].y, mGR.mAnim[i].z,
								-(mGR.mAnim[i].rotate * 20), 0, 1, 1, 1);
						break;
					
					}
				}
				else
				{
					switch (mGR.mPlayer.opp) {
					case 2://Gilahari
						mGR.mTex_Partical[mGR.mAnim[i].img].drawRTS(gl, mGR.mAnim[i].x, mGR.mAnim[i].y,mGR.mAnim[i].z,-(mGR.mAnim[i].rotate*20)%360,.5f,.32f,.16f,1);
						break;
					case 3://Chidiya
						mGR.mTex_Partical[mGR.mAnim[i].img].drawRTS(gl, mGR.mAnim[i].x, mGR.mAnim[i].y,mGR.mAnim[i].z,-(mGR.mAnim[i].rotate*20)%360,.12f,.39f,.92f,1);
						break;
					case 4://Rokect
						mGR.mTex_Partical[mGR.mAnim[i].img].drawRTS(gl, mGR.mAnim[i].x, mGR.mAnim[i].y,mGR.mAnim[i].z,-(mGR.mAnim[i].rotate*20)%360,1,.68f,.00f,1);
						break;
					default://1 chakra
						mGR.mTex_Partical[mGR.mAnim[i].img].drawRTS(gl, mGR.mAnim[i].x	, mGR.mAnim[i].y,mGR.mAnim[i].z,-(mGR.mAnim[i].rotate*20)%360,1,1,1,1);
						break;
					}
				}
			}
		}
		if(mGR.mPower.y > -1.1f){
			DrawTexture(gl, mGR.mTex_Shild[0], mGR.mPower.x, mGR.mPower.y);
		}
		if(mGR.mPlayer.Actoin > 0){
			if(mGR.mPlayer.Power)
				mGR.mTex_Shild[1].drawRTS(gl, mGR.mPlayer.x, mGR.mPlayer.y, mGR.mPlayer.z, -(Counter%360)*20, 1, 1, 1, 1);
			switch (mGR.mPlayer.opp) {
			case 2://Gilahari
				if(mGR.mPlayer.mg2 < 10)
					DrawTexture(gl, mGR.mTex_MGilhari[mGR.mPlayer.vx>0?0:1][mGR.mPlayer.mg2%13], mGR.mPlayer.x, mGR.mPlayer.y);
				else
					DrawTexture(gl, mGR.mTex_MGilhari[mGR.mPlayer.vx>0?0:1][9+(mGR.mPlayer.mg2%4)],
							mGR.mPlayer.x+(mGR.mPlayer.x>0?-.15f:.15f), mGR.mPlayer.y);
				break;
			case 3://Chidiya
				DrawTexture(gl, mGR.mTex_MBird[C2%7], mGR.mPlayer.x, mGR.mPlayer.y);
				break;
			case 4://Rokect
				setAnim(mGR.mPlayer.x, mGR.mPlayer.y-.07f, 1,11);
				DrawTexture(gl, mGR.mTex_MRoket, mGR.mPlayer.x, mGR.mPlayer.y);
				break;
			default://1 chakra
				setAnimFal(mGR.mPlayer.x, mGR.mPlayer.y, 5,0);
				DrawTextureR(gl, mGR.mTex_MRotate, mGR.mPlayer.x, mGR.mPlayer.y,(mGR.mPlayer.vx >0 ? -1:1)*(Counter*45)%360,true);
				break;
			}
		}else{
			if(mGR.mPlayer.Power)
				DrawTextureR(gl, mGR.mTex_Shild[1], mGR.mPlayer.x, mGR.mPlayer.y,-(Counter%360)*20,true);
			if(mGR.mPlayer.speed > 0){
				if(mGR.mPlayer.vx > 0)
					DrawTexture(gl, mGR.mTex_MJump[1],mGR.mPlayer.x, mGR.mPlayer.y);
				else if(mGR.mPlayer.vx < 0)
					DrawTexture(gl, mGR.mTex_MJump[0],mGR.mPlayer.x, mGR.mPlayer.y);
				else 
					DrawTexture(gl, mGR.mTex_Monkey[mGR.mPlayer.x < 0 ? 0 : 1][Counter%mGR.mTex_Monkey[0].length],mGR.mPlayer.x, mGR.mPlayer.y);
			}else{
				if(Counter % 2 == 0)
					mGR.mPlayer.cout++;
				if(mGR.mPlayer.cout < 4)
					DrawTexture(gl, mGR.mTex_MFall[mGR.mPlayer.x > 0?1:0][mGR.mPlayer.cout %mGR.mTex_MFall.length],mGR.mPlayer.x, mGR.mPlayer.y);
				else
					DrawTexture(gl, mGR.mTex_MFall[mGR.mPlayer.x > 0?1:0][(mGR.mPlayer.cout %2)+4],mGR.mPlayer.x, mGR.mPlayer.y);
				if(mGR.mPlayer.x < -.1 || mGR.mPlayer.x > .1f){
					if(mGR.mPlayer.x<-.1f)
						mGR.mPlayer.x+=mGR.mPlayer.vx;
					else
						mGR.mPlayer.x-=mGR.mPlayer.vx;
					if(mGR.mPlayer.vx > .02f)
						mGR.mPlayer.vx-=.005f;
				}
			}
		}
		for(int i =0;i<mGR.mPlayer.oppCont;i++){
			DrawTexture(gl, mGR.mTex_Glow,-.90f+i*.18f, -.70f);
			switch (mGR.mPlayer.opp) {
			case 2://Gilahari
				DrawTextureS(gl, mGR.mTex_Gilhari[1][Counter%mGR.mTex_Gilhari[0].length],-.90f+i*.18f, -.70f,.60f);
				break;
			case 3://Chidiya
				DrawTextureS(gl, mGR.mTex_Chidiya[0][C2%mGR.mTex_Chidiya[0].length],-.90f+i*.18f, -.70f,.55f);
				break;
			case 4://Rokect
				setAnim(-.90f+i*.18f, -.70f+.09f, 1,10);
				DrawTextureS(gl, mGR.mTex_Bomb[1],-.90f+i*.18f, -.70f,1);
				break;
			default://1 chakra
				DrawTextureR(gl, mGR.mTex_Bomb[0],-.90f+i*.18f, -.70f,-(Counter*20)%360,true);
				break;
			}
		}
		DrawTexture(gl, mGR.mTex_SBar, .1f, .9f);
		drawNumber(gl, ""+(int)(10*mGR.mPlayer.distance), 0.13f, .9f,1);
		
		DrawTransScal(gl, mGR.mTex_Power, -.76f, .9f,mGR.mSel == 1?1.1f:1,mGR.mSel == 1?0.5f:1);
		drawNumber(gl, ""+mGR.mPlayer.TPower, -.76f, .9f,0);
		
		DrawTransScal(gl, mGR.mTex_PBTN, .82f, .9f,mGR.mSel == 2?1.1f:1,mGR.mSel == 2?0.5f:1);
		if(mGR.mPlayer.NewScore > 0 && mGR.mPlayer.NewScore < 100){
			if(Counter%2==0){
				setAnim((M.mRand.nextInt()%8)*.1f, (M.mRand.nextInt()%6)*.1f, 10, 20);	
			}
			mGR.mPlayer.NewScore ++;
			DrawTexture(gl, mGR.mTex_NewScore,0,0.3f);
		}
		if(mGR.BGy[4] > -2)
			DrawTexture(gl, mGR.mTex_GameBG[4],0,mGR.BGy[4]);
		gamelogic();
	}
	boolean Handle_Gameplay(MotionEvent event){
		mGR.mSel = 0;
		if(CircRectsOverlap(-.76f, .9f, mGR.mTex_Power.width()*.5f, mGR.mTex_Power.Height()*.5f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05f)){
			mGR.mSel = 1;//Leader board
		}
		if(CircRectsOverlap(.82f, .9f, mGR.mTex_PBTN.width()*.5f, mGR.mTex_PBTN.Height()*.5f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05f)){
			mGR.mSel = 2;//Achievement
		}
		if(MotionEvent.ACTION_DOWN == event.getAction() && mGR.mSel == 0){
			if(mGR.mPlayer.vx == 0){
				if(mGR.mPlayer.x < 0){
					mGR.mPlayer.vx = .1f;
					mGR.mPlayer.vy = .05f;
				}else{
					mGR.mPlayer.vx =-.1f;
					mGR.mPlayer.vy = .05f;
				}
				if(M.mRand.nextBoolean())
					M.sound18(GameRenderer.mContext, R.drawable.kud1);
				else
					M.sound19(GameRenderer.mContext, R.drawable.kud2);
			}
		}
		if(MotionEvent.ACTION_UP == event.getAction()){
			switch (mGR.mSel) {
			case 1:
				if(!mGR.mPlayer.Power && mGR.mPlayer.TPower>0){
					mGR.mPlayer.Power = true;
					mGR.mPlayer.TPower--;
				}
				else{
					M.GameScreen = M.GAMEBUY;
					mGR.fromGame  = true;
				}
				break;
			case 2:
				M.sound8Pause();
				M.stop();
				M.GameScreen = M.GAMEPAUSE;
				break;
			}
			mGR.mSel = 0;
		}
		return true;
	}
	/*************************Game play ENd*************************************/
	
	void DrawTexture(GL10 gl, SimplePlane Tex, float x, float y) {
		Tex.drawPos(gl, x, y);
	}

	void DrawTextureR(GL10 gl, SimplePlane Tex, float x, float y, float angle,boolean isFullRotate) {
		Tex.drawRotet(gl, x, y,angle,isFullRotate);
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
		float c = ((a / M.ScreenWidth) - 0.5f) * 2;
		return c;
	}

	float screen2worldY(float a) {
		float c = ((a / M.ScreenHieght) - 0.5f) * (-2);
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
	//$  : 
	//=  ;
	//.  <
	void drawNumber(GL10 gl, String strs, float x, float y,int aling) {
		float dx = mGR.mTex_Font[0].width()*.6f;
		if(aling ==1)
			x-=dx*strs.length()*.5f;
		for (int i = 0; i < strs.length(); i++) {
			int k = ((int) strs.charAt(i)) - 48;
			if (k >= 0 && k < mGR.mTex_Font.length)
				mGR.mTex_Font[k].drawPos(gl, x + i * dx, y);
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

	void share2friend() {
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("text/plain");
		i.putExtra(Intent.EXTRA_SUBJECT, "Roking new Game '"
				+ GameRenderer.mContext.getResources().getString(R.string.app_name)
				+ "'");
		// i.putExtra(Intent.EXTRA_TEXT,"Let the battle commence!!! Download it now and let’s ROCK!!!!  "+M.PUBLICLINK+getClass().getPackage().getName());
		i.putExtra(Intent.EXTRA_TEXT,
				"Let the battle commence!!! Download it now and let’s ROCK!!!!  "
						+ M.SHARELINK);
		try {
			GameRenderer.mContext.startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(GameRenderer.mStart,
					"There are no email clients installed.", Toast.LENGTH_SHORT)
					.show();
		}
	}
	

}
