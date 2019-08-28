package com.oneday.games24.zombiesnak;

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

public class GameRenderer implements Renderer 
{
	final Group root;
	public static Context mContext;
	public static Start mStart;
	long startTime = System.currentTimeMillis();
	
	SimplePlane mTex_Ball[];
	SimplePlane mTex_Logo;
	SimplePlane mTex_Arrow;
	SimplePlane mTex_Font[];
	
	SimplePlane mTex_Achiv;
	SimplePlane mTex_CBG;
	SimplePlane mTex_GOver;
	SimplePlane mTex_Pause;
	SimplePlane mTex_Help;
	SimplePlane mTex_Home;
	SimplePlane mTex_Leder;
	SimplePlane mTex_Play;
	SimplePlane mTex_Rate;
	SimplePlane mTex_SBox;
	SimplePlane mTex_Sound;
	SimplePlane mTex_SOff;
	SimplePlane mTex_Text;
	SimplePlane mTex_Sel;
	SimplePlane mTex_Rand;
	SimplePlane mTex_Player;
	
	
	Follower mFollower[];
	Ball mBall = new Ball();
	Line mLine[];
	
	int mScore;
	int mHScore;
	int mTotal;
	boolean addFree = false;
	boolean SingUpadate = false;
	boolean mAchiUnlock[] = new boolean[5];
	
	private Handler handler = new Handler() {public void handleMessage(Message msg) {mStart.adView.setVisibility(msg.what);}};
	
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
			mTex_Player = add("Ui/player_btn.png");
			mTex_Rand = add("random.png");
			mTex_Sel = addRotate("player_select.png");
			mTex_Achiv = add("Ui/Achievement.png");
			mTex_CBG = add("Ui/comman-bg.png");
			mTex_GOver = add("Ui/gameover.png");
			mTex_Pause = add("Ui/game-paused.png");
			mTex_Help = add("Ui/help.png");
			mTex_Home = add("Ui/home.png");
			mTex_Leder = add("Ui/lether.png");
			mTex_Play = add("Ui/play.png");
			mTex_Rate = add("Ui/rate.png");
			mTex_SBox = add("Ui/score_box.png");
			mTex_Sound = add("Ui/sound.png");
			mTex_SOff = add("Ui/soundoff.png");
			mTex_Text = add("Ui/text.png");
			load_Font();
			
			mTex_Arrow = add("arrow.png");
			mTex_Logo = add("logo.png");
			
			mTex_Ball = new SimplePlane[15];
			for(int i =0;i<mTex_Ball.length;i++)
				mTex_Ball[i] = addRotate("monster/"+i+".png");
			
			mLine = new Line[2];
			for(int i =0;i<mLine.length;i++){
				mLine[i] = new Line(); 
			}
			mFollower = new Follower[20];
			for(int i =0;i<mFollower.length;i++){
				mFollower[i] = new Follower(); 
			}
//			gameReset();
		}catch(Exception e){}
		
	}
	
	
	void gameReset()
	{
		mBall.set(-.3f,0,M.mRand.nextInt(mTex_Ball.length));
		for(int i =0;i<mLine.length;i++){
			mLine[i].set(2+i*2); 
		}
		for(int i =0;i<mFollower.length;i++){
			mFollower[i].set(-2, 0); 
		}
		mScore = 0;
		if(ads %3==0)
			mStart.load();
		ads++;
	}
	int ads =0;
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glClearColor(0.7f, 0.7f, 0.7f, 0.7f);
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
		mTex_Font = new SimplePlane[13];
		Bitmap b = LoadImgfromAsset("Ui/fontstrip.png");
		for (int i = 0; i < mTex_Font.length; i++)
			mTex_Font[i] = addBitmap(Bitmap.createBitmap(b, i * b.getWidth()/ 16, 0, b.getWidth() / 16,b.getHeight(), null, true));
	}
}
