package com.htt.app.photoframeschool;
import android.media.MediaPlayer;

public class M 
{
	public static final String DIR = "/photo_grid";
	public static final String MARKET		= "https://play.google.com/store/apps/developer?id=htt+games";
	public static final String LINK 		= "market://details?id=";
	public static final String SHARELINK 	= "https://play.google.com/store/apps/details?id=";
	public static int AppScreen = 0;
	public static final int APPLOGO = 0;
	public static final int APPMAIN = 1;
	public static final int APPMENU = 2;
	public static final int APPSUB  = 3;
	public static final int APPPLAY = 4;
	public static final int ADDTEXT = 5;
	public static final int APPGRID = 6;

	public static float ScreenWidth,ScreenHieght;
	public static final float mMaxX = 480,mMaxY=854;

	private static MediaPlayer mp 	= null;
	public  static boolean setValue	= true;
	public static void play( int resource) 
	{
		try{
			stop();
			if (setValue) 
			{
				if(mp==null)
					mp = MediaPlayer.create(GameRenderer.mContext, resource);
				
				if(!mp.isPlaying())
				{
					if(resource!=2131099648)
						mp.setLooping(true);
					mp.start();
				}
			}
		}catch(Exception e){}
	}
	public static void stop() {
		try{
			if (mp != null)
			{
				mp.stop();
				mp.release();
				mp = null;
			}
		}catch(Exception e){}
	}
	
	
	
	
}
