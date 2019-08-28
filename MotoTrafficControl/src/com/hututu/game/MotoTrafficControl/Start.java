package com.hututu.game.MotoTrafficControl;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.games.Games;
import com.hututu.game.MotoTrafficControl.R;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class Start extends BaseGameActivity
{
	int _keyCode = 0;
	boolean addFree = false; 
	boolean SingUpadate = false;
	BroadcastReceiver mReceiver;
	GameRenderer mGR = null;
	private static Context CONTEXT;
	private InterstitialAd interstitial;
	AdView adBanner,adRect;
	void callAdds()
	{
		adBanner = (AdView) this.findViewById(R.id.adBanner);
		AdRequest adRequest = new AdRequest.Builder().build();
//		.addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("67701763FB847AEBD3C6EE486475ED94")
		adBanner.loadAd(adRequest);
		adBanner.setAdListener(new AdListener() {
			public void onAdLoaded() {
				adBanner.bringToFront();
			}
		});
		
		adRect    = (AdView) this.findViewById(R.id.adRect);
	    adRequest = new AdRequest.Builder().build();
//		.addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("67701763FB847AEBD3C6EE486475ED94")
		adRect.loadAd(adRequest);
		adRect.setAdListener(new AdListener() {
			public void onAdLoaded() {
				adRect.bringToFront();
			}
		});
	}
	public void onCreate(Bundle savedInstanceState) 
	{
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);//OnlyOneChange
		CONTEXT	=	this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		interstitial = new InterstitialAd(this);
		interstitial.setAdUnitId(M.MY_INTERSTITIAL_ID);
		
		WindowManager mWinMgr = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
		M.ScreenWidth =mWinMgr.getDefaultDisplay().getWidth();
		M.ScreenHieght=mWinMgr.getDefaultDisplay().getHeight();
		mGR=new GameRenderer(this);
	    VortexView glSurface=(VortexView) findViewById(R.id.vortexview); // use the xml to set the view
	    glSurface.setRenderer(mGR);
	    glSurface.showRenderer(mGR);
	    if(!getSharedPreferences("X", MODE_PRIVATE).getBoolean("addFree", addFree))
	    	callAdds();
	    
	    
//	    //Recievier 
	    IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        mReceiver = new ScreenReceiver();
        registerReceiver(mReceiver, filter);
//	   //Recievier
	}

	public static Context getContext() {
	        return CONTEXT;
	}
	@Override 
	public void onPause () {
		M.StopSound();
		pause();
		super.onPause();
	}
	@Override 
	public void onResume() {
		resume();
		super.onResume();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		Log.d("----------------=>  "+keyCode,"   -----------    ");
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			switch (M.GameScreen){
			case M.GAMEPLAY:
				M.GameScreen=M.GAMEPAUSE;
				M.StopSound();
				break;
			case M.GAMESPLASH:
				if(mGR.mFreeCoin>0)
				  {
					M.FreeCoinSound(GameRenderer.mContext,R.drawable.freecoin);
				    M.GameScreen = M.GAMEFREECOIN;
				    mGR.root.mScal=.1f;
				    mGR.mTotalCoin +=mGR.mFreeCoin;
				    mGR.mFreeCoin=0;
				    
				  }
				  else
				  {
					  M.GameScreen = M.GAMEWORLD;
				  }
				M.StopSound();
				break;
			  case M.GAMEADD:
				   M.SplashPlay(GameRenderer.mContext,R.drawable.splash_theme);
				   M.GameScreen = M.GAMESPLASH;
				   mGR.root.setSplash();
				   mGR.root.Counter =0;
				   mGR.root.mScal=.1f;
				break;
			case M.GAMEKEYUSE:
				M.GameScreen = M.GAMEOVER; 
				break;
			case M.GAMEMENU:
				 mGR.resetWorld();
			     M.GameScreen = M.GAMEWORLD;
				break;
			case M.GAMEABTUS:
				 M.GameScreen = M.GAMEMENU;
				break;
			case M.GAMEUSEPOWER:
				if(M.SetBG)
					M.BGPlay(GameRenderer.mStart,R.drawable.bg);
				M.GameScreen = M.GAMEPLAY;
				break;
			default:
				 mGR.resetWorld();
				 M.GameScreen=M.GAMEWORLD;
				break;
			case M.GAMEWORLD:
				 Exit();
				break;
			
			}
			return false;
		}
		_keyCode = keyCode;
		return super.onKeyDown(keyCode,event); 
	}  
	public boolean onKeyUp(int keyCode, KeyEvent event) 
	{
//		if(keyCode==KeyEvent.KEYCODE_BACK)
//			return false;
		_keyCode = 0;
		return super.onKeyUp( keyCode, event ); 
	}

	public void onDestroy(){
		if(mReceiver != null)
        {
            unregisterReceiver(mReceiver);
            mReceiver = null;
        }
		if(mGR.mMainActivity!=null)
		   mGR.mMainActivity.onDestroy();
		super.onDestroy();
	}

	void resetvalue()
	{
		int i=0;
	
		mGR.resumeCounter = 0;
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
	    Editor editor = prefs.edit();
	    editor.putBoolean("addFree",false);
	    editor.putBoolean("SingUpadate",false);
	    editor.putInt("mTotalCoin" , 0);
	    editor.putInt("mKharchCoin", 0);
	    editor.putInt("tmpCoin"    , 0);
	    editor.putInt("mLevelCoin" , 0);
	    editor.putInt("mNoKey"     , 1);
	    editor.putInt("mScore"     , 0);
	    editor.putInt("mBestScore" , 0);
	    editor.putInt("mBestStage" , 0);
	    editor.putInt("mStageRem"  , 0);
	    editor.putInt("mTotalMin"  , 0);
	    editor.putInt("mTotalSec"  , 0);
	    editor.putInt("mNoCrossCar", 0);
	    editor.putInt("mChallangetime",0);
	    
	    for(i=0;i<mGR.mWorld.length;i++)
	    {
    	   if(i==0)
    	     editor.putBoolean("isUnLock"+i,true);
    	   else
		     editor.putBoolean("isUnLock"+i,false);
	    }
	    editor.putInt("SlowCartime",0);
	    editor.putInt("SlowCarUnVal",250);
	    editor.putInt("SlowCarUpgradeVal",20);
	    editor.putInt("SlowCarnoBuy",0);
	    editor.putBoolean("SlowCarisActive",false);
	    editor.putBoolean("SlowCarisUnlock",false);
	    
	    editor.putInt("mFastCartime",0);
	    editor.putInt("mFastCarUnVal",250);
	    editor.putInt("mFastCarUpgradeVal",20);
	    editor.putInt("FastCarnoBuy",0);
	    editor.putBoolean("mFastCarisActive",false);
	    editor.putBoolean("mFastCarisUnlock",false);
	    
	    editor.putInt("mBridgetime",0);
	    editor.putInt("mBridgeUnVal",500);
	    editor.putInt("mBridgeUpgradeVal",20);
	    editor.putInt("mBridgenoBuy",0);
	    editor.putBoolean("mBridgeisActive",false);
	    editor.putBoolean("mBridgeisUnlock",false);
	    
	    editor.putInt("mTunneltime",0);
	    editor.putInt("mTunnelUnVal",200);
	    editor.putInt("mTunnelUpgradeVal",20);
	    editor.putInt("mTunnelnoBuy",0);
	    editor.putBoolean("mTunnelisActive",false);
	    editor.putBoolean("mTunnelisUnlock",false);
	    
	    editor.putInt("mStopCartime",0);
	    editor.putInt("mStopCarUnVal",100);
	    editor.putInt("mStopCarUpgradeVal",20);
	    editor.putInt("mStopCarnoBuy",0);
	    editor.putBoolean("mStopCarisActive",false);
	    editor.putBoolean("mStopCarisUnlock",false);
	    
	    
	    for(i=0;i<mGR.isChalleng.length;i++)
	    {
	      editor.putBoolean("isChallange"+i,false);
	    }
	    for(i=0;i<mGR.isAchie.length;i++)
	    {
	    	editor.putBoolean("isAchieve"+i,false);
	    }
	    
	    editor.commit();
	    
	}
	void pause()
	{
		int i=0;
		if(M.GameScreen == M.GAMEPLAY)
			 M.GameScreen = M.GAMEPAUSE;
		mGR.resumeCounter = 0;
		M.StopSound();
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
	    Editor editor = prefs.edit();
	    
	    editor.putBoolean("addFree", addFree);
	    editor.putBoolean("SingUpadate", SingUpadate);
	    editor.putInt("screen", M.GameScreen);
	    editor.putBoolean("setValue", M.setValue);
	    editor.putInt("mWorldno", mGR.mWorldno);
	    editor.putInt("mTotalCoin", mGR.mTotalCoin);
	    editor.putInt("mKharchCoin", mGR.mKharchCoin);
	    editor.putInt("tmpCoin", mGR.tmpCoin);
	    editor.putInt("mLevelCoin", mGR.mLevelCoin);
	    editor.putInt("mNoKey", mGR.mNoKey);
	    editor.putInt("mWorldSel", mGR.mWorldSel);
	    editor.putInt("mScore", mGR.mScore);
	    editor.putInt("mBestScore", mGR.mBestScore);
	    editor.putInt("mMove", mGR.mMove);
	    editor.putInt("mOverCnt", mGR.mOverCnt);
	    editor.putInt("mKeyAniCnt", mGR.mKeyAniCnt);
	    editor.putInt("mCount", mGR.mCount);
	    editor.putInt("mGenTime", mGR.mGenTime);
	    editor.putInt("mCarGenSpeed", mGR.mCarGenSpeed);
	    editor.putInt("mDelay", mGR.mDelay);
	    editor.putInt("mAdCount", mGR.mAdCount);
	    editor.putInt("mCarIndex", mGR.mCarIndex);
	    editor.putInt("mMod", mGR.mMod);
	    editor.putInt("mGameTime", mGR.mGameSec);
	    editor.putInt("mGameMin", mGR.mGameMin);
	    
	    editor.putInt("noStage", mGR.noStage);
	    editor.putInt("mBestStage", mGR.mBestStage);
	    editor.putInt("mStageRem",mGR.mStageRem);
	    editor.putInt("mTotalMin", mGR.mTotalMin);
	    editor.putInt("mTotalSec", mGR.mTotalSec);
	    editor.putInt("mNoCrossCar", mGR.mNoCrossCar);
	    editor.putFloat("trans",mGR.root.trans);
	    editor.putFloat("mScal",mGR.root.mScal);
	    editor.putFloat("inc",mGR.root.inc);
	    editor.putFloat("px",mGR.root.px);
	    editor.putFloat("py",mGR.root.py);
	    editor.putFloat("ChallengY",mGR.root.ChallengY);
	    editor.putBoolean("isCrash", mGR.isCrash);
	    editor.putBoolean("isJoin", mGR.isJoin);
//	    editor.putFloat("mPopX",mGR.mPopX);
//	    editor.putFloat("mPopVX",mGR.mPopVX);
	    editor.putInt("mFreeCoin",mGR.mFreeCoin);
	    
	    
	    for(i=0;i<mGR.mSplashCar.length;i++)
	    {
	    	editor.putFloat("mSplashCarX"+i,mGR.mSplashCar[i].x);
	    	editor.putFloat("mSplashCarY"+i,mGR.mSplashCar[i].y);
	    	editor.putFloat("mSplashCarVX"+i,mGR.mSplashCar[i].vx);
	    	editor.putFloat("mSplashCarVy"+i,mGR.mSplashCar[i].vy);
	    	editor.putFloat("mSplashCarAng"+i,mGR.mSplashCar[i].ang);
	    	editor.putInt("mSplashCarNo"+i,mGR.mSplashCar[i].no);
	    }
	    for(i=0;i<mGR.mCar.length;i++)
	    {
	    	editor.putFloat("CarX"+i,mGR.mCar[i].x);
	    	editor.putFloat("CarY"+i,mGR.mCar[i].y);
	    	editor.putFloat("CarVX"+i,mGR.mCar[i].vx);
	    	editor.putFloat("CarVy"+i,mGR.mCar[i].vy);
	    	editor.putFloat("CarAng"+i,mGR.mCar[i].ang);
	    	editor.putFloat("CarSpeed"+i,mGR.mCar[i].Speed);
	    	editor.putInt("CarNo"+i,mGR.mCar[i].no);
	    	editor.putInt("CarTap"+i,mGR.mCar[i].mTap);
	    	editor.putBoolean("isCollide"+i, mGR.mCar[i].isCollide);
	    	editor.putBoolean("isBridge"+i, mGR.mCar[i].isBridge);
	    	editor.putBoolean("isTuunel"+i, mGR.mCar[i].isTuunel);
	    	editor.putBoolean("isStop"+i, mGR.mCar[i].isStop);
	    	
	    	
	    	editor.putFloat("mSetCarX"+i  ,mGR.mSetCar[i].x);
	    	editor.putFloat("mSetCarY"+i  ,mGR.mSetCar[i].y);
	    	editor.putFloat("mSetCarVX"+i ,mGR.mSetCar[i].vx);
	    	editor.putFloat("mSetCarVy"+i ,mGR.mSetCar[i].vy);
	    	editor.putFloat("mSetCarAng"+i,mGR.mSetCar[i].ang);
	    	
	    	editor.putFloat("CarBreakX"+i,mGR.mCarBreak[i].x);
	    	editor.putFloat("CarBreakY"+i,mGR.mCarBreak[i].y);
	    	editor.putFloat("CarBreakAng"+i,mGR.mCarBreak[i].ang);
	    	editor.putInt("CarBreakNo"+i,mGR.mCarBreak[i].no);
	    	
	    }
	    for(i=0;i<mGR.mAni.length;i++)
		{
		   for(int j=0;j<mGR.mAni[0].length;j++)
		   {
			   editor.putFloat(i+"mAniX"+j,mGR.mAni[i][j].x);
			   editor.putFloat(i+"mAniY"+j,mGR.mAni[i][j].y);
			   editor.putFloat(i+"mAniVX"+j,mGR.mAni[i][j].vx);
			   editor.putFloat(i+"mAniVY"+j,mGR.mAni[i][j].vy);
			   editor.putFloat(i+"mAniZ"+j,mGR.mAni[i][j].z);
			   editor.putFloat(i+"mAnit"+j,mGR.mAni[i][j].t);
			   editor.putFloat(i+"mAnidt"+j,mGR.mAni[i][j].dt);
			   editor.putFloat(i+"mAniang"+j,mGR.mAni[i][j].ang);
		   }
		}
	    for(i=0;i<mGR.mWorld.length;i++)
	    {
	    	   editor.putFloat("mWorldX"+i,mGR.mWorld[i].x);
	    	   editor.putFloat("mWorldZ"+i,mGR.mWorld[i].z);
	    	   editor.putFloat("mWorldDollar"+i,mGR.mWorld[i].mDollar);
	    	   editor.putInt("mWorldCoin"+i,mGR.mWorld[i].mWorldCoin);
	    	   editor.putBoolean("isUnLock"+i,mGR.mWorld[i].isUnLock);
	    	   editor.putFloat("WorldVX",World.VX);
	    	   editor.putInt("WorldCount",World.Count);
	    }
	    editor.putFloat("mSlowCarX",mGR.mSlowCar.x);
	    editor.putFloat("mSlowCarY",mGR.mSlowCar.y);
	    editor.putFloat("mSlowCarang",mGR.mSlowCar.ang);
	    editor.putInt("SlowCartime",mGR.mSlowCar.mActivetime);
	    editor.putInt("SlowCarUnVal",mGR.mSlowCar.mUnlockVal);
	    editor.putInt("SlowCarUpgradeVal",mGR.mSlowCar.mUpgradeVal);
	    editor.putInt("SlowCarnoBuy",mGR.mSlowCar.noBuy);
	    editor.putBoolean("SlowCarisActive",mGR.mSlowCar.isActive);
	    editor.putBoolean("SlowCarisUnlock",mGR.mSlowCar.isUnlock);
	    
	    editor.putFloat("mFastCarX",mGR.mFastCar.x);
	    editor.putFloat("mFastCarY",mGR.mFastCar.y);
	    editor.putFloat("mFastCarang",mGR.mFastCar.ang);
	    editor.putInt("mFastCartime",mGR.mFastCar.mActivetime);
	    editor.putInt("mFastCarUnVal",mGR.mFastCar.mUnlockVal);
	    editor.putInt("mFastCarUpgradeVal",mGR.mFastCar.mUpgradeVal);
	    editor.putInt("FastCarnoBuy",mGR.mFastCar.noBuy);
	    editor.putBoolean("mFastCarisActive",mGR.mFastCar.isActive);
	    editor.putBoolean("mFastCarisUnlock",mGR.mFastCar.isUnlock);
	    
	    editor.putFloat("mBridgeX",mGR.mBridge.x);
	    editor.putFloat("mBridgeY",mGR.mBridge.y);
	    editor.putFloat("mBridgeang",mGR.mBridge.ang);
	    editor.putInt("mBridgetime",mGR.mBridge.mActivetime);
	    editor.putInt("mBridgeUnVal",mGR.mBridge.mUnlockVal);
	    editor.putInt("mBridgeUpgradeVal",mGR.mBridge.mUpgradeVal);
	    editor.putInt("mBridgenoBuy",mGR.mBridge.noBuy);
	    editor.putBoolean("mBridgeisActive",mGR.mBridge.isActive);
	    editor.putBoolean("mBridgeisUnlock",mGR.mBridge.isUnlock);
	    
	    editor.putFloat("mTunnelX",mGR.mTunnel.x);
	    editor.putFloat("mTunnelY",mGR.mTunnel.y);
	    editor.putFloat("mTunnelang",mGR.mTunnel.ang);
	    editor.putInt("mTunneltime",mGR.mTunnel.mActivetime);
	    editor.putInt("mTunnelUnVal",mGR.mTunnel.mUnlockVal);
	    editor.putInt("mTunnelUpgradeVal",mGR.mTunnel.mUpgradeVal);
	    editor.putInt("mTunnelnoBuy",mGR.mTunnel.noBuy);
	    editor.putBoolean("mTunnelisActive",mGR.mTunnel.isActive);
	    editor.putBoolean("mTunnelisUnlock",mGR.mTunnel.isUnlock);
	    
	    editor.putFloat("mStopCarX",mGR.mStopCar.x);
	    editor.putFloat("mStopCarY",mGR.mStopCar.y);
	    editor.putFloat("mStopCarang",mGR.mStopCar.ang);
	    editor.putInt("mStopCartime",mGR.mStopCar.mActivetime);
	    editor.putInt("mStopCarUnVal",mGR.mStopCar.mUnlockVal);
	    editor.putInt("mStopCarUpgradeVal",mGR.mStopCar.mUpgradeVal);
	    editor.putInt("mStopCarnoBuy",mGR.mStopCar.noBuy);
	    editor.putBoolean("mStopCarisActive",mGR.mStopCar.isActive);
	    editor.putBoolean("mStopCarisUnlock",mGR.mStopCar.isUnlock);
	    
	    
	    editor.putFloat("mAchieveAniX", mGR.mAchieveAni.x);
	    editor.putFloat("mAchieveAniY", mGR.mAchieveAni.y);
	    editor.putFloat("mAchieveAniCnt", mGR.mAchieveAni.ang);
	    editor.putFloat("mAchieveAniVZ", mGR.mAchieveAni.vz);
	    
	    
	    editor.putFloat("mChallengeX", mGR.mChallenge.x);
	    editor.putFloat("mChallengeY", mGR.mChallenge.y);
	    editor.putFloat("mChallengeCnt", mGR.mChallenge.ang);
	    editor.putFloat("mChallengeVZ", mGR.mChallenge.vz);
	    
	    editor.putFloat("mTapEffectX", mGR.mTapEffect.x);
	    editor.putFloat("mTapEffectY", mGR.mTapEffect.y);
	    editor.putFloat("mTapEffectZ", mGR.mTapEffect.z);
	    
	    editor.putInt("mstageCounter",mGR.mstageCounter);
	    editor.putInt("mStageFill",mGR.mStageFill);
	    
	    editor.putInt("mChallangetime",mGR.mChallangeSec);
	    
	    for(i=0;i<mGR.isChalleng.length;i++)
	    {
	      editor.putBoolean("isChallange"+i,mGR.isChalleng[i]);
	    }
	    for(i=0;i<mGR.isAchie.length;i++)
	    {
	    	editor.putBoolean("isAchieve"+i,mGR.isAchie[i]);
	    }
	    editor.commit();
	    
	}
	void resume()
	{
		int i=0;
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		addFree 				= prefs.getBoolean("addFree", addFree);
		SingUpadate 			= prefs.getBoolean("SingUpadate", SingUpadate);
		M.GameScreen 	    	= prefs.getInt("screen", M.GameScreen);
		M.setValue 	 	    	= prefs.getBoolean("setValue", M.setValue);
	 	
		mGR.mWorldno    = prefs.getInt("mWorldno", mGR.mWorldno);
		mGR.mTotalCoin  = prefs.getInt("mTotalCoin", mGR.mTotalCoin);
		mGR.mKharchCoin = prefs.getInt("mKharchCoin", mGR.mKharchCoin);
		mGR.tmpCoin     = prefs.getInt("tmpCoin", mGR.tmpCoin);
		mGR.mLevelCoin  = prefs.getInt("mLevelCoin", mGR.mLevelCoin);
		mGR.mNoKey 		= prefs.getInt("mNoKey", mGR.mNoKey);
		mGR.mWorldSel   = prefs.getInt("mWorldSel", mGR.mWorldSel);
		mGR.mScore      = prefs.getInt("mScore", mGR.mScore);
		mGR.mBestScore  = prefs.getInt("mBestScore", mGR.mBestScore);
		mGR.mMove       = prefs.getInt("mMove", mGR.mMove);
		mGR.mOverCnt    = prefs.getInt("mOverCnt", mGR.mOverCnt);
		mGR.mKeyAniCnt  = prefs.getInt("mKeyAniCnt", mGR.mKeyAniCnt);
		mGR.mCount      = prefs.getInt("mCount", mGR.mCount);
		mGR.mGenTime    = prefs.getInt("mGenTime", mGR.mGenTime);
		mGR.mCarGenSpeed = prefs.getInt("mCarGenSpeed", mGR.mCarGenSpeed);
		mGR.mDelay       = prefs.getInt("mDelay", mGR.mDelay);
		mGR.mAdCount     = prefs.getInt("mAdCount", mGR.mAdCount);
		mGR.mCarIndex    = prefs.getInt("mCarIndex", mGR.mCarIndex);
		mGR.mMod         = prefs.getInt("mMod", mGR.mMod);
		mGR.mGameSec     = prefs.getInt("mGameTime", mGR.mGameSec);
		mGR.mGameMin     = prefs.getInt("mGameMin", mGR.mGameMin);
		mGR.noStage		 = prefs.getInt("noStage", mGR.noStage);
		mGR.mBestStage   = prefs.getInt("mBestStage", mGR.mBestStage);
		mGR.mStageRem    = prefs.getInt("mStageRem",mGR.mStageRem);
		mGR.mTotalMin    = prefs.getInt("mTotalMin", mGR.mTotalMin);
		mGR.mTotalSec    = prefs.getInt("mTotalSec", mGR.mTotalSec);
		mGR.mNoCrossCar  = prefs.getInt("mNoCrossCar", mGR.mNoCrossCar);
		mGR.root.trans   = prefs.getFloat("trans",mGR.root.trans);
		mGR.root.mScal   = prefs.getFloat("mScal",mGR.root.mScal);
		mGR.root.inc     = prefs.getFloat("inc",mGR.root.inc);
		mGR.root.px      = prefs.getFloat("px",mGR.root.px);
		mGR.root.py      = prefs.getFloat("py",mGR.root.py);
		mGR.isCrash      = prefs.getBoolean("isCrash", mGR.isCrash);
		mGR.isJoin       = prefs.getBoolean("isJoin", mGR.isJoin);
		mGR.root.ChallengY = prefs.getFloat("ChallengY",mGR.root.ChallengY);
		
		mGR.mstageCounter = prefs.getInt("mstageCounter",mGR.mstageCounter);
		mGR.mStageFill    = prefs.getInt("mStageFill",mGR.mStageFill);
		
//		mGR.mPopX  = prefs.getFloat("mPopX",mGR.mPopX);
//		mGR.mPopVX = prefs.getFloat("mPopVX",mGR.mPopVX);
		mGR.mFreeCoin = prefs.getInt("mFreeCoin",mGR.mFreeCoin);
	    
	    for(i=0;i<mGR.mSplashCar.length;i++)
	    {
	    	mGR.mSplashCar[i].x  = prefs.getFloat("mSplashCarX"+i,mGR.mSplashCar[i].x);
	    	mGR.mSplashCar[i].y  = prefs.getFloat("mSplashCarY"+i,mGR.mSplashCar[i].y);
	    	mGR.mSplashCar[i].vx = prefs.getFloat("mSplashCarVX"+i,mGR.mSplashCar[i].vx);
	    	mGR.mSplashCar[i].vy = prefs.getFloat("mSplashCarVy"+i,mGR.mSplashCar[i].vy);
	    	mGR.mSplashCar[i].ang = prefs.getFloat("mSplashCarAng"+i,mGR.mSplashCar[i].ang);
	    	mGR.mSplashCar[i].no  = prefs.getInt("mSplashCarNo"+i,mGR.mSplashCar[i].no);
	    }
	    for(i=0;i<mGR.mCar.length;i++)
	    {
	    	mGR.mCar[i].x         = prefs.getFloat("CarX"+i,mGR.mCar[i].x);
	    	mGR.mCar[i].y 		  = prefs.getFloat("CarY"+i,mGR.mCar[i].y);
	    	mGR.mCar[i].vx 		  = prefs.getFloat("CarVX"+i,mGR.mCar[i].vx);
	    	mGR.mCar[i].vy 		  = prefs.getFloat("CarVy"+i,mGR.mCar[i].vy);
	    	mGR.mCar[i].ang 	  = prefs.getFloat("CarAng"+i,mGR.mCar[i].ang);
	    	mGR.mCar[i].Speed 	  = prefs.getFloat("CarSpeed"+i,mGR.mCar[i].Speed);
	    	mGR.mCar[i].no 		  = prefs.getInt("CarNo"+i,mGR.mCar[i].no);
	    	mGR.mCar[i].mTap 	  = prefs.getInt("CarTap"+i,mGR.mCar[i].mTap);
	    	mGR.mCar[i].isCollide = prefs.getBoolean("isCollide"+i, mGR.mCar[i].isCollide);
	    	mGR.mCar[i].isBridge  = prefs.getBoolean("isBridge"+i, mGR.mCar[i].isBridge);
	    	mGR.mCar[i].isTuunel  = prefs.getBoolean("isTuunel"+i, mGR.mCar[i].isTuunel);
	    	mGR.mCar[i].isStop    = prefs.getBoolean("isStop"+i, mGR.mCar[i].isStop);
	    	
	    	
	    	
	    	mGR.mSetCar[i].x  = prefs.getFloat("mSetCarX"+i  ,mGR.mSetCar[i].x);
	    	mGR.mSetCar[i].y  = prefs.getFloat("mSetCarY"+i  ,mGR.mSetCar[i].y);
	    	mGR.mSetCar[i].vx = prefs.getFloat("mSetCarVX"+i ,mGR.mSetCar[i].vx);
	    	mGR.mSetCar[i].vy = prefs.getFloat("mSetCarVy"+i ,mGR.mSetCar[i].vy);
	    	mGR.mSetCar[i].ang = prefs.getFloat("mSetCarAng"+i,mGR.mSetCar[i].ang);
	    	
	    	mGR.mCarBreak[i].x = prefs.getFloat("CarBreakX"+i,mGR.mCarBreak[i].x);
	    	mGR.mCarBreak[i].y = prefs.getFloat("CarBreakY"+i,mGR.mCarBreak[i].y);
	    	mGR.mCarBreak[i].ang = prefs.getFloat("CarBreakAng"+i,mGR.mCarBreak[i].ang);
	    	mGR.mCarBreak[i].no  = prefs.getInt("CarBreakNo"+i,mGR.mCarBreak[i].no);
	    }
	    for(i=0;i<mGR.mAni.length;i++)
		{
		   for(int j=0;j<mGR.mAni[0].length;j++)
		   {
			   mGR.mAni[i][j].x   =  prefs.getFloat(i+"mAniX"+j,mGR.mAni[i][j].x);
			   mGR.mAni[i][j].y   =  prefs.getFloat(i+"mAniY"+j,mGR.mAni[i][j].y);
			   mGR.mAni[i][j].vx  =  prefs.getFloat(i+"mAniVX"+j,mGR.mAni[i][j].vx);
			   mGR.mAni[i][j].vy  =  prefs.getFloat(i+"mAniVY"+j,mGR.mAni[i][j].vy);
			   mGR.mAni[i][j].z   =  prefs.getFloat(i+"mAniZ"+j,mGR.mAni[i][j].z);
			   mGR.mAni[i][j].t   =  prefs.getFloat(i+"mAnit"+j,mGR.mAni[i][j].t);
			   mGR.mAni[i][j].dt  =  prefs.getFloat(i+"mAnidt"+j,mGR.mAni[i][j].dt);
			   mGR.mAni[i][j].ang =  prefs.getFloat(i+"mAniang"+j,mGR.mAni[i][j].ang);
		   }
		}
	    for(i=0;i<mGR.mWorld.length;i++)
	    {
	    	mGR.mWorld[i].x = prefs.getFloat("mWorldX"+i,mGR.mWorld[i].x);
	    	mGR.mWorld[i].z = prefs.getFloat("mWorldZ"+i,mGR.mWorld[i].z);
	    	mGR.mWorld[i].mDollar = prefs.getFloat("mWorldDollar"+i,mGR.mWorld[i].mDollar);
	    	mGR.mWorld[i].mWorldCoin = prefs.getInt("mWorldCoin"+i,mGR.mWorld[i].mWorldCoin);
	    	mGR.mWorld[i].isUnLock = prefs.getBoolean("isUnLock"+i,mGR.mWorld[i].isUnLock);
	    	World.VX    = prefs.getFloat("WorldVX",World.VX);
	    	World.Count = prefs.getInt("WorldCount",World.Count);
	    }
	    mGR.mSlowCar.x = prefs.getFloat("mSlowCarX",mGR.mSlowCar.x);
	    mGR.mSlowCar.y = prefs.getFloat("mSlowCarY",mGR.mSlowCar.y);
	    mGR.mSlowCar.ang = prefs.getFloat("mSlowCarang",mGR.mSlowCar.ang);
	    mGR.mSlowCar.mActivetime = prefs.getInt("SlowCartime",mGR.mSlowCar.mActivetime);
	    mGR.mSlowCar.mUnlockVal  = prefs.getInt("SlowCarUnVal",mGR.mSlowCar.mUnlockVal);
	    mGR.mSlowCar.mUpgradeVal = prefs.getInt("SlowCarUpgradeVal",mGR.mSlowCar.mUpgradeVal);
	    mGR.mSlowCar.noBuy = prefs.getInt("SlowCarnoBuy",mGR.mSlowCar.noBuy);
	    mGR.mSlowCar.isActive = prefs.getBoolean("SlowCarisActive",mGR.mSlowCar.isActive);
	    mGR.mSlowCar.isUnlock = prefs.getBoolean("SlowCarisUnlock",mGR.mSlowCar.isUnlock);
	    
	    mGR.mFastCar.x = prefs.getFloat("mFastCarX",mGR.mFastCar.x);
	    mGR.mFastCar.y = prefs.getFloat("mFastCarY",mGR.mFastCar.y);
	    mGR.mFastCar.ang = prefs.getFloat("mFastCarang",mGR.mFastCar.ang);
	    mGR.mFastCar.mActivetime = prefs.getInt("mFastCartime",mGR.mFastCar.mActivetime);
	    mGR.mFastCar.mUnlockVal  = prefs.getInt("mFastCarUnVal",mGR.mFastCar.mUnlockVal);
	    mGR.mFastCar.mUpgradeVal = prefs.getInt("mFastCarUpgradeVal",mGR.mFastCar.mUpgradeVal);
	    mGR.mFastCar.noBuy       = prefs.getInt("FastCarnoBuy",mGR.mFastCar.noBuy);
	    mGR.mFastCar.isActive    = prefs.getBoolean("mFastCarisActive",mGR.mFastCar.isActive);
	    mGR.mFastCar.isUnlock    = prefs.getBoolean("mFastCarisUnlock",mGR.mFastCar.isUnlock);
	    
	    mGR.mBridge.x = prefs.getFloat("mBridgeX",mGR.mBridge.x);
	    mGR.mBridge.y = prefs.getFloat("mBridgeY",mGR.mBridge.y);
	    mGR.mBridge.ang  = prefs.getFloat("mBridgeang",mGR.mBridge.ang);
	    mGR.mBridge.mActivetime =  prefs.getInt("mBridgetime",mGR.mBridge.mActivetime);
	    mGR.mBridge.mUnlockVal  = prefs.getInt("mBridgeUnVal",mGR.mBridge.mUnlockVal);
	    mGR.mBridge.mUpgradeVal = prefs.getInt("mBridgeUpgradeVal",mGR.mBridge.mUpgradeVal);
	    mGR.mBridge.noBuy       = prefs.getInt("mBridgenoBuy",mGR.mBridge.noBuy);
	    mGR.mBridge.isActive    = prefs.getBoolean("mBridgeisActive",mGR.mBridge.isActive);
	    mGR.mBridge.isUnlock   = prefs.getBoolean("mBridgeisUnlock",mGR.mBridge.isUnlock);
	    
	    mGR.mTunnel.x = prefs.getFloat("mTunnelX",mGR.mTunnel.x);
	    mGR.mTunnel.y = prefs.getFloat("mTunnelY",mGR.mTunnel.y);
	    mGR.mTunnel.ang = prefs.getFloat("mTunnelang",mGR.mTunnel.ang);
	    mGR.mTunnel.mActivetime = prefs.getInt("mTunneltime",mGR.mTunnel.mActivetime);
	    mGR.mTunnel.mUnlockVal  = prefs.getInt("mTunnelUnVal",mGR.mTunnel.mUnlockVal);
	    mGR.mTunnel.mUpgradeVal = prefs.getInt("mTunnelUpgradeVal",mGR.mTunnel.mUpgradeVal);
	    mGR.mTunnel.noBuy       = prefs.getInt("mTunnelnoBuy",mGR.mTunnel.noBuy);
	    mGR.mTunnel.isActive    = prefs.getBoolean("mTunnelisActive",mGR.mTunnel.isActive);
	    mGR.mTunnel.isUnlock    = prefs.getBoolean("mTunnelisUnlock",mGR.mTunnel.isUnlock);
	    
	    mGR.mStopCar.x     = prefs.getFloat("mStopCarX",mGR.mStopCar.x);
	    mGR.mStopCar.y     = prefs.getFloat("mStopCarY",mGR.mStopCar.y);
	    mGR.mStopCar.ang   = prefs.getFloat("mStopCarang",mGR.mStopCar.ang);
	    mGR.mStopCar.mActivetime = prefs.getInt("mStopCartime",mGR.mStopCar.mActivetime);
	    mGR.mStopCar.mUnlockVal  = prefs.getInt("mStopCarUnVal",mGR.mStopCar.mUnlockVal);
	    mGR.mStopCar.mUpgradeVal = prefs.getInt("mStopCarUpgradeVal",mGR.mStopCar.mUpgradeVal);
	    mGR.mStopCar.noBuy       = prefs.getInt("mStopCarnoBuy",mGR.mStopCar.noBuy);
	    mGR.mStopCar.isActive    = prefs.getBoolean("mStopCarisActive",mGR.mStopCar.isActive);
	    mGR.mStopCar.isUnlock    = prefs.getBoolean("mStopCarisUnlock",mGR.mStopCar.isUnlock);
	    
	    mGR.mAchieveAni.x = prefs.getFloat("mAchieveAniX", mGR.mAchieveAni.x);
	    mGR.mAchieveAni.y = prefs.getFloat("mAchieveAniY", mGR.mAchieveAni.y);
	    mGR.mAchieveAni.ang = prefs.getFloat("mAchieveAniCnt", mGR.mAchieveAni.ang);
	    mGR.mAchieveAni.vz = prefs.getFloat("mAchieveAniVZ", mGR.mAchieveAni.vz);
	    
	    
	    mGR.mChallenge.x = prefs.getFloat("mChallengeX", mGR.mChallenge.x);
	    mGR.mChallenge.y = prefs.getFloat("mChallengeY", mGR.mChallenge.y);
	    mGR.mChallenge.ang = prefs.getFloat("mChallengeCnt", mGR.mChallenge.ang);
	    mGR.mChallenge.vz = prefs.getFloat("mChallengeVZ", mGR.mChallenge.vz);
	    
	    mGR.mTapEffect.x = prefs.getFloat("mTapEffectX", mGR.mTapEffect.x);
	    mGR.mTapEffect.y = prefs.getFloat("mTapEffectY", mGR.mTapEffect.y);
	    mGR.mTapEffect.z = prefs.getFloat("mTapEffectZ", mGR.mTapEffect.z);
	    
	    mGR.mChallangeSec = prefs.getInt("mChallangetime",mGR.mChallangeSec);
	    for(i=0;i<mGR.isChalleng.length;i++)
	    {
	    	mGR.isChalleng[i] = prefs.getBoolean("isChallange"+i,mGR.isChalleng[i]);
	    }
	    for(i=0;i<mGR.isAchie.length;i++)
	    {
	    	mGR.isAchie[i]    = prefs.getBoolean("isAchieve"+i,mGR.isAchie[i]);
	    }
	    
	}
	void Exit()
	{
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Do you want to Exit?")
	    .setPositiveButton("Rate Us",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
    	   Intent intent = new Intent(Intent.ACTION_VIEW);
    	   intent.setData(Uri.parse(M.LINK+getClass().getPackage().getName()));
    	   startActivity(intent);
      }}).setNeutralButton("No",new DialogInterface.OnClickListener(){   public void onClick(DialogInterface dialog, int which)
    	  {
	  }}).setNegativeButton("Yes",new DialogInterface.OnClickListener(){ public void onClick(DialogInterface dialog, int which)
    	  {
 		    finish();M.GameScreen=M.GAMELOGO;mGR.root.Counter =0;
 		    M.StopSound();
    	  }}).show();
    }
	void unLockWorld()
	{
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Please Unlock the World")
	     .setNeutralButton("OK",new DialogInterface.OnClickListener(){ public void onClick(DialogInterface dialog, int which)
    	  {
     	  }}).show();
    }
	void UseOnePower()
	{
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("You can use only one power at a time either bridge or tunnel")
	     .setNeutralButton("OK",new DialogInterface.OnClickListener(){ public void onClick(DialogInterface dialog, int which)
    	  {
     	  }}).show();
    }
	void UseFarOrSlowPower()
	{
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("You can use only one power at a time either Fast Car or Slow Car")
	     .setNeutralButton("OK",new DialogInterface.OnClickListener(){ public void onClick(DialogInterface dialog, int which)
    	  {
     	  }}).show();
    }
	void After5world()
	{
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("You can Use After 5th World")
	     .setNeutralButton("OK",new DialogInterface.OnClickListener(){ public void onClick(DialogInterface dialog, int which)
    	  {
     	  }}).show();
    }
	void NoCoin()
	{
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Not Enough Coin")
	    .setPositiveButton("Buy Now",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
    	  //Coin Shop
	    	M.GameScreen = M.COINSHOP;
	     }}).setNegativeButton("Later",new DialogInterface.OnClickListener(){ public void onClick(DialogInterface dialog, int which)
    	  {
     	  }}).show();
    }
	void NoKey()
	{
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Do you want key?")
	    .setPositiveButton("Buy Now",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
    	  //Coin Shop
	    	M.GameScreen = M.KEYSHOP;
	     }}).setNegativeButton("Later",new DialogInterface.OnClickListener(){ public void onClick(DialogInterface dialog, int which)
    	  {
     	  }}).show();
    }
	void resetGame()
	{
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Are you sure to reset game?")
	    .setPositiveButton("Yes",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
	    	SharedPreferences prefs = getSharedPreferences("X",MODE_PRIVATE);
			prefs.edit().clear().commit();
			resetvalue();
			resume();
			
      }}).setNegativeButton("No",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
    	  }}).show();
	}
	
  @Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		if(!SingUpadate)
		{
			for(int i=0;i<mGR.isAchie.length;i++)
			{
				if(mGR.isAchie[i])
				  UnlockAchievement(M.Achivemnet[i]);
			}
		}
	    SingUpadate = true;
		
	}
	
	int RC_UNUSED = 5001;
	public void Submitscore(final int ID,final int score) {
//		final int ID =R.string.leaderboard_score;
    	System.out.println("~~~~~~~~~~~~Submitscore~~~~~~~~~~");
    	if(!isSignedIn()){
       	// beginUserInitiatedSignIn();
		   return;
		}
    	else{
		  Games.Leaderboards.submitScore(getApiClient(), getString(ID), score);
		}
	}
	public void onShowLeaderboardsRequested() {
		if (isSignedIn()){
            startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(getApiClient()),RC_UNUSED);
        } else{
//        	beginUserInitiatedSignIn();
        }
	}

	public void UnlockAchievement(final int ID) {
		try {
			if(!isSignedIn()){
//		    	beginUserInitiatedSignIn();
		}
		else
		 {
		   if(isSignedIn()){
				Games.Achievements.unlock(getApiClient(), getString(ID));
			}
		 }
	} catch (Exception e) {}
	}
	public void onShowAchievementsRequested() {
		try {
			 if (isSignedIn()) {
		            startActivityForResult(Games.Achievements.getAchievementsIntent(getApiClient()),RC_UNUSED);
		        } else {
//		        	beginUserInitiatedSignIn();
		        }
		} catch (Exception e) {}
	}


	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			super.onActivityResult(requestCode, resultCode, data);
			System.out.println("[requestCode = " + requestCode + "] [resultCode = " + resultCode + "] [data = " + data + "]");
			if (requestCode == MainActivity.RC_REQUEST) {
				if (!mGR.mMainActivity.mHelper.handleActivityResult(
						requestCode, resultCode, data)) {
					super.onActivityResult(requestCode, resultCode, data);
				} else {
					System.out.println("onActivityResult error = RC_REQUEST");
				}
			}
		} catch (Exception e) {
			System.out.println("onActivityResult error = " + e.toString());
		}
	}

	void loadInterstitial(){
		if (!interstitial.isLoaded() && !addFree) {
			
			System.out.println("Loading Start .................");
			AdRequest adRequest = new AdRequest.Builder()
//			.addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("67701763FB847AEBD3C6EE486475ED94")
			.build();
			interstitial.loadAd(adRequest);
			interstitial.setAdListener(new ToastAdListener(this));
		}
	}

	public void ShowInterstitial() {
		try{handler.sendEmptyMessage(0);}catch(Exception e){}
		System.out.println("ShowInterstitial Start .................");
	}
	Handler handler = new Handler() {public void handleMessage(Message msg) {showInterstitial();}};

	void showInterstitial(){
		System.out.println("show Inter .................");
		try{
			if(interstitial != null)
				if(interstitial.isLoaded()){
					interstitial.show();
					System.out.println("show Start .................");
				}
		}catch (Exception e){
			System.out.println(e.toString());
		}
	}
	
	
	
}