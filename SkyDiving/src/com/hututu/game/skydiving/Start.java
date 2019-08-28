package com.hututu.game.skydiving;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.games.Games;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class Start extends BaseGameActivity {
	int _keyCode = 0;
	AdView adView = null;
	AdView adfull = null;
	AdView adHouse = null;

	GameRenderer mGR = null;
	private static Context CONTEXT;

	void callAdds() {
		{
			adView = (AdView) this.findViewById(R.id.advbanner);
			AdRequest adRequest = new AdRequest.Builder().build();
			adView.loadAd(adRequest);
			adView.setAdListener(new AdListener() {
				public void onAdLoaded() {
					adView.bringToFront();
				}
			});
		}
		{
			adfull = (AdView) this.findViewById(R.id.advfull);
			AdRequest adRequest = new AdRequest.Builder().build();
			adfull.loadAd(adRequest);
			adfull.setAdListener(new AdListener() {
				public void onAdLoaded() {
					adfull.bringToFront();
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
	public void onCreate(Bundle savedInstanceState) {

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);// OnlyOneChange
		CONTEXT = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		WindowManager mWinMgr = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		M.ScreenWidth = mWinMgr.getDefaultDisplay().getWidth();
		M.ScreenHieght = mWinMgr.getDefaultDisplay().getHeight();
		mGR = new GameRenderer(this);
		VortexView glSurface = (VortexView) findViewById(R.id.vortexview);
		glSurface.setRenderer(mGR);
		glSurface.showRenderer(mGR);
		callAdds();
	}

	public static Context getContext() {
		return CONTEXT;
	}

	@Override
	public void onPause() {
		M.stop(CONTEXT);
		pause();
		super.onPause();
	}

	@Override
	public void onResume() {
		resume();
		super.onResume();
		// view.onResume();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			switch (M.GameScreen) {
			case M.GAMEMENU:
				Exit();
				break;
			case M.GAMEPLAY:
				M.GameScreen = M.GAMEPAUSE;
				break;
			default:
				M.GameScreen = M.GAMEMENU;
				break;
			}
			return false;
		}
		_keyCode = keyCode;
		return super.onKeyDown(keyCode, event);
	}

	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// if(keyCode==KeyEvent.KEYCODE_BACK)
		// return false;
		_keyCode = 0;
		return super.onKeyUp(keyCode, event);
	}

	public void onDestroy() {
		super.onDestroy();
	}

	void pause() {
		
		
		System.out.println(mGR.mBase.go+" !!!!!!!!!!!!!!!!!!!! "+M.SPEED+"  "+mGR.mBase.x+"   "+mGR.mBase.y);
		
		if (M.GameScreen == M.GAMEPLAY)
			M.GameScreen = M.GAMEPAUSE;
		int i = 0;
		mGR.resumeCounter = 0;
		M.stop(mGR.mContext);
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putInt("screen", M.GameScreen);
		editor.putBoolean("setValue", M.setValue);
		editor.putFloat("SPEED", M.SPEED);

		editor.putInt("mDistance", mGR.mDistance);
		editor.putInt("mLevel", mGR.mLevel);
		editor.putInt("mUnLevel", mGR.mUnLevel);
		editor.putInt("mPage", mGR.mPage);
		editor.putInt("mWave", mGR.mWave);
		for (i = 0; i < mGR.mArrayWve.length; i++)
			editor.putInt("mArrayWve" + i, mGR.mArrayWve[i]);
		editor.putInt("mScore", mGR.mScore);
		editor.putInt("mHScore", mGR.mHScore);

		
		editor.putFloat("r", mGR.r);
		editor.putFloat("g", mGR.g);
		editor.putFloat("b", mGR.b);
		
		editor.putFloat("mDown", mGR.mDown);
		editor.putFloat("y1", mGR.y1);
		editor.putFloat("y2", mGR.y2);
		editor.putFloat("move", mGR.move);
		editor.putFloat("mMenu", mGR.mMenu);
		editor.putFloat("my", mGR.my);
		editor.putFloat("Inc", mGR.Inc);
		for (i = 0; i < mGR.mBG.length; i++) {
			editor.putFloat("BGx" + i, mGR.mBG[i].x);
			editor.putFloat("BGy" + i, mGR.mBG[i].y);
			editor.putFloat("BGvx" + i, mGR.mBG[i].vx);
		}
		for (i = 0; i < mGR.mCluod.length; i++) {
			editor.putFloat("mCluodx" + i, mGR.mCluod[i].x);
			editor.putFloat("mCluody" + i, mGR.mCluod[i].y);
			editor.putFloat("mCluodvx" + i, mGR.mCluod[i].vx);
		}
		for (i = 0; i < mGR.mChar.length; i++) {
			for (int j = 0; j < mGR.mChar[i].length; j++) {
				editor.putFloat(i + "mCharx" + j, mGR.mChar[i][j].x);
				editor.putFloat(i + "mChary" + j, mGR.mChar[i][j].y);
				editor.putFloat(i + "mCharvx" + j, mGR.mChar[i][j].vx);
				editor.putFloat(i + "mCharvy" + j, mGR.mChar[i][j].vy);
				editor.putFloat(i + "mCharp" + j, mGR.mChar[i][j].p);

				editor.putInt(i + "mChargo" + j, mGR.mChar[i][j].go);
				editor.putInt(i + "mCharr" + j, mGR.mChar[i][j].rotate);
				editor.putInt(i + "mChari" + j, mGR.mChar[i][j].inc);
				editor.putInt(i + "mCharb" + j, mGR.mChar[i][j].blast);
				editor.putInt(i + "mCharPl" + j, mGR.mChar[i][j].Pl);
				editor.putInt(i + "mCharcoun" + j, mGR.mChar[i][j].counter);
			}
		}

		{
			editor.putFloat("mBasex", mGR.mBase.x);
			editor.putFloat("mBasey", mGR.mBase.y);
			editor.putFloat("mBasevx", mGR.mBase.vx);
			editor.putFloat("mBasevy", mGR.mBase.vy);
			editor.putFloat("mBasep", mGR.mBase.p);

			editor.putInt("mBasego", mGR.mBase.go);
			editor.putInt("mBaser", mGR.mBase.rotate);
			editor.putInt("mBasei", mGR.mBase.inc);
			editor.putInt("mBaseb", mGR.mBase.blast);
		}

		editor.commit();
	}

	void resume() {
		int i;
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = prefs.getInt("screen", M.GAMELOGO);
		M.setValue = prefs.getBoolean("setValue", M.setValue);
		M.SPEED = prefs.getFloat("SPEED", M.SPEED);
		mGR.resumeCounter = 0;

		mGR.r= prefs.getFloat("r", mGR.r);
		mGR.g= prefs.getFloat("g", mGR.g);
		mGR.b= prefs.getFloat("b", mGR.b);
		
		
		
		mGR.mDistance = prefs.getInt("mDistance", mGR.mDistance);
		mGR.mLevel = prefs.getInt("mLevel", mGR.mLevel);
		mGR.mUnLevel = prefs.getInt("mUnLevel", mGR.mUnLevel);
		mGR.mPage = prefs.getInt("mPage", mGR.mPage);
		mGR.mWave = prefs.getInt("mWave", mGR.mWave);
		for (i = 0; i < mGR.mArrayWve.length; i++)
			mGR.mArrayWve[i] = prefs.getInt("mArrayWve" + i, mGR.mArrayWve[i]);
		mGR.mScore = prefs.getInt("mScore", mGR.mScore);
		mGR.mHScore = prefs.getInt("mHScore", mGR.mHScore);

		mGR.mDown = prefs.getFloat("mDown", mGR.mDown);
		mGR.y1 = prefs.getFloat("y1", mGR.y1);
		mGR.y2 = prefs.getFloat("y2", mGR.y2);
		mGR.move = prefs.getFloat("move", mGR.move);
		mGR.mMenu = prefs.getFloat("mMenu", mGR.mMenu);
		mGR.my = prefs.getFloat("my", mGR.my);
		mGR.Inc = prefs.getFloat("Inc", mGR.Inc);
		for (i = 0; i < mGR.mBG.length; i++) {
			mGR.mBG[i].x = prefs.getFloat("BGx" + i, mGR.mBG[i].x);
			mGR.mBG[i].y = prefs.getFloat("BGy" + i, mGR.mBG[i].y);
			mGR.mBG[i].vx = prefs.getFloat("BGvx" + i, mGR.mBG[i].vx);
		}
		for (i = 0; i < mGR.mCluod.length; i++) {
			mGR.mCluod[i].x = prefs.getFloat("mCluodx" + i, mGR.mCluod[i].x);
			mGR.mCluod[i].y = prefs.getFloat("mCluody" + i, mGR.mCluod[i].y);
			mGR.mCluod[i].vx = prefs.getFloat("mCluodvx" + i, mGR.mCluod[i].vx);
		}
		for (i = 0; i < mGR.mChar.length; i++) {
			for (int j = 0; j < mGR.mChar[i].length; j++) {
				mGR.mChar[i][j].x = prefs.getFloat(i + "mCharx" + j,
						mGR.mChar[i][j].x);
				mGR.mChar[i][j].y = prefs.getFloat(i + "mChary" + j,
						mGR.mChar[i][j].y);
				mGR.mChar[i][j].vx = prefs.getFloat(i + "mCharvx" + j,
						mGR.mChar[i][j].vx);
				mGR.mChar[i][j].vy = prefs.getFloat(i + "mCharvy" + j,
						mGR.mChar[i][j].vy);
				mGR.mChar[i][j].p = prefs.getFloat(i + "mCharp" + j,
						mGR.mChar[i][j].p);

				mGR.mChar[i][j].go = prefs.getInt(i + "mChargo" + j,
						mGR.mChar[i][j].go);
				mGR.mChar[i][j].rotate = prefs.getInt(i + "mCharr" + j,
						mGR.mChar[i][j].rotate);
				mGR.mChar[i][j].inc = prefs.getInt(i + "mChari" + j,
						mGR.mChar[i][j].inc);
				mGR.mChar[i][j].blast = prefs.getInt(i + "mCharb" + j,
						mGR.mChar[i][j].blast);
				mGR.mChar[i][j].Pl = prefs.getInt(i + "mCharPl" + j,
						mGR.mChar[i][j].Pl);
				mGR.mChar[i][j].counter = prefs.getInt(i + "mCharcoun" + j,
						mGR.mChar[i][j].counter);
			}
		}

		{
			mGR.mBase.x = prefs.getFloat("mBasex", mGR.mBase.x);
			mGR.mBase.y = prefs.getFloat("mBasey", mGR.mBase.y);
			mGR.mBase.vx = prefs.getFloat("mBasevx", mGR.mBase.vx);
			mGR.mBase.vy = prefs.getFloat("mBasevy", mGR.mBase.vy);
			mGR.mBase.p = prefs.getFloat("mBasep", mGR.mBase.p);

			mGR.mBase.go = prefs.getInt("mBasego", mGR.mBase.go);
			mGR.mBase.rotate = prefs.getInt("mBaser", mGR.mBase.rotate);
			mGR.mBase.inc = prefs.getInt("mBasei", mGR.mBase.inc);
			mGR.mBase.blast = prefs.getInt("mBaseb", mGR.mBase.blast);
		}
		System.out.println(mGR.mBase.go+"  @@@@@@@@@@@@@@@@@@ "+M.SPEED+"  "+mGR.mBase.x+"   "+mGR.mBase.y);
		mGR.root.is = isSignedIn();
		if (M.GameScreen != M.GAMELOGO && M.GameScreen != M.GAMEADD)
			M.play(mGR.mContext, R.drawable.splash_2);
	}

	void Exit() {
		new AlertDialog.Builder(this)
				.setIcon(R.drawable.icon)
				.setTitle("Do you want to Exit?")
				.setPositiveButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// Intent intent = new Intent(Intent.ACTION_VIEW);
						// intent.setData(Uri.parse(M.LINK+getClass().getPackage().getName()));
						// startActivity(intent);
					}
				})
				.setNegativeButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								mGR.root.Counter = 0;
								finish();
								M.GameScreen = M.GAMELOGO;
							}
						}).show();
	}

	@Override
	public void onSignInFailed() {
		mGR.root.is = isSignedIn();
		// TODO Auto-generated method stub

	}

	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		mGR.root.is = isSignedIn();
	}

	public void Submitscore(final int ID, int score) {
		if (!isSignedIn()) {
			beginUserInitiatedSignIn();

		} else {
			Games.Leaderboards.submitScore(getApiClient(), getString(ID), score);
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

}