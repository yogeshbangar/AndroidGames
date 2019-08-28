package com.hututu.game.bubblecandyrescue;


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
			/* AdHouse */
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
		interstitialAd =  new InterstitialAd(this);
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

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.d("----------------=>  " + keyCode, "   -----------    ");
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			switch (M.GameScreen) {
			case M.GAMEPLAY:
				M.GameScreen = M.GAMEPAUSE;
				M.stop(GameRenderer.mContext);
				break;
			case M.GAMEMENU:
				Exit();
				break;
			case M.GAMEPAUSE:
			case M.GAMEOVER:
			case M.GAMEWIN:
			case M.GAMECONG:
				if (mGR.IsEndless) {
					M.GameScreen = M.GAMEMENU;
					M.play(GameRenderer.mContext, R.drawable.splash_theme);
				} else{
					M.GameScreen = M.GAMELEVEL;
					mGR.root.page = mGR.mLvl/15;
				}
				break;
			default:
				M.GameScreen = M.GAMEMENU;
				M.play(GameRenderer.mContext, R.drawable.splash_theme);
				break;
			}

			return false;
		}
		_keyCode = keyCode;
		return super.onKeyDown(keyCode, event);
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
	void pause()
	{
		if(M.GameScreen == M.GAMEPLAY)
			M.GameScreen = M.GAMEPAUSE;
		mGR.resumeCounter = 0;
		M.stop(GameRenderer.mContext);
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
	    Editor editor = prefs.edit();
	    editor.putInt("screen", M.GameScreen);
	    editor.putBoolean("setValue", M.setValue);
	    editor.putBoolean("setMusic", M.setMusic);
	    
	    
	    
	    
	    
		for (int i = 0; i < M.Row; i++) {
			for (int j = 0; j < M.C; j++) {
				editor.putBoolean(i+"matrixv"+j, mGR.matrix[i][j].visited);
				editor.putBoolean(i+"matrixch"+j, mGR.matrix[i][j].checkFall);
				
				editor.putInt(i+"matrixca"+j, mGR.matrix[i][j].candy);
				editor.putInt(i+"matrixco"+j, mGR.matrix[i][j].Color);
				editor.putInt(i+"matrixani"+j, mGR.matrix[i][j].anim);
				editor.putInt(i+"matrixi"+j, mGR.matrix[i][j].i);
				editor.putInt(i+"matrixj"+j, mGR.matrix[i][j].j);
				
			}
		}
		{
			editor.putBoolean("playerm", mGR.mPlayer.move);
			editor.putInt("playerfir", mGR.mPlayer.fireColor);
			editor.putInt("playerres", mGR.mPlayer.ResColor);
			editor.putInt("playerani", mGR.mPlayer.anim);
			editor.putFloat("playerx", mGR.mPlayer.x);
			editor.putFloat("playery", mGR.mPlayer.y);
			editor.putFloat("playervx", mGR.mPlayer.vx);
			editor.putFloat("playervy", mGR.mPlayer.vy);
			editor.putFloat("playerang", mGR.mPlayer.ang);
		}
		
		
		editor.putBoolean("IsEndless", mGR.IsEndless);
		editor.putBoolean("playBest", mGR.playBest);
		editor.putBoolean("addFree", mGR.addFree);
		editor.putBoolean("SingUpadate", mGR.SingUpadate);
		for (int i = 0; i < mGR.Achi.length; i++)
			editor.putBoolean("Achi"+i, mGR.Achi[i]);
		
		for (int i = 0; i < GameRenderer.Candy.length; i++)
			editor.putInt("Candy"+i, GameRenderer.Candy[i]);
		
		for (int i = 0; i < mGR.BScore.length; i++)
			editor.putInt("BScore"+i, mGR.BScore[i]);
		
		editor.putInt("mHEScore", mGR.mHEScore);
		editor.putInt("score", mGR.score);
		editor.putInt("mLvl", mGR.mLvl);
		editor.putInt("mULvl", mGR.mULvl);
		editor.putInt("win", mGR.win);
		editor.putInt("hitBall", mGR.hitBall);
		editor.putFloat("by", mGR.by);
		
	    editor.commit();
	}
	void resume()
	{
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = prefs.getInt("screen", M.GAMELOGO);
		M.setValue = prefs.getBoolean("setValue", M.setValue);
		M.setMusic = prefs.getBoolean("setMusic", M.setMusic);
		
		

	    
	    
		for (int i = 0; i < M.Row; i++) {
			for (int j = 0; j < M.C; j++) {
				mGR.matrix[i][j].visited = prefs.getBoolean(i+"matrixv"+j, mGR.matrix[i][j].visited);
				mGR.matrix[i][j].checkFall = prefs.getBoolean(i+"matrixch"+j, mGR.matrix[i][j].checkFall);
				
				mGR.matrix[i][j].candy = (byte)prefs.getInt(i+"matrixca"+j, mGR.matrix[i][j].candy);
				mGR.matrix[i][j].Color = prefs.getInt(i+"matrixco"+j, mGR.matrix[i][j].Color);
				mGR.matrix[i][j].anim = prefs.getInt(i+"matrixani"+j, mGR.matrix[i][j].anim);
				mGR.matrix[i][j].i = prefs.getInt(i+"matrixi"+j, mGR.matrix[i][j].i);
				mGR.matrix[i][j].j = prefs.getInt(i+"matrixj"+j, mGR.matrix[i][j].j);
				
			}
		}
		{
			mGR.mPlayer.move = prefs.getBoolean("playerm", mGR.mPlayer.move);
			mGR.mPlayer.fireColor = prefs.getInt("playerfir", mGR.mPlayer.fireColor);
			mGR.mPlayer.ResColor = prefs.getInt("playerres", mGR.mPlayer.ResColor);
			mGR.mPlayer.anim = prefs.getInt("playerani", mGR.mPlayer.anim);
			mGR.mPlayer.x = prefs.getFloat("playerx", mGR.mPlayer.x);
			mGR.mPlayer.y = prefs.getFloat("playery", mGR.mPlayer.y);
			mGR.mPlayer.vx = prefs.getFloat("playervx", mGR.mPlayer.vx);
			mGR.mPlayer.vy = prefs.getFloat("playervy", mGR.mPlayer.vy);
			mGR.mPlayer.ang  = prefs.getFloat("playerang", mGR.mPlayer.ang);
		}
		
		
		mGR.IsEndless = prefs.getBoolean("IsEndless", mGR.IsEndless);
		mGR.playBest = prefs.getBoolean("playBest", mGR.playBest);
		mGR.addFree = prefs.getBoolean("addFree", mGR.addFree);
		mGR.SingUpadate = prefs.getBoolean("SingUpadate", mGR.SingUpadate);
		for (int i = 0; i < mGR.Achi.length; i++)
			mGR.Achi[i] = prefs.getBoolean("Achi"+i, mGR.Achi[i]);
		
		for (int i = 0; i < GameRenderer.Candy.length; i++)
			GameRenderer.Candy[i] = (byte)prefs.getInt("Candy"+i, GameRenderer.Candy[i]);
		
		for (int i = 0; i < mGR.BScore.length; i++)
			mGR.BScore[i]= prefs.getInt("BScore"+i, mGR.BScore[i]);
		
		mGR.mHEScore = prefs.getInt("mHEScore", mGR.mHEScore);
		mGR.score = prefs.getInt("score", mGR.score);
		mGR.mLvl = prefs.getInt("mLvl", mGR.mLvl);
		mGR.mULvl= prefs.getInt("mULvl", mGR.mULvl);
		mGR.win = prefs.getInt("win", mGR.win);
		mGR.hitBall = prefs.getInt("hitBall", mGR.hitBall);
		mGR.by = prefs.getFloat("by", mGR.by);
		
	    mGR.resumeCounter = 0;
	    if(M.GameScreen == M.GAMEMENU)
	    	M.play(GameRenderer.mContext,R.drawable.splash_theme);
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
		if(!mGR.SingUpadate)
		{
			for(int i=0;i<mGR.Achi.length;i++)
			{
				if(mGR.Achi[i])
				{
					UnlockAchievement(mGR.achiment[i]);
				}
			}
			{
				int scr = 0;
				for (int i = 0; i < mGR.BScore.length; i++)
					scr += mGR.BScore[i];
				Submitscore(R.string.leaderboard_puzzle_score, scr);
				Submitscore(R.string.leaderboard_puzzle_score, mGR.mHEScore);
			}
			mGR.SingUpadate = true;
			
		}
	}
	public void Submitscore(final int ID,int total) {
		if (!isSignedIn()) {
//			beginUserInitiatedSignIn();
		} else { Games.Leaderboards.submitScore(getApiClient(), getString(ID),total);
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
	void loadInter(){
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