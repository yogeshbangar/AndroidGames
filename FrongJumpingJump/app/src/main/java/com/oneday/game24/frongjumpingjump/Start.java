package com.oneday.game24.frongjumpingjump;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class Start extends BaseGameActivity
{
	int _keyCode = 0;
	boolean addFree = false; 
	boolean SingUpdate = false;
	BroadcastReceiver mReceiver;
	GameRenderer mGR = null;
	Vibrator mVibrate;
	private static Context CONTEXT;
	GameAd mGameAd = new GameAd();
	@SuppressWarnings("deprecation")
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
		mGameAd.initAds(this);
	    
//	    //Recievier 
	    IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        mReceiver = new ScreenReceiver();
        registerReceiver(mReceiver, filter);
//	   //Recievier
        
        mVibrate = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);//Vibration
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
				mGR.mGameTime -=  System.currentTimeMillis();
				M.GameScreen   =  M.GAMEPAUSE;
				mGR.mAni       = .01f;
				mGR.mMenuAni   = .005f;
				mGR.root.setBubble();
				M.StopSound();
				break;
			  case M.GAMEADD:
			  case M.GAMELOADING:
				break;
			  case M.GAMEOVER:
			  case M.GAMEPAUSE:
			  case M.GAMEMODE:
				  M.GameScreen = M.GAMEWORLD;
				  mGR.root.setBubble2();
				  break;
			  case M.GAMEABTUS:
			  case M.GAMEHELP:
				  mGR.mAni     =.01f;
				  mGR.mMenuAni =.005f;
				  M.GameScreen = M.GAMESETTING;
				  mGR.root.setBubble();
				  break;
			default:
				mGR.mAni     =.01f;
				mGR.mMenuAni =.005f;
				M.GameScreen=M.GAMEMENU;
				mGR.root.setBubble();
				break;
			case M.GAMEMENU:
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
		if(mVibrate != null)
        {
		   mVibrate.cancel();
           mVibrate= null;
        }
		super.onDestroy();
	}

	void pause()
	{
		int i=0;
		if(M.GameScreen == M.GAMEPLAY)
		{
			 M.GameScreen   = M.GAMEPAUSE;
			 mGR.mGameTime -=System.currentTimeMillis();
		}
		mGR.resumeCounter = 0;
		M.StopSound();
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
	    Editor editor = prefs.edit();
	    
	    editor.putBoolean("addFree", addFree);
	    editor.putBoolean("SingUpadate", SingUpdate);
	    editor.putInt("screen", M.GameScreen);
	    editor.putBoolean("setValue", M.setValue);
	    editor.putBoolean("SetBG", M.SetBG);
	    editor.putBoolean("Vibrate", M.Vibrate);
	    editor.putLong("GameTime", mGR.mGameTime);
	    editor.putInt("mGameMode", mGR.mGameMode);
	    editor.putInt("mGameType", mGR.mGameType);
	    editor.putInt("mScore", mGR.mScore);
	    editor.putInt("mTargetTile", mGR.mTargetTile);
	    editor.putInt("mTileCnt", mGR.mTileCnt);
	    editor.putInt("WinCnt"  , mGR.mWinCnt);
	    editor.putFloat("mAni",mGR.mAni);
	    editor.putFloat("mMenuAni",mGR.mMenuAni);
	    editor.putFloat("mJoinAnim",mGR.mJoinAnim);
	    editor.putFloat("BG1",mGR.BG1);
	    editor.putFloat("BG2",mGR.BG2);
	    editor.putFloat("move",mGR.move);
	    editor.putFloat("plrx",mGR.plyX);
	    editor.putFloat("plry",mGR.plyY);
	    editor.putFloat("plrvy",mGR.plyVy);
	    editor.putFloat("mTargetTime",mGR.mTargetTime);
	    editor.putFloat("mTimeCnt",mGR.mTimeCnt);
	    for(i=0;i<mGR.mBestScore.length;i++)
	    {
	    	for(int j=0;j<mGR.mBestScore[i].length;j++)
	    	{
	    		editor.putFloat(i+"BestScore"+j, mGR.mBestScore[i][j]);
	    	}
	    }
	    editor.putInt("ST",mGR.st);
	    editor.putInt("Space",mGR.Space);
	    editor.putInt("jumpCount",mGR.jumpCount);
	    editor.putInt("mCharAni",mGR.mCharAni);
	    editor.putInt("wCount",mGR.wCount);
	    editor.putBoolean("isStart",mGR.isStart);
	    editor.putBoolean("isJoin",mGR.isJoin);
	    for(i=0;i<mGR.mStep.length;i++)
	    {
	    	editor.putFloat("mStepx"+i, mGR.mStep[i].x);
	    	editor.putBoolean("mStepOn"+i, mGR.mStep[i].isOn);
	    	editor.putBoolean("mStepWatch"+i, mGR.mStep[i].Watch);
	    	editor.putInt("mStepno"+i, mGR.mStep[i].no);
	    }
	    for(i=0; i<mGR.mBubble.length; i++)
	    {
	    	editor.putFloat("mbubbleX"+i, mGR.mBubble[i].x);
	    	editor.putBoolean("mbubbleisShow"+i, mGR.mBubble[i].isShow);
	    	editor.putFloat("mbubbleY"+i, mGR.mBubble[i].y);
	    	editor.putFloat("mbubbleZ"+i, mGR.mBubble[i].z);
	    	editor.putFloat("mbubbleVz"+i, mGR.mBubble[i].vz);
	    	editor.putFloat("mbubbleTrans"+i, mGR.mBubble[i].trans);
	    }
	    editor.commit();
	    
	}
	void resume()
	{
		int i=0;
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		addFree 				= prefs.getBoolean("addFree", addFree);
		SingUpdate = prefs.getBoolean("SingUpadate", SingUpdate);
		M.GameScreen 	    	= prefs.getInt("screen", M.GameScreen);
		M.setValue 	 	    	= prefs.getBoolean("setValue", M.setValue);
		M.SetBG                 = prefs.getBoolean("SetBG", M.SetBG);
		M.Vibrate               = prefs.getBoolean("Vibrate", M.Vibrate);
		mGR.mGameTime 			= prefs.getLong("GameTime", mGR.mGameTime);
		mGR.mGameMode 			= prefs.getInt("mGameMode", mGR.mGameMode);
		mGR.mGameType 			= prefs.getInt("mGameType", mGR.mGameType);
		mGR.mScore 				= prefs.getInt("mScore", mGR.mScore);
		mGR.mTargetTile 		= prefs.getInt("mTargetTile", mGR.mTargetTile);
		mGR.mTileCnt 			= prefs.getInt("mTileCnt", mGR.mTileCnt);
		mGR.mWinCnt 			= prefs.getInt("WinCnt"  , mGR.mWinCnt);
		mGR.mAni 				= prefs.getFloat("mAni",mGR.mAni);
		mGR.mMenuAni 			= prefs.getFloat("mMenuAni",mGR.mMenuAni);
		mGR.mJoinAnim 			= prefs.getFloat("mJoinAnim",mGR.mJoinAnim);
		mGR.BG1 				= prefs.getFloat("BG1",mGR.BG1);
		mGR.BG2 				= prefs.getFloat("BG2",mGR.BG2);
		mGR.move 				= prefs.getFloat("move",mGR.move);
		mGR.plyX = prefs.getFloat("plrx",mGR.plyX);
		mGR.plyY = prefs.getFloat("plry",mGR.plyY);
		mGR.plyVy = prefs.getFloat("plrvy",mGR.plyVy);
		mGR.mTargetTime 		= prefs.getFloat("mTargetTime",mGR.mTargetTime);
		mGR.mTimeCnt 			= prefs.getFloat("mTimeCnt",mGR.mTimeCnt);
	    for(i=0;i<mGR.mBestScore.length;i++)
	    {
	    	for(int j=0;j<mGR.mBestScore[i].length;j++)
	    	{
	    		mGR.mBestScore[i][j] = prefs.getFloat(i+"BestScore"+j, mGR.mBestScore[i][j]);
	    	}
	    }
	    mGR.st    =  (byte)prefs.getInt("ST",mGR.st);
	    mGR.Space =  (byte)prefs.getInt("Space",mGR.Space);
	    mGR.jumpCount = (byte)prefs.getInt("jumpCount",mGR.jumpCount);
	    mGR.mCharAni  = (byte)prefs.getInt("mCharAni",mGR.mCharAni);
	    mGR.wCount    = (byte)prefs.getInt("wCount",mGR.wCount);
	    mGR.isStart   = prefs.getBoolean("isStart",mGR.isStart);
	    mGR.isJoin    = prefs.getBoolean("isJoin",mGR.isJoin);
	    for(i=0;i<mGR.mStep.length;i++)
	    {
	    	mGR.mStep[i].x 	   = prefs.getFloat("mStepx"+i, mGR.mStep[i].x);
	    	mGR.mStep[i].isOn  = prefs.getBoolean("mStepOn"+i, mGR.mStep[i].isOn);
	    	mGR.mStep[i].Watch = prefs.getBoolean("mStepWatch"+i, mGR.mStep[i].Watch);
	    	mGR.mStep[i].no    = prefs.getInt("mStepno"+i, mGR.mStep[i].no);
	    }
	    for(i=0; i<mGR.mBubble.length; i++)
	    {
	    	mGR.mBubble[i].x 	  = prefs.getFloat("mbubbleX"+i, mGR.mBubble[i].x);
	    	mGR.mBubble[i].isShow = prefs.getBoolean("mbubbleisShow"+i, mGR.mBubble[i].isShow);
	    	mGR.mBubble[i].y      = prefs.getFloat("mbubbleY"+i, mGR.mBubble[i].y);
	    	mGR.mBubble[i].z      = prefs.getFloat("mbubbleZ"+i, mGR.mBubble[i].z);
	    	mGR.mBubble[i].vz     = prefs.getFloat("mbubbleVz"+i, mGR.mBubble[i].vz);
	    	mGR.mBubble[i].trans = prefs.getFloat("mbubbleTrans"+i, mGR.mBubble[i].trans);
	    }
	    if(M.GameScreen == M.GAMEMENU)
	    	M.SplashPlay(GameRenderer.mContext,R.raw.menu_theme);
	 	
	}
	void Exit()
	{
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Do you want to Exit?")
	    .setNeutralButton("No",new DialogInterface.OnClickListener(){   public void onClick(DialogInterface dialog, int which)
    	  {
	  }}).setNegativeButton("Yes",new DialogInterface.OnClickListener(){ public void onClick(DialogInterface dialog, int which)
    	  {
 		    finish();M.GameScreen=M.GAMELOGO;mGR.root.Counter =0;
 		    M.StopSound();
    	  }}).show();
    }
	public void loadInter() {
		try{handlerInter.sendEmptyMessage(0);}catch(Exception e){}
	}
	Handler handlerInter = new Handler() {public void handleMessage(Message msg) {load();}};
	private void load(){
		mGameAd.loadInterstitialAd();
	}
	void showInterstitial(){
		mGameAd.showInterstitialAd();

	}
	
	
	
}