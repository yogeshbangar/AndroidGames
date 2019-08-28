package com.tech.game.Audituch;

import java.io.FileInputStream;
import java.io.IOException;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;




import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;


public class menu extends Activity  implements OnClickListener 
{
	String TAG = "Menu";
	ImageButton mNewGame,mScore,mExit,mSoundButton;
	
	boolean flag = false;
	private MyReceiver reciever;
	String mPath = "str";
	int mSound=0;
	void callAdds()
	{
		String MY_AD_UNIT_ID = "a14e4a69df7ae78";
		AdView adView = new AdView(this, AdSize.BANNER, MY_AD_UNIT_ID);
        LinearLayout layout = (LinearLayout)findViewById(R.id.addmenu);
        layout.addView(adView);
        adView.loadAd(new AdRequest());
	}
	public class MyReceiver extends BroadcastReceiver 
	{
		menu mMenu;
		public MyReceiver(menu Menu) {
			mMenu = Menu;
	    }
		@Override
		public void onReceive(Context context, Intent intent) 
		{
			flag = true;
		   // TODO Auto-generated method stub
			mSound =intent.getIntExtra("Level",2);
		}
	}
	public void onCreate(Bundle savedInstanceState) 
    {
    	super.onCreate(savedInstanceState);
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setButton();
    	reciever = new MyReceiver(this);
    	registerReceiver(reciever, new IntentFilter("Gurba"));
    	callAdds();

	 }	   
	public void onClick(View v) {
		 Intent gEndIntent;
		switch (v.getId()) 
		{
		case R.id.IB0:
			gEndIntent = new Intent(getApplicationContext(), Base.class); //yogesh
			gEndIntent.putExtra("Level", mSound);
			startActivityForResult(gEndIntent,1);
			break;
		    case R.id.IB1:
		    	gEndIntent = new Intent(this, GameEnd.class); //yogesh
		    	startActivity(gEndIntent);
		    	break;
		    case R.id.IB2:
		    	finish();
		    	break;
		    case R.id.IB3:
		    	if(mSound==0)
		    	{
		    		mSound=1;
		    		mSoundButton.setBackgroundResource(R.drawable.soundxoff);
		    	}
		    	else
		    	{
		    		mSound=0;
		    		mSoundButton.setBackgroundResource(R.drawable.soundxon);
		    	}
		    	break;
		}
		
	}
	public String ReadSettings()
	 {
        FileInputStream fIn = null;
        byte[] inputBuffer = new byte[255];
        String data = null;
        try{
       	 fIn = openFileInput("settings.dat");
	          if(fIn!=null)
	          {
	        	  int i = fIn.available();
	        	  fIn.read(inputBuffer);
	        	  data = new String(inputBuffer,0,i);
	          }
         }
         catch (Exception e) {      
       	  e.printStackTrace();
       	 
         }
         finally {
       	  try {
       		  
       		  if(fIn!=null)
       			  fIn.close();
       	  } catch (IOException e) {
       		  e.printStackTrace();
       	  }
       	  
         }	
         if(data==null)
         {
       	  return "0";
         }
         return data;
    }
	void setButton()
	{
		setContentView(R.layout.menu);
		
    	mNewGame = (ImageButton) findViewById(R.id.IB0);
    	mScore = (ImageButton) findViewById(R.id.IB1);
    	mExit= (ImageButton) findViewById(R.id.IB2);
    	mSoundButton = (ImageButton) findViewById(R.id.IB3);    	

    	mNewGame.setOnClickListener(this);
    	mScore.setOnClickListener(this);
    	mSoundButton.setOnClickListener(this);
    	mExit.setOnClickListener(this);
	}
	
}

