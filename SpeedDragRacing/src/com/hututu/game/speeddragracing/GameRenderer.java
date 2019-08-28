package com.hututu.game.speeddragracing;

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
	public static Context mContext;
	public static Start mStart;
	long startTime = System.currentTimeMillis();
	int resumeCounter =0;
	int mSel = 0;
	int sond = 0;
	
	SimplePlane[] mTex_Font,mTex_Car[],mTex_CarBar,mTex_Acce,mTex_Gear,mTex_MRPM,mTex_Boundry;
	SimplePlane mTex_Road,mTex_Building,mTex_Clock,mTex_Dasboard,mTex_RaodMeter,mTex_Logo,mTex_Meter,mTex_font;
	
	SimplePlane[] mTex_MenuBut,mTex_MenuIcn,mTex_Level,mTex_LevelBut,mTex_About,mTex_Help,mTex_Light[],mTex_NO2,mTex_Fire;
	SimplePlane mTex_Splash,mTex_SetBG,mTex_CarPer,mTex_Arrow,mTex_Mask,mTex_Power,mTex_SOff,mTex_Red,mTex_Menubar,mTex_Lock;
	SimplePlane mTex_Go,mTex_Ready,mTex_Signal,mTex_Start,mTex_StartL,mTex_winning,mTex_youlost,mTex_youwin,mTex_Pattern;
	SimplePlane   mTex_Pointer, mTex_Hightbar,mTex_Skip; //AdHouse
	
	Car[]		mCar;
	Level[]		mLevel;
	Font		mFont;
	Animation	mAnimation;
	
	MainActivity mMainActivity;
	
//	int mGameStart = 0;
	
	boolean addFree = false;
	
	int mMove = 0;
	int breakPoint = 0;
	int unlockLevel = 1;
	int mCoint = 0;
	int mSignal;
	int EndCounter =0;
	int mHScore;
	
	float mBG ,mBG2;
	float mBGB ,mBGB2;
	float mBGBD ,mBGBD2;
	float mSGame;
	float mLvlLength;
	
	String opptime	="SEC";
	String playertime="SEC";
	
//	private Handler handler = new Handler() {public void handleMessage(Message msg) {mStart.adView.setVisibility(msg.what);}};
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
		int i =0;
		try
		{
			mMainActivity = new MainActivity();
			mMainActivity.onCreate();
			
			mTex_Pointer = add("pointer.png");//AdHouse
			mTex_Hightbar = add("hightbar0.png");//AdHouse
			mTex_Skip = add("exit_icon.png");//AdHouse
			mFont				= new Font();
			mTex_Logo			= add("hututugames.png");
			
			mTex_Pattern		= add("ui/black_pattern.png");
			mTex_Menubar		= add("ui/menubar.png");
			mTex_Red			= add("ui/1.jpg");
			mTex_Power			= add("ui/power.png");
			mTex_Splash			= add("ui/splash.jpg");
			mTex_SetBG			= add("ui/setting_bg.jpg");
			mTex_CarPer			= add("ui/carpreview.png");
			mTex_Arrow			= add("ui/arrow_de.png");
			mTex_Mask			= add("ui/mask.png");
			
			mTex_Level			= new SimplePlane[2];
			mTex_Level[0]		= add("ui/levelbutton_de.png");
			mTex_Level[1]		= add("ui/levelbutton_se.png");
			
			mTex_LevelBut		= new SimplePlane[2];
			mTex_LevelBut[0]	= add("ui/bigbutton_se.png");
			mTex_LevelBut[1]	= add("ui/smallbutton_se.png");
			
			mTex_About			= new SimplePlane[2];
			mTex_About[0]		= add("ui/about_text.png");
			mTex_About[1]		= add("ui/astunt_ad.png");
			
			mTex_Help			= new SimplePlane[2];
			mTex_Help[0]		= add("ui/help.png");
			mTex_Help[1]		= add("ui/heli_ad.png");
			
			mTex_NO2			= new SimplePlane[2];
			mTex_NO2[0]			= add("power0.png");
			mTex_NO2[1]			= add("power1.png");
			
			mTex_Fire			= new SimplePlane[2];
			mTex_Fire[0]		= add("fire0.png");
			mTex_Fire[1]		= add("fire1.png");
			
			
			mTex_MenuBut		= new SimplePlane[2];
			mTex_MenuBut[0]		= add("ui/menubutton_de.png");
			mTex_MenuBut[1]		= add("ui/menubutton_se.png");
			
			mTex_MenuIcn		= new SimplePlane[5];
			mTex_MenuIcn[0]		= add("ui/raceicon.png");
			mTex_MenuIcn[1]		= add("ui/shopicon.png");
			mTex_MenuIcn[2]		= add("ui/helpicon.png");
			mTex_MenuIcn[3]		= add("ui/abouticon.png");
			mTex_MenuIcn[4]		= add("ui/soundicon.png");
			mTex_SOff			= add("ui/sondoff.png");
			mTex_Lock			= add("lock.png");
			
			mTex_Road			= add("road.jpg");
			mTex_Building		= add("building.jpg");
			
			mTex_Boundry		= new SimplePlane[2];
			mTex_Boundry[0]		= add("boundary.jpg");
			mTex_Boundry[1]		= add("boundary1.jpg");
			
			mTex_RaodMeter		= add("racemeter.png");
			
			mTex_Acce			= new SimplePlane[2];
			mTex_Acce[0]		= add("dashbord/accelerator1.png");
			mTex_Acce[1]		= add("dashbord/accelerator0.png");
			mTex_Gear			= new SimplePlane[2];
			mTex_Gear[0]		= add("dashbord/geardown.png");
			mTex_Gear[1]		= add("dashbord/gearup.png");
			mTex_MRPM			= new SimplePlane[3];
			mTex_MRPM[0]		= add("dashbord/green0.png");
//			mTex_MRPM[1]		= add("dashbord/red.png");
			mTex_MRPM[1]		= add("dashbord/blue.png");
			mTex_MRPM[2]		= add("dashbord/green1.png");
			
			mTex_Dasboard		= add("dashbord/dasboard.png");
			mTex_Clock			= add("dashbord/clockhand.png");
			mTex_Meter			= add("dashbord/meter.png");
			
			
			mTex_Go				= add("up/go_light.png");
			mTex_Ready			= add("up/ready_light.png");
			mTex_Signal			= add("up/signal.png");
			mTex_Start			= add("up/start_light.png");
			mTex_StartL			= add("up/start_line.png");
			mTex_winning		= add("up/winning_line.png");
			mTex_youlost		= add("up/youlost.png");
			mTex_youwin			= add("up/youwin.png");
			
			
			
			mTex_Light			= new SimplePlane[3][];
			
			mTex_Light[0]		= new SimplePlane[2];
			mTex_Light[0][0]	= add("up/bluealert.png");
			mTex_Light[0][1]	= add("up/goodgear.png");
			
			mTex_Light[1]		= new SimplePlane[2];
			mTex_Light[1][0]	= add("up/greenalert.png");
			mTex_Light[1][1]	= add("up/perfectgear.png");
			
			mTex_Light[2]		= new SimplePlane[2];
			mTex_Light[2][0]	= add("up/redalert.png");
			mTex_Light[2][1]	= add("up/overdrive.png");
			
			
			mTex_Car			= new SimplePlane[6][];
			for(i=0;i<mTex_Car.length;i++)
			{
				mTex_Car[i]		= new SimplePlane[2];
				for(int j=0;j<mTex_Car[i].length;j++)
				{
					mTex_Car[i][j] 		= add("car/car_"+i+""+j+".png");
				}
			}
			mTex_CarBar			= new SimplePlane[2];
			mTex_CarBar[0]	 	= add("userpreview.png");
			mTex_CarBar[1]	 	= add("computerpreview.png");
			load_Font();
			
			
			mCar				= new Car[2];
			for(i =0;i<mCar.length;i++)
				mCar[i]			= new Car();
			
			mLevel				= new Level[6];
			for(i =0;i<mLevel.length;i++)
				mLevel[i]			= new Level();
			
			mAnimation	= new Animation();
			
			gameReset();
		}catch(Exception e){}
	}
	
	void gameReset()
	{
//		mCoint = 1000000;
		mSGame =0;
		sond = 50;
//		unlockLevel = 6;
		float power = 1f+(breakPoint*4+mLevel[breakPoint].mSLevel)/10f;
		mCar[0].set(-.5f, .38f,(breakPoint*4+mLevel[breakPoint].mSLevel)%mTex_Car.length,power,50);
		if(breakPoint<2)
		{
			mCar[1].set(-.5f,-.15f,breakPoint%mTex_Car.length,1+(breakPoint*4+mLevel[breakPoint].mUPower)/20f,0);
//			System.out.println(breakPoint+"  ~~~~~~~~~~~~$$$$$$$$$$$$$$$~~~~~~~~~~~~~~~~");
		}
		else if(breakPoint<3)
		{
			mCar[1].set(-.5f,-.15f,breakPoint%mTex_Car.length,1+(breakPoint*4+mLevel[breakPoint].mUPower)/15f,0);
//			System.out.println(breakPoint+"  ~~~~~~~~~~~~!!!!!!!!!!~~~~~~~~~~~~~~~~");
		}
		else if(breakPoint<4)
		{
			mCar[1].set(-.5f,-.15f,breakPoint%mTex_Car.length,1+(breakPoint*4+mLevel[breakPoint].mUPower)/13f,0);
//			System.out.println(breakPoint+"  ~~~~~~~~~~~~@@@@@@@@@@@@~~~~~~~~~~~~~~~~");
		}
		else
		{
			mCar[1].set(-.5f,-.15f,breakPoint%mTex_Car.length,1+(breakPoint*4+mLevel[breakPoint].mUPower)/12f,0);
//			System.out.println(breakPoint+"  ~~~~~~~~~~~~#W##################~~~~~~~~~~~~~~~~");
		}
//		System.out.println(breakPoint+"  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		switch (breakPoint) {
		case 0:
			mLvlLength = .01f;
			mCar[0].totalDis = mCar[1].totalDis = 180;
			break;
		case 1:
			mLvlLength = .008f;
			mCar[0].totalDis = mCar[1].totalDis = 220;
			break;
		case 2:
			mLvlLength = .006f;
			mCar[0].totalDis = mCar[1].totalDis = 300;
			break;
		case 3:
			mLvlLength = .005f;
			mCar[0].totalDis = mCar[1].totalDis = 360;
			break;
		case 4:
			mLvlLength = .004f;
			mCar[0].totalDis = mCar[1].totalDis = 450;
			break;
		case 5:default:
			mLvlLength = .003f;
			mCar[0].totalDis = mCar[1].totalDis = 600;
			break;
		}
	//	mLvlLength = .01f-(breakPoint+1f)/1000f;
		//mCar[0].totalDis = mCar[1].totalDis= 18000*(.01f+(breakPoint+1f)/1000f);
		
		
		mBGBD  = mBGB  = mBG  = 0;
		mBGBD2 = mBGB2 = mBG2 = 2;
		mSignal = -4;
//		System.out.println("mLvlLength = "+mLvlLength);
		{
			int i=breakPoint+1;
			if(i>=mLevel.length)
				i=0;
			for(int j=0;j<mLevel.length;j++)
			{
				if(i<0)
					i=mLevel.length-1;
				if(j<2)
					mLevel[i].set(0.6f-(j*.6f),(.6f + (j*.4f)),-.0066f);
				else
				{
					mLevel[i].set(0.6f-(j*.6f),(1.0f - ((j-1)*.4f)),0.0066f);
				}
				i--;
			}
		}
		
		mLevel[0].CStrength(1.0f,.7f,.3f,.2511f,.5f,1.5f);
		mLevel[1].CStrength(1.1f,.8f,.4f,.5033f,.6f,1.6f);
		mLevel[2].CStrength(1.2f,.8f,.5f,.6585f,.7f,1.7f);
		mLevel[3].CStrength(1.2f,.9f,.5f,.7147f,.8f,1.5f);
		mLevel[4].CStrength(1.4f,.9f,.4f,.8259f,.5f,1.8f);
		mLevel[5].CStrength(1.3f,.9f,.3f,.9325f,.8f,1.8f);
		EndCounter=0;
		mAnimation.set(100,0);
		M.loopstop();
		if(M.GameScreen == M.GAMEPLAY)
		{
			M.sound2(GameRenderer.mContext, R.drawable.start);
		}
			
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
//		if(mStart.adView!=null)
//		{
//			resumeCounter++;
//			if(resumeCounter >20 && M.GameScreen != M.GAMESHOP)
//			{
//				int inv=mStart.adView.getVisibility();
//				if(inv==AdView.INVISIBLE){try{handler.sendEmptyMessage(AdView.VISIBLE);}catch(Exception e){}}
//			}
//			else
//			{
//				int inv=mStart.adView.getVisibility();
//				if(inv==AdView.VISIBLE){try{handler.sendEmptyMessage(AdView.INVISIBLE);}catch(Exception e){}}
//			}
//		}
		/*AdHouse*/
		if (mStart.adHouse != null) {
			if (M.GameScreen == M.GAMEADD) {
				int inv = mStart.adHouse.getVisibility();
				if (inv == AdView.INVISIBLE) {try {handler2.sendEmptyMessage(AdView.VISIBLE);} catch (Exception e) {}}
			} else {
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
//		GLU.gluPerspective(gl, 45.0f, (float)width / (float)height, 0.1f, 100.0f);
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
		}catch(Exception e){
			System.out.print(ID+" Image is not loading.............");
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
		mTex_font			= add("meter_font0.png");
		mTex_Font	= new SimplePlane[10];
		Bitmap b = LoadImgfromAsset("fontstrip.png");
		for(int i = 0;i<mTex_Font.length;i++)
			mTex_Font[i] = addBitmap(Bitmap.createBitmap(b, i*b.getWidth()/mTex_Font.length, 0,b.getWidth()/mTex_Font.length, b.getHeight(),null, true));
	}
}
