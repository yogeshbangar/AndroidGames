package com.onedaygames24.pokernpoker;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.google.android.gms.ads.AdView;
//import com.google.ads.AdView;

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
	Context mContext;
	public static Start mStart;
	long startTime = System.currentTimeMillis();
	int resumeCounter =0;
	int mSel = 0;
	
	SimplePlane[] mTex_Font[],mTex_Button[],mTex_Card,mTex_CardSelect,mTex_ChaalBack,mTex_SoundOn,mTex_SoundOff,mTex_BB;
	SimplePlane[] mTex_HSin,mTex_Retry,mTex_Countinue,mTex_Next,mTex_WinCrd,mTex_SplCount,mTex_MSound,mTex_MD,mTex_MS;
	SimplePlane mTex_Popup,mTex_AllBack,mTex_Menu,mTex_Share,mTex_BigPopup,/*mTex_RateUs,*/mTex_BackCard,mTex_Star,mTex_Back;
	SimplePlane mTex_Gameover,mTex_Pausescr,mTex_Win,mTex_NScore,mTex_BScore,mTex_buttonSe,mTex_MExit,mTex_Poker;
	SimplePlane mTex_CardBack,mTex_GameBG,mTex_RaiseBox,mTex_Cancel,mTex_Slider,mTex_Pot,mTex_Pause,mTex_Logo;
	SimplePlane mTex_Help,mTex_About,mTex_Chip[],mTex_Plyar[],mTex_Condition[];
	SimplePlane   mTex_Pointer, mTex_Hightbar,mTex_Skip,mTex_Ling,mTex_LingBar,mTex_Loading; //AdHouse
	
	boolean IsGamePause = false;
	boolean addFree = false;
	byte cardShuffle[] = new byte[52];
	
	int AniCounter = 0;
	int ShowCurrent =0;
	int cardNo = 0;
	int mHighScore = 0;
	int NewCong = 0;

	float mSliderX = -.3f;
	
	Card	mCard[];
	Poker	mPoker;
	Dealer	mDealer;
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
		int i;
		try
		{
			mTex_Condition   		= new SimplePlane[10];
            for(i=0;i<mTex_Condition.length;i++)
            {
            	mTex_Condition[i] 	= add("condition/"+i+".png");
            }
			mTex_Ling		= add("loading0.png");
			mTex_LingBar	= add("loading1.png");
			mTex_Loading	= add("loading2.png");
			mTex_Pointer 	= add("pointer.png");//AdHouse
			mTex_Hightbar 	= add("hightbar0.png");//AdHouse
			mTex_Skip 		= add("exit_icon.png");//AdHouse
			
			font();
			mTex_Logo			= add("logo.png");
			mTex_GameBG 		= add("gamebg.png");
			mTex_CardBack		= add("cardsback.png");
			
			
			mTex_Button			= new SimplePlane[4][];
			
			mTex_Button[0]		= new SimplePlane[2];
			mTex_Button[0][0]	= add("button/call_de.png");
			mTex_Button[0][1]	= add("button/call_se.png");
			
			mTex_Button[1]		= new SimplePlane[2];
			mTex_Button[1][0]	= add("button/check_de.png");
			mTex_Button[1][1]	= add("button/check_se.png");
			
			mTex_Button[2]		= new SimplePlane[2];
			mTex_Button[2][0]	= add("button/fold_de.png");
			mTex_Button[2][1]	= add("button/fold_se.png");
			
			mTex_Button[3]		= new SimplePlane[2];
			mTex_Button[3][0]	= add("button/raise_de.png");
			mTex_Button[3][1]	= add("button/raise_se.png");
			
			mTex_RaiseBox		= add("button/raisebox.png");
			mTex_Cancel			= add("button/cancel.png");
			mTex_Slider			= add("button/slider.png");
			mTex_Popup			= add("chip/chipbox.png");
			mTex_AllBack		= add("splash.png");
			mTex_BigPopup		= add("big_popup.png");
			mTex_Menu			= add("button/menu.png");
			mTex_Share			= add("button/share.png");
//			mTex_RateUs			= add("button/rateus.png");
			mTex_buttonSe		= add("button/button_se.png");
			mTex_Pot			= add("button/pot.png");
			mTex_Pause			= add("button/pause.png");
			mTex_BackCard		= add("cardsback.png");
			mTex_Star			= add("button/star.png");
			
			mTex_Gameover		= add("gameover_text.png");
			mTex_Pausescr		= add("gamepaused_text.png");
			mTex_Win			= add("youwon_text.png");
			mTex_NScore			= add("newscore.png");
			mTex_BScore			= add("bestscore.png");
			
			
			mTex_ChaalBack		= new SimplePlane[2];
			mTex_ChaalBack[0]	= add("chip/white.png");
			mTex_ChaalBack[1]	= add("chip/yellow.png");
			
			mTex_CardSelect		= new SimplePlane[2];
            mTex_CardSelect[0]	= add("card0.png");
            mTex_CardSelect[1]	= add("card1.png");
            
            
            
            mTex_SoundOn		= new SimplePlane[2];
            mTex_SoundOn[0]		= add("button/soundon_de.png");
            mTex_SoundOn[1]		= add("button/soundon_se.png");
            mTex_SoundOff		= new SimplePlane[2];
            mTex_SoundOff[0]	= add("button/soundoff_de.png");
            mTex_SoundOff[1]	= add("button/soundoff_se.png");
            
            mTex_BB				= new SimplePlane[1];
            mTex_BB[0]			= add("chip/bb.png");
            
            
            mTex_HSin			= new SimplePlane[2];
            mTex_HSin[0]		= add("button/highscore2_de.png");
            mTex_HSin[1]		= add("button/highscore2_se.png");
            
            mTex_Retry			= new SimplePlane[2];
            mTex_Retry[0]		= add("button/retry_de.png");
            mTex_Retry[1]		= add("button/retry_se.png");
            
            mTex_Countinue		= new SimplePlane[2];
            mTex_Countinue[0]	= add("button/continue2_de.png");
            mTex_Countinue[1]	= add("button/continue2_se.png");
            
            mTex_Next			= new SimplePlane[2];
            mTex_Next[0]		= add("button/next_de.png");
            mTex_Next[1]		= add("button/next_se.png");
            
            
            mTex_WinCrd			= new SimplePlane[2];
            mTex_WinCrd[0]		= add("card0.png");
            mTex_WinCrd[1]		= add("card1.png");
            
            mTex_SplCount		= new SimplePlane[2];
            mTex_SplCount[0]	= add("button/continuesplash_de.png");
            mTex_SplCount[1]	= add("button/continuesplash_se.png");
            
            
            mTex_MSound			= new SimplePlane[2];
            mTex_MSound[0]		= add("menubutton/sound.png");
            mTex_MSound[1]		= add("menubutton/sondoff.png");
            
            mTex_MD				= new SimplePlane[5];
            mTex_MD[0]			= add("menubutton/continue_de.png");
            mTex_MD[1]			= add("menubutton/newgame_de.png");
            mTex_MD[2]			= add("menubutton/help_de.png");
            mTex_MD[3]			= add("menubutton/about_de.png");
            mTex_MD[4]			= add("menubutton/highscore_de.png");
            
            mTex_MS				= new SimplePlane[5];
            mTex_MS[0]			= add("menubutton/continue_se.png");
            mTex_MS[1]			= add("menubutton/newgame_se.png");
            mTex_MS[2]			= add("menubutton/help_se.png");
            mTex_MS[3]			= add("menubutton/about_se.png");
            mTex_MS[4]			= add("menubutton/highscore_se.png");
            
            
            mTex_MExit			= add("menubutton/exit.png");
            mTex_Back			= add("menubutton/back.png");
            
            mTex_Poker			= add("poker_poker.png");
            mTex_Help			= add("help.png");
            mTex_About			= add("about_text.png");
            
            mTex_Chip			= new SimplePlane[4];
            for(i=0;i<mTex_Chip.length;i++)
            	mTex_Chip[i]	= add("chip/"+i+".png");
            
            
            mTex_Plyar			= new SimplePlane[5];
            mTex_Plyar[0]		= add("player/0.png");
            mTex_Plyar[1]		= add("player/1.png");
            mTex_Plyar[2]		= add("player/2.png");
            mTex_Plyar[3]		= add("player/3.png");
            mTex_Plyar[4]		= add("player/4.png");
            
            
			mTex_Card   		= new SimplePlane[52];
            for(i=0;i<mTex_Card.length;i++)
            {
            	mTex_Card[i] 	= add((i/13)+"/"+(i%13)+".png");
            }
            mCard		= new Card[5];
            for(i =0;i<mCard.length;i++)
            {
            	mCard[i]	= new Card();
            }
    		
            mDealer	= new Dealer();
            mPoker = new Poker(this); 
//            Reset();
		}catch(Exception e){}
		
	}
	
	 
	void gameReset()
	{
		
		float a =3.0f;
		mCard[0].reset(-.0f,0.25f,-.035f*a,0.0026f*a,-.030f*a,0.0030f*a);
		mCard[1].reset(0.0f,0.25f,0.030f*a,0.0030f*a,0.035f*a,0.0026f*a);
		
		mCard[2].reset(0.0f,0.25f,0.012f*a,-.0200f*a,0.015f*a,-.0210f*a);
		mCard[3].reset(0.0f,0.25f,-.002f*a,-.0300f*a,0.002f*a,-.0300f*a);
		mCard[4].reset(-.0f,0.25f,-.015f*a,-.0210f*a,-.012f*a,-.0201f*a);
		ChardReset();
		int k=0;
		for(int i =0;i<mCard.length;i++)
        {
			mCard[i].mBat = 0;
			if(mCard[i].mTotalCoin>0)
				mCard[i].set(mCard[i].mTotalCoin);
			else{
				mCard[i].mState = M.GAMEOUT;k++;}
        }
		if(mCard[M.ID].mState <= M.FOLD)
		{
			M.GameScreen = M.GAMEOVER;
			if(NewCong > 0)
				NewCong --;
			if(NewCong < 1)
				IsGamePause = false;
			mStart.show();//Smart();
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!if(mCard[M.ID].mState <= M.FOLD)");
		}
		else if(k == 4)
		{
			M.GameScreen = M.GAMECONG;
			NewCong ++;
			if(NewCong*M.MAXAMOUNT*M.MAXAMOUNT*mCard.length > mHighScore)
			{
				mHighScore = NewCong*M.MAXAMOUNT*mCard.length; 
			}
			mStart.show();//Smart();
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!GAMECONG");
		}
		else
			M.GameScreen = M.GAMEPLAY;
		mDealer.mTableBat++;
		mDealer.mNewStart++;
		mDealer.mNewStart%=mCard.length;
		for(int i =0,m=mDealer.mNewStart;i<mCard.length;i++,m++)
        {
			if(mCard[m%mCard.length].mState >M.FOLD)
			{
				mDealer.mNewStart = m%mCard.length;
				break;
			}
        }
		mDealer.set(mDealer.mNewStart, mDealer.mTableBat, mDealer.mTableBat);
//		System.out.println(mDealer.GameCounter+" ~1~ "+mDealer.mBatChance+" ~2~ "+mDealer.mBigBat+" ~3~ "+mDealer.mCurrentBat+" ~4~ "+mDealer.mNewStart+" ~5~ "+mDealer.mRaiseCount
//				+" ~6~ "+mDealer.mShowCard+" ~7~ "+mDealer.mStart+" ~8~ "+mDealer.mTableBat+" ~9~ "+mDealer);
		boolean temp = M.setValue; 
		M.setValue = false;
		root.Raise(0);
		M.setValue = temp;
		
		mDealer.mBatChance++;
		mDealer.mBatChance%=mCard.length;
		for(int i =0,m=mDealer.mBatChance;i<mCard.length;i++,m++)
        {
			if(mCard[m%mCard.length].mState >M.FOLD)
				break;
			else
				mDealer.mBatChance = m%mCard.length;
        }
		AniCounter = 0;
		if(M.GameScreen == M.GAMEPLAY && !isReset)
			M.play(mContext, R.drawable.car_serv);
		if (ads % 3 == 0 || isReset){
			System.out.println(isReset+"  ~~~~~~~~~~~~~~~~~~~~~~~~ "+ads);
			try {
//				adsHandle.sendEmptyMessage(AdView.VISIBLE);
				mStart.loadInter();//SmartHandler();
			} catch (Exception e) {
			}
		}
		ads++;
	}
	int ads=0;
//	private Handler adsHandle = new Handler() {public void handleMessage(Message msg) {mStart.loadInter();}};//AdHouse
	boolean isReset = true;
	void Reset()
	{
		for(int i =0;i<mCard.length;i++)
        {
			mCard[i].set(M.MAXAMOUNT);
        }
		mDealer.mTableBat = 1;
		isReset = true;
		gameReset();
		isReset = false;
		IsGamePause = true;
	}
	
	
	void ChardReset()
    {
		for(byte k =0;k<cardShuffle.length;k++)
		{
			cardShuffle[k] = k;
		}
        for(byte k =0;k<cardShuffle.length;k++){
            for(byte j =0;j<cardShuffle.length;j++)
            {
                byte a,b,temp;
               /* if(j%5==0)
                {
                    a = (byte)((Math.abs(M.mRand.nextDouble()*10000000))%52);
                    b = (byte)((Math.abs(M.mRand.nextFloat()*10000000))%52);
                }
                else if(j%5==1)
                {
                    a = (byte)((Math.abs(M.mRand.nextFloat()*10000000))%52);
                    b = (byte)((Math.abs(M.mRand.nextLong()*10000000))%52);
                }
                else if(j%5==2)
                {
                    a = (byte)((Math.abs(M.mRand.nextLong()*10000000))%52);
                    b = (byte)((Math.abs(M.mRand.nextInt()*10000000))%52);
                }
                else if(j%5==3)
                {
                    a = (byte)((Math.abs(M.mRand.nextDouble()*10000000))%52);
                    b = (byte)((Math.abs(M.mRand.nextInt()*10000000))%52);
                }
                else
                {
                    a = (byte)((Math.abs(M.mRand.nextFloat()*10000000))%52);
                    b = (byte)((Math.abs(M.mRand.nextInt(52)*10000000))%52);
                }*/
                a = (byte)Math.abs(M.mRand.nextInt(52));
                b = (byte)Math.abs(M.mRand.nextInt(52));
                temp = cardShuffle[a];
                cardShuffle[a] = cardShuffle[b];
                cardShuffle[b]= temp;

            }
        }
        
//        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        for(byte k =0;k<cardShuffle.length;k++)
		{
//			System.out.println("cardShuffle["+k+"] = "+cardShuffle[k]+";");
		}
       /* cardShuffle[0] = 48;
        cardShuffle[1] = 36;
        
        cardShuffle[2] = 32;
        cardShuffle[3] = 22;
        
        cardShuffle[4] = 40;
        cardShuffle[5] = 51;
        
        cardShuffle[6] = 19;
        cardShuffle[7] = 37;
        
        cardShuffle[8] = 49;
        cardShuffle[9] = 0;
        
        cardShuffle[10] = 1;
        cardShuffle[11] = 23;
        cardShuffle[12] = 25;
        cardShuffle[13] = 2;
        cardShuffle[14] = 26;*/

        
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
			if(M.GameScreen != M.GAMEPLAY && M.GameScreen != M.GAMEADD&&M.GameScreen != M.GAMELOAD)
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
			if (M.GameScreen == M.GAMEADD||M.GameScreen == M.GAMELOAD) {
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
			System.out.println(""+ID+"!!!!!!!!!!!!!!!!!!!!!~~~~~~~~123 ~~~~~!!!!!!!!!!!!!!!!!!!!!!!!!"+e.getMessage());
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
	void font()
	{
		mTex_Font	= new SimplePlane[2][];
		for(int j = 0;j<mTex_Font.length;j++)
		{
			mTex_Font[j]	= new SimplePlane[10];
			Bitmap b = LoadImgfromAsset("fontstrip/"+j+".png");
			for(int i = 0;i<mTex_Font[j].length;i++)
				mTex_Font[j][i] = addBitmap(Bitmap.createBitmap(b, i*(b.getWidth()/16), 0,b.getWidth()/16, b.getHeight(),null, true));
		}
	}
}
