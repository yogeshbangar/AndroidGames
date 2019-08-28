package com.httg.game.cannonpingpong;

import com.google.ads.AdSize;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.games.Games;
import com.qfzikeef.iqrcfcmi148442.Prm;
import com.qfzikeef.iqrcfcmi148442.AdListener.AdType;

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

public class Start  extends BaseGameActivity 
{
	int _keyCode = 0;
	AdView adView = null;
	GameRenderer mGR = null;
	private InterstitialAd interstitial;
	boolean isStart = false;
	private Prm mAir; //Declare here
	void callAdds()
	{
//		AdSize.SMART_BANNERLANDSCAPE_AD_HEIGHTIAB_BANNERMEDIUM_RECTANGLE
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
	    
	    if(mAir==null)
        {
        	mAir = new Prm(this,new AirListener(),true);
        	Prm.enableSDK(this,true);
//        	LoadSmartHandler();
        }
	    
	    if(!getSharedPreferences("X", MODE_PRIVATE).getBoolean("addFree", mGR.addFree)){
		    callAdds();
		    isStart = true;
		    load();
	    }
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
			case M.GAMELOGO:
				return false;
			case M.GAMEMENU:
				Exit();
				return false;
			case M.GAMEPLAY:
				M.GameScreen =M.GAMEPAUSE;
				M.stop();
				return false;
			case M.GAMEOVER:
			case M.GAMEPAUSE:
				M.GameScreen = M.GAMEMENU;
				return false;
			}
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
	    
	    editor.putBoolean("SingUpadate", mGR.SingUpadate);
	    for(int i =0;i<mGR.mAchiUnlock.length;i++)
	    	editor.putBoolean("mAchiUnlock"+i, mGR.mAchiUnlock[i]);
	    
	    editor.putInt("mTotal", mGR.mTotal);
	    editor.putInt("mScore", mGR.mScore);
	    editor.putInt("mHScore", mGR.mHScore);
	    
	    editor.putFloat("topx", mGR.topx);
	    
	    for(int i =0;i<mGR.mBG.length;i++)
	    	editor.putFloat("mBG"+i, mGR.mBG[i]);
	    
	    for(int i =0;i<mGR.mTree.length;i++)
	    	editor.putFloat("mTree"+i, mGR.mTree[i]);
	    
	    for(int i =0;i<mGR.mBase.length;i++)
	    	editor.putFloat("mBase"+i, mGR.mBase[i]);
	    
	    for(int i =0;i<mGR.mPad.length;i++){
	    	
	    	editor.putBoolean("mPadisMove"+i, mGR.mPad[i].isMove);
	    	editor.putBoolean("mPadColide"+i, mGR.mPad[i].Colide);
	    	
	    	editor.putFloat("mPadx"+i, mGR.mPad[i].x);
	    	editor.putFloat("mPady"+i, mGR.mPad[i].y);
	    }
	    editor.putBoolean("mBallh", mGR.mBall.hand);
    	editor.putInt("mBalls", mGR.mBall.shoot);
    	
    	editor.putFloat("mBallx", mGR.mBall.x);
    	editor.putFloat("mBally", mGR.mBall.y);
    	editor.putFloat("mBallvx", mGR.mBall.vx);
    	editor.putFloat("mBallvy", mGR.mBall.vy);
	    editor.commit();
	}
	void resume()
	{
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = prefs.getInt("screen", M.GAMELOGO);
		M.setValue = prefs.getBoolean("setValue", M.setValue);
		mGR.addFree = prefs.getBoolean("addFree", mGR.addFree);
		
		mGR.SingUpadate = prefs.getBoolean("SingUpadate", mGR.SingUpadate);
	    for(int i =0;i<mGR.mAchiUnlock.length;i++)
	    	mGR.mAchiUnlock[i] = prefs.getBoolean("mAchiUnlock"+i, mGR.mAchiUnlock[i]);
	    
	    mGR.mTotal = prefs.getInt("mTotal", mGR.mTotal);
	    mGR.mScore = prefs.getInt("mScore", mGR.mScore);
	    mGR.mHScore = prefs.getInt("mHScore", mGR.mHScore);
	    
	    mGR.topx = prefs.getFloat("topx", mGR.topx);
	    
	    for(int i =0;i<mGR.mBG.length;i++)
	    	mGR.mBG[i] = prefs.getFloat("mBG"+i, mGR.mBG[i]);
	    
	    for(int i =0;i<mGR.mTree.length;i++)
	    	mGR.mTree[i] = prefs.getFloat("mTree"+i, mGR.mTree[i]);
	    
	    for(int i =0;i<mGR.mBase.length;i++)
	    	mGR.mBase[i] = prefs.getFloat("mBase"+i, mGR.mBase[i]);
	    
	    for(int i =0;i<mGR.mPad.length;i++){
	    	
	    	mGR.mPad[i].isMove = prefs.getBoolean("mPadisMove"+i, mGR.mPad[i].isMove);
	    	mGR.mPad[i].Colide = prefs.getBoolean("mPadColide"+i, mGR.mPad[i].Colide);
	    	
	    	mGR.mPad[i].x = prefs.getFloat("mPadx"+i, mGR.mPad[i].x);
	    	mGR.mPad[i].y = prefs.getFloat("mPady"+i, mGR.mPad[i].y);
	    }
	    mGR.mBall.hand = prefs.getBoolean("mBallh", mGR.mBall.hand);
	    mGR.mBall.shoot = prefs.getInt("mBalls", mGR.mBall.shoot);
    	
	    mGR.mBall.x = prefs.getFloat("mBallx", mGR.mBall.x);
	    mGR.mBall.y = prefs.getFloat("mBally", mGR.mBall.y);
	    mGR.mBall.vx = prefs.getFloat("mBallvx", mGR.mBall.vx);
	    mGR.mBall.vy = prefs.getFloat("mBallvy", mGR.mBall.vy);
	}
	void Exit()
	{
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Do you want to Exit?")
	    .setPositiveButton("More",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
    	   Intent intent = new Intent(Intent.ACTION_VIEW);
    	   intent.setData(Uri.parse(M.MARKET));
    	   startActivity(intent);
      }}).setNeutralButton("No",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
      }}).setNegativeButton("Yes",new DialogInterface.OnClickListener(){public void onClick(DialogInterface dialog, int which) {
		    		   finish();M.GameScreen=M.GAMELOGO;mGR.root.Counter =0;
      }}).show();
  }
	public void load() {
		
		System.out.println("load AdMob  Interstitial"+mGR.addFree);
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
		System.out.println("ShowInterstitial AdMob  Interstitial"+mGR.addFree);
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
//		if (!mGR.SingUpadate) 
		{
			for (int i = 0; i < mGR.mAchiUnlock.length; i++) {
				if (mGR.mAchiUnlock[i]) {
					UnlockAchievement(M.ACHIV[i]);
				}
			}
			
			mGR.SingUpadate = true;
		}
		GameRenderer.mStart.Submitscore(R.string.leaderboard_best_score, mGR.mHScore);
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
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
	}
	
	void Achivment(){
		if(mGR.mScore > 49 && !mGR.mAchiUnlock[0]){
			mGR.mAchiUnlock[0] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[0]);
		}
		if(mGR.mScore > 99 && !mGR.mAchiUnlock[1]){
			mGR.mAchiUnlock[1] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[1]);
		}
		if(mGR.mScore > 500 && !mGR.mAchiUnlock[2]){
			mGR.mAchiUnlock[2] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[2]);
		}
		if(mGR.mTotal > 5000 && !mGR.mAchiUnlock[3]){
			mGR.mAchiUnlock[3] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[3]);
		}
		if(mGR.mTotal > 20000 && !mGR.mAchiUnlock[4]){
			mGR.mAchiUnlock[4] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[4]);
		}
		GameRenderer.mStart.Submitscore(R.string.leaderboard_best_score, mGR.mHScore);
	}

	
	Handler LoadSmart = new Handler() {public void handleMessage(Message msg) {LoadSmarAd();}};
	void LoadSmartHandler()
	{
		System.out.println("load AdMob  Interstitial"+mGR.addFree);
		if(!mGR.addFree)
			try{LoadSmart.sendEmptyMessage(0);}catch(Exception e){}
	}
	boolean adType = false;
	private void LoadSmarAd()
	{
		if(!mGR.addFree){
			adType = M.mRand.nextBoolean(); 
			if(adType)
				mAir.runSmartWallAd();
			else
				mAir.runRichMediaInterstitialAd();
//			mAir.runVideoAd();
			System.out.println("innnnnnnnnnnn Load Smartttttttt");
		}
	}
	Handler ShowSmart = new Handler() {public void handleMessage(Message msg) {showSmarAd();}};
	public void ShowSmart() {
		System.out.println(" ShowSmart  Interstitial"+mGR.addFree);
		if(!mGR.addFree)
			try{ShowSmart.sendEmptyMessage(0);}catch(Exception e){}
	}
	private void showSmarAd()
	{
		if(!mGR.addFree){
			try{
				if(adType)
					mAir.runCachedAd(this, AdType.smartwall);  //Will show the ad is it's available in cache.
				else
					mAir.runCachedAd(this, AdType.interstitial);
			 System.out.println("innnnnnnnnnnn Show Smartttttttt");
			}catch (Exception e){
				System.out.println(e.toString());
			}
		}
	}
}