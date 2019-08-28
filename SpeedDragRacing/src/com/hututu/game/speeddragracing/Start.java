package com.hututu.game.speeddragracing;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.InterstitialAd;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.google.android.gms.games.achievement.OnAchievementUpdatedListener;
import com.google.android.gms.games.leaderboard.OnScoreSubmittedListener;
import com.google.android.gms.games.leaderboard.SubmitScoreResult;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
//import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.net.Uri;
//import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Start extends BaseGameActivity  implements AdListener
{
	int _keyCode = 0;
//	AdView adView = null;
	AdView adHouse = null;//AdHouse
	GameRenderer mGR = null;
	private static Context CONTEXT;
//	BroadcastReceiver mReceiver;
	private InterstitialAd interstitialAd;
	void callAdds()
	{
//		adView = new AdView(this, AdSize.BANNER, M.MY_AD_UNIT_ID);
//		LinearLayout layout = (LinearLayout)findViewById(R.id.addgame);
//		adView.setGravity(Gravity.TOP);
//        layout.addView(adView);
//        adView.loadAd(new AdRequest());
        
        
        /*AdHouse*/
        adHouse = new AdView(this, AdSize.IAB_MRECT, M.HOUSE_ADV_ID);
		LinearLayout layout2 = (LinearLayout)findViewById(R.id.advhouse);
		adHouse.setGravity(Gravity.TOP);
        layout2.addView(adHouse);
        adHouse.loadAd(new AdRequest());
        /*AdHouse*/
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
		
		interstitialAd = new InterstitialAd(this, M.MY_AD_UNIT_ID);
	    interstitialAd.setAdListener(this);
		
		
		WindowManager mWinMgr = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
		M.ScreenWidth =mWinMgr.getDefaultDisplay().getWidth();
		M.ScreenHieght=mWinMgr.getDefaultDisplay().getHeight();
		mGR=new GameRenderer(this);
	    VortexView glSurface=(VortexView) findViewById(R.id.vortexview); // use the xml to set the view
	    glSurface.setRenderer(mGR);
	    glSurface.showRenderer(mGR);
//	    mGR.addFree = true;
	    
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
		super.onResume();
		resume();
		if(!mGR.addFree)
			callAdds();
	}
	@Override
	public void onStart()
	{
		super.onStart();
	}
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		Log.d("----------------=>  "+keyCode,"   -----------    ");
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			if(M.GameScreen == M.GAMEMENU)
				get();
			else
			{
				M.GameScreen = M.GAMEMENU;
				M.play(GameRenderer.mContext, R.drawable.background);
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
	public void onDestroy() {
		super.onDestroy();
		if(mGR.mMainActivity!=null)
			mGR.mMainActivity.onDestroy();
	}
	void pause()
	{
		mGR.resumeCounter = 0;
		M.stop(GameRenderer.mContext);
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
	    Editor editor = prefs.edit();
	    if(M.GameScreen == M.GAMEPLAY)
	    	M.GameScreen = M.GAMEMENU;
	    editor.putInt("screen", M.GameScreen);
	    editor.putBoolean("setValue", M.setValue);
	    editor.putBoolean("addFree", mGR.addFree);
	    
	    int i=0;
	    for(i=0;i<mGR.mCar.length;i++)
	    {
	    	editor.putBoolean("crComplete"+i, mGR.mCar[i].Complete);
	    	editor.putBoolean("crstart"+i, mGR.mCar[i].start);
	    	
	    	editor.putInt("crmSpeed"+i, mGR.mCar[i].mSpeed);
	    	editor.putInt("crmGear"+i, mGR.mCar[i].mGear);
	    	editor.putInt("crmNo"+i, mGR.mCar[i].mNo);
	    	editor.putInt("crpShift"+i, mGR.mCar[i].pShift);
	    	editor.putInt("crm60"+i, mGR.mCar[i].m60);
	    	editor.putInt("crm100"+i, mGR.mCar[i].m100);
	    	editor.putInt("crRcount"+i, mGR.mCar[i].Rcount);
	    	editor.putInt("crracebonus"+i, mGR.mCar[i].racebonus);
	    	editor.putInt("crlauncebonus"+i, mGR.mCar[i].launcebonus);
	    	editor.putInt("crgoodbonus"+i, mGR.mCar[i].goodbonus);
	    	editor.putInt("crperfect"+i, mGR.mCar[i].perfect);
	    	editor.putInt("crraceprice"+i, mGR.mCar[i].raceprice);
	    	editor.putInt("crtotal"+i, mGR.mCar[i].total);
	    	editor.putInt("crNO2"+i, mGR.mCar[i].NO2);
	    	
	    	editor.putLong("crmTime"+i, mGR.mCar[i].mTime);

	    	editor.putFloat("crx"+i, mGR.mCar[i].x);
	    	editor.putFloat("cry"+i, mGR.mCar[i].y);
	    	editor.putFloat("crdx"+i, mGR.mCar[i].dx);
	    	editor.putFloat("crmCarSpeed"+i, mGR.mCar[i].mCarSpeed);
	    	editor.putFloat("crmRPM"+i, mGR.mCar[i].mRPM);
	    	editor.putFloat("crmCPower"+i, mGR.mCar[i].mCPower);
	    	editor.putFloat("crtotalDis"+i, mGR.mCar[i].totalDis);
	    }
	    for(i=0;i<mGR.mLevel.length;i++)
	    {
	    	editor.putInt("lemUPower"+i, mGR.mLevel[i].mUPower);
	    	editor.putInt("lemSLevel"+i, mGR.mLevel[i].mSLevel);
	    	editor.putInt("lemULevel"+i, mGR.mLevel[i].mULevel);
	    	
	    	editor.putFloat("lex"+i, mGR.mLevel[i].x);
	    	editor.putFloat("lez"+i, mGR.mLevel[i].z);
	    	editor.putFloat("levz"+i, mGR.mLevel[i].vz);
	    	for(int j=0;j<mGR.mLevel[i].strength.length;j++)
	    	{
	    		editor.putFloat("le"+i+"st"+j, mGR.mLevel[i].strength[j]);
	    	}
	    }
	    {
	    	editor.putFloat("mAny", mGR.mAnimation.y);
	    	editor.putFloat("mAnvy", mGR.mAnimation.vy);
	    	editor.putInt("mAncounter", mGR.mAnimation.counter);
	    	editor.putInt("mAnNo", mGR.mAnimation.No);
	    }
	    editor.putInt("mMove", mGR.mMove);
	    editor.putInt("breakPoint", mGR.breakPoint);
	    editor.putInt("unlockLevel", mGR.unlockLevel);
	    editor.putInt("mCoint", mGR.mCoint);
	    editor.putInt("mSignal", mGR.mSignal);
	    editor.putInt("EndCounter", mGR.EndCounter);
		

	    editor.putFloat("mBG", mGR.mBG);
	    editor.putFloat("mBG2", mGR.mBG2);
	    editor.putFloat("mBGB", mGR.mBGB);
	    editor.putFloat("mBGB2", mGR.mBGB2);
	    editor.putFloat("mBGBD", mGR.mBGBD);
	    editor.putFloat("mBGBD2", mGR.mBGBD2);
	    editor.putFloat("mSGame", mGR.mSGame);
	    editor.putFloat("mLvlLength", mGR.mLvlLength);
		
	    editor.putString("opptime", mGR.opptime);
	    editor.putString("playertime", mGR.playertime);
	    
	    
	    editor.commit();
	}
	void resume()
	{
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = prefs.getInt("screen", M.GAMELOGO);
		M.setValue = prefs.getBoolean("setValue", true);
		mGR.addFree = prefs.getBoolean("addFree", mGR.addFree);
		
		int i=0;
	    for(i=0;i<mGR.mCar.length;i++)
	    {
	    	mGR.mCar[i].Complete = prefs.getBoolean("crComplete"+i, mGR.mCar[i].Complete);
	    	mGR.mCar[i].start = prefs.getBoolean("crstart"+i, mGR.mCar[i].start);
	    	
	    	mGR.mCar[i].mSpeed = prefs.getInt("crmSpeed"+i, mGR.mCar[i].mSpeed);
	    	mGR.mCar[i].mGear = prefs.getInt("crmGear"+i, mGR.mCar[i].mGear);
	    	mGR.mCar[i].mNo = prefs.getInt("crmNo"+i, mGR.mCar[i].mNo);
	    	mGR.mCar[i].pShift = prefs.getInt("crpShift"+i, mGR.mCar[i].pShift);
	    	mGR.mCar[i].m60 = prefs.getInt("crm60"+i, mGR.mCar[i].m60);
	    	mGR.mCar[i].m100 = prefs.getInt("crm100"+i, mGR.mCar[i].m100);
	    	mGR.mCar[i].Rcount = prefs.getInt("crRcount"+i, mGR.mCar[i].Rcount);
	    	mGR.mCar[i].racebonus = prefs.getInt("crracebonus"+i, mGR.mCar[i].racebonus);
	    	mGR.mCar[i].launcebonus = prefs.getInt("crlauncebonus"+i, mGR.mCar[i].launcebonus);
	    	mGR.mCar[i].goodbonus = prefs.getInt("crgoodbonus"+i, mGR.mCar[i].goodbonus);
	    	mGR.mCar[i].perfect = prefs.getInt("crperfect"+i, mGR.mCar[i].perfect);
	    	mGR.mCar[i].raceprice = prefs.getInt("crraceprice"+i, mGR.mCar[i].raceprice);
	    	mGR.mCar[i].total = prefs.getInt("crtotal"+i, mGR.mCar[i].total);
	    	mGR.mCar[i].NO2 = prefs.getInt("crNO2"+i, mGR.mCar[i].NO2);
	    	
	    	mGR.mCar[i].mTime = prefs.getLong("crmTime"+i, mGR.mCar[i].mTime);

	    	mGR.mCar[i].x = prefs.getFloat("crx"+i, mGR.mCar[i].x);
	    	mGR.mCar[i].y = prefs.getFloat("cry"+i, mGR.mCar[i].y);
	    	mGR.mCar[i].dx = prefs.getFloat("crdx"+i, mGR.mCar[i].dx);
	    	mGR.mCar[i].mCarSpeed = prefs.getFloat("crmCarSpeed"+i, mGR.mCar[i].mCarSpeed);
	    	mGR.mCar[i].mRPM = prefs.getFloat("crmRPM"+i, mGR.mCar[i].mRPM);
	    	mGR.mCar[i].mCPower = prefs.getFloat("crmCPower"+i, mGR.mCar[i].mCPower);
	    	mGR.mCar[i].totalDis = prefs.getFloat("crtotalDis"+i, mGR.mCar[i].totalDis);
	    }
	    for(i=0;i<mGR.mLevel.length;i++)
	    {
	    	mGR.mLevel[i].mUPower = prefs.getInt("lemUPower"+i, mGR.mLevel[i].mUPower);
	    	mGR.mLevel[i].mSLevel = prefs.getInt("lemSLevel"+i, mGR.mLevel[i].mSLevel);
	    	mGR.mLevel[i].mULevel = prefs.getInt("lemULevel"+i, mGR.mLevel[i].mULevel);
	    	
	    	mGR.mLevel[i].x = prefs.getFloat("lex"+i, mGR.mLevel[i].x);
	    	mGR.mLevel[i].z = prefs.getFloat("lez"+i, mGR.mLevel[i].z);
	    	mGR.mLevel[i].vz = prefs.getFloat("levz"+i, mGR.mLevel[i].vz);
	    	for(int j=0;j<mGR.mLevel[i].strength.length;j++)
	    	{
	    		mGR.mLevel[i].strength[j] = prefs.getFloat("le"+i+"st"+j, mGR.mLevel[i].strength[j]);
	    	}
	    }
	    {
	    	mGR.mAnimation.y = prefs.getFloat("mAny", mGR.mAnimation.y);
	    	mGR.mAnimation.vy = prefs.getFloat("mAnvy", mGR.mAnimation.vy);
	    	mGR.mAnimation.counter = prefs.getInt("mAncounter", mGR.mAnimation.counter);
	    	mGR.mAnimation.No = prefs.getInt("mAnNo", mGR.mAnimation.No);
	    }
	    mGR.mMove = prefs.getInt("mMove", mGR.mMove);
	    mGR.breakPoint = prefs.getInt("breakPoint", mGR.breakPoint);
	    mGR.unlockLevel = prefs.getInt("unlockLevel", mGR.unlockLevel);
	    mGR.mCoint = prefs.getInt("mCoint", mGR.mCoint);
	    mGR.mSignal = prefs.getInt("mSignal", mGR.mSignal);
	    mGR.EndCounter = prefs.getInt("EndCounter", mGR.EndCounter);
	    
	    mGR.mBG = prefs.getFloat("mBG", mGR.mBG);
	    mGR.mBG2 = prefs.getFloat("mBG2", mGR.mBG2);
	    mGR.mBGB = prefs.getFloat("mBGB", mGR.mBGB);
	    mGR.mBGB2 = prefs.getFloat("mBGB2", mGR.mBGB2);
	    mGR.mBGBD = prefs.getFloat("mBGBD", mGR.mBGBD);
	    mGR.mBGBD2 = prefs.getFloat("mBGBD2", mGR.mBGBD2);
	    mGR.mSGame = prefs.getFloat("mSGame", mGR.mSGame);
	    mGR.mLvlLength = prefs.getFloat("mLvlLength", mGR.mLvlLength);
		
	    mGR.opptime = prefs.getString("opptime", mGR.opptime);
	    mGR.playertime = prefs.getString("playertime", mGR.playertime);
	    
	    mGR.resumeCounter = 0;
	  
	    if(M.setValue && M.GameScreen!=M.GAMELOGO)
  			M.play(GameRenderer.mContext, R.drawable.background);
//  		else
//  			M.loopstop();
	}
	void get()
	{
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Do you want to exit?")
	    .setPositiveButton("No",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
//    	   Intent intent = new Intent(Intent.ACTION_VIEW);
//    	   intent.setData(Uri.parse(M.LINK+getClass().getPackage().getName()));
//    	   startActivity(intent);
		       }}).setNegativeButton("Yes",new DialogInterface.OnClickListener() {
		    	   public void onClick(DialogInterface dialog, int which) {
		    		   mGR.root.Counter = 0;
		    		   finish();
		    		   M.GameScreen=M.GAMELOGO;
		       }}).show();
	  }
	void Massage(String msg)
	{
		new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle(msg)
	    .setPositiveButton("Ok",new DialogInterface.OnClickListener() 
	    {
	    	public void onClick(DialogInterface dialog, int which) 
	    	{
	    	   
	    	}
    	}).show();
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
		}, getString(ID), mGR.mHScore);
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

	
	public void UnlockAchievement(final int ID) {
		try {
			if (!isSignedIn()) {
//			beginUserInitiatedSignIn();
		} else {
			if ((!isSignedIn()))
				return;
			getGamesClient().unlockAchievementImmediate(new OnAchievementUpdatedListener() {
						@Override
						public void onAchievementUpdated(int statusCode,String arg1) {
						}
					}, getString(ID));

		}
	} catch (Exception e) {}
	}
	public void onShowAchievementsRequested() {
		try {
			if (isSignedIn()) {
				startActivityForResult(getGamesClient().getAchievementsIntent(),RC_UNUSED);
			} else {
//				M.GameScreen = M.GAMEJOIN;
//				M.sound12(GameRenderer.mContext, R.drawable.popup1);
//				mGR.root.sx = .1f;
//				beginUserInitiatedSignIn();
			}
		} catch (Exception e) {}
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
	
	
	
	/** Called when an ad is clicked and about to return to the application. */
	String LOG_TAG = "Hututu Games";
	@Override
	public void onDismissScreen(Ad ad) {
		Log.d(LOG_TAG, "onDismissScreen");
	}
	/** Called when an ad was not received. */
	@Override
	public void onFailedToReceiveAd(Ad ad, AdRequest.ErrorCode error) {
		String message = "onFailedToReceiveAd (" + error + ")";
		Log.d(LOG_TAG, message);
		if (ad == interstitialAd) {}
	}
	@Override
	public void onLeaveApplication(Ad ad) {
		Log.d(LOG_TAG, "onLeaveApplication");
	}
	@Override
	public void onPresentScreen(Ad ad) {
		Log.d(LOG_TAG, "onPresentScreen");
	}
	/** Called when an ad is received. */
	@Override
	public void onReceiveAd(Ad ad) {
		Log.d(LOG_TAG, "onReceiveAd");
		if (ad == interstitialAd) {
			if (interstitialAd.isReady()) {
				interstitialAd.show();
			} else {
				Log.d(LOG_TAG, "Interstitial ad was not ready to be shown.");
			}
		}
	}
	void load() {
		if(!mGR.addFree){
			AdRequest adRequest = new AdRequest();
			interstitialAd.loadAd(adRequest);
		}
	}
	
}