package com.hututu.game.papershot;
import javax.microedition.khronos.opengles.GL10;


import android.content.Intent;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.MotionEvent;
public class Group extends Mesh 
{
	
	GameRenderer mGR = null;
	float sx,sy;
	float dx,dy;
	int Counter =0;
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
//		System.out.println(M.GameScreen+" ~~~~~~~~~~~~~~~      "+sx+"  ~~~~~~~~~~~~       "+sy);
	}
	@Override
	public void draw(GL10 gl) 
	{
		Counter++;
//		M.GameScreen = M.GAMEMENU;
		switch (M.GameScreen) {
		case M.GAMELOGO:
			DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if(Counter>50){
//				M.GameScreen = M.GAMEMENU;
				M.GameScreen = M.GAMEADD;//AdHouse
				Counter=0;//AdHouse
			}
			break;
			/*AdHouse*/
		case M.GAMEADD:
			if(Counter>100)
				DrawTexture(gl,mGR.mTex_Skip, .9f,-.9f);
			else{
				DrawTexture(gl, mGR.mTex_Hightbar,0.2f,-.9f);
				DrawTexture(gl, mGR.mTex_Pointer,.2f+Counter*(mGR.mTex_Hightbar.width()/100f)-.6f,-.9f);
			}
			break;
			/*AdHouse*/
		case M.GAMEMENU:
			Draw_Menu(gl);
			break;
		case M.GAMEBG:
			Draw_BG(gl);
			break;
		case M.GAMEABOUT:
			Draw_About(gl);
			break;
		case M.GAMEHELP:
			break;
		case M.GAMEHIGH:
			Draw_HS(gl);
			break;
		case M.GAMEPAUSE:
			Draw_Pause(gl);
			break;
		case M.GAMEPLAY:
			DrawGamePlay(gl);
			break;
		
		}
//		Draw_Pause(gl);
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
		case M.GAMEBG:
			Handle_BG(event);
			break;
		case M.GAMEABOUT:
			Handle_About(event);
			break;
		case M.GAMEHELP:
			break;
		case M.GAMEHIGH:
			Handle_HS(event);
			break;
		case M.GAMEPAUSE:
			Handle_Pause(event);
			break;
		case M.GAMEPLAY:
			HandleGame(event);
			break;
		}
//		
		return true;
	}
	
	void Draw_Menu(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_Menu, 0, 0);
		
		for(int i=0;i<3;i++)
		{
			if(mGR.mSel == i+1)
				DrawTexture(gl, mGR.mTex_Menu_button_se, .4f, -(i*.25f));
			else
				DrawTexture(gl, mGR.mTex_Menu_button_de, .4f, -(i*.25f));
			if(i==0)
				mGR.mFont.draw(gl, M.EASY, .4f, -(i*.25f), 0, 1);
			if(i==1)
				mGR.mFont.draw(gl, M.MEDIUM, .4f, -(i*.25f), 0, 1);
			if(i==2)
				mGR.mFont.draw(gl, M.HARD, .4f, -(i*.25f), 0, 1);
		}
		DrawTexture(gl, mGR.mTex_Soundon, .8f, .7f);
		if(!M.setValue)
			DrawTexture(gl, mGR.mTex_soundoff, .8f, .7f);
//		DrawTexture(gl, mGR.mTex_Star, -.38f, -.16f);//AboutUs
//		DrawTexture(gl, mGR.mTex_Star, -.81f, -.24f);//Hight Score
//		DrawTexture(gl, mGR.mTex_Star, -.49f, -.72f);//Exit
	}
	
	
	boolean Handle_Menu(MotionEvent event)
	{
		mGR.mSel = 0;
		for(int i=0;i<3;i++)
		{
			if(CircRectsOverlap(.4f, -(i*.25f), mGR.mTex_Menu_button_de.width()*.4f, mGR.mTex_Menu_button_de.Height()*.4f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				mGR.mSel = i+1;
			}
		}
		if(CircRectsOverlap(.8f, .7f, mGR.mTex_Soundon.width()*.4f, mGR.mTex_Soundon.Height()*.4f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			//Sound
			mGR.mSel = 4;
		}
		if(CircRectsOverlap(-.38f, -.16f, mGR.mTex_Soundon.width()*.4f, mGR.mTex_Soundon.Height()*.4f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			//AboutUs
			mGR.mSel = 5;
		}
		if(CircRectsOverlap(-.81f, -.24f, mGR.mTex_Soundon.width()*.4f, mGR.mTex_Soundon.Height()*.4f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			//HighScore
			mGR.mSel = 6;
		}
		if(CircRectsOverlap(-.49f, -.72f, mGR.mTex_Soundon.width()*.4f, mGR.mTex_Soundon.Height()*.4f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			//Exit
			mGR.mSel = 7;
		}
		
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			switch (mGR.mSel) {
			case 1:case 2:case 3:
				M.GameScreen = M.GAMEBG;
				mGR.mLevel = mGR.mSel-1; 
				mGR.mScore =0;
				break;
			case 4:
				M.setValue = !M.setValue;
				break;
			case 5:
				M.GameScreen = M.GAMEABOUT;
				break;
			case 6:
				M.GameScreen = M.GAMEHIGH;
				break;
			case 7:
				GameRenderer.mStart.get();
				break;
			}
			mGR.isFromMenu = true;
			mGR.mSel =0;
		}
		
		return true;
	}
	
	
	
	void Draw_BG(GL10 gl)
	{
		int bg =0;
		DrawTexture(gl, mGR.mTex_Menu, 0, 0);
		
		if(mGR.mLevel == 0)
			bg =5;
		else
			bg = 4;
		for(int i=0;i<bg;i++)
		{
			if(mGR.mSel == i+1)
				DrawTexture(gl, mGR.mTex_Menu_button_se, .4f, .1f-(i*.23f));
			else
				DrawTexture(gl, mGR.mTex_Menu_button_de, .4f, .1f-(i*.23f));
			
			switch (mGR.mLevel) {
			case 0:
				if(i == 0)
					mGR.mFont.draw(gl, M.HALL, .4f, .1f-(i*.23f), 0, 1);
				if(i == 1)
					mGR.mFont.draw(gl, M.OFFICE, .4f, .1f-(i*.23f), 0, 1);
				if(i == 2)
					mGR.mFont.draw(gl, M.RESTROOM, .4f, .1f-(i*.23f), 0, 1);
				if(i == 3)
					mGR.mFont.draw(gl, M.DRAWING, .4f, .1f-(i*.23f), 0, 1);
				if(i == 4)
					mGR.mFont.draw(gl, M.KITCHEN, .4f, .1f-(i*.23f), 0, 1);
				break;
			case 1:
				if(i == 0)
					mGR.mFont.draw(gl, M.HALL, .4f, .1f-(i*.23f), 0, 1);
				if(i == 1)
					mGR.mFont.draw(gl, M.OFFICE, .4f, .1f-(i*.23f), 0, 1);
				if(i == 2)
					mGR.mFont.draw(gl, M.DRAWING, .4f, .1f-(i*.23f), 0, 1);
				if(i == 3)
					mGR.mFont.draw(gl, M.KITCHEN, .4f, .1f-(i*.23f), 0, 1);
				break;
			case 2:
				if(i == 0)
					mGR.mFont.draw(gl, M.HALL, .4f, .1f-(i*.23f), 0, 1);
				if(i == 1)
					mGR.mFont.draw(gl, M.OFFICE, .4f, .1f-(i*.23f), 0, 1);
				if(i == 2)
					mGR.mFont.draw(gl, M.KITCHEN, .4f, .1f-(i*.23f), 0, 1);
				if(i == 3)
					mGR.mFont.draw(gl, M.LOUNGE, .4f, .1f-(i*.23f), 0, 1);
				
				break;
			}
			
			
			
		}
		DrawTexture(gl, mGR.mTex_Soundon, .8f, .7f);
		if(!M.setValue)
			DrawTexture(gl, mGR.mTex_soundoff, .8f, .7f);
//		DrawTexture(gl, mGR.mTex_Star, -.38f, -.16f);//AboutUs
//		DrawTexture(gl, mGR.mTex_Star, -.81f, -.24f);//Hight Score
//		DrawTexture(gl, mGR.mTex_Star, -.49f, -.72f);//Exit
	}
	
	
	boolean Handle_BG(MotionEvent event)
	{
		mGR.mSel = 0;
		int bg;
		if(mGR.mLevel == 0)
			bg =5;
		else
			bg = 4;
		for(int i=0;i<bg;i++)
		{
			if(CircRectsOverlap(.4f, .1f-(i*.23f), mGR.mTex_Menu_button_de.width()*.4f, mGR.mTex_Menu_button_de.Height()*.4f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				mGR.mSel = i+1;
			}
		}
		if(CircRectsOverlap(.8f, .7f, mGR.mTex_Soundon.width()*.4f, mGR.mTex_Soundon.Height()*.4f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			//Sound
			mGR.mSel = 6;
		}
		if(CircRectsOverlap(-.38f, -.16f, mGR.mTex_Soundon.width()*.4f, mGR.mTex_Soundon.Height()*.4f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			//AboutUs
			mGR.mSel = 7;
		}
		if(CircRectsOverlap(-.81f, -.24f, mGR.mTex_Soundon.width()*.4f, mGR.mTex_Soundon.Height()*.4f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			//HighScore
			mGR.mSel = 8;
		}
		if(CircRectsOverlap(-.49f, -.72f, mGR.mTex_Soundon.width()*.4f, mGR.mTex_Soundon.Height()*.4f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			//Exit
			mGR.mSel = 9;
		}
		
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			switch (mGR.mSel) {
			case 1:case 2:case 3:case 4:case 5:
				M.GameScreen = M.GAMEPLAY;
				switch (mGR.mLevel) {
				case 0:
					if(mGR.mSel == 1)
						mGR.mBG = 1;
					if(mGR.mSel == 2)
						mGR.mBG = 5;
					if(mGR.mSel == 3)
						mGR.mBG = 4;
					if(mGR.mSel == 4)
						mGR.mBG = 0;
					if(mGR.mSel == 5)
						mGR.mBG = 2;
					break;
				case 1:
					if(mGR.mSel == 1)
						mGR.mBG = 1;
					if(mGR.mSel == 2)
						mGR.mBG = 5;
					if(mGR.mSel == 3)
						mGR.mBG = 0;
					if(mGR.mSel == 4)
						mGR.mBG = 2;
					break;
				case 2:
					if(mGR.mSel == 1)
						mGR.mBG = 1;
					if(mGR.mSel == 2)
						mGR.mBG = 5;
					if(mGR.mSel == 3)
						mGR.mBG = 2;
					if(mGR.mSel == 4)
						mGR.mBG = 3;
					
					break;
				}
				break;
			case 6:
				M.setValue = !M.setValue;
				break;
			case 7:
				M.GameScreen = M.GAMEABOUT;
				break;
			case 8:
				M.GameScreen = M.GAMEHIGH;
				break;
			case 9:
				GameRenderer.mStart.get();
				break;
			}
			mGR.isFromMenu = false;
			mGR.mSel =0;
		}
		
		return true;
	}
	
	void Draw_About(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_Menu, 0, 0);
		DrawTexture(gl, mGR.mTex_Popup, 0, -.4f);
		
//		mGR.mFont.draw(gl, M.ABOUTUS, -.25f, -.04f, 1,1);
//		mGR.mFont.draw(gl, M.PAPERSHOOT,-.53f, -.19f, 0,1);
//		
//		mGR.mFont.draw(gl, M.VER, -.13f, -.3f, 2,1);
//		mGR.mFont.draw(gl, M.DEVELOPED, sx, sy-.4f, 2,1);
//		mGR.mFont.draw(gl, M.HUTUTU, sx, sy-.5f, 1,1);
		
		
		
		mGR.mFont.draw(gl, M.ABOUTUS	, 0.00f, -.04f, 1,1);
		mGR.mFont.draw(gl, M.PAPERSHOOT	, 0.00f, -.19f, 0,1);
		
		mGR.mFont.draw(gl, M.VER		, 0.00f, -.30f, 2,1);
		mGR.mFont.draw(gl, M.DEVELOPED	, 0.00f, -.40f, 2,1);
		mGR.mFont.draw(gl, M.HUTUTU		, 0.00f, -.50f, 1,1);
		
		
//		DrawTexture(gl, mGR.mTex_Star, 0.7f, -.7f);
	}
	
	boolean Handle_About(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(.0f, -.7f, mGR.mTex_Soundon.width()*.4f, mGR.mTex_Soundon.Height()*.4f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 1;
		}
		if(CircRectsOverlap(0.7f, -.7f, mGR.mTex_Soundon.width()*.4f, mGR.mTex_Soundon.Height()*.4f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			//AboutUs
//			mGR.mSel = 2;
		}
		
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			Intent intent = new Intent(Intent.ACTION_VIEW);
			switch (mGR.mSel) {
			case 1:
				intent.setData(Uri.parse(M.MARKET));
				GameRenderer.mContext.startActivity(intent);
				break;
			case 2:
//				intent.setData(Uri.parse(M.LINK+getClass().getPackage().getName()));
				intent.setData(Uri.parse(M.MARKET));
				GameRenderer.mContext.startActivity(intent);
				break;
			}
			mGR.mSel =0;
		}
		return true;
	}
	
	void Draw_Pause(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_Menu, 0, 0);
		DrawTexture(gl, mGR.mTex_Popup, 0, -.4f);
		
		mGR.mFont.draw(gl, M.PAUSE, .0f, -.05f, 1, 1);
		
		
		mGR.mFont.draw(gl, M.MAINMANU	, .0f,-.20f, 0, 1);
		mGR.mFont.draw(gl, M.RESUME		, .0f,-.35f, 0, 1);
		mGR.mFont.draw(gl, M.RETRY		, .0f,-.50f, 0, 1);
		
//		DrawTexture(gl, mGR.mTex_Star, 0.7f, -.7f);//Exit
		DrawTexture(gl, mGR.mTex_Smalsoundon, -.7f, -.7f);//Exit
		if(!M.setValue)
			DrawTexture(gl, mGR.mTex_soundoff,-.7f,-.7f);
	}
	
	boolean Handle_Pause(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(.0f,-.20f, mGR.mTex_Soundon.width()*.99f, mGR.mTex_Soundon.Height()*.3f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 1;
		}
		if(CircRectsOverlap(.0f,-.35f, mGR.mTex_Soundon.width()*.99f, mGR.mTex_Soundon.Height()*.3f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			//AboutUs
			mGR.mSel = 2;
		}
		if(CircRectsOverlap(.0f,-.50f, mGR.mTex_Soundon.width()*.99f, mGR.mTex_Soundon.Height()*.3f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			//AboutUs
			mGR.mSel = 3;
		}
		if(CircRectsOverlap( 0.7f, -.7f, mGR.mTex_Soundon.width()*.4f, mGR.mTex_Soundon.Height()*.4f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			//Star
//			mGR.mSel = 4;
		}
		if(CircRectsOverlap(-.7f,-.7f, mGR.mTex_Soundon.width()*.4f, mGR.mTex_Soundon.Height()*.4f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			//Sound
			mGR.mSel = 5;
		}
		if(CircRectsOverlap( 0.0f, -.7f, mGR.mTex_Soundon.width()*.4f, mGR.mTex_Soundon.Height()*.4f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			//Hututu
			mGR.mSel = 6;
		}
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			Intent intent = new Intent(Intent.ACTION_VIEW);
			switch (mGR.mSel) {
			case 1:
				M.GameScreen = M.GAMEMENU;
				break;
			case 2:
				M.GameScreen = M.GAMEPLAY;
				break;
			case 3:
				M.GameScreen = M.GAMEPLAY;
				mGR.mScore = 0;
				break;
			case 4:
//				intent.setData(Uri.parse(M.LINK+getClass().getPackage().getName()));
				intent.setData(Uri.parse(M.MARKET));
				GameRenderer.mContext.startActivity(intent);
				break;
			case 5:
				M.setValue = !M.setValue;
				break;
			case 6:
				intent.setData(Uri.parse(M.MARKET));
				GameRenderer.mContext.startActivity(intent);
				break;
			}
			mGR.mSel =0;
		}
		return true;
	}
	
	
	
	void Draw_HS(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_Menu, 0, 0);
		DrawTexture(gl, mGR.mTex_Popup, 0, -.4f);
		
		mGR.mFont.draw(gl, M.HS			, 0.00f, -.04f, 1,1);
		mGR.mFont.draw(gl, M.EASY		, -.70f, -.20f, 0,0);
		mGR.mFont.draw(gl, ":"+mGR.mEScore	, -.00f, -.20f, 0,0);
		
		mGR.mFont.draw(gl, M.MEDIUM		, -.70f, -.35f, 0,0);
		mGR.mFont.draw(gl, ":"+mGR.mMScore, -.00f, -.35f, 0,0);
		
		mGR.mFont.draw(gl, M.HARD		, -.70f, -.50f, 0,0);
		mGR.mFont.draw(gl, ":"+mGR.mHScore, -.00f, -.50f, 0,0);
		
//		mGR.mFont.draw(gl, M.SUBMIT		, -.80f, -.75f, 2,0);
//		mGR.mFont.draw(gl, M.GLOBLE		, 0.23f, -.75f, 2,0);
		
	}
	
	boolean Handle_HS(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(.0f, -.7f, mGR.mTex_Soundon.width()*.4f, mGR.mTex_Soundon.Height()*.4f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 1;
			//Logo
		}
		if(CircRectsOverlap(-.70f, -.75f, mGR.mTex_Soundon.width()*.4f, mGR.mTex_Soundon.Height()*.6f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			//Submit
			mGR.mSel = 2;
		}
		if(CircRectsOverlap(0.7f, -.75f, mGR.mTex_Soundon.width()*.4f, mGR.mTex_Soundon.Height()*.6f,screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			//Back
			mGR.mSel = 3;
		}
		
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			Intent intent = new Intent(Intent.ACTION_VIEW);
			switch (mGR.mSel) {
			case 1:
				intent.setData(Uri.parse(M.MARKET));
				GameRenderer.mContext.startActivity(intent);
				break;
			case 2:
//				GameRenderer.mStart.SubmitScore();
				break;
			case 3:
//				M.GameScreen = M.GAMEHIGH;
//				GameRenderer.mStart.CallLeaderBord();
				break;
			}
			mGR.mSel =0;
		}
		return true;
	}
	
	
	
	
	void Gamelogic()
	{
		mGR.mStone.update();
		if(CirCir(-mGR.mPos[mGR.mLevel].x, mGR.mPos[mGR.mLevel].y, .02, mGR.mStone.x, mGR.mStone.y, .02f)&& mGR.mStone.vy<0)
		{
			if(mGR.mStone.vy<0)
			{
				if(mGR.mBG == 3)
					M.sound3(GameRenderer.mContext, R.drawable.steel);
				else
					M.sound2(GameRenderer.mContext, R.drawable.plastic);
			}
			mGR.mStone.vy = Math.abs(mGR.mStone.vy*.5f);
			mGR.mStone.tuch = true;
			if(mGR.mStone.x > -mGR.mPos[mGR.mLevel].x)
				mGR.mStone.vx = Math.abs(mGR.mStone.vx)*0.5f;
			else
				mGR.mStone.vx = -Math.abs(mGR.mStone.vx)*0.5f;
//			System.out.println("[x = "+mGR.mStone.x+"]~~0~~[y = "+mGR.mStone.y+"]~~~~[z = "+mGR.mStone.z+"] [vx = "+
//					mGR.mStone.vx+"] [vy = "+mGR.mStone.vy+"]  [vz = "+mGR.mStone.vz+"][Air = "+M.mAir+"]");
			
			if(Math.abs(mGR.mStone.vx)<.001)
				mGR.mStone.vx = .002f;
			
//			System.out.println("[x = "+mGR.mStone.x+"]~~~~[y = "+mGR.mStone.y+"]~~~~[z = "+mGR.mStone.z+"] [vx = "+
//					mGR.mStone.vx+"] [vy = "+mGR.mStone.vy+"]  [vz = "+mGR.mStone.vz+"][Air = "+M.mAir+"]");
//			
			
		}
		if(CirCir(mGR.mPos[mGR.mLevel].x, mGR.mPos[mGR.mLevel].y, .02, mGR.mStone.x, mGR.mStone.y, .02f)&& mGR.mStone.vy<0)
		{
			if(mGR.mStone.vy<0)
			{
				if(mGR.mBG == 3)
					M.sound3(GameRenderer.mContext, R.drawable.steel);
				else
					M.sound2(GameRenderer.mContext, R.drawable.plastic);
			}
			mGR.mStone.vy = Math.abs(mGR.mStone.vy*.5f);
			mGR.mStone.tuch = true;
			if(mGR.mStone.x > mGR.mPos[mGR.mLevel].x)
				mGR.mStone.vx = Math.abs(mGR.mStone.vx)*0.5f;
			else
				mGR.mStone.vx = -Math.abs(mGR.mStone.vx)*0.5f;
//			System.out.println("[x = "+mGR.mStone.x+"]~0~~~[y = "+mGR.mStone.y+"]~~~~[z = "+mGR.mStone.z+"] [vx = "+
//					mGR.mStone.vx+"] [vy = "+mGR.mStone.vy+"]  [vz = "+mGR.mStone.vz+"][Air = "+M.mAir+"]");
			if(Math.abs(mGR.mStone.vx)<.001)
				mGR.mStone.vx = Math.abs(mGR.mStone.vx)*2.5f;
//			System.out.println("[x = "+mGR.mStone.x+"]~~~~[y = "+mGR.mStone.y+"]~~~~[z = "+mGR.mStone.z+"] [vx = "+
//					mGR.mStone.vx+"] [vy = "+mGR.mStone.vy+"]  [vz = "+mGR.mStone.vz+"][Air = "+M.mAir+"]");
			
		}
		if(CircRectsOverlap(0.0f, mGR.mPos[mGR.mLevel].y, .2f,0.03f, mGR.mStone.x, mGR.mStone.y, .03f)&& mGR.mStone.vy<0)
		{
			if(!mGR.mStone.inside)
			{
				mGR.mScore ++;
				if(mGR.mBG == 3)
					M.sound3(GameRenderer.mContext, R.drawable.steel);
				else
					M.sound2(GameRenderer.mContext, R.drawable.plastic);
			}
			mGR.mStone.inside = true;
			
		}
		if(mGR.mStone.inside)
		{
			if(mGR.mStone.x>0.05)
			{
				if(mGR.mStone.x >.02f)
					mGR.mStone.x = .02f;
				mGR.mStone.vx =0;
			}
			if(mGR.mStone.x<-0.05)
			{
				if(mGR.mStone.x<-0.02)
					mGR.mStone.x =-.02f;
				mGR.mStone.vx = 0;
			}
		}
		if(mGR.mStone.inside && mGR.mLevel == 2)
		{
//			if(mGR.mStone.y<mGR.mPos[2].c)
			if(mGR.mStone.x>0.02)
			{
				mGR.mStone.x = .0f;
				mGR.mStone.vx =0;
			}
			if(mGR.mStone.x<-0.02)
			{
				mGR.mStone.x =0.0f;
				mGR.mStone.vx = 0;
			}
		}
		if(mGR.mStone.counter==9 && !mGR.mStone.inside)
		{
			M.sound1(GameRenderer.mContext, R.drawable.foot);
			mGR.mScore = 0;
		}
		
		if(mGR.mLevel == 0 && mGR.mScore > mGR.mEScore)
			mGR.mEScore = mGR.mScore;
		if(mGR.mLevel == 1 && mGR.mScore > mGR.mMScore)
			mGR.mMScore = mGR.mScore;
		if(mGR.mLevel == 2 && mGR.mScore > mGR.mHScore)
			mGR.mHScore = mGR.mScore;
	}
	void DrawGamePlay(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_BackGround[mGR.mBG], 0, 0.00f);
		if(mGR.mBG ==5)
			DrawTexture(gl, mGR.mTex_FBulti[1][mGR.mLevel], 0, mGR.mPos[mGR.mLevel].c);
		else
			DrawTexture(gl, mGR.mTex_FBulti[mGR.mBG][mGR.mLevel], 0, mGR.mPos[mGR.mLevel].c);
		if(mGR.mStone.vy <=0)
		{
			if(mGR.mStone.counter <1)
				DrawTextureS(gl, mGR.mTex_Ball[Counter%mGR.mTex_Ball.length], mGR.mStone.x, mGR.mStone.y,mGR.mStone.z);
			else
				DrawTextureS(gl, mGR.mTex_Ball[0], mGR.mStone.x, mGR.mStone.y,mGR.mStone.z);
		}
		if(mGR.mBG ==5)
			DrawTexture(gl, mGR.mTex_BBulti[1][mGR.mLevel], 0, mGR.mPos[mGR.mLevel].c);
		else
			DrawTexture(gl, mGR.mTex_BBulti[mGR.mBG][mGR.mLevel], 0, mGR.mPos[mGR.mLevel].c);
		
		if(mGR.mBG!=1 && mGR.mBG!=2)
			DrawTexture(gl, mGR.mTex_BGFrant[mGR.mBG], 0, 0.00f);
		if(mGR.mStone.vy >0)
			DrawTextureS(gl, mGR.mTex_Ball[Counter%mGR.mTex_Ball.length], mGR.mStone.x, mGR.mStone.y,mGR.mStone.z);
		
		if(mGR.mStone.y < -99)
			DrawTexture(gl, mGR.mTex_Ball[0], 0, -.9f);
		
		
		
//		drawNumber(gl, 1, sx, sy);
//		mGR.mPos[2].c = sy;
		
		if(M.mAir > 0)
			DrawTexture	(gl, mGR.mTex_Fan[Counter%mGR.mTex_Fan.length],-.88f,-.7f);
		else
			DrawFlip	(gl, mGR.mTex_Fan[Counter%mGR.mTex_Fan.length],+.88f,-.7f);
		
		
		
		float inc =-.2f;
		DrawTexture(gl, mGR.mTex_Scoreboard,-.76f, 0.45f+inc);
		mGR.mFont.draw(gl, M.SCORE		, -.97f, 0.60f+inc, 2, 0);
		mGR.mFont.draw(gl, ""+mGR.mScore, -.82f, 0.52f+inc, 2, 1);
		mGR.mFont.draw(gl, M.BEST		, -.94f, 0.40f+inc, 2, 0);
		mGR.mFont.draw(gl, M.SCORE		, -.97f, 0.35f+inc, 2, 0);
		if(mGR.mLevel == 0)
			mGR.mFont.draw(gl, ""+mGR.mEScore,-.82f, 0.28f+inc, 2, 1);
		if(mGR.mLevel == 1)
			mGR.mFont.draw(gl, ""+mGR.mMScore,-.82f, 0.28f+inc, 2, 1);
		if(mGR.mLevel == 2)
			mGR.mFont.draw(gl, ""+mGR.mHScore,-.82f, 0.28f+inc, 2, 1);
		
		
		
		DrawTexture(gl, mGR.mTex_Smalsoundon,0.86f, 0.7f);
		if(!M.setValue)
			DrawTexture(gl, mGR.mTex_soundoff,0.86f, 0.7f);
		DrawTexture(gl, mGR.mTex_Pause		 ,0.86f, 0.5f);
		
		drawNumber(gl, ""+(M.mAir)		,-.1f, -.50f);
		if(M.mAir<0)
			DrawTexture(gl, mGR.mTex_Arrow	,0.0f, -.55f);
		else
			DrawTextureR(gl, mGR.mTex_Arrow	,0.0f, -.55f,180);
		
		Gamelogic();
	}
	
	
	
	boolean HandleGame(MotionEvent event)
	{
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			dx = event.getX();
			dy = event.getY();
			break;
		case MotionEvent.ACTION_UP:
			if(CirCir(0.86f, 0.7f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				M.setValue = !M.setValue;
			}
			else if(CirCir(0.86f, 0.5f, .2f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				M.GameScreen = M.GAMEPAUSE;
			}
			else if(mGR.mStone.y <-99 && (dx !=event.getX() && dy !=event.getY()))
			{
				
				dx =  M.ScreenWidth/2;
				dy =  M.ScreenHieght * .95f;
				double ang = (GetAngle(-(dy-event.getY()),-(dx-event.getX())));
//				float x = screen2worldX(event.getX());
//				float y = .9f+screen2worldY(event.getY());
//				double ang = (GetAngle(y,x));
				//for(int i =0;i<mGR.mStone.length;i++)
				{
					//if(mGR.mStone[i].x<-1 || mGR.mStone[i].x>1 || mGR.mStone[i].y<-1 || mGR.mStone[i].y>1)
					{
//						mGR.throughtC++;
						mGR.mStone.set(0,-.9f, 1, (float)Math.sin(ang)*mGR.mPos[mGR.mLevel].h, -(float)Math.cos(ang)*mGR.mPos[mGR.mLevel].h, -.018f);
//						M.sound6(mGR.mContext, R.drawable.shoot);
						//Log.d("[X = "+mGR.mStone[i].x+"] [Y = " + mGR.mStone[i].y+"]", "[vx = "+mGR.mStone[i].vx+"] [ vy " + mGR.mStone[i].vy+"]~~~~~~~~~~~~~ "+i+" ~~~~"+Math.toDegrees(ang));
//						System.out.println("[x = "+mGR.mStone.x+"]~~~~[y = "+mGR.mStone.y+"]~~~~[z = "+mGR.mStone.z+"] [vx = "+
//								mGR.mStone.vx+"] [vy = "+mGR.mStone.vy+"]  ["+mGR.mStone.vz+"][Air = "+M.mAir+"]");
					}
				}
			}
			break;
		}
		return true;
	}
	
	
	
	
	
	void DrawTexture(GL10 gl,SimplePlane Tex,float x,float y)
	{
		Tex.drawTransprent(gl, x, y);
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
		float dx = mGR.mTex_Font[0].width();
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i))-47;
			if(k == -1)
				mGR.mTex_Font[0].drawPos(gl,x+i*dx,y);
			if(k>=0 && k<mGR.mTex_Font.length)
				mGR.mTex_Font[k].drawPos(gl,x+i*dx,y);
		}
	}
}
