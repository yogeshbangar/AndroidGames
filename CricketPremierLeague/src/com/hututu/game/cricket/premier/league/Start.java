package com.hututu.game.cricket.premier.league;

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
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class Start extends BaseGameActivity 
{
	int _keyCode = 0;
	AdView adView = null;
	GameRenderer mGR = null;
	private static Context CONTEXT;
	private InterstitialAd interstitial;
	boolean isCreate = false;
	void callAdds()
	{
		//AdSize.MEDIUM_RECTANGLE
		adView = (AdView) this.findViewById(R.id.addgame);
		AdRequest adRequest = new AdRequest.Builder()
//		.addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("67701763FB847AEBD3C6EE486475ED94")
		.build();
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
//		System.out.println("creat 1 ");
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);//OnlyOneChange
		CONTEXT	=	this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		interstitial = new InterstitialAd(this);
		interstitial.setAdUnitId(getString(R.string.INTERSTITIAL_ID));
	    WindowManager mWinMgr = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
		M.ScreenWidth =mWinMgr.getDefaultDisplay().getWidth();
		M.ScreenHieght=mWinMgr.getDefaultDisplay().getHeight();
		mGR=new GameRenderer(this);
	    VortexView glSurface=(VortexView) findViewById(R.id.vortexview); // use the xml to set the view
	    glSurface.setRenderer(mGR);
	    glSurface.showRenderer(mGR);
	    if(!getSharedPreferences("X", MODE_PRIVATE).getBoolean("addFree", mGR.addFree)){
		    callAdds();
		    load();
	    }
	    isCreate = true;
//	    System.out.println("creat 0 ");
	}
	public static Context getContext() {
	        return CONTEXT;
	}
	@Override 
	public void onPause () {
//		System.out.println("Pause 0 ");
		
		M.stop();
		pause();
		super.onPause();
//		System.out.println("Pause 1 ");
	}
	@Override 
	public void onResume() {      
//		System.out.println("onResume 0 ");
		resume();
		super.onResume();
//		System.out.println("onResume 1 ");
		//view.onResume();
	}
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			switch (M.GameScreen) {
			case M.GAMESERIES:
				Exit();
				break;
			case M.GAMETEAMSEL:
				M.GameScreen = M.GAMESERIES;
				break;
			case M.TEAMDETAIL:
				M.GameScreen = M.GAMESERIES;
				break;
			case M.GAMECAPTAIN:
				M.GameScreen = M.GAMESERIES;
				break;
			case M.TEAMINFO:
				M.GameScreen = M.GAMESERIES;
				break;
			case M.GAMEPLAY:
				M.GameScreen = M.PAUSE;
				M.stop();
				break;
			case M.BUYCOIN:
				M.GameScreen = M.GAMESERIES;
				break;
			case M.ABOUT:
				M.GameScreen = M.GAMESERIES;
				break;
			case M.SINGLEMATCH:
				M.GameScreen = M.GAMESERIES;
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
		if(mGR.mInApp!=null)
			mGR.mInApp.onDestroy();
	}
	void pause()
	{
		if(M.GameScreen == M.GAMEPLAY)
			M.GameScreen = M.PAUSE;
		M.stop();
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putInt("screen", M.GameScreen);
		editor.putBoolean("setValue", M.setValue);
		
		editor.putBoolean("addFree", mGR.addFree);
		editor.putBoolean("SingUpadate", mGR.SingUpadate);
		for(int i=0;i<mGR.mAchiUnlock.length;i++)
			editor.putBoolean("mAchiUnlock"+i, mGR.mAchiUnlock[i]);
		
		
		editor.putInt("mtBall", M.tBall);
		editor.putInt("SIX", M.T_SIX);
		editor.putInt("FOUR", M.T_FOUR);
		editor.putInt("TOTAL", M.TOTAL);
		
		for (int i = 0; i < mGR.TeamSelect.length; i++)
			editor.putBoolean("TeamSelect" + i, mGR.TeamSelect[i]);

		editor.putInt("popUp", mGR.popUp);
		for (int i = 0; i < mGR.Pos.length; i++)
			for (int j = 0; j < mGR.Pos[i].length; j++)
				editor.putInt(i + "Pos" + j, mGR.Pos[i][j]);

		for (int i = 0; i < mGR.Over.length; i++)
			editor.putInt(i + "Over", mGR.Over[i]);

		editor.putInt("Serise", mGR.Serise);
		editor.putInt("Formove", mGR.Formove);
		editor.putInt("type", mGR.type);
		editor.putInt("mGold", mGR.mGold);

		for (int i = 0; i < mGR.round.length; i++)
			editor.putInt(i + "round", mGR.round[i]);
		for (int i = 0; i < mGR.Hcol.length; i++)
			editor.putInt(i + "Hcol", mGR.Hcol[i]);
		for (int i = 0; i < mGR.Scol.length; i++)
			editor.putInt(i + "Scol", mGR.Scol[i]);
		for (int i = 0; i < mGR.tS6.length; i++)
			editor.putInt(i + "tS6", mGR.tS6[i]);
		for (int i = 0; i < mGR.tS4.length; i++)
			editor.putInt(i + "tS4", mGR.tS4[i]);
		for (int i = 0; i < mGR.Year.length; i++)
			editor.putInt(i + "Year", mGR.Year[i]);

		for (int i = 0; i < mGR.mBatTeam.length; i++) {
			editor.putBoolean("mBatTeamistwo" + i, mGR.mBatTeam[i].isTwo);

			editor.putInt("mBatTeamno" + i, mGR.mBatTeam[i].no);
			editor.putInt("mBatTeamp1" + i, mGR.mBatTeam[i].p1);
			editor.putInt("mBatTeamp2" + i, mGR.mBatTeam[i].p2);
			editor.putInt("mBatTeamRun" + i, mGR.mBatTeam[i].Run);
			editor.putInt("mBatTeamball" + i, mGR.mBatTeam[i].ball);
			editor.putInt("mBatTeamfalWicket" + i, mGR.mBatTeam[i].falWicket);
			editor.putInt("mBatTeams4" + i, mGR.mBatTeam[i].s4);
			editor.putInt("mBatTeams6" + i, mGR.mBatTeam[i].s6);

			for (int j = 0; j < mGR.mBatTeam[i].mPly.length; j++) {
				editor.putInt(j + "mBmPlyOutBy" + i, mGR.mBatTeam[i].mPly[j].OutBy);
				editor.putInt(j + "mBmPlyHowOut" + i, mGR.mBatTeam[i].mPly[j].HowOut);
				editor.putInt(j + "mBmPlyfallOff" + i, mGR.mBatTeam[i].mPly[j].fallOff);
				editor.putInt(j + "mBmPlys4" + i, mGR.mBatTeam[i].mPly[j].s4);
				editor.putInt(j + "mBmPlys6" + i, mGR.mBatTeam[i].mPly[j].s6);
				editor.putInt(j + "mBmPlyrun" + i, mGR.mBatTeam[i].mPly[j].run);
				editor.putInt(j + "mBmPlyBall" + i, mGR.mBatTeam[i].mPly[j].Ball);
			}
		}

		for (int i = 0; i < mGR.mBalTeam.length; i++) {
			editor.putBoolean("mBalTeamistwo" + i, mGR.mBalTeam[i].isTwo);

			editor.putInt("mBalTeamno" + i, mGR.mBalTeam[i].no);
			editor.putInt("mBalTeamp1" + i, mGR.mBalTeam[i].p1);
			editor.putInt("mBalTeamp2" + i, mGR.mBalTeam[i].p2);
			editor.putInt("mBalTeamRun" + i, mGR.mBalTeam[i].Run);
			editor.putInt("mBalTeamball" + i, mGR.mBalTeam[i].ball);
			editor.putInt("mBalTeamfalWicket" + i, mGR.mBalTeam[i].falWicket);
			editor.putInt("mBalTeams4" + i, mGR.mBalTeam[i].s4);
			editor.putInt("mBalTeams6" + i, mGR.mBalTeam[i].s6);

			for (int j = 0; j < mGR.mBalTeam[i].mPly.length; j++) {
				editor.putInt(j + "lBlPlyOutBy" + i, mGR.mBalTeam[i].mPly[j].OutBy);
				editor.putInt(j + "lBlPlyHowOut" + i, mGR.mBalTeam[i].mPly[j].HowOut);
				editor.putInt(j + "lBlPlyfallOff" + i, mGR.mBalTeam[i].mPly[j].fallOff);
				editor.putInt(j + "lBlPlys4" + i, mGR.mBalTeam[i].mPly[j].s4);
				editor.putInt(j + "lBlPlys6" + i, mGR.mBalTeam[i].mPly[j].s6);
				editor.putInt(j + "lBlPlyrun" + i, mGR.mBalTeam[i].mPly[j].run);
				editor.putInt(j + "lBlPlyBall" + i, mGR.mBalTeam[i].mPly[j].Ball);
			}
		}
		for (int i = 0; i < mGR.mTeamInfo.length; i++) {
			for (int j = 0; j < mGR.mTeamInfo[i].length; j++) {
				editor.putInt(j + "mTeamInfowin" + i, mGR.mTeamInfo[i][j].win);
				editor.putInt(j + "mTeamInfodraw" + i, mGR.mTeamInfo[i][j].draw);
				editor.putInt(j + "mTeamInfoloose" + i, mGR.mTeamInfo[i][j].loose);
				editor.putInt(j + "mTeamInfopld" + i, mGR.mTeamInfo[i][j].pld);
				editor.putInt(j + "mTeamInfopoint" + i, mGR.mTeamInfo[i][j].point);
			}
		}

		for (int i = 0; i < mGR.mBall.length; i++) {
			editor.putBoolean("mBallgoSedo" + i, mGR.mBall[i].goSedo);

			editor.putInt("mBalltye" + i, mGR.mBall[i].tye);
			editor.putInt("mBallhit" + i, mGR.mBall[i].hit);
			editor.putInt("mBallRun" + i, mGR.mBall[i].Run);
			editor.putInt("mBallRunTran" + i, mGR.mBall[i].RunTran);

			editor.putFloat("mBallx" + i, mGR.mBall[i].x);
			editor.putFloat("mBally" + i, mGR.mBall[i].y);
			editor.putFloat("mBallz" + i, mGR.mBall[i].z);
			editor.putFloat("mBallvx" + i, mGR.mBall[i].vx);
			editor.putFloat("mBallvy" + i, mGR.mBall[i].vy);
			editor.putFloat("mBallvz" + i, mGR.mBall[i].vz);
			editor.putFloat("mBallsedo" + i, mGR.mBall[i].sedo);

		}

		for (int i = 0; i < mGR.mGuli.length; i++) {
			editor.putFloat("mGulix" + i, mGR.mGuli[i].x);
			editor.putFloat("mGuliy" + i, mGR.mGuli[i].y);
			editor.putFloat("mGulivx" + i, mGR.mGuli[i].vx);
			editor.putFloat("mGulivy" + i, mGR.mGuli[i].vy);
		}
		{
			editor.putBoolean("mBatsmanisClick", mGR.mBatsman.isClick);
			editor.putInt("mBatsmanhit", mGR.mBatsman.hit);
			editor.putInt("mBatsmanimg", mGR.mBatsman.img);
		}

		editor.commit();
	}

	void resume() {
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = (byte) prefs.getInt("screen", M.GAMELOGO);
		M.setValue = prefs.getBoolean("setValue", M.setValue);
		
		mGR.addFree = prefs.getBoolean("addFree", mGR.addFree);
		mGR.SingUpadate = prefs.getBoolean("SingUpadate", mGR.SingUpadate);
		for(int i=0;i<mGR.mAchiUnlock.length;i++)
			mGR.mAchiUnlock[i] = prefs.getBoolean("mAchiUnlock"+i, mGR.mAchiUnlock[i]);
		
		M.tBall = prefs.getInt("mtBall", 60);
		M.T_SIX = prefs.getInt("SIX", M.T_SIX);
		M.T_FOUR = prefs.getInt("FOUR", M.T_FOUR);
		M.TOTAL = prefs.getInt("TOTAL", M.TOTAL);
		
		for (int i = 0; i < mGR.TeamSelect.length; i++)
			mGR.TeamSelect[i] = prefs.getBoolean("TeamSelect" + i, false);

		mGR.popUp = (byte) prefs.getInt("popUp", 0);
		for (int i = 0; i < mGR.Pos.length; i++)
			for (int j = 0; j < mGR.Pos[i].length; j++)
				mGR.Pos[i][j] = (byte) prefs.getInt(i + "Pos" + j, 0);

		for (int i = 0; i < mGR.Over.length; i++)
			mGR.Over[i] = (byte) prefs.getInt(i + "Over", -1);

		mGR.Serise = prefs.getInt("Serise", 0);
		mGR.Formove = prefs.getInt("Formove", 0);
		mGR.type = prefs.getInt("type", 0);
		mGR.mGold = prefs.getInt("mGold", 200);

		for (int i = 0; i < mGR.round.length; i++)
			mGR.round[i] = prefs.getInt(i + "round", 0);
		for (int i = 0; i < mGR.Hcol.length; i++)
			mGR.Hcol[i] = prefs.getInt(i + "Hcol", 0);
		for (int i = 0; i < mGR.Scol.length; i++)
			mGR.Scol[i] = prefs.getInt(i + "Scol", 0);
		for (int i = 0; i < mGR.tS6.length; i++)
			mGR.tS6[i] = prefs.getInt(i + "tS6", 0);
		for (int i = 0; i < mGR.tS4.length; i++)
			mGR.tS4[i] = prefs.getInt(i + "tS4", 0);
		for (int i = 0; i < mGR.Year.length; i++)
			mGR.Year[i] = prefs.getInt(i + "Year", 0);

		for (int i = 0; i < mGR.mBatTeam.length; i++) {
			mGR.mBatTeam[i].isTwo = prefs.getBoolean("mBatTeamistwo" + i, false);

			mGR.mBatTeam[i].no = prefs.getInt("mBatTeamno" + i, 0);
			mGR.mBatTeam[i].p1 = prefs.getInt("mBatTeamp1" + i, 0);
			mGR.mBatTeam[i].p2 = prefs.getInt("mBatTeamp2" + i, 1);
			mGR.mBatTeam[i].Run = prefs.getInt("mBatTeamRun" + i, 0);
			mGR.mBatTeam[i].ball = prefs.getInt("mBatTeamball" + i, 0);
			mGR.mBatTeam[i].falWicket = prefs.getInt("mBatTeamfalWicket" + i, 0);
			mGR.mBatTeam[i].s4 = prefs.getInt("mBatTeams4" + i, 0);
			mGR.mBatTeam[i].s6 = prefs.getInt("mBatTeams6" + i, 0);

			for (int j = 0; j < mGR.mBatTeam[i].mPly.length; j++) {
				mGR.mBatTeam[i].mPly[j].OutBy = (byte) prefs.getInt(j+ "mBmPlyOutBy" + i, -1);
				mGR.mBatTeam[i].mPly[j].HowOut = (byte) prefs.getInt(j+ "mBmPlyHowOut" + i, -1);
				mGR.mBatTeam[i].mPly[j].fallOff = (byte) prefs.getInt(j+ "mBmPlyfallOff" + i, -1);
				mGR.mBatTeam[i].mPly[j].s4 = (short) prefs.getInt(j+ "mBmPlys4" + i, 0);
				mGR.mBatTeam[i].mPly[j].s6 = (short) prefs.getInt(j+ "mBmPlys6" + i, 0);
				mGR.mBatTeam[i].mPly[j].run = prefs.getInt(j + "mBmPlyrun" + i,0);
				mGR.mBatTeam[i].mPly[j].Ball = prefs.getInt(j + "mBmPlyBall"+ i, -1);
			}
		}

		for (int i = 0; i < mGR.mBalTeam.length; i++) {
			mGR.mBalTeam[i].isTwo = prefs.getBoolean("mBalTeamistwo" + i, false);
			mGR.mBalTeam[i].no = prefs.getInt("mBalTeamno" + i, 1);
			mGR.mBalTeam[i].p1 = prefs.getInt("mBalTeamp1" + i, 0);
			mGR.mBalTeam[i].p2 = prefs.getInt("mBalTeamp2" + i, 1);
			mGR.mBalTeam[i].Run = prefs.getInt("mBalTeamRun" + i, 0);
			mGR.mBalTeam[i].ball = prefs.getInt("mBalTeamball" + i, 0);
			mGR.mBalTeam[i].falWicket = prefs.getInt("mBalTeamfalWicket" + i, 0);
			mGR.mBalTeam[i].s4 = prefs.getInt("mBalTeams4" + i, 0);
			mGR.mBalTeam[i].s6 = prefs.getInt("mBalTeams6" + i, 0);

			for (int j = 0; j < mGR.mBalTeam[i].mPly.length; j++) {
				mGR.mBalTeam[i].mPly[j].OutBy = (byte) prefs.getInt(j+ "lBlPlyOutBy" + i, -1);
				mGR.mBalTeam[i].mPly[j].HowOut = (byte) prefs.getInt(j+ "lBlPlyHowOut" + i, -1);
				mGR.mBalTeam[i].mPly[j].fallOff = (byte) prefs.getInt(j+ "lBlPlyfallOff" + i, -1);
				mGR.mBalTeam[i].mPly[j].s4 = (short) prefs.getInt(j+ "lBlPlys4" + i, 0);
				mGR.mBalTeam[i].mPly[j].s6 = (short) prefs.getInt(j+ "lBlPlys6" + i, 0);
				mGR.mBalTeam[i].mPly[j].run = prefs.getInt(j + "lBlPlyrun" + i,0);
				mGR.mBalTeam[i].mPly[j].Ball = prefs.getInt(j + "lBlPlyBall"+ i, -1);
			}
		}
		for (int i = 0; i < mGR.mTeamInfo.length; i++) {
			for (int j = 0; j < mGR.mTeamInfo[i].length; j++) {
				mGR.mTeamInfo[i][j].win = prefs.getInt(j + "mTeamInfowin" + i,0);
				mGR.mTeamInfo[i][j].draw = prefs.getInt(j + "mTeamInfodraw" + i, 0);
				mGR.mTeamInfo[i][j].loose = prefs.getInt(j + "mTeamInfoloose"+ i, 0);
				mGR.mTeamInfo[i][j].pld = prefs.getInt(j + "mTeamInfopld" + i,0);
				mGR.mTeamInfo[i][j].point = prefs.getInt(j + "mTeamInfopoint"+ i, 0);
			}
		}

		for (int i = 0; i < mGR.mBall.length; i++) {
			mGR.mBall[i].goSedo = prefs.getBoolean("mBallgoSedo" + i, false);

			mGR.mBall[i].tye = (byte) prefs.getInt("mBalltye" + i, 0);
			mGR.mBall[i].hit = prefs.getInt("mBallhit" + i, 0);
			mGR.mBall[i].Run = prefs.getInt("mBallRun" + i, 0);
			mGR.mBall[i].RunTran = prefs.getInt("mBallRunTran" + i, 1000);

			mGR.mBall[i].x = prefs.getFloat("mBallx" + i, 10);
			mGR.mBall[i].y = prefs.getFloat("mBally" + i, 10);
			mGR.mBall[i].z = prefs.getFloat("mBallz" + i, 0);
			mGR.mBall[i].vx = prefs.getFloat("mBallvx" + i, 0);
			mGR.mBall[i].vy = prefs.getFloat("mBallvy" + i, 0);
			mGR.mBall[i].vz = prefs.getFloat("mBallvz" + i, 0);
			mGR.mBall[i].sedo = prefs.getFloat("mBallsedo" + i, 0);
		}

		for (int i = 0; i < mGR.mGuli.length; i++) {
			mGR.mGuli[i].x = prefs.getFloat("mGulix" + i, 10);
			mGR.mGuli[i].y = prefs.getFloat("mGuliy" + i, 10);
			mGR.mGuli[i].vx = prefs.getFloat("mGulivx" + i, 0);
			mGR.mGuli[i].vy = prefs.getFloat("mGulivy" + i, 0);
		}
		{
			mGR.mBatsman.isClick = prefs.getBoolean("mBatsmanisClick", false);
			mGR.mBatsman.hit = prefs.getInt("mBatsmanhit", 0);
			mGR.mBatsman.img = prefs.getInt("mBatsmanimg", 0);
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
	
	void Reaset()
	{
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Do you want to Reset?")
	    .setNeutralButton("No",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
      }}).setNegativeButton("Yes",new DialogInterface.OnClickListener(){public void onClick(DialogInterface dialog, int which) {
		    		   mGR.Year[mGR.Serise] =0;
      }}).show();
  }
	public void load() {
		try{handlerload.sendEmptyMessage(0);}catch(Exception e){}
	}
	Handler handlerload = new Handler() {public void handleMessage(Message msg) {loadInter();}};

	
	private void loadInter(){
		if (!interstitial.isLoaded() && !mGR.addFree) {
			AdRequest adRequest = new AdRequest.Builder()
//					.addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("67701763FB847AEBD3C6EE486475ED94")
			.build();
			interstitial.loadAd(adRequest);
			interstitial.setAdListener(new AdGameListener(this));
		}
	}

	public void ShowInterstitial() {
		if(!mGR.addFree)
			try{handler.sendEmptyMessage(0);}catch(Exception e){}
	}
	Handler handler = new Handler() {public void handleMessage(Message msg) {show();}};

	private void show() {
		try {
			if (interstitial != null && !mGR.addFree)
				if (interstitial.isLoaded()) {
					interstitial.show();
				}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		if (!mGR.SingUpadate) {
			for (int i = 0; i < mGR.mAchiUnlock.length; i++) {
				if (mGR.mAchiUnlock[i]) {
					UnlockAchievement(M.ACHIV[i]);
				}
			}
			GameRenderer.mStart.Submitscore(R.string.leaderboard_total_run, M.TOTAL);
			mGR.SingUpadate = true;
		}
	}
	public void Submitscore(final int ID,long total) {
		if (!isSignedIn()) {
//			beginUserInitiatedSignIn();
		} else { Games.Leaderboards.submitScore(getApiClient(), getString(ID), total);}
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
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (!mGR.mInApp.mHelper.handleActivityResult(requestCode,resultCode, data)) {
			super.onActivityResult(requestCode, resultCode, data);
		} else {
		}
	}
	void Achivment(){
		if(!mGR.mAchiUnlock[0]){
			for(int i =0;i<mGR.mBatTeam[mGR.Serise].mPly.length;i++){
				if(mGR.mBatTeam[mGR.Serise].mPly[i].run>99)
					mGR.mAchiUnlock[0] = true;	
			}
			if(mGR.mAchiUnlock[0])
				GameRenderer.mStart.UnlockAchievement(M.ACHIV[0]);
		}
		if(!mGR.mAchiUnlock[1]){
			for(int i =0;i<mGR.mBatTeam[mGR.Serise].mPly.length;i++){
				if(mGR.mBatTeam[mGR.Serise].mPly[i].run>49)
					mGR.mAchiUnlock[1] = true;	
			}
			if(mGR.mAchiUnlock[1])
				GameRenderer.mStart.UnlockAchievement(M.ACHIV[1]);
		}
		if(M.T_SIX > 99 && !mGR.mAchiUnlock[2]){
			mGR.mAchiUnlock[2] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[2]);
		}
		if(M.T_FOUR > 99 && !mGR.mAchiUnlock[3]){
			mGR.mAchiUnlock[3] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[3]);
		}
		if(mGR.mBatTeam[mGR.Serise].Run > 149 && !mGR.mAchiUnlock[4]){
			mGR.mAchiUnlock[4] = true;
			GameRenderer.mStart.UnlockAchievement(M.ACHIV[4]);
		}
		GameRenderer.mStart.Submitscore(R.string.leaderboard_total_run, M.TOTAL);
	}
	
}