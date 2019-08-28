package com.hututu.game.minehero;

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
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class Start   extends BaseGameActivity 
{
	int _keyCode = 0;
	AdView adView = null;
	GameRenderer mGR = null;
	private static Context CONTEXT;
	private InterstitialAd interstitial;
	boolean oncreate = false;
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
	    if(!getSharedPreferences("X", MODE_PRIVATE).getBoolean("addFree", mGR.addFree)){
	    	callAdds();
	    	load();
	    	oncreate = true;
	    }
	}
	public static Context getContext() {
	        return CONTEXT;
	}
	@Override 
	public void onPause () {
		M.stop();
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
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			switch (M.GameScreen) {
			case M.GAMEMENU:
				Exit();
				break;
			case M.GAMEPLAY:
				M.stop();
				M.GameScreen = M.GAMEPAUSE;
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

	void pause() {
		M.stop();
		if (M.GameScreen == M.GAMEPAUSE) {
			M.GameScreen = M.GAMEPLAY;
		}
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putInt("screen", M.GameScreen);
		editor.putBoolean("setValue", M.setValue);
		editor.putBoolean("addFree", mGR.addFree);

		editor.putBoolean("SingUpadate", mGR.SingUpadate);
		for (int i = 0; i < mGR.mAchiUnlock.length; i++)
			editor.putBoolean("mAchiUnlock" + i, mGR.mAchiUnlock[i]);
		editor.putInt("mBPoint", mGR.mBPoint);
		
		editor.putBoolean("imgreset", mGR.imgreset);

		editor.putInt("Tileimg", mGR.Tileimg);
		editor.putInt("mCrystal", mGR.mCrystal);
		editor.putInt("mCoin", mGR.mCoin);
		editor.putInt("mMoney", mGR.mMoney);
		editor.putInt("mPoint", mGR.mPoint);
		editor.putInt("newMoster", mGR.newMoster);

		editor.putFloat("spd", mGR.spd);
		editor.putFloat("dist", mGR.dist);
		editor.putFloat("Score", mGR.Score);

		for (int i = 0; i < mGR.mTileRow.length; i++) {
			editor.putFloat("TileRow" + i, mGR.mTileRow[i].y);
			for (int j = 0; j < mGR.mTileRow[i].tile.length; j++) {
				editor.putInt(j + "RowTNo" + i, mGR.mTileRow[i].tile[j].no);
				editor.putInt(j + "RowTObj" + i, mGR.mTileRow[i].tile[j].obj);
				editor.putInt(j + "RowTReset" + i,
						mGR.mTileRow[i].tile[j].reset);
			}
		}
		{
			editor.putInt("mCharslow", mGR.mChar.slow);
			editor.putInt("mCharmegnat", mGR.mChar.megnat);
			editor.putInt("mCharTimer", mGR.mChar.Timer);
			editor.putInt("mCharDrill", mGR.mChar.Drill);
			editor.putInt("mCharCoinBox", mGR.mChar.CoinBox);
			for (int i = 0; i < mGR.mChar.Power.length; i++)
				editor.putInt("mCharPower" + i, mGR.mChar.Power[i]);

			editor.putFloat("mCharx", mGR.mChar.x);
			editor.putFloat("mChary", mGR.mChar.y);
			editor.putFloat("mCharspd", mGR.mChar.spd);
		}
		{
			editor.putFloat("yChackra", mGR.mChackra.y);
		}
		for (int i = 0; i < mGR.mMonster.length; i++) {
			editor.putBoolean("mMonstertuch" + i, mGR.mMonster[i].tuch);
			editor.putFloat("mMonsterx" + i, mGR.mMonster[i].x);
			editor.putFloat("mMonstery" + i, mGR.mMonster[i].y);
			editor.putFloat("mMonstervx" + i, mGR.mMonster[i].vx);

		}
		for (int i = 0; i < mGR.mSlider.length; i++) {
			editor.putFloat("mSliderx" + i, mGR.mSlider[i].x);
			editor.putFloat("mSlidery" + i, mGR.mSlider[i].y);
			editor.putFloat("mSlidervx" + i, mGR.mSlider[i].vx);

		}
		{
			editor.putInt("mCharBlastimg", mGR.mCharBlast.img);
			editor.putFloat("mCharBlastx", mGR.mCharBlast.x);
			editor.putFloat("mCharBlasty", mGR.mCharBlast.y);

		}

		editor.commit();
	}
	void resume()
	{
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		System.out.println("resume0");
		M.GameScreen = (byte)prefs.getInt("screen", M.GAMELOGO);
		M.setValue = prefs.getBoolean("setValue", M.setValue);
		mGR.addFree = prefs.getBoolean("addFree", mGR.addFree);
		mGR.imgreset = prefs.getBoolean("imgreset", mGR.imgreset);
		mGR.SingUpadate  = prefs.getBoolean("SingUpadate", mGR.SingUpadate);
		for (int i = 0; i < mGR.mAchiUnlock.length; i++)
			mGR.mAchiUnlock[i]  = prefs.getBoolean("mAchiUnlock" + i, mGR.mAchiUnlock[i]);
		mGR.mBPoint  = prefs.getInt("mBPoint", mGR.mBPoint);
		
		mGR.Tileimg = prefs.getInt("Tileimg", mGR.Tileimg);
		mGR.mCrystal = prefs.getInt("mCrystal", mGR.mCrystal);
		mGR.mCoin = prefs.getInt("mCoin", mGR.mCoin);
		mGR.mMoney = prefs.getInt("mMoney", mGR.mMoney);
		mGR.mPoint = prefs.getInt("mPoint", mGR.mPoint);
		mGR.newMoster = prefs.getInt("newMoster", mGR.newMoster);
		mGR.spd = prefs.getFloat("spd", mGR.spd);
		mGR.dist = prefs.getFloat("dist", mGR.dist);
		mGR.Score = prefs.getFloat("Score", mGR.Score);
		
		for (int i = 0; i < mGR.mTileRow.length; i++) {
			mGR.mTileRow[i].y = prefs.getFloat("TileRow" + i, mGR.mTileRow[i].y);
			for (int j = 0; j < mGR.mTileRow[i].tile.length; j++) {
				mGR.mTileRow[i].tile[j].no = (byte)prefs.getInt(j + "RowTNo" + i, mGR.mTileRow[i].tile[j].no);
				mGR.mTileRow[i].tile[j].obj = (byte) prefs.getInt(j + "RowTObj" + i, mGR.mTileRow[i].tile[j].obj);
				mGR.mTileRow[i].tile[j].reset = (byte) prefs.getInt(j + "RowTReset" + i,mGR.mTileRow[i].tile[j].reset);
			}
		}
		{
			mGR.mChar.slow = prefs.getInt("mCharslow", mGR.mChar.slow);
			mGR.mChar.megnat = prefs.getInt("mCharmegnat", mGR.mChar.megnat);
			mGR.mChar.Timer = prefs.getInt("mCharTimer", mGR.mChar.Timer);
			mGR.mChar.Drill = prefs.getInt("mCharDrill", mGR.mChar.Drill);
			mGR.mChar.CoinBox = prefs.getInt("mCharCoinBox", mGR.mChar.CoinBox);
			for (int i = 0; i < mGR.mChar.Power.length; i++)
				mGR.mChar.Power[i]= prefs.getInt("mCharPower" + i, mGR.mChar.Power[i]);

			mGR.mChar.x = prefs.getFloat("mCharx", mGR.mChar.x);
			mGR.mChar.y = prefs.getFloat("mChary", mGR.mChar.y);
			mGR.mChar.spd = prefs.getFloat("mCharspd", mGR.mChar.spd);
		}
		{
			 mGR.mChackra.y = prefs.getFloat("yChackra", mGR.mChackra.y);
		}
		for (int i = 0; i < mGR.mMonster.length; i++) {
			mGR.mMonster[i].tuch = prefs.getBoolean("mMonstertuch" + i, mGR.mMonster[i].tuch);
			mGR.mMonster[i].x = prefs.getFloat("mMonsterx" + i, mGR.mMonster[i].x);
			mGR.mMonster[i].y = prefs.getFloat("mMonstery" + i, mGR.mMonster[i].y);
			mGR.mMonster[i].vx = prefs.getFloat("mMonstervx" + i, mGR.mMonster[i].vx);

		}
		for (int i = 0; i < mGR.mSlider.length; i++) {
			mGR.mSlider[i].x = prefs.getFloat("mSliderx" + i, mGR.mSlider[i].x);
			mGR.mSlider[i].y = prefs.getFloat("mSlidery" + i, mGR.mSlider[i].y);
			mGR.mSlider[i].vx = prefs.getFloat("mSlidervx" + i, mGR.mSlider[i].vx);

		}
		{
			mGR.mCharBlast.img = prefs.getInt("mCharBlastimg", mGR.mCharBlast.img);
			mGR.mCharBlast.x = prefs.getFloat("mCharBlastx", mGR.mCharBlast.x);
			mGR.mCharBlast.y = prefs.getFloat("mCharBlasty", mGR.mCharBlast.y);

		}
		System.out.println("resume1");
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
			Submitscore(R.string.leaderboard_best_score, mGR.mBPoint);
			mGR.SingUpadate = true;
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
	void Achivment(){
		if(mGR.mPoint > 5000 && !mGR.mAchiUnlock[0]){
			mGR.mAchiUnlock[0] = true;
			UnlockAchievement(M.ACHIV[0]);
		}
		if(mGR.mPoint > 7000 && !mGR.mAchiUnlock[1]){
			mGR.mAchiUnlock[1] = true;
			UnlockAchievement(M.ACHIV[1]);
		}
		if(mGR.mPoint > 10000 && !mGR.mAchiUnlock[2]){
			mGR.mAchiUnlock[2] = true;
			UnlockAchievement(M.ACHIV[2]);
		}
		if(mGR.mBPoint > 50000 && !mGR.mAchiUnlock[3]){
			mGR.mAchiUnlock[3] = true;
			UnlockAchievement(M.ACHIV[3]);
		}
		if(mGR.mBPoint > 100000 && !mGR.mAchiUnlock[4]){
			mGR.mAchiUnlock[4] = true;
			UnlockAchievement(M.ACHIV[4]);
		}
		Submitscore(R.string.leaderboard_best_score, mGR.mBPoint);
	}
}