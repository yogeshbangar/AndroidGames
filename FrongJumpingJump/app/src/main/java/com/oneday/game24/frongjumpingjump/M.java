

package com.oneday.game24.frongjumpingjump;

import java.util.Random;
import android.content.Context;
import android.media.MediaPlayer;
public class M 
{
	public static final String MARKET 		= "https://play.google.com/store/apps/developer?id=Onedaygame24";
	public static final String LINK 		= "market://details?id=";
	public static final String SHARELINK 	= "https://play.google.com/store/apps/details?id=com.oneday.game24.frongjumpingjump";
	
	public static Random mRand = new Random();
	public static final String score = "score";
	public static int GameScreen;
	public static final int GAMELOGO 		   = 0;
	public static final int GAMEADD 		   = 1;
	public static final int GAMEMENU 	 	   = 2;
	public static final int GAMESETTING	 	   = 3;
	public static final int GAMEWORLD 	 	   = 4;
	public static final int GAMEPLAY 		   = 5;
	public static final int GAMEABTUS 		   = 6;
	public static final int GAMEHELP 		   = 7;
	public static final int GAMEADFREE 		   = 8;
	public static final int GAMEPAUSE 		   = 9;
	public static final int GAMEOVER 		   = 10;
	public static final int GAMELOADING		   = 11;
	public static final int GAMEMODE 		   = 12;
	public static final int GAMECONG 		   = 13;
	
	
	
	
	
	public static float ScreenWidth,ScreenHieght;
	public static final float mMaxX = 854,mMaxY=480;
	public  static boolean setValue	= true,SetBG =true,Vibrate=true;
	private static MediaPlayer mpEffect  = null,mpEffect2=null,mpEffect3=null,mpEffect4=null,mpEffect5=null,
							   mpEffect6 = null,mpEffect7=null,mpEffect8=null,mpEffect9=null,mpEffect10=null;
							  
	private static MediaPlayer mpBG = null,mpBG1 = null;
	
	public static final String MY_INTERSTITIAL_ID  = "ca-app-pub-3395412980708319/4249103384";	//AndroidMarket
													  

	public static float randomRange(float min,float max)
	{
		float rand = M.mRand.nextFloat();
		max = max-min;
		max  = rand%max;
		return (max+min);
	}
	public static int randomRangeInt(int min, int max) {

	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	public static double GetAngle(double d,double e)
	{
	  if(d==0)
		  return e>=0 ? Math.PI/2 : -Math.PI/2;
	  else if (d > 0)
		  return Math.atan(e/d);
	  else
		  return Math.atan(e/d) + Math.PI;
	}
	public static void loadSound(Context context)
	{
		try{
			mpBG       = MediaPlayer.create(context, R.raw.gameplay);
			mpBG1      = MediaPlayer.create(context, R.raw.menu_theme);
			mpEffect   = MediaPlayer.create(context, R.raw.button_click);
			mpEffect2  = MediaPlayer.create(context, R.raw.frog);
			mpEffect3  = MediaPlayer.create(context, R.raw.gameover);
			mpEffect4  = MediaPlayer.create(context, R.raw.dumping);
			mpEffect5  = MediaPlayer.create(context, R.raw.jump0);
			mpEffect6  = MediaPlayer.create(context, R.raw.jump1);
			mpEffect7  = MediaPlayer.create(context, R.raw.jump2);
			mpEffect8  = MediaPlayer.create(context, R.raw.jump3);
			mpEffect9  = MediaPlayer.create(context, R.raw.swing);
			mpEffect10 = MediaPlayer.create(context, R.raw.congratulations);
			 
		}catch(Exception e){}
	}
	public static void BGPlay(Context context, int resource) 
	{
		try{
			StopSound();
			if(SetBG) 
			{
			  if(mpBG==null)
				 mpBG= MediaPlayer.create(context, resource);
				
				if(!mpBG.isPlaying())
				{
				  if(resource!=2131099648)
					mpBG.setLooping(true);
					mpBG.start();
				}
			}
		}catch(Exception e){}
	}
	public static void SplashPlay(Context context, int resource) 
	{
		try{
			StopSound();
			if(SetBG) 
			{
			  if(mpBG1==null)
				 mpBG1= MediaPlayer.create(context, resource);
				
				if(!mpBG1.isPlaying())
				{
				  if(resource!=2131099648)
					mpBG1.setLooping(true);
					mpBG1.start();
				}
			}
		}catch(Exception e){}
	}
	
	public static void ClickSound(Context context, int resource) {
		 if(!setValue)
			 return;
		try{
			if(mpEffect==null)
			{
				mpEffect = MediaPlayer.create(context, resource);
			}
			if(!mpEffect.isPlaying()){
				mpEffect.start();
			}
		}catch(Exception e){}	
	}
	public static void FrogSound(Context context, int resource) {
		 if(!setValue)
			 return;
		try{
			if(mpEffect2==null)
			{
				mpEffect2 = MediaPlayer.create(context, resource);
			}
			if(!mpEffect2.isPlaying()){
				mpEffect2.start();
			}
		}catch(Exception e){}	
	}
	public static void GameOver(Context context, int resource) {
		 if(!setValue)
			 return;
		try{
			if(mpEffect3==null)
			{
				mpEffect3 = MediaPlayer.create(context, resource);
			}
			if(!mpEffect3.isPlaying()){
				mpEffect3.start();
			}
		}catch(Exception e){}	
	}
	public static void DumpingSound(Context context, int resource) {
		 if(!setValue)
			 return;
		try{
			if(mpEffect4==null)
			{
				mpEffect4 = MediaPlayer.create(context, resource);
			}
			if(!mpEffect4.isPlaying()){
				mpEffect4.start();
			}
		}catch(Exception e){}	
	}
	
	public static void Jumpsound(int no)
	{
		switch(no)
		{
		  case 0:
			    Jump0Sound(GameRenderer.mContext,R.raw.jump0);
			   break;
		  case 1:
			    Jump1Sound(GameRenderer.mContext,R.raw.jump1);
			   break;
		  case 2:
			    Jump2Sound(GameRenderer.mContext,R.raw.jump2);
			   break;
		  case 3:
			    Jump3Sound(GameRenderer.mContext,R.raw.jump3);
			   break;
		}
		System.out.println("in          "+no);
	}
	public static void Jump0Sound(Context context, int resource) {
		 if(!setValue)
			 return;
		try{
			if(mpEffect5==null)
			{
				mpEffect5 = MediaPlayer.create(context, resource);
			}
			if(!mpEffect5.isPlaying()){
				mpEffect5.start();
			}
		}catch(Exception e){}	
	}
	public static void Jump1Sound(Context context, int resource) {
		 if(!setValue)
			 return;
		try{
			if(mpEffect6==null)
			{
				mpEffect6 = MediaPlayer.create(context, resource);
			}
			if(!mpEffect6.isPlaying()){
				mpEffect6.start();
			}
		}catch(Exception e){}	
	}
	public static void Jump2Sound(Context context, int resource) {
		 if(!setValue)
			 return;
		try{
			if(mpEffect7==null)
			{
				mpEffect7 = MediaPlayer.create(context, resource);
			}
			if(!mpEffect7.isPlaying()){
				mpEffect7.start();
			}
		}catch(Exception e){}	
	}
	public static void Jump3Sound(Context context, int resource) {
		 if(!setValue)
			 return;
		try{
			if(mpEffect8==null)
			{
				mpEffect8 = MediaPlayer.create(context, resource);
			}
			if(!mpEffect8.isPlaying()){
				mpEffect8.start();
			}
		}catch(Exception e){}	
	}
	public static void SwingSound(Context context, int resource) {
		 if(!setValue)
			 return;
		try{
			if(mpEffect9==null)
			{
				mpEffect9 = MediaPlayer.create(context, resource);
			}
			if(!mpEffect9.isPlaying()){
				mpEffect9.start();
			}
		}catch(Exception e){}	
	}
	public static void CongSound(Context context, int resource) {
		 if(!setValue)
			 return;
		try{
			if(mpEffect10==null)
			{
				mpEffect10 = MediaPlayer.create(context, resource);
			}
			if(!mpEffect10.isPlaying()){
				mpEffect10.start();
			}
		}catch(Exception e){}	
	}
	public static void BgStop() 
	{
		try {
			if(mpBG != null)
			{
				mpBG.stop();
				mpBG.setLooping(false);
				mpBG.release();
				mpBG = null;
			}
			if(mpBG1 != null)
			{
				mpBG1.stop();
				mpBG1.setLooping(false);
				mpBG1.release();
				mpBG1 = null;
			}
		  }catch (Exception e){}
	}
	public static void StopSound() {
	   try{
		     BgStop();
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
			if (mpEffect6 != null)
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
			
		}catch(Exception e){}
	}

}
