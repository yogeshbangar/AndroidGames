package com.apps.cropimage;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Align;
import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;

public class GameRenderer implements Renderer 
{
	final Group root;
	public static Context mContext;
	public static Start mStart;
	long startTime = System.currentTimeMillis();
	
	
	SimplePlane mTex_z[];
	SimplePlane mTex_Play;
	SimplePlane mTex_back;
	SimplePlane mTex_camera;
	SimplePlane mTex_clipart;
	SimplePlane mTex_cross;
	SimplePlane mTex_effect;
	SimplePlane mTex_file;
	SimplePlane mTex_flipH;
	SimplePlane mTex_flipV;
	SimplePlane mTex_frame;
	SimplePlane mTex_Mod[];
	SimplePlane mTex_more;
	SimplePlane mTex_plus;
	SimplePlane mTex_r[];
	SimplePlane mTex_right;
	SimplePlane mTex_rotate;
	SimplePlane mTex_save;
	SimplePlane mTex_selectPhoto;
	SimplePlane mTex_share;
	SimplePlane mTex_text;
	SimplePlane mTex_undo;
	SimplePlane mTex_zoom;
	SimplePlane mTex_Crop;
	SimplePlane mTex_RGBBar;
	SimplePlane mTex_RGBSel;
	
	int LibSel,page,LibSubSel;
	SimplePlane mTex_BG,mTex_Library[][],mTex_SubICN,mTex_Arw[],mTex_Main[];
	SimplePlane mTex_Splash,mTex_Start,mTex_Spl[],mTex_Logo;
	SimplePlane mTex_Box;
	Bitmap mBitmap;
	Canvas mFontCnVs;
	Paint mPaint;

	ClipArt mClipArt;
	
	static ArrayList<EditImg> mEditImg = new ArrayList<EditImg>();
	ArrayList<Gellary> mGellary = new ArrayList<Gellary>();
	
	int mMode = 0;
	boolean showPopup = false;
	boolean isShare,isMenu;
	String PhotoPath = "";
	public GameRenderer(Context context) 
	{
		mContext = context;
		mStart	= (Start)mContext;
		root = new Group(this);
		mClipArt = new ClipArt(this);
		init();
	}
	void init()
	{
		try
		{
			
			mTex_BG = add("bg.jpg");
			mTex_Box= add("box.png");
			byte no[] = { 9, 42, 16, 17, 37, 49, 43, 68, 50, 16, 20, 30, 14, 11,
					40, 14, 50, 20, 20, 26 };
			mTex_Library = new SimplePlane[20][];
			for (int i = 0; i < mTex_Library.length; i++) {
				mTex_Library[i] = new SimplePlane[no[i]];
				for (int j = 0; j < mTex_Library[i].length; j++) {
					mTex_Library[i][j] = add("library/" + i + "/" + j + ".png");
				}
			}
			mTex_SubICN = add("privew1.png");
			mTex_Arw = new SimplePlane[2];
			mTex_Arw[0] = add("l_arrow.png");
			mTex_Arw[1] = add("r_arrow.png");
			
			mTex_Main = new SimplePlane[2];
			mTex_Main[0] = add("privew2.png");
			mTex_Main[1] = add("privew0.png");

			mTex_Splash = add("splash.jpg");
			mTex_Start = add("start_btn.png");
			mTex_Logo = add("logo.png");

			mTex_Spl = new SimplePlane[5];
			for (int i = 0; i < mTex_Spl.length; i++)
				mTex_Spl[i] = add("sp" + i + ".png");

			
			
			mBitmap = Bitmap.createBitmap(512, 64, Config.ARGB_8888);
			mFontCnVs = new Canvas(mBitmap);
			mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			mPaint.setColor(Color.WHITE);
			mPaint.setTextAlign(Align.CENTER);
			mPaint.setTextSize(32);
			Typeface tf = Typeface.createFromAsset(mContext.getAssets(),"andyb.ttf");
			mPaint.setTypeface(tf);
			mFontCnVs.drawText("Enter Text", 256, 32, mPaint);

			
			
			
			mTex_Play = add("playicon.png");
			mTex_back = add("Ui/back.png");
			mTex_camera = add("Ui/camera.png");
			mTex_clipart = add("Ui/clipart.png");
			mTex_cross = add("Ui/cross.png");
			mTex_effect = add("Ui/effect.png");
			mTex_file = add("Ui/file.png");
			mTex_flipH = add("Ui/flipH.png");
			mTex_flipV = add("Ui/flipV.png");
			mTex_frame = add("Ui/frame.png");
			mTex_Mod = new SimplePlane[4];
			mTex_Mod[0] = add("Ui/frame-mode0.png");
			mTex_Mod[1] = add("Ui/frame-mode1.png");
			mTex_Mod[2] = add("Ui/clipart-mode.png");
			mTex_Mod[3] = add("Ui/clipart-mode2.png");

			mTex_more = add("Ui/more.png");
			mTex_plus = add("Ui/plus.png");
			mTex_r = new SimplePlane[4];
			for(int i =0;i<mTex_r.length;i++)
				mTex_r[i] = add("Ui/r" + i + ".png");
			mTex_right = add("Ui/right.png");
			mTex_rotate = add("Ui/rotate.png");
			mTex_save = add("Ui/save.png");
			mTex_selectPhoto = add("Ui/select-photo.png");
			mTex_share = add("Ui/share.png");
			mTex_text = add("Ui/text.png");
			mTex_undo = add("Ui/undo.png");
			mTex_z = new SimplePlane[6];
			for(int i =0;i<mTex_z.length;i++)
				mTex_z[i] = add("Ui/z" + i + ".png");
			mTex_zoom = add("Ui/zoom.png");
			mTex_Crop = add("Ui/crop.png");
			mTex_RGBBar = add("blue_bar.png");
			mTex_RGBSel = add("select_bar.png");
			gameReset();
		}catch(Exception e){}
		
	}
	void gameReset()
	{
		mMode =0;
		mEditImg.clear();
		mGellary.clear();
		
		PhotoPath = M.DIR + "/" + System.currentTimeMillis() + ".jpg";
		isShare = false;
		isMenu = false;
		root.screenshot = false;
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
	}

	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		
		if(M.GameScreen == M.GAMEPLAY){
			root.draw(gl);
		}else
		{
			mClipArt.draw(gl);
		}
		
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f,100.0f);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
	}
	SimplePlane add(String ID) {
		SimplePlane SP = null;
		Bitmap b = LoadImgfromAsset(ID);
		try {
			SP = new SimplePlane((b.getWidth() / M.mMaxY),(b.getHeight() / M.mMaxY));
			SP.loadBitmap(b);
		} catch (Exception e) {
		}
		return SP;
	}

	static SimplePlane addBitmap(Bitmap b) {
		SimplePlane SP = null;
		try {
			SP = new SimplePlane((b.getWidth() / M.mMaxY),(b.getHeight() / M.mMaxY));
			SP.loadBitmap(b);
		} catch (Exception e) {
		}
		return SP;
	}

	SimplePlane addBitmapFram(Bitmap b, float fvx, float fvy) {
		SimplePlane SP = null;
		try {
			SP = new SimplePlane(fvx, fvy);
			SP.loadBitmap(b);
		} catch (Exception e) {
		}
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
	Bitmap FlipVerticle(Bitmap bitmapOrg) {
		Matrix matrix = new Matrix();
		matrix.postScale(1f, -1f);
		matrix.postRotate(0);
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0,
				bitmapOrg.getWidth(), bitmapOrg.getHeight(), matrix, true);
		return resizedBitmap;
	}
}
