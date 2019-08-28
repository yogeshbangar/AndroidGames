package com.hututu.extreamdargerace;

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


public class Start extends BaseGameActivity{
	int _keyCode = 0;
	AdView adHouse = null;//AdHouse
	private InterstitialAd interstitialAd;
	GameRenderer mGR = null;
	private static Context CONTEXT;

	void callAdds() {
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~ Adds  ~~~~~~~~~~~~~~~~~~~~~~");
		// AdSize.MEDIUM_RECTANGLE
		adHouse = (AdView) this.findViewById(R.id.advhouse);
		AdRequest adRequest = new AdRequest.Builder().build();
		adHouse.loadAd(adRequest);
		adHouse.setAdListener(new AdListener() {
			public void onAdLoaded() {
				adHouse.bringToFront();
			}
		});
	}
	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) 
	{
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);//OnlyOneChange
		CONTEXT	=	this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId(getString(R.string.INTERSTITIAL_ID));
		WindowManager mWinMgr = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
		M.ScreenWidth =mWinMgr.getDefaultDisplay().getWidth();
		M.ScreenHieght=mWinMgr.getDefaultDisplay().getHeight();
		mGR=new GameRenderer(this);
	    VortexView glSurface=(VortexView) findViewById(R.id.vortexview); // use the xml to set the view
	    glSurface.setRenderer(mGR);
	    glSurface.showRenderer(mGR);
	    if(!getSharedPreferences("X", MODE_PRIVATE).getBoolean("addFree", mGR.addFree))
	    	callAdds();
	}
	public static Context getContext() {
	        return CONTEXT;
	}
	@Override 
	public void onPause () {
		M.stop(CONTEXT);
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
		Log.d("----------------=>  "+keyCode,"   -----------    ");
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{

			switch (M.GameScreen) {
			case M.GAMEPLAY:
				M.GameScreen = M.GAMEPUASE;
				M.stop(GameRenderer.mContext);
				break;
			case M.GAMEMENU:
				Exit();
				break;
			default:
				M.GameScreen = M.GAMEMENU;
				M.stop(GameRenderer.mContext);
				break;
			}
			mGR.root.popup = 0;
			
			return false;
		}
		_keyCode = keyCode;
		return super.onKeyDown(keyCode,event); 
	}

	public boolean onKeyUp(int keyCode, KeyEvent event) {
		_keyCode = 0;
		return super.onKeyUp(keyCode, event);
	}

	public void onDestroy() {
		super.onDestroy();
		if(mGR.mMainActivity!=null)
			mGR.mMainActivity.onDestroy();
	}

	void pause() {
		if(M.GameScreen == M.GAMEPLAY)
			M.GameScreen = M.GAMEPUASE;
		mGR.resumeCounter = 0;
		M.stop(GameRenderer.mContext);
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putInt("screen", M.GameScreen);
		editor.putBoolean("setValue", M.setValue);
		
		editor.putBoolean("addFree", mGR.addFree);
		
		for(int i=0;i<mGR.gameUnlock.length;i++)
			for(int j=0;j<mGR.gameUnlock[i].length;j++)
				editor.putBoolean(i+"gameUnlock"+j, mGR.gameUnlock[i][j]);
		
		for(int i=0;i<mGR.gamePlayed.length;i++)
			for(int j=0;j<mGR.gamePlayed[i].length;j++)
				editor.putBoolean(i+"gamePlayed"+j, mGR.gamePlayed[i][j]);
		
		for(int i=0;i<mGR.mBikeUnlock.length;i++)
			editor.putBoolean("mBikeUnlock"+i, mGR.mBikeUnlock[i]);
		
		for(int i=0;i<mGR.mAchiUnlock.length;i++)
			editor.putBoolean("mAchiUnlock"+i, mGR.mAchiUnlock[i]);
		editor.putBoolean("mIsQuckRace", mGR.mIsQuckRace);
		editor.putBoolean("citiUnlock", mGR.citiUnlock);
		editor.putBoolean("SingUpadate", mGR.SingUpadate);
		
		editor.putInt("mIsGameWin", mGR.mIsGameWin);
		editor.putInt("city", mGR.city);
		editor.putInt("start", mGR.start);
		editor.putInt("mLevel", mGR.mLevel);
		editor.putInt("mBike", mGR.mBike);
		editor.putInt("mDoller", mGR.mDoller);
		editor.putInt("upgrade", mGR.upgrade);
		editor.putInt("waitCounter", mGR.waitCounter);
		editor.putInt("mScore", mGR.mScore);
		editor.putInt("showCount", mGR.showCount);
		
		editor.putLong("Gamestart", mGR.Gamestart);
		
		editor.putFloat("mBikex", mGR.mBikex);
		editor.putFloat("mBikevx", mGR.mBikevx);
		editor.putFloat("mFLine", mGR.mFLine);
		for(int i=0;i<mGR.mBGX0.length;i++)
			editor.putFloat("mBGX0"+i, mGR.mBGX0[i]);
		for(int i=0;i<mGR.mBGX1.length;i++)
			editor.putFloat("mBGX1"+i, mGR.mBGX1[i]);
		editor.putFloat("mTowerX", mGR.mTowerX);
		editor.putFloat("mSpeed", mGR.mSpeed);
		editor.putFloat("gamedistace", mGR.gamedistace);
		
		editor.putString("mPName", mGR.mPName);
		
		for(int i=0;i<mGR.mSpec.length;i++){
			editor.putInt("mSpeclvlExhaust"+i, mGR.mSpec[i].lvlExhaust);
			editor.putInt("mSpeclvlNitro"+i, mGR.mSpec[i].lvlNitro);
			editor.putInt("mSpeclvlWeight"+i, mGR.mSpec[i].lvlWeight);
			editor.putInt("mSpeclvlGearBox"+i, mGR.mSpec[i].lvlGearBox);
		}
		
		
		
		{
			editor.putBoolean("Playernetro", mGR.mPlayer.netro);
			
			editor.putInt("PlayerGear", mGR.mPlayer.Gear);
			editor.putInt("PlayerBikeNo", mGR.mPlayer.BikeNo);
			editor.putInt("Playerm0to60", mGR.mPlayer.m0to60);
			editor.putInt("Playerm0to100", mGR.mPlayer.m0to100);
			editor.putInt("Playertime", mGR.mPlayer.time);
			editor.putInt("PlayerMaxSpeed", mGR.mPlayer.MaxSpeed);
			editor.putInt("Playerparfect", mGR.mPlayer.parfect);
			editor.putInt("PlayerGood", mGR.mPlayer.Good);
			editor.putInt("Playerover", mGR.mPlayer.over);
			editor.putInt("Playerbonus", mGR.mPlayer.bonus);
			editor.putInt("Playerprice", mGR.mPlayer.price);
			editor.putInt("PlayergearChange", mGR.mPlayer.gearChange);
			editor.putInt("PlayerNitro", mGR.mPlayer.Nitro);
			
			editor.putFloat("Playerx", mGR.mPlayer.x);
			editor.putFloat("Playery", mGR.mPlayer.y);
			editor.putFloat("PlayerDis", mGR.mPlayer.Dis);
			editor.putFloat("PlayerEngine", mGR.mPlayer.Engine);
			editor.putFloat("PlayerExhaust", mGR.mPlayer.Exhaust);
			editor.putFloat("PlayerWeight", mGR.mPlayer.Weight);
			editor.putFloat("PlayerGearBox", mGR.mPlayer.GearBox);
			editor.putFloat("Playerrpm", mGR.mPlayer.rpm);
		}
		
		
		{

			editor.putBoolean("Opponentnetro", mGR.mOpponent.netro);
			
			editor.putInt("OpponentGear", mGR.mOpponent.Gear);
			editor.putInt("OpponentBikeNo", mGR.mOpponent.BikeNo);
			editor.putInt("Opponentm0to60", mGR.mOpponent.m0to60);
			editor.putInt("Opponentm0to100", mGR.mOpponent.m0to100);
			editor.putInt("Opponenttime", mGR.mOpponent.time);
			editor.putInt("OpponentMaxSpeed", mGR.mOpponent.MaxSpeed);
			editor.putInt("Opponentparfect", mGR.mOpponent.parfect);
			editor.putInt("OpponentGood", mGR.mOpponent.Good);
			editor.putInt("Opponentover", mGR.mOpponent.over);
			editor.putInt("Opponentbonus", mGR.mOpponent.bonus);
			editor.putInt("Opponentprice", mGR.mOpponent.price);
			editor.putInt("OpponentgearChange", mGR.mOpponent.gearChange);
			editor.putInt("OpponentNitro", mGR.mOpponent.Nitro);
			
			editor.putFloat("Opponentx", mGR.mOpponent.x);
			editor.putFloat("Opponenty", mGR.mOpponent.y);
			editor.putFloat("OpponentDis", mGR.mOpponent.Dis);
			editor.putFloat("OpponentEngine", mGR.mOpponent.Engine);
			editor.putFloat("OpponentExhaust", mGR.mOpponent.Exhaust);
			editor.putFloat("OpponentWeight", mGR.mOpponent.Weight);
			editor.putFloat("OpponentGearBox", mGR.mOpponent.GearBox);
			editor.putFloat("Opponentrpm", mGR.mOpponent.rpm);
		
		}
		
		
		
		
		
		
		editor.commit();
	}

	void resume() {
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = prefs.getInt("screen", M.GAMELOGO);
		M.setValue = prefs.getBoolean("setValue", M.setValue);
		
		mGR.addFree = prefs.getBoolean("addFree", mGR.addFree);
		
		for(int i=0;i<mGR.gameUnlock.length;i++)
			for(int j=0;j<mGR.gameUnlock[i].length;j++)
				mGR.gameUnlock[i][j] = prefs.getBoolean(i+"gameUnlock"+j, mGR.gameUnlock[i][j]);
		
		for(int i=0;i<mGR.gamePlayed.length;i++)
			for(int j=0;j<mGR.gamePlayed[i].length;j++)
				mGR.gamePlayed[i][j] = prefs.getBoolean(i+"gamePlayed"+j, mGR.gamePlayed[i][j]);
		
		for(int i=0;i<mGR.mBikeUnlock.length;i++)
			mGR.mBikeUnlock[i] = prefs.getBoolean("mBikeUnlock"+i, mGR.mBikeUnlock[i]);
		
		for(int i=0;i<mGR.mAchiUnlock.length;i++)
			mGR.mAchiUnlock[i] = prefs.getBoolean("mAchiUnlock"+i, mGR.mAchiUnlock[i]);
		mGR.mIsQuckRace= prefs.getBoolean("mIsQuckRace", mGR.mIsQuckRace);
		
		mGR.citiUnlock = prefs.getBoolean("citiUnlock", mGR.citiUnlock);
		mGR.SingUpadate = prefs.getBoolean("SingUpadate", mGR.SingUpadate);
		
		mGR.mIsGameWin = prefs.getInt("mIsGameWin", mGR.mIsGameWin);
		mGR.city = prefs.getInt("city", mGR.city);
		mGR.start = prefs.getInt("start", mGR.start);
		mGR.mLevel = prefs.getInt("mLevel", mGR.mLevel);
		mGR.mBike = prefs.getInt("mBike", mGR.mBike);
		mGR.mDoller = prefs.getInt("mDoller", mGR.mDoller);
		mGR.upgrade = prefs.getInt("upgrade", mGR.upgrade);
		mGR.waitCounter = prefs.getInt("waitCounter", mGR.waitCounter);
		mGR.mScore = prefs.getInt("mScore", mGR.mScore);
		mGR.showCount = prefs.getInt("showCount", mGR.showCount);
		
		mGR.Gamestart = prefs.getLong("Gamestart", mGR.Gamestart);
		
		mGR.mBikex = prefs.getFloat("mBikex", mGR.mBikex);
		mGR.mBikevx = prefs.getFloat("mBikevx", mGR.mBikevx);
		mGR.mFLine = prefs.getFloat("mFLine", mGR.mFLine);
		for(int i=0;i<mGR.mBGX0.length;i++)
			mGR.mBGX0[i] = prefs.getFloat("mBGX0"+i, mGR.mBGX0[i]);
		for(int i=0;i<mGR.mBGX1.length;i++)
			mGR.mBGX1[i] = prefs.getFloat("mBGX1"+i, mGR.mBGX1[i]);
		mGR.mTowerX = prefs.getFloat("mTowerX", mGR.mTowerX);
		mGR.mSpeed = prefs.getFloat("mSpeed", mGR.mSpeed);
		mGR.gamedistace = prefs.getFloat("gamedistace", mGR.gamedistace);
		
		mGR.mPName = prefs.getString("mPName", mGR.mPName);
		
		for(int i=0;i<mGR.mSpec.length;i++){
			mGR.mSpec[i].lvlExhaust = prefs.getInt("mSpeclvlExhaust"+i, mGR.mSpec[i].lvlExhaust);
			mGR.mSpec[i].lvlNitro = prefs.getInt("mSpeclvlNitro"+i, mGR.mSpec[i].lvlNitro);
			mGR.mSpec[i].lvlWeight = prefs.getInt("mSpeclvlWeight"+i, mGR.mSpec[i].lvlWeight);
			mGR.mSpec[i].lvlGearBox = prefs.getInt("mSpeclvlGearBox"+i, mGR.mSpec[i].lvlGearBox);
		}
		
		
		
		{
			mGR.mPlayer.netro = prefs.getBoolean("Playernetro", mGR.mPlayer.netro);
			
			mGR.mPlayer.Gear = prefs.getInt("PlayerGear", mGR.mPlayer.Gear);
			mGR.mPlayer.BikeNo = prefs.getInt("PlayerBikeNo", mGR.mPlayer.BikeNo);
			mGR.mPlayer.m0to60 = prefs.getInt("Playerm0to60", mGR.mPlayer.m0to60);
			mGR.mPlayer.m0to100 = prefs.getInt("Playerm0to100", mGR.mPlayer.m0to100);
			mGR.mPlayer.time = prefs.getInt("Playertime", mGR.mPlayer.time);
			mGR.mPlayer.MaxSpeed = prefs.getInt("PlayerMaxSpeed", mGR.mPlayer.MaxSpeed);
			mGR.mPlayer.parfect = prefs.getInt("Playerparfect", mGR.mPlayer.parfect);
			mGR.mPlayer.Good = prefs.getInt("PlayerGood", mGR.mPlayer.Good);
			mGR.mPlayer.over = prefs.getInt("Playerover", mGR.mPlayer.over);
			mGR.mPlayer.bonus = prefs.getInt("Playerbonus", mGR.mPlayer.bonus);
			mGR.mPlayer.price = prefs.getInt("Playerprice", mGR.mPlayer.price);
			mGR.mPlayer.gearChange = prefs.getInt("PlayergearChange", mGR.mPlayer.gearChange);
			mGR.mPlayer.Nitro = prefs.getInt("PlayerNitro", mGR.mPlayer.Nitro);
			
			mGR.mPlayer.x = prefs.getFloat("Playerx", mGR.mPlayer.x);
			mGR.mPlayer.y = prefs.getFloat("Playery", mGR.mPlayer.y);
			mGR.mPlayer.Dis = prefs.getFloat("PlayerDis", mGR.mPlayer.Dis);
			mGR.mPlayer.Engine = prefs.getFloat("PlayerEngine", mGR.mPlayer.Engine);
			mGR.mPlayer.Exhaust = prefs.getFloat("PlayerExhaust", mGR.mPlayer.Exhaust);
			mGR.mPlayer.Weight = prefs.getFloat("PlayerWeight", mGR.mPlayer.Weight);
			mGR.mPlayer.GearBox = prefs.getFloat("PlayerGearBox", mGR.mPlayer.GearBox);
			mGR.mPlayer.rpm = prefs.getFloat("Playerrpm", mGR.mPlayer.rpm);
		}
		
		
		{
			mGR.mOpponent.netro = prefs.getBoolean("Opponentnetro", mGR.mOpponent.netro);
			
			mGR.mOpponent.Gear = prefs.getInt("OpponentGear", mGR.mOpponent.Gear);
			mGR.mOpponent.BikeNo = prefs.getInt("OpponentBikeNo", mGR.mOpponent.BikeNo);
			mGR.mOpponent.m0to60 = prefs.getInt("Opponentm0to60", mGR.mOpponent.m0to60);
			mGR.mOpponent.m0to100 = prefs.getInt("Opponentm0to100", mGR.mOpponent.m0to100);
			mGR.mOpponent.time = prefs.getInt("Opponenttime", mGR.mOpponent.time);
			mGR.mOpponent.MaxSpeed = prefs.getInt("OpponentMaxSpeed", mGR.mOpponent.MaxSpeed);
			mGR.mOpponent.parfect = prefs.getInt("Opponentparfect", mGR.mOpponent.parfect);
			mGR.mOpponent.Good = prefs.getInt("OpponentGood", mGR.mOpponent.Good);
			mGR.mOpponent.over = prefs.getInt("Opponentover", mGR.mOpponent.over);
			mGR.mOpponent.bonus = prefs.getInt("Opponentbonus", mGR.mOpponent.bonus);
			mGR.mOpponent.price = prefs.getInt("Opponentprice", mGR.mOpponent.price);
			mGR.mOpponent.gearChange = prefs.getInt("OpponentgearChange", mGR.mOpponent.gearChange);
			mGR.mOpponent.Nitro = prefs.getInt("OpponentNitro", mGR.mOpponent.Nitro);
			
			mGR.mOpponent.x = prefs.getFloat("Opponentx", mGR.mOpponent.x);
			mGR.mOpponent.y = prefs.getFloat("Opponenty", mGR.mOpponent.y);
			mGR.mOpponent.Dis = prefs.getFloat("OpponentDis", mGR.mOpponent.Dis);
			mGR.mOpponent.Engine = prefs.getFloat("OpponentEngine", mGR.mOpponent.Engine);
			mGR.mOpponent.Exhaust = prefs.getFloat("OpponentExhaust", mGR.mOpponent.Exhaust);
			mGR.mOpponent.Weight = prefs.getFloat("OpponentWeight", mGR.mOpponent.Weight);
			mGR.mOpponent.GearBox = prefs.getFloat("OpponentGearBox", mGR.mOpponent.GearBox);
			mGR.mOpponent.rpm = prefs.getFloat("Opponentrpm", mGR.mOpponent.rpm);
		}
		
		
		
		mGR.resumeCounter = 0;
		
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
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (!mGR.mMainActivity.mHelper.handleActivityResult(requestCode,resultCode, data)) {
			super.onActivityResult(requestCode, resultCode, data);
		} else {
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
			GameRenderer.mStart.Submitscore(R.string.leaderboard_score, mGR.mScore);
			mGR.SingUpadate = true;
			
//			mGR.mPName = getGamesClient().getCurrentPlayer().getDisplayName().substring(0, 9);
			
			mGR.mPName = Games.Players.getCurrentPlayer(getApiClient()).getDisplayName();
			if(mGR.mPName.length() > 9)
				mGR.mPName = mGR.mPName.substring(0, 9);
			
		}
	}
	public void Submitscore(final int ID,int total) {
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
	void loadInter(){
		if (!interstitialAd.isLoaded() && !mGR.addFree) {
			AdRequest adRequest = new AdRequest.Builder()
//					.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//					.addTestDevice("67701763FB847AEBD3C6EE486475ED94")
			.build();
			interstitialAd.loadAd(adRequest);
			interstitialAd.setAdListener(new ToastAdListener(this));
		}
	}

	public void show() {
		try{handler.sendEmptyMessage(0);}catch(Exception e){}
	}
	Handler handler = new Handler() {public void handleMessage(Message msg) {show_ads();}};

	void show_ads() {
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