package com.tech.game.Audituch;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;


import android.app.Activity;
import android.graphics.Color;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GameEnd extends Activity  implements OnClickListener 
{
	String TAG = "Menu";
	TextView txt0;
	int mlevel=1;
	void callAdds()
	{
		String MY_AD_UNIT_ID = "a14e4a69df7ae78";
		AdView adView = new AdView(this, AdSize.BANNER, MY_AD_UNIT_ID);
        LinearLayout layout = (LinearLayout)findViewById(R.id.addgend);
        layout.addView(adView);
        adView.loadAd(new AdRequest());
	}
	public void onCreate(Bundle savedInstanceState) 
    {
    	super.onCreate(savedInstanceState);
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	setContentView(R.layout.gend);
    	    	
    	txt0=(TextView) findViewById(R.id.T1);
    	String str = ReadSettings();
    	
    	if(str.equalsIgnoreCase("-1"))
    	{
    		WriteSettings("40");
    		txt0.setText("Highest punches : 40");
    	}
    	else
    	{
    		txt0.setText("Essy Level : "+str);
    	}
    	txt0.setTextColor(Color.WHITE);
    	
    	callAdds();
    	
	 }	   
	public void onClick(View v) {
		 switch (v.getId()) {
		    case R.id.T1:
		    	
		    	break;
		    }
		
	}
	 
	 public String ReadSettings(){
         FileInputStream fIn = null;
         byte[] inputBuffer = new byte[255];
         String data = null;
         try{
        	 fIn = openFileInput("level.dat");
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
        	  return "-1";
          return data;
     }
	 public void WriteSettings(String data)
	 {
		 
	     FileOutputStream fOut = null;
        OutputStreamWriter osw = null;
        try{
       	 fOut = openFileOutput("level.dat",0);      
       	 osw = new OutputStreamWriter(fOut);
       	 osw.write(data);
       	 osw.flush();
       	 
        }
        catch (Exception e) {      
       	 e.printStackTrace();
       	 
        }
        finally {
       	 try {
           	 osw.close();
           	 fOut.close();
            } catch (IOException e) {
           	 e.printStackTrace();
            }
        }
    }
	 
	
}

