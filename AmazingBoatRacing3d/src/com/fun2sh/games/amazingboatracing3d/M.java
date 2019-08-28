

package com.fun2sh.games.amazingboatracing3d;

import java.util.Random;
import android.content.Context;
import android.media.MediaPlayer;
public class M 
{

	public static final String MORE 		= "https://play.google.com/store/apps/developer?id=fun2sh+games";
	public static final String LINK 		= "market://details?id=";
	public static final String SHARELINK 	= "https://play.google.com/store/apps/details?id=";
//	
	
	public static Random mRand = new Random();
	
	public static float ScreenWidth,ScreenHieght;
	public static final float mMaxX = 854,mMaxY=480;
	
	public static int GameScreen;
	
	public static final int GAMELOGO 	 = 0;
	public static final int GAMEMENU 	 = 1;
	public static final int GAMEPLAY 	 = 2;
	public static final int GAMEPAUSE	 = 3;
	public static final int GAMEOVER 	 = 4;
	public static final int GAMEWIN      = 5;
	public static final int GAMESETTING  = 6;
	public static final int GAMEHELP     = 7;
	public static final int GAMEABTUS    = 8;
	public static final int GAMESTART    = 9;
	public static final int GAMESHOP     = 10;

	
	
	public  static boolean setValue	= true,SetBG =true;
	private static MediaPlayer mpEffect1  = null;
	private static MediaPlayer mpEffect2  = null;
	private static MediaPlayer mpEffect3  = null;
	private static MediaPlayer mpEffect4  = null;
	private static MediaPlayer mpEffect5  = null;
	private static MediaPlayer mpEffect6  = null;
	private static MediaPlayer mpEffect7  = null;
	
							  
	private static MediaPlayer mpBG  = null;
	public static float randomRange(float min,float max)
	{
		float rand = mRand.nextFloat();
		max = max-min;
		max  = rand%max;
		return (max+min);
	}
	public static int randomRangeInt(int min, int max) {

	    int randomNum = mRand.nextInt((max - min) + 1) + min;
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
			mpBG         = MediaPlayer.create(context, R.drawable.boat);
			mpEffect1    = MediaPlayer.create(context, R.drawable.click);
			mpEffect2    = MediaPlayer.create(context, R.drawable.coin);
			mpEffect3    = MediaPlayer.create(context, R.drawable.coin);
			mpEffect4    = MediaPlayer.create(context, R.drawable.coin);
			mpEffect5    = MediaPlayer.create(context, R.drawable.coin);
			mpEffect6    = MediaPlayer.create(context, R.drawable.crash);
			mpEffect7    = MediaPlayer.create(context, R.drawable.count);
		  
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
	public static void clickSound(Context context, int resource) {
		 if(!setValue)
			 return;
		try{
			if(mpEffect1==null)
			  mpEffect1 = MediaPlayer.create(context, resource);
			if(!mpEffect1.isPlaying()){
				mpEffect1.start();
			}
		}catch(Exception e){}	
	}
	public static void coinSound(Context context, int resource) {
		 if(!setValue)
			 return;
		try{
			if(mpEffect2==null)
				mpEffect2 = MediaPlayer.create(context, resource);
			if(!mpEffect2.isPlaying())
				mpEffect2.start();
			else
				coinSound1(context, resource);
		}catch(Exception e){}	
	}
	public static void coinSound1(Context context, int resource) {
		 if(!setValue)
			 return;
		try{
			if(mpEffect3==null)
				mpEffect3 = MediaPlayer.create(context, resource);
			if(!mpEffect3.isPlaying())
				mpEffect3.start();
			else
				coinSound2(context, resource);
			
		}catch(Exception e){}	
	}
	public static void coinSound2(Context context, int resource) {
		 if(!setValue)
			 return;
		try{
			if(mpEffect4==null)
				mpEffect4 = MediaPlayer.create(context, resource);
			if(!mpEffect4.isPlaying())
				mpEffect4.start();
			else
				coinSound3(context, resource);
			
		}catch(Exception e){}	
	}
	public static void coinSound3(Context context, int resource) {
		 if(!setValue)
			 return;
		try{
			if(mpEffect5==null)
				mpEffect5 = MediaPlayer.create(context, resource);
			if(!mpEffect5.isPlaying())
				mpEffect5.start();
			
		}catch(Exception e){}	
	}
	public static void crashSound(Context context, int resource) {
		 if(!setValue)
			 return;
		try{
			if(mpEffect6==null)
				mpEffect6 = MediaPlayer.create(context, resource);
			if(!mpEffect6.isPlaying())
				mpEffect6.start();
		}catch(Exception e){}	
	}
	public static void BeepSound(Context context, int resource) {
		 if(!setValue)
			 return;
		try{
			if(mpEffect7==null)
				mpEffect7 = MediaPlayer.create(context, resource);
			if(!mpEffect7.isPlaying())
			{
//				mpEffect7.setLooping(true);
				mpEffect7.start();
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
			
//			if(mpBG1 != null)
//			{
//				mpBG1.stop();
//				mpBG1.setLooping(false);
//				mpBG1.release();
//				mpBG1 = null;
//			}
		  }catch (Exception e){}
	}
	public static void StopSound() {
	   try{
		     BgStop();
			if (mpEffect1 != null)
			{
				mpEffect1.stop();
				mpEffect1.release();
				mpEffect1 = null;
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
				mpEffect6.setLooping(false);
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
			
		}catch(Exception e){}
	}

}
