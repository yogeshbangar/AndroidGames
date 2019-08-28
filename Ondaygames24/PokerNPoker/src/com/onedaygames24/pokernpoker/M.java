

package com.onedaygames24.pokernpoker;

import java.util.Random;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

public class M 
{
	
	static final byte ROYALFLASH 		= 9;
    static final byte STRAIGHTFLUSH 	= 8;
    static final byte FOUROFKIND		= 7;
    static final byte FULLHOUSE		= 6;
    static final byte FLUSH 			= 5;
    static final byte STRAIGHT		= 4;
    static final byte THREEOFKIND		= 3;
    static final byte TWOPAIR			= 2;
    static final byte PAIR			= 1;
    static final byte NOTHING			= 0;
    static final byte FOLD			=-1;
    static final byte GAMEOUT			=-2;
    static final byte SHOWCARD		=-3;
	
    
    public static final String MARKET 		= "https://play.google.com/store/apps/developer?id=Onedaygame24";
	public static final String LINK 		= "market://details?id=";
	public static final String SHARELINK 	= "https://play.google.com/store/apps/details?id=com.onedaygames24.pokernpoker";
	
	
    
    public static final int MAXAMOUNT= 200;
    public static final int ID		= 3;
	public static Random mRand = new Random();
	public static final String score = "score";
	public static float ScreenWidth,ScreenHieght;
	public static final float mMaxX = 800,mMaxY=480;
	private static MediaPlayer mp 	= null;
	public  static boolean setValue	= true;
	private static MediaPlayer mpEffect = null,mpEffect2=null,mpEffect3=null,mpEffect4=null,mpEffect5=null,mpEffect6=null,mpEffect7=null,mpEffect8=null;
	private static MediaPlayer mpEffect9 = null,mpEffect10 = null,mpEffect11 = null,mpEffect12 = null,mpEffect13 = null,mpEffect14 = null,mpEffect15 = null;
	public static final String MY_AD_UNIT_ID = "ca-app-pub-3395412980708319/3808246185";	//AndroidMarket
	public static final String HOUSE_ADV_ID  = "ca-app-pub-3395412980708319/5284979384";	//AdHouse
	
	public static int GameScreen;
	public static final int GAMELOGO = 0 ,GAMERAISE = 1,GAMEMENU  = 2,GAMEHIGH = 3 ,GAMEPLAY = 4,GAMECONG = 5,GAMEADD=50;
	public static final int GAMEHELP = 6 ,GAMEOVER  = 7,GAMEPAUSE = 8,GAMEINFO = 9 ,GAMEWIN = 10,GAMESPLA = 11,GAMELOAD=51;
	
	
	
	public static void loadSound(Context context)
	{
		try{
			if(mp==null)
				mp = MediaPlayer.create(context, R.drawable.car_serv);//
			
			if(mpEffect==null)
				mpEffect = MediaPlayer.create(context, R.drawable.call1);//
			
			if(mpEffect2==null)
				mpEffect2 = MediaPlayer.create(context, R.drawable.check1);
			
			if(mpEffect3==null)
				mpEffect3 = MediaPlayer.create(context, R.drawable.fold1);//
			
			if(mpEffect4==null)
				mpEffect4 = MediaPlayer.create(context, R.drawable.raise1);//
			
			
			if(mpEffect5==null)
				mpEffect5 = MediaPlayer.create(context, R.drawable.call2);//
			
			if(mpEffect6==null)
				mpEffect6 = MediaPlayer.create(context, R.drawable.check2);
			
			if(mpEffect7==null)
				mpEffect7 = MediaPlayer.create(context, R.drawable.fold2);//
			
			if(mpEffect8==null)
				mpEffect8 = MediaPlayer.create(context, R.drawable.raise2);
		}catch(Exception e){}
	}
	public static void play(Context context, int resource) 
	{
		try{
			stop(context);
			if (setValue) 
			{
				if(mp==null)
					mp = MediaPlayer.create(context, resource);
				
				if(!mp.isPlaying())
				{
					if(resource!=2131099648)
						mp.setLooping(true);
					mp.start();
				}
			}
		}catch(Exception e){}
	}
	public static void playStop() 
	{
		try{
			if (mp != null)
			{
				Log.d("=================","=====================loopStop========================================");
				mp.pause();
				mp.stop();
			}	
		}catch(Exception e){}
	}
	public static void loopStop() 
	{
		try{
//			if (mp != null)
//			{
//				Log.d("=================","=====================loopStop========================================");
//				mp.pause();
//			}	
			sound3Pause();
		}catch(Exception e){}
	}
	public static void stop(Context context) {
		try{
			Log.d("---------------------------------------------","sound stop");
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
			if (mpEffect2 != null)
			{
				mpEffect2.stop();
				mpEffect2.release();
				mpEffect2 = null;
			}
			if (mpEffect3 != null)
			{
				mpEffect3.stop();
				mpEffect3.release();
				mpEffect3 = null;
			}
			if (mpEffect4 != null)
			{
				mpEffect4.stop();
				mpEffect4.release();
				mpEffect4 = null;
			}
			if (mpEffect5 != null)
			{
				mpEffect5.stop();
				mpEffect5.release();
				mpEffect5 = null;
			}
			if (mpEffect6 != null)
			{
				mpEffect6.stop();
				mpEffect6.release();
				mpEffect6 = null;
			}
			if (mpEffect7 != null)
			{
				mpEffect7.stop();
				mpEffect7.release();
				mpEffect7 = null;
			}
			if (mpEffect8 != null)
			{
				mpEffect8.stop();
				mpEffect8.release();
				mpEffect8 = null;
			}
			if (mpEffect9 != null)
			{
				mpEffect9.stop();
				mpEffect9.release();
				mpEffect9 = null;
			}
			if (mpEffect10 != null)
			{
				mpEffect10.stop();
				mpEffect10.release();
				mpEffect10 = null;
			}
			if (mpEffect11 != null)
			{
				mpEffect11.stop();
				mpEffect11.release();
				mpEffect11 = null;
			}
			if (mpEffect12 != null)
			{
				mpEffect12.stop();
				mpEffect12.release();
				mpEffect12 = null;
			}
			if (mpEffect13 != null)
			{
				mpEffect13.stop();
				mpEffect13.release();
				mpEffect13 = null;
			}
			if (mpEffect14 != null)
			{
				mpEffect14.stop();
				mpEffect14.release();
				mpEffect14 = null;
			}
			if (mpEffect15 != null)
			{
				mpEffect15.stop();
				mpEffect15.release();
				mpEffect15 = null;
			}
			
		}catch(Exception e){}
	}
	public static void sound1(Context context, int resource) {
		 if(!setValue)
			 return;
		try{
			if(mpEffect==null)
			{
				mpEffect = MediaPlayer.create(context, resource);
			}
			if(!mpEffect.isPlaying())
				mpEffect.start();
//			else
//				sound2(context,R.drawable.g2);
		}catch(Exception e){}	
	}
	public static void sound2(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect2==null)
			{
				mpEffect2 = MediaPlayer.create(context, resource);
			}
			if(!mpEffect2.isPlaying() && setValue)
				mpEffect2.start();
//			else
//				sound3Play(context,R.drawable.g3);
		}catch(Exception e){}
	}
	public static void sound3(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if (setValue) 
			{
				if(mpEffect3==null)
					mpEffect3 = MediaPlayer.create(context, resource);
				if(!mpEffect3.isPlaying())
				{
					//if(resource!=2131099648)
						//mpEffect3.setLooping(true);
					mpEffect3.start();
				}
//				else
//					sound4(context,R.drawable.g4);
			}
		}catch(Exception e){}
	}
	public static void sound3Pause() 
	{
		if(!setValue)
			 return;
		try{
			if (mpEffect3 != null)
			{
				Log.d("=================","=====================loopStop========================================");
				mpEffect3.pause();
			}	
		}catch(Exception e){}
	}
	public static void sound4(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect4==null)
			{
				mpEffect4 = MediaPlayer.create(context, resource);
			}
			if(!mpEffect4.isPlaying() && setValue)
				mpEffect4.start();
			//else
				//sound5(context,R.drawable.b5);
		}catch(Exception e){}
	}
	public static void sound5(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect5==null)
		{
			mpEffect5 = MediaPlayer.create(context, resource);
		}
		if(!mpEffect5.isPlaying() && setValue)
			mpEffect5.start();
	
		}catch(Exception e){}
	}
	public static void sound6(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect6==null)
		{
				mpEffect6 = MediaPlayer.create(context, resource);
		}
		if(!mpEffect6.isPlaying() && setValue)
			mpEffect6.start();
		//else
			///sound7(context,R.drawable.b7);
		}catch(Exception e){}
	}
	public static void sound7(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect7==null)
		{
				mpEffect7 = MediaPlayer.create(context, resource);
		}
		if(!mpEffect7.isPlaying() && setValue)
			mpEffect7.start();
	
		}catch(Exception e){}
	}
	public static void sound8(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect8==null)
		{
				mpEffect8 = MediaPlayer.create(context, resource);
		}
		if(!mpEffect8.isPlaying() && setValue)
			mpEffect8.start();
		}catch(Exception e){}
	}
	public static void sound9(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect9==null)
		{
				mpEffect9 = MediaPlayer.create(context, resource);
		}
		if(!mpEffect9.isPlaying() && setValue)
			mpEffect9.start();
		}catch(Exception e){}
	}
	public static void sound10(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect10==null)
		{
				mpEffect10 = MediaPlayer.create(context, resource);
		}
		if(!mpEffect10.isPlaying() && setValue)
			mpEffect10.start();
		}catch(Exception e){}
	}
	public static void sound11(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect11==null)
		{
				mpEffect11 = MediaPlayer.create(context, resource);
		}
		if(!mpEffect11.isPlaying() && setValue)
			mpEffect11.start();
		}catch(Exception e){}
	}
	public static void sound12(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect12==null)
		{
				mpEffect12 = MediaPlayer.create(context, resource);
		}
		if(!mpEffect12.isPlaying() && setValue)
			mpEffect12.start();
		}catch(Exception e){}
	}
	public static void sound13(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect13==null)
		{
				mpEffect13 = MediaPlayer.create(context, resource);
		}
		if(!mpEffect13.isPlaying() && setValue)
			mpEffect13.start();
		}catch(Exception e){}
	}
	public static void sound14(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect14==null)
		{
				mpEffect14 = MediaPlayer.create(context, resource);
		}
		if(!mpEffect14.isPlaying() && setValue)
			mpEffect14.start();
		}catch(Exception e){}
	}
	
	public static void sound15(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if(mpEffect15==null)
		{
				mpEffect15 = MediaPlayer.create(context, resource);
		}
		if(!mpEffect15.isPlaying() && setValue)
			mpEffect15.start();
		}catch(Exception e){}
	}
	
	
	
	
	
	
	
}
