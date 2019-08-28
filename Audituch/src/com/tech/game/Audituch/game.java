package com.tech.game.Audituch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;

import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;


public class game extends SurfaceView implements SurfaceHolder.Callback
{
    
    Bitmap mImg_BG,mImg_smallA[],mImg_smallB[],mImg_BigA[],mImg_BigB[];
    Bitmap mImg_smallAniA[],mImg_smallAniB[],mImg_bigAniA[],mImg_bigAniB[];
    Bitmap mImg_char;
	public gameplay mgameplay;
	int mStripCount=5,mSmallWidth,mSmallHight,mAniCount=5;
	//Base mBase = null;
	Context mContext = null;
	/*public game(Base base,int sound) 
	{
		
		super(base.getApplicationContext());
		mBase = base;
		
	}*/
	public game(Context context) {
		super(context);
		mContext = context;
		// TODO Auto-generated constructor stub
		init();
	}

	public game(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		// TODO Auto-generated constructor stub
		init();
	}

	public game(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		// TODO Auto-generated constructor stub
		init();
	}
	void init()
	{
		WindowManager mWinMgr = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
		mImg_BG 	= LoadImgfromAsset("1.jpg");//BitmapFactory.decodeResource(getResources(), R.drawable.gamexback);
		mImg_smallA = new Bitmap[mStripCount];
		mImg_smallB = new Bitmap[mStripCount];
		mImg_BigA	= new Bitmap[mStripCount];
		mImg_BigB 	= new Bitmap[mStripCount];
		mImg_smallAniA= new Bitmap[mAniCount];
		mImg_smallAniB= new Bitmap[mAniCount];
		mImg_bigAniA= new Bitmap[mAniCount];
		mImg_bigAniB= new Bitmap[mAniCount];
		
		mImg_smallA[0]= BitmapFactory.decodeResource(getResources(), R.drawable.s);
	    mImg_smallA[1]= BitmapFactory.decodeResource(getResources(), R.drawable.sa1);
	    mImg_smallA[2]= BitmapFactory.decodeResource(getResources(), R.drawable.sa2);
	    mImg_smallA[3]= BitmapFactory.decodeResource(getResources(), R.drawable.sa3);
	    mImg_smallA[4]= BitmapFactory.decodeResource(getResources(), R.drawable.s);
	    
	    mImg_smallB[0]= BitmapFactory.decodeResource(getResources(), R.drawable.s);
	    mImg_smallB[1]= BitmapFactory.decodeResource(getResources(), R.drawable.sb1);
	    mImg_smallB[2]= BitmapFactory.decodeResource(getResources(), R.drawable.sb2);
	    mImg_smallB[3]= BitmapFactory.decodeResource(getResources(), R.drawable.sb3);
	    mImg_smallB[4]= BitmapFactory.decodeResource(getResources(), R.drawable.s);
	    
	    mImg_BigA[0]= BitmapFactory.decodeResource(getResources(), R.drawable.b);
	    mImg_BigA[1]= BitmapFactory.decodeResource(getResources(), R.drawable.ba1);
	    mImg_BigA[2]= BitmapFactory.decodeResource(getResources(), R.drawable.ba2);
	    mImg_BigA[3]= BitmapFactory.decodeResource(getResources(), R.drawable.ba3);
	    mImg_BigA[4]= BitmapFactory.decodeResource(getResources(), R.drawable.b);
	    
	    
	    mImg_BigB[0]= BitmapFactory.decodeResource(getResources(), R.drawable.b);
	    mImg_BigB[1]= BitmapFactory.decodeResource(getResources(), R.drawable.bb1);
	    mImg_BigB[2]= BitmapFactory.decodeResource(getResources(), R.drawable.bb2);
	    mImg_BigB[3]= BitmapFactory.decodeResource(getResources(), R.drawable.bb3);
	    mImg_BigB[4]= BitmapFactory.decodeResource(getResources(), R.drawable.b);
	    
	    mImg_smallAniA[0]= BitmapFactory.decodeResource(getResources(), R.drawable.sania1);
	    mImg_smallAniA[1]= BitmapFactory.decodeResource(getResources(), R.drawable.sania2);
	    mImg_smallAniA[2]= BitmapFactory.decodeResource(getResources(), R.drawable.sania3);
	    mImg_smallAniA[3]= BitmapFactory.decodeResource(getResources(), R.drawable.sania4);
	    mImg_smallAniA[4]= BitmapFactory.decodeResource(getResources(), R.drawable.sania5);
	    
	    
	    mImg_smallAniB[0]= BitmapFactory.decodeResource(getResources(), R.drawable.sanib1);
	    mImg_smallAniB[1]= BitmapFactory.decodeResource(getResources(), R.drawable.sanib2);
	    mImg_smallAniB[2]= BitmapFactory.decodeResource(getResources(), R.drawable.sanib3);
	    mImg_smallAniB[3]= BitmapFactory.decodeResource(getResources(), R.drawable.sanib4);
	    mImg_smallAniB[4]= BitmapFactory.decodeResource(getResources(), R.drawable.sanib5);
	    
	    mImg_bigAniA[0]= BitmapFactory.decodeResource(getResources(), R.drawable.bania1);
	    mImg_bigAniA[1]= BitmapFactory.decodeResource(getResources(), R.drawable.bania2);
	    mImg_bigAniA[2]= BitmapFactory.decodeResource(getResources(), R.drawable.bania3);
	    mImg_bigAniA[3]= BitmapFactory.decodeResource(getResources(), R.drawable.bania4);
	    mImg_bigAniA[4]= BitmapFactory.decodeResource(getResources(), R.drawable.bania5);
	    
	    mImg_bigAniB[0]= BitmapFactory.decodeResource(getResources(), R.drawable.banib1);
	    mImg_bigAniB[1]= BitmapFactory.decodeResource(getResources(), R.drawable.banib2);
	    mImg_bigAniB[2]= BitmapFactory.decodeResource(getResources(), R.drawable.banib3);
	    mImg_bigAniB[3]= BitmapFactory.decodeResource(getResources(), R.drawable.banib4);
	    mImg_bigAniB[4]= BitmapFactory.decodeResource(getResources(), R.drawable.banib5);
	    mImg_char = BitmapFactory.decodeResource(getResources(), R.drawable.charecter);
//		mImg_BG 	= resizeImg(mImg_BG,mWinMgr.getDefaultDisplay().getWidth(),mWinMgr.getDefaultDisplay().getHeight());
		CropImg();
		Intent data =  ((Activity)mContext).getIntent();
		int sound = data.getIntExtra("Level",10);
		mgameplay=new gameplay(this,mWinMgr.getDefaultDisplay().getWidth(),mWinMgr.getDefaultDisplay().getHeight(),sound);
		getHolder().addCallback(this);
		setFocusable(true);
	}
	//@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) 
	{
		Log.d("in game thread","******************surface changed " );
	}

	//@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mgameplay.is_run=true;
		mgameplay.start();
	}

	//@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
        while (retry) {
        			 mgameplay.is_run=false;
                     mgameplay.close();
                     Music.stop(mContext);
                     ((Activity)mContext).finish();
                     retry = false;
        }
	}
	//override 
	public void onDraw(Canvas canvas)
	{
		canvas.drawColor(Color.WHITE);
		mgameplay.onDraw(canvas);
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		mgameplay.doKeyDown(keyCode, event);
		return true; 
	}  
	public boolean onKeyUp(int keyCode, KeyEvent event) 
	{  	
		mgameplay.doKeyUp(keyCode, event);			
	 	return true; 
	}  

	Path path = new Path();

	public boolean onTouchEvent(MotionEvent event) {
		// mgameplay.doTouchEvent(event);
		float eventX = event.getX();
		float eventY = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:// path.reset();
			path.moveTo(eventX, eventY);
			return true;
		case MotionEvent.ACTION_MOVE:
			path.lineTo(eventX, eventY);
			break;
		case MotionEvent.ACTION_UP:
			//nothing to do
			Crop2(path);
			break;
		default:
			return false;
		}
		invalidate();
		return true;
	}
	public void sensor_change(float x, float y, float z){}

	Bitmap resizeImg(Bitmap bitmapOrg,int newWidth,int newHeight)
	{
		 int width = bitmapOrg.getWidth();
		 int height = bitmapOrg.getHeight();
		 float scaleWidth = ((float) newWidth) / width;
		 float scaleHeight = ((float) newHeight) / height;
		
		 Matrix matrix = new Matrix();
		 matrix.postScale(scaleWidth, scaleHeight);
		 matrix.postRotate(0);
		 Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0,width, height, matrix, true);
		 return resizedBitmap;
	}
	Bitmap GmaskImg;
	void CropImg(){
		Bitmap obmp = BitmapFactory.decodeResource(getResources(), R.drawable.front);
		Bitmap resultImg = Bitmap.createBitmap(obmp.getWidth(), obmp.getHeight(), Bitmap.Config.ARGB_8888);
		Bitmap maskImg = Bitmap.createBitmap(512,512, Bitmap.Config.ARGB_8888);

		Canvas mCanvas = new Canvas(resultImg);
		Canvas maskCanvas = new Canvas(maskImg);

		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL);;
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

		Path path = new Path();
		path.moveTo(10,10);
		path.moveTo(100,40);
		path.moveTo(90,140);
		path.moveTo(50,100);
		path.moveTo(70,70);
		path.close();

		maskCanvas.drawPath(path, paint);   
		mCanvas.drawBitmap(obmp, 0, 0, null);
		mCanvas.drawBitmap(maskImg, 0, 0, paint);
		GmaskImg = resultImg;
//		Crop2();
		System.out.println(GmaskImg+"!!!!!!!!!!!!!!!!!~~ "+GmaskImg.getWidth()+"  "+GmaskImg.getHeight());
	}
	Bitmap GmaskImg2;
	void Crop2(Path path) {

//		Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(),R.drawable.gamexback);
//		Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(),R.drawable.gamexback);
		Bitmap bitmap2 = LoadImgfromAsset("1.jpg");
		Bitmap resultingImage = Bitmap.createBitmap(bitmap2.getWidth(), bitmap2.getHeight(),Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(resultingImage);

		Paint paint = new Paint();
		paint.setAntiAlias(true);
		/*Path path = new Path();
		path.lineTo(150, 0);
		path.lineTo(230, 120);
		path.lineTo(70, 120);
		path.lineTo(70, 90);
		path.lineTo(150, 0);*/

		canvas.drawPath(path, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap2, 0, 0, paint);
		GmaskImg2 = resultingImage;
		mgameplay.sx =mgameplay.sy= 0;
	}
	Bitmap LoadImgfromAsset(String ID)
	{
		try{
			return BitmapFactory.decodeStream(mContext.getAssets().open(ID));}
		catch(Exception e)
		{
			System.out.println(""+ID+"!!!!!!!!!!!!!!!!!!!!!~~~~~~~~~~~~~!!!!!!!!!!!!!!!!!!!!!!!!!"+e.getMessage());
			return null;
		}
	}
}
