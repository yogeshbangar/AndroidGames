package com.hututu.game.galaxyhunt;
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
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class Start extends Activity
{
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
	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		Log.d("----------------=>  "+keyCode,"   -----------    ");
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			switch (M.GameScreen) {
			case  M.GAMEPAUSE:
			case  M.GAMEHELP:
			case  M.GAMEOVER:
			case  M.GAMEINFO:
			case  M.GAMEHIGH:
				M.GameScreen = M.GAMEMENU;
				break;
			case  M.GAMEPLAY:
				M.GameScreen = M.GAMEPAUSE;
				M.stop();
				break;
			default:
				get();
				break;
			}
			
			return false;
		}
		return super.onKeyDown(keyCode,event); 
	}  
	public boolean onKeyUp(int keyCode, KeyEvent event) 
	{
		if(keyCode==KeyEvent.KEYCODE_BACK)
			return false;
		return super.onKeyUp( keyCode, event ); 
	}

	void pause()
	{
		int i=0;
		mGR.resumeCounter = 0;
		M.stop();
		if(M.GameScreen==M.GAMEPLAY)
			M.GameScreen = M.GAMEPAUSE;
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
	    Editor editor = prefs.edit();
	    editor.putInt("screen", M.GameScreen);
	    editor.putBoolean("setValue", M.setValue);
	    editor.putInt("Plife", M.Plife);
	    
	    editor.putInt("mScore", mGR.mScore);
	    editor.putInt("mLevel", mGR.mLevel);
	    editor.putInt("Counter", mGR.root.Counter);
	    editor.putInt("mHighScore", mGR.mHighScore);
	    
	    for(i=0;i<mGR.mObject.length;i++)
	    {
	    	
	    	editor.putInt("objpNo"+i, mGR.mObject[i].pNo);
	    	editor.putInt("objmLife"+i, mGR.mObject[i].mLife);
	    	editor.putFloat("objx"+i, mGR.mObject[i].x);
	    	editor.putFloat("objy"+i, mGR.mObject[i].y);
	    	editor.putFloat("objvx"+i, mGR.mObject[i].vx);
	    	editor.putFloat("objvy"+i, mGR.mObject[i].vy);
	    }
	    for(i=0;i<mGR.mAni.length;i++)
	    {
	    	editor.putInt("mAnicl"+i, mGR.mAni[i].cl);
	    	editor.putFloat("mAnix"+i, mGR.mAni[i].x);
	    	editor.putFloat("mAniy"+i, mGR.mAni[i].y);
	    	editor.putFloat("mAniz"+i, mGR.mAni[i].z);
	    	editor.putFloat("mAnivx"+i, mGR.mAni[i].vx);
	    	editor.putFloat("mAnivy"+i, mGR.mAni[i].vy);
	    	editor.putFloat("mAnivz"+i, mGR.mAni[i].vz);
	    }
	    for(i=0;i<mGR.mBullete.length;i++)
	    {
	    	editor.putInt("mBulletecolor"+i, mGR.mBullete[i].color);
	    	editor.putInt("mBulletemPower"+i, mGR.mBullete[i].mPower);
	    	editor.putFloat("mBulletex"+i, mGR.mBullete[i].x);
	    	editor.putFloat("mBulletey"+i, mGR.mBullete[i].y);
	    	editor.putFloat("mBulletevx"+i, mGR.mBullete[i].vx);
	    	editor.putFloat("mBulletevy"+i, mGR.mBullete[i].vy);
	    }
	    editor.putInt("mPowercolor", mGR.mPower.color);
    	editor.putInt("mPowermPower", mGR.mPower.mPower);
    	editor.putFloat("mPowerx", mGR.mPower.x);
    	editor.putFloat("mPowery", mGR.mPower.y);
    	editor.putFloat("mPowervx", mGR.mPower.vx);
    	editor.putFloat("mPowervy", mGR.mPower.vy);
    	
    	
    	editor.putInt("mElectriccolor", mGR.mElectric.color);
    	editor.putInt("mElectricmElectric", mGR.mElectric.mPower);
    	editor.putFloat("mElectricx", mGR.mElectric.x);
    	editor.putFloat("mElectricy", mGR.mElectric.y);
    	editor.putFloat("mElectricvx", mGR.mElectric.vx);
    	editor.putFloat("mElectricvy", mGR.mElectric.vy);
	    
		
		editor.putFloat("mPlayerx", mGR.mPlayer.x);
    	editor.putFloat("mPlayery", mGR.mPlayer.y);
    	editor.putFloat("mPlayervx", mGR.mPlayer.vx);
    	editor.putFloat("mPlayervy", mGR.mPlayer.vy);
    	editor.putInt("mPlayer0", mGR.mPlayer.Power[0]);
    	editor.putInt("mPlayer1", mGR.mPlayer.Power[1]);
    	editor.putInt("mPlayer2", mGR.mPlayer.Power[2]);
    	editor.putInt("mPlayerActivePower", mGR.mPlayer.ActivePower);
    	editor.putInt("mPlayerActiveCounter", mGR.mPlayer.ActiveCounter);
	
	    
	    
	    editor.commit();
	}
	void resume()
	{
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = prefs.getInt("screen", M.GAMELOGO);
		M.setValue = prefs.getBoolean("setValue", M.setValue);
		M.Plife = prefs.getInt("Plife", M.Plife);
		
		
		mGR.mScore = prefs.getInt("mScore", mGR.mScore);
		mGR.mHighScore = prefs.getInt("mHighScore", mGR.mHighScore);
		
		mGR.mLevel = prefs.getInt("mLevel", mGR.mLevel);
		mGR.root.Counter = prefs.getInt("Counter", mGR.root.Counter);
	    int i;
	    for(i=0;i<mGR.mObject.length;i++)
	    {
	    	
	    	mGR.mObject[i].pNo = prefs.getInt("objpNo"+i, mGR.mObject[i].pNo);
	    	mGR.mObject[i].mLife = prefs.getInt("objmLife"+i, mGR.mObject[i].mLife);
	    	mGR.mObject[i].x = prefs.getFloat("objx"+i, mGR.mObject[i].x);
	    	mGR.mObject[i].y = prefs.getFloat("objy"+i, mGR.mObject[i].y);
	    	mGR.mObject[i].vx = prefs.getFloat("objvx"+i, mGR.mObject[i].vx);
	    	mGR.mObject[i].vy = prefs.getFloat("objvy"+i, mGR.mObject[i].vy);
	    }
	    for(i=0;i<mGR.mAni.length;i++)
	    {
	    	mGR.mAni[i].cl = prefs.getInt("mAnicl"+i, mGR.mAni[i].cl);
	    	mGR.mAni[i].x = prefs.getFloat("mAnix"+i, mGR.mAni[i].x);
	    	mGR.mAni[i].y = prefs.getFloat("mAniy"+i, mGR.mAni[i].y);
	    	mGR.mAni[i].z = prefs.getFloat("mAniz"+i, mGR.mAni[i].z);
	    	mGR.mAni[i].vx = prefs.getFloat("mAnivx"+i, mGR.mAni[i].vx);
	    	mGR.mAni[i].vy = prefs.getFloat("mAnivy"+i, mGR.mAni[i].vy);
	    	mGR.mAni[i].vz = prefs.getFloat("mAnivz"+i, mGR.mAni[i].vz);
	    }
	    for(i=0;i<mGR.mBullete.length;i++)
	    {
	    	mGR.mBullete[i].color = prefs.getInt("mBulletecolor"+i, mGR.mBullete[i].color);
	    	mGR.mBullete[i].mPower = prefs.getInt("mBulletemPower"+i, mGR.mBullete[i].mPower);
	    	mGR.mBullete[i].x = prefs.getFloat("mBulletex"+i, mGR.mBullete[i].x);
	    	mGR.mBullete[i].y = prefs.getFloat("mBulletey"+i, mGR.mBullete[i].y);
	    	mGR.mBullete[i].vx = prefs.getFloat("mBulletevx"+i, mGR.mBullete[i].vx);
	    	mGR.mBullete[i].vy = prefs.getFloat("mBulletevy"+i, mGR.mBullete[i].vy);
	    }
	    mGR.mPower.color = prefs.getInt("mPowercolor", mGR.mPower.color);
	    mGR.mPower.mPower = prefs.getInt("mPowermPower", mGR.mPower.mPower);
	    mGR.mPower.x = prefs.getFloat("mPowerx", mGR.mPower.x);
	    mGR.mPower.y = prefs.getFloat("mPowery", mGR.mPower.y);
	    mGR.mPower.vx = prefs.getFloat("mPowervx", mGR.mPower.vx);
	    mGR.mPower.vy = prefs.getFloat("mPowervy", mGR.mPower.vy);
    	
    	
	    mGR.mElectric.color = prefs.getInt("mElectriccolor", mGR.mElectric.color);
	    mGR.mElectric.mPower = prefs.getInt("mElectricmElectric", mGR.mElectric.mPower);
	    mGR.mElectric.x = prefs.getFloat("mElectricx", mGR.mElectric.x);
	    mGR.mElectric.y = prefs.getFloat("mElectricy", mGR.mElectric.y);
	    mGR.mElectric.vx = prefs.getFloat("mElectricvx", mGR.mElectric.vx);
	    mGR.mElectric.vy = prefs.getFloat("mElectricvy", mGR.mElectric.vy);
	    
		
	    mGR.mPlayer.x = prefs.getFloat("mPlayerx", mGR.mPlayer.x);
	    mGR.mPlayer.y = prefs.getFloat("mPlayery", mGR.mPlayer.y);
	    mGR.mPlayer.vx = prefs.getFloat("mPlayervx", mGR.mPlayer.vx);
	    mGR.mPlayer.vy = prefs.getFloat("mPlayervy", mGR.mPlayer.vy);
	    mGR.mPlayer.Power[0] = prefs.getInt("mPlayer0", mGR.mPlayer.Power[0]);
	    mGR.mPlayer.Power[1] = prefs.getInt("mPlayer1", mGR.mPlayer.Power[1]);
	    mGR.mPlayer.Power[2] = prefs.getInt("mPlayer2", mGR.mPlayer.Power[2]);
	    mGR.mPlayer.ActivePower = prefs.getInt("mPlayerActivePower", mGR.mPlayer.ActivePower);
	    mGR.mPlayer.ActiveCounter = prefs.getInt("mPlayerActiveCounter", mGR.mPlayer.ActiveCounter);
		
	    mGR.resumeCounter = 0;
	}
	void get()
	{
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Do you want to Exit?")
	    .setPositiveButton("No",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
//    	   Intent intent = new Intent(Intent.ACTION_VIEW);
//    	   intent.setData(Uri.parse("market://details?id="+getClass().getPackage().getName()));
//    	   startActivity(intent);
		       }}).setNegativeButton("Yes",new DialogInterface.OnClickListener() {
		    	   public void onClick(DialogInterface dialog, int which) {
		    		   mGR.root.Counter = 0;
		    		   finish();
		    		   M.GameScreen=M.GAMELOGO;
		       }}).show();
	}

	
	
}