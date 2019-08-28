package com.hututu.game.bubblecandyunlimited;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.google.android.gms.ads.AdView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Handler;
import android.os.Message;

public class GameRenderer implements Renderer 
{
	final Group root;
	public static Context mContext;
	public static Start mStart;
	long startTime = System.currentTimeMillis();
	int mSel = 0;
	boolean addFree = false;
	
	SimplePlane mTex_Hututu,mTex_About,mTex_AbouTxt,mTex_Achiv,mTex_Back,mTex_BScore,mTex_btn,mTex_Congrat,mTex_fb;
	SimplePlane mTex_gp,mTex_GOver,mTex_GPaused,mTex_BG,mTex_Home,mTex_Leader,mTex_Play,mTex_Retry;
	SimplePlane mTex_Score,mTex_Splash,mTex_Popup,mTex_Join,mTex_Canan,mTex_Line,mTex_CBar,mTex_Paused;
	SimplePlane mTex_TBar,mTex_CCircle,mTex_SBall;
	
	SimplePlane[] mTex_Music,mTex_Sound,mTex_MenuTxt,mTex_BCandy[],mTex_Blast,mTex_Font,mTex_Star;
	
	
	boolean SingUpadate;
	boolean mAchiUnlock[] = new boolean[5];
	boolean playBest;
	boolean IsEndless;
	int mHScore;
	int mTScore;
	int hitBall;
	int newHit;
	int mScore;
	int win = 0;
	final float by = 0.80f;
	Player mPlayer;
	CBubble[][] mCBubble;
	RCandy[] mRCandy;
	Animation mJoin;
	Animation mAnim[];
	
	
	private Handler handler = new Handler() {public void handleMessage(Message msg) {mStart.adView.setVisibility(msg.what);}};
//	private Handler handler2 = new Handler() {public void handleMessage(Message msg) {mStart.adHouse.setVisibility(msg.what);}};//AdHouse
	public GameRenderer(Context context) 
	{
		mContext = context;
		mStart	= (Start)mContext;
		root = new Group(this);
		init();
	}
	void init() {
		try {
			mTex_SBall = add("deselect-candy.png");
			mTex_CCircle = add("canon_circle.png");
			mTex_Line = add("line.png");
			mTex_CBar = add("canon_lifebar.png");
			mTex_Paused = add("paused_icon.png");
			mTex_TBar = add("time_bar.png");
			
			mTex_Blast = new SimplePlane[3];
			for (int i = 0; i < mTex_Blast.length; i++)
				mTex_Blast[i] = add("blast/" + i + ".png");
			mTex_Canan = addRotate("canon.png");
			mTex_Join = add("join.png");
//			mTex_Reset = add("refresh_1.png");
			mTex_Hututu = add("hututugames.png");
			mTex_Popup = add("board.png");
			mTex_About = add("about.png");
			mTex_AbouTxt = add("about_us.png");
			mTex_Achiv = add("achivement_icon.png");
			mTex_Back = add("back_icon.png");
			mTex_BScore = add("bestscore.png");
			mTex_btn = add("btn.png");
			mTex_Congrat = add("congratulation.png");
			mTex_fb = add("facebook.png");
			mTex_gp = add("g+.png");
			mTex_GOver = add("gameover.png");
			mTex_GPaused = add("game_paused.png");
			mTex_BG = add("gameplay_bg.png");
			mTex_Home = add("home_icon.png");
			mTex_Leader = add("leader_board.png");
			mTex_Music = new SimplePlane[2];
			mTex_Music[0] = add("music_off.png");
			mTex_Music[1] = add("music_on.png");
			mTex_Play = add("play_icon.png");
			mTex_Retry = add("retry_icon.png");
			mTex_Score = add("score.png");
			mTex_Sound =new SimplePlane[2];
			mTex_Sound[0] = add("sound_off.png");
			mTex_Sound[1] = add("sound_on.png");
			mTex_MenuTxt =new SimplePlane[3];
			mTex_MenuTxt[0] = add("bubble.png");
			mTex_MenuTxt[1] = add("candy.png");
			mTex_MenuTxt[2] = addRotate("option.png");
			mTex_Splash = add("splash.png");
			load_Font();
			mTex_BCandy = new SimplePlane[2][];
			for (int j = 0; j < mTex_BCandy.length; j++){
				mTex_BCandy[j] = new SimplePlane[M.NOBAL];
				Bitmap b = LoadImgfromAsset(j+".png");
				for (int i = 0; i < mTex_BCandy[j].length; i++) {
					mTex_BCandy[j][i] = addBitmap(Bitmap.createBitmap(b,((i * b.getWidth()) / M.NOBAL), 0, b.getWidth()/ M.NOBAL, b.getHeight(), null, true));
				}
			}
			Bitmap b = LoadImgfromAsset("star_sprite.png");
			mTex_Star = new SimplePlane[7];
			for(int i=0;i<mTex_Star.length;i++)
				mTex_Star[i] = addRotate(Bitmap.createBitmap(b,((i * b.getWidth()) / M.NOBAL), 0, b.getWidth()/ M.NOBAL, b.getHeight(), null, true));
			mAnim = new Animation[20];
			for (int i = 0; i < mAnim.length; i++) {
				mAnim[i] = new Animation();
			}
			mJoin = new Animation();
			mRCandy = new RCandy[M.Row * M.C];
			for (int i = 0; i < mRCandy.length; i++) {
				mRCandy[i] = new RCandy();
			}
			mCBubble = new CBubble[M.Row][M.C];
			for (int i = 0; i < M.Row; i++) {
				mCBubble[i] = new CBubble[M.C];
				for (int j = 0; j < M.C; j++) {
					mCBubble[i][j] = new CBubble();
				}
			}
			mPlayer = new Player();
			
//			gameReset();
		} catch (Exception e) {
		}

	}
	void gameReset(int type) {
		mPlayer.ball = (byte)Math.abs(type%2);
//		by = M.BY;
		playBest = false;
		for (int i = 0; i < mAnim.length; i++) {
			mAnim[i].set(0, 0, -1,0);
		}
		for (int j = 0; j < M.Row; j++) {// 13
			for (int i = 0; i < M.C; i++) {// 9
				mCBubble[j][i].set(-1, j, i);
			}
		}
		
		for (int j = 0; j < M.Row/2-1; j++) {// 13
			for (int i = 0; i < M.C; i++) {// 9
				mCBubble[j][i].set(M.mRand.nextInt(M.NOBAL), j, i);
//				mCBubble[j][i].set(1, j, i);
			}
		}
		mPlayer.set(3, false);
		for (int i = 0; i < mRCandy.length; i++) {
			mRCandy[i].resetAll();
		}
		mScore = 0;
		mPlayer.fireColor = (byte)root.setNewBall();
		root.setNewBall();
		newHit = 5;
		hitBall =newHit;
		win = 0;
		IsEndless =  true;
		GameRenderer.mStart.load();
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
			if(M.GameScreen != M.GAMEPLAY)
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
	SimplePlane addRotate(String ID) {
		SimplePlane SP = null;
		Bitmap b = LoadImgfromAsset(ID);
		try {
			SP = new SimplePlane((b.getWidth() / M.mMaxY),
					(b.getHeight() / M.mMaxY));
			SP.loadBitmap(b);// R.drawable.jay
		} catch (Exception e) {
		}
		return SP;
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
	SimplePlane addRotate(Bitmap b) {
		SimplePlane SP = null;
		try {
			SP = new SimplePlane((b.getWidth() / M.mMaxY),
					(b.getHeight() / M.mMaxY));
			SP.loadBitmap(b);// R.drawable.jay
		} catch (Exception e) {
		}
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
			System.out.println(""+ID+"!!!!!!!!!!!!!!!!!!!!!~~~~~~~~~~~~~!!!!!!!!!!!!!!!!!!!!!!!!!"+e.getMessage());
			return null;
		}
	}
	void load_Font() {
		mTex_Font = new SimplePlane[10];
		Bitmap b = LoadImgfromAsset("fontstrip.png");
		for (int i = 0; i < mTex_Font.length; i++)
			mTex_Font[i] = addBitmap(Bitmap.createBitmap(b, i * b.getWidth()
					/ 16, 0, b.getWidth() / 16, b.getHeight(), null, true));

	}
}
