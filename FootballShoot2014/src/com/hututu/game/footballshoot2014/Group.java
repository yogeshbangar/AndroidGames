package com.hututu.game.footballshoot2014;
import javax.microedition.khronos.opengles.GL10;


import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.widget.Toast;
public class Group extends Mesh 
{
	GameRenderer mGR = null;
	float sx,sy;
	float dx,dy;
	int Counter =0;
	public Group(GameRenderer _GameRenderer) {
		mGR = _GameRenderer;
	}
	public void setting(){float ud=.01f;switch (GameRenderer.mStart._keyCode) {case 1:sy-=ud;break;case 2:sy+=ud;break;case 3:sx-=ud;break;case 4:sx+=ud;break;}System.out.println(M.GameScreen+"~~~~~~~~~~~~~~~      "+sx+"~~~~~~~~~~~~       "+sy);}
	public boolean Handle(MotionEvent event){
		if(CircRectsOverlap(-.8f,0.0f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 3;
		if(CircRectsOverlap(0.8f,0.0f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 4;
		if(CircRectsOverlap(-.0f,-.8f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 1;
		if(CircRectsOverlap(0.0f,0.8f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 2;
		if(event.getAction()== MotionEvent.ACTION_UP)GameRenderer.mStart._keyCode = 0;
		return true;
	}

	@Override
	public void draw(GL10 gl) {
//		M.GameScreen = M.GAMEEXIT;
		Counter++;
		switch (M.GameScreen) {
		case M.GAMELOGO:
			DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if (Counter > 50) {
				// M.GameScreen = M.GAMEMENU;
				M.GameScreen = M.GAMEADD;// AdHouse
				Counter = 0;// AdHouse
			}
			break;
		/* AdHouse */
		case M.GAMEADD:
			if (Counter > 100)
				DrawTexture(gl, mGR.mTex_Skip, .9f, -.9f);
			else {
				DrawTexture(gl, mGR.mTex_Hightbar, 0.2f, -.9f);
				DrawTexture(gl, mGR.mTex_Pointer, .2f + Counter * (mGR.mTex_Hightbar.width() / 100f) - .6f, -.9f);
			}
			break;
		/* AdHouse */
		case M.GAMEMENU:case M.GAMEEXIT:
			Draw_Menu(gl);
			break;
		case M.GAMEJOIN:
		case M.GAMEBG:
			Draw_BG(gl);
			break;
		case M.GAMEABOUT:
			Draw_About(gl);
			break;
		case M.GAMEHELP:
			break;
		case M.GAMEOVER:
			Draw_Gameover(gl);
			break;
		case M.GAMEPAUSE:
			Draw_Pause(gl);
			break;
		case M.GAMEPLAY:
			DrawGamePlay(gl);
			break;
		}
//		 setting();
	}
	public boolean TouchEvent(MotionEvent event) 
	{	
		switch (M.GameScreen) {
		case M.GAMELOGO:
			break;
			/*AdHouse*/
		case M.GAMEADD:
			if((MotionEvent.ACTION_UP == event.getAction())&&(Counter>100)&&
					CirCir(0.9f,-.9f, .11f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				M.GameScreen = M.GAMEMENU;
				M.sound5(GameRenderer.mContext, R.drawable.click);
			}
			break;
			/*AdHouse*/
		case M.GAMEMENU:
			Handle_Menu(event);
			break;
		case M.GAMEBG:
			Handle_BG(event);
			break;
		case M.GAMEABOUT:
			Handle_About(event);
			break;
		case M.GAMEHELP:
			break;
		case M.GAMEOVER:
			Handle_Gameover(event);
			break;
		case M.GAMEPAUSE:
			Handle_Pause(event);
			break;
		case M.GAMEPLAY:
			HandleGame(event);
			break;
		case M.GAMEJOIN:
			Handle_Join(event);
			break;
		case M.GAMEEXIT:
			Handle_Exit(event);
			break;
		}
		Handle(event);
		return true;
	}
	
	void Draw_Exit(GL10 gl)
	{
		mGR.mTex_Menu.drawFilp(gl, 0, 0,.7f);
		DrawTexture(gl, mGR.mTex_Popup, 0, 0);
//		DrawTexture(gl, mGR.mTex_Like, 0, .35f);
		DrawTranScal(gl,mGR.mTex_Like,0.00f,0.35f, (mGR.mSel == 4)?1.1f:1,(mGR.mSel == 4)?.8f:1);
		
		DrawTexture(gl, mGR.mTex_ExitAds, 0, .02f);
		DrawTexture(gl, mGR.mTex_BoardF, 0, -.32f);
		DrawTexture(gl, mGR.mTex_Quit, 0, -.28f);
		
		DrawTranScal(gl,mGR.mTex_Yes,-.35f,-.35f, (mGR.mSel == 1)?1.1f:1,(mGR.mSel == 1)?.8f:1);
		DrawTranScal(gl,mGR.mTex_No	,0.35f,-.35f, (mGR.mSel == 2)?1.1f:1,(mGR.mSel == 2)?.8f:1);
		
	}
	
	boolean Handle_Exit(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(-.35f,-.35f, mGR.mTex_SButton.width()*.4, mGR.mTex_SButton.Height()*.3f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 1;//Yes
		}
		if(CircRectsOverlap(0.35f,-.35f, mGR.mTex_SButton.width()*.4, mGR.mTex_SButton.Height()*.3f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 2;//No
		}
		if(CircRectsOverlap( 0, .02f, mGR.mTex_ExitAds.width()*.3f, mGR.mTex_ExitAds.Height()*.2f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 3;//Download
		}
		if(CircRectsOverlap( 0, .35f, mGR.mTex_Like.width()*.4f, mGR.mTex_Like.Height()*.4f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 4;//Ratus
		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			switch (mGR.mSel) {
			case 1:
				GameRenderer.mStart.finish();
				Counter = 0;
				M.GameScreen = M.GAMELOGO;
				break;
			case 2:
				M.GameScreen = M.GAMEMENU;
				break;
			case 3: 
				Intent mIntent = new Intent(Intent.ACTION_VIEW);
				mIntent.setData(Uri.parse(M.LINK + "com.hututu.game.bubblecandyrescue"));
				GameRenderer.mContext.startActivity(mIntent);
				break;
			case 4:
				RateUs();
				break;
			}
			if (mGR.mSel != 0)
				M.sound5(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	
	
	void Draw_Join(GL10 gl)
	{
		mGR.mTex_Menu.drawFilp(gl, 0, 0,.7f);
		DrawTexture(gl, mGR.mTex_Popup, 0, 0);
		DrawTexture(gl, mGR.mTex_Join, 0, .35f);
		DrawTexture(gl, mGR.mTex_BoardF, 0, -.32f);
		DrawTexture(gl, mGR.mTex_JoinTex, 0, .15f);
		
		
		DrawTranScal(gl,mGR.mTex_FSButton,-.405f,-.10f, (mGR.mSel == 1)?1.1f:1,(mGR.mSel == 1)?.8f:1);
		DrawTexture(gl, mGR.mTex_JoinF	,-.510f,-.12f);
		DrawTexture(gl, mGR.mTex_RightI	,-.282f,-.11f);
		
		DrawTranScal(gl,mGR.mTex_SButton,0.400f,-.10f, (mGR.mSel == 2)?1.1f:1,(mGR.mSel == 2)?.8f:1);
		DrawTexture(gl, mGR.mTex_Later	,0.510f,-.12f);
		DrawTexture(gl, mGR.mTex_ExitI	,0.28f,-.11f);
		
		DrawTranScal(gl,mGR.mTex_Buy,0.000f,-.32f, (mGR.mSel == 3)?1.1f:1,(mGR.mSel == 3)?.8f:1);
	}
	
	boolean Handle_Join(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(-.405f,-.10f, mGR.mTex_SButton.width()*.4, mGR.mTex_SButton.Height()*.3f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 1;//Join
		}
		if(CircRectsOverlap(0.405f,-.10f, mGR.mTex_SButton.width()*.4, mGR.mTex_SButton.Height()*.3f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 2;//Later
		}
		if(CircRectsOverlap( 0.000f,-.32f, mGR.mTex_Buy.width()*.4f, mGR.mTex_Buy.Height()*.3f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 3;//buy
		}
		
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			switch (mGR.mSel) {
			case 1:
				M.GameScreen = M.GAMEBG;
				GameRenderer.mStart.beginUserInitiatedSignIn();
				break;
			case 2:
				M.GameScreen = M.GAMEBG;
				break;
			case 3:
				mGR.mMainActivity.onBuyGold50000(null);
//				M.GameScreen = M.GAMEBG;
				break;
			}
			if (mGR.mSel != 0)
				M.sound5(GameRenderer.mContext, R.drawable.click);
			mGR.mSel =0;
		}
		return true;
	}
	
	
	
	float setting = -1.18f;
	boolean settingShow = false;
	void Draw_Menu(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_Menu, 0, 0);
		DrawTexture(gl, mGR.mTex_STxt, 0, .57f);
		
		for(int i=0;i<3;i++)
		{
			DrawTranScal(gl, mGR.mTex_Button	, .40f,	-.200f-(i*.23f),(mGR.mSel == i+1)?1.05f:1,(mGR.mSel == i+1)?.8f:1);
			DrawTexture(gl, mGR.mTex_Level[i]	, .56f, -.215f-(i*.23f));
		}
		for(int i=0;i<5;i++){
			DrawTranScal(gl,mGR.mTex_ICN[6], -.80f, setting-.21f*i, (mGR.mSel == i+4)?1.1f:1,(mGR.mSel == i+4)?.8f:1);
			DrawTranScal(gl,mGR.mTex_ICN[i], -.80f, setting-.21f*i, (mGR.mSel == i+4)?1.1f:1,(mGR.mSel == i+4)?.8f:1);
		}
		DrawTranScal(gl,mGR.mTex_ICN[5], -.80f, -.88f, (mGR.mSel == 10)?1.1f:1,(mGR.mSel == 10)?.8f:1);
		
		DrawTranScal(gl,mGR.mTex_ICN[6], .81f, .30f, (mGR.mSel == 11)?1.1f:1,(mGR.mSel == 11)?.8f:1);
		DrawTranScal(gl,mGR.mTex_ICN[7], .81f, .30f, (mGR.mSel == 11)?1.1f:1,(mGR.mSel == 11)?.8f:1);
		
		
		DrawTranScal(gl, mGR.mTex_SButton	, .67f,-.91f,(mGR.mSel == 12)?1.1f:1,(mGR.mSel == 12)?.8f:1);
		DrawTexture(gl, mGR.mTex_Exit	, .76f,-.92f);
		DrawTexture(gl, mGR.mTex_ExitI	,.55f,-.92f);
		if(!settingShow && setting >-1.2)
		{
			setting-=.07f;
		}
		if(settingShow && setting <.18)
		{
			setting+=.07f;
		}
		if(M.GameScreen == M.GAMEEXIT)
			Draw_Exit(gl);
	}
	
	
	boolean Handle_Menu(MotionEvent event)
	{
		mGR.mSel = 0;
		for(int i=0;i<3;i++)
		{
			if(CircRectsOverlap(.40f,	-.200f-(i*.23f), mGR.mTex_Button.width()*.4f, mGR.mTex_Button.Height()*.4f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				mGR.mSel = i+1;
			}
		}
		for (int i = 0; i < 5; i++) {
			if (CircRectsOverlap(-.80f, setting - .21f * i, mGR.mTex_ICN[i].width() * .4f, mGR.mTex_ICN[i].Height() * .4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)) {
				mGR.mSel = i + 4;
			}
		}

		if(CircRectsOverlap( -.80f, -.88f, mGR.mTex_ICN[5].width()*.4f, mGR.mTex_ICN[5].Height()*.4f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 10;
		}
		if(CircRectsOverlap( .81f, .30f, mGR.mTex_ICN[7].width()*.4f, mGR.mTex_ICN[7].Height()*.4f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 11;
		}
		if(CircRectsOverlap(.67f,-.91f, mGR.mTex_SButton.width()*.4f, mGR.mTex_SButton.Height()*.4f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 12;
		}
		if (event.getAction() == MotionEvent.ACTION_UP) {

			switch (mGR.mSel) {
			case 1:
			case 2:
			case 3:
				if (!GameRenderer.mStart.isSignedIn())
					M.GameScreen = M.GAMEJOIN;
				else
					M.GameScreen = M.GAMEBG;
				mGR.mLevel = mGR.mSel - 1;
				break;
			case 4:
				google();
				break;
			case 5:
				facebook();
				break;
			case 6:
				share2friend();
				break;
			case 7:
				GameRenderer.mStart.onShowLeaderboardsRequested();
				// Leader Board
				break;
			case 8:
				GameRenderer.mStart.onShowAchievementsRequested();
				// Achievement
				break;
			case 10:
				settingShow = !settingShow;
				break;
			case 11:
				M.GameScreen = M.GAMEABOUT;
				break;
			case 12:
				GameRenderer.mStart.Exit();
				break;
			}
			if (mGR.mSel != 0)
				M.sound5(GameRenderer.mContext, R.drawable.click);
			mGR.isFromMenu = true;
			mGR.mSel = 0;
		}

		return true;
	}
	
	void Draw_BG(GL10 gl)
	{
		int bg =0;
		DrawTexture(gl, mGR.mTex_Menu, 0, 0);
		bg =5;
		for(int i=0;i<bg;i++)
		{
			DrawTranScal(gl, mGR.mTex_Button	, .40f, .40f-(i*.23f),(mGR.mSel == i+1)?1.1f:1,(mGR.mSel == i+1)?.5f:1);
			DrawTranScal(gl, mGR.mTex_Stadium	, .55f, .39f-(i*.23f),(mGR.mSel == i+1)?1.1f:1,(mGR.mSel == i+1)?.5f:1);
			drawNumber(gl, i+1+""				, .18f, .38f-(i*.23f));
		}
		DrawTranScal(gl, mGR.mTex_SButton	, .67f,-.91f,(mGR.mSel == 12)?1.1f:1,(mGR.mSel == 12)?.8f:1);
		DrawTexture(gl, mGR.mTex_Back, .76f,-.92f);
		DrawTexture(gl, mGR.mTex_ExitI	,.55f,-.92f);
		if(M.GameScreen == M.GAMEJOIN)
			Draw_Join(gl);
	}
	
	
	boolean Handle_BG(MotionEvent event)
	{
		mGR.mSel = 0;
		int bg=5;
		for(int i=0;i<bg;i++)
		{
			if(CircRectsOverlap(.40f, .40f-(i*.23f), mGR.mTex_Button.width()*.4f, mGR.mTex_Button.Height()*.4f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				mGR.mSel = i+1;
			}
		}
		if(CircRectsOverlap(.67f,-.91f, mGR.mTex_Button.width()*.4f, mGR.mTex_Button.Height()*.4f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 12;
		}
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			switch (mGR.mSel) {
			case 1:case 2:case 3:case 4:case 5:
				M.GameScreen = M.GAMEPLAY;
				mGR.mBG = mGR.mSel-1;
				mGR.gameReset();
				break;
			case 12:
				M.GameScreen = M.GAMEMENU;
				break;
			}
			if (mGR.mSel != 0)
				M.sound5(GameRenderer.mContext, R.drawable.click);
			mGR.isFromMenu = false;
			mGR.mSel =0;
		}
		
		return true;
	}
	
	void Draw_About(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_Menu, 0, 0);
		DrawTexture(gl, mGR.mTex_Popup, 0, 0);
		DrawTexture(gl, mGR.mTex_About, 0, .35f);
		DrawTexture(gl, mGR.mTex_AboutText, 0, -.05f);
		
		DrawTranScal(gl, mGR.mTex_SButton	, .67f,-.51f,(mGR.mSel == 12)?1.1f:1,(mGR.mSel == 12)?.8f:1);
		DrawTexture(gl, mGR.mTex_Back	, .76f,-.52f);
		DrawTexture(gl, mGR.mTex_ExitI	,.55f,-.52f);
	}
	
	boolean Handle_About(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(.67f,-.51f, mGR.mTex_SButton.width()*.4f, mGR.mTex_SButton.Height()*.4f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 12;
		}
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			switch (mGR.mSel) {
			case 12:
				M.GameScreen = M.GAMEMENU;
				break;
			}
			if (mGR.mSel != 0)
				M.sound5(GameRenderer.mContext, R.drawable.click);
			mGR.mSel =0;
		}
		return true;
	}
	
	
	void Draw_Gameover(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_Menu, 0, 0);
		DrawTexture(gl, mGR.mTex_Popup, 0, 0);
		DrawTexture(gl, mGR.mTex_GOver, 0, .35f);
		
		
		DrawTexture(gl, mGR.mTex_Score, -.500f,.20f);
		DrawTexture(gl, mGR.mTex_FSButton	,-.405f,.12f);
		drawNumber(gl, mGR.mScore+"",-.62f, .11f);
		
		DrawTexture(gl, mGR.mTex_BSCore, 0.405f,.20f);		
		DrawTexture(gl, mGR.mTex_SButton	,0.400f,.12f);
		drawNumber(gl, mGR.mLScore[mGR.mLevel%3]+"", .40f, .11f);
		 
		{
			DrawTranScal(gl, mGR.mTex_ICN[0], -.38f+.38f*0,-.07f,(mGR.mSel == 1)?1.1f:1,(mGR.mSel == 1)?.8f:1);
			DrawTranScal(gl, mGR.mTex_ICN[1], -.38f+.38f*1,-.07f,(mGR.mSel == 2)?1.1f:1,(mGR.mSel == 2)?.8f:1);
			DrawTranScal(gl, mGR.mTex_ICN[8], -.38f+.38f*2,-.07f,(mGR.mSel == 3)?1.1f:1,(mGR.mSel == 3)?.8f:1);
		}
		for (int i = 0; i < 4; i++) {
			DrawTranScal(gl, mGR.mTex_ICN[6], -.57f+.38f*i, -.29f,(mGR.mSel == i+4)?1.1f:1,(mGR.mSel == i+4)?.8f:1);
		}
		DrawTranScal(gl, mGR.mTex_MenuIcn,-.57f+.38f*0, -.29f,(mGR.mSel == 4)?1.1f:1,(mGR.mSel == 4)?.8f:1);
		DrawTranScal(gl, mGR.mTex_Retry	, -.57f+.38f*1, -.29f,(mGR.mSel == 5)?1.1f:1,(mGR.mSel == 5)?.8f:1);
		DrawTranScal(gl, mGR.mTex_ICN[3], -.57f+.38f*2, -.29f,(mGR.mSel == 6)?1.1f:1,(mGR.mSel == 6)?.8f:1);
		DrawTranScal(gl, mGR.mTex_ICN[4], -.57f+.38f*3, -.29f,(mGR.mSel == 7)?1.1f:1,(mGR.mSel == 7)?.8f:1);
		
	}
	boolean Handle_Gameover(MotionEvent event)
	{
		mGR.mSel = 0;
		
		
		for (int i = 0; i < 3; i++) {
			if(CircRectsOverlap(-.38f+.38f*i,-.07f, mGR.mTex_ICN[6].width()*.4f, mGR.mTex_ICN[6].Height()*.4f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
				mGR.mSel = 1+i;
		}
		for (int i = 0; i < 4; i++) {
			if(CircRectsOverlap(-.57f+.38f*i, -.29f, mGR.mTex_ICN[6].width()*.4f, mGR.mTex_ICN[6].Height()*.4f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
				mGR.mSel = 4+i;
		}
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			switch (mGR.mSel) {
			case 1:
				google();
				break;
			case 2:
				facebook();
				break;
			case 3:
				twitter();
				break;
			case 4:
				M.GameScreen = M.GAMEMENU;
				break;
			case 5:
				mGR.gameReset();
				M.GameScreen = M.GAMEPLAY;
				break;
			case 6:
				GameRenderer.mStart.onShowLeaderboardsRequested();
				//Leader Board
				break;
			case 7:
				GameRenderer.mStart.onShowAchievementsRequested();
				//Achievement
				break;
			
			}
			if (mGR.mSel != 0)
				M.sound5(GameRenderer.mContext, R.drawable.click);
			mGR.mSel =0;
		}
		return true;
	}
	
	void Draw_Pause(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_Menu, 0, 0);
		DrawTexture(gl, mGR.mTex_Popup, 0, 0);
		DrawTexture(gl, mGR.mTex_PText, 0, .35f);
		
		DrawTranScal(gl, mGR.mTex_ICN[6], 0, .17f,(mGR.mSel == 1)?1.1f:1,(mGR.mSel == 1)?.8f:1);
		DrawTranScal(gl, mGR.mTex_Resume, 0, .17f,(mGR.mSel == 1)?1.1f:1,(mGR.mSel == 1)?.8f:1);
		DrawTranScal(gl, mGR.mTex_ICN[6], 0, -.08f,(mGR.mSel == 2)?1.1f:1,(mGR.mSel == 2)?.8f:1);
		DrawTranScal(gl, mGR.mTex_Retry, 0, -.08f,(mGR.mSel == 2)?1.1f:1,(mGR.mSel == 2)?.8f:1);
		DrawTranScal(gl, mGR.mTex_ICN[6], -.32f, -.28f,(mGR.mSel == 3)?1.1f:1,(mGR.mSel == 3)?.8f:1);
		DrawTranScal(gl, mGR.mTex_MenuIcn, -.32f, -.28f,(mGR.mSel == 3)?1.1f:1,(mGR.mSel == 3)?.8f:1);
		DrawTranScal(gl, mGR.mTex_ICN[6], .32f, -.28f,(mGR.mSel == 4)?1.1f:1,(mGR.mSel == 4)?.8f:1);
		DrawTranScal(gl, mGR.mTex_Sound[M.setValue ? 1 : 0], .32f, -.28f,(mGR.mSel == 4)?1.1f:1,(mGR.mSel == 4)?.8f:1);
		DrawTranScal(gl, mGR.mTex_BButton, .24f, -.58f,(mGR.mSel == 5)?1.01f:1,(mGR.mSel == 5)?.8f:1);
		DrawTranScal(gl, mGR.mTex_More, .38f, -.59f,(mGR.mSel == 5)?1.1f:1,(mGR.mSel == 5)?.8f:1);
	}
	
	boolean Handle_Pause(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap( 0, .17f, mGR.mTex_ICN[6].width()*.99f, mGR.mTex_ICN[6].Height()*.3f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 1;//Resume
		}
		if(CircRectsOverlap(0, -.08f, mGR.mTex_ICN[6].width()*.99f, mGR.mTex_ICN[6].Height()*.3f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 2;//retry
		}
		if(CircRectsOverlap( -.32f, -.28f, mGR.mTex_ICN[6].width()*.99f, mGR.mTex_ICN[6].Height()*.3f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 3;//Menu
		}
		if(CircRectsOverlap(  0.32f, -.28f, mGR.mTex_ICN[6].width()*.4f, mGR.mTex_ICN[6].Height()*.4f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 4;//Sound
		}
		if(CircRectsOverlap(.24f, -.58f, mGR.mTex_BButton.width()*.4f, mGR.mTex_BButton.Height()*.3f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 5;//More
		}
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			switch (mGR.mSel) {
			case 1:
				M.GameScreen = M.GAMEPLAY;
				break;
			case 2:
				M.GameScreen = M.GAMEPLAY;
				mGR.gameReset();
				break;
			case 3:
				M.GameScreen = M.GAMEMENU;
				break;
			case 4:
				M.setValue = !M.setValue;
				break;
			case 5:
				MoreGame();
				break;
			
			}
			if (mGR.mSel != 0)
				M.sound5(GameRenderer.mContext, R.drawable.click);
			mGR.mSel =0;
		}
		return true;
	}
	
	
	
	void Gamelogic() {
		mGR.mStone.update();
//		if(mGR.mStone.y > -1)
			setAnimation(mGR.mStone.x, mGR.mStone.y);
		if (CirCir(-mGR.mPos[mGR.mLevel].x, mGR.mPos[mGR.mLevel].y, .02, mGR.mStone.x, mGR.mStone.y, .02f) && mGR.mStone.vy < 0) {
			if (mGR.mStone.vy < 0) {
//				if (mGR.mBG == 3)
//					M.sound3(GameRenderer.mContext, R.drawable.steel);
//				else
					M.sound2(GameRenderer.mContext, R.drawable.ground_bounce);
			}
			mGR.mStone.vy = Math.abs(mGR.mStone.vy * .5f);
			mGR.mStone.tuch = true;
			if (mGR.mStone.x > -mGR.mPos[mGR.mLevel].x)
				mGR.mStone.vx = Math.abs(mGR.mStone.vx) * 0.5f;
			else
				mGR.mStone.vx = -Math.abs(mGR.mStone.vx) * 0.5f;

			if (Math.abs(mGR.mStone.vx) < .001)
				mGR.mStone.vx = .002f;
		}
		if (CirCir(mGR.mPos[mGR.mLevel].x, mGR.mPos[mGR.mLevel].y, .02, mGR.mStone.x, mGR.mStone.y, .02f) && mGR.mStone.vy < 0) {
			if (mGR.mStone.vy < 0) {
//				if (mGR.mBG == 3)
//					M.sound3(GameRenderer.mContext, R.drawable.steel);
//				else
//					M.sound2(GameRenderer.mContext, R.drawable.plastic);
				M.sound2(GameRenderer.mContext, R.drawable.ground_bounce);
			}
			mGR.mStone.vy = Math.abs(mGR.mStone.vy * .5f);
			mGR.mStone.tuch = true;
			if (mGR.mStone.x > mGR.mPos[mGR.mLevel].x)
				mGR.mStone.vx = Math.abs(mGR.mStone.vx) * 0.5f;
			else
				mGR.mStone.vx = -Math.abs(mGR.mStone.vx) * 0.5f;
			if (Math.abs(mGR.mStone.vx) < .001)
				mGR.mStone.vx = Math.abs(mGR.mStone.vx) * 2.5f;
		}
		if (CircRectsOverlap(0.0f, mGR.mPos[mGR.mLevel].y, .2f, 0.03f, mGR.mStone.x, mGR.mStone.y, .03f) && mGR.mStone.vy < 0) {
			if (!mGR.mStone.inside) {
				mGR.mScore++;
				mGR.mStone.reset++;
				mGR.mStone.conti++;
				if (mGR.mStone.reset > 2) {
					mGR.mStone.no++;
					mGR.mStone.bani = 10;
					mGR.mStone.reset =0;
				}
				M.sound1(GameRenderer.mContext, R.drawable.goal);
				// mGR.mStone.bani=10;
//				if (mGR.mBG == 3)
//					M.sound3(GameRenderer.mContext, R.drawable.steel);
//				else
//					M.sound2(GameRenderer.mContext, R.drawable.plastic);
				ChackAchiv();
			}
			mGR.mStone.inside = true;
		}
		if (mGR.mStone.inside) {
			if (mGR.mStone.x > 0.05) {
				if (mGR.mStone.x > .02f)
					mGR.mStone.x = .02f;
				mGR.mStone.vx = 0;
			}
			if (mGR.mStone.x < -0.05) {
				if (mGR.mStone.x < -0.02)
					mGR.mStone.x = -.02f;
				mGR.mStone.vx = 0;
			}
		}
		if (mGR.mStone.inside && mGR.mLevel == 2) {
			if (mGR.mStone.x > 0.02) {
				mGR.mStone.x = .0f;
				mGR.mStone.vx = 0;
			}
			if (mGR.mStone.x < -0.02) {
				mGR.mStone.x = 0.0f;
				mGR.mStone.vx = 0;
			}
		}
		if (mGR.mStone.counter == 9 && !mGR.mStone.inside) {
			M.sound2(GameRenderer.mContext, R.drawable.ground_bounce);
			mGR.mStone.no --;
			mGR.mStone.reset = 0;
			mGR.mStone.conti = 0;
		}
		if (mGR.mScore > mGR.mLScore[mGR.mLevel%3])
			mGR.mLScore[mGR.mLevel%3] = mGR.mScore;
		
		
		if(mGR.mStone.no<=0){
			M.GameScreen = M.GAMEOVER;
			if (mGR.ads % 1 == 0) {
				try {
					GameRenderer.mStart.show();
				} catch (Exception e) {
				}
			}
			GameRenderer.mStart.Submitscore(scoreID[mGR.mLevel], mGR.mLScore[mGR.mLevel]);
			M.sound3(GameRenderer.mContext, R.drawable.game_over);
		}
	}
	int scoreID[] = {
		R.string.leaderboard_easy_score,
		R.string.leaderboard_medium_score,
		R.string.leaderboard_hard_score,
	};
	void ChackAchiv()
	{
		if(mGR.mStone.conti>5 && !mGR.Achi[0])
		{
			GameRenderer.mStart.UnlockAchievement(M.achiment[0]);
			mGR.Achi[0] = true;
		}
		if(mGR.mStone.conti > 9 && !mGR.Achi[1])
		{
			GameRenderer.mStart.UnlockAchievement(M.achiment[1]);
			mGR.Achi[1] = true;
		}
		if(mGR.mStone.conti > 19 && !mGR.Achi[2])
		{
			GameRenderer.mStart.UnlockAchievement(M.achiment[2]);
			mGR.Achi[2] = true;
		}
		if(mGR.mStone.total>250 && !mGR.Achi[3])
		{
			GameRenderer.mStart.UnlockAchievement(M.achiment[3]);
			mGR.Achi[3] = true;
		}
		if(mGR.mStone.total>1000 && !mGR.Achi[4])
		{
			GameRenderer.mStart.UnlockAchievement(M.achiment[4]);
			mGR.Achi[4] = true;
		}
	}
	void setAnimation(float x, float y) {
		for (int i = 0; i < mGR.mAni.length; i++) {
			if (mGR.mAni[i].no <= 0) {
				mGR.mAni[i].set(x, y);
				break;
			}
		}

	}
	void DrawGamePlay(GL10 gl) {
		DrawTexture(gl, mGR.mTex_BackGround[mGR.mBG], 0, 0.00f);
		DrawTexture(gl, mGR.mTex_FBulti[mGR.mBalti][mGR.mLevel], 0, mGR.mPos[mGR.mLevel].c);
		if (mGR.mStone.vy <= 0) {
			DrawAnim(gl);
			if(mGR.mStone.counter <1)
				DrawTextureS(gl, mGR.mTex_Ball[Counter%mGR.mTex_Ball.length], mGR.mStone.x, mGR.mStone.y,mGR.mStone.z);
			else
				DrawTextureS(gl, mGR.mTex_Ball[0], mGR.mStone.x, mGR.mStone.y,mGR.mStone.z);
		}
		DrawTexture(gl, mGR.mTex_BBulti[mGR.mBalti][mGR.mLevel], 0, mGR.mPos[mGR.mLevel].c);
		
		if(mGR.mStone.vy >0){
			DrawAnim(gl);
			DrawTextureS(gl, mGR.mTex_Ball[Counter%mGR.mTex_Ball.length], mGR.mStone.x, mGR.mStone.y,mGR.mStone.z);
		}
		
		if(mGR.mStone.y < -99)
			DrawTexture(gl, mGR.mTex_Ball[0], 0, -.9f);
		if(M.mAir > 0)
			DrawTexture	(gl, mGR.mTex_Fan[Counter%mGR.mTex_Fan.length],-.88f,-.7f);
		else
			DrawTexture	(gl, mGR.mTex_FanF[Counter%mGR.mTex_Fan.length],0.88f,-.7f);
		
		
		DrawTexture(gl, mGR.mTex_SBoard, 0, .96f);
		drawNumber(gl, "" + mGR.mScore		, -.65f, .964f,.7f);
		drawNumber(gl, "" + mGR.mStone.no	, -.07f, .964f,.7f);
		drawNumber(gl, "" + mGR.mLScore[mGR.mLevel%3], 0.75f, .964f,.7f);
		
		drawNumber(gl, ""+(M.mAir)		,-.1f, -.50f);
		if(M.mAir<0)
			DrawTexture(gl, mGR.mTex_Arrow	,0.0f, -.55f);
		else
			DrawTextureR(gl, mGR.mTex_Arrow	,0.0f, -.55f,180);
		
		
		if (mGR.mStone.bani > 0) {
			DrawTextureS(gl, mGR.mTex_Blast[0], -.09f, .959f, mGR.mStone.bani * .1f);
			DrawTextureS(gl, mGR.mTex_Blast[1], -.09f, .959f, .4f + (10 - mGR.mStone.bani) * .1f);
			DrawTextureS(gl, mGR.mTex_Blast[2], -.09f, .959f, .4f + (10 - mGR.mStone.bani) * .1f);
			mGR.mStone.bani--;
		}
		
		Gamelogic();
	}
	
	void DrawAnim(GL10 gl){
		for (int i = 0; i < mGR.mAni.length; i++) {
			if (mGR.mAni[i].no > 0) {
				DrawTranScal(gl,mGR.mTex_Tel	, mGR.mAni[i].x, mGR.mAni[i].y,mGR.mAni[i].no*.1f,mGR.mAni[i].no*.1f);
				mGR.mAni[i].no--;
			}
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
			if (mGR.mStone.y < -99 && (dx != event.getX() && dy != event.getY())) {
				dx = M.ScreenWidth / 2;
				dy = M.ScreenHieght * .95f;
				double ang = (GetAngle(-(dy - event.getY()), -(dx - event.getX()))); 
				mGR.mStone.set(0, -.9f, 1, (float) Math.sin(ang) * mGR.mPos[mGR.mLevel].h, -(float) Math.cos(ang) * mGR.mPos[mGR.mLevel].h, -.01f);
				if (mGR.mLevel == 2)
					mGR.mStone.vz = -.018f;
				M.sound4(GameRenderer.mContext, R.drawable.ball_shoot);
			}
			break;
		}
		return true;
	}
	
	
	
	
	
	void DrawTexture(GL10 gl, SimplePlane Tex, float x, float y) {
		Tex.drawPos(gl, x, y);
	}

	void DrawTranScal(GL10 gl, SimplePlane Tex, float x, float y, float z, float t) {
		Tex.drawTranScal(gl, x, y, z, t);
	}

	void DrawTextureR(GL10 gl, SimplePlane Tex, float x, float y, float angle) {
		Tex.drawRotet(gl, 0, 0, angle, x, y);
	}

	void DrawTextureS(GL10 gl, SimplePlane Tex, float x, float y, float scal) {
		Tex.drawScal(gl, x, y, scal, scal);
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
	void drawNumber(GL10 gl,String strs,float x, float y)
	{
		float dx = mGR.mTex_Font[0].width()*.5f;
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i))-47;
			if(k == -1)
				mGR.mTex_Font[0].drawPos(gl,x+i*dx,y);
			if(k>=0 && k<mGR.mTex_Font.length)
				mGR.mTex_Font[k].drawPos(gl,x+i*dx,y);
		}
	}

	void drawNumber(GL10 gl, String strs, float x, float y, float scal) {
		float dx = mGR.mTex_Font[0].width() * .5f * scal;
		for (int i = 0; i < strs.length(); i++) {
			int k = ((int) strs.charAt(i)) - 47;
			if (k == -1)
				mGR.mTex_Font[0].drawScal(gl, x + i * dx, y, scal, scal);
			if (k >= 0 && k < mGR.mTex_Font.length)
				mGR.mTex_Font[k].drawScal(gl, x + i * dx, y, scal, scal);
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
	
	void facebook()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://www.facebook.com/HututuGames"));
		GameRenderer.mContext.startActivity(mIntent);
	}
	
	void twitter()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://twitter.com/hututu_games"));
		GameRenderer.mContext.startActivity(mIntent);
	}
	
	
	void google()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://plus.google.com/+Hututugames"));
		GameRenderer.mContext.startActivity(mIntent);
	}
	
	void share2friend()
	{
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("text/plain");
		i.putExtra(Intent.EXTRA_SUBJECT,"Roking new Game '"+GameRenderer.mContext.getResources().getString(R.string.app_name)+"'");
		//i.putExtra(Intent.EXTRA_TEXT,"Let the battle commence!!! Download it now and let’s ROCK!!!!  "+M.PUBLICLINK+getClass().getPackage().getName());
		i.putExtra(Intent.EXTRA_TEXT,"Let the battle commence!!! Download it now and let’s ROCK!!!!  https://play.google.com/store/apps/details?id="+getClass().getPackage().getName());
		try {
		    GameRenderer.mContext.startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(GameRenderer.mStart, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
}
