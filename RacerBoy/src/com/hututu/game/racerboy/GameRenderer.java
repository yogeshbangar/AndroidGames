package com.hututu.game.racerboy;

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
	int mSel = 0;
	boolean addFree = false;
	
	 
	
	SimplePlane mTex_Logo,mTex_Back,mTex_Squr,mTex_Splash,mTex_Button,mTex_AboutB,mTex_CBG,mTex_HelpB,mTex_OptionB;
	SimplePlane mTex_About,mTex_AboutTxt,mTex_Help,mTex_Conti,mTex_gameover,mTex_paused,mTex_HS,mTex_HelpSrc;
	SimplePlane mTex_Jump,mTex_Menu,mTex_score,mTex_Bar,mTex_buy2,mTex_buy3,mTex_level;
	SimplePlane mTex_levelwin,mTex_NLevel;
	
	SimplePlane[] mTex_Font,mTex_Tile,mTex_SBtn,mTex_Music,mTex_Sound,mTex_Icon,mTex_BG,mTex_Anim;
	SimplePlane[][] mTex_RSGuy,mTex_Guy,mTex_Rival,mTex_BGForg,mTex_GO;
	
	l01 mL01 = new l01();
	l02 mL02 = new l02();
	l03 mL03 = new l03();
	l04 mL04 = new l04();
	l05 mL05 = new l05();
	l06 mL06 = new l06();
	l07 mL07 = new l07();
	l08 mL08 = new l08();
	l09 mL09 = new l09();
	l10 mL10 = new l10();
	l11 mL11 = new l11();
	l12 mL12 = new l12();
	l13 mL13 = new l13();
	
	
	boolean go = false;
	boolean mAchiUnlock[] = new boolean[5];
	boolean SingUpadate= false;
	boolean FromLevel = false;
	
	
	int Levelno =0; 
	int colli =0;
	int mBG;
	int OverCunt = 0;
	int goAnim = 0;
	
	float Bestdistance;
	float TDistance;
	float bgbx[];
	float bgfx[];
	float mScore = 0;
	
	Tile mTile[];
	
	String mPName ="";
	
	Guy mGuy;
	Guy mOppt;
	Animation mAnim;
	
	InApp mInApp;
	
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
			mInApp = new InApp();
			mInApp.onCreate();
			
			
			
			mTex_buy2 = add("new/buy_2.png");
			mTex_buy3 = add("new/buy_3.png");
			mTex_level = add("new/level.png");
			mTex_levelwin = add("new/level_win.png");
			mTex_NLevel = add("new/next_level.png");
			
			
			mTex_Conti = add("UI/continue.png");
			mTex_gameover = add("UI/game-over_text.png");
			
			mTex_paused = add("UI/game-paused.png");
			mTex_HS = add("UI/highscore_text.png");
			mTex_HelpSrc = add("UI/help.png");
			mTex_Jump = add("UI/jump-again.png");
			mTex_Menu = add("UI/menu.png");
			mTex_score = add("UI/score.png");
			mTex_Bar = add("UI/score_bar.png");
			
			mTex_Icon = new SimplePlane[5];
			mTex_Icon[0] = add("UI/achievement.png");
			mTex_Icon[1] = add("UI/lether.png");
			mTex_Icon[2] = add("UI/fb.png");
			mTex_Icon[3] = add("UI/more.png");
			mTex_Icon[4] = add("UI/ratus.png");
			
			
			mTex_BG = new SimplePlane[3];
			for (int i = 0; i < mTex_BG.length; i++)
				mTex_BG[i] = add("bg/" + i + "/0.png");			
			
			mTex_BGForg = new SimplePlane[2][2];
			mTex_BGForg[0][0] = add("bg/0/1.png");
			mTex_BGForg[0][1] = add("bg/0/2.png");
			
			mTex_BGForg[1][0] = add("bg/1/1.png");
			mTex_BGForg[1][1] = add("bg/1/2.png");
			
			
			mTex_Help = add("UI/hoe2play_text.png");
			mTex_About = add("UI/about.png");
			mTex_AboutTxt = add("UI/about_text.png");
			mTex_AboutB = add("UI/aboutus.png");
			mTex_CBG = add("UI/coman_bg.png");
			mTex_HelpB = add("UI/hoe2play.png");
			mTex_OptionB = add("UI/option.png");
			mTex_Music = new SimplePlane[2];
			mTex_Music[0] = add("UI/music0.png");
			mTex_Music[1] = add("UI/music1.png");
			mTex_Sound = new SimplePlane[2];
			mTex_Sound[0] = add("UI/sound0.png");
			mTex_Sound[1] = add("UI/sound1.png");
			
			mTex_Back = add("UI/back_btn.png");
			mTex_Squr = add("UI/back.png");
			mTex_Splash = add("splash.png");
			mTex_Button = add("UI/button.png");
			mTex_SBtn = new SimplePlane[3];
			mTex_SBtn[0] = add("UI/play.png");
			mTex_SBtn[1] = add("UI/setting.png");
			mTex_SBtn[2] = add("new/unlock_world.png");
			mTex_GO = new SimplePlane[2][4];
			for(int i =0;i<mTex_GO[0].length;i++){
				mTex_GO[0][i] = add(i+".png");
				mTex_GO[1][i] = addBitmap(FlipHorizontal(mTex_GO[0][i].getBitmap()));
			}
			load_Font();
			mTex_Logo = add("hututugames.png");
			Bitmap b = LoadImgfromAsset("map.png");
			mTex_Tile = new SimplePlane[185];
			for (int i = 0; i < mTex_Tile.length; i++) {
				mTex_Tile[i] = addBitmap(Bitmap.createBitmap(b, (((i%16)*b.getWidth())/16), (((i/16)*b.getHeight())/16),b.getWidth()/16, b.getHeight()/16, null, true));
			}
			
			b.recycle();
			mTex_Anim = new SimplePlane[8];
			b = LoadImgfromAsset("anim.png");
			for (int i = 0; i < mTex_Anim.length; i++) {
				mTex_Anim[i] = addBitmap(Bitmap.createBitmap(b, (((i%4)*b.getWidth())/4), (((i/4)*b.getHeight())/2),b.getWidth()/4, b.getHeight()/2, null, true));
			}
			
			b.recycle();
			b = LoadImgfromAsset("user.png");
			mTex_Guy = new SimplePlane[2][14];
			for (int i = 0; i < mTex_Guy[0].length; i++) {
				mTex_Guy[0][i] = addBitmap(Bitmap.createBitmap(b, (((i%8)*b.getWidth())/8), (((i/8)*b.getHeight())/2),b.getWidth()/8, b.getHeight()/2, null, true));
				mTex_Guy[1][i] = addBitmap(FlipHorizontal(mTex_Guy[0][i].getBitmap()));
			}
			
			b.recycle();
			b = LoadImgfromAsset("user_restart.png");
			mTex_RSGuy = new SimplePlane[2][14];
			for (int i = 0; i < mTex_RSGuy[0].length; i++) {
				mTex_RSGuy[0][i] = addBitmap(Bitmap.createBitmap(b, (((i%8)*b.getWidth())/8), (((i/8)*b.getHeight())/2),b.getWidth()/8, b.getHeight()/2, null, true));
				mTex_RSGuy[1][i] = addBitmap(FlipHorizontal(mTex_RSGuy[0][i].getBitmap()));
			}
			
			b.recycle();
			b = LoadImgfromAsset("opponent.png");
			mTex_Rival = new SimplePlane[2][14];
			for (int i = 0; i < mTex_Rival[0].length; i++) {
				mTex_Rival[0][i] = addBitmap(Bitmap.createBitmap(b, (((i%8)*b.getWidth())/8), (((i/8)*b.getHeight())/2),b.getWidth()/8, b.getHeight()/2, null, true));
				mTex_Rival[1][i] = addBitmap(FlipHorizontal(mTex_Rival[0][i].getBitmap()));
			}
			mTile = new Tile[30];
			for(int i =0;i<mTile.length;i++){
				mTile[i] = new Tile();
			}
			mAnim = new Animation();
			mGuy = new Guy();
			mOppt = new Guy();
			bgbx = new float[3];
			bgfx = new float[3];
//			gameReset(1);
		} catch (Exception e) {
		}

	}
	//http://www.youtube.com/watch?v=i1tbJ8zJ7mo
	//http://www.youtube.com/watch?v=1JJOAYwov_E   .?
	void gameReset(int lvl,boolean _FLevel) {
		FromLevel = _FLevel;
		mScore = 0;
		go = true;
		goAnim = 0;
		OverCunt = 0;
		colli =0;
//		Levelno++;
//		Levelno =(int)(root.sx*10);
//		Levelno %= 14;
		Levelno =lvl;
		Levelno %= 14;
		
		
		
		
		mGuy.set();
		mOppt.Oppset();
//		mBgx = -.97f;
		for(mGuy.colNo =0;mGuy.colNo<mTile.length;mGuy.colNo++){
			for(int j=0;j<mL01.l1.length;j++){
				switch (Levelno) {
				case 13:
					mTile[mGuy.colNo].arry[j] = mL13.l1[j][mGuy.colNo];
					break;
				case 12:
					mTile[mGuy.colNo].arry[j] = mL12.l1[j][mGuy.colNo];
					break;
				case 11:
					mTile[mGuy.colNo].arry[j] = mL11.l1[j][mGuy.colNo];
					break;
				case 10:
					mTile[mGuy.colNo].arry[j] = mL10.l1[j][mGuy.colNo];
					break;
				case 9:
					mTile[mGuy.colNo].arry[j] = mL09.l1[j][mGuy.colNo];
					break;
				case 8:
					mTile[mGuy.colNo].arry[j] = mL08.l1[j][mGuy.colNo];
					break;
				case 7:
					mTile[mGuy.colNo].arry[j] = mL07.l1[j][mGuy.colNo];
					break;
				case 6:
					mTile[mGuy.colNo].arry[j] = mL06.l1[j][mGuy.colNo];
					break;
				case 5:
					mTile[mGuy.colNo].arry[j] = mL05.l1[j][mGuy.colNo];
					break;
				case 4:
					mTile[mGuy.colNo].arry[j] = mL04.l1[j][mGuy.colNo];
					break;
				case 3:
					mTile[mGuy.colNo].arry[j] = mL03.l1[j][mGuy.colNo];
					break;
				case 2:
					mTile[mGuy.colNo].arry[j] = mL02.l1[j][mGuy.colNo];
					break;
				case 1:
				default:
					mTile[mGuy.colNo].arry[j] = mL01.l1[j][mGuy.colNo];
					break;
				}
//				 mTile[mGuy.colNo].arry[j] = mL01.l1[j][mGuy.colNo]; 
			}
			mTile[mGuy.colNo].x = -.97f + mGuy.colNo * mTex_Tile[0].width();
		}
		mBG = M.mRand.nextInt(3);
		for (int i = 0; i < 3; i++) {
			bgbx[i] = -1+i*mTex_BGForg[0][0].width();
			bgfx[i] = -1+i*mTex_BGForg[0][0].width();
		}
		mAnim.set(0, 0, 100);
		mGuy.down =0;
		if(Levelno==6 || Levelno==8|| Levelno==9|| Levelno==12)
			mGuy.down =1;
		
		if(Levelno==12)
			mGuy.y = .7f;
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
	public void onDrawFrame(GL10 gl) 
	{
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		root.draw(gl);
		if(mStart.adView!=null)
		{
			if(M.GameScreen != M.GAMEPLAY && !addFree)
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
			System.out.println(""+ID+"!!!!!!!!!!!!!!!!!!!!!~~~~~~~~~~~~~!!!!!!!!!!!!!!!!!!!!!!!!!"+e.getMessage());
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
		matrix.postScale(1f, -1f);
		matrix.postRotate(0);
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0,bitmapOrg.getWidth(), bitmapOrg.getHeight(), matrix, true);
		return resizedBitmap;
	}
	void load_Font() {
		mTex_Font = new SimplePlane[10];
		Bitmap b = LoadImgfromAsset("fontstrip.png");
		for (int i = 0; i < mTex_Font.length; i++)
			mTex_Font[i] = addBitmap(Bitmap.createBitmap(b, i * b.getWidth()/ 16, 0, b.getWidth() / 16,b.getHeight(), null, true));
	}
}
