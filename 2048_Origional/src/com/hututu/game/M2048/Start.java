package com.hututu.game.M2048;
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
	GameRenderer mGR = null;
	private static Context CONTEXT;
	private InterstitialAd interstitialAd;
	AdView adView = null;
	void callAdds()
	{
		//AdSize.MEDIUM_RECTANGLE
		adView = (AdView) this.findViewById(R.id.adView);
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
		interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId(M.INTERSTITIAL_ID);
	    
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
		if(mGR.mInApp!=null)
			mGR.mInApp.onDestroy();
	}
	void pause()
	{
		mGR.resumeCounter = 0;
		M.stop(GameRenderer.mContext);
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putInt("screen", M.GameScreen);
		editor.putBoolean("setValue", M.setValue);
		editor.putBoolean("addFree", mGR.addFree);
		editor.putInt("mLevel", mGR.mLevel);
		editor.putInt("mScore", mGR.mScore);
		editor.putInt("mHScore", mGR.mHScore);
		editor.putInt("max", mGR.max);
		editor.putInt("moveDirection", mGR.moveDirection);
		
		for(int i=0;i<mGR.ach.length;i++)
		{
			editor.putBoolean("ach"+i, mGR.ach[i]);
		}
		
		for (int i = 0; i < mGR.mNum.length; i++) {
			for (int j = 0; j < mGR.mNum[i].length; j++) {
				editor.putBoolean(i + "NumMar" + j, mGR.mNum[i][j].isMarge);
				editor.putInt(i + "Numnum" + j, mGR.mNum[i][j].num);

			}
		}
		editor.commit();
	}
	void resume()
	{
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = prefs.getInt("screen", M.GAMELOGO);
		M.setValue = prefs.getBoolean("setValue", M.setValue);
		mGR.addFree = prefs.getBoolean("addFree", mGR.addFree);
		mGR.mLevel = prefs.getInt("mLevel", mGR.mLevel);
		mGR.mScore = prefs.getInt("mScore", mGR.mScore);
		mGR.mHScore = prefs.getInt("mHScore", mGR.mHScore);
		mGR.max = prefs.getInt("max", mGR.max);
		mGR.moveDirection = prefs.getInt("moveDirection", mGR.moveDirection);

		for(int i=0;i<mGR.ach.length;i++)
		{
			 mGR.ach[i] = prefs.getBoolean("ach"+i, mGR.ach[i]);
		}
		
		for (int i = 0; i < mGR.mNum.length; i++) {
			for (int j = 0; j < mGR.mNum[i].length; j++) {
				mGR.mNum[i][j].isMarge = prefs.getBoolean(i + "NumMar" + j,
						mGR.mNum[i][j].isMarge);
				mGR.mNum[i][j].num = prefs.getInt(i + "Numnum" + j,
						mGR.mNum[i][j].num);

			}
		}

		mGR.resumeCounter = 0;
		if(M.GameScreen == M.GAMEPLAY)
			mGR.mHelp = 10;
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
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		for(int i=0;i<mGR.ach.length;i++)
		{
			if(mGR.ach[i])
				UnlockAchievement(mGR.root.Achivment[i]);
		}
		
	}
	int RC_UNUSED = 5001;
	public void Submitscore() {
		final int ID =R.string.leaderboard_score;
    	System.out.println("~~~~~~~~~~~~Submitscore~~~~~~~~~~");
    	if (isSignedIn())
    		Games.Leaderboards.submitScore(getApiClient(), getString(ID), mGR.mHScore);
	}
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
		System.out.println("Load!!!!!!!!!!!!!!!!!!");
		if (!interstitialAd.isLoaded() && !mGR.addFree) {
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
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			super.onActivityResult(requestCode, resultCode, data);
			System.out.println("[requestCode = " + requestCode
					+ "] [resultCode = " + resultCode + "] [data = " + data
					+ "]");
			if (requestCode == _InApp.RC_REQUEST) {
				if (!mGR.mInApp.mHelper.handleActivityResult(
						requestCode, resultCode, data)) {
					super.onActivityResult(requestCode, resultCode, data);
				} else {
					System.out.println("onActivityResult error = RC_REQUEST");
				}
			}
		} catch (Exception e) {
			System.out.println("onActivityResult error = " + e.toString());
		}
	}
}