package com.oneday.games24.fightersofocean;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
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
//	private Prm mAir; //Declare here
	private InterstitialAd interstitialAd;
//	AdView adView;//,adViewBig,adHouse;
	
	AdView adView = null;
//	AdView adViewBig = null;
//	AdView adHouse = null;//AdHouse
	GameRenderer mGR = null;
	@SuppressWarnings("deprecation")
	void callAdds()
	{
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
	}
	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);//OnlyOneChange
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
			switch (M.GameScreen) {
			case M.GAMEMENU:
				get();
				break;
			case M.GAMEPLAY:
				M.GameScreen = M.GAMEPUSE;
				M.stop();
				break;
			case M.GAMEOVER: case M.GAMEWIN: case M.GAMESHOP:
			case M.GAMEHELP: case M.GAMEABUT: case M.GAMEPUSE: 
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
		mGR.resumeCounter = 0;
		M.stop();
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
	    Editor editor = prefs.edit();
	    if(M.GameScreen == M.GAMEPLAY)
	    	M.GameScreen = M.GAMEPUSE;
	    
	    
	    editor.putInt("a", M.GameScreen);
	    editor.putBoolean("b", M.setValue);
	    editor.putInt("c", M.PlayerLife);
	    editor.putInt("d", M.mOppSet);
	    
	    editor.putInt("e", mGR.mAdd);
	    editor.putInt("f", mGR.mLevel);
	    editor.putInt("g", mGR.mWait);
	    editor.putInt("h", mGR.GunPoint);
	    editor.putInt("i", mGR.PowerPoint);
	    editor.putInt("j", mGR.BoatPoint);
	    editor.putInt("k", mGR.SelPoint);
	    editor.putInt("l", mGR.mMove);
	    editor.putInt("m", mGR.Bullet);
	    editor.putInt("n", mGR.lastLife);
	    editor.putInt("o", mGR.bombBlast);
	    
	    editor.putFloat("p", mGR.NetBlast);
	    editor.putFloat("q", mGR.mReload);
	    editor.putFloat("r", mGR.Load);
		for(int i=0;i<2;i++){
			editor.putFloat("s"+i, mGR.mBGMove1[i]);
			editor.putFloat("t"+i, mGR.mBGMove2[i]);
			editor.putFloat("u"+i, mGR.mBGMove3[i]);
			editor.putFloat("v"+i, mGR.mBGMove4[i]);
		}
		
		for(int i=0;i<mGR.mOpp.length;i++){
			editor.putFloat("x"+i, mGR.mOpp[i].x);
			editor.putFloat("y"+i, mGR.mOpp[i].y);
			editor.putFloat("z"+i, mGR.mOpp[i].vx);
			editor.putInt("aa"+i, mGR.mOpp[i].mLife);
			editor.putInt("ab"+i, mGR.mOpp[i].mNo);
		}
		{
			editor.putBoolean("ac", mGR.mPlayer.tuch);
			
			editor.putInt("ad", mGR.mPlayer.mB);
			editor.putInt("ae", mGR.mPlayer.mBullet);
			editor.putInt("af", mGR.mPlayer.mDamageBy);
			editor.putInt("ag", mGR.mPlayer.mNoKill);
			editor.putInt("ah", mGR.mPlayer.mKillTarget);
			editor.putInt("ai", mGR.mPlayer.mCoin);
			editor.putInt("aj", mGR.mPlayer.mLCoin);
			editor.putInt("ak", mGR.mPlayer.mXP);
			editor.putInt("al", mGR.mPlayer.FirBullate);
			editor.putInt("am", mGR.mPlayer.Accurecy);
			editor.putInt("an", mGR.mPlayer.Power);
			editor.putInt("ao", mGR.mPlayer.BoatMan);
			editor.putInt("ap", mGR.mPlayer.Bomb);
			editor.putInt("aq", mGR.mPlayer.Net);
			editor.putInt("ar", mGR.mPlayer.MaxLife);
			editor.putInt("as", mGR.mPlayer.mGunType);
			
			editor.putFloat("at", mGR.mPlayer.x);
			editor.putFloat("au", mGR.mPlayer.y);
			editor.putFloat("av", mGR.mPlayer.tx);
			editor.putFloat("ax", mGR.mPlayer.ty);
			
		}
		for(int i=0;i<mGR.mGun.length;i++)
		{
			editor.putFloat("ay"+i, mGR.mGun[i].x);
			editor.putBoolean("az"+i, mGR.mGun[i].mIsPurchased);
		}
		for(int i=0;i<mGR.mPower.length;i++)
		{
			editor.putFloat("ba"+i, mGR.mPower[i].x);
			editor.putBoolean("bb"+i, mGR.mPower[i].mIsPurchased);
		}
		for(int i=0;i<mGR.mUBoat.length;i++)
		{
			editor.putFloat("bc"+i, mGR.mUBoat[i].x);
			editor.putBoolean("bd"+i, mGR.mUBoat[i].mIsPurchased);
			editor.putInt("be"+i, mGR.mUBoat[i].mCost);
		}
		
	    
	    editor.commit();
	}
	void resume()
	{
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = prefs.getInt("a", M.GAMELOGO);
		M.setValue = prefs.getBoolean("b", M.setValue);
		M.PlayerLife = prefs.getInt("c", M.PlayerLife);
		M.mOppSet = prefs.getInt("d", M.mOppSet);
	    
		mGR.mAdd = prefs.getInt("e", mGR.mAdd);
		mGR.mLevel = prefs.getInt("f", mGR.mLevel);
		mGR.mWait = prefs.getInt("g", mGR.mWait);
		mGR.GunPoint = prefs.getInt("h", mGR.GunPoint);
		mGR.PowerPoint = prefs.getInt("i", mGR.PowerPoint);
		mGR.BoatPoint = prefs.getInt("j", mGR.BoatPoint);
		mGR.SelPoint = prefs.getInt("k", mGR.SelPoint);
		mGR.mMove = prefs.getInt("l", mGR.mMove);
		mGR.Bullet = prefs.getInt("m", mGR.Bullet);
		mGR.lastLife = prefs.getInt("n", mGR.lastLife);
		mGR.bombBlast = prefs.getInt("o", mGR.bombBlast);
	    
		mGR.NetBlast = prefs.getFloat("p", mGR.NetBlast);
		mGR.mReload = prefs.getFloat("q", mGR.mReload);
		mGR.Load = prefs.getFloat("r", mGR.Load);
		for(int i=0;i<2;i++){
			mGR.mBGMove1[i] = prefs.getFloat("s"+i, mGR.mBGMove1[i]);
			mGR.mBGMove2[i] = prefs.getFloat("t"+i, mGR.mBGMove2[i]);
			mGR.mBGMove3[i] = prefs.getFloat("u"+i, mGR.mBGMove3[i]);
			mGR.mBGMove4[i] = prefs.getFloat("v"+i, mGR.mBGMove4[i]);
		}
		
		for(int i=0;i<mGR.mOpp.length;i++){
			mGR.mOpp[i].x = prefs.getFloat("x"+i, mGR.mOpp[i].x);
			mGR.mOpp[i].y = prefs.getFloat("y"+i, mGR.mOpp[i].y);
			mGR.mOpp[i].vx = prefs.getFloat("z"+i, mGR.mOpp[i].vx);
			mGR.mOpp[i].mLife = prefs.getInt("aa"+i, mGR.mOpp[i].mLife);
			mGR.mOpp[i].mNo = prefs.getInt("ab"+i, mGR.mOpp[i].mNo);
		}
		{
			mGR.mPlayer.tuch = prefs.getBoolean("ac", mGR.mPlayer.tuch);
			
			mGR.mPlayer.mB = prefs.getInt("ad", mGR.mPlayer.mB);
			mGR.mPlayer.mBullet = prefs.getInt("ae", mGR.mPlayer.mBullet);
			mGR.mPlayer.mDamageBy = prefs.getInt("af", mGR.mPlayer.mDamageBy);
			mGR.mPlayer.mNoKill = prefs.getInt("ag", mGR.mPlayer.mNoKill);
			mGR.mPlayer.mKillTarget = prefs.getInt("ah", mGR.mPlayer.mKillTarget);
			mGR.mPlayer.mCoin = prefs.getInt("ai", mGR.mPlayer.mCoin);
			mGR.mPlayer.mLCoin = prefs.getInt("aj", mGR.mPlayer.mLCoin);
			mGR.mPlayer.mXP = prefs.getInt("ak", mGR.mPlayer.mXP);
			mGR.mPlayer.FirBullate = prefs.getInt("al", mGR.mPlayer.FirBullate);
			mGR.mPlayer.Accurecy = prefs.getInt("am", mGR.mPlayer.Accurecy);
			mGR.mPlayer.Power = prefs.getInt("an", mGR.mPlayer.Power);
			mGR.mPlayer.BoatMan = prefs.getInt("ao", mGR.mPlayer.BoatMan);
			mGR.mPlayer.Bomb = prefs.getInt("ap", mGR.mPlayer.Bomb);
			mGR.mPlayer.Net = prefs.getInt("aq", mGR.mPlayer.Net);
			mGR.mPlayer.MaxLife = prefs.getInt("ar", mGR.mPlayer.MaxLife);
			mGR.mPlayer.mGunType = prefs.getInt("as", mGR.mPlayer.mGunType);
			
			mGR.mPlayer.x = prefs.getFloat("at", mGR.mPlayer.x);
			mGR.mPlayer.y = prefs.getFloat("au", mGR.mPlayer.y);
			mGR.mPlayer.tx = prefs.getFloat("av", mGR.mPlayer.tx);
			mGR.mPlayer.ty = prefs.getFloat("ax", mGR.mPlayer.ty);
			
		}
		for(int i=0;i<mGR.mGun.length;i++)
		{
			mGR.mGun[i].x = prefs.getFloat("ay"+i, mGR.mGun[i].x);
			mGR.mGun[i].mIsPurchased = prefs.getBoolean("az"+i, mGR.mGun[i].mIsPurchased);
		}
		for(int i=0;i<mGR.mPower.length;i++)
		{
			mGR.mPower[i].x = prefs.getFloat("ba"+i, mGR.mPower[i].x);
			mGR.mPower[i].mIsPurchased = prefs.getBoolean("bb"+i, mGR.mPower[i].mIsPurchased);
		}
		for(int i=0;i<mGR.mUBoat.length;i++)
		{
			mGR.mUBoat[i].x = prefs.getFloat("bc"+i, mGR.mUBoat[i].x);
			mGR.mUBoat[i].mIsPurchased = prefs.getBoolean("bd"+i, mGR.mUBoat[i].mIsPurchased);
			mGR.mUBoat[i].mCost = prefs.getInt("be"+i, mGR.mUBoat[i].mCost);
		}
		
	    mGR.resumeCounter = 50;
	}
	void get()
	{
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Do you want to Exit?")
	    .setNeutralButton("More",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
	    	mGR.root.MoreGame();
    	       }}).setPositiveButton("No",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
    	       }}).setNegativeButton("Yes",new DialogInterface.OnClickListener() {
		    	   public void onClick(DialogInterface dialog, int which) {
		    		   mGR.root.Counter = 0;
		    		   finish();
		    		   M.GameScreen=M.GAMELOGO;
		       }}).show();
	}
	void Massage(String msg)
	{
		new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle(msg)
	    .setPositiveButton("Ok",new DialogInterface.OnClickListener() 
	    {
	    	public void onClick(DialogInterface dialog, int which) 
	    	{
	    	   
	    	}
    	}).show();
	}
	public void loadInter() {
		try{handlerInter.sendEmptyMessage(0);}catch(Exception e){}
	}
	Handler handlerInter = new Handler() {public void handleMessage(Message msg) {load();}};
	private void load(){
		if (!interstitialAd.isLoaded()) {
			AdRequest adRequest = new AdRequest.Builder().build();
			interstitialAd.loadAd(adRequest);
			interstitialAd.setAdListener(new ToastAdListener(this));
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

}