package com.hututu.game.papershot;


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
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class Start extends Activity{
	int _keyCode = 0;
	AdView adView = null;
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
	@Override
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
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
//		Log.d("----------------=>  "+keyCode,"   -----------    ");
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			switch (M.GameScreen) {
			case M.GAMELOGO:case M.GAMEMENU:
				get();
				break;
			case M.GAMEHIGH:case M.GAMEABOUT:
				if(mGR.isFromMenu)
					M.GameScreen = M.GAMEMENU;
				else
					M.GameScreen = M.GAMEBG;
				break;
			case M.GAMEPLAY:
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
	@Override
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
		if(M.GameScreen == M.GAMEPLAY)
		{
			M.GameScreen = M.GAMEPAUSE;
		}
		M.stop(GameRenderer.mContext);
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
	    Editor editor = prefs.edit();
	    editor.putInt("screen", M.GameScreen);
	    editor.putBoolean("setValue", M.setValue);
	  
	    
	    
	    
	    editor.putFloat("mStonex", mGR.mStone.x);
	    editor.putFloat("mStoney", mGR.mStone.y);
	    editor.putFloat("mStonez", mGR.mStone.z);
	    editor.putFloat("mStonevx", mGR.mStone.vx);
	    editor.putFloat("mStonevy", mGR.mStone.vy);
	    editor.putFloat("mStonevz", mGR.mStone.vz);
	    
	    editor.putInt("mStonec", mGR.mStone.counter);
	    
	    editor.putBoolean("mStonet", mGR.mStone.tuch);
	    editor.putBoolean("mStonein", mGR.mStone.inside);
	    editor.putBoolean("mStoneis", mGR.mStone.isNeg);
	    
	    editor.putInt("mScore", mGR.mScore);
	    
	    editor.putInt("mEScore", mGR.mEScore);
	    editor.putInt("mMScore", mGR.mMScore);
	    editor.putInt("mHScore", mGR.mHScore);
	    
	    editor.putInt("mLevel", mGR.mLevel);
	    editor.putInt("mBG", mGR.mBG);
	    
	    editor.putBoolean("isFromMenu", mGR.isFromMenu);
	    
	    
	    editor.commit();
	}
	void resume()
	{
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = prefs.getInt("screen", M.GAMELOGO);
		M.setValue = prefs.getBoolean("setValue", M.setValue);
		
		
		mGR.mStone.x = prefs.getFloat("mStonex", mGR.mStone.x);
		mGR.mStone.y = prefs.getFloat("mStoney", mGR.mStone.y);
		mGR.mStone.z = prefs.getFloat("mStonez", mGR.mStone.z);
		mGR.mStone.vx = prefs.getFloat("mStonevx", mGR.mStone.vx);
		mGR.mStone.vy = prefs.getFloat("mStonevy", mGR.mStone.vy);
		mGR.mStone.vz = prefs.getFloat("mStonevz", mGR.mStone.vz);
	    
		mGR.mStone.counter = prefs.getInt("mStonec", mGR.mStone.counter);
	    
		mGR.mStone.tuch = prefs.getBoolean("mStonet", mGR.mStone.tuch);
		mGR.mStone.inside = prefs.getBoolean("mStonein", mGR.mStone.inside);
		mGR.mStone.isNeg = prefs.getBoolean("mStoneis", mGR.mStone.isNeg);
	    
		mGR.mScore = prefs.getInt("mScore", mGR.mScore);
	    
		mGR.mEScore = prefs.getInt("mEScore", mGR.mEScore);
		mGR.mMScore = prefs.getInt("mMScore", mGR.mMScore);
		mGR.mHScore = prefs.getInt("mHScore", mGR.mHScore);
	    
		mGR.mLevel = prefs.getInt("mLevel", mGR.mLevel);
		mGR.mBG = prefs.getInt("mBG", mGR.mBG);
	    
		mGR.isFromMenu = prefs.getBoolean("isFromMenu", mGR.isFromMenu);

		
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
		    		   finish();
		    		   M.GameScreen=M.GAMELOGO;
		       }}).show();
	  }

	
}