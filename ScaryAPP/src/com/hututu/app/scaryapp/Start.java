package com.hututu.app.scaryapp;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import android.app.Activity;
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
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class Start extends Activity
{
	int _keyCode = 0;
	AdView adView = null;
	GameRenderer mGR = null;
	private static Context CONTEXT;
	private InterstitialAd interstitialAd;

	void callAdds() {
		adView = (AdView) this.findViewById(R.id.addgame);
		AdRequest adRequest = new AdRequest.Builder().build();
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
		interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId(getString(R.string.Interstitial));
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
			if(M.GameScreen == M.GAMEPLAY)
				return false;
			if(M.GameScreen == M.GAMEMENU)
				Exit();
			else
				M.GameScreen = M.GAMEMENU;
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

	void pause() {
		mGR.resumeCounter = 0;
		M.stop(GameRenderer.mContext);
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putInt("mSound", mGR.mSound);
		editor.putInt("mTime", M.mTime);
		editor.putInt("mImg", mGR.mImg);
		
		editor.commit();
	}

	void resume() {
		Sound();
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
//		M.GameScreen = prefs.getInt("screen", M.GAMELOGO);
		M.GameScreen = M.GAMELOGO;
		
		mGR.mSound = prefs.getInt("mSound", mGR.mSound);
		M.mTime = prefs.getInt("mTime", M.mTime);
		mGR.mImg = prefs.getInt("mImg", mGR.mImg);

		
		
		mGR.resumeCounter = 0;
		
		Intent intent = getIntent();
		if (intent != null) {
			int a = intent.getIntExtra("yogesh", 0);
			System.out.println("~~~~~~~~~~~~ ~~~~~~~~ "+a);
			if ( a == 10) {
				intent.putExtra("yogesh", 11);
				M.GameScreen = M.GAMEPLAY;
				M.soundPlay(GameRenderer.mContext, mGR.mSound);
				Vibrator v = (Vibrator) GameRenderer.mContext.getSystemService(Context.VIBRATOR_SERVICE);
				v.vibrate(1000);
			} else {
				try {
					handler.sendEmptyMessage(0);
				} catch (Exception e) {
				}
			}
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

	private AudioManager audio;
	
	
	public boolean Sound() {
		audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		audio.setStreamVolume(AudioManager.STREAM_MUSIC, audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
		return false;
	}
	void load(){
		System.out.println("Load!!!!!!!!!!!!!!!!!!");
		if (!interstitialAd.isLoaded()) {
			AdRequest adRequest = new AdRequest.Builder().build();
			interstitialAd.loadAd(adRequest);
			interstitialAd.setAdListener(new GameAdListener(this));
		}
	}

	public void show() {
		try{handler2.sendEmptyMessage(0);}catch(Exception e){}
	}
	Handler handler2 = new Handler() {public void handleMessage(Message msg) {show_ads();}};

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
	Handler handler = new Handler() {public void handleMessage(Message msg) {GameRenderer.mStart.load();}};
}