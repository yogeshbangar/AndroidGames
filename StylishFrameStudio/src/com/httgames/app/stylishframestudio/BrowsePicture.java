package com.httgames.app.stylishframestudio;

import java.io.File;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Environment;
public class BrowsePicture implements MediaScannerConnectionClient {

    private MediaScannerConnection mMs;
    private File mFile;
    public BrowsePicture() {
    	File folder = new File(Environment.getExternalStorageDirectory().getPath()+M.DIR+"/");
        File[] allFiles = folder.listFiles();
    	mFile = allFiles[0];
        mMs = new MediaScannerConnection(ZRenderer.mContext, this);
        mMs.connect();
    }

    public void onMediaScannerConnected() {
        mMs.scanFile(mFile.getAbsolutePath(), null);
    }

    public void onScanCompleted(String path, Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        ZRenderer.mContext.startActivity(intent);
        mMs.disconnect();
    }
}
