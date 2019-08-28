package com.hututu.games.freekickshoot;


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
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class Start extends BaseGameActivity
{
	int _keyCode = 0;
	AdView adView = null;
	GameRenderer mGR = null;
	private static Context CONTEXT;
	private InterstitialAd interstitialAd;

	void callAdds() {
		adView = (AdView) this.findViewById(R.id.addgame);
		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);
		adView.setAdListener(new AdListener() {
			public void onAdLoaded() {
				adView.bringToFront();
			}
		});
	}
	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);// OnlyOneChange
		CONTEXT = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId(getString(R.string.INTERSTITIAL_ID));
	    
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
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			switch (M.GameScreen) {
			default:
				M.GameScreen = M.GAMEMENU;
				break;
			case M.GAMEMENU:
				Exit();
				break;
			case M.GAMEPLAY:
				M.GameScreen = M.GAMEPAUSE;
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
	}

	void pause() {
		if (M.GameScreen == M.GAMEPLAY)
			M.GameScreen = M.GAMEPAUSE;
		M.stop(GameRenderer.mContext);
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putInt("screen", M.GameScreen);
		editor.putBoolean("setValue", M.setValue);
		
		editor.putBoolean("Goal", mGR.Goal);
		editor.putBoolean("addFree", mGR.addFree);
		editor.putBoolean("SingUpadate", mGR.SingUpadate);
		for(int i=0;i<mGR.Achi.length;i++)
			editor.putBoolean("Achi"+i, mGR.Achi[i]);
		
		editor.putInt("mULvl", mGR.mULvl);
		editor.putInt("Level", mGR.Level);
		editor.putInt("total", mGR.total);
		editor.putInt("mBall", mGR.mBall);
		editor.putInt("mType", mGR.mType);
		editor.putInt("Deewar", mGR.Deewar);
		for(int i=0;i<mGR.mBest.length;i++)
			editor.putInt("mBest"+i, mGR.mBest[i]);
		
		editor.putFloat("DeewarX", mGR.DeewarX);
		
		{
			editor.putBoolean("mPlayermove", mGR.mPlayer.move);
			editor.putBoolean("mPlayerpmove", mGR.mPlayer.pmove);

			editor.putInt("mPlayerpno", mGR.mPlayer.pno);
			editor.putInt("mPlayeranim", mGR.mPlayer.anim);
			editor.putInt("mPlayerpani", mGR.mPlayer.pani);
			editor.putInt("mPlayerrset", mGR.mPlayer.rset);

			editor.putFloat("mPlayerbx", mGR.mPlayer.bx);
			editor.putFloat("mPlayerby", mGR.mPlayer.by);
			editor.putFloat("mPlayerbz", mGR.mPlayer.bz);
			editor.putFloat("mPlayerbvx", mGR.mPlayer.bvx);
			editor.putFloat("mPlayerbvy", mGR.mPlayer.bvy);
			editor.putFloat("mPlayerbgx", mGR.mPlayer.bgx);
			editor.putFloat("mPlayersx", mGR.mPlayer.sx);
			editor.putFloat("mPlayersy", mGR.mPlayer.sy);
			editor.putFloat("mPlayerang", mGR.mPlayer.ang);
			for (int i = 0; i < mGR.mPlayer.px.length; i++)
				editor.putFloat("mPlayerpx" + i, mGR.mPlayer.px[i]);
			for (int i = 0; i < mGR.mPlayer.py.length; i++)
				editor.putFloat("mPlayerpy" + i, mGR.mPlayer.py[i]);

		}
		{
		
			editor.putInt("mKeeperanim", mGR.mKeeper.anim);
			editor.putInt("mKeeperimg", mGR.mKeeper.img);
			editor.putInt("mKeeperrcount", mGR.mKeeper.counter);

			editor.putFloat("mKeeperx", mGR.mKeeper.x);
			editor.putFloat("mKeepery", mGR.mKeeper.y);
			editor.putFloat("mKeeperay", mGR.mKeeper.ay);
		
			
		}
		
		editor.commit();
	}

	void resume() {
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = prefs.getInt("screen", M.GAMELOGO);
		M.setValue = prefs.getBoolean("setValue", M.setValue);
		
		mGR.Goal = prefs.getBoolean("Goal", mGR.Goal);
		mGR.addFree = prefs.getBoolean("addFree", mGR.addFree);
		mGR.SingUpadate = prefs.getBoolean("SingUpadate", mGR.SingUpadate);
		for(int i=0;i<mGR.Achi.length;i++)
			mGR.Achi[i] = prefs.getBoolean("Achi"+i, mGR.Achi[i]);
		
		mGR.mULvl = prefs.getInt("mULvl", mGR.mULvl);
		mGR.Level = prefs.getInt("Level", mGR.Level);
		mGR.total = prefs.getInt("total", mGR.total);
		mGR.mBall = prefs.getInt("mBall", mGR.mBall);
		mGR.mType = prefs.getInt("mType", mGR.mType);
		mGR.Deewar = prefs.getInt("Deewar", mGR.Deewar);
		for(int i=0;i<mGR.mBest.length;i++)
			mGR.mBest[i] = prefs.getInt("mBest"+i, mGR.mBest[i]);
		
		mGR.DeewarX = prefs.getFloat("DeewarX", mGR.DeewarX);
		
		{
			mGR.mPlayer.move = prefs.getBoolean("mPlayermove", mGR.mPlayer.move);
			mGR.mPlayer.pmove = prefs.getBoolean("mPlayerpmove", mGR.mPlayer.pmove);

			mGR.mPlayer.pno = prefs.getInt("mPlayerpno", mGR.mPlayer.pno);
			mGR.mPlayer.anim = prefs.getInt("mPlayeranim", mGR.mPlayer.anim);
			mGR.mPlayer.pani = prefs.getInt("mPlayerpani", mGR.mPlayer.pani);
			mGR.mPlayer.rset = prefs.getInt("mPlayerrset", mGR.mPlayer.rset);

			mGR.mPlayer.bx = prefs.getFloat("mPlayerbx", mGR.mPlayer.bx);
			mGR.mPlayer.by= prefs.getFloat("mPlayerby", mGR.mPlayer.by);
			mGR.mPlayer.bz = prefs.getFloat("mPlayerbz", mGR.mPlayer.bz);
			mGR.mPlayer.bvx = prefs.getFloat("mPlayerbvx", mGR.mPlayer.bvx);
			mGR.mPlayer.bvy = prefs.getFloat("mPlayerbvy", mGR.mPlayer.bvy);
			mGR.mPlayer.bgx = prefs.getFloat("mPlayerbgx", mGR.mPlayer.bgx);
			mGR.mPlayer.sx = prefs.getFloat("mPlayersx", mGR.mPlayer.sx);
			mGR.mPlayer.sy = prefs.getFloat("mPlayersy", mGR.mPlayer.sy);
			mGR.mPlayer.ang = prefs.getFloat("mPlayerang", mGR.mPlayer.ang);
			for (int i = 0; i < mGR.mPlayer.px.length; i++)
				mGR.mPlayer.px[i] = prefs.getFloat("mPlayerpx" + i, mGR.mPlayer.px[i]);
			for (int i = 0; i < mGR.mPlayer.py.length; i++)
				mGR.mPlayer.py[i] = prefs.getFloat("mPlayerpy" + i, mGR.mPlayer.py[i]);

		}
		{
			mGR.mKeeper.anim = prefs.getInt("mKeeperanim", mGR.mKeeper.anim);
			mGR.mKeeper.img = prefs.getInt("mKeeperimg", mGR.mKeeper.img);
			mGR.mKeeper.counter = prefs.getInt("mKeeperrcount", mGR.mKeeper.counter);

			mGR.mKeeper.x = prefs.getFloat("mKeeperx", mGR.mKeeper.x);
			mGR.mKeeper.y = prefs.getFloat("mKeepery", mGR.mKeeper.y);
			mGR.mKeeper.ay = prefs.getFloat("mKeeperay", mGR.mKeeper.ay);
		}
		
		
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
				for(int i=0;i<mGR.mBest.length;i++)
				{
					GameRenderer.mStart.Submitscore(M.scoreID[i], mGR.mBest[i]);
				}
			}
			mGR.SingUpadate = true;
			
		}
	}
	public void Submitscore(final int ID,int total) {
		if (!isSignedIn()) {
//			beginUserInitiatedSignIn();
		} else {Games.Leaderboards.submitScore(getApiClient(), getString(ID), total);
		}
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

	void loadInter() {
		if (!interstitialAd.isLoaded() && !mGR.addFree) {
			AdRequest adRequest = new AdRequest.Builder().build();
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