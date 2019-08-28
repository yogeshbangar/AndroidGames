package com.hututu.game.penaltyshootout;
import javax.microedition.khronos.opengles.GL10;
import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.widget.Toast;
public class Group extends Mesh 
{
	GameRenderer mGR = null;
	int Counter =0;
	int sc;
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
	
	public Group(GameRenderer _GameRenderer)
	{
		mGR = _GameRenderer;
	}
	
	@Override
	public void draw(GL10 gl) 
	{
		Counter++;
//		M.GameScreen = M.GAMEOVER;
		switch (M.GameScreen) {
		case M.GAMELOGO:
			DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if(Counter>60){
				M.GameScreen = M.GAMEADD;
				Counter=0;
			}
			break;
		case M.GAMEADD:
			if(Counter>100)
				DrawTexture(gl,mGR.mTex_Exit, .9f,.9f);
			else{
				DrawTexture(gl, mGR.mTex_Bar,0.0f,-.9f);
				DrawTexture(gl, mGR.mTex_Pointer,Counter*(mGR.mTex_Bar.width()/100f)-.32f,-.9f);
			}
			break;
		case M.GAMEMENU:
			Draw_Menu(gl);
			break;
		case M.GAMELOAD:
			DrawTexture(gl, mGR.mTex_Loading, 0,-.9f);
			DrawTexture(gl, mGR.mTex_Ball[Counter%mGR.mTex_Ball.length],-1+Counter*.01f,-.9f);
			if(Counter>200){
				M.GameScreen = M.GAMEPLAY;
				mGR.gameReset();
				M.play(GameRenderer.mContext, R.drawable.background);
				M.sound2(GameRenderer.mContext, R.drawable.gamestart_end);
			}
			break;
		case M.GAMEABOUT:
			DrawBG(gl);
			DrawTexture(gl, mGR.mTex_GPbg, 0, 0);
			DrawTexture(gl, mGR.mTex_Aboutscr, 0, 0);
			break;
		default:
			Draw_GamePlay(gl);
			if(M.GameScreen == M.GAMEOVER)
				Draw_GameOver(gl);
			if(M.GameScreen == M.GAMEPUSE)
				Draw_Pause(gl);
			break;
		}
//		DrawBG(gl);
//		setting();
	}
	public boolean TouchEvent(MotionEvent event) 
	{
		switch (M.GameScreen) {
		case M.GAMEADD:
			if((Counter>100)&&CirCir(0.9f,0.9f, .11f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f)&&MotionEvent.ACTION_UP == event.getAction())
			{
				M.GameScreen = M.GAMEMENU;
				M.sound1(GameRenderer.mContext, R.drawable.click);
			}
			break;
		case M.GAMEMENU:
			Handle_Menu(event);
			break;
		case M.GAMEOVER:
			Handle_GameOver(event);
			break;
		case M.GAMEPUSE:
			Handle_GamePause(event);
			break;
		case M.GAMEPLAY:
			Handle_GamePlay(event);
			break;
		case M.GAMEABOUT:
			if(MotionEvent.ACTION_UP == event.getAction())
				M.GameScreen = M.GAMEMENU;
			break;
		}
		Handle(event);
		return true;
	}
	/************************GameOver Start**************************************/
	void Draw_GameOver(GL10 gl)
	{
		float cy = 0;
		DrawTexture(gl, mGR.mTex_GPbg	, 0.00f, 0.00f+cy);
		DrawTexture(gl, mGR.mTex_GOver	, 0.00f, 0.35f+cy);
		
		DrawTexture(gl, mGR.mTex_CScore	, -.34f, 0.17f+cy);
		DrawTexture(gl, mGR.mTex_SBack	, 0.00f, 0.17f+cy);
		drawNumber (gl, mGR.mScore		, -.10f, 0.17f+cy);
		
		DrawTexture(gl, mGR.mTex_HS		, -.34f,0.03f+cy);
		DrawTexture(gl, mGR.mTex_SBack	, 0.00f,0.03f+cy);
		drawNumber (gl, mGR.mHScore[mGR.type], -.10f,0.03f+cy);
		
		DrawTexture(gl, mGR.mSel==5?mGR.mTex_RSBG95:mGR.mTex_RSBGp	, .38f,.1f+cy);
		DrawTexture(gl, mGR.mTex_Submit	, .38f,.1f+cy);
		
		
		DrawTransScal(gl, mGR.mTex_MenuBTN		, -.32f,-.13f+cy,1,mGR.mSel==1?.5f:1);
		DrawTransScal(gl, mGR.mTex_MenuLeft[0][0],-.32f,-.13f+cy,1,mGR.mSel==1? 1f:1);
		DrawTransScal(gl, mGR.mTex_MenuLeft[1][0],-.06f,-.13f+cy,1,mGR.mSel==1?.5f:1);
		
		DrawTransScal(gl, mGR.mTex_SBtn			, -.32f,-.34f+cy,1,mGR.mSel==2?.5f:1);
		DrawTransScal(gl, mGR.mTex_MenuLeft[0][3],-.32f,-.34f+cy,1,mGR.mSel==2? 1f:1);
		DrawTransScal(gl, mGR.mTex_MoreGo		, -.12f,-.34f+cy,1,mGR.mSel==2?.5f:1);
//		
		DrawTexture(gl, mGR.mSel==3?mGR.mTex_RSBG95:mGR.mTex_RSBGp	, 0.38f,-.13f+cy);
		DrawTexture(gl, mGR.mTex_Retry	, 0.38f,-.13f+cy);
		
		DrawTexture(gl, mGR.mSel==4?mGR.mTex_RSBG95:mGR.mTex_RSBGp	, 0.38f,-.34f+cy);
		DrawTexture(gl, mGR.mTex_Main	, 0.38f,-.34f+cy);

	}
	boolean Handle_GameOver(MotionEvent event)
	{
		mGR.mSel = 0;
		float cy = 0;
		if(CircRectsOverlap(-.32f,-.13f+cy, mGR.mTex_SBtn.width()*.6f, mGR.mTex_SBtn.Height()*.4f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
			mGR.mSel = 1;//Like
		}
		if(CircRectsOverlap(-.32f,-.34f+cy, mGR.mTex_SBtn.width()*.6f, mGR.mTex_SBtn.Height()*.4f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
			mGR.mSel = 2;//More
		}
		if(CircRectsOverlap(0.38f,-.13f+cy, mGR.mTex_SBtn.width()*.4f, mGR.mTex_SBtn.Height()*.4f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
			mGR.mSel = 3;//Retry
		}
		if(CircRectsOverlap(0.38f,-.34f+cy, mGR.mTex_SBtn.width()*.4f, mGR.mTex_SBtn.Height()*.4f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
			mGR.mSel = 4;//Main menu
		}
		if(CircRectsOverlap(0.38f,0.10f+cy, mGR.mTex_SBtn.width()*.4f, mGR.mTex_SBtn.Height()*.4f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
			mGR.mSel = 5;//Submit
		}
		if(MotionEvent.ACTION_UP == event.getAction()){
			switch (mGR.mSel) {
			case 1:
				GameRenderer.mStart.onShowLeaderboardsRequested();
				break;
			case 2:
				MoreGame();
				break;
			case 3:
				mGR.gameReset();
				M.GameScreen = M.GAMEPLAY;
				if(M.mRand.nextBoolean())
					M.play(GameRenderer.mContext, R.drawable.background);
				else
					M.play(GameRenderer.mContext, R.drawable.background4);
				break;
			case 4:
				M.GameScreen = M.GAMEMENU;
				break;
			case 5:
				GameRenderer.mStart.Submitscore();
				break;
			}
			if(mGR.mSel != 0)
				M.sound1(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	/************************GameOver  End **************************************/
	
	
	/************************GamePause Start**************************************/
	void Draw_Pause(GL10 gl)
	{
		float cy =0;
		DrawTexture(gl, mGR.mTex_GPbg	, 0.00f, 0.00f+cy);
		DrawTexture(gl, mGR.mTex_GPaused, 0.00f, 0.35f+cy);
		
		DrawTransScal(gl, mGR.mTex_SBtn	, -.32f,0.05f,1,mGR.mSel==1?.5f:1);
		DrawTransScal(gl, mGR.mTex_Snd	, -.32f,0.05f,1,mGR.mSel==1? 1f:1);
		DrawTransScal(gl, M.setValue?mGR.mTex_SndGP:mGR.mTex_SoundOff, -.12f,0.05f,1,mGR.mSel==1?.5f:1);
		
		DrawTransScal(gl, mGR.mTex_SBtn	, -.32f,-.20f,1,mGR.mSel==2?.5f:1);
		DrawTransScal(gl, mGR.mTex_Rate	, -.32f,-.20f,1,mGR.mSel==2? 1f:1);
		DrawTransScal(gl, mGR.mTex_RteGP, -.12f,-.20f,1,mGR.mSel==2?.5f:1);
		
		DrawTexture(gl, mGR.mSel==3?mGR.mTex_RSBG95:mGR.mTex_RSBGp	, 0.30f,0.15f+cy);
		DrawTexture(gl, mGR.mTex_Cont	, 0.30f,0.15f+cy);
		
		DrawTexture(gl, mGR.mSel==4?mGR.mTex_RSBG95:mGR.mTex_RSBGp	, 0.30f,-.10f+cy);
		DrawTexture(gl, mGR.mTex_Retry	, 0.30f,-.10f+cy);
		
		DrawTexture(gl, mGR.mSel==5?mGR.mTex_RSBG95:mGR.mTex_RSBGp	, 0.30f,-.35f+cy);
		DrawTexture(gl, mGR.mTex_Main	, 0.30f,-.35f+cy);

	}
	boolean Handle_GamePause(MotionEvent event)
	{
		mGR.mSel = 0;
		float cy = 0;
		if(CircRectsOverlap(-.32f,0.05f+cy, mGR.mTex_SBtn.width()*.4f, mGR.mTex_SBtn.Height()*.4f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
			mGR.mSel = 1;//Sound
		}
		if(CircRectsOverlap(-.32f,-.20f+cy, mGR.mTex_SBtn.width()*.4f, mGR.mTex_SBtn.Height()*.4f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
			mGR.mSel = 2;//Rate
		}
		if(CircRectsOverlap(0.30f,0.15f+cy, mGR.mTex_SBtn.width()*.4f, mGR.mTex_SBtn.Height()*.4f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
			mGR.mSel = 3;//Continue
		}
		if(CircRectsOverlap(0.30f,-.10f+cy, mGR.mTex_SBtn.width()*.4f, mGR.mTex_SBtn.Height()*.4f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
			mGR.mSel = 4;////Retry
		}
		if(CircRectsOverlap(0.30f,-.35f+cy, mGR.mTex_SBtn.width()*.4f, mGR.mTex_SBtn.Height()*.4f, screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
			mGR.mSel = 5;//Main menu
		}
		if(MotionEvent.ACTION_UP == event.getAction()){
			switch (mGR.mSel) {
			case 1:
				M.setValue = !M.setValue;
				break;
			case 2:
				RateUs();
				break;
			case 3:
				M.GameScreen = M.GAMEPLAY;
				mGR.gametime = System.currentTimeMillis() - mGR.gametime;
				M.play(GameRenderer.mContext, R.drawable.background);
				break;
			case 4:
				mGR.gameReset();
				M.GameScreen = M.GAMEPLAY;
				M.play(GameRenderer.mContext, R.drawable.background);
				break;
			case 5:
				M.GameScreen = M.GAMEMENU;
				break;
			}
			if(mGR.mSel != 0)
				M.sound1(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	/************************GamePause  End **************************************/
	
	/************************Menu Start*******************************************/
	float mx=0,mvx=0;
	float mv = .53f;
	void Draw_Menu(GL10 gl)
	{
		float cy=-.14f;
		DrawTexture(gl, mGR.mTex_Splash	, 0,0);
		DrawTextureR(gl, mGR.mTex_RPanel, mx+0.80f, .00f+cy, 180);
		DrawTextureR(gl, mGR.mTex_InOut	, mx+0.445f, 0, 000);
		DrawTextureR(gl, mGR.mTex_MPoint, mx+0.45f, 0, mx!=0?0:180);
		
		for(int i=0;i<6;i++){
			DrawTexture(gl, mGR.mTex_RedMenuBtn	, mx+0.78f, .73f-.29f*i+cy);
			if(i==mGR.mSel-1)
				DrawTexture(gl, mGR.mTex_RMenuBtn, mx+0.78f, .73f-.29f*i+cy);
			DrawTexture(gl, mGR.mTex_MenuRight[i], mx+0.78f, .73f-.29f*i+cy);
		}
		
		DrawTextureR(gl, mGR.mTex_RPanel, 1.1f*mx-1.315f, .00f+cy, 000);
		DrawTextureR(gl, mGR.mTex_InOut , 1.1f*mx-0.96f, .00f, 180);
		DrawTextureR(gl, mGR.mTex_MPoint, 1.1f*mx-0.96f, 0, mx!=0?0:180);
		for(int i=0;i<6;i++){
			DrawTransScal(gl, mGR.mTex_MenuBTN			, 1.1f*mx-1.35f,.73f-.29f*i+cy,1,mGR.mSel==i+10?.5f:1);
			DrawTransScal(gl, mGR.mTex_MenuLeft[0][i]	, 1.1f*mx-1.35f,.73f-.29f*i+cy,1,mGR.mSel==i+10?.5f:1);
			DrawTransScal(gl, mGR.mTex_MenuLeft[1][i]	, 1.1f*mx-1.09f,.73f-.29f*i+cy,1,mGR.mSel==i+10?.5f:1);
			if(!M.setValue && i==4)
				DrawTransScal(gl, mGR.mTex_SoundOff	, 1.1f*mx-1.08f,.73f-.29f*i+cy,1,mGR.mSel==i+10?.5f:1);
		}
		
		mx+=mvx;
		if(mx>=mv){
			mvx=0;
			mx=mv;
		}
		if(mx<0){
			mvx=0;
			mx=0f;
		}
	}
	boolean Handle_Menu(MotionEvent event)
	{
		mGR.mSel =0;
		float cy=-.14f;
		if(mvx == 0 && mx == 0)
		{
			for(int i=0;i<6;i++){
				if(CircRectsOverlap(mx+0.78f, .73f-.29f*i+cy, mGR.mTex_MenuBTN.width()*.3f, mGR.mTex_MenuBTN.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05))
				{
					mGR.mSel = i+1;
				}
			}
			if(CircRectsOverlap( mx+0.4f, 0, mGR.mTex_InOut.width(), mGR.mTex_InOut.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05))
			{
				mGR.mSel = 19;
			}
			if(CircRectsOverlap( mx-0.9f, 0, mGR.mTex_InOut.width(), mGR.mTex_InOut.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05))
			{
				mGR.mSel = 19;
			}
		}
		if(mvx == 0 && mx == mv)
		{
			for(int i=0;i<6;i++){
				if(CircRectsOverlap(1.1f*mx-1.38f,.73f-.29f*i+cy, mGR.mTex_MenuBTN.width()*.3f, mGR.mTex_MenuBTN.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05))
				{
					mGR.mSel = i+10;
				}
			}
			if(CircRectsOverlap( mx+0.40f, 0, mGR.mTex_InOut.width(), mGR.mTex_InOut.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05))
			{
				mGR.mSel = 20;
			}
			if(CircRectsOverlap( 1.1f*mx-0.90f, 0, mGR.mTex_InOut.width(), mGR.mTex_InOut.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05))
			{
				mGR.mSel = 20;
			}
		}
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mGR.mSel) {
			case 1:
				mGR.type =0;
				mGR.gameReset();
				Counter =0;
				M.GameScreen = M.GAMEPLAY;
				break;
			case 2:
				mGR.type =1;
				mGR.gameReset();
				Counter =0;
				M.GameScreen = M.GAMEPLAY;
				break;
			case 3:
				mGR.type =2;
				mGR.gameReset();
				Counter =0;
				M.GameScreen = M.GAMEPLAY;
				break;
			case 4:
				mGR.type =3;
				mGR.gameReset();
				Counter =0;
				M.GameScreen = M.GAMEPLAY;
				break;
			case 5:
				mGR.type =4;
				mGR.gameReset();
				Counter =0;
				M.GameScreen = M.GAMEPLAY;
				break;
			case 6:
				mGR.type =5;
				mGR.gameReset();
				Counter =0;
				M.GameScreen = M.GAMEPLAY;
				break;
			case 10:
				GameRenderer.mStart.onShowLeaderboardsRequested();
				break;
			case 11:
				Google();
				break;
			case 12:
				facebook();
				break;
			case 13:
				MoreGame();
				break;
			case 14:
				M.setValue= !M.setValue;
				break;
			case 15:
				M.GameScreen = M.GAMEABOUT;
				break;
			case 19:
				mvx =0.04f;
				mx = 0;
				break;
			case 20:
				mvx =-.04f;
				mx = mv;
				break;
			}
			if(mGR.mSel != 0)
				M.sound1(GameRenderer.mContext, R.drawable.click);
			mGR.mSel =0;
		}
		return true;
	}
	/************************Menu End  **************************************/
	
	
	/************************Game play Start**************************************/
	void drawPass(GL10 gl ,int go)
	{
		int m=go==0?1:-1;
		switch (mGR.mBall.ani%mGR.mTex_Fall[go].length) {
		case 0:
			mGR.mKeeper.ex = m*0.00f;
			mGR.mKeeper.ey = 0.00f;
			DrawTexture(gl, mGR.mTex_Fall[go][0], mGR.mKeeper.ex+mGR.mKeeper.x, mGR.mKeeper.ey+mGR.mKeeper.y);
			break;
		case 1:
			mGR.mKeeper.ex = m*0.01f;
			mGR.mKeeper.ey = 0.00f;
			DrawTexture(gl, mGR.mTex_Fall[go][1], mGR.mKeeper.ex+mGR.mKeeper.x, mGR.mKeeper.ey+mGR.mKeeper.y);
			break;
		case 2:
			mGR.mKeeper.ex = m*0.02f;
			mGR.mKeeper.ey = 0.03f;
			DrawTexture(gl, mGR.mTex_Fall[go][2], mGR.mKeeper.ex+mGR.mKeeper.x, mGR.mKeeper.ey+mGR.mKeeper.y);
			break;
		case 3:
			mGR.mKeeper.ex = m*0.03f;
			mGR.mKeeper.ey = 0.06f;
			DrawTexture(gl, mGR.mTex_Fall[go][3], mGR.mKeeper.ex+mGR.mKeeper.x, mGR.mKeeper.ey+mGR.mKeeper.y);
			break;
		case 4:
			mGR.mKeeper.ex = m*0.04f;
			mGR.mKeeper.ey = 0.08f;
			DrawTexture(gl, mGR.mTex_Fall[go][4], mGR.mKeeper.ex+mGR.mKeeper.x, mGR.mKeeper.ey+mGR.mKeeper.y);
			break;
		case 5:
			mGR.mKeeper.ex = m*0.05f;
			mGR.mKeeper.ey = 0.05f;
			DrawTexture(gl, mGR.mTex_Fall[go][5], mGR.mKeeper.ex+mGR.mKeeper.x, mGR.mKeeper.ey+mGR.mKeeper.y);
			break;
		case 6:
			mGR.mKeeper.ex = m*0.06f;
			mGR.mKeeper.ey = 0.04f;
			DrawTexture(gl, mGR.mTex_Fall[go][6], mGR.mKeeper.ex+mGR.mKeeper.x, mGR.mKeeper.ey+mGR.mKeeper.y);
			break;
		case 7:
			mGR.mKeeper.ex = m*0.07f;
			mGR.mKeeper.ey = 0.03f;
			DrawTexture(gl, mGR.mTex_Fall[go][7], mGR.mKeeper.ex+mGR.mKeeper.x, mGR.mKeeper.ey+mGR.mKeeper.y);
			break;
		case 8:
			mGR.mKeeper.ex = m*0.08f;
			mGR.mKeeper.ey = 0.00f;
			DrawTexture(gl, mGR.mTex_Fall[go][8], mGR.mKeeper.ex+mGR.mKeeper.x, mGR.mKeeper.ey+mGR.mKeeper.y);
			break;
		case 9:
			mGR.mKeeper.ex = m*0.09f;
			mGR.mKeeper.ey = -.01f;
			DrawTexture(gl, mGR.mTex_Fall[go][9], mGR.mKeeper.ex+mGR.mKeeper.x, mGR.mKeeper.ey+mGR.mKeeper.y);
			break;
		case 10:
			mGR.mKeeper.ex = m*0.10f;
			mGR.mKeeper.ey = -.01f;
			DrawTexture(gl, mGR.mTex_Fall[go][10],mGR.mKeeper.ex+mGR.mKeeper.x, mGR.mKeeper.ey+mGR.mKeeper.y);
			break;
		case 11:
			mGR.mKeeper.ex = m*0.12f;
			mGR.mKeeper.ey = -.04f;
			DrawTexture(gl, mGR.mTex_Fall[go][11],mGR.mKeeper.ex+mGR.mKeeper.x, mGR.mKeeper.ey+mGR.mKeeper.y);
			break;
		case 12:
			mGR.mKeeper.ex = m*0.14f;
			mGR.mKeeper.ey = -.04f;
			DrawTexture(gl, mGR.mTex_Fall[go][12], mGR.mKeeper.ex+mGR.mKeeper.x, mGR.mKeeper.ey+mGR.mKeeper.y);
			break;
		case 13:
			mGR.mKeeper.ex = m*0.16f;
			mGR.mKeeper.ey = -.05f;
			DrawTexture(gl, mGR.mTex_Fall[go][13], mGR.mKeeper.ex+mGR.mKeeper.x, mGR.mKeeper.ey+mGR.mKeeper.y);
			break;
		case 14:
			mGR.mKeeper.ex = m*0.18f;
			mGR.mKeeper.ey = -.06f;
			DrawTexture(gl, mGR.mTex_PShaw		, mGR.mKeeper.ex+mGR.mKeeper.x, mGR.mKeeper.ey+mGR.mKeeper.y);
			DrawTexture(gl, mGR.mTex_Fall[go][14],mGR.mKeeper.ex+mGR.mKeeper.x, mGR.mKeeper.ey+mGR.mKeeper.y);
			break;
		}
		
	}
	void drawDoor(GL10 gl ,int go)
	{
		int m=go==0?1:-1;
		switch (mGR.mBall.ani%mGR.mTex_Fall[go].length) {
		case 0:
			mGR.mKeeper.ex = m*0.00f;
			mGR.mKeeper.ey = 0.00f;
			DrawTexture(gl, mGR.mTex_Fall[go][0], mGR.mKeeper.ex+mGR.mKeeper.x, mGR.mKeeper.ey+mGR.mKeeper.y);
			break;
		case 1:
			mGR.mKeeper.ex = m*0.02f;
			mGR.mKeeper.ey = 0.00f;
			DrawTexture(gl, mGR.mTex_Fall[go][1], mGR.mKeeper.ex+mGR.mKeeper.x, mGR.mKeeper.ey+mGR.mKeeper.y);
			break;
		case 2:
			mGR.mKeeper.ex = m*0.04f;
			mGR.mKeeper.ey = 0.03f;
			DrawTexture(gl, mGR.mTex_Fall[go][2], mGR.mKeeper.ex+mGR.mKeeper.x, mGR.mKeeper.ey+mGR.mKeeper.y);
			break;
		case 3:
			mGR.mKeeper.ex =  m*0.08f;
			mGR.mKeeper.ey = 0.06f;
			DrawTexture(gl, mGR.mTex_Fall[go][3],mGR.mKeeper.ex+mGR.mKeeper.x, mGR.mKeeper.ey+mGR.mKeeper.y);
			break;
		case 4:
			mGR.mKeeper.ex = m*0.12f;
			mGR.mKeeper.ey = 0.08f;
			DrawTexture(gl, mGR.mTex_Fall[go][4], mGR.mKeeper.ex+mGR.mKeeper.x, mGR.mKeeper.ey+mGR.mKeeper.y);
			break;
		case 5:
			mGR.mKeeper.ex = m*0.14f;
			mGR.mKeeper.ey = 0.05f;
			DrawTexture(gl, mGR.mTex_Fall[go][5], mGR.mKeeper.ex+mGR.mKeeper.x, mGR.mKeeper.ey+mGR.mKeeper.y);
			break;
		case 6:
			mGR.mKeeper.ex = m*0.16f;
			mGR.mKeeper.ey = 0.04f;
			DrawTexture(gl, mGR.mTex_Fall[go][6], mGR.mKeeper.ex+mGR.mKeeper.x, mGR.mKeeper.ey+mGR.mKeeper.y);
			break;
		case 7:
			mGR.mKeeper.ex = m*0.18f;
			mGR.mKeeper.ey = 0.03f;
			DrawTexture(gl, mGR.mTex_Fall[go][7], mGR.mKeeper.ex+mGR.mKeeper.x, mGR.mKeeper.ey+mGR.mKeeper.y);
			break;
		case 8:
			mGR.mKeeper.ex = m*0.21f;
			mGR.mKeeper.ey = 0.00f;
			DrawTexture(gl, mGR.mTex_Fall[go][8], mGR.mKeeper.ex+mGR.mKeeper.x, mGR.mKeeper.ey+mGR.mKeeper.y);
			break;
		case 9:
			mGR.mKeeper.ex = m*0.22f;
			mGR.mKeeper.ey = -.01f;
			DrawTexture(gl, mGR.mTex_Fall[go][9], mGR.mKeeper.ex+mGR.mKeeper.x, mGR.mKeeper.ey+mGR.mKeeper.y);
			break;
		case 10:
			mGR.mKeeper.ex = m*0.23f;
			mGR.mKeeper.ey = -.01f;
			DrawTexture(gl, mGR.mTex_Fall[go][10], mGR.mKeeper.ex+mGR.mKeeper.x, mGR.mKeeper.ey+mGR.mKeeper.y);
			break;
		case 11:
			mGR.mKeeper.ex = m*0.26f;
			mGR.mKeeper.ey = -.04f;
			DrawTexture(gl, mGR.mTex_Fall[go][11], mGR.mKeeper.ex+mGR.mKeeper.x, mGR.mKeeper.ey+mGR.mKeeper.y);
			break;
		case 12:
			mGR.mKeeper.ex = m*0.28f;
			mGR.mKeeper.ey = -.05f;
			DrawTexture(gl, mGR.mTex_Fall[go][12], mGR.mKeeper.ex+mGR.mKeeper.x, mGR.mKeeper.ey+mGR.mKeeper.y);
			break;
		case 13:
			mGR.mKeeper.ex = m*0.28f;
			mGR.mKeeper.ey = -.07f;
			DrawTexture(gl, mGR.mTex_Fall[go][13], mGR.mKeeper.ex+mGR.mKeeper.x, mGR.mKeeper.ey+mGR.mKeeper.y);
			break;
		case 14:
			mGR.mKeeper.ex = m*0.28f;
			mGR.mKeeper.ey = -.09f;
			DrawTexture(gl, mGR.mTex_PShaw		, mGR.mKeeper.ex+mGR.mKeeper.x, mGR.mKeeper.ey+mGR.mKeeper.y);
			DrawTexture(gl, mGR.mTex_Fall[go][14], mGR.mKeeper.ex+mGR.mKeeper.x, mGR.mKeeper.ey+mGR.mKeeper.y);
			break;
		}
	}
	void updatescore()
	{
		mGR.mGoal++;
		if(mGR.mGoal%4 == 0)
		{
			mGR.mNlvl.set(-2.0f, .1f, 1);
		}
		int score = mGR.mGoal*10+89+M.mRand.nextInt(mGR.mGoal*10);
		mGR.mScore += score;
		mGR.mPoint.set(0,0);
		if(mGR.mScore > mGR.mHScore[mGR.type])
		{
			mGR.mHScore[mGR.type]  =mGR.mScore;
		}
	}
	int r5 = 0;// Change 4/12
	void reset()
	{
		mGR.mTPoint[0].set(+(M.mRand.nextFloat()%.2f)+.08f, (M.mRand.nextFloat()%.2f)+.6f, M.mRand.nextInt(2));
		mGR.mTPoint[1].set(-(M.mRand.nextFloat()%.2f)-.08f, (M.mRand.nextFloat()%.2f)+.6f,mGR.mTPoint[0].m==0?1:0);
		mGR.mBall.set(0,-.8f, 1, 0,0,0);
		mGR.mKeeper.set();
		/* Change 4/12 */
		r5++;
		if(r5%3==0)
			mGR.mTPoint[1].x = -1000;
		mGR.mTTime.set();
		/* Change 4/12 */
	}
	void gameOver()
	{
		//if (true) 
		{
			try {
				GameRenderer.mStart.show();
			} catch (Exception e) {
			}
		}
		M.playStop();
		M.GameScreen = M.GAMEOVER;
		M.sound2(GameRenderer.mContext, R.drawable.gamestart_end);
		
	}
	
	void gamelogic()
	{
		if(mGR.mBall.reset>0)
			mGR.mBall.update();
		mGR.mLevel = 1+(mGR.mGoal/4);
		setAnimation(mGR.mBall.x, mGR.mBall.y);
		/* Change 4/12 */
		if(mGR.type==5)
		{
			for(int i = 0;i<mGR.mTPoint.length;i++)
			{
				if(mGR.mTPoint[i].isMoving)
				{
					mGR.mTPoint[i].update();
				}
			}
		}
		if(mGR.type == 0)
		{
			mGR.mTTime.update();
		}
		/* Change 4/12 */
		if(mGR.mBall.z<=M.SC && mGR.mBall.in ==0)
		{
			/* Change 4/12 */
			if(mGR.type==0 && !mGR.mTTime.isCollide){
				if(CirCir(mGR.mTTime.x,mGR.mTTime.y, mGR.mTex_Time[0][1].Height()*.23f, mGR.mBall.x, mGR.mBall.y, .05f))
				{
					mGR.mTTime.isCollide = true;
					if(mGR.mTTime.no == 0)
						mGR.mTTime.inc += 2100;
					if(mGR.mTTime.no == 1)
						mGR.mTTime.inc += 3100;
					if(mGR.mTTime.no == 2)
						mGR.mTTime.inc += 5100;
					mGR.mScore += mGR.mTTime.inc /100;
					if(mGR.mScore > mGR.mHScore[mGR.type])
						mGR.mHScore[mGR.type]  =mGR.mScore;
				}
			}
			/* Change 4/12 */
			if(mGR.type==5){
				if(CirCir(mGR.mTPoint[0].x,mGR.mTPoint[0].y, mGR.mTex_Disk[0].Height()*.23f, mGR.mBall.x, mGR.mBall.y, .05f))
				{
					if(mGR.mBall.in ==0){
						mGR.mBall.in = 5;
						updatescore();
					}
				}
				if(CirCir(mGR.mTPoint[1].x,mGR.mTPoint[1].y, mGR.mTex_Disk[0].Height()*.23f, mGR.mBall.x, mGR.mBall.y, .05f))
				{
					if(mGR.mBall.in ==0){
						mGR.mBall.in = 6;
						updatescore();
					}
				}
			}
			if(mGR.type!=2f && mGR.type!=5){
				if(mGR.mBall.dir==0||mGR.mBall.dir==1){
					if(CircRectsOverlap(mGR.mKeeper.x+mGR.mKeeper.ex,mGR.mKeeper.y+mGR.mKeeper.ey, .08f*mGR.mLevel*.1f, .15f*mGR.mLevel*.1f, mGR.mBall.x, mGR.mBall.y, .05f))
					{
						if(mGR.mBall.in ==0)
							mGR.mBall.in = 3;
					}
				}
				else
					if(CircRectsOverlap(mGR.mKeeper.x+mGR.mKeeper.ex,mGR.mKeeper.y+mGR.mKeeper.ey, .15f*mGR.mLevel*.1f, .05f*mGR.mLevel*.1f, mGR.mBall.x, mGR.mBall.y, .05f))
					{
						if(mGR.mBall.in ==0)
							mGR.mBall.in = 3;
					}
			}
			if(CircRectsOverlap(0, 0.71f, mGR.mTex_Ground[3].width()*.2f, mGR.mTex_Ground[3].Height()*.18f, mGR.mBall.x, mGR.mBall.y, .05f))
			{
				if(mGR.mBall.in ==0){
					mGR.mBall.in = 1;
					if(mGR.type!=2f && mGR.type!=5&& mGR.type!=6){
						updatescore();
					}
				}
			}
			for(int i=0;i<10;i++)
			{
				if(CirCir(-.32f, .56f+i*.03f, .01f, mGR.mBall.x, mGR.mBall.y, .05f)||
						CirCir(0.32f, .56f+i*.03f, .01f, mGR.mBall.x, mGR.mBall.y, .05f))
				{
					if(mGR.mBall.in ==0)
					{
						mGR.mBall.in = 3;
					}
				}
			}
			for(int i=0;i<17;i++){
				if(CirCir(-.32f+i*(.04f), .87f, .01f, mGR.mBall.x, mGR.mBall.y, .05f))
				{
					if(mGR.mBall.in ==0){
						mGR.mBall.in = 3;
					}
				}
			}
			if(mGR.mBall.in ==0)
				mGR.mBall.in = 2;
		}
		if(mGR.mBall.in !=0 && !mGR.mBall.sound)
		{
			mGR.mBall.sound =true;
			if(mGR.mBall.in == 3)
				M.sound5(GameRenderer.mContext, R.drawable.goal_post);
			if(mGR.type != 2 && mGR.type != 5)
			{
				if(mGR.mBall.in == 1)
					M.sound3(GameRenderer.mContext, R.drawable.goal);
				else
					M.sound4(GameRenderer.mContext, R.drawable.miss);
			}
			if(mGR.type == 2)
			{
				if(mGR.mBall.in == 3)
					M.sound3(GameRenderer.mContext, R.drawable.goal);
				else
					M.sound4(GameRenderer.mContext, R.drawable.miss);
			}
			if(mGR.type == 5)
			{
				if(mGR.mBall.in == 5 || mGR.mBall.in == 6)
					M.sound5(GameRenderer.mContext, R.drawable.target_shoot);
				else
					M.sound4(GameRenderer.mContext, R.drawable.miss);
			}
		}
		if(mGR.mBall.z<.4f || mGR.mBall.in !=0)
		{
			mGR.mBall.reset ++;
			if(mGR.mBall.reset >50){
				if((mGR.type==1||mGR.type==4) && mGR.mBall.in!=1){
					mGR.Target--;
					if(mGR.Target<=0)
						gameOver();
				}
				if((mGR.type==5) && mGR.mBall.in!=5&& mGR.mBall.in!=6){
					mGR.Target--;
					if(mGR.Target<=0)
						gameOver();
				}
				if(mGR.type==2){
					if(mGR.mBall.in!=3){
						mGR.Target--;
						if(mGR.Target<=0 )
							gameOver();
					}
					else{
						
						updatescore();
					}
				}
				reset();
			}
		}
		else
		{
			mGR.mKeeper.sc+=mGR.mKeeper.inc;
			if(mGR.mKeeper.sc>=mGR.mTex_Stand.length-1)
				mGR.mKeeper.inc = -1;
			if(mGR.mKeeper.sc<=0)
				mGR.mKeeper.inc = 1;
		}
	}
	void Draw_GamePlay(GL10 gl){
		DrawBG(gl);
		if(mGR.type == 5){
			DrawTransScal(gl, mGR.mTex_Disk[mGR.mTPoint[0].m],mGR.mTPoint[0].x,mGR.mTPoint[0].y,1.4f,mGR.mBall.in==5?.5f:1);
			DrawTransScal(gl, mGR.mTex_Disk[mGR.mTPoint[1].m],mGR.mTPoint[1].x,mGR.mTPoint[1].y,1.9f,mGR.mBall.in==6?.5f:1);// Change 4/12
		}
		/* Change 4/12 */ 
		if(mGR.type == 0){
			DrawTransScal(gl, mGR.mTex_Time[mGR.mTTime.no][mGR.mTTime.isCollide?0:1],mGR.mTTime.x,mGR.mTTime.y,1.0f,mGR.mBall.in==5?.5f:1);
		}
		/* Change 4/12 */
		if(mGR.mBall.in == 1){
			drawAnimation(gl);
			if(mGR.mBall.in!=0 && mGR.mBall.in!=2)
				mGR.mTex_Shadow.drawRGBS(gl, mGR.mBall.x, mGR.mBall.s-(mGR.mBall.z>.3?.05f:.03f), 1,1,1,0.6f, mGR.mBall.z>.3?.8f:.55f);
			else
				mGR.mTex_Ball[Counter%mGR.mTex_Ball.length].drawRGBS(gl, mGR.mBall.x, mGR.mBall.s, 0,0,0,.4f, mGR.mBall.z);
			if(mGR.mBall.reset>30)
				DrawTextureS(gl, mGR.mTex_Ball[0], mGR.mBall.x, mGR.mBall.y,mGR.mBall.z);
			else
				DrawTextureS(gl, mGR.mTex_Ball[Counter%mGR.mTex_Ball.length], mGR.mBall.x, mGR.mBall.y,mGR.mBall.z);
		}
		if(mGR.type!=2f && mGR.type!=5)
		{
			if(mGR.mBall.z<.4f){
				switch (mGR.mBall.dir) {
				case 0:
					mGR.mKeeper.ex=mGR.mKeeper.ey=0;
					mGR.mKeeper.x = mGR.mBall.x*mGR.mLevel*.1f;
					DrawTexture(gl, mGR.mTex_Block[mGR.mBall.ani], mGR.mKeeper.ex+mGR.mKeeper.x, mGR.mKeeper.ey+mGR.mKeeper.y);
					if(mGR.mBall.ani<mGR.mTex_Block.length-1)
						mGR.mBall.ani++;
					break;
				case 1:
					mGR.mKeeper.x = mGR.mBall.x*mGR.mLevel*.1f;
					mGR.mKeeper.ex=mGR.mKeeper.ey=0;
					DrawTexture(gl, mGR.mTex_Cover[mGR.mBall.ani], mGR.mKeeper.ex+mGR.mKeeper.x, mGR.mKeeper.ey+mGR.mKeeper.y);
					if(mGR.mBall.ani<mGR.mTex_Cover.length-1)
						mGR.mBall.ani++;
					break;
				case 2:
					drawPass(gl,0);//PassRight
					if(mGR.mBall.ani<mGR.mTex_Fall[0].length-1)
						mGR.mBall.ani++;
					break;
				case 3:
					drawPass(gl,1);//PassLeft
					if(mGR.mBall.ani<mGR.mTex_Fall[0].length-1 )
						mGR.mBall.ani++;
					break;
				case 4:
					drawDoor(gl,0);//DoorRight
					if(mGR.mBall.ani<mGR.mTex_Fall[0].length-1)
						mGR.mBall.ani++;
					break;
				case 5:
					drawDoor(gl,1);//DoorLeft
					if(mGR.mBall.ani<mGR.mTex_Fall[0].length-1)
						mGR.mBall.ani++;
					break;
				}
			}
			else{
				DrawTexture(gl, mGR.mTex_Stand[mGR.mKeeper.sc]	, mGR.mKeeper.x,mGR.mKeeper.y);
			}
		}
		
		if(mGR.mPoint.y<1.5f)
		{
			DrawTexture(gl, mGR.mTex_Goal[mGR.mPoint.i], mGR.mPoint.x, mGR.mPoint.y);
			mGR.mPoint.update();
		}
		if(M.GameScreen == M.GAMEPLAY){
			if(mGR.mBall.in != 1){
				drawAnimation(gl);
				if(mGR.mBall.in!=0 && mGR.mBall.in!=2)
					mGR.mTex_Shadow.drawRGBS(gl, mGR.mBall.x, mGR.mBall.s-(mGR.mBall.z>.3?.05f:.03f), 1,1,1,0.6f, mGR.mBall.z>.3?.8f:.55f);
				else
					mGR.mTex_Ball[Counter%mGR.mTex_Ball.length].drawRGBS(gl, mGR.mBall.x, mGR.mBall.s, 0,0,0,.4f, mGR.mBall.z);
				if(mGR.mBall.reset>30)
					DrawTextureS(gl, mGR.mTex_Ball[0], mGR.mBall.x, mGR.mBall.y,mGR.mBall.z);
				else
					DrawTextureS(gl, mGR.mTex_Ball[Counter%mGR.mTex_Ball.length], mGR.mBall.x, mGR.mBall.y,mGR.mBall.z);
			}
			DrawTexture(gl, mGR.mTex_Score_back	, -.86f, 0.90f);DrawTexture(gl, mGR.mTex_Score	, -.86f, 0.90f);
			DrawTexture(gl, mGR.mTex_ScorePrint	, -.84f, 0.75f);drawNumber (gl, mGR.mScore		, -.92f, 0.75f);
			DrawTexture(gl, mGR.mTex_HSbg		, -.82f, 0.60f);DrawTexture(gl, mGR.mTex_HS		, -.82f, 0.60f);
			DrawTexture(gl, mGR.mTex_ScorePrint	, -.84f, 0.45f);drawNumber (gl, mGR.mHScore[mGR.type]		, -.92f, 0.45f);
			
			
			switch(mGR.type){
			case 0:
			case 3:
				int time = 31000;
				if(mGR.type == 0)
					time = 61000+mGR.mTTime.inc;
				int sec = (int)(mGR.gametime+time-System.currentTimeMillis())/1000;
				if(M.GameScreen == M.GAMEOVER)
					sec = 0;
				if(M.GameScreen == M.GAMEPUSE)
					sec = (int)(time-mGR.gametime)/1000;
				DrawTexture(gl, mGR.mTex_Score_back	, 0.86f, 0.90f);DrawTexture(gl, mGR.mTex_MenuRight[0], 0.86f, 0.90f);
				DrawTexture(gl, mGR.mTex_Score_back	, 0.86f, 0.75f);drawNumber(gl, sec, 0.81f, 0.75f);
				if(sec<=0)
					gameOver();
				break;
			case 1:case 2:
			case 4:case 5:
				DrawTexture(gl, mGR.mTex_Score_back	, 0.86f, 0.90f);DrawTexture(gl, mGR.mTex_BText, 0.86f, 0.90f);
				DrawTexture(gl, mGR.mTex_Score_back	, 0.86f, 0.75f);drawNumber(gl, mGR.Target, 0.81f, 0.75f);
				break;
			}
			DrawTexture(gl, mGR.mTex_Score_back	, 0.86f, 0.60f);DrawTexture(gl, mGR.mTex_Level	, 0.86f, 0.60f);
			DrawTexture(gl, mGR.mTex_Score_back	, 0.86f, 0.45f);drawNumber(gl, mGR.mLevel, 0.81f, 0.45f);
		}
		if(mGR.mNlvl.m>0 && mGR.mNlvl.m<100)
		{
			if(mGR.mNlvl.x>-.1f && mGR.mNlvl.x <.1 && mGR.mNlvl.m<40)
				mGR.mNlvl.y=0;
			else
				mGR.mNlvl.y = .1f;
			mGR.mNlvl.m++;
			mGR.mNlvl.x+=mGR.mNlvl.y;
			DrawTexture(gl, mGR.mTex_NewLvl	,mGR.mNlvl.x, 0);
		}
		gamelogic();
	}
	float dx,dy;
	public boolean Handle_GamePlay(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			dx = event.getX();
			dy = event.getY();
			break;
		case MotionEvent.ACTION_UP:
			if((dx !=event.getX() && dy !=event.getY()) && mGR.mBall.reset == 0)
			{
				float d = (float)Math.sqrt((screen2worldX(dx)-screen2worldX(event.getX()))*(screen2worldX(dx)-screen2worldX(event.getX()))+
						(screen2worldY(dy)-screen2worldY(event.getY()))*(screen2worldY(dy)-screen2worldY(event.getY())))*.012f;
				d+=.093f;
				mGR.mBall.ang = (float)(GetAngle(-(dy-event.getY()),-(dx-event.getX())));
				if(mGR.mBall.ang > 2.2f && mGR.mBall.ang < 3.9f && d>.097)
				{
					dx =  M.ScreenWidth/2;
					dy =  M.ScreenHieght * .95f;
					
//					mGR.mBall.ang = 2.9108067f;
					mGR.mBall.set(0,-.9f, 1, (float)Math.sin(mGR.mBall.ang)*d*.4f,-(float)Math.cos(mGR.mBall.ang)*d, -.025f);
//					System.out.println("[x ="+mGR.mBall.x+"] [y = "+mGR.mBall.y+"] [vx = "+mGR.mBall.vx+"] [vy = "+mGR.mBall.vy+"] [ang = "+mGR.mBall.ang+"] [d = "+d+"]");
					mGR.mBall.reset++;
					M.sound6(GameRenderer.mContext, R.drawable.kick);
					
				}
			}
			break;
		}
		return true;
	}
	/************************Game play End  **************************************/
	float r,g,b;
	int fd=100,vfd=1;
	void DrawBG(GL10 gl)
	{
		DrawTextureS(gl, mGR.mTex_Ground[0], 0, 0.00f, 2);
		DrawTexture(gl, mGR.mTex_Ground[1], 0, -.05f);
		DrawTexture(gl, mGR.mTex_Ground[2], 0, 0.88f);
		if(mGR.mBall.reset>6 && mGR.mBall.reset<12 && mGR.mBall.in==1)
			DrawTexture(gl, mGR.mTex_Ground[3+(mGR.mBall.reset%3)], 0, 0.71f);
		else
			DrawTexture(gl, mGR.mTex_Ground[3], 0, 0.71f);

		if (mGR.type == 2) {
			mGR.mTex_Ground[6].drawRGBS(gl, 0, 0.71f, 1, .8f, 1, (fd) * .01f, 1);
			fd += vfd;
			if (fd > 100) vfd = -5;
			if (fd < 10) vfd = 5;
		}
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
		float dx = mGR.mTex_Font[0].width()*.8f;
		 String strs = ""+no;
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i))-48;
			if(k>=0 && k<mGR.mTex_Font.length)
				mGR.mTex_Font[k].drawPos(gl,x+i*dx,y);
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
	void setAnimation(float x,float y)
	{
		int cr = 0;
		for(int i=0;i<mGR.ani.length&& cr<1;i++)
		{
			if(mGR.ani[i].x<-1.1||mGR.ani[i].x>1.1||mGR.ani[i].y<-1.1||mGR.ani[i].y>1.1)
			{
				cr++;
				mGR.ani[i].set(x,y);
			}
		}
	}
	void drawAnimation(GL10 gl)
	{
		for(int i=0;i<mGR.ani.length;i++)
		{
			if(mGR.ani[i].x>-1.2&&mGR.ani[i].x<1.2&&mGR.ani[i].y>-1.2&&mGR.ani[i].y<1.2)
			{
				mGR.mTex_8x8.drawRGBS(gl, mGR.ani[i].x, mGR.ani[i].y, 1,1,1,.6f, mGR.ani[i].scal);
				mGR.ani[i].update();
			}
		}
	}
	void Google()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://plus.google.com/101161010890539846728"));
		GameRenderer.mContext.startActivity(mIntent);
	}
	void facebook()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://www.facebook.com/HututuGames"));
		GameRenderer.mContext.startActivity(mIntent);
	}
}
