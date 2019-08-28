package com.oneday.games24.highwayextreamracing;
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
	GameRenderer mGR = null;
	private static Context CONTEXT;
	
	private InterstitialAd interstitialAd;

	void callAdds()
	{
		{
			adView = (AdView) this.findViewById(R.id.addgame);
			AdRequest adRequest = new AdRequest.Builder()
			.build();
			adView.loadAd(adRequest);
			adView.setAdListener(new AdListener() {
				public void onAdLoaded() {
					adView.bringToFront();
				}
			});
		}{
			adHouse = (AdView) this.findViewById(R.id.advhouse);
			AdRequest adRequest = new AdRequest.Builder()
			.build();
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
		interstitialAd.setAdUnitId(getString(R.string.Intertitial));
		
		
		WindowManager mWinMgr = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
		M.ScreenWidth =mWinMgr.getDefaultDisplay().getWidth();
		M.ScreenHieght=mWinMgr.getDefaultDisplay().getHeight();
		mGR=new GameRenderer(this);
	    VortexView glSurface=(VortexView) findViewById(R.id.vortexview); // use the xml to set the view
	    glSurface.setRenderer(mGR);
	    glSurface.showRenderer(mGR);
	    if(!mGR.addFree)
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
			if(M.GameScreen == M.GAMEPLAY)
			{
				M.GameScreen = M.GAMEPAUSE;
				M.stop(GameRenderer.mContext);
			}
			else if(M.GameScreen !=M.GAMEMENU)
				mGR.root.Menu();
			else
				Exit();
			return false;
		}
		_keyCode = keyCode;
		return super.onKeyDown(keyCode,event); 
	}  
	public boolean onKeyUp(int keyCode, KeyEvent event) 
	{
		if(keyCode==KeyEvent.KEYCODE_BACK)
			return false;
		_keyCode = 0;
		return super.onKeyUp( keyCode, event ); 
	}
	public void onDestroy() {
		super.onDestroy();
	}
	void pause()
	{
		if(M.GameScreen == M.GAMEPLAY)
			M.GameScreen = M.GAMEPAUSE;
		M.stop(GameRenderer.mContext);
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
	    Editor editor = prefs.edit();
	    editor.putInt("screen", M.GameScreen);
	    editor.putBoolean("setValue", M.setValue);
	    editor.putBoolean("setBG", M.setBG);
	    
	    for(int i=0;i<mGR.car_buy.length;i++)
	    	editor.putBoolean("car_buy"+i, mGR.car_buy[i]);
	    editor.putBoolean("EndLess", mGR.EndLess);
	    editor.putBoolean("addFree", mGR.addFree);
	    editor.putBoolean("SingUpadate", mGR.SingUpadate);
	    for(int i=0;i<mGR.Achiv.length;i++)
	    	editor.putBoolean("Achiv"+i, mGR.Achiv[i]);
	    for(int i=0;i<mGR.mPower.length;i++)
	    	editor.putBoolean("mPower"+i, mGR.mPower[i]);
	    
	    for(int i=0;i<mGR.mLStar.length;i++)
	    	editor.putInt("mLStar"+i, mGR.mLStar[i]);
	    
	    
	    editor.putInt("mGoCount", mGR.mGoCount);
	    editor.putInt("mLevel", mGR.mLevel);
	    editor.putInt("mULevel", mGR.mULevel);
	    editor.putInt("mNoCoin", mGR.mNoCoin);
	    editor.putInt("mTotalCoin", mGR.mTotalCoin);
	    editor.putInt("mTempCoin", mGR.mTempCoin);
	    editor.putInt("mNoDimond", mGR.mNoDimond);
	    editor.putInt("mCarCount", mGR.mCarCount);
	    editor.putInt("mBus", mGR.mBus);
	    editor.putInt("mKill", mGR.mKill);
	    editor.putInt("levelScr", mGR.levelScr);
	    editor.putInt("roadNo", mGR.roadNo);
	    editor.putInt("car_No", mGR.car_No);
	    editor.putInt("car_Sel", mGR.car_Sel);
	    for(int i=0;i<mGR.Total.length;i++)
	    	editor.putInt("Total"+i, mGR.Total[i]);
	    
	    editor.putFloat("mMeter", mGR.mMeter);
	    for(int i=0;i<mGR.mRaodY.length;i++)
	    	editor.putFloat("mRaodY"+i, mGR.mRaodY[i]);
	    for(int i=0;i<mGR.mSRaodY.length;i++)
	    	editor.putFloat("mSRaodY"+i, mGR.mSRaodY[i]);
	    editor.putFloat("gx", mGR.gx);
	    editor.putFloat("finish", mGR.finish);
		
	    for(int i=0;i<mGR.mOpp.length;i++){
	    	editor.putFloat("mOppx"+i, mGR.mOpp[i].x);
	    	editor.putFloat("mOppy"+i, mGR.mOpp[i].y);
	    	editor.putFloat("mOpps"+i, mGR.mOpp[i].spd);
	    	editor.putInt("mOppn"+i, mGR.mOpp[i].no);
	    }
	    {
	    	for(int i=0;i<mGR.mPlayer.mPower.length;i++)
	    		editor.putBoolean("plymPower"+i, mGR.mPlayer.mPower[i]);
	    	
	    	editor.putInt("plydir", mGR.mPlayer.dir);
	    	editor.putInt("plylen", mGR.mPlayer.len);
	    	editor.putInt("plyblast", mGR.mPlayer.blast);
	    	editor.putInt("plylife", mGR.mPlayer.life);
	    	editor.putInt("plycollide", mGR.mPlayer.collide);
	    	
	    	editor.putFloat("plyx", mGR.mPlayer.x);
	    	editor.putFloat("plyy", mGR.mPlayer.y);
	    	editor.putFloat("plyvx", mGR.mPlayer.vx);
	    	
	    }
	    for(int i=0;i<mGR.mCoin.length;i++){
	    	editor.putInt("mCoinc"+i, mGR.mCoin[i].counter);
	    	editor.putFloat("mCoinx"+i, mGR.mCoin[i].x);
	    	editor.putFloat("mCoiny"+i, mGR.mCoin[i].y);
	    }
	    {
	    	editor.putInt("mDimondc", mGR.mDimond.counter);
	    	editor.putFloat("mDimondx", mGR.mDimond.x);
	    	editor.putFloat("mDimondy", mGR.mMagnet.y);
	    }
	    {
	    	editor.putInt("mMagnetc", mGR.mMagnet.counter);
	    	editor.putFloat("mMagnetx", mGR.mMagnet.x);
	    	editor.putFloat("mMagnety", mGR.mMagnet.y);
	    }
	    {
	    	editor.putInt("mCoinAnimc", mGR.mCoinAnim.counter);
	    	editor.putFloat("mCoinAnimx", mGR.mCoinAnim.x);
	    	editor.putFloat("mCoinAnimy", mGR.mCoinAnim.y);
	    }
	    for(int i=0;i<mGR.mAnim.length;i++){
	    	editor.putFloat("mAnimx"+i, mGR.mAnim[i].x);
	    	editor.putFloat("mAnimy"+i, mGR.mAnim[i].y);
	    	editor.putFloat("mAnimvx"+i, mGR.mAnim[i].vx);
	    	editor.putFloat("mAnimvy"+i, mGR.mAnim[i].vy);
	    	editor.putFloat("mAnimtran"+i, mGR.mAnim[i].tran);
	    }
	    {
	    	editor.putInt("mFirei", mGR.mFire.i);
	    	editor.putInt("mFirecon", mGR.mFire.count);
	    	
	    	editor.putFloat("mFirex", mGR.mFire.x);
	    	editor.putFloat("mFirey", mGR.mFire.y);
	    	editor.putFloat("mFirez", mGR.mFire.z);
	    	editor.putFloat("mFirevx", mGR.mFire.vx);
	    	editor.putFloat("mFirevy", mGR.mFire.vy);
	    	editor.putFloat("mFirevz", mGR.mFire.vz);
	    }
	    {
	    	editor.putInt("mSkycon", mGR.mSky.type);
	    	editor.putFloat("mSkyx", mGR.mSky.x);
	    	editor.putFloat("mSkyy", mGR.mSky.y);
	    	for(int i=0;i<mGR.mSky.mCar.length;i++){
	    		editor.putInt("mSkymCarc"+i, mGR.mSky.mCar[i].counter);
		    	editor.putFloat("mSkymCarx"+i, mGR.mSky.mCar[i].x);
		    	editor.putFloat("mSkymCary"+i, mGR.mSky.mCar[i].y);
	    	}
	    }
	    for(int i=0;i<mGR.mRoket.length;i++){
	    	editor.putInt("mRoketimg"+i, mGR.mRoket[i].img);
	    	
	    	editor.putFloat("mRoketx"+i, mGR.mRoket[i].x);
	    	editor.putFloat("mRokety"+i, mGR.mRoket[i].y);
	    	editor.putFloat("mRoketz"+i, mGR.mRoket[i].z);
	    	editor.putFloat("mRoketvy"+i, mGR.mRoket[i].vy);
	    }
	    for(int i=0;i<mGR.mBAnim.length;i++){
	    	editor.putInt("mBAnimimg"+i, mGR.mBAnim[i].img);
	    	editor.putFloat("mBAnimx"+i, mGR.mBAnim[i].x);
	    	editor.putFloat("mBAnimy"+i, mGR.mBAnim[i].y);
	    	editor.putFloat("mBAnimz"+i, mGR.mBAnim[i].z);
	    	editor.putFloat("mBAnimvy"+i, mGR.mBAnim[i].vy);
	    	editor.putFloat("mBAnimvx"+i, mGR.mBAnim[i].vx);
	    }
	    editor.commit();
	}
	void resume()
	{
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = prefs.getInt("screen", M.GAMELOGO);
		M.setValue = prefs.getBoolean("setValue", M.setValue);
		M.setBG = prefs.getBoolean("setBG", M.setBG);
	    

		
		for(int i=0;i<mGR.car_buy.length;i++)
			mGR.car_buy[i] = prefs.getBoolean("car_buy"+i, mGR.car_buy[i]);
		mGR.EndLess = prefs.getBoolean("EndLess", mGR.EndLess);
		mGR.addFree = prefs.getBoolean("addFree", mGR.addFree);
		mGR.SingUpadate = prefs.getBoolean("SingUpadate", mGR.SingUpadate);
	    for(int i=0;i<mGR.Achiv.length;i++)
	    	mGR.Achiv[i] = prefs.getBoolean("Achiv"+i, mGR.Achiv[i]);
	    for(int i=0;i<mGR.mPower.length;i++)
	    	mGR.mPower[i] = prefs.getBoolean("mPower"+i, mGR.mPower[i]);
	    
	    for(int i=0;i<mGR.mLStar.length;i++)
	    	mGR.mLStar[i] = (byte)prefs.getInt("mLStar"+i, mGR.mLStar[i]);
	    
	    
	    mGR.mGoCount = prefs.getInt("mGoCount", mGR.mGoCount);
	    mGR.mLevel= prefs.getInt("mLevel", mGR.mLevel);
	    mGR.mULevel = prefs.getInt("mULevel", mGR.mULevel);
	    mGR.mNoCoin = prefs.getInt("mNoCoin", mGR.mNoCoin);
	    mGR.mTotalCoin = prefs.getInt("mTotalCoin", mGR.mTotalCoin);
	    mGR.mTempCoin = prefs.getInt("mTempCoin", mGR.mTempCoin);
	    mGR.mNoDimond = prefs.getInt("mNoDimond", mGR.mNoDimond);
	    mGR.mCarCount = prefs.getInt("mCarCount", mGR.mCarCount);
	    mGR.mBus = prefs.getInt("mBus", mGR.mBus);
	    mGR.mKill = prefs.getInt("mKill", mGR.mKill);
	    mGR.levelScr = prefs.getInt("levelScr", mGR.levelScr);
	    mGR.roadNo = prefs.getInt("roadNo", mGR.roadNo);
	    mGR.car_No = prefs.getInt("car_No", mGR.car_No);
	    mGR.car_Sel = prefs.getInt("car_Sel", mGR.car_Sel);
	    for(int i=0;i<mGR.Total.length;i++)
	    	mGR.Total[i] = prefs.getInt("Total"+i, mGR.Total[i]);
	    
	    mGR.mMeter = prefs.getFloat("mMeter", mGR.mMeter);
	    for(int i=0;i<mGR.mRaodY.length;i++)
	    	mGR.mRaodY[i] = prefs.getFloat("mRaodY"+i, mGR.mRaodY[i]);
	    for(int i=0;i<mGR.mSRaodY.length;i++)
	    	mGR.mSRaodY[i] = prefs.getFloat("mSRaodY"+i, mGR.mSRaodY[i]);
	    mGR.gx = prefs.getFloat("gx", mGR.gx);
	    mGR.finish = prefs.getFloat("finish", mGR.finish);
		
	    for(int i=0;i<mGR.mOpp.length;i++){
	    	mGR.mOpp[i].x = prefs.getFloat("mOppx"+i, mGR.mOpp[i].x);
	    	mGR.mOpp[i].y = prefs.getFloat("mOppy"+i, mGR.mOpp[i].y);
	    	mGR.mOpp[i].spd = prefs.getFloat("mOpps"+i, mGR.mOpp[i].spd);
	    	mGR.mOpp[i].no = prefs.getInt("mOppn"+i, mGR.mOpp[i].no);
	    }
	    {
	    	for(int i=0;i<mGR.mPlayer.mPower.length;i++)
	    		mGR.mPlayer.mPower[i] = prefs.getBoolean("plymPower"+i, mGR.mPlayer.mPower[i]);
	    	
	    	mGR.mPlayer.dir = prefs.getInt("plydir", mGR.mPlayer.dir);
	    	mGR.mPlayer.len = prefs.getInt("plylen", mGR.mPlayer.len);
	    	mGR.mPlayer.blast = prefs.getInt("plyblast", mGR.mPlayer.blast);
	    	mGR.mPlayer.life = prefs.getInt("plylife", mGR.mPlayer.life);
	    	mGR.mPlayer. collide = prefs.getInt("plycollide", mGR.mPlayer.collide);
	    	
	    	mGR.mPlayer.x = prefs.getFloat("plyx", mGR.mPlayer.x);
	    	mGR.mPlayer.y = prefs.getFloat("plyy", mGR.mPlayer.y);
	    	mGR.mPlayer.vx = prefs.getFloat("plyvx", mGR.mPlayer.vx);
	    	
	    }
	    for(int i=0;i<mGR.mCoin.length;i++){
	    	mGR.mCoin[i].counter = prefs.getInt("mCoinc"+i, mGR.mCoin[i].counter);
	    	mGR.mCoin[i].x = prefs.getFloat("mCoinx"+i, mGR.mCoin[i].x);
	    	mGR.mCoin[i].y = prefs.getFloat("mCoiny"+i, mGR.mCoin[i].y);
	    }
	    {
	    	mGR.mDimond.counter = prefs.getInt("mDimondc", mGR.mDimond.counter);
	    	mGR.mDimond.x = prefs.getFloat("mDimondx", mGR.mDimond.x);
	    	mGR.mDimond.y = prefs.getFloat("mDimondy", mGR.mMagnet.y);
	    }
	    {
	    	mGR.mMagnet.counter = prefs.getInt("mMagnetc", mGR.mMagnet.counter);
	    	mGR.mMagnet.x = prefs.getFloat("mMagnetx", mGR.mMagnet.x);
	    	mGR.mMagnet.y = prefs.getFloat("mMagnety", mGR.mMagnet.y);
	    }
	    {
	    	mGR.mCoinAnim.counter = prefs.getInt("mCoinAnimc", mGR.mCoinAnim.counter);
	    	mGR.mCoinAnim.x = prefs.getFloat("mCoinAnimx", mGR.mCoinAnim.x);
	    	mGR.mCoinAnim.y = prefs.getFloat("mCoinAnimy", mGR.mCoinAnim.y);
	    }
	    for(int i=0;i<mGR.mAnim.length;i++){
	    	mGR.mAnim[i].x = prefs.getFloat("mAnimx"+i, mGR.mAnim[i].x);
	    	mGR.mAnim[i].y = prefs.getFloat("mAnimy"+i, mGR.mAnim[i].y);
	    	mGR.mAnim[i].vx = prefs.getFloat("mAnimvx"+i, mGR.mAnim[i].vx);
	    	mGR.mAnim[i].vy = prefs.getFloat("mAnimvy"+i, mGR.mAnim[i].vy);
	    	mGR.mAnim[i].tran = prefs.getFloat("mAnimtran"+i, mGR.mAnim[i].tran);
	    }
	    {
	    	mGR.mFire.i = prefs.getInt("mFirei", mGR.mFire.i);
	    	mGR.mFire.count = prefs.getInt("mFirecon", mGR.mFire.count);
	    	
	    	mGR.mFire.x = prefs.getFloat("mFirex", mGR.mFire.x);
	    	mGR.mFire.y = prefs.getFloat("mFirey", mGR.mFire.y);
	    	mGR.mFire.z = prefs.getFloat("mFirez", mGR.mFire.z);
	    	mGR.mFire.vx = prefs.getFloat("mFirevx", mGR.mFire.vx);
	    	mGR.mFire.vy = prefs.getFloat("mFirevy", mGR.mFire.vy);
	    	mGR.mFire.vz = prefs.getFloat("mFirevz", mGR.mFire.vz);
	    }
	    {
	    	mGR.mSky.type = prefs.getInt("mSkycon", mGR.mSky.type);
	    	mGR.mSky.x = prefs.getFloat("mSkyx", mGR.mSky.x);
	    	mGR.mSky.y = prefs.getFloat("mSkyy", mGR.mSky.y);
	    	for(int i=0;i<mGR.mSky.mCar.length;i++){
	    		mGR.mSky.mCar[i].counter = prefs.getInt("mSkymCarc"+i, mGR.mSky.mCar[i].counter);
	    		mGR.mSky.mCar[i].x = prefs.getFloat("mSkymCarx"+i, mGR.mSky.mCar[i].x);
	    		mGR.mSky.mCar[i].y = prefs.getFloat("mSkymCary"+i, mGR.mSky.mCar[i].y);
	    	}
	    }
	    for(int i=0;i<mGR.mRoket.length;i++){
	    	mGR.mRoket[i].img = prefs.getInt("mRoketimg"+i, mGR.mRoket[i].img);
	    	
	    	mGR.mRoket[i].x = prefs.getFloat("mRoketx"+i, mGR.mRoket[i].x);
	    	mGR.mRoket[i].y = prefs.getFloat("mRokety"+i, mGR.mRoket[i].y);
	    	mGR.mRoket[i].z = prefs.getFloat("mRoketz"+i, mGR.mRoket[i].z);
	    	mGR.mRoket[i].vy = prefs.getFloat("mRoketvy"+i, mGR.mRoket[i].vy);
	    }
	    for(int i=0;i<mGR.mBAnim.length;i++){
	    	mGR.mBAnim[i].img = prefs.getInt("mBAnimimg"+i, mGR.mBAnim[i].img);
	    	mGR.mBAnim[i].x = prefs.getFloat("mBAnimx"+i, mGR.mBAnim[i].x);
	    	mGR.mBAnim[i].y = prefs.getFloat("mBAnimy"+i, mGR.mBAnim[i].y);
	    	mGR.mBAnim[i].z = prefs.getFloat("mBAnimz"+i, mGR.mBAnim[i].z);
	    	mGR.mBAnim[i].vy = prefs.getFloat("mBAnimvy"+i, mGR.mBAnim[i].vy);
	    	mGR.mBAnim[i].vx = prefs.getFloat("mBAnimvx"+i, mGR.mBAnim[i].vx);
	    }
	    if(M.GameScreen == M.GAMEMENU)
	    	M.play(GameRenderer.mContext, R.drawable.splash_theme);
	}
	void Exit()
	{
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Do you want to Exit?")
	    .setPositiveButton("More",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
    	   Intent intent = new Intent(Intent.ACTION_VIEW);
    	   intent.setData(Uri.parse(M.MARKET));
    	   startActivity(intent);
      }}).setNeutralButton("No",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
      }}).setNegativeButton("Yes",new DialogInterface.OnClickListener(){public void onClick(DialogInterface dialog, int which) {
		    		   finish();M.GameScreen=M.GAMELOGO;mGR.root.Counter =0;
      }}).show();
  }
	
	void buy()
	{
		   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("You don't have enough coins.")
		    /*.setPositiveButton("Buy",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
		    	mGR.mMainActivity.onBuyGold50000(null);
	      }})*/.setNegativeButton("Ok",new DialogInterface.OnClickListener(){public void onClick(DialogInterface dialog, int which) {
	      }}).show();
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
			for(int i=0;i<mGR.Achiv.length;i++)
			{
				if(mGR.Achiv[i])
				{
					UnlockAchievement(mGR.root.ID[i]);
				}
			}
			Submitscore(R.string.leaderboard_score);
			mGR.SingUpadate = true;
		}
	}
	public void Submitscore(final int ID) {
		if (!isSignedIn()) {
//			beginUserInitiatedSignIn();
		} else { Games.Leaderboards.submitScore(getApiClient(), getString(ID), mGR.Total[0]);}
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
	public void loadInter() {
		try{handlerInter.sendEmptyMessage(0);}catch(Exception e){}
	}
	Handler handlerInter = new Handler() {public void handleMessage(Message msg) {load();}};
	private void load(){
		if (!interstitialAd.isLoaded()) {
			AdRequest adRequest = new AdRequest.Builder().build();
			interstitialAd.loadAd(adRequest);
			interstitialAd.setAdListener(new AdGameListener(this));
		}
	}
//	void load(){
//		if (!interstitialAd.isLoaded() && !mGR.addFree) {
//			AdRequest adRequest = new AdRequest.Builder()
////					.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
////					.addTestDevice("67701763FB847AEBD3C6EE486475ED94")
//			.build();
//			interstitialAd.loadAd(adRequest);
//			interstitialAd.setAdListener(new AdGameListener(this));
//		}
//	}

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

       /* if (!mGR.mMainActivity.mHelper.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }*/
    }
	
}