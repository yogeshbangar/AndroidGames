package com.hututu.games.monkeyjump;

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
	    if(!getSharedPreferences("X", MODE_PRIVATE).getBoolean("addFree", mGR.addFree))
	    	callAdds();
	}
	public static Context getContext() {
	        return CONTEXT;
	}
	@Override 
	public void onPause () {
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
//			M.GameScreen = M.GAMEOPTION;
			switch (M.GameScreen) {
			case M.GAMELOGO:
				break;
			case M.GAMEABOUT:
			case M.GAMEHELP:
			case M.GAMEPAUSE:
			case M.GAMEOPTION:
				M.GameScreen = M.GAMEMENU;
				M.sound17(GameRenderer.mContext, R.drawable.start);
				break;
			case M.GAMEBUY:
				M.GameScreen = M.GAMEOPTION;
				break;
			case M.GAMEOVER:
				M.stop();
				M.GameScreen = M.GAMEMENU;
				M.sound17(GameRenderer.mContext, R.drawable.start);
				break;
			case M.GAMEMENU:
				Exit();
				break;
			case M.GAMEPLAY:
				M.sound8Pause();
				M.stop();
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
		if(mGR.mInApp!=null)
			mGR.mInApp.onDestroy();
	}
	void pause()
	{
		if(M.GameScreen == M.GAMEPLAY)
			M.GameScreen = M.GAMEPAUSE;
		M.sound8Pause();
		M.stop();
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
	    Editor editor = prefs.edit();
	    editor.putInt("screen", M.GameScreen);
	    editor.putBoolean("setValue", M.setValue);
	    editor.putBoolean("setMusic", M.setMusic);
	    editor.putBoolean("addFree", mGR.addFree);
	    editor.putBoolean("SingUpadate", mGR.SingUpadate);
	    editor.putBoolean("fromgame", mGR.fromGame);
	    
	    
	    for(int i =0;i<mGR.mAchiUnlock.length;i++)
	    	editor.putBoolean("mAchiUnlock"+i, mGR.mAchiUnlock[i]);
		
	    for(int i =0;i<mGR.BGy.length;i++)
	    	editor.putFloat("BGy"+i, mGR.BGy[i]);
		
	    editor.putFloat("mPowerx", mGR.mPower.x);
	    editor.putFloat("mPowery", mGR.mPower.y);

	    {
	    	editor.putBoolean("Playerpr", mGR.mPlayer.Power);
	    	editor.putBoolean("Playerrpr", mGR.mPlayer.resetPower);
		    
	    	editor.putInt("Playerpt", mGR.mPlayer.PowerTime);
	    	
	    	editor.putInt("PlayerTPower", mGR.mPlayer.TPower);
	    	editor.putInt("Playerco", mGR.mPlayer.cout);
	    	editor.putInt("Playeropp", mGR.mPlayer.opp);
	    	editor.putInt("PlayeroppCont", mGR.mPlayer.oppCont);
	    	editor.putInt("PlayerActoin", mGR.mPlayer.Actoin);
	    	editor.putInt("PlayerHardle", mGR.mPlayer.Hardle);
	    	editor.putInt("PlayerresetCount", mGR.mPlayer.resetCount);
	    	editor.putInt("Playermg2", mGR.mPlayer.mg2);
	    	
	    	editor.putFloat("Playerx", mGR.mPlayer.x);
	    	editor.putFloat("Playery", mGR.mPlayer.y);
	    	editor.putFloat("Playervx", mGR.mPlayer.vx);
	    	editor.putFloat("Playervy", mGR.mPlayer.vy);
	    	editor.putFloat("Playerspeed", mGR.mPlayer.speed);
	    	editor.putFloat("Playerdis", mGR.mPlayer.distance);
	    	editor.putFloat("PlayerBest", mGR.mPlayer.Bestdistance);
	    	editor.putFloat("PlayerTDistance", mGR.mPlayer.TDistance);
	    }
	    
		for (int i = 0; i < mGR.mTile.length; i++) {
			editor.putFloat("Tilex" + i, mGR.mTile[i].x);
			editor.putFloat("Tiley" + i, mGR.mTile[i].y);
			editor.putInt("Tileh" + i, mGR.mTile[i].hardle);
		}
		
		
		for (int i = 0; i < mGR.mGilhari.length; i++) {
			editor.putBoolean("Gilharih" + i, mGR.mGilhari[i].isgildhari);
			editor.putFloat("Gilharix" + i, mGR.mGilhari[i].x);
			editor.putFloat("Gilhariy" + i, mGR.mGilhari[i].y);
			editor.putFloat("Gilharivx" + i, mGR.mGilhari[i].vx);
		}
		
		for (int i = 0; i < mGR.mChidia.length; i++) {
			editor.putInt("Chidiah" + i, mGR.mChidia[i].counter);
			editor.putFloat("Chidiax" + i, mGR.mChidia[i].x);
			editor.putFloat("Chidiay" + i, mGR.mChidia[i].y);
			editor.putFloat("Chidiavx" + i, mGR.mChidia[i].vx);
		}
		
		for (int i = 0; i < mGR.mChakraTrow.length; i++) {
			editor.putFloat("ChakraTrowx" + i, mGR.mChakraTrow[i].x);
			editor.putFloat("ChakraTrowy" + i, mGR.mChakraTrow[i].y);
			editor.putBoolean("ChakraTrowh" + i, mGR.mChakraTrow[i].isTrow);
		}
		
		for (int i = 0; i < mGR.mSudarshan.length; i++) {
			editor.putFloat("Sudarshanx" + i, mGR.mSudarshan[i].x);
			editor.putFloat("Sudarshany" + i, mGR.mSudarshan[i].y);
			editor.putFloat("Sudarshanvx" + i, mGR.mSudarshan[i].vx);
			editor.putFloat("Sudarshanvy" + i, mGR.mSudarshan[i].vy);
			editor.putBoolean("Sudarshanh" + i, mGR.mSudarshan[i].isRokect);
		}
		for (int i = 0; i < mGR.mVillain.length; i++) {
			editor.putFloat("Villainx" + i, mGR.mVillain[i].x);
			editor.putFloat("Villainy" + i, mGR.mVillain[i].y);
			editor.putFloat("Villainh" + i, mGR.mVillain[i].vy);
		}
	    editor.commit();
	}
	void resume()
	{
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = prefs.getInt("screen", M.GAMELOGO);
		M.setValue = prefs.getBoolean("setValue", true);
		M.setMusic = prefs.getBoolean("setMusic", true);
		mGR.addFree = prefs.getBoolean("addFree", false);
		mGR.SingUpadate = prefs.getBoolean("SingUpadate", false);
		mGR.fromGame = prefs.getBoolean("fromgame", mGR.fromGame);
	    for(int i =0;i<mGR.mAchiUnlock.length;i++)
	    	mGR.mAchiUnlock[i] = prefs.getBoolean("mAchiUnlock"+i, false);
		
	    for(int i =0;i<mGR.BGy.length;i++)
	    	mGR.BGy[i] = prefs.getFloat("BGy"+i, mGR.BGy[i]);
		
	    mGR.mPower.x = prefs.getFloat("mPowerx", mGR.mPower.x);
	    mGR.mPower.y = prefs.getFloat("mPowery", mGR.mPower.y);

	    {
	    	mGR.mPlayer.Power = prefs.getBoolean("Playerpr", mGR.mPlayer.Power);
	    	mGR.mPlayer.resetPower = prefs.getBoolean("Playerrpr", mGR.mPlayer.resetPower);
		    
	    	mGR.mPlayer.PowerTime = (byte)prefs.getInt("Playerpt", mGR.mPlayer.PowerTime);
	    	
	    	mGR.mPlayer.TPower = prefs.getInt("PlayerTPower", 1);
	    	mGR.mPlayer.cout = prefs.getInt("Playerco", mGR.mPlayer.cout);
	    	mGR.mPlayer.opp = prefs.getInt("Playeropp", mGR.mPlayer.opp);
	    	mGR.mPlayer.oppCont = prefs.getInt("PlayeroppCont", mGR.mPlayer.oppCont);
	    	mGR.mPlayer.Actoin = prefs.getInt("PlayerActoin", mGR.mPlayer.Actoin);
	    	mGR.mPlayer.Hardle = prefs.getInt("PlayerHardle", mGR.mPlayer.Hardle);
	    	mGR.mPlayer.resetCount = prefs.getInt("PlayerresetCount", mGR.mPlayer.resetCount);
	    	mGR.mPlayer.mg2 = prefs.getInt("Playermg2", mGR.mPlayer.mg2);
	    	
	    	mGR.mPlayer.x = prefs.getFloat("Playerx", mGR.mPlayer.x);
	    	mGR.mPlayer.y = prefs.getFloat("Playery", mGR.mPlayer.y);
	    	mGR.mPlayer.vx = prefs.getFloat("Playervx", mGR.mPlayer.vx);
	    	mGR.mPlayer.vy = prefs.getFloat("Playervy", mGR.mPlayer.vy);
	    	mGR.mPlayer.speed = prefs.getFloat("Playerspeed", mGR.mPlayer.speed);
	    	mGR.mPlayer.distance = prefs.getFloat("Playerdis", mGR.mPlayer.distance);
	    	mGR.mPlayer.Bestdistance = prefs.getFloat("PlayerBest", mGR.mPlayer.Bestdistance);
	    	mGR.mPlayer.TDistance = prefs.getFloat("PlayerTDistance", mGR.mPlayer.TDistance);
	    }
	    
		for (int i = 0; i < mGR.mTile.length; i++) {
			mGR.mTile[i].x = prefs.getFloat("Tilex" + i, mGR.mTile[i].x);
			mGR.mTile[i].y = prefs.getFloat("Tiley" + i, mGR.mTile[i].y);
			mGR.mTile[i].hardle = prefs.getInt("Tileh" + i, mGR.mTile[i].hardle);
		}
		
		
		for (int i = 0; i < mGR.mGilhari.length; i++) {
			mGR.mGilhari[i].isgildhari = prefs.getBoolean("Gilharih" + i, mGR.mGilhari[i].isgildhari);
			mGR.mGilhari[i].x = prefs.getFloat("Gilharix" + i, mGR.mGilhari[i].x);
			mGR.mGilhari[i].y = prefs.getFloat("Gilhariy" + i, mGR.mGilhari[i].y);
			mGR.mGilhari[i].vx = prefs.getFloat("Gilharivx" + i, mGR.mGilhari[i].vx);
		}
		
		for (int i = 0; i < mGR.mChidia.length; i++) {
			mGR.mChidia[i].counter = prefs.getInt("Chidiah" + i, mGR.mChidia[i].counter);
			mGR.mChidia[i].x = prefs.getFloat("Chidiax" + i, mGR.mChidia[i].x);
			mGR.mChidia[i].y = prefs.getFloat("Chidiay" + i, mGR.mChidia[i].y);
			mGR.mChidia[i].vx = prefs.getFloat("Chidiavx" + i, mGR.mChidia[i].vx);
		}
		
		for (int i = 0; i < mGR.mChakraTrow.length; i++) {
			mGR.mChakraTrow[i].x = prefs.getFloat("ChakraTrowx" + i, mGR.mChakraTrow[i].x);
			mGR.mChakraTrow[i].y = prefs.getFloat("ChakraTrowy" + i, mGR.mChakraTrow[i].y);
			mGR.mChakraTrow[i].isTrow = prefs.getBoolean("ChakraTrowh" + i, mGR.mChakraTrow[i].isTrow);
		}
		
		for (int i = 0; i < mGR.mSudarshan.length; i++) {
			mGR.mSudarshan[i].x = prefs.getFloat("Sudarshanx" + i, mGR.mSudarshan[i].x);
			mGR.mSudarshan[i].y = prefs.getFloat("Sudarshany" + i, mGR.mSudarshan[i].y);
			mGR.mSudarshan[i].vx = prefs.getFloat("Sudarshanvx" + i, mGR.mSudarshan[i].vx);
			mGR.mSudarshan[i].vy = prefs.getFloat("Sudarshanvy" + i, mGR.mSudarshan[i].vy);
			mGR.mSudarshan[i].isRokect = prefs.getBoolean("Sudarshanh" + i, mGR.mSudarshan[i].isRokect);
		}
		for (int i = 0; i < mGR.mVillain.length; i++) {
			mGR.mVillain[i].x = prefs.getFloat("Villainx" + i, mGR.mVillain[i].x);
			mGR.mVillain[i].y = prefs.getFloat("Villainy" + i, mGR.mVillain[i].y);
			mGR.mVillain[i].vy = prefs.getFloat("Villainh" + i, mGR.mVillain[i].vy);
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
			GameRenderer.mStart.Submitscore(R.string.leaderboard_best_score, (int)(mGR.mPlayer.Bestdistance*10));
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
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (!mGR.mInApp.mHelper.handleActivityResult(requestCode,resultCode, data)) {
			super.onActivityResult(requestCode, resultCode, data);
		} else {
		}
	}
	
	void Achivement(){
		if(!mGR.mAchiUnlock[0] && mGR.mPlayer.Bestdistance > 500){
			mGR.mAchiUnlock[0] = true;
			UnlockAchievement(M.ACHIV[0]);
		}
		if(!mGR.mAchiUnlock[1] && mGR.mPlayer.Bestdistance > 700){
			mGR.mAchiUnlock[1] = true;
			UnlockAchievement(M.ACHIV[1]);
		}
		if(!mGR.mAchiUnlock[2] && mGR.mPlayer.Bestdistance > 1200){
			mGR.mAchiUnlock[2] = true;
			UnlockAchievement(M.ACHIV[2]);
		}
		if(!mGR.mAchiUnlock[3] && mGR.mPlayer.TDistance > 7500){
			mGR.mAchiUnlock[3] = true;
			UnlockAchievement(M.ACHIV[3]);
		}
		if(!mGR.mAchiUnlock[4] && mGR.mPlayer.TDistance > 20000){
			mGR.mAchiUnlock[4] = true;
			UnlockAchievement(M.ACHIV[4]);
		}
		GameRenderer.mStart.Submitscore(R.string.leaderboard_best_score, (int)(mGR.mPlayer.Bestdistance*10));
	}
}