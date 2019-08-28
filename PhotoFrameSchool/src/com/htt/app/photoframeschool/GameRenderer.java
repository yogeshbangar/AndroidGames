package com.htt.app.photoframeschool;


import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;

public class GameRenderer implements Renderer 
{
	final Group root;
	public static Context mContext;
	public static Start mStart;
	long startTime = System.currentTimeMillis();
	
	SimplePlane[] mTex_Zoom,mTex_Rotate,mTex_MainICN,mTex_Library[],mTex_Arrow,mTex_Mode,mTex_SplBtn,mTex_Grid[];
	SimplePlane mTex_Undo, mTex_BackBtn,  mTex_ClrBtn,   mTex_SaveBtn;
	SimplePlane mTex_ShareBtn, mTex_TextBtn, mTex_Bar, mTex_Clipart,  mTex_Effect, mTex_flipH;
	SimplePlane mTex_FlipV, mTex_RotBtn, mTex_ZoomBtn,mTex_Test,mTex_RGBBar,mTex_RGBSel,mTex_SubICN;
	SimplePlane mTex_Ok,mTex_Cross,mTex_More,mTex_Camera,mTex_File,mTex_Popup,mTex_Plus;
	SimplePlane mTex_Splash,mTex_Start,mTex_Logo;
	
	Bitmap mBitmap;
	Canvas mCanvas;
	Paint mPaint;
	SimplePlane mTex_BG;
	boolean addFree = false;
	ArrayList<Frame> mFrames;
	ArrayList<FrameTex> mTex_Frame;
	
	int mMode =0;
	int mFramImg = 0;
	int mFramPage = 2;
	int mImgSel = 0;
	int action =0;
	int LibSel = 7;
	int LibSubSel = 0;
	int page = 0;
	boolean showPopup = false;
	ArrayList<Edit> mEdit;
	
	String mFrameWay = "";
	boolean isShare = false;
	boolean isMenu = false;
	//	private Handler handler = new Handler() {public void handleMessage(Message msg) {mStart.adView.setVisibility(msg.what);}};
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
			mBitmap = Bitmap.createBitmap(512, 64,Config.ARGB_8888);
			mCanvas = new Canvas(mBitmap);
			mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			mPaint.setColor(Color.WHITE);
			mPaint.setTextAlign(Align.CENTER);
			mPaint.setTextSize(32);
			Typeface tf = Typeface.createFromAsset(mContext.getAssets(),"andyb.ttf");
			mPaint.setTypeface(tf);
			mCanvas.drawText("Yogesh Bangar \n ram jane", 256,32, mPaint);
			
			mTex_SplBtn = new SimplePlane[5];
			for(int i =0;i<mTex_SplBtn.length;i++)
				mTex_SplBtn[i]	= add("spl"+i+".png");

			mTex_Splash	= add("splash.jpg");
			mTex_Start	= add("start-btn.png");
			mTex_Logo	= add("logo.png");
			
			byte grd[] = { 8, 13, 9, 7, 8, 10, 8, 8};
			
			mTex_Grid = new SimplePlane[grd.length][];
			for (int i = 0; i < mTex_Grid.length; i++) {
				mTex_Grid[i] = new SimplePlane[grd[i]];
				for (int j = 0; j < mTex_Grid[i].length; j++) {
					mTex_Grid[i][j] = add("frame64/" + i + "/" + j + ".png");
				}
			}
			
			mTex_SubICN = add("privew1.png");
			mTex_Arrow = new SimplePlane[2];
			mTex_Arrow[0] = add("left_arrow.png");
			mTex_Arrow[1] = add("right_arrow.png");
			
			byte no[] = { 9, 9, 16, 17, 37, 49, 43, 68, 50, 16, 20, 30, 14, 11,
					40, 14, 50, 20, 20, 26 };
			mTex_Library = new SimplePlane[20][];
			for (int i = 0; i < mTex_Library.length; i++) {
				mTex_Library[i] = new SimplePlane[no[i]];
				for (int j = 0; j < mTex_Library[i].length; j++) {
					mTex_Library[i][j] = add("library/" + i + "/" + j + ".png");
				}
			}
			mTex_MainICN = new SimplePlane[2];
			mTex_MainICN[0] = add("privew2.png");
			mTex_MainICN[1] = add("privew0.png");
			
			mTex_RGBBar	= add("blue_bar.png");
			mTex_RGBSel	= add("select_bar.png");
			mTex_Undo	= add("Ui/undo_btn.png");
			mTex_BackBtn= add("Ui/back-btn.png");
			mTex_Bar	= add("Ui/bar.png");
			mTex_ClrBtn	= add("Ui/colour_frame.png");
			mTex_More 	= add("Ui/more.png");
			mTex_Camera	= add("Ui/camera.png");
			mTex_File	= add("Ui/file.png");
			mTex_Popup	= add("Ui/popup.png");
			mTex_Plus	= add("Ui/plus.png");
			mTex_Mode = new SimplePlane[4];
			mTex_Mode[0] 	= add("Ui/frame_mode0.png");
			mTex_Mode[1] 	= add("Ui/frame_mode1.png");
			mTex_Mode[2] 	= add("Ui/clipart_mode0.png");
			mTex_Mode[3] 	= add("Ui/clipart_mode1.png");
			
			mTex_SaveBtn	= add("Ui/save-btn.png");
			mTex_ShareBtn	= add("Ui/share-btn.png");
			mTex_TextBtn	= add("Ui/text-btn.png");
			
			mTex_Clipart	= add("Ui/clipart-btn.png");
			mTex_Effect		= add("Ui/effect-btn.png");
			mTex_flipH		= add("Ui/flip-h-btn.png");
			mTex_FlipV		= add("Ui/flip-v-btn.png");
			mTex_RotBtn		= add("Ui/rotate-btn.png");
			mTex_ZoomBtn	= add("Ui/zoom-btn.png");
			
			mTex_Zoom		= new SimplePlane[6];
			for (int i = 0; i < mTex_Zoom.length; i++) {
				mTex_Zoom[i]= add("Ui/z"+i+".png");
			}
			mTex_Rotate		= new SimplePlane[4];
			for (int i = 0; i < mTex_Rotate.length; i++) {
				mTex_Rotate[i]= add("Ui/r"+i+".png");
			}
			
			mTex_Test		= add("ball.png");
			
			
//			mTex_Frame = new SimplePlane[1];
			mTex_Frame = new ArrayList<FrameTex>();
			mTex_Frame.add(new FrameTex(add("frame/1/0.png")));
			
			mTex_BG = add("bg.png");
			mTex_Ok = add("Ui/right.png");
			mTex_Cross = add("Ui/cross.png");
			
			mFrames = new ArrayList<Frame>();
			mEdit	= new ArrayList<Edit>();
			
//			mTe = new SimplePlane[9];
//			for(int i =0;i<9;i++)
//			{
//				mTe[i] = add(i+".jpg");
//			}
//			gameReset();
		}catch(Exception e){}
		
	}
//	SimplePlane mTe[];
	
	void gameReset(){
		mEdit.clear();
		mImgSel = 0;
		mMode = 0;
		mFrameWay = M.DIR + "/" + System.currentTimeMillis() + ".jpg";
		isShare = false;
		isMenu = false;
		framUpdate();
		mStart.load();
	}
	void framUpdate(){
		System.out.println(mFramPage+"   "+mFramImg);
		mFrames.clear();
		for(int i =0;i<FrameArr.Arr[mFramPage][mFramImg].length;i++){
			mFrames.add(new Frame(
					root.world2screenx((FrameArr.Arr[mFramPage][mFramImg][i][0]/1000f)),
					root.world2screenY((FrameArr.Arr[mFramPage][mFramImg][i][1]/1000f)),
					(int)(M.ScreenHieght*(FrameArr.Arr[mFramPage][mFramImg][i][2]/1000f)), 
					(int)(M.ScreenHieght*(FrameArr.Arr[mFramPage][mFramImg][i][3]/1000f)),
					(FrameArr.Arr[mFramPage][mFramImg][i][0]/1000f)+(FrameArr.Arr[mFramPage][mFramImg][i][2]/1000f),
					-((FrameArr.Arr[mFramPage][mFramImg][i][1]/1000f)-(FrameArr.Arr[mFramPage][mFramImg][i][3]/1000f))));

			/*System.out.println(mFramImg+"  "+i+"  "+
					((FrameArr.Arr[mFramPage][mFramImg][i][0]/1000f))+"  "+
					((FrameArr.Arr[mFramPage][mFramImg][i][1]/1000f))+"  "+
					((FrameArr.Arr[mFramPage][mFramImg][i][2]/1000f))+"  "+
					((FrameArr.Arr[mFramPage][mFramImg][i][3]/1000f))
					);*/
			
		}
		
		
		mTex_Frame.clear();
		mTex_Frame.add(new FrameTex(add("frame/"+mFramPage+"/"+mFramImg+".png")));
	}
	
	
	void addBit(Bitmap mTempBit) {
		root.Counter = 0;
		int val = 2;
		if(FrameArr.Arr[mFramPage][mFramImg][mImgSel][2]<FrameArr.Arr[mFramPage][mFramImg][mImgSel][3]){
			val = 3;
		}
		
		float ax = (FrameArr.Arr[mFramPage][mFramImg][mImgSel][val]/1000f);
		float ay = (FrameArr.Arr[mFramPage][mFramImg][mImgSel][val]/1000f);
		
		float fvx = (mTempBit.getWidth()/M.mMaxY)*ax*2;
		float fvy = (mTempBit.getHeight()/M.mMaxY)*ay*2;
		
		mFrames.get(mImgSel).mTexture = addBitmapFram(mTempBit, fvx, fvy);
		
		System.out.println(fvx + "      " + fvy+"  "+ax + "      " + ay);
		
		
		
		mFrames.get(mImgSel).Reset();
		showPopup = false;
		M.AppScreen = M.APPPLAY;
		SharedPreferences prefs = mStart.getSharedPreferences("X", Start.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putInt("screen", M.AppScreen);
		editor.commit();
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
			SP = new SimplePlane((b.getWidth()/M.mMaxY),(b.getHeight()/M.mMaxY));
			SP.loadBitmap(b);// R.drawable.jay
		}catch(Exception e){}
		return SP;
	}
	SimplePlane addBitmapFram(Bitmap b,float fvx,float fvy)
	{
		SimplePlane SP = null;
		try
		{
			SP = new SimplePlane(fvx,fvy);
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
			System.out.println(""+ID+"!!!!!!!!!!!!!!!!!!!!!~~~~~~~~~~~~~!!!!!!!!!!!!!!!!!!!!!!!!!"+e.getMessage());
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
	Bitmap FlipVerticle(Bitmap bitmapOrg)
	{
		Matrix matrix = new Matrix();
		matrix.postScale(1f, -1f);
		matrix.postRotate(0);
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0,bitmapOrg.getWidth(), bitmapOrg.getHeight(), matrix, true);
		return resizedBitmap;
	}
}
