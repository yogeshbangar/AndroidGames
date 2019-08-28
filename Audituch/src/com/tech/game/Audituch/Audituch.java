/*package com.tech.game.Audituch;

import android.app.Activity;
import android.os.Bundle;

public class Audituch extends Activity {
    *//** Called when the activity is first created. *//*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}*/
package com.tech.game.Audituch;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class Audituch extends Activity {
 /** Called when the activity is first created. */
	
	void callAdds()
	{
//		String MY_AD_UNIT_ID = "a14e4a69df7ae78";
//		AdView adView = new AdView(this, AdSize.BANNER, MY_AD_UNIT_ID);
//        LinearLayout layout = (LinearLayout)findViewById(R.id.addmain);
//        layout.addView(adView);
//        adView.loadAd(new AdRequest());
	}
	private static final int MANOSCR = 0;
	private static final int AUDISCR = 1;
	private static final int STOPSPLASH = 2;
	private static final long SPLASHTIME = 2000;
	static int i =0;
	
	private Handler splashHandler = new Handler() 
	{
		@Override
		public void handleMessage(Message msg) {
			RelativeLayout mlayout= (RelativeLayout)findViewById(R.id.sp2);
			ImageView mImgVeiw = (ImageView)findViewById(R.id.spimg);
			Message msg1 = new Message();
			switch (msg.what) {
			case MANOSCR:
				/*mlayout.setBackgroundColor(Color.BLACK);
				mImgVeiw.setImageResource(R.drawable.audi);
				msg1.what = AUDISCR; 
				splashHandler.sendMessageDelayed(msg1, SPLASHTIME/2);
				break;
			case AUDISCR:*/
				 
				/*mlayout.setBackgroundResource(R.drawable.splash);
				mImgVeiw.setVisibility(50);
				mImgVeiw.setImageBitmap(null);
				msg1.what = STOPSPLASH; 
				splashHandler.sendMessageDelayed(msg1, SPLASHTIME);
				break;
			case STOPSPLASH:*/
				Intent myIntent = new Intent(Audituch.this, Base.class); //yogesh
				startActivity(myIntent);
				finish();
				break;
			}
			super.handleMessage(msg);
		}
	};
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);
		callAdds();
		Message msg = new Message(); 
		msg.what = MANOSCR; 
		splashHandler.sendMessageDelayed(msg, 100);
	}
}