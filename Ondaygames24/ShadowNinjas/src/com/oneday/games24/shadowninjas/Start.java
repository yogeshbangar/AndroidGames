package com.oneday.games24.shadowninjas;

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
	boolean fromCreat = false; 
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
//		CONTEXT	=	this;
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
	    if (!getSharedPreferences("X", MODE_PRIVATE).getBoolean("addFree",mGR.addFree)){
			callAdds();
			load();
			fromCreat = true;
	    }
	}
//	public static Context getContext() {
//	        return CONTEXT;
//	}
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
			if (M.GameScreen == M.GAMEPLAY)
				M.GameScreen = M.GAMEPAUS;
			else if (M.GameScreen == M.GAMEMENU) {
				Exit();
			} else {
				M.GameScreen = M.GAMEMENU;
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
		if (M.GameScreen == M.GAMEPLAY)
			M.GameScreen = M.GAMEPAUS;
		M.stop();
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
	    Editor editor = prefs.edit();
	    editor.putInt("screen", M.GameScreen);
	    editor.putBoolean("setValue", M.setValue);
	    editor.putBoolean("addFree", mGR.addFree);

	    editor.putBoolean("SingUpadate", mGR.SingUpadate);
		for(int i=0;i<mGR.mAchiUnlock.length;i++)
			editor.putBoolean("mAchiUnlock"+i, mGR.mAchiUnlock[i]);
		
	    
	    editor.putInt("mScore", mGR.mScore);
	    editor.putInt("mTotal", mGR.mTotal);
	    editor.putInt("mHScore", mGR.mHScore);
	    editor.putInt("gameOver", mGR.gameOver);
	    for(int i =0;i<mGR.mWay.length;i++)
	    {
	    	editor.putFloat("mWayx"+i, mGR.mWay[i].x);
	    	editor.putFloat("mWayy"+i, mGR.mWay[i].y);
	    	editor.putFloat("mWayr"+i, mGR.mWay[i].r);
	    	editor.putFloat("mWayg"+i, mGR.mWay[i].g);
	    	editor.putFloat("mWayb"+i, mGR.mWay[i].b);
	    	editor.putFloat("mWaysx"+i, mGR.mWay[i].sx);
	    	
	    }
	    {
	    	editor.putInt("mPlyeract", mGR.mPlyer.act);
	    	editor.putInt("mPlyercount", mGR.mPlyer.count);
	    	editor.putInt("mPlyerJump", mGR.mPlyer.Jump);
	    	
	    	editor.putFloat("mPlyerx", mGR.mPlyer.x);
	    	editor.putFloat("mPlyery", mGR.mPlyer.y);
	    	editor.putFloat("mPlyervy", mGR.mPlyer.vy);
	    }
	    for(int i =0;i<mGR.mOpp.length;i++)
	    {
	    	editor.putFloat("mOppx"+i, mGR.mOpp[i].x);
	    	editor.putFloat("mOppy"+i, mGR.mOpp[i].y);
	    	editor.putFloat("mOppay"+i, mGR.mOpp[i].ay);
	    	editor.putFloat("mOppvy"+i, mGR.mOpp[i].vy);
	    	editor.putInt("mOppact"+i, mGR.mOpp[i].act);
	    	editor.putInt("mOpphit"+i, mGR.mOpp[i].hit);
	    	editor.putInt("mOppcount"+i, mGR.mOpp[i].count);
	    }
	    
	    editor.commit();
	}
	void resume()
	{
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = prefs.getInt("screen", M.GAMELOGO);
		M.setValue = prefs.getBoolean("setValue", M.setValue);
		mGR.addFree = prefs.getBoolean("addFree", mGR.addFree);
		mGR.SingUpadate = prefs.getBoolean("SingUpadate", mGR.SingUpadate);
		for(int i=0;i<mGR.mAchiUnlock.length;i++)
			mGR.mAchiUnlock[i] = prefs.getBoolean("mAchiUnlock"+i, mGR.mAchiUnlock[i]);
		
		mGR.mScore = prefs.getInt("mScore", mGR.mScore);
		mGR.mTotal = prefs.getInt("mTotal", mGR.mTotal);
		mGR.mHScore = prefs.getInt("mHScore", mGR.mHScore);
		mGR.gameOver = prefs.getInt("gameOver", mGR.gameOver);
	    for(int i =0;i<mGR.mWay.length;i++)
	    {
	    	mGR.mWay[i].x = prefs.getFloat("mWayx"+i, mGR.mWay[i].x);
	    	mGR.mWay[i].y = prefs.getFloat("mWayy"+i, mGR.mWay[i].y);
	    	mGR.mWay[i].r = prefs.getFloat("mWayr"+i, mGR.mWay[i].r);
	    	mGR.mWay[i].g = prefs.getFloat("mWayg"+i, mGR.mWay[i].g);
	    	mGR.mWay[i].b = prefs.getFloat("mWayb"+i, mGR.mWay[i].b);
	    	mGR.mWay[i].sx = prefs.getFloat("mWaysx"+i, mGR.mWay[i].sx);
	    	
	    }
	    {
	    	mGR.mPlyer.act = prefs.getInt("mPlyeract", mGR.mPlyer.act);
	    	mGR.mPlyer.count = prefs.getInt("mPlyercount", mGR.mPlyer.count);
	    	mGR.mPlyer.Jump = prefs.getInt("mPlyerJump", mGR.mPlyer.Jump);
	    	
	    	mGR.mPlyer.x = prefs.getFloat("mPlyerx", mGR.mPlyer.x);
	    	mGR.mPlyer.y = prefs.getFloat("mPlyery", mGR.mPlyer.y);
	    	mGR.mPlyer.vy = prefs.getFloat("mPlyervy", mGR.mPlyer.vy);
	    }
	    for(int i =0;i<mGR.mOpp.length;i++)
	    {
	    	mGR.mOpp[i].x = prefs.getFloat("mOppx"+i, mGR.mOpp[i].x);
	    	mGR.mOpp[i].y = prefs.getFloat("mOppy"+i, mGR.mOpp[i].y);
	    	mGR.mOpp[i].ay = prefs.getFloat("mOppay"+i, mGR.mOpp[i].ay);
	    	mGR.mOpp[i].vy = prefs.getFloat("mOppvy"+i, mGR.mOpp[i].vy);
	    	mGR.mOpp[i].act = prefs.getInt("mOppact"+i, mGR.mOpp[i].act);
	    	mGR.mOpp[i].hit = prefs.getInt("mOpphit"+i, mGR.mOpp[i].hit);
	    	mGR.mOpp[i].count = prefs.getInt("mOppcount"+i, mGR.mOpp[i].count);
	    }
	    
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
			System.out.println("~~~~loadInter~~~~~~~~~");
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
		if (!mGR.SingUpadate) {
			for (int i = 0; i < mGR.mAchiUnlock.length; i++) {
				if (mGR.mAchiUnlock[i]) {
					UnlockAchievement(M.ACHIV[i]);
				}
			}
			Submitscore(R.string.leaderboard_best_score, mGR.mHScore);
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
}