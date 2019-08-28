package com.hututu.game.stuntracingcar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class Start extends Activity {
	int _keyCode = 0;
	AdView adView = null;
//	AdView adHouse = null;//AdHouse
	GameRenderer mGR = null;
	private static Context CONTEXT;
	private InterstitialAd interstitial;
	boolean fromStart = false;
	void callAdds() {
		{
			adView = (AdView) this.findViewById(R.id.addgame);
			AdRequest adRequest = new AdRequest.Builder().build();
			adView.loadAd(adRequest);
			adView.setAdListener(new AdListener() {
				public void onAdLoaded() {
					adView.bringToFront();
				}
			});
		}
//		{
//			adHouse = (AdView) this.findViewById(R.id.advhouse);
//			AdRequest adRequest = new AdRequest.Builder().build();
//			adHouse.loadAd(adRequest);
//			adHouse.setAdListener(new AdListener() {
//				public void onAdLoaded() {
//					adHouse.bringToFront();
//				}
//			});
//		}
	}

	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) {
		CONTEXT = this;
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);// OnlyOneChange
		setContentView(R.layout.game);
		interstitial = new InterstitialAd(this);
		interstitial.setAdUnitId(getString(R.string.INTERSTITIAL_ID));
		WindowManager mWinMgr = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		M.ScreenWidth = mWinMgr.getDefaultDisplay().getWidth();
		M.ScreenHieght = mWinMgr.getDefaultDisplay().getHeight();
		mGR = new GameRenderer(this);
		VortexView glSurface = (VortexView) findViewById(R.id.vortexview); 
		glSurface.setRenderer(mGR);
		glSurface.showRenderer(mGR);
		callAdds();
		load();
		fromStart = true;
	}

	public static Context getContext() {
		return CONTEXT;
	}

	@Override
	public void onPause() {
		M.stop(CONTEXT);
		pause();
		super.onPause();
	}

	@Override
	public void onResume() {
		resume();
		super.onResume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// Log.d("----------------=>  "+keyCode,"   -----------    ");
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			switch (M.GameScreen) {
			case M.GAMEPLAY:
				M.GameScreen = M.GAMEPAUSE;
				M.stop(CONTEXT);
				break;
			case M.GAMELOGO:
			case M.GAMESPLASH:
			case M.GAMEADD:
			case M.GAMEPAUSE:
			case M.GAMEMENU:
				get();
				break;
			default:
				M.GameScreen = M.GAMEMENU;
				break;
			}
			return false;
		}
		_keyCode = keyCode;
		return super.onKeyDown(keyCode, event);
	}

	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK)
			return false;
		_keyCode = 0;
		return super.onKeyUp(keyCode, event);
	}

	void pause() {
		M.stop(GameRenderer.mContext);
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		Editor editor = prefs.edit();
		if (M.GameScreen == M.GAMETIMEUP)
			M.GameScreen = M.GAMEOVER;
		if (M.GameScreen == M.GAMEPLAY)
			M.GameScreen = M.GAMEPAUSE;
		editor.putInt("screen", M.GameScreen);
		editor.putBoolean("setValue", M.setValue);

		for (int i = 0; i < mGR.mStrip.length; i++) {
			editor.putFloat("mStripx" + i, mGR.mStrip[i].x);
			editor.putFloat("mStripy" + i, mGR.mStrip[i].y);
			editor.putFloat("mStripz" + i, mGR.mStrip[i].z);
			editor.putFloat("mStripvx" + i, mGR.mStrip[i].vx);
			editor.putFloat("mStripvy" + i, mGR.mStrip[i].vy);
			editor.putFloat("mStripvz" + i, mGR.mStrip[i].vz);
		}
		for (int i = 0; i < mGR.mCar.length; i++) {
			editor.putFloat("mCarx" + i, mGR.mCar[i].x);
			editor.putFloat("mCary" + i, mGR.mCar[i].y);
			editor.putFloat("mCarz" + i, mGR.mCar[i].z);
			editor.putFloat("mCarvx" + i, mGR.mCar[i].vx);
			editor.putFloat("mCarvy" + i, mGR.mCar[i].vy);
			editor.putFloat("mCarvz" + i, mGR.mCar[i].vz);
			editor.putFloat("mCarvt" + i, mGR.mCar[i].temp);
			for (int j = 0; j < mGR.mCar[i].cars.length; j++) {
				editor.putInt(i + "cars" + j, mGR.mCar[i].cars[j]);
			}
		}
		editor.putFloat("mPlayerx", mGR.mPlayer.x);
		editor.putFloat("mPlayery", mGR.mPlayer.y);
		editor.putFloat("mPlayervx", mGR.mPlayer.vx);
		editor.putFloat("mPlayervy", mGR.mPlayer.vx);
		editor.putFloat("mPlayerinitY", mGR.mPlayer.initY);

		editor.putInt("mPlayercollide", mGR.mPlayer.collide);
		editor.putInt("mPlayerpos", mGR.mPlayer.pos);
		editor.putBoolean("mPlayerisOnair", mGR.mPlayer.isOnair);

		editor.putInt("Carcounter", mGR.Carcounter);
		editor.putInt("mTime", mGR.mTime);
		editor.putInt("mScore", mGR.mScore);
		editor.putInt("mScore", 10000);
		editor.putInt("mLevel", mGR.mLevel);
		editor.putInt("mLevelUpCounter", mGR.mLevelUpCounter);

		editor.putFloat("mTargetDistance", mGR.mTargetDistance);
		editor.putFloat("mDistance", mGR.mDistance);
		System.out.println("~~~~~~~~~~#1#~~~~~~~~~~~~     " + mGR.mStime
				+ "    ~~~~~~~~~~##~~~~~~~~~~~");
		if (mGR.mStime > 9999999)
			mGR.mStime -= System.currentTimeMillis();
		editor.putLong("mStime", mGR.mStime);
		System.out.println("~~~~~~~~~~#2#~~~~~~~~~~~~     " + mGR.mStime
				+ "    ~~~~~~~~~~##~~~~~~~~~~~");

		editor.commit();
	}

	void resume() {
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = prefs.getInt("screen", M.GAMELOGO);
		M.setValue = prefs.getBoolean("setValue", M.setValue);
		for (int i = 0; i < mGR.mStrip.length; i++) {
			mGR.mStrip[i].x = prefs.getFloat("mStripx" + i, mGR.mStrip[i].x);
			mGR.mStrip[i].y = prefs.getFloat("mStripy" + i, mGR.mStrip[i].y);
			mGR.mStrip[i].z = prefs.getFloat("mStripz" + i, mGR.mStrip[i].z);
			mGR.mStrip[i].vx = prefs.getFloat("mStripvx" + i, mGR.mStrip[i].vx);
			mGR.mStrip[i].vy = prefs.getFloat("mStripvy" + i, mGR.mStrip[i].vy);
			mGR.mStrip[i].vz = prefs.getFloat("mStripvz" + i, mGR.mStrip[i].vz);
		}
		for (int i = 0; i < mGR.mCar.length; i++) {
			mGR.mCar[i].x = prefs.getFloat("mCarx" + i, mGR.mCar[i].x);
			mGR.mCar[i].y = prefs.getFloat("mCary" + i, mGR.mCar[i].y);
			mGR.mCar[i].z = prefs.getFloat("mCarz" + i, mGR.mCar[i].z);
			mGR.mCar[i].vx = prefs.getFloat("mCarvx" + i, mGR.mCar[i].vx);
			mGR.mCar[i].vy = prefs.getFloat("mCarvy" + i, mGR.mCar[i].vy);
			mGR.mCar[i].vz = prefs.getFloat("mCarvz" + i, mGR.mCar[i].vz);
			mGR.mCar[i].temp = prefs.getFloat("mCarvt" + i, mGR.mCar[i].temp);
			for (int j = 0; j < mGR.mCar[i].cars.length; j++) {
				mGR.mCar[i].cars[j] = prefs.getInt(i + "cars" + j,
						mGR.mCar[i].cars[j]);
			}
		}
		mGR.mPlayer.x = prefs.getFloat("mPlayerx", mGR.mPlayer.x);
		mGR.mPlayer.y = prefs.getFloat("mPlayery", mGR.mPlayer.y);
		mGR.mPlayer.vx = prefs.getFloat("mPlayervx", mGR.mPlayer.vx);
		mGR.mPlayer.vy = prefs.getFloat("mPlayervy", mGR.mPlayer.vy);
		mGR.mPlayer.initY = prefs.getFloat("mPlayerinitY", mGR.mPlayer.initY);

		mGR.mPlayer.collide = prefs.getInt("mPlayercollide",
				mGR.mPlayer.collide);
		mGR.mPlayer.pos = prefs.getInt("mPlayerpos", mGR.mPlayer.pos);
		mGR.mPlayer.isOnair = prefs.getBoolean("mPlayerisOnair",
				mGR.mPlayer.isOnair);

		mGR.Carcounter = prefs.getInt("Carcounter", mGR.Carcounter);
		mGR.mTime = prefs.getInt("mTime", mGR.mTime);
		mGR.mScore = prefs.getInt("mScore", mGR.mScore);
		mGR.mHScore = prefs.getInt("mHScore", mGR.mHScore);
		mGR.mLevel = prefs.getInt("mLevel", mGR.mLevel);
		mGR.mLevelUpCounter = prefs.getInt("mLevelUpCounter",
				mGR.mLevelUpCounter);

		mGR.mTargetDistance = prefs.getFloat("mTargetDistance",
				mGR.mTargetDistance);
		mGR.mDistance = prefs.getFloat("mDistance", mGR.mDistance);
		mGR.mStime = prefs.getLong("mStime", mGR.mStime);
		System.out.println("~~~~~~~~~~Resume~~~~~~~~~~~~     " + mGR.mStime
				+ "    ~~~~~~~~~~##~~~~~~~~~~~");
		// mGR.mStime +=System.currentTimeMillis();
		mGR.resumeCounter = 0;
	}

	void get() {
		new AlertDialog.Builder(this)
				.setIcon(R.drawable.icon)
				.setTitle("Do you want to exit?")
				.setPositiveButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

					}
				})
				.setNegativeButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								mGR.root.Counter = 0;
								finish();
								M.GameScreen = M.GAMELOGO;
							}
						}).show();
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
}