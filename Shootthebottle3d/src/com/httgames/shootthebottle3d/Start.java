package com.httgames.shootthebottle3d;

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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class Start extends RajawaliActivity implements GameHelper.GameHelperListener {

	static Context mContext;
	private HTTRenderer mGR;
	AdView adView = null;
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
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);  
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
		if (!getSharedPreferences("X", MODE_PRIVATE).getBoolean("addFree",mGR.addFree))
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
				if(mGR.mIsArcad)
					mGR.mTimeScore = System.currentTimeMillis()-mGR.mTimeScore;
				else
					mGR.mTimeScore= mGR.mTimeScore - System.currentTimeMillis();
				M.GameScreen = M.GAMEPAUSE;
				mGR.setVisible(false);
				M.stop();
				break;
			default:
				M.GameScreen = M.GAMEMENU;
				mGR.setVisible(false);
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
	public void onPause() {
		M.stop();
		pause();
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	void pause() {
		if(mGR.mParticle == null)
			return;
//		mGR.setVisible(false);
		M.stop();
		if (M.GameScreen == M.GAMEPLAY){
			M.GameScreen = M.GAMEPAUSE;
			if(mGR.mIsArcad)
				mGR.mTimeScore = System.currentTimeMillis()-mGR.mTimeScore;
			else
				mGR.mTimeScore= mGR.mTimeScore - System.currentTimeMillis();
		}
		
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		Editor editor = prefs.edit();
		
		editor.putInt("a", M.GameScreen);
		
		editor.putBoolean("b", M.setValue);
		
		for(int i = 0;i<mGR.mBottle.length;i++){
			editor.putBoolean("c"+i, mGR.mBottle[i].ison);
			editor.putInt("d"+i, mGR.mBottle[i].img);
			editor.putFloat("e"+i, mGR.mBottle[i].vz);
		}
		
		editor.putBoolean("f", mGR.mIsArcad);
		editor.putBoolean("g", mGR.mIsGameStart);
		editor.putBoolean("h", mGR.addFree);
		editor.putBoolean("i", mGR.SingUpadate);
		for(int i = 0;i<mGR.mAchiUnlock.length;i++)
			editor.putBoolean("j"+i, mGR.mAchiUnlock[i]);
		
		editor.putLong("k", mGR.mTimeScore);
		editor.putInt("l", mGR.NoBottle);
		for(int i = 0;i<mGR.mTScore.length;i++)
			editor.putLong("m"+i, mGR.mTScore[i]);
		for(int i = 0;i<mGR.mBScore.length;i++)
			editor.putInt("n"+i, mGR.mBScore[i]);
		editor.putInt("o", mGR.mMode);
		
		
		
		editor.commit();
//		mGR.setVisible(false);
	}

	void resume() {
//		if(mGR.mParticle == null)
//			return;
		mGR.setVisible(false);
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = (byte) prefs.getInt("a", M.GAMELOGO);
		M.setValue = prefs.getBoolean("b", M.setValue);
		
		for(int i = 0;i<mGR.mBottle.length;i++){
			mGR.mBottle[i].ison = prefs.getBoolean("c"+i, mGR.mBottle[i].ison);
			mGR.mBottle[i].img = prefs.getInt("d"+i, mGR.mBottle[i].img);
			mGR.mBottle[i].vz = prefs.getFloat("e"+i, mGR.mBottle[i].vz);
		}
		
		mGR.mIsArcad = prefs.getBoolean("f", mGR.mIsArcad);
		mGR.mIsGameStart = prefs.getBoolean("g", mGR.mIsGameStart);
		mGR.addFree = prefs.getBoolean("h", mGR.addFree);
		mGR.SingUpadate = prefs.getBoolean("i", mGR.SingUpadate);
		for(int i = 0;i<mGR.mAchiUnlock.length;i++)
			mGR.mAchiUnlock[i] = prefs.getBoolean("j"+i, mGR.mAchiUnlock[i]);
		
		mGR.mTimeScore = prefs.getLong("k", mGR.mTimeScore);
		mGR.NoBottle = prefs.getInt("l", mGR.NoBottle);
		for(int i = 0;i<mGR.mTScore.length;i++)
			mGR.mTScore[i] = prefs.getLong("m"+i, mGR.mTScore[i]);
		for(int i = 0;i<mGR.mBScore.length;i++)
			mGR.mBScore[i] = prefs.getInt("n"+i, mGR.mBScore[i]);
		mGR.mMode = prefs.getInt("o", mGR.mMode);
		
		mGR.setVisible(false);
	}

	void Exit() {
		new AlertDialog.Builder(this)
				.setIcon(R.drawable.icon)
				.setTitle("Do you want to exit?")
				.setPositiveButton("No", new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {}})
				.setNeutralButton("More", new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {mGR.root.MoreGame();}})
				.setNegativeButton("Yes",new DialogInterface.OnClickListener() {  public void onClick(DialogInterface dialog, int which) 
					{mGR.root.counter = 0;finish();M.GameScreen = M.GAMELOGO;}
				}).show();
	}

	void Buy() {
		new AlertDialog.Builder(this)
				.setIcon(R.drawable.icon)
				.setTitle("You don't have sufficient coin.")
				.setPositiveButton("Later",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog,int which) {}})
				.setNegativeButton("Buy",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog,int which) {
								/*try {mGR.mInApp.onBuyGold10000(null);} catch (Exception e) {}*/
				}}).show();
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
		if (!mGR.SingUpadate) {
			for (int i = 0; i < mGR.mAchiUnlock.length; i++) {
				if (mGR.mAchiUnlock[i]) {
					UnlockAchievement(M.ACHIV[i]);
				}
			}
			for(int i =0;i<mGR.mTScore.length;i++){
				if(mGR.mTScore[i]>0)
					Submitscore(M.ARCAD[i], mGR.mTScore[i]);
				if(mGR.mBScore[i]>0)
					Submitscore(M.TIME[i], mGR.mBScore[i]);
			}
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
		if(mGR.mIsArcad && mGR.mTimeScore < 30000 && mGR.mMode == 0 && !mGR.mAchiUnlock[0]){
			mGR.mAchiUnlock[0] = true;
			UnlockAchievement(M.ACHIV[0]);
		}
		if(mGR.mIsArcad && mGR.mTimeScore < 50000 && mGR.mMode == 1 && !mGR.mAchiUnlock[1]){
			mGR.mAchiUnlock[1] = true;
			UnlockAchievement(M.ACHIV[1]);
		}
		if(mGR.mIsArcad && mGR.mTimeScore < 90000 && mGR.mMode == 2 && !mGR.mAchiUnlock[2]){
			mGR.mAchiUnlock[2] = true;
			UnlockAchievement(M.ACHIV[2]);
		}
		if(!mGR.mIsArcad && mGR.NoBottle > 20 && mGR.mMode == 0 && !mGR.mAchiUnlock[3]){
			mGR.mAchiUnlock[3] = true;
			UnlockAchievement(M.ACHIV[3]);
		}
		if(!mGR.mIsArcad && mGR.NoBottle > 30 && mGR.mMode == 1 && !mGR.mAchiUnlock[4]){
			mGR.mAchiUnlock[4] = true;
			UnlockAchievement(M.ACHIV[4]);
		}
		for(int i =0;i<mGR.mTScore.length;i++){
			if(mGR.mTScore[i]>0)
				Submitscore(M.ARCAD[i], mGR.mTScore[i]);
			if(mGR.mBScore[i]>0)
				Submitscore(M.TIME[i], mGR.mBScore[i]);
		}
	}
}
