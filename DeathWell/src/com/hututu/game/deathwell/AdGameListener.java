package com.hututu.game.deathwell;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import android.content.Context;
public class AdGameListener extends AdListener {
    Start mStart;
    public AdGameListener(Context context) {
        this.mStart = (Start)context;
    }
    // Classic Racer
    //Cross 50 move in a game to become Classic Racer.
    
    // Sharp Racer
    //Cross 100 move in a game to become Sharp Racer.
    
    // Exalted Racer
    //Cross 200 move in a game to become Exalted Racer.
    
    //Master Racer
    //Cross 1000 moves to become Master Racer.

    //King Racer
    //Cross 5000 moves to become King Racer.
    
    @Override
    public void onAdLoaded() {
//    	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~onAdLoaded~~~~~~~~~~~~~~~~~~~~~~~");
    	if(mStart.OnCreate){
    		this.mStart.ShowInterstitial();
    		mStart.OnCreate =false;
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
