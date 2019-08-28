package com.hututu.game.footballshoot2014;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.games.Games;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class Start extends BaseGameActivity{
	int _keyCode = 0;
	AdView adView = null;
	AdView adHouse = null;//AdHouse
	GameRenderer mGR = null;
	private static Context CONTEXT;
	private InterstitialAd interstitialAd;

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

		{
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
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);// OnlyOneChange
		CONTEXT = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId(getString(R.string.Interstitial));
		WindowManager mWinMgr = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		M.ScreenWidth = mWinMgr.getDefaultDisplay().getWidth();
		M.ScreenHieght = mWinMgr.getDefaultDisplay().getHeight();
		mGR = new GameRenderer(this);
		VortexView glSurface = (VortexView) findViewById(R.id.vortexview); 
		glSurface.setRenderer(mGR);
		glSurface.showRenderer(mGR);
		if (!getSharedPreferences("X", MODE_PRIVATE).getBoolean("addFree", mGR.addFree))
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
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
//		Log.d("----------------=>  "+keyCode,"   -----------    ");
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			switch (M.GameScreen) {
			case M.GAMEMENU:
				Exit();
				break;
			case M.GAMEPLAY:
				M.GameScreen = M.GAMEPAUSE;
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
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) 
	{
		if(keyCode==KeyEvent.KEYCODE_BACK)
			return false;
		_keyCode = 0;
		return super.onKeyUp( keyCode, event ); 
	}
	public void onDestroy()
	{
		super.onDestroy();
		if(mGR.mMainActivity!=null)
			mGR.mMainActivity.onDestroy();
	}
	void pause()
	{
		if(M.GameScreen == M.GAMEPLAY)
		{
			M.GameScreen = M.GAMEPAUSE;
		}
		M.stop(GameRenderer.mContext);
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
	    Editor editor = prefs.edit();
	    editor.putInt("screen", M.GameScreen);
	    editor.putBoolean("setValue", M.setValue);
	    
	    editor.putBoolean("addFree", mGR.addFree);
	  
	    editor.putBoolean("SingUpadate", mGR.SingUpadate);
	    editor.putFloat("mStonex", mGR.mStone.x);
	    editor.putFloat("mStoney", mGR.mStone.y);
	    editor.putFloat("mStonez", mGR.mStone.z);
	    editor.putFloat("mStonevx", mGR.mStone.vx);
	    editor.putFloat("mStonevy", mGR.mStone.vy);
	    editor.putFloat("mStonevz", mGR.mStone.vz);
	    
	    editor.putInt("mStonec", mGR.mStone.counter);
	    
	    editor.putBoolean("mStonet", mGR.mStone.tuch);
	    editor.putBoolean("mStonein", mGR.mStone.inside);
	    editor.putBoolean("mStoneis", mGR.mStone.isNeg);
	    
	    
		editor.putInt("mStoneno", mGR.mStone.no);
		editor.putInt("mStonereset", mGR.mStone.reset);
		editor.putInt("mStonebani", mGR.mStone.bani);
		editor.putInt("mStonetotal", mGR.mStone.total);
	    
	    
	    editor.putInt("mScore", mGR.mScore);
	    for(int i=0;i<mGR.mLScore.length;i++)
	    	editor.putInt("mLScore"+i, mGR.mLScore[i]);
	    
	    editor.putInt("mLevel", mGR.mLevel);
	    editor.putInt("mBG", mGR.mBG);
	    
	    editor.putBoolean("isFromMenu", mGR.isFromMenu);
	    
	    
	    editor.commit();
	}
	void resume()
	{
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = prefs.getInt("screen", M.GAMELOGO);
		M.setValue = prefs.getBoolean("setValue", M.setValue);
		mGR.addFree = prefs.getBoolean("addFree", mGR.addFree);
		mGR.SingUpadate = prefs.getBoolean("SingUpadate", mGR.SingUpadate);
		
		mGR.mStone.x = prefs.getFloat("mStonex", mGR.mStone.x);
		mGR.mStone.y = prefs.getFloat("mStoney", mGR.mStone.y);
		mGR.mStone.z = prefs.getFloat("mStonez", mGR.mStone.z);
		mGR.mStone.vx = prefs.getFloat("mStonevx", mGR.mStone.vx);
		mGR.mStone.vy = prefs.getFloat("mStonevy", mGR.mStone.vy);
		mGR.mStone.vz = prefs.getFloat("mStonevz", mGR.mStone.vz);
	    
		mGR.mStone.counter = prefs.getInt("mStonec", mGR.mStone.counter);
	    
		mGR.mStone.tuch = prefs.getBoolean("mStonet", mGR.mStone.tuch);
		mGR.mStone.inside = prefs.getBoolean("mStonein", mGR.mStone.inside);
		mGR.mStone.isNeg = prefs.getBoolean("mStoneis", mGR.mStone.isNeg);
	    
		mGR.mStone.no = prefs.getInt("mStoneno", mGR.mStone.no);
		mGR.mStone.reset = prefs.getInt("mStonereset", mGR.mStone.reset);
		mGR.mStone.bani = prefs.getInt("mStonebani", mGR.mStone.bani);
		mGR.mStone.total = prefs.getInt("mStonetotal", mGR.mStone.total);
		
		mGR.mScore = prefs.getInt("mScore", mGR.mScore);
	    
		for (int i = 0; i < mGR.mLScore.length; i++)
			mGR.mLScore[i] = prefs.getInt("mLScore" + i, mGR.mLScore[i]);
	    
		mGR.mLevel = prefs.getInt("mLevel", mGR.mLevel);
		mGR.mBG = prefs.getInt("mBG", mGR.mBG);
	    
		mGR.isFromMenu = prefs.getBoolean("isFromMenu", mGR.isFromMenu);

		
	    mGR.resumeCounter = 0;
	}
	void Exit()
	{
		M.GameScreen = M.GAMEEXIT;
//	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Do you want to exit?")
//	    .setPositiveButton("No",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
//		       }}).setNegativeButton("Yes",new DialogInterface.OnClickListener() {
//		    	   public void onClick(DialogInterface dialog, int which) {
//		    		   finish();
//		    		   mGR.root.Counter =0;
//		    		   M.GameScreen=M.GAMELOGO;
//		       }}).show();
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
		if(!mGR.SingUpadate)
		{
			for(int i=0;i<mGR.Achi.length;i++)
			{
				if(mGR.Achi[i])
				{
					UnlockAchievement(M.achiment[i]);
				}
			}
			{
				for(int i=0;i<mGR.mLScore.length;i++)
				{
					GameRenderer.mStart.Submitscore(mGR.root.scoreID[i], mGR.mLScore[i]);
				}
			}
			mGR.SingUpadate = true;
		}
	}
	public void Submitscore(final int ID,int total) {
		if (!isSignedIn()) {
//			beginUserInitiatedSignIn();
		} else { Games.Leaderboards.submitScore(getApiClient(), getString(ID), total);
		}
	}

	int RC_UNUSED = 5001;

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
	void load(){
		if (!interstitialAd.isLoaded() && !mGR.addFree) {
			AdRequest adRequest = new AdRequest.Builder()
//					.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//					.addTestDevice("67701763FB847AEBD3C6EE486475ED94")
			.build();
			interstitialAd.loadAd(adRequest);
			interstitialAd.setAdListener(new GameAdListener(this));
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