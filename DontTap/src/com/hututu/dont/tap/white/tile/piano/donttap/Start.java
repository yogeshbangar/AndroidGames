package com.hututu.dont.tap.white.tile.piano.donttap;

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

public class Start  extends BaseGameActivity 
{
	int _keyCode = 0;
	AdView adView = null;
	GameRenderer mGR = null;
	private static Context CONTEXT;
	private InterstitialAd interstitial;
	void callAdds()
	{
		//AdSize.MEDIUM_RECTANGLE
		adView = (AdView) this.findViewById(R.id.addgame);
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
		interstitial = new InterstitialAd(this);
		interstitial.setAdUnitId(getString(R.string.INTERSTITIAL_ID));
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
			if(M.GameScreen == M.GAMEMENU)
				Exit();
			else
				M.GameScreen = M.GAMEMENU;
			mGR.mSelTile =-1;
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

	void pause() {
		M.stop(GameRenderer.mContext);
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putInt("screen", M.GameScreen);
		editor.putBoolean("setValue", M.setValue);
		editor.putBoolean("addFree", mGR.addFree);

		editor.putBoolean("SingUpadate", mGR.SingUpadate);
		for (int i = 0; i < mGR.mAchiUnlock.length; i++)
			editor.putBoolean("mAchiUnlock" + i, mGR.mAchiUnlock[i]);
		editor.putBoolean("mul_Color", mGR.mul_Color);
		editor.putBoolean("OverRev", mGR.OverRev);
		editor.putBoolean("NewBest", mGR.NewBest);

		editor.putInt("RowNo", mGR.RowNo);
		editor.putInt("mSelTile", mGR.mSelTile);
		editor.putInt("mSelSemiTile", mGR.mSelSemiTile);
		editor.putInt("tile", mGR.tile);
		editor.putInt("time", mGR.time);
		editor.putInt("MoveTile", mGR.MoveTile);
		editor.putInt("TotalTile", mGR.TotalTile);
		editor.putInt("OverCounter", mGR.OverCounter);
		editor.putInt("type", mGR.type);
		editor.putInt("next", mGR.next);
		editor.putInt("Bllinck", mGR.Bllinck);
		editor.putInt("spd", mGR.spd);

		editor.putFloat("Speed", mGR.Speed);
		editor.putFloat("go", mGR.go);
		editor.putFloat("lastWindow", mGR.lastWindow);
		for (int i = 0; i < mGR.BestScore.length; i++) {
			for (int j = 0; j < mGR.BestScore[i].length; j++)
				editor.putFloat(i + "BestScore" + j, mGR.BestScore[i][j]);
		}
		editor.putFloat("Score", mGR.Score);

		editor.putString("mPName", mGR.mPName);
		for (int i = 0; i < mGR.mTile.length; i++) {
			for (int j = 0; j < mGR.mTile[i].length; j++) {
				editor.putInt(i + "Tilemul" + j, mGR.mTile[i][j].mul);
				editor.putInt(i + "Tileclr" + j, mGR.mTile[i][j].clr);
				editor.putFloat(i + "Tiley" + j, mGR.mTile[i][j].y);
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
		
		mGR.SingUpadate = prefs.getBoolean("SingUpadate", mGR.SingUpadate);
		for (int i = 0; i < mGR.mAchiUnlock.length; i++)
			mGR.mAchiUnlock[i] = prefs.getBoolean("mAchiUnlock" + i, mGR.mAchiUnlock[i]);
		mGR.mul_Color = prefs.getBoolean("mul_Color", mGR.mul_Color);
		mGR.OverRev = prefs.getBoolean("OverRev", mGR.OverRev);
		mGR.NewBest = prefs.getBoolean("NewBest", mGR.NewBest);

		mGR.RowNo = prefs.getInt("RowNo", mGR.RowNo);
		mGR.mSelTile = prefs.getInt("mSelTile", mGR.mSelTile);
		mGR.mSelSemiTile = prefs.getInt("mSelSemiTile", mGR.mSelSemiTile);
		mGR.tile = prefs.getInt("tile", mGR.tile);
		mGR.time= prefs.getInt("time", mGR.time);
		mGR.MoveTile = prefs.getInt("MoveTile", mGR.MoveTile);
		mGR.TotalTile = prefs.getInt("TotalTile", mGR.TotalTile);
		mGR.OverCounter = prefs.getInt("OverCounter", mGR.OverCounter);
		mGR.type = prefs.getInt("type", mGR.type);
		mGR.next = prefs.getInt("next", mGR.next);
		mGR.Bllinck = prefs.getInt("Bllinck", mGR.Bllinck);
		mGR.spd = prefs.getInt("spd", mGR.spd);

		mGR.Speed = prefs.getFloat("Speed", mGR.Speed);
		mGR.go = prefs.getFloat("go", mGR.go);
		mGR.lastWindow = prefs.getFloat("lastWindow", mGR.lastWindow);
		for (int i = 0; i < mGR.BestScore.length; i++) {
			for (int j = 0; j < mGR.BestScore[i].length; j++)
				mGR.BestScore[i][j] = prefs.getFloat(i + "BestScore" + j, mGR.BestScore[i][j]);
		}
		mGR.Score = prefs.getFloat("Score", mGR.Score);

		mGR.mPName = prefs.getString("mPName", mGR.mPName);
		for (int i = 0; i < mGR.mTile.length; i++) {
			for (int j = 0; j < mGR.mTile[i].length; j++) {
				mGR.mTile[i][j].mul = prefs.getInt(i + "Tilemul" + j, mGR.mTile[i][j].mul);
				mGR.mTile[i][j].clr = prefs.getInt(i + "Tileclr" + j, mGR.mTile[i][j].clr);
				mGR.mTile[i][j].y = prefs.getFloat(i + "Tiley" + j, mGR.mTile[i][j].y);
			}
		}
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
	public void load() {
		try{handlerload.sendEmptyMessage(0);}catch(Exception e){}
	}
	Handler handlerload = new Handler() {public void handleMessage(Message msg) {loadInter();}};

	
	private void loadInter(){
		if (!interstitial.isLoaded() && !mGR.addFree) {
			AdRequest adRequest = new AdRequest.Builder()
//					.addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("67701763FB847AEBD3C6EE486475ED94")
			.build();
			interstitial.loadAd(adRequest);
			interstitial.setAdListener(new AdGameListener(this));
		}
	}

	public void ShowInterstitial() {
		if(!mGR.addFree)
			try{handler.sendEmptyMessage(0);}catch(Exception e){}
	}
	Handler handler = new Handler() {public void handleMessage(Message msg) {show();}};

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
//			GameRenderer.mStart.Submitscore(R.string.leaderboard_score, mGR.mScore);
			mGR.SingUpadate = true;
			
//			mGR.mPName = getGamesClient().getCurrentPlayer().getDisplayName().substring(0, 9);
			
			mGR.mPName = Games.Players.getCurrentPlayer(getApiClient()).getDisplayName();
			if(mGR.mPName.length() > 26)
				mGR.mPName = mGR.mPName.substring(0, 26);
			
		}
	}
	public void Submitscore(final int ID,long total) {
		if (!isSignedIn()) {
//			beginUserInitiatedSignIn();
		} else { Games.Leaderboards.submitScore(getApiClient(), getString(ID), total);}
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
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (!mGR.mInApp.mHelper.handleActivityResult(requestCode,resultCode, data)) {
			super.onActivityResult(requestCode, resultCode, data);
		} else {
		}
	}
}