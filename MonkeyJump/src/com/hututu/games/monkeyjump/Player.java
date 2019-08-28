package com.hututu.games.monkeyjump;

public class Player {
	boolean Power;
	boolean resetPower;
	byte PowerTime = 0;
	
	int TPower = 1;
	int cout = 0;
	int opp = 0;
	int oppCont = 0;
	int Actoin = 0;
	int Hardle = 0;
	int resetCount = 0;
	int mg2 = 0;
	int NewScore = 0;
	
	float x,y;
	float vx,vy;
	float speed;
	float distance;
	float Bestdistance;
	float TDistance = 0;
	
	float z = 1;
	float bvz = .02f;
	
	GameRenderer mGR;
	public Player(GameRenderer mGR)
	{
		this.mGR =mGR;
	}
	void set(float _x,float _y){
		x =_x;
		y =_y;
		speed = 0.01f;
		vx = vy = distance = 0;
		cout =0;
		opp = oppCont =0;
		Actoin = 0;
		Hardle =-1;
		PowerTime = 1;
		resetPower = false;
		Power = false;
		z = 1;
		bvz = .05f;
		if(Bestdistance > 0){
			NewScore = 0;
		}else{
			NewScore = 100001;
		}
	}

	void update() 
	{
		if (Actoin > 0) {
			if(z > 1.7){
				bvz = -.04f; 
			}
			if(z < 1.0){
				bvz = 0.04f; 
			}
			z += bvz;
			Actoin--;
			speed = .1f;
			if (opp == 2) {
				Gilhari();
			} else {
				if (Actoin > 10) {
					M.sound4Bonus(GameRenderer.mContext, opp, mGR.root.Counter);
					if (x > .65f) {
						vx = -.02f;
					}
					if (x < -0.65f) {
						vx = 0.02f;
					}
					x += vx;
				} else {
					if (x > -.65f && (x < .65f))
						x += vx;
				}
				if (Actoin < 1) {
					M.sound4Bstop(GameRenderer.mContext, opp, mGR.root.Counter);
					speed = 0.04f;
					if (x < 0)
						x = -.65f;
					else
						x = 0.65f;
					vx = 0;
					//mGR.root.setAnim(x, y, 100, 2);
					switch (opp) {
					case 1://sudrshan
						mGR.root.setAnim(x, y, 30,3);
						break;
					case 4:// Roket
						mGR.root.setAnim(x, y, 30,4);
						break;
					case 2://Gilhari
						mGR.root.setAnim(x, y, 30,1);
						break;
					case 3: //chidiya
						mGR.root.setAnim(x, y, 30,2);
						break;
					}
					opp = oppCont = 0;
				}
			}
		} else {
			if(speed > 0 && speed < 0.05f)
					speed += 0.001f;
			if (vx != 0 && speed > 0) {
				x += vx;
				y += vy;
				vy -= .009f;
				if (vx > 0 && x > .65f) {
					vy = vx = 0;
					x = .65f;
					y = -.4f;
					if(M.mRand.nextBoolean())
						M.sound20(GameRenderer.mContext, R.drawable.jam1);
					else
						M.sound21(GameRenderer.mContext, R.drawable.jam2);
				}
				if (vx < 0 && x < -0.65f) {
					vy = vx = 0;
					x = -.65f;
					y = -.4f;
					if(M.mRand.nextBoolean())
						M.sound20(GameRenderer.mContext, R.drawable.jam1);
					else
						M.sound21(GameRenderer.mContext, R.drawable.jam2);
				}

			}
		}
		if(distance > PowerTime*70){
			System.out.print("PowerTime"+PowerTime+"   "+resetPower+"< = resetPower");
			resetPower = true;
			PowerTime++;
			
		}
	}
	

	void Gilhari() {
		if (mGR.root.Counter % 2 == 0)
			mg2++;
		if (Actoin > 10) {
			M.sound4Bonus(GameRenderer.mContext, opp, mGR.root.Counter);
			if (mg2 < 10 || (x < .65f && vx > 0) || (x > -.65f && vx < 0)) {
				x += vx;
				y += vy;
				vy -= .007f;
			}

			
			if (x > .65f) {
				if (mg2 > 17) {
					vx = -.07f;
					y = -.4f;
					x = 0.65f;
					vy = .06f;
					mg2 = 0;
				} else {

				}
			}
			if (x < -0.65f) {
				if (mg2 > 17) {
					vx = 0.07f;
					y = -.4f;
					x = -.65f;
					vy = .06f;
					mg2 = 0;
				} else {

				}
			}
//			System.out.println("[x = "+x+"] [y="+y+"] [vx="+vx+"] [vy = "+vy+"] [mg2 ="+mg2+"]");
		} else {
			if (x > -.65f && (x < .65f)){
				x += vx;
				y += vy;
			}
		}
		if (Actoin < 1) {
			M.sound4Bstop(GameRenderer.mContext, opp, mGR.root.Counter);
			speed = 0.03f;
			if (x < 0)
				x = -.65f;
			else
				x = 0.65f;
			vx = 0;
			mGR.root.setAnim(x, y, 30,1);
			opp = oppCont = 0;
		}
		
		
		
	}
	
	void setOpp(int _opp){
		if(_opp == 0)
			oppCont =0;
		if(_opp == opp)
			oppCont++;
		else
			oppCont =1;
		opp = _opp;
		if(oppCont > 2){
			Actoin = 150;
			if (opp == 2) {
				if (x > 0) {
					vx = -.07f;
					y = -.4f;
					x = 0.65f;
					vy = .06f;
					mg2 = 0;
				} else {
					vx = 0.07f;
					y = -.4f;
					x = -.65f;
					vy = .06f;
					mg2 = 0;
				}
			}
		}
		
	}
}
