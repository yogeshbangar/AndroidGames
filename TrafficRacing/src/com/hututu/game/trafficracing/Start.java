package com.hututu.game.trafficracing;


import com.google.android.gms.ads.AdSize;
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
	AdView adHouse = null;
	boolean addFree = false;
	GameRenderer mGR = null;
	private static Context CONTEXT;
	private InterstitialAd interstitialAd;
	void callAdds()
	{
		if(adView==null){
			adView = (AdView) this.findViewById(R.id.addgame);
			AdRequest adRequest = new AdRequest.Builder()
//			.addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("67701763FB847AEBD3C6EE486475ED94")
			.build();
			adView.loadAd(adRequest);
			adView.setAdListener(new AdListener() {
				public void onAdLoaded() {
					adView.bringToFront();
				}
			});
		}
		
		if(adHouse == null){
		    adHouse = (AdView) this.findViewById(R.id.advhouse);
		    AdRequest adRequest = new AdRequest.Builder()
//			.addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("67701763FB847AEBD3C6EE486475ED94")
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
		interstitialAd.setAdUnitId(getString(R.string.INTERSTITIAL_ID));
		WindowManager mWinMgr = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
		M.ScreenWidth =mWinMgr.getDefaultDisplay().getWidth();
		M.ScreenHieght=mWinMgr.getDefaultDisplay().getHeight();
		mGR=new GameRenderer(this);
	    VortexView glSurface=(VortexView) findViewById(R.id.vortexview); // use the xml to set the view
	    glSurface.setRenderer(mGR);
	    glSurface.showRenderer(mGR);
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
		if(!addFree)
	    	callAdds();
		//view.onResume();
	}
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		Log.d("----------------=>  "+keyCode,"   -----------    ");
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			switch (M.GameScreen) {
			case M.GAMEPLAY:
				M.GameScreen=M.GAMEPAUSE;
				mGR.root.sound();
				break;
			case M.GAMECONG:
				gReset();
				break;
			case M.GAMEMENU:
				Exit();
				break;
			default:
				M.GameScreen=M.GAMEMENU;
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

	public void onDestroy() {
		super.onDestroy();
		if(mGR.mMainActivity!=null)
			mGR.mMainActivity.onDestroy();
	}

	void pause()
	{
		if(M.GameScreen == M.GAMEPLAY)
			M.GameScreen =M.GAMEPAUSE;
		mGR.resumeCounter = 0;
		M.stop(GameRenderer.mContext);
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
	    Editor editor = prefs.edit();
	    editor.putInt("screen", M.GameScreen);
	    editor.putBoolean("setValue", M.setValue);
	    editor.putFloat("speed", M.SPEED);
	    editor.putBoolean("addFree", addFree);
	    int i =0;
	    for(i=0;i<mGR.mBG.length;i++){
	    	editor.putFloat("mbgx"+i, mGR.mBG[i].x);
	    	editor.putFloat("mbgy"+i, mGR.mBG[i].y);
	    	editor.putInt("mbgno"+i, mGR.mBG[i].no);
	    }
	    for(i=0;i<mGR.mOpp.length;i++){
	    	editor.putFloat("mOppx"+i, mGR.mOpp[i].x);
	    	editor.putFloat("mOppy"+i, mGR.mOpp[i].y);
	    	editor.putFloat("mOppr"+i, mGR.mOpp[i].r);
	    	editor.putFloat("mOppg"+i, mGR.mOpp[i].g);
	    	editor.putFloat("mOppb"+i, mGR.mOpp[i].b);
	    	editor.putInt("mOppno"+i, mGR.mOpp[i].no);
	    }
	    {
	    	editor.putFloat("mPlayerx", mGR.mPlayer.x);
	    	editor.putFloat("mPlayery", mGR.mPlayer.y);
	    	editor.putFloat("mPlayerfule", mGR.mPlayer.fule);
	    	editor.putFloat("mPlayerboost", mGR.mPlayer.boost);
	    	editor.putFloat("mPlayerDis", mGR.mPlayer.Dis);
	    	editor.putFloat("mPlayerdisBar", mGR.mPlayer.disBar);
	    	editor.putBoolean("mPlayerisBoost", mGR.mPlayer.isBoost);
	    	editor.putInt("mPlayerd", mGR.mPlayer.d);
	    	editor.putInt("mPlayerstart", mGR.mPlayer.start);
	    	editor.putInt("mPlayerGun", mGR.mPlayer.Gun);
	    	editor.putInt("mPlayerdamege", mGR.mPlayer.damege);
	    	editor.putInt("mPlayercarNo", mGR.mPlayer.carNo);
	    	for(i=0;i<mGR.mPlayer.buy.length;i++)
	    		editor.putInt("mPlayerbuy"+i, mGR.mPlayer.buy[i]);
	    	for(i=0;i<mGR.mPlayer.power.length;i++)
	    		for(int j=0;j<mGR.mPlayer.power[i].length;j++)
	    			editor.putInt(i+"mPlayerpower"+j, mGR.mPlayer.power[i][j]);
	    }
	    for(i=0;i<mGR.mBullet.length;i++){
	    	editor.putFloat("mBulletx"+i, mGR.mBullet[i].x);
	    	editor.putFloat("mBullety"+i, mGR.mBullet[i].y);
	    	editor.putInt("mBulletco"+i, mGR.mBullet[i].co);
	    }
	    for(i=0;i<mGR.mCoin.length;i++){
	    	editor.putFloat("mCoinx"+i, mGR.mCoin[i].x);
	    	editor.putFloat("mCoiny"+i, mGR.mCoin[i].y);
	    	editor.putBoolean("mCoing"+i, mGR.mCoin[i].g);
	    }
	    {
	    	editor.putFloat("mHelix"+i, mGR.mHeli.x);
	    	editor.putFloat("mHeliy"+i, mGR.mHeli.y);
	    	editor.putBoolean("mHelig"+i, mGR.mHeli.g);
	    }
	    for(i=0;i<mGR.mAni.length;i++){
	    	editor.putFloat("mAnix"+i, mGR.mAni[i].x);
	    	editor.putFloat("mAniy"+i, mGR.mAni[i].y);
	    	editor.putFloat("mAniz"+i, mGR.mAni[i].z);
	    	editor.putFloat("mAnit"+i, mGR.mAni[i].t);
	    }
		
		editor.putBoolean("mCarUp", mGR.mCarUp);
		editor.putInt("RCount", mGR.RCount);
		editor.putInt("RArry", mGR.RArry);
		editor.putInt("coin", mGR.coin);
		editor.putInt("mScore", mGR.mScore);
		editor.putInt("mHScore", mGR.mHScore);
		editor.putInt("CoinSet", mGR.CoinSet);
		editor.putInt("oPPset", mGR.oPPset);
		editor.putInt("oPPCnt", mGR.oPPCnt);
		
		editor.putFloat("cy", mGR.cy);
		
		
	    
	    
	    editor.commit();
	}
	void resume()
	{
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = prefs.getInt("screen", M.GAMELOGO);
		M.setValue = prefs.getBoolean("setValue", M.setValue);
		M.SPEED = prefs.getFloat("speed", M.SPEED);
		addFree = prefs.getBoolean("addFree", addFree);
	    
	    int i =0;
	    for(i=0;i<mGR.mBG.length;i++){
	    	mGR.mBG[i].x = prefs.getFloat("mbgx"+i, mGR.mBG[i].x);
	    	mGR.mBG[i].y = prefs.getFloat("mbgy"+i, mGR.mBG[i].y);
	    	mGR.mBG[i].no = prefs.getInt("mbgno"+i, mGR.mBG[i].no);
	    }
	    for(i=0;i<mGR.mOpp.length;i++){
	    	mGR.mOpp[i].x = prefs.getFloat("mOppx"+i, mGR.mOpp[i].x);
	    	mGR.mOpp[i].y = prefs.getFloat("mOppy"+i, mGR.mOpp[i].y);
	    	mGR.mOpp[i].r = prefs.getFloat("mOppr"+i, mGR.mOpp[i].r);
	    	mGR.mOpp[i].g = prefs.getFloat("mOppg"+i, mGR.mOpp[i].g);
	    	mGR.mOpp[i].b= prefs.getFloat("mOppb"+i, mGR.mOpp[i].b);
	    	mGR.mOpp[i].no = prefs.getInt("mOppno"+i, mGR.mOpp[i].no);
	    }
	    {
	    	mGR.mPlayer.x = prefs.getFloat("mPlayerx", mGR.mPlayer.x);
	    	mGR.mPlayer.y = prefs.getFloat("mPlayery", mGR.mPlayer.y);
	    	mGR.mPlayer.fule = prefs.getFloat("mPlayerfule", mGR.mPlayer.fule);
	    	mGR.mPlayer.boost = prefs.getFloat("mPlayerboost", mGR.mPlayer.boost);
	    	mGR.mPlayer.Dis = prefs.getFloat("mPlayerDis", mGR.mPlayer.Dis);
	    	mGR.mPlayer.disBar = prefs.getFloat("mPlayerdisBar", mGR.mPlayer.disBar);
	    	mGR.mPlayer.isBoost = prefs.getBoolean("mPlayerisBoost", mGR.mPlayer.isBoost);
	    	mGR.mPlayer.d = prefs.getInt("mPlayerd", mGR.mPlayer.d);
	    	mGR.mPlayer.start = prefs.getInt("mPlayerstart", mGR.mPlayer.start);
	    	mGR.mPlayer.Gun = prefs.getInt("mPlayerGun", mGR.mPlayer.Gun);
	    	mGR.mPlayer.damege = prefs.getInt("mPlayerdamege", mGR.mPlayer.damege);
	    	mGR.mPlayer.carNo = prefs.getInt("mPlayercarNo", mGR.mPlayer.carNo);
	    	for(i=0;i<mGR.mPlayer.buy.length;i++)
	    		mGR.mPlayer.buy[i] = prefs.getInt("mPlayerbuy"+i, mGR.mPlayer.buy[i]);
	    	for(i=0;i<mGR.mPlayer.power.length;i++)
	    		for(int j=0;j<mGR.mPlayer.power[i].length;j++)
	    			mGR.mPlayer.power[i][j] = prefs.getInt(i+"mPlayerpower"+j, mGR.mPlayer.power[i][j]);
	    }
	    for(i=0;i<mGR.mBullet.length;i++){
	    	mGR.mBullet[i].x = prefs.getFloat("mBulletx"+i, mGR.mBullet[i].x);
	    	mGR.mBullet[i].y = prefs.getFloat("mBullety"+i, mGR.mBullet[i].y);
	    	mGR.mBullet[i].co = prefs.getInt("mBulletco"+i, mGR.mBullet[i].co);
	    }
	    for(i=0;i<mGR.mCoin.length;i++){
	    	mGR.mCoin[i].x = prefs.getFloat("mCoinx"+i, mGR.mCoin[i].x);
	    	mGR.mCoin[i].y = prefs.getFloat("mCoiny"+i, mGR.mCoin[i].y);
	    	mGR.mCoin[i].g = prefs.getBoolean("mCoing"+i, mGR.mCoin[i].g);
	    }
	    {
	    	mGR.mHeli.x = prefs.getFloat("mHelix"+i, mGR.mHeli.x);
	    	mGR.mHeli.y = prefs.getFloat("mHeliy"+i, mGR.mHeli.y);
	    	mGR.mHeli.g = prefs.getBoolean("mHelig"+i, mGR.mHeli.g);
	    }
	    for(i=0;i<mGR.mAni.length;i++){
	    	mGR.mAni[i].x = prefs.getFloat("mAnix"+i, mGR.mAni[i].x);
	    	mGR.mAni[i].y = prefs.getFloat("mAniy"+i, mGR.mAni[i].y);
	    	mGR.mAni[i].z = prefs.getFloat("mAniz"+i, mGR.mAni[i].z);
	    	mGR.mAni[i].t = prefs.getFloat("mAnit"+i, mGR.mAni[i].t);
	    }
		
	    mGR.mCarUp = prefs.getBoolean("mCarUp", mGR.mCarUp);
	    mGR.RCount = prefs.getInt("RCount", mGR.RCount);
	    mGR.RArry = prefs.getInt("RArry", mGR.RArry);
	    mGR.coin = prefs.getInt("coin", mGR.coin);
	    mGR.mScore = prefs.getInt("mScore", mGR.mScore);
	    mGR.mHScore = prefs.getInt("mHScore", mGR.mHScore);
	    mGR.CoinSet = prefs.getInt("CoinSet", mGR.CoinSet);
	    mGR.oPPset = prefs.getInt("oPPset", mGR.oPPset);
	    mGR.oPPCnt = prefs.getInt("oPPCnt", mGR.oPPCnt);
		
	    mGR.cy = prefs.getFloat("cy", mGR.cy);
		
	    mGR.resumeCounter = 0;
	    
	    if(M.GameScreen != M.GAMEADD && M.GameScreen != M.GAMELOGO)
	    {
	    	mGR.root.sound();
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

	void Dont()
	{
		   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("You don't have enough coins.")
		    .setPositiveButton("Buy",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
		    	mGR.mMainActivity.onBuyGold50000(null);
	      }}).setNegativeButton("Later",new DialogInterface.OnClickListener(){public void onClick(DialogInterface dialog, int which) {
	      }}).show();
	  }
	
	void gReset()
	{
		   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Do you want to Reset Game?")
		    .setPositiveButton("Exit",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
		    	finish();
	      }}).setNegativeButton("Yes",new DialogInterface.OnClickListener(){public void onClick(DialogInterface dialog, int which) {
	    	  mGR.Newgame();
	      }}).show();
	  }
	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		
	}

	public void Submitscore(final int ID) {
		if (!isSignedIn()) {
			beginUserInitiatedSignIn();
		} else {Games.Leaderboards.submitScore(getApiClient(), getString(ID),mGR.mHScore);
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

	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~"+ "onActivityResult(" + requestCode + "," + resultCode + "," + data);

        // Pass on the activity result to the helper for handling
        if (!mGR.mMainActivity.mHelper.handleActivityResult(requestCode, resultCode, data)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            super.onActivityResult(requestCode, resultCode, data);
        }
        else {
        	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~"+ "onActivityResult handled by IABUtil.");
        }
    }
	void loadInter(){
		if (!interstitialAd.isLoaded() && !addFree) {
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