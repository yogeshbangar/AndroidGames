package com.htt.games.motospeedking;

import java.util.Random;
import android.media.MediaPlayer;

public class M {
	public static float SPD = -.01f;
	public static byte n =0;
	public static final String MARKET = "https://play.google.com/store/apps/developer?id=htt+games";
	public static final String LINK = "market://details?id=";
	public static final String SHARELINK = "https://play.google.com/store/apps/details?id=";

	public static Random mRand = new Random();
	public static int GameScreen;
	public static final int GAMELOGO = 0, GAMESETTING = 1, GAMEMENU = 3;
	public static final int GAMEPAUSE = 4, GAMEPLAY = 5, GAMEOVER = 8, BUT = 9;
	public static float ScreenWidth, ScreenHieght;
	public static final float mMaxX = 480, mMaxY = 854;
	private static MediaPlayer mp = null;
	public static boolean setValue = true;
	public static boolean setMusic = true;
	private static MediaPlayer mpEffect0 = null, mpEffect2 = null;
	private static MediaPlayer mpEffect3 = null, mpEffect4 = null;
	private static MediaPlayer mpEffect6 = null, mpEffect7 = null;
	private static MediaPlayer mpEffect8 = null, mpEffect5 = null;

	public static void play(int resource) {
		try {
			stop();
			if (setMusic) {
				if (mp == null)
					mp = MediaPlayer.create(GameRenderer.mContext, resource);

				if (!mp.isPlaying()) {
					if (resource != 2131099648)
						mp.setLooping(true);
					mp.start();
				}
			}
		} catch (Exception e) {
		}
	}

	public static void BGSTOP() {
		try {
			if (mp != null) {
				mp.stop();
				mp.release();
				mp = null;
			}
		} catch (Exception e) {
		}

	}
	public static void stop() {
		try {
			if (mp != null) {
				mp.stop();
				mp.release();
				mp = null;
			}
			if (mpEffect0 != null) {
				mpEffect0.stop();
				mpEffect0.release();
				mpEffect0 = null;
			}
			if (mpEffect2 != null) {
				mpEffect2.stop();
				mpEffect2.release();
				mpEffect2 = null;
			}
			if (mpEffect3 != null) {
				mpEffect3.stop();
				mpEffect3.release();
				mpEffect3 = null;
			}
			if (mpEffect4 != null) {
				mpEffect4.stop();
				mpEffect4.release();
				mpEffect4 = null;
			}
			if (mpEffect5 != null) {
				mpEffect5.stop();
				mpEffect5.release();
				mpEffect5 = null;
			}
			if (mpEffect6 != null) {
				mpEffect6.stop();
				mpEffect6.release();
				mpEffect6 = null;
			}
			if (mpEffect7 != null) {
				mpEffect7.stop();
				mpEffect7.release();
				mpEffect7 = null;
			}
			if (mpEffect8 != null) {
				mpEffect8.stop();
				mpEffect8.release();
				mpEffect8 = null;
			}

		} catch (Exception e) {
		}
	}

	public static void sound1(int resource) {
		if (!setValue)
			return;
		try {
			if (mpEffect0 == null) {
				mpEffect0 = MediaPlayer.create(GameRenderer.mContext, resource);
			}
			if (!mpEffect0.isPlaying())
				mpEffect0.start();
			// else
			// sound2(GameRenderer.mContext,R.drawable.g2);
		} catch (Exception e) {
		}
	}

	public static void sound2(int resource) {
		if (!setValue)
			return;
		try {
			if (mpEffect2 == null) {
				mpEffect2 = MediaPlayer.create(GameRenderer.mContext, resource);
			}
			if (!mpEffect2.isPlaying() && setValue)
				mpEffect2.start();
			// else
			// sound3Play(GameRenderer.mContext,R.drawable.g3);
		} catch (Exception e) {
		}
	}

	public static void sound3(int resource) {
		try {
			if (setValue) {
				if (mpEffect3 == null)
					mpEffect3 = MediaPlayer.create(GameRenderer.mContext,
							resource);
				if (!mpEffect3.isPlaying()) {
					mpEffect3.start();
				} else
					sound4(R.drawable.jump);
			}
		} catch (Exception e) {
		}
	}

	public static void sound4(int resource) {
		if (!setValue)
			return;
		try {
			if (mpEffect4 == null) {
				mpEffect4 = MediaPlayer.create(GameRenderer.mContext, resource);
			}
			if (!mpEffect4.isPlaying() && setValue)
				mpEffect4.start();
			else
				sound5(R.drawable.jump);
		} catch (Exception e) {
		}
	}

	public static void sound5(int resource) {
		if (!setValue)
			return;
		try {
			if (mpEffect5 == null) {
				mpEffect5 = MediaPlayer.create(GameRenderer.mContext, resource);
			}
			if (!mpEffect5.isPlaying() && setValue)
				mpEffect5.start();
			else
				sound6(R.drawable.jump);

		} catch (Exception e) {
		}
	}

	public static void sound6(int resource) {
		if (!setValue)
			return;
		try {
			if (mpEffect6 == null) {
				mpEffect6 = MediaPlayer.create(GameRenderer.mContext, resource);
			}
			if (!mpEffect6.isPlaying() && setValue)
				mpEffect6.start();
			else
				sound7(R.drawable.jump);
		} catch (Exception e) {
		}
	}

	public static void sound7(int resource) {
		if (!setValue)
			return;
		try {
			if (mpEffect7 == null) {
				mpEffect7 = MediaPlayer.create(GameRenderer.mContext, resource);
			}
			if (!mpEffect7.isPlaying() && setValue)
				mpEffect7.start();
			else
				sound8(R.drawable.jump);

		} catch (Exception e) {
		}
	}

	public static void sound8(int resource) {
		if (!setValue)
			return;
		try {
			if (mpEffect8 == null) {
				mpEffect8 = MediaPlayer.create(GameRenderer.mContext, resource);
			}
			if (!mpEffect8.isPlaying() && setValue)
				mpEffect8.start();
		} catch (Exception e) {
		}
	}
	public static final int ACHIV[] = {
		  R.string.achievement_classic,
		  R.string.achievement_sharp,
		  R.string.achievement_amazing,
		  R.string.achievement_master,
		  R.string.achievement_awesome
	};
}
