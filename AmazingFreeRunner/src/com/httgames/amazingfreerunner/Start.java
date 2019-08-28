package com.httgames.amazingfreerunner;

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
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class Start extends BaseGameActivity {
	int _keyCode = 0;
	AdView adView = null;
	HTTRenderer mGR = null;
	private InterstitialAd interstitial;
	boolean fromCreat = false;
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
		interstitial = new InterstitialAd(this);
		interstitial.setAdUnitId(getString(R.string.INTERSTITIAL_ID));
	    WindowManager mWinMgr = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
		C.ScreenWidth =mWinMgr.getDefaultDisplay().getWidth();
		C.ScreenHieght=mWinMgr.getDefaultDisplay().getHeight();
		mGR=new HTTRenderer(this);
	    CView glSurface=(CView) findViewById(R.id.vortexview); // use the xml to set the view
	    glSurface.setRenderer(mGR);
	    glSurface.showRenderer(mGR);
	    if (!getSharedPreferences("X", MODE_PRIVATE).getBoolean("addFree",mGR.addFree)){
			callAdds();
			load();
			fromCreat = true;
	    }
	}
	@Override 
	public void onPause () {
		C.stop();
		pause();
		super.onPause();
	}
	@Override 
	public void onResume() {                                                                                                       
		resume();
		super.onResume();
		//view.onResume();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			switch (C.GameScreen) {
			case C.GAMELOGO:
				return false;
			case C.GAMEMENU:
				Exit();
				return false;
			case C.GAMEPLAY:
				C.GameScreen = C.GAMEPAUSE;
				return false;
			default:
				C.GameScreen = C.GAMEMENU;
				return false;
			}
		}
		_keyCode = keyCode;
		return super.onKeyDown(keyCode, event);
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

	void pause() {
		if (C.GameScreen == C.GAMEPLAY)
			C.GameScreen = C.GAMEPAUSE;
		C.stop();
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putInt("a", C.GameScreen);
		editor.putBoolean("b", C.setValue);
		editor.putBoolean("g", mGR.mPlyer.isStart);
		editor.putInt("h", mGR.mPlyer.Jump);
		editor.putInt("i", mGR.mPlyer.die);
		editor.putFloat("j", mGR.mPlyer.x);
		editor.putFloat("k", mGR.mPlyer.y);
		editor.putFloat("l", mGR.mPlyer.vy);
		for (int i = 0; i < mGR.mBlock.length; i++) {
			editor.putBoolean("c" + i, mGR.mBlock[i].isUp);
			editor.putFloat("d" + i, mGR.mBlock[i].x);
			editor.putFloat("e" + i, mGR.mBlock[i].y);
			editor.putFloat("f" + i, mGR.mBlock[i].sx);
		}
		editor.putInt("m", mGR.mScore);
		editor.putInt("n", mGR.mHScore);
		editor.putInt("o", mGR.mTotal);

		editor.putBoolean("p", mGR.addFree);
		for (int i = 0; i < mGR.mAchiUnlock.length; i++)
			editor.putBoolean("q" + i, mGR.mAchiUnlock[i]);

		editor.commit();
	}
	void resume(){
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		C.GameScreen = prefs.getInt("a", C.GAMELOGO);
		C.setValue = prefs.getBoolean("b", true);
	    mGR.mPlyer.isStart = prefs.getBoolean("g", mGR.mPlyer.isStart);
	    mGR.mPlyer.Jump = prefs.getInt("h", mGR.mPlyer.Jump);
	    mGR.mPlyer.die = prefs.getInt("i", mGR.mPlyer.die);
	    mGR.mPlyer.x = prefs.getFloat("j", mGR.mPlyer.x);
	    mGR.mPlyer.y = prefs.getFloat("k", mGR.mPlyer.y);
	    mGR.mPlyer.vy = prefs.getFloat("l", mGR.mPlyer.vy);
	    for(int i =0;i<mGR.mBlock.length;i++){
	    	mGR.mBlock[i].isUp = prefs.getBoolean("c"+i, mGR.mBlock[i].isUp);
	    	mGR.mBlock[i].x = prefs.getFloat("d"+i, mGR.mBlock[i].x);
	    	mGR.mBlock[i].y = prefs.getFloat("e"+i, mGR.mBlock[i].y);
	    	mGR.mBlock[i].sx = prefs.getFloat("f"+i, mGR.mBlock[i].sx);
	    }
	    mGR.mScore = prefs.getInt("m", mGR.mScore);
	    mGR.mHScore = prefs.getInt("n", mGR.mHScore);
	    mGR.mTotal = prefs.getInt("o", mGR.mTotal);
	    mGR.addFree = prefs.getBoolean("p", mGR.addFree);
	    for(int i =0;i<mGR.mAchiUnlock.length;i++)
	    	mGR.mAchiUnlock[i] = prefs.getBoolean("q"+i, mGR.mAchiUnlock[i]);
	}
	void Exit()
	{
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Do you want to Exit?")
	    .setPositiveButton("More",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
    	   Intent intent = new Intent(Intent.ACTION_VIEW);
    	   intent.setData(Uri.parse(C.MARKET));
    	   startActivity(intent);
      }}).setNeutralButton("No",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
      }}).setNegativeButton("Yes",new DialogInterface.OnClickListener(){public void onClick(DialogInterface dialog, int which) {
		    		   finish();C.GameScreen=C.GAMELOGO;mGR.root.Counter =0;
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
		System.out.println("~~~~show~~~~~~~~~");
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
					UnlockAchievement(C.ACHIV[i]);
				}
			}
			Submitscore(R.string.leaderboard_best_score, mGR.mHScore);
//			mGR.SingUpadate = true;
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
		if(mGR.mScore > 19 && !mGR.mAchiUnlock[0]){
			mGR.mAchiUnlock[0] = true;
			UnlockAchievement(C.ACHIV[0]);
		}
		if(mGR.mScore > 49 && !mGR.mAchiUnlock[1]){
			mGR.mAchiUnlock[1] = true;
			UnlockAchievement(C.ACHIV[1]);
		}
		if(mGR.mScore > 99 && !mGR.mAchiUnlock[2]){
			mGR.mAchiUnlock[2] = true;
			UnlockAchievement(C.ACHIV[2]);
		}
		if(mGR.mTotal > 499 && !mGR.mAchiUnlock[3]){
			mGR.mAchiUnlock[3] = true;
			UnlockAchievement(C.ACHIV[3]);
		}
		if(mGR.mTotal > 1999 && !mGR.mAchiUnlock[4]){
			mGR.mAchiUnlock[4] = true;
			UnlockAchievement(C.ACHIV[4]);
		}
		Submitscore(R.string.leaderboard_best_score, mGR.mHScore);
	}
}