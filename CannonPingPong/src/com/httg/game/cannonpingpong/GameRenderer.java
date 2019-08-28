package com.httg.game.cannonpingpong;

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
	
	
	
	SimplePlane[] mTex_Font,mTex_Tree,mTex_Blast,mTex_Wheel,mTex_Particle;
	SimplePlane mTex_Logo,mTex_Sky,mTex_Clud,mTex_BG,mTex_Base,mTex_Popup,mTex_Retry;
	SimplePlane mTex_Play,mTex_Music,mTex_Sound,mTex_Achiv,mTex_Share,mTex_Cross;
	SimplePlane mTex_More,mTex_Leadr,mTex_Menu,mTex_pad,mTex_Ball,mTex_Top,mTex_Smock;
	SimplePlane mTex_Cart,mTex_Hand,mTex_Pause,mTex_Name;
	
	boolean addFree = false, SingUpadate = false;
	boolean mAchiUnlock[] = new boolean[5];
	
	int mTotal = 0;
	int mScore = 0, mHScore = 0;

	final float spd = -.01f;
	float topx;
	float mBG[], mTree[], mBase[];

	Claude mClude[];
	Pad mPad[];
	Ball mBall;
	Particle mParticle[];
	Animation mAnim[];
	public GameRenderer(Context context) 
	{
		mContext = context;
		mStart	= (Start)mContext;
		root = new Group(this);
		init();
	}

	void init() {
		try {
			
			mTex_Name = add("game_name_01.png");
			mTex_Tree = new SimplePlane[2];
			mTex_Tree[0] = add("tree_01.png");
			mTex_Tree[1] = add("tree_02.png");
			Bitmap b = LoadImgfromAsset("effect.png");
			mTex_Blast = new SimplePlane[6];
			for(int i =0;i<mTex_Blast.length;i++)
				mTex_Blast[i] = addBitmap(Bitmap.createBitmap(b, i*(b.getWidth()/8), 0, b.getWidth()/8, b.getHeight()));
			
			b.recycle();
			mTex_Wheel = new SimplePlane[2];
			b = LoadImgfromAsset("wheel_03.png");
			for(int i =0;i<mTex_Wheel.length;i++)
				mTex_Wheel[i] = addBitmap(Bitmap.createBitmap(b, i*(b.getWidth()/2), 0, b.getWidth()/2, b.getHeight()));
			
			mTex_Particle = new SimplePlane[5];
			for(int i =0;i<mTex_Particle.length;i++)
				mTex_Particle[i] = add("box_part_0"+i+".png");
			
			
			mTex_Logo	= add("logo.png");
			mTex_Sky	= add("Sky_01.png");
			mTex_Clud	= add("cloude.png");
			mTex_BG		= add("mountain.png");
			mTex_Base	= add("base.png");
			mTex_Popup	= add("popup.png");
			
			b.recycle();
			b = LoadImgfromAsset("Ui_btn.png");
			mTex_Retry	= addBitmap(Bitmap.createBitmap(b, 0*(b.getWidth()/8), 0, b.getWidth()/8, b.getHeight()));
			mTex_Play	= addBitmap(Bitmap.createBitmap(b, 1*(b.getWidth()/8), 0, b.getWidth()/8, b.getHeight()));
			mTex_Achiv	= addBitmap(Bitmap.createBitmap(b, 2*(b.getWidth()/8), 0, b.getWidth()/8, b.getHeight()));
			mTex_Music	= addBitmap(Bitmap.createBitmap(b, 3*(b.getWidth()/8), 0, b.getWidth()/8, b.getHeight()));
			mTex_Share	= addBitmap(Bitmap.createBitmap(b, 4*(b.getWidth()/8), 0, b.getWidth()/8, b.getHeight()));
			mTex_Pause 	= addBitmap(Bitmap.createBitmap(b, 5*(b.getWidth()/8), 0, b.getWidth()/8, b.getHeight()));
			mTex_Menu	= addBitmap(Bitmap.createBitmap(b, 6*(b.getWidth()/8), 0, b.getWidth()/8, b.getHeight()));
			mTex_Sound	= addBitmap(Bitmap.createBitmap(b, 7*(b.getWidth()/8), 0, b.getWidth()/8, b.getHeight()));
			
			b.recycle();
			b = LoadImgfromAsset("Ui_btn_01.png");
			mTex_Leadr	= addBitmap(Bitmap.createBitmap(b, 0*(b.getWidth()/2), 0, b.getWidth()/2, b.getHeight()));
			mTex_More	= addBitmap(Bitmap.createBitmap(b, 1*(b.getWidth()/2), 0, b.getWidth()/2, b.getHeight()));
			
			
			mTex_Cross	= add("slash_btn.png");
			
			
			mTex_pad	= add("box_02.png");
			mTex_Ball	= addRotate("bomb_01.png");
			mTex_Top	= add("cannon.png");
			mTex_Smock	= add("smoke2.png");
			mTex_Cart	= add("cart.png");
			mTex_Hand	= add("hand.png");
			
			
			
			topx = -.9f;
			mBG = new float[3];
			mTree  = new float[8];
			mBase = new float[8];
			
			mClude = new Claude[3];
			for(int i =0;i<mClude.length;i++)
				mClude[i] = new Claude();
			
			mPad = new Pad[3];
			for(int i =0;i<mPad.length;i++)
				mPad[i] = new Pad();
			
			mBall = new Ball();
			
			mParticle = new Particle[10];
			for(int i =0;i<mParticle.length;i++)
				mParticle[i] = new Particle();
			
			mAnim = new Animation[35];
			for(int i =0;i<mAnim.length;i++)
				mAnim[i] = new Animation();
			
			load_Font();
			gameReset(false);
		} catch (Exception e) {
		}
	}
	int AdcCount =0;
	void gameReset(boolean isStart) {
		
		topx = -.9f;
		mScore = 0;
		for(int i =0;i<mBG.length;i++)
			mBG[i] = -.6f + mTex_BG.width()*i;
		
		
		for(int i =0;i<mTree.length;i++)
			mTree[i] = -1f + mTex_Base.width()*i;
		
		for(int i =0;i<mBase.length;i++)
			mBase[i] = -1f + mTex_Base.width()*i;
		
		for(int i =0;i<mClude.length;i++)
			mClude[i].set(-.6f + mTex_BG.width()*i);
			
		for(int i =0;i<mPad.length;i++)
			mPad[i].set(-2);
		
		mPad[0].set(0.9f);
		
		mBall.set(-.67f,-.2f);
		
		for(int i =0;i<mParticle.length;i++)
			mParticle[i].set(-100, -10);
		
		for(int i =0;i<mAnim.length;i++)
			mAnim[i].sz = -1;
		if(adsCount %10==1)
			mStart.load();
		if(adsCount %10==3)
			mStart.LoadSmartHandler();
	}
	int adsCount = 0;
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
		Bitmap b = LoadImgfromAsset("sprite.png");
		for(int i = 0;i<mTex_Font.length;i++)
			mTex_Font[i] = addBitmap(Bitmap.createBitmap(b, i*b.getWidth()/16, 0,b.getWidth()/16, b.getHeight(),null, true));
	}
}
