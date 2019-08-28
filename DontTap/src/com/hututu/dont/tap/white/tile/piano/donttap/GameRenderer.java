package com.hututu.dont.tap.white.tile.piano.donttap;

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
	public static Context mContext;
	public static Start mStart;
	long startTime = System.currentTimeMillis();
	long STime = System.currentTimeMillis();
	
	SimplePlane mTex_Logo, mTex_About, mTex_Back, mTex_Exit, mTex_Best,mTex_Signin,mTex_Leader,
			mTex_New, mTex_Retry, mTex_Bomb, mTex_Failed, mTex_Well,mTex_Start,mTex_DTap;
	SimplePlane[] mTex_Tile, mTex_BTile, mTex_MemuText, mTex_STile,
			mTex_SMText[], mTex_InText, mTex_More[], mTex_Font[];
	
	Tile mTile[][];
	
	boolean addFree = false;
	boolean  SingUpadate = false;
	boolean mAchiUnlock[] = new boolean[5];
	boolean mul_Color = false;
	boolean OverRev;
	boolean NewBest = false;
	
	int RowNo = 0;
	int mSelTile = -1;
	int mSelSemiTile = 0;
	int tile = -1;
	int time = -1;
	int MoveTile;
	int TotalTile = 0;
	int OverCounter;
	int type;
	int next;
	int Bllinck;
	int spd = 0;
	
	float Speed = -.1f;
	float go = 0;
	float lastWindow;
	float BestScore[][];
	float Score;
	
	

	String mPName = "";
	
	Font mFont;
	InApp mInApp;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			mStart.adView.setVisibility(msg.what);
		}
	};

	public GameRenderer(Context context) {
		mContext = context;
		mStart = (Start) mContext;
		root = new Group(this);
		init();
	}

	void init() {
		try {
			mInApp = new InApp();
			mInApp.onCreate();
			
			mTex_DTap = add("font/double_tab0.png");
			mTex_Signin = add("font/signin.png");
			mTex_Leader = add("font/leaderboard_icon.png");
			mTex_Start = add("font/start.png");
			mTex_Well = add("font/welldone.png");
			mTex_Failed = add("font/failed.png");
			mTex_Bomb = add("bomb.png");
			mTex_Retry = add("font/retry.png");
			mTex_Best = add("font/best0.png");
			mTex_New = add("font/new0.png");
			mTex_About = add("aboutus.png");
			mTex_Back = add("font/back.png");
			mTex_Exit = add("font/exit.png");
			mTex_SMText = new SimplePlane[4][];
			mTex_SMText[0] = new SimplePlane[3];
			mTex_SMText[0][0] = add("font/25tile.png");
			mTex_SMText[0][1] = add("font/50tile.png");
			mTex_SMText[0][2] = add("font/100tile.png");

			mTex_SMText[1] = new SimplePlane[3];
			mTex_SMText[1][0] = add("font/normal.png");
			mTex_SMText[1][1] = add("font/faster.png");
			mTex_SMText[1][2] = add("font/reverse.png");

			mTex_SMText[2] = new SimplePlane[3];
			mTex_SMText[2][0] = add("font/15sec.png");
			mTex_SMText[2][1] = add("font/30sec.png");
			mTex_SMText[2][2] = add("font/60sec.png");

			mTex_SMText[3] = new SimplePlane[3];
			mTex_SMText[3][0] = add("font/normal0.png");
			mTex_SMText[3][1] = add("font/faster0.png");
			mTex_SMText[3][2] = add("font/reverse0.png");

			mTex_More = new SimplePlane[6][];
			mTex_More[0] = new SimplePlane[1];
			mTex_More[0][0] = add("font/score.png");

			mTex_More[1] = new SimplePlane[3];
			mTex_More[1][0] = add("font/sound.png");
			mTex_More[1][1] = add("font/on.png");
			mTex_More[1][2] = add("font/off.png");

			mTex_More[2] = new SimplePlane[1];
			mTex_More[2][0] = add("font/moregame.png");

			mTex_More[3] = new SimplePlane[1];
			mTex_More[3][0] = add("font/about.png");

			mTex_More[4] = new SimplePlane[3];
			mTex_More[4][0] = add("font/facebook.png");
			mTex_More[4][1] = add("font/google+.png");
			mTex_More[4][2] = add("font/share.png");

			mTex_More[5] = new SimplePlane[3];
			mTex_More[5][0] = add("font/color.png");

			mTex_STile = new SimplePlane[2];
			mTex_STile[0] = add("submenu0.png");
			mTex_STile[1] = add("submenu1.png");

			mTex_BTile = new SimplePlane[2];
			mTex_BTile[0] = add("black_tile.png");
			mTex_BTile[1] = add("white_tile.png");

			mTex_MemuText = new SimplePlane[6];
			mTex_MemuText[0] = add("font/classic.png");
			mTex_MemuText[1] = add("font/arcade.png");
			mTex_MemuText[2] = add("font/timetravel.png");
			mTex_MemuText[3] = add("font/timetravel+.png");
			mTex_MemuText[4] = add("font/tileinside.png");
			mTex_MemuText[5] = add("font/more.png");

			mTex_InText = new SimplePlane[6];
			mTex_InText[0] = add("font/bomb.png");
			mTex_InText[1] = add("font/blinking.png");
			mTex_InText[2] = add("font/boublerow.png");
			mTex_InText[3] = add("font/Dubletab.png");
			mTex_InText[4] = add("font/hidenseek.png");
			mTex_InText[5] = add("font/buy.png");

			mTex_Logo = add("hututugames.png");
			mTex_Tile = new SimplePlane[4];
			mTex_Tile[0] = add("tile/pattern.png");
			for (int i = 1; i < mTex_Tile.length; i++) {
				mTex_Tile[i] = add("tile/" + (i - 1) + ".png");
			}
			mFont = new Font(this);
			load_Font();

			mTile = new Tile[6][];
			for (int i = 0; i < mTile.length; i++) {
				mTile[i] = new Tile[4];
				for (int j = 0; j < mTile[i].length; j++)
					mTile[i][j] = new Tile();
			}
			BestScore = new float[5][];
			for(int i=0;i<BestScore.length-1;i++)
				BestScore[i] = new float[3];
			BestScore[4] = new float[5];
			BestScore[0][0] = 999999;
			BestScore[0][1] = 999999;
			BestScore[0][2] = 999999;
			root.th = mTex_BTile[0].Height()*.93f;
			// gameReset();
		} catch (Exception e) {
		}
	}
	
	void gameReset(final int _tile, final int _time, final int _spd,
			final int typ) {
		for (int i = 0; i < mTile.length; i++) {
			int rand = M.mRand.nextInt(4);
			for (int j = 0; j < mTile[i].length; j++)
				mTile[i][j].set(rand == j ? M.BLACK : M.WHITE, -.0f + i * .5f);
		}
		MoveTile = RowNo = 0;
		time = _time;
		tile = _tile;
		spd = _spd;
		lastWindow = 1000;
		NewBest = false;
		STime = System.currentTimeMillis();
		go =0;
		OverCounter =-1;
		OverRev = false;
		type = typ;
		next = 0;//M.mRand.nextInt(4);
		Bllinck =0;
		switch (spd) {
		case 1:
			Speed = 0.04f;
			for (int i = 0; i < mTile.length; i++) {
				int rand = M.mRand.nextInt(4);
				for (int j = 0; j < mTile[i].length; j++)
					mTile[i][j].set(rand == j ? M.BLACK : M.WHITE, 0.0f - i * .5f);
				
			}
			break;
		case -1:
			Speed = -.04f;
			break;
		case -2:
			Speed = -.06f;
			break;
		default:
			Speed = -.1f;
			break;
		}
		adsCount++;
		if(adsCount %2 ==0)
			GameRenderer.mStart.load();
		M.GameScreen = M.GAMEPLAY;
	}
	int adsCount = 1;
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
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
			if (M.GameScreen == M.GAMEPLAY && !addFree) {
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
		if (dt < 30)
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
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~    "+ID);
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
		{
			mTex_Font = new SimplePlane[2][12];
			for (int j = 0; j < mTex_Font.length; j++) {
				Bitmap b = LoadImgfromAsset(j + ".png");
				for (int i = 0; i < mTex_Font[j].length; i++)
					mTex_Font[j][i] = addBitmap(Bitmap.createBitmap(b,
							i * b.getWidth() / 16, 0, b.getWidth() / 16,
							b.getHeight(), null, true));
			}
		}

	}
}
