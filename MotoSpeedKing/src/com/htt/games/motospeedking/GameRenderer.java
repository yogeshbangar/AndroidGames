package com.htt.games.motospeedking;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.opengl.GLSurfaceView.Renderer;

public class GameRenderer implements Renderer 
{
	final Group root;
	public static Context mContext;
	public static Start mStart;
	long startTime = System.currentTimeMillis();
	ZPlane mTex_Tile[];
	ZPlane mTex_Blast[];
	ZPlane mTex_Road;
	ZPlane mTex_Driver;
	ZPlane mTex_Tree;
	ZPlane mTex_TreeS;
	
	
//	ZPlane mTex_ads;
	ZPlane mTex_BScore;
	ZPlane mTex_Font[][];
	ZPlane mTex_GOver;
	ZPlane mTex_Pause;
	ZPlane mTex_level;
	ZPlane mTex_more[];
	ZPlane mTex_music;
	ZPlane mTex_musicB;
	ZPlane mTex_NewBest;
	ZPlane mTex_play;
	ZPlane mTex_score;
	ZPlane mTex_seti;
	ZPlane mTex_settingT;
	ZPlane mTex_sound;
	ZPlane mTex_soundB;
	ZPlane mTex_soundO;
	ZPlane mTex_splash2;
	ZPlane mTex_tabL;
	ZPlane mTex_tabR;
	ZPlane mTex_Logo;
	
	
	
	
	Scan mScan[]; 
	Move mMove[];
	Driver mDriver = new Driver();
	
	float BGY[] = new float [5];
	float mScore = 0;
	
	int mHScore = 0;
	int NewBest =0;
	float mTotal = 0;
	
	boolean addFree;
	boolean SingUpadate;
	boolean mAchiUnlock[] = new boolean[5];
	boolean BackKey = false;
	public GameRenderer(Context context) 
	{
		mContext = context;
		mStart	= (Start)mContext;
		root = new Group(this);
		init();
	}

	void init() {
		try {
			Bitmap b = LoadImgfromAsset("tiles-sprite.png");
			mTex_Tile = new ZPlane[17];
			for (int i = 0; i < mTex_Tile.length-1; i++) {
				mTex_Tile[i] = addBitmap(Bitmap.createBitmap(b,
						(b.getWidth() / 8) * (i % 8),
						(b.getHeight() / 2)* (i / 8), 
						b.getWidth() / 8, b.getHeight() / 2));
			}
			b.recycle();
			mTex_Tile[16] = add("tiles-sprite2.png");
			
			b = LoadImgfromAsset("blast.png");
			mTex_Blast = new ZPlane[8];
			for (int i = 0; i < mTex_Blast.length; i++) {
				mTex_Blast[i] = addBitmap(Bitmap.createBitmap(b,
						(b.getWidth() / 4) * (i % 4),
						(b.getHeight() / 2)* (i / 4), 
						b.getWidth() / 4, b.getHeight() / 2));
			}
			b.recycle();
			
			mTex_Road = add("road.png");
			mTex_Driver = add("bk-small.png");
			mTex_TreeS = add("shadow.png");
			mTex_Tree = addRotate("tree.png");
			
			
			
//			mTex_ads = add("Ui/-ads.png");
			mTex_BScore = add("Ui/best-score-text.png");
			
			mTex_GOver = add("Ui/gameover-text.png");
			mTex_Pause = add("Ui/game-paused-text.png");
			mTex_level = add("Ui/level.png");
			
			mTex_more = new ZPlane[5];
			mTex_more[0] = add("Ui/more.png");
			mTex_more[1] = add("Ui/fb.png");
			mTex_more[2] = add("Ui/share.png");
			mTex_more[3] = add("Ui/lether.png");
			mTex_more[4] = add("Ui/achievement.png");
			
			mTex_music = add("Ui/music.png");
			mTex_musicB = add("Ui/musicbtn.png");
			mTex_NewBest = add("Ui/new-best-score.png");
			mTex_play = add("Ui/play.png");
			mTex_score = add("Ui/score-text.png");
			mTex_seti = add("Ui/setting.png");
			mTex_settingT = add("Ui/setting-text.png");
			
			mTex_sound = add("Ui/sound.png");
			mTex_soundB = add("Ui/sound-btn.png");
			mTex_soundO = add("Ui/sound-off.png");
			mTex_splash2 = add("Ui/splash2.png");
			mTex_tabL = add("Ui/tabL.png");
			mTex_tabR = add("Ui/tabR.png");
			mTex_Logo = add("logo.png");
			load_Font();
			
			mScan = new Scan[2];
			for (int i = 0; i < mScan.length; i++)
				mScan[i] = new Scan();
			
			mMove = new Move[6];
			for (int i = 0; i < mMove.length; i++)
				mMove[i] = new Move();
			gameReset(false);
		} catch (Exception e) {
		}

	}
	
	
	void gameReset(boolean isFrom)
	{
		M.n = 0;
		for(int i =0;i<mScan.length;i++)
			mScan[i].set(2.7f+i*26*mTex_Tile[i].Height());
		
		for(int i =0; i<BGY.length;i++){
			BGY[i] = -1+i*mTex_Road.Height();
		}
		mDriver.set(-.8f + 3*mTex_Tile[0].width());
		M.SPD = -.02f;
		for (int i = 0; i < mMove.length; i++)
			mMove[i].set();

		mScore = 0;
		NewBest = 0;
		if (mHScore == 0) {
			NewBest = 5;
		}
		if(isFrom){
			if(ads % 3== 0)
				mStart.load();
			ads++;
		}
	}
	int ads = 0;
	
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glClearColor(.541f, 0.67f, 0.0f, 1.0f);
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
	ZPlane add (String ID)
	{
		ZPlane SP = null;
		Bitmap b = LoadImgfromAsset(ID);
		try
		{
			SP = new ZPlane((b.getWidth()/M.mMaxX),(b.getHeight()/M.mMaxY));
			SP.loadBitmap(b);// R.drawable.jay
		}catch(Exception e){}
		return SP;
	}
	ZPlane addBitmap (Bitmap b)
	{
		ZPlane SP = null;
		try
		{
			SP = new ZPlane((b.getWidth()/M.mMaxX),(b.getHeight()/M.mMaxY));
			SP.loadBitmap(b);// R.drawable.jay
		}catch(Exception e){}
		return SP;
	}
	ZPlane addRotate(String ID)
    {
        ZPlane SP = null;
        Bitmap b = LoadImgfromAsset(ID);
        try
        {
                SP = new ZPlane((b.getWidth()/M.mMaxY),(b.getHeight()/M.mMaxY));
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
		mTex_Font = new ZPlane[2][10];
		for (int j = 0; j < mTex_Font.length; j++) {
			Bitmap b = LoadImgfromAsset("Ui/" + j + ".png");
			for (int i = 0; i < mTex_Font[j].length; i++)
				mTex_Font[j][i] = addBitmap(Bitmap.createBitmap(b,
						i * b.getWidth() / 16, 0, b.getWidth() / 16,
						b.getHeight(), null, true));
		}
	}
}
