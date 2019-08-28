package com.fun2sh.penguinfreejump;

public class Hardle {
	
	int worng = -1;
	int jump = 0;
	int img[] = new int[3];
	float y;
	
	public Hardle(float _y){
		set(_y);
	}
	
	void set(float _y) {
		y = _y;
		int im = M.mRand.nextInt(3);
		worng = -1;
		for (int i = 0; i < img.length; i++) {
			if (im == i)
				img[i] = 0;
			else
				img[i] = M.mRand.nextInt(2) + 1;
		}
		jump = -1;
	}
}
