package com.hututu.game.santafreerunner;
import javax.microedition.khronos.opengles.GL10;


import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.widget.Toast;
public class Group extends Mesh 
{
	GameRenderer mGR = null;
	int Counter =0;
	int SCounter =0;
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
				DrawTexture(gl,mGR.mTex_Skip, .9f,.9f);
			else{
				DrawTexture(gl, mGR.mTex_Hightbar,0.2f,0.9f);
				DrawTexture(gl, mGR.mTex_Pointer,.2f+Counter*(mGR.mTex_Hightbar.width()/100f)-.32f,0.9f);
			}
			break;
		case M.GAMEMENU:
			Draw_Menu(gl);
			break;
		case M.GAMEABOUT:
			DrawBG(gl);
			DrawTexture(gl, mGR.mTex_about2, 0, 0);
			break;
		case M.GAMELOAD:
			float x =-1+Counter/100f,py = 0.8f;
			DrawTexture(gl, mGR.mTex_CPadel[Counter%2], x+.02f,py-.12f);
			DrawTexture(gl, mGR.mTex_Char[SCounter%mGR.mTex_Char.length],x,py);	
			if(Counter>200){
				mGR.gameReset();
				M.GameScreen = M.GAMEPLAY;
				M.play(GameRenderer.mContext, R.drawable.theme);
			}
			DrawTexture(gl, mGR.mTex_Load, 0,.9f);
			break;
		case M.GAMEOVER:
			Draw_GameOver(gl);
		break;	
		default:
			Draw_GamePlay(gl);
			if(M.GameScreen == M.GAMEPAUSE)
				Draw_GamePause(gl);
			break;
		}
		
		Counter++;
		if(Counter%2 == 0)
			SCounter++;
//		setting();
	}
	public boolean TouchEvent(MotionEvent event) 
	{

		switch (M.GameScreen) {
		case M.GAMEABOUT:
			if(MotionEvent.ACTION_UP == event.getAction()){
				M.GameScreen = M.GAMEMENU;
				mm=0;
				M.sound2(GameRenderer.mContext, R.drawable.click);
			}
			break;
		case M.GAMEADD:
			if((MotionEvent.ACTION_UP == event.getAction())&&(Counter>100)&&
					CirCir(0.9f,0.9f, .11f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				M.GameScreen = M.GAMEMENU;
				mm=0;
				M.sound2(GameRenderer.mContext, R.drawable.click);
			}
			break;
		case M.GAMEOVER:
			Handle_GameOver(event);
			break;
		case M.GAMEPAUSE:
			Handle_GamePause(event);
			break;
		case M.GAMEMENU:
			Handle_Menu(event);
			break;
		default:
			Handle_GamePlay(event);
			break;
		}
		Handle(event);
		return true;
	}
	
	/************************************Menu Start******************************************************/
	int mm=0;
	void Draw_Menu(GL10 gl)
	{
		M.Speed = -.02f;
		for(int i=0;i<mGR.mFG.length && mm ==0;i++)
		{
			mGR.mFG[i].set(-1+i*mGR.mTex_GBG[mGR.BG][2].width(), -1.1f,0);
		}
		mm =1;
		DrawBG(gl);
		float x =.5f,py = -.36f;
		mGR.mPlayer.x = x-.05f;
		mGR.mPlayer.y = py;
		mGR.mPlayer.tap = 0;
		Smoke(gl);
		
		DrawTexture(gl, mGR.mTex_CPadel[Counter%2], x+.02f,py-.12f);
		DrawTexture(gl, mGR.mTex_Char[SCounter%mGR.mTex_Char.length],x,py);	
		DrawTexture(gl, mGR.mTex_Splash, 0.0f, .4f);
		DrawTransScal(gl, mGR.mTex_fb	, 0.9f, .84f-(0*.26f),mGR.mSel==1?1.2f:1,mGR.mSel==1?.5f:1);
		DrawTransScal(gl, mGR.mTex_info	, 0.9f, .84f-(1*.26f),mGR.mSel==2?1.2f:1,mGR.mSel==2?.5f:1);
		DrawTransScal(gl, mGR.mTex_gp	, 0.9f, .84f-(2*.26f),mGR.mSel==3?1.2f:1,mGR.mSel==3?.5f:1);
		
		DrawTransScal(gl, mGR.mTex_play	,-.75f,-.40f,mGR.mSel==4?1.2f:1,mGR.mSel==4?.5f:1);
		DrawTransScal(gl, mGR.mTex_HS	,-.75f,-.70f,mGR.mSel==5?1.2f:1,mGR.mSel==5?.5f:1);
		
		
		int i =0;
		
		
		for(i=0;i<mGR.mBG.length;i++){
			mGR.mBG[i].x+=M.Speed/4;
			mGR.mBG[i].y+=M.Speed/2;
		}
		for(i=0;i<mGR.mBG.length;i++)
		{
			if(mGR.mBG[i].x<-2)
				mGR.mBG[i].x = mGR.mBG[i==0?mGR.mBG.length-1:i-1].x+mGR.mTex_GBG[mGR.BG][0].width();
			if(mGR.mBG[i].y<-2)
				mGR.mBG[i].y = mGR.mBG[i==0?mGR.mBG.length-1:i-1].y+mGR.mTex_GBG[mGR.BG][0].width();
		}
		for(i=0;i<mGR.mFG.length;i++)
			mGR.mFG[i].x+=M.Speed;
		
		for(i=0;i<mGR.mFG.length;i++)
		{
			if(mGR.mFG[i].x<-2){
				mGR.mFG[i].set(mGR.mFG[i==0?mGR.mFG.length-1:i-1].x+mGR.mTex_GBG[mGR.BG][2].width(),-1.1f,0);
			}
		}
	}
	boolean Handle_Menu(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(0.9f, .84f-(0*.26f), mGR.mTex_info.width()*.4f, mGR.mTex_info.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 1;//FaceBook
		}
		if(CircRectsOverlap(0.9f, .84f-(1*.26f), mGR.mTex_info.width()*.4f, mGR.mTex_info.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 2;//About
		}
		if(CircRectsOverlap(0.9f, .84f-(2*.26f), mGR.mTex_info.width()*.4f, mGR.mTex_info.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 3;//Google Play
		}
		if(CircRectsOverlap(-.75f,-.40f, mGR.mTex_play.width()*.4f, mGR.mTex_play.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 4;//GamePlay
		}
		if(CircRectsOverlap(-.75f,-.70f, mGR.mTex_HS.width()*.4f, mGR.mTex_HS.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 5;//Achievement
		}
		
		if(MotionEvent.ACTION_UP == event.getAction()){
			switch (mGR.mSel) {
			case 1:
				FaceBook();
				break;
			case 2:
				M.GameScreen = M.GAMEABOUT;
				break;
			case 3:
				Google();//MoreGame();
				break;
			case 4:
				Counter = 0;
				M.GameScreen = M.GAMELOAD;
				break;
			case 5:
				GameRenderer.mStart.onShowLeaderboardsRequested();
//				M.GameScreen = M.GAMEACHIEV;
				break;
			case 6:
				GameRenderer.mStart.Exit();
				break;
			}
			if(mGR.mSel!=0)
				M.sound2(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	/*************************Menu End*************************************************/
	/*************************Game Pause Start*****************************************/
	void Draw_GamePause(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_Cont	, 0.00f, -.30f);
		if(10==mGR.mSel)
			DrawTexture(gl, mGR.mTex_SSmall	, 0.00f, -.30f);
		DrawTexture(gl, mGR.mTex_HS		, 0.00f, -.60f);
		if(11==mGR.mSel)
			DrawTexture(gl, mGR.mTex_SSmall	, 0.00f, -.60f);
		
		DrawTextureS(gl, mGR.mTex_CBG	, .88f, .11f+mGR.pAni,0.64f,1.16f);
		DrawTexture(gl, mGR.mTex_sound[M.setValue?1:0], .88f, .79f-0*.32f+mGR.pAni);
		DrawTexture(gl, mGR.mTex_rate	, .88f, .79f-1*.32f+mGR.pAni);
		DrawTexture(gl, mGR.mTex_help	, .88f, .79f-2*.32f+mGR.pAni);
		DrawTexture(gl, mGR.mTex_share	, .88f, .79f-3*.32f+mGR.pAni);
		DrawTexture(gl, mGR.mTex_home	, .88f, .79f-4*.32f+mGR.pAni);
		
		for(int i=0;i<5;i++){
			if(i==mGR.mSel-1)
			{
				DrawTexture(gl, mGR.mTex_select, .88f, .79f-i*.32f+mGR.pAni);
			}
		}
		mGR.mTex_setting.drawRotet(gl, mGR.pAni*100, 0.88f, -.81f);
		
		
		if(mGR.pAni>0){
			mGR.pvy = 0;
			mGR.pAni =0;
		}
		if(mGR.pAni<-2){
			mGR.pvy = 0;
			mGR.pAni=-2;
		}
		mGR.pAni+=mGR.pvy;
	}
	boolean Handle_GamePause(MotionEvent event)
	{
		mGR.mSel = 0;
		for(int i=0;i<5 && mGR.pAni == 0;i++){
			if(CircRectsOverlap(.88f, .79f-i*.32f+mGR.pAni , mGR.mTex_play.width()*.4f, mGR.mTex_play.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				mGR.mSel = i+1;//
			}
		}
		if(CircRectsOverlap(-.00f, -.3f , mGR.mTex_play.width()*.4f, mGR.mTex_play.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 10;//Continue
		}
		if(CircRectsOverlap(0.00f, -.6f , mGR.mTex_play.width()*.4f, mGR.mTex_play.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 11;//HighScore
		}
		if(CircRectsOverlap(0.88f, -.81f , mGR.mTex_play.width()*.4f, mGR.mTex_play.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 12;//HighScore
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
				M.GameScreen = M.GAMEHELP;
				break;
			case 4:
				share2friend();
				break;
			case 5:
				mm=0;
				M.GameScreen = M.GAMEMENU;
				break;
			case 10:
				M.GameScreen = M.GAMEPLAY;
				M.play(GameRenderer.mContext, R.drawable.theme);
				break;
			case 11:
				GameRenderer.mStart.onShowLeaderboardsRequested();
				break;
			case 12:
				if(mGR.pAni>=0)
					mGR.pvy = -.1f;
				if(mGR.pAni<=-2)
					mGR.pvy = 0.1f;
				break;
			}
			if(mGR.mSel!=0)
				M.sound2(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	/*************************Game Pause End******************************************/
	/*************************Game Over Start******************************************/
	void Draw_GameOver(GL10 gl)
	{
		DrawBG(gl);
		DrawTexture(gl, mGR.mTex_GOver	, 0.00f, 0.80f);
		DrawTexture(gl, mGR.mTex_score	, 0.00f, 0.50f);
		drawNumber (gl, mGR.mScore		, -.07f, 0.50f);
		DrawTexture(gl, mGR.mTex_VSubmit, 0.00f, 0.10f);
		drawNumber (gl, mGR.mHScore		, -.20f, -.03f);
		DrawTexture(gl, mGR.mTex_home	, -.60f, -.50f);
		DrawTexture(gl, mGR.mTex_rate	, 0.00f, -.50f);
		DrawTexture(gl, mGR.mTex_retry	, 0.60f, -.50f);
		
		
		switch (mGR.mSel) {
		case 2:
			DrawTexture(gl, mGR.mTex_SSmall	, -.42f, -.02f);
			break;
		case 1:
			DrawTexture(gl, mGR.mTex_SSmall	, 0.42f, -.02f);
			break;
		case 3:
			DrawTexture(gl, mGR.mTex_select	, -.60f, -.50f);
			break;
		case 4:
			DrawTexture(gl, mGR.mTex_select	, 0.00f, -.50f);
			break;
		case 5:
			DrawTexture(gl, mGR.mTex_select	, 0.60f, -.50f);
			break;
		}
		{
			for(int i=0;i<mGR.mParticle.length;i++){
				mGR.mTex_Particle[i%mGR.mTex_Particle.length].drawRotet(gl, (Counter%360)*10, mGR.mParticle[i].x,mGR.mParticle[i].y);
				mGR.mParticle[i].update();
			}
		}
	}
	boolean Handle_GameOver(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(-.42f, -.02f , mGR.mTex_play.width()*.4f, mGR.mTex_play.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 2;//View
		}
		if(CircRectsOverlap(0.42f, -.02f , mGR.mTex_play.width()*.4f, mGR.mTex_play.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 1;//Submit
		}
		if(CircRectsOverlap(-.60f, -.50f , mGR.mTex_play.width()*.4f, mGR.mTex_play.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 3;//Home
		}
		if(CircRectsOverlap(-.00f, -.50f , mGR.mTex_play.width()*.4f, mGR.mTex_play.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 4;//Rate
		}
		if(CircRectsOverlap(+.60f, -.50f , mGR.mTex_play.width()*.4f, mGR.mTex_play.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 5;//Retry
		}
		if(MotionEvent.ACTION_UP == event.getAction()){
			switch (mGR.mSel) {
			case 1:
				GameRenderer.mStart.Submitscore(R.string.leaderboard_high_score);
				break;
			case 2:
				GameRenderer.mStart.onShowLeaderboardsRequested();
				break;
			case 3:
				mm=0;
				M.GameScreen = M.GAMEMENU;
				break;
			case 4:
				RateUs();
				break;
			case 5:
				Counter = 0;
				M.GameScreen = M.GAMELOAD;
				break;
			}
			if(mGR.mSel!=0)
				M.sound2(GameRenderer.mContext, R.drawable.click);
			mGR.mSel = 0;
		}
		return true;
	}
	/*************************Game Over End******************************************/
	
	/*************************Game Play Start******************************************/
	void gameover()
	{
		M.playStop();
		M.sound1(GameRenderer.mContext, R.drawable.game_over);
		M.GameScreen = M.GAMEOVER;
		mGR.mScore += mGR.gameCont;
		if(mGR.mScore > mGR.mHScore)
			mGR.mHScore = mGR.mScore;
		for(int i=0;i<mGR.mParticle.length;i++){
			float vx = (M.mRand.nextBoolean()?M.mRand.nextFloat():-M.mRand.nextFloat())%.1f;
			mGR.mParticle[i].set(mGR.mPlayer.x, mGR.mPlayer.y,vx,M.mRand.nextFloat()%.1f);
		}
	}
	void gamelogic()
	{
		if(M.Speed >-.06f)
			M.Speed -=.00001f;
		int i =0;
		mGR.gameCont++;
		mGR.mPlayer.vy-=.003f;
		mGR.mPlayer.y+=mGR.mPlayer.vy;
		for(i=0;i<mGR.mBG.length;i++){
			mGR.mBG[i].x+=M.Speed/4;
			mGR.mBG[i].y+=M.Speed/2;
		}
		for(i=0;i<mGR.mBG.length;i++)
		{
			if(mGR.mBG[i].x<-2)
				mGR.mBG[i].x = mGR.mBG[i==0?mGR.mBG.length-1:i-1].x+mGR.mTex_GBG[mGR.BG][0].width();
			if(mGR.mBG[i].y<-2)
				mGR.mBG[i].y = mGR.mBG[i==0?mGR.mBG.length-1:i-1].y+mGR.mTex_GBG[mGR.BG][0].width();
		}
		for(i=0;i<mGR.mFG.length;i++)
			mGR.mFG[i].x+=M.Speed;
		mGR.mPlayer.OnAir =true;
		
		for(i=0;i<mGR.mFG.length;i++)
		{
			if(mGR.mFG[i].x<-2){
				mGR.mFG[i].set(mGR.mFG[i==0?mGR.mFG.length-1:i-1].x+mGR.mTex_GBG[mGR.BG][2].width(),mGR.ay,0);
				if(mGR.mFGcount == mGR.fg){
					mGR.mFG[i].p = 2;
					mGR.ay = mGR.mFG[i].y = -(M.mRand.nextInt(9)/10f)-.6f;
					mGR.mFG[i].x+=.5f+(M.mRand.nextInt(7)/10f);
				}
				mGR.mFGcount--;
				if(mGR.mFGcount == 0){
					int inc = 4;
					mGR.mFG[i].p = 1;
					if(M.Speed <-.05f)
						inc = 8;
					else if(M.Speed <-.04f)
						inc = 6;
					mGR.fg= mGR.mFGcount= M.mRand.nextInt(7)+inc;
				}
			
			}
			if(mGR.mFG[i].x>-1 && mGR.mFG[i].x<-.5f && mGR.mPlayer.vy< 0)
			{
				if(CircRectsOverlap(mGR.mFG[i].x,mGR.mFG[i].y, mGR.mTex_GBG[mGR.BG][2].width()*.5f,mGR.mTex_GBG[mGR.BG][2].Height()*.7f,mGR.mPlayer.x, mGR.mPlayer.y, .08f))
				{
					if(mGR.mFG[i].y+mGR.mTex_GBG[mGR.BG][2].Height()*.5f>mGR.mPlayer.y)
						gameover();
					mGR.mPlayer.y = mGR.mFG[i].y+mGR.mTex_GBG[mGR.BG][2].Height()*.7f;
					mGR.mPlayer.setZero();
				}
			}
		}
		for(i=0;i<mGR.mGift.length;i++)
		{
			if (mGR.mGift[i].x<-1.1f)
			{
				float x = mGR.mGift[i==0?mGR.mGift.length-1:i-1].x+1.5f;
				mGR.mGift[i].set(x, mGR.setGift(x)+mGR.mTex_GBG[0][2].Height()*.8f*((M.mRand.nextInt(6)*.1f)+1), M.mRand.nextInt(12));
				mGR.GCount++;
			}
			if(mGR.mGift[i].no<12 &&CircRectsOverlap(mGR.mGift[i].x,mGR.mGift[i].y, mGR.mTex_Gift[0].width()*.5f,mGR.mTex_Gift[0].Height()*.6f,mGR.mPlayer.x, mGR.mPlayer.y, .08f))
			{
				mGR.mScore += 100+mGR.mGift[i].no*3;
				if(mGR.mScore>mGR.mHScore)
					mGR.mHScore = mGR.mScore;
				mGR.mGift[i].no = 13;
				setAnimation(mGR.mGift[i].x,mGR.mGift[i].y);
				M.sound3(GameRenderer.mContext, R.drawable.gift);
			}
			mGR.mGift[i].x+=M.Speed;
		}
		if(mGR.mPlayer.y<-.9f)
			gameover();
		
	}
	void DrawBG(GL10 gl)
	{
		float fy = (mGR.mPlayer.y<.8)?0:(.8f-mGR.mPlayer.y);
		for(int i=0;i<mGR.mBG.length;i++){
			DrawTexture(gl, mGR.mTex_GBG[mGR.BG][0], mGR.mBG[i].x, 0.61f+fy/4);
		}
		for(int i=0;i<mGR.mBG.length;i++){
			DrawTexture(gl, mGR.mTex_GBG[mGR.BG][1], mGR.mBG[i].y, -.40f+fy/2);
		}
		for(int i=0;i<mGR.mFG.length;i++){
			if(mGR.mFG[i].x < 1.5f){
				DrawTexture(gl, mGR.mTex_GBG[mGR.BG][2], mGR.mFG[i].x, mGR.mFG[i].y+fy);
				if(mGR.mFG[i].p == 2)
					DrawTexture(gl, mGR.mTex_GBG[mGR.BG][3], mGR.mFG[i].x-mGR.mTex_GBG[mGR.BG][2].width()*.5f, mGR.mFG[i].y+fy);
				if(mGR.mFG[i].p == 1)
					DrawTexture(gl, mGR.mTex_GBG[mGR.BG][4], mGR.mFG[i].x+mGR.mTex_GBG[mGR.BG][2].width()*.5f, mGR.mFG[i].y+fy);
			}
		}
	}
	void Draw_GamePlay(GL10 gl)
	{
		
		DrawBG(gl);
		for(int i=0;i<mGR.mGift.length;i++)
		{
			if (mGR.mGift[i].x>-1.1f && mGR.mGift[i].x<1.1f && mGR.mGift[i].no<12) {
				mGR.mTex_Effect[0].drawRotet(gl, (Counter%360)*10, mGR.mGift[i].x,mGR.mGift[i].y);
				mGR.mTex_Effect[0].drawRotet(gl,-(Counter%360)*10, mGR.mGift[i].x,mGR.mGift[i].y);
				DrawTexture(gl, mGR.mTex_Gift[mGR.mGift[i].no], mGR.mGift[i].x,mGR.mGift[i].y);
			}
		}
		if(M.GameScreen != M.GAMEOVER && M.GameScreen != M.GAMEPAUSE){
			float py = (mGR.mPlayer.y<.8)?mGR.mPlayer.y:.8f;
			switch (mGR.mPlayer.ani) {
			case 6:
				if(Counter*10<360)
					mGR.mTex_Rotate.drawRotet(gl, (Counter%360)*10, mGR.mPlayer.x,py);
				else
					mGR.mTex_Rotate.drawRotet(gl, 0, mGR.mPlayer.x,py);
				break;
			case 5:
				DrawTexture(gl, mGR.mTex_CPadel[Counter%2], mGR.mPlayer.x+.04f,py-.15f);
				DrawTexture(gl, mGR.mTex_Handle[Counter%mGR.mTex_Handle.length], mGR.mPlayer.x+.12f,py-.03f);
				DrawTexture(gl, mGR.mTex_HFChar[SCounter%mGR.mTex_HFChar.length], mGR.mPlayer.x,py);
				break;
			case 4:
				DrawTexture(gl, mGR.mTex_CPadel[Counter%2], mGR.mPlayer.x+.04f,py-.15f);
				DrawTexture(gl, mGR.mTex_Handle[0], mGR.mPlayer.x+.12f,py-.03f);
				DrawTexture(gl, mGR.mTex_HFChar[SCounter%mGR.mTex_HFChar.length], mGR.mPlayer.x,py);
				break;
			case 3:
				PadelAni(gl, mGR.mPlayer.x+.02f,py-.16f);
				DrawTexture(gl, mGR.mTex_FChar[SCounter%mGR.mTex_FChar.length], mGR.mPlayer.x,py);
				break;
			case 2:
				DrawTexture(gl, mGR.mTex_CPadel[Counter%2], mGR.mPlayer.x+.02f,py-.15f);
				DrawTexture(gl, mGR.mTex_FChar[SCounter%mGR.mTex_FChar.length], mGR.mPlayer.x,py);
				break;
			case 1:
				PadelAni(gl, mGR.mPlayer.x+.02f,py-.12f);
				DrawTexture(gl, mGR.mTex_Char[0], mGR.mPlayer.x,py);
				break;
			default:
				DrawTexture(gl, mGR.mTex_CPadel[Counter%2], mGR.mPlayer.x+.02f,py-.12f);
				if(mGR.mPlayer.tap == 0)
					DrawTexture(gl, mGR.mTex_Char[SCounter%mGR.mTex_Char.length], mGR.mPlayer.x,py);
				else
					DrawTexture(gl, mGR.mTex_Char[0], mGR.mPlayer.x,py);
				break;
			}
			gamelogic();
			Smoke(gl);
		}
		else{
			for(int i=0;i<mGR.mParticle.length;i++){
				mGR.mTex_Particle[i%mGR.mTex_Particle.length].drawRotet(gl, (Counter%360)*10, mGR.mParticle[i].x,mGR.mParticle[i].y);
				mGR.mParticle[i].update();
			}
		}
		drawAnimation(gl);
			
	}
	void Smoke(GL10 gl)
	{
		for(int i=0;i<mGR.mSmoke.length;i++)
		{
			if(mGR.mSmoke[i].i<mGR.mTex_Smok.length){
				DrawTexture(gl, mGR.mTex_Smok[mGR.mSmoke[i].i%mGR.mTex_Smok.length], mGR.mSmoke[i].x-.05f,mGR.mSmoke[i].y-.21f);
				if(Counter%2==0)
					mGR.mSmoke[i].i++;
			}
			mGR.mSmoke[i].x-=.01f;
			mGR.mSmoke[i].y+=.005f;
		}
		for(int i=0;i<mGR.mSmoke.length&&Counter%4==0&&mGR.mPlayer.tap == 0;i++)
		{
			if(mGR.mSmoke[i].i>=mGR.mTex_Smok.length){
				mGR.mSmoke[i].set(mGR.mPlayer.x, mGR.mPlayer.y, 0);
				break;
			}
		}
	}
	boolean Handle_GamePlay(MotionEvent event)
	{
		if(MotionEvent.ACTION_UP == event.getAction()){
			if(mGR.mPlayer.tap < 2)
			{
				if(mGR.mPlayer.tap == 1){
					if(mGR.mPlayer.vy > .01f){
						mGR.mPlayer.vy = .07f;
						mGR.mPlayer.ani = M.mRand.nextInt(7);
						Counter = 0;
						mGR.mPlayer.tap++;
						M.sound6(GameRenderer.mContext, R.drawable.jump1);
					}
				}
				else if(!mGR.mPlayer.OnAir){
					mGR.mPlayer.vy = .05f;
					mGR.mPlayer.tap++;
					M.sound4(GameRenderer.mContext, R.drawable.jump0);
				}
			}
		}
		return true;
	}
	void PadelAni(GL10 gl,float x,float y)
	{
		switch (Counter%5) {
		case 4:
			DrawTexture(gl, mGR.mTex_CPadel[4], .08f+x,y);
			break;
		case 3:
			DrawTexture(gl, mGR.mTex_CPadel[3], .16f+x,y);
			break;
		case 2:
			DrawTexture(gl, mGR.mTex_CPadel[2],0.08f+x,y);
			break;
		case 1:
			DrawTexture(gl, mGR.mTex_CPadel[1], x,y);
			break;
		default:
			DrawTexture(gl, mGR.mTex_CPadel[0], x,y);
			break;
		}
	}
	/*************************Game Play End******************************************/
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
		float dx = mGR.mTex_Font[0].width()*.7f;
		 String strs = ""+no;
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i))-48;
			if(k>=0 && k<mGR.mTex_Font.length)
				mGR.mTex_Font[k].drawPos(gl,x+i*dx,y);
		}
	}
	void FaceBook()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://www.facebook.com/HututuGames"));
	    GameRenderer.mContext.startActivity(mIntent);
	}
	void Google()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://plus.google.com/101161010890539846728"));
	    GameRenderer.mContext.startActivity(mIntent);
	}
	void RateUs()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
//		mIntent.setData(Uri.parse(M.LINK+getClass().getPackage().getName()));
		mIntent.setData(Uri.parse(M.MARKET));
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
		i.putExtra(Intent.EXTRA_SUBJECT,"Rocking new Game '"+GameRenderer.mContext.getResources().getString(R.string.app_name)+"'");
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
		for(int i=0;i<mGR.ani.length&& cr<50;i++)
		{
			if(mGR.ani[i].x<-1.1||mGR.ani[i].x>1.1||mGR.ani[i].y<-1.1||mGR.ani[i].y>1.1)
			{
				mGR.ani[i].set(x, y);
				if(cr<3)
					mGR.ani[i].r=mGR.ani[i].g=mGR.ani[i].b=1;
				else if(cr<6){
					mGR.ani[i].r=mGR.ani[i].g=1;
					mGR.ani[i].b=0;
				}
				cr++;
			}
		}
	}
	void drawAnimation(GL10 gl)
	{
		for(int i=0;i<mGR.ani.length;i++)
		{
			if(mGR.ani[i].x>-1.2&&mGR.ani[i].x<1.2&&mGR.ani[i].y>-1.2&&mGR.ani[i].y<1.2)
			{
				mGR.mTex_8x8.drawRGBS(gl, mGR.ani[i].x, mGR.ani[i].y, mGR.ani[i].r, mGR.ani[i].g, mGR.ani[i].b, mGR.ani[i].t, mGR.ani[i].scal,(Counter%360)*20);
				mGR.ani[i].update();
			}
		}
	}
}
