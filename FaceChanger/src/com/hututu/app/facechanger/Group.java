

package com.hututu.app.facechanger;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import javax.microedition.khronos.opengles.GL10;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.view.MotionEventCompat;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.widget.Toast;
public class Group extends Mesh 
{
	GameRenderer mGR = null;
	int Counter =0;
	float sx,sy;
	public void setting(){float ud=.1f;switch (GameRenderer.mStart._keyCode) {case 1:sy-=ud;break;case 2:sy+=ud;break;case 3:sx-=ud;break;case 4:sx+=ud;break;}System.out.println(M.AppScreen+"~~~~~~~~~~~~~~~      "+sx+"~~~~~~~~~~~~       "+sy);}
	public boolean Handle(MotionEvent event){
		if(CircRectsOverlap(-.8f,0.0f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 3;
		if(CircRectsOverlap(0.8f,0.0f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 4;
		if(CircRectsOverlap(-.0f,-.8f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 1;
		if(CircRectsOverlap(0.0f,0.8f,.1f, .1f, screen2worldX(event.getX()), screen2worldY(event.getY()), .1f))GameRenderer.mStart._keyCode = 2;
		if(event.getAction()== MotionEvent.ACTION_UP)GameRenderer.mStart._keyCode = 0;
		return true;
	}
	public Group(GameRenderer _GameRenderer)
	{
		mGR = _GameRenderer;
	}
	public void draw(GL10 gl) 
	{
//		M.AppScreen=M.APPADD;
		switch (M.AppScreen) {
		case M.APPLOGO:
			DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if(Counter>60){
				if(mGR.addFree)
					M.AppScreen = M.APPGALERY;
				else
					M.AppScreen = M.APPADD;
				M.AppScreen = M.APPGALERY;
				Counter = 0;
			}
			break;
		case M.APPADD:
			if(Counter>100)
				DrawTransScal(gl,mGR.mTex_Cancle, 15,30f,mGR.mSel==1?1.1f:1,mGR.mSel==1?.5f:1);
			else 
			{
				DrawTextureS(gl, mGR.mTex_Ling, 0, 30,.9f);
				mGR.mTex_LingBar.drawSS(gl,-22.4f,30, (Counter%100)*.22f, .8f);//10
			}
			break;
		case M.APPEDIT:
			Draw_Edit(gl);
			break;
		case M.APPOBJECT:
			Draw_Objet(gl);
			break;
		case M.APPGALERY:
			Draw_Gallery(gl);
			break;
		case M.APPCLIPS:
			Draw_Clips(gl);
			break;
		case M.APPABOUT:
			Draw_Anout(gl);
			break;
		case M.APPOPTION:
			Draw_Option(gl);
			break;
		}
		Counter++;
//		setting();
	}
	public boolean TouchEvent(MotionEvent event) 
	{

		switch (M.AppScreen) {
		case M.APPLOGO:
			break;
		case M.APPADD:
			mGR.mSel = 0;
			if (CirCir(15,30f,mGR.mTex_Cancle.width()*.5f, Uscreen2worldX(event.getX()),
					Uscreen2worldY(event.getY()), 5f)) {
				mGR.mSel = 1;
			}
			if ((MotionEvent.ACTION_UP == event.getAction()) && (Counter > 100)) {
				if (mGR.mSel == 1) {
					M.AppScreen = M.APPGALERY;
				}
				mGR.mSel = 0;
			}
			break;
		case M.APPEDIT:
			Handle_Edit(event);
			break;
		case M.APPOBJECT:
			Handle_Objet(event);
			break;
		case M.APPGALERY:
			Handle_Gallery(event);
			break;
		case M.APPCLIPS:
			Handle_Clips(event);
			break;
		case M.APPABOUT:
			Handle_About(event);
			break;
		case M.APPOPTION:
			Handle_Option(event);
			break;
		}
	
		Handle(event);
		return true;
	}
	
	
	final float chY = 5f; 
	void Draw_Anout(GL10 gl)
	{
		DrawTextureS(gl, mGR.mTex_BG	, 0, chY,1.1f);
		DrawTextureS(gl, mGR.mTex_Board	, 0, chY,1.0f);
		DrawTextureS(gl, mGR.mTex_AbouUs, 0, chY,1.0f);
		
		DrawTransScal(gl, mGR.mTex_Back, -15f, chY+30f,mGR.mSel==4?1.1f:1,mGR.mSel==4?0.5f:1);

		DrawTransScal(gl, mGR.mTex_Like, -15f,chY-30f,mGR.mSel==1?1.1f:1,mGR.mSel==1?0.5f:1);
		DrawTransScal(gl, mGR.mTex_gp,   0f,chY-30f,mGR.mSel==2?1.1f:1,mGR.mSel==2?0.5f:1);
		DrawTransScal(gl, mGR.mTex_RateUs,15f,chY-30f,mGR.mSel==3?1.1f:1,mGR.mSel==3?0.5f:1);
	}
	boolean Handle_About(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(-15f, chY-30f, mGR.mTex_GlrTxt.width()*.4f, mGR.mTex_GlrTxt.Height()*.4f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1))
		{
			mGR.mSel = 1;//facebook
		}
		if(CircRectsOverlap(0f,chY-30f, mGR.mTex_GlrTxt.width()*.4f, mGR.mTex_GlrTxt.Height()*.4f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1))
		{
			mGR.mSel = 2;//Google Plus
		}
		if(CircRectsOverlap(15f,chY-30f, mGR.mTex_GlrTxt.width()*.4f, mGR.mTex_GlrTxt.Height()*.4f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1))
		{
			mGR.mSel = 3;//Rate Us
		}
		if(CircRectsOverlap(-15f, chY+30f, mGR.mTex_GlrTxt.width()*.4f, mGR.mTex_GlrTxt.Height()*.4f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1))
		{
			mGR.mSel = 4;//Back
		}
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mGR.mSel) {
			case 1:
				facebook();
				break;
			case 2:
				google();
				break;
			case 3:
				RateUs();
				break;
			case 4:
				M.AppScreen = M.APPGALERY;
				break;
			
			}
			mGR.mSel = 0;
		}
		return true;
	}
	
	
	
	void Draw_Option(GL10 gl)
	{
		DrawTextureS(gl, mGR.mTex_BG	, 0, chY,1.1f);
		DrawTextureS(gl, mGR.mTex_Board	, 0, chY,1.0f);
		
		DrawTextureS(gl, mGR.mTex_Dollor , 0,chY+15,1.0f);
		DrawTextureS(gl, mGR.mTex_AddFree, 0, chY,1.5f);
		
		DrawTransScal(gl, mGR.mTex_Letter,-10,chY-15,mGR.mSel==5?1.1f:1,mGR.mSel==5?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Buy	 , 10,chY-15,mGR.mSel==6?1.1f:1,mGR.mSel==6?0.5f:1);
		
		DrawTransScal(gl, mGR.mTex_Back, -15f,chY+30f,mGR.mSel==4?1.1f:1,mGR.mSel==4?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Like, -15f,chY-30f,mGR.mSel==1?1.1f:1,mGR.mSel==1?0.5f:1);
		DrawTransScal(gl, mGR.mTex_gp,   0f,chY-30f,mGR.mSel==2?1.1f:1,mGR.mSel==2?0.5f:1);
		DrawTransScal(gl, mGR.mTex_RateUs,15f,chY-30f,mGR.mSel==3?1.1f:1,mGR.mSel==3?0.5f:1);
	}
	boolean Handle_Option(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(-15f, chY-30f, mGR.mTex_Like.width()*.4f, mGR.mTex_Like.Height()*.4f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1))
		{
			mGR.mSel = 1;//facebook
		}
		if(CircRectsOverlap(0f,chY-30f, mGR.mTex_Like.width()*.4f, mGR.mTex_Like.Height()*.4f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1))
		{
			mGR.mSel = 2;//Google Plus
		}
		if(CircRectsOverlap(15f,chY-30f, mGR.mTex_Like.width()*.4f, mGR.mTex_Like.Height()*.4f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1))
		{
			mGR.mSel = 3;//Rate Us
		}
		if(CircRectsOverlap(-15f,chY+ 30f, mGR.mTex_Back.width()*.4f, mGR.mTex_Back.Height()*.4f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1))
		{
			mGR.mSel = 4;//Back
		}
		
		if(CircRectsOverlap(-10f,chY-15f, mGR.mTex_Letter.width()*.4f, mGR.mTex_Letter.Height()*.4f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1))
		{
			mGR.mSel = 5;//Latter
		}
		if(CircRectsOverlap( 10f,chY-15f, mGR.mTex_Letter.width()*.4f, mGR.mTex_Letter.Height()*.4f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1))
		{
			mGR.mSel = 6;//Buy
		}
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mGR.mSel) {
			case 1:
				facebook();
				break;
			case 2:
				google();
				break;
			case 3:
				RateUs();
				break;
			case 4:case 5:
				M.AppScreen = M.APPGALERY;
				break;
			case 6:
				mGR.mMainActivity.onBuyGold50000(null);
				break;
			}
			mGR.mSel = 0;
		}
		return true;
	}
	
	
	void Draw_Images(GL10 gl)
	{
		for(int i=0;i<mGR.mEdit.size();i++){
			DrawTextRS(gl, mGR.mEdit.get(i).mTexture, 
					mGR.mEdit.get(i).x, 
					mGR.mEdit.get(i).y,
					mGR.mEdit.get(i).angle+mGR.mEdit.get(i).langle,
					mGR.mEdit.get(i).lsx*mGR.mEdit.get(i).sx,
					mGR.mEdit.get(i).lsy*mGR.mEdit.get(i).sy);
		}
	}
	
	void Draw_Edit(GL10 gl)
	{
		DrawTextureS(gl, mGR.mTex_BG, 0, 0,1.1f);
		DrawTextureS(gl, mGR.mTex_Board, 0, 0,1.0f);
		
		
		Draw_Images(gl);
		
		DrawTextureS(gl, mGR.mTex_Cancle, -10f, 30f,1.0f);
		DrawTextureS(gl, mGR.mTex_Apply , 010f, 30f,1.0f);
		DrawTextureS(gl, mGR.mTex_MoveA[mGR.action == 0?1:0]		, -17+0*11,-30,1.0f);
		DrawTextureS(gl, mGR.mTex_ScaleWidth[mGR.action == 1?1:0] , -17+1*11,-30,1.0f);
		DrawTextureS(gl, mGR.mTex_SclHgh[mGR.action == 2?1:0]		, -17+2*11,-30,1.0f);
		DrawTextureS(gl, mGR.mTex_Rotate[mGR.action == 3?1:0]		, -17+3*11,-30,1.0f);
	}
	boolean Handle_Edit(MotionEvent event)
	{
		mGR.mSel = 0;
		
		if(CircRectsOverlap(-10f, 30f, mGR.mTex_GlrTxt.width()*.4f, mGR.mTex_GlrTxt.Height()*.4f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1))
		{
			mGR.mSel = 1;//Cancel
		}
		if(CircRectsOverlap(010f, 30f, mGR.mTex_GlrTxt.width()*.4f, mGR.mTex_GlrTxt.Height()*.4f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1))
		{
			mGR.mSel = 2;//Right
		}
		if(CircRectsOverlap( -17+0*11,-30, mGR.mTex_GlrTxt.width()*.4f, mGR.mTex_GlrTxt.Height()*.4f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1))
		{
			mGR.mSel = 3;//Scale
		}
		if(CircRectsOverlap( -17+1*11,-30, mGR.mTex_GlrTxt.width()*.4f, mGR.mTex_GlrTxt.Height()*.4f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1))
		{
			mGR.mSel = 4; //Width Scale 
		}
		if(CircRectsOverlap( -17+2*11,-30, mGR.mTex_GlrTxt.width()*.4f, mGR.mTex_GlrTxt.Height()*.4f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1))
		{
			mGR.mSel = 5;//Height Scale
		}
		if(CircRectsOverlap( -17+3*11,-30, mGR.mTex_GlrTxt.width()*.4f, mGR.mTex_GlrTxt.Height()*.4f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1))
		{
			mGR.mSel = 6;//Rotate
		}
		
		if(mGR.mEdit.size()>0 && mGR.mSel == 0)
			TouchMove(event);
		
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mGR.mSel) {
			case 1:
				if(mGR.mEdit.size()>1){
				M.AppScreen = M.APPOBJECT;
				mGR.mEdit.remove(mGR.mEdit.size()-1);
				}
				else{
					M.AppScreen = M.APPGALERY;
				}
				
				break;
			case 2:
				M.AppScreen = M.APPOBJECT;
				mGR.mRedo.clear();
				break;
			case 3:
				mGR.action = 0;//Scal
				break;
			case 4:
				mGR.action = 1;//Undo
				break;
			case 5:
				mGR.action = 2;//redo
				break;
			case 6:
				mGR.action = 3;//object
				break;
			}
			mGR.mSel = 0;
		}
		return true;
	}
	float oldDist;
	float oldAng;
	float mode = 0;
	float mmode = 0;
	float scale = 1;
	float cx,cy;
    public boolean TouchMove( MotionEvent event) 
    {
    	int work = mGR.mEdit.size()-1;
    	switch (event.getAction() & MotionEvent.ACTION_MASK) 
    	{
		case MotionEvent.ACTION_DOWN: // first finger down only
			mode = 0;
			mmode = 1;
			cx = event.getX();
			cy = event.getY();
			break;
		case MotionEvent.ACTION_UP: // first finger lifted
			 mode = 0;
			 mmode = 0;
			 mGR.mEdit.get(work).lsx = mGR.mEdit.get(work).lsx*mGR.mEdit.get(work).sx;
			 mGR.mEdit.get(work).lsy = mGR.mEdit.get(work).lsy*mGR.mEdit.get(work).sy;
			 mGR.mEdit.get(work).sx = mGR.mEdit.get(work).sy = 1;
			 mGR.mEdit.get(work).angle +=mGR.mEdit.get(work).langle;
			 mGR.mEdit.get(work).langle = 0;
			break;
		case MotionEvent.ACTION_POINTER_UP: // second finger lifted
			 mode = 0;
			 mmode = 0;
			break;
		case MotionEvent.ACTION_POINTER_DOWN: // first and second finger down
			mode = 1;
			mmode = 0;
			oldDist = spacing(event);
			oldAng = Angle(event);
			break;
		case MotionEvent.ACTION_MOVE:
			if(mode == 1)
			{
				float newDist = spacing(event);
				if (mGR.action == 0) 
				{
					scale = (newDist / oldDist);
					mGR.mEdit.get(work).sy=mGR.mEdit.get(work).sx=scale;
				}
				if (mGR.action == 1) 
				{
					scale = (newDist / oldDist);
					mGR.mEdit.get(work).sx=scale;
				}
				if (mGR.action == 2) 
				{
					scale = (newDist / oldDist);
					mGR.mEdit.get(work).sy=scale;
				}
				if (mGR.action == 3){
					float  newAng = Angle(event);
					mGR.mEdit.get(work).langle = (float)Math.toDegrees(oldAng-newAng);
				}
			}else if (mmode == 1) 
			{
				mGR.mEdit.get(work).x+=Uscreen2worldX(event.getX())-Uscreen2worldX(cx);
				mGR.mEdit.get(work).y+= Uscreen2worldY(event.getY())-Uscreen2worldY(cy);
				cx = event.getX();
				cy = event.getY();
			}
			break;
		}
        return true; // indicate event was handled
    }
    float spacing(MotionEvent event)
	{
    	int count =  MotionEventCompat.getPointerCount(event);
    	if(count<2){
    		return 0;
    	}
        float x = screen2worldX(event.getX(0)) - screen2worldX(event.getX(1));
        float y = screen2worldY(event.getY(0)) - screen2worldY(event.getY(1));
        return FloatMath.sqrt(x * x + y * y);
    }
    float Angle(MotionEvent event)
	{
    	int count =  MotionEventCompat.getPointerCount(event);
    	if(count<2){
    		return 0;
    	}
        float x = screen2worldX(event.getX(0)) - screen2worldX(event.getX(1));
        float y = screen2worldY(event.getY(0)) - screen2worldY(event.getY(1));
        return (float)GetAngle(y, x);
    }
	
	void Draw_Objet(GL10 gl)
	{
		DrawTextureS(gl, mGR.mTex_BG, 0, 0,1.1f);
		DrawTextureS(gl, mGR.mTex_Board, 0, 0,1.0f);
		
		Draw_Images(gl);
		
		DrawTransScal(gl, mGR.mTex_Back, -15f, 30f,mGR.mSel==1?1.1f:1,mGR.mSel==1?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Save,   0f, 30f,mGR.mSel==2?1.1f:1,mGR.mSel==2?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Shared,15f, 30f,mGR.mSel==3?1.1f:1,mGR.mSel==3?0.5f:1);
		if(mGR.mEdit.size()>1)
			DrawTransScal(gl, mGR.mTex_Undo,  -15f,-30f,mGR.mSel==4?1.1f:1,mGR.mSel==4?0.5f:1);
		else
			DrawTransScal(gl, mGR.mTex_Undo,  -15f,-30f,1,.4f);
		if(mGR.mRedo.size()>0)
			DrawTransScal(gl, mGR.mTex_Redo,    0f,-30f,mGR.mSel==5?1.1f:1,mGR.mSel==5?0.5f:1);
		else
			DrawTransScal(gl, mGR.mTex_Redo,    0f,-30f,1,.4f);
		DrawTransScal(gl, mGR.mTex_Gallery,15f,-30f,mGR.mSel==6?1.1f:1,mGR.mSel==6?0.5f:1);
		
		if(screenshot)
			SaveScreen(gl);
		
	}
//	Handler handler = new Handler() {public void handleMessage(Message msg) {GameRenderer.mStart.loadInter();}};
	boolean Handle_Objet(MotionEvent event)
	{
		mGR.mSel = 0;
		if(CircRectsOverlap(-15f, 30f, mGR.mTex_GlrTxt.width()*.4f, mGR.mTex_GlrTxt.Height()*.4f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1))
		{
			mGR.mSel = 1;//Back
		}
		if(CircRectsOverlap(-00f, 30f, mGR.mTex_GlrTxt.width()*.4f, mGR.mTex_GlrTxt.Height()*.4f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1))
		{
			mGR.mSel = 2;//Save
		}
		if(CircRectsOverlap(015f, 30f, mGR.mTex_GlrTxt.width()*.4f, mGR.mTex_GlrTxt.Height()*.4f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1))
		{
			mGR.mSel = 3;//Share
		}
		if(CircRectsOverlap(-15f,-30f, mGR.mTex_GlrTxt.width()*.4f, mGR.mTex_GlrTxt.Height()*.4f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1))
		{
			mGR.mSel = 4; //undo
		}
		if(CircRectsOverlap(- 0f,-30f, mGR.mTex_GlrTxt.width()*.4f, mGR.mTex_GlrTxt.Height()*.4f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1))
		{
			mGR.mSel = 5;//redo
		}
		if(CircRectsOverlap( 15f,-30f, mGR.mTex_GlrTxt.width()*.4f, mGR.mTex_GlrTxt.Height()*.4f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1))
		{
			mGR.mSel = 6;//Object
		}
		
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			switch (mGR.mSel) {
			case 1:
				String extr = Environment.getExternalStorageDirectory().toString();
		        File myPath = new File(extr, mGR.path);
				if(myPath.exists())
		        {
					M.AppScreen = M.APPGALERY;
					if(!mGR.addFree){
						GameRenderer.mStart.show();
					}
						
		        }
				else {
					GameRenderer.mStart.dOyou();
				}
				break;
			case 2:
				screenshot = true;
				//Save
				break;
			case 3:
				shareImage();
				//Share
				break;
			case 4:
				M.AppScreen = M.APPOBJECT;
				if(mGR.mEdit.size()>1){
					mGR.mRedo.add(mGR.mEdit.get(mGR.mEdit.size()-1));
					mGR.mEdit.remove(mGR.mEdit.size()-1);
				}
				//Undo
				break;
			case 5:
				if(mGR.mRedo.size()>0){
					mGR.mEdit.add(mGR.mRedo.get(mGR.mRedo.size()-1));
					mGR.mRedo.remove(mGR.mRedo.size()-1);
				}
				//redo
				break;
			case 6:
				M.AppScreen = M.APPCLIPS;
				//object
				break;
			}
			mGR.mSel = 0;
		}
		return true;
	}
	
	void Draw_Gallery(GL10 gl)
	{
		DrawTextureS(gl, mGR.mTex_BG, 0, 0,1.1f);
		int k=0;
		for(int i=0;i<4;i++)
		{
			for(int j=0;j<3;j++)
			{
				DrawTexture(gl, mGR.mTex_ImgBox,move-15+j*15,32+-14*i);
				if(page*12+k<mGR.mGallary.size()){
					float dx = (10/mGR.mGallary.get(page*12+k).mTexture.width())*(mGR.mSel-1==(page*12+k)?1.1f:1);
					DrawTextureS(gl, mGR.mGallary.get(page*12+k).mTexture,move-15+j*15,32+-14*i,dx);
//					System.out.println(k+"   "+mGR.mGallary.get(page*12+k).mTexture.mBitmap.getWidth()+"   "+mGR.mGallary.get(page*12+k).mTexture.mBitmap.getHeight());
				}
				k++;
			}
		}
		k =0;
		if(move > 0 && page>0)
		{
			for(int i=0;i<4;i++)
			{
				for(int j=0;j<3;j++)
				{
					DrawTexture(gl, mGR.mTex_ImgBox,-50+move-15+j*15,32+-14*i);
					if((page-1)*12+k<mGR.mGallary.size())
						DrawTextureS(gl, mGR.mGallary.get((page-1)*12+k).mTexture,-50+move-15+j*15,32+-14*i,(M.ScreenWidth / mGR.mGallary.get(k).mTexture.width())*.01f);
					k++;
				}
			}
		}
		k =0;
		if(move < 0 && page<mGR.mGallary.size()/12){
			for(int i=0;i<4;i++)
			{
				for(int j=0;j<3;j++)
				{
					DrawTexture(gl, mGR.mTex_ImgBox,50+move-15+j*15,32+-14*i);
					if((page+1)*12+k<mGR.mGallary.size())
						DrawTextureS(gl, mGR.mGallary.get((page+1)*12+k).mTexture,50+move-15+j*15,32+-14*i,(M.ScreenWidth / mGR.mGallary.get(k).mTexture.width())*.01f);
					k++;
				}
			}
		}
		
		if(move!=0 && isUp)
		{
			if(move>0){
				move+=4;
				if(move > 45){
					move = 0;
					isUp = false;
					page --;
				}
			}else{
				move-=4;
				if(move <- 45){
					move = 0;
					isUp = false;
					page ++;
				}
			}
		}
		
		DrawTransScal(gl, mGR.mTex_GlrTxt	,-12,-21,mGR.mSel==5001?1.1f:1,mGR.mSel==5001?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Camera	,+12,-21,mGR.mSel==5002?1.1f:1,mGR.mSel==5002?0.5f:1);
		DrawTransScal(gl, mGR.mTex_About	,-12,-30,mGR.mSel==5003?1.1f:1,mGR.mSel==5003?0.5f:1);
		DrawTransScal(gl, mGR.mTex_OptTxt	, 12,-30,mGR.mSel==5004?1.1f:1,mGR.mSel==5004?0.5f:1);
		if(delCounter>0 && (Math.abs(move)<.8f)){
			delCounter++;
			if(delCounter ==25){
				try {handler2.sendEmptyMessage(0);} catch (Exception e) {}
			}
		}
	}
	private Handler handler2 = new Handler() {public void handleMessage(Message msg) {GameRenderer.mStart.Delete();}};//AdHouse
	int delCounter = 0;
	float change = 0;
	float move = 0;
	int glNo = 0;
	int page = 0;
	boolean isUp=false; 
	boolean Handle_Gallery(MotionEvent event)
	{
		mGR.mSel = 0;
		int k =0;
		for(int i=0;i<4;i++)
		{
			for(int j=0;j<3;j++)
			{
				
				if(page*12+k<mGR.mGallary.size())
				{
					if(CircRectsOverlap(move-15+j*15,32+-14*i, mGR.mTex_GlrTxt.width()*.4f, mGR.mTex_GlrTxt.Height()*.4f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1)){
						mGR.mSel = page*12+k+1;
						glNo = page*12+k;
					}
				}
				k++;
			}
		}
		
		if(CircRectsOverlap(-12,-21, mGR.mTex_GlrTxt.width()*.4f, mGR.mTex_GlrTxt.Height()*.4f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1))
		{
			mGR.mSel = 5001;
		}
		if(CircRectsOverlap(+12,-21, mGR.mTex_GlrTxt.width()*.4f, mGR.mTex_GlrTxt.Height()*.4f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1))
		{
			mGR.mSel = 5002;
		}
		if(CircRectsOverlap(-12,-30, mGR.mTex_GlrTxt.width()*.4f, mGR.mTex_GlrTxt.Height()*.4f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1))
		{
			mGR.mSel = 5003;
		}
		if(CircRectsOverlap(12,-30, mGR.mTex_GlrTxt.width()*.4f, mGR.mTex_GlrTxt.Height()*.4f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1))
		{
			mGR.mSel = 5004;
		}
		if(MotionEvent.ACTION_DOWN == event.getAction())
		{
			change = Uscreen2worldX(event.getX());
			if(mGR.mSel!=0)
				delCounter =1;
		}
		if(MotionEvent.ACTION_MOVE == event.getAction())
		{
			move = -change + Uscreen2worldX(event.getX());
			if(Math.abs(move)>.8f){
				delCounter = 0;
			}
			if((move > 0 && page==0)||(move < 0&&page==mGR.mGallary.size()/12)){
				move =0;
			}
		}
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			if(Math.abs(move)>2){
				isUp = true;
				return true;
			}
			if (mGR.mSel != 0) {
//				try {handler.sendEmptyMessage(0);} catch (Exception e) {}
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~Handle_Gallery~~~~~~~~~~~~~~~~~~~~~~~~");
				switch (mGR.mSel) {
				case 5001:
					Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
					photoPickerIntent.setType("image/*");
					GameRenderer.mStart.startActivityForResult(photoPickerIntent, 1);
					break;
				case 5002:
					GameRenderer.mStart.takePhoto();
					break;
				case 5003:
					M.AppScreen = M.APPABOUT;
					break;
				case 5004:
					M.AppScreen = M.APPOPTION;
					break;
				default:
					if (delCounter < 20 && Math.abs(-change + Uscreen2worldX(event.getX()))<.8f) {
						mGR.mEdit.clear();
						Edit tempe = new Edit(mGR.mGallary.get(mGR.mSel - 1).mTexture);
						tempe.lsy = tempe.lsx = 50/mGR.mGallary.get(mGR.mSel - 1).mTexture.width();
						mGR.mEdit.add(tempe);
						M.AppScreen = M.APPEDIT;
						mGR.action = 0;
						 mGR.path	= M.DIR+"/"+System.currentTimeMillis()+".jpg";
						 GameRenderer.mStart.load();
					}
					break;
				}
			}
			mGR.mSel = 0;
			delCounter =0;
		}
		return true;
	}
	float asx = 1;
	void Draw_Clips(GL10 gl)
	{
		DrawTextureS(gl, mGR.mTex_BG, 0, 0,1.1f);
		int k =0;
		if(asx>=1){
			for(int i=0;i<5;i++)
			{
				for(int j=0;j<4;j++)
				{
					DrawTransScal(gl, mGR.mTex_ImgBox	,-18+j*12,32-12*i,mGR.mSel-1==k?1.1f:1,mGR.mSel-1==k?.5f:1f);
					DrawTransScal(gl, mGR.mTex_Obj[mGR.clp*20+k]	,-18+j*12,32-12*i,mGR.mSel-1==k?.85f:.7f,mGR.mSel-1==k?.5f:1f);
					k++;
				}
			}
		}
		else
		{
			for(int i=0;i<5;i++)
			{
				for(int j=0;j<4;j++)
				{
					DrawTransScal(gl, mGR.mTex_ImgBox	,-18+j*12,32-12*i,asx,1f);
					DrawTransScal(gl, mGR.mTex_Obj[mGR.clp*20+k]	,-18+j*12,32-12*i,asx-.3f,1f);
					k++;
				}
			}
			asx*=1.5f;
		}
		if(mGR.clp != 0)
			DrawTextRS(gl, mGR.mTex_Next	,-10,-27, 0,mGR.mSel==5003?1.1f:1,mGR.mSel==5003?1.1f:1f);
		if(mGR.clp != 9)
			DrawTextRS(gl, mGR.mTex_Arrow	, 10,-27,  0,mGR.mSel==5004?1.1f:1,mGR.mSel==5004?1.1f:1f);
		
//		DrawTransScal(gl, mGR.mTex_GetMore	,-10,-35,mGR.mSel==5001?1.1f:1,mGR.mSel==5001?.5f:1f);
//		DrawTransScal(gl, mGR.mTex_htt		, 13,-35,mGR.mSel==5002?1.1f:1,mGR.mSel==5002?.5f:1f);
	}
	
	boolean Handle_Clips(MotionEvent event)
	{
		mGR.mSel = 0;
		int k =0;
		for(int i=0;i<5;i++)
		{
			for(int j=0;j<4;j++)
			{
				if(CircRectsOverlap(-18+j*12,32-12*i, mGR.mTex_ImgBox.width()*.4f, mGR.mTex_ImgBox.Height()*.4f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1))
				{
					mGR.mSel = k+1;//Back
				}
				k++;
			}
		}
		
//		if(CircRectsOverlap(-10,-35, mGR.mTex_GetMore.width()*.4f, mGR.mTex_GetMore.Height()*.4f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1))
//		{
//			mGR.mSel = 5001;//GetMore
//		}
//		if(CircRectsOverlap(13,-35, mGR.mTex_htt.width()*.4f, mGR.mTex_htt.Height()*.4f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1))
//		{
//			mGR.mSel = 5002;//Hututu
//		}
		if(CircRectsOverlap(-10,-27, mGR.mTex_Arrow.width()*.4f, mGR.mTex_GetMore.Height()*.4f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1))
		{
			mGR.mSel = 5003;//Left
		}
		if(CircRectsOverlap( 10,-27, mGR.mTex_Arrow.width()*.4f, mGR.mTex_htt.Height()*.4f, Uscreen2worldX(event.getX()), Uscreen2worldY(event.getY()), 1))
		{
			mGR.mSel = 5004;//Right
		}
		
		if(MotionEvent.ACTION_UP == event.getAction())
		{
			
			if(mGR.mSel>0 && mGR.mSel < 21)
			{
				Edit tempe	= new Edit(mGR.mTex_Obj[(mGR.clp*20)+(mGR.mSel-1)]);
				mGR.mEdit.add(tempe);
				M.AppScreen = M.APPEDIT;
				mGR.action = 0;
			}
//			if(mGR.mSel==5001)
//			{
//				MoreGame();
//			}
//			if(mGR.mSel==5002)
//			{
//				MoreGame();
//			}
			if(mGR.mSel==5003)
			{
				if(mGR.clp > 0){
					asx = .1f;
					mGR.clp--;
				}
			}
			if(mGR.mSel==5004)
			{
				if(mGR.clp<9){
					asx = .1f;
					mGR.clp++;
				}
			}
			mGR.mSel = 0;
		}
		return true;
	}
	
	
	void DrawTexture(GL10 gl,SimplePlane Tex,float x,float y)
	{
		Tex.drawPos(gl, x, y);
	}
	
	void DrawTextRS(GL10 gl,SimplePlane Tex,float x,float y,float angle,float sclx,float scly)
	{
		Tex.drawRotet(gl, x, y,angle,sclx,scly);
	}
	void DrawTextureS(GL10 gl,SimplePlane Tex,float x,float y,float scal)
	{
		Tex.drawScal(gl, x, y,scal,scal);
	}
	void DrawFlip(GL10 gl,SimplePlane Tex,float x,float y)
	{
		Tex.drawFilp(gl, x, y);
	}
	void DrawTransScal(GL10 gl,SimplePlane Tex,float x,float y, float z,float t)
	{
		Tex.drawTransprentScal(gl, x, y, z, t);
	}
	boolean CircRectsOverlap(double CRX,  double CRY,double CRDX,double CRDY ,double centerX,double centerY, double radius)
    {
        if ((Math.abs(centerX - CRX) <= (CRDX + radius)) && (Math.abs(centerY - CRY) <= (CRDY + radius)))
           return true;
        return false ;

    }
	float Uscreen2worldX(float a)
	{
		float c = ( a-(M.ScreenWidth/2))*(82/M.ScreenHieght);
		return c;
	}
	float Uscreen2worldY(float a)
	{
		//float c = ((a / M.ScreenHieght)- 0.5f)*(-41);
		float c = ((M.ScreenHieght/2)- a)*(82/M.ScreenHieght);
		
		return c;
	}
	float screen2worldX(float a)
	{
		float c = ((a / M.ScreenWidth)- 0.5f)*2;
		return c;
	}
	float screen2worldY(float a)
	{
		float c = ((a / M.ScreenHieght)- 0.5f)*(-2);
		return c;
	}
	boolean Rect2RectIntersection(float ax,float ay,float adx,float ady,float bx,float by,float bdx,float bdy)
	{
		ax -= adx/2;
		ay += ady/2;
		bx -= bdx/2;
		by += bdy/2;
		if( ax+adx > bx  && ay-ady < by && bx+bdx > ax && by-bdy< ay)
		{
			return true;
		}
		return false;
	}
	boolean CirCir(double cx1,double cy1, double r1,double cx2,double cy2, double r2)
    {
		float bVectMag = (float) Math.sqrt(((cx1-cx2)*(cx1-cx2)) + ((cy1-cy2)*(cy1-cy2)));
		if (bVectMag<(r1+r2))
           return true;
        return false ;

    }
	double GetAngle(double d,double e)
	{
	  if(d==0)
		  return e>=0 ? Math.PI/2 : -Math.PI/2;
	  else if (d > 0)
		  return Math.atan(e/d);
	  else
		  return Math.atan(e/d) + Math.PI;
	}
	void drawNumber(GL10 gl,int no,float x, float y)
	{
//		float dx = mGR.mTex_Font[0].width();
//		 String strs = ""+no;
//		for(int i =0;i<strs.length();i++)
//		{
//			int k = ((int)strs.charAt(i))-48;
//			if(k>=0 && k<mGR.mTex_Font.length)
//				mGR.mTex_Font[k].drawPos(gl,x+i*dx,y);
//		}
	}
	void RateUs()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.LINK+getClass().getPackage().getName()));
	    GameRenderer.mContext.startActivity(mIntent);
	}
	void MoreGame()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.MARKET));
		GameRenderer.mContext.startActivity(mIntent);
	}
	void facebook()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://www.facebook.com/HututuGames"));
		GameRenderer.mContext.startActivity(mIntent);
	}
	void google()
	{
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://plus.google.com/+Hututugames"));
		GameRenderer.mContext.startActivity(mIntent);
	}
	void share2friend()
	{
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("text/plain");
		i.putExtra(Intent.EXTRA_SUBJECT,"Roking new Game '"+GameRenderer.mContext.getResources().getString(R.string.app_name)+"'");
		//i.putExtra(Intent.EXTRA_TEXT,"Let the battle commence!!! Download it now and let’s ROCK!!!!  "+M.PUBLICLINK+getClass().getPackage().getName());
		i.putExtra(Intent.EXTRA_TEXT,"Let the battle commence!!! Download it now and let’s ROCK!!!!  "+M.SHARELINK);
		try {
		    GameRenderer.mContext.startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(GameRenderer.mStart, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}

	void shareImage() {
		String extr = Environment.getExternalStorageDirectory().toString();
		File myPath = new File(extr, mGR.path);
		if (!myPath.exists()) {
			GameRenderer.mStart.SaveMassage();
		}
		String str = Environment.getExternalStorageDirectory() + mGR.path;
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(str)));
		shareIntent.setType("image/png");
		GameRenderer.mContext.startActivity(Intent.createChooser(shareIntent, "Where?"));
	}
	
	
	void SaveScreen(GL10 gl){
		if(!screenshot)
			return;
        Bitmap bb = getNew(gl);
        Bitmap b = ImageCut(bb);
        String extr = Environment.getExternalStorageDirectory().toString();
        
        File folder = new File(Environment.getExternalStorageDirectory() + M.DIR);
        if(!folder.exists())
        {
            folder.mkdir();
        }  
        
        File myPath = new File(extr, mGR.path);
        if(myPath.exists())
        {
        	if(mGR.mGallary.size()>0)
        		mGR.mGallary.remove(mGR.mGallary.size()-1);
        	myPath.delete();
        }
        Gallary gallary = new Gallary(mGR.addBitmap(b), extr+ mGR.path);
        mGR.mGallary.add(gallary);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            b.compress(Bitmap.CompressFormat.JPEG, 85, fos);
            fos.flush();
            fos.close();
            MediaStore.Images.Media.insertImage(GameRenderer.mStart.getContentResolver(), b, "Screen", "screen");
        }catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        GameRenderer.mStart.finish();
    }
    boolean screenshot = false;
    Bitmap getNew(GL10 gl)
    {
    	int width = (int)M.ScreenWidth;
    	int height = (int)M.ScreenHieght;
    	int screenshotSize = width * height;
    	ByteBuffer bb = ByteBuffer.allocateDirect(screenshotSize * 4);
    	bb.order(ByteOrder.nativeOrder());
    	gl.glReadPixels(0, 0, width, height, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, bb);
    	int pixelsBuffer[] = new int[screenshotSize];
    	bb.asIntBuffer().get(pixelsBuffer);
    	bb = null;
    	Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
    	bitmap.setPixels(pixelsBuffer, screenshotSize-width, -width, 0, 0, width, height);
    	pixelsBuffer = null;
    	short sBuffer[] = new short[screenshotSize];
    	ShortBuffer sb = ShortBuffer.wrap(sBuffer);
    	bitmap.copyPixelsToBuffer(sb);
    	for (int i = 0; i < screenshotSize; ++i) {                  
    		short v = sBuffer[i];
    		sBuffer[i] = (short) (((v&0x1f) << 11) | (v&0x7e0) | ((v&0xf800) >> 11));
        }
        sb.rewind();
        bitmap.copyPixelsFromBuffer(sb);
        screenshot = false;
    	return bitmap;
    }
    Bitmap ImageCut(Bitmap bitmap)
    {
    	int x =(int)(-M.ScreenWidth	 *.06);
    	int y =(int)(-M.ScreenHieght*.21);
    	int dx=(int)(M.ScreenWidth  *.88);
    	int dy=(int)(M.ScreenHieght *.58);
    	
    	
    	Bitmap image = Bitmap.createBitmap(dx,dy, Bitmap.Config.ARGB_8888);
//        image.eraseColor(Color.TRANSPARENT);
        Canvas canvas = new Canvas(image);
//        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawBitmap(bitmap, x, y, null);
        return image;
    }
    
}
