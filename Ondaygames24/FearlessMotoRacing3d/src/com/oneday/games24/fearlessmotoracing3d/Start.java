package com.oneday.games24.fearlessmotoracing3d;

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

public class Start extends RajawaliActivity implements
		GameHelper.GameHelperListener {
	static Context mContext;
	private HTTRenderer mGR;

	AdView adView = null;
	AdView adfull = null;
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
		
		
		adfull = (AdView) this.findViewById(R.id.adful);
		AdRequest adReqful = new AdRequest.Builder().build();
		adfull.loadAd(adReqful);
		adfull.setAdListener(new AdListener() {
			public void onAdLoaded() {
				adfull.bringToFront();
			}
		});
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
		if (!getSharedPreferences("X", MODE_PRIVATE).getBoolean("addFree",
				HTTRenderer.addFree))
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
		if(mGR.mPlayer == null)
			return;
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putInt("screen", M.GameScreen);
		editor.putBoolean("setValue", M.setValue);
		editor.putBoolean("SingUpadate", mGR.SingUpadate);
		for (int i = 0; i < mGR.mAchiUnlock.length; i++)
			editor.putBoolean("mAchiUnlock" + i, mGR.mAchiUnlock[i]);
		editor.putBoolean("addFree", HTTRenderer.addFree);

		{

			editor.putBoolean("plyisRoad", mGR.mPlayer.isRoad);

			editor.putInt("plylevel", mGR.mPlayer.level);
			editor.putInt("plyrowOpp", mGR.mPlayer.rowOpp);
			editor.putInt("plybike", mGR.mPlayer.bike);
			editor.putInt("plyCrash", mGR.mPlayer.Crash);
			editor.putInt("plyCrosscar", mGR.mPlayer.Crosscar);
			editor.putInt("plyRoad", mGR.mPlayer.Road);
			editor.putInt("plyCollectCoin", mGR.mPlayer.CollectCoin);
			editor.putInt("plytapBot", mGR.mPlayer.tapBot);

			editor.putFloat("plyvx", mGR.mPlayer.vx);
			editor.putFloat("plySpd", mGR.mPlayer.Spd);
			editor.putFloat("plynewOpp", mGR.mPlayer.newOpp);
			editor.putFloat("plyDistance", mGR.mPlayer.Distance);
		}
		for (int i = 0; i < mGR.mOpp.length; i++) {
			editor.putInt("oppindi", mGR.mOpp[i].indi);

			editor.putFloat("oppspd", mGR.mOpp[i].spd);
			editor.putFloat("oppvx", mGR.mOpp[i].vx);
			editor.putFloat("oppup", mGR.mOpp[i].up);

			editor.putFloat("oppobjx", (float) mGR.mOpp[i].obj.getX());
			editor.putFloat("oppobjy", (float) mGR.mOpp[i].obj.getY());

		}
		for (int i = 0; i < mGR.bike.length; i++) {
			editor.putBoolean("pBike"+i, mGR.bike[i]);
			System.out.println(" Pause  k  "+i +"  "+mGR.bike[i]);
		}
		for (int i = 0; i < mGR.TreVal.length; i++) {
			editor.putInt("TreVal", mGR.TreVal[i]);
		}
		editor.putInt("Challenge", mGR.Challenge);
		editor.putInt("mCoin", HTTRenderer.mCoin);
		editor.putInt("mScore", mGR.mScore);
		editor.putInt("mHScore", mGR.mHScore);
		editor.putLong("mTime", mGR.mTime);

		editor.commit();
	}

	void resume() {
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = (byte) prefs.getInt("screen", M.GAMELOGO);
		M.setValue = prefs.getBoolean("setValue", M.setValue);
		mGR.SingUpadate = prefs.getBoolean("SingUpadate", mGR.SingUpadate);
		for (int i = 0; i < mGR.mAchiUnlock.length; i++)
			mGR.mAchiUnlock[i] = prefs.getBoolean("mAchiUnlock" + i,
					mGR.mAchiUnlock[i]);
		HTTRenderer.addFree = prefs.getBoolean("addFree", HTTRenderer.addFree);
		{

			mGR.mPlayer.isRoad = prefs.getBoolean("plyisRoad",
					mGR.mPlayer.isRoad);

			mGR.mPlayer.level = prefs.getInt("plylevel", mGR.mPlayer.level);
			mGR.mPlayer.rowOpp = prefs.getInt("plyrowOpp", mGR.mPlayer.rowOpp);
			mGR.mPlayer.bike = prefs.getInt("plybike", mGR.mPlayer.bike);
			mGR.mPlayer.Crash = prefs.getInt("plyCrash", mGR.mPlayer.Crash);
			mGR.mPlayer.Crosscar = prefs.getInt("plyCrosscar",
					mGR.mPlayer.Crosscar);
			mGR.mPlayer.Road = prefs.getInt("plyRoad", mGR.mPlayer.Road);
			mGR.mPlayer.CollectCoin = prefs.getInt("plyCollectCoin",
					mGR.mPlayer.CollectCoin);
			mGR.mPlayer.tapBot = prefs.getInt("plytapBot", mGR.mPlayer.tapBot);

			mGR.mPlayer.vx = prefs.getFloat("plyvx", mGR.mPlayer.vx);
			mGR.mPlayer.Spd = prefs.getFloat("plySpd", mGR.mPlayer.Spd);
			mGR.mPlayer.newOpp = prefs
					.getFloat("plynewOpp", mGR.mPlayer.newOpp);
			mGR.mPlayer.Distance = prefs.getFloat("plyDistance",
					mGR.mPlayer.Distance);
		}
		for (int i = 0; i < mGR.mOpp.length; i++) {
			mGR.mOpp[i].indi = prefs.getInt("oppindi", mGR.mOpp[i].indi);

			mGR.mOpp[i].spd = prefs.getFloat("oppspd", mGR.mOpp[i].spd);
			mGR.mOpp[i].vx = prefs.getFloat("oppvx", mGR.mOpp[i].vx);
			mGR.mOpp[i].up = prefs.getFloat("oppup", mGR.mOpp[i].up);

			mGR.mOpp[i].obj.setX(prefs.getFloat("oppobjx",
					(float) mGR.mOpp[i].obj.getX()));
			mGR.mOpp[i].obj.setY(prefs.getFloat("oppobjy",
					(float) mGR.mOpp[i].obj.getY()));

		}
		for (int i = 0; i < mGR.bike.length; i++) {
			mGR.bike[i] = prefs.getBoolean("pBike"+i, mGR.bike[i]);
			System.out.println(i +"  "+mGR.bike[i]);
		}
		for (int i = 0; i < mGR.TreVal.length; i++) {
			mGR.TreVal[i] = (byte) prefs.getInt("TreVal", mGR.TreVal[i]);
		}
		mGR.Challenge = prefs.getInt("Challenge", mGR.Challenge);
		HTTRenderer.mCoin = prefs.getInt("mCoin", HTTRenderer.mCoin);
		mGR.mScore = prefs.getInt("mScore", mGR.mScore);
		mGR.mHScore = prefs.getInt("mHScore", mGR.mHScore);
		mGR.mTime = prefs.getLong("mTime", mGR.mTime);

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

	void Buy() {
		new AlertDialog.Builder(this)
				.setIcon(R.drawable.icon)
				.setTitle("You don't have sufficient coin.")
				.setPositiveButton("Ok",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog,int which) {}})
				/*.setNegativeButton("Buy",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog,int which) {
								try {mGR.mInApp.onBuyGold10000(null);} catch (Exception e) {}}})*/
				.show();
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

	public void onDestroy() {
		super.onDestroy();
//		if (mGR.mInApp != null)
//			mGR.mInApp.onDestroy();
	}

	@Override
	protected void onActivityResult(int request, int response, Intent data) {
		super.onActivityResult(request, response, data);
		mHelper.onActivityResult(request, response, data);

		/*if (!mGR.mInApp.mHelper.handleActivityResult(request, response, data)) {
			super.onActivityResult(request, response, data);
		}*/
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
		if (!interstitial.isLoaded() && !HTTRenderer.addFree) {
			AdRequest adRequest = new AdRequest.Builder()
			// .addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("67701763FB847AEBD3C6EE486475ED94")
					.build();
			interstitial.loadAd(adRequest);
			interstitial.setAdListener(new AdGameListener(this));
		}
	}

	public void ShowInterstitial() {
		if (!HTTRenderer.addFree)
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
			if (interstitial != null && !HTTRenderer.addFree)
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
			Submitscore(R.string.leaderboard_best_score, mGR.mHScore);
			mGR.SingUpadate = true;
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
		if(mGR.mScore > 5000 && !mGR.mAchiUnlock[0]){
			mGR.mAchiUnlock[0] = true;
			UnlockAchievement(M.ACHIV[0]);
		}
		if(mGR.mScore > 7000 && !mGR.mAchiUnlock[1]){
			mGR.mAchiUnlock[1] = true;
			UnlockAchievement(M.ACHIV[1]);
		}
		if(mGR.mTime > 120000 && !mGR.mAchiUnlock[2]){
			mGR.mAchiUnlock[2] = true;
			UnlockAchievement(M.ACHIV[2]);
		}
		if(mGR.mTime > 300000 && !mGR.mAchiUnlock[3]){
			mGR.mAchiUnlock[3] = true;
			UnlockAchievement(M.ACHIV[3]);
		}
		if(mGR.mPlayer.CollectCoin > 1000 && !mGR.mAchiUnlock[4]){
			mGR.mAchiUnlock[4] = true;
			UnlockAchievement(M.ACHIV[4]);
		}
		Submitscore(R.string.leaderboard_best_score, mGR.mHScore);
	}
}
