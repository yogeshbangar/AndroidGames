package com.httgames.app.stylishframestudio;

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

public class ZRenderer implements Renderer {
	final Group root;
	public static Context mContext;
	public static Start mStart;
	long startTime = System.currentTimeMillis();

	APLAN[][] mTex_Grid, mTex_Library;
	APLAN[] mTex_Zom, mTex_Rot, mTex_Main, mTex_Arw, mTex_Mod, mTex_Spl;
	APLAN mTex_Cliprt, mTex_RotBtn, mTex_RGBBar, mTex_BG, mTex_ShareBtn;
	APLAN mTex_ClrBtn, Tex_Effect, mTex_flipH, mTex_Efect, mTex_BackBtn; 
	APLAN mTex_Text, mTex_Zoom, mTex_FlipV, mTex_Undo,mTex_Logo,mTex_Ok;
	APLAN mTex_Splash, mTex_Start, mTex_Popup, mTex_RGBSel, mTex_SubICN;
	APLAN mTex_Cros, mTex_More, mTex_Cam, mTex_Save,mTex_Plus,mTex_File;

	Bitmap mBitmap;
	Canvas mFontCnVs;
	Paint mPaint;

	boolean showPopup = false;
	boolean addFree = false;
	boolean isShare = false;
	boolean isMenu = false;

	int action = 0;
	int mFramImg = 0;
	int mMode = 0;
	int mFramPage = 2;
	int mImgSel = 0;
	int LibSel = 7;
	int LibSubSel = 0;
	int page = 0;

	String PhotoPath = "";

	ArrayList<Skeleton> mSkeleton;
	ArrayList<SkeletonTex> mTex_Frame;
	ArrayList<Gellary> mGellary;

	public ZRenderer(Context context) {
		mContext = context;
		mStart = (Start) mContext;
		root = new Group(this);
		init();
	}

	void init() {
		try {
			mBitmap = Bitmap.createBitmap(512, 64, Config.ARGB_8888);
			mFontCnVs = new Canvas(mBitmap);
			mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			mPaint.setColor(Color.WHITE);
			mPaint.setTextAlign(Align.CENTER);
			mPaint.setTextSize(32);
			Typeface tf = Typeface.createFromAsset(mContext.getAssets(),"andyb.ttf");
			mPaint.setTypeface(tf);
			mFontCnVs.drawText("Enter Text", 256, 32, mPaint);

			mTex_Spl = new APLAN[5];
			for (int i = 0; i < mTex_Spl.length; i++)
				mTex_Spl[i] = add("sp" + i + ".png");

			mTex_Splash = add("splash.jpg");
			mTex_Start = add("start_btn.png");
			mTex_Logo = add("logo.png");

			byte grd[] = { 8, 12, 10, 7, 8, 10, 8, 8 };

			mTex_Grid = new APLAN[grd.length][];
			for (int i = 0; i < mTex_Grid.length; i++) {
				mTex_Grid[i] = new APLAN[grd[i]];
				for (int j = 0; j < mTex_Grid[i].length; j++) {
					mTex_Grid[i][j] = add("frame64/" + i + "/" + j + ".png");
				}
			}

			mTex_SubICN = add("privew1.png");
			mTex_Arw = new APLAN[2];
			mTex_Arw[0] = add("l_arrow.png");
			mTex_Arw[1] = add("r_arrow.png");

			byte no[] = { 9, 42, 16, 17, 37, 49, 43, 68, 50, 16, 20, 30, 14, 11,
					40, 14, 50, 20, 20, 26 };
			mTex_Library = new APLAN[20][];
			for (int i = 0; i < mTex_Library.length; i++) {
				mTex_Library[i] = new APLAN[no[i]];
				for (int j = 0; j < mTex_Library[i].length; j++) {
					mTex_Library[i][j] = add("library/" + i + "/" + j + ".png");
				}
			}
			mTex_Main = new APLAN[2];
			mTex_Main[0] = add("privew2.png");
			mTex_Main[1] = add("privew0.png");

			mTex_RGBBar = add("blue_bar.png");
			mTex_RGBSel = add("select_bar.png");
			mTex_Undo = add("Ui/undo.png");
			mTex_BackBtn = add("Ui/back.png");
			mTex_ClrBtn = add("Ui/frame.png");
			mTex_More = add("Ui/more.png");
			mTex_Cam = add("Ui/camera.png");
			mTex_File = add("Ui/file.png");
			mTex_Popup = add("Ui/select-photo.png");
			mTex_Plus = add("Ui/plus.png");
			mTex_Mod = new APLAN[4];
			mTex_Mod[0] = add("Ui/frame-mode0.png");
			mTex_Mod[1] = add("Ui/frame-mode1.png");
			mTex_Mod[2] = add("Ui/clipart-mode.png");
			mTex_Mod[3] = add("Ui/clipart-mode2.png");

			mTex_Save = add("Ui/save.png");
			mTex_ShareBtn = add("Ui/share.png");
			mTex_Text = add("Ui/text.png");

			mTex_Cliprt = add("Ui/clipart.png");
			mTex_Efect = add("Ui/effect.png");
			mTex_flipH = add("Ui/flipH.png");
			mTex_FlipV = add("Ui/flipV.png");
			mTex_RotBtn = add("Ui/rotate.png");
			mTex_Zoom = add("Ui/zoom.png");

			mTex_Zom = new APLAN[6];
			for (int i = 0; i < mTex_Zom.length; i++) {
				mTex_Zom[i] = add("Ui/z" + i + ".png");
			}
			mTex_Rot = new APLAN[4];
			for (int i = 0; i < mTex_Rot.length; i++) {
				mTex_Rot[i] = add("Ui/r" + i + ".png");
			}

			mTex_Frame = new ArrayList<SkeletonTex>();
			mTex_Frame.add(new SkeletonTex(add("frame/1/0.png")));

			mTex_BG = add("bg.jpg");
			mTex_Ok = add("Ui/right.png");
			mTex_Cros = add("Ui/cross.png");

			mSkeleton = new ArrayList<Skeleton>();
			mGellary = new ArrayList<Gellary>();
		} catch (Exception e) {
		}

	}

	void gameReset() {
		mGellary.clear();
		mImgSel = 0;
		mMode = 0;
		PhotoPath = M.DIR + "/" + System.currentTimeMillis() + ".jpg";
		isShare = false;
		isMenu = false;
		framUpdate();
		mStart.load();
	}

	void framUpdate() {
		System.out.println(mFramPage + "   " + mFramImg);
		mSkeleton.clear();
		for (int i = 0; i < Draft.Rectangle[mFramPage][mFramImg].length; i++) {
			mSkeleton
					.add(new Skeleton(
							root.w2sX((Draft.Rectangle[mFramPage][mFramImg][i][0] / 1000f)),
							root.w2sY((Draft.Rectangle[mFramPage][mFramImg][i][1] / 1000f)),
							(int) (M.ScreenHieght * (Draft.Rectangle[mFramPage][mFramImg][i][2] / 1000f)),
							(int) (M.ScreenHieght * (Draft.Rectangle[mFramPage][mFramImg][i][3] / 1000f)),
							(Draft.Rectangle[mFramPage][mFramImg][i][0] / 1000f)
									+ (Draft.Rectangle[mFramPage][mFramImg][i][2] / 1000f),
							-((Draft.Rectangle[mFramPage][mFramImg][i][1] / 1000f) - (Draft.Rectangle[mFramPage][mFramImg][i][3] / 1000f))));
		}

		mTex_Frame.clear();
		mTex_Frame.add(new SkeletonTex(add("frame/" + mFramPage + "/"
				+ mFramImg + ".png")));
	}

	void addBit(Bitmap mTempBit) {
		root.Counter = 0;
		int val = 2;
		if (Draft.Rectangle[mFramPage][mFramImg][mImgSel][2] < Draft.Rectangle[mFramPage][mFramImg][mImgSel][3]) {
			val = 3;
		}

		float ax = (Draft.Rectangle[mFramPage][mFramImg][mImgSel][val] / 1000f);
		float ay = (Draft.Rectangle[mFramPage][mFramImg][mImgSel][val] / 1000f);

		float fvx = (mTempBit.getWidth() / M.mMaxY) * ax * 2;
		float fvy = (mTempBit.getHeight() / M.mMaxY) * ay * 2;

		mSkeleton.get(mImgSel).mTexture = addBitmapFram(mTempBit, fvx, fvy);

		System.out.println(fvx + "      " + fvy + "  " + ax + "      " + ay);

		mSkeleton.get(mImgSel).Reset();
		showPopup = false;
		M.AppScreen = M.APPPLAY;
		SharedPreferences prefs = mStart.getSharedPreferences("X",
				Start.MODE_PRIVATE);
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

	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		root.draw(gl);
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f,
				100.0f);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	APLAN add(String ID) {
		APLAN SP = null;
		Bitmap b = LoadImgfromAsset(ID);
		try {
			SP = new APLAN((b.getWidth() / M.mMaxY),(b.getHeight() / M.mMaxY));
			SP.loadBitmap(b);
		} catch (Exception e) {
		}
		return SP;
	}

	APLAN addBitmap(Bitmap b) {
		APLAN SP = null;
		try {
			SP = new APLAN((b.getWidth() / M.mMaxY),(b.getHeight() / M.mMaxY));
			SP.loadBitmap(b);
		} catch (Exception e) {
		}
		return SP;
	}

	APLAN addBitmapFram(Bitmap b, float fvx, float fvy) {
		APLAN SP = null;
		try {
			SP = new APLAN(fvx, fvy);
			SP.loadBitmap(b);
		} catch (Exception e) {
		}
		return SP;
	}

	Bitmap LoadImgfromAsset(String ID) {
		try {
			return BitmapFactory.decodeStream(mContext.getAssets().open(ID));
		} catch (Exception e) {
			System.out.println(ID + " ~~~ " + e.getMessage());
			return null;
		}
	}

	Bitmap resizeImg(Bitmap bitmapOrg, int newWidth, int newHeight) {
		int width = bitmapOrg.getWidth();
		int height = bitmapOrg.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		matrix.postRotate(0);
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, width,
				height, matrix, true);
		return resizedBitmap;
	}

	Bitmap FlipHorizontal(Bitmap bitmapOrg) {
		Matrix matrix = new Matrix();
		matrix.postScale(-1f, 1f);
		matrix.postRotate(0);
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0,
				bitmapOrg.getWidth(), bitmapOrg.getHeight(), matrix, true);
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
