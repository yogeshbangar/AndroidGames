package com.hututu.game.stuntracingcar;

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
	int resumeCounter =0;
	long startTime = System.currentTimeMillis();
	
	SimplePlane[] mTex_Font,mTex_Car,mTex_Bg,mTex_MButton;
	SimplePlane mTex_Cactas,mTex_CarShadow,mTex_Spark,mTex_Whitepart;
	SimplePlane mTex_Jump,mTex_LR_key;
	
	
	SimplePlane mTex_Aboutusfont,mTex_Backde,mTex_Backse,mTex_Buttonselect,mTex_Buttonselect1,mTex_Continue;
	SimplePlane mTex_Distancefont,mTex_Gameover,mTex_Gamepaused,mTex_Help,mTex_Submitscore,mTex_Levelfont;
	SimplePlane mTex_Menu,mTex_Menu_popup,mTex_More,mTex_Pausede,mTex_Popup_menu,mTex_Rateus,mTex_Retry,mTex_Scorefont;
	SimplePlane mTex_Share,mTex_Soundde,mTex_Soundoff,mTex_Splash,mTex_Timefont,mTex_Timeup,mTex_Logo,mTex_HS;
	SimplePlane   mTex_Pointer, mTex_Hightbar,mTex_Skip; //AdHouse
	//SimplePlane mTex_Play,mTex_HelpB,mTex_About,mTex_Submitscore,mTex_Exit;
	
	boolean addFree = false;
	Strip[] mStrip;
	Car[] mCar;
	Player mPlayer;
	
	int Carcounter = 0;
	int mTime = 50;
	int mScore;
	int mHScore;
	int mLevel =0;
	int mLevelUpCounter = 0;
	int mTimeUp = 0;
	int mSel	=0;
	
	float mTargetDistance = 6000,mDistance;
	long mStime = 0;
	
	
	private Handler handler = new Handler() {public void handleMessage(Message msg) {mStart.adView.setVisibility(msg.what);}};
//	private Handler handler2 = new Handler() {public void handleMessage(Message msg) {mStart.adHouse.setVisibility(msg.what);}};//AdHouse
	public GameRenderer(Context context) 
	{
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
			mTex_HS			= add("highscore_font.png");
			mTex_Logo		= add("hututugames.png");
			mTex_Bg			= new SimplePlane[3]; 
			mTex_Bg[0]		= add("bg0.png");
			mTex_Bg[1]		= add("bg2.png");
			mTex_Bg[2]		= add("bg3.png");
			
			mTex_Cactas		= add("cactas.png");
			mTex_CarShadow	= add("carshadow.png");
			mTex_Spark		= add("spark.png");
			mTex_Whitepart	= add("bg1.png");
			mTex_Jump		= add("jumpkey.png");
			mTex_LR_key		= add("leftright_key.png");
			
			mTex_Car		= new SimplePlane[7];
			for(i=0;i<mTex_Car.length;i++)
			{
				mTex_Car[i]	= add("car"+i+".png");
			}
			
			mTex_MButton		= new SimplePlane[5];
			mTex_MButton[0]		= add("play.png");
			mTex_MButton[1]		= add("help-44.png");
			mTex_MButton[2]		= add("about.png");
			mTex_MButton[3]		= add("highscore.png");
			mTex_MButton[4]		= add("exit.png");
			
			
			mTex_Aboutusfont	= add("aboutusfont.png");
			mTex_Backde			= add("backde.png");
			mTex_Backse			= add("backse.png");
			mTex_Buttonselect	= add("buttonselect.png");
			mTex_Buttonselect1	= add("buttonselect1.png");
			mTex_Continue		= add("continue.png");
			mTex_Distancefont	= add("distancefont.png");
			
			mTex_Gameover		= add("gameover.png");
			mTex_Gamepaused		= add("gamepaused.png");
			mTex_Help			= add("help.png");
			
			mTex_Submitscore	= add("submitscore.png");
			mTex_Levelfont		= add("levelfont.png");
			mTex_Menu			= add("menu.png");
			mTex_Menu_popup		= add("menu_popup.png");
			mTex_More			= add("more.png");
			mTex_Pausede		= add("pausede.png");
			
			mTex_Popup_menu		= add("popup_menu.png");
			mTex_Rateus			= add("rateus.png");
			mTex_Retry			= add("retry.png");
			mTex_Scorefont		= add("scorefont.png");
			mTex_Share			= add("share.png");
			mTex_Soundde		= add("soundde.png");
			mTex_Soundoff		= add("sondoff.png");
			mTex_Splash			= add("splash.jpg");
			
			mTex_Timefont		= add("timefont.png");
			mTex_Timeup			= add("timeup.png");
			
			
			
			
			
			
			
			
			
			
			
			
			
			font();
			mStrip			= new Strip[15];
			for(i=0;i<mStrip.length;i++)
			{
				mStrip[i]	= new Strip();
			}
			mCar			= new Car[20];
			for(i=0;i<mCar.length;i++)
			{
				mCar[i]	= new Car();
			}
			mPlayer		= new Player();
//			gameReset();
		}catch(Exception e){}
		
	}
	void gameReset()
	{
		int  i=0;
		mTime = 40;
		M.speed = 1.1f;
		for(i=0;i<mStrip.length;i++)
		{
			mStrip[i].Set(-100, 0,-.03f, .01f);
		}
		for(int j=0;j<100;j++)
		{
			for(i=0;i<mStrip.length;i++)
			{
				if(mStrip[i].y > -2)
					mStrip[i].update();
			}
			root.findBig();
		}
		M.speed = 1.0f;
		for(i=0;i<mCar.length;i++)
		{
			mCar[i].Set(0,-100, 0,0,-.03f, .01f,0,0,0);
		}
		mPlayer.set(0, -.56f, 0, 0);
		mLevel =0;
		setlevel();
		mStart.load();
	}
	void setlevel()
	{
		M.GameScreen = M.GAMEPLAY;
		mLevel++;
		System.out.println("mLevel = "+mLevel);
		mTargetDistance = 6000;
		mDistance		= 0;
		if(mLevel>1)
			mScore += (mStime - System.currentTimeMillis())/1000;
		else
			mScore  =0;
		if((50 - mLevel)>20)
			mTime =35 - mLevel;
		else
			mTime = 20;
		mLevelUpCounter = 30;
		mStime = System.currentTimeMillis()+(mTime*1000);
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
		if(mStart.adView!=null)
		{
			resumeCounter++;
			if(resumeCounter > 50 && M.GameScreen != M.GAMEADD)
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
		/*if (mStart.adHouse != null) {
			if (M.GameScreen == M.GAMEADD) {
				int inv = mStart.adHouse.getVisibility();
				if (inv == AdView.GONE) {try {handler2.sendEmptyMessage(AdView.VISIBLE);} catch (Exception e) {}}
			} else {
				int inv = mStart.adHouse.getVisibility();
				if (inv == AdView.VISIBLE) {try {handler2.sendEmptyMessage(AdView.GONE);} catch (Exception e) {}
				}
			}
		}*/
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
			Log.d(""+ID,"!!!!!!!!!!!!!!!!!!!!!~~~~~~~~~~~~~!!!!!!!!!!!!!!!!!!!!!!!!!"+e.getMessage());
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
	void font()
	{
		mTex_Font	= new SimplePlane[10];
		Bitmap b = LoadImgfromAsset("fontstrip.png");
		for(int i = 0;i<mTex_Font.length;i++)
			mTex_Font[i] = addBitmap(Bitmap.createBitmap(b, i*b.getWidth()/mTex_Font.length, 0,b.getWidth()/mTex_Font.length, b.getHeight(),null, true));
	}
	
}
