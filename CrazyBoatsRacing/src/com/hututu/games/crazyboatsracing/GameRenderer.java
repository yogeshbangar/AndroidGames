package com.hututu.games.crazyboatsracing;

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
	public static Context mContext;
	public static Start mStart;
	long startTime = System.currentTimeMillis();
	int mSel = 0;
	
	/********************************************************************/
	boolean car_buy[]={true,false,false,false,false};
	boolean EndLess;
	boolean addFree = false;
	boolean SingUpadate = false;
	boolean Achiv[] = new boolean[10];
	boolean mPower[] = new boolean[3];
	
	byte mLStar[] = new byte[30];
	
	int mGoCount = 0;
	int mLevel = 0;
	int mULevel = 1;
	int mNoCoin = 0;
	int mTotalCoin = 0;
	int mTempCoin = 0;
	int mNoDimond = 0;
	int mCarCount = 0;
	int mBus = 0;
	int mKill = 0;
	int levelScr = 0;
	int roadNo;
	int roadSide;
	int car_No;
	int car_Sel;
	int Total[] = new int[5];
	float mMeter = 0;
	float mRaodY[];
	float mSRaodY[];
	float gx;
	float finish = -2;
	
	Opponent	mOpp[];
	Player 		mPlayer;
	Coin		mCoin[];
	Coin		mDimond;
	Coin		mMagnet;
	Coin		mCoinAnim;
	Coin		mRpatern;
	Coin		mRMtion;
	
//	Blast		mBlast;
	Animation	mAnim[];
	Fire 		mFire;
	Sky			mSky;
	RoketAnim	mRoket[];
	Blast	mBAnim[];
	
	MainActivity mMainActivity;
	
	SimplePlane mTex_Logo,mTex_coin2bar,mTex_cresh,mTex_box, mTex_LCompleted,mTex_LFailed,mTex_Buy,mTex_Highway;
	SimplePlane mTex_mainBtn, mTex_morecoin, mTex_nextBtn, mTex_restart,mTex_star,mTex_bStar,mTex_Go,mTex_Cloud;
	SimplePlane mTex_LvlBox,mTex_LvlTxt,mTex_ARadar,mTex_Cross,mTex_Goggles,mTex_Magnet,mTex_PowerTxt,mTex_Free;
	SimplePlane mTex_Diamond2,mTex_Magnet2,mTex_killCar,mTex_police,mTex_Bar,mTex_Finish,mTex_$,mTex_X,mTex_Pls;
	SimplePlane mTex_Splash,mTex_Arrow,mTex_Back, mTex_MapDot,mTex_Lock,/*mTex_Lvl,*/mTex_Missile_Btn,mTex_PlanWing;
	SimplePlane mTex_Life,mTex_CLock,mTex_CarS,mTex_RaceBtn,mTex_RightMrk,mTex_SelectB,mTex_CashHnd,mTex_Price;
	SimplePlane mTex_Plan,mTex_fb,mTex_About,mTex_Cong,mTex_LingBar,mTex_Ling,mTex_Pause,mTex_BVlue;
	SimplePlane mTex_Help,mTex_HelpIcn,mTex_Speed,mTex_Cntorl,mTex_RedCir;
	SimplePlane mTex_Button,mTex_Cash,mTex_Highscore,mTex_Playagain,mTex_Store,mTex_Missile,mTex_ShpT,mTex_MapT;
	
	SimplePlane[] mTex_SideRoad[],mTex_OppCar,mTex_PlyCar[],mTex_Target,mTex_Font,mTex_Coin,mTex_LvlScr, mTex_Blast;
	SimplePlane[] mTex_ShopCar[],mTex_Bridge,mTex_SplashBtn,mTex_Road,mTex_SplashIcn,mTex_Join,/*mTex_Roket,*/mTex_SpdFill;
	SimplePlane[] mTex_BWA,mTex_SWA;
	
	
	
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
		try
		{
			mMainActivity = new MainActivity();
			mMainActivity.onCreate();
			
			mTex_Pause		= add("Comp_failed/paused_text.png");
			mTex_Ling		= add("loading0.png");
			mTex_LingBar	= add("loading1.png");
			mTex_Join	= new SimplePlane[2];
			mTex_Join[0] = add("join_btn.png");
			mTex_Join[1] = add("join_text.png");
//			mTex_Roket	= new SimplePlane[2];
//			mTex_Roket[0] = add("rocket0.png");
//			mTex_Roket[1] = add("rocket1.png");
			mTex_Missile = addRotate("missile.png");
			
			mTex_Splash 	= add("Splash/Splash.png");
//			mTex_RoadPatrn	= add("Splash/road_pattern.png");
//			mTex_Wheel		= add("Splash/wheel_effect.png");
			mTex_Help		= add("help.png");
			mTex_HelpIcn	= add("help_btn.png");
			
			mTex_SplashBtn = new SimplePlane[4];
			mTex_SplashBtn[0] = add("Splash/race_btn.png");
			mTex_SplashBtn[1] = add("Splash/endlessrace_btn.png");
			mTex_SplashBtn[2] = add("Splash/boats_btn.png");
			mTex_SplashBtn[3] = add("Splash/trophies_btn.png");
			mTex_Highway = add("Splash/splash_text.png");
			mTex_About = add("aboutus_text.png");
			
			mTex_SplashIcn = new SimplePlane[5];
			mTex_SplashIcn[0] = add("Comp_failed/music_btn.png");
			mTex_SplashIcn[1] = add("Comp_failed/position_icon.png");
			mTex_SplashIcn[2] = add("Comp_failed/sound_btn.png");
			mTex_SplashIcn[3] = add("Comp_failed/more.png");
			mTex_SplashIcn[4] = add("Comp_failed/about_btn.png");
			
			mTex_fb = add("Comp_failed/fb.png");
			
			mTex_Logo = add("hututugames.png");
			mTex_Plan = add("BG/plane.png");
			mTex_PlanWing = add("BG/wings.png");
			mTex_Cloud = add("BG/cloud.png");
			mTex_Bridge = new SimplePlane[3];
			for(int i =0;i<mTex_Bridge.length;i++){
				mTex_Bridge[i] = add("BG/bridge"+i+".png");
			}
			mTex_Road = new SimplePlane[2];
			mTex_Road[0] = add("BG/water0.png");
			mTex_Road[1] = add("BG/water1.png");
			mTex_SideRoad = new SimplePlane[5][];
			for(int i =0;i<mTex_SideRoad.length;i++)
			{
				mTex_SideRoad[i] = new SimplePlane[2];
				Bitmap b = LoadImgfromAsset("BG/tile"+i+".png");
				mTex_SideRoad[i][0] = addBitmap(Bitmap.createBitmap(b, 0,0 * b.getHeight() / 2, b.getWidth(), b.getHeight() / 2,null, true));
				mTex_SideRoad[i][1] = addBitmap(Bitmap.createBitmap(b, 0,1 * b.getHeight() / 2, b.getWidth(), b.getHeight() / 2,null, true));
			}
			
			mTex_OppCar = new SimplePlane[15];
			for (int i = 0; i < mTex_OppCar.length; i++) {
				mTex_OppCar[i] = add("Car/" + i + ".png");
			}
			mTex_PlyCar = new SimplePlane[5][3];
			for (int i = 0; i < mTex_PlyCar.length; i++) {
				mTex_PlyCar[i] = new SimplePlane[3];
				Bitmap bb = LoadImgfromAsset("usr/"+i+".png");
				for (int j = 0; j < mTex_PlyCar[i].length; j++) {
					mTex_PlyCar[i][j] = addBitmap(Bitmap.createBitmap(bb,  j * bb.getWidth()/ 4,0, bb.getWidth()/4, bb.getHeight(),null, true));
				}
			}

			mTex_Arrow = addRotate("map/arrow.png");
			mTex_Back = add("map/back.png");
			mTex_LvlScr = new SimplePlane[3];
			mTex_LvlScr[0] = add("map/map0.png");
			mTex_LvlScr[1] = add("map/map1.png");
			mTex_LvlScr[2] = add("map/map2.png");
			mTex_MapDot = add("map/map_dot.png");
			mTex_Lock = add("map/mapdot2.png");
//			mTex_Lvl = add("map/level_selection_bg.png");

			mTex_Missile_Btn = add("missile_btn.png");
			mTex_coin2bar = add("Comp_failed/coin2bar.png");
			mTex_cresh = add("Comp_failed/cresh.png");
			
			mTex_box = add("Comp_failed/bg.png");
			mTex_LCompleted = add("Comp_failed/level_completed_tect.png");
			mTex_Cong = add("Comp_failed/congratulation.png");
			mTex_LFailed = add("Comp_failed/gameover_text.png");
			mTex_mainBtn = add("Comp_failed/main-menu_btn.png");
			mTex_morecoin = add("Comp_failed/morecoin.png");
			
			
			mTex_Cash	= add("Comp_failed/cash.png");
			mTex_Highscore	= add("Comp_failed/highscore.png");
			mTex_Playagain = add("Comp_failed/playagain.png");
			mTex_Store = add("Comp_failed/store.png");
			
			
			mTex_nextBtn = add("Comp_failed/next_level_btn.png");
			mTex_restart = add("Comp_failed/restart_btn.png");
			mTex_star = add("Comp_failed/star.png");
			mTex_bStar = add("Comp_failed/star_back.png");
			mTex_Life = add("life.png");

			mTex_ShopCar = new SimplePlane[5][2];

			mTex_ShopCar[0][0] = add("shop/alfa.png");
			mTex_ShopCar[0][1] = add("shop/1.png");
			mTex_ShopCar[1][0] = add("shop/apollo.png");
			mTex_ShopCar[1][1] = add("shop/2.png");
			mTex_ShopCar[2][0] = add("shop/legona.png");
			mTex_ShopCar[2][1] = add("shop/3.png");
			mTex_ShopCar[3][0] = add("shop/Romeo.png");
			mTex_ShopCar[3][1] = add("shop/4.png");
			mTex_ShopCar[4][0] = add("shop/Javelin.png");
			mTex_ShopCar[4][1] = add("shop/5.png");

			mTex_CLock = add("shop/lock.png");
			mTex_CarS = add("shop/shop.png");
			mTex_ShpT = add("shop/shop_text.png");
			mTex_MapT = add("shop/map_text.png");
			mTex_RaceBtn = add("shop/race.png");
			mTex_RightMrk = add("shop/right_mark.png");
			mTex_SelectB = add("shop/select.png");
			mTex_Speed = add("shop/speed.png");
			mTex_Cntorl = add("shop/controle.png");
			mTex_CashHnd = add("shop/cashin-hand.png");
			mTex_BVlue = add("shop/boat-value.png");
			mTex_Price = add("shop/price.png");
			mTex_RedCir = add("shop/rightbg.png");
			mTex_Button = add("shop/btn.png");
			
			mTex_SpdFill = new SimplePlane[2];
			mTex_SpdFill[0] = add("shop/bar.png");
			mTex_SpdFill[1] = add("shop/bar2.png");
			mTex_Buy = add("shop/buy.png");

			mTex_Target = new SimplePlane[5];
			for (int i = 0; i < mTex_Target.length; i++)
				mTex_Target[i] = add("Level/" + i + ".png");
			mTex_Go = add("Level/Go_btn.png");
			mTex_LvlBox = add("Level/pause_level_bg.png");
			mTex_LvlTxt = add("Level/level_text.png");
			mTex_Bar = add("Level/bar.png");
			mTex_ARadar = add("Powerups/anti-radar.png");
			mTex_Cross = add("Powerups/cross.png");
			mTex_Goggles = add("Powerups/goggless.png");
			mTex_Magnet = add("Powerups/magnet.png");
			mTex_PowerTxt = add("Powerups/powerups_text.png");
			mTex_Free = add("Powerups/free.png");
			mTex_Diamond2 = add("diamond2.png");
			mTex_Magnet2 = add("magnet2.png");
			mTex_killCar = addRotate("Car/minicar_pointer.png");
			mTex_police = addRotate("Car/police-car.png");
			mTex_Finish = add("finishing_line.png");
			
			mTex_BWA	= new SimplePlane[3];
			mTex_SWA	= new SimplePlane[3];
			for(int i=0;i<mTex_BWA.length;i++){
				mTex_BWA[i]	= add("anim/b_water"+i+".png");
				mTex_SWA[i]	= add("anim/s_water"+i+".png");
			}
			
			load_Font();

			mRaodY = new float[5];
			mSRaodY = new float[5];

			mOpp = new Opponent[30];
			for (int i = 0; i < mOpp.length; i++)
				mOpp[i] = new Opponent();
			mPlayer = new Player();
			mCoin = new Coin[50];
			for (int i = 0; i < mCoin.length; i++)
				mCoin[i] = new Coin();

			mDimond = new Coin();
			mMagnet = new Coin();

			mAnim = new Animation[50];
			for (int i = 0; i < mAnim.length; i++) {
				mAnim[i] = new Animation();
			}
//			mBlast = new Blast();
			mFire = new Fire();
			mSky = new Sky();
			mRoket 	= new RoketAnim[20];
			for(int i=0;i<mRoket.length;i++)
			{
				mRoket[i]	= new RoketAnim();
			}
			mBAnim 	= new Blast[30];
			for(int i=0;i<mBAnim.length;i++)
			{
				mBAnim[i]	= new Blast();
			}
			mCoinAnim = new Coin();
			mRpatern = new Coin();
			mRpatern.set(1.8f, -.54f, 0);
			mRMtion = new Coin();
			mRMtion.set(0, mTex_Cloud.width(), 0);
			for(int i=0;i<mRaodY.length;i++)
				mRaodY[i] = -1 +  i*mTex_Road[0].Height();
			for(int i=0;i<mSRaodY.length;i++)
				mSRaodY[i] = -1 +  i*mTex_Road[0].Height();
			for(int i=0;i<mOpp.length;i++){
				mOpp[i].set(-0.93f+0.13f*(M.mRand.nextInt(10)+2), -2.5f, 0.001f+M.mRand.nextInt(40)/10000f,M.mRand.nextInt(9));
			}
//			gameReset();
//			System.out.println(Math.toRadians(90)+"  Roket~~~  ");
		}catch(Exception e){}
		
	}
	void gameReset()
	{
		for(int i=0;i<mRaodY.length;i++)
			mRaodY[i] = -1 +  i*mTex_Road[0].Height();
		for(int i=0;i<mSRaodY.length;i++)
			mSRaodY[i] = -1 +  i*mTex_Road[0].Height();
		for(int i=0;i<mOpp.length;i++){
			mOpp[i].set(-0.93f+0.13f*(M.mRand.nextInt(10)+2), -2.5f, 0.001f+M.mRand.nextInt(40)/10000f,M.mRand.nextInt(9));
		}
		for(int i =0;i<mCoin.length;i++)
			mCoin[i] = new Coin();
		mPlayer.set(7, -.6f, 0,root.car_power[car_Sel]);
		M.Speed = M.SSpeed*root.car_power[car_Sel]*.5f;
		if(car_Sel>2)
			M.Speed = M.SSpeed*root.car_power[car_Sel]*.3f;
		int rand = M.mRand.nextInt(9)+2;
		for(int i =0;i<mCoin.length;i++){
			mCoin[i].set(-0.93f+0.13f*(rand+(i%2)), 1+i*.05f);
		}
		for(int i=0;i<mAnim.length;i++)
		{
			mAnim[i].reset();
		}
		mDimond.set(-0.93f+0.13f*( M.mRand.nextInt(13)), 2);
		mMagnet.set(-0.93f+0.13f*( M.mRand.nextInt(13)), 3);
//		mBlast.set(0, 0,-1);
		mFire.reset();
		
		mNoCoin = 0;
		mNoDimond = 0;
		mCarCount = 0;
		mMeter = 0;
		mBus = 0;
		mKill = 0;
		finish = 2f;
		mSky.set(0, 4, M.mRand.nextInt(3));
		for(int i=0;i<mPower.length;i++)
			mPower[i] = (M.mRand.nextInt(5)==0)?true:false;
		
		for(int i=0;i<mRoket.length;i++)
		{
			mRoket[i].set(-100, 0);
		}
		for(int i=0;i<mBAnim.length;i++)
		{
			mBAnim[i].set(-100, 0);
		}
		mCoinAnim.counter = 100;
		mGoCount =4;
		ADScount++;
		if (!addFree && ADScount % 5 == 0) {
			
			try {
				ads.sendEmptyMessage(0);
			} catch (Exception e) {
			}
		}
		
	}
	int ADScount = 0;
	private Handler ads = new Handler() {public void handleMessage(Message msg) {GameRenderer.mStart.loadInter();}};
	
	
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
			if(M.GameScreen != M.GAMEMENU && M.GameScreen != M.GAMEHELP && !addFree
					&&  M.GameScreen != M.GAMEADD && M.GameScreen != M.GAMELOAD)
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
			if ((M.GameScreen == M.GAMEADD || M.GameScreen == M.GAMELOAD)&& !addFree) {
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
	SimplePlane addRotate(String ID)
    {
		Bitmap b = LoadImgfromAsset(ID);
        SimplePlane SP = null;
        try
        {
                SP = new SimplePlane((b.getWidth()/M.mMaxY),(b.getHeight()/M.mMaxY));
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
			System.out.println(ID+"     "+e.getMessage());
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
		{
			mTex_Font = new SimplePlane[10];
			Bitmap b = LoadImgfromAsset("Level/font.png");
			for (int i = 0; i < mTex_Font.length; i++)
				mTex_Font[i] = addBitmap(Bitmap.createBitmap(b, i * b.getWidth()/ 16, 0, b.getWidth() / 16,b.getHeight(), null, true));
			mTex_$= addBitmap(Bitmap.createBitmap(b, 10 * b.getWidth()/ 16, 0, b.getWidth() / 16,b.getHeight(), null, true));
			mTex_X= addBitmap(Bitmap.createBitmap(b, 11 * b.getWidth()/ 16, 0, b.getWidth() / 16,b.getHeight(), null, true));
			mTex_Pls= addBitmap(Bitmap.createBitmap(b, 12 * b.getWidth()/ 16, 0, b.getWidth() / 16,b.getHeight(), null, true));
		}
		{
			mTex_Coin = new SimplePlane[10];
			Bitmap b = LoadImgfromAsset("coin.png");
			for (int i = 0; i < mTex_Coin.length; i++)
				mTex_Coin[i] = addBitmap(Bitmap.createBitmap(b, i * b.getWidth()/ mTex_Coin.length, 0, b.getWidth() / mTex_Coin.length,b.getHeight(), null, true));
		}
		{
			mTex_Blast = new SimplePlane[8];
			Bitmap b = LoadImgfromAsset("blast.png");
			for (int i = 0; i < mTex_Blast.length; i++)
				mTex_Blast[i] = addBitmap(Bitmap.createBitmap(b, i * b.getWidth()/ mTex_Blast.length, 0, b.getWidth() / mTex_Blast.length,b.getHeight(), null, true));
		}

	}
}
