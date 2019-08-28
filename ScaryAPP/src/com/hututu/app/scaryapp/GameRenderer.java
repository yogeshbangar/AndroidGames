package com.hututu.app.scaryapp;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.opengl.GLSurfaceView.Renderer;

import android.util.Log;

public class GameRenderer implements Renderer 
{
	final Group root;
	static Context mContext;
	public static Start mStart;
	long startTime = System.currentTimeMillis();
	int resumeCounter =0;
	int mSel = 0;
	
	SimplePlane[] mTex_Time,mTex_Text,mTex_Icn,mTex_SIcn,mTex_Skal;
	SimplePlane mTex_Logo,mTex_back,mTex_bg,mTex_go,mTex_InfoI,mTex_InfoT,mTex_Minute,mTex_Name;
	SimplePlane mTex_Popup,mTex_Scremming,mTex_Second,mTex_Select;
	
	int mSound = 0;
	
	int mImg = 0;
	float scal = .1f;
//	private Handler handler = new Handler() {public void handleMessage(Message msg) {mStart.adView.setVisibility(msg.what);}};
	public GameRenderer(Context context) 
	{
		mContext = context;
		mStart	= (Start)mContext;
		root = new Group(this);
		init();
	}

	void init() {
		try {
			mTex_Logo = add("hututugames.png");
			mTex_Time = new SimplePlane[7];
			mTex_Time[0] = add("10sec.png");
			mTex_Time[1] = add("15sec.png");
			mTex_Time[2] = add("30sec.png");
			mTex_Time[3] = add("60sec.png");
			mTex_Time[4] = add("2min.png");
			mTex_Time[5] = add("5min.png");
			mTex_Time[6] = add("10sec.png");

			mTex_back = add("back_button.png");
			mTex_bg = add("bg.png");
			mTex_go = add("go.png");
			mTex_InfoI = add("info_icon.png");
			mTex_InfoT = add("info_text.png");
			mTex_Minute = add("minute_font.png");
			mTex_Name = add("name.png");
			mTex_Popup = add("po_pup.png");
			mTex_Scremming = add("scremming_font.png");
			mTex_Second = add("second_font.png");
			mTex_Select = add("select.png");
			
			mTex_SIcn = new SimplePlane[2];
			mTex_SIcn[0] = add("select_icon.png");
			mTex_SIcn[1] = add("de_select_icon.png");
			
			
			mTex_Icn = new SimplePlane[3];
			mTex_Icn[0] = add("picture.png");
			mTex_Icn[1] = add("sound.png");
			mTex_Icn[2] = add("time.png");
			
			mTex_Text = new SimplePlane[3];
			mTex_Text[0] = add("picture_text.png");
			mTex_Text[1] = add("sound_text.png");
			mTex_Text[2] = add("time_text.png");
			mTex_Skal			= new SimplePlane[12];
			for(int i =0;i<mTex_Skal.length;i++)
				mTex_Skal[i] 	= add("img/"+i+".jpg");
			gameReset();
		} catch (Exception e) {
		}

	}
	
	void gameReset()
	{
		mSound = 0;
		M.mTime = 0;
		mImg =0;
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
		/*if(mStart.adView!=null)
		{
			resumeCounter++;
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
		}*/
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
}
