package com.hututu.game.scarygames;

/*
 * 1 ->  Color ball in 5 $ (users selected Color will be automatically destroy)
 * 2 ->   Electric ball in 5 $ (one column will be automatically destroyed)
 * 3 ->   fill blank area power in 1 $.
 * 4 ->   Color replace power in 10 $ (user can select the color ball for one time) 
 * 5 ->   Fire ball (Bomb) power in 10 $ (all balls will be destroyed, on which area will be covered by fire ball.)
 */



public class Player {
	float x=0,y=0,vy;
    int color;
    int Power = 0;
    Player(float _x,float _y)
    {
        x=_x;
        y=_y;
        vy =.1f;
    }
    void set(float _x,float _y,float vy,int _color)
    {
        x =_x;
        y =_y;
        color =_color;
        Power = 0;
        vy =.1f;
    }
	
}
