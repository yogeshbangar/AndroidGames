package com.robotics.app.funnyfacemaker;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import android.content.Context;
public class AppAdListener extends AdListener {
    
    @Override
    public void onAdLoaded() {
//    	if(mStart.IsStart)
    	{
    		this.mStart.ShowInterstitial();
//    		mStart.IsStart = false;
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
    }

    @Override
    public void onAdClosed() {
    }

    @Override
    public void onAdLeftApplication() {
    }
    Start mStart;
    public AppAdListener(Context context) {
        this.mStart = (Start)context;
    }

}
