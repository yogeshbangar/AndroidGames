package com.oneday.games24.zombiesnak;

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
		editor.putInt("q", M.GameScreen);
		editor.putBoolean("w", M.setValue);
		
		for(int i =0;i<mGR.mFollower.length;i++){
			editor.putFloat("e"+i, mGR.mFollower[i].x);
			editor.putFloat("r"+i, mGR.mFollower[i].y);
		}
		for(int i =0;i<mGR.mLine.length;i++){
			editor.putFloat("t"+i, mGR.mLine[i].x);
			editor.putFloat("y"+i, mGR.mLine[i].y);
			editor.putFloat("u"+i, mGR.mLine[i].r);
			editor.putFloat("i"+i, mGR.mLine[i].g);
			editor.putFloat("o"+i, mGR.mLine[i].b);
		}
		
		editor.putFloat("p", mGR.mBall.x);
		editor.putFloat("a", mGR.mBall.y);
		editor.putFloat("s", mGR.mBall.vy);
		
		editor.putBoolean("d", mGR.mBall.isTuch);
		editor.putBoolean("f", mGR.mBall.isStart);
		editor.putBoolean("g", mGR.mBall.isRandom);
		editor.putInt("h", mGR.mBall.Over);
		editor.putInt("j", mGR.mBall.img);
		
		editor.putInt("k", mGR.mScore);
		editor.putInt("l", mGR.mHScore);
		editor.putInt("z", mGR.mTotal);
		editor.putBoolean("x", mGR.addFree);
		editor.putBoolean("c", mGR.SingUpadate);
		for(int i =0;i<mGR.mAchiUnlock.length;i++)
			editor.putBoolean("v"+i, mGR.mAchiUnlock[i]);
		editor.commit();
	}

	void resume() {
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = prefs.getInt("screen", M.GAMELOGO);
		M.setValue = prefs.getBoolean("setValue", M.setValue);
		
		
		M.GameScreen = prefs.getInt("q", M.GAMELOGO);
		M.setValue = prefs.getBoolean("w", M.setValue);
		
		for(int i =0;i<mGR.mFollower.length;i++){
			mGR.mFollower[i].x = prefs.getFloat("e"+i, mGR.mFollower[i].x);
			mGR.mFollower[i].y = prefs.getFloat("r"+i, mGR.mFollower[i].y);
		}
		for(int i =0;i<mGR.mLine.length;i++){
			mGR.mLine[i].x = prefs.getFloat("t"+i, mGR.mLine[i].x);
			mGR.mLine[i].y = prefs.getFloat("y"+i, mGR.mLine[i].y);
			mGR.mLine[i].r = prefs.getFloat("u"+i, mGR.mLine[i].r);
			mGR.mLine[i].g = prefs.getFloat("i"+i, mGR.mLine[i].g);
			mGR.mLine[i].b = prefs.getFloat("o"+i, mGR.mLine[i].b);
		}
		
		mGR.mBall.x = prefs.getFloat("p", mGR.mBall.x);
		mGR.mBall.y = prefs.getFloat("a", mGR.mBall.y);
		mGR.mBall.vy = prefs.getFloat("s", mGR.mBall.vy);
		
		mGR.mBall.isTuch = prefs.getBoolean("d", mGR.mBall.isTuch);
		mGR.mBall.isStart = prefs.getBoolean("f", mGR.mBall.isStart);
		mGR.mBall.isRandom = prefs.getBoolean("g", mGR.mBall.isRandom);
		mGR.mBall.Over = (byte)prefs.getInt("h", mGR.mBall.Over);
		mGR.mBall.img = prefs.getInt("j", mGR.mBall.img);
		
		mGR.mScore = prefs.getInt("k", mGR.mScore);
		mGR.mHScore = prefs.getInt("l", mGR.mHScore);
		mGR.mTotal = prefs.getInt("z", mGR.mTotal);
		mGR.addFree = prefs.getBoolean("x", mGR.addFree);
		mGR.SingUpadate = prefs.getBoolean("c", mGR.SingUpadate);
		for(int i =0;i<mGR.mAchiUnlock.length;i++)
			mGR.mAchiUnlock[i] = prefs.getBoolean("v"+i, mGR.mAchiUnlock[i]);
		
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
	
	void Achivment(){
		if(mGR.mScore > 19 && !mGR.mAchiUnlock[0]){
			mGR.mAchiUnlock[0] = true;
			UnlockAchievement(M.ACHIV[0]);
		}
		if(mGR.mScore > 49 && !mGR.mAchiUnlock[1]){
			mGR.mAchiUnlock[1] = true;
			UnlockAchievement(M.ACHIV[1]);
		}
		if(mGR.mScore > 99 && !mGR.mAchiUnlock[2]){
			mGR.mAchiUnlock[2] = true;
			UnlockAchievement(M.ACHIV[2]);
		}
		if(mGR.mTotal > 499 && !mGR.mAchiUnlock[3]){
			mGR.mAchiUnlock[3] = true;
			UnlockAchievement(M.ACHIV[3]);
		}
		if(mGR.mTotal > 1999 && !mGR.mAchiUnlock[4]){
			mGR.mAchiUnlock[4] = true;
			UnlockAchievement(M.ACHIV[4]);
		}
		Submitscore(R.string.leaderboard_best_score, mGR.mHScore);
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
	
}