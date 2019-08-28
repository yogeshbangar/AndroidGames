package com.hututu.app.facechanger;

import java.io.File;
import java.util.ArrayList;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.google.android.gms.ads.AdView;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

public class GameRenderer implements Renderer 
{
	final Group root;
	public static Context mContext;
	public static Start mStart;
	long startTime = System.currentTimeMillis();
	
	int resumeCounter =0;
	int mSel = 0;
	int action = 0;
	int clp = 0;
	ArrayList<Edit> mEdit;
	ArrayList<Edit> mRedo;
	ArrayList<Gallary> mGallary;
	MainActivity mMainActivity;
	
	
	String path = "";
	boolean addFree = false;
	SimplePlane mTex_Logo, mTex_BG, mTex_Board, mTex_Apply, mTex_Back, mTex_Cancle, mTex_Flip,  mTex_gp, mTex_Gallery;
	SimplePlane mTex_Move, mTex_Next, mTex_Option, mTex_RateUs, mTex_Redo, mTex_Save, mTex_ImgBox,mTex_Text,mTex_Undo;
	SimplePlane mTex_Shared, mTex_Camera, mTex_GlrTxt, mTex_About, mTex_OptTxt, mTex_GetMore, mTex_htt, mTex_Arrow;
	SimplePlane mTex_Like,mTex_AbouUs,mTex_AddFree,mTex_Buy,mTex_Dollor,mTex_Letter,mTex_Ling,mTex_LingBar;
	
	SimplePlane[] mTex_ScaleWidth, mTex_Rotate, mTex_SclHgh, mTex_MoveA,mTex_Obj;
	private Handler handler = new Handler() {public void handleMessage(Message msg) {mStart.adView.setVisibility(msg.what);}};
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
			mMainActivity = new MainActivity();
			mMainActivity.onCreate();
			mTex_Ling		= add("loading0.png");
			mTex_LingBar	= add("loading1.png");
			mTex_Logo 		= add("hututugames.png");
			mTex_BG			= add("bg.jpg");
			mTex_Board 		= add("object-board.png");
			mTex_Apply		= add("icon/apply.png");
			mTex_Back		= add("icon/back.png");
			mTex_Cancle		= add("icon/cancle.png");
			mTex_Flip		= add("icon/flip.png");
			mTex_gp			= add("icon/g+.png");
			mTex_Gallery	= add("icon/gallery.png");
			mTex_Like		= add("icon/like.png");
			mTex_Arrow		= add("icon/next.png");
			mTex_Move		= add("icon/move.png");
			mTex_MoveA		= new SimplePlane[2];
			mTex_MoveA[0]	= add("icon/movesmall.png");
			mTex_MoveA[1]	= add("icon/movesmall0.png");
			mTex_Next		= add("icon/back-2.png");
			mTex_Option		= add("icon/option.png");
			mTex_RateUs		= add("icon/rate-us.png");
			mTex_Redo		= add("icon/redo.png");
			mTex_Rotate		= new SimplePlane[2];
			mTex_Rotate[0]	= add("icon/rotate.png");
			mTex_Rotate[1]	= add("icon/rotate0.png");
			mTex_Save		= add("icon/save.png");
			mTex_SclHgh		= new SimplePlane[2];
			mTex_SclHgh[0]	= add("icon/scale-hight.png");
			mTex_SclHgh[1]	= add("icon/scale-hight0.png");
			mTex_ScaleWidth	= new SimplePlane[2];
			mTex_ScaleWidth[0]= add("icon/scale-width.png");
			mTex_ScaleWidth[1]= add("icon/scale-width0.png");
			mTex_Shared		= add("icon/shared.png");
			mTex_Text		= add("icon/text.png");
			mTex_Undo		= add("icon/undo.png");
			mTex_ImgBox		= add("image-box.png");
			mTex_Camera		= add("camera.png");
			mTex_GlrTxt		= add("gallery.png");
			mTex_About		= add("about.png");
			mTex_AbouUs		= add("about-us.png");
			mTex_OptTxt		= add("option.png");
			mTex_GetMore	= add("getmoreoption.png");
			mTex_htt		= add("hututu.png");
			mTex_AddFree	= add("add-free-small.png");
			mTex_Buy		= add("buy.png");
			mTex_Dollor		= add("dollor.png");
			mTex_Letter		= add("letter.png");
			mTex_Obj		= new SimplePlane[200];
			
			for(int i=0;i<mTex_Obj.length;i++)
				mTex_Obj[i]	= add("clipart/"+i+".png");
			mEdit		= new ArrayList<Edit>();
			mRedo		= new ArrayList<Edit>();
			mGallary		= new ArrayList<Gallary>();
			ThreadA thread = new ThreadA();
			thread.run();
			gameReset();
		}catch(Exception e){}
	}
	void gameReset()
	{
		
	}
	void addImage(String name)
	{
		root.Counter =0;
		Bitmap mTempBit = loadFromFile(name);
		mEdit.clear();
		SimplePlane mTemp = addBitmap(mTempBit);
		Edit tempe	= new Edit(mTemp);
		tempe.lsy = tempe.lsx = 50/mTemp.width();
//		if(mTempBit.getWidth()>mTempBit.getHeight())
//			tempe.lsy = tempe.lsx = (M.ScreenWidth / mTempBit.getWidth())*.25f;  
//		else
//			tempe.lsy = tempe.lsx = (M.ScreenHieght / mTempBit.getHeight())*.25f;
		mEdit.add(tempe);
		M.AppScreen = M.APPEDIT;
		action = 0;
		SharedPreferences prefs = mStart.getSharedPreferences("X", Start.MODE_PRIVATE);
	    Editor editor = prefs.edit();
	    editor.putInt("screen", M.AppScreen);
	    editor.commit();
	    path	= M.DIR+"/"+System.currentTimeMillis()+".jpg";
	}
	
	
	void addBit(Bitmap mTempBit)
	{
		root.Counter =0;
//		Bitmap mTempBit = name;//loadFromFile(name);
		mEdit.clear();
		SimplePlane mTemp = addBitmap(mTempBit);
		Edit tempe	= new Edit(mTemp);
		tempe.lsy = tempe.lsx = 50/mTemp.width();
//		if(mTempBit.getWidth()>mTempBit.getHeight())
//			tempe.lsy = tempe.lsx = (M.ScreenWidth / mTempBit.getWidth())*.25f;  
//		else
//			tempe.lsy = tempe.lsx = (M.ScreenHieght / mTempBit.getHeight())*.25f;
		mEdit.add(tempe);
		M.AppScreen = M.APPEDIT;
		action = 0;
		SharedPreferences prefs = mStart.getSharedPreferences("X", Start.MODE_PRIVATE);
	    Editor editor = prefs.edit();
	    editor.putInt("screen", M.AppScreen);
	    editor.commit();
	    path	= M.DIR+"/"+System.currentTimeMillis()+".jpg";
	    
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
		GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f, 100.0f);
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
//			SP = new SimplePlane((b.getWidth()/M.mMaxX),(b.getHeight()/M.mMaxY));
			SP = new SimplePlane((b.getWidth())/20,(b.getHeight())/20);
			SP.loadBitmap(b);// R.drawable.jay
		}catch(Exception e){
			System.out.println("------->  "+ID+"  <---------");
		}
		return SP;
	}
	SimplePlane addBitmap (Bitmap b)
	{
		SimplePlane SP = null;
		try
		{
			SP = new SimplePlane((b.getWidth())/20,(b.getHeight())/20);
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
	public static Bitmap loadFromFile(String filename) {
	      try {
	          File f = new File(filename);
	          if (!f.exists()) { return null; }
	          Bitmap tmp = BitmapFactory.decodeFile(filename);
	          return tmp;
	      } catch (Exception e) {
	          return null;
	      }
	  }
	public class ThreadA implements Runnable {
		public void run() {
			String dirPath = Environment.getExternalStorageDirectory() + M.DIR+"/";
			File f = new File(dirPath);
			File[] files = f.listFiles();
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				if (file.isFile() && file.getName().endsWith(".jpg")){
//					Gallary gallary = new Gallary(addBitmap(loadFromFile(file.getAbsolutePath())), file.getAbsolutePath());
					Bitmap bimap = Start.getBitmapGallary(file.getAbsolutePath());
					Gallary gallary = new Gallary(addBitmap(bimap), file.getAbsolutePath());
					gallary.mTexture.mBitmap = PTImg(gallary.mTexture.mBitmap);
					mGallary.add(gallary);
//					System.out.println(bimap.getWidth()+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ "+bimap.getHeight());
//					mTGellary.add(addBitmap(loadFromFile(file.getAbsolutePath())));
				}
			}
		}
	}
	
	static Bitmap PTImg(Bitmap bitmapOrg) {
		int width = bitmapOrg.getWidth();
		int height = bitmapOrg.getHeight();
		int newWidth = 256;
		int newHeight = 256;
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
//		System.out.println(width+"   "+height+"  "+scaleWidth+"  "+scaleHeight+" resizeImg========"+"newWidth [" + newWidth + "] newHeight ["+ newHeight + "]");
		
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		matrix.postRotate(0);
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, width,
				height, matrix, true);
		
		return resizedBitmap;
	}
	
}
