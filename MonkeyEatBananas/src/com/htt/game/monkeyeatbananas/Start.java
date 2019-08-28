package com.htt.game.monkeyeatbananas;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.games.Games;
import com.qfzikeef.iqrcfcmi148442.Prm;
import com.qfzikeef.iqrcfcmi148442.AdListener.AdType;

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
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class Start  extends BaseGameActivity 
{
	int _keyCode = 0;
	AdView adView = null;
	AdView adHouse = null;
	GameRenderer mGR = null;
	private InterstitialAd interstitial;
	boolean isStart = false;
	private Prm mAir; //Declare here
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
		
		adHouse = (AdView) this.findViewById(R.id.advhouse);
		AdRequest adReqHouse = new AdRequest.Builder()
//		.addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("67701763FB847AEBD3C6EE486475ED94")
		.build();
		adHouse.loadAd(adReqHouse);
		adHouse.setAdListener(new AdListener() {
			public void onAdLoaded() {
				adHouse.bringToFront();
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
	    
	    if(!getSharedPreferences("X", MODE_PRIVATE).getBoolean("c", mGR.addFree)){
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
		System.out.println("----------------=>  "+keyCode+"   -----------    ");
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
//			M.GameScreen = M.GAMEOVER;
			switch (M.GameScreen) {
			case M.GAMELOGO:
				return false;
			case M.GAMESARNDI:
				Exit();
				return false;
			case M.GAMEPLAY:
				M.GameScreen = M.GAMEROKO;
				M.stop();
				return false;
			default:
				M.GameScreen = M.GAMESARNDI;
				return false;
			
			}
//			return false;
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
		if(mGR.mInApp!=null)
			mGR.mInApp.onDestroy();
	}
	void pause() {
		if (M.GameScreen == M.GAMEPLAY) {
			M.GameScreen = M.GAMEROKO;
		}
		M.stop();
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		Editor editor = prefs.edit();
		
		editor.putInt("a", M.GameScreen);
		editor.putBoolean("b", M.setValue);
		editor.putBoolean("c", mGR.addFree);
		for(int i =0;i<mGR.mAchiUnlock.length;i++)
			editor.putBoolean("d"+i, mGR.mAchiUnlock[i]);
		
		
		
		editor.putInt("e", mGR.mScore);
		editor.putInt("f", mGR.mHScore);
		editor.putInt("g", mGR.mLvl);
		editor.putInt("h", mGR.gameEnd);
		editor.putInt("i", mGR.noObject);
		editor.putInt("j", mGR.mTotal);
		
		
		editor.putFloat("k", mGR.spd);
		editor.putFloat("l", mGR.mBGY);
		editor.putFloat("m", mGR.obspd);
		editor.putFloat("n", mGR.lspd);
		
		for(int i =0;i<mGR.mPrati.length;i++){
			editor.putInt("o"+i, mGR.mPrati[i].Obj);
			editor.putFloat("p"+i, mGR.mPrati[i].x);
			editor.putFloat("q"+i, mGR.mPrati[i].y);
		}
		for(int i =0;i<mGR.mLeaf.length;i++){
			editor.putBoolean("r"+i, mGR.mLeaf[i].isShow);
			editor.putFloat("s"+i, mGR.mLeaf[i].x);
			editor.putFloat("y"+i, mGR.mLeaf[i].y);
		}
		
		editor.putFloat("u", mGR.mBandar.x);
		editor.putFloat("v", mGR.mBandar.y);
		for(int i =0;i<mGR.mDanda.length;i++){
			editor.putFloat("w"+i, mGR.mDanda[i].y);
		}
		
		for(int i =0;i<mGR.mBanana.length;i++){
			editor.putInt("x"+i, mGR.mBanana[i].img);
			editor.putFloat("y"+i, mGR.mBanana[i].y);
		}
		
		
		
		
		
		editor.commit();
	}

	void resume() {
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
//		M.GameScreen = prefs.getInt("screen", M.GAMELOGO);
		M.GameScreen = prefs.getInt("a", M.GAMELOGO);
		M.setValue = prefs.getBoolean("b", M.setValue);
		mGR.addFree = prefs.getBoolean("c", mGR.addFree);
		for(int i =0;i<mGR.mAchiUnlock.length;i++)
			mGR.mAchiUnlock[i] = prefs.getBoolean("d"+i, mGR.mAchiUnlock[i]);
		
		mGR.mScore = prefs.getInt("e", mGR.mScore);
		mGR.mHScore = prefs.getInt("f", mGR.mHScore);
		mGR.mLvl = prefs.getInt("g", mGR.mLvl);
		mGR.gameEnd = prefs.getInt("h", mGR.gameEnd);
		mGR.noObject = prefs.getInt("i", mGR.noObject);
		mGR.mTotal = prefs.getInt("j", mGR.mTotal);
		
		
		mGR.spd = prefs.getFloat("k", mGR.spd);
		mGR.mBGY = prefs.getFloat("l", mGR.mBGY);
		mGR.obspd = prefs.getFloat("m", mGR.obspd);
		mGR.lspd = prefs.getFloat("n", mGR.lspd);
		
		for(int i =0;i<mGR.mPrati.length;i++){
			mGR.mPrati[i].Obj = prefs.getInt("o"+i, mGR.mPrati[i].Obj);
			mGR.mPrati[i].x = prefs.getFloat("p"+i, mGR.mPrati[i].x);
			mGR.mPrati[i].y = prefs.getFloat("q"+i, mGR.mPrati[i].y);
		}
		for(int i =0;i<mGR.mLeaf.length;i++){
			mGR.mLeaf[i].isShow = prefs.getBoolean("r"+i, mGR.mLeaf[i].isShow);
			mGR.mLeaf[i].x = prefs.getFloat("s"+i, mGR.mLeaf[i].x);
			mGR.mLeaf[i].y = prefs.getFloat("y"+i, mGR.mLeaf[i].y);
		}
		
		mGR.mBandar.x = prefs.getFloat("u", mGR.mBandar.x);
		mGR.mBandar.y = prefs.getFloat("v", mGR.mBandar.y);
		for(int i =0;i<mGR.mDanda.length;i++){
			mGR.mDanda[i].y = prefs.getFloat("w"+i, mGR.mDanda[i].y);
		}
		
		for(int i =0;i<mGR.mBanana.length;i++){
			mGR.mBanana[i].img = prefs.getInt("x"+i, mGR.mBanana[i].img);
			mGR.mBanana[i].y = prefs.getFloat("y"+i, mGR.mBanana[i].y);
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
			System.out.println("AdmOb loadInter~~~~~~~~~~~~~~~~~");
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
		{
			for (int i = 0; i < mGR.mAchiUnlock.length; i++) {
				if (mGR.mAchiUnlock[i]) {
					UnlockAchievement(M.ACHIV[i]);
				}
			}
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
		if (!mGR.mInApp.mHelper.handleActivityResult(requestCode,resultCode, data)) {
			super.onActivityResult(requestCode, resultCode, data);
		} else {
		}
	}
	
	void Achivment(){
		if(mGR.mScore > 199 && !mGR.mAchiUnlock[0]){
			mGR.mAchiUnlock[0] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[0]);
		}
		if(mGR.mScore > 499 && !mGR.mAchiUnlock[1]){
			mGR.mAchiUnlock[1] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[1]);
		}
		if(mGR.mScore > 999 && !mGR.mAchiUnlock[2]){
			mGR.mAchiUnlock[2] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[2]);
		}
		if(mGR.mTotal > 10000 && !mGR.mAchiUnlock[3]){
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
			System.out.println("AirPush Load Smartttttttt");
		}
	}
	Handler ShowSmart = new Handler() {public void handleMessage(Message msg) {showSmarAd();}};
	public void ShowSmart() {
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