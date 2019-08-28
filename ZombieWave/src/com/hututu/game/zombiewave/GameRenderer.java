package com.hututu.game.zombiewave;

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
	
	
	SimplePlane[] mTex_gGunfire,mTex_Car,mTex_WBox,mTex_SCar,mTex_Engine,mTex_Gear,mTex_SDGun,mTex_SDBoost,mTex_gBoost;
	SimplePlane[] mTex_MapIcn,mTex_Sound,mTex_TZombie;
	SimplePlane[][] mTex_BG,mTex_Blood,mTex_CarAcc,mTex_pWheel,mTex_SKit,mTex_SDKit,mTex_Zombie,mTex_ZTukde,mTex_STyre;
	SimplePlane[][] mTex_Joint,mTex_SWeapon,mTex_Story;
	SimplePlane mTex_Bamboo,mTex_Arrow,mTex_Buy,mTex_CoinBar,mTex_Light,mTex_MapBtn,mTex_Play,mTex_Lock,mTex_MCross;
	SimplePlane mTex_Shopitem,mTex_Logo,mTex_ShopBG,mTex_Popup,mTex_Common,mTex_Fual,mTex_Bar,mTex_Fill,mTex_Map;
	SimplePlane mTex_Ok,mTex_Cross,mTex_Garege,mTex_BoostBtn,mTex_AcceBtn,mTex_DisBar,mTex_Pointer,mTex_OverPOp;
	SimplePlane mTex_FRect,mTex_FBox,mTex_Reset,mTex_Point,mTex_Cutter,mTex_Arra,mTex_Bullate,mTex_Meter,mTex_MKata;
	SimplePlane mTex_item,mTex_FB,mTex_More,mTex_PlayBtn,mTex_AbBut,mTex_Splash,mTex_SplashT,mTex_MapDot,mTex_MLock;
	SimplePlane mTex_About,mTex_indicator,mTex_Banner,mTex_CongBG,mTex_congTex,mTex_PlayAgn,mTex_Ratus,mTex_Skip;
	
	Background[] mBGOne,mBGTwo,mBGThree,mBGFour;
	
	Background mBGBambo;
	Player	mPlayer;
	
	Zombie mZombie[];
	Tukde mTukde[];
	Box mBox[];
	Box mSBox[];
	Font mFont;
	Car mCar[];
	Blood mBlood[];
	
	boolean unclockCar[];
	boolean addFree;
	
	
	int bamboCont = 2;
	int Attack = 0;
	int carSelect = 0;
	int Popup = 0;
	int Zombie = 0;
	int setJoin = 0;
	int Dollor = 1;
	
	float newSbox = 0;
	float newZombie = 0;
	float newZombieV = 0;
	
	float mLast =-.50f;
	
	int story = 0;
//	float leftx = 1.5f;
//	float midy = 2f;

	float One,two,three;
	
	_InApp mInApp;
	
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
		try
		{
			mInApp = new _InApp();
			mInApp.onCreate();
			
			mTex_Skip =  add("Story/escape_btn.png");
			mTex_Story	= new SimplePlane[3][];
			
			mTex_Story[0]	= new SimplePlane[6];
			mTex_Story[0][0] = add("Story/0/0.png");
			mTex_Story[0][1] = add("Story/0/1.png");
			mTex_Story[0][2] = add("Story/0/2.png");
			mTex_Story[0][3] = add("Story/0/0t.png");
			mTex_Story[0][4] = add("Story/0/1t.png");
			mTex_Story[0][5] = add("Story/0/2t.png");
			
			mTex_Story[1]	= new SimplePlane[3];
			mTex_Story[1][0] = add("Story/1/0.png");
			mTex_Story[1][1] = add("Story/1/1.png");
			mTex_Story[1][2] = add("Story/1/0t.png");
			
			mTex_Story[2]	= new SimplePlane[4];
			mTex_Story[2][0] = add("Story/2/0.png");
			mTex_Story[2][1] = add("Story/2/1.png");
			mTex_Story[2][2] = add("Story/2/0t.png");
			mTex_Story[2][3] = add("Story/2/1t.png");
			
			mTex_Banner = add("Congrat/banner.png");
			mTex_CongBG = add("Congrat/Congratulation_screenBG.jpg");
			mTex_congTex = add("Congrat/congratulation_text.png");
			mTex_PlayAgn = add("Congrat/play_again.png");
			mTex_Ratus = add("Congrat/ratus_btn.png");
			
			
			mTex_indicator = add("pointer.png");
			mTex_About = add("about_text.png");
			mTex_Sound = new SimplePlane[2];
			mTex_Sound[0] = add("Splash/sound_off.png"); 
			mTex_Sound[1] = add("Splash/sound_on.png"); 
			mTex_MCross = add("Map/map_cross.png");
			mTex_Map = add("Map/map.png");
			mTex_MapIcn = new SimplePlane[3];
			for(int i=0;i<mTex_MapIcn.length;i++)
				mTex_MapIcn[i] = add("Map/"+i+".png");
			mTex_MapDot = add("Map/map_dot.png");
			mTex_MLock = add("Map/map_lock.png");
			mTex_AbBut = add("Splash/about_btn.png");
			mTex_FB = add("Splash/fb_btn.png");
			mTex_More = add("Splash/more_btn.png");
			mTex_PlayBtn = add("Splash/play_btn.png");
			
			mTex_Splash = add("Splash/splash4.png");
			mTex_SplashT = add("Splash/splash_text.png");
			
			
			mTex_item = add("item_select2.png");
			mTex_MKata = addRotate("kata1.png");
			mTex_Meter = add("meter.png");
			mTex_Bullate = add("bullet.png");
			mTex_Arra = addRotate("Car/7/kit.png");
			mTex_Cutter = addRotate("Car/common/cutter.png");
			mTex_gGunfire	= new SimplePlane[2];
			mTex_gGunfire[0] = add("Car/common/gun_fire0.png");
			mTex_gGunfire[1] = add("Car/common/gun_fire1.png");
			mTex_gBoost		= new SimplePlane[2];
			mTex_gBoost[0] = add("Car/common/boost_fire0.png");
			mTex_gBoost[1] = add("Car/common/boost_fire1.png");
			mTex_Point = add("pointer2.png");
			mTex_Reset = add("retry_btn.png");
			mTex_FRect = add("day_comptete_bar_fill.png");
			mTex_FBox = add("day_comptete_bar.png");
			mTex_OverPOp = add("attack_comptete_bg.png");
			mTex_Pointer = add("mark.png");
			mTex_DisBar = add("bar.png");
			mTex_AcceBtn = add("accelerate0.png");
			mTex_BoostBtn = add("boost_btn.png");
			mTex_Garege = add("garage_btn.png");
			
			mTex_Lock = add("shop/lock.png");
			mTex_Cross = add("shop/cross.png");
			mTex_Ok = add("shop/ok_btn.png");
			mTex_Bar = add("shop/upgrate_bar.png");
			mTex_Fill = add("shop/upgrate_ba_fillr.png");
			mTex_Fual = add("shop/petrole-tank.png");
			mTex_Popup = add("shop/gearbox_bg.png");
			mTex_Common = add("shop/common_box.png");
			mTex_Engine		= new SimplePlane[3];
			for(int i=0;i<mTex_Engine.length;i++)
				mTex_Engine[i]	= add("shop/engine"+i+".png");
			
			mTex_Gear		= new SimplePlane[3];
			for(int i=0;i<mTex_Gear.length;i++)
				mTex_Gear[i]	= add("shop/gear"+i+".png");
			
			
			mTex_TZombie		= new SimplePlane[5];
			for(int i=0;i<mTex_TZombie.length;i++)
				mTex_TZombie[i]		= addRotate("Zombi/"+i+"/0.png");
			
			mTex_Logo		= add("hututugames.png");
			mTex_Zombie		= new SimplePlane[4][];
			for(int i=0;i<mTex_Zombie.length;i++){
				if(i==0)
					mTex_Zombie[i]	= new SimplePlane[8];
				else if(i==1)
					mTex_Zombie[i]	= new SimplePlane[7];
				else
					mTex_Zombie[i]	= new SimplePlane[9];
				for(int j=0;j<mTex_Zombie[i].length;j++){
					mTex_Zombie[i][j]	= add("Zombi/"+i+"/"+j+".png");
				}
				
			}
			mTex_ZTukde		= new SimplePlane[5][];
			for(int i=0;i<mTex_ZTukde.length;i++){
				mTex_ZTukde[i]	= new SimplePlane[5];
				for(int j=0;j<mTex_ZTukde[i].length;j++){
					mTex_ZTukde[i][j]	= addRotate("Zombi/"+i+"/tukde/"+j+".png");
				}
			}
			
			mTex_BG = new SimplePlane[3][4];
			for (int j = 0; j < mTex_BG.length; j++) {
				mTex_BG[j] = new SimplePlane[4];
				for (int i = 0; i < mTex_BG[j].length; i++){
					if (!((i == 0 || i == 1) && j == 2))
					{
						mTex_BG[j][i] = add("BG/"+j+"/" + i + ".png");
					}
				}
			}
			mTex_STyre = new SimplePlane[8][];
			for (int i = 0; i < mTex_STyre.length; i++) {
				mTex_STyre[i] = new SimplePlane[3];
				for (int j = 0; j < mTex_STyre[i].length; j++)
					mTex_STyre[i][j] = add("shop/gcar/" + i +"/"+j+ ".png");
			}
			
			
			mTex_SWeapon = new SimplePlane[8][];
			for (int i = 0; i < mTex_SWeapon.length; i++) {
				mTex_SWeapon[i] = new SimplePlane[5];
//				System.out.println(("[shop/gcar/" + i +"/gun1.png")+"]  ~~~~~~~"+i);
				mTex_SWeapon[i][0] = add("shop/gcar/" + i +"/boost.png");
				mTex_SWeapon[i][1] = add("shop/gcar/" + i +"/gun1.png");
				mTex_SWeapon[i][2] = add("shop/gcar/" + i +"/kit1.png");
				mTex_SWeapon[i][3] = add("shop/gcar/" + i +"/kit2.png");
				mTex_SWeapon[i][4] = add("shop/gcar/" + i +"/shadow.png");
			}
			
			
			mTex_SCar			= new SimplePlane[8];
			for(int i=0;i<mTex_SCar.length;i++)
				mTex_SCar[i]	= add("shop/gcar/"+i+".png");
			
			mTex_SDKit = new SimplePlane[8][];
			for (int i = 0; i < mTex_SDKit.length; i++) {
				mTex_SDKit[i] = new SimplePlane[2];
				for (int j = 0; j < mTex_SDKit[i].length; j++)
					mTex_SDKit[i][j] = add("shop/popwheel/"+i+"/kit"+(j+1)+".png");
			}
			
			mTex_SKit = new SimplePlane[8][];
			for (int i = 0; i < mTex_SKit.length; i++) {
				mTex_SKit[i] = new SimplePlane[2];
				for (int j = 0; j < mTex_SKit[i].length; j++)
					mTex_SKit[i][j] = add("shop/gcar/"+i+"/kit"+(j+1)+".png");
			}
			
			mTex_Blood = new SimplePlane[3][];
			for (int i = 0; i < mTex_Blood.length; i++) {
				mTex_Blood[i] = new SimplePlane[6];
				for (int j = 0; j < mTex_Blood[i].length; j++)
					mTex_Blood[i][j] = add("Blood/"+i+"/"+j+".png");
			}
			
			mTex_pWheel = new SimplePlane[8][];
			for (int i = 0; i < mTex_pWheel.length; i++) {
				mTex_pWheel[i] = new SimplePlane[3];
				for (int j = 0; j < mTex_pWheel[i].length; j++)
					mTex_pWheel[i][j] = add("shop/popwheel/"+i+"/"+j+".png");
			}
			
			mTex_CarAcc = new SimplePlane[8][];
			for (int i = 0; i < mTex_CarAcc.length; i++) {
				mTex_CarAcc[i] = new SimplePlane[7];
				mTex_CarAcc[i][0] = addRotate("Car/"+i+"/0.png");
				mTex_CarAcc[i][1] = addRotate("Car/"+i+"/1.png");
				mTex_CarAcc[i][2] = addRotate("Car/"+i+"/2.png");
				mTex_CarAcc[i][3] = add("Car/"+i+"/boost.png");
				mTex_CarAcc[i][4] = add("Car/"+i+"/gun.png");
				mTex_CarAcc[i][5] = add("Car/"+i+"/kit1.png");
				mTex_CarAcc[i][6] = add("Car/"+i+"/kit2.png");
				
			}
			
			mTex_SDGun		= new SimplePlane[8];
			for(int i=0;i<mTex_SDGun.length;i++)
				mTex_SDGun[i]	= add("shop/popwheel/"+i+"/gun.png");
			
			mTex_SDBoost		= new SimplePlane[8];
			for(int i=0;i<mTex_SDBoost.length;i++)
				mTex_SDBoost[i]	= add("shop/popwheel/"+i+"/boost.png");
			
			
			mTex_Car		= new SimplePlane[8];
			for(int i=0;i<mTex_Car.length;i++)
				mTex_Car[i]	= add("Car/"+i+"/car.png");
			
			
			mTex_WBox		= new SimplePlane[2];
			mTex_WBox[0]	= add("Wood_box/box1.png");
			mTex_WBox[1]	= add("Wood_box/box2.png");
			
			
			mTex_Joint = new SimplePlane[3][];
			for (int j = 0; j < mTex_Joint.length; j++) {
				mTex_Joint[j] = new SimplePlane[2];
				for (int i = 0; i < mTex_Joint[j].length; i++){
					mTex_Joint[j][i] = add("BG/"+j+"/joint" + i + ".png");
				}
			}
			
			
			mTex_Bamboo		= add("Wood_box/wood_block.png");
			
			mTex_Arrow		= add("shop/arrow.png");
			mTex_Buy		= add("shop/buy_coin.png");
			mTex_CoinBar	= add("shop/coin_bar.png");
			mTex_Light		= add("shop/light.png");
			mTex_MapBtn		= add("shop/map_btn.png");
			mTex_Play		= add("shop/play_btn.png");
			mTex_Shopitem	= add("shop/shopitem_bg.png");
			mTex_ShopBG		= add("shop/shop.jpg");
			mFont			= new Font(this);
			
			mBGOne		= new Background[3];
			mBGOne[0]	= new Background();
			mBGOne[1]	= new Background();
			mBGOne[2]	= new Background();
			mBGTwo		= new Background[2];
			mBGTwo[0]	= new Background();
			mBGTwo[1]	= new Background();
			mBGThree	= new Background[2];
			mBGThree[0]	= new Background();
			mBGThree[1]	= new Background();
			mBGFour		= new Background[3];
			mBGFour[0]	= new Background();
			mBGFour[1]	= new Background();
			mBGFour[2]	= new Background();
			mBGBambo	= new Background();
			mPlayer		= new Player();
			
			mSBox		= new Box[10];
			for(int i =0;i<mSBox.length;i++)
				mSBox[i]	= new Box();
			
			mBlood		= new Blood[10];
			for(int i =0;i<mBlood.length;i++)
				mBlood[i]	= new Blood();
			
			
			mBox		= new Box[9];
			for(int i =0;i<mBox.length;i++)
				mBox[i]	= new Box();
			
			mZombie		= new Zombie[10];
			for(int i =0;i<mZombie.length;i++)
				mZombie[i]	= new Zombie();
			
			
			mTukde		= new Tukde[100];
			for(int i =0;i<mTukde.length;i++)
				mTukde[i]	= new Tukde();
			
			mCar	= new Car[8];
			for(int i=0;i<mCar.length;i++){
				mCar[i] = new Car();
			}
			unclockCar = new boolean[8];
			for(int i=0;i<unclockCar.length;i++)
				unclockCar[i] = false;
			unclockCar[0] = true;
//			gameReset();
			
		}catch(Exception e){}
		
	}
	
	
	void gameReset() {
//		mPlayer.citi = 10;
		if(!unclockCar[carSelect]){
			for(int i=unclockCar.length-1;i>=0;i--){
				if(unclockCar[i]){
					carSelect = i;
					break;
				}
			}
		}
		mBGOne[0].set(-mTex_BG[1][0].width(), .19f);
		mBGOne[1].set(0, .19f);
		mBGOne[2].set(mTex_BG[1][0].width(), .19f);

		mBGTwo[0].set(0, -.24f);
		mBGTwo[1].set(2, -.24f);

		mBGThree[0].set(0, -.34f);
		mBGThree[1].set(2, -.34f);

		mBGFour[0].set(0, -.82f);
		mBGFour[1].set(2, -.82f);
		mBGFour[2].set(4, -.82f);
		
		mBGBambo.set(-2,-.82f);//-.65f
		for(int i =0;i<mBox.length;i++)
			mBox[i].set(mBGBambo.x, mBGBambo.y);
		
		for(int i =0;i<mSBox.length;i++)
			mSBox[i].set(-2,0);
		mPlayer.set( -1.46f, root.GamecarPos[carSelect][0],-.02f,carSelect);
		mPlayer.Engine = mCar[carSelect].sEngine;
		mPlayer.Gear = mCar[carSelect].sGear;
		mPlayer.pahiye = mCar[carSelect].sWheel;
		mPlayer.Gunfill = mCar[carSelect].sGun < 1 ?0:(5 + mCar[carSelect].sGun + (int) ((mPlayer.img+1) * 4 * mCar[carSelect].sGun / 10f));
		mPlayer.Boostfill = mCar[carSelect].Boost * 10;
		mPlayer.petrol = 500 + (mCar[carSelect].Petrol*10) + (mPlayer.img+1) * 100;
		mPlayer.kitNo = 0;
		if(mCar[carSelect].Kit[0] && mCar[carSelect].sKit == 0)
			mPlayer.kitNo = 1;
		if(mCar[carSelect].Kit[1] && mCar[carSelect].sKit == 1)
			mPlayer.kitNo = 2;
		
//		mPlayer.Print();
		for(int i =0;i<mZombie.length;i++)
			mZombie[i].set(-3, -.52f, 0);
		
		for(int i =0;i<mTukde.length;i++)
			mTukde[i].set(-2, 0,0,0,mPlayer.Speed);
		for(int i =0;i<mBlood.length;i++)
			mBlood[i].set(-10, 0, 0);
		bamboCont = 1;
		newZombie = 0;
		newZombieV = 1;
		newSbox = 0;
		Attack++;
		Zombie = 0;
		mStart.load();
	}
	
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
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
			if(M.GameScreen == M.GAMEADD && !addFree)
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
	public SimplePlane addRotate(String ID)
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
			System.out.println(ID+"   !!!!!!!!!!!    "+e.getMessage());
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
}
