package com.oneday.game24.frongjumpingjump;
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
	        	wasScreenOn = true;
	        	switch (M.GameScreen)
	        	{
				  case M.GAMEMENU: case M.GAMESETTING:case M.GAMEHELP:case M.GAMEABTUS:case M.GAMEADFREE:
					  if(M.SetBG)
						  M.SplashPlay(GameRenderer.mContext,R.drawable.menu_theme);
					  else
						  M.BgStop();
					break;
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
