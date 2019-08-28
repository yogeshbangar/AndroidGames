package com.hututu.games.ninjajumper;
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
	int mSel = 0;
	
	boolean addFree = false;
	boolean SingUpadate = false;
	boolean mAchiUnlock[] = new boolean[5];
	boolean gameStart = false;
	
	
	int mScore = 0;
	int mHScore = 0;
	int mTotal = 0;
	int bgNo = 0;
//	int Move = 0;
	int OverCounter = 0;
	int NoPower = 0;
	
	Spike mSpike[][];
	Player mPlayer;
	Power mPower;
	InApp mInApp;
	RedKhon mRed[]; 
	Power NewHigh;
	
	SimplePlane[] mTex_BG[], mTex_Sound, mTex_SoundIcn, mTex_Font, mTex_Icn;
	SimplePlane mTex_Logo, mTex_Player, mTex_Button, mTex_buyIcn, mTex_Power,
			mTex_Option, mTex_optionIcn, mTex_Play, mTex_PlayIcn, mTex_Splash,
			mTex_About, mTex_AboutIcn, mTex_BackIcn, mTex_Back, mTex_JoinBox,
			mTex_Board, mTex_HelpIcn, mTex_AbtScr, mTex_BuyTex, mTex_BuyBtn,
			mTex_HelpTex, mTex_Best, mTex_OverBox, mTex_OverTxt, mTex_JoinTxt,
			mTex_Help, mTex_Retry, mTex_RetryIcn, mTex_ScoreTxt, mTex_Leader,
			mTex_Achive, mTex_Shiled, mTex_PoseIcn, mTex_ScorBar, mTex_Sheald,
			mTex_SPower,mTex_Red,mTex_Menu,mTex_MIcn,mTex_PauseTex, mTex_NScr;
	
	
	
	private Handler handler = new Handler() {public void handleMessage(Message msg) {mStart.adView.setVisibility(msg.what);}};
	public GameRenderer(Context context) 
	{
		mContext = context;
		mStart	= (Start)mContext;
		root = new Group(this);
		init();
	}

	void init() {
		try {
			mInApp = new InApp();
			mInApp.onCreate();
			mTex_NScr = add("newhighscore.png");
			mTex_Menu = add("menu.png");
			mTex_PauseTex = add("gamepaused_taxt.png");
			mTex_MIcn = add("menu_icon.png");
			mTex_Red = add("0.png");
			mTex_Sheald = addRotate("sheald.png");
			mTex_SPower = addRotate("slowpower.png");
			mTex_Shiled = add("shealdboard.png");
			mTex_PoseIcn = add("paused_icon.png");
			mTex_ScorBar = add("gameplay_score.png");
			mTex_Leader = add("leaderboard_icon.png");
			mTex_Achive = add("achivement_icon.png");
			mTex_Best = add("best-score.png");
			mTex_OverBox = add("gameover_box.png");
			mTex_OverTxt = add("gameover_taxt.png");
			mTex_JoinBox = add("join_box.png");
			mTex_JoinTxt = add("join_taxt.png");
			mTex_Retry = add("retry.png");
			mTex_RetryIcn = add("retry_icon.png");
			mTex_ScoreTxt = add("score_text.png");
			mTex_Icn = new SimplePlane[4];
			mTex_Icn[0] = add("hututu.png");
			mTex_Icn[1] = add("G+.png");
			mTex_Icn[2] = add("twiter.png");
			mTex_Icn[3] = add("facebook.png");
			
			mTex_Logo = add("hututugames.png");
			mTex_HelpTex = add("help_taxt.png");
			mTex_Player = add("ninja.png");
			mTex_BuyBtn = add("buy.png");
			mTex_BuyTex = add("buy_power-txt.png");
			mTex_Button = add("btn_rec.png");
			mTex_buyIcn = add("buy_icon.png");
			mTex_Power = add("buy_power.png");
			mTex_Option = add("option.png");
			mTex_optionIcn = add("option_icon.png");
			mTex_Play = add("play.png");
			mTex_PlayIcn = add("play_icon.png");
			mTex_Splash = add("splash.png");
			
			mTex_About = add("about.png");
			mTex_AboutIcn = add("about-icon.png");
			mTex_BackIcn = add("back.png");
			mTex_Back = add("back_text.png");
			mTex_Help = add("help.png");
			mTex_Board = add("help_about_board.png");
			mTex_HelpIcn = add("help_icon.png");
			mTex_AbtScr = add("about_taxt.png");
			
			mTex_Sound = new SimplePlane[2];
			mTex_Sound[0] = add("soundoff.png");
			mTex_Sound[1] = add("soundon.png");
			mTex_SoundIcn = new SimplePlane[2];
			mTex_SoundIcn[0] = add("soundoff_icon.png");
			mTex_SoundIcn[1] = add("soundon_icon.png");
			
			load_Font();
			mTex_BG = new SimplePlane[3][];
			for (int i = 0; i < mTex_BG.length; i++) {
				mTex_BG[i] = new SimplePlane[4];
				for (int j = 0; j < mTex_BG[i].length; j++) {
					mTex_BG[i][j] = add("bg/" + i + "" + j + ".png");
				}
			}
			mSpike = new Spike[2][];
			for (int i = 0; i < mSpike.length; i++) {
				mSpike[i] = new Spike[9];
				for (int j = 0; j < mSpike[i].length; j++) {
					mSpike[i][j] = new Spike();
				}
			}
			mPower = new Power();
			mPlayer = new Player();
			mRed = new RedKhon[100];
			for(int i =0;i<mRed.length;i++)
				mRed[i] = new RedKhon();
			NewHigh = new Power();
			gameReset();
		} catch (Exception e) {
		}

	}

	void gameReset() {
		for (int i = 0; i < mSpike.length; i++) {
			for (int j = 0; j < mSpike[i].length; j++) {
				mSpike[i][j].set(false);
			}
		}
		mPlayer.set();
		gameStart = false;
		OverCounter = 0;
		mScore = 0;
		mPower.set(0);
		for (int i = 0; i < mRed.length; i++)
			mRed[i].set(-100, -100);
		bgNo = M.mRand.nextInt(3);
		if(mHScore > 0){
			NewHigh.set(0);
			NewHigh.x = 0;
			NewHigh.y = -.3f;
		}else{
			NewHigh.set(2);
		}
		mStart.load();
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
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
		if (mStart.adView != null) {
			if (M.GameScreen != M.GAMEMENU) {
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

		// Log.d("----------------=>  "+x,y+"   -----------    "+z);
	}

	public void onShake(float force) {
	}

	SimplePlane add(String ID) {
		SimplePlane SP = null;
		Bitmap b = LoadImgfromAsset(ID);
		try {
			SP = new SimplePlane((b.getWidth() / M.mMaxX), (b.getHeight() / M.mMaxY));
			SP.loadBitmap(b);// R.drawable.jay
		} catch (Exception e) {
		}
		return SP;
	}

	SimplePlane addBitmap(Bitmap b) {
		SimplePlane SP = null;
		try {
			SP = new SimplePlane((b.getWidth() / M.mMaxX), (b.getHeight() / M.mMaxY));
			SP.loadBitmap(b);// R.drawable.jay
		} catch (Exception e) {
		}
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
//		System.out.println(ID + " ~~  " );
		try {
			return BitmapFactory.decodeStream(mContext.getAssets().open(ID));
		} catch (Exception e) {
			System.out.println(ID + " ~~  " + e.getMessage());
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
		Bitmap b = LoadImgfromAsset("fontstrip.png");
		for (int i = 0; i < mTex_Font.length; i++)
			mTex_Font[i] = addBitmap(Bitmap.createBitmap(b, i * b.getWidth()/ 16, 0, b.getWidth() / 16,b.getHeight(), null, true));
	}
}
