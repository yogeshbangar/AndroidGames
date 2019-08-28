package com.hututu.game.racerboy;
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
	private static Context CONTEXT;
	private InterstitialAd interstitial;
	boolean OnCreate = false;
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
		CONTEXT	=	this;
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
	    if(!getSharedPreferences("X", MODE_PRIVATE).getBoolean("addFree", mGR.addFree)){
	    	OnCreate = true;
	    	load();
	    	callAdds();
	    }
	}
	public static Context getContext() {
	        return CONTEXT;
	}
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
//			M.GameScreen = M.GAMEOVER;
			switch (M.GameScreen) {
			case M.GAMELOGO:
				break;
			case M.GAMEMENU:
				Exit();
				break;
			case M.GAMEPLAY:
				M.stop();
				M.GameScreen = M.GAMEPAUSE;
				break;
			case M.GAMEOVER:
			case M.GAMEABOUT:
			case M.GAMEHELP:
			case M.GAMESET:
			case M.GAMEPAUSE:
				M.GameScreen = M.GAMEMENU;
				M.play(R.drawable.splash);
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
		if(mGR.mInApp!=null)
			mGR.mInApp.onDestroy();
	}

	void pause() {
		if (M.GameScreen == M.GAMEPLAY)
			M.GameScreen = M.GAMEPAUSE;
		M.stop();
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putInt("screen", M.GameScreen);
		editor.putBoolean("setValue", M.setValue);
		editor.putBoolean("setMusic", M.setMusic);
		editor.putBoolean("addFree", mGR.addFree);

		editor.putBoolean("go", mGR.go);
		for (int i = 0; i < mGR.mAchiUnlock.length; i++)
			editor.putBoolean("mAchiUnlock" + i, mGR.mAchiUnlock[i]);
		editor.putBoolean("SingUpadate", mGR.SingUpadate);

		editor.putInt("Levelno", mGR.Levelno);
		editor.putInt("colli", mGR.colli);
		editor.putInt("mBG", mGR.mBG);
		editor.putInt("OverCunt", mGR.OverCunt);
		editor.putInt("goAnim", mGR.goAnim);

		editor.putFloat("Bestdistance", mGR.Bestdistance);
		editor.putFloat("TDistance", mGR.TDistance);
		for (int i = 0; i < mGR.bgbx.length; i++)
			editor.putFloat("bgbx" + i, mGR.bgbx[i]);
		for (int i = 0; i < mGR.bgfx.length; i++)
			editor.putFloat("bgfx" + i, mGR.bgfx[i]);
		editor.putFloat("mScore", mGR.mScore);

		for (int i = 0; i < mGR.mTile.length; i++) {
			editor.putInt("mTiled" + i, mGR.mTile[i].down);
			for (int j = 0; j < mGR.mTile[i].arry.length; j++) {
				editor.putInt(i + "mTilearry" + j, mGR.mTile[i].arry[j]);
			}
			editor.putFloat("mGR.mTile[i].x" + i, mGR.mTile[i].x);
		}
		editor.putString("pname", mGR.mPName);
		{
			editor.putInt("mGuyd", mGR.mGuy.down);
			editor.putInt("mGuycn", mGR.mGuy.colNo);

			editor.putFloat("mGuyx", mGR.mGuy.x);
			editor.putFloat("mGuyy", mGR.mGuy.y);
			editor.putFloat("mGuyspd", mGR.mGuy.spd);
		}
		{
			editor.putInt("mOpptd", mGR.mOppt.down);
			editor.putInt("mOpptcn", mGR.mOppt.colNo);

			editor.putFloat("mOpptx", mGR.mOppt.x);
			editor.putFloat("mOppty", mGR.mOppt.y);
			editor.putFloat("mOpptspd", mGR.mOppt.spd);
		}

		editor.commit();
	}

	void resume() {
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = prefs.getInt("screen", M.GAMELOGO);
		M.setValue = prefs.getBoolean("setValue", M.setValue);
		mGR.addFree = prefs.getBoolean("addFree", false);
		M.setMusic = prefs.getBoolean("setMusic", M.setMusic);

		mGR.go = prefs.getBoolean("go", mGR.go);
		for (int i = 0; i < mGR.mAchiUnlock.length; i++)
			mGR.mAchiUnlock[i] = prefs.getBoolean("mAchiUnlock" + i, mGR.mAchiUnlock[i]);
		mGR.SingUpadate = prefs.getBoolean("SingUpadate", mGR.SingUpadate);

		mGR.Levelno = prefs.getInt("Levelno", mGR.Levelno);
		mGR.colli = prefs.getInt("colli", mGR.colli);
		mGR.mBG = prefs.getInt("mBG", mGR.mBG);
		mGR.OverCunt = prefs.getInt("OverCunt", mGR.OverCunt);
		mGR.goAnim = prefs.getInt("goAnim", mGR.goAnim);

		mGR.Bestdistance = prefs.getFloat("Bestdistance", mGR.Bestdistance);
		mGR.TDistance = prefs.getFloat("TDistance", mGR.TDistance);
		for (int i = 0; i < mGR.bgbx.length; i++)
			mGR.bgbx[i] = prefs.getFloat("bgbx" + i, mGR.bgbx[i]);
		for (int i = 0; i < mGR.bgfx.length; i++)
			mGR.bgfx[i] = prefs.getFloat("bgfx" + i, mGR.bgfx[i]);
		mGR.mScore = prefs.getFloat("mScore", mGR.mScore);

		for (int i = 0; i < mGR.mTile.length; i++) {
			mGR.mTile[i].down = (byte)prefs.getInt("mTiled" + i, mGR.mTile[i].down);
			for (int j = 0; j < mGR.mTile[i].arry.length; j++) {
				mGR.mTile[i].arry[j] = (short)prefs.getInt(i + "mTilearry" + j, mGR.mTile[i].arry[j]);
			}
			mGR.mTile[i].x = prefs.getFloat("mGR.mTile[i].x" + i, mGR.mTile[i].x);
		}
		mGR.mPName = prefs.getString("pname", mGR.mPName);
		{
			mGR.mGuy.down = (byte)prefs.getInt("mGuyd", mGR.mGuy.down);
			mGR.mGuy.colNo = prefs.getInt("mGuycn", mGR.mGuy.colNo);

			mGR.mGuy.x = prefs.getFloat("mGuyx", mGR.mGuy.x);
			mGR.mGuy.y = prefs.getFloat("mGuyy", mGR.mGuy.y);
			mGR.mGuy.spd = prefs.getFloat("mGuyspd", mGR.mGuy.spd);
		}
		{
			mGR.mOppt.down = (byte)prefs.getInt("mOpptd", mGR.mOppt.down);
			mGR.mOppt.colNo = prefs.getInt("mOpptcn", mGR.mOppt.colNo);

			mGR.mOppt.x = prefs.getFloat("mOpptx", mGR.mOppt.x);
			mGR.mOppt.y = prefs.getFloat("mOppty", mGR.mOppt.y);
			mGR.mOppt.spd = prefs.getFloat("mOpptspd", mGR.mOppt.spd);
		}

		if (M.GameScreen == M.GAMEMENU) {
			M.play(R.drawable.splash);
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
	public void load() {
		if(!mGR.addFree)
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
		if (!mGR.SingUpadate) {
			for (int i = 0; i < mGR.mAchiUnlock.length; i++) {
				if (mGR.mAchiUnlock[i]) {
					UnlockAchievement(M.ACHIV[i]);
				}
			}
			GameRenderer.mStart.Submitscore(R.string.leaderboard_best_score, (int)mGR.Bestdistance);
			mGR.SingUpadate = true;
			
//			mGR.mPName = getGamesClient().getCurrentPlayer().getDisplayName().substring(0, 9);
			
			mGR.mPName = Games.Players.getCurrentPlayer(getApiClient()).getDisplayName();
			if(mGR.mPName.length() > 26)
				mGR.mPName = mGR.mPName.substring(0, 26);
			
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
		if (!mGR.mInApp.mHelper.handleActivityResult(requestCode,resultCode, data)) {
			super.onActivityResult(requestCode, resultCode, data);
		} else {
		}
	}
	void Achivement(){
		if(!mGR.mAchiUnlock[0] && mGR.Bestdistance > 1000){
			mGR.mAchiUnlock[0] = true;
			UnlockAchievement(M.ACHIV[0]);
		}
		if(!mGR.mAchiUnlock[1] && mGR.Bestdistance > 5000){
			mGR.mAchiUnlock[1] = true;
			UnlockAchievement(M.ACHIV[1]);
		}
		if(!mGR.mAchiUnlock[2] && mGR.Bestdistance > 7000){
			mGR.mAchiUnlock[2] = true;
			UnlockAchievement(M.ACHIV[2]);
		}
		if(!mGR.mAchiUnlock[3] && mGR.TDistance > 50000){
			mGR.mAchiUnlock[3] = true;
			UnlockAchievement(M.ACHIV[3]);
		}
		if(!mGR.mAchiUnlock[4] && mGR.TDistance > 200000){
			mGR.mAchiUnlock[4] = true;
			UnlockAchievement(M.ACHIV[4]);
		}
		GameRenderer.mStart.Submitscore(R.string.leaderboard_best_score, (int)(mGR.Bestdistance));
	}
}