package com.oneday.games24.fearlessmotoracing3d;

import rajawali.RajawaliActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class Start extends RajawaliActivity {
	static Context mContext;
	private HTTRenderer mGR;

	void callAdds() {
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);  
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		WindowManager mWinMgr = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		M.ScreenWidth = mWinMgr.getDefaultDisplay().getWidth();
		M.ScreenHieght = mWinMgr.getDefaultDisplay().getHeight();
		mContext = this;
		super.onCreate(savedInstanceState);
		mGR = new HTTRenderer(this);
		mGR.setSurfaceView(mSurfaceView);
		super.setRenderer(mGR);
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
		mLayout.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
		
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
		new AlertDialog.Builder(this)
				.setIcon(R.drawable.icon)
				.setTitle("Do you want to exit?")
				.setPositiveButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				})
				.setNegativeButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								mGR.root.counter = 0;
								finish();
								M.GameScreen = M.GAMELOGO;
							}
						}).show();
	}

	void Buy() {
		new AlertDialog.Builder(this)
				.setIcon(R.drawable.icon)
				.setTitle("You don't have sufficient coin.")
				.setPositiveButton("Later",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int which) {}})
				.setNegativeButton("Buy",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int which) {

							}
						}).show();
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

	}

}
