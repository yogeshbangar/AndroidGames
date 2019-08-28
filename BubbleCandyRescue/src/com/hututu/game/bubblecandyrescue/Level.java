package com.hututu.game.bubblecandyrescue;

import java.io.IOException;
import java.io.InputStream;

public class Level {
	byte level[][][];
	public Level() {
		try {
			InputStream is = GameRenderer.mContext.getAssets().open("levels.txt");
			int size = is.available();
			byte[] levels = new byte[size];
			is.read(levels);
			is.close();
			level = new byte [100][][];
			LevelManager(levels, 0);
		} catch (IOException e) {
			// Should never happen.
			System.out.println("~~~~~~~~~~ "+e.toString());
			throw new RuntimeException(e);
		}
	}

	public void LevelManager(byte[] levels, int startingLevel) {
		String allLevels = new String(levels);
		int nextLevel = allLevels.indexOf("\n\n");
		if (nextLevel == -1 && allLevels.trim().length() != 0) {
			nextLevel = allLevels.length();
		}
		int count =0;
		while (nextLevel != -1) {
			String currentLevel = allLevels.substring(0, nextLevel).trim();
//			System.out.println(count+"~~~~~~~~~~ ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+nextLevel);
			level[count] =getLevel(currentLevel);// levelList.addElement(getLevel(currentLevel)); 
			count++;
			

			allLevels = allLevels.substring(nextLevel).trim();

			if (allLevels.length() == 0) {
				nextLevel = -1;
			} else {
				nextLevel = allLevels.indexOf("\n\n");

				if (nextLevel == -1) {
					nextLevel = allLevels.length();
				}
			}
		}
	}

	private byte[][] getLevel(String data) {
		byte[][] temp = new byte[M.C][M.Row];
		for (int j = 0; j < M.Row; j++) {
			for (int i = 0; i < 9; i++) {
				temp[i][j] = -1;
			}
		}
		int tempX = 0;
		int tempY = 0;
		for (int i = 0; i < data.length(); i++) {
			if (data.charAt(i) >= 48 && data.charAt(i) <= 55) {
				temp[tempX][tempY] = (byte) (data.charAt(i) - 48);
				tempX++;
			} else if (data.charAt(i) == 45) {
				temp[tempX][tempY] = -1;
				tempX++;
			}
			if (tempX == M.C) {
				tempY++;

				if (tempY == M.Row) {
					return temp;
				}
				tempX = tempY % 2;
			}
		}
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//		for (int j = 0; j < M.Row; j++) {
//			System.out.print("{");
//			for (int i = 0; i < M.C; i++) {
//				if (temp[i][j] > -1)
//					System.out.print(temp[i][j] + ",");
//				else
//					System.out.print(0 + ",");
//			}
//			System.out.println("}");
//		}
//		System.out.println("=============================================");
		return temp;
	}

}
