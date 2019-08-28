//G.G. Enterprises
//HDFC0002487
//24872000000637
//

package com.hututu.game.scarygames;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Toast;
public class Group extends Mesh 
{
	GameRenderer mGR = null;
	int FireCnt,Counter,AniCnt1,AniCnt = 0; 
	float sx,sy;
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
	float zx=1,zvx=.004f;
	@Override
	public void draw(GL10 gl) 
	{
//		M.GameScreen=M.GAMESEL;
		Counter++;
		switch (M.GameScreen) {
		case M.GAMELOGO:
			DrawTextureS(gl, mGR.mTex_Logo, 0, 0,zx,zx);
			zx+=zvx;
			if(zx > 1.05f)
				zvx = -.005f;
			if(zx < 0.95f)
				zvx = 0.005f;
			if(Counter >120){
				M.GameScreen = M.GAMEMENU;
//				M.GameScreen = M.GAMEADD;//AdHouse
				Counter=0;//AdHouse
			}
			break;
			/*AdHouse*/
		case M.GAMEADD:
			if(Counter>100)
				DrawTexture(gl,mGR.mTex_Skip, .9f,-.9f);
			else{
				DrawTexture(gl, mGR.mTex_Hightbar,0.2f,-.9f);
				DrawTexture(gl, mGR.mTex_Pointer,Counter*(mGR.mTex_Hightbar.width()/100f)-.32f,-.9f);
			}
			break;
			/*AdHouse*/
		case M.GAMEMENU:
			DrawMenu(gl);
			break;
		case M.GAMESETTING:
			DrawSetting(gl);
			break;
		case M.GAMEHELP:
		case M.GAMEABOUT:
		case M.GAMEDIS:
			DrawHAD(gl);
			break;
		case M.GAMESKAL:
			drawGameSkal(gl);
			break;
		case M.GAMEPLAY:
			if(System.currentTimeMillis()>(mGR.startGame+(M.Timesec[mGR.mGameTime]*1000)))
			{
				SetGameOver();
			}
			switch (mGR.mGameNo) {
			case 1:
				DrawGamePlay(gl);
				break;
			case 0:
				GamePlayBallbalance(gl);
				break;
			case 2:
				DrawTexture(gl, mGR.mTex_Illution, 0, 0);
				DrawTexture(gl, mGR.mTex_Back[0], +.85f,-.90f);
				if(mGR.mSel == 13)
					DrawTexture(gl, mGR.mTex_Back[1], +.85f,-.90f);
				break;
			case 3:
				DrawMistake(gl);
				break;
			}
			break;
		case M.GAMESOUND:
			DrawSound(gl);
			break;
		case M.GAMETIME:
			DrawTime(gl);
			break;
		case M.GAMEOVER:
			DrawGameOver(gl);
			break;
		case M.GAMESEL:
			DrawGameSel(gl);
			break;
		}
		
		
		//GamePlayBallbalance(gl);
//		mGR.mFont.draw(gl, M.PLAY1, -.35f, .65f);
//		setting();
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
			}
			break;
			/*AdHouse*/
		case M.GAMEMENU:
			Handle_Menu(event);
			break;
		case M.GAMESETTING:
			Handle_Setting(event);
			break;
		case M.GAMEHELP:
		case M.GAMEABOUT:
		case M.GAMEDIS:
			Handle_HAD(event);
			break;
		case M.GAMESKAL:
			Handle_Skal(event);
			break;
		case M.GAMEPLAY:
			switch (mGR.mGameNo) {
			case 1:
				HandleGame(event);
				break;
			case 0:
				Handle_Ballbalance(event);
				break;
			case 2:
				Handle_Ballbalance(event);
				break;
			case 3:
				Handle_Ballbalance(event);
				break;
			}
			break;
		case M.GAMESOUND:
			Handle_Sound(event);
			break;
		case M.GAMETIME:
			Handle_Time(event);
			break;
		case M.GAMEOVER:
			Handle_GameOver(event);
			break;
		case M.GAMESEL:
			Handle_GameSel(event);
			break;
		}
		
		return true;
	}
	void DrawMistake(GL10 gl)
	{
		int k = 0;
		DrawTexture(gl, mGR.mTex_5mistake, 0, 0);
		for(int i=0;i<mGR.mMistake.length;i++)
		{
			if(mGR.mMistake[i].isBoolean)
			{
				DrawTexture(gl, mGR.mTex_Ring, mGR.mMistake[i].x,mGR.mMistake[i].y);
				k++;
			}
		}
		if(k == 5)
			SetGameOver();
		DrawTexture(gl, mGR.mTex_Back[0], +.85f,-.90f);
		if(mGR.mSel == 13)
			DrawTexture(gl, mGR.mTex_Back[1], +.85f,-.90f);
		
		
	}
	void DrawGamePlay(GL10 gl)
	{
		int k=0;
		boolean isgamewin = false;
		for(int i=0;i<mGR.totalBricks;i++)
    	{
    		for(int j=0;j<mGR.totalBricks;j++)
    		{
    			if(mGR.arr[k]!=mGR.checkNumber && mGR.mTex_Pic[mGR.arr[k]]!=null)
    			{
    				mGR.mTex_Pic[mGR.arr[k]].drawPos(gl, -1+mGR.mTex_Pic[0].width()/2+(mGR.mTex_Pic[0].width()+.02f)*j,.80f-mGR.mTex_Pic[0].Height()/2-(mGR.mTex_Pic[0].Height()+.015f)*i);
    				if(mGR.arr[k]!=k)
    					isgamewin = true;
    			}
    		k++;
    		}
    	}
		if(!isgamewin)
		{
			SetGameOver();
			
		}
//		mGR.mTex_pause.drawPos(gl, .9f,-.9f);
//		drawNumber(gl, mGR.mScore,-.9f, .7f);
	}
	boolean HandleGame(MotionEvent event)
	{
		int k=0;
		int curr = -1;
		switch(event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			 break;		
		 case MotionEvent.ACTION_MOVE:
			 break;
		  case MotionEvent.ACTION_UP:
			  if(CircRectsOverlap(.9f,-.9f,mGR.mTex_Back[0].width()/2,mGR.mTex_Back[0].Height()/2,screen2worldX(event.getX()),screen2worldY(event.getY()),0.01f))
			  {
				  //M.GameScreen = M.GAMEMENU;
			  }
			  /*else if(CircRectsOverlap(0f,0.9f,mGR.mTex_bowling.width()/2,mGR.mTex_bowling.Height()/2,screen2worldX(event.getX()),screen2worldY(event.getY()),0.01f))			  
			  {
				  Intent BannerI = new Intent(Intent.ACTION_VIEW);
				  BannerI.setData(Uri.parse("market://details?id=com.myo.game.bowling"));
				  mGR.mContext.startActivity(BannerI);
			  }*/
			  else{
				  for(int i=0;i<mGR.totalBricks&&curr==-1;i++)
					{
						for(int j=0;j<mGR.totalBricks&&curr==-1;j++)
						{
							//if(checkColloision(strpx+j*brickSize,strpy+i*brickSize,brickSize-2,brickSize-2,event.getX(),event.getY()))
							if(CircRectsOverlap(-1+mGR.mTex_Pic[0].width()/2+(mGR.mTex_Pic[0].width()+.01f)*j,.80f-mGR.mTex_Pic[0].Height()/2-(mGR.mTex_Pic[0].Height()+.008f)*i,mGR.mTex_Pic[0].width()/2,mGR.mTex_Pic[0].Height()/2,screen2worldX(event.getX()),screen2worldY(event.getY()),0.01f))
							{
								curr=k;
							}
							k++;
						}
					}
				  if(curr==-1)
					  break;
				  if(mGR.arr[curr]==mGR.checkNumber)
					  break;
				  if(curr>=1 && (curr %mGR.totalBricks) !=0)
				  {
					  if(mGR.arr[curr-1]==mGR.checkNumber)
					  {
						  mGR.arr[curr-1]=mGR.arr[curr];
						  mGR.arr[curr]=mGR.checkNumber;
						  mGR.mScore++;
						  break;
					  }
				  }
				  if(curr<=mGR.totalBricks*mGR.totalBricks-2 && (curr %mGR.totalBricks) != (mGR.totalBricks-1))
				  {
					  if(mGR.arr[curr+1]==mGR.checkNumber)
					  {
						  mGR.arr[curr+1]=mGR.arr[curr];
						  mGR.arr[curr]=mGR.checkNumber;
						  mGR.mScore++;
						  break;
					  }
				  }
				  if(curr>=mGR.totalBricks) 
				  {  
					  if(mGR.arr[curr-mGR.totalBricks]==mGR.checkNumber)
					  {
						  mGR.arr[curr-mGR.totalBricks]=mGR.arr[curr];
						  mGR.arr[curr]=mGR.checkNumber;
						  mGR.mScore++;
						  break;
					  }
				  }
				  if(curr<=mGR.totalBricks*mGR.totalBricks-mGR.totalBricks-1)
				  {
					  if(mGR.arr[curr+mGR.totalBricks]==mGR.checkNumber)
					  {
						  mGR.arr[curr+mGR.totalBricks]=mGR.arr[curr];
						  mGR.arr[curr]=mGR.checkNumber;
						  mGR.mScore++;
						  break;
					  }
				  }			  
			  }
			  break;
		}
		return false;
	}
	
	
	
	void DrawMenu(GL10 gl)
	{
		DrawTextureS(gl, mGR.mTex_BG, 0, 0,2,2);
		
		DrawTexture(gl, mGR.mTex_Splashfont, 0, .6f);
		
		DrawTexture(gl, mGR.mTex_Bar[0], 0, .1f);
		DrawTexture(gl, mGR.mTex_settingicon, -.57f, .1f);
		DrawTexture(gl, mGR.mTex_Ok[0], 0.6f, .1f);
		mGR.mFont.draw(gl, M.Scary_Game_Setting,-.4f, .1f,1);
		
		DrawTexture(gl, mGR.mTex_Bar[3], 0, -.22f);
		DrawTexture(gl, mGR.mTex_playfont, 0, -.22f);
		DrawTexture(gl, mGR.mTex_Playgame[0], 0.6f, -.22f);
		
		
		DrawTexture(gl, mGR.mTex_Bar[4]		, -.19f,-.5f);
		DrawTexture(gl, mGR.mTex_5starde	, 0.23f, -.5f);
		DrawTexture(gl, mGR.mTex_Share[0]	, .57f, -.5f);
		mGR.mFont.draw(gl, M.Share, -.68f, -.39f,0);

		DrawTexture(gl, mGR.mTex_Info[0], -.85f,-.9f);
		
		DrawTexture(gl, mGR.mTex_Exit[0], +.85f,-.9f);
		
		
		switch (mGR.mSel) {
		case 1:
			DrawTexture(gl, mGR.mTex_Ok[1], 0.6f, .1f);
			break;
		case 2:
			DrawTexture(gl, mGR.mTex_Playgame[1], 0.6f, -.22f);
			break;
		case 3:
			DrawTexture(gl, mGR.mTex_Ok[1], 0.23f, -.5f);
			break;
		case 4:
			DrawTexture(gl, mGR.mTex_Share[1]	, .57f, -.5f);
			break;
		case 5:
			DrawTexture(gl, mGR.mTex_Info[1], -.85f,-.9f);
			break;
		case 6:
			DrawTexture(gl, mGR.mTex_Exit[1], +.85f,-.9f);
			break;
		}
	}
	
	boolean Handle_Menu(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(0, .1f, mGR.mTex_Bar[0].width()*.4f, mGR.mTex_Bar[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 1;
		}
		if(CircRectsOverlap(0, -.22f, mGR.mTex_Bar[3].width()*.4f, mGR.mTex_Bar[3].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 2;
		}
		if(CircRectsOverlap(-.19f,-.5f, mGR.mTex_Bar[4].width()*.4f, mGR.mTex_Bar[4].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 3;
		}
		if(CircRectsOverlap(.57f, -.5f, mGR.mTex_Share[0].width()*.4f, mGR.mTex_Share[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 4;
		}
		if(CircRectsOverlap(-.85f,-.9f, mGR.mTex_Info[0].width()*.4f, mGR.mTex_Info[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 5;
		}
		if(CircRectsOverlap(0.85f,-.9f, mGR.mTex_Exit[0].width()*.4f, mGR.mTex_Exit[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 6;
		}
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			switch (mGR.mSel) {
			case 1:
				M.GameScreen = M.GAMESETTING;
				break;
			case 2:
				mGR.reset();
				break;
			case 3:
				Intent intent1 = new Intent(Intent.ACTION_VIEW);
//				intent1.setData(Uri.parse("market://details?id="+getClass().getPackage().getName()));
				intent1.setData(Uri.parse(M.MARKET));
				GameRenderer.mContext.startActivity(intent1);
				break;
			case 4:
				share2friend();
				break;
			case 5:
				M.GameScreen = M.GAMEABOUT;
				break;
			case 6:
				GameRenderer.mStart.get();
				break;
			}
			mGR.mSel = 0;
		}
		return true;
	}
	
	
	void share2friend()
	{
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("text/plain");
		i.putExtra(Intent.EXTRA_SUBJECT, "Scary Games");
		i.putExtra(Intent.EXTRA_TEXT   , "DO YOU WANT TO SCARE YOUR FRIENDS? Install the best free application  https://play.google.com/store/apps/details?id="+getClass().getPackage().getName());
		try {
		    GameRenderer.mContext.startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(GameRenderer.mStart, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
	
	void DrawSetting(GL10 gl)
	{
		DrawTextureS(gl, mGR.mTex_BG, 0, 0,2,2);
		
		DrawTexture(gl, mGR.mTex_Bar[0], 0, .65f);
		//DrawTexture(gl, mGR.mTex_settingicon, -.54f, .65f);
		DrawTexture(gl, mGR.mTex_GameIcon[mGR.mGameNo], -.54f, .65f);
		DrawTexture(gl, mGR.mTex_Ok[0], 0.6f, .65f);
		mGR.mFont.draw(gl, M.Choose_Game,-.69f, .15f+.65f,0);
		mGR.mFont.draw(gl, M.PLAY[mGR.mGameNo], -.35f, .65f,1);
		
		DrawTexture(gl, mGR.mTex_Bar[0], 0, .31f);
		//DrawTexture(gl, mGR.mTex_settingicon, -.54f, .31f);
		DrawTextureS(gl,mGR.mTex_Skal[mGR.mSelImg],-.54f, .31f, .3f, .2f);
		DrawTexture(gl, mGR.mTex_Ok[0], 0.6f, .31f);
		mGR.mFont.draw(gl, M.Choose_Scary, -.69f, .15f+.31f,0);
		mGR.mFont.draw(gl, M.ScaryName[mGR.mSelImg],-.35f, .31f,1);
		
		DrawTexture(gl, mGR.mTex_Bar[1]		, 0.00f, .02f);
		DrawTexture(gl, mGR.mTex_Select[0]	, -.54f, .02f);
		DrawTexture(gl, mGR.mTex_Ok[0]		, 0.6f, .02f);
		mGR.mFont.draw(gl, M.Time,-.69f, .11f+.02f,0);
		mGR.mFont.draw(gl, M.TimeDetail[mGR.mGameTime],-.40f, .02f,1);
		
		
		DrawTexture(gl, mGR.mTex_Bar[1]		, 0.00f,-.22f);
		DrawTexture(gl, mGR.mTex_Select[0]	, -.54f,-.22f);
		DrawTexture(gl, mGR.mTex_Ok[0]		, 0.60f,-.22f);
		mGR.mFont.draw(gl, M.Sound,-.69f, .11f-.22f,0);
		mGR.mFont.draw(gl, M.Scrreming[mGR.mGameSound],-.40f,-.22f,1);
		
		
		DrawTexture(gl, mGR.mTex_Bar[3]		, 0.00f, -.48f);
		DrawTexture(gl, mGR.mTex_playfont	, 0.00f, -.48f);
		DrawTexture(gl, mGR.mTex_Playgame[0], 0.60f, -.48f);
//		
		
		
		DrawTexture(gl, mGR.mTex_Bar[4]		, -.19f,-.71f);
		DrawTexture(gl, mGR.mTex_5starde	, 0.23f,-.71f);
		DrawTexture(gl, mGR.mTex_Share[0]	, 0.57f,-.71f);
		mGR.mFont.draw(gl, M.Share,-.69f, .11f-.71f,0);

		DrawTexture(gl, mGR.mTex_Info[0], -.85f,-.92f);
		
		DrawTexture(gl, mGR.mTex_Back[0], +.85f,-.92f);
		
		
		switch (mGR.mSel) {
		case 1:
			DrawTexture(gl, mGR.mTex_Ok[1], 0.6f, .65f);
			break;
		case 2:
			DrawTexture(gl, mGR.mTex_Ok[1], 0.6f, .31f);
			break;
		case 3:
			DrawTexture(gl, mGR.mTex_Select[1]	, -.54f, .02f);
			DrawTexture(gl, mGR.mTex_Ok[1]		, 0.6f, .02f);
			break;
		case 4:
			DrawTexture(gl, mGR.mTex_Select[1]	, -.54f,-.22f);
			DrawTexture(gl, mGR.mTex_Ok[1]		, 0.60f,-.22f);
			break;
		case 5:
			DrawTexture(gl, mGR.mTex_Playgame[1], 0.60f, -.48f);
			break;
		case 6:
			DrawTexture(gl, mGR.mTex_Ok[1], 0.23f,-.71f);
			break;
		case 7:
			DrawTexture(gl, mGR.mTex_Share[1]	, 0.57f,-.71f);
			break;
		case 8:
			DrawTexture(gl, mGR.mTex_Info[1], -.85f,-.92f);
			break;
		case 9:
			DrawTexture(gl, mGR.mTex_Back[1], +.85f,-.92f);
			break;
		}
	}
	
	boolean Handle_Setting(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(0, .65f, mGR.mTex_Bar[0].width()*.4f, mGR.mTex_Bar[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 1;
		}
		if(CircRectsOverlap(0, .31f, mGR.mTex_Bar[0].width()*.4f, mGR.mTex_Bar[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 2;
		}
		if(CircRectsOverlap(0.00f, .02f, mGR.mTex_Bar[1].width()*.4f, mGR.mTex_Bar[1].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 3;
		}
		if(CircRectsOverlap(0.00f,-.22f, mGR.mTex_Bar[1].width()*.4f, mGR.mTex_Bar[1].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 4;
		}
		
		if(CircRectsOverlap(0.00f, -.48f, mGR.mTex_Bar[3].width()*.4f, mGR.mTex_Bar[1].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 5;
		}
		
		if(CircRectsOverlap(-.19f,-.71f, mGR.mTex_Bar[4].width()*.4f, mGR.mTex_Bar[4].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 6;
		}
		
		if(CircRectsOverlap(0.57f,-.71f, mGR.mTex_Share[0].width()*.4f, mGR.mTex_Share[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 7;
		}
		if(CircRectsOverlap(-.85f,-.92f, mGR.mTex_Info[0].width()*.4f, mGR.mTex_Info[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel =8;
		}
		if(CircRectsOverlap(0.85f,-.92f, mGR.mTex_Exit[0].width()*.4f, mGR.mTex_Exit[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 9;
		}
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			switch (mGR.mSel) {
			case 1:
				M.GameScreen = M.GAMESEL;
				//mGR.mGameNo++;
				//mGR.mGameNo %=4; 
				break;
			case 2:
				M.GameScreen = M.GAMESKAL;
				break;
			case 3:
				M.GameScreen = M.GAMETIME;
				break;
			case 4:
				M.GameScreen = M.GAMESOUND;
				break;
			case 5:
				mGR.reset();
				break;
			case 6:
				Intent intent1 = new Intent(Intent.ACTION_VIEW);
				intent1.setData(Uri.parse(M.MARKET));
				GameRenderer.mContext.startActivity(intent1);
				break;
			case 7:
				share2friend();
				break;
			case 8:
				M.GameScreen = M.GAMEABOUT;
				break;
			case 9:
				M.GameScreen = M.GAMEMENU;
				break;
			}
			mGR.mSel = 0;
		}
		return true;
	}
	
	
	void DrawHAD(GL10 gl)
	{
		DrawTextureS(gl, mGR.mTex_BG, 0, 0,2,2);
		
		DrawTexture(gl, mGR.mTex_About[0],-.6f, 0.7f);
		DrawTexture(gl, mGR.mTex_Disct[0],0.0f, 0.7f);
		DrawTexture(gl, mGR.mTex_Help[0] ,0.6f, 0.7f);
		
		DrawTexture(gl, mGR.mTex_infoback, 0, .15f);
		if(M.GameScreen == M.GAMEHELP)
		{
			DrawTexture(gl, mGR.mTex_helpfont, 0, .15f);
			DrawTexture(gl, mGR.mTex_Help[1] ,0.6f, 0.7f);
		}
		if(M.GameScreen == M.GAMEABOUT)
		{
			DrawTexture(gl, mGR.mTex_aboutusfont, 0, .15f);
			DrawTexture(gl, mGR.mTex_About[1],-.6f, 0.7f);
		}
		if(M.GameScreen == M.GAMEDIS)
		{
			DrawTexture(gl, mGR.mTex_discfont, 0, .15f);
			DrawTexture(gl, mGR.mTex_Disct[1],0.0f, 0.7f);
		}
		
		DrawTexture(gl, mGR.mTex_Bar[3]		, 0.0f, -.44f);
		DrawTexture(gl, mGR.mTex_shootthebottle, -.61f, -.44f);
		DrawTexture(gl, mGR.mTex_playshootfont,0, -.44f);
		DrawTexture(gl, mGR.mTex_Playgame[0], 0.6f, -.44f);
		
		DrawTexture(gl, mGR.mTex_Bar[4]		, -.19f,-.68f);
		DrawTexture(gl, mGR.mTex_5starde	, 0.23f,-.68f);
		DrawTexture(gl, mGR.mTex_Share[0]	, 0.57f,-.68f);


		DrawTexture(gl, mGR.mTex_Back[0], +.85f,-.9f);
		
		
		switch (mGR.mSel) {
		case 1:
			DrawTexture(gl, mGR.mTex_About[1],-.6f, 0.7f);
			break;
		case 2:
			DrawTexture(gl, mGR.mTex_Disct[1],0.0f, 0.7f);
			break;
		case 3:
			DrawTexture(gl, mGR.mTex_Help[1] ,0.6f, 0.7f);
			break;
		case 4:
			DrawTexture(gl, mGR.mTex_Playgame[1], 0.6f, -.44f);
			break;
		case 5:
			DrawTexture(gl, mGR.mTex_Ok[1], 0.23f,-.68f);
			break;
		case 6:
			DrawTexture(gl, mGR.mTex_Share[1]	, 0.57f,-.68f);
			break;
		case 7:
			DrawTexture(gl, mGR.mTex_Back[1], +.85f,-.9f);
			break;
		}
	}
	
	boolean Handle_HAD(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(-.6f, 0.7f, mGR.mTex_About[0].width()*.4f, mGR.mTex_About[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 1;
		}
		if(CircRectsOverlap(-.0f, 0.7f, mGR.mTex_Disct[0].width()*.4f, mGR.mTex_Disct[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 2;
		}
		if(CircRectsOverlap(0.6f, 0.7f, mGR.mTex_Help[0].width()*.4f, mGR.mTex_Help[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 3;
		}
		
		
		if(CircRectsOverlap(0.0f, -.44f, mGR.mTex_Bar[3].width()*.4f, mGR.mTex_Bar[3].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 4;
		}
		if(CircRectsOverlap(-.19f,-.68f, mGR.mTex_Bar[4].width()*.4f, mGR.mTex_Bar[4].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 5;
		}
		if(CircRectsOverlap(0.57f,-.68f, mGR.mTex_Share[0].width()*.4f, mGR.mTex_Share[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 6;
		}
		if(CircRectsOverlap(0.85f,-.9f, mGR.mTex_Exit[0].width()*.4f, mGR.mTex_Exit[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 7;
		}
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			switch (mGR.mSel) {
			case 1:
				M.GameScreen = M.GAMEABOUT;
				break;
			case 2:
				M.GameScreen = M.GAMEDIS;
				break;
			case 3:
				M.GameScreen = M.GAMEHELP;
				break;
			case 4:
				Intent intent1 = new Intent(Intent.ACTION_VIEW);
				intent1.setData(Uri.parse(M.MARKET));
				GameRenderer.mContext.startActivity(intent1);
				break;
			case 5:
				Intent intent2 = new Intent(Intent.ACTION_VIEW);
				intent2.setData(Uri.parse("market://details?id=com.hututu.game.shootthebottle"));
				GameRenderer.mContext.startActivity(intent2);
				break;
			case 6:
				share2friend();
				break;
			case 7:
				M.GameScreen = M.GAMEMENU;
				break;
			}
			mGR.mSel = 0;
		}
		return true;
	}
	
	
	
	void drawGameSkal(GL10 gl)
	{
		int k =0;
		for(int i=0;i<4;i++)
		{
			for(int j=0;j<3;j++)
			{
				DrawTextureS(gl,mGR.mTex_Skal[k],-.63f+(j*.63f),.59f-(i*.46f), .61f, .45f);
				if(mGR.mSelImg == k)
					DrawTexture(gl,mGR.mTex_Right,-.63f+(j*.63f),.59f-(i*.46f));
				k++;
			}
		}
		if(mGR.mSel > 0)
			DrawTextureS(gl,mGR.mTex_Skal[mGR.mSel-1],-.63f+(((mGR.mSel-1)%3)*.63f),.59f-(((mGR.mSel-1)/3)*.46f), .81f, .65f);
//		DrawTextureS(gl,mGR.mTex_Skal[k],-1f+sx,1f+sy, .5f);
	}
	boolean Handle_Skal(MotionEvent event)
	{
		int k=0;
		mGR.mSel = 0;
		for(int i=0;i<4;i++)
		{
			for(int j=0;j<3;j++)
			{
				if(CircRectsOverlap(-.63f+(j*.63f),.59f-(i*.46f), .2f,.2f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
				{
					mGR.mSel = k+1;
				}
				k++;
			}
		}
		if(event.getAction() == MotionEvent.ACTION_UP && mGR.mSel > 0)
		{
			mGR.mSelImg = mGR.mSel - 1;
			mGR.mSel = 0;
			M.GameScreen = M.GAMESETTING;
		}
		return true;
	}
	
	
	
	
	
	void DrawSound(GL10 gl)
	{
		DrawTextureS(gl, mGR.mTex_BG, 0, 0,2,2);
		for(int i = 0;i<6 ;i++)
		{
			DrawTexture(gl, mGR.mTex_Bar[2]		, -.19f,0.65f-(i*.25f));
			DrawTexture(gl, mGR.mTex_Select[0]	, -.60f,0.65f-(i*.25f));
			DrawTexture(gl, mGR.mTex_Play[0]	, 0.57f,0.65f-(i*.25f));
			if(mGR.mGameSound == i)
			{
				DrawTexture(gl, mGR.mTex_Select[1]	, -.60f,0.65f-(i*.25f));
			}
			if(mGR.mSel-7 == i)
			{
				DrawTexture(gl, mGR.mTex_Play[1]	, 0.57f,0.65f-(i*.25f));
			}
			mGR.mFont.draw(gl, M.Scrreming[i]	, -.52f,0.65f-(i*.25f),1);
		}
		DrawTexture(gl, mGR.mTex_Back[0], +.85f,-.90f);
		if(mGR.mSel == 13)
			DrawTexture(gl, mGR.mTex_Back[1], +.85f,-.90f);
	}
	
	
	boolean Handle_Sound(MotionEvent event)
	{
		mGR.mSel = 0;
		for(int i = 0;i<6 ;i++)
		{
			if(CircRectsOverlap(-.19f,0.65f-(i*.25f), mGR.mTex_Bar[2].width()*.4f, mGR.mTex_Bar[4].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				mGR.mSel = i+1;
			}
			if(CircRectsOverlap(0.57f,0.65f-(i*.25f), mGR.mTex_Share[0].width()*.4f, mGR.mTex_Share[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				mGR.mSel = i+7;
			}
		}
		if(CircRectsOverlap(+.85f,-.90f, mGR.mTex_Back[0].width()*.4f, mGR.mTex_Back[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 13;
		}
		if(event.getAction() == MotionEvent.ACTION_UP && mGR.mSel > 0)
		{
			if(mGR.mSel == 13)
			{
				M.GameScreen = M.GAMESETTING;
				M.stop();
			}
			else
			{
				if(mGR.mSel <7)
				{
					mGR.mGameSound = mGR.mSel - 1;
					M.GameScreen = M.GAMESETTING;
					M.stop();
				}
				else
				{
					M.playsound(GameRenderer.mContext, mGR.mSel - 7);
				}
				mGR.mSel = 0;
			}
		}
		return true;
	}
	
	
	void DrawTime(GL10 gl)
	{
		DrawTextureS(gl, mGR.mTex_BG, 0, 0,2,2);
		for(int i = 0;i<7 ;i++)
		{
			DrawTexture(gl, mGR.mTex_Bar[1]		, 0.00f,0.74f-(i*.24f));
			DrawTexture(gl, mGR.mTex_Select[0]	, -.60f,0.74f-(i*.24f));
			DrawTexture(gl, mGR.mTex_Ok[0]		, 0.57f,0.74f-(i*.24f));
			if(mGR.mSel-1 == i)
			{
				DrawTexture(gl, mGR.mTex_Ok[1]		, 0.57f,0.74f-(i*.24f));
			}
			if(mGR.mGameTime == i)
			{
				DrawTexture(gl, mGR.mTex_Select[1]	, -.60f,0.74f-(i*.24f));
			}
			mGR.mFont.draw(gl, M.TimeDetail[i]	, -.52f,0.74f-(i*.24f),1);
			
		}
		DrawTexture(gl, mGR.mTex_Back[0], +.85f,-.90f);
		if(mGR.mSel == 13)
			DrawTexture(gl, mGR.mTex_Back[1], +.85f,-.90f);
	}
	
	
	boolean Handle_Time(MotionEvent event)
	{
		mGR.mSel = 0;
		for(int i = 0;i<7 ;i++)
		{
			if(CircRectsOverlap(0.00f,0.74f-(i*.24f), mGR.mTex_Bar[1].width()*.4f, mGR.mTex_Bar[4].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				mGR.mSel = i+1;
			}
			
		}
		if(CircRectsOverlap(+.85f,-.90f, mGR.mTex_Back[0].width()*.4f, mGR.mTex_Back[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 13;
		}
		if(event.getAction() == MotionEvent.ACTION_UP && mGR.mSel > 0)
		{
			if(mGR.mSel == 13)
			{
				M.GameScreen = M.GAMESETTING;
			}
			else
			{
				M.GameScreen = M.GAMESETTING;
				mGR.mGameTime = mGR.mSel - 1;
				mGR.mSel = 0;
			}
		}
		return true;
	}
	
	void DrawGameOver(GL10 gl)
	{
		DrawTextureS(gl, mGR.mTex_Skal[mGR.mSelImg], 0, 0,mGR.scal,mGR.scal);
		if(mGR.scal<2)
			mGR.scal+=.2f;
		else{
		DrawTexture(gl, mGR.mTex_Back[0], +.85f,-.90f);
		if(mGR.mSel == 13)
			DrawTexture(gl, mGR.mTex_Back[1], +.85f,-.90f);
		}
		if(Counter == 60){
			GameRenderer.mStart.ShowInterstitial();
		}
	}
	
	
	boolean Handle_GameOver(MotionEvent event)
	{
		mGR.mSel = 0;
		
		if(CircRectsOverlap(+.85f,-.90f, mGR.mTex_Back[0].width()*.4f, mGR.mTex_Back[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 13;
		}
		if(event.getAction() == MotionEvent.ACTION_UP && mGR.mSel > 0)
		{
			if(mGR.mSel == 13)
			{
				M.GameScreen = M.GAMESETTING;
			}
			
		}
		return true;
	}
	
	void GameLogic()
	{
		int i = 0;
		if (mGR.score > mGR.Hscore) {
			mGR.Hscore = mGR.score;
			if (mGR.newScore == 5000000)
				mGR.newScore = 0;
		}
		if (mGR.speenCounter >= 50)
			mGR.PlayerSpeed = M.xIncNormal;
		float Dcr = mGR.PlayerSpeed * M.Dc;
		mGR.Player.tuch = false;
		Counter++;
		FireCnt++;
		if (FireCnt >= 50) {
			if (Counter % 10 == 0)
				AniCnt1++;
			if (AniCnt1 > 5) {
				FireCnt = 0;
				AniCnt1 = 0;
			}
 		}
		if (Counter % 2 == 0)
			AniCnt++;
		if (Counter % 10 == 0) {
			if (mGR.acx > 0)
				mGR.counter1++;
			else
				mGR.counter1--;
			if (mGR.counter1 < 0)
				mGR.counter1 = 2000;
		}
		if (Counter > 0)
			mGR.Player.counter--;
		if (Counter % 4 == 0)
			mGR.score++;
		if (Counter % 5 == 0)
			mGR.speenCounter++;

		if (mGR.msg.y < 1.5) {
			mGR.msg.y += mGR.msg.vy;
			mGR.msg.vy += .001f;
		}
		for (i = 0; i < mGR.mSweet.length; i++) {
			mGR.mSweet[i].y -= Dcr;
			float r = mGR.mImg_BallMove[0].width() / 3 + .05f;
			if (CircRectsOverlap(mGR.mSweet[i].x + (M.gapWidth) / 2,
					mGR.mSweet[i].y, mGR.mImg_Speed[0].width(),
					mGR.mImg_Speed[0].Height(), mGR.Player.x + r, mGR.Player.y
							+ r, r)
					&& (mGR.mSweet[i].gift == 0 || mGR.mSweet[i].gift == 1)) {
				mGR.msg.pos = mGR.mSweet[i].gift;
				mGR.msg.y = -1;
				mGR.msg.vy = 0;
				if (mGR.mSweet[i].gift == 0)
					mGR.PlayerSpeed = M.xIncFast;
				else
					mGR.PlayerSpeed = M.xIncSlow;
				M.sound7(GameRenderer.mContext, R.drawable.take);
				mGR.speenCounter = 0;
				mGR.mSweet[i].gift = 20;
			}
			int m = (int) (Math.abs(mGR.Player.vy / .15f));
			m = m < 1 ? 1 : m;
			for (int f = 0; f < m; f++) {
				float get = (mGR.Player.vy > 0 ? 1 : -1) * .2f * f; // .15
				if ((CircRectsOverlap(mGR.mSweet[i].x - mGR.mImg_strip.width()
						/ 2 - .17f, mGR.mSweet[i].y + mGR.mImg_strip.Height()
						/ 2, mGR.mImg_strip.width() / 2 - .05f,
						mGR.mImg_strip.Height() / 2, mGR.Player.x, mGR.Player.y
								+ get, r) || CircRectsOverlap(mGR.mSweet[i].x
						+ mGR.mImg_strip.width() / 2 + M.gapWidth + .17f,
						mGR.mSweet[i].y + mGR.mImg_strip.Height() / 2,
						mGR.mImg_strip.width() / 2 - .05f,
						mGR.mImg_strip.Height() / 2, mGR.Player.x, mGR.Player.y
								+ get, r))/* &&mGR.Player.counter<1 */)// change
				{
					if (!mGR.mSweet[i].tuch) {
						mGR.Player.pos = 0;
						mGR.mSweet[i].tuch = true;
						mGR.Player.vy = .1f;
						mGR.Player.y = mGR.mSweet[i].y + .13f;
						M.sound8(GameRenderer.mContext, R.drawable.bounce);
						// DropSet(mGR.Player.x,mGR.Player.y);
					} else {

						mGR.Player.tuch = true;
						mGR.Player.vy = Dcr;
						mGR.Player.y = mGR.mSweet[i].y + .13f;/* +.11f; */
					}
					break;
				}
			}
			if (mGR.mSweet[i].y > 1 + mGR.mImg_strip.Height()) {
				float sy = 0;
				float x = M.mRand.nextInt(160) / 100f;
				x = -.8f + x;
				int g = M.mRand.nextInt(20);
				if (i == 0)
					sy = mGR.mSweet[mGR.mSweet.length - 1].y
							- M.mRand.nextFloat() % .5f + M.Diff;
				else
					sy = mGR.mSweet[i - 1].y - M.mRand.nextFloat() % .5f
							+ M.Diff;
				mGR.mSweet[i].setSweet(x, sy, (int) g, false, 0);
			}
		}
		if (mGR.Player.tuch) {

			mGR.Player.vy = Dcr;
			mGR.Player.b = false;
		} else {
			if (!mGR.Player.b) {
				mGR.Player.counter = 5;
				mGR.Player.b = true;
			}
			if (mGR.Player.pos < 3)
				mGR.Player.pos++;
			if (mGR.Player.vy == Dcr)
				mGR.Player.vy = 0;
			mGR.Player.vy -= (mGR.PlayerSpeed * 1.1f);
			if (mGR.Player.vy < -.15)
				mGR.Player.vy = -.15f;
			mGR.Player.y += mGR.Player.vy;

		}
		if (mGR.Player.y < -1.2 || mGR.Player.y > 1.2) {
			mGR.msg.y = -2;
			SetGameOver();
			// M.BgStop();
		}

		/*
		 * // New For Ninja for(i=0;i<mGR.mBgScrl.length;i++) {
		 * 
		 * if(mGR.mBgScrl[i].y>1+mGR.mImg_bg[0].Height()/2) { if(i>0)
		 * mGR.mBgScrl[i].y = mGR.mBgScrl[i-1].y-(mGR.mImg_bg[0].Height()); else
		 * mGR.mBgScrl[i].y =
		 * mGR.mBgScrl[mGR.mBgScrl.length-1].y-(mGR.mImg_bg[0].Height()); }
		 * mGR.mBgScrl[i].y +=mGR.PlayerSpeed/4; }
		 * 
		 * // New For Ninja
		 */
	}
	
	
	void GamePlayBallbalance(GL10 gl)
	{
		GameLogic();
		{
			mGR.Player.x -= (mGR.acx*.02f);
			if(mGR.Player.x<	-1 + mGR.mImg_BallMove[0].width()/2)
				mGR.Player.x =	-1 + mGR.mImg_BallMove[0].width()/2;
			if(mGR.Player.x >	+1 - mGR.mImg_BallMove[0].width()/2)
				mGR.Player.x = 	+1 - mGR.mImg_BallMove[0].width()/2;
		}
		for (int i = 0; i < mGR.BG.length; i++)
		{
			DrawTexture(gl, mGR.mTex_GameBG, 0, mGR.BG[i]);
		}
		for (int i = 0; i < mGR.BG.length; i++)
		{
			mGR.BG[i]-=.01f;
		}
		for (int i = 0; i < mGR.BG.length; i++)
		{
			if(mGR.BG[i]<-1.1f)
			{
				if( i==mGR.BG.length-1)
					mGR.BG[i] = mGR.BG[0]+mGR.mTex_GameBG.Height();
				else
					mGR.BG[i] = mGR.BG[i+1]+mGR.mTex_GameBG.Height();
			}
		}
		
		if (M.GameScreen == M.GAMEPLAY || M.GameScreen == M.GAMEPAUSE) {
			for (int i = 0; i < mGR.mSweet.length; i++) {
				if (mGR.mSweet[i].y > -1.3f) {
					mGR.mImg_strip.drawPos(gl,mGR.mSweet[i].x - mGR.mImg_strip.width() / 2,mGR.mSweet[i].y);
					mGR.mImg_strip.drawPos(gl,mGR.mSweet[i].x + mGR.mImg_strip.width() / 2+ M.gapWidth, mGR.mSweet[i].y);
					if (mGR.mSweet[i].gift == 0 || mGR.mSweet[i].gift == 1)
						mGR.mImg_Speed[mGR.mSweet[i].gift].drawPos(gl,mGR.mSweet[i].x+ mGR.mImg_Speed[0].width() / 2,mGR.mSweet[i].y+ mGR.mImg_Speed[0].Height()/ 2);
				}
			}
		}
		mGR.mImg_msg[mGR.msg.pos % 2].drawPos(gl, mGR.msg.x, mGR.msg.y);

		// if(M.GameScreen==M.GAMEPLAY)
		// {
		// mGR.mImg_Pause[0].drawPos(gl, -.8f,-.85f);
		// if(mGR.mSel==1)
		// mGR.mImg_Pause[1].drawPos(gl, -.8f,-.85f);
		// }
		// if(M.GameScreen==M.GAMEPAUSE)
		// {
		// mGR.mImg_Play[0].drawPos(gl, -.8f,-.85f);
		// if(mGR.mSel==1)
		// mGR.mImg_Play[1].drawPos(gl, -.8f,-.85f);
		// }

		DrawTexture(gl, mGR.mTex_Back[0], +.85f,-.90f);
		if(mGR.mSel == 13)
			DrawTexture(gl, mGR.mTex_Back[1], +.85f,-.90f);
		
		if (M.GameScreen == M.GAMEPLAY) {
			// mGR.mImg_DScore.drawPos(gl, -.76f,.9f);
			mGR.mFont.draw(gl, "" + mGR.score, -.48f, .9f,0);

		}

		// if(!mGR.Player.tuch)
		// mGR.mImg_BallJump[mGR.Player.pos%4].drawPos(gl, mGR.Player.x,
		// mGR.Player.y);
		// else
		if (M.GameScreen == M.GAMEPLAY || M.GameScreen == M.GAMEPAUSE) {
			if (mGR.acx < 0) {
				mGR.mImg_BallMove[AniCnt % mGR.mImg_BallMove.length].drawPos(
						gl, mGR.Player.x, mGR.Player.y);
				// if(FireCnt>=50)
				// {
				// mGR.mImg_Fire[AniCnt1%mGR.mImg_Fire.length].drawPos(gl,
				// mGR.Player.x+.19f, mGR.Player.y+.132f);
				// }
			} else {
				mGR.mImg_BallMoveFlip[AniCnt % mGR.mImg_BallMove.length]
						.drawPos(gl, mGR.Player.x, mGR.Player.y);
				// if(FireCnt>=50)
				// {
				// mGR.mImg_FireFlip[AniCnt1%mGR.mImg_Fire.length].drawPos(gl,mGR.Player.x-.192f,
				// mGR.Player.y+.132f);
				// }
			}

		}

		// if(M.GameScreen == M.GamePlay || M.GameScreen == M.GamePause ||
		// M.GameScreen == M.GameStart)
		// {
		// if(M.setValue)
		// mGR.mImg_soundOn[mGR.mSel==100?1:0].drawPos(gl,.8f,.85f);
		// else
		// mGR.mImg_soundOff[mGR.mSel==100?1:0].drawPos(gl,.8f,.85f);
		// }
		// DrawStarAni(gl);
		if (mGR.newScore < 50 && M.GameScreen == M.GAMEPLAY) {
			// mGR.newScore++;
			// mGR.mTex_newscore.draw(gl);
			// Start4Reset(((20+mGR.mRand.nextInt(160))/100f)-1f,((20+mGR.mRand.nextInt(160))/100f)-1f);
		}
		// DrawDrop(gl);

	}
	
	boolean Handle_Ballbalance(MotionEvent event)
	{
		mGR.mSel = 0;
		
		
		
		if(CircRectsOverlap(+.85f,-.90f, mGR.mTex_Back[0].width()*.4f, mGR.mTex_Back[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 13;
		}
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			if(mGR.mSel== 13)
				M.GameScreen = M.GAMEMENU;
		
			if(mGR.mGameNo == 3)
			{
				if(CircRectsOverlap(0.07f,0.87f, mGR.mTex_Back[0].width()*.4f, mGR.mTex_Back[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
				{
					mGR.mMistake[0].isBoolean = true;
				}
				if(CircRectsOverlap(0.57f,0.67f   , mGR.mTex_Back[0].width()*.4f, mGR.mTex_Back[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
				{
					mGR.mMistake[1].isBoolean = true;
				}
				if(CircRectsOverlap(-.65f,0.41f  , mGR.mTex_Back[0].width()*.4f, mGR.mTex_Back[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
				{
					mGR.mMistake[2].isBoolean = true;
				}
				if(CircRectsOverlap(-.05f,0.18f , mGR.mTex_Back[0].width()*.4f, mGR.mTex_Back[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
				{
					mGR.mMistake[3].isBoolean = true;
				}
				if(CircRectsOverlap(-.78f,0.64f, mGR.mTex_Back[0].width()*.4f, mGR.mTex_Back[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
				{
					mGR.mMistake[4].isBoolean = true;
				}
			}
				
		}
		Log.d("    "+screen2worldX(event.getX()), screen2worldY(event.getY())+"   ");
		return true;
	}
	
	void SetGameOver()
	{
		Counter =0;
		M.GameScreen = M.GAMEOVER;
		AudioManager audioManager = (AudioManager) GameRenderer.mContext.getSystemService(Context.AUDIO_SERVICE);
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
		M.playsound(GameRenderer.mContext,mGR.mGameSound);
	}
	
	void DrawGameSel(GL10 gl)
	{
		DrawTextureS(gl, mGR.mTex_BG, 0, 0,2,2);
		for(int i = 0;i<4 ;i++)
		{
			DrawTexture(gl, mGR.mTex_Bar[0]		, 0.00f,.58f-(i*.37f));
			DrawTexture(gl, mGR.mTex_Select[0]	, 0.60f,.58f-(i*.37f));
			DrawTexture(gl, mGR.mTex_GameIcon[i], -.57f,.58f-(i*.37f));
			if(mGR.mSel-1 == i)
			{
				//DrawTexture(gl, mGR.mTex_Ok[1]		, 0.57f,0.74f-(i*.24f));
			}
			if(mGR.mGameNo == i)
			{
				DrawTexture(gl, mGR.mTex_Select[1]	, 0.60f,.58f-(i*.37f));
			}
			mGR.mFont.draw(gl, M.PLAY[i]	, -.38f,.58f-(i*.37f),1);
			
		}
		DrawTexture(gl, mGR.mTex_Back[0], +.85f,-.90f);
		if(mGR.mSel == 13)
			DrawTexture(gl, mGR.mTex_Back[1], +.85f,-.90f);
	}
	
	
	boolean Handle_GameSel(MotionEvent event)
	{
		mGR.mSel = 0;
		for(int i = 0;i<4 ;i++)
		{
			if(CircRectsOverlap(0.00f,.58f-(i*.37f), mGR.mTex_Bar[1].width()*.4f, mGR.mTex_Bar[4].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				mGR.mSel = i+1;
			}
		}
		if(CircRectsOverlap(+.85f,-.90f, mGR.mTex_Back[0].width()*.4f, mGR.mTex_Back[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 13;
		}
		if(event.getAction() == MotionEvent.ACTION_UP && mGR.mSel > 0)
		{
			if(mGR.mSel == 13)
			{
				M.GameScreen = M.GAMESETTING;
			}
			else
			{
				M.GameScreen = M.GAMESETTING;
				mGR.mGameNo = mGR.mSel - 1;
				mGR.mSel = 0;
			}
		}
		return true;
	}
	
	
	
	
	
	
	
	void DrawTexture(GL10 gl,SimplePlane Tex,float x,float y)
	{
		Tex.drawPos(gl, x, y);
	}
	void DrawTextureR(GL10 gl,SimplePlane Tex,float x,float y,float angle)
	{
		Tex.drawRotet(gl, angle, x, y);
	}
	void DrawTextureS(GL10 gl,SimplePlane Tex,float x,float y,float Scalx,float Scaly)
	{
		Tex.drawScal(gl, x, y, Scalx, Scaly);
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
	
}
