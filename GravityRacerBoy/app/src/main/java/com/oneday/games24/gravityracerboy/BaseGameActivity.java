package com.oneday.games24.gravityracerboy;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
public abstract class BaseGameActivity extends FragmentActivity{
    private final static String TAG = "BaseGameActivity";
    protected boolean mDebugLog = false;
    protected BaseGameActivity() {
        super();
    }
    protected BaseGameActivity(int requestedClients) {
        super();
    }
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onActivityResult(int request, int response, Intent data) {
        super.onActivityResult(request, response, data);
    }
}