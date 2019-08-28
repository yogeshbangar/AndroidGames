package com.hututu.game.bubbleboomblast;

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

public class Start extends BaseGameActivity
{
	int _keyCode = 0;
	AdView adView = null;
	AdView adHouse = null;//AdHouse
	GameRenderer mGR = null;
	private InterstitialAd interstitialAd;
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
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);//OnlyOneChange
		CONTEXT	=	this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId(getString(R.string.INTERSTITIALID));
		WindowManager mWinMgr = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
		M.ScreenWidth =mWinMgr.getDefaultDisplay().getWidth();
		M.ScreenHieght=mWinMgr.getDefaultDisplay().getHeight();
		mGR=new GameRenderer(this);
	    VortexView glSurface=(VortexView) findViewById(R.id.vortexview); // use the xml to set the view
	    glSurface.setRenderer(mGR);
	    glSurface.showRenderer(mGR);
	    if(!getSharedPreferences("X", MODE_PRIVATE).getBoolean("addFree", mGR.addFree))
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
			if(M.GameScreen != M.GAMEMENU)
				M.GameScreen = M.GAMEMENU;
			else
				Exit();
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
	public void onDestroy() {
		super.onDestroy();
		if(mGR.mMainActivity!=null)
			mGR.mMainActivity.onDestroy();
	}
	void pause()
	{
		mGR.resumeCounter = 0;
		M.stop(GameRenderer.mContext);
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
	    Editor editor = prefs.edit();
	    editor.putInt("screen", M.GameScreen);
	    editor.putBoolean("setValue", M.setValue);
	    
	    editor.putBoolean("addFree", mGR.addFree);
	    editor.putBoolean("SingUpadate", mGR.SingUpadate);
	    for(int i=0;i<mGR.Achi.length;i++)
	    	editor.putBoolean("Achi"+i, mGR.Achi[i]);
	    

	    
	    editor.putInt("mPage", mGR.mPage);
	    editor.putInt("mScore", mGR.mScore);
	    editor.putInt("mHScore", mGR.mHScore);
	    editor.putInt("mLevel", mGR.mLevel);
	    editor.putInt("mULevel", mGR.mULevel);
	    editor.putInt("mrBomb", mGR.mrBomb);
	    editor.putInt("mPopedBall", mGR.mPopedBall);
	    editor.putInt("mTargetBall", mGR.mTargetBall);
	    for(int i=0;i<mGR.BestScore.length;i++)
	    	editor.putInt("BestScore"+i, mGR.BestScore[i]);
	    editor.putInt("NumberOfBall", mGR.NumberOfBall);
	    editor.putInt("mTotalBall", mGR.mTotalBall);
	    editor.putInt("BlastCount", mGR.BlastCount);
	    editor.putInt("fireCount", mGR.fireCount);
	    
	    for(byte i = 0;i<mGR.mArr.length;i++)
		{
			for(byte j = 0;j<mGR.mArr[0].length;j++){
				editor.putInt(i+"mArrc"+j, mGR.mArr[i][j].col);
				editor.putInt(i+"mArrv"+j, mGR.mArr[i][j].val);
			}
		}
	    for(byte i = 0;i<mGR.mBall.length;i++)
	    {
	    	editor.putFloat("Ballx"+i, mGR.mBall[i].x);
	    	editor.putFloat("Bally"+i, mGR.mBall[i].y);
	    	editor.putFloat("Ballvx"+i, mGR.mBall[i].vx);
	    	editor.putFloat("Ballvy"+i, mGR.mBall[i].vy);
	    	editor.putFloat("Balla"+i, mGR.mBall[i].ang);
	    	editor.putInt("Ballg"+i, mGR.mBall[i].gayab);
	    	editor.putInt("Balls"+i, mGR.mBall[i].SolidCount);
	    }
	    editor.putBoolean("isReady", mGR.isReady);
	    editor.putInt("Leave", mGR.Leave);
	    editor.putInt("mul", mGR.mul);
	    editor.putInt("mulCount", M.mulCount);
	    
	    for(byte i = 0;i<mGR.mBall.length;i++)
	    {
	    	editor.putFloat("Blastx"+i, mGR.mBlast[i].x);
	    	editor.putFloat("Blasty"+i, mGR.mBlast[i].y);
	    	editor.putInt("Blasts"+i, mGR.mBlast[i].blast);
	    }
	    
	    for(byte i = 0;i<mGR.mB11.length;i++)
	    {
	    	editor.putFloat("B11x"+i, mGR.mB11[i].x);
	    	editor.putFloat("B11y"+i, mGR.mB11[i].y);
	    	editor.putInt("B11s"+i, mGR.mB11[i].blast);
	    }
	    
	    for(byte i = 0;i<mGR.mArrow.length;i++)
	    {
	    	editor.putFloat("Arrowx"+i, mGR.mArrow[i].x);
	    	editor.putFloat("Arrowy"+i, mGR.mArrow[i].y);
	    	editor.putInt("Arrows"+i, mGR.mArrow[i].dir);
	    }
	    for(byte i = 0;i<mGR.mBomb.length;i++)
	    {
	    	editor.putFloat("Bombx"+i, mGR.mBomb[i].x);
	    	editor.putFloat("Bomby"+i, mGR.mBomb[i].y);
	    	editor.putInt("Bombs"+i, mGR.mBomb[i].dir);
	    }
	
	    
	    editor.commit();
	}
	void resume()
	{
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = prefs.getInt("screen", M.GAMELOGO);
		M.setValue = prefs.getBoolean("setValue", M.setValue);
		
		
		mGR.addFree = prefs.getBoolean("addFree", mGR.addFree);
		mGR.SingUpadate = prefs.getBoolean("SingUpadate", mGR.SingUpadate);
	    for(int i=0;i<mGR.Achi.length;i++)
	    	mGR.Achi[i] = prefs.getBoolean("Achi"+i, mGR.Achi[i]);
	    

	    
	    mGR.mPage = prefs.getInt("mPage", mGR.mPage);
	    mGR.mScore = prefs.getInt("mScore", mGR.mScore);
	    mGR.mHScore = prefs.getInt("mHScore", mGR.mHScore);
	    mGR.mLevel = prefs.getInt("mLevel", mGR.mLevel);
	    mGR.mULevel = prefs.getInt("mULevel", mGR.mULevel);
	    mGR.mrBomb = prefs.getInt("mrBomb", mGR.mrBomb);
	    mGR.mPopedBall = prefs.getInt("mPopedBall", mGR.mPopedBall);
	    mGR.mTargetBall = prefs.getInt("mTargetBall", mGR.mTargetBall);
	    for(int i=0;i<mGR.BestScore.length;i++)
	    	mGR.BestScore[i] = prefs.getInt("BestScore"+i, mGR.BestScore[i]);
	    mGR.NumberOfBall = prefs.getInt("NumberOfBall", mGR.NumberOfBall);
	    mGR.mTotalBall = prefs.getInt("mTotalBall", mGR.mTotalBall);
	    mGR.BlastCount = prefs.getInt("BlastCount", mGR.BlastCount);
	    mGR.fireCount = prefs.getInt("fireCount", mGR.fireCount);
	    
	    for(byte i = 0;i<mGR.mArr.length;i++)
		{
			for(byte j = 0;j<mGR.mArr[0].length;j++){
				mGR.mArr[i][j].col = (byte)prefs.getInt(i+"mArrc"+j, mGR.mArr[i][j].col);
				mGR.mArr[i][j].val = (byte)prefs.getInt(i+"mArrv"+j, mGR.mArr[i][j].val);
			}
		}
	    for(byte i = 0;i<mGR.mBall.length;i++)
	    {
	    	mGR.mBall[i].x = prefs.getFloat("Ballx"+i, mGR.mBall[i].x);
	    	mGR.mBall[i].y = prefs.getFloat("Bally"+i, mGR.mBall[i].y);
	    	mGR.mBall[i].vx = prefs.getFloat("Ballvx"+i, mGR.mBall[i].vx);
	    	mGR.mBall[i].vy = prefs.getFloat("Ballvy"+i, mGR.mBall[i].vy);
	    	mGR.mBall[i].ang = prefs.getFloat("Balla"+i, mGR.mBall[i].ang);
	    	mGR.mBall[i].gayab= prefs.getInt("Ballg"+i, mGR.mBall[i].gayab);
	    	mGR.mBall[i].SolidCount= prefs.getInt("Balls"+i, mGR.mBall[i].SolidCount);
	    }
	    mGR.isReady = prefs.getBoolean("isReady", mGR.isReady);
	    mGR.Leave = prefs.getInt("Leave", mGR.Leave);
	    mGR.mul = prefs.getInt("mul", mGR.mul);
	    M.mulCount = prefs.getInt("mulCount", M.mulCount);
	    
	    for(byte i = 0;i<mGR.mBall.length;i++)
	    {
	    	mGR.mBlast[i].x = prefs.getFloat("Blastx"+i, mGR.mBlast[i].x);
	    	mGR.mBlast[i].y = prefs.getFloat("Blasty"+i, mGR.mBlast[i].y);
	    	mGR.mBlast[i].blast = prefs.getInt("Blasts"+i, mGR.mBlast[i].blast);
	    }
	    
	    for(byte i = 0;i<mGR.mB11.length;i++)
	    {
	    	mGR.mB11[i].x = prefs.getFloat("B11x"+i, mGR.mB11[i].x);
	    	mGR.mB11[i].y = prefs.getFloat("B11y"+i, mGR.mB11[i].y);
	    	mGR.mB11[i].blast = prefs.getInt("B11s"+i, mGR.mB11[i].blast);
	    }
	    
	    for(byte i = 0;i<mGR.mArrow.length;i++)
	    {
	    	mGR.mArrow[i].x = prefs.getFloat("Arrowx"+i, mGR.mArrow[i].x);
	    	mGR.mArrow[i].y = prefs.getFloat("Arrowy"+i, mGR.mArrow[i].y);
	    	mGR.mArrow[i].dir = (byte)prefs.getInt("Arrows"+i, mGR.mArrow[i].dir);
	    }
	    for(byte i = 0;i<mGR.mBomb.length;i++)
	    {
	    	mGR.mBomb[i].x = prefs.getFloat("Bombx"+i, mGR.mBomb[i].x);
	    	mGR.mBomb[i].y = prefs.getFloat("Bomby"+i, mGR.mBomb[i].y);
	    	mGR.mBomb[i].dir = (byte)prefs.getInt("Bombs"+i, mGR.mBomb[i].dir);
	    }
	
		
		
	    mGR.resumeCounter = 0;
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

	void buy() {
		new AlertDialog.Builder(this)
				.setIcon(R.drawable.icon).setTitle("You don't have enough coins.").setPositiveButton("Buy",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								mGR.mMainActivity.onBuyGold50000(null);
							}
						})
				.setNegativeButton("Later",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
							}
						}).show();
	}
	
	
	void Join() {
		new AlertDialog.Builder(this)
				.setIcon(R.drawable.icon).setTitle("Challenge your friends.").setPositiveButton("Join",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								beginUserInitiatedSignIn();
							}
						})
				.setNegativeButton("Later",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
							}
						}).show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (!mGR.mMainActivity.mHelper.handleActivityResult(requestCode,resultCode, data)) {
			super.onActivityResult(requestCode, resultCode, data);
		} else {
		}
	}
	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		if(!mGR.SingUpadate)
		{
			for(int i=0;i<mGR.Achi.length;i++)
			{
				if(mGR.Achi[i])
				{
					UnlockAchievement(mGR.root.achiment[i]);
				}
			}
//			Submitscore(R.string.leaderboard_score);
			mGR.SingUpadate = true;
		}
	}
	public void Submitscore(final int ID,int total) {
    	System.out.println("~~~~~~~~~~~~Submitscore~~~~~~~~~~");
    	if (isSignedIn())
    		Games.Leaderboards.submitScore(getApiClient(), getString(ID), total);
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

	public void UnlockAchievement(final int ID) {
		try {
			if (!isSignedIn()) {
//			beginUserInitiatedSignIn();
		} else {
			if (isSignedIn()) {
				Games.Achievements.unlock(getApiClient(), getString(ID));
			}

		}
	} catch (Exception e) {}
	}
	public void onShowAchievementsRequested() {
		try {
			 if (isSignedIn()) {
		            startActivityForResult(Games.Achievements.getAchievementsIntent(getApiClient()),RC_UNUSED);
		        } else {
		        	beginUserInitiatedSignIn();
		        }
		} catch (Exception e) {}
	}
	void loadInter(){
		if (!interstitialAd.isLoaded() && !mGR.addFree) {
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
	Handler handler = new Handler() {public void handleMessage(Message msg) {show_Ads();}};

	void show_Ads() {
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