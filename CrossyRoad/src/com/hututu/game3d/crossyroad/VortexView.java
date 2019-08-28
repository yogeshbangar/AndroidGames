package com.hututu.game3d.crossyroad;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class VortexView extends GLSurfaceView 
{
	public HTTRenderer _renderer; 
	public Start mStart = null;
	public VortexView(Context context) { 
		super(context);
		mStart = (Start)context;
	}
	public VortexView(Context context, AttributeSet attrs) { 
		super(context, attrs);
		mStart = (Start)context;
	}
	public void showRenderer(HTTRenderer renderer){ 
		this._renderer=renderer;        
	}
	public boolean onTouchEvent(final MotionEvent event) 
	{ 
		_renderer.root.onTouch(null, event);
		return true;
	}
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		return super.onKeyDown( keyCode, event ); 
	}  
	public boolean onKeyUp(int keyCode, KeyEvent event) 
	{  	
	 	return super.onKeyUp( keyCode, event ); 
	}
}
