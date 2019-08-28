package com.hututu.game.ScarecrowvsBirds;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class Start extends Activity {
	int _keyCode = 0;
	AdView adView = null;
	AdView adHouse = null;//AdHouse
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
	public void onCreate(Bundle savedInstanceState) {
		CONTEXT = this;
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);// OnlyOneChange
		setContentView(R.layout.game);
		WindowManager mWinMgr = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		M.ScreenWidth = mWinMgr.getDefaultDisplay().getWidth();
		M.ScreenHieght = mWinMgr.getDefaultDisplay().getHeight();
		mGR = new GameRenderer(this);
		VortexView glSurface = (VortexView) findViewById(R.id.vortexview); // use
																			// the
																			// xml
																			// to
																			// set
																			// the
																			// view
		glSurface.setRenderer(mGR);
		glSurface.showRenderer(mGR);
		callAdds();
	}

	public static Context getContext() {
		return CONTEXT;
	}

	@Override
	public void onPause() {
		M.stop();
		M.BGStop();
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
		Log.d("----------------=>  " + keyCode, "   -----------    ");
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			switch (M.GameScreen) {
			case M.GAMEPLAY:
				M.stop();
				M.BGStop();
				M.GameScreen = M.GAMEPAUSE;
				break;
			case M.GAMEHIGHSCORE:
				M.GameScreen = M.GamePrevScreen;
				break;
			case M.GAMELOGO:
			case M.GAMEADD:
			case M.GAMEMENU:
				get();
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
		if (keyCode == KeyEvent.KEYCODE_BACK)
			return false;
		_keyCode = 0;
		return super.onKeyUp(keyCode, event);
	}

	public void onDestroy() {
		super.onDestroy();
	}

	void pause() {
		M.stop();
		M.BGStop();
		int i = 0;
		if (M.GameScreen == M.GAMEPLAY) {
			M.GameScreen = M.GAMEPAUSE;
		}
		mGR.resumeCounter = 0;

		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putInt("screen", M.GameScreen);
		editor.putBoolean("setValue", M.setValue);
		editor.putInt("mLevel", mGR.mLevel);
		editor.putInt("mScore", mGR.mScore);
		editor.putInt("HighScore", mGR.HighScore);
		editor.putInt("resumeCounter", mGR.resumeCounter);

		editor.putInt("SettingOpen", mGR.mSetting.Open);
		editor.putFloat("SettingX", mGR.mSetting.x);
		editor.putFloat("SettingY", mGR.mSetting.y);
		editor.putFloat("Settingvy", mGR.mSetting.vy);

		editor.putInt("ControlOpen", mGR.mControl.Open);
		editor.putFloat("mControlX", mGR.mControl.x);
		editor.putFloat("mControlY", mGR.mControl.y);
		editor.putFloat("mControlVy", mGR.mControl.vy);

		editor.putInt("mSpeedCount", mGR.mSpeedCount);
		editor.putFloat("M.Speed", M.Speed);
		editor.putBoolean("isFire", mGR.mArrow.isFire);
		editor.putBoolean("isGoal", mGR.mArrow.isGoal);
		editor.putFloat("mArrowVx", mGR.mArrow.vx);
		editor.putFloat("mArrowVy", mGR.mArrow.vy);
		editor.putFloat("mArrowang", (float) mGR.mArrow.ang);
		editor.putInt("mHattrickCnt", mGR.mArrow.mHattrickCnt);
		editor.putInt("mArrowPower", mGR.mArrow.Power);
		editor.putFloat("mBowX", mGR.mBow.x);
		editor.putFloat("mBowY", mGR.mBow.y);
		editor.putFloat("mBowang", (float) mGR.mBow.ang);
		editor.putInt("mBowNo", mGR.mBow.No);
		for (i = 0; i < mGR.mBird.length; i++) {
			editor.putFloat("mBirdX" + i, mGR.mBird[i].x);
			editor.putFloat("mBirdY" + i, mGR.mBird[i].y);
			editor.putFloat("mBirdScal" + i, mGR.mBird[i].scal);
			editor.putFloat("mBirdVx" + i, mGR.mBird[i].vx);
			editor.putFloat("mBirdVy" + i, mGR.mBird[i].vy);
			editor.putInt("mBirdType" + i, mGR.mBird[i].type);
			editor.putInt("mBirdNo" + i, mGR.mBird[i].no);
		}
		for (i = 0; i < mGR.mBlast.length; i++) {
			editor.putFloat("mBlastX" + i, mGR.mBlast[i].x);
			editor.putFloat("mBlastY" + i, mGR.mBlast[i].y);
			editor.putFloat("mBlastScal" + i, mGR.mBlast[i].scal);
			editor.putFloat("mBlastVx" + i, mGR.mBlast[i].vx);
			editor.putFloat("mBlastVy" + i, mGR.mBlast[i].vy);
			editor.putFloat("mBlastang" + i, mGR.mBlast[i].ang);
			editor.putFloat("mBlastangV" + i, mGR.mBlast[i].angV);
			editor.putInt("mBlastNo" + i, mGR.mBlast[i].no);
		}
		for (i = 0; i < mGR.mPankh.length; i++) {
			editor.putFloat("mPankhX" + i, mGR.mPankh[i].x);
			editor.putFloat("mPankhY" + i, mGR.mPankh[i].y);
			editor.putFloat("mPankhScal" + i, mGR.mPankh[i].scal);
			editor.putFloat("mPankhVx" + i, mGR.mPankh[i].vx);
			editor.putFloat("mPankhVy" + i, mGR.mPankh[i].vy);
			editor.putFloat("mPankhang" + i, mGR.mPankh[i].ang);
			editor.putFloat("mPankhangV" + i, mGR.mPankh[i].angV);
			editor.putFloat("mPankhanFad" + i, mGR.mPankh[i].fad);
			editor.putInt("mPankhNo" + i, mGR.mPankh[i].no);
		}
		for (i = 0; i < mGR.mPoint.length; i++) {
			editor.putFloat("mPointX" + i, mGR.mPoint[i].x);
			editor.putFloat("mPointY" + i, mGR.mPoint[i].y);
			editor.putFloat("mPointVy" + i, mGR.mPoint[i].vy);
			editor.putFloat("mPointFad" + i, mGR.mPoint[i].fad);
			editor.putInt("mPankhNo" + i, mGR.mPoint[i].no);
		}
		editor.putFloat("mPowerX", mGR.mPower.x);
		editor.putFloat("mPowerY", mGR.mPower.y);
		editor.putFloat("mPowerVy", mGR.mPower.vy);
		editor.putInt("mPowerCount", mGR.mPower.PowerCount);
		editor.putInt("mPowerType", mGR.mPower.PowerType);

		editor.putFloat("mPowerActiveX", mGR.mPowerActive.x);
		editor.putFloat("mPowerActiveY", mGR.mPowerActive.y);
		editor.putFloat("mPowerActiveVx", mGR.mPowerActive.vx);
		editor.putFloat("mPowerActiveVy", mGR.mPowerActive.vy);

		for (i = 0; i < mGR.mArrowPower.length; i++) {
			editor.putFloat("mArrowPowerX" + i, mGR.mArrowPower[i].x);
			editor.putFloat("mArrowPowerY" + i, mGR.mArrowPower[i].y);
			editor.putFloat("mArrowPowerVx" + i, mGR.mArrowPower[i].vx);
			editor.putFloat("mArrowPowerVy" + i, mGR.mArrowPower[i].vy);
			editor.putBoolean("mArrowPowerFire" + i, mGR.mArrowPower[i].isFire);
			editor.putFloat("mArrowPowerAng" + i,
					(float) mGR.mArrowPower[i].ang);
		}
		editor.putFloat("mmTargetX", mGR.mTarget.x);
		editor.putFloat("mmTargetY", mGR.mTarget.y);
		editor.putFloat("mmTargetFad", mGR.mTarget.fad);
		editor.putFloat("mmTargetVy", mGR.mTarget.vy);

		editor.putFloat("mPowerActiveX", mGR.mPowerActive.x);
		editor.putFloat("mPowerActiveY", mGR.mPowerActive.y);
		editor.putFloat("mPowerActiveVx", mGR.mPowerActive.vx);
		editor.putFloat("mPowerActiveVY", mGR.mPowerActive.vy);
		editor.commit();
	}

	void resume() {
		int i = 0;
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = prefs.getInt("screen", M.GameScreen);
		M.setValue = prefs.getBoolean("setValue", M.setValue);
		mGR.mLevel = prefs.getInt("mLevel", mGR.mLevel);
		mGR.mScore = prefs.getInt("mScore", mGR.mScore);
		mGR.HighScore = prefs.getInt("HighScore", mGR.HighScore);
		mGR.resumeCounter = prefs.getInt("resumeCounter", mGR.resumeCounter);

		mGR.mSetting.Open = prefs.getInt("SettingOpen", mGR.mSetting.Open);
		mGR.mSetting.x = prefs.getFloat("SettingX", mGR.mSetting.x);
		mGR.mSetting.y = prefs.getFloat("SettingY", mGR.mSetting.y);
		mGR.mSetting.vy = prefs.getFloat("Settingvy", mGR.mSetting.vy);

		mGR.mControl.Open = prefs.getInt("ControlOpen", mGR.mControl.Open);
		mGR.mControl.x = prefs.getFloat("mControlX", mGR.mControl.x);
		mGR.mControl.y = prefs.getFloat("mControlY", mGR.mControl.y);
		mGR.mControl.vy = prefs.getFloat("mControlVy", mGR.mControl.vy);
		mGR.mSpeedCount = prefs.getInt("mSpeedCount", mGR.mSpeedCount);
		M.Speed = prefs.getFloat("M.Speed", M.Speed);

		mGR.mArrow.isFire = prefs.getBoolean("isFire", mGR.mArrow.isFire);
		mGR.mArrow.isGoal = prefs.getBoolean("isGoal", mGR.mArrow.isGoal);
		mGR.mArrow.vx = prefs.getFloat("mArrowVx", mGR.mArrow.vx);
		mGR.mArrow.vy = prefs.getFloat("mArrowVy", mGR.mArrow.vy);
		mGR.mArrow.ang = prefs.getFloat("mArrowang", (float) mGR.mArrow.ang);
		mGR.mArrow.mHattrickCnt = prefs.getInt("mHattrickCnt",
				mGR.mArrow.mHattrickCnt);
		mGR.mArrow.Power = prefs.getInt("mArrowPower", mGR.mArrow.Power);

		mGR.mBow.x = prefs.getFloat("mBowX", mGR.mBow.x);
		mGR.mBow.y = prefs.getFloat("mBowY", mGR.mBow.y);
		mGR.mBow.ang = prefs.getFloat("mBowang", (float) mGR.mBow.ang);
		mGR.mBow.No = prefs.getInt("mBowNo", mGR.mBow.No);

		for (i = 0; i < mGR.mBird.length; i++) {
			mGR.mBird[i].x = prefs.getFloat("mBirdX" + i, mGR.mBird[i].x);
			mGR.mBird[i].y = prefs.getFloat("mBirdY" + i, mGR.mBird[i].y);
			mGR.mBird[i].scal = prefs.getFloat("mBirdScal" + i,
					mGR.mBird[i].scal);
			mGR.mBird[i].vx = prefs.getFloat("mBirdVx" + i, mGR.mBird[i].vx);
			mGR.mBird[i].vy = prefs.getFloat("mBirdVy" + i, mGR.mBird[i].vy);
			mGR.mBird[i].type = prefs
					.getInt("mBirdType" + i, mGR.mBird[i].type);
			mGR.mBird[i].no = prefs.getInt("mBirdNo" + i, mGR.mBird[i].no);
		}
		for (i = 0; i < mGR.mBlast.length; i++) {
			mGR.mBlast[i].x = prefs.getFloat("mBlastX" + i, mGR.mBlast[i].x);
			mGR.mBlast[i].y = prefs.getFloat("mBlastY" + i, mGR.mBlast[i].y);
			mGR.mBlast[i].scal = prefs.getFloat("mBlastScal" + i,
					mGR.mBlast[i].scal);
			mGR.mBlast[i].vx = prefs.getFloat("mBlastVx" + i, mGR.mBlast[i].vx);
			mGR.mBlast[i].vy = prefs.getFloat("mBlastVy" + i, mGR.mBlast[i].vy);
			mGR.mBlast[i].ang = prefs.getFloat("mBlastang" + i,
					mGR.mBlast[i].ang);
			mGR.mBlast[i].angV = prefs.getFloat("mBlastangV" + i,
					mGR.mBlast[i].angV);
			mGR.mBlast[i].no = prefs.getInt("mBlastNo" + i, mGR.mBlast[i].no);
		}
		for (i = 0; i < mGR.mPankh.length; i++) {
			mGR.mPankh[i].x = prefs.getFloat("mPankhX" + i, mGR.mPankh[i].x);
			mGR.mPankh[i].y = prefs.getFloat("mPankhY" + i, mGR.mPankh[i].y);
			mGR.mPankh[i].scal = prefs.getFloat("mPankhScal" + i,
					mGR.mPankh[i].scal);
			mGR.mPankh[i].vx = prefs.getFloat("mPankhVx" + i, mGR.mPankh[i].vx);
			mGR.mPankh[i].vy = prefs.getFloat("mPankhVy" + i, mGR.mPankh[i].vy);
			mGR.mPankh[i].ang = prefs.getFloat("mPankhang" + i,
					mGR.mPankh[i].ang);
			mGR.mPankh[i].angV = prefs.getFloat("mPankhangV" + i,
					mGR.mPankh[i].angV);
			mGR.mPankh[i].fad = prefs.getFloat("mPankhanFad" + i,
					mGR.mPankh[i].fad);
			mGR.mPankh[i].no = prefs.getInt("mPankhNo" + i, mGR.mPankh[i].no);
		}
		for (i = 0; i < mGR.mPoint.length; i++) {
			mGR.mPoint[i].x = prefs.getFloat("mPointX" + i, mGR.mPoint[i].x);
			mGR.mPoint[i].y = prefs.getFloat("mPointY" + i, mGR.mPoint[i].y);
			mGR.mPoint[i].vy = prefs.getFloat("mPointVy" + i, mGR.mPoint[i].vy);
			mGR.mPoint[i].fad = prefs.getFloat("mPointFad" + i,
					mGR.mPoint[i].fad);
			mGR.mPoint[i].no = prefs.getInt("mPankhNo" + i, mGR.mPoint[i].no);
		}
		mGR.mPower.x = prefs.getFloat("mPowerX", mGR.mPower.x);
		mGR.mPower.y = prefs.getFloat("mPowerY", mGR.mPower.y);
		mGR.mPower.vy = prefs.getFloat("mPowerVy", mGR.mPower.vy);
		mGR.mPower.PowerCount = prefs.getInt("mPowerCount",
				mGR.mPower.PowerCount);
		mGR.mPower.PowerType = prefs.getInt("mPowerType", mGR.mPower.PowerType);

		mGR.mPowerActive.x = prefs
				.getFloat("mPowerActiveX", mGR.mPowerActive.x);
		mGR.mPowerActive.y = prefs
				.getFloat("mPowerActiveY", mGR.mPowerActive.y);
		mGR.mPowerActive.vx = prefs.getFloat("mPowerActiveVx",
				mGR.mPowerActive.vx);
		mGR.mPowerActive.vy = prefs.getFloat("mPowerActiveVy",
				mGR.mPowerActive.vy);

		for (i = 0; i < mGR.mArrowPower.length; i++) {
			mGR.mArrowPower[i].x = prefs.getFloat("mArrowPowerX" + i,
					mGR.mArrowPower[i].x);
			mGR.mArrowPower[i].y = prefs.getFloat("mArrowPowerY" + i,
					mGR.mArrowPower[i].y);
			mGR.mArrowPower[i].vx = prefs.getFloat("mArrowPowerVx" + i,
					mGR.mArrowPower[i].vx);
			mGR.mArrowPower[i].vy = prefs.getFloat("mArrowPowerVy" + i,
					mGR.mArrowPower[i].vy);
			mGR.mArrowPower[i].isFire = prefs.getBoolean("mArrowPowerFire" + i,
					mGR.mArrowPower[i].isFire);
			mGR.mArrowPower[i].ang = prefs.getFloat("mArrowPowerAng" + i,
					(float) mGR.mArrowPower[i].ang);
		}
		mGR.mTarget.x = prefs.getFloat("mmTargetX", mGR.mTarget.x);
		mGR.mTarget.y = prefs.getFloat("mmTargetY", mGR.mTarget.y);
		mGR.mTarget.fad = prefs.getFloat("mmTargetFad", mGR.mTarget.fad);
		mGR.mTarget.vy = prefs.getFloat("m mTargetVy", mGR.mTarget.vy);

		mGR.mPowerActive.x = prefs
				.getFloat("mPowerActiveX", mGR.mPowerActive.x);
		mGR.mPowerActive.y = prefs
				.getFloat("mPowerActiveY", mGR.mPowerActive.y);
		mGR.mPowerActive.vx = prefs.getFloat("mPowerActiveVx",
				mGR.mPowerActive.vx);
		mGR.mPowerActive.vy = prefs.getFloat("mPowerActiveVY",
				mGR.mPowerActive.vy);
	}

	void get() {
		new AlertDialog.Builder(this)
				.setIcon(R.drawable.icon)
				.setTitle("Do you want to exit?")
				.setPositiveButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// mGR.root.RateUs();
					}
				})
				.setNegativeButton("yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								mGR.root.Counter = 0;
								finish();
								M.GameScreen = M.GAMELOGO;
							}
						}).show();
	}

}