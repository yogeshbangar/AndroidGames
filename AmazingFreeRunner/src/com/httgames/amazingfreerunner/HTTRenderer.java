package com.httgames.amazingfreerunner;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;

public class HTTRenderer implements Renderer 
{
	final Group root;
	public static Context mContext;
	public static Start mStart;
	long startTime = System.currentTimeMillis();
	APlane mTex_Boy[];
	APlane mTex_Logo;
	APlane mTex_Achiv;
	APlane mTex_Font[];
	APlane mTex_gameover;
	APlane mTex_paused;
	APlane mTex_help;
	APlane mTex_home;
	APlane mTex_leder;
	APlane mTex_pausebtn;
	APlane mTex_play;
	APlane mTex_rate;
	APlane mTex_score_box;
	APlane mTex_sound;
	APlane mTex_soundoff;
	APlane mTex_text;
	APlane mTex_block;
	APlane mTex_comman;
	APlane mTex_grass;
	APlane mTex_sky;
	
	Block mBlock[];
	BPlayer mPlyer = new BPlayer();
	final float dis = .7f;
	
	int mScore =0;
	int mHScore =0;
	int mTotal =0;
	
	boolean addFree = false;
	boolean mAchiUnlock[] = new boolean[5];
	public HTTRenderer(Context context) 
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
			mTex_Logo = add("logo.png");
			mTex_Boy = new APlane[7];
			for(int i =0;i<mTex_Boy.length-1;i++){
				mTex_Boy[i] = add("boy/"+i+".png");
			}
			mTex_Boy[6] = addRotate("boy/6.png");
			
			mTex_Achiv = add("Ui/Achievement.png");
			mTex_gameover = add("Ui/gameover.png");
			mTex_paused = add("Ui/game-paused.png");
			mTex_help = add("Ui/help.png");
			mTex_home = add("Ui/home.png");
			mTex_leder = add("Ui/lether.png");
			mTex_pausebtn = add("Ui/pausebtn.png");
			mTex_play = add("Ui/play.png");
			mTex_rate = add("Ui/rate.png");
			mTex_score_box = add("Ui/score_box.png");
			mTex_sound = add("Ui/sound.png");
			mTex_soundoff = add("Ui/soundoff.png");
			mTex_text = add("Ui/text.png");
			
			
			mTex_block = add("block.png");
			mTex_comman = add("comman-bg.png");
			mTex_grass = add("grass.png");
			mTex_sky = add("sky.png");
			
			mBlock = new Block[5];
			for(int i =0;i<mBlock.length;i++){
				mBlock[i] = new Block();	
			}
			load_Font();
//			gameReset();
		}catch(Exception e){}
		
	}
	
	
	void gameReset()
	{
		for(int i =0;i<mBlock.length;i++){
			mBlock[i].start(-1+i*mTex_block.width()*.6f);	
		}
		mPlyer.set(-.7f, 0);
		mScore =0;
		C.spd = -.035f;
		ads++;
		if(ads%4==0)
			mStart.load();
	}
	int ads =0;
	
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
	APlane add (String ID)
	{
		APlane SP = null;
		Bitmap b = LoadImgfromAsset(ID);
		try
		{
			SP = new APlane((b.getWidth()/C.mMaxX),(b.getHeight()/C.mMaxY));
			SP.loadBitmap(b);// R.drawable.jay
		}catch(Exception e){}
		return SP;
	}
	APlane addBitmap (Bitmap b)
	{
		APlane SP = null;
		try
		{
			SP = new APlane((b.getWidth()/C.mMaxX),(b.getHeight()/C.mMaxY));
			SP.loadBitmap(b);// R.drawable.jay
		}catch(Exception e){}
		return SP;
	}
	APlane addRotate(String ID)
    {
        APlane SP = null;
        Bitmap b = LoadImgfromAsset(ID);
        try
        {
                SP = new APlane((b.getWidth()/C.mMaxY),(b.getHeight()/C.mMaxY));
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
		mTex_Font = new APlane[10];
		Bitmap b = LoadImgfromAsset("Ui/font.png");
		for (int i = 0; i < mTex_Font.length; i++)
			mTex_Font[i] = addBitmap(Bitmap.createBitmap(b, i * b.getWidth()/ 16, 0, b.getWidth() / 16,b.getHeight(), null, true));
	}
}
