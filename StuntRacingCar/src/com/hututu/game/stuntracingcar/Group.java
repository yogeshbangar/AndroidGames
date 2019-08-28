package com.hututu.game.stuntracingcar;
import javax.microedition.khronos.opengles.GL10;


import android.content.Intent;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Toast;
public class Group extends Mesh
{
	GameRenderer mGR = null;
	float sx =0,sy = 0;
	float init = .55f;
	int Counter = 0;
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
//		System.out.println(M.GameScreen+"  ~~~~~~~~~~~~~~~      "+sx+"  ~~~~~~~~~~~~       "+sy);
	}
	float zx=1,zvx=.001f;
	@Override
	public void draw(GL10 gl)
	{
		Counter++;
		switch (M.GameScreen) {
		case M.GAMELOGO:
			DrawTextureS(gl, mGR.mTex_Logo, 0, 0,zx);
			if(zx<.95f)
				zvx = 0.001f;
			if(zx>1.05f)
				zvx = -.001f;
			zx+=zvx;
			if(Counter>110)
			{
				M.GameScreen = M.GAMESPLASH;
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
				DrawTexture(gl, mGR.mTex_Pointer,.2f+Counter*(mGR.mTex_Hightbar.width()/100f)-.32f,-.9f);
			}
			break;
			/*AdHouse*/
		case M.GAMESPLASH:
			DrawTexture(gl, mGR.mTex_Splash, 0, 0);
			if(Counter>70)
			{
				M.GameScreen = M.GAMEMENU;
				Counter =0;
			}
			break;
		case M.GAMEMENU:
			DrawMenu(gl);
			break;
		case M.GAMEABOUT:
		case M.GAMEHELP:
		case M.GAMEHS:
			DrawHelpAbout(gl);
			break;
		case M.GAMEPLAY:
		case M.GAMETIMEUP:
			DrawGamePlay(gl);
			break;
		case M.GAMEPAUSE:
			DrawPause(gl);
			break;
		case M.GAMEOVER:
			DrawOver(gl);
			break;
		}
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
				M.GameScreen = M.GAMESPLASH;Counter=0;//AdHouse
			}
			break;
			/*AdHouse*/
		case M.GAMESPLASH:
			break;
		case M.GAMEMENU:
			HandleMenu(event);
			break;
		case M.GAMEABOUT:
		case M.GAMEHELP:
		case M.GAMEHS:
			HandleHelpAbout(event);
			break;
		case M.GAMEPLAY:
			HandleGame(event);
			break;
		case M.GAMEPAUSE:
			HandlePause(event);
			break;
		case M.GAMEOVER:
			HandleOver(event);
			break;

		}
//
//		HandleGame(event);
		return true;
	}



	void DrawPause(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_Splash, 0, 0);
		DrawTexture(gl, mGR.mTex_Popup_menu, 0, -.20f);
		DrawTexture(gl, mGR.mTex_Gamepaused, 0, 0.35f);


		DrawTexture(gl, mGR.mTex_Continue, 0,-.13f-(0*.33f));
		DrawTexture(gl, mGR.mTex_Menu	 , 0,-.13f-(1*.33f));
//		DrawTexture(gl, mGR.mTex_Rateus	 , 0,-.13f-(2*.33f));
		for(int i=0;i<2;i++)
		{
			if(mGR.mSel == i+1)
			{
				DrawTexture(gl, mGR.mTex_Buttonselect, 0,-.13f-(i*.33f));
			}
		}


		DrawTexture(gl, mGR.mTex_More, .72f,-.51f);
		if(mGR.mSel == 6)
			DrawTexture(gl, mGR.mTex_Buttonselect1, .72f,-.51f);


		DrawTexture(gl, mGR.mTex_Share,.72f,-.81f);
		if(mGR.mSel == 7)
			DrawTexture(gl, mGR.mTex_Buttonselect1, .72f,-.81f);
	}

	boolean HandlePause(MotionEvent event)
	{
		mGR.mSel = 0;
		for(int i=0;i<2;i++)
		{
			if(CircRectsOverlap(0,-.13f-(i*.33f), mGR.mTex_MButton[0].width()*.4f, mGR.mTex_MButton[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				mGR.mSel = i+1;
			}
		}
		if(CircRectsOverlap(.72f,-.51f, mGR.mTex_More.width()*.4f, mGR.mTex_More.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 6;
		}
		if(CircRectsOverlap(.72f,-.81f, mGR.mTex_Share.width()*.4f, mGR.mTex_Share.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 7;
		}
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			switch (mGR.mSel) {
			case 1:
				M.GameScreen = M.GAMEPLAY;
				System.out.println("~~~~~~~~~~##~~~~~~~~~~~~     "+mGR.mStime+"    ~~~~~~~~~~#1#~~~~~~~~~~~");
				mGR.mStime  += System.currentTimeMillis();
				System.out.println("~~~~~~~~~~##~~~~~~~~~~~~     "+mGR.mStime+"    ~~~~~~~~~~#2#~~~~~~~~~~~");
				M.play(GameRenderer.mContext, R.drawable.bgsound);
				break;
			case 2:
				M.GameScreen = M.GAMEMENU;
				break;
			case 3:
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(M.Market));
				GameRenderer.mContext.startActivity(intent);
				break;

			case 6:
				Intent intent1 = new Intent(Intent.ACTION_VIEW);
				intent1.setData(Uri.parse(M.Market));
				GameRenderer.mContext.startActivity(intent1);
				break;
			case 7:
				share2friend();
				break;
			}
			mGR.mSel = 0;
		}
		return true;
	}



	void DrawOver(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_Splash, 0, 0);
		DrawTexture(gl, mGR.mTex_Popup_menu, 0, -.20f);
		DrawTexture(gl, mGR.mTex_Gameover , 0, 0.41f);
		DrawTexture(gl, mGR.mTex_Scorefont, -.15f, .21f);
		drawNumber(gl, (int)mGR.mScore	  , -.01f, .21f);

		DrawTexture(gl, mGR.mTex_Retry		, 0,-.13f-(0*.33f));
		DrawTexture(gl, mGR.mTex_Menu	 	, 0,-.13f-(1*.33f));
//		DrawTexture(gl, mGR.mTex_Submitscore, 0,-.13f-(2*.33f));
		for(int i=0;i<2;i++)
		{
			if(mGR.mSel == i+1)
			{
				DrawTexture(gl, mGR.mTex_Buttonselect, 0,-.13f-(i*.33f));
			}
		}


		DrawTexture(gl, mGR.mTex_More, .72f,-.51f);
		if(mGR.mSel == 6)
			DrawTexture(gl, mGR.mTex_Buttonselect1, .72f,-.51f);


		DrawTexture(gl, mGR.mTex_Share,.72f,-.81f);
		if(mGR.mSel == 7)
			DrawTexture(gl, mGR.mTex_Buttonselect1, .72f,-.81f);
	}

	boolean HandleOver(MotionEvent event)
	{
		mGR.mSel = 0;
		for(int i=0;i<2;i++)
		{
			if(CircRectsOverlap(0,-.13f-(i*.33f), mGR.mTex_MButton[0].width()*.4f, mGR.mTex_MButton[0].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				mGR.mSel = i+1;
			}
		}
		if(CircRectsOverlap(.72f,-.51f, mGR.mTex_More.width()*.4f, mGR.mTex_More.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 6;
		}
		if(CircRectsOverlap(.72f,-.81f, mGR.mTex_Share.width()*.4f, mGR.mTex_Share.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 7;
		}
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			switch (mGR.mSel) {
			case 1:
				M.GameScreen = M.GAMEPLAY;
				mGR.gameReset();
				M.play(GameRenderer.mContext, R.drawable.bgsound);
				break;
			case 2:
				M.GameScreen = M.GAMEMENU;
				break;
			case 3:
//				GameRenderer.mStart.SubmitScore();
				break;

			case 6:
				Intent intent1 = new Intent(Intent.ACTION_VIEW);
				intent1.setData(Uri.parse(M.Market));
				GameRenderer.mContext.startActivity(intent1);
				break;
			case 7:
				share2friend();
				break;
			}
			mGR.mSel = 0;
		}
		return true;
	}





	void DrawHelpAbout(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_Splash, 0, 0);
		DrawTexture(gl, mGR.mTex_Popup_menu, 0, 0);
		if(M.GameScreen == M.GAMEHELP)
			DrawTexture(gl, mGR.mTex_Help, 0, 0);
		else if(M.GameScreen == M.GAMEABOUT)
			DrawTexture(gl, mGR.mTex_Aboutusfont, 0, 0);
		else
		{
			DrawTexture(gl, mGR.mTex_HS, 0, 0.1f);
			DrawTexture(gl, mGR.mTex_Scorefont, -.15f,-.11f);
			drawNumber(gl, (int)mGR.mHScore	  , -.01f,-.11f);
		}
		DrawTexture(gl, mGR.mTex_Backde, .9f, -.85f);
		if(mGR.mSel == 1)
			DrawTexture(gl, mGR.mTex_Backse, .9f, -.85f);
	}

	boolean HandleHelpAbout(MotionEvent event)
	{

		if(CircRectsOverlap(.9f, -.85f, mGR.mTex_Backse.width()*.4f, mGR.mTex_Backse.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 1;
		}
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			switch (mGR.mSel) {
			case 1:
				M.GameScreen = M.GAMEMENU;
				break;
			}
			mGR.mSel = 0;
		}
		return true;
	}

	void DrawMenu(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_Splash, 0, 0);
		DrawTexture(gl, mGR.mTex_Menu_popup, -.6f, -.1f);
		for(int i=0;i<mGR.mTex_MButton.length;i++)
		{
			DrawTexture(gl, mGR.mTex_MButton[i], -.6f, .52f-(i*.33f));
			if(mGR.mSel == i+1)
			{
				DrawTexture(gl, mGR.mTex_Buttonselect, -.6f, .52f-(i*.33f));
			}
		}
		DrawTexture(gl, mGR.mTex_More, .72f,-.51f);
		if(mGR.mSel == 6)
			DrawTexture(gl, mGR.mTex_Buttonselect1, .72f,-.51f);


		DrawTexture(gl, mGR.mTex_Share,.72f,-.81f);
		if(mGR.mSel == 7)
			DrawTexture(gl, mGR.mTex_Buttonselect1, .72f,-.81f);
	}



	boolean HandleMenu(MotionEvent event)
	{
		mGR.mSel = 0;
		for(int i=0;i<mGR.mTex_MButton.length;i++)
		{
			if(CircRectsOverlap(-.6f, .52f-(i*.33f), mGR.mTex_MButton[i].width()*.4f, mGR.mTex_MButton[i].Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				mGR.mSel = i+1;
			}
		}
		if(CircRectsOverlap(.72f,-.51f, mGR.mTex_More.width()*.4f, mGR.mTex_More.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 6;
		}
		if(CircRectsOverlap(.72f,-.81f, mGR.mTex_Share.width()*.4f, mGR.mTex_Share.Height()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 7;
		}
		if(event.getAction() == MotionEvent.ACTION_UP)
		{
			switch (mGR.mSel) {
			case 1:
				mGR.gameReset();
				M.GameScreen = M.GAMEPLAY;
				M.play(GameRenderer.mContext, R.drawable.bgsound);
				break;
			case 2:
				M.GameScreen = M.GAMEHELP;
				break;
			case 3:
				M.GameScreen = M.GAMEABOUT;
				break;
			case 4:
				M.GameScreen = M.GAMEHS;
//				GameRenderer.mStart.CallLeaderBord();
				break;
			case 5:
				GameRenderer.mStart.get();
				break;
			case 6:
				Intent intent1 = new Intent(Intent.ACTION_VIEW);
				intent1.setData(Uri.parse(M.Market));
				GameRenderer.mContext.startActivity(intent1);
				break;
			case 7:
				share2friend();
				break;
			}
			mGR.mSel = 0;
		}
		return true;
	}
















	int findBig()
	{
		int temp = 0;
		for(int i=0;i<mGR.mStrip.length;i++)
		{
			if(mGR.mStrip[i].y > mGR.mStrip[temp].y)
			{
				temp = i;
			}
		}
		if(mGR.mStrip[temp].y < init-.001f)
		{
			for(int i=0;i<mGR.mStrip.length;i++)
			{
				if(mGR.mStrip[i].y <= -2)
				{
					mGR.mStrip[i].Set( init, .1f,0, 0);
					break;
				}
			}
		}
		return temp;
	}


	int findBigCar()
	{
		int temp = 0;
		for(int i=0;i<mGR.mCar.length;i++)
		{
			if(mGR.mCar[i].y > mGR.mCar[temp].y)
			{
				temp = i;
			}
		}
		if(mGR.mCar[temp].y < init-.003f)
		{
			for(int i=0;i<mGR.mCar.length;i++)
			{
				if(mGR.mCar[i].y <= -2)
				{
					if(mGR.Carcounter < 2)
					{
						mGR.mCar[i].Set( 0,init, .01f,0,0, 0,0,0,0);
					}
					else if(mGR.mLevel < 4)
					{
						mGR.mCar[i].Set( 0,init, .01f,0,0, 0,M.mRand.nextInt(2),M.mRand.nextInt(2),M.mRand.nextInt(2));
					}
					else if(mGR.mLevel < 5)
					{
						mGR.mCar[i].Set( 0,init, .01f,0,0, 0,M.mRand.nextInt(3),M.mRand.nextInt(3),M.mRand.nextInt(3));
					}
					else if(mGR.mLevel < 8)
					{
						mGR.mCar[i].Set( 0,init, .01f,0,0, 0,M.mRand.nextInt(5),M.mRand.nextInt(5),M.mRand.nextInt(5));
					}
					else
					{
						mGR.mCar[i].Set( 0,init, .01f,0,0, 0,M.mRand.nextInt(7),M.mRand.nextInt(7),M.mRand.nextInt(7));
					}
 					mGR.Carcounter--;
					if(mGR.Carcounter <= 0)
						mGR.Carcounter = 8;
					break;

				}
			}
		}
		return temp;
	}



	void gameLogic()
	{
		if(mGR.mScore>mGR.mHScore)
			mGR.mHScore = mGR.mScore;
		int i=0;
		if(M.speed>1)
			mGR.mDistance += (M.speed-1)*100;
		if(mGR.mTargetDistance - mGR.mDistance <= 0)
		{
			mGR.setlevel();
		}
		if(mGR.mStime - System.currentTimeMillis() < 0)
		{
			M.GameScreen = M.GAMETIMEUP;
			mGR.mTimeUp = 30;
			M.speed=1;
		}
		for(i=0;i<mGR.mStrip.length;i++)
		{
			if(mGR.mStrip[i].y > -2)
				mGR.mStrip[i].update();
		}
		for(i=0;i<mGR.mCar.length;i++)
		{
			if(mGR.mCar[i].y > -2)
				mGR.mCar[i].update();
		}
		if(!mGR.mPlayer.isOnair)
		{
			for(i=0;i<mGR.mCar.length;i++)
			{
				if(mGR.mCar[i].cars[0] != 0)//Center
				{
					if(CirCir(0, mGR.mCar[i].y, .1f, mGR.mPlayer.x, mGR.mPlayer.y, .1f))
					{
						mGR.mPlayer.collide = 20;
						M.speed = 1;
						M.sound1(GameRenderer.mContext, R.drawable.collision);
					}
				}
				if(mGR.mCar[i].cars[1] != 0)//Left
				{
					if(CirCir(-mGR.mCar[i].x, mGR.mCar[i].y, .1f, mGR.mPlayer.x, mGR.mPlayer.y, .1f))
					{
						mGR.mPlayer.collide = 20;
						M.speed = 1;
						M.sound1(GameRenderer.mContext, R.drawable.collision);
					}
				}
				if(mGR.mCar[i].cars[2] != 0)//Right
				{
					if(CirCir(mGR.mCar[i].x, mGR.mCar[i].y, .1f, mGR.mPlayer.x, mGR.mPlayer.y, .1f))
					{
						mGR.mPlayer.collide = 20;
						M.speed = 1;
						M.sound1(GameRenderer.mContext, R.drawable.collision);
					}
				}
			}
		}
		findBig();
		findBigCar();
		mGR.mPlayer.Uodate();
//		Log.d("~~~~~~~ ","~~~~~~~~~~~ "+M.speed);
	}
	void DrawGamePlay(GL10 gl)
	{
		int i=0;
		DrawTexture(gl, mGR.mTex_Bg[0], 0, 0);
		for(i=0;i<mGR.mStrip.length;i++)
		{
			if(mGR.mStrip[i].y>-1.5)
				DrawRoS(gl, mGR.mTex_Whitepart,  0, mGR.mStrip[i].y,mGR.mStrip[i].z, 0);
//			Log.d(i+"[y = "+ mGR.mStrip[i].y+"][z = "+mGR.mStrip[i].z+" ] ","[vy = "+ mGR.mStrip[i].vy+"][vz = "+mGR.mStrip[i].vz+" ] ");
		}

		DrawTexture(gl, mGR.mTex_Bg[1], 0, 0);
		DrawTexture(gl, mGR.mTex_Bg[2], 0, 0);
		for(i=0;i<mGR.mStrip.length;i++)
		{
			if(mGR.mStrip[i].y>-1.5)
			{
				if(i%2==0)
					DrawTextureS(gl, mGR.mTex_Cactas	, -mGR.mStrip[i].x,mGR.mStrip[i].y,mGR.mStrip[i].z);
				else
					DrawTextureS(gl, mGR.mTex_Cactas	,  mGR.mStrip[i].x,mGR.mStrip[i].y,mGR.mStrip[i].z);
			}
		}
		DrawTextureS(gl, mGR.mTex_CarShadow	,  mGR.mPlayer.x,mGR.mPlayer.initY,.6f);
		for(i=0;i<mGR.mCar.length;i++)
		{
			if(mGR.mCar[i].y>-1.5)
			{
				if(mGR.mCar[i].cars[0]!=0)
				{
					DrawTextureS(gl, mGR.mTex_CarShadow	,  0, mGR.mCar[i].y,mGR.mCar[i].z);
					DrawTextureS(gl, mGR.mTex_Car[mGR.mCar[i].cars[0]],  0, mGR.mCar[i].y,mGR.mCar[i].z);
				}
				if(mGR.mCar[i].cars[1]!=0)
				{
					DrawTextureS(gl, mGR.mTex_CarShadow	, -mGR.mCar[i].x, mGR.mCar[i].y,mGR.mCar[i].z);
					DrawTextureS(gl, mGR.mTex_Car[mGR.mCar[i].cars[1]], -mGR.mCar[i].x, mGR.mCar[i].y,mGR.mCar[i].z);
				}
				if(mGR.mCar[i].cars[2]!=0)
				{
					DrawTextureS(gl, mGR.mTex_CarShadow	, mGR.mCar[i].x, mGR.mCar[i].y,mGR.mCar[i].z);
					DrawTextureS(gl, mGR.mTex_Car[mGR.mCar[i].cars[2]],  mGR.mCar[i].x, mGR.mCar[i].y,mGR.mCar[i].z);
				}
//				if(i==0)
//					Log.d(i+"[y = "+ (mGR.mCar[i].y)+"][z = "+(mGR.mCar[i].z)+" ] ","[vy = "+ mGR.mCar[i].vy+"][vz = "+mGR.mCar[i].vz+" ] ");
			}
		}
		if(mGR.mPlayer.collide>0)
			DrawTextureS(gl, mGR.mTex_Spark	,  mGR.mPlayer.x,mGR.mPlayer.y+.1f	,.6f);
		if(mGR.mPlayer.vx > 0)
			DrawRoS2(gl, mGR.mTex_Car[0]	,  mGR.mPlayer.x,mGR.mPlayer.y		,.6f,-25);
		else if(mGR.mPlayer.vx < 0)
			DrawRoS2(gl, mGR.mTex_Car[0]	,  mGR.mPlayer.x,mGR.mPlayer.y		,.6f,025);
		else{
			if(mGR.mPlayer.collide > -10)
			{
				if(Counter%2==0)
					mGR.mTex_Car[0].drawTransprentScal(gl, mGR.mPlayer.x,mGR.mPlayer.y		,.6f, .5f);
			}
			else
				DrawTextureS(gl, mGR.mTex_Car[0],  mGR.mPlayer.x,mGR.mPlayer.y		,.6f);
		}

		DrawTexture(gl, mGR.mTex_Jump	, -.80f,   -.73f);
		DrawTexture(gl, mGR.mTex_LR_key	, 0.76f, -.80f);
//		DrawTexture(gl, mGR.mTex_Jump	, sx, sy);

		if(M.GameScreen == M.GAMEPLAY){
			DrawTexture(gl, mGR.mTex_Distancefont, -.83f, .63f);
			drawNumber(gl, (int)(mGR.mTargetDistance - mGR.mDistance), -.64f, .63f);

			DrawTexture(gl, mGR.mTex_Timefont, -.89f, .45f);
			drawNumber(gl, (int)(mGR.mStime - System.currentTimeMillis())/1000,-.73f, .45f);

			DrawTexture(gl, mGR.mTex_Scorefont, -.87f, .27f);
			drawNumber(gl, (int)mGR.mScore	  , -.73f, .27f);
		}

		DrawTexture(gl, mGR.mTex_Pausede, 0.87f, .69f);
		DrawTexture(gl, mGR.mTex_Soundde, 0.87f, .31f);
		if(!M.setValue)
			DrawTexture(gl, mGR.mTex_Soundoff, 0.87f, .21f);

		if(M.GameScreen == M.GAMETIMEUP)
		{
			DrawTexture(gl, mGR.mTex_Timeup	, 0, 0);
			mGR.mTimeUp --;
			if(mGR.mTimeUp<0)
			{
				M.stop(GameRenderer.mContext);
				M.GameScreen = M.GAMEOVER;
				GameRenderer.mStart.ShowInterstitial();
			}
		}
		if(mGR.mLevelUpCounter>0)
		{
			DrawTexture(gl, mGR.mTex_Levelfont, -.1f, 0);
			drawNumber(gl, mGR.mLevel	 , .1f, 0);
			mGR.mLevelUpCounter --;
		}
		if(M.GameScreen == M.GAMEPLAY)
			gameLogic();
//		Log.d(mGR.mPlayer.isOnair+"     "+mGR.mPlayer.vy,"  "+mGR.mPlayer.y+"--------");
//		System.out.println("yogesh");
	}


	boolean HandleGame(MotionEvent event)
	{
		int action = event.getAction();
		/*int actioncode = action & MotionEvent.ACTION_MASK;
		if(actioncode==MotionEvent.ACTION_POINTER_DOWN && event.getPointerCount()>1)
		{
			for (int i = 0; i < event.getPointerCount(); i++) {
				if(CirCir(-.86f, -.79f, .1f, screen2worldX(event.getX(i)), screen2worldY(event.getY(i)), .1f))
				{
					if(!mGR.mPlayer.isOnair)
					{
						mGR.mPlayer.vy = .23f;
						mGR.mPlayer.y  +=mGR.mPlayer.vy;
						mGR.mPlayer.isOnair = true;
						M.speed = 1.11f;
					}
				}
				if(CirCir(0.6f, -.80f, .1f, screen2worldX(event.getX(i)), screen2worldY(event.getY(i)), .05f))
				{
					mGR.mPlayer.vx =-.1f;
					if(mGR.mPlayer.x>.4f)
						mGR.mPlayer.pos = 1;
					else
						mGR.mPlayer.pos = 0;
				}
				if(CirCir(0.9f, -.80f, .1f, screen2worldX(event.getX(i)), screen2worldY(event.getY(i)), .05f))
				{
					mGR.mPlayer.vx =+.1f;
					if(mGR.mPlayer.x<-.4f)
						mGR.mPlayer.pos = 1;
					else
						mGR.mPlayer.pos = 2;
				}
			}
		}
		else */
		if(action==MotionEvent.ACTION_DOWN && mGR.mPlayer.collide < -10)
		{
			if(CirCir(-.80f, -.79f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))
			{
				if(!mGR.mPlayer.isOnair)
				{
					mGR.mPlayer.vy = .23f;
					mGR.mPlayer.y  +=mGR.mPlayer.vy;
					mGR.mPlayer.isOnair = true;
					M.speed = 1.11f;
				}
			}
			if(CirCir(0.6f, -.80f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				mGR.mPlayer.vx =-.1f;
				if(mGR.mPlayer.x>.4f)
					mGR.mPlayer.pos = 1;
				else
					mGR.mPlayer.pos = 0;
				M.speed = 1.11f;
			}
			if(CirCir(0.9f, -.80f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
//				 GameRenderer.mStart.CallLeaderBord();
				mGR.mPlayer.vx =+.1f;
				if(mGR.mPlayer.x<-.4f)
					mGR.mPlayer.pos = 1;
				else
					mGR.mPlayer.pos = 2;
				M.speed = 1.11f;
			}
			if(CirCir(0.87f, .69f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				M.GameScreen = M.GAMEPAUSE;
				mGR.mStime  -= System.currentTimeMillis();
				M.stop(GameRenderer.mContext);
			}
			if(CirCir(0.87f, .31f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				M.setValue = !M.setValue;
				if(!M.setValue)
					M.stop(GameRenderer.mContext);
				else
					M.play(GameRenderer.mContext, R.drawable.bgsound);
			}
		}
		return true;
	}




	/** Show an event in the LogCat view, for debugging */
	@SuppressWarnings("deprecation")
	public void dumpEvent(MotionEvent event)
	{
			String names[] = { "down" , "up" , "move" , "cancel" , "outside" ,"pointer_down" , "pointer_up" , "7?" , "8?" , "9?" };
			StringBuilder sb = new StringBuilder();
			int action = event.getAction();
			int actioncode = action & MotionEvent.ACTION_MASK;
			sb.append("event action_" ).append(names[actioncode]);
			if (actioncode == MotionEvent.ACTION_POINTER_DOWN  || actioncode == MotionEvent.ACTION_POINTER_UP) {
				sb.append("(pid " ).append(action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
				sb.append(")" );
			}
			sb.append("[" );
			for (int i = 0; i < event.getPointerCount(); i++) {
				sb.append("#" ).append(i);
				sb.append("(pid " ).append(event.getPointerId(i));
				sb.append(")=" ).append((int) event.getX(i));
				sb.append("," ).append((int) event.getY(i));
				if (i + 1 < event.getPointerCount())
					sb.append(";" );
			}
			sb.append("]" );
			//Log.d("tag===========", sb.toString());
			System.out.println(sb.toString());
	}









//	public boolean onTouch(MotionEvent event) {
//		int action = event.getAction() & MotionEvent.ACTION_MASK;
//		int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
//		int pointerCount = event.getPointerCount();
//
//		int actionId = event.getPointerId(pointerIndex);
////		Log.d("greenrobot", "MotionEvent - pointer ID:  " + actionId+ ", action: " + mapActionCodeToString(action)+ ", pointer count: " + pointerCount);
//		if (actionId < MAX_POINTERS) {
//			lastActions[actionId] = action;
//		}
//
//		for (int i = 0; i < pointerCount; i++) {
//			int pointerId = event.getPointerId(i);
//			if (pointerId < MAX_POINTERS) {
//				points[pointerId] = new PointF(event.getX(i), event.getY(i));
//				if (action == MotionEvent.ACTION_MOVE) {
//					lastActions[pointerId] = action;
//				}
//			}
//		}
//
//		touchView.invalidate();
//		return true;
//	}






































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
	void DrawRoS(GL10 gl,SimplePlane Tex,float x,float y,float scal,float rotate)
	{
		Tex.drawRS(gl, x, y,scal,rotate);
	}
	void DrawRoS2(GL10 gl,SimplePlane Tex,float x,float y,float scal,float rotate)
	{
		Tex.drawRS(gl, x, y,scal,rotate);
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
	void share2friend()
	{
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("text/plain");
		i.putExtra(Intent.EXTRA_SUBJECT, "Stunt Racing Car");
		i.putExtra(Intent.EXTRA_TEXT   , "It is a new brand and distinctive racing game! Install the best free application  https://play.google.com/store/apps/details?id="+getClass().getPackage().getName());
		try {
		    GameRenderer.mContext.startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(GameRenderer.mStart, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
	void drawNumber(GL10 gl,int no,float x, float y)
	{
		float dx = mGR.mTex_Font[0].width();
		 String strs = ""+no;
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i))-48;
			if(k>=0 && k<mGR.mTex_Font.length)
				mGR.mTex_Font[k].drawPos(gl,x+i*dx,y);
		}
	}
}
