package com.hututu.game.shadowninja;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;

public class GameRenderer implements Renderer 
{
	final Group root;
	public static Context mContext;
	public static Start mStart;
	long startTime = System.currentTimeMillis();

	boolean addFree = false;
	SimplePlane mTex_Logo, mTex_BG,mTex_Play, mTex_Rate, mTex_Score, mTex_SBox, mTex_Sound[];
	SimplePlane mTex_Ads, mTex_Best, mTex_Gameover, mTex_Home, mTex_leader,mTex_Splash,mTex_Font[];
	SimplePlane mTex_PSit,mTex_OpSit,mTex_Help,mTex_Pause,mTex_Achive;		
	SimplePlane[][] mTex_PAct, mTex_Opp, mTex_Die;
	
	
	boolean SingUpadate = false;
	boolean mAchiUnlock[] = new boolean[5];
	boolean gameStart = false;
	int mScore = 0;
	int mTotal = 0;
	int mHScore = 0;
	int gameOver = 0;
	
	final float dis = .7f;
	
	BaseWay mWay[];
	Player mPlyer;
	Opponent mOpp[];
	
	Kill mHide;
	InApp mInApp;
//	private Handler handler = new Handler() {public void handleMessage(Message msg) {mStart.adView.setVisibility(msg.what);}};
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
			mInApp = new InApp();
			mInApp.onCreate();
			mTex_Logo = add("hututugames.png");
			mTex_BG = add("block_GP.png");
			
			
			mTex_PAct = new SimplePlane[3][];
			
			Bitmap  b = LoadImgfromAsset("run_L.png"); 
			mTex_PAct[0] = new SimplePlane[6];
			for(int i =0;i<mTex_PAct[0].length;i++){
				mTex_PAct[0][i] = addBitmap(Bitmap.createBitmap(b, (b.getWidth()/4)*(i%4), (b.getHeight()/2)*(i/4), b.getWidth()/4, b.getHeight()/2));
			}
			b.recycle();
			mTex_PAct[1] = new SimplePlane[1];
			mTex_PAct[1][0] = addRotate("jump.png");
			System.out.println(mTex_PAct[1][0]);
			
//			b = LoadImgfromAsset("jump.png");
//			for(int i =0;i<mTex_PAct[1].length;i++){
//				mTex_PAct[1][i] = addBitmap(Bitmap.createBitmap(b, (b.getWidth()/4)*(i%4), (b.getHeight()/2)*(i/4), b.getWidth()/4, b.getHeight()/2));
//			}
			b.recycle();
			mTex_PAct[2] = new SimplePlane[3];
			b = LoadImgfromAsset("hit.png"); 
			for(int i =0;i<mTex_PAct[2].length;i++){
				mTex_PAct[2][i] = addBitmap(Bitmap.createBitmap(b, (b.getWidth()/4)*i, 0, b.getWidth()/4, b.getHeight()));
			}
//			b.recycle();
//			mTex_PAct[3] = new SimplePlane[6];
//			b = LoadImgfromAsset("attacking2.png"); 
//			for(int i =0;i<mTex_PAct[3].length;i++){
//				mTex_PAct[3][i] = addBitmap(Bitmap.createBitmap(b, (b.getWidth()/4)*(i%4), (b.getHeight()/2)*(i/4), b.getWidth()/4, b.getHeight()/2));
//			}
			
			mTex_Opp = new SimplePlane[3][];
			b.recycle();
			b = LoadImgfromAsset("red_sprite.png"); 
			mTex_Opp[0] = new SimplePlane[4];
			for(int i =0;i<mTex_Opp[0].length;i++){
				mTex_Opp[0][i] = addBitmap(FlipHorizontal(Bitmap.createBitmap(b, (b.getWidth()/4)*i, 0, b.getWidth()/4, b.getHeight())));
			}
			b.recycle();
			mTex_Opp[1] = new SimplePlane[5];
			mTex_Opp[1][0] = addRotate("jump_red.png");
			
			b = LoadImgfromAsset("red_sprite.png");
			for(int i =0;i<4;i++){
				mTex_Opp[1][i+1] = addBitmap(/*FlipHorizontal*/(Bitmap.createBitmap(b, (b.getWidth()/4)*i, 0, b.getWidth()/4, b.getHeight())));
			}
			
			b.recycle();
			mTex_Opp[2] = new SimplePlane[5];
			b = LoadImgfromAsset("blue.png");
			for(int i =0;i<mTex_Opp[2].length;i++){
				mTex_Opp[2][i] = addBitmap(FlipHorizontal(Bitmap.createBitmap(b, (b.getWidth()/4)*(i%4), (b.getHeight()/2)*(i/4), b.getWidth()/4, b.getHeight()/2)));
			}
			
			

			mTex_Die = new SimplePlane[2][];
			b.recycle();
			b = LoadImgfromAsset("dying.png"); 
			mTex_Die[0] = new SimplePlane[1];
			mTex_Die[0][0] =add("dying.png"); 
			mTex_Die[1] = new SimplePlane[1];
			mTex_Die[1][0] =addBitmap(FlipHorizontal(LoadImgfromAsset("dying_blue.png")));
//			for(int i =0;i<mTex_Die[0].length;i++){
//				mTex_Die[0][i] = addBitmap((Bitmap.createBitmap(b, (b.getWidth()/4)*i,0, b.getWidth()/4, b.getHeight())));
//				mTex_Die[1][i] = addBitmap(FlipHorizontal(Bitmap.createBitmap(b, (b.getWidth()/4)*i,0, b.getWidth()/4, b.getHeight())));
//			}
			
			
			mTex_Ads = add("-ads.png");
			mTex_Best = add("best.png");
			mTex_Gameover = add("game-over_text.png");
			mTex_Home = add("home_btn.png");
			mTex_leader = add("lether.png");
			mTex_Splash = add("text.png");
			mTex_Play = add("play_btn.png");
			mTex_Rate = add("rate_btn.png");
			mTex_Score = add("score.png");
			mTex_SBox = add("score-box.png");
			mTex_Sound = new SimplePlane[2];
			mTex_Sound[0] = add("sound_btn.png");
			mTex_Sound[1] = add("sound_off_btn.png");
			mTex_Help = add("help.png");
			
			mTex_PSit= add("0.png"); 
			mTex_OpSit = add("00.png");
			mTex_Pause = add("paused_text.png");
			mTex_Achive = add("achive.png");
			load_Font();
			
			mPlyer = new Player();
			mWay = new BaseWay[10];
			for(int i =0;i<mWay.length;i++){
				mWay[i] = new BaseWay();
			}
			
			mOpp = new Opponent[5];
			for(int i =0;i<mOpp.length;i++){
				mOpp[i] = new Opponent();
			}
			mHide = new Kill();
//			gameReset();
		}catch(Exception e){}
		
	}
	
	
	void gameReset()
	{
		gameOver =0;
		mWay[0].sx = 2;
		mWay[0].set(0);
		mWay[0].y = -.57f;
		for(int i =1;i<mWay.length;i++){
			mWay[i].setSx();
			mWay[i].set(dis+mWay[i-1].x+(mWay[i-1].sx+mWay[i].sx)*(mTex_BG.width()/2f));
		}
		for(int i =0;i<mOpp.length;i++){
			mOpp[i].set(-100, 0,0);
		}
		mPlyer.set(-.6f, 0);
		M.spd = -.025f;
		mScore = 0;
		mHide.set(100, 100, 0);
		gameStart = false;
		if(ads %3 == 0)
			GameRenderer.mStart.load();
		ads++;
	}
	int ads =0;
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glClearColor(.8f, .8f, .8f, 1.0f);
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
//			if(M.GameScreen != M.GAMEMENU)
//			{
//				int inv=mStart.adView.getVisibility();
//				if(inv==AdView.GONE){try{handler.sendEmptyMessage(AdView.VISIBLE);}catch(Exception e){}}
//			}
//			else
//			{
//				int inv=mStart.adView.getVisibility();
//				if(inv==AdView.VISIBLE){try{handler.sendEmptyMessage(AdView.GONE);}catch(Exception e){}}
//			}
//		}
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
		if (dt < 25)
			try {
				Thread.sleep(25 - dt);
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
		mTex_Font = new SimplePlane[13];
		Bitmap b = LoadImgfromAsset("font.png");
		for (int i = 0; i < mTex_Font.length; i++)
			mTex_Font[i] = addBitmap(Bitmap.createBitmap(b, i * b.getWidth()/ 16, 0, b.getWidth() / 16,b.getHeight(), null, true));
	}
}
