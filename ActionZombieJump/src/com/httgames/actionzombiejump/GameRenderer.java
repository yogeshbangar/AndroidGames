package com.httgames.actionzombiejump;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.opengl.GLSurfaceView.Renderer;

public class GameRenderer implements Renderer {
	final Group root;
	public static Context mContext;
	public static Start mStart;
	long startTime = System.currentTimeMillis();

	APlane mTex_Tile[];
	APlane mTex_Zombie[];
	APlane mTex_Logo;
	APlane mTex_Base;
	APlane mTex_BG;
	APlane mTex_Font[];
	APlane mTex_achi;
	APlane mTex_ads;
	APlane mTex_gbox;
	APlane mTex_home;
	APlane mTex_lether;
	APlane mTex_play;
	APlane mTex_rate;
	APlane mTex_share;
	APlane mTex_strip;
	APlane mTex_text;
	APlane mTex_GOver;
	APlane mTex_Pause;
	APlane mTex_Score;
	APlane mTex_Coin[];
	
	Hardle mHardle[];
	Zombie mZombie = new Zombie();
	
	float[] mBase = new float[5];
	float[] mBack = new float[3];
	
	float mScore = 0;
	int mHScore = 0;
	float mTotal =0;
	boolean addFree;
	boolean SingUpadate;
	boolean mAchiUnlock[] = new boolean[5];
	boolean isgameStart = false;
	public GameRenderer(Context context) {
		mContext = context;
		mStart = (Start) mContext;
		root = new Group(this);
		init();
	}

	void init() {
		try {
			
			mTex_Tile = new APlane[8];
			Bitmap b = LoadImgfromAsset("tile.png");
			for(int i =0;i<mTex_Tile.length;i++)
				mTex_Tile[i] = addBitmap(Bitmap.createBitmap(b, (b.getWidth()/4)*(i%4), (b.getHeight()/2)*(i/4), b.getWidth()/4, b.getHeight()/2));
			b.recycle();
			mTex_Zombie = new APlane[6];
			b = LoadImgfromAsset("zombie_sprite.png");
			for(int i =0;i<mTex_Zombie.length;i++)
				mTex_Zombie[i] = addBitmap(Bitmap.createBitmap(b, (b.getWidth()/8)*i, 0, b.getWidth()/8, b.getHeight()));
			
			
			
			b.recycle();
			mTex_Coin = new APlane[6];
			b = LoadImgfromAsset("Coins.png");
			for(int i=0;i<mTex_Coin.length;i++)
				mTex_Coin[i] = addBitmap(Bitmap.createBitmap(b, (b.getWidth()/8)*i, 0, b.getWidth()/8, b.getHeight()));
			 mTex_achi = add("Ui/achi.png");
			 mTex_ads = add("Ui/-ads.png");
			 mTex_gbox = add("Ui/gameover-box.png");
			 mTex_home = add("Ui/home.png");
			 mTex_lether = add("Ui/lether.png");
			 mTex_play = add("Ui/play-btn.png");
			 mTex_rate = add("Ui/rate.png");
			 mTex_share = add("Ui/share.png");
			 mTex_strip = add("Ui/strip.png");
			 mTex_text = add("Ui/text.png");
			 mTex_GOver = add("Ui/gameover-text.png");
			 mTex_Pause = add("Ui/gamepaused-text.png");
			 mTex_Score = add("Ui/score.png");
			
			
			
			mTex_Logo = add("logo.png");
			mTex_Base = add("tiles2.png");
			mTex_BG = add("final-bg.png");
			load_Font();
			mHardle = new Hardle[3];
			for(int i=0;i<mHardle.length;i++){
				mHardle[i] = new Hardle();
			}
			for(int i=0;i<mBase.length;i++){
				mBase[i] = -1 +i*mTex_Base.width();
			}
			for(int i=0;i<mBack.length;i++){
				mBack[i] = -1 +i*mTex_BG.width();
			}
//			gameReset();
		} catch (Exception e) {
		}

	}

	void gameReset() {
		isgameStart = false;
		for(int i=0;i<mHardle.length;i++){
			mHardle[i].set(1+i*2.5f, i);
		}
		M.SPD = -.025f;
		for(int i=0;i<mBase.length;i++){
			mBase[i] = -1 +i*mTex_Base.width();
		}
		for(int i=0;i<mBack.length;i++){
			mBack[i] = -1 +i*mTex_BG.width();
		}
		mScore =0;
		mZombie.set();
		if(ads%3==0)
			mStart.load();
		ads++; 
	}
	int ads = 0;
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
	}

	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		root.draw(gl);
		long dt = System.currentTimeMillis() - startTime;
		if (dt < 33)
			try {
				Thread.sleep(33 - dt);
			} catch (InterruptedException e) {
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


	APlane add(String ID) {
		APlane SP = null;
		Bitmap b = LoadImgfromAsset(ID);
		try {
			SP = new APlane((b.getWidth() / M.mMaxX),
					(b.getHeight() / M.mMaxY));
			SP.loadBitmap(b);// R.drawable.jay
		} catch (Exception e) {
		}
		return SP;
	}

	APlane addBitmap(Bitmap b) {
		APlane SP = null;
		try {
			SP = new APlane((b.getWidth() / M.mMaxX),
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
			System.out.println(ID+" ~~~~~~~~~~~~  "+e.getMessage());
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
	void load_Font() {
		mTex_Font = new APlane[10];
		Bitmap b = LoadImgfromAsset("Ui/font.png");
		for (int i = 0; i < mTex_Font.length; i++)
			mTex_Font[i] = addBitmap(Bitmap.createBitmap(b, i * b.getWidth()/ 16, 0, b.getWidth() / 16,b.getHeight(), null, true));
	}
}
