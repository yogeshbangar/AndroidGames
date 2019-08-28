package com.hututu.game.skydiving;

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

public class GameRenderer implements Renderer {
	final Group root;
	Context mContext;
	public static Start mStart;
	long startTime = System.currentTimeMillis();
	int resumeCounter = 0;
	int mSel = 0;

	private Handler handler0 = new Handler() {
		public void handleMessage(Message msg) {
			mStart.adView.setVisibility(msg.what);
		}
	};
	private Handler handler1 = new Handler() {
		public void handleMessage(Message msg) {
			mStart.adfull.setVisibility(msg.what);
		}
	};
	private Handler handler2 = new Handler() {
		public void handleMessage(Message msg) {
			mStart.adHouse.setVisibility(msg.what);
		}
	};

	SimplePlane mTex_GameBG, mTex_GameBase, mTex_Logo, mTex_Splash, mTex_cDown,
			mTex_cUp, mTex_BoyL, mTex_scrAbout, mTex_LevelBox, mTex_LevelPar,
			mTex_Downarrow, mTex_uparrow, mTex_CSel, mTex_LSel, mTex_Lock,
			mTex_Playse, mTex_About, mTex_Cntbg, mTex_Facebok, mTex_Google,
			mTex_Playde, mTex_Soundoff, mTex_Soundon, mTex_bScore, mTex_Fall,
			mTex_Continue, mTex_Gameover, mTex_GPaused, mTex_Cong,
			mTex_Jumphight, mTex_Level, mTex_Like, mTex_Menu, mTex_Next,
			mTex_Nextlevel, mTex_Pause, mTex_Rate, mTex_Retry, mTex_Score,
			mTex_Share, mTex_Exit, mTex_Rip, mTex_More, mTex_Angel,
			mTex_Pointer, mTex_Hightbar, mTex_Bomb, mTex_Leader, mTex_8x8;

	SimplePlane[] mTex_Boy, mTex_Blast, mTex_Safe, mTex_Font, mTex_MBoy,
			mTex_Cluod, mTex_bBlast, mTex_bBoy, mTex_Point, mTex_Power;

	int mDistance;
	int mLevel;
	int mUnLevel = 1;
	int mPage;
	int mWave;
	int mArrayWve[] = new int[10];
	int mScore;
	int mHScore;

	float r, g, b;
	float mDown;
	float y1 = 0, y2 = 2;
	float move = 0;
	float mMenu = 0;
	float my = -1.5f;
	float Inc = 0.1f;

	Vector[] mBG;
	Vector[] mCluod;
	Vector[] mPoint;
	Vector mCh = new Vector(0,-100);

	Charactor[][] mChar;
	Charactor mBase;
	Animation ani[];//christmasChange
	Power mPower;

	public GameRenderer(Context context) {
		mContext = context;
		mStart = (Start) mContext;
		root = new Group(this);
		init();
	}

	void init() {
		try {
			mTex_8x8 = add("smoke2.png");
			mTex_Fall = addRotate("ch_fall.png");
			mTex_Angel = add("angle.png");
			mTex_Rip = add("rip.png");
			mTex_Exit = add("exit_icon.png");
			mTex_Logo = add("hututugames.png");
			mTex_Splash = add("splashfont.png");
			mTex_GameBG = add2("gamebg.jpg");
			mTex_GameBase = add("stone.jpg");

			mTex_LevelBox = add("level_box.png");
			mTex_LevelPar = add("level_parachute.png");
			mTex_Downarrow = add("downarrow.png");
			mTex_uparrow = add("uparrow.png");
			mTex_CSel = add("controlse.png");
			mTex_LSel = add("iconselection.png");
			mTex_Lock = add("lock.png");
			mTex_Playse = add("playse.png");

			mTex_About = add("about.png");
			mTex_Cntbg = add("controlbg.png");
			mTex_Facebok = add("facebook.png");
			mTex_Google = add("google.png");
			mTex_Playde = add("playde.png");
			mTex_Soundoff = add("soundoff.png");
			mTex_Soundon = add("soundon.png");
			mTex_cDown = add("controldown.png");
			mTex_cUp = add("controlup.png");
			mTex_BoyL = add("boy1.png");

			mTex_bScore = add("best_score.png");
			mTex_Continue = add("continue.png");
			mTex_Gameover = add("gameover.png");
			mTex_GPaused = add("gamepaused.png");
			mTex_Hightbar = add("hightbar.png");
			mTex_Jumphight = add("jumphight.png");
			mTex_Level = add("level.png");
			mTex_Like = add("like.png");
			mTex_Menu = add("menu.png");
			mTex_Next = add("next.png");
			mTex_Nextlevel = add("nextlevel.png");
			mTex_Pause = add("pause.png");
			mTex_Pointer = add("pointer.png");
			mTex_Rate = add("rate.png");
			mTex_Retry = add("retry.png");
			mTex_Score = add("viewscore.png");
			mTex_Leader = add("leaderboard.png");
			mTex_Share = add("share.png");
			mTex_scrAbout = add("aboutus0.png");
			mTex_Cong = add("congratulations.png");
			mTex_More = add("rate.png");
			mTex_Cluod = new SimplePlane[2];
			mTex_Cluod[0] = add("cloud0.png");
			mTex_Cluod[1] = add("cloud1.png");

			mTex_Point = new SimplePlane[6];
			for (int i = 0; i < mTex_Point.length; i++)
				mTex_Point[i] = add("power/" + i + ".png");

			mTex_Power = new SimplePlane[5];
			for (int i = 0; i < mTex_Power.length; i++)
				mTex_Power[i] = add("power/0" + i + ".png");

			mTex_MBoy = new SimplePlane[4];
			for (int i = 0; i < mTex_MBoy.length; i++)
				mTex_MBoy[i] = add("ch" + i + ".png");

			mTex_Bomb = add("bomb.png");
			mTex_bBlast = new SimplePlane[3];
			Bitmap b = LoadImgfromAsset("bmob_fire.png");
			for (int i = 0; i < mTex_bBlast.length; i++)
				mTex_bBlast[i] = addBitmap(Bitmap
						.createBitmap(b, i * b.getWidth() / mTex_bBlast.length,
								0, b.getWidth() / mTex_bBlast.length,
								b.getHeight(), null, true));

			b.recycle();
			mTex_bBoy = new SimplePlane[4];
			b = LoadImgfromAsset("power/05.png");
			for (int i = 0; i < mTex_bBoy.length; i++)
				mTex_bBoy[i] = addBitmap(Bitmap.createBitmap(b,
						i * b.getWidth() / mTex_bBoy.length, 0, b.getWidth()
								/ mTex_bBoy.length, b.getHeight(), null, true));

			b.recycle();
			mTex_Boy = new SimplePlane[4];
			b = LoadImgfromAsset("boy.png");
			for (int i = 0; i < mTex_Boy.length; i++)
				mTex_Boy[i] = addBitmap(Bitmap.createBitmap(b, i * b.getWidth()
						/ mTex_Boy.length, 0, b.getWidth() / mTex_Boy.length,
						b.getHeight(), null, true));

			b.recycle();
			mTex_Blast = new SimplePlane[8];
			b = LoadImgfromAsset("blast.png");
			for (int i = 0; i < mTex_Blast.length; i++)
				mTex_Blast[i] = addBitmap(Bitmap.createBitmap(b,
						i * b.getWidth() / mTex_Blast.length, 0, b.getWidth()
								/ mTex_Blast.length, b.getHeight(), null, true));

			b.recycle();
			mTex_Safe = new SimplePlane[3];
			b = LoadImgfromAsset("safe_landing.png");
			for (int i = 0; i < mTex_Safe.length; i++)
				mTex_Safe[i] = addBitmap(Bitmap.createBitmap(b,
						i * b.getWidth() / mTex_Safe.length, 0, b.getWidth()
								/ mTex_Safe.length, b.getHeight(), null, true));

			load_Font();

			mPower = new Power();
			mPoint = new Vector[10];
			for (int i = 0; i < mPoint.length; i++)
				mPoint[i] = new Vector(-100, 100);
			mBG = new Vector[2];
			mBG[0] = new Vector();
			mBG[1] = new Vector();

			mCluod = new Vector[5];
			for (int i = 0; i < mCluod.length; i++) {
				mCluod[i] = new Vector();
			}
			mChar = new Charactor[10][8];
			for (int i = 0; i < mChar.length; i++) {
				mChar[i] = new Charactor[8];
				for (int j = 0; j < mChar[i].length; j++) {
					mChar[i][j] = new Charactor();
				}
			}
			ani = new Animation[200];
			for(int i=0;i<ani.length;i++)
			{
				ani[i] = new Animation();
			}
			mBase = new Charactor();
			mLevel = 0;
			//gameReset();
		} catch (Exception e) {
		}

	}

	void gameReset() {
		mBG[0].set(0, 0, 0);
		mBG[1].set(0, 2, 0);
		mDistance = 500;
		mDistance += (mLevel) * 50;
		System.out.println(mBase.go+" !#################### "+M.SPEED+"  "+mBase.x+"   "+mBase.y);
		mBase.set(0, -2, 0, 0, 0);
		for (int i = 0; i < mCluod.length; i++) {
			mCluod[i].set(1 - i * .5f);
		}
		mWave = 0;
		Inc = mTex_Hightbar.Height() / (float) mDistance;
		setWave(Map.levelWave[mLevel][mWave]);
		r = (M.mRand.nextFloat());
		g = (M.mRand.nextFloat());
		b = (M.mRand.nextFloat());
		int r = M.mRand.nextInt(3);
		if (r == 0)
			r = 1;
		if (r == 1)
			g = 1;
		mPower.set(-100, -100, 0, 0);
	}

	void setWave(int wave) {
		mDown = -0.53f;
		M.SPEED = M.MAXSPEED;
		// mDistance = 1500;
		for (int i = 0; i < mChar.length; i++) {
			for (int j = 0; j < mChar[i].length; j++) {
				if (Map.Tiles1Map1MapData[wave][i][j] != 0)
					mChar[i][j].set(-.84f + (j * .24f), 2.84f - (i * .16f), 0,
							-M.SPEED * 5, Map.Tiles1Map1MapData[wave][i][j]);
				else
					mChar[i][j].set(-100, 100, 0, 0,
							Map.Tiles1Map1MapData[wave][i][j]);
			}
		}

	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glClearColor(255.0f, 255.0f, 255.0f, 255.0f);
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
			if (M.GameScreen != M.GAMEADD && M.GameScreen != M.GAMEPAUSE
					&& M.GameScreen != M.GAMEOVER && M.GameScreen != M.GAMEWIN) {
				int inv = mStart.adView.getVisibility();
				if (inv == AdView.GONE) {
					try {
						handler0.sendEmptyMessage(AdView.VISIBLE);
					} catch (Exception e) {
					}
				}
			} else {
				int inv = mStart.adView.getVisibility();
				if (inv == AdView.VISIBLE) {
					try {
						handler0.sendEmptyMessage(AdView.GONE);
					} catch (Exception e) {
					}
				}
			}
		}
		if (mStart.adfull != null) {
			resumeCounter++;
			if (M.GameScreen != M.GAMEADD
					&& (M.GameScreen == M.GAMEPAUSE
							|| M.GameScreen == M.GAMEOVER || M.GameScreen == M.GAMEWIN)) {
				int inv = mStart.adfull.getVisibility();
				if (inv == AdView.GONE) {
					try {
						handler1.sendEmptyMessage(AdView.VISIBLE);
					} catch (Exception e) {
					}
				}
			} else {
				int inv = mStart.adfull.getVisibility();
				if (inv == AdView.VISIBLE) {
					try {
						handler1.sendEmptyMessage(AdView.GONE);
					} catch (Exception e) {
					}
				}
			}
		}
		if (mStart.adHouse != null) {
			resumeCounter++;
			if (M.GameScreen == M.GAMEADD) {
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
			SP = new SimplePlane((b.getWidth() / M.mMaxX),
					(b.getHeight() / M.mMaxY));
			// SP = new
			// SimplePlane((b.getWidth()/M.mMaxX),(b.getHeight()/M.mMaxX));
			SP.loadBitmap(b);// R.drawable.jay
		} catch (Exception e) {
		}
		return SP;
	}

	SimplePlane add2(String ID) {
		SimplePlane SP = null;
		Bitmap b = LoadImgfromAsset(ID);
		try {
			SP = new SimplePlane((b.getWidth() / M.mMaxX) * 2,
					(b.getHeight() / M.mMaxY) * 2);
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
					/ mTex_Font.length, 0, b.getWidth() / mTex_Font.length,
					b.getHeight(), null, true));
	}
}
