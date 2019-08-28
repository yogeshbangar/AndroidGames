package com.ultimate.frog.fun;

public class Leaf {
	int no[] = new int[4];
	float y;
	int iAnim = 0;
	int jAnim = 0;
	void Set(float _y) {
		y = _y;
		for(int i=0;i<no.length;i++){
			no[i] = -1;
		}
		no[M.mRand.nextInt(4)] = 0;
		for(int i=0;i<no.length;i++){
			if(no[i]==-1)
				no[i] = M.mRand.nextInt(3)+1;
		}
		iAnim = 0;
	}
}
