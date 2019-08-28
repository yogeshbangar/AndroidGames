package com.hututu.game.M2048;

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
	public static Context mContext;
	public static Start mStart;
	long startTime = System.currentTimeMillis();
	int resumeCounter =0;
	int mSel = 0;
	SimplePlane[] mTex_Font,mTex_Block,mTex_Sound,mTex_Target0,mTex_Icn,mTex_Target;
	SimplePlane mTex_Logo,mTex_Pls,mTex_X,mTex_Cross,mTex_LingBar,mTex_Ling,mTex_Target_box,mTex_Top_pattern,mTex_Yes;
	SimplePlane mTex_AboutTxt,mTex_aboutFont,mTex_AreU,mTex_Back,mTex_BestScore,mTex_Bg,mTex_Big_popup,mTex_DSelect;
	SimplePlane mTex_CongratsPopup,mTex_Congratulation,mTex_ContinueBtn,mTex_ContinueFont,mTex_ControlIcon,mTex_uFont;
	SimplePlane mTex_DoU,mTex_DoUSave,mTex_Fill,mTex_GameBoard,mTex_GameEnd,mTex_Star0,mTex_SwiptoMove,mTex_Gameover;
	SimplePlane mTex_HelpBtn,mTex_HelpFont,mTex_LeaderBoard,mTex_LeaderNo,mTex_Mark,mTex_MenuB,mTex_$,mTex_Bottum;
	SimplePlane mTex_NewGameBtn,mTex_NoBtn,mTex_Play,mTex_RestartBtn,mTex_RestartFont,mTex_Save_font,mTex_ScoreBox;
	SimplePlane mTex_ScoreFont,mTex_ScorePreview,mTex_Select,mTex_Small_popup,mTex_SplashFont,mTex_chalg,mTex_join;
	SimplePlane mTex_Later,mTex_Hand,mTex_Arrow,mTex_Arrow1,Ads_Free;
	
	
	
	boolean addFree = false;
	int mLevel = 0;
	int mScore = 0;
	int mHScore = 0;
	int max = 2;
	Number[][] mNum;
	boolean ach[] = new boolean[5];
	int moveDirection =0;
	int goCounter = 0;
	int mHelp = 0;
	
	_InApp mInApp;
	
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
		try {
			mInApp = new _InApp();
			mInApp.onCreate();
			mTex_Hand = add("help_hand.png");
			mTex_Arrow = add("help_arrow.png");
			mTex_Arrow1 = add("help_arrow1.png");
			mTex_Later = add("later_button.png");
			mTex_chalg = add("challenge.png");
			mTex_join = add("join_button.png");
			mTex_Bottum = add("bottum_pattern.png");
			mTex_Cross = add("cross.png");
			mTex_Ling = add("loading0.png");
			mTex_LingBar = add("loading1.png");
			mTex_Logo = add("hututugames.png");
			Ads_Free = add("ads_free.png");
			mTex_Block = new SimplePlane[11];
			for (int i = 0; i < mTex_Block.length; i++) {
				mTex_Block[i] = add(i + ".png");
			}

			mTex_Icn = new SimplePlane[5];
			mTex_Icn[0] = add("about_icon.png");
			mTex_Icn[1] = add("help_icon.png");
			mTex_Icn[2] = add("hututu_icon.png");
			mTex_Icn[3] = add("facebook_icon.png");
			mTex_Icn[4] = add("google_icon.png");
			
			
			mTex_AboutTxt = add("about_text.png");
			mTex_aboutFont = add("aboutus_font.png");
			mTex_AreU = add("areyou_sure.png");
			mTex_Back = add("back.png");
			mTex_BestScore = add("best_score_box.png");
			mTex_Bg = add("bg_PNG.png");
			mTex_Big_popup = add("big_popup.png");
			mTex_CongratsPopup = add("congrats_popup.png");
			mTex_Congratulation = add("congratulation_font.png");
			mTex_ContinueBtn = add("continue_button.png");
			mTex_ContinueFont = add("continue_font.png");
			mTex_ControlIcon = add("control_icon.png");
			mTex_DSelect = add("de_select.png");
			mTex_DoU = add("doyou_want_font.png");
			mTex_DoUSave = add("doyou_want_save.png");
			mTex_Fill = add("fill_bar.png");
			mTex_GameBoard = add("game-board.png");
			mTex_GameEnd = add("gameend_font.png");
			mTex_Gameover = add("gameover_font.png");
			mTex_HelpBtn = add("help_button.png");
			mTex_HelpFont = add("help_font.png");
			mTex_LeaderBoard = add("achievements.png");
			mTex_LeaderNo = add("leader_number.png");
			mTex_Mark = add("mark.png");
			mTex_MenuB = add("menu_button.png");
			mTex_NewGameBtn = add("newgame_button.png");
			mTex_NoBtn = add("no_button.png");
			mTex_Play = add("play_button.png");
			mTex_RestartBtn = add("restart_button.png");
			mTex_RestartFont = add("restart_font.png");
			mTex_Save_font = add("save_font.png");
			mTex_ScoreBox = add("score_box.png");
			mTex_ScoreFont = add("score_font.png");
			mTex_ScorePreview = add("score_preview.png");
			mTex_Select = add("select.png");
			mTex_Small_popup = add("small_popup.png");
			mTex_Sound = new SimplePlane[2];
			mTex_Sound[0] = add("sound_on_icon.png");
			mTex_Sound[1] = add("sound_off_icon.png");
			mTex_SplashFont = add("splash_font.png");
			mTex_Star0 = add("star0.png");
			mTex_SwiptoMove = add("swipto_move.png");
			
			mTex_Target = new SimplePlane[4];	
			mTex_Target[0] = add("target_512.png");
			mTex_Target[1] = add("target_1024.png");
			mTex_Target[2] = add("target_2048.png");
			mTex_Target[3] = add("target_Endless.png");
			
			mTex_Target0 = new SimplePlane[4];
			mTex_Target0[0] = add("target_5120.png");
			mTex_Target0[1] = add("target_10240.png");
			mTex_Target0[2] = add("target_20480.png");
			mTex_Target0[3] = add("endless.png");
			
			mTex_Target_box = add("target_box.png");
			mTex_Top_pattern = add("top_pattern.png");
			mTex_Yes = add("yes_button.png");
			mTex_uFont = add("you_have_font.png");

			// mTex_Block = add("155.png");
			// mTex_RBlock = add("156.png");
			load_Font();
			mNum = new Number[4][];
			for (int i = 0; i < mNum.length; i++) {
				mNum[i] = new Number[4];
				for (int j = 0; j < mNum[i].length; j++) {
					mNum[i][j] = new Number();
				}
			}
			for(int i=0;i<ach.length;i++)
			{
				ach[i] = false;
			}
			gameReset();
		} catch (Exception e) {
		}

	}
	
	int k = 0;
	void gameReset()
	{
		mScore = 0;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
		for(int i =0;i<mNum.length;i++)
		{
			for(int j =0;j<mNum[i].length;j++)
			{
				mNum[i][j].num = 0;
			}
		}
		mNum[1][0].num = 2;
		mNum[1][1].num = 2;
		max = 2;
		goCounter = 0;
		root.scot =0;
		try{root.handler.sendEmptyMessage(0);}catch(Exception e){}
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
			if(resumeCounter>10 && !addFree)
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
			System.out.println("["+ID+"]   !!!!!!!!!!!!!!!!!!!!!~~~~~~~~~~~~~!!!!!!!!!!!!!!!!!!!!!!!!!"+e.getMessage());
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
	void load_Font() {
		{
			mTex_Font = new SimplePlane[10];
			Bitmap b = LoadImgfromAsset("font_strip.png");
			for (int i = 0; i < mTex_Font.length; i++)
				mTex_Font[i] = addBitmap(Bitmap.createBitmap(b, i * b.getWidth()/ 16, 0, b.getWidth() / 16,b.getHeight(), null, true));
			mTex_$= addBitmap(Bitmap.createBitmap(b, 10 * b.getWidth()/ 16, 0, b.getWidth() / 16,b.getHeight(), null, true));
			mTex_X= addBitmap(Bitmap.createBitmap(b, 11 * b.getWidth()/ 16, 0, b.getWidth() / 16,b.getHeight(), null, true));
			mTex_Pls= addBitmap(Bitmap.createBitmap(b, 12 * b.getWidth()/ 16, 0, b.getWidth() / 16,b.getHeight(), null, true));
		}
		

	}
}
