package com.onedaygames24.shoot2bottle;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.google.android.gms.ads.AdView;

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
	Context mContext;
	public static Start mStart;
	boolean addFree;
	int resumeCounter 	= 0;
	int BreakingBottle 	= 0;
	int TargetBottle 	= 0;
	int HighScore 		= 0;
	int Level 			= 0;
	int mSel 			= 0;
	int throughtC 		= 0;
	int throughtL 		= 0;
	int incCounter 		= 1;
	int starcounter	 	= 0;
	boolean isPause		= false;
	long startTime = System.currentTimeMillis();
	long startGTime= System.currentTimeMillis();
	
	Stone mStone[];
	Bottle mBottle[];
	Animation[] mAnimation,mTitle;
	Animation[] mStars;
	
	SimplePlane[] mTex_Yog,mTex_BottleD,mTex_Break,mTex_Stone,mTex_BG,mTex_Title;
	SimplePlane[] mTex_Particle,mTex_Font,mTex_Star,mTex_HighScore,mTex_Submit,mTex_FontB;
	SimplePlane[] mTex_Contine,mTex_Help,mTex_Info,mTex_Newgame,mTex_Nextlvl,/*mTex_Rateus,*/mTex_Retry,mTex_Menu;
	SimplePlane mTex_BgBar,mTex_Color,mTex_Shield,mTex_SoundOff,mTex_Spark,mTex_Logo;
	SimplePlane mTex_Splash,/*mTex_Shadow,*/mTex_HelpScr,mTex_Back,mTex_AllBack,mTex_InfoScr;
	SimplePlane mTex_LevelCleared,mTex_LevelFailed,mTex_ScoreBoard,mTex_TimesUp,mTex_Exit_icon;
	SimplePlane   mTex_Pointer, mTex_Hightbar,mTex_Skip; //AdHouse
	
	public GameRenderer(Context context) 
	{
		mContext = context;
		mStart	= (Start)mContext;
		root = new Group(this);
		init();
	}
	void init()
	{
		int i = 0;
		try
		{
			mTex_Pointer = add("pointer.png");//AdHouse
			mTex_Hightbar = add("hightbar0.png");//AdHouse
			mTex_Skip = add("exit_icon.png");//AdHouse
			mTex_Exit_icon		= add("exit_icon.png");
			mTex_BG 			= new SimplePlane[2];
			mTex_BG[0]			= add("bg0.png");
			mTex_BG[1]			= add("bg1.png");
			mTex_Shield			= add("shieled.png");
			mTex_Color			= add("Avinyo.jpg"); 
			mTex_BgBar			= add("bgcolorbar.png");
			mTex_SoundOff 		= add("sondoff.png");
			mTex_Logo 			= add("logo.png");
			mTex_Splash			= add("splash.jpg"); 
			mTex_Spark			= add("spark.png");
//			mTex_Shadow 		= add("bottleshadow.png");
			mTex_HelpScr		= add("help.jpg");
			mTex_Back 			= add("backbutton.png");
			mTex_AllBack 		= add("innerbg.jpg");
			mTex_InfoScr 		= add("info.png");
			mTex_LevelCleared	= add("levelcleared.png");
			mTex_LevelFailed	= add("levelfailed.png");
			mTex_ScoreBoard		= add("score-board.png");
			mTex_TimesUp 		= add("timeup.png");
			mTex_Contine		= new SimplePlane[3];
			mTex_Contine[0]		= add("menufont/contine0.png");
			mTex_Contine[1]		= add("menufont/contine1.png");
			mTex_Contine[2]		= add("menufont/contine2.png");
			mTex_Help			= new SimplePlane[3];
			mTex_Help[0]		= add("menufont/help0.png");
			mTex_Help[1]		= add("menufont/help1.png");
			mTex_Help[2]		= add("menufont/help2.png");
			mTex_Info			= new SimplePlane[3];
			mTex_Info[0]		= add("menufont/info0.png");
			mTex_Info[1]		= add("menufont/info1.png");
			mTex_Info[2]		= add("menufont/info2.png");
			mTex_Newgame		= new SimplePlane[3];
			mTex_Newgame[0]		= add("menufont/newgame0.png");
			mTex_Newgame[1]		= add("menufont/newgame1.png");
			mTex_Newgame[2]		= add("menufont/newgame2.png");
			mTex_Nextlvl		= new SimplePlane[3];
			mTex_Nextlvl[0]		= add("menufont/nextlevel0.png");
			mTex_Nextlvl[1]		= add("menufont/nextlevel1.png");
			mTex_Nextlvl[2]		= add("menufont/nextlevel2.png");
//			mTex_Rateus			= new SimplePlane[3];
//			mTex_Rateus[0]		= add("menufont/rateus0.png");
//			mTex_Rateus[1]		= add("menufont/rateus1.png");
//			mTex_Rateus[2]		= add("menufont/rateus2.png");
			mTex_Retry			= new SimplePlane[3];
			mTex_Retry[0]		= add("menufont/retry0.png");
			mTex_Retry[1]		= add("menufont/retry1.png");
			mTex_Retry[2]		= add("menufont/retry2.png");
			mTex_Menu			= new SimplePlane[3];
			mTex_Menu[0]		= add("menufont/menu0.png");
			mTex_Menu[1]		= add("menufont/menu1.png");
			mTex_Menu[2]		= add("menufont/menu2.png");
			mTex_HighScore		= new SimplePlane[3];
			mTex_HighScore[0]	= add("menufont/highscore0.png");
			mTex_HighScore[1]	= add("menufont/highscore1.png");
			mTex_HighScore[2]	= add("menufont/highscore2.png");
			mTex_Submit			= new SimplePlane[3];
			mTex_Submit[0]		= add("menufont/submitde.png");
			mTex_Submit[1]		= add("menufont/submitse.png");
			
			
			mTex_Yog			= new SimplePlane[18];
			Bitmap b 			= LoadImgfromAsset("bottlestrip0.png");
			for(i = 0;i<mTex_Yog.length;i++)
				mTex_Yog[i] 	= addBitmap(Bitmap.createBitmap(b, i*b.getWidth()/mTex_Yog.length,0,b.getWidth()/mTex_Yog.length,b.getHeight(),null, true));
			
			b 					= LoadImgfromAsset("bottlestrip2.png");
			mTex_Break			= new SimplePlane[18];
			for(i = 0;i<mTex_Break.length;i++)
				mTex_Break[i] 	= addBitmap(Bitmap.createBitmap(b, i*b.getWidth()/mTex_Yog.length,0,b.getWidth()/mTex_Yog.length,b.getHeight(),null, true));
			
			mTex_BottleD		= new SimplePlane[18];
			b					= LoadImgfromAsset("bottlestrip1.png");
			for(i = 0;i<mTex_BottleD.length;i++)
				mTex_BottleD[i] 	= addBitmap(Bitmap.createBitmap(b, i*b.getWidth()/mTex_BottleD.length,0,b.getWidth()/mTex_BottleD.length,b.getHeight(),null, true));
			
			
			mTex_Star			= new SimplePlane[4];
			for(i=0;i<mTex_Star.length;i++)
				mTex_Star[i]	= add("star/star"+i+".png");
			
			int k =0;
			mTex_Particle		= new SimplePlane[234];
			for(i = 0;i<18;i++)
			{
				for(int j = 0;j<13;j++)
				{
					mTex_Particle[k] = add("partical/"+i+"/"+j+".png");
					k++;
				}
			}
			mTex_Title		= new SimplePlane[7];
			for(i = 0;i<mTex_Title.length;i++)
			{
				mTex_Title[i] = add("new/"+i+".png");
			}
			k=0;
			b = LoadImgfromAsset("paper_ball.png");
			mTex_Stone			= new SimplePlane[16];
			for(i = 0;i<4&&k<mTex_Stone.length;i++)
			{
				for(int j = 0;j<5&&k<mTex_Stone.length;j++)
				{
					mTex_Stone[k] = addBitmap(Bitmap.createBitmap(b, j*b.getWidth()/5,i*b.getHeight()/4,b.getWidth()/5,b.getHeight()/4,null, true));
					k++;
				}
			}
			mStone = new Stone[20];
			for(i =0;i<mStone.length;i++)
				mStone[i] = new Stone();	
			
			mBottle= new Bottle[6];
			for(i =0;i<mBottle.length;i++)
				mBottle[i] = new Bottle();	
			
			
			mAnimation= new Animation[100];
			for(i =0;i<mAnimation.length;i++)
				mAnimation[i] = new Animation();
			
			mTitle= new Animation[5];
			for(i =0;i<mTitle.length;i++)
				mTitle[i] = new Animation();	
			
			
			mStars= new Animation[40];
			for(i =0;i<mStars.length;i++)
				mStars[i] = new Animation();	
			
			font();
			gameReset();
		}catch(Exception e){}
		
	}
	void gameReset()
	{
		int i =0;
		switch (Level) {
		case 0:
		case 1:
			Level = 1;
			TargetBottle = 20;
			BreakingBottle = 0;
			break;
		case 2:
			TargetBottle = 40;
			break;
		case 3:
			TargetBottle = 80;
			break;
		default:
			if(BreakingBottle < (TargetBottle -10))
				TargetBottle = BreakingBottle+50;
			else
				TargetBottle+=60;
			break;
		}
		resumeCounter = 0;
		M.GameScreen = M.GAMEPLAY;
		for(i =0;i<mStone.length;i++)
			mStone[i].set(-100,-100,1,0,0, 0);
		
		for(i =0;i<mBottle.length;i++)
			mBottle[i].set(-.75f+(i*.3f), .38f,M.mRand.nextInt(mTex_Yog.length));
		
		for(i =0;i<mAnimation.length;i++)
			mAnimation[i].set(-110, -100, 0,0, 0);
		
		for(i =0;i<mTitle.length;i++)
			mTitle[i].set(110, 100, 0,0, 0);
		
		for(i =0;i<mStars.length;i++)
			mStars[i].set((M.mRand.nextBoolean()?M.mRand.nextFloat():-M.mRand.nextFloat())%.8f, 
					(M.mRand.nextInt(20)+30)/100f, 0,0, M.mRand.nextInt(4));
		
		startGTime = System.currentTimeMillis();
		incCounter =1;
		throughtC = 0;
		throughtL = 0;
		isPause = false;
		root.scal = .5f;
		mStart.loadInter();//SmartHandler();
	}
	
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
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
//		if(mStart.adView!=null)
//		{
//			resumeCounter++;
//			if(resumeCounter>50 && M.GameScreen != M.GAMEADD && M.GameScreen != M.GAMEADD1)
//			{
//				int inv=mStart.adView.getVisibility();
//				if(inv==AdView.GONE){try{handler.sendEmptyMessage(AdView.VISIBLE);}catch(Exception e){}}
//			}
//			else
//			{
//				int inv=mStart.adView.getVisibility();
//				if(inv==AdView.VISIBLE){try{handler.sendEmptyMessage(AdView.GONE);}catch(Exception e){}}
//			}
//		}
		
//		if(mStart.adViewBig!=null)
//		{
//			resumeCounter++;
//			if(M.GameScreen == M.GAMEADD)
//			{
//				int inv=mStart.adViewBig.getVisibility();
//				if(inv==AdView.GONE){try{handlerB.sendEmptyMessage(AdView.VISIBLE);}catch(Exception e){}}
//			}
//			else
//			{
//				int inv=mStart.adViewBig.getVisibility();
//				if(inv==AdView.VISIBLE){try{handlerB.sendEmptyMessage(AdView.GONE);}catch(Exception e){}}
//			}
//		}
		/*AdHouse*/
//		if (mStart.adHouse != null) {
//			if (M.GameScreen == M.GAMEADD1) {
//				int inv = mStart.adHouse.getVisibility();
//				if (inv == AdView.GONE) {try {handler2.sendEmptyMessage(AdView.VISIBLE);} catch (Exception e) {}}
//			} else {
//				int inv = mStart.adHouse.getVisibility();
//				if (inv == AdView.VISIBLE) {try {handler2.sendEmptyMessage(AdView.GONE);} catch (Exception e) {}
//				}
//			}
//		}
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
//	public Handler getHandler() {
//		return handler;
//	}
//	public void setHandler(Handler handler) {
//		this.handler = handler;
//	}
	void font()
	{
		mTex_Font	= new SimplePlane[10];
		Bitmap b = LoadImgfromAsset("fontstrip.png");
		for(int i = 0;i<mTex_Font.length;i++)
			mTex_Font[i] = addBitmap(Bitmap.createBitmap(b, i*b.getWidth()/mTex_Font.length, 0,b.getWidth()/mTex_Font.length, b.getHeight(),null, true));
		fontB();
	}
	void fontB()
	{
		mTex_FontB	= new SimplePlane[10];
		Bitmap b = LoadImgfromAsset("new/numberfont.png");
		for(int i = 0;i<mTex_FontB.length;i++)
			mTex_FontB[i] = addBitmap(Bitmap.createBitmap(b, i*b.getWidth()/mTex_FontB.length, 0,b.getWidth()/mTex_FontB.length, b.getHeight(),null, true));
	}
	
}
