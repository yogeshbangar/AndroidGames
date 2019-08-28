package com.httgames.app.stylishframestudio;


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
import android.graphics.PorterDuff.Mode;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.MotionEventCompat;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
public class Group extends Mesh {
	ZRenderer mGR = null;
	int Sel =0;
	int forSave = 0;
	int Counter =0;
	public Group(ZRenderer _GameRenderer)
	{
		mGR = _GameRenderer;
	}
	private void Draw_Menu(GL10 gl) {
		DrawTexture(gl, mGR.mTex_Splash, 0, 0);
		DrawTransScal(gl, mGR.mTex_Start, 0.40f, -.42f, Sel == 1 ? 1.1f : 1,Sel == 1 ? 0.5f : 1);
		for (int i = 0; i < mGR.mTex_Spl.length; i++)
			DrawTransScal(gl, mGR.mTex_Spl[i], -.46f + .23f * i, -.71f,Sel == 2 + i ? 1.1f : 1, Sel == 2 + i ? 0.5f : 1);

	}
	private boolean Handle_Menu(MotionEvent event){
		Sel = 0;
		if(CircRectsOverlap(0.40f, -.42f, mGR.mTex_Efect.width()*.4f, mGR.mTex_Efect.Height()*.3f, s2wX(event.getX()), s2wY(event.getY()), .01f)){
			Sel =1;
		}
		for(int i =0;i<5;i++){
			if(CircRectsOverlap(-.46f + .23f*i, -.687f, mGR.mTex_Efect.width()*.2f, mGR.mTex_Efect.Height()*.3f, s2wX(event.getX()), s2wY(event.getY()), .01f)){
				Sel =2+i;
			}
		}
		if(MotionEvent.ACTION_UP == event.getAction() && Sel > 0){
			switch (Sel) {
			case 1:
				ZRenderer.mStart.ShowGPlus(View.GONE);//GameRenderer.mStart.mPlusOneButton.setVisibility(View.GONE);
				M.AppScreen = M.APPFRAME;
				break;
			case 2:
				ZRenderer.mStart.openFolder();
				break;
			case 3:
				MoreGame();
				break;
			case 4:
				share2friend();
				break;
			case 5:
				facebook();
				break;
			case 6:
				RateUs();
				break;
			}
			Sel = 0;
		}
		return true;
	}
	private float rgb[] = {.35f,.35f,.35f};
	private void Draw_Play(GL10 gl){
		
		DrawTexture(gl, mGR.mTex_BG, 0, 0);
		
		DrawTransScal(gl, mGR.mTex_Mod[mGR.mMode==0?0:1], -.49f, .89f,Sel == 15 ?1.1f:1,Sel == 15 ?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Mod[mGR.mMode==0?3:2], -.20f, .89f,Sel == 16 ?1.1f:1,Sel == 16 ?0.5f:1);
		
		if(mGR.mMode == 1 && mGR.mGellary.size()>0)
			DrawTransScal(gl, mGR.mTex_Undo, 0.12f, .89f,forSave == 19 ?1.1f:1,forSave == 19 ?0.5f:1);
		DrawTransScal(gl, mGR.mTex_ShareBtn, 0.30f, .89f,forSave == 17 ?1.1f:1,forSave == 17 ?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Save , 0.48f, .89f,forSave == 18 ?1.1f:1,forSave == 18 ?0.5f:1);
		for(int i =0;i<mGR.mSkeleton.size();i++){
			gl.glEnable(GL10.GL_SCISSOR_TEST);
			gl.glScissor((int)(mGR.mSkeleton.get(i).rectX),(int)(mGR.mSkeleton.get(i).rectY),(int)mGR.mSkeleton.get(i).rectDx,(int)mGR.mSkeleton.get(i).rectDy);
			if(mGR.mSkeleton.get(i).mTexture!=null){
				mGR.mSkeleton.get(i).mTexture.drawAll(gl, mGR.mSkeleton.get(i).x, mGR.mSkeleton.get(i).y, 
						mGR.mSkeleton.get(i).ang + mGR.mSkeleton.get(i).langle,
						mGR.mSkeleton.get(i).lsx * mGR.mSkeleton.get(i).sx,
						mGR.mSkeleton.get(i).lsy * mGR.mSkeleton.get(i).sy,
						mGR.mSkeleton.get(i).rgb);
			}else{
				DrawTexture(gl, mGR.mTex_Plus, mGR.mSkeleton.get(i).x, mGR.mSkeleton.get(i).y);
			}
			gl.glDisable(GL10.GL_SCISSOR_TEST);	
		}
		for(int i =0;i<mGR.mTex_Frame.size();i++){
			mGR.mTex_Frame.get(i).mTex.drawAll(gl, mGR.mTex_Frame.get(i).x, mGR.mTex_Frame.get(i).y, 
					0,1,1,mGR.mTex_Frame.get(i).rgb);
			
		}
		for(int i =0;i<mGR.mGellary.size();i++){
			if(mGR.mGellary.get(i).mTexture!=null){
				mGR.mGellary.get(i).mTexture.drawAll(gl, mGR.mGellary.get(i).movx, mGR.mGellary.get(i).movy, 
						mGR.mGellary.get(i).Eangle + mGR.mGellary.get(i).Elangle,
						mGR.mGellary.get(i).lex * mGR.mGellary.get(i).ex,
						mGR.mGellary.get(i).ley * mGR.mGellary.get(i).ey,
						mGR.mGellary.get(i).Ergb);
			}
		}
		
		if(mGR.action == 4)
		{
			if(mGR.mMode == 0){
				for(int i=0;i<3;i++){
					rgb[0] = rgb[1] =rgb[2]= -.4f;
					rgb[i] = .4f;
					mGR.mTex_RGBBar.drawAll(gl, 0, -.35f-.08f*i, 0, 11, 1.2f, rgb);
					rgb[i] = mGR.mSkeleton.get(mGR.mImgSel).rgb[i];
					mGR.mTex_RGBBar.drawAll(gl, 0, -.35f-.08f*i, 0, 11, 0.8f, rgb);
					DrawTexture(gl, mGR.mTex_RGBSel, mGR.mSkeleton.get(mGR.mImgSel).rgb[i], -.35f-.08f*i);
	
				}
			}else if(mGR.mGellary.size()>0){
				for(int i=0;i<3;i++){
					rgb[0] = rgb[1] =rgb[2]= -.4f;
					rgb[i] = .4f;
					mGR.mTex_RGBBar.drawAll(gl, 0, -.35f-.08f*i, 0, 11, 1.2f, rgb);
					rgb[i] = mGR.mGellary.get(mGR.mGellary.size()-1).Ergb[i];
					mGR.mTex_RGBBar.drawAll(gl, 0, -.35f-.08f*i, 0, 11, 0.8f, rgb);
					DrawTexture(gl, mGR.mTex_RGBSel, mGR.mGellary.get(mGR.mGellary.size()-1).Ergb[i], -.35f-.08f*i);
				}
			}
		}
		if (mGR.action == 5 && mGR.mTex_Frame.size() > 0)
		{
			for(int i=0;i<3;i++){
				rgb[0] = rgb[1] =rgb[2]= -.4f;
				rgb[i] = .4f;
				mGR.mTex_RGBBar.drawAll(gl, 0, -.35f-.08f*i, 0, 11, 1.2f, rgb);
				rgb[i] = mGR.mTex_Frame.get(0).rgb[i];
				mGR.mTex_RGBBar.drawAll(gl, 0, -.35f-.08f*i, 0, 11, 0.8f, rgb);
				DrawTexture(gl, mGR.mTex_RGBSel, mGR.mTex_Frame.get(0).rgb[i], -.35f-.08f*i);

			}
		}
		
		{
			DrawTransScal(gl, mGR.mTex_Efect	, -.48f+.162f*0, -.72f,mGR.action == 4?1.1f:1,mGR.action == 4 ?0.5f:1);
			DrawTransScal(gl, mGR.mTex_ClrBtn	, -.48f+.162f*1, -.72f,mGR.action == 5?1.1f:1,mGR.action == 5 ?0.5f:1);
			DrawTransScal(gl, mGR.mTex_Zoom		, -.48f+.162f*2, -.72f,mGR.action == 0 ?1.1f:1,mGR.action == 0 ?0.5f:1);
			DrawTransScal(gl, mGR.mTex_RotBtn	, -.48f+.162f*3, -.72f,mGR.action == 3 ?1.1f:1,mGR.action == 3 ?0.5f:1);
			DrawTransScal(gl, mGR.mTex_flipH	, -.48f+.162f*4, -.72f,Sel == 10?1.1f:1,Sel == 10?0.5f:1);
			DrawTransScal(gl, mGR.mTex_FlipV	, -.48f+.162f*5, -.72f,Sel == 11?1.1f:1,Sel == 11?0.5f:1);
			DrawTransScal(gl, mGR.mTex_More		, -.48f+.162f*6, -.72f,mGR.action == 6?1.1f:1,mGR.action == 6?0.5f:1);
			switch (mGR.action) {
			case 0:
				for(int i =0;i<6;i++){
					DrawTransScal(gl, mGR.mTex_Zom[i]	, -.47f+.188f*i, -.46f,Sel == 20+i ?1.1f:1,Sel == 20+i ?0.5f:1);
				}
				switch (Sel) {
				case 20:
					if (mGR.mMode == 0) {
						mGR.mSkeleton.get(mGR.mImgSel).lsx += .01f;
						mGR.mSkeleton.get(mGR.mImgSel).lsy += .01f;
					} else if (mGR.mGellary.size() > 0) {
						mGR.mGellary.get(mGR.mGellary.size() - 1).lex += .01f;
						mGR.mGellary.get(mGR.mGellary.size() - 1).ley += .01f;
					}
					break;
				case 21:
					if (mGR.mMode == 0) {
						mGR.mSkeleton.get(mGR.mImgSel).lsx -= .01f;
						mGR.mSkeleton.get(mGR.mImgSel).lsy -= .01f;
					} else if (mGR.mGellary.size() > 0) {
						mGR.mGellary.get(mGR.mGellary.size() - 1).lex -= .01f;
						mGR.mGellary.get(mGR.mGellary.size() - 1).ley -= .01f;
					}
					break;
				case 22:
					if (mGR.mMode == 0) {
						mGR.mSkeleton.get(mGR.mImgSel).lsx += .01f;
					} else if (mGR.mGellary.size() > 0) {
						mGR.mGellary.get(mGR.mGellary.size() - 1).lex += .01f;
					}
					break;
				case 23:
					if (mGR.mMode == 0) {
						mGR.mSkeleton.get(mGR.mImgSel).lsx -= .01f;
					} else if (mGR.mGellary.size() > 0) {
						mGR.mGellary.get(mGR.mGellary.size() - 1).lex -= .01f;
					}
					break;
				case 24:
					if (mGR.mMode == 0) {
						mGR.mSkeleton.get(mGR.mImgSel).lsy += .01f;
					} else if (mGR.mGellary.size() > 0) {
						mGR.mGellary.get(mGR.mGellary.size() - 1).ley += .01f;
					}
					break;
				case 25:
					if (mGR.mMode == 0) {
						mGR.mSkeleton.get(mGR.mImgSel).lsy -= .01f;
					} else if (mGR.mGellary.size() > 0) {
						mGR.mGellary.get(mGR.mGellary.size() - 1).ley -= .01f;
					}
					break;
				}
				break;
			case 3:
				for(int i =0;i<4;i++){
					DrawTransScal(gl, mGR.mTex_Rot[i], -.47f+.188f*i, -.46f,Sel == 30+i ?1.1f:1,Sel == 30+i ?0.5f:1);
				}
				switch (Sel) {
				case 30:
					if (mGR.mMode == 0) {
						mGR.mSkeleton.get(mGR.mImgSel).ang += 1f;
					} else if (mGR.mGellary.size() > 0) {
						mGR.mGellary.get(mGR.mGellary.size() - 1).Eangle += 1f;
					}
					break;
				case 31:
					if (mGR.mMode == 0) {
						mGR.mSkeleton.get(mGR.mImgSel).ang -= 1f;
					} else if (mGR.mGellary.size() > 0) {
						mGR.mGellary.get(mGR.mGellary.size() - 1).Eangle -= 1f;
					}
					break;
				}
				break;
			case 6:
				DrawTransScal(gl, mGR.mTex_Cliprt	, 0.47f-.188f*0, -.46f,Sel == 60+0 ?1.1f:1,Sel == 60+0 ?0.5f:1);
				DrawTransScal(gl, mGR.mTex_Text	, 0.47f-.188f*1, -.46f,Sel == 60+1 ?1.1f:1,Sel == 60+1 ?0.5f:1);
				break;
			}
		}
		
		
		if(mGR.showPopup)
		{
			mGR.mTex_BG.drawDark(gl, 0, 0);
//			newset(gl);
			DrawTexture(gl, mGR.mTex_Popup	, 0, 0);
			DrawTransScal(gl, mGR.mTex_Cros	, .506f, 0.196f,Sel == 70 ?1.1f:1,Sel == 70 ?0.5f:1);
			DrawTransScal(gl, mGR.mTex_Cam	,-.270f, -.080f,Sel == 71 ?1.1f:1,Sel == 71 ?0.5f:1);
			DrawTransScal(gl, mGR.mTex_File		, .270f, -.080f,Sel == 72 ?1.1f:1,Sel == 72 ?0.5f:1);
		}
		if(screenshot){
			SaveScreen(gl);
		}
	}
	public void openFolder()
	{
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath());
		intent.setDataAndType(uri, "image/*");
		ZRenderer.mStart.startActivityForResult(intent, 1);// Gallery
//		GameRenderer.mStart.startActivityForResult(Intent.createChooser(intent, "Open folder"));
	}
	private boolean Handle_Play(MotionEvent event){
		Sel =0;
		if(mGR.showPopup && (MotionEvent.ACTION_DOWN == event.getAction())){
			
			if(CircRectsOverlap(.506f, 0.196f, mGR.mTex_Efect.width()*.2f, mGR.mTex_Efect.Height()*.3f, s2wX(event.getX()), s2wY(event.getY()), .01f)){
				Sel = 70;
				mGR.showPopup = false;
			}
			if(CircRectsOverlap(-.270f, -.080f, mGR.mTex_Efect.width()*.2f, mGR.mTex_Efect.Height()*.3f, s2wX(event.getX()), s2wY(event.getY()), .01f)){
				Sel = 71;	
				mGR.showPopup = false;
				ZRenderer.mStart.takePhoto();// Camera
			}
			if(CircRectsOverlap(0.270f, -.080f, mGR.mTex_Efect.width()*.2f, mGR.mTex_Efect.Height()*.3f, s2wX(event.getX()), s2wY(event.getY()), .01f)){
				Sel = 72;
				mGR.showPopup = false;
				/*Intent photoPickerIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				photoPickerIntent.setType("image/*");
				GameRenderer.mStart.startActivityForResult(photoPickerIntent, 1);// Gallery
*/			openFolder();
				
			}
			if(MotionEvent.ACTION_UP == event.getAction() && Sel >0){
				switch (Sel) {
				case 70:
//					mGR.showPopup = false;
					break;
				case 71:
//					GameRenderer.mStart.takePhoto();// Camera
					break;
				case 72:
//					Intent photoPickerIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//					photoPickerIntent.setType("image/*");
//					GameRenderer.mStart.startActivityForResult(photoPickerIntent, 1);// Gallery
					break;
				}
				Sel =0;
			}
			return true;
		}
		if(MotionEvent.ACTION_DOWN == event.getAction()){
			Action = false;
			for(int i =0;i<mGR.mSkeleton.size();i++){
				if(CircRectsOverlap(
						(Draft.Rectangle[mGR.mFramPage][mGR.mFramImg][i][0]/1000f)+(Draft.Rectangle[mGR.mFramPage][mGR.mFramImg][i][2]/1000f),
					  -((Draft.Rectangle[mGR.mFramPage][mGR.mFramImg][i][1]/1000f)-(Draft.Rectangle[mGR.mFramPage][mGR.mFramImg][i][3]/1000f)),
					  (Draft.Rectangle[mGR.mFramPage][mGR.mFramImg][i][2]/1000f), 
					  (Draft.Rectangle[mGR.mFramPage][mGR.mFramImg][i][3]/1000f), 
					  s2wX(event.getX()), 
					  s2wY(event.getY()), .01f)){
					mGR.mImgSel =i;
					if(mGR.mMode == 0&& mGR.mSkeleton.get(mGR.mImgSel).mTexture == null){
						mGR.showPopup = true;
						return true;
					}else{
						Action = true;
					}
					
					
					
				}
			}
			{
				
				switch (mGR.action) {
				case 0:
					for(int i =0;i<6;i++){
						if(CircRectsOverlap(-.47f+.188f*i, -.46f, mGR.mTex_Efect.width()*.2f, mGR.mTex_Efect.Height()*.3f, s2wX(event.getX()), s2wY(event.getY()), .01f)){
							Sel =20+i;
					}
					}
					break;
				case 3:
					for(int i =0;i<4;i++){
						if(CircRectsOverlap(-.47f+.188f*i, -.46f, mGR.mTex_Efect.width()*.2f, mGR.mTex_Efect.Height()*.3f, s2wX(event.getX()), s2wY(event.getY()), .01f)){
							Sel =30+i;
							if (Sel == 32) {

								if (mGR.mMode == 0) {
									mGR.mSkeleton.get(mGR.mImgSel).ang += 90f;
								} else if (mGR.mGellary.size() > 0) {
									mGR.mGellary.get(mGR.mGellary.size() - 1).Eangle += 90f;
								}
							}
							if (Sel == 33) {
								if (mGR.mMode == 0) {
									mGR.mSkeleton.get(mGR.mImgSel).ang -= 90f;
								} else if (mGR.mGellary.size() > 0) {
									mGR.mGellary.get(mGR.mGellary.size() - 1).Eangle -= 90f;
								}
							}
						}
					}
					break;
				case 4:
					rgbsel = -1;
					if(mGR.mMode == 0) {
						for (int i = 0; i < 3; i++){
							if (CircRectsOverlap(mGR.mSkeleton.get(mGR.mImgSel).rgb[i], -.35f-.08f*i,mGR.mTex_RGBSel.width() * 1.0f,mGR.mTex_RGBSel.Height() * 0.4f,
									s2wX(event.getX()),s2wY(event.getY()), .01)) {
								rgbsel = i;
								Action = true;
							}
						}
					}else if(mGR.mGellary.size() > 0){
						for (int i = 0; i < 3; i++){
							int work = mGR.mGellary.size()-1;
							if (CircRectsOverlap(mGR.mGellary.get(work).Ergb[i], -.35f-.08f*i,mGR.mTex_RGBSel.width() * 1.0f,mGR.mTex_RGBSel.Height() * 0.4f,
									s2wX(event.getX()),s2wY(event.getY()), .01)) {
								rgbsel = i;
								Action = true;
							}
						}
					}
					break;
				case 5:
					rgbsel = -1;
					 {
						for (int i = 0; i < 3; i++){
							if (CircRectsOverlap(mGR.mTex_Frame.get(0).rgb[i], -.35f-.08f*i,mGR.mTex_RGBSel.width() * 1.0f,mGR.mTex_RGBSel.Height() * 0.4f,
									s2wX(event.getX()),s2wY(event.getY()), .01)) {
								rgbsel = i;
								Action = true;
							}
						}
					}
					break;
				case 6:
					for(int i =0;i<2;i++){
						if(CircRectsOverlap(0.47f-.188f*i, -.46f, mGR.mTex_Efect.width()*.2f, mGR.mTex_Efect.Height()*.3f, s2wX(event.getX()), s2wY(event.getY()), .01f)){
							Sel =60+i;
							forSave = 60;
							mGR.action =0;
							if(Sel == 61){
								M.AppScreen = M.ADDTEXT;
							}
						}
					}
					break;
				}
				for(int i =0;i<7;i++){
					if(CircRectsOverlap(-.48f+.162f*i,-.72f, mGR.mTex_Efect.width()*.2f, mGR.mTex_Efect.Height()*.3f, s2wX(event.getX()), s2wY(event.getY()), .01f)){
						Sel =6+i;
						switch (Sel) {
						case 6:
							mGR.action = 4;
							break;
						case 7:
							mGR.action = 5;
							break;
						case 8:
							mGR.action = 0;
							break;
						case 9:
							mGR.action = 3;
							break;
						case 10:
							if(mGR.mSkeleton.get(mGR.mImgSel).mTexture!=null && mGR.mMode == 0){
								mGR.mSkeleton.get(mGR.mImgSel).mTexture.loadBitmap(mGR.FlipHorizontal(mGR.mSkeleton.get(mGR.mImgSel).mTexture.getBitmap()));
							}else if(mGR.mGellary.size()>0 && mGR.mMode == 1){
								mGR.mGellary.get(mGR.mGellary.size()-1).mTexture.loadBitmap(mGR.FlipHorizontal(mGR.mGellary.get(mGR.mGellary.size()-1).mTexture.getBitmap()));
							}
							break;
						case 11:
							if(mGR.mSkeleton.get(mGR.mImgSel).mTexture!=null && mGR.mMode == 0){
								mGR.mSkeleton.get(mGR.mImgSel).mTexture.loadBitmap(mGR.FlipVerticle(mGR.mSkeleton.get(mGR.mImgSel).mTexture.getBitmap()));
							}else if(mGR.mGellary.size()>0 && mGR.mMode == 1){
								mGR.mGellary.get(mGR.mGellary.size()-1).mTexture.loadBitmap(mGR.FlipVerticle(mGR.mGellary.get(mGR.mGellary.size()-1).mTexture.getBitmap()));
							}
							break;
						case 12:
							mGR.action = 6;
							break;
						}
					}
				}
			}
			if(CircRectsOverlap(-.49f, .89f, mGR.mTex_Efect.width()*.2f, mGR.mTex_Efect.Height()*.3f, s2wX(event.getX()), s2wY(event.getY()), .01f)){
				Sel =15;
				mGR.mMode = 0;
			}
			if(CircRectsOverlap(-.20f, .89f, mGR.mTex_Efect.width()*.2f, mGR.mTex_Efect.Height()*.3f, s2wX(event.getX()), s2wY(event.getY()), .01f)){
				Sel =16;
				mGR.mMode = 1;
			}
			if(CircRectsOverlap(0.30f, .89f, mGR.mTex_Efect.width()*.2f, mGR.mTex_Efect.Height()*.3f, s2wX(event.getX()), s2wY(event.getY()), .01f)){
				
				forSave = 17;
				
			}
			if(CircRectsOverlap(0.48f, .89f, mGR.mTex_Efect.width()*.2f, mGR.mTex_Efect.Height()*.3f, s2wX(event.getX()), s2wY(event.getY()), .01f)){
				forSave = 18;
			}
			if(CircRectsOverlap(0.12f, .89f, mGR.mTex_Efect.width()*.2f, mGR.mTex_Efect.Height()*.3f, s2wX(event.getX()), s2wY(event.getY()), .01f)){
				forSave = 19;
			}
		}
		if(mGR.mSkeleton.size()>0 && Action)
			if(mGR.mMode == 0){
				TouchMove(event);
			}else{
				TouchEdit(event);
			}
		
		
		
		if(MotionEvent.ACTION_UP ==event.getAction()){
			{
				if(forSave == 17)
				{
					if(!screenshot){
						mGR.isShare =true;
						screenshot = true;
					}
				}
				
			}
			{
				if(forSave == 18)
				{
					if(!screenshot){
						screenshot = true;
					}
					
				}
			}
			{
				if(forSave == 19 && mGR.mMode == 1 && mGR.mGellary.size()>0){
					mGR.mGellary.remove(mGR.mGellary.size()-1);
				}
			}
			if(forSave == 60)
			{
				M.AppScreen = M.APPMAIN;
			}
			forSave =0;
			Sel = 0;
			
		}
		
		return true;
	}
	private void Draw_Frame(GL10 gl) {
		DrawTexture(gl, mGR.mTex_BG, 0, 0);
		DrawTransScal(gl, mGR.mTex_BackBtn , -.48f, .89f,Sel == 100 ?1.1f:1,Sel == 100 ?0.5f:1);
		for (int i = 0; i < 12 && i < mGR.mTex_Grid[mGR.mFramPage].length; i++) {
			
			DrawTransScal(gl, mGR.mTex_SubICN,move-.36f+.36f*(i%3), .60f-.35f*(i/3),
					Sel==(i+1)?1.1f:1.0f,Sel==(i+1)?0.5f:1);
			DrawTransScal(gl, mGR.mTex_Grid[mGR.mFramPage][i],move-.36f+.36f*(i%3), .60f-.35f*(i/3),
					Sel==(i+1)?1.8f:1.6f,Sel==(i+1)?0.5f:1);
		}
		
		if (move != 0 || movevx !=0) {
			int nPage = 0;
			move += movevx;
			if (move > 0 ){
				nPage = mGR.mFramPage-1;
			}
			if (move < 0 ){
				nPage = mGR.mFramPage+1;
			}
			if (nPage >= 0 && nPage < mGR.mTex_Grid.length) {
				for (int i = 0; i < 12 && i < mGR.mTex_Grid[nPage].length; i++) {
					DrawTransScal(gl, mGR.mTex_SubICN,move-.36f+.36f*(i%3)+(move < 0 ? 1.3f:-1.3f), .60f-.35f*(i/3),
							Sel==(i+1)?1.8f:1.6f,Sel==(i+1)?0.5f:1);
					DrawTransScal(gl, mGR.mTex_Grid[nPage][i],move-.36f+.36f*(i%3)+(move < 0 ? 1.3f:-1.3f), .60f-.35f*(i/3),
							Sel==(i+1)?1.8f:1.6f,Sel==(i+1)?0.5f:1);
				}
			}
			if (movevx > 0 && move > 1) {
				movevx = move = 0;
				mGR.mFramPage--;
			}
			if (movevx < 0 && move < -1) {
				movevx = move = 0;
				mGR.mFramPage++;
			}
		}
		if (mGR.mFramPage > 0)
			DrawTransScal(gl, mGR.mTex_Arw[0],-.3f, -.72f,Sel==(101)?1.1f:1,Sel==(101)?0.5f:1);
		
		if (mGR.mFramPage < mGR.mTex_Grid.length-1)
			DrawTransScal(gl, mGR.mTex_Arw[1],0.3f, -.72f,Sel==(102)?1.1f:1,Sel==(102)?0.5f:1);
	}
	private boolean Handle_Frame(MotionEvent event){
		Sel =0;
		for (int i = 0; i < 12 && i < mGR.mTex_Grid[mGR.mFramPage].length; i++) {
			if(CircRectsOverlap(move-.36f+.36f*(i%3), .60f-.35f*(i/3), mGR.mTex_SubICN.width()*.3f, mGR.mTex_SubICN.Height()*.2f, s2wX(event.getX()), s2wY(event.getY()), .01f)){
				Sel = i+1;
			}
		}
		if(CircRectsOverlap(-.48f, .89f, mGR.mTex_Efect.width()*.2f, mGR.mTex_Efect.Height()*.3f, s2wX(event.getX()), s2wY(event.getY()), .01f)){
			Sel =100;
		}
		if(CircRectsOverlap(-.3f,-.72f, mGR.mTex_Efect.width()*.2f, mGR.mTex_Efect.Height()*.3f, s2wX(event.getX()), s2wY(event.getY()), .01f)){
			Sel =101;
		}
		if(CircRectsOverlap(0.3f,-.72f, mGR.mTex_Efect.width()*.2f, mGR.mTex_Efect.Height()*.3f, s2wX(event.getX()), s2wY(event.getY()), .01f)){
			Sel =102;
		}
		if (MotionEvent.ACTION_DOWN == event.getAction()){
			moveR = s2wX(event.getX());
		}
		if (MotionEvent.ACTION_MOVE == event.getAction()){
			move =  s2wX(event.getX())-moveR;
			if(Math.abs(move) > .1)
				isMove = true;
			else
				isMove = false;
		}
		if (MotionEvent.ACTION_UP == event.getAction()) {
			if(isMove){
				isMove = false;
				if (mGR.mFramPage > 0 && move > 0)
					movevx = .1f;
				else if (mGR.mFramPage < mGR.mTex_Grid.length-1 && move < 0)
					movevx = -.1f;
				else
					moveR = move = movevx = 0;
			}else if (Sel > 0) {
				moveR = move = movevx = 0;
				switch (Sel) {
				case 100:
					ZRenderer.mStart.ShowGPlus(View.VISIBLE);//GameRenderer.mStart.mPlusOneButton.setVisibility(View.VISIBLE);
					M.AppScreen = M.APPMENU;
					break;
				case 101:
					if (mGR.mFramPage > 0)
						movevx = .1f;
					break;
				case 102:
					if (mGR.mFramPage < mGR.mTex_Grid.length-1)
						movevx = -.1f;
					break;
				default:
					forSave =0;
					if(Sel <= mGR.mTex_Grid[mGR.mFramPage].length )
						mGR.mFramImg = Sel -1;
					mGR.gameReset();
					M.AppScreen = M.APPPLAY;
					break;
				}
			}
			Sel = 0;
		}
		return true;
	}
	private boolean Action = false;
	private float oldDist;
	private float oldAng;
	private float mode = 0;
	private float mmode = 0;
	private float scale = 1;
	private float cx,cy;
	private int rgbsel = -1;
	private  boolean TouchMove( MotionEvent event) 
    {
    	
    	int work = mGR.mImgSel;
    	switch (event.getAction() & MotionEvent.ACTION_MASK) 
    	{
		case MotionEvent.ACTION_DOWN: // first finger down only
			mode = 0;
			mmode = 1;
			cx = s2wX(event.getX());
			cy = s2wY(event.getY());
			break;
		case MotionEvent.ACTION_UP: // first finger lifted
			 mode = 0;
			 mmode = 0;
			 mGR.mSkeleton.get(work).lsx = mGR.mSkeleton.get(work).lsx*mGR.mSkeleton.get(work).sx;
			 mGR.mSkeleton.get(work).lsy = mGR.mSkeleton.get(work).lsy*mGR.mSkeleton.get(work).sy;
			 mGR.mSkeleton.get(work).sx = mGR.mSkeleton.get(work).sy = 1;
			 mGR.mSkeleton.get(work).ang +=mGR.mSkeleton.get(work).langle;
			 mGR.mSkeleton.get(work).langle = 0;
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
				if (mGR.action == 0) //Scal
				{
					scale = (newDist / oldDist);
					mGR.mSkeleton.get(work).sy=mGR.mSkeleton.get(work).sx=scale;
				}
				if (mGR.action == 1) 
				{
					scale = (newDist / oldDist);
					mGR.mSkeleton.get(work).sx=scale;
				}
				if (mGR.action == 2) 
				{
					scale = (newDist / oldDist);
					mGR.mSkeleton.get(work).sy=scale;
				}
				if (mGR.action == 3){
					float  newAng = Angle(event);
					mGR.mSkeleton.get(work).langle = (float)Math.toDegrees(oldAng-newAng);
				}
				
			} else if (mmode == 1) {
				if (mGR.action == 4 && rgbsel>=0 ) {
					if(s2wX(event.getX()) > -.4f && s2wX(event.getX()) < 0.4f){
						mGR.mSkeleton.get(work).rgb[rgbsel] =s2wX(event.getX());
					}
				} else if (mGR.action == 5 && rgbsel>=0 ) {
					if(s2wX(event.getX()) > -.4f && s2wX(event.getX()) < 0.4f){
						mGR.mTex_Frame.get(0).rgb[rgbsel] =s2wX(event.getX());
					}
				} else if(mGR.mSkeleton.get(work).mTexture !=null){
					mGR.mSkeleton.get(work).x += -cx + s2wX(event.getX());
					mGR.mSkeleton.get(work).y += -cy + s2wY(event.getY());
					cx = s2wX(event.getX());
					cy = s2wY(event.getY());
				}
			}
			break;
		}
        return true; 
    }
    private boolean TouchEdit( MotionEvent event) 
    {
    	
    	int work = mGR.mGellary.size()-1;
    	if(work<0)
    		return false;
    	switch (event.getAction() & MotionEvent.ACTION_MASK) 
    	{
		case MotionEvent.ACTION_DOWN:
			mode = 0;
			mmode = 1;
			cx = s2wX(event.getX());
			cy = s2wY(event.getY());
			break;
		case MotionEvent.ACTION_UP: 
			 mode = 0;
			 mmode = 0;
			 mGR.mGellary.get(work).lex = mGR.mGellary.get(work).lex*mGR.mGellary.get(work).ex;
			 mGR.mGellary.get(work).ley = mGR.mGellary.get(work).ley*mGR.mGellary.get(work).ey;
			 mGR.mGellary.get(work).ex = mGR.mGellary.get(work).ey = 1;
			 mGR.mGellary.get(work).Eangle +=mGR.mGellary.get(work).Elangle;
			 mGR.mGellary.get(work).Elangle = 0;
			break;
		case MotionEvent.ACTION_POINTER_UP: 
			 mode = 0;
			 mmode = 0;
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
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
					mGR.mGellary.get(work).ey=mGR.mGellary.get(work).ex=scale;
				}
				if (mGR.action == 1) 
				{
					scale = (newDist / oldDist);
					mGR.mGellary.get(work).ex=scale;
				}
				if (mGR.action == 2) 
				{
					scale = (newDist / oldDist);
					mGR.mGellary.get(work).ey=scale;
				}
				if (mGR.action == 3){
					float  newAng = Angle(event);
					mGR.mGellary.get(work).Elangle = (float)Math.toDegrees(oldAng-newAng);
				}
				
			} else if (mmode == 1) {
				if (mGR.action == 4 && rgbsel>=0 ) {
					if(s2wX(event.getX()) > -.4f && s2wX(event.getX()) < 0.4f){
						mGR.mGellary.get(work).Ergb[rgbsel] =s2wX(event.getX());
					}
				} else if (mGR.action == 5 && rgbsel>=0 ) {
					if(s2wX(event.getX()) > -.4f && s2wX(event.getX()) < 0.4f){
						mGR.mTex_Frame.get(0).rgb[rgbsel] =s2wX(event.getX());
					}
				} else {
					mGR.mGellary.get(work).movx += -cx + s2wX(event.getX());
					mGR.mGellary.get(work).movy += -cy + s2wY(event.getY());
					cx = s2wX(event.getX());
					cy = s2wY(event.getY());
				}
			}
			break;
		}
        return true;
    }
    
    private float spacing(MotionEvent event)
	{
    	int count =  MotionEventCompat.getPointerCount(event);
    	if(count<2){
    		return 0;
    	}
        float x = s2wX(event.getX(0)) - s2wX(event.getX(1));
        float y = s2wY(event.getY(0)) - s2wY(event.getY(1));
        return FloatMath.sqrt(x * x + y * y);
    }
    private float Angle(MotionEvent event)
	{
    	int count =  MotionEventCompat.getPointerCount(event);
    	if(count<2){
    		return 0;
    	}
        float x = s2wX(event.getX(0)) - s2wX(event.getX(1));
        float y = s2wY(event.getY(0)) - s2wY(event.getY(1));
        return (float)GetAngle(y, x);
    }
    private float move,movevx =0;
    private float moveR = 0;
    private boolean isMove = false;
	private void Draw_GallerySub(GL10 gl){
		DrawTexture(gl, mGR.mTex_BG, 0, 0);
		DrawTransScal(gl, mGR.mTex_BackBtn , -.48f, .89f,Sel == 100 ?1.1f:1,Sel == 100 ?0.5f:1);
		for (int i = 0; i < 12 && mGR.page*12 +i < mGR.mTex_Library[mGR.LibSel].length; i++) {
			DrawTransScal(gl, mGR.mTex_SubICN,move-.36f+.36f*(i%3), .60f-.35f*(i/3),Sel==(i+1)?1.1f:1,Sel==(i+1)?0.5f:1);
			DrawTransScal(gl, mGR.mTex_Library[mGR.LibSel][mGR.page*12 +i],move-.36f+.36f*(i%3), .60f-.35f*(i/3),
					mGR.LibSel<1?.5f:1,Sel==(i+1)?0.5f:1);
		}
		
		if (move != 0 || movevx !=0) {
			int nPage = 0;
			move += movevx;
			if (move > 0 ){
				nPage = mGR.page-1;
			}
			if (move < 0 ){
				nPage = mGR.page+1;
			}
			for (int i = 0; i < 12 && nPage*12 +i < mGR.mTex_Library[mGR.LibSel].length && nPage>=0; i++) {
				DrawTransScal(gl, mGR.mTex_SubICN,move-.36f+.36f*(i%3)+(move < 0 ? 1.3f:-1.3f), .60f-.35f*(i/3)
						,Sel==(i+1)?1.1f:1,Sel==(i+1)?0.5f:1);
				DrawTransScal(gl, mGR.mTex_Library[mGR.LibSel][nPage*12 +i],move-.36f+.36f*(i%3)+(move < 0 ? 1.3f:-1.3f), .60f-.35f*(i/3),
						mGR.LibSel<1?.5f:1,Sel==(i+1)?0.5f:1);
			}
			if (movevx > 0 && move > 1) {
				movevx = move = 0;
				mGR.page--;
			}
			if (movevx < 0 && move < -1) {
				movevx = move = 0;
				mGR.page++;
			}
		}
		
		
		if (mGR.page > 0)
			DrawTransScal(gl, mGR.mTex_Arw[0],-.3f, -.72f,Sel==(101)?1.1f:1,Sel==(101)?0.5f:1);
		
		if ((mGR.page+1)*12 < mGR.mTex_Library[mGR.LibSel].length)
			DrawTransScal(gl, mGR.mTex_Arw[1],0.3f, -.72f,Sel==(102)?1.1f:1,Sel==(102)?0.5f:1);
	}
	private boolean Handle_GallerySub(MotionEvent event){
		Sel =0;
		
		for (int i = 0; i < 12 && mGR.page*12 +i < mGR.mTex_Library[mGR.LibSel].length; i++) {
			if(CircRectsOverlap(-.36f+.36f*(i%3), .60f-.35f*(i/3), mGR.mTex_SubICN.width()*.3f, mGR.mTex_SubICN.Height()*.2f, s2wX(event.getX()), s2wY(event.getY()), .01f)){
				Sel = i+1;
			}
		}
		if(CircRectsOverlap(-.48f, .89f, mGR.mTex_Efect.width()*.2f, mGR.mTex_Efect.Height()*.3f, s2wX(event.getX()), s2wY(event.getY()), .01f)){
			Sel =100;
		}
		
		if(CircRectsOverlap(-.3f,-.72f, mGR.mTex_Efect.width()*.2f, mGR.mTex_Efect.Height()*.3f, s2wX(event.getX()), s2wY(event.getY()), .01f)){
			Sel =101;
		}
		
		if(CircRectsOverlap(0.3f,-.72f, mGR.mTex_Efect.width()*.2f, mGR.mTex_Efect.Height()*.3f, s2wX(event.getX()), s2wY(event.getY()), .01f)){
			Sel =102;
		}
		if (MotionEvent.ACTION_DOWN == event.getAction()){
			moveR = s2wX(event.getX());
		}
		if (MotionEvent.ACTION_MOVE == event.getAction()){
			move =  s2wX(event.getX())-moveR;
			if(Math.abs(move) > .1)
				isMove = true;
			else
				isMove = false;
		}
		if (MotionEvent.ACTION_UP == event.getAction()) {
			if(isMove){
				isMove = false;
				if (mGR.page > 0 && move > 0)
					movevx = .1f;
				else if ((mGR.page+1)*12 < mGR.mTex_Library[mGR.LibSel].length && move < 0)
					movevx = -.1f;
				else
					moveR = move = movevx = 0;
			}else if (Sel > 0) {
				moveR = move = movevx = 0;
				switch (Sel) {
				case 100:
					M.AppScreen = M.APPMAIN;
					break;
				case 101:
					if (mGR.page > 0)
						movevx = .1f;
					break;
				case 102:
					if ((mGR.page + 1) * 12 < mGR.mTex_Library[mGR.LibSel].length)
						movevx = -.1f;
				default:
					mGR.mMode = 1;
					mGR.LibSubSel = mGR.page * 12 + Sel - 1;
					if(mGR.LibSubSel < mGR.mTex_Library[mGR.LibSel].length){
						Gellary tempe = new Gellary(mGR.mTex_Library[mGR.LibSel][mGR.LibSubSel]);
						mGR.mGellary.add(tempe);
						M.AppScreen = M.APPPLAY;
//						mGR.clear();
					}
					break;
				}
			}
			Sel = 0;
		}
		return true;
	}
	private void Draw_GalleryMain(GL10 gl){
		DrawTexture(gl, mGR.mTex_BG, 0, 0);
		DrawTransScal(gl, mGR.mTex_BackBtn , -.48f, .89f,Sel == 100 ?1.1f:1,Sel == 100 ?0.5f:1);
		
		for (int i = 0; i < 20; i++) {
			DrawTransScal(gl, mGR.mTex_Main[0]	,-.400f+.271f*(i%4), .610f-.271f*(i/4),Sel==(i+1)?1.1f:1,Sel==(i+1)?0.5f:1);
			if(i == 1)
				DrawTransScal(gl, mGR.mTex_Library[i][0],-.400f+.271f*(i%4), .610f-.271f*(i/4),i<2?.9f:1.0f,Sel==(i+1)?0.5f:1);
			else
				DrawTransScal(gl, mGR.mTex_Library[i][0],-.400f+.271f*(i%4), .610f-.271f*(i/4),i<2?.4f:.7f,Sel==(i+1)?0.5f:1);
			DrawTransScal(gl, mGR.mTex_Main[1]	,-.305f+.271f*(i%4), .515f-.271f*(i/4),1,1);
		}
		
	}
	private boolean Handle_GalleryMain(MotionEvent event){
		Sel =0;
		for(int i=0;i<20 ;i++){
			if(CircRectsOverlap(-.400f+.271f*(i%4), .610f-.271f*(i/4), mGR.mTex_Main[0].width()*.2f, 
					mGR.mTex_Main[0].Height()*.2f, s2wX(event.getX()), s2wY(event.getY()), .01f)){
				Sel =1+i;
			}
		}
		
		if(CircRectsOverlap(-.48f, .89f, mGR.mTex_Efect.width()*.2f, mGR.mTex_Efect.Height()*.3f, s2wX(event.getX()), s2wY(event.getY()), .01f)){
			Sel =100;
		}
		if (MotionEvent.ACTION_UP == event.getAction() && Sel >0) {
			switch (Sel) {
			case 100:
				M.AppScreen = M.APPPLAY;
				break;
			default:
				mGR.LibSel = Sel-1;
				M.AppScreen = M.APPSUB;
				mGR.page = 0;
				break;
			
			}
			Sel = 0;
		}
		return true;
	}
	private void Draw_AddText(GL10 gl){
		DrawTexture(gl, mGR.mTex_BG, 0, 0);
		DrawTransScal(gl, mGR.mTex_Cros , -.48f, .56f,Sel == 100 ?1.1f:1,Sel == 100 ?0.5f:1);
		DrawTransScal(gl, mGR.mTex_Ok , 0.48f, .56f,Sel == 100 ?1.1f:1,Sel == 100 ?0.5f:1);
		if(!ZRenderer.mStart.isTxtVisible()){
			try {
				ZRenderer.mStart.Txthandler.sendEmptyMessage(View.VISIBLE);
			} catch (Exception e) {
			}
		}
		
	}
	private boolean Handle_AddText(MotionEvent event){
		Sel =0;
		if(CircRectsOverlap(-.48f, .56f, mGR.mTex_Efect.width()*.3f, mGR.mTex_Efect.Height()*.3f, s2wX(event.getX()), s2wY(event.getY()), .01f)){
			Sel =100;
		}
		if(CircRectsOverlap(0.48f, .56f, mGR.mTex_Efect.width()*.3f, mGR.mTex_Efect.Height()*.3f, s2wX(event.getX()), s2wY(event.getY()), .01f)){
			Sel =101;
		}
		if (MotionEvent.ACTION_UP == event.getAction() && Sel >0) {
			switch (Sel) {
			case 100:
				ZRenderer.mStart.hideKey();
				M.AppScreen = M.APPPLAY;
				break;
			default:
				mGR.mFontCnVs.drawColor(0, Mode.CLEAR);
				mGR.mFontCnVs.drawText(ZRenderer.mStart.getAddTxt(), 256,32, mGR.mPaint);
				Gellary tempe = new Gellary(mGR.addBitmap(mGR.mBitmap));
				mGR.mGellary.add(tempe);
				M.AppScreen = M.APPPLAY;
				break;
			}
			try {
				ZRenderer.mStart.Txthandler.sendEmptyMessage(View.GONE);
			} catch (Exception e) {
			}
			forSave =0;
			Sel = 0;
		}
		return true;
	}
	private void DrawTexture(GL10 gl,APLAN Tex,float x,float y)
	{
		Tex.drawPos(gl, x, y);
	}
	
	private void DrawTransScal(GL10 gl,APLAN Tex,float x,float y, float z,float t)
	{
		Tex.drawTransprentScal(gl, x, y, z, t);
	}
	private boolean CircRectsOverlap(double CRX,  double CRY,double CRDX,double CRDY ,double centerX,double centerY, double radius)
    {
        if ((Math.abs(centerX - CRX) <= (CRDX + radius)) && (Math.abs(centerY - CRY) <= (CRDY + radius)))
           return true;
        return false ;

    }
	
	private float s2wX(float a) {
		float c = ((a - M.ScreenWidth / 2) / M.ScreenHieght) * 2;
		return c;
	}
	private float s2wY(float a)
	{
		float c = ((a / M.ScreenHieght)- 0.5f)*(-2);
		return c;
	}
	
	public int w2sX(float a)
	{
		int c = (int)(.5f*((a*M.ScreenHieght)+M.ScreenWidth));
		return c;
	}
	
	public int w2sY(float a)
	{
		int c = (int)((.5f-(a / 2f))*(M.ScreenHieght));
		return c;
	}
	private void RateUs() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.LINK + getClass().getPackage().getName()));
		ZRenderer.mContext.startActivity(mIntent);
	}
	private double GetAngle(double d,double e)
	{
	  if(d==0)
		  return e>=0 ? Math.PI/2 : -Math.PI/2;
	  else if (d > 0)
		  return Math.atan(e/d);
	  else
		  return Math.atan(e/d) + Math.PI;
	}
	private void facebook() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://www.facebook.com/HututuGames"));
		ZRenderer.mContext.startActivity(mIntent);
	}
	public void MoreGame() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.MARKET));
		ZRenderer.mContext.startActivity(mIntent);
	}
	private void share2friend() {
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("text/plain");
		i.putExtra(Intent.EXTRA_SUBJECT,ZRenderer.mContext.getResources().getString(R.string.app_name));
		i.putExtra(Intent.EXTRA_TEXT,"!!! Download it now and let’s ROCK!!!!  "+ M.SHARELINK+getClass().getPackage().getName());
		try {ZRenderer.mContext.startActivity(Intent.createChooser(i,"Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(ZRenderer.mStart,"There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
	private void shareImage() {
		String extr = Environment.getExternalStorageDirectory().toString();
		File myPath = new File(extr, mGR.PhotoPath);
		if (!myPath.exists()) {
			ZRenderer.mStart.SaveMassage();
		}
		String str = Environment.getExternalStorageDirectory() + mGR.PhotoPath;
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(str)));
		shareIntent.setType("image/png");
		ZRenderer.mContext.startActivity(Intent.createChooser(shareIntent, "Where?"));
		mGR.isShare = false;
	}
	
	private Bitmap ImageCut(Bitmap ybitmap)
    {
    	int x = -w2sX(-.535f);
    	int y = -w2sY(0.750f);
    	int dx=(int)(w2sX(0.535f)-w2sX(-.535f));
    	int dy=(int)(M.ScreenHieght*.54f);
    	Bitmap image = Bitmap.createBitmap(dx,dy, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawBitmap(ybitmap, x, y, null);
        return image;
    }
	
	private Bitmap getNew(GL10 gl)
    {
    	int width = (int)M.ScreenWidth;
    	int height = (int)(M.ScreenHieght);
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
    
    private void SaveScreen(GL10 gl){
		if(!screenshot)
			return;
		for(int i =0;i<mGR.mSkeleton.size();i++){
			gl.glEnable(GL10.GL_SCISSOR_TEST);
			gl.glScissor((int)(mGR.mSkeleton.get(i).rectX),(int)(mGR.mSkeleton.get(i).rectY),(int)mGR.mSkeleton.get(i).rectDx,(int)mGR.mSkeleton.get(i).rectDy);
			if(mGR.mSkeleton.get(i).mTexture!=null){
				mGR.mSkeleton.get(i).mTexture.drawAll(gl, mGR.mSkeleton.get(i).x, mGR.mSkeleton.get(i).y, 
						mGR.mSkeleton.get(i).ang + mGR.mSkeleton.get(i).langle,
						mGR.mSkeleton.get(i).lsx * mGR.mSkeleton.get(i).sx,
						mGR.mSkeleton.get(i).lsy * mGR.mSkeleton.get(i).sy,
						mGR.mSkeleton.get(i).rgb);
			}
			
			gl.glDisable(GL10.GL_SCISSOR_TEST);	
		}
		for(int i =0;i<mGR.mTex_Frame.size();i++){
			mGR.mTex_Frame.get(i).mTex.drawAll(gl, mGR.mTex_Frame.get(i).x, mGR.mTex_Frame.get(i).y, 
					0,1,1,mGR.mTex_Frame.get(i).rgb);
			
		}
		for(int i =0;i<mGR.mGellary.size();i++){
			if(mGR.mGellary.get(i).mTexture!=null){
				mGR.mGellary.get(i).mTexture.drawAll(gl, mGR.mGellary.get(i).movx, mGR.mGellary.get(i).movy, 
						mGR.mGellary.get(i).Eangle + mGR.mGellary.get(i).Elangle,
						mGR.mGellary.get(i).lex * mGR.mGellary.get(i).ex,
						mGR.mGellary.get(i).ley * mGR.mGellary.get(i).ey,
						mGR.mGellary.get(i).Ergb);
			}
		}
		
		
        Bitmap bb = getNew(gl);
        Bitmap b = ImageCut(bb);
        String extr = Environment.getExternalStorageDirectory().toString();
        
        File folder = new File(Environment.getExternalStorageDirectory() + M.DIR);
        if(!folder.exists())
        {
            folder.mkdir();
        }  
        
        File myPath = new File(extr, mGR.PhotoPath);
        
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            b.compress(Bitmap.CompressFormat.JPEG, 85, fos);
            fos.flush();
            fos.close();
            MediaStore.Images.Media.insertImage(ZRenderer.mStart.getContentResolver(), b, "Screen", "screen");
            if(mGR.isShare)
            	shareImage();
        }catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println(myPath.exists()+"~~~~~~~~~~~~~123~~~~~~~~~~~~~~~"+e.toString());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println(myPath.exists()+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+e.toString());
        }
        if(mGR.isMenu){
        	M.AppScreen =M.APPMENU;
//        	GameRenderer.mStart.mPlusOneButton.setVisibility(View.VISIBLE);
        	ZRenderer.mStart.ShowGPlus(View.VISIBLE);
        	mGR.isMenu = false;
        }
        	
    }
    boolean screenshot = false;
    @Override
	public void draw(GL10 gl) 
	{
		switch (M.AppScreen) {
		case M.APPLOGO:
			DrawTexture(gl, mGR.mTex_Logo, 0, 0);
			if(Counter > 120){
				M.AppScreen = M.APPMENU;
				ZRenderer.mStart.ShowGPlus(View.VISIBLE);//GameRenderer.mStart.mPlusOneButton.setVisibility(View.VISIBLE);
			}
			Counter++;
			break;
		case M.APPMENU:
			Draw_Menu(gl);
			break;
		case M.APPFRAME:
			Draw_Frame(gl);
			break;
		case M.APPMAIN:
			Draw_GalleryMain(gl);
			break;
		case M.APPSUB:
			Draw_GallerySub(gl);
			break;
		case M.APPPLAY:
			Draw_Play(gl);
			break;
		case M.ADDTEXT:
			Draw_AddText(gl);
			break;
		}
	}
	public boolean TouchEvent(MotionEvent event) 
	{
		
		switch (M.AppScreen) {
		case M.APPMENU:
			Handle_Menu(event);
			break;
		case M.APPFRAME:
			Handle_Frame(event);
			break;
		case M.APPMAIN:
			Handle_GalleryMain(event);
			break;
		case M.APPSUB:
			Handle_GallerySub(event);
			break;
		case M.APPPLAY:
			Handle_Play(event);
			break;
		case M.ADDTEXT:
			Handle_AddText(event);
			break;
		}
		return true;
	}    
}
