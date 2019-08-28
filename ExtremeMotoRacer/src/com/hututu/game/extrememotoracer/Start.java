package com.hututu.game.extrememotoracer;

import java.util.List;


import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.google.ads.InterstitialAd;
import com.google.android.gms.games.leaderboard.OnScoreSubmittedListener;
import com.google.android.gms.games.leaderboard.SubmitScoreResult;

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
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Start extends BaseGameActivity  implements AdListener {
	AdView adView = null;
	AdView adHouse=null,adViewF = null;
	GameRenderer mGR = null;
//	BroadcastReceiver mReceiver;
	private static Context CONTEXT;
	int _keyCode = 0;
	boolean addFree = false;
	private InterstitialAd interstitialAd;
	void callAdds() {
		adView = new AdView(this, AdSize.BANNER, M.MY_AD_UNIT_ID);
		LinearLayout layout = (LinearLayout) findViewById(R.id.addgame);
		adView.setGravity(Gravity.TOP);
		layout.addView(adView);
		adView.loadAd(new AdRequest());
		
		adViewF = new AdView(this, AdSize.IAB_MRECT, M.MY_AD_UNIT_IDF);
		LinearLayout layout2 = (LinearLayout)findViewById(R.id.addgameRect);
		adViewF.setGravity(Gravity.TOP); layout2.addView(adViewF);
		adViewF.loadAd(new AdRequest());
		
		adHouse = new AdView(this, AdSize.IAB_MRECT, M.HOUSE_ADV_ID);
		LinearLayout layout3 = (LinearLayout)findViewById(R.id.advhouse);
		adHouse.setGravity(Gravity.TOP);
        layout3.addView(adHouse);
        adHouse.loadAd(new AdRequest());
        
//		GamesClient client = new GamesClient;
	}

	

	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);//OnlyOneChange
		CONTEXT = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		interstitialAd = new InterstitialAd(this, M.INTERSTITIAL_ID);
	    interstitialAd.setAdListener(this);
		WindowManager mWinMgr = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		M.ScreenWidth = mWinMgr.getDefaultDisplay().getWidth();
		M.ScreenHieght = mWinMgr.getDefaultDisplay().getHeight();
		mGR = new GameRenderer(this);
		VortexView glSurface = (VortexView) findViewById(R.id.vortexview); 
		glSurface.setRenderer(mGR);
		glSurface.showRenderer(mGR);
		// Recievier
//		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
//		filter.addAction(Intent.ACTION_SCREEN_OFF);
//		filter.addAction(Intent.ACTION_USER_PRESENT);
//		mReceiver = new ScreenReceiver();
//		registerReceiver(mReceiver, filter);
		// Recievier
		callAdds();
		
		
		
		
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
		if(mGR.mMainActivity!=null)
			mGR.mMainActivity.onDestroy();
	}

	void pause() {
		
		M.stop(GameRenderer.mContext);
		M.loopSoundStop();
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		Editor editor = prefs.edit();
		if (M.GameScreen == M.GamePlay)
			M.GameScreen = M.GamePause;
		editor.putInt("screen", M.GameScreen);
		editor.putBoolean("setValue", M.setValue);
		editor.putBoolean("setBG", M.setBG);
		editor.putBoolean("addFree", addFree);

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
		addFree  = prefs.getBoolean("addFree", addFree);
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
		    .setPositiveButton("Rate Us",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
	    	   Intent intent = new Intent(Intent.ACTION_VIEW);
	    	   intent.setData(Uri.parse(M.LINK+getClass().getPackage().getName()));
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
	
	
	
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~"+ "onActivityResult(" + requestCode + "," + resultCode + "," + data);

        // Pass on the activity result to the helper for handling
        if (!mGR.mMainActivity.mHelper.handleActivityResult(requestCode, resultCode, data)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            super.onActivityResult(requestCode, resultCode, data);
        }
        else {
        	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~"+ "onActivityResult handled by IABUtil.");
        }
    }



	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		
	}

	public void Submitscore(final int ID,long Score) {
		if (!isSignedIn()) {
			beginUserInitiatedSignIn();
		} else { getGamesClient().submitScoreImmediate(new OnScoreSubmittedListener() {
			@Override
			public void onScoreSubmitted(int arg0,SubmitScoreResult arg1) {
				// TODO Auto-generated method stub
				//if (ID == R.string.leaderboard_score) {Submitscore(R.string.leaderboard_high_score,mGR.mHScore);} else 
				{
//								System.out.println("#########Submitscore##############");
					Toast.makeText(Start.this, "Submited",Toast.LENGTH_LONG).show();
				}
			}
		}, getString(ID), Score);
		}
	}

	int RC_UNUSED = 5001;

	// @Override
	public void onShowLeaderboardsRequested() {
		if (isSignedIn()) {
			startActivityForResult(getGamesClient().getAllLeaderboardsIntent(),RC_UNUSED);
		} else {
			beginUserInitiatedSignIn();
			// showAlert(getString(R.string.leaderboard_score));
		}
	}



	@Override
	public void onDismissScreen(Ad arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onLeaveApplication(Ad arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onPresentScreen(Ad arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onReceiveAd(Ad arg0) {
		// TODO Auto-generated method stub
		System.out.println("Interstitial Receive..............");
	}
	void load() {
		System.out.println("Interstitial loading..............");
//		if(!mGR.addFree)
		{
			AdRequest adRequest = new AdRequest();
			interstitialAd.loadAd(adRequest);
		}
	}
	
	void show() {
//		if (!mGR.addFree) 
		{
			if (interstitialAd.isReady()) {
				interstitialAd.show();
				System.out.println("Interstitial Show.......   "+interstitialAd.toString());
			} else {
//				System.out.println("Interstitial ad was not ready to be shown.--------  "+interstitialAd.toString());
			}
		}
	}
	boolean interstitialAdisReady()
	{
//		System.out.println(interstitialAd.isReady());
		return interstitialAd.isReady();
	}


//	@Override
//	public void onSignInFailed() {
//		// TODO Auto-generated method stub
//		System.out.println("onSignInFailed =  "+M.SingIn );
//		new AlertDialog.Builder(this).setIcon(R.drawable.icon)
//				.setTitle("Please try later...")
//				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int which) {
//						
//					}
//				}).show();
//	
//	}
//	@Override
//	public void onSignInSucceeded() {
//		// TODO Auto-generated method stub
//		M.GameScreen = M.GameOver;
//		M.SingIn = true;
//		System.out.println("onSignInSucceeded =  "+M.SingIn );
//	}
//	protected void beginUserInitiatedSignIn() {
//		System.out.println("beginUserInitiatedSignIn 12");
//		mHelper.beginUserInitiatedSignIn();
//		
//		
//	}
//	
//	protected void Show() {
//		System.out.println("Show ~~~~~~~~~~~~~~~~~~~~~~~~~~ 13");
//		if (getGamesClient().isConnected()) {
//     	   startActivityForResult(getGamesClient().getAllLeaderboardsIntent(), 5001);
//        }
//	}
//	protected void Submit() {
//		System.out.println("Show ~~~~~~~~~~~~~~~~~~~~~~~~~~ "+Math.abs(mGR.pauseTime-mGR.GameTime));
//		if (getGamesClient().isConnected()) {
//			getGamesClient().submitScore(getString(R.string.leaderboard_time), Math.abs(mGR.pauseTime-mGR.GameTime));
//			System.out.println("Show ~~~~~~~~~100~~~~~~~~~~~~~~~~~ "+Math.abs(mGR.pauseTime-mGR.GameTime));
//        }
//	}
	
}