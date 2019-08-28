package com.hututu.games.fightersofocean;

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
	static Context mContext;
	public static Start mStart;
	long startTime = System.currentTimeMillis();
	int resumeCounter =0;
	int mSel = 0;
	
	SimplePlane[] mTex_GameBG,mTex_GameBGF,mTex_Boat,mTex_BoatX,mTex_Gun,mTex_Power,mTex_UBoat,mTex_BFire;
	SimplePlane[] mTex_Button0,mTex_Button1,mTex_Button2,mTex_Pattern,mTex_Option,mTex_Fire,mTex_BBlast;
	SimplePlane[] mTex_mPowrBoat,mTex_BWall,mTex_CBlast,mTex_Add;
	SimplePlane	  mTex_Target,mTex_mLife,mTex_Fill,mTex_Logo,mTex_Player,mTex_PIcon,mTex_About,mTex_mPFire;
	SimplePlane	  mTex_AIcon,mTex_HIcon,mTex_Objectbox,mTex_Scorebox,mTex_Black,mTex_Sound,mTex_Back,mTex_HelpScr;
	SimplePlane	  mTex_Continue,mTex_Exit,mTex_Help,mTex_Info_icon,mTex_Reload0,mTex_Reload1,mTex_RIcon,mTex_Splash;
	SimplePlane   mTex_Pointer, mTex_Hightbar; //AdHouse
	int mAdd 		= 0;
	int mLevel 		= 1;
	int mWait		= 0;
	int GunPoint 	= 0;
	int PowerPoint 	= 0;
	int BoatPoint 	= 0;
	int SelPoint 	= 0;
	int mMove 		= 0;
	int Bullet 		= 25;
	int lastLife	= 0;
	int bombBlast	= -1;
	
	
	float NetBlast	= -2;
	float mReload	= 0;
	float mBGMove1[];
	float mBGMove2[];
	float mBGMove3[];
	float mBGMove4[];
	float Load = -1f;
	
	Font 	mFont;
	Boat[]	mOpp;	
	Player	mPlayer;
	
	Gun[]	mGun;
	Power[]	mPower;
	UBoat[]	mUBoat;
	
	
	private Handler handler = new Handler() {public void handleMessage(Message msg) {mStart.adView.setVisibility(msg.what);}};
	private Handler handler1= new Handler() {public void handleMessage(Message msg) {mStart.adViewBig.setVisibility(msg.what);}};
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
			mTex_Pointer = add("pointer.png");//AdHouse
			mTex_Hightbar = add("hightbar0.png");//AdHouse
			mTex_Logo			= add("hututugames.png");
			mTex_GameBG			= new SimplePlane[5];
			mTex_GameBG[0]		= add("bg.png");
			mTex_GameBG[1]		= add("cloud.png");
			mTex_GameBG[2]		= add("water_xxx.png");
			mTex_GameBG[3]		= add("water_xx.png");
			mTex_GameBG[4]		= add("water_x.png");
			
			mTex_GameBGF		= new SimplePlane[5];
			mTex_GameBGF[0]		= addBitmap(FlipHorizontal(mTex_GameBG[2].getBitmap()));
			mTex_GameBGF[1]		= addBitmap(FlipHorizontal(mTex_GameBG[3].getBitmap()));
			mTex_GameBGF[2]		= addBitmap(FlipHorizontal(mTex_GameBG[4].getBitmap()));
			
			mTex_Black			= add("ui/black.png");
			mTex_AIcon			= add("ui/arrow_icon.png");
			mTex_HIcon			= add("ui/helth_icon.png");
			mTex_Objectbox		= add("ui/objectbox.png");
			mTex_Scorebox		= add("ui/scorebox.png");
			mTex_Target			= add("ui/target.png");
			mTex_Sound			= add("ui/soundicon.png");
			mTex_Back			= add("ui/back.png");
			mTex_HelpScr		= add("ui/help.jpg");
			
			mTex_Button0		= new SimplePlane[2];
			mTex_Button0[0]		= add("ui/button_0.png");
			mTex_Button0[1]		= add("ui/button_0_se.png");
			
			mTex_Button1		= new SimplePlane[2];
			mTex_Button1[0]		= add("ui/button_1.png");
			mTex_Button1[1]		= add("ui/button_1_se.png");
			
			mTex_Button2		= new SimplePlane[2];
			mTex_Button2[0]		= add("ui/button_2.png");
			mTex_Button2[1]		= add("ui/button_2_se.png");
			
			mTex_Pattern		= new SimplePlane[4];
			mTex_Pattern[0]		= add("ui/pattern_0.png");
			mTex_Pattern[1]		= add("ui/pattern_1.png");
			mTex_Pattern[2]		= addBitmap(FlipHorizontal(mTex_Pattern[0].getBitmap()));
			mTex_Pattern[3]		= addBitmap(FlipHorizontal(mTex_Pattern[1].getBitmap()));
			
			mTex_Option			= new SimplePlane[2];
			mTex_Option[0]		= add("ui/option.png");
			mTex_Option[1]		= add("ui/select_option.png");
			
			
			mTex_mLife			= add("life.png");
			mTex_Fill			= add("red_dot.png");

			mTex_Player			= add("player.png");
			mTex_PIcon			= add("fire/power_icon.png");
			mTex_About			= add("fire/aboutus.png");
			
			mTex_Continue		= add("fire/continue.png");
			mTex_Exit			= add("fire/exit.png");
			mTex_Help			= add("fire/help.png");
			mTex_Info_icon		= add("fire/info_icon.png");
			mTex_Reload0		= add("fire/reload0.png");
			mTex_Reload1		= add("fire/reload1.png");
			mTex_RIcon			= add("fire/reload_icon.png");
			mTex_Splash			= add("fire/Splash.jpg");
			
			mTex_BBlast			= new SimplePlane[7];
			for(int i =0;i<mTex_BBlast.length;i++)
				mTex_BBlast[i]	= add("fire/bomb_blast"+i+".png");
			
			mTex_CBlast			= new SimplePlane[12];
			Bitmap b = LoadImgfromAsset("fire/copter_blast.png");
			for(int i = 0;i<mTex_CBlast.length;i++)
				mTex_CBlast[i] = addBitmap(Bitmap.createBitmap(b, i*b.getWidth()/mTex_CBlast.length, 0,b.getWidth()/mTex_CBlast.length, b.getHeight(),null, true));
			
			mTex_Add			= new SimplePlane[3];
			for(int i =0;i<mTex_Add.length;i++)
				mTex_Add[i]	= add("ad/"+i+".png");
			
				
			mTex_BFire			= new SimplePlane[2];
			mTex_BFire[0]		= add("fire/bullet_fire.png");
			mTex_BFire[1]		= add("fire/copter_fire.png");

			mTex_BWall			= new SimplePlane[2];
			mTex_BWall[0]		= add("fire/boat_wall.png");
			mTex_BWall[1]		= addBitmap(FlipHorizontal(mTex_BWall[0].getBitmap()));
			
			
			mTex_Boat			= new SimplePlane[15];
			for(int i =0;i<mTex_Boat.length;i++)
				mTex_Boat[i]	= add("opp/"+i+".png");
			
			mTex_Fire			= new SimplePlane[6];
			for(int i =0;i<mTex_Fire.length;i++)
				mTex_Fire[i]	= add("fire/"+i+".png");
			
			mTex_BoatX			= new SimplePlane[4];
			mTex_BoatX[0]		= add("opp/2h.png");
			mTex_BoatX[1]		= add("opp/5h.png");
			mTex_BoatX[2]		= add("opp/11h.png");
			mTex_BoatX[3]		= add("opp/14h.png");
			
			mTex_Gun			= new SimplePlane[6];
			for(int i =0;i<mTex_Gun.length;i++)
			{
				mTex_Gun[i]		= add("gun/"+i+".png");
			}
			
			mTex_Power			= new SimplePlane[6];
			for(int i =0;i<mTex_Power.length;i++)
			{
				mTex_Power[i]	= add("Power/"+i+".png");
			}
			
			mTex_mPowrBoat		= new SimplePlane[6];
			for(int i =0;i<mTex_mPowrBoat.length;i++)
			{
				mTex_mPowrBoat[i]	= add("Power/g/"+i+".png");
			}
			mTex_mPFire		= add("Power/g/power_fire.png");
			
			mTex_UBoat		= new SimplePlane[6];
			for(int i =0;i<mTex_UBoat.length;i++)
			{
				mTex_UBoat[i]	= add("boat/"+i+".png");
			}
			mFont = new Font();
//			font();
			
			mBGMove1	= new float[2];
			mBGMove2	= new float[2];
			mBGMove3	= new float[2];
			mBGMove4	= new float[2];
			
			mOpp		= new Boat[20];
			for(int i = 0;i<mOpp.length;i++)
			{
				mOpp[i]	= new Boat();
			}
			mPlayer		= new Player();
			M.PlayerLife = mPlayer.MaxLife;
			mGun		= new Gun[6];
			mGun[0]		= new Gun("MAC-11"			,  2, 30, 1000);
			mGun[1]		= new Gun("HK-MP5"			,  2, 34, 5000);
			mGun[2]		= new Gun("Spring-G3"		,  3, 38,10000);
			mGun[3]		= new Gun("Colt-M16"		,  3, 42,25000);
			mGun[4]		= new Gun("AK-47"			,  4, 46,50000);
			mGun[5]		= new Gun("M-240 Machine Gun", 5, 50,75000);
			
			mPower		= new Power[6];
			mPower[0]	= new Power("AKS-74U"			,  1,  1200);
			mPower[1]	= new Power("Fire"				,  2,  5000);
			mPower[2]	= new Power("Laser Gun"			,  3, 10000);
			mPower[3]	= new Power("Cannon"			,  4, 20000);
			mPower[4]	= new Power("M41_Rocket Launcher", 5, 30000);
			mPower[5]	= new Power("Missile Launcher"	,  6, 50000);
			
			
			mUBoat		= new UBoat[6];
			mUBoat[0]	= new UBoat("Hand_Grenade"	,  4,  50);
			mUBoat[1]	= new UBoat("Net Through"	,  6,  50);
			mUBoat[2]	= new UBoat("Health"		, 40,  40);
			mUBoat[3]	= new UBoat("Tools"			,500,  100);
			mUBoat[4]	= new UBoat("Sharp Shooter"	, 14, 100);
			mUBoat[5]	= new UBoat("Ultra Shooter"	, 18, 500);
			
			gameReset();
			
		}catch(Exception e){}
		
	}
	
	
	void gameReset()
	{
		M.mOppSet =0;
		mBGMove1[0]	= 0;mBGMove1[1] = 2;
		mBGMove2[0]	= 0;mBGMove2[1] = 2;
		mBGMove3[0] = 0;mBGMove3[1] = 2;
		mBGMove4[0] = 0;mBGMove4[1] = 2;
		mPlayer.set(.6f  ,   -.2f  ,  0,  mPlayer.mDamageBy  ,  5+mLevel*3  ,  Bullet);
		lastLife = M.PlayerLife;
//		M.PlayerLife = 1000000;
//		Bullet = 500;
		for(int i = 0;i<mOpp.length;i++)
			setOpp(i,-1-(i*.16f));
		for(int i=mPlayer.mKillTarget;i<mOpp.length;i++)
			mOpp[i].mLife = -13;
		setShot();
		BoatSet();
		bombBlast = -1;
		NetBlast = -1.5f;
		mWait = 0;
		Load = -1f;
		mReload =50;
//		mPlayer.mCoin=1000000;
		M.stop();
	}
	void setOpp(int i,float x)
	{
		int no = (mLevel/5)+1;
		if(mLevel<=1)
			no =0;
		mOpp[i].mNo =0;
		if(no>1)
			mOpp[i].mNo = M.mRand.nextInt(no);
		if(mOpp[i].mNo == no-1){
			if(M.mRand.nextInt(5-(no%5))==0)
				mOpp[i].mNo = no;
			}
		
//		System.out.println("~~~~~~~~~~~~~~"+mOpp[i].mNo);
//		mOpp[i].mNo =14;
		mOpp[i].mNo%=mTex_Boat.length;
//		mOpp[i].mNo =3;
		
		mOpp[i].set(x, (M.mRand.nextInt(800)-800)/1000f,mOpp[i].mNo);
		if(mOpp[i].mNo%3==2)
		{
			mOpp[i].y = (M.mRand.nextInt(500)+300)/1000f;
		}
//		mOpp[i].mLife = 1;
//		mOpp[i].mNo =2;
//		mOpp[i].mNo%=mTex_Boat.length;
	}
	void setShot()
	{
//		SelPoint = 0;
//		GunPoint = PowerPoint = BoatPoint = 0;
		int i=GunPoint+2;
		if(i>=mGun.length)
			i=0;
		for(int j=0;j<mGun.length;j++)
		{
			if(i<0)
				i=mGun.length-1;
			if(j<3)
				mGun[i].set(1.2f-(j*.6f));
			else
			{
				mGun[i].set(1.2f-(j*.6f));
			}
			i--;
		}
		i=PowerPoint+2;
		if(i>=mPower.length)
			i=0;
		for(int j=0;j<mPower.length;j++)
		{
			if(i<0)
				i=mPower.length-1;
			if(j<3)
				mPower[i].set(1.2f-(j*.6f));
			else
			{
				mPower[i].set(1.2f-(j*.6f));
			}
			i--;
		}
		i=BoatPoint+2;
		if(i>=mUBoat.length)
			i=0;
		for(int j=0;j<mUBoat.length;j++)
		{
			if(i<0)
				i=mUBoat.length-1;
			if(j<3)
				mUBoat[i].set(1.2f-(j*.6f));
			else
			{
				mUBoat[i].set(1.2f-(j*.6f));
			}
			i--;
		}
	}
	void BoatSet()
	{
		Boat	temp = new Boat();
		for(int i=0;i<mOpp.length;i++)
		{
			for(int j=i+1;j<mOpp.length;j++)
			{
				if(mOpp[i].y<mOpp[j].y)
				{
					temp.replace(mOpp[i]);
					mOpp[i].replace(mOpp[j]);
					mOpp[j].replace(temp);
				}
			}
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
		if(mStart.adView!=null)
		{
			resumeCounter++;
			if(resumeCounter > 60 && M.GameScreen != M.GAMEPLAY && M.GameScreen != M.GAMELOAD &&  M.GameScreen != M.GAMEADD)
			{
				int inv=mStart.adView.getVisibility();
				if(inv==AdView.GONE){try{handler.sendEmptyMessage(AdView.VISIBLE);}catch(Exception e){}}
			}
			else
			{
				int inv=mStart.adView.getVisibility();
				if(inv==AdView.VISIBLE){try{handler.sendEmptyMessage(AdView.GONE);}catch(Exception e){}}
			}
			
			
			
			if(M.GameScreen == M.GAMELOAD)
			{
				int inv=mStart.adViewBig.getVisibility();
				if(inv==AdView.GONE){try{handler1.sendEmptyMessage(AdView.VISIBLE);}catch(Exception e){}}
			}
			else
			{
				int inv=mStart.adViewBig .getVisibility();
				if(inv==AdView.VISIBLE){try{handler1.sendEmptyMessage(AdView.GONE);}catch(Exception e){}}
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
			
		}
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
//	void font()
//	{
//		mTex_Font	= new SimplePlane[12];
//		Bitmap b = LoadImgfromAsset("fontstrip.png");
//		for(int i = 0;i<mTex_Font.length;i++)
//			mTex_Font[i] = addBitmap(Bitmap.createBitmap(b, i*b.getWidth()/mTex_Font.length, 0,b.getWidth()/mTex_Font.length, b.getHeight(),null, true));
//		mFont = new Font();
//	}
}
