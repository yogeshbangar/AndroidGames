package com.hututu.game.extrememotoracer;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
//import android.graphics.Color;
//import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
//import android.provider.MediaStore.Images;
//import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MList extends ListActivity 
{
	static int counter =0;
	TextView txt0;
	private LayoutInflater mInflater = null;
	static Context context = null;
	private final int icon[] = {
			
			R.drawable.archeryking,
			R.drawable.fighterofocean,
			R.drawable.galaxyhunt,
			R.drawable.helicoptercontrol,
			R.drawable.papertoss,
			R.drawable.pokernpoker,
			R.drawable.reversegravity,
			R.drawable.scarecrowvsbirds,
			R.drawable.scarygames,
			R.drawable.shootthebottle,
			R.drawable.speeddragracing,
			R.drawable.stuntracingcar,
			R.drawable.torch
			
	};
	private final String Name[] = {
			"Archery King",
			"Fighter Of Ocean",
			"Galaxy Hunt",
			"Helicopter Control",
			"Paper Toss",
			"Poker n Poker",
			"Reverse Gravity",
			"Scarecrow vs Birds",
			"Scary Games",
			"Shoot The Bottle",
			"Speed Drag Racing",
			"Stunt Racingcar",
			"Torch"
	};
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
		setContentView(R.layout.list);
		ExampleListViewAdapter adapter = new ExampleListViewAdapter(this);
        setListAdapter(adapter);
		callAdds();
    	}catch (Exception e) {
			// TODO: handle exception
		}
    }
    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) 
    {
    	super.onListItemClick(listView, view, position, id);
    	Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(M.LINK+M.Package[position]));
		startActivity(intent);
    }
    private class ExampleListViewAdapter extends BaseAdapter {
        private Context mContext;
        public ExampleListViewAdapter(Context context) {
                super();
                mContext = context;
                mInflater = (LayoutInflater)this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
    		return icon.length;
        }

        public Object getItem(int arg0) {
        	return null;
        }

        public long getItemId(int arg0) {
        	return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) 
        {
        	convertView = mInflater.inflate(R.layout.rowlayout, null);
        	ImageView tv = (ImageView)convertView.findViewById(R.id.lno1);
            TextView tv1 = (TextView)convertView.findViewById(R.id.lna1);
            //ImageView tv2 = (ImageView)convertView.findViewById(R.id.lsc1);

            tv.setImageResource(icon[position]);
            tv1.setTextColor(Color.WHITE);
//            tv1.setTextSize(14);
        	tv1.setText(Name[position]);
        	//tv2.setText("YOGOESH ");
        	return convertView;
        }
    }
    protected void onResume()
    {
    	super.onResume();
    }
    protected void onPause()
    {
    	super.onPause();
    }
    public boolean onKeyDown(int keyCode, KeyEvent event)
	{
    	return super.onKeyDown( keyCode, event ); 
	}  
	public boolean onKeyUp(int keyCode, KeyEvent event) 
	{
		return super.onKeyUp( keyCode, event ); 
	}
    
}
    
