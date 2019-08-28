package com.oneday.games24.extrememotoracer;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.games.Games;


import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class Start extends Activity{
	static Context CONTEXT;
	int _keyCode = 0;
	AdView adView,adViewF;//,adHouse;
	GameRenderer mGR = null;
	private InterstitialAd interstitialAd;
	@SuppressWarnings("deprecation")
	void callAdds()
	{
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
		{
			adViewF = (AdView) this.findViewById(R.id.advhouse);
			AdRequest adRequest = new AdRequest.Builder().build();
			adViewF.loadAd(adRequest);
			adViewF.setAdListener(new AdListener() {
				public void onAdLoaded() {
					adViewF.bringToFront();
				}
			});
		}
	}
	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);//OnlyOneChange
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		
		interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId(getString(R.string.INTERSTITIAL_ID));
		
		
		CONTEXT = this;
		WindowManager mWinMgr = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		M.ScreenWidth = mWinMgr.getDefaultDisplay().getWidth();
		M.ScreenHieght = mWinMgr.getDefaultDisplay().getHeight();
		mGR = new GameRenderer(this);
		VortexView glSurface = (VortexView) findViewById(R.id.vortexview); 
		glSurface.setRenderer(mGR);
		glSurface.showRenderer(mGR);
		callAdds();
	}
	public static Context getContext() {
		return CONTEXT;
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
		System.out.println("1111~~~~"+M.GameScreen);
		super.onResume();
	}
	@Override
	public void onStart() {
		super.onStart();
		System.out.println("jflkjdsklfjdklsj~~~~"+M.GameScreen);
		
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			switch (M.GameScreen) {
			case M.GameLogo:
				finish();
				break;
			case M.GameADD:
			case M.GameSplash:
			case M.GameMenu:
				Exit();
				break;
			case M.GamePause:
			case M.GameOver:
//			case M.GameSetting:
			case M.GameChallenge:
			case M.GameHelp:
			case M.GameAbout:
			case M.GameCarSelection:
				if (M.setBG)
					M.Splashplay(GameRenderer.mContext, R.drawable.splash);
				else
					M.loopSoundStop();
				M.GameScreen = M.GameMenu;
				break;
			case M.GamePlay:
				M.loopSoundStop();
				M.GameScreen = M.GamePause;
				break;
			case M.GameBulk:
				M.Splashplay(GameRenderer.mContext, R.drawable.splash);
				M.GameScreen = M.GameCarSelection;
				break;
			}
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return false;
		}
		return super.onKeyUp(keyCode, event);
	}

	public void onDestroy() {
		super.onDestroy();
	}

	void pause() {
		
		M.stop();
		M.loopSoundStop();
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		Editor editor = prefs.edit();
		if (M.GameScreen == M.GamePlay)
			M.GameScreen = M.GamePause;
		editor.putInt("screen", M.GameScreen);
		editor.putBoolean("setValue", M.setValue);
		editor.putBoolean("setBG", M.setBG);
//		editor.putBoolean("addFree", addFree);

		editor.putInt("RoadBlocksNext", RoadBlock.sNext);
		editor.putInt("BG", RoadBlock.BG);

		for (int i = 0; i < mGR.mRow.length; i++) {
			editor.putFloat("mRowy" + i, mGR.mRow[i].y);
			editor.putInt("mRowId" + i, mGR.mRow[i].Id);
			editor.putInt("mRowobj" + i, mGR.mRow[i].obj);
			editor.putInt("mRowbg" + i, mGR.mRow[i].bg);
		}
		{
			editor.putFloat("mPlayerax", mGR.mPlayer.ax);
			editor.putFloat("mPlayerx", mGR.mPlayer.x);
			editor.putFloat("mPlayery", mGR.mPlayer.y);
			editor.putFloat("mPlayervx", mGR.mPlayer.vx);
			editor.putFloat("mPlayervy", mGR.mPlayer.vy);
			editor.putFloat("mPlayerMinSpeed", mGR.mPlayer.MinSpeed);
			editor.putFloat("mPlayerBost", mGR.mPlayer.Bost);

			editor.putInt("mPlayerforKeyPress", mGR.mPlayer.forKeyPress);
			editor.putInt("mPlayerSpeed", mGR.mPlayer.Speed);
			editor.putInt("mPlayerDistance", mGR.mPlayer.Distance);
			editor.putInt("mPlayerCrosscar", mGR.mPlayer.Crosscar);
			editor.putInt("mPlayerCollectCoin", mGR.mPlayer.CollectCoin);
			editor.putInt("mPlayerChallengeCom", mGR.mPlayer.ChallengeCom);
			editor.putInt("mPlayerStrenth", mGR.mPlayer.Strenth);
			editor.putInt("mPlayerDistroy", mGR.mPlayer.Distroy);

			editor.putBoolean("mPlayermTuchScr", mGR.mPlayer.mTuchScr);

			editor.putLong("mPlayerforDistance", mGR.mPlayer.forDistance);

			editor.putLong("PlayermTotalDistance", Player.mTotalTime);
			editor.putInt("PlayermTotalTime", Player.mTotalDistance);
			editor.putInt("PlayermTotalCrossCar", Player.mTotalCrossCar);
			editor.putInt("PlayermTotalCoin", Player.mTotalCoin);
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~      "+Player.mTotalCoin);
		}
		for (int i = 0; i < mGR.mOpponent.length; i++) {
			editor.putFloat("mOpponentax" + i, mGR.mOpponent[i].ax);
			editor.putFloat("mOpponentx" + i, mGR.mOpponent[i].x);
			editor.putFloat("mOpponenty" + i, mGR.mOpponent[i].y);
			editor.putFloat("mOpponentvx" + i, mGR.mOpponent[i].vx);
			editor.putFloat("mOpponentvy" + i, mGR.mOpponent[i].vy);
			editor.putInt("mOpponentno" + i, mGR.mOpponent[i].no);
		}
		for (int i = 0; i < mGR.mCoins.length; i++) {
			editor.putFloat("mCoinsx" + i, mGR.mCoins[i].x);
			editor.putFloat("mCoinsy" + i, mGR.mCoins[i].y);
		}
		for (int i = 0; i < mGR.setVeh.length; i++) {
			editor.putFloat("setVehx" + i, mGR.setVeh[i].x);
			editor.putFloat("setVehscal" + i, mGR.setVeh[i].scal);
			editor.putInt("setVehno" + i, mGR.setVeh[i].no);
			editor.putBoolean("setVehlock" + i, mGR.setVeh[i].lock);
		}
		editor.putInt("Animationcounter", Animation.counter);
		editor.putBoolean("AnimationMove", Animation.Move);

		editor.putInt("mVCount", mGR.mVCount);
		editor.putInt("mPath", mGR.mPath);
		editor.putInt("brigeCount", mGR.brigeCount);
		editor.putInt("mSel", mGR.mSel);
		editor.putInt("mCarSel", mGR.mCarSel);
		editor.putInt("StartConter", mGR.StartConter);
		editor.putInt("Challenge", mGR.Challenge);
		editor.putFloat("pause", mGR.pause);
		editor.putFloat("settingPlay", mGR.settingPlay);

		editor.putLong("GameTime", mGR.GameTime);
		editor.putLong("pauseTime", mGR.pauseTime);
		editor.putString("packagese", mGR.packages);
		editor.commit();
	}

	void resume() {
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = prefs.getInt("screen", M.GameLogo);
		M.setValue = prefs.getBoolean("setValue", M.setValue);
		M.setBG = prefs.getBoolean("setBG", M.setBG);
//		addFree  = prefs.getBoolean("addFree", addFree);
		RoadBlock.sNext = prefs.getInt("RoadBlocksNext", RoadBlock.sNext);
		RoadBlock.BG = prefs.getInt("BG", RoadBlock.BG);

		for (int i = 0; i < mGR.mRow.length; i++) {
			mGR.mRow[i].y = prefs.getFloat("mRowy" + i, mGR.mRow[i].y);
			mGR.mRow[i].Id = prefs.getInt("mRowId" + i, mGR.mRow[i].Id);
			mGR.mRow[i].obj = prefs.getInt("mRowobj" + i, mGR.mRow[i].obj);
			mGR.mRow[i].bg = prefs.getInt("mRowbg" + i, mGR.mRow[i].bg);
		}
		{

			mGR.mPlayer.ax = prefs.getFloat("mPlayerax", mGR.mPlayer.ax);
			mGR.mPlayer.x = prefs.getFloat("mPlayerx", mGR.mPlayer.x);
			mGR.mPlayer.y = prefs.getFloat("mPlayery", mGR.mPlayer.y);
			mGR.mPlayer.vx = prefs.getFloat("mPlayervx", mGR.mPlayer.vx);
			mGR.mPlayer.vy = prefs.getFloat("mPlayervy", mGR.mPlayer.vy);
			mGR.mPlayer.MinSpeed = prefs.getFloat("mPlayerMinSpeed",
					mGR.mPlayer.MinSpeed);
			mGR.mPlayer.Bost = prefs.getFloat("mPlayerBost", mGR.mPlayer.Bost);

			mGR.mPlayer.forKeyPress = prefs.getInt("mPlayerforKeyPress",
					mGR.mPlayer.forKeyPress);
			mGR.mPlayer.Speed = prefs.getInt("mPlayerSpeed", mGR.mPlayer.Speed);
			mGR.mPlayer.Distance = prefs.getInt("mPlayerDistance",
					mGR.mPlayer.Distance);
			mGR.mPlayer.Crosscar = prefs.getInt("mPlayerCrosscar",
					mGR.mPlayer.Crosscar);
			mGR.mPlayer.CollectCoin = prefs.getInt("mPlayerCollectCoin",
					mGR.mPlayer.CollectCoin);
			mGR.mPlayer.ChallengeCom = prefs.getInt("mPlayerChallengeCom",
					mGR.mPlayer.ChallengeCom);
			mGR.mPlayer.Strenth = prefs.getInt("mPlayerStrenth",
					mGR.mPlayer.Strenth);
			mGR.mPlayer.Distroy = prefs.getInt("mPlayerDistroy",
					mGR.mPlayer.Distroy);

			mGR.mPlayer.mTuchScr = prefs.getBoolean("mPlayermTuchScr",
					mGR.mPlayer.mTuchScr);

			mGR.mPlayer.forDistance = prefs.getLong("mPlayerforDistance",
					mGR.mPlayer.forDistance);

			Player.mTotalTime = prefs.getLong("PlayermTotalDistance",
					Player.mTotalTime);
			Player.mTotalDistance = prefs.getInt("PlayermTotalTime",
					Player.mTotalDistance);
			Player.mTotalCrossCar = prefs.getInt("PlayermTotalCrossCar",
					Player.mTotalCrossCar);
			Player.mTotalCoin = prefs.getInt("PlayermTotalCoin",
					Player.mTotalCoin);
		}
		for (int i = 0; i < mGR.mOpponent.length; i++) {
			mGR.mOpponent[i].ax = prefs.getFloat("mOpponentax" + i,
					mGR.mOpponent[i].ax);
			mGR.mOpponent[i].x = prefs.getFloat("mOpponentx" + i,
					mGR.mOpponent[i].x);
			mGR.mOpponent[i].y = prefs.getFloat("mOpponenty" + i,
					mGR.mOpponent[i].y);
			mGR.mOpponent[i].vx = prefs.getFloat("mOpponentvx" + i,
					mGR.mOpponent[i].vx);
			mGR.mOpponent[i].vy = prefs.getFloat("mOpponentvy" + i,
					mGR.mOpponent[i].vy);
			mGR.mOpponent[i].no = prefs.getInt("mOpponentno" + i,
					mGR.mOpponent[i].no);
		}
		for (int i = 0; i < mGR.mCoins.length; i++) {
			mGR.mCoins[i].x = prefs.getFloat("mCoinsx" + i, mGR.mCoins[i].x);
			mGR.mCoins[i].y = prefs.getFloat("mCoinsy" + i, mGR.mCoins[i].y);
		}
		for (int i = 0; i < mGR.setVeh.length; i++) {
			mGR.setVeh[i].x = prefs.getFloat("setVehx" + i, mGR.setVeh[i].x);
			mGR.setVeh[i].scal = prefs.getFloat("setVehscal" + i,
					mGR.setVeh[i].scal);
			mGR.setVeh[i].no = prefs.getInt("setVehno" + i, mGR.setVeh[i].no);
			mGR.setVeh[i].lock = prefs.getBoolean("setVehlock" + i,
					mGR.setVeh[i].lock);
		}
		Animation.counter = prefs.getInt("Animationcounter", Animation.counter);
		Animation.Move = prefs.getBoolean("AnimationMove", Animation.Move);

		mGR.mVCount = prefs.getInt("mVCount", mGR.mVCount);
		mGR.mPath = prefs.getInt("mPath", mGR.mPath);
		mGR.brigeCount = prefs.getInt("brigeCount", mGR.brigeCount);
		mGR.mSel = prefs.getInt("mSel", mGR.mSel);
		mGR.mCarSel = prefs.getInt("mCarSel", mGR.mCarSel);
		mGR.StartConter = prefs.getInt("StartConter", mGR.StartConter);
		mGR.Challenge = prefs.getInt("Challenge", mGR.Challenge);
		mGR.pause = prefs.getFloat("pause", mGR.pause);
		mGR.settingPlay = prefs.getFloat("settingPlay", mGR.settingPlay);

		mGR.GameTime = prefs.getLong("GameTime", mGR.GameTime);
		mGR.pauseTime = prefs.getLong("pauseTime", mGR.pauseTime);

		switch (M.GameScreen) {
		case M.GameADD:
			mGR.mTex_AllBG = mGR.add("add.jpg");
			break;
		case M.GameSplash:
			mGR.mTex_AllBG = mGR.add("splash.jpg");
			break;
		case M.GameLogo:
			mGR.mTex_AllBG = mGR.add("logo.jpg");
			break;
		default:
		case M.GameMenu:
			mGR.mTex_AllBG = mGR.add("menu/back.png");
			break;
		}
		mGR.packages = prefs.getString("packagese", "");
		GetPackage();
		mGR.resumeCounter = 0;
		if(M.GameScreen == M.GameMenu || M.GameScreen == M.GameCarSelection)
		{
			M.Splashplay(GameRenderer.mContext, R.drawable.splash);
		}
	}
	final int REQUEST_LEADERBOARD = 101;
	void Exit() {
		   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Do you want to Exit?")
		    .setPositiveButton("More",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
	    	   Intent intent = new Intent(Intent.ACTION_VIEW);
	    	   intent.setData(Uri.parse(M.MARKET));
	    	   startActivity(intent);
	      }}).setNeutralButton("No",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
	      }}).setNegativeButton("Yes",new DialogInterface.OnClickListener(){public void onClick(DialogInterface dialog, int which) {
			    		   finish();M.GameScreen=M.GameLogo;mGR.root.counter =0;
	      }}).show();
	  }

	void Money() {
		new AlertDialog.Builder(this).setIcon(R.drawable.icon)
				.setTitle("You dont have enough Coin.")
				.setPositiveButton("Buy", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						M.GameScreen = M.GameBulk;
						M.loopSoundStop();
					}
				}).setNegativeButton("No", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					}
				}).show();
	}

	void SubmitScore() {
		new AlertDialog.Builder(this)
				.setIcon(R.drawable.icon)
				.setTitle("Submit Score ?")
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// Download.updateURL(renderer.mScore);
								// Download.DownloadFromUrl(CONTEXT,
								// Download.SUBMIT);

							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {

					// @Override
					public void onClick(DialogInterface dialog, int which) {
						Exit();
						// TODO Auto-generated method stub
					}
				}).show();
	}

	void GetPackage() {
//		Log.d("~~~~~~~~~~~~~~~"+mGR.packages, "not package : "+ mGR.packages);
		final PackageManager pm = getPackageManager();
		List<ApplicationInfo> packages = pm
				.getInstalledApplications(PackageManager.GET_META_DATA);
		for (ApplicationInfo packageInfo : packages) {
			String s = ".*" + packageInfo.packageName + ".*";
			for (int i = 0; i < M.Package.length; i++) {
				if (M.Package[i].matches(s)) {
					if (mGR.packages.matches(s)) {
//						System.out.println("~~~~~~~~~~~~~~~"+"Installed package :" + packageInfo.packageName);
					} else {
						mGR.packages = mGR.packages + packageInfo.packageName;
						Player.mTotalCoin += 1000;
						// Log.d("~~~~~~~~~~~~~~~"+mGR.packages, "not package :"
						// + packageInfo.packageName);
					}
					break;
				}
			}
		}
	}
	/*Handler LoadSmart = new Handler() {public void handleMessage(Message msg) {LoadSmarAd();}};
	void LoadSmartHandler()
	{
//		if(!mGR.addFree)
			try{LoadSmart.sendEmptyMessage(0);}catch(Exception e){}
	}
	boolean adType = false;
	private void LoadSmarAd()
	{
//		if(!mGR.addFree)
		{
			adType = !adType;//M.mRand.nextBoolean(); 
			if(adType)
				mAir.runSmartWallAd();
			else
				mAir.runRichMediaInterstitialAd();
//			mAir.runVideoAd();
		}
	}
	Handler ShowSmart = new Handler() {public void handleMessage(Message msg) {showSmarAd();}};
	public void ShowSmart() {
//		if(!mGR.addFree)
			try{ShowSmart.sendEmptyMessage(0);}catch(Exception e){}
	}
	private void showSmarAd()
	{
//		if(!mGR.addFree)
		{
			try{
				if(adType)
					mAir.runCachedAd(this, AdType.smartwall);  //Will show the ad is it's available in cache.
				else
					mAir.runCachedAd(this, AdType.interstitial);
			}catch (Exception e){
				System.out.println(e.toString());
			}
		}
	}*/

	public void loadInter() {
		try{handlerInter.sendEmptyMessage(0);}catch(Exception e){}
	}
	Handler handlerInter = new Handler() {public void handleMessage(Message msg) {load();}};
	private void load(){
		if (!interstitialAd.isLoaded()) {
			AdRequest adRequest = new AdRequest.Builder().build();
			interstitialAd.loadAd(adRequest);
			interstitialAd.setAdListener(new ToastAdListener(this));
		}
	}

	public void show() {
		try{handler.sendEmptyMessage(0);}catch(Exception e){}
	}
	Handler handler = new Handler() {public void handleMessage(Message msg) {show_ads();}};

	private void show_ads() {
		try {
			if (interstitialAd != null)
				if (interstitialAd.isLoaded()) {
					interstitialAd.show();
				}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
}