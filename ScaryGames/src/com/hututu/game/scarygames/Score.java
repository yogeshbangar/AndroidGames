package com.hututu.game.scarygames;

import android.util.Log;

public class Score {
    float x=0,y=0;
    int color,couter,avy;
    int c2;
    public Score(float _x,float _y)
    {
        x=_x;
        y=_y;
    }
    public void setScore(float _x,float _y,int _color)
    {
        x=_x;
        y=_y;
        color =Math.abs(_color-1);
        avy = 5;
        c2 = 20;
        Log.d(x+"   "+y,color+" ~~~~~~~~~~~~~~~~~~~~~~~~~~~ "+c2);
    }
    public void update()
    {
    	c2--;
    	couter+=avy;
		if(couter>75)
			avy=-15;
		if(couter<-75)
			avy=+15;
		y -= .005f;
    }
}
