package com.hututu.game.bubblecandyrescue;

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

public class GameRenderer implements Renderer {
	final Group root;
	static Context mContext;
	public static Start mStart;
	long startTime = System.currentTimeMillis();
	int resumeCounter = 0;
	int mSel = 0;

	SimplePlane[] mTex_Ball, mTex_Music, mTex_Sound,  mTex_Font,  mTex_Button,mTex_Dot, mTex_Blast, mTex_Jar, mTex_Fire;
	SimplePlane[] mTex_House,mTex_Exchenge,mTex_Water,mTex_LAnim,mTex_Hit,mTex_Candy,mTex_Botline,mTex_SFont, mTex_Jstr;
	SimplePlane mTex_logo, mTex_cont, mTex_HangB, mTex_HangS, mTex_More,mTex_Name, mTex_newgame, mTex_Splash, mTex_LvlW;
	SimplePlane mTex_About, mTex_Achiv, mTex_ads, mTex_back, mTex_conticn,mTex_fb, mTex_gp, mTex_help,mTex_home,mTex_BG;
	SimplePlane mTex_hututu, mTex_leader, mTex_Pause,  mTex_play, mTex_rateus,mTex_retry,  mTex_setting, mTex_BestScore;
	SimplePlane mTex_AboutF, mTex_AboutTxt, mTex_Board, mTex_Board0,mTex_Sharebar, mTex_Line, mTex_adsbar, mTex_control;
	SimplePlane mTex_Cong, mTex_Freegame, mTex_Gameover, mTex_Gamepaus,mTex_Score, mTex_controlT, mTex_share, mTex_SBar;
	SimplePlane mTex_Life, mTex_smoke, mTex_Join, mTex_Level, mTex_Topline, mTex_Cross,mTex_Ling,mTex_LingBar,mTex_Achi;
	SimplePlane mTex_BScore,mTex_Ltet;
	
	Ball[][] matrix;
	Player mPlayer;
	Score mScore[];
	RBall[] mRBall;
	Level mLevel;
	Animation mAnim[];
	Animation mJoin;
	
	MainActivity mMainActivity;
	
	boolean IsEndless;
	boolean playBest;
	boolean addFree,SingUpadate;
	
	
	static byte Candy[] = new byte[25];
	
	int BScore[] = new int[105];
	int mHEScore;
	int score;
	int mLvl = 0;
	int mULvl = 1;
	int win = 0;
	int hitBall;
	float by = 0.80f;
	
	boolean Achi[] = new boolean[25];
	final int achiment[] = {
			R.string.achievement_apple_candy,
			R.string.achievement_apricot_candy,
			R.string.achievement_banana_candy,
			R.string.achievement_blackberry_candy,
			R.string.achievement_blueberry_candy,
			R.string.achievement_cherry_candy,
			R.string.achievement_chokeberry_candy,
			R.string.achievement_dates_candy,
			R.string.achievement_grapes_candy,
			R.string.achievement_kiwi_candy,
			R.string.achievement_lemon_candy,
			R.string.achievement_lychee_candy,
			R.string.achievement_mango_candy,
			R.string.achievement_melon_candy,
			R.string.achievement_mixfruits_candy,
			R.string.achievement_mulberry_candy,
			R.string.achievement_orenge_candy,
			R.string.achievement_papaya_candy,
			R.string.achievement_pear_candy,
			R.string.achievement_pineapple_candy,
			R.string.achievement_plum_candy,
			R.string.achievement_pomogranate_candy,
			R.string.achievement_starfruit_candy,
			R.string.achievement_strawberry_candy,
			R.string.achievement_sweetlime_candy
	};
	
	
	
	
//	float movex0, movex1 = 0;
	
	
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			mStart.adView.setVisibility(msg.what);
		}
	};
	private Handler handler2 = new Handler() {
		public void handleMessage(Message msg) {
			mStart.adHouse.setVisibility(msg.what);
		}
	};// AdHouse

	public GameRenderer(Context context) {
		mContext = context;
		mStart = (Start) mContext;
		root = new Group(this);
		init();
	}

	void init() {
		try {
			mMainActivity = new MainActivity();
			mMainActivity.onCreate();
			
			mLevel = new Level();
			
			mTex_Cross 		= add("cross.png");
			mTex_Ling 		= add("bar.png");
			mTex_LingBar 	= add("fill-bar.png");
			
			
			mTex_BScore		= add("score_Bar_0.png");
			mTex_Ltet		= add("score_Bar_1.png");
			
			
			mTex_Topline = add("line/dad_line.png");
			
			mTex_Achi = add("achivementunlock.png");
			
			
			mTex_Jstr 		= new SimplePlane[3];
			mTex_Jstr[0]	= add("join.png");
			mTex_Jstr[1]	= add("join_0.png");
			mTex_Jstr[2]	= add("join_1.png");
			
			
			
			mTex_Botline = new SimplePlane[5];
			mTex_Botline[0]	= add("line/first.png");
			mTex_Botline[1]	= add("line/second0.png");
			mTex_Botline[2]	= add("line/second1.png");
			mTex_Botline[3]	= add("line/third0.png");
			mTex_Botline[4]	= add("line/third1.png");
			
			mTex_Candy = new SimplePlane[25];
			for (int i = 0; i < mTex_Candy.length; i++)
				mTex_Candy[i] = add("candy/" + i + ".png");
			
			
			mTex_Blast = new SimplePlane[3];
			for (int i = 0; i < mTex_Blast.length; i++)
				mTex_Blast[i] = add("blast/" + i + ".png");
			mTex_SBar = add("score_bar.png");
			mTex_BG = add("gameplay_bg.png");
			mTex_smoke = add("smoke2.png");
			mTex_Join = add("0.png");
			mTex_Level = add("UI/menu.png");

			mTex_Dot = new SimplePlane[2];
			mTex_Dot[0] = add("level_dot.png");
			mTex_Dot[1] = add("level_dot_selected.png");

			
			mTex_Exchenge = new SimplePlane[3];
			mTex_Exchenge[0] = add("house/refresh_o.png");
			mTex_Exchenge[1] = add("house/refresh_1.png");
			mTex_Exchenge[2] = add("house/refrash_board.png");
			
			mTex_Fire = new SimplePlane[2];
			mTex_Fire[0] = addRotate("fire_0.png");
			mTex_Fire[1] = add("fire_1.png");

			mTex_Hit = new SimplePlane[9];
			for(int i=0;i<mTex_Hit.length;i++)
				mTex_Hit[i] = add("Hit/"+i+".png");

			
			mTex_House = new SimplePlane[3];
			for(int i=0;i<mTex_House.length;i++)
				mTex_House[i] = add("house/"+i+".png");

			mTex_Jar = new SimplePlane[8];
			for (int i = 0; i < mTex_Jar.length; i++){
				mTex_Jar[i] = add("woter/w" + i + ".png");
			}
			
			mTex_Button = new SimplePlane[2];
			mTex_Button[0] = add("level_off.png");
			mTex_Button[1] = add("level_on.png");

			mTex_LvlW = add("levelcomp_font.png");
			mTex_BestScore = add("bestscore.png");
			mTex_Cong = add("cong_font.png");
			mTex_Freegame = add("freegame.png");
			mTex_Gameover = add("gameover_font.png");
			mTex_Gamepaus = add("gamepaused_font.png");
			mTex_Score = add("score.png");

			mTex_adsbar = add("ads.png");
			mTex_control = add("control_font.png");
			mTex_controlT = add("control_text.png");

			mTex_Line = add("board_line.png");
			mTex_AboutF = add("about_font.png");
			mTex_AboutTxt = add("about.png");
			mTex_Board = add("board.png");
			mTex_Board0 = add("board_0.png");
			mTex_Sharebar = add("share_bar.png");

			mTex_About = add("UI/about.png");
			mTex_Achiv = add("UI/achivement.png");
			mTex_ads = add("UI/adsicon.png");
			mTex_back = add("UI/back.png");
			mTex_conticn = add("UI/continue.png");
			mTex_fb = add("UI/facebook.png");
			mTex_gp = add("UI/g+.png");
			mTex_help = add("UI/help.png");
			mTex_home = add("UI/home.png");
			mTex_hututu = add("UI/hututu.png");
			mTex_leader = add("UI/leaderboard.png");
			mTex_Pause = add("UI/paused.png");
			mTex_play = add("UI/play.png");
			mTex_rateus = add("UI/rateus.png");
			mTex_retry = add("UI/retry.png");
			mTex_setting = add("UI/setting.png");
			mTex_share = add("UI/share.png");

			mTex_Music = new SimplePlane[2];
			mTex_Music[0] = add("UI/musicoff.png");
			mTex_Music[1] = add("UI/musicon.png");
			mTex_Sound = new SimplePlane[2];
			mTex_Sound[0] = add("UI/sound-off.png");
			mTex_Sound[1] = add("UI/soundon.png");

			mTex_cont = add("continue.png");
			mTex_HangB = add("hanging_big.png");
			mTex_HangS = add("hanging_small.png");
			mTex_More = add("more_button.png");
			mTex_Name = add("name.png");
			mTex_newgame = add("newgame.png");
			mTex_Splash = add("splash.png");

			mTex_logo = add("hututugames.png");
			
			
			
			mTex_LAnim = new SimplePlane[2];
			mTex_LAnim[0] = add("Level/0.png");
			mTex_LAnim[1] = addRotate("Level/1.png");
			
			
			
			mTex_Water = new SimplePlane[12];
			
			for (int i = 0; i < mTex_Water.length; i++){
				mTex_Water[i] = add("woter/" + i + ".png");
			}
			
			
			mTex_Ball = new SimplePlane[8];
			for (int i = 0; i < mTex_Ball.length; i++)
				mTex_Ball[i] = add("ball/" + i + ".png");

			load_Font();

			// bubbleFace = new Ball();
			matrix = new Ball[M.Row][M.C];
			for (int i = 0; i < M.Row; i++) {
				matrix[i] = new Ball[M.C];
				for (int j = 0; j < M.C; j++) {
					matrix[i][j] = new Ball();
				}
			}

			mPlayer = new Player();
			mRBall = new RBall[M.Row * M.C];
			for (int i = 0; i < mRBall.length; i++) {
				mRBall[i] = new RBall();
			}
			
			mScore = new Score[15];
			for (int i = 0; i < mScore.length; i++) {
				mScore[i] = new Score();
			}
			

			mAnim = new Animation[20];
			for (int i = 0; i < mAnim.length; i++) {
				mAnim[i] = new Animation();
			}
			mJoin = new Animation();
			gameReset(0);
		} catch (Exception e) {
		}

	}
	Handler adsHandler = new Handler() {
		public void handleMessage(Message msg) {
			mStart.loadInter();
		}
	};
	int ads = 0;
	void gameReset(int level) {
		by = M.BY; 
//		System.out.println(level);
		for (int i = 0; i < mAnim.length; i++) {
			mAnim[i].set(0, 0, -1, 0);
		}

		mJoin.set(0, 0, -1, 0);
		
		mLvl = level;
		if (level > 99) {
			for (int j = 0; j < M.Row; j++) {// 13
				for (int i = 0; i < M.C; i++) {// 9
					matrix[j][i].set(-1, j, i);
				}
			}
			
			for (int j = 0; j < M.Row/2+(level-101); j++) {// 13
				for (int i = 0; i < M.C; i++) {// 9
					matrix[j][i].set(M.mRand.nextInt(7), j, i);
				}
			}
		} else {
			for (int j = 0; j < M.Row; j++) {// 13
				for (int i = 0; i < M.C; i++) {// 9
					matrix[j][i].set(mLevel.level[mLvl][i][j], j, i);
				}
			}
		}
		mPlayer.set(0, false);
		for (int i = 0; i < mRBall.length; i++) {
			mRBall[i].resetAll();
		}
		
		
		for (int i = 0; i < mScore.length; i++) {
			mScore[i].set(-100, -100, 0);
		}
		
		score = 0;
		mPlayer.fireColor = root.setNewBall();
		root.setNewBall();
		hitBall = 5;
		win = 0;
		IsEndless = false;
		playBest = false;

		if (ads % 3 == 1 ) {
			try {
				adsHandler.sendEmptyMessage(0);
			} catch (Exception e) {
			}
		}
	}
	
	
	void gameEndless() {
		by = M.BY;
//		movex0 =0;
		playBest = false;
//		movex1 = mTex_Jar[1].width();
		for (int i = 0; i < mAnim.length; i++) {
			mAnim[i].set(0, 0, -1,0);
		}

		for (int j = 0; j < M.Row; j++) {// 13
			for (int i = 0; i < M.C; i++) {// 9
				matrix[j][i].set(-1, j, i);
			}
		}
		
		for (int j = 0; j < M.Row/2; j++) {// 13
			for (int i = 0; i < M.C; i++) {// 9
				matrix[j][i].set(M.mRand.nextInt(7), j, i);
			}
		}
		mPlayer.set(0, false);
		for (int i = 0; i < mRBall.length; i++) {
			mRBall[i].resetAll();
		}
		score = 0;
		mPlayer.fireColor = root.setNewBall();
		root.setNewBall();
		hitBall =5;
		win = 0;
		IsEndless =  true;
		if (ads % 3 == 1 ) {
			try {
				adsHandler.sendEmptyMessage(0);
			} catch (Exception e) {
			}
		}
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
			resumeCounter++;
			if (M.GameScreen != M.GAMEMENU && M.GameScreen != M.GAMEPLAY && !addFree &&  M.GameScreen != M.GAMEADD) {
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
		/* AdHouse */
		if (mStart.adHouse != null) {
			if (M.GameScreen == M.GAMEADD && !addFree) {
				int inv = mStart.adHouse.getVisibility();
				if (inv == AdView.GONE) {
					try {
						handler2.sendEmptyMessage(AdView.VISIBLE);
					} catch (Exception e) {
					}
				}
			} else {
				int inv = mStart.adHouse.getVisibility();
				if (inv == AdView.VISIBLE) {
					try {
						handler2.sendEmptyMessage(AdView.GONE);
					} catch (Exception e) {
					}
				}
			}
		}
		/* AdHouse */
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
			SP = new SimplePlane((b.getWidth() / M.mMaxX),
					(b.getHeight() / M.mMaxY));
			SP.loadBitmap(b);// R.drawable.jay
		} catch (Exception e) {
		}
		return SP;
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

	SimplePlane addBitmap(Bitmap b) {
		SimplePlane SP = null;
		try {
			SP = new SimplePlane((b.getWidth() / M.mMaxX),
					(b.getHeight() / M.mMaxY));
			SP.loadBitmap(b);// R.drawable.jay
		} catch (Exception e) {
		}
		return SP;
	}

	Bitmap LoadImgfromAsset(String ID) {
		try {
			return BitmapFactory.decodeStream(mContext.getAssets().open(ID));
		} catch (Exception e) {
			// Log.d(""+ID,"!!!!!!!!!!!!!!!!!!!!!~~~~~~~~~~~~~!!!!!!!!!!!!!!!!!!!!!!!!!"+e.getMessage());
			return null;
		}
	}

	Bitmap resizeImg(Bitmap bitmapOrg, int newWidth, int newHeight) {
		int width = bitmapOrg.getWidth();
		int height = bitmapOrg.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		matrix.postRotate(0);
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, width,
				height, matrix, true);
		Log.d("resizeImg========", "newWidth [" + newWidth + "] newHeight ["
				+ newHeight + "]");
		return resizedBitmap;
	}

	Bitmap FlipHorizontal(Bitmap bitmapOrg) {
		Matrix matrix = new Matrix();
		matrix.postScale(-1f, 1f);
		matrix.postRotate(0);
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0,
				bitmapOrg.getWidth(), bitmapOrg.getHeight(), matrix, true);
		return resizedBitmap;
	}

	void load_Font() {
		mTex_Font = new SimplePlane[10];
		Bitmap b = LoadImgfromAsset("fontstrip.png");
		for (int i = 0; i < mTex_Font.length; i++)
			mTex_Font[i] = addBitmap(Bitmap.createBitmap(b, i * b.getWidth()
					/ 16, 0, b.getWidth() / 16, b.getHeight(), null, true));
		
		score_Font();
	}
	
	void score_Font() {
		mTex_SFont = new SimplePlane[10];
		Bitmap b = LoadImgfromAsset("score_font.png");
		for (int i = 0; i < mTex_SFont.length; i++)
			mTex_SFont[i] = addBitmap(Bitmap.createBitmap(b, i * b.getWidth()
					/ 16, 0, b.getWidth() / 16, b.getHeight(), null, true));
	}
	
	
	Bitmap loaddrawable(int ID)
	{
		return BitmapFactory.decodeResource(mContext.getResources(), ID);
	}
}
