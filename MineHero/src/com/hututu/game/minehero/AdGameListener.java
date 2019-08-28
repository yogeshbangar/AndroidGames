package com.hututu.game.minehero;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import android.content.Context;
public class AdGameListener extends AdListener {
    Start mStart;
    public AdGameListener(Context context) {
        this.mStart = (Start)context;
    }

    @Override
    public void onAdLoaded() {
//    	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~onAdLoaded~~~~~~~~~~~~~~~~~~~~~~~");
    	if(mStart.oncreate){
    		this.mStart.ShowInterstitial();
    		mStart.oncreate = false;
    	}
    }

    @Override
    public void onAdFailedToLoad(int errorCode) {
        String errorReason = "";
        switch(errorCode) {
            case AdRequest.ERROR_CODE_INTERNAL_ERROR:
                errorReason = "Internal error";
                break;
            case AdRequest.ERROR_CODE_INVALID_REQUEST:
                errorReason = "Invalid request";
                break;
            case AdRequest.ERROR_CODE_NETWORK_ERROR:
                errorReason = "Network Error";
                break;
            case AdRequest.ERROR_CODE_NO_FILL:
                errorReason = "No fill";
                break;
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~ "+errorReason+" ~~~~~~~~~~~~~~~~~~~~~~~");
    }

    @Override
    public void onAdOpened() {
//    	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~onAdOpened~~~~~~~~~~~~~~~~~~~~~~~");
    }

    @Override
    public void onAdClosed() {
//    	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~onAdClosed~~~~~~~~~~~~~~~~~~~~~~~");
    }

    @Override
    public void onAdLeftApplication() {
//    	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~onAdLeftApplication~~~~~~~~~~~~~~~~~~~~~~~");
    }
}
