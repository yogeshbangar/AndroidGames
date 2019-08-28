package com.tech.game.Audituch;


import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;



public class Base extends Activity 
{
	public game mgame;
	void callAdds()
	{
//		String MY_AD_UNIT_ID = "a14e4a69df7ae78";
//		AdView adView = new AdView(this, AdSize.BANNER, MY_AD_UNIT_ID);
//        LinearLayout layout = (LinearLayout)findViewById(R.id.addgame);
//        layout.addView(adView);
//        adView.loadAd(new AdRequest());
	}
    public void onCreate(Bundle savedInstanceState) 
    {
    	super.onCreate(savedInstanceState);
    	getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    	this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	setContentView(R.layout.game);
    	callAdds();
	 }	   
    protected void onResume() {
    	super.onResume();
    	
    }
    
    protected void onDestroy() {
    	super.onDestroy();
    	
    	
    }
   
}
