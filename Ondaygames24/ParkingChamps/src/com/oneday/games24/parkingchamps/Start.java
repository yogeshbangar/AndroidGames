package com.oneday.games24.parkingchamps;

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
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class Start extends Activity 
{
	
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
			case M.GAMELOGO:
			case M.GAMEMENU:
				Exit();
				break;
			case M.GAMEPLAY:
				M.GameScreen = M.GAMEPAUSE;
				M.playStop();
				mGR.GameTime = System.currentTimeMillis()-mGR.GameTime;
				break;
			case M.GAMEA:
			case M.GAMEHELP:
				M.GameScreen = M.GAMESETT;
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
	}
	void pause()
	{
		int i;
		mGR.resumeCounter = 0;
		M.playStop();
		M.stop(GameRenderer.mContext);
		
		if(M.GameScreen == M.GAMEPAUSE||M.GameScreen == M.GAMEPLAY)
		{
			if(M.GameScreen == M.GAMEPLAY)
			{
				M.GameScreen = M.GAMEPAUSE;
				mGR.GameTime = System.currentTimeMillis()-mGR.GameTime;
			}
		}
		if(M.GameScreen == M.GAMEOVER)
		{
			M.GameScreen = M.GAMEMENU;
		}
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
	    Editor editor = prefs.edit();
	    editor.putInt("screen", M.GameScreen);
	    editor.putBoolean("setValue", M.setValue);
	    editor.putBoolean("setVibrator", M.setVibrator);
	    
	    
	    editor.putInt("Level", mGR.mLevel);
	    editor.putInt("unLock", mGR.unLock);
	    for(i=0;i<mGR.unStar.length;i++)
	    	editor.putInt("unStar"+i, mGR.unStar[i]);
	    editor.putInt("mPage", mGR.mPage);
	    editor.putFloat("levelWinS1", mGR.levelWinS1);
	    editor.putFloat("levelWinS2", mGR.levelWinS2);
	    editor.putFloat("levelWinS3", mGR.levelWinS3);
	    
	    
	    editor.putBoolean("mPlayergameWin", mGR.mPlayer.gameWin);
	    editor.putInt("mPlayerangleno", mGR.mPlayer.angleno);
	    editor.putInt("mPlayerspeedno", mGR.mPlayer.speedno);
	    editor.putInt("mPlayermThukai", mGR.mPlayer.mThukai);
	    editor.putInt("mPlayermBlast", mGR.mPlayer.mBlast);
	    editor.putFloat("mPlayerx", mGR.mPlayer.x);
	    editor.putFloat("mPlayery", mGR.mPlayer.y);
	    editor.putFloat("mPlayera", mGR.mPlayer.a);
	    editor.putFloat("mPlayerpa", mGR.mPlayer.pa);
	    editor.putFloat("mPlayerox", mGR.mPlayer.ox);
	    editor.putFloat("mPlayeroy", mGR.mPlayer.oy);
	    editor.putFloat("mPlayeroa", mGR.mPlayer.oa);
	    editor.putFloat("mPlayervx", mGR.mPlayer.vx);
	    editor.putFloat("mPlayervy", mGR.mPlayer.vy);
	    
		

	    for(i=0;i<mGR.mOpponent.length;i++){
		    editor.putInt("Opp"+i, mGR.mOpponent[i].type);
		    editor.putFloat("Oppx"+i, mGR.mOpponent[i].x);
		    editor.putFloat("Oppy"+i, mGR.mOpponent[i].y);
		    editor.putFloat("Oppx1"+i, mGR.mOpponent[i].x1);
		    editor.putFloat("Oppy1"+i, mGR.mOpponent[i].y1);
		    editor.putFloat("Oppvx"+i, mGR.mOpponent[i].vx);
		    editor.putFloat("Oppvy"+i, mGR.mOpponent[i].vy);
	    }
	    
	    
	    editor.putLong("GameTime", mGR.GameTime);
	    editor.commit();
	}
	void resume()
	{
		int i;
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = prefs.getInt("screen", M.GAMELOGO);
		M.setValue = prefs.getBoolean("setValue", true);
		M.setVibrator = prefs.getBoolean("setVibrator", true);
	    
	    
		mGR.mLevel = prefs.getInt("Level", mGR.mLevel);
		mGR.unLock = prefs.getInt("unLock", mGR.unLock);
	    for(i=0;i<mGR.unStar.length;i++)
	    	mGR.unStar[i] = prefs.getInt("unStar"+i, mGR.unStar[i]);
	    mGR.mPage = prefs.getInt("mPage", mGR.mPage);
	    mGR.levelWinS1 = prefs.getFloat("levelWinS1", mGR.levelWinS1);
	    mGR.levelWinS2 = prefs.getFloat("levelWinS2", mGR.levelWinS2);
	    mGR.levelWinS3 = prefs.getFloat("levelWinS3", mGR.levelWinS3);
	    
	    
	    mGR.mPlayer.gameWin = prefs.getBoolean("mPlayergameWin", mGR.mPlayer.gameWin);
	    mGR.mPlayer.angleno = prefs.getInt("mPlayerangleno", mGR.mPlayer.angleno);
	    mGR.mPlayer.speedno = prefs.getInt("mPlayerspeedno", mGR.mPlayer.speedno);
	    mGR.mPlayer.mThukai = prefs.getInt("mPlayermThukai", mGR.mPlayer.mThukai);
	    mGR.mPlayer.mBlast  = prefs.getInt("mPlayermBlast", mGR.mPlayer.mBlast);
	    mGR.mPlayer.x = prefs.getFloat("mPlayerx", mGR.mPlayer.x);
	    mGR.mPlayer.y = prefs.getFloat("mPlayery", mGR.mPlayer.y);
	    mGR.mPlayer.a = prefs.getFloat("mPlayera", mGR.mPlayer.a);
	    mGR.mPlayer.pa= prefs.getFloat("mPlayerpa", mGR.mPlayer.pa);
	    mGR.mPlayer.ox = prefs.getFloat("mPlayerox", mGR.mPlayer.ox);
	    mGR.mPlayer.oy = prefs.getFloat("mPlayeroy", mGR.mPlayer.oy);
	    mGR.mPlayer.oa = prefs.getFloat("mPlayeroa", mGR.mPlayer.oa);
	    mGR.mPlayer.vx = prefs.getFloat("mPlayervx", mGR.mPlayer.vx);
	    mGR.mPlayer.vy = prefs.getFloat("mPlayervy", mGR.mPlayer.vy);
	    
		

	    for(i=0;i<mGR.mOpponent.length;i++){
	    	mGR.mOpponent[i].type = prefs.getInt("Opp"+i, mGR.mOpponent[i].type);
	    	mGR.mOpponent[i].x = prefs.getFloat("Oppx"+i, mGR.mOpponent[i].x);
	    	mGR.mOpponent[i].y = prefs.getFloat("Oppy"+i, mGR.mOpponent[i].y);
	    	mGR.mOpponent[i].x1 = prefs.getFloat("Oppx1"+i, mGR.mOpponent[i].x1);
	    	mGR.mOpponent[i].y1 = prefs.getFloat("Oppy1"+i, mGR.mOpponent[i].y1);
	    	mGR.mOpponent[i].vx = prefs.getFloat("Oppvx"+i, mGR.mOpponent[i].vx);
	    	mGR.mOpponent[i].vy = prefs.getFloat("Oppvy"+i, mGR.mOpponent[i].vy);
	    }
		mGR.GameTime = prefs.getLong("GameTime", mGR.GameTime);
	    mGR.resumeCounter = 0;
	    if(M.GameScreen == M.GAMEPAUSE)
	    {
	    	mGR.getArr(mGR.mLevel);
	    }
	}

	void Exit() {
		new AlertDialog.Builder(this)
				.setIcon(R.drawable.icon)
				.setTitle("Do you want to Exit?")
				.setPositiveButton("More", new DialogInterface.OnClickListener() { public void onClick(DialogInterface dialog,int which) {
								Intent intent = new Intent(Intent.ACTION_VIEW);
								intent.setData(Uri.parse(M.MARKET));
								startActivity(intent);
							}
						})
				.setNeutralButton("No", new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {}})
				.setNegativeButton("Yes", new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog,int which) {
								finish();
								M.GameScreen = M.GAMELOGO;
								mGR.root.Counter = 0;}
			}).show();
	}
	
	private InterstitialAd interstitialAd;
	int _keyCode = 0;
	AdView adView = null;
	AdView adHouse = null;//AdHouse
	GameRenderer mGR = null;
	private static Context CONTEXT;

	void callAdds() {
		{
			//AdSize.MEDIUM_RECTANGLE
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
		{
			//AdSize.MEDIUM_RECTANGLE
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
	    callAdds();
	}
	
	public void load() {
		try{handlerload.sendEmptyMessage(0);}catch(Exception e){}
	}
	Handler handlerload = new Handler() {public void handleMessage(Message msg) {loadInter();}};

	
	private void loadInter(){
		if (!interstitialAd.isLoaded()) {
			AdRequest adRequest = new AdRequest.Builder().build();
			interstitialAd.loadAd(adRequest);
			interstitialAd.setAdListener(new ToastAdListener(this));
		}
	}

	public void showIntertitail() {
		try{handler.sendEmptyMessage(0);}catch(Exception e){}
	}
	Handler handler = new Handler() {public void handleMessage(Message msg) {show_ads();}};

	private void show_ads() {
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