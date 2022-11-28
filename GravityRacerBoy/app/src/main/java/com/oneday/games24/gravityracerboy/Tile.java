package com.oneday.games24.gravityracerboy;

public class Tile {
	byte down;
	short array[] = new short[15];
	 float x;
	 
	 void set(float _x,short _array[]){
		 for(int i=0;i<_array.length;i++)
			 array[i] = _array[i];
		 x =_x;
		 down =0;
	 }
}
