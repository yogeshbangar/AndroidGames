package com.hututu.games.basketballhitz;

//import com.airpush.android.Airpush;

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

public class Start extends BaseGameActivity
{
	int _keyCode = 0;
	AdView adView = null;
	AdView adHouse = null;//AdHouse
	private InterstitialAd interstitialAd;
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
			adHouse = (AdView) this.findViewById(R.id.advhouse);
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
		interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId(getString(R.string.INTERSTITIAL_ID));
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
				M.GameScreen = M.GAMEPAUSE;
				mGR.gametime = System.currentTimeMillis() - mGR.gametime;
				M.stop(GameRenderer.mContext);
				break;
			case M.GAMEMENU:
				Exit();
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
//		if(keyCode==KeyEvent.KEYCODE_BACK)
//			return false;
		_keyCode = 0;
		return super.onKeyUp( keyCode, event ); 
	}
	public void onDestroy()
	{
		super.onDestroy();
		if(mGR.mMainActivity!=null)
			mGR.mMainActivity.onDestroy();
	}

	void pause() {
		mGR.resumeCounter = 0;
		M.stop(GameRenderer.mContext);
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putInt("screen", M.GameScreen);
		editor.putBoolean("setValue", M.setValue);

		{
			editor.putBoolean("Ballmove", mGR.mBall.move);
			editor.putInt("Ballrev", mGR.mBall.rev);
			editor.putInt("BallBounce", mGR.mBall.Bounce);
			editor.putInt("Ballcollide", mGR.mBall.collide);
			editor.putInt("Ballanim", mGR.mBall.anim);
			editor.putInt("BallmBalls", mGR.mBall.mBalls);
			editor.putInt("Balltype", mGR.mBall.type);
			editor.putInt("BallDrap", mGR.mBall.Drap);
			editor.putInt("Ballcombo", mGR.mBall.combo);
			editor.putInt("Ballxtime", mGR.mBall.xtime);

			editor.putFloat("Ballx", mGR.mBall.x);
			editor.putFloat("Bally", mGR.mBall.y);
			editor.putFloat("Ballz", mGR.mBall.z);
			editor.putFloat("Ballsx", mGR.mBall.sx);
			editor.putFloat("Ballsy", mGR.mBall.sy);
			editor.putFloat("Ballvx", mGR.mBall.vx);
			editor.putFloat("Ballvy", mGR.mBall.vy);
			editor.putFloat("Ballvz", mGR.mBall.vz);
			editor.putFloat("Ballang", mGR.mBall.ang);
			editor.putFloat("Balltx", mGR.mBall.tx);
			editor.putFloat("Ballty", mGR.mBall.ty);
		}
		{
			editor.putInt("TimeAnino", mGR.mTimeAni.no);
			editor.putFloat("TimeAnix", mGR.mTimeAni.x);
			editor.putFloat("TimeAniy", mGR.mTimeAni.y);
		}
		{
			editor.putInt("ComboAnino", mGR.mComboAni.no);
			editor.putFloat("ComboAnix", mGR.mComboAni.x);
			editor.putFloat("ComboAniy", mGR.mComboAni.y);
		}
		for (int i = 0; i < mGR.mAni.length; i++) {
			editor.putInt("Anino" + i, mGR.mAni[i].no);
			editor.putFloat("Anix"+i, mGR.mAni[i].x);
			editor.putFloat("Aniy"+i, mGR.mAni[i].y);
		}

		editor.putBoolean("addFree", mGR.addFree);
		editor.putBoolean("SingUpadate", mGR.SingUpadate);
		for (int i = 0; i < mGR.Achi.length; i++)
			editor.putBoolean("Achi" + i, mGR.Achi[i]);

		editor.putInt("mScore", mGR.mScore);
		editor.putInt("mTScore", mGR.mTScore);
		for (int i = 0; i < mGR.mBest.length; i++){
			editor.putInt("mBest"+i, mGR.mBest[i]);
		}
		editor.putInt("ads", mGR.ads);
		editor.putLong("gametime", mGR.gametime);

		editor.commit();
	}
	void resume()
	{
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = prefs.getInt("screen", M.GAMELOGO);
		M.setValue = prefs.getBoolean("setValue", M.setValue);
		
		{
			mGR.mBall.move = prefs.getBoolean("Ballmove", mGR.mBall.move);

			mGR.mBall.rev = (byte)prefs.getInt("Ballrev", mGR.mBall.rev);
			mGR.mBall.Bounce = prefs.getInt("BallBounce", mGR.mBall.Bounce);
			mGR.mBall.collide = prefs.getInt("Ballcollide", mGR.mBall.collide);
			mGR.mBall.anim = prefs.getInt("Ballanim", mGR.mBall.anim);
			mGR.mBall.mBalls = prefs.getInt("BallmBalls", mGR.mBall.mBalls);
			mGR.mBall.type = prefs.getInt("Balltype", mGR.mBall.type);
			mGR.mBall.Drap = prefs.getInt("BallDrap", mGR.mBall.Drap);
			mGR.mBall.combo = prefs.getInt("Ballcombo", mGR.mBall.combo);
			mGR.mBall.xtime = prefs.getInt("Ballxtime", mGR.mBall.xtime);

			mGR.mBall.x = prefs.getFloat("Ballx", mGR.mBall.x);
			mGR.mBall.y = prefs.getFloat("Bally", mGR.mBall.y);
			mGR.mBall.z = prefs.getFloat("Ballz", mGR.mBall.z);
			mGR.mBall.sx = prefs.getFloat("Ballsx", mGR.mBall.sx);
			mGR.mBall.sy = prefs.getFloat("Ballsy", mGR.mBall.sy);
			mGR.mBall.vx = prefs.getFloat("Ballvx", mGR.mBall.vx);
			mGR.mBall.vy = prefs.getFloat("Ballvy", mGR.mBall.vy);
			mGR.mBall.vz = prefs.getFloat("Ballvz", mGR.mBall.vz);
			mGR.mBall.ang = prefs.getFloat("Ballang", mGR.mBall.ang);
			mGR.mBall.tx = prefs.getFloat("Balltx", mGR.mBall.tx);
			mGR.mBall.ty = prefs.getFloat("Ballty", mGR.mBall.ty);
		}
		{
			mGR.mTimeAni.no = prefs.getInt("TimeAnino", mGR.mTimeAni.no);
			mGR.mTimeAni.x = prefs.getFloat("TimeAnix", mGR.mTimeAni.x);
			mGR.mTimeAni.y = prefs.getFloat("TimeAniy", mGR.mTimeAni.y);
		}
		{
			mGR.mComboAni.no = prefs.getInt("ComboAnino", mGR.mComboAni.no);
			mGR.mComboAni.x = prefs.getFloat("ComboAnix", mGR.mComboAni.x);
			mGR.mComboAni.y = prefs.getFloat("ComboAniy", mGR.mComboAni.y);
		}
		for (int i = 0; i < mGR.mAni.length; i++) {
			mGR.mAni[i].no = prefs.getInt("Anino" + i, mGR.mAni[i].no);
			mGR.mAni[i].x = prefs.getFloat("Anix"+i, mGR.mAni[i].x);
			mGR.mAni[i].y = prefs.getFloat("Aniy"+i, mGR.mAni[i].y);
		}

		mGR.addFree = prefs.getBoolean("addFree", mGR.addFree);
		mGR.SingUpadate = prefs.getBoolean("SingUpadate", mGR.SingUpadate);
		for (int i = 0; i < mGR.Achi.length; i++)
			mGR.Achi[i] = prefs.getBoolean("Achi" + i, mGR.Achi[i]);

		mGR.mScore = prefs.getInt("mScore", mGR.mScore);
		mGR.mTScore = prefs.getInt("mTScore", mGR.mTScore);
		for (int i = 0; i < mGR.mBest.length; i++){
			mGR.mBest[i] = prefs.getInt("mBest"+i, mGR.mBest[i]);
		}
		mGR.ads = prefs.getInt("ads", mGR.ads);
		mGR.gametime = prefs.getLong("gametime", mGR.gametime);
		
		
	    mGR.resumeCounter = 0;
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (!mGR.mMainActivity.mHelper.handleActivityResult(requestCode,resultCode, data)) {
			super.onActivityResult(requestCode, resultCode, data);
		} else {
		}
	}
	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
//		System.out.println(getGamesClient().getCurrentPlayer().getDisplayName());
		if(!mGR.SingUpadate)
		{
			for(int i=0;i<mGR.Achi.length;i++)
			{
				if(mGR.Achi[i])
				{
					UnlockAchievement(M.achiment[i]);
				}
			}
			{
				for(int i=0;i<mGR.mBest.length;i++)
				{
					GameRenderer.mStart.Submitscore(M.scoreID[i], mGR.mBest[i]);
				}
			}
			mGR.SingUpadate = true;
			
		}
	}
	public void Submitscore(final int ID,int total) {
		if (!isSignedIn()) {
//			beginUserInitiatedSignIn();
		} else { 
			Games.Leaderboards.submitScore(getApiClient(), getString(ID), total);
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
	void load(){
		if (!interstitialAd.isLoaded() && !mGR.addFree) {
			AdRequest adRequest = new AdRequest.Builder()
//					.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//					.addTestDevice("67701763FB847AEBD3C6EE486475ED94")
			.build();
			interstitialAd.loadAd(adRequest);
			interstitialAd.setAdListener(new ToastAdListener(this));
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
}