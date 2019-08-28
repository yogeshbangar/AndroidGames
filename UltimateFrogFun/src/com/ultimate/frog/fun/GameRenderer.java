package com.ultimate.frog.fun;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.opengl.GLSurfaceView.Renderer;

public class GameRenderer implements Renderer 
{
	final Group root;
	public static Context mContext;
	public static Start mStart;
	long startTime = System.currentTimeMillis();
	
	
	SimplePlane mTex_BG,mTex_Gover,mTex_Win,mTex_GPaused,mTex_Logo,mTex_Play,mTex_Splash,mTex_Top;
	SimplePlane mTex_Woter,mTex_Board,mTex_Classic,mTex_Leader,mTex_Rate,mTex_Survival,mTex_Time;
	SimplePlane mTex_Best,mTex_Menu,mTex_Retry,mTex_Score,mTex_TrvTime,mTex_SText,mTex_Jump;
	SimplePlane mTex_Achiv;
	SimplePlane[] mTex_Leaf,mTex_Ninja,mTex_LeafJump,mTex_Sound,mTex_Font,mTex_Frog,mTex_Arrow; 

	
	boolean addFree = false;
	boolean SingUpadate = false;
	boolean mAchiUnlock[] = new boolean[5];
	
	int mLevel = 0;
	int mRow = 0;
	int mScore;
	int m1Best;
	int m2Best;
	
	float mBG = 0;
	long mTime;
	long m0Best;
	
	Leaf mLeaf[];
	Player mPlayer;
	public GameRenderer(Context context) 
	{
		mContext = context;
		mStart	= (Start)mContext;
		root = new Group(this);
		init();
	}

	void init() {
		try {
			
			mTex_Top = add("top.png");
			mTex_Board = add("ui/comman.png");
			mTex_Classic = add("ui/classic.png");
			mTex_Leader = add("ui/leader.png");
			mTex_Rate = add("ui/rate.png");
			mTex_Survival = add("ui/survival.png");
			mTex_Time = add("ui/timetravel.png");
			
			mTex_Frog = new SimplePlane[5];
			for(int i =0;i<mTex_Frog.length;i++)
				mTex_Frog[i] = add("Frog/"+i+".png");
			
			mTex_Arrow = new SimplePlane[2];
			mTex_Arrow[0] = add("ui/tab1.png");
			mTex_Arrow[1] = add("ui/tab2.png");
			
			mTex_Best = add("ui/best.png");
			mTex_Menu = add("ui/menu.png");
			mTex_Retry = add("ui/retry.png");
			mTex_Score = add("ui/score.png");
			mTex_TrvTime = add("ui/time.png");
			
			mTex_Sound = new SimplePlane[2];
			mTex_Sound[0] = add("ui/soundoff.png");
			mTex_Sound[1] = add("ui/soundon.png");
			
			mTex_LeafJump = new SimplePlane[2];
			mTex_LeafJump[0] = add("frogfall0.png");
			mTex_LeafJump[1] = add("frogfall1.png");
			mTex_Leaf = new SimplePlane[4];
			for(int i =0;i<mTex_Leaf.length;i++)
				mTex_Leaf[i]  = add("a"+i+".png");
			
			mTex_BG = add("bg.png");
			mTex_Gover = add("gameover.png");
			mTex_Win = add("youwin.png");
			mTex_GPaused = add("gamepaused_taxt.png");
			mTex_Logo = add("logo.png");
			mTex_SText = add("text.png");
			mTex_Jump = add("ui/jump.png");
			mTex_Achiv = add("ui/achi.png");
			mTex_Ninja = new SimplePlane[2];
			mTex_Ninja[0] = add("f1.png");
			mTex_Ninja[1] = add("f2.png");
			mTex_Play = add("play.png");
			mTex_Splash = add("splash.png");
			mTex_Woter = add("water.png");
			load_Font();
			
			
			mLeaf = new Leaf[10];
			for(int i = 0 ;i<mLeaf.length;i++){
				mLeaf[i] = new Leaf();
			}
			mPlayer = new Player(); 
//			gameReset(0);
		} catch (Exception e) {
		}

	}
	
	
	void gameReset(int no) {
		mLevel = no;
		
		for(int i = 0 ;i<mLeaf.length;i++){
			mLeaf[i].Set(-.3f+i*M.HGT);
		}
		int pos = 0;
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~`"+mTex_Leaf[0].Height()+"~~~~~~~~~~~~~~~~~~");
		mPlayer.start(.25f,-.3f+pos*M.HGT ,pos);
		for(int j =0;j<4;j++){
			if(mLeaf[pos].no[j] == 0){
				mPlayer.x = -.75f + j * .5f;
				mPlayer.cj = j;
			}
		}
		
		switch (mLevel) {
		case 1:
			mTime = System.currentTimeMillis();
			M.SPD = -.1f;
			break;
		case 2:
			M.SPD = -.01f;
			break;
		default:
			M.SPD = -.1f;
			mTime = System.currentTimeMillis();
			mRow = 50;
			break;
		}
		mScore = 0;
		mBG = -.3f+(mRow+2.4f)*M.HGT;
		if(ads%3 == 0){
			mStart.load();
		}
		ads ++;
	}
	int ads=0;
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

	Bitmap LoadImgfromAsset(String ID) {
		try {
			return BitmapFactory.decodeStream(mContext.getAssets().open(ID));
		} catch (Exception e) {
			System.out.println(ID + "  ~~Error~~  " + e.getMessage());
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
//		 Log.d("resizeImg========","newWidth ["+newWidth+"] newHeight ["+newHeight+"]");
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
		Bitmap b = LoadImgfromAsset("ui/font.png");
		for (int i = 0; i < mTex_Font.length; i++)
			mTex_Font[i] = addBitmap(Bitmap.createBitmap(b, i * b.getWidth()/ 16, 0, b.getWidth() / 16,b.getHeight(), null, true));
	}
}
