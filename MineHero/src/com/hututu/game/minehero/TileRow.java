package com.hututu.game.minehero;

public class TileRow {
	float y;
	Tile tile[];

	public TileRow() {
		tile = new Tile[9];
		for (int i = 0; i < tile.length; i++)
			tile[i] = new Tile();
	}

	public void set(float _y, byte Arr[]) {
		int Zeros = 0;
		for (int i = 0; i < tile.length; i++) {
			tile[i].set(Arr[i]);
			if (tile[i].no == 0) {
				Zeros++;
			}
			if (tile[i].no == 3) {
				Zeros = 0;
			}
		}
		if (Zeros > 1) {
			Zeros = M.mRand.nextInt(Zeros);
			int m = 0;
			for (int i = 0; i < tile.length; i++) {
				if (tile[i].no == 0) {
					if (m >= Zeros)
						tile[i].no = 2;
					m++;
				}
			}
		}
		y = _y;
	}

	public void update(float _spd) {
		y += _spd;
		for (int i = 0; i < tile.length; i++) {
			if (tile[i].reset > 0) {
				tile[i].reset += 1;
				if (tile[i].reset > 10) {
					GameRenderer.mStart.mGR.root.setAnimation(-.888f + i* .222f, y);
					tile[i].no = tile[i].reset = 0;
				}
			}
		}
	}
}
