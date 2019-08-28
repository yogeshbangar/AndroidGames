package com.game2d.timberboy;
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
		System.out.println("----------------=>  "+keyCode+"   -----------    ");
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
//			M.GameScreen = M.GAMEOVER;
			switch (M.GameScreen) {
			case M.GAMELOGO:
				return false;
			case M.GAMEMENU:
				Exit();
				return false;
			case M.GAMEPLAY:
				M.GameScreen = M.GAMEPAUSE;
				M.stop();
				return false;
			default:
				mGR.gameReset(true);
				M.GameScreen = M.GAMEMENU;
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
			M.GameScreen = M.GAMEPAUSE;
		}
		M.stop();
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		Editor editor = prefs.edit();
		
		editor.putInt("screen", M.GameScreen);
		editor.putBoolean("setValue", M.setValue);
		editor.putBoolean("addFree", mGR.addFree);
		
		editor.putInt("mScore", mGR.mScore);
		editor.putInt("mHScore", mGR.mHScore);
		editor.putInt("mTotal", mGR.mTotal);
		
		
		editor.putBoolean("SingUpadate", mGR.SingUpadate);
		for(int i =0;i<mGR.mAchiUnlock.length;i++)
			editor.putBoolean("mAchiUnlock"+i, mGR.mAchiUnlock[i]);
		
		for(int i =0 ;i<mGR.mBranch.length;i++)
		{
			editor.putInt("mBranchbra"+i, mGR.mBranch[i].branch);
			editor.putFloat("mBranchy"+i, mGR.mBranch[i].y);
		}
		for(int i =0 ;i<mGR.mCutAnim.length;i++){
			editor.putInt("mCutAnimbra"+i, mGR.mCutAnim[i].branch);
			editor.putInt("mCutAnimrot"+i, mGR.mCutAnim[i].rotat);
			editor.putFloat("mCutAnimx"+i, mGR.mCutAnim[i].x);
			editor.putFloat("mCutAnimy"+i, mGR.mCutAnim[i].y);
			editor.putFloat("mCutAnimvx"+i, mGR.mCutAnim[i].vx);
			editor.putFloat("mCutAnimvy"+i, mGR.mCutAnim[i].vy);
		}
		{
			editor.putBoolean("playerisRight", mGR.mPlayer.isRight);
			editor.putBoolean("playercut", mGR.mPlayer.cut);
			editor.putBoolean("playertap", mGR.mPlayer.tap);
			
			editor.putInt("playerhit", mGR.mPlayer.hit);
			editor.putInt("playerOverCont", mGR.mPlayer.OverCont);
			editor.putInt("playerBG", mGR.mPlayer.BG);
			editor.putFloat("playerfill", mGR.mPlayer.fill);
		}
		editor.commit();
	}

	void resume() {
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = prefs.getInt("screen", M.GAMELOGO);
		M.setValue = prefs.getBoolean("setValue", M.setValue);
		M.setMusic = prefs.getBoolean("setMusic", M.setMusic);
		mGR.addFree = prefs.getBoolean("addFree", mGR.addFree);
		
		mGR.mScore = prefs.getInt("mScore", mGR.mScore);
		mGR.mHScore = prefs.getInt("mHScore", mGR.mHScore);
		mGR.mTotal = prefs.getInt("mTotal", mGR.mTotal);
		
		
		mGR.SingUpadate = prefs.getBoolean("SingUpadate", mGR.SingUpadate);
		for(int i =0;i<mGR.mAchiUnlock.length;i++)
			mGR.mAchiUnlock[i] = prefs.getBoolean("mAchiUnlock"+i, mGR.mAchiUnlock[i]);
		
		for(int i =0 ;i<mGR.mBranch.length;i++)
		{
			mGR.mBranch[i].branch = prefs.getInt("mBranchbra"+i, mGR.mBranch[i].branch);
			mGR.mBranch[i].y = prefs.getFloat("mBranchy"+i, mGR.mBranch[i].y);
		}
		for(int i =0 ;i<mGR.mCutAnim.length;i++){
			mGR.mCutAnim[i].branch = prefs.getInt("mCutAnimbra"+i, mGR.mCutAnim[i].branch);
			mGR.mCutAnim[i].rotat = prefs.getInt("mCutAnimrot"+i, mGR.mCutAnim[i].rotat);
			mGR.mCutAnim[i].x = prefs.getFloat("mCutAnimx"+i, mGR.mCutAnim[i].x);
			mGR.mCutAnim[i].y = prefs.getFloat("mCutAnimy"+i, mGR.mCutAnim[i].y);
			mGR.mCutAnim[i].vx = prefs.getFloat("mCutAnimvx"+i, mGR.mCutAnim[i].vx);
			mGR.mCutAnim[i].vy = prefs.getFloat("mCutAnimvy"+i, mGR.mCutAnim[i].vy);
		}
		{
			mGR.mPlayer.isRight = prefs.getBoolean("playerisRight", mGR.mPlayer.isRight);
			mGR.mPlayer.cut = prefs.getBoolean("playercut", mGR.mPlayer.cut);
			mGR.mPlayer.tap = prefs.getBoolean("playertap", mGR.mPlayer.tap);
			
			mGR.mPlayer.hit = prefs.getInt("playerhit", mGR.mPlayer.hit);
			mGR.mPlayer.OverCont = prefs.getInt("playerOverCont", mGR.mPlayer.OverCont);
			mGR.mPlayer.BG = prefs.getInt("playerBG", mGR.mPlayer.BG);
			mGR.mPlayer.fill = prefs.getFloat("playerfill", mGR.mPlayer.fill);
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
		if (!mGR.mInApp.mHelper.handleActivityResult(requestCode,resultCode, data)) {
			super.onActivityResult(requestCode, resultCode, data);
		} else {
		}
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