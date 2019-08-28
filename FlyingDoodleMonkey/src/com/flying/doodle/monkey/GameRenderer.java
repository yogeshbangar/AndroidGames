package com.flying.doodle.monkey;

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
	
	SimplePlane mTex_Char[],mTex_Logo,mTex_Font[];
	SimplePlane mTex_Anim, mTex_Text, mTex_Kante,mTex_Tap,mTex_Score2;
	SimplePlane mTex_Hardle,mTex_Tree,mTex_Best,mTex_Last,mTex_Score;
	SimplePlane mTex_Gameover,mTex_Gamepaused,mTex_Play,mTex_Splash;
	SimplePlane mTex_Achiv,mTex_Leader,mTex_Rate,mTex_Sound[];
	
	Player mPlayer;
	Hardle mHardle[];
	Transprent mTrans[];
	Particle mPar[];
	
	float mKante[],mTotal=0;
	
	float mHScore,mLScore,mScore;
	
	boolean addFree = false;
	boolean SingUpadate = false;
	boolean mAchiUnlock[] = new boolean[5];
	
	public GameRenderer(Context context) 
	{
		mContext = context;
		mStart	= (Start)mContext;
		root = new Group(this);
		init();
	}

	void init() {
		try {
			mTex_Char = new SimplePlane[2];
			mTex_Char[0] = addRotate("0.png");
			mTex_Char[1] = addRotate("1.png");
			mTex_Anim = add("anim.png");
			mTex_Text = add("text.png");
			mTex_Tree = add("tree.png");
			
			mTex_Best = add("best.png");
			mTex_Last = add("last.png");
			mTex_Score = add("score.png");
			mTex_Score2 = add("score2.png");
			
			mTex_Hardle = add("cloud.png");
			mTex_Logo = add("logo.png");
			mTex_Gameover = add("gameover.png");
			mTex_Gamepaused = add("gamepasued.png");
			mTex_Play = add("play.png");
			mTex_Splash = add("splash-bg.jpg");
			mTex_Kante = add("kante.png");
			mTex_Tap = add("tap.png");
			
			mTex_Achiv = add("achievement.png");
			mTex_Leader = add("lether.png");
			mTex_Rate = add("rate.png");
			mTex_Sound = new SimplePlane[2];
			mTex_Sound[0] = add("sound.png");
			mTex_Sound[1] = add("soundoff.png");
			load_Font();
			mPlayer = new Player();
			
			mTrans = new Transprent[5];
			for(int i =0;i<mTrans.length;i++)
				mTrans[i] = new Transprent();
			
			mHardle = new Hardle[5];
			for(int i =0;i<mHardle.length;i++)
				mHardle[i] = new Hardle();
			mPar = new Particle[80];
			for(int i =0;i<mPar.length;i++)
				mPar[i] = new Particle();
			mKante = new float[3];
			gameReset();
		} catch (Exception e) {
		}

	}

	void gameReset() {
		mPlayer.set();
		M.SPD= -.01f;
		for (int i = 0; i < mHardle.length; i++)
			mHardle[i].set( 2+i*mPlayer.mDist);
		
		for(int i =0;i<mTrans.length;i++)
			mTrans[i].set(i*.5f);
		
		for (int i = 0; i < mPar.length; i++)
			mPar[i].sx = 0;
		
		for(int i =0;i<mKante.length;i++)
			mKante[i] = -1+mTex_Kante.Height()*i;
		mScore =0;
		if(ads%3==0)
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
	Bitmap LoadImgfromAsset(String ID) {
		try {
			return BitmapFactory.decodeStream(mContext.getAssets().open(ID));
		} catch (Exception e) {
			System.out.println(ID + "  ~~Error~~  " + e.getMessage());
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
	void load_Font() {
		mTex_Font = new SimplePlane[13];
		Bitmap b = LoadImgfromAsset("font.png");
		for (int i = 0; i < mTex_Font.length; i++)
			mTex_Font[i] = addBitmap(Bitmap.createBitmap(b, i * b.getWidth()/ 16, 0, b.getWidth() / 16,b.getHeight(), null, true));
	}
}
