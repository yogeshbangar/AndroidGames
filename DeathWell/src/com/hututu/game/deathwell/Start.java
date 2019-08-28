package com.hututu.game.deathwell;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.games.Games;

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

public class Start   extends BaseGameActivity 
{
	int _keyCode = 0;
	AdView adView = null;
	GameRenderer mGR = null;
	private static Context CONTEXT;
	private InterstitialAd interstitial;
	boolean OnCreate = false;
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
	    load();
	    OnCreate = true;
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
				M.GameScreen = M.GAMEPAUSE;
				M.stop();
				break;
			case M.GAMEMENU:
			case M.GAMEOVER:
				Exit();
				break;
			default:
				M.GameScreen = M.GAMEMENU;
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
		if(M.GameScreen == M.GAMEPLAY)
			M.GameScreen = M.GAMEPAUSE;
		M.stop();
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
	    Editor editor = prefs.edit();
	    editor.putInt("screen", M.GameScreen);
	    editor.putBoolean("setValue", M.setValue);
	    editor.putBoolean("addFree", mGR.addFree);
	    
	    editor.putBoolean("SingUpadate", mGR.SingUpadate);
	    for(int i=0;i<mGR.mAchiUnlock.length;i++)
	    	editor.putBoolean("mAchiUnlock"+i, mGR.mAchiUnlock[i]);
	    
		
	    editor.putInt("OverCount", mGR.OverCount);
	    editor.putInt("oppCrash", mGR.oppCrash);
	    editor.putInt("mTotal", mGR.mTotal);
	    editor.putInt("Cir", mGR.Cir);
	    editor.putInt("BestCir", mGR.BestCir);
	    
	    {
	    	editor.putInt("PlayermPath", mGR.mPlayer.mPath);
	    	editor.putInt("PlayermCount", mGR.mPlayer.mCount);
	    	editor.putInt("Playerang", mGR.mPlayer.ang);
	    	
	    	editor.putFloat("Playermul", mGR.mPlayer.mul);
	    }
	    for(int i =0;i<mGR.mOpp.length;i++)
	    {
	    	editor.putBoolean("Oppchange"+i, mGR.mOpp[i].change);
	    	editor.putBoolean("Oppplace"+i, mGR.mOpp[i].place);
	    	editor.putInt("OppmPath"+i, mGR.mOpp[i].mPath);
	    	editor.putInt("OppmCount"+i, mGR.mOpp[i].mCount);
	    	editor.putInt("Oppang"+i, mGR.mOpp[i].ang);
	    	
	    	editor.putFloat("Oppmul"+i, mGR.mOpp[i].mul);
	    }
	    
	    
	    editor.commit();
	}
	void resume()
	{
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = prefs.getInt("screen", M.GAMELOGO);
		M.setValue = prefs.getBoolean("setValue", M.setValue);
		mGR.addFree = prefs.getBoolean("addFree", false);
		
		
		mGR.SingUpadate = prefs.getBoolean("SingUpadate", false);
	    for(int i=0;i<mGR.mAchiUnlock.length;i++)
	    	mGR.mAchiUnlock[i] = prefs.getBoolean("mAchiUnlock"+i, false);
	    
		
	    mGR.OverCount = (byte)prefs.getInt("OverCount", mGR.OverCount);
	    mGR.oppCrash = (byte)prefs.getInt("oppCrash", mGR.oppCrash);
	    mGR.mTotal = prefs.getInt("mTotal", mGR.mTotal);
	    mGR.Cir = prefs.getInt("Cir", mGR.Cir);
	    mGR.BestCir = prefs.getInt("BestCir", mGR.BestCir);
	    
	    {
	    	mGR.mPlayer.mPath = prefs.getInt("PlayermPath", mGR.mPlayer.mPath);
	    	mGR.mPlayer.mCount = prefs.getInt("PlayermCount", mGR.mPlayer.mCount);
	    	mGR.mPlayer.ang = prefs.getInt("Playerang", mGR.mPlayer.ang);
	    	
	    	mGR.mPlayer.mul = prefs.getFloat("Playermul", mGR.mPlayer.mul);
	    }
	    for(int i =0;i<mGR.mOpp.length;i++)
	    {
	    	mGR.mOpp[i].change = prefs.getBoolean("Oppchange"+i, mGR.mOpp[i].change);
	    	mGR.mOpp[i].place = prefs.getBoolean("Oppplace"+i, mGR.mOpp[i].place);
	    	mGR.mOpp[i].mPath = prefs.getInt("OppmPath"+i, mGR.mOpp[i].mPath);
	    	mGR.mOpp[i].mCount = prefs.getInt("OppmCount"+i, mGR.mOpp[i].mCount);
	    	mGR.mOpp[i].ang = prefs.getInt("Oppang"+i, mGR.mOpp[i].ang);
	    	
	    	mGR.mOpp[i].mul = prefs.getFloat("Oppmul"+i, mGR.mOpp[i].mul);
	    }
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
			Submitscore(R.string.leaderboard_best_score, mGR.BestCir);
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
	void Achivment(){
		if(mGR.Cir > 49 && !mGR.mAchiUnlock[0]){
			mGR.mAchiUnlock[0] = true;
			UnlockAchievement(M.ACHIV[0]);
		}
		if(mGR.Cir > 99 && !mGR.mAchiUnlock[1]){
			mGR.mAchiUnlock[1] = true;
			UnlockAchievement(M.ACHIV[1]);
		}
		if(mGR.Cir > 199 && !mGR.mAchiUnlock[2]){
			mGR.mAchiUnlock[2] = true;
			UnlockAchievement(M.ACHIV[2]);
		}
		if(mGR.mTotal > 999 && !mGR.mAchiUnlock[3]){
			mGR.mAchiUnlock[3] = true;
			UnlockAchievement(M.ACHIV[3]);
		}
		if(mGR.mTotal > 4999 && !mGR.mAchiUnlock[4]){
			mGR.mAchiUnlock[4] = true;
			UnlockAchievement(M.ACHIV[4]);
		}
		Submitscore(R.string.leaderboard_best_score, mGR.BestCir);
	}
}