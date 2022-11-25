package com.oneday.games24.clashofbattles;
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
	GameRenderer mGR = null;
	GameAd mGameAd = new GameAd();;
	private static Context CONTEXT;
	boolean IsCreate = false;
	void callAdds()
	{
		/*adView = (AdView) this.findViewById(R.id.addgame);
		AdRequest adRequest = new AdRequest.Builder()
		.build();
		adView.loadAd(adRequest);
		adView.setAdListener(new AdListener() {
			public void onAdLoaded() {
				adView.bringToFront();
			}
		});*/
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
		mGameAd.initAds(this);
//		interstitial = new InterstitialAd(this);
//		interstitial.setAdUnitId(getString(R.string.INTERSTITIAL_ID));
	    WindowManager mWinMgr = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
		M.ScreenWidth =mWinMgr.getDefaultDisplay().getWidth();
		M.ScreenHieght=mWinMgr.getDefaultDisplay().getHeight();
		mGR=new GameRenderer(this);
	    VortexView glSurface=(VortexView) findViewById(R.id.vortexview); // use the xml to set the view
	    glSurface.setRenderer(mGR);
	    glSurface.showRenderer(mGR);
	    callAdds();
	    IsCreate = true;
	    load();
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
//		Log.d("----------------=>  "+keyCode,"   -----------    ");
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			switch (M.GameScreen) {
			case M.GAMEPLAY:
				M.GameScreen = M.GAMEPAUS;
				M.stop();
				break;
			case M.GAMEMENU:
				Exit();
				break;
			default:
				M.play(R.raw.splash);
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

	void pause() {
		M.stop();
		if(M.GameScreen == M.GAMEPLAY)
			M.GameScreen = M.GAMEPAUS;
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		Editor editor = prefs.edit();
		
		editor.putInt("screen", M.GameScreen);
		
		
		editor.putBoolean("setValue", M.setValue);
		editor.putBoolean("setMusic", M.setMusic);
		editor.putBoolean("addFree", mGR.addFree);
		for(int i=0;i<mGR.mAchiUnlock.length;i++)
			editor.putBoolean("mAchiUnlock"+i, mGR.mAchiUnlock[i]);
		editor.putBoolean("SingUpadate", mGR.SingUpadate);
		editor.putBoolean("shopCarDrive", mGR.shopCarDrive);

		editor.putInt("carSel", mGR.carSel);
		
		
		for (int i = 0; i < mGR.carDrive.length; i++)
			editor.putInt("carDrive" + i, mGR.carDrive[i]);
		
		
		editor.putInt("topCount", mGR.topCount);
		editor.putInt("overCount", mGR.overCount);
		for (int i = 0; i < mGR.oppseq.length; i++) {
			for (int j = 0; j < mGR.oppseq[i].length; j++)
				editor.putInt(i + "oppseq" + j, mGR.oppseq[i][j]);
		}
		editor.putInt("Bullate", mGR.Bullate);
		editor.putInt("Totalkill", mGR.Totalkill);
		editor.putInt("mCoin", mGR.mCoin);
		editor.putInt("kill", mGR.kill);
		editor.putInt("killVal", mGR.killVal);
		editor.putInt("Wall", mGR.Wall);
		editor.putInt("Rewrd", mGR.Rewrd);
		editor.putInt("tcalcu", mGR.tcalcu);

		editor.putFloat("spd", mGR.spd);
		editor.putFloat("distance", mGR.distance);
		editor.putFloat("Tdistance", mGR.Tdistance);

		for (int i = 0; i < mGR.mPlayCar.length; i++) {

			editor.putBoolean("mPlayCariSAlive" + i, mGR.mPlayCar[i].iSAlive);

			editor.putInt("mPlayCarNoBomb" + i, mGR.mPlayCar[i].NoBomb);
			editor.putInt("mPlayCarcount" + i, mGR.mPlayCar[i].count);
			editor.putInt("mPlayCarimg" + i, mGR.mPlayCar[i].img);
			editor.putInt("mPlayCarshild" + i, mGR.mPlayCar[i].shild);

			editor.putFloat("mPlayCarx" + i, mGR.mPlayCar[i].x);
			editor.putFloat("mPlayCary" + i, mGR.mPlayCar[i].y);
			editor.putFloat("mPlayCarvx" + i, mGR.mPlayCar[i].vx);
			editor.putFloat("mPlayCarvy" + i, mGR.mPlayCar[i].vy);
			editor.putFloat("mPlayCarang" + i, mGR.mPlayCar[i].ang);
			editor.putFloat("mPlayCarlife" + i, mGR.mPlayCar[i].life);
			editor.putFloat("mPlayCartlife" + i, mGR.mPlayCar[i].Totallife);
		}

		for (int i = 0; i < mGR.mDusman.length; i++) {

			editor.putInt("mDusmanimg" + i, mGR.mDusman[i].img);
			editor.putInt("mDusmananim" + i, mGR.mDusman[i].anim);
			editor.putInt("mDusmanang" + i, mGR.mDusman[i].ang);
			editor.putInt("mDusmancounter" + i, mGR.mDusman[i].counter);

			editor.putFloat("mDusmanx" + i, mGR.mDusman[i].x);
			editor.putFloat("mDusmany" + i, mGR.mDusman[i].y);
			editor.putFloat("mDusmanvx" + i, mGR.mDusman[i].vx);
			editor.putFloat("mDusmanvy" + i, mGR.mDusman[i].vy);
			editor.putFloat("mDusmanvariX" + i, mGR.mDusman[i].variX);
			editor.putFloat("mDusmanlife" + i, mGR.mDusman[i].life);
		}
		for (int i = 0; i < mGR.mMissile.length; i++) {
			editor.putInt("mMissileimg" + i, mGR.mMissile[i].img);
			editor.putInt("mMissileangle" + i, mGR.mMissile[i].angle);

			editor.putFloat("mMissilex" + i, mGR.mMissile[i].x);
			editor.putFloat("mMissiley" + i, mGR.mMissile[i].y);
			editor.putFloat("mMissilevx" + i, mGR.mMissile[i].vx);
			editor.putFloat("mMissilevy" + i, mGR.mMissile[i].vy);

		}

		for (int i = 0; i < mGR.mBulltet.length; i++) {
			editor.putInt("mBulltetimg" + i, mGR.mBulltet[i].img);
			editor.putInt("mBulltetangle" + i, mGR.mBulltet[i].angle);

			editor.putFloat("mBulltetx" + i, mGR.mBulltet[i].x);
			editor.putFloat("mBulltety" + i, mGR.mBulltet[i].y);
			editor.putFloat("mBulltetvx" + i, mGR.mBulltet[i].vx);
			editor.putFloat("mBulltetvy" + i, mGR.mBulltet[i].vy);

		}

		for (int i = 0; i < mGR.mBlast.length; i++) {
			editor.putInt("mBlastimg" + i, mGR.mBlast[i].img);

			editor.putFloat("mBlastx" + i, mGR.mBlast[i].x);
			editor.putFloat("mBlasty" + i, mGR.mBlast[i].y);

		}

		for (int i = 0; i < mGR.mPMissile.length; i++) {

			editor.putBoolean("mPMissileistap" + i, mGR.mPMissile[i].isTop);

			editor.putInt("mPMissileimg" + i, mGR.mPMissile[i].img);
			editor.putInt("mPMissilecount" + i, mGR.mPMissile[i].count);

			editor.putFloat("mPMissilex" + i, mGR.mPMissile[i].x);
			editor.putFloat("mPMissiley" + i, mGR.mPMissile[i].y);
			editor.putFloat("mPMissilevx" + i, mGR.mPMissile[i].vx);
			editor.putFloat("mPMissilevy" + i, mGR.mPMissile[i].vy);
			editor.putFloat("mPMissileradin" + i, mGR.mPMissile[i].radin);

		}

		for (int i = 0; i < mGR.mCarValue.length; i++) {
			editor.putBoolean("mCarValuebuy" + i, mGR.mCarValue[i].Buy);
			for (int j = 0; j < mGR.mCarValue[i].upgred.length; j++)
				editor.putInt(j + "mCarValueupgred" + i,
						mGR.mCarValue[i].upgred[j]);
		}

		for (int i = 0; i < mGR.mSmock.length; i++) {
			editor.putFloat("mSmockx" + i, mGR.mSmock[i].x);
			editor.putFloat("mSmocky" + i, mGR.mSmock[i].y);
			editor.putFloat("mSmockz" + i, mGR.mSmock[i].z);
		}

		editor.commit();
	}
	void resume()
	{
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		
		
		M.GameScreen = prefs.getInt("screen", M.GAMELOGO);
		M.GameScreen = prefs.getInt("screen", M.GameScreen);
		M.setValue = prefs.getBoolean("setValue", true);
		M.setMusic = prefs.getBoolean("setMusic", true);
		mGR.addFree = prefs.getBoolean("addFree", mGR.addFree);
		mGR.SingUpadate = prefs.getBoolean("SingUpadate", mGR.SingUpadate);
		for(int i=0;i<mGR.mAchiUnlock.length;i++)
			mGR.mAchiUnlock[i] = prefs.getBoolean("mAchiUnlock"+i, mGR.mAchiUnlock[i]);
		
		mGR.shopCarDrive = prefs.getBoolean("shopCarDrive", false);

		mGR.carSel = (byte)prefs.getInt("carSel", 0);
		for (int i = 0; i < mGR.carDrive.length; i++)
			mGR.carDrive[i] = (byte) prefs.getInt("carDrive" + i, i == 0 ? 0 : -1);
		mGR.topCount = (byte)prefs.getInt("topCount", 7);
		mGR.overCount = (byte)prefs.getInt("overCount", 0);
		for (int i = 0; i < mGR.oppseq.length; i++) {
			for (int j = 0; j < mGR.oppseq[i].length; j++)
				mGR.oppseq[i][j] = (byte)prefs.getInt(i + "oppseq" + j, 0);
		}
		mGR.Bullate = (byte)prefs.getInt("Bullate", 0);

		mGR.Totalkill = prefs.getInt("Totalkill", mGR.Totalkill);
		mGR.mCoin = prefs.getInt("mCoin", 0);
		mGR.kill = prefs.getInt("kill", 0);
		mGR.killVal = prefs.getInt("killVal", 0);
		mGR.Wall = prefs.getInt("Wall", 0);
		mGR.Rewrd = prefs.getInt("Rewrd", 0);
		mGR.tcalcu = prefs.getInt("tcalcu", 0);

		mGR.spd = prefs.getFloat("spd", 0);
		mGR.distance = prefs.getFloat("distance", 0);
		mGR.Tdistance = prefs.getFloat("Tdistance", 0);

		for (int i = 0; i < mGR.mPlayCar.length; i++) {

			mGR.mPlayCar[i].iSAlive = prefs.getBoolean("mPlayCariSAlive" + i, false);

			mGR.mPlayCar[i].NoBomb = (byte)prefs.getInt("mPlayCarNoBomb" + i, 1);
			mGR.mPlayCar[i].count = (short)prefs.getInt("mPlayCarcount" + i, 0);
			mGR.mPlayCar[i].img = prefs.getInt("mPlayCarimg" + i, 0);
			mGR.mPlayCar[i].shild = prefs.getInt("mPlayCarshild" + i, 1);

			mGR.mPlayCar[i].x = prefs.getFloat("mPlayCarx" + i, mGR.mPlayCar[i].x);
			mGR.mPlayCar[i].y = prefs.getFloat("mPlayCary" + i, mGR.mPlayCar[i].y);
			mGR.mPlayCar[i].vx = prefs.getFloat("mPlayCarvx" + i, mGR.mPlayCar[i].vx);
			mGR.mPlayCar[i].vy = prefs.getFloat("mPlayCarvy" + i, mGR.mPlayCar[i].vy);
			mGR.mPlayCar[i].ang = prefs.getFloat("mPlayCarang" + i, mGR.mPlayCar[i].ang);
			mGR.mPlayCar[i].life = prefs.getFloat("mPlayCarlife" + i, mGR.mPlayCar[i].life);
			mGR.mPlayCar[i].Totallife = prefs.getFloat("mPlayCartlife" + i, mGR.mPlayCar[i].Totallife);
		}

		for (int i = 0; i < mGR.mDusman.length; i++) {

			mGR.mDusman[i].img = (byte)prefs.getInt("mDusmanimg" + i, mGR.mDusman[i].img);
			mGR.mDusman[i].anim = (byte)prefs.getInt("mDusmananim" + i, mGR.mDusman[i].anim);
			mGR.mDusman[i].ang = (short)prefs.getInt("mDusmanang" + i, mGR.mDusman[i].ang);
			mGR.mDusman[i].counter = (short)prefs.getInt("mDusmancounter" + i, mGR.mDusman[i].counter);

			mGR.mDusman[i].x = prefs.getFloat("mDusmanx" + i, mGR.mDusman[i].x);
			mGR.mDusman[i].y = prefs.getFloat("mDusmany" + i, mGR.mDusman[i].y);
			mGR.mDusman[i].vx = prefs.getFloat("mDusmanvx" + i, mGR.mDusman[i].vx);
			mGR.mDusman[i].vy = prefs.getFloat("mDusmanvy" + i, mGR.mDusman[i].vy);
			mGR.mDusman[i].variX = prefs.getFloat("mDusmanvariX" + i, mGR.mDusman[i].variX);
			mGR.mDusman[i].life = prefs.getFloat("mDusmanlife" + i, mGR.mDusman[i].life);
		}
		for (int i = 0; i < mGR.mMissile.length; i++) {
			mGR.mMissile[i].img = (byte)prefs.getInt("mMissileimg" + i, mGR.mMissile[i].img);
			mGR.mMissile[i].angle = prefs.getInt("mMissileangle" + i, mGR.mMissile[i].angle);

			mGR.mMissile[i].x = prefs.getFloat("mMissilex" + i, mGR.mMissile[i].x);
			mGR.mMissile[i].y = prefs.getFloat("mMissiley" + i, mGR.mMissile[i].y);
			mGR.mMissile[i].vx = prefs.getFloat("mMissilevx" + i, mGR.mMissile[i].vx);
			mGR.mMissile[i].vy = prefs.getFloat("mMissilevy" + i, mGR.mMissile[i].vy);

		}

		for (int i = 0; i < mGR.mBulltet.length; i++) {
			mGR.mBulltet[i].img = prefs.getInt("mBulltetimg" + i, mGR.mBulltet[i].img);
			mGR.mBulltet[i].angle = prefs.getInt("mBulltetangle" + i, mGR.mBulltet[i].angle);

			mGR.mBulltet[i].x = prefs.getFloat("mBulltetx" + i, mGR.mBulltet[i].x);
			mGR.mBulltet[i].y = prefs.getFloat("mBulltety" + i, mGR.mBulltet[i].y);
			mGR.mBulltet[i].vx = prefs.getFloat("mBulltetvx" + i, mGR.mBulltet[i].vx);
			mGR.mBulltet[i].vy = prefs.getFloat("mBulltetvy" + i, mGR.mBulltet[i].vy);

		}

		for (int i = 0; i < mGR.mBlast.length; i++) {
			mGR.mBlast[i].img = (byte)prefs.getInt("mBlastimg" + i, mGR.mBlast[i].img);

			mGR.mBlast[i].x = prefs.getFloat("mBlastx" + i, mGR.mBlast[i].x);
			mGR.mBlast[i].y = prefs.getFloat("mBlasty" + i, mGR.mBlast[i].y);

		}

		for (int i = 0; i < mGR.mPMissile.length; i++) {

			mGR.mPMissile[i].isTop = prefs.getBoolean("mPMissileistap" + i, mGR.mPMissile[i].isTop);

			mGR.mPMissile[i].img = (byte)prefs.getInt("mPMissileimg" + i, mGR.mPMissile[i].img);
			mGR.mPMissile[i].count = prefs.getInt("mPMissilecount" + i, mGR.mPMissile[i].count);

			mGR.mPMissile[i].x = prefs.getFloat("mPMissilex" + i, mGR.mPMissile[i].x);
			mGR.mPMissile[i].y = prefs.getFloat("mPMissiley" + i, mGR.mPMissile[i].y);
			mGR.mPMissile[i].vx = prefs.getFloat("mPMissilevx" + i, mGR.mPMissile[i].vx);
			mGR.mPMissile[i].vy = prefs.getFloat("mPMissilevy" + i, mGR.mPMissile[i].vy);
			mGR.mPMissile[i].radin = prefs.getFloat("mPMissileradin" + i, mGR.mPMissile[i].radin);

		}

		for (int i = 0; i < mGR.mCarValue.length; i++) {
			mGR.mCarValue[i].Buy = prefs.getBoolean("mCarValuebuy" + i, i == 0?true:false);
			for (int j = 0; j < mGR.mCarValue[i].upgred.length; j++)
				mGR.mCarValue[i].upgred[j] = (byte)prefs.getInt(j + "mCarValueupgred" + i,1);
		}

		for (int i = 0; i < mGR.mSmock.length; i++) {
			mGR.mSmock[i].x = prefs.getFloat("mSmockx" + i, -10);
			mGR.mSmock[i].y = prefs.getFloat("mSmocky" + i, 10);
			mGR.mSmock[i].z = prefs.getFloat("mSmockz" + i, 0);
		}
		
		if(M.GameScreen == M.GAMEMENU){
			M.play(R.raw.splash);
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
	
	void Donot()
	{
		M.GameScreen = M.GAMEBUY;
	  /* new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Don't have sufficient Coin?")
	    .setNeutralButton("Buy",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
	    	M.GameScreen = M.GAMEBUY;
      }}).setNegativeButton("No",new DialogInterface.OnClickListener(){public void onClick(DialogInterface dialog, int which) {
      }}).show();*/
  }
	void Shop()
	{
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Buy car first.")
	    .setNeutralButton("Ok",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
      }}).show();
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
	public void load() {
		try{handlerload.sendEmptyMessage(0);}catch(Exception e){}
	}
	Handler handlerload = new Handler() {public void handleMessage(Message msg) {loadInter();}};

	
	private void loadInter(){
		mGameAd.loadInterstitialAd();
		mGameAd.LoadReward();
//		if (!interstitial.isLoaded() && !mGR.addFree) {
//			AdRequest adRequest = new AdRequest.Builder()
////					.addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("67701763FB847AEBD3C6EE486475ED94")
//			.build();
//			interstitial.loadAd(adRequest);
//			interstitial.setAdListener(new AdGameListener(this));
//		}
	}

	public void ShowInterstitial() {
		Log.d("Yogesh", "ShowInterstitial: ");
		if(!mGR.addFree)
			try{handler.sendEmptyMessage(0);}catch(Exception e){}
	}
	Handler handler = new Handler() {public void handleMessage(Message msg) {show();}};

	private void show() {
		try {
			mGameAd.showInterstitialAd();
//			mGameAd.showReward();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
	void Achivment(){

	}	
}