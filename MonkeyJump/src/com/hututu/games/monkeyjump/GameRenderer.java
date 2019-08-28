package com.hututu.games.monkeyjump;

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
	
	
	SimplePlane mTex_Logo,mTex_BG,mTex_kapde,mTex_SBar,mTex_CBG,mTex_About,mTex_AnyPur,mTex_BuyBar,mTex_Pause;
	SimplePlane mTex_BuyBtn,mTex_free,mTex_Achiev,mTex_Bestscore,mTex_fb,mTex_gameover,mTex_highscore,mTex_join;
	SimplePlane mTex_JumpAgain,mTex_Leader,mTex_More,mTex_Power_up,mTex_ratus,mTex_continue,mTex_menu,mTex_score;
	SimplePlane mTex_Aboutus,mTex_BuyPower,mTex_how2play,mTex_Option,mTex_MRotate,mTex_MRoket,mTex_Help,mTex_Glow;
	SimplePlane mTex_Power,mTex_PBTN,mTex_NewScore,mTex_Splash,mTex_Play,mTex_OptiontBtn;
	
	SimplePlane[] mTex_Tile,mTex_MJump,mTex_Char,mTex_Font,mTex_Partical,mTex_Bomb,mTex_Back,mTex_Music,mTex_Sound;
	SimplePlane[] mTex_MBird,mTex_GameBG,mTex_Shild;
	SimplePlane[][] mTex_Monkey,mTex_Hardle,mTex_Gilhari,mTex_Chidiya,mTex_Villain,mTex_MFall,mTex_MGilhari; 
	
	
	boolean fromGame = false;
	boolean addFree = false;
	boolean SingUpadate = false;
	boolean mAchiUnlock[] = new boolean[5];
	
	float BGy[]={0,0,-.4f,-4f,-.25f};
	
	Power mPower;
	Tile mTile[];
	Player mPlayer;
	Gilhari mGilhari[];
	Chidia mChidia[];
	ChakraTrow mChakraTrow[];
	Sudarshan mSudarshan[];
	Villain mVillain[];
	Animation mAnim[];
	
	InApp mInApp;
	
	FPSCounter mFPSCounter = new FPSCounter(); 
	
	private Handler handler = new Handler() {public void handleMessage(Message msg) {mStart.adView.setVisibility(msg.what);}};
//	private Handler handler2 = new Handler() {public void handleMessage(Message msg) {mStart.adHouse.setVisibility(msg.what);}};//AdHouse
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
			mInApp = new InApp();
			mInApp.onCreate();
			mTex_Char = new SimplePlane[2];
			mTex_Char[0] =  add("thrower.png");
			mTex_Char[1] = addBitmap(FlipHorizontal(mTex_Char[0].getBitmap()));
			
			mTex_OptiontBtn = add("option.png");
			mTex_Play = add("play.png");
			mTex_Splash = add("splash.png");
			mTex_NewScore = add("UI/new-high_score.png");
			mTex_PBTN = add("UI/pause_btn.png");
			mTex_Power = add("shild_btn.png");
			mTex_Glow = add("preview_glow.png");
			mTex_Help = add("UI/how2play.jpg");
			mTex_Aboutus = add("UI/aboutus.png");
			mTex_BuyPower = add("UI/buy_power.png");
			mTex_how2play = add("UI/hoe2play.png");
			mTex_Option = add("UI/option.png");
			
			mTex_Music = new SimplePlane[3];
			mTex_Music[0] = add("UI/music.png");
			mTex_Music[1] = add("UI/music_off.png");
			mTex_Music[2] = add("UI/music_on.png");
			
			mTex_Sound = new SimplePlane[3];
			mTex_Sound[0] = add("UI/sound.png");
			mTex_Sound[1] = add("UI/sound_off.png");
			mTex_Sound[2] = add("UI/sound_on.png");
			
			mTex_MRotate = addRotate("monkey_spark.png");
			mTex_MRoket = add("rocket2.png");
			
			mTex_Pause = add("UI/game-paused.png");
			mTex_continue = add("UI/continue.png");
			mTex_menu = add("UI/menu.png");
			mTex_score = add("UI/score.png");
			mTex_Achiev = add("UI/achievement.png");
			mTex_Bestscore  = add("UI/bestscore_text.png");
			mTex_fb  = add("UI/fb.png");
			mTex_gameover  = add("UI/game-over_text.png");
			mTex_highscore  = add("UI/highscore_text.png");
			mTex_join  = add("UI/join.png");
			mTex_JumpAgain  = add("UI/jump-again.png");
			mTex_Leader  = add("UI/lether.png");
			mTex_More  = add("UI/more.png");
			mTex_Power_up = add("UI/power_up.png");
			mTex_ratus = add("UI/ratus.png");
			
			
			mTex_AnyPur = add("UI/any_purches.png");
			mTex_BuyBar = add("UI/buy_bar.png");
			mTex_BuyBtn = add("UI/buy_btn.png");
			mTex_free = add("UI/free-games.png");
			
			mTex_CBG = add("UI/coman_bg.png");
			mTex_About = add("UI/about_text.png");
			mTex_Back = new SimplePlane[2];
			mTex_Back[0] = add("UI/back_btn.png");
			mTex_Back[1] = add("UI/back.png");
			
			mTex_Logo = add("hututugames.png");
			mTex_BG = add("sky.png");
			mTex_SBar = add("UI/score_bar.png");
			mTex_kapde = add("kapde.png");
			
			mTex_Tile = new SimplePlane[2];
			mTex_Tile[0] =  add("0.png");
			mTex_Tile[1] =  add("1.png");
			
			mTex_Shild = new SimplePlane[2];
			mTex_Shild[0] =  add("shiled1.png");
			mTex_Shild[1] =  addRotate("shiled0.png");
			
			mTex_GameBG = new SimplePlane[5];
			for(int i =0;i<mTex_GameBG.length;i++)
				mTex_GameBG[i] =  add("bg"+i+".png");
			
			mTex_Partical = new SimplePlane[5];
			mTex_Partical[0] =  addRotate("partical.png");
			mTex_Partical[1] =  addRotate("fur.png");
			mTex_Partical[2] =  addRotate("feather.png");
			mTex_Partical[3] =  addRotate("bomb0.png");
			mTex_Partical[4] =  addRotate("bolcony_smoke.png");
			
			mTex_Bomb = new SimplePlane[2];
			mTex_Bomb[0] = addRotate("star.png");
			mTex_Bomb[1] =  add("bomb1.png");
			
			mTex_MJump = new SimplePlane[2];
			mTex_MJump[0] =  add("monkey_jump.png");
			mTex_MJump[1] = addBitmap(FlipHorizontal(mTex_MJump[0].getBitmap()));
			
			
			mTex_MFall = new SimplePlane[2][6];
			Bitmap b = LoadImgfromAsset("monkey_fall0.png");
			for(int i =0;i<mTex_MFall[0].length;i++){
				mTex_MFall[0][i] = addBitmap(Bitmap.createBitmap(b, (i%4)*b.getWidth()/4,(i/4)*b.getHeight()/2,b.getWidth()/4, b.getHeight()/2, null, true));
				mTex_MFall[1][i] = addBitmap(FlipHorizontal(mTex_MFall[0][i].getBitmap()));
			}
			b.recycle();
			mTex_MBird = new SimplePlane[7];
			b = LoadImgfromAsset("monkey_bird0.png");
			for(int i =0;i<mTex_MBird.length;i++){
				mTex_MBird[i] = addBitmap(Bitmap.createBitmap(b, (i%2)*b.getWidth()/2,(i/2)*b.getHeight()/4,b.getWidth()/2, b.getHeight()/4, null, true));
			}
			
			
			b.recycle();
			mTex_MGilhari = new SimplePlane[2][13];
			b = LoadImgfromAsset("squireel_monkey0.png");
			for(int i =0;i<mTex_MGilhari[0].length;i++){
				mTex_MGilhari[0][i] = addBitmap(Bitmap.createBitmap(b, (i%4)*b.getWidth()/4,(i/4)*b.getHeight()/4,b.getWidth()/4, b.getHeight()/4, null, true));
				mTex_MGilhari[1][i] = addBitmap(FlipHorizontal(mTex_MGilhari[0][i].getBitmap())); 
			}
			
			b.recycle();
			mTex_Monkey = new SimplePlane[2][14];
			b = LoadImgfromAsset("monkey0.png");
			for(int i =0;i<mTex_Monkey[0].length;i++){
				mTex_Monkey[0][i] = addBitmap(Bitmap.createBitmap(b, (i%4)*b.getWidth()/4,(i/4)*b.getHeight()/4,b.getWidth()/4, b.getHeight()/4, null, true));
				mTex_Monkey[1][i] = addBitmap(FlipHorizontal(mTex_Monkey[0][i].getBitmap())); 
			}
			
			
			b.recycle();
			mTex_Villain = new SimplePlane[2][4];
			b = LoadImgfromAsset("villain.png");
			for(int i =0;i<mTex_Villain[0].length;i++){
				mTex_Villain[0][i] = addBitmap(Bitmap.createBitmap(b, i*b.getWidth()/4,0,b.getWidth()/4, b.getHeight(), null, true));
				mTex_Villain[1][i] = addBitmap(FlipHorizontal(mTex_Villain[0][i].getBitmap())); 
			}
			
			b.recycle();
			mTex_Chidiya = new SimplePlane[2][9];
			b = LoadImgfromAsset("bird0.png");
			for(int i =0;i<mTex_Chidiya[0].length;i++){
				mTex_Chidiya[0][i] = addBitmap(Bitmap.createBitmap(b, (i%4)*b.getWidth()/4,(i/4)*b.getHeight()/4,b.getWidth()/4, b.getHeight()/4, null, true));
				mTex_Chidiya[1][i] = addBitmap(FlipHorizontal(mTex_Chidiya[0][i].getBitmap())); 
			}
			mTex_Hardle = new SimplePlane[2][4];
			for(int i =0;i<mTex_Hardle[0].length;i++){
				mTex_Hardle[0][i] = add("h"+i+".png");
				mTex_Hardle[1][i] = addBitmap(FlipHorizontal(mTex_Hardle[0][i].getBitmap()));
			}
			
			b.recycle();
			mTex_Gilhari = new SimplePlane[2][7];
			b = LoadImgfromAsset("squirrel0.png");
			for(int i =0;i<mTex_Gilhari[0].length;i++){
				mTex_Gilhari[0][i] = addBitmap(Bitmap.createBitmap(b, (i%4)*b.getWidth()/4,(i/4)*b.getHeight()/2,b.getWidth()/4, b.getHeight()/2, null, true));
				mTex_Gilhari[1][i] = addBitmap(FlipHorizontal(mTex_Gilhari[0][i].getBitmap())); 
			}
			
			mPlayer = new Player(this);
			mTile = new Tile[5];
			for(int i =0; i<mTile.length;i++)
				mTile[i] = new Tile();
			
			mGilhari = new Gilhari[3];
			for(int i =0; i<mGilhari.length;i++)
				mGilhari[i] = new Gilhari();
			
			mChidia = new Chidia[3];
			for(int i =0; i<mChidia.length;i++)
				mChidia[i] = new Chidia();
			
			mChakraTrow = new ChakraTrow[3];
			for(int i =0; i<mChakraTrow.length;i++)
				mChakraTrow[i] = new ChakraTrow();
			
			mVillain = new Villain[3];
			for(int i =0; i<mVillain.length;i++)
				mVillain[i] = new Villain();
			
			
			mAnim = new Animation[200];
			for(int i =0; i<mAnim.length;i++)
				mAnim[i] = new Animation();
			
			mSudarshan = new Sudarshan[4];
			for(int i =0; i<mSudarshan.length;i++)
				mSudarshan[i] = new Sudarshan();
			
			mPower = new Power();
			load_Font();
		gameReset();
		mPlayer.vx =0.1f;
		}catch(Exception e){}
		
	}
	
	
	void gameReset()
	{
		for(int i =0; i<mTile.length;i++){
			mTile[i].set(1-mTex_Tile[0].width()*.5f, -1+mTex_Tile[0].Height()*i,0);
		}
		for(int i =0; i<mGilhari.length;i++)
			mGilhari[i].set( -10);
		
		for(int i =0; i<mChidia.length;i++)
			mChidia[i].set(0, -10);
		
		for(int i =0; i<mChakraTrow.length;i++)
			mChakraTrow[i].set(0, -10);
		
		for(int i =0; i<mVillain.length;i++)
			mVillain[i].set(0, -10);
		
		for(int i =0; i<mSudarshan.length;i++)
			mSudarshan[i].set(0, -10,0,0,false);
		
		for(int i =0; i<mAnim.length;i++)
			mAnim[i].set(0, -10,0);
		
		BGy[1] = BGy[0] = 0;
		BGy[2] = BGy[3] = -.4f;
		BGy[4] = -.25f;
		mPower.set(0, -10);
		
		
		mPlayer.set(.65f,-.4f);
		
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
			if(!addFree)
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
		
		mFPSCounter.logFrame();
		
		long dt = System.currentTimeMillis() - startTime;
		if (dt < 33){try {Thread.sleep(33 - dt);} catch (InterruptedException e) {e.printStackTrace();}}
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

	SimplePlane addRotate(String ID) {
		SimplePlane SP = null;
		Bitmap b = LoadImgfromAsset(ID);
		try {
			SP = new SimplePlane((b.getWidth() / M.mMaxY),(b.getHeight() / M.mMaxY));
			SP.loadBitmap(b);
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
		mTex_Font = new SimplePlane[13];
		Bitmap b = LoadImgfromAsset("fontstrip.png");
		for (int i = 0; i < mTex_Font.length; i++)
			mTex_Font[i] = addBitmap(Bitmap.createBitmap(b, i * b.getWidth()/ 16, 0, b.getWidth() / 16,b.getHeight(), null, true));
	}
}
