package com.game2d.timberboy;

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
	
	SimplePlane mTex_BG[],mTex_Logo,mTex_Font[],mTex_smoke[],mTimberBoy;
	SimplePlane mTex_Player[],mTex_Block,mTex_Branch[][],mTex_Over,mTex_ScoreBg,mTex_Retry;
	SimplePlane mTex_about,mTex_fb,mTex_lether,mTex_Music[],mTex_play,mTex_rate,mTex_sound[];
	SimplePlane mTex_CutBlock,mTex_Home,mTex_ads,mTex_gp,mTex_Share;
	SimplePlane mTex_Tab[],mTex_FillBar,mTex_Red,mTex_Rip;
	
	Branch mBranch[];
	CutAnim mCutAnim[];
	Player mPlayer;
	
	int mScore = 0;
	int mHScore = 0;
	int mTotal =0;
	boolean addFree = false;
	boolean SingUpadate = false;
	boolean mAchiUnlock[] = new boolean[5];
	
	
	InApp mInApp;
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
			mTex_smoke	= new SimplePlane[8];
			Bitmap b = LoadImgfromAsset("smoke.png");
			for(int i = 0;i<mTex_smoke.length;i++)
				mTex_smoke[i] = addBitmap(Bitmap.createBitmap(b, i*b.getWidth()/8, 0,b.getWidth()/8, b.getHeight(),null, true));
			mTex_Rip = add("ui/rip.png");
			mTex_FillBar = add("ui/fillbar.png");
			mTex_Red = add("ui/red.png");
			mTex_Logo = add("logo.png");
			mTex_BG = new SimplePlane[5];
			for(int i =0;i<mTex_BG.length;i++)
				mTex_BG[i] = add("bg"+i+".jpg");
			mTex_Player = new SimplePlane[4];
			mTex_Player[0] = add("0.png");
			mTex_Player[1] = add("1.png");
			mTex_Player[2] = addBitmap(FlipHorizontal(LoadImgfromAsset("0.png")));
			mTex_Player[3] = addBitmap(FlipHorizontal(LoadImgfromAsset("1.png")));
			
			mTex_Tab = new SimplePlane[2];
			mTex_Tab[0] = add("ui/tab1.png");
			mTex_Tab[1] = add("ui/tab2.png");
			
			
			mTex_Block = add("block.png");
			
			
			mTex_Branch = new SimplePlane[5][];
			for(int i =0;i<mTex_Branch.length;i++){
				mTex_Branch[i] = new SimplePlane[2];
				mTex_Branch[i][0] = addRotate("branch"+i+".png");
				mTex_Branch[i][1] = addRBit(FlipHorizontal(LoadImgfromAsset("branch"+i+".png")));
			}
			
//			mTex_Branch[0][1] = addRotate("branch2.png");
//			mTex_Branch[0][3] = addRBit(FlipHorizontal(LoadImgfromAsset("branch2.png")));
			
			mTex_CutBlock = addRotate("block.png");
			mTimberBoy= add("ui/timber-boy.png");
			mTex_Home = add("ui/home.png");
			mTex_about = add("ui/about.png");
			mTex_fb = add("ui/fb.png");
			mTex_lether = add("ui/lether.png");
			mTex_Music = new SimplePlane[2];
			mTex_Music[0]= add("ui/music_off.png");
			mTex_Music[1]= add("ui/music_on.png");
			mTex_play = add("ui/play_mainmenu.png");
			mTex_rate = add("ui/rate.png");
			mTex_sound = new SimplePlane[2];
			mTex_sound[0]= add("ui/sound_off.png");
			mTex_sound[1]= add("ui/sound_on.png");
			
			mTex_ads = add("ui/-ads.png");
			mTex_gp = add("ui/achievemen.png");
			mTex_Share = add("ui/share.png");
			
			mTex_Over = add("ui/gameover.png");
			mTex_ScoreBg = add("ui/score-bg.png");
			mTex_Retry = add("ui/retry.png");
			load_Font();
			mBranch = new Branch[10];
			for(int i =0;i<mBranch.length;i++){
				mBranch[i] = new Branch();
			}
			mPlayer = new Player();
			mCutAnim = new CutAnim[10];
			for(int i =0;i<mCutAnim.length;i++){
				mCutAnim[i] = new CutAnim();
			}
			
			
			gameReset(false);
		} catch (Exception e) {
		}
	}
	int AdcCount =0;
	void gameReset(boolean isStart) {
		for(int i =0;i<mBranch.length;i++){
			mBranch[i].set(-.49f+mTex_Block.Height()*i,i%2==0?M.mRand.nextInt(5):4);
			if(i< 5){
				mBranch[i].branch =4;
			}
		}
		for(int i =0;i<mCutAnim.length;i++){
			mCutAnim[i].reset();
		}
		mScore =0;
		mPlayer.set();
		if(isStart){
			if(AdcCount%10==1)
				mStart.load();
			if(AdcCount%10==2)
				GameRenderer.mStart.LoadSmartHandler();
//			mStart.LoadSmartHandler();
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
		if (mStart.adView != null) {
			if (M.GameScreen != M.GAMEOVER) {
				int inv = mStart.adView.getVisibility();
				if (inv == AdView.GONE) {
					try {
						handler.sendEmptyMessage(AdView.VISIBLE);
					} catch (Exception e) {
					}
				}
			} else {
				int inv = mStart.adView.getVisibility();
				if (inv == AdView.VISIBLE) {
					try {
						handler.sendEmptyMessage(AdView.GONE);
					} catch (Exception e) {
					}
				}
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
	
	SimplePlane addRBit(final Bitmap b)
    {
        SimplePlane SP = null;
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
			System.out.println(ID+"!!!!!!!!!!!!!!!!!!!!!~~~~~~~~~~~~~!!!!!!!!!!!!!!!!!!!!!!!!!"+e.getMessage());
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
	void load_Font()
	{
		mTex_Font	= new SimplePlane[10];
		Bitmap b = LoadImgfromAsset("ui/sprite.png");
		for(int i = 0;i<mTex_Font.length;i++)
			mTex_Font[i] = addBitmap(Bitmap.createBitmap(b, i*b.getWidth()/16, 0,b.getWidth()/16, b.getHeight(),null, true));
	}
}
