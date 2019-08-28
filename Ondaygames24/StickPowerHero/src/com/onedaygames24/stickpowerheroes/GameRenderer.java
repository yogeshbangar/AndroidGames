package com.onedaygames24.stickpowerheroes;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import com.google.android.gms.ads.AdView;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
@SuppressLint("HandlerLeak")
public class GameRenderer implements Renderer 
{
	final Group root;
	static Context mContext;
	public static Start mStart;
	long startTime = System.currentTimeMillis();
	int resumeCounter =0;
	int mSel = 0;
//	InAppActivity mMainActivity;
	
	SimplePlane[]   mTex_Font;
	SimplePlane 	mTex_Logo,mTex_GameBg[],mTex_Block,mTex_Danda,mTex_Player[][],mTex_PlayerFlip[][],mTex_RedDot;
	SimplePlane		mTex_SplashTxt,mTex_Play,mTex_Help,mTex_Sound[],mTex_HeroBtn,mTex_DiamondBtn,mTex_GameOver,mTex_ScoreBox;//mTex_NoAds,
	SimplePlane     mTex_Home,mTex_Leader,mTex_Rate,mTex_Retry,mTex_Share,mTex_ScoreTxt,mTex_Best,mTex_CommonBox,mTex_HeroTxt,mTex_Diamond;
	SimplePlane     mTex_Box[],mTex_ShopTxt,mTex_ShopBlock[],mTex_DiaEnable[],mTex_Perfect,mTex_Blast,mTex_H2Play,mTex_Hand,mTex_Tap,mTex_LetsGo;
	SimplePlane     mtex_GamePause,mTex_PlayIcn;
	
	Block  mBlock[];
	Danda  mDanda[];
	Player mPlayer;
	Animation mNumberAni,mPerfectAni,mDiamond[];
	int mNoTaken=0,mBgNo=0;
	boolean isDiaEnable=true;
	int mBlockSel=0,mHilanaCnt=0,mWaitCnt=0,mTotalDiamond;
	int mScore,mBestScore,mHelpCnt=0;
	float mHilaVal=0;
	final float VX=-.076f;
	float BG[] = new float[2];
	private Handler handlerAdBanner = new Handler() {public void handleMessage(Message msg) {mStart.adBanner.setVisibility(msg.what);}};
//	private Handler handlerAdRect   = new Handler() {public void handleMessage(Message msg) {mStart.adRect.setVisibility(msg.what);}};
	
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
			int i;
//			mMainActivity = new InAppActivity();
//			mMainActivity.onCreate();
			System.gc();
			mTex_Logo   = add("onedaygames24.png");
			mTex_GameBg = new SimplePlane[6];
			for(i=0;i<mTex_GameBg.length;i++)
			{
				mTex_GameBg[i] = add("bg/"+i+".png");
			}
			mTex_Block  =  add("block.png");
			mTex_Danda  =  addRotate("danda.png");
			mTex_Player     =  new SimplePlane[6][4];
			mTex_PlayerFlip =  new SimplePlane[6][4];
			for(i=0;i<mTex_Player.length;i++)
			{
			  for(int k=0;k<mTex_Player[0].length;k++)
			  {
			    mTex_Player[i][k]    = addRotate("player/"+(i+1)+"/"+k+".png");
			    Bitmap b = LoadImgfromAsset("player/"+(i+1)+"/"+k+".png");
			    mTex_PlayerFlip[i][k]= addBitmapR(FlipHorizontal(b,1));
			    b.recycle();
			  }
			}
			mTex_RedDot = add("dot.png");
			mTex_Blast  = add("diamond_blast.png");
			LoadUi();
			loadFont();
			LoadGameObj();
		    M.loadSound(mContext);
		}catch(Exception e){}
	}
	void LoadUi()
	{
		mTex_SplashTxt  = add("ui/name.png");
		mTex_Play       = add("ui/play_btn.png");
//		mTex_NoAds      = add("ui/noads.png");
		mTex_Help       = add("ui/help_btn.png");
		mTex_Sound      = new SimplePlane[2];
		mTex_Sound[0]   = add("ui/sound.png");
		mTex_Sound[1]   = add("ui/sound_off.png");
		mTex_HeroBtn    = add("ui/hero_btn.png");
		mTex_DiamondBtn = add("ui/diamond_btn.png");
		mTex_GameOver   = add("ui/gameover.png");
		mTex_ScoreBox   = add("ui/scorebox.png");
		mTex_Home       = add("ui/home_btn.png");	
		mTex_Leader     = add("ui/lether.png");
		mTex_Rate       = add("ui/rate.png");
		mTex_Retry      = add("ui/retray.png");
		mTex_Share      = add("ui/share_btn.png");
		mTex_ScoreTxt   = add("ui/score_text.png");
		mTex_Best       = add("ui/best_text.png");
		mTex_CommonBox  = add("ui/comman_box.png");
		mTex_HeroTxt    = add("ui/heroes_text.png");
		mTex_Diamond    = add("ui/diamond.png");
		mTex_Box        = new SimplePlane[2];
		mTex_Box[0]     = add("ui/box1.png"); 
		mTex_Box[1]     = add("ui/box2.png");
		mTex_ShopTxt    = add("ui/shop_text.png");
		mTex_ShopBlock  = new SimplePlane[4];
		for(int i=0;i<mTex_ShopBlock.length;i++)
		 mTex_ShopBlock[i] = add("ui/block"+i+".png");
		
		mTex_DiaEnable = new SimplePlane[2];
		mTex_DiaEnable[0] = add("ui/disablebar.png");
		mTex_DiaEnable[1] = add("ui/enadlebar.png");
		mTex_Perfect      = add("ui/perfect.png"); 
		mTex_H2Play       = add("ui/how-to-play.png");
		mTex_Hand	      = add("ui/tapfinger.png");
		mTex_Tap	      = add("ui/tapfinger2.png");
		mTex_LetsGo       = add("ui/letsgo.png");
		mtex_GamePause    = add("ui/gamepaused.png");
		mTex_PlayIcn      = add("ui/play.png");
 	}
	void loadFont(){
		mTex_Font   = new SimplePlane[10];
		Bitmap b    = LoadImgfromAsset("font.png");
		for(int i=0;i< mTex_Font.length; i++)
		{
		   mTex_Font[i] = addBitmap(Bitmap.createBitmap(b,(i*b.getWidth()/16),0,b.getWidth()/16,b.getHeight(),null,true));
		}
		b.recycle();
	}
	void LoadGameObj()
	{
		mBlock = new Block[2];
		for(int i=0;i<mBlock.length;i++)
		{
			mBlock[i]=new Block(this);
		}
		mDanda = new Danda[2];
		for(int i=0;i<mBlock.length;i++)
		{
			mDanda[i]=new Danda(this);
		}
		mPlayer     = new Player();
		mNumberAni  = new Animation();
		mPerfectAni = new Animation();
		mDiamond    = new Animation[4];
		for(int i=0;i<mDiamond.length;i++)
		  mDiamond[i] = new Animation();	
	}
	void GameReset()
	{
		
		mHelpCnt=mNoTaken=mScore=0;
		mBgNo++;
		mBgNo%=mTex_GameBg.length;
		for(int i=0;i<BG.length;i++)
			BG[i]=0+i*mTex_GameBg[0].width();
		Reset();
		for(int i=0;i<mBlock.length;i++)
		{
			mBlock[i].Set(-.7f+i*1.2f,1,-.7f);
			mBlock[0].isDisCmp = mBlock[0].isStop=true;
		}
		mPlayer.set((mBlock[0].x+mBlock[0].width)-.06f);
		mPlayer.isUnLock[0] =true;
		if(mPlayer.mHeroNo==3)
		  mPlayer.y+=.01f;
		if(mPlayer.mHeroNo==5)
		  mPlayer.y+=.03f;
		mHilaVal=mBlockSel=0;
		mHilanaCnt=100;
		mWaitCnt=0;
		for(int i=0;i<mDiamond.length;i++)
		   mDiamond[i].SetDia(100,100,0);
		if(root.AdCnt %3 == 2)
			GameRenderer.mStart.LoadHandler();
		if(M.setValue && M.GameScreen == M.GAMEPLAY)
		   M.BGPlay(mContext,R.drawable.bg);
		
	}
	void Reset()
	{
		for(int i=0;i<mBlock.length;i++)
		{
			mBlock[i].reset();
		}
		for(int i=0;i<mDanda.length;i++)
		{
		  mDanda[i].reset();
		}
		mPlayer.reset();
	}
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
  	    gl.glClearColor(0,0,0,1);
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glEnable(GL10.GL_REPEAT);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
	
	}
	public void onDrawFrame(GL10 gl) 
	{
		long dt = System.currentTimeMillis()-startTime;
		if (dt < 33)
			try {
				Thread.sleep(33 - dt);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		startTime = System.currentTimeMillis();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
 	    gl.glClearColor(0,0,0,1);
		gl.glLoadIdentity();
		root.draw(gl);
		resumeCounter++;
		if(resumeCounter>50)
		{
			 if(mStart.adBanner!=null)
	 		 {
				if(!mStart.addFree && (M.GameScreen != M.GAMEMENU))
				{
					int inv=mStart.adBanner.getVisibility();
					if(inv==AdView.GONE){try{handlerAdBanner.sendEmptyMessage(AdView.VISIBLE);}catch(Exception e){}}
				}
				else
				{
					int inv=mStart.adBanner.getVisibility();
					if(inv==AdView.VISIBLE){try{handlerAdBanner.sendEmptyMessage(AdView.GONE);}catch(Exception e){}}
				}
			 }
			// Adload
//			if (mStart.adRect != null)  
//			{
//				if(!mStart.addFree && M.GameScreen == M.GAMELOADING)
//				{
//					int inv = mStart.adRect.getVisibility();
//					if (inv == AdView.GONE) {try {handlerAdRect.sendEmptyMessage(AdView.VISIBLE);} catch (Exception e) {}}
//				} 
//				else
//				{
//					int inv = mStart.adRect.getVisibility();
//					if (inv == AdView.VISIBLE) {try {handlerAdRect.sendEmptyMessage(AdView.GONE);} catch (Exception e) {}
//					}
//				}
//			}
			if(mStart.addFree)
			{
				int inv = mStart.adBanner.getVisibility();
				if(inv==AdView.VISIBLE){try{handlerAdBanner.sendEmptyMessage(AdView.GONE);}catch(Exception e){}}
//				inv = mStart.adRect.getVisibility();
//				if (inv == AdView.VISIBLE) {try {handlerAdRect.sendEmptyMessage(AdView.GONE);} catch (Exception e) {}}
			}
		}
		if(resumeCounter>500000)
			resumeCounter=0;
		/*AdHouse*/
		
	}
//	double deltaTime = (SystemClock.elapsedRealtime()-startTime)/1000d;
//	startTime = SystemClock.elapsedRealtime();
//	if(deltaTime<60)
//	{
//		try{
//			Thread.sleep((int)deltaTime);
//		 }catch (InterruptedException e){e.printStackTrace();}
//	}
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
	SimplePlane addBig (String ID)
	{
		SimplePlane SP = null;
		Bitmap b = LoadImgfromAsset(ID);
		try
		{
			SP = new SimplePlane(((b.getWidth()+20f)/(M.mMaxX)),(b.getHeight()/(M.mMaxY)));
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
	SimplePlane addDouble(String ID)
	{
		SimplePlane SP = null;
		Bitmap b = LoadImgfromAsset(ID);
		try
		{
			SP = new SimplePlane((b.getWidth()/(.5f*M.mMaxX)),(b.getHeight()/(.65f*M.mMaxY)));
			SP.loadBitmap(b);// R.drawable.jay
		}catch(Exception e){}
		return SP;
	}
	public static SimplePlane addBitmap (Bitmap b)
	{
		SimplePlane SP = null;
		try
		{
			SP = new SimplePlane((b.getWidth()/M.mMaxX),(b.getHeight()/M.mMaxY));
			SP.loadBitmap(b);// R.drawable.jay
		}catch(Exception e){}
		return SP;
	}
	SimplePlane addBitmapR(Bitmap b)
	{
		SimplePlane SP = null;
		try
		{
			SP = new SimplePlane((b.getWidth()/M.mMaxY),(b.getHeight()/M.mMaxY));
			SP.loadBitmap(b);// R.drawable.jay
		}catch(Exception e){}
		return SP;
	}
	public static Bitmap LoadImgfromAsset(String ID)
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
	Bitmap FlipHorizontal(Bitmap bitmapOrg,int type)
	{
		Matrix matrix = new Matrix();
		if(type ==0) // Verticle
		 matrix.postScale(1f,-1f);
		if(type ==1) //Horizontal
	  	 matrix.postScale(-1f, 1f);
		matrix.postRotate(0);
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0,bitmapOrg.getWidth(), bitmapOrg.getHeight(), matrix, true);
		return resizedBitmap;
	}

}

