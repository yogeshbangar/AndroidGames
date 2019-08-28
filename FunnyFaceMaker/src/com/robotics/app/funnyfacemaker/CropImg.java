package com.robotics.app.funnyfacemaker;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuffXfermode;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class CropImg extends Activity{
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(new MyView(this));
	}

	public class MyView extends View  implements OnTouchListener{
		Bitmap mImg_BG; 
		public MyView(Context context) {
			super(context);
			init();
			// TODO Auto-generated constructor stub
		}
		void init(){
			this.setOnTouchListener(this);
			mImg_BG = LoadImgfromAsset("bg.jpg");
		}
		int counter = 0;
		@Override
		protected void onDraw(Canvas canvas) {
			// TODO Auto-generated method stub
			super.onDraw(canvas);
			int x = getWidth();
			int y = getHeight();
			int radius;
			radius = 100;
			Paint paint = new Paint();
			paint.setStyle(Paint.Style.FILL);
			paint.setColor(Color.WHITE);
			canvas.drawPaint(paint);
			// Use Color.parseColor to define HTML colors
			paint.setColor(Color.parseColor("#CD5C5C"));
			canvas.drawCircle(x / 2, y / 2, radius, paint);
			
			
//			System.out.println("YOgesh game "+counter);
			counter++;
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeWidth(10);
			paint.setColor(Color.BLACK);
			canvas.drawPaint(paint);
			paint.setAntiAlias(false);
			paint.setColor(Color.GREEN);
			canvas.drawBitmap(mImg_BG, 0, 0, paint);
			
			if(Start.BitmapPath != null)
				canvas.drawBitmap(Start.BitmapPath, 0, 0, paint);
			
			canvas.drawPath(path, paint);
			
			if (GmaskImg2 != null) {
				canvas.drawBitmap(GmaskImg2, 0, 0, null);
			}
		}
		Path path = new Path();
		Bitmap GmaskImg2;
		void Crop2(Path path) {
			if(Start.BitmapPath == null)
				return;
//			Bitmap bitmap2 = LoadImgfromAsset("1.jpg");
			Bitmap resultingImage = Bitmap.createBitmap(Start.BitmapPath.getWidth(), Start.BitmapPath.getHeight(),Bitmap.Config.ARGB_8888);
			
			System.out.println("[BW = " + Start.BitmapPath.getWidth() + "][BH = "
					+ Start.BitmapPath.getHeight() + "][RW = "
					+ resultingImage.getWidth() + "][RH = "
					+ resultingImage.getHeight()
					+ "][MiX = " + (int) minX + "][MiY = " + (int) minY
					+ "][MaX = " + (int) maxX + "][MaY = " + (int) maxY
					+ "][M-x = " + (int) (maxX - minX) + "][M-Y = "
					+ (int) (maxY - minY)+"]");
			Canvas canvas = new Canvas(resultingImage);
			Paint paint = new Paint();
			paint.setAntiAlias(true);
			canvas.drawPath(path, paint);
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(Start.BitmapPath, 0, 0, paint);
			
			GmaskImg2 = resultingImage;
			
			if(maxY<Start.BitmapPath.getHeight()){
				Bitmap bit = Bitmap.createBitmap(resultingImage, (int) minX, (int) minY,(int) (maxX - minX), (int) (maxY - minY));
				GameRenderer.mEditImg.add(new EditImg(GameRenderer.addBitmap(bit)));// = GameRenderer.addBitmap(bit);
				finish();
			}else{
				Toast.makeText(GameRenderer.mStart,"Please select Image Area Only.", Toast.LENGTH_LONG).show();
				path.reset();
			}
			
		}
		float minX = 0;
		float minY = 0;
		float maxX = 1;
		float maxY = 1;
		@Override
		public boolean onTouch(View v, MotionEvent event) {
//			System.out.println("YOgesh MotionEvent "+event);
			float eventX = event.getX();
			float eventY = event.getY();
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:// path.reset();
				path.reset();
				path.moveTo(eventX, eventY);
				minX = eventX;
				minY = eventY;
				maxX = eventX+1;
				maxY = eventY+1;
				return true;
			case MotionEvent.ACTION_MOVE:
				path.lineTo(eventX, eventY);
				if (minX > eventX)
					minX = eventX;
				if (minY > eventY)
					minY = eventY;

				if (maxX < eventX)
					maxX = eventX;
				if (maxY < eventY)
					maxY = eventY;
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
		Bitmap LoadImgfromAsset(String ID)
		{
			try{
				return BitmapFactory.decodeStream(GameRenderer.mContext.getAssets().open(ID));}
			catch(Exception e)
			{
				System.out.println(""+ID+"!!!!!!!!!!!!!!!!!!!!!~~~~~~~~~~~~~!!!!!!!!!!!!!!!!!!!!!!!!!"+e.getMessage());
				return null;
			}
		}
	}

}