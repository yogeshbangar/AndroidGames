package com.hututu.game.poker;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import android.app.Activity;
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
import android.view.Window;
import android.view.WindowManager;

public class Start extends Activity 
{
	int _keyCode = 0;
	AdView adView = null;
	AdView adHouse = null;//AdHouse
	GameRenderer mGR = null;
	private InterstitialAd interstitial;
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
		}{
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
		CONTEXT	=	this;
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);//OnlyOneChange
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
			case M.GAMELOGO:case M.GAMEMENU:
					get();
				break;
				case M.GAMERAISE:
					M.GameScreen = M.GAMEPLAY;
					break;
				case M.GAMEPLAY:
					M.playStop();
					M.GameScreen = M.GAMEPAUSE;
					break;
				case M.GAMEOVER:
				case M.GAMECONG:
				case M.GAMEWIN:
					mGR.IsGamePause = false;
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
		if(keyCode==KeyEvent.KEYCODE_BACK)
			return false;
		_keyCode = 0;
		return super.onKeyUp( keyCode, event ); 
	}
	public void onDestroy()
	{
		super.onDestroy();
	}
	void pause()
	{
		int i=0;
//		if(M.GameScreen == M.GAMEPLAY)
//			M.GameScreen = M.GAMEPAUSE;
		
		mGR.resumeCounter = 0;
		M.stop(mGR.mContext);
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
	    Editor editor = prefs.edit();
	    editor.putInt("screen", M.GameScreen);
	    editor.putBoolean("setValue", M.setValue);
	    
	    editor.putBoolean("IsGamePause", mGR.IsGamePause);
	    for(i=0;i<mGR.cardShuffle.length;i++)
	    	editor.putInt("cs"+i, mGR.cardShuffle[i]);
	    
	    editor.putInt("AniCounter", mGR.AniCounter);
	    editor.putInt("ShowCurrent", mGR.ShowCurrent);
	    editor.putInt("cardNo", mGR.cardNo);
	    editor.putInt("mHighScore", mGR.mHighScore);
	    editor.putInt("NewCong", mGR.NewCong);
	    editor.putFloat("mSliderX", mGR.mSliderX);
		
	    
	    for(i=0;i<mGR.mCard.length;i++)
	    {
	    	editor.putInt("CdmState"+i, mGR.mCard[i].mState);
	    	editor.putInt("CdmBig"+i, mGR.mCard[i].mBig);
	    	editor.putInt("CdmBat"+i, mGR.mCard[i].mBat);
	    	editor.putInt("mTotalCoin"+i, mGR.mCard[i].mTotalCoin);
	    	
	    	editor.putFloat("Cdx"+i, mGR.mCard[i].x);
	    	editor.putFloat("Cdy"+i, mGR.mCard[i].y);
	    	editor.putFloat("Cdvx"+i, mGR.mCard[i].vx);
	    	editor.putFloat("Cdvy"+i, mGR.mCard[i].vy);
	    	
	    	editor.putFloat("Cdx1"+i, mGR.mCard[i].x1);
	    	editor.putFloat("Cdy1"+i, mGR.mCard[i].y1);
	    	editor.putFloat("Cdvx1"+i, mGR.mCard[i].vx1);
	    	editor.putFloat("Cdvy1"+i, mGR.mCard[i].vy1);
	    	
	    	
	    }
	    {
	    	for(i=0;i<mGR.mPoker.cardvals.length;i++)
	    		editor.putInt("pkcs"+i, mGR.mPoker.cardvals[i]);
	    	for(i=0;i<mGR.mPoker.correct.length;i++)
	    		editor.putInt("pkct"+i, mGR.mPoker.correct[i]);
	    	editor.putInt("pkst", mGR.mPoker.mState);
	    	
	    }
	    {
	    	editor.putInt("dlbc", mGR.mDealer.mBatChance);
	    	editor.putInt("dlgc", mGR.mDealer.GameCounter);
	    	editor.putInt("dlsc", mGR.mDealer.mShowCard);
	    	editor.putInt("dltb", mGR.mDealer.mTableBat);
	    	editor.putInt("dlcb", mGR.mDealer.mCurrentBat);
	    	editor.putInt("dlrc", mGR.mDealer.mRaiseCount);
	    	editor.putInt("dlbb", mGR.mDealer.mBigBat);
	    	editor.putInt("dlst", mGR.mDealer.mStart);
	    	editor.putInt("dlns", mGR.mDealer.mNewStart);
	    	
	   
	    }
	    
	    
	    
	    
	    editor.commit();
	}
	void resume()
	{
		int i=0;
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = prefs.getInt("screen", M.GAMELOGO);
		M.setValue = prefs.getBoolean("setValue", M.setValue);
		
		mGR.IsGamePause = prefs.getBoolean("IsGamePause", false);
	    for(i=0;i<mGR.cardShuffle.length;i++)
	    	mGR.cardShuffle[i] = (byte)prefs.getInt("cs"+i, mGR.cardShuffle[i]);
	    
	    mGR.AniCounter = prefs.getInt("AniCounter", mGR.AniCounter);
	    mGR.ShowCurrent = prefs.getInt("ShowCurrent", mGR.ShowCurrent);
	    mGR.cardNo = prefs.getInt("cardNo", mGR.cardNo);
	    mGR.mHighScore = prefs.getInt("mHighScore", mGR.mHighScore);
	    mGR.NewCong = prefs.getInt("NewCong", mGR.NewCong);
	    mGR.mSliderX = prefs.getFloat("mSliderX", mGR.mSliderX);
		
	    
	    for(i=0;i<mGR.mCard.length;i++)
	    {
	    	mGR.mCard[i].mState = (byte)prefs.getInt("CdmState"+i, mGR.mCard[i].mState);
	    	mGR.mCard[i].mBig = (byte)prefs.getInt("CdmBig"+i, mGR.mCard[i].mBig);
	    	mGR.mCard[i].mBat = prefs.getInt("CdmBat"+i, mGR.mCard[i].mBat);
	    	mGR.mCard[i].mTotalCoin = prefs.getInt("mTotalCoin"+i, mGR.mCard[i].mTotalCoin);
	    	
	    	mGR.mCard[i].x = prefs.getFloat("Cdx"+i, mGR.mCard[i].x);
	    	mGR.mCard[i].y = prefs.getFloat("Cdy"+i, mGR.mCard[i].y);
	    	mGR.mCard[i].vx= prefs.getFloat("Cdvx"+i, mGR.mCard[i].vx);
	    	mGR.mCard[i].vy= prefs.getFloat("Cdvy"+i, mGR.mCard[i].vy);
	    	
	    	mGR.mCard[i].x1 = prefs.getFloat("Cdx1"+i, mGR.mCard[i].x1);
	    	mGR.mCard[i].y1 = prefs.getFloat("Cdy1"+i, mGR.mCard[i].y1);
	    	mGR.mCard[i].vx1= prefs.getFloat("Cdvx1"+i, mGR.mCard[i].vx1);
	    	mGR.mCard[i].vy1= prefs.getFloat("Cdvy1"+i, mGR.mCard[i].vy1);
	    	
	    	
	    }
	    {
	    	for(i=0;i<mGR.mPoker.cardvals.length;i++)
	    		mGR.mPoker.cardvals[i] = (byte)prefs.getInt("pkcs"+i, mGR.mPoker.cardvals[i]);
	    	for(i=0;i<mGR.mPoker.correct.length;i++)
	    		mGR.mPoker.correct[i] = (byte)prefs.getInt("pkct"+i, mGR.mPoker.correct[i]);
	    	mGR.mPoker.mState = (byte)prefs.getInt("pkst", mGR.mPoker.mState);
	    	
	    }
	    {
	    	mGR.mDealer.mBatChance = prefs.getInt("dlbc", mGR.mDealer.mBatChance);
	    	mGR.mDealer.GameCounter = prefs.getInt("dlgc", mGR.mDealer.GameCounter);
	    	mGR.mDealer.mShowCard = prefs.getInt("dlsc", mGR.mDealer.mShowCard);
	    	mGR.mDealer.mTableBat = prefs.getInt("dltb", mGR.mDealer.mTableBat);
	    	mGR.mDealer.mCurrentBat = prefs.getInt("dlcb", mGR.mDealer.mCurrentBat);
	    	mGR.mDealer.mRaiseCount = prefs.getInt("dlrc", mGR.mDealer.mRaiseCount);
	    	mGR.mDealer.mBigBat = prefs.getInt("dlbb", mGR.mDealer.mBigBat);
	    	mGR.mDealer.mStart = prefs.getInt("dlst", mGR.mDealer.mStart);
	    	mGR.mDealer.mNewStart = prefs.getInt("dlns", mGR.mDealer.mNewStart);
	    	
	   
	    }
		
	    mGR.resumeCounter = 0;
	}
	void get()
	{
		   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Do you want to Exit?")
		    .setPositiveButton("Yes",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
		    	finish();M.GameScreen=M.GAMELOGO;mGR.root.Counter =0;
	      }}).setNegativeButton("No",new DialogInterface.OnClickListener(){public void onClick(DialogInterface dialog, int which) {
    		   
	      }}).show();
	  }
	void loadInter(){
		System.out.println("Load~~~~~~~~~~~~~~~~~~");
		if (!interstitial.isLoaded()) {
			AdRequest adRequest = new AdRequest.Builder().build();
			interstitial.loadAd(adRequest);
			interstitial.setAdListener(new ToastAdListener(this));
		}
	}

	public void show() {
		try{handler.sendEmptyMessage(0);}catch(Exception e){}
	}
	Handler handler = new Handler() {public void handleMessage(Message msg) {show_ads();}};

	void show_ads() {
		try {
			if (interstitial != null)
				if (interstitial.isLoaded()) {
					interstitial.show();
				}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
}