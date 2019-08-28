package com.hututu.game.papershot;

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
	final 	Group root;
	static Context mContext;
	public 	static Start mStart;
	long 	startTime = System.currentTimeMillis();
	int 	resumeCounter =0;
	int 	mSel = 0;
	
	
	Font mFont;
	SimplePlane[] mTex_BackGround,mTex_Ball,mTex_Fan,mTex_Font,mTex_BGFrant;
	SimplePlane[][] mTex_FBulti,mTex_BBulti;
	
	SimplePlane mTex_AboutSelect,mTex_Exitselect,mTex_Help,mTex_Highscoreselect,mTex_Menu;
	SimplePlane mTex_Menu_button_de,mTex_Menu_button_se,mTex_Popup,mTex_Scoreboard,mTex_Logo;
	SimplePlane mTex_Smalsoundon,mTex_Soundon,/*mTex_Star,*/mTex_soundoff,mTex_Pause,mTex_Arrow;
	SimplePlane   mTex_Pointer, mTex_Hightbar,mTex_Skip; //AdHouse
	Stone mStone;
	
	int mScore =0;
	int mEScore =0,mMScore =0,mHScore =0;
	int mLevel = 0;
	int mBG = 0;
	boolean isFromMenu = true;
	Position[] mPos;
	private Handler handler = new Handler() {public void handleMessage(Message msg) {mStart.adView.setVisibility(msg.what);}};
	private Handler handler2 = new Handler() {public void handleMessage(Message msg) {mStart.adHouse.setVisibility(msg.what);}};//AdHouse
	public GameRenderer(Context context) 
	{
		mContext = context;
		mStart	= (Start)mContext;
		root = new Group(this);
		init();
	}
	void init()
	{
		int i =0;
		try
		{
			mTex_Pointer = add("pointer.png");//AdHouse
			mTex_Hightbar = add("hightbar0.png");//AdHouse
			mTex_Skip = add("exit_icon.png");//AdHouse
			
			mFont = new Font();
			mTex_Menu			= add("UI/menu.jpg");
			mTex_Logo			= add("hututugames.png");
			mTex_BackGround		= new SimplePlane[6];
			mTex_BackGround[0]	= add("drawing.jpg");
			mTex_BackGround[1]	= add("hall.jpg");
			mTex_BackGround[2]	= add("road.jpg");
			mTex_BackGround[3]	= add("lounge.jpg");
			mTex_BackGround[4]	= add("restroom.jpg");
			mTex_BackGround[5]	= add("office.jpg");
			
			mTex_BGFrant		= new SimplePlane[6];
			mTex_BGFrant[0]		= add("drawing_table.png");
			
//			mTex_BGFrant[2]		= add("kitchen_table.png");
			mTex_BGFrant[3]		= add("lounge_table.png");
			mTex_BGFrant[4]		= add("restroom_table.png");
			mTex_BGFrant[5]		= add("office_table.png");
			
			
			mTex_FBulti			= new SimplePlane[5][];
			mTex_BBulti			= new SimplePlane[5][];
			
			mTex_FBulti[0]		= new SimplePlane[2];
			mTex_FBulti[0][0]	= add("drawing_easy0.png");
			mTex_FBulti[0][1]	= add("drawing_medi0.png");
			
			mTex_BBulti[0]	 	= new SimplePlane[2];
			mTex_BBulti[0][0]	= add("Drawing_easy1.png");
			mTex_BBulti[0][1]	= add("Drawing_medi1.png");
			
			
			mTex_FBulti[1] 		= new SimplePlane[3];
			mTex_FBulti[1][0]	= add("hall_easy0.png");
			mTex_FBulti[1][1]	= add("hall_medi0.png");
			mTex_FBulti[1][2]	= add("hall_hard0.png");
			
			mTex_BBulti[1] 		= new SimplePlane[3];
			mTex_BBulti[1][0]	= add("hall_easy1.png");
			mTex_BBulti[1][1]	= add("hall_medi1.png");
			mTex_BBulti[1][2]	= add("hall_hard1.png");
			
			
			mTex_FBulti[2] 		= new SimplePlane[3];
			mTex_FBulti[2][0]	= add("kitchen_easy0.png");
			mTex_FBulti[2][1]	= add("kitchen_medi0.png");
			mTex_FBulti[2][2]	= add("kitchen_hard0.png");
			
			mTex_BBulti[2] 		= new SimplePlane[3];
			mTex_BBulti[2][0]	= add("kitchen_easy1.png");
			mTex_BBulti[2][1]	= add("kitchen_medi1.png");
			mTex_BBulti[2][2]	= add("kitchen_hard1.png");
			
			
			mTex_FBulti[3] 		= new SimplePlane[3];
//			mTex_FBulti[3][0]	= add("kitchen_easy0.png");
//			mTex_FBulti[3][1]	= add("kitchen_medi0.png");
			mTex_FBulti[3][2]	= add("lounge_hard0.png");
			
			mTex_BBulti[3] 		= new SimplePlane[3];
//			mTex_BBulti[3][0]	= add("kitchen_easy1.png");
//			mTex_BBulti[3][1]	= add("kitchen_medi1.png");
			mTex_BBulti[3][2]	= add("lounge_hard1.png");
			
			
			mTex_FBulti[4] 		= new SimplePlane[1];
			mTex_FBulti[4][0]	= add("restroom_easy0.png");
//			mTex_FBulti[3][1]	= add("kitchen_medi0.png");
//			mTex_FBulti[3][2]	= add("lounge_hard0.png");
			
			mTex_BBulti[4] 		= new SimplePlane[1];
			mTex_BBulti[4][0]	= add("restroom_easy1.png");
//			mTex_BBulti[3][1]	= add("kitchen_medi1.png");
//			mTex_BBulti[3][2]	= add("lounge_hard1.png");
			
			
			mTex_AboutSelect	= add("UI/about-select.png");
			mTex_Exitselect		= add("UI/exitselect.png");
			mTex_Help			= add("UI/help.png");
			mTex_Highscoreselect= add("UI/highscoreselect.png");
			mTex_Menu_button_de	= add("UI/menu_button_de.png");
			mTex_Menu_button_se	= add("UI/menu_button_se.png");
			mTex_Popup			= add("UI/popup.png");
			mTex_Scoreboard		= add("UI/scoreboard.png");
			mTex_Smalsoundon	= add("UI/smalsoundon.png");
			mTex_Soundon		= add("UI/soundon.png");
//			mTex_Star			= add("UI/star.png");
			mTex_soundoff		= add("UI/sondoff.png");
			mTex_Pause			= add("UI/Pause.png");
			mTex_Arrow			= add("arrow.png");
			
			
			Bitmap b 		= LoadImgfromAsset("fan.png");
			mTex_Fan		= new SimplePlane[2];
			mTex_Fan[0]		= addBitmap(Bitmap.createBitmap(b, 0,0,b.getWidth()/2,b.getHeight(),null, true));
			mTex_Fan[1]		= addBitmap(Bitmap.createBitmap(b, b.getWidth()/2,0,b.getWidth()/2,b.getHeight(),null, true));
			
			
			b.recycle();
			int k=0;
			b 				= LoadImgfromAsset("paper_ball.png");
			mTex_Ball		= new SimplePlane[16];
			for(i = 0;i<4&&k<mTex_Ball.length;i++)
			{
				for(int j = 0;j<5&&k<mTex_Ball.length;j++)
				{
					mTex_Ball[k] = addBitmap(Bitmap.createBitmap(b, j*b.getWidth()/5,i*b.getHeight()/4,b.getWidth()/5,b.getHeight()/4,null, true));
					k++;
				}
			}
			font();
			mStone = new Stone(this);
			
			mPos 		= new Position[3];
			mPos[0] 	= new Position(0.20f, -.16f,-.28f,0.10f);
			mPos[1] 	= new Position(0.14f, 0.03f,-.01f,0.11f);
			mPos[2] 	= new Position(0.08f, 0.36f,0.30f,0.12f);
			mLevel = 2;
			
			mBG	= 1;
		
			
			
			
			
			gameReset();
		}catch(Exception e){}
		
	}
	
	
	void gameReset()
	{
		mStone.set(-100, -100, 1, 0, 0, 0);
		M.mAir	= (float)(M.mRand.nextInt()%500)/100f;
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
			resumeCounter++;
			if(resumeCounter>20 && M.GameScreen != M.GAMEADD)
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
	void font()
	{
		mTex_Font	= new SimplePlane[11];
		Bitmap b = LoadImgfromAsset("fontstrip.png");
		for(int i = 0;i<mTex_Font.length;i++)
			mTex_Font[i] = addBitmap(Bitmap.createBitmap(b, i*b.getWidth()/mTex_Font.length, 0,b.getWidth()/mTex_Font.length, b.getHeight(),null, true));
	}
}
