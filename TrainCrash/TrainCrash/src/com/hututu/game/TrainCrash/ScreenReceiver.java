package com.hututu.game.TrainCrash;
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
	            M.StopSound();
	            System.out.println("Innnnn Offfffff");
	        }
	        if(intent.getAction().equals(Intent.ACTION_USER_PRESENT))
	        {
	        	if(M.GameScreen == M.GAMEWORLD || M.GameScreen == M.GAMEOVER ||  M.GameScreen == M.GAMEWIN ||  M.GameScreen == M.GAMELEVEL)
	        	{
	        		if(M.setValue)
	        		 M.SplashPlay(GameRenderer.mContext,R.drawable.ui);
	        	}
	        	wasScreenOn = true;
	        }
	    }
     public void  onSaveInstanceState(Bundle outState)
     {
//    	 System.out.println("In Saveeeeeeeeeeeeee");
     }
     public void onRestoreInstanceState(Bundle inState)
     {
//    	 System.out.println("In Restoreeeeeeeeeeee");
     }
}
