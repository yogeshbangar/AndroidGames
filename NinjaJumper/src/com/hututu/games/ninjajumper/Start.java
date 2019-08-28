package com.hututu.games.ninjajumper;

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
		M.stop(GameRenderer.mContext);
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
				M.stop(GameRenderer.mContext);
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
		if(mGR.mInApp!=null)
			mGR.mInApp.onDestroy();
	}


	void pause() {
		if(M.GameScreen == M.GAMEPLAY)
			M.GameScreen = M.GAMEPAUSE;
		M.stop(GameRenderer.mContext);
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putInt("screen", M.GameScreen);
		editor.putBoolean("setValue", M.setValue);
		editor.putBoolean("addFree", mGR.addFree);
		
		editor.putBoolean("SingUpadate", mGR.SingUpadate);
		editor.putBoolean("gameStart", mGR.gameStart);
		for(int i=0;i<mGR.mAchiUnlock.length;i++)
			editor.putBoolean("mAchiUnlock"+i, mGR.mAchiUnlock[i]);
		
		editor.putInt("mScore", mGR.mScore);
		editor.putInt("mHScore", mGR.mHScore);
		editor.putInt("mTotal", mGR.mTotal);
		editor.putInt("bgNo", mGR.bgNo);
		editor.putInt("OverCounter", mGR.OverCounter);
		editor.putInt("NoPower", mGR.NoPower);
		
		for(int i = 0;i<mGR.mSpike.length;i++){
			for(int j = 0;j<mGR.mSpike[i].length;j++){
				editor.putBoolean(i+"mSpiker"+j, mGR.mSpike[i][j].rShow);
				editor.putFloat(i+"mSpikex"+j, mGR.mSpike[i][j].x);
			}
		}
		{
			editor.putInt("mPlayerpower", mGR.mPlayer.power);
			editor.putInt("mPlayercounter", mGR.mPlayer.counter);
			
			editor.putFloat("mPlayerx", mGR.mPlayer.x);
			editor.putFloat("mPlayery", mGR.mPlayer.y);
			editor.putFloat("mPlayervx", mGR.mPlayer.vx);
			editor.putFloat("mPlayervy", mGR.mPlayer.vy);
			
			for(int i = 0;i<mGR.mPlayer.x1.length;i++){
				editor.putFloat(i+"mPlayerx1", mGR.mPlayer.x1[i]);
				editor.putFloat(i+"mPlayery1", mGR.mPlayer.y1[i]);
				editor.putFloat(i+"mPlayerz1", mGR.mPlayer.z1[i]);
			}
		}
		
		
		editor.putInt("mPowerpowerNo", mGR.mPower.powerNo);
		editor.putFloat("mPowerx", mGR.mPower.x);
		editor.putFloat("mPowery", mGR.mPower.y);
		
		editor.commit();
	}

	void resume() {
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = prefs.getInt("screen", M.GAMELOGO);
		M.setValue = prefs.getBoolean("setValue", M.setValue);
		mGR.addFree = prefs.getBoolean("addFree", mGR.addFree);
		
		
		mGR.SingUpadate = prefs.getBoolean("SingUpadate", mGR.SingUpadate);
		mGR.gameStart = prefs.getBoolean("gameStart", mGR.gameStart);
		for(int i=0;i<mGR.mAchiUnlock.length;i++)
			mGR.mAchiUnlock[i] = prefs.getBoolean("mAchiUnlock"+i, mGR.mAchiUnlock[i]);
		
		mGR.mScore = prefs.getInt("mScore", mGR.mScore);
		mGR.mHScore = prefs.getInt("mHScore", mGR.mHScore);
		mGR.mTotal = prefs.getInt("mTotal", mGR.mTotal);
		mGR.bgNo = prefs.getInt("bgNo", mGR.bgNo);
		mGR.OverCounter = prefs.getInt("OverCounter", mGR.OverCounter);
		mGR.NoPower = prefs.getInt("NoPower", mGR.NoPower);
		
		for(int i = 0;i<mGR.mSpike.length;i++){
			for(int j = 0;j<mGR.mSpike[i].length;j++){
				mGR.mSpike[i][j].rShow = prefs.getBoolean(i+"mSpiker"+j, mGR.mSpike[i][j].rShow);
				mGR.mSpike[i][j].x = prefs.getFloat(i+"mSpikex"+j, mGR.mSpike[i][j].x);
			}
		}
		{
			mGR.mPlayer.power = prefs.getInt("mPlayerpower", mGR.mPlayer.power);
			mGR.mPlayer.counter = prefs.getInt("mPlayercounter", mGR.mPlayer.counter);
			
			mGR.mPlayer.x = prefs.getFloat("mPlayerx", mGR.mPlayer.x);
			mGR.mPlayer.y = prefs.getFloat("mPlayery", mGR.mPlayer.y);
			mGR.mPlayer.vx = prefs.getFloat("mPlayervx", mGR.mPlayer.vx);
			mGR.mPlayer.vy = prefs.getFloat("mPlayervy", mGR.mPlayer.vy);
			
			for(int i = 0;i<mGR.mPlayer.x1.length;i++){
				mGR.mPlayer.x1[i] = prefs.getFloat(i+"mPlayerx1", mGR.mPlayer.x1[i]);
				mGR.mPlayer.y1[i] = prefs.getFloat(i+"mPlayery1", mGR.mPlayer.y1[i]);
				mGR.mPlayer.z1[i] = prefs.getFloat(i+"mPlayerz1", mGR.mPlayer.z1[i]);
			}
		}
		
		
		mGR.mPower.powerNo = prefs.getInt("mPowerpowerNo", mGR.mPower.powerNo);
		mGR.mPower.x = prefs.getFloat("mPowerx", mGR.mPower.x);
		mGR.mPower.y = prefs.getFloat("mPowery", mGR.mPower.y);
		
		
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
		if (!mGR.mInApp.mHelper.handleActivityResult(requestCode,resultCode, data)) {
			super.onActivityResult(requestCode, resultCode, data);
		} else {
		}
	}
	
}