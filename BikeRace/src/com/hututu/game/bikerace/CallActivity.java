package com.hututu.game.bikerace;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import rajawali.RajawaliActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class CallActivity extends RajawaliActivity {

	static Context mContext;
	private HTTRenderer mGR;
	InterstitialAd interstitial;
	
	
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		WindowManager mWinMgr = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		M.ScreenWidth = mWinMgr.getDefaultDisplay().getWidth();
		M.ScreenHieght = mWinMgr.getDefaultDisplay().getHeight();
		mContext = this;
		
		
		super.onCreate(savedInstanceState);
		mGR = new HTTRenderer(this);
		mGR.setSurfaceView(mSurfaceView);
		super.setRenderer(mGR);
		
		interstitial = new InterstitialAd(this);
		interstitial.setAdUnitId(getString(R.string.INTERSTITIAL_ID));
		LinearLayout placeHolder = new LinearLayout(this);
		getLayoutInflater().inflate(R.layout.game, placeHolder);
		mLayout.addView(placeHolder);
		
	}
	@Override
	
	protected void onResume()
	{
		System.out.println("Resume!~~~~~~~~~~~~~~~~~~~~~~~");
		
		super.onResume();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.d("----------------=>  " + keyCode, "   -----------    ");
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (M.GameScreen != M.GAMEMENU) {
				M.GameScreen = M.GAMEMENU;
			} else {
				Exit();
			}
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK)
			return false;
		return super.onKeyUp(keyCode, event);
	}

	void Exit() {
		new AlertDialog.Builder(this)
				.setIcon(R.drawable.icon)
				.setTitle("Do you want to exit?")
				.setPositiveButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {}})
				.setNegativeButton("Yes",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
//					mGR.root.counter = 0;
					finish();
					M.GameScreen = M.GAMELOGO;
				}}).show();
	}
	void Buy() {
		new AlertDialog.Builder(this)
				.setIcon(R.drawable.icon)
				.setTitle("You don't have sufficient coin.")
				.setPositiveButton("Later", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {}})
				.setNegativeButton("Buy",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					
				}}).show();
	}
	

	public void load() {
		try{handlerload.sendEmptyMessage(0);}catch(Exception e){}
	}
	Handler handlerload = new Handler() {public void handleMessage(Message msg) {loadInter();}};

	long time = System.currentTimeMillis()-120000;

	private void loadInter() {
		interstitial = new InterstitialAd(this);
		interstitial.setAdUnitId(getString(R.string.INTERSTITIAL_ID));
		if (!interstitial.isLoaded()) {
			AdRequest adRequest = new AdRequest.Builder()
			// .addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("67701763FB847AEBD3C6EE486475ED94")
					.build();
			interstitial.loadAd(adRequest);
			interstitial.setAdListener(new AdGameListener(this));
		}
	}

	public void ShowInterstitial() {
			try{handler.sendEmptyMessage(0);}catch(Exception e){}
	}
	Handler handler = new Handler() {public void handleMessage(Message msg) {show();}};

	private void show1() {
		try {
			if (interstitial != null)
				if (interstitial.isLoaded()) {
					interstitial.show();
				}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}	
	
	public void show () {
	    this.runOnUiThread(new Runnable() {
	        @Override
	        public void run() {
	        	try {
	    			if (interstitial != null)
	    				if (interstitial.isLoaded()) {
	    					interstitial.show();
	    				}
	    		} catch (Exception e) {
	    			System.out.println(e.toString());
	    		}
	        }
	    });
	}
}
