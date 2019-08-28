

package com.onedaygames24.stickpowerheroes;

import java.util.Random;
import android.content.Context;
import android.media.MediaPlayer;
public class M 
{
//	public static final String MORE 		= "https://play.google.com/store/apps/developer?id=httgames";
	public static final String LINK 		= "market://details?id=";
	public static final String SHARELINK 	= "https://play.google.com/store/apps/details?id=";
//	public static final String MARKET 		= "samsungapps://ProductDetail/com.hututu.game.galaxyhunt";
//	public static final String LINK 		= "samsungapps://ProductDetail/";
//	public static final String SHARELINK 	= "samsungapps://ProductDetail/com.hututu.game.galaxyhunt";
//	
//	public static final String MARKET 		= "http://slideme.org/user/hututugames";
//	public static final String LINK 		= "market://details?id=";
//	public static final String SHARELINK 	= "http://slideme.org/user/hututugames";

//	public static final String  LINK 		= "http://www.amazon.com/gp/mas/dl/android?p=";
//	public static final String  MARKET 		= "http://www.amazon.com/gp/mas/dl/android?p=com.hututu.game.shootthebottle&showAll=1";
//	public static final String SHARELINK 	= "http://www.amazon.com/gp/mas/dl/android?p=com.hututu.game.shootthebottle&showAll=1";
//	
	
	public static Random mRand = new Random();
	
	public static float ScreenWidth,ScreenHieght;
	public static final float mMaxX = 480,mMaxY=854;
	
	public static int GameScreen;
	public static final int GAMELOGO 		   = 0;
	public static final int GAMEMENU 	 	   = 1;
	public static final int GAMEPLAY 		   = 2;
	public static final int GAMEOVER 		   = 3;
	public static final int GAMEHERO 		   = 4;
//	public static final int GAMESHOP		   = 5;
	public static final int GAMEHELP		   = 6;
	public static final int GAMEPAUSE		   = 7;
	
	public  static boolean setValue	= true,SetBG =true;
	private static MediaPlayer mpEffect = null,mpEffect2=null,mpEffect3=null,mpEffect4=null,mpEffect41=null,mpEffect5=null,mpEffect51=null,mpEffect52=null,mpEffect6=null,
							   mpEffect61=null,mpEffect62=null,mpEffect7=null,mpEffect8=null,mpEffect9=null;
							  
	private static MediaPlayer mpBG = null,mpBG1 = null;
	
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
			mpBG      = MediaPlayer.create(context,R.drawable.bg);
			mpEffect  = MediaPlayer.create(context,R.drawable.click);
			mpEffect2 = MediaPlayer.create(context,R.drawable.linedraw);
			mpEffect3 = MediaPlayer.create(context,R.drawable.line_fall);
			mpEffect4 = MediaPlayer.create(context,R.drawable.chafall1);
			mpEffect5 = MediaPlayer.create(context,R.drawable.chafall2);
			mpEffect6 = MediaPlayer.create(context,R.drawable.coincollect);
			mpEffect7 = MediaPlayer.create(context,R.drawable.newbrick);
			mpEffect8 = MediaPlayer.create(context,R.drawable.perfect);
			mpEffect9 = MediaPlayer.create(context,R.drawable.swing);
			
		}catch(Exception e){}
	}
	public static void BGPlay(Context context, int resource) 
	{
		try{
			StopSound();
			if(setValue) 
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
			if(mpEffect==null)
			{
				mpEffect = MediaPlayer.create(context, resource);
			}
			if(!mpEffect.isPlaying()){
				mpEffect.start();
			}
		}catch(Exception e){}	
	}
	public static void LineDrawSound(Context context, int resource) {
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
	public static void LineFallSound(Context context, int resource) {
		 if(!setValue)
			 return;
		try{
			if(mpEffect3==null)
				mpEffect3 = MediaPlayer.create(context, resource);
			if(!mpEffect3.isPlaying()){
				mpEffect3.start();
			}
		}catch(Exception e){}	
	}
	public static void Fall1Sound(Context context, int resource) {
		 if(!setValue)
			 return;
		try{
			if(mpEffect4==null)
				mpEffect4 = MediaPlayer.create(context, resource);
			if(!mpEffect4.isPlaying()){
				mpEffect4.start();
			}
		}catch(Exception e){}	
	}
	public static void Fall2Sound(Context context, int resource) {
		 if(!setValue)
			 return;
		try{
			if(mpEffect5==null)
				mpEffect5 = MediaPlayer.create(context, resource);
			if(!mpEffect5.isPlaying()){
				mpEffect5.start();
			}
		}catch(Exception e){}	
	}
	public static void CoinSound(Context context, int resource) {
		 if(!setValue)
			 return;
		try{
			if(mpEffect6==null)
				mpEffect6 = MediaPlayer.create(context, resource);
			if(!mpEffect6.isPlaying()){
				mpEffect6.start();
			}
		}catch(Exception e){}	
	}
	public static void NewPollSound(Context context, int resource) {
		 if(!setValue)
			 return;
		try{
			if(mpEffect7==null)
				mpEffect7 = MediaPlayer.create(context, resource);
			if(!mpEffect7.isPlaying()){
				mpEffect7.start();
			}
		}catch(Exception e){}	
	}
	public static void PerfectSound(Context context, int resource) {
		 if(!setValue)
			 return;
		try{
			if(mpEffect8==null)
				mpEffect8 = MediaPlayer.create(context, resource);
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
				mpEffect9 = MediaPlayer.create(context, resource);
			if(!mpEffect9.isPlaying()){
				mpEffect9.start();
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
			if (mpEffect41 != null)
			{
				mpEffect41.stop();
				mpEffect41.release();
				mpEffect41 = null;
			}
			if (mpEffect5 != null)
			{
				mpEffect5.stop();
				mpEffect5.release();
				mpEffect5 = null;
			}
			if (mpEffect51 != null)
			{
				mpEffect51.stop();
				mpEffect51.release();
				mpEffect51 = null;
			}
			if (mpEffect52 != null)
			{
				mpEffect52.stop();
				mpEffect52.release();
				mpEffect52 = null;
			}
			if (mpEffect6 != null)
			{
				mpEffect6.stop();
				mpEffect6.release();
				mpEffect6 = null;
			}
			if (mpEffect61 != null)
			{
				mpEffect61.stop();
				mpEffect61.release();
				mpEffect61 = null;
			}
			if (mpEffect62 != null)
			{
				mpEffect62.stop();
				mpEffect62.release();
				mpEffect62 = null;
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
			
			
			
		}catch(Exception e){}
	}

}
