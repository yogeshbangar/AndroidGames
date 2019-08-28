package com.hututu.game.galaxyhunt;

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
	long startTime = System.currentTimeMillis();
	int resumeCounter =0;
	int mSel = 0;
		
	SimplePlane[] mTex_OppPlan[],mTex_Bullet,mTex_Smok,mTex_Player,mTex_SoundBtn,mTex_Submitt,mTex_Font,mTex_Power,mTex_shield;
	
	SimplePlane mTex_Splash,mTex_NewGameBtn,mTex_HelpBtn,mTex_AbtUsBtn,mTex_HighScoreBtn,/*mTex_RateUsBtn,*/mTex_ShareBtn;
	SimplePlane mTex_Exit,mTex_Selector,mTex_SmallSel,mTex_Logo,mTex_HututTitle,mTex_MenuBtn,mTex_Continue,mTex_Replay;
	SimplePlane mTex_PopUp,mTex_GamePauseTitle,mTex_GameOverTitle,mTex_NewScore,mTex_ScoreBox,mTex_BestScore,mTex_Score;
	SimplePlane mTex_Pause,mTex_HelpTitle,mTex_HelpTxt,mTex_AbtTitle,mTex_AbtUsTxt,mTex_backBtn,mTex_PBullet,mTex_lifebaar;
	SimplePlane mTex_UIbg,mTex_Level,mTex_LevelUp;
	SimplePlane   mTex_Pointer, mTex_Hightbar,mTex_Skip; //AdHouse
	
	int mScore,mHighScore,mLevel;
	int mLevelC;
	Object[] 	mObject;
	Animation[] mAni;
	Bullete[]	mBullete;
	Bullete 	mPower;
	Bullete 	mElectric;
	Player		mPlayer;
	
	private Handler handler = new Handler() {public void handleMessage(Message msg) {mStart.adView.setVisibility(msg.what);}};
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
		int i=0;
		try
		{
			mTex_Pointer = add("pointer.png");//AdHouse
			mTex_Hightbar = add("hightbar0.png");//AdHouse
			mTex_Skip = add("exit_icon.png");//AdHouse
			mTex_Level				= add("ui/level_text.png");
			mTex_LevelUp			= add("ui/level_up.png");
			mTex_UIbg				= add("ui/gamebg.jpg");
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
			
			
			mTex_Power				= new SimplePlane[4];
			mTex_Power[0]			= add("bullet/bullet1_power.png");
			mTex_Power[1]			= add("bullet/bullet2_power.png");
			mTex_Power[2]			= add("bullet/shield_power.png");
			mTex_Power[3]			= add("bullet/health_power.png");
			
			mTex_shield				= new SimplePlane[2];
			mTex_shield[0]			= add("bullet/shield0.png");
			mTex_shield[1]			= add("bullet/shield1.png");
			
			
			
			mTex_PBullet			= add("bullet/bullet2.png");
			mTex_lifebaar			= add("bullet/lifebaar.png");
			
			
			mTex_OppPlan	= new SimplePlane[15][];
			for(i=0;i<mTex_OppPlan.length;i++)
			{
				mTex_OppPlan[i]		= new SimplePlane[2];
				mTex_OppPlan[i][0]	= add(i+"/0.png");
				mTex_OppPlan[i][1]	= add(i+"/1.png");
			}
			
			mTex_Bullet				= new SimplePlane[4];
			for(i=0;i<mTex_Bullet.length;i++)
				mTex_Bullet[i]	= add("bullet_"+i+".png");
			
			mTex_Smok				= new SimplePlane[3];
			for(i=0;i<mTex_Smok.length;i++)
				mTex_Smok[i]	= add("smoke"+i+".png");
			
			
			mTex_Player				= new SimplePlane[3];
			mTex_Player[0]			= add("user/0.png");
			mTex_Player[1]			= add("user/1.png");
			
			load_Font();
			
			mObject		= new Object[20];
			for(i= 0;i<mObject.length;i++)
			{
				mObject[i] = new Object();
			}
			
			mAni		= new Animation[100];
			for(i= 0;i<mAni.length;i++)
			{
				mAni[i] = new Animation();
			}
			mPower			= new Bullete();
			mElectric		= new Bullete();
			mBullete		= new Bullete[100];
			for(i= 0;i<mBullete.length;i++)
			{
				mBullete[i] = new Bullete();
			}
			mPlayer = new Player();
			gameReset();
		}catch(Exception e){}
		
	}
	
	void gameReset()
	{
		int i;
		for(i= 0;i<mObject.length;i++)
		{
			mObject[i].set(-100,-11,0,0,0);
		}
		for(i= 0;i<mAni.length;i++)
		{
			mAni[i].set(-100, 0, 0, 0, 0, 0);
		}
		for(i= 0;i<mBullete.length;i++)
		{
			mBullete[i].set(-10, 0, 0, 0,0);
		}
		mPlayer.set(0, 0, 0, 0.04f);
		mElectric.set(110, 100, 0, 0,0);
		mPower.set(-110, -100, 0, 0,0);
		root.Counter = 1;
		mScore = 0;
		M.Plife = M.Max_Plife;
		mLevel =1;
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
			if(resumeCounter >20 && M.GameScreen != M.GAMEPLAY && M.GameScreen != M.GAMEADD)
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
	void load_Font()
	{
		mTex_Font	= new SimplePlane[10];
		Bitmap b = LoadImgfromAsset("fontstrip.png");
		for(int i = 0;i<mTex_Font.length;i++)
			mTex_Font[i] = addBitmap(Bitmap.createBitmap(b, i*b.getWidth()/mTex_Font.length, 0,b.getWidth()/mTex_Font.length, b.getHeight(),null, true));
	}
}
