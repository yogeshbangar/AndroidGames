package com.fun2sh.penguinfreejump;

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
	
	SimplePlane mTex_Finger,mTex_Tap,mTex_Top2;
	SimplePlane[] mTex_Ice,mTex_Sprite,mTex_Font;
	
	
	SimplePlane mTex_Logo,mTex_Splash,mTex_SText,mTex_Play,mTex_Leader,mTex_Achiv;
	SimplePlane mTex_Board,mTex_Classic,mTex_Time,mTex_Survival,mTex_Sound[],mTex_Rate;
	SimplePlane mTex_Gover,mTex_Win,mTex_GPaused,mTex_Jump,mTex_TrvTime,mTex_Best;
	SimplePlane mTex_Retry,mTex_Menu,mTex_Score;
	
	Hardle mHardle[];
	Player mPlayer;
	
	boolean addFree = false;
	boolean SingUpadate = false;
	boolean mAchiUnlock[] = new boolean[5];
	
	int mScore;
	int mLevel =0;
	int m1Best;
	int m2Best;
	long mTime;
	long m0Best;
	float mBG =0;
	public GameRenderer(Context context) 
	{
		mContext = context;
		mStart	= (Start)mContext;
		root = new Group(this);
		init();
	}

	void init() {
		try {
			mTex_Top2 = add("top2.png");
			mTex_Finger = add("finger.png");
			mTex_Tap = add("tap.png");
			mTex_Ice = new SimplePlane[5];
			for(int i =0;i<mTex_Ice.length;i++)
				mTex_Ice[i] = add("ice"+i+".png");
			
			Bitmap b = LoadImgfromAsset("penguin_sprite.png");
			mTex_Sprite = new SimplePlane[11];
			for(int i =0;i<mTex_Sprite.length;i++){
				mTex_Sprite[i] = addBitmap(Bitmap.createBitmap(b, (b.getWidth()/16)*i, 0, b.getWidth()/16, b.getHeight()));
			}
			
			
			mTex_Logo = add("ui/logo.png");
			mTex_Splash = add("ui/splash.png");
			mTex_SText = add("ui/text.png");
			mTex_Play = add("ui/play.png");
			mTex_Leader = add("ui/leader.png");
			mTex_Achiv = add("ui/achi.png");
			mTex_Board = add("ui/comman.png");
			mTex_Classic = add("ui/classic.png");
			mTex_Time = add("ui/timetravel.png");
			mTex_Survival = add("ui/survival.png");
			mTex_Sound = new SimplePlane[2];
			mTex_Sound[0] = add("ui/soundoff.png");
			mTex_Sound[1] = add("ui/soundon.png");
			mTex_Rate = add("ui/rate.png");
			mTex_Gover = add("ui/gameover.png");
			mTex_Win = add("ui/youwin.png");
			mTex_GPaused = add("ui/gamepaused_taxt.png");
			mTex_Jump = add("ui/jump.png");
			mTex_TrvTime = add("ui/time.png");
			mTex_Best = add("ui/best.png");
			mTex_Retry = add("ui/retry.png");
			mTex_Menu = add("ui/menu.png");
			mTex_Score = add("ui/score.png");
			load_Font();
			mHardle = new Hardle[7];
			for(int i =0;i<mHardle.length;i++){
				mHardle[i] = new Hardle(-.5f + i * .5f);
			}
			mPlayer = new Player();
			
//			gameReset();
		} catch (Exception e) {
		}

	}

	void gameReset(int mode) {
		for (int i = 0; i < mHardle.length; i++) {
			mHardle[i].set(-.5f + i * .5f);
		}
		mHardle[0].img[1] = 0;
		mHardle[0].img[0] = mHardle[0].img[2] = 1;
		mPlayer.set(0, -.5f);
		mScore = 0;
		mLevel = mode;
		M.SPD = -.05f;
		if(mode == 2)
			M.SPD = -.01f;
		
		mTime = System.currentTimeMillis();
		
		mScore = 0;
		mBG = -.3f+(M.ROW+1.4f)*.5f;
		if(ads %3 ==0)
			mStart.load();
		ads++;
	}
	int ads = 0;
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glClearColor(.475f, .792f, .976f, 1.0f);
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
		} catch (Exception e) {
		}
		return SP;
	}

	Bitmap LoadImgfromAsset(String ID) {
		try {
			return BitmapFactory.decodeStream(mContext.getAssets().open(ID));
		} catch (Exception e) {
			System.out.println(ID + " ~~~  " + e.getMessage());
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
		mTex_Font = new SimplePlane[11];
		Bitmap b = LoadImgfromAsset("font.png");
		for (int i = 0; i < mTex_Font.length; i++)
			mTex_Font[i] = addBitmap(Bitmap.createBitmap(b, i * b.getWidth()/ 16, 0, b.getWidth() / 16,b.getHeight(), null, true));
	}
}
