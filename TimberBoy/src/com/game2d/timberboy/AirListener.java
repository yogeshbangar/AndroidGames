package com.game2d.timberboy;
import com.qfzikeef.iqrcfcmi148442.AdListener;

public class AirListener implements AdListener{

	@Override
	public void noAdAvailableListener() {
		// TODO Auto-generated method stub
		System.out.println("   noAdAvailableListener     ");
	}

	@Override
	public void onAdCached(AdType arg0) {
		// TODO Auto-generated method stub
		System.out.println("   onAdCached     "+arg0);
	}

	@Override
	public void onAdError(String arg0) {
		// TODO Auto-generated method stub
		System.out.println("   onAdError     "+arg0);
	}

	@Override
	public void onSDKIntegrationError(String arg0) {
		// TODO Auto-generated method stub
		System.out.println("@   onSDKIntegrationError     "+arg0);
		
	}

	@Override
	public void onSmartWallAdClosed() {
		// TODO Auto-generated method stub
		System.out.println("   onSDKIntegrationError     ");
	}

	@Override
	public void onSmartWallAdShowing() {
		// TODO Auto-generated method stub
		System.out.println("   onSmartWallAdShowing     ");
	}
}
