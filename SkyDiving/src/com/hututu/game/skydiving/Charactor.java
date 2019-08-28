package com.hututu.game.skydiving;

public class Charactor {
	float x,y,vx,vy;
	float p;
	int go;
	int rotate,inc;
	int blast;
	int Pl,counter;
	void set(float _x,float _y,float _vx,float _vy,int _Pl){
		x  =_x;
		y  =_y;
		vx =_vx;
		vy =_vy;
		go =-1;
		p=.3f;
		inc = 1;
		blast =0;
		Pl = _Pl;
		counter=100;
	}
	void update()
	{
		counter--;
		if(blast == 0){
			if(y>-.9f || go <0)
				y+=(M.SPEED+vy);
			if(go>-1|| x<-10)
				go++;
			if(go>-1 && vy>-.005)
			{
				vy-=.0005f;
				if(p<1)
					p*=1.15f;
			}
			rotate+=inc;
			if(rotate>9)
				inc =-1;
			if(rotate<-9)
				inc =1;
		}
		if((y<-.84f && go ==-1)||blast>0)
		{
			blast ++;
			Pl =1;
			y+=.01f;
		}
		if(counter==0 && Pl ==3)
			Pl =1;
			
//		System.out.println(M.SPEED+"    "+vy+"   "+y);
	}
	void Collide(){
		if(Pl==3)return;
		if(go==-1){
			go=0;
			vy = M.SPEED*3;
		}
	}
}
