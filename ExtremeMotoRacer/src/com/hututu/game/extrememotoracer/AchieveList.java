package com.hututu.game.extrememotoracer;


import java.util.List;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
//import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class AchieveList extends ListActivity 
{
	static int counter =0;
	TextView txt0;
	Bitmap mFont[];
	Button mButton[];
	int totalPackege = 0;
	private LayoutInflater mInflater = null;
	static Context context = null;
	boolean carlock[] = new boolean[4]; 
	String AchName[] = { "Learning License",
			"Permanent License",
			"Excellent Driver",
			"Berrari ZX150",
			"Bolls Boyce A1",
			"Kajaki FCX 2000",
			"Bummer BX2",
			"Bronze  Drive",
			"Silver  Drive",
			"Gold  Drive",
			"Platinum  Drive",
			"Bronze  Mile",
			"Silver  Mile",
			"Gold  Mile",
			"Platinum  Mile",
			"Premium buyer",
			"Regular buyer",
			"Mayor of Coins",
			"Collector of Coins",
			"Governor of Coins",
			"Lord of Coins",
			"Super Drive",
			"Rush Drive",
			"Keen",
			"Lusty",
	};
	String AchDis[]={
			"drive atleast 2 min",
			"drive atleast 5 min",
			"drive atleast 15 min",
			"buy this car",
			"buy this car",
			"buy this car",
			"buy this car",
			"overtake 100 Cars",
			"overtake 500 Cars",
			"overtake 1000 Cars",
			"overtake 10000 Cars",
			"drive over 100 miles", 
			"drive over 1000 miles", 
			"drive over 10000 miles", 
			"drive over 100000 miles", 
			"Buy Coins or adv free",
			"download game listed on page",
			"collect 500 coins",
			"collect 2500 coins",
			"collect 10000 coins",
			"collect 50000 coins",
			"drive for atleast 30 min", 
			"drive for atleast 60 min",
			"get 5 achievements",
			"get 10 achievements",
	};
	int AchPoint[]= {200,250,300,2000,2500,3000,5000,100,250,500,1000,100
			,250,500,1000,50000,500,100,250,500,1000,1000,5000,1000,5000};
	byte AchGet[]= {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	
	void checkAchivement()
	{
		int achivment = 0;
		for(int i =0;i<AchGet.length;i++)
		{
//			Log.d("==========================AchGet["+i+"]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  "+AchGet[i],"------------------------------");
			if(AchGet[i]==0)
			{
				switch (i) {
				case 0:
					if((Player.mTotalTime/(1000*60))>=2)
					{
						AchGet[i] = 1;
					}
					break;
				case 1:
					if((Player.mTotalTime/(1000*60))>=5)
					{
						AchGet[i] = 1;
					}
					break;
				case 2:
					if((Player.mTotalTime/(1000*60))>=15)
					{
						AchGet[i] = 1;
					}
					break;
				case 3:
					if(!carlock[0])
						AchGet[i] = 1;
					break;
				case 4:
					if(!carlock[1])
						AchGet[i] = 1;
					break;
				case 5:
					if(!carlock[2])
						AchGet[i] = 1;
					break;
				case 6:
					if(!carlock[3])
						AchGet[i] = 1;
					break;
				case 7:
					if(Player.mTotalCrossCar>100)
						AchGet[i] = 1;
					break;
				case 8:
					if(Player.mTotalCrossCar>500)
						AchGet[i] = 1;
					break;
				case 9:
					if(Player.mTotalCrossCar>1000)
						AchGet[i] = 1;
					break;
				case 10:
					if(Player.mTotalCrossCar>10000)
						AchGet[i] = 1;
					break;
					
				case 11:
					if(Player.mTotalDistance>100)
						AchGet[i] = 1;
					break;
				case 12:
					if(Player.mTotalDistance>1000)
						AchGet[i] = 1;					
					break;
				case 13:
					if(Player.mTotalDistance>10000)
						AchGet[i] = 1;
					break;
				case 14:
					if(Player.mTotalDistance>100000)
						AchGet[i] = 1;
					break;
				case 15:
					if(totalPackege>=5)//Bulk game
						AchGet[i] = 1;
					break;
				case 16:
					if(totalPackege>0)//Single game
						AchGet[i] = 1;
					break;
				case 17:
					if(Player.mTotalCoin>500)
						AchGet[i] = 1;
					break;
				case 18:
					if(Player.mTotalCoin>2500)
						AchGet[i] = 1;
					break;
				case 19:
					if(Player.mTotalCoin>10000)
						AchGet[i] = 1;
					break;
				case 20:
					if(Player.mTotalCoin>50000)
						AchGet[i] = 1;
					break;
				case 21:
					if((Player.mTotalTime/(1000*60))>=30)
					{
						AchGet[i] = 1;
					}
					break;
				case 22:
					if((Player.mTotalTime/(1000*60))>=60)
					{
						AchGet[i] = 1;
					}
					break;
				case 23:
					if(achivment>=5)
					{
						AchGet[i] = 1;
					}
					break;
				case 24:
					if(achivment>=10)
					{
						AchGet[i] = 1;
					}
					break;
				}
			}
			else
				achivment++;
		}
	}
	
	void callAdds()
	{
		AdView adView = new AdView(this, AdSize.BANNER, M.MY_AD_UNIT_ID);
        LinearLayout layout = (LinearLayout)findViewById(R.id.addmain);
        layout.addView(adView);
        adView.loadAd(new AdRequest());
	}
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	try {
    	super.onCreate(savedInstanceState);
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		context = this;
		font();
		setContentView(R.layout.achieve);
		ExampleListViewAdapter adapter = new ExampleListViewAdapter(this);
        setListAdapter(adapter);
//		callAdds();
    	}catch (Exception e) {
			// TODO: handle exception
		}
    }
    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) 
    {
//    	Log.d("@@@@@@@@@@@@@","~~~~~~~~~~~~~~~~~~~~~~~~~~~ "+position);
    }
    private class ExampleListViewAdapter extends BaseAdapter {
        private Context mContext;
        public ExampleListViewAdapter(Context context) {
                super();
                mContext = context;
                mInflater = (LayoutInflater)this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
    		return AchPoint.length;
        }

        public Object getItem(int arg0) {
        	return null;
        }

        public long getItemId(int arg0) {
        	return 0;
        }

        public View getView(final int position, View convertView, ViewGroup parent) 
        {
        	convertView = mInflater.inflate(R.layout.row_achieve, null);
        	ImageView tv = (ImageView)convertView.findViewById(R.id.IV1);
        	tv.setImageBitmap(ImageOne(AchName[position],AchDis[position]));
        	ImageView tv2 = (ImageView)convertView.findViewById(R.id.IV2);
        	final Button AB = (Button)convertView.findViewById(R.id.AB1);
//        	Log.d("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~AchGet["+position+"]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  "+AchGet[position],"~~~~~~~~~~");
        	if(AchGet[position]==0){
        		tv2.setImageBitmap(ImageTwo(""+AchPoint[position]));
        		AB.setBackgroundColor(0);
        	}else if(AchGet[position]==2){
        		tv2.setImageBitmap(ImageTwo(" "));
        		AB.setBackgroundResource(R.drawable.get1);
        		AB.setOnClickListener(new OnClickListener() {
         	       public void onClick(View v) {
         	    	   Massege(position);
         	                 }});
        	}else{
        		tv2.setImageBitmap(ImageTwo(""+AchPoint[position]));
        		AB.setOnClickListener(new OnClickListener() {
        	       public void onClick(View v) {
        	    	   AB.setBackgroundResource(R.drawable.get1);
        	    	   Massege(position);
        	    	   AchGet[position]=2;
//        	    	   Log.d("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~AchGet["+position+"]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  "+AchGet[position],"~~~~~~~~~~");
        	                 }});
        	}
        	return convertView;
        }
    }
    void Massege(int val)
	{
    	if(AchGet[val] == 1)
    		Player.mTotalCoin +=AchPoint[val];
	   new AlertDialog.Builder(this)
	    .setIcon(R.drawable.icon)
	    .setTitle(AchGet[val] == 2? "You already collect Coins":"You got "+AchPoint[val]+" coins on you achievement.")
	    .setPositiveButton("Ok", 
	      new DialogInterface.OnClickListener() {
		       public void onClick(DialogInterface dialog, int which) {
//		    	Download.updateURL(renderer.mScore);
//		   		Download.DownloadFromUrl(CONTEXT, Download.SUBMIT);
		   		
		       }
		      }).show();
	  }
    protected void onResume()
    {
    	resume();
    	super.onResume();
    }
    protected void onPause()
    {
    	pause();
    	super.onPause();
    }
    public boolean onKeyDown(int keyCode, KeyEvent event)
	{
    	if(keyCode==KeyEvent.KEYCODE_BACK)
		{
    		finish();
	    	 if(M.setBG)
				M.Splashplay(context,R.drawable.splash);
			 else
				M.loopSoundStop();
			  return false;
 		 }
    	return super.onKeyDown( keyCode, event ); 
	}  
	public boolean onKeyUp(int keyCode, KeyEvent event) 
	{
		return super.onKeyUp( keyCode, event ); 
	}
	Bitmap ImageOne(String str1,String str2)
	{
		Bitmap bitmap = Bitmap.createBitmap(256,40, Config.ARGB_8888);
	    Canvas canvas = new Canvas(bitmap);
	    drawNumber(canvas, str1, 0, 8, 1);
	    drawNumber(canvas, str2, 0, 28, 2);
	    return bitmap;
	}
	Bitmap ImageTwo(String str1)
	{
		int lenth = (str1.length()*mFont[0].getWidth()/10)+46;
//		Log.d(lenth+"   !!!!!!!!!!!!!!!!!!!!!!1 ","  "+mFont[0].getWidth());
		Bitmap bitmap = Bitmap.createBitmap(lenth,24, Config.ARGB_4444);
	    Canvas canvas = new Canvas(bitmap);
//	    canvas.drawBitmap(mFont[3], 0, 11, null);
	    drawNumber(canvas, str1, mFont[3].getWidth(), 5, 0);
	    return bitmap;
	}
	Bitmap LoadImgfromAsset(String ID)
	{
		try{
			return BitmapFactory.decodeStream(getAssets().open(ID));}
		catch(Exception e)
		{
//			Log.d(""+ID,"!!!!!!!!!!!!!!!!!!!!!~~~~~~~~~~~~~!!!!!!!!!!!!!!!!!!!!!!!!!"+e.getMessage());
			return null;
		}
	}
	void font()
	{
		mFont	= new Bitmap[4];
		for(int j = 0;j<mFont.length;j++)
		{
			mFont[j] = LoadImgfromAsset("font/"+j+".png");
		}
	}
	void drawNumber(Canvas c,String no,float x, float y,int fontType)
	{
		int length = 10;
		int k;
		
		if(fontType == 0)
			length = 10;
		if(fontType == 1)
			length = 26;
		if(fontType == 2)
			length = 43;
		
		float dx = mFont[fontType].getWidth()/length;
		no = no.toUpperCase();
		
		if(fontType==0)
			dx *= 1f;
		else if(fontType==2)
			dx *= .5f;
		else 
			dx *= .7f;
		
		for(int i =0;i<no.length();i++)
		{
			if(no.charAt(i) == 'I' || no.charAt(i) == ' ')
			{
				if(fontType==1)
					x-= 7f;
				if(fontType==2)
					x-= 4f; 
			}
			if(fontType==1)
				k = ((int)no.charAt(i))-65;
			else
				k = ((int)no.charAt(i))-48;
			if(k>=0 && k<length)
				c.drawBitmap(Bitmap.createBitmap(mFont[fontType], k*mFont[fontType].getWidth()/length, 0,mFont[fontType].getWidth()/length, mFont[fontType].getHeight(),null, true),
						x+i*dx,y,null);
		}
	}

	void pause()
	{
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
	    Editor editor = prefs.edit();
	    editor.putBoolean("setBG", M.setBG);
	    editor.putInt("screen", M.GameScreen);
	    for(int i=0;i<AchGet.length;i++)
	    {
	    	editor.putInt("AchGet"+i, AchGet[i]);
//	    	Log.d("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~AchGet["+i+"]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  "+AchGet[i],"####################################");
	    }
	    
	    editor.putInt("PlayermTotalCoin", Player.mTotalCoin);
	    editor.commit();
	}
	
	void resume()
	{
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.setBG = prefs.getBoolean("setBG", M.setBG);
		M.GameScreen = prefs.getInt("screen", M.GameScreen);
		for(int i=0;i<AchGet.length;i++)
		{
			AchGet[i] = (byte)prefs.getInt("AchGet"+i, AchGet[i]);
//	    	Log.d("==========================AchGet["+i+"]~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  "+AchGet[i],"----------------++++++++++++++--------------");
		}
		Player.mTotalTime = prefs.getLong("PlayermTotalDistance", Player.mTotalTime);
    	Player.mTotalDistance = prefs.getInt("PlayermTotalTime", Player.mTotalDistance);
    	Player.mTotalCrossCar = prefs.getInt("PlayermTotalCrossCar", Player.mTotalCrossCar);
    	Player.mTotalCoin = prefs.getInt("PlayermTotalCoin", Player.mTotalCoin);
    	for(int i=1;i<5;i++)
    	{
    		carlock[i-1] = prefs.getBoolean("setVehlock"+i, true);
    	}
    	GetPackage();
		checkAchivement();
	}
	void GetPackage()
	{
		final PackageManager pm = getPackageManager();
		List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
		for (ApplicationInfo packageInfo : packages) {
			String s = ".*"+packageInfo.packageName+".*";
			for(int i=0;i<M.Package.length;i++)
			{
				if(M.Package[i].matches(s))
				{
					totalPackege++;
//					if(mGR.packages.matches(s))
//					{
////						Log.d("~~~~~~~~~~~~~~~", "Installed package :" + packageInfo.packageName);
//					}
//					else
//					{
//						mGR.packages = mGR.packages + packageInfo.packageName;
//						mGR.mPlayer.mTotalCoin+=1000; 
////						Log.d("~~~~~~~~~~~~~~~"+mGR.packages, "not package :" + packageInfo.packageName);
//					}
					break;	
				}
	        	}
	        } 
	    }
	
}
    
