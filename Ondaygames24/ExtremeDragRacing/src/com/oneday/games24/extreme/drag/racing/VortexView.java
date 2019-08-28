package com.oneday.games24.extreme.drag.racing;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class VortexView extends GLSurfaceView 
{
	
	public VortexView(Context context, AttributeSet attrs) { 
		super(context, attrs);
		mStart = (Start)context;
	}
	public void showRenderer(GameRenderer renderer){ 
		this._renderer=renderer;        
	}
	public GameRenderer _renderer; 
	public Start mStart = null;
	public VortexView(Context context) { 
		super(context);
		mStart = (Start)context;
	}
	public boolean onTouchEvent(final MotionEvent event) 
	{ 
		_renderer.root.TouchEvent(event);

		return true;
	}
}
