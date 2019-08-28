package com.hututu.game.trafficracing;

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
	
	
	SimplePlane[] mTex_BG,mTex_OppCar[],mTex_PCar[],mTex_Blast,mTex_Coin,mTex_Font,mTex_UText,mTex_Fill,mTex_UIcn;
	SimplePlane mTex_SCarBG[],mTex_Heli[],mTex_Ling,mTex_LingBar,mTex_Button,mTex_fuelglow,mTex_name,mTex_gore;
	SimplePlane mTex_Logo,mTex_Arrow,mTex_Joy,mTex_Bullet,mTex_FuelBoost,mTex_Feel,mTex_Boost,mTex_SBar,mTex_CBar;
	SimplePlane mTex_Pointer,mTex_Bar,mTex_STxt,mTex_CTxt,mTex_Skip,mTex_Splash,mTex_Map,mTex_Cont/*,mTex_CarT*/;
	SimplePlane mTex_About,mTex_FB,mTex_GP,mTex_HScore,mTex_Play,mTex_Upgrade,mTex_CarBTN,mTex_CarBS/*,mTex_Box*/;
	SimplePlane mTex_Coins,mTex_Back,mTex_Buy,mTex_Vlue,mTex_GOver,mTex_NScore,mTex_BScore,mTex_NLevel,mTex_More;
	SimplePlane mTex_Menu,mTex_View,mTex_Retry,mTex_Shop,mTex_Submit,mTex_Mark,mTex_Next,mTex_Cong,mTex_Rate;
	SimplePlane	mTex_PAgain,mTex_CirSel,mTex_GPuase,mTex_Gun,mTex_Tree,mTex_AllBG,mTex_Smoke,mTex_Loading,mTex_AScr;
	SimplePlane mTex_Truk[][],mTex_ADS;	
	
	BackGround	mBG[];
	Opponent[]	mOpp;
	Opponent[]	mCOpp;
	Player		mPlayer;
	Bullet[]	mBullet;
	Coin[]		mCoin;
	CoinAnim[]	mACoin;
	Coin		mHeli;
	Animation 	mAni[];
	Truk		mTruk[];
	boolean mCarUp;
	
	int RCount;
	int RArry;
	
	int coin = 1;
	int mScore = 0;
	int mHScore = 0;
	int CoinSet =0;
	int oPPset = 50;
	int oPPCnt = 0;
	float cy =0;
	
	MainActivity mMainActivity;
	
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
			
			mTex_ADS		= add("ui/ADS.png");
			mTex_Truk		= new SimplePlane[7][2];
			for(int i =0;i<mTex_Truk.length;i++){
				mTex_Truk[i][0]	= add("truck/"+i+".png");
				mTex_Truk[i][1]	= addBitmap(FlipHorizontal(mTex_Truk[i][0].mBitmap));
			}
			mTex_gore		= add("moregames.png");
			mTex_name		= add("splash_name.png");
			mTex_fuelglow	= add("fuelglow.png");
			mTex_Button		= add("back.png");
			mTex_Ling		= add("loading0.png");
			mTex_LingBar	= add("loading1.png");
			mTex_AScr		= add("about.png");
			mTex_Loading	= add("loading2.png");
			mTex_Smoke		= add("smoke.png");
			mTex_AllBG		= add("gameover.jpg");
			mTex_Heli		= new SimplePlane[2];
			mTex_Heli[0]	= add("heli0.png");
			mTex_Heli[1]	= add("heli1.png");
			mTex_Gun		= add("gun0.png");
			mTex_Tree		= add("coconuttree.png");
			mTex_GPuase		= add("ui/game_pause.png");
			mTex_CirSel		= add("ui/csel.png");
			mTex_PAgain		= add("ui/playagain.png");
			mTex_Rate 		= add("ui/rating.png");
			mTex_More		= add("ui/more.png");
			mTex_Cong		= add("ui/congratulations.png");
			mTex_Next		= add("ui/next_btn.png");
			mTex_Bullet		= add("bullet.png");
			mTex_Mark		= add("mark.png");
			mTex_Menu		= add("ui/menu.png");
			mTex_View		= add("ui/positionbox.png");
			mTex_Retry		= add("ui/retry-119.png");
			mTex_Shop		= add("ui/shop_btn.png");
			mTex_Submit		= add("ui/submitscore_text.png");
			
			mTex_NLevel		= add("ui/game_win.png");
			mTex_BScore		= add("ui/bestscore_text.png");
			mTex_NScore		= add("ui/newscore_text.png");
			
			mTex_GOver		= add("ui/gameover.png");
			mTex_Vlue		= add("ui/valuebox.png");
			mTex_Fill		= new SimplePlane[2];
			mTex_Fill[0]	= add("ui/value_graybox.png");
			mTex_Fill[1]	= add("ui/value_whitebox.png");
			
			mTex_UIcn		= new SimplePlane[3];
			mTex_UIcn[0]	= add("ui/fueltank.png");
			mTex_UIcn[1]	= add("ui/bost.png");
			mTex_UIcn[2]	= add("ui/gun.png");
			
			mTex_Buy		= add("ui/buy.png");
			
			
			mTex_UText		= new SimplePlane[4];
			mTex_UText[0]	= add("ui/fuel.png");
			mTex_UText[1]	= add("ui/Canvas_176.png");
			mTex_UText[2]	= add("ui/bullet.png");
			mTex_UText[3]	= add("ui/damage.png");
			mTex_Upgrade 	= add("ui/Upgrade_text.png");
			mTex_CarBTN		= add("ui/car_btn.png");
			mTex_CarBS		= add("ui/car_btn_select.png");
//			mTex_CarT		= add("ui/car_text.png");
//			mTex_Box		= add("ui/bg_box.png");
			mTex_Coins		= add("ui/coins.png");
			mTex_Back		= add("ui/back.png");
			mTex_SCarBG		= new SimplePlane[2];
			mTex_SCarBG[0]	= add("ui/shopcar_bg.png");
			mTex_SCarBG[1]	= add("ui/graybtn.png");
			
			mTex_Cont		= add("ui/continue_text.png");
			mTex_About		= add("ui/about.png");
			mTex_FB			= add("ui/Fb.png");
			mTex_GP			= add("ui/g+.png");
			mTex_HScore		= add("ui/highscore.png");
			mTex_Play		= add("ui/play.png");
			
			
			mTex_Map		= add("map.jpg");
			mTex_Splash		= add("splash.jpg");
			mTex_FuelBoost	= add("ui/fuel_boost.png");
			mTex_Feel		= add("ui/Avinyo.png");
			mTex_Arrow		= add("ui/up_downkey.png");
			mTex_Joy		= add("ui/gun_GP.png");
			mTex_Boost		= add("ui/boost_GP.png");
			mTex_SBar		= add("ui/scorebox_GP.png");
			mTex_CBar		= add("ui/coinbox_GP.png");
			mTex_STxt		= add("ui/score.png");
			mTex_CTxt		= add("ui/coin_text.png");
			
			mTex_Pointer	= add("ui/arow.png");
			mTex_Bar 		= add("hightbar0.png");
			mTex_Logo		= add("hututugames.png");
			mTex_Skip		= add("exit_icon.png");
			mTex_BG			= new SimplePlane[12];
			for(int i =0;i<mTex_BG.length;i++)
				mTex_BG[i]	= add("road/"+i+".png");
			
			Bitmap b 		= LoadImgfromAsset("blast.png");
			mTex_Blast		= new SimplePlane[13];
			for(int i=0;i<mTex_Blast.length;i++)
				mTex_Blast[i] = addBitmap(Bitmap.createBitmap(b, i*b.getWidth()/mTex_Blast.length, 0,b.getWidth()/mTex_Blast.length, b.getHeight(), null, true));
			
			b.recycle();
			b	 			= LoadImgfromAsset("coin.png");
			mTex_Coin		= new SimplePlane[10];
			for(int i=0;i<mTex_Coin.length;i++)
				mTex_Coin[i] = addBitmap(Bitmap.createBitmap(b, i*b.getWidth()/mTex_Coin.length, 0,b.getWidth()/mTex_Coin.length, b.getHeight(), null, true));
			
			
			mTex_OppCar		= new SimplePlane[10][3];
			for(int i=0;i<mTex_OppCar.length;i++){
				mTex_OppCar[i][0]	= add("car/"+(i*2+1)+".png");
				mTex_OppCar[i][1]	= add("car/"+(i*2+2)+".png");
				mTex_OppCar[i][2]	= add("car/s/"+(i*2+2)+".png");
			}
			
			mTex_PCar		= new SimplePlane[4][2];
			for(int i=0;i<mTex_PCar.length;i++){
				mTex_PCar[i][0]= add("uCar/"+(i+1)+".png");
				mTex_PCar[i][1]= add("uCar/s/"+(i+1)+".png");
			}
			
			
			load_Font();
			mBG				= new BackGround[10];
			for(int i =0;i<mBG.length;i++)
			{
				mBG[i] = new BackGround();
			}
			
			mOpp			= new Opponent[8];
			for(int i =0;i<mOpp.length;i++)
			{
				mOpp[i] 	= new Opponent();
			}
			mCOpp			= new Opponent[3];
			for(int i =0;i<mCOpp.length;i++)
			{
				mCOpp[i] 	= new Opponent();
			}
			mBullet			= new Bullet[20];
			for(int i =0;i<mBullet.length;i++)
			{
				mBullet[i] 	= new Bullet();
			}
			mCoin		= new Coin[20];
			for(int i =0;i<mCoin.length;i++)
			{
				mCoin[i]	= new Coin();
			}
			
			mACoin		= new CoinAnim[8];
			for(int i =0;i<mACoin.length;i++)
			{
				mACoin[i]	= new CoinAnim();
			}
			
			mPlayer			= new Player();
			mHeli			= new Coin();
			mAni			= new Animation[5];
			for(int i =0;i<mAni.length;i++)
				mAni[i]		= new Animation();
			
			mTruk			= new Truk[2];	
			mTruk[0]		= new Truk(0);
			mTruk[1]		= new Truk(1);
		
//			gameReset();
		}catch(Exception e){}
	}
	
//	mGR.mOpp[i].set(mGR.mOpp[i==0?mGR.mOpp.length-1:i-1].x + 1.1f, M.mRand.nextFloat()-.5f);
	void gameReset()
	{
		M.playStop();
		CoinSet =0;
		RArry = 0;
		RCount = 0;
		RCount = BackGround.Road[RArry];
		for(int i =0;i<mBG.length;i++)
		{
			mBG[i].set(-1+(i*mTex_BG[0].width()), 0,RArry);
			RCount--;
			if(RCount<=0)
			{
				RArry++;
				RArry %= BackGround.Road.length;
				RCount = BackGround.Road[RArry];
			}
		}
		for(int i =0;i<mOpp.length;i++)
		{
			mOpp[i].set(-1.5f,M.mRand.nextFloat()-.5f);
		}
		for(int i =0;i<mCOpp.length;i++)
		{
			mCOpp[i].set(-3,0);
		}
		for(int i =0;i<mBullet.length;i++)
		{
			mBullet[i].set(100, 100);
		}
		for(int i =0;i<mCoin.length;i++,CoinSet++)
		{
			mCoin[i].set(i*.2f, cy);
			if(CoinSet%10==0)
				cy =-.5f + M.mRand.nextFloat(); 
		}
		mPlayer.set(-.5f, 0);
		mScore=0;
		M.SPEED = M.CSPEED;
		oPPset = 50;
		oPPCnt =0;
		M.GameScreen = M.GAMEPLAY;
		root.Counter =0;
		if (ads % 1 == 0) {
			try {
				adsHandler.sendEmptyMessage(0);
			} catch (Exception e) {
			}
		}
	}
	int ads = 0;
	Handler adsHandler = new Handler() {
		public void handleMessage(Message msg) {
			mStart.loadInter();
		}
	};
	void Newgame()
	{
		mPlayer.reset();
		M.uLevel = 0;
		M.GameScreen = M.GAMEMENU;
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
			if(M.GameScreen == M.GAMEOVER || M.GameScreen == M.GAMEWIN  || M.GameScreen == M.GAMEPAUSE)
			{
				int inv=mStart.adView.getVisibility();
				if(inv==AdView.GONE ){try{handler.sendEmptyMessage(AdView.VISIBLE);}catch(Exception e){}}
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
	void load_Font() {
		mTex_Font = new SimplePlane[10];
		Bitmap b = LoadImgfromAsset("fontstrip.png");
		for (int i = 0; i < mTex_Font.length; i++)
			mTex_Font[i] = addBitmap(Bitmap.createBitmap(b, i * b.getWidth()
					/ mTex_Font.length, 0, b.getWidth() / mTex_Font.length,
					b.getHeight(), null, true));
	}
}
