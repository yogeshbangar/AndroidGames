package com.apps.cropimage;

public class EditImg {
	SimplePlane tex;
	public float sx = 0;
	public float lsx = 1;
	public float sy = 0;
	public float lsy = 0;
	public float rgb[] = { .35f, .35f, .35f };
	public float ang = 0;
	public float langle = 0;
	public float x = 0;
	public float y = 0;
	public EditImg(SimplePlane _tex){
		tex = _tex;
		rgb[0] = .4f;
		rgb[1] = .4f;
		rgb[2] = .4f;
		lsx = 1;
		sx = 1;
		lsy = 1;
		sy = 1;
		langle = ang = 0;

	
	}
	
}
