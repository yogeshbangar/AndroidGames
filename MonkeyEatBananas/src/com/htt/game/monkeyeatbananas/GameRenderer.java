package com.htt.game.monkeyeatbananas;

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
	
	SimplePlane[] mTex_BG,mTex_Opp,mTex_Kela,mTex_Bandar[],mTex_Font,mTex_sound,mTex_music;
	SimplePlane[] mTex_BanderFall,mTex_Leaf,mTex_Star;
	SimplePlane mTex_ads,mTex_BnnTxt,mTex_Lvl,mTex_fb,mTex_home,mTex_leader;
	SimplePlane mTex_PauseBtn,mTex_Paused,mTex_Play,mTex_Playbig,mTex_rate,mTex_Dead,mTex_Best;
	SimplePlane mTex_retry,mTex_score,mTex_share,mTex_text,mTex_Logo,mTex_Danda,mTex_Anim;
	SimplePlane mTex_SBnn,mTex_Claud,mTex_Achiv;
	
	boolean addFree = false;
	boolean mAchiUnlock[] = new boolean[5];
	
	int mScore =0;
	int mHScore =0;
	int mLvl =0;
	int gameEnd = 0;
	int noObject = 1;
	int mTotal =0;
	
	float spd = -.01f;
	float mBGY = 0;
	float obspd = 1;
	float lspd = 0;
	
	Pratidwandi mPrati[];
	Leaf mLeaf[];
	Bandar mBandar;
	Danda mDanda[];
	Banana mBanana[];
	
	Takkar mTakkar[];
	BCollect mBcCollect;
	Partical mPartical[];
	InApp mInApp;
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
			mTex_Achiv = add("Ui/Achievement.png");
			mTex_Claud = add("cloud.png");
			mTex_SBnn = add("small-banana-partical.png");
			mTex_Leaf = new SimplePlane[2];
			mTex_Leaf[0] = add("leaf.png");
			mTex_Leaf[1] = addBitmap(FlipHorizontal(LoadImgfromAsset("leaf.png")));
			
			mTex_BanderFall = new SimplePlane[2];
			mTex_BanderFall[0] = add("monkey1.png");
			mTex_BanderFall[1] = add("monkey2.png");
			mTex_Dead = add("dead.png");
			mTex_ads = add("Ui/ads.png");
			mTex_Best = add("Ui/best.png");
			mTex_BnnTxt = add("Ui/banana-text.png");
			mTex_Lvl = add("Ui/level.png");
			mTex_fb = add("Ui/fb.png");
			mTex_home = add("Ui/home.png");
			mTex_leader = add("Ui/lether.png");
			mTex_music = new SimplePlane[2];
			mTex_music[0] = add("Ui/music-off.png");
			mTex_music[1] = add("Ui/music-on.png");
			mTex_PauseBtn = add("Ui/pause-btn.png");
			mTex_Paused = add("Ui/paused-text.png");
			mTex_Play = add("Ui/play.png");
			mTex_Playbig = add("Ui/play-big.png");
			mTex_rate = add("Ui/rate.png");
			
			mTex_retry = add("Ui/retry.png");
			mTex_score = add("Ui/score-bg.png");
			mTex_share = add("Ui/share.png");
			mTex_sound = new SimplePlane[2];
			mTex_sound[0] = add("Ui/sound_off.png");
			mTex_sound[1] = add("Ui/sound_on.png");
			mTex_text = add("Ui/text.png");
			
			
			mTex_Kela = new SimplePlane[4];
			mTex_Kela[0] = add("Bananas1.png");
			mTex_Kela[1] = addBitmap(FlipHorizontal(LoadImgfromAsset("Bananas1.png")));
			mTex_Kela[2] = addBitmap(FlipHorizontal(LoadImgfromAsset("Bananas2.png")));
			mTex_Kela[3] = add("Bananas2.png");
			
			Bitmap b = LoadImgfromAsset("monkey_sprite.png");
			mTex_Bandar = new SimplePlane[2][14];
			for(int i =0 ;i<mTex_Bandar[0].length;i++){
				mTex_Bandar[0][i] = addBitmap(Bitmap.createBitmap(b, (b.getWidth()/4)*(i%4), (b.getHeight()/4)*(i/4), (b.getWidth()/4), (b.getHeight()/4)));
				mTex_Bandar[1][i] = addBitmap(FlipHorizontal(Bitmap.createBitmap(b, (b.getWidth()/4)*(i%4), (b.getHeight()/4)*(i/4), (b.getWidth()/4), (b.getHeight()/4))));
			}
			b = LoadImgfromAsset("monkeyStar.png");
			mTex_Star = new SimplePlane[3];
			for(int i =0 ;i<mTex_Star.length;i++){
				mTex_Star[i] = addBitmap(Bitmap.createBitmap(b, (b.getWidth()/4)*(i%4), (b.getHeight()/4)*(i/4), (b.getWidth()/4), (b.getHeight())));
			}
			
			mTex_Opp	= new SimplePlane[12];
			for(int i =0;i<mTex_Opp.length;i++)
				mTex_Opp[i]		= add("opp/"+i+".png");
			
			mTex_BG				= new SimplePlane[8];
			for(int i =0;i<mTex_BG.length;i++)
				mTex_BG[i]		= add("BG/"+i+".jpg");
			load_Font();
			mTex_Logo = add("logo.png");
			mTex_Danda = add("danda.png");
			
			mTex_Anim = add("banana-ani.png");

			mDanda = new Danda[5];
			for (int i = 0; i < mDanda.length; i++) {
				mDanda[i] = new Danda();
			}
			mBanana = new Banana[5];
			for (int i = 0; i < mBanana.length; i++) {
				mBanana[i] = new Banana();
			}
			mPrati = new Pratidwandi[20];
			for (int i = 0; i < mPrati.length; i++) {
				mPrati[i] = new Pratidwandi();
			}

			mTakkar = new Takkar[20];
			for (int i = 0; i < mTakkar.length; i++) {
				mTakkar[i] = new Takkar();
			}
			mLeaf = new Leaf[2];
			for (int i = 0; i < mLeaf.length; i++) {
				mLeaf[i] = new Leaf();
			}
			mPartical = new Partical[50];
			for(int i = 0;i<mPartical.length;i++){
				mPartical[i] = new Partical();
			}
			mBandar = new Bandar();
			mBcCollect = new BCollect();
//			gameReset();
		} catch (Exception e) {
			System.out.println("~~~~~~~initError~~~~~~> "+e.toString());
		}
	}

	void gameReset() {
		for(int i =0;i<mDanda.length;i++){
			mDanda[i].set(-1+i*mTex_Danda.Height());
		}
		for (int i = 0; i < mPrati.length; i++) {
			mPrati[i].set(M.mRand.nextBoolean()?.2f:-.2f, 1+i*1.5f,0);
		}
		for (int i = 0; i < mBanana.length; i++) {
			mBanana[i].set(i*.5f);
		}
		for (int i = 0; i < mTakkar.length; i++) {
			mTakkar[i].set(-100,0);
		}
		for (int i = 0; i < mLeaf.length; i++) {
			mLeaf[i].set(-100,-i,false);
		}
		mBandar.set(-.18f,-.7f);
		mBcCollect.set(0, -10);
		mBGY = mScore = mLvl = gameEnd = 0;
		noObject = -1;
		spd = -.01f;
		obspd = 1;
		for(int i = 0;i<mPartical.length;i++){
			mPartical[i].vx =0;
		}
		{
			if (AdcCount % 6 == 1)
				mStart.load();
			if (AdcCount % 6 == 2)
				GameRenderer.mStart.LoadSmartHandler();
		}
	}
	int AdcCount =0;
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
	}
	private Handler handler = new Handler() {public void handleMessage(Message msg) {mStart.adView.setVisibility(msg.what);}};
	private Handler handler2 = new Handler() {public void handleMessage(Message msg) {mStart.adHouse.setVisibility(msg.what);}};
	public void onDrawFrame(GL10 gl) 
	{
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		root.draw(gl);
		
		if (mStart.adView != null) {
			if (M.GameScreen != M.GAMEOVER) {
				int inv = mStart.adView.getVisibility();
				if (inv == AdView.GONE) { try { handler.sendEmptyMessage(AdView.VISIBLE); } catch (Exception e) {}}
			} else {
				int inv = mStart.adView.getVisibility();
				if (inv == AdView.VISIBLE) { try { handler.sendEmptyMessage(AdView.GONE); } catch (Exception e) { } }
			}
		}
//		/*AdHouse*/
		if (mStart.adHouse != null) {
			if (M.GameScreen == M.GAMEOVER) {
				int inv = mStart.adHouse.getVisibility();
				if (inv == AdView.GONE) {try {handler2.sendEmptyMessage(AdView.VISIBLE);} catch (Exception e) {}}
			} else {
				int inv = mStart.adHouse.getVisibility();
				if (inv == AdView.VISIBLE) {try {handler2.sendEmptyMessage(AdView.GONE);} catch (Exception e) {}
				}
			}
		}
//		/*AdHouse*/
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
			System.out.println(ID+"!!!!!!!!!!!!!!!!!!!!!~~~~~~~~~~~~~!!!!!!!!!!!!!!!!!!!!!!!!!"+e.getMessage());
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
		mTex_Font = new SimplePlane[11];
		Bitmap b = LoadImgfromAsset("Ui/fontstrip.png");
		for (int i = 0; i < mTex_Font.length; i++)
			mTex_Font[i] = addBitmap(Bitmap.createBitmap(b, i * b.getWidth()/ 16, 0, b.getWidth() / 16, b.getHeight(), null, true));
		
	}
	
}
