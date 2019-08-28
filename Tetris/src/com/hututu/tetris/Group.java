package com.hututu.tetris;

import javax.microedition.khronos.opengles.GL10;
import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.widget.Toast;
import com.hututu.tetris.Shape.Tetrominoes;

public class Group extends Mesh {

	GameRenderer mGR = null;

	int Counter = 0;
	float sx, sy;

	public void setting() {
		float ud = .01f;
		switch (GameRenderer.mStart._keyCode) {
		case 1:
			sy -= ud;
			break;
		case 2:
			sy += ud;
			break;
		case 3:
			sx -= ud;
			break;
		case 4:
			sx += ud;
			break;
		}
		System.out.println(M.GameScreen + "~~~~~~~~~~~~~~~      " + sx+ "~~~~~~~~~~~~       " + sy);
	}

	public boolean Handle(MotionEvent event) {
		if (CircRectsOverlap(-.8f, 0.0f, .1f, .1f, screen2worldX(event.getX()),
				screen2worldY(event.getY()), .1f))
			GameRenderer.mStart._keyCode = 3;
		if (CircRectsOverlap(0.8f, 0.0f, .1f, .1f, screen2worldX(event.getX()),
				screen2worldY(event.getY()), .1f))
			GameRenderer.mStart._keyCode = 4;
		if (CircRectsOverlap(-.0f, -.8f, .1f, .1f, screen2worldX(event.getX()),
				screen2worldY(event.getY()), .1f))
			GameRenderer.mStart._keyCode = 1;
		if (CircRectsOverlap(0.0f, 0.8f, .1f, .1f, screen2worldX(event.getX()),
				screen2worldY(event.getY()), .1f))
			GameRenderer.mStart._keyCode = 2;
		if (event.getAction() == MotionEvent.ACTION_UP)
			GameRenderer.mStart._keyCode = 0;
		return true;
	}

	@Override
	public void draw(GL10 gl) {
		DrawTexture(gl, mGR.mTex_Logo, 0, 0);
		GamePlay(gl);
		Counter++;
	}

	public boolean TouchEvent(MotionEvent event) {
		Handle_GamePlay(event);
		return true;
	}

	final int BoardWidth = 10;
	final int BoardHeight = 22;
	boolean isFallingFinished = false;
	boolean isStarted = false;
	boolean isPaused = false;
	int numLinesRemoved = 0;
	int curX = 0;
	int curY = 0;
	// JLabel statusbar;
	Shape curPiece;
	Tetrominoes[] board;

	public void actionPerformed() {
		if (isFallingFinished) {
			isFallingFinished = false;
			newPiece();
		} else {
			oneLineDown();
		}
	}

	public Group(GameRenderer _GameRenderer) {
		mGR = _GameRenderer;

		// setFocusable(true);
		curPiece = new Shape();
		// timer = new Timer(400, this);
		board = new Tetrominoes[BoardWidth * BoardHeight];
		start();
		// statusbar = parent.getStatusBar();

		// addKeyListener(new TAdapter());
		clearBoard();
	}

	int squareWidth() {
		return (int) 1;// getSize().getWidth() / BoardWidth;
	}

	int squareHeight() {
		return (int) 1;// getSize().getHeight() / BoardHeight;
	}

	Tetrominoes shapeAt(int x, int y) {
		return board[(y * BoardWidth) + x];
	}

	public void start() {
		if (isPaused)
			return;

		isStarted = true;
		isFallingFinished = false;
		numLinesRemoved = 0;
		clearBoard();
		newPiece();
		// timer.start();
	}

	private void pause() {
		if (!isStarted)
			return;

		isPaused = !isPaused;
		// if (isPaused) {
		// timer.stop();
		// statusbar.setText("paused");
		// } else {
		// timer.start();
		// statusbar.setText(String.valueOf(numLinesRemoved));
		// }
		// repaint();
	}

	public void GamePlay(GL10 gl) {
		// super.paint(g);

		// Dimension size = getSize();
		int boardTop = 1;// (int) size.getHeight() - BoardHeight *
							// squareHeight();

		for (int i = 0; i < BoardHeight; ++i) {
			for (int j = 0; j < BoardWidth; ++j) {
				Tetrominoes shape = shapeAt(j, BoardHeight - i - 1);
				if (shape != Tetrominoes.NoShape)
					drawSquare(gl, 0 + j * squareWidth(), boardTop + i
							* squareHeight(), shape);
			}
		}

		if (curPiece.getShape() != Tetrominoes.NoShape) {
			for (int i = 0; i < 4; ++i) {
				int x = curX + curPiece.x(i);
				int y = curY - curPiece.y(i);
				drawSquare(gl, 0 + x * squareWidth(), boardTop+ (BoardHeight - y - 1) * squareHeight(),curPiece.getShape());
//				System.out.print("[" + (0 + x * squareWidth()) + "," + (boardTop + (BoardHeight - y - 1) * squareHeight())+ "]");
			}
		}
		if (Counter % 5== 0)
		{
			if (move < -.1)
				tryMove(curPiece, curX - 1, curY);

			if (move > .1)
				tryMove(curPiece, curX + 1, curY);
		}
		if (Counter % 20 == 0) {
			actionPerformed();
		}
//		System.out.println("~~~~~~~~~~~~~~~~");
	}

	private void dropDown() {
		int newY = curY;
		while (newY > 0) {
			if (!tryMove(curPiece, curX, newY - 1))
				break;
			--newY;
		}
		pieceDropped();
	}

	private void oneLineDown() {
		if (!tryMove(curPiece, curX, curY - 1))
			pieceDropped();
	}

	private void clearBoard() {
		for (int i = 0; i < BoardHeight * BoardWidth; ++i)
			board[i] = Tetrominoes.NoShape;
	}

	private void pieceDropped() {
		for (int i = 0; i < 4; ++i) {
			int x = curX + curPiece.x(i);
			int y = curY - curPiece.y(i);
			board[(y * BoardWidth) + x] = curPiece.getShape();
		}

		removeFullLines();

		if (!isFallingFinished)
			newPiece();
	}

	private void newPiece() {
		curPiece.setRandomShape();
		curX = BoardWidth / 2 + 1;
		curY = BoardHeight - 1 + curPiece.minY();

		if (!tryMove(curPiece, curX, curY)) {
			curPiece.setShape(Tetrominoes.NoShape);
			// timer.stop();
			isStarted = false;
			// statusbar.setText("game over");
		}
	}

	private boolean tryMove(Shape newPiece, int newX, int newY) {
		for (int i = 0; i < 4; ++i) {
			int x = newX + newPiece.x(i);
			int y = newY - newPiece.y(i);
			if (x < 0 || x >= BoardWidth || y < 0 || y >= BoardHeight)
				return false;
			if (shapeAt(x, y) != Tetrominoes.NoShape)
				return false;
		}

		curPiece = newPiece;
		curX = newX;
		curY = newY;
		// repaint();
		return true;
	}

	private void removeFullLines() {
		int numFullLines = 0;

		for (int i = BoardHeight - 1; i >= 0; --i) {
			boolean lineIsFull = true;

			for (int j = 0; j < BoardWidth; ++j) {
				if (shapeAt(j, i) == Tetrominoes.NoShape) {
					lineIsFull = false;
					break;
				}
			}

			if (lineIsFull) {
				++numFullLines;
				for (int k = i; k < BoardHeight - 1; ++k) {
					for (int j = 0; j < BoardWidth; ++j)
						board[(k * BoardWidth) + j] = shapeAt(j, k + 1);
				}
			}
		}

		if (numFullLines > 0) {
			numLinesRemoved += numFullLines;
			// statusbar.setText(String.valueOf(numLinesRemoved));
			isFallingFinished = true;
			curPiece.setShape(Tetrominoes.NoShape);
			// repaint();
		}
	}

	private void drawSquare(GL10 gl, int x, int y, Tetrominoes shape) {
		DrawTexture(gl, mGR.mTex_Square, -.8f + x * mGR.mTex_Square.width(), 1
				- y * mGR.mTex_Square.Height());
//		System.out.print("[" + (-.8f + x * mGR.mTex_Square.width()) + ","
//				+ (1 - y * mGR.mTex_Square.Height()) + "]");

		/*
		 * Color colors[] = { new Color(0, 0, 0), new Color(204, 102, 102), new
		 * Color(102, 204, 102), new Color(102, 102, 204), new Color(204, 204,
		 * 102), new Color(204, 102, 204), new Color(102, 204, 204), new
		 * Color(218, 170, 0) };
		 * 
		 * Color color = colors[shape.ordinal()];
		 * 
		 * g.setColor(color); g.fillRect(x + 1, y + 1, squareWidth() - 2,
		 * squareHeight() - 2);
		 * 
		 * g.setColor(color.brighter()); g.drawLine(x, y + squareHeight() - 1,
		 * x, y); g.drawLine(x, y, x + squareWidth() - 1, y);
		 * 
		 * g.setColor(color.darker()); g.drawLine(x + 1, y + squareHeight() - 1,
		 * x + squareWidth() - 1, y + squareHeight() - 1); g.drawLine(x +
		 * squareWidth() - 1, y + squareHeight() - 1, x + squareWidth() - 1, y +
		 * 1);
		 */
	}

	float move = 0;
	float srt = 0;

	boolean Handle_GamePlay(MotionEvent event) {
		if (MotionEvent.ACTION_DOWN == event.getAction()) {
			srt = screen2worldX(event.getX());
			move = 0;
		}
		if (MotionEvent.ACTION_MOVE == event.getAction()) {
			move = screen2worldX(event.getX()) - srt;
		}
		if (MotionEvent.ACTION_UP == event.getAction()) {
			if (move > -.1 && move < .1)
				tryMove(curPiece.rotateRight(), curX, curY);
			move = srt = 0;
		}
		return true;
	}

	// class TAdapter extends KeyAdapter {

	public void keyPressed(MotionEvent e) {

		if (!isStarted || curPiece.getShape() == Tetrominoes.NoShape) {
			return;
		}

		/*
		 * int keycode = e.getKeyCode();
		 * 
		 * if (keycode == 'p' || keycode == 'P') { pause(); return; }
		 * 
		 * 
		 * if (isPaused) return;
		 * 
		 * switch (keycode) { case KeyEvent.VK_LEFT: tryMove(curPiece, curX - 1,
		 * curY); break; case KeyEvent.VK_RIGHT: tryMove(curPiece, curX + 1,
		 * curY); break; case KeyEvent.VK_DOWN: tryMove(curPiece.rotateRight(),
		 * curX, curY); break; case KeyEvent.VK_UP:
		 * tryMove(curPiece.rotateLeft(), curX, curY); break; case
		 * KeyEvent.VK_SPACE: dropDown(); break; case 'd': oneLineDown(); break;
		 * case 'D': oneLineDown(); break; }
		 */
	}

	// }

	void DrawTexture(GL10 gl, SimplePlane Tex, float x, float y) {
		Tex.drawPos(gl, x, y);
	}

	void DrawTextureR(GL10 gl, SimplePlane Tex, float x, float y, float angle) {
		Tex.drawRotet(gl, 0, 0, angle, x, y);
	}

	void DrawTextureS(GL10 gl, SimplePlane Tex, float x, float y, float scal) {
		Tex.drawScal(gl, x, y, scal, scal);
	}

	void DrawFlip(GL10 gl, SimplePlane Tex, float x, float y) {
		Tex.drawFilp(gl, x, y);
	}

	void DrawTransScal(GL10 gl, SimplePlane Tex, float x, float y, float z,
			float t) {
		Tex.drawTransprentScal(gl, x, y, z, t);
	}

	boolean CircRectsOverlap(double CRX, double CRY, double CRDX, double CRDY,
			double centerX, double centerY, double radius) {
		if ((Math.abs(centerX - CRX) <= (CRDX + radius))
				&& (Math.abs(centerY - CRY) <= (CRDY + radius)))
			return true;
		return false;

	}

	float screen2worldX(float a) {
		float c = ((a / M.ScreenWidth) - 0.5f) * 2;
		return c;
	}

	float screen2worldY(float a) {
		float c = ((a / M.ScreenHieght) - 0.5f) * (-2);
		return c;
	}

	boolean Rect2RectIntersection(float ax, float ay, float adx, float ady,
			float bx, float by, float bdx, float bdy) {
		ax -= adx / 2;
		ay += ady / 2;
		bx -= bdx / 2;
		by += bdy / 2;
		if (ax + adx > bx && ay - ady < by && bx + bdx > ax && by - bdy < ay) {
			return true;
		}
		return false;
	}

	boolean CirCir(double cx1, double cy1, double r1, double cx2, double cy2,
			double r2) {
		float bVectMag = (float) Math.sqrt(((cx1 - cx2) * (cx1 - cx2))
				+ ((cy1 - cy2) * (cy1 - cy2)));
		if (bVectMag < (r1 + r2))
			return true;
		return false;

	}

	double GetAngle(double d, double e) {
		if (d == 0)
			return e >= 0 ? Math.PI / 2 : -Math.PI / 2;
		else if (d > 0)
			return Math.atan(e / d);
		else
			return Math.atan(e / d) + Math.PI;
	}

	void drawNumber(GL10 gl, int no, float x, float y) {
		// float dx = mGR.mTex_Font[0].width();
		// String strs = ""+no;
		// for(int i =0;i<strs.length();i++)
		// {
		// int k = ((int)strs.charAt(i))-48;
		// if(k>=0 && k<mGR.mTex_Font.length)
		// mGR.mTex_Font[k].drawPos(gl,x+i*dx,y);
		// }
	}

	void RateUs() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.LINK + getClass().getPackage().getName()));
		GameRenderer.mContext.startActivity(mIntent);
	}

	void MoreGame() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse(M.MARKET));
		GameRenderer.mContext.startActivity(mIntent);
	}

	void Twitter() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://twitter.com/hututu_games"));
		GameRenderer.mContext.startActivity(mIntent);
	}

	void google() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://plus.google.com/+Hututugames"));
		GameRenderer.mContext.startActivity(mIntent);
	}

	void facebook() {
		Intent mIntent = new Intent(Intent.ACTION_VIEW);
		mIntent.setData(Uri.parse("https://www.facebook.com/HututuGames"));
		GameRenderer.mContext.startActivity(mIntent);
	}

	void share2friend() {
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("text/plain");
		i.putExtra(
				Intent.EXTRA_SUBJECT,
				"Roking new Game '"
						+ GameRenderer.mContext.getResources().getString(
								R.string.app_name) + "'");
		// i.putExtra(Intent.EXTRA_TEXT,"Let the battle commence!!! Download it now and let’s ROCK!!!!  "+M.PUBLICLINK+getClass().getPackage().getName());
		i.putExtra(Intent.EXTRA_TEXT,
				"Let the battle commence!!! Download it now and let’s ROCK!!!!  "
						+ M.SHARELINK + getClass().getPackage().getName());
		try {
			GameRenderer.mContext.startActivity(Intent.createChooser(i,
					"Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(GameRenderer.mStart,
					"There are no email clients installed.", Toast.LENGTH_SHORT)
					.show();
		}
	}
}
