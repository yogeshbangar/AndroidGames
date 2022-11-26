package com.oneday.games24.extrememotoracer;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MList extends ListActivity 
{
	static int counter =0;
	TextView txt0;
	private LayoutInflater mInflater = null;
	static Context context = null;
	private final int icon[] = {

			
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
//		AdView adView = new AdView(this, AdSize.BANNER, M.MY_AD_UNIT_ID);
//        LinearLayout layout = (LinearLayout)findViewById(R.id.addmain);
//        layout.addView(adView);
//        adView.loadAd(new AdRequest());
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
		setContentView(R.layout.game);
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
    
