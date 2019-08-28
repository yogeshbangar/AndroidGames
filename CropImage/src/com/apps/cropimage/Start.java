package com.apps.cropimage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class Start extends Activity 
{
	int _keyCode = 0;
	GameRenderer mGR = null;
	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) 
	{
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setVolumeControlStream(AudioManager.STREAM_MUSIC);//OnlyOneChange
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
	    WindowManager mWinMgr = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
		M.ScreenWidth =mWinMgr.getDefaultDisplay().getWidth();
		M.ScreenHieght=mWinMgr.getDefaultDisplay().getHeight();
		mGR=new GameRenderer(this);
	    VortexView glSurface=(VortexView) findViewById(R.id.vortexview); // use the xml to set the view
	    glSurface.setRenderer(mGR);
	    glSurface.showRenderer(mGR);
	    
	    
	    edittext = (EditText) findViewById(R.id.editText);
	    edittext.setVisibility(View.GONE);
	    edittext.bringToFront();
	}
	@Override 
	public void onPause () {
		M.stop();
		pause();
		super.onPause();
	}
	@Override 
	public void onResume() {                                                                                                       
		resume();
		super.onResume();
		//view.onResume();
	}
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		Log.d("----------------=>  "+keyCode,"   -----------    ");
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
			switch (M.GameScreen) {
			case M.GAMEMENU:
				Exit();
				break;
			case M.APPMAIN:
			case M.GAMETEXT:
				M.GameScreen = M.GAMEPLAY;
				break;
			case M.GameSUB:
				M.GameScreen = M.APPMAIN;
				break;
			case M.GAMEPLAY:
				dOyou();
				break;
			}
			return false;
		}
		_keyCode = keyCode;
		return super.onKeyDown(keyCode,event); 
	}  
	public boolean onKeyUp(int keyCode, KeyEvent event) 
	{
//		if(keyCode==KeyEvent.KEYCODE_BACK)
//			return false;
		_keyCode = 0;
		return super.onKeyUp( keyCode, event ); 
	}
	public void onDestroy()
	{
		super.onDestroy();
	}
	void pause()
	{
		M.stop();
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
	    Editor editor = prefs.edit();
	    editor.putInt("screen", M.GameScreen);
	    editor.putBoolean("setValue", M.setValue);
	    editor.commit();
	}
	void resume()
	{
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		M.GameScreen = prefs.getInt("screen", M.GAMELOGO);
		M.setValue = prefs.getBoolean("setValue", M.setValue);
	}
	void Exit()
	{
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Do you want to Exit?")
	    .setPositiveButton("Rate Us",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
    	   Intent intent = new Intent(Intent.ACTION_VIEW);
    	   intent.setData(Uri.parse(M.LINK+getClass().getPackage().getName()));
    	   startActivity(intent);
      }}).setNeutralButton("No",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
      }}).setNegativeButton("Yes",new DialogInterface.OnClickListener(){public void onClick(DialogInterface dialog, int which) {
		    		   finish();M.GameScreen=M.GAMELOGO;mGR.root.Counter =0;
      }}).show();
  }
	void Network()
	{
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Cheack your network for picasa?")
	    .setNeutralButton("Ok",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
      }}).show();
	}
	public static boolean hasConnection() {
	    ConnectivityManager cm = (ConnectivityManager) GameRenderer.mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
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
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		Editor editor = prefs.edit();
		try {
			super.onActivityResult(requestCode, resultCode, data);
			System.out.println(data + " ~ " + resultCode + " > " + requestCode);
			if (requestCode == 1) {
				if (data == null) {
					/*M.AppScreen = M.APPMENU;
					editor.putInt("screen", M.APPMENU);
					editor.commit();
					System.out.println(M.AppScreen + "  One=null " + path);*/
					return;
				}
				String str = data.getData() + "";
				if (str.contains("com.") /*&& str.contains("picasa")*/) {
					if ((!hasConnection())) {
						Network();
						return;
					}
					BitmapFactory.Options options = new BitmapFactory.Options();
					InputStream is;
					try {
						is = GameRenderer.mContext.getContentResolver().openInputStream(data.getData());
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
					editor.putInt("screen", M.GAMEPLAY);
					editor.commit();
				}
			}
			if (requestCode == 100) {
				if (data == null) {
					return;
				}
				String str = data.getData() + "";
				if ((!hasConnection() && str.contains("com.") && str.contains("picasa"))) {
					Network();
					return;
				}
				BitmapFactory.Options options = new BitmapFactory.Options();
				InputStream is;
				try {
					is = GameRenderer.mContext.getContentResolver().openInputStream(data.getData());
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
	static Bitmap BitmapPath = null;
	public class ThreadB implements Runnable {
		
		 public ThreadB(Bitmap _path) {
			 BitmapPath=_path;
		   }
	    public void run() {
	    	
	    	float dy = M.ScreenHieght/BitmapPath.getHeight();
	    	float dx = BitmapPath.getWidth()*dy;
	    	if(dx > M.ScreenWidth){
	    		dx = M.ScreenWidth/BitmapPath.getWidth();
				dy = BitmapPath.getHeight() * dx;
		    	BitmapPath = GameRenderer.resizeImg(BitmapPath, (int)M.ScreenWidth, (int)dy);
	    	}else{
	    		BitmapPath = GameRenderer.resizeImg(BitmapPath, (int)dx, (int)M.ScreenHieght);
	    	}
	    	if(GameRenderer.mEditImg.size()==0){
	    		GameRenderer.mEditImg.add(new EditImg(GameRenderer.addBitmap(BitmapPath)));
	    	}
	    	else{
	    		Intent gEndIntent = new Intent(GameRenderer.mContext, CropImg.class); // yogesh
	    		GameRenderer.mStart.startActivityForResult(gEndIntent, 1);
	    	}
//	    	mGR.addBit(path);
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
	private EditText edittext;
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
	public void openFolder() {
		File myPath = new File(Environment.getExternalStorageDirectory().toString(), M.DIR);
		if (myPath.exists())
			new BrowsePicture();
		else
			Toast.makeText(this,M.DIR, Toast.LENGTH_SHORT).show();
	}
	void SaveMassage()
	{
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("First save Image.")
	    .setPositiveButton("Ok",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
      }}).show();
	}
	void dOyou() {
	   new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle("Do you want to?")
	    .setPositiveButton("Save",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
    	   mGR.root.screenshot =true;
    	   mGR.isMenu = true;
//	    	   ShowInterstitial();
      }}).setNeutralButton("Share",new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {
    	  mGR.root.screenshot =true;
    	  mGR.isShare =true;
    	  mGR.isMenu = true;
//	    	  ShowInterstitial();
      }}).setNegativeButton("Discard",new DialogInterface.OnClickListener(){public void onClick(DialogInterface dialog, int which) {
		   M.GameScreen = M.GAMEMENU;
      }}).show();
	}
}