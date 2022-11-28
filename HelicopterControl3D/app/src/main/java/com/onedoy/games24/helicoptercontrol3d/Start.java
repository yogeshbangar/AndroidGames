package com.onedoy.games24.helicoptercontrol3d;
import org.rajawali3d.surface.RajawaliSurfaceView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class Start extends Activity{
	public RajawaliSurfaceView mSurfaceView;
	HTTRenderer mGR;
	public static Context mContext;
	GameAd mGameAd = new GameAd();
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
		mGR=new HTTRenderer(this);
		mSurfaceView=(RajawaliSurfaceView) findViewById(R.id.rajawali_surface); // use the xml to set the view
		mSurfaceView.setSurfaceRenderer(mGR);
        mContext = this;
        WindowManager mWinMgr = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		M.ScreenWidth = mWinMgr.getDefaultDisplay().getWidth();
		M.ScreenHieght = mWinMgr.getDefaultDisplay().getHeight();
		mGameAd.initAds(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (mSurfaceView != null) {
            mSurfaceView.onResume();
        }
        resume();
    }
 
    @Override
    protected void onPause() {
        super.onPause();
        if (mSurfaceView != null) {
            mSurfaceView.onPause();
        }
        pause();
    }
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			switch (M.GameScreen) {
			case M.GAMEMENU:
				Exit();
				break;
			case M.GAMEPLAY:
				M.loopstop();
				mGR.root.changeScreen(M.GAMEPUASE);
				mGR.iSGamePause = true;
				break;
			default:
				mGR.root.changeScreen(M.GAMEMENU);
				break;
			}
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
	void Exit()
	{
		new AlertDialog.Builder(this).setIcon(R.drawable.icon)
				.setTitle("Do you want to exit?")
				.setPositiveButton("No", new DialogInterface.OnClickListener() {
				 public void onClick(DialogInterface dialog, int which){
				   }
				})
				.setNegativeButton("Yes",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								mGR.root.Counter = 0;
								finish();
								M.GameScreen = M.GAMELOGO;
							}
						}).show();
	}
	void pause() {
		if (M.GameScreen == M.GAMEPLAY)
			M.GameScreen = M.GAMEPUASE;
		M.stop();
		SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putInt("a", M.GameScreen);
		editor.putBoolean("b", M.setValue);
		editor.putString("down", M.DOWNLOAD);
		
		for(int i =0;i<mGR.mOpp.length;i++){
			editor.putBoolean("c", mGR.mOpp[i].isAdd);
			editor.putFloat("d", (float)mGR.mOpp[i].m3D_Obj.getX());
		}
		{
			editor.putInt("e", mGR.mPly.OverCount);
			editor.putInt("f", mGR.mPly.imgNo);
			editor.putFloat("g", mGR.mPly.vz);
			if(mGR.mPly.obj.size()>0)
				editor.putFloat("h", (float)mGR.mPly.obj.get(0).getZ());
		}
		editor.putInt("i", mGR.bgNo);
		editor.putInt("j", mGR.plNo);
		editor.putInt("k", mGR.mScore);
		editor.putInt("l", mGR.mHScore);
		editor.putInt("m", mGR.mTotal);
		editor.putBoolean("ba", mGR.addFree);
		editor.putBoolean("bb", mGR.SingUpadate);
		for(int i=0;i<mGR.mAchiUnlock.length;i++)
			editor.putBoolean("bc"+i, mGR.mAchiUnlock[i]);
		editor.commit();
	}
	void resume() {
		SharedPreferences prefs = HTTRenderer.mStart.getSharedPreferences("X", Start.MODE_PRIVATE);
		M.GameScreen = (byte) prefs.getInt("a", M.GAMELOGO);
		M.setValue = prefs.getBoolean("b", M.setValue);
		mGR.bgNo = prefs.getInt("i", mGR.bgNo);
		mGR.plNo = prefs.getInt("j", mGR.plNo);
		mGR.mScore = prefs.getInt("k", mGR.mScore);
		mGR.mHScore = prefs.getInt("l", mGR.mHScore);
		mGR.mTotal = prefs.getInt("m", mGR.mTotal);
		mGR.addFree= prefs.getBoolean("ba", mGR.addFree);
		 mGR.SingUpadate = prefs.getBoolean("bb", mGR.SingUpadate);
		for(int i=0;i<mGR.mAchiUnlock.length;i++)
			mGR.mAchiUnlock[i] = prefs.getBoolean("bc"+i, mGR.mAchiUnlock[i]);
		
		mGR.iSGamePause = true;
	}
	
	void DoNot() {
		new AlertDialog.Builder(this)
			.setIcon(R.drawable.icon)
			.setTitle("Play More for "+mGR.mFont.name[mGR.plNo]+" Helicopter")
			.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {}}
			).show();
	}
	public void load() {
		try{
			handlerLoad.sendEmptyMessage(0);
		}catch(Exception e){
			System.out.println(e);
		}
	}
	Handler handlerLoad = new Handler() {public void handleMessage(Message msg) {loadInter();}};
	private void loadInter(){
		mGameAd.loadInterstitialAd();
	}
	public void ShowInterstitial() {
			try{handler.sendEmptyMessage(0);}catch(Exception e){}
	}
	Handler handler = new Handler() {public void handleMessage(Message msg) {show();}};
	private void show() {
		mGameAd.showInterstitialAd();
	}
}
