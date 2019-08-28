package com.hututu.games.basketballhitz;

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
	int resumeCounter =0;
	int mSel = 0;
	
	SimplePlane[] mTex_Hoop[],mTex_Ball,mTex_Shadow, mTex_Menuhoop,mTex_Circle, mTex_Icon,mTex_ITxt,mTex_MPlay,mTex_Font;
	SimplePlane mTex_Logo,mTex_Bboard,mTex_Building,mTex_BestScore,mTex_Wall,mTex_Tel,mTex_Cross,mTex_Ling, mTex_LingBar;
	SimplePlane mTex_BG,mTex_Title,mTex_PlayIcn,mTex_Arow,mTex_menubox, mTex_SmallBox, mTex_Board, mTex_Abuot, mTex_Back;
	SimplePlane mTex_SBack,mTex_GScore,mTex_GBest,mTex_Balls,mTex_Time, mTEx_GameOver, mTex_Menu, mTex_Retry, mTex_Pause;
	SimplePlane mTex_Target[],mTex_Join,mTex_joinboard,mTex_Later,mTex_Now,mTex_Tup[],mTex_blast,mTex_combo ,mTex_Ground;
	SimplePlane mTex_Ads,mTex_Playtxt,mTex_Blast[];
	
	
	
	Ball mBall;
	Animation mTimeAni;
	Animation mComboAni;
	Animation mAni[];
	MainActivity mMainActivity;
	
	boolean addFree,SingUpadate;
	boolean Achi[] = new boolean[5];
	 
	int mScore;
	int mTScore;
	int mBest[] = new int[5];
	int ads = 0;
	long gametime ;
	
	
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
			
			mTex_Cross 		= add("cross.png");
			mTex_Ling 		= add("bar.png");
			mTex_LingBar 	= add("fill-bar.png");
			
			mTex_Blast = new SimplePlane[3];
			for (int i = 0; i < mTex_Blast.length; i++)
				mTex_Blast[i] = add("blast/" + i + ".png");
			mTex_Playtxt	= add("play_splash.png");
			mTex_Ads		= add("ads-free-logo.png");
			mTex_blast		= add("colorblast.png");
			mTex_combo		= add("combo.png");
			mTex_Join		= add("join.png");
			mTex_joinboard	= add("joinboard-taxt.png");
			mTex_Later		= add("later.png");
			mTex_Now		= add("now.png");
			mTex_BestScore	= add("best-score.png");
			mTex_Target		= new SimplePlane[2];
			mTex_Target[0]	= add("disk.png");
			mTex_Target[1]	= add("disk2.png");
			mTex_Pause		= add("gamepaused-taxt.png");
			mTex_Menu		= add("menuicon.png");
			mTex_Retry		= add("retryicon.png");
			mTEx_GameOver	= add("gameovertaxt.png");
			mTex_Balls		= add("balls.png");
			mTex_Time		= add("gameplay_time.png");
			mTex_GScore		= add("gameplay_score.png");
			mTex_GBest		= add("gameplay_bestscore.png");
			mTex_SBack		= add("level_box.png");
			mTex_Back		= add("back.png");
			mTex_Abuot		= add("about_us.png");
			mTex_Board		= add("board.png");
			mTex_Arow		= add("arow.png");
			mTex_Circle		= new SimplePlane[2];
			mTex_Circle[0]	= add("arowcircle.png");
			mTex_Circle[1]	= add("arowcircle-dselect.png");
			mTex_menubox	= add("menubox.png");
			mTex_Menuhoop	= new SimplePlane[2];
			mTex_Menuhoop[0]= add("menuhoop.png");
			mTex_Menuhoop[1]= addBitmap(FlipHorizontal(mTex_Menuhoop[0].getBitmap()));
			
			mTex_SmallBox	= add("small-box.png");
			
			mTex_Icon		= new SimplePlane[7];
			mTex_Icon[0]	= add("leadericon.png");
			mTex_Icon[1]	= add("reward_icon.png");
			mTex_Icon[2]	= add("likeicon.png");
			mTex_Icon[3]	= add("moreicon.png");
			mTex_Icon[4]	= add("abouticon.png");
			mTex_Icon[5]	= add("soundicon-on.png");
			mTex_Icon[6]	= add("soundiconoff.png");
			
			mTex_ITxt		= new SimplePlane[6];
			mTex_ITxt[0]	= add("scoreboard.png");
			mTex_ITxt[1]	= add("reward.png");
			mTex_ITxt[2]	= add("like.png");
			mTex_ITxt[3]	= add("more.png");
			mTex_ITxt[4]	= add("about.png");
			mTex_ITxt[5]	= add("sound.png");
			
			mTex_MPlay		= new SimplePlane[5];
			mTex_MPlay[0]	= add("time.png");
			mTex_MPlay[1]	= add("arcade.png");
			mTex_MPlay[2]	= add("turnament.png");
			mTex_MPlay[3]	= add("2-ball-shoot.png");
			mTex_MPlay[4]	= add("target-shoot.png");
			
			
			
			
			
			mTex_BG			= add("splash.png");
			mTex_Title		= add("splash_font.png");
			mTex_PlayIcn	= add("playicon.png");
			mTex_Tel		= add("smoke2.png");
			mTex_Logo		= add("hututugames.png");
			mTex_Bboard		= add("backboard.png");
			mTex_Building	= add("building.png");
			mTex_Ground		= add("ground.png");
			mTex_Wall		= add("wall.png");
			mTex_Hoop		= new SimplePlane[2][];
			mTex_Hoop[0]	= new SimplePlane[4];
			mTex_Hoop[1]	= new SimplePlane[4];
			
			for(int i=0;i<mTex_Hoop[0].length;i++)
			{
				mTex_Hoop[0][i]	= add("hoop/back"+i+".png");
				mTex_Hoop[1][i]	= add("hoop/front"+i+".png");
			}
			
			mTex_Ball		= new SimplePlane[12];
			Bitmap bit		= LoadImgfromAsset("hoop/ball0.png");
			for(int i=0;i<mTex_Ball.length;i++)
				mTex_Ball[i]	= addBitmap(Bitmap.createBitmap(bit, (bit.getWidth()/4)*(i%4), (bit.getHeight()/4)*(i/4),bit.getWidth()/4, bit.getHeight()/4, null, true));
			
			mTex_Shadow		= new SimplePlane[2];
			mTex_Shadow[0]	= add("shadow.png");
			mTex_Shadow[1]	= add("shadow_still.png");
			
			mTex_Tup		= new SimplePlane[3];
			mTex_Tup[0]		= add("2x0.png");
			mTex_Tup[1]		= add("3x0.png");
			mTex_Tup[2]		= add("5x0.png");
			
			
			mBall = new Ball();
			
			mAni	= new Animation[20];
			for(int i=0;i<mAni.length;i++)
			{
				mAni[i] = new Animation();
			}
			mTimeAni	= new Animation();
			mComboAni	= new Animation();
			load_Font();
//			gameReset(0);
		}catch(Exception e){}
		
	}
	
	
	void gameReset(int _type)
	{
		mBall.type = _type;
		mScore =0;
		gametime = System.currentTimeMillis();
		mBall.reset();
		for(int i=0;i<mAni.length;i++)
		{
			mAni[i].reset();
		}
		mBall.Drap = 0;
		mBall.combo = 0;
		switch (mBall.type) {
		case 1:
			mBall.mBalls = 12;
			break;
		case 3:
			mBall.mBalls = 1;
			break;
		case 4:
			mBall.mBalls = 12;
			mBall.setTarget();
			break;
		}
		if (ads % 1 == 0) {
			try {
				adsHandler.sendEmptyMessage(0);
			} catch (Exception e) {
			}
		}
		mTimeAni.set(0, 1000);
		mComboAni.set(0, 1000);
	}
	Handler adsHandler = new Handler() {
		public void handleMessage(Message msg) {
			mStart.load();
		}
	};
	
	
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
		mTex_Font = new SimplePlane[12];
		Bitmap b = LoadImgfromAsset("fontstrip.png");
		for (int i = 0; i < mTex_Font.length; i++)
			mTex_Font[i] = addBitmap(Bitmap.createBitmap(b, i * b.getWidth() / 16, 0, b.getWidth() / 16, b.getHeight(), null, true));
		
	}
}
