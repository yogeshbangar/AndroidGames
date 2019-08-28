package com.ultimate.frog.fun;
//rh34 
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

public class Start extends BaseGameActivity 
{
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
	    callAdds();
	}

	@Override
	public void onPause() {
		M.stop();
		pause();
		super.onPause();
	}

	@Override
	public void onResume() {
		resume();
		super.onResume();
		// view.onResume();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			switch (M.GameScreen) {
			case M.GAMELOGO:
				break;
			case M.GAMESPLASH:
			case M.GAMEPAUSE:
			case M.GAMEWIN:
			case M.GAMEOVER:
				M.GameScreen = M.GAMEMENU;
				break;
			case M.GAMEMENU:
				Exit();
				break;
			case M.GAMEPLAY:
			case M.GAMESURVIVAL:
				M.GameScreen = M.GAMEPAUSE;
				mGR.mTime = System.currentTimeMillis() - mGR.mTime;
				M.Stopplay();
				break;
			}
			return false;
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
		M.stop();
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		if(M.GameScreen == M.GAMESURVIVAL || M.GameScreen == M.GAMEPLAY){
			mGR.mTime = System.currentTimeMillis() - mGR.mTime;
			M.GameScreen = M.GAMEPAUSE;
		}
	    Editor editor = prefs.edit();
	    editor.putInt("screen", M.GameScreen);
	    editor.putBoolean("setValue", M.setValue);
	    editor.commit();
	}
	void resume()
	{
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = prefs.getInt("screen", M.GAMELOGO);
		M.setValue = prefs.getBoolean("setValue", M.setValue);
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
			if(mGR.m0Best > 0 && mGR.mLevel == 0)
				Submitscore(R.string.leaderboard_classic_mode, mGR.m0Best);
			if(mGR.m1Best > 0 && mGR.mLevel == 1)
				Submitscore(R.string.leaderboard_time_travel, mGR.m1Best);
			if(mGR.m2Best > 0 && mGR.mLevel == 2)
				Submitscore(R.string.leaderboard_survival, mGR.m2Best);
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
//	adob air flex
	void Achivment(){
		if(mGR.mScore > 49 && !mGR.mAchiUnlock[0] && mGR.mLevel == 1){
			mGR.mAchiUnlock[0] = true;
			UnlockAchievement(M.ACHIV[0]);
		}
		if(mGR.mScore > 99 && !mGR.mAchiUnlock[1] && mGR.mLevel == 1){
			mGR.mAchiUnlock[1] = true;
			UnlockAchievement(M.ACHIV[1]);
		}
		if(mGR.mTime < 10000 && !mGR.mAchiUnlock[2] && mGR.mLevel == 0){
			mGR.mAchiUnlock[2] = true;
			UnlockAchievement(M.ACHIV[2]);
		}
		if(mGR.mScore > 199 && !mGR.mAchiUnlock[3] && mGR.mLevel == 2){
			mGR.mAchiUnlock[3] = true;
			UnlockAchievement(M.ACHIV[3]);
		}
		if(mGR.mScore > 499 && !mGR.mAchiUnlock[4] && mGR.mLevel == 2){
			mGR.mAchiUnlock[4] = true;
			UnlockAchievement(M.ACHIV[4]);
		}
		if(mGR.m0Best > 0 && mGR.mLevel == 0)
			Submitscore(R.string.leaderboard_classic_mode, mGR.m0Best);
		if(mGR.m1Best > 0 && mGR.mLevel == 1)
			Submitscore(R.string.leaderboard_time_travel, mGR.m1Best);
		if(mGR.m2Best > 0 && mGR.mLevel == 2)
			Submitscore(R.string.leaderboard_survival, mGR.m2Best);
	}
}