package com.hututu.game.reversegravity;

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

public class GameRenderer implements Renderer  , AccelerometerListener
{
	final Group root;
	Context mContext;
	public static Start mStart;
	long startTime = System.currentTimeMillis();
	int resumeCounter =0;
	int mSel = 0;
	float Ax;
	
	SimplePlane[] 	mTex_Ball,mTex_Object,mTex_BG,mTex_Gift,mTex_Point,mTex_Smok,mTex_Speed,mTex_Life,mTex_Font;
	SimplePlane		mTex_Pad,mTex_StarBG;
	
	SimplePlane[] mTex_SoundBtn,mTex_Submitt;
	
	SimplePlane mTex_Splash,mTex_NewGameBtn,mTex_HelpBtn,mTex_AbtUsBtn,mTex_HighScoreBtn,/*mTex_RateUsBtn,*/mTex_ShareBtn;
	SimplePlane mTex_Exit,mTex_Selector,mTex_SmallSel,mTex_Logo,mTex_HututTitle,mTex_MenuBtn,mTex_Continue,mTex_Replay;
	SimplePlane mTex_PopUp,mTex_GamePauseTitle,mTex_GameOverTitle,mTex_NewScore,mTex_ScoreBox,mTex_BestScore,mTex_Score;
	SimplePlane mTex_Pause,mTex_HelpTitle,mTex_HelpTxt,mTex_AbtTitle,mTex_AbtUsTxt,mTex_backBtn;
	SimplePlane mTex_Level,mTex_LevelUp;
	SimplePlane   mTex_Pointer, mTex_Hightbar,mTex_Skip; //AdHouse
	
	Player 		mPlayer;
	Pad[]		mPad;
	Object[]	mObject;
	Point[]		mPoint;
	Animation[] mAni;
	
	boolean mContinue = false;
	
	int mScore	= 0;
	int mHScore	= 0;
	int mSpeedC	= 0;
	int mSpeedN	= 0;
	int mLevel  = 0;
	int hss		= 0;
	int levelUp = 0;
	float mBGY = 0;
	float mBGSY1 = 0;
	float mBGSY2 = 0;
	
	
	private Handler handler = new Handler() {public void handleMessage(Message msg) {mStart.adView.setVisibility(msg.what);}};
	private Handler handler1= new Handler() {public void handleMessage(Message msg) {mStart.adRect.setVisibility(msg.what);}};
	private Handler handler2 = new Handler() {public void handleMessage(Message msg) {mStart.adHouse.setVisibility(msg.what);}};//AdHouse
	public GameRenderer(Context context) 
	{
		if(AccelerometerManager.isSupported())
			AccelerometerManager.startListening(this);
		mContext = context;
		mStart	= (Start)mContext;
		root = new Group(this);
		init();
	}
	void init()
	{
		int i =0;
		try
		{
			mTex_Pointer = add("pointer.png");//AdHouse
			mTex_Hightbar = add("hightbar0.png");//AdHouse
			mTex_Skip = add("exit_icon.png");//AdHouse
			mTex_Level				= add("ui/level_text.png");
			mTex_LevelUp			= add("ui/levelup.png");
			mTex_Pause				= add("ui/pause.png");
			mTex_PopUp				= add("ui/bigpopup.png");
			mTex_GamePauseTitle		= add("ui/gamepaused.png");
			mTex_GameOverTitle		= add("ui/gameover.png");
			mTex_NewScore			= add("ui/newscore_text.png");
			mTex_ScoreBox			= add("ui/scorebox.png");
			mTex_BestScore			= add("ui/bestscore_text.png");
			mTex_Score				= add("ui/score_text.png");
			mTex_HututTitle			= add("ui/hututu_button.png");
			mTex_MenuBtn			= add("ui/menu.png");
			mTex_Continue			= add("ui/continue.png");
			mTex_Replay				= add("ui/retry.png");
			mTex_HelpTitle			= add("ui/help.png");
			mTex_HelpTxt			= add("ui/helptext.png");
			mTex_AbtTitle			= add("ui/aboutus.png");
			mTex_AbtUsTxt			= add("ui/abouttext.png");
			mTex_backBtn			= add("ui/back.png");
			
			
			mTex_Submitt			= new SimplePlane[2];
			mTex_Submitt[0]			= add("ui/submitde.png");
			mTex_Submitt[1]			= add("ui/submitse.png");
			
			
			mTex_Logo				= add("hututugames.png");
			mTex_Splash				= add("ui/splash.jpg");
			mTex_NewGameBtn			= add("ui/ngame_button.png");
			mTex_HelpBtn			= add("ui/help_button.png");
			mTex_AbtUsBtn			= add("ui/about_button.png");
			mTex_HighScoreBtn		= add("ui/hscore_button.png");
//			mTex_RateUsBtn			= add("ui/rateus.png");
			mTex_ShareBtn			= add("ui/share.png");
			mTex_Exit				= add("ui/exit.png");
			mTex_Selector			= add("ui/bigselection.png");
			mTex_SmallSel			= add("ui/smallselection.png");
			
			mTex_SoundBtn			= new SimplePlane[2];
			mTex_SoundBtn[0]		= add("ui/soundon.png");
			mTex_SoundBtn[1]		= add("ui/soundoff.png");
			
			
			mTex_Ball			= new SimplePlane[5];
			for(i=0;i<mTex_Ball.length;i++)
			{
				mTex_Ball[i]	= add("ball/"+i+".png");
			}
			mTex_Pad			= add("brick.png");
			mTex_StarBG			= add("stars2.png");
			
			
			mTex_Gift			= new SimplePlane[8];
			for(i=0;i<mTex_Gift.length;i++){
				mTex_Gift[i]	= add("object/"+i+".png");
			}
			
			mTex_Point			= new SimplePlane[7];
			for(i=0;i<mTex_Point.length;i++){
				mTex_Point[i]	= add("point/"+i+".png");
			}
			
			mTex_Smok			= new SimplePlane[5];
			for(i=0;i<mTex_Smok.length;i++){
				mTex_Smok[i]	= add("blast/"+i+".png");
			}
			
			mTex_Speed			= new SimplePlane[2];
			mTex_Speed[0]		= add("point/speeddown_text.png");
			mTex_Speed[1]		= add("point/speedup_text.png");
			
			mTex_Life			= new SimplePlane[2];
			mTex_Life[0]		= add("object/life1.png");
			mTex_Life[1]		= add("object/life0.png");
			
			
			mTex_Object			= new SimplePlane[21];
			mTex_Object[0]		= add("earth_bird_0.png");
			mTex_Object[1]		= add("earth_bird_1.png");
			
			mTex_Object[2]		= add("cloud_bird_0.png");
			mTex_Object[3]		= add("cloud_bird_1.png");
			mTex_Object[4]		= add("cloud_copter.png");
			mTex_Object[5]		= add("cloud0_.png");
			
			mTex_Object[6]		= add("day_bird_0.png");
			mTex_Object[7]		= add("day_plane_0.png");
			mTex_Object[8]		= add("day_plane_1.png");
			mTex_Object[9]		= add("cloud1_.png");
			
			mTex_Object[10]		= add("moon.png");
			mTex_Object[11]		= add("moon_plane_0.png");
			
			mTex_Object[12]		= add("night_sattelite_0.png");
			mTex_Object[13]		= add("night_sattelite_1.png");
			mTex_Object[14]		= add("night_sattelite_2.png");
			
			mTex_Object[15]		= add("planet_0.png");
			mTex_Object[16]		= add("planet_1.png");
			mTex_Object[17]		= add("planet_2.png");
			mTex_Object[18]		= add("planet_3.png");
			
			mTex_Object[19]		= add("galaxy_0.png");
			mTex_Object[20]		= add("galaxy_1.png");
			
			mTex_BG				= new SimplePlane[7];
			mTex_BG[0]			= add("earthsky.jpg");
			mTex_BG[1]			= add("cloudsky.jpg");
			mTex_BG[2]			= add("daysky.jpg");
			mTex_BG[3]			= add("moonsky.jpg");
			mTex_BG[4]			= add("nightsky.jpg");
			mTex_BG[5]			= add("planetsky.jpg");
			mTex_BG[6]			= add("galaxysky.jpg");
			
			load_Font();
			
			
			mPlayer				= new Player();
			mPad				= new Pad[10];
			for(i=0;i<mPad.length;i++)
			{
				mPad[i]			= new Pad();
			}
			
			
			mObject				= new Object[21];
			for(i=0;i<mObject.length;i++)
			{
				mObject[i]		= new Object();
			}
			
			mPoint				= new Point[5];
			for(i=0;i<mPoint.length;i++)
			{
				mPoint[i]		= new Point();
			}
			
			mAni		= new Animation[300];
			for(i= 0;i<mAni.length;i++)
			{
				mAni[i] = new Animation();
			}
			gameReset();
		}catch(Exception e){}
		
	}
	
	
	void gameReset()
	{
		int i =0;
		for(i=0;i<mPad.length;i++)
		{
			int x = M.mRand.nextInt()%80;
			mPad[i].set(x/100f, -.8f + (i*M.DIFF));
//			System.out.println(x+"  ~~~~~~~~~~~ "+ mPad[i].x+"    "+ mPad[i].y);
		}
		mPad[1].x =0;
		mPlayer.set(mPad[1].x, mPad[1].y-.08f, 0, .01f,5);
		for(i= 0;i<mAni.length;i++)
		{
			mAni[i].set(-100, 0, 0, 0, 0, 0);
		}
		mObject[0].set ((M.mRand.nextInt()%100)/100f, 0*2+(M.mRand.nextInt(50))/100f,-.001f);
		mObject[1].set(-(M.mRand.nextInt()%100)/100f, 0*2+(M.mRand.nextInt(50)+50)/100f,0.001f);
		
		mObject[2].set ((M.mRand.nextInt()%100)/100f, 1*2+(M.mRand.nextInt(40))/100f,-.001f);
		mObject[3].set ((M.mRand.nextInt()%100)/100f, 1*2+(M.mRand.nextInt(40)+50)/100f,0.001f);
		mObject[4].set ((M.mRand.nextInt()%100)/100f, 1*2+(M.mRand.nextInt(40)+100)/100f,-.001f);
		mObject[5].set ((M.mRand.nextInt()%100)/100f, 1*2+(M.mRand.nextInt(40)+150)/100f,0.001f);
		
		mObject[6].set ((M.mRand.nextInt()%100)/100f, 2*2+(M.mRand.nextInt(40))/100f,0.001f);
		mObject[7].set ((M.mRand.nextInt()%100)/100f, 2*2+(M.mRand.nextInt(40)+50)/100f,-.001f);
		mObject[8].set ((M.mRand.nextInt()%100)/100f, 2*2+(M.mRand.nextInt(40)+100)/100f,0.001f);
		mObject[9].set ((M.mRand.nextInt()%100)/100f, 2*2+(M.mRand.nextInt(40)+150)/100f,-.001f);
		
		mObject[10].set ((M.mRand.nextInt()%100)/100f, 3*2+(M.mRand.nextInt(80))/100f,0.001f);
		mObject[11].set ((M.mRand.nextInt()%100)/100f, 3*2+(M.mRand.nextInt(80)+100)/100f,-.001f);
		
		mObject[12].set((M.mRand.nextInt()%100)/100f, 4*2+(M.mRand.nextInt(50))/100f,-.001f);
		mObject[13].set((M.mRand.nextInt()%100)/100f, 4*2+(M.mRand.nextInt(50)+66)/100f,0.001f);
		mObject[14].set((M.mRand.nextInt()%100)/100f, 4*2+(M.mRand.nextInt(50)+133)/100f,-.001f);
		
		mObject[15].set((M.mRand.nextInt()%100)/100f, 5*2+(M.mRand.nextInt(40))/100f,-.001f);
		mObject[16].set((M.mRand.nextInt()%100)/100f, 5*2+(M.mRand.nextInt(40)+50)/100f,0.001f);
		mObject[17].set((M.mRand.nextInt()%100)/100f, 5*2+(M.mRand.nextInt(40)+100)/100f,-.001f);
		mObject[18].set((M.mRand.nextInt()%100)/100f, 5*2+(M.mRand.nextInt(40)+150)/100f,0.001f);
		
		mObject[19].set((M.mRand.nextInt()%100)/100f, 6*2+(M.mRand.nextInt(80))/100f,-.001f);
		mObject[20].set((M.mRand.nextInt()%100)/100f, 6*2+(M.mRand.nextInt(80)+100)/100f,0.001f);
		
		mBGY =0;
		mBGSY1 = 2.1f;
		mBGSY2 = 4.1f;
		
		for(i=0;i<mPoint.length;i++)
		{
			mPoint[i].set(-100,10,0,0);
		}
		mScore	= 0;
		mSpeedC	= 0;
		mLevel = 1;
		mContinue = false;
		levelUp = 0;
		mSpeedC = 200 ;
		mSpeedN = 0;
	}
	
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
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
			if(resumeCounter >20 && M.GameScreen != M.GAMEPAUSE && M.GameScreen != M.GAMEOVER && M.GameScreen != M.GAMEADD)
			{
				int inv=mStart.adView.getVisibility();
				if(inv==AdView.GONE){try{handler.sendEmptyMessage(AdView.VISIBLE);}catch(Exception e){}}
			}
			else
			{
				int inv=mStart.adView.getVisibility();
				if(inv==AdView.VISIBLE){try{handler.sendEmptyMessage(AdView.GONE);}catch(Exception e){}}
			}
		}
		if(mStart.adRect!=null)
		{
			resumeCounter++;
			if(M.GameScreen == M.GAMEPAUSE || M.GameScreen == M.GAMEOVER)
			{
				int inv=mStart.adRect.getVisibility();
				if(inv==AdView.GONE){try{handler1.sendEmptyMessage(AdView.VISIBLE);}catch(Exception e){}}
			}
			else
			{
				int inv=mStart.adRect.getVisibility();
				if(inv==AdView.VISIBLE){try{handler1.sendEmptyMessage(AdView.GONE);}catch(Exception e){}}
			}
		}
		/*AdHouse*/
		if (mStart.adHouse != null) {
			if (M.GameScreen == M.GAMEADD) {
				int inv = mStart.adHouse.getVisibility();
				if (inv == AdView.GONE) {try {handler2.sendEmptyMessage(AdView.VISIBLE);} catch (Exception e) {}}
			} else {
				int inv = mStart.adHouse.getVisibility();
				if (inv == AdView.VISIBLE) {try {handler2.sendEmptyMessage(AdView.GONE);} catch (Exception e) {}
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
	@Override
	public void onAccelerationChanged(float x, float y, float z) {
//		System.out.println(x + "----------------=>  "+y+"   -----------    "+z);
		if(mContinue){
			Ax = x;
			if(mPlayer.x <-.95)
				mPlayer.x = -.95f;
			if(mPlayer.x >.95)
				mPlayer.x = .95f;
		}
	}
	@Override
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
		}catch(Exception e)
		{
			System.out.println("~~~~~~~~~~~~~~ "+ID);
		}
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
	void load_Font()
	{
		mTex_Font	= new SimplePlane[10];
		Bitmap b = LoadImgfromAsset("fontstrip.png");
		for(int i = 0;i<mTex_Font.length;i++)
			mTex_Font[i] = addBitmap(Bitmap.createBitmap(b, i*b.getWidth()/mTex_Font.length, 0,b.getWidth()/mTex_Font.length, b.getHeight(),null, true));
	}
}
