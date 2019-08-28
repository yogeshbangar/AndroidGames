package com.hututu.game.speeddragracing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class ScreenReceiver extends BroadcastReceiver {
	
	 public static boolean wasScreenOn = true;
	    @Override
	    public void onReceive(Context context, Intent intent)
	    {
	    	System.out.println("~~~~~~~~~~~!!!!!!!!!~~~~~~~" + intent.getAction());
	        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF))
	        {
	            wasScreenOn = false;
	        }
	        if(intent.getAction().equals(Intent.ACTION_USER_PRESENT))
	        {
	        	wasScreenOn = true;
        	  if(M.setValue && M.GameScreen!=M.GAMELOGO)
	      			M.play(GameRenderer.mContext, R.drawable.background);
	      		else
	      			M.loopstop();
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


// Paste In Start
//   BroadcastReceiver mReceiver;
//   // Recievier 
//	    IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
//        filter.addAction(Intent.ACTION_SCREEN_OFF);
//        filter.addAction(Intent.ACTION_USER_PRESENT);
//        mReceiver = new ScreenReceiver();
//       registerReceiver(mReceiver, filter);
//   // Recievier
