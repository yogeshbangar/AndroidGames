package com.onedaygames24.shoot2bottle;

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
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
public class Start extends BaseGameActivity
{
	
	int _keyCode = 0;
	GameRenderer mGR = null;
	private static Context CONTEXT;
	AdView adView;
	private InterstitialAd interstitialAd;
	@SuppressWarnings("deprecation")
	void DynamicBannerAd() {
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
		/*layout = new LinearLayout(Start.this);
		layout.setOrientation(LinearLayout.HORIZONTAL);
		layout.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL);
		Start.this.addContentView(layout, new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		adView = new AdView(Start.this, AdView.BANNER_TYPE_IN_APP_AD,AdView.PLACEMENT_TYPE_INTERSTITIAL, 
				false, false,AdView.ANIMATION_TYPE_LEFT_TO_RIGHT);
		layout.addView(adView);

		// set Cannback listener for Dynamic Banner
		adView.setAdListener(new AirBannerListener());
		System.out.println("DynamicBannerAd");*/
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
	    DynamicBannerAd();
	}
	public static Context getContext() {
	        return CONTEXT;
	}
	@Override 
	public void onPause () {
		M.stop(CONTEXT);
		pause();
		super.onPause();
//		ScoreloopManagerSingleton.get().setOnScoreSubmitObserver(null);
	}
	@Override 
	public void onResume() {
		resume();
		super.onResume();
//		ScoreloopManagerSingleton.get().setOnScoreSubmitObserver(this);
		//view.onResume();
	}
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		Log.d("----------------=>  "+keyCode,"   -----------    ");
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			
			switch (M.GameScreen) {
			case M.GAMEPLAY:
				M.GameScreen = M.GAMEMENU;
				mGR.isPause = true;
				mGR.startGTime = System.currentTimeMillis()-mGR.startGTime;
				break;
			case M.GAMELOGO:
			case M.GAMEMENU:
				Exit();
				break;
			case M.GAMEADD:
				if(mGR.TargetBottle>mGR.BreakingBottle)
					M.GameScreen = M.GAMEOVER;
				else
					M.GameScreen = M.GAMEWIN;
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
//		ScoreloopManagerSingleton.get().setOnStartGamePlayRequestObserver(null);
	}
	void pause()
	{
		int i = 0;
		if(M.GameScreen == M.GAMEPLAY)
		{
			mGR.startGTime = System.currentTimeMillis()-mGR.startGTime;
			mGR.isPause = true; 
			M.GameScreen = M.GAMEMENU;
		}
		mGR.resumeCounter = 0;
		M.stop(mGR.mContext);
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
	    Editor editor = prefs.edit();
	    editor.putInt("screen", M.GameScreen);
	    editor.putBoolean("setValue", M.setValue);
	    
	    editor.putInt("resumeCounter", mGR.resumeCounter);
	    editor.putInt("BreakingBottle", mGR.BreakingBottle);
	    editor.putInt("TargetBottle", mGR.TargetBottle);
	    editor.putInt("HighScore", mGR.HighScore);
	    editor.putInt("Level", mGR.Level);
	    editor.putInt("throughtC", mGR.throughtC);
	    editor.putInt("throughtL", mGR.throughtL);
	    editor.putInt("incCounter", mGR.incCounter);
	    editor.putInt("starcounter", mGR.starcounter);
	    
	    editor.putLong("startGTime", mGR.startGTime);
	    editor.putBoolean("isPause", mGR.isPause);
	    for(i=0;i<mGR.mStone.length;i++)
	    {
	    	editor.putFloat("mStonex"+i, mGR.mStone[i].x);
	    	editor.putFloat("mStoney"+i, mGR.mStone[i].y);
	    	editor.putFloat("mStonez"+i, mGR.mStone[i].z);
	    	editor.putFloat("mStonevx"+i, mGR.mStone[i].vx);
	    	editor.putFloat("mStonevy"+i, mGR.mStone[i].vy);
	    	editor.putFloat("mStonevz"+i, mGR.mStone[i].vz);
	    	editor.putBoolean("mStoneisUsed"+i, mGR.mStone[i].isUsed);
	    }
	    for(i=0;i<mGR.mBottle.length;i++)
	    {
	    	editor.putBoolean("mBottleisBlast"+i, mGR.mBottle[i].isBlast);
	    	editor.putInt("mBottletype"+i, mGR.mBottle[i].type);
	    	editor.putInt("mBottleSheld"+i, mGR.mBottle[i].Sheld);
	    	editor.putInt("mBottleWakeUpCounter"+i, mGR.mBottle[i].WakeUpCounter);
	    	editor.putInt("mBottlespark"+i, mGR.mBottle[i].spark);
	    	editor.putFloat("mBottlex"+i, mGR.mBottle[i].x);
	    	editor.putFloat("mBottley"+i, mGR.mBottle[i].y);
	    	editor.putFloat("mBottledy"+i, mGR.mBottle[i].dy);
	    	editor.putFloat("mBottledvy"+i, mGR.mBottle[i].dvy);
	    }
	    
	    
	    for(i=0;i<mGR.mAnimation.length;i++)
	    {
	    	editor.putInt("mAnimationimg"+i, mGR.mAnimation[i].img);
	    	editor.putInt("mAnimationrotetion"+i, mGR.mAnimation[i].rotetion);
	    	editor.putFloat("mAnimationx"+i, mGR.mAnimation[i].x);
	    	editor.putFloat("mAnimationy"+i, mGR.mAnimation[i].y);
	    	editor.putFloat("mAnimationvx"+i, mGR.mAnimation[i].vx);
	    	editor.putFloat("mAnimationvy"+i, mGR.mAnimation[i].vy);
	    }
	    editor.commit();
	}
	void resume()
	{
		int i=0;
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		
		M.GameScreen = prefs.getInt("screen", M.GAMELOGO);
		M.setValue = prefs.getBoolean("setValue", M.setValue);
	    
	    mGR.resumeCounter = prefs.getInt("resumeCounter", mGR.resumeCounter);
	    mGR.BreakingBottle = prefs.getInt("BreakingBottle", mGR.BreakingBottle);
	    mGR.TargetBottle = prefs.getInt("TargetBottle", mGR.TargetBottle);
	    mGR.HighScore = prefs.getInt("HighScore", mGR.HighScore);
	    mGR.Level = prefs.getInt("Level", mGR.Level);
	    mGR.throughtC = prefs.getInt("throughtC", mGR.throughtC);
	    mGR.throughtL = prefs.getInt("throughtL", mGR.throughtL);
	    mGR.incCounter = prefs.getInt("incCounter", mGR.incCounter);
	    mGR.starcounter = prefs.getInt("starcounter", mGR.starcounter);
	    
	    mGR.startGTime = prefs.getLong("startGTime", mGR.startGTime);
	    mGR.isPause = prefs.getBoolean("isPause", mGR.isPause);
	    for(i=0;i<mGR.mStone.length;i++)
	    {
	    	mGR.mStone[i].x = prefs.getFloat("mStonex"+i, mGR.mStone[i].x);
	    	mGR.mStone[i].y = prefs.getFloat("mStoney"+i, mGR.mStone[i].y);
	    	mGR.mStone[i].z = prefs.getFloat("mStonez"+i, mGR.mStone[i].z);
	    	mGR.mStone[i].vx = prefs.getFloat("mStonevx"+i, mGR.mStone[i].vx);
	    	mGR.mStone[i].vy = prefs.getFloat("mStonevy"+i, mGR.mStone[i].vy);
	    	mGR.mStone[i].vz = prefs.getFloat("mStonevz"+i, mGR.mStone[i].vz);
	    	mGR.mStone[i].isUsed = prefs.getBoolean("mStoneisUsed"+i, mGR.mStone[i].isUsed);
	    }
	    for(i=0;i<mGR.mBottle.length;i++)
	    {
	    	mGR.mBottle[i].isBlast = prefs.getBoolean("mBottleisBlast"+i, mGR.mBottle[i].isBlast);
	    	mGR.mBottle[i].type = prefs.getInt("mBottletype"+i, mGR.mBottle[i].type);
	    	mGR.mBottle[i].Sheld = prefs.getInt("mBottleSheld"+i, mGR.mBottle[i].Sheld);
	    	mGR.mBottle[i].WakeUpCounter = prefs.getInt("mBottleWakeUpCounter"+i, mGR.mBottle[i].WakeUpCounter);
	    	mGR.mBottle[i].spark = prefs.getInt("mBottlespark"+i, mGR.mBottle[i].spark);
	    	mGR.mBottle[i].x = prefs.getFloat("mBottlex"+i, mGR.mBottle[i].x);
	    	mGR.mBottle[i].y = prefs.getFloat("mBottley"+i, mGR.mBottle[i].y);
	    	mGR.mBottle[i].dy = prefs.getFloat("mBottledy"+i, mGR.mBottle[i].dy);
	    	mGR.mBottle[i].dvy = prefs.getFloat("mBottledvy"+i, mGR.mBottle[i].dvy);
	    }
	    
	    
	    for(i=0;i<mGR.mAnimation.length;i++)
	    {
	    	mGR.mAnimation[i].img = prefs.getInt("mAnimationimg"+i, mGR.mAnimation[i].img);
	    	mGR.mAnimation[i].rotetion = prefs.getInt("mAnimationrotetion"+i, mGR.mAnimation[i].rotetion);
	    	mGR.mAnimation[i].x = prefs.getFloat("mAnimationx"+i, mGR.mAnimation[i].x);
	    	mGR.mAnimation[i].y = prefs.getFloat("mAnimationy"+i, mGR.mAnimation[i].y);
	    	mGR.mAnimation[i].vx = prefs.getFloat("mAnimationvx"+i, mGR.mAnimation[i].vx);
	    	mGR.mAnimation[i].vy = prefs.getFloat("mAnimationvy"+i, mGR.mAnimation[i].vy);
	    }
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
	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		Submitscore(R.string.leaderboard_high_score);
		
	}
	public void Submitscore(final int ID) {
		if (!isSignedIn()) {
//			beginUserInitiatedSignIn();
		} else { Games.Leaderboards.submitScore(getApiClient(), getString(ID), mGR.HighScore);
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