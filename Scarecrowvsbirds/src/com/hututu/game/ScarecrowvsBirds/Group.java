package com.hututu.game.ScarecrowvsBirds;
import javax.microedition.khronos.opengles.GL10;


import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Toast;
public class Group extends Mesh 
{
	
	GameRenderer mGR = null;
	
	float sx=0,sy=0;
	int Counter =0;
	final int Check=100; 
	public Group(GameRenderer _GameRenderer)
	{
		mGR = _GameRenderer;
	}
	public void setting()
	{
		switch (GameRenderer.mStart._keyCode) {
		case KeyEvent.KEYCODE_DPAD_DOWN:
			sy-=.01f;
			break;
		case KeyEvent.KEYCODE_DPAD_UP:
			sy+=.01f;
			break;
		case KeyEvent.KEYCODE_DPAD_LEFT:
			sx-=.01f;
			break;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			sx+=.01f;
			break;
		}
		Log.d("~~~~~~~~~~~~~~~      "+sx,"~~~~~~~~~~~~       "+sy);
	}
	@Override
	public void draw(GL10 gl) 
	{
		switch (M.GameScreen) {
		case M.GAMELOGO:
			Counter++;
			DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if(Counter>80)
			{
				M.GameScreen = M.GAMESPLASH;
				M.GameScreen = M.GAMEADD;//AdHouse
				Counter=0;//AdHouse
			}
			break;
			/*AdHouse*/
		case M.GAMEADD:
			Counter++;
			if(Counter>100)
				DrawTexture(gl,mGR.mTex_Skip, .9f,.9f);
			else{
				DrawTexture(gl, mGR.mTex_Hightbar,0.2f,0.9f);
				DrawTexture(gl, mGR.mTex_Pointer,.2f+Counter*(mGR.mTex_Hightbar.width()/100f)-.32f,0.9f);
			}
			break;
			/*AdHouse*/
//		case Check:
//			  DrawTexture(gl,mGR.mTex_GameBg,0,0);
//			  DrawTexture(gl,mGR.mTex_ThronsPower,mGR.mPowerActive.x,0);
//			  DrawTexture(gl,mGR.mTex_ThronsBig,mGR.mPowerActive.x,-.5f);
//		    break;
		case M.GAMESPLASH:
			 Counter++;
			 DrawTexture(gl,mGR.mTex_Splash,0,0);
			 if(Counter>80)
			 {
			   M.GameScreen = M.GAMEMENU;
			   Counter=0;
			 }
			break;
		case M.GAMEMENU:
			DrawMenu(gl);
			break;
		case M.GAMEPLAY:
			DrawGamePlay(gl);
			break;
		case M.GAMEPAUSE:
			DrawPause(gl);
			break;
		case M.GAMEOVER:
		case M.GAMEWIN:
			DrawGameover(gl);
			break;
		case M.GAMEHELP:
		case M.GAMEABTUS:
		case M.GAMEHIGHSCORE:		
			drawAbtUs(gl);
			break;
		}
	}
	public boolean TouchEvent(MotionEvent event) 
	{
		Log.d("  "+M.GameScreen,"   "+M.GameScreen);
		switch (M.GameScreen) {
		case M.GAMELOGO:
			break;
			/*AdHouse*/
		case M.GAMEADD:
			if((MotionEvent.ACTION_UP == event.getAction())&&(Counter>100)&&
					CirCir(0.9f,0.9f, .11f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				M.GameScreen = M.GAMESPLASH;Counter=0;
			}
			break;
			/*AdHouse*/
		case M.GAMEMENU:
			HandleMenu(event);
			break;
		case M.GAMEPLAY:
			HandleGame(event);
			break;
		case M.GAMEPAUSE:
			HandlePause(event);
			break;
		case M.GAMEOVER:
		case M.GAMEWIN:
			HandleGameOver(event);
			break;
		case M.GAMEHELP:
		case M.GAMEABTUS:
			HandleAbtUS(event);
			break;
		case M.GAMEHIGHSCORE:
			HandleHighScore(event);
			break;
		}
		return true;
		
	}
	
	void DrawMenu(GL10 gl)
	{
		DrawTexture(gl,mGR.mTex_Splash,0,0);
		DrawTexture(gl,mGR.mTex_PlayBtn[0],0,-0.5f);
	    DrawTexture(gl,mGR.mTex_Iconbar,mGR.mControl.x,mGR.mControl.y);
	    DrawTexture(gl,mGR.mTex_Iconbar,mGR.mSetting.x,mGR.mSetting.y);
	    DrawTexture(gl,mGR.mTex_RateUs,mGR.mControl.x,mGR.mControl.y+.4f);
		DrawTexture(gl,mGR.mTex_Share,mGR.mControl.x,mGR.mControl.y+.05f	);
		DrawTexture(gl,mGR.mTex_hututuicn,mGR.mControl.x,mGR.mControl.y-.3f);
		if(M.setValue)
		 DrawTexture(gl,mGR.mTex_SoundIcn[0],mGR.mSetting.x,mGR.mSetting.y+.4f);
		else
		 DrawTexture(gl,mGR.mTex_SoundIcn[1],mGR.mSetting.x,mGR.mSetting.y+.4f);
		 DrawTexture(gl,mGR.mTex_HelpIcn,mGR.mSetting.x,mGR.mSetting.y+.05f);
		 DrawTexture(gl,mGR.mTex_AbtUsIcn,mGR.mSetting.x,mGR.mSetting.y-.3f);
		
		switch(mGR.mSel)
		{
		   case 1:
			   DrawTexture(gl,mGR.mTex_PlayBtn[1],0,-0.5f);
			  break;
		   case 2:
			  DrawTexture(gl,mGR.mTex_Select,mGR.mControl.x,mGR.mControl.y+.4f);
			  break;
		   case 3:
			   DrawTexture(gl,mGR.mTex_Select,mGR.mControl.x,mGR.mControl.y+.05f);
			 break;
		   case 4:
			   DrawTexture(gl,mGR.mTex_Select,mGR.mControl.x,mGR.mControl.y-.3f);
			 break; 
		   case 5:
			  DrawTexture(gl,mGR.mTex_Select,mGR.mSetting.x,mGR.mSetting.y+.4f);
			  break;
		   case 6:
			   DrawTexture(gl,mGR.mTex_Select,mGR.mSetting.x,mGR.mSetting.y+.05f);
			 break;
		   case 7:
			   DrawTexture(gl,mGR.mTex_Select,mGR.mSetting.x,mGR.mSetting.y-.3f);
			 break;
		}
		DrawTexture(gl,mGR.mTex_Trans[0],-1+mGR.mTex_Trans[0].width()/2,-1+mGR.mTex_Trans[0].Height()/2);
	    DrawTexture(gl,mGR.mTex_Trans[1],1-mGR.mTex_Trans[0].width()/2,-1+mGR.mTex_Trans[0].Height()/2);
		DrawTextureR(gl,mGR.mTex_Control,-.85f,-.8f,mGR.mControl.ang);
		DrawTextureR(gl,mGR.mTex_ArrowBtn,.85f,-.8f,mGR.mSetting.ang);
	    mGR.mControl.update();
		mGR.mSetting.update();
	
	}
	boolean HandleMenu(MotionEvent event)
	{
	  mGR.mSel=0;
   	  if(event.getAction() == MotionEvent.ACTION_DOWN)
	   {
			 
		   if(CircRectsOverlap(-.85f,-.8f,mGR.mTex_Control.width()*.3f,mGR.mTex_Control.Height()*.3f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
			  {
			    if(mGR.mControl.Open ==1)
			     {
			       mGR.mControl.Open = 0;
			       mGR.mControl.vy   =-.05f;
			     }
			    else
			     {
				   mGR.mControl.Open = 1;
			       mGR.mControl.vy = +.05f;
			     }
			  }
		   if(CircRectsOverlap(.85f,-.8f,mGR.mTex_ArrowBtn.width()*.3f,mGR.mTex_ArrowBtn.Height()*.3,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
			  {
			    if(mGR.mSetting.Open ==1)
			     {
			       mGR.mSetting.Open = 0;
			       mGR.mSetting.vy   =-.05f;
			     }
			    else
			     {
				   mGR.mSetting.Open = 1;
			       mGR.mSetting.vy = +.05f;
			     }
			  }
          }
  		  if(CircRectsOverlap(0,-.5f,mGR.mTex_PlayBtn[0].width()*.4f,mGR.mTex_PlayBtn[0].Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
  			   mGR.mSel=1;
		  if(mGR.mControl.Open == 1 && mGR.mControl.y>-.5f)
		  {
		    if(CircRectsOverlap(mGR.mControl.x,mGR.mControl.y+.4f,mGR.mTex_RateUs.width()*.3f,mGR.mTex_Control.Height()*.3f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		       mGR.mSel=2;//RateUs;
		    if(CircRectsOverlap(mGR.mControl.x,mGR.mControl.y+.05f,mGR.mTex_Share.width()*.3f,mGR.mTex_Control.Height()*.3f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
			   mGR.mSel=3;//Share
		    if(CircRectsOverlap(mGR.mControl.x,mGR.mControl.y-.3f,mGR.mTex_hututuicn.width()*.3f,mGR.mTex_Control.Height()*.3f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
			   mGR.mSel=4;//Hututu
		  }
		  if(mGR.mSetting.Open == 1 && mGR.mSetting.y>-.5f)
		  {
			  if(CircRectsOverlap(mGR.mSetting.x,mGR.mSetting.y+.4f,mGR.mTex_RateUs.width()*.3f,mGR.mTex_Control.Height()*.3f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
			       mGR.mSel=5;//Sound;
			  if(CircRectsOverlap(mGR.mSetting.x,mGR.mSetting.y+.05f,mGR.mTex_RateUs.width()*.3f,mGR.mTex_Control.Height()*.3f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
				   mGR.mSel=6;//Help
			  if(CircRectsOverlap(mGR.mSetting.x,mGR.mSetting.y-.3f,mGR.mTex_RateUs.width()*.3f,mGR.mTex_Control.Height()*.3f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
				   mGR.mSel=7;//Aboutus
		  }

	   if(event.getAction() == MotionEvent.ACTION_UP)
	   {
		   switch(mGR.mSel)
		   {
		     case 1:
		    	 M.BGplay(mGR.mContext,R.drawable.background);
		    	 mGR.gameReset();
		    	 M.GameScreen = M.GAMEPLAY;
		    	break;
		     case 2:
		    	RateUs();
		        break;
		     case 3:
		    	share2friend();
		        break;
		     case 4:
		    	MoreGame(); 
		        break;
		     case 5:
                 M.setValue = !M.setValue;  
		        break;
		     case 6:
		    	 M.GameScreen = M.GAMEHELP;
		        break;
		     case 7:
		    	 M.GameScreen = M.GAMEABTUS;
		        break;  
		   }
		   
		   mGR.mSel=0;
	   }
  	 return true;
	
  }
	void drawAbtUs(GL10 gl)
	{
		DrawTexture(gl,mGR.mTex_GameBg,0,0);
		DrawTexture(gl,mGR.mTex_PopUp,0,0);
		DrawTexture(gl,mGR.mTex_hututuicn,-0.45f,-0.35f);
		DrawTexture(gl,mGR.mTex_BackBtn,0.45f,-0.35f);
		if(M.GameScreen == M.GAMEHELP)
		{
		  DrawTexture(gl,mGR.mTex_HelpTitle,0,0.4f);
		  DrawTexture(gl,mGR.mTex_HelpTxt,0,0.2f);
		}
		if(M.GameScreen == M.GAMEABTUS)
		{
			DrawTexture(gl,mGR.mTex_AbtTitle,0,0.4f);
			DrawTexture(gl,mGR.mTex_AbtTxt,0,-0.125f);
		}
		if(M.GameScreen == M.GAMEHIGHSCORE)
		{
		  DrawTexture(gl,mGR.mTex_HighScoreBtn,0,0.4f);
		  String str = mGR.HighScore+"";
		  float dx   = (str.length()*mGR.mTex_Font[0].width())/2;
		  drawNumber(gl,mGR.HighScore,0.02f-dx,0);
		}
		if(mGR.mSel ==1)
	     DrawTexture(gl,mGR.mTex_Select,-.45f,-.35f);
		if(mGR.mSel ==2)
	     DrawTexture(gl,mGR.mTex_Select,.45f,-.35f);
		
	}
	boolean HandleAbtUS(MotionEvent event)
	{
		mGR.mSel=0;
  	  if(CircRectsOverlap(-.45f,-.35f,mGR.mTex_BackBtn.width()*.5f,mGR.mTex_BackBtn.Height()*.5f,screen2worldX(event.getX()), screen2worldY(event.getY()),.05f))
		   mGR.mSel=1;
	   if(CircRectsOverlap(.45f,-.35f,mGR.mTex_BackBtn.width()*.5f,mGR.mTex_BackBtn.Height()*.5f,screen2worldX(event.getX()), screen2worldY(event.getY()),.05f))
		   mGR.mSel=2;
	  
	   if(event.getAction() == MotionEvent.ACTION_UP)
	   {
	     if(mGR.mSel==1)
			 MoreGame();
		  if(mGR.mSel==2)
			  M.GameScreen = M.GAMEMENU;
		   mGR.mSel=0;
	   }
		return true;
	}
	boolean HandleHighScore(MotionEvent event)
	{
		mGR.mSel=0;
  	  if(CircRectsOverlap(-.45f,-.35f,mGR.mTex_BackBtn.width()*.5f,mGR.mTex_BackBtn.Height()*.5f,screen2worldX(event.getX()), screen2worldY(event.getY()),.05f))
		   mGR.mSel=1;
	   if(CircRectsOverlap(.45f,-.35f,mGR.mTex_BackBtn.width()*.5f,mGR.mTex_BackBtn.Height()*.5f,screen2worldX(event.getX()), screen2worldY(event.getY()),.05f))
		   mGR.mSel=2;
	  
	   if(event.getAction() == MotionEvent.ACTION_UP)
	   {
	     if(mGR.mSel==1)
			 MoreGame();
		  if(mGR.mSel==2)
			  M.GameScreen = M.GamePrevScreen;
		   mGR.mSel=0;
	   }
		return true;
	}
	void DrawPause(GL10 gl)
	{
		DrawTexture(gl,mGR.mTex_GameBg,0,0);
		DrawTexture(gl,mGR.mTex_PopUp,0,0);
		DrawTexture(gl,mGR.mTex_PauseTitle,0,0.4f);
		DrawTexture(gl,mGR.mTex_ContinueBtn,0,0.15f);
		DrawTexture(gl,mGR.mTex_HomeBtn,0,-0.06f);
		DrawTexture(gl,mGR.mTex_HighScoreBtn,0,-0.27f);
		DrawTexture(gl,mGR.mTex_hututuicn,-0.45f,-0.35f);
		DrawTexture(gl,mGR.mTex_SoundIcn[M.setValue?0:1],0.45f,-0.35f);
		switch(mGR.mSel)
		{
		  case 1:
			  DrawTexture(gl,mGR.mTex_BtnSelect,0,0.15f);
			  break;
		  case 2:
			  DrawTexture(gl,mGR.mTex_BtnSelect,0,-0.06f);
			  break;
		  case 3:
			  DrawTexture(gl,mGR.mTex_BtnSelect,0,-0.27f);
			  break;
		  case 4:
			  DrawTexture(gl,mGR.mTex_Select,-0.45f,-0.35f);
			  break;
		  case 5:
			  DrawTexture(gl,mGR.mTex_Select,0.45f,-0.35f);
			  break;
		}
	}
	boolean HandlePause(MotionEvent event)
	{
		mGR.mSel=0;
	   if(CircRectsOverlap(0f,.15f,mGR.mTex_BtnSelect.width()*.4f,mGR.mTex_BtnSelect.Height()*.4f,screen2worldX(event.getX()), screen2worldY(event.getY()),.05f))
		   mGR.mSel =1;// Continue
	   if(CircRectsOverlap(0f,-.06f,mGR.mTex_BtnSelect.width()*.4f,mGR.mTex_BtnSelect.Height()*.4f,screen2worldX(event.getX()), screen2worldY(event.getY()),.05f))
		   mGR.mSel =2;// Home
	   if(CircRectsOverlap(0f,-.27f,mGR.mTex_BtnSelect.width()*.4f,mGR.mTex_BtnSelect.Height()*.4f,screen2worldX(event.getX()), screen2worldY(event.getY()),.05f))
		   mGR.mSel =3;// HighScore
	   if(CircRectsOverlap(-.45f,-.35f,mGR.mTex_hututuicn.width()*.4f,mGR.mTex_hututuicn.Height()*.4f,screen2worldX(event.getX()), screen2worldY(event.getY()),.05f))
		   mGR.mSel =4; // Hututu
	   if(CircRectsOverlap(.45f,-.35f,mGR.mTex_BackBtn.width()*.4f,mGR.mTex_BackBtn.Height()*.4f,screen2worldX(event.getX()), screen2worldY(event.getY()),.05f))
		   mGR.mSel =5; // back
	  
	   if(event.getAction() == MotionEvent.ACTION_UP)
	   {
		   switch(mGR.mSel)
		   {
		     case 1:
		    	 if(M.setValue)
		    		 M.BGplay(mGR.mContext,R.drawable.background);
		    	 else
		    		 M.BGStop();
		    	 M.GameScreen = M.GAMEPLAY;
			   break;
		     case 2:
		    	 M.GameScreen = M.GAMEMENU;
			   break;
		     case 3:
		    	 M.GamePrevScreen = M.GameScreen;
		    	 M.GameScreen = M.GAMEHIGHSCORE;
			   break;
		     case 4:
                 MoreGame(); 
			   break;
		     case 5:
		    	 M.setValue = !M.setValue;
			   break;
		   }
		   mGR.mSel=0;
	   }
	   return true; 
	}
	void DrawGameover(GL10 gl)
	{
		DrawTexture(gl,mGR.mTex_GameBg,0,0);
		DrawTexture(gl,mGR.mTex_PopUp,0,0);
		DrawTexture(gl,mGR.mTex_GameOverTxt,0,0.4f);
		DrawTexture(gl,mGR.mTex_RetryBtn,0,0.15f);
		DrawTexture(gl,mGR.mTex_HomeBtn,0,-0.06f);
		DrawTexture(gl,mGR.mTex_HighScoreBtn,0,-0.27f);
		DrawTexture(gl,mGR.mTex_hututuicn,-0.45f,-0.35f);
		DrawTexture(gl,mGR.mTex_RateUs,0.45f,-0.35f);
		switch(mGR.mSel)
		{
		  case 1:
			  DrawTexture(gl,mGR.mTex_BtnSelect,0,0.15f);
			  break;
		  case 2:
			  DrawTexture(gl,mGR.mTex_BtnSelect,0,-0.06f);
			  break;
		  case 3:
			  DrawTexture(gl,mGR.mTex_BtnSelect,0,-0.27f);
			  break;
		  case 4:
			  DrawTexture(gl,mGR.mTex_Select,-0.45f,-0.35f);
			  break;
		  case 5:
			  DrawTexture(gl,mGR.mTex_Select,0.45f,-0.35f);
			  break;
		}
	}
	boolean HandleGameOver(MotionEvent event)
	{
		mGR.mSel=0;
		   if(CircRectsOverlap(0f,.15f,mGR.mTex_BtnSelect.width()*.4f,mGR.mTex_BtnSelect.Height()*.4f,screen2worldX(event.getX()), screen2worldY(event.getY()),.05f))
			   mGR.mSel =1; // Retry
		   if(CircRectsOverlap(0f,-.06f,mGR.mTex_BtnSelect.width()*.4f,mGR.mTex_BtnSelect.Height()*.4f,screen2worldX(event.getX()), screen2worldY(event.getY()),.05f))
			   mGR.mSel =2; // Home
		   if(CircRectsOverlap(0f,-.27f,mGR.mTex_BtnSelect.width()*.4f,mGR.mTex_BtnSelect.Height()*.4f,screen2worldX(event.getX()), screen2worldY(event.getY()),.05f))
			   mGR.mSel =3; // HighScore
		   if(CircRectsOverlap(-.45f,-.35f,mGR.mTex_hututuicn.width()*.4f,mGR.mTex_hututuicn.Height()*.4f,screen2worldX(event.getX()), screen2worldY(event.getY()),.05f))
			   mGR.mSel =4; // MoreGames
		   if(CircRectsOverlap(.45f,-.35f,mGR.mTex_BackBtn.width()*.4f,mGR.mTex_BackBtn.Height()*.4f,screen2worldX(event.getX()), screen2worldY(event.getY()),.05f))
			   mGR.mSel =5; // RateUs
		  
		   if(event.getAction() == MotionEvent.ACTION_UP)
		   {
			   switch(mGR.mSel)
			   {
			     case 1:
			    	 if(M.setValue)
			    	   M.BGplay(mGR.mContext,R.drawable.background);
			    	 else
			    	   M.BGStop();	 
			    	 mGR.gameReset();
			    	 M.GameScreen = M.GAMEPLAY;
				   break;
			     case 2:
			    	 M.GameScreen = M.GAMEMENU;
				   break;
			     case 3:
			    	 M.GamePrevScreen = M.GameScreen;
			    	 M.GameScreen = M.GAMEHIGHSCORE;
				   break;
			     case 4:
	                 MoreGame(); 
				   break;
			     case 5:
			    	 RateUs();
				   break;
			   }
			   mGR.mSel=0;
		   }
		   return true; 
		}
	void SetBird()
	{
		for(int i=0;i<mGR.mBird.length;i++)
		  {
		    if(mGR.mBird[i].y<-1.2f)
		    {
		       if(mGR.mBird[i].no==2)	
		         mGR.mBird[i].set(mGR.randomRange(-1f,.2f),SetBig()+M.Diff,.008f,-mGR.randomRange(.025f,.035f),mGR.randomRange(.5f,1),i%mGR.mTex_Bird.length);
		       else if(mGR.mBird[i].no==3)
	    	     mGR.mBird[i].set(mGR.randomRange(.7f,1.2f),SetBig()+M.Diff,-.008f,-mGR.randomRange(.025f,.035f),mGR.randomRange(.5f,1),i%mGR.mTex_Bird.length);
		       else
	    	     mGR.mBird[i].set(mGR.randomRange(-.5f,.9f),SetBig()+M.Diff,0,-mGR.randomRange(.025f,.035f),mGR.randomRange(.5f,1),i%mGR.mTex_Bird.length);
		    }
		  }
	}
	void gameLogic()
	{
       Counter++;
       if(mGR.mArrow.Power == M.ARROWPOWER)
    	{
   		  for(int i=0;i<mGR.mArrowPower.length;i++)
   		  {
   		    if(mGR.mArrowPower[i].isFire)
   	        {
   		      mGR.mArrowPower[i].updatePowerArrow();
   		      if(mGR.mArrowPower[i].vx ==0 && mGR.mArrowPower[i].vy ==0)
   		      {
   		    	 mGR.mArrow.Power=-1;  	  
   		    	 mGR.mArrow.ResetArrow();
   		      }
   	        }
   		  }
        }
       if(mGR.mArrow.isFire)
         mGR.mArrow.Update();
	 for(int i=0;i<mGR.mBird.length;i++)
	  {
	  	 mGR.mBird[i].updateBird();
	  	 float dx =(mGR.mTex_Bird[0][0].width()*mGR.mBird[i].scal)/3;
	  	 float dy =(mGR.mTex_Bird[0][0].Height()*mGR.mBird[i].scal)/3;
	  	
	  	 if(mGR.mArrow.Power == M.ARROWPOWER)
	   	 {
	  	   for(int ii=0;ii<mGR.mArrowPower.length;ii++)
	       {
	  		 if(mGR.mArrowPower[ii].x<1.2f && mGR.mArrowPower[ii].x>-1.2f && mGR.mArrowPower[ii].y<1.2f && mGR.mArrowPower[ii].y>-1.2f )
			   {
				  if(CircRectsOverlap(mGR.mBird[i].x,mGR.mBird[i].y,dx,dy,mGR.mArrowPower[ii].x,mGR.mArrowPower[ii].y,mGR.mTex_Arrow.width()/5))
				  {
					PlayBirdSound(mGR.mBird[i].no);  
					mGR.mPoint[i].no = 10*mGR.mBird[i].type;
					mGR.mScore       += mGR.mPoint[i].no;
					mGR.mPankh[i].setPankh(mGR.mBird[i].x,mGR.mBird[i].y,mGR.mBird[i].vy/2,mGR.mBird[i].scal,mGR.mBird[i].no%mGR.mTex_PankhL.length);
				    mGR.mBlast[i].setblast(mGR.mBird[i].x,mGR.mBird[i].y,mGR.mBird[i].vy,mGR.mBird[i].scal,mGR.mBird[i].no%mGR.mTex_BirdBlast.length);
				    mGR.mPoint[i].set(mGR.mBird[i].x,mGR.mBird[i].y-.05f,-mGR.mBird[i].vy,1);
					mGR.mBird[i].reset();
			      } 
			   } 
	        }
	   	 }
	  	 else if(mGR.mArrow.Power == M.GANTAPOWER || mGR.mArrow.Power == M.THROWNPOWER)
	  	 {
	  		float rad;
	  	    if(mGR.mArrow.Power == M.GANTAPOWER)
	  		   rad = mGR.mTex_GantaPower[0].width()/2;
	  	    else
	  	       rad = mGR.mTex_ThronsBig.width()/2;
	  	   if(CircRectsOverlap(mGR.mBird[i].x,mGR.mBird[i].y,dx,dy,mGR.mPowerActive.x,mGR.mPowerActive.y,rad))
		   {
	  		   PlayBirdSound(mGR.mBird[i].no);
			   mGR.mPoint[i].no  =  10*mGR.mBird[i].type;
			   mGR.mScore        += mGR.mPoint[i].no;
			   mGR.mBlast[i].setblast(mGR.mBird[i].x,mGR.mBird[i].y,mGR.mBird[i].vy,mGR.mBird[i].scal,mGR.mBird[i].no%mGR.mTex_BirdBlast.length);
			   mGR.mPankh[i].setPankh(mGR.mBird[i].x,mGR.mBird[i].y,mGR.mBird[i].vy/2,mGR.mBird[i].scal,mGR.mBird[i].no%mGR.mTex_PankhL.length);
			   mGR.mPoint[i].set(mGR.mBird[i].x,mGR.mBird[i].y-.05f,-mGR.mBird[i].vy,1);
			   mGR.mBird[i].reset();
		   }
	  	 }
	  	 else
	  	 {
		   if(mGR.mArrow.x<1.2f && mGR.mArrow.x>-1.2f && mGR.mArrow.y<1.2f && mGR.mArrow.y>-1.2f )
		    {
			  if(CircRectsOverlap(mGR.mBird[i].x,mGR.mBird[i].y,dx,dy,mGR.mArrow.x+mGR.mArrow.rx,mGR.mArrow.y-mGR.mArrow.ry,mGR.mTex_Arrow.width()/5) && mGR.mArrow.isFire)
			   {
				  PlayBirdSound(mGR.mBird[i].no);
				  mGR.mPoint[i].no  =  10*mGR.mBird[i].type;
				  mGR.mScore        += mGR.mPoint[i].no;
				  mGR.mPankh[i].setPankh(mGR.mBird[i].x,mGR.mBird[i].y,mGR.mBird[i].vy/2,mGR.mBird[i].scal,mGR.mBird[i].no%mGR.mTex_PankhL.length);
				  mGR.mBlast[i].setblast(mGR.mBird[i].x,mGR.mBird[i].y,mGR.mBird[i].vy,mGR.mBird[i].scal,mGR.mBird[i].no%mGR.mTex_BirdBlast.length);
				  mGR.mPoint[i].set(mGR.mBird[i].x,mGR.mBird[i].y-.05f,-mGR.mBird[i].vy,1);
				  mGR.mBird[i].reset();
				  mGR.mArrow.mHattrickCnt++;
				  mGR.mArrow.isGoal = true;

			   }
		    }
	  	 }
	  	 mGR.mPoint[i].update();
	  	 mGR.mBlast[i].updateBlast();
	  	 mGR.mPankh[i].updatePankh();
	     if(mGR.mBlast[i].y<-1.2f)
		   mGR.mBlast[i].reset();
	     
	  }
	  if(CircRectsOverlap(mGR.mPower.x,mGR.mPower.y,mGR.mTex_BlastPower.width()/2,mGR.mTex_BlastPower.Height()/2,mGR.mArrow.x+mGR.mArrow.rx,mGR.mArrow.y+mGR.mArrow.ry,mGR.mTex_Arrow.width()/5))
	  {
		  M.sound6(mGR.mContext,R.drawable.powercollect);
		  mGR.mArrow.Power = mGR.mPower.PowerType;
	      mGR.mPower.PowerType =-1;
	      mGR.mSpeedCount =0;
	      switch(mGR.mArrow.Power)
	      {
	         case M.BLASTPOWER:
	    	     for(int i=0;i<mGR.mBird.length;i++)
				  {
					if(mGR.mBird[i].y>-1.2f && mGR.mBird[i].y<1.2f)
					{ 
					   PlayBirdSound(mGR.mBird[i].no);
					   mGR.mPoint[i].no  =  10*mGR.mBird[i].type;
					   mGR.mScore        += mGR.mPoint[i].no;
					   mGR.mBlast[i].setblast(mGR.mBird[i].x,mGR.mBird[i].y,mGR.mBird[i].vy,mGR.mBird[i].scal,mGR.mBird[i].no%mGR.mTex_BirdBlast.length);
					   mGR.mPankh[i].setPankh(mGR.mBird[i].x,mGR.mBird[i].y,mGR.mBird[i].vy/2,mGR.mBird[i].scal,mGR.mBird[i].no%mGR.mTex_PankhL.length);
					   mGR.mPoint[i].set(mGR.mBird[i].x,mGR.mBird[i].y-.05f,-mGR.mBird[i].vy,1);
					   mGR.mBird[i].reset();
				    }
				  }
				  mGR.mArrow.Power =-1;   
	             break;
	          case M.GANTAPOWER:
	             mGR.mPowerActive.setPower(0,0,.035f,0);
	        	 break;
	          case M.THROWNPOWER:
	        	  mGR.mPowerActive.setPower(0.3f,-.8f,0,.02f);
	        	  break;
		  }
		  mGR.mPower.x = mGR.mPower.y =10;
		  mGR.mPower.vy =0;
	  }
	  switch(mGR.mArrow.Power)
	  {
	     case  M.SLOWPOWER:
		       M.Speed = M.SlowSpeed;
			  if(Counter%4==0)
			    mGR.mSpeedCount++;
			  if(mGR.mSpeedCount>M.ACTIVETIME)
			  {
				 mGR.mSpeedCount=0;
				 M.Speed =1.0f;
				 mGR.mArrow.Power=-1;
			  }
		   break;
	     case M.GANTAPOWER:
	     case M.THROWNPOWER:
    	     mGR.mPowerActive.updatePower();
	    	 if(Counter%4==0)
			    mGR.mSpeedCount++;
	    	 if(mGR.mSpeedCount>M.ACTIVETIME)
    	      {
   	    	     mGR.mSpeedCount = 0;
 				 mGR.mArrow.Power= -1;
 				 mGR.mPowerActive.setPower(10,10,0,0);
    	      }
	    	 break;
	  }
	  SetBird();
	  mGR.mPower.PowerCount++;
	  if(mGR.mPower.PowerCount%500==0)
	  {
	    mGR.mPower.set(mGR.randomRange(-.2f,.8f),1.2f,-.025f,M.mRand.nextInt()%5);
	  }
	  mGR.mPower.update();
//	  mGR.mPower.PowerCount++;
//	  if(mGR.mPower.PowerCount>M.POWERTIME && mGR.mPower.y>1.2f)
//       mGR.mPower.set(mGR.randomRange(.2f,.8f),SetBig()+M.Diff,-.025f,M.mRand.nextInt()%5);
	  if(mGR.mScore>=(mGR.mLevel*250))
	  {
		  mGR.mLevel++;
		  mGR.mTarget.set(0,0,.01f,1);
	  }
	  if(mGR.mTarget.y>-1.2 && mGR.mTarget.y<1.2f)
		  mGR.mTarget.update();
	  if(mGR.mScore>mGR.HighScore)
		 mGR.HighScore = mGR.mScore;
	  if(Counter>500000)
		  Counter=0;

	}
	
 void DrawGamePlay(GL10 gl)
	{
		DrawTexture(gl,mGR.mTex_GameBg,0,0);
		DrawTextureR(gl,mGR.mTex_Bow[mGR.mBow.No%mGR.mTex_Bow.length],mGR.mBow.x,mGR.mBow.y,(float)Math.toDegrees(mGR.mBow.ang)-180);
		 
	     if(mGR.mArrow.Power == M.ARROWPOWER)
	     {
		   for(int i=0;i<mGR.mArrowPower.length;i++)
		   {
		     if(mGR.mArrowPower[i].isFire)
		     {
		      if(mGR.mArrowPower[i].x<1.2f && mGR.mArrowPower[i].x>-1.2f && mGR.mArrowPower[i].y<1.2f && mGR.mArrowPower[i].y>-1.2f )
		       DrawTextureR(gl,mGR.mTex_Arrow,mGR.mArrowPower[i].x,mGR.mArrowPower[i].y,(float)Math.toDegrees(mGR.mArrowPower[i].ang)-180);
		     }
		   }
	     }
	     if(mGR.mArrow.isFire)
	     {
           if(mGR.mArrow.x<1.2f && mGR.mArrow.x>-1.2f && mGR.mArrow.y<1.2f && mGR.mArrow.y>-1.2f)
       	     DrawTextureR(gl,mGR.mTex_Arrow,mGR.mArrow.x,mGR.mArrow.y,(float)Math.toDegrees(mGR.mArrow.ang)-180);
	   	 }
		for(int i=0;i<mGR.mBird.length;i++)
		{
		  if(mGR.mBird[i].y<1.2f && mGR.mBird[i].y>-1.2f)
		     DrawTextureS(gl,mGR.mTex_Bird[mGR.mBird[i].no][Counter/2%mGR.mTex_Bird[0].length],mGR.mBird[i].x,mGR.mBird[i].y,mGR.mBird[i].scal);
		}
		for(int i=0;i<mGR.mBlast.length;i++)
		{
		  if(mGR.mBlast[i].x<1.2f && mGR.mBlast[i].x>-1.2f && mGR.mBlast[i].y<1.2f && mGR.mBlast[i].y>-1.2f)
			  DrawTextureRPS(gl,mGR.mTex_BirdBlast[mGR.mBlast[i].no],mGR.mBlast[i].x,mGR.mBlast[i].y,mGR.mBlast[i].ang,0,0,mGR.mBlast[i].scal-.3f,mGR.mBlast[i].scal-.3f);
		}
		for(int i=0;i<mGR.mPankh.length;i++)
		{
		  if(mGR.mPankh[i].x<1.2f && mGR.mPankh[i].x>-1.2f && mGR.mPankh[i].y<1.2f && mGR.mPankh[i].y>-1.2f)
		  {
			DrawTextureRST(gl,mGR.mTex_PankhL[mGR.mPankh[i].no%4],mGR.mPankh[i].x-.05f,mGR.mPankh[i].y,-mGR.mPankh[i].ang,mGR.mPankh[i].scal,mGR.mPankh[i].fad);
		    DrawTextureRST(gl,mGR.mTex_PankhR[mGR.mPankh[i].no%4],mGR.mPankh[i].x+.05f,mGR.mPankh[i].y,mGR.mPankh[i].ang,mGR.mPankh[i].scal,mGR.mPankh[i].fad);
		  }
		}
		if(mGR.mPower.x>-1.2f && mGR.mPower.x<1.2f && mGR.mPower.y>-1.2f &&  mGR.mPower.y<1.2f)
		{
			if(mGR.mPower.PowerType>=0)
			{
			  switch(mGR.mPower.PowerType)
			  {
			    case M.ARROWPOWER:
			    	DrawTexture(gl,mGR.mTex_ArrowPower[Counter/5%mGR.mTex_ArrowPower.length],mGR.mPower.x, mGR.mPower.y);
			    	break;
			    case M.SLOWPOWER:
			    	DrawTexture(gl,mGR.mTex_SlowPower,mGR.mPower.x, mGR.mPower.y);
			    	break;
			    case M.BLASTPOWER:
			    	DrawTexture(gl,mGR.mTex_BlastPower,mGR.mPower.x, mGR.mPower.y);
			    	break;		
			    case M.GANTAPOWER:
			    	DrawTexture(gl,mGR.mTex_GantaPower[Counter/5%mGR.mTex_GantaPower.length],mGR.mPower.x, mGR.mPower.y);
			    	break;
			    case M.THROWNPOWER:
			    	DrawTexture(gl,mGR.mTex_ThronsPower,mGR.mPower.x, mGR.mPower.y);
			    	break; 	
			    	
			  }
			 
			}
		}
		DrawPower(gl);
		for(int i=0;i<mGR.mPoint.length;i++)
		{
		  if(mGR.mPoint[i].x<1.2f && mGR.mPoint[i].x>-1.2f && mGR.mPoint[i].y<1.2f && mGR.mPoint[i].y>-1.2f)
			  drawScore(gl,mGR.mPoint[i].no,mGR.mPoint[i].x, mGR.mPoint[i].y,mGR.mPoint[i].fad);
		}
		 if(mGR.mTarget.x<1.2f && mGR.mTarget.x>-1.2f && mGR.mTarget.y<1.2f && mGR.mTarget.y>-1.2f)
			 DrawTextureTrans(gl,mGR.mTex_TargetTxt,mGR.mTarget.x, mGR.mTarget.y,mGR.mTarget.fad);
		 
		DrawTexture(gl,mGR.mTex_PauseIcn,-.92f,.55f);
		DrawTexture(gl,mGR.mTex_SoundIcn[M.setValue?0:1],-.92f,.25f);
		switch(mGR.mSel)
		 {
		   case 1:
			   DrawTexture(gl,mGR.mTex_Select,-.92f,.55f); 
			   break;
		   case 2:
			   DrawTexture(gl,mGR.mTex_Select,-.92f,.25f);
			  break;
		 }
		DrawTexture(gl,mGR.mTex_ScoreBar,-.68f,.9f);
		String str = mGR.mScore+"";
		float dx = (str.length()*mGR.mTex_Font[0].width())/2;
		drawNumber(gl,mGR.mScore,-.55f-dx,.9f);
		DrawTexture(gl,mGR.mTex_ShootBar,-.74f,-.9f);
		drawNumber(gl,mGR.mNoArrow,-.65f,-.9f);
		DrawTexture(gl,mGR.mTex_TargetBox,.695f,.9f);
		String str1 = mGR.mLevel*250+"";
		float dx1   = (str1.length()*mGR.mTex_Font[0].width())/2; 
		drawNumber(gl,mGR.mLevel*250,.84f-dx1,.9f);
 	    gameLogic();
	}
  void DrawPower(GL10 gl)
  {
	 switch(mGR.mArrow.Power)
	 {
	   case M.GANTAPOWER:
		   DrawTexture(gl,mGR.mTex_GantaPower[Counter/5%mGR.mTex_GantaPower.length],mGR.mPowerActive.x, mGR.mPowerActive.y);
		  break;
	  case M.THROWNPOWER:
		  DrawTexture(gl,mGR.mTex_ThronsBig,mGR.mPowerActive.x, mGR.mPowerActive.y);
		  break;
		  
	 }
  }
	boolean HandleGame(MotionEvent event)
	{
		float x,y;
		 mGR.mSel=0;
		 if(CircRectsOverlap(-.92f,.55f,mGR.mTex_PauseIcn.width()*.4f,mGR.mTex_PauseIcn.Height()*.4f,screen2worldX(event.getX()), screen2worldY(event.getY()),.05f))
			 mGR.mSel=1;// Pause
		 if(CircRectsOverlap(-.92f,.25f,mGR.mTex_SoundIcn[0].width()*.4f,mGR.mTex_PauseIcn.Height()*.4f,screen2worldX(event.getX()), screen2worldY(event.getY()),.05f))
			 mGR.mSel=2;// game
		 if(event.getAction() == MotionEvent.ACTION_UP)
		 {
			 switch(mGR.mSel)
			 {
			   case 1:
				   M.BGStop();
				   M.GameScreen = M.GAMEPAUSE; 
				   break;
			   case 2:
				   M.setValue =!M.setValue;
				   if(M.setValue)
			    	 M.BGplay(mGR.mContext,R.drawable.background);
		    	   else
			    	 M.BGStop();
				  break;
				   
			 }
			 mGR.mSel=0;
		 }
		 if(mGR.mSel !=0)
			 return true;
		 if(mGR.mArrow.Power == M.GANTAPOWER || mGR.mArrow.Power == M.THROWNPOWER)
			 return true;
		 
	 	switch (event.getAction())
		{
		   case MotionEvent.ACTION_DOWN:
		   case MotionEvent.ACTION_MOVE:
			   if(mGR.mArrow.vx ==0 && mGR.mArrow.vy ==0 && !mGR.mArrow.isFire)
			    {
		          if(screen2worldX(event.getX())<=-.4f)
		           {
		        	   mGR.mArrow.ang=mGR.mBow.ang = Math.atan2(screen2worldY(event.getY())- mGR.mBow.y,screen2worldX(event.getX())- mGR.mBow.x);
			    	   x = screen2worldX(event.getX())-mGR.mBow.x;
			    	   y = screen2worldY(event.getY())-mGR.mBow.y;
			    	   mGR.mBow.No  = (int)(Math.sqrt(x*x+y*y)/.05f);
			    	   if(mGR.mBow.No<=0)
			    		 mGR.mBow.No=1;
			    	   if(mGR.mBow.No>11)
			    		  mGR.mBow.No=11;
		           }
			     }
			  break;
		  case MotionEvent.ACTION_UP:
		      if(mGR.mArrow.vx ==0 && mGR.mArrow.vy ==0 && mGR.mBow.No>0)
			    {
		    	   M.sound1(mGR.mContext,R.drawable.arrowshoot);
				   mGR.mArrow.vx = -(float)(Math.cos(mGR.mBow.ang)*(mGR.mBow.No*.0095f));
		    	   mGR.mArrow.vy = -(float)(Math.sin(mGR.mBow.ang)*(mGR.mBow.No*.0095f));
		    	   mGR.mArrow.isFire =true; 
		    	   if(mGR.mArrow.mHattrickCnt<3)
		    	      mGR.mNoArrow--; 
		    	   switch(mGR.mArrow.Power)
		    	    {
		    	       case M.ARROWPOWER:
		    	   	   for(int i=0;i<mGR.mArrowPower.length;i++)
				   	    {
			    	       mGR.mArrowPower[i].ang = mGR.mBow.ang; 
			    	       mGR.mArrowPower[i].setPower(mGR.mBow.x,.6f-i*.25f,mGR.mArrow.vx,mGR.mArrow.vy);
			    	       mGR.mArrowPower[i].isFire =true;
			    	       mGR.mArrow.isFire =false;
				   	    }
		    	        break;
		    	    }
		    	    mGR.mBow.No=0;
			    }
			  break;
		  }
		return true;
	}
	void PlayBirdSound(int no)
	{
		switch(no)
		{
		 case 0:
			 if(M.mRand.nextBoolean())
			   M.sound3(mGR.mContext,R.drawable.crow);
			 else
			   M.sound4(mGR.mContext,R.drawable.crow2);
			 break;
		 case 1:
		 case 3:
			   M.sound5(mGR.mContext,R.drawable.duck);
			 break; 
		 case 2:
			 M.sound2(mGR.mContext,R.drawable.bird);
			 break;	 
		 
		}
	}
	void DrawTexture(GL10 gl,SimplePlane Tex,float x,float y)
	{
	   Tex.drawTransprent(gl, x, y);
	}
	void DrawTextureR(GL10 gl,SimplePlane Tex,float x,float y,float angle)
	{
	  Tex.drawRotet(gl, 0,0,angle, x, y);
	}
	void DrawTextureRPS(GL10 gl,SimplePlane Tex,float x,float y,float angle,float rx,float ry,float scalx,float scaly)
	{
	  Tex.drawRotetAtPointS(gl,angle,rx,ry,x,y,scalx,scaly);
	}
	void DrawTextureS(GL10 gl,SimplePlane Tex,float x,float y,float scal)
	{
		Tex.drawScal(gl, x, y,scal,scal);
	}
	void DrawTextureTrans(GL10 gl,SimplePlane Tex,float x,float y,float trans)
	{
		Tex.drawTransprentScal(gl,x,y,1,trans);
	}
	void DrawTextureRST(GL10 gl,SimplePlane Tex,float x,float y,float ang,float scal,float trans)
	{
		Tex.drawRST(gl,x,y,ang,scal,trans);
	}
	void DrawFlip(GL10 gl,SimplePlane Tex,float x,float y)
	{
		Tex.drawFilp(gl, x, y);
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
		float dx = mGR.mTex_Font[0].width();
		 String strs = ""+no;
		for(int i =0;i<strs.length();i++)
		{
		  int k = ((int)strs.charAt(i))-48;
		  if(k>=0 && k<mGR.mTex_Font.length)
		    DrawTexture(gl,mGR.mTex_Font[k],x+i*dx,y);
		}
	}
	void drawScore(GL10 gl,int no,float x, float y,float trans)
	{
		float dx = mGR.mTex_Font[0].width();
		 String strs = ""+no;
		for(int i =0;i<strs.length();i++)
		{
		  int k = ((int)strs.charAt(i))-48;
		  if(k>=0 && k<mGR.mTex_Font.length)
		    DrawTextureTrans(gl,mGR.mTex_Font[k],x+i*dx,y,trans);
		}
	}
	float SetBig()
	{
		float  lowest =1.2f;
		for(int i = 0; i<mGR.mBird.length;i++)
		{
			if(mGR.mBird[i].y > lowest)
			{
			  lowest = mGR.mBird[i].y; 
			}
			
		}
		return lowest;
	}
	void RateUs()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.MARKET));
	    mGR.mContext.startActivity(mIntent);
	}
	void MoreGame()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.MARKET));
		mGR.mContext.startActivity(mIntent);
	}
	void share2friend()
	{
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("text/plain");
		i.putExtra(Intent.EXTRA_SUBJECT,"ScareCrow vs Birds");
		i.putExtra(Intent.EXTRA_TEXT,"In this game you are a arrow shooter to save your farm! Install the best free game"+M.LINK+getClass().getPackage().getName());
		try {
		    mGR.mContext.startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(GameRenderer.mStart, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
}

//drawGullel()
//{
//	    float x1 = (float)(.32f*Math.sin(Math.toRadians(Counter)));
//		float y1 = (float)(.45f*Math.cos(Math.toRadians(Counter)));
//		ang      = (float)Math.atan2((y1+CenterY)-PosY[0],(CenterX-x1)-Posx[0]);
//		ang1     = (float)Math.atan2((y1+CenterY)-PosY[1],(CenterX-x1)-Posx[1]);
//		
//		ang      = (float)Math.toDegrees(ang);
//		ang1     = (float)Math.toDegrees(ang1);
//	    dx[0]    =  Posx[0]-(CenterX-x1); 	
//	    dy[0]    =  PosY[0]-(CenterY+y1);
//	    dis[0]   =  (float)Math.sqrt(dx[0]*dx[0]+dy[0]*dy[0]);
//	    		
//	    dx[1]    =  Posx[1]+.1f-(-x1+CenterX); 	
//    dy[1]    =  PosY[1]+.1f-(y1+CenterY);
//    dis[1]   =  (float)Math.sqrt(dx[1]*dx[1]+dy[1]*dy[1]);
//		DrawTexture(gl, mGR.mTex_GameBg,0,0);
//		System.out.println("ang================                 "+ang +"  ang1===================          "+ang1+"^^^^^^^^^^^^^^^^^^          "+Counter);
//		DrawTextureRPS(gl,mGR.mTex_Rubber[1],(float)Posx[0],PosY[0],(float)(178-ang),-mGR.mTex_Rubber[1].width()*.45f,mGR.mTex_Rubber[1].Height()*.05f,1+dis[0],1);
//		DrawTextureRPS(gl,mGR.mTex_Rubber[1],(float)Posx[1],PosY[1],(float)182-ang1,-mGR.mTex_Rubber[1].width()*.45f,mGR.mTex_Rubber[1].Height()*.05f,1+dis[1],1);
//		DrawTextureRPS(gl,mGR.mTex_Rubber[1],(float)CenterX,CenterY,(float)(90-Counter),-mGR.mTex_Rubber[1].width()*.45f,mGR.mTex_Rubber[1].Height()*.05f,1,1);
//	DrawTexture(gl,mGR.mTex_Arrow,CenterX,CenterY);
//		DrawTexture(gl,mGR.mTex_Arrow,CenterX-x1,CenterY+y1);
//		DrawTexture(gl,mGR.mTex_Arrow,Posx[0],PosY[0]);
//		DrawTexture(gl,mGR.mTex_Arrow,Posx[1],PosY[1]);
//}