package com.hututu.game.footballshoot2014;

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
	final 	Group root;
	static Context mContext;
	public 	static Start mStart;
	long 	startTime = System.currentTimeMillis();
	int 	resumeCounter =0;
	int 	mSel = 0;
	boolean addFree,SingUpadate;
	
	SimplePlane[] mTex_Level,mTex_Blast,mTex_FanF;
	SimplePlane[] mTex_BackGround,mTex_Ball ,mTex_Fan, mTex_Font, mTex_ICN, mTex_Sound,mTex_FBulti[],mTex_BBulti[];
	SimplePlane mTex_Exit,mTex_Menu,mTex_Stadium,mTex_More,mTex_PText,mTex_Resume,mTex_Retry,mTex_Arrow,mTex_GOver;
	SimplePlane mTex_SButton,mTex_Button,mTex_BButton,mTex_Popup, mTex_SBoard,mTex_Logo, mTex_AboutText,mTex_About;
	SimplePlane mTex_Pointer, mTex_Hightbar,mTex_Skip,mTex_STxt, mTex_MenuIcn, mTex_FSButton, mTex_Tel, mTex_BackI;
	SimplePlane mTex_Score,mTex_BSCore,mTex_Buy,mTex_JoinF,mTex_Later,mTex_Join,mTex_JoinTex,mTex_BoardF, mTex_Yes;
	SimplePlane mTex_ExitAds,mTex_No,mTex_ExitI,mTex_RightI,mTex_Back,mTex_Quit,mTex_Like;
	
	Stone mStone;
	
	int mScore =0;
	int mBalti =0;
	int mLScore[]= new int[3];
	int mLevel = 0;
	int mBG = 0;
	boolean Achi[] = new boolean[5];
	boolean isFromMenu = true;
	Position[] mPos;
	Animation mAni[];
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
		int i =0;
		try
		{
			mMainActivity = new MainActivity();
			mMainActivity.onCreate();
			
			mTex_Pointer = add("pointer.png");//AdHouse
			mTex_Hightbar = add("hightbar0.png");//AdHouse
			mTex_Skip = add("exit_icon.png");//AdHouse
			
			
			
			mTex_Blast = new SimplePlane[3];
			for (i = 0; i < mTex_Blast.length; i++)
				mTex_Blast[i] = add("blast/" + i + ".png");
			mTex_Quit 		= add("quit.png");
			mTex_BackI		= add("UI/back.png");
			mTex_ExitI		= add("UI/exit.png");
			mTex_RightI		= add("UI/right.png");
			mTex_Like 		= add("rateus.png");
			mTex_BoardF 	= add("board_front.png");
			mTex_ExitAds 	= add("exit_ads.png");
			mTex_No 		= add("no.png");
			mTex_Yes 		= add("yes.png");
			
			mTex_Tel		= add("smoke2.png");
			mTex_Buy		= add("UI/buy.png");
			mTex_JoinF		= add("UI/font_join.png");
			mTex_Later		= add("UI/font_later.png");
			mTex_Join		= add("UI/join.png");
			mTex_JoinTex	= add("UI/tex_join.png");
			
			mTex_BSCore		= add("UI/bestscore_gameover.png");
			mTex_Score 		= add("UI/score_gameover.png");
			mTex_GOver		= add("UI/font_gameover.png");
			mTex_PText		= add("UI/font_paushed.png");
			mTex_More		= add("UI/text_freegame.png");
			mTex_MenuIcn	= add("UI/menu.png");
			mTex_Resume		= add("UI/resume.png");
			mTex_Retry		= add("UI/retry.png");
			mTex_Sound		= new SimplePlane[2];
			mTex_Sound[0]	= add("UI/sound_off.png");
			mTex_Sound[1]	= add("UI/sound_on.png");
			mTex_Back		= add("UI/font_back.png");
			mTex_About		= add("UI/font_about.png");
			mTex_AboutText	= add("UI/text_about.png");
			mTex_Stadium	= add("UI/text_stadium.png");
			mTex_ICN		= new SimplePlane[9];
			mTex_ICN[0]		= add("UI/g+.png");
			mTex_ICN[1]		= add("UI/facebook.png");
			mTex_ICN[2]		= add("UI/share.png");
			mTex_ICN[3]		= add("UI/leder-boad.png");
			mTex_ICN[4]		= add("UI/achivement.png");
			mTex_ICN[5]		= add("UI/football.png");
			mTex_ICN[6]		= add("UI/icon.png");
			mTex_ICN[7]		= add("UI/about.png");
			mTex_ICN[8]		= add("UI/twitter.png");
			mTex_Level		= new SimplePlane[3];
			mTex_Level[0]	= add("UI/text_easy.png");
			mTex_Level[1]	= add("UI/tex_medium.png");
			mTex_Level[2]	= add("UI/tex_hard.png");
			mTex_STxt			= add("font.png");
			
			mTex_Menu			= add("splash.png");
			mTex_Logo			= add("hututugames.png");
			mTex_BackGround		= new SimplePlane[5];
			for (i = 0; i < mTex_BackGround.length; i++) 
				mTex_BackGround[i]	= add("bg"+i+".png");
			
				
			mTex_FBulti			= new SimplePlane[5][];
			mTex_BBulti			= new SimplePlane[5][];
			for (i = 0; i < mTex_FBulti.length; i++) {
				mTex_FBulti[i] = new SimplePlane[3];
				mTex_BBulti[i] = new SimplePlane[3];
				for (int j = 0; j < mTex_FBulti[i].length; j++) {
					mTex_FBulti[i][j] = add("basket/" + j + "/f" + i + ".png");
					mTex_BBulti[i][j] = add("basket/" + j + "/b" + i + ".png");
				}
			}
			
			mTex_Exit			= add("UI/font_exit.png");
			mTex_BButton		= add("UI/big_button.png");
			mTex_Button			= add("UI/button.png");
			mTex_SButton		= add("UI/small_button.png");
			mTex_FSButton		= addBitmap(FlipHorizontal(LoadImgfromAsset("UI/small_button.png")));
			mTex_Popup			= add("UI/board.png");
			mTex_SBoard			= add("UI/score_bar.png");
			mTex_Arrow			= add("arrow.png");
			
			
			Bitmap b 		= LoadImgfromAsset("fan.png");
			mTex_Fan		= new SimplePlane[2];
			mTex_FanF		= new SimplePlane[2];
			mTex_Fan[0]		= addBitmap(Bitmap.createBitmap(b, 0,0,b.getWidth()/2,b.getHeight(),null, true));
			mTex_FanF[0]	= addBitmap(FlipHorizontal(mTex_Fan[0].mBitmap));
			mTex_Fan[1]		= addBitmap(Bitmap.createBitmap(b, b.getWidth()/2,0,b.getWidth()/2,b.getHeight(),null, true));
			mTex_FanF[1]	= addBitmap(FlipHorizontal(mTex_Fan[1].mBitmap));
			
			
			Bitmap bit		= LoadImgfromAsset("football_sprite.png");
			mTex_Ball		= new SimplePlane[18];
			
			for(i = 0;i<mTex_Ball.length;i++){
				mTex_Ball[i] = addBitmap(Bitmap.createBitmap(bit, 
						i*bit.getWidth()/mTex_Ball.length,0,
						bit.getWidth()/mTex_Ball.length,bit.getHeight(),null, true));
			}
			font();
			mStone = new Stone(this);
			
			mPos 		= new Position[3];
			mPos[0] 	= new Position(0.20f, -.16f,-.28f,0.10f);
			mPos[1] 	= new Position(0.14f, 0.03f,-.01f,0.11f);
			mPos[2] 	= new Position(0.08f, 0.36f,0.30f,0.12f);
			mLevel = 2;
			mAni	= new Animation[20];
			for( i=0;i<mAni.length;i++)
			{
				mAni[i] = new Animation();
			}
			
			mBG	= 1;
			gameReset();
		}catch(Exception e){}
		
	}
	
	
	void gameReset()
	{
		for(int i=0;i<mAni.length;i++)
			mAni[i].reset();
		mStone.set(-100, -100, 1, 0, 0, 0);
		M.mAir	= (float)(M.mRand.nextInt()%500)/100f;
		mScore = 0;
		mStone.no = 10;
		mStone.reset = 0;
		mBalti = M.mRand.nextInt(5);
		if (ads % 1 == 0 ) {
			try {
				adsHandler.sendEmptyMessage(0);
			} catch (Exception e) {
			}
		}
	}
	Handler adsHandler = new Handler() {
		public void handleMessage(Message msg) {
			mStart.load();
		}
	};
	int ads = 0;
	
	
	
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
			if (M.GameScreen != M.GAMEPLAY && M.GameScreen != M.GAMEADD)
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
			System.out.println("["+ID+"] "+e.getMessage());
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
	void font()
	{
		mTex_Font	= new SimplePlane[11];
		Bitmap b = LoadImgfromAsset("fontstrip.png");
		for(int i = 0;i<mTex_Font.length;i++)
			mTex_Font[i] = addBitmap(Bitmap.createBitmap(b, i*b.getWidth()/16, 0,b.getWidth()/16, b.getHeight(),null, true));
	}
}
