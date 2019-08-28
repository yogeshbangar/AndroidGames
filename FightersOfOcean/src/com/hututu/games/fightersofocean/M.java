

package com.hututu.games.fightersofocean;

import java.util.Random;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

public class M 
{
	
	public static final String SHARE		= "Share";
	public static final String FREEGAME		= "Free games";
	public static final String CONTINUE		= "Continue";
	
	public static final String BOAT			= "Upgarde Boat";
	public static final String GUN			= "Upgarde Gun";
	public static final String POWER		= "Upgarde Power";
	
	public static final String XPMSG		= "You Don't have Sufficient XP.";
	public static final String XPCON		= "You Don't have Sufficient Coin.";
	
	
	
	public static final String MARKET 		= "https://play.google.com/store/apps/developer?id=Hututu+Games";
	public static final String LINK 		= "market://details?id=";
	public static final String SHARELINK 	= "https://play.google.com/store/apps/details?id=com.hututu.games.fightersofocean";
	public static final String LOADGAME[] 	= {"https://play.google.com/store/apps/details?id=com.hututu.game.helicoptercontrol",
												"https://play.google.com/store/apps/details?id=com.hututu.game.speeddragracing",
												"https://play.google.com/store/apps/details?id=com.hututu.game.stuntracingcar"};
//	

	
//	public static final String MARKET 		= "samsungapps://ProductDetail/com.example.fightersofocean";
//	public static final String LINK 		= "samsungapps://ProductDetail/";
//	public static final String SHARELINK 	= "samsungapps://ProductDetail/com.example.fightersofocean";
//	

//	public static final String  LINK 		= "http://www.amazon.com/gp/mas/dl/android?p=";
//	public static final String  MARKET 		= "http://www.amazon.com/gp/mas/dl/android?p=com.hututu.game.shootthebottle&showAll=1";
//	public static final String SHARELINK 	= "http://www.amazon.com/gp/mas/dl/android?p=com.hututu.games.fightersofocean";
//	public static final String LOADGAME[] 	= {"http://www.amazon.com/gp/mas/dl/android?p=com.hututu.game.helicoptercontrol",
//												"http://www.amazon.com/gp/mas/dl/android?p=com.hututu.game.speeddragracing",
//												"http://www.amazon.com/gp/mas/dl/android?p=com.hututu.game.stuntracingcar"};

//	
//	public static final String MARKET 		= "http://in.lgworld.com/snw.dev?gu=app&m1=ASF_01_00&m2=101&m3&aid=000000125372714&lang=EN";
//	public static final String LINK 		= "http://in.lgworld.com/snw.dev?gu=app&m1=ASF_01_00&m2=101&m3&aid=000000125372714&lang=EN";
//	public static final String SHARELINK 	= "http://in.lgworld.com/snw.dev?gu=app&m1=ASF_01_00&m2=101&m3&aid=000000125372714&lang=EN";
//	public static final String LOADGAME[] 	= {"http://in.lgworld.com/snw.dev?gu=app&m1=ASF_01_00&m2=101&m3&aid=000000125372714&lang=EN",
//												"http://in.lgworld.com/snw.dev?gu=app&m1=ASF_01_00&m2=101&m3&aid=000000125372714&lang=EN",
//												"http://in.lgworld.com/snw.dev?gu=app&m1=ASF_01_00&m2=101&m3&aid=000000125372714&lang=EN"};
//	
	
	
	
	public static int PlayerLife = 100;
	public static int mOppSet = 0;
	
	public static final int LS = 6;
	
	public static Random mRand = new Random();
	
	public static int GameScreen;
	public static final int GAMELOGO = 0,GAMESHOP = 1,GAMEMENU = 2,GAMEPLAY = 3,GAMELOAD = 4,GAMEADD=50;
	public static final int GAMEHELP = 6,GAMEOVER = 7,GAMEABUT = 8,GAMEWIN =  9,GAMEPUSE =10;
	
	public static float ScreenWidth,ScreenHieght;
	public static final float mMaxX = 800,mMaxY=480;
	
	public static final String MY_AD_UNIT_ID = "ca-app-pub-3395412980708319/5777833789";	//AndroidMarket//a1519f20aff0df6
	public static final String HOUSE_ADV_ID  = "ca-app-pub-3395412980708319/7254566988";	//AdHouse
	
	private static MediaPlayer mp 	= null;
	public  static boolean setValue	= true;
	private static MediaPlayer mpEffect = null,mpEffect2=null,mpEffect3=null,mpEffect4=null,mpEffect5=null,mpEffect6=null,mpEffect7=null,mpEffect8=null;
	private static MediaPlayer mpBG1 = null,mpBG2 = null,mpBG3 = null,mpBG4 = null,mpBG5 = null;
	private static MediaPlayer mpEffect9 = null,mpEffect10 = null,mpEffect11 = null,mpEffect12 = null,mpEffect13 = null,mpEffect14 = null,mpEffect15 = null;
	
	public static void loadSound(Context context)
	{
		try{
//			if(mp==null)
//				mp = MediaPlayer.create(context, R.drawable.bgsound);//
			
//			if(mpEffect==null)
//				mpEffect = MediaPlayer.create(context, R.drawable.g1);//
//			
//			if(mpEffect2==null)
//				mpEffect2 = MediaPlayer.create(context, R.drawable.g2);
//			
//			if(mpEffect3==null)
//				mpEffect3 = MediaPlayer.create(context, R.drawable.g3);//
//	
			
			
			
		}catch(Exception e){}
	}
	public static void play(Context context, int resource) 
	{
		try{
			stop();
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
//		try{
//			if (mp != null)
//			{
//				Log.d("=================","=====================loopStop========================================");
//				mp.pause();
//			}	
//		}catch(Exception e){}
	}
	public static void loopStop() 
	{
		try{
			if (mp != null)
			{
				Log.d("=================","=====================loopStop========================================");
				mp.setLooping(false);
				mp.pause();
				stop();
			}	
//			sound3Pause();
		}catch(Exception e){}
	}
	public static void stop() {
		try{
			Log.d("---------------------------------------------","sound stop");
			if (mp != null)
			{
				mp.setLooping(false);
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
				mpEffect3.setLooping(false);
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
			
			
			
			
			if (mpBG1 != null)
			{
				mpBG1.stop();
				mpBG1.release();
				mpBG1 = null;
			}
			if (mpBG2 != null)
			{
				mpBG2.stop();
				mpBG2.release();
				mpBG2 = null;
			}
			if (mpBG3 != null)
			{
				mpBG3.stop();
				mpBG3.release();
				mpBG3 = null;
			}
			if (mpBG4 != null)
			{
				mpBG4.stop();
				mpBG4.release();
				mpBG4 = null;
			}
			if (mpBG5 != null)
			{
				mpBG5.stop();
				mpBG5.release();
				mpBG5 = null;
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
	public static void sound3Play(Context context, int resource) {
		if(!setValue)
			 return;
		try{
			if (setValue) 
			{
				if(mpEffect3==null)
					mpEffect3 = MediaPlayer.create(context, resource);
				if(!mpEffect3.isPlaying())
				{
					if(resource!=2131099648)
						mpEffect3.setLooping(true);
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
				if(mpEffect3.isPlaying()){
					mpEffect3.setLooping(false);
					mpEffect3.pause();
				}
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
	
	public static float[] sort(float []a)
	{
		float temp;
		for(int i=0;i<a.length;i++)
		{
			for(int j=i+1;j<a.length;j++)
			{
				if(a[i]>a[j])
				{
					temp = a[i];
					a[i] = a[j];
					a[j] = temp;
				}
			}
		}
		for(int i=0;i<a.length;i++)
		{
			System.out.println("!!!!!!!!!!!!  "+a[i]);
		}
		return a;
	}
	
	static void playGun(Context context,int type,boolean tuch,int shell)
	{
//		System.out.println(type+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+tuch);
		if(tuch){
		switch (type) {
			case 0:
				sound3Play(context, R.drawable.g0);
				break;
			case 1:
				sound3Play(context, R.drawable.g1);
				break;
			case 2:
				sound3Play(context, R.drawable.g2);
				break;
			case 3:
				sound3Play(context, R.drawable.g3);
				break;
			case 4:
				sound3Play(context, R.drawable.g4);
				break;
			case 5:
				sound3Play(context, R.drawable.g5);
				break;
			case 6:
				sound3Play(context, R.drawable.g6);
				break;
			}
		if(shell%20==0)
			sound2(context, R.drawable.bullet_shell);
		}
		else
		{
			sound3Pause();
		}
			
	}
	
}
