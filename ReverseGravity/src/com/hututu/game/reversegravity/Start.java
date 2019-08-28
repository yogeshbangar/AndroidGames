package com.hututu.game.reversegravity;

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

public class Start extends Activity
{
	int _keyCode = 0;
	AdView adView = null;
	AdView adRect = null;
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
			adRect = (AdView) this.findViewById(R.id.addrect);
			AdRequest adRequest = new AdRequest.Builder().build();
			adRect.loadAd(adRequest);
			adRect.setAdListener(new AdListener() {
				public void onAdLoaded() {
					adRect.bringToFront();
				}
			});
		}
		{
			adHouse = (AdView) this.findViewById(R.id.advhouse);
			AdRequest adRequest = new AdRequest.Builder().build();
			adHouse.loadAd(adRequest);
			adView.setAdListener(new AdListener() {
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
		pause();
		super.onPause();
		
	}
	@Override 
	public void onResume() {
		resume();
		super.onResume();
	}
	/*@Override 
	public void onStart()
	{
		Log.d("----------------  1","   -----------    ");
		try{
			adBuddiz.onStart(this);
			adBuddiz.cacheAds(this);    // start ads caching
		}catch (Exception e) {
			// TODO: handle exception
			Log.d("~~~~~",e.toString());
		}
	}*/
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		Log.d("----------------=>  "+keyCode,"   -----------    ");
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{			
			switch (M.GameScreen) {
			case M.GAMELOGO:case M.GAMEMENU:
				get();
				break;
			case M.GAMEHIGH:
				M.GameScreen = mGR.hss;
				break;
			case M.GAMEPLAY:
				M.GameScreen = M.GAMEPAUSE;
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
		AccelerometerManager.stopListening();
	}
	void pause()
	{
		int i =0;
		if(M.GameScreen == M.GAMEPLAY)
			M.GameScreen = M.GAMEPAUSE;
		mGR.resumeCounter = 0;
		M.stop(mGR.mContext);
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
	    Editor editor = prefs.edit();
	    editor.putInt("screen", M.GameScreen);
	    editor.putBoolean("setValue", M.setValue);
	       
	 
		
		editor.putFloat("Plyx",mGR.mPlayer.x);
		editor.putFloat("Plyy",mGR.mPlayer.y);
		editor.putFloat("Plyvx",mGR.mPlayer.vx);
		editor.putFloat("Plyvy",mGR.mPlayer.vy);
		editor.putInt("PlyT",mGR.mPlayer.mtap);
		editor.putInt("PlyL",mGR.mPlayer.mLife);
		
		
		for(i =0; i<mGR.mPad.length;i++)
		{
			editor.putFloat("Padx"+i,mGR.mPad[i].x);
			editor.putFloat("Pady"+i,mGR.mPad[i].y);
			editor.putInt("Padg"+i,mGR.mPad[i].Gift);
		}
		
		for(i =0; i<mGR.mObject.length;i++)
		{
			editor.putFloat("Objx"+i,mGR.mObject[i].x);
			editor.putFloat("Objy"+i,mGR.mObject[i].y);
			editor.putFloat("Objvx"+i,mGR.mObject[i].vx);
		}
		for(i =0; i<mGR.mPoint.length;i++)
		{
			editor.putFloat("Poinx"+i,mGR.mPoint[i].x);
			editor.putFloat("Poiny"+i,mGR.mPoint[i].y);
			editor.putFloat("Poinvy"+i,mGR.mPoint[i].vy);
			editor.putInt("Poinp"+i,mGR.mPoint[i].mPoint);
		}
		for(i =0; i<mGR.mAni.length;i++)
		{
			editor.putFloat("Anix"+i,mGR.mAni[i].x);
			editor.putFloat("Aniy"+i,mGR.mAni[i].y);
			editor.putFloat("Anivx"+i,mGR.mAni[i].vx);
			editor.putFloat("Anivy"+i,mGR.mAni[i].vy);
			editor.putInt("Anicl"+i,mGR.mAni[i].cl);
		}
		editor.putBoolean("mContinue", mGR.mContinue);
		editor.putInt("mScore", mGR.mScore);
		
		editor.putInt("mHScore", mGR.mHScore);
		editor.putInt("mSpeedC", mGR.mSpeedC);
		editor.putInt("mSpeedN", mGR.mSpeedN);
		editor.putInt("mLevel", mGR.mLevel);
		editor.putInt("hss", mGR.hss);
		editor.putFloat("mBGY", mGR.mBGY);
		editor.putFloat("mBGSY1", mGR.mBGSY1);
		editor.putFloat("mBGSY2", mGR.mBGSY2);
	  
	    editor.commit();
	}
	void resume()
	{
		int i=0;
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = prefs.getInt("screen", M.GAMELOGO);
		M.setValue = prefs.getBoolean("setValue", M.setValue);
		
		mGR.mPlayer.x = prefs.getFloat("Plyx",mGR.mPlayer.x);
		mGR.mPlayer.y = prefs.getFloat("Plyy",mGR.mPlayer.y);
		mGR.mPlayer.vx = prefs.getFloat("Plyvx",mGR.mPlayer.vx);
		mGR.mPlayer.vy = prefs.getFloat("Plyvy",mGR.mPlayer.vy);
		mGR.mPlayer.mtap = prefs.getInt("PlyT",mGR.mPlayer.mtap);
		mGR.mPlayer.mLife = prefs.getInt("PlyL",mGR.mPlayer.mLife);
		
		
		for(i =0; i<mGR.mPad.length;i++)
		{
			mGR.mPad[i].x = prefs.getFloat("Padx"+i,mGR.mPad[i].x);
			mGR.mPad[i].y = prefs.getFloat("Pady"+i,mGR.mPad[i].y);
			mGR.mPad[i].Gift = prefs.getInt("Padg"+i,mGR.mPad[i].Gift);
		}
		
		for(i =0; i<mGR.mObject.length;i++)
		{
			mGR.mObject[i].x = prefs.getFloat("Objx"+i,mGR.mObject[i].x);
			mGR.mObject[i].y = prefs.getFloat("Objy"+i,mGR.mObject[i].y);
			mGR.mObject[i].vx = prefs.getFloat("Objvx"+i,mGR.mObject[i].vx);
		}
		for(i =0; i<mGR.mPoint.length;i++)
		{
			mGR.mPoint[i].x = prefs.getFloat("Poinx"+i,mGR.mPoint[i].x);
			mGR.mPoint[i].y = prefs.getFloat("Poiny"+i,mGR.mPoint[i].y);
			mGR.mPoint[i].vy = prefs.getFloat("Poinvy"+i,mGR.mPoint[i].vy);
			mGR.mPoint[i].mPoint = prefs.getInt("Poinp"+i,mGR.mPoint[i].mPoint);
		}
		for(i =0; i<mGR.mAni.length;i++)
		{
			mGR.mAni[i].x = prefs.getFloat("Anix"+i,mGR.mAni[i].x);
			mGR.mAni[i].y = prefs.getFloat("Aniy"+i,mGR.mAni[i].y);
			mGR.mAni[i].vx = prefs.getFloat("Anivx"+i,mGR.mAni[i].vx);
			mGR.mAni[i].vy = prefs.getFloat("Anivy"+i,mGR.mAni[i].vy);
			mGR.mAni[i].cl = prefs.getInt("Anicl"+i,mGR.mAni[i].cl);
		}
		mGR.mContinue = prefs.getBoolean("mContinue", mGR.mContinue);
		mGR.mScore = prefs.getInt("mScore", mGR.mScore);
		
		mGR.mHScore = prefs.getInt("mHScore", mGR.mHScore);
		mGR.mSpeedC = prefs.getInt("mSpeedC", mGR.mSpeedC);
		mGR.mSpeedN = prefs.getInt("mSpeedN", mGR.mSpeedN);
		mGR.mLevel = prefs.getInt("mLevel", mGR.mLevel);
		mGR.hss = prefs.getInt("hss", mGR.hss);
		mGR.mBGY = prefs.getFloat("mBGY", mGR.mBGY);
		mGR.mBGSY1 = prefs.getFloat("mBGSY1", mGR.mBGSY1);
		mGR.mBGSY2 = prefs.getFloat("mBGSY2", mGR.mBGSY2);
		
		
		
	    mGR.resumeCounter = 0;
	}
	void get()
	{
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Do you want to exit?")
	    .setPositiveButton("No",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
//    	   Intent intent = new Intent(Intent.ACTION_VIEW);
//    	   intent.setData(Uri.parse(M.LINK+getClass().getPackage().getName()));
//    	   startActivity(intent);
		       }}).setNegativeButton("Yes",new DialogInterface.OnClickListener() {
		    	   public void onClick(DialogInterface dialog, int which) {
		    		   mGR.root.Counter =0;
		    		   finish();
		    		   M.GameScreen=M.GAMELOGO;
		       }}).show();
	  }
	
}