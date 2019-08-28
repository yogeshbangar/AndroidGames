package com.hututu.game.helicoptercontrol;

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
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Start extends BaseGameActivity 
{
	int _keyCode = 0;
	AdView adView = null;
	AdView adView2 = null;
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
			adView2 = (AdView) this.findViewById(R.id.addgame2);
			AdRequest adRequest = new AdRequest.Builder().build();
			adView2.loadAd(adRequest);
			adView2.setAdListener(new AdListener() {
				public void onAdLoaded() {
					adView2.bringToFront();
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
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			
			switch (M.GameScreen) {
			case M.GAMEPLAY:
				 M.stop();
				 M.GameScreen = M.GAMEPAUSE;
				break;
			case M.GAMELOGO:
			case M.GAMEADD:
			case M.GAMEMENU:
				Exit();
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
		int i=0;
		if(M.GameScreen == M.GAMEPLAY)
		{
			M.GameScreen = M.GAMEPAUSE;
		}
		mGR.resumeCounter = 0;
		M.stop();
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
	    Editor editor = prefs.edit();
	    editor.putInt("screen", M.GameScreen);
	    editor.putBoolean("setValue", M.setValue);
	    editor.putInt("resumeCounter", mGR.resumeCounter);
	    editor.putInt("mScore", mGR.mScore);
	    editor.putInt("Score", mGR.root.Score);
	    editor.putInt("timeCnt", mGR.timeCnt);
	    editor.putInt("HighScore", mGR.HighScore);
	    editor.putInt("GameCount", mGR.GameCount);
	    editor.putFloat("ScalValue", mGR.ScalVal);
	    editor.putInt("BlastCount", mGR.BlastCnt);
	    editor.putFloat("BlastX", mGR.blastX);
	    editor.putFloat("BlastY", mGR.blastY);
	    
	    editor.putFloat("PlayerGravity",mGR.mPlayer.gravity);
	    editor.putFloat("PlayerX",mGR.mPlayer.x);
	    editor.putFloat("PlayerY",mGR.mPlayer.y);
	    editor.putFloat("PlayerVY",mGR.mPlayer.vy);
	    editor.putFloat("PlayerAng",player.mPlyerang);
	    for(i=0;i<mGR.mObstacle.length;i++)
	    {
	    	editor.putFloat("mObstacleX"+i,mGR.mObstacle[i].x);
	    	editor.putFloat("mObstacleY"+i,mGR.mObstacle[i].y);
	    	editor.putFloat("mObstacleVX"+i,mGR.mObstacle[i].vx);
	    }
	    for(i=0;i<mGR.mCoin.length;i++)
	    {
	    	editor.putFloat("CoinX"+i,mGR.mCoin[i].x);
	    	editor.putFloat("CoinY"+i,mGR.mCoin[i].y);
	    	editor.putFloat("CoinVX"+i,mGR.mCoin[i].vx);
	    }
	    for(i=0;i<mGR.mBg1.length;i++)
	    {
	    	editor.putFloat("Bg1X"+i,mGR.mBg1[i].x);
	    	editor.putFloat("Bg1VX"+i,mGR.mBg1[i].vx);
	    	
	    	editor.putFloat("Bg2X"+i,mGR.mBg2[i].x);
	    	editor.putFloat("Bg2VX"+i,mGR.mBg2[i].vx);
	    	
	    	editor.putFloat("Bg3X"+i,mGR.mBg3[i].x);
	    	editor.putFloat("Bg3VX"+i,mGR.mBg3[i].vx);
	    	
	    	editor.putFloat("Bg4X"+i,mGR.mBg4[i].x);
	    	editor.putFloat("Bg4VX"+i,mGR.mBg4[i].vx);
	    	
	    	editor.putFloat("Bg5X"+i,mGR.mBg5[i].x);
	    	editor.putFloat("Bg5VX"+i,mGR.mBg5[i].vx);
	    	
	    	editor.putFloat("BgtopX"+i,mGR.mBgtop[i].x);
	    	editor.putFloat("BgtopVX"+i,mGR.mBgtop[i].vx);
	    }
	    for(i=0;i<mGR.mCloud.length;i++)
	    {
	    	editor.putFloat("CloudX"+i,mGR.mCloud[i].x);
	    	editor.putFloat("CloudVX"+i,mGR.mCloud[i].vx);
	    }
	    for(i=0;i<mGR.mParticle.length;i++)
	    {
	    	editor.putFloat("ParticleX"+i,mGR.mParticle[i].x);
	    	editor.putFloat("ParticleY"+i,mGR.mParticle[i].y);
	    	editor.putFloat("ParticleVX"+i,mGR.mParticle[i].vx);
	    	editor.putFloat("ParticleVY"+i,mGR.mParticle[i].vy);
	    	editor.putFloat("ParticleScal"+i,mGR.mParticle[i].scal);
	    }
	    editor.commit();
	}
	void resume()
	{
		int i=0;
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = prefs.getInt("screen", M.GameScreen);
		M.setValue = prefs.getBoolean("setValue", M.setValue);
	    mGR.resumeCounter    =  prefs.getInt("resumeCounter", mGR.resumeCounter);
	    mGR.mScore           =  prefs.getInt("mScore", mGR.mScore);
	    mGR.root.Score       =  prefs.getInt("Score", mGR.root.Score);
	    mGR.timeCnt          =  prefs.getInt("timeCnt", mGR.timeCnt);
	    mGR.HighScore        =  prefs.getInt("HighScore", mGR.HighScore);
	    mGR.GameCount 		 =  prefs.getInt("GameCount", mGR.GameCount);
	    mGR.ScalVal   		 =  prefs.getFloat("ScalValue", mGR.ScalVal);
	    mGR.BlastCnt  		 =  prefs.getInt("BlastCount", mGR.BlastCnt);
	    mGR.blastX    		 =  prefs.getFloat("BlastX", mGR.blastX);
	    mGR.blastY    		 =  prefs.getFloat("BlastY", mGR.blastY);
	    
	    mGR.mPlayer.gravity  =  prefs.getFloat("PlayerGravity",mGR.mPlayer.gravity);
	    mGR.mPlayer.x        =  prefs.getFloat("PlayerX",mGR.mPlayer.x);
	    mGR.mPlayer.y        =  prefs.getFloat("PlayerY",mGR.mPlayer.y);
	    mGR.mPlayer.vy       =  prefs.getFloat("PlayerVY",mGR.mPlayer.vy);
	    player.mPlyerang     =  prefs.getFloat("PlayerAng",player.mPlyerang);
	    for(i=0;i<mGR.mObstacle.length;i++)
	    {
	       mGR.mObstacle[i].x = prefs.getFloat("mObstacleX"+i,mGR.mObstacle[i].x);
	       mGR.mObstacle[i].y = prefs.getFloat("mObstacleY"+i,mGR.mObstacle[i].y);
	       mGR.mObstacle[i].vx= prefs.getFloat("mObstacleVX"+i,mGR.mObstacle[i].vx);
	    }
	    for(i=0;i<mGR.mCoin.length;i++)
	    {
	    	mGR.mCoin[i].x    = prefs.getFloat("CoinX"+i,mGR.mCoin[i].x);
	    	mGR.mCoin[i].y    = prefs.getFloat("CoinY"+i,mGR.mCoin[i].y);
	    	mGR.mCoin[i].vx   = prefs.getFloat("CoinVX"+i,mGR.mCoin[i].vx);
	    }
	    for(i=0;i<mGR.mBg1.length;i++)
	    {
	    	mGR.mBg1[i].x     = prefs.getFloat("Bg1X"+i,mGR.mBg1[i].x);
	    	mGR.mBg1[i].vx    = prefs.getFloat("Bg1VX"+i,mGR.mBg1[i].vx);
	    	
	    	mGR.mBg2[i].x     = prefs.getFloat("Bg2X"+i,mGR.mBg2[i].x);
	    	mGR.mBg2[i].vx    = prefs.getFloat("Bg2VX"+i,mGR.mBg2[i].vx);
	    	
	    	mGR.mBg3[i].x     = prefs.getFloat("Bg3X"+i,mGR.mBg3[i].x);
	    	mGR.mBg3[i].vx    = prefs.getFloat("Bg3VX"+i,mGR.mBg3[i].vx);
	    	
	    	mGR.mBg4[i].x     = prefs.getFloat("Bg4X"+i,mGR.mBg4[i].x);
	    	mGR.mBg4[i].vx    = prefs.getFloat("Bg4VX"+i,mGR.mBg4[i].vx);
	    	
	    	mGR.mBg5[i].x     = prefs.getFloat("Bg5X"+i,mGR.mBg5[i].x);
	    	mGR.mBg5[i].vx    = prefs.getFloat("Bg5VX"+i,mGR.mBg5[i].vx);
	    	
	    	mGR.mBgtop[i].x   = prefs.getFloat("BgtopX"+i,mGR.mBgtop[i].x);
	    	mGR.mBgtop[i].vx  = prefs.getFloat("BgtopVX"+i,mGR.mBgtop[i].vx);
	    }
	    for(i=0;i<mGR.mCloud.length;i++)
	    {
	    	mGR.mCloud[i].x     = prefs.getFloat("CloudX"+i,mGR.mCloud[i].x);
	    	mGR.mCloud[i].vx    = prefs.getFloat("CloudVX"+i,mGR.mCloud[i].vx);
	    }
	    for(i=0;i<mGR.mParticle.length;i++)
	    {
	    	mGR.mParticle[i].x  = prefs.getFloat("ParticleX"+i,mGR.mParticle[i].x);
	    	mGR.mParticle[i].y  = prefs.getFloat("ParticleY"+i,mGR.mParticle[i].y);
	    	mGR.mParticle[i].vx = prefs.getFloat("ParticleVX"+i,mGR.mParticle[i].vx);
	    	mGR.mParticle[i].vy = prefs.getFloat("ParticleVY"+i,mGR.mParticle[i].vy);
	    	mGR.mParticle[i].scal = prefs.getFloat("ParticleScal"+i,mGR.mParticle[i].scal);
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
	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		
	}
	public void Submitscore(final int ID) {
		if (!isSignedIn()) {
			beginUserInitiatedSignIn();
		} else { Games.Leaderboards.submitScore(getApiClient(), getString(ID), mGR.HighScore);
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