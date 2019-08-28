package com.hututu.game.ScarecrowvsBirds;

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
	Context mContext;
	public static Start mStart;
	public Arrow mArrow,mBow;
//	public Bow   ;
	public Bird mBird[],mBlast[],mPankh[];
	public Power mPower,mArrowPower[],mPowerActive;
	public menu  mControl,mSetting;
	public Point mPoint[],mTarget;
	int resumeCounter= 0,mSpeedCount=0;
	int HighScore= 0,mScore=0,mNoArrow;
	int mSel= 0,mLevel=1;
	
	long startTime = System.currentTimeMillis();
	SimplePlane[][] mTex_Bird;
	SimplePlane[] 	mTex_Font,mTex_Bow,mTex_BirdBlast,mTex_PankhL,mTex_PankhR;
	SimplePlane     mTex_ArrowPower[],mTex_BlastPower,mTex_SlowPower,mTex_GantaPower[],mTex_ThronsPower,mTex_ThronsBig; 
	SimplePlane 	mTex_Logo,mTex_Splash,mTex_GameBg,mTex_Arrow;
	SimplePlane 	mTex_PopUp,mTex_PauseTitle,mTex_HomeBtn,mTex_ContinueBtn,mTex_HighScoreBtn,mTex_RetryBtn,mTex_BtnSelect;
	SimplePlane 	mTex_PlayBtn[],mTex_Control,mTex_ArrowBtn,mTex_Iconbar,mTex_RateUs,mTex_Share,mTex_hututuicn,mTex_Select,mTex_SoundIcn[],mTex_ScoreBar,mTex_ShootBar,mTex_TargetTxt;
	SimplePlane 	mTex_HelpIcn,mTex_AbtUsIcn,mTex_HelpTitle,mTex_AbtTitle,mTex_AbtTxt,mTex_BackBtn,mTex_Trans[],mTex_PauseIcn,mTex_GameOverTxt,mTex_TargetBox,mTex_HelpTxt;
	SimplePlane   mTex_Pointer, mTex_Hightbar,mTex_Skip; //AdHouse
	private Handler handler = new Handler() {public void handleMessage(Message msg) {mStart.adView.setVisibility(msg.what);}};
	private Handler handler2 = new Handler() {public void handleMessage(Message msg) {mStart.adHouse.setVisibility(msg.what);}};//AdHouse
	public GameRenderer(Context context) 
	{
		mContext = context;
		mStart	= (Start)mContext;
		root = new Group(this);
		init();
		M.GameScreen = M.GAMELOGO;
	}
	void init()
	{
		int k=0;
		try
		{			
			mTex_Pointer = add("pointer.png");//AdHouse
			mTex_Hightbar = add("hightbar0.png");//AdHouse
			mTex_Skip = add("exit_icon.png");//AdHouse

			loadUI();
			mTex_Logo 			= add("hututugames.png");
		    mTex_GameBg         = add("bg.jpg");
		    mTex_Arrow          = addRotate("arrow.png");
		    mTex_PauseTitle     = add("ui/game_paused_text.png");
		    mTex_PauseIcn       = add("ui/pause_icon.png");
		    mTex_ShootBar       = add("ui/shoot.png");
		    mTex_ScoreBar       = add("ui/score.png");
		    mTex_ArrowPower     = new SimplePlane[2];
		    mTex_ArrowPower[0]  = add("power/shot0.png"); 
		    mTex_ArrowPower[1]  = add("power/shot1.png");
		    mTex_BlastPower     = add("power/blast.png");  
		    mTex_SlowPower      = add("power/speed_slow.png");
		    mTex_GantaPower     = new SimplePlane[4];
		    for(int i=0;i<3;i++)
		    mTex_GantaPower[i]  = add("power/ring"+i+".png");
		    mTex_GantaPower[3]  = add("power/ring1.png");
		    mTex_ThronsPower    = add("power/throns.png");
		    
		    mTex_ThronsBig      = add("thronsbig.png");
		    mTex_Bow            = new SimplePlane[12];
		    Bitmap b            = LoadImgfromAsset("bow.png");
		    for(int i=0;i<2 && i< mTex_Bow.length;i++)
		    {
		       for(int j=0;j<6 && j< mTex_Bow.length;j++)
		    	{
		    	  mTex_Bow[k] = addBitmapRotate(Bitmap.createBitmap(b, j*b.getWidth()/6,i*b.getHeight()/2,b.getWidth()/6,b.getHeight()/2,null, true));
				  k++;
		    	}
		    }
		    Bitmap Bird[] = new Bitmap[4];
		    for(int i=0;i<Bird.length;i++)
	    	Bird[i] = LoadImgfromAsset("bird/"+i+".png");
		    mTex_Bird     = new SimplePlane[4][6];
		    for(int n=0;n<Bird.length;n++)
		    {
	    	  for(int j=0;j< mTex_Bird[0].length;j++)
	    		 mTex_Bird[n][j] = addBitmapRotate(Bitmap.createBitmap(Bird[n],j*Bird[n].getWidth()/6,0,Bird[n].getWidth()/6,Bird[n].getHeight(),null, true));
		    }
		    mTex_BirdBlast = new SimplePlane[4];
		    for(int i=0;i<mTex_BirdBlast.length;i++)
		    {
		    	mTex_BirdBlast[i] = addRotate("bird/fall"+i+".png"); 
		    }
		    mTex_PankhL = new SimplePlane[4];
		    mTex_PankhR = new SimplePlane[4];
		    for(int i=0;i<mTex_PankhL.length;i++)
		    {
		    	mTex_PankhL[i] = addRotate("bird/l"+i+".png");
		    	mTex_PankhR[i] = addRotate("bird/r"+i+".png");
		    }
		    mTex_PankhL[3] = addRotate("bird/l"+1+".png");
	    	mTex_PankhR[3] = addRotate("bird/r"+1+".png");
		   LoadFont();
			mBow    = new Arrow(this);
			mArrow  = new Arrow(this);
			mBird   = new Bird[5];
			mBlast  = new Bird[mBird.length];
			mPoint  = new Point[mBird.length];
			mTarget = new Point();
			mPankh  = new Bird[mBird.length];  
			for(int i=0;i<mBird.length;i++)
			{
		  	  mBird[i]   = new Bird();
		  	  mBlast[i]  = new Bird();
		  	  mPankh[i]  = new Bird();
		  	  mPoint[i]  = new Point();   
			}
			mPower       = new Power();
			mArrowPower  = new Power[8];
			for(int i=0;i<mArrowPower.length;i++)
		 	  mArrowPower[i] = new Power();
			mPowerActive  = new Power();
			mControl     = new menu();
			mSetting     = new menu();
			gameReset();
		}catch(Exception e){}
		
	}
	void loadUI()
	{
		mTex_Splash			= add("splash.jpg"); 
		mTex_PlayBtn        = new SimplePlane[2];
		mTex_PlayBtn[0]     = add("ui/play_icon_de.png");
		mTex_PlayBtn[1]     = add("ui/play_icon_se.png");
		mTex_Control        = addRotate("ui/control.png");
		mTex_ArrowBtn       = addRotate("ui/up_down_arrow.png");
		mTex_Iconbar        = add("ui/iconbar.png"); 
		mTex_RateUs         = add("ui/hututu_icon.png"); 
		mTex_Share          = add("ui/share_icon.png");
		mTex_hututuicn      = add("ui/hututu_icon.png");
		mTex_Select         = add("ui/icon_select.png");
		mTex_HelpIcn        = add("ui/help_icon.png");
		mTex_AbtUsIcn       = add("ui/about_icon.png");
		mTex_SoundIcn       = new SimplePlane[2];
		mTex_SoundIcn[0]    = add("ui/sound_on_icon.png");
		mTex_SoundIcn[1]    = add("ui/sound_off_icon.png");
		mTex_HelpTitle      = add("ui/help_text.png");
		mTex_AbtTitle       = add("ui/about_us_text.png");
		mTex_AbtTxt         = add("ui/about.png");
		mTex_PopUp          = add("ui/po-pup.png");
		mTex_BackBtn        = add("ui/back_icon.png");
		mTex_BtnSelect      = add("ui/button_select.png");
		mTex_HomeBtn        = add("ui/home_de.png");
		mTex_ContinueBtn    = add("ui/continue_de.png");
		mTex_HighScoreBtn   = add("ui/highscore_de.png");
		mTex_Trans          = new SimplePlane[2]; 
		mTex_Trans[0]       = add("ui/left_box.png");       
		mTex_Trans[1]       = add("ui/right_box.png");
		mTex_GameOverTxt    = add("ui/game_over_text.png");
		mTex_TargetTxt      = add("ui/target_text.png");
		mTex_TargetBox      = add("ui/targetbox.png");
		mTex_RetryBtn       = add("ui/retrybox.png");
		mTex_HelpTxt        = add("ui/help_po-pup.png");
		
	}
	void gameReset()
	{
		mLevel     = 1;
		mScore     = 0;
		mNoArrow =25;
		mBow.setBow(-.5f,-.25f,0);
		mArrow.set(mBow.x,mBow.y,0,0);
		mPower.set(10,10,0,0); 
		for(int i=0;i<mBird.length;i++)
		{
		  if(i==2)	
		      mBird[i].set(randomRange(-1f,.2f),1.2f+(i*.7f),.008f,-randomRange(.025f,.035f),randomRange(.5f,1),i%mTex_Bird.length);
		  else if(i==3)	
			  mBird[i].set(	randomRange(.7f,1.2f),1.2f+(i*.7f),-.008f,-randomRange(.025f,.035f),randomRange(.5f,1f),i%mTex_Bird.length);
		  else
			  mBird[i].set(randomRange(-.5f,.9f),1.2f+(i*.7f),0,-randomRange(.025f,.035f),randomRange(.5f,1f),i%mTex_Bird.length);
		   mBlast[i].setblast(-10,0,0,0,1);
		   mPankh[i].setPankh(-10,0,0,0,1);
		   mPoint[i].set(-10,0,0,1);
		}
		
	    mControl.set(-.85f,-1f-mTex_Iconbar.Height()/2,0f,0);
	    mSetting.set(.85f,-1f-mTex_Iconbar.Height()/2,0f,0);
	    mTarget.set(-10,-10,0,1); 
 
	}
			
	public float randomRange(float min,float max)
	{
		float rand = M.mRand.nextFloat();
		max = max-min;
		max  = rand%max;
		return (max+min);
	}
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		
		gl.glClearColor(0f,0f,0f, 0.5f);
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
		resumeCounter++;
		if(mStart.adView!=null)
		{
			if(resumeCounter>50 && M.GameScreen != M.GAMEADD)
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
		/*AdHouse*/
		if (mStart.adHouse != null) {
			if (M.GameScreen == M.GAMEADD) {
				int inv = mStart.adHouse.getVisibility();
				if (inv == AdView.GONE) {try {handler2.sendEmptyMessage(AdView.VISIBLE);} catch (Exception e) {}}
			} else {
				int inv = mStart.adHouse.getVisibility();
				if (inv == AdView.VISIBLE) {try {handler2.sendEmptyMessage(AdView.GONE);} catch (Exception e) {}
				}
			}
		}
		/*AdHouse*/
		
		long dt = System.currentTimeMillis() - startTime;
		if (dt < 33)
			try{
				Thread.sleep(33 - dt);
			}catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		startTime = System.currentTimeMillis();
	}
	public void onSurfaceChanged(GL10 gl, int width, int height) {
//		gl.glViewport(0, 0, width, height);
//		gl.glMatrixMode(GL10.GL_PROJECTION);
//		gl.glLoadIdentity();
//		gl.glMatrixMode(GL10.GL_MODELVIEW);  
//		gl.glEnable(GL10.GL_TEXTURE_2D);
//		gl.glEnable(GL10.GL_CULL_FACE);
//		gl.glCullFace(GL10.GL_BACK);
//	    gl.glLoadIdentity();
		gl.glEnable(GL10.GL_TEXTURE_2D);
	    gl.glViewport(0, 0, width, height);
	    float ratio = (float) width / height;
	    gl.glMatrixMode(GL10.GL_PROJECTION);
	    gl.glLoadIdentity();
	    gl.glMatrixMode(GL10.GL_MODELVIEW);
	    gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);

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
	
	SimplePlane addBitmapRotate(Bitmap b)
	{
		SimplePlane SP = null;
		try
		{
			SP = new SimplePlane((b.getWidth()/M.mMaxY),(b.getHeight()/M.mMaxY));
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
	public Handler getHandler() {
		return handler;
	}
	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	void LoadFont()
	{
		mTex_Font	= new SimplePlane[10];
		Bitmap b = LoadImgfromAsset("fontstrip.png");
		for(int i = 0;i<mTex_Font.length;i++)
			mTex_Font[i] = addBitmap(Bitmap.createBitmap(b, i*b.getWidth()/mTex_Font.length, 0,b.getWidth()/mTex_Font.length, b.getHeight(),null, true));
	}
	
}
