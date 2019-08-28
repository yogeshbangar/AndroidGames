package com.oneday.games24.extreme.drag.racing;

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

public class GameRenderer implements Renderer  {
	final Group root;
	public static Context mContext;
	public static Start mStart;
	long startTime = System.currentTimeMillis();
	int resumeCounter = 0;
	int mSel = 0;
	
	String mPName = "Sing In";
	
	Spec mSpec[];
	Animation mAnim[];
	Shift mShift;
	Font mFont;
	Bike mPlayer;
	Bike mOpponent;
	
	Smock mSmock[];
	
	float mBikex = 0;
	float mBikevx = 0;
	float mFLine = 1.2f;
	float mBGX0[] = new float[2];
	float mBGX1[] = new float[2];
	float mTowerX = -.41f;
	float mSpeed = 0;
	float gamedistace = 0;
	
	int mIsGameWin = 0;
	int city = 0;
	int start = 0;
	int mLevel =0;
	int mBike = 0;
	int mDoller = 99;
	int upgrade = 0;
	int waitCounter = 0;
	int mScore = 0;
	int showCount = 0;
	long Gamestart = 0;
	
	boolean gameUnlock[][];
	boolean gamePlayed[][];
	boolean mBikeUnlock	[]= new boolean[17];
	boolean mAchiUnlock	[]= new boolean[6];
	boolean mIsQuckRace = false;
	boolean citiUnlock = false;
	boolean SingUpadate= false;
	boolean addFree = false;
	boolean fromLevel = false;
	private Handler handler2 = new Handler() {public void handleMessage(Message msg) {mStart.adHouse.setVisibility(msg.what);}};//AdHouse

	public GameRenderer(Context context) {
		mContext = context;
		mStart = (Start) mContext;
		root = new Group(this);
		init();
	}

	void init() {
		try {
			mTex_Mock = add("smoke/0.png");
			mTex_Smock = new SimplePlane[2];
			for (int i = 0; i < mTex_Smock.length; i++)
				mTex_Smock[i] = add("smoke/" + (i+1) + ".png");

			mTex_Adsfree = add("-ads_btn.png");
//			mTex_Buy2  = add("buy_red.png");
			mTex_Conti = add("continue.png");
			mTex_SLine = add("BG/white_strip.png");
			mTex_Ad = new SimplePlane[7];
			for(int i=0;i<mTex_Ad.length;i++)
				mTex_Ad[i] = add("BG/Ad/"+i+".png");
			mTex_Ling = add("loading0.png");
			mTex_LBar = add("loading1.png");
			mTex_Flame = new SimplePlane[3];
			mTex_Flame[0] = addRotate("flame1.png");
			mTex_Flame[1] = addRotate("flame2.png");
			mTex_Ray = addRotate("Splash/rays.png");
			mTex_Name = new SimplePlane[3];
			for (int i = 0; i < mTex_Name.length; i++)
				mTex_Name[i] = add("Splash/b" + i + ".png");

			mTex_SBack = new SimplePlane[4];
			for (int i = 0; i < mTex_SBack.length; i++)
				mTex_SBack[i] = add("Splash/a" + i + ".png");
			mTex_SBike = new SimplePlane[4];
			mTex_SBike[0] = add("Splash/splash_bike3.png");
			mTex_SBike[1] = add("Splash/Splash_bike_tyre.png");
			mTex_SBike[2] = add("Splash/tyre_patch0.png");
			mTex_SBike[3] = add("Splash/tyre_patch1.png");
			
			mTex_Splash = new SimplePlane[5];
			for (int i = 0; i < mTex_Splash.length; i++)
				mTex_Splash[i] = add("Splash/" + i + ".png");
			mTex_PopBuy = add("Buy_window.png");
			mTex_Popup = add("enough_money_back.png");
			mTex_anim = new SimplePlane[3];
			mTex_anim[0] = addRotate("gears.png");
			mTex_anim[1] = addRotate("gears2.png");
			mTex_anim[2] = addRotate("gears3.png");

			mTex_Gear = new SimplePlane[2];
			mTex_Gear[0] = add("Garage/gear_lock.png");
			mTex_Gear[1] = add("Garage/gear_install.png");
			mTex_Nitro = new SimplePlane[2];
			mTex_Nitro[0] = add("Garage/nitro_lock.png");
			mTex_Nitro[1] = add("Garage/nitro_install.png");
			mTex_Exhaus = new SimplePlane[2];
			mTex_Exhaus[0] = add("Garage/exhaus_lockt.png");
			mTex_Exhaus[1] = add("Garage/exhaust_install.png");
			mTex_Engin = new SimplePlane[2];
			mTex_Engin[0] = add("Garage/engine_lock.png");
			mTex_Engin[1] = add("Garage/engine_install.png");
			mTex_UpArrow = add("Garage/garagr_arrow.png");
			mTex_UpBox = add("Garage/upgrade_box.png");
			mTex_Strip = add("Garage/Upgrate_strip.png");
			mTex_LevelBoxS = add("Maplevel/level_box_select.png");
			mTex_Stand = add("upgrate_bike_stand.png");
			mTex_Lock = new SimplePlane[3];
			mTex_Lock[0] = add("Maplevel/lock.png");
			mTex_Lock[1] = add("Maplevel/Un_lock.png");
			mTex_Lock[2] = add("Maplevel/level_lock2.png");
			mTex_GoB = add("Maplevel/go_btn.png");
			mTex_LevelBack = add("Maplevel/level_back.png");
			mTex_LevelBox = add("Maplevel/level_box.png");
			mTex_LevelCup = add("Maplevel/level_cup.png");
			mTex_LevelDollar = add("Maplevel/level_dollar.png");
			mTex_Map2 = add("Maplevel/map2.png");
			mTex_MapBox = add("Maplevel/Map_box.png");
			mTex_MapDot = add("Maplevel/map_dot.png");
			mTex_MapDot2 = add("Maplevel/map_dot2.png");

			mTex_GarageB2 = add("comp/garage_btn.png");
			mTex_BackB = add("comp/back_btn.png");
			mTex_HintB = add("comp/hint_btn.png");
			mTex_NextN = add("comp/next_btn.png");
			mTex_RetryB = add("comp/retry_btn.png");
			mTex_ShopBO = add("comp/shop_btn.png");
			mTex_LostTxt = add("comp/you-lost_text.png");
			mTex_WinLstBG = add("comp/youwin_lost-bg.png");
			mTex_WinTxt = add("comp/youwin_text.png");

			mTex_Arrow = new SimplePlane[4];
			mTex_Arrow[0] = add("L-move.png");
			mTex_Arrow[1] = add("R-move.png");

			mTex_BykFG = add("bike_bg.png");
			mTex_ICN = new SimplePlane[6];
			for (int i = 0; i < mTex_ICN.length; i++)
				mTex_ICN[i] = add("icn" + i + ".png");
			mTex_ComanBG = add("ComanBG.png");
			mTex_Sing = add("sign_in.png");
			mTex_GarageB = add("garage_btn.png");
			mTex_PowerBar = add("power_bar.png");
			mTex_PowerFill = add("power_barfill.png");
			mTex_RaceB = add("race.png");
			mTex_ShopB = add("shop.png");
			mTex_QRaceB = add("quick_race.png");
			mTex_CareerB = add("career_btn.png");
			mTex_Buy = add("buy_btn.png");

			mTex_Logo = add("logo.png");
			mTex_Driver = new SimplePlane[3];
			mTex_Driver[0] = add("bike/for_bike_15_16.png");
			mTex_Driver[1] = add("bike/p1.png");
			mTex_Driver[2] = add("bike/p2.png");
			mTex_Bike = new SimplePlane[17];

			for (int i = 0; i < mTex_Bike.length; i++)
				mTex_Bike[i] = add("bike/" + i + ".png");

			mTex_Tyre = new SimplePlane[17][2];
			for (int i = 0; i < mTex_Tyre.length; i++) {
				mTex_Tyre[i][0] = addRotate("bike/" + i + "tb.png");
				mTex_Tyre[i][1] = addRotate("bike/" + i + "tf.png");
			}

			mTex_BG = new SimplePlane[2];
			mTex_BG[0] = add("BG/building.png");
			mTex_BG[1] = add("BG/final_bg.png");

			mTex_Bar = add("BG/bar.png");
			mTex_Pointer = new SimplePlane[2];
			mTex_Pointer[0] = add("BG/pointer1.png");
			mTex_Pointer[1] = add("BG/pointer2.png");

			mTex_FLine = add("BG/finish_line.png");
			mTex_GearShift = add("BG/gear_shift_1.png");
			mTex_Shift = new SimplePlane[3];
			mTex_Shift[0] = add("BG/gear_shift_4.png");
			mTex_Shift[1] = add("BG/gear_shift_3.png");
			mTex_Shift[2] = add("BG/gear_shift_2.png");

			mTex_ShiftTxt = new SimplePlane[3];
			mTex_ShiftTxt[0] = add("BG/good-shift.png");
			mTex_ShiftTxt[1] = add("BG/perfect-shift.png");
			mTex_ShiftTxt[2] = add("BG/over_drive.png");
			mTex_Kata = addRotate("BG/kata.png");

			mTex_Light = new SimplePlane[3];
			mTex_Light[0] = add("BG/white.png");
			mTex_Light[1] = add("BG/yellow.png");
			mTex_Light[2] = add("BG/green.png");

			mTex_Handle = new SimplePlane[3];
			mTex_Handle[0] = add("BG/handle.png");
			mTex_Handle[1] = add("BG/handle2.png");

			mTex_Tower = add("BG/light.png");
			mTex_Meter = add("BG/meter.png");
			mTex_Shadow = add("BG/shadow.png");
			mTex_ULost = add("BG/you-lost.png");
			mTex_UWin = add("BG/you-win.png");

			mTex_Star = add("BG/star.png");

			mTex_City = new SimplePlane[7][2];

			mTex_City[0][0] = add("Maplevel/Singapore.png");
			mTex_City[0][1] = add("Maplevel/Singapore6.png");

			mTex_City[1][0] = add("Maplevel/thailand.png");
			mTex_City[1][1] = add("Maplevel/thailand7.png");

			mTex_City[2][0] = add("Maplevel/hong-kong.png");
			mTex_City[2][1] = add("Maplevel/hong-kong4.png");

			mTex_City[3][0] = add("Maplevel/russia.png");
			mTex_City[3][1] = add("Maplevel/russia2.png");

			mTex_City[4][0] = add("Maplevel/Rio.png");
			mTex_City[4][1] = add("Maplevel/Rio8.png");

			mTex_City[5][0] = add("Maplevel/spain.png");
			mTex_City[5][1] = add("Maplevel/spain3.png");

			mTex_City[6][0] = add("Maplevel/New-york.png");
			mTex_City[6][1] = add("Maplevel/New-york1.png");

			mFont = new Font();

			mPlayer = new Bike();
			mOpponent = new Bike();

			mSpec = new Spec[17];
			mSpec[0] = new Spec("TRIPAL SEVEN", 0);
			mSpec[1] = new Spec("KT 1000R", 1);
			mSpec[2] = new Spec("GRAPTOR B1", 2);
			mSpec[3] = new Spec("BOXER GT", 3);
			mSpec[4] = new Spec("ATM 110", 4);
			mSpec[5] = new Spec("SRV 6", 5);
			mSpec[6] = new Spec("MONSTER KING", 6);
			mSpec[7] = new Spec("FZ 12000", 7);
			mSpec[8] = new Spec("STREETFIGHTER", 8);
			mSpec[9] = new Spec("CRB 777Z", 9);
			mSpec[10] = new Spec("MT 2K", 10);
			mSpec[11] = new Spec("GT 650", 11);
			mSpec[12] = new Spec("DUKES 290", 12);
			mSpec[13] = new Spec("MK2 1100S", 13);
			mSpec[14] = new Spec("TUNON V4", 14);
			mSpec[15] = new Spec("DETONAL 300R", 15);
			mSpec[16] = new Spec("YAMA Z SPORTS", 16);

			gamePlayed = new boolean[7][10];
			gameUnlock = new boolean[7][10];
			for (int i = 0; i < gameUnlock.length; i++)
				gameUnlock[i][0] = true;
			mAnim = new Animation[8];
			for (int i = 0; i < 8; i++) {
				mAnim[i] = new Animation(i);
			}
			mShift = new Shift();
			mBikeUnlock[0]	= true;
			mSmock	= new Smock[10];
			for(int i=0;i<mSmock.length;i++)
				mSmock[i]	= new Smock();
			gameReset();
		} catch (Exception e) {
		}
	}

	void gameReset() {
		int opp = (int) ((city * 10 + mLevel) / 4.5f);
		float val = ((city * 10 + mLevel) - opp*4.5f); 
		mPlayer.set	 (-.50f, -.18f, 
				mSpec[mBike].getExhost(), mSpec[mBike].getNitro(), mSpec[mBike].getWeight(),mSpec[mBike].getGearBox(),mBike);
		mOpponent.set(-.50f, 0.30f, 
				 mSpec[opp].get4OppExhost(val), mSpec[opp].get4OppNitro(val), mSpec[opp].get4OppWeight(val),mSpec[opp].get4OppGearBox(val),opp);
		
		mOpponent.Gear = 1;
		mOpponent.rpm = 50;
		mBGX0[0] = 0;
		mBGX0[1] = mTex_BG[0].width();
		mBGX1[0] = 0;
		mBGX1[1] = mTex_BG[1].width();
		mSpeed = -0.1f;
		mTowerX = -.41f;
		start = 0;
		gamedistace = city*10+50;
		mFLine = gamedistace;
		mIsGameWin = 0;
		mShift.set(100,0);
		Gamestart = System.currentTimeMillis();
		root.Counter = 0;
		waitCounter =0;
		citiUnlock = false;
//		if(!mStart.interstitialAdisReady())
		if(showCount%3==0)
		{
			try {adshandler.sendEmptyMessage(0);} catch (Exception e) {}
		}
		showCount++;
	}
	private Handler adshandler = new Handler() {public void handleMessage(Message msg) {mStart.loadInter();}};
	
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
		if (mStart.adHouse != null) {
			if (M.GameScreen == M.GAMEADD || M.GameScreen == M.GAMELOAD) {
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
	public static SimplePlane add (String ID)
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
	public static SimplePlane addBitmap (Bitmap b)
	{
		SimplePlane SP = null;
		try
		{
			SP = new SimplePlane((b.getWidth()/M.mMaxX),(b.getHeight()/M.mMaxY));
			SP.loadBitmap(b);// R.drawable.jay
		}catch(Exception e){}
		return SP;
	}
	public static SimplePlane addRotate(String ID)
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
	public static Bitmap LoadImgfromAsset(String ID)
	{
		try{
			return BitmapFactory.decodeStream(mContext.getAssets().open(ID));}
		catch(Exception e)
		{
			System.out.println(ID+"  ~~~~~~~~~~~~~  "+e.getMessage());
			return null;
		}
	}
	public static Bitmap resizeImg(Bitmap bitmapOrg,int newWidth,int newHeight)
	{
		 int width = bitmapOrg.getWidth();
		 int height = bitmapOrg.getHeight();
		 float scaleWidth 	= ((float) newWidth) / width;
		 float scaleHeight = ((float) newHeight) / height;
		
		 Matrix matrix = new Matrix();
		 matrix.postScale(scaleWidth, scaleHeight);
		 matrix.postRotate(0);
		 Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0,width, height, matrix, true);
//		 Log.d("resizeImg========","newWidth ["+newWidth+"] newHeight ["+newHeight+"]");
		 return resizedBitmap;
	}
	public static Bitmap FlipHorizontal(Bitmap bitmapOrg)
	{
		Matrix matrix = new Matrix();
		matrix.postScale(-1f, 1f);
		matrix.postRotate(0);
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0,bitmapOrg.getWidth(), bitmapOrg.getHeight(), matrix, true);
		return resizedBitmap;
	}
	SimplePlane mTex_PopBuy,mTex_LBar,mTex_Sing,mTex_UpArrow,mTex_WinTxt,mTex_Adsfree,mTex_GarageB2,mTex_GoB;
	SimplePlane mTex_Bar,mTex_FLine,mTex_GearShift, mTex_Tower, mTex_Meter, mTex_Shadow,mTex_ULost,mTex_UWin;
	SimplePlane mTex_Kata,mTex_Star, mTex_Logo,mTex_ComanBG,mTex_GarageB,mTex_PowerBar,mTex_BykFG,mTex_Conti;
	SimplePlane[] mTex_Driver,mTex_SBack,mTex_Arrow,mTex_City[],mTex_Engin,mTex_Exhaus,mTex_Smock,mTex_Nitro;
	SimplePlane mTex_PowerFill,mTex_RaceB,mTex_ShopB, mTex_QRaceB,mTex_CareerB,mTex_Buy,mTex_SLine/*,mTex_Buy2*/;
	SimplePlane mTex_BackB,mTex_HintB,mTex_NextN,mTex_RetryB,mTex_ShopBO,mTex_LostTxt,mTex_Ray,mTex_WinLstBG;
	SimplePlane[] mTex_Bike,mTex_Tyre[],mTex_BG,mTex_Pointer,mTex_Shift,mTex_ShiftTxt,mTex_Light,mTex_Handle;
	SimplePlane mTex_LevelBack,mTex_LevelBox,mTex_LevelCup,mTex_LevelDollar,mTex_Map2,mTex_MapBox,mTex_Popup;
	SimplePlane[] mTex_anim,mTex_Splash,mTex_ICN,mTex_SBike,mTex_Name,mTex_Flame,mTex_Lock,mTex_Ad,mTex_Gear;
	SimplePlane mTex_MapDot,mTex_MapDot2,mTex_Stand,mTex_LevelBoxS,mTex_Strip,mTex_UpBox,mTex_Mock,mTex_Ling;
	
	
	
}
