package com.oneday.games24.extrememotoracer;


import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
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

public class AboutUs extends ListActivity  
{
	static int counter =0;
	TextView txt0;
	ImageView Img;
	String split[];
	private LayoutInflater mInflater = null;
	static Context context = null;
	void callAdds()
	{
//		AdView adView = new AdView(this, AdSize.BANNER, M.MY_AD_UNIT_ID);
//        LinearLayout layout = (LinearLayout)findViewById(R.id.addabout);
//        layout.addView(adView);
//        adView.loadAd(new AdRequest());
	}
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	super.onCreate(savedInstanceState);
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		context = this;
		setContentView(R.layout.about);
		ExampleListViewAdapter adapter = new ExampleListViewAdapter(this);
        setListAdapter(adapter);
        callAdds();
}
   void Start()
    {
    }
    @Override
   
    protected void onListItemClick(ListView listView, View view, int position, long id) 
    {
    	 super.onListItemClick(listView, view, position,id);
    }
    
    private class ExampleListViewAdapter extends BaseAdapter {
        private Context mContext;
        public ExampleListViewAdapter(Context context) {
                super();
                mContext = context;
                mInflater = (LayoutInflater)this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
        	return 1;
        }

        public Object getItem(int arg0) {
        	return null;
        }

        public long getItemId(int arg0) {
        	
        	return 0;
        }
        public View getView(int position, View convertView, ViewGroup parent) 
        {
        	convertView = mInflater.inflate(R.layout.aboutusrowlayout, null);
        	ImageView tv2 = (ImageView)convertView.findViewById(R.id.imageView1);
        	tv2.setBackgroundResource(R.drawable.aboutus);
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
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
		    finish();
//    	   if(M.setBG)
//			 M.Splashplay(context,R.drawable.splash);
//		    else
//		 	M.loopSoundStop();
		  return false;
		}
		return super.onKeyDown( keyCode, event ); 
	}  
	public boolean onKeyUp(int keyCode, KeyEvent event) 
	{  	
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
//			finish();
		}
	 	return super.onKeyUp( keyCode, event ); 
	}

}
    
