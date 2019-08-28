package com.Oneday.games24.towerempireglory;
import javax.microedition.khronos.opengles.GL10;
import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.widget.Toast;
public class Group extends Mesh {
	GameRenderer mGR = null;
	
	int Counter =0;
	int C2 =0;
	float sx,sy;
	public void setting(){float ud=.01f;switch (GameRenderer.mStart._keyCode) {case 1:sy-=ud;break;case 2:sy+=ud;break;case 3:sx-=ud;break;case 4:sx+=ud;break;}System.out.println(mGR.mLevel+"~~~~~~~~~~~~~~~      "+sx+"~~~~~~~~~~~~       "+sy);}
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
//		 M.GameScreen = M.GAMEOVER;
		switch (M.GameScreen) {
		case M.GAMELOGO:
			DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if (Counter > 60){
				M.GameScreen = M.GAMESPLASH;
				M.play(GameRenderer.mContext, R.drawable.splash_other);
			}
//			M.GameScreen = M.GAMEUPGR;
			break;
		case M.GAMESPLASH:
			DrawTexture(gl, mGR.mTex_Splash, 0, 0);
			DrawTransScal(gl, mGR.mTex_button[0], -.8f	, -.84f,mGR.mSel == 1?1.1f:1,mGR.mSel == 1?0.5f:1);
			mGR.mFont.Draw(gl, "Tap to continue", -.76f , -.82f, 0, 1);
			break;
		case M.GAMEABOUT:
			Draw_About(gl);
			break;
		case M.GAMEOVER:
		case M.GAMEWIN:
			Draw_Over(gl);
			break;
		case M.GAMEMENU:
			Draw_Menu(gl);
			break;
		case M.GAMEUPGR:
			Draw_Upgrade(gl);
			break;
		case M.GAMESETI:
		case M.GAMEPAUSE:
			Draw_Setting(gl);
			break;
		case M.GAMEPLAY:
			Draw_GamePlay(gl);
			break;
		case M.GAMEBUY:
			Draw_Buy(gl);
			break;
		}
//		 setting();
		Counter++;
	}

	public boolean TouchEvent(MotionEvent event) {
		switch (M.GameScreen) {
		case M.GAMELOGO:
			break;
		case M.GAMESPLASH:
			mGR.mSel = 0;
			if(CircRectsOverlap(-.8f	, -.84f, mGR.mTex_button[0].width()*.8f, mGR.mTex_button[0].Height()*.9f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
				mGR.mSel = 1;
			}
			if(MotionEvent.ACTION_UP == event.getAction() && mGR.mSel == 1){
				M.GameScreen =M.GAMEMENU;
				mGR.mSel = 0;
			}
			break;
		case M.GAMEABOUT:
			Handle_About(event);
			break;
		case M.GAMEOVER:
		case M.GAMEWIN:
			Handle_Over(event);
			break;
		case M.GAMEMENU:
			Handle_Menu(event);
			break;
		case M.GAMEUPGR:
			Handle_Upgrade(event);
			break;
		case M.GAMESETI:
		case M.GAMEPAUSE:
			Handle_Setting(event);
			break;
		case M.GAMEPLAY:
			Handle_GamePlay(event);
			break;
		case M.GAMEBUY:
			Handle_Buy(event);
			break;
		}
		
		Handle(event);
		return true;
	}
	
	/************************Buy Start*************************/
	int selbut = 0;
	void Draw_Buy(GL10 gl){
		DrawTexture(gl, mGR.mTex_BG[mGR.mPlayer.bgNo], 0, 0);
		for(int i=0;i<3;i++){
			if(selbut == i)
				DrawTransScal(gl, mGR.mTex_button[0], -.5f+.5f*i, .63f,mGR.mSel == (i+1)?1.1f:1,mGR.mSel == (i+1)?0.5f:1);
			else
				mGR.mTex_button[0].drawBlack(gl, -.5f+.5f*i, .63f, .3f, .5f, .5f, 1, mGR.mSel == (i+1)?1.2f:1);
		}
		mGR.mFont.Draw(gl, GameRenderer.mStart.getString(R.string.CRYSTAL), -.5f+.5f*0, .65f, 0, 1);
		mGR.mFont.Draw(gl, ": "+mGR.mPlayer.TCrystal, .1f-.5f+.5f*0, .65f, 1, 0);
		
		mGR.mFont.Draw(gl, GameRenderer.mStart.getString(R.string.KEY), -.5f+.5f*1, .65f, 0, 1);
		mGR.mFont.Draw(gl, ": "+mGR.mPlayer.TKey, -.45f+.5f*1, .65f, 1, 0);
		
		mGR.mFont.Draw(gl, GameRenderer.mStart.getString(R.string.COINS), sx-.55f+.5f*2, .65f, 0, 1);
		mGR.mFont.Draw(gl, ": "+mGR.mPlayer.T$, -.48f+.5f*2, .65f, 1, 0);
		
		DrawTexture(gl, mGR.mTex_Crystal, -.7f+.5f*0, .7f);
		DrawTexture(gl, mGR.mTex_Key, -.7f+.5f*1, .7f);
		DrawTexture(gl, mGR.mTex_$, -.7f+.5f*2, .7f);
		
		DrawTextureS(gl, mGR.mTex_board, 0.0f, 0f,1.3f,1);
		for(int i=0;i<4;i++){
			DrawTransScal(gl, mGR.mTex_button[7], .30f, .33f-.25f*i,mGR.mSel == (i+5)?1.1f:1,mGR.mSel == (i+5)?0.5f:1);
			if(selbut == 2)
				mGR.mFont.Draw(gl, ""+((i*i)+1)*5000, -.22f, .33f-.25f*i, 1, 1);
			else
				mGR.mFont.Draw(gl, ""+((i*i)+1)*25, -.22f, .33f-.25f*i, 1, 1);
			mGR.mFont.Draw(gl, "$"+(0.99f+i), 0, .33f-.25f*i, 1, 1);
			mGR.mFont.Draw(gl, "Buy", .34f, .35f-.25f*i, 0, 1);
		}
		
		
		
		DrawTransScal(gl, mGR.mTex_button[4], 0.87f, -.85f,mGR.mSel == 9?1.1f:1,mGR.mSel == 9?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Back 	, 0.87f, -.85f,mGR.mSel == 9?1.1f:1,mGR.mSel == 9?0.5f:1);
		
	} 
	boolean Handle_Buy(MotionEvent event){
		mGR.mSel =0;
		for(int i =0;i<3;i++){
			if(CircRectsOverlap(-.5f+.5f*i, .63f, mGR.mTex_button[0].width()*.4f, mGR.mTex_button[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
				mGR.mSel = i+1;
			}
		}
		for(int i =0;i<4;i++){
			if(CircRectsOverlap(.30f, .33f-.25f*i, mGR.mTex_button[7].width()*.4f, mGR.mTex_button[7].Height()*.8f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
				mGR.mSel = i+5;
			}
		}
		if(CircRectsOverlap(0.87f, -.85f, mGR.mTex_button[4].width()*.4f, mGR.mTex_button[4].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 9;
		}
		if(MotionEvent.ACTION_UP ==event.getAction()){/*
			switch (mGR.mSel) {
			case 1:case 3:case 2:
				selbut = mGR.mSel - 1;
				break;
			case 5:case 6:case 7:case 8:
				switch (selbut) {
				case 0:
					mGR.mInApp.onCrystal(mGR.mSel-4);
					break;
				case 1:
					mGR.mInApp.onKey(mGR.mSel-4);
					break;
				case 3:
					mGR.mInApp.onCoin(mGR.mSel-4);
					break;
				}
				break;
			
				
			case 9:
				M.GameScreen = M.GAMEUPGR;
				break;
			}
			if(mGR.mSel !=0)
				M.sound6(GameRenderer.mContext, R.drawable.button_click);
			mGR.mSel =0;
		*/}
		return true;
	}
	/************************Buy End*************************/
	/************************About Start*************************/
	void Draw_About(GL10 gl){
		DrawTexture(gl, mGR.mTex_BG[mGR.mPlayer.bgNo], 0, 0);
		DrawTexture(gl, mGR.mTex_board, 0.0f, 0.23f);
		DrawTexture(gl, mGR.mTex_Aboutscr, 0.0f, 0.23f);

		
		DrawTransScal(gl, mGR.mTex_fb, -.39f+.26f*0, -.50f,mGR.mSel == 5?1.1f:1,mGR.mSel == 5?0.5f:1);
		DrawTransScal(gl, mGR.mTex_GP, -.39f+.26f*1, -.50f,mGR.mSel == 6?1.1f:1,mGR.mSel == 6?0.5f:1);
		DrawTransScal(gl, mGR.mTex_twitter, -.39f+.26f*2, -.50f,mGR.mSel == 7?1.1f:1,mGR.mSel == 7?0.5f:1);
		DrawTransScal(gl, mGR.mTex_button[4], -.39f+.26f*3, -.50f,mGR.mSel == 8?1.1f:1,mGR.mSel == 8?0.5f:1);
		DrawTransScal(gl, mGR.mTex_share, -.39f+.26f*3, -.50f,mGR.mSel == 8?1.1f:1,mGR.mSel == 8?0.5f:1);
		
		DrawTransScal(gl, mGR.mTex_button[4], 0.87f, -.85f,mGR.mSel == 9?1.1f:1,mGR.mSel == 9?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Back 	, 0.87f, -.85f,mGR.mSel == 9?1.1f:1,mGR.mSel == 9?0.5f:1);
		
	} 
	boolean Handle_About(MotionEvent event){
		mGR.mSel =0;
		for(int i =0;i<4;i++){
			if(CircRectsOverlap(0, .58f-.23f*i, mGR.mTex_button[0].width()*.4f, mGR.mTex_button[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
				mGR.mSel = i+1;
			}
		}
		for(int i =0;i<4;i++){
			if(CircRectsOverlap(-.39f+.26f*i, -.50f, mGR.mTex_button[4].width()*.4f, mGR.mTex_button[4].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
				mGR.mSel = i+5;
			}
		}
		if(CircRectsOverlap(0.87f, -.85f, mGR.mTex_button[4].width()*.4f, mGR.mTex_button[4].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 9;
		}
		if(MotionEvent.ACTION_UP ==event.getAction()){
			switch (mGR.mSel) {
			case 5:
				facebook();
				break;
			case 6:
				Google();
				break;
			case 7:
				Twitter();
				break;
			case 8:
				share2friend();
				break;
			case 9:
				M.GameScreen = M.GAMESETI;
				break;
			}
			if(mGR.mSel != 0)
				M.sound6(GameRenderer.mContext, R.drawable.button_click);
			mGR.mSel =0;
		}
		return true;
	}
	/************************About End*************************/
	
	/************************Setting Start*************************/
	void Draw_Setting(GL10 gl){
		DrawTexture(gl, mGR.mTex_BG[mGR.mPlayer.bgNo], 0, 0);
		DrawTexture(gl, mGR.mTex_board, 0.0f, 0.23f);
		for(int i =0;i<4;i++){
			DrawTransScal(gl, mGR.mTex_button[0], 0, .58f-.23f*i,mGR.mSel == i+1?1.1f:1,mGR.mSel == i+1?0.5f:1);
		}
		
		if(M.GameScreen == M.GAMESETI){
			DrawTransScal(gl, mGR.mTex_AboutT, 0, .58f-.23f*0,mGR.mSel == 1?1.1f:1,mGR.mSel == 1?0.5f:1);
			DrawTransScal(gl, mGR.mTex_sound[M.setValue?1:0], 0, .58f-.23f*1,mGR.mSel == 2?1.1f:1,mGR.mSel == 2?0.5f:1);
			DrawTransScal(gl, mGR.mTex_Music[M.setMusic?1:0], 0, .58f-.23f*2,mGR.mSel == 3?1.1f:1,mGR.mSel == 3?0.5f:1);
			DrawTransScal(gl, mGR.mTex_ResetT, 0, .58f-.23f*3,mGR.mSel == 4?1.1f:1,mGR.mSel == 4?0.5f:1);
		}else{
			DrawTransScal(gl, mGR.mTex_Play, 0, .58f-.23f*0,mGR.mSel == 1?1.1f:1,mGR.mSel == 1?0.5f:1);
			DrawTransScal(gl, mGR.mTex_Retry, 0, .58f-.23f*1,mGR.mSel == 2?1.1f:1,mGR.mSel == 2?0.5f:1);
			DrawTransScal(gl, mGR.mTex_Music[M.setMusic?1:0], 0, .58f-.23f*2,mGR.mSel == 3?1.1f:1,mGR.mSel == 3?0.5f:1);
			DrawTransScal(gl, mGR.mTex_Shop, 0, .58f-.23f*3,mGR.mSel == 4?1.1f:1,mGR.mSel == 4?0.5f:1);
		}
			
		
		DrawTransScal(gl, mGR.mTex_fb, -.39f+.26f*0, -.50f,mGR.mSel == 5?1.1f:1,mGR.mSel == 5?0.5f:1);
		DrawTransScal(gl, mGR.mTex_GP, -.39f+.26f*1, -.50f,mGR.mSel == 6?1.1f:1,mGR.mSel == 6?0.5f:1);
		DrawTransScal(gl, mGR.mTex_twitter, -.39f+.26f*2, -.50f,mGR.mSel == 7?1.1f:1,mGR.mSel == 7?0.5f:1);
		DrawTransScal(gl, mGR.mTex_button[4], -.39f+.26f*3, -.50f,mGR.mSel == 8?1.1f:1,mGR.mSel == 8?0.5f:1);
		DrawTransScal(gl, mGR.mTex_share, -.39f+.26f*3, -.50f,mGR.mSel == 8?1.1f:1,mGR.mSel == 8?0.5f:1);
		
		if(M.GameScreen == M.GAMESETI){
			DrawTransScal(gl, mGR.mTex_button[4], 0.87f, -.85f,mGR.mSel == 9?1.1f:1,mGR.mSel == 9?0.5f:1);
			DrawTransScal(gl, mGR.mTex_Back 	, 0.87f, -.85f,mGR.mSel == 9?1.1f:1,mGR.mSel == 9?0.5f:1);
		}
		
	} 
	boolean Handle_Setting(MotionEvent event){
		mGR.mSel =0;
		for(int i =0;i<4;i++){
			if(CircRectsOverlap(0, .58f-.23f*i, mGR.mTex_button[0].width()*.4f, mGR.mTex_button[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
				mGR.mSel = i+1;
			}
		}
		for(int i =0;i<4;i++){
			if(CircRectsOverlap(-.39f+.26f*i, -.50f, mGR.mTex_button[4].width()*.4f, mGR.mTex_button[4].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
				mGR.mSel = i+5;
			}
		}
		if(CircRectsOverlap(0.87f, -.85f, mGR.mTex_button[4].width()*.4f, mGR.mTex_button[4].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 9;
		}
		if(MotionEvent.ACTION_UP ==event.getAction()){
			switch (mGR.mSel) {
			case 1:
				if(M.GameScreen == M.GAMESETI)
					M.GameScreen =M.GAMEABOUT;
				else{
					M.GameScreen = M.GAMEPLAY;
					M.playGame(GameRenderer.mContext);
				}
				break;
			case 2:
				if(M.GameScreen == M.GAMESETI)
					M.setValue = !M.setValue;
				else{
						M.playGame(GameRenderer.mContext);
						M.GameScreen = M.GAMEPLAY;
						mGR.gameReset();
					}
				break;
			case 3:
				M.setMusic = !M.setMusic;
				if(M.setMusic)
					M.play(GameRenderer.mContext, R.drawable.splash_other);
				else
					M.stop(1);
				break;
			case 4:
				if(M.GameScreen == M.GAMESETI)
					GameRenderer.mStart.resetGame();//Reset
				else
					M.GameScreen = M.GAMEUPGR;
				break;
			case 5:
				facebook();
				break;
			case 6:
				Google();
				break;
			case 7:
				Twitter();
				break;
			case 8:
				share2friend();
				break;
			case 9:
				if(M.GameScreen == M.GAMESETI)
					M.GameScreen = M.GAMEMENU;
				break;
			}
			if(mGR.mSel !=0)
				M.sound6(GameRenderer.mContext, R.drawable.button_click);
			mGR.mSel =0;
		}
		return true;
	}
	/************************Setting End*************************/
	float cx1[] ={0,2};
	float cx2[] ={0,2};
	/************************Upgrade Start*************************/
	void upgrade1(GL10 gl){
		for (int i = 0; i < 3; i++) {
			if (i == 0) {
				if(mGR.selPower == 0)
					DrawTransScal(gl, mGR.mTex_button[8], -.45f + i * .55f,0.20f, 1, 1);
				DrawTransScal(gl, mGR.mTex_button[5], -.45f + i * .55f,0.20f, 1, 1);
				
				if(mGR.selPower == 1)
					DrawTransScal(gl, mGR.mTex_button[8], -.45f + i * .55f,-.20f, 1, 1);
				DrawTransScal(gl, mGR.mTex_button[5], -.45f + i * .55f,-.20f, 1, 1);
				
				mGR.mFont.Draw(gl, "Force", -.45f + i * .55f,0.20f, 0, 1);
				mGR.mFont.Draw(gl, "Brio", -.45f + i * .55f,-.20f, 0, 1);
				
				DrawTransScal(gl, mGR.mTex_BPower[0], -.45f + i * .55f+.12f,0.20f, 1, 1);
				DrawTransScal(gl, mGR.mTex_BPower[1], -.45f + i * .55f+.12f,-.20f, 1, 1);
//				mGR.mFont.Draw(gl, "Force", -.45f + i * .55f,0.20f, 0, 1);
				mGR.mFont.Draw(gl, ""+(mGR.uPower[0]+1), -.45f + i * .55f+.16f,0.20f-.04f, 0, 0);
				mGR.mFont.Draw(gl, ""+(mGR.uPower[1]+1), -.45f + i * .55f+.16f,-.20f-.04f, 0, 0);
			}
			if (i == 1) {
				for (int j = 0; j < 3; j++){
					if(mGR.selPower == j+2)
						DrawTransScal(gl, mGR.mTex_button[8], -.45f + i * .55f		,0.35f - .35f * j, 1, 1);
					DrawTransScal(gl, mGR.mTex_button[5], -.45f + i * .55f		,0.35f - .35f * j, 1, 1);
					DrawTransScal(gl, mGR.mTex_BPower[j+2], -.45f + i * .55f+.12f	,0.35f - .35f * j, 1, 1);
					if(j==0)
						mGR.mFont.Draw(gl, "Power", -.47f + i * .55f,0.41f - .35f * j, 0, 1);
					if(j==1)
						mGR.mFont.Draw(gl, "Toxic", -.47f + i * .55f,0.41f - .35f * j, 0, 1);
					if(j==2)
						mGR.mFont.Draw(gl, "Fatal", -.47f + i * .55f,0.41f - .35f * j, 0, 1);
					mGR.mFont.Draw(gl, "Shot", -.47f + i * .55f,0.33f - .35f * j, 0, 1);
					if(mGR.uPower[j+2]>0)
						mGR.mFont.Draw(gl, ""+mGR.uPower[j+2], -.45f + i * .55f+.16f	,0.35f - .35f * j-.04f, 0, 0);
					if (mGR.uPower[0] < 1 && j==0)
						mGR.mTex_button[5].drawBlack(gl,  -.45f + i * .55f		,0.35f - .35f * j,0,0,0, .6f,1);
					if ((mGR.uPower[0] < 3 || mGR.uPower[1] < 3) && j==1)
						mGR.mTex_button[5].drawBlack(gl,  -.45f + i * .55f		,0.35f - .35f * j,0,0,0, .6f,1);
					if (mGR.uPower[1] < 1 && j==2)
						mGR.mTex_button[5].drawBlack(gl,  -.45f + i * .55f		,0.35f - .35f * j,0,0,0, .6f,1);
				}
			}
			if (i == 2) {
				if(mGR.selPower == 5)
					DrawTransScal(gl, mGR.mTex_button[8], -.45f + i * .55f		,0, 1, 1);
				DrawTransScal(gl, mGR.mTex_button[5], -.45f + i * .55f, 0,1, 1);
				DrawTransScal(gl, mGR.mTex_BPower[5], -.45f + i * .55f+.12f, 0,1, 1);
				mGR.mFont.Draw(gl, "Multi", -.47f + i * .55f,0.06f, 0, 1);
				mGR.mFont.Draw(gl, "Shot" , -.47f + i * .55f,-.03f, 0, 1);
				if(mGR.uPower[5]>0)
					mGR.mFont.Draw(gl, ""+mGR.uPower[4] , -.23f + i * .55f,-.05f, 0, 1);
				if (mGR.uPower[2] < 3 || mGR.uPower[4] < 3){
					mGR.mTex_button[5].drawBlack(gl,-.45f + i * .55f, 0,0,0,0, .6f,1);
				}
			}
			
		}
		if(mGR.mPopUp.pop == 1 || mGR.mPopUp.pop == 2)
		{
			DrawTextureS(gl, mGR.mTex_board, .1f, mGR.mPopUp.y,1.2f,.50f);
			DrawTextureS(gl, mGR.mTex_UpBox, .6f, mGR.mPopUp.y,2.4f,1.8f);
			 
			switch (mGR.selPower) {
			case 0:
				mGR.mFont.Draw(gl, "Force", -.54f, mGR.mPopUp.y, 1, 0);
				mGR.mFont.Draw(gl, "Increase your arrow's base damege.", -.54f, -.08f+mGR.mPopUp.y, 0, 0);
				mGR.mFont.Draw(gl, "Current: "+(mGR.uPower[mGR.selPower]*4+1)+"  Next: "+((mGR.uPower[mGR.selPower]+1)*4+1), -.54f, -.16f+mGR.mPopUp.y, 0, 0);
				mGR.mFont.Draw(gl, ""+(mGR.uPower[mGR.selPower]+1)*100, .59f, +mGR.mPopUp.y, 0, 0);
				DrawTextureS(gl, mGR.mTex_$, +.48f, mGR.mPopUp.y,1,1);
				
//				DrawTextureS(gl, mGR.mTex_button[7], .6f, -.18f+mGR.mPopUp.y,1,1);
				DrawTransScal(gl, mGR.mTex_button[7], .6f, -.18f + mGR.mPopUp.y, mGR.mSel == 51?1.1f:1,mGR.mSel == 51?0.5f:1);
				mGR.mFont.Draw(gl, "Upgrade", .64f, -.17f + mGR.mPopUp.y, 1, 1);
				break;
			case 1:
				mGR.mFont.Draw(gl, "Brio", -.54f, mGR.mPopUp.y, 1, 0);
				mGR.mFont.Draw(gl, "Increase your Bow Fire rate.", -.54f, -.08f+mGR.mPopUp.y, 0, 0);
				mGR.mFont.Draw(gl, "Current: "+(mGR.uPower[mGR.selPower]*3+1)+"  Next: "+((mGR.uPower[mGR.selPower]+1)*3+1), -.54f, -.16f+mGR.mPopUp.y, 0, 0);
				mGR.mFont.Draw(gl, ""+(mGR.uPower[mGR.selPower]+1)*110, .59f, +mGR.mPopUp.y, 0, 0);
				DrawTextureS(gl, mGR.mTex_$, +.48f, mGR.mPopUp.y,1,1);
//				DrawTextureS(gl, mGR.mTex_button[7], .6f, -.18f+mGR.mPopUp.y,1,1);
				DrawTransScal(gl, mGR.mTex_button[7], .6f, -.18f + mGR.mPopUp.y, mGR.mSel == 51?1.1f:1,mGR.mSel == 51?0.5f:1);
				mGR.mFont.Draw(gl, "Upgrade", .64f, -.17f + mGR.mPopUp.y, 1, 1);
				break;
			case 2:
				mGR.mFont.Draw(gl, "Power Shot", -.54f, mGR.mPopUp.y, 1, 0);
				mGR.mFont.Draw(gl, "Your Arrow can repel monster.", -.54f, -.08f+mGR.mPopUp.y, 0, 0);
				mGR.mFont.Draw(gl, "Upgrade to improve distance.", -.54f, -.16f+mGR.mPopUp.y, 0, 0);
				if (mGR.uPower[0] >= 1){
					DrawTextureS(gl, mGR.mTex_$, +.48f, mGR.mPopUp.y,1,1);
					mGR.mFont.Draw(gl, ""+(mGR.uPower[mGR.selPower]+1)*500, .59f, +mGR.mPopUp.y, 0, 0);
//					DrawTextureS(gl, mGR.mTex_button[7], .6f, -.18f+mGR.mPopUp.y,1,1);
					DrawTransScal(gl, mGR.mTex_button[7], .6f, -.18f + mGR.mPopUp.y, mGR.mSel == 51?1.1f:1,mGR.mSel == 51?0.5f:1);
					if(mGR.uPower[mGR.selPower] == 0)
						mGR.mFont.Draw(gl, "Buy", .64f, -.17f+mGR.mPopUp.y, 1, 1);
					else
						mGR.mFont.Draw(gl, "Update", .64f, -.17f+mGR.mPopUp.y, 1, 1);
				}
				else
					mGR.mFont.Draw(gl, "Force Level: 2", .44f, +mGR.mPopUp.y, 0, 0);
				
				break;
			case 3:
				mGR.mFont.Draw(gl, "Toxic Shot", -.54f,+mGR.mPopUp.y, 1, 0);
				mGR.mFont.Draw(gl, "Decrease monster attack speed.", -.54f, -.08f+mGR.mPopUp.y, 0, 0);
				mGR.mFont.Draw(gl, "Current: "+(mGR.uPower[mGR.selPower]*5+1)+"  Next: "+((mGR.uPower[mGR.selPower]+1)*5+1)+"%", -.54f, -.16f+mGR.mPopUp.y, 0, 0);
				if ((mGR.uPower[0] >= 3 || mGR.uPower[1] >= 3)){
					mGR.mFont.Draw(gl, ""+(mGR.uPower[mGR.selPower]+1)*450, .59f, +mGR.mPopUp.y, 0, 0);
					DrawTextureS(gl, mGR.mTex_$, +.48f, mGR.mPopUp.y,1,1);
//					DrawTextureS(gl, mGR.mTex_button[7], .6f, -.18f+mGR.mPopUp.y,1,1);
					DrawTransScal(gl, mGR.mTex_button[7], .6f, -.18f + mGR.mPopUp.y, mGR.mSel == 51?1.1f:1,mGR.mSel == 51?0.5f:1);
					if(mGR.uPower[mGR.selPower] == 0)
						mGR.mFont.Draw(gl, "Buy", .64f, -.17f+mGR.mPopUp.y, 1, 1);
					else
						mGR.mFont.Draw(gl, "Update", .64f, -.17f+mGR.mPopUp.y, 1, 1);
				}
				else{
					mGR.mFont.Draw(gl, "Force Level: 4", .44f, .05f+mGR.mPopUp.y, 0, 0);
					mGR.mFont.Draw(gl, "Brio Level: 4", .44f, -.05f+mGR.mPopUp.y, 0, 0);
				}
				
				break;
			case 4:
				mGR.mFont.Draw(gl, "Fatal Shot", -.54f, +mGR.mPopUp.y, 1, 0);
				mGR.mFont.Draw(gl, "Your bow can cause double damage.", -.54f, -.08f+mGR.mPopUp.y, 0, 0);
				mGR.mFont.Draw(gl, "Current: "+(mGR.uPower[mGR.selPower]*5+1)+"  Next: "+((mGR.uPower[mGR.selPower]+1)*5+1)+"%", -.54f, -.16f+mGR.mPopUp.y, 0, 0);
				if (mGR.uPower[1] > 0){
					DrawTextureS(gl, mGR.mTex_$, +.48f, mGR.mPopUp.y,1,1);
					mGR.mFont.Draw(gl, ""+(mGR.uPower[mGR.selPower]+1)*600, .59f, +mGR.mPopUp.y, 0, 0);
//					DrawTextureS(gl, mGR.mTex_button[7], .6f, -.18f+mGR.mPopUp.y,1,1);
					DrawTransScal(gl, mGR.mTex_button[7], .6f, -.18f + mGR.mPopUp.y, mGR.mSel == 51?1.1f:1,mGR.mSel == 51?0.5f:1);
					if(mGR.uPower[mGR.selPower] == 0)
						mGR.mFont.Draw(gl, "Buy", .64f, -.17f+mGR.mPopUp.y, 1, 1);
					else
						mGR.mFont.Draw(gl, "Update", .64f, -.17f+mGR.mPopUp.y, 1, 1);
				}
				else{
					mGR.mFont.Draw(gl, "Brio Level: 2", .44f, .05f+mGR.mPopUp.y, 0, 0);
				}
				break;
			case 5:
				mGR.mFont.Draw(gl, "Multi Arrow Shot", -.54f, mGR.mPopUp.y, 1, 0);
				mGR.mFont.Draw(gl, "Current: "+(mGR.uPower[mGR.selPower]+1)+",    100% Damage ", -.54f, -.08f+mGR.mPopUp.y, 0, 0);
				mGR.mFont.Draw(gl, "Next: "+(mGR.uPower[mGR.selPower]+2)+",    50% Damage ", -.54f, -.16f+mGR.mPopUp.y, 0, 0);
				if (mGR.uPower[2] >= 3 && mGR.uPower[4] >= 3){
					DrawTextureS(gl, mGR.mTex_$, +.48f, mGR.mPopUp.y,1,1);
					mGR.mFont.Draw(gl, ""+(mGR.uPower[mGR.selPower]+1)*5000, .59f, +mGR.mPopUp.y, 0, 0);
//					DrawTextureS(gl, mGR.mTex_button[7], .6f, -.18f+mGR.mPopUp.y,1,1);
					DrawTransScal(gl, mGR.mTex_button[7], .6f, -.18f + mGR.mPopUp.y, mGR.mSel == 51?1.1f:1,mGR.mSel == 51?0.5f:1);
					if(mGR.uPower[mGR.selPower] == 0)
						mGR.mFont.Draw(gl, "Buy", .64f, -.17f+mGR.mPopUp.y, 1, 1);
					else
						mGR.mFont.Draw(gl, "Update", .64f, -.17f+mGR.mPopUp.y, 1, 1);
				}else{
					mGR.mFont.Draw(gl, "Power Shot: 3", .44f, .05f+mGR.mPopUp.y, 0, 0);
					mGR.mFont.Draw(gl, "Fatal Shot: 3", .44f, -.05f+mGR.mPopUp.y, 0, 0);
				}
				break;
			}
			
			//
			
			mGR.mPopUp.update();
			
		}
	}
	
	void upgrade2(GL10 gl){
		int total =0;
		for(int i=0;total<mGR.mPlayer.TKill;i++){
			total +=(i)*100;
			mGR.mLevel = i; 
			if(total>mGR.mPlayer.TKill){
				break;
			}
		}
		
		if(mGR.mPlayer.SelBow == 100)
			DrawTransScal(gl, mGR.mTex_button[9], move-.54f ,0 , 1, 1);
		if(mGR.selPower == 100)
			mGR.mTex_button[3].drawBlack(gl,   move-.54f ,0,0,0,0, 1.17f, 1.17f);
		DrawTransScal(gl, mGR.mTex_button[3], move-.54f ,0 , 1, 1);
		DrawTransScal(gl, mGR.mTex_Rect, move-.54f ,0 , 1, 1);
		DrawTransScal(gl, mGR.mTex_UpBow[0][1], move-.54f ,0 , 1, 1);
		DrawTransScal(gl, mGR.mTex_UpBow[0][0], move-.54f ,0 , 1, 1);
		
		
		
//		int total = 0;
//		int po = 0;
		for (int i = 0; i < 9; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				if(mGR.mPlayer.SelBow == i*3+j)
					DrawTransScal(gl, mGR.mTex_button[9], move-.24f + i * .30f,0.40f-.40f*j, 1, 1);
				
				if(mGR.selPower == i*3+j)
					mGR.mTex_button[3].drawBlack(gl,  move-.24f + i * .30f,0.40f-.40f*j,0,0,0, 1.17f, 1.17f);
				
				if(mGR.mLevel>=(i*5)){
					DrawTransScal(gl, mGR.mTex_button[3], move-.24f + i * .30f,0.40f-.40f*j, 1, 1);
					if(j == 0){
						mGR.mTex_Rect.drawBlack(gl,  move-.24f + i * .30f,0.40f-.40f*j,1,0,0, 1, 1);
						mGR.mTex_UpBow[1][i].drawBlack(gl,  move-.24f + i * .30f,0.40f-.40f*j,1,0,0, 1, 1);
					}
					if(j == 1){
						mGR.mTex_Rect.drawBlack(gl,  move-.24f + i * .30f,0.40f-.40f*j,.2f,.78f,0, 1, 1);
						mGR.mTex_UpBow[1][i].drawBlack(gl,  move-.24f + i * .30f,0.40f-.40f*j,.2f,.78f,0, 1, 1);
					}
					if(j == 2){
						mGR.mTex_Rect.drawBlack(gl,  move-.24f + i * .30f,0.40f-.40f*j,.0f,.4f,1, 1, 1);
						mGR.mTex_UpBow[1][i].drawBlack(gl,  move-.24f + i * .30f,0.40f-.40f*j,.0f,.4f,1, 1, 1);
					}
					DrawTransScal(gl, mGR.mTex_UpBow[0][i], move-.24f + i * .30f,0.40f-.40f*j, 1, 1);
					if(!mGR.BowPower[i*3+j]){
						mGR.mTex_button[3].drawBlack(gl,  move-.24f + i * .30f,0.40f-.40f*j,0,0,0, .7f, 1);
					}
				}else{
					DrawTransScal(gl, mGR.mTex_button[6], move-.24f + i * .30f,0.40f-.40f*j, 1, 1);
					DrawTransScal(gl, mGR.mTex_Lock, move-.24f + i * .30f,0.40f-.40f*j, 1, 1);
				}
				
			}
		}
//		mGR.mPopUp.pop = 1;
		if (mGR.mPopUp.pop == 1 || mGR.mPopUp.pop == 2) {
			DrawTextureS(gl, mGR.mTex_board, .1f, mGR.mPopUp.y, 1.2f, .50f);
			DrawTextureS(gl, mGR.mTex_UpBox, .6f, mGR.mPopUp.y, 2.4f, 1.8f);
			
			mGR.mFont.Draw(gl, "Bow: "+((mGR.selPower / 3)+1), -.54f, mGR.mPopUp.y, 1, 0);
			if(mGR.selPower % 3==0)
				mGR.mFont.Draw(gl, "Force:+"+((mGR.selPower / 3)+1)+", Brio:+"+((mGR.selPower / 3)+1), -.54f,-.08f+mGR.mPopUp.y, 0, 0);
			if(mGR.selPower % 3==1)
				mGR.mFont.Draw(gl, "Force:+"+((mGR.selPower / 3)+2)+", Brio:+"+((mGR.selPower / 3)+1), -.54f,-.08f+mGR.mPopUp.y, 0, 0);
			if(mGR.selPower % 3==2)
				mGR.mFont.Draw(gl, "Force:+"+((mGR.selPower / 3)+2)+", Brio:+"+((mGR.selPower / 3)+2), -.54f,-.08f+mGR.mPopUp.y, 0, 0);
			if(mGR.selPower == 100){
				mGR.mFont.Draw(gl, "Use", .64f, -.17f + mGR.mPopUp.y, 1, 1);
			}
			else if (mGR.mLevel>= (mGR.selPower / 3)*5) {
//				DrawTextureS(gl, mGR.mTex_button[7], .6f, -.18f + mGR.mPopUp.y, 1,1);
				DrawTransScal(gl, mGR.mTex_button[7], .6f, -.18f + mGR.mPopUp.y, mGR.mSel == 51?1.1f:1,mGR.mSel == 51?0.5f:1);
				if (mGR.BowPower[mGR.selPower]){
					mGR.mFont.Draw(gl, "Use", .64f, -.17f + mGR.mPopUp.y, 1, 1);
				}else{
					mGR.mFont.Draw(gl, "Buy", .64f, -.17f + mGR.mPopUp.y, 1, 1);
					switch (mGR.selPower % 3) {
					case 0:
						mGR.mFont.Draw(gl, "Force:+"+((mGR.selPower / 3)+1)+", Brio:+"+((mGR.selPower / 3)+1), -.54f,-.08f+mGR.mPopUp.y, 0, 0);
						DrawTextureS(gl, mGR.mTex_$, +.48f, mGR.mPopUp.y, 1, 1);
						mGR.mFont.Draw(gl, "" + (mGR.selPower / 3 + 1) * 500, .59f,+mGR.mPopUp.y, 0, 0);
						break;
					case 1:
						mGR.mFont.Draw(gl, "Force:+"+((mGR.selPower / 3)+2)+", Brio:+"+((mGR.selPower / 3)+1), -.54f,-.08f+mGR.mPopUp.y, 0, 0);
						DrawTextureS(gl, mGR.mTex_$, +.48f, mGR.mPopUp.y, 1, 1);
						mGR.mFont.Draw(gl,""+ (((mGR.selPower / 3 + 1) * 500) + (mGR.selPower / 3 + 1) * 200),.59f, +mGR.mPopUp.y, 0, 0);
						break;
					case 2:
						mGR.mFont.Draw(gl, "Force:+"+((mGR.selPower / 3)+2)+", Brio:+"+((mGR.selPower / 3)+2), -.54f,-.08f+mGR.mPopUp.y, 0, 0);
						DrawTextureS(gl, mGR.mTex_Crystal, +.48f, mGR.mPopUp.y, 1,1);
						mGR.mFont.Draw(gl, "" + ((mGR.selPower / 3 + 1) * 5), .59f,+mGR.mPopUp.y, 0, 0);
						break;
					}
				}
			}else{
				mGR.mFont.Draw(gl, "Need", .50f,+mGR.mPopUp.y, 0, 0);
				mGR.mFont.Draw(gl, "Level:  " + ((mGR.selPower / 3 ))*5,.50f,-.08f+mGR.mPopUp.y, 1, 0);
				mGR.mFont.Draw(gl, "To unlock", .50f,-.16f+mGR.mPopUp.y, 0, 0);
			}
			mGR.mPopUp.update();
		}
	}
	void upgrade3(GL10 gl){
		if(mGR.selPower == 0)
			DrawTransScal(gl, mGR.mTex_button[8], move-.45f , 0, 1, 1);
		DrawTransScal(gl, mGR.mTex_button[5], move-.45f , 0,1, 1);
		DrawTransScal(gl, mGR.mTex_SMana	, move-.33f, 0,1, 1);
		mGR.mFont.Draw(gl, "Mana", move-.47f, 0, 0, 1);
		mGR.mFont.Draw(gl, (mGR.Mana[0] + 1) + "", move - .25f, -.05f, 0, 1);
		for (int i = 0; i < 9; i++) {
			if(mGR.selPower == i+1)
				DrawTransScal(gl, mGR.mTex_button[8], move-0.05f + (i%3) * .50f		, 0.35f - .35f * (i/3), 1, 1);
			DrawTransScal(gl, mGR.mTex_button[5], move-0.05f + (i%3) * .50f		, 0.35f - .35f * (i/3),1, 1);
			DrawTransScal(gl, mGR.mTex_Mana[i]	, move-0.05f + (i%3) * .50f+.12f	, 0.35f - .35f * (i/3),1, 1);
			mGR.mFont.Draw(gl, mSMana[i], move-.07f + (i%3) * .50f	, 0.35f - .35f * (i/3), 0, 1);
			if(mGR.Mana[i+1]>0)
				mGR.mFont.Draw(gl, (mGR.Mana[i+1])+"", move+.15f + (i%3) * .50f	, 0.30f - .35f * (i/3), 0, 1);
			
			switch (i+1) {
			case 1:
				if(mGR.Mana[0]<1){
					mGR.mTex_button[5].drawBlack(gl,move-0.05f + (i%3) * .50f, 0.35f - .35f * (i/3),0,0,0, .6f,1);
				}
				break;
			case 2:
				if(mGR.Mana[0]<3 || mGR.Mana[1]<2){
					mGR.mTex_button[5].drawBlack(gl,move-0.05f + (i%3) * .50f, 0.35f - .35f * (i/3),0,0,0, .6f,1);
				}
				break;
			case 3:
				if(mGR.Mana[0]<5 || mGR.Mana[2]<2){
					mGR.mTex_button[5].drawBlack(gl,move-0.05f + (i%3) * .50f, 0.35f - .35f * (i/3),0,0,0, .6f,1);
				}
				break;
			case 4:
				if(mGR.Mana[0]<1){
					mGR.mTex_button[5].drawBlack(gl,move-0.05f + (i%3) * .50f, 0.35f - .35f * (i/3),0,0,0, .6f,1);
				}
				break;
			case 5:
				if(mGR.Mana[0]<3 || mGR.Mana[4]<2){
					mGR.mTex_button[5].drawBlack(gl,move-0.05f + (i%3) * .50f, 0.35f - .35f * (i/3),0,0,0, .6f,1);
				}
				break;
			case 6:
				if(mGR.Mana[0]<5 || mGR.Mana[5]<2){
					mGR.mTex_button[5].drawBlack(gl,move-0.05f + (i%3) * .50f, 0.35f - .35f * (i/3),0,0,0, .6f,1);
				}
				break;
			case 7:
				if(mGR.Mana[0]<1){
					mGR.mTex_button[5].drawBlack(gl,move-0.05f + (i%3) * .50f, 0.35f - .35f * (i/3),0,0,0, .6f,1);
				}
				break;
			case 8:
				if(mGR.Mana[0]<3 || mGR.Mana[7]<2){
					mGR.mTex_button[5].drawBlack(gl,move-0.05f + (i%3) * .50f, 0.35f - .35f * (i/3),0,0,0, .6f,1);
				}
				break;
			case 9:
				if(mGR.Mana[0]<5 || mGR.Mana[8]<2){
					mGR.mTex_button[5].drawBlack(gl,move-0.05f + (i%3) * .50f, 0.35f - .35f * (i/3),0,0,0, .6f,1);
				}
				break;
				
			}
			
		}
//		mGR.mPopUp.pop = 1;
		if (mGR.mPopUp.pop == 1 || mGR.mPopUp.pop == 2) {
			DrawTextureS(gl, mGR.mTex_board, .1f, mGR.mPopUp.y, 1.2f, .50f);
			DrawTextureS(gl, mGR.mTex_UpBox, .6f, mGR.mPopUp.y, 2.4f, 1.8f);
			switch (mGR.selPower) {
			case 0:
				mGR.mFont.Draw(gl, "Mana: ", -.54f, mGR.mPopUp.y, 1, 0);
				mGR.mFont.Draw(gl, "Increase Your mana Point. ", -.54f, -.08f+mGR.mPopUp.y, 1, 0);
				mGR.mFont.Draw(gl, "Current: "+(mGR.Mana[0]*20+100)+
						",  Next: "+((mGR.Mana[0]+1)*20+100), -.54f,-.16f+ mGR.mPopUp.y, 1, 0);
				DrawTextureS(gl, mGR.mTex_Crystal, +.48f, mGR.mPopUp.y,1,1);
				mGR.mFont.Draw(gl,""+ (mGR.Mana[mGR.selPower]+1)*5,.59f, +mGR.mPopUp.y, 0, 0);
//				DrawTextureS(gl, mGR.mTex_button[7], .6f, -.18f + mGR.mPopUp.y, 1,1);
				DrawTransScal(gl, mGR.mTex_button[7], .6f, -.18f + mGR.mPopUp.y, mGR.mSel == 51?1.1f:1,mGR.mSel == 51?0.5f:1);
				mGR.mFont.Draw(gl, "Upgrade", .64f, -.17f + mGR.mPopUp.y, 1, 1);
				break;
			case 1:
				mGR.mFont.Draw(gl, "Fire: ", -.54f, mGR.mPopUp.y, 1, 0);
				mGR.mFont.Draw(gl, "Primary: Burn monster.", -.54f, -.08f+mGR.mPopUp.y, 1, 0);
				mGR.mFont.Draw(gl, "Increase Damage and Burn time.", -.54f, -.16f+mGR.mPopUp.y, 1, 0);
				
				if(mGR.Mana[0]<1){
					mGR.mFont.Draw(gl, "Need", .50f,+mGR.mPopUp.y, 0, 0);
					mGR.mFont.Draw(gl, "Mana:  " + (((mGR.selPower-1) % 3 )+1)*2,.50f,-.08f+mGR.mPopUp.y, 1, 0);
					mGR.mFont.Draw(gl, "To unlock", .50f,-.16f+mGR.mPopUp.y, 0, 0);
				}else{
					DrawTextureS(gl, mGR.mTex_Crystal, +.48f, mGR.mPopUp.y,1,1);
					mGR.mFont.Draw(gl,""+ (mGR.Mana[mGR.selPower]+1)*3,.59f, +mGR.mPopUp.y, 0, 0);
//					DrawTextureS(gl, mGR.mTex_button[7], .6f, -.18f + mGR.mPopUp.y, 1,1);
					DrawTransScal(gl, mGR.mTex_button[7], .6f, -.18f + mGR.mPopUp.y, mGR.mSel == 51?1.1f:1,mGR.mSel == 51?0.5f:1);
					mGR.mFont.Draw(gl, "Upgrade", .64f, -.17f + mGR.mPopUp.y, 1, 1);
				}
				
				break;
			case 2:
				mGR.mFont.Draw(gl, "Fire: ", -.54f, mGR.mPopUp.y, 1, 0);
				mGR.mFont.Draw(gl, "Intermediate: Burn monster.", -.54f, -.08f+mGR.mPopUp.y, 1, 0);
				mGR.mFont.Draw(gl, "Increase Damage and Burn time.", -.54f, -.16f+mGR.mPopUp.y, 1, 0);
				if(mGR.Mana[0]<3 || mGR.Mana[1]<2){
					mGR.mFont.Draw(gl, "Need", .50f,+mGR.mPopUp.y, 0, 0);
					mGR.mFont.Draw(gl, "Mana:  " + (((mGR.selPower-1) % 3 )+1)*2,.50f,-.08f+mGR.mPopUp.y, 1, 0);
					mGR.mFont.Draw(gl, "To unlock", .50f,-.16f+mGR.mPopUp.y, 0, 0);
				}else{
					DrawTextureS(gl, mGR.mTex_Crystal, +.48f, mGR.mPopUp.y,1,1);
					mGR.mFont.Draw(gl,""+ (mGR.Mana[mGR.selPower]+1)*4,.59f, +mGR.mPopUp.y, 0, 0);
//					DrawTextureS(gl, mGR.mTex_button[7], .6f, -.18f + mGR.mPopUp.y, 1,1);
					DrawTransScal(gl, mGR.mTex_button[7], .6f, -.18f + mGR.mPopUp.y, mGR.mSel == 51?1.1f:1,mGR.mSel == 51?0.5f:1);
					mGR.mFont.Draw(gl, "Upgrade", .64f, -.17f + mGR.mPopUp.y, 1, 1);
				}
				break;
			case 3:
				mGR.mFont.Draw(gl, "Fire: ", -.54f, mGR.mPopUp.y, 1, 0);
				mGR.mFont.Draw(gl, "Advanced: Burn monster.", -.54f, -.08f+mGR.mPopUp.y, 1, 0);
				mGR.mFont.Draw(gl, "Increase Damage and Burn time.", -.54f, -.16f+mGR.mPopUp.y, 1, 0);
				if(mGR.Mana[0]<5 || mGR.Mana[2]<2){
					mGR.mFont.Draw(gl, "Need", .50f,+mGR.mPopUp.y, 0, 0);
					mGR.mFont.Draw(gl, "Mana:  " + (((mGR.selPower-1) % 3 )+1)*2,.50f,-.08f+mGR.mPopUp.y, 1, 0);
					mGR.mFont.Draw(gl, "To unlock", .50f,-.16f+mGR.mPopUp.y, 0, 0);
				}else{
					DrawTextureS(gl, mGR.mTex_Crystal, +.48f, mGR.mPopUp.y,1,1);
					mGR.mFont.Draw(gl,""+ (mGR.Mana[mGR.selPower]+1)*5,.59f, +mGR.mPopUp.y, 0, 0);
//					DrawTextureS(gl, mGR.mTex_button[7], .6f, -.18f + mGR.mPopUp.y, 1,1);
					DrawTransScal(gl, mGR.mTex_button[7], .6f, -.18f + mGR.mPopUp.y, mGR.mSel == 51?1.1f:1,mGR.mSel == 51?0.5f:1);
					mGR.mFont.Draw(gl, "Upgrade", .64f, -.17f + mGR.mPopUp.y, 1, 1);
				}
				break;
			case 4:
				mGR.mFont.Draw(gl, "Glacial: ", -.54f, mGR.mPopUp.y, 1, 0);
				mGR.mFont.Draw(gl, "Primary: Freez monster.", -.54f, -.08f+mGR.mPopUp.y, 1, 0);
				mGR.mFont.Draw(gl, "Increase Damage and Freez time.", -.54f, -.16f+mGR.mPopUp.y, 1, 0);
				
				if(mGR.Mana[0]<1){
					mGR.mFont.Draw(gl, "Need", .50f,+mGR.mPopUp.y, 0, 0);
					mGR.mFont.Draw(gl, "Mana:  " + (((mGR.selPower-1) % 3 )+1)*2,.50f,-.08f+mGR.mPopUp.y, 1, 0);
					mGR.mFont.Draw(gl, "To unlock", .50f,-.16f+mGR.mPopUp.y, 0, 0);
				}else{
					DrawTextureS(gl, mGR.mTex_Crystal, +.48f, mGR.mPopUp.y,1,1);
					mGR.mFont.Draw(gl,""+ (mGR.Mana[mGR.selPower]+1)*3,.59f, +mGR.mPopUp.y, 0, 0);
//					DrawTextureS(gl, mGR.mTex_button[7], .6f, -.18f + mGR.mPopUp.y, 1,1);
					DrawTransScal(gl, mGR.mTex_button[7], .6f, -.18f + mGR.mPopUp.y, mGR.mSel == 51?1.1f:1,mGR.mSel == 51?0.5f:1);
					mGR.mFont.Draw(gl, "Upgrade", .64f, -.17f + mGR.mPopUp.y, 1, 1);
				}
				break;
			case 5:
				mGR.mFont.Draw(gl, "Glacial: ", -.54f, mGR.mPopUp.y, 1, 0);
				mGR.mFont.Draw(gl, "Intermediate: Freez monster.", -.54f, -.08f+mGR.mPopUp.y, 1, 0);
				mGR.mFont.Draw(gl, "Increase Damage and Freez time.", -.54f, -.16f+mGR.mPopUp.y, 1, 0);
				if(mGR.Mana[0]<3 || mGR.Mana[4]<2){
					mGR.mFont.Draw(gl, "Need", .50f,+mGR.mPopUp.y, 0, 0);
					mGR.mFont.Draw(gl, "Mana:  " + (((mGR.selPower-1) % 3 )+1)*2,.50f,-.08f+mGR.mPopUp.y, 1, 0);
					mGR.mFont.Draw(gl, "To unlock", .50f,-.16f+mGR.mPopUp.y, 0, 0);
				}else{
					DrawTextureS(gl, mGR.mTex_Crystal, +.48f, mGR.mPopUp.y,1,1);
					mGR.mFont.Draw(gl,""+ (mGR.Mana[mGR.selPower]+1)*4,.59f, +mGR.mPopUp.y, 0, 0);
//					DrawTextureS(gl, mGR.mTex_button[7], .6f, -.18f + mGR.mPopUp.y, 1,1);
					DrawTransScal(gl, mGR.mTex_button[7], .6f, -.18f + mGR.mPopUp.y, mGR.mSel == 51?1.1f:1,mGR.mSel == 51?0.5f:1);
					mGR.mFont.Draw(gl, "Upgrade", .64f, -.17f + mGR.mPopUp.y, 1, 1);
				}
				break;
			case 6:
				mGR.mFont.Draw(gl, "Glacial: ", -.54f, mGR.mPopUp.y, 1, 0);
				mGR.mFont.Draw(gl, "Advanced: Freez monster.", -.54f, -.08f+mGR.mPopUp.y, 1, 0);
				mGR.mFont.Draw(gl, "Increase Damage and Freez time.", -.54f, -.16f+mGR.mPopUp.y, 1, 0);
				if(mGR.Mana[0]<5 || mGR.Mana[5]<2){
					mGR.mFont.Draw(gl, "Need", .50f,+mGR.mPopUp.y, 0, 0);
					mGR.mFont.Draw(gl, "Mana:  " + (((mGR.selPower-1) % 3 )+1)*2,.50f,-.08f+mGR.mPopUp.y, 1, 0);
					mGR.mFont.Draw(gl, "To unlock", .50f,-.16f+mGR.mPopUp.y, 0, 0);
				}else{
					DrawTextureS(gl, mGR.mTex_Crystal, +.48f, mGR.mPopUp.y,1,1);
					mGR.mFont.Draw(gl,""+ (mGR.Mana[mGR.selPower]+1)*5,.59f, +mGR.mPopUp.y, 0, 0);
//					DrawTextureS(gl, mGR.mTex_button[7], .6f, -.18f + mGR.mPopUp.y, 1,1);
					DrawTransScal(gl, mGR.mTex_button[7], .6f, -.18f + mGR.mPopUp.y, mGR.mSel == 51?1.1f:1,mGR.mSel == 51?0.5f:1);
					mGR.mFont.Draw(gl, "Upgrade", .64f, -.17f + mGR.mPopUp.y, 1, 1);
				}
				break;
			case 7:
				mGR.mFont.Draw(gl, "Lighting: ", -.54f, mGR.mPopUp.y, 1, 0);
				mGR.mFont.Draw(gl, "Primary: Paralyze monster.", -.54f, -.08f+mGR.mPopUp.y, 1, 0);
				mGR.mFont.Draw(gl, "Increase Damage and paralyze time.", -.54f, -.16f+mGR.mPopUp.y, 1, 0);
				if(mGR.Mana[0]<1){
					mGR.mFont.Draw(gl, "Need", .50f,+mGR.mPopUp.y, 0, 0);
					mGR.mFont.Draw(gl, "Mana:  " + (((mGR.selPower-1) % 3 )+1)*2,.50f,-.08f+mGR.mPopUp.y, 1, 0);
					mGR.mFont.Draw(gl, "To unlock", .50f,-.16f+mGR.mPopUp.y, 0, 0);
				}else{
					DrawTextureS(gl, mGR.mTex_Crystal, +.48f, mGR.mPopUp.y,1,1);
					mGR.mFont.Draw(gl,""+ (mGR.Mana[mGR.selPower]+1)*3,.59f, +mGR.mPopUp.y, 0, 0);
//					DrawTextureS(gl, mGR.mTex_button[7], .6f, -.18f + mGR.mPopUp.y, 1,1);
					DrawTransScal(gl, mGR.mTex_button[7], .6f, -.18f + mGR.mPopUp.y, mGR.mSel == 51?1.1f:1,mGR.mSel == 51?0.5f:1);
					mGR.mFont.Draw(gl, "Upgrade", .64f, -.17f + mGR.mPopUp.y, 1, 1);
				}
				break;
			case 8:
				mGR.mFont.Draw(gl, "Lighting: ", -.54f, mGR.mPopUp.y, 1, 0);
				mGR.mFont.Draw(gl, "Intermediate: Paralyze monster.", -.54f, -.08f+mGR.mPopUp.y, 1, 0);
				mGR.mFont.Draw(gl, "Increase Damage and paralyze time.", -.54f, -.16f+mGR.mPopUp.y, 1, 0);
				if(mGR.Mana[0]<3 || mGR.Mana[7]<2){
					mGR.mFont.Draw(gl, "Need", .50f,+mGR.mPopUp.y, 0, 0);
					mGR.mFont.Draw(gl, "Mana:  " + (((mGR.selPower-1) % 3 )+1)*2,.50f,-.08f+mGR.mPopUp.y, 1, 0);
					mGR.mFont.Draw(gl, "To unlock", .50f,-.16f+mGR.mPopUp.y, 0, 0);
				}else{
					DrawTextureS(gl, mGR.mTex_Crystal, +.48f, mGR.mPopUp.y,1,1);
					mGR.mFont.Draw(gl,""+ (mGR.Mana[mGR.selPower]+1)*4,.59f, +mGR.mPopUp.y, 0, 0);
//					DrawTextureS(gl, mGR.mTex_button[7], .6f, -.18f + mGR.mPopUp.y, 1,1);
					DrawTransScal(gl, mGR.mTex_button[7], .6f, -.18f + mGR.mPopUp.y, mGR.mSel == 51?1.1f:1,mGR.mSel == 51?0.5f:1);
					mGR.mFont.Draw(gl, "Upgrade", .64f, -.17f + mGR.mPopUp.y, 1, 1);
				}
				break;
			case 9:
				mGR.mFont.Draw(gl, "Lighting: ", -.54f, mGR.mPopUp.y, 1, 0);
				mGR.mFont.Draw(gl, "Advanced: Paralyze monster.", -.54f, -.08f+mGR.mPopUp.y, 1, 0);
				mGR.mFont.Draw(gl, "Increase Damage and paralyze time.", -.54f, -.16f+mGR.mPopUp.y, 1, 0);
				if(mGR.Mana[0]<5 || mGR.Mana[8]<2){
					mGR.mFont.Draw(gl, "Need", .50f,+mGR.mPopUp.y, 0, 0);
					mGR.mFont.Draw(gl, "Mana:  " + (((mGR.selPower-1) % 3 )+1)*2,.50f,-.08f+mGR.mPopUp.y, 1, 0);
					mGR.mFont.Draw(gl, "To unlock", .50f,-.16f+mGR.mPopUp.y, 0, 0);
				}else{
					DrawTextureS(gl, mGR.mTex_Crystal, +.48f, mGR.mPopUp.y,1,1);
					mGR.mFont.Draw(gl,""+ (mGR.Mana[mGR.selPower]+1)*5,.59f, +mGR.mPopUp.y, 0, 0);
//					DrawTextureS(gl, mGR.mTex_button[7], .6f, -.18f + mGR.mPopUp.y, 1,1);
					DrawTransScal(gl, mGR.mTex_button[7], .6f, -.18f + mGR.mPopUp.y, mGR.mSel == 51?1.1f:1,mGR.mSel == 51?0.5f:1);
					mGR.mFont.Draw(gl, "Upgrade", .64f, -.17f + mGR.mPopUp.y, 1, 1);
				}
				break;
				
			}
			
			mGR.mPopUp.update();
		}
	}
	void upgrade4(GL10 gl){
		for (int i = 0; i < 3; i++) {
			if (i == 0) {
				if(mGR.selPower == 0)
					DrawTransScal(gl, mGR.mTex_button[8],  -.45f + i * .55f, 0, 1, 1);
				DrawTransScal(gl, mGR.mTex_button[5], -.45f + i * .55f, 0,1, 1);
				DrawTransScal(gl, mGR.mTex_Kila[0], -.45f + i * .55f+.12f, 0,1, 1);
				mGR.mFont.Draw(gl, "Mahal", -.45f + i * .55f, 0, 0, 1);
				mGR.mFont.Draw(gl, ""+(mGR.Tower[0]+1), -.25f + i * .55f, -.05f, 0, 1);
			}
			if (i == 1) {
				if(mGR.selPower == 1)
					DrawTransScal(gl, mGR.mTex_button[8],  -.45f + i * .55f,0.27f, 1, 1);
				DrawTransScal(gl, mGR.mTex_button[5], -.45f + i * .55f,0.27f, 1, 1);
				if(mGR.selPower == 2)
					DrawTransScal(gl, mGR.mTex_button[8],  -.45f + i * .55f,-.27f, 1, 1);
				DrawTransScal(gl, mGR.mTex_button[5], -.45f + i * .55f,-.27f, 1, 1);
				
				DrawTransScal(gl, mGR.mTex_Kila[1], -.45f + i * .55f+.12f,0.27f, 1, 1);
				DrawTransScal(gl, mGR.mTex_Kila[2], -.45f + i * .55f+.12f,-.27f, 1, 1);
				
				mGR.mFont.Draw(gl, "Iron", -.45f + i * .55f,0.33f, 0, 1);
				mGR.mFont.Draw(gl, "Wall", -.45f + i * .55f,0.24f, 0, 1);
				mGR.mFont.Draw(gl, "Vodoo", -.45f + i * .55f,-.21f, 0, 1);
				mGR.mFont.Draw(gl, "Tower", -.45f + i * .55f,-.30f, 0, 1);
				
				if(mGR.Tower[1]>0)
					mGR.mFont.Draw(gl, ""+mGR.Tower[1], -.25f + i * .55f,0.22f, 0, 1);
				if(mGR.Tower[2]>0)
					mGR.mFont.Draw(gl, ""+mGR.Tower[2], -.25f + i * .55f,-.32f, 0, 1);
				
				if(mGR.Tower[0]<3){
					mGR.mTex_button[5].drawBlack(gl,  -.45f + i * .55f,0.27f,0,0,0, .6f, 1);
					mGR.mTex_button[5].drawBlack(gl,  -.45f + i * .55f,-.27f,0,0,0, .6f, 1);
				}
//				if(!mGR.towerUnlock[0])
//					mGR.mTex_button[5].drawBlack(gl,  -.45f + i * .55f,0.27f,0,0,0, .6f, 1);
//				if(!mGR.towerUnlock[1])
//					mGR.mTex_button[5].drawBlack(gl,  -.45f + i * .55f,-.27f,0,0,0, .6f, 1);
			}
			if (i == 2) {
				for (int j = 0; j < 4; j++){
					if(mGR.selPower == j+3)
						DrawTransScal(gl, mGR.mTex_button[8], -.45f + i * .55f		,0.40f - .31f * j, 1, 1);
					DrawTransScal(gl, mGR.mTex_button[5]	, -.45f + i * .55f		,0.40f - .31f * j, 1, 1);
					DrawTransScal(gl, mGR.mTex_Kila[j+3]	, -.45f + i * .55f+.12f	,0.40f - .31f * j, 1, 1);
					mGR.mFont.Draw(gl, mMahal[j]			, -.47f + i * .55f		,0.40f - .31f * j, 0, 1);
					if(mGR.Tower[j+3]>0)
						mGR.mFont.Draw(gl, ""+mGR.Tower[j+3], -.25f + i * .55f		,0.35f - .31f * j, 0, 1);
//					if(!mGR.towerUnlock[j+2])
//						mGR.mTex_button[5].drawBlack(gl,  -.47f + i * .55f		,0.40f - .31f * j,0,0,0, .6f, 1);
					switch (j+3) {
					
					case 3:
						if(mGR.Tower[1]<3){
							mGR.mTex_button[5].drawBlack(gl,  -.45f + i * .55f		,0.40f - .31f * j,0,0,0, .6f, 1);
						}else{
						}
						break;
					case 4:
						if(mGR.Tower[1]<3){
							mGR.mTex_button[5].drawBlack(gl,  -.45f + i * .55f		,0.40f - .31f * j,0,0,0, .6f, 1);
						}else{
						}
						break;
					case 5:
						if(mGR.Tower[2]<3){
							mGR.mTex_button[5].drawBlack(gl,  -.45f + i * .55f		,0.40f - .31f * j,0,0,0, .6f, 1);
						}else{
						}
						break;
					case 6:
						if(mGR.Tower[2]<3){
							mGR.mTex_button[5].drawBlack(gl,  -.45f + i * .55f		,0.40f - .31f * j,0,0,0, .6f, 1);
						}else{
						}
						break;
						
					}
				}
			}
		}
//		mGR.mPopUp.pop = 1;
		if (mGR.mPopUp.pop == 1 || mGR.mPopUp.pop == 2) {
			DrawTextureS(gl, mGR.mTex_board, .1f, mGR.mPopUp.y, 1.2f, .50f);
			DrawTextureS(gl, mGR.mTex_UpBox, .6f, mGR.mPopUp.y, 2.4f, 1.8f);
			switch (mGR.selPower) {
			case 0:
				mGR.mFont.Draw(gl, "Mahal: ", -.54f, mGR.mPopUp.y, 1, 0);
				mGR.mFont.Draw(gl, "Increase Your Tower Defence. ", -.54f, -.08f+mGR.mPopUp.y, 1, 0);
				mGR.mFont.Draw(gl, "Current: "+(mGR.Tower[0]*20+100)+",  Next: "+((mGR.Tower[0]+1)*20+100), -.54f,-.16f+ mGR.mPopUp.y, 1, 0);
				DrawTextureS(gl, mGR.mTex_$, +.48f, mGR.mPopUp.y,1,1);
				mGR.mFont.Draw(gl,""+ (mGR.Tower[mGR.selPower]+1)*1000,.59f, +mGR.mPopUp.y, 0, 0);
//				DrawTextureS(gl, mGR.mTex_button[7], .6f, -.18f + mGR.mPopUp.y, 1,1);
				DrawTransScal(gl, mGR.mTex_button[7], .6f, -.18f + mGR.mPopUp.y, mGR.mSel == 51?1.1f:1,mGR.mSel == 51?0.5f:1);
				mGR.mFont.Draw(gl, "Upgrade", .64f, -.17f + mGR.mPopUp.y, 1, 1);
				break;
			case 1:
				mGR.mFont.Draw(gl, "Iron Wall: ", -.54f, mGR.mPopUp.y, 1, 0);
				mGR.mFont.Draw(gl, "Increase Your Mahal Defence.", -.54f, -.08f+mGR.mPopUp.y, 1, 0);
				mGR.mFont.Draw(gl, "Current: "+(mGR.Tower[1]+1)*20+", " +
						" Next: "+(mGR.Tower[1]+2)*20, -.54f,-.16f+ mGR.mPopUp.y, 1, 0);
				
				if(mGR.Tower[0]<3){
					mGR.mFont.Draw(gl, "Need", .50f,+mGR.mPopUp.y, 0, 0);
					mGR.mFont.Draw(gl, "Mahal:  " + (((mGR.selPower-1) % 3 )+1)*2,.50f,-.08f+mGR.mPopUp.y, 1, 0);
					mGR.mFont.Draw(gl, "To unlock", .50f,-.16f+mGR.mPopUp.y, 0, 0);
				}else{
					DrawTextureS(gl, mGR.mTex_$, +.48f, mGR.mPopUp.y,1,1);
					mGR.mFont.Draw(gl,""+ (mGR.Tower[mGR.selPower]+1)*1000,.59f, +mGR.mPopUp.y, 0, 0);
//					DrawTextureS(gl, mGR.mTex_button[7], .6f, -.18f + mGR.mPopUp.y, 1,1);
					DrawTransScal(gl, mGR.mTex_button[7], .6f, -.18f + mGR.mPopUp.y, mGR.mSel == 51?1.1f:1,mGR.mSel == 51?0.5f:1);
					mGR.mFont.Draw(gl, "Upgrade", .64f, -.17f + mGR.mPopUp.y, 1, 1);
				}
				
				break;
			case 2:
				mGR.mFont.Draw(gl, "Vadoo Tower: ", -.54f, mGR.mPopUp.y, 1, 0);
				mGR.mFont.Draw(gl, "Increase Your Demage Power.", -.54f, -.08f+mGR.mPopUp.y, 1, 0);
				mGR.mFont.Draw(gl, "Current: +"+(mGR.Tower[1]+1)+", " +
						" Next: +"+(mGR.Tower[1]+2), -.54f,-.16f+ mGR.mPopUp.y, 1, 0);
				
				if(mGR.Tower[0]<3){
					mGR.mFont.Draw(gl, "Need", .50f,+mGR.mPopUp.y, 0, 0);
					mGR.mFont.Draw(gl, "Iron Wall:  " + (((mGR.selPower-1) % 3 )+1)*2,.50f,-.08f+mGR.mPopUp.y, 1, 0);
					mGR.mFont.Draw(gl, "To unlock", .50f,-.16f+mGR.mPopUp.y, 0, 0);
				}else{
					DrawTextureS(gl, mGR.mTex_$, +.48f, mGR.mPopUp.y,1,1);
					mGR.mFont.Draw(gl,""+ (mGR.Tower[mGR.selPower]+1)*1000,.59f, +mGR.mPopUp.y, 0, 0);
//					DrawTextureS(gl, mGR.mTex_button[7], .6f, -.18f + mGR.mPopUp.y, 1,1);
					DrawTransScal(gl, mGR.mTex_button[7], .6f, -.18f + mGR.mPopUp.y, mGR.mSel == 51?1.1f:1,mGR.mSel == 51?0.5f:1);
					mGR.mFont.Draw(gl, "Upgrade", .64f, -.17f + mGR.mPopUp.y, 1, 1);
				}
				break;
			case 3:
				mGR.mFont.Draw(gl, "Iron Wall: ", -.54f, mGR.mPopUp.y, 1, 0);
				mGR.mFont.Draw(gl, "Increase Your Mahal Defence.", -.54f, -.08f+mGR.mPopUp.y, 1, 0);
				mGR.mFont.Draw(gl, "Current: "+(mGR.Tower[1]+1)*20+", " +
						" Next: "+(mGR.Tower[1]+2)*20, -.54f,-.16f+ mGR.mPopUp.y, 1, 0);
				
				if(mGR.Tower[1]<3){
					mGR.mFont.Draw(gl, "Need", .50f,+mGR.mPopUp.y, 0, 0);
					mGR.mFont.Draw(gl, "Iron Wall:  " + (((mGR.selPower-1) % 3 )+1)*2,.50f,-.08f+mGR.mPopUp.y, 1, 0);
					mGR.mFont.Draw(gl, "To unlock", .50f,-.16f+mGR.mPopUp.y, 0, 0);
				}else{
					DrawTextureS(gl, mGR.mTex_$, +.48f, mGR.mPopUp.y,1,1);
					mGR.mFont.Draw(gl,""+ (mGR.Tower[mGR.selPower]+1)*2000,.59f, +mGR.mPopUp.y, 0, 0);
//					DrawTextureS(gl, mGR.mTex_button[7], .6f, -.18f + mGR.mPopUp.y, 1,1);
					DrawTransScal(gl, mGR.mTex_button[7], .6f, -.18f + mGR.mPopUp.y, mGR.mSel == 51?1.1f:1,mGR.mSel == 51?0.5f:1);
					mGR.mFont.Draw(gl, "Upgrade", .64f, -.17f + mGR.mPopUp.y, 1, 1);
				}
				break;
			case 4:
				mGR.mFont.Draw(gl, "Iron Wall: ", -.54f, mGR.mPopUp.y, 1, 0);
				mGR.mFont.Draw(gl, "Increase Your Mahal Defence.", -.54f, -.08f+mGR.mPopUp.y, 1, 0);
				mGR.mFont.Draw(gl, "Current: "+(mGR.Tower[1]+1)*20+", " +
						" Next: "+(mGR.Tower[1]+2)*20, -.54f,-.16f+ mGR.mPopUp.y, 1, 0);
				
				if(mGR.Tower[1]<3){
					mGR.mFont.Draw(gl, "Need", .50f,+mGR.mPopUp.y, 0, 0);
					mGR.mFont.Draw(gl, "Mahal:  " + (((mGR.selPower-1) % 3 )+1)*2,.50f,-.08f+mGR.mPopUp.y, 1, 0);
					mGR.mFont.Draw(gl, "To unlock", .50f,-.16f+mGR.mPopUp.y, 0, 0);
				}else{
					DrawTextureS(gl, mGR.mTex_$, +.48f, mGR.mPopUp.y,1,1);
					mGR.mFont.Draw(gl,""+ (mGR.Tower[mGR.selPower]+1)*2000,.59f, +mGR.mPopUp.y, 0, 0);
//					DrawTextureS(gl, mGR.mTex_button[7], .6f, -.18f + mGR.mPopUp.y, 1,1);
					DrawTransScal(gl, mGR.mTex_button[7], .6f, -.18f + mGR.mPopUp.y, mGR.mSel == 51?1.1f:1,mGR.mSel == 51?0.5f:1);
					mGR.mFont.Draw(gl, "Upgrade", .64f, -.17f + mGR.mPopUp.y, 1, 1);
				}
				break;
			case 5:
				mGR.mFont.Draw(gl, "Vadoo Tower: ", -.54f, mGR.mPopUp.y, 1, 0);
				mGR.mFont.Draw(gl, "Increase Your Demage Power.", -.54f, -.08f+mGR.mPopUp.y, 1, 0);
				mGR.mFont.Draw(gl, "Current: +"+(mGR.Tower[1]+1)+", " +
						" Next: +"+(mGR.Tower[1]+2), -.54f,-.16f+ mGR.mPopUp.y, 1, 0);
				
				if(mGR.Tower[2]<3){
					mGR.mFont.Draw(gl, "Need", .50f,+mGR.mPopUp.y, 0, 0);
					mGR.mFont.Draw(gl, "Vadoo Tower:  " + (((mGR.selPower-1) % 3 )+1)*2,.50f,-.08f+mGR.mPopUp.y, 1, 0);
					mGR.mFont.Draw(gl, "To unlock", .50f,-.16f+mGR.mPopUp.y, 0, 0);
				}else{
					DrawTextureS(gl, mGR.mTex_$, +.48f, mGR.mPopUp.y,1,1);
					mGR.mFont.Draw(gl,""+ (mGR.Tower[mGR.selPower]+1)*2000,.59f, +mGR.mPopUp.y, 0, 0);
//					DrawTextureS(gl, mGR.mTex_button[7], .6f, -.18f + mGR.mPopUp.y, 1,1);
					DrawTransScal(gl, mGR.mTex_button[7], .6f, -.18f + mGR.mPopUp.y, mGR.mSel == 51?1.1f:1,mGR.mSel == 51?0.5f:1);
					mGR.mFont.Draw(gl, "Upgrade", .64f, -.17f + mGR.mPopUp.y, 1, 1);
				}
				break;
			case 6:
				mGR.mFont.Draw(gl, "Vadoo Tower: ", -.54f, mGR.mPopUp.y, 1, 0);
				mGR.mFont.Draw(gl, "Increase Your Demage Power.", -.54f, -.08f+mGR.mPopUp.y, 1, 0);
				mGR.mFont.Draw(gl, "Current: +"+(mGR.Tower[1]+1)+", " +
						" Next: +"+(mGR.Tower[1]+2), -.54f,-.16f+ mGR.mPopUp.y, 1, 0);
				
				if(mGR.Tower[2]<3){
					mGR.mFont.Draw(gl, "Need", .50f,+mGR.mPopUp.y, 0, 0);
					mGR.mFont.Draw(gl, "Vadoo Tower:  " + (((mGR.selPower-1) % 3 )+1)*2,.50f,-.08f+mGR.mPopUp.y, 1, 0);
					mGR.mFont.Draw(gl, "To unlock", .50f,-.16f+mGR.mPopUp.y, 0, 0);
				}else{
					DrawTextureS(gl, mGR.mTex_$, +.48f, mGR.mPopUp.y,1,1);
					mGR.mFont.Draw(gl,""+ (mGR.Tower[mGR.selPower]+1)*2000,.59f, +mGR.mPopUp.y, 0, 0);
//					DrawTextureS(gl, mGR.mTex_button[7], .6f, -.18f + mGR.mPopUp.y, 1,1);
					DrawTransScal(gl, mGR.mTex_button[7], .6f, -.18f + mGR.mPopUp.y, mGR.mSel == 51?1.1f:1,mGR.mSel == 51?0.5f:1);
					mGR.mFont.Draw(gl, "Upgrade", .64f, -.17f + mGR.mPopUp.y, 1, 1);
				}
				break;
				
			}
			
			mGR.mPopUp.update();
		}
	}
	String mSMana[]={
			"Fire","Ironfall","Aerolite",
			"Glacial","Frost","JceAge",
			"Lighting","Thunder","Ragnrok",
	};
	
	String mMahal[]={
			"Gold","Pletinum","Magic","Spell"
	};
	
	void Draw_Upgrade(GL10 gl){
		DrawTexture(gl, mGR.mTex_MenuBG, 0, 0);
//		for(int i=0;i<2;i++){
//			DrawTexture(gl, mGR.mTex_Clude[0], cx1[i], 0);
//			if(cx1[0]<-2) cx1[0] = cx1[1] +2;
//			if(cx1[1]<-2) cx1[1] = cx1[0] +2;
//			cx1[i]-=.005f;
//		}
		DrawTextureS(gl, mGR.mTex_board, 0.0f, 0.0f,1.55f,1.20f);
		DrawTransScal(gl, mGR.mTex_button[1]	, -.20f, .85f,mGR.mSel == 96?1.1f:1,mGR.mSel == 96?0.5f:1);
		DrawTexture(gl, mGR.mTex_Key		, -.08f, .85f);
		mGR.mFont.Draw(gl, GameRenderer.mStart.getString(R.string.KEY), -.20f, .92f, 0, 1);
		mGR.mFont.Draw(gl, mGR.mPlayer.TKey+"", -.20f, .83f, 0, 1);
		
		DrawTransScal(gl, mGR.mTex_button[1]	, 0.25f, .85f,mGR.mSel == 97?1.1f:1,mGR.mSel == 97?0.5f:1);
		DrawTexture(gl, mGR.mTex_Crystal	, 0.37f, .85f);
		mGR.mFont.Draw(gl, GameRenderer.mStart.getString(R.string.CRYSTAL), 0.25f, .92f, 0, 1);
		mGR.mFont.Draw(gl, mGR.mPlayer.TCrystal+"", 0.25f, .83f, 0, 1);
		
		DrawTransScal(gl, mGR.mTex_button[1]	, 0.70f, .85f,mGR.mSel == 98?1.1f:1,mGR.mSel == 98?0.5f:1);
		DrawTexture(gl, mGR.mTex_$			, 0.82f, .85f);//.47f
		mGR.mFont.Draw(gl, GameRenderer.mStart.getString(R.string.COINS), 0.70f, .92f, 0, 1);
		mGR.mFont.Draw(gl, mGR.mPlayer.T$+"", 0.70f, .83f, 0, 1);
		
		
		DrawTransScal(gl, mGR.mTex_button[2], 0.81f, -.85f,mGR.mSel == 1?1.1f:1,mGR.mSel == 1?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Play 	, 0.81f, -.85f,mGR.mSel == 1?1.1f:1,mGR.mSel == 1?0.5f:1);
		
		DrawTransScal(gl, mGR.mTex_button[4], -.87f, -.85f,mGR.mSel == 2?1.1f:1,mGR.mSel == 2?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Achiev 	, -.87f, -.85f,mGR.mSel == 2?1.1f:1,mGR.mSel == 2?0.5f:1);
		
		for (int i = 0; i < 4; i++) {
			DrawTransScal(gl, mGR.selUpgrd == i ? mGR.mTex_button[3]:mGR.mTex_Upgrad[4][0],
					-.8f, .53f - .35f * i, mGR.mSel - 3 == i ? 1.1f : 1,mGR.mSel - 3 == i ? 0.5f : 1);
			DrawTransScal(gl, mGR.mTex_Upgrad[i][mGR.selUpgrd == i ? 1 : 0],
					-.8f, .53f - .35f * i, mGR.mSel - 3 == i ? 1.1f : 1,mGR.mSel - 3 == i ? 0.5f : 1);
		}
		gl.glEnable(GL10.GL_SCISSOR_TEST);
		gl.glScissor((int)world2screenx(-.69f),0, (int)(M.ScreenWidth*.8f), (int)(M.ScreenHieght));
		switch (mGR.selUpgrd) {
		case 0:
			upgrade1(gl);
			break;
		case 1:
			upgrade2(gl);
			break;
		case 2:
			upgrade3(gl);
			break;
		case 3:
			upgrade4(gl);
			break;
		}
		gl.glDisable(GL10.GL_SCISSOR_TEST);
		
		mGR.mFont.Draw(gl, "Coins: "+mGR.mPlayer.T$, 0,-.8f, 0, 1);
		mGR.mFont.Draw(gl, "Level: "+mGR.mLevel, -.73f,0.85f, 0, 1);
		
//		for(int i=0;i<2;i++){
//			DrawTexture(gl, mGR.mTex_Clude[1], cx2[i], 0);
//			if(cx2[0] < -2) cx2[0] = cx2[1]+2;
//			if(cx2[1] < -2) cx2[1] = cx2[0]+2;
//			cx2[i]-=.002f;
//		}
	} 
	float old = 0;
	void notEnof(){
		GameRenderer.mStart.notEnof();
	}
	boolean Handle_Upgrade(MotionEvent event){
		mGR.mSel = 0;
		if(CircRectsOverlap(0.81f,-.85f, mGR.mTex_button[2].width()*.4f, mGR.mTex_button[2].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 1;
		}
		if(CircRectsOverlap(-.87f,-.85f, mGR.mTex_button[4].width()*.4f, mGR.mTex_button[4].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 2;
		}
		for(int i=0;i<4;i++){
			if(CircRectsOverlap(-.8f, .53f-.35f*i, mGR.mTex_Upgrad[4][0].width()*.3f, mGR.mTex_Upgrad[4][0].Height()*.3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
				mGR.mSel = i+3;
			}
		}
		if(mGR.mPopUp.pop == 0 || (mGR.mPopUp.y > 1.2f)){
		if(CircRectsOverlap(-.20f, .85f, mGR.mTex_button[1].width()*.4f, mGR.mTex_button[1].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 96;
		}
		if(CircRectsOverlap(0.25f, .85f, mGR.mTex_button[1].width()*.4f, mGR.mTex_button[1].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 97;
		}
		if(CircRectsOverlap(0.70f, .85f, mGR.mTex_button[1].width()*.4f, mGR.mTex_button[1].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 98;
		}
		}else{
			if(CircRectsOverlap(.6f, -.25f+mGR.mPopUp.y, mGR.mTex_button[7].width()*.6f, mGR.mTex_button[7].Height()*.8f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
				mGR.mSel = 51;
			}
		}
		if (MotionEvent.ACTION_DOWN == event.getAction())
			init = screen2worldX(event.getX());
		
		
		float dif = screen2worldX(event.getX()) - init;
		if(mGR.selUpgrd == 1)
		{
			if(move < 0f && dif > 0){
				move = dif+old;
			}
			if(move >- 1.5f && dif < 0){
				move = dif+old;
			}
		}
		if(mGR.selUpgrd == 2){
			if(move < 0 && dif > 0){
				move = dif+old;
			}
			if(move >- 0.3f && dif < 0){
				move = dif+old;
			}
		}
		boolean upgarde = false;
		
		
		if (MotionEvent.ACTION_UP == event.getAction()) {
			old = move;
			
			switch (mGR.selUpgrd){
			case 0:
				mGR.mPopUp.pop = 2;
				for (int i = 0; i < 3; i++) {
					if (i == 0) {
						if(CircRectsOverlap(-.45f + i * .55f,+0.20f, mGR.mTex_button[5].width()*.4f, mGR.mTex_button[5].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
							mGR.selPower = 0;
							mGR.mPopUp.pop = 1;
						}
						if(CircRectsOverlap(-.45f + i * .55f,-0.20f, mGR.mTex_button[5].width()*.4f, mGR.mTex_button[5].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
							mGR.selPower = 1;
							mGR.mPopUp.pop = 1;
						}
					}
					if (i == 1) {
						for (int j = 0; j < 3; j++){
							if(CircRectsOverlap(-.45f + i * .55f,0.35f - .35f * j, mGR.mTex_button[5].width()*.4f, mGR.mTex_button[5].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
								mGR.selPower = j+2;
								mGR.mPopUp.pop = 1;
							}
						}
					}
					if (i == 2) {
						if(CircRectsOverlap(-.45f + i * .55f,0, mGR.mTex_button[5].width()*.4f, mGR.mTex_button[5].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
							mGR.selPower = 5;
							mGR.mPopUp.pop = 1;
						}
					}
					
				}
				if(CircRectsOverlap(.6f, -.25f+mGR.mPopUp.y, mGR.mTex_button[7].width()*.6f, mGR.mTex_button[7].Height()*.8f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
					mGR.mPopUp.pop = 1;
					int req= 0;
					switch (mGR.selPower){
					case 0:
						req= (mGR.uPower[mGR.selPower]+1)*100;
						if(req<=mGR.mPlayer.T$){
							mGR.mPlayer.T$-=req;
							mGR.uPower[mGR.selPower]++;
							upgarde = true;
						}else{notEnof();}
						break;
					case 1:
						req= (mGR.uPower[mGR.selPower]+1)*110;
						if(req<=mGR.mPlayer.T$){
							mGR.mPlayer.T$-=req;
							mGR.uPower[mGR.selPower]++;
							upgarde = true;
						}else{notEnof();}
						break;
					case 2:
						if (mGR.uPower[0] >= 1){
							req= (mGR.uPower[mGR.selPower]+1)*500;
							if(req<=mGR.mPlayer.T$){
								mGR.mPlayer.T$-=req;
								mGR.uPower[mGR.selPower]++;
								upgarde = true;
							}else{notEnof();}
						}
						break;
					case 3:
						if ((mGR.uPower[0] >= 3 || mGR.uPower[1] >= 3)){
							req= (mGR.uPower[mGR.selPower]+1)*450;
							if(req<=mGR.mPlayer.T$){
								mGR.mPlayer.T$-=req;
								mGR.uPower[mGR.selPower]++;
								upgarde = true;
							}else{notEnof();}
						}
						break;
					case 4:
						if (mGR.uPower[1] > 0){
							req=(mGR.uPower[mGR.selPower]+1)*600;
							if(req<=mGR.mPlayer.T$){
								mGR.uPower[mGR.selPower]++;
								mGR.mPlayer.T$-=req;
								upgarde = true;
							}else{notEnof();}
						}
						break;
					case 5:
						if (mGR.uPower[2] >= 3 && mGR.uPower[4] >= 3){
							req=(mGR.uPower[mGR.selPower]+1)*5000;
							if(req<=mGR.mPlayer.T$){
								mGR.uPower[mGR.selPower]++;
								mGR.mPlayer.T$-=req;
								upgarde = true;
							}else{notEnof();}
						}
						break;
					}
				}
				break;
			case 1:
				mGR.mPopUp.pop = 2;
				if(CircRectsOverlap(move-.54f ,0, mGR.mTex_button[3].width()*.4f, mGR.mTex_button[3].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
					mGR.selPower = 100;
					mGR.mPopUp.pop = 1;
				}
				for (int i = 0; i < 9; i++)
				{
					for (int j = 0; j < 3; j++)
					{
						if(CircRectsOverlap(move-.24f + i * .30f,0.40f-.40f*j, mGR.mTex_button[3].width()*.4f, mGR.mTex_button[3].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
							mGR.selPower = i*3+j;
							mGR.mPopUp.pop = 1;
//							mGR.BowPower[mGR.selPower] = true;
						}
					}
				}
				
				if(CircRectsOverlap(.6f, -.18f+mGR.mPopUp.y, mGR.mTex_button[7].width()*.4f, mGR.mTex_button[7].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
					mGR.mPopUp.pop = 1;
					if (mGR.mLevel>= (mGR.selPower / 3)*5) {
						switch (mGR.selPower % 3) {
						case 0:
							if(mGR.mPlayer.T$ >= (mGR.selPower / 3 + 1) * 500){
								mGR.BowPower[mGR.selPower] = true;
								mGR.mPlayer.T$ -= (mGR.selPower / 3 + 1) * 500;
								upgarde = true;
							}else{notEnof();}
							break;
						case 1:
							if(mGR.mPlayer.T$ >= (((mGR.selPower / 3 + 1) * 500) + (mGR.selPower / 3 + 1) * 200)){
								mGR.BowPower[mGR.selPower] = true;
								mGR.mPlayer.T$ -= (((mGR.selPower / 3 + 1) * 500) + (mGR.selPower / 3 + 1) * 200);
								upgarde = true;
							}else{notEnof();}
							break;
						case 2:
							if(mGR.mPlayer.TCrystal >= (((mGR.selPower / 3 + 1) * 5))){
								mGR.BowPower[mGR.selPower] = true;
								mGR.mPlayer.TCrystal -= (((mGR.selPower / 3 + 1) * 5));
								upgarde = true;
							}else{notEnof();}
							break;
						}
						if(mGR.BowPower[mGR.selPower])
							mGR.mPlayer.SelBow =mGR.selPower;
					}
				}
				
				
				
				break;
			case 2:
				mGR.mPopUp.pop = 2;
				if(CircRectsOverlap(move-.45f , 0, mGR.mTex_button[5].width()*.4f, mGR.mTex_button[5].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
					mGR.selPower = 0;
					mGR.mPopUp.pop = 1;
				}
				for (int i = 0; i < 9; i++) {
					
					if(CircRectsOverlap(move-0.05f + (i%3) * .50f, 0.35f - .35f * (i/3), mGR.mTex_button[5].width()*.4f, mGR.mTex_button[5].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
						mGR.selPower = i+1;
						mGR.mPopUp.pop = 1;
					}
				}
				if(CircRectsOverlap(.6f, -.18f+mGR.mPopUp.y, mGR.mTex_button[7].width()*.4f, mGR.mTex_button[7].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
					mGR.mPopUp.pop = 1;
					switch (mGR.selPower) {
					case 0:
						if (mGR.mPlayer.TCrystal >= ((mGR.Mana[mGR.selPower] + 1) * 5)){
							mGR.mPlayer.TCrystal -= ((mGR.Mana[mGR.selPower] + 1) * 5);
							mGR.Mana[mGR.selPower]++;
							upgarde = true;
						}
						break;
					case 1:
						if (mGR.mPlayer.TCrystal >= ((mGR.Mana[mGR.selPower] + 1) * 3)
								&& mGR.Mana[0] >= 1){
							mGR.mPlayer.TCrystal -= ((mGR.Mana[mGR.selPower] + 1) * 3);
							mGR.Mana[mGR.selPower]++;
							upgarde = true;
						}
						break;
					case 2:
						if (mGR.mPlayer.TCrystal >= ((mGR.Mana[mGR.selPower] + 1) * 4)
								&& mGR.Mana[0] >= 3 && mGR.Mana[1] >= 2){
							mGR.mPlayer.TCrystal -= ((mGR.Mana[mGR.selPower] + 1) * 4);
							mGR.Mana[mGR.selPower]++;
							upgarde = true;
						}
						break;
					case 3:
						if (mGR.mPlayer.TCrystal >= ((mGR.Mana[mGR.selPower] + 1) * 5)
								&& mGR.Mana[0] >= 5 && mGR.Mana[2] >= 2){
							mGR.mPlayer.TCrystal -= ((mGR.Mana[mGR.selPower] + 1) * 5);
							mGR.Mana[mGR.selPower]++;
							upgarde = true;
						}
						break;
					case 4:
						if (mGR.mPlayer.TCrystal >= ((mGR.Mana[mGR.selPower] + 1) * 3)
								&& mGR.Mana[0] >= 1){
							mGR.mPlayer.TCrystal -= ((mGR.Mana[mGR.selPower] + 1) * 3);
							mGR.Mana[mGR.selPower]++;
							upgarde = true;
						}
						break;
					case 5:
						if (mGR.mPlayer.TCrystal >= ((mGR.Mana[mGR.selPower] + 1) * 4)
								&& mGR.Mana[0] >= 3 && mGR.Mana[4] >= 2){
							mGR.mPlayer.TCrystal -= ((mGR.Mana[mGR.selPower] + 1) * 4);
							mGR.Mana[mGR.selPower]++;
							upgarde = true;
						}
						break;
					case 6:
						if (mGR.mPlayer.TCrystal >= ((mGR.Mana[mGR.selPower] + 1) * 5)
								&& mGR.Mana[0] >= 5 && mGR.Mana[5] >= 2){
							mGR.mPlayer.TCrystal -= ((mGR.Mana[mGR.selPower] + 1) * 5);
							mGR.Mana[mGR.selPower]++;
							upgarde = true;
						}
						break;
					case 7:
						if (mGR.mPlayer.TCrystal >= ((mGR.Mana[mGR.selPower] + 1) * 3)
								&& mGR.Mana[0] >= 1){
							mGR.mPlayer.TCrystal -= ((mGR.Mana[mGR.selPower] + 1) * 3);
							mGR.Mana[mGR.selPower]++;
							upgarde = true;
							}
						break;
					case 8:
						if (mGR.mPlayer.TCrystal >= ((mGR.Mana[mGR.selPower] + 1) * 4)
								&& mGR.Mana[0] >= 3 && mGR.Mana[7] >= 2){
							mGR.mPlayer.TCrystal -= ((mGR.Mana[mGR.selPower] + 1) * 4);
							mGR.Mana[mGR.selPower]++;
							upgarde = true;
						}
						break;
					case 9:
						if (mGR.mPlayer.TCrystal >= ((mGR.Mana[mGR.selPower] + 1) * 5)
								&& mGR.Mana[0] >= 5 && mGR.Mana[8] >= 2){
							mGR.mPlayer.TCrystal -= ((mGR.Mana[mGR.selPower] + 1) * 5);
							mGR.Mana[mGR.selPower]++;
							upgarde = true;
						}
						break;

					}
					mGR.mPopUp.update();
				}
				break;
			case 3:
				mGR.mPopUp.pop = 2;
				for (int i = 0; i < 3; i++) {
					if (i == 0) {
						if(CircRectsOverlap(-.45f + i * .55f, 0, mGR.mTex_button[5].width()*.4f, mGR.mTex_button[5].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
							mGR.selPower = 0;
							mGR.mPopUp.pop = 1;
						}
					}
					if (i == 1) {
						
						if(CircRectsOverlap(-.45f + i * .55f,0.27f, mGR.mTex_button[5].width()*.4f, mGR.mTex_button[5].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
							mGR.selPower = 1;
							mGR.mPopUp.pop = 1;
						}
						if(CircRectsOverlap(-.45f + i * .55f,-.27f, mGR.mTex_button[5].width()*.4f, mGR.mTex_button[5].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
							mGR.selPower = 2;
							mGR.mPopUp.pop = 1;
						}
					}
					if (i == 2) {
						for (int j = 0; j < 4; j++){
							if(CircRectsOverlap(-.45f + i * .55f,0.40f - .31f * j, mGR.mTex_button[5].width()*.4f, mGR.mTex_button[5].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
								mGR.selPower = j+3;
								mGR.mPopUp.pop = 1;
							}
						}
					}
				}
				if(CircRectsOverlap(.6f, -.18f+mGR.mPopUp.y, mGR.mTex_button[7].width()*.4f, mGR.mTex_button[7].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
					mGR.mPopUp.pop = 1;
					switch (mGR.selPower) {
					case 0:
						if(mGR.mPlayer.T$>=((mGR.Tower[mGR.selPower]+1)*1000)){
							mGR.mPlayer.T$-=((mGR.Tower[mGR.selPower]+1)*1000);
							mGR.Tower[mGR.selPower]++;
							upgarde = true;
						}else{notEnof();}
						break;
					case 1:
						
						if(mGR.Tower[0]<3){ 
							if(mGR.mPlayer.T$>=((mGR.Tower[mGR.selPower]+1)*1000)){
								mGR.mPlayer.T$-=((mGR.Tower[mGR.selPower]+1)*1000);
								mGR.Tower[mGR.selPower]++;
								upgarde = true;
							}else{notEnof();}
						}
						break;
					case 2:
						if (mGR.Tower[0] < 3) {
							if (mGR.mPlayer.T$ >= ((mGR.Tower[mGR.selPower] + 1) * 1000)) {
								mGR.mPlayer.T$ -= ((mGR.Tower[mGR.selPower] + 1) * 1000);
								mGR.Tower[mGR.selPower]++;
								upgarde = true;
							} else {
								notEnof();
							}
						}
						break;
					case 3:
						if (mGR.Tower[1] < 3) {
							if (mGR.mPlayer.T$ >= ((mGR.Tower[mGR.selPower] + 1) * 2000)) {
								mGR.mPlayer.T$ -= ((mGR.Tower[mGR.selPower] + 1) * 2000);
								mGR.Tower[mGR.selPower]++;
								upgarde = true;
							} else {
								notEnof();
							}
						}
						break;
					case 4:
						if (mGR.Tower[1] < 3) {
							if (mGR.mPlayer.T$ >= ((mGR.Tower[mGR.selPower] + 1) * 2000)) {
								mGR.mPlayer.T$ -= ((mGR.Tower[mGR.selPower] + 1) * 2000);
								mGR.Tower[mGR.selPower]++;
								upgarde = true;
							} else {
								notEnof();
							}
						}
						break;
					case 5:
						if (mGR.Tower[2] < 3) {
							if (mGR.mPlayer.T$ >= ((mGR.Tower[mGR.selPower] + 1) * 2000)) {
								mGR.mPlayer.T$ -= ((mGR.Tower[mGR.selPower] + 1) * 2000);
								mGR.Tower[mGR.selPower]++;
								upgarde = true;
							} else {
								notEnof();
							}
						}
						break;
					case 6:
						if (mGR.Tower[2] < 3) {
							if (mGR.mPlayer.T$ >= ((mGR.Tower[mGR.selPower] + 1) * 2000)) {
								mGR.mPlayer.T$ -= ((mGR.Tower[mGR.selPower] + 1) * 2000);
								mGR.Tower[mGR.selPower]++;
								upgarde = true;
							} else {
								notEnof();
							}
						}
						break;
						
					}
					
					mGR.mPopUp.update();
				
					
				}
				break;
			}
			
			
			
			
			switch (mGR.mSel) {
			case 1:
				M.playGame(GameRenderer.mContext);
				M.GameScreen =M.GAMEPLAY;
				mGR.gameReset();
				mGR.mPopUp.pop = 2;
				//Play
				break;
			case 2:
				mGR.mPopUp.pop = 2;
				GameRenderer.mStart.onShowAchievementsRequested();//Achievement
				break;
			case 3:case 4:case 5:case 6:
				mGR.selUpgrd = mGR.mSel-3;
				mGR.selPower = 0;
				old =move =0;
				mGR.mPopUp.pop = 2;
				break;
			case 96:case 97:case 98:
//				M.GameScreen = M.GAMEBUY;
				notEnof();
				break;
			}
			Achivment();
			if(upgarde){
				M.sound7(GameRenderer.mContext, R.drawable.button_upgrade);
			}
			else if(mGR.mSel != 0 || mGR.mPopUp.pop == 1)
				M.sound6(GameRenderer.mContext, R.drawable.button_click);
			mGR.mSel = 0;
		}
		return true;
	}
	/************************Upgrade End*************************/
	/************************Menu Start*************************/
	void Draw_Menu(GL10 gl){
		DrawTexture(gl, mGR.mTex_MenuBG, 0, 0);
//		for(int i=0;i<2;i++){
//			DrawTexture(gl, mGR.mTex_Clude[0], cx1[i], 0);
//			if(cx1[0]<-2) cx1[0] = cx1[1] +2;
//			if(cx1[1]<-2) cx1[1] = cx1[0] +2;
//			cx1[i]-=.005f;
//		}
		
		DrawTexture(gl, mGR.mTex_board, 0.0f, 0.1f);
		DrawTexture(gl, mGR.mTex_Dot[page == 1 ? 0 : 1], -.03f, -.41f);
		DrawTexture(gl, mGR.mTex_Dot[page == 0 ? 0 : 1], 0.03f, -.41f);

		for (int i = 0; i < 15 && i + page * 15 < 26; i++) {
			if(mGR.Achi[i + page * 15])
				DrawTexture(gl, mGR.mTex_AchI[i + page * 15]	, move-.44f+.22f*(i%5), .50f-.35f*(i/5));
			else
				DrawTransScal(gl, mGR.mTex_AchI[i + page * 15]	, move-.44f+.22f*(i%5), .50f-.35f*(i/5),1,.5f);
		}
		if (move > 0 && page > 0){
			for (int i = 0; i < 15 && i + (page-1) * 15 < 26; i++) {
				if(mGR.Achi[i + (page-1) * 15])
					DrawTexture(gl, mGR.mTex_AchI[i + (page-1) * 15]	, move-.44f+.22f*(i%5)-2, .50f-.35f*(i/5));
				else
					DrawTransScal(gl, mGR.mTex_AchI[i + (page-1) * 15]	, move-.44f+.22f*(i%5)-2, .50f-.35f*(i/5),1,.5f);
			}
		}
		if(move < 0 && page < 1){
			for (int i = 0; i < 15 && i + (page+1) * 15 < 26; i++) {
				if(mGR.Achi[i + (page+1) * 15])
					DrawTexture(gl, mGR.mTex_AchI[ i + (page+1) * 15]	, move-.44f+.22f*(i%5)+2, .50f-.35f*(i/5));
				else
					DrawTransScal(gl, mGR.mTex_AchI[ i + (page+1) * 15]	, move-.44f+.22f*(i%5)+2, .50f-.35f*(i/5),1,.5f);
			}
		}
		
		if (ismove == true) {
			if (move > 0) {
				move += page == 0 ? -.1f : .1f;
				if (move > 1.9f || move < .1f) {
					ismove = false;
					page = 0;
					move = 0;
				}
			} else {
				move += page == 1 ? 0.1f : -.1f;
				if (move < -1.9f||move > -0.1f) {
					page = 1;
					ismove = false;
					move = 0;
				}
			}

		}
		DrawTransScal(gl, mGR.mTex_button[1]	, -.20f, .85f,mGR.mSel == 6?1.1f:1,mGR.mSel == 6?0.5f:1);
		DrawTexture(gl, mGR.mTex_Key		, -.08f, .85f);
		mGR.mFont.Draw(gl, GameRenderer.mStart.getString(R.string.KEY), -.20f, .92f, 0, 1);
		mGR.mFont.Draw(gl, mGR.mPlayer.TKey+"", -.20f, .83f, 0, 1);
		
		DrawTransScal(gl, mGR.mTex_button[1]	, 0.25f, .85f,mGR.mSel == 7?1.1f:1,mGR.mSel == 7?0.5f:1);
		DrawTexture(gl, mGR.mTex_Crystal	, 0.37f, .85f);
		mGR.mFont.Draw(gl, GameRenderer.mStart.getString(R.string.CRYSTAL), 0.25f, .92f, 0, 1);
		mGR.mFont.Draw(gl, mGR.mPlayer.TCrystal+"", 0.25f, .83f, 0, 1);
		
		DrawTransScal(gl, mGR.mTex_button[1]	, 0.70f, .85f,mGR.mSel == 8?1.1f:1,mGR.mSel == 8?0.5f:1);
		DrawTexture(gl, mGR.mTex_$			, 0.82f, .85f);//.47f
		mGR.mFont.Draw(gl, GameRenderer.mStart.getString(R.string.COINS), 0.70f, .92f, 0, 1);
		mGR.mFont.Draw(gl, mGR.mPlayer.T$+"", 0.70f, .83f, 0, 1);
		
		
		DrawTransScal(gl, mGR.mTex_button[0], -.71f, .85f,mGR.mSel == 1?1.1f:1,mGR.mSel == 1?0.5f:1);
		DrawTransScal(gl, mGR.mTex_upgrade	, -.71f, .85f,mGR.mSel == 1?1.1f:1,mGR.mSel == 1?0.5f:1);

		DrawTransScal(gl, mGR.mTex_button[0], -.00f, -.56f,mGR.mSel == 2?1.1f:1,mGR.mSel == 2?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Continue	, -.00f, -.56f,mGR.mSel == 2?1.1f:1,mGR.mSel == 2?0.5f:1);
		
		DrawTransScal(gl, mGR.mTex_button[4], 0.87f, -.85f,mGR.mSel == 3?1.1f:1,mGR.mSel == 3?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Achiev 	, 0.87f, -.85f,mGR.mSel == 3?1.1f:1,mGR.mSel == 3?0.5f:1);
		
		DrawTransScal(gl, mGR.mTex_button[4], 0.87f, -.50f,mGR.mSel == 4?1.1f:1,mGR.mSel == 4?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Leader 	, 0.87f, -.50f,mGR.mSel == 4?1.1f:1,mGR.mSel == 4?0.5f:1);
		
		DrawTransScal(gl, mGR.mTex_button[4], -.87f, -.85f,mGR.mSel == 5?1.1f:1,mGR.mSel == 5?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Setting 	, -.87f, -.85f,mGR.mSel == 5?1.1f:1,mGR.mSel == 5?0.5f:1);
		
//		for(int i=0;i<2;i++){
//			DrawTexture(gl, mGR.mTex_Clude[1], cx2[i], 0);
//			if(cx2[0] < -2) cx2[0] = cx2[1]+2;
//			if(cx2[1] < -2) cx2[1] = cx2[0]+2;
//			cx2[i]-=.002f;
//		}
	}
	int page =0;
	float init =0;
	float move =0;
	boolean ismove = false;
	boolean Handle_Menu(MotionEvent event){
		mGR.mSel = 0;
		if(CircRectsOverlap(-.71f, .85f, mGR.mTex_button[0].width()*.4f, mGR.mTex_button[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 1;
		}
		if(CircRectsOverlap(-.00f, -.56f, mGR.mTex_button[0].width()*.4f, mGR.mTex_button[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 2;
		}
		if(CircRectsOverlap(0.87f,-.85f, mGR.mTex_button[4].width()*.4f, mGR.mTex_button[4].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 3;
		}
		if(CircRectsOverlap(0.87f,-.50f, mGR.mTex_button[4].width()*.4f, mGR.mTex_button[4].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 4;
		}
		if(CircRectsOverlap(-.87f,-.85f, mGR.mTex_button[4].width()*.4f, mGR.mTex_button[4].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 5;
		}
		if(CircRectsOverlap(-.20f, .85f, mGR.mTex_button[1].width()*.4f, mGR.mTex_button[1].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 6;
		}
		if(CircRectsOverlap(0.25f, .85f, mGR.mTex_button[1].width()*.4f, mGR.mTex_button[1].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 7;
		}
		if(CircRectsOverlap(0.70f, .85f, mGR.mTex_button[1].width()*.4f, mGR.mTex_button[1].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 8;
		}
		if (MotionEvent.ACTION_DOWN == event.getAction())
			init = screen2worldX(event.getX());
		
		move = screen2worldX(event.getX()) - init;
		if (MotionEvent.ACTION_UP == event.getAction()) {
			if(Math.abs(move) > .1f){
				ismove = true;
			}else
				move =0;
			switch (mGR.mSel) {
			case 1:
				M.GameScreen = M.GAMEUPGR;//Upgrade
				break;
			case 2:
				M.GameScreen = M.GAMEUPGR;//Continue
				break;
			case 3:
				GameRenderer.mStart.onShowAchievementsRequested();				//Achievement
				break;
			case 4:
				GameRenderer.mStart.onShowLeaderboardsRequested();//Leader-Board
				break;
			case 5:
				M.GameScreen = M.GAMESETI;//Setting
				break;
			case 6:case 7:case 8:
//				M.GameScreen = M.GAMEBUY;
				notEnof();
				break;
			}
			if(mGR.mSel != 0)
				M.sound6(GameRenderer.mContext, R.drawable.button_click);
			mGR.mSel = 0;
		}
		return true;
	}
	/************************Menu End*************************/
	
	
	/************************Over Start*************************/
	void Draw_Over(GL10 gl){
		if(Counter == 60){
			M.play(GameRenderer.mContext, R.drawable.splash_other);
		}
		if(Counter == 10){
			M.sound8(GameRenderer.mContext, R.drawable.counting);
		}
		DrawTexture(gl, mGR.mTex_BG[mGR.mPlayer.bgNo], 0, 0);
		DrawTexture(gl, mGR.mTex_board, 0.0f, 0.1f);
		if(M.GameScreen == M.GAMEWIN)
			DrawTexture(gl, mGR.mTex_Win, 0.0f, .57f);
		else
			DrawTexture(gl, mGR.mTex_GameOver, 0.0f, .57f);
		DrawTextureS(gl, mGR.mTex_infoBox, 0.15f, .38f - .17f * 0,2.0f,1);
		
		
		if(mGR.Tdamy<mGR.mPlayer.TKill){
			mGR.Tdamy+=mGR.damy;
			if(mGR.Tdamy>mGR.mPlayer.TKill)
				mGR.Tdamy=mGR.mPlayer.TKill;
		}
		int total =0;
		for(int i=0;total<mGR.Tdamy;i++){
			total +=(i)*100;
			mGR.mLevel = i; 
			if(total>mGR.Tdamy){
				break;
			}
		}
		
		
		
		
		mGR.mFont.Draw(gl, GameRenderer.mStart.getString(R.string.LEVEL), -.30f,  .40f - .17f * 0, 1, 0);
		mGR.mTex_lifebar[1].drawSS(gl, -.14f, 0.38f, (30f/(mGR.mLevel*100f))*((mGR.mLevel*100f)-(total-mGR.Tdamy)), 1);//16
		mGR.mFont.Draw(gl, (((mGR.mLevel)*100)-(total-mGR.Tdamy))+"/"+(mGR.mLevel*100), 0.20f,  0.40f, 0, 1);
		
		DrawTextureS(gl, mGR.mTex_infoBox, 0.00f, .38f - .17f * 1,1.0f,1);
		mGR.mFont.Draw(gl, GameRenderer.mStart.getString(R.string.KILL), -.27f,  .40f - .17f * 1, 1, 0);
		mGR.mFont.Draw(gl, ""+mGR.mPlayer.kill, -.08f, .40f - .17f * 1, 0, 0);
		
		DrawTextureS(gl, mGR.mTex_infoBox, 0.03f, .38f - .17f * 2,1.2f,1);
		mGR.mFont.Draw(gl, GameRenderer.mStart.getString(R.string.SCORE), -.30f,  .40f - .17f * 2, 1, 0);
		mGR.mFont.Draw(gl, ""+mGR.mPlayer.Score, -.08f, .40f - .17f * 2, 0, 0);
		
		DrawTextureS(gl, mGR.mTex_infoBox, -.06f, .38f - .17f * 3,0.6f,1);
		mGR.mFont.Draw(gl, GameRenderer.mStart.getString(R.string.CRYSTAL), -.35f,  .40f - .17f * 3, 1, 0);
		mGR.mFont.Draw(gl, ""+mGR.mPlayer.Crystal, -.08f, .40f - .17f * 3, 0, 0);
		
		DrawTextureS(gl, mGR.mTex_infoBox, 0.00f, .38f - .17f * 4,1.0f,1);
		mGR.mFont.Draw(gl, "Bonus "+GameRenderer.mStart.getString(R.string.COINS),-.46f,  .40f - .17f * 4, 1, 0);
		mGR.mFont.Draw(gl, ""+mGR.mPlayer.Coints, -.08f, .40f - .17f * 4, 0, 0);
		
		
		DrawTexture(gl, mGR.mTex_button[1]	, -.20f, .85f);
		DrawTexture(gl, mGR.mTex_Key		, -.08f, .85f);
		mGR.mFont.Draw(gl, GameRenderer.mStart.getString(R.string.KEY), -.20f, .92f, 0, 1);
		mGR.mFont.Draw(gl, mGR.mPlayer.TKey+"", -.20f, .83f, 0, 1);
		
		DrawTexture(gl, mGR.mTex_button[1]	, 0.25f, .85f);
		DrawTexture(gl, mGR.mTex_Crystal	, 0.37f, .85f);
		mGR.mFont.Draw(gl, GameRenderer.mStart.getString(R.string.CRYSTAL), 0.25f, .92f, 0, 1);
		mGR.mFont.Draw(gl, mGR.mPlayer.TCrystal+"", 0.25f, .83f, 0, 1);
		
		DrawTexture(gl, mGR.mTex_button[1]	, 0.70f, .85f);
		DrawTexture(gl, mGR.mTex_$			, 0.82f, .85f);//.47f
		mGR.mFont.Draw(gl, GameRenderer.mStart.getString(R.string.COINS), 0.70f, .92f, 0, 1);
		mGR.mFont.Draw(gl, mGR.mPlayer.T$+"", 0.70f, .83f, 0, 1);
		
		
//		DrawTransScal(gl, mGR.mTex_button[0], -.71f, .85f,mGR.mSel == 1?1.1f:1,mGR.mSel == 1?0.5f:1);
//		DrawTransScal(gl, mGR.mTex_upgrade	, -.71f, .85f,mGR.mSel == 1?1.1f:1,mGR.mSel == 1?0.5f:1);

		DrawTransScal(gl, mGR.mTex_button[0], -.00f, -.56f,mGR.mSel == 2?1.1f:1,mGR.mSel == 2?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Continue	, -.00f, -.56f,mGR.mSel == 2?1.1f:1,mGR.mSel == 2?0.5f:1);
		
		DrawTransScal(gl, mGR.mTex_button[4], 0.87f, -.85f,mGR.mSel == 3?1.1f:1,mGR.mSel == 3?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Achiev 	, 0.87f, -.85f,mGR.mSel == 3?1.1f:1,mGR.mSel == 3?0.5f:1);
		
		DrawTransScal(gl, mGR.mTex_button[4], 0.87f, -.50f,mGR.mSel == 4?1.1f:1,mGR.mSel == 4?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Leader 	, 0.87f, -.50f,mGR.mSel == 4?1.1f:1,mGR.mSel == 4?0.5f:1);
		
		DrawTransScal(gl, mGR.mTex_button[4], -.87f, -.85f,mGR.mSel == 5?1.1f:1,mGR.mSel == 5?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Menu 	, -.87f, -.85f,mGR.mSel == 5?1.1f:1,mGR.mSel == 5?0.5f:1);
		
		if(GameRenderer.mStart.isSignedIn())
			mGR.mFont.Draw(gl, mGR.mPName, 0,-.4f, 0, 1);
	} 
	boolean Handle_Over(MotionEvent event){
		mGR.mSel = 0;
		if(CircRectsOverlap(-.71f, .85f, mGR.mTex_button[0].width()*.4f, mGR.mTex_button[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
//			mGR.mSel = 1;
		}
		if(CircRectsOverlap(-.00f, -.56f, mGR.mTex_button[0].width()*.4f, mGR.mTex_button[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 2;
		}
		if(CircRectsOverlap(0.87f,-.85f, mGR.mTex_button[4].width()*.4f, mGR.mTex_button[4].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 3;
		}
		if(CircRectsOverlap(0.87f,-.50f, mGR.mTex_button[4].width()*.4f, mGR.mTex_button[4].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 4;
		}
		if(CircRectsOverlap(-.87f,-.85f, mGR.mTex_button[4].width()*.4f, mGR.mTex_button[4].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .02f)){
			mGR.mSel = 5;
		}
		if (MotionEvent.ACTION_UP == event.getAction()) {
			switch (mGR.mSel) {
			case 1:
				M.GameScreen = M.GAMEUPGR;//Upgrade
				break;
			case 2:
				M.GameScreen = M.GAMEUPGR;//Upgrade
//				if(M.GameScreen == M.GAMEOVER)
//					M.GameScreen = M.GAMEPLAY;
//				else
//					M.GameScreen = M.GAMEPLAY;
//				mGR.gameReset();
				break;
			case 3:
				GameRenderer.mStart.onShowAchievementsRequested();//Achievement
				break;
			case 4:
				GameRenderer.mStart.onShowLeaderboardsRequested();//Leader-Board
				break;
			case 5:
				M.GameScreen = M.GAMEMENU;//Menu
				break;
			}
			if(mGR.mSel != 0)
				M.sound6(GameRenderer.mContext, R.drawable.button_click);
			mGR.mSel = 0;
		}
		return true;
	}
	/************************Over End*************************/
	
	/************************GamePlay Start*************************/
	void gamewinOver(){
		M.stop(2);
		if(M.GameScreen == M.GAMEOVER){
			mGR.mPlayer.Coints = 50 + M.mRand.nextInt(5*mGR.mStage);
			mGR.mPlayer.Crystal = 0;
			mGR.mPlayer.Score = mGR.mPlayer.kill*7;
		}else{
			mGR.mPlayer.Coints = 100 + M.mRand.nextInt(50*mGR.mStage);
			mGR.mPlayer.Crystal = 2+(int)(mGR.mStage*.1f);
			mGR.mPlayer.Score = mGR.mPlayer.kill*11;
			mGR.mStage ++;
		}
		mGR.Tdamy = mGR.mPlayer.TKill;
		mGR.damy=1+mGR.mPlayer.TKill/30;
		
		mGR.mPlayer.TKill+=mGR.mPlayer.kill;
		mGR.mPlayer.TCrystal+=mGR.mPlayer.Crystal;
		mGR.mPlayer.T$+=mGR.mPlayer.Coints;
		mGR.mPlayer.HScore+=mGR.mPlayer.Score;
		mGR.mPlayer.TCo+=mGR.mPlayer.Coints;
		mGR.mPlayer.TCy+=mGR.mPlayer.Crystal;
		Achivment();
		GameRenderer.mStart.Submitscore(R.string.leaderboard_score, mGR.mPlayer.HScore);
		GameRenderer.mStart.ShowInterstitial();
		Counter = 0;
//		M.play(GameRenderer.mContext, R.drawable.splash_other);
	}
	void setFirePower(float x, float y) {
		for (int i = 0; i < mGR.mPowerFire.length; i++) {
			if (!mGR.mPowerFire[i].isScrean()) {
				mGR.mPowerFire[i].set(x, y, 0);
				break;
			}
		}
	}

	void DrawFirePower(GL10 gl) {
		for (int i = 0; i < mGR.mPowerFire.length; i++) {
			if (mGR.mPowerFire[i].update()){
				if(mGR.mPowerFire[i].count == 20){
					M.sound9(GameRenderer.mContext, R.drawable.fireball_blast);
				}
				if(mGR.mPowerFire[i].count < 20)
					DrawTextureS(gl, mGR.mTex_FireBall[Counter%mGR.mTex_FireBall.length], mGR.mPowerFire[i].x, mGR.mPowerFire[i].y,2,2);
				else
					DrawTexture(gl, mGR.mTex_FirePower[(mGR.mPowerFire[i].count-20)%mGR.mTex_FirePower.length], mGR.mPowerFire[i].x, mGR.mPowerFire[i].y);
			}
		}
	}
	
	
	
	void setFireAnim(float x, float y,int img) {
		M.sound15(GameRenderer.mContext, R.drawable.kart_shoot);
		for (int i = 0; i < mGR.mFire.length; i++) {
			if (!mGR.mFire[i].isScrean()) {
				mGR.mFire[i].set(x, y, -.06f, .04f,img);
				break;
			}
		}
	}

	void DrawFireAnim(GL10 gl) {
		for (int i = 0; i < mGR.mFire.length; i++) {
			if (mGR.mFire[i].update()){
				if(mGR.mFire[i].blast == -1){
					if(mGR.mFire[i].img == 0)
						DrawTexture(gl, mGR.mTex_Gola[mGR.mFire[i].img][1], mGR.mFire[i].x, mGR.mFire[i].y);
					else
						DrawTexture(gl, mGR.mTex_Gola[mGR.mFire[i].img][Counter%mGR.mTex_Gola[mGR.mFire[i].img].length], mGR.mFire[i].x, mGR.mFire[i].y);
				}
				else
					DrawTexture(gl, mGR.mTex_Fire[mGR.mFire[i].blast], mGR.mFire[i].x, mGR.mFire[i].y);
			}
		}
	}
	void setOpponent(int no, int img) {
		float dis = .2f;
		switch (no) {
		case 1:
			for (int i = 0, k = 0; i < mGR.mOpp.length && k < no; i++) {
				if (mGR.mOpp[i].mode < 0) {
					int pos = M.mRand.nextInt(7);
					mGR.mOpp[i].set(1.5f, .80f - .26f * pos, img, 1,pos);
					k++;
				}
			}
			break;
		case 2:
			if (M.mRand.nextBoolean()) {
				for (int i = 0, k = 0; i < mGR.mOpp.length && k < no; i++) {
					if (mGR.mOpp[i].mode < 0) {
						mGR.mOpp[i].set(1.5f, .80f - .26f * (3 + k), img, 1,(3 + k));
						k++;
					}
				}
			} else {
				int l =M.mRand.nextInt(7);
				for (int i = 0, k = 0; i < mGR.mOpp.length && k < no; i++) {
					if (mGR.mOpp[i].mode < 0) {
						mGR.mOpp[i].set(1.5f+k*dis, .80f - .26f * (l), img, 1,l);
						k++;
					}
				}
			}
			break;
		case 3:
			if (M.mRand.nextBoolean()){
			for (int i = 0, k = 0; i < mGR.mOpp.length && k < no; i++) {
				if (mGR.mOpp[i].mode < 0) {
					mGR.mOpp[i].set(1.5f, .80f - .26f * (3 + k), img, 1,(3 + k));
					k++;
				}
			}}else{
				int l =M.mRand.nextInt(7);
				for (int i = 0, k = 0; i < mGR.mOpp.length && k < no; i++) {
					if (mGR.mOpp[i].mode < 0) {
						mGR.mOpp[i].set(1.5f+k*dis, .80f - .26f * (l), img, 1,l);
						k++;
					}
				}
			}
			break;
		case 4:
			switch (M.mRand.nextInt(2)) {
			case 0:
				for (int i = 0, k = 0; i < mGR.mOpp.length && k < no; i++) {
					if (mGR.mOpp[i].mode < 0) {
						mGR.mOpp[i].set(1.5f, .80f - .26f * (1 + k), img, 1,(1 + k));
						k++;
					}
				}
				break;
			default:
				int l =M.mRand.nextInt(7);
				for (int i = 0, k = 0; i < mGR.mOpp.length && k < no; i++) {
					if (mGR.mOpp[i].mode < 0) {
						mGR.mOpp[i].set(1.5f+k*dis, .80f - .26f * l, img, 1,l);
						k++;
					}
				}
				break;
			}
			
			break;
		case 5:
			switch (M.mRand.nextInt(2)) {
			case 0:
			for (int i = 0, k = 0; i < mGR.mOpp.length && k < no; i++) {
				if (mGR.mOpp[i].mode < 0) {
					mGR.mOpp[i].set(1.5f, .82f - .27f * (1 + k), img, 1,(1 + k));
					k++;
				}
			}
			break;
			default:
				int l =M.mRand.nextInt(7);
				for (int i = 0, k = 0; i < mGR.mOpp.length && k < no; i++) {
					if (mGR.mOpp[i].mode < 0) {
						mGR.mOpp[i].set(1.5f+k*dis, .82f - .27f * l, img, 1,l);
						k++;
					}
				}
				break;
			}
			break;
		case 6:
			switch (M.mRand.nextInt(2)) {
			case 0:
			for (int i = 0, k = 0; i < mGR.mOpp.length && k < no; i++) {
				if (mGR.mOpp[i].mode < 0) {
					mGR.mOpp[i].set(1.5f, .82f - .27f * (1 + k), img, 1,(1 + k));
					k++;
				}
			}
			break;
			default:
				int l =M.mRand.nextInt(7);
				for (int i = 0, k = 0; i < mGR.mOpp.length && k < no; i++) {
					if (mGR.mOpp[i].mode < 0) {
						mGR.mOpp[i].set(1.5f+k*dis, .82f - .27f * l, img, 1,l);
						k++;
					}
				}
				break;
			}
			break;
		case 7:
			switch (M.mRand.nextInt(2)) {
			case 0:
			for (int i = 0, k = 0; i < mGR.mOpp.length && k < no; i++) {
				if (mGR.mOpp[i].mode < 0) {
					mGR.mOpp[i].set(1.5f, .82f - .27f * (1 + k), img, 1,(1 + k));
					k++;
				}
			}
			break;
			default:
				int l =M.mRand.nextInt(7);
				for (int i = 0, k = 0; i < mGR.mOpp.length && k < no; i++) {
					if (mGR.mOpp[i].mode < 0) {
						mGR.mOpp[i].set(1.5f+k*dis, .82f - .27f * l, img, 1,l);
						k++;
					}
				}
				break;
			}
			break;
		default:
			for (int i = 0, k = 0; i < mGR.mOpp.length && k < no; i++) {
				if (mGR.mOpp[i].mode < 0) {
					mGR.mOpp[i].set(1.5f+(k/7)*dis, .82f - .27f * (k%7), img, 1,(k%7));
					k++;
				}
			}
			break;
		}
		
	}
	float getRedian(){
		float k = 1;
		int m = -1;
		for (int i = 0; i < mGR.mOpp.length; i++){
			if(mGR.mOpp[i].x>-.8f && mGR.mOpp[i].x < k && mGR.mOpp[i].isScreen()){
				k = mGR.mOpp[i].x;
				m = i;
			}
		}
		if(m == -1){
			return 0;
		}
		else{
			float x = mGR.mOpp[m].x - (-.8f);
			float y = mGR.mOpp[m].y - (-.4f);
			double radian = M.GetAngle(x, y);
			radian*=.78f;
			return (float)radian;
		}
	}
	float dx, dy;
	void gameLoagic() {
		dx = (float) Math.cos(mGR.mBow.radian) * .17f;
		dy = (float) Math.sin(mGR.mBow.radian) * .28f;
		if(mGR.mPlayer.fireArrowRateTower<31){
			for (int i = 0; i < mGR.mArrow.length && Counter % mGR.mPlayer.fireArrowRateTower == 0 ; i++) {
				if (mGR.mArrow[i].x < -1.0f || mGR.mArrow[i].x > 1.3f || mGR.mArrow[i].y < -1.13f || mGR.mArrow[i].y > 1.3f) {
					mGR.mArrow[i].set(-.8f, -.4f, getRedian());
					break;
				}
			}
		}
		for (int i = 0, j = 0; i < mGR.mArrow.length
				&& Counter % mGR.mPlayer.fireArrowRate == 0
				&& j < mGR.mPlayer.mulArrow && (istap || isFirtap); i++) 
		{
			isFirtap = false;
			if (mGR.mArrow[i].x < -1.0f || mGR.mArrow[i].x > 1.3f
					|| mGR.mArrow[i].y < -1.13f || mGR.mArrow[i].y > 1.3f) {
				float dx1 = (float) Math.cos(mGR.mBow.radian-(mGR.mPlayer.mulArrow/2*.2f)+j*.2f) * .17f;
				float dy1 = (float) Math.sin(mGR.mBow.radian-(mGR.mPlayer.mulArrow/2*.2f)+j*.2f) * .28f;
				mGR.mArrow[i].set(mGR.mBow.x + dx1, mGR.mBow.y + dy1, mGR.mBow.radian);
				if(j==0)
					M.sound3(GameRenderer.mContext, R.drawable.arrow_shoot);
				j++;
			}
		}
		for (int i = 0; i < mGR.mOpp.length; i++) {
			if(mGR.mOpp[i].mode > -1)
				mGR.mOpp[i].update(Counter);
		}
		for (int i = 0; i < mGR.mOpp.length; i++) {
			if (mGR.mOpp[i].isScreen()){
			for (int j = 0; j < mGR.mArrow.length; j++) {
				if (mGR.mArrow[j].isScreen() && mGR.mOpp[i].Life >10) {
					if(CircRectsOverlap(mGR.mOpp[i].x, mGR.mOpp[i].y, .11f, .10f, mGR.mArrow[j].cx(), mGR.mArrow[j].cy(), .05f)){
						mGR.mOpp[i].arrowShot(mGR.mPlayer);
						mGR.mArrow[j].reset();
						if(mGR.mOpp[i].Freez >0)
							M.sound14(GameRenderer.mContext, R.drawable.ice_shoot);
						else
							M.sound2(GameRenderer.mContext, R.drawable.anime_hit);
					}
				}
			}
			}
		}
		for (int j = 0; j < mGR.mPowerFire.length; j++) {
			if (mGR.mPowerFire[j].isScrean() && mGR.mPowerFire[j].count == 36) {
				for (int i = 0; i < mGR.mOpp.length; i++) {
					if (mGR.mOpp[i].isScreen()) {
						if (CirCir(mGR.mOpp[i].x, mGR.mOpp[i].y, .1f,
								mGR.mPowerFire[j].x, mGR.mPowerFire[j].y, mGR.mPlayer.fireArea)) {
							mGR.mOpp[i].bourn = mGR.mPlayer.firepower;
							mGR.mOpp[i].reduceSpeed = mGR.mPlayer.fireSpeedRate;
							// mGR.mPowerFire[j].reset();
						}
					}
				}
			}
		}
		if(mGR.mFreez.update()){
			if (mGR.mFreez.count == 7) {
				for (int i = 0; i < mGR.mOpp.length; i++) {
					if (mGR.mOpp[i].isScreen()) {
						if (CirCir(mGR.mOpp[i].x, mGR.mOpp[i].y, .1f,
								mGR.mFreez.x, mGR.mFreez.y, mGR.mPlayer.FreezArea)) {
							mGR.mOpp[i].Freez = mGR.mPlayer.Freezpower;
							mGR.mOpp[i].reduceSpeed = mGR.mPlayer.FreezSpeedRate;
						}
					}
				}
			}
		}
		if(mGR.mSparkal.update()){
			if (mGR.mSparkal.count == 24) {
				for (int i = 0; i < mGR.mOpp.length; i++) {
					if (mGR.mOpp[i].isScreen()) {
						if (CirCir(mGR.mOpp[i].x, mGR.mOpp[i].y, .1f,
								mGR.mSparkal.x, mGR.mSparkal.y, mGR.mPlayer.SparkalArea)) {
							mGR.mOpp[i].Sparkal = mGR.mPlayer.Sparkalpower;
							mGR.mOpp[i].reduceSpeed = mGR.mPlayer.SparkalSpeedRate;
						}
					}
				}
			}
		}

		if (Counter % 100 == 0) {
			int no = 0;
			for (int i = 0; i < mGR.OppCount.length; i++) {
				if (mGR.OppCount[i] > 0) {
					no++;
				}
			}
			if (no > 0) {
				int NewOppcont[] = new int[no];
				no = 0;
				for (int i = 0; i < mGR.OppCount.length && no < NewOppcont.length; i++) {
					if (mGR.OppCount[i] > 0) {
						NewOppcont[no] = i;
						no++;
					}
				}
				
				no = M.mRand.nextInt(NewOppcont.length);
				int newopp = M.mRand.nextInt(mGR.OppCount[NewOppcont[no]]) + 1;
				if (mGR.Wave < 5 && newopp > 5) {
					newopp = M.mRand.nextInt(5) + 1;
				}
				if (mGR.Wave >= 5 && mGR.Wave < 15 && newopp > 8) {
					newopp = M.mRand.nextInt(8) + 1;
				}
				if (mGR.Wave >= 15 && mGR.Wave < 30 && newopp > 12) {
					newopp = M.mRand.nextInt(12) + 1;
				}
				
				setOpponent(newopp, NewOppcont[no]);
//				setOpponent(newopp, 14);
				mGR.OppCount[NewOppcont[no]] -= newopp;
				
				mGR.RemainOpp =0;
				for (int i = 0; i < mGR.OppCount.length; i++) {
					mGR.RemainOpp+= mGR.OppCount[i];
				}
				mGR.Wave++;
				
			}else{
				boolean isWin = true;
				for (int i = 0; i < mGR.mOpp.length && isWin; i++)
				{
					if(mGR.mOpp[i].mode >-1){
						isWin = false;
					}
				}
				if(isWin){
					M.stop(3);
					mGR.mPlayer.key = 300;
					M.sound17(GameRenderer.mContext, R.drawable.levelvomplete);
//					M.GameScreen = M.GAMEWIN;
//					gamewinOver();
				}
			}
		}
		if(mGR.mPlayer.Life <=0){
			M.stop(4);
			mGR.mPlayer.key++;
		}
	}

	void Draw_GamePlay(GL10 gl) {
		DrawTexture(gl, mGR.mTex_BG[0], 0, 0);
		if(mGR.mPlayer.blink){
			if(Counter%2==0)
				DrawTexture(gl, mGR.mTex_Fort[0], -.83f, 0);
			else
				mGR.mTex_Fort[0].drawRGB(gl, -.83f, 0,1,0,0);
			if(Counter%10==0)
				mGR.mPlayer.blink = false;
		}else{
			DrawTexture(gl, mGR.mTex_Fort[0], -.83f, 0);
		}
		Draw360(gl, mGR.mTex_Bow[0], mGR.mBow.x, mGR.mBow.y, (float) Math.toDegrees(mGR.mBow.radian), .25f, 0);
//		Draw360(gl, mGR.mTex_Arrow[0], mGR.mBow.x + dx, mGR.mBow.y + dy, (float) Math.toDegrees(mGR.mBow.radian), 0, 0);
		for (int i = 0; i < mGR.mArrow.length; i++) {
			if (mGR.mArrow[i].update()) {
				Draw360(gl, mGR.mTex_Arrow[0], mGR.mArrow[i].x, mGR.mArrow[i].y,
						(float) Math.toDegrees(mGR.mArrow[i].radian), 0, 0);
				
//				DrawTexture(gl, mGR.mTex_Play, mGR.mArrow[i].cx() , mGR.mArrow[i].cy());
			}
		}
		boolean spark =  false;
		
		if(mGR.mTex_Opp == null){
			mGR.opppLoad();
		}
		for (int i = 0; i < mGR.mOpp.length; i++) {
			for (int j = 0; j < mGR.mOpp.length; j++) {
				if (mGR.mOpp[i].isScreen() && mGR.mOpp[i].pos == j) {
					if(mGR.mOpp[i].Life > 10 && mGR.mOpp[i].Freez == 0){
					if(mGR.mOpp[i].mode == 0 && mGR.mOpp[i].img == 2){
						DrawTexture(gl, mGR.mTex_Opp[mGR.mOpp[i].img][1][0], mGR.mOpp[i].x, mGR.mOpp[i].y);
						DrawTexture(gl, mGR.mTex_Opp[2][0][Counter%4], -.17f+mGR.mOpp[i].x, mGR.mOpp[i].y+.12f);
						DrawTexture(gl, mGR.mTex_Opp[2][0][Counter%4], -.27f+mGR.mOpp[i].x, mGR.mOpp[i].y+.13f);
						spark = true;
					}else if(mGR.mOpp[i].mode == 0 && mGR.mOpp[i].img == 7){
						DrawTexture(gl, mGR.mTex_Opp[mGR.mOpp[i].img][0][0], mGR.mOpp[i].x, mGR.mOpp[i].y);
						Draw360(gl, mGR.mTex_Opp[7][0][1], .04f+mGR.mOpp[i].x, mGR.mOpp[i].y+.06f, Counter%20 < 10?( -(10-(Counter%10))*10):0, -.16f, -.06f);
					}else if((mGR.mOpp[i].mode == 0||mGR.mOpp[i].mode == 1) && mGR.mOpp[i].img == 10){
						DrawTexture(gl, mGR.mTex_Opp[10][1][0], mGR.mOpp[i].x, mGR.mOpp[i].y);
						DrawTexture(gl, mGR.mTex_Opp[10][0][Counter % 2], -.14f + mGR.mOpp[i].x, mGR.mOpp[i].y - .04f);
					}else if(mGR.mOpp[i].mode == 0 && mGR.mOpp[i].img == 13){
						DrawTexture(gl, mGR.mTex_Opp[13][0][0], mGR.mOpp[i].x, mGR.mOpp[i].y);
						Draw360(gl, mGR.mTex_Opp[13][0][1], -.01f+ mGR.mOpp[i].x, mGR.mOpp[i].y+.04f, Counter%20 < 10?( -(10-(Counter%10))*10):0, -.14f,-.21f);
					}else if((mGR.mOpp[i].mode == 0||mGR.mOpp[i].mode == 1) && mGR.mOpp[i].img == 11){
						DrawTextureR(gl, mGR.mTex_Opp[11][0][0], -.09f+mGR.mOpp[i].x, mGR.mOpp[i].y-.03f, mGR.mOpp[i].mode == 0?0:mGR.mOpp[i].Cter*5, .6f,.6f);//small front
						DrawTextureR(gl, mGR.mTex_Opp[11][0][0], .12f+mGR.mOpp[i].x, mGR.mOpp[i].y-.03f,mGR.mOpp[i].mode == 0?0: mGR.mOpp[i].Cter*5, .8f,.8f);//big back
						DrawTexture(gl, mGR.mTex_Opp[11][2][0], mGR.mOpp[i].x, mGR.mOpp[i].y);
						DrawTextureR(gl, mGR.mTex_Opp[11][0][0], .13f+mGR.mOpp[i].x, mGR.mOpp[i].y-.15f, mGR.mOpp[i].mode == 0?0:mGR.mOpp[i].Cter*5, 1,1);//big back
						DrawTextureR(gl, mGR.mTex_Opp[11][0][0], -.12f+mGR.mOpp[i].x, mGR.mOpp[i].y-.17f,mGR.mOpp[i].mode == 0?0: mGR.mOpp[i].Cter*5, .8f,.8f);//small frant
						if (mGR.mOpp[i].mode == 0) {
							Draw360(gl,mGR.mTex_Opp[11][1][0],0.08f + mGR.mOpp[i].x,mGR.mOpp[i].y + 0.09f,
								mGR.mOpp[i].Cter % 30 < 15 ? ((-40 + (mGR.mOpp[i].Cter % 15)) * 10):110, .07f, -.13f);
								if (mGR.mOpp[i].Cter % 30 == 15&& mGR.mOpp[i].mode == 0) {
									setFireAnim(mGR.mOpp[i].x,mGR.mOpp[i].y + .39f, 0);
								}
						}else{
							Draw360(gl,mGR.mTex_Opp[11][1][0],0.08f + mGR.mOpp[i].x,mGR.mOpp[i].y + 0.09f,0, .07f, -.13f);
						}
					}else if(mGR.mOpp[i].mode == 0 && mGR.mOpp[i].img == 9){
						DrawTexture(gl, mGR.mTex_Opp[9][0][0], mGR.mOpp[i].x, mGR.mOpp[i].y);
						if(mGR.mOpp[i].Cter %30==0)
							setFireAnim(-.06f+mGR.mOpp[i].x, mGR.mOpp[i].y+ .17f,1);
					}else{
						DrawTexture(gl,
								mGR.mTex_Opp[mGR.mOpp[i].img][mGR.mOpp[i].mode][mGR.mOpp[i].Cter
										% mGR.mTex_Opp[mGR.mOpp[i].img][mGR.mOpp[i].mode].length],
								mGR.mOpp[i].x, mGR.mOpp[i].y);
						
						 if(mGR.mOpp[i].img == 14){
								DrawTexture(gl,mGR.mTex_Opp14[mGR.mOpp[i].mode][mGR.mOpp[i].Cter% mGR.mTex_Opp14[mGR.mOpp[i].mode].length],mGR.mOpp[i].x, mGR.mOpp[i].y-.2f);
							}
					}
					if(mGR.mOpp[i].bourn >0){
						DrawTexture(gl, mGR.mTex_Born[C2%mGR.mTex_Born.length],mGR.mOpp[i].x, mGR.mOpp[i].y);
						if(Counter%mGR.mPlayer.bornby == 0){
							mGR.mOpp[i].Life-=1;
							mGR.mOpp[i].lBar =M.time;
						}
					}
					if(mGR.mOpp[i].Sparkal >0){
						DrawTexture(gl, mGR.mTex_SparBreak[C2%mGR.mTex_SparBreak.length],mGR.mOpp[i].x, mGR.mOpp[i].y);
						if(Counter%mGR.mPlayer.Sparkalby == 0){
							mGR.mOpp[i].Life-=1;
							mGR.mOpp[i].lBar =M.time;
						}
					}
				}else if(mGR.mOpp[i].Life <= 10) {
					DrawTransScal(gl,mGR.mTex_Opp[mGR.mOpp[i].img][1][0],mGR.mOpp[i].x, mGR.mOpp[i].y,1,mGR.mOpp[i].Life*.1f);
				}else if(mGR.mOpp[i].Freez >0){
					
					if(mGR.mOpp[i].img == 11){
						DrawTextureR(gl, mGR.mTex_Opp[11][0][1], -.09f+mGR.mOpp[i].x, mGR.mOpp[i].y-.03f, mGR.mOpp[i].Cter*5, .6f,.6f);//small front
						DrawTextureR(gl, mGR.mTex_Opp[11][0][1], .12f+mGR.mOpp[i].x, mGR.mOpp[i].y-.03f, mGR.mOpp[i].Cter*5, .8f,.8f);//big back
						DrawTexture(gl, mGR.mTex_Opp[11][2][1], mGR.mOpp[i].x, mGR.mOpp[i].y);
						DrawTextureR(gl, mGR.mTex_Opp[11][0][1], .13f+mGR.mOpp[i].x, mGR.mOpp[i].y-.15f, mGR.mOpp[i].Cter*5, 1,1);//big back
						DrawTextureR(gl, mGR.mTex_Opp[11][0][1], -.12f+mGR.mOpp[i].x, mGR.mOpp[i].y-.17f, mGR.mOpp[i].Cter*5, .8f,.8f);//small frant
						Draw360(gl,mGR.mTex_Opp[11][1][1],0.08f + mGR.mOpp[i].x,mGR.mOpp[i].y + 0.09f,0, .07f, -.13f);
					}else {
						DrawTexture(gl,mGR.mTex_Opp[mGR.mOpp[i].img][2][0],mGR.mOpp[i].x, mGR.mOpp[i].y);
						if(mGR.mOpp[i].img == 10)
							DrawTexture(gl, mGR.mTex_Opp[10][2][1], -.14f + mGR.mOpp[i].x, mGR.mOpp[i].y - .04f);
						if(mGR.mOpp[i].img == 14){
							DrawTexture(gl,mGR.mTex_Opp14[mGR.mOpp[i].mode][mGR.mOpp[i].Cter% mGR.mTex_Opp14[mGR.mOpp[i].mode].length],mGR.mOpp[i].x, mGR.mOpp[i].y-.2f);
						}
					}
				}
					if(mGR.mOpp[i].lBar >0){
						DrawTexture(gl, mGR.mTex_OLife[0], mGR.mOpp[i].x, mGR.mOpp[i].y);
						mGR.mTex_OLife[1].drawSS(gl, -.07f+mGR.mOpp[i].x, mGR.mOpp[i].y, mGR.mOpp[i].Lbar(), 1.5f);//14
						mGR.mOpp[i].lBar --;
					}
				}
			}
		}
		for(int i=0;i<3 && spark;i++)
			DrawTexture(gl, mGR.mTex_WalSpark[Counter%mGR.mTex_WalSpark.length], -.96f+(M.mRand.nextInt(5)*.025f)*i, .2f-(M.mRand.nextInt(5)*.08f));
		
		DrawTransScal(gl, mGR.mTex_button[4], -.89f, 0.85f,mGR.mSel == 9?1.1f:1,mGR.mSel == 9?0.5f:1);
		DrawTransScal(gl, mGR.mTex_pause, -.89f, 0.85f,mGR.mSel == 9?1.1f:1,mGR.mSel == 9?0.5f:1);
		
//		DrawTexture(gl, mGR.mTex_button[4], -.89f, .85f);
//		DrawTexture(gl, mGR.mTex_pause, -.89f, .85f);
		
		
		if (mGR.mFreez.isScreen() && mGR.mFreez.count >= 0) {
			DrawTextureS(gl, mGR.mTex_Freez[mGR.mFreez.count%mGR.mTex_Freez.length],mGR.mFreez.x,
					mGR.mFreez.y+mGR.mTex_Freez[0].Height()*.34f*2.5f,1.5f,2.5f);
		}
		if (mGR.mSparkal.isScrean() && mGR.mSparkal.count >= 0) {
			DrawTextureS(gl, mGR.mTex_Spark[Counter%mGR.mTex_Spark.length],mGR.mSparkal.x-.21f,mGR.mSparkal.y+.38f,4,4);
		}
		DrawFireAnim(gl);
		DrawFirePower(gl);

		if(mGR.mCover.power>0)
			DrawTexture(gl, mGR.mTex_Cover,mGR.mCover.x,mGR.mCover.y);
		
		DrawTexture(gl, mGR.mTex_bar[0], .61f, .88f);
		
		if(mGR.lifeBar>((.85f - (((mGR.totalOpp - mGR.RemainOpp) / 50f) / (mGR.totalOpp / 50f)) * .5f)))
			mGR.lifeBar-=.002f;
		DrawTexture(gl,mGR.mTex_bar[1],mGR.lifeBar,.88f);// .85f to .35f
		
		
		for(int i=0;i<mGR.mTex_Power.length;i++){
			if(mGR.Mana[(i*3)+1]<=0)
				DrawTexture(gl, mGR.mTex_powerlock, .30f+.3f*i, -.87f);
			else{
				if(mGR.mPlayer.Mana >= 20){
					DrawTexture(gl, mGR.mTex_button[4], .30f+.3f*i, -.88f);
					DrawTexture(gl, mGR.mTex_Power[i][0], .30f+.3f*i, -.87f);
				}
				if(mGR.mPlayer.PowerResume[i]<1000)
				{
					gl.glEnable(GL10.GL_SCISSOR_TEST);
					gl.glScissor(0,(int)world2screenY(.80f+mGR.mTex_powerload.Height()*.35f), (int)(M.ScreenWidth), 
							(int)(M.ScreenHieght*.00018*(mGR.mPlayer.PowerResume[i])));
					DrawTexture(gl, mGR.mTex_powerload, .30f+.3f*i, -.87f);
					gl.glDisable(GL10.GL_SCISSOR_TEST);
					mGR.mPlayer.update(i);
				}else{
					DrawTexture(gl, mGR.mTex_powerload, .30f+.3f*i, -.87f);
				}
				if(mGR.mPlayer.Mana < 20)
				{
					DrawTransScal(gl, mGR.mTex_button[4], .30f+.3f*i, -.88f,1,.5f);
					DrawTransScal(gl, mGR.mTex_Power[i][0], .30f+.3f*i, -.87f,1,.5f);
					mGR.mFont.Draw(gl, "Low", .33f+.3f*i, -.81f, 0, 1);
					mGR.mFont.Draw(gl, "Mana", .33f+.3f*i, -.89f, 0, 1);
				}
			}	
		}
		DrawTexture(gl, mGR.mTex_lifebar[0], -.76f, -.88f);
		mGR.mTex_lifebar[1].drawSS(gl, -.87f, -.844f, (16f/mGR.mPlayer.tLife)*(mGR.mPlayer.Life), 1);//16
		
		
		mGR.mTex_lifebar[2].drawSS(gl, -.87f, -.917f, (14.2f/mGR.mPlayer.tMana)*(mGR.mPlayer.Mana), 1);//16
		if(mGR.mPlayer.Mana<mGR.mPlayer.tMana && Counter%10==0){
			mGR.mPlayer.Mana+=1;
			if(mGR.mPlayer.Mana==mGR.mPlayer.tMana)
				M.sound18(GameRenderer.mContext, R.drawable.mana_full);
		}
		
//		mGR.mTex_lifebar[2].drawSS(gl, -.87f, -.917f, 14.2f, 1);//14
		mGR.mFont.Draw(gl, "Stage: "+mGR.mStage, -.20f, .92f, 0, 1);
		
		mGR.mFont.Draw(gl,(int)(mGR.mPlayer.Life)+"/ "+mGR.mPlayer.tLife, -.70f, -.83f, 0, 1);
		mGR.mFont.Draw(gl,mGR.mPlayer.Mana+"/ "+mGR.mPlayer.tMana, -.70f, -.92f, 0, 1);
		
		if(mGR.mPlayer.key==0)
			gameLoagic();
		else{
			if (mGR.mPlayer.key < 300) {
				DrawTexture(gl, mGR.mTex_button[4], 0, 0.00f);
				gl.glEnable(GL10.GL_SCISSOR_TEST);
				gl.glScissor(0,(int) world2screenY(mGR.mTex_powerload.Height() * .35f),(int) (M.ScreenWidth),
						(int) (M.ScreenHieght * .0018 * (mGR.mPlayer.key * .5f)));
				mGR.mTex_powerload.drawBlack(gl, 0, 0, 0, 0, 0, 1, 1);
				gl.glDisable(GL10.GL_SCISSOR_TEST);
				mGR.mFont.Draw(gl, "Key", .035f, .02f, 0, 1);
				DrawTexture(gl, mGR.mTex_button[0], 0.02f, -.22f);
				mGR.mFont.Draw(gl, "Use " + mGR.mPlayer.TKey, 0.035f, -.2f, 0,
						1);
				if (mGR.mPlayer.key < 200)
					mGR.mPlayer.key++;
				else {
					if(mGR.mPlayer.key == 201){
						M.sound11(GameRenderer.mContext, R.drawable.game_over);
					}
					mGR.mPlayer.key++;
					if (mGR.mPlayer.key < 250) {
						mGR.mTex_BG[0].drawBlack(gl, 0, 0, 0, 0, 0,(mGR.mPlayer.key-200) * .02f, 1);
					} else {
						M.GameScreen = M.GAMEOVER;
//						M.sound8(GameRenderer.mContext, R.drawable.counting);
						gamewinOver();
					}
				}
			}else{
				mGR.mPlayer.key++;
				if (mGR.mPlayer.key < 350) {
					mGR.mTex_BG[0].drawBlack(gl, 0, 0, 0, 0, 0,(mGR.mPlayer.key-300) * .02f, 1);
				} else {
					M.GameScreen = M.GAMEWIN;
//					M.sound8(GameRenderer.mContext, R.drawable.counting);
					gamewinOver();
				}
			
			}
		}
		if(Counter%3 == 0)
			C2++;
	}
	boolean istap = false;
	boolean isFirtap = false;
	boolean Handle_GamePlay(MotionEvent event) {
		mGR.mSel = 0; 
		if(mGR.mPlayer.key > 0 && mGR.mPlayer.key < 200){
			if (CircRectsOverlap(0.02f, -.22f, mGR.mTex_button[0].width() * .5f,mGR.mTex_button[0].Height() * .7f,
					screen2worldX(event.getX()),screen2worldY(event.getY()), .01f)) 
			{
				if(mGR.mPlayer.TKey > 0){
					mGR.mPlayer.TKey-=1;
					mGR.mPlayer.Life= mGR.mPlayer.tLife;
					mGR.mPlayer.Mana= mGR.mPlayer.tMana;
					mGR.mPlayer.key = 0;
				}else{
					M.GameScreen =M.GAMEOVER;
					gamewinOver();
//					M.GameScreen =M.GAMEBUY;
				}
			}
			return true;
		}else{
			if (CircRectsOverlap(-.89f, .85f, mGR.mTex_button[4].width() * .5f,mGR.mTex_button[4].Height() * .4f,
					screen2worldX(event.getX()),screen2worldY(event.getY()), .01f)){
				mGR.mSel = 9;
			}
		}
		
		float x = screen2worldX(event.getX()) - mGR.mBow.x;
		float y = screen2worldY(event.getY()) - mGR.mBow.y;
		mGR.mBow.radian = M.GetAngle(x, y);
		mGR.mBow.radian *= .78f;
		istap = true;
		isFirtap = true;
		if (MotionEvent.ACTION_DOWN == event.getAction()) {
			for (int i = 0; i < mGR.mTex_Power.length; i++) {
				if (CirCir(.30f + .3f * i, -.88f,mGR.mTex_button[4].Height() * .3f,
						screen2worldX(event.getX()),screen2worldY(event.getY()), .01f)) 
				{
					if(mGR.mPlayer.Mana>20 && (mGR.mPlayer.PowerResume[i]>=999) && mGR.Mana[i*3+1]>0)
						mGR.mCover.set(screen2worldX(event.getX()),screen2worldY(event.getY()), i+1);
				}
			}
		}
		if(mGR.mCover.power>0)
			mGR.mCover.set(screen2worldX(event.getX()),screen2worldY(event.getY()), mGR.mCover.power);
		
		if (MotionEvent.ACTION_UP == event.getAction()) {
			istap = false;
			if (mGR.mCover.power > 0) {
				switch (mGR.mCover.power) {
				case 1:
					setFirePower(screen2worldX(event.getX()) - 2,2 + screen2worldY(event.getY()));
					mGR.mPlayer.PowerResume[mGR.mCover.power-1]=0;
					mGR.mPlayer.Mana-=20;
					M.sound10(GameRenderer.mContext, R.drawable.fireball_shoot);
					break;
				case 2:
					mGR.mFreez.set(screen2worldX(event.getX()),screen2worldY(event.getY()), -1);
					mGR.mPlayer.PowerResume[mGR.mCover.power-1]=0;
					mGR.mPlayer.Mana-=20;
					M.sound12(GameRenderer.mContext, R.drawable.ice_shoot);
					break;
				case 3:
					mGR.mSparkal.set(screen2worldX(event.getX()),screen2worldY(event.getY()), -1);
					mGR.mPlayer.PowerResume[mGR.mCover.power-1]=0;
					mGR.mPlayer.Mana-=20;
					M.sound13(GameRenderer.mContext, R.drawable.thunderstrom);
					break;
				}
				mGR.mCover.set(0,0, 0);
			}
			if(mGR.mSel == 9){
				M.stop(5);
				M.GameScreen = M.GAMEPAUSE;
				M.play(GameRenderer.mContext, R.drawable.splash_other);
				mGR.mSel = 0;
			}
		}
		return true;
	}
	/************************GamePlay End***************************/
	
	void Draw360(GL10 gl,SimplePlane Tex,float x,float y,float angle,float dwr,float dhr)
	{
		Tex.draw360(gl, x, y,angle,dwr,dhr);
	}
	void DrawTexture(GL10 gl,SimplePlane Tex,float x,float y)
	{
		Tex.drawPos(gl, x, y);
	}
	void DrawTextureR(GL10 gl,SimplePlane Tex,float x,float y,float angle,float sx,float sy)
	{
		Tex.drawRotet(gl, x, y,angle,sx,sy);
	}
	void DrawTextureS(GL10 gl,SimplePlane Tex,float x,float y,float sx,float sy)
	{
		Tex.drawScal(gl, x, y,sx,sy);
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
	
	float world2screenx(float a)
	{
		float c = (a/2f+.5f)*M.ScreenWidth;
		return c;
	}
	float world2screenY(float a)
	{
		float c = (.5f-(a / 2f))*(M.ScreenHieght);
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
	
	void drawNumber(GL10 gl,int no,float x, float y)
	{
//		float dx = mGR.mTex_Font[0].width();
//		 String strs = ""+no;
//		for(int i =0;i<strs.length();i++)
//		{
//			int k = ((int)strs.charAt(i))-48;
//			if(k>=0 && k<mGR.mTex_Font.length)
//				mGR.mTex_Font[k].drawPos(gl,x+i*dx,y);
//		}
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

	void Achivment() {
		if (!mGR.Achi[0]
				&& (mGR.Mana[1] > 0 || mGR.Mana[4] > 0 || mGR.Mana[7] > 0)) {
			mGR.Achi[0] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[0]);
		}
		if (!mGR.Achi[1] && mGR.mLevel > 1) {
			mGR.Achi[1] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[1]);
		}
		if (!mGR.Achi[2] && mGR.mLevel > 5) {
			mGR.Achi[2] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[2]);
		}
		if (!mGR.Achi[3]
				&& (mGR.BowPower[2 * 3 + 0] || mGR.BowPower[2 * 3 + 1] || mGR.BowPower[2 * 3 + 2])) {
			mGR.Achi[3] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[3]);
		}
		if (!mGR.Achi[4] && mGR.Mana[4] > 0) {
			mGR.Achi[4] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[4]);
		}
		if (!mGR.Achi[5] && mGR.mPlayer.TCo > 10000) {
			mGR.Achi[5] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[5]);
		}
		if (!mGR.Achi[6] && mGR.Mana[7] > 0) {
			mGR.Achi[6] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[6]);
		}
		if (!mGR.Achi[7] && mGR.mPlayer.TCo > 30000) {
			mGR.Achi[7] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[7]);
		}
		if (!mGR.Achi[8] && mGR.mPlayer.TCy > 9) {
			mGR.Achi[8] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[8]);
		}
		if (!mGR.Achi[9] && mGR.mPlayer.TCy > 29) {
			mGR.Achi[9] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[10]);
		}
		if (!mGR.Achi[10] && mGR.mPlayer.TCy > 69) {
			mGR.Achi[10] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[10]);
		}
		if (!mGR.Achi[11] && mGR.mPlayer.TCo >= 70000) {
			mGR.Achi[11] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[11]);
		}
		if (!mGR.Achi[12] && mGR.Tower[3] > 0) {
			mGR.Achi[12] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[12]);
		}
		if (!mGR.Achi[13] && mGR.mStage > 9) {
			mGR.Achi[13] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[13]);
		}
		if (!mGR.Achi[14] && mGR.Tower[4] > 0) {
			mGR.Achi[14] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[14]);
		}
		if (!mGR.Achi[15] && mGR.mStage > 29) {
			mGR.Achi[15] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[15]);
		}
		if (!mGR.Achi[16] && mGR.Tower[2] > 0) {
			mGR.Achi[16] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[16]);
		}
		if (!mGR.Achi[17]
				&& (mGR.BowPower[4 * 3 + 0] || mGR.BowPower[4 * 3 + 1] || mGR.BowPower[4 * 3 + 2])) {
			mGR.Achi[17] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[17]);
		}
		if (!mGR.Achi[18] && mGR.mStage > 69) {
			mGR.Achi[18] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[18]);
		}
		if (!mGR.Achi[19] && mGR.mPlayer.TKill > 999) {
			mGR.Achi[19] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[19]);
		}
		if (!mGR.Achi[20] && mGR.mPlayer.TKill > 4999) {
			mGR.Achi[20] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[20]);
		}
		if (!mGR.Achi[21] && mGR.mPlayer.TKill > 19999) {
			mGR.Achi[21] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[21]);
		}
		if (!mGR.Achi[22] && mGR.uPower[0] > 4) {
			mGR.Achi[22] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[22]);
		}
		if (!mGR.Achi[23] && mGR.uPower[1] > 4) {
			mGR.Achi[23] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[23]);
		}
		if (!mGR.Achi[24] && (mGR.Mana[1] > 0)) {
			mGR.Achi[24] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[24]);
		}
		if (!mGR.Achi[25]
				&& (mGR.BowPower[7 * 3 + 0] || mGR.BowPower[7 * 3 + 1] || mGR.BowPower[7 * 3 + 2])) {
			mGR.Achi[25] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[25]);
		}
	}
}
