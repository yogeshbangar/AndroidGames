package com.Oneday.games24.peguinadventure;
import java.util.List;
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
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class Start  extends BaseGameActivity{
	boolean addFree = false;
	boolean SingUpadate = false;
	int _keyCode = 0;

	AdView adHouse = null;//AdHouse
	GameRenderer mGR = null;
	private InterstitialAd interstitialAd;
	private static Context CONTEXT;

	@SuppressWarnings("deprecation")
	void callAdds() {
		
		
		adHouse = (AdView) this.findViewById(R.id.advhouse);
		AdRequest adRequest = new AdRequest.Builder().build();
		adHouse.loadAd(adRequest);
		adHouse.setAdListener(new AdListener() {
			public void onAdLoaded() {
				adHouse.bringToFront();
			}
		});
	}
	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) 
	{
		System.out.println("~~~~~~~~~~~~~~~~~~onCreate~~~~~~~~~~~~~~~~~~~~~");
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);//OnlyOneChange
		CONTEXT	=	this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId(getString(R.string.Interstitial));
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
//	boolean pause = false;
	@Override 
	public void onPause () {
		M.stop();
		System.out.println("~~~~~~~~~~~~~~~~~~pause~~~~~~~~~~~~~~~~~~~~~");
		
//		pause = true;
		pause();
		super.onPause();
	}
	@Override 
	public void onResume() {
		System.out.println("~~~~~~~~~~~~~~~~~~resume~~~~~~~~~~~~~~~~~~~~~");
		resume();
		
		super.onResume();
//		pause = false;
		//view.onResume();
	}
	
	@Override 
	public void onStart()
	{
		System.out.println("~~~~~~~~~~~~~~~~~~Start ~~~~~~~~~~~~~~~~~~~~~");
		super.onStart();
	}
	@Override 
	public void onDestroy() {
		System.out.println("~~~~~~~~~~~~~~~~~~Destroy~~~~~~~~~~~~~~~~~~~~~");
		super.onDestroy();
//		if(mGR.mMainActivity!=null)
//			mGR.mMainActivity.onDestroy();
	}
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		Log.d("----------------=>  "+keyCode,"   -----------    ");
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			switch (M.GameScreen) {
			case M.GAMEMAIN:
				Exit();
				break;
//			case M.GAMEONE:
//				M.GameScreen = M.GAMELVLPUSE;
//				mGR.mGameTime -= System.currentTimeMillis();
//				break;
//			case M.GAMETWO:
//				mGR.mLTwo.Draw_GamePlay(gl);
//				break;
//			case M.GAMETREE:
//				mGR.mLTree.Draw_GamePlay(gl);
//				break;
//			case M.GAMEFOUR:
//				mGR.mLFour.Draw_GamePlay(gl);
//				break;
//			case M.GAMEFIVE:
//				mGR.mLFive.Draw_GamePlay(gl);
//				break;
//			case M.GAMESIX:
//				mGR.mLsix.Draw_GamePlay(gl);
//				break;
//			case M.GAMESEVEN:
//				mGR.mLSeven.Draw_GamePlay(gl);
//				break;
//			case M.GAMEEIGHT:
//				mGR.mLEight.Draw_GamePlay(gl);
//				break;
//			case M.GAMENINE:
//				mGR.mLNine.Draw_GamePlay(gl);
//				break;
//			case M.GAMETEN:
//				mGR.mLTen.Draw_GamePlay(gl);
//				break;
//			case M.GAMEELEVEN:
//				mGR.mLEleven.Draw_GamePlay(gl);
//				break;
//			case M.GAMETWELVE:
//				mGR.mLTwelve.Draw_GamePlay(gl);
//				break;
//			case M.GAMETHIRTEEN:
//				mGR.mLThirteen.Draw_GamePlay(gl);
//				break;
			default:
				M.stop();
				mGR.mLSelect.calcStars();
				M.GameScreen = M.GAMEMAIN;
				M.play(GameRenderer.mContext, R.drawable.splesh);
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
	

	void pause()
	{
		if(M.GameScreen>0 && M.GameScreen<14){
			mGR.mGameTime -= System.currentTimeMillis();
			M.GameScreen = M.GAMELVLPUSE;
		}
		M.stop();
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
	    Editor editor = prefs.edit();
	    editor.putBoolean("addFree", addFree);
	    editor.putBoolean("SingUpadate", SingUpadate);
	    {
	    	editor.putBoolean("MsetValue", M.setValue);
	    	editor.putBoolean("MsBGValue", M.sBGValue);
	    	for(int i=0;i<M.ACHIVE.length;i++)
	    		for(int j=0;j<M.ACHIVE[i].length;j++)
	    			editor.putBoolean(i+"MACHIVE"+j, M.ACHIVE[i][j]);
	    	
	    	
	    	for(int i=0;i<M.HELPSCR.length;i++)
	    		editor.putBoolean(i+"MHELPSCR", M.HELPSCR[i]);
	    	
	    	editor.putInt("MGameScreen", M.GameScreen);
	    }
	    
	    editor.putBoolean("isPlay", mGR.isPlay);
//	    for(int i=0;i<mGR.Stars.length;i++)
//	    	editor.putInt("Stars"+i, mGR.Stars[i]);
		
	    editor.putInt("BuyStar", mGR.BuyStar);
	    editor.putInt("TStar", mGR.TStar);
	    editor.putInt("Clock", mGR.Clock);
	    editor.putInt("ClockCount", mGR.ClockCount);
	    editor.putInt("timesUp", mGR.timesUp);
	    editor.putInt("mScore", mGR.mScore);
	    
	    for(int i=0;i<mGR.BestScore.length;i++)
	    	editor.putInt("BestScore"+i, mGR.BestScore[i]);
	    for(int i=0;i<mGR.Total.length;i++)
	    	editor.putInt("Total"+i, mGR.Total[i]);
	    editor.putFloat("mRankFill", mGR.mRankFill);
	    editor.putLong("mGameTime", mGR.mGameTime);
	    
	    {
	    	for(int i=0;i<mGR.mLOne.arr.length;i++)
	    	{
	    		for(int j=0;j<mGR.mLOne.arr[i].length;j++)
	    		{
	    			editor.putInt(i+"Oarr"+j, mGR.mLOne.arr[i][j]);
	    		}
	    	}
	    	editor.putInt("Ocounter", mGR.mLOne.counter);
	    	editor.putInt("Om", mGR.mLOne.m);
	    	editor.putInt("On", mGR.mLOne.n);
	    	editor.putInt("Oani", mGR.mLOne.ani);
	    	editor.putInt("Oin", mGR.mLOne.in);
	    	editor.putInt("Oanicount", mGR.mLOne.anicount);
	    	editor.putFloat("Omx", mGR.mLOne.mx);
	    	editor.putFloat("Omy", mGR.mLOne.my);
	    }
	    {
	    	editor.putBoolean("T2fall", mGR.mLTwo.fall);
	    	
	    	editor.putInt("T2st", mGR.mLTwo.st);
	    	editor.putInt("T2Space", mGR.mLTwo.Space);
	    	editor.putInt("T2Counter", mGR.mLTwo.Counter);
	    	editor.putInt("T2wCount", mGR.mLTwo.wCount);
	    	editor.putInt("T2c1", mGR.mLTwo.c1);
	    	editor.putInt("T2jumpCount", mGR.mLTwo.jumpCount);
	    	
	    	
	    	editor.putFloat("T2Bx1", mGR.mLTwo.Bx1);
	    	editor.putFloat("T2Bx2", mGR.mLTwo.Bx2);
	    	editor.putFloat("T2move", mGR.mLTwo.move);
	    	editor.putFloat("T2dis", mGR.mLTwo.dis);
	    	editor.putFloat("T2px", mGR.mLTwo.px);
	    	editor.putFloat("T2py", mGR.mLTwo.py);
	    	editor.putFloat("T2pvy", mGR.mLTwo.pvy);
	    	
	    	for(int i =0;i<mGR.mLTwo.mStep.length;i++){
	    		editor.putBoolean("T2mStepon"+i, mGR.mLTwo.mStep[i].isOn);
	    		editor.putBoolean("T2mStepwa"+i, mGR.mLTwo.mStep[i].Watch);
	    		editor.putInt("T2mStepno"+i, mGR.mLTwo.mStep[i].no);
	    		editor.putFloat("T2mStepx"+i, mGR.mLTwo.mStep[i].x);
	    	}
	    }
	    {
	    	editor.putInt("T3in", mGR.mLTree.in);
	    	editor.putInt("T3counter", mGR.mLTree.counter);
	    	editor.putInt("T3counter", mGR.mLTree.counter);
	    	editor.putInt("T3ax", mGR.mLTree.ax);
	    	editor.putInt("T3ay", mGR.mLTree.ay);
	    	
	    	editor.putFloat("T3x", mGR.mLTree.x);
	    	editor.putFloat("T3y", mGR.mLTree.y);
	    	
	    	for(int i=0;i<mGR.mLTree.arr.length;i++)
	    	{
	    		for(int j=0;j<mGR.mLTree.arr[i].length;j++)
	    		{
	    			editor.putBoolean(i+"T3arrisOne"+j, mGR.mLTree.arr[i][j].isOne);
	    			editor.putBoolean(i+"T3arrex"+j, mGR.mLTree.arr[i][j].ex);
	    			editor.putInt(i+"T3arrcolor"+j, mGR.mLTree.arr[i][j].color);
	    			editor.putInt(i+"T3arrno"+j, mGR.mLTree.arr[i][j].no);
	    		}
	    	}
	    }
	    {
	    	editor.putInt("F4animCount", mGR.mLFour.animCount);
	    	editor.putInt("F4Counter", mGR.mLFour.Counter);
	    	editor.putInt("F4Counter2", mGR.mLFour.Counter2);
	    	editor.putInt("F4ballcount", mGR.mLFour.ballcount);
	    	editor.putInt("F4Watch", mGR.mLFour.Watch);
	    	editor.putFloat("F4dir", mGR.mLFour.dir);
	    	editor.putFloat("F4move", mGR.mLFour.move);
	    	editor.putFloat("F4mBGY", mGR.mLFour.mBGY);
	    	editor.putFloat("F4distance", mGR.mLFour.distance);
	    	for(int i=0;i<mGR.mLFour.mBall.length;i++)
	    	{
	    		editor.putBoolean("F4Balloff"+i, mGR.mLFour.mBall[i].off);
	    		editor.putInt("F4Ballno"+i, mGR.mLFour.mBall[i].no);
	    		editor.putFloat("F4Ballx"+i, mGR.mLFour.mBall[i].x);
	    		editor.putFloat("F4Bally"+i, mGR.mLFour.mBall[i].y);
	    		editor.putFloat("F4Ballvy"+i, mGR.mLFour.mBall[i].vy);
	    	}
	    	{
	    		editor.putBoolean("F4Playeroff", mGR.mLFour.Player.off);
	    		editor.putInt("F4Playerno", mGR.mLFour.Player.no);
	    		editor.putFloat("F4Playerx", mGR.mLFour.Player.x);
	    		editor.putFloat("F4Playery", mGR.mLFour.Player.y);
	    		editor.putFloat("F4Playervy", mGR.mLFour.Player.vy);
	    	}
	    	{
	    		editor.putBoolean("F4PMoveoff", mGR.mLFour.PMove.off);
	    		editor.putInt("F4PMoveno", mGR.mLFour.PMove.no);
	    		editor.putFloat("F4PMovex", mGR.mLFour.PMove.x);
	    		editor.putFloat("F4PMovey", mGR.mLFour.PMove.y);
	    		editor.putFloat("F4PMovevy", mGR.mLFour.PMove.vy);
	    	}
	    	for(int i=0;i<mGR.mLFour.mObject.length;i++){
	    		editor.putFloat("F4Objectx"+i, mGR.mLFour.mObject[i].x);
	    		editor.putFloat("F4Objecty"+i, mGR.mLFour.mObject[i].y);
	    		editor.putFloat("F4Objectvx"+i, mGR.mLFour.mObject[i].vx);
	    	}
	    }
	    {
	    	editor.putBoolean("F5isWrong", mGR.mLFive.isWrong);
	    	editor.putInt("F5counter", mGR.mLFive.counter);
	    	editor.putInt("F5game", mGR.mLFive.game);
	    	for(int i=0;i<mGR.mLFive.mVecter.length;i++)
	    	{
	    		editor.putBoolean("F5Voff"+i, mGR.mLFive.mVecter[i].off);
	    		editor.putInt("F5Vno"+i, mGR.mLFive.mVecter[i].no);
	    		editor.putFloat("F5Vx"+i, mGR.mLFive.mVecter[i].x);
	    		editor.putFloat("F5Vy"+i, mGR.mLFive.mVecter[i].y);
	    		editor.putFloat("F5Vvy"+i, mGR.mLFive.mVecter[i].vy);
	    	}
	    }
	    {
	    	editor.putBoolean("S6move", mGR.mLsix.move);
	    	editor.putInt("S6counter", mGR.mLsix.counter);
	    	editor.putInt("S6counter2", mGR.mLsix.counter2);
	    	for(int i=0;i<mGR.mLsix.mFruit.length;i++){
	    		editor.putInt("S6fn"+i, mGR.mLsix.mFruit[i].n);
	    		editor.putFloat("S6fx"+i, mGR.mLsix.mFruit[i].x);
	    		editor.putFloat("S6fy"+i, mGR.mLsix.mFruit[i].y);
	    		editor.putFloat("S6fvy"+i, mGR.mLsix.mFruit[i].vy);
	    		editor.putFloat("S6fvx"+i, mGR.mLsix.mFruit[i].vx);
	    	}
	    }
	    {
	    	editor.putInt("S7fall", mGR.mLSeven.fall);
	    	editor.putFloat("S7BGMove1", mGR.mLSeven.BGMove1);
	    	editor.putFloat("S7BGMove2", mGR.mLSeven.BGMove2);
	    	editor.putFloat("S7vx", mGR.mLSeven.vx);
	    	editor.putFloat("S7vy", mGR.mLSeven.vy);
	    	editor.putFloat("S7x", mGR.mLSeven.hx);
	    	editor.putFloat("S7y", mGR.mLSeven.hy);
	    	editor.putFloat("S7speed", mGR.mLSeven.speed);
	    	
	    	for(int i=0;i<mGR.mLSeven.mObj.length;i++){
	    		for(int j=0;j<mGR.mLSeven.mObj[i].obj.length;j++){
	    			editor.putInt(i+"S7OOj"+j, mGR.mLSeven.mObj[i].obj[j]);
	    		}
	    		editor.putInt(i+"S7Omove", mGR.mLSeven.mObj[i].move);
	    		editor.putFloat(i+"S7Ox", mGR.mLSeven.mObj[i].x);
	    		editor.putFloat(i+"S7Oy", mGR.mLSeven.mObj[i].y);
	    	}
		    {
	    		for(int j=0;j<mGR.mLSeven.mFall.obj.length;j++){
	    			editor.putInt("S7FOj"+j, mGR.mLSeven.mFall.obj[j]);
	    		}
	    		editor.putInt("S7Fmove", mGR.mLSeven.mFall.move);
	    		editor.putFloat("S7Fx", mGR.mLSeven.mFall.x);
	    		editor.putFloat("S7Fy", mGR.mLSeven.mFall.y);
	    	}
	    }
	    {
	    	for(int i=0;i<mGR.mLEight.candy.length;i++){
	    		for(int j=0;j<mGR.mLEight.candy[i].length;j++){
	    			editor.putInt(i+"E8can"+j, mGR.mLEight.candy[i][j]);
	    		}
	    	}
	    	editor.putInt("E8ext", mGR.mLEight.ext);
	    	{
	    		editor.putInt("E8EOi", mGR.mLEight.mEightObj.i);
	    		editor.putInt("E8EOj", mGR.mLEight.mEightObj.j);
	    		editor.putInt("E8EOco", mGR.mLEight.mEightObj.counter);
	    		
	    		editor.putFloat("E8EOx", mGR.mLEight.mEightObj.x);
	    		editor.putFloat("E8EOy", mGR.mLEight.mEightObj.y);
	    	}
	    	{
	    		editor.putBoolean("E8WRoff", mGR.mLEight.mWrong.off);
	    		
	    		editor.putInt("E8WRno", mGR.mLEight.mWrong.no);
	    		
	    		editor.putFloat("E8WRx", mGR.mLEight.mWrong.x);
	    		editor.putFloat("E8WRy", mGR.mLEight.mWrong.y);
	    		editor.putFloat("E8WRvy", mGR.mLEight.mWrong.vy);
	    	}
	    }
	    {
	    	
	    	
	    	editor.putBoolean("N9fall", mGR.mLNine.fall);
	    	editor.putInt("N9stepCount", mGR.mLNine.stepCount);
	    	editor.putInt("N9ano", mGR.mLNine.ano);
	    	editor.putInt("N9c1", mGR.mLNine.c1);
	    	editor.putFloat("N9Move", mGR.mLNine.Move);
	    	editor.putFloat("N9fx", mGR.mLNine.fx);
	    	editor.putFloat("N9fy", mGR.mLNine.fy);
	    	editor.putFloat("N9fvy", mGR.mLNine.fvy);
	    	for(int i=0;i<mGR.mLNine.mObj.length;i++){
	    		for(int j=0;j<mGR.mLNine.mObj[i].no.length;j++){
	    			editor.putInt(i+"N9Obno"+j, mGR.mLNine.mObj[i].no[j]);
	    		}
	    		editor.putInt(i+"N9Obtile", mGR.mLNine.mObj[i].tile);
	    		editor.putFloat(i+"N9Obx", mGR.mLNine.mObj[i].x);
	    		editor.putFloat(i+"N9Oby", mGR.mLNine.mObj[i].y);
	    	}
	    	{
	    		editor.putFloat("N9FRx", mGR.mLNine.mFrog.x);
	    		editor.putFloat("N9FRy", mGR.mLNine.mFrog.y);
	    		editor.putFloat("N9FRvx", mGR.mLNine.mFrog.vx);
	    		editor.putFloat("N9FRvy", mGR.mLNine.mFrog.vy);
	    		
	    	}
	    }
	    {
	    	editor.putInt("T10Counter", mGR.mLTen.Counter);
	    	editor.putInt("T10acc", mGR.mLTen.acc);
	    	editor.putFloat("T10Speed", mGR.mLTen.Speed);
	    	editor.putFloat("T10SpeedChange", mGR.mLTen.SpeedChange);
	    	editor.putFloat("T10fortDis", mGR.mLTen.fortDis);
	    	for(int i=0;i<mGR.mLTen.rx.length;i++)
	    		editor.putFloat(i+"T10rx", mGR.mLTen.rx[i]);
	    	for(int i=0;i<mGR.mLTen.bx.length;i++)
	    		editor.putFloat(i+"T10bx", mGR.mLTen.bx[i]);
	    	for(int i=0;i<mGR.mLTen.cx.length;i++)
	    		editor.putFloat(i+"T10cx", mGR.mLTen.cx[i]);
	    	for(int i=0;i<mGR.mLTen.mVecter.length;i++)
	    	{
	    		editor.putBoolean("T10Voff"+i, mGR.mLTen.mVecter[i].off);
	    		editor.putInt("T10Vno"+i, mGR.mLTen.mVecter[i].no);
	    		editor.putFloat("T10Vx"+i, mGR.mLTen.mVecter[i].x);
	    		editor.putFloat("T10Vy"+i, mGR.mLTen.mVecter[i].y);
	    		editor.putFloat("T10Vvy"+i, mGR.mLTen.mVecter[i].vy);
	    	}
	    	{
	    		editor.putInt("T10Plyno", mGR.mLTen.mPlayer.no);
	    		editor.putFloat("T10Plyx", mGR.mLTen.mPlayer.x);
	    		editor.putFloat("T10Plyy", mGR.mLTen.mPlayer.y);
	    		editor.putFloat("T10Plyvy", mGR.mLTen.mPlayer.vy);
	    	}
	    	
	    }
	    {
	    	editor.putInt("E11in", mGR.mLEleven.in);
	    	editor.putInt("E11counter", mGR.mLEleven.counter);
	    	editor.putFloat("E11x", mGR.mLEleven.x);
	    	editor.putFloat("E11y", mGR.mLEleven.y);
	    	for(int i=0;i<mGR.mLEleven.arr.length;i++){
	    		for(int j=0;j<mGR.mLEleven.arr[i].length;j++){
	    			editor.putBoolean(i+"E11arrit"+j, mGR.mLEleven.arr[i][j].isTuch);
		    		editor.putInt(i+"E11arrno"+j, mGR.mLEleven.arr[i][j].no);
	    		}
	    	}
	    }
	    {
	    	editor.putBoolean("T12fall", mGR.mLTwelve.fall);
	    	editor.putInt("T12counter", mGR.mLTwelve.counter);
	    	editor.putInt("T12C1", mGR.mLTwelve.C1);
	    	editor.putInt("T12C2", mGR.mLTwelve.C2);
	    	{

	    		editor.putBoolean("T12stoff", mGR.mLTwelve.mStep.off);
	    		editor.putInt("T12stno", mGR.mLTwelve.mStep.no);
	    		editor.putFloat("T12stx", mGR.mLTwelve.mStep.x);
	    		editor.putFloat("T12sty", mGR.mLTwelve.mStep.y);
	    		editor.putFloat("T12stvy", mGR.mLTwelve.mStep.vy);
	    	}
	    	
	    	for(int i=0;i<mGR.mLTwelve.Woter.length;i++)
	    	{
	    		editor.putBoolean("T12wooff"+i, mGR.mLTwelve.Woter[i].off);
	    		editor.putInt("T12wono"+i, mGR.mLTwelve.Woter[i].no);
	    		editor.putFloat("T12wox"+i, mGR.mLTwelve.Woter[i].x);
	    		editor.putFloat("T12woy"+i, mGR.mLTwelve.Woter[i].y);
	    		editor.putFloat("T12wovy"+i, mGR.mLTwelve.Woter[i].vy);
	    	}
	    	for(int i=0;i<mGR.mLTwelve.mCandy.length;i++)
	    	{
	    		editor.putBoolean("T12Canwoter"+i, mGR.mLTwelve.mCandy[i].woter);
	    		editor.putBoolean("T12Cancoliition"+i, mGR.mLTwelve.mCandy[i].coliition);
	    		editor.putInt("T12Cancandy"+i, mGR.mLTwelve.mCandy[i].candy);
	    		editor.putInt("T12Canc"+i, mGR.mLTwelve.mCandy[i].c);
	    		editor.putFloat("T12Canx"+i, mGR.mLTwelve.mCandy[i].x);
	    		editor.putFloat("T12Cany"+i, mGR.mLTwelve.mCandy[i].y);
	    		editor.putFloat("T12Canvx"+i, mGR.mLTwelve.mCandy[i].vx);
	    		editor.putFloat("T12Canvy"+i, mGR.mLTwelve.mCandy[i].vy);
	    	}
	    	{
	    		editor.putBoolean("T12mWatchwoter", mGR.mLTwelve.mWatch.woter);
	    		editor.putBoolean("T12mWatchcoliition", mGR.mLTwelve.mWatch.coliition);
	    		editor.putInt("T12mWatchcandy", mGR.mLTwelve.mWatch.candy);
	    		editor.putInt("T12mWatchc", mGR.mLTwelve.mWatch.c);
	    		editor.putFloat("T12mWatchx", mGR.mLTwelve.mWatch.x);
	    		editor.putFloat("T12mWatchy", mGR.mLTwelve.mWatch.y);
	    		editor.putFloat("T12mWatchvx", mGR.mLTwelve.mWatch.vx);
	    		editor.putFloat("T12mWatchvy", mGR.mLTwelve.mWatch.vy);
	    	}
	    }
	    {
	    	editor.putBoolean("T13fall", mGR.mLThirteen.fall);
	    	{
	    		editor.putFloat("T13THx", mGR.mLThirteen.mThirteen.x);
	    		editor.putFloat("T13THy", mGR.mLThirteen.mThirteen.y);
	    		editor.putFloat("T13THvx", mGR.mLThirteen.mThirteen.vx);
	    		editor.putFloat("T13THvy", mGR.mLThirteen.mThirteen.vy);
	    	}
	    	{
	    		editor.putFloat("T13objno", mGR.mLThirteen.mObj.no);
	    		editor.putFloat("T13objx", mGR.mLThirteen.mObj.x);
	    		editor.putFloat("T13objy", mGR.mLThirteen.mObj.y);
	    		
	    	}
	    }
	    {
	    	editor.putBoolean("LSelisDown", mGR.mLSelect.isDown);
	    	editor.putBoolean("LSelpopUp", mGR.mLSelect.popUp);
	    	
	    	editor.putInt("LSelmPage", mGR.mLSelect.mPage);
	    	editor.putInt("LSelmGameSel", mGR.mLSelect.mGameSel);
	    	editor.putInt("LSelanim", mGR.mLSelect.anim);
	    	editor.putInt("LSelcounter", mGR.mLSelect.counter);
	    	editor.putInt("LSelScoreCounter", mGR.mLSelect.ScoreCounter);
	    	
	    	editor.putFloat("LSelsd", mGR.mLSelect.sd);
	    	editor.putFloat("LSelsx", mGR.mLSelect.sx);
	    	editor.putFloat("LSelsvx", mGR.mLSelect.svx);
	    	editor.putFloat("LSelscal", mGR.mLSelect.scal);
	    	editor.putFloat("LSelmove", mGR.mLSelect.move);
	    	editor.putFloat("LSelfillupdate", mGR.mLSelect.fillupdate);
	    }
	    editor.putString("mGR.packages", mGR.packages);
		editor.commit();
	}
	void resume()
	{
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		
		addFree = prefs.getBoolean("addFree", addFree);
		SingUpadate = prefs.getBoolean("SingUpadate", SingUpadate);
	    {
	    	M.setValue = prefs.getBoolean("MsetValue", M.setValue);
	    	M.sBGValue = prefs.getBoolean("MsBGValue", M.sBGValue);
	    	for(int i=0;i<M.ACHIVE.length;i++)
	    		for(int j=0;j<M.ACHIVE[i].length;j++)
	    			M.ACHIVE[i][j] = prefs.getBoolean(i+"MACHIVE"+j, false);
	    	
	    	for(int i=0;i<M.HELPSCR.length;i++)
	    		M.HELPSCR[i] = prefs.getBoolean(i+"MHELPSCR", false);
	    	
	    	M.GameScreen = prefs.getInt("MGameScreen", M.GameScreen);
	    }
	    
	    mGR.isPlay = prefs.getBoolean("isPlay", false);
	    mGR.BuyStar = prefs.getInt("BuyStar", 0);
	    mGR.TStar = prefs.getInt("TStar", 0);
	    mGR.Clock = prefs.getInt("Clock", 1);
	    mGR.ClockCount = prefs.getInt("ClockCount", 0);
	    mGR.timesUp = prefs.getInt("timesUp", 0);
	    mGR.mScore = prefs.getInt("mScore", 0);
	    
	    for(int i=0;i<mGR.BestScore.length;i++)
	    	mGR.BestScore[i] = prefs.getInt("BestScore"+i, 0);
	    for(int i=0;i<mGR.Total.length;i++)
	    	mGR.Total[i] = prefs.getInt("Total"+i, 0);
	    mGR.mRankFill = prefs.getFloat("mRankFill", 0);
	    mGR.mGameTime = prefs.getLong("mGameTime", 0);
	    
	    {
	    	for(int i=0;i<mGR.mLOne.arr.length;i++)
	    	{
	    		for(int j=0;j<mGR.mLOne.arr[i].length;j++)
	    		{
	    			mGR.mLOne.arr[i][j] = (byte)prefs.getInt(i+"Oarr"+j, 0);
	    		}
	    	}
	    	mGR.mLOne.counter = prefs.getInt("Ocounter", 0);
	    	mGR.mLOne.m = prefs.getInt("Om", 0);
	    	mGR.mLOne.n = prefs.getInt("On", 0);
	    	mGR.mLOne.ani = prefs.getInt("Oani", 0);
	    	mGR.mLOne.in = prefs.getInt("Oin", 0);
	    	mGR.mLOne.anicount = prefs.getInt("Oanicount", 0);
	    	mGR.mLOne.mx = prefs.getFloat("Omx", 0);
	    	mGR.mLOne.my = prefs.getFloat("Omy", 0);
	    }
	    {
	    	mGR.mLTwo.fall = prefs.getBoolean("T2fall", false);
	    	
	    	mGR.mLTwo.st = (byte)prefs.getInt("T2st", 0);
	    	mGR.mLTwo.Space = (byte)prefs.getInt("T2Space", 0);
	    	mGR.mLTwo.Counter = (byte)prefs.getInt("T2Counter", 0);
	    	mGR.mLTwo.wCount = (byte)prefs.getInt("T2wCount", 0);
	    	mGR.mLTwo.c1 = (byte)prefs.getInt("T2c1", 0);
	    	mGR.mLTwo.jumpCount = (byte)prefs.getInt("T2jumpCount", 0);
	    	
	    	
	    	mGR.mLTwo.Bx1 = prefs.getFloat("T2Bx1", 0);
	    	mGR.mLTwo.Bx2 = prefs.getFloat("T2Bx2", 0);
	    	mGR.mLTwo.move = prefs.getFloat("T2move", 0);
	    	mGR.mLTwo.dis = prefs.getFloat("T2dis", 0);
	    	mGR.mLTwo.px = prefs.getFloat("T2px", 0);
	    	mGR.mLTwo.py = prefs.getFloat("T2py", 0);
	    	mGR.mLTwo.pvy = prefs.getFloat("T2pvy", 0);
	    	
	    	for(int i =0;i<mGR.mLTwo.mStep.length;i++){
	    		mGR.mLTwo.mStep[i].isOn = prefs.getBoolean("T2mStepon"+i, false);
	    		mGR.mLTwo.mStep[i].Watch = prefs.getBoolean("T2mStepwa"+i, false);
	    		mGR.mLTwo.mStep[i].no = prefs.getInt("T2mStepno"+i, 0);
	    		mGR.mLTwo.mStep[i].x = prefs.getFloat("T2mStepx"+i, 0);
	    	}
	    }
	    {
	    	mGR.mLTree.in = prefs.getInt("T3in", 0);
	    	mGR.mLTree.counter = prefs.getInt("T3counter", 0);
	    	mGR.mLTree.ax = prefs.getInt("T3ax", 0);
	    	mGR.mLTree.ay = prefs.getInt("T3ay", 0);
	    	
	    	mGR.mLTree.x = prefs.getFloat("T3x", 0);
	    	mGR.mLTree.y = prefs.getFloat("T3y", 0);
	    	
	    	for(int i=0;i<mGR.mLTree.arr.length;i++)
	    	{
	    		for(int j=0;j<mGR.mLTree.arr[i].length;j++)
	    		{
	    			mGR.mLTree.arr[i][j].isOne = prefs.getBoolean(i+"T3arrisOne"+j, false);
	    			mGR.mLTree.arr[i][j].ex = prefs.getBoolean(i+"T3arrex"+j, false);
	    			mGR.mLTree.arr[i][j].color = prefs.getInt(i+"T3arrcolor"+j, 0);
	    			mGR.mLTree.arr[i][j].no = prefs.getInt(i+"T3arrno"+j, 0);
	    		}
	    	}
	    }
	    {
	    	mGR.mLFour.animCount = (byte)prefs.getInt("F4animCount", 0);
	    	mGR.mLFour.Counter = prefs.getInt("F4Counter", 0);
	    	mGR.mLFour.Counter2 = prefs.getInt("F4Counter2", 0);
	    	mGR.mLFour.ballcount = prefs.getInt("F4ballcount", 0);
	    	mGR.mLFour.Watch = prefs.getInt("F4Watch", 0);
	    	mGR.mLFour.dir = prefs.getFloat("F4dir", 0);
	    	mGR.mLFour.move = prefs.getFloat("F4move", 0);
	    	mGR.mLFour.mBGY = prefs.getFloat("F4mBGY", 0);
	    	mGR.mLFour.distance = prefs.getFloat("F4distance", 0);
	    	for(int i=0;i<mGR.mLFour.mBall.length;i++)
	    	{
	    		mGR.mLFour.mBall[i].off = prefs.getBoolean("F4Balloff"+i, false);
	    		mGR.mLFour.mBall[i].no = (byte) prefs.getInt("F4Ballno"+i, 0);
	    		mGR.mLFour.mBall[i].x = prefs.getFloat("F4Ballx"+i, 0);
	    		mGR.mLFour.mBall[i].y = prefs.getFloat("F4Bally"+i, 0);
	    		mGR.mLFour.mBall[i].vy = prefs.getFloat("F4Ballvy"+i, 0);
	    	}
	    	{
	    		mGR.mLFour.Player.off = prefs.getBoolean("F4Playeroff", false);
	    		mGR.mLFour.Player.no = (byte) prefs.getInt("F4Playerno", 0);
	    		mGR.mLFour.Player.x = prefs.getFloat("F4Playerx", 0);
	    		mGR.mLFour.Player.y = prefs.getFloat("F4Playery", 0);
	    		mGR.mLFour.Player.vy = prefs.getFloat("F4Playervy", 0);
	    	}
	    	{
	    		mGR.mLFour.PMove.off = prefs.getBoolean("F4PMoveoff", false);
	    		mGR.mLFour.PMove.no = (byte) prefs.getInt("F4PMoveno", 0);
	    		mGR.mLFour.PMove.x = prefs.getFloat("F4PMovex", 0);
	    		mGR.mLFour.PMove.y = prefs.getFloat("F4PMovey", 0);
	    		mGR.mLFour.PMove.vy = prefs.getFloat("F4PMovevy", 0);
	    	}
	    	for(int i=0;i<mGR.mLFour.mObject.length;i++){
	    		mGR.mLFour.mObject[i].x = prefs.getFloat("F4Objectx"+i, 0);
	    		mGR.mLFour.mObject[i].y = prefs.getFloat("F4Objecty"+i, 0);
	    		mGR.mLFour.mObject[i].vx = prefs.getFloat("F4Objectvx"+i, 0);
	    	}
	    }
	    {
	    	mGR.mLFive.isWrong = prefs.getBoolean("F5isWrong", mGR.mLFive.isWrong);
	    	mGR.mLFive.counter = prefs.getInt("F5counter", mGR.mLFive.counter);
	    	mGR.mLFive.game = prefs.getInt("F5game", mGR.mLFive.game);
	    	for(int i=0;i<mGR.mLFive.mVecter.length;i++)
	    	{
	    		mGR.mLFive.mVecter[i].off = prefs.getBoolean("F5Voff"+i, mGR.mLFive.mVecter[i].off);
	    		mGR.mLFive.mVecter[i].no = (byte) prefs.getInt("F5Vno"+i, mGR.mLFive.mVecter[i].no);
	    		mGR.mLFive.mVecter[i].x = prefs.getFloat("F5Vx"+i, mGR.mLFive.mVecter[i].x);
	    		mGR.mLFive.mVecter[i].y = prefs.getFloat("F5Vy"+i, mGR.mLFive.mVecter[i].y);
	    		mGR.mLFive.mVecter[i].vy = prefs.getFloat("F5Vvy"+i, mGR.mLFive.mVecter[i].vy);
	    	}
	    }
	    {
	    	mGR.mLsix.move = prefs.getBoolean("S6move", false);
	    	mGR.mLsix.counter = prefs.getInt("S6counter", 0);
	    	mGR.mLsix.counter2 = prefs.getInt("S6counter2", 0);
	    	for(int i=0;i<mGR.mLsix.mFruit.length;i++){
	    		mGR.mLsix.mFruit[i].n = prefs.getInt("S6fn"+i, 0);
	    		mGR.mLsix.mFruit[i].x = prefs.getFloat("S6fx"+i, 0);
	    		mGR.mLsix.mFruit[i].y = prefs.getFloat("S6fy"+i, 0);
	    		mGR.mLsix.mFruit[i].vy = prefs.getFloat("S6fvy"+i, 0);
	    		mGR.mLsix.mFruit[i].vx = prefs.getFloat("S6fvx"+i, 0);
	    	}
	    }
	    {
	    	mGR.mLSeven.fall = prefs.getInt("S7fall", 0);
	    	mGR.mLSeven.BGMove1 = prefs.getFloat("S7BGMove1", 0);
	    	mGR.mLSeven.BGMove2 = prefs.getFloat("S7BGMove2", 0);
	    	mGR.mLSeven.vx = prefs.getFloat("S7vx", 0);
	    	mGR.mLSeven.vy = prefs.getFloat("S7vy", 0);
	    	mGR.mLSeven.hx = prefs.getFloat("S7x", 0);
	    	mGR.mLSeven.hy = prefs.getFloat("S7y", 0);
	    	mGR.mLSeven.speed = prefs.getFloat("S7speed", 0);
	    	
	    	for(int i=0;i<mGR.mLSeven.mObj.length;i++){
	    		for(int j=0;j<mGR.mLSeven.mObj[i].obj.length;j++){
	    			mGR.mLSeven.mObj[i].obj[j] = prefs.getInt(i+"S7OOj"+j, 0);
	    		}
	    		mGR.mLSeven.mObj[i].move = prefs.getInt(i+"S7Omove", 0);
	    		mGR.mLSeven.mObj[i].x = prefs.getFloat(i+"S7Ox", 0);
	    		mGR.mLSeven.mObj[i].y = prefs.getFloat(i+"S7Oy", 0);
	    	}
		    {
	    		for(int j=0;j<mGR.mLSeven.mFall.obj.length;j++){
	    			mGR.mLSeven.mFall.obj[j] = prefs.getInt("S7FOj"+j, 0);
	    		}
	    		mGR.mLSeven.mFall.move = prefs.getInt("S7Fmove", 0);
	    		mGR.mLSeven.mFall.x = prefs.getFloat("S7Fx", 0);
	    		mGR.mLSeven.mFall.y = prefs.getFloat("S7Fy", 0);
	    	}
	    }
	    {
	    	for(int i=0;i<mGR.mLEight.candy.length;i++){
	    		for(int j=0;j<mGR.mLEight.candy[i].length;j++){
	    			mGR.mLEight.candy[i][j] = (byte) prefs.getInt(i+"E8can"+j, 0);
	    		}
	    	}
	    	mGR.mLEight.ext = prefs.getInt("E8ext", mGR.mLEight.ext);
	    	{
	    		mGR.mLEight.mEightObj.i = prefs.getInt("E8EOi", 0);
	    		mGR.mLEight.mEightObj.j = prefs.getInt("E8EOj", 0);
	    		mGR.mLEight.mEightObj.counter = prefs.getInt("E8EOco", 0);
	    		
	    		mGR.mLEight.mEightObj.x = prefs.getFloat("E8EOx", 0);
	    		mGR.mLEight.mEightObj.y = prefs.getFloat("E8EOy", 0);
	    	}
	    	{
	    		mGR.mLEight.mWrong.off = prefs.getBoolean("E8WRoff", false);
	    		
	    		mGR.mLEight.mWrong.no = (byte) prefs.getInt("E8WRno", 0);
	    		
	    		mGR.mLEight.mWrong.x = prefs.getFloat("E8WRx", 0);
	    		mGR.mLEight.mWrong.y = prefs.getFloat("E8WRy", 0);
	    		mGR.mLEight.mWrong.vy = prefs.getFloat("E8WRvy", 0);
	    	}
	    }
	    {
	    	
	    	
	    	mGR.mLNine.fall = prefs.getBoolean("N9fall", false);
	    	mGR.mLNine.stepCount = prefs.getInt("N9stepCount", 0);
	    	mGR.mLNine.ano = prefs.getInt("N9ano", 0);
	    	mGR.mLNine.c1 = prefs.getInt("N9c1", 0);
	    	mGR.mLNine.Move = prefs.getFloat("N9Move", 0);
	    	mGR.mLNine.fx = prefs.getFloat("N9fx", 0);
	    	mGR.mLNine.fy = prefs.getFloat("N9fy", 0);
	    	mGR.mLNine.fvy = prefs.getFloat("N9fvy", 0);
	    	for(int i=0;i<mGR.mLNine.mObj.length;i++){
	    		for(int j=0;j<mGR.mLNine.mObj[i].no.length;j++){
	    			mGR.mLNine.mObj[i].no[j] = prefs.getInt(i+"N9Obno"+j, mGR.mLNine.mObj[i].no[j]);
	    		}
	    		mGR.mLNine.mObj[i].tile = prefs.getInt(i+"N9Obtile", mGR.mLNine.mObj[i].tile);
	    		mGR.mLNine.mObj[i].x = prefs.getFloat(i+"N9Obx", mGR.mLNine.mObj[i].x);
	    		mGR.mLNine.mObj[i].y = prefs.getFloat(i+"N9Oby", mGR.mLNine.mObj[i].y);
	    	}
	    	{
	    		mGR.mLNine.mFrog.x = prefs.getFloat("N9FRx", mGR.mLNine.mFrog.x);
	    		mGR.mLNine.mFrog.y = prefs.getFloat("N9FRy", mGR.mLNine.mFrog.y);
	    		mGR.mLNine.mFrog.vx = prefs.getFloat("N9FRvx", mGR.mLNine.mFrog.vx);
	    		mGR.mLNine.mFrog.vy = prefs.getFloat("N9FRvy", mGR.mLNine.mFrog.vy);
	    		
	    	}
	    }
	    {
	    	mGR.mLTen.Counter = prefs.getInt("T10Counter", mGR.mLTen.Counter);
	    	mGR.mLTen.acc = prefs.getInt("T10acc", mGR.mLTen.acc);
	    	mGR.mLTen.Speed = prefs.getFloat("T10Speed", mGR.mLTen.Speed);
	    	mGR.mLTen.SpeedChange = prefs.getFloat("T10SpeedChange", mGR.mLTen.SpeedChange);
	    	mGR.mLTen.fortDis = prefs.getFloat("T10fortDis", mGR.mLTen.fortDis);
	    	for(int i=0;i<mGR.mLTen.rx.length;i++)
	    		mGR.mLTen.rx[i] = prefs.getFloat(i+"T10rx", mGR.mLTen.rx[i]);
	    	for(int i=0;i<mGR.mLTen.bx.length;i++)
	    		mGR.mLTen.bx[i] = prefs.getFloat(i+"T10bx", mGR.mLTen.bx[i]);
	    	for(int i=0;i<mGR.mLTen.cx.length;i++)
	    		mGR.mLTen.cx[i] = prefs.getFloat(i+"T10cx", mGR.mLTen.cx[i]);
	    	for(int i=0;i<mGR.mLTen.mVecter.length;i++)
	    	{
	    		mGR.mLTen.mVecter[i].off = prefs.getBoolean("T10Voff"+i, mGR.mLTen.mVecter[i].off);
	    		mGR.mLTen.mVecter[i].no = (byte) prefs.getInt("T10Vno"+i, mGR.mLTen.mVecter[i].no);
	    		mGR.mLTen.mVecter[i].x = prefs.getFloat("T10Vx"+i, mGR.mLTen.mVecter[i].x);
	    		mGR.mLTen.mVecter[i].y = prefs.getFloat("T10Vy"+i, mGR.mLTen.mVecter[i].y);
	    		mGR.mLTen.mVecter[i].vy = prefs.getFloat("T10Vvy"+i, mGR.mLTen.mVecter[i].vy);
	    	}
	    	{
	    		mGR.mLTen.mPlayer.no = prefs.getInt("T10Plyno", mGR.mLTen.mPlayer.no);
	    		mGR.mLTen.mPlayer.x = prefs.getFloat("T10Plyx", mGR.mLTen.mPlayer.x);
	    		mGR.mLTen.mPlayer.y = prefs.getFloat("T10Plyy", mGR.mLTen.mPlayer.y);
	    		mGR.mLTen.mPlayer.vy = prefs.getFloat("T10Plyvy", mGR.mLTen.mPlayer.vy);
	    	}
	    	
	    }
	    {
	    	mGR.mLEleven.in = prefs.getInt("E11in", mGR.mLEleven.in);
	    	mGR.mLEleven.counter = prefs.getInt("E11counter", mGR.mLEleven.counter);
	    	mGR.mLEleven.x = prefs.getFloat("E11x", mGR.mLEleven.x);
	    	mGR.mLEleven.y = prefs.getFloat("E11y", mGR.mLEleven.y);
	    	for(int i=0;i<mGR.mLEleven.arr.length;i++){
	    		for(int j=0;j<mGR.mLEleven.arr[i].length;j++){
	    			mGR.mLEleven.arr[i][j].isTuch = prefs.getBoolean(i+"E11arrit"+j, mGR.mLEleven.arr[i][j].isTuch);
	    			mGR.mLEleven.arr[i][j].no = (byte) prefs.getInt(i+"E11arrno"+j, mGR.mLEleven.arr[i][j].no);
	    		}
	    	}
	    }
	    {
	    	mGR.mLTwelve.fall = prefs.getBoolean("T12fall", mGR.mLTwelve.fall);
	    	mGR.mLTwelve.counter = prefs.getInt("T12counter", mGR.mLTwelve.counter);
	    	mGR.mLTwelve.C1 = prefs.getInt("T12C1", mGR.mLTwelve.C1);
	    	mGR.mLTwelve.C2 = prefs.getInt("T12C2", mGR.mLTwelve.C2);
	    	{

	    		mGR.mLTwelve.mStep.off = prefs.getBoolean("T12stoff", mGR.mLTwelve.mStep.off);
	    		mGR.mLTwelve.mStep.no = (byte) prefs.getInt("T12stno", mGR.mLTwelve.mStep.no);
	    		mGR.mLTwelve.mStep.x = prefs.getFloat("T12stx", mGR.mLTwelve.mStep.x);
	    		mGR.mLTwelve.mStep.y = prefs.getFloat("T12sty", mGR.mLTwelve.mStep.y);
	    		mGR.mLTwelve.mStep.vy = prefs.getFloat("T12stvy", mGR.mLTwelve.mStep.vy);
	    	}
	    	
	    	for(int i=0;i<mGR.mLTwelve.Woter.length;i++)
	    	{
	    		mGR.mLTwelve.Woter[i].off = prefs.getBoolean("T12wooff"+i, mGR.mLTwelve.Woter[i].off);
	    		mGR.mLTwelve.Woter[i].no = (byte) prefs.getInt("T12wono"+i, mGR.mLTwelve.Woter[i].no);
	    		mGR.mLTwelve.Woter[i].x = prefs.getFloat("T12wox"+i, mGR.mLTwelve.Woter[i].x);
	    		mGR.mLTwelve.Woter[i].y = prefs.getFloat("T12woy"+i, mGR.mLTwelve.Woter[i].y);
	    		mGR.mLTwelve.Woter[i].vy = prefs.getFloat("T12wovy"+i, mGR.mLTwelve.Woter[i].vy);
	    	}
	    	for(int i=0;i<mGR.mLTwelve.mCandy.length;i++)
	    	{
	    		mGR.mLTwelve.mCandy[i].woter = prefs.getBoolean("T12Canwoter"+i, mGR.mLTwelve.mCandy[i].woter);
	    		mGR.mLTwelve.mCandy[i].coliition = prefs.getBoolean("T12Cancoliition"+i, mGR.mLTwelve.mCandy[i].coliition);
	    		mGR.mLTwelve.mCandy[i].candy = (byte) prefs.getInt("T12Cancandy"+i, mGR.mLTwelve.mCandy[i].candy);
	    		mGR.mLTwelve.mCandy[i].c = (byte) prefs.getInt("T12Canc"+i, mGR.mLTwelve.mCandy[i].c);
	    		mGR.mLTwelve.mCandy[i].x = prefs.getFloat("T12Canx"+i, mGR.mLTwelve.mCandy[i].x);
	    		mGR.mLTwelve.mCandy[i].y = prefs.getFloat("T12Cany"+i, mGR.mLTwelve.mCandy[i].y);
	    		mGR.mLTwelve.mCandy[i].vx = prefs.getFloat("T12Canvx"+i, mGR.mLTwelve.mCandy[i].vx);
	    		mGR.mLTwelve.mCandy[i].vy = prefs.getFloat("T12Canvy"+i, mGR.mLTwelve.mCandy[i].vy);
	    	}
	    	{
	    		mGR.mLTwelve.mWatch.woter = prefs.getBoolean("T12mWatchwoter", mGR.mLTwelve.mWatch.woter);
	    		mGR.mLTwelve.mWatch.coliition = prefs.getBoolean("T12mWatchcoliition", mGR.mLTwelve.mWatch.coliition);
	    		mGR.mLTwelve.mWatch.candy = (byte) prefs.getInt("T12mWatchcandy", mGR.mLTwelve.mWatch.candy);
	    		mGR.mLTwelve.mWatch.c = (byte) prefs.getInt("T12mWatchc", mGR.mLTwelve.mWatch.c);
	    		mGR.mLTwelve.mWatch.x = prefs.getFloat("T12mWatchx", mGR.mLTwelve.mWatch.x);
	    		mGR.mLTwelve.mWatch.y = prefs.getFloat("T12mWatchy", mGR.mLTwelve.mWatch.y);
	    		mGR.mLTwelve.mWatch.vx = prefs.getFloat("T12mWatchvx", mGR.mLTwelve.mWatch.vx);
	    		mGR.mLTwelve.mWatch.vy = prefs.getFloat("T12mWatchvy", mGR.mLTwelve.mWatch.vy);
	    	}
	    }
	    {
	    	mGR.mLThirteen.fall = prefs.getBoolean("T13fall", mGR.mLThirteen.fall);
	    	{
	    		mGR.mLThirteen.mThirteen.x = prefs.getFloat("T13THx", mGR.mLThirteen.mThirteen.x);
	    		mGR.mLThirteen.mThirteen.y = prefs.getFloat("T13THy", mGR.mLThirteen.mThirteen.y);
	    		mGR.mLThirteen.mThirteen.vx = prefs.getFloat("T13THvx", mGR.mLThirteen.mThirteen.vx);
	    		mGR.mLThirteen.mThirteen.vy = prefs.getFloat("T13THvy", mGR.mLThirteen.mThirteen.vy);
	    	}
	    	{
	    		mGR.mLThirteen.mObj.no = (byte) prefs.getFloat("T13objno", mGR.mLThirteen.mObj.no);
	    		mGR.mLThirteen.mObj.x = prefs.getFloat("T13objx", mGR.mLThirteen.mObj.x);
	    		mGR.mLThirteen.mObj.y = prefs.getFloat("T13objy", mGR.mLThirteen.mObj.y);
	    		
	    	}
	    }
	    {
	    	mGR.mLSelect.isDown = prefs.getBoolean("LSelisDown", mGR.mLSelect.isDown);
	    	mGR.mLSelect.popUp = prefs.getBoolean("LSelpopUp", mGR.mLSelect.popUp);
	    	
	    	mGR.mLSelect.mPage = prefs.getInt("LSelmPage", mGR.mLSelect.mPage);
	    	mGR.mLSelect.mGameSel = prefs.getInt("LSelmGameSel", mGR.mLSelect.mGameSel);
	    	mGR.mLSelect.anim = prefs.getInt("LSelanim", mGR.mLSelect.anim);
	    	mGR.mLSelect.counter = prefs.getInt("LSelcounter", mGR.mLSelect.counter);
	    	mGR.mLSelect.ScoreCounter = prefs.getInt("LSelScoreCounter", mGR.mLSelect.ScoreCounter);
	    	
	    	mGR.mLSelect.sd = prefs.getFloat("LSelsd", mGR.mLSelect.sd);
	    	mGR.mLSelect.sx = prefs.getFloat("LSelsx", mGR.mLSelect.sx);
	    	mGR.mLSelect.svx = prefs.getFloat("LSelsvx", mGR.mLSelect.svx);
	    	mGR.mLSelect.scal = prefs.getFloat("LSelscal", mGR.mLSelect.scal);
	    	mGR.mLSelect.move = prefs.getFloat("LSelmove", mGR.mLSelect.move);
	    	mGR.mLSelect.fillupdate = prefs.getFloat("LSelfillupdate", mGR.mLSelect.fillupdate);
	    }
	    mGR.packages = prefs.getString("mGR.packages", mGR.packages);
		mGR.mLSelect.calcStars();
		GetPackage();
		if(M.GameScreen == M.GAMEMAIN)
		{
			M.play(GameRenderer.mContext, R.drawable.splesh);
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
	
	void Massage(String str)
	{
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle(str)
	    .setPositiveButton("Ok",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which){}}).show();
	}
	
	
	
	void GetPackage() {
//		Log.d("~~~~~~~~~~~~~~~"+mGR.packages, "not package : "+ mGR.packages);
		final PackageManager pm = getPackageManager();
		List<ApplicationInfo> packages = pm
				.getInstalledApplications(PackageManager.GET_META_DATA);
		for (ApplicationInfo packageInfo : packages) {
			String s = ".*" + packageInfo.packageName + ".*";
//			System.out.println(s+" --- "+packageInfo.packageName);
			for (int i = 0; i < M.Package.length; i++) {
				if (M.Package[i].matches(s)) {
					if (mGR.packages.matches(s)) {
//						System.out.println("~~~~~~~~~~~~~~~"+"Installed package :" + packageInfo.packageName);
					} else {
						mGR.packages = mGR.packages + packageInfo.packageName;
						mGR.Clock+=1;
						// Log.d("~~~~~~~~~~~~~~~"+mGR.packages, "not package :"
						// + packageInfo.packageName);
					}
					break;
				}
			}
		}
	}
	
	
	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		if(!SingUpadate)
		{
			for(int i=0;i<M.ACHIVE.length;i++)
			{
				for(int j=0;j<M.ACHIVE[i].length;j++)
				{
					if(M.ACHIVE[i][j])
					{
						UnlockAchievement(frog.Achivemnet[i*4+j]);
					}
				}
			}
			for(int i=0;i<10 && i<=(mGR.mRankFill/16);i++)
			{
				UnlockAchievement(frog.Achivemnet_RANK[i]);
			}
			SingUpadate = true;
		}
	}

	public void Submitscore(final int ID, int score) {
		try {
			if (!isSignedIn()) {// beginUserInitiatedSignIn();
				return;
			} else {
				Games.Leaderboards.submitScore(getApiClient(), getString(ID), score);
			}
		} catch (Exception e) {}
	}

	
	int RC_UNUSED = 5001;
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
	public void loadInter() {
		try{handlerInter.sendEmptyMessage(0);}catch(Exception e){}
	}
	Handler handlerInter = new Handler() {public void handleMessage(Message msg) {load();}};
	private void load(){
		if (!interstitialAd.isLoaded()) {
			AdRequest adRequest = new AdRequest.Builder().build();
			interstitialAd.loadAd(adRequest);
			interstitialAd.setAdListener(new GameAdListener(this));
		}
	}

	public void show() {
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
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~"+ "onActivityResult(" + requestCode + "," + resultCode + "," + data);
//        if (!mGR.mMainActivity.mHelper.handleActivityResult(requestCode, resultCode, data)) {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
//        else {
//        	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~"+ "onActivityResult handled by IABUtil.");
//        }
        
    }

	void resetGame()
	{
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Are you sure to reset game?")
	    .setPositiveButton("Yes",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
    	   	SharedPreferences settings = getSharedPreferences("X", MODE_PRIVATE);
			settings.edit().clear().commit();
			resume();
      }}).setNegativeButton("No",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
    	  }}).show();
	}
	
	/*Handler LoadSmart = new Handler() {public void handleMessage(Message msg) {LoadSmarAd();}};
	void LoadSmartHandler()
	{
//		if(!mGR.addFree)
			try{LoadSmart.sendEmptyMessage(0);}catch(Exception e){}
	}
	boolean adType = false;
	private void LoadSmarAd()
	{
//		if(!mGR.addFree)
		{
			adType = !adType;//M.mRand.nextBoolean(); 
			if(adType)
				mAir.runSmartWallAd();
			else
				mAir.runRichMediaInterstitialAd();
//			mAir.runVideoAd();
		}
	}
	Handler ShowSmart = new Handler() {public void handleMessage(Message msg) {showSmarAd();}};
	public void ShowSmart() {
//		if(!mGR.addFree)
			try{ShowSmart.sendEmptyMessage(0);}catch(Exception e){}
	}
	private void showSmarAd()
	{
//		if(!mGR.addFree)
		{
			try{
				if(adType)
					mAir.runCachedAd(this, AdType.smartwall);  //Will show the ad is it's available in cache.
				else
					mAir.runCachedAd(this, AdType.interstitial);
			}catch (Exception e){
				System.out.println(e.toString());
			}
		}
	}*/
}