package com.hututu.game.bubblecandyunlimited;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.games.Games;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
	AdView adView = null;
	GameRenderer mGR = null;
	private static Context CONTEXT;
	private InterstitialAd interstitial;
	boolean OnCreat =false; 
	void callAdds()
	{
		//AdSize.MEDIUM_RECTANGLE
		adView = (AdView) this.findViewById(R.id.addgame);
		AdRequest adRequest = new AdRequest.Builder()
//		.addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("67701763FB847AEBD3C6EE486475ED94")
		.build();
		adView.loadAd(adRequest);
		adView.setAdListener(new AdListener() {
			public void onAdLoaded() {
				adView.bringToFront();
			}
		});
	}
	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) 
	{
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);//OnlyOneChange
		CONTEXT	=	this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		interstitial = new InterstitialAd(this);
		interstitial.setAdUnitId(getString(R.string.INTERSTITIAL_ID));
	    WindowManager mWinMgr = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
		M.ScreenWidth =mWinMgr.getDefaultDisplay().getWidth();
		M.ScreenHieght=mWinMgr.getDefaultDisplay().getHeight();
		mGR=new GameRenderer(this);
	    VortexView glSurface=(VortexView) findViewById(R.id.vortexview); // use the xml to set the view
	    glSurface.setRenderer(mGR);
	    glSurface.showRenderer(mGR);
	    callAdds();
	    OnCreat =true;
	    load();
	}
	public static Context getContext() {
	        return CONTEXT;
	}
	@Override 
	public void onPause () {
		M.stop();
		pause();
		super.onPause();
	}
	@Override 
	public void onResume() {                                                                                                       
		resume();
		super.onResume();
		//view.onResume();
	}
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		Log.d("----------------=>  "+keyCode,"   -----------    ");
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			switch (M.GameScreen) {
			case M.GAMEPLAY:
				M.mpstop();
				M.GameScreen = M.GAMEPAUSE;
				break;
			case M.GAMEMENU:
				Exit();
				break;
			default:
				M.GameScreen = M.GAMEMENU;
				M.play(R.drawable.splash_other);
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
	public void onDestroy()
	{
		super.onDestroy();
	}
	void pause()
	{
		M.stop();
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
	    Editor editor = prefs.edit();
	    editor.putInt("screen", M.GameScreen);
	    editor.putBoolean("setValue", M.setValue);
	    editor.putBoolean("addFree", mGR.addFree);
	    editor.putBoolean("setMusic", M.setMusic);
	    
	    editor.putBoolean("SingUpadate", mGR.SingUpadate);
	    for(int i =0;i<mGR.mAchiUnlock.length;i++)
	    	editor.putBoolean("mAchiUnlock"+i, mGR.mAchiUnlock[i]);
	    editor.putBoolean("playBest", mGR.playBest);
	    editor.putBoolean("IsEndless", mGR.IsEndless);
	    
	    editor.putInt("mHScore", mGR.mHScore);
	    editor.putInt("mTScore", mGR.mTScore);
	    editor.putInt("hitBall", mGR.hitBall);
	    editor.putInt("newHit", mGR.newHit);
	    editor.putInt("mScore", mGR.mScore);
	    editor.putInt("win", mGR.win);

	    
	    editor.putBoolean("Playermo", mGR.mPlayer.move);
	    
	    editor.putInt("Playerball", mGR.mPlayer.ball);
	    editor.putInt("PlayerfireColor", mGR.mPlayer.fireColor);
	    editor.putInt("PlayerResColor", mGR.mPlayer.ResColor);
	    editor.putInt("Playeranim", mGR.mPlayer.anim);
	    
	    editor.putFloat("Playerx", mGR.mPlayer.x);
	    editor.putFloat("Playery", mGR.mPlayer.y);
	    editor.putFloat("Playervx", mGR.mPlayer.vx);
	    editor.putFloat("Playervy", mGR.mPlayer.vy);
	    editor.putFloat("Playerang", mGR.mPlayer.ang);
	    
		for (int i = 0; i < mGR.mCBubble.length; i++) {
			for (int j = 0; j < mGR.mCBubble[i].length; j++) {
				editor.putBoolean(i+"CBubblevi"+j, mGR.mCBubble[i][j].visited);
				editor.putBoolean(i+"CBubbleche"+j, mGR.mCBubble[i][j].checkFall);
				editor.putInt(i+"CBubblecandy"+j, mGR.mCBubble[i][j].candy);
				editor.putInt(i+"CBubbleCBubble"+j, mGR.mCBubble[i][j].CBubble);
				editor.putInt(i+"CBubbleanim"+j, mGR.mCBubble[i][j].anim);
				editor.putInt(i+"CBubble_i"+j, mGR.mCBubble[i][j].i);
				editor.putInt(i+"CBubble_j"+j, mGR.mCBubble[i][j].j);
				
			}
		}
	    
	    editor.commit();
	}
	void resume()
	{
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = prefs.getInt("screen", M.GAMELOGO);
		M.setValue = prefs.getBoolean("setValue", M.setValue);
		mGR.addFree = prefs.getBoolean("addFree", mGR.addFree);
		
		M.setMusic = prefs.getBoolean("setMusic", M.setMusic);
	    
		mGR.SingUpadate = prefs.getBoolean("SingUpadate", mGR.SingUpadate);
	    for(int i =0;i<mGR.mAchiUnlock.length;i++)
	    	mGR.mAchiUnlock[i] = prefs.getBoolean("mAchiUnlock"+i, mGR.mAchiUnlock[i]);
	    mGR.playBest = prefs.getBoolean("playBest", mGR.playBest);
	    mGR.IsEndless = prefs.getBoolean("IsEndless", mGR.IsEndless);

	    
	    mGR.mHScore = prefs.getInt("mHScore", mGR.mHScore);
	    mGR.mTScore = prefs.getInt("mTScore", mGR.mTScore);
	    mGR.hitBall = prefs.getInt("hitBall", mGR.hitBall);
	    mGR.newHit = prefs.getInt("newHit", mGR.newHit);
	    mGR.mScore = prefs.getInt("mScore", mGR.mScore);
	    mGR.win = prefs.getInt("win", mGR.win);

	    
	    mGR.mPlayer.move = prefs.getBoolean("Playermo", mGR.mPlayer.move);
	    
	    mGR.mPlayer.ball = (byte)prefs.getInt("Playerball", mGR.mPlayer.ball);
	    mGR.mPlayer.fireColor = (byte)prefs.getInt("PlayerfireColor", mGR.mPlayer.fireColor);
	    mGR.mPlayer.ResColor = (byte)prefs.getInt("PlayerResColor", mGR.mPlayer.ResColor);
	    mGR.mPlayer.anim = (byte)prefs.getInt("Playeranim", mGR.mPlayer.anim);
	    
	    mGR.mPlayer.x = prefs.getFloat("Playerx", mGR.mPlayer.x);
	    mGR.mPlayer.y = prefs.getFloat("Playery", mGR.mPlayer.y);
	    mGR.mPlayer.vx = prefs.getFloat("Playervx", mGR.mPlayer.vx);
	    mGR.mPlayer.vy = prefs.getFloat("Playervy", mGR.mPlayer.vy);
	    mGR.mPlayer.ang = prefs.getFloat("Playerang", mGR.mPlayer.ang);
	    
		for (int i = 0; i < mGR.mCBubble.length; i++) {
			for (int j = 0; j < mGR.mCBubble[i].length; j++) {
				mGR.mCBubble[i][j].visited = prefs.getBoolean(i+"CBubblevi"+j, mGR.mCBubble[i][j].visited);
				mGR.mCBubble[i][j].checkFall = prefs.getBoolean(i+"CBubbleche"+j, mGR.mCBubble[i][j].checkFall);
				mGR.mCBubble[i][j].candy = (byte)prefs.getInt(i+"CBubblecandy"+j, mGR.mCBubble[i][j].candy);
				mGR.mCBubble[i][j].CBubble = (byte)prefs.getInt(i+"CBubbleCBubble"+j, mGR.mCBubble[i][j].CBubble);
				mGR.mCBubble[i][j].anim = (byte)prefs.getInt(i+"CBubbleanim"+j, mGR.mCBubble[i][j].anim);
				mGR.mCBubble[i][j].i = (byte)prefs.getInt(i+"CBubble_i"+j, mGR.mCBubble[i][j].i);
				mGR.mCBubble[i][j].j = (byte)prefs.getInt(i+"CBubble_j"+j, mGR.mCBubble[i][j].j);
				
			}
		}
		if(M.GameScreen == M.GAMEMENU)
			M.play(R.drawable.splash_other);
		if(M.GameScreen == M.GAMEPLAY)
			M.play(R.drawable.main_gmeplay);
	}
	void Exit()
	{
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Do you want to Exit?")
	    .setPositiveButton("Rate Us",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
    	   Intent intent = new Intent(Intent.ACTION_VIEW);
    	   intent.setData(Uri.parse(M.LINK+getClass().getPackage().getName()));
    	   startActivity(intent);
      }}).setNeutralButton("No",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
      }}).setNegativeButton("Yes",new DialogInterface.OnClickListener(){public void onClick(DialogInterface dialog, int which) {
		    		   finish();M.GameScreen=M.GAMELOGO;mGR.root.Counter =0;
      }}).show();
  }
	public void load() {
		try{handlerload.sendEmptyMessage(0);}catch(Exception e){}
	}
	Handler handlerload = new Handler() {public void handleMessage(Message msg) {loadInter();}};

	
	private void loadInter(){
		if (!interstitial.isLoaded() && !mGR.addFree) {
			AdRequest adRequest = new AdRequest.Builder()
//					.addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("67701763FB847AEBD3C6EE486475ED94")
			.build();
			interstitial.loadAd(adRequest);
			interstitial.setAdListener(new AdGameListener(this));
		}
	}

	public void ShowInterstitial() {
		if(!mGR.addFree)
			try{handler.sendEmptyMessage(0);}catch(Exception e){}
	}
	Handler handler = new Handler() {public void handleMessage(Message msg) {show();}};

	private void show() {
		try {
			if (interstitial != null && !mGR.addFree)
				if (interstitial.isLoaded()) {
					interstitial.show();
				}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		if (!mGR.SingUpadate) {
			for (int i = 0; i < mGR.mAchiUnlock.length; i++) {
				if (mGR.mAchiUnlock[i]) {
					UnlockAchievement(M.ACHIV[i]);
				}
			}
			GameRenderer.mStart.Submitscore(R.string.leaderboard_best_score, (int)mGR.mHScore);
			mGR.SingUpadate = true;
		}
	}
	public void Submitscore(final int ID,long total) {
		if (!isSignedIn()) {
//			beginUserInitiatedSignIn();
		} else { Games.Leaderboards.submitScore(getApiClient(), getString(ID), total);}
	}

	int RC_UNUSED = 5001;

	// @Override
	public void onShowLeaderboardsRequested() {
		if (isSignedIn()) {
            startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(getApiClient()),RC_UNUSED);
        } else {
        	beginUserInitiatedSignIn();
        }
	}

	public void UnlockAchievement(final int ID) {
		try {
			if (!isSignedIn()) {
//			beginUserInitiatedSignIn();
		} else {
			if (isSignedIn()) {
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
		        	beginUserInitiatedSignIn();
		        }
		} catch (Exception e) {}
	}
	void Achivement(){
		if(!mGR.mAchiUnlock[0] && mGR.mHScore > 5000){
			mGR.mAchiUnlock[0] = true;
			UnlockAchievement(M.ACHIV[0]);
		}
		if(!mGR.mAchiUnlock[1] && mGR.mHScore > 10000){
			mGR.mAchiUnlock[1] = true;
			UnlockAchievement(M.ACHIV[1]);
		}
		if(!mGR.mAchiUnlock[2] && mGR.mHScore > 20000){
			mGR.mAchiUnlock[2] = true;
			UnlockAchievement(M.ACHIV[2]);
		}
		if(!mGR.mAchiUnlock[3] && mGR.mTScore > 100000){
			mGR.mAchiUnlock[3] = true;
			UnlockAchievement(M.ACHIV[3]);
		}
		if(!mGR.mAchiUnlock[4] && mGR.mTScore > 500000){
			mGR.mAchiUnlock[4] = true;
			UnlockAchievement(M.ACHIV[4]);
		}
		GameRenderer.mStart.Submitscore(R.string.leaderboard_best_score, (int)(mGR.mHScore));
	}
	
}