package com.oneday.games24.extreme.drag.racing;

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


public class Start extends BaseGameActivity{
	
	
	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) 
	{
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);//OnlyOneChange
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
	

	void pause() {
		if(M.GameScreen == M.GAMEPLAY)
			M.GameScreen = M.GAMEPUASE;
		mGR.resumeCounter = 0;
		M.stop(GameRenderer.mContext);
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		Editor editor = prefs.edit();
		
		
		editor.putInt("a", M.GameScreen);
		editor.putBoolean("b", M.setValue);
		
		editor.putBoolean("c", mGR.addFree);
		
		for(int i=0;i<mGR.gameUnlock.length;i++)
			for(int j=0;j<mGR.gameUnlock[i].length;j++)
				editor.putBoolean(i+"d"+j, mGR.gameUnlock[i][j]);
		
		for(int i=0;i<mGR.gamePlayed.length;i++)
			for(int j=0;j<mGR.gamePlayed[i].length;j++)
				editor.putBoolean(i+"e"+j, mGR.gamePlayed[i][j]);
		
		for(int i=0;i<mGR.mBikeUnlock.length;i++)
			editor.putBoolean("f"+i, mGR.mBikeUnlock[i]);
		
		for(int i=0;i<mGR.mAchiUnlock.length;i++)
			editor.putBoolean("g"+i, mGR.mAchiUnlock[i]);
		editor.putBoolean("h", mGR.mIsQuckRace);
		editor.putBoolean("i", mGR.citiUnlock);
		editor.putBoolean("j", mGR.SingUpadate);
		
		editor.putInt("k", mGR.mIsGameWin);
		editor.putInt("l", mGR.city);
		editor.putInt("m", mGR.start);
		editor.putInt("n", mGR.mLevel);
		editor.putInt("o", mGR.mBike);
		editor.putInt("p", mGR.mDoller);
		editor.putInt("q", mGR.upgrade);
		editor.putInt("r", mGR.waitCounter);
		editor.putInt("s", mGR.mScore);
		editor.putInt("t", mGR.showCount);
		
		editor.putLong("u", mGR.Gamestart);
		
		editor.putFloat("v", mGR.mBikex);
		editor.putFloat("x", mGR.mBikevx);
		editor.putFloat("y", mGR.mFLine);
		for(int i=0;i<mGR.mBGX0.length;i++)
			editor.putFloat("z"+i, mGR.mBGX0[i]);
		for(int i=0;i<mGR.mBGX1.length;i++)
			editor.putFloat("aa"+i, mGR.mBGX1[i]);
		editor.putFloat("ab", mGR.mTowerX);
		editor.putFloat("ac", mGR.mSpeed);
		editor.putFloat("ad", mGR.gamedistace);
		
		editor.putString("ae", mGR.mPName);
		
		for(int i=0;i<mGR.mSpec.length;i++){
			editor.putInt("af"+i, mGR.mSpec[i].lvlExhaust);
			editor.putInt("ag"+i, mGR.mSpec[i].lvlNitro);
			editor.putInt("ah"+i, mGR.mSpec[i].lvlWeight);
			editor.putInt("ai"+i, mGR.mSpec[i].lvlGearBox);
		}
		
		
		
		{
			editor.putBoolean("aj", mGR.mPlayer.netro);
			
			editor.putInt("ak", mGR.mPlayer.Gear);
			editor.putInt("al", mGR.mPlayer.BikeNo);
			editor.putInt("am", mGR.mPlayer.m0to60);
			editor.putInt("an", mGR.mPlayer.m0to100);
			editor.putInt("ao", mGR.mPlayer.time);
			editor.putInt("ap", mGR.mPlayer.MaxSpeed);
			editor.putInt("aq", mGR.mPlayer.parfect);
			editor.putInt("ar", mGR.mPlayer.Good);
			editor.putInt("as", mGR.mPlayer.over);
			editor.putInt("at", mGR.mPlayer.bonus);
			editor.putInt("au", mGR.mPlayer.price);
			editor.putInt("av", mGR.mPlayer.gearChange);
			editor.putInt("aw", mGR.mPlayer.Nitro);
			
			editor.putFloat("ax", mGR.mPlayer.x);
			editor.putFloat("ay", mGR.mPlayer.y);
			editor.putFloat("az", mGR.mPlayer.Dis);
			editor.putFloat("ba", mGR.mPlayer.Engine);
			editor.putFloat("bb", mGR.mPlayer.Exhaust);
			editor.putFloat("bc", mGR.mPlayer.Weight);
			editor.putFloat("bd", mGR.mPlayer.GearBox);
			editor.putFloat("be", mGR.mPlayer.rpm);
		}
		
		
		{

			editor.putBoolean("bf", mGR.mOpponent.netro);
			
			editor.putInt("bg", mGR.mOpponent.Gear);
			editor.putInt("bh", mGR.mOpponent.BikeNo);
			editor.putInt("bi", mGR.mOpponent.m0to60);
			editor.putInt("bj", mGR.mOpponent.m0to100);
			editor.putInt("bk", mGR.mOpponent.time);
			editor.putInt("bl", mGR.mOpponent.MaxSpeed);
			editor.putInt("bm", mGR.mOpponent.parfect);
			editor.putInt("bn", mGR.mOpponent.Good);
			editor.putInt("bo", mGR.mOpponent.over);
			editor.putInt("bp", mGR.mOpponent.bonus);
			editor.putInt("bq", mGR.mOpponent.price);
			editor.putInt("br", mGR.mOpponent.gearChange);
			editor.putInt("bs", mGR.mOpponent.Nitro);
			
			editor.putFloat("bt", mGR.mOpponent.x);
			editor.putFloat("bu", mGR.mOpponent.y);
			editor.putFloat("bv", mGR.mOpponent.Dis);
			editor.putFloat("bw", mGR.mOpponent.Engine);
			editor.putFloat("bx", mGR.mOpponent.Exhaust);
			editor.putFloat("by", mGR.mOpponent.Weight);
			editor.putFloat("bz", mGR.mOpponent.GearBox);
			editor.putFloat("ca", mGR.mOpponent.rpm);
		
		}
		
		
		
		
		
		
		editor.commit();
	}

	void resume() {
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		
		
		M.GameScreen = prefs.getInt("a", M.GAMELOGO);
		M.setValue = prefs.getBoolean("b", M.setValue);
		
		mGR.addFree = prefs.getBoolean("c", mGR.addFree);
		
		for(int i=0;i<mGR.gameUnlock.length;i++)
			for(int j=0;j<mGR.gameUnlock[i].length;j++)
				mGR.gameUnlock[i][j] = prefs.getBoolean(i+"d"+j, mGR.gameUnlock[i][j]);
		
		for(int i=0;i<mGR.gamePlayed.length;i++)
			for(int j=0;j<mGR.gamePlayed[i].length;j++)
				mGR.gamePlayed[i][j] = prefs.getBoolean(i+"e"+j, mGR.gamePlayed[i][j]);
		
		for(int i=0;i<mGR.mBikeUnlock.length;i++)
			mGR.mBikeUnlock[i] = prefs.getBoolean("f"+i, mGR.mBikeUnlock[i]);
		
		for(int i=0;i<mGR.mAchiUnlock.length;i++)
			mGR.mAchiUnlock[i] = prefs.getBoolean("g"+i, mGR.mAchiUnlock[i]);
		mGR.mIsQuckRace = prefs.getBoolean("h", mGR.mIsQuckRace);
		mGR.citiUnlock = prefs.getBoolean("i", mGR.citiUnlock);
		mGR.SingUpadate = prefs.getBoolean("j", mGR.SingUpadate);
		
		mGR.mIsGameWin = prefs.getInt("k", mGR.mIsGameWin);
		mGR.city = prefs.getInt("l", mGR.city);
		mGR.start = prefs.getInt("m", mGR.start);
		mGR.mLevel = prefs.getInt("n", mGR.mLevel);
		mGR.mBike = prefs.getInt("o", mGR.mBike);
		mGR.mDoller = prefs.getInt("p", mGR.mDoller);
		mGR.upgrade = prefs.getInt("q", mGR.upgrade);
		mGR.waitCounter = prefs.getInt("r", mGR.waitCounter);
		mGR.mScore = prefs.getInt("s", mGR.mScore);
		mGR.showCount = prefs.getInt("t", mGR.showCount);
		
		mGR.Gamestart = prefs.getLong("u", mGR.Gamestart);
		
		mGR.mBikex = prefs.getFloat("v", mGR.mBikex);
		mGR.mBikevx = prefs.getFloat("x", mGR.mBikevx);
		mGR.mFLine = prefs.getFloat("y", mGR.mFLine);
		for(int i=0;i<mGR.mBGX0.length;i++)
			mGR.mBGX0[i] = prefs.getFloat("z"+i, mGR.mBGX0[i]);
		for(int i=0;i<mGR.mBGX1.length;i++)
			mGR.mBGX1[i] = prefs.getFloat("aa"+i, mGR.mBGX1[i]);
		mGR.mTowerX = prefs.getFloat("ab", mGR.mTowerX);
		mGR.mSpeed = prefs.getFloat("ac", mGR.mSpeed);
		mGR.gamedistace = prefs.getFloat("ad", mGR.gamedistace);
		
		mGR.mPName = prefs.getString("ae", mGR.mPName);
		
		for(int i=0;i<mGR.mSpec.length;i++){
			mGR.mSpec[i].lvlExhaust = prefs.getInt("af"+i, mGR.mSpec[i].lvlExhaust);
			mGR.mSpec[i].lvlNitro = prefs.getInt("ag"+i, mGR.mSpec[i].lvlNitro);
			mGR.mSpec[i].lvlWeight = prefs.getInt("ah"+i, mGR.mSpec[i].lvlWeight);
			mGR.mSpec[i].lvlGearBox = prefs.getInt("ai"+i, mGR.mSpec[i].lvlGearBox);
		}
		
		
		
		{
			mGR.mPlayer.netro = prefs.getBoolean("aj", mGR.mPlayer.netro);
			
			mGR.mPlayer.Gear = prefs.getInt("ak", mGR.mPlayer.Gear);
			mGR.mPlayer.BikeNo = prefs.getInt("al", mGR.mPlayer.BikeNo);
			mGR.mPlayer.m0to60 = prefs.getInt("am", mGR.mPlayer.m0to60);
			mGR.mPlayer.m0to100 = prefs.getInt("an", mGR.mPlayer.m0to100);
			mGR.mPlayer.time = prefs.getInt("ao", mGR.mPlayer.time);
			mGR.mPlayer.MaxSpeed = prefs.getInt("ap", mGR.mPlayer.MaxSpeed);
			mGR.mPlayer.parfect = prefs.getInt("aq", mGR.mPlayer.parfect);
			mGR.mPlayer.Good = prefs.getInt("ar", mGR.mPlayer.Good);
			mGR.mPlayer.over = prefs.getInt("as", mGR.mPlayer.over);
			mGR.mPlayer.bonus = prefs.getInt("at", mGR.mPlayer.bonus);
			mGR.mPlayer.price = prefs.getInt("au", mGR.mPlayer.price);
			mGR.mPlayer.gearChange = prefs.getInt("av", mGR.mPlayer.gearChange);
			mGR.mPlayer.Nitro = prefs.getInt("aw", mGR.mPlayer.Nitro);
			
			mGR.mPlayer.x = prefs.getFloat("ax", mGR.mPlayer.x);
			mGR.mPlayer.y = prefs.getFloat("ay", mGR.mPlayer.y);
			mGR.mPlayer.Dis = prefs.getFloat("az", mGR.mPlayer.Dis);
			mGR.mPlayer.Engine = prefs.getFloat("ba", mGR.mPlayer.Engine);
			mGR.mPlayer.Exhaust = prefs.getFloat("bb", mGR.mPlayer.Exhaust);
			mGR.mPlayer.Weight = prefs.getFloat("bc", mGR.mPlayer.Weight);
			mGR.mPlayer.GearBox = prefs.getFloat("bd", mGR.mPlayer.GearBox);
			mGR.mPlayer.rpm = prefs.getFloat("be", mGR.mPlayer.rpm);
		}
		
		
		{

			mGR.mOpponent.netro = prefs.getBoolean("bf", mGR.mOpponent.netro);
			
			mGR.mOpponent.Gear = prefs.getInt("bg", mGR.mOpponent.Gear);
			mGR.mOpponent.BikeNo = prefs.getInt("bh", mGR.mOpponent.BikeNo);
			mGR.mOpponent.m0to60 = prefs.getInt("bi", mGR.mOpponent.m0to60);
			mGR.mOpponent.m0to100 = prefs.getInt("bj", mGR.mOpponent.m0to100);
			mGR.mOpponent.time = prefs.getInt("bk", mGR.mOpponent.time);
			mGR.mOpponent.MaxSpeed = prefs.getInt("bl", mGR.mOpponent.MaxSpeed);
			mGR.mOpponent.parfect = prefs.getInt("bm", mGR.mOpponent.parfect);
			mGR.mOpponent.Good = prefs.getInt("bn", mGR.mOpponent.Good);
			mGR.mOpponent.over = prefs.getInt("bo", mGR.mOpponent.over);
			mGR.mOpponent.bonus = prefs.getInt("bp", mGR.mOpponent.bonus);
			mGR.mOpponent.price = prefs.getInt("bq", mGR.mOpponent.price);
			mGR.mOpponent.gearChange = prefs.getInt("br", mGR.mOpponent.gearChange);
			mGR.mOpponent.Nitro = prefs.getInt("bs", mGR.mOpponent.Nitro);
			
			mGR.mOpponent.x = prefs.getFloat("bt", mGR.mOpponent.x);
			mGR.mOpponent.y = prefs.getFloat("bu", mGR.mOpponent.y);
			mGR.mOpponent.Dis = prefs.getFloat("bv", mGR.mOpponent.Dis);
			mGR.mOpponent.Engine = prefs.getFloat("bw", mGR.mOpponent.Engine);
			mGR.mOpponent.Exhaust = prefs.getFloat("bx", mGR.mOpponent.Exhaust);
			mGR.mOpponent.Weight = prefs.getFloat("by", mGR.mOpponent.Weight);
			mGR.mOpponent.GearBox = prefs.getFloat("bz", mGR.mOpponent.GearBox);
			mGR.mOpponent.rpm = prefs.getFloat("ca", mGR.mOpponent.rpm);
		
		}
		
		
		
		
		
		
		
		
		
		mGR.resumeCounter = 0;
		
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

	public boolean onKeyDown(int keyCode, KeyEvent event) {
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
		return super.onKeyDown(keyCode,event); 
	}

	public boolean onKeyUp(int keyCode, KeyEvent event) {
		return super.onKeyUp(keyCode, event);
	}

	public void onDestroy() {
		super.onDestroy();
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
	void Donot()
	{
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("You do not have coin")
	    .setNeutralButton("Ok",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
      }}).show();
  }
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		/*if (!mGR.mMainActivity.mHelper.handleActivityResult(requestCode,resultCode, data)) {
			super.onActivityResult(requestCode, resultCode, data);
		} */
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

	public void showIntertitail() {
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
	AdView adHouse = null;//AdHouse
	private InterstitialAd interstitialAd;
	GameRenderer mGR = null;

	void callAdds() {
		adHouse = (AdView) this.findViewById(R.id.advhouse);
		AdRequest adRequest = new AdRequest.Builder().build();
		adHouse.loadAd(adRequest);
		adHouse.setAdListener(new AdListener() {
			public void onAdLoaded() {
				adHouse.bringToFront();
			}
		});
	}	
}