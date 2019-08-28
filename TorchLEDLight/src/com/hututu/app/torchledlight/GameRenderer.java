package com.hututu.app.torchledlight;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.google.android.gms.ads.AdView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;

public class GameRenderer implements Renderer 
{
	final Group root;
	public static Context mContext;
	public static Start mStart;
	long startTime = System.currentTimeMillis();
	int resumeCounter =0;
	int mSel = 0;
	boolean addFree = false;
	
	SimplePlane[] mTex_Button; 
	SimplePlane /*mTex_5star,*/mTex_bg,mTex_downloadgames,mTex_hututugames,mTex_ledlight,mTex_screenlight;
	SimplePlane   mTex_Pointer, mTex_Hightbar,mTex_Skip; //AdHouse
	
	Camera cam = null;
	
	boolean mIsLedOn = true;
	boolean mIsDisplayOn = false;
	
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
			mTex_Pointer = add("pointer.png");//AdHouse
			mTex_Hightbar = add("hightbar0.png");//AdHouse
			mTex_Skip = add("exit_icon.png");//AdHouse
			
			mTex_Button			= new SimplePlane[2];
			mTex_Button[0]		= add("button_off.png");
			mTex_Button[1]		= add("button_on.png");
			
//			mTex_5star			= add("5star.png");
			mTex_bg				= add("bg.jpg");
			mTex_downloadgames	= add("downloadgames.png");
			mTex_hututugames	= add("hututugames.png");
			mTex_ledlight		= add("ledlight.png");
			mTex_screenlight	= add("screenlight.png");
			
			mIsLedOn = true;
			mIsDisplayOn = false;
			
			
			flashLightOn();
			Britness(.25f);
		}catch(Exception e){}
		
	}
	
	
	void gameReset()
	{}
	
	
	void Britness(float val)
	{
		 WindowManager.LayoutParams localLayoutParams = mStart.getWindow().getAttributes();
		 localLayoutParams.screenBrightness = val;
		 mStart.getWindow().setAttributes(localLayoutParams);
	}
	public void flashLightOn() {
		System.out.println("@@@@");
	    try {
	        /*if (GameRenderer.mStart.getPackageManager().hasSystemFeature(
	        		PackageManager.FEATURE_CAMERA_FLASH))*/
	        {
	            cam = Camera.open();
	            Parameters p = cam.getParameters();
	            p.setFlashMode(Parameters.FLASH_MODE_TORCH);
	            cam.setParameters(p);
	            cam.startPreview();
	            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~!!!");
	    }
	    System.out.println("####");
	}

	public void flashLightOff() {
	    try {
	        /*if (GameRenderer.mStart.getPackageManager().hasSystemFeature(
	                PackageManager.FEATURE_CAMERA_FLASH))*/ 
	        {
	        	cam.stopPreview();
	        	cam.release();
	        	cam = null;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glClearColor(255.0f, 255.0f, 255.0f, 255.0f);
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
//		/*AdHouse*/
//		if (mStart.adHouse != null) {
//			if (M.GameScreen == M.GAMEADD) {
//				int inv = mStart.adHouse.getVisibility();
//				if (inv == AdView.GONE) {try {handler2.sendEmptyMessage(AdView.VISIBLE);} catch (Exception e) {}}
//			} else {
//				int inv = mStart.adHouse.getVisibility();
//				if (inv == AdView.VISIBLE) {try {handler2.sendEmptyMessage(AdView.GONE);} catch (Exception e) {}
//				}
//			}
//		}
//		/*AdHouse*/
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
