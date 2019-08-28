package com.fun2sh.games.amazingboatracing3d;

import rajawali.RajawaliActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class Start extends RajawaliActivity implements GameHelper.GameHelperListener
 {
  static Context mContext;
  private HTTRenderer mGR;
  AdView adBanner = null;
  boolean isSignSucc,addFree;
  private InterstitialAd interstitial;
  boolean isStart=false;
   @SuppressWarnings("deprecation")
   @Override
   public void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		BeginGooglePlayService();
		WindowManager mWinMgr = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		
		mContext = this;
		super.onCreate(savedInstanceState);
		mGR = new HTTRenderer(this);
		mGR.setSurfaceView(mSurfaceView);
		super.setRenderer(mGR);
		
		interstitial = new InterstitialAd(this);
		interstitial.setAdUnitId(getString(R.string.INTERSTITIAL_ID));
		
		LinearLayout placeHolder = new LinearLayout(this);
		getLayoutInflater().inflate(R.layout.game, placeHolder);
		mLayout.addView(placeHolder);
		HideView();
		M.ScreenWidth = mWinMgr.getDefaultDisplay().getWidth();
		M.ScreenHieght = mWinMgr.getDefaultDisplay().getHeight();
		System.out.println("On Createee  "+M.ScreenWidth+"     "+M.ScreenHieght);
		if(!addFree)
        {
           callAdds();
           LoadHandler();
           isStart=true;
        }
	}
   void callAdds() {
	   
//	   AdSize.BANNER;
	   adBanner = (AdView) this.findViewById(R.id.adBanner);
		AdRequest adRequest = new AdRequest.Builder().build();
		adBanner.loadAd(adRequest);
		adBanner.setAdListener(new AdListener() {
			public void onAdLoaded() {
				adBanner.bringToFront();
			}
		});
	}
   void HideView()
   {
	   mLayout.setSystemUiVisibility(
       View.SYSTEM_UI_FLAG_LAYOUT_STABLE
       | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
       | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
       | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
       | View.SYSTEM_UI_FLAG_FULLSCREEN
       | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
   }
   @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.d("----------------=>  " + keyCode, "   -----------    ");
		if (keyCode == KeyEvent.KEYCODE_BACK){
			switch(M.GameScreen){
			
			  case M.GAMEPLAY:
				    M.GameScreen = M.GAMEPAUSE;
				    mGR.root.DrawGamePlay(false);
			        mGR.root.ResetCamera();
			        mGR.mGameTime-=System.currentTimeMillis();
			        M.StopSound();
				  break;
			  case M.GAMEPAUSE:
			  case M.GAMEOVER:
				  M.GameScreen = M.GAMEMENU;
				  mGR.root.DrawGameOver(false);
				  break;
//			  case M.GAMESHOP:
//				  M.GameScreen = M.GAMEMENU;
//				  mGR.root.DrawShop(false);
//				  break;
			  case M.GAMEMENU:
				  Exit();
				  break;
				  
			}
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK)
			return false;
		return super.onKeyUp(keyCode, event);
	}
	@Override
	protected void onStart() {
		super.onStart();
		mHelper.onStart(this);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		mHelper.onStop();
	}
	
	public void onDestroy() {
//		if (mGR.mInApp != null)
//			mGR.mInApp.onDestroy();
		mGR.getCurrentScene().destroyScene();
		super.onDestroy();
		
	}
	@Override
	public void onPause() {
		super.onPause();
		M.StopSound();
		pause();
		System.gc();
	}

	@Override
	public void onResume() {
		super.onResume();
		resume();
		System.gc();
	}
	public void onRestart() {
		super.onRestart();
		resume();
		
	}
	void pause(){
		
		if(M.GameScreen == M.GAMEPLAY)
		{
		   M.GameScreen = M.GAMEPAUSE;
	       mGR.root.DrawGamePlay(false);
	       mGR.root.ResetCamera();
		   mGR.mGameTime-=System.currentTimeMillis();
		}
		M.StopSound();
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putInt("GameScreen"  , M.GameScreen);
		editor.putBoolean("setValue", M.setValue);
		editor.putBoolean("SetBG"   , M.SetBG);
		editor.putBoolean("addFree" , addFree);
		
		editor.putInt("mOppGenCnt"  , mGR.mOppGenCnt);
		editor.putInt("mGenTime"    , mGR.mGenTime);
		editor.putInt("mGenSpeed"   , mGR.mGenSpeed);
		editor.putInt("mGenDelay"   , mGR.mGenDelay);
		editor.putInt("mCrashCnt"   , mGR.mCrashCnt);
		editor.putInt("mCoinCollect", mGR.mCoinCollect);
		editor.putInt("mScore"      , mGR.mScore);
		editor.putInt("mBestScore"  , mGR.mBestScore);
		editor.putInt("mSideNo"     , mGR.mSideNo);
		editor.putInt("mStartCnt"   , mGR.mStartCnt);
		editor.putInt("mStoneCnt"   , mGR.mStoneCnt);
		editor.putInt("mCrossCar"   , mGR.mCrossBoat);
		editor.putFloat("mPlayTime" , mGR.mPlayTime);
		editor.putLong("mGameTime" , mGR.mGameTime);
		
		
		for(int i=0;i<mGR.isAchieve.length;i++)
			editor.putBoolean("isAchieve"+i,mGR.isAchieve[i]);
		
		editor.commit();
		Runtime.getRuntime().gc();
	}

	void resume(){
		
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.setValue   		    =  prefs.getBoolean("setValue", M.setValue);
		addFree   		        =  prefs.getBoolean("addFree", addFree);
		M.GameScreen  		    =  prefs.getInt("GameScreen", M.GameScreen);
		M.SetBG                 =  prefs.getBoolean("SetBG", M.SetBG);
//		isSignSucc              =  prefs.getBoolean("isSignSucc", isSignSucc);
		
		mGR.mOppGenCnt = prefs.getInt("mOppGenCnt"  , mGR.mOppGenCnt);
		mGR.mGenTime   = prefs.getInt("mGenTime"    , mGR.mGenTime);
		mGR.mGenSpeed  = prefs.getInt("mGenSpeed"   , mGR.mGenSpeed);
		mGR.mGenDelay  = prefs.getInt("mGenDelay"   , mGR.mGenDelay);
		mGR.mCrashCnt  = prefs.getInt("mCrashCnt"   , mGR.mCrashCnt);
		mGR.mCoinCollect = prefs.getInt("mCoinCollect", mGR.mCoinCollect);
		mGR.mScore       = prefs.getInt("mScore"      , mGR.mScore);
		mGR.mBestScore   = prefs.getInt("mBestScore"  , mGR.mBestScore);
		mGR.mSideNo      = prefs.getInt("mSideNo"     , mGR.mSideNo);
		mGR.mStartCnt    = prefs.getInt("mStartCnt"   , mGR.mStartCnt);
		mGR.mStoneCnt    = prefs.getInt("mStoneCnt"   , mGR.mStoneCnt);
		mGR.mCrossBoat   = prefs.getInt("mCrossCar"   , mGR.mCrossBoat);
		mGR.mPlayTime    = prefs.getFloat("mPlayTime" ,mGR.mPlayTime);
		mGR.mGameTime    = prefs.getLong("mGameTime" , mGR.mGameTime);
		
		
		for(int i=0;i<mGR.isAchieve.length;i++)
			prefs.getBoolean("isAchieve"+i,mGR.isAchieve[i]);
		
		HideView();
		Runtime.getRuntime().gc();
	}
	void Exit()
	{
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Do you want to Exit?")
	    .setPositiveButton("More Games",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
    	   mGR.root.MoreGame();
      }}).setNeutralButton("No",new DialogInterface.OnClickListener(){   public void onClick(DialogInterface dialog, int which)
    	  {
    	    HideView();
	  }}).setNegativeButton("Yes",new DialogInterface.OnClickListener(){ public void onClick(DialogInterface dialog, int which)
    	  {
 		    finish();M.GameScreen=M.GAMELOGO;mGR.root.Counter =0;
 		    M.StopSound();
    	  }}).show();
    }
	void NotEnough()
	{
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Not Enough Coins?")
        .setNeutralButton("Ok",new DialogInterface.OnClickListener(){   public void onClick(DialogInterface dialog, int which)
    	  {
    	 }}).show();
    }
	public Handler LoadHandler = new Handler() {public void handleMessage(Message msg){loadInterstitial();}};
	void LoadHandler()
	{
	   try{LoadHandler.sendEmptyMessage(0);} catch (Exception e){}
	}
//	.addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("67701763FB847AEBD3C6EE486475ED94")
	public void loadInterstitial(){
		System.gc();
		if(!interstitial.isLoaded() && !addFree)
		{
			System.out.println("Loading Start .................");
			AdRequest adRequest = new AdRequest.Builder().build();
			interstitial.loadAd(adRequest);
			interstitial.setAdListener(new AdGameListener(this));
		}
	}
	Handler Showhandler = new Handler() {public void handleMessage(Message msg) {showInterstitial();}};
	public void ShowHandle() {
		try{Showhandler.sendEmptyMessage(0);}catch(Exception e){}
	}
	public void showInterstitial(){

		try{
			  if(interstitial != null && !addFree)
				if(interstitial.isLoaded()){
				   interstitial.show();
				   System.gc();
				   System.out.println("show Start .................");
 			   }
 		   }catch(Exception e){
			System.out.println(e.toString());
		 }
	}
// The game helper object. This class is mainly a wrapper around this
// object.
protected GameHelper mHelper;
// We expose these constants here because we don't want users of this class
// to have to know about GameHelper at all.
public static final int CLIENT_GAMES = GameHelper.CLIENT_GAMES;
public static final int CLIENT_APPSTATE = GameHelper.CLIENT_APPSTATE;
public static final int CLIENT_PLUS = GameHelper.CLIENT_PLUS;
public static final int CLIENT_ALL = GameHelper.CLIENT_ALL;

// Requested clients. By default, that's just the games client.
protected int mRequestedClients = CLIENT_GAMES;

private final static String TAG = "BaseGameActivity";
protected boolean mDebugLog = false;

/** Constructs a BaseGameActivity with default client (GamesClient). */

/**
 * Sets the requested clients. The preferred way to set the requested
 * clients is via the constructor, but this method is available if for some
 * reason your code cannot do this in the constructor. This must be called
 * before onCreate or getGameHelper() in order to have any effect. If called
 * after onCreate()/getGameHelper(), this method is a no-op.
 * 
 * @param requestedClients
 *            A combination of the flags CLIENT_GAMES, CLIENT_PLUS and
 *            CLIENT_APPSTATE, or CLIENT_ALL to request all available
 *            clients.
 */
		protected void setRequestedClients(int requestedClients) {
			mRequestedClients = requestedClients;
		}
		
		public GameHelper getGameHelper() {
			if (mHelper == null) {
				mHelper = new GameHelper(this, mRequestedClients);
				mHelper.enableDebugLog(mDebugLog);
			}
			return mHelper;
		}
		
		protected void BeginGooglePlayService() {
			if (mHelper == null) {
				getGameHelper();
			}
			mHelper.setup(this);
		}
		
		
		
		@Override
		protected void onActivityResult(int request, int response, Intent data) {
			super.onActivityResult(request, response, data);
			mHelper.onActivityResult(request, response, data);
//			if(!mGR.mInApp.mHelper.handleActivityResult(request, response, data)) 
//			{
//			  super.onActivityResult(request, response, data);
//			}
//			else 
//			{
//			}
		}
		
		protected GoogleApiClient getApiClient() {
			return mHelper.getApiClient();
		}
		
		protected boolean isSignedIn() {
			return mHelper.isSignedIn();
		}
		
		protected void beginUserInitiatedSignIn() {
			mHelper.beginUserInitiatedSignIn();
		}
		
		protected void signOut() {
			mHelper.signOut();
		}
		
		protected void showAlert(String message) {
			mHelper.makeSimpleDialog(message).show();
		}
		
		protected void showAlert(String title, String message) {
			mHelper.makeSimpleDialog(title, message).show();
		}
		
		protected void enableDebugLog(boolean enabled) {
			mDebugLog = true;
			if (mHelper != null) {
				mHelper.enableDebugLog(enabled);
			}
		}
		
		@Deprecated
		protected void enableDebugLog(boolean enabled, String tag) {
			Log.w(TAG, "BaseGameActivity.enabledDebugLog(bool,String) is "
					+ "deprecated. Use enableDebugLog(boolean)");
			enableDebugLog(enabled);
		}
		
		protected String getInvitationId() {
			return mHelper.getInvitationId();
		}
		
		protected void reconnectClient() {
			mHelper.reconnectClient();
		}
		
		protected boolean hasSignInError() {
			return mHelper.hasSignInError();
		}
		
		protected GameHelper.SignInFailureReason getSignInError() {
			return mHelper.getSignInError();
		}
		
		@Override
		public void onSignInFailed() {
			// TODO Auto-generated method stub
		
		}
		@Override
		public void onSignInSucceeded(){
			
			if(!isSignSucc)
			{
				for(int i=0;i<mGR.isAchieve.length;i++)
				{
					if(mGR.isAchieve[i])
					{
						HTTRenderer.mStart.UnlockAchievement(mGR.root.Achievements[i]);
					}
				}
				Submitscore(R.string.leaderboard_best_score,mGR.mBestScore);
			   isSignSucc=true;
			}
		}
		public void Submitscore(final int ID, long total) {
			if (!isSignedIn()) {
				// beginUserInitiatedSignIn();
			} else {
				Games.Leaderboards.submitScore(getApiClient(), getString(ID), total);
			}
		}
		int RC_UNUSED = 5001;
		// @Override
		public void onShowLeaderboardsRequested() {
			if(isSignedIn()){
				startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(getApiClient()),RC_UNUSED);
			}else{
				beginUserInitiatedSignIn();
			}
		}
		
		public void UnlockAchievement(final int ID) {
			try {
				if (!isSignedIn()) {
					// beginUserInitiatedSignIn();
				} else {
					if (isSignedIn()) {
						Games.Achievements.unlock(getApiClient(), getString(ID));
					}
		
				}
			} catch (Exception e) {
			}
		}
		
		public void onShowAchievementsRequested() {
	   	  try{
				if(isSignedIn()){
					startActivityForResult(
							Games.Achievements.getAchievementsIntent(getApiClient()),RC_UNUSED);
				} else {
					beginUserInitiatedSignIn();
				}
		} catch (Exception e){}
	}
		
 }