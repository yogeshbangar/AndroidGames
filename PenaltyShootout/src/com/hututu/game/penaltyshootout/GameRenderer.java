package com.hututu.game.penaltyshootout;

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

public class GameRenderer implements Renderer 
{
	final Group root;
	public static Context mContext;
	public static Start mStart;
	long startTime = System.currentTimeMillis();
	int mSel = 0;

	SimplePlane[] mTex_Ball,mTex_Ground,mTex_Block,mTex_Cover,mTex_Fall[],mTex_Stand,mTex_MenuLeft[];
	SimplePlane[] mTex_MenuRight,mTex_Font,mTex_Disk,mTex_Goal,mTex_Time[];// Change 4/12
	SimplePlane mTex_8x8,mTex_Logo,mTex_Bar,mTex_Exit,mTex_Pointer,mTex_Splash,mTex_Score,mTex_Level;
	SimplePlane mTex_InOut,mTex_likeGo,mTex_BText,mTex_CScore,mTex_GOver,mTex_GPbg,mTex_Shadow;
	SimplePlane mTex_MenuBTN,mTex_MoreGo,mTex_RteGP,mTex_Retry,mTex_GPaused,mTex_PShaw,mTex_Main;
	SimplePlane mTex_RedMenuBtn,mTex_RSBGp,mTex_RSBG95,mTex_RPanel,mTex_RMenuBtn,mTex_Score_back;
	SimplePlane mTex_ScorePrint,mTex_SBtn,mTex_SndGP,mTex_MPoint,mTex_SoundOff,mTex_Cont,mTex_Aboutscr;
	SimplePlane mTex_Snd,mTex_Rate,mTex_Submit,mTex_HS,mTex_HSbg,mTex_Loading,mTex_SBack,mTex_NewLvl;
	
	Point mPoint = new Point(); 
	Ball mBall;
	Keeper mKeeper;
	Animation ani[];
	Target mTPoint[];
	Target mNlvl;
	
	TimeTarget	mTTime;
	
	long gametime =0;
	
	int mScore;
	int mHScore[] = new int[6];
	int type;
	int mLevel;
	int mGoal;
	int Target;
	
	
	
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
			int i;
			// Change 4/12
			mTex_Time			= new SimplePlane[3][2];
			mTex_Time[0][0]		= add("update/2x.png");
			mTex_Time[0][1]		= add("update/2x0.png");
			mTex_Time[1][0]		= add("update/3x.png");
			mTex_Time[1][1]		= add("update/3x0.png");
			mTex_Time[2][0]		= add("update/5x.png");
			mTex_Time[2][1]		= add("update/5x0.png");
			// Change 4/12
			
			
			
			
			
			mTex_NewLvl			= add("mtext/newlevel_font.png");
			mTex_Submit			= add("mtext/submit.png");
			mTex_Aboutscr		= add("ui/about2.png");
			mTex_Loading		= add("mtext/loading2.png");
			mTex_Snd			= add("mtext/sound2.png");
			mTex_Rate			= add("mtext/rate.png");
			mTex_Level			= add("ui/level.png");
			mTex_Score			= add("ui/score.png");
			mTex_Splash			= add("splash.jpg");
			mTex_SoundOff		= add("ui/sound_off.png");
			
			mTex_MenuRight		= new SimplePlane[6];
			mTex_MenuRight[0]	= add("mtext/time.png");
			mTex_MenuRight[1]	= add("mtext/arcade.png");
			mTex_MenuRight[2]	= add("mtext/goal_post.png");
			mTex_MenuRight[3]	= add("mtext/tournament.png");
			mTex_MenuRight[4]	= add("mtext/1ballshoot.png");
			mTex_MenuRight[5]	= add("mtext/targetshoot.png");
			
			mTex_MenuLeft		= new SimplePlane[2][6];
			mTex_MenuLeft[0][0]	= add("mtext/highscore2.png");
			mTex_MenuLeft[0][1]	= add("mtext/share.png");
			mTex_MenuLeft[0][2]	= add("mtext/like.png");
			mTex_MenuLeft[0][3]	= add("mtext/more.png");
			mTex_MenuLeft[0][4]	= add("mtext/sound.png");
			mTex_MenuLeft[0][5]	= add("mtext/about.png");
			
			mTex_MenuLeft[1][0]	= add("ui/score_icon.png");
			mTex_MenuLeft[1][1]	= add("ui/share_icon.png");
			mTex_MenuLeft[1][2]	= add("ui/like_icon.png");
			mTex_MenuLeft[1][3]	= add("ui/more_icon.png");
			mTex_MenuLeft[1][4]	= add("ui/sound_icon.png");
			mTex_MenuLeft[1][5]	= add("ui/about_icon.png");
			
			mTex_MPoint			= add("ui/pointer.png");
			mTex_BText			= add("ui/ball.png");
			mTex_CScore			= add("ui/current_score.png");
			mTex_GOver			= add("ui/game_over.png");
			mTex_GPaused		= add("ui/game_paused.png");
			mTex_GPbg			= add("ui/game_paused_bg.png");
			mTex_Shadow			= add("shadow.png");
			mTex_HS				= add("ui/high_score.png");
			mTex_HSbg			= add("ui/high_score_bg.png");
			mTex_InOut			= add("ui/in_out.png");
			mTex_PShaw			= add("Sprite/fall_right15_sadow.png");
			mTex_likeGo			= add("ui/like_icon_go.png");
			mTex_MenuBTN		= add("ui/menu_btn.png");
			
			mTex_MoreGo			= add("ui/more_icon_go.png");
			mTex_RteGP			= add("ui/rate_icon_gp.png");
			mTex_RedMenuBtn		= add("ui/red_menubtn.png");
			mTex_RSBGp			= add("ui/red_smallbtn_gp.png");
			mTex_RSBG95			= add("ui/red_smallbtn_gp-95.png");
			mTex_RPanel			= add("ui/right_panel.png");
			mTex_RMenuBtn		= add("ui/rollover_menu_btn.png");
			
			mTex_Score_back		= add("ui/score_back.png");
			mTex_SBack			= add("ui/score_back_go.png");
			mTex_ScorePrint		= add("ui/score_print.png");
			mTex_SBtn			= add("ui/small_btn_gp.png");
			mTex_SndGP			= add("ui/soundicon_gp.png");
			mTex_Cont			= add("mtext/continue.png");
			mTex_Main			= add("mtext/mainmenu.png");
			mTex_Retry			= add("mtext/retry.png");
			mTex_Bar			= add("add/bar.png");
			mTex_Exit			= add("add/exit.png");
			mTex_Pointer		= add("add/pointer.png");
			mTex_Logo			= add("hututugames.png");
			mTex_8x8 			= add("smoke2.png");
			
			mTex_Ground			= new SimplePlane[7];
			mTex_Ground[0]		= add("ground/ground.jpg");
			mTex_Ground[1]		= add("ground/boundry.png");
			mTex_Ground[2]		= add("ground/bg0.png");
			mTex_Ground[3]		= add("ground/net.png");
			mTex_Ground[4]		= add("ground/net1.png");
			mTex_Ground[5]		= add("ground/net2.png");
			mTex_Ground[6]		= add("ground/netglow.png");
			mTex_Disk			= new SimplePlane[2];
			mTex_Disk[0]		= add("ground/disk.png");
			mTex_Disk[1]		= add("ground/disk2.png");
			Bitmap b 			= LoadImgfromAsset("football_sprite.png");
			mTex_Ball			= new SimplePlane[18];
			for(i = 0;i<mTex_Ball.length;i++)
			{
				mTex_Ball[i] 	= addBitmap(Bitmap.createBitmap(b, i*(b.getWidth()/mTex_Ball.length),0,b.getWidth()/mTex_Ball.length,b.getHeight(),null, true));
			}
			mTex_Goal			= new SimplePlane[7];
			for(i = 0;i<mTex_Goal.length;i++)
			{
				mTex_Goal[i] 	= add("point/"+i+".png");
			}	
			mTex_Block			= new SimplePlane[7];
			for(i = 0;i<mTex_Block.length;i++)
				mTex_Block[i] 	= add("Sprite/block"+i+".png");
			
			mTex_Cover			= new SimplePlane[13];
			for(i = 0;i<mTex_Cover.length;i++)
				mTex_Cover[i] 	= add("Sprite/cover"+i+".png");
			
			mTex_Fall			= new SimplePlane[2][15];
			for(i = 0;i<mTex_Fall[0].length;i++)
			{
				mTex_Fall[0][i]	= add("Sprite/fall_right"+i+".png");
				mTex_Fall[1][i] = addBitmap(FlipHorizontal(mTex_Fall[0][i].getImg()));
			}
			mTex_Stand			= new SimplePlane[10];
			for(i = 0;i<mTex_Stand.length;i++)
				mTex_Stand[i] 	= add("Sprite/stand"+i+".png");
			load_Font();
			
			mNlvl = new Target();
			mTPoint	= new Target[2];
			mTPoint[0]	= new Target();
			mTPoint[1]	= new Target();
			
			mKeeper = new Keeper();
			mBall = new Ball();
			ani = new Animation[200];
			for(i=0;i<ani.length;i++)
			{
				ani[i] = new Animation();
			}
			mTTime = new TimeTarget();
//			gameReset();
		}catch(Exception e){}
		
	}
	
	
	void gameReset()
	{
		mBall.set(0, -.8f, 1, 0, 0, 0);
		mKeeper.set();
		mScore = 0;
		mLevel = 1;
		gametime = System.currentTimeMillis();
		mGoal = 0;
		if (type == 4)
			Target = 1;
		else
			Target = 12;
		mTPoint[0].set(+(M.mRand.nextFloat()%.2f)+.08f, (M.mRand.nextFloat()%.2f)+.6f, M.mRand.nextInt(2));
		mTPoint[1].set(-(M.mRand.nextFloat()%.2f)-.08f, (M.mRand.nextFloat()%.2f)+.6f, mTPoint[0].m==0?1:0);
		mPoint.set(0, 100);
		mNlvl.set(-100, 0, 0);
		mTTime.set();
		mTTime.inc = 0;
		 
		{
			try {
				adsHandler.sendEmptyMessage(0);
			} catch (Exception e) {
			}
		}
	}
	Handler adsHandler = new Handler() {
		public void handleMessage(Message msg) {
			mStart.loadInter();
		}
	};
	
	
	
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
			if(M.GameScreen != M.GAMEPLAY && M.GameScreen != M.GAMEADD && M.GameScreen != M.GAMELOAD)
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


		
		/*AdHouse*/
		if (mStart.adHouse != null) {
			if(M.GameScreen == M.GAMEADD || (M.GameScreen == M.GAMELOAD) )
			{
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
		mTex_Font = new SimplePlane[11];
		Bitmap b = LoadImgfromAsset("fontstrip.png");
		for (int i = 0; i < mTex_Font.length; i++)
			mTex_Font[i] = addBitmap(Bitmap.createBitmap(b, i * b.getWidth()/ mTex_Font.length, 0, b.getWidth() / mTex_Font.length,b.getHeight(), null, true));
	}
}
