package com.hututu.game.rescuemission;

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
	static Context mContext;
	public static Start mStart;
	long startTime = System.currentTimeMillis();
	int mSel = 0;
	
	private Handler handler0 = new Handler() {public void handleMessage(Message msg) {mStart.adView.setVisibility(msg.what);}};
	private Handler handler1 = new Handler() {public void handleMessage(Message msg) {mStart.adfull.setVisibility(msg.what);}};
	private Handler handler2 = new Handler() {public void handleMessage(Message msg) {mStart.adHouse.setVisibility(msg.what);}};
	
	Truck[]	mTruck;
	Helicopter mHeli;
	Soldier		mSoldier[];
	Soldier		mMissile;
	Tank		mTank;
	Animation	mAni[];
	Animation	mUfo;
	
	long mTimeTaken = System.currentTimeMillis();
	
	float clud_x,mount_x,ston_x,ground_x,road_x;
	float my = -1.5f;
	float mMenu = 0;
	float move = 0;
	float y1 = 0, y2 = 2;
	
	int mLevel;
	int mScore;
	int mHScore;
	int mUnLevel = 1;
	int mPage;
	int mSS;
	int mLSoldier;
	
	SimplePlane[] mTex_Helicopter0,mTex_Tank,mTex_Rays,mTex_ufo_rays;
	SimplePlane[] mTex_Chakri,mTex_PJump,mTex_PetPart,mTex_Truck,mTex_Font,mTex_PDead;
	SimplePlane mTex_BStone,mTex_Cloud,mTex_Ground,mTex_HPupet,mTex_Jumpky,mTex_Arrow;
	SimplePlane mTex_hututugames,mTex_Launcher,mTex_Missile,mTex_Mountain,mTex_Newufo;
	SimplePlane mTex_Ralling,mTex_Road,mTex_Tower,mTex_PupetHalf,mTex_Tree;
	
	SimplePlane mTex_Exit,mTex_Hightbar,mTex_Pointer,mTex_Splash,mTex_Playde,mTex_Ggl;
	SimplePlane	mTex_Leader,mTex_Cntbg,mTex_About,mTex_Soundon,mTex_Soundoff,mTex_FBI;
	SimplePlane mTex_cDown,mTex_cUp,mTex_CSel,mTex_Plays,mTex_LSel,mTex_LBox,mTex_UPA;
	SimplePlane mTex_uparrowS,mTex_Lock,mTex_scrAbt,mTex_GPaused,mTex_Score,mTex_Cont;
	SimplePlane mTex_Retry,mTex_Menu,mTex_Rate,mTex_Lvl,mTex_Next,mTex_Cong,mTex_Save;
	SimplePlane	mTex_More,mTex_Gameover,mTex_bScore,mTex_Like,mTex_Level;
	
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
			mTex_Level				= add("UI/level.png");
//			mTex_Lives				= add("UI/lives.png");
			mTex_Save				= add("UI/savelives.png");
			mTex_Like				= add("UI/like.png");
			mTex_bScore				= add("UI/best_score.png");
			mTex_Gameover			= add("UI/gameover.png");
			mTex_More				= add("UI/more_icon.png");
			mTex_Cong				= add("UI/cong.png");
			mTex_Next				= add("UI/next.png");
			mTex_Lvl				= add("UI/nextlevel.png");
			mTex_Rate				= add("UI/rate.png");
			mTex_Menu				= add("UI/menu.png");
			mTex_Retry				= add("UI/retry.png");
			mTex_Cont				= add("UI/continue.png");
			mTex_Score				= add("UI/view_submit.png");	
			mTex_GPaused			= add("UI/gamepaused.png");
			mTex_scrAbt				= add("UI/about2.png");
			mTex_Lock 				= add("UI/lock.png");
			mTex_uparrowS 			= addRotate("UI/control.png");
			mTex_UPA	 			= addRotate("UI/uparrow.png");
			mTex_LBox 				= add("UI/level_box.png");
			mTex_LSel 				= add("UI/iconselection.png");
			mTex_Plays				= add("UI/playse.png");
			mTex_CSel				= add("UI/controlse.png");
			mTex_cDown				= add("UI/controldown.png");
			mTex_cUp 				= add("UI/controlup.png");
			mTex_FBI	 			= add("UI/facebook.png");
			mTex_Soundon			= add("UI/soundon.png");
			mTex_Soundoff			= add("UI/soundoff.png");
			mTex_About				= add("UI/about.png");
			mTex_Cntbg				= add("UI/controlbg.png");
			mTex_Ggl				= add("UI/google.png");
			mTex_Leader				= add("UI/leaderboard.png");
			mTex_Pointer 			= add("UI/pointer.png");
			mTex_Exit				= add("UI/exit_icon.png");
			mTex_Hightbar			= add("UI/hightbar0.png");
			mTex_Splash 			= add("UI/splash2.jpg");
			mTex_Playde				= add("UI/playde.png");
			
			mTex_Helicopter0		= new SimplePlane[2];
			mTex_Helicopter0[0]		= add("helicopter.png");
			mTex_Helicopter0[1]		= add("helicopter02.png");
//			mTex_helicopter1		= new SimplePlane[2];
//			mTex_helicopter1[0]		= add("helicopter03.png");
//			mTex_helicopter1[1]		= add("helicopter04.png");
			mTex_Tank				= new SimplePlane[3];
			mTex_Tank[0]			= add("tank0.png");
			mTex_Tank[1]			= add("tank1.png");
			mTex_Tank[2]			= add("chakri_stand.png");
			mTex_ufo_rays			= new SimplePlane[2];
			mTex_ufo_rays[0]		= add("ufo_rays.png");
			mTex_ufo_rays[1]		= add("ufo_rays2.png");
			mTex_PJump				= new SimplePlane[2];
			mTex_PJump[0]			= add("puppet.png");
			mTex_PJump[1]			= add("puppet0.png");
			mTex_PetPart			= new SimplePlane[4];
			mTex_PetPart[0]			= add("puppet1.png");
			mTex_PetPart[1]			= add("puppet2.png");
			mTex_PetPart[2]			= add("puppet3.png");
			mTex_PetPart[3]			= add("puppet4.png");
			mTex_Truck				= new SimplePlane[5];
			mTex_Truck[0]			= add("truck0.png");
			mTex_Truck[1]			= add("truck1.png");
			mTex_Truck[2]			= add("truck2.png");
			mTex_Truck[3]			= add("cotton.png");
			mTex_Truck[4]			= add("kante.png");
			mTex_Chakri				= new SimplePlane[2];
			mTex_Chakri[0]			= addRotate("chakri.png");
			mTex_Chakri[1]			= addRotate("chakri_blood.png");
			mTex_Rays				= new SimplePlane[2];
			mTex_Rays[0]			= add("rays.png");
			mTex_Rays[1]			= add("rays2.png");
			mTex_PDead				= new SimplePlane[4];
			mTex_PDead[0]			= add("puppet_dead.png");
			mTex_PDead[1]			= add("puppet_dead2.png");
			mTex_PDead[2]			= add("puppet_dead3.png");
			mTex_PDead[3]			= add("puppet_dead4.png");
			
			mTex_BStone				= add("big_stone.png");
			mTex_Cloud				= addDouble("cloud.png");
			mTex_Ground				= add("ground.png");
			mTex_HPupet				= add("hang_puppet.png");
			mTex_hututugames		= add("hututugames.png");
			mTex_Launcher			= add("launcher.png");
			mTex_Missile			= addRotate("missile.png");
			mTex_Mountain			= addDouble("mountain.png");
			mTex_Newufo				= add("newufo.png");
			
			mTex_Ralling			= add("ralling.png");
			mTex_Road				= add("road.png");
			mTex_Tower				= add("tower.png");
			mTex_PupetHalf			= add("puppet_half.png");
			mTex_Tree				= add("tree.png");
			mTex_Arrow				= add("right0.png");
			mTex_Jumpky				= add("jumpkey.png");
			
			mTruck					= new Truck[5];
			for(int i=0;i<mTruck.length;i++)
				mTruck[i]			= new Truck();
			
			mHeli 					= new Helicopter();
			
			mSoldier					= new Soldier[100];
			for(int i=0;i<mSoldier.length;i++)
				mSoldier[i]			= new Soldier();
			mTank					= new Tank();
			mMissile				= new Soldier();
			mAni					= new Animation[100];
			for(int i=0;i<mAni.length;i++)
				mAni[i]				= new Animation();
			mUfo					= new Animation();
			load_Font();
			gameReset();
		}catch(Exception e){}
		
	}
	
	
	void gameReset()
	{
		clud_x = 1.1f;
		mount_x = 1.1f;
		ground_x = 1.1f;
		road_x = 1.1f;
		ston_x = -.68f;
		for(int i=0;i<mTruck.length;i++){
			mTruck[i].set(-i*1.5f,.01f);
			mTruck[i].type = 0;
		}
		mHeli.set(100);
		for(int i=0;i<mSoldier.length;i++)
			mSoldier[i].set(-100, -100);
		root.Counter =0;
		mTank.set(0, -.45f);
		mMissile.set(-110,100);
		for(int i=0;i<mAni.length;i++)
			mAni[i].set(-100,-100);
		mUfo.set(-100,0);
		mLSoldier = 100;
		setLevel();
		root.c2 =0;
	}
	
	void setLevel()
	{
		float k = 0.045f;
		mLSoldier = mLevel*2+5;
		mSS = 0;
		mTimeTaken = System.currentTimeMillis();
		clud_x 	= 1.1f-mLevel*k;
		mount_x = 1.1f-mLevel*k;
		ground_x= 1.1f-mLevel*k;
		road_x 	= 1.1f-mLevel*k;
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
		if (mStart.adView != null) {
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
	SimplePlane addDouble(String ID)
	{
		SimplePlane SP = null;
		Bitmap b = LoadImgfromAsset(ID);
		try
		{
			SP = new SimplePlane((b.getWidth()/M.mMaxX)*2,(b.getHeight()/M.mMaxY));
			SP.loadBitmap(b);// R.drawable.jay
		}catch(Exception e){}
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
		mTex_Font = new SimplePlane[10];
		Bitmap b = LoadImgfromAsset("fontstrip.png");
		for (int i = 0; i < mTex_Font.length; i++)
			mTex_Font[i] = addBitmap(Bitmap.createBitmap(b, i * b.getWidth()/ mTex_Font.length, 0, b.getWidth() / mTex_Font.length,b.getHeight(), null, true));
	}
}
