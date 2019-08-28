package com.hututu.game.bubbleboomblast;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.google.android.gms.ads.AdView;

import android.content.Context;
//import android.content.pm.PackageManager;
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
	
	
	SimplePlane[] mTex_Font, mTex_Ball,mTex_bg,mTex_Cannon,mTex_Sound,mTex_Dot,mTex_Star,mTex_Fire,mTex_Cef;
	SimplePlane[] mTex_Blast,mTex_tBall,mTex_Bomb;
	SimplePlane mTex_FireBall,mTex_logo,mTex_Splash,mTex_Achiev,mTex_Lboard,mTex_Play,mTex_Setting,mTex_Lok;
	SimplePlane mTex_About,mTex_Board,mTex_fb,mTex_gp,mTex_Help,mTex_Twit,mTex_Back,mTex_AboutTex,mTex_LIcn;
	SimplePlane mTex_MArrow,mTex_Cong,mTex_Gameover,mTex_Paused, mTex_Leaderboard,mTex_Levelclear,mTex_Menu;
	SimplePlane mTex_Next,mTex_PlayIcn,mTex_Retry,mTex_$,mTex_x,mTex_Cross,mTex_Ling,mTex_LingBar,mTex_Puse;
	SimplePlane mTex_Score,mTex_Arrow,mTex_BStone,mTex_Spiral,mTex_BarBomb,mTex_SplashT,mTex_Poped,mTex_Ter;
	SimplePlane mTex_SocrBar,mTex_helpscr,mTex_Board2,mTex_NexLvl,mTxt_Target,mTex_Finger;
	
	boolean addFree = false;
	boolean SingUpadate = false;
	boolean Achi[] = new boolean[6];
	boolean isReady = true;
	boolean ShowHelp = false;
	
	int Leave;
	int mul;
	
	int mPage = 0;
	int mScore = 0;
	int mHScore = 0;
	int mLevel = 0;
	int mULevel = 1;
	int mrBomb;
	int mPopedBall;
	int mTargetBall;
	int BestScore[] = new int[60];
	int NumberOfBall;
	int mTotalBall = 10;
	int BlastCount = 10;
	int fireCount = 100;
	
	float svx =0;
	
	Arry mArr[][];
	Ball mBall[];
	Blast mBlast[];
	Blast mB11[];
	Arrow mArrow[];
	Arrow mBomb[];
	
	MainActivity mMainActivity;
	
	private Handler handler = new Handler() {public void handleMessage(Message msg) {mStart.adView.setVisibility(msg.what);}};
	private Handler handler2 = new Handler() {public void handleMessage(Message msg) {mStart.adHouse.setVisibility(msg.what);}};//AdHouse
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
			mMainActivity = new MainActivity();
			mMainActivity.onCreate();
			
			mTex_Finger		= add("finger.png");
			mTex_Board2		= add("unlock-level-taxt.png");
			mTex_NexLvl		= add("next-level.png");
			mTxt_Target		= add("target-complete.png");	
			mTex_helpscr	= add("help-board.png");
			
			mTex_BarBomb	= add("bombar.png");
			mTex_Lok 		= add("lock.png");
			
			mTex_Cross 		= add("cross.png");
			mTex_Ling 		= add("bar.png");
			mTex_LingBar 	= add("fill-bar.png");
			
			mTex_Arrow		= addRotate("arow.png");
			mTex_Score		= add("level-clear-score.png");
			mTex_Cong		= add("congretulation.png");
			mTex_Gameover	= add("gameover.png");
			mTex_Paused		= add("game-paused.png");
			mTex_Leaderboard= add("leaderboard.png");
			mTex_Levelclear	= add("level-clear.png");
			mTex_Menu		= add("menu.png");
			mTex_Next		= add("next.png");
			mTex_PlayIcn	= add("play-icon.png");
			mTex_Retry		= add("retry.png");
			mTex_Star		= new SimplePlane[2];
			mTex_Star[0]	= add("deselect-star.png");
			mTex_Star[1]	= add("select-satar.png");
			
			mTex_MArrow		= add("arrow.png");
			mTex_Dot		= new SimplePlane[2];
			mTex_Dot[0]		= add("deselect-dot.png");
			mTex_Dot[1]		= add("select-dot.png");
			mTex_LIcn		= add("level-icon.png");
			mTex_AboutTex	= add("about-text.png");
			mTex_Back		= add("back.png");
			mTex_About		= add("About.png");
			mTex_Board		= add("board.png");
			mTex_fb			= add("facebook.png");
			mTex_gp			= add("G+.png");
			mTex_Help		= add("help.png");
			mTex_Sound		= new SimplePlane[2];
			mTex_Sound[0]	= add("sound-off.png");
			mTex_Sound[1]	= add("sound-on.png");
			mTex_Twit		= add("twetter.png");
			
			
			mTex_Achiev		= add("achievement.png");
			mTex_Lboard		= add("leader-board-btn.png");
			mTex_Play		= add("Play.png");
			mTex_Setting	= add("setting.png");
			mTex_Splash		= add("splash.jpg");
			mTex_SplashT	= add("splash_font.png");
			mTex_Puse		= add("paused.png");
			mTex_Poped		= add("popped.png");
			mTex_Ter		= add("target.png");
			mTex_SocrBar	= add("score-bar.png");
			
			
			mTex_Spiral		= addRotate("level/spiral.png");
			mTex_BStone		= add("level/6_0.png");
			
			mTex_Ball = new SimplePlane[12];
			for (int i = 0; i < mTex_Ball.length; i++)
				mTex_Ball[i] = add("level/" + (i + 1) + ".png");
			
			mTex_tBall		= new SimplePlane[7];
			for(int i=0;i<mTex_tBall.length;i++)
				mTex_tBall[i]= add("level/1/"+(i)+".png");
			
			
			mTex_Bomb		= new SimplePlane[8];
			Bitmap b 		= LoadImgfromAsset("blast.png"); 
			for(int i=0;i<mTex_Bomb.length;i++)
				mTex_Bomb[i]= addBitmap(Bitmap.createBitmap(b, i * b.getWidth()/ mTex_Bomb.length, 0, b.getWidth() / mTex_Bomb.length,b.getHeight(), null, true));
			
			
			
			mTex_bg			= new SimplePlane[4];
			for(int i=0;i<mTex_bg.length;i++)
				mTex_bg[i]		= add("bg/"+i+".png");

			mTex_Cannon		= new SimplePlane[3];
			mTex_Cannon[0]	= addRotate("cannon.png");
			mTex_Cannon[1]	= add("cannon_backside.png");
			mTex_Cannon[2]	= add("cannon_frontside.png");
			
			mTex_FireBall	= add("fire_ball.png");
			mTex_logo		= add("hututugames.png");
			
			
			mTex_Fire		= new SimplePlane[21];
			for(int i=0;i<mTex_Fire.length;i++)
				mTex_Fire[i]= add("ani_fire/"+i+".png");
			
			mTex_Cef		= new SimplePlane[6];
			for(int i=0;i<mTex_Cef.length;i++)
				mTex_Cef[i]= add("ani_cannon/"+i+".png");
			
			mTex_Blast		= new SimplePlane[17];
			for(int i=0;i<mTex_Blast.length;i++)
				mTex_Blast[i]= add("blast/"+i+".png");
			
			
//			mTex_Blast		= new SimplePlane[11];
//			for(int i=0;i<mTex_Blast.length;i++)
//				mTex_Blast[i]= add("Ani_Blast ball/"+i+".png");
			
			load_Font();
			
			
			mArr			= new Arry[Level.Level[0].length][Level.Level[0][0].length];
			for(byte i = 0;i<mArr.length;i++)
			{
				mArr[i] = new Arry[Level.Level[0][0].length];
				for(byte j = 0;j<mArr[0].length;j++){
					mArr[i][j] = new Arry();
				}
			}
			mBall = new Ball[3];
			for(int i=0;i<mBall.length;i++)
				mBall[i] = new Ball();
			
			mBlast= new Blast[10];
			for(int i=0;i<mBlast.length;i++)
			{
				mBlast[i] = new Blast();
			}
			
			mB11= new Blast[10];
			for(int i=0;i<mB11.length;i++)
			{
				mB11[i] = new Blast();
			}
			
			mArrow= new Arrow[100];
			for(int i=0;i<mArrow.length;i++)
			{
				mArrow[i] = new Arrow();
			}
			
			mBomb= new Arrow[5];
			for(int i=0;i<mBomb.length;i++)
			{
				mBomb[i] = new Arrow();
			}
			
//			gameReset();
		}catch(Exception e){}
		
	}
	int adsCount=0;
//	int gameCounter = 0;
	void gameReset()
	{
		mrBomb =20;
		mScore =0;
		NumberOfBall =mTotalBall;
		System.out.println(mLevel);
		for(int i=0;i<mBall.length;i++){
			mBall[i].ang = (float)Math.toRadians(-50);
			mBall[i].set((root.cx- (float)Math.sin(mBall[i].ang)*.42f), (root.cy+ (float)Math.cos(mBall[i].ang)*.70f), 0,0);
			mBall[i].set(-100,-100, 0,0);
			mBall[i].ang = -50;
		}
		Leave = 4;
		mul = 1;
		mPopedBall = 0;
		mTargetBall = 0;
		for(byte i =0;i<mArr.length;i++)
		{
			for(byte j =0;j<mArr[i].length;j++)
			{
				mArr[i][j].set(Level.Level[mLevel][i][j], (byte)M.mRand.nextInt(mTex_tBall.length));
				if(Level.Level[mLevel][i][j]==1)
				{
					mTargetBall++;
				}
			}
		}
		mTargetBall = (mTargetBall*70)/100;
		for(int i=0;i<mBlast.length;i++)
		{
			mBlast[i].set(0, 0, 100);
		}
		for(int i=0;i<mB11.length;i++)
		{
			mB11[i].set(-100, -100, 100);
		}
		for(int i=0;i<mArrow.length;i++)
		{
			mArrow[i].set(-100, -100, (byte)0);
		}
		
		for(int i=0;i<mBomb.length;i++)
		{
			mBomb[i].set(-100, -100, (byte)-1);
		}
//		gameCounter =0;
		ShowHelp = false;
		svx =-.3f;
		adsCount++;
		if(adsCount%2==0)
			{try {handler5.sendEmptyMessage(AdView.VISIBLE);} catch (Exception e) {}}
	}
	private Handler handler5 = new Handler() {public void handleMessage(Message msg) {GameRenderer.mStart.loadInter();}};
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
			if(M.GameScreen != M.GAMEPLAY && !addFree &&  M.GameScreen != M.GAMEADD)
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
		/*AdHouse*/
		if (mStart.adHouse != null) {
			if (M.GameScreen == M.GAMEADD && !addFree) {
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
	void load_Font() 
	{
		mTex_Font = new SimplePlane[10];
		Bitmap b = LoadImgfromAsset("fontstrip.png");
		for (int i = 0; i < mTex_Font.length; i++)
			mTex_Font[i] = addBitmap(Bitmap.createBitmap(b, i * b.getWidth()/ 16, 0, b.getWidth() / 16,b.getHeight(), null, true));
		mTex_$= addBitmap(Bitmap.createBitmap(b, 10 * b.getWidth()/ 16, 0, b.getWidth() / 16,b.getHeight(), null, true));
		mTex_x= addBitmap(Bitmap.createBitmap(b, 11 * b.getWidth()/ 16, 0, b.getWidth() / 16,b.getHeight(), null, true));
//		mTex_Pls= addBitmap(Bitmap.createBitmap(b, 12 * b.getWidth()/ 16, 0, b.getWidth() / 16,b.getHeight(), null, true));
	}
}
