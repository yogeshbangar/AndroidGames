package com.oneday.games24.clashofbattles;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.opengl.GLSurfaceView.Renderer;

public class GameRenderer implements Renderer 
{
	final Group root;
	public static Context mContext;
	public static Start mStart;
	long startTime = System.currentTimeMillis();
	
		
	SimplePlane mTex_Hututu, mTex_Sky , mTex_Spark , mTex_Hand, mTex_Smock,mTex_Timer;
	SimplePlane mTex_Fill, mTex_BGBox ,mTex_BuyBtn , mTex_Meter, mTex_Bar, mTex_Reset;
	SimplePlane mTex_ShopSield, mTex_Dot , mTex_SelectBtn , mTex_CancleBtn ,mTex_Lock;
	SimplePlane mTex_SText, mTex_Cong, mTex_KmBar , mTex_Coln, mTex_Shiled ,mTex_BuyW;
	SimplePlane mTex_About, mTex_More, mTex_Optin, mTex_Play,  mTex_Splash , mTex_Ads;
	SimplePlane mTex_Kata, mTex_Yellow, mTex_Red, mTex_Over, mTex_Kill , mTex_AbutScr;
	SimplePlane mTex_Wall, mTex_Total, mTex_Reward, mTex_Dist, mTex_Result,mTex_miles;
	SimplePlane mTex_Again,mTex_Resume,mTex_Pause,mTex_RewardBtn;
	SimplePlane[] mTex_Road, mTex_Car, mTex_CTyre, mTex_Gun, mTex_OppCar, mTex_Bullet;
	SimplePlane[] mTex_Fan, mTex_OppTyre, mTex_Coin, mTex_Fire, mTex_Blast, mTex_Icon;
	SimplePlane[] mTex_Scar, mTex_SName, mTex_Font, mTex_Tab, mTex_PName, mTex_OppMis;

	
	boolean shopCarDrive = false;
	
	boolean SingUpadate = false;
	
	boolean addFree = false;
	boolean mAchiUnlock[] = new boolean[5];
	
	byte carSel = 0;
	byte carDrive[] = new byte[3];
	byte topCount;
	byte overCount = 0;
	byte oppseq[][];
	byte Bullate = 0;
	
	int mCoin =0;
	int kill =0;
	int Totalkill =0;
	int killVal =0;
	int Wall =0;
	int Rewrd =0;
	int tcalcu =0;
	
	float spd = -.1f;
	float distance = 0;
	float Tdistance = 0;
	
	
	Vahicle mPlayCar[];
	Dusman mDusman[];
	Background[] mTop,mCenter,mDown;
	Missile mMissile[];
	Bulltet mBulltet[];
	Blast mBlast[];
	PMissile mPMissile[];
	CarValue  mCarValue[];
	Smock mSmock[];
	
//	InApp mInApp;
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
//			mInApp = new InApp();
//			mInApp.onCreate();
			mTex_Again = add("ui/play_again.png");
			mTex_Resume = add("ui/resume.png");
			mTex_Pause = add("ui/game_paused_-text.png");
			mTex_miles = add("ui/miles.png");
			mTex_Ads = add("ui/ads.png");
			mTex_BuyW = add("ui/buy_window.png");
			mTex_AbutScr= add("ui/about_window.png");
			mTex_Lock = add("ui/locked2.png");
			mTex_Reset = add("ui/reset.png");
			Bitmap b = LoadImgfromAsset("Splash/play_opt_btn.png");
			mTex_About = add("Splash/i.png");
			mTex_More = add("Splash/more_games.png");
			mTex_Optin = addBitmap(Bitmap.createBitmap(b, 1*(b.getWidth()/2), 0,b.getWidth()/2, b.getHeight(), null, true));
			mTex_Play  = addBitmap(Bitmap.createBitmap(b, 0*(b.getWidth()/2), 0,b.getWidth()/2, b.getHeight(), null, true));
			mTex_Splash = add("Splash/splash.jpg");
			mTex_SText = add("Splash/splash_text.png");
			
			b = LoadImgfromAsset("ui/game_over_text_sprite.png");
			mTex_Kill	= addBitmap(Bitmap.createBitmap(b, 0*(b.getWidth()/4), 0,b.getWidth()/4, b.getHeight()/2, null, true));
			mTex_Wall	= addBitmap(Bitmap.createBitmap(b, 1*(b.getWidth()/4), 0,b.getWidth()/4, b.getHeight()/2, null, true));
			mTex_Total	= addBitmap(Bitmap.createBitmap(b, 2*(b.getWidth()/4), 0,b.getWidth()/4, b.getHeight()/2, null, true));
			mTex_Reward	= addBitmap(Bitmap.createBitmap(b, 3*(b.getWidth()/4), 0,b.getWidth()/4, b.getHeight()/2, null, true));
			mTex_Dist	= addBitmap(Bitmap.createBitmap(b, 0*(b.getWidth()/2), b.getHeight()/2,b.getWidth()/2, b.getHeight()/2, null, true));
			mTex_Result	= addBitmap(Bitmap.createBitmap(b, 1*(b.getWidth()/2), b.getHeight()/2,b.getWidth()/2, b.getHeight()/2, null, true));
			mTex_Coln = add("ui/dot.png");
			mTex_Shiled = add("final_car/power.png");
			mTex_Hututu = add("hututugames.png");
			mTex_Meter = add("ui/meter_bg.png");
			mTex_Bar = add("ui/bar.png");
			mTex_KmBar = add("ui/km_bar.png");
			mTex_Kata = addRotate("ui/kata.png");
			mTex_Yellow = add("ui/yellow.png");
			mTex_Red = add("ui/red.png");
			mTex_Over = add("ui/game-over-text.png");
			mTex_Fill = add("ui/bar_fill.png");
			mTex_Cong = add("ui/congratulation.png");
			mTex_Sky = add("bg/sky.png");
			mTex_Road = new SimplePlane[4];
			for(int i=0;i<mTex_Road.length;i++)
				mTex_Road[i] = add("bg/"+i+".png");
			
			b.recycle();
			mTex_Car = new SimplePlane[15];
			Bitmap b1 = LoadImgfromAsset("final_car/car1.png");
			b = LoadImgfromAsset("final_car/car0.png");
			for(int i=0;i<mTex_Car.length;i++)
			{
				if(i<8){
					mTex_Car[i] = addBitmap(Bitmap.createBitmap(b, (i%4)*(b.getWidth()/4), (i/4)*(b.getHeight()/2),b.getWidth()/4, b.getHeight()/2, null, true));
				}else
					mTex_Car[i] = addBitmap(Bitmap.createBitmap(b1, ((i-8)%4)*(b1.getWidth()/4), ((i-8)/4)*(b1.getHeight()/2),b1.getWidth()/4, b1.getHeight()/2, null, true));
			}
			
			
			mTex_CTyre = new SimplePlane[5];
			for(int i=0;i<mTex_CTyre.length;i++)
				mTex_CTyre[i] = addRotate("final_car/T"+i+".png");
			
			mTex_Gun = new SimplePlane[7];
			for(int i=0;i<mTex_Gun.length;i++)
				mTex_Gun[i] = addRotate("final_car/g"+i+".png");
			mTex_Spark = add("final_car/raygun_spark.png");
			
			mTex_Bullet = new SimplePlane[4];
			mTex_Bullet[0] = addRotate("final_car/bullet.png");
			mTex_Bullet[1] = add("final_car/loncher_bomb.png");
			mTex_Bullet[2] = addRotate("final_car/lazer_bullet.png");
			mTex_Bullet[3] = addRotate("final_car/lazer_bullet2.png");
			
			mTex_Smock = add("final_car/smoke.png");
			mTex_OppCar = new SimplePlane[9];
			for(int i=0;i<mTex_OppCar.length;i++)
				mTex_OppCar[i] = add("opp/"+i+".png");
			
			mTex_OppTyre = new SimplePlane[5];
			for(int i=0;i<mTex_OppTyre.length;i++)
				mTex_OppTyre[i] = addRotate("opp/t"+i+".png");
			mTex_Hand = addRotate("opp/hand.png");
			mTex_Fire = new SimplePlane[2];
			mTex_Fire[0] = addRotate("opp/fire.png");
			mTex_Fire[1] = addRotate("opp/minigun_fair0.png");
			mTex_Fan = new SimplePlane[2];
			mTex_Fan[0] = add("opp/fan0.png");
			mTex_Fan[1] = addRotate("opp/fan1.png");
			b.recycle();
			mTex_Blast = new SimplePlane[15];
			b = LoadImgfromAsset("blast_sprite.png");
			for(int i=0;i<mTex_Blast.length;i++)
			{
				mTex_Blast[i] = addBitmap(Bitmap.createBitmap(b, (i%4)*(b.getWidth()/4), (i/4)*(b.getHeight()/4),b.getWidth()/4, b.getHeight()/4, null, true));
			}
			mTex_OppMis = new SimplePlane[2];
			mTex_OppMis[0] = addRotate("opp/missile.png");
			mTex_OppMis[1] = add("opp/tank.png");
			
			
			mTex_BGBox = add("ui/final_garage.jpg");
			b.recycle();
			mTex_Scar = new SimplePlane[15];
			b = LoadImgfromAsset("ui/all_car.png");
			for(int i=0;i<mTex_Scar.length;i++){
				mTex_Scar[i] = addBitmap(Bitmap.createBitmap(b, (i%4)*(b.getWidth()/4), (i/4)*(b.getHeight()/4),b.getWidth()/4, b.getHeight()/4, null, true));
			}
			
			b.recycle();
			mTex_SName = new SimplePlane[15];
			b = LoadImgfromAsset("ui/car_text.png");
			for(int i=0;i<mTex_SName.length;i++){
				mTex_SName[i] = addBitmap(Bitmap.createBitmap(b, (i%4)*(b.getWidth()/4), (i/4)*(b.getHeight()/4),b.getWidth()/4, b.getHeight()/4, null, true));
			}
			
			b.recycle();
			mTex_Tab = new SimplePlane[3];
			b = LoadImgfromAsset("ui/selection.png");
			mTex_Tab[0] = addBitmap(Bitmap.createBitmap(b, 0*(b.getWidth()/4), 0,b.getWidth()/4, b.getHeight(), null, true));
			mTex_Tab[1] = addBitmap(Bitmap.createBitmap(b, 1*(b.getWidth()/4), 0,b.getWidth()/4, b.getHeight(), null, true));
			mTex_Tab[2] = addBitmap(Bitmap.createBitmap(b, 2*(b.getWidth()/4), 0,b.getWidth()/4, b.getHeight(), null, true));
			
			mTex_Timer = add("ui/timer.png");
			mTex_ShopSield = add("ui/power-shield.png");
			mTex_Dot = add("ui/white.png");
			
			
			mTex_SelectBtn = add("ui/select_btn.png");
			mTex_CancleBtn = add("ui/cancle_btn.png");
			mTex_BuyBtn = add("ui/buy_btn.png");
			mTex_RewardBtn= add("ui/rewards.png");
			mTex_PName = new SimplePlane[5];
			mTex_PName[0] = add("ui/launcher.png");
			mTex_PName[1] = add("ui/minigun_text.png");
			mTex_PName[2] = add("ui/missle_attack_text.png");
			mTex_PName[3] = add("ui/power-shield_text.png");
			mTex_PName[4] = add("ui/timet_text.png");
			
			
			b.recycle();
			mTex_Icon = new SimplePlane[16];
			b = LoadImgfromAsset("ui/all_icon.png");
			for(int i=0;i<mTex_Icon.length;i++){
				mTex_Icon[i] = addBitmap(Bitmap.createBitmap(b, (i%8)*(b.getWidth()/8), (i/8)*(b.getHeight()/2),b.getWidth()/8, b.getHeight()/2, null, true));
			}
			
			b.recycle();
			mTex_Coin = new SimplePlane[10];
			b = LoadImgfromAsset("ui/coin.png");
			for(int i=0;i<mTex_Coin.length;i++){
				mTex_Coin[i] = addBitmap(Bitmap.createBitmap(b, i*(b.getWidth()/10), 0,b.getWidth()/10, b.getHeight(), null, true));
			}
			loadFont();
			
			mPlayCar = new Vahicle[3];
			for(int i=0;i<3;i++){
				mPlayCar[i] = new Vahicle();
			}
			mDusman = new Dusman[60];
			for(int i =0;i<mDusman.length;i++){
				mDusman[i] = new Dusman();
			}
			
			
			mMissile = new Missile[20];
			for(int i =0;i<mMissile.length;i++){
				mMissile[i] = new Missile();
			}
			
			mBulltet = new Bulltet[100];
			for(int i =0;i<mBulltet.length;i++){
				mBulltet[i] = new Bulltet();
			}
			mBlast = new Blast[100];
			for(int i =0;i<mBlast.length;i++){
				mBlast[i] = new Blast();
			}
			
			
			mPMissile = new PMissile[20];
			for(int i =0;i<mPMissile.length;i++){
				mPMissile[i] = new PMissile();
			}
			
			mCarValue = new CarValue[15];
			for(int i =0;i<mCarValue.length;i++){
				mCarValue[i] = new CarValue(false);
			}
			mCarValue[0].Buy = true;
			mSmock = new Smock[60];
			for(int i =0;i<mSmock.length;i++){
				mSmock[i] = new Smock();
			}
			
			carDrive[0] = 0;
			carDrive[1] = -1;
			carDrive[2] = -1;
			
			mTop = new Background[2];
			mTop[0] = new Background();
			mTop[1] = new Background();
			
			mCenter = new Background[2];
			mCenter[0] = new Background();
			mCenter[1] = new Background();
			
			mDown = new Background[2];
			mDown[0] = new Background();
			mDown[1] = new Background();
			
			oppseq = new byte[8][9];
			for (int i = 0; i < mTop.length; i++) {
				mTop[i].set(i * mTex_Road[0].width(), -.31f);
				mCenter[i].set(i * mTex_Road[0].width(), -.13f);
				mDown[i].set(i * mTex_Road[0].width(), 0.32f);
			}
//			gameReset();
		}catch(Exception e){}
		
	}
	final byte opp[][]=
	{
		{ 50, 50,  0,  0,  0,  0,  0,  0,  50},
		{ 40, 40, 20,  0,  0,  0,  0,  0,  50},
		{ 30, 30, 30, 30,  0,  0,  0,  0,  0},
		{ 20, 20, 20, 20, 10,  0,  0,  0,  0},
		{ 20, 20, 20, 20, 20, 20,  0,  0,  0},
		{ 15, 15, 15, 15, 15, 15, 10,  0,  0},
		{ 15, 15, 15, 15, 15, 15, 15, 10,  0},
		{ 30, 30, 30, 30, 30, 30, 20, 20, 20},
	};
	
	void gameReset() {
		Bullate = (byte)M.mRand.nextInt(3);
		spd = -.01f;
		overCount =0;
		
		topCount = (byte) M.mRand.nextInt(8);
		for(int i=0;i<3;i++){
			mPlayCar[i].set(-.42f, -.28f,14,false,0,0);
		}
		for (int i = 0; i < 3; i++) {
			if (carDrive[i] >= 0)
				mPlayCar[i].set(-1.12f, -.28f, carDrive[i], true,
						(mCarValue[carDrive[i]].upgred[2] * .4f) * ((carDrive[i]*.4f) + 4),
						mCarValue[carDrive[i]].upgred[1]);
		}

		for (byte i = 0; i < mDusman.length; i++) {
//			mDusman[i].set(1.5f, -.28f, (byte)M.mRand.nextInt(9));
			mDusman[i].reset();
			
		}
		for(int i =0;i<mMissile.length;i++){
			mMissile[i].set(-10, -10, 0, 0,0);
		}
		for(int i =0;i<mBulltet.length;i++){
			mBulltet[i].set(-10, -10, 0);
		}
		for(int i =0;i<mBlast.length;i++){
			mBlast[i].set(-10, -10, 100);
		}
		for(int i =0;i<mPMissile.length;i++){
			mPMissile[i].set(-10, -10, 100,false);
		}
		for(int i =0;i<mSmock.length;i++){
			mSmock[i].set(-10,-10);
		}
		
		distance =0;
		Rewrd=tcalcu=Wall = killVal = kill =0;
		for(int i =0;i<opp.length;i++){
			for(int j =0;j<opp[i].length;j++){
				oppseq[i][j] = opp[i][j];
			}
		}
		mStart.load();
	}
//	mDusman 2 Car y = -.28f;
//	mDusman 4 Car y = -.20f;
//	mDusman 6 Car y = -.18f;
//	mDusman 7 Car y = -.29f;
	
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
		/*if(mStart.adView!=null)
		{
			resumeCounter++;
			if(M.GameScreen != M.GAMEMENU)
			{
				int inv=mStart.adView.getVisibility();
				if(inv==AdView.GONE){try{handler.sendEmptyMessage(AdView.VISIBLE);}catch(Exception e){}}
			}
			else
			{
				int inv=mStart.adView.getVisibility();
				if(inv==AdView.VISIBLE){try{handler.sendEmptyMessage(AdView.GONE);}catch(Exception e){}}
			}
		}*/
//		/*AdHouse*/
//		if (mStart.adHouse != null) {
//			if (M.GameScreen == M.GAMEADD) {
//				int inv = mStart.adHouse.getVisibility();
//				if (inv == AdView.GONE) {try {handler2.sendEmptyMessage(AdView.VISIBLE);} catch (Exception e) {}}
//			} else {
//				int inv = mStart.adHouse.getVisibility();
//				if (inv == AdView.VISIBLE) {try {handler2.sendEmptyMessage(AdView.GONE);} catch (Exception e) {}
//				}
//			}
//		}
//		/*AdHouse*/
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

	SimplePlane add(String ID) {
		SimplePlane SP = null;
		Bitmap b = LoadImgfromAsset(ID);
		try {
			SP = new SimplePlane((b.getWidth() / M.mMaxX),
					(b.getHeight() / M.mMaxY));
			SP.loadBitmap(b);// R.drawable.jay
		} catch (Exception e) {
		}
		return SP;
	}

	SimplePlane addRotate(String ID) {
		SimplePlane SP = null;
		Bitmap b = LoadImgfromAsset(ID);
		try {
			SP = new SimplePlane((b.getWidth() / M.mMaxX),(b.getHeight() / M.mMaxX));
			SP.loadBitmap(b);// R.drawable.jay
		} catch (Exception e) {
		}
		return SP;
	}

	SimplePlane addBitmap(Bitmap b) {
		SimplePlane SP = null;
		try {
			SP = new SimplePlane((b.getWidth() / M.mMaxX),
					(b.getHeight() / M.mMaxY));
			SP.loadBitmap(b);// R.drawable.jay
		} catch (Exception e) {
		}
		return SP;
	}

	Bitmap LoadImgfromAsset(String ID) {
		try {
			return BitmapFactory.decodeStream(mContext.getAssets().open(ID));
		} catch (Exception e) {
			// Log.d(""+ID,"!!!!!!!!!!!!!!!!!!!!!~~~~~~~~~~~~~!!!!!!!!!!!!!!!!!!!!!!!!!"+e.getMessage());
			return null;
		}
	}

	Bitmap resizeImg(Bitmap bitmapOrg, int newWidth, int newHeight) {
		int width = bitmapOrg.getWidth();
		int height = bitmapOrg.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		matrix.postRotate(0);
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, width,
				height, matrix, true);
		// Log.d("resizeImg========","newWidth ["+newWidth+"] newHeight ["+newHeight+"]");
		return resizedBitmap;
	}

	Bitmap FlipHorizontal(Bitmap bitmapOrg) {
		Matrix matrix = new Matrix();
		matrix.postScale(-1f, 1f);
		matrix.postRotate(0);
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0,
				bitmapOrg.getWidth(), bitmapOrg.getHeight(), matrix, true);
		return resizedBitmap;
	}
	void loadFont(){
		mTex_Font = new SimplePlane[12];
		Bitmap b = LoadImgfromAsset("font/0.png");
		for(int i=0;i<mTex_Font.length;i++){
			mTex_Font[i] = addBitmap(Bitmap.createBitmap(b, i* (b.getWidth() / 16), 0,
					b.getWidth() / 16, b.getHeight(),null, true));
		}
		
	}
	/*void loadFont(){
		mTex_Font = new SimplePlane[38];
		Bitmap b = LoadImgfromAsset("font/0.png");
		for(int i=0;i<32;i++){
			mTex_Font[i] = addBitmap(Bitmap.createBitmap(b,
					(i % 8) * (b.getWidth() / 8),(i / 8) * (b.getHeight() / 4), b.getWidth() / 8,
					b.getHeight() / 4, null, true));
		}
		b = LoadImgfromAsset("font/1.png");
		for(int i=0;i<6;i++){
			mTex_Font[i+32] = addBitmap(Bitmap.createBitmap(b, i * (b.getWidth() / 8), 0, b.getWidth() / 8, b.getHeight(),
					null, true));
		}
	}*/
}
