package com.hututu.game.BoomBaseball;
import javax.microedition.khronos.opengles.GL10;
import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.widget.Toast;
public class Group extends Mesh 
{
	GameRenderer mGR = null;
	int Counter =0,Counter2=0;
	float PowerCnt=0;
	int Score=0;
	float PopY= 1.5f;
	float PopVY=-.05f;
	int mPage=0;
	boolean isDown = false;
	final int Check=100;
	float sx=0,sy=0;
	float mx,mdx;
	public void setting(){float ud=.01f;switch (GameRenderer.mStart._keyCode) {case 1:sy-=ud;break;case 2:sy+=ud;break;case 3:sx-=ud;break;case 4:sx+=ud;break;}
	     System.out.println(M.GameScreen+"~~~~~~~~~~~~~~~      "+sx+"~~~~~~~~~~~~       "+sy);
	}
	public boolean Handle(MotionEvent event){
		if(CircRectsOverlap(-.7f,0.0f,.2f, .2f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 3;
		if(CircRectsOverlap(0.7f,0.0f,.2f, .2f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 4;
		if(CircRectsOverlap(-.0f,-.7f,.2f, .2f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 1;
		if(CircRectsOverlap(0.0f,0.7f,.2f, .2f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 2;
		if(event.getAction()== MotionEvent.ACTION_UP)GameRenderer.mStart._keyCode = 0;
		return true;
	}
	public Group(GameRenderer _GameRenderer){
		mGR = _GameRenderer;
	}
	
	public void draw(GL10 gl) 
	{
	  Counter++;
	  switch (M.GameScreen)
	   {
		 case Check:
			break;
		 case M.GAMELOGO:
			 DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if(Counter>80)
			{
				if(!GameRenderer.mStart.addFree)
			       M.GameScreen = M.GAMELOAD;
				else
				{
				   M.GameScreen = M.GAMESPLASH;
				   M.SplashPlay(GameRenderer.mContext,R.drawable.theme);
				}
				Counter =0;
			}
			break;
		case M.GAMELOAD:
			if(Counter>100)
				DrawTexture(gl,mGR.mTex_Exit, .9f,.9f);
			else{
				DrawTexture(gl, mGR.mTex_LoadBar,0.0f,-.9f);
				DrawTexture(gl, mGR.mTex_Pointer,Counter*(mGR.mTex_LoadBar.width()/100f)-.32f,-.9f);
			}
			break;
		case M.GAMEADD:
			 DrawTexture(gl,mGR.mTex_Exit, .9f,.9f);
			break;
		case M.GAMESPLASH:
			DrawTexture(gl,mGR.mTex_Splash,0,0);
			if(Counter>60)
			{
				M.GameScreen = M.GAMEMENU;
				Counter=0;
			}
			break;
		case M.GAMEMENU:
			DrawMenu(gl);
			break;
		case M.GAMEACHIV:
			DrawAchiveMent(gl);
			break;
		case M.GAMEOVER:
		case M.GAMEWIN:
			DrawScorBoard(gl);
			break;
		case M.GAMESETTING:
			DrawSetting(gl);
			break;
		case M.GAMEHELP:
		case M.GAMEABOUT:
			DrawAbtHelp(gl);
			break;
		case M.GAMEPAUSE:
			DrawPause(gl);
			break;
		case M.GAMEPLAY:
		case M.GAMETARGET:	
			DrawGamePlay(gl);
			break;
		}
//		setting();
	}
	public boolean TouchEvent(MotionEvent event) 
	{
	 switch (M.GameScreen){
		case M.GAMELOGO:
			break;
		case Check:
			break;
		case M.GAMELOAD:
			if((MotionEvent.ACTION_UP == event.getAction())&&(Counter>100)
			  && CirCir(0.9f,.9f, .11f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			  {
				
				M.GameScreen = M.GAMESPLASH;
				Counter=0;
				M.SplashPlay(GameRenderer.mContext,R.drawable.theme);
			  }
			break;
		case M.GAMEADD:
			if((MotionEvent.ACTION_UP == event.getAction())
			  && CirCir(0.9f,.9f, .11f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			  {
				if(mGR.mLevelWindow>=mGR.mTargetWindow) 
				  M.GameScreen = M.GAMEWIN;
				 else
				  M.GameScreen = M.GAMEOVER;
			  }
			break;
		case M.GAMEMENU:
			HandleMenu(event);
			break;
		case M.GAMEACHIV:
			HandleAchiveMent(event);
			break;
		case M.GAMESETTING:
			HandleSetting(event);
			break;
		case M.GAMEHELP:
		case M.GAMEABOUT:
			HandleAbtHelp(event);
			break;
		case M.GAMEPAUSE:
			HandlePause(event);
			break;
		case M.GAMEOVER:
		case M.GAMEWIN:	
			HandleScoboard(event);
			break;
		case M.GAMEPLAY:
			HandleGame(event);
			break;
		}
	   if(mGR.mSel!=0 && event.getAction() == MotionEvent.ACTION_DOWN)
		   M.ClickSound(GameRenderer.mContext,R.drawable.click);
	   
		Handle(event);
		return true;
	}
void DrawMenu(GL10 gl)
{
	 DrawTexture(gl,mGR.mTex_Splash,0,0);
	 DrawTexture(gl,mGR.mTex_HighScore,-.889f,-.879f);
	 DrawTexture(gl,mGR.mTex_Position,-.679f,-.879f);
	 DrawTexture(gl,mGR.mTex_More,.679f,-.879f);
	 DrawTexture(gl,mGR.mTex_Setting,.889f,-.879f);
	 DrawTransScal(gl,mGR.mTex_Play,0,-.659f,mGR.mSel==1?1.3f:1,mGR.mSel==1?.5f:1);
	 switch(mGR.mSel)
	 {
	 	case 2:
	 		DrawTexture(gl,mGR.mTex_IcnSel,-.889f,-.879f);
	 		break;
	 	case 3:
	 		DrawTexture(gl,mGR.mTex_IcnSel,-.679f,-.879f);
	 		break;
	 	case 4:
	 		DrawTexture(gl,mGR.mTex_IcnSel,.679f,-.879f);
	 		break;
	 	case 5:
	 		DrawTexture(gl,mGR.mTex_IcnSel,.889f,-.879f);
	 		break;
	 }
	 if(mGR.isJoin)
    	 DrawJoin(gl);
}
boolean HandleMenu(MotionEvent event)
{
	float dx = mGR.mTex_More.width()*.4f;
	float dy = mGR.mTex_More.Height()*.4f;
	mGR.mSel=0;
	if(mGR.isJoin)
		return HandleJoin(event);
	if(CircRectsOverlap(0,-.659f,2*dx,2*dy,screen2worldX(event.getX()),screen2worldY(event.getY()),.045f))
	{
	  mGR.mSel=1; //play
	}
	if(CircRectsOverlap(-.889f,-.879f, dx, dy,screen2worldX(event.getX()),screen2worldY(event.getY()),.045f))
	{
	  mGR.mSel=2; //HighScore
	}
	if(CircRectsOverlap(-.679f,-.879f, dx, dy,screen2worldX(event.getX()),screen2worldY(event.getY()),.045f))
	{
	  mGR.mSel=3; //LeaderBoard   		
	}
	if(CircRectsOverlap(.679f,-.879f, dx, dy,screen2worldX(event.getX()),screen2worldY(event.getY()),.045f))
	{
	  mGR.mSel=4; // More   		
	}
	if(CircRectsOverlap(.889f,-.879f, dx, dy,screen2worldX(event.getX()),screen2worldY(event.getY()),.045f))
	{
	  mGR.mSel=5; //Setting   		
	}
	if(event.getAction() == MotionEvent.ACTION_UP)
	{
		switch(mGR.mSel)
		{
		  case 1:
			  mGR.GameReset();
			break;
		  case 2:
			  mGR.isJoin = false;
			  if(!GameRenderer.mStart.isSignedIn())
				  mGR.isJoin = true;
			  M.GameScreen = M.GAMEACHIV;
			break;
		  case 3:
			   mGR.isJoin = false;
			  if(GameRenderer.mStart.isSignedIn())
				   GameRenderer.mStart.onShowLeaderboardsRequested();
			  else
				  mGR.isJoin = true;
			  break; 
		  case 4:
			  MoreGame();
			break;
		  case 5:
			  M.GameScreen = M.GAMESETTING;
			break;
	 		
		}
		if(mGR.mSel!=0)
			M.StopSound();
		mGR.mSel=0;
	}
  return true;	
}
void DrawSetting(GL10 gl)
{
	 DrawTexture(gl,mGR.mTex_Splash,0,0);
	 DrawTexture(gl,mGR.mTex_PopUp[0],0,0);
	 DrawTexture(gl,mGR.mTex_Music,-.339f,.26f);
	 DrawTexture(gl,mGR.mTex_SoundIcn[M.SetBG?0:1],-.339f+.3f,.26f);
	 DrawTexture(gl,mGR.mTex_Sound,-.339f,-.26f);
	 DrawTexture(gl,mGR.mTex_SoundIcn[M.setValue?0:1],-.339f+.3f,-.26f);
	 DrawTexture(gl,mGR.mTex_AbtUs,.339f,.25f);
	 DrawTexture(gl,mGR.mTex_Help,.339f,-.25f);
	 DrawTexture(gl,mGR.mTex_BackBtn,-.879f,-.869f);
	 DrawTexture(gl,mGR.mTex_Position,.679f,-.879f);
	 DrawTexture(gl,mGR.mTex_HighScore,.889f,-.879f);
	 switch(mGR.mSel)
	 {
	  case 1:
		  DrawTexture(gl,mGR.mTex_BtnSel,-.339f,.26f);
		  DrawTexture(gl,mGR.mTex_IcnSel,-.339f+.3f,.26f);
		  break;
	  case 2:
		  DrawTexture(gl,mGR.mTex_BtnSel,-.339f,-.26f);
		  DrawTexture(gl,mGR.mTex_IcnSel,-.339f+.3f,-.26f);
		  break;
	  case 3:
		  DrawTexture(gl,mGR.mTex_BtnSel,.339f,.25f);
		  break;
	  case 4:
		  DrawTexture(gl,mGR.mTex_BtnSel,.339f,-.25f);
		  break;
	  case 5:
		  DrawTexture(gl,mGR.mTex_BackBtnSel,-.879f,-.869f);
		  break;
	  case 6:
	 	  DrawTexture(gl,mGR.mTex_IcnSel,.679f,-.879f);
	 	  break;
	  case 7:
	 	  DrawTexture(gl,mGR.mTex_IcnSel,.889f,-.879f);
	 	  break;
	   
	 }
	 if(mGR.isJoin)
		DrawJoin(gl);
}
boolean HandleSetting(MotionEvent event)
{
	float dx = mGR.mTex_Music.width();
	float dy = mGR.mTex_Music.Height()*.4f;
	mGR.mSel=0;
	if(mGR.isJoin)
		return HandleJoin(event);
	if(CircRectsOverlap(-.339f,.26f, dx, dy,screen2worldX(event.getX()),screen2worldY(event.getY()),.045f))
	{
	  mGR.mSel=1; //Mucic
	}
	if(CircRectsOverlap(-.339f,-.26f, dx, dy,screen2worldX(event.getX()),screen2worldY(event.getY()),.045f))
	{
	  mGR.mSel=2; // Sound   		
	}
	if(CircRectsOverlap(.339f,.25f, dx/2f, dy,screen2worldX(event.getX()),screen2worldY(event.getY()),.045f))
	{
	  mGR.mSel=3; //About   		
	}
	if(CircRectsOverlap(.339f,-.25f, dx/2f, dy,screen2worldX(event.getX()),screen2worldY(event.getY()),.045f))
	{
	  mGR.mSel=4; //Help   		
	}
	if(CircRectsOverlap(-.879f,-.869f, dx/4f, dy/4,screen2worldX(event.getX()),screen2worldY(event.getY()),.045f))
	{
	  mGR.mSel=5; //Back   		
	}
	if(CircRectsOverlap(.679f,-.879f,mGR.mTex_IcnSel.width()*.4f,mGR.mTex_IcnSel.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.045f))
	{
	  mGR.mSel=6; //LeaderBoard   		
	}
	if(CircRectsOverlap(.889f,-.879f,mGR.mTex_IcnSel.width()*.4f,mGR.mTex_IcnSel.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.045f))
	{
	  mGR.mSel=7; //HighScore
	}
	
	if(event.getAction() == MotionEvent.ACTION_UP)
	{
		switch(mGR.mSel)
		{
		  case 1:
			  M.SetBG = !M.SetBG;
			break;
		  case 2:
			  M.setValue = !M.setValue;
			  break;
		  case 3:
			  M.GameScreen = M.GAMEABOUT;
			  break;
		  case 4:
			  M.GameScreen = M.GAMEHELP;
			  break;
		  case 5:
			  M.GameScreen = M.GAMEMENU;
			  break; 
		  case 6:	  
			   mGR.isJoin = false;
			  if(GameRenderer.mStart.isSignedIn())
				   GameRenderer.mStart.onShowLeaderboardsRequested();
			  else
				 mGR.isJoin = true;
		  case 7:
			   mGR.isJoin =false;
			  if(GameRenderer.mStart.isSignedIn()){
				  GameRenderer.mStart.onShowAchievementsRequested();
				}
			  else
				mGR.isJoin = true;
			  break;
		} 
		if(M.GameScreen == M.GAMEMENU)
		   M.SplashPlay(GameRenderer.mContext,R.drawable.theme);
		mGR.mSel=0;
	}
	return true;
}
void DrawAbtHelp(GL10 gl)
{
	DrawTexture(gl,mGR.mTex_Splash,0,0);
	if(M.GameScreen == M.GAMEABOUT)
	DrawTexture(gl,mGR.mTex_AbtTxt,0,-.4f);
	if(M.GameScreen == M.GAMEHELP)
	DrawTexture(gl,mGR.mTex_Helptxt,0,0);
	DrawTexture(gl,mGR.mTex_BackBtn,.9f,-.86f);
	if(mGR.mSel ==1)
		DrawTexture(gl,mGR.mTex_BackBtnSel,.9f,-.86f);
}
boolean HandleAbtHelp(MotionEvent event)
{
	mGR.mSel =0;
	if(CircRectsOverlap(.9f,-.86f,mGR.mTex_BackBtnSel.width()*.5f,mGR.mTex_BackBtnSel.Height()*.5f,screen2worldX(event.getX()),screen2worldY(event.getY()),.045f))
	{
	  mGR.mSel=1; //Back   		
	}
	if(event.getAction() == MotionEvent.ACTION_UP)
	{
		if(mGR.mSel ==1)
		{
			M.GameScreen = M.GAMESETTING;
			mGR.mSel =0;
		}
	}
	return true;
}
void DrawScorBoard(GL10 gl)
{
	float py=.25f;
	float px= 0;
	float dy=.15f;
	DrawTexture(gl,mGR.mTex_Splash,0,0);
	DrawTransScal(gl,mGR.mTex_AddFree,.329f,-.621f,mGR.mSel==10?1.2f:1,mGR.mSel==10?.5f:1);
	DrawTexture(gl,mGR.mTex_PopUp[1],0,0);
	DrawTexture(gl,mGR.mTex_ScoreBoard,0,.459f);
	DrawTexture(gl,mGR.mTex_TargetWindow,px,py);
	DrawTexture(gl,mGR.mTex_BonusShot,px,py-dy);
	DrawTexture(gl,mGR.mTex_ExtraShot,px,py-dy*2);
	DrawTexture(gl,mGR.mTex_PrevScore,px,py-dy*3);
	DrawTexture(gl,mGR.mTex_NewScore,px,py-dy*4);
	px =.15f;

	if(mGR.NoCount<mGR.mSboardAni.length)
	  mGR.mSboardAni[mGR.NoCount].updateScoreBoard();
 	 DrawNumberScal(gl,mGR.mSboardAni[0].Score,px,py,mGR.mSboardAni[0].s,1);
	if(mGR.NoCount>0)
		DrawNumberScal(gl,mGR.mSboardAni[1].Score,px,py-dy,mGR.mSboardAni[1].s,1);
	if(mGR.NoCount>1)
		DrawNumberScal(gl,mGR.mSboardAni[2].Score,px,py-2*dy,mGR.mSboardAni[2].s,1);
	if(mGR.NoCount>2)
		DrawNumberScal(gl,mGR.mSboardAni[3].Score,px,py-3*dy,mGR.mSboardAni[3].s,1);
	if(mGR.NoCount>3)
		DrawNumberScal(gl,mGR.mSboardAni[4].Score,px,py-4*dy,mGR.mSboardAni[4].s,1);
	
	SimplePlane Tex=null;
	if(M.GameScreen == M.GAMEWIN)
		Tex = mGR.mTex_Continue;
	else
		Tex = mGR.mTex_Retry;
	DrawTexture(gl,Tex,-.07f,-.66f);
	DrawTexture(gl,mGR.mTex_Home,-.5f,-.449f);
	switch(mGR.mSel)
	{
	  case 1:
		  DrawTexture(gl,mGR.mTex_BtnSel,-.07f,-.66f);
		  break;
	  case 2:
		  DrawTexture(gl,mGR.mTex_BackBtnSel,-.5f,-.449f);
		  break;
	}
	
}
boolean HandleScoboard(MotionEvent event)
{
	float dx = mGR.mTex_Home.width()*.5f;
	float dy = mGR.mTex_Home.Height()*.35f;
	mGR.mSel=0;
	if(CircRectsOverlap(-0.07f,-.66f,dx*1.3f,dy,screen2worldX(event.getX()),screen2worldY(event.getY()),.045f))
	{
	  mGR.mSel=1; //Retry   		
	}
	if(CircRectsOverlap(-.5f,-.449f,dx,dy,screen2worldX(event.getX()),screen2worldY(event.getY()),.045f))
	{
	  mGR.mSel=2; //Home   		
	}
	if(CircRectsOverlap(.329f,-.621f,mGR.mTex_AddFree.width()*.5f,mGR.mTex_AddFree.Height()*.5f,screen2worldX(event.getX()),screen2worldY(event.getY()),.045f))
	{
	  mGR.mSel=10; //AdsFree  		
	}
	if(event.getAction() == MotionEvent.ACTION_UP)
	{
		switch(mGR.mSel)
		{
		  case 1:
			   if(M.GameScreen == M.GAMEWIN)
				   mGR.mLevel++;
			   mGR.GameReset();
			   System.out.println("In mlevel================    "+mGR.mLevel);
			break;
		  case 2:
			   M.GameScreen = M.GAMEMENU;   
			break;
		  case 10:
			  mGR.mMainActivity.onBuyHTT_REMOVE_ADS(null);
			  break;
		}
		if(M.GameScreen == M.GAMEMENU)
		   M.SplashPlay(GameRenderer.mContext,R.drawable.theme);
		mGR.mSel=0;
	}
	return true;
}
void DrawAchiveMent(GL10 gl)
{
		DrawTexture(gl,mGR.mTex_Splash,0,0);
		DrawTexture(gl,mGR.mTex_PopUp[1],0,0);
		DrawTexture(gl,mGR.mTex_AchiveTxt,0,.439f);
		if(((mx >0 && mPage==0)||(mx <0 && mPage==2))&&!isDown){
			float mm = mx >0 ? -2:2;
			mx+=mm*.1f;
			if(Math.abs(mx)<.2f){
				mx =0;
			}
		}
		for(int i=0;i<8 && i+mPage*8<mGR.mTex_Achive.length;i++)
		{
		  if(mGR.mSel-1 == i+mPage*8)
	       DrawTextureS(gl, mGR.mTex_Achive[i+mPage*8],mx-.365f+(i%4)*.25f,.15f-(i/4)*.35f,1.2f);
			  
		  if(M.isAchive[i+mPage*8])
			  DrawTransScal(gl, mGR.mTex_Achive[i+mPage*8],mx-.365f+(i%4)*.25f,.15f-(i/4)*.35f,1f,1f);
		  else
			  DrawTransScal(gl, mGR.mTex_Achive[i+mPage*8],mx-.365f+(i%4)*.25f,.15f-(i/4)*.35f,1f,.5f);
		}
		if((mx >0 && mPage>0)||(mx <0 && mPage<2)){
			float mm = mx >0 ? -2:2;
			for(int i=0;i<8 && i+mPage*8<mGR.mTex_Achive.length;i++)
			{
				if(mx<0){
					if(M.isAchive[i+mPage*8])
						DrawTransScal(gl,mGR.mTex_Achive[i+mPage*8],mm+mx-.365f+(i%4)*.25f,.15f-(i/4)*.35f,1f,1f);
					else
						DrawTransScal(gl,mGR.mTex_Achive[i+mPage*8],mm+mx-.365f+(i%4)*.25f,.15f-(i/4)*.35f,1f,1f);
				}
				else
				{
					if(M.isAchive[i+mPage*8])
					   DrawTransScal(gl, mGR.mTex_Achive[i+mPage*8],mm+mx-.365f+(i%4)*.25f,.15f-(i/4)*.35f,1f,1f);
					else
					   DrawTransScal(gl, mGR.mTex_Achive[i+mPage*8],mm+mx-.365f+(i%4)*.25f,.15f-(i/4)*.35f,1f,.5f);
				}
			}
			if(!isDown)
				mx-=mm*.1f;
			if(Math.abs(mx)>2){
				mx =0;
				mPage+=mm/2;
			}
		}
		for(int i=0;i<3;i++)
		{
			if(mPage==i)
				DrawTexture(gl,mGR.mTex_Dot1, -.1f+i*.09f, -.45f);
			else
				DrawTexture(gl, mGR.mTex_Dot0,-.1f+i*.09f, -.45f);
		}
	  	 DrawTexture(gl,mGR.mTex_Home,-.5f,-.469f);
		if(mGR.mSel ==100)
		 DrawTexture(gl,mGR.mTex_BackBtnSel,-.5f,-.469f);
		DrawTexture(gl,mGR.mTex_Position,-0.679f,-.879f);
		DrawTexture(gl,mGR.mTex_HighScore,-.889f,-.879f);
		switch(mGR.mSel)
		{
		  case 101:
	 		DrawTexture(gl,mGR.mTex_IcnSel,-.679f,-.879f);
	 		break;
	 	  case 102:
	 		DrawTexture(gl,mGR.mTex_IcnSel,-.889f,-.879f);
	 		break;
		}
		if(mGR.isJoin)
			DrawJoin(gl);
}
boolean HandleAchiveMent(MotionEvent event)
{
	mGR.mSel=0;
	if(mGR.isJoin)
		return HandleJoin(event);
	for(int i=0;i<8 && i+mPage*8<mGR.mTex_Achive.length;i++)
	{
	  	if(CircRectsOverlap(mx-.365f+(i%4)*.25f,.15f-(i/4)*.35f,mGR.mTex_Achive[0].width()*.5f, mGR.mTex_Achive[0].Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()),.045f))
	  	{
	  		mGR.mSel = i+1+mPage*8;
	     	System.out.println("msel===============    "+mGR.mSel);
	  	}
	}
	if(CircRectsOverlap(-.5f,-.469f,mGR.mTex_BackBtn.width()*.5f, mGR.mTex_BackBtn.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()),.045f))
	{
		mGR.mSel=100; //Home
	}
	if(CircRectsOverlap(-.679f,-.879f,mGR.mTex_BackBtn.width()*.5f, mGR.mTex_BackBtn.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()),.045f))
	{
		mGR.mSel=101; //LeaderBoard
	}
	if(CircRectsOverlap(-.889f,-.879f,mGR.mTex_BackBtn.width()*.5f, mGR.mTex_BackBtn.Height()*.5f, screen2worldX(event.getX()), screen2worldY(event.getY()),.045f))
	{
		mGR.mSel=102; //Achivement
	}
	if(MotionEvent.ACTION_DOWN == event.getAction())
	{
		mdx = screen2worldX(event.getX());
		isDown = true;
	}
	mx = screen2worldX(event.getX())-mdx;
	if(MotionEvent.ACTION_UP == event.getAction())
	{
		if(Math.abs(mx) < .10)
			mx =0;
		isDown = false;
		if(mGR.mSel == 100)
		{
			M.GameScreen = M.GAMEMENU;
			mGR.mSel=0;
			mPage=0;
		}
		if(mGR.mSel>0 && mGR.mSel<100 && mx == 0)
		{
			GameRenderer.mStart.ShowAchiveMsg(mGR.mSel-1,mGR.mSel-1);
		}
		switch(mGR.mSel)
		{
		   case 101:
			  mGR.isJoin =false;
			  if(GameRenderer.mStart.isSignedIn())
				   GameRenderer.mStart.onShowLeaderboardsRequested();
			  else
				  mGR.isJoin = true;
			  break;
		   case 102:
			  mGR.isJoin =false;
			  if(GameRenderer.mStart.isSignedIn()){
					GameRenderer.mStart.onShowAchievementsRequested();
				}
			  else
				mGR.isJoin = true;    
			break;
		  
		}
		if(M.GameScreen == M.GAMEMENU)
		   M.SplashPlay(GameRenderer.mContext,R.drawable.theme);
		if(mx == 0)
	  	  mGR.mSel=0;
	}
	return true;
}
void DrawPause(GL10 gl)
{
	 DrawTexture(gl,mGR.mTex_Splash,0,0);
	 
	 DrawTexture(gl,mGR.mTex_PopUp[1],0,0);
	 DrawTexture(gl,mGR.mTex_PauseTxt,0,.459f);
	 
	 DrawTexture(gl,mGR.mTex_Continue,0,.24f);
	 DrawTexture(gl,mGR.mTex_HighScore,-.29f,0);
	 DrawTexture(gl,mGR.mTex_Position,-.29f,-.30f);
	 DrawTransScal(gl,mGR.mTex_SoundIcn[M.setValue?0:1],.29f,0,1,M.setValue?1:.5f);
	 DrawTransScal(gl,mGR.mTex_MusicIcn,.29f,-.30f,1,M.SetBG?1:.5f);
	 DrawTexture(gl,mGR.mTex_Home,-.5f,-.449f);
	 DrawTransScal(gl,mGR.mTex_AddFree,.449f,-.589f,mGR.mSel==10?1.2f:1,mGR.mSel==10?.5f:1);
	 
	 switch(mGR.mSel)
	 {
	   case 1:
		    DrawTexture(gl,mGR.mTex_BtnSel,0,.24f);
		   break;
	   case 2:
		    DrawTexture(gl,mGR.mTex_IcnSel,-.29f,0);
		   break;
	   case 3:
		    DrawTexture(gl,mGR.mTex_IcnSel,-.29f,-.30f);
		   break;
	   case 4:
		    DrawTexture(gl,mGR.mTex_IcnSel,.29f,0f);
		   break;
	   case 5:
		    DrawTexture(gl,mGR.mTex_IcnSel,.29f,-.30f);
		   break;
	   case 6:
		   DrawTexture(gl,mGR.mTex_BackBtnSel,-.5f,-.449f);
		   break;
	 }
     if(mGR.isJoin)
    	 DrawJoin(gl); 
}
boolean HandlePause(MotionEvent event)
{
	float dx = mGR.mTex_MusicIcn.width();
	float dy = mGR.mTex_MusicIcn.Height()*.35f;
	mGR.mSel=0;
	if(mGR.isJoin)
		return HandleJoin(event);
	if(CircRectsOverlap(0,.24f,dx*1.25f,dy*1.25f,screen2worldX(event.getX()),screen2worldY(event.getY()),.045f))
	{
	  mGR.mSel=1; //Continue   		
	}
	if(CircRectsOverlap(-.29f,0, dx/2,dy/2,screen2worldX(event.getX()),screen2worldY(event.getY()),.045f))
	{
	  mGR.mSel=2; //Achivement
	}
	if(CircRectsOverlap(-.29f,-.30f,dx/2, dy/2,screen2worldX(event.getX()),screen2worldY(event.getY()),.045f))
	{
	  mGR.mSel=3; // Position   		
	}
	if(CircRectsOverlap(.29f,0, dx/2, dy/2,screen2worldX(event.getX()),screen2worldY(event.getY()),.045f))
	{
	  mGR.mSel=4; //Sound
	}
	if(CircRectsOverlap(.29f,-.30f,dx/2, dy/2,screen2worldX(event.getX()),screen2worldY(event.getY()),.045f))
	{
	  mGR.mSel=5; // Music   		
	}
	if(CircRectsOverlap(-.5f,-.449f, dx/2f, dy/2f,screen2worldX(event.getX()),screen2worldY(event.getY()),.045f))
	{
	  mGR.mSel=6; //Back   		
	}
	if(CircRectsOverlap(.449f,-.589f,mGR.mTex_AddFree.width()*.5f,mGR.mTex_AddFree.Height()*.5f,screen2worldX(event.getX()),screen2worldY(event.getY()),.045f))
	{
	  mGR.mSel=10; //Adfree   		
	}
	if(event.getAction() == MotionEvent.ACTION_UP)
	{
		switch(mGR.mSel)
		{
		  case 1:
			     if(M.SetBG)
			    	 M.BGPlay(GameRenderer.mContext,R.drawable.gamebg);
			     else
			    	 M.BgStop();
			   	M.GameScreen = M.GAMEPLAY;
			break;
		  case 2:
			  mGR.isJoin =false;
			  if(GameRenderer.mStart.isSignedIn()){
					GameRenderer.mStart.onShowAchievementsRequested();
				}
			  else
				mGR.isJoin = true;    
			break;
		  case 3:
			  mGR.isJoin =false;
			  if(GameRenderer.mStart.isSignedIn())
				   GameRenderer.mStart.onShowLeaderboardsRequested();
			  else
				  mGR.isJoin = true;
			  break;
		  case 4:
			  	M.setValue = !M.setValue;
			  break;
		  case 5:
			  	M.SetBG = !M.SetBG;
			  break;
		  case 6:
			   	M.GameScreen = M.GAMEMENU;
			  break; 
		  case 10:
			  mGR.mMainActivity.onBuyHTT_REMOVE_ADS(null);
			  break;
		} 
		if(M.GameScreen == M.GAMEMENU)
			   M.SplashPlay(GameRenderer.mContext,R.drawable.theme);
		mGR.mSel=0;
	}
	return true;
}
float inc=1.345f;
void GameLogic()
{
	if(!mGR.isPowerTouch)
	 {
	   if(PowerCnt>24f)
		 inc=-1.345f;
	   if(PowerCnt<=2)
		 inc=1.345f;
	   if(Counter%2==0)
		  PowerCnt+=inc;
	    mGR.dis =PowerCnt*.003f;
	    mGR.dis +=.035f;
	    if(mGR.dis>=.1f)
	    	mGR.dis =.1f;
//	    System.out.println("dis ====================    "+mGR.dis+" PowerCountt           "+PowerCnt);
	 }
	if(mGR.isPowerTouch)
	   mGR.mBowler.Update();
	 mGR.mBatsMan.Update();
	if(mGR.mBowler.isThrough)
	{
  	   mGR.mBall.update();
  	   mGR.mBall.Reset++;
	}
	mGR.mBallon.updateBallon();
	mGR.mFly.UpdatePlaneBird();
	if(mGR.mFly.No ==0)
	{
	   if(mGR.mFly.x>3.0f)
		 mGR.SetBirdPlane();	
	}
	if(mGR.mFly.No >0)
	{
	   if(mGR.mFly.x<-3.0f)
	     mGR.SetBirdPlane();	
	}
	if(!mGR.isBonus)
	{
		if(Counter%2==0)
 		 mGR.mBonusCount++;
		if(mGR.mBonusCount>200+M.mRand.nextInt(100))
		{
			mGR.isBonus =true;
			mGR.mBonusX= M.randomRange(-.7f,.7f);
			mGR.mBonusY= M.randomRange(-.1f,.6f);
		}
	}
	if(mGR.mBatsMan.isBatmanHit && mGR.mBatsMan.mBatsManAni>3/* && mGR.mBatsMan.mBatsManAni<7*/&& mGR.mBowler.isThrough)
	 {
		  for(int i=0;i<7;i++)
		  {
			  if(!mGR.isHit && CircRectsOverlap((mGR.mBatsMan.x+.04f)+mGR.px*i,(mGR.mBatsMan.y-.03f)+mGR.py*i,mGR.mTex_dot.width()*.5f, mGR.mTex_dot.Height()*.5f,mGR.mBall.x,mGR.mBall.y,mGR.mTex_Ball.Height()*.5f) && mGR.mBall.y<mGR.mBatsMan.y-.03f)
			  {
				  M.BallhitSound(GameRenderer.mContext,R.drawable.ballhit);  
				  mGR.isHit = true;
				  mGR.mAchCntShot++;
				  System.out.println("Shot==============     "+mGR.mAchCntShot);
				  mGR.mAchTotalShot++;
				  mGR.mAchStrike =0;
				  mGR.mBall.Set(mGR.mBall.x,mGR.mBall.y,mGR.vx,mGR.vy);
				  mGR.mBoom.Set(mGR.mBall.x,mGR.mBall.y,0);
				  break;
			  }
		  }
	 }
	if(mGR.isHit && !mGR.mBall.isSky)
	{
		if(CircRectsOverlap(0,-.4f,.48f,.01f, mGR.mBall.x,mGR.mBall.y,.01f) && mGR.mBatsMan.isBatmanHit &&!mGR.mBall.isTouch)
		 {
			if(mGR.vy<=.05f)
			{
			  if(mGR.mBall.TapCount>0 && mGR.vy<=.048f)
			   	 BounceBall();
			  else
			  {
				 if(mGR.mBall.vy<-.025f)
					BounceBall();
			  }
		    }
		  }
		if(CirCir(mGR.mBonusX,mGR.mBonusY,.04f,mGR.mBall.x,mGR.mBall.y,.02f) && mGR.mBall.vy<M.VY)
		{
			M.BonusShotSound(GameRenderer.mContext,R.drawable.bonusshot);
			mGR.isBonus=false;
			mGR.mBonusCount=0;
			Score=0;
			mGR.mBonus.Set(mGR.mBall.x,mGR.mBall.y,(10000+M.randomRangeInt(1000,5000)));
			mGR.mBonusScore+=mGR.mBonus.No;
			mGR.mBonusX=mGR.mBonusY=-100;
			mGR.mBall.Reset=-50;
			System.out.println("In Bonus===========%%%%%%%%%%%%=="+mGR.mBonusScore);
		}
	    KhadiCarCollison();
  	   if(mGR.vy>.05f)
 	   {
		 MoveCarCollision();
         WindowDoorCheck();
         TreeColli();
	     BuildingColli();
	     ChimneyCollison();
 	   }
    }
	
    float d =(float)(Math.sin(Math.toRadians(mGR.mBallon.t)))*.32f;
    if(mGR.mBallon.isVisible)
	  if(CirCir(mGR.mBallon.x-d,mGR.mBallon.y+mGR.mTex_Ballon.Height()*.85f,.02f,mGR.mBall.x,mGR.mBall.y,.02f) && mGR.mBall.vy<M.VY)
	   {
		    M.AirBonusSound(GameRenderer.mContext,R.drawable.airbonus);
		    mGR.mBallon.SetBallon(-100, -100);
		    mGR.mBallon.AniCount=-50;
		    mGR.mEffect.SetEffect(mGR.mBall.x,mGR.mBall.y);
		    mGR.mTextAni.Set(mGR.mBall.x,mGR.mBall.y,6500);
		    mGR.mScore +=mGR.mTextAni.No;
		    mGR.mExtraScore +=6500;
		    mGR.mTotalBall+=1;
		    M.BonusBallSound(GameRenderer.mContext,R.drawable.bonusball);
			mGR.mBall.reset();
			mGR.mAchBallon++;
			System.out.println("In Ballon");
	   }
    if(CircRectsOverlap(mGR.mFly.x,mGR.mFly.y,.02f,.025f,mGR.mBall.x,mGR.mBall.y,.02f) && mGR.mBall.vy<M.VY*.5f)
	   {
    	    M.PlaneHeliStop();
    	    M.AirBonusSound(GameRenderer.mContext,R.drawable.airbonus);
		    mGR.mFly.y=100;
		    mGR.mEffect.SetEffect(mGR.mBall.x,mGR.mBall.y);
		    mGR.mTextAni.Set(mGR.mBall.x,mGR.mBall.y,1000);
		    mGR.mScore +=mGR.mTextAni.No;
		    mGR.mExtraScore +=1000;
		    mGR.mTotalBall+=1;
		    M.BonusBallSound(GameRenderer.mContext,R.drawable.bonusball);
			mGR.mBall.reset();
			System.out.println("In Bird");
	   }
    if(mGR.mLevelWindow==mGR.mTargetWindow && !mGR.isTarget) 
    {
    	System.out.println("In Set-============");
    	M.ClapSound(GameRenderer.mContext,R.drawable.clap);
    	mGR.mTargetComp.Set(0,1.2f,0);
    	mGR.isTarget =true;
    }
   if(mGR.isHit)
	  setTail(mGR.mBall.x,mGR.mBall.y);
   if(mGR.mBall.y<-.95f && mGR.mBall.y>-1f)
	 {
		if(mGR.mBowler.isThrough)
		{
		   mGR.mTextAni.Set(0,0,8);
		   mGR.mAchStrike++;
		   M.StrikeSound(GameRenderer.mContext,R.drawable.strike);
		}
//		System.out.println("In Strike===========   "+mGR.mAchStrike+"Shotttttttttt   "+mGR.mAchCntShot);
		 mGR.mAchCntShot=0;
	 }
    UpdateAchivement();
    Reset();
}
void MoveCarCollision()
{
	for(int i=0;i<mGR.mCar.length;i++)
	   {
		  int no;
		  if(CircRectsOverlap(mGR.mCar[i].x,mGR.mCar[i].y,mGR.mTex_CarR[0].width()*.35f,mGR.mTex_CarR[0].Height()*.3f,mGR.mBall.x,mGR.mBall.y,.02f) && mGR.mBall.vy<M.VY*.5f)
		  {
			  if(mGR.mCar[i].No ==2)
				  no =1;
			  else if(mGR.mCar[i].No ==3)
				  no=2;
			  else
				  no=0;
			  if(no ==0)
			  {
			     mGR.mScore +=500;
			     mGR.mExtraScore+=500;
			  }
			  else
			  {
				 mGR.mScore +=1000;
				 mGR.mExtraScore+=1000;
			  }
			  M.WallSound(GameRenderer.mContext,R.drawable.wall);
			  mGR.mEffect.SetEffect(mGR.mBall.x,mGR.mBall.y);
			  mGR.mTextAni.Set(mGR.mBall.x,mGR.mBall.y,no);
			  mGR.isCar=true;
			  mGR.mBall.reset();
			  mGR.mAchCarCnt++;
//			  System.out.println("InCarrrrrr=============");
		  }
		  if(CircRectsOverlap(mGR.mCarL[i].x,mGR.mCarL[i].y,mGR.mTex_CarR[0].width()*.35f,mGR.mTex_CarR[0].Height()*.3f,mGR.mBall.x,mGR.mBall.y,.02f) && mGR.mBall.vy<M.VY*.5f)
		  {
			  M.WallSound(GameRenderer.mContext,R.drawable.wall);
			  mGR.mEffect.SetEffect(mGR.mBall.x,mGR.mBall.y);
			  mGR.mTextAni.Set(mGR.mBall.x,mGR.mBall.y,0);
			  mGR.mExtraScore+=500;
			  mGR.isCar=true;
			  mGR.mBall.reset();
			  mGR.mAchCarCnt++;
//			  System.out.println("InCarrLLLL=============");
		  }
	   }
}
void WindowDoorCheck()
{
	for(int i=0;i<M.Wx.length;i++)
	  {
		switch(i)
		 {
		   case 0:case 1:
			   mGR.mWindow[i].No =0;
			   break;
		   case 2:case 3:case 4:
			   mGR.mWindow[i].No =1;
			   break;
		   case 5:case 6:
			   mGR.mWindow[i].No =10;
			   break;
		   case 7:case 8:case 9:case 10:case 11:
			   mGR.mWindow[i].No =2;
			   break;
		   case 12:case 13:case 14:case 15:
			   mGR.mWindow[i].No =3;
			   break;
		   case 16:
			   mGR.mWindow[i].No =11;
			   break;
		   case 17: case 18:case 19:
			   mGR.mWindow[i].No =4;
			   break;
		   case 20: case 21:
			   mGR.mWindow[i].No =12;
			   break;
		   case 22: case 23:case 24: case 25:case 26: case 27:case 28:
			   mGR.mWindow[i].No =5;
			   break;	      
		   case 29:
			   mGR.mWindow[i].No =7;
			   break;   
		   case 30:
			   mGR.mWindow[i].No =8;
			   break;	  
		   case 31:case 32:case 33:case 34:
			   mGR.mWindow[i].No =6;
			   break;	  
		   case 35:
			   mGR.mWindow[i].No =9;
			   break;
		 }
		   float w = mGR.mTex_BreakWindow[0].width()*.12f;
		   float h = mGR.mTex_BreakWindow[0].Height()*.15f;
		   if(i>=M.Wx.length-2)
		   {
			   w =.01f;
			   h =.015f;
		   }
		  if(CircRectsOverlap(M.Wx[i],M.Wy[i],w,h,mGR.mBall.x,mGR.mBall.y,.02f) && mGR.mBall.vy<M.VY)
		  {
			  int no;
			  no=3;
			  mGR.mEffect.SetEffect(mGR.mBall.x,mGR.mBall.y);
			  mGR.mTextAni.Set(mGR.mBall.x,mGR.mBall.y,no);
			  mGR.SetGlassAni(mGR.mBall.x,mGR.mBall.y);
			  mGR.mWindow[i].Set(M.Wx[i],M.Wy[i],mGR.mWindow[i].No);
		    if(i!=29 && i!=30 && i<M.Wx.length-2)
		       mGR.mWindowChar.SetChar(M.Wx[i],M.Wy[i]);
			   mGR.mScore 		+=1000;
			   mGR.mTargetScore+=1000;
			   mGR.mLevelWindow++;
			   mGR.mAchTotalWindow++;
			   mGR.isWinDow=true;
			   mGR.mBall.reset();
			   M.WindowSound(GameRenderer.mContext,R.drawable.window);
//			  System.out.println("InWindowwwww=============");
		  }
	  }
	  for(int i=0;i<M.DoorX.length;i++)
	  {
		  float  w = mGR.mTex_BreakWindow[2].width()*.15f;
		   float h = mGR.mTex_BreakWindow[2].Height()*.25f;
		  if(CircRectsOverlap(M.DoorX[i],M.DoorY[i],w,h,mGR.mBall.x,mGR.mBall.y,.02f) && mGR.mBall.vy<M.VY)
		  {
			  M.WallSound(GameRenderer.mContext,R.drawable.wall);
			  mGR.mEffect.SetEffect(mGR.mBall.x,mGR.mBall.y);
			  mGR.mTextAni.Set(mGR.mBall.x,mGR.mBall.y,5);
			  mGR.mExtraScore+=500;
			  mGR.mBall.reset();
			  mGR.mScore +=500;
			  mGR.mAchDoorCnt++;
//			  System.out.println("InDorrrr=============");
		  }
	  }
}
void KhadiCarCollison()
{
	for(int i=0;i<M.CarX.length;i++)
	{
	  if(mGR.vy<=.05f)
		{
		  if(CircRectsOverlap(M.CarX[i],M.CarY[i],M.CarRad[0][i],M.CarRad[1][i],mGR.mBall.x,mGR.mBall.y,.02f))
 		  {
			  mGR.mEffect.SetEffect(mGR.mBall.x,mGR.mBall.y);
		  	  mGR.mBall.reset();
			 System.out.println("InKhadiCarrrrrrrrr=============");
		  }
		}
		else
		{
			if(CircRectsOverlap(M.CarX[i],M.CarY[i],M.CarRad[0][i],M.CarRad[1][i],mGR.mBall.x,mGR.mBall.y,.02f)&& mGR.mBall.vy<M.VY)
			{
				mGR.mEffect.SetEffect(mGR.mBall.x,mGR.mBall.y);
				mGR.mBall.reset();
				System.out.println("InKhadiCarrrrrrrrr=======2222222222======");
			}
		}
	}
}

void BuildingColli()
{
	int no;
	for(int i=0;i<M.Building[0].length;i++)
	 { 
		 float w =M.BuildingArea[0][i]; 
		 float h =M.BuildingArea[1][i];
		 if(CircRectsOverlap(M.Building[0][i],M.Building[1][i],w,h,mGR.mBall.x,mGR.mBall.y,.02f) && mGR.mBall.vy<M.VY)
		 {
			if(mGR.mBall.y>M.Building[1][i]+h*.5f)
			{
				no=6;
				mGR.isRoof=true;
				mGR.mAchRoof++;
			}
			else
				no=4;
			mGR.mEffect.SetEffect(mGR.mBall.x,mGR.mBall.y);
			mGR.mTextAni.Set(mGR.mBall.x,mGR.mBall.y,no);
			mGR.mBall.reset();
			mGR.mScore +=500;
			mGR.mExtraScore+=500;
			M.WallSound(GameRenderer.mContext,R.drawable.wall);
//			System.out.println("In Buildinggggggggg");
		 }
	 }
	for(int i=0;i<10;i++)
	 {
		if(CircRectsOverlap(-.789f+(i*.04f),.040f+(i*.03f),.02f,.03f,mGR.mBall.x,mGR.mBall.y,.02f) && mGR.mBall.vy<M.VY)
		{
			mGR.isRoof=true;
			mGR.mEffect.SetEffect(mGR.mBall.x,mGR.mBall.y);
			mGR.mTextAni.Set(mGR.mBall.x,mGR.mBall.y,6);
			mGR.mBall.reset();
			mGR.mScore +=500;
			mGR.mExtraScore+=500;
			mGR.mAchRoof++;
			M.WallSound(GameRenderer.mContext,R.drawable.wall);
//			System.out.println("In Building 11111111111");
		}
	 }
}
void ChimneyCollison()
{
	 float w =.025f; 
	 float h =.02f;
	for(int i=0;i<M.Chimney[0].length;i++)
	{ 
	   if(CircRectsOverlap(M.Chimney[0][i],M.Chimney[1][i],w,h,mGR.mBall.x,mGR.mBall.y,.02f) && mGR.mBall.vy<M.VY)
		{
		    mGR.mEffect.SetEffect(mGR.mBall.x,mGR.mBall.y);
		    if(i>1 && i<6)
		    {
		      mGR.mTextAni.Set(mGR.mBall.x,mGR.mBall.y,6);
		      mGR.isRoof=true;
		      mGR.mAchRoof++;
		    }
		    else
		    	mGR.mTextAni.Set(mGR.mBall.x,mGR.mBall.y,500);
			mGR.mBall.reset();
			mGR.mScore +=500;
			mGR.mExtraScore+=500;
			M.WallSound(GameRenderer.mContext,R.drawable.wall);
//			System.out.println("In Chimeny");
		}
	}
}
void TreeColli()
{
	for(int i=0;i<M.TreeX.length;i++)
	{
	  if(CirCir(M.TreeX[i],M.TreeY[i],.022f, mGR.mBall.x,mGR.mBall.y,.02f) && mGR.mBall.vy<M.VY)
	  {
		    mGR.mEffect.SetEffect(mGR.mBall.x,mGR.mBall.y);
		    mGR.mTextAni.Set(mGR.mBall.x,mGR.mBall.y,500);
			mGR.mScore +=500;
			mGR.mBall.reset();
			mGR.mExtraScore+=500;
			M.BonusBallSound(GameRenderer.mContext,R.drawable.bonusball);
//			System.out.println("In Tree          ");
	  }
	}
}
void BounceBall()
{
	mGR.mBall.isTouch= true;
    mGR.mBall.TapCount =0;
    mGR.mBall.vy=-.01f;
    if(mGR.vx<0)
      mGR.mBall.vx=-.009f;
    else
	  mGR.mBall.vx=.009f;
}
void Reset()
{
	  if(/*mGR.mBall.y<-1.2f && */mGR.mBall.Reset>M.Restart)
	   {
		 mGR.mBowler.reset();
		 mGR.mBall.Reset =0;
		 mGR.isHit = false;
		 mGR.isPowerTouch = false;
		 mGR.isAngleTouch = false;
		 mGR.vx=mGR.vy=0;
		 ContinueBreak();
		 if(mGR.mTotalBall<1)
		 {
		   	M.StopSound(); 
		   if(!GameRenderer.mStart.addFree)
			 M.GameScreen = M.GAMEADD;
		   else
		    {
			 if(mGR.mLevelWindow>=mGR.mTargetWindow) 
			  M.GameScreen = M.GAMEWIN;
			 else
			  M.GameScreen = M.GAMEOVER;
		    }
			mGR.UpdateScore();
			GameRenderer.mStart.Submitscore(R.string.leaderboard_boom_baseball, mGR.mNewScore);
//			System.out.println("In Over ==============     "+mGR.mLevelWindow+"Target=================  "+mGR.mTargetWindow);
		 }
	   }
}
void ContinueBreak()
{
	if(mGR.isWinDow)
	   mGR.mWindowCnt++;
	else
	  mGR.mWindowCnt=0;
	
	if(mGR.isCar)
	  mGR.mCarCnt++;
	else
	  mGR.mCarCnt =0;
	if(mGR.isRoof)
	  mGR.mRoofCnt++;
	else
      mGR.mRoofCnt=0;
	
	int id; 
	if(mGR.mWindowCnt==3 || mGR.mCarCnt ==3  ||mGR.mRoofCnt==3)
	{
		id =15;
		if(mGR.mWindowCnt==3 && !M.isAchive[id])
		{
			M.isAchive[id] =true;
			GameRenderer.mStart.UnlockAchievement(M.Achivemnet[id]);
			mGR.mAchiveAni.Set(0,0,id);
		}
    	mGR.mTextAni.Set(0,.8f,10);
    	mGR.mTotalBall +=1;
    	M.BonusBallSound(GameRenderer.mContext,R.drawable.bonusball);
	}
	
	if(mGR.mWindowCnt==5||mGR.mCarCnt ==5 ||mGR.mRoofCnt==5)
	{
		id =16;
		if(mGR.mWindowCnt==5 && !M.isAchive[id])
		{
			M.isAchive[id] =true;
			GameRenderer.mStart.UnlockAchievement(M.Achivemnet[id]);
			mGR.mAchiveAni.Set(0,0,id);
		}
    	mGR.mTextAni.Set(0,.8f,11);
    	mGR.mTotalBall +=2;
    	M.BonusBallSound(GameRenderer.mContext,R.drawable.bonusball);
	}
	
	if(mGR.mWindowCnt>9 ||mGR.mCarCnt >6 || mGR.mRoofCnt>6)
	{
		id =17;
		if(mGR.mWindowCnt==10 && !M.isAchive[id])
		{
			M.isAchive[id] =true;
			GameRenderer.mStart.UnlockAchievement(M.Achivemnet[id]);
			mGR.mAchiveAni.Set(0,0,id);
		}	
   	  mGR.mTextAni.Set(0,.8f,12);
   	  mGR.mTotalBall +=3;
   	  M.BonusBallSound(GameRenderer.mContext,R.drawable.bonusball);
   	  mGR.mWindowCnt = mGR.mCarCnt =mGR.mRoofCnt=0;
	}
	if(mGR.mAchiveAni.x>-1 && mGR.mAchiveAni.x<1f)
       M.ClapSound(GameRenderer.mContext,R.drawable.clap);
//	System.out.println("Window===========    "+mGR.mWindowCnt+"    "+"Roof ==============     "+"    "+mGR.mRoofCnt+"CarCnt============  "+mGR.mCarCnt);
	mGR.isWinDow = mGR.isRoof =mGR.isCar =false;
}
void UpdateAchivement()
{
	int id;
	id=0;
	if(mGR.mAchCarCnt ==5 && !M.isAchive[id])
	{
  	    M.isAchive[id] = true;
  	    GameRenderer.mStart.UnlockAchievement(M.Achivemnet[id]);
  	   mGR.mAchiveAni.Set(0,0,id);
	}
	id=1;
	if(mGR.mAchCarCnt ==10 && !M.isAchive[id])
	{
  	   M.isAchive[id] = true;
  	   GameRenderer.mStart.UnlockAchievement(M.Achivemnet[id]);
  	   mGR.mAchiveAni.Set(0,0,id);
	}
	id=2;
	if(mGR.mAchCarCnt ==50 && !M.isAchive[id])
	{
  	   M.isAchive[id] = true;
  	   GameRenderer.mStart.UnlockAchievement(M.Achivemnet[id]);
       mGR.mAchiveAni.Set(0,0,id);
	}
	id=3;
	if(mGR.mAchDoorCnt ==25 && !M.isAchive[id])
	{
		M.isAchive[id] = true;
		GameRenderer.mStart.UnlockAchievement(M.Achivemnet[id]);
		mGR.mAchiveAni.Set(0,0,id);
	}
	id=4;
	if(mGR.mAchRoof == 25 && !M.isAchive[id])
	{
		M.isAchive[id] =true;
		GameRenderer.mStart.UnlockAchievement(M.Achivemnet[id]);
		mGR.mAchiveAni.Set(0,0,id);
	}
	id=5;
	if(mGR.mAchCntShot == 3 && !M.isAchive[id])
	{
		M.isAchive[id] =true;
		GameRenderer.mStart.UnlockAchievement(M.Achivemnet[id]);
		mGR.mAchiveAni.Set(0,0,id);
	}
	id=6;
	if(mGR.mAchCntShot == 5 && !M.isAchive[id])
	{
		M.isAchive[id] =true;
		GameRenderer.mStart.UnlockAchievement(M.Achivemnet[id]);
		mGR.mAchiveAni.Set(0,0,id);
	}
	id=7;
	if(mGR.mAchCntShot == 10 && !M.isAchive[id])
	{
		M.isAchive[id] =true;
		GameRenderer.mStart.UnlockAchievement(M.Achivemnet[id]);
		mGR.mAchiveAni.Set(0,0,id);
	}
	id=8;
	if(mGR.mAchCntShot == 15 && !M.isAchive[id])
	{
		M.isAchive[id] =true;
		GameRenderer.mStart.UnlockAchievement(M.Achivemnet[id]);
		mGR.mAchiveAni.Set(0,0,id);
	}
	id=9;
	if(mGR.mAchCntShot == 25 && !M.isAchive[id])
	{
		M.isAchive[id] =true;
		GameRenderer.mStart.UnlockAchievement(M.Achivemnet[id]);
		mGR.mAchiveAni.Set(0,0,id);
	}
	id=10;
	if(mGR.mAchTotalShot == 100 && !M.isAchive[id])
	{
		M.isAchive[id] =true;
		GameRenderer.mStart.UnlockAchievement(M.Achivemnet[id]);
		mGR.mAchiveAni.Set(0,0,id);
	}
	id=11;
	if(mGR.mAchTotalShot == 200 && !M.isAchive[id])
	{
		M.isAchive[id] =true;
		GameRenderer.mStart.UnlockAchievement(M.Achivemnet[id]);
		mGR.mAchiveAni.Set(0,0,id);
	}
	id=12;
	if(mGR.mAchTotalShot == 500 && !M.isAchive[id])
	{
		M.isAchive[id] =true;
		GameRenderer.mStart.UnlockAchievement(M.Achivemnet[id]);
		mGR.mAchiveAni.Set(0,0,id);
	}
	id=13;
	if(mGR.mAchTotalShot == 1000 && !M.isAchive[id])
	{
		M.isAchive[id] =true;
		GameRenderer.mStart.UnlockAchievement(M.Achivemnet[id]);
		mGR.mAchiveAni.Set(0,0,id);
	}
	id =14;
	if(mGR.mAchStrike ==3 && !M.isAchive[id])
	{
		M.isAchive[id] =true;
		GameRenderer.mStart.UnlockAchievement(M.Achivemnet[id]);
		mGR.mAchiveAni.Set(0,0,id);
	}
	id =18;
	if(mGR.mAchTotalWindow ==500 && !M.isAchive[id])
	{
		M.isAchive[id] =true;
		GameRenderer.mStart.UnlockAchievement(M.Achivemnet[id]);
		mGR.mAchiveAni.Set(0,0,id);
	}
	id =19;
	if(mGR.mAchTotalWindow ==1000 && !M.isAchive[id])
	{
		M.isAchive[id] =true;
		GameRenderer.mStart.UnlockAchievement(M.Achivemnet[id]);
		mGR.mAchiveAni.Set(0,0,id);
	}
	id =20;
	if(mGR.mAchBallon==25 && !M.isAchive[id])
	{
		M.isAchive[id] =true;
		GameRenderer.mStart.UnlockAchievement(M.Achivemnet[id]);
		mGR.mAchiveAni.Set(0,0,id);
	}
	if(mGR.mAchiveAni.x>-1 && mGR.mAchiveAni.x<1f)
       M.ClapSound(GameRenderer.mContext,R.drawable.clap);
}
void DrawAnimation(GL10 gl)
{
	for(int i=0;i<mGR.mAni.length;i++)
	{
	  if(mGR.mAni[i].x>-1 && mGR.mAni[i].x<1)
	     DrawTextureS(gl,mGR.mTex_Katch[mGR.mAni[i].No],mGR.mAni[i].x,mGR.mAni[i].y,mGR.mAni[i].z);
	   mGR.mAni[i].update();
	}
}
void setTail(float x,float y)
{
	int cr =0;
	for(int i=0;i<mGR.mTail.length && cr<1;i++)
	{
		if(mGR.mTail[i].x<-1.1||mGR.mTail[i].x>1.1||mGR.mTail[i].y<-1.1||mGR.mTail[i].y>1.1)
		{
		    cr++;
			mGR.mTail[i].settail(x,y,mGR.mBall.z);
		}
	}
}
void DrawTail(GL10 gl)
{
	for(int i=0;i<mGR.mTail.length;i++)
	{
		if(mGR.mTail[i].x>-1.2&&mGR.mTail[i].x<1.2&&mGR.mTail[i].y>-1.2&&mGR.mTail[i].y<1.2)
		{
			mGR.mTex_dot.drawRGBS(gl, mGR.mTail[i].x, mGR.mTail[i].y, 1,1,1,.6f, mGR.mTail[i].z);
			mGR.mTail[i].updatetail();
		}
	}
}
void DrawGamePlay(GL10 gl)
{
	
	 DrawTexture(gl,mGR.mTex_GameBg[0],0,.379f);
	 if(mGR.mBall.isSky)
	 {
	   DrawTextureRS(gl,mGR.mTex_Ball,mGR.mBall.x,mGR.mBall.y,mGR.mBall.z,(Counter*10)%360);
	   DrawTail(gl);
	 }
	 if(mGR.mFly.x>-1.2f && mGR.mFly.x<1.2f)
	 {
	   if(mGR.mFly.No==0)	 
	   {
		  if(mGR.mFly.x>-.2f && mGR.mFly.x<.2f) 
  	        M.BirdSound(GameRenderer.mContext,R.drawable.bird);	   
	      DrawTexture(gl,mGR.mTex_Bird[Counter/4%mGR.mTex_Bird.length],mGR.mFly.x,mGR.mFly.y);
	   }
	   if(mGR.mFly.No==1)
	   {
		 if(mGR.mFly.x>=.9f && mGR.mFly.x<1f) 
		   M.PlaneSound(GameRenderer.mContext,R.drawable.plain);
		 if(mGR.mFly.y>1f || mGR.mFly.x<-1f)
			  M.PlaneHeliStop();
	     DrawTexture(gl,mGR.mTex_Plane,mGR.mFly.x,mGR.mFly.y);
	   }
	   if(mGR.mFly.No==2)
	   {
		  if(mGR.mFly.x>=.9f && mGR.mFly.x<1f)
            M.HeliSound(GameRenderer.mContext,R.drawable.helli);
		  if(mGR.mFly.y>1f || mGR.mFly.x<-1f)
		    M.PlaneHeliStop();
		 DrawTexture(gl,mGR.mTex_Heli[Counter/4%2],mGR.mFly.x,mGR.mFly.y);
	   }
	   
	 }
	 if(mGR.mBallon.x>-1 && mGR.mBallon.x<1)
	 {
  	   if(mGR.mBallon.AniCount>50) 
	      DrawTexturR(gl,mGR.mTex_Ballon,mGR.mBallon.x,mGR.mBallon.y,mGR.mBallon.t,0,mGR.mTex_Ballon.Height()*.45f);
	 }
	 
	 DrawTexture(gl,mGR.mTex_GameBg[1],0,.17f);
	 for(int i=0;i<mGR.mWindow.length;i++)
	 {
  	   if(mGR.mWindow[i].x>-1f && mGR.mWindow[i].x<1f)
  	   {
  		 if(i<mGR.mWindow.length-2) 
		   DrawTexture(gl,mGR.mTex_BreakWindow[mGR.mWindow[i].No],mGR.mWindow[i].x,mGR.mWindow[i].y);
  		 else
  		  DrawTextureS(gl,mGR.mTex_BreakWindow[1],mGR.mWindow[i].x,mGR.mWindow[i].y,.5f);
  	   }
	 }
	 if(mGR.mWindowChar.x>-1f && mGR.mWindowChar.x<1f)
	   {
	      int k = mGR.mTex_WinDowChar[mGR.mWindowChar.No].length;	 
	      DrawRGB(gl,mGR.mTex_WinDowChar[mGR.mWindowChar.No][Counter/3%k],mGR.mWindowChar.x,mGR.mWindowChar.y,mGR.mWindowChar.gayab,mGR.mWindowChar.gayab,mGR.mWindowChar.gayab,mGR.mWindowChar.gayab);
	      mGR.mWindowChar.UpdateChar();
	   }
	 for(int i=0;i<mGR.mCar.length;i++)
	    {
		  if(mGR.mCarL[i].x>-1.1f && mGR.mCarL[i].x<1.1f)
		     DrawTexture(gl,mGR.mTex_CarL[mGR.mCarL[i].No],mGR.mCarL[i].x,mGR.mCarL[i].y);
		  if(mGR.mCar[i].x>-1.1f && mGR.mCar[i].x<1.1f)
			  DrawTexture(gl,mGR.mTex_CarR[mGR.mCar[i].No],mGR.mCar[i].x,mGR.mCar[i].y);
	   
		   mGR.mCarL[i].Update();
		   mGR.mCar[i].Update();
			if(mGR.mCar[i].x<-2f)
			  mGR.mCar[i].set(2f,-M.randomRange(.006f,.01f),M.mRand.nextInt(mGR.mTex_CarR.length));
			if(mGR.mCarL[i].x>2f)
			  mGR.mCarL[i].set(-2f,M.randomRange(.005f,.01f),M.mRand.nextInt(mGR.mTex_CarL.length));
	    }
	    DrawAnimation(gl);
	 if(mGR.isBonus)
	    DrawTexture(gl,mGR.mTex_Bonus[Counter/6%2],mGR.mBonusX,mGR.mBonusY);
	
	 DrawTexture(gl,mGR.mTex_GameBg[2],0,-.469f);
	 if(mGR.mBonus.x >-1 && mGR.mBonus.x<1)
	 {
		DrawTransScal(gl,mGR.mTex_Text[9],mGR.mBonus.x,mGR.mBonus.y,mGR.mBonus.s,mGR.mBonus.t);
		DrawNumberScal(gl,Score,mGR.mBonus.x,mGR.mBonus.y-.2f,mGR.mBonus.s*1.3f,mGR.mBonus.t);
		if(Score<=mGR.mBonus.No)
		{
	  	   Score+=200;
	  	   M.CountingSound(GameRenderer.mContext,R.drawable.counting);
		}
		else
		   mGR.mBonus.update();
		if(mGR.mBonus.x==-100)
		{
			mGR.mScore+=Score;
			M.BonusBallSound(GameRenderer.mContext,R.drawable.bonusball);
			mGR.mTextAni.Set(0,.8f,M.randomRangeInt(11,12));
//			System.out.println("             "+mGR.mTotalBall);
			mGR.mTotalBall +=mGR.mTextAni.No-9;
//			System.out.println("In Score Updateeeeee"+mGR.mTextAni.No+"       "+mGR.mTotalBall);
		}
	 }
	 DrawTexture(gl,mGR.mTex_BowlerShad,mGR.mBowler.x-.01f,mGR.mBowler.y-.16f);
	 DrawTexture(gl,mGR.mTex_Bowler[mGR.mBowler.mBowlerAni],mGR.mBowler.x,mGR.mBowler.y);
	
	 if(mGR.mBowler.isThrough && !mGR.mBall.isSky)
        DrawTextureRS(gl,mGR.mTex_Ball,mGR.mBall.x,mGR.mBall.y,mGR.mBall.z,(Counter*10)%360);
	 if(mGR.isHit)
	 {
	   if(mGR.mBowler.isThrough && !mGR.mBall.isSky)
	      DrawTail(gl);
	   if(mGR.mBoom.x>-1f && mGR.mBoom.x<1f)
	   {
	     DrawTransScal(gl,mGR.mTex_Boom,mGR.mBoom.x,mGR.mBoom.y,mGR.mBoom.t,mGR.mBoom.t);
	     mGR.mBoom.updateBoom();
	   }
	 }
	 mGR.px = (float)Math.cos(Math.toRadians(mGR.mBatsMan.BatAngle))*.04f;
	 mGR.py = (float)Math.sin(Math.toRadians(mGR.mBatsMan.BatAngle))*.04f;
	 DrawTexture(gl,mGR.mTex_PlayerShad,mGR.mBatsMan.x,mGR.mBatsMan.y-.369f);
	 if(mGR.mBatsMan.isBatmanHit)
	    DrawTexture(gl,mGR.mTex_Player[mGR.mBatsMan.mBatsManAni],mGR.mBatsMan.x,mGR.mBatsMan.y);
	 else
	    DrawTexture(gl,mGR.mTex_PlayerReady[Counter/5%3],mGR.mBatsMan.x,mGR.mBatsMan.y);
 	 
	 
	 if(mGR.mEffect.x>-1.1f && mGR.mEffect.x<1.1f)
	 {
		 DrawTexture(gl,mGR.mTex_Effect[mGR.mEffect.No] ,mGR.mEffect.x,mGR.mEffect.y);
		 mGR.mEffect.updateEffect();
	 }
	 if(mGR.mTextAni.x>-1f && mGR.mTextAni.x<1f)
	 {
//		 if(mGR.mBonusScore.x<-1)
		 {
			if(mGR.mTextAni.No<13) 
		     DrawTransScal(gl,mGR.mTex_Text[mGR.mTextAni.No],mGR.mTextAni.x,mGR.mTextAni.y,mGR.mTextAni.s,mGR.mTextAni.t);
			else
			{
			  DrawNumberScal(gl,mGR.mTextAni.No,mGR.mTextAni.x,mGR.mTextAni.y,mGR.mTextAni.s,mGR.mTextAni.t);
//			  if(mGR.mTextAni.No>13)
//				 DrawNumberScal(gl,mGR.mTextAni.No,mGR.mTextAni.x,mGR.mTextAni.y,mGR.mTextAni.s,mGR.mTextAni.t);
 		      if(mGR.mTextAni.No>500)
				DrawTransScal(gl,mGR.mTex_Text[10],0,.8f,mGR.mTextAni.s,mGR.mTextAni.t);
			}
		 }
		 mGR.mTextAni.update();
	 }
	 if(mGR.mTargetComp.y>-1f && mGR.mTargetComp.y<2f)
	 {
	   DrawTransScal(gl,mGR.mTex_TargetComp,mGR.mTargetComp.x,mGR.mTargetComp.y,mGR.mTargetComp.s,mGR.mTargetComp.t);
	   if(mGR.mTargetComp.y>=.6f)
	     mGR.mTargetComp.y-=2f*.02f;
	   else
	     mGR.mTargetComp.update();
	 }
	 if(mGR.mAchiveAni.x>-1f && mGR.mAchiveAni.x<1f)
	 {
	   DrawTransScal(gl,mGR.mTex_Achive[mGR.mAchiveAni.No],mGR.mAchiveAni.x,mGR.mAchiveAni.y+.4f,mGR.mAchiveAni.s,mGR.mAchiveAni.t);
	   DrawTransScal(gl,mGR.mTex_AchiveClear,mGR.mAchiveAni.x,mGR.mAchiveAni.y,mGR.mAchiveAni.s,mGR.mAchiveAni.t);
	   mGR.mAchiveAni.update();
	 }
	 DrawTransScal(gl,mGR.mTex_Bar[0],-.751f,-.50f,mGR.mSel==2?1.5f:1f,mGR.mSel==2?1.5f:1f);
	 mGR.mTex_Bar[1].drawSS(gl,-.749f,-.759f,1,PowerCnt,0,mGR.mTex_Bar[1].Height()*.5f);
	 DrawTransScal(gl,mGR.mTex_Bar[2],-.751f,-.88f,mGR.mSel==2?1.5f:1f,mGR.mSel==2?1.5f:1f);
	 
	 DrawTexturR(gl,mGR.mTex_Circle,0,-1.019f,(float)Math.toDegrees(mGR.ang),0,0);
	 DrawTexturRPS(gl,mGR.mTex_Arrow,0,-.98f,(float)Math.toDegrees(mGR.ang),0,mGR.mTex_Arrow.Height()*.3f,1);
	 DrawTexture(gl,mGR.mTex_BallNo,.929f,.769f);
	 drawNumber(gl,mGR.mTotalBall,.897f,.7f,0);
	 drawNumber(gl,mGR.mScore,-.915f,.8f,0);
	 DrawTexture(gl,mGR.mTex_Pause,.899f,-.899f);
	 if(mGR.mSel ==1)
    	DrawTexture(gl,mGR.mTex_IcnSel,.899f,-.899f);
	 if(M.GameScreen == M.GAMETARGET)
	 {
		 DrawTexture(gl,mGR.mTex_PopUp[1],0,PopY);
		 DrawTexture(gl,mGR.mTex_Target[0],0,PopY+.369f);
		 DrawTexture(gl,mGR.mTex_Target[1],0,PopY+.369f-.35f);
		 DrawTexture(gl,mGR.mTex_Plane,-.499f,PopY-.459f);
		 drawNumber(gl,mGR.mTargetWindow,-.2f,PopY+.02f,1);
	     drawNumber(gl,mGR.mTotalBall,.275f,PopY+.02f,1);
		 if(PopY<0f && Counter2<4)
		 {
			Counter2++;
		    PopVY =.065f/Counter2;
		 }
		 else if(Counter2>=4 && Counter2<40)
	     {
	    	 PopY  =0;
	    	 PopVY =0;
	    	 PopVY +=.0031f;
	    	 Counter2++;
	     }
		 else if(Counter2>=40)
	     {
	    	 PopVY =.07f;
	    	 PopVY +=.005f;
	    	 if(PopY>1.5f)
	    	 {
	    		M.GameScreen = M.GAMEPLAY;
	 		    Counter=0;
	 		    M.BGPlay(GameRenderer.mContext,R.drawable.gamebg);
	    	 }
	     }
	     PopY += PopVY;
		 PopVY -=.003f;
	 }
	 if(M.GameScreen == M.GAMEPLAY)
	   GameLogic();
}
boolean HandleGame(MotionEvent event)
{
	float deltaX=0,deltaY=0;
	 if(event.getAction() == MotionEvent.ACTION_DOWN) 
	 {
		mGR.mSel =0;
		if(CircRectsOverlap(.899f,-.899f,mGR.mTex_Pause.width()*.5f,mGR.mTex_Pause.Height()*.5f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		{
		   mGR.mSel =1;
	    }
	   if(!mGR.isPowerTouch && CircRectsOverlap(-.751f,-.50f,mGR.mTex_Bar[0].width()*.8f,mGR.mTex_Bar[0].Height()*.9f,screen2worldX(event.getX()), screen2worldY(event.getY()),.05f))
	    {
		   mGR.mSel =2;
	    }
	 }
	switch(event.getAction())
	{
	  case MotionEvent.ACTION_DOWN:
	  case MotionEvent.ACTION_MOVE:
			if(mGR.isPowerTouch && mGR.mSel ==0)
			{
			  deltaX 	= (event.getX()-M.ScreenWidth/2);
			  deltaY 	= (event.getY()-M.ScreenHieght);
//			  mGR.ang   = (float)Math.atan2(-deltaY,-deltaX);
			  mGR.ang   = (float)M.GetAngle(-deltaY,-deltaX);
//			  System.out.println("Angleeeee=================  "+Math.toDegrees(mGR.ang));
 	          mGR.isAngleTouch = true;
			}
		 break;
	  case MotionEvent.ACTION_UP:
		  if(mGR.mSel ==1)
	 	   {
		  	  M.StopSound();
		  	  M.GameScreen = M.GAMEPAUSE;
		  	  mGR.mSel =0;
	 	   }
		  if(mGR.mSel ==2)
	 	   {
			  mGR.isPowerTouch =true;
			  mGR.mSel =0;
	 	   }
		  if(mGR.mBatsMan.mBatsManAni==0 && mGR.isAngleTouch && mGR.isPowerTouch)
		  {
			 M.BatSwingSound(GameRenderer.mContext,R.drawable.batswing);
			 mGR.mBatsMan.isBatmanHit = true;
			 mGR.vx  = -(float)Math.sin(mGR.ang)*((mGR.dis*.59940f));
			 mGR.vy  = mGR.dis;
			 mGR.isAngleTouch =false;
		  }
		  break;
	}
	return true;
}
void DrawJoin(GL10 gl)
{
	 DrawTexture(gl,mGR.mTex_Splash,0,0);
	 DrawTexture(gl,mGR.mTex_PopUp[2],0,0);
	 DrawTexture(gl,mGR.mTex_Achive[4],.04f,.419f);
	 DrawTransScal(gl,mGR.mTex_Remove,.63f,.459f,mGR.mSel==2?1.2f:1,mGR.mSel==2?.5f:1);
	 DrawTransScal(gl,mGR.mTex_Join[0],0,-.319f,mGR.mSel==1?1.2f:1,mGR.mSel==1?.5f:1);
	 DrawTexture(gl,mGR.mTex_Join[1],0,.109f);
	 
}
boolean HandleJoin(MotionEvent event)
{
	mGR.mSel =0;
	if(CircRectsOverlap(0.00f,-.319f,mGR.mTex_Join[0].width()*.4f, mGR.mTex_Join[0].Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	{
		mGR.mSel =1;
	}
	if(CirCir(.63f,.459f,mGR.mTex_Remove.width()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	{
		mGR.mSel =2;
	}
	if(MotionEvent.ACTION_UP == event.getAction())
	{
		switch(mGR.mSel) {
		case 1:
			GameRenderer.mStart.beginUserInitiatedSignIn();
			mGR.isJoin = false;
			break;
		case 2:
			mGR.isJoin = false;
			break;
		}
		mGR.mSel =0;
	}
	return true;
}
void DrawTexture(GL10 gl,SimplePlane Tex,float x,float y)
{
	Tex.drawPos(gl, x, y);
}
void DrawTexturR(GL10 gl,SimplePlane Tex,float x,float y,float angle,float rotX,float rotY)
{
	Tex.drawRotet(gl, x, y,angle,rotX,rotY);
}
void DrawTexturRPS(GL10 gl,SimplePlane Tex,float x,float y,float angle,float rotX,float rotY,float scal)
{
	Tex.drawRotetSP(gl, x, y,angle,rotX,rotY,scal);
}
void DrawTextureS(GL10 gl,SimplePlane Tex,float x,float y,float scal)
{
	Tex.drawScal(gl, x, y,scal,scal);
}
void DrawTextureRS(GL10 gl,SimplePlane Tex,float x,float y,float scal,float angle)
{
	Tex.drawRotetScal(gl, x, y,scal,angle);
}
void DrawRGB(GL10 gl,SimplePlane Tex,float x,float y,float r,float g,float b,float t)
{
	Tex.drawRGB(gl, x, y, r, g, b,t);
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
boolean circleLineIntersect(float x1, float y1, float x2, float y2, float cx, float cy, float cr){
      float dx = x2 - x1;
      float dy = y2 - y1;
      float a = dx * dx + dy * dy;
      float b = 2 * (dx * (x1 - cx) + dy * (y1 - cy));
      float c = cx * cx + cy * cy;
      c += x1 * x1 + y1 * y1;
      c -= 2 * (cx * x1 + cy * y1);
      c -= cr * cr;
      float bb4ac = b * b - 4 * a * c;

      if(bb4ac<0){
          return false;// No collision
      }else{
          return true;//Collision
      }
}
   boolean LineIntersectCircle(float  Line_X1,float Line_Y1,float Line_X2,float  Line_Y2,float  Circle_X,float  Circle_Y,float Circle_Radius){

	 float U;
	 float Px,Py;
	 float Distance;

		U = (Circle_X - Line_X1) * (Line_X2 - Line_X1) + (Circle_Y - Line_Y1) * (Line_Y2 - Line_Y1); 
		U = U/((Line_X2-Line_X1)*(Line_X2 - Line_X1) + (Line_Y2 - Line_Y1)*(Line_Y2 - Line_Y1)); 

		Px = Line_X1 + (U * (Line_X2 - Line_X1));
		Py = Line_Y1 + (U * (Line_Y2 - Line_Y1));
		
		Distance = (Px-Circle_X)*(Px-Circle_X) + (Py-Circle_Y)*(Py-Circle_Y);
		
		if (Distance <(Circle_Radius*Circle_Radius))
			return true; 
		else
		return false;
   }
   boolean clIntersect(float cx ,float cy ,float cr ,float x1 ,float y1 ,float x2 ,float y2)
	{	
		float x0 = cx;	
		float y0 = cy;	
			
		float n = Math.abs((x2-x1)*(y1-y0)-(x1-x0)*(y2-y1));	
		float d = (float) Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));	
		float dist = n/d;	
		if(dist > cr)
			return false;	
		float d1 = (float) Math.sqrt((x0-x1)*(x0-x1)+(y0-y1)*(y0-y1));	
		if((d1-cr) > d)
			return false;	
		float d2 = (float) Math.sqrt((x0-x2)*(x0-x2)+(y0-y2)*(y0-y2));	
		if((d2-cr) > d)
			return false;	
		return true;
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
	void drawNumber(GL10 gl,int no,float x, float y,int Align)
	{
		float dx = mGR.mTex_Font[0].width()*.7f;
		String str = no+"";
		float len  = str.length();
		if(Align ==1) //Center
		  x -= (dx*(len))*.5f;
		if(Align ==2)//Right
	  	  x -= (dx*(len))*1f;
		String strs = ""+no;
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i))-48;
			if(k>=0 && k<mGR.mTex_Font.length)
				DrawTexture(gl,mGR.mTex_Font[k],x+i*dx,y);
//				mGR.mTex_Font[k].drawPos(gl,x+i*dx,y);
		}
	}
	void DrawNumberScal(GL10 gl,int no,float x, float y,float Scal,float tr)
	{
		float dx = (mGR.mTex_Font[0].width()*.75f)*Scal;
		 String strs = ""+no;
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i))-48;
			if(k>=0 && k<mGR.mTex_Font.length)
				DrawTransScal(gl,mGR.mTex_Font[k],x+i*dx,y,Scal,tr);
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
		i.putExtra(Intent.EXTRA_SUBJECT,"Rocking new Game '"+GameRenderer.mContext.getResources().getString(R.string.app_name)+"'");
		i.putExtra(Intent.EXTRA_TEXT,"Let the battle commence!!! Download it now and let’s ROCK!!!!  "+M.SHARELINK);
		try {
			GameRenderer.mContext.startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(GameRenderer.mStart, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
}





//void DrawScorBoard(GL10 gl)
//{
//	float py=.25f;
//	float px= 0;
//	float dy=.15f;
//	DrawTexture(gl,mGR.mTex_Splash,0,0);
//	DrawTexture(gl,mGR.mTex_PopUp[1],0,0);
//	DrawTexture(gl,mGR.mTex_ScoreBoard,0,.509f);
//	DrawTexture(gl,mGR.mTex_TargetWindow,px,py);
//	DrawTexture(gl,mGR.mTex_BonusShot,px,py-dy);
//	DrawTexture(gl,mGR.mTex_ExtraShot,px,py-dy*2);
//	DrawTexture(gl,mGR.mTex_PrevScore,px,py-dy*3);
//	DrawTexture(gl,mGR.mTex_NewScore,px,py-dy*4);
//	px =.2f;
//	mGR.mTargetScore=mGR.mBonusScore=mGR.mExtraScore=mGR.mPrevScore=1000;
//	if(sc[NumberCount]<mGR.mTargetScore)
//		sc[NumberCount]+=100;
//	else
//	{
//	   sc[NumberCount]= mGR.mTargetScore;
//	   if(SV[NumberCount]<tmp.length-1)
//	    SV[NumberCount]++;
//	   else
//	   {
//		  NumberCount++;
//	   }
//	}
//// if(NumberCount>0)
//// {
////	if(sc[1]<mGR.mBonusScore)
////		sc[1]+=100;
////	else
////	{
////	   sc[1]= mGR.mBonusScore;
////	   if(SV[1]<tmp.length-1)
////	    SV[1]++;
////	   else
////	   {
////		  NumberCount++;
////	   }
////	}
//// }
//	
////	
////	if(sc[2]<mGR.mExtraScore)
////		sc[2]+=100;
////	else
////	   sc[2]= mGR.mExtraScore;
////	
////	if(sc[3]<mGR.mPrevScore)
////		sc[3]+=100;
////	else
////	   sc[3]= mGR.mPrevScore;
////	
////	mGR.mNewScore =  sc[0]+sc[1]+sc[2]+sc[3];
////	if(sc[4]<mGR.mNewScore)
////		sc[4]+=100;
////	else
////	   sc[4]= mGR.mNewScore;
//	
//	DrawNumberScal(gl,sc[0] ,px,py,tmp[SV[0]],1);
//	if(NumberCount>0)
//	DrawNumberScal(gl,sc[1] ,px,py-dy,tmp[SV[1]],1);
////	DrawNumberScal(gl,sc[2] ,px,py-2*dy,Scal[0],1);
////	DrawNumberScal(gl,sc[3] ,px,py-3*dy,Scal[0],1);
////	DrawNumberScal(gl,sc[4] ,px,py-4*dy,Scal[0],1);
//	
//	
//	
//	
////	DrawNumberScal(gl,sc[1] ,px,py-dy,mGR.ScalCount>0?Scal[1]:0,1);
////	DrawNumberScal(gl,sc[2] ,px,py-dy*2,mGR.ScalCount>1?Scal[2]:0,1);
////	DrawNumberScal(gl,sc[3] ,px,py-dy*3,mGR.ScalCount>2?Scal[3]:0,1);
////	DrawNumberScal(gl,sc[4] ,px,py-dy*4,mGR.ScalCount>3?Scal[4]:0,1);
//	
////	if(mGR.ScalCount<Scal.length)
////	{
////		if(Scal[mGR.ScalCount]>=.8f && isScal)
////		{
////			Scal[mGR.ScalCount]-=sVy;
////			sVy -=.001f;
////		}
////		else
////		{
//////			if(Scal[mGR.ScalCount]<.8f)
//////				sVy=.1f;
////			if(Scal[mGR.ScalCount]<=1f)
////			{
////				isScal =false; 
////				Scal[mGR.ScalCount]+=sVy;
////				sVy +=.001f;
////			}
////			else
////			{
////				mGR.ScalCount++;
////				isScal =true;
////				sVy =.08f;
////			}
////			
////	 	}
////	}
//	
//	SimplePlane Tex=null;
//	if(M.GameScreen == M.GAMEWIN)
//		Tex = mGR.mTex_Continue;
//	else
//		Tex = mGR.mTex_Retry;
//	DrawTexture(gl,Tex,.409f,-.599f);
//	DrawTexture(gl,mGR.mTex_Home,-.5f,-.449f);
//	
//	switch(mGR.mSel)
//	{
//	  case 1:
//		  DrawTexture(gl,mGR.mTex_BtnSel,.409f,-.599f);
//		  break;
//	  case 2:
//		  DrawTexture(gl,mGR.mTex_BackBtnSel,-.5f,-.449f);
//		  break;
//	}
//	
//}


//boolean HandleGame(MotionEvent event)
//{
//	float deltaX=0,deltaY=0;
//	switch(event.getAction())
//	{
//	  case MotionEvent.ACTION_DOWN:
//	  case MotionEvent.ACTION_MOVE:
//		    
//		  if(screen2worldY(event.getY())>-.1f)
//		  	{
//			  
//			   deltaX = (event.getX()-M.ScreenWidth/2);
//			   deltaY = (event.getY()-M.ScreenHieght);
//			   mGR.ang  = (float)Math.atan2(-deltaY,-deltaX);
//		    if(Math.toDegrees(mGR.ang)<20)
//		    	mGR.ang = (float)Math.toRadians(20.0f);
//		    if(Math.toDegrees(mGR.ang)>160)
//		    	mGR.ang = (float)Math.toRadians(160.0f);
//			  
//			  deltaX = screen2worldX(event.getX())-0;
//			  deltaY = screen2worldY(event.getY())-(-.1f);
//			  mGR.dis = (float)Math.sqrt(deltaX*deltaX+deltaY*deltaY);
//			  mGR.dis = (mGR.dis*.2f);
//			  if(mGR.dis<.043f)
//				  mGR.dis =.043f;
//			  if(mGR.dis>.095f)
//				  mGR.dis =.095f;
//		    }
//		  else
//		   {
//			  mGR.ang=(float)Math.toRadians(90f);  
//			  mGR.dis =.043f;
//		   }
////		  System.out.println("ang=============     "+Math.toDegrees(ang)+" Dis==============      "+dis);
//		  break;
//	  case MotionEvent.ACTION_UP:
//		  	if(mGR.mBatsMan.mBatsManAni==0)
//			{
//		  		mGR.vx  = -(float)Math.cos(mGR.ang)*((mGR.dis*.59940f));
//				mGR.vy  = mGR.dis;
//				mGR.mBatsMan.isBatmanHit = true;
//		    }
//		  break;
//	}
//	return true;
//}
//boolean HandleGame(MotionEvent event)
//{
//	
//	float deltaX,deltaY;
//	float dis;
//	if(event.getAction() == MotionEvent.ACTION_DOWN)
//	{
//		deltaX = screen2worldX(event.getX())-0;
//		deltaY = screen2worldY(event.getY())-(mGR.mBatsMan.y);
//		dis = (float)Math.sqrt(deltaX*deltaX+deltaY*deltaY)*.009f;
//		dis = (dis)/.1f;
////		System.out.println("dis=================   "+dis);
//		if(dis<.043f)
//		   dis =.043f;
//		if(dis>.095f)
//		   dis =.095f;
//		
//	 if(mGR.mBatsMan.mBatsManAni==0)
//	  {
//		 mGR.mBatsMan.isBatmanHit = true;
//		 ang = (float)Math.atan2(deltaY,deltaX);
//		 System.out.println("Ang=============     "+ang);
//		 mGR.vx  = (float)Math.cos(ang)*((dis*.69940f));
////		 mGR.vy  = (float)Math.sin(ang)*(dis);
//		 mGR.vy  = dis;
////		 System.out.println("vy================      "+mGR.vy+"vx==================        "+mGR.vx+"  "+"Disssssssssss       "+dis);
//	  }
//	}
//	if(event.getAction() == MotionEvent.ACTION_UP)
//	{
//		
//	}
//	return true;
//}