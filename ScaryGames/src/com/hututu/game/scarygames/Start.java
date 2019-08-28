
package com.hututu.game.scarygames;


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
	AdView adView = null;
//	AdView adHouse = null;//AdHouse
	GameRenderer mGR = null;
	private static Context CONTEXT;
	int _keyCode = 0;
	private InterstitialAd interstitial;
	boolean IsOncreate = false;
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
//		{
//			adHouse = (AdView) this.findViewById(R.id.advhouse);
//			AdRequest adRequest = new AdRequest.Builder().build();
//			adHouse.loadAd(adRequest);
//			adHouse.setAdListener(new AdListener() {
//				public void onAdLoaded() {
//					adHouse.bringToFront();
//				}
//			});
//		}
	}
	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) 
	{
		CONTEXT	=	this;
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.game);
		interstitial = new InterstitialAd(this);
		interstitial.setAdUnitId(getString(R.string.INTERSTITIAL_ID));
		setVolumeControlStream(AudioManager.STREAM_MUSIC);//OnlyOneChange
		WindowManager mWinMgr = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
		M.ScreenWidth =mWinMgr.getDefaultDisplay().getWidth();
		M.ScreenHieght=mWinMgr.getDefaultDisplay().getHeight();
		mGR=new GameRenderer(this);
	    VortexView glSurface=(VortexView) findViewById(R.id.vortexview); // use the xml to set the view
	    glSurface.setRenderer(mGR);
	    glSurface.showRenderer(mGR);
	    callAdds();
	    IsOncreate = true;
	    load();
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
		Log.d("----------------=>  "+keyCode,"   -----------    ");
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			switch (M.GameScreen) {
			case M.GAMESETTING: case M.GAMEPLAY:case M.GAMEHELP:case M.GAMEOVER:case M.GAMEABOUT:case M.GAMEDIS:case M.GAMEWIN: case M.GAMEPAUSE: 
				M.GameScreen = M.GAMEMENU;
				break;
			case M.GAMESKAL:case  M.GAMESOUND:case M.GAMESEL:case M.GAMETIME:
				M.GameScreen = M.GAMESETTING;
				break;
			case M.GAMEMENU:
			case M.GAMELOGO:
			default:
				get();
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
		mGR.resumeCounter = 0;
		M.stop();
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		if(M.GameScreen==M.GAMEPLAY /*|| M.GameScreen==M.GAMEOVER*/)
		{
			M.GameScreen = M.GAMEMENU;
		}
	    Editor editor = prefs.edit();
	    editor.putInt("screen", M.GameScreen);
	    editor.putBoolean("setValue", M.setValue);
	    
	    editor.putInt("mSelImg"		, mGR.mSelImg);
	    editor.putInt("mGameNo"		, mGR.mGameNo);
	    editor.putInt("mGameTime"	, mGR.mGameTime);
	    editor.putInt("mGameSound"	, mGR.mGameSound);
	    
	    editor.commit();
	}
	void resume()
	{
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = prefs.getInt("screen", 0);
		M.setValue = prefs.getBoolean("setValue", M.setValue);
	}
	void get()
	{
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Do you want to exit?")
	    .setPositiveButton("No",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
//		    	   Intent intent = new Intent(Intent.ACTION_VIEW);
//		    	   intent.setData(Uri.parse(M.LINK+getClass().getPackage().getName()));
//		    	   startActivity(intent);
		       }}).setNegativeButton("Yes",new DialogInterface.OnClickListener() {
		    	   public void onClick(DialogInterface dialog, int which) {
		    		   mGR.root.Counter =0;
		    	   finish();
		    	   M.GameScreen=M.GAMELOGO;
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
}