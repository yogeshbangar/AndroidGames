package com.hututu.game.BoomBaseball;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import com.google.ads.AdView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class GameRenderer implements Renderer 
{
	final Group root;
	static Context mContext;
	public static Start mStart;
	long startTime = System.currentTimeMillis();
	int resumeCounter =0;
	int mSel = 0;
	boolean isHit; 
	
	SimplePlane[] mTex_Font,mTex_Bowler,mTex_PlayerReady,mTex_Player,mTex_GameBg,mTex_CarR,mTex_CarL,mTex_WinDowChar[],mTex_Target;
	SimplePlane   mTex_Katch[],mTex_Effect[],mTex_Text[],mTex_Bonus[],mTex_Ballon,mTex_Boom,mTex_BallNo,mTex_PlayerShad,mTex_BowlerShad;
	SimplePlane   mTex_Logo,mTex_Ball,mTex_dot,mTex_BreakWindow[],mTex_Circle,mTex_Arrow,mTex_Bar[],mTex_Bird[],mTex_Plane,mTex_Heli[];
	SimplePlane   mTex_Splash,mTex_HighScore,mTex_More,mTex_Setting,mTex_IcnSel,mTex_BtnSel,mTex_PopUp[],mTex_Music,mTex_Sound,mTex_SoundIcn[]
				  ,mTex_AbtUs,mTex_Help,mTex_BackBtn,mTex_BackBtnSel,mTex_Continue,mTex_Pause,mTex_PauseTxt,mTex_ScoreBoard;
	SimplePlane   mTex_Play,mTex_MusicIcn,mTex_Position,mTex_Home,mTex_BonusShot,mTex_ExtraShot,mTex_NewScore,mTex_PrevScore
				  ,mTex_TargetWindow,mTex_Retry,mTex_AbtTxt;
	SimplePlane   mTex_Achive[],mTex_Dot0,mTex_Dot1,mTex_AchiveTxt,mTex_TargetComp,mTex_LoadBar,mTex_Exit,mTex_Pointer,mTex_AddFree,mTex_Remove;
	SimplePlane   mTex_Join[],mTex_Helptxt,mTex_AchiveClear;
	
	
	Animation mAni[],mEffect,mBallon,mTail[];
	Bowler 	mBowler; 
	Ball   	mBall;
	BatsMan mBatsMan;
	Car 	mCar[],mCarL[],mFly;
	TextAni mTextAni,mBonus,mBoom,mSboardAni[],mTargetComp,mAchiveAni;
	Window 	mWindow[],mWindowChar;
	 
	boolean isBonus,isPowerTouch,isAngleTouch,isJoin;
	float px,py;
	float vx,vy;
	float ang,dis;
	float mBonusX,mBonusY; 
	int mTotalBall,mScore,mBonusCount;
	boolean isWinDow,isRoof,isCar,isTarget; 
	int     mWindowCnt,mRoofCnt,mCarCnt;
	int		mTargetScore,mExtraScore,mBonusScore,mPrevScore,mNewScore;
	int     mTargetWindow,mLevelWindow=0,mLevel=1;
	int		NoCount=0;
	
	int     mAchCarCnt,mAchDoorCnt,mAchRoof,mAchCntShot,mAchTotalShot,mAchStrike,mAchTotalWindow,mAchBallon;
	MainActivity mMainActivity;
	private Handler handler = new Handler() {public void handleMessage(Message msg) {mStart.adView.setVisibility(msg.what);}};
	private Handler handler3 = new Handler() {public void handleMessage(Message msg) {mStart.adalaod.setVisibility(msg.what);}};
	private Handler handler2 = new Handler() {public void handleMessage(Message msg) {mStart.adHouse.setVisibility(msg.what);}};//AdHouse
	public GameRenderer(Context context) 
	{
		mContext = context;
		mStart	= (Start)mContext;
		root = new Group(this);
		init();
	}
	void init()
	{
		try
		{
			mMainActivity = new MainActivity();
			mMainActivity.onCreate();
			System.gc();
			mTex_Logo		= add("hututugames.png");
			mTex_Ball       = addRotate("ball.png");
			mTex_dot        = add("smoke.png");
			mTex_Bowler     = new SimplePlane[53];
			for(int i = 0; i < mTex_Bowler.length; i++)
			  mTex_Bowler[i] = add("bowler/"+(i+1)+".png");
			mTex_BowlerShad  = add("bowler/shadow.png");
			
			mTex_PlayerReady = new SimplePlane[3];
			for(int i=0;i<mTex_PlayerReady.length;i++)
			mTex_PlayerReady[i] = add("batsman/"+(i+1)+".png");
			mTex_PlayerShad     = add("batsman/shadow.png");
			
			mTex_Player    = new SimplePlane[40];
			for(int i=0;i<mTex_Player.length;i++)
 			  mTex_Player[i] = add("batsman/"+(i+1)+".png");
			
			mTex_CarR     = new SimplePlane[8];
			mTex_CarL     = new SimplePlane[5];
			for(int i=0;i<mTex_CarR.length;i++)
			 mTex_CarR[i] = add("car/car"+(i+1)+".png");
			
			for(int i=0;i<mTex_CarL.length;i++)
			mTex_CarL[i]     = add("car/car"+(i+9)+".png");
			mTex_GameBg      = new SimplePlane[3];
			mTex_GameBg[0]   = add("bg0.jpg");
			mTex_GameBg[1]   = add("bg1.png");
			mTex_GameBg[2]   = add("bg2.png");
			mTex_BreakWindow = new SimplePlane[13];
			for(int i=0;i<mTex_BreakWindow.length-3;i++)
			mTex_BreakWindow[i]  = add("window/"+i+".png");
			
			mTex_BreakWindow[10] = add("window/11.png");
			mTex_BreakWindow[11] = add("window/31.png");
			mTex_BreakWindow[12] = add("window/41.png");
			mTex_WinDowChar    = new SimplePlane[4][];
			mTex_WinDowChar[0] = new SimplePlane[9];
			for(int i=0;i<mTex_WinDowChar[0].length;i++)
			  mTex_WinDowChar[0][i] = add("windowman/1/"+(i+1)+(".png"));
			
			mTex_WinDowChar[1]      = new SimplePlane[11];
			for(int i=0;i<mTex_WinDowChar[1].length;i++)
			  mTex_WinDowChar[1][i] = add("windowman/2/"+(i+1)+(".png"));
			
			mTex_WinDowChar[2]      = new SimplePlane[7];
			for(int i=0;i<mTex_WinDowChar[2].length;i++)
			  mTex_WinDowChar[2][i] = add("windowman/3/"+(i+1)+(".png"));
			
			mTex_WinDowChar[3]      = new SimplePlane[11];
			for(int i=0;i<mTex_WinDowChar[3].length;i++)
			  mTex_WinDowChar[3][i] = add("windowman/4/"+(i+1)+(".png"));
			
			mTex_Katch 		  =	 new SimplePlane[7];
			for(int i=0;i<mTex_Katch .length;i++)
				mTex_Katch[i] = add("mirror/"+(i+1)+".png");

			mTex_Effect       =  new SimplePlane[5];
			for(int i=0;i<mTex_Effect.length;i++)
			 mTex_Effect[i]   = add("effect/"+i+".png");
			mTex_Ballon       = addRotate("ballon.png");
			mTex_Text         = new SimplePlane[13];
			for(int i=0;i<mTex_Text.length;i++)
			mTex_Text[i]      = add("text/"+i+".png");
			
			mTex_Bonus        = new SimplePlane[2];
			mTex_Bonus[0]     = add("text/bonus0.png");
			mTex_Bonus[1]     = add("text/bonus1.png");
			
			mTex_Boom         = add("boom.png");
			mTex_BallNo       = add("ballNo.png"); 
			mTex_Circle       = addRotate("Circle.png");
			mTex_Arrow        = addRotate("arrow.png");
			mTex_Bar          = new SimplePlane[3];
			mTex_Bar[0]       = add("bar0.png");
			mTex_Bar[1]       = add("bar1.png");
			mTex_Bar[2]       = add("bar2.png");
			mTex_Bird         = new SimplePlane[8];
			for(int i=0;i<mTex_Bird.length;i++)
			  mTex_Bird[i]    = add("bird/"+(i+1)+".png");	
			mTex_Plane        = add("plane.png");
			mTex_Heli         = new SimplePlane[2];
			mTex_Heli[0]      = add("helicopter0.png");
			mTex_Heli[1]      = add("helicopter1.png");
			mTex_Target       = new SimplePlane[2];
			mTex_Target[0]    = add("target.png");  
			mTex_Target[1]    = add("targetballs.png");
			mTex_Achive       = new SimplePlane[21];
			for(int i=0;i<mTex_Achive.length;i++)
			   mTex_Achive[i] = add("Achive/"+i+".png");
			
			mTex_Dot0         = add("Achive/dot0.png");
			mTex_Dot1         = add("Achive/dot1.png");
			mTex_AchiveTxt    = add("Achive/achievement.png");
			mTex_TargetComp   = add("targetcompleted.png");
			mTex_AchiveClear  = add("Achievementclear.png");
			LoadUi();
			load_Font();
		    InitGameObj();
		    M.loadSound(mContext);
		}catch(Exception e){}
	}
	void LoadUi()
	{
	   mTex_Splash 		= add("ui/Splash.jpg");
	   mTex_HighScore   = add("ui/highscore.png");
	   mTex_Setting     = add("ui/setting.png");
	   mTex_More        = add("ui/moregames.png");
	   mTex_BtnSel      = add("ui/btn_select.png");
	   mTex_IcnSel      = add("ui/icon_select.png");
	   mTex_PopUp       = new SimplePlane[3];
	   for(int i=0;i<mTex_PopUp.length;i++)
	   mTex_PopUp[i]    = add("ui/box"+(i+2)+".png");
	   mTex_Music       = add("ui/music.png");
	   mTex_Sound       = add("ui/sound.png");
	   mTex_SoundIcn    = new SimplePlane[2];
	   mTex_SoundIcn[0] = add("ui/sound_on.png");  
	   mTex_SoundIcn[1] = add("ui/sound_off.png");
	   mTex_Help	    = add("ui/help.png");
	   mTex_AbtUs	    = add("ui/about.png");
	   mTex_BackBtn     = add("ui/backbtn.png");
	   mTex_BackBtnSel  = add("ui/backselect.png");
	   mTex_Continue    = add("ui/continue.png");
	   mTex_Pause       = add("ui/pause.png");
	   mTex_PauseTxt    = add("ui/gamepause.png");
	   mTex_Play        = add("ui/play.png"); 
	   mTex_MusicIcn    = add("ui/music_icon.png");
	   mTex_Position    = add("ui/position.png");
	   mTex_Home        = add("ui/home.png");
	   mTex_BonusShot   = add("ui/BonusShot.png");
	   mTex_ExtraShot   = add("ui/ExtraBonus.png");
	   mTex_NewScore    = add("ui/NewScore.png");
	   mTex_PrevScore   = add("ui/PreviousScore.png");
	   mTex_TargetWindow= add("ui/TargetWindow.png");
	   mTex_ScoreBoard  = add("ui/scoreboard.png");
	   mTex_Retry       = add("ui/retry.png");
	   mTex_AbtTxt      = add("ui/about_text.png");
	   mTex_LoadBar		= add("bar.png");
	   mTex_Exit		= add("exit.png");
	   mTex_Pointer		= add("pointer.png");
	   mTex_AddFree     = add("adsfree.png");
	   mTex_Remove      = add("no_ads.png");
	   mTex_Join        = new SimplePlane[2];
	   mTex_Join[0]     = add("join.png");        
	   mTex_Join[1]     = add("join_text.png");
	   mTex_Helptxt     = add("ui/helptxt.jpg");
	}
	void InitGameObj()
	{
		int i;
		mBowler 	= new Bowler(this);
		mBall		= new Ball(this);
		mBatsMan    = new BatsMan(this);
		mAni        = new Animation[10];
		for(i=0;i<mAni.length;i++)
		mAni[i]     = new Animation(this);
		
		mTail       = new Animation[100];
		for(i=0;i<mTail.length;i++)
		  mTail[i]  = new Animation(this);
		
		mEffect     = new Animation(this);
		mBallon     = new Animation(this);
		mCar        = new Car[4];
		mCarL       = new Car[4];
		for(i=0;i<mCar.length;i++)
		{
		  mCar[i]    = new Car(this);
		  mCarL[i]   = new Car(this);
		}
		mFly         = new Car(this);
		mWindow      = new Window[M.Wx.length];
		for(i=0;i<mWindow.length;i++)
			mWindow[i]  = new Window();
		 mWindowChar    = new Window();
		 mTextAni       = new TextAni();
		 mBonus   	    = new TextAni();
		 mBoom          = new TextAni();
		 mSboardAni 	= new TextAni[5];
		 for(i=0;i<mSboardAni.length;i++)
			 mSboardAni[i] = new TextAni(this);	 
		 mTargetComp    = new TextAni();
		 mAchiveAni     = new TextAni();
		
	}
	void GameReset()
	{
		int i;
		isHit = false;
		mBowler.Set(0,-.389f);
		mBatsMan.Set(-.25f,-.59f);
		mBall.Set(-100,-100,0,0);
		for(i=0;i<mCar.length;i++)
		{
		  mCar[i].set(2f+(i*.8f),-M.randomRange(.006f,.01f),M.mRand.nextInt(mTex_CarR.length));
		  mCarL[i].set(-2f-(i*.8f),M.randomRange(.005f,.01f),M.mRand.nextInt(mTex_CarL.length));
		}
		
		for(i=0;i<mWindow.length;i++)
		  mWindow[i].Set(-100,-100,0);
		  mWindowChar.SetChar(-100,-100);
		  mBallon.SetBallon(-100, -100);
		  mBallon.isVisible =false;
		  mBallon.AniCount =0;
		  SetBirdPlane();
		vx=vy=0;
		isCar = isWinDow = isRoof   = false; 
		mCarCnt=mWindowCnt=mRoofCnt = 0;
		mAchCntShot = mAchStrike =0;
		dis =.043f;
		ang = 0;
		isPowerTouch = false;
		isAngleTouch = false;
		isJoin       = false;          
		mTargetScore = mExtraScore = mBonusScore=0;
		mScore=0;
		root.PowerCnt=0;
		isBonus =false;
		mBonusCount=0;
		mBonusX=-100f;
		mBonusY=-100f;
		SetTarget();
	} 
   void SetGlassAni(float x,float y)
   {
	   for(int i=0;i<mAni.length;i++)
		{
			mAni[i].set(x,y,M.randomRange(-.005f,.005f),M.randomRange(-.01f,.01f));
		}
   }
   void SetBirdPlane()
   {
	   mFly.No =M.randomRangeInt(0,2);
		if(mFly.No==0)
		{
			mFly.x  =-2.5f;
			mFly.vx = .004f;
		}
		if(mFly.No>0)
		{
		  mFly.x  = 2.5f;
		  mFly.vx = -.005f;
		}
		mFly.SetPlaneBird(mFly.x,.809f,mFly.vx,mFly.No);
		
//		System.out.println("N0================    "+mFly.No+"X=============     "+mFly.x+"vx===============   "+mFly.vx);
   }
   void SetTarget()
   {
	  M.StopSound();
	  mLevelWindow= 0;
	  mTotalBall  = 0;
	  root.Counter2=0;
	  root.PopY  = 1.5f;
	  root.PopVY =-.05f;
	  isTarget=false;
	  mTargetWindow = mLevel; 
	  if(mLevel<5)
		  mTotalBall    = (2+(mLevel/5))*5;
	  else
		  mTotalBall    = (1+(mLevel/5))*5;
	  System.out.println("mTargetWindow ==================      "+mTargetWindow+"   Balllll      "+mTotalBall);
	  M.GameScreen  = M.GAMETARGET;
      
   }
   void UpdateScore()
   {
	   for(int i=0;i<mSboardAni.length;i++)
		   mSboardAni[i].SetScore(0,0,0);
	   NoCount=0;
	   mPrevScore = mNewScore;
	   mSboardAni[0].FinalScore = mTargetScore;
	   mSboardAni[1].FinalScore = mBonusScore;
	   mSboardAni[2].FinalScore = mExtraScore;
	   mSboardAni[3].FinalScore = mPrevScore;
	   mNewScore 				= mTargetScore+mBonusScore+mExtraScore+mPrevScore;
	   mSboardAni[4].FinalScore = mNewScore;
	   
   }
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glClearColor(0,0,0,1);
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
	}
	public void onDrawFrame(GL10 gl) 
	{
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		root.draw(gl);
		if(mStart.adView!=null)
		{
			resumeCounter++;
			if(!mStart.addFree && M.GameScreen != M.GAMEPLAY && M.GameScreen != M.GAMEMENU)
			{
				int inv=mStart.adView.getVisibility();
				if(inv==AdView.INVISIBLE){try{handler.sendEmptyMessage(AdView.VISIBLE);}catch(Exception e){}}
			}
			else
			{
				int inv=mStart.adView.getVisibility();
				if(inv==AdView.VISIBLE){try{handler.sendEmptyMessage(AdView.INVISIBLE);}catch(Exception e){}}
			}
		}
		
		if (mStart.adalaod != null) 
		{
			if (!mStart.addFree && M.GameScreen == M.GAMELOAD){
				int inv = mStart.adalaod.getVisibility();
				if (inv == AdView.INVISIBLE) {try {handler3.sendEmptyMessage(AdView.VISIBLE);} catch (Exception e) {}}
			} 
			else{
				int inv = mStart.adalaod.getVisibility();
				if (inv == AdView.VISIBLE) {try {handler3.sendEmptyMessage(AdView.INVISIBLE);} catch (Exception e) {}
				}
			}
		}
		
		/*AdHouse*/
		if(mStart.adHouse != null)
		{
			if(!mStart.addFree && M.GameScreen == M.GAMEADD)
			{
				int inv = mStart.adHouse.getVisibility();
				if (inv == AdView.INVISIBLE) {try {handler2.sendEmptyMessage(AdView.VISIBLE);} catch (Exception e) {}}
			}
			else
			{
				int inv = mStart.adHouse.getVisibility();
				if (inv == AdView.VISIBLE) {try {handler2.sendEmptyMessage(AdView.INVISIBLE);} catch (Exception e) {}
				}
			}
		}
		/*AdHouse*/
		long dt = System.currentTimeMillis() - startTime;
		if (dt < 33)
			try {
				Thread.sleep(33 - dt);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		startTime = System.currentTimeMillis();
	}
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
	}
	public void onAccelerationChanged(float x, float y, float z) {
		
		//Log.d("----------------=>  "+x,y+"   -----------    "+z);
	}
	public void onShake(float force) {
	}
	SimplePlane add (String ID)
	{
		SimplePlane SP = null;
		Bitmap b = LoadImgfromAsset(ID);
		try
		{
			SP = new SimplePlane((b.getWidth()/M.mMaxX),(b.getHeight()/M.mMaxY));
			SP.loadBitmap(b);// R.drawable.jay
		}catch(Exception e){}
		return SP;
	}
	SimplePlane addRotate(String ID)
	{
		SimplePlane SP = null;
		Bitmap b = LoadImgfromAsset(ID);
		try
		{
			SP = new SimplePlane((b.getWidth()/M.mMaxY),(b.getHeight()/M.mMaxY));
			SP.loadBitmap(b);// R.drawable.jay
		}catch(Exception e){}
		return SP;
	}
	SimplePlane addDouble(String ID)
	{
		SimplePlane SP = null;
		Bitmap b = LoadImgfromAsset(ID);
		try
		{
			SP = new SimplePlane((b.getWidth()/(.5f*M.mMaxX)),(b.getHeight()/(.65f*M.mMaxY)));
			SP.loadBitmap(b);// R.drawable.jay
		}catch(Exception e){}
		return SP;
	}
	SimplePlane addBitmap (Bitmap b)
	{
		SimplePlane SP = null;
		try
		{
			SP = new SimplePlane((b.getWidth()/M.mMaxX),(b.getHeight()/M.mMaxY));
			SP.loadBitmap(b);// R.drawable.jay
		}catch(Exception e){}
		return SP;
	}
	SimplePlane addBitmapdouble(Bitmap b)
	{
		SimplePlane SP = null;
		try
		{
			SP = new SimplePlane((b.getWidth()/(.5f*M.mMaxX)),(b.getHeight()/(.65f*M.mMaxY)));
			SP.loadBitmap(b);// R.drawable.jay
		}catch(Exception e){}
		return SP;
	}
	Bitmap LoadImgfromAsset(String ID)
	{
		try{
			return BitmapFactory.decodeStream(mContext.getAssets().open(ID));}
		catch(Exception e)
		{
			//Log.d(""+ID,"!!!!!!!!!!!!!!!!!!!!!~~~~~~~~~~~~~!!!!!!!!!!!!!!!!!!!!!!!!!"+e.getMessage());
			return null;
		}
	}
	Bitmap resizeImg(Bitmap bitmapOrg,int newWidth,int newHeight)
	{
		 int width = bitmapOrg.getWidth();
		 int height = bitmapOrg.getHeight();
		 float scaleWidth 	= ((float) newWidth) / width;
		 float scaleHeight = ((float) newHeight) / height;
		
		 Matrix matrix = new Matrix();
		 matrix.postScale(scaleWidth, scaleHeight);
		 matrix.postRotate(0);
		 Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0,width, height, matrix, true);
		 Log.d("resizeImg========","newWidth ["+newWidth+"] newHeight ["+newHeight+"]");
		 return resizedBitmap;
	}
	Bitmap FlipHorizontal(Bitmap bitmapOrg)
	{
		Matrix matrix = new Matrix();
		matrix.postScale(-1f, 1f);
		matrix.postRotate(0);
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0,bitmapOrg.getWidth(), bitmapOrg.getHeight(), matrix, true);
		return resizedBitmap;
	}
	void load_Font() {
		mTex_Font = new SimplePlane[10];
		Bitmap b = LoadImgfromAsset("fontstrip.png");
		for(int i = 0; i < mTex_Font.length; i++)
		  mTex_Font[i] = addBitmap(Bitmap.createBitmap(b, i * (b.getWidth()-(32*6))/ mTex_Font.length, 0, (b.getWidth()-(32*6))/mTex_Font.length,b.getHeight(), null, true));
	}
}
