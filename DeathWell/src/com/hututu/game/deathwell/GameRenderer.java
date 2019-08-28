package com.hututu.game.deathwell;

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
	boolean addFree = false;
	
	SimplePlane mTex_Car,mTex_GameBG,mTex_Logo,mTex_Score,mTex_Board,mTex_fb,mTex_Icon,mTex_LBoard,mTex_Play,mTex_Retry;
	SimplePlane mTex_Opp[],mTex_Font[][],mTex_Sound[],mTex_Blast[],mTex_Partical[],mTex_Over;
	
	boolean SingUpadate =false;
	boolean mAchiUnlock[] = new boolean[5];
	
	byte OverCount = 0;
	byte oppCrash= 0;
	
	int mTotal =0;
	int Cir = 0;
	int BestCir = 0;
	
	
	pixelArray mPixel;
	Player mPlayer;
	Opponent mOpp[];
	
	Crash mCrash[];
	
	
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
			mTex_Logo = add("hututugames.png");
			mTex_Car = addRotate("car.png");
			mTex_GameBG = add("bg.png");
					
			mTex_Opp = new SimplePlane[3];
			for(int i=0;i<mTex_Opp.length;i++){
				mTex_Opp[i] = addRotate("car_opp"+i+".png");
			}
			
			mTex_Blast = new SimplePlane[4];
			for(int i=0;i<mTex_Blast.length;i++){
				mTex_Blast[i] = add("blast/img/"+i+".png");
			}
			mTex_Partical = new SimplePlane[8];
			for(int i=0;i<mTex_Partical.length;i++){
				mTex_Partical[i] = add("blast/"+i+".png");
			}
			
			mTex_Over = add("gameover.png");
			mTex_Score = add("bestscore.png");
			mTex_Board = add("board.png");
			mTex_fb = add("facebook.png");
			mTex_Icon = add("icon.png");
			mTex_LBoard = add("lederboard.png");
			mTex_Play = add("play.png");
			mTex_Retry = add("retry.png");
			mTex_Sound = new SimplePlane[2];
			mTex_Sound[0] = add("sound_off.png"); 
			mTex_Sound[1] = add("sound_on.png");
			
			
			load_Font();
			
			mOpp = new Opponent[3];
			for(int i =0;i<mOpp.length;i++){
				mOpp[i] = new Opponent();
			}
			
			
			mCrash = new Crash[50];
			for(int i =0;i<mCrash.length;i++){
				mCrash[i] = new Crash();
			}
			
			mPlayer = new Player();
			mPixel = new pixelArray(M.ARR);
			mPixel.setPixelArray(LoadImgfromAsset("path.png"));
			
			
			gameReset(false);
		}catch(Exception e){}
		
	}
	int count =0;
	void gameReset(boolean From) {
		mPlayer.set(1, 560);
		Cir = 0;
		for(int i =0;i<mOpp.length;i++){
			mOpp[i].set(1, 530-i*35);
		}
		for(int i =0;i<mCrash.length;i++){
			mCrash[i].reset();
		}
		count++;
		if(From)
			mStart.load();
		OverCount = 0;
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
			if (M.GameScreen != M.GAMEPLAY) {
				int inv = mStart.adView.getVisibility();
				if (inv == AdView.GONE) {try {handler.sendEmptyMessage(AdView.VISIBLE);} catch (Exception e) {}}
			} else {
				int inv = mStart.adView.getVisibility();
				if (inv == AdView.VISIBLE) {try {handler.sendEmptyMessage(AdView.GONE);} catch (Exception e) {}}
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
			System.out.println(ID);
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
		mTex_Font = new SimplePlane[2][10];
		for (int j = 0; j < mTex_Font.length; j++){
			Bitmap b = LoadImgfromAsset(j+".png");
			for (int i = 0; i < mTex_Font[j].length; i++)
				mTex_Font[j][i] = addBitmap(Bitmap.createBitmap(b, i * b.getWidth()/ 16, 0, b.getWidth() / 16,b.getHeight(), null, true));
		}
	}
}
