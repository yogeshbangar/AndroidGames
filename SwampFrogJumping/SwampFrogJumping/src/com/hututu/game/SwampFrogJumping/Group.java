package com.hututu.game.SwampFrogJumping;
import java.math.BigDecimal;
import javax.microedition.khronos.opengles.GL10;
import com.hututu.game.SwampFrogJumping.R;
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
	float hilana=0f;
	
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


	
	public void draw(GL10 gl) 
	{
	  Counter++;
	  switch (M.GameScreen)
	   {
		  case Check:
			  DrawTextureSS(gl,mGR.mTex_WaterAni[Counter/5%mGR.mTex_WaterAni.length],0,0-mGR.mTex_WaterAni[0].Height()*.3f,1,mGR.mAni,0,mGR.mTex_WaterAni[0].Height()*.3f);
			  if(mGR.mAni<3f)
			   mGR.mAni*=1.5f;
			  else
			   mGR.mAni=3f;
			break;
		  case M.GAMELOGO:
			 DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if(Counter>80)
			{
				if(!GameRenderer.mStart.addFree)
			       M.GameScreen = M.GAMEADD;
				else
				{
				   M.SplashPlay(GameRenderer.mContext,R.drawable.menu_theme);
				   setBubble();
				   M.GameScreen = M.GAMEMENU;
				   mGR.mAni=.01f;
				   mGR.mMenuAni=.005f;
				}
				Counter =0;
			}
			break;
		 case M.GAMEADD:
		 case M.GAMELOADING:
			 if(Counter>100)
				DrawTexture(gl,mGR.mTex_Exit, .9f,.9f);
			else
			{
				DrawTexture(gl, mGR.mTex_LoadBar,0.0f,-.9f);
				DrawTexture(gl, mGR.mTex_Pointer,Counter*(mGR.mTex_LoadBar.width()/100f)-.32f,-.9f);
			}
			break;
		 case M.GAMEMENU:
			 DrawMenu(gl);
			 break;
		 case M.GAMESETTING:
			 DrawSetting(gl);
			 break;
		 case M.GAMEHELP:
			 DrawHelp(gl);
			 break;
		 case M.GAMEABTUS:
			 DrawAbtUs(gl);
			 break;
		 case M.GAMEADFREE:
			 DrawAdFree(gl);
			 break;
		 case M.GAMEWORLD:
			 DrawWorld(gl);
			 break;
		 case M.GAMEMODE:
			 DrawGameMode(gl);
			 break;
		 case M.GAMEOVER:
		 case M.GAMEPAUSE:
			 DrawOverPause(gl);
			 break;
		 case M.GAMEPLAY:
			 DrawGamePlay(gl);
			 break;
		 case M.GAMECONG:
			 DrawCong(gl);
			 break;
			 
	   }
	  if(mGR.isJoin)
		  DrawJoin(gl);
//		setting();
	}
	
public boolean TouchEvent(MotionEvent event) 
{
		if(mGR.isJoin)
	      return HandleJoin(event);
	  switch (M.GameScreen)
	  {
			case Check:
				break;
			case M.GAMEADD:
			case M.GAMELOADING:
				if((MotionEvent.ACTION_UP == event.getAction()) && (Counter>100)
				   && CirCir(0.9f,.9f,.11f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
				   {
					  if(M.GameScreen == M.GAMEADD)
					  {
						  M.SplashPlay(GameRenderer.mContext,R.drawable.menu_theme);
						  M.GameScreen = M.GAMEMENU;
						  mGR.mAni	   =.01f;
						  mGR.mMenuAni =.005f;
						  setBubble();
					  }
					  if(M.GameScreen == M.GAMELOADING)
					  {
						  M.BGPlay(GameRenderer.mContext,R.drawable.gameplay);
						  M.GameScreen = M.GAMEPLAY;
					  }
				   }
				break;
			case M.GAMEMENU:
				HandleMenu(event);
				break;
			case M.GAMESETTING:
				HandleSetting(event);
				break;
			case M.GAMEHELP:
				HandleHelp(event);
				break;
			case M.GAMEABTUS:
				HandleAbtUs(event);
				break;
			case M.GAMEADFREE:
				HandleAdFree(event);
				break;
			case M.GAMEWORLD:
				HandleWorld(event);
				break;
			case M.GAMEMODE:
				HandleMode(event);
				break;
			case M.GAMEOVER:
			case M.GAMEPAUSE:
				HandleOverPause(event);
				break;
			case M.GAMEPLAY:
				HandleGamePlay(event);
				break;
			case M.GAMECONG:
				HandleCong(event);
				break;
   	  }
	  if(mGR.isJoin)
		 mGR.mJoinAnim=0.01f;
	  if(event.getAction() == MotionEvent.ACTION_DOWN)
	  {
		  if(mGR.mSel!=0 && M.GameScreen!=M.GAMEPLAY &&  M.GameScreen!=M.GAMEWORLD)
			  M.ClickSound(GameRenderer.mContext,R.drawable.button_click);
	  }
	  Handle(event);
	return true;
}
    void setBubble()//Common
    {
    	resetBubble();
    	if(M.GameScreen == M.GAMEPAUSE || M.GameScreen == M.GAMEOVER || M.GameScreen == M.GAMECONG)
    	{
	    	mGR.mbubble[0].set(.03f  ,-.68f);
	    	mGR.mbubble[1].set(-.709f,-.53f);
	    	mGR.mbubble[2].set(.529f ,-.859f);
	    	
	    	mGR.mbubble[3].set(.03f  ,-.68f);
	    	mGR.mbubble[4].set(-.709f,-.53f);
	    	mGR.mbubble[5].set(.529f ,-.859f);
	    	
	    	mGR.mbubble[6].set(.03f  ,-.68f);
	    	mGR.mbubble[7].set(-.709f,-.53f);
	    	mGR.mbubble[8].set(.529f ,-.859f);
    	}
    	else
    	{
    		mGR.mbubble[0].set(.03f ,-.919f);
        	mGR.mbubble[1].set(-.809f,-.639f);
        	mGR.mbubble[2].set(.529f,-.859f);
        	
        	mGR.mbubble[3].set(.03f ,-.919f);
        	mGR.mbubble[4].set(-.809f,-.639f);
        	mGR.mbubble[5].set(.529f,-.859f);
        	
        	mGR.mbubble[6].set(.03f ,-.919f);
        	mGR.mbubble[7].set(-.809f,-.639f);
        	mGR.mbubble[8].set(.529f,-.859f);
    	}
    }
    void resetBubble()
    {
    	for(int i=0;i<mGR.mbubble.length;i++)
    	{
    		mGR.mbubble[i].set(100,100);
    	}
    }
    void Drawbubble(GL10 gl)
    {
    	for(int i=0;i<mGR.mbubble.length;i++)
        {
        	if(mGR.mbubble[i].x>-1 && mGR.mbubble[i].x<1)
        	{
        	  if(mGR.mbubble[i].isShow)
        	   DrawTransScal(gl,mGR.mTex_Ripple,mGR.mbubble[i].x,mGR.mbubble[i].y,mGR.mbubble[i].z,1-(mGR.mbubble[i].z)*.4f);
        	  mGR.mbubble[i].updateBubble();
        	}
        }
    }
    int ani[]={0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,3,4};
    
    void DrawCommonBg(GL10 gl)
    {
    	
    	if(Counter%79==1 && ScreenReceiver.wasScreenOn)
      	  M.FrogSound(GameRenderer.mContext,R.drawable.frog);
      	
      	DrawTexture(gl,mGR.mTex_BG[0],0,0);
      	if(M.GameScreen != M.GAMEWORLD && M.GameScreen != M.GAMEMODE)
      	{
            Drawbubble(gl);
      	}
    }
    void DrawCommonFront(GL10 gl)
    {
   	    float y= (float)Math.toRadians((Counter*2)%360);
   	    if(Counter%4==0)
   	    	Counter2++;
   	    if(Counter2>100)
   	    	Counter2=0;
   	    
	     DrawTexture(gl,mGR.mTex_Drum,.529f,-.789f+Math.abs((float)Math.sin(y)*.02f));
    	 DrawTexture(gl,mGR.mTex_SplashFrog[ani[Counter2%ani.length]],.509f,-.479f+Math.abs((float)Math.sin(y)*.02f));
    	 
	    for(int i=0;i<mGR.BGDark.length;i++)
	    {
		    DrawTexture(gl,mGR.mTex_bgDark[0],M.GameScreen==M.GAMEWORLD?worldX+i*mGR.mTex_bgDark[0].width()*.833f:0,0);
	    }
	    
    	 if(M.GameScreen == M.GAMEWORLD)
    	 {
    	      for(int i=0;i<mGR.BGDark.length;i++)
	    	   {
	    	      mGR.BGDark[i] +=MoveVX;
	    	      if(mGR.BGDark[i]<-2)
	    	      {
	    	        mGR.BGDark[i] = mGR.BGDark[i==0?mGR.BGDark.length-1:i-1]+2f;
	    	      }
	    	   }
    	 }
    }
	void DrawMenu(GL10 gl)
	{
		  DrawCommonBg(gl);
		  DrawTexture(gl,mGR.mTex_SplashTxt,-.529f,.539f);
		  DrawTexture(gl,mGR.mTex_Makdi    ,-.549f,-.27f);
	      DrawTextureSS(gl,mGR.mTex_Board,0,-.28f-mGR.mTex_Board.Height()*.3f,1,mGR.mAni,0,mGR.mTex_Board.Height()*.3f);
	      DrawTextureSS(gl,mGR.mTex_SmallBoard,-.819f,-.459f-mGR.mTex_SmallBoard.Height()*.35f,1,mGR.mSel==4?1.2f:mGR.mAni,0,mGR.mTex_SmallBoard.Height()*.35f);
	      DrawTextureSS(gl,mGR.mTex_Google,-.819f,-.399f-mGR.mTex_Google.Height()*.4f,1,mGR.mSel==4?1.2f:mGR.mAni,0,mGR.mTex_Google.Height()*.4f);
	      for(int i=0;i<mGR.mTex_Menu.length && mGR.mAni==1f;i++)
	         DrawTransScal(gl,mGR.mTex_Menu[i],0,.139f-i*mGR.mMenuAni,mGR.mSel==i+1?1.2f:1,mGR.mSel==i+1?.5f:1);
	      
	      
	      DrawCommonFront(gl);
	      if(Counter>20)
	      {
		      if(mGR.mAni<1)
		        mGR.mAni*=1.25f;
		      else
		      {
	    	    mGR.mAni =1;
	    	    if(mGR.mMenuAni<.23f)
			       mGR.mMenuAni*=1.25f;
	    	    else
    	    	  mGR.mMenuAni =.23f;
		      }
	      }
	      
	}
	boolean HandleMenu(MotionEvent event)
	{
		mGR.mSel=0;
		for(int i=0;i<mGR.mTex_Menu.length && mGR.mAni==1f;i++)
  		  if(CircRectsOverlap(0,.139f-i*mGR.mMenuAni,mGR.mTex_Menu[0].width()*.4f,mGR.mTex_Menu[0].Height()*.3f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		  {
  			 mGR.mSel=i+1; //Menu
		  } 
		  if(CircRectsOverlap(-.819f,-.459f,mGR.mTex_SmallBoard.width()*.4f,mGR.mTex_SmallBoard.Height()*.3f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
			 mGR.mSel=4;//Google
		  
		  if(event.getAction() == MotionEvent.ACTION_UP)
		  {
			  switch (mGR.mSel)
			  {
				case 1:
					M.BgStop();
					M.GameScreen = M.GAMEWORLD;
					setBubble2();
					break;
				case 2:
					M.SplashPlay(GameRenderer.mContext,R.drawable.menu_theme);
					M.GameScreen = M.GAMESETTING;
					mGR.mMenuAni =.005f;
					mGR.mAni     =.01f;
					break;
				case 3:
					M.SplashPlay(GameRenderer.mContext,R.drawable.menu_theme);
					M.GameScreen = M.GAMEADFREE;
					mGR.mAni     =.01f;
					break;
				case 4:
					Google();
					break;
			  }
		      mGR.mSel=0;
		  }
		return true; 
	}
	void DrawSetting(GL10 gl)
	{
		  DrawCommonBg(gl);
		  DrawTexture(gl,mGR.mTex_Makdi    ,-.549f,-.27f);
	      DrawTextureSS(gl,mGR.mTex_Board,0,-.28f-mGR.mTex_Board.Height()*.3f,1,mGR.mAni,0,mGR.mTex_Board.Height()*.3f);
	      DrawTextureSS(gl,mGR.mTex_SmallBoard,-.819f,-.459f-mGR.mTex_SmallBoard.Height()*.35f,1,mGR.mSel==5?1.2f:mGR.mAni,0,mGR.mTex_SmallBoard.Height()*.35f);
	      DrawTextureSS(gl,mGR.mTex_AbtBtn,-.819f,-.399f-mGR.mTex_AbtBtn.Height()*.4f,1,mGR.mSel==5?1.2f:mGR.mAni,0,mGR.mTex_AbtBtn.Height()*.4f);
	      
	      for(int i=0;i<mGR.mTex_Setting.length && mGR.mAni==1f;i++)
	      {
	         DrawTransScal(gl,mGR.mTex_Setting[i],-.079f,.129f-i*mGR.mMenuAni,mGR.mSel==i+1?1.2f:1,mGR.mSel==i+1?.5f:1);
	         DrawTransScal(gl,mGR.mTex_SettingIcn[i][0],.19f,.18f-i*mGR.mMenuAni,mGR.mSel==i+1?1.2f:1,mGR.mSel==i+1?.5f:1);
	         if(i==0 && !M.setValue)
	            DrawTransScal(gl,mGR.mTex_SettingIcn[i][1],.19f,.18f-i*mGR.mMenuAni,mGR.mSel==i+1?1.2f:1,mGR.mSel==i+1?.5f:1);
             if(i==1 && !M.SetBG)
	            DrawTransScal(gl,mGR.mTex_SettingIcn[i][1],.19f,.18f-i*mGR.mMenuAni,mGR.mSel==i+1?1.2f:1,mGR.mSel==i+1?.5f:1);
	         if(i==2 && !M.Vibrate)
	            DrawTransScal(gl,mGR.mTex_SettingIcn[i][1],.19f,.18f-i*mGR.mMenuAni,mGR.mSel==i+1?1.2f:1,mGR.mSel==i+1?.5f:1);
	      }
	      if(mGR.mAni==1)
	       DrawTransScal(gl,mGR.mTex_HelpBtn,0,.059f-3*mGR.mMenuAni,mGR.mSel==4?1.2f:1,mGR.mSel==4?.5f:1);
	      DrawTransScal(gl,mGR.mTex_Home,-.889f,.819f,mGR.mSel==6?1.2f:1,mGR.mSel==6?.5f:1);
	      DrawCommonFront(gl);
	      if(Counter>20)
	      {
		      if(mGR.mAni<1)
		        mGR.mAni*=1.25f;
		      else
		      {
	    	    mGR.mAni =1;
	    	    if(mGR.mMenuAni<.23f)
			       mGR.mMenuAni*=1.25f;
	    	    else
    	    	  mGR.mMenuAni =.23f;
		      }
	      }
	      
	}
	boolean HandleSetting(MotionEvent event)
	{
		mGR.mSel=0;
		for(int i=0;i<mGR.mTex_Menu.length && mGR.mAni==1f;i++)
  		  if(CircRectsOverlap(0,.139f-i*mGR.mMenuAni,mGR.mTex_Menu[0].width()*.4f,mGR.mTex_Menu[0].Height()*.3f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		  {
  			 mGR.mSel=i+1; //Menu
		  } 
		 if(CircRectsOverlap(0,.059f-3*mGR.mMenuAni,mGR.mTex_HelpBtn.width()*.4f,mGR.mTex_HelpBtn.Height()*.2f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
			mGR.mSel=4;//Help
		
		 if(CircRectsOverlap(-.819f,-.459f,mGR.mTex_SmallBoard.width()*.4f,mGR.mTex_SmallBoard.Height()*.3f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
			 mGR.mSel=5;//Google
		 
		 if(CircRectsOverlap(-.889f,.819f,mGR.mTex_Home.width()*.4f,mGR.mTex_Home.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
			 mGR.mSel=6;//Home
		  
		  if(event.getAction() == MotionEvent.ACTION_UP)
		  {
			  switch (mGR.mSel)
			  {
				case 1:
					M.setValue =!M.setValue; 
					break;
				case 2:
					M.SetBG =!M.SetBG;
					break;
				case 3:
					M.Vibrate =!M.Vibrate;
					break;
				case 4:
					mGR.mAni=.01f;
					M.GameScreen = M.GAMEHELP;
					break;
				case 5:
					mGR.mAni=.01f;
					M.GameScreen = M.GAMEABTUS;
					break;
				case 6:
					mGR.mAni=.01f;
					mGR.mMenuAni=.005f;
					M.GameScreen = M.GAMEMENU;
					break;
			  }
		      mGR.mSel=0;
		  }
		return true; 
	}
	void DrawHelp(GL10 gl)
	{
		DrawTexture(gl,mGR.mTex_BG[0],0,0);
	    DrawTexture(gl,mGR.mTex_bgDark[0],0,0);
	    DrawTextureS(gl,mGR.mTex_Help,0,1-mGR.mAni,mGR.mAni);
	    DrawTransScal(gl,mGR.mTex_Home,.889f,-.819f,mGR.mSel==1?1.2f:1,mGR.mSel==1?.5f:1);
	    if(mGR.mAni<1)
     	  mGR.mAni*=1.5f;
	    else
    	 mGR.mAni=1f;
	    
	}
	boolean HandleHelp(MotionEvent event)
	{
		mGR.mSel=0;
		
		 if(CircRectsOverlap(.889f,-.819f,mGR.mTex_Home.width()*.4f,mGR.mTex_Home.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
			 mGR.mSel=1;//Home
		  
		  if(event.getAction() == MotionEvent.ACTION_UP)
		  {
			  switch (mGR.mSel)
			  {
				case 1:
					mGR.mAni=.01f;
					mGR.mMenuAni=.005f;
					M.GameScreen = M.GAMESETTING;
					break;
			  }
		      mGR.mSel=0;
		  }
		return true; 
	}
	void DrawAbtUs(GL10 gl)
	{
		DrawCommonBg(gl);
		DrawTexture(gl,mGR.mTex_Makdi    ,-.549f,-.27f);
	    DrawTextureSS(gl,mGR.mTex_Board,0,-.28f-mGR.mTex_Board.Height()*.3f,1,mGR.mAni,0,mGR.mTex_Board.Height()*.3f);
	    DrawTextureSS(gl,mGR.mTex_Abt,0,-.1f+mGR.mTex_Abt.Height()*.3f,1,mGR.mAni,0,-mGR.mTex_Abt.Height()*.3f);
        DrawTextureSS(gl,mGR.mTex_SmallBoard,-.819f,-.459f-mGR.mTex_SmallBoard.Height()*.35f,1,mGR.mSel==2?1.2f:mGR.mAni,0,mGR.mTex_SmallBoard.Height()*.35f);
	    DrawTextureSS(gl,mGR.mTex_More,-.819f,-.399f-mGR.mTex_More.Height()*.4f,1,mGR.mSel==2?1.2f:mGR.mAni,0,mGR.mTex_More.Height()*.4f);
	    DrawTransScal(gl,mGR.mTex_Home,-.889f,.819f,mGR.mSel==1?1.2f:1,mGR.mSel==1?.5f:1);
	    DrawCommonFront(gl);
	    if(mGR.mAni<1)
     	  mGR.mAni*=1.25f;
	    else
    	 mGR.mAni=1f;
	    
	}
	boolean HandleAbtUs(MotionEvent event)
	{
		mGR.mSel=0;
		
		 if(CircRectsOverlap(-.889f,.819f,mGR.mTex_Home.width()*.4f,mGR.mTex_Home.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
			 mGR.mSel=1;//Home
		 if(CircRectsOverlap(-.819f,-.459f,mGR.mTex_Home.width()*.4f,mGR.mTex_Home.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
			 mGR.mSel=2;//More
		  
		  if(event.getAction() == MotionEvent.ACTION_UP)
		  {
			  switch (mGR.mSel)
			  {
				case 1:
					mGR.mAni=.01f;
					mGR.mMenuAni=.005f;
					M.GameScreen = M.GAMESETTING;
					break;
				case 2:
					MoreGame();
					break;
			  }
		      mGR.mSel=0;
		  }
		return true; 
	}
	void DrawAdFree(GL10 gl)
	{
		DrawCommonBg(gl);	
 	    DrawTexture(gl,mGR.mTex_Makdi    ,-.549f,-.27f);
		DrawTextureS(gl,mGR.mTex_Board   ,0,-.28f,mGR.mAni);
	    DrawTextureS(gl,mGR.mTex_AdFree  ,0, .0f ,mGR.mAni);
	    if(mGR.mAni==1f)
	     drawNumber(gl, .99+"",.03f,-.089f,.015f,1);
	    DrawTextureSS(gl,mGR.mTex_Buybtn ,0,-.369f-mGR.mTex_Buybtn.Height()*.4f,1,mGR.mSel==4?1.5f:mGR.mAni,0,mGR.mTex_Buybtn.Height()*.4f);
	    
	    DrawTransScal(gl,mGR.mTex_Home      ,-.889f,.819f,mGR.mSel==1?1.2f:1,mGR.mSel==1?.5f:1);
		DrawTextureSS(gl,mGR.mTex_SmallBoard,-.819f,-.459f-mGR.mTex_SmallBoard.Height()*.35f,1,mGR.mSel==2?1.2f:mGR.mAni,0,mGR.mTex_SmallBoard.Height()*.35f);
	    DrawTextureSS(gl,mGR.mTex_FB        ,-.819f,-.399f-mGR.mTex_More.Height()*.4f,1,mGR.mSel==2?1.2f:mGR.mAni,0,mGR.mTex_More.Height()*.4f);
	    DrawTransScal(gl,mGR.mTex_BubbleIcn ,.839f,.71f,mGR.mSel==3?1.2f:mGR.mAni,mGR.mSel==3?.5f:1);
	    DrawCommonFront(gl);
	    if(mGR.mAni<1)
     	  mGR.mAni*=1.25f;
	    else
    	 mGR.mAni=1f;
	}
	boolean HandleAdFree(MotionEvent event)
	{
		mGR.mSel=0;
		if(CircRectsOverlap(-.889f,.819f,mGR.mTex_Home.width()*.4f,mGR.mTex_Home.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
			mGR.mSel=1;//Home
		if(CircRectsOverlap(-.819f,-.459f,mGR.mTex_SmallBoard.width()*.4f,mGR.mTex_SmallBoard.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
			mGR.mSel=2;//FB
		if(CircRectsOverlap(.839f,.71f,mGR.mTex_BubbleIcn.width()*.4f,mGR.mTex_BubbleIcn.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
			mGR.mSel=3;//bubble
		if(CircRectsOverlap(0,-.369f,mGR.mTex_Buybtn.width()*.4f,mGR.mTex_Buybtn.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
			mGR.mSel=4;//buy
		
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			switch(mGR.mSel)
			{
			   case 1:
				   M.GameScreen = M.GAMEMENU;
				   mGR.mAni	    =.01f;
				   mGR.mMenuAni =.005f;
				   setBubble();
			  	   break;
			   case 2:
				   FaceBook();
				   break;
			   case 3:
				    Intent mIntent = new Intent(Intent.ACTION_VIEW);
					mIntent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.hututu.game.bubblecandyrescue"));
				    GameRenderer.mContext.startActivity(mIntent);
				   break;
			   case 4:
				   mGR.mMainActivity.onBuyAdfree(null);
				   break;

			}
			mGR.mSel=0; 
		}
		return true;
	}
	float worldX;
	float MoveX ;
	float MoveVX;
	int   DotSel; 
	void setBubble2()//World
    {
    	resetBubble();
    	mGR.mbubble[0].set(.529f,-.859f);
    	mGR.mbubble[1].set(.529f,-.859f);
    	mGR.mbubble[2].set(.529f,-.859f);
	 	for(int i=0;i<12;i++)
	 	{
	   	   mGR.mbubble[i+3].set(0,0);
	 	}
    	
    }
	void DrawWorld(GL10 gl)
	{
		DrawCommonBg(gl);
		for(int i=0;i<mGR.mbubble.length;i++)
        {
        	if(mGR.mbubble[i].x>-1 && mGR.mbubble[i].x<1)
        	{
        	  if(mGR.mbubble[i].isShow)
        	  {
        		  if(i<3)
        	       DrawTransScal(gl,mGR.mTex_Ripple,mGR.mbubble[i].x,mGR.mbubble[i].y,mGR.mbubble[i].z,1-(mGR.mbubble[i].z)*.4f);
        		  else
    			   DrawTransScal(gl,mGR.mTex_Ripple,worldX+(i%3)*1.329f,0-mGR.mTex_WorldBoard.Height()*.3f,mGR.mbubble[i].z,1-(mGR.mbubble[i].z)*.4f);
        	  }
        	  mGR.mbubble[i].updateBubble();
        	}
        }
		for(int i=0;i<mGR.mTex_WorldIcn.length;i++)
		{
		  if(worldX+i*1.329f>-1.2f && worldX+i*1.329f<1.2f)
		  {
			  DrawTexture(gl,mGR.mTex_WorldBoard ,worldX+i*1.329f,0);
			  DrawTransScal(gl,mGR.mTex_WorldIcn[i],worldX+i*1.329f,.21f  ,mGR.mSel==i+1?1.1f:1,mGR.mSel==i+1?.7f:1);
			  DrawTransScal(gl,mGR.mTex_WorldTxt[i],worldX+i*1.329f,-.349f,mGR.mSel==i+1?1.1f:1,mGR.mSel==i+1?.7f:1);
		  }
		  if(worldX+i*1.329f>-.4f && worldX+i*1.329f<.4f)
		  {
			  DotSel=i;
		  }
		}
//		float y= (float)Math.toRadians((Counter*2)%360);
//		DrawTexture(gl,mGR.mTex_Drum,.529f,-.789f+Math.abs((float)Math.sin(y)*.02f));
//		DrawTexture(gl,mGR.mTex_SplashFrog[ani[Counter2%ani.length]],.509f,-.479f+Math.abs((float)Math.sin(y)*.02f));
		for(int i=0;i<3;i++)
		{
			if(DotSel==i)
			   DrawTexture(gl,mGR.mTex_Dot[0],-.13f+i*.13f,-.849f);
			else
			   DrawTexture(gl,mGR.mTex_Dot[1],-.13f+i*.13f,-.849f);
		}
		DrawCommonFront(gl);
		if(MoveVX>0)
		  {
			 MoveVX -=MoveVX*.1f;
		    if(MoveVX<=.001f)
		    	MoveVX=0;
		  }
		  if(MoveVX<0)
		  {
			 MoveVX -=MoveVX*.1f;
		    if(MoveVX>=-0.001f)
		    	MoveVX=0;
		  }
		  if(worldX+(2*1.329f)<-.6f)
		  {
			  MoveVX = .04f;
		  }
		  if(worldX>.6f)
		  {
			  MoveVX = -.04f;
		  }
		  worldX +=MoveVX;
		DrawTransScal(gl,mGR.mTex_Home      ,-.889f,.819f,mGR.mSel==4?1.2f:1,mGR.mSel==4?.5f:1);
	}
	boolean HandleWorld(MotionEvent event)
	{
		mGR.mSel=0;
		for(int i=0;i<mGR.mTex_WorldIcn.length;i++)
		  if(MoveVX==0 && CircRectsOverlap(worldX+i*1.329f,0,mGR.mTex_WorldBoard.width()*.35f,mGR.mTex_WorldBoard.Height()*.25f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
			  mGR.mSel=i+1;
		
		if(CircRectsOverlap(-.889f,.819f,mGR.mTex_Home.width()*.4f,mGR.mTex_Home.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
			mGR.mSel=4;
		
		if(event.getAction() == MotionEvent.ACTION_DOWN)
		{
			MoveX = screen2worldX(event.getX());
		}
		float dx = Math.abs(MoveX-screen2worldX(event.getX()));
		if(event.getAction() == MotionEvent.ACTION_MOVE && dx>.1f /*&& mGR.mSel==0*/)
		{
		  	
			if(MoveX<screen2worldX(event.getX()))
			{
				M.SwingSound(GameRenderer.mContext,R.drawable.swing);
				MoveVX = Math.abs(MoveX-screen2worldX(event.getX()))*.45f;
				MoveVX +=.04f;
				MoveX  = screen2worldX(event.getX()); 
			}
			if(MoveX>screen2worldX(event.getX()))
			{
				M.SwingSound(GameRenderer.mContext,R.drawable.swing);
				MoveVX = -Math.abs(MoveX-screen2worldX(event.getX()))*.45f;
				MoveVX -=.04f;
				MoveX  = screen2worldX(event.getX());
			}
		}
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			switch(mGR.mSel)
			{
			    case 1:case 2:case 3:
			    	mGR.mGameMode = mGR.mSel-1;
			    	if(mGR.mGameMode<2)
			    	{
			    	  M.GameScreen = M.GAMEMODE;
			    	  setBubble2();
			    	  for(int i=0;i<Scal.length;i++)
			    	  {
			    		  Scal[i] =.005f;
			    		  Cnt     = 0;
			    	  }
			    	}
			    	else
			    	{
			    	  mGR.GameReset(mGR.mSel); //classic
			    	}
				  break;
			    case 4:
			    	M.SplashPlay(GameRenderer.mContext,R.drawable.menu_theme);
			    	mGR.mAni    =.01f;
			    	mGR.mMenuAni=.005f;
			    	setBubble();
			    	M.GameScreen = M.GAMEMENU;
		    	  break;
			}
			mGR.mSel=0;
		}
		return true;
	}
	float pos[][] ={{-.089f,-.549f,.179f},{-.139f,-.379f,-.559f}}; 
	float Scal[]  ={.005f,.005f,.005f};
	int Cnt=0;
	void DrawGameMode(GL10 gl)
	{
		DrawCommonBg(gl);
		for(int i=0;i<mGR.mbubble.length;i++)
        {
        	if(mGR.mbubble[i].x>-1 && mGR.mbubble[i].x<1)
        	{
        	  if(mGR.mbubble[i].isShow)
        	  {
        		  if(i<3)
        	       DrawTransScal(gl,mGR.mTex_Ripple,mGR.mbubble[i].x,mGR.mbubble[i].y,mGR.mbubble[i].z,1-(mGR.mbubble[i].z)*.4f);
        		  else
        		  {
    			    DrawTransScal(gl,mGR.mTex_Ripple,pos[0][i%3],pos[1][i%3]-mGR.mTex_ModeBoard.Height()*.25f,mGR.mbubble[i].z,1-(mGR.mbubble[i].z)*.4f);
        		  }
        	  }
        	  mGR.mbubble[i].updateBubble();
        	}
        }
		for(int i=0;i<3;i++)
		{
		  DrawTextureSS(gl,mGR.mTex_ModeBoard ,pos[0][i],pos[1][i]-mGR.mTex_ModeBoard.Height()*.25f,1       ,mGR.mSel==i+1?1.1f:Scal[i],0,mGR.mTex_ModeBoard.Height()*.25f);
		  DrawTextureSS(gl,mGR.mTex_Mode[mGR.mGameMode][i],pos[0][i],(pos[1][i]+.079f)-mGR.mTex_Mode[0][i].Height()*.45f,1,mGR.mSel==i+1?1.1f:Scal[i],0,mGR.mTex_Mode[0][i].Height()*.45f);
		}
//		float y= (float)Math.toRadians((Counter*2)%360);
//		DrawTexture(gl,mGR.mTex_Drum,.529f,-.789f+Math.abs((float)Math.sin(y)*.02f));
//		DrawTexture(gl,mGR.mTex_SplashFrog[ani[Counter2%ani.length]],.509f,-.479f+Math.abs((float)Math.sin(y)*.02f));
        
		DrawTransScal(gl,mGR.mTex_Home      ,-.889f,.819f,mGR.mSel==4?1.2f:1,mGR.mSel==4?.5f:1);
		DrawCommonFront(gl);
		if(Scal[Cnt]<1)
		{
		   Scal[Cnt]*=2f;
		   if(Scal[Cnt]>.8f)
		     M.SwingSound(GameRenderer.mContext,R.drawable.swing);
		}
		else
		{
		 	
		   Scal[Cnt]=1;
		   Cnt++;
		   if(Cnt>2)
			 Cnt=2;
		}
			
	}
	boolean HandleMode(MotionEvent event)
	{
		mGR.mSel=0;
		for(int i=0;i<3;i++)
		{
			if(CircRectsOverlap(pos[0][i],pos[1][i],mGR.mTex_ModeBoard.width()*.3f,mGR.mTex_ModeBoard.Height()*.3f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
				mGR.mSel=i+1;
		}
		if(CircRectsOverlap(-.889f,.819f,mGR.mTex_Home.width()*.4f,mGR.mTex_Home.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
			mGR.mSel=4;
		
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			switch(mGR.mSel)
			{
			    case 1:case 2:case 3:
			    	mGR.GameReset(mGR.mSel);
				  break;
			    case 4:
			    	M.GameScreen = M.GAMEWORLD;
			    	setBubble2();
			    	for(int i=0;i<3;i++)
			    	{
			    		Scal[i] =.005f;
			    		Cnt=0;
			    	}
		    	  break;
			}
			mGR.mSel=0;
		}
		return true;
	}
	void DrawOverPause(GL10 gl)
	{
		DrawCommonBg(gl);
		DrawTexture(gl,mGR.mTex_Makdi    ,-.549f,-.27f);
		float dy = mGR.mTex_Board.Height()*.3f;
		DrawTextureSS(gl,mGR.mTex_Board    ,0,0-dy,1,mGR.mAni,0,dy);
		
		dy = mGR.mTex_ScoreBoard.Height()*.3f;
		DrawTextureSS(gl,mGR.mTex_ScoreBoard,0,.18f+dy,1,mGR.mAni,0,-dy);
		
		if(mGR.mAni==1f)
		{
		   float yy; 	
		   if(M.GameScreen == M.GAMEOVER)
			  DrawTextureR(gl,mGR.mTex_GameOver,0,.469f,-12.9f,0,0);
		   if(M.GameScreen == M.GAMEPAUSE)
			 DrawTextureR(gl,mGR.mTex_GamePause,0,.469f,-12.9f,0,0);
		   
		   String str = mGR.mGameMode==0?mGR.mTimeCnt+"":(int)mGR.mTileCnt+"";
		   if(str.length()<3)
			  yy = .339f+.015f;
		   else
			  yy = .339f;
 		   drawNumber(gl,mGR.mGameMode==0?mGR.mTimeCnt+"":(int)mGR.mTileCnt+"",.079f,yy,.015f,1); //Score
 		   
		   float sc = mGR.mBestScore[mGR.mGameMode][mGR.mGameType%mGR.mBestScore[mGR.mGameMode].length];
		   str = mGR.mGameMode==0?sc+"":(int)sc+"";
		   if(str.length()<3)
			  yy = .175f+.015f;
		   else
			   yy = .175f;
		   drawNumber(gl,mGR.mGameMode==0?sc+"":(int)sc+"",.079f,yy,.015f,1); //Best
		  
		}
		dy = mGR.mTex_ChangeGame.Height()*.35f;
		DrawTextureSS(gl,mGR.mTex_ChangeGame,-.719f,-.3f-dy,1,mGR.mSel==5?1.2f:mGR.mAni,0,dy);
		DrawTransScal(gl,mGR.mTex_Home      ,-.889f,.819f  ,mGR.mSel==4?1.2f:1,mGR.mSel==4?.5f:1);
		int no;
		if(M.GameScreen == M.GAMEPAUSE)
			no=3;
		else
			no=2;
		for(int i=0;i<no && mGR.mAni==1f;i++)
		{
		  if(i==0)	
		   DrawTransScal(gl,mGR.mTex_Retry,-.129f+i*mGR.mMenuAni,-.079f+i*.042f,mGR.mSel==i+1?1.2f:1,mGR.mSel==i+1?.5f:1);
		  if(i==1)
		    DrawTransScal(gl,mGR.mTex_LeaderBoard ,-.129f+i*mGR.mMenuAni,-.079f+i*.042f,mGR.mSel==i+1?1.2f:1,mGR.mSel==i+1?.5f:1);
		  if(i==2)
		    DrawTransScal(gl,mGR.mTex_Play,-.129f+i*mGR.mMenuAni,-.079f+i*.042f,mGR.mSel==i+1?1.2f:1,mGR.mSel==i+1?.5f:1);
		  
		}
		DrawCommonFront(gl);
		float dd;
		if(M.GameScreen == M.GAMEOVER)
			dd=.3f;
		else
			dd=.17f;
  	    if(mGR.mAni<.7f)
		  mGR.mAni *=1.35f;
	    else
	    {
		  mGR.mAni=1;
		  if(mGR.mMenuAni<dd)
			 mGR.mMenuAni*=1.15f;
		  else
			 mGR.mMenuAni=dd;
	    }
	}
	boolean HandleOverPause(MotionEvent event)
	{
		mGR.mSel=0;
		int no;
		if(M.GameScreen == M.GAMEPAUSE)
			no=3;
		else
			no=2;
		for(int i=0;i<no;i++)
		 if(CircRectsOverlap(-.129f+i*mGR.mMenuAni,-.079f+i*.042f,mGR.mTex_Retry.width()*.4f,mGR.mTex_Retry.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		 	mGR.mSel=i+1;
		if(CircRectsOverlap(-.889f,.819f,mGR.mTex_Home.width()*.4f,mGR.mTex_Home.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
			mGR.mSel=4;//Home
		if(CircRectsOverlap(-.719f,-.3f,mGR.mTex_SmallBoard.width()*.4f,mGR.mTex_SmallBoard.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
			mGR.mSel=5;//World
		
		
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			switch(mGR.mSel)
			{
			 	case 1:
				  	 mGR.GameReset(mGR.mGameType+1);
				   break;
				 case 2:
					  mGR.isJoin =false;
					 if(GameRenderer.mStart.isSignedIn())
				       GameRenderer.mStart.onShowLeaderboardsRequested();
				     else
					   mGR.isJoin=true; 
				   break;
				 case 3:
					 if(M.SetBG)
						 M.BGPlay(GameRenderer.mContext,R.drawable.gameplay);
					 else
						 M.StopSound();
					 mGR.mGameTime +=System.currentTimeMillis();
					 
 					  M.GameScreen = M.GAMEPLAY;
				   break;
				 case 4:
					 if(M.SetBG)
					    M.SplashPlay(GameRenderer.mContext,R.drawable.menu_theme);
					 else
						M.StopSound();
				     mGR.mAni=.01f;
				     mGR.mMenuAni=.005f;
				     M.GameScreen = M.GAMEMENU;
				     setBubble();
				   break;
				 case 5:
					 M.GameScreen = M.GAMEWORLD;
					 setBubble2();
				   break;
			}
			mGR.mSel=0;
		}
		return true;
	}
	void bgMove()
	{
		mGR.BG1 -= (mGR.move / 2);
		mGR.BG2 -= (mGR.move / 2);
		if(mGR.BG1 < -2f)
			mGR.BG1 = mGR.BG2 + mGR.mTex_BG[0].width()*.833f;
		if(mGR.BG2 < -2f)
			mGR.BG2 = mGR.BG1 + mGR.mTex_BG[0].width()*.833f;
		
		mGR.BGDark[0] -= mGR.move;
		mGR.BGDark[1] -= mGR.move;
		if(mGR.BGDark[0] < -2f)
			mGR.BGDark[0] = mGR.BGDark[1] + mGR.mTex_bgDark[0].width()*.833f;
		if(mGR.BGDark[1]< -2f)
		   mGR.BGDark[1] = mGR.BGDark[0]  + mGR.mTex_bgDark[0].width()*.833f;
		
		
		mGR.BGWave[0] -= (mGR.move*.75f);
		mGR.BGWave[1] -= (mGR.move*.75f);
		if(mGR.BGWave[0] < -2f)
			mGR.BGWave[0] = mGR.BGWave[1]+mGR.mTex_Wave[0].width()*.833f;
		if(mGR.BGWave[1] < -2f)
			mGR.BGWave[1]= mGR.BGWave[0]+mGR.mTex_Wave[0].width()*.833f;
	}
	void gamelogic() 
	{
		Counter2++;
		if(Counter2>360)
			Counter2=0;
		if(mGR.plry > -.24f)
		{
			mGR.plry += mGR.plrvy;
			mGR.plrvy -= .03f;
			if(mGR.plry<-.24f)
			{
			   hilana -=.03f;
			}
		}
		else
		{
			mGR.plrvy = 0;
			mGR.plry = -.24f;
		}
		for(int i = 0; i < mGR.mStep.length; i++)
		{
			mGR.mStep[i].moveCnt+=3;
			  if(mGR.mStep[i].moveCnt>360)
				  mGR.mStep[i].moveCnt=0;
		}
		if(mGR.mCharAni< 8)
		{
			for(int i = 0; i < mGR.mStep.length; i++)
			{
				mGR.mStep[i].x -= (mGR.move);
			}
			for(int i = 0; i < mGR.mbubble .length; i++)
			{
				mGR.mbubble[i].x -= (mGR.move);
			}
			bgMove();
			for(int i = 0; i < mGR.mStep.length; i++)
			{
				if(mGR.mStep[i].x < -1.2f) 
				{
					mGR.mStep[i].set(mGR.mStep[i == 0 ? mGR.mStep.length- 1:i-1].x+.24f,true,(mGR.mScore/10)%mGR.mTex_Step.length);
					
					if(mGR.mStep[i].isOn)
					{
						for(int j=0;j<mGR.mbubble.length;j++)
						{
						  if(mGR.mbubble[j].x<-1.2f)
						    mGR.mbubble[j].set(mGR.mStep[i].x,0);
						}
					}
					if(mGR.wCount%(mGR.mScore/25+6)==0)
						mGR.mStep[i].Watch = true;
					if(mGR.Space <= 0)
					{
						mGR.Space = (byte)(M.mRand.nextInt(4) + 2);
						mGR.mStep[i].isOn  = false;
						mGR.mStep[i].Watch = false;
					}
					mGR.wCount ++;
					mGR.Space--;
				}

			}
			mGR.mCharAni++;
			if(mGR.mCharAni == 8)
			{
				if(!mGR.mStep[mGR.st].isOn)
				{
					M.DumpingSound(GameRenderer.mContext,R.drawable.dumping);
					mGR.move = -.03f;
					mGR.mCharAni = 0;
					mGR.st--;
					if(mGR.st < 0)
					 mGR.st = (byte)(mGR.mStep.length - 1);
					mGR.mScore    -= 1;
					mGR.mTileCnt  -= 1;
					mGR.jumpCount = 0;
				}
				if(mGR.mStep[mGR.st].Watch)
				{
					mGR.mStep[mGR.st].Watch = false;
				}
			}
		}
		else
			mGR.move = 0;
		
		CheckGameWin();

	}
	void UpDateScore(int mode)
	{
		switch(mode)
		{
			case 0: //Arcade
				 long t    = (System.currentTimeMillis()-mGR.mGameTime);
				 if(mGR.mBestScore[mode][mGR.mGameType]<=0f)
				 {
					 mGR.mBestScore[mode][mGR.mGameType] = mGR.mTimeCnt;
				 }
				 else if(mGR.mTimeCnt<mGR.mBestScore[mode][mGR.mGameType])
				 {
					 mGR.mBestScore[mode][mGR.mGameType] = mGR.mTimeCnt;
				 }
				 GameRenderer.mStart.Submitscore(M.Arcade[mGR.mGameType],t);
				break;
			case 1://Time
			     if(mGR.mTileCnt>mGR.mBestScore[mode][mGR.mGameType])
			     {
	     	       mGR.mBestScore[mode][mGR.mGameType] = mGR.mTileCnt;
			     }
			     GameRenderer.mStart.Submitscore(M.Time[mGR.mGameType],(int)mGR.mBestScore[mode][mGR.mGameType]);
				break;
			case 2:
			     if(mGR.mTileCnt>mGR.mBestScore[mode][0])
			     {
	     	        mGR.mBestScore[mode][0] = mGR.mTileCnt;
			     }
			     GameRenderer.mStart.Submitscore(R.string.leaderboard_classic,(int)mGR.mBestScore[mode][0]);
				break;
		}
	}
	void CheckGameWin()
	{ 
		 switch(mGR.mGameMode)
		 {
		   case 0://arcade
			    if(mGR.mTileCnt>=mGR.mTargetTile)
			    {
			    	mGR.mWinCnt++;
			    	UpDateScore(mGR.mGameMode);
			    }
			   break;
		   case 1://Time
			    if(mGR.mTimeCnt>=mGR.mTargetTime)
			    {
				   mGR.mWinCnt++;
				   UpDateScore(mGR.mGameMode);
		
			    }
			   break;

		 }
		 if(mGR.mWinCnt>0)
		 {
			if(Counter%15==0)
			  AniReset(0,0);
			mGR.mWinCnt++;
		 }
		 if(mGR.mWinCnt>100)
		 {
			 mGR.mAdCount++;
			 mGR.mAni	 =.01f;
			 mGR.mMenuAni=.005f;
		     M.GameScreen = M.GAMECONG;
		     setBubble();
		     M.StopSound();
		     M.CongSound(GameRenderer.mContext,R.drawable.congratulations);
		     if(!GameRenderer.mStart.addFree && mGR.mAdCount%3==0)
			 {
			     GameRenderer.mStart.ShowInterstitial();
			 }
		 }
	}
	public void AniReset(float x,float y)
	{
//		int count = 0; 
		for(int i=0;i<mGR.mStarAni.length;i++)
		{
			if(mGR.mStarAni[i].x>1||mGR.mStarAni[i].x<-1||mGR.mStarAni[i].y>1||mGR.mStarAni[i].y<-1)
			{
//			  count++;
			  mGR.mStarAni[i].setAni(x, y, M.mRand.nextBoolean()?M.randomRange(.005f,.05f):-M.randomRange(.005f,.05f),M.randomRange(.005f,.05f),M.mRand.nextInt()%mGR.mTex_Ani.length,0);
			}
		}
	}
	void DrawStarAni(GL10 gl)
	{
		for(int i=0; i< mGR.mStarAni.length;i++)
		{
			if(mGR.mStarAni[i].x<1.5&&mGR.mStarAni[i].x>-1.5&&mGR.mStarAni[i].y<1.5&&mGR.mStarAni[i].y>-1.5 )
			{
				if(mGR.mStarAni[i].scal>0)
				{
 			       mGR.mStarAni[i].UpdateAni();
 			       DrawTransScal(gl,mGR.mTex_Ani[mGR.mStarAni[i].star],mGR.mStarAni[i].x, mGR.mStarAni[i].y,mGR.mStarAni[i].scal,mGR.mStarAni[i].scal);
				}
			}
		}
	}
	void DrawScene(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_BG[mGR.mGameMode], mGR.BG1, 0);
		DrawTexture(gl, mGR.mTex_BG[mGR.mGameMode], mGR.BG2, 0);
		
				
		DrawTexture(gl, mGR.mTex_Wave[mGR.mGameMode], mGR.BGWave[0], 0);
		DrawTexture(gl, mGR.mTex_Wave[mGR.mGameMode], mGR.BGWave[1], 0);
	}
	
	void DrawGamePlay(GL10 gl)
	{
		if(mGR.mWinCnt==0 && mGR.isStart)
		{
		  float t         = (System.currentTimeMillis()-mGR.mGameTime);	
		  mGR.mTimeCnt    = ((t/1000)+((t%10f)/1000));
		  mGR.mTimeCnt    = new BigDecimal(mGR.mTimeCnt).setScale(1,BigDecimal.ROUND_HALF_UP).floatValue();
		}
		DrawScene(gl);
		for(int i=0;i<mGR.mbubble.length;i++)
        {
        	if(mGR.mbubble[i].x>-1 && mGR.mbubble[i].x<1)
        	{
        	  if(mGR.mbubble[i].isShow && mGR.mStep[i%mGR.mStep.length].isOn)
        	  {
        		  float yy= -.575f;
        		 if(mGR.mStep[i%mGR.mStep.length].no==3)
        			   yy = -.555f;
        	    DrawTransScal(gl,mGR.mTex_Ripple,mGR.mbubble[i].x,yy,mGR.mbubble[i].z,1-(mGR.mbubble[i].z)*.4f);
        	  }
        	  mGR.mbubble[i].updateBubble();
        	}
        }
		for(int i = 0; i < mGR.mStep.length; i++) 
		{
			if(mGR.mStep[i].x>-1.2f && mGR.mStep[i].x<1.2f)
			{
				if(mGR.mStep[i].isOn)
				{
					float yy=-.45f;	
					if(mGR.mStep[i].no==3)
					  yy=-.42f;
					float y= (float)Math.toRadians((mGR.mStep[i].moveCnt)%360);
					if(i==mGR.st)
				      DrawTexture(gl, mGR.mTex_Step[mGR.mStep[i].no], mGR.mStep[i].x, (yy-Math.abs((float)Math.sin(y)*.025f))+hilana);
					else
					  DrawTexture(gl, mGR.mTex_Step[mGR.mStep[i].no], mGR.mStep[i].x, (yy-Math.abs((float)Math.sin(y)*.025f)));
				}
				else if(mGR.jumpCount<mGR.mTex_WaterAni.length && mGR.mStep[i].x >mGR.plrx-.05f && mGR.mStep[i].x<mGR.plrx+.25f)
				{
					if(mGR.jumpCount<2)
						DrawTexture(gl,mGR. mTex_Frog[mGR.jumpCount+6], mGR.mStep[i].x,-.35f);
					DrawTexture(gl,mGR.mTex_WaterAni[mGR.jumpCount%mGR.mTex_WaterAni.length], mGR.mStep[i].x+.05f,-.40f);
					if(Counter%2==0)
						mGR.jumpCount++;
					
					if(mGR.jumpCount>mGR.mTex_WaterAni.length-1)
					{
						 M.GameOver(GameRenderer.mContext,R.drawable.gameover);
						if(M.Vibrate)
						  GameRenderer.mStart.mVibrate.vibrate(2000);
						else
						  GameRenderer.mStart.mVibrate.cancel();
						
						if(mGR.mGameMode>1)
					 	  UpDateScore(mGR.mGameMode);
						mGR.mAdCount++;
					 	mGR.mAni	 =.01f;
					 	mGR.mMenuAni =.005f;
					 	M.GameScreen = M.GAMEOVER;
					 	setBubble();
					 	M.BgStop();
					 	if(!GameRenderer.mStart.addFree && mGR.mAdCount%3==0)
						   GameRenderer.mStart.ShowInterstitial();
					}
				}
			}
		}
		
		if(mGR.jumpCount>=mGR.mTex_WaterAni.length)
		{
			
			if(mGR.mCharAni>=1 &&mGR.mCharAni<8)
			{
			  DrawTexture(gl,mGR.mTex_Frog[mGR.mCharAni],mGR.plrx,mGR.plry);
			}
			else
			{
				float y= (float)Math.toRadians((mGR.mStep[mGR.st].moveCnt)%360);
				DrawTexture(gl,mGR.mTex_Frog[0],mGR.plrx,(mGR.plry-Math.abs((float)Math.sin(y)*.025f))+hilana);
				
			}
		}
		DrawTexture(gl, mGR.mTex_bgDark[mGR.mGameMode], mGR.BGDark[0], 0);
		DrawTexture(gl, mGR.mTex_bgDark[mGR.mGameMode], mGR.BGDark[1], 0);
		
		DrawTransScal(gl,mGR.mTex_BOne  ,-.80f , -.8f,mGR.mSel==1?1.15f:1,mGR.mSel==1?0.5f:1);
		DrawTransScal(gl,mGR.mTex_BTwo  , .80f , -.8f,mGR.mSel==2?1.15f:1,mGR.mSel==2?0.5f:1);
		DrawTransScal(gl,mGR.mTex_Pause ,-.889f,.819f,mGR.mSel==3?1.15f:1,mGR.mSel==3?0.5f:1);
		
		DrawTexture(gl,mGR.mTex_ScoreBox,.829f,.869f);
		if(mGR.mGameMode==0)
		  drawNumber(gl,mGR.mTimeCnt+"",.849f,.869f,0,1);
		else
		  drawNumber(gl,mGR.mTileCnt+"",.849f,.869f,0,1);
		DrawStarAni(gl);
		gamelogic();
	}
	boolean HandleGamePlay(MotionEvent event) {

		mGR.mSel = 0;
		if(mGR.move == 0 && CircRectsOverlap(-.80f, -.8f,mGR.mTex_BTwo.width() * .4f,mGR.mTex_BTwo.Height() * .4f,screen2worldX(event.getX()),screen2worldY(event.getY()), .05f)) {
			mGR.mSel = 1;
		}
		if(mGR.move == 0 && CircRectsOverlap(0.80f, -.8f,mGR. mTex_BTwo.width() * .4f,mGR.mTex_BTwo.Height() * .4f,screen2worldX(event.getX()),screen2worldY(event.getY()), .05f)) {
			mGR.mSel = 2;
		}
		if(CircRectsOverlap(-.889f,.819f,mGR. mTex_Pause.width() * .4f,mGR.mTex_Pause.Height() * .4f,screen2worldX(event.getX()),screen2worldY(event.getY()), .05f)) {
			mGR.mSel = 3;
		}
		if(MotionEvent.ACTION_DOWN == event.getAction())
		{
			if(!mGR.isStart)
			{
				mGR.mGameTime = System.currentTimeMillis();
				mGR.isStart = true;
				performAction();
			}
			else if((mGR.jumpCount>=mGR.mTex_WaterAni.length))
			{
				performAction();
			}
			mGR.st%=mGR.mStep.length;
		}
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			if(mGR.mSel==3)
			{
				M.StopSound();
				mGR.mGameTime -= System.currentTimeMillis();
				M.GameScreen = M.GAMEPAUSE;
				mGR.mAni 	 =.01f;
				mGR.mMenuAni =.005f;
				setBubble();
			}
			mGR.mSel = 0;
		}
		return true;
	}
	void performAction()
	{
	   if(mGR.mWinCnt==0)
	   {
		    switch(mGR.mSel) 
			{
			  case 1:
				M.Jumpsound(M.randomRangeInt(0, 3));  
				mGR.plry = -.23f;
				mGR.plrvy = .1f;
				mGR.st += 1;
				mGR.move = 0.03f;
				mGR.mCharAni = 0;
				mGR.mScore += 1;
				mGR.mTileCnt +=1;
				hilana=0f;
	//			M.sound1(GameRenderer.mContext, R.drawable.l_2_jump);
				break;
 			  case 2:
 			     M.Jumpsound(M.randomRangeInt(0, 3));
				mGR.plry = -.23f;
				mGR.plrvy = .1f;
				mGR.st += 2;
				mGR.move = 0.06f;
				mGR.mCharAni = 0;
				mGR.mScore   += 2;
				mGR.mTileCnt += 2;
				hilana=0;
	//			M.sound2(GameRenderer.mContext, R.drawable.l_2_second_jump);
				break;
			}
	    }
	}
	void DrawJoin(GL10 gl)
	{
		gl.glDisable(GL10.GL_SCISSOR_TEST);
		DrawTextureS(gl , mGR.mTex_WorldBoard,0,0,mGR.mJoinAnim);
	  	DrawTextureS(gl , mGR.mTex_JoinText  ,0,.21f,mGR.mJoinAnim);
	  	DrawTransScal(gl, mGR.mTex_JoinBtn   ,0   ,-.349f,mGR.mSel==100?1.2f:mGR.mJoinAnim,mGR.mSel==100?.45f:1);
	  	DrawTransScal(gl, mGR.mTex_Exit      ,.35f,.58f  ,mGR.mSel==200?1.2f:mGR.mJoinAnim,mGR.mSel==200?.45f:1);
	  	
	  	if(mGR.mJoinAnim<1)
	  	  mGR.mJoinAnim*=1.5f; 
	  	else
	  	  mGR.mJoinAnim =1;
	}
	boolean HandleJoin(MotionEvent event)
	{
		mGR.mSel=0;
		if(CircRectsOverlap(0   ,-.349f,mGR.mTex_Buybtn.width()*.5f,mGR.mTex_Buybtn.Height()*.5f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
			mGR.mSel=100;
		if(CircRectsOverlap(.35f,.58f,mGR.mTex_Buybtn.width()*.5f,mGR.mTex_Buybtn.Height()*.5f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
			mGR.mSel=200;
		
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			switch(mGR.mSel)
			{
			  case 100:
				   GameRenderer.mStart.beginUserInitiatedSignIn();
				   mGR.isJoin = false;
				 break;
			 case 200:
				   mGR.isJoin =false;
				 break;
			}
			mGR.mSel=0;
		}
		return true;
	}
	void DrawCong(GL10 gl)
	{
		DrawCommonBg(gl);
		DrawTexture(gl,mGR.mTex_Makdi    ,-.549f,-.27f);
		float dy = mGR.mTex_Board.Height()*.3f;
		DrawTextureSS(gl,mGR.mTex_Board    ,0,0-dy,1,mGR.mAni,0,dy);
		
		dy = mGR.mTex_ScoreBoard.Height()*.3f;
		DrawTextureSS(gl,mGR.mTex_ScoreBoard,0,.18f+dy,1,mGR.mAni,0,-dy);
		
		if(mGR.mAni==1f)
		{
		   float yy; 	
		   DrawTextureR(gl,mGR.mTex_Cong,0,.46f,-11f,0,0);
		   String str = mGR.mGameMode==0?mGR.mTimeCnt+"":(int)mGR.mTileCnt+"";
		   if(str.length()<3)
			  yy = .339f+.015f;
		   else
			  yy = .339f;
 		   drawNumber(gl,mGR.mGameMode==0?mGR.mTimeCnt+"":(int)mGR.mTileCnt+"",.079f,yy,.015f,1); //Score
 		   
		   float sc = mGR.mBestScore[mGR.mGameMode][mGR.mGameType%mGR.mBestScore[mGR.mGameMode].length];
		   str = mGR.mGameMode==0?sc+"":(int)sc+"";
		   if(str.length()<3)
			  yy = .175f+.015f;
		   else
			   yy = .175f;
		   drawNumber(gl,mGR.mGameMode==0?sc+"":(int)sc+"",.079f,yy,.015f,1); //Best
		  
		}
		dy = mGR.mTex_ChangeGame.Height()*.35f;
		DrawTextureSS(gl,mGR.mTex_ChangeGame,-.719f,-.3f-dy,1,mGR.mSel==5?1.2f:mGR.mAni,0,dy);
		DrawTransScal(gl,mGR.mTex_Home      ,-.889f,.819f  ,mGR.mSel==4?1.2f:1,mGR.mSel==4?.5f:1);
		for(int i=0;i<2 && mGR.mAni==1f;i++)
		{
		  if(i==0)	
		   DrawTransScal(gl,mGR.mTex_Retry,-.129f+i*mGR.mMenuAni,-.079f+i*.042f,mGR.mSel==i+1?1.2f:1,mGR.mSel==i+1?.5f:1);
		  if(i==1)
		    DrawTransScal(gl,mGR.mTex_LeaderBoard ,-.129f+i*mGR.mMenuAni,-.079f+i*.042f,mGR.mSel==i+1?1.2f:1,mGR.mSel==i+1?.5f:1);
		}
		DrawCommonFront(gl);
  	    if(mGR.mAni<.7f)
		  mGR.mAni *=2.05f;
	    else
	    {
		  mGR.mAni=1;
		  if(mGR.mMenuAni<.3f)
			 mGR.mMenuAni*=1.35f;
		  else
			 mGR.mMenuAni=.3f;
	    }
	}
	boolean HandleCong(MotionEvent event)
	{
		mGR.mSel=0;
		for(int i=0;i<2;i++)
		 if(CircRectsOverlap(-.129f+i*mGR.mMenuAni,-.079f+i*.042f,mGR.mTex_Retry.width()*.4f,mGR.mTex_Retry.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
		 	mGR.mSel=i+1;
		if(CircRectsOverlap(-.889f,.819f,mGR.mTex_Home.width()*.4f,mGR.mTex_Home.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
			mGR.mSel=4;//Home
		if(CircRectsOverlap(-.719f,-.3f,mGR.mTex_SmallBoard.width()*.4f,mGR.mTex_SmallBoard.Height()*.4f,screen2worldX(event.getX()),screen2worldY(event.getY()),.05f))
			mGR.mSel=5;//World
		
		
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			switch(mGR.mSel)
			{
			 	case 1:
				  	 mGR.GameReset(mGR.mGameType+1);
				   break;
				 case 2:
					  mGR.isJoin =false;
					 if(GameRenderer.mStart.isSignedIn())
				       GameRenderer.mStart.onShowLeaderboardsRequested();
				     else
					   mGR.isJoin=true; 
				   break;
				 case 4:
				     mGR.mAni=.01f;
				     mGR.mMenuAni=.005f;
				     M.GameScreen = M.GAMEMENU;
				     setBubble();
				   break;
				 case 5:
					 M.GameScreen = M.GAMEWORLD;
					 setBubble2();
				   break;
			}
			mGR.mSel=0;
		}
		return true;
	}
	void DrawTexture(GL10 gl,SimplePlane Tex,float x,float y)
	{
		Tex.drawPos(gl, x, y);
	}
	void DrawTextureR(GL10 gl,SimplePlane Tex,float x,float y,float angle,float rotX,float rotY)
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
	void DrawTextureSS(GL10 gl,SimplePlane Tex,float x,float y,float sx,float sy,float w,float h)
	{
		Tex.drawSS(gl, x, y,sx,sy,w,h);
	}
	void DrawTextureRST(GL10 gl,SimplePlane Tex,float x,float y,float scal,float tran,float angle)
	{
		Tex.drawRotetScal(gl, x, y,scal,angle,tran);
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
	boolean Rext2RectColli(float x1,float y1,float dx1,float dy1,float x2,float y2,float dx2,float dy2)
	{
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
		
	void drawNumber(GL10 gl,String no,float x, float y,float dy,int Align)
	{
		float dx = mGR.mTex_Font[0].width()*.6f;
		String str = no;
		float len  = str.length();
		if(Align ==1) //Center
		  x -= (dx*(len))*.5f;
		if(Align ==2)//Right
	  	  x -= (dx*(len))*1f;
		String strs = no;
		
		for(int i =0;i<strs.length();i++)
		{
			int k;
			if(strs.charAt(i) =='.')
			  k = 12;
			else
		   	  k = ((int)strs.charAt(i))-48;
			if(k>=0 && k<mGR.mTex_Font.length)
				DrawTexture(gl,mGR.mTex_Font[k],x+i*dx,y+i*dy);
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
