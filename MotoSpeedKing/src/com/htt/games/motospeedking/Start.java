package com.htt.games.motospeedking;

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
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;

public class Start  extends BaseGameActivity 
{
	AdView adView = null;
	GameRenderer mGR = null;
	private InterstitialAd interstitial;
	private View mMainView; // The main view of the activity
	void callAdds() {
		adView = (AdView) this.findViewById(R.id.addgame);
		AdRequest adRequest = new AdRequest.Builder().build();
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
		WindowManager mWinMgr = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
		M.ScreenWidth =mWinMgr.getDefaultDisplay().getWidth();
		M.ScreenHieght=mWinMgr.getDefaultDisplay().getHeight();
		System.out.println(M.ScreenWidth+"   " +M.ScreenWidth);
		mMainView = getWindow().getDecorView();
		interstitial = new InterstitialAd(this);
		interstitial.setAdUnitId(getString(R.string.INTERSTITIAL_ID));
	    mWinMgr = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
		M.ScreenWidth =mWinMgr.getDefaultDisplay().getWidth();
		M.ScreenHieght=mWinMgr.getDefaultDisplay().getHeight();
		System.out.println(M.ScreenWidth+"  ~~ " +M.ScreenWidth);
		mGR=new GameRenderer(this);
		
	    VortexView glSurface=(VortexView) findViewById(R.id.vortexview); // use the xml to set the view
	    glSurface.setRenderer(mGR);
	    glSurface.showRenderer(mGR);
	    callAdds();
	}
	@Override 
	public void onPause () {
		M.stop();
		pause();
		super.onPause();
	}
	
	public void FullScreencall() {
		if (Build.VERSION.SDK_INT > 14 && !ViewConfiguration.get(GameRenderer.mContext).hasPermanentMenuKey()) {
			mMainView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
					| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
					| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
					| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
					| View.SYSTEM_UI_FLAG_FULLSCREEN
					| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
		}
		mGR.BackKey = true;
	}
	
	@Override 
	public void onResume() {
		if(!ViewConfiguration.get(GameRenderer.mContext).hasPermanentMenuKey()){
			FullScreencall();
		}
		
		resume();
		super.onResume();
		//view.onResume();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			switch (M.GameScreen) {
			case M.GAMELOGO:
				return true;
			case M.GAMEMENU:
				Exit();
				return true;
			case M.GAMEPLAY:
				M.GameScreen = M.GAMEPAUSE;
				M.BGSTOP();
				return true;
			default:
				M.GameScreen = M.GAMEMENU;
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	public boolean onKeyUp(int keyCode, KeyEvent event) {
		return super.onKeyUp(keyCode, event);
	}
	public void onDestroy()
	{
		super.onDestroy();
	}
	void pause()
	{
		if(M.GAMEPLAY == M.GameScreen)
			M.GameScreen = M.GAMEPAUSE;
		M.stop();
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
	    Editor editor = prefs.edit();
	    editor.putInt("q", M.GameScreen);
	    editor.putBoolean("w", M.setValue);
	    editor.putFloat("e", M.SPD);
	    editor.putInt("r", M.n);
	    
	    for(int i =0;i<mGR.mScan.length;i++){
	    	editor.putFloat("t"+i, mGR.mScan[i].y);
		    editor.putInt("y"+i, mGR.mScan[i].no);
		     
	    }
	    for(int i =0;i<mGR.mMove.length;i++){
	    	editor.putFloat("u"+i, mGR.mMove[i].move);
	    	editor.putFloat("i"+i, mGR.mMove[i].mv);
	    }
	    
	    editor.putFloat("o", mGR.mDriver.x);
	    editor.putFloat("p", mGR.mDriver.y);
	    editor.putFloat("a", mGR.mDriver.vx);
	    editor.putInt("s", mGR.mDriver.blast);
		
		for(int i =0;i<mGR.BGY.length;i++)
	    	editor.putFloat("d"+i, mGR.BGY[i]);
		
		
		editor.putFloat("f", mGR.mScore);
		editor.putInt("g", mGR.mHScore);
		editor.putInt("h", mGR.NewBest);
		editor.putFloat("j", mGR.mTotal);
		
		editor.putBoolean("k", mGR.addFree);
		editor.putBoolean("l", mGR.SingUpadate);
		for(int i =0;i<mGR.mAchiUnlock.length;i++)
			editor.putBoolean("z"+i, mGR.mAchiUnlock[i]);
		
	    
	    editor.commit();
	}
	void resume()
	{
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = prefs.getInt("q", M.GAMELOGO);
		M.setValue = prefs.getBoolean("w", M.setValue);
		M.SPD = prefs.getFloat("e", M.SPD);
		M.n = (byte)prefs.getInt("r", M.n);
	    
	    for(int i =0;i<mGR.mScan.length;i++){
	    	mGR.mScan[i].y = prefs.getFloat("t"+i, mGR.mScan[i].y);
	    	mGR.mScan[i].no = prefs.getInt("y"+i, mGR.mScan[i].no);
		     
	    }
	    for(int i =0;i<mGR.mMove.length;i++){
	    	mGR.mMove[i].move = prefs.getFloat("u"+i, mGR.mMove[i].move);
	    	mGR.mMove[i].mv = prefs.getFloat("i"+i, mGR.mMove[i].mv);
	    }
	    
	    mGR.mDriver.x = prefs.getFloat("o", mGR.mDriver.x);
	    mGR.mDriver.y = prefs.getFloat("p", mGR.mDriver.y);
	    mGR.mDriver.vx = prefs.getFloat("a", mGR.mDriver.vx);
	    mGR.mDriver.blast = (byte)prefs.getInt("s", mGR.mDriver.blast);
		
		for(int i =0;i<mGR.BGY.length;i++)
			mGR.BGY[i] = prefs.getFloat("d"+i, mGR.BGY[i]);
		
		
		mGR.mScore = prefs.getFloat("f", mGR.mScore);
		mGR.mHScore = prefs.getInt("g", mGR.mHScore);
		mGR.NewBest = prefs.getInt("h", mGR.NewBest);
		mGR.mTotal = prefs.getFloat("j", mGR.mTotal);
		
		mGR.addFree = prefs.getBoolean("k", mGR.addFree);
		mGR.SingUpadate = prefs.getBoolean("l", mGR.SingUpadate);
		for(int i =0;i<mGR.mAchiUnlock.length;i++)
			mGR.mAchiUnlock[i] = prefs.getBoolean("z"+i, mGR.mAchiUnlock[i]);
		
	}
	void Exit()
	{
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Do you want to Exit?")
	    .setPositiveButton("More",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
    	   Intent intent = new Intent(Intent.ACTION_VIEW);
    	   intent.setData(Uri.parse(M.MARKET));
    	   startActivity(intent);
      }}).setNeutralButton("No",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
    	  if(!ViewConfiguration.get(GameRenderer.mContext).hasPermanentMenuKey()){
  			FullScreencall();
  		}
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
			GameRenderer.mStart.Submitscore(R.string.leaderboard_best_score, mGR.mHScore);
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
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		/*if (!mGR.mInApp.mHelper.handleActivityResult(requestCode,resultCode, data)) {
			super.onActivityResult(requestCode, resultCode, data);
		}*/
	}
	void Achivment(){
		if(mGR.mScore > 100 && !mGR.mAchiUnlock[0]){
			mGR.mAchiUnlock[0] = true;
			UnlockAchievement(M.ACHIV[0]);
		}
		if(mGR.mScore > 200 && !mGR.mAchiUnlock[1]){
			mGR.mAchiUnlock[1] = true;
			UnlockAchievement(M.ACHIV[1]);
		}
		if(mGR.mScore > 500 && !mGR.mAchiUnlock[2]){
			mGR.mAchiUnlock[2] = true;
			UnlockAchievement(M.ACHIV[2]);
		}
		if(mGR.mTotal > 5000 && !mGR.mAchiUnlock[3]){
			mGR.mAchiUnlock[3] = true;
			UnlockAchievement(M.ACHIV[3]);
		}
		if(mGR.mTotal > 10000 && !mGR.mAchiUnlock[4]){
			mGR.mAchiUnlock[4] = true;
			UnlockAchievement(M.ACHIV[4]);
		}
		Submitscore(R.string.leaderboard_best_score, mGR.mHScore);
	}
}