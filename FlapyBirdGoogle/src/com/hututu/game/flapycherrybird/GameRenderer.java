package com.hututu.game.flapycherrybird;

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
	static Context mContext;
	public static Start mStart;
	long startTime = System.currentTimeMillis();
	int resumeCounter =0;
	int mSel = 0;
	
	SimplePlane[] mTex_Font;
	SimplePlane   mTex_Logo,mTex_Pipe[]/*,mTex_PipeFlip*/,mTex_Bird[],mTex_BirdPart[],mTex_BG[];
	SimplePlane   mTex_Splash,mTex_Play[],mTex_Position[],mTex_Option,mTex_PopUp,mTex_Back,mTex_AdFee[],mTex_Sound[],mTex_AboutUs,mTex_AboutTxt;
	SimplePlane   mTex_More,mTex_Like,mTex_HelpTxt,mTex_Retry,mTex_Menu,mTex_BestScore,mTex_CurrentScore,mTex_GameOver;
	SimplePlane   mTex_ColliBG,mTex_Score,mTex_ScoreBox,mTex_Hand,mTex_TapToPlay,mTex_Pause,mTex_PauseTxt,mTex_LoadBar,mTex_Exit,mTex_Pointer;
	SimplePlane   mTex_Join[];
	
	MainActivity mMainActivity;
	public player mPlayer;
	public Obstackle mObst[];
	public Animation mParticle[];
	public Bg mBg[];
	
//	float BG1,BG2;
	float mPopy,mPopvy,mAni;
	int   mBgNo,mCurrentScore,mBestScore;
	boolean isJoin;
	
	private Handler handlerView  = new Handler() {public void handleMessage(Message msg) {mStart.adView.setVisibility(msg.what);}};
	private Handler handlerBtm   = new Handler() {public void handleMessage(Message msg) {mStart.adBtm.setVisibility(msg.what);}};
	private Handler handlerLoad  = new Handler() {public void handleMessage(Message msg) {mStart.adload.setVisibility(msg.what);}};
//	private Handler handlerHouse = new Handler() {public void handleMessage(Message msg) {mStart.adHouse.setVisibility(msg.what);}};//AdHouse
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
			mTex_Logo		 = add("hututugames.png");
			mTex_BG          = new SimplePlane[3];
			for(int i=0;i<mTex_BG.length;i++)
			 mTex_BG[i]      = add("game/bg"+i+".png");           
			mTex_Bird        = new SimplePlane[6];
			for(int i=0;i<mTex_Bird.length;i++)
			mTex_Bird[i]     = addRotate("Bird/"+i+".png");
			
			mTex_Pipe        = new SimplePlane[5];
			for(int i=0;i<mTex_Pipe .length;i++)
			mTex_Pipe[i]     = add("game/pipe"+i+".png");
//			mTex_PipeFlip    = addRotate("pipe.png");
			mTex_BirdPart    = new SimplePlane[2];
			for(int i=0;i<mTex_BirdPart.length;i++)
			mTex_BirdPart[i] = addRotate("Bird/w"+i+".png");
			mTex_ColliBG     = add("game/collibg.png");  
			LoadUi();
			load_Font();
		    InitGameObj();
		    M.loadSound(mContext);
		}catch(Exception e){}
	}
	void LoadUi()
	{
		mTex_Splash 	  =	add("ui/splash.png");
		mTex_Play   	  = new SimplePlane[2];
		mTex_Play[0]   	  = add("ui/play-icon.png");  
		mTex_Play[1]   	  = add("ui/play.png");
		mTex_Position     = new SimplePlane[2];
	    mTex_Position[0]  = add("ui/high-score.png"); 	
	    mTex_Position[1]  = add("ui/highscore-button.png");
	    mTex_Option       = add("ui/option.png");        
	    mTex_PopUp        = add("ui/board.png");
	    mTex_Back         = add("ui/back.png");
	    mTex_AdFee        = new SimplePlane[2];
	    mTex_AdFee[0]     = add("ui/ads-free-logo.png");
	    mTex_AdFee[1]     = add("ui/ads-free.png");
	    mTex_Sound        = new SimplePlane[2];
	    mTex_Sound[0]     = add("ui/sound-on.png");
	    mTex_Sound[1]     = add("ui/sound-off.png");
	    mTex_AboutUs      = add("ui/about-btn.png");
	    mTex_AboutTxt     = add("ui/about-us.png");
	    mTex_More         = add("ui/more.png");
	    mTex_Like         = add("ui/like.png");
	    mTex_HelpTxt      = add("ui/help-board.png");
	    mTex_Retry        = add("ui/retry.png");
	    mTex_Menu         = add("ui/menu.png"); 
	    mTex_BestScore    = add("ui/best-score.png");
	    mTex_CurrentScore = add("ui/current-Score.png");
	    mTex_GameOver     = add("ui/game-over.png");
	    mTex_Score        = add("ui/score.png");
	    mTex_ScoreBox     = add("ui/score-box.png");
	    mTex_TapToPlay    = add("ui/tap-to-play.png");
	    mTex_Hand	      = add("ui/touch-hand.png");
	    mTex_Pause        = add("ui/paused-icon.png");
	    mTex_PauseTxt     = add("ui/game-paused.png");
	    mTex_LoadBar	  = add("bar.png");
	    mTex_Exit	      = add("exit.png");
	    mTex_Pointer	  = add("pointer.png");
	    mTex_Join         = new SimplePlane[2]; 
	    mTex_Join[0]      = add("join.png");
	    mTex_Join[1]      = add("join_text.png");
	} 
	void InitGameObj()
	{
		int i;
		mPlayer = new player();
		mObst   = new Obstackle[3];
	 for(i=0;i<mObst.length;i++)
		 mObst[i] = new Obstackle();
		
		 mParticle  = new Animation[4];
	  for(i=0;i<mParticle.length;i++)
	    mParticle[i] = new Animation(this);
	    mBg          = new Bg[2];
	    for(i=0;i<mBg.length;i++)
	    	mBg[i]   = new Bg();
			
	}
	void GameReset()
	{
		mCurrentScore =0;
		mBgNo  = M.mRand.nextInt(3);
		mPlayer.set(-.4f,0,0);
		for(int i=0;i<mObst.length;i++)
		 mObst[i].Set(1.25f+i*.8f,M.randomRange(-1.60f,-.70f),M.mRand.nextInt(5));
		for(int i=0;i<mBg.length;i++)
		  mBg[i].Set(0+i*mTex_BG[0].width());
		try {handler.sendEmptyMessage(0);} catch (Exception e) {}
	} 
	private Handler handler = new Handler() {public void handleMessage(Message msg) {GameRenderer.mStart.load();}};
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
		if(mStart.adView!=null && resumeCounter>40)
		{
			if(!mStart.addFree && (M.GameScreen != M.GAMELOAD && M.GameScreen != M.GAMEADD && M.GameScreen != M.GAMEMENU && M.GameScreen != M.GAMEPLAY && M.GameScreen != M.GAMESPLASH))
			{
				int inv=mStart.adView.getVisibility();
				if(inv==AdView.GONE){try{handlerView.sendEmptyMessage(AdView.VISIBLE);}catch(Exception e){}}
			}
			else
			{
				int inv=mStart.adView.getVisibility();
				if(inv==AdView.VISIBLE){try{handlerView.sendEmptyMessage(AdView.GONE);}catch(Exception e){}}
			}
		}
		if(mStart.adBtm != null && resumeCounter>40)
		{
			if(!mStart.addFree && (M.GameScreen == M.GAMEMENU ||  M.GameScreen == M.GAMESPLASH || M.GameScreen == M.GAMEPLAY))
			{
				int inv = mStart.adBtm.getVisibility();
				if(inv == AdView.GONE){try{handlerBtm.sendEmptyMessage(AdView.VISIBLE);}catch(Exception e){}}
			}
			else
			{
				int inv = mStart.adBtm.getVisibility();
				if(inv == AdView.VISIBLE){try{handlerBtm.sendEmptyMessage(AdView.GONE);}catch(Exception e){}}
			}
		}
		/*adBig*/
		if(mStart.adload != null && resumeCounter>40)
		{
			if(!mStart.addFree && M.GameScreen == M.GAMELOAD) 
			{
				int inv = mStart.adload.getVisibility();
				if(inv == AdView.GONE) {try {handlerLoad.sendEmptyMessage(AdView.VISIBLE);} catch (Exception e) {}}
			}
			else{
				int inv = mStart.adload.getVisibility();
				if (inv == AdView.VISIBLE) {try {handlerLoad.sendEmptyMessage(AdView.GONE);} catch (Exception e) {}
				}
			}
		}
		/*adBig*/
		
	   resumeCounter++;
		if(resumeCounter>500000)
		  resumeCounter =0;
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
			SP = new SimplePlane((b.getWidth()/(M.mMaxX)),(b.getHeight()/(.65f*M.mMaxY)));
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
