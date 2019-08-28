package com.hututu.app.scaryapp;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

public class MyService extends Service {
	private int interval = 5000; // 1 Second
	private Handler handler = new Handler();
	private Runnable runnable = new Runnable() {
		public void run() {
			// Toast.makeText(MyService.this, "This is new One",
			// Toast.LENGTH_LONG).show();
			
			Intent LaunchIntent = getPackageManager()
					.getLaunchIntentForPackage("com.hututu.app.scaryapp");
			LaunchIntent.putExtra("yogesh", 10);
			startActivity(LaunchIntent);
			stopService(new Intent(getBaseContext(), MyService.class));
			System.out.println("~~~~~~~~~~~~Runnable~~~~~~~~ ");
		}
	};
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		System.out.println("~~~~~~~~~~~~onStartCommand~~~~~~~~ ");
		// Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();
		switch (M.mTime) {
		default:
			interval = 10000;
			break;
		case 1:
			interval = 15000;
			break;
		case 2:
			interval = 30000;
			break;
		case 3:
			interval = 60000;
			break;
		case 4:
			interval = 120000;
			break;
		case 5:
			interval = 300000;
			break;
		case 6:
			interval = 600000;
			break;
		}
		System.out.println(interval);
		
		handler.postDelayed(runnable, interval);
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		System.out.println("~~~~~~~~~~~~onDestroy~~~~~~~~ ");
		super.onDestroy();
		// Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
	}
}