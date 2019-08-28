package com.oneday.games24.funnyfacechanger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

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
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class Start extends Activity
{
	
	int _keyCode = 0;
	AdView adView = null;
	GameRenderer mGR = null;
	private static Context CONTEXT;
	private InterstitialAd interstitialAd;

	void callAdds() {
		adView = (AdView) this.findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);
		adView.setAdListener(new AdListener() {
			public void onAdLoaded() {
				adView.bringToFront();
				System.out.println("   onCreate  ");
			}
		});

	}
	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) 
	{
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);//OnlyOneChange
		CONTEXT	=	this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId(getString(R.string.INTERSTITIAL_ID));
		WindowManager mWinMgr = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
		M.ScreenWidth =mWinMgr.getDefaultDisplay().getWidth();
		M.ScreenHieght=mWinMgr.getDefaultDisplay().getHeight();
		mGR=new GameRenderer(this);
	    VortexView glSurface=(VortexView) findViewById(R.id.vortexview); // use the xml to set the view
	    glSurface.setRenderer(mGR);
	    glSurface.showRenderer(mGR);
	    if(!getSharedPreferences("X", MODE_PRIVATE).getBoolean("addFree", mGR.addFree))
	    	callAdds();
//	    System.out.println("   onCreate  ");
	}
	
	
	
	public static Context getContext() {
	        return CONTEXT;
	}
	
	@Override 
	public void onStart () {
		super.onStart();
//		System.out.println("   onStart  ");
	}
	
	@Override 
	public void onPause () {
		M.stop(CONTEXT);
		pause();
		super.onPause();
	}
	@Override 
	public void onResume() {
		resume();
		super.onResume();
//		if(!mGR.addFree)
//	    	callAdds();
		//view.onResume();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.d("----------------=>  " + keyCode, "   -----------    ");
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			switch (M.AppScreen) {
			case M.APPMENU:
				Exit();
				break;
			case M.APPMAIN:
			case M.APPSUB:
				M.AppScreen = M.APPCLIPS;
				break;
			case M.APPABOUT:
			case M.APPOPTION:
				M.AppScreen = M.APPMENU;
				break;
			}
			return false;
		}
		_keyCode = keyCode;
		return super.onKeyDown(keyCode, event);
	}

	public boolean onKeyUp(int keyCode, KeyEvent event) 
	{
//		if(keyCode==KeyEvent.KEYCODE_BACK)
//			return false;
		_keyCode = 0;
		return super.onKeyUp( keyCode, event ); 
	}
	public void onDestroy() {
		super.onDestroy();
//		if(mGR.mInAPP!=null)
//			mGR.mInAPP.onDestroy();
	}
	void pause()
	{
		M.stop(GameRenderer.mContext);
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
	    Editor editor = prefs.edit();
//	    if(M.AppScreen == M.APPOBJECT ||M.AppScreen == M.APPEDIT || M.AppScreen == M.APPCLIPS)
//	    	editor.putInt("screen", M.APPLOGO);
//	    else 
    	editor.putInt("screen", M.AppScreen);
	    editor.putBoolean("setValue", M.setValue);
	    editor.putBoolean("addFree", mGR.addFree);
	    editor.putString("path", path);
	    editor.commit();
	    System.out.println("Screen =========pause======== "+path);
	}
	void resume()
	{
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.AppScreen = prefs.getInt("screen", M.APPHTT);
		M.setValue = prefs.getBoolean("setValue", M.setValue);
		path = prefs.getString("path", path);
		mGR.addFree = prefs.getBoolean("addFree", mGR.addFree);
		mGR.root.callAds();
	}
	void Exit()
	{
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Do you want to Exit?")
	    .setPositiveButton("More",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
    	   Intent intent = new Intent(Intent.ACTION_VIEW);
    	   intent.setData(Uri.parse(M.MARKET));
    	   startActivity(intent);
      }}).setNeutralButton("No",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
      }}).setNegativeButton("Yes",new DialogInterface.OnClickListener(){public void onClick(DialogInterface dialog, int which) {
		    		   finish();M.AppScreen=M.APPHTT;mGR.root.Counter =0;
      }}).show();
	}
	
	
	
	void dOyou()
	{
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Do you want to Save?")
	    .setNeutralButton("No",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
	    	M.AppScreen = M.APPCLIPS;
	    	if(!mGR.addFree)
	    		show();
      }}).setNegativeButton("Yes",new DialogInterface.OnClickListener(){public void onClick(DialogInterface dialog, int which) {
    	  mGR.root.screenshot = true;
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
	    ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(
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
			/*if (requestCode == InApp.RC_REQUEST) {
				if (!mGR.mInAPP.mHelper.handleActivityResult(requestCode, resultCode, data)) {
					super.onActivityResult(requestCode, resultCode, data);
				} else {
				}
			}*/
			if (requestCode == 1) {
				if (data == null) {
					M.AppScreen = M.APPMENU;
					editor.putInt("screen", M.APPMENU);
					editor.commit();
					System.out.println(M.AppScreen + "  One=null " + path);
					return;
				}
				String str = data.getData() + "";
				if (str.contains("com.google.android") && str.contains("picasa")) {
					if ((!hasConnection())) {
						Network();
						return;
					}
					BitmapFactory.Options options = new BitmapFactory.Options();
					InputStream is;
					try {
						is = CONTEXT.getContentResolver().openInputStream(data.getData());
						final Bitmap bitmap = BitmapFactory.decodeStream(is,null, options);
						ThreadB te = new ThreadB(bitmap);
						te.run();
						is.close();
					} catch (FileNotFoundException e) {
						System.out.println(e.toString() + " FileNotFound");
						e.printStackTrace();
					} catch (IOException e) {
						System.out.println(e.toString() + " IOException  ");
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
						System.out.println("Image Gallery " + filePath);
						cursor.close();
						ThreadB te = new ThreadB(getBitmap(filePath));
						te.run();
						System.out.println("Load Gallery File Path" + filePath);
					}
				}
			}
			if (requestCode == 2) {
				path = prefs.getString("path", path);
				System.out.println("Load Image 2 ~~~~ " + path);
				File folder = new File(path);
				if (folder.exists()) {
					ThreadB te = new ThreadB(getBitmap(path));
					te.run();
					System.out.println("ThreadB 2  >" + path);
				} else {
					editor.putInt("screen", M.APPMENU);
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
					is = CONTEXT.getContentResolver().openInputStream(data.getData());
					final Bitmap bitmap = BitmapFactory.decodeStream(is, null,options);
					ThreadB te = new ThreadB(bitmap);
					te.run();
					is.close();
				} catch (FileNotFoundException e) {
					System.out.println(e.toString() + " FileNotFound");
					e.printStackTrace();
				} catch (IOException e) {
					System.out.println(e.toString() + " IOException  ");
					e.printStackTrace();
				}
			}

		} catch (Exception e) {

		}
	}
	public class ThreadA implements Runnable {
		String path ="";
		 public ThreadA(String _path) {
			 path=_path;
		   }
	    public void run() {
	    	//Code
//	    	mGR.addImage(path);
	    }
	}
	public class ThreadB implements Runnable {
		Bitmap path ;
		 public ThreadB(Bitmap _path) {
			 path=_path;
		   }
	    public void run() {
	    	//Code
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
			System.out.println("takePhoto" + "exists Size = " + str);
		}
		path = str + "/" + System.currentTimeMillis() + ".png";
		File photo = new File(path);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
		startActivityForResult(intent, 2);
		System.out.println(path + "  takePhoto =>> " + str);
	}
	void load(){
		if (!interstitialAd.isLoaded() && !mGR.addFree) {
			AdRequest adRequest = new AdRequest.Builder()
			.build();
			interstitialAd.loadAd(adRequest);
			interstitialAd.setAdListener(new GameAdListener(this));
		}
	}

	public void show() {
		try{handler.sendEmptyMessage(0);}catch(Exception e){}
	}
	Handler handler = new Handler() {public void handleMessage(Message msg) {show_ads();}};

	void show_ads() {
		try {
			if (interstitialAd != null)
				if (interstitialAd.isLoaded()) {
					interstitialAd.show();
				}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	public static Bitmap getBitmap(String path1) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path1, options);
		int imageHeight = options.outHeight;
		int imageWidth = options.outWidth;
		if (imageWidth > imageHeight) {
			options.inSampleSize = calculateInSampleSize(options, 512, 256);// landscape
		} else {
			options.inSampleSize = calculateInSampleSize(options, 256, 512);// portrait
		}
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(path1, options);
	}

	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
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
}
