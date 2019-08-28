package com.htt.app.photoframeschool;

import java.io.File;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Environment;

public class BrowsePhoto implements MediaScannerConnectionClient {
	
	public void onScanCompleted(String path, Uri uri) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(uri);
		GameRenderer.mContext.startActivity(intent);
		mMs.disconnect();
	}
	private File mFile;
	private MediaScannerConnection mMs;

	public void onMediaScannerConnected() {
		mMs.scanFile(mFile.getAbsolutePath(), null);
	}

	public BrowsePhoto() {
		File folder = new File(Environment.getExternalStorageDirectory()
				.getPath() + M.DIR + "/");
		File[] allFiles = folder.listFiles();
		mFile = allFiles[0];
		mMs = new MediaScannerConnection(GameRenderer.mContext, this);
		mMs.connect();
	}

}
