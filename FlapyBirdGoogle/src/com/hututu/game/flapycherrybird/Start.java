package com.hututu.game.flapycherrybird;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.games.Games;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class Start extends BaseGameActivity
{
	int _keyCode = 0;
	AdView adView   = null;
	AdView adBtm    = null;
	AdView adload   = null;
	boolean addFree = false;
	boolean SingUpadate = false;
	BroadcastReceiver mReceiver;

	GameRenderer mGR = null;
	
	private InterstitialAd interstitialAd;
	
	private static Context CONTEXT;

	void callAdds() {
		if (adView == null) {
			adView = (AdView) this.findViewById(R.id.addgame);
			AdRequest adRequest = new AdRequest.Builder().build();
			adView.loadAd(adRequest);
			adView.setAdListener(new AdListener() {
				public void onAdLoaded() {
					adView.bringToFront();
				}
			});
		}
		if (adBtm == null) {
			adBtm = (AdView) this.findViewById(R.id.addbtm);
			AdRequest adRequest = new AdRequest.Builder().build();
			adBtm.loadAd(adRequest);
			adBtm.setAdListener(new AdListener() {
				public void onAdLoaded() {
					adBtm.bringToFront();
				}
			});
		}
		if (adload == null) {
			adload = (AdView) this.findViewById(R.id.advload);
			AdRequest adRequest = new AdRequest.Builder().build();
			adload.loadAd(adRequest);
			adload.setAdListener(new AdListener() {
				public void onAdLoaded() {
					adload.bringToFront();
				}
			});
		}
	}
	public void onCreate(Bundle savedInstanceState) 
	{
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);//OnlyOneChange
		CONTEXT	=	this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		
		interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId(getString(R.string.Intertitial));
	    
		WindowManager mWinMgr = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
		M.ScreenWidth =mWinMgr.getDefaultDisplay().getWidth();
		M.ScreenHieght=mWinMgr.getDefaultDisplay().getHeight();
		mGR=new GameRenderer(this);
	    VortexView glSurface=(VortexView) findViewById(R.id.vortexview); // use the xml to set the view
	    glSurface.setRenderer(mGR);
	    glSurface.showRenderer(mGR);
	    
	    if(!addFree)
	      callAdds();
	  // Recievier 
	    IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        mReceiver = new ScreenReceiver();
        registerReceiver(mReceiver, filter);
	  // Recievier
        
       
	}
	public static Context getContext() {
	        return CONTEXT;
	}
	@Override 
	public void onPause () {
		M.StopSound();
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
			case M.GAMELOAD:
				  mGR.root.Counter=0;
				  M.GameScreen = M.GAMESPLASH;
				break;
			case M.GAMEMENU:
				Exit();
				break;
			case M.GAMEABTUS:
				 mGR.root.SetPopUp();
				 M.GameScreen = M.GAMEOPTION;
				break;
			case M.GAMEPLAY:
				mGR.mAni=-30;
				M.GameScreen = M.GAMEPAUSE;
				M.StopSound();
				break;
			case M.GAMEADD:
				mGR.mAni=-30;
				M.GameScreen=M.GAMEOVER;
				break;
			default:
				mGR.mAni=-30;
				M.GameScreen=M.GAMEMENU;
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

	public void onDestroy(){
		if(mReceiver != null)
        {
            unregisterReceiver(mReceiver);
            mReceiver = null;
        }
		
		if(mGR.mMainActivity!=null)
		   mGR.mMainActivity.onDestroy();
		super.onDestroy();
	}

	void pause()
	{
		int i=0;
		if(M.GameScreen == M.GAMEPLAY)
		{
			M.GameScreen = M.GAMEPAUSE;
			mGR.mAni=-30;
		}
		mGR.resumeCounter = 0;
		M.StopSound();
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
	    Editor editor = prefs.edit();
	    
	    editor.putBoolean("addFree", addFree);
	    editor.putBoolean("SingUpadate", SingUpadate);
	    editor.putInt("screen", M.GameScreen);
	    editor.putBoolean("setValue", M.setValue);
	    editor.putFloat("speed",M.speed);
	    editor.putInt("mGR.mCurrentScore", mGR.mCurrentScore);
	    editor.putInt("mGR.mBestScore", mGR.mBestScore);
	    editor.putFloat("mAni",mGR.mAni);
	    editor.putFloat("mPopy",mGR.mPopy);
	    editor.putFloat("mPopvy",mGR.mPopvy);
	    editor.putFloat("mPlayerx",mGR.mPlayer.x);
	    editor.putFloat("mPlayery",mGR.mPlayer.y);
	    editor.putFloat("mPlayervy",mGR.mPlayer.vy);
	    editor.putFloat("mPlayergravity",mGR.mPlayer.gravity);
	    editor.putFloat("mPlyerang",player.mPlyerang);
	    editor.putFloat("mPlyerVy",player.VY);
	    for(i=0;i<mGR.mObst.length;i++)
    	{
     	  editor.putFloat("mObstX"+i,mGR.mObst[i].x);
     	  editor.putFloat("mObstY"+i,mGR.mObst[i].y);
     	  editor.putInt("mObstNo"+i,mGR.mObst[i].No);
    	}
	    for(i=0;i<mGR.mParticle.length;i++)
    	{
     	  editor.putFloat("mParticleX"+i,mGR.mParticle[i].x);
     	  editor.putFloat("mParticleY"+i,mGR.mParticle[i].y);
     	  editor.putFloat("mParticleVY"+i,mGR.mParticle[i].vy);
     	  editor.putFloat("mParticleVX"+i,mGR.mParticle[i].vx);
     	  editor.putFloat("mParticleZ"+i,mGR.mParticle[i].z);
     	  editor.putInt("mParticleNo"+i,mGR.mParticle[i].No);
    	}
	    for(i=0;i<mGR.mBg.length;i++)
	    	editor.putFloat("mBgX"+i,mGR.mBg[i].x);
	    editor.putInt("mBgNo",mGR.mBgNo);
	    
	    editor.commit();
	}
	void resume()
	{
		int i=0;
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		addFree 			= prefs.getBoolean("addFree", addFree);
		SingUpadate = prefs.getBoolean("SingUpadate", SingUpadate);
		M.GameScreen 	  = prefs.getInt("screen", M.GAMELOGO);
		M.setValue 	 	  = prefs.getBoolean("setValue", M.setValue);
		
		mGR.mCurrentScore = prefs.getInt("mGR.mCurrentScore", mGR.mCurrentScore);
		mGR.mBestScore    = prefs.getInt("mGR.mBestScore", mGR.mBestScore);
		mGR.mAni 	      = prefs.getFloat("mAni",mGR.mAni);
		mGR.mPopy 		  = prefs.getFloat("mPopy",mGR.mPopy);
		mGR.mPopvy 		  = prefs.getFloat("mPopvy",mGR.mPopvy);
		mGR.mPlayer.x     = prefs.getFloat("mPlayerx",mGR.mPlayer.x);
		mGR.mPlayer.y     = prefs.getFloat("mPlayery",mGR.mPlayer.y);
		mGR.mPlayer.vy    = prefs.getFloat("mPlayervy",mGR.mPlayer.vy);
		mGR.mPlayer.gravity  = prefs.getFloat("mPlayergravity",mGR.mPlayer.gravity);
		player.mPlyerang     = prefs.getFloat("mPlyerang",player.mPlyerang);
		player.VY 			 = prefs.getFloat("mPlyerVy",player.VY);
	    for(i=0;i<mGR.mObst.length;i++)
    	{
	    	mGR.mObst[i].x  = prefs.getFloat("mObstX"+i,mGR.mObst[i].x);
	    	mGR.mObst[i].y  = prefs.getFloat("mObstY"+i,mGR.mObst[i].y);
	    	mGR.mObst[i].No = prefs.getInt("mObstNo"+i,mGR.mObst[i].No);
    	}
	    for(i=0;i<mGR.mParticle.length;i++)
    	{
	    	mGR.mParticle[i].x  = prefs.getFloat("mParticleX"+i,mGR.mParticle[i].x);
	    	mGR.mParticle[i].y  = prefs.getFloat("mParticleY"+i,mGR.mParticle[i].y);
	    	mGR.mParticle[i].vy = prefs.getFloat("mParticleVY"+i,mGR.mParticle[i].vy);
	    	mGR.mParticle[i].vx = prefs.getFloat("mParticleVX"+i,mGR.mParticle[i].vx);
	    	mGR.mParticle[i].z  = prefs.getFloat("mParticleZ"+i,mGR.mParticle[i].z);
	    	mGR.mParticle[i].No = prefs.getInt("mParticleNo"+i,mGR.mParticle[i].No);
    	}
	    for(i=0;i<mGR.mBg.length;i++)
	    	mGR.mBg[i].x =  prefs.getFloat("mBgX"+i,mGR.mBg[i].x);
	    mGR.mBgNo = prefs.getInt("mBgNo",mGR.mBgNo);
    	
	}
	void Exit()
	{
		M.StopSound();
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Do you want to Exit?")
	    .setPositiveButton("Rate Us",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
    	   Intent intent = new Intent(Intent.ACTION_VIEW);
    	   intent.setData(Uri.parse(M.LINK+getClass().getPackage().getName()));
    	   startActivity(intent);
      }}).setNeutralButton("No",new DialogInterface.OnClickListener()
      {
    	  public void onClick(DialogInterface dialog, int which)
    	  {
      }}).setNegativeButton("Yes",new DialogInterface.OnClickListener(){public void onClick(DialogInterface dialog, int which)
      {
 		   finish();M.GameScreen=M.GAMELOGO;
 		   mGR.root.Counter =0;
 		   M.StopSound();
      }}).show();
  }
	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		if(!SingUpadate)
		{
		
	    }
		  SingUpadate = true;
   	  }
	
	public void Submitscore(final int ID, int score) {
		try {
			if (!isSignedIn()) {// beginUserInitiatedSignIn();
				return;
			} else {
				Games.Leaderboards.submitScore(getApiClient(), getString(ID), score);
				}
		} catch (Exception e) {}
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

	void load() {
		if (!interstitialAd.isLoaded() && !addFree) {
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
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~"+ "onActivityResult(" + requestCode + "," + resultCode + "," + data);
        if (!mGR.mMainActivity.mHelper.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
        else {
        	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~"+ "onActivityResult handled by IABUtil.");
        }
        
    }
	
}