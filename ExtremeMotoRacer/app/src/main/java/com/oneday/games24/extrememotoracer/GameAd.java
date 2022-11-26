package com.oneday.games24.extrememotoracer;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;

import java.util.Arrays;
import java.util.List;

public class GameAd {

    Activity mContext;
    private InterstitialAd mInterstitialAd;
    private RewardedInterstitialAd rewardedInterstitialAd;
    String interstitialAdId="ca-app-pub-7665074309496944/3569497715";
    private String TAG = "GameAd";
    void initAds(Activity activity)
    {
        mContext = activity;
        interstitialAdId = activity.getString(R.string.INTERSTITIAL_ID);

        List<String> testDeviceIds = Arrays.asList("C0F5353BC2B3811D76E7FC7321822645");
        RequestConfiguration configuration =  new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
        MobileAds.initialize(mContext, initializationStatus -> {
            MobileAds.setRequestConfiguration(configuration);
            loadInterstitialAd();
            loadReward();
        });
    }
    void loadInterstitialAd()
    {

        mContext.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AdRequest adRequest = new AdRequest.Builder().build();

                InterstitialAd.load(mContext,interstitialAdId, adRequest,
                        new InterstitialAdLoadCallback() {
                            @Override
                            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                                // The mInterstitialAd reference will be null until
                                // an ad is loaded.
                                mInterstitialAd = interstitialAd;
                                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        // Called when fullscreen content is dismissed.
                                        Log.d(TAG, "The ad was dismissed.");
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        // Called when fullscreen content failed to show.
                                        Log.d(TAG, "The ad failed to show.");
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        // Called when fullscreen content is shown.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        //mInterstitialAd = null;
                                        loadInterstitialAd();
                                        Log.d(TAG, "The ad was shown.");
                                    }
                                });
                                Log.i("loadInterstitialAd", "onAdLoaded");
                            }

                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                // Handle the error
                                Log.i("loadInterstitialAd", loadAdError.getMessage());
                                //mInterstitialAd = null;
                                loadInterstitialAd();
                            }
                        });
            }
        });

    }
    void showInterstitialAd(){
        Log.d(TAG, "showInterstitialAd");
        mContext.runOnUiThread(new Runnable() {
               @Override
               public void run() {
                   if (mInterstitialAd != null) {
                       Log.d(TAG,mContext+"");
                       mInterstitialAd.show(mContext);
                   } else {
                       Log.d(TAG, "The interstitial ad wasn't ready yet.");
                   }
               }
       });
    }
    public void loadReward(){
        RewardedInterstitialAd.load(mContext, mContext.getString(R.string.REWARD_ID),
                new AdRequest.Builder().build(),  new RewardedInterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(RewardedInterstitialAd ad) {
                        Log.d(TAG, "Ad was loaded.");
                        rewardedInterstitialAd = ad;
                    }
                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        Log.d(TAG, loadAdError.toString());
                        rewardedInterstitialAd = null;
                    }
                });
    }
    public void ShowRewardVideo(){
        Log.d(TAG, "ShowRewardVideo~~~.");
        mContext.runOnUiThread(new Runnable(){
            @Override
            public void run() {
                showReward();
            }
        });
    }
    void showReward(){
        Log.d(TAG, "showReward@~~~.");
        final boolean[] isReward = new boolean[1];
        if(rewardedInterstitialAd == null)return;
        rewardedInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
            @Override
            public void onAdShowedFullScreenContent() {
                Log.d(TAG, "Ad was shown.");
                rewardedInterstitialAd = null;
            }

            @Override
            public void onAdFailedToShowFullScreenContent(AdError adError) {
                Log.d(TAG, "Ad failed to show");

            }
            @Override
            public void onAdImpression() {
                // Called when an impression is recorded for an ad.
                Log.d(TAG, "Ad recorded an impression.");
            }

            @Override
            public void onAdDismissedFullScreenContent() {
                loadReward();
                Log.d(TAG, "Ad was dismissed.");
            }

        });
        Log.d(TAG, "rewardedInterstitialAd~~~~!!!!~~");
        if (rewardedInterstitialAd != null) {
            Activity activityContext = mContext;
            rewardedInterstitialAd.show(activityContext, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    // Handle the reward.
                    int rewardAmount = rewardItem.getAmount();
                    String rewardType = rewardItem.getType();
                    int coin = Player.mTotalCoin + 2500;
                    SharedPreferences prefs = mContext.getSharedPreferences("X", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("PlayermTotalCoin", coin);
                    editor.commit();
                    GameRenderer.mStart.mGR.isReword = true;
                    Log.d(TAG, coin+"  The user earned the reward.  "+rewardAmount);

                }
            });
        } else {
            Log.d(TAG, "The rewarded ad wasn't ready yet.");
        }
    }

}
