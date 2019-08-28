package com.fun2sh.penguinfreejump;
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
			case M.GAMEMENU:
				Exit();
				break;
			case M.GAMEPLAY:
			case M.GAMESURVIVAL:
				M.GameScreen = M.GAMEPAUSE;
				mGR.mTime = System.currentTimeMillis() - mGR.mTime;
				M.stopplay();
				break;
			default:
				M.GameScreen = M.GAMEMENU;
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

	void pause() {
		M.stop();
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		if (M.GameScreen == M.GAMESURVIVAL || M.GameScreen == M.GAMEPLAY) {
			mGR.mTime = System.currentTimeMillis() - mGR.mTime;
			M.GameScreen = M.GAMEPAUSE;
		}

		Editor editor = prefs.edit();
		editor.putInt("a", M.GameScreen);
		editor.putBoolean("b", M.setValue);

		for (int i = 0; i < mGR.mHardle.length; i++) {
			editor.putInt("c" + i, mGR.mHardle[i].worng);
			editor.putInt("d" + i, mGR.mHardle[i].jump);
			for (int j = 0; j < mGR.mHardle[i].img.length; j++)
				editor.putInt(j + "e" + i, mGR.mHardle[i].img[j]);
			editor.putFloat("f", mGR.mHardle[i].y);
		}

		editor.putBoolean("g", mGR.mPlayer.isWin);
		editor.putInt("h", mGR.mPlayer.img);
		editor.putInt("i", mGR.mPlayer.row);
		editor.putInt("j", mGR.mPlayer.mCond);
		editor.putFloat("k", mGR.mPlayer.x);
		editor.putFloat("l", mGR.mPlayer.y);
		editor.putFloat("m", mGR.mPlayer.z);
		editor.putFloat("n", mGR.mPlayer.vx);
		editor.putFloat("o", mGR.mPlayer.vy);
		editor.putFloat("p", mGR.mPlayer.vz);

		editor.putBoolean("q", mGR.addFree);
		editor.putBoolean("r", mGR.SingUpadate);
		for (int i = 0; i < mGR.mAchiUnlock.length; i++)
			editor.putBoolean("s" + i, mGR.mAchiUnlock[i]);

		editor.putInt("t", mGR.mScore);
		editor.putInt("u", mGR.mLevel);
		editor.putInt("v", mGR.m1Best);
		editor.putInt("w", mGR.m2Best);

		editor.putLong("x", mGR.m0Best);
		editor.putLong("y", mGR.mTime);

		editor.putFloat("z", mGR.mBG);

		editor.commit();
	}

	void resume() {
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = prefs.getInt("a", M.GAMELOGO);
		M.setValue = prefs.getBoolean("b", M.setValue);

		for (int i = 0; i < mGR.mHardle.length; i++) {
			mGR.mHardle[i].worng = prefs.getInt("c" + i, mGR.mHardle[i].worng);
			mGR.mHardle[i].jump = prefs.getInt("d" + i, mGR.mHardle[i].jump);
			for (int j = 0; j < mGR.mHardle[i].img.length; j++)
				mGR.mHardle[i].img[j] = prefs.getInt(j + "e" + i,mGR.mHardle[i].img[j]);
			mGR.mHardle[i].y = prefs.getFloat("f", mGR.mHardle[i].y);
		}

		mGR.mPlayer.isWin = prefs.getBoolean("g", mGR.mPlayer.isWin);
		mGR.mPlayer.img = prefs.getInt("h", mGR.mPlayer.img);
		mGR.mPlayer.row = prefs.getInt("i", mGR.mPlayer.row);
		mGR.mPlayer.mCond = prefs.getInt("j", mGR.mPlayer.mCond);
		mGR.mPlayer.x = prefs.getFloat("k", mGR.mPlayer.x);
		mGR.mPlayer.y = prefs.getFloat("l", mGR.mPlayer.y);
		mGR.mPlayer.z = prefs.getFloat("m", mGR.mPlayer.z);
		mGR.mPlayer.vx = prefs.getFloat("n", mGR.mPlayer.vx);
		mGR.mPlayer.vy = prefs.getFloat("o", mGR.mPlayer.vy);
		mGR.mPlayer.vz = prefs.getFloat("p", mGR.mPlayer.vz);

		mGR.addFree = prefs.getBoolean("q", mGR.addFree);
		mGR.SingUpadate = prefs.getBoolean("r", mGR.SingUpadate);
		for (int i = 0; i < mGR.mAchiUnlock.length; i++)
			mGR.mAchiUnlock[i] = prefs.getBoolean("s" + i, mGR.mAchiUnlock[i]);

		mGR.mScore = prefs.getInt("t", mGR.mScore);
		mGR.mLevel = prefs.getInt("u", mGR.mLevel);
		mGR.m1Best = prefs.getInt("v", mGR.m1Best);
		mGR.m2Best = prefs.getInt("w", mGR.m2Best);

		mGR.m0Best = prefs.getLong("x", mGR.m0Best);
		mGR.mTime = prefs.getLong("y", mGR.mTime);

		mGR.mBG = prefs.getFloat("z", mGR.mBG);

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