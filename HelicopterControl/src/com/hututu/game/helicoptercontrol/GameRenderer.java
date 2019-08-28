package com.hututu.game.helicoptercontrol;

import java.util.Random;

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
	Context mContext;
	Random mRand = new Random();
	public static Start mStart;
	public player mPlayer,mBg1[],mBg2[],mBg3[],mBg4[],mBg5[],mBgtop[],mObstacle[],mSmoke[],mCoin[],mCloud[];
	public StarAnimation mParticle[];
	int resumeCounter 	= 0,GameCount;
	int HighScore=0,mScore=0,StopCnt=10,NoSmoke,timeCnt=0;
	int mSel= 0,BlastCnt=100;
	float blastX,blastY,ScalVal=.35f;
	long startTime = System.currentTimeMillis();
	SimplePlane[] mTex_Font,mTex_Blast,mTex_Coin;
	SimplePlane mTex_Logo,mTex_Smoke,mTex_Copter[],mTex_Particle[];
	SimplePlane mTex_Splash,mTex_Strip,mTex_Bg[],mTex_obstacle,mTexPauseBtn,mTex_GameScore,mTex_Grass,mTex_Cloud;
	SimplePlane mTex_AbtUsBtn,mTex_NewGameBtn,mTex_HelpBtn,mTex_HighScoreBtn,mTex_backBtn,mTex_RateUsBtn,mTex_ShareBtn,mTex_SoundBtn[],mTex_Exit;
	SimplePlane mTex_Selector,mTex_SmallSel,mTex_PopUp,mTex_HelpTxt,mTex_HelpTitle,mTex_AbtUsTxt,mTex_AbtTitle,mTex_GameOverTitle,mTex_GamePauseTitle;
	SimplePlane mTex_UIbg,mTex_NewScore,mTex_BestScore,mTex_ScoreBox,mTex_HighScoreTitle,mTex_HututTitle,mTex_MenuBtn,mTex_Continue,mTex_Replay,mTex_Submitt[];
	SimplePlane   mTex_Pointer, mTex_Hightbar,mTex_Skip; //AdHouse
	private Handler handler = new Handler() {public void handleMessage(Message msg) {mStart.adView.setVisibility(msg.what);}};
	private Handler handler1 = new Handler() {public void handleMessage(Message msg) {mStart.adView2.setVisibility(msg.what);}};
	private Handler handler2 = new Handler() {public void handleMessage(Message msg) {mStart.adHouse.setVisibility(msg.what);}};//AdHouse
	public GameRenderer(Context context) 
	{
		mContext = context;
		mStart	= (Start)mContext;
		root = new Group(this);
		init();
		M.GameScreen = M.GAMELOGO;
	}
	void init()
	{
		int i=0;
		try
		{
			mTex_Pointer = add("pointer.png");//AdHouse
			mTex_Hightbar = add("hightbar0.png");//AdHouse
			mTex_Skip = add("exit_icon.png");//AdHouse

			load_UI();
			mTex_Logo 			= add("hututugames.png");
			mTex_Splash			= add("splash.jpg");
			mTex_Strip          = add("strip.jpg");
			mTex_Cloud          = add("cloud.png");
			mTex_Bg             = new SimplePlane[5];
			for(i=0;i<mTex_Bg.length;i++)
		    mTex_Bg[i]          = add("bg"+i+".png");
			mTex_Grass          = add("grass.png");
		    mTex_Smoke          = add("smoke.png");
            mTexPauseBtn        = add("pause.png");
            mTex_GameScore      = add("score_text.png");
            mTex_Particle       = new SimplePlane[4];
            for(i=0;i<mTex_Particle.length;i++)
              mTex_Particle[i] = addRotate("copter/partical"+i+".png");
            mTex_Copter         = new SimplePlane[2];
            for(i=0;i<mTex_Copter.length;i++)
            mTex_Copter[i]      = addRotate("copter/heli"+i+".png");
            Bitmap b            = LoadImgfromAsset("blast.png");
			mTex_Blast = new SimplePlane[13];
			for(i = 0;i<mTex_Blast.length;i++)
				mTex_Blast[i] = addBitmap(Bitmap.createBitmap(b, i*b.getWidth()/mTex_Blast.length, 0,b.getWidth()/mTex_Blast.length, b.getHeight(),null, true));
			
			mTex_Coin  = new SimplePlane[10];
			b = LoadImgfromAsset("coin.png");
			for(i = 0;i<mTex_Coin.length;i++)
				mTex_Coin[i] = addBitmap(Bitmap.createBitmap(b, i*b.getWidth()/mTex_Coin.length, 0,b.getWidth()/mTex_Coin.length, b.getHeight(),null, true));
			
			mTex_obstacle      = add("obstacle.png");   
			load_Font();
			mPlayer  = new player();
			mBg1     = new player[2];
			mBg2     = new player[2];
			mBg3     = new player[2];
			mBg4     = new player[2];
			mBg5     = new player[2];
			mBgtop   = new player[2];
			mCloud   = new player[15];
			for(i=0;i<mBg1.length;i++)
			{
				mBg1[i] = new player();
				mBg2[i] = new player();
				mBg3[i] = new player();
				mBg4[i] = new player();
				mBg5[i] = new player();
				mBgtop[i] = new player();
			}
			for(i=0;i<mCloud.length;i++)
				mCloud[i] = new player();
			mObstacle = new player[3];
			mCoin     = new player[10];  
			for(i=0;i<mObstacle.length;i++)
			 {
			   mObstacle[i] = new player();
			   mObstacle[i].setObject(-100,-100,0);
			 }
			 for(i=0;i<mCoin.length;i++)
			 {
			   mCoin[i] = new player();
			   mCoin[i].setObject(-100,-100,0);
			 }
			 mParticle = new StarAnimation[5];
			 for(i=0;i<mParticle.length;i++)
			   mParticle[i] = new StarAnimation();
		     mSmoke  = new player[20];
		     for(i=0;i<mSmoke.length;i++)
	    	  mSmoke[i] = new player();
		     gameReset();
		     M.loadSound(mContext);
		}catch(Exception e){}
		
	}
	
	void load_UI()
	{
		mTex_UIbg            = add("uibg.jpg");
		mTex_AbtUsBtn        = add("About_button.png");
		mTex_NewGameBtn      = add("New_button.png");
		mTex_HelpBtn         = add("help_button.png");
		mTex_HighScoreBtn    = add("HighScore_button.png");
		mTex_Exit            = add("exit.png");
		mTex_backBtn         = add("back.png");
		mTex_RateUsBtn       = add("rateus.png");
		mTex_ShareBtn        = add("share.png");
		mTex_SoundBtn        = new SimplePlane[2]; 
		mTex_SoundBtn[0]     = add("soundon.png");
		mTex_SoundBtn[1]     = add("soundoff.png");
		mTex_Selector        = add("bigselection.png");
		mTex_SmallSel        = add("smallselection.png");
		mTex_PopUp           = add("bigpopup.png");
		mTex_HelpTxt         = add("helptext.png");
		mTex_HelpTitle       = add("help.png");
		mTex_AbtUsTxt        = add("abouttext.png");
		mTex_AbtTitle        = add("aboutus.png");
		mTex_GameOverTitle   = add("gameover.png");
		mTex_GamePauseTitle  = add("gamepaused.png");
		mTex_NewScore        = add("newscore_text.png");
		mTex_BestScore       = add("bestscore_text.png");
		mTex_ScoreBox        = add("scorebox.png");  
		mTex_HighScoreTitle  = add("HighScoreTitle.png");
		mTex_HututTitle      = add("hututu_button.png");
		mTex_MenuBtn         = add("menu.png"); 
		mTex_Continue        = add("continue.png");
		mTex_Replay          = add("retry.png");
		mTex_Submitt         = new SimplePlane[2];
		mTex_Submitt[0]      = add("submitde.png");
		mTex_Submitt[1]      = add("submitse.png");
	}
	void load_Font()
	{
		mTex_Font	= new SimplePlane[10];
		Bitmap b = LoadImgfromAsset("fontstrip.png");
		for(int i = 0;i<mTex_Font.length;i++)
			mTex_Font[i] = addBitmap(Bitmap.createBitmap(b, i*b.getWidth()/mTex_Font.length, 0,b.getWidth()/mTex_Font.length, b.getHeight(),null, true));
	}
	void gameReset()
	{ 
		mPlayer.set(-.4f,0,0);
		mScore=0;
		root.Score =0;
		BlastCnt=100;
		blastX =-100;blastY =-100;
		GameCount= 0;
		timeCnt  = 0;
		ScalVal=.35f;
		for(int i=0;i<mBg1.length;i++)
		{
		  mBg1[i].setObject(0+i*mTex_Bg[0].width(),0,M.BGSPEED/6);
		  mBg2[i].setObject(0+i*mTex_Bg[1].width(),0,M.BGSPEED/4);
		  mBg3[i].setObject(0+i*mTex_Bg[2].width(),0,M.BGSPEED/3);
		  mBg4[i].setObject(0+i*mTex_Bg[3].width(),0,M.BGSPEED/2);
		  mBg5[i].setObject(0+i*mTex_Bg[4].width(),0,M.BGSPEED);
		  mBgtop[i].setObject(0+i*mTex_Grass.width(),0,3*M.BGSPEED/2);
		}
		for(int i=0;i<mCloud.length;i++)
		  mCloud[i].setObject(-1+mTex_Cloud.width()/2+i*mTex_Cloud.width()/2,1-mTex_Cloud.Height()/2,3*M.BGSPEED/2);		 
		for(int i=0;i<mObstacle.length;i++)
		  mObstacle[i].setObject(1.2f+i*.8f,randomRange(-.6f,.6f),M.BGSPEED);
		coinPattern1(1.4f,10);
		NoSmoke =0;
	    StopCnt =10;
		for(int i=0;i<mSmoke.length;i++)
	    	mSmoke[i].SetSmoke(-100,-100,.35f,1,0);
	}
	void coinPattern1(float x,int no)
	{
		float y =randomRange(-.4f,.4f);
		for(int i=0;i<no;i++)
  		  mCoin[i].setObject(x+i*.25f,y,M.BGSPEED);
	}
	void coinPattern2(float x,int no)
	{
		float y =randomRange(-.2f,.2f);
		for(int i=0;i<no/2;i++)
  		  mCoin[i].setObject(x+i*.35f,y,M.BGSPEED);
		for(int i=no/2;i<no;i++)
		 mCoin[i].setObject(x+(i-(no/2))*.35f,y+.2f,M.BGSPEED);
		
	}
	void coinPattern3(float x,int no)
	{
		float y =0;
		for(int i=0;i<no;i++)
  		   mCoin[i].setObject(x+i*.35f,i<(no/2)?y:y+.2f,M.BGSPEED);
		
	}
	void coinPattern4(float x,int no)
	{
		float dx=.2f;
		float y =randomRange(-.4f,.35f);
		mCoin[0].setObject(x+0*dx,y,M.BGSPEED);
		mCoin[1].setObject(x+1*dx,y+.1f,M.BGSPEED);
		mCoin[2].setObject(x+2*dx,y+.2f,M.BGSPEED);
		mCoin[3].setObject(x+3*dx,y+.27f,M.BGSPEED);
		mCoin[4].setObject(x+4*dx,y+.35f,M.BGSPEED);
		mCoin[5].setObject(x+5*dx,y+.36f,M.BGSPEED);
		mCoin[6].setObject(x+6*dx,y+.27f,M.BGSPEED);
		mCoin[7].setObject(x+7*dx,y+.2f,M.BGSPEED);
		mCoin[8].setObject(x+8*dx,y+.1f,M.BGSPEED);
		mCoin[9].setObject(x+9*dx,y,M.BGSPEED);
	}
	void coinPattern5(float x,int no)
	{
		float dx=.2f;
		float y =randomRange(-.4f,.35f);
		for(int i=0;i<no;i++)
		{
		  if(i>=0 && i<4)
	 	   mCoin[i].setObject(x+i*dx,y,M.BGSPEED);
		  if(i>=4 && i<7)
			 mCoin[i].setObject(x+i*dx,y+.3f,M.BGSPEED);
		  if(i>=7 && i<no)
 		   mCoin[i].setObject(x+i*dx,y,M.BGSPEED);
		}
	}
	void coinPattern6(float x,int no)
	{
		
		float dx=.2f;
		for(int i=0;i<no;i++)
		  mCoin[i].setObject(x+i*dx,randomRange(-.4f,.35f),M.BGSPEED);
		
	}
	public float randomRange(float min,float max)
	{
		float rand = mRand.nextFloat();
		max = max-min;
		max  = rand%max;
		return (max+min);
	}
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
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
		if(mStart.adView!=null){
			if(resumeCounter>50){
				if (M.GameScreen != M.GAMESPLASH && M.GameScreen != M.GAMEMENU
						&& M.GameScreen != M.GAMEPLAY
						&& M.GameScreen != M.GAMEADD) {
					int inv = mStart.adView.getVisibility();
					if (inv == AdView.VISIBLE) {
						try {
							handler1.sendEmptyMessage(AdView.GONE);
						} catch (Exception e) {
						}
					}
					if (inv == AdView.GONE) {
						try {
							handler.sendEmptyMessage(AdView.VISIBLE);
						} catch (Exception e) {
						}
					}
				} else {
					int inv = mStart.adView2.getVisibility();
					if (inv == AdView.VISIBLE) {
						try {
							handler.sendEmptyMessage(AdView.GONE);
						} catch (Exception e) {
						}
					}
					if (inv == AdView.GONE) {
						try {
							handler1.sendEmptyMessage(AdView.VISIBLE);
						} catch (Exception e) {
						}
					}
				}
			}
			else
			{
				int inv=mStart.adView.getVisibility();
				if(inv==AdView.VISIBLE){try{handler.sendEmptyMessage(AdView.GONE);}catch(Exception e){}}
				if(inv==AdView.VISIBLE){try{handler1.sendEmptyMessage(AdView.GONE);}catch(Exception e){}}
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
		resumeCounter++;
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
	SimplePlane addBitmapRotet(Bitmap b)
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
	
}

