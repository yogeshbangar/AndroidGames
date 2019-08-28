package com.hututu.game.archeryking;
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
			case M.GAMEHIGH:
				M.GameScreen = M.GAMEPLAY;
				M.stop(GameRenderer.mContext);
				break;
			case M.GAMELOGO:
			case M.GAMEACHIEV:
			
			case M.GAMEHELP:
			case M.GAMEOVER:
			case M.GAMEABOUT:
			case M.GAMEPUASE:
			default:
				M.GameScreen = M.GAMEMENU;
				break;
			case M.GAMEWIN:
				M.GameScreen = M.GAMEOVER;
				break;
			case M.GAMEPLAY:
				M.play(GameRenderer.mContext,R.drawable.mainbg);
				M.GameScreen = M.GAMEPUASE;
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
		if(keyCode==KeyEvent.KEYCODE_BACK)
			return false;
		_keyCode = 0;
		return super.onKeyUp( keyCode, event ); 
	}
	public void onDestroy()
	{
		super.onDestroy();
		M.stop(CONTEXT);
	}
	void pause()
	{
		int i=0;
		mGR.resumeCounter = 0;
		M.stop(GameRenderer.mContext);
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
	    Editor editor = prefs.edit();
	    editor.putInt("screen", M.GameScreen);
	    editor.putBoolean("setValue", M.setValue);
	    
	    for(i=0;i<mGR.mArrow.length;i++)
	    {
	    	editor.putBoolean("arrc"+i, mGR.mArrow[i].colide);
	    	editor.putFloat("arrx"+i, mGR.mArrow[i].x);
	    	editor.putFloat("arry"+i, mGR.mArrow[i].y);
	    	editor.putFloat("arrvx"+i, mGR.mArrow[i].vx);
	    	editor.putFloat("arrvy"+i, mGR.mArrow[i].vy);
	    	editor.putFloat("arrri"+i, mGR.mArrow[i].radian);
	    	editor.putFloat("arrt"+i, mGR.mArrow[i].t);
	    }
	    {
	    	editor.putFloat("Bowx", mGR.mBow.x);
	    	editor.putFloat("Bowy", mGR.mBow.y);
	    	editor.putFloat("Bowd", mGR.mBow.d);
	    	editor.putFloat("Bowa", mGR.mBow.angle);
	    }
	    for(i=0;i<mGR.mTarget.length;i++)
	    {
	    	editor.putInt("Tarc"+i, mGR.mTarget[i].Color);
	    	editor.putInt("Tarp"+i, mGR.mTarget[i].Power);
	    	editor.putFloat("Tarx"+i, mGR.mTarget[i].x);
	    	editor.putFloat("Tary"+i, mGR.mTarget[i].y);
	    	editor.putFloat("Tarh"+i, mGR.mTarget[i].hit);
	    	editor.putFloat("Tart"+i, mGR.mTarget[i].ty);
	    	editor.putFloat("Tartv"+i, mGR.mTarget[i].tvy);
	    }
	    {
	    	for(i=0;i<mGR.mCom.Achiev.length;i++)
				for(int j=0;j<mGR.mCom.Achiev[i].length;j++)
	    			editor.putBoolean(i+"cmach"+j, mGR.mCom.Achiev[i][j]);
	    	editor.putBoolean("cmbs", mGR.mCom.BSet);
	    	editor.putBoolean("cmna", mGR.mCom.NewAchiv);
	    	for(i=0;i<mGR.mCom.Color.length;i++)
	    		editor.putInt("cmco"+i, mGR.mCom.Color[i]);
	    	editor.putInt("cmcou", mGR.mCom.Counter);
	    	editor.putInt("cmca", mGR.mCom.ColorAch);
	    	editor.putFloat("cmcBlast", mGR.mCom.Blast);
	    }
	    {
	    	editor.putInt("powp", mGR.mPower.Power);
	    	editor.putInt("powt", mGR.mPower.time);
	    	editor.putInt("powr", mGR.mPower.rPower);
	    	editor.putInt("powc", mGR.mPower.counter);
	    	editor.putFloat("powx", mGR.mPower.x);
	    	editor.putFloat("powy", mGR.mPower.y);
	    }
	    for(i=0;i<mGR.mBGround.mTree.length;i++)
	    {
	    	editor.putInt("btreid"+i, mGR.mBGround.mTree[i].ID);
	    	editor.putFloat("btrex"+i, mGR.mBGround.mTree[i].x);
	    	editor.putFloat("btre"+i, mGR.mBGround.mTree[i].y);
	    }
	    editor.putInt("mArrowNO", GameRenderer.mArrowNO);
	    editor.putInt("mScore", mGR.mScore);
	    editor.putInt("mHighScore", mGR.mHighScore);
	    
	    editor.commit();
	}
	void resume()
	{
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = prefs.getInt("screen", M.GAMELOGO);
		M.setValue = prefs.getBoolean("setValue", M.setValue);
		int i;
		
		
		for(i=0;i<mGR.mArrow.length;i++)
	    {
			 mGR.mArrow[i].colide = prefs.getBoolean("arrc"+i, mGR.mArrow[i].colide);
			 mGR.mArrow[i].x = prefs.getFloat("arrx"+i, mGR.mArrow[i].x);
			 mGR.mArrow[i].y = prefs.getFloat("arry"+i, mGR.mArrow[i].y);
			 mGR.mArrow[i].vx = prefs.getFloat("arrvx"+i, mGR.mArrow[i].vx);
			 mGR.mArrow[i].vy = prefs.getFloat("arrvy"+i, mGR.mArrow[i].vy);
			 mGR.mArrow[i].radian = prefs.getFloat("arrri"+i, mGR.mArrow[i].radian);
			 mGR.mArrow[i].t = prefs.getFloat("arrt"+i, mGR.mArrow[i].t);
	    }
	    {
	    	mGR.mBow.x = prefs.getFloat("Bowx", mGR.mBow.x);
	    	mGR.mBow.y = prefs.getFloat("Bowy", mGR.mBow.y);
	    	mGR.mBow.d = prefs.getFloat("Bowd", mGR.mBow.d);
	    	mGR.mBow.angle = prefs.getFloat("Bowa", mGR.mBow.angle);
	    }
	    for(i=0;i<mGR.mTarget.length;i++)
	    {
	    	mGR.mTarget[i].Color = prefs.getInt("Tarc"+i, mGR.mTarget[i].Color);
	    	mGR.mTarget[i].Power = prefs.getInt("Tarp"+i, mGR.mTarget[i].Power);
	    	mGR.mTarget[i].x = prefs.getFloat("Tarx"+i, mGR.mTarget[i].x);
	    	mGR.mTarget[i].y = prefs.getFloat("Tary"+i, mGR.mTarget[i].y);
	    	mGR.mTarget[i].hit = prefs.getFloat("Tarh"+i, mGR.mTarget[i].hit);
	    	mGR.mTarget[i].ty = prefs.getFloat("Tart"+i, mGR.mTarget[i].ty);
	    	mGR.mTarget[i].tvy = prefs.getFloat("Tartv"+i, mGR.mTarget[i].tvy);
	    }
	    {
	    	for(i=0;i<mGR.mCom.Achiev.length;i++)
				for(int j=0;j<mGR.mCom.Achiev[i].length;j++)
					mGR.mCom.Achiev[i][j] = prefs.getBoolean(i+"cmach"+j, mGR.mCom.Achiev[i][j]);
	    	mGR.mCom.BSet = prefs.getBoolean("cmbs", mGR.mCom.BSet);
	    	mGR.mCom.NewAchiv = prefs.getBoolean("cmna", mGR.mCom.NewAchiv);
	    	for(i=0;i<mGR.mCom.Color.length;i++)
	    		mGR.mCom.Color[i] = prefs.getInt("cmco"+i, mGR.mCom.Color[i]);
	    	mGR.mCom.Counter = prefs.getInt("cmcou", mGR.mCom.Counter);
	    	mGR.mCom.ColorAch = prefs.getInt("cmca", mGR.mCom.ColorAch);
	    	mGR.mCom.Blast = prefs.getFloat("cmcBlast", mGR.mCom.Blast);
	    }
	    {
	    	mGR.mPower.Power= prefs.getInt("powp", mGR.mPower.Power);
	    	mGR.mPower.time = prefs.getInt("powt", mGR.mPower.time);
	    	mGR.mPower.rPower = prefs.getInt("powr", mGR.mPower.rPower);
	    	mGR.mPower.counter = prefs.getInt("powc", mGR.mPower.counter);
	    	mGR.mPower.x = prefs.getFloat("powx", mGR.mPower.x);
	    	mGR.mPower.y = prefs.getFloat("powy", mGR.mPower.y);
	    }
	    for(i=0;i<mGR.mBGround.mTree.length;i++)
	    {
	    	mGR.mBGround.mTree[i].ID = prefs.getInt("btreid"+i, mGR.mBGround.mTree[i].ID);
	    	mGR.mBGround.mTree[i].x = prefs.getFloat("btrex"+i, mGR.mBGround.mTree[i].x);
	    	mGR.mBGround.mTree[i].y = prefs.getFloat("btre"+i, mGR.mBGround.mTree[i].y);
	    }
	    GameRenderer.mArrowNO = prefs.getInt("mArrowNO", GameRenderer.mArrowNO);
	    mGR.mScore = prefs.getInt("mScore", mGR.mScore);
	    mGR.mHighScore = prefs.getInt("mHighScore", mGR.mHighScore);
		    
		
		
	    mGR.resumeCounter = 0;
	    if(M.GameScreen != M.GAMELOGO && M.GameScreen  != M.GAMEPLAY)
	    	M.play(GameRenderer.mContext,R.drawable.mainbg);
	}
	void Exit()
	{
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Do you want to exit?")
	    .setPositiveButton("No",new DialogInterface.OnClickListener() {
	    	public void onClick(DialogInterface dialog, int which) {
    	   
		       }}).setNegativeButton("Yes",new DialogInterface.OnClickListener() {
		    	   public void onClick(DialogInterface dialog, int which) {
		    		   mGR.root.Counter = 0;
		    		   finish();
		    		   M.GameScreen=M.GAMELOGO;
		       }}).show();
	  }

	
}