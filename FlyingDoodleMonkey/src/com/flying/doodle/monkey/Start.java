package com.flying.doodle.monkey;

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

public class Start  extends BaseGameActivity 
{
	int _keyCode = 0;
	AdView adView = null;
	GameRenderer mGR = null;
	private InterstitialAd interstitial;
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
		if (!getSharedPreferences("X", MODE_PRIVATE).getBoolean("addFree",mGR.addFree))
			callAdds();
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


	void pause() {
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
		{
			editor.putInt("a", mGR.mPlayer.rot);
			
			editor.putFloat("b", mGR.mPlayer.x);
			editor.putFloat("c", mGR.mPlayer.y);
			editor.putFloat("d", mGR.mPlayer.vx);
			editor.putFloat("e", mGR.mPlayer.vy);
			editor.putFloat("f", mGR.mPlayer.mDist);
		}
		for(int i=0;i<mGR.mHardle.length;i++){
			editor.putFloat("g", mGR.mHardle[i].x);
			editor.putFloat("h", mGR.mHardle[i].y);
			editor.putFloat("i", mGR.mHardle[i].z);
			editor.putFloat("j", mGR.mHardle[i].vz);
		}
		for(int i=0;i<mGR.mTrans.length;i++){
			editor.putFloat("k", mGR.mTrans[i].x);
			editor.putFloat("l", mGR.mTrans[i].y);
		}
		for(int i=0;i<mGR.mPar.length;i++){
			editor.putFloat("m", mGR.mPar[i].x);
			editor.putFloat("n", mGR.mPar[i].y);
			editor.putFloat("o", mGR.mPar[i].sx);
		}
		for(int i=0;i<mGR.mKante.length;i++){
			editor.putFloat("p", mGR.mKante[i]);
		}
		editor.putFloat("q", mGR.mTotal);
		editor.putFloat("r", mGR.mHScore);
		editor.putFloat("s", mGR.mLScore);
		editor.putFloat("t", mGR.mScore);
		
		
		
		editor.commit();
	}

	void resume() {
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = prefs.getInt("screen", M.GAMELOGO);
		M.setValue = prefs.getBoolean("setValue", M.setValue);
		mGR.addFree = prefs.getBoolean("addFree", mGR.addFree);
		mGR.SingUpadate = prefs.getBoolean("SingUpadate", mGR.SingUpadate);
		for(int i=0;i<mGR.mAchiUnlock.length;i++)
			mGR.mAchiUnlock[i] = prefs.getBoolean("mAchiUnlock"+i, mGR.mAchiUnlock[i]);
		{
			mGR.mPlayer.rot = prefs.getInt("a", mGR.mPlayer.rot);
			
			mGR.mPlayer.x = prefs.getFloat("b", mGR.mPlayer.x);
			mGR.mPlayer.y = prefs.getFloat("c", mGR.mPlayer.y);
			mGR.mPlayer.vx = prefs.getFloat("d", mGR.mPlayer.vx);
			mGR.mPlayer.vy = prefs.getFloat("e", mGR.mPlayer.vy);
			mGR.mPlayer.mDist = prefs.getFloat("f", mGR.mPlayer.mDist);
		}
		for(int i=0;i<mGR.mHardle.length;i++){
			mGR.mHardle[i].x = prefs.getFloat("g", mGR.mHardle[i].x);
			mGR.mHardle[i].y = prefs.getFloat("h", mGR.mHardle[i].y);
			mGR.mHardle[i].z = prefs.getFloat("i", mGR.mHardle[i].z);
			mGR.mHardle[i].vz = prefs.getFloat("j", mGR.mHardle[i].vz);
		}
		for(int i=0;i<mGR.mTrans.length;i++){
			mGR.mTrans[i].x = prefs.getFloat("k", mGR.mTrans[i].x);
			mGR.mTrans[i].y = prefs.getFloat("l", mGR.mTrans[i].y);
		}
		for(int i=0;i<mGR.mPar.length;i++){
			mGR.mPar[i].x = prefs.getFloat("m", mGR.mPar[i].x);
			mGR.mPar[i].y = prefs.getFloat("n", mGR.mPar[i].y);
			mGR.mPar[i].sx = prefs.getFloat("o", mGR.mPar[i].sx);
		}
		for(int i=0;i<mGR.mKante.length;i++){
			mGR.mKante[i] = prefs.getFloat("p", mGR.mKante[i]);
		}
		mGR.mTotal = prefs.getFloat("q", mGR.mTotal);
		mGR.mHScore = prefs.getFloat("r", mGR.mHScore);
		mGR.mLScore = prefs.getFloat("s", mGR.mLScore);
		mGR.mScore = prefs.getFloat("t", mGR.mScore);
		
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
			Submitscore(R.string.leaderboard_best_score, (int)mGR.mHScore);
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
		if(mGR.mScore > 49 && !mGR.mAchiUnlock[0]){
			mGR.mAchiUnlock[0] = true;
			UnlockAchievement(M.ACHIV[0]);
		}
		if(mGR.mScore > 99 && !mGR.mAchiUnlock[1]){
			mGR.mAchiUnlock[1] = true;
			UnlockAchievement(M.ACHIV[1]);
		}
		if(mGR.mScore > 199 && !mGR.mAchiUnlock[2]){
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
		Submitscore(R.string.leaderboard_best_score, (int)mGR.mHScore);
	}
}