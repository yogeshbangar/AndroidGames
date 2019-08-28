package com.hututu.game.santafreerunner;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
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
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Start extends BaseGameActivity 
{
	int _keyCode = 0;
	AdView adView = null;
	AdView adBig = null;
	AdView adHouse = null;//AdHouse
	GameRenderer mGR = null;
	private static Context CONTEXT;

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
			adBig = (AdView) this.findViewById(R.id.advbig);
			AdRequest adRequest = new AdRequest.Builder().build();
			adBig.loadAd(adRequest);
			adBig.setAdListener(new AdListener() {
				public void onAdLoaded() {
					adBig.bringToFront();
				}
			});
		}
		{
			adHouse = (AdView) this.findViewById(R.id.advhous);
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
	public void onCreate(Bundle savedInstanceState) 
	{
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);//OnlyOneChange
		CONTEXT	=	this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		WindowManager mWinMgr = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
		M.ScreenWidth =mWinMgr.getDefaultDisplay().getWidth();
		M.ScreenHieght=mWinMgr.getDefaultDisplay().getHeight();
		mGR=new GameRenderer(this);
	    VortexView glSurface=(VortexView) findViewById(R.id.vortexview); // use the xml to set the view
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
		Log.d("----------------=>  "+keyCode,"   -----------    ");
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			switch (M.GameScreen) {
			case M.GAMEPLAY:
				M.playStop();
				M.GameScreen = M.GAMEPAUSE;
				break;
			case M.GAMEMENU:
				Exit();
				break;
			default:
				mGR.root.mm=0;
				M.GameScreen = M.GAMEMENU;
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
	void pause()
	{
		System.out.println("[x = "+mGR.mPlayer.x+"] [y = "+mGR.mPlayer.y+"] [vy = "+mGR.mPlayer.vy+"]");
		if(M.GameScreen == M.GAMEPLAY)
			M.GameScreen = M.GAMEPAUSE;
		mGR.resumeCounter = 0;
		M.stop(GameRenderer.mContext);
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
	    Editor editor = prefs.edit();
	    editor.putInt("screen", M.GameScreen);
	    editor.putBoolean("setValue", M.setValue);
	    editor.putFloat("MSpeed", M.Speed);
	    
	    int i =0;
	    for(i =0;i<mGR.mBG.length;i++){
	    	editor.putFloat("BGx"+i, mGR.mBG[i].x);
	    	editor.putFloat("BGy"+i, mGR.mBG[i].y);
	    }
	    
	    for(i =0;i<mGR.mFG.length;i++){
	    	editor.putFloat("FGx"+i, mGR.mFG[i].x);
	    	editor.putFloat("FGy"+i, mGR.mFG[i].y);
	    	editor.putInt("FGp"+i, mGR.mFG[i].p);
	    }
	    for(i =0;i<mGR.mGift.length;i++){
	    	editor.putFloat("Giftx"+i, mGR.mGift[i].x);
	    	editor.putFloat("Gifty"+i, mGR.mGift[i].y);
	    	editor.putInt("Giftno"+i, mGR.mGift[i].no);
	    }
	    {
	    	editor.putFloat("Playerx", mGR.mPlayer.x);
	    	editor.putFloat("Playery", mGR.mPlayer.y);
	    	editor.putFloat("Playervy", mGR.mPlayer.vy);
	    	
	    	editor.putInt("Playert", mGR.mPlayer.tap);
	    	editor.putInt("Playera", mGR.mPlayer.ani);
	    	editor.putBoolean("Playero", mGR.mPlayer.OnAir);
	    }
	    for(i =0;i<mGR.mParticle.length;i++){
	    	editor.putFloat("Particlex"+i, mGR.mParticle[i].x);
	    	editor.putFloat("Particley"+i, mGR.mParticle[i].y);
	    	editor.putFloat("Particlevx"+i, mGR.mParticle[i].vx);
	    	editor.putFloat("Particlevy"+i, mGR.mParticle[i].vy);
	    }
	    for(i =0;i<mGR.mSmoke.length;i++){
	    	editor.putFloat("Smokex"+i, mGR.mSmoke[i].x);
	    	editor.putFloat("Smokey"+i, mGR.mSmoke[i].y);
	    	editor.putInt("Smokei"+i, mGR.mSmoke[i].i);
	    }
	    editor.putInt("mFGcount", mGR.mFGcount);
	    editor.putInt("fg", mGR.fg);
	    editor.putInt("BG", mGR.BG);
	    editor.putInt("GCount", mGR.GCount);
	    editor.putInt("mScore", mGR.mScore);
	    editor.putInt("mHScore", mGR.mHScore);
	    editor.putInt("gameCont", mGR.gameCont);
	    editor.putFloat("ay", mGR.ay);
	    editor.putFloat("pani", mGR.pAni);
	    editor.putFloat("pvy", mGR.pvy);
	    
	    editor.commit();
	}
	void resume()
	{
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = prefs.getInt("screen", M.GAMELOGO);
		M.setValue = prefs.getBoolean("setValue", M.setValue);
		M.Speed = prefs.getFloat("MSpeed", M.Speed);
	    mGR.resumeCounter = 0;
	    
	    int i =0;
	    for(i =0;i<mGR.mBG.length;i++){
	    	mGR.mBG[i].x = prefs.getFloat("BGx"+i, mGR.mBG[i].x);
	    	mGR.mBG[i].y = prefs.getFloat("BGy"+i, mGR.mBG[i].y);
	    }
	    
	    for(i =0;i<mGR.mFG.length;i++){
	    	mGR.mFG[i].x = prefs.getFloat("FGx"+i, mGR.mFG[i].x);
	    	mGR.mFG[i].y = prefs.getFloat("FGy"+i, mGR.mFG[i].y);
	    	mGR.mFG[i].p = prefs.getInt("FGp"+i, mGR.mFG[i].p);
	    }
	    for(i =0;i<mGR.mGift.length;i++){
	    	mGR.mGift[i].x = prefs.getFloat("Giftx"+i, mGR.mGift[i].x);
	    	mGR.mGift[i].y = prefs.getFloat("Gifty"+i, mGR.mGift[i].y);
	    	mGR.mGift[i].no = prefs.getInt("Giftno"+i, mGR.mGift[i].no);
	    }
	    {
	    	mGR.mPlayer.x = prefs.getFloat("Playerx", mGR.mPlayer.x);
	    	mGR.mPlayer.y = prefs.getFloat("Playery", mGR.mPlayer.y);
	    	mGR.mPlayer.vy = prefs.getFloat("Playervy", mGR.mPlayer.vy);
	    	
	    	mGR.mPlayer.tap = prefs.getInt("Playert", mGR.mPlayer.tap);
	    	mGR.mPlayer.ani = prefs.getInt("Playera", mGR.mPlayer.ani);
	    	mGR.mPlayer.OnAir = prefs.getBoolean("Playero", mGR.mPlayer.OnAir);
	    }
	    for(i =0;i<mGR.mParticle.length;i++){
	    	mGR.mParticle[i].x = prefs.getFloat("Particlex"+i, mGR.mParticle[i].x);
	    	mGR.mParticle[i].y = prefs.getFloat("Particley"+i, mGR.mParticle[i].y);
	    	mGR.mParticle[i].vx = prefs.getFloat("Particlevx"+i, mGR.mParticle[i].vx);
	    	mGR.mParticle[i].vy = prefs.getFloat("Particlevy"+i, mGR.mParticle[i].vy);
	    }
	    for(i =0;i<mGR.mSmoke.length;i++){
	    	mGR.mSmoke[i].x = prefs.getFloat("Smokex"+i, mGR.mSmoke[i].x);
	    	mGR.mSmoke[i].y = prefs.getFloat("Smokey"+i, mGR.mSmoke[i].y);
	    	mGR.mSmoke[i].i = prefs.getInt("Smokei"+i, mGR.mSmoke[i].i);
	    }
	    mGR.mFGcount = prefs.getInt("mFGcount", mGR.mFGcount);
	    mGR.fg = prefs.getInt("fg", mGR.fg);
	    mGR.BG = prefs.getInt("BG", mGR.BG);
	    mGR.GCount = prefs.getInt("GCount", mGR.GCount);
	    mGR.mScore = prefs.getInt("mScore", mGR.mScore);
	    mGR.mHScore = prefs.getInt("mHScore", mGR.mHScore);
	    mGR.gameCont = prefs.getInt("gameCont", mGR.gameCont);
	    mGR.ay = prefs.getFloat("ay", mGR.ay);
	    mGR.pAni = prefs.getFloat("pani", -2);
	    mGR.pvy = prefs.getFloat("pvy", mGR.pvy);
	    System.out.println("[Resume][x = "+mGR.mPlayer.x+"] [y = "+mGR.mPlayer.y+"] [vy = "+mGR.mPlayer.vy+"]");
	    
	}
	void Exit()
	{
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Do you want to Exit?")
	    .setPositiveButton("Rate Us",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
    	   Intent intent = new Intent(Intent.ACTION_VIEW);
    	   intent.setData(Uri.parse(M.LINK+getClass().getPackage().getName()));
    	   startActivity(intent);
      }}).setNeutralButton("No",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
//    	  UnlockAchievement(R.string.achievement_third);
      }}).setNegativeButton("Yes",new DialogInterface.OnClickListener(){public void onClick(DialogInterface dialog, int which) {
		    		   finish();M.GameScreen=M.GAMELOGO;mGR.root.Counter =0;
//    	  onShowAchievementsRequested();
      }}).show();
  }
	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		
	}
	public void Submitscore(final int ID) {
		if (!isSignedIn()) {
			beginUserInitiatedSignIn();
		} else {Games.Leaderboards.submitScore(getApiClient(), getString(ID), mGR.mHScore);
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
	
}