package com.hututu.game.penaltyshootout;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
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
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class Start extends BaseGameActivity{
	int _keyCode = 0;
	AdView adView = null;
	AdView adHouse = null;//AdHouse
	GameRenderer mGR = null;
	private static Context CONTEXT;
	private InterstitialAd interstitialAd;

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
				M.GameScreen = M.GAMEPUSE;
				mGR.gametime = System.currentTimeMillis() - mGR.gametime;
				M.playStop();
				break;
			case M.GAMEMENU:
				Exit();
				break;
			default:
				M.GameScreen = M.GAMEMENU;
				break;
			}
			M.sound1(GameRenderer.mContext, R.drawable.click);
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
	    if(M.GameScreen == M.GAMEPLAY){
	    	M.GameScreen = M.GAMEPUSE;
	    	mGR.gametime = System.currentTimeMillis() - mGR.gametime;
	    }
	    editor.putInt("screen", M.GameScreen);
	    editor.putBoolean("setValue", M.setValue);
	    
	    
	    {
	    	editor.putFloat("pointx", mGR.mPoint.x);
	    	editor.putFloat("pointy", mGR.mPoint.y);
	    	editor.putFloat("pointvy", mGR.mPoint.vy);
	    	editor.putInt("pointi", mGR.mPoint.i);
	    }
	    
	    {
	    	editor.putBoolean("Ballsound", mGR.mBall.sound);
	    	editor.putBoolean("Balltappa", mGR.mBall.tappa);
	    	
	    	editor.putInt("Ballin", mGR.mBall.in);
	    	editor.putInt("Ballani", mGR.mBall.ani);
	    	editor.putInt("Balldir", mGR.mBall.dir);
	    	editor.putInt("Ballreset", mGR.mBall.reset);
	    	
	    	editor.putFloat("Ballx", mGR.mBall.x);
	    	editor.putFloat("Bally", mGR.mBall.y);
	    	editor.putFloat("Ballz", mGR.mBall.z);
	    	editor.putFloat("Ballvx", mGR.mBall.vx);
	    	editor.putFloat("Ballvy", mGR.mBall.vy);
	    	editor.putFloat("Ballvz", mGR.mBall.vz);
	    	editor.putFloat("Ballang", mGR.mBall.ang);
	    	editor.putFloat("Balls", mGR.mBall.s);
	    	editor.putFloat("Ballsy", mGR.mBall.sy);
	    }
	     
	    {
	    	editor.putInt("Keepersc", mGR.mKeeper.sc);
	    	editor.putInt("Keeperinc", mGR.mKeeper.inc);
	    	
	    	editor.putFloat("Keeperx", mGR.mKeeper.x);
	    	editor.putFloat("Keepery", mGR.mKeeper.y);
	    	editor.putFloat("Keeperex", mGR.mKeeper.ex);
	    	editor.putFloat("Keeperey", mGR.mKeeper.ey);
	    	editor.putFloat("Keepervx", mGR.mKeeper.vx);
	    }
	    for(int i=0;i<mGR.ani.length;i++)
	    {
	    	editor.putFloat("anix"+i, mGR.ani[i].x);
	    	editor.putFloat("aniy"+i, mGR.ani[i].y);
	    	editor.putFloat("anivx"+i, mGR.ani[i].vx);
	    	editor.putFloat("anivy"+i, mGR.ani[i].vy);
	    	editor.putFloat("aniscal"+i, mGR.ani[i].scal);
	    	editor.putFloat("anir"+i, mGR.ani[i].r);
	    	editor.putFloat("anig"+i, mGR.ani[i].g);
	    	editor.putFloat("anib"+i, mGR.ani[i].b);
	    }
	    for(int i=0;i<mGR.mTPoint.length;i++)
	    {
	    	editor.putFloat("mTPointx"+i, mGR.mTPoint[i].x);
	    	editor.putFloat("mTPointy"+i, mGR.mTPoint[i].y);
	    	editor.putInt("mTPointm"+i, mGR.mTPoint[i].m);
	    }
	    editor.putFloat("mNlvlx", mGR.mNlvl.x);
    	editor.putFloat("mNlvly", mGR.mNlvl.y);
    	editor.putInt("mNlvlm", mGR.mNlvl.m);
    	
    	editor.putLong("gametime", mGR.gametime);
		
    	editor.putInt("mScore", mGR.mScore);
    	editor.putInt("type", mGR.type);
    	editor.putInt("mLevel", mGR.mLevel);
    	editor.putInt("mGoal", mGR.mGoal);
    	editor.putInt("Target", mGR.Target);
    	for(int i =0 ;i<mGR.mHScore.length;i++)
    		editor.putInt("mHScore"+i, mGR.mHScore[i]);
    	
    	editor.putInt("TTimeinc", mGR.mTTime.inc);
    	editor.commit();
	}
	void resume()
	{
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = prefs.getInt("screen", M.GAMELOGO);
		M.setValue = prefs.getBoolean("setValue", M.setValue);
		
		
		

	    {
	    	mGR.mPoint.x = prefs.getFloat("pointx", mGR.mPoint.x);
	    	mGR.mPoint. y = prefs.getFloat("pointy", mGR.mPoint.y);
	    	mGR.mPoint.vy = prefs.getFloat("pointvy", mGR.mPoint.vy);
	    	mGR.mPoint.i = prefs.getInt("pointi", mGR.mPoint.i);
	    }
	    
	    {
	    	mGR.mBall.sound = prefs.getBoolean("Ballsound", mGR.mBall.sound);
	    	mGR.mBall.tappa = prefs.getBoolean("Balltappa", mGR.mBall.tappa);
	    	
	    	mGR.mBall.in = prefs.getInt("Ballin", mGR.mBall.in);
	    	mGR.mBall.ani = prefs.getInt("Ballani", mGR.mBall.ani);
	    	mGR.mBall.dir = prefs.getInt("Balldir", mGR.mBall.dir);
	    	mGR.mBall.reset = prefs.getInt("Ballreset", mGR.mBall.reset);
	    	
	    	mGR.mBall.x = prefs.getFloat("Ballx", mGR.mBall.x);
	    	mGR.mBall.y = prefs.getFloat("Bally", mGR.mBall.y);
	    	mGR.mBall.z = prefs.getFloat("Ballz", mGR.mBall.z);
	    	mGR.mBall.vx = prefs.getFloat("Ballvx", mGR.mBall.vx);
	    	mGR.mBall.vy = prefs.getFloat("Ballvy", mGR.mBall.vy);
	    	mGR.mBall.vz = prefs.getFloat("Ballvz", mGR.mBall.vz);
	    	mGR.mBall.ang = prefs.getFloat("Ballang", mGR.mBall.ang);
	    	mGR.mBall.s = prefs.getFloat("Balls", mGR.mBall.s);
	    	mGR.mBall.sy = prefs.getFloat("Ballsy", mGR.mBall.sy);
	    }
	     
	    {
	    	mGR.mKeeper.sc = prefs.getInt("Keepersc", mGR.mKeeper.sc);
	    	mGR.mKeeper.inc = prefs.getInt("Keeperinc", mGR.mKeeper.inc);
	    	
	    	mGR.mKeeper.x = prefs.getFloat("Keeperx", mGR.mKeeper.x);
	    	mGR.mKeeper.y = prefs.getFloat("Keepery", mGR.mKeeper.y);
	    	mGR.mKeeper.ex = prefs.getFloat("Keeperex", mGR.mKeeper.ex);
	    	mGR.mKeeper.ey = prefs.getFloat("Keeperey", mGR.mKeeper.ey);
	    	mGR.mKeeper.vx = prefs.getFloat("Keepervx", mGR.mKeeper.vx);
	    }
	    for(int i=0;i<mGR.ani.length;i++)
	    {
	    	mGR.ani[i].x = prefs.getFloat("anix"+i, mGR.ani[i].x);
	    	mGR.ani[i].y = prefs.getFloat("aniy"+i, mGR.ani[i].y);
	    	mGR.ani[i].vx = prefs.getFloat("anivx"+i, mGR.ani[i].vx);
	    	mGR.ani[i].vy = prefs.getFloat("anivy"+i, mGR.ani[i].vy);
	    	mGR.ani[i].scal = prefs.getFloat("aniscal"+i, mGR.ani[i].scal);
	    	mGR.ani[i].r = prefs.getFloat("anir"+i, mGR.ani[i].r);
	    	mGR.ani[i].g = prefs.getFloat("anig"+i, mGR.ani[i].g);
	    	mGR.ani[i].b = prefs.getFloat("anib"+i, mGR.ani[i].b);
	    }
	    for(int i=0;i<mGR.mTPoint.length;i++)
	    {
	    	mGR.mTPoint[i].x = prefs.getFloat("mTPointx"+i, mGR.mTPoint[i].x);
	    	mGR.mTPoint[i].y = prefs.getFloat("mTPointy"+i, mGR.mTPoint[i].y);
	    	mGR.mTPoint[i].m = prefs.getInt("mTPointm"+i, mGR.mTPoint[i].m);
	    }
	    mGR.mNlvl.x = prefs.getFloat("mNlvlx", mGR.mNlvl.x);
	    mGR.mNlvl.y = prefs.getFloat("mNlvly", mGR.mNlvl.y);
	    mGR.mNlvl.m = prefs.getInt("mNlvlm", mGR.mNlvl.m);
    	
	    mGR.gametime = prefs.getLong("gametime", mGR.gametime);
		
	    mGR.mScore = prefs.getInt("mScore", mGR.mScore);
	    mGR.type = prefs.getInt("type", mGR.type);
	    mGR.mLevel = prefs.getInt("mLevel", mGR.mLevel);
	    mGR.mGoal = prefs.getInt("mGoal", mGR.mGoal);
	    mGR.Target = prefs.getInt("Target", mGR.Target);
    	for(int i =0 ;i<mGR.mHScore.length;i++)
    		mGR.mHScore[i] = prefs.getInt("mHScore"+i, mGR.mHScore[i]);
    	mGR.mTTime.inc = prefs.getInt("TTimeinc", mGR.mTTime.inc);
	}
	void Exit()
	{
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Do you want to Exit??")
	    .setPositiveButton("Rate",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
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

	public void Submitscore() {
		int ID = R.string.leaderboard_time_score;
		switch (mGR.type) {
		case 0:ID = R.string.leaderboard_time_score;break;
		case 1:ID = R.string.leaderboard_arcade;break;
		case 2:ID = R.string.leaderboard_goal_post;break;
		case 3:ID = R.string.leaderboard_tournament;break;
		case 4:ID = R.string.leaderboard_1_ball_shoot;break;
		case 5:ID = R.string.leaderboard_target_shoot;break;
		}
		if (!isSignedIn()) { beginUserInitiatedSignIn();} 
		else {
			Games.Leaderboards.submitScore(getApiClient(), getString(ID), mGR.mHScore[mGR.type]);
		}
	}

	int RC_UNUSED = 5001;

	// @Override
	public void onShowLeaderboardsRequested() {
		if (isSignedIn()) {
			startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(getApiClient()),RC_UNUSED);
		} else {
			beginUserInitiatedSignIn();
			// showAlert(getString(R.string.leaderboard_score));
		}
	}
	
	void loadInter(){
		if (!interstitialAd.isLoaded()) {
			AdRequest adRequest = new AdRequest.Builder()
//					.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//					.addTestDevice("67701763FB847AEBD3C6EE486475ED94")
			.build();
			interstitialAd.loadAd(adRequest);
			interstitialAd.setAdListener(new ToastAdListener(this));
		}
	}

	public void show() {
		try{handler.sendEmptyMessage(0);}catch(Exception e){}
	}
	Handler handler = new Handler() {public void handleMessage(Message msg) {show_ads();}};

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
}