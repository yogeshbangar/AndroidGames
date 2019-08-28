package com.onedaygames24.stickpowerheroes;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.games.Games;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
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

public class Start extends BaseGameActivity 
{
	int _keyCode = 0;
	boolean addFree = false; 
	boolean SingUpadate = false;
	BroadcastReceiver mReceiver;
	GameRenderer mGR = null;
	private static Context CONTEXT;
	private InterstitialAd interstitial;
	AdView adBanner; //,adRect
	boolean isStart=false;
	void callAdds()
	{
			adBanner = (AdView) this.findViewById(R.id.adBanner);
			AdRequest adRequest = new AdRequest.Builder().build();
	//		.addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("67701763FB847AEBD3C6EE486475ED94")
			adBanner.loadAd(adRequest);
			adBanner.setAdListener(new AdListener() {
				public void onAdLoaded() {
					adBanner.bringToFront();
				}
			});
		
	//		adRect    = (AdView) this.findViewById(R.id.adRect);
	//	    adRequest = new AdRequest.Builder().build();
	////		.addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("67701763FB847AEBD3C6EE486475ED94")
	//		adRect.loadAd(adRequest);
	//		adRect.setAdListener(new AdListener() {
	//			public void onAdLoaded() {
	//				adRect.bringToFront();
	//			}
	//		});
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
		
		WindowManager mWinMgr = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
		M.ScreenWidth =mWinMgr.getDefaultDisplay().getWidth();
		M.ScreenHieght=mWinMgr.getDefaultDisplay().getHeight();
		mGR=new GameRenderer(this);
	    VortexView glSurface=(VortexView) findViewById(R.id.vortexview); // use the xml to set the view
	    glSurface.setRenderer(mGR);
	    glSurface.showRenderer(mGR);
	    
	    interstitial = new InterstitialAd(Start.this);
		interstitial.setAdUnitId(getString(R.string.INTERSTITIAL_ID));
		
//	 //Recievier 
	    IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        mReceiver = new ScreenReceiver();
        registerReceiver(mReceiver, filter);
	 //Recievier
        if(!addFree)
        {
           callAdds();
           LoadHandler();
           isStart=true;
        }
	}

	public static Context getContext() {
	        return CONTEXT;
	}
	@Override 
	public void onPause () {
		super.onPause();
		M.StopSound();
		pause();
		
	}
	@Override 
	public void onResume()
	{
		super.onResume();
		resume();
	}
	public void onStart() 
	{
		super.onStart();
	}
	public void onRestart() 
	{
		super.onRestart();
	}
	public void onDestroy(){
		super.onDestroy();
		 M.StopSound();
		if(mReceiver != null)
        {
            unregisterReceiver(mReceiver);
            mReceiver = null;
        }
//		if(mGR.mMainActivity!=null)
//		{
//		   mGR.mMainActivity.onDestroy();
//		}
		
	}
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		Log.d("----------------=>  "+keyCode,"   -----------    ");
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			switch(M.GameScreen)
			{
				case M.GAMEPLAY:
					M.StopSound();
					M.GameScreen = M.GAMEPAUSE; 
					break;
				case M.GAMEMENU:
					Exit();
					break;
				case M.GAMEHERO:
//				case M.GAMESHOP:
				case M.GAMEOVER:
				case M.GAMEHELP:
				case M.GAMEPAUSE:
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
	void pause()
	{
		if(M.GameScreen == M.GAMEPLAY)
		{
		  M.GameScreen = M.GAMEPAUSE;
		}
		mGR.resumeCounter = 0;
		M.StopSound();
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
	    Editor editor = prefs.edit();
	    editor.putBoolean("addFree", addFree);
	    editor.putBoolean("SingUpadate", SingUpadate);
	    editor.putInt("screen", M.GameScreen);
	    editor.putBoolean("setValue", M.setValue);
	    
	    editor.putBoolean("isDiaEnable", mGR.isDiaEnable);
	    editor.putInt("mNoTaken", mGR.mNoTaken);
	    editor.putInt("mBgNo", mGR.mBgNo);
	    editor.putInt("mBlockSel", mGR.mBlockSel);
	    editor.putInt("mHilanaCnt", mGR.mHilanaCnt);
	    editor.putInt("mWaitCnt", mGR.mWaitCnt);
	    editor.putInt("mTotalDiamond", mGR.mTotalDiamond);
	    editor.putInt("mScore", mGR.mScore);
	    editor.putInt("mBestScore", mGR.mBestScore);
	    editor.putInt("mHelpCnt", mGR.mHelpCnt);
	    
	    
	    for(int i=0;i<mGR.mBlock.length;i++)
	    {
	    	editor.putFloat("BlockX"+i,mGR.mBlock[i].x);
	    	editor.putFloat("BlockY"+i,mGR.mBlock[i].y);
	    	editor.putFloat("BlockS"+i,mGR.mBlock[i].s);
	    	editor.putFloat("BlockW"+i,mGR.mBlock[i].width);
	    	editor.putFloat("BlockVX"+i,mGR.mBlock[i].vx);
	    	editor.putFloat("BlockDis"+i,mGR.mBlock[i].dis);
	    	editor.putBoolean("BlockisStop"+i,mGR.mBlock[i].isStop);
	    	editor.putBoolean("BlockisDisCmp"+i,mGR.mBlock[i].isDisCmp);
	    }
	    for(int i=0;i<mGR.mDanda.length;i++)
	    {
	    	editor.putFloat("DandaX"+i,mGR.mDanda[i].x);
	    	editor.putFloat("DandaY"+i,mGR.mDanda[i].y);
	    	editor.putFloat("DandaS"+i,mGR.mDanda[i].s);
	    	editor.putFloat("DandaSV"+i,mGR.mDanda[i].sv);
	    	editor.putFloat("DandaVX"+i,mGR.mDanda[i].vx);
	    	editor.putFloat("Dandawidth"+i,mGR.mDanda[i].width);
	    	editor.putFloat("DandaAng"+i,mGR.mDanda[i].ang);
	    	editor.putFloat("DandaangInc"+i,mGR.mDanda[i].angInc);
	    	editor.putInt("DandamAction"+i,mGR.mDanda[i].mAction);
	    }
	    
	    editor.putFloat("PlrX",mGR.mPlayer.x);
	    editor.putFloat("PlrY",mGR.mPlayer.y);
	    editor.putFloat("PlrVX",mGR.mPlayer.vx);
	    editor.putFloat("PlrVY",mGR.mPlayer.vy);
	    editor.putInt("PlrmAction",mGR.mPlayer.mAction);
	    editor.putInt("PlrmCrash",mGR.mPlayer.mCrash);
	    editor.putInt("PlrmHeroNo",mGR.mPlayer.mHeroNo);
	    editor.putBoolean("PlrisUlta",mGR.mPlayer.isUlta);
	    for(int i=0;i<mGR.mPlayer.isUnLock.length;i++)
	    {
	    	editor.putBoolean("PlrisUnLock"+i,mGR.mPlayer.isUnLock[i]);
	    	editor.putInt("PlrmVal"+i,mGR.mPlayer.mVal[i]);
	    }
	    for(int i=0;i<mGR.mDiamond.length;i++)
	    {
	    	editor.putFloat("mDiamondX"+i,mGR.mDiamond[i].x);
	    	editor.putFloat("mDiamondY"+i,mGR.mDiamond[i].y);
	    	editor.putFloat("mDiamondVX"+i,mGR.mDiamond[i].vx);
	    	editor.putFloat("mDiamondBx"+i,mGR.mDiamond[i].bx);
	    	editor.putFloat("mDiamondBy"+i,mGR.mDiamond[i].by);
	    	editor.putInt("mDiamondmStop"+i,mGR.mDiamond[i].mStop);
	    }
	    editor.commit();
	    
	}
	void resume()
	{
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		addFree 				= prefs.getBoolean("addFree", addFree);
		SingUpadate 			= prefs.getBoolean("SingUpadate", SingUpadate);
		M.GameScreen 	    	= prefs.getInt("screen", M.GameScreen);
		M.setValue 	 	    	= prefs.getBoolean("setValue", M.setValue);
		
		mGR.isDiaEnable   =  prefs.getBoolean("isDiaEnable", mGR.isDiaEnable);
		mGR.mBgNo         =  prefs.getInt("mNoTaken", mGR.mBgNo);
	    mGR.mBgNo         =  prefs.getInt("mBgNo", mGR.mBgNo);
	    mGR.mBlockSel     =  prefs.getInt("mBlockSel", mGR.mBlockSel);
	    mGR.mHilanaCnt    =  prefs.getInt("mHilanaCnt", mGR.mHilanaCnt);
	    mGR.mWaitCnt      =  prefs.getInt("mWaitCnt", mGR.mWaitCnt);
	    mGR.mTotalDiamond =  prefs.getInt("mTotalDiamond", mGR.mTotalDiamond);
	    mGR.mScore        =  prefs.getInt("mScore", mGR.mScore);
	    mGR.mBestScore    =  prefs.getInt("mBestScore", mGR.mBestScore);
	    mGR.mHelpCnt      =  prefs.getInt("mHelpCnt", mGR.mHelpCnt);
	    
	    
	    for(int i=0;i<mGR.mBlock.length;i++)
	    {
	    	mGR.mBlock[i].x        =  prefs.getFloat("BlockX"+i,mGR.mBlock[i].x);
	    	mGR.mBlock[i].y        =  prefs.getFloat("BlockY"+i,mGR.mBlock[i].y);
	    	mGR.mBlock[i].s        =  prefs.getFloat("BlockS"+i,mGR.mBlock[i].s);
	    	mGR.mBlock[i].width    =  prefs.getFloat("BlockW"+i,mGR.mBlock[i].width);
	    	mGR.mBlock[i].vx       =  prefs.getFloat("BlockVX"+i,mGR.mBlock[i].vx);
	    	mGR.mBlock[i].dis      =  prefs.getFloat("BlockDis"+i,mGR.mBlock[i].dis);
	    	mGR.mBlock[i].isStop   =  prefs.getBoolean("BlockisStop"+i,mGR.mBlock[i].isStop);
	    	mGR.mBlock[i].isDisCmp =  prefs.getBoolean("BlockisDisCmp"+i,mGR.mBlock[i].isDisCmp);
	    }
	    for(int i=0;i<mGR.mDanda.length;i++)
	    {
	    	mGR.mDanda[i].x       = prefs.getFloat("DandaX"+i,mGR.mDanda[i].x);
	    	mGR.mDanda[i].y       = prefs.getFloat("DandaY"+i,mGR.mDanda[i].y);
	    	mGR.mDanda[i].s       = prefs.getFloat("DandaS"+i,mGR.mDanda[i].s);
	    	mGR.mDanda[i].sv      = prefs.getFloat("DandaSV"+i,mGR.mDanda[i].sv);
	    	mGR.mDanda[i].vx      = prefs.getFloat("DandaVX"+i,mGR.mDanda[i].vx);
	    	mGR.mDanda[i].width   = prefs.getFloat("Dandawidth"+i,mGR.mDanda[i].width);
	    	mGR.mDanda[i].ang     = prefs.getFloat("DandaAng"+i,mGR.mDanda[i].ang);
	    	mGR.mDanda[i].angInc  = prefs.getFloat("DandaangInc"+i,mGR.mDanda[i].angInc);
	    	mGR.mDanda[i].mAction = prefs.getInt("DandamAction"+i,mGR.mDanda[i].mAction);
	    }
	    
	    prefs.getFloat("PlrX",mGR.mPlayer.x);
	    mGR.mPlayer.y       =  prefs.getFloat("PlrY",mGR.mPlayer.y);
	    mGR.mPlayer.vx      =  prefs.getFloat("PlrVX",mGR.mPlayer.vx);
	    mGR.mPlayer.vy      =  prefs.getFloat("PlrVY",mGR.mPlayer.vy);
	    mGR.mPlayer.mAction =  prefs.getInt("PlrmAction",mGR.mPlayer.mAction);
	    mGR.mPlayer.mCrash  =  prefs.getInt("PlrmCrash",mGR.mPlayer.mCrash);
	    mGR.mPlayer.mHeroNo =  prefs.getInt("PlrmHeroNo",mGR.mPlayer.mHeroNo);
	    mGR.mPlayer.isUlta  =  prefs.getBoolean("PlrisUlta",mGR.mPlayer.isUlta);
	    for(int i=0;i<mGR.mPlayer.isUnLock.length;i++)
	    {
	    	mGR.mPlayer.isUnLock[i] = prefs.getBoolean("PlrisUnLock"+i,mGR.mPlayer.isUnLock[i]);
	    	mGR.mPlayer.mVal[i] 	= prefs.getInt("PlrmVal"+i,mGR.mPlayer.mVal[i]);
	    }
	    for(int i=0;i<mGR.mDiamond.length;i++)
	    {
	    	mGR.mDiamond[i].x     =  prefs.getFloat("mDiamondX"+i,mGR.mDiamond[i].x);
	    	mGR.mDiamond[i].y     =  prefs.getFloat("mDiamondY"+i,mGR.mDiamond[i].y);
	    	mGR.mDiamond[i].vx    =  prefs.getFloat("mDiamondVX"+i,mGR.mDiamond[i].vx);
	    	mGR.mDiamond[i].bx    =  prefs.getFloat("mDiamondBx"+i,mGR.mDiamond[i].bx);
	    	mGR.mDiamond[i].by    =  prefs.getFloat("mDiamondBy"+i,mGR.mDiamond[i].by);
	    	mGR.mDiamond[i].mStop =  prefs.getInt("mDiamondmStop"+i,mGR.mDiamond[i].mStop);
	    }
		
	}
	void Exit()
	{
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Do you want to Exit?")
	    .setPositiveButton("Rate Us",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
    	   mGR.root.RateUs();
      }}).setNeutralButton("No",new DialogInterface.OnClickListener(){   public void onClick(DialogInterface dialog, int which)
    	  {
	  }}).setNegativeButton("Yes",new DialogInterface.OnClickListener(){ public void onClick(DialogInterface dialog, int which)
    	  {
 		    finish();M.GameScreen=M.GAMELOGO;mGR.root.Counter =0;
 		    M.StopSound();
    	  }}).show();
    }
	void NotEnough()
	{
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Not enough diamonds,Do you want buy? ")
	    .setNeutralButton("Ok",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
//	  }}).setNegativeButton("No",new DialogInterface.OnClickListener(){ public void onClick(DialogInterface dialog, int which)
//    	  {
    	  }}).show();
    }
  @Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		if(!SingUpadate)
		{
//			Submitscore(R.string.leaderboard_best_score,mGR.mBestScore);
		}
	    SingUpadate = true;
	}
	int RC_UNUSED = 5001;
	public void Submitscore(final int ID,final int score) {
    	System.out.println("~~~~~~~~~~~~Submitscore~~~~~~~~~~");
    	if(!isSignedIn())
    	{
       	  // beginUserInitiatedSignIn();
		  return;
		}
    	else
    	{
		   Games.Leaderboards.submitScore(getApiClient(),getString(ID),score);
		}
	}
	public void onShowLeaderboardsRequested()
	{
		if(isSignedIn())
		{
            startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(getApiClient()),RC_UNUSED);
        }
		else
		{
        	beginUserInitiatedSignIn();
        }
	}

	public void UnlockAchievement(final int ID) {
		try{
			 if(!isSignedIn())
			 {
//		    	beginUserInitiatedSignIn();
			 }
			 else
			 {
				if(isSignedIn()){
					Games.Achievements.unlock(getApiClient(), getString(ID));
				}
			 }
		  }catch (Exception e){}
	}
	public void onShowAchievementsRequested(){
	   try{
		    if(isSignedIn()) 
			   startActivityForResult(Games.Achievements.getAchievementsIntent(getApiClient()),RC_UNUSED);
	        else 
			   beginUserInitiatedSignIn();
		 }catch(Exception e){}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			super.onActivityResult(requestCode, resultCode, data);
			/*if (requestCode == InAppActivity.RC_REQUEST) {
				if(!mGR.mMainActivity.mHelper.handleActivityResult(requestCode, resultCode, data)) {
					super.onActivityResult(requestCode, resultCode, data);
				}
			}*/
		} catch (Exception e) {
			System.out.println("onActivityResult error = " + e.toString());
		}
	}
	
	public Handler LoadHandler = new Handler() {public void handleMessage(Message msg){loadInterstitial();}};
	void LoadHandler()
	{
	   try{LoadHandler.sendEmptyMessage(0);} catch (Exception e){}
	}
	void loadInterstitial(){
		if (!interstitial.isLoaded() && !addFree) {
			System.out.println("Loading Start .................");
			AdRequest adRequest = new AdRequest.Builder().build();
			interstitial.loadAd(adRequest);
			interstitial.setAdListener(new AdGameListener(this));
		}
	}
	Handler Showhandler = new Handler() {public void handleMessage(Message msg) {showInterstitial();}};
	public void ShowHandle() {
		try{Showhandler.sendEmptyMessage(0);}catch(Exception e){}
		System.out.println("ShowInterstitial Start .................");
	}
	void showInterstitial(){
		System.out.println("show Inter .................");
		try{
			if(interstitial != null && !addFree)
				if(interstitial.isLoaded()){
					interstitial.show();
					System.out.println("show Start .................");
				}
		}catch (Exception e){
			System.out.println(e.toString());
		}
	}
}