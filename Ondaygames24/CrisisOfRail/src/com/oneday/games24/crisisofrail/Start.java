package com.oneday.games24.crisisofrail;
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
		
		interstitial = new InterstitialAd(this);
		interstitial.setAdUnitId(getString(R.string.Intertitial));
		
		WindowManager mWinMgr = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
		M.ScreenWidth =mWinMgr.getDefaultDisplay().getWidth();
		M.ScreenHieght=mWinMgr.getDefaultDisplay().getHeight();
		mGR=new GameRenderer(this);
	    VortexView glSurface=(VortexView) findViewById(R.id.vortexview); // use the xml to set the view
	    glSurface.setRenderer(mGR);
	    glSurface.showRenderer(mGR);
//	 //Recievier 
	    IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        mReceiver = new ScreenReceiver();
        registerReceiver(mReceiver, filter);
//	 //Recievier
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
		M.StopSound();
		pause();
		super.onPause();
	}
	@Override 
	public void onResume()
	{
		resume();
		super.onResume();
	}
	public void onStart() 
	{
		if(M.GameScreen == M.GAMEWORLD || M.GameScreen == M.GAMEOVER ||  M.GameScreen == M.GAMEWIN ||  M.GameScreen == M.GAMELEVEL)
    	{
    		if(M.setValue  && ScreenReceiver.wasScreenOn)
    		 M.SplashPlay(GameRenderer.mContext,R.drawable.ui);
    	}
		super.onStart();
	}
	public void onRestart() 
	{
		super.onRestart();
	}
	public void onDestroy(){
		 M.StopSound();
		if(mReceiver != null)
        {
            unregisterReceiver(mReceiver);
            mReceiver = null;
        }
//		if(mGR.mMainActivity!=null)
//		   mGR.mMainActivity.onDestroy();
		super.onDestroy();
	}
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		Log.d("----------------=>  "+keyCode,"   -----------    ");
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			switch(M.GameScreen)
			{
				case M.GAMEPLAY:
					if(mGR.mWaitCnt==0)
					  M.GameScreen=M.GAMEPAUSE;
					M.StopSound();
					break;
				case M.GAMELEVEL:
				case M.GAMEPAUSE:
				case M.GAMEWIN:
				case M.GAMEOVER:
					if(M.setValue && M.GameScreen == M.GAMEPAUSE)
		   		  	    M.SplashPlay(GameRenderer.mContext,R.drawable.ui);
					mGR.root.SetWorld();
					M.GameScreen = M.GAMEWORLD;
					break;
				default:
					M.StopSound();
					M.GameScreen = M.GAMEMENU;
					break;
				case M.GAMEMENU:
					 Exit();
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
		int i=0;
		if(M.GameScreen == M.GAMEPLAY)
			 M.GameScreen = M.GAMEPAUSE;
		mGR.resumeCounter = 0;
		M.StopSound();
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
	    Editor editor = prefs.edit();
	    
	    editor.putBoolean("addFree", addFree);
	    editor.putBoolean("SingUpadate", SingUpadate);
	    editor.putInt("screen", M.GameScreen);
	    editor.putBoolean("setValue", M.setValue);
	    
	    editor.putInt("mLevelNo", mGR.mLevelNo);
	    editor.putInt("mWorldNo", mGR.mWorldNo);
	    editor.putInt("mUnlockLev", mGR.mUnlockLev);
	    editor.putInt("mTrainNo", mGR.mTrainNo);
	    editor.putInt("mArrowNo", mGR.mArrowNo);
	    editor.putInt("mSignalNo", mGR.mSignalNo);
	    editor.putInt("mBombNo", mGR.mBombNo);
	    editor.putInt("mHilanaCnt", mGR.mHilanaCnt);
	    editor.putInt("mLoosecnt", mGR.mLoosecnt);
	    editor.putInt("mWinCnt", mGR.mWinCnt);
	    editor.putInt("mWaitCnt", mGR.mWaitCnt);
	    editor.putFloat("mHilaVal", mGR.mHilaVal);
	    editor.putBoolean("isHilana",mGR.isHilana);
	    for(i=0;i<M.ROW;i++)
	    {
	    	for(int j=0;j<M.COL;j++)
	    	{
	    		editor.putFloat(i+"TileX"+j,mGR.mTile[i][j].x);
	    		editor.putFloat(i+"TileY"+j,mGR.mTile[i][j].y);
	    		editor.putInt(i+"mTNo"+j,mGR.mTile[i][j].mTNo);
	    	}
	    }
	    for(i=0;i<mGR.mTrain.length;i++)
	    {
	    	editor.putFloat("TrainX"+i,mGR.mTrain[i].x);
	    	editor.putFloat("TrainY"+i,mGR.mTrain[i].y);
	    	editor.putFloat("TrainVX"+i,mGR.mTrain[i].vx);
	    	editor.putFloat("TrainVY"+i,mGR.mTrain[i].vy);
	    	editor.putFloat("TrainAng"+i,mGR.mTrain[i].ang);
	    	editor.putFloat("TrainRad"+i,mGR.mTrain[i].rad);
	    	editor.putFloat("Trainmudna"+i,mGR.mTrain[i].mudna);
	    	editor.putFloat("Trainspeed"+i,mGR.mTrain[i].speed);
	    	editor.putFloat("Trainvelo"+i,mGR.mTrain[i].velo);
	    	editor.putFloat("TrainrotVal"+i,mGR.mTrain[i].rotVal);
	    	editor.putBoolean("TrainisMove"+i,mGR.mTrain[i].isMove);
	    	editor.putBoolean("TrainisCollide"+i,mGR.mTrain[i].isCollide);
	    	editor.putInt("Traindir"+i,mGR.mTrain[i].dir);
	    	editor.putInt("TrainrCnt"+i,mGR.mTrain[i].rCnt);
	    	editor.putInt("TrainNo"+i,mGR.mTrain[i].no);
	    	
	    	
	    	editor.putFloat("mBogiX"+i,mGR.mBogi[i].x);
	    	editor.putFloat("mBogiY"+i,mGR.mBogi[i].y);
	    	editor.putFloat("mBogiVX"+i,mGR.mBogi[i].vx);
	    	editor.putFloat("mBogiVY"+i,mGR.mBogi[i].vy);
	    	editor.putFloat("mBogiAng"+i,mGR.mBogi[i].ang);
	    	editor.putFloat("mBogiRad"+i,mGR.mBogi[i].rad);
	    	editor.putFloat("mBogimudna"+i,mGR.mBogi[i].mudna);
	    	editor.putFloat("mBogispeed"+i,mGR.mBogi[i].speed);
	    	editor.putFloat("mBogivelo"+i,mGR.mBogi[i].velo);
	    	editor.putFloat("mBogirotVal"+i,mGR.mBogi[i].rotVal);
	    	editor.putBoolean("mBogiisMove"+i,mGR.mBogi[i].isMove);
	    	editor.putBoolean("mBogiisCollide"+i,mGR.mBogi[i].isCollide);
	    	editor.putInt("mBogidir"+i,mGR.mBogi[i].dir);
	    	editor.putInt("mBogirCnt"+i,mGR.mBogi[i].rCnt);
	    	editor.putInt("mBogiNo"+i,mGR.mBogi[i].no);
	    	
	    }
	    for(i=0;i<mGR.mArrow.length;i++)
	    {
	    	editor.putFloat("mArrowX"+i,mGR.mArrow[i].x);
	    	editor.putFloat("mArrowY"+i,mGR.mArrow[i].y);
	    	editor.putFloat("mArrowang"+i,mGR.mArrow[i].ang);
	    	editor.putFloat("mArroworigAng"+i,mGR.mArrow[i].origAng);
	    	editor.putFloat("mArroworigmudna"+i,mGR.mArrow[i].mudna);
	    	editor.putInt("mArrowOpen"+i,mGR.mArrow[i].Open);
	    	editor.putInt("mArrowmdir"+i,mGR.mArrow[i].mdir);
	    	editor.putBoolean("mArrowisMove"+i,mGR.mArrow[i].isMove);
	    	editor.putBoolean("mArrowisNegative"+i,mGR.mArrow[i].isNegative);
	    }
	    for(i=0;i<mGR.mSignal.length;i++)
	    {
	    	editor.putFloat("mSignalX"+i,mGR.mSignal[i].x);
	    	editor.putFloat("mSignalY"+i,mGR.mSignal[i].y);
	    	editor.putBoolean("mSignalisRedSignal"+i,mGR.mSignal[i].isRedSignal);
	    	editor.putInt("mSingnalCnt"+i,mGR.mSignal[i].mSignalTime);
	    	editor.putInt("mSingnalno"+i,mGR.mSignal[i].no);
	    	editor.putInt("mSingnalmLimit"+i,mGR.mSignal[i].mLimit);
	    }
	    for(i=0;i<mGR.mSmoke.length;i++)
	    {
	    	for(int j=0;j<mGR.mSmoke[i].length;j++)
	    	{
	    	  editor.putFloat(i+"mSmokeX"+j,mGR.mSmoke[i][j].x);
	    	  editor.putFloat(i+"mSmokeY"+j,mGR.mSmoke[i][j].y);
	    	  editor.putFloat(i+"mSmokeVY"+j,mGR.mSmoke[i][j].vy);
	    	  editor.putFloat(i+"mSmokeVX"+j,mGR.mSmoke[i][j].vx);
	    	  editor.putFloat(i+"mSmokeZ"+j,mGR.mSmoke[i][j].z);
	    	  editor.putFloat(i+"mSmokeT"+j,mGR.mSmoke[i][j].t);
	    	  editor.putInt(i+"mSmokeCnt"+j,mGR.mSmoke[i][j].Cnt);
	    	}
	    }
	    for(i=0;i<mGR.mBomb.length;i++)
	    {
	    	editor.putFloat("mBombX"+i,mGR.mBomb[i].x);
	    	editor.putFloat("mBombY"+i,mGR.mBomb[i].y);
	    	editor.putInt("mBombCnt"+i,mGR.mBomb[i].mBombCnt);
	    	editor.putBoolean("isBlast"+i,mGR.mBomb[i].isBlast);
	    }
	    for(i=0;i<mGR.isAchieve.length;i++)
	    {
	    	editor.putBoolean("isAchieve"+i,mGR.isAchieve[i]);
	    }
	    editor.commit();
	    
	}
	void resume()
	{
		int i=0;
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		addFree 				= prefs.getBoolean("addFree", addFree);
		SingUpadate 			= prefs.getBoolean("SingUpadate", SingUpadate);
		M.GameScreen 	    	= prefs.getInt("screen", M.GameScreen);
		M.setValue 	 	    	= prefs.getBoolean("setValue", M.setValue);
		
		mGR.mLevelNo   = prefs.getInt("mLevelNo", mGR.mLevelNo);
		mGR.mWorldNo   = prefs.getInt("mWorldNo", mGR.mWorldNo);
		mGR.mUnlockLev = prefs.getInt("mUnlockLev", mGR.mUnlockLev);
		mGR.mTrainNo   = prefs.getInt("mTrainNo", mGR.mTrainNo);
	    mGR.mArrowNo   = prefs.getInt("mArrowNo", mGR.mArrowNo);
	    mGR.mSignalNo  = prefs.getInt("mSignalNo", mGR.mSignalNo);
	    mGR.mBombNo    = prefs.getInt("mBombNo", mGR.mBombNo);
	    mGR.mHilanaCnt = prefs.getInt("mHilanaCnt",mGR.mHilanaCnt);
	    mGR.mLoosecnt  = prefs.getInt("mLoosecnt", mGR.mLoosecnt);
	    mGR.mWinCnt    = prefs.getInt("mWinCnt", mGR.mWinCnt);
	    mGR.mWaitCnt   = prefs.getInt("mWaitCnt", mGR.mWaitCnt);
	    mGR.mHilaVal   = prefs.getFloat("mHilaVal", mGR.mHilaVal);
	    mGR.isHilana   = prefs.getBoolean("isHilana",mGR.isHilana);
	    for(i=0;i<M.ROW;i++)
	    {
	    	for(int j=0;j<M.COL;j++)
	    	{
	    		mGR.mTile[i][j].x    =  prefs.getFloat(i+"TileX"+j,mGR.mTile[i][j].x);
	    		mGR.mTile[i][j].y    = prefs.getFloat(i+"TileY"+j,mGR.mTile[i][j].y);
	    		mGR.mTile[i][j].mTNo = prefs.getInt(i+"mTNo"+j,mGR.mTile[i][j].mTNo);
	    	}
	    }
	    for(i=0;i<mGR.mTrain.length;i++)
	    {
	    	mGR.mTrain[i].x 		 = prefs.getFloat("TrainX"+i,mGR.mTrain[i].x);
	    	mGR.mTrain[i].y 		 = prefs.getFloat("TrainY"+i,mGR.mTrain[i].y);
	    	mGR.mTrain[i].vx 		 = prefs.getFloat("TrainVX"+i,mGR.mTrain[i].vx);
	    	mGR.mTrain[i].vy   		 = prefs.getFloat("TrainVY"+i,mGR.mTrain[i].vy);
	    	mGR.mTrain[i].ang 		 = prefs.getFloat("TrainAng"+i,mGR.mTrain[i].ang);
	    	mGR.mTrain[i].rad 	  	 = prefs.getFloat("TrainRad"+i,mGR.mTrain[i].rad);
	    	mGR.mTrain[i].mudna 	 = prefs.getFloat("Trainmudna"+i,mGR.mTrain[i].mudna);
	    	mGR.mTrain[i].speed 	 = prefs.getFloat("Trainspeed"+i,mGR.mTrain[i].speed);
	    	mGR.mTrain[i].velo 		 = prefs.getFloat("Trainvelo"+i,mGR.mTrain[i].velo);
	    	mGR.mTrain[i].rotVal     = prefs.getFloat("TrainrotVal"+i,mGR.mTrain[i].rotVal);
	    	mGR.mTrain[i].isMove     = prefs.getBoolean("TrainisMove"+i,mGR.mTrain[i].isMove);
	    	mGR.mTrain[i].isCollide  = prefs.getBoolean("TrainisCollide"+i,mGR.mTrain[i].isCollide);
	    	mGR.mTrain[i].dir  		 = prefs.getInt("Traindir"+i,mGR.mTrain[i].dir);
	    	mGR.mTrain[i].rCnt 		 = prefs.getInt("TrainrCnt"+i,mGR.mTrain[i].rCnt);
	    	mGR.mTrain[i].no   		 = prefs.getInt("TrainNo"+i,mGR.mTrain[i].no);
	    	
	    	
	    	mGR.mBogi[i].x         = prefs.getFloat("mBogiX"+i,mGR.mBogi[i].x);
	    	mGR.mBogi[i].y         = prefs.getFloat("mBogiY"+i,mGR.mBogi[i].y);
	    	mGR.mBogi[i].vx        = prefs.getFloat("mBogiVX"+i,mGR.mBogi[i].vx);
	    	mGR.mBogi[i].vy        = prefs.getFloat("mBogiVY"+i,mGR.mBogi[i].vy);
	    	mGR.mBogi[i].ang 	   = prefs.getFloat("mBogiAng"+i,mGR.mBogi[i].ang);
	    	mGR.mBogi[i].rad 	   = prefs.getFloat("mBogiRad"+i,mGR.mBogi[i].rad);
	    	mGR.mBogi[i].mudna 	   = prefs.getFloat("mBogimudna"+i,mGR.mBogi[i].mudna);
	    	mGR.mBogi[i].speed 	   = prefs.getFloat("mBogispeed"+i,mGR.mBogi[i].speed);
	    	mGR.mBogi[i].velo      = prefs.getFloat("mBogivelo"+i,mGR.mBogi[i].velo);
	    	mGR.mBogi[i].rotVal    = prefs.getFloat("mBogirotVal"+i,mGR.mBogi[i].rotVal);
	    	mGR.mBogi[i].isMove    = prefs.getBoolean("mBogiisMove"+i,mGR.mBogi[i].isMove);
	    	mGR.mBogi[i].isCollide = prefs.getBoolean("mBogiisCollide"+i,mGR.mBogi[i].isCollide);
	    	mGR.mBogi[i].dir  	   = prefs.getInt("mBogidir"+i,mGR.mBogi[i].dir);
	    	mGR.mBogi[i].rCnt      =  prefs.getInt("mBogirCnt"+i,mGR.mBogi[i].rCnt);
	    	mGR.mBogi[i].no        = prefs.getInt("mBogiNo"+i,mGR.mBogi[i].no);
	    	
	    }
	    for(i=0;i<mGR.mArrow.length;i++)
	    {
	    	mGR.mArrow[i].x          = prefs.getFloat("mArrowX"+i,mGR.mArrow[i].x);
	    	mGR.mArrow[i].y          = prefs.getFloat("mArrowY"+i,mGR.mArrow[i].y);
	    	mGR.mArrow[i].ang        = prefs.getFloat("mArrowang"+i,mGR.mArrow[i].ang);
	    	mGR.mArrow[i].origAng    = prefs.getFloat("mArroworigAng"+i,mGR.mArrow[i].origAng);
	    	mGR.mArrow[i].mudna      = prefs.getFloat("mArroworigmudna"+i,mGR.mArrow[i].mudna);
	    	mGR.mArrow[i].Open 		 = prefs.getInt("mArrowOpen"+i,mGR.mArrow[i].Open);
	    	mGR.mArrow[i].mdir       = prefs.getInt("mArrowmdir"+i,mGR.mArrow[i].mdir);
	    	mGR.mArrow[i].isMove     = prefs.getBoolean("mArrowisMove"+i,mGR.mArrow[i].isMove);
	    	mGR.mArrow[i].isNegative = prefs.getBoolean("mArrowisNegative"+i,mGR.mArrow[i].isNegative);
	    }
	    for(i=0;i<mGR.mSignal.length;i++)
	    {
	    	mGR.mSignal[i].x           = prefs.getFloat("mSignalX"+i,mGR.mSignal[i].x);
	    	mGR.mSignal[i].y           = prefs.getFloat("mSignalY"+i,mGR.mSignal[i].y);
	    	mGR.mSignal[i].isRedSignal = prefs.getBoolean("mSignalisRedSignal"+i,mGR.mSignal[i].isRedSignal);
	    	mGR.mSignal[i].mSignalTime = prefs.getInt("mSingnalCnt"+i,mGR.mSignal[i].mSignalTime);
	    	mGR.mSignal[i].no 		   = prefs.getInt("mSingnalno"+i,mGR.mSignal[i].no);
	    	mGR.mSignal[i].mLimit      = prefs.getInt("mSingnalmLimit"+i,mGR.mSignal[i].mLimit);
	    }
	    for(i=0;i<mGR.mSmoke.length;i++)
	    {
	    	for(int j=0;j<mGR.mSmoke[i].length;j++)
	    	{
	    	  mGR.mSmoke[i][j].x   = prefs.getFloat(i+"mSmokeX"+j,mGR.mSmoke[i][j].x);
	    	  mGR.mSmoke[i][j].y   = prefs.getFloat(i+"mSmokeY"+j,mGR.mSmoke[i][j].y);
	    	  mGR.mSmoke[i][j].vy  = prefs.getFloat(i+"mSmokeVY"+j,mGR.mSmoke[i][j].vy);
	    	  mGR.mSmoke[i][j].vx  = prefs.getFloat(i+"mSmokeVX"+j,mGR.mSmoke[i][j].vx);
	    	  mGR.mSmoke[i][j].z   = prefs.getFloat(i+"mSmokeZ"+j,mGR.mSmoke[i][j].z);
	    	  mGR.mSmoke[i][j].t   = prefs.getFloat(i+"mSmokeT"+j,mGR.mSmoke[i][j].t);
	    	  mGR.mSmoke[i][j].Cnt = prefs.getInt(i+"mSmokeCnt"+j,mGR.mSmoke[i][j].Cnt);
	    	}
	    }
	    for(i=0;i<mGR.mBomb.length;i++)
	    {
	    	mGR.mBomb[i].x 		  = prefs.getFloat("mBombX"+i,mGR.mBomb[i].x);
	    	mGR.mBomb[i].y 		  = prefs.getFloat("mBombY"+i,mGR.mBomb[i].y);
	    	mGR.mBomb[i].mBombCnt = prefs.getInt("mBombCnt"+i,mGR.mBomb[i].mBombCnt);
	    	mGR.mBomb[i].isBlast  = prefs.getBoolean("isBlast"+i,mGR.mBomb[i].isBlast);
	    }
	    for(i=0;i<mGR.isAchieve.length;i++)
	    {
	    	mGR.isAchieve[i] = prefs.getBoolean("isAchieve"+i,mGR.isAchieve[i]);
	    }
	}
	void Exit()
	{
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Do you want to Exit?")
	    .setPositiveButton("More",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
    	   Intent intent = new Intent(Intent.ACTION_VIEW);
    	   intent.setData(Uri.parse(M.MARKET));
    	   startActivity(intent);
      }}).setNeutralButton("No",new DialogInterface.OnClickListener(){   public void onClick(DialogInterface dialog, int which)
    	  {
	  }}).setNegativeButton("Yes",new DialogInterface.OnClickListener(){ public void onClick(DialogInterface dialog, int which)
    	  {
 		    finish();M.GameScreen=M.GAMELOGO;mGR.root.Counter =0;
 		    M.StopSound();
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
			for(int i=0;i<mGR.isAchieve.length;i++)
			{
			   if(mGR.isAchieve[i])
			   {
				 if(i<5)
				   UnlockAchievement(mGR.root.A[i]);
				 else
				   UnlockAchievement(R.string.achievement_30th_level_without_lost);
			   }
			}
		    Submitscore(R.string.leaderboard_levelcross,mGR.mUnlockLev);
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
			System.out.println("[requestCode = " + requestCode + "] [resultCode = " + resultCode + "] [data = " + data + "]");
			/*if (requestCode == InAppActivity.RC_REQUEST) {
				if(!mGR.mMainActivity.mHelper.handleActivityResult(
						requestCode, resultCode, data)) {
					super.onActivityResult(requestCode, resultCode, data);
				}
				else{
					 System.out.println("onActivityResult error = RC_REQUEST");
					 if(M.GameScreen == M.GAMEWORLD || M.GameScreen == M.GAMEOVER ||  M.GameScreen == M.GAMEWIN ||  M.GameScreen == M.GAMELEVEL)
		        	 {
		        		if(M.setValue)
 		        		  M.SplashPlay(GameRenderer.mContext,R.drawable.ui);
		        	 }
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
			AdRequest adRequest = new AdRequest.Builder()
//			.addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("67701763FB847AEBD3C6EE486475ED94")
			.build();
			interstitial.loadAd(adRequest);
			interstitial.setAdListener(new ToastAdListener(this));
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