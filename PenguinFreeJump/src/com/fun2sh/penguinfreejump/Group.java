package com.fun2sh.penguinfreejump;
import javax.microedition.khronos.opengles.GL10;


import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.widget.Toast;
public class Group extends Mesh 
{
	
	GameRenderer mGR = null;
	int _keyCode;
	int Counter =0;
	int sel = 0;
	float sx,sy;
	
	public void setting(){float ud=.01f;switch (_keyCode) {case 1:sy-=ud;break;case 2:sy+=ud;break;case 3:sx-=ud;break;case 4:sx+=ud;break;}System.out.println(M.GameScreen+"~~~~~~~~~~~~~~~      "+sx+"~~~~~~~~~~~~       "+sy);}
	public boolean Handle(MotionEvent event){
		if(CircRectsOverlap(-.8f,0.0f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))_keyCode = 3;
		if(CircRectsOverlap(0.8f,0.0f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))_keyCode = 4;
		if(CircRectsOverlap(-.0f,-.8f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))_keyCode = 1;
		if(CircRectsOverlap(0.0f,0.8f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))_keyCode = 2;
		if(event.getAction()== MotionEvent.ACTION_UP)_keyCode = 0;
		return true;
	}
	
	public Group(GameRenderer _GameRenderer)
	{
		mGR = _GameRenderer;
	}
	@Override
	public void draw(GL10 gl) {
//		M.GameScreen = M.GAMEOVER;
//		mGR.mLevel =2;
		switch (M.GameScreen) {
		case M.GAMELOGO:
			mGR.mTex_Splash.drawBlack(gl, 0, 0);
			DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if (Counter > 60) {
				M.GameScreen = M.GAMESPLASH;
			}
			break;
		case M.GAMESPLASH:
			Draw_Start(gl);
			break;
		case M.GAMEMENU:
			Draw_Menu(gl);
			break;
		case M.GAMEPAUSE:
		case M.GAMEWIN:
		case M.GAMEOVER:
			Draw_PauseOver(gl);
			break;
		case M.GAMEPLAY:
			Draw_GamePlay(gl);
			break;
		case M.GAMESURVIVAL:
			Draw_SurvivalPlay(gl);
			break;
		}
		setting();
		Counter++;
		if(Counter%3==0)
			C2++;
	}
	public boolean TouchEvent(MotionEvent event) {

		switch (M.GameScreen) {
		case M.GAMELOGO:
			break;
		case M.GAMESPLASH:
			Handle_Start(event);
			break;
		case M.GAMEMENU:
			Handle_Menu(event);
			break;
		case M.GAMEWIN:
		case M.GAMEPAUSE:
		case M.GAMEOVER:
			Handle_PauseOver(event);
			break;
		case M.GAMEPLAY:
			Handle_Gameplay(event);
			break;
		case M.GAMESURVIVAL:
			Handle_Survival(event);
			break;
		}
		Handle(event);
		return true;
	}
	
	float asz = 1;
	float avz = .002f;
	int C2 = 0;
	void Draw_Start(GL10 gl) {
		
		DrawTexture(gl, mGR.mTex_Splash, 0, 0);
		DrawTextureS(gl, mGR.mTex_SText, 0, .42f,asz);
		DrawTransScal(gl, mGR.mTex_Play, -.57f, -.53f, sel == 1 ? 1.1f : 1, sel == 1 ? .5f : 1);
		if (asz > 1.04)
			avz = -.001f;
		if (asz < 0.96)
			avz = 0.001f;
		asz += avz;
		
		DrawTransScal(gl, mGR.mTex_Leader, -.76f, -.8f, sel == 2 ? 1.1f : 1, sel == 2 ? .5f : 1);
		DrawTransScal(gl, mGR.mTex_Achiv, -.34f, -.8f, sel == 3 ? 1.1f : 1, sel == 3 ? .5f : 1);
	}
	boolean Handle_Start(MotionEvent event){
		sel = 0;
		if(CircRectsOverlap(-.57f, -.53f, mGR.mTex_Play.width()*.5, mGR.mTex_Play.Height()*.5, screen2worldX(event.getX()), screen2worldY(event.getY()), .03)){
			sel = 1;
		}
		if(CircRectsOverlap(-.76f, -.8f, mGR.mTex_Play.width()*.5, mGR.mTex_Play.Height()*.5, screen2worldX(event.getX()), screen2worldY(event.getY()), .03)){
			sel = 2;
		}
		if(CircRectsOverlap(-.34f, -.8f, mGR.mTex_Play.width()*.5, mGR.mTex_Play.Height()*.5, screen2worldX(event.getX()), screen2worldY(event.getY()), .03)){
			sel = 3;
		}
		if (MotionEvent.ACTION_UP == event.getAction() && sel>0) {
			switch (sel) {
			case 1:
				M.GameScreen = M.GAMEMENU;
				break;
			case 2:
				GameRenderer.mStart.onShowLeaderboardsRequested();//Leader Board
				break;
			case 3:
				GameRenderer.mStart.onShowAchievementsRequested();//Achievement
				break;
			}
			M.sound4(R.drawable.button_click);
			sel =0;
		}
		return true;
	}
	
	void Draw_Menu(GL10 gl){
		for (int i = 0; i < mGR.mHardle.length; i++) {
			for (int j = 0; j < mGR.mHardle[i].img.length; j++) {
				DrawTexture(gl, mGR.mTex_Ice[mGR.mHardle[i].img[j]], -M.DIS + j * M.DIS, mGR.mHardle[i].y);
			}
		}
		DrawTexture(gl, mGR.mTex_Board, 0, 0);
		DrawTransScal(gl, mGR.mTex_Classic	, 0, .5f,sel==1?1.1f:1,sel==1?.5f:1);
		DrawTransScal(gl, mGR.mTex_Time		, 0, .2f,sel==2?1.1f:1,sel==2?.5f:1);
		DrawTransScal(gl, mGR.mTex_Survival	, 0,-.1f,sel==3?1.1f:1,sel==3?.5f:1);
		
		DrawTransScal(gl, mGR.mTex_Sound[M.setValue?1:0],0.5f, -.48f,sel==4?1.1f:1,sel==4?.5f:1);
		DrawTransScal(gl, mGR.mTex_Rate, 0, -.48f,sel==5?1.1f:1,sel==5?.5f:1);
		DrawTransScal(gl, mGR.mTex_Leader, -.5f,-.48f,sel==6?1.1f:1,sel==6?.5f:1);
	}
	boolean Handle_Menu(MotionEvent event){
		sel = 0;
		if(CircRectsOverlap(0, 0.5, mGR.mTex_Classic.width()*.3, mGR.mTex_Classic.Height()*.5, screen2worldX(event.getX()), screen2worldY(event.getY()), .03)){
			sel = 1;
		}
		if(CircRectsOverlap(0, 0.2, mGR.mTex_Classic.width()*.3, mGR.mTex_Classic.Height()*.5, screen2worldX(event.getX()), screen2worldY(event.getY()), .03)){
			sel = 2;
		}
		if(CircRectsOverlap(0, -.1, mGR.mTex_Classic.width()*.3, mGR.mTex_Classic.Height()*.5, screen2worldX(event.getX()), screen2worldY(event.getY()), .03)){
			sel = 3;
		}
		if(CircRectsOverlap(0.5, -.48, mGR.mTex_Rate.width()*.5, mGR.mTex_Rate.Height()*.5, screen2worldX(event.getX()), screen2worldY(event.getY()), .03)){
			sel = 4;
		}
		if(CircRectsOverlap(0.0, -.48, mGR.mTex_Rate.width()*.5, mGR.mTex_Rate.Height()*.5, screen2worldX(event.getX()), screen2worldY(event.getY()), .03)){
			sel = 5;
		}
		if(CircRectsOverlap(-.5, -.48, mGR.mTex_Rate.width()*.5, mGR.mTex_Rate.Height()*.5, screen2worldX(event.getX()), screen2worldY(event.getY()), .03)){
			sel = 6;
		}
		
		if (MotionEvent.ACTION_UP == event.getAction() && sel>0) {
			switch (sel) {
			case 1:
				mGR.gameReset(0);
				M.GameScreen = M.GAMEPLAY;
				M.play(R.drawable.bg);
				break;
			case 2:
				mGR.gameReset(1);
				M.GameScreen = M.GAMEPLAY;
				M.play(R.drawable.bg);
				break;
			case 3:
				mGR.gameReset(2);
				M.GameScreen = M.GAMESURVIVAL;
				M.play(R.drawable.bg);
				break;
			case 4:
				M.setValue = !M.setValue;
				break;
			case 5:
				RateUs();
				break;
			case 6:
				GameRenderer.mStart.onShowLeaderboardsRequested();//Leader Board
				break;
				
			}
			sel =0;M.sound4(R.drawable.button_click);
		}
		return true;
	}
	
	void Draw_PauseOver(GL10 gl){
		for (int i = 0; i < mGR.mHardle.length; i++) {
			for (int j = 0; j < mGR.mHardle[i].img.length; j++) {
				DrawTexture(gl, mGR.mTex_Ice[mGR.mHardle[i].img[j]], -M.DIS + j * M.DIS, mGR.mHardle[i].y);
			}
		}
		DrawTexture(gl, mGR.mTex_Top2, 0, .5f);
		DrawTexture(gl, mGR.mTex_Board, 0, 0);
		
		
		
		if(M.GameScreen == M.GAMEOVER)
			DrawTexture(gl, mGR.mTex_Gover,0, 0.47f);
		else if(M.GameScreen == M.GAMEWIN)
			DrawTexture(gl, mGR.mTex_Win,0, 0.47f);
		else
			DrawTexture(gl, mGR.mTex_GPaused,0, 0.47f);
		if (M.GameScreen == M.GAMEPAUSE) {
			DrawTransScal(gl, mGR.mTex_Play, 0.0f,0.1f,sel==4?1.1f:1,sel==4?.5f:1);
		} else {
			switch (mGR.mLevel) {
			case 1:
				if (M.GameScreen == M.GAMEWIN) {
					DrawTexture(gl, mGR.mTex_Jump, 0, .31f);
					drawNumber(gl, mGR.mScore + "", .06f, 0.2f, 1);
				}
				if (mGR.m1Best > 0) {
					DrawTexture(gl, mGR.mTex_Best, 0, .05f);
					drawNumber(gl, mGR.m1Best + "", .06f, -.06f, 1);
				}
				break;
			case 2:
//				if (M.GameScreen == M.GAMEWIN) 
				{
					DrawTexture(gl, mGR.mTex_Jump, 0, .31f);
					drawNumber(gl, mGR.mScore + "", .06f, 0.2f, 1);
				}
				if (mGR.m2Best > 0) {
					DrawTexture(gl, mGR.mTex_Best, 0, .05f);
					drawNumber(gl, mGR.m2Best + "", .06f, -.06f, 1);
				}
				break;
			default:
				
				String str = mGR.mTime / 1000 + ":";
				if (M.GameScreen == M.GAMEWIN) {
					DrawTexture(gl, mGR.mTex_TrvTime, 0, .31f);
					if (mGR.mTime % 1000 < 10)
						str += "00" + mGR.mTime % 1000;
					else if (mGR.mTime % 1000 < 100)
						str += "0" + mGR.mTime % 1000;
					else
						str += "" + mGR.mTime % 1000;

					drawNumber(gl, str, .06f, .2f, 1);
				} 
				if (mGR.mTime > 0) {
					DrawTexture(gl, mGR.mTex_Best, 0, .05f);
					str = mGR.m0Best / 1000 + ":";
					if (mGR.m0Best % 1000 < 10)
						str += "00" + mGR.m0Best % 1000;
					else if (mGR.m0Best % 1000 < 100)
						str += "0" + mGR.m0Best % 1000;
					else
						str += "" + mGR.m0Best % 1000;

					drawNumber(gl, str, .06f, -.06f, 1);
				} 
				break;
			}
			
		}
		DrawTransScal(gl, mGR.mTex_Retry, 0.0f,-.25f,sel==1?1.1f:1,sel==1?.5f:1);
		DrawTransScal(gl, mGR.mTex_Menu ,-.40f,-.50f,sel==2?1.1f:1,sel==2?.5f:1);
		DrawTransScal(gl, mGR.mTex_Score, .40f,-.50f,sel==3?1.1f:1,sel==3?.5f:1);
	}
	boolean Handle_PauseOver(MotionEvent event){
		sel = 0;
		if(CircRectsOverlap(0.0f,-.25f, mGR.mTex_Play.width()*.5, mGR.mTex_Play.Height()*.4, screen2worldX(event.getX()), screen2worldY(event.getY()), .03)){
			sel = 1;
		}
		if(CircRectsOverlap(-.40f,-.50f, mGR.mTex_Play.width()*.5, mGR.mTex_Play.Height()*.4, screen2worldX(event.getX()), screen2worldY(event.getY()), .03)){
			sel = 2;
		}
		if(CircRectsOverlap(0.40f,-.50f, mGR.mTex_Play.width()*.5, mGR.mTex_Play.Height()*.4, screen2worldX(event.getX()), screen2worldY(event.getY()), .03)){
			sel = 3;
		}
		if(CircRectsOverlap(0.0f,0.10f, mGR.mTex_Play.width()*.5, mGR.mTex_Play.Height()*.5, screen2worldX(event.getX()), screen2worldY(event.getY()), .03)){
			sel = 4;
		}
		if (MotionEvent.ACTION_UP == event.getAction() && sel>0) {
			switch (sel) {
			case 1:
				mGR.gameReset(mGR.mLevel);
				if(mGR.mLevel == 2)
					M.GameScreen = M.GAMESURVIVAL;
				else
					M.GameScreen = M.GAMEPLAY;
				M.play(R.drawable.bg);
				break;
			case 2:
				M.GameScreen = M.GAMEMENU;
				break;
			case 3:
				GameRenderer.mStart.onShowLeaderboardsRequested();//Leader Board
				break;
			case 4:
				if (M.GameScreen == M.GAMEPAUSE) {
					mGR.mTime = System.currentTimeMillis() - mGR.mTime;
					if (mGR.mLevel == 2)
						M.GameScreen = M.GAMESURVIVAL;
					else
						M.GameScreen = M.GAMEPLAY;
					M.play(R.drawable.bg);
				}
				break;
			}
			M.sound4(R.drawable.button_click);sel =0;
		}
		return true;
	}
	
	
	void Gameover() {
		M.stopplay();
		switch (mGR.mLevel) {
		case 1:
			if (mGR.mTime + M.TIME < System.currentTimeMillis()) {
				M.GameScreen = M.GAMEWIN;
				// GameRenderer.mStart.Achivment();
				if (mGR.mScore > mGR.m1Best)
					mGR.m1Best = mGR.mScore;
			} else {
				M.GameScreen = M.GAMEOVER;
				M.sound1(R.drawable.game_over);
			}
			break;
		case 2:
			if (mGR.mScore > mGR.m2Best)
				mGR.m2Best = mGR.mScore;
			M.GameScreen = M.GAMEOVER;
			M.sound1(R.drawable.game_over);
			break;
		default:
			if (mGR.mLevel == 0 && M.ROW <= mGR.mScore) {
				M.GameScreen = M.GAMEWIN;
				// GameRenderer.mStart.Achivment();
				mGR.mTime = (int) (System.currentTimeMillis() - mGR.mTime);
				if (mGR.m0Best == 0 || mGR.m0Best > mGR.mTime)
					mGR.m0Best = mGR.mTime;
			} else {
				M.GameScreen = M.GAMEOVER;
				M.sound1(R.drawable.game_over);
			}
			break;
		}
		GameRenderer.mStart.ShowInterstitial();
		GameRenderer.mStart.Achivment();
		// GameRenderer.mStart.ShowInterstitial();

	}

	void gamelogic(){
		if (mGR.mPlayer.vy > 0) {
			if(mGR.mLevel == 0)
				mGR.mBG += M.SPD;
			for (int i = 0; i < mGR.mHardle.length; i++) {
				mGR.mHardle[i].y += M.SPD;
			}
			for (int i = 0; i < mGR.mHardle.length; i++) {
				if (mGR.mHardle[i].y < -1.2) {
					mGR.mHardle[i].set(mGR.mHardle[(i == 0 ? mGR.mHardle.length: i) - 1].y + .5f);
				}
			}
		}
		
		
		if (mGR.mPlayer.mCond == 0) {
			switch (mGR.mLevel) {
			case 1:
				if (mGR.mTime + M.TIME < System.currentTimeMillis()) {
					mGR.mPlayer.mCond = 1;
					mGR.mPlayer.isWin = true;
				}
				break;
			default:
				if (mGR.mLevel == 0 && M.ROW <= mGR.mScore) {
					mGR.mPlayer.mCond = 1;
					mGR.mPlayer.isWin = true;
				}
				break;
			}
		}

	
		
		
		mGR.mPlayer.update();
		if (mGR.mPlayer.mCond > 0) {
			mGR.mPlayer.mCond++;
			if (mGR.mPlayer.mCond > 30) {
				Gameover();//M.GameScreen = M.GAMEOVER;
			}
		}
	}
	
	void Draw_GamePlay(GL10 gl) {
		for (int i = 0; i < mGR.mHardle.length; i++) {
			for (int j = 0; j < mGR.mHardle[i].img.length; j++) {
				if (mGR.mPlayer.mCond > 10 && mGR.mHardle[i].worng == j && !mGR.mPlayer.isWin){
					if(mGR.mPlayer.mCond < 14)
						DrawTexture(gl, mGR.mTex_Ice[3], -M.DIS + j * M.DIS, mGR.mHardle[i].y);
					else if(mGR.mPlayer.mCond < 19)
						DrawTexture(gl, mGR.mTex_Ice[4], -M.DIS + j * M.DIS, mGR.mHardle[i].y);
				}else{
					if(mGR.mHardle[i].jump > 0 && mGR.mHardle[i].worng == j){
						mGR.mHardle[i].jump--;
						 if(mGR.mHardle[i].jump <5)
							 DrawTexture(gl, mGR.mTex_Ice[mGR.mHardle[i].img[j]], -M.DIS + j * M.DIS, mGR.mHardle[i].y-.04f);
						 else
							 DrawTexture(gl, mGR.mTex_Ice[mGR.mHardle[i].img[j]], -M.DIS + j * M.DIS, mGR.mHardle[i].y);
					}else
						DrawTexture(gl, mGR.mTex_Ice[mGR.mHardle[i].img[j]], -M.DIS + j * M.DIS, mGR.mHardle[i].y);
				}
			}
		}
		if (mGR.mPlayer.mCond <= 10  || mGR.mPlayer.isWin){
			DrawTextureS(gl, mGR.mTex_Sprite[mGR.mPlayer.img], mGR.mPlayer.x, mGR.mPlayer.y, mGR.mPlayer.z);
		}
		if(mGR.mBG < 2)
			DrawTexture(gl, mGR.mTex_Top2, 0, mGR.mBG);
		
		for (int i = 0; i < 3; i++) {
			if(sel ==i+1)
				DrawTexture(gl, mGR.mTex_Tap, -M.DIS + i * M.DIS, -.84f);
			DrawTexture(gl, mGR.mTex_Finger, -M.DIS + i * M.DIS, -.92f);
		}
		if(mGR.mPlayer.mCond > -1){
			String str = "";
			if (mGR.mLevel == 0) {
				int tii = (int) (System.currentTimeMillis() - mGR.mTime);
				str = tii / 1000 + ":";
				if (tii % 1000 < 10)
					str += "00" + tii % 1000;
				else if (tii % 1000 < 100)
					str += "0" + tii % 1000;
				else
					str += "" + tii % 1000;
				
			}
			if (mGR.mLevel == 1) {
				int tii = (int) ((mGR.mTime + 20000) - System.currentTimeMillis());
				str = tii / 1000 + ":";
				if (tii % 1000 < 10)
					str += "00" + tii % 1000;
				else if (tii % 1000 < 100)
					str += "0" + tii % 1000;
				else
					str += "" + tii % 1000;
			}
			drawNumber(gl, str, .55f, .70f, 1);
			gamelogic();
		}
		if(Counter % 60 == 0){
			M.playvoice();
		}
	}
	boolean Handle_Gameplay(MotionEvent event){
		sel = 0;
		if (MotionEvent.ACTION_DOWN == event.getAction() && mGR.mPlayer.vy <= 0 && mGR.mPlayer.mCond < 1) {
			if(mGR.mPlayer.mCond == -1){
				mGR.mPlayer.mCond =0;
				mGR.mTime = System.currentTimeMillis();
			}
			for (int i = 0; i < 3; i++) {
				if(CircRectsOverlap(-M.DIS + i * M.DIS, -.92f, .3f, .3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
					mGR.mPlayer.row++;
					mGR.mPlayer.row%=mGR.mHardle.length;
					mGR.mHardle[mGR.mPlayer.row].jump = 13;
					mGR.mHardle[mGR.mPlayer.row].worng = i;
					if (mGR.mHardle[mGR.mPlayer.row].img[i] != 0) {
						mGR.mPlayer.mCond = 1;
						M.sound2(R.drawable.in_water);
					} else{
						mGR.mScore++;
						M.sound3(R.drawable.jump);
					}
					mGR.mPlayer.Jump(.5f,i);
					
					sel = i+1;
					break;
				}
			}
		}
		if (MotionEvent.ACTION_UP == event.getAction()){
			sel  =0;
		}
		return true;
	}
	
	
	
	void Survival(){
		if(M.SPD > -.03)
			M.SPD-=.0001;
		
//		if (mGR.mPlayer.vy > 0) 
		{
			for (int i = 0; i < mGR.mHardle.length; i++) {
				mGR.mHardle[i].y += M.SPD;
			}
			for (int i = 0; i < mGR.mHardle.length; i++) {
				if (mGR.mHardle[i].y < -1.2) {
					mGR.mHardle[i].set(mGR.mHardle[(i == 0 ? mGR.mHardle.length: i) - 1].y + .5f);
				}
			}
		}
		if(mGR.mPlayer.img >0 && mGR.mPlayer.img<10)
			mGR.mPlayer.servUpdate();
		else
			mGR.mPlayer.reset(mGR.mHardle[mGR.mPlayer.row].y);
		mGR.mPlayer.y +=M.SPD;
		
		
		if (mGR.mPlayer.mCond > 0 || mGR.mPlayer.y <-.95) {
			mGR.mPlayer.mCond++;
			if (mGR.mPlayer.mCond > 30) {
				Gameover();//M.GameScreen = M.GAMEOVER;
			}
		}
	}
	
	void Draw_SurvivalPlay(GL10 gl) {
		for (int i = 0; i < mGR.mHardle.length; i++) {
			for (int j = 0; j < mGR.mHardle[i].img.length; j++) {
				if (mGR.mPlayer.mCond > 10 && mGR.mHardle[i].worng == j && mGR.mPlayer.row == i){
					if(mGR.mPlayer.mCond < 14)
						DrawTexture(gl, mGR.mTex_Ice[3], -M.DIS + j * M.DIS, mGR.mHardle[i].y);
					else if(mGR.mPlayer.mCond < 19)
						DrawTexture(gl, mGR.mTex_Ice[4], -M.DIS + j * M.DIS, mGR.mHardle[i].y);
				}else{
					if(mGR.mHardle[i].jump > 0 && mGR.mHardle[i].worng == j){
						mGR.mHardle[i].jump--;
						 if(mGR.mHardle[i].jump <5)
							 DrawTexture(gl, mGR.mTex_Ice[mGR.mHardle[i].img[j]], -M.DIS + j * M.DIS, mGR.mHardle[i].y-.04f);
						 else
							 DrawTexture(gl, mGR.mTex_Ice[mGR.mHardle[i].img[j]], -M.DIS + j * M.DIS, mGR.mHardle[i].y);
					}else
						DrawTexture(gl, mGR.mTex_Ice[mGR.mHardle[i].img[j]], -M.DIS + j * M.DIS, mGR.mHardle[i].y);
				}
			}
		}
		if (mGR.mPlayer.mCond <= 10){
			DrawTextureS(gl, mGR.mTex_Sprite[mGR.mPlayer.img], mGR.mPlayer.x, mGR.mPlayer.y, mGR.mPlayer.z);
		}

		for (int i = 0; i < 3; i++) {
			if(sel ==i+1)
				DrawTexture(gl, mGR.mTex_Tap, -M.DIS + i * M.DIS, -.92f);
			DrawTexture(gl, mGR.mTex_Finger, -M.DIS + i * M.DIS, -.92f);
		}
		if(mGR.mPlayer.mCond > -1)
			Survival();
		
		if(Counter % 60 == 0){
			M.playvoice();
		}
	}
	boolean Handle_Survival(MotionEvent event){
		sel = 0;
		if (MotionEvent.ACTION_DOWN == event.getAction() && mGR.mPlayer.vy <= 0 && mGR.mPlayer.mCond < 1) {
			if(mGR.mPlayer.mCond == -1){
				mGR.mPlayer.mCond =0;
				mGR.mTime = System.currentTimeMillis();
			}
			for (int i = 0; i < 3; i++) {
				if(CircRectsOverlap(-M.DIS + i * M.DIS, -.92f, .3f, .3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)){
					mGR.mPlayer.row++;
					mGR.mPlayer.row%=mGR.mHardle.length;
					mGR.mHardle[mGR.mPlayer.row].jump = 13;
					mGR.mHardle[mGR.mPlayer.row].worng = i;
					if(mGR.mHardle[mGR.mPlayer.row].img[i] != 0){
						mGR.mPlayer.mCond = 1;
						M.sound2(R.drawable.in_water);
					}else{
						M.sound3(R.drawable.jump);
						mGR.mScore++;
					}
					mGR.mPlayer.Survival(mGR.mHardle[mGR.mPlayer.row].y,i);
					
					sel = i+1;
					break;
				}
			}
		}
		if (MotionEvent.ACTION_UP == event.getAction()){
			sel  =0;
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

	void drawNumber(GL10 gl, String strs, float x, float y, int aling) {
		float dx = mGR.mTex_Font[0].width() * .6f;
		if (aling == 1)
			x -= strs.length() * dx * .5f;
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
