package com.oneday.games24.clashofbattles;

public class PMissile {
	
	boolean isTop = false;
	
	byte img = 0;
	int count =0;
	
	float x, y;
	float vx, vy;
	float radin = 0;
	
	void set(float _x, float _y, int _img,boolean _isTop) {
		x = _x;
		y = _y;
		img = (byte) _img;
		radin = 2.8f;
		count =0;
		vx = (float) Math.sin(radin) * .07f * .5f;
		vy = -(float) Math.cos(radin) * .08f * .5f;
		isTop = _isTop;
	}
	
	void set(float _x, float _y,boolean _isTop) {
		x = _x;
		y = _y;
		{
			radin = .5f;
			vx = (float) Math.sin(radin) * .03f ;
			vy = -(float) Math.cos(radin) * .04f;
			isTop = _isTop;
			img = 0;
		} 
//		System.out.println("[x= "+x + "][y= " + y + "][vx = " + vx + "][vy= " + vy + "][ang = " + radin+"]");
	}
	
	
//	arvpb4907b20031984
	boolean update(float x1, float y1) {
		if (x < 1.5 && x > -1.5 && y < 1.5 && y > -1.5){
			if (count % 3 == 0) {
				if (!isTop) {
					System.out.println("[x="+x+"][y="+y+"]   [x1="+x1+"]  [y1="+y1+"] [rad="+radin+"]");
					if (x1 < 1.5 && x1 > -1.5 && y1 < 1.5 && y1 > -1.5) {
						double ang = (GameRenderer.mStart.mGR.root.GetAngle((y - y1), -(x - x1)));
						if (radin + .15f > ang)
							radin -= .15f;
						else
							radin = (float) ang;
					}else{
						radin -= .15f;
					}
					vx = (float) Math.sin(radin) * .07f * .5f;
					vy = -(float) Math.cos(radin) * .08f * .5f;
				}
				GameRenderer.mStart.mGR.root.setSmock(x, y);
			}
			x += vx;
			y += vy;
			count++;
			if (count > 100 || y < -.32f) {
				GameRenderer.mStart.mGR.root.setBlast(x, y,M.mRand.nextInt(3));
				x = -10;
				return false;
			}
			return true;
		}
		return false;
	}

	boolean IsIn() {
		if (x < 1.5 && x > -1.5 && y < 1.5 && y > -1.5) {
			return true;
		}
		return false;
	}
}
