package com.hututu.game.facechanger2;

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
//	long startTime = System.currentTimeMillis();
	boolean addFree = false;
	int mSel;
	
	SimplePlane[] mTex_Button,mTex_Icon,mTex_Buy,mTex_EditIcn[],mTex_URL,mTex_ClipIcn,mTex_PopIcn,mTex_PopBtt;
	SimplePlane[]mTex_ContIcn, mTex_ContiBtt,mTex_Library[],mTex_MainICN,mTex_Arrow,mTex_RGB;
	SimplePlane mTex_htt,mTex_BG,mTex_Popup,mTex_Priveiw,mTex_Splash,mTex_Back,mTex_AdsFree,mTex_About;
	SimplePlane mTex_SubICN;
	
	InApp mInAPP;
	
	int Popup = 0;
	int LibSel = 0;
	int LibSubSel = 0;
	int page = 0;
	int action;
	
	String path="";
	
	boolean isChange = true;
	boolean isShare = false;
	ArrayList<Edit> mEdit;
	ArrayList<Gallary> mGallary;
	ArrayList<Edit> mRedo;
	
	private Handler handler = new Handler() {public void handleMessage(Message msg) {mStart.adView.setVisibility(msg.what);}};
	public GameRenderer(Context context) 
	{
		mContext = context;
		mStart	= (Start)mContext;
		root = new Group(this);
		init();
	}

	void init() {
		try {
			mInAPP = new InApp();
			mInAPP.onCreate();
			mTex_SubICN = add("privew1.png");
			mTex_MainICN = new SimplePlane[2];
			mTex_MainICN[0] = add("privew2.png");
			mTex_MainICN[1] = add("privew0.png");
			mTex_ContiBtt = new SimplePlane[2];
			mTex_ContiBtt[0] = add("apply0.png");
			mTex_ContiBtt[1] = add("cancel0.png");
			mTex_ContIcn = new SimplePlane[2];
			mTex_ContIcn[0] = add("right.png");
			mTex_ContIcn[1] = add("cross.png");
			
			mTex_htt = add("hututugames.png");
			mTex_BG = add("bg_texture.png");
			mTex_Button = new SimplePlane[8];
			mTex_Button[0] = add("camera.png");
			mTex_Button[1] = add("gallery.png");
			mTex_Button[2] = add("about.png");
			mTex_Button[3] = add("options.png");
			mTex_Button[4] = add("like0.png");
			mTex_Button[5] = add("share0.png");
			mTex_Button[6] = add("join0.png");
			mTex_Button[7] = add("more0.png");

			mTex_Icon = new SimplePlane[8];
			mTex_Icon[0] = add("camera_icon.png");
			mTex_Icon[1] = add("gallery_icon.png");
			mTex_Icon[2] = add("about_icon.png");
			mTex_Icon[3] = add("options_icon.png");
			mTex_Icon[4] = add("like_icon.png");
			mTex_Icon[5] = add("share_icon.png");
			mTex_Icon[6] = add("google_icon.png");
			mTex_Icon[7] = add("star_icon.png");

			mTex_Popup = add("po_pup0.png");
			mTex_Priveiw = add("splash_box.png");
			mTex_Splash = add("splash_font.png");
			mTex_Back = add("back.png");
			mTex_AdsFree = add("ads_free.png");
			mTex_Buy = new SimplePlane[2];
			mTex_Buy[0] = add("later.png");
			mTex_Buy[1] = add("now.png");

			mTex_Arrow = new SimplePlane[2];
			mTex_Arrow[0] = add("left_arrow.png");
			mTex_Arrow[1] = add("right_arrow.png");

			mTex_RGB = new SimplePlane[4];
			mTex_RGB[0] = add("red_bar.png");
			mTex_RGB[1] = add("green_bar.png");
			mTex_RGB[2] = add("blue_bar.png");
			mTex_RGB[3] = add("select_bar.png");
			
			mTex_URL = new SimplePlane[3];
			mTex_URL[0] = add("library_icon.png");
			mTex_URL[1] = add("undo.png");
			mTex_URL[2] = add("redo.png");

			mTex_ClipIcn = new SimplePlane[4];
			mTex_ClipIcn[0] = add("menu_button.png");
			mTex_ClipIcn[1] = add("share_button.png");
			mTex_ClipIcn[2] = add("save_button.png");
			mTex_ClipIcn[3] = add("more_button.png");

			mTex_PopIcn = new SimplePlane[3];
			mTex_PopIcn[0] = add("share_sym.png");
			mTex_PopIcn[1] = add("save_sym.png");
			mTex_PopIcn[2] = add("discard_sym.png");

			mTex_PopBtt = new SimplePlane[3];
			mTex_PopBtt[0] = add("share.png");
			mTex_PopBtt[1] = add("save.png");
			mTex_PopBtt[2] = add("discard.png");

			mTex_About = add("about_text.png");

			mTex_EditIcn = new SimplePlane[5][];
			for (int i = 0; i < mTex_EditIcn.length; i++) {
				mTex_EditIcn[i] = new SimplePlane[2];
				for (int j = 0; j < mTex_EditIcn[i].length; j++) {
					mTex_EditIcn[i][j] = add("edit/" + i + "" + j + ".png");
				}
			}

			
			/*********************************************************************/
			mRedo		= new ArrayList<Edit>();
			mEdit		= new ArrayList<Edit>();
			mGallary		= new ArrayList<Gallary>();
			TGallary thread = new TGallary();
			thread.run();
		} catch (Exception e) {
			System.out.println(" Init = " + e.toString());

		}
	}

	void gameReset() {

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
//		if (mStart.adHouse != null) {
//			if (M.AppScreen == M.APPADD && !addFree) {
//				int inv = mStart.adHouse.getVisibility();
//				if (inv == AdView.INVISIBLE) {try {handler2.sendEmptyMessage(AdView.VISIBLE);} catch (Exception e) {}}
//			} else {
//				int inv = mStart.adHouse.getVisibility();
//				if (inv == AdView.VISIBLE) {try {handler2.sendEmptyMessage(AdView.INVISIBLE);} catch (Exception e) {}
//				}
//			}
//		}
//		long dt = System.currentTimeMillis() - startTime;
//		if (dt < 33)
//			try {
//				Thread.sleep(33 - dt);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		startTime = System.currentTimeMillis();
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

	void addBit(Bitmap mTempBit) {
		root.Counter = 0;
		mEdit.clear();
		SimplePlane mTemp = addBitmap(mTempBit);
		Edit tempe = new Edit(mTemp);
		tempe.lsy = tempe.lsx = 50 / mTemp.width();
		mEdit.add(tempe);
		M.AppScreen = M.APPEDIT;
		SharedPreferences prefs = mStart.getSharedPreferences("X", Start.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putInt("screen", M.AppScreen);
		editor.commit();
		path = M.DIR + "/" + System.currentTimeMillis() + ".jpg";
		clear();
		root.callAds();
	}
	void clear()
	{
		action = 0;
		Popup = 0;
		mRedo.clear();
		isChange = true;
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
	
	public class TGallary implements Runnable {
		public void run() {
			byte no[] = { 9, 9, 16, 17, 37, 49, 43, 68, 50, 16, 20, 30, 14, 11,
					40, 14, 50, 20, 20, 26 };
			mTex_Library = new SimplePlane[20][];
			for (int i = 0; i < mTex_Library.length; i++) {
				mTex_Library[i] = new SimplePlane[no[i]];
				for (int j = 0; j < mTex_Library[i].length; j++) {
					mTex_Library[i][j] = add("library/" + i + "/" + j + ".png");
				}
			}
			String dirPath = Environment.getExternalStorageDirectory() + M.DIR+"/";
			System.out.println(""+dirPath);
			File f = new File(dirPath);
			File[] files = f.listFiles();
			
			int val[] = new int[files.length];
			for (int i = 0; i < files.length ; i++) {
				val[i] = i;
			}
			
			for(int i=0;i<val.length;i++){
				for(int j=i+1;j<val.length;j++){
					if(files[val[i]].lastModified()<files[val[j]].lastModified()){
						int temp = val[i];
						val[i] = val[j];
						val[j] = temp;
					}
				}
			}

			for (int i = 0, j = 0; i < files.length && j < 3; i++) {
				File file = files[val[i]];
				if (file.isFile() && file.getName().endsWith(".jpg")){
					Bitmap bimap = Start.getBitmapGallary(file.getAbsolutePath());
					Gallary gallary = new Gallary(addBitmap(bimap), file.getAbsolutePath());
					gallary.mTexture.mBitmap = PTImg(gallary.mTexture.mBitmap);
					mGallary.add(gallary);
					j++;
				}
			}
		}
	}
	
}
