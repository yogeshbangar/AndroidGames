package com.hututu.game.extrememotoracer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class ScreenReceiver extends BroadcastReceiver {
	
	 public static boolean wasScreenOn = true;
	    @Override
	    public void onReceive(Context context, Intent intent)
	    {
	        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF))
	        {
	            wasScreenOn = false;
	        }
	        if(intent.getAction().equals(Intent.ACTION_USER_PRESENT))
	        {
	        	wasScreenOn = true;
	            if(M.GameScreen == M.GameMenu || M.GameScreen == M.GameCarSelection /*||M.GameScreen == M.GameSetting*/)
	            {
	            	M.Splashplay(context,R.drawable.splash);
	            }
	        }
	    }
     public void  onSaveInstanceState(Bundle outState)
     {
    	 System.out.println("In Saveeeeeeeeeeeeee");
     }
     public void onRestoreInstanceState(Bundle inState)
     {
    	 System.out.println("In Restoreeeeeeeeeeee");
     }
}
