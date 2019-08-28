package com.hututu.game.rescuemission;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.games.Games;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Start  extends BaseGameActivity 
{
	int _keyCode = 0;
	AdView adView = null;
	AdView adfull = null;
	AdView adHouse = null;//AdHouse
	GameRenderer mGR = null;
	private static Context CONTEXT;

	void callAdds() {
		{
			adView = (AdView) this.findViewById(R.id.advbanner);
			AdRequest adRequest = new AdRequest.Builder().build();
			adView.loadAd(adRequest);
			adView.setAdListener(new AdListener() {
				public void onAdLoaded() {
					adView.bringToFront();
				}
			});
		}
		{
			adfull = (AdView) this.findViewById(R.id.advfull);
			AdRequest adRequest = new AdRequest.Builder().build();
			adfull.loadAd(adRequest);
			adfull.setAdListener(new AdListener() {
				public void onAdLoaded() {
					adfull.bringToFront();
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
			case M.GAMEPLAY:
				M.GameScreen = M.GAMEPAUSE;
				M.loopStop();
				break;
			case M.GAMEMENU:
				Exit();
				break;
			default:
				M.GameScreen = M.GAMEMENU;
				M.loopStop();
				M.play(GameRenderer.mContext, R.drawable.splash_theme);
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
		M.stop(GameRenderer.mContext);
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
	    Editor editor = prefs.edit();
	    editor.putInt("screen", M.GameScreen);
	    editor.putBoolean("setValue", M.setValue);
	    if(M.GameScreen == M.GAMEPLAY)
	    	M.GameScreen = M.GAMEPAUSE;
	    for(int i =0;i<mGR.mTruck.length;i++)
	    {
	    	editor.putFloat("Truckx"+i, mGR.mTruck[i].x);
	    	editor.putFloat("Trucky"+i, mGR.mTruck[i].y);
	    	editor.putFloat("Truckvx"+i, mGR.mTruck[i].vx);
	    	editor.putFloat("Truckr"+i, mGR.mTruck[i].r);
	    	editor.putFloat("Truckg"+i, mGR.mTruck[i].g);
	    	editor.putFloat("Truckb"+i, mGR.mTruck[i].b);
	    	editor.putInt("Truckt"+i, mGR.mTruck[i].type);
	    }
	    {
	    	editor.putFloat("Helix", mGR.mHeli.x);
	    	editor.putFloat("Heliy", mGR.mHeli.y);
	    	editor.putInt("Helil", mGR.mHeli.life);
	    	editor.putInt("Helis", mGR.mHeli.save);
	    	editor.putInt("Helid", mGR.mHeli.dir);
	    }
	    for(int i =0;i<mGR.mSoldier.length;i++)
	    {
	    	editor.putFloat("Soldierx"+i, mGR.mSoldier[i].x);
	    	editor.putFloat("Soldiery"+i, mGR.mSoldier[i].y);
	    	editor.putFloat("Soldiervy"+i, mGR.mSoldier[i].vy);
	    	editor.putFloat("Soldiervx"+i, mGR.mSoldier[i].vx);
	    	editor.putBoolean("Soldierid"+i, mGR.mSoldier[i].isDead);
	    	editor.putBoolean("Soldierir"+i, mGR.mSoldier[i].isRailing);
	    }
	    {
	    	editor.putFloat("Missilex", mGR.mMissile.x);
	    	editor.putFloat("Missiley", mGR.mMissile.y);
	    	editor.putFloat("Missilevy", mGR.mMissile.vy);
	    	editor.putFloat("Missilevx", mGR.mMissile.vx);
	    	editor.putBoolean("Missileid", mGR.mMissile.isDead);
	    	editor.putBoolean("Missileir", mGR.mMissile.isRailing);
	    }
	    {
	    	editor.putFloat("Tankx", mGR.mTank.x);
	    	editor.putFloat("Tanky", mGR.mTank.y);
	    	editor.putFloat("Tankcx1", mGR.mTank.cx1);
	    	editor.putFloat("Tankcy1", mGR.mTank.cy1);
	    	editor.putFloat("Tankcx2", mGR.mTank.cx2);
	    	editor.putFloat("Tankcy2", mGR.mTank.cy2);
	    	editor.putFloat("Tankcx3", mGR.mTank.cx3);
	    	editor.putFloat("Tankcy3", mGR.mTank.cy3);
	    	editor.putInt("Tankb1", mGR.mTank.isB1);
	    	editor.putInt("Tankb2", mGR.mTank.isB2);
	    	editor.putInt("Tankb3", mGR.mTank.isB3);
	    }
	    {
	    	editor.putFloat("Ufox", mGR.mUfo.x);
	    	editor.putFloat("Ufoy", mGR.mUfo.y);
	    	editor.putFloat("Ufovx", mGR.mUfo.vx);
	    	editor.putFloat("Ufovy", mGR.mUfo.vy);
	    	editor.putInt("Ufoobj", mGR.mUfo.obj);
	    }
		
		
	    editor.putFloat("clud_x", mGR.clud_x);
	    editor.putFloat("mount_x", mGR.mount_x);
	    editor.putFloat("ston_x", mGR.ston_x);
	    editor.putFloat("ground_x", mGR.ground_x);
	    editor.putFloat("road_x", mGR.road_x);
	    editor.putFloat("my", mGR.my);
	    editor.putFloat("mMenu", mGR.mMenu);
	    editor.putFloat("move", mGR.move);
	    editor.putFloat("y1", mGR.y1);
	    editor.putFloat("y2", mGR.y2);
	    
		
	    editor.putInt("mLevel", mGR.mLevel);
	    editor.putInt("mScore", mGR.mScore);
	    editor.putInt("mHScore", mGR.mHScore);
	    editor.putInt("mUnLevel", mGR.mUnLevel);
	    editor.putInt("mPage", mGR.mPage);
	    editor.putInt("mSaveSoldier", mGR.mSS);
	    editor.putInt("mLSoldier", mGR.mLSoldier);
	    
	    
	    editor.commit();
	}
	void resume()
	{
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = prefs.getInt("screen", M.GAMELOGO);
		M.setValue = prefs.getBoolean("setValue", M.setValue);
		mGR.mTimeTaken = System.currentTimeMillis();



		for(int i =0;i<mGR.mTruck.length;i++)
		{
			mGR.mTruck[i].x = prefs.getFloat("Truckx"+i, mGR.mTruck[i].x);
			mGR.mTruck[i].y = prefs.getFloat("Trucky"+i, mGR.mTruck[i].y);
			mGR.mTruck[i].vx = prefs.getFloat("Truckvx"+i, mGR.mTruck[i].vx);
			mGR.mTruck[i].r = prefs.getFloat("Truckr"+i, mGR.mTruck[i].r);
			mGR.mTruck[i].g = prefs.getFloat("Truckg"+i, mGR.mTruck[i].g);
			mGR.mTruck[i].b = prefs.getFloat("Truckb"+i, mGR.mTruck[i].b);
			mGR.mTruck[i].type = prefs.getInt("Truckt"+i, mGR.mTruck[i].type);
		}
		{
			mGR.mHeli.x = prefs.getFloat("Helix", mGR.mHeli.x);
			mGR.mHeli.y = prefs.getFloat("Heliy", mGR.mHeli.y);
			mGR.mHeli.life = prefs.getInt("Helil", mGR.mHeli.life);
			mGR.mHeli.save = prefs.getInt("Helis", mGR.mHeli.save);
			mGR.mHeli.dir = prefs.getInt("Helid", mGR.mHeli.dir);
		}
		for(int i =0;i<mGR.mSoldier.length;i++)
		{
			mGR.mSoldier[i].x = prefs.getFloat("Soldierx"+i, mGR.mSoldier[i].x);
			mGR.mSoldier[i].y = prefs.getFloat("Soldiery"+i, mGR.mSoldier[i].y);
			mGR.mSoldier[i].vy = prefs.getFloat("Soldiervy"+i, mGR.mSoldier[i].vy);
			mGR.mSoldier[i].vx = prefs.getFloat("Soldiervx"+i, mGR.mSoldier[i].vx);
			mGR.mSoldier[i].isDead = prefs.getBoolean("Soldierid"+i, mGR.mSoldier[i].isDead);
			mGR.mSoldier[i].isRailing = prefs.getBoolean("Soldierir"+i, mGR.mSoldier[i].isRailing);
		}
		{
			mGR.mMissile.x = prefs.getFloat("Missilex", mGR.mMissile.x);
			mGR.mMissile.y = prefs.getFloat("Missiley", mGR.mMissile.y);
			mGR.mMissile.vy = prefs.getFloat("Missilevy", mGR.mMissile.vy);
			mGR.mMissile.vx = prefs.getFloat("Missilevx", mGR.mMissile.vx);
			mGR.mMissile.isDead = prefs.getBoolean("Missileid", mGR.mMissile.isDead);
			mGR.mMissile.isRailing = prefs.getBoolean("Missileir", mGR.mMissile.isRailing);
		}
		{
			mGR.mTank.x = prefs.getFloat("Tankx", mGR.mTank.x);
			mGR.mTank.y = prefs.getFloat("Tanky", mGR.mTank.y);
			mGR.mTank.cx1 = prefs.getFloat("Tankcx1", mGR.mTank.cx1);
			mGR.mTank.cy1 = prefs.getFloat("Tankcy1", mGR.mTank.cy1);
			mGR.mTank.cx2 = prefs.getFloat("Tankcx2", mGR.mTank.cx2);
			mGR.mTank.cy2 = prefs.getFloat("Tankcy2", mGR.mTank.cy2);
			mGR.mTank.cx3 = prefs.getFloat("Tankcx3", mGR.mTank.cx3);
			mGR.mTank.cy3 = prefs.getFloat("Tankcy3", mGR.mTank.cy3);
			mGR.mTank.isB1 = (byte)prefs.getInt("Tankb1", mGR.mTank.isB1);
			mGR.mTank.isB2 = (byte)prefs.getInt("Tankb2", mGR.mTank.isB2);
			mGR.mTank.isB3 = (byte)prefs.getInt("Tankb3", mGR.mTank.isB3);
		}
		{
			mGR.mUfo.x = prefs.getFloat("Ufox", mGR.mUfo.x);
			mGR.mUfo.y = prefs.getFloat("Ufoy", mGR.mUfo.y);
			mGR.mUfo.vx = prefs.getFloat("Ufovx", mGR.mUfo.vx);
			mGR.mUfo.vy = prefs.getFloat("Ufovy", mGR.mUfo.vy);
			mGR.mUfo.obj = prefs.getInt("Ufoobj", mGR.mUfo.obj);
		}


		mGR.clud_x = prefs.getFloat("clud_x", mGR.clud_x);
		mGR.mount_x = prefs.getFloat("mount_x", mGR.mount_x);
		mGR.ston_x = prefs.getFloat("ston_x", mGR.ston_x);
		mGR.ground_x = prefs.getFloat("ground_x", mGR.ground_x);
		mGR.road_x = prefs.getFloat("road_x", mGR.road_x);
		mGR.my = prefs.getFloat("my", mGR.my);
		mGR.mMenu = prefs.getFloat("mMenu", mGR.mMenu);
		mGR.move = prefs.getFloat("move", mGR.move);
		mGR.y1 = prefs.getFloat("y1", mGR.y1);
		mGR.y2 = prefs.getFloat("y2", mGR.y2);


		mGR.mLevel = prefs.getInt("mLevel", mGR.mLevel);
		mGR.mScore = prefs.getInt("mScore", mGR.mScore);
		mGR.mHScore = prefs.getInt("mHScore", mGR.mHScore);
		mGR.mUnLevel = prefs.getInt("mUnLevel", mGR.mUnLevel);
		mGR.mPage = prefs.getInt("mPage", mGR.mPage);
		mGR.mSS = prefs.getInt("mSaveSoldier", mGR.mSS);
		mGR.mLSoldier = prefs.getInt("mLSoldier", mGR.mLSoldier);

		if(M.GameScreen==M.GAMEMENU)
		{
			M.loopStop();
			M.play(GameRenderer.mContext, R.drawable.splash_theme);
		}


	}
	void Exit()
	{
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Share your opinion?")
	    .setPositiveButton("Now",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
    	   Intent intent = new Intent(Intent.ACTION_VIEW);
    	   intent.setData(Uri.parse(M.LINK+getClass().getPackage().getName()));
    	   startActivity(intent);
		       }}).setNegativeButton("Later",new DialogInterface.OnClickListener() {
		    	   public void onClick(DialogInterface dialog, int which) {
		    		   finish();
		    		   M.GameScreen=M.GAMELOGO;
		    		   mGR.root.Counter =0;
		       }}).show();
	  }

	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
//		Toast.makeText(Start.this, "Please Try Later!",Toast.LENGTH_LONG).show();
	}

	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
	}

	public void Submitscore(final int ID, int score) {
		if (!isSignedIn()) {
			beginUserInitiatedSignIn();

		} else {
			Games.Leaderboards.submitScore(getApiClient(), getString(ID), score);
		}
	}

	int RC_UNUSED = 5001;

	// @Override
	public void onShowLeaderboardsRequested() {
		if (isSignedIn()) {
            startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(getApiClient()),RC_UNUSED);
        } else {
        	beginUserInitiatedSignIn();
        }
	}

	
}