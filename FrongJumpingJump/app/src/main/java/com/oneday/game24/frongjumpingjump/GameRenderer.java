package com.oneday.game24.frongjumpingjump;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
@SuppressLint("HandlerLeak")
public class GameRenderer implements Renderer 
{
	final Group root;
	static Context mContext;
	public static Start mStart;
	long startTime = System.currentTimeMillis();
	int resumeCounter =0;
	int mSel = 0;
	
	SimplePlane[]   mTex_Font,mTex_BG,mTex_bgDark,mTex_Wave,mTex_SplashFrog,mTex_Menu,mTex_Setting,mTex_SettingIcn[];
	SimplePlane 	mTex_Logo,mTex_SplashTxt,mTex_Board,mTex_Drum,mTex_SmallBoard,mTex_Google,mTex_FB,mTex_HelpBtn,mTex_Home,mTex_AbtBtn;
	SimplePlane     mTex_Help,mTex_Abt,mTex_More,mTex_AdFree,mTex_BubbleIcn, mTex_BuyBtn,mTex_GameOver,mTex_GamePause,mTex_ScoreBoard;
	SimplePlane     mTex_LeaderBoard,mTex_Retry,mTex_Play,mTex_ChangeGame,mTex_Makdi,mTex_WorldBoard,mTex_WorldIcn[],mTex_WorldTxt[],mTex_Mode[][];
	SimplePlane     mTex_ModeBoard,mTex_ScoreBox,mTex_Pause,mTex_LoadBar,mTex_Exit,mTex_Pointer,mTex_JoinText,mTex_JoinBtn,mTex_Cong;
	SimplePlane     mTex_Ani[],mTex_Dot[]; 
	

	SimplePlane mTex_Step[], mTex_BOne;
	SimplePlane mTex_Frog[], mTex_BTwo,mTex_WaterAni[],mTex_Ripple;
	
	long mGameTime = 0;
	
	int mGameMode=0,mGameType,mScore,mTargetTile,mTileCnt;
	int mWinCnt=0,mAdCount;
	
	float mAni=.01f,mMenuAni=.005f,mJoinAnim;
	float BG1,BG2,move;
	float plyX, plyY, plyVy;
	float mTargetTime,mTimeCnt;
	float mBestScore[][];
	float[] BGDark = new float [2],BGWave = new float[2];
	
	byte st,Space,jumpCount,mCharAni,wCount;
	
	boolean isStart=false,isJoin;
	
	Step[] mStep;
	Bubble mBubble[];
	StarAnimation	mStarAni[];
	private Handler handlerAdBanner = new Handler() {public void handleMessage(Message msg) {}};
	private Handler handlerAdRect   = new Handler() {public void handleMessage(Message msg) {}};
	
//	private Handler LoadAD = new Handler() {public void handleMessage(Message msg) {GameRenderer.mStart.loadInterstitial();}};
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
			System.gc();
			mTex_LoadBar    = add("bar.png");
		    mTex_Exit	    = add("exit.png");
		    mTex_Pointer    = add("pointer.png");
		    mTex_JoinText   = add("join_text.png");
		    mTex_JoinBtn    = add("join.png");
			mTex_Logo   = add("logo.png");
			mTex_BG     = new SimplePlane[3];
			mTex_bgDark = new SimplePlane[3];
			mTex_Wave   = new SimplePlane[3]; 
			for(int i=0;i<mTex_BG.length;i++)
			{
			  mTex_BG[i]     = add("bg/bg"+i+".png");
			  mTex_bgDark[i] = add("bg/bgdark"+i+".png");
			  mTex_Wave[i]   = add("bg/wave"+i+".png");       
			}
			
			mTex_Step	= new SimplePlane[7];
			for(int i=0;i<mTex_Step.length;i++)
				mTex_Step[i] = add("bg/step"+i+".png");
			mTex_BOne = add("one_jump.png");
			mTex_BTwo = add("two_jump.png");
			
			mTex_Frog = new SimplePlane[8];
			for (int i = 0; i < mTex_Frog.length; i++){
				mTex_Frog[i] = add("frog/small"+i+".png");
			}
			mTex_WaterAni	= new SimplePlane[8];
			Bitmap b = GameRenderer.LoadImgfromAsset("water_splash.png");
			for(int i=0;i<8;i++){
				mTex_WaterAni[i]= addBitmap(Bitmap.createBitmap(b,i*b.getWidth()/8, 0, b.getWidth()/8,b.getHeight(), null, true));
			}
			b.recycle();
			mTex_Ripple = add("bg/water_ripple.png");
			LoadUi();
			loadFont();
			InitGameObj();
		    M.loadSound(mContext);
		}catch(Exception e){}
	}
	void LoadUi()
	{
		mTex_SplashTxt = add("ui/splash_text.png");
		mTex_Board     = add("ui/big_bord.png");  
		mTex_SplashFrog = new SimplePlane[5];
		for(int i=0;i<mTex_SplashFrog.length;i++)
	 	 mTex_SplashFrog[i] = add("frog/big"+i+".png");
		mTex_Drum        = add("drum.png");
		mTex_Menu        = new SimplePlane[3];
		for(int i=0;i<mTex_Menu.length;i++)
		 mTex_Menu[i]    = add("ui/menu"+i+".png");
		mTex_SmallBoard  = add("ui/small_board.png");
		mTex_Google      = add("ui/g+.png");
		mTex_Setting     = new SimplePlane[3];
		for(int i=0;i<mTex_Setting.length;i++)
		{
		  mTex_Setting[i] = add("ui/setting"+i+".png");
		}
		
		mTex_SettingIcn  = new SimplePlane[3][2];
		mTex_SettingIcn[0][0] = add("ui/settingicn00"+".png");
		for(int i=0;i<mTex_SettingIcn.length;i++)
		{
		  for(int j=0;j<mTex_SettingIcn[0].length;j++)
		  {
			mTex_SettingIcn[i][j] = add(("ui/settingicn"+i)+j+".png");
		  }
		}
		mTex_FB          = add("ui/fb.png");
		mTex_HelpBtn     = add("ui/help_btn.png");
		mTex_Home        = add("ui/home.png");
		mTex_AbtBtn      = add("ui/about.png");
		mTex_Help        = add("ui/help.png");
		mTex_Abt         = add("ui/about_text.png");
		mTex_More        = add("ui/more.png");
		mTex_AdFree      = add("ui/buy_ads.png");
		mTex_BubbleIcn   = add("ui/bubble_icon.png");
		mTex_BuyBtn = add("ui/buy_btn.png");
		mTex_GameOver    = addRotate("ui/game-over.png");
		mTex_GamePause   = addRotate("ui/gamepaused.png"); 
		mTex_ScoreBoard  = add("ui/scoree.png");
		mTex_LeaderBoard = add("ui/leader.png");
		mTex_Retry       = add("ui/retry.png");
		mTex_Play        = add("ui/play.png");
		mTex_ChangeGame  = add("ui/change-game.png");
		mTex_Makdi       = add("ui/spider_web.png");
		mTex_WorldBoard  = add("ui/world_bord.png");
		mTex_WorldIcn    = new SimplePlane[3];
		mTex_WorldTxt    = new SimplePlane[3];
		for(int i=0;i<mTex_WorldIcn.length;i++)
		{
			mTex_WorldIcn[i] = add("ui/worldIcn"+i+".png");
			mTex_WorldTxt[i] = add("ui/worldtxt"+i+".png");
		}
		mTex_Mode        = new SimplePlane[2][3];
		for(int i=0;i<2;i++)
		{
 		  for(int j=0;j<3;j++)
			mTex_Mode[i][j] = add(("ui/mode"+i)+j+".png"); 	
		} 
		mTex_ModeBoard = add("ui/world_bord_small.png");
		mTex_ScoreBox  = add("ui/score_box.png");
		mTex_Pause     = add("ui/pause_btn.png");
		mTex_Cong      = addRotate("ui/congratulation.png");
	
		mTex_Dot      = new SimplePlane[2];
		mTex_Dot[0]   = add("ui/dot0.png");   
		mTex_Dot[1]   = add("ui/dot1.png");
		mTex_Ani      = new SimplePlane[10];
		for(int i=0;i<mTex_Ani.length;i++)
		 mTex_Ani[i]   = add("drop/"+i+".png");
 	}
	void loadFont() {
		mTex_Font  	 = new SimplePlane[13];
		Bitmap b = LoadImgfromAsset("font.png");
		for(int i = 0; i < mTex_Font.length; i++)
		{
		    mTex_Font[i]  = addBitmap(Bitmap.createBitmap(b,i*b.getWidth()/16,0,b.getWidth()/16,b.getHeight(), null, true));
		}
	}
	void InitGameObj()
	{
		mStarAni     = new StarAnimation[50];
	    for(int i=0;i< mStarAni.length;i++)
		  mStarAni[i]   = new StarAnimation();
	    
		mStep = new Step[10];
		for(int i = 0; i < mStep.length; i++)
		  mStep[i] = new Step(0, true);
		mBestScore = new float[3][];
	    for(int i=0;i<2;i++)
     	  mBestScore[i] = new float[3];
	    
	    mBestScore[2]   = new float[1];

	    mBubble = new Bubble[30];
	    for(int i = 0; i< mBubble.length; i++)
	    {
	      mBubble[i] = new Bubble();
	    }
	    
	}
	void resetTarget()
	{
		mTargetTile = 0;
		mTileCnt    = 0;
		mTargetTime = 0;
		mTimeCnt    = 0;
		mWinCnt  	= 0;
	}
	void GameReset(int _type)
	{ 
		mGameType = _type-1;
		root.resetBubble();
//		try{LoadAD.sendEmptyMessage(0);} catch (Exception e){}
//		mStart.loadInter();
		mGameTime = System.currentTimeMillis();
		mScore = 0;
		mWinCnt= 0; 
		isStart = false;
		resetTarget();
		set();
	    BGDark[0] = 0;
	    BGDark[1] = mTex_bgDark[0].width()*.833f;
	    
	    BGWave[0] = 0;
	    BGWave[1] = mTex_Wave[0].width()*.833f;
		switch(mGameMode)
		{
		  case 0: //Arcade
				switch(mGameType)
				{
				  case 0: 
					  mTargetTile = 50;//50 Tile
					  break;
				  case 1:
					  mTargetTile = 100;//100 Tile
					  break;
				  case 2:
					  mTargetTile = 500;//500 Tile
					  break;
				}
			break;
		  case 1://Time
			    switch(mGameType)
				{
			      case 0:
			    	mTargetTime =30.0f;//Sec
			    	break;
			      case 1:
			    	mTargetTime =60.0f;//Sec
			    	break;
			      case 2:
			    	mTargetTime =120.0f;//Sec
			    	break;
				}
			break;
		}
//		if(GameRenderer.mStart.addFree)
//		{
			M.BGPlay(GameRenderer.mContext,R.raw.gameplay);
			M.GameScreen = M.GAMEPLAY;
//		}
//		else
//		{
//			M.GameScreen = M.GAMELOADING;
//			root.Counter=0;
//		}
	} 
	void set() {
		jumpCount = (byte)mTex_WaterAni.length;
		plyX = -.30f;
		plyY = -.24f;
		plyVy = 0;
		BG1 = 0;
		BG2 = mTex_BG[0].width()*.833f;
		move = 0.01f;
		Space = 5;
		st = 0;
		for(int i = 0; i<mStep.length; i++)
		{
			mStep[i].set(-.3f+i*.24f,true,0);
			if(wCount%6==0)
				mStep[i].Watch = true;
			if(Space<= 0)
			{
				Space = (byte)(M.mRand.nextInt(5) + 2);
				mStep[i].isOn  = false;
				mStep[i].Watch = false;
			}
			wCount++;
			Space--;
		}
		for(int i = 0; i< mBubble.length; i++)
		{
		    mBubble[i].set(-.3f+((i%mStep.length)*.24f),0);
		}
		mStep[0].Watch = false;
		mCharAni = 10;
	}
//	sudo apt-get install flashplugin-installer 
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
 	    gl.glClearColor(50/255.0f,52/255.0f,100/255.0f,1);
		gl.glLoadIdentity();
		root.draw(gl);
		if(M.GameScreen!=M.GAMEWORLD)
		  gl.glDisable(GL10.GL_SCISSOR_TEST);
		//Banner AD
		resumeCounter++;
		if(resumeCounter>500000)
			resumeCounter=0;
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
			SP.loadBitmap(b);// R.raw.jay
		}catch(Exception e){System.out.println("Image       "+ID);}
		return SP;
	}
	SimplePlane addBig (String ID)
	{
		SimplePlane SP = null;
		Bitmap b = LoadImgfromAsset(ID);
		try
		{
			SP = new SimplePlane(((b.getWidth()+20f)/(M.mMaxX)),(b.getHeight()/(M.mMaxY)));
			SP.loadBitmap(b);// R.raw.jay
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
			SP.loadBitmap(b);// R.raw.jay
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
			SP.loadBitmap(b);// R.raw.jay
		}catch(Exception e){}
		return SP;
	}
	public static SimplePlane addBitmap (Bitmap b)
	{
		SimplePlane SP = null;
		try
		{
			SP = new SimplePlane((b.getWidth()/M.mMaxX),(b.getHeight()/M.mMaxY));
			SP.loadBitmap(b);// R.raw.jay
		}catch(Exception e){}
		return SP;
	}
	SimplePlane addBitmapR(Bitmap b)
	{
		SimplePlane SP = null;
		try
		{
			SP = new SimplePlane((b.getWidth()/M.mMaxY),(b.getHeight()/M.mMaxY));
			SP.loadBitmap(b);// R.raw.jay
		}catch(Exception e){}
		return SP;
	}
	public static Bitmap LoadImgfromAsset(String ID)
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
	Bitmap FlipHorizontal(Bitmap bitmapOrg,int type)
	{
		Matrix matrix = new Matrix();
		if(type ==0) // Verticle
		 matrix.postScale(1f,-1f);
		if(type ==1) //Horizontal
	  	 matrix.postScale(-1f, 1f);
		matrix.postRotate(0);
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0,bitmapOrg.getWidth(), bitmapOrg.getHeight(), matrix, true);
		return resizedBitmap;
	}

}

