package com.hututu.game.flapycherrybird;
import javax.microedition.khronos.opengles.GL10;
import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.widget.Toast;
public class Group extends Mesh 
{
	GameRenderer mGR = null;
	int Counter =0,Counter2=0;
	int PopAniCnt=0,AniCount=0,Inc;
	int adCount;
	
	final int Check=100;
	float sx=0,sy=0;
	public void setting(){float ud=.01f;switch (GameRenderer.mStart._keyCode) {case 1:sy-=ud;break;case 2:sy+=ud;break;case 3:sx-=ud;break;case 4:sx+=ud;break;}
	     System.out.println(M.GameScreen+"~~~~~~~~~~~~~~~      "+sx+"~~~~~~~~~~~~       "+sy);
	}
	public boolean Handle(MotionEvent event){
		if(event.getAction() == MotionEvent.ACTION_DOWN)
		{
		  if(CircRectsOverlap(-.7f,0.0f,.2f, .2f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 3;
		  if(CircRectsOverlap(0.7f,0.0f,.2f, .2f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 4;
		  if(CircRectsOverlap(-.0f,-.7f,.2f, .2f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 1;
		  if(CircRectsOverlap(0.0f,0.7f,.2f, .2f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 2;
		}
		if(event.getAction()== MotionEvent.ACTION_UP)
			GameRenderer.mStart._keyCode = 0;
		
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
		 case M.GAMELOGO:
			 DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if(Counter>80)
			{
			    if(!GameRenderer.mStart.addFree)
			       M.GameScreen = M.GAMELOAD;
				else
				{
				   M.GameScreen = M.GAMESPLASH;
				}
				Counter =0;
			}
			break;
	    case Check:
	    	    int no=4;
	    	    mGR.mObst[0].x=0;
	    	    mGR.mObst[0].y=-.4f;
	    	    mGR.mPlayer.x =sx;
	    	    mGR.mPlayer.y =sy;
	    	    
//	    	    float cx[] = {.3f,.3f};
//	    	    float cy[] = {.365f,.345f,.37f,.4f,.48f};
	    	    
	    	    float dx = mGR.mTex_Pipe[no].width()*.18f;
			    float dy = mGR.mTex_Pipe[no].Height()*.48f;
			    
			    DrawTexture(gl,mGR.mTex_BG[0], 0,0);
			    DrawTexturR(gl,mGR.mTex_Bird[Counter/4%mGR.mTex_Bird.length],mGR.mPlayer.x,mGR.mPlayer.y,player.mPlyerang,-mGR.mTex_Bird[0].width()*.45f,mGR.mTex_Bird[0].Height()*.05f);
			    DrawTexture(gl,mGR.mTex_Pipe[no],mGR.mObst[0].x,mGR.mObst[0].y);
			    
			    DrawTextureS(gl,mGR.mTex_BirdPart[0],mGR.mObst[0].x+dx,mGR.mObst[0].y,.2f);
			    DrawTextureS(gl,mGR.mTex_BirdPart[0],mGR.mObst[0].x,mGR.mObst[0].y+dy,.2f);
			    
			    
			    if(CircRectsOverlap(mGR.mObst[0].x,mGR.mObst[0].y,dx,dy,mGR.mPlayer.x-mGR.mTex_Bird[0].width()*.225f,mGR.mPlayer.y-mGR.mTex_Bird[0].Height()*.025f,.04f))
			    {
			    	System.out.println("In Colllision========");
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
			 if(Counter>40)
			    Counter2++;
			  DrawTransScal(gl,mGR.mTex_Splash,0,0,1,Counter<40?1:1-(Counter2*.015f));
			 if(Counter>100)
			 {
			   M.GameScreen = M.GAMEMENU;
			   Counter=0;
			   Counter2=0;
			   mGR.mAni=-30;
			 }
			break;
		case M.GAMEMENU:
			if(mGR.isJoin)
			{
		   	   Drawjoin(gl);
		   	   break; 
			}
			DrawMenu(gl);
			break;
		case M.GAMEOPTION:
			DrawOption(gl);
			break;
		case M.GAMEABTUS:
		case M.GAMEHELP:
			DrawAbtHelp(gl);
			break;
		case M.GAMEOVER:
			if(mGR.isJoin)
			{
		   	   Drawjoin(gl);
		   	   break; 
			}
			DrawGameOver(gl);
			break;
		case M.GAMEPAUSE:
			 DrawPause(gl);
			break; 
		case M.GAMEPLAY:
		case M.GAMEGOINGOVER:
		case M.GAMEGO2PLAY:
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
			  }
			break;
		case M.GAMEADD:
			if((MotionEvent.ACTION_UP == event.getAction())
			  && CirCir(0.9f,.9f, .11f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			  {
				  M.GameScreen = M.GAMEOVER;
				  mGR.mAni=-30;
			  }
			break;
		case M.GAMEGO2PLAY:
			if(event.getAction() == MotionEvent.ACTION_DOWN)
			{
				M.GameScreen = M.GAMEPLAY;
			}
			break;
		case M.GAMEMENU:
			HandleMenu(event);
			break;
		case M.GAMEOPTION:
			HandleOption(event);
			break;
		case M.GAMEHELP:
		case M.GAMEABTUS:
			HandleHelpAbt(event);
			break;
		case M.GAMEOVER:
			 HandleGameOver(event);
			break; 
		case M.GAMEPAUSE:
			 HandlePause(event);
			break;
		case M.GAMEPLAY:
			HandleGame(event);
			break;
		}
	   if(M.GameScreen != M.GAMEPLAY)
	   {
		   if(mGR.mSel!=0 && event.getAction() == MotionEvent.ACTION_DOWN)
			  M.ClickSound(GameRenderer.mContext,R.drawable.button);
			   
	   }
//		Handle(event);
		return true;
	}
void DrawMenu(GL10 gl)
{
//	float scal[] = {1,1.4f,1.2f,1.1f};
	float scal[] = {.7f,.9f,1.2f,1f};
	if(mGR.mSel>0)
	{
		if(Counter%5==0)
	        Counter2++;
	}
	DrawTexture(gl,mGR.mTex_Splash,0,0);
	DrawTransScal(gl,mGR.mTex_Play[0],mGR.mAni-.829f,-.17f,mGR.mSel==1?scal[Counter2%4]:1,mGR.mSel==1?.5f:1f);
	DrawTransScal(gl,mGR.mTex_Play[1],mGR.mAni-.59f ,-.17f,mGR.mSel==1?scal[Counter2%4]:1,mGR.mSel==1?.5f:1);
	
	DrawTransScal(gl,mGR.mTex_Position[0],-mGR.mAni-.829f,-.479f,mGR.mSel==2?scal[Counter2%4]:1,mGR.mSel==2?.5f:1f);
	DrawTransScal(gl,mGR.mTex_Position[1],-mGR.mAni-.59f ,-.479f,mGR.mSel==2?scal[Counter2%4]:1,mGR.mSel==2?.5f:1f);
	
	DrawTransScal(gl,mGR.mTex_Option,.919f,-mGR.mAni-.849f,mGR.mSel==3?scal[Counter2%4]:1,mGR.mSel==3?.5f:1f);
	mGR.mAni*=.8f;
	
	
}
boolean HandleMenu(MotionEvent event)  
{
	mGR.mSel=0;
	if(event.getAction() == MotionEvent.ACTION_DOWN)
	{
		if(mGR.mSel==0)
			Counter2=0;
	}
	if(mGR.isJoin)
		return HandleJoin(event);
	if(CircRectsOverlap(mGR.mAni-.80f,-.17f,mGR.mTex_Play[1].width(),mGR.mTex_Play[1].Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	{
		mGR.mSel=1; // Play
	}
	if(CircRectsOverlap(-mGR.mAni-.80f,-.479f,mGR.mTex_Play[1].width(),mGR.mTex_Play[1].Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	{
		mGR.mSel=2;// Position
	}
	if(CircRectsOverlap(.919f,-mGR.mAni-.849f,mGR.mTex_Option.width()*.5f,mGR.mTex_Option.Height()*.5f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	{
		mGR.mSel=3;// Option
	}
	
	if(event.getAction() == MotionEvent.ACTION_UP)
	{
		switch(mGR.mSel)
		{
		  case 1:
			   M.GameScreen = M.GAMEHELP;
			   mGR.mAni=-30;
			  break;
		  case 2:
			  mGR.isJoin = false;
			  if(GameRenderer.mStart.isSignedIn())
				   GameRenderer.mStart.onShowLeaderboardsRequested();
			  else
			  {
  			     mGR.isJoin = true;
  			     mGR.mAni=30;
			  }
			  break;
		  case 3:
			  SetPopUp();
			  M.GameScreen = M.GAMEOPTION;
			  break;
		}
	   mGR.mSel=0;
	}
	return true;
}
void SetPopUp()
{
	mGR.mPopy   =-2f;
    mGR.mPopvy  = 0f;
    AniCount    = 0;
    PopAniCnt   = 0;
    Inc =1;
}
void DrawOption(GL10 gl)
{
	float x = -.07f;
	float y =.23f;
	float dx=.099f;
	float dy=.23f;
	float scal[] = {1.3f,1.1f,1.2f,.8f};
	
	if(mGR.mSel>0)
	{
	   if(Counter%5==0)
	      Counter2++;
	}
	DrawTexture(gl,mGR.mTex_BG[1],0,0);
	DrawTexture(gl,mGR.mTex_PopUp,0,mGR.mPopy);
    DrawTexture(gl,mGR.mTex_AdFee[0],-.4f+mGR.mPopy,.28f);
    DrawTransScal(gl,mGR.mTex_AdFee[1]				,x 	   ,y+mGR.mPopy    ,mGR.mSel==1?scal[Counter2%4]:1,mGR.mSel==1?.5f:1f);
    DrawTransScal(gl,mGR.mTex_Sound[M.setValue?0:1],x+1*dx,y-1*dy+mGR.mPopy,mGR.mSel==2?scal[Counter2%4]:1,mGR.mSel==2?.5f:1f);
    DrawTransScal(gl,mGR.mTex_AboutUs              ,x+2*dx,y-2*dy+mGR.mPopy,mGR.mSel==3?scal[Counter2%4]:1,mGR.mSel==3?.5f:1f);
    DrawTransScal(gl,mGR.mTex_Back				   ,.429f ,-.339f+mGR.mPopy,mGR.mSel==4?scal[Counter2%4]:1,mGR.mSel==4?.5f:1f);
    
     PopAniCnt   +=Inc;
     mGR.mPopy   +=mGR.mPopvy;
	 mGR.mPopvy  +=(PopAniCnt/5)*.005f;
	 if(mGR.mPopy>=.2f && AniCount ==0)
	  {
	       AniCount = 1;
	       PopAniCnt =-10;
		   Inc =-1;
		   mGR.mPopvy =0;
	  }
	 if(mGR.mPopy<=0f && AniCount ==1)
	  {
		   mGR.mPopy =0;
		   mGR.mPopvy =0;
		   Inc =0;
	 }
}

boolean HandleOption(MotionEvent event)
{
	float x = -.07f;
	float y =.23f;
	float dx=.099f;
	float dy=.23f;
	mGR.mSel=0;
	if(event.getAction() == MotionEvent.ACTION_DOWN)
	{
		if(mGR.mSel==0)
			Counter2=0;
	}
	for(int i=0;i<3;i++)
	{
	  if(CircRectsOverlap(x+i*dx,y-i*dy+mGR.mPopy,mGR.mTex_AboutUs.width()*.4f, mGR.mTex_AboutUs.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		 mGR.mSel =i+1;
	}
	if(CircRectsOverlap(.429f,-.339f+mGR.mPopy,mGR.mTex_Back.width()*.4f, mGR.mTex_Back.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	{
	    mGR.mSel =4;
	}
	
	if(event.getAction() == MotionEvent.ACTION_UP)
	{
		switch(mGR.mSel)
		{
		  case 1:
			  // AdFRee
			  mGR.mMainActivity.onBuyHTT_REMOVE_ADS(null);
			  break;
		  case 2:
			  M.setValue =!M.setValue;
			  break;
		  case 3:
			  M.GameScreen = M.GAMEABTUS;
			  mGR.mAni =-50f;
			  break;
		  case 4:
			  M.GameScreen = M.GAMEMENU;
			  mGR.mAni =-30f;
			  break;
		}
		mGR.mSel=0;
	}
	return true;
}
void DrawAbtHelp(GL10 gl)
{
	DrawTexture(gl,mGR.mTex_BG[0],0,0);
	
	if(M.GameScreen == M.GAMEABTUS)
	{
	   DrawTexture(gl,mGR.mTex_PopUp,mGR.mAni,mGR.mAni);
 	   DrawTexture(gl,mGR.mTex_AboutTxt,-mGR.mAni,-mGR.mAni);
	   DrawTransScal(gl,mGR.mTex_Back,-mGR.mAni+.919f,mGR.mAni-.849f,mGR.mSel==1?1.2f:1,mGR.mSel==1?.5f:1f);
	}
	if(M.GameScreen == M.GAMEHELP)
	{
	   DrawTexture(gl,mGR.mTex_HelpTxt, mGR.mAni,-mGR.mAni);
	   DrawTransScal(gl,mGR.mTex_Play[1],0,mGR.mAni-.749f,mGR.mSel==1?1.2f:1,mGR.mSel==1?.5f:1f);
	   
	}
	
	
	DrawTransScal(gl,mGR.mTex_More,-mGR.mAni+.839f,-mGR.mAni+.869f,mGR.mSel==2?1.2f:1,mGR.mSel==2?.5f:1f);
	DrawTransScal(gl,mGR.mTex_Like,mGR.mAni-.839f,-mGR.mAni+.869f,mGR.mSel==3?1.2f:1,mGR.mSel==3?.5f:1f);

	if(mGR.mAni<=0)
	  mGR.mAni*=.75f;
}


boolean HandleHelpAbt(MotionEvent event)
{
	mGR.mSel=0;
	if(M.GameScreen == M.GAMEABTUS && CircRectsOverlap(-mGR.mAni+.919f,mGR.mAni-.849f,mGR.mTex_Back.width()*.4f, mGR.mTex_Back.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	{
		mGR.mSel=1; //Back 
	}
	if(M.GameScreen == M.GAMEHELP && CircRectsOverlap(0,mGR.mAni-.749f,mGR.mTex_Play[1].width()*.4f, mGR.mTex_Play[1].Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	{
		mGR.mSel=1; //Play
	}
	if(CircRectsOverlap(-mGR.mAni+.839f,-mGR.mAni+.869f,mGR.mTex_More.width()*.4f, mGR.mTex_More.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	{
		mGR.mSel=2; // More
	}
	if(CircRectsOverlap(mGR.mAni-.839f,-mGR.mAni+.869f,mGR.mTex_More.width()*.4f, mGR.mTex_More.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	{
		mGR.mSel=3; // LikeUs
	}
	if(event.getAction() == MotionEvent.ACTION_UP)
	{
		switch(mGR.mSel)
		{
			case 1:
				 if(M.GameScreen == M.GAMEABTUS)
				 {
				   M.GameScreen = M.GAMEOPTION;
				   SetPopUp();
				 }
				 if(M.GameScreen == M.GAMEHELP)
				 {
					 M.GameScreen = M.GAMEGO2PLAY; 
					 mGR.GameReset();
				 }
				break;
			case 2:
				 MoreGame();
				break;
			case 3:
				 FaceBook();
				break;
		}
		mGR.mSel=0;
	}
	return true;
}
int tmp=0;
float sc=1.1f;
void DrawGameOver(GL10 gl)
{
	DrawTexture(gl,mGR.mTex_BG[mGR.mBgNo],0,0);
	DrawTexture(gl,mGR.mTex_PopUp,mGR.mAni+0,0);
	DrawTexture(gl,mGR.mTex_GameOver,0,-mGR.mAni+.26f);
	DrawTexture(gl,mGR.mTex_CurrentScore,mGR.mAni-.069f,.059f);
	  if(mGR.mAni>=-0.001f)
	  {
		  if(tmp<mGR.mCurrentScore)
		  {
		    tmp+=1;
		  }
		  else
		   {
			tmp = mGR.mCurrentScore;
			if(sc>1)
			   sc/=.8f;
			if(sc>2)
			  sc =1;
		   }
	  }	
	  DrawNumberScal(gl,tmp,-mGR.mAni+.21f,.058f,sc,1);
	  DrawTexture(gl,mGR.mTex_BestScore  ,-mGR.mAni-.02f,-.109f);
	  drawNumber(gl,mGR.mBestScore ,mGR.mAni+.21f,-.108f,0);
	
	  DrawTransScal(gl,mGR.mTex_Menu       ,mGR.mAni-.28f,mGR.mAni-.349f  ,mGR.mSel==1?1.2f:1,mGR.mSel==1?.5f:1f);
	  DrawTransScal(gl,mGR.mTex_Position[0],.059f ,mGR.mAni-.349f         ,mGR.mSel==2?1.2f:1,mGR.mSel==2?.5f:1f);
	  DrawTransScal(gl,mGR.mTex_Retry      ,-mGR.mAni+.399f,mGR.mAni-.349f,mGR.mSel==3?1.2f:1,mGR.mSel==3?.5f:1f);
	  mGR.mAni*=.77f;
	  
}
boolean HandleGameOver(MotionEvent event)
{
	mGR.mSel=0;
	if(mGR.isJoin)
		return HandleJoin(event);
	if(CircRectsOverlap(mGR.mAni-.28f,mGR.mAni-.349f,mGR.mTex_Retry.width()*.4f, mGR.mTex_Retry.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	{
		mGR.mSel=1; //Menu
	}
	if(CircRectsOverlap(.059f ,mGR.mAni-.349f,mGR.mTex_Retry.width()*.4f, mGR.mTex_Retry.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	{
		mGR.mSel=2; // LeaderBoard
	}
	if(CircRectsOverlap(-mGR.mAni+.399f,mGR.mAni-.349f,mGR.mTex_Retry.width()*.4f, mGR.mTex_Retry.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	{
		mGR.mSel=3; // Retry
	}
	if(event.getAction() == MotionEvent.ACTION_UP)
	{
		switch(mGR.mSel)
		{
		  case 1:
			    mGR.mAni=-30; 
			    M.GameScreen = M.GAMEMENU;
			  break;
		  case 2:
			  mGR.isJoin = false;
			  if(GameRenderer.mStart.isSignedIn())
				   GameRenderer.mStart.onShowLeaderboardsRequested();
			  else
			  {
  			     mGR.isJoin = true;
  			     mGR.mAni=30;
			  }
		     break;
		  case 3:
			   M.GameScreen = M.GAMEHELP;
			   mGR.mAni=-30;
			  break;
		}
		mGR.mSel=0;
	}
	return true;
}
void DrawPause(GL10 gl)
{
	DrawTexture(gl,mGR.mTex_BG[mGR.mBgNo],0,0);
	DrawTexture(gl,mGR.mTex_PopUp,mGR.mAni+0,0);
	DrawTexture(gl,mGR.mTex_PauseTxt,0-mGR.mAni,+.26f);
	DrawTransScal(gl,mGR.mTex_Menu     ,-.28f,-mGR.mAni-.349f  ,mGR.mSel==1?1.2f:1,mGR.mSel==1?.5f:1f);
	DrawTransScal(gl,mGR.mTex_Play[0]  ,+.399f,mGR.mAni-.349f,mGR.mSel==2?1.2f:1,mGR.mSel==2?.5f:1f);
	
	mGR.mAni*=.77f;
}
boolean HandlePause(MotionEvent event)
{
	mGR.mSel=0;
	if(CircRectsOverlap(-.28f,-mGR.mAni-.349f,mGR.mTex_Retry.width()*.4f, mGR.mTex_Retry.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	{
		mGR.mSel=1; //Menu
	}
//	if(CircRectsOverlap(.059f ,mGR.mAni-.349f,mGR.mTex_Retry.width()*.4f, mGR.mTex_Retry.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
//	{
//		mGR.mSel=2; // LeaderBoard
//	}
	if(CircRectsOverlap(+.399f,mGR.mAni-.349f,mGR.mTex_Retry.width()*.4f, mGR.mTex_Retry.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	{
		mGR.mSel=2; // Retry
	}
	if(event.getAction() == MotionEvent.ACTION_UP)
	{
		switch(mGR.mSel)
		{
		  case 1:
			    mGR.mAni=-30; 
			    M.GameScreen = M.GAMEMENU;
			  break;
		  case 2:
			   M.GameScreen = M.GAMEPLAY;
			  break;
		}
		mGR.mSel=0;
	}
	return true;
}
void GameLogic()
{
	mGR.mPlayer.update();
	for(int i=0;i<mGR.mBg.length;i++)
	{
		mGR.mBg[i].x +=M.speed;
	}
	for(int i=0;i<mGR.mBg.length;i++)
	{
		if(mGR.mBg[i].x<-1-mGR.mTex_BG[0].width()*.5f)
		{
	 	   if(i>0)
			 mGR.mBg[i].x = mGR.mBg[i-1].x+mGR.mTex_BG[0].width();
		   else
			 mGR.mBg[i].x  = mGR.mBg[mGR.mBg.length-1].x+mGR.mTex_BG[0].width();
		}
	}
	for(int i=0;i<mGR.mObst.length;i++)
	{
		mGR.mObst[i].update();
		if(mGR.mObst[i].x<-1.2f)
		{
			if(i>0)
			  mGR.mObst[i].Set(mGR.mObst[i-1].x+.8f,M.randomRange(-1.74f,-.64f),M.mRand.nextInt(5));
			else
			  mGR.mObst[i].Set(mGR.mObst[mGR.mObst.length-1].x+.8f,M.randomRange(-1.60f,-.70f),M.mRand.nextInt(5));
		}
	}
	PipeCollision();
	if(mGR.mCurrentScore>mGR.mBestScore)
	{
		mGR.mBestScore=mGR.mCurrentScore;
	}
	if(mGR.mPlayer.y<-1f)
	{
  	  M.GameScreen = M.GAMEGOINGOVER;
	}
	
}
void PipeCollision()
{
	float cy[] = {.365f,.345f,.37f,.4f,.48f};
	
    float Point[][] ={{-.099f,.099f,.099f,-.099f},
    		  	    {.29f ,.579f,-.169f,-.51f}};
    
    float Point1[][] ={{-.079f,.079f,-.079f},
	  	    		   { .549f,-.040f,-.549f}};
    
    
    for(int i=0;i<mGR.mObst.length;i++)
	{
       float dx,dy;
       float px  = -mGR.mTex_Bird[0].width()*.225f;
       float py  = -mGR.mTex_Bird[0].Height()*.025f;	
        ;
       float diff[] = {.949f,.899f,.969f,1.019f,1.239f};
	   float Ydiff  = mGR.mObst[i].y+mGR.mTex_Pipe[mGR.mObst[i].No].Height()*diff[mGR.mObst[i].No];
	   
	   if(mGR.mObst[i].No<3)
		   dx = mGR.mTex_Pipe[mGR.mObst[i].No].width()*.3f;
	   else
		   dx = mGR.mTex_Pipe[mGR.mObst[i].No].width()*.18f;
	   
	       dy = mGR.mTex_Pipe[mGR.mObst[i].No].Height()*cy[mGR.mObst[i].No];
	       
	   if(CircRectsOverlap(mGR.mObst[i].x,mGR.mObst[i].y,dx,dy,mGR.mPlayer.x+px,mGR.mPlayer.y+py,.04f)
		 ||CircRectsOverlap(mGR.mObst[i].x,Ydiff,dx,dy,mGR.mPlayer.x+px,mGR.mPlayer.y+py,.04f))
		 {
		    SetParticle(mGR.mPlayer.x,mGR.mPlayer.y);
		    M.ColliSound(GameRenderer.mContext,R.drawable.collision);
		    System.out.println("Innnnnnnn Pipeeeeeee"+mGR.mObst[i].No);
		 }
	   if(mGR.mObst[i].No==3)
	   {
		 for(int j=0;j<Point[0].length;j++)
		 {
  	       if(CircRectsOverlap(mGR.mObst[i].x+Point[0][j],mGR.mObst[i].y+Point[1][j],.02f,.06f,mGR.mPlayer.x+px,mGR.mPlayer.y+py,.04f)
		   ||CircRectsOverlap(mGR.mObst[i].x+Point[0][j],Ydiff+Point[1][j],.02f,.06f,mGR.mPlayer.x+px,mGR.mPlayer.y+py,.04f))
		  {
  	    	  System.out.println("Innnnnnnn 3333333");
  	    	  SetParticle(mGR.mPlayer.x,mGR.mPlayer.y);
   	    	  M.ColliSound(GameRenderer.mContext,R.drawable.collision);
		  } 
	     }
	   }
	   if(mGR.mObst[i].No==4)
	   {
		 for(int j=0;j<Point1[0].length;j++)
		 {
  	       if(CircRectsOverlap(mGR.mObst[i].x+Point1[0][j],mGR.mObst[i].y+Point1[1][j],.06f,.085f,mGR.mPlayer.x+px,mGR.mPlayer.y+py,.04f)
		      ||CircRectsOverlap(mGR.mObst[i].x+Point1[0][j],Ydiff+Point1[1][j],.06f,.085f,mGR.mPlayer.x+px,mGR.mPlayer.y+py,.04f))
		   {
  	    	  System.out.println("Innnnnnnn 444444444");
  	    	  SetParticle(mGR.mPlayer.x,mGR.mPlayer.y);
  	    	  M.ColliSound(GameRenderer.mContext,R.drawable.collision);
		   } 
	     }
	   }
	   if(mGR.mObst[i].x>-.4f && mGR.mObst[i].x+M.speed<-.39f)
	   {
		   mGR.mCurrentScore++;
	   }
	}
   	

}
void SetParticle(float x,float y)
{
	for(int i=0;i<mGR.mParticle.length;i++)
	{
		float vx =M.mRand.nextBoolean()?M.mRand.nextInt(15)/1000.0f:-M.mRand.nextInt(15)/1000.0f;
		float vy =M.mRand.nextBoolean()?-M.mRand.nextInt(15)/10000.0f:M.mRand.nextInt(15)/10000.0f;
	    mGR.mParticle[i].set(x,y,vx,vy,i%mGR.mTex_BirdPart.length);
	    M.GameScreen = M.GAMEGOINGOVER;
	    Counter2=0;
	}
}
void Going2over()
{
	if(mGR.mPlayer.y>-.95f)
	{
		mGR.mPlayer.y  +=mGR.mPlayer.vy*.4f;
		mGR.mPlayer.vy += mGR.mPlayer.gravity*.3f; 
		player.mPlyerang+=4.12f;
	}
	else
	{
		mGR.mAni =-30;
		if(!GameRenderer.mStart.addFree && adCount%5==0)
			GameRenderer.mStart.show();
		adCount++;
        M.GameScreen = M.GAMEOVER;
        tmp=0;
        sc=1.1f;
        GameRenderer.mStart.Submitscore(R.string.leaderboard_flapy_cherry_bird,mGR.mBestScore);
        M.OverSound(GameRenderer.mContext,R.drawable.gameover);
	}
}
void DrawGamePlay(GL10 gl)
{
	for(int i=0;i<mGR.mBg.length;i++)
	{
	   if(mGR.mBg[i].x<1+mGR.mTex_BG[0].width()*.5f)
         DrawTexture(gl,mGR.mTex_BG[mGR.mBgNo],mGR.mBg[i].x,0);
	}
	
	for(int i=0;i<mGR.mObst.length;i++)
	{
	  if(mGR.mObst[i].x>-1.2f && mGR.mObst[i].x<1.2f)
	  {
	     DrawTexture(gl,mGR.mTex_Pipe[mGR.mObst[i].No],mGR.mObst[i].x,mGR.mObst[i].y);
	     float diff[] = {.949f,.899f,.969f,1.019f,1.239f};
         float YDiff = mGR.mObst[i].y+mGR.mTex_Pipe[mGR.mObst[i].No].Height()*diff[mGR.mObst[i].No];
	     DrawTexture(gl,mGR.mTex_Pipe[mGR.mObst[i].No],mGR.mObst[i].x,YDiff);
	  }
	}
	DrawTexturR(gl,mGR.mTex_Bird[Counter/4%mGR.mTex_Bird.length],mGR.mPlayer.x,mGR.mPlayer.y,player.mPlyerang,-mGR.mTex_Bird[0].width()*.45f,mGR.mTex_Bird[0].Height()*.05f);
	for(int i=0;i<mGR.mParticle.length;i++)
	{
  	  if(mGR.mParticle[i].y>-1.2f && mGR.mParticle[i].y<1.2f)
  	  {
  		  if(M.GameScreen == M.GAMEGOINGOVER)
  		  {
 		    DrawTextureRS(gl, mGR.mTex_BirdPart[mGR.mParticle[i].No],mGR.mParticle[i].x,mGR.mParticle[i].y,mGR.mParticle[i].z,Counter*5%360);
 		    Counter2++;
 		    DrawTransScal(gl,mGR.mTex_ColliBG,0,0,1,1-Counter2*.003f);
  		  }
  		   mGR.mParticle[i].update();
  	  }
  	   
	}
	DrawTexture(gl,mGR.mTex_Score,-.909f,.909f);
    DrawTexture(gl,mGR.mTex_ScoreBox,-.709f,.899f);
    drawNumber(gl,mGR.mCurrentScore,-.765f,.899f,0);
    if(M.GameScreen == M.GAMEGO2PLAY)
    {
    	mGR.mPlayer.x=-.4f;mGR.mPlayer.y=0;
    	DrawTextureS(gl,mGR.mTex_TapToPlay,.04f,0,1+Math.abs((float)Math.cos(Math.toRadians(Counter*5))*.3f));
	    DrawTexture(gl,mGR.mTex_Hand,0,-.619f+Math.abs((float)Math.cos(Math.toRadians(Counter*5))*.3f));
	    DrawTexturR(gl,mGR.mTex_Bird[Counter/4%mGR.mTex_Bird.length],mGR.mPlayer.x,mGR.mPlayer.y,player.mPlyerang,-mGR.mTex_Bird[0].width()*.45f,mGR.mTex_Bird[0].Height()*.05f);
    }
    
   if(M.GameScreen == M.GAMEPLAY)
   {
	   DrawTransScal(gl, mGR.mTex_Pause,.899f,.849f,mGR.mSel ==1?1.2f:1,mGR.mSel ==1?.5f:1);
	   GameLogic();
   }
   if(M.GameScreen == M.GAMEGOINGOVER)
	   Going2over();
}
boolean HandleGame(MotionEvent event)
{
	mGR.mSel=0;
	if(CircRectsOverlap(.899f,.849f,mGR.mTex_More.width()*.4f, mGR.mTex_More.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
	{ 
		mGR.mSel=1;
	}
	if(event.getAction() == MotionEvent.ACTION_UP)
	{
	   if(mGR.mSel==1)
	   {
		   M.GameScreen = M.GAMEPAUSE;
		   mGR.mAni =-30;
		   mGR.mSel=0;
	   }
	}
	if(mGR.mSel!=0)
		return true;
	if(event.getAction() == MotionEvent.ACTION_DOWN)
	{
		mGR.mPlayer.vy = player.VY;
		M.flapSound(GameRenderer.mContext,R.drawable.birdflap);
	}
	if(event.getAction() == MotionEvent.ACTION_UP)
	{
	}
	return true;
}
void Drawjoin(GL10 gl)
{
	DrawTexture(gl,mGR.mTex_BG[mGR.mBgNo],0,0);
	DrawTexture(gl,mGR.mTex_PopUp, mGR.mAni-0,0);
    DrawTexture(gl,mGR.mTex_Join[1],-.01f,mGR.mAni+.16f);
    DrawTransScal(gl,mGR.mTex_Join[0],.059f,-mGR.mAni-.329f,mGR.mSel==1?1.2f:1,mGR.mSel==1?.5f:1);
    DrawTransScal(gl,mGR.mTex_Back,mGR.mAni+.439f,-.339f,mGR.mSel==2?1.2f:1,mGR.mSel==2?.5f:1);
    mGR.mAni*=.7f;
}
boolean HandleJoin(MotionEvent event)
{
	mGR.mSel =0;
	if(CircRectsOverlap(.059f,-mGR.mAni-.329f,mGR.mTex_Join[0].width()*.5f, mGR.mTex_Join[0].Height()*.5f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		mGR.mSel =1;
	if(CircRectsOverlap(mGR.mAni+.439f,-.339f,mGR.mTex_Back.width()*.5f, mGR.mTex_Back.Height()*.5f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		mGR.mSel =2;
	if(event.getAction() == MotionEvent.ACTION_UP)
	{ 
		if(mGR.mSel ==1)
		{
			GameRenderer.mStart.beginUserInitiatedSignIn();
			mGR.isJoin = false;
		}
		if(mGR.mSel ==2)
		{
			mGR.isJoin = false;
			mGR.mAni   =-30;
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
		float dx = (mGR.mTex_Font[0].width()*.7f)*Scal;
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

