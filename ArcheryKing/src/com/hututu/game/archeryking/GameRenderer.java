package com.hututu.game.archeryking;
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
	int resumeCounter =0;
	int mSel = 0;
	
	SimplePlane   mTex_Arrow,mTex_Target,mTex_ComboText,mTex_ArrowCut,mTex_PausedArrow,mTex_Angle2,mTex_Score;
	SimplePlane[] mTex_Bow,mTex_Text,mTex_Combo,mTex_Font,mTex_Dot,mTex_Power,mTex_Character,mTex_Walk,mTex_Ach; 
	
	SimplePlane   mTex_AboutI,mTex_About,mTex_Achievment,mTex_Angle0,mTex_Angle1,mTex_Splash_font;
	SimplePlane   mTex_ArrowsBar,mTex_Back,mTex_Color,mTex_Facebook,mTex_Flag_bg;
	SimplePlane   mTex_Googleplay,mTex_HelpI,mTex_Highscore,mTex_Home,mTex_Node0,mTex_Node1,mTex_Share;
	SimplePlane   mTex_Pause,mTex_Playagain,mTex_Play,/*mTex_Rateus,*/mTex_Reset,mTex_Resume,mTex_Logo;
	SimplePlane   mTex_Aboutus,mTex_Archery,mTex_Help,mTex_HelpText,mTex_HighscoreT,mTex_Failed,mTex_Paused;
	
	SimplePlane   mTex_Pointer, mTex_Hightbar,mTex_Skip; //AdHouse
	Arrow	mArrow[];
	Bow		mBow;
	Target	mTarget[];
	Combo	mCom;
	Power 	mPower;
	BGround mBGround;
	Point	mPoint[];
	
	
	public static int 	mArrowNO;
	int 	mScore;
	int		mHighScore;
	
	
	private Handler handler = new Handler() {public void handleMessage(Message msg) {mStart.adView.setVisibility(msg.what);}};
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
			
			mTex_Pointer = add("pointer.png");//AdHouse
			mTex_Hightbar = add("hightbar0.png");//AdHouse
			mTex_Skip = add("exit_icon.png");//AdHouse
			
			mTex_Logo			= add("hututugames.png");
			mTex_Arrow			= addRotate("arrow.png");
			mTex_ArrowCut		= addBitmap2(Bitmap.createBitmap(mTex_Arrow.mBitmap, 0, 0,mTex_Arrow.mBitmap.getWidth()-10, mTex_Arrow.mBitmap.getHeight(),null, true));
			
			mTex_Target			= add("target.png");
			
			mTex_AboutI			= add("about_icon.png");
			mTex_About			= add("about_text.png");
			mTex_Achievment		= add("achievment_button.png");
			mTex_Angle0			= add("angle0.png");
			mTex_Angle1			= add("angle1.png");
			mTex_Angle2			= add("angle2.png");
			mTex_Score			= add("score_bar.png");
			mTex_ArrowsBar		= add("arrows_bar.png");
			mTex_Back			= add("back_icon.png");
			
			mTex_Facebook		= add("facebook_icon.png");
			mTex_Flag_bg		= add("flag_bg.png");
			mTex_Splash_font	= add("splash_font.png");
			mTex_Googleplay		= add("googleplay_icon.png");
			mTex_HelpI			= add("help_icon.png");
			mTex_Highscore		= add("highscore_button.png");
			mTex_Home			= add("home_icon.png");
			mTex_Node0			= add("node0.png");
			mTex_Node1			= add("node1.png");
			mTex_Share			= add("share_icon.png");
			mTex_Pause			= add("pause_icon.png");
			mTex_Playagain		= add("playagain_button.png");
			mTex_Play			= add("play_button.png");
//			mTex_Rateus			= add("rateus_icon.png");
			mTex_Reset			= add("reset_icon.png");
			mTex_Resume			= add("resume_button.png");
			
			mTex_Aboutus		= add("udate/about_us.png");
			mTex_Archery		= add("udate/archery.png");
			mTex_Help			= add("udate/help.png");
			mTex_HelpText		= add("udate/help_text.png");
			mTex_HighscoreT		= add("udate/highscore_text.png");
			mTex_Failed			= add("udate/ofailed.png");
			mTex_Paused			= add("udate/paused.png");
			mTex_PausedArrow	= add("udate/paused_arrow.png");
			
			mTex_Power			= new SimplePlane[4];
			mTex_Power[0]		= add("udate/power_bigtarget.png");
			mTex_Power[1]		= add("udate/power_multiarrow.png");
			mTex_Power[2]		= add("udate/power_multitarget.png");
			mTex_Power[3]		= add("udate/power_pixedtarget.png");
			
			mTex_Character		= new SimplePlane[9];
			mTex_Character[0]	= add("character/backhand.png");
			mTex_Character[1]	= addRotate("character/face.png");
			mTex_Character[2]	= addRotate("character/fronthand.png");
			mTex_Character[3]	= add("character/shoulder.png");
			mTex_Character[4]	= add("character/standing_leg.png");
			mTex_Character[5]	= add("character/thorso.png");
			mTex_Character[6]	= addRotate("character/backhand0.png");
			mTex_Character[7]	= addRotate("character/backhand1.png");
			mTex_Character[8]	= addRotate("character/backhand2.png");
			
			
			mTex_Ach			= new SimplePlane[5];
			for(int i=0;i<mTex_Ach.length;i++)
				mTex_Ach[i]		= add("achievemet/"+i+".png");
			
			mTex_Walk			= new SimplePlane[12];
			Bitmap b = LoadImgfromAsset("character/walking.png");
			for(int i = 0;i<mTex_Walk.length;i++)
				mTex_Walk[i] 	= addBitmap(Bitmap.createBitmap(b, i*b.getWidth()/mTex_Walk.length, 0,b.getWidth()/mTex_Walk.length, b.getHeight(),null, true));
			
			
			mTex_Text			= new SimplePlane[14];
			mTex_Text[0]		= add("font/can_do_better.png");
			mTex_Text[1]		= add("font/poor.png");
			mTex_Text[2]		= add("font/missed.png");
			mTex_Text[3]		= add("font/close.png");
			mTex_Text[4]		= add("font/good.png");
			mTex_Text[5]		= add("font/nice.png");
			mTex_Text[6]		= add("font/verygood.png");
			mTex_Text[7]		= add("font/best.png");
			mTex_Text[8]		= add("font/exccellent.png");
			mTex_Text[9]		= add("font/perfect.png");
			mTex_Text[10]		= add("font/classic.png");
			mTex_Text[11]		= add("font/superb.png");
			mTex_Text[12]		= add("font/beautiful.png");
			mTex_Text[13]		= add("font/archeryking.png");
			
			mTex_Dot			= new SimplePlane[4];
			mTex_Dot[0]			= add("udate/white_dot.png");
			mTex_Dot[1]			= add("udate/yellow_dot.png");
			mTex_Dot[2]			= add("udate/blue_dot.png");
			mTex_Dot[3]			= add("udate/black_dot.png");
			
			mTex_Color			= add("font/a/colorblast.png");
			mTex_ComboText		= add("font/a/combo.png");
			mTex_Combo			= new SimplePlane[5];
			mTex_Combo[0]		= add("font/a/king.png");
			mTex_Combo[1]		= add("font/a/7.png");
			mTex_Combo[2]		= add("font/a/5.png");
			mTex_Combo[3]		= add("font/a/3.png");
			mTex_Combo[4]		= add("font/a/newachievement.png");
			
			
			
			b.recycle();
			mTex_Bow			= new SimplePlane[12];
			b = LoadImgfromAsset("bow.png");
			for(int i=0;i<mTex_Bow.length;i++)
			{
				mTex_Bow[i] = addBitmap2(Bitmap.createBitmap(b, ((i%6)*b.getWidth()*2)/(mTex_Bow.length), (i/6)*(b.getHeight()/2),(b.getWidth()*2)/(mTex_Bow.length), b.getHeight()/2,null, true));
//				System.out.println(i+"   (Width = "+b.getWidth()+") (Height = "+b.getHeight()+")    (x = "+(((i%6)*b.getWidth()*2)/(mTex_Bow.length))+")  (y = "+( (i/6)*(b.getHeight()/2))+")  (dx = "+((b.getWidth()*2)/(mTex_Bow.length))+")  (dy = "+( b.getHeight()/2)+")");
			}
			load_Font();
			mArrow			= new Arrow[9];
			for(int i=0;i<mArrow.length;i++)
				mArrow[i]	= new Arrow();
			mBow			= new Bow();
			mTarget			= new Target[3];
			for(int i=0;i<mTarget.length;i++)
				mTarget[i]	= new Target();
			mCom			= new Combo();
			mPower			= new Power();
			mPoint			= new Point[6];
			for(int i=0;i<mPoint.length;i++)
				mPoint[i]	= new Point();
			mBGround		= new BGround(this);
			gameReset();
			
		}catch(Exception e){}
		
	}
	void gameReset()
	{
		mCom.set();
		setArrow(0,0);
		mArrowNO	= 10;
		mScore 		= 0;
		mPower.Power= 0;
		mPower.set(-100,-100);
		 if(M.GameScreen == M.GAMEPLAY)
		    	M.stop(mContext);
	}
	void setArrow(float vx,float vy)
	{
		for(int i=0,k=5;i<mArrow.length;i++)
		{
			mArrow[k].set(mBow.x,mBow.y+(i*.1f-.4f), vx, vy);
			k++;
			k%=mArrow.length;
		}
	}
	void setTarget()
	{
		mBow.angle = 0;
		mBow.d =0;
		if(mPower.Power == 4){
			mPower.time--;
			if(mPower.time<1)
				mPower.Power =0;
		}
		else
		{
//			mPower.Power = 3;
			mTarget[0].set((M.mRand.nextFloat()*.8f)+.1f, (M.mRand.nextBoolean()?-.7f:.8f)*(M.mRand.nextFloat()));
			if(mPower.Power == 3)
			{
				mTarget[0].set(.2f, 0);
				mTarget[1].set(.6f,mTarget[0].y-mTex_Target.Height()*1.2f);
				mTarget[2].set(.6f,mTarget[0].y+mTex_Target.Height()*1.2f);
			}
			mPower.Power = 0;
			if(M.mRand.nextInt(7)==0 && mPower.y<-1.2f)
				mPower.set(0,1.2f);
		}
	}
	void score(float x,float y,int core)
	{
		mScore += core;//(100-(int)Math.abs(mTarget[no].hit*100));
		if(mScore > mHighScore)
		{
			mHighScore = mScore;
		}
		for(int i=0;i<mPoint.length;i++)
		{
			if(mPoint[i].y>.99f)
			{
				mPoint[i].set(x, y, core);
				break;
			}
		}
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
		resumeCounter++;
		if(mStart.adView!=null)
		{
			if(resumeCounter > 20 && M.GameScreen != M.GAMEWIN &&  M.GameScreen != M.GAMEADD)
			{
				int inv=mStart.adView.getVisibility();
				if(inv==AdView.GONE){try{handler.sendEmptyMessage(AdView.VISIBLE);}catch(Exception e){}}
			}
			else
			{
				int inv=mStart.adView.getVisibility();
				if(inv==AdView.VISIBLE){try{handler.sendEmptyMessage(AdView.GONE);}catch(Exception e){}}
			}
//			if(M.GameScreen == M.GAMEWIN)
//			{
//				int inv=mStart.adViewBig.getVisibility();
//				if(inv==AdView.GONE){try{handler1.sendEmptyMessage(AdView.VISIBLE);}catch(Exception e){}}
//			}
//			else
//			{
//				int inv=mStart.adViewBig .getVisibility();
//				if(inv==AdView.VISIBLE){try{handler1.sendEmptyMessage(AdView.GONE);}catch(Exception e){}}
//			}
		}

		/*AdHouse*/
		if (mStart.adHouse != null) {
			if (M.GameScreen == M.GAMEADD || M.GameScreen == M.GAMEWIN) {
				int inv = mStart.adHouse.getVisibility();
				if (inv == AdView.GONE) {try {handler2.sendEmptyMessage(AdView.VISIBLE);} catch (Exception e) {}}
			} else {
				int inv = mStart.adHouse.getVisibility();
				if (inv == AdView.VISIBLE) {try {handler2.sendEmptyMessage(AdView.GONE);} catch (Exception e) {}
				}
			}
		}
		/*AdHouse*/
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
	
	SimplePlane addRotate(String ID)
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
	SimplePlane addBitmap2(Bitmap b)
	{
		SimplePlane SP = null;
		try
		{
			SP = new SimplePlane((b.getWidth()/M.mMaxY),(b.getHeight()/M.mMaxY));
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
	void load_Font()
	{
		mTex_Font	= new SimplePlane[10];
		Bitmap b = LoadImgfromAsset("fontstrip.png");
		for(int i = 0;i<mTex_Font.length;i++)
			mTex_Font[i] = addBitmap(Bitmap.createBitmap(b, i*b.getWidth()/mTex_Font.length, 0,b.getWidth()/mTex_Font.length, b.getHeight(),null, true));
	}
}
