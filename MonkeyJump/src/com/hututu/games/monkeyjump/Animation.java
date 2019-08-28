package com.hututu.games.monkeyjump;

public class Animation {
	float x,y,z;
	float vx,vy,vz;
	byte color = 0;
	byte img=0;
	byte rotate;
	void set(float _x,float _y,int _img){
		img = (byte)_img;
		x = _x;
		y = _y;
		z =0.1f;
		vz = 0.1f;
		
		if(img == 11){
			vy = (M.mRand.nextInt(15)+10)*-.0005f;
			vz = 0.1f;
			img =10;
		}else
			vy = (M.mRand.nextInt()%15)*-.0005f;
		rotate =(byte)(M.mRand.nextBoolean()?1:-1);
		
		if(img > 0 && img <20){
			vx = (M.mRand.nextInt()%15)*.002f;
			vy = (M.mRand.nextInt(15)+10)*-.002f;
		}else{
			vx = (M.mRand.nextInt()%15)*.002f;
		}
		
	}
	
	
	
	void setFal(float _x,float _y,int _img){
		x = _x;
		y = _y;
		vx = (M.mRand.nextInt()%15)*.001f;
		vy = (M.mRand.nextInt()%15)*-.0005f;
		vz = 0.1f;
		img = (byte)_img;
		z =.1f;
		rotate =(byte)(M.mRand.nextBoolean()?1:-1);
	}
	void setOne(float _x, float _y) {
		x = _x + (_x > 0 ? 0.06f : -.06f);
		y = _y;
		z = 0.1f;
		vx = (M.mRand.nextInt(2) + 5) * (x > 0 ? -.002f : 0.002f);
		vx = (M.mRand.nextInt(5)) * (M.mRand.nextBoolean()  ? -.001f : 0.001f);
		vy = (M.mRand.nextInt(5) + 20) * -.0008f;
		vz = 0.2f;
		img = 0;
		rotate =(byte)(M.mRand.nextBoolean()?1:-1);
	}
	boolean update(){
		if(z>0 && x > -1.1 && x < 1.1){
			if(rotate>0){
				rotate++;
			}else{
				rotate--;
			}
			x += vx;
			y += vy;
			z += vz;
			if(img != 0 && img <20){
				if(z > 1.5f)
					vz = -.2f;
			}
			else if(z > 2)
				vz = -.2f;
			return true;
		}
		return false;
	}
	
	boolean isIn(){
		
		
		if(z<=.1 || x <= -1.1 || x >= 1.1){
			return false;
		}
		return true;
	}
}
