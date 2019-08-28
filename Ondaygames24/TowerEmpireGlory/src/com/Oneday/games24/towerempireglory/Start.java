package com.Oneday.games24.towerempireglory;

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

public class Start extends BaseGameActivity {
	int _keyCode = 0;
	AdView adView = null;
	GameRenderer mGR = null;
	private static Context CONTEXT;
	private InterstitialAd interstitial;

	void callAdds() {
		// AdSize.MEDIUM_RECTANGLE
		adView = (AdView) this.findViewById(R.id.addgame);
		AdRequest adRequest = new AdRequest.Builder()
		// .addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("67701763FB847AEBD3C6EE486475ED94")
				.build();
		adView.loadAd(adRequest);
		adView.setAdListener(new AdListener() {
			public void onAdLoaded() {
				adView.bringToFront();
			}
		});
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
		interstitial = new InterstitialAd(this);
		interstitial.setAdUnitId(getString(R.string.INTERSTITIAL_ID));
		WindowManager mWinMgr = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		M.ScreenWidth = mWinMgr.getDefaultDisplay().getWidth();
		M.ScreenHieght = mWinMgr.getDefaultDisplay().getHeight();
		mGR = new GameRenderer(this);
		VortexView glSurface = (VortexView) findViewById(R.id.vortexview);
		glSurface.setRenderer(mGR);
		glSurface.showRenderer(mGR);
		if (!getSharedPreferences("X", MODE_PRIVATE).getBoolean("addFree", mGR.addFree))
			callAdds();
		
		resume = true;
		load();
	}

	public static Context getContext() {
		return CONTEXT;
	}

	@Override
	public void onPause() {
		pause();
		super.onPause();
	}

	@Override
	public void onResume() {
		resume();
		super.onResume();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.d("----------------=>  " + keyCode, "   -----------    ");
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			switch (M.GameScreen) {
			case M.GAMEMENU:
				Exit();
				break;
			case M.GAMEPLAY:
				M.stop(10);
				M.GameScreen = M.GAMEPAUSE;
				M.play(GameRenderer.mContext, R.drawable.splash_other);
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
//		if (mGR.mInApp != null)
//			mGR.mInApp.onDestroy();
	}

	void pause() {
		M.stop(11);
		if(M.GameScreen == M.GAMEPLAY)
			M.GameScreen = M.GAMEPAUSE;
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putInt("screen", M.GameScreen);
		editor.putBoolean("setValue", M.setValue);
		editor.putBoolean("addFree", mGR.addFree);
		editor.putBoolean("SingUpadate", mGR.SingUpadate);
		for (int i = 0; i < mGR.Achi.length; i++)
			editor.putBoolean("Achi" + i, mGR.Achi[i]);
		for (int i = 0; i < mGR.BowPower.length; i++)
			editor.putBoolean("BowPower" + i, mGR.BowPower[i]);

		for (int i = 0; i < mGR.OppCount.length; i++)
			editor.putInt("OppCount" + i, mGR.OppCount[i]);
		for (int i = 0; i < mGR.Mana.length; i++)
			editor.putInt("Mana" + i, mGR.Mana[i]);
		for (int i = 0; i < mGR.Tower.length; i++)
			editor.putInt("Tower" + i, mGR.Tower[i]);
		for (int i = 0; i < mGR.uPower.length; i++)
			editor.putInt("uPower" + i, mGR.uPower[i]);

		editor.putInt("mStage", mGR.mStage);
		editor.putInt("mLevel", mGR.mLevel);
		editor.putInt("totalOpp", mGR.totalOpp);
		editor.putInt("RemainOpp", mGR.RemainOpp);
		editor.putInt("selUpgrd", mGR.selUpgrd);
		editor.putInt("selPower", mGR.selPower);
		editor.putInt("Tdamy", mGR.Tdamy);
		editor.putInt("damy", mGR.damy);
		editor.putInt("Wave", mGR.Wave);
		editor.putString("Name", mGR.mPName);

		editor.putFloat("lifeBar", mGR.lifeBar);

		for (int i = 0; i < mGR.mArrow.length; i++) {
			editor.putFloat("Arrowx" + i, mGR.mArrow[i].x);
			editor.putFloat("Arrowy" + i, mGR.mArrow[i].y);
			editor.putFloat("Arrowvx" + i, mGR.mArrow[i].vx);
			editor.putFloat("Arrowvy" + i, mGR.mArrow[i].vy);
			editor.putFloat("Arrowra" + i, mGR.mArrow[i].radian);
		}

		for (int i = 0; i < mGR.mOpp.length; i++) {
			editor.putInt("Oppimg" + i, mGR.mOpp[i].img);
			editor.putInt("Oppmode" + i, mGR.mOpp[i].mode);
			editor.putInt("OppCter" + i, mGR.mOpp[i].Cter);
			editor.putInt("Opppos" + i, mGR.mOpp[i].pos);
			editor.putInt("Oppbourn" + i, mGR.mOpp[i].bourn);
			editor.putInt("OppFreez" + i, mGR.mOpp[i].Freez);
			editor.putInt("OppSparkal" + i, mGR.mOpp[i].Sparkal);
			editor.putInt("OppreduceSpeed" + i, mGR.mOpp[i].reduceSpeed);

			editor.putFloat("Oppx" + i, mGR.mOpp[i].x);
			editor.putFloat("Oppy" + i, mGR.mOpp[i].y);
			editor.putFloat("Oppvx" + i, mGR.mOpp[i].vx);
			editor.putFloat("Oppvy" + i, mGR.mOpp[i].vy);
			editor.putFloat("Oppli" + i, mGR.mOpp[i].Life);
			editor.putFloat("Oppto" + i, mGR.mOpp[i].toxic);
		}

		editor.putFloat("Bowx", mGR.mBow.x);
		editor.putFloat("Bowy", mGR.mBow.y);
		editor.putFloat("Bowra", (float) mGR.mBow.radian);

		for (int i = 0; i < mGR.mFire.length; i++) {
			editor.putInt("Fireimg" + i, mGR.mFire[i].img);
			editor.putInt("Fireblast" + i, mGR.mFire[i].blast);

			editor.putFloat("Firex" + i, mGR.mFire[i].x);
			editor.putFloat("Firey" + i, mGR.mFire[i].y);
			editor.putFloat("Firevx" + i, mGR.mFire[i].vx);
			editor.putFloat("Firevy" + i, mGR.mFire[i].vy);
		}

		for (int i = 0; i < mGR.mPowerFire.length; i++) {
			editor.putInt("PowerFirecount" + i, mGR.mPowerFire[i].count);
			editor.putFloat("PowerFirex" + i, mGR.mPowerFire[i].x);
			editor.putFloat("PowerFirey" + i, mGR.mPowerFire[i].y);
		}

		{
			editor.putInt("Freezcount", mGR.mFreez.count);
			editor.putFloat("Freezx", mGR.mFreez.x);
			editor.putFloat("Freezy", mGR.mFreez.y);
		}

		{
			editor.putInt("Sparkalcount", mGR.mSparkal.count);
			editor.putFloat("Sparkalx", mGR.mSparkal.x);
			editor.putFloat("Sparkaly", mGR.mSparkal.y);
		}

		{
			editor.putInt("Coverpower", mGR.mCover.power);
			editor.putFloat("Coverx", mGR.mCover.x);
			editor.putFloat("Covery", mGR.mCover.y);
		}

		{
			editor.putInt("PopUppop", mGR.mPopUp.pop);
			editor.putFloat("PopUpy", mGR.mPopUp.y);
		}

		{
			for (int i = 0; i < mGR.mPlayer.PowerResume.length; i++)
				editor.putInt("playerPowerResume" + i,
						mGR.mPlayer.PowerResume[i]);
			editor.putInt("playerTCo", mGR.mPlayer.TCo);
			editor.putInt("playerTCy", mGR.mPlayer.TCy);
			editor.putInt("playerT$", mGR.mPlayer.T$);
			editor.putInt("playerTCrystal", mGR.mPlayer.TCrystal);
			editor.putInt("playerTKey", mGR.mPlayer.TKey);
			editor.putInt("playerTKill", mGR.mPlayer.TKill);
			editor.putInt("playerSelBow", mGR.mPlayer.SelBow);
			editor.putInt("playerbgNo", mGR.mPlayer.bgNo);
			editor.putInt("playerkill", mGR.mPlayer.kill);
			editor.putInt("playerScore", mGR.mPlayer.Score);
			editor.putInt("playerHScore", mGR.mPlayer.HScore);
			editor.putInt("playerCrystal", mGR.mPlayer.Crystal);
			editor.putInt("playerCoints", mGR.mPlayer.Coints);
			editor.putInt("playerDamage", mGR.mPlayer.Damage);
			editor.putInt("playerfirepower", mGR.mPlayer.firepower);
			editor.putInt("playerfireSpeedRate", mGR.mPlayer.fireSpeedRate);
			editor.putInt("playerFreezpower", mGR.mPlayer.Freezpower);
			editor.putInt("playerFreezSpeedRate", mGR.mPlayer.FreezSpeedRate);
			editor.putInt("playerSparkalpower", mGR.mPlayer.Sparkalpower);
			editor.putInt("playerSparkalSpeedRate",
					mGR.mPlayer.SparkalSpeedRate);
			editor.putInt("playertLife", mGR.mPlayer.tLife);
			editor.putInt("playerMana", mGR.mPlayer.Mana);
			editor.putInt("playertMana", mGR.mPlayer.tMana);
			editor.putInt("playerfireArrowRateTower",
					mGR.mPlayer.fireArrowRateTower);
			editor.putInt("playerfireArrowRate", mGR.mPlayer.fireArrowRate);
			editor.putInt("playerFatalshoot", mGR.mPlayer.Fatalshoot);
			editor.putInt("playermulArrow", mGR.mPlayer.mulArrow);
			editor.putInt("playerkey", mGR.mPlayer.key);

			editor.putFloat("playerfireArea", mGR.mPlayer.fireArea);
			editor.putFloat("playerFreezArea", mGR.mPlayer.FreezArea);
			editor.putFloat("playerSparkalArea", mGR.mPlayer.SparkalArea);
			editor.putFloat("playerbornby", mGR.mPlayer.bornby);
			editor.putFloat("playerSparkalby", mGR.mPlayer.Sparkalby);
			editor.putFloat("playerLife", mGR.mPlayer.Life);
			editor.putFloat("playerLifeDefence", mGR.mPlayer.LifeDefence);
			editor.putFloat("playerpowerShoot", mGR.mPlayer.powerShoot);
			editor.putFloat("playerFatalDamage", mGR.mPlayer.FatalDamage);
			editor.putFloat("playertoxic", mGR.mPlayer.toxic);
		}

		editor.commit();
	}
	boolean resume = false;
	void resume() {
		
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = prefs.getInt("screen", M.GAMELOGO);
		M.setValue = prefs.getBoolean("setValue", M.setValue);
		mGR.addFree = prefs.getBoolean("addFree", mGR.addFree);

		mGR.SingUpadate = prefs.getBoolean("SingUpadate", mGR.SingUpadate);

		for (int i = 0; i < mGR.Achi.length; i++)
			mGR.Achi[i] = prefs.getBoolean("Achi" + i, false);
		for (int i = 0; i < mGR.BowPower.length; i++)
			mGR.BowPower[i] = prefs.getBoolean("BowPower" + i, false);

		for (int i = 0; i < mGR.OppCount.length; i++)
			mGR.OppCount[i] = prefs.getInt("OppCount" + i, 0);
		for (int i = 0; i < mGR.Mana.length; i++)
		{
			if(i < 2)
				mGR.Mana[i] = prefs.getInt("Mana" + i, 1);
			else
				mGR.Mana[i] = prefs.getInt("Mana" + i, 0);
		}
		for (int i = 0; i < mGR.Tower.length; i++)
			mGR.Tower[i] = prefs.getInt("Tower" + i, 0);
		for (int i = 0; i < mGR.uPower.length; i++)
			mGR.uPower[i] = prefs.getInt("uPower" + i, 0);

		mGR.mStage = prefs.getInt("mStage", 1);
		mGR.mLevel = prefs.getInt("mLevel", 1);
		mGR.totalOpp = prefs.getInt("totalOpp", 0);
		mGR.RemainOpp = prefs.getInt("RemainOpp", 0);
		mGR.selUpgrd = prefs.getInt("selUpgrd", 0);
		mGR.selPower = prefs.getInt("selPower", 0);
		mGR.Tdamy = prefs.getInt("Tdamy", 0);
		mGR.damy = prefs.getInt("damy", 0);
		mGR.Wave = prefs.getInt("Wave", mGR.Wave);
		mGR.mPName = prefs.getString("Name", mGR.mPName);
		mGR.lifeBar = prefs.getFloat("lifeBar", .85f);

		for (int i = 0; i < mGR.mArrow.length; i++) {
			mGR.mArrow[i].x = prefs.getFloat("Arrowx" + i, -10);
			mGR.mArrow[i].y = prefs.getFloat("Arrowy" + i, -10);
			mGR.mArrow[i].vx = prefs.getFloat("Arrowvx" + i, 0);
			mGR.mArrow[i].vy = prefs.getFloat("Arrowvy" + i, 0);
			mGR.mArrow[i].radian = prefs.getFloat("Arrowra" + i, 0);
		}

		for (int i = 0; i < mGR.mOpp.length; i++) {
			mGR.mOpp[i].img = prefs.getInt("Oppimg" + i, 0);
			mGR.mOpp[i].mode = prefs.getInt("Oppmode" + i, -1);
			mGR.mOpp[i].Cter = prefs.getInt("OppCter" + i, 0);
			mGR.mOpp[i].pos = prefs.getInt("Opppos" + i, 0);
			mGR.mOpp[i].bourn = prefs.getInt("Oppbourn" + i, 0);
			mGR.mOpp[i].Freez = prefs.getInt("OppFreez" + i, 0);
			mGR.mOpp[i].Sparkal = prefs.getInt("OppSparkal" + i, 0);
			mGR.mOpp[i].reduceSpeed = prefs.getInt("OppreduceSpeed" + i, 0);

			mGR.mOpp[i].x = prefs.getFloat("Oppx" + i, mGR.mOpp[i].x);
			mGR.mOpp[i].y = prefs.getFloat("Oppy" + i, mGR.mOpp[i].y);
			mGR.mOpp[i].vx = prefs.getFloat("Oppvx" + i, mGR.mOpp[i].vx);
			mGR.mOpp[i].vy = prefs.getFloat("Oppvy" + i, mGR.mOpp[i].vy);
			mGR.mOpp[i].Life = prefs.getFloat("Oppli" + i, mGR.mOpp[i].Life);
			mGR.mOpp[i].toxic = prefs.getFloat("Oppto" + i, mGR.mOpp[i].toxic);
		}

		mGR.mBow.x = prefs.getFloat("Bowx", -.98f);
		mGR.mBow.y = prefs.getFloat("Bowy", 0);
		mGR.mBow.radian = prefs.getFloat("Bowra", 0);

		for (int i = 0; i < mGR.mFire.length; i++) {
			mGR.mFire[i].img = prefs.getInt("Fireimg" + i, 0);
			mGR.mFire[i].blast = prefs.getInt("Fireblast" + i, -1);

			mGR.mFire[i].x = prefs.getFloat("Firex" + i, -10);
			mGR.mFire[i].y = prefs.getFloat("Firey" + i, -10);
			mGR.mFire[i].vx = prefs.getFloat("Firevx" + i, 0);
			mGR.mFire[i].vy = prefs.getFloat("Firevy" + i, 0);
		}

		for (int i = 0; i < mGR.mPowerFire.length; i++) {
			mGR.mPowerFire[i].count = prefs.getInt("PowerFirecount" + i, 10000);
			mGR.mPowerFire[i].x = prefs.getFloat("PowerFirex" + i, -10);
			mGR.mPowerFire[i].y = prefs.getFloat("PowerFirey" + i, -10);
		}

		{
			mGR.mFreez.count = prefs.getInt("Freezcount", 10000);
			mGR.mFreez.x = prefs.getFloat("Freezx", -10);
			mGR.mFreez.y = prefs.getFloat("Freezy", 10);
		}

		{
			mGR.mSparkal.count = prefs.getInt("Sparkalcount", 10000);
			mGR.mSparkal.x = prefs.getFloat("Sparkalx", -10);
			mGR.mSparkal.y = prefs.getFloat("Sparkaly", -10);
		}

		{
			mGR.mCover.power = prefs.getInt("Coverpower", 0);
			mGR.mCover.x = prefs.getFloat("Coverx", 0);
			mGR.mCover.y = prefs.getFloat("Covery", 0);
		}

		{
			mGR.mPopUp.pop = prefs.getInt("PopUppop", 0);
			mGR.mPopUp.y = prefs.getFloat("PopUpy", 1.3f);
		}

		{
			for (int i = 0; i < mGR.mPlayer.PowerResume.length; i++)
				mGR.mPlayer.PowerResume[i] = prefs.getInt("playerPowerResume"
						+ i, 100);
			mGR.mPlayer.TCo = prefs.getInt("playerTCo", 0);
			mGR.mPlayer.TCy = prefs.getInt("playerTCy", 0);
			mGR.mPlayer.T$ = prefs.getInt("playerT$", 100);
			mGR.mPlayer.TCrystal = prefs.getInt("playerTCrystal", 5);
			mGR.mPlayer.TKey = prefs.getInt("playerTKey", 1);
			mGR.mPlayer.TKill = prefs.getInt("playerTKill", 0);
			mGR.mPlayer.SelBow = prefs.getInt("playerSelBow", 100);
			mGR.mPlayer.bgNo = prefs.getInt("playerbgNo", 0);
			mGR.mPlayer.kill = prefs.getInt("playerkill", 0);
			mGR.mPlayer.Score = prefs.getInt("playerScore", 0);
			mGR.mPlayer.HScore = prefs.getInt("playerHScore", 0);
			mGR.mPlayer.Crystal = prefs.getInt("playerCrystal", 0);
			mGR.mPlayer.Coints = prefs.getInt("playerCoints", 0);
			mGR.mPlayer.Damage = prefs.getInt("playerDamage", 1);
			mGR.mPlayer.firepower = prefs.getInt("playerfirepower", 10);
			mGR.mPlayer.fireSpeedRate = prefs.getInt("playerfireSpeedRate", 0);
			mGR.mPlayer.Freezpower = prefs.getInt("playerFreezpower", 100);
			mGR.mPlayer.FreezSpeedRate = prefs
					.getInt("playerFreezSpeedRate", 0);
			mGR.mPlayer.Sparkalpower = prefs.getInt("playerSparkalpower", 100);
			mGR.mPlayer.SparkalSpeedRate = prefs.getInt(
					"playerSparkalSpeedRate", 0);
			mGR.mPlayer.tLife = prefs.getInt("playertLife", 100);
			mGR.mPlayer.Mana = prefs.getInt("playerMana", 100);
			mGR.mPlayer.tMana = prefs.getInt("playertMana", 100);
			mGR.mPlayer.fireArrowRateTower = prefs.getInt(
					"playerfireArrowRateTower", 30);
			mGR.mPlayer.fireArrowRate = prefs.getInt("playerfireArrowRate", 30);
			mGR.mPlayer.Fatalshoot = prefs.getInt("playerFatalshoot", 31);
			mGR.mPlayer.mulArrow = prefs.getInt("playermulArrow", 1);
			mGR.mPlayer.key = prefs.getInt("playerkey", 0);

			mGR.mPlayer.fireArea = prefs.getFloat("playerfireArea", .2f);
			mGR.mPlayer.FreezArea = prefs.getFloat("playerFreezArea", .2f);
			mGR.mPlayer.SparkalArea = prefs.getFloat("playerSparkalArea", .2f);
			mGR.mPlayer.bornby = prefs.getFloat("playerbornby", 5);
			mGR.mPlayer.Sparkalby = prefs.getFloat("playerSparkalby", 5);
			mGR.mPlayer.Life = prefs.getFloat("playerLife", 100);
			mGR.mPlayer.LifeDefence = prefs.getFloat("playerLifeDefence", 1);
			mGR.mPlayer.powerShoot = prefs.getFloat("playerpowerShoot", 0);
			mGR.mPlayer.FatalDamage = prefs.getFloat("playerFatalDamage", 1);
			mGR.mPlayer.toxic = prefs.getFloat("playertoxic", 0);
		}
		if(M.GameScreen != M.GAMELOGO && !((M.GameScreen == M.GAMEOVER||M.GameScreen == M.GAMEWIN) && mGR.root.Counter <60))
			M.play(GameRenderer.mContext, R.drawable.splash_other);
		
		
	}
	void notEnof(){
//		M.GameScreen = M.GAMEBUY;

		new AlertDialog.Builder(this)
				.setIcon(R.drawable.icon)
				.setTitle("Don't have sufficient coin?")
				.setPositiveButton("Ok",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog,int which) {
				}}).show();
	
	}
	void Exit() {
		new AlertDialog.Builder(this)
				.setIcon(R.drawable.icon)
				.setTitle("Do you want to Exit?")
				.setPositiveButton("More",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog,int which) {
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setData(Uri.parse(M.MARKET));
					startActivity(intent);
				}}).setNeutralButton("No", new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {}
				}).setNegativeButton("Yes",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog,int which) {
					finish();M.GameScreen = M.GAMELOGO;mGR.root.Counter = 0;}
				}).show();
	}

	public void load() {
		try {
			if(!mGR.addFree)
				handlerload.sendEmptyMessage(0);
		} catch (Exception e) {
		}
	}

	Handler handlerload = new Handler() {public void handleMessage(Message msg) {loadInter();}};

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

	Handler handler = new Handler() { public void handleMessage(Message msg) {show();}};

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
			for (int i = 0; i < mGR.Achi.length; i++) {
				if (mGR.Achi[i]) {
					UnlockAchievement(M.ACHIV[i]);
				}
			}
			GameRenderer.mStart.Submitscore(R.string.leaderboard_score,
					mGR.mPlayer.HScore);
			mGR.SingUpadate = true;
			mGR.mPName = Games.Players.getCurrentPlayer(getApiClient()).getDisplayName();
			if(mGR.mPName.length() > 35)
				mGR.mPName = mGR.mPName.substring(0, 35);
		}
	}

	public void Submitscore(final int ID, long total) {
		if (!isSignedIn()) {
			// beginUserInitiatedSignIn();
		} else {
			Games.Leaderboards
					.submitScore(getApiClient(), getString(ID), total);
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		/*if (!mGR.mInApp.mHelper.handleActivityResult(requestCode, resultCode,
				data)) {
			super.onActivityResult(requestCode, resultCode, data);
		}*/ 
	}
	void resetGame()
	{
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Are you sure to reset game?")
	    .setPositiveButton("Yes",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
    	   	SharedPreferences settings = getSharedPreferences("X", MODE_PRIVATE);
			settings.edit().clear().commit();
			resume();
      }}).setNegativeButton("No",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
    	  }}).show();
	}
}