package com.robotics.crosstrafficcity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

import rajawali.RajawaliActivity;
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
import android.widget.LinearLayout;

public class Start extends RajawaliActivity 
implements GameHelper.GameHelperListener
{
	static Context mContext;
	HTTRenderer mGR;
	boolean isVideoAvail= false;
	Runnable mLoadRun,mShowRun;
	final static String APP_ID  = "app848ee2cd83ca40d297";
	final static String ZONE_ID = "vz11bc094df87947f790";
	
	AdView adView = null;
//	AdView adfull = null;
	private InterstitialAd interstitial;
	
	void callAdds() {
		
		adView = (AdView) this.findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);
		adView.setAdListener(new AdListener() {
			public void onAdLoaded() {
				adView.bringToFront();
			}
		});
		
//		adfull = (AdView) this.findViewById(R.id.adful);
//		AdRequest adReqful = new AdRequest.Builder().build();
//		adfull.loadAd(adReqful);
//		adfull.setAdListener(new AdListener() {
//			public void onAdLoaded() {
//				adfull.bringToFront();
//			}
//		});
	}
									 

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		onPlay();
		WindowManager mWinMgr = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		M.ScreenWidth = mWinMgr.getDefaultDisplay().getWidth();
		M.ScreenHieght = mWinMgr.getDefaultDisplay().getHeight();
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
		if (!getSharedPreferences("X", MODE_PRIVATE).getBoolean("addFree", mGR.addFree))
			callAdds();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.d("----------------=>  " + keyCode, "   -----------    ");
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			switch (M.GameScreen) {
			case M.GAMEMENU:
				Exit();
				break;
			case M.GAMEPLAY:
				M.GameScreen = M.GAMEPAUSE;
				M.stop();
				break;
			default:
				M.GameScreen = M.GAMEMENU;
				break;
			}
//			if (M.GameScreen != M.GAMEMENU) {
//				M.GameScreen = M.GAMEMENU;
//			} else {
//				Exit();
//			}
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
	public void onPause() {
		M.stop();
		 pause();
		super.onPause();
	}

	@Override
	public void onResume() {
		// resume();
		super.onResume();
		// view.onResume();
	}

	void pause() {
		if (M.GameScreen == M.GAMEPLAY)
			M.GameScreen = M.GAMEPAUSE;
		M.stop();
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putInt("screen", M.GameScreen);
		editor.putBoolean("setValue", M.setValue);
		
		
		
		editor.putBoolean("addFree", mGR.addFree);
		editor.putBoolean("SingUpadate", HTTRenderer.SingUpadate);
		for(int i =0;i<mGR.isbuyPlayer.length;i++)
			editor.putBoolean("isbuyPlayer"+i, mGR.isbuyPlayer[i]);
		editor.putBoolean("isPeggie", mGR.isPeggie);
		editor.putBoolean("isdpublecent", mGR.isdpublecent);
		
		editor.putInt("giftMove", mGR.giftMove);
		editor.putInt("type", mGR.type);
		editor.putInt("noTime", mGR.noTime);
		editor.putInt("mScore", mGR.mScore);
		editor.putInt("mHScore", mGR.mHScore);
		editor.putInt("giftNo", mGR.giftNo);
		editor.putInt("cent", mGR.cent);
		editor.putInt("FreeCoin", mGR.FreeCoin);
		
		
		editor.putFloat("scal", mGR.scal);
		editor.putFloat("move", mGR.move);
		for(int i =0;i<mGR.mBGVactor.length;i++){
			editor.putInt("mBGVactort"+i, mGR.mBGVactor[i].type);
			editor.putInt("mBGVactorn"+i, mGR.mBGVactor[i].no);
			editor.putFloat("mBGVactory"+i, mGR.mBGVactor[i].y);
		}
		for(int i =0;i<mGR.mCar.length;i++){
			editor.putInt("mCarn"+i, mGR.mCar[i].no);
			editor.putFloat("mCarvx"+i, mGR.mCar[i].vx);
		}
		for(int i =0;i<mGR.mWoods.length;i++){
			editor.putInt("mWoodsn"+i, mGR.mWoods[i].no);
			editor.putFloat("mWoodsvx"+i, mGR.mWoods[i].vx);
		}
		
		for(int i =0;i<mGR.mTrees.length;i++){
			editor.putInt("mTreesn"+i, mGR.mTrees[i].no);
		}

		{
			editor.putBoolean("Playerwater", mGR.mPlayer.water);
			editor.putBoolean("PlayerisPress", mGR.mPlayer.isPress);
			editor.putBoolean("PlayerisEgle", mGR.mPlayer.isEgle);
			editor.putBoolean("PlayerwaterSound", mGR.mPlayer.waterSound);
			editor.putBoolean("Playerrandom", mGR.mPlayer.random);
			
			editor.putInt("Playerno", mGR.mPlayer.no);
			editor.putInt("PlayerCrash", mGR.mPlayer.Crash);
			editor.putInt("Playerdied", mGR.mPlayer.died);
			editor.putInt("Playerimg", mGR.mPlayer.img);
			editor.putInt("PlayerbuyChar", mGR.mPlayer.buyChar);
			
			editor.putFloat("Playerx", mGR.mPlayer.x);
			editor.putFloat("Playery", mGR.mPlayer.y);
			editor.putFloat("Playerz", mGR.mPlayer.z);
			editor.putFloat("Playervz", mGR.mPlayer.vz);
			editor.putFloat("Playervx", mGR.mPlayer.vx);
			editor.putFloat("Playerprez", mGR.mPlayer.pres);

			editor.putLong("Playertime", mGR.mPlayer.time);
		}
		for(int i =0;i<mGR.mTrain.length;i++){
			editor.putInt("mTrainn"+i, mGR.mTrain[i].no);
			
			editor.putFloat("mTrainx"+i, mGR.mTrain[i].x);
			editor.putFloat("mTrainy"+i, mGR.mTrain[i].y);
			editor.putFloat("mTrainvx"+i, mGR.mTrain[i].vx);
		}
		
		
		for(int i =0;i<mGR.mWater.length;i++){
			editor.putFloat("mWatervz"+i, mGR.mWater[i].vz);
		}
		editor.putFloat("mEglevx", mGR.mEgle.vx);
		editor.putFloat("mEglevy", mGR.mEgle.vy);
		editor.putFloat("mEglevz", mGR.mEgle.vz);
		
		editor.putLong("time", mGR.time);
		
		editor.commit();
	}

	void resume() {
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = (byte) prefs.getInt("screen", M.GAMELOGO);
		M.setValue = prefs.getBoolean("setValue", M.setValue);
		
		
		
		mGR.addFree = prefs.getBoolean("addFree", mGR.addFree);
		HTTRenderer.SingUpadate= prefs.getBoolean("SingUpadate", HTTRenderer.SingUpadate);
		for(int i =0;i<mGR.isbuyPlayer.length;i++)
			mGR.isbuyPlayer[i] = prefs.getBoolean("isbuyPlayer"+i, mGR.isbuyPlayer[i]);
		mGR.isPeggie = prefs.getBoolean("isPeggie", mGR.isPeggie);
		mGR.isdpublecent = prefs.getBoolean("isdpublecent", mGR.isdpublecent);
		
		mGR.giftMove = prefs.getInt("giftMove", mGR.giftMove);
		mGR.type = prefs.getInt("type", mGR.type);
		mGR.noTime = prefs.getInt("noTime", mGR.noTime);
		mGR.mScore = prefs.getInt("mScore", mGR.mScore);
		mGR.mHScore = prefs.getInt("mHScore", mGR.mHScore);
		mGR.giftNo = prefs.getInt("giftNo", mGR.giftNo);
		mGR.cent = prefs.getInt("cent", mGR.cent);
		mGR.FreeCoin = prefs.getInt("FreeCoin", mGR.FreeCoin);
		
		
		mGR.scal = prefs.getFloat("scal", mGR.scal);
		mGR.move = prefs.getFloat("move", mGR.move);
		for(int i =0;i<mGR.mBGVactor.length;i++){
			mGR.mBGVactor[i].type = prefs.getInt("mBGVactort"+i, mGR.mBGVactor[i].type);
			mGR.mBGVactor[i].no = prefs.getInt("mBGVactorn"+i, mGR.mBGVactor[i].no);
			mGR.mBGVactor[i].y = prefs.getFloat("mBGVactory"+i, mGR.mBGVactor[i].y);
		}
		for(int i =0;i<mGR.mCar.length;i++){
			mGR.mCar[i].no = prefs.getInt("mCarn"+i, mGR.mCar[i].no);
			mGR.mCar[i].vx = prefs.getFloat("mCarvx"+i, mGR.mCar[i].vx);
		}
		for(int i =0;i<mGR.mWoods.length;i++){
			mGR.mWoods[i].no = prefs.getInt("mWoodsn"+i, mGR.mWoods[i].no);
			mGR.mWoods[i].vx = prefs.getFloat("mWoodsvx"+i, mGR.mWoods[i].vx);
		}
		
		for(int i =0;i<mGR.mTrees.length;i++){
			mGR.mTrees[i].no = prefs.getInt("mTreesn"+i, mGR.mTrees[i].no);
		}

		{
			mGR.mPlayer.water = prefs.getBoolean("Playerwater", mGR.mPlayer.water);
			mGR.mPlayer.isPress = prefs.getBoolean("PlayerisPress", mGR.mPlayer.isPress);
			mGR.mPlayer.isEgle = prefs.getBoolean("PlayerisEgle", mGR.mPlayer.isEgle);
			mGR.mPlayer.waterSound = prefs.getBoolean("PlayerwaterSound", mGR.mPlayer.waterSound);
			mGR.mPlayer.random = prefs.getBoolean("Playerrandom", mGR.mPlayer.random);
			
			mGR.mPlayer.no = prefs.getInt("Playerno", mGR.mPlayer.no);
			mGR.mPlayer.Crash = prefs.getInt("PlayerCrash", mGR.mPlayer.Crash);
			mGR.mPlayer.died = prefs.getInt("Playerdied", mGR.mPlayer.died);
			mGR.mPlayer.img = prefs.getInt("Playerimg", mGR.mPlayer.img);
			mGR.mPlayer.buyChar = prefs.getInt("PlayerbuyChar", mGR.mPlayer.buyChar);
			
			mGR.mPlayer.x = prefs.getFloat("Playerx", mGR.mPlayer.x);
			mGR.mPlayer.y = prefs.getFloat("Playery", mGR.mPlayer.y);
			mGR.mPlayer.z = prefs.getFloat("Playerz", mGR.mPlayer.z);
			mGR.mPlayer.vz = prefs.getFloat("Playervz", mGR.mPlayer.vz);
			mGR.mPlayer.vx = prefs.getFloat("Playervx", mGR.mPlayer.vx);
			mGR.mPlayer.pres = prefs.getFloat("Playerprez", mGR.mPlayer.pres);

			mGR.mPlayer.time = prefs.getLong("Playertime", mGR.mPlayer.time);
		}
		for(int i =0;i<mGR.mTrain.length;i++){
			mGR.mTrain[i].no = prefs.getInt("mTrainn"+i, mGR.mTrain[i].no);
			
			mGR.mTrain[i].x= prefs.getFloat("mTrainx"+i, mGR.mTrain[i].x);
			mGR.mTrain[i].y = prefs.getFloat("mTrainy"+i, mGR.mTrain[i].y);
			mGR.mTrain[i].vx = prefs.getFloat("mTrainvx"+i, mGR.mTrain[i].vx);
		}
		
		
		for(int i =0;i<mGR.mWater.length;i++){
			mGR.mWater[i].vz = prefs.getFloat("mWatervz"+i, mGR.mWater[i].vz);
		}
		mGR.mEgle.vx = prefs.getFloat("mEglevx", mGR.mEgle.vx);
		mGR.mEgle.vy = prefs.getFloat("mEglevy", mGR.mEgle.vy);
		mGR.mEgle.vz = prefs.getFloat("mEglevz", mGR.mEgle.vz);
		mGR.time = prefs.getLong("time", mGR.time);
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

		protected void onPlay() {
			if (mHelper == null) {
				getGameHelper();
			}
			mHelper.setup(this);
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
		@Override
		public void onDestroy() {
			super.onDestroy();
			if (mGR.mInApp != null)
				mGR.mInApp.onDestroy();
		}


	 
	 @Override
		protected void onActivityResult(int request, int response, Intent data) {
			super.onActivityResult(request, response, data);
			mHelper.onActivityResult(request, response, data);

			if (!mGR.mInApp.mHelper.handleActivityResult(request, response, data)) {
				super.onActivityResult(request, response, data);
			} else {
			}
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

		public void load() {
			try {
				handlerload.sendEmptyMessage(0);
			} catch (Exception e) {
			}
		}

		Handler handlerload = new Handler() {
			public void handleMessage(Message msg) {
				loadInter();
			}
		};

		private void loadInter() {
			if (!interstitial.isLoaded() && !mGR.addFree) {
				AdRequest adRequest = new AdRequest.Builder()
				// .addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("67701763FB847AEBD3C6EE486475ED94")
						.build();
				interstitial.loadAd(adRequest);
				interstitial.setAdListener(new AdGameListener(this));
			}
		}

		public void ShowInterstitial() {
			if (!mGR.addFree)
				try {
					handler.sendEmptyMessage(0);
				} catch (Exception e) {
				}
		}

		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				show();
			}
		};

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
//			if (!HTTRenderer.SingUpadate) 
			{
				
				for(int i =0;i<M.ACHIV.length;i++){
					if(mGR.isbuyPlayer[i])
						UnlockAchievement(M.ACHIV[i]);
				}
				Submitscore(R.string.leaderboard_best_score, mGR.mHScore);
				HTTRenderer.SingUpadate = true;
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
			if (isSignedIn()) {
				startActivityForResult(
						Games.Leaderboards.getAllLeaderboardsIntent(getApiClient()),
						RC_UNUSED);
			} else {
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
			try {
				if (isSignedIn()) {
					startActivityForResult(
							Games.Achievements
									.getAchievementsIntent(getApiClient()),
							RC_UNUSED);
				} else {
					beginUserInitiatedSignIn();
				}
			} catch (Exception e) {
			}
		}
		
		void Achivment(){
			for(int i =0;i<M.ACHIV.length;i++){
				if(mGR.isbuyPlayer[i])
					UnlockAchievement(M.ACHIV[i]);
			}
			Submitscore(R.string.leaderboard_best_score, mGR.mHScore);
		}
	 
	 
	void Exit() {
		   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Do you want to Exit?")
		    .setPositiveButton("More",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
	    	   Intent intent = new Intent(Intent.ACTION_VIEW);
	    	   intent.setData(Uri.parse(M.MARKET));
	    	   startActivity(intent);
	      }}).setNeutralButton("No",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
	      }}).setNegativeButton("Yes",new DialogInterface.OnClickListener(){public void onClick(DialogInterface dialog, int which) {
			    		   finish();M.GameScreen=M.GAMELOGO;
	      }}).show();
	  }


}
