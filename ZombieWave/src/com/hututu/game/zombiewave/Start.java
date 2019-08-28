package com.hututu.game.zombiewave;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import android.app.Activity;
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

public class Start extends Activity 
{
	int _keyCode = 0;
	GameRenderer mGR = null;
	private static Context CONTEXT;
	private InterstitialAd interstitial;
	AdView adView = null;
	void callAdds()
	{
		adView = (AdView) this.findViewById(R.id.adView);
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
			if(M.GameScreen != M.GAMEMENU){
				M.stop(GameRenderer.mContext);
				M.GameScreen = M.GAMEMENU;
				M.play(GameRenderer.mContext, R.drawable.splash);
			}
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
	public void onDestroy()
	{
		super.onDestroy();
		if(mGR.mInApp!=null)
			mGR.mInApp.onDestroy();
	}
	void pause()
	{
		M.stop(GameRenderer.mContext);
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
	    Editor editor = prefs.edit();
	    editor.putInt("screen", M.GameScreen);
	    editor.putBoolean("setValue", M.setValue);
	    
	    editor.putFloat("bomboox", mGR.mBGBambo.x);
	    editor.putFloat("bombooy", mGR.mBGBambo.y);
		
	    for(int i = 0 ; i<mGR.mBGOne.length;i++){
	    	editor.putFloat("mBGOnex"+i, mGR.mBGOne[i].x);
		    editor.putFloat("mBGOney"+i, mGR.mBGOne[i].y);
	    }
	    for(int i = 0 ; i<mGR.mBGTwo.length;i++){
	    	editor.putFloat("mBGTwox"+i, mGR.mBGTwo[i].x);
		    editor.putFloat("mBGTwoy"+i, mGR.mBGTwo[i].y);
	    }
	    for(int i = 0 ; i<mGR.mBGThree.length;i++){
	    	editor.putFloat("mBGThreex"+i, mGR.mBGThree[i].x);
		    editor.putFloat("mBGThreey"+i, mGR.mBGThree[i].y);
	    }
	    for(int i = 0 ; i<mGR.mBGFour.length;i++){
	    	editor.putFloat("mBGFourx"+i, mGR.mBGFour[i].x);
		    editor.putFloat("mBGFoury"+i, mGR.mBGFour[i].y);
	    }
	    
	    
	    {
	    	editor.putBoolean("PlayerBoostActive", mGR.mPlayer.BoostActive);
	    	editor.putBoolean("PlayerRun", mGR.mPlayer.Run);
	    	editor.putBoolean("PlayerGameOn", mGR.mPlayer.GameOn);
	    	
	    	editor.putInt("Playerimg", mGR.mPlayer.img);
	    	editor.putInt("Playerpahiye", mGR.mPlayer.pahiye);
	    	editor.putInt("PlayerkitNo", mGR.mPlayer.kitNo);
	    	editor.putInt("Playerpetrol", mGR.mPlayer.petrol);
	    	editor.putInt("PlayerBoostfill", mGR.mPlayer.Boostfill);
	    	editor.putInt("Playerbullatefire", mGR.mPlayer.bullatefire);
	    	editor.putInt("Playerciti", mGR.mPlayer.citi);
	    	editor.putInt("PlayerEngine", mGR.mPlayer.Engine);
	    	editor.putInt("PlayerGear", mGR.mPlayer.Gear);
	    	editor.putInt("PlayeroverCounter", mGR.mPlayer.overCounter);
	    	editor.putInt("PlayerGunfill", mGR.mPlayer.Gunfill);
	    	
	    	editor.putFloat("PlayermDis", mGR.mPlayer.mDis);
	    	editor.putFloat("PlayerSpeed", mGR.mPlayer.Speed);
	    	editor.putFloat("PlayerboostSpd", mGR.mPlayer.boostSpd);
	    	editor.putFloat("Playerx", mGR.mPlayer.x);
	    	editor.putFloat("Playery", mGR.mPlayer.y);
	    	editor.putFloat("Playervx", mGR.mPlayer.vx);
	    	editor.putFloat("Playerp", mGR.mPlayer.p);
	    	editor.putFloat("Playervp", mGR.mPlayer.vp);
	    	editor.putFloat("Playerbegin", mGR.mPlayer.mBegin);
	    }
	    for(int i=0;i<mGR.mZombie.length;i++){
	    	editor.putInt("Zombieno"+i, mGR.mZombie[i].no);
	    	editor.putFloat("Zombiex"+i, mGR.mZombie[i].x);
	    	editor.putFloat("Zombiey"+i, mGR.mZombie[i].y);
	    }
	    for(int i=0;i<mGR.mTukde.length;i++){
	    	editor.putInt("Tukdeno"+i, mGR.mTukde[i].no);
	    	editor.putInt("Tukdeimg"+i, mGR.mTukde[i].img);
	    	editor.putFloat("Tukdex"+i, mGR.mTukde[i].x);
	    	editor.putFloat("Tukdey"+i, mGR.mTukde[i].y);
	    	editor.putFloat("Tukdevx"+i, mGR.mTukde[i].vx);
	    	editor.putFloat("Tukdevy"+i, mGR.mTukde[i].vy);
	    }
	    for(int i=0;i<mGR.mBox.length;i++){
	    	editor.putBoolean("Boxdw"+i, mGR.mBox[i].down);
	    	editor.putFloat("Boxx"+i, mGR.mBox[i].x);
	    	editor.putFloat("Boxy"+i, mGR.mBox[i].y);
	    	editor.putFloat("Boxvy"+i, mGR.mBox[i].vy);
	    }
	    for(int i=0;i<mGR.mSBox.length;i++){
	    	editor.putBoolean("SBoxdw"+i, mGR.mSBox[i].down);
	    	editor.putFloat("SBoxx"+i, mGR.mSBox[i].x);
	    	editor.putFloat("SBoxy"+i, mGR.mSBox[i].y);
	    	editor.putFloat("SBoxvy"+i, mGR.mSBox[i].vy);
	    }
	    for(int i=0;i<mGR.mCar.length;i++){
	    	
	    	for(int j =0;j<mGR.mCar[i].Engine.length;j++){
	    		editor.putBoolean(j+"mCarEngine"+i, mGR.mCar[i].Engine[j]);
	    	}
	    	for(int j =0;j<mGR.mCar[i].Gear.length;j++){
	    		editor.putBoolean(j+"mCarGear"+i, mGR.mCar[i].Gear[j]);
	    	}
	    	for(int j =0;j<mGR.mCar[i].Wheels.length;j++){
	    		editor.putBoolean(j+"mCarWheels"+i, mGR.mCar[i].Wheels[j]);
	    	}
	    	for(int j =0;j<mGR.mCar[i].Kit.length;j++){
	    		editor.putBoolean(j+"mCarKit"+i, mGR.mCar[i].Kit[j]);
	    	}
	    	editor.putInt("CarsEngine"+i, mGR.mCar[i].sEngine);
	    	editor.putInt("CarsGear"+i, mGR.mCar[i].sGear);
	    	editor.putInt("CarsWheel"+i, mGR.mCar[i].sWheel);
	    	editor.putInt("CarsKit"+i, mGR.mCar[i].sKit);
	    	editor.putInt("CarsGun"+i, mGR.mCar[i].sGun);
	    	editor.putInt("CarBoost"+i, mGR.mCar[i].Boost);
	    	editor.putInt("CarPetrol"+i, mGR.mCar[i].Petrol);
	    }
	    for(int i=0;i<mGR.mBlood.length;i++){
	    	editor.putInt("Bloodno"+i, mGR.mBlood[i].no);
	    	editor.putInt("Bloodimg"+i, mGR.mBlood[i].img);
	    	editor.putFloat("Bloodx"+i, mGR.mBlood[i].x);
	    	editor.putFloat("Bloody"+i, mGR.mBlood[i].y);
	    }
		for(int i =0;i<mGR.unclockCar.length;i++)
			editor.putBoolean("unclockCar"+i, mGR.unclockCar[i]);
		
		editor.putBoolean("addFree", mGR.addFree);
		
		
		editor.putInt("bamboCont", mGR.bamboCont);
		editor.putInt("Attack", mGR.Attack);
		editor.putInt("carSelect", mGR.carSelect);
		editor.putInt("Popup", mGR.Popup);
		editor.putInt("Zombie", mGR.Zombie);
		editor.putInt("setJoin", mGR.setJoin);
		editor.putInt("Dollor", mGR.Dollor);

		editor.putInt("story", mGR.story);
		
		editor.putFloat("one", mGR.One);
		editor.putFloat("two", mGR.two);
		editor.putFloat("three", mGR.three);
		
		
		editor.putFloat("newSbox", mGR.newSbox);
		editor.putFloat("newZombie", mGR.newZombie);
		editor.putFloat("newZombieV", mGR.newZombieV);
		editor.putFloat("mLast", mGR.mLast);

	    editor.commit();
	}
	void resume()
	{
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = prefs.getInt("screen", M.GAMELOGO);
		M.setValue = prefs.getBoolean("setValue", M.setValue);
		
		
		mGR.mBGBambo.x = prefs.getFloat("bomboox", mGR.mBGBambo.x);
		mGR.mBGBambo.y = prefs.getFloat("bombooy", mGR.mBGBambo.y);
		for(int i = 0 ; i<mGR.mBGOne.length;i++){
			mGR.mBGOne[i].x = prefs.getFloat("mBGOnex"+i, mGR.mBGOne[i].x);
			mGR.mBGOne[i].y = prefs.getFloat("mBGOney"+i, mGR.mBGOne[i].y);
	    }
	    for(int i = 0 ; i<mGR.mBGTwo.length;i++){
	    	mGR.mBGTwo[i].x = prefs.getFloat("mBGTwox"+i, mGR.mBGTwo[i].x);
	    	mGR.mBGTwo[i].y = prefs.getFloat("mBGTwoy"+i, mGR.mBGTwo[i].y);
	    }
	    for(int i = 0 ; i<mGR.mBGThree.length;i++){
	    	mGR.mBGThree[i].x = prefs.getFloat("mBGThreex"+i, mGR.mBGThree[i].x);
	    	mGR.mBGThree[i].y = prefs.getFloat("mBGThreey"+i, mGR.mBGThree[i].y);
	    }
	    for(int i = 0 ; i<mGR.mBGFour.length;i++){
	    	mGR.mBGFour[i].x = prefs.getFloat("mBGFourx"+i, mGR.mBGFour[i].x);
	    	mGR.mBGFour[i].y = prefs.getFloat("mBGFoury"+i, mGR.mBGFour[i].y);
	    }
	    {
	    	mGR.mPlayer.BoostActive = prefs.getBoolean("PlayerBoostActive", false);
	    	mGR.mPlayer.Run = prefs.getBoolean("PlayerRun", false);
	    	mGR.mPlayer.GameOn = prefs.getBoolean("PlayerGameOn", false);
	    	
	    	mGR.mPlayer.img = prefs.getInt("Playerimg", 0);
	    	mGR.mPlayer.pahiye = prefs.getInt("Playerpahiye", 0);
	    	mGR.mPlayer.kitNo = prefs.getInt("PlayerkitNo", 0);
	    	mGR.mPlayer.petrol = prefs.getInt("Playerpetrol", 0);
	    	mGR.mPlayer.Boostfill = prefs.getInt("PlayerBoostfill", 0);
	    	mGR.mPlayer.bullatefire = prefs.getInt("Playerbullatefire", 0);
	    	mGR.mPlayer.citi = prefs.getInt("Playerciti", 1);
	    	mGR.mPlayer.Engine = prefs.getInt("PlayerEngine", 0);
	    	mGR.mPlayer.Gear = prefs.getInt("PlayerGear", 0);
	    	mGR.mPlayer.overCounter = prefs.getInt("PlayeroverCounter", 0);
	    	mGR.mPlayer.Gunfill = prefs.getInt("PlayerGunfill", 0);
	    	
	    	mGR.mPlayer.mDis = prefs.getFloat("PlayermDis", 0);
	    	mGR.mPlayer.Speed = prefs.getFloat("PlayerSpeed", 0);
	    	mGR.mPlayer.boostSpd = prefs.getFloat("PlayerboostSpd", 0);
	    	mGR.mPlayer.x = prefs.getFloat("Playerx", 0);
	    	mGR.mPlayer.y = prefs.getFloat("Playery", 0);
	    	mGR.mPlayer.vx = prefs.getFloat("Playervx", 0);
	    	mGR.mPlayer.p = prefs.getFloat("Playerp", 0);
	    	mGR.mPlayer.vp = prefs.getFloat("Playervp", 0);
	    	mGR.mPlayer.mBegin = prefs.getFloat("Playerbegin", -.50f);
	    }
	    for(int i=0;i<mGR.mZombie.length;i++){
	    	mGR.mZombie[i].no = prefs.getInt("Zombieno"+i, 0);
	    	mGR.mZombie[i].x = prefs.getFloat("Zombiex"+i, 0);
	    	mGR.mZombie[i].y = prefs.getFloat("Zombiey"+i, 0);
	    }
	    for(int i=0;i<mGR.mTukde.length;i++){
	    	mGR.mTukde[i].no = prefs.getInt("Tukdeno"+i, 0);
	    	mGR.mTukde[i].img = prefs.getInt("Tukdeimg"+i, 0);
	    	mGR.mTukde[i].x = prefs.getFloat("Tukdex"+i, 0);
	    	mGR.mTukde[i].y = prefs.getFloat("Tukdey"+i, 0);
	    	mGR.mTukde[i].vx = prefs.getFloat("Tukdevx"+i, 0);
	    	mGR.mTukde[i].vy = prefs.getFloat("Tukdevy"+i, 0);
	    }
	    for(int i=0;i<mGR.mBox.length;i++){
	    	mGR.mBox[i].down = prefs.getBoolean("Boxdw"+i, false);
	    	mGR.mBox[i].x = prefs.getFloat("Boxx"+i, 0);
	    	mGR.mBox[i].y = prefs.getFloat("Boxy"+i, 0);
	    	mGR.mBox[i].vy = prefs.getFloat("Boxvy"+i, 0);
	    }
	    for(int i=0;i<mGR.mSBox.length;i++){
	    	mGR.mSBox[i].down = prefs.getBoolean("SBoxdw"+i, false);
	    	mGR.mSBox[i].x = prefs.getFloat("SBoxx"+i, 0);
	    	mGR.mSBox[i].y = prefs.getFloat("SBoxy"+i, 0);
	    	mGR.mSBox[i].vy = prefs.getFloat("SBoxvy"+i, 0);
	    }
	    for(int i=0;i<mGR.mCar.length;i++){
	    	
	    	for(int j =0;j<mGR.mCar[i].Engine.length;j++){
	    		mGR.mCar[i].Engine[j] = prefs.getBoolean(j+"mCarEngine"+i, j == 0);
	    	}
	    	for(int j =0;j<mGR.mCar[i].Gear.length;j++){
	    		mGR.mCar[i].Gear[j] = prefs.getBoolean(j+"mCarGear"+i, j == 0);
	    	}
	    	for(int j =0;j<mGR.mCar[i].Wheels.length;j++){
	    		mGR.mCar[i].Wheels[j] = prefs.getBoolean(j+"mCarWheels"+i, j == 0);
	    	}
	    	for(int j =0;j<mGR.mCar[i].Kit.length;j++){
	    		mGR.mCar[i].Kit[j] = prefs.getBoolean(j+"mCarKit"+i, false);
	    	}
	    	mGR.mCar[i].sEngine = prefs.getInt("CarsEngine"+i, 0);
	    	mGR.mCar[i].sGear = prefs.getInt("CarsGear"+i, 0);
	    	mGR.mCar[i].sWheel = prefs.getInt("CarsWheel"+i, 0);
	    	mGR.mCar[i].sKit = prefs.getInt("CarsKit"+i, 0);
	    	mGR.mCar[i].sGun = prefs.getInt("CarsGun"+i, 0);
	    	mGR.mCar[i].Boost = prefs.getInt("CarBoost"+i, 0);
	    	mGR.mCar[i].Petrol = prefs.getInt("CarPetrol"+i, 1);
	    }
	    for(int i=0;i<mGR.mBlood.length;i++){
	    	mGR.mBlood[i].no = prefs.getInt("Bloodno"+i, 0);
	    	mGR.mBlood[i].img = prefs.getInt("Bloodimg"+i, 0);
	    	mGR.mBlood[i].x = prefs.getFloat("Bloodx"+i, 0);
	    	mGR.mBlood[i].y = prefs.getFloat("Bloody"+i, 0);
	    }
		for(int i =0;i<mGR.unclockCar.length;i++)
			mGR.unclockCar[i] = prefs.getBoolean("unclockCar"+i, i == 0);
		
		mGR.addFree = prefs.getBoolean("addFree", mGR.addFree);
		mGR.bamboCont = prefs.getInt("bamboCont", 1);
		mGR.Attack = prefs.getInt("Attack", 0);
		mGR.carSelect = prefs.getInt("carSelect", 0);
		mGR.Popup = prefs.getInt("Popup", 0);
		mGR.Zombie = prefs.getInt("Zombie", 0);
		mGR.setJoin = prefs.getInt("setJoin", 0);
		mGR.Dollor = prefs.getInt("Dollor", 1);
		mGR.story = prefs.getInt("story", 0);
		
		mGR.One = prefs.getFloat("one", mGR.One);
		mGR.two = prefs.getFloat("two", mGR.two);
		mGR.three = prefs.getFloat("three", mGR.three);
		
		mGR.newSbox = prefs.getFloat("newSbox", 0);
		mGR.newZombie = prefs.getFloat("newZombie", 0);
		mGR.newZombieV = prefs.getFloat("newZombieV", 0);
		mGR.mLast = prefs.getFloat("mLast", -.50f);
		if(M.GameScreen == M.GAMEMENU){
			M.play(GameRenderer.mContext, R.drawable.splash);
		}
		if(M.GameScreen == M.GAMEMAP ||M.GameScreen == M.GAMESHOP ){
			M.play(GameRenderer.mContext, R.drawable.garage_map);
		}
		if(M.GameScreen == M.GAMESTORY){
			M.play(GameRenderer.mContext, R.drawable.story);
		}
		if(M.GameScreen == M.GAMEPLAY){
			M.play(GameRenderer.mContext, R.drawable.gameplay_theme1);
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
	
	public void load() {
		try{handlerload.sendEmptyMessage(0);}catch(Exception e){}
	}
	Handler handlerload = new Handler() {public void handleMessage(Message msg) {loadInter();}};

	
	void loadInter(){
		if (!interstitial.isLoaded() && !mGR.addFree) {
			AdRequest adRequest = new AdRequest.Builder()
//					.addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("67701763FB847AEBD3C6EE486475ED94")
			.build();
			interstitial.loadAd(adRequest);
			interstitial.setAdListener(new AdGameListener(this));
		}
	}

	public void ShowInterstitial() {
		try{handler.sendEmptyMessage(0);}catch(Exception e){}
	}
	Handler handler = new Handler() {public void handleMessage(Message msg) {show();}};

	void show() {
		try {
			if (interstitial != null)
				if (interstitial.isLoaded()) {
					interstitial.show();
				}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			super.onActivityResult(requestCode, resultCode, data);
			System.out.println("Yogesh--->[requestCode = " + requestCode + "] [resultCode = " + resultCode + "] [data = " + data + "]");
			if (requestCode == _InApp.RC_REQUEST) {
				if (!mGR.mInApp.mHelper.handleActivityResult(
						requestCode, resultCode, data)) {
					super.onActivityResult(requestCode, resultCode, data);
				} else {
					System.out.println("Yogesh--onActivityResult error = RC_REQUEST");
				}
			}
		} catch (Exception e) {
			System.out.println("Yogesh--onActivityResult error = " + e.toString());
		}
	}
}