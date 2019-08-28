package com.hututu.game.minehero;

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

public class GameRenderer implements Renderer 
{
	final Group root;
	public static Context mContext;
	public static Start mStart;
	long startTime = System.currentTimeMillis();
	
	
	boolean addFree = false;
	boolean SingUpadate = false;
	boolean mAchiUnlock[] = new boolean[5];
	
	boolean imgreset = false;
	
	int Tileimg = 0;
	int mCrystal = 0;
	int mCoin = 0;
	int mMoney = 0;
	int mPoint = 0;
	int mBPoint = 0;
	int newMoster;
	
	float mBG[] = new float[3];
	float spd;
	float dist = 0;
	float Score = 0;
	
	
	
	TileRow mTileRow[];
	Charctor mChar = new Charctor();
	Chackra mChackra = new Chackra();
	Monster mMonster[];

	MonBlast mMonBlast[];
	Slider mSlider[];

	Animation mAnim[];
	Animation mDust[];
	AnimCoin mAniCoin[];

	MonBlast mCharBlast;
	MonBlast mChamak = new MonBlast();
	MonBlast mDrill = new MonBlast();
	
	InApp mInApp;
	
	SimplePlane mTex_Logo, mTex_BG, mTex_Rod, mTex_Chakra, mTex_Wood,mTex_Slider;
	SimplePlane mTex_Splash, mTex_Button, mTex_AboutTxt, mTex_About, mTex_hututu;
	SimplePlane mTex_Board, mTex_Buy, mTex_Back,mTex_Start,mTex_Achiv, mTex_Arow;
	SimplePlane mTex_Board1, mTex_fb, mTex_Over, mTex_Help, mTex_Icon,mTex_AFont;
	SimplePlane mTex_Score, mTex_WoodBox, mTex_LBoard, mTex_Crystal, mTex_BuyBtn;
	SimplePlane mTex_UBox, mTex_Total, mTex_TCash, mTex_Retry,mTex_BuyF,mTex_Upg;
	SimplePlane mTex_Pause, mTex_Star;
	SimplePlane mTex_STitle,mTex_Menu,mTex_HScr,mTex_Drill[],mTex_Dust,mTex_Cont;
	SimplePlane[] mTex_Tile, mTex_Font, mTex_Monster[], mTex_MonBlast,mTex_SChar;
	SimplePlane[] mTex_Car, mTex_Txt, mTex_Store, mTex_Blood,mTex_SBox,mTex_Coin; 
	
	private Handler handler = new Handler() {public void handleMessage(Message msg) {mStart.adView.setVisibility(msg.what);}};
	public GameRenderer(Context context) 
	{
		mContext = context;
		mStart	= (Start)mContext;
		root = new Group(this);
		init();
	}

	void init() {
		try {
			mInApp = new InApp();
			mInApp.onCreate();
			mTex_Star = add("star.png");
			mTex_Pause = add("UI/paused_font.png");
			mTex_HScr = add("help.png");
			mTex_Menu = add("UI/menu.png");
			mTex_STitle = add("UI/store_font.png");
			mTex_Upg = add("UI/upgrade.png");
			mTex_BuyBtn = add("UI/buy_0.png");
			mTex_BuyF = add("UI/buy_font.png");
			mTex_Achiv = add("UI/achivement.png");
			mTex_Arow = add("UI/aerrow.png");
			mTex_Board1 = add("UI/board1.png");
			mTex_fb = add("UI/fb.png");
			mTex_Over = add("UI/gameover_font.png");
			mTex_hututu = add("UI/hututu.png");
			mTex_Icon = add("UI/icon.png");
			mTex_LBoard = add("UI/leaderboard.png");
			mTex_Retry = add("UI/retry.png");
			mTex_Score = add("UI/score.png");
			mTex_Total = add("UI/total.png");
			mTex_TCash = add("UI/total_cash.png");
			mTex_UBox = add("box.png");
			mTex_Dust = add("dust.png");
			mTex_Cont = add("UI/continue.png");
			
			mTex_Button = add("UI/button.png");
			mTex_Buy = add("UI/buy.png");
			mTex_About = add("UI/about.png");
			mTex_Help = add("UI/help.png");
			mTex_Start = add("UI/start.png");
			mTex_Board = add("UI/board0.png");
			mTex_AboutTxt = add("UI/aboutus.png");
			mTex_AFont = add("UI/about_font.png");
			mTex_Back = add("UI/back.png");
			
			
			mTex_Logo = add("hututugames.png");
			mTex_BG = add("bg.png");
			mTex_Tile = new SimplePlane[3];
			for (int i = 0; i < mTex_Tile.length; i++)
				mTex_Tile[i] = add("tile" + i + ".png");
			mTex_Txt = new SimplePlane[7];
			for (int i = 0; i < mTex_Txt.length; i++)
				mTex_Txt[i] = add("UI/store_" + i + ".png");
			mTex_Store = new SimplePlane[7];
			for (int i = 0; i < mTex_Store.length; i++)
				mTex_Store[i] = add("store_" + i + ".png");
			
			
			mTex_Splash = add("Splash/Splash.png");
			mTex_SChar = new SimplePlane[3];
			mTex_SChar[0] = add("Splash/Splash_0.png");
			mTex_SChar[1] = add("Splash/Splash_1.png");
			mTex_SChar[2] = add("Splash/Splash_font.png");
			mTex_Crystal = add("crystal.png");
			
			
			mTex_Slider = add("wood_2.png");
			
			
			mTex_Rod  = add("danger_0.png");
			mTex_Chakra = addRotate("danger_1.png");
			mTex_Wood = add("wood.png");
			mTex_WoodBox = add("opt_0.png");
			
			
			
			
			mTex_SBox = new SimplePlane[2];
			mTex_SBox[0] = add("score_bar1.png");
			mTex_SBox[1] = add("score_bar0.png");
			
			mTex_Car = new SimplePlane[11];
			Bitmap b =  LoadImgfromAsset("char0.png");
			mTex_Car[0] = addBitmap(Bitmap.createBitmap(b, (b.getWidth()/2)*0, 0,b.getWidth()/2, b.getHeight(), null, true));
			mTex_Car[1] = addBitmap(Bitmap.createBitmap(b, (b.getWidth()/2)*1, 0,b.getWidth()/2, b.getHeight(), null, true));
			b.recycle();
			b =  LoadImgfromAsset("char1.png");
			mTex_Car[2] = addBitmap(Bitmap.createBitmap(b, (b.getWidth()/4)*0, 0,b.getWidth()/4, b.getHeight(), null, true));
			mTex_Car[3] = addBitmap(Bitmap.createBitmap(b, (b.getWidth()/4)*1, 0,b.getWidth()/4, b.getHeight(), null, true));
			mTex_Car[4] = addBitmap(Bitmap.createBitmap(b, (b.getWidth()/4)*2, 0,b.getWidth()/4, b.getHeight(), null, true));
			mTex_Car[5] = addBitmap(Bitmap.createBitmap(b, (b.getWidth()/4)*3, 0,b.getWidth()/4, b.getHeight(), null, true));
			mTex_Car[6] = addBitmap(FlipHorizontal(mTex_Car[2].getBitmap()));
			mTex_Car[7] = addBitmap(FlipHorizontal(mTex_Car[3].getBitmap()));
			mTex_Car[8] = addBitmap(FlipHorizontal(mTex_Car[4].getBitmap()));
			mTex_Car[9] = addBitmap(FlipHorizontal(mTex_Car[5].getBitmap()));
			mTex_Car[10] =add("drill.png"); 

			b.recycle();
			b =  LoadImgfromAsset("0.png");
			mTex_Monster = new SimplePlane[2][4];
			for(int i=0;i<mTex_Monster[0].length;i++){
				if(i==3){
					mTex_Monster[0][i] = addRotate(Bitmap.createBitmap(b, (b.getWidth()/4)*i, 0,b.getWidth()/4, b.getHeight(), null, true));
					mTex_Monster[1][i] = addRotate(FlipHorizontal(mTex_Monster[0][i].getBitmap()));
				}else{
					mTex_Monster[0][i] = addBitmap(Bitmap.createBitmap(b, (b.getWidth()/4)*i, 0,b.getWidth()/4, b.getHeight(), null, true));
					mTex_Monster[1][i] = addBitmap(FlipHorizontal(mTex_Monster[0][i].getBitmap()));
				}
			}
//			mTex_Monster[0][1] = addRotate("1.png");
//			
//			mTex_Monster[1][0] = addBitmap(FlipHorizontal(mTex_Monster[0][0].getBitmap()));;
//			mTex_Monster[1][1] = addRotate(FlipHorizontal(mTex_Monster[0][1].getBitmap()));;
			
			b.recycle();
			mTex_MonBlast = new SimplePlane[6];
			b = LoadImgfromAsset("chr_opp_blast.png");
			for (int i = 0; i < mTex_MonBlast.length; i++) {
				mTex_MonBlast[i] = addBitmap(Bitmap.createBitmap(b, (b.getWidth() / 4) * (i % 4), (b.getHeight() / 2)
								* (i / 4), b.getWidth() / 4, b.getHeight() / 2, null, true));
			}
			/*b.recycle();
			mTex_Blood = new SimplePlane[7];
			b = LoadImgfromAsset("blood.png");
			for (int i = 0; i < mTex_Blood.length; i++) {
				mTex_Blood[i] = addBitmap(Bitmap.createBitmap(b, (b.getWidth() / 4) * (i % 4), (b.getHeight() / 2)
								* (i / 4), b.getWidth() / 4, b.getHeight() / 2, null, true));
			}*/
			mTex_Blood = new SimplePlane[4];
			for (int i = 0; i < mTex_Blood.length; i++) {
				mTex_Blood[i] = add("Blood/"+i+".png");
			}
			b.recycle();
			mTex_Coin = new SimplePlane[10];
			b = LoadImgfromAsset("coin.png");
			for (int i = 0; i < mTex_Coin.length; i++) {
				mTex_Coin[i] = addBitmap(Bitmap.createBitmap(b, (b.getWidth() / 8) * (i % 8), (b.getHeight() / 2)
								* (i / 8), b.getWidth() / 8, b.getHeight() / 2, null, true));
			}
			
			b.recycle();
			mTex_Drill = new SimplePlane[4];
			b = LoadImgfromAsset("UI/drill_animation.png");
			for (int i = 0; i < mTex_Drill.length; i++) {
				mTex_Drill[i] = addBitmap(Bitmap.createBitmap(b, (b.getWidth() / 4) * (i % 4), 
						0, b.getWidth() / 4, b.getHeight(), null, true));
			}
			
			loadFont();
			mMonster = new Monster[8];
			for(int i=0;i<mMonster.length;i++)
				mMonster[i] = new Monster();
			mMonBlast = new MonBlast[10];
			for(int i=0;i<mMonBlast.length;i++)
				mMonBlast[i] = new MonBlast();
			
			mTileRow = new TileRow[10];
			for(int i=0;i<mTileRow.length;i++)
				mTileRow[i] = new TileRow();
			mAnim = new Animation[50];
			for(int i=0;i<mAnim.length;i++){
				mAnim[i] =  new  Animation();
			}
			mDust = new Animation[10];
			for(int i=0;i<mDust.length;i++){
				mDust[i] =  new  Animation();
			}
			mAniCoin = new AnimCoin[20];
			for(int i=0;i<mAniCoin.length;i++){
				mAniCoin[i] =  new  AnimCoin();
			}
			mSlider = new Slider[3];
			for(int i=0;i<mSlider.length;i++){
				mSlider[i] =  new  Slider();
			}
			for(int i = 0;i<mBG.length;i++)
				mBG[i] = 1-i*mTex_BG.Height();
			
			mCharBlast = new MonBlast();
			
//			gameReset();
		} catch (Exception e) {
		}
	}

	final byte Action[][] = { 
			{ 0, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 0, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 0, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 0, 1, 1, 0, 1, 1 },
			{ 1, 1, 1, 1, 0, 1, 1, 1, 1 },
			{ 1, 0, 1, 1, 1, 0, 1, 1, 1 },
			{ 1, 1, 1, 1, 0, 1, 0, 1, 1 },
			{ 1, 1, 1, 1, 0, 1, 1, 0, 1 },
			{ 1, 1, 0, 1, 1, 1, 1, 1, 0 },
			{ 1, 0, 1, 0, 1, 0, 1, 0, 1 },
			{ 0, 1, 0, 1, 0, 1, 0, 1, 0 },
			{ 0, 1, 1, 0, 1, 1, 0, 1, 1 },
			};
	void gameReset()
	{
		mChar.set(0, .1f);
		spd = 0.05f;
		
		for(int i = 0;i<mBG.length;i++)
			mBG[i] = 1-i*mTex_BG.Height();
		
		for(int i=0;i<mTileRow.length;i++)
			mTileRow[i].set(10,Action[M.mRand.nextInt(Action.length)]);
		
		for(int i=0;i<mAnim.length;i++)
			mAnim[i].set(-10, -10);
		for(int i=0;i<mDust.length;i++)
			mDust[i].vy =0;
		
		for(int i=0;i<mMonster.length;i++)
			mMonster[i].set(-10, -10);
		for(int i=0;i<mMonBlast.length;i++)
			mMonBlast[i].set(-10, -10,100);
		
		for(int i=0;i<mAniCoin.length;i++){
			mAniCoin[i].set(-10, -10, 0, 0);
		}
		for(int i=0;i<mSlider.length;i++){
			mSlider[i].set(-10, -10);
		}
		
		mCharBlast.set(0, -.1f, -1);
		mDrill.set(0, -.1f,100);
		mChamak.set(0, -.1f,100);
		dist = mCrystal = mPoint = Tileimg = mCoin = 0;
		mChackra.set(1.2f);
		imgreset = false;
		root.setOpp(-1,-.3f);
		newMoster = 300;
		mStart.load();
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
		if(mStart.adView!=null){
			if(M.GameScreen == M.GAMEPLAY){
				int inv=mStart.adView.getVisibility();
				if(inv==AdView.GONE){try{handler.sendEmptyMessage(AdView.VISIBLE);}catch(Exception e){}}
			}else{
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
	SimplePlane addRotate(String ID) {
		SimplePlane SP = null;
		Bitmap b = LoadImgfromAsset(ID);
		try {
			SP = new SimplePlane((b.getWidth() / M.mMaxY),(b.getHeight() / M.mMaxY));
			SP.loadBitmap(b);
		} catch (Exception e) {
		}
		return SP;
	}
	SimplePlane addRotate(Bitmap b) {
		SimplePlane SP = null;
		try {
			SP = new SimplePlane((b.getWidth() / M.mMaxY),(b.getHeight() / M.mMaxY));
			SP.loadBitmap(b);
		} catch (Exception e) {
		}
		return SP;
	}
	Bitmap LoadImgfromAsset(String ID)
	{
		try{
			return BitmapFactory.decodeStream(mContext.getAssets().open(ID));}
		catch(Exception e)
		{
			System.out.println("~~~~~~~~~~~~~ [" + ID + "] ~~~~~~~~~~~~~~~~~~");
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
//		 System.out.println("resizeImg========newWidth ["+newWidth+"] newHeight ["+newHeight+"]");
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
	void loadFont(){
		mTex_Font = new SimplePlane[12];
		Bitmap b = LoadImgfromAsset("font.png");
		for(int i=0;i<mTex_Font.length;i++){
			mTex_Font[i] = addBitmap(Bitmap.createBitmap(b, i* (b.getWidth() / 16), 0,b.getWidth() / 16, b.getHeight(),null, true));
		}
		
	}
	
}
