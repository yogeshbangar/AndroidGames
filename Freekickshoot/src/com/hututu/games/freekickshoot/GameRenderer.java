package com.hututu.games.freekickshoot;
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
	
	SimplePlane[] mTex_BG,mTex_Net,mTex_Opp,mTex_Player[],mTex_Ball,mTex_Part,mTex_Font,mTex_Dot;
	SimplePlane mTex_logo,mTex_Shadow,mTex_Goal,mTex_Level, mTex_Arcade,mTex_Control, mTex_Quick, mTex_Score;
	SimplePlane mTex_Splash,mTex_Turnament,mTex_Reward,mTex_RIcn, mTex_RTex,mTex_Button, mTex_Over,mTex_Rtry;
	SimplePlane mTex_Off,mTex_On,mTex_Slash,mTex_SIcon,mTex_SText, mTex_Menu,mTex_MIcn,mTex_AText,mTex_AllBG;
	SimplePlane mTex_Join,mTex_Later,mTex_CText,mTex_CIcn,mTex_JTex,mTex_Back,mTex_PText,mTex_Icon,mTex_PIcn;
	SimplePlane mTex_ScorBox,mTex_BallText,mTex_Dewar,mTex_ReIcn,mTex_levelOff,mTex_Bord;
	
	boolean Goal = false;
	boolean addFree = false;
	boolean SingUpadate = false;
	boolean Achi[] = new boolean[5];
	
	int Level = 1;
	int total = 1;
	int mBall = 3;
	int mType = 0;
	int mBest[] = new int[3];
	int Deewar = 0;
	int mULvl = 1;
	
	float DeewarX = 0;
	
	Player mPlayer;
	Keeper	mKeeper;
	Animation mAni[];
	
	
	private Handler handler = new Handler() {public void handleMessage(Message msg) {mStart.adView.setVisibility(msg.what);}};
	public GameRenderer(Context context) {
		mContext = context;
		mStart	= (Start)mContext;
		root = new Group(this);
		init();
	}
	void init()
	{
		try
		{
			mTex_Dot		= new SimplePlane[2];
			mTex_Dot[0]		= add("dot0.png");
			mTex_Dot[1]		= add("dot1.png");
			mTex_levelOff	= add("lock.png");
			mTex_BallText	= add("balls_text.png");
			mTex_ScorBox	= add("big_score_box.png");
			mTex_Rtry		= add("retry_text.png");
			mTex_ReIcn		= add("retry_icon.png");
			mTex_Over		= add("gameover_text.png");
			mTex_RIcn		= add("resume_icon.png");
			mTex_RTex		= add("resume_text.png");
			mTex_PText		= add("paused_text.png");
			mTex_PIcn		= add("pause_icon.png");
			mTex_Off		= add("off_text.png");
			mTex_On			= add("on_text.png");
			mTex_Slash		= add("slash.png");
			mTex_SIcon		= add("sound_icon.png");
			mTex_SText		= add("sound_text.png");
			mTex_Button		= add("small_box.png");
			mTex_Menu		= add("menu_text.png");
			mTex_MIcn		= add("menu_icon.png");
			mTex_AText		= add("about.png");
			mTex_Bord		= add("big_pop_box.png");
			mTex_AllBG		= add("game_bg.jpg");
			mTex_Join		= add("join_text.png");
			mTex_Later		= add("later_text.png");
			mTex_CText		= add("control_text.png");
			mTex_CIcn		= add("control_icon-94.png");
			mTex_Arcade		= add("arcade_button.png");
			mTex_Control	= add("control_icon.png");
			mTex_Quick		= add("quick_button.png");
			mTex_Score		= add("score_icon.png");
			mTex_Splash		= add("splash_bg.jpg");
			mTex_Turnament	= add("turnament_button.png");
			mTex_Reward		= add("score_icon-69.png");
			mTex_JTex		= add("challenge_text.png");
			mTex_Back		= add("back_button.png");
			mTex_logo		= add("hututugames.png");
			mTex_Goal		= add("goal.png");
			mTex_Level		= add("level_text.png");
			mTex_BG			= new SimplePlane[3];
			mTex_BG[0]		= add("playbg_left.png");
			mTex_BG[1]		= add("playbg_center.png");
			mTex_BG[2]		= add("playbg_right.png");
			mTex_Icon		= add("blank_icon.png");
			mTex_Net		= new SimplePlane[3];
			mTex_Net[0]		= add("net0.png");
			mTex_Net[1]		= add("net1.png");
			mTex_Net[2]		= add("net2.png");
			mTex_Part		= new SimplePlane[11];
			for(int i=0;i<mTex_Part.length;i++)
				mTex_Part[i]= add("part/"+i+".png");
			Bitmap bit 		= LoadImgfromAsset("oppo.png"); 
			mTex_Opp		= new SimplePlane[10];
			mTex_Opp[0]		= addBitmap(Bitmap.createBitmap(bit, 0*bit.getWidth()/8, 0,bit.getWidth()/8, bit.getHeight(), null, true));
			mTex_Opp[1]		= addBitmap(Bitmap.createBitmap(bit, 2*bit.getWidth()/8, 0,bit.getWidth()/8, bit.getHeight(), null, true));
			
			mTex_Opp[2]		= addBitmap(Bitmap.createBitmap(bit, 1*bit.getWidth()/8, 0,bit.getWidth()/8, bit.getHeight(), null, true));
			mTex_Opp[6]		= addBitmap(FlipHorizontal(mTex_Opp[2].getBitmap()));
			
			mTex_Opp[3]		= addBitmap(Bitmap.createBitmap(bit, 3*bit.getWidth()/8, 0,bit.getWidth()/8, bit.getHeight(), null, true));
			mTex_Opp[4]		= addBitmap(Bitmap.createBitmap(bit, 4*bit.getWidth()/8, 0,bit.getWidth()/8, bit.getHeight(), null, true));
			mTex_Opp[5]		= addBitmap(Bitmap.createBitmap(bit, 5*bit.getWidth()/8, 0,bit.getWidth()/8, bit.getHeight(), null, true));
			
			mTex_Opp[7]		= addBitmap(FlipHorizontal(mTex_Opp[3].getBitmap()));
			mTex_Opp[8]		= addBitmap(FlipHorizontal(mTex_Opp[4].getBitmap()));
			mTex_Opp[9]		= addBitmap(FlipHorizontal(mTex_Opp[5].getBitmap()));
			
			mTex_Shadow		= addBitmap(Bitmap.createBitmap(bit, 6*bit.getWidth()/8, 0,bit.getWidth()/8, bit.getHeight(), null, true));
			mTex_Dewar		= addBitmap(Bitmap.createBitmap(bit, 7*bit.getWidth()/8, 0,bit.getWidth()/8, bit.getHeight(), null, true));
			
			
			bit.recycle();
			bit		= LoadImgfromAsset("front_run.png");
			mTex_Player		= new SimplePlane[3][];
			mTex_Player[0]	= new SimplePlane[7];
			for(int i=0;i<mTex_Player[0].length;i++)
				mTex_Player[0][i]	= addBitmap(Bitmap.createBitmap(bit, i*bit.getWidth()/8, 0,bit.getWidth()/8, bit.getHeight(), null, true));
			
			bit.recycle();
			bit		= LoadImgfromAsset("left_run.png");
			mTex_Player[1]	= new SimplePlane[8];
			mTex_Player[2]	= new SimplePlane[8];
			for(int i=0;i<mTex_Player[1].length;i++){
				mTex_Player[1][i]	= addBitmap(Bitmap.createBitmap(bit, i*bit.getWidth()/8, 0,bit.getWidth()/8, bit.getHeight(), null, true));
				mTex_Player[2][i]	= addBitmap(FlipHorizontal(mTex_Player[1][i].getBitmap()));
			}
			bit.recycle();
			bit		= LoadImgfromAsset("football_sprite.png");
			mTex_Ball	= new SimplePlane[8];
			for(int i=0;i<mTex_Ball.length;i++){
				mTex_Ball[i]	= addBitmap(Bitmap.createBitmap(bit, i*bit.getWidth()/mTex_Ball.length, 0,bit.getWidth()/mTex_Ball.length, bit.getHeight(), null, true));
			}
			load_Font();
			mPlayer	= new Player(this);
			mKeeper	= new Keeper(this);
			mAni	= new Animation[50];
			for(int i=0;i<mAni.length;i++)
				mAni[i]	= new Animation();
//			gameReset();
		}catch(Exception e){}
		
	}
	
	
	void gameReset(final int type,final int _Level)
	{
		mType = type;
		Level = _Level;
		 
		Goal = true;
		reset();
		mPlayer.pmove=false;
		for(int i=0;i<mAni.length;i++)
			mAni[i].no = 0;
		if (ads % 1 == 0) {
			try {
				adsHandler.sendEmptyMessage(0);
			} catch (Exception e) {
			}
		}
	}
	int ads =0;
	Handler adsHandler = new Handler() {
		public void handleMessage(Message msg) {
			mStart.loadInter();
		}
	};

	void reset() {
		if (Goal) {
			mPlayer.bgx = M.mRand.nextFloat() - .5f;
			mKeeper.reset(mPlayer.bgx, .28f);
			mPlayer.reset(M.mRand.nextFloat() - .7f, -.83f);
			
			Deewar = M.mRand.nextInt(3);
			if (Deewar != 0) {
				Deewar += 3;
				DeewarX = Math.abs(mPlayer.px[0] - mKeeper.x) / 2;
				DeewarX += (mPlayer.px[0] < mKeeper.x ? mPlayer.px[0] : mKeeper.x);
				DeewarX -= (Deewar/2f)*.05f;
			}
			Goal = false;
			if (mType == 0)
				mBall = 1000;
			if (mType == 1)
				mBall = 3;
			if (mType == 2)
				mBall = 1;
			Level++;
			total++;
			if(mULvl < Level&& mType == 1)
				mULvl = Level;
			if(Level%10==0)
				GameRenderer.mStart.show();
			ChackAchiv();
		} else {
			mKeeper.reset(mPlayer.bgx, .28f);
			mPlayer.reset(mPlayer.px[0], -.83f);
			mBall--;
			if (mBall == 0 && mType != 0) {
				root.gameOver();
			}

		}
	}
	void ChackAchiv()
	{
		if(mBest[mType]<Level){
			mBest[mType]=Level;
			GameRenderer.mStart.Submitscore(M.scoreID[mType], mBest[mType]);
		}
		if(Level >5 && !Achi[0])
		{
			GameRenderer.mStart.UnlockAchievement(M.achiment[0]);
			Achi[0] = true;
		}
		if(Level >15 && !Achi[1])
		{
			GameRenderer.mStart.UnlockAchievement(M.achiment[1]);
			Achi[1] = true;
		}
		if(Level >50 && !Achi[2])
		{
			GameRenderer.mStart.UnlockAchievement(M.achiment[2]);
			Achi[2] = true;
		}
		if(total >200 && !Achi[3])
		{
			GameRenderer.mStart.UnlockAchievement(M.achiment[3]);
			Achi[3] = true;
		}
		if(total >999 && !Achi[4])
		{
			GameRenderer.mStart.UnlockAchievement(M.achiment[4]);
			Achi[4] = true;
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
	public void onDrawFrame(GL10 gl) 
	{
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		root.draw(gl);
		if(mStart.adView!=null)
		{
			if(M.GameScreen != M.GAMEMENU)
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
		 System.out.println("resizeImg======== newWidth ["+newWidth+"] newHeight ["+newHeight+"]");
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
		mTex_Font = new SimplePlane[10];
		Bitmap b = LoadImgfromAsset("fontstrip.png");
		for (int i = 0; i < mTex_Font.length; i++)
			mTex_Font[i] = addBitmap(Bitmap.createBitmap(b, i * b.getWidth() / 16, 0, b.getWidth() / 16, b.getHeight(), null, true));
		
	}
}
