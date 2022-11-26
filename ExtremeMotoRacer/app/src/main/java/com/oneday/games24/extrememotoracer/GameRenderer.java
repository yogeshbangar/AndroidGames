package com.oneday.games24.extrememotoracer;

import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Handler;
import android.os.Message;
public class GameRenderer implements Renderer , AccelerometerListener
{
	final Group root;
	static Context mContext;
	int resumeCounter =0;
	static Start mStart;
	static GameRenderer mGR;
	String packages = new String();
	SimplePlane[][] mTex_BG,mTex_Font,mTex_Obj;
	SimplePlane[] mTex_Car,mTex_Traffic,mTex_Coin,mTex_Menu,mTex_sound,mTex_Music,mTex_Selection;
	SimplePlane[] mTex_Play,mTex_Back,mTex_Arrow,mTex_Grear,mTex_Free,mTex_SelCar,mTex_Buy,mTex_Features,mTex_Blast;
	SimplePlane mTex_AllBG,mTex_BackScr,mTex_HelpScr,mTex_CoinBack,mTex_CoinTex,mTex_CarSelBack,mTex_ShopBack;
	SimplePlane mTex_PBack,mTex_GameBar,mTex_Boost,mTex_Boostindi,mTex_Life,mTex_Nip[],mTex_gLeft,mTex_gRight;
	SimplePlane mTex_TimeBar,mTex_Pause,mTex_pauseBack,mTex_Continue,mTex_Light,mTex_Replay,mTex_MenuIcn,mTex_About;
	SimplePlane mTex_pausetex,mTex_GameOverTex,mTex_Tag,/*mTex_Rate,*/mTex_Burst,mTex_TopGame,mTex_Logo,mTex_CoinBuy;
	SimplePlane mTex_crash[],mTex_facebook,mTex_Submit,mTex_leaderboard;
	boolean isReword;
	RoadBlock[][]	mRoadBlock;
	Row mRow[];
	Player mPlayer;
	Vehicle mOpponent[];
	Coin mCoins[];
	Vehicle overPlayer;
	
	Animation setVeh[] = new Animation[5];
	Random mRand; 
	int mVCount = 0,mPath;
	int brigeCount;
	int mSel =0;
	int mCarSel =0;
	int StartConter=0;
	int Challenge = 0;
	float pause = -.5f;
	float settingPlay = -1.25f;
	long GameTime = System.currentTimeMillis();
	long pauseTime = System.currentTimeMillis();
	long startTime = System.currentTimeMillis();
	public GameRenderer(Context context)
	{
		if(AccelerometerManager.isSupported())
			AccelerometerManager.startListening(this);
		mContext = context;
		mStart	= (Start)mContext;
		mGR = this;
		root = new Group(this);
		
		init();
		mRand = new Random(); 
		
	}
	void init()
	{
		resumeCounter =0;
		try
		{
			overPlayer	= new Vehicle();
			
			
			mTex_leaderboard = add("leaderboard.png");
			mTex_Submit	= add("submitde.png");
			mTex_About	= add("aboutus.png");
			mTex_facebook	= add("facebook.png");
			mRow	= new Row[27];
			for(int i=0;i<mRow.length;i++)
				mRow[i] = new Row();
			
			mPlayer = new Player();
			
			mOpponent = new Vehicle[10];
			for(int i=0;i<mOpponent.length;i++)
				mOpponent[i] = new Vehicle(); 
			
			mCoins = new Coin[30];
			for(int i=0;i<mCoins.length;i++)
				mCoins[i] = new Coin();
			
			
			mTex_crash		= new SimplePlane[4];
			mTex_crash[0]	= addRotate("bike_fall.png");
			mTex_crash[1]	= addRotate("cha_fall0.png");
			mTex_crash[2]	= addRotate("cha_fall1..png");
			mTex_crash[3]	= addRotate("cha_fall2..png");
			
			
			setVeh[0] = new Animation(-1.6f + 2*.8f ,.6f,0,false,     0,3, 80,1,0,"Haya fusa");
			setVeh[1] = new Animation(-1.6f + 3*.8f ,.4f,1, true, 10000,2,100,1,1,"Berrari ZX150");
			setVeh[2] = new Animation(-1.6f + 4*.8f ,.2f,2, true,100000,2,120,3,2,"Bolls Boyce A1");
			setVeh[3] = new Animation(-1.6f + 0*.8f ,.2f,3, true,250000,5,140,2,3,"Banguar FCX 2000");
			setVeh[4] = new Animation(-1.6f + 1*.8f ,.4f,4, true,500000,4,180,4,5,"Bummer BX2");
			mTex_Car	= new SimplePlane[5];
			for(int i=0;i<mTex_Car.length;i++)
				mTex_Car[i]	= add("car/"+i+".png");
			
			mTex_Traffic	= new SimplePlane[7];
			for(int i=0;i<mTex_Traffic.length;i++)
				mTex_Traffic[i]	= add("traffic/"+i+".png");
			
			mTex_Obj	= new SimplePlane[4][];
			
			mTex_Obj[0]	= new SimplePlane[2];
			for(int i=0;i<mTex_Obj[0].length;i++)
				mTex_Obj[0][i]	= add("obj/0"+i+".png");
			
			mTex_Obj[1]	= new SimplePlane[1];
			mTex_Obj[1][0]	= add("obj/10.png");
			
			mTex_Obj[2]	= new SimplePlane[2];
			mTex_Obj[2][0]	= add("obj/20.png");
			mTex_Obj[2][1]	= add("obj/21.png");
			
			mTex_Obj[3]	= new SimplePlane[3];
			mTex_Obj[3][0]	= add("obj/30.png");
			mTex_Obj[3][1]	= add("obj/31.png");
			mTex_Obj[3][2]	= add("obj/32.png");
			
			
			
			
			mTex_Coin	= new SimplePlane[10];
			Bitmap b = LoadImgfromAsset("coin.png");
			for(int i=0;i<mTex_Coin.length;i++)
			{
				mTex_Coin[i] = addBitmap(Bitmap.createBitmap(b, i*b.getWidth()/mTex_Coin.length, 0,b.getWidth()/mTex_Coin.length, b.getHeight(),null, true));
			}
			
			mTex_Menu	= new SimplePlane[7];
			for(int i=0;i<mTex_Menu.length;i++)
				mTex_Menu[i]	= add("menu/"+i+".png");
			
			mTex_CoinBuy			= add("buy_ad_free.png");
			mTex_Logo				= add("hututugames.png");
			mTex_sound				= new SimplePlane[2];
			mTex_sound[0]			= add("pause/sound0.png");
			mTex_sound[1]			= add("pause/sound1.png");
			
			mTex_Music				= new SimplePlane[2];
			mTex_Music[0]			= add("pause/music0.png");
			mTex_Music[1]			= add("pause/music1.png");
			
			mTex_Continue			= add("pause/play0.png");
			mTex_Light				= add1("pause/light.png");
			mTex_Replay				= add("pause/replay.png");
			mTex_MenuIcn			= add("pause/menu.png");
			
			mTex_pausetex			= add("pause/paused.png");
			mTex_GameOverTex		= add("pause/gameover.png");
			mTex_Tag				= add("pause/tag.png");
//			mTex_Rate				= add("pause/rate.png");
			b.recycle();
			mTex_Selection			= new SimplePlane[5];
			b = LoadImgfromAsset("pause/selection.png");
			for(int i=0;i<mTex_Selection.length;i++)
			{
				mTex_Selection[i] = addBitmap(Bitmap.createBitmap(b, i*b.getWidth()/mTex_Selection.length, 0,b.getWidth()/mTex_Selection.length, b.getHeight(),null, true));
			}
			
			mTex_GameBar			= add("gamebaar/baar.png");
			mTex_Boost				= add("gamebaar/boost.png");
			mTex_Boostindi			= add("gamebaar/boostindi.png");
			mTex_Life				= add("gamebaar/life.png");
			
			b.recycle();
			mTex_Blast				= new SimplePlane[6];
			b = LoadImgfromAsset("gamebaar/blast.png");
			for(int i=0;i<mTex_Blast.length;i++)
			{
				mTex_Blast[i] = addBitmap(Bitmap.createBitmap(b, i*b.getWidth()/mTex_Blast.length, 0,b.getWidth()/mTex_Blast.length, b.getHeight(),null, true));
			}
			mTex_Burst				= add("gamebaar/burst.png");
			mTex_TopGame			= add("TopGamesscreen.jpg");
			mTex_Nip				= new SimplePlane[2];
			mTex_Nip[0]				= add("gamebaar/nip.png");
			mTex_Nip[1]				= add("gamebaar/niptip.png");
			mTex_gLeft				= add("gamebaar/left0.png");
			mTex_gRight				= add("gamebaar/right0.png");
			
			mTex_Play				= new SimplePlane[2];
			mTex_Play[0]			= add("shop/playback/play0.png");
			mTex_Play[1]			= add("shop/playback/play1.png");
			
			mTex_Back				= new SimplePlane[2];
			mTex_Back[0]			= add("shop/playback/back0.png");
			mTex_Back[1]			= add("shop/playback/back1.png");
			mTex_CoinBack			= add("shop/back.png");
			mTex_CoinTex			= add("font/3.png");
			mTex_CarSelBack			= add("shop/carselection/back.png");
			mTex_Arrow				= new SimplePlane[3];
			mTex_Arrow[0]			= add("shop/carselection/left.png");
			mTex_Arrow[1]			= add("shop/carselection/right.png");
			mTex_Arrow[2]			= add("shop/carselection/lock.png");
			mTex_Grear				= new SimplePlane[3];
			mTex_Grear[0]			= add("shop/gear/0.png");
			mTex_Grear[1]			= add("shop/gear/1.png");
			mTex_Grear[2]			= add("shop/gear/2.png");
			mTex_Free				= new SimplePlane[3];
			mTex_Free[0]			= add("shop/free/back.png");
			mTex_Free[1]			= add("shop/free/bulk.png");
			mTex_Free[2]			= add("shop/free/regular.png");
			mTex_ShopBack			= add("shop/plateform.jpg");
			
			mTex_Buy				= new SimplePlane[3];
			mTex_Buy[0]				= add("shop/buy/back.png");
			mTex_Buy[1]				= add("shop/buy/buy.png");
			mTex_PBack				= add("shop/purchase/back0.png");
			mTex_TimeBar			= add("pause/timebar.png");
			mTex_Pause				= add("pause/pause0.png");
			mTex_pauseBack			= add("pause/back.png");
			mTex_Features			= new SimplePlane[5];
			mTex_Features[0]		= add("shop/features/back.png");
			mTex_Features[1]		= add("shop/features/box.png");
			mTex_Features[2]		= add("shop/features/feature.png");
			mTex_Features[3]		= add("shop/features/grid.png");
			mTex_Features[4]		= add("shop/features/line.png");
			
			
			mTex_SelCar	= new SimplePlane[5];
			for(int i=0;i<mTex_SelCar.length;i++)
				mTex_SelCar[i]	= add("shop/cars/"+i+".png");
			
			
			mTex_HelpScr		= add("help.jpg");
			mTex_BackScr		= add("back.jpg");
			BG();
			font();
			
			
			
			
			mCarSel = 0;
			setRoadBlock();
//			gameReset();
			M.GameScreen = M.GameLogo;
		}catch(Exception e){}
		
	}
	void gameReset()
	{
		gameStart();
		mPath =0;
		RoadBlock.reset();
		for(int i=0; i<mRoadBlock.length;i++)
		{
			for(int j=0; j<mRoadBlock[i].length;j++)
			{
				mRoadBlock[i][j].counter = 0;
			}
		}
		for(int i=0;i<mRow.length;i++)
		{
			int no = mRoadBlock[mPath][RoadBlock.sNext].mTrack[mRoadBlock[mPath][RoadBlock.sNext].counter%mRoadBlock[mPath][RoadBlock.sNext].mTrack.length];
			mRow[i].set(-1+i*mTex_BG[0][0].Height(), no, 0, RoadBlock.BG);
			mRoadBlock[mPath][RoadBlock.sNext].update();
		}
		mPlayer.set(0,-.6f, 0,0,.1f,setVeh[mCarSel].strenth);
		for(int i=0;i<mOpponent.length;i++)
			mOpponent[i].set(M.mRand.nextBoolean()?M.mRand.nextFloat():-M.mRand.nextFloat(), -1-i*.7f,0,-.01f,M.mRand.nextInt(mTex_Traffic.length));
		int n = mRand.nextInt(4);
		for(int i=0;i<mCoins.length;i++)
		{
			switch (n) {
			case 0:
				mCoins[i].setCoin(-.6f+i*.04f, -.5f+i*.05f);
				break;
			case 1:
				mCoins[i].setCoin(0.6f-i*.04f, -.5f+i*.05f);
				break;
			case 2:
				mCoins[i].setCoin(0, -.5f+i*.05f);
				break;
			default:
				mCoins[i].setCoin(0.1f, -.5f+i*.05f);i++;
				mCoins[i%mCoins.length].setCoin(-.1f, -.5f+i*.05f);
				break;
			}
			
		}
		mStart.load();
	}
	Handler adsHandler = new Handler() {
		public void handleMessage(Message msg) {
			mStart.load();
		}
	};
	void gameStart()
	{
		root.counter = 0;
		M.GameScreen = M.GamePlay;
		mPlayer.CollectCoin =0;
		mPlayer.Distance =0;
		GameTime = System.currentTimeMillis();
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
		long dt = System.currentTimeMillis() - startTime;
		
		if (dt < 50)
			try {
				Thread.sleep(50 - dt);
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
	float xAc =0;
	public void onAccelerationChanged(float x, float y, float z) {
		xAc = x;
//		Log.d("----------------=>  "+x,y+"   -----------    "+z);
	}
	void acc()
	{
		if(mPlayer!=null && M.GameScreen == M.GamePlay && M.setsensor)
		{
			float move = .02f;
			switch (mCarSel) {
			case 0:
				move = .010f;
				break;
			case 1:
				move = .010f;
				break;
			case 2:
				move = .010f;
				break;
			case 3:
				move = .03f;
				break;
			case 4:
				move = .04f;
				break;
			}
			mPlayer.vx = -xAc*move;
		}
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
        SimplePlane SP = null;
        Bitmap b = LoadImgfromAsset(ID);
        try
        {
                SP = new SimplePlane((b.getWidth()/M.mMaxY),(b.getHeight()/M.mMaxY));
                SP.loadBitmap(b);// R.drawable.jay
        }catch(Exception e){}
        return SP;
    }
	SimplePlane add1 (String ID)
	{
		SimplePlane SP = null;
		Bitmap b = LoadImgfromAsset(ID);
		try
		{
			SP = new SimplePlane((b.getWidth()/M.mMaxX),(b.getWidth()/M.mMaxX));
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
//			Log.d((b.getWidth()/M.mMaxX)+"         "+b.getWidth(),(b.getHeight()/M.mMaxY)+"    ____________     "+b.getHeight());
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
//			Log.d(""+ID,"!!!!!!!!!!!!!!!!!!!!!~~~~~~~~~~~~~!!!!!!!!!!!!!!!!!!!!!!!!!"+e.getMessage());
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
//		 Log.d("resizeImg========","newWidth ["+newWidth+"] newHeight ["+newHeight+"]");
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
		mTex_Font	= new SimplePlane[3][];
		for(int j = 0;j<mTex_Font.length;j++)
		{
			Bitmap b = LoadImgfromAsset("font/"+j+".png");
			if(j == 0)
				mTex_Font[j] = new SimplePlane[10];
			if(j == 1)
				mTex_Font[j] = new SimplePlane[26];
			if(j == 2)
				mTex_Font[j] = new SimplePlane[43];
			for(int i = 0;i<mTex_Font[j].length;i++)
				mTex_Font[j][i] = addBitmap(Bitmap.createBitmap(b, i*b.getWidth()/mTex_Font[j].length, 0,b.getWidth()/mTex_Font[j].length, b.getHeight(),null, true));
		}
	}
	void BG()
	{
		mTex_BG	= new SimplePlane[4][];
		for(int j = 0;j<4;j++)
		{
			Bitmap b = LoadImgfromAsset("bg/"+j+".png");
			mTex_BG[j]	= new SimplePlane[b.getWidth()/32];
			for(int i = 0;i<mTex_BG[j].length;i++)
				mTex_BG[j][i] = addBitmap(Bitmap.createBitmap(b, i*b.getWidth()/mTex_BG[j].length, 0,b.getWidth()/mTex_BG[j].length, b.getHeight(),null, true));
		}
	}
	void setRoadBlock()
	{
		mPath = 0;
		mRoadBlock= new RoadBlock[2][];
		mRoadBlock[0] = new RoadBlock[9];
		for(int i=0; i<mRoadBlock[0].length;i++)
		{
			int blockno[]= null;
			int times = 50;
			switch (i) {
			case 0:
				times = 150;//150;
				blockno = new int[2];
				blockno[0] = 29;
				blockno[1] = 28;
				break;
			case 1:
				times = 1;
				blockno = new int[4];
				blockno[0] = 27;
				blockno[1] = 26;
				blockno[2] = 25;
				blockno[3] = 24;
				break;
			case 2:
				times = 150;//100;
				blockno = new int[2];
				blockno[0] = 23;
				blockno[1] = 22;
				break;
			case 3:
				times = 1;
				blockno = new int[4];
				blockno[0] = 21;
				blockno[1] = 20;
				blockno[2] = 19;
				blockno[3] = 18;
				break;
			case 4:
				times = 400;//50;
				blockno = new int[2];
				blockno[0] = 17;
				blockno[1] = 16;
				break;
			case 5:
				times = 1;
				blockno = new int[8];
				blockno[0] = 15;
				blockno[1] = 14;
				blockno[2] = 13;
				blockno[3] = 12;
				blockno[4] = 11;
				blockno[5] = 10;
				blockno[6] = 9;
				blockno[7] = 8;
				break;
			case 6:
				times = 300;//50;
				blockno = new int[2];
				blockno[0] = 7;
				blockno[1] = 6;
				break;
			case 7:
				times = 1;
				blockno = new int[4];
				blockno[0] = 5;
				blockno[1] = 4;
				blockno[2] = 3;
				blockno[3] = 2;
				break;
			case 8:
				times = 200;//200;
				blockno = new int[2];
				blockno[0] = 1;
				blockno[1] = 0;
				break;
			}
			mRoadBlock[0][i] = new RoadBlock(times, blockno);
		}
		/************************************************************************/
		mRoadBlock[1] = new RoadBlock[11];
		for(int i=0; i<mRoadBlock[1].length;i++)
		{
			int blockno[]= null;
			int times = 50;
			switch (i) {
			case 0:
				times = 1;//200;
				blockno = new int[1];
				blockno[0] = 4;
				break;
			case 1:
				times = 400;//150;
				blockno = new int[2];
				blockno[0] = 46;
				blockno[1] = 45;
				break;
			case 2:
				times = 1;
				blockno = new int[6];
				blockno[0] = 44;
				blockno[1] = 43;
				blockno[2] = 42;
				blockno[3] = 41;
				blockno[4] = 40;
				blockno[5] = 39;
				break;
			case 3:
				times = 350;//100;
				blockno = new int[2];
				blockno[0] = 38;
				blockno[1] = 37;
				break;
			case 4:
				times = 1;
				blockno = new int[8];
				blockno[0] = 36;
				blockno[1] = 35;
				blockno[2] = 34;
				blockno[3] = 33;
				blockno[4] = 32;
				blockno[5] = 31;
				blockno[6] = 30;
				blockno[7] = 29;
				break;
			case 5:
				times = 250;//50;
				blockno = new int[2];
				blockno[0] = 28;
				blockno[1] = 27;
				break;
			case 6:
				times = 1;
				blockno = new int[11];
				blockno[0] = 26;
				blockno[1] = 25;
				blockno[2] = 24;
				blockno[3] = 23;
				blockno[4] = 22;
				blockno[5] = 21;
				blockno[6] = 20;
				blockno[7] = 19;
				blockno[8] = 18;
				blockno[9] = 17;
				blockno[10]= 16;
				break;
			case 7:
				times = 200;//50;
				blockno = new int[2];
				blockno[0] = 15;
				blockno[1] = 14;
				break;
			case 8:
				times = 1;
				blockno = new int[7];
				blockno[0] = 13;
				blockno[1] = 12;
				blockno[2] = 11;
				blockno[3] = 10;
				blockno[4] = 9;
				blockno[5] = 8;
				blockno[6] = 7;
				break;
			case 9:
				times = 200;//200;
				blockno = new int[2];
				blockno[0] = 6;
				blockno[1] = 5;
				break;
			case 10:
				times = 1;
				blockno = new int[5];
				blockno[0] = 4;
				blockno[1] = 3;
				blockno[2] = 2;
				blockno[3] = 1;
				blockno[4] = 0;
				break;
			}
			mRoadBlock[1][i] = new RoadBlock(times, blockno);
		}
	}
	/*void setRoadBlock()
	{
		mPath = 0;
		mRoadBlock= new RoadBlock[2][];
		mRoadBlock[0] = new RoadBlock[9];
		for(int i=0; i<mRoadBlock[0].length;i++)
		{
			int blockno[]= null;
			int times = 5;
			switch (i) {
			case 0:
				times = 10;//150;
				blockno = new int[2];
				blockno[0] = 29;
				blockno[1] = 28;
				break;
			case 1:
				times = 1;
				blockno = new int[4];
				blockno[0] = 27;
				blockno[1] = 26;
				blockno[2] = 25;
				blockno[3] = 24;
				break;
			case 2:
				times = 1;//100;
				blockno = new int[2];
				blockno[0] = 23;
				blockno[1] = 22;
				break;
			case 3:
				times = 1;
				blockno = new int[4];
				blockno[0] = 21;
				blockno[1] = 20;
				blockno[2] = 19;
				blockno[3] = 18;
				break;
			case 4:
				times = 4;//50;
				blockno = new int[2];
				blockno[0] = 17;
				blockno[1] = 16;
				break;
			case 5:
				times = 1;
				blockno = new int[8];
				blockno[0] = 15;
				blockno[1] = 14;
				blockno[2] = 13;
				blockno[3] = 12;
				blockno[4] = 11;
				blockno[5] = 10;
				blockno[6] = 9;
				blockno[7] = 8;
				break;
			case 6:
				times = 3;//50;
				blockno = new int[2];
				blockno[0] = 7;
				blockno[1] = 6;
				break;
			case 7:
				times = 1;
				blockno = new int[4];
				blockno[0] = 5;
				blockno[1] = 4;
				blockno[2] = 3;
				blockno[3] = 2;
				break;
			case 8:
				times = 2;//200;
				blockno = new int[2];
				blockno[0] = 1;
				blockno[1] = 0;
				break;
			}
			mRoadBlock[0][i] = new RoadBlock(times, blockno);
		}
		*//************************************************************************//*
		mRoadBlock[1] = new RoadBlock[11];
		for(int i=0; i<mRoadBlock[1].length;i++)
		{
			int blockno[]= null;
			int times = 50;
			switch (i) {
			case 0:
				times = 1;//200;
				blockno = new int[1];
				blockno[0] = 4;
				break;
			case 1:
				times = 40;//150;
				blockno = new int[2];
				blockno[0] = 46;
				blockno[1] = 45;
				break;
			case 2:
				times = 1;
				blockno = new int[6];
				blockno[0] = 44;
				blockno[1] = 43;
				blockno[2] = 42;
				blockno[3] = 41;
				blockno[4] = 40;
				blockno[5] = 39;
				break;
			case 3:
				times = 30;//100;
				blockno = new int[2];
				blockno[0] = 38;
				blockno[1] = 37;
				break;
			case 4:
				times = 1;
				blockno = new int[8];
				blockno[0] = 36;
				blockno[1] = 35;
				blockno[2] = 34;
				blockno[3] = 33;
				blockno[4] = 32;
				blockno[5] = 31;
				blockno[6] = 30;
				blockno[7] = 29;
				break;
			case 5:
				times = 20;//50;
				blockno = new int[2];
				blockno[0] = 28;
				blockno[1] = 27;
				break;
			case 6:
				times = 1;
				blockno = new int[11];
				blockno[0] = 26;
				blockno[1] = 25;
				blockno[2] = 24;
				blockno[3] = 23;
				blockno[4] = 22;
				blockno[5] = 21;
				blockno[6] = 20;
				blockno[7] = 19;
				blockno[8] = 18;
				blockno[9] = 17;
				blockno[10]= 16;
				break;
			case 7:
				times = 20;//50;
				blockno = new int[2];
				blockno[0] = 15;
				blockno[1] = 14;
				break;
			case 8:
				times = 1;
				blockno = new int[7];
				blockno[0] = 13;
				blockno[1] = 12;
				blockno[2] = 11;
				blockno[3] = 10;
				blockno[4] = 9;
				blockno[5] = 8;
				blockno[6] = 7;
				break;
			case 9:
				times = 20;//200;
				blockno = new int[2];
				blockno[0] = 6;
				blockno[1] = 5;
				break;
			case 10:
				times = 1;
				blockno = new int[5];
				blockno[0] = 4;
				blockno[1] = 3;
				blockno[2] = 2;
				blockno[3] = 1;
				blockno[4] = 0;
				break;
			}
			mRoadBlock[1][i] = new RoadBlock(times, blockno);
		}
	}*/
}
