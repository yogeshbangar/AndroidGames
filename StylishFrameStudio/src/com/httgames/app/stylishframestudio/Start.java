package com.httgames.app.stylishframestudio;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.plus.PlusOneButton;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class Start extends Activity 
{
	private EditText edittext;
	int _keyCode = 0;
	PlusOneButton mPlusOneButton;
	AdView adView = null;
	ZRenderer mGR = null;
	private InterstitialAd interstitialAd;
	private static final int PLUS_ONE_REQUEST_CODE = 0;

	boolean IsCreate = false;
	boolean IsStart = false;
	void callAdds() {
		adView = (AdView) this.findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);
		adView.setAdListener(new AdListener() {
			public void onAdLoaded() {
				adView.bringToFront();
			}
		});
	}
	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) 
	{
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);//OnlyOneChange
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId(getString(R.string.INTERSTITIAL_ID));
	    WindowManager mWinMgr = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
		M.ScreenWidth =mWinMgr.getDefaultDisplay().getWidth();
		M.ScreenHieght=mWinMgr.getDefaultDisplay().getHeight();
		mGR=new ZRenderer(this);
	    VortexView glSurface=(VortexView) findViewById(R.id.vortexview); // use the xml to set the view
	    glSurface.setRenderer(mGR);
	    glSurface.showRenderer(mGR);

	    mPlusOneButton = (PlusOneButton) findViewById(R.id.plus_one_button);

	    
	    edittext = (EditText) findViewById(R.id.editText);
	    edittext.setVisibility(View.GONE);
	    edittext.bringToFront();
	    IsCreate = true;
	    IsStart = true;
	    callAdds();
	    load();
	}
	@Override 
	public void onPause () {
		pause();
		super.onPause();
	}
	@Override 
	public void onResume() {                                                                                                       
		resume();
		super.onResume();
	}
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			switch (M.AppScreen) {
			case M.APPLOGO:
				return false;
			case M.APPMENU:
				Exit();
				return false;
			case M.ADDTEXT:
			case M.APPMAIN:
				try {
					ZRenderer.mStart.Txthandler.sendEmptyMessage(View.GONE);
				} catch (Exception e) {
				}
				mGR.root.forSave =0;
				M.AppScreen = M.APPPLAY;
				return false;
			case M.APPSUB:
				M.AppScreen = M.APPMAIN;
				return false;
			case M.APPPLAY:
				dOyou();
				return false;
			default:
				M.AppScreen = M.APPMENU;
				ShowGPlus(View.VISIBLE);//mPlusOneButton.setVisibility(View.VISIBLE);
				return false;
			}
		}
		_keyCode = keyCode;
		return super.onKeyDown(keyCode,event); 
	}  
	public boolean onKeyUp(int keyCode, KeyEvent event) 
	{
		_keyCode = 0;
		return super.onKeyUp( keyCode, event ); 
	}
	public void onDestroy()
	{
		super.onDestroy();
	}
	void pause()
	{
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
	    Editor editor = prefs.edit();
	    editor.putInt("screen", M.AppScreen);
	    editor.putBoolean("addFree", mGR.addFree);
	    editor.putString("p", path);
	    editor.commit();
	}
	void resume()
	{
		mPlusOneButton.initialize("https://play.google.com/store/apps/details?id="+ getClass().getPackage().getName(),
				PLUS_ONE_REQUEST_CODE);
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.AppScreen = prefs.getInt("screen", M.APPLOGO);
		mGR.addFree = prefs.getBoolean("addFree", mGR.addFree);
		if(IsCreate)
			M.AppScreen = M.APPLOGO;
		
		path = prefs.getString("p", path);
		IsCreate = false;
	}
	void Exit()
	{
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle(getString(R.string.Exit))
	    .setPositiveButton("More",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
    	   mGR.root.MoreGame();
      }}).setNeutralButton("No",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
      }}).setNegativeButton("Yes",new DialogInterface.OnClickListener(){public void onClick(DialogInterface dialog, int which) {
		    		   finish();M.AppScreen=M.APPLOGO;mGR.root.Counter =0;
      }}).show();
	}
	
	boolean isTxtVisible(){
		return (edittext.getVisibility() ==View.VISIBLE);
	}
	
	Handler Txthandler = new Handler() {
		public void handleMessage(Message msg) {
			AddTxtshow(msg.what);
		}
	};

	private void AddTxtshow(int val) {
		try {
			edittext.setVisibility(val);
			} catch (Exception e) {
		}
	}
	String getAddTxt(){
		hideKey();
		return edittext.getText().toString();
	}
	void hideKey()
	{
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(edittext.getWindowToken(), 0);
	}
	
	void dOyou() {
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle(getString(R.string.Save))
	    .setPositiveButton("Save",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
    	   mGR.root.screenshot =true;
    	   mGR.isMenu = true;
    	   ShowInterstitial();
      }}).setNeutralButton("Share",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
    	  mGR.root.screenshot =true;
    	  mGR.isShare =true;
    	  mGR.isMenu = true;
    	  ShowInterstitial();
      }}).setNegativeButton("Discard",new DialogInterface.OnClickListener(){public void onClick(DialogInterface dialog, int which) {
		   M.AppScreen = M.APPMENU;
		   ShowGPlus(View.VISIBLE);//mPlusOneButton.setVisibility(View.VISIBLE);
		   ShowInterstitial();
      }}).show();
	}
	void Network()
	{
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Cheack your network for picasa?")
	    .setNeutralButton("Ok",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
      }}).show();
	}
	
	
	void SaveMassage()
	{
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("First save Image.")
	    .setPositiveButton("Ok",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
      }}).show();
	}
	public static boolean hasConnection() {
	    ConnectivityManager cm = (ConnectivityManager) ZRenderer.mContext.getSystemService(
	        Context.CONNECTIVITY_SERVICE);

	    NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	    if (wifiNetwork != null && wifiNetwork.isConnected()) {
	      return true;
	    }

	    NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
	    if (mobileNetwork != null && mobileNetwork.isConnected()) {
	      return true;
	    }

	    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
	    if (activeNetwork != null && activeNetwork.isConnected()) {
	      return true;
	    }

	    return false;
	  }
		
	@SuppressLint("CommitPrefEdits")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		Editor editor = prefs.edit();
		try {
			super.onActivityResult(requestCode, resultCode, data);
			System.out.println(requestCode+"~~~~~~~~~~~~~~~~~`"+ resultCode+"~~~~~~~~~~~~`"+ data);
			if(data !=null)
				System.out.println(data.getData());
			if (requestCode == 1) {
				if (data == null) {
					return;
				}
				String str = data.getData() + "";
				if (str.contains("com.")/* && str.contains("picasa")*/) {
					if ((!hasConnection())) {
						Network();
						return;
					}
					BitmapFactory.Options options = new BitmapFactory.Options();
					InputStream is;
					try {
						is = ZRenderer.mContext.getContentResolver().openInputStream(data.getData());
						final Bitmap bitmap = BitmapFactory.decodeStream(is,null, options);
						ThreadB te = new ThreadB(bitmap);
						te.run();
						is.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					Uri photoUri = data.getData();
					if (photoUri != null) {
						String[] filePathColumn = { MediaStore.Images.Media.DATA };
						Cursor cursor = getContentResolver().query(photoUri,filePathColumn, null, null, null);
						cursor.moveToFirst();
						int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
						String filePath = cursor.getString(columnIndex);
						cursor.close();
						ThreadB te = new ThreadB(getBitmap(filePath));
						te.run();
					}
				}
			}
			if (requestCode == 2) {
				path = prefs.getString("path", path);
				File folder = new File(path);
				if (folder.exists()) {
					Bitmap bit = getBitmap(path);
					ThreadB te = new ThreadB(bit);
					te.run();
				} else {
					editor.putInt("screen", M.APPPLAY);
					editor.commit();
				}
			}
			if (requestCode == 100) {
				if (data == null) {
					return;
				}
				String str = data.getData() + "";
				if ((!hasConnection() && str.contains("com.google.android") && str.contains("picasa"))) {
					Network();
					return;
				}
				BitmapFactory.Options options = new BitmapFactory.Options();
				InputStream is;
				try {
					is = ZRenderer.mContext.getContentResolver().openInputStream(data.getData());
					final Bitmap bitmap = BitmapFactory.decodeStream(is, null,options);
					ThreadB te = new ThreadB(bitmap);
					te.run();
					is.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {

		}
	}
	public class ThreadB implements Runnable {
		Bitmap path;

		public ThreadB(Bitmap _path) {
			path = _path;
		}

		public void run() {
			mGR.addBit(path);
		}
	}

	String path = "";
	public void takePhoto() {
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		String str = Environment.getExternalStorageDirectory() + "/LoadImg1";
		File folder = new File(str);
		if (!folder.exists()) {
			folder.mkdir();
		}
		path = str + "/" + System.currentTimeMillis() + ".png";
		File photo = new File(path);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
		startActivityForResult(intent, 2);
	}
	
	public static Bitmap getBitmap(String path1) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path1, options);
		int imageHeight = options.outHeight;
		int imageWidth = options.outWidth;
		options.inPreferQualityOverSpeed=false;
		options.inSampleSize=10;
		options.inScaled=false;
		options.inTargetDensity=100;
		if (imageWidth > imageHeight) {
			options.inSampleSize = calculateInSampleSize(options, 512, 256);// landscape
		} else {
			options.inSampleSize = calculateInSampleSize(options, 256, 512);// portrait
		}
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(path1, options);
	}
	
	public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;

    if (height > reqHeight || width > reqWidth) {

        final int halfHeight = height / 2;
        final int halfWidth = width / 2;
        while ((halfHeight / inSampleSize) > reqHeight
                && (halfWidth / inSampleSize) > reqWidth) {
            inSampleSize *= 2;
        }
    }

    return inSampleSize;
}

	public static Bitmap getBitmapGallary(String path1) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path1, options);
		options.inSampleSize = calculateInSampleSize(options, 256, 256);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(path1, options);
	}

	void load(){
		if (!interstitialAd.isLoaded() && !mGR.addFree) {
			AdRequest adRequest = new AdRequest.Builder().build();
			interstitialAd.loadAd(adRequest);
			interstitialAd.setAdListener(new AppAdListener(this));
		}
	}

	public void ShowInterstitial() {
		try{handler.sendEmptyMessage(0);}catch(Exception e){}
	}
	Handler handler = new Handler() {public void handleMessage(Message msg) {show_ads();}};

	private void show_ads() {
		try {
			if (interstitialAd != null)
				if (interstitialAd.isLoaded()) {
					interstitialAd.show();
				}
		} catch (Exception e) {
		}
	}
	
	public void openFolder() {
		File myPath = new File(Environment.getExternalStorageDirectory().toString(), mGR.PhotoPath);
		if (myPath.exists())
			new BrowsePicture();
		else
			Toast.makeText(ZRenderer.mStart,R.string.FOLDER, Toast.LENGTH_SHORT).show();
	}
	public void ShowGPlus(int what) {
		try{handlerGPlus.sendEmptyMessage(what);}catch(Exception e){}
	}
	private Handler handlerGPlus = new Handler() {public void handleMessage(Message msg) {show_GPlus(msg.what);}};

	private void show_GPlus(int what) {
		try {
			mPlusOneButton.setVisibility(what);
		} catch (Exception e) {
		}
	}
}