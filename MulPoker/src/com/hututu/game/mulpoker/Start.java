package com.hututu.game.mulpoker;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.games.Games;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Start extends BaseGameActivity 
{
	int _keyCode = 0;
	GameRenderer mGR = null;
	private static Context CONTEXT;
	private InterstitialAd interstitial;
	ToastAdListener mToastAdListener;
	
	AdView adView = null;
	void callAdds()
	{
		System.out.println(Build.VERSION.CODENAME+"   "
				+Build.VERSION.INCREMENTAL+"   "
				+Build.VERSION.RELEASE+"   "
				+Build.VERSION.SDK+"   "
				+Build.VERSION.SDK_INT+"   "
				);
		
		adView = (AdView) this.findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder()
		.addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("67701763FB847AEBD3C6EE486475ED94")
		.build();
		adView.loadAd(adRequest);
		adView.setAdListener(new AdListener() {
			public void onAdLoaded() {
				adView.bringToFront();
			}
		});
	}
	public void onCreate(Bundle savedInstanceState) 
	{
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
	    callAdds();
	}
	
	public static Context getContext() {
	        return CONTEXT;
	}
	@Override 
	public void onPause () {
		M.stop(CONTEXT);
		super.onPause();
	}
	@Override 
	public void onResume() {
		super.onResume();
		//view.onResume();
	}
	public void onDestroy()
	{
		super.onDestroy();
	}
	void loadInter(){
		if (!interstitial.isLoaded()) {
			AdRequest adRequest = new AdRequest.Builder()
					.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
					.addTestDevice("67701763FB847AEBD3C6EE486475ED94").build();
			interstitial.loadAd(adRequest);
			interstitial.setAdListener(new ToastAdListener(this));
		}
	}
	public void ShowInterstitial() {
		// If Ads are loaded, show Interstitial else show nothing.
		if (interstitial.isLoaded()) {
			interstitial.show();
			mGR.Intertitial = false;
		}
	}
	public boolean isLoad()
	{
		return interstitial.isLoaded();
	}
	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		
	}

	void Submit(int ID, int val) {
		Games.Leaderboards.submitScore(getApiClient(), getString(ID), val);
	}
	void unloackAchive(int ID) {
		if (isSignedIn()) {
			Games.Achievements.unlock(getApiClient(), getString(ID));
		}
	}
	int RC_UNUSED =5501;
    public void onShowAchievementsRequested() {
        if (isSignedIn()) {
            startActivityForResult(Games.Achievements.getAchievementsIntent(getApiClient()),RC_UNUSED);
        } else {
//            showAlert(getString(R.string.achievements_not_available));
        }
    }

    public void onShowLeaderboardsRequested() {
        if (isSignedIn()) {
            startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(getApiClient()),RC_UNUSED);
        } else {
//            showAlert(getString(R.string.leaderboards_not_available));
        }
    }
}