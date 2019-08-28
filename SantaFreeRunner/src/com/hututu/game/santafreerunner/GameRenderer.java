package com.hututu.game.santafreerunner;

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
	static Context mContext;
	public static Start mStart;
	long startTime = System.currentTimeMillis();
	int resumeCounter =0;
	int mSel = 0;
	
	private Handler handler = new Handler() {public void handleMessage(Message msg) {mStart.adView.setVisibility(msg.what);}};
	private Handler handler1 = new Handler() {public void handleMessage(Message msg) {mStart.adBig.setVisibility(msg.what);}};
	private Handler handler2 = new Handler() {public void handleMessage(Message msg) {mStart.adHouse.setVisibility(msg.what);}};//AdHouse
	
	
	SimplePlane[] mTex_GBG[], mTex_Char, mTex_CPadel, mTex_FChar, mTex_HFChar, mTex_Handle, mTex_Gift, mTex_Effect;
	SimplePlane[] mTex_Particle,mTex_Smok,mTex_Font,mTex_sound;
	
	SimplePlane mTex_Logo, mTex_Rotate, mTex_about2, mTex_Cont, mTex_CBG,  mTex_fb, mTex_gp, mTex_GOver;
	SimplePlane mTex_rate, mTex_retry, mTex_score, mTex_select,mTex_SLarge, mTex_help, mTex_HS,mTex_home;
	SimplePlane mTex_setting, mTex_share, mTex_VSubmit, mTex_play, mTex_SSmall, mTex_Splash,mTex_Load;
	SimplePlane mTex_Pointer, mTex_Hightbar, mTex_Skip,mTex_8x8,mTex_info; // AdHouse
	
	Background	mBG[];
	Foreground	mFG[];
	Gift[]		mGift;
	Player 		mPlayer;
	Particle[]	mParticle;
	Smoke[]		mSmoke;
	Animation ani[];//christmasChange
	
	int mFGcount;
	int fg;
	int BG;
	int GCount;
	int mScore = 0;
	int mHScore = 0;
	int gameCont=0;
	float ay = -.4f;
	float pAni,pvy;
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
			mTex_8x8		= addRotate("star.png");
			mTex_Load		= add("loading2.png");//AdHouse
			mTex_Pointer 	= add("add/pointer.png");//AdHouse
			mTex_Hightbar 	= add("add/hightbar0.png");//AdHouse
			mTex_Skip 		= add("add/exit_icon.png");//AdHouse
			
			mTex_Splash		= add("Ui/soplashfont.png");//AdHouse
			mTex_about2		= add("Ui/about2.png");
			mTex_Cont		= add("Ui/continue.png");
			mTex_CBG		= add("Ui/controlbg.png");
			mTex_fb			= add("Ui/fb.png");
			mTex_gp			= add("Ui/g+.png");
			mTex_GOver		= add("Ui/game_over.png");
			mTex_help		= add("Ui/help.png");
			mTex_HS			= add("Ui/highscore.png");
			mTex_home		= add("Ui/home.png");
			mTex_info		= add("Ui/info.png");
			mTex_play		= add("Ui/play.png");
			mTex_rate		= add("Ui/rate.png");
			mTex_retry		= add("Ui/retry.png");
			mTex_score		= add("Ui/score.png");
			mTex_select		= add("Ui/select.png");
			mTex_SLarge		= add("Ui/selection_large.png");
			mTex_SSmall		= add("Ui/selection_small.png");
			mTex_setting	= addRotate("Ui/setting.png");
			mTex_share		= add("Ui/share.png");
			mTex_sound		= new SimplePlane[2];
			mTex_sound[0]	= add("Ui/sound_off.png");
			mTex_sound[1]	= add("Ui/sound_on.png");
			mTex_VSubmit	= add("Ui/view_submit.png");
			mTex_Logo 		= add("hututugames.png");
			mTex_GBG		= new SimplePlane[3][5];
			mTex_GBG[0][0]	= add("BG0/cloud.png");
			mTex_GBG[0][1]	= add("BG0/back_bg.png");
			mTex_GBG[0][2]	= add("BG0/ground_tyle0.png");
			mTex_GBG[0][3]	= add("BG0/joint_L.png");
			mTex_GBG[0][4]	= add("BG0/joint_R.png");
			mTex_GBG[1][0]	= add("BG1/cloud2.png");
			mTex_GBG[1][1]	= add("BG1/back_bg2.png");
			mTex_GBG[1][2]	= add("BG1/ground_tyle2.png");
			mTex_GBG[1][3]	= add("BG1/jointsnow_L.png");
			mTex_GBG[1][4]	= add("BG1/jointsnow_R.png");
			mTex_GBG[2][0]	= add("BG2/cloud3.png");
			mTex_GBG[2][1]	= add("BG2/back_bg3.png");
			mTex_GBG[2][2]	= add("BG2/ground_tyle3.png");
			mTex_GBG[2][3]	= add("BG2/joint3_L.png");
			mTex_GBG[2][4]	= add("BG2/joint3_R.png");
			mTex_Rotate		= addRotate("cha/raound.png");
			Bitmap	b		= LoadImgfromAsset("cha/run.png");
			mTex_Char		= new SimplePlane[4];
			for(int i=0;i<mTex_Char.length;i++)
				mTex_Char[i]= addBitmap(Bitmap.createBitmap(b, i*(b.getWidth()/mTex_Char.length), 0,b.getWidth()/mTex_Char.length, b.getHeight(), null, true));
			
			b.recycle();
			b				= LoadImgfromAsset("cha/scat.png");
			mTex_CPadel		= new SimplePlane[5];
			for(int i=0;i<mTex_CPadel.length;i++)
				mTex_CPadel[i]	= addBitmap(Bitmap.createBitmap(b, i*(b.getWidth()/mTex_CPadel.length), 0,b.getWidth()/mTex_CPadel.length, b.getHeight(), null, true));
			
			b.recycle();
			b				= LoadImgfromAsset("cha/fly.png");
			mTex_FChar		= new SimplePlane[2];
			for(int i=0;i<mTex_FChar.length;i++)
				mTex_FChar[i]	= addBitmap(Bitmap.createBitmap(b, i*(b.getWidth()/mTex_FChar.length), 0,b.getWidth()/mTex_FChar.length, b.getHeight(), null, true));
			
			
			b.recycle();
			b				= LoadImgfromAsset("cha/hand_free.png");
			mTex_HFChar		= new SimplePlane[2];
			for(int i=0;i<mTex_HFChar.length;i++)
				mTex_HFChar[i]	= addBitmap(Bitmap.createBitmap(b, i*(b.getWidth()/mTex_HFChar.length), 0,b.getWidth()/mTex_HFChar.length, b.getHeight(), null, true));
			
			
			b.recycle();
			b				= LoadImgfromAsset("cha/handle.png");
			mTex_Handle		= new SimplePlane[6];
			for(int i=0;i<mTex_Handle.length;i++)
				mTex_Handle[i]	= addBitmap(Bitmap.createBitmap(b, i*(b.getWidth()/mTex_Handle.length), 0,b.getWidth()/mTex_Handle.length, b.getHeight(), null, true));
			
			b.recycle();
			b				= LoadImgfromAsset("cha/smoke.png");
			mTex_Smok		= new SimplePlane[10];
			for(int i=0,j=mTex_Smok.length-1;i<mTex_Smok.length;i++,j--){
				mTex_Smok[j]	= addBitmap(Bitmap.createBitmap(b, i*(b.getWidth()/mTex_Smok.length), 0,b.getWidth()/mTex_Smok.length, b.getHeight(), null, true));
			}
			
			load_Font();
			mTex_Gift		= new SimplePlane[12];
			for(int i=0;i<mTex_Gift.length;i++)
				mTex_Gift[i]= add("gift/"+i+".png");			
			
			mTex_Effect		= new SimplePlane[2];
			mTex_Effect[0]	= addRotate("gift/object_effect0.png");
			mTex_Effect[1]	= addRotate("gift/object_effect1.png");
			
			mTex_Particle		= new SimplePlane[5];
			for(int i=0;i<mTex_Particle.length;i++)
				mTex_Particle[i] = addRotate("cha/"+i+".png");
			
			mBG				= new Background[3];
			for(int i=0;i<mBG.length;i++)
				mBG[i] 		= new Background();
			
			mFG				= new Foreground[20];
			for(int i=0;i<mFG.length;i++)
				mFG[i] = new Foreground();
			
			mGift			= new Gift[3];
			for(int i=0;i<mGift.length;i++)
				mGift[i] = new Gift();
			
			mParticle		= new Particle[5];
			for(int i=0;i<mParticle.length;i++)
				mParticle[i]= new Particle();
				
			mSmoke		= new Smoke[15];
			for(int i=0;i<mSmoke.length;i++)
				mSmoke[i]= new Smoke();
				
			mPlayer			= new Player();
			ani = new Animation[200];
			for(int i=0;i<ani.length;i++)
			{
				ani[i] = new Animation();
			}
			gameReset();
		}catch(Exception e){}
		
	}
	//development 
	
	void gameReset()
	{
		BG = M.mRand.nextInt(3);
		for(int i=0;i<mBG.length;i++)
			mBG[i].set(-1+i*mTex_GBG[BG][0].width(), -1+i*mTex_GBG[BG][0].width());
		gameCont=0;
		mFGcount = 10;
		fg= mFGcount;
		float d =0;
		for(int i=0;i<mFG.length;i++)
		{
			mFG[i].set(d-1+i*mTex_GBG[BG][2].width(), ay,0);
			if(mFGcount == fg){
				mFG[i].p = 2;
				ay = mFG[i].y = -(M.mRand.nextFloat()%.4f)-.6f;
			}
			mFGcount--;
			if(mFGcount == 0){
				mFG[i].p = 1;
				mFGcount = M.mRand.nextInt(7)+4;
				fg= mFGcount;
				d +=.5f;
			}
		}
		mPlayer.set(-.8f, .7f);
		for(int i=0;i<mGift.length;i++)
		{
			mGift[i].set(i*1f, setGift(i*1f) + mTex_GBG[BG][2].Height()*.8f, M.mRand.nextInt(12));
		}
		for(int i=0;i<mParticle.length;i++)
			mParticle[i].set(-100, -100, 0,0);
			
		for(int i=0;i<mSmoke.length;i++)
				mSmoke[i].set(-110, -100,mTex_Smok.length);
		M.Speed = -.03f;
		mScore = 0;
	}
	float setGift(float x)
	{
		for(int j=0;j<mFG.length;j++){
			if(root.CircRectsOverlap(mFG[j].x,mFG[j].y, mTex_GBG[BG][2].width()*.5f,mTex_GBG[BG][2].Height()*.6f,
					x, mFG[j].y, .1f))
			{
				return mFG[j].y;
			}
		}
		return 0;
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
		if(mStart.adView!=null)
		{
			resumeCounter++;
			if(M.GameScreen != M.GAMEPLAY && M.GameScreen != M.GAMEADD && M.GameScreen != M.GAMELOAD)
			{
				int inv=mStart.adView.getVisibility();
				if(inv==AdView.GONE){try{handler.sendEmptyMessage(AdView.VISIBLE);}catch(Exception e){}}
			}
			else
			{
				int inv=mStart.adView.getVisibility();
				if(inv==AdView.VISIBLE){try{handler.sendEmptyMessage(AdView.GONE);}catch(Exception e){}}
			}
		}
		
		if (mStart.adBig != null) {
			if (M.GameScreen == M.GAMELOAD) {
				int inv = mStart.adBig.getVisibility();
				if (inv == AdView.GONE) {try {handler1.sendEmptyMessage(AdView.VISIBLE);} catch (Exception e) {}}
			} else {
				int inv = mStart.adBig.getVisibility();
				if (inv == AdView.VISIBLE) {try {handler1.sendEmptyMessage(AdView.GONE);} catch (Exception e) {}
				}
			}
		}
		
		/*AdHouse*/
		if (mStart.adHouse != null) {
			if (M.GameScreen == M.GAMEADD) {
				int inv = mStart.adHouse.getVisibility();
				if (inv == AdView.GONE) {try {handler2.sendEmptyMessage(AdView.VISIBLE);} catch (Exception e) {}}
			} else {
				int inv = mStart.adHouse.getVisibility();
				if (inv == AdView.VISIBLE) {try {handler2.sendEmptyMessage(AdView.GONE);} catch (Exception e) {}
				}
			}
		}
		/*AdHouse*/
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
		mTex_Font = new SimplePlane[10];
		Bitmap b = LoadImgfromAsset("fontstrip.png");
		for (int i = 0; i < mTex_Font.length; i++)
			mTex_Font[i] = addBitmap(Bitmap.createBitmap(b, i * b.getWidth()
					/ mTex_Font.length, 0, b.getWidth() / mTex_Font.length,
					b.getHeight(), null, true));
	}
}
