package com.oneday.games24.motocitytraffic;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.google.android.gms.ads.AdView;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
@SuppressLint("HandlerLeak")
public class GameRenderer implements Renderer 
{
	final Group root;
	static Context mContext;
	public static Start mStart;
	long startTime = System.currentTimeMillis();
	int resumeCounter =0;
	int mSel = 0;
	
	SimplePlane[]   mTex_Font,mTex_Car,mTex_CarBreak,mTex_Smoke,mTex_WorldBG,mTex_WorldUnLock,mTex_WorldLock;
	SimplePlane 	mTex_Logo,mTex_Tap2Play,mTex_Fire,mTex_Btn,mTex_BackBtn,mTex_GoogleBtn,mTex_FbBtn,mTex_LeaderBtn;
	SimplePlane     mTex_BigPup,mTex_SmallPup,mTex_TransBg,mTex_Arrow,mTex_BigBox,mTex_SmallBox,mTex_ShopIcn,mTex_KeyIcn,mTex_Add;
	SimplePlane     mTex_CoinIcn,mTex_PlayBtn,mTex_CntrlBtn,mTex_ChallangeBtn,mTex_Join,mTex_MenuBox,mTex_MenuIcn[],mTex_soundOff,mTex_MusicOff;
	SimplePlane     mTex_PauseIcn[],mTex_AbtTxt,mTex_AchiveBtn,mTex_GamePower[],mTex_Lock,mTex_Power,mTex_Preview,mTex_SmallCoin,mTex_PreviewBox,mTex_UnlockTxt;
	SimplePlane     mTex_ShopBox,mTex_Coinde[],mTex_Keyde[],mTex_Worldde[],mTex_DollarBox,mTex_BuyIcn,mTex_StageFont,mTex_ScoreFont,mTex_ScoreBox,mTex_StageBox;
	SimplePlane     mTex_StageBar,mTex_UseBtn,mTex_KeyAnimation,mTex_Fillcir,mTex_Bridge[],mTex_Tunnel,mTex_PauseBtn,mTex_FastEffect,mTex_SlowEffect;
	SimplePlane     mTex_SplashFont,mTex_AchieUnlk,mTex_ChallengeBox,mTex_ChallengRW[],mTex_ChallengComp,mTex_LoadBar,mTex_Exit,mTex_Pointer;
	SimplePlane     mTex_JoinText,mTex_JoinBtn,mTex_UpgradeFill,mTex_GamePopup,mTex_GameArrow,mTex_TapEffect,mTex_Buy;
	
	
  
	Car mCar[],mCarBreak[];
	Car mSetCar[],mSplashCar[];
	World mWorld[];
	Font  mFont;
	Animation mAni[][],mAchieveAni,mChallenge,mTapEffect,mScoreAni,mCoinAni;
	GameAcc mSlowCar,mFastCar,mBridge,mTunnel,mStopCar;
	
	int mWorldno=0,mTotalCoin,mKharchCoin,tmpCoin,mLevelCoin=0;
	int mNoKey=1,mWorldSel=0,mScore,mBestScore,mCntScore,mMove;
	int mOverCnt=0,mKeyAniCnt,mStageFill,mstageCounter;
	
	int mCount = 0,mGenTime = 180,mCarGenSpeed = 2;	
    int mDelay = 60,mAdCount;
    int mCarIndex=0,mMod,mStageRem;
    
    int mGameSec=0,mGameMin=1,mChallangeSec,noStage,mBestStage,mTotalMin,mTotalSec;
	boolean isCrash,isJoin;
	
	float CarCordI[][]  = new float[2][2],BusCordI[][]  = new float[2][6];
	float CarCordJ[][]  = new float[2][2],BusCordJ[][]  = new float[2][6];
	float mJoinAnim/*,mPopX=-1.02f,mPopVX,mPopAng*/;
	
	boolean isAchie[] = new boolean[50],isChalleng[] = new boolean[M.Challenge.length];
	int mNoCrossCar,mFreeCoin=500;
	
	private Handler handlerAdBanner = new Handler() {public void handleMessage(Message msg) {mStart.adBanner.setVisibility(msg.what);}};
	private Handler handlerAdRect   = new Handler() {public void handleMessage(Message msg) {mStart.adRect.setVisibility(msg.what);}};
	
//	private Handler LoadAD = new Handler() {public void handleMessage(Message msg) {GameRenderer.mStart.loadInterstitial();}};
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
			System.gc();
			mTex_LoadBar    = add("bar.png");
		    mTex_Exit	    = add("exit.png");
		    mTex_Pointer    = add("pointer.png");
			mTex_Logo       = add("logo.png");
			mTex_Tap2Play   = add("tap-to-play.png");
			mTex_WorldBG    = new SimplePlane[12];
			mTex_WorldBG[4] = add("world/"+4+".png");
//			for(i=0;i<mTex_WorldBG.length;i++)
//			{
//			  mTex_WorldBG[i] = add("world/"+i+".png");
//			}
			mTex_Car       = new SimplePlane[24];
			mTex_CarBreak  = new SimplePlane[24]; 
			for(i=0;i<mTex_Car.length;i++)
			{
			  mTex_Car[i]        = addRotate("car/car"+i+".png");
			  mTex_CarBreak[i]   = addRotate("carbreak/break"+i+".png");
			}
		    mTex_Fire      = addRotate("explode.png");
		    mTex_Smoke     = new SimplePlane[16];
		    for(i=0;i<mTex_Smoke.length;i++)
		    	mTex_Smoke[i] = add("smoke/"+i+".png");
		    
			mTex_WorldUnLock = new SimplePlane[12];
			mTex_WorldLock   = new SimplePlane[12];
			for(i=0;i<mTex_WorldUnLock.length;i++)
			{
				mTex_WorldUnLock[i] = add("worldicon/" +(i+1)+".png");
				mTex_WorldLock[i]   = add("worldicon/0"+(i+1)+".png");
			}
			mTex_Bridge     = new SimplePlane[2];
			mTex_Bridge[0]  =  addRotate("bridge.png");
			mTex_Bridge[1]  =  addRotate("bridge2.png");
			mTex_Tunnel     =  addRotate("tunnel.png");
			mTex_FastEffect =  add("fast_effect.png");
			mTex_SlowEffect =  add("slow_effect.png");
			mTex_AchieUnlk  =  add("achieve_unlock.png");
			mTex_GamePopup  =  add("ui/popup.png");
			mTex_GameArrow  =  add("ui/gamearrow.png");
			mTex_TapEffect  =  add("tap.png");
			
			LoadUi();
			loadFont();
			InitGameObj();
		    M.loadSound(mContext);
		}catch(Exception e){}
	}
	void LoadUi()
	{
		mTex_BigPup   = add("ui/big_po_pup.png");
		mTex_TransBg  = add("ui/trans_bg.png");
		mTex_SmallPup = add("ui/small_popup.png");
		mTex_Arrow    = addRotate("ui/arrow.png");
		mTex_BigBox   = add("ui/box0.png");
		mTex_SmallBox = add("ui/box1.png");
		mTex_ShopIcn  = add("ui/shop_icon.png");
		mTex_KeyIcn   = add("ui/key_icon.png");
		mTex_Add      = add("ui/add.png");
		mTex_CoinIcn  = add("ui/coin_icon.png");
		mTex_PlayBtn  = add("ui/play_button.png");
		mTex_ChallangeBtn = add("ui/challenge_button.png");
		mTex_CntrlBtn     = add("ui/control_button.png");
		mTex_Join         = add("ui/join1.png");
		mTex_MenuBox      = addBig("ui/box5.png");
		
		mTex_MenuIcn      = new SimplePlane[5];
		mTex_MenuIcn[0]   = add("ui/info_button.png"); 
		mTex_MenuIcn[1]   = add("ui/soundon_button.png");
		mTex_MenuIcn[2]   = add("ui/musicon_button.png");
		mTex_MenuIcn[3]   = add("ui/reset_button.png");
		mTex_MenuIcn[4]   = add("ui/share_button.png");
		mTex_MusicOff     = add("ui/musicoff_button.png");
		mTex_soundOff     = add("ui/soundoff_button.png");
		mTex_BackBtn      = add("ui/back_button.png");
		mTex_GoogleBtn    = add("ui/g+_button.png");  
		mTex_FbBtn	      = add("ui/face_button.png");
		mTex_LeaderBtn    = add("ui/leaerboard_button.png");
		
		mTex_PauseIcn     = new SimplePlane[5];
		mTex_PauseIcn[0]  = add("ui/play_icon.png");
		mTex_PauseIcn[1]  = add("ui/retry.png");
		mTex_PauseIcn[2]  = add("ui/menu_button.png");
		mTex_PauseIcn[3]  = add("ui/shop_button.png");
		mTex_PauseIcn[4]  = add("ui/control_button.png");
		mTex_AbtTxt       = add("ui/aboutus_text.png");
		mTex_AchiveBtn    = add("ui/avhievment_button.png");
		mTex_GamePower    = new SimplePlane[5];
		mTex_GamePower[0] = add("ui/slow_icon.png");
		mTex_GamePower[1] = add("ui/fast_icon.png");
		mTex_GamePower[2] = add("ui/bridge.png");
		mTex_GamePower[3] = add("ui/bridge_blue.png");
		mTex_GamePower[4] = add("ui/Stop_sign.png");
		mTex_Lock         = add("ui/lock.png"); 
		mTex_Power        = add("ui/box7.png");
		mTex_Preview      = add("ui/preview.png");
		mTex_SmallCoin    = add("ui/small_coinbox.png");
		mTex_PreviewBox   = add("ui/box4.png");
		mTex_UnlockTxt    = add("ui/unlock_button.png");
		mTex_ShopBox	  = add("ui/box2.png");
		mTex_Coinde       = new SimplePlane[2]; 
		mTex_Coinde[0]    = add("ui/Coin_de.png"); 
		mTex_Coinde[1]    = add("ui/Coin_se.png");
		mTex_Keyde        = new SimplePlane[2];
		mTex_Keyde[0]     = add("ui/key_de.png");
		mTex_Keyde[1]     = add("ui/key_se.png");
		mTex_Worldde      = new SimplePlane[2];
		mTex_Worldde[0]   = add("ui/world_de.png");
		mTex_Worldde[1]   = add("ui/world_se.png");
		mTex_DollarBox    = add("ui/box6.png");
		mTex_BuyIcn       = add("ui/buy_icon.png"); 
		mTex_StageFont    = add("ui/stage_font.png");
		mTex_ScoreFont    = add("ui/score_font.png");   
		mTex_ScoreBox     = add("ui/score_number_font.png");
		mTex_StageBox     = add("ui/stage_score.png");
		mTex_StageBar     = add("ui/stagefill.png");
		mTex_UseBtn       = add("ui/use_button.png");
		mTex_KeyAnimation = add("ui/key_animation.png");
		mTex_Fillcir      = add("ui/fill.png");
		mTex_PauseBtn     = add("ui/pause_button.png");
		mTex_SplashFont   = add("ui/splashfont.png");
		mTex_ChallengeBox = add("ui/challenge_box.png");
		mTex_ChallengRW    = new SimplePlane[2];
		mTex_ChallengRW[0] = add("ui/challenge_cross.png");
		mTex_ChallengRW[1] = add("ui/challenge_right.png");
		mTex_ChallengComp  = add("ui/challenge_comp.png"); 
		mTex_JoinText      = add("join_text.png"); 
		mTex_JoinBtn       = add("join.png");
		mTex_UpgradeFill   = add("ui/upgarde_fill.png");
		mTex_Buy           = add("ui/buy_power.png");
		
 	}
	void InitGameObj()
	{
		int i;
		mCar       =  new Car[20];
		mSetCar    =  new Car[20];
		mCarBreak  =  new Car[20];
		mSplashCar =  new Car[2];
		for(i=0;i<mSplashCar.length;i++)
		{
			mSplashCar[i] = new Car(this);	
		}
		for(i=0;i<mCar.length;i++)
		{
		  mCar[i]       = new Car(this);
		  mSetCar[i]    = new Car(this);
		  mCarBreak[i]  = new Car(this);
		    
		  
		}
		mAni  = new Animation[mCar.length][20];
		for(i=0;i<mAni.length;i++)
		{
		   for(int j=0;j<mAni[0].length;j++)
  		     mAni[i][j]  = new Animation(this);
		}
		mWorld = new World[mTex_WorldLock.length];
		for(i=0;i<mWorld.length;i++)
			mWorld[i] = new World();	
		mFont = new Font();
		mSlowCar    = new GameAcc(250,20,0);
		mFastCar    = new GameAcc(250,20,0);
		mBridge     = new GameAcc(500,20,0);
		mTunnel     = new GameAcc(200,20,0);
		mStopCar    = new GameAcc(100,20,0);
		mAchieveAni = new Animation(this);
		mChallenge  = new Animation(this);
		mTapEffect  = new Animation(this);
		mScoreAni   = new Animation(this);
		mCoinAni    = new Animation(this);
		setWorld();
	}
	
	void GameReset()
	{ 
//		try{LoadAD.sendEmptyMessage(0);} catch (Exception e){}
		mStart.loadInterstitial();
		for(int i=0;i<mCar.length;i++)
		{
		   mCar[i].resetCar(100,100);
		   mCarBreak[i].resetCar(100,100);
		   root.ResetBoost(i,100,100);
		}
		mCount   = 0;
	    mGenTime = 180; 
	    mCarGenSpeed = 2;	
	    mDelay   = 60;
		mOverCnt=0;
		root.Counter2=0;
		isCrash =false;
		mKeyAniCnt = 0;
		mMod       = 0;
		mScore = 0;
		mGameSec  = 0;
		mGameMin  = 1;
		mChallangeSec = 0;
		mLevelCoin = 0;
		root.Counter=0;
//		mPopX  = -1.02f;
//		mPopVX = 0;
		setLevel();
	} 
	void resetPower()
	{
	    mBridge.resetPower();
		mTunnel.resetPower();
		mStopCar.resetPower();
		mSlowCar.resetPower();
		mFastCar.resetPower();
	}
	int   worldCoin[]   ={0,300,600,900,1200,1200,1300,1400,1500,1600,1700,1800};
	float worldDollar[] ={0,0,0,.99f,.99f,.99f,.99f,.99f,.99f,.99f,.99f,1.99f};
	void setWorld()
	{
		mWorldSel  = 0;
		for(int i=0;i<mWorld.length;i++)
		{
			if(i<mWorld.length/2)
			  mWorld[i].set(0+i*.4f,worldCoin[i],worldDollar[i]);
			else
			  mWorld[i].set(0-(i-5)*.4f,worldCoin[i],worldDollar[i]);
			mWorld[i].z = 1f-(float)Math.abs(mWorld[i].x*.85f-0);
		}
		mWorld[mWorldSel].isUnLock=true;
	}
	
	void resetWorld()
	{
		for(int i=0;i<mWorld.length;i++)
		{
			if(i<mWorld.length/2)
			  mWorld[i].set(0+i*.4f,worldCoin[i],worldDollar[i]);
			else
			  mWorld[i].set(0-(i-5)*.4f,worldCoin[i],worldDollar[i]);
			mWorld[i].z = 1f-(float)Math.abs(mWorld[i].x*.85f-0);
		}
	}
	void setLevel()
	{
		System.gc();
		mWorldno = mWorldSel;
		if(mTex_WorldBG[mWorldno%mTex_WorldBG.length] == null)
  		  mTex_WorldBG[mWorldno] = add("world/"+mWorldno+".png");
		for(int i=0;i<mSetCar.length;i++)
		{
			mSetCar[i].resetCar(100,100);
		}
		switch(mWorldno)
		{
		  case 0:
				float x[]   = {-1.15f,0};
				float y[]   = {   0  ,1.19f};
				float ang[] = {0,90f};
				for(int i=0;i<x.length;i++)
				{
					mSetCar[i].set(x[i],y[i],0,0,ang[i]);
					mMod=i;
				}
			break;
		  case 1:
				float x1[]   = {-.656f  ,-.665f};
				float y1[]   = { 1.182f ,-1.193f};
				float ang1[] = {47,313f};
				for(int i=0;i<x1.length;i++)
				{
					mSetCar[i].set(x1[i],y1[i],0,0,ang1[i]);
					mMod=i;
				}
			break;
		  case 2:
				float x2[]   = {-1.19f ,1.19f,0f};
				float y2[]   = {-.129f ,.129f,-1.19f};
				float ang2[] = {0,180,270};
				for(int i=0;i<x2.length;i++)
				{
					mSetCar[i].set(x2[i],y2[i],0,0,ang2[i]);
					mMod=i;
				}
			break;
		  case 3:
				float x3[]   = {-1.19f ,1.19f,-.07f,.089f};
				float y3[]   = {-.129f ,.129f,-1.19f,1.19f};
				float ang3[] = {0,180,270,90};
				for(int i=0;i<x3.length;i++)
				{
					mSetCar[i].set(x3[i],y3[i],0,0,ang3[i]);
					mMod=i;
				}
			break;
		  case 4:
				float x4[]   = {-.784f,.785f  ,-.774f ,.765f};
				float y4[]   = {1.197f,-1.192f,-1.197f,1.182f};
				float ang4[] = {47    ,227    ,313    ,133f};
				for(int i=0;i<x4.length;i++)
				{
					mSetCar[i].set(x4[i],y4[i],0,0,ang4[i]);
					mMod=i;
				}
			break;
		  case 5:
				float x5[]   = {-1.19f,1.19f,1.19f,.069f ,-.08f};
				float y5[]   = {  0   ,.28f ,-.26f ,1.182f,-1.182f};
				float ang5[] = {0,180,180,90,270};
				for(int i=0;i<x5.length;i++)
				{
					mSetCar[i].set(x5[i],y5[i],0,0,ang5[i]);
					mMod=i;
				}
			break;
		  case 6:
				float x6[]   = {-1.19f,1.19f,-.375f,.375f};
				float y6[]   = {-.129f,.139f,-1.18f,1.18f};
				float ang6[] = {0,180,270,90};
				for(int i=0;i<x6.length;i++)
				{
					mSetCar[i].set(x6[i],y6[i],0,0,ang6[i]);
					mMod=i;
				}
			break;
		  case 7:
				float x7[]   = {-.66f ,.887f  ,.463f ,-.561f ,.982f ,.563f};
				float y7[]   = {1.188f,-1.174f,-1.182f,-1.193f,1.183f,1.198f};
				float ang7[] = {47    ,226.5f ,226.5f ,312.5f ,133f  ,133f};
				for(int i=0;i<x7.length;i++)
				{
					mSetCar[i].set(x7[i],y7[i],0,0,ang7[i]);
					mMod=i;
				}
				break;
		  case 8:
				float x8[]   = {-1.19f,-1.19f,1.19f,1.19f,-.225f,.23f};
				float y8[]   = {.399f,.139f,-.139f,-.399f,-1.19f,1.19f};
				float ang8[] = {0,0,180,180,270,90};
				for(int i=0;i<x8.length;i++)
				{
					mSetCar[i].set(x8[i],y8[i],0,0,ang8[i]);
					mMod=i;
				}
			break;
		  case 9:
				float x9[]   = {-1.19f,-1.119f,1.103f,1.19f};
				float y9[]   = {.409f ,-1.196f,1.186f ,-.389f};
				float ang9[] = {0     ,313f   ,133f   ,180};
				for(int i=0;i<x9.length;i++)
				{
					mSetCar[i].set(x9[i],y9[i],0,0,ang9[i]);
					mMod=i;
				}
				break;
		  case 10:
				float x10[]   = {-1.19f,-1.073f,1.19f,1.072f,-1.19f,1.19f };
				float y10[]   = {.533f ,-1.198f,.253f,1.171f,-.246f,-.516f};
				float ang10[] = {0     ,313f   ,180  ,133f  ,0     ,180f};
				for(int i=0;i<x10.length;i++)
				{
					mSetCar[i].set(x10[i],y10[i],0,0,ang10[i]);
					mMod=i;
				}
			break;
		  case 11:
				float x11[]   = {-1.19f,-.379f,.359f,1.19f,1.19f ,.23f  ,-.23f ,-1.19f};
				float y11[]   = {.26f  ,1.19f ,1.19f,.529f,-.509f,-1.19f,-1.19f,-.26f};
				float ang11[] = {0     , 90   ,90   ,180  ,180   ,270   ,270   ,0};
				for(int i=0;i<x11.length;i++)
				{
					mSetCar[i].set(x11[i],y11[i],0,0,ang11[i]);
					mMod=i;
				}
			break;
		}
		
	    int no=M.randomRangeInt(1,2);
		for(int i=0;i<no;i++)
		{
			mCar[i].set(mSetCar[i].x,mSetCar[i].y,0,0,mSetCar[i].ang);
			mCarIndex=i;
		}
		
	}
	int arrayPos[]={0,1,2,3,4,5,6,7,8,9,10};
	void Shuffle(int n)
	{
		for(int i=0;i<n;i++)
		{
			int radom       = M.randomRangeInt(0,mMod);
			int tmp         = arrayPos[i];
			arrayPos[i]     = arrayPos[radom];
			arrayPos[radom] = tmp; 
		}
	}
	void CreateCar()
	{
		int ii = M.randomRangeInt(0,mMod)+1;
		
		if(ii>3)
		{
		  if(mMod>5)
  		     ii=4;  
		  else
			 ii=3;
		}
		Shuffle(ii);
	    for(int n = 0; n<ii; n++)
	     {
	    	int pos = arrayPos[n];
			mCarIndex++;
			mCarIndex %=mCar.length;
			mCar[mCarIndex].set(mSetCar[pos].x,mSetCar[pos].y,0,0,mSetCar[pos].ang);
			
		 }
	}
	void setBridge()
	{
		switch(mWorldno)
		{
		  case 5:
			  float y5[] = {0,.28f,-.26f};
			  float ang[] = {0,180f,180f};
			  int pos = M.randomRangeInt(0,2);
			  mBridge.SetPos(0,y5[pos],ang[pos]);
			  mBridge.no=0;
			break;
		  case 6:
			  float x6[]   = {-.369f,.369f};
			  float y6[]   = {-.119f,.129f};
			  float ang6[] = {0     ,180f};
			  pos = M.randomRangeInt(0,1);
			  mBridge.SetPos(x6[pos],y6[pos],ang6[pos]);
			  mBridge.no=0;
			  break;
		  case 7:
			  float x7[]   = {.079f  ,.119f ,-.04f ,.059f,-.02f ,.139f};
			  float y7[]   = {-.109f ,.13f  ,-.23f ,-.07f,.119f ,-.26f};
			  float ang7[] = {47     ,226.5f,226.5f,312f ,133f  ,133f};
			  pos = M.randomRangeInt(0,x7.length-1);
			  mBridge.SetPos(x7[pos],y7[pos],ang7[pos]);
			  mBridge.no=1;
			  break;
		  case 8:
			  float x8[]   = {-.319f ,-.319f ,.26f  ,.26f};
			  float y8[]   = { .379f ,.129f  ,-.13f ,-.399f};
			  float ang8[] = {  0    ,0      ,180   , 180};
			  pos = M.randomRangeInt(0,x8.length-1);
			  mBridge.SetPos(x8[pos],y8[pos],ang8[pos]);
			  mBridge.no=0;
			  break;
		  case 9:
			  float x9[]   = { .26f  ,-.20f };
			  float y9[]   = {-.399f ,.409f };
			  float ang9[] = { 180   , 0    };
			  pos = M.randomRangeInt(0,x9.length-1);
			  mBridge.SetPos(x9[pos],y9[pos],ang9[pos]);
			  mBridge.no=0;
			  break;
		  case 10:
			  float x10[]   = { -.09f  ,.07f};
			  float y10[]   = {  .529f ,-.509f};
			  float ang10[] = {  0     , 180  };
			  pos = M.randomRangeInt(0,x10.length-1);
			  mBridge.SetPos(x10[pos],y10[pos],ang10[pos]);
			  mBridge.no=0;
			  break;
		  case 11:
			  float x11[]   = { -.29f ,.29f};
			  float y11[]   = {  .26f ,-.509f};
			  float ang11[] = {   0   , 180  };
			  pos = M.randomRangeInt(0,x11.length-1);
			  mBridge.SetPos(x11[pos],y11[pos],ang11[pos]);
			  mBridge.no=0;
			  break;
			
  		  
		}
	}
	void setTunnel()
	{
		switch(mWorldno)
		{
		  case 5:
			  float y5[] = {0,.28f,-.26f};
			  float ang[] = {0,180f,180f};
			  int pos = M.randomRangeInt(0,2);
			  mTunnel.SetPos(0,y5[pos],ang[pos]);
			break;
		  case 6:
			  float x6[]   = {-.369f,.369f};
			  float y6[]   = {-.119f,.129f};
			  float ang6[] = {0     ,180f};
			  pos = M.randomRangeInt(0,1);
			  mTunnel.SetPos(x6[pos],y6[pos],ang6[pos]);
			  break;
		  case 7:
			  float x7[]   = {.079f  ,.119f ,-.04f ,.059f,-.02f ,.139f};
			  float y7[]   = {-.109f ,.13f  ,-.23f ,-.07f,.119f ,-.26f};
			  float ang7[] = {47     ,226.5f,226.5f,312f ,133f  ,133f};
			  pos = M.randomRangeInt(0,x7.length-1);
			  mTunnel.SetPos(x7[pos],y7[pos],ang7[pos]);
			  mTunnel.no=1;
			  break;
		  case 8:
			  float x8[]   = {-.319f ,-.319f ,.26f  ,.26f};
			  float y8[]   = { .379f ,.129f  ,-.13f ,-.399f};
			  float ang8[] = {  0    ,0      ,180   , 180};
			  pos = M.randomRangeInt(0,x8.length-1);
			  mTunnel.SetPos(x8[pos],y8[pos],ang8[pos]);
			  break;
		  case 9:
			  float x9[]   = { .26f  ,-.20f };
			  float y9[]   = {-.399f ,.409f };
			  float ang9[] = { 180   , 0    };
			  pos = M.randomRangeInt(0,x9.length-1);
			  mTunnel.SetPos(x9[pos],y9[pos],ang9[pos]);
			  break;
		  case 10:
			  float x10[]   = { -.09f  ,.07f};
			  float y10[]   = {  .529f ,-.509f};
			  float ang10[] = {  0     , 180  };
			  pos = M.randomRangeInt(0,x10.length-1);
			  mTunnel.SetPos(x10[pos],y10[pos],ang10[pos]);
			  break;
		  case 11:
			  float x11[]   = { -.29f ,.29f};
			  float y11[]   = {  .26f ,-.509f};
			  float ang11[] = {   0   , 180  };
			  pos = M.randomRangeInt(0,x11.length-1);
			  mTunnel.SetPos(x11[pos],y11[pos],ang11[pos]);
			  break;
			
  		  
		}
	}

//	sudo apt-get install flashplugin-installer 
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		
  	    gl.glClearColor(0,0,0,1);
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
	}
	public void onDrawFrame(GL10 gl) 
	{
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
 	    gl.glClearColor(0,0,0,1);
		gl.glLoadIdentity();
		root.draw(gl);
		if(M.GameScreen!=M.GAMEWORLD)
		  gl.glDisable(GL10.GL_SCISSOR_TEST);
		//Banner AD
		resumeCounter++;
		if(resumeCounter>50)
		{
			 if(mStart.adBanner!=null)
	 		 {
			
				if(!mStart.addFree &&( M.GameScreen == M.GAMEMENU || M.GameScreen == M.GAMEABTUS || M.GameScreen == M.GAMEKEYUSE ||  M.GameScreen == M.GAMEPAUSE))
				{
					int inv=mStart.adBanner.getVisibility();
					if(inv==AdView.GONE){try{handlerAdBanner.sendEmptyMessage(AdView.VISIBLE);}catch(Exception e){}}
				}
				else
				{
					int inv=mStart.adBanner.getVisibility();
					if(inv==AdView.VISIBLE){try{handlerAdBanner.sendEmptyMessage(AdView.GONE);}catch(Exception e){}}
				}
			 }
			// Adload
			if (mStart.adRect != null)  
			{
				if(!mStart.addFree && (M.GameScreen == M.GAMEADD || M.GameScreen == M.GAMELOADING))
				{
					int inv = mStart.adRect.getVisibility();
					if (inv == AdView.GONE) {try {handlerAdRect.sendEmptyMessage(AdView.VISIBLE);} catch (Exception e) {}}
				} 
				else
				{
					int inv = mStart.adRect.getVisibility();
					if (inv == AdView.VISIBLE) {try {handlerAdRect.sendEmptyMessage(AdView.GONE);} catch (Exception e) {}
					}
				}
			}
			if(mStart.addFree)
			{
				int inv = mStart.adBanner.getVisibility();
				if(inv==AdView.VISIBLE){try{handlerAdBanner.sendEmptyMessage(AdView.GONE);}catch(Exception e){}}
				
				
				inv = mStart.adRect.getVisibility();
				if (inv == AdView.VISIBLE) {try {handlerAdRect.sendEmptyMessage(AdView.GONE);} catch (Exception e) {}}
			}
		}
		if(resumeCounter>500000)
			resumeCounter=0;
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
	SimplePlane addBig (String ID)
	{
		SimplePlane SP = null;
		Bitmap b = LoadImgfromAsset(ID);
		try
		{
			SP = new SimplePlane(((b.getWidth()+20f)/(M.mMaxX)),(b.getHeight()/(M.mMaxY)));
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
	SimplePlane addDouble(String ID)
	{
		SimplePlane SP = null;
		Bitmap b = LoadImgfromAsset(ID);
		try
		{
			SP = new SimplePlane((b.getWidth()/(.5f*M.mMaxX)),(b.getHeight()/(.65f*M.mMaxY)));
			SP.loadBitmap(b);// R.drawable.jay
		}catch(Exception e){}
		return SP;
	}
	public static SimplePlane addBitmap (Bitmap b)
	{
		SimplePlane SP = null;
		try
		{
			SP = new SimplePlane((b.getWidth()/M.mMaxX),(b.getHeight()/M.mMaxY));
			SP.loadBitmap(b);// R.drawable.jay
		}catch(Exception e){}
		return SP;
	}
	SimplePlane addBitmapR(Bitmap b)
	{
		SimplePlane SP = null;
		try
		{
			SP = new SimplePlane((b.getWidth()/M.mMaxY),(b.getHeight()/M.mMaxY));
			SP.loadBitmap(b);// R.drawable.jay
		}catch(Exception e){}
		return SP;
	}
	public static Bitmap LoadImgfromAsset(String ID)
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
	Bitmap FlipHorizontal(Bitmap bitmapOrg,int type)
	{
		Matrix matrix = new Matrix();
		if(type ==0) // Verticle
		 matrix.postScale(1f,-1f);
		if(type ==1) //Horizontal
	  	 matrix.postScale(-1f, 1f);
		matrix.postRotate(0);
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0,bitmapOrg.getWidth(), bitmapOrg.getHeight(), matrix, true);
		return resizedBitmap;
	}
	void loadFont() {
		mTex_Font  	 = new SimplePlane[10];
		Bitmap b = LoadImgfromAsset("ui/number_font.png");
		for(int i = 0; i < mTex_Font.length; i++)
		{
		    mTex_Font[i]  = addBitmap(Bitmap.createBitmap(b, i*(b.getWidth()-(16*6))/ mTex_Font.length, 0, (b.getWidth()-(16*6))/mTex_Font.length,b.getHeight(), null, true));
		}
	}
}


//void setStop()
//{
//	switch(mWorldno)
//	{
//	  case 5:
//		  float x5[]  = {-.2f,-.2f,0};
//		  float y5[]  = {0,.38f,-.36f};
//		  float ang[] = {0,180f,180f};
//		  int pos = M.randomRangeInt(0,2);
//		  mStopCar.SetPos(x5[pos],y5[pos],ang[pos]);
//		break;
//	  case 6:
//		  float x6[]   = {-.369f,.369f};
//		  float y6[]   = {-.119f,.129f};
//		  float ang6[] = {0     ,180f};
//		  pos = M.randomRangeInt(0,1);
//		  mStopCar.SetPos(x6[pos],y6[pos],ang6[pos]);
//		  break;
//	  case 7:
//		  float x7[]   = {.079f  ,.119f ,-.04f ,.059f,-.02f ,.139f};
//		  float y7[]   = {-.109f ,.13f  ,-.23f ,-.07f,.119f ,-.26f};
//		  float ang7[] = {47     ,226.5f,226.5f,312f ,133f  ,133f};
//		  pos = M.randomRangeInt(0,x7.length-1);
//		  mStopCar.SetPos(x7[pos],y7[pos],ang7[pos]);
//		  break;
//	  case 8:
//		  float x8[]   = {-.319f ,-.319f ,.26f  ,.26f};
//		  float y8[]   = { .379f ,.129f  ,-.13f ,-.399f};
//		  float ang8[] = {  0    ,0      ,180   , 180};
//		  pos = M.randomRangeInt(0,x8.length-1);
//		  mStopCar.SetPos(x8[pos],y8[pos],ang8[pos]);
//		  break;
//	  case 9:
//		  float x9[]   = { .26f  ,-.20f };
//		  float y9[]   = {-.399f ,.409f };
//		  float ang9[] = { 180   , 0    };
//		  pos = M.randomRangeInt(0,x9.length-1);
//		  mStopCar.SetPos(x9[pos],y9[pos],ang9[pos]);
//		  break;
//	  case 10:
//		  float x10[]   = { -.09f  ,.07f};
//		  float y10[]   = {  .529f ,-.509f};
//		  float ang10[] = {  0     , 180  };
//		  pos = M.randomRangeInt(0,x10.length-1);
//		  mStopCar.SetPos(x10[pos],y10[pos],ang10[pos]);
//		  break;
//	  case 11:
//		  float x11[]   = { -.29f ,.29f};
//		  float y11[]   = {  .26f ,-.509f};
//		  float ang11[] = {   0   , 180  };
//		  pos = M.randomRangeInt(0,x11.length-1);
//		  mStopCar.SetPos(x11[pos],y11[pos],ang11[pos]);
//		  break;
//		
//		  
//	}
//	mStopCar.isActive=true;
//	mStopCar.mActivetime=100;
//}