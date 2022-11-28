package com.oneday.games24.gravityracerboy;
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

public class Start  extends BaseGameActivity 
{
	int _keyCode = 0;
	GameRenderer mGR = null;
	private static Context CONTEXT;
	boolean OnCreate = false;
	GameAd mGameAd = new GameAd();
	void callAdds()
	{

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
		mGameAd.initAds(this);
		mGR=new GameRenderer(this);
	    VortexView glSurface=(VortexView) findViewById(R.id.vortexview); // use the xml to set the view
	    glSurface.setRenderer(mGR);
	    glSurface.showRenderer(mGR);
	    if(!getSharedPreferences("X", MODE_PRIVATE).getBoolean("addFree", mGR.addFree)){
	    	OnCreate = true;
	    	load();
	    	callAdds();
	    }
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
//			M.GameScreen = M.GAMEOVER;
			switch (M.GameScreen) {
			case M.GAMELOGO:
				break;
			case M.GAMEMENU:
				Exit();
				break;
			case M.GAMEPLAY:
				M.stop();
				M.GameScreen = M.GAMEPAUSE;
				break;
			case M.GAMEOVER:
			case M.GAMEABOUT:
			case M.GAMEHELP:
			case M.GAMESET:
			case M.GAMEPAUSE:
				M.GameScreen = M.GAMEMENU;
				M.play(R.raw.splash);
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

	void pause() {
		if (M.GameScreen == M.GAMEPLAY)
			M.GameScreen = M.GAMEPAUSE;
		M.stop();
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putInt("screen", M.GameScreen);
		editor.putBoolean("setValue", M.setValue);
		editor.putBoolean("setMusic", M.setMusic);
		editor.putBoolean("addFree", mGR.addFree);

		editor.putBoolean("go", mGR.go);
		for (int i = 0; i < mGR.mAchiUnlock.length; i++)
			editor.putBoolean("mAchiUnlock" + i, mGR.mAchiUnlock[i]);
		editor.putBoolean("SingUpadate", mGR.SingUpadate);

		editor.putInt("Levelno", mGR.Levelno);
		editor.putInt("colli", mGR.colli);
		editor.putInt("mBG", mGR.mBG);
		editor.putInt("OverCunt", mGR.OverCunt);
		editor.putInt("goAnim", mGR.goAnim);

		editor.putFloat("Bestdistance", mGR.Bestdistance);
		editor.putFloat("TDistance", mGR.TDistance);
		for (int i = 0; i < mGR.bgbx.length; i++)
			editor.putFloat("bgbx" + i, mGR.bgbx[i]);
		for (int i = 0; i < mGR.bgfx.length; i++)
			editor.putFloat("bgfx" + i, mGR.bgfx[i]);
		editor.putFloat("mScore", mGR.mScore);

		for (int i = 0; i < mGR.mTile.length; i++) {
			editor.putInt("mTiled" + i, mGR.mTile[i].down);
			for (int j = 0; j < mGR.mTile[i].array.length; j++) {
				editor.putInt(i + "mTilearry" + j, mGR.mTile[i].array[j]);
			}
			editor.putFloat("mGR.mTile[i].x" + i, mGR.mTile[i].x);
		}
		editor.putString("pname", mGR.mPName);
		{
			editor.putInt("mGuyd", mGR.mGuy.down);
			editor.putInt("mGuycn", mGR.mGuy.colNo);

			editor.putFloat("mGuyx", mGR.mGuy.x);
			editor.putFloat("mGuyy", mGR.mGuy.y);
			editor.putFloat("mGuyspd", mGR.mGuy.spd);
		}
		{
			editor.putInt("mOpptd", mGR.mOppt.down);
			editor.putInt("mOpptcn", mGR.mOppt.colNo);

			editor.putFloat("mOpptx", mGR.mOppt.x);
			editor.putFloat("mOppty", mGR.mOppt.y);
			editor.putFloat("mOpptspd", mGR.mOppt.spd);
		}

		editor.commit();
	}

	void resume() {
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = prefs.getInt("screen", M.GAMELOGO);
		M.setValue = prefs.getBoolean("setValue", M.setValue);
		mGR.addFree = prefs.getBoolean("addFree", false);
		M.setMusic = prefs.getBoolean("setMusic", M.setMusic);

		mGR.go = prefs.getBoolean("go", mGR.go);
		for (int i = 0; i < mGR.mAchiUnlock.length; i++)
			mGR.mAchiUnlock[i] = prefs.getBoolean("mAchiUnlock" + i, mGR.mAchiUnlock[i]);
		mGR.SingUpadate = prefs.getBoolean("SingUpadate", mGR.SingUpadate);

		mGR.Levelno = prefs.getInt("Levelno", mGR.Levelno);
		mGR.colli = prefs.getInt("colli", mGR.colli);
		mGR.mBG = prefs.getInt("mBG", mGR.mBG);
		mGR.OverCunt = prefs.getInt("OverCunt", mGR.OverCunt);
		mGR.goAnim = prefs.getInt("goAnim", mGR.goAnim);

		mGR.Bestdistance = prefs.getFloat("Bestdistance", mGR.Bestdistance);
		mGR.TDistance = prefs.getFloat("TDistance", mGR.TDistance);
		for (int i = 0; i < mGR.bgbx.length; i++)
			mGR.bgbx[i] = prefs.getFloat("bgbx" + i, mGR.bgbx[i]);
		for (int i = 0; i < mGR.bgfx.length; i++)
			mGR.bgfx[i] = prefs.getFloat("bgfx" + i, mGR.bgfx[i]);
		mGR.mScore = prefs.getFloat("mScore", mGR.mScore);

		for (int i = 0; i < mGR.mTile.length; i++) {
			mGR.mTile[i].down = (byte)prefs.getInt("mTiled" + i, mGR.mTile[i].down);
			for (int j = 0; j < mGR.mTile[i].array.length; j++) {
				mGR.mTile[i].array[j] = (short)prefs.getInt(i + "mTilearry" + j, mGR.mTile[i].array[j]);
			}
			mGR.mTile[i].x = prefs.getFloat("mGR.mTile[i].x" + i, mGR.mTile[i].x);
		}
		mGR.mPName = prefs.getString("pname", mGR.mPName);
		{
			mGR.mGuy.down = (byte)prefs.getInt("mGuyd", mGR.mGuy.down);
			mGR.mGuy.colNo = prefs.getInt("mGuycn", mGR.mGuy.colNo);

			mGR.mGuy.x = prefs.getFloat("mGuyx", mGR.mGuy.x);
			mGR.mGuy.y = prefs.getFloat("mGuyy", mGR.mGuy.y);
			mGR.mGuy.spd = prefs.getFloat("mGuyspd", mGR.mGuy.spd);
		}
		{
			mGR.mOppt.down = (byte)prefs.getInt("mOpptd", mGR.mOppt.down);
			mGR.mOppt.colNo = prefs.getInt("mOpptcn", mGR.mOppt.colNo);

			mGR.mOppt.x = prefs.getFloat("mOpptx", mGR.mOppt.x);
			mGR.mOppt.y = prefs.getFloat("mOppty", mGR.mOppt.y);
			mGR.mOppt.spd = prefs.getFloat("mOpptspd", mGR.mOppt.spd);
		}

		if (M.GameScreen == M.GAMEMENU) {
			M.play(R.raw.splash);
		}
	}
	void Exit()
	{
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Do you want to Exit?")
	    .setPositiveButton("More",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
    	   Intent intent = new Intent(Intent.ACTION_VIEW);
    	   intent.setData(Uri.parse(M.MARKET));
    	   startActivity(intent);
      }}).setNeutralButton("No",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
      }}).setNegativeButton("Yes",new DialogInterface.OnClickListener(){public void onClick(DialogInterface dialog, int which) {
		    		   finish();M.GameScreen=M.GAMELOGO;mGR.root.Counter =0;
      }}).show();
  }
	public void load() {
		if(!mGR.addFree)
			try{
				handlerLoad.sendEmptyMessage(0);}catch(Exception e){}
	}
	Handler handlerLoad = new Handler() {public void handleMessage(Message msg) {loadInter();}};

	
	private void loadInter(){
		mGameAd.loadInterstitialAd();
	}

	public void ShowInterstitial() {
		if(!mGR.addFree)
			try{handler.sendEmptyMessage(0);}catch(Exception e){}
	}
	Handler handler = new Handler() {public void handleMessage(Message msg) {show();}};

	private void show() {
		mGameAd.showInterstitialAd();
	}
}