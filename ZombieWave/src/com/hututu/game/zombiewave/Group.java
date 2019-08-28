package com.hututu.game.zombiewave;
import javax.microedition.khronos.opengles.GL10;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.MotionEvent;
import android.widget.Toast;
public class Group extends Mesh {
	GameRenderer mGR = null;
	int Counter =0;
	int Counter2 =0;
	int CountSlow =0;
	float sx,sy;
	public void setting(){float ud=.01f;switch (GameRenderer.mStart._keyCode) {case 1:sy-=ud;break;case 2:sy+=ud;break;case 3:sx-=ud;break;case 4:sx+=ud;break;}System.out.println(mGR.mPlayer.citi+"~~~~~~~~~~~~~~~     , "+sx+"f, "+sy+"f");}
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
	public void draw(GL10 gl)  {
//		M.GameScreen = M.GAMEADD;
		switch (M.GameScreen) {
		case M.GAMELOGO:
			DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if (Counter	> 60) {
				M.GameScreen = M.GAMEADD;
				Counter =0;
				if(mGR.addFree){
					M.GameScreen = M.GAMEADD;
					M.play(GameRenderer.mContext, R.drawable.splash);
				}
			}
			break;
		case M.GAMEADD:
			if (Counter	> 100) {
				DrawTexture(gl, mGR.mTex_Skip, 0.9f, 0.83f);
			}else{
				DrawTexture(gl, mGR.mTex_FBox, 0.0f,-.9f);
				mGR.mTex_FRect.drawSS(gl,-.29f, -.904f,(Counter%100)*.077f,1);
			}
			
			break;
		case M.GAMEMAP:
			Draw_Map(gl);
			break;
		case  M.GAMEMENU:
			Draw_GameMenu(gl);
			break;
		case M.GAMESHOP:
			Draw_GameShop(gl);
			break;
		case M.GAMEPLAY:
			Draw_Gameplay(gl);
			break;
		case M.GAMEWIN:
		case M.GAMEOVER:
		case M.GAMECONG:
			Draw_WOC(gl);
			break;
		case M.GAMEPRMO:
			Draw_Promotional(gl);
			break;
		case M.GAMEABOUT:
			Draw_About(gl);
			break;
		case M.GAMESTORY:
			Draw_Story(gl);
			break;
		}
//		
//		setting();
		Counter++;
	}

	public boolean TouchEvent(MotionEvent event) {
		switch (M.GameScreen) {
		case M.GAMEADD:
			if(MotionEvent.ACTION_UP == event.getAction() && Counter > 100){
				if(CircRectsOverlap(0.88f, 0.83f, mGR.mTex_Skip.width()*.5f, mGR.mTex_Skip.Height()*.4f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05)){
					M.GameScreen = M.GAMEMENU;
					M.play(GameRenderer.mContext, R.drawable.splash);
				}
			}
			break;
		case M.GAMEMAP:
			Handle_Map(event);
			break;
		case  M.GAMEMENU:
			Handle_GameMenu(event);
			break;
		case M.GAMESHOP:
			if (mGR.Popup != 0)
				Handle_PopUp(event);
			else
				Handle_GameShop(event);
			break;
		case M.GAMEPLAY:
			Handle_Gameplay(event);
			break;
		case M.GAMEWIN:
		case M.GAMEOVER:
		case M.GAMECONG:
			Handle_WOC(event);
			break;
		case M.GAMEPRMO:
			Handle_Promotional(event);
			break;
		case M.GAMEABOUT:
			Handle_About(event);
			break;
		case M.GAMESTORY:
			Handle_Story(event);
			break;
		}

		Handle(event);
		return true;
	}
	/***********************Story End**********************************/
	
	void Draw_Story(GL10 gl) {
		if (mGR.story == 0) {
			DrawTexture(gl, mGR.mTex_Story[0][0], mGR.One, 0);
			DrawTexture(gl, mGR.mTex_Story[0][1], -.01f, mGR.two);
			DrawTexture(gl, mGR.mTex_Story[0][2], mGR.three, 0);
			if(mGR.One >= -.61f)
				DrawTexture(gl, mGR.mTex_Story[0][3], -.80f, -.9f);
			if (mGR.two <= 0)
				DrawTexture(gl, mGR.mTex_Story[0][4], -.11f, -.9f);
			if (mGR.three <= 0.61f)
				DrawTexture(gl, mGR.mTex_Story[0][5], 0.68f, -.9f);
			
			
			if(mGR.One < -.61f){
				mGR.One += .1f;
				if (mGR.One > -.61f) {
					mGR.One = -.609f;
				}
			}else if (mGR.two > 0) {
				if(Counter > 50)
					mGR.two -= .2f;
				if (mGR.two < 0) {
					mGR.two = 0;
				}
			}else if(mGR.three > 0.61f){
				if(Counter == 75)
					M.sound1(GameRenderer.mContext, R.drawable.wocal_1);
				if(Counter > 100)
					mGR.three -= .1f;
				if (mGR.three < 0.61f) {
					mGR.three = 0.609f;
				}
			}else if(Counter == 130){
				M.sound2(GameRenderer.mContext, R.drawable.wocal_2);
			}else if(Counter > 200){
				Counter = 0;
				mGR.story++;
				mGR.One = -2f;
				mGR.two = 2f;
				mGR.three = 2f;
			}
		}if (mGR.story == 1){
			
			DrawTexture(gl, mGR.mTex_Story[1][1], mGR.three, 0);
			DrawTexture(gl, mGR.mTex_Story[1][0],mGR.One, 0);
			
			if(Counter > 50)
				DrawTexture(gl, mGR.mTex_Story[1][2], -.0f, -.9f);
			
			if (mGR.One < -.42f) {
				mGR.One += .1f;
				if (mGR.One > -.42f) {
					mGR.One = -.419f;
				}
			} 
			else if (mGR.three > .39f) {
				mGR.three -= .1f;
				if (mGR.three < .39f) {
					mGR.three = .389f;
				}
			}else if(Counter == 50){
				M.sound3(GameRenderer.mContext, R.drawable.wocal_3);
			}else if(Counter > 150){
				Counter = 0;
				mGR.story++;
				mGR.One = -2f;
				mGR.two = 2f;
				mGR.three = 2f;
			}
		}
		if (mGR.story == 2){
			

			DrawTexture(gl, mGR.mTex_Story[2][1], mGR.three, 0);
			DrawTexture(gl, mGR.mTex_Story[2][0],mGR.One, 0);
			
			if(Counter> 20)
				DrawTexture(gl, mGR.mTex_Story[2][2], -.60f, -.9f);
			if(Counter > 80)
				DrawTexture(gl, mGR.mTex_Story[2][3], 0.40f, -.9f);
			
			if(Counter == 22)
				M.sound4(GameRenderer.mContext, R.drawable.wocal_4);
			if(Counter == 100)
				M.sound5(GameRenderer.mContext, R.drawable.wocal_5);
			if(Counter == 190){
				M.sound6(GameRenderer.mContext, R.drawable.wocal_6);
			}
			if (mGR.One < -.42f) {
				mGR.One += .1f;
				if (mGR.One > -.42f) {
					mGR.One = -.419f;
				}
			} 
			else if (mGR.three > .39f) {
				if(Counter > 60)
					mGR.three -= .1f;
				if (mGR.three < .39f) {
					mGR.three = .389f;
				}
			}else if(Counter > 220){
				M.GameScreen = M.GAMEMAP;
				M.stop(GameRenderer.mContext);
				M.play(GameRenderer.mContext, R.drawable.garage_map);
			}
			if(Counter > 180){
				mGR.mTex_ShopBG.drawBlack(gl, 0, 0, (Counter - 180)*.04f,1,1);
			}
		}
		DrawTransScal(gl, mGR.mTex_Skip,0.88f, 0.83f, mGR.mSel == 1 ? 1.08f : 1, mGR.mSel == 1 ? 0.5f : 1);
	}
	boolean Handle_Story(MotionEvent event){
		mGR.mSel = 0;
		if(CircRectsOverlap(0.88f, 0.83f, mGR.mTex_Skip.width()*.5f, mGR.mTex_Skip.Height()*.4f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05)){
			mGR.mSel = 1;//Skip
		}
		if (MotionEvent.ACTION_UP == event.getAction()) {
			switch (mGR.mSel) {
			case 1:
				M.GameScreen = M.GAMEMAP;
				M.stop(GameRenderer.mContext);
				M.play(GameRenderer.mContext, R.drawable.garage_map);
				break;

			}
			mGR.mSel = 0;
		}
		return true;
	}
	/***********************Story End**********************************/
	/***********************Promotional End**********************************/
	void Draw_Promotional(GL10 gl){
		DrawTexture(gl, mGR.mTex_CongBG, 0, 0);
		DrawTexture(gl, mGR.mTex_congTex, 0.19f, 0.57f);
		
		DrawTransScal(gl, mGR.mTex_Banner,0.363f, -0.16f, mGR.mSel == 1 ? 1.08f : 1,
				mGR.mSel == 1 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_PlayAgn, 0.59f, -0.67f, mGR.mSel == 2 ? 1.1f : 1,
				mGR.mSel == 2 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_Ratus, .25f, -0.67f, mGR.mSel == 3 ? 1.1f : 1,
				mGR.mSel == 3 ? 0.5f : 1);
		DrawTransScal(gl, mGR.mTex_FB, .00f, -0.67f, mGR.mSel == 4 ? 1.1f : 1,
				mGR.mSel == 4 ? 0.5f : 1);
	}
	boolean Handle_Promotional(MotionEvent event){
		mGR.mSel = 0;
		if(CircRectsOverlap(0.365f, -0.16f, mGR.mTex_Banner.width()*.35f, mGR.mTex_Banner.Height()*.4f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05)){
			mGR.mSel = 1;//More Games
		}
		if(CircRectsOverlap(0.60f, -0.67f, mGR.mTex_Garege.width()*.4f, mGR.mTex_Garege.Height()*.6f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05)){
			mGR.mSel = 2;//play again
		}
		if(CircRectsOverlap(0.25f, -0.67f, mGR.mTex_Garege.width()*.4f, mGR.mTex_Garege.Height()*.6f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05)){
			mGR.mSel = 3;//Rate Us
		}
		if(CircRectsOverlap(0.0f, -0.67f, mGR.mTex_Garege.width()*.4f, mGR.mTex_Garege.Height()*.6f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05)){
			mGR.mSel = 4;//FaceBook
		}
		if (MotionEvent.ACTION_UP == event.getAction()) {
			switch (mGR.mSel) {
			case 1:
				MoreGame();
				break;
			case 2:
				SharedPreferences settings = GameRenderer.mStart.getSharedPreferences("X", Start.MODE_PRIVATE);
				settings.edit().clear().commit();
				GameRenderer.mStart.resume();
				M.GameScreen = M.GAMEMENU;
				M.play(GameRenderer.mContext, R.drawable.splash);
				break;
			case 3:
				MoreGame();
				break;
			case 4:
				facebook();
				break;

			}
			mGR.mSel = 0;
		}
		return true;
	}
	/***********************Promotional End**********************************/
	/*********************** About End **********************************/
	void Draw_About(GL10 gl) {
		DrawTexture(gl, mGR.mTex_Splash, 0, 0);
		DrawTexture(gl, mGR.mTex_Popup, 0, 0);
		DrawTexture(gl, mGR.mTex_About, 0, 0);
		DrawTransScal(gl, mGR.mTex_Ok, 0.82f, -.86f, mGR.mSel == 1 ? 1.1f : 1,
				mGR.mSel == 1 ? 0.5f : 1);
	}

	boolean Handle_About(MotionEvent event) {
		mGR.mSel = 0;
		if (CircRectsOverlap(0.82f, -.86f, mGR.mTex_Ok.width() * .5f,
				mGR.mTex_Ok.Height() * .6f, screen2worldX(event.getX()),
				screen2worldY(event.getY()), .05)) {
			mGR.mSel = 1;
		}
		if (MotionEvent.ACTION_UP == event.getAction()) {
			switch (mGR.mSel) {
			case 1:
				M.GameScreen = M.GAMEMENU;
				M.play(GameRenderer.mContext, R.drawable.splash);
				break;
			}
			mGR.mSel = 0;
		}
		return true;
	}

	/*********************** About End **********************************/
	/***********************WIN OVER CONG Start********************************/
	float inc =0;
	int dInc = 0;
	int vdInc = 1;
	void Draw_WOC(GL10 gl){
		Draw_GameShop(gl);
		mGR.mTex_ShopBG.drawBlack(gl, 0, 0, .8f,1,1);
		DrawTexture(gl, mGR.mTex_OverPOp, 0.0f, 0.0f);
		mGR.mFont.Draw_String(gl, "Attack "+mGR.Attack, -.02f, 0.61f, 1, 1);
		mGR.mFont.Draw_String(gl, "Complete", -.02f, 0.47f, 1, 1);
		switch (M.GameScreen) {
		case M.GAMEOVER:
			DrawTexture(gl, mGR.mTex_FBox, 0.0f, .3f);
			if(mGR.mLast > -.49f)
				DrawTexture(gl, mGR.mTex_Pointer,mGR.mLast*.56f,.33f);
			mGR.mTex_FRect.drawSS(gl,-.29f, .296f,inc,1);
			if(((mGR.mPlayer.mBegin+.5f)*7.67f)>inc)
				inc+=.1f;
			DrawTransScal(gl, mGR.mTex_Reset, -.20f,-.5f,mGR.mSel==1?1.1f:1,mGR.mSel==1?0.5f:1);
			DrawTransScal(gl, mGR.mTex_Garege, .15f,-.5f,mGR.mSel==2?1.1f:1,mGR.mSel==2?0.5f:1);
			break;
		case M.GAMEWIN:
			mGR.mFont.Draw_String(gl, "City Achieve", -.02f, 0.3f, 1, 1);
			DrawTransScal(gl, mGR.mTex_Skip, -.00f,-.5f,mGR.mSel==1?1.1f:1,mGR.mSel==1?0.5f:1);
			break;
		case M.GAMECONG:
			mGR.mFont.Draw_String(gl, "Congrats", -.02f, 0.3f, 1, 1);
			DrawTransScal(gl, mGR.mTex_Skip, -.00f,-.5f,mGR.mSel==1?1.1f:1,mGR.mSel==1?0.5f:1);
			break;
		}
		mGR.mFont.Draw_String(gl, (int)((.5+mGR.mPlayer.mBegin)*1000*mGR.mPlayer.citi)+"m", .04f	, .04f, 2, 0);
		mGR.mFont.Draw_String(gl, ""+mGR.Zombie, .04f,-.09f, 2, 0);
		if(dInc<mGR.Zombie*13){
			dInc+=vdInc;
			if(dInc>mGR.Zombie*13)
				dInc=mGR.Zombie*13;
		}
		mGR.mFont.Draw_String(gl, ""+dInc, .04f,-.21f, 2, 0);
		
	}
	boolean Handle_WOC(MotionEvent event){
		mGR.mSel = 0;
		if(M.GameScreen == M.GAMEOVER){
			if(CircRectsOverlap(-.20f,-.5f, mGR.mTex_Reset.width()*.5f, mGR.mTex_Reset.Height()*.6f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05)){
				mGR.mSel = 1;
			}
			if(CircRectsOverlap(.15f,-.5f, mGR.mTex_Garege.width()*.4f, mGR.mTex_Garege.Height()*.6f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05)){
				mGR.mSel = 2;
			}
		}else{
			if(CircRectsOverlap(.0f,-.5f, mGR.mTex_Garege.width()*.4f, mGR.mTex_Garege.Height()*.6f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05)){
				mGR.mSel = 3;
			}
		}
		if (MotionEvent.ACTION_UP == event.getAction()) {
			switch (mGR.mSel) {
			case 1:
				if (M.GameScreen == M.GAMEWIN && mGR.mPlayer.citi > 1)
					mGR.mPlayer.citi --;
				M.GameScreen = M.GAMEPLAY;
				if(mGR.mLast<mGR.mPlayer.mBegin)
					mGR.mLast=mGR.mPlayer.mBegin;
				mGR.gameReset();
				M.play(GameRenderer.mContext, R.drawable.gameplay_theme1);
				break;
			case 2:
			case 3:
				if(M.GAMEWIN == M.GameScreen){
					M.GameScreen = M.GAMEMAP;
					mGR.mLast = -.5f;
				}
				else
					M.GameScreen = M.GAMESHOP;
				if(mGR.mLast<mGR.mPlayer.mBegin)
					mGR.mLast=mGR.mPlayer.mBegin;
				M.stop(GameRenderer.mContext);
				M.play(GameRenderer.mContext, R.drawable.garage_map);
				break;

			}
			mGR.mSel = 0;
		}
		return true;
	}
	/***********************WIN OVER CONG End**********************************/
	/***********************Map Start********************************/
	final byte mapdot[][] = {
			{-69,-73},
			{-25,-67},
			{ 23,-74},
			{ 71,-59},
			{ 35,-17},
			{-15,-17},
			{-63, 17},
			{-21, 61},
			{ 24, 23},
			{ 26, 66},
	};
	float zx=1,vzx=.01f;
	void Draw_Map(GL10 gl){
		DrawTexture(gl, mGR.mTex_Map, 0, 0);
		DrawTexture(gl, mGR.mTex_MapIcn[mGR.mPlayer.citi%mGR.mTex_BG.length],.705f,.42f);
		for(int i=0;i<mapdot.length;i++){
			if(i == 9)
				DrawTexture(gl, mGR.mTex_MCross,mapdot[i][0]*.01f,mapdot[i][1]*.01f);
			else{
				if(mGR.mPlayer.citi-1 != i){
					DrawTexture(gl, mGR.mTex_MapDot,mapdot[i][0]*.01f,mapdot[i][1]*.01f);
					if(mGR.mPlayer.citi <= i)
						DrawTexture(gl, mGR.mTex_MLock,mapdot[i][0]*.01f,mapdot[i][1]*.01f);
				}else{
					DrawTextureS(gl, mGR.mTex_MapDot,mapdot[i][0]*.01f,mapdot[i][1]*.01f,zx,zx);
				}
			}
		}
		zx+=vzx;
		if(zx > 1.2f)
			vzx = -.01f;
		if(zx < 0.9f)
			vzx = 0.01f;
		DrawTransScal(gl, mGR.mTex_Garege, 0.80f, -.84f,mGR.mSel==1?1.1f:1,mGR.mSel==1?0.5f:1);
	}
	boolean Handle_Map(MotionEvent event){
		mGR.mSel = 0;
		if(CircRectsOverlap(0.8f, -.84f, mGR.mTex_Ok.width()*.7f, mGR.mTex_Ok.Height()*.4f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05)){
			mGR.mSel = 1;
		}
		if (MotionEvent.ACTION_UP == event.getAction()) {
			switch (mGR.mSel) {
			case 1:
				M.GameScreen = M.GAMESHOP;
				break;

			}
			mGR.mSel = 0;
		}
		return true;
	}
	/***********************Map End**********************************/
	/***********************GameMenu Start********************************/
	void Draw_GameMenu(GL10 gl){
		DrawTexture(gl, mGR.mTex_Splash, 0, 0);
		DrawTexture(gl, mGR.mTex_SplashT, .41f, .36f);
		DrawTransScal(gl, mGR.mTex_PlayBtn,  0.70f, -0.38f,mGR.mSel==1?1.1f:1,mGR.mSel==1?0.5f:1);
		
		DrawTransScal(gl, mGR.mTex_AbBut,  0.46f+0.22f*0, -.81f,mGR.mSel==2?1.1f:1,mGR.mSel==2?0.5f:1);
		DrawTransScal(gl, mGR.mTex_FB,  0.46f+0.22f*1, -.81f,mGR.mSel==3?1.1f:1,mGR.mSel==3?0.5f:1);
		DrawTransScal(gl, mGR.mTex_More,  0.46f+0.22f*2, -.81f,mGR.mSel==4?1.1f:1,mGR.mSel==4?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Sound[M.setValue ? 1 : 0],  -.90f, -.81f,mGR.mSel==5?1.1f:1,mGR.mSel==5?0.5f:1);
	}
	boolean Handle_GameMenu(MotionEvent event){
		mGR.mSel = 0;
		if(CircRectsOverlap(0.70f, -0.38f, mGR.mTex_Ok.width()*.7f, mGR.mTex_Ok.Height()*.4f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05)){
			mGR.mSel = 1;
		}
		for(int i=0;i<3;i++){
			if(CircRectsOverlap(0.46f+0.22f*i,-.81f, mGR.mTex_FB.width()*.4f, mGR.mTex_FB.Height()*.4f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05)){
				mGR.mSel = i+2;
			}	
		}
		if(CircRectsOverlap(-.90f, -.81f, mGR.mTex_Ok.width()*.7f, mGR.mTex_Ok.Height()*.4f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05)){
			mGR.mSel = 5;
		}
		if (MotionEvent.ACTION_UP == event.getAction()) {
			switch (mGR.mSel) {
			case 1:
				if(mGR.mPlayer.citi >= 10)
					M.GameScreen = M.GAMEPRMO;
				else{
					M.GameScreen = M.GAMESTORY;
					M.play(GameRenderer.mContext, R.drawable.story);
					mGR.story = 0;
					mGR.One = -2f;
					mGR.two = 2f;
					mGR.three = 2f;
					Counter = 0;

				}
				break;
			case 2:
				M.GameScreen = M.GAMEABOUT;
				break;
			case 3:
				facebook();
				break;
			case 4:
				MoreGame();
				break;
			case 5:
				M.setValue = !M.setValue;
				if(M.setValue)
					M.play(GameRenderer.mContext, R.drawable.splash);
				else
					M.stop(GameRenderer.mContext);
				break;
			}
			mGR.mSel = 0;
		}
		return true;
	}
	/***********************GameMenu End**********************************/
	float power(float val,float power){
		float v = val;
		val =1;
		for(int i=0;i<power;i++)
			val*=v;
		return val;
	}
	float Val(float val){
		float v =val;
		for(int i=0;i<10;i++)
			val+=(v*(i+1));
		return val;
	}
	/***********************GameShop Start********************************/
	float move;
	float movevx;
	void Draw_Popup(GL10 gl){
		mGR.mTex_ShopBG.drawBlack(gl, 0, 0, .7f,1,1);
		DrawTexture(gl, mGR.mTex_Popup, 0, 0);
		switch (mGR.Popup) {
		case 1:
			mGR.mFont.Draw_String(gl, "Engine", 0, .62f, 1, 1);
			for(int i=0;i<3;i++){
				DrawTexture(gl, mGR.mTex_Common, -.40f+i*.40f, 0);
				DrawTexture(gl, mGR.mTex_Engine[i], -.40f+i*.40f, .05f);
				if(!mGR.mCar[mGR.carSelect].Engine[i]){
					mGR.mTex_Common.drawBlack(gl, -.40f+i*.40f, 0,.5f,1,1);
					mGR.mFont.Draw_StringRGB(gl, "$"+(int)(Engin*power(mEngin,i+mGR.carSelect*2)),-.40f + i * .40f, -.21f, 2, 1,1);
				}
				mGR.mFont.Draw_StringRGB(gl, (1 + i + i * 2) + "L", -.50f + i * .40f, -.30f, 0, 1,mGR.mCar[mGR.carSelect].Engine[i]?0:1);
				mGR.mFont.Draw_StringRGB(gl, "Engine", -.37f + i * .40f, -.30f, 2, 1,mGR.mCar[mGR.carSelect].Engine[i]?0:1);				
				if(mGR.mCar[mGR.carSelect].sEngine == i){
					mGR.mFont.Draw_String(gl, "Installed", -.40f+i*.40f, .37f, 2, 1);
					DrawTexture(gl, mGR.mTex_Cross, -.27f+i*.40f, .37f);
				}
				else{
					mGR.mTex_Common.drawBlack(gl, -.40f+i*.40f, 0,.2f,1,1);
					mGR.mFont.Draw_StringRGB(gl, "Tap To Install", -.40f+i*.40f, .37f, 2, 1,mGR.mCar[mGR.carSelect].Engine[i]?0:1);
				}
			}
			break;

		case 2:
			mGR.mFont.Draw_String(gl, "Gear Box", 0, .62f, 1, 1);
			for (int i = 0; i < 3; i++) {
				DrawTexture(gl, mGR.mTex_Common, -.40f + i * .40f, 0);
				DrawTexture(gl, mGR.mTex_Gear[i], -.40f + i * .40f, .05f);
				if (!mGR.mCar[mGR.carSelect].Gear[i]){
					mGR.mTex_Common.drawBlack(gl, -.40f+i*.40f, 0,.5f,1,1);
					mGR.mFont.Draw_StringRGB(gl,  "$"+(int)(Gear*power(mGear, i+mGR.carSelect*2)), -.40f + i * .40f, -.15f, 2, 1,1);
				}
				
				mGR.mFont.Draw_StringRGB(gl, (3 + i) + "Speed", -.40f + i * .40f, -.25f, 2, 1, mGR.mCar[mGR.carSelect].Gear[i]?0:1);
				mGR.mFont.Draw_StringRGB(gl, "Gear Box", -.40f + i * .40f, -.35f, 2, 1, mGR.mCar[mGR.carSelect].Gear[i]?0:1);

				if(mGR.mCar[mGR.carSelect].sGear == i){
					mGR.mFont.Draw_String(gl, "Installed", -.40f+i*.40f, .37f, 2, 1);
					DrawTexture(gl, mGR.mTex_Cross, -.27f+i*.40f, .37f);
				}
				else
					mGR.mFont.Draw_StringRGB(gl, "Tap To Install", -.40f+i*.40f, .37f, 2, 1,mGR.mCar[mGR.carSelect].Gear[i]?0:1);
			
			}
			break;
		case 3:
			char P = (char)(66+mGR.carSelect*3);
			char Q = (char)(75+mGR.carSelect*2);
			mGR.mFont.Draw_String(gl, "Wheel", 0, .62f, 1, 1);
			for (int i = 0; i < 3; i++) {
				DrawTexture(gl, mGR.mTex_Common, -.40f + i * .40f, 0);
				DrawTexture(gl, mGR.mTex_pWheel[mGR.carSelect][i], -.40f + i * .40f, .05f);
				if (!mGR.mCar[mGR.carSelect].Wheels[i]){
						mGR.mTex_Common.drawBlack(gl, -.40f+i*.40f, 0,.5f,1,1);
					mGR.mFont.Draw_StringRGB(gl, "$"+(int)(Wheel*power(mWheel, i+mGR.carSelect*2)),
							-.40f + i * .40f, -.15f, 2, 1,1);
				}
				mGR.mFont.Draw_StringRGB(gl, "R"+(13 + mGR.carSelect*2+i*3) +" "+ P+Q, -.40f + i * .40f, -.25f, 0, 1,mGR.mCar[mGR.carSelect].Wheels[i]?0:1);
				mGR.mFont.Draw_StringRGB(gl, "Wheels", -.40f + i * .40f, -.35f, 2, 1,mGR.mCar[mGR.carSelect].Wheels[i]?0:1);
				
				if(mGR.mCar[mGR.carSelect].sWheel == i){
					mGR.mFont.Draw_String(gl, "Installed", -.40f+i*.40f, .37f, 2, 1);
					DrawTexture(gl, mGR.mTex_Cross, -.27f+i*.40f, .37f);
				}
				else{
					mGR.mTex_Common.drawBlack(gl, -.40f+i*.40f, 0,.2f,1,1);
					mGR.mFont.Draw_StringRGB(gl, "Tap To Install", -.40f+i*.40f, .37f, 2, 1,mGR.mCar[mGR.carSelect].Wheels[i]?0:1);
				}
			
			}
			break;
		case 4:
			mGR.mFont.Draw_String(gl, "vehicle Kit", 0, .62f, 1, 1);
			for (int i = 0; i < 2; i++) {
				DrawTexture(gl, mGR.mTex_Common, -.25f + i * .50f, 0);
				DrawTexture(gl, mGR.mTex_SDKit[mGR.carSelect][i], -.25f + i * .50f, .02f);
				if (!mGR.mCar[mGR.carSelect].Kit[i]) {
					mGR.mTex_Common.drawBlack(gl, -.25f + i * .50f, 0, .5f, 1, 1);
					mGR.mFont.Draw_StringRGB( gl, "$" + (int) (Kit * power(mKit, 1 + i
											+ mGR.carSelect * 2)), -.25f + i * .50f, -.15f, 2, 1, 1);
				}
				mGR.mFont.Draw_StringRGB(gl, name[mGR.carSelect][i], -.25f + i * .50f, -.25f, 2, 1,mGR.mCar[mGR.carSelect].Kit[i]?0:1);
				mGR.mFont.Draw_StringRGB(gl, "Weapons", -.25f + i * .50f, -.35f, 2, 1,mGR.mCar[mGR.carSelect].Kit[i]?0:1);
				if(mGR.mCar[mGR.carSelect].sKit== i && mGR.mCar[mGR.carSelect].Kit[i]){
					mGR.mFont.Draw_String(gl, "Installed", -.25f + i * .50f, .37f, 2, 1);
					DrawTexture(gl, mGR.mTex_Cross, -.13f + i * .50f, .37f);
				}
				else{
					mGR.mTex_Common.drawBlack(gl, -.25f + i * .50f, 0,.2f,1,1);
					mGR.mFont.Draw_StringRGB(gl, "Tap To Install", -.25f + i * .50f, .37f, 2, 1,mGR.mCar[mGR.carSelect].Kit[i]?0:1);
				}
			}
			break;
		case 5:
			mGR.mFont.Draw_String(gl, "Rifle", 0, .62f, 1, 1);
			{
				DrawTexture(gl, mGR.mTex_Common, 0, 0);
				DrawTexture(gl, mGR.mTex_SDGun[mGR.carSelect],0, .02f);
				
				if (mGR.mCar[mGR.carSelect].sGun>0)
					mGR.mFont.Draw_String(gl, "Installed", 0, .37f, 2, 1);
				else
					mGR.mFont.Draw_String(gl, "Tap To Install",0, .37f, 2, 1);

				
				if(mGR.mCar[mGR.carSelect].sGun>=10)
					mGR.mFont.Draw_String(gl, "Full",0, -.25f, 2, 1);
				else if(mGR.mCar[mGR.carSelect].sGun==0){
					mGR.mTex_Common.drawBlack(gl, 0, 0,.5f,1,1);
					mGR.mFont.Draw_StringRGB(gl, "$"+(int)(Val(Gun*(mGR.mCar[mGR.carSelect].sGun+1)*(1+mGR.carSelect))),
							0, -.15f, 2, 1, 1);
					mGR.mFont.Draw_StringRGB(gl, GunName[mGR.carSelect],0, -.25f, 2, 1, 1);
					mGR.mFont.Draw_StringRGB(gl, "Weapons", 0, -.35f, 2, 1, 1);
				}
				else
					mGR.mFont.Draw_String(gl, "$"+(int)(Gun*mGR.mCar[mGR.carSelect].sGun*(1+mGR.carSelect)),0, -.25f, 2, 1);
				
				if (mGR.mCar[mGR.carSelect].sGun > 0) {
					DrawTexture(gl, mGR.mTex_Bar, 0, -.37f);
					for (int i = 0; i < mGR.mCar[mGR.carSelect].sGun; i++)
						DrawTexture(gl, mGR.mTex_Fill, -0.095f + .021f * i, -.374f);
				}
			}
			break;
		case 6:
			mGR.mFont.Draw_String(gl, "Boost", 0, .62f, 1, 1);
			{
				DrawTexture(gl, mGR.mTex_Common, 0, 0);
				DrawTexture(gl, mGR.mTex_SDBoost[mGR.carSelect],0, .02f);
				if (mGR.mCar[mGR.carSelect].Boost > 0)
					mGR.mFont.Draw_String(gl, "Installed", 0, .37f, 2, 1);
				else
					mGR.mFont.Draw_String(gl, "Tap To Install",0, .37f, 2, 1);

				if(mGR.mCar[mGR.carSelect].Boost>=10)
					mGR.mFont.Draw_String(gl, "Full",0, -.25f, 2, 1);
				else if(mGR.mCar[mGR.carSelect].Boost==0){
					mGR.mTex_Common.drawBlack(gl, 0, 0,.5f,1,1);
					mGR.mFont.Draw_StringRGB(gl, "$"+(int)(Val(Bost*(mGR.mCar[mGR.carSelect].Boost+1)*(1+mGR.carSelect))),
							0, -.15f, 2, 1, 1);
					mGR.mFont.Draw_StringRGB(gl, "Boost", 0, -.35f, 2, 1, 1);
				}
				else
					mGR.mFont.Draw_String(gl, "$"+(int)(Bost*mGR.mCar[mGR.carSelect].Boost*(1+mGR.carSelect)),0, -.25f, 2, 1);
				
				if (mGR.mCar[mGR.carSelect].Boost > 0) {
					DrawTexture(gl, mGR.mTex_Bar, 0, -.37f);
					for (int i = 0; i < mGR.mCar[mGR.carSelect].Boost; i++)
						DrawTexture(gl, mGR.mTex_Fill, -0.095f + .021f * i, -.374f);
				}
			}
			break;
		case 7:
			mGR.mFont.Draw_String(gl, "Fual", 0, .62f, 1, 1);
			{
				DrawTexture(gl, mGR.mTex_Common, 0, 0);
				DrawTexture(gl, mGR.mTex_Fual,0, .02f);
				if (mGR.mCar[mGR.carSelect].Petrol > 0)
					mGR.mFont.Draw_String(gl, "Installed", 0, .37f, 2, 1);
				else
					mGR.mFont.Draw_String(gl, "Tap To Install",0, .37f, 2, 1);

				if(mGR.mCar[mGR.carSelect].Petrol>=10)
					mGR.mFont.Draw_String(gl, "Full",0, -.25f, 2, 1);
				else
					mGR.mFont.Draw_String(gl, "$"+(int)(Petrol*mGR.mCar[mGR.carSelect].Petrol*(1+mGR.carSelect)),0, -.25f, 2, 1);
				
				if (mGR.mCar[mGR.carSelect].Petrol > 0) {
					DrawTexture(gl, mGR.mTex_Bar, 0, -.37f);
					for (int i = 0; i < mGR.mCar[mGR.carSelect].Petrol; i++)
						DrawTexture(gl, mGR.mTex_Fill, -0.095f + .021f * i, -.374f);
				}
			}
			break;
		}
		DrawTransScal(gl, mGR.mTex_Ok, 0, -.61f,mGR.mSel==1?1.1f:1,mGR.mSel==1?0.5f:1);
	}

	boolean Handle_PopUp(MotionEvent event) {
		mGR.mSel = 0;
		if(CircRectsOverlap(0, -.61f, mGR.mTex_Ok.width()*.7f, mGR.mTex_Ok.Height()*.4f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05)){
			mGR.mSel = 50;
		}
		if (mGR.Popup < 4) {
			for (int i = 0; i < 3; i++) {
				if (CircRectsOverlap(-.40f + i * .40f, 0, mGR.mTex_Common.width() * .4f, mGR.mTex_Common.Height() * .4f,
						screen2worldX(event.getX()), screen2worldY(event.getY()), .05)) {
					mGR.mSel = i+100;
				}
			}
		}
		if (mGR.Popup == 4) {
			for (int i = 0; i < 2; i++) {
				if (CircRectsOverlap(-.25f + i * .50f, 0, mGR.mTex_Common.width() * .4f, mGR.mTex_Common.Height() * .4f,
						screen2worldX(event.getX()), screen2worldY(event.getY()), .05)) {
					mGR.mSel = i+100;
				}
			}
		}
		if (mGR.Popup > 4) {
			
				if (CircRectsOverlap(0, 0, mGR.mTex_Common.width() * .4f, mGR.mTex_Common.Height() * .4f,
						screen2worldX(event.getX()), screen2worldY(event.getY()), .05)) {
					mGR.mSel = 100;
				}
			
		}
		if(MotionEvent.ACTION_UP == event.getAction() && mGR.mSel > 0){
//			mGR.mCar[mGR.carSelect].sGun++;
			switch (mGR.mSel) {
			case 50:
				mGR.Popup =0;
				break;
			default:
				mGR.mSel -=100; 
				switch (mGR.Popup) {
				case 1:
					if(mGR.mCar[mGR.carSelect].Engine[mGR.mSel])
					{
						mGR.mCar[mGR.carSelect].sEngine = mGR.mSel;
					}else{
						if(mGR.Dollor>=(int)(Engin*power(mEngin,mGR.mSel+mGR.carSelect*2)))
						{
							mGR.Dollor-=(int)(Engin*power(mEngin,mGR.mSel+mGR.carSelect*2));
							mGR.mCar[mGR.carSelect].sEngine = mGR.mSel;
							mGR.mCar[mGR.carSelect].Engine[mGR.mSel] = true;
						}
					}
					break;
				case 2:
					if(mGR.mCar[mGR.carSelect].Gear[mGR.mSel])
					{
						mGR.mCar[mGR.carSelect].sGear = mGR.mSel;
					}else{
						if(mGR.Dollor>=(int)(Gear*power(mGear,mGR.mSel+mGR.carSelect*2)))
						{
							mGR.Dollor-=(int)(Gear*power(mGear,mGR.mSel+mGR.carSelect*2));
							mGR.mCar[mGR.carSelect].sGear = mGR.mSel;
							mGR.mCar[mGR.carSelect].Gear[mGR.mSel] = true;
						}
					}
					break;
				case 3:
					if(mGR.mCar[mGR.carSelect].Wheels[mGR.mSel])
					{
						mGR.mCar[mGR.carSelect].sWheel = mGR.mSel;
					}else{
						if(mGR.Dollor>=(int)(Wheel*power(mWheel,mGR.mSel+mGR.carSelect*2)))
						{
							mGR.Dollor-=(int)(Wheel*power(mWheel,mGR.mSel+mGR.carSelect*2));
							mGR.mCar[mGR.carSelect].sWheel= mGR.mSel;
							mGR.mCar[mGR.carSelect].Wheels[mGR.mSel] = true;
						}
					}
					break;
				case 4:
					if(mGR.mCar[mGR.carSelect].Kit[mGR.mSel])
					{
						mGR.mCar[mGR.carSelect].sKit = mGR.mSel;
					}else{
						if(mGR.Dollor>=(int)(Kit*power(mKit,mGR.mSel-1+mGR.carSelect*2)))
						{
							mGR.Dollor-=(int)(Kit*power(mKit,mGR.mSel-1+mGR.carSelect*2));
							mGR.mCar[mGR.carSelect].sKit= mGR.mSel;
							mGR.mCar[mGR.carSelect].Kit[mGR.mSel] = true;
						}
					}
					break;
				case 5:
					if(mGR.mCar[mGR.carSelect].sGun ==0)
					{
						if(mGR.Dollor>=(int)(Val(Gun*(mGR.mCar[mGR.carSelect].sGun+1)*(1+mGR.carSelect)))){
							mGR.Dollor-=(int)(Val(Gun*(mGR.mCar[mGR.carSelect].sGun+1)*(1+mGR.carSelect)));
							mGR.mCar[mGR.carSelect].sGun++;
									
						}
					}
					else if(mGR.mCar[mGR.carSelect].sGun<10){
						if(mGR.Dollor>=(int)(Gun*mGR.mCar[mGR.carSelect].sGun*(1+mGR.carSelect))){
							mGR.Dollor-=(int)(Gun*mGR.mCar[mGR.carSelect].sGun*(1+mGR.carSelect));
							mGR.mCar[mGR.carSelect].sGun++;
									
						}
					}
					break;
				case 6:
					if(mGR.mCar[mGR.carSelect].Boost==0)
					{
						if(mGR.Dollor>=(int)(Val(Bost*(mGR.mCar[mGR.carSelect].Boost+1)*(1+mGR.carSelect)))){
							mGR.Dollor-=(int)(Val(Bost*(mGR.mCar[mGR.carSelect].Boost+1)*(1+mGR.carSelect)));
							mGR.mCar[mGR.carSelect].Boost++;
									
						}
					}
					else if(mGR.mCar[mGR.carSelect].Boost<10){
						if(mGR.Dollor>=(int)(Bost*mGR.mCar[mGR.carSelect].Boost*(1+mGR.carSelect))){
							mGR.Dollor-=(int)(Bost*mGR.mCar[mGR.carSelect].Boost*(1+mGR.carSelect));
							mGR.mCar[mGR.carSelect].Boost++;
									
						}
					}
					break;
				case 7:
					if(mGR.mCar[mGR.carSelect].Petrol<10){
						if(mGR.Dollor>=(int)(Petrol*mGR.mCar[mGR.carSelect].Petrol*(1+mGR.carSelect))){
							mGR.Dollor-=(int)(Petrol*mGR.mCar[mGR.carSelect].Petrol*(1+mGR.carSelect));
							mGR.mCar[mGR.carSelect].Petrol++;
									
						}
					}
					break;
				}
				break;
			}
			mGR.mSel = 0;
		}
		return true; 
	}
	String name[][] = { { "Ballista", "Fauchard" }, { "Francisca", "Halberd" },
			{ "Bardiche", "Guisarme" }, { "Cataphract", "Penteconter" },
			{ "Chariot", "Sambuca" }, { "Claymore", "Testudo" },
			{ "Cavalry", "Steppe Pony" }, { "Corseque", "Voulge" } };
	
	String GunName[] = { "Assault",
			"Buntline",
			"AAC Honey",
			"KAI OICW",
			"Madam Mk2",
			"AMT AutoMg ",
			"F88S GLA ",
			"LCZ B20",};

	int Engin =40;
	int Gear =35;
	int Wheel =38;
	int Kit =30;
	int Gun =6;
	int Bost =7;
	int Petrol =5;
	int carPrice = 500;
	float mEngin =1.8f;
	float mGear =1.8f;
	float mWheel =1.8f;
	float mKit =1.8f;
	
	// CarY,WheelY,rearWheel,frantWheel,boostx,boosty,Gunx,guny,kitx,kity
	float carPos[][] = { 
			{ -.11f, -.26f, -.22f, .20f ,-.20f,0.31f, 0.03f,0.33f, 0.29f, -.15f},//0
			{ -.11f, -.24f, -.24f, .17f ,-.29f,0.38f, 0.03f,0.32f, 0.32f, -.08f},//1
			{ -.12f, -.23f, -.22f, .31f ,-.37f,0.01f, 0.31f,0.13f, 0.42f, -.04f},//2-
			{ -.03f, -.28f, -.23f, .25f ,-.27f,0.30f, 0.10f,0.39f, 0.36f, 0.05f},//3
			{ -.03f, -.32f, -.24f, .21f ,-.29f,0.04f, 0.19f,0.10f, 0.39f, -.13f},//4
			{ -.00f, -.33f, -.32f, .39f ,0.04f,0.31f, 0.41f,0.13f, 0.55f, -.10f},//5-
			{ 0.10f, -.47f, -.30f, .47f ,0.05f,0.42f, 0.49f,0.06f, 0.67f, -.24f},//6-
			{ 0.10f, -.47f, -.46f, .45f ,-.28f,-.02f, 0.55f,0.07f, 0.67f, -.24f},//7
			};
	void Draw_GameShop(GL10 gl){
		DrawTexture(gl, mGR.mTex_ShopBG, 0, 0);
		mGR.mFont.Draw_String(gl, "ATTACK "+mGR.Attack, 0, .88f, 1, 1);
		DrawTextureS(gl, mGR.mTex_SWeapon[mGR.carSelect][4],move, carPos[mGR.carSelect][0]+carPos[mGR.carSelect][1]-mGR.mTex_STyre[0][0].Height()*.5f,1,1);
		DrawTextureS(gl, mGR.mTex_SCar[mGR.carSelect],move, carPos[mGR.carSelect][0],1,1);
		if(mGR.mCar[mGR.carSelect].Boost>0)
			DrawTextureS(gl, mGR.mTex_SWeapon[mGR.carSelect][0],move+carPos[mGR.carSelect][4], carPos[mGR.carSelect][0]+carPos[mGR.carSelect][5],1,1);
		if(mGR.mCar[mGR.carSelect].sGun>0)
			DrawTextureS(gl, mGR.mTex_SWeapon[mGR.carSelect][1],move+carPos[mGR.carSelect][6], carPos[mGR.carSelect][0]+carPos[mGR.carSelect][7],1,1);
		if(mGR.mCar[mGR.carSelect].Kit[0] && mGR.mCar[mGR.carSelect].sKit == 0)
			DrawTextureS(gl, mGR.mTex_SWeapon[mGR.carSelect][2],move+carPos[mGR.carSelect][8], carPos[mGR.carSelect][0]+carPos[mGR.carSelect][9],1,1);
		if(mGR.mCar[mGR.carSelect].Kit[1] && mGR.mCar[mGR.carSelect].sKit == 1)
			DrawTextureS(gl, mGR.mTex_SWeapon[mGR.carSelect][3],move+carPos[mGR.carSelect][8], carPos[mGR.carSelect][0]+carPos[mGR.carSelect][9],1,1);
		DrawTextureS(gl, mGR.mTex_STyre[mGR.carSelect][mGR.mCar[mGR.carSelect].sWheel],move+carPos[mGR.carSelect][2], carPos[mGR.carSelect][0]+carPos[mGR.carSelect][1],1,1);
		DrawTextureS(gl, mGR.mTex_STyre[mGR.carSelect][mGR.mCar[mGR.carSelect].sWheel],move+carPos[mGR.carSelect][3], carPos[mGR.carSelect][0]+carPos[mGR.carSelect][1],1,1);
		if(!mGR.unclockCar[mGR.carSelect]){
			DrawTextureS(gl, mGR.mTex_Lock,move, carPos[mGR.carSelect][0],1,1);
			mGR.mFont.Draw_String(gl, ""+(int)(carPrice* power( 4,mGR.carSelect)),move-.05f, carPos[mGR.carSelect][0]-.2f, 1, 1);
			
		}
		if(movevx !=0){
			int nxt = (movevx>0?1:-1)+mGR.carSelect;
			DrawTextureS(gl, mGR.mTex_SWeapon[nxt][4],(movevx>0?-1:1)*2+move, carPos[nxt][0]+carPos[nxt][1]
					-mGR.mTex_STyre[0][0].Height()*.5f,1,1);
			DrawTextureS(gl, mGR.mTex_SCar[nxt], (movevx>0?-1:1)*2+move,carPos[nxt][0],1,1);
			if(mGR.mCar[nxt].Boost>0)
				DrawTextureS(gl, mGR.mTex_SWeapon[nxt][0],(movevx>0?-1:1)*2+move+carPos[nxt][4], carPos[nxt][0]+carPos[nxt][5],1,1);
			if(mGR.mCar[nxt].sGun>0)
				DrawTextureS(gl, mGR.mTex_SWeapon[nxt][1],(movevx>0?-1:1)*2+move+carPos[nxt][6], carPos[nxt][0]+carPos[nxt][7],1,1);
			if(mGR.mCar[nxt].Kit[0] && mGR.mCar[nxt].sKit == 0)
				DrawTextureS(gl, mGR.mTex_SWeapon[nxt][2],(movevx>0?-1:1)*2+move+carPos[nxt][8], carPos[nxt][0]+carPos[nxt][9],1,1);
			if(mGR.mCar[nxt].Kit[1] && mGR.mCar[nxt].sKit == 1)
				DrawTextureS(gl, mGR.mTex_SWeapon[nxt][3],(movevx>0?-1:1)*2+move+carPos[nxt][8], carPos[nxt][0]+carPos[nxt][9],1,1);
			
			DrawTextureS(gl, mGR.mTex_STyre[nxt][mGR.mCar[nxt].sWheel],(movevx>0?-1:1)*2+move+carPos[nxt][2], carPos[nxt][0]+carPos[nxt][1],1,1);
			DrawTextureS(gl, mGR.mTex_STyre[nxt][mGR.mCar[nxt].sWheel],(movevx>0?-1:1)*2+move+carPos[nxt][3], carPos[nxt][0]+carPos[nxt][1],1,1);
			
			//DrawTextureS(gl, mGR.mTex_SCar[(movevx>0?1:-1)+mGR.carSelect],(movevx>0?-1:1)*2+move, -.21f,1,1);
			move +=movevx;
			if(Math.abs(move) >1.9){
				mGR.carSelect += movevx > 0 ? 1 : -1;
				move = movevx =0;
			}
		}
		
	
		
		if(mGR.carSelect > 0)
			DrawTexture(gl, mGR.mTex_Arrow,-.56f,.22f);
		if(mGR.carSelect < 7)
			DrawTextureR(gl, mGR.mTex_Arrow,.56f,.22f,180);
		for(int i=0;i<7;i++){
			DrawTransScal(gl, mGR.mTex_Shopitem, -.84f + i * .28f, -.785f,mGR.mSel==(i+3)?1.05f:1,mGR.mSel==(i+3)?0.5f:1);
			if(i==0){
				DrawTexture(gl, mGR.mTex_Engine[mGR.mCar[mGR.carSelect].sEngine], -.84f + i * .28f, -.785f);
				
				for(int j=0;j<3;j++)
				{
					if(j<=mGR.mCar[mGR.carSelect].sEngine)
						DrawTextureS(gl, mGR.mTex_Fill, (-.015f+.015f*j)-.84f + i * .28f, -.96f,.6f,1);
					else
						mGR.mTex_Fill.drawBlack(gl, (-.015f+.015f*j)-.84f + i * .28f, -.96f,.7f,.6f,1);
				}
				if(mGR.unclockCar[mGR.carSelect])
				{
					if(mGR.mCar[mGR.carSelect].Engine[2])
						mGR.mFont.Draw_String(gl, "Full",-.84f + i * .28f, -.617f, 2, 1);
					else
						mGR.mFont.Draw_String(gl, "$"+(int)(Engin*power(mEngin, mGR.mCar[mGR.carSelect].sEngine+1+mGR.carSelect*2)),-.84f + i * .28f, -.62f, 2, 1);
					
				}
				else{
					mGR.mFont.Draw_String(gl, "na",-.84f + i * .28f, -.62f, 2, 1);
				}
			}
			if(i==1){
				DrawTexture(gl, mGR.mTex_Gear[mGR.mCar[mGR.carSelect].sGear], -.84f + i * .28f, -.785f);
				for(int j=0;j<3;j++)
				{
					if(j<=mGR.mCar[mGR.carSelect].sGear)
						DrawTextureS(gl, mGR.mTex_Fill, (-.015f+.015f*j)-.84f + i * .28f, -.96f,.6f,1);
					else
						mGR.mTex_Fill.drawBlack(gl, (-.015f+.015f*j)-.84f + i * .28f, -.96f,.7f,.6f,1);
				}
				if(mGR.unclockCar[mGR.carSelect]){
					if(mGR.mCar[mGR.carSelect].Gear[2])
						mGR.mFont.Draw_String(gl, "Full",-.84f + i * .28f, -.617f, 2, 1);
					else
						mGR.mFont.Draw_String(gl, "$"+(int)(Gear*power(mGear, mGR.mCar[mGR.carSelect].sGear+mGR.carSelect*2)),-.84f + i * .28f, -.62f, 2, 1);
				}else{
					mGR.mFont.Draw_String(gl, "na",-.84f + i * .28f, -.62f, 2, 1);
				}
			}
			if(i==2){
				DrawTexture(gl, mGR.mTex_pWheel[mGR.carSelect][mGR.mCar[mGR.carSelect].sWheel], -.84f + i * .28f, -.785f);
				for(int j=0;j<3;j++)
				{
					if(j<=mGR.mCar[mGR.carSelect].sWheel)
						DrawTextureS(gl, mGR.mTex_Fill, (-.015f+.015f*j)-.84f + i * .28f, -.96f,.6f,1);
					else
						mGR.mTex_Fill.drawBlack(gl, (-.015f+.015f*j)-.84f + i * .28f, -.96f,.7f,.6f,1);
				}
				if(mGR.unclockCar[mGR.carSelect]){
					if(mGR.mCar[mGR.carSelect].Wheels[2])
						mGR.mFont.Draw_String(gl, "Full",-.84f + i * .28f, -.617f, 2, 1);
					else
						mGR.mFont.Draw_String(gl, "$"+(int)(Wheel*power(mWheel, mGR.mCar[mGR.carSelect].sWheel+mGR.carSelect*2)),-.84f + i * .28f, -.62f, 2, 1);
				}else{
					mGR.mFont.Draw_String(gl, "na",-.84f + i * .28f, -.62f, 2, 1);
				}
			}
			if(i==3){
				DrawTexture(gl, mGR.mTex_SDKit[mGR.carSelect][mGR.mCar[mGR.carSelect].sKit], -.84f + i * .28f, -.785f);
				for(int j=0;j<2;j++)
				{
					if(j<=mGR.mCar[mGR.carSelect].sKit && mGR.mCar[mGR.carSelect].Kit[j])
						DrawTextureS(gl, mGR.mTex_Fill, (-.007f+.015f*j)-.84f + i * .28f, -.96f,.6f,1);
					else
						mGR.mTex_Fill.drawBlack(gl, (-.007f+.015f*j)-.84f + i * .28f, -.96f,.7f,.6f,1);
				}
				if(mGR.unclockCar[mGR.carSelect]){
					if(mGR.mCar[mGR.carSelect].Kit[1])
						mGR.mFont.Draw_String(gl, "Full",-.84f + i * .28f, -.617f, 2, 1);
					else
						mGR.mFont.Draw_String(gl, "$"+(int)(Kit*power(mKit, 1+mGR.mCar[mGR.carSelect].sKit+mGR.carSelect*2)),-.84f + i * .28f, -.62f, 2, 1);
				}else{
					mGR.mFont.Draw_String(gl, "na",-.84f + i * .28f, -.62f, 2, 1);
				}
			}
			if(i==4){
				DrawTexture(gl, mGR.mTex_SDGun[mGR.carSelect], -.84f + i * .28f, -.785f);
				for(int j=0;j<10;j++)
				{
					if(j<mGR.mCar[mGR.carSelect].sGun)
						DrawTextureS(gl, mGR.mTex_Fill, (-.067f+.015f*j)-.84f + i * .28f, -.96f,.6f,1);
					else
						mGR.mTex_Fill.drawBlack(gl, (-.067f+.015f*j)-.84f + i * .28f, -.96f,.7f,.6f,1);
				}
				if(mGR.unclockCar[mGR.carSelect]){
					if(mGR.mCar[mGR.carSelect].sGun>=10)
						mGR.mFont.Draw_String(gl, "Full",-.84f + i * .28f, -.617f, 2, 1);
					else if(mGR.mCar[mGR.carSelect].sGun==0)
						mGR.mFont.Draw_String(gl, "$"+(int)(Val(Gun*(mGR.mCar[mGR.carSelect].sGun+1)*(1+mGR.carSelect))),-.84f + i * .28f, -.62f, 2, 1);
					else
						mGR.mFont.Draw_String(gl, "$"+(int)(Gun*mGR.mCar[mGR.carSelect].sGun*(1+mGR.carSelect)),-.84f + i * .28f, -.62f, 2, 1);
				}else{
					mGR.mFont.Draw_String(gl, "na",-.84f + i * .28f, -.62f, 2, 1);
				}
			}
			if(i==5){
				DrawTexture(gl, mGR.mTex_SDBoost[mGR.carSelect], -.84f + i * .28f, -.785f);
				for(int j=0;j<10;j++)
				{
					if(j<mGR.mCar[mGR.carSelect].Boost)
						DrawTextureS(gl, mGR.mTex_Fill, (-.067f+.015f*j)-.84f + i * .28f, -.96f,.6f,1);
					else
						mGR.mTex_Fill.drawBlack(gl, (-.067f+.015f*j)-.84f + i * .28f, -.96f,.7f,.6f,1);
				}
				if(mGR.unclockCar[mGR.carSelect]){
					if(mGR.mCar[mGR.carSelect].Boost>=10)
						mGR.mFont.Draw_String(gl, "Full",-.84f + i * .28f, -.617f, 2, 1);
					else if(mGR.mCar[mGR.carSelect].Boost==0)
						mGR.mFont.Draw_String(gl, "$"+(int)(Val(Bost*(mGR.mCar[mGR.carSelect].Boost+1)*(1+mGR.carSelect))),-.84f + i * .28f, -.62f, 2, 1);
					else
						mGR.mFont.Draw_String(gl, "$"+(int)(Bost*mGR.mCar[mGR.carSelect].Boost*(1+mGR.carSelect)),-.84f + i * .28f, -.62f, 2, 1);
				}else{
					mGR.mFont.Draw_String(gl, "na",-.84f + i * .28f, -.62f, 2, 1);
				}
			}
			if(i==6){
				DrawTexture(gl, mGR.mTex_Fual, -.84f + i * .28f, -.785f);
				for(int j=0;j<10;j++)
				{
					if(j<mGR.mCar[mGR.carSelect].Petrol)
						DrawTextureS(gl, mGR.mTex_Fill, (-.067f+.015f*j)-.84f + i * .28f, -.96f,.6f,1);
					else
						mGR.mTex_Fill.drawBlack(gl, (-.067f+.015f*j)-.84f + i * .28f, -.96f,.7f,.6f,1);
				}
				if(mGR.unclockCar[mGR.carSelect]){
					if(mGR.mCar[mGR.carSelect].Petrol>=10)
						mGR.mFont.Draw_String(gl, "Full",-.84f + i * .28f, -.617f, 2, 1);
					else
						mGR.mFont.Draw_String(gl, "$"+(int)(Petrol*mGR.mCar[mGR.carSelect].Petrol*(1+mGR.carSelect)),-.84f + i * .28f, -.62f, 2, 1);
				}else{
					mGR.mFont.Draw_String(gl, "na",-.84f + i * .28f, -.62f, 2, 1);
				}
			}
		}
		
		
		
		
		DrawTransScal(gl, mGR.mTex_MapBtn,-.84f, 0.89f,mGR.mSel==22?1.1f:1,mGR.mSel==22?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Buy	,  .80f, -.33f,mGR.mSel==23?1.1f:1,mGR.mSel==23?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Play	, -.81f, -.33f,mGR.mSel==24?1.1f:1,mGR.mSel==24?0.5f:1);
		
		if(mGR.Popup!=0)
			Draw_Popup(gl);
//		DrawTexture(gl, mGR.mTex_CoinBar , .82f, .89f);
		mGR.mFont.Draw_String(gl, ""+mGR.Dollor, .50f, .89f, 1, 0);
	}
	boolean Handle_GameShop(MotionEvent event){
		mGR.mSel = 0;
		if(CircRectsOverlap(-.56f,.22f, mGR.mTex_Arrow.width()*.6f, mGR.mTex_Arrow.Height()*.6f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05)){
			mGR.mSel = 1;
		}
		if(CircRectsOverlap(0.56f,.22f, mGR.mTex_Arrow.width()*.6f, mGR.mTex_Arrow.Height()*.6f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05)){
			mGR.mSel = 2;
		}
		for(int i=0;i<7;i++){
			if(CircRectsOverlap(-.84f + i * .28f, -.785f, mGR.mTex_Shopitem.width()*.4f, mGR.mTex_Shopitem.Height()*.4f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05)){
				mGR.mSel = i+3;
			}
		}
		if(CircRectsOverlap(move, carPos[mGR.carSelect][0], mGR.mTex_SCar[0].width()*.6f, mGR.mTex_SCar[0].Height()*.4f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05)){
			mGR.mSel = 21;
		}
		if(CircRectsOverlap(-.84f, 0.89f, mGR.mTex_MapBtn.width()*.6f, mGR.mTex_MapBtn.Height()*.4f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05)){
			mGR.mSel = 22;
		}
		if(CircRectsOverlap(.80f, -.33f, mGR.mTex_MapBtn.width()*.6f, mGR.mTex_MapBtn.Height()*.4f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05)){
			mGR.mSel = 23;
		}
		if(CircRectsOverlap(-.81f, -.33f, mGR.mTex_MapBtn.width()*.6f, mGR.mTex_MapBtn.Height()*.4f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05)){
			mGR.mSel = 24;
		}
		if(MotionEvent.ACTION_UP == event.getAction() && mGR.mSel > 0){
			switch (mGR.mSel) {
			case 1:
				if(mGR.carSelect > 0 && movevx == 0)
					movevx=-.15f;
				break;
			case 2:
				if(mGR.carSelect < 7 && movevx == 0)
					movevx=0.15f;
				break;
			case 21:
				int val = (int)(carPrice* power( 4,mGR.carSelect));
				if(!mGR.unclockCar[mGR.carSelect] && val <= mGR.Dollor){
					mGR.Dollor -= val;
					mGR.unclockCar[mGR.carSelect] = true;
				}
				break;
			case 22:
				M.GameScreen = M.GAMEMAP;
				break;
			case 23:
				mGR.mInApp.onBuyGold50000(null);
//				M.GameScreen = M.GAMEBUY;
				break;
			case 24:
				mGR.gameReset();
				M.GameScreen = M.GAMEPLAY;
				M.stop(GameRenderer.mContext);
				M.play(GameRenderer.mContext, R.drawable.gameplay_theme1);
				break;
			default:
				if(mGR.unclockCar[mGR.carSelect])
					mGR.Popup = mGR.mSel - 2;
				break;
			}
			mGR.mSel = 0;
		}
		return true; 
	}
	/***********************GameShop End**********************************/
	/***********************GamePlay Start**********************************/
	// CarY,WheelY,rearWheel,frantWheel,boostx,boosty,fireboostx,fireboosty,Gunx,guny,firGunx,firguny,kitx,kity
		final float GamecarPos[][] = { 
				{ -.47f,-.12f, -.10f, 0.10f ,-.10f, 0.15f,-.22f, 0.15f,0.03f, 0.16f,0.13f, 0.19f, 0.13f, -.01f},//0
				{ -.47f,-.11f, -.11f, 0.08f ,-.14f, 0.17f,-.22f, 0.15f,-.01f, 0.16f,0.10f, 0.18f, 0.14f, -.01f},//1
				{ -.47f,-.10f, -.09f, 0.13f ,-.17f, 0.00f,-.27f, 0.01f,0.16f, 0.06f,0.26f, 0.09f, 0.18f, -.03f},//2-
				{ -.47f,-.10f, -.09f, 0.10f ,-.10f, 0.12f,-.19f, 0.14f,0.05f, 0.15f,0.16f, 0.16f, 0.13f, 0.03f},//3
				{ -.47f,-.10f, -.09f, 0.08f ,-.10f, 0.00f,-.20f, 0.00f,0.09f, 0.05f,0.20f, 0.06f, 0.15f, -.03f},//4
				{ -.44f,-.12f, -.12f, 0.14f ,0.03f, 0.12f,-.06f, 0.17f,0.20f, 0.07f,0.33f, 0.09f, 0.21f, -.03f},//5-
				{ -.39f,-.17f, -.11f, 0.17f ,0.02f, 0.16f,-.11f, 0.17f,0.18f, 0.03f,0.27f, 0.03f, 0.24f, -.09f},//6-
				{ -.40f,-.16f, -.15f, 0.17f ,-.10f, -.01f,-.21f, 0.00f,0.22f, 0.03f,0.33f, 0.06f, 0.23f, -.06f},//7
				};

	void Draw_equipment(GL10 gl) {
		switch (mGR.mPlayer.img) {
		case 0:
			if (mGR.mPlayer.kitNo == 2) {// for kit 2
				DrawFullRotate(gl, mGR.mTex_Cutter, mGR.mPlayer.x + .17f, 
						GamecarPos[mGR.mPlayer.img][0] - .01f + mGR.mPlayer.p, Counter * 10, 1);
			}
			break;
		case 1:
			if (mGR.mPlayer.Boostfill > 1 && mGR.mPlayer.BoostActive) { //Boost
				DrawTexture(gl, mGR.mTex_gBoost[Counter % 2], mGR.mPlayer.x - 0.218f,
						GamecarPos[mGR.mPlayer.img][0] + 0.22f + mGR.mPlayer.p);
			}
			if (mGR.mPlayer.kitNo == 2) {// for kit 2
				DrawFullRotate(gl, mGR.mTex_Cutter, mGR.mPlayer.x + .19f,
						GamecarPos[mGR.mPlayer.img][0] + .04f + mGR.mPlayer.p, Counter * 10, 1);
				DrawFullRotate(gl, mGR.mTex_Cutter, mGR.mPlayer.x + .19f,
						GamecarPos[mGR.mPlayer.img][0] - .05f + mGR.mPlayer.p, Counter * 10, 1);
			}
			break;
		case 2:
			if (mGR.mPlayer.kitNo == 2) {// for kit 2
				DrawFullRotate(gl, mGR.mTex_Cutter, mGR.mPlayer.x + .25f,
						GamecarPos[mGR.mPlayer.img][0] - .08f + mGR.mPlayer.p,Counter * 10, 1);
			}
			break;
		case 3:
			if (mGR.mPlayer.kitNo == 1) {// for kit1
				DrawFullRotate(gl, mGR.mTex_Cutter, mGR.mPlayer.x + .19f,
						GamecarPos[mGR.mPlayer.img][0] + 0 + mGR.mPlayer.p, Counter * 10, .9f);
				DrawFullRotate(gl, mGR.mTex_Cutter, mGR.mPlayer.x + 0.18f,
						GamecarPos[mGR.mPlayer.img][0] + .05f + mGR.mPlayer.p, Counter * 10, .5f);
			}
			if (mGR.mPlayer.kitNo == 2) {// for kit 2
				DrawFullRotate(gl, mGR.mTex_Cutter, mGR.mPlayer.x + .15f,
						GamecarPos[mGR.mPlayer.img][0] + .02f + mGR.mPlayer.p, Counter * 10, 1);
			}
			break;
		case 4:
			if (mGR.mPlayer.kitNo == 2) {// for kit 2
				DrawFullRotate(gl, mGR.mTex_Cutter, mGR.mPlayer.x + .158f,
						GamecarPos[mGR.mPlayer.img][0] - .06f + mGR.mPlayer.p, Counter * 10, 1);
			}
			break;
		case 5:
			if (mGR.mPlayer.Boostfill > 1 && mGR.mPlayer.BoostActive) {// Boost
				DrawTexture(gl, mGR.mTex_gBoost[Counter % 2],
						mGR.mPlayer.x - 0.09f, GamecarPos[mGR.mPlayer.img][0] + 0.08f + mGR.mPlayer.p);
			}
			break;
		case 6:
			if (mGR.mPlayer.kitNo == 2) {// for kit 2
				DrawFullRotate(gl, mGR.mTex_Cutter, mGR.mPlayer.x + .25f,
						GamecarPos[mGR.mPlayer.img][0] - .03f + mGR.mPlayer.p, Counter * 10, .7f);
				DrawFullRotate(gl, mGR.mTex_Cutter, mGR.mPlayer.x + .30f,
						GamecarPos[mGR.mPlayer.img][0] - .08f + mGR.mPlayer.p, Counter * 10, 1);
				DrawFullRotate(gl, mGR.mTex_Cutter, mGR.mPlayer.x + .28f,
						GamecarPos[mGR.mPlayer.img][0] - .14f + mGR.mPlayer.p, Counter * 10, .7f);
			}
			break;
		case 7:
			if (mGR.mPlayer.kitNo == 1 || mGR.mPlayer.kitNo == 2) {// for kit1 and  kit2
				mGR.mTex_Arra.drawfullRotet(gl, mGR.mPlayer.x + .24f,
						GamecarPos[mGR.mPlayer.img][0] - .07f + mGR.mPlayer.p, cx, .3f, 1);
				if (cx < -45)
					cvx = 5;
				if (cx > 45)
					cvx = -5;
				cx += cvx;
			}
			break;
		}
		if (mGR.mPlayer.Boostfill > 0) {
			if(mGR.mPlayer.BoostActive && mGR.mPlayer.Boostfill > 1){
				DrawTexture(gl, mGR.mTex_gBoost[Counter % 2], mGR.mPlayer.x + GamecarPos[mGR.mPlayer.img][6],
						GamecarPos[mGR.mPlayer.img][0] + GamecarPos[mGR.mPlayer.img][7] + mGR.mPlayer.p);
			}
			DrawTexture(gl, mGR.mTex_CarAcc[mGR.mPlayer.img][3], mGR.mPlayer.x + GamecarPos[mGR.mPlayer.img][4],
					GamecarPos[mGR.mPlayer.img][0] + GamecarPos[mGR.mPlayer.img][5] + mGR.mPlayer.p);
			DrawTransScal(gl, mGR.mTex_BoostBtn ,0.88f, -.50f,mGR.mSel==1?1.1f:1,mGR.mSel==1?0.5f:1);
		}
		if (mGR.mPlayer.Gunfill > 0) {
			if(mGR.mPlayer.bullatefire>0){
				DrawTexture(gl, mGR.mTex_gGunfire[Counter % 2], mGR.mPlayer.x + GamecarPos[mGR.mPlayer.img][10],
						GamecarPos[mGR.mPlayer.img][0] + GamecarPos[mGR.mPlayer.img][11] + mGR.mPlayer.p);
				}
			DrawTexture(gl, mGR.mTex_CarAcc[mGR.mPlayer.img][4], mGR.mPlayer.x + GamecarPos[mGR.mPlayer.img][8],
					GamecarPos[mGR.mPlayer.img][0] + GamecarPos[mGR.mPlayer.img][9] + mGR.mPlayer.p);
		}
		if (mGR.mPlayer.kitNo == 1) {
			DrawTexture(gl, mGR.mTex_CarAcc[mGR.mPlayer.img][5], mGR.mPlayer.x + GamecarPos[mGR.mPlayer.img][12],
					GamecarPos[mGR.mPlayer.img][0] + GamecarPos[mGR.mPlayer.img][13] + mGR.mPlayer.p);
		}
		if (mGR.mPlayer.kitNo == 2) {
			DrawTexture(gl, mGR.mTex_CarAcc[mGR.mPlayer.img][6], mGR.mPlayer.x + GamecarPos[mGR.mPlayer.img][12],
					GamecarPos[mGR.mPlayer.img][0] + GamecarPos[mGR.mPlayer.img][13] + mGR.mPlayer.p);
		}

	}
	void setTukde(float x, float y, int img,int blood) {
		int k = 0;
		if(M.mRand.nextInt(5) != 0){
			for (int i = 0; i < mGR.mTukde.length && k < mGR.mTex_ZTukde[img].length; i++) {
				if (mGR.mTukde[i].x < -1) {
					mGR.mTukde[i].set(x, y, img, k,mGR.mPlayer.Speed);
					k++;
				}
			}
		}else{
			for (int i = 0; i < mGR.mTukde.length && k < 1; i++) {
				if (mGR.mTukde[i].x < -1) {
					mGR.mTukde[i].set(x, y, img, 10,mGR.mPlayer.Speed);
					k++;
				}
			}
		}
		if (blood < 3) {
			for (int i = 0; i < mGR.mBlood.length; i++) {
				if (mGR.mBlood[i].x < -1) {
					mGR.mBlood[i].set(x, y, blood);
					k++;
				}
			}
		}
	}

	void setSmalBox(float x, float y) {
		int k = M.mRand.nextInt(mGR.mPlayer.citi);
		int m = 2;
		float dx = mGR.mTex_WBox[0].width();
		float dy = mGR.mTex_WBox[0].Height();
		for (int i = 0, j = 0; i < mGR.mSBox.length && j < m; i++) {
			if (mGR.mSBox[i].x < -1.5) {
				switch (k) {
				case 0:
					mGR.mSBox[i].set(x, y);
					m = 1;
					break;
				case 1:
					mGR.mSBox[i].set(x+j*dx, y);
					m = 2;
					break;
				case 2:
					mGR.mSBox[i].set(x, y+j*dy);
					m = 2;
					break;
				case 3:
					mGR.mSBox[i].set(x+j*dx/2f, y+(j%2)*dy);
					m = 3;
					break;
				case 4:
					mGR.mSBox[i].set(x+j*dx/2f, y+(j%2)*dy);
					m = 4;
					break;
				case 5:
					mGR.mSBox[i].set(x+j*dx/2f, y+(j%2)*dy);
					m = 6;
					break;
				case 6:
					mGR.mSBox[i].set(x+j*dx/2f, y+(j%2)*dy);
					m = 7;
					break;
				case 7:case 8:case 9:
					mGR.mSBox[i].set(x+j*dx*.6f, y+(j%2)*dy);
					m = 7;
					break;
				}
				j++;
			}
		}
	}
	void setZomie() {
		
		
		
		if (mGR.mPlayer.mDis < mGR.newZombie) {
			int k = 1;
			mGR.newZombie -= mGR.newZombieV;
			if (mGR.newZombieV > .6f) {
				mGR.newZombieV -= .1f;
			} else {
				switch (mGR.mPlayer.citi) {
				case 1:
					k = 2;
					break;
				case 2:
				case 3:
					k = 2;
					break;
				case 4:
				case 5:
				case 6:
					k = 3;
					break;
				default:
					k = 4;
					break;
				}

			}
			for (int i = 0, j = 0; j < k && i < mGR.mZombie.length; i++) {
				if (mGR.mZombie[i].x < -1.4f) {
					switch (mGR.mPlayer.citi) {
					case 1:
						mGR.mZombie[i]
								.set(1.3f + j * .07f, mGR.mZombie[i].y, 0);
						break;
					case 2:
						mGR.mZombie[i]
								.set(1.3f + j * .07f, mGR.mZombie[i].y, j);
						break;
					case 3:
						if (mGR.mPlayer.mBegin < 0)
							mGR.mZombie[i].set(1.3f + j * .07f,
									mGR.mZombie[i].y, j);
						else
							mGR.mZombie[i].set(1.3f + j * .07f,
									mGR.mZombie[i].y, 1);
						break;
					case 4:
						if (mGR.mPlayer.mBegin < 0)
							mGR.mZombie[i].set(1.3f + j * .07f,
									mGR.mZombie[i].y, j);
						else
							mGR.mZombie[i].set(1.3f + j * .07f,
									mGR.mZombie[i].y, j == 0 ? 1 : j);
						break;
					case 5:
						if (mGR.mPlayer.mBegin < -.3)
							mGR.mZombie[i].set(1.3f + j * .07f,
									mGR.mZombie[i].y, j);
						else
							mGR.mZombie[i].set(1.3f + j * .07f,
									mGR.mZombie[i].y, j % 2 + 1);
						break;
					case 6:
						if (mGR.mPlayer.mBegin < 0)
							mGR.mZombie[i].set(1.3f + j * .07f,
									mGR.mZombie[i].y, j % 2 + 1);
						else
							mGR.mZombie[i].set(1.3f + j * .07f,
									mGR.mZombie[i].y, 2);
						break;
					default:
						mGR.mZombie[i].set(1.3f + j * .07f,
								mGR.mZombie[i].y, j);
						break;
					}

					j++;
				}
			}
		}
	}
	
	void setBox(){
		float by = mGR.mBGBambo.y+.19f; 
		switch (mGR.setJoin) {
		case 0:
			for(int i =0;i<mGR.mBox.length;i++){
				mGR.mBox[i].set(mGR.mBGBambo.x-mGR.mTex_WBox[0].width()*1.5f+((i%3)*mGR.mTex_WBox[0].width()), by+mGR.mTex_WBox[0].Height()*.5f+((i/3)*mGR.mTex_WBox[0].Height()));
			}
			break;
		default:
			for(int i =0;i<mGR.mBox.length;i++){
				if(i<4)
					mGR.mBox[i].set(mGR.mBGBambo.x-mGR.mTex_WBox[0].width()*2f+((i%4)*mGR.mTex_WBox[0].width()), by+mGR.mTex_WBox[0].Height()*.5f+((0)*mGR.mTex_WBox[0].Height()));
				else if(i<7)
					mGR.mBox[i].set(mGR.mBGBambo.x-mGR.mTex_WBox[0].width()*1.5f+((i%4)*mGR.mTex_WBox[0].width()), by+mGR.mTex_WBox[0].Height()*.5f+((1)*mGR.mTex_WBox[0].Height()));
				else if(i<9)
					mGR.mBox[i].set(mGR.mBGBambo.x-mGR.mTex_WBox[0].width()*1+((i%7)*mGR.mTex_WBox[0].width()), by+mGR.mTex_WBox[0].Height()*.5f+((2)*mGR.mTex_WBox[0].Height()));
				else
					mGR.mBox[i].set(mGR.mBGBambo.x-mGR.mTex_WBox[0].width()*.5f+((0)*mGR.mTex_WBox[0].width()), by+mGR.mTex_WBox[0].Height()*.5f+((3)*mGR.mTex_WBox[0].Height()));
			}
			break;
		}
	}

	void gameWinOver() {
		if (mGR.mPlayer.overCounter > 30) {
			inc =0;
			mGR.Dollor+=mGR.Zombie*13;
			dInc= 0 ;
			vdInc = (int)((mGR.Zombie*13f)/20f);
			
			if (mGR.mPlayer.mBegin >= .5f){
				M.GameScreen = M.GAMEWIN;
				mGR.mPlayer.mBegin =.5f;
				if(mGR.mPlayer.citi >= 10){
					M.GameScreen = M.GAMEPRMO;
				}
				if(mGR.mPlayer.mBegin>=.5f && mGR.mPlayer.citi < 10){
					mGR.mPlayer.citi++;
					mGR.mLast = -.5f;
				}
			}
			else if (mGR.mPlayer.petrol < 1){
				M.GameScreen = M.GAMEOVER;
			}
			
			M.stop(GameRenderer.mContext);
			GameRenderer.mStart.ShowInterstitial();
		} else {
			if(mGR.mPlayer.Speed > -.001f)
			mGR.mPlayer.overCounter++;
			mGR.mPlayer.Run = mGR.mPlayer.BoostActive = false;
		}
		
	}
	
	void gameLogic() {
		if(mGR.mPlayer.mBegin>.5f)
			gameWinOver();
		if(mGR.mPlayer.petrol<1)
			gameWinOver();
		
		
		mGR.mPlayer.mBegin-=(mGR.mPlayer.Speed * .05f)/mGR.mPlayer.citi;
		mGR.mPlayer.update();
		for (int i = 0; i < 2; i++) {
			mGR.mBGTwo[i].update(mGR.mPlayer.Speed * .3f);
			mGR.mBGThree[i].update(mGR.mPlayer.Speed * .5f);
		}
		for (int i = 0; i < mGR.mBGFour.length; i++)
			mGR.mBGFour[i].update(mGR.mPlayer.Speed * 1f);
		mGR.mBGBambo.update(mGR.mPlayer.Speed * 1f);
		for (int i = 0; i < mGR.mBGFour.length; i++){
			if (mGR.mBGFour[i].x < -2.5) {
				if (i == 0 && mGR.mBGBambo.x < -2) {
					mGR.setJoin = M.mRand.nextInt(2);
//					mGR.setJoin =0;
					mGR.mBGBambo.x = mGR.mBGFour[i == 0 ? 2 : i-1].x + .95f
							+ mGR.mTex_Joint[mGR.mPlayer.citi%mGR.mTex_BG.length][mGR.setJoin].width() * .5f;
					setBox();
					mGR.mBGFour[i].x = mGR.mBGBambo.x + mGR.mTex_Joint[mGR.mPlayer.citi%mGR.mTex_BG.length][mGR.setJoin].width()
							* .5f + 1;
					
					mGR.bamboCont = 2;
				} else {
					mGR.mBGFour[i].x = mGR.mBGFour[i == 0 ? 2 : i - 1].x + 2;
				}
				if (i == 0 && mGR.bamboCont > 0) {
					mGR.bamboCont--;
				}
			}
		}
		
		for (int i = 0; i < 2; i++) {
			if (mGR.mBGTwo[i].x < -2)
				mGR.mBGTwo[i].x = mGR.mBGTwo[i == 0 ? 1 : 0].x + 2;
			if (mGR.mBGThree[i].x < -2)
				mGR.mBGThree[i].x = mGR.mBGThree[i == 0 ? 1 : 0].x + 2;
		}
		for (int i = 0; i < mGR.mBGOne.length; i++) {
			mGR.mBGOne[i].update(mGR.mPlayer.Speed * 0.1f);
		}
		for (int i = 0; i < mGR.mBGOne.length; i++) {
			if (mGR.mBGOne[i].x < -1.7)
					mGR.mBGOne[i].x = mGR.mBGOne[i == 0 ? 2 : i - 1].x + mGR.mTex_BG[1][0].width();
			
		}
		for (int i = 0; i < mGR.mBox.length; i++) {
			if (mGR.mBox[i].x > -1.5f){
				if(CircRectsOverlap(mGR.mPlayer.x, mGR.mPlayer.y, mGR.mTex_Car[mGR.mPlayer.img].width()*.4f, mGR.mTex_Car[mGR.mPlayer.img].Height()*.5f, 
						mGR.mBox[i].x, mGR.mBox[i].y, .1f)){
					mGR.Zombie++;
					mGR.mPlayer.Decrease(1);
					setTukde(mGR.mBox[i].x, mGR.mBox[i].y, 4,3);
					mGR.mBox[i].x =-2;
					M.Box(GameRenderer.mContext);
					for(int j=0;j<mGR.mBox.length;j++)
						mGR.mBox[j].down =true;
				}
				mGR.mBox[i].update(mGR.mPlayer.Speed * 1f);
				
			}
		}
		for (int i = 0; i < mGR.mSBox.length; i++) {
			if (mGR.mSBox[i].x > -1.5f){
				if(CircRectsOverlap(mGR.mPlayer.x, mGR.mPlayer.y, mGR.mTex_Car[mGR.mPlayer.img].width()*.4f, mGR.mTex_Car[mGR.mPlayer.img].Height()*.5f, 
						mGR.mSBox[i].x, mGR.mSBox[i].y, .1f)){
					mGR.Zombie++;
					mGR.mPlayer.Decrease(1);
					setTukde(mGR.mSBox[i].x, mGR.mSBox[i].y, 4,3);
					mGR.mSBox[i].x =-2;
				}
				mGR.mSBox[i].update(mGR.mPlayer.Speed * 1f);
			}
		}
		int findLow = -1;
		float val = 1.0f;
		for (int i = 0; i < mGR.mZombie.length; i++) {
			if (mGR.mZombie[i].x > -1.5f && mGR.mZombie[i].x < 1.5f){
				if(CircRectsOverlap(mGR.mPlayer.x, mGR.mPlayer.y, mGR.mTex_Car[mGR.mPlayer.img].width()*.3f, mGR.mTex_Car[mGR.mPlayer.img].Height()*.05f, 
						mGR.mZombie[i].x, mGR.mZombie[i].y, .1f)){
					setTukde(mGR.mZombie[i].x, mGR.mZombie[i].y, mGR.mZombie[i].no,mGR.mPlayer.kitNo ==0?0:1);
					mGR.mZombie[i].x =-2;
					mGR.Zombie+=mGR.mZombie[i].no+1;
					mGR.mPlayer.Decrease((mGR.mZombie[i].no+1)/(mGR.mPlayer.kitNo+1));
					M.Zombie(GameRenderer.mContext);
				}/*else if(mGR.mZombie[i].x > .5f && mGR.mPlayer.Gunfill>1){
					if (mGR.mZombie[i].x < val){
						findLow = i;
						val = mGR.mZombie[i].x;
					}
				}*/
				mGR.mZombie[i].update(mGR.mPlayer.Speed * 1f);
			}
		}
		
		if (Counter % 5 == 0) {
			for (int i = 0; i < mGR.mZombie.length && mGR.mPlayer.Gunfill > 1; i++) {
				if (mGR.mZombie[i].x > -1.5f && mGR.mZombie[i].x < 1.5f) {
					if (mGR.mZombie[i].x > .6f && mGR.mZombie[i].x < val) {
						findLow = i;
						val = mGR.mZombie[i].x;
					}
				}
			}
			if (findLow >= 0) {
				setTukde(mGR.mZombie[findLow].x, mGR.mZombie[findLow].y, mGR.mZombie[findLow].no, 2);
				mGR.mPlayer.Gunfill--;
				mGR.mPlayer.bullatefire = 3;
				mGR.mPlayer.petrol+=3;
				mGR.mZombie[findLow].x = -2;
				mGR.Zombie += mGR.mZombie[findLow].no + 1;
				M.sound8(GameRenderer.mContext, R.drawable.bullet);
			}
		}
		if(mGR.mPlayer.mDis < mGR.newSbox){
			setSmalBox(2, mGR.mBGBambo.y+.25f);
			mGR.newSbox -= (2 - mGR.mPlayer.citi*.1f);
		}
		setZomie();
		for(int i =0;i<mGR.mTukde.length;i++){
			if(mGR.mTukde[i].x > -1.1){
				mGR.mTukde[i].update(mGR.mPlayer.Speed * 1f);
			}
		}
		for (int i = 0; i < mGR.mBlood.length; i++) {
			if (mGR.mBlood[i].x > -1.1) {
				if (Counter % 2 == 0)
					mGR.mBlood[i].no++;
				if (mGR.mBlood[i].no >= mGR.mTex_Blood[mGR.mBlood[i].img].length)
					mGR.mBlood[i].x = -2;
			}
		}
		if (Counter % 5 == 0)
			CountSlow++;
		
		
	}
	//-.33f,-.46f,-.57f
	void Draw_Gameplay(GL10 gl) {
		
		int  bg = mGR.mPlayer.citi%mGR.mTex_BG.length;
		for(int i=0;i<3;i++) DrawTexture(gl, mGR.mTex_BG[bg==2?1:bg][0], mGR.mBGOne[i].x, mGR.mBGOne[i].y);
		for(int i=0;i<2;i++) DrawTexture(gl, mGR.mTex_BG[bg==2?1:bg][1], mGR.mBGTwo[i].x, mGR.mBGTwo[i].y);
		for(int i=0;i<2;i++) DrawTexture(gl, mGR.mTex_BG[bg][2], mGR.mBGThree[i].x, mGR.mBGThree[i].y-bg*.12f);
		for(int i=0;i<3;i++) DrawTexture(gl, mGR.mTex_BG[bg][3], mGR.mBGFour[i].x, mGR.mBGFour[i].y);
		
		DrawTexture(gl, mGR.mTex_Joint[mGR.mPlayer.citi%mGR.mTex_BG.length][mGR.setJoin], mGR.mBGBambo.x+.02f, mGR.mBGBambo.y);
		if(mGR.setJoin ==0)
			DrawTextureS(gl, mGR.mTex_Bamboo, mGR.mBGBambo.x+.02f, mGR.mBGBambo.y+.17f,1.2f,1);
		else
		{
			DrawTextureS(gl, mGR.mTex_Bamboo, mGR.mBGBambo.x-mGR.mTex_Bamboo.width()*.6f, mGR.mBGBambo.y+.17f,1.2f,1);
			DrawTextureS(gl, mGR.mTex_Bamboo, mGR.mBGBambo.x+mGR.mTex_Bamboo.width()*.6f, mGR.mBGBambo.y+.17f,1.2f,1);
		}
		
		for(int i =0;i<mGR.mBox.length;i++){
			DrawTexture(gl, mGR.mTex_WBox[i%2], mGR.mBox[i].x, mGR.mBox[i].y);
		}
		for(int i =0;i<mGR.mSBox.length;i++){
			DrawTexture(gl, mGR.mTex_WBox[i%2], mGR.mSBox[i].x, mGR.mSBox[i].y);
		}
		for(int i =0;i<mGR.mZombie.length;i++){
			if (mGR.mZombie[i].x > -1.5f && mGR.mZombie[i].x < 1.5f)
				DrawTexture(gl, mGR.mTex_Zombie[mGR.mZombie[i].no][CountSlow%mGR.mTex_Zombie[mGR.mZombie[i].no].length],
						mGR.mZombie[i].x, mGR.mZombie[i].y);
		}
		for(int i =0;i<mGR.mTukde.length;i++){
			if(mGR.mTukde[i].x > -1.1){
				if(mGR.mTukde[i].no >= mGR.mTex_ZTukde[mGR.mTukde[i].img].length){
					if(mGR.mTukde[i].img < 5)
						DrawFullRotate(gl, mGR.mTex_TZombie[mGR.mTukde[i].img], mGR.mTukde[i].x, mGR.mTukde[i].y,mGR.mTukde[i].vx==0?90:-Counter*10,1);
				}
				else
					DrawFullRotate(gl, mGR.mTex_ZTukde[mGR.mTukde[i].img][mGR.mTukde[i].no], mGR.mTukde[i].x, mGR.mTukde[i].y,mGR.mTukde[i].vx==0?0:-Counter*10,1);
			}
		}
		if(mGR.mPlayer.x < -.46f){
			mGR.mPlayer.x+=mGR.mPlayer.vx;
			if(mGR.mPlayer.vx >.01f)
				mGR.mPlayer.vx -= .001f;
		}
		DrawTexture(gl, mGR.mTex_Car[mGR.mPlayer.img], mGR.mPlayer.x, GamecarPos[mGR.mPlayer.img][0]+mGR.mPlayer.p);
		DrawFullRotate(gl, mGR.mTex_CarAcc[mGR.mPlayer.img][mGR.mPlayer.pahiye], mGR.mPlayer.x+GamecarPos[mGR.mPlayer.img][2], 
				GamecarPos[mGR.mPlayer.img][0]+GamecarPos[mGR.mPlayer.img][1]-mGR.mPlayer.p,mGR.mPlayer.Speed < -.001f?-Counter*30:0,1);
		DrawFullRotate(gl, mGR.mTex_CarAcc[mGR.mPlayer.img][mGR.mPlayer.pahiye], mGR.mPlayer.x+GamecarPos[mGR.mPlayer.img][3],
				GamecarPos[mGR.mPlayer.img][0]+GamecarPos[mGR.mPlayer.img][1]-mGR.mPlayer.p,mGR.mPlayer.Speed < -.001f?-Counter*30:0,1);
		Draw_equipment(gl);
		
		
		mGR.mFont.Draw_String(gl, ""+mGR.mPlayer.citi, 0.82f, .843f, 0, 0);
		
		DrawTexture(gl, mGR.mTex_Bullate,-.87f,.87f);
		mGR.mFont.Draw_String(gl, ""+((mGR.mPlayer.Gunfill>0?-1:0)+mGR.mPlayer.Gunfill), -.82f, .843f, 0, 0);
		
		for(int i=0;i<mGR.mBlood.length;i++){
			if(mGR.mBlood[i].x > -1.1 && mGR.mBlood[i].no < mGR.mTex_Blood[mGR.mBlood[i].img].length){
				DrawTexture(gl, mGR.mTex_Blood[mGR.mBlood[i].img][mGR.mBlood[i].no],mGR.mBlood[i].x,mGR.mBlood[i].y);
			}
		}
		
		DrawTransScal(gl, mGR.mTex_AcceBtn	,0.60f, -.80f,mGR.mSel==2?1.1f:1,mGR.mSel==2?0.5f:1);
		DrawTexture(gl, mGR.mTex_DisBar,0,.9f);
		if(mGR.mLast > -.49f)
			DrawTexture(gl, mGR.mTex_Pointer,mGR.mLast,.9f);
		DrawTexture(gl, mGR.mTex_Point,mGR.mPlayer.mBegin>.49f?.5f:mGR.mPlayer.mBegin,.9f);
		
		DrawTexture(gl, mGR.mTex_Meter,0,-.86f);
		mGR.mTex_MKata.drawfullRotet(gl, 0, -1, mGR.mPlayer.Speed*1000, -.4f, .5f);//170
		DrawTextureS(gl, mGR.mTex_item,-.005f,-1,.6f,.6f);
		
		mGR.mTex_MKata.drawfullRotet(gl, -.192f, -.9548f, 30-(mGR.mPlayer.petrol*.05f), -.4f, .3f); //30 to -195
		DrawTextureS(gl, mGR.mTex_item,-.192f, -.9548f,.6f,.6f);
		
		mGR.mTex_MKata.drawfullRotet(gl, .187f,-0.972f, -62f-(mGR.mPlayer.Boostfill*1.2f), -.4f, .3f);//-62 to -184
		DrawTextureS(gl, mGR.mTex_item,.187f,-0.972f,.6f,.6f);
		if(mGR.mPlayer.petrol <100 ){
			if(Counter %2==0)
				Counter2++;
			if(Counter2%2==0)
				DrawTexture(gl, mGR.mTex_indicator,-0.17f, -0.94f);
		}
		gameLogic();
	}
	float cx,cvx=1;
	boolean Handle_Gameplay(MotionEvent event){
		mGR.mSel = 0;
		if(mGR.mPlayer.x < -.46f)
			return true;
		if(CircRectsOverlap(0.88f, -.50f, mGR.mTex_BoostBtn.width()*.3f, mGR.mTex_BoostBtn.Height()*.3f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05)){
			mGR.mSel = 1;
			mGR.mPlayer.Run = true;
			if(mGR.mPlayer.Boostfill>1){
				mGR.mPlayer.BoostActive = true;
			}
		}
		else
			mGR.mPlayer.BoostActive = false;
		
		if(CircRectsOverlap(0.60f, -.80f, mGR.mTex_AcceBtn.width()*.5f, mGR.mTex_AcceBtn.Height()*.4f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05)){
			mGR.mSel = 2;
			mGR.mPlayer.Run = true;
		}
		if (MotionEvent.ACTION_UP == event.getAction()) {
			switch (mGR.mSel) {
			case 1://Boost
				break;
			case 2:
				break;
			}
			mGR.mSel = 0;
			mGR.mPlayer.BoostActive = false;
			mGR.mPlayer.Run = false;
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
	void DrawTextureS(GL10 gl,SimplePlane Tex,float x,float y,float sx,float sy)
	{
		Tex.drawScal(gl, x, y,sx,sy);
	}
	void DrawFullRotate(GL10 gl, SimplePlane Tex, float x, float y, float r,float s) {
		Tex.drawfullRotet(gl, x, y,r,0,s);
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
	float screen2worldX(float a){
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
	void facebook()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://www.facebook.com/HututuGames"));
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
