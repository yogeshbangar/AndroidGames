package com.hututu.game.BoomBaseball;

//import com.airpush.android.Airpush;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.google.android.gms.games.achievement.OnAchievementUpdatedListener;
import com.google.android.gms.games.leaderboard.OnScoreSubmittedListener;
import com.google.android.gms.games.leaderboard.SubmitScoreResult;
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
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class Start extends BaseGameActivity  
{
	int _keyCode = 0;
	AdView adView = null;
	AdView adalaod = null;
	AdView adHouse = null;	//AdHouse
	boolean addFree = false;
	boolean SingUpadate = false;
	BroadcastReceiver mReceiver;

	GameRenderer mGR = null;
	private static Context CONTEXT;
	void callAdds()
	{
		if(adView==null){
			adView = new AdView(this, AdSize.BANNER, M.MY_AD_UNIT_ID);
			LinearLayout layout = (LinearLayout)findViewById(R.id.addgame);
			adView.setGravity(Gravity.TOP);
	        layout.addView(adView);
	        adView.loadAd(new AdRequest());
		}
		if(adalaod==null){
	        adalaod = new AdView(this, AdSize.IAB_MRECT, M.HOUSE_ADV_ID);
			LinearLayout layout3 = (LinearLayout)findViewById(R.id.advload);
			adalaod.setGravity(Gravity.TOP);
	        layout3.addView(adalaod);
	        adalaod.loadAd(new AdRequest());
		}
		if(adHouse == null){
		    /*AdHouse*/
		    adHouse = new AdView(this, AdSize.IAB_MRECT, M.HOUSE_ADV_ID);
			LinearLayout layout2 = (LinearLayout)findViewById(R.id.advhouse);
			adHouse.setGravity(Gravity.TOP);
		    layout2.addView(adHouse);
		    adHouse.loadAd(new AdRequest());
		    /*AdHouse*/
		}
	}
	public void onCreate(Bundle savedInstanceState) 
	{
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);//OnlyOneChange
		CONTEXT	=	this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		WindowManager mWinMgr = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
		M.ScreenWidth =mWinMgr.getDefaultDisplay().getWidth();
		M.ScreenHieght=mWinMgr.getDefaultDisplay().getHeight();
		mGR=new GameRenderer(this);
	    VortexView glSurface=(VortexView) findViewById(R.id.vortexview); // use the xml to set the view
	    glSurface.setRenderer(mGR);
	    glSurface.showRenderer(mGR);
	  // Recievier 
	    IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        mReceiver = new ScreenReceiver();
        registerReceiver(mReceiver, filter);
	  // Recievier
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
		if(!addFree)
	    	callAdds();
		//view.onResume();
	}
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		Log.d("----------------=>  "+keyCode,"   -----------    ");
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			switch (M.GameScreen) {
			case M.GAMEPLAY:
				M.GameScreen=M.GAMEPAUSE;
				M.StopSound();
				break;
			case M.GAMELOAD:
				 M.SplashPlay(GameRenderer.mContext,R.drawable.theme);
				 mGR.root.Counter=0;
				 M.GameScreen = M.GAMESPLASH;
				break;
			case M.GAMEMENU:
				Exit();
				M.SplashPlay(GameRenderer.mContext,R.drawable.theme);
				break;
			default:
				M.SplashPlay(GameRenderer.mContext,R.drawable.theme);
				M.GameScreen=M.GAMEMENU;
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
	    editor.putBoolean("isJoin", mGR.isJoin);
	    editor.putBoolean("setBG", M.SetBG);
	    editor.putBoolean("isHit", mGR.isHit);
	    editor.putFloat("px", mGR.px);
	    editor.putFloat("py", mGR.py);
	    editor.putFloat("vx", mGR.vx);
	    editor.putFloat("vy", mGR.vy);
	    editor.putBoolean("isCar", mGR.isCar);
	    editor.putBoolean("isRoof", mGR.isRoof);
	    editor.putBoolean("isWinDow", mGR.isWinDow);
	    editor.putInt("mWindowCnt", mGR.mWindowCnt);
	    editor.putInt("mRoofCnt", mGR.mRoofCnt);
	    editor.putInt("mCarCnt", mGR.mCarCnt);
	    editor.putInt("mTotalBall", mGR.mTotalBall);
	    editor.putInt("mScore", mGR.mScore);
	    
	    editor.putBoolean("isPowerTouch", mGR.isPowerTouch);
	    editor.putBoolean("isAngleTouch", mGR.isAngleTouch);
	    editor.putBoolean("isBonus", mGR.isBonus);
	    editor.putFloat("mBonusX", mGR.mBonusX);
	    editor.putFloat("mBonusY", mGR.mBonusY);
	    editor.putInt("mBonusCount", mGR.mBonusCount);
	    editor.putFloat("PowerCnt", mGR.root.PowerCnt);
	    editor.putFloat("dis", mGR.dis);
	    editor.putFloat("ang", mGR.ang);
	    
	    editor.putFloat("mBowler.x", mGR.mBowler.x);
	    editor.putFloat("mBowler.y", mGR.mBowler.y);
	    editor.putInt("mBowler.Count", mGR.mBowler.Count);
	    editor.putInt("mBowler.mBowlerAni", mGR.mBowler.mBowlerAni);
	    editor.putBoolean("mBowler.isThrough", mGR.mBowler.isThrough);
	    
	    editor.putFloat("mBatsMan.x", mGR.mBatsMan.x);
	    editor.putFloat("mBatsMan.y", mGR.mBatsMan.y);
	    editor.putFloat("mBatsMan.angleCount", mGR.mBatsMan.angleCount);
	    editor.putFloat("mBatsMan.BatAngle", mGR.mBatsMan.BatAngle);
	    editor.putInt("mBatsMan.mBatsManAni", mGR.mBatsMan.mBatsManAni);
	    editor.putBoolean("mBatsMan.isBatmanHit", mGR.mBatsMan.isBatmanHit);
	    
	    editor.putFloat("mBall.x", mGR.mBall.x);
	    editor.putFloat("mBall.y", mGR.mBall.y);
	    editor.putFloat("mBall.vx", mGR.mBall.vx);
	    editor.putFloat("mBall.vy", mGR.mBall.vy);
	    editor.putFloat("mBall.z", mGR.mBall.z);
	    editor.putFloat("mBall.Grav", mGR.mBall.grav);
	    editor.putInt("mBall.TapCount", mGR.mBall.TapCount);
	    editor.putInt("mBall.Reset", mGR.mBall.Reset);
	    editor.putBoolean("mBall.isHome", mGR.mBall.isHome);
	    editor.putBoolean("mBall.isSky", mGR.mBall.isSky);
	    editor.putBoolean("mBall.isTouch", mGR.mBall.isTouch);
	    
	    for(i=0;i<mGR.mTail.length;i++)
	    {
	      editor.putFloat("mTail.x"+i, mGR.mTail[i].x);
	      editor.putFloat("mTail.y"+i, mGR.mTail[i].y);
	      editor.putFloat("mTail.z"+i, mGR.mTail[i].z);
	    }
	    for(i=0;i<mGR.mAni.length;i++)
	    {
	    	editor.putFloat("mAni.x"+i, mGR.mAni[i].x);
	    	editor.putFloat("mAni.y"+i, mGR.mAni[i].y);
	    	editor.putFloat("mAni.z"+i, mGR.mAni[i].z);
	    	editor.putFloat("mAni.vy"+i, mGR.mAni[i].vy);
	    	editor.putFloat("mAni.vx"+i, mGR.mAni[i].vx);
	    }
    	editor.putFloat("mEffect.x", mGR.mEffect.x);
    	editor.putFloat("mEffect.y", mGR.mEffect.y);
    	editor.putInt("mEffect.AniCount", mGR.mEffect.AniCount);
    	editor.putInt("mEffect.No", mGR.mEffect.No);
    	
    	editor.putFloat("mBallon.x", mGR.mBallon.x);
    	editor.putFloat("mBallon.y", mGR.mBallon.y);
    	editor.putInt("mBallon.AniCount", mGR.mBallon.AniCount);
    	editor.putFloat("mBallon.t", mGR.mBallon.t);
    	editor.putBoolean("mBallon.isVisible", mGR.mBallon.isVisible);
    	
    	editor.putFloat("mWindowChar.x", mGR.mWindowChar.x);
    	editor.putFloat("mWindowChar.y", mGR.mWindowChar.y);
    	editor.putFloat("mWindowChar.gayab", mGR.mWindowChar.gayab);
    	editor.putInt("mWindowChar.No", mGR.mWindowChar.No);
    	editor.putInt("mWindowChar.Count", mGR.mWindowChar.Count);
    	
    	editor.putFloat("mTextAni.x", mGR.mTextAni.x);
    	editor.putFloat("mTextAni.y", mGR.mTextAni.y);
    	editor.putFloat("mTextAni.t", mGR.mTextAni.t);
    	editor.putFloat("mTextAni.s", mGR.mTextAni.s);
    	editor.putInt("mTextAni.Ani", mGR.mTextAni.Ani);
    	editor.putInt("mTextAni.No", mGR.mTextAni.No);
    	editor.putInt("mTextAni.StartCnt", mGR.mTextAni.StartCnt);
    	
    	editor.putFloat("mBonusScore.x", 	 mGR.mBonus.x);
    	editor.putFloat("mBonusScore.y", 	 mGR.mBonus.y);
    	editor.putFloat("mBonusScore.t",     mGR.mBonus.t);
    	editor.putFloat("mBonusScore.s", 	 mGR.mBonus.s);
    	editor.putInt("mBonusScore.Ani",     mGR.mBonus.Ani);
    	editor.putInt("mBonusScore.No", 	 mGR.mBonus.No);
    	editor.putInt("mBonusScore.StartCnt",mGR.mBonus.StartCnt);
    	
    	editor.putFloat("mBoom.x", 	 mGR.mBoom.x);
    	editor.putFloat("mBoom.y", 	 mGR.mBoom.y);
    	editor.putFloat("mBoom.t",   mGR.mBoom.t);
    	
    	for(i=0;i<mGR.mWindow.length;i++)
    	{
    	   editor.putFloat("mWindow.x"+i, mGR.mWindow[i].x);
    	   editor.putFloat("mWindow.y"+i, mGR.mWindow[i].y);
    	   editor.putInt("mWindow.No"+i, mGR.mWindow[i].No);
//    	   editor.putBoolean("mWindow.isBreak"+i, mGR.mWindow[i].isBreak);
    	}
    	
    	for(i=0;i<mGR.mCar.length;i++)
	    {
    		editor.putFloat("mCar.x"+i, mGR.mCar[i].x);
    		editor.putFloat("mCar.y"+i, mGR.mCar[i].y);
    		editor.putFloat("mCar.vx"+i, mGR.mCar[i].vx);
    		editor.putInt("mCar.CarNo"+i, mGR.mCar[i].No);
    		
    		editor.putFloat("mCarL.x"+i, mGR.mCarL[i].x);
    		editor.putFloat("mCarL.y"+i, mGR.mCarL[i].y);
    		editor.putFloat("mCarL.vx"+i, mGR.mCarL[i].vx);
    		editor.putInt("mCarL.CarNo"+i, mGR.mCarL[i].No);
	    }
    	editor.putInt("mLevel", mGR.mLevel);
    	editor.putInt("mLevelWindow", mGR.mLevelWindow);
    	editor.putInt("mTargetWindow", mGR.mTargetWindow);
    	editor.putInt("mTargetScore", mGR.mTargetScore);
    	editor.putInt("mGR.mBonusScore", mGR.mBonusScore);
    	editor.putInt("mGR.mExtraScore", mGR.mExtraScore);
    	editor.putInt("mPrevScore", mGR.mPrevScore);
    	editor.putInt("mNewScore", mGR.mNewScore);
    	for(i=0;i<mGR.mSboardAni.length;i++)
    	{
    		editor.putInt("FinalScore"+i, mGR.mSboardAni[i].FinalScore);
    		editor.putInt("Score"+i, mGR.mSboardAni[i].Score);
    		editor.putInt("No"+i, mGR.mSboardAni[i].No);
    		editor.putFloat("s"+i, mGR.mSboardAni[i].s);
    	}
    	editor.putInt("NoCount", mGR.NoCount);
    	for(i=0;i<M.isAchive.length;i++)
    	  editor.putBoolean("isAchiv"+i, M.isAchive[i]);
    	editor.putInt("mAchCarCnt"  , mGR.mAchCarCnt); 
    	editor.putInt("mAchDoorCnt" , mGR.mAchDoorCnt);
    	editor.putInt("mAchRoof"	, mGR.mAchRoof);
    	editor.putInt("mAchCntShot" , mGR.mAchCntShot);
    	editor.putInt("mAchTotalShot" , mGR.mAchTotalShot);
    	editor.putInt("mAchTotalWindow" , mGR.mAchTotalWindow);
    	editor.putInt("mAchStrike" , mGR.mAchStrike);
    	editor.putInt("mAchBallon" , mGR.mAchBallon);
	    editor.commit();
	}
	void resume()
	{
		int i=0;
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		addFree 			= prefs.getBoolean("addFree", addFree);
		SingUpadate = prefs.getBoolean("SingUpadate", SingUpadate);
		M.GameScreen 	  = prefs.getInt("screen", M.GAMELOGO);
		M.setValue 	 	  = prefs.getBoolean("setValue", M.setValue);
		mGR.isJoin        = prefs.getBoolean("isJoin", mGR.isJoin);
		M.SetBG      	  = prefs.getBoolean("setBG", M.SetBG);
		mGR.isHit         = prefs.getBoolean("isHit", mGR.isHit);
		mGR.px 			  = prefs.getFloat("px", mGR.px);
		mGR.py 			  = prefs.getFloat("py", mGR.py);
		mGR.vx 			  = prefs.getFloat("vx", mGR.vx);
		mGR.vy			  = prefs.getFloat("vy", mGR.vy);
		mGR.isCar 		  = prefs.getBoolean("isCar", mGR.isCar);
		mGR.isRoof 		  = prefs.getBoolean("isRoof", mGR.isRoof);
		mGR.isWinDow 	  = prefs.getBoolean("isWinDow", mGR.isWinDow);
		mGR.mWindowCnt 	  = prefs.getInt("mWindowCnt", mGR.mWindowCnt);
		mGR.mRoofCnt	  = prefs.getInt("mRoofCnt", mGR.mRoofCnt);
		mGR.mCarCnt       = prefs.getInt("mCarCnt", mGR.mCarCnt);
		mGR.mTotalBall    = prefs.getInt("mTotalBall", mGR.mTotalBall);
		mGR.mScore 		  = prefs.getInt("mScore", mGR.mScore);
	    
		mGR.isPowerTouch  = prefs.getBoolean("isPowerTouch", mGR.isPowerTouch);
		mGR.isAngleTouch  = prefs.getBoolean("isAngleTouch", mGR.isAngleTouch);
		mGR.isBonus 	  = prefs.getBoolean("isBonus", mGR.isBonus);
		mGR.mBonusX 	  = prefs.getFloat("mBonusX", mGR.mBonusX);
		mGR.mBonusY 	  = prefs.getFloat("mBonusY", mGR.mBonusY);
		mGR.mBonusCount   = prefs.getInt("mBonusCount", mGR.mBonusCount);
		mGR.root.PowerCnt = prefs.getFloat("PowerCnt", mGR.root.PowerCnt);
		mGR.dis 		  = prefs.getFloat("dis", mGR.dis);
		mGR.ang 		  = prefs.getFloat("ang", mGR.ang);
	    
		mGR.mBowler.x 		   = prefs.getFloat("mBowler.x", mGR.mBowler.x);
		mGR.mBowler.y 		   = prefs.getFloat("mBowler.y", mGR.mBowler.y);
		mGR.mBowler.Count 	   = prefs.getInt("mBowler.Count", mGR.mBowler.Count);
		mGR.mBowler.mBowlerAni = prefs.getInt("mBowler.mBowlerAni", mGR.mBowler.mBowlerAni);
		mGR.mBowler.isThrough  = prefs.getBoolean("mBowler.isThrough", mGR.mBowler.isThrough);
	    
		mGR.mBatsMan.x			 = prefs.getFloat("mBatsMan.x", mGR.mBatsMan.x);
		mGR.mBatsMan.y 			 = prefs.getFloat("mBatsMan.y", mGR.mBatsMan.y);
		mGR.mBatsMan.angleCount  = prefs.getFloat("mBatsMan.angleCount", mGR.mBatsMan.angleCount);
		mGR.mBatsMan.BatAngle 	 = prefs.getFloat("mBatsMan.BatAngle", mGR.mBatsMan.BatAngle);
		mGR.mBatsMan.mBatsManAni = prefs.getInt("mBatsMan.mBatsManAni", mGR.mBatsMan.mBatsManAni);
		mGR.mBatsMan.isBatmanHit = prefs.getBoolean("mBatsMan.isBatmanHit", mGR.mBatsMan.isBatmanHit);
	    
		mGR.mBall.x 		= prefs.getFloat("mBall.x", mGR.mBall.x);
		mGR.mBall.y 		= prefs.getFloat("mBall.y", mGR.mBall.y);
		mGR.mBall.vx 		= prefs.getFloat("mBall.vx", mGR.mBall.vx);
		mGR.mBall.vy 		= prefs.getFloat("mBall.vy", mGR.mBall.vy);
		mGR.mBall.z 		= prefs.getFloat("mBall.z", mGR.mBall.z);
		mGR.mBall.grav 		= prefs.getFloat("mBall.Grav", mGR.mBall.grav);
		mGR.mBall.TapCount  = prefs.getInt("mBall.TapCount", mGR.mBall.TapCount);
		mGR.mBall.Reset 	= prefs.getInt("mBall.Reset", mGR.mBall.Reset);
		mGR.mBall.isHome 	= prefs.getBoolean("mBall.isHome", mGR.mBall.isHome);
		mGR.mBall.isSky 	= prefs.getBoolean("mBall.isSky", mGR.mBall.isSky);
		mGR.mBall.isTouch   = prefs.getBoolean("mBall.isTouch", mGR.mBall.isTouch);
	    
	    for(i=0;i<mGR.mTail.length;i++)
	    {
	      mGR.mTail[i].x  = prefs.getFloat("mTail.x"+i, mGR.mTail[i].x);
	      mGR.mTail[i].y  = prefs.getFloat("mTail.y"+i, mGR.mTail[i].y);
	      mGR.mTail[i].z  = prefs.getFloat("mTail.z"+i, mGR.mTail[i].z);
	    }
	    for(i=0;i<mGR.mAni.length;i++)
	    {
	    	mGR.mAni[i].x  = prefs.getFloat("mAni.x"+i, mGR.mAni[i].x);
	    	mGR.mAni[i].y  = prefs.getFloat("mAni.y"+i, mGR.mAni[i].y);
	    	mGR.mAni[i].z  = prefs.getFloat("mAni.z"+i, mGR.mAni[i].z);
	    	mGR.mAni[i].vy = prefs.getFloat("mAni.vy"+i, mGR.mAni[i].vy);
	    	mGR.mAni[i].vx = prefs.getFloat("mAni.vx"+i, mGR.mAni[i].vx);
	    }
	    mGR.mEffect.x 		 = prefs.getFloat("mEffect.x", mGR.mEffect.x);
	    mGR.mEffect.y 		 = prefs.getFloat("mEffect.y", mGR.mEffect.y);
	    mGR.mEffect.AniCount = prefs.getInt("mEffect.AniCount", mGR.mEffect.AniCount);
	    mGR.mEffect.No 		 = prefs.getInt("mEffect.No", mGR.mEffect.No);
    	
	    mGR.mBallon.x 		  = prefs.getFloat("mBallon.x", mGR.mBallon.x);
	    mGR.mBallon.y 		  = prefs.getFloat("mBallon.y", mGR.mBallon.y);
	    mGR.mBallon.AniCount  = prefs.getInt("mBallon.AniCount", mGR.mBallon.AniCount);
	    mGR.mBallon.t 		  = prefs.getFloat("mBallon.t", mGR.mBallon.t);
	    mGR.mBallon.isVisible = prefs.getBoolean("mBallon.isVisible", mGR.mBallon.isVisible);
    	
	    mGR.mWindowChar.x 	  = prefs.getFloat("mWindowChar.x", mGR.mWindowChar.x);
	    mGR.mWindowChar.y 	  = prefs.getFloat("mWindowChar.y", mGR.mWindowChar.y);
	    mGR.mWindowChar.gayab = prefs.getFloat("mWindowChar.gayab", mGR.mWindowChar.gayab);
	    mGR.mWindowChar.No 	  = prefs.getInt("mWindowChar.No", mGR.mWindowChar.No);
	    mGR.mWindowChar.Count = prefs.getInt("mWindowChar.Count", mGR.mWindowChar.Count);
    	
	    mGR.mTextAni.x		  = prefs.getFloat("mTextAni.x", mGR.mTextAni.x);
	    mGR.mTextAni.y 		  = prefs.getFloat("mTextAni.y", mGR.mTextAni.y);
	    mGR.mTextAni.t		  = prefs.getFloat("mTextAni.t", mGR.mTextAni.t);
	    mGR.mTextAni.s 		  = prefs.getFloat("mTextAni.s", mGR.mTextAni.s);
	    mGR.mTextAni.Ani 	  = prefs.getInt("mTextAni.Ani", mGR.mTextAni.Ani);
	    mGR.mTextAni.No 	  = prefs.getInt("mTextAni.No", mGR.mTextAni.No);
	    mGR.mTextAni.StartCnt = prefs.getInt("mTextAni.StartCnt", mGR.mTextAni.StartCnt);
    	
	    mGR.mBonus.x 		 = prefs.getFloat("mBonusScore.x", 	 mGR.mBonus.x);
	    mGR.mBonus.y 		 = prefs.getFloat("mBonusScore.y", 	 mGR.mBonus.y);
	    mGR.mBonus.t 		 = prefs.getFloat("mBonusScore.t",   mGR.mBonus.t);
	    mGR.mBonus.s 		 = prefs.getFloat("mBonusScore.s", 	 mGR.mBonus.s);
	    mGR.mBonus.Ani 		 = prefs.getInt("mBonusScore.Ani",   mGR.mBonus.Ani);
	    mGR.mBonus.No  	 	 = prefs.getInt("mBonusScore.No" , 	 mGR.mBonus.No);
	    mGR.mBonus.StartCnt  = prefs.getInt("mBonusScore.StartCnt",mGR.mBonus.StartCnt);
    	
	    mGR.mBoom.x = prefs.getFloat("mBoom.x", 	 mGR.mBoom.x);
	    mGR.mBoom.y = prefs.getFloat("mBoom.y", 	 mGR.mBoom.y);
	    mGR.mBoom.t = prefs.getFloat("mBoom.t",   mGR.mBoom.t);
    	
    	for(i=0;i<mGR.mWindow.length;i++)
    	{
    		mGR.mWindow[i].x 	  = prefs.getFloat("mWindow.x"+i, mGR.mWindow[i].x);
    		mGR.mWindow[i].y 	  = prefs.getFloat("mWindow.y"+i, mGR.mWindow[i].y);
    		mGR.mWindow[i].No 	  = prefs.getInt("mWindow.No"+i, mGR.mWindow[i].No);
//    		mGR.mWindow[i].isBreak= prefs.getBoolean("mWindow.isBreak"+i, mGR.mWindow[i].isBreak);
    	}
    	
    	for(i=0;i<mGR.mCar.length;i++)
	    {
    		mGR.mCar[i].x 	    = prefs.getFloat("mCar.x"+i, mGR.mCar[i].x);
    		mGR.mCar[i].y 	    = prefs.getFloat("mCar.y"+i, mGR.mCar[i].y);
    		mGR.mCar[i].vx 	    = prefs.getFloat("mCar.vx"+i, mGR.mCar[i].vx);
    		mGR.mCar[i].No   	= prefs.getInt("mCar.CarNo"+i, mGR.mCar[i].No);
    		
    		mGR.mCarL[i].x 		= prefs.getFloat("mCarL.x"+i, mGR.mCarL[i].x);
    		mGR.mCarL[i].y 		= prefs.getFloat("mCarL.y"+i, mGR.mCarL[i].y);
    		mGR.mCarL[i].vx 	= prefs.getFloat("mCarL.vx"+i, mGR.mCarL[i].vx);
    		mGR.mCarL[i].No  	= prefs.getInt("mCarL.CarNo"+i, mGR.mCarL[i].No);
	    }
    	mGR.mLevel		  = prefs.getInt("mLevel", mGR.mLevel);
    	mGR.mLevelWindow  = prefs.getInt("mLevelWindow", mGR.mLevelWindow);
    	mGR.mTargetWindow = prefs.getInt("mTargetWindow", mGR.mTargetWindow);
    	mGR.mTargetScore  = prefs.getInt("mTargetScore", mGR.mTargetScore);
    	mGR.mBonusScore   = prefs.getInt("mGR.mBonusScore", mGR.mBonusScore);
    	mGR.mExtraScore   = prefs.getInt("mGR.mExtraScore", mGR.mExtraScore);
    	mGR.mPrevScore    = prefs.getInt("mPrevScore", mGR.mPrevScore);
    	mGR.mNewScore     = prefs.getInt("mNewScore", mGR.mNewScore);
    	for(i=0;i<mGR.mSboardAni.length;i++)
    	{
    		mGR.mSboardAni[i].FinalScore = prefs.getInt("FinalScore"+i, mGR.mSboardAni[i].FinalScore);
    		mGR.mSboardAni[i].Score      = prefs.getInt("Score"+i, mGR.mSboardAni[i].Score);
    		mGR.mSboardAni[i].No         = prefs.getInt("No"+i, mGR.mSboardAni[i].No);
    		mGR.mSboardAni[i].s          = prefs.getFloat("s"+i, mGR.mSboardAni[i].s);
    	}
    	mGR.NoCount  = prefs.getInt("NoCount", mGR.NoCount);
    	for(i=0;i<M.isAchive.length;i++)
         M.isAchive[i] = prefs.getBoolean("isAchiv"+i, M.isAchive[i]);
    	
    	mGR.mAchCarCnt      =  prefs.getInt("mAchCarCnt"  , mGR.mAchCarCnt); 
    	mGR.mAchDoorCnt     =  prefs.getInt("mAchDoorCnt" , mGR.mAchDoorCnt);
    	mGR.mAchRoof        =  prefs.getInt("mAchRoof"	, mGR.mAchRoof);
    	mGR.mAchCntShot     =  prefs.getInt("mAchCntShot" , mGR.mAchCntShot);
    	mGR.mAchTotalShot   =  prefs.getInt("mAchTotalShot" , mGR.mAchTotalShot);
    	mGR.mAchTotalWindow =  prefs.getInt("mAchTotalWindow" , mGR.mAchTotalWindow);
    	mGR.mAchStrike      =  prefs.getInt("mAchStrike" , mGR.mAchStrike);
    	mGR.mAchBallon      =  prefs.getInt("mAchBallon" , mGR.mAchBallon);
    	
	}
	void ShowAchiveMsg(int iconid,int msgid)
	{
		new AlertDialog.Builder(this).setIcon(M.Icon[iconid]).setTitle(M.Msg[msgid])
	    .setPositiveButton("Ok",new DialogInterface.OnClickListener()
	    {
	      public void onClick(DialogInterface dialog, int which)
	      {
    	   Intent intent = new Intent(Intent.ACTION_VIEW);
          }
	     }
	   ).show();
	}
	void Exit()
	{
		M.StopSound();
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Do you want to Exit?")
	    .setPositiveButton("Rate Us",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
    	   Intent intent = new Intent(Intent.ACTION_VIEW);
    	   intent.setData(Uri.parse(M.LINK+getClass().getPackage().getName()));
    	   startActivity(intent);
      }}).setNeutralButton("No",new DialogInterface.OnClickListener()
      {
    	  public void onClick(DialogInterface dialog, int which)
    	  {
    		  if(M.GameScreen == M.GAMEMENU)
    		  {
    			  M.SplashPlay(GameRenderer.mContext,R.drawable.theme);
    		  }
      }}).setNegativeButton("Yes",new DialogInterface.OnClickListener(){public void onClick(DialogInterface dialog, int which)
      {
 		   finish();M.GameScreen=M.GAMELOGO;
 		   mGR.root.Counter =0;
 		   M.StopSound();
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
			for(int i=0;i<M.isAchive.length;i++)
			{
				if(M.isAchive[i])
				  UnlockAchievement(M.Achivemnet[i]);
			}
		 }
		  SingUpadate = true;
   	  }
	
	public void Submitscore(final int ID, int score) {
		try {
			if (!isSignedIn()) {// beginUserInitiatedSignIn();
				return;
			} else {
				getGamesClient().submitScoreImmediate(new OnScoreSubmittedListener() {
					@Override
					public void onScoreSubmitted(int arg0,SubmitScoreResult arg1) {}}, getString(ID), score);
			}
		} catch (Exception e) {}
	}

	
	int RC_UNUSED = 5001;

	// @Override
	public void onShowLeaderboardsRequested() {
		try {
		if (isSignedIn()) {
			startActivityForResult(getGamesClient().getAllLeaderboardsIntent(),RC_UNUSED);
		} else {
//			beginUserInitiatedSignIn();
			// showAlert(getString(R.string.leaderboard_score));
		}
		} catch (Exception e) {}
	}
	public void UnlockAchievement(final int ID) {
		try {
			if (!isSignedIn()) {
//			beginUserInitiatedSignIn();
		} else {
			if ((!isSignedIn()))
				return;
			getGamesClient().unlockAchievementImmediate(new OnAchievementUpdatedListener() {
						@Override
						public void onAchievementUpdated(int statusCode,String arg1) {
							System.out.println(statusCode+" ~~~~UnlockAchievement~~~~ "+arg1);
						}
					}, getString(ID));

		}
	} catch (Exception e) {}
	}
	public void onShowAchievementsRequested() {
		try {
			if (isSignedIn()) {
				startActivityForResult(getGamesClient().getAchievementsIntent(),RC_UNUSED);
			} else {
//				beginUserInitiatedSignIn();
			}
		} catch (Exception e) {}
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~"+ "onActivityResult(" + requestCode + "," + resultCode + "," + data);
        if (!mGR.mMainActivity.mHelper.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
        else {
        	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~"+ "onActivityResult handled by IABUtil.");
        }
        
    }

}