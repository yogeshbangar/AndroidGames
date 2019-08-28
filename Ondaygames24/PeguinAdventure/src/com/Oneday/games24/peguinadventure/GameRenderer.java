package com.Oneday.games24.peguinadventure;
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
	
	final byte wd[][] = {{10,17,14,18,26,28,8,15,15,14,16,10,11,10,18,19,18,19,19,19,18,20,19,19,19,10,10,16,16,16,16,22,27,24,23,25,22,22,25,25,14,22,27,22,28,26,24,22,24,25,20,23,26,26,32,26,26,22,13,18,13,16,16,10,18,21,17,21,18,15,20,22,13,12,24,13,30,22,19,22,21,18,16,17,22,22,32,20,21,18,13,6,13,16},
		{4,8,11,11,14,14,3,6,6,10,11,4,6,4,12,11,11,12,11,12,11,11,10,12,11,4,4,10,10,10,10,14,13,12,12,12,11,11,12,12,4,7,12,9,16,12,12,11,12,12,12,10,12,12,16,12,12,12,5,12,5,10,10,5,10,10,10,10,10,6,12,10,4,6,11,4,15,10,10,10,10,6,10,8,10,10,14,10,9,8,5,3,5,10}};
	
	boolean isPlay = false;
	
//	byte Stars1[];
	
	int BuyStar = 0;
	int TStar=0;
	int Clock = 1;
	int ClockCount = 0;
	int timesUp = 0;
	int mScore = 0;
	
	
	int BestScore[] = new int[13];
	int Total[] = new int[13];
	
	float mRankFill= 0;
	
	long mGameTime = 0;
	
	String packages = new String();
	
	LevelOne   mLOne;
	LevelTwo   mLTwo;
	LevelThree  mLTree;
	LevelFour  mLFour;
	LevelFive  mLFive;
	LevelSix   mLsix;
	LevelSeven mLSeven;
	LevelEight mLEight;
	LevelNine  mLNine;
	LevelTen   mLTen;
	LevelEleven mLEleven;
	LevelTwelve mLTwelve;
	LevelThirteen mLThirteen;
	LevelSelection mLSelect;
//	MainActivity mMainActivity;

	SimplePlane mTex_Logo,mTex_Skip,mTex_Ling,mTex_LingBar;
	SimplePlane mTex_HelpB,mTex_PauseB,mTex_Time_box,mTex_Star[],mTex_Trans;
	SimplePlane mTex_Font[][],mTex_NFont[][];
	
	
//	private Handler handler = new Handler() {public void handleMessage(Message msg) {mStart.adView.setVisibility(msg.what);}};
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
		try
		{
//			mMainActivity = new MainActivity();
//			mMainActivity.onCreate();
			System.gc();
//			System.out.println("[1="+mLOne+"][2="+ mLTwo+"][3="+mLTree+"][4="+mLFour+"][5="+ mLFive+"][6="+ mLsix+"][7="+mLSeven+"][8="+
//			mLEight+"][9="+ mLNine+"][10="+mLTen+"][11="+ mLEleven+"][12="+mLTwelve+"][13="+ mLThirteen+"][Sel="+ mLSelect+"]");
//			Stars	 		= new byte[13];
			load();
			mTex_HelpB		= add("common/help_button.png");
			mTex_PauseB		= add("common/pause_button.png");
			mTex_Time_box	= add("common/time_box.png");
			mTex_Trans		= add("common/trans.png");
			
			mTex_Skip		= add("exit_icon.png");
			mTex_Ling		= add("loading0.png");
			mTex_LingBar	= add("loading1.png");
			
			mTex_Star		= new SimplePlane[7];
			for(int i = 0;i<mTex_Star.length;i++)
			{
				mTex_Star[i]= add("common/small_star"+i+".png");
			}
			
			mTex_Logo		= add("oneday.png");
			mLOne			= new LevelOne(this);
			mLTwo			= new LevelTwo(this);
			mLTree			= new LevelThree(this);
			mLFour			= new LevelFour(this);
			mLFive			= new LevelFive(this);
			mLsix			= new LevelSix(this);
			mLSeven			= new LevelSeven(this);
			mLEight			= new LevelEight(this);
			mLNine			= new LevelNine(this);
			mLTen			= new LevelTen(this);
			mLEleven		= new LevelEleven(this);
			mLTwelve		= new LevelTwelve(this);
			mLThirteen		= new LevelThirteen(this);
			mLSelect		= new LevelSelection(this);
//			font();
			gameReset();
		}catch(Exception e){}
		
	}

	void gameReset()
	{
//		mLOne.set(0);
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
//		if(!mStart.pause)
		{
//			if(mStart.adView!=null)
//			{
//				int inv=mStart.adView.getVisibility();
//				if (M.GameScreen == M.GAMEADV/*||M.GameScreen == M.GAMELOAD*/||M.GameScreen == M.GAMESELECT)
//				{
//					if(inv==AdView.GONE){
//						try{handler.sendEmptyMessage(AdView.VISIBLE);}catch(Exception e){}}
//				}
//				else
//				{
//					if(inv==AdView.VISIBLE){
//						try{handler.sendEmptyMessage(AdView.GONE);}catch(Exception e){}}
//				}
////				System.out.println(inv+"    "+mStart.adView.isEnabled());
//			}
			/*AdHouse*/
			if (mStart.adHouse != null) {
				if (M.GameScreen == M.GAMEADV/*||M.GameScreen == M.GAMELOAD*/) {
					int inv = mStart.adHouse.getVisibility();
					if (inv == AdView.GONE) {try {handler2.sendEmptyMessage(AdView.VISIBLE);} catch (Exception e) {}}
				} else {
					int inv = mStart.adHouse.getVisibility();
					if (inv == AdView.VISIBLE) {try {handler2.sendEmptyMessage(AdView.GONE);} catch (Exception e) {}
					}
				}
			}
			/*AdHouse*/
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
	static SimplePlane add (String ID)
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
	static SimplePlane addScal(String ID,float s)
	{
		SimplePlane SP = null;
		Bitmap b = LoadImgfromAsset(ID);
		try
		{
			SP = new SimplePlane((b.getWidth()/M.mMaxX)*s,(b.getHeight()/M.mMaxY)*s);
			SP.loadBitmap(b);// R.drawable.jay
		}catch(Exception e){}
		return SP;
	}
	static SimplePlane addBitmap (Bitmap b)
	{
		SimplePlane SP = null;
		try
		{
			SP = new SimplePlane((b.getWidth()/M.mMaxX),(b.getHeight()/M.mMaxY));
			SP.loadBitmap(b);// R.drawable.jay
		}catch(Exception e){}
		return SP;
	}
	static SimplePlane addRotateBitmap(Bitmap b)
    {
        SimplePlane SP = null;
        try
        {
                SP = new SimplePlane((b.getWidth()/M.mMaxY),(b.getHeight()/M.mMaxY));
                SP.loadBitmap(b);// R.drawable.jay
        }catch(Exception e){}
        return SP;
    }
	static Bitmap LoadImgfromAsset(String ID)
	{
//		System.out.println("~~~~~~~~~~~~~~~~~~ "+ID+" ~~~~~~~~~~~~~~~~~~~");
		try{
			return BitmapFactory.decodeStream(mContext.getAssets().open(ID));}
		catch(Exception e)
		{
			//Log.d(""+ID,"!!!!!!!!!!!!!!!!!!!!!~~~~~~~~~~~~~!!!!!!!!!!!!!!!!!!!!!!!!!"+e.getMessage());
			System.out.println("~~~~~~~~~~~~~~~~~~ "+ID+" ~~~~~~~~~~~~~~~~~~~");
			return null;
		}
	}
	static SimplePlane addRotate(String ID)
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
	static Bitmap resizeImg(Bitmap bitmapOrg,int newWidth,int newHeight)
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
	static Bitmap FlipHorizontal(Bitmap bitmapOrg)
	{
		Matrix matrix = new Matrix();
		matrix.postScale(-1f, 1f);
		matrix.postRotate(0);
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0,bitmapOrg.getWidth(), bitmapOrg.getHeight(), matrix, true);
		return resizedBitmap;
	}

	void load()
	{
		for(int j=0;j<wd[0].length;j++){
			wd[0][j]-=1;
		}
		if(mTex_Font==null){
			mTex_Font		= new SimplePlane[2][];
			for(int k=0;k<2;k++){
				Bitmap b		= GameRenderer.LoadImgfromAsset("font/"+k+".png");
				mTex_Font[k]		= new SimplePlane[94];
				int m =0;
				for(int i=0;i<6 && m < mTex_Font[k].length;i++)
				{
					for(int j=0;j<16 && m < mTex_Font[k].length;j++){
						mTex_Font[k][m] = GameRenderer.addBitmap(Bitmap.createBitmap(b, j*b.getWidth()/16, i*b.getHeight()/8,b.getWidth()/16, b.getHeight()/8, null, true));
						m++;
					}
				}
			}
		}
		if(mTex_NFont==null){
			{
				mTex_NFont	= new SimplePlane[2][12];
				for(int k=0;k<2;k++){
					Bitmap b		= GameRenderer.LoadImgfromAsset("font/f"+k+".png");
					mTex_NFont[k]		= new SimplePlane[12];
					for(int i=0;i<mTex_NFont[k].length;i++)
						mTex_NFont[k][i] = GameRenderer.addBitmap(Bitmap.createBitmap(b, i*b.getWidth()/16, 0,b.getWidth()/16, b.getHeight(), null, true));
				}
			}
		}
	}
	void Draw_Num(GL10 gl,String strs,int type,float x,float y){
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i));
			if(k>47 && k <58)
				k-=48;
			else if(k==36)
				k=11;
			else if(k==46)
				k=10;
			Group.DrawTexture(gl, mTex_NFont[type][k],x, y);
			x+=mTex_NFont[type][k].width()*.8f;
		}
	}
	void Draw(GL10 gl,String strs,int type,float x,float y,int Aling){
		if(Aling == 1)//Center
		{
			x-=getWidth(strs,type)/2;
		}
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i));
			if(k==32){//Space
				x+=wd[type][0]/M.mMaxX;
				continue;
			}
			k-=33;
			float m = -mTex_Font[type][k].width()/2+wd[type][k]/M.mMaxX;
			Group.DrawTexture(gl, mTex_Font[type][k],x+m, y);
			x+=wd[type][k]*2f/M.mMaxX;
		}
	}
	float getWidth(String strs,int type)
	{
		float x=0;
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i));
			if(k==32){//Space
				x+=wd[type][0]/M.mMaxX;
				continue;
			}
			k-=33;
			x+=wd[type][k]*2f/M.mMaxX;
		}
		return x;
	}
	void DrawS(GL10 gl,String strs,float x,float y,float s){
		int type = 0;
		x-=getWidth(strs,type)/2 *s;
		for(int i =0;i<strs.length();i++)
		{
			int k = ((int)strs.charAt(i));
			if(k==32){//Space
				x+=wd[type][0]/M.mMaxX;
				continue;
			}
			k-=33;
			float m = (-mTex_Font[type][k].width()/2+wd[type][k]/M.mMaxX)*s;
			Group.DrawTextureS(gl, mTex_Font[type][k],x+m, y,s);
			x+=wd[type][k]*2f/M.mMaxX*s;
		}
	}
	void NumberRotate(GL10 gl,int no,float x, float y,int rotate)
	{
		String strs = "" + no;
		for (int i = 0; i < strs.length(); i++) {
			int k = ((int) strs.charAt(i)) - 33;
			if (strs.length() == 1) {
				mTex_Font[0][k].drawRotet(gl, x, y, rotate, 1.5f);
			} else {
				if (k >= 0) {
					if (i == 0)
						mTex_Font[0][k].drawRotet1(gl, x, y, rotate, -.4f);
					else
						mTex_Font[0][k].drawRotet1(gl, x, y, rotate, 0.4f);
				}
			}
		}
	}
}
