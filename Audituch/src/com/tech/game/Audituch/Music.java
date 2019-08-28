

package com.tech.game.Audituch;

import android.content.Context;
import android.media.MediaPlayer;

public class Music {
   private static MediaPlayer mp = null;

   public static boolean setValue=true;
   private static MediaPlayer mpEffect = null;
   
   /** Stop old song and start new one */
   public static void play(Context context, int resource) 
   {
      stop(context);
      if (setValue) {
         mp = MediaPlayer.create(context, resource);
         System.out.println("rsource is whaty"+resource);
         if(resource!=2131099648)
         mp.setLooping(true);
         mp.start();
      }
   }
   

   /** Stop the music */
   public static void stop(Context context) { 
      if (mp != null)
      {
         mp.stop();
         mp.release();
         mp = null;
      }
      if (mpEffect != null)
      {
    	  mpEffect.stop();
    	  mpEffect.release();
    	  mpEffect = null;
      }
   }
   
  
   public static void playEffect(Context context, int resource) {
		//if(Prefs.getEffect(context))
	   if(mpEffect==null)
	   {
	   		mpEffect = MediaPlayer.create(context, resource);
	   }
	   mpEffect.start();
		
	}
   
}
