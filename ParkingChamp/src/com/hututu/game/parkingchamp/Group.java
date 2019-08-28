package com.hututu.game.parkingchamp;
import javax.microedition.khronos.opengles.GL10;



import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.widget.Toast;
public class Group extends Mesh 
{
	
	GameRenderer mGR = null;
	
	int Counter =0;
//	int Counter =0;
	float sx,sy;
	float ml = .8f;
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
	
	public void draw(GL10 gl) 
	{
		Counter++;
//		M.GameScreen = M.GAMEOVER;
		switch (M.GameScreen) {
		case M.GAMELOGO:
			DrawTexture(gl, mGR.mTex_Huhutu, 0, 0);
			if(Counter>60){
				M.GameScreen = M.GAMEMENU;
				M.GameScreen = M.GAMEADD;//AdHouse
				Counter=0;//AdHouse
			}
			break;
			/*AdHouse*/
		case M.GAMEADD:
			if(Counter>100)
				DrawTexture(gl,mGR.mTex_Skip, .9f,.9f);
			else{
				DrawTexture(gl, mGR.mTex_Hightbar,0.2f,0.9f);
				DrawTexture(gl, mGR.mTex_Pointer,.2f+Counter*(mGR.mTex_Hightbar.width()/100f)-.32f,0.9f);
			}
			break;
			/*AdHouse*/
		case M.GAMEMENU:
			DrawMenu(gl);
			break;
		case M.GAMEPLAY:
			DrawGamePlay(gl);
			break;
		case M.GAMESETT:
			DrawSetting(gl);
			break;
		case M.GAMEA:
		case M.GAMEHELP:
			DrawAH(gl);
			break;
		case M.GAMELEVEL:
			DrawLevel(gl);
			break;
		case M.GAMEOVER:
		case M.GAMEWIN:
		case M.GAMECONG:
		case M.GAMEPAUSE:
			DrawOverCom(gl);
			break;
		case M.GAMELOADING:
			DrawTextureR(gl, mGR.mTex_Car[0], sx, -.9f, 0);
			DrawTexture(gl, mGR.mTex_CarLight,sx+.133f,-.86f);
			DrawTexture(gl, mGR.mTex_CarLight,sx+.133f,-.94f);
			DrawTexture(gl, mGR.mTex_Loading, 0, -.9f);
			if(Counter == 10)
				mGR.gameReset();
			sx+=.01f;
			if(sx>1)
			{
				M.GameScreen=M.GAMEPLAY;
				M.play(GameRenderer.mContext, R.drawable.bg);
			}
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
					CirCir(0.9f,0.9f, .11f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				M.GameScreen = M.GAMEMENU;
			}
			break;
			/*AdHouse*/
		case M.GAMEMENU:
			HandleMenu(event);
			break;
		case M.GAMEPLAY:
			Handle_GamePlay(event);
			break;
		case M.GAMESETT:
			HandleSetting(event);
			break;
		case M.GAMEA:
		case M.GAMEHELP:
			HandleAH(event);
			break;
		case M.GAMELEVEL:
			HandleLevel(event);
			break;
		case M.GAMEOVER:
		case M.GAMEWIN:
		case M.GAMECONG:
		case M.GAMEPAUSE:
			HandleOverCom(event);
			break;
		}
//		Handle(event);
		return true;
	}
	
	
	void DrawMenu(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_Splash, 0, 0);
		DrawTransScal(gl, mGR.mTex_Setting	, 0.88f, -.4f,mGR.mSel==1?1.5f:1,mGR.mSel==1?.4f:1);
		DrawTransScal(gl, mGR.mTex_PC		, 0.88f, -.8f,mGR.mSel==2?1.5f:1,mGR.mSel==2?.4f:1);
		DrawTransScal(gl, mGR.mTex_Exit		, 0.88f, 0.8f,mGR.mSel==3?1.5f:1,mGR.mSel==3?.4f:1);
		
		DrawTransScal(gl, mGR.mTex_GPluse	, -.88f, -.8f,mGR.mSel==4?1.5f:1,mGR.mSel==4?.4f:1);
		DrawTransScal(gl, mGR.mTex_Facebook	, -.68f, -.8f,mGR.mSel==5?1.5f:1,mGR.mSel==5?.4f:1);
	}
	boolean HandleMenu(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CirCir(0.88f, -.4f, mGR.mTex_Setting.width()/2, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 1;
		}
		if(CirCir(0.88f, -.8f, mGR.mTex_Setting.width()/2, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 2;
		}
		if(CirCir(0.88f,0.8f, mGR.mTex_Setting.width()/2, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 3;
		}
		if(CirCir(-.88f, -.8f, mGR.mTex_Setting.width()/2, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 4;
		}
		if(CirCir(-.68f, -.8f, mGR.mTex_Setting.width()/2, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 5;
		}
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mGR.mSel) {
			case 1:
				M.GameScreen = M.GAMESETT;
				break;
			case 2:
				M.GameScreen = M.GAMELEVEL;
				sy =0;
				break;
			case 3:
				GameRenderer.mStart.Exit();
				break;
			case 4:
				gPluce();
				break;
			case 5:
				facebook();
				break;
			}
			if(mGR.mSel!=0)
				M.sound1(GameRenderer.mContext, R.drawable.button);
			mGR.mSel = 0;
		}
		return true;
	}
	void DrawSetting(GL10 gl)
	{
		DrawRGBS(gl, mGR.mTex_Road		, 0.00f, 0.00f,1,.3f,.3f,.3f);
		DrawRGBS(gl, mGR.mTex_GameBG	, 0.00f, 0.00f,1,.3f,.3f,.3f);
		DrawTexture(gl, mGR.mTex_Board	, 0.00f, 0.00f);
		DrawTexture(gl, mGR.mTex_SetTex	, -.03f, 0.42f);
		
		DrawTransScal(gl, mGR.mTex_Back					, -.88f, 0.8f,mGR.mSel==1?1.5f:1,mGR.mSel==1?.4f:1);
		DrawTransScal(gl, mGR.mTex_Sound[M.setValue?1:0], -.33f, -.1f,mGR.mSel==2?1.5f:1,mGR.mSel==2?.4f:1);
		DrawTransScal(gl, mGR.mTex_Vibrat[M.setVibrator?1:0], -.11f, -.1f,mGR.mSel==3?1.5f:1,mGR.mSel==3?.4f:1);
		DrawTransScal(gl, mGR.mTex_Aboutus				, 0.11f, -.1f,mGR.mSel==4?1.5f:1,mGR.mSel==4?.4f:1);
		DrawTransScal(gl, mGR.mTex_Help					, 0.33f, -.1f,mGR.mSel==5?1.5f:1,mGR.mSel==5?.4f:1);
		
		
	}
	boolean HandleSetting(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CirCir(-.88f, 0.8f, mGR.mTex_Setting.width()/2, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 1;
		}
		if(CirCir(-.33f, -.1f, mGR.mTex_Setting.width()/2, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 2;
		}
		if(CirCir(-.11f, -.1f, mGR.mTex_Setting.width()/2, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 3;
		}
		if(CirCir(0.11f, -.1f, mGR.mTex_Setting.width()/2, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 4;
		}
		if(CirCir(0.33f, -.1f, mGR.mTex_Setting.width()/2, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 5;
		}
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mGR.mSel) {
			case 1:
				M.GameScreen = M.GAMEMENU;
				break;
			case 2:
				M.setValue=!M.setValue;
				break;
			case 3:
				M.setVibrator=!M.setVibrator;
				if(M.setVibrator){
					Vibrator vib = (Vibrator) GameRenderer.mContext.getSystemService(Context.VIBRATOR_SERVICE);  
					vib.vibrate(500);
				}
				break;
			case 4:
				M.GameScreen = M.GAMEA;
				break;
			case 5:
				M.GameScreen = M.GAMEHELP;
				break;
			}
			if(mGR.mSel!=0)
				M.sound1(GameRenderer.mContext, R.drawable.button);
			mGR.mSel = 0;
		}
		
		return true;
	}
	
	void DrawAH(GL10 gl)
	{
		DrawRGBS(gl, mGR.mTex_Road		, 0.00f, 0.00f,1,.3f,.3f,.3f);
		DrawRGBS(gl, mGR.mTex_GameBG	, 0.00f, 0.00f,1,.3f,.3f,.3f);
		if(M.GameScreen == M.GAMEHELP)
			DrawTexture(gl, mGR.mTex_HelpScr	, 0.00f, 0.00f);
		else
		{
			DrawTexture(gl, mGR.mTex_Board	, 0.00f, 0.00f);
			DrawTexture(gl, mGR.mTex_AboutUs_SCR, -.03f, 0.42f);
			DrawTexture(gl, mGR.mTex_About_SCR	, 0.00f, -.13f);
		}
		DrawTransScal(gl, mGR.mTex_Back					, -.88f, 0.8f,mGR.mSel==1?1.5f:1,mGR.mSel==1?.4f:1);
	}
	boolean HandleAH(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CirCir(-.88f, 0.8f, mGR.mTex_Setting.width()/2, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 1;
		}
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mGR.mSel) {
			case 1:
				M.GameScreen = M.GAMESETT;
				break;
			}
			if(mGR.mSel!=0)
				M.sound1(GameRenderer.mContext, R.drawable.button);
			mGR.mSel = 0;
		}
		return true;
	}
	
	
	void DrawLevel(GL10 gl)
	{
		DrawRGBS(gl, mGR.mTex_Road		, 0.00f, 0.00f,1,.3f,.3f,.3f);
		DrawRGBS(gl, mGR.mTex_GameBG	, 0.00f, 0.00f,1,.3f,.3f,.3f);
		for(int i=0;i<6;i++)
		{
			if(sy<=1)
				sy+=.02;
			DrawTransScal(gl, mGR.mTex_Levelbox, -.56f+(i%3)*.56f, .4f-(i/3)*.81f,sy,mGR.mSel==i+1?.4f:1);
			if(sy>=1){
				if(mGR.unLock>i+mGR.mPage*6){
					if(mGR.unStar[i+mGR.mPage*6]>0)
						DrawTexture(gl, mGR.mTex_LevelSStar, -.56f+(i%3)*.56f-(.08f),.4f-(i/3)*.81f-(.16f));
					if(mGR.unStar[i+mGR.mPage*6]>1)
						DrawTexture(gl, mGR.mTex_LevelSStar, -.56f+(i%3)*.56f+(.01f),.4f-(i/3)*.81f-(.18f));
					if(mGR.unStar[i+mGR.mPage*6]>2)
						DrawTexture(gl, mGR.mTex_LevelSStar, -.56f+(i%3)*.56f+(.10f),.4f-(i/3)*.81f-(.16f));
					if((i+1+mGR.mPage*6)>9)
						drawNumberM(gl, (i+1+mGR.mPage*6)+"", -.56f+(i%3)*.56f-.01f, .4f-(i/3)*.81f);
					else
						drawNumberM(gl, (i+1+mGR.mPage*6)+"", -.56f+(i%3)*.56f+.01f, .4f-(i/3)*.81f);
				}
				else
				{
					DrawTexture(gl, mGR.mTex_Levellock, -.56f+(i%3)*.56f+.01f, .4f-(i/3)*.81f);
				}
			}
		}
		if(mGR.mPage > 0)
			mGR.mTex_Level_next.drawTSR(gl, -.85f,0,mGR.mSel==99?1.5f:1,mGR.mSel==99?.4f:1, 180);
		if(mGR.mPage < 2)
			mGR.mTex_Level_next.drawTSR(gl, 0.85f,0,mGR.mSel==100?1.5f:1,mGR.mSel==100?.4f:1, 000);
		DrawTransScal(gl, mGR.mTex_Back		, -.88f, 0.8f,mGR.mSel==101?1.5f:1,mGR.mSel==101?.4f:1);
	}
	boolean HandleLevel(MotionEvent event)
	{
		mGR.mSel = 0;
		for(int i=0;i<6;i++)
		{
			if(CirCir(-.56f+(i%3)*.56f, .4f-(i/3)*.81f, mGR.mTex_Levelbox.width()*.4f, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
			{
				mGR.mSel = i+1;
			}
		}
		
		if(CirCir(-.85f,0, mGR.mTex_Setting.width()/2, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 99;
		}
		if(CirCir(0.85f,0, mGR.mTex_Setting.width()/2, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 100;
		}
		if(CirCir(-.88f, 0.8f, mGR.mTex_Setting.width()/2, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 101;
		}
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mGR.mSel) {
			case 1:case 2:case 3:case 4:case 5:case 6:
				if(mGR.unLock>mGR.mSel-1+mGR.mPage*6)
				{
					mGR.mLevel = mGR.mSel+mGR.mPage*6;
					mGR.loading();
				}
				break;
			case 99:
				if(mGR.mPage>0)
				{
					mGR.mPage--;
					sy =0;
				}
				break;
			case 100:
				if(mGR.mPage<2)
				{
					mGR.mPage++;
					sy =0;
				}
				break;
			case 101:
				M.GameScreen = M.GAMEMENU;
				break;
			}
			if(mGR.mSel!=0)
				M.sound1(GameRenderer.mContext, R.drawable.button);
			mGR.mSel = 0;
		}
		return true;
	}
	/********************Start OverCom**************************/
	void DrawOverCom(GL10 gl)
	{
		DrawRGBS(gl, mGR.mTex_Road		, 0.00f, 0.00f,1,.3f,.3f,.3f);
		DrawRGBS(gl, mGR.mTex_GameBG	, 0.00f, 0.00f,1,.3f,.3f,.3f);
		DrawTexture(gl, mGR.mTex_Board	, 0.00f, 0.00f);
		DrawTransScal(gl, mGR.mTex_Menu		, -.33f,-.38f,mGR.mSel==2?1.5f:1,mGR.mSel==2?.4f:1);
		DrawTransScal(gl, mGR.mTex_Rate_us	, -.11f, -.38f,mGR.mSel==3?1.5f:1,mGR.mSel==3?.4f:1);
		DrawTransScal(gl, mGR.mTex_Retry	, 0.11f, -.38f,mGR.mSel==4?1.5f:1,mGR.mSel==4?.4f:1);
		if(M.GameScreen == M.GAMEWIN || M.GameScreen == M.GAMECONG )
		{
			if(M.GameScreen == M.GAMEWIN){
				DrawTexture(gl, mGR.mTex_LevelComplete	, -.00f, 0.42f);
				DrawTransScal(gl, mGR.mTex_Next	, 0.33f, -.38f,mGR.mSel==5?1.5f:1,mGR.mSel==5?.4f:1);
			}
			else
			{
				DrawTexture(gl, mGR.mTex_Congratulation	, -.03f, 0.42f);
			}
			DrawTexture(gl, mGR.mTex_Starboard	, 0.0f, 0.05f);
			if(mGR.mPlayer.mThukai<3)
			{
				DrawTransScal(gl, mGR.mTex_Levelster	, -.205f, 0.01f,mGR.levelWinS1,1);
				if(mGR.levelWinS1>=1)
				{
					mGR.levelWinS1*=.8f;
					if(mGR.levelWinS1<=1)
					{
						setAnimation2(-.205f, 0.01f);
						mGR.levelWinS1=.98f;
					}
				}
			}
			if(mGR.mPlayer.mThukai<2 && mGR.levelWinS1<1)
			{
				DrawTransScal(gl, mGR.mTex_Levelster	, -.005f, 0.09f,mGR.levelWinS2,1);
				if(mGR.levelWinS2>=1)
				{
					mGR.levelWinS2*=.8f;
					if(mGR.levelWinS2<=1){
						mGR.levelWinS2 =.98f;
						setAnimation2(-.005f, 0.01f);
					}
				}
			}
			if(mGR.mPlayer.mThukai<1 && mGR.levelWinS2<1)
			{
				DrawTransScal(gl, mGR.mTex_Levelster	, 0.205f, 0.015f,mGR.levelWinS3,1);
				if(mGR.levelWinS3>=1)
				{
					mGR.levelWinS3*=.8f;
					if(mGR.levelWinS3<=1){
						setAnimation2(0.205f, 0.01f);
						mGR.levelWinS3=.98f;
					}
				}
			}
		}
		if(M.GameScreen == M.GAMEPAUSE)
		{
			DrawTexture(gl, mGR.mTex_Gamepaused	, -.03f, 0.42f);
			DrawTransScal(gl, mGR.mTex_PC	, 0.33f, -.38f,mGR.mSel==5?1.5f:1,mGR.mSel==5?.4f:1);
		}
		if(M.GameScreen == M.GAMEOVER)
		{
			DrawTexture(gl, mGR.mTex_Gameover	, -.03f, 0.42f);
			DrawTransScal(gl, mGR.mTex_More	, 0.33f, -.38f,mGR.mSel==5?1.5f:1,mGR.mSel==5?.4f:1);
			DrawTexture(gl, mGR.mTex_TimeScore, -.18f, 0.00f);
			drawNumberM(gl, mGR.mLevel+"", 0.02f, 0.09f);
			
			int time1 = (int)(mGR.GameTime/60000);
			String str = time1+":"; 
			if(time1<10)
				str = "0"+time1+":";
			time1 = (int)(mGR.GameTime/1000)%60;
			if(time1<10)
				str += "0"+time1;
			else
				str += time1;
			drawNumberM(gl, str, 0.02f, -.09f);
		}
		
		drawAnimation(gl);
	}
	boolean HandleOverCom(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CirCir(-.33f, -.38f, mGR.mTex_Setting.width()/2, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 2;
		}
		if(CirCir(-.11f, -.38f, mGR.mTex_Setting.width()/2, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 3;
		}
		if(CirCir(0.11f, -.38f, mGR.mTex_Setting.width()/2, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 4;
		}
		if(CirCir(0.33f, -.38f, mGR.mTex_Setting.width()/2, screen2worldX(event.getX()), screen2worldY(event.getY()), .05f))
		{
			mGR.mSel = 5;
		}
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mGR.mSel) {
			case 2:
				sy =0;
				M.GameScreen = M.GAMELEVEL;
				break;
			case 3:
				RateUs();
				break;
			case 4:
				mGR.loading();
				break;
			case 5:
				if(M.GameScreen == M.GAMEWIN)
				{
					if(mGR.mLevel<18)
						mGR.mLevel++;
					mGR.loading();
				}
				if(M.GameScreen == M.GAMEPAUSE)
				{
					M.GameScreen = M.GAMEPLAY;
					mGR.GameTime = System.currentTimeMillis()-mGR.GameTime;
					M.play(GameRenderer.mContext, R.drawable.bg);
				}
				if(M.GameScreen == M.GAMEOVER){
					MoreGame();
				}
				
				break;
			}
			if(mGR.mSel!=0)
				M.sound1(GameRenderer.mContext, R.drawable.button);
			mGR.mSel = 0;
		}
		
		return true;
	}
	/**************************End OverCom*******************/
	
	void GameWin()
	{
		M.GameScreen = M.GAMEWIN;
		if(mGR.mLevel == 18)
			M.GameScreen = M.GAMECONG;
		if(mGR.unStar[mGR.mLevel-1] < 3-mGR.mPlayer.mThukai)
			mGR.unStar[mGR.mLevel-1] = 3-mGR.mPlayer.mThukai;
		if(mGR.unLock<=mGR.mLevel)
			mGR.unLock=mGR.mLevel+1;
		if((mGR.mLevel>=3 && mGR.unLock <7)||(mGR.mLevel>=9 && mGR.unLock <12))
			mGR.unLock+=6;
			
		M.playStop();
		M.sound4(GameRenderer.mContext, R.drawable.lavelcomp);
	}
	void life()
	{
		if(mGR.mPlayer.mBlast>6)
		{
			if(M.setVibrator){
				Vibrator vib = (Vibrator) GameRenderer.mContext.getSystemService(Context.VIBRATOR_SERVICE);  
				vib.vibrate(500);
			}
			M.sound2(GameRenderer.mContext, R.drawable.carblast);
			if(mGR.mPlayer.mThukai<3)
				mGR.mPlayer.mThukai++;
			mGR.mPlayer.mBlast =0;
		}
	}
	float x1,y1,x2,y2;
	float x3,y3,x4,y4;
//	float x5,y5,x6,y6;
	void gameLogic()
	{
		mGR.mPlayer.update();
		x1 = mGR.mPlayer.x+(float)Math.cos(Math.toRadians(mGR.mPlayer.a))*0.06f*ml*.2f;
		y1 = mGR.mPlayer.y+(float)Math.sin(Math.toRadians(mGR.mPlayer.a))*0.10f*ml*.2f;
		x2 = mGR.mPlayer.x+(float)Math.cos(Math.toRadians(mGR.mPlayer.a+180))*0.06f*ml*2;
	    y2 = mGR.mPlayer.y+(float)Math.sin(Math.toRadians(mGR.mPlayer.a+180))*0.10f*ml*2;
	    
	    
	    mGR.mPlayer.x1 = mGR.mPlayer.x+(float)Math.cos(Math.toRadians(mGR.mPlayer.a))*0.06f*6f;
	    mGR.mPlayer.y1 = mGR.mPlayer.y+(float)Math.sin(Math.toRadians(mGR.mPlayer.a))*0.10f*6f;
	    mGR.mPlayer.x2 = mGR.mPlayer.x+(float)Math.cos(Math.toRadians(mGR.mPlayer.a+180))*0.06f*3;
	    mGR.mPlayer.y2 = mGR.mPlayer.y+(float)Math.sin(Math.toRadians(mGR.mPlayer.a+180))*0.10f*3;
	    
		for(int i =0;i<mGR.mPixel.length;i++)
		{
			if(mGR.mPixel[i] !=null){
				if(CircRectsOverlap(mGR.mPlayer.x, mGR.mPlayer.y, .25f, .22f, mGR.mPixel[i].x, mGR.mPixel[i].y, .21f))
				{
					if(CircRectsOverlap(x1,y1, .01f, .01f, mGR.mPixel[i].x, mGR.mPixel[i].y, .02f))
					{
						mGR.mPlayer.x = mGR.mPlayer.ox;
						mGR.mPlayer.y = mGR.mPlayer.oy;
						mGR.mPlayer.a = mGR.mPlayer.oa;
						life();
	//					System.out.println("!!!!!![plx = "+mGR.mPlayer.x+"] [ply = "+mGR.mPlayer.y+"]  [px = "+mGR.mPixel[i].x+"] [py = "+mGR.mPixel[i].y+"]");
					}
					if(CircRectsOverlap(x2,y2, .01f, .01f, mGR.mPixel[i].x, mGR.mPixel[i].y, .02f))
					{
						mGR.mPlayer.x = mGR.mPlayer.ox;
						mGR.mPlayer.y = mGR.mPlayer.oy;
						mGR.mPlayer.a = mGR.mPlayer.oa;
						life();
	//					System.out.println("~~~~~~[plx = "+mGR.mPlayer.x+"] [ply = "+mGR.mPlayer.y+"]  [px = "+mGR.mPixel[i].x+"] [py = "+mGR.mPixel[i].y+"]");
					}
	//				System.out.println("[px = "+mGR.mPlayer.x+"] [py = "+mGR.mPlayer.y+"] ~~ [ x1 = "+x1+"] [y1 = "+y1+"] ~~ [x2 = "+x2+"] [y2 = "+y2+"]");
				}
			}
		}
		
		if(mGR.mPlayer.x < -1 || mGR.mPlayer.x >1 || mGR.mPlayer.y <-1 || mGR.mPlayer.y >1)
		{
			mGR.mPlayer.x = mGR.mPlayer.ox;
			mGR.mPlayer.y = mGR.mPlayer.oy;
			mGR.mPlayer.a = mGR.mPlayer.oa;
			life();
		}
		
		
		if(mGR.mRPixel[0].x<x1 && mGR.mRPixel[1].x>x1 && mGR.mRPixel[2].x<x1 && mGR.mRPixel[3].x>x1)
		{
			if(mGR.mRPixel[0].y>y1 && mGR.mRPixel[1].y>y1 && mGR.mRPixel[2].y<y1 && mGR.mRPixel[3].y<y1)
			{
//				System.out.println("~~~~~~~~~~~~~~~~~Congretulation~~~~~~~~~~~~~~~~");
				if(mGR.mRPixel[0].x<x2 && mGR.mRPixel[1].x>x2 && mGR.mRPixel[2].x<x2 && mGR.mRPixel[3].x>x2)
				{
					if(mGR.mRPixel[0].y>y2 && mGR.mRPixel[1].y>y2 && mGR.mRPixel[2].y<y2 && mGR.mRPixel[3].y<y2)
					{
//						System.out.println("~~~~~~~~~~~~~~~~~Mukti~~~~~~~~~~~~~~~~");
						setAnimation(mGR.mPlayer.x, mGR.mPlayer.y);
						 mGR.mPlayer.gameWin =true;
						 if(Counter>110)
							 Counter = 0;
					}
				}
			}
		}
		
		for(int i =0;i<mGR.mOpponent.length;i++)
		{
			if(mGR.mOpponent[i].type>0)
			{
				boolean tuch =false;
				if(CircRectsOverlap(x1,y1, .02f, .02f, mGR.mOpponent[i].x, mGR.mOpponent[i].y, .02f))
				{
					System.out.println("@@@@@@@@[plx = "+mGR.mPlayer.x+"] [ply = "+mGR.mPlayer.y+"]  [px = "+mGR.mOpponent[i].x+"] [py = "+mGR.mOpponent[i].y+"]");
					life();
					tuch =true;
				}
				if(CircRectsOverlap(x2,y2, .02f, .02f, mGR.mOpponent[i].x, mGR.mOpponent[i].y, .02f))
				{
					tuch =true;
					life();
					System.out.println("#######[plx = "+mGR.mPlayer.x+"] [ply = "+mGR.mPlayer.y+"]  [px = "+mGR.mOpponent[i].x+"] [py = "+mGR.mOpponent[i].y+"]");
				}
				if(tuch || mGR.mOpponent[i].get_line_intersection(mGR.mPlayer.x2, mGR.mPlayer.y2, mGR.mPlayer.x1, mGR.mPlayer.y1))
				{
					
				}
				else
					mGR.mOpponent[i].update();
			}
		}
		if(mGR.mPlayer.gameWin && Counter>50)
		{
			GameWin();
		}
		if(mGR.mPlayer.mThukai>2 && mGR.mPlayer.mBlast>5)
		{
			M.GameScreen = M.GAMEOVER;
			mGR.GameTime = System.currentTimeMillis()-mGR.GameTime;
			M.playStop();
			M.sound3Play(GameRenderer.mContext, R.drawable.gameover);
		}
		
	}
	void DrawGamePlay(GL10 gl)
	{
		DrawTexture(gl, mGR.mTex_Road, 0, 0);
		DrawTexture(gl, mGR.mTex_GameBG, 0, 0);
		drawAnimation(gl);
		
		
		float b = .45f;
		x3 = x1+(float)Math.cos(Math.toRadians(mGR.mPlayer.a+90))*0.06f*b;
		y3 = y1+(float)Math.sin(Math.toRadians(mGR.mPlayer.a+90))*0.10f*b;
		DrawTextureR(gl, mGR.mTex_Pahiye, x3,y3,mGR.mPlayer.a+mGR.mPlayer.pa);
		
		x4 = x1+(float)Math.cos(Math.toRadians(mGR.mPlayer.a-90))*0.06f*b;
		y4 = y1+(float)Math.sin(Math.toRadians(mGR.mPlayer.a-90))*0.10f*b;
		DrawTextureR(gl, mGR.mTex_Pahiye, x4,y4,mGR.mPlayer.a+mGR.mPlayer.pa);
		
		
//		DrawTextureR(gl, mGR.mTex_Pahiye, mGR.mPlayer.x1,mGR.mPlayer.y1,mGR.mPlayer.a);
		
		
		
//		x5 = x2+(float)Math.cos(Math.toRadians(mGR.mPlayer.a+90))*0.06f*.5f;
//		y5 = y2+(float)Math.sin(Math.toRadians(mGR.mPlayer.a+90))*0.10f*.5f;
//		DrawTextureR(gl, mGR.mTex_Pahiye, x5,y5,mGR.mPlayer.a);
//		x6 = x2+(float)Math.cos(Math.toRadians(mGR.mPlayer.a-90))*0.06f*.5f;
//		y6 = y2+(float)Math.sin(Math.toRadians(mGR.mPlayer.a-90))*0.10f*.5f;
//		DrawTextureR(gl, mGR.mTex_Pahiye, x6,y6,mGR.mPlayer.a);
		
		
		
		if(mGR.mPlayer.glow)
		{
			if(mGR.mPlayer.t>1.5)
				mGR.mPlayer.vt =-.05f;
			if(mGR.mPlayer.t<.2)
				mGR.mPlayer.vt =0.05f;
			mGR.mPlayer.t+=mGR.mPlayer.vt;
			float xm = mGR.mPlayer.x+(float)Math.cos(Math.toRadians(mGR.mPlayer.a))*0.06f*ml*.6f;
			float ym = mGR.mPlayer.y+(float)Math.sin(Math.toRadians(mGR.mPlayer.a))*0.10f*ml*.6f;
			DrawTextureRC(gl, mGR.mTex_Glow	, xm, ym,mGR.mPlayer.a,mGR.mPlayer.t);
		}
		DrawTextureRC(gl, mGR.mTex_Car[mGR.mPlayer.mThukai%4]	, mGR.mPlayer.x, mGR.mPlayer.y,mGR.mPlayer.a,1);
		if(mGR.mPlayer.mBlast<7){
			DrawTexture(gl, mGR.mTex_Blast[mGR.mPlayer.mBlast], mGR.mPlayer.x, mGR.mPlayer.y);
			if(Counter%4==0)
				mGR.mPlayer.mBlast++;
		}
		
		
		
		
//		DrawTextureRC(gl, mGR.mTex_Car	, 0, 0,Counter);
//		System.out.println(Counter+"~~~~~~~~~~~~~~~~~~~~   "+Counter +"   [v2x = "+Counter+"]   [vy = "+Counter+"]");
//		DrawTexture(gl, mGR.mTex_8x8	, x1,y1);
//		DrawTexture(gl, mGR.mTex_8x8, x2,y2);
		
//		mGR.mArrow[0].x+(float)Math.cos(mGR.mArrow[0].radian)*.33f,mGR.mArrow[0].y+ (float)Math.sin(mGR.mArrow[0].radian)*.605f
		for(int i =0;i<mGR.mOpponent.length;i++)
		{
//			if(mGR.mOpponent[i].type>0){
//				DrawTextureR(gl, mGR.mTex_Pahiye, mGR.mOpponent[i].x1,mGR.mOpponent[i].y1,00);
//				DrawTextureR(gl, mGR.mTex_Pahiye, mGR.mOpponent[i].i_x,mGR.mOpponent[i].i_y,00);
//			}
			switch (mGR.mOpponent[i].type) {
			case 1:
				DrawTextureR(gl, mGR.mTex_Opp[0], mGR.mOpponent[i].x,mGR.mOpponent[i].y,00);
				break;

			case 2:
				DrawTextureR(gl, mGR.mTex_Opp[1], mGR.mOpponent[i].x,mGR.mOpponent[i].y,-90);
				break;
			case 3:
				DrawTextureR(gl, mGR.mTex_Opp[2], mGR.mOpponent[i].x,mGR.mOpponent[i].y, 90);
				break;
			case 4:
				DrawTextureR(gl, mGR.mTex_Opp[3], mGR.mOpponent[i].x,mGR.mOpponent[i].y,180);
				break;
			case 5:case 13:
				DrawTextureR(gl, mGR.mTex_Char[Counter%mGR.mTex_Char.length], mGR.mOpponent[i].x,mGR.mOpponent[i].y,0);
				break;
			case 6:case 7:case 12:case 14:case 15:case 16:
				DrawTextureR(gl, mGR.mTex_Char[Counter%mGR.mTex_Char.length], mGR.mOpponent[i].x,mGR.mOpponent[i].y,90);
				break;
			case 8:case 9:case 10:case 11:case 17:case 18:
				DrawTextureR(gl, mGR.mTex_Char[Counter%mGR.mTex_Char.length], mGR.mOpponent[i].x,mGR.mOpponent[i].y,45);
				break;
			}
				
		}
		DrawTexture(gl,mGR.mTex_Meter[0], -.84f, 0.83f);
		DrawTexture(gl,mGR.mTex_Meter[(mGR.mPlayer.mThukai%3)+1], -.84f, 0.88f);
		
		
		
		long time = System.currentTimeMillis()-mGR.GameTime;
		int time1 = (int)(time/60000);
		String str = time1+":"; 
		if(time1<10)
			str = "0"+time1+":";
		time1 = (int)(time/1000)%60;
		if(time1<10)
			str += "0"+time1;
		else
			str += time1;
		drawNumber(gl, str, -.90f, 0.75f);
		
		DrawTransScal(gl, mGR.mTex_Pausad		, +.88f, 0.8f,mGR.mSel==101?1.5f:1,mGR.mSel==101?.4f:1);
		
		mGR.mTex_Strin.drawTSR(gl, 0.8f,-.68f, mGR.mPlayer.angleno!=0?1.5f:1,mGR.mPlayer.angleno!=0?.6f:1, mGR.mPlayer.pa*8);
		mGR.mTex_Speed.drawTSR(gl, -.8f, -.2f, mGR.mPlayer.speedno==1?1.5f:1,mGR.mPlayer.speedno==1?.4f:1, 000);
		mGR.mTex_Speed.drawTSR(gl, -.8f, -.8f, mGR.mPlayer.speedno==2?1.5f:1,mGR.mPlayer.speedno==2?.4f:1, 180);
		gameLogic();
	}

	
	int oldx,oldy;
	double olda;
	public boolean Handle_GamePlay(MotionEvent ev) 
	{
		mGR.mPlayer.glow = false;
		//		mGR.mPlayer.tuch = mGR.mTarget.tuch = false;
		int count =  MotionEventCompat.getPointerCount(ev);
		mGR.mPlayer.speedno =0;
		for(int i=0;i<count;i++)
		{
			final float x = MotionEventCompat.getX(ev, i); 
			final float y = MotionEventCompat.getY(ev, i); 
			if(CirCir(0.8f, -.68f, .4f, screen2worldX(x), screen2worldY(y), .1f))
			{
				double cura = GetAngle(y-oldy, x-oldx);
				if(olda>cura)
					mGR.mPlayer.angleno = 4;
				if(olda<cura)
					mGR.mPlayer.angleno = 3;
				if(olda==cura)
					mGR.mPlayer.angleno = 5;
				olda = cura;
				oldx = (int)x;
				oldy = (int)y;
			}
			if(CircRectsOverlap(-.8f, -.2f,.2f, .2f, screen2worldX(x), screen2worldY(y), .1f))
				mGR.mPlayer.speedno = 1;
			if(CircRectsOverlap(-.8f, -.8f,.2f, .2f, screen2worldX(x), screen2worldY(y), .1f))
				mGR.mPlayer.speedno = 2;
		}
		if(MotionEvent.ACTION_UP== ev.getAction())
		{
			
			mGR.mPlayer.speedno = 0;
			mGR.mPlayer.angleno = 0;
			if(CirCir(+.88f, 0.8f, .1f, screen2worldX(ev.getX()), screen2worldY(ev.getY()), .1f))
			{
				M.GameScreen = M.GAMEPAUSE;
				mGR.GameTime = System.currentTimeMillis()-mGR.GameTime;
				M.playStop();
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
		Tex.drawRotet(gl, x, y,angle,1);
	}
	void DrawTextureRC(GL10 gl,SimplePlane Tex,float x,float y,float angle,float t)
	{
		Tex.drawRotetC(gl, x, y,angle,t);
	}
	
	void DrawTransScal(GL10 gl,SimplePlane Tex,float x,float y, float z,float t)
	{
		Tex.drawTransprentScal(gl, x, y, z, t);
	}
	void DrawRGBS(GL10 gl,SimplePlane Tex,float x,float y, float z,float r,float g,float b)
	{
		Tex.drawRGBS(gl, x, y, r, g, b, z);
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
		float dx = mGR.mTex_Font[0].width()*1.2f;
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i))-48;
			if(k>=0 && k<mGR.mTex_Font.length)
				mGR.mTex_Font[k].drawPos(gl,x+i*dx,y);
		}
	}
	void drawNumberM(GL10 gl,String strs,float x, float y)
	{
		float dx = mGR.mTex_Font2[0].width()*.8f;
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i))-48;
			if(k>=0 && k<mGR.mTex_Font2.length)
				mGR.mTex_Font2[k].drawPos(gl,x+i*dx,y);
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
	void facebook()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://www.facebook.com/HututuGames"));
		GameRenderer.mContext.startActivity(mIntent);
	}
	void gPluce()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://plus.google.com/101161010890539846728"));
		GameRenderer.mContext.startActivity(mIntent);
	}
	void setAnimation(float x,float y)
	{
		int cr = 0;
		for(int i=0;i<mGR.ani.length&& cr<10;i++)
		{
			if(mGR.ani[i].x<-1.1||mGR.ani[i].x>1.1||mGR.ani[i].y<-1.1||mGR.ani[i].y>1.1)
			{
				cr++;
				mGR.ani[i].set(x, y);
			}
		}
	}
	void setAnimation2(float x,float y)
	{
		int cr = 0;
		for(int i=0;i<mGR.ani.length&& cr<80;i++)
		{
			if(mGR.ani[i].x<-1.1||mGR.ani[i].x>1.1||mGR.ani[i].y<-1.1||mGR.ani[i].y>1.1)
			{
				cr++;
				mGR.ani[i].set(x, y);
			}
		}
	}
	void drawAnimation(GL10 gl)
	{
		for(int i=0;i<mGR.ani.length;i++)
		{
			if(mGR.ani[i].x>-1.2&&mGR.ani[i].x<1.2&&mGR.ani[i].y>-1.2&&mGR.ani[i].y<1.2)
			{
				mGR.mTex_8x8.drawRGBS(gl, mGR.ani[i].x, mGR.ani[i].y, mGR.ani[i].r, mGR.ani[i].g, mGR.ani[i].b, mGR.ani[i].scal);
				mGR.ani[i].update();
			}
		}
	}
}
//boolean HandleGamePlay(MotionEvent event)
//{
//	if(mGR.mPlayer.gameWin)
//		return true;
//	mGR.mSel = 0;
//	System.out.println("~~~~~~~~~~~~~~~~~~~~~ "+GetAngle(event.getY()-oldy, event.getX()-oldx));
//	oldx = (int)event.getX();
//	oldy = (int)event.getY();
//	if(CircRectsOverlap(-.8f,0.8f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))
//		mGR.mPlayer.no = 1;
//	if(CircRectsOverlap(0.8f,0.8f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))
//		mGR.mPlayer.no = 2;
//	if(CircRectsOverlap(-.8f,-.8f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))
//		mGR.mPlayer.no = 3;
//	if(CircRectsOverlap(0.8f,-.8f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))
//		mGR.mPlayer.no = 4;
//	if(CircRectsOverlap(0.0f,-.0f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))
//		mGR.mSel = 5;
//	if(event.getAction()== MotionEvent.ACTION_UP)
//	{
//		mGR.mPlayer.no = 0;
//		if(mGR.mSel == 5)
//		{
//			Counter =0;
//			mGR.mLevel++;
//			mGR.mLevel%=18;
//			if(mGR.mLevel == 0)
//				mGR.mLevel = 1;
//			mGR.gameReset();
//		}
//		mGR.mSel = 0;
//	}
//	
//	return true;
//}