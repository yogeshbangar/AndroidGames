package com.oneday.games24.crisisofrail;
import javax.microedition.khronos.opengles.GL10;
import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.widget.Toast;
public class Group extends Mesh 
{
	GameRenderer mGR = null;
	int Counter =0,Counter2=0;
	final int Check=100;
	float sx=0,sy=0;
	public void setting(){float ud=.01f;switch (GameRenderer.mStart._keyCode) {case 1:sy-=ud;break;case 2:sy+=ud;break;case 3:sx-=ud;break;case 4:sx+=ud;break;}
	     System.out.println(M.GameScreen+"~~~~~~~~~~~~~~~      "+sx+"~~~~~~~~~~~~       "+sy);
	}
	
	public boolean Handle(MotionEvent event){
		if(CircRectsOverlap(-.7f,0.0f,.3f, .3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))
		{
			GameRenderer.mStart._keyCode = 3;
		}
		if(CircRectsOverlap(0.7f,0.0f,.3f, .3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))
		{
		    GameRenderer.mStart._keyCode = 4;
		}
		if(CircRectsOverlap(.0f,-.7f,.3f, .3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 1;
		if(CircRectsOverlap(0f,0.7f,.3f, .3f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 2;
		if(event.getAction()== MotionEvent.ACTION_UP)
		{
			GameRenderer.mStart._keyCode = 0;
		}
		return true;
	}

	public Group(GameRenderer _GameRenderer){
		mGR = _GameRenderer;
	}

    float ax,ay;
    
	public void draw(GL10 gl) 
	{
	  Counter++;
//	  System.out.println("        "+mGR.mTex_Test.width()+"               "+mGR.mTex_Test.Height());
	  switch (M.GameScreen)
	   {
			  case Check:
//				  DrawTexture(gl,mGR.mTex_SandBg,0,0);
//				  for(int i=0;i<M.ROW;i++)
//				   {
//				     for(int j=0;j<M.COL;j++)
//				     {
//				       if(mGR.mTile[i][j].mTNo>0)
//				       {
//					      DrawTexture(gl, mGR.mTex_Tile[(mGR.mTile[i][j].mTNo-1)%mGR.mTex_Tile.length],mGR.mTile[i][j].x+mGR.mHilaVal,mGR.mTile[i][j].y-mGR.mHilaVal);
//				       }
//				     }
//				   }
//				  DrawTextureSS(gl,mGR.mTex_Life,0,.2f,.72f,1,0,0);
//				  DrawTextureSS(gl,mGR.mTex_Red,-.052f,.2f,22,2,mGR.mTex_Red.width()*.5f,0);
//				  DrawTexture(gl,mGR.mTex_StopBtn,0,0);
//				  DrawTexture(gl,mGR.mTex_Signal[0],0,0);
				break;
			  case M.GAMELOGO:
				 DrawTexture(gl, mGR.mTex_Logo, 0, 0);
				 if(Counter>80)
				 {
				    M.GameScreen = M.GAMEMENU;
					Counter =0;
				 }
				break;
//			  case M.GAMELOADING:
//				 if(Counter>100)
//					DrawTexture(gl,mGR.mTex_Exit, .9f,.9f);
//				else
//				{
//					DrawTexture(gl, mGR.mTex_LoadBar,0.0f,-.9f);
//					DrawTexture(gl, mGR.mTex_Pointer,Counter*(mGR.mTex_LoadBar.width()/100f)-.32f,-.9f);
//				}
//				break;
			  case M.GAMEMENU:
				 DrawMenu(gl);
				 break;
			  case M.GAMEWORLD:
				  DrawWorld(gl);
				  break;
			  case M.GAMELEVEL:
				  DrawLevel(gl);
				 break;
			  case M.GAMEPLAY:
			  case M.GAMESTART:
			  case M.GAMEWIN:
			  case M.GAMEOVER:
			  case M.GAMECONG:
			  case M.GAMEPAUSE:
				  DrawGameplay(gl);
				 break;	
	   }
//		setting();
	}
	
public boolean TouchEvent(MotionEvent event) 
{
	  switch (M.GameScreen)
	  {
			case Check:
				break;
//			case M.GAMELOADING:
//				if((MotionEvent.ACTION_UP == event.getAction()) && (Counter>100)
//				   && CirCir(0.9f,.9f,.11f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
//				   {
//						M.GameScreen = M.GAMESTART;
//						Counter=0;
//				   }
//				break;
			case M.GAMEMENU:
				HandleMenu(event);
				break;
			case M.GAMEWORLD:
				HandleWorld(event);
				break;
			case M.GAMELEVEL:
				HandleLevel(event);
				break;
			case M.GAMEPAUSE:
			case M.GAMEOVER:
			case M.GAMEWIN:
			case M.GAMECONG:
				HandleWPO(event);
				break;
			case M.GAMESTART:
				 if(event.getAction() == MotionEvent.ACTION_UP)
				 {
					 if(M.setValue)
					 	M.BGPlay(GameRenderer.mContext,R.drawable.game_theme);
					 else
						M.BgStop(); 
					 M.GameScreen = M.GAMEPLAY;
				 }
				break;
			case M.GAMEPLAY:
			     HandleGame(event);
				break;
   	  }
	  if(event.getAction() == MotionEvent.ACTION_DOWN && mGR.mSel!=0)
	  {
		  if(M.GameScreen != M.GAMEPLAY)
		     M.clickSound(GameRenderer.mContext,R.drawable.button_click);
	  }
	  Handle(event);
	return true;
}
void DrawMenu(GL10 gl)
{
	DrawTexture(gl,mGR.mTex_Splash,0,0);
	 if(Counter%8==0)
		 Counter2++;
	 DrawTransScal(gl,mGR.mTex_Tap2Start,0,-.4f,1,Counter2%4==0?1:.5f);
	 DrawTransScal(gl,mGR.mTex_Gplus  ,.509f,-.819f,mGR.mSel==1?1.1f:1f,mGR.mSel==1?.5f:1f);
	 DrawTransScal(gl,mGR.mTex_FB     ,.699f,-.819f,mGR.mSel==2?1.1f:1f,mGR.mSel==2?.5f:1f);
	 DrawTransScal(gl,mGR.mTex_Twitter,.889f,-.819f,mGR.mSel==3?1.1f:1f,mGR.mSel==3?.5f:1f);
	 
	 DrawTransScal(gl,mGR.mTex_Icn	 ,-.879f,.799f,mGR.mSel==4?1.1f:1f,mGR.mSel==4?.5f:1f);
	 DrawTransScal(gl,mGR.mTex_Achiev,-.879f,.799f,mGR.mSel==4?1.1f:1f,mGR.mSel==4?.5f:1f);
	 
	 DrawTransScal(gl,mGR.mTex_Icn	 ,-.879f,.469f,mGR.mSel==5?1.1f:1f,mGR.mSel==5?.5f:1f);
	 DrawTransScal(gl,mGR.mTex_Leder ,-.879f,.469f,mGR.mSel==5?1.1f:1f,mGR.mSel==5?.5f:1f);
	 
	 DrawTransScal(gl,mGR.mTex_CirIcn				,-.889f,-.819f,mGR.mSel==7?1.1f:1f,mGR.mSel==7?.5f:1f);
	 DrawTransScal(gl,mGR.mTex_Sound[M.setValue?0:1],-.889f,-.819f,mGR.mSel==7?1.1f:1f,mGR.mSel==7?.5f:1f);
}
boolean HandleMenu(MotionEvent event)
{
	  mGR.mSel=0;
	for(int i=0;i<3;i++)
	 if(CircRectsOverlap(.509f+i*.19f,-.819f,mGR.mTex_Gplus.width()*.45f,mGR.mTex_Gplus.Height()*.45f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	 {
		mGR.mSel=i+1;
	 }
	 if(CircRectsOverlap(-.879f,.799f,mGR.mTex_Icn.width()*.45f,mGR.mTex_Icn.Height()*.45f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	 {
		mGR.mSel=4;//Achieve
	 }
	 if(CircRectsOverlap(-.879f,.469f,mGR.mTex_Icn.width()*.45f,mGR.mTex_Icn.Height()*.45f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	 {
		mGR.mSel=5;//Leader
	 }
	 if(CircRectsOverlap(0,-.13f,mGR.mTex_Tap2Start.width()*.6f,mGR.mTex_Tap2Start.Height()*.6f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	 {
		mGR.mSel=6;//Tap2Start
	 }
	 if(CircRectsOverlap(-.889f,-.819f,mGR.mTex_Icn.width()*.45f,mGR.mTex_Icn.Height()*.45f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	 {
		mGR.mSel=7;//Sound
	 }
	if(event.getAction() == MotionEvent.ACTION_UP)
	{
		switch(mGR.mSel) 
		{
		   case 1:
			   Google();
			   break;
		   case 2:
			   FaceBook();
			   break;
		   case 3:
			   Twitter();
			  break;
		   case 4:
			   GameRenderer.mStart.onShowAchievementsRequested();
			   break;
		   case 5:
			   GameRenderer.mStart.onShowLeaderboardsRequested();
			   break;
		   case 6:
			   if(M.setValue)
				  M.SplashPlay(GameRenderer.mContext,R.drawable.ui);
			   else
				  M.BgStop();
			   SetWorld();
			   M.GameScreen = M.GAMEWORLD;
			   break;
		   case 7:
			   M.setValue=!M.setValue;
			   break;
			   
		}
		mGR.mSel=0;
	}
	return true;
}
void DrawWorld(GL10 gl)
{
	DrawWorldPath(gl);
	for(int i=0;i<2;i++)
	{
	  DrawTexture(gl,mGR.mTex_WorldShadow,-.429f+(i*.429f*2f),-.1f);
	  DrawTransScal(gl,mGR.mTex_World[i],-.429f+(i*.429f*2f),-.1f,mGR.mSel==i+1?1.2f:1,mGR.mSel==i+1?.5f:1);
	  if(mGR.mUnlockLev<=i*24)
	  {
		  DrawTransScal(gl,mGR.mTex_WorldLock,-.429f+(i*.429f*2f),-.025f,mGR.mSel==i+1?1.2f:1,mGR.mSel==i+1?.5f:1);
	  }
	}
	DrawTransScal(gl,mGR.mTex_CirIcn   ,.909f,-.849f,mGR.mSel==100?1.1f:1f,mGR.mSel==100?.6f:1);
    DrawTransScal(gl,mGR.mTex_BackBtn  ,.909f,-.849f,mGR.mSel==100?1.1f:1f,mGR.mSel==100?.6f:1f);
}
boolean HandleWorld(MotionEvent event)
{
	  mGR.mSel=0;
	for(int i=0;i<2;i++)
	 if(CircRectsOverlap(-.429f+(i*.429f*2f),-.1f,mGR.mTex_World[0].width()*.45f,mGR.mTex_World[0].Height()*.45f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	 {
		mGR.mSel=i+1;
	 }
	 if(CircRectsOverlap(.909f,-.849f,mGR.mTex_CirIcn.width()*.45f,mGR.mTex_CirIcn.Height()*.45f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	 {
		mGR.mSel=100;//Back
	 }
	if(event.getAction() == MotionEvent.ACTION_UP)
	{
		switch(mGR.mSel) 
		{
		   case 1:case 2:
			   int k =mGR.mSel-1;
			   if(mGR.mUnlockLev>k*24)
			    {
			      M.GameScreen = M.GAMELEVEL;
			      mGR.mWorldNo= mGR.mSel-1;
 			      SetWorld(); 
			    }
			    break;
		   case 100:
			    M.StopSound();
			    M.GameScreen = M.GAMEMENU;
			   break;
		}
		mGR.mSel=0;
	}
	return true;
}
void DrawLevel(GL10 gl)
{
	DrawWorldPath(gl);
	for(int i=0;i<24;i++)
	{
		DrawTexture(gl,mGR.mTex_BtnShadow,-.749f+(i%6)*.299f,.489f-(i/6)*.349f);
		DrawTransScal(gl,mGR.mTex_Icn,-.749f+(i%6)*.299f,.489f-(i/6)*.349f,mGR.mSel==i+1?1.1f:1f,mGR.mSel==i+1?.5f:1);
		if(mGR.mUnlockLev<=i+(mGR.mWorldNo*24))
		  DrawTransScal(gl,mGR.mTex_LevelLock,-.749f+(i%6)*.299f,.489f-(i/6)*.349f,mGR.mSel==i+1?1.1f:1f,mGR.mSel==i+1?.5f:1);
		else
	 	 drawNumber(gl,i+1,-.749f+(i%6)*.299f,.489f-(i/6)*.349f,1);
	}
	DrawTransScal(gl,mGR.mTex_CirIcn   ,.909f,-.849f,mGR.mSel==100?1.1f:1f,mGR.mSel==100?.6f:1);
    DrawTransScal(gl,mGR.mTex_BackBtn  ,.909f,-.849f,mGR.mSel==100?1.1f:1f,mGR.mSel==100?.6f:1f);
}
boolean HandleLevel(MotionEvent event)
{
	  mGR.mSel=0;
	for(int i=0;i<24;i++)
	 if(CircRectsOverlap(-.749f+(i%6)*.299f,.489f-(i/6)*.349f,mGR.mTex_Icn.width()*.45f,mGR.mTex_Icn.Height()*.45f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	 {
		mGR.mSel=i+1;
	 }
	 if(CircRectsOverlap(.909f,-.849f,mGR.mTex_CirIcn.width()*.45f,mGR.mTex_CirIcn.Height()*.45f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	 {
		mGR.mSel=100;//Back
	 }
	if(event.getAction() == MotionEvent.ACTION_UP)
	{
		switch(mGR.mSel) 
		{
		   default:
		      if(mGR.mSel>0)
			   {
		    	  int k =mGR.mSel-1;
		    	  if(mGR.mUnlockLev>k+(mGR.mWorldNo*24))
		    	  {
		    	    mGR.mLevelNo = mGR.mSel+(mGR.mWorldNo*24);
				    mGR.GameReset();
		    	  }
			   }
			   break;
		   case 100:
			    SetWorld(); 
			    M.GameScreen = M.GAMEWORLD;
			   break;
		}
		mGR.mSel=0;
	}
	return true;
}
void DrawWPO(GL10 gl)
{
	DrawTexture(gl,mGR.mTex_Board,0,0);
	if(M.GameScreen==M.GAMEWIN)
	  DrawTexture(gl,mGR.mTex_GameWin,0,.389f);
	else if(M.GameScreen==M.GAMEOVER)
	  DrawTexture(gl,mGR.mTex_GameOver,0,.389f);
	else if(M.GameScreen==M.GAMECONG)
	  DrawTexture(gl,mGR.mTex_Cong,0,.389f);
	else
	  DrawTexture(gl,mGR.mTex_GamePause ,0,.389f);
	
	DrawTransScal(gl,mGR.mTex_Icn  ,-.399f,.069f,mGR.mSel==1?1.2f:1,mGR.mSel==1?.5f:1f);
	DrawTransScal(gl,mGR.mTex_Menu ,-.399f,.069f,mGR.mSel==1?1.2f:1,mGR.mSel==1?.5f:1f);
	
	DrawTransScal(gl,mGR.mTex_Icn  ,-.16f,.069f,mGR.mSel==2?1.2f:1,mGR.mSel==2?.5f:1);
	DrawTransScal(gl,mGR.mTex_Retry,-.16f,.069f,mGR.mSel==2?1.2f:1,mGR.mSel==2?.5f:1);
	
	if(M.GameScreen != M.GAMEOVER && M.GameScreen != M.GAMECONG)
	{
	  DrawTransScal(gl,mGR.mTex_Icn ,-.28f,-.309f,mGR.mSel==3?1.2f:1,mGR.mSel==3?.5f:1f);
	  DrawTransScal(gl,M.GameScreen==M.GAMEWIN?mGR.mTex_Next:mGR.mTex_Play,-.28f,-.309f,mGR.mSel==3?1.2f:1,mGR.mSel==3?.5f:1f);
	}
	
	DrawTransScal(gl,mGR.mTex_Btn     ,.22f,.059f ,mGR.mSel==4?1.2f:1,mGR.mSel==4?.5f:1f);
	DrawTransScal(gl,mGR.mTex_LeadeTxt,.22f,.059f ,mGR.mSel==4?1.2f:1,mGR.mSel==4?.5f:1f);
	
	DrawTransScal(gl,mGR.mTex_Btn     ,.22f,-.310f,mGR.mSel==5?1.2f:1,mGR.mSel==5?.5f:1f);
	DrawTransScal(gl,mGR.mTex_AchTxt  ,.22f,-.310f,mGR.mSel==5?1.2f:1,mGR.mSel==5?.5f:1f);
	
	DrawTransScal(gl,mGR.mTex_Gplus  ,-.509f,-.819f,mGR.mSel==6?1.1f:1f,mGR.mSel==6?.5f:1f);
	DrawTransScal(gl,mGR.mTex_FB     ,-.699f,-.819f,mGR.mSel==7?1.1f:1f,mGR.mSel==7?.5f:1f);
	DrawTransScal(gl,mGR.mTex_Twitter,-.889f,-.819f,mGR.mSel==8?1.1f:1f,mGR.mSel==8?.5f:1f);
	
//	DrawTransScal(gl,mGR.mTex_RemoveAds,.659f,-.819f,mGR.mSel==20?1.1f:1f,mGR.mSel==20?.5f:1f);
	
}
boolean HandleWPO(MotionEvent event)
{
	mGR.mSel=0;
	if(CircRectsOverlap(-.399f,.069f,mGR.mTex_CirIcn.width()*.45f,mGR.mTex_CirIcn.Height()*.45f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	 {
		mGR.mSel=1;//Home
	 }
	if(CircRectsOverlap(-.16f,.069f,mGR.mTex_CirIcn.width()*.45f,mGR.mTex_CirIcn.Height()*.45f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	 {
		mGR.mSel=2;//Retry
	 }
	if(CircRectsOverlap(-.28f,-.309f,mGR.mTex_CirIcn.width()*.45f,mGR.mTex_CirIcn.Height()*.45f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	 {
	   if(M.GameScreen != M.GAMEOVER && M.GameScreen != M.GAMECONG)
		   mGR.mSel=3;//Next Or Continue
	 }
	if(CircRectsOverlap(.22f,.059f,mGR.mTex_Btn.width()*.45f,mGR.mTex_Btn.Height()*.45f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	 {
		mGR.mSel=4;//Leader
	 }
	if(CircRectsOverlap(.22f,-.310f,mGR.mTex_Btn.width()*.45f,mGR.mTex_Btn.Height()*.45f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	 {
		mGR.mSel=5;//Achie
	 }
	for(int i=0;i<3;i++)
		 if(CircRectsOverlap(-.509f-i*.19f,-.819f,mGR.mTex_Gplus.width()*.45f,mGR.mTex_Gplus.Height()*.45f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		 {
			mGR.mSel=i+6;
		 }
//	if(CircRectsOverlap(.659f,-.819f,mGR.mTex_RemoveAds.width()*.45f,mGR.mTex_RemoveAds.Height()*.45f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
//		mGR.mSel=20; //AdFree 
		
	if(event.getAction() == MotionEvent.ACTION_UP)
	{
		switch(mGR.mSel) 
		{
		   case 1:
			   	  if(M.setValue)
			   		  M.SplashPlay(GameRenderer.mContext,R.drawable.ui);
			      SetWorld();
			      M.GameScreen = M.GAMEWORLD;
			   break;
		   case 2:
			      mGR.GameReset();
			   break;
		   case 3:
			     if(M.GameScreen == M.GAMEWIN)
			     {
			    	 mGR.mLevelNo++;
			    	 mGR.GameReset();
			     }
			     else
			     {
			    	if(M.setValue)
			    	  M.BGPlay(GameRenderer.mContext,R.drawable.game_theme);
			    	else
			    	  M.StopSound();
			    	M.GameScreen = M.GAMEPLAY;
			     }
			   break;
		   case 4:
			    GameRenderer.mStart.onShowLeaderboardsRequested();
			   break;
		   case 5:
			    GameRenderer.mStart.onShowAchievementsRequested();
			   break;
		   case 6:
			   Google();
			   break;
		   case 7:
			   FaceBook();
			   break;
		   case 8:
			   Twitter();
			  break;
		   case 20:
//			    mGR.mMainActivity.onBuyAdfree(null);
			   break;
		   
		}
		mGR.mSel=0;
	}
	return true;
}
float getDis(float x1,float y1,float x2,float y2)
{
	float d=0;
	 float X = x2-x1;
	 float Y = y2-y1;
	       d = (float)Math.sqrt(X*X+Y*Y); 
	return d;
}
void gamelogic()
{
	
	for(int i=0;i<mGR.mTrainNo;i++) // Train-Train 
	{
		float irad,jrad;
		int Imul,Jmul;
		if(mGR.mBogi[i].x!=100 && mGR.mBogi[i].y!=100)
		{
			irad =.041f;
			Imul  =13;
		}
		else
		{
			irad =.02f;
			Imul  = 8;
		}
		for(int j=i+1;j<mGR.mTrainNo;j++)
		{	
			if(mGR.mBogi[j].x!=100 && mGR.mBogi[j].y!=100)
			{
				jrad =.041f;
				Jmul  =13;
			}
			else
			{
				jrad =.02f;
				Jmul  = 8;
			}
			for(int e = 0; e < 3; e++) 
			{
				for(int d = 0; d < 3; d++)
				{
					if(CirCir(mGR.mMidPoint[i].x+((-Imul+e*Imul)*mGR.mMidPoint[i].vx),mGR.mMidPoint[i].y+((-Imul+e*Imul)*mGR.mMidPoint[i].vy),irad,
							  mGR.mMidPoint[j].x+((-Jmul+d*Jmul)*mGR.mMidPoint[j].vx),mGR.mMidPoint[j].y+((-Jmul+d*Jmul)*mGR.mMidPoint[j].vy),jrad))
					{
						if(!mGR.mTrain[i].isCollide|| !mGR.mTrain[j].isCollide)
						{
							mGR.mTrain[i].isCollide =true;
							mGR.mTrain[j].isCollide =true;
							mGR.mTrain[i].ang+=15;
							mGR.mTrain[j].ang-=15;
							mGR.mHilanaCnt=0;
							M.CrashSound(GameRenderer.mContext,R.drawable.crash);
						}
						mGR.mGameLevel.TrainTukker(i);
						mGR.mGameLevel.TrainTukker(j);
					}
				}
			}
		}
	}
    for(int i=0;i<mGR.mSignalNo;i++) //Train
     {
	   if(mGR.mSignal[i].isRedSignal)
	   {
		  mGR.mSignal[i].update();
   	      for(int t=0;t<mGR.mTrainNo;t++)
	  	  {  	   
	  	     if(CircRectsOverlap(mGR.mSignal[i].x-(10*mGR.mMidPoint[t].vx),mGR.mSignal[i].y-(15*mGR.mMidPoint[t].vy),mGR.mTex_StopBtn.width()*.5f,mGR.mTex_StopBtn.Height()*.5f,mGR.mMidPoint[t].x,mGR.mMidPoint[t].y,.05f))
			  {
				 mGR.mTrain[t].SlowSpeed(.5f);
				 mGR.mBogi[t].SlowSpeed(.5f);
			     if(mGR.mSignal[i].mSignalTime<1)
				 {
				    mGR.mTrain[t].speed=1;
			        mGR.mBogi[t].speed=1;
				 }
			  }
		   }
		}
	 }
    for(int i=0;i<mGR.mBomb.length;i++)
    {
       if(mGR.mBomb[i].x>-1.2f && mGR.mBomb[i].x<1.2f)
       {
	      for(int t=0;t<mGR.mTrainNo;t++)
	      {
	         if(getDis(mGR.mBomb[i].x-(10*mGR.mTrain[t].vx),mGR.mBomb[i].y-(15*mGR.mTrain[t].vy),mGR.mMidPoint[t].x,mGR.mMidPoint[t].y)<(mGR.mTrain[t].x!=100?.1f:.04f))
		     {
	    	      mGR.mTrain[t].SlowSpeed(.5f);
			      mGR.mBogi[t].SlowSpeed(.5f);
			      if(mGR.mBombNo<1 && !mGR.mBomb[i].isBlast)
			      {
			    	  mGR.mLoosecnt++;
			      }
			      if(mGR.mBomb[i].isBlast && mGR.mBomb[i].mBombCnt>=mGR.mTex_BlastAni.length)
				  {
			         mGR.mTrain[t].speed=1;
					 mGR.mBogi[t].speed=1;
					 mGR.mBomb[i].reset();
					 mGR.mWaitCnt=0;
				  }
			      
		     }
	      }
        }
	}
	for(int ir=0;ir<M.ROW;ir++)
	{
	  for(int jc=0;jc<M.COL;jc++)
	   {	
		 if(mGR.mLevelNo>0)
		  {
			  mGR.mGameLevel.LevelLogic(ir, jc);
		  }
	   }
	}
	int over=18;
    for(int i=0;i<mGR.mTrainNo;i++)
    {
 	   mGR.mTrain[i].update();
	   mGR.mBogi[i].update();
	  if(mGR.mTrain[i].isMove && mGR.mTrain[i].speed>0)
	  {
	     mGR.mTrain[i].rCnt++;
	    if(mGR.mTrain[i].rCnt>=over)
	     mGR.mTrain[i].resetMudna();
	  }
	  if(mGR.mBogi[i].isMove && mGR.mBogi[i].speed>0)
	  {
	     mGR.mBogi[i].rCnt++;
	    if(mGR.mBogi[i].rCnt>=over)
	     mGR.mBogi[i].resetMudna();
	  }
    }
    for(int i=0;i<mGR.mArrowNo;i++)
      mGR.mArrow[i].update();
    if(mGR.isHilana)
	  {
		 hilana();
	  }
    if(mGR.mWinCnt>=mGR.mTrainNo)
    {
       if(mGR.mWaitCnt==10)
    	 M.BgStop();
    	mGR.mWaitCnt++;
    	if(mGR.mUnlockLev<mGR.mLevelNo+1)
    	{
  		   mGR.mUnlockLev = mGR.mLevelNo+1;
  		   GameRenderer.mStart.Submitscore(R.string.leaderboard_levelcross,mGR.mUnlockLev);
    	}
    	if(mGR.mWaitCnt>200)
    	{
    		CheckAchie();
    		if(M.setValue)
			  M.SplashPlay(GameRenderer.mContext,R.drawable.ui);
    		
    		if(mGR.mLevelNo<48)
    		  M.GameScreen = M.GAMEWIN;
    		else
    		  M.GameScreen = M.GAMECONG;
    		mGR.mWaitCnt=0;
    	  if(!GameRenderer.mStart.addFree)
    		 GameRenderer.mStart.ShowHandle();
    	}
    }
    if(mGR.mLoosecnt>0)
    {
      mGR.is30Level =true;
      if(mGR.mWaitCnt==10)
    	  M.BgStop();
    	if(mGR.mWaitCnt==150)
    	  M.OverSound(GameRenderer.mContext,R.drawable.game_over);
    	mGR.mWaitCnt++;
    	if(mGR.mWaitCnt>200)
    	{
    	   if(M.setValue)
  			  M.SplashPlay(GameRenderer.mContext,R.drawable.ui);
    		M.GameScreen = M.GAMEOVER;
    		mGR.mWaitCnt=0;
    	   if(!GameRenderer.mStart.addFree && mGR.mAdCnt%2==0)
    		   GameRenderer.mStart.ShowHandle();
    	     mGR.mAdCnt++;
    	}
    }
}

int val[] = {10,20,30,40,48};
int A[]={
	R.string.achievement_unlock_10_level,
	R.string.achievement_unlock_20_level,
	R.string.achievement_unlock_30th_level,
	R.string.achievement_unlock_40th_level,
	R.string.achievement_final_destination,
};
void CheckAchie()
{
	for(int i=0;i<val.length;i++)
	{
		if(mGR.mUnlockLev>=val[i] && !mGR.isAchieve[i])
		{
			mGR.isAchieve[i] =true;
			GameRenderer.mStart.UnlockAchievement(A[i]);
		}
	}
	if(!mGR.is30Level && !mGR.isAchieve[5])
	{
		if(mGR.mUnlockLev>=30)
		{
			mGR.isAchieve[5]= true;
			GameRenderer.mStart.UnlockAchievement(R.string.achievement_30th_level_without_lost);
		}
	}
}
void SetSmoke(int id,float x,float y,float trainVY)
{
	int cr =0;
	for(int j=0;j<mGR.mSmoke[id].length && cr<1;j++)
	{
		if(mGR.mSmoke[id][j].x == mGR.mSmoke[id][j].y)
		{
	       if(mGR.mSmoke[id][j].Cnt%2==1)
	       {
		     mGR.mSmoke[id][j].Set(x,y,trainVY);
	       }
	       cr++;
		}
		mGR.mSmoke[id][j].Cnt++;
	}
}
void ResetSmoke(int id)
{
	for(int j=0;j<mGR.mSmoke[id].length;j++)
	{
   	  mGR.mSmoke[id][j].x =mGR.mSmoke[id][j].y=100;
	}
}
void SetParticle(float x,float y)
{
	 int k=0;
	 for(int i=0;i<mGR.mParticle.length && k<50;i++)
	 {
		if(mGR.mParticle[i].x>1.1f && mGR.mParticle[i].y>1.1f)
		{
	      mGR.mParticle[i].SetParticle(x,y);
		  k++;
		}
	 }
}
void DrawParticle(GL10 gl)
{
	for(int i=0;i<mGR.mParticle.length;i++)
	  {
		  if(mGR.mParticle[i].x>-1.1f && mGR.mParticle[i].x<1.1f &&  mGR.mParticle[i].y>-1.1f && mGR.mParticle[i].y<1.1f)
		  {
			  DrawRGB(gl,mGR.mTex_Partical[mGR.mParticle[i].no],mGR.mParticle[i].x,mGR.mParticle[i].y,mGR.mParticle[i].r,mGR.mParticle[i].g,mGR.mParticle[i].b,mGR.mParticle[i].t,mGR.mParticle[i].z);
			  mGR.mParticle[i].updateParticle();
		  }
	  }
}
void DrawSmoke(int id,GL10 gl)
{
   for(int j=0;j<mGR.mSmoke[id].length;j++)
   	{
		if(mGR.mSmoke[id][j].x>-1.2&&mGR.mSmoke[id][j].x<1.2&&mGR.mSmoke[id][j].y>-1.2&&mGR.mSmoke[id][j].y<1.2)
		{
			DrawSmoke(gl,mGR.mTex_Smoke,mGR.mSmoke[id][j].x+mGR.mHilaVal, mGR.mSmoke[id][j].y+mGR.mHilaVal,mGR.mSmoke[id][j].z,mGR.mSmoke[id][j].t);
		   mGR.mSmoke[id][j].update();
		}
	}
}
void DrawBg(GL10 gl,float r,float g,float b,float t)
{
	if(mGR.mLevelNo<25)
	{
 	  for(int i=0;i<14;i++)
	   {
		  for(int j=0;j<8;j++)
		  {
		   	 DrawRGB(gl,mGR.mTex_Grass,(-.939f+i*mGR.mTex_Grass.width())+mGR.mHilaVal,(.899f-j*mGR.mTex_Grass.Height())-mGR.mHilaVal,r,g,b,t,1);
		  }
	   }
	}	
	else
	{
		DrawRGB(gl,mGR.mTex_SandBg,0+mGR.mHilaVal,0-+mGR.mHilaVal,r,g,b,t,1);
	}
}
void DrawGameplay(GL10 gl)
{
       DrawBg(gl,1,1,1,1);
	   for(int i=0;i<mGR.mSignalNo;i++)
	   {
		  if(mGR.mSignal[i].mSignalTime>150 && mGR.mSignal[i].isRedSignal) 
		  {
			if(Counter%4==0)
			  Counter2++; 
		     DrawTransScal(gl,mGR.mTex_StopBtn,mGR.mSignal[i].x+mGR.mHilaVal,mGR.mSignal[i].y-mGR.mHilaVal,1,Counter2%2==0?.5f:1.2f);
		  }
		  else
		     DrawTransScal(gl,mGR.mTex_StopBtn,mGR.mSignal[i].x+mGR.mHilaVal,mGR.mSignal[i].y-mGR.mHilaVal,mGR.mSel==i+50?1.2f:1,mGR.mSel==i+50?.5f:1f);
	   }
	   for(int i=0;i<M.ROW;i++)
	   {
	     for(int j=0;j<M.COL;j++)
	     {
	       if(mGR.mTile[i][j].mTNo>0)
	       {
		      DrawTexture(gl, mGR.mTex_Tile[(mGR.mTile[i][j].mTNo-1)%mGR.mTex_Tile.length],mGR.mTile[i][j].x+mGR.mHilaVal,mGR.mTile[i][j].y-mGR.mHilaVal);
	       }
	     }
	   }
	   for(int i=0;i<mGR.mArrowNo;i++)
	   {
		 DrawTransScal(gl,mGR.mTex_StopBtn,mGR.mArrow[i].x+mGR.mHilaVal,mGR.mArrow[i].y-mGR.mHilaVal,mGR.mSel==i+1?1.2f:1,mGR.mSel==i+1?.5f:1f);
		 if(mGR.mArrow[i].ang==mGR.mArrow[i].origAng)
	        DrawTextureR(gl,mGR.mTex_Arrow[0],mGR.mArrow[i].x+mGR.mHilaVal,mGR.mArrow[i].y-mGR.mHilaVal,mGR.mArrow[i].ang,0,0,false);
	     else
	     {
    	   if(mGR.mArrow[i].isNegative)
	          DrawTextureR(gl,mGR.mTex_Arrow[1],mGR.mArrow[i].x+mGR.mHilaVal,mGR.mArrow[i].y-mGR.mHilaVal,mGR.mArrow[i].ang+45,0,0,false);
    	   else
    		  DrawTextureR(gl,mGR.mTex_Arrow[1],mGR.mArrow[i].x+mGR.mHilaVal,mGR.mArrow[i].y-mGR.mHilaVal,mGR.mArrow[i].ang+135,0,0,false);
	     }
	   }
	   for(int i=0;i<mGR.mBomb.length;i++)
	   {
	       for(int k=0;k<mGR.mBomb[i].bx.length;k++)  
	    	   DrawTexture(gl,mGR.mTex_Stone[k],mGR.mBomb[i].bx[k]+mGR.mHilaVal,mGR.mBomb[i].by[k]-mGR.mHilaVal);
          if(mGR.mBomb[i].isBlast)
		   {
	    	 if(mGR.mBomb[i].mBombCnt<mGR.mTex_BlastAni.length)
		        DrawTexture(gl,mGR.mTex_BlastAni[mGR.mBomb[i].mBombCnt%mGR.mTex_BlastAni.length],mGR.mBomb[i].x+mGR.mHilaVal,mGR.mBomb[i].y-mGR.mHilaVal);
	    	 if(Counter%4==0)
	    	 {
	           mGR.mBomb[i].mBombCnt++;
	    	  if(mGR.mBomb[i].mBombCnt==4)
	    		  M.blastSound(GameRenderer.mContext,R.drawable.blast);
	    	 }
	    	 if(mGR.mBomb[i].mBombCnt>2 && mGR.mBomb[i].mBombCnt<5)
		       {
			      mGR.mBomb[i].update();
		       }
		   }
	   }
	   for(int i=0;i<mGR.mTrainNo;i++)
	   {

	     DrawTextureR(gl,mGR.mTex_Bogi[mGR.mBogi[i].no],mGR.mBogi[i].x+mGR.mHilaVal,mGR.mBogi[i].y-mGR.mHilaVal,mGR.mBogi[i].ang,0,0,mGR.mBogi[i].isCollide);
	     DrawTextureR(gl,mGR.mTex_Train[mGR.mTrain[i].no],mGR.mTrain[i].x+mGR.mHilaVal,mGR.mTrain[i].y-mGR.mHilaVal,mGR.mTrain[i].ang,0,0,mGR.mTrain[i].isCollide);
	     
	     ax  = (float)Math.cos(mGR.mTrain[i].rad)*.035f; 
		 ay  = (float)Math.sin(mGR.mTrain[i].rad)*.045f;
		 SetSmoke(i,mGR.mTrain[i].x+ax,mGR.mTrain[i].y-ay,mGR.mTrain[i].vy);
	     DrawSmoke(i,gl);
		   if(mGR.mBogi[i].x!=100 && mGR.mBogi[i].y!=100)
			   mGR.mMidPoint[i].Set(mGR.mTrain[i].x,mGR.mTrain[i].y,mGR.mBogi[i].x,mGR.mBogi[i].y,mGR.mTrain[i].vx,mGR.mTrain[i].vy,mGR.mBogi[i].vx,mGR.mBogi[i].vy);
		   else
			   mGR.mMidPoint[i].Set2(mGR.mTrain[i].x,mGR.mTrain[i].y,mGR.mTrain[i].vx,mGR.mTrain[i].vy);
	   }
	   DrawTexture(gl,mGR.mTex_Red,0,0);
	   for(int i=0;i<mGR.mSignalNo;i++)
	   {
		   if(mGR.mSignal[i].isRedSignal)
		     DrawTexture(gl,mGR.mTex_Signal[mGR.mSignal[i].no],mGR.mSignal[i].x+mGR.mHilaVal,mGR.mSignal[i].y-mGR.mHilaVal);
		   if(mGR.mSignal[i].mSignalTime>0)
		   {
			 DrawTextureSS(gl,mGR.mTex_Life,mGR.mSignal[i].x+mGR.mHilaVal,(mGR.mSignal[i].y+.1f)-mGR.mHilaVal,.7f,1,0,0);
			 DrawTextureSS(gl,mGR.mTex_Red,(mGR.mSignal[i].x-.052f)+mGR.mHilaVal,(mGR.mSignal[i].y+.1f)-mGR.mHilaVal,(200-mGR.mSignal[i].mSignalTime)/9.01f,2f,mGR.mTex_Red.width()*.5f,0);
		   }
//		     drawNumber2(gl,mGR.mSignal[i].mLimit+"",mGR.mSignal[i].x+mGR.mHilaVal,(mGR.mSignal[i].y+.12f)-mGR.mHilaVal);
	   }
	   for(int i=0;i<mGR.mFlag.length;i++)
	   {
		   if(mGR.mFlag[i].x>-1.1f && mGR.mFlag[i].x<1.1 && mGR.mFlag[i].y>-1.1f && mGR.mFlag[i].y<1.1f)
		   {
		     DrawRGB(gl,mGR.mTex_Flag[0][(mGR.mFlag[i].Cnt/4)%10],mGR.mFlag[i].x+mGR.mHilaVal,mGR.mFlag[i].y-mGR.mHilaVal,mGR.mFlag[i].r,mGR.mFlag[i].g,mGR.mFlag[i].b,1,1);
		     DrawTexture(gl,mGR.mTex_Flag[1][(mGR.mFlag[i].Cnt/4)%10],mGR.mFlag[i].x+mGR.mHilaVal,mGR.mFlag[i].y-mGR.mHilaVal);
		     mGR.mFlag[i].Cnt++;
		   }
	   }
	  
	   if(mGR.mWaitCnt==0 && (M.GameScreen == M.GAMEPLAY || M.GameScreen == M.GAMESTART))
	   {
	     DrawTransScal(gl,mGR.mTex_CirIcn ,.909f,-.849f,mGR.mSel==100?1.1f:1f,mGR.mSel==100?.5f:1f);
         DrawTransScal(gl,mGR.mTex_Pause  ,.909f,-.849f,mGR.mSel==100?1.1f:1f,mGR.mSel==100?.5f:1f);
         
		 DrawTexture(gl,mGR.mTex_Box,-.849f,.879f);
		 DrawTexture(gl,mGR.mTex_Level,-.899f,.879f);
		 drawNumber2(gl,(mGR.mLevelNo<25?mGR.mLevelNo:mGR.mLevelNo-24)+"",-.799f,.879f);
		   
		 DrawTexture(gl,mGR.mTex_Box,.849f,.879f);
		 DrawTexture(gl,mGR.mTex_Bomb,.779f,.879f);
		 drawNumber2(gl,"x"+mGR.mBombNo+"",.849f,.879f);
	   }
       if(M.GameScreen == M.GAMEPLAY)
       {
           gamelogic();
       }
       DrawParticle(gl);
       if(mGR.mWaitCnt>150)
       {
    	   DrawBg(gl,0,0,0,(mGR.mWaitCnt-150)*.02f);
       }
       else
       {
	       switch(M.GameScreen)
	       {
	         case M.GAMEWIN:
	         case M.GAMECONG:
	         case M.GAMEOVER:
	         case M.GAMEPAUSE:
	        	 DrawWPO(gl);
	        	 break;
	         case M.GAMESTART:
	        	 if(Counter%8==0)
	   			  Counter2++;
	       		 DrawTransScal(gl,mGR.mTex_Tap2Start,0,-.13f,1,Counter2%4==0?1:.5f);
	        	 break;
	       }
       }
	   
}
int CheckArrowTrain(int i)
{
	for(int t=0;t<mGR.mTrainNo;t++)
	{
		float trad=0;
		if(mGR.mBogi[t].x!=100 && mGR.mBogi[t].y!=100)
			trad = .17f;
		else
			trad =.09f;
		if(getDis(mGR.mArrow[i].x,mGR.mArrow[i].y,mGR.mMidPoint[t].x,mGR.mMidPoint[t].y)<trad)
		{
			return -1;
		}
	}
	return 0;
}
boolean HandleGame(MotionEvent event)
{
	mGR.mSel=0;
	int aNo=0;
	if(CircRectsOverlap(.909f,-.849f,mGR.mTex_CirIcn.width()*.6f,mGR.mTex_CirIcn.Height()*.6f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	{
	   if(mGR.mWaitCnt==0)
	   {
		  mGR.mSel=100; //Pause
		  M.clickSound(GameRenderer.mContext,R.drawable.button_click);
	   }
	}
	for(int i=0;i<mGR.mArrowNo;i++)
	{
      	  if(!mGR.mArrow[i].isMove && CircRectsOverlap(mGR.mArrow[i].x,mGR.mArrow[i].y,mGR.mTex_StopBtn.width()*.6f,mGR.mTex_StopBtn.Height()*.6f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
      	  {
      		aNo = CheckArrowTrain(i);
      		if( aNo !=-1)
      	 	 {
      			mGR.mSel=i+1;
      		 }
      	  }
	}
	for(int i=0;i<mGR.mSignalNo;i++)
	{
	  if(CircRectsOverlap(mGR.mSignal[i].x,mGR.mSignal[i].y,mGR.mTex_StopBtn.width()*.6f,mGR.mTex_StopBtn.Height()*.6f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		mGR.mSel=i+50;
	}
	for(int i=0;i<mGR.mBomb.length;i++)
	{
	  if(mGR.mBombNo>0 && CircRectsOverlap(mGR.mBomb[i].x,mGR.mBomb[i].y,mGR.mTex_StopBtn.width()*.6f,mGR.mTex_StopBtn.Height()*.6f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		 mGR.mSel=i+500;
	}
	if(event.getAction() == MotionEvent.ACTION_UP)
	{
		switch(mGR.mSel)
		{
		  default:
			  for(int i=0;i<mGR.mArrowNo;i++)
			  {
			    if(i+1==mGR.mSel)
			    {
			    	M.arrowSound(GameRenderer.mContext,R.drawable.arrow_click);
			    	updateArrow(i);
				}
			  }
			  for(int i=0;i<mGR.mSignalNo;i++)
			  {
				  if(i+50==mGR.mSel)
				  {
					 mGR.mSignal[i].ActiveSignal();
			      }
			  }
			  for(int i=0;i<mGR.mBomb.length;i++)
			  {
				  if(i+500==mGR.mSel)
				  {
					 if(!mGR.mBomb[i].isBlast)
					 {
					   mGR.mBomb[i].ActiveBomb();
					   mGR.mBombNo--;
					 }
			      }
			  }
			 break;
		  case 100:
			    M.StopSound();
			    M.GameScreen = M.GAMEPAUSE;
			  break;
		}
		mGR.mSel=0; 
	}
	return true;
}
void updateArrow(int i)
{
	     mGR.mArrow[i].Open++;
	  if(mGR.mArrow[i].Open>2)
	     mGR.mArrow[i].Open=1;
  	  if(mGR.mArrow[i].Open==1)
  	  { 
  		 if(!mGR.mArrow[i].isNegative)
	         mGR.mArrow[i].setMudna(90,1);
  		 else
  			mGR.mArrow[i].setMudna(-90,2);
  	  }
  	  if(mGR.mArrow[i].Open==2)
  	  {
  		if(!mGR.mArrow[i].isNegative)
	       mGR.mArrow[i].setMudna(-90,2);
  		else
  	  	   mGR.mArrow[i].setMudna(90,1);
  	  }
}
void hilana()
{
	
	 if(mGR.mHilanaCnt<40)
	 {
	   float rad 	  = (float)Math.toRadians(mGR.mHilanaCnt*2f);
	   mGR.mHilaVal   = (float)(Math.sin(rad*60f)*.02f);
	   mGR.mHilanaCnt++;
//	   System.out.println("innnnnnnnnnnnnnnn Counttt"+mGR.mHilanaCnt);
	 }
	 else
	 {
		 mGR.isHilana =false;
		 mGR.mHilanaCnt=100;
		 mGR.mHilaVal  =0;
	 }
}
void SetWorld()
{
	int no=0;
     mGR.mTrainNo=2;
	for(int i=0;i<M.ROW;i++)
	{
		for(int j=0;j<M.COL;j++)
		{
			switch(mGR.mWorldNo)
			{
				case 0:
					no= mGR.mTile[i][j].mTNo = Level.menu1[j][i];
					break;
				case 1:
					no= mGR.mTile[i][j].mTNo = Level.menu2[j][i];
					break;
			}
			mGR.mTile[i][j].set(-.935f+i*mGR.mTex_Tile[0].width(),.925f-j*mGR.mTex_Tile[0].Height(),no);
		}
	}
	if(mGR.mWorldNo==0)
	{
		
		mGR.mTrain[0].set(-.929f,-.539f,270,M.BROWNTRAIN);
		mGR.mBogi[0].set(-.929f ,-.539f,270,M.BROWNTRAIN);
		
		mGR.mTrain[1].set(-.09f,.929f,180,M.GREENTRAIN);
		mGR.mBogi[1].set(-.09f,.929f,180,M.GREENTRAIN);
		
//		mGR.mTrain[2].set(.109f,.26f,0,M.BLUETRAIN);
//		mGR.mBogi[2].set(.109f ,.26f,0,M.BLUETRAIN);
		
	}
	else
	{
		mGR.mTrain[0].set(.639f,-.20f,270,M.BROWNTRAIN);
		mGR.mBogi[0].set(.639f,-.20f,270,M.BROWNTRAIN);
		
		mGR.mTrain[1].set(-.299f,.659f,180,M.GREENTRAIN);
		mGR.mBogi[1].set(-.299f,.659f,180,M.GREENTRAIN);
		
//		mGR.mTrain[2].set(.419f,-.0f,270,M.BLUETRAIN);
//		mGR.mBogi[2].set(.419f ,-.0f,270,M.BLUETRAIN);
		
	}
	for(int i=0;i<mGR.mTrainNo;i++)
	{
		mGR.mBogi[i].x+=5*mGR.mBogi[i].vx;
		mGR.mBogi[i].y+=5*mGR.mBogi[i].vy;
		mGR.mTrain[i].x+=25*mGR.mTrain[i].vx;
		mGR.mTrain[i].y+=25*mGR.mTrain[i].vy;
	}
}
void DrawWorldPath(GL10 gl )
{
	if(mGR.mWorldNo==0)
	{
	   for(int i=0;i<14;i++)
		   for(int j=0;j<8;j++)
		   	 DrawTexture(gl,mGR.mTex_Grass,(-.939f+i*mGR.mTex_Grass.width()),(.899f-j*mGR.mTex_Grass.Height()));
	}
	else
	   	 DrawTexture(gl,mGR.mTex_SandBg,0,0);
	
	for(int i=0;i<M.ROW;i++)
	   {
	     for(int j=0;j<M.COL;j++)
	     {
	       if(mGR.mTile[i][j].mTNo>0)
	       {
		      DrawTexture(gl, mGR.mTex_Tile[(mGR.mTile[i][j].mTNo-1)%mGR.mTex_Tile.length],mGR.mTile[i][j].x+mGR.mHilaVal,mGR.mTile[i][j].y-mGR.mHilaVal);
	       }
	     }
	   }
	for(int i=0;i<mGR.mTrainNo;i++)
	   {
	     DrawTextureR(gl,mGR.mTex_Bogi[mGR.mBogi[i].no],mGR.mBogi[i].x+mGR.mHilaVal,mGR.mBogi[i].y-mGR.mHilaVal,mGR.mBogi[i].ang,0,0,mGR.mBogi[i].isCollide);
	     DrawTextureR(gl,mGR.mTex_Train[mGR.mTrain[i].no],mGR.mTrain[i].x+mGR.mHilaVal,mGR.mTrain[i].y-mGR.mHilaVal,mGR.mTrain[i].ang,0,0,mGR.mTrain[i].isCollide);
	     ax  = (float)Math.cos(mGR.mTrain[i].rad)*.035f; 
		 ay  = (float)Math.sin(mGR.mTrain[i].rad)*.045f;
		 SetSmoke(i,mGR.mTrain[i].x+ax,mGR.mTrain[i].y-ay,mGR.mTrain[i].vy);
	     DrawSmoke(i,gl);
	   }
	
	for(int i=0;i<mGR.mTrainNo;i++)
    {
 	   mGR.mTrain[i].update();
	   
	  if(mGR.mTrain[i].isMove && mGR.mTrain[i].speed>0)
	  {
	     mGR.mTrain[i].rCnt++;
	    if(mGR.mTrain[i].rCnt>=18)
	     mGR.mTrain[i].resetMudna();
	  }
	  mGR.mBogi[i].update();
	  if(mGR.mBogi[i].isMove && mGR.mBogi[i].speed>0)
	  {
	     mGR.mBogi[i].rCnt++;
	    if(mGR.mBogi[i].rCnt>=18)
	     mGR.mBogi[i].resetMudna();
	  }
    }
	for(int ir=0;ir<M.ROW;ir++)
	{
	  for(int jc=0;jc<M.COL;jc++)
	   {	
		  mGR.mGameLevel.LevelLogic(ir, jc);
	   }
	}
}
void DrawTexture(GL10 gl,SimplePlane Tex,float x,float y)
{
	Tex.drawPos(gl, x, y);
}
void DrawTextureR(GL10 gl,SimplePlane Tex,float x,float y,float angle,float rotX,float rotY,boolean isRevA)
{
	Tex.drawRotet(gl, x, y,angle,rotX,rotY,isRevA);
}
void DrawTexturRPS(GL10 gl,SimplePlane Tex,float x,float y,float angle,float rotX,float rotY,float scal)
{
	Tex.drawRotetSP(gl, x, y,angle,rotX,rotY,scal);
}
void DrawTextureS(GL10 gl,SimplePlane Tex,float x,float y,float scal)
{
	Tex.drawScal(gl, x, y,scal,scal);
}
void DrawTextureSS(GL10 gl,SimplePlane Tex,float x,float y,float sx,float sy,float w,float h)
{
	Tex.drawSS(gl, x, y,sx,sy,w,h);
}
void DrawRGB(GL10 gl,SimplePlane Tex,float x,float y,float r,float g,float b,float t,float s)
{
	Tex.drawRGBS(gl, x, y, r, g, b,t,s);
}
void DrawTransScal(GL10 gl,SimplePlane Tex,float x,float y, float z,float t)
{
	Tex.drawTransprentScal(gl, x, y, z, t);
}
void DrawSmoke(GL10 gl,SimplePlane Tex,float x,float y, float z,float t)
{
	Tex.drawSmoke(gl, x, y, z, t);
}

boolean CircRectsOverlap(double CRX,  double CRY,double CRDX,double CRDY ,double centerX,double centerY, double radius)
{
    if ((Math.abs(centerX - CRX) <= (CRDX + radius)) && (Math.abs(centerY - CRY) <= (CRDY + radius)))
       return true;
    return false ;

}
boolean Rext2RectColli(float x1,float y1,float dx1,float dy1,float x2,float y2,float dx2,float dy2)
{
//	x1 -= dx1/2;
//	y1 -= dy1/2;
//	
//	x2 -= dx2/2;
//	y2 -= dy2/2;
	
 if(x1 <=x2+dx2 && x1+dx1>=x2 && y1<=y2+dy2 && dy1+y1>=y2)
    return true;
 
  return false;
}

boolean CirCir(double cx1,double cy1, double r1,double cx2,double cy2, double r2)
{
	float bVectMag = (float) Math.sqrt(((cx1-cx2)*(cx1-cx2)) + ((cy1-cy2)*(cy1-cy2)));
	if (bVectMag<(r1+r2))
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

int XPos(float x){
        return (int)(((1+x)*M.ScreenWidth)/2);
}

int YPos(float y){
    return (int)(((1-(y))*M.ScreenHieght)/2);
}
	
void drawNumber(GL10 gl,int no,float x, float y,int Align)
{
	float dx = mGR.mTex_Font[0].width()*.6f;
	String str = no+"";
	float len  = str.length();
	if(Align ==1) //Center
	  x -= (dx*(len))*.2f;
	if(Align ==2)//Right
  	  x -= (dx*(len))*1f;
	String strs = ""+no;
	for(int i =0;i<strs.length();i++)
	{
		int k = ((int)strs.charAt(i))-48;
		if(k>=0 && k<mGR.mTex_Font.length)
			DrawTexture(gl,mGR.mTex_Font[k],x+i*dx,y);
	}
}
void drawNumber2(GL10 gl,String str,float x, float y)
{
	float dx = mGR.mTex_Font[0].width()*.509f;
	for(int i =0;i<str.length();i++)
	{
		int k;
		
		if(str.charAt(i) == 'x')
		  k=10;
		else
		  k= ((int)str.charAt(i))-48;
	  if(k>=0 && k<mGR.mTex_Font1.length)
		 DrawTexture(gl,mGR.mTex_Font1[k],x+i*dx,y);
	}
}

void Twitter()
{
	Intent mIntent = new Intent(Intent.ACTION_VIEW);
	mIntent.setData(Uri.parse("https://twitter.com/hututu_games"));
    GameRenderer.mContext.startActivity(mIntent);
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
