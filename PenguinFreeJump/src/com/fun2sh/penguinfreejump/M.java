package com.fun2sh.penguinfreejump;
import java.util.Random;
import android.media.MediaPlayer;

public class M {
	public static boolean setValue = true;
	
	public static int TIME = 20000;
	public static int ROW = 50;
	public static int GameScreen;
	public static final int GAMELOGO = 0, GAMEMENU = 3, GAMESPLASH = 4;
	public static final int GAMEOVER = 8, GAMEPAUSE = 9, GAMESURVIVAL = 10;
	public static final int GAMEPLAY = 5,GAMEWIN = 11;
	
	public static float ScreenWidth, ScreenHieght;
	public static final float mMaxX = 480, mMaxY = 800;
	public static float SPD = -.05f;
	public static final float DIS = 0.7f;

	public static Random mRand = new Random();
	
	public static final String MARKET = "https://play.google.com/store/apps/developer?id=htt+games";
	public static final String LINK = "market://details?id=";
	public static final String SHARELINK = "https://play.google.com/store/apps/details?id=";
	
	private static MediaPlayer mp = null;
	
	private static MediaPlayer mpEffect = null, mpEffect2 = null;
	private static MediaPlayer mpEffect3 = null, mpEffect4 = null;
	private static MediaPlayer mpEffect6 = null, mpEffect7 = null;
	private static MediaPlayer mpEffect9 = null, mpEffect10 = null;
	private static MediaPlayer mpEffect11 = null, mpEffect12 = null;
	private static MediaPlayer mpEffect14 = null, mpEffect15 = null;
	private static MediaPlayer mpEffect5 = null, mpEffect13 = null;
	private static MediaPlayer mpEffect8 = null;
	
	public static final int ACHIV[] = { R.string.achievement_smart_penguin,
			R.string.achievement_master_penguin, R.string.achievement_jump_penguin,
			R.string.achievement_fast_penguin, R.string.achievement_king_penguin };

	public static void play(int resource) {
		try {
			stopplay();
			if (setValue) {
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

	public static void stopplay() {
		if (mp != null) {
			mp.stop();
			mp.release();
			mp = null;
		}
	}

	public static void stop() {
		try {
			if (mp != null) {
				mp.stop();
				mp.release();
				mp = null;
			}
			if (mpEffect != null) {
				mpEffect.stop();
				mpEffect.release();
				mpEffect = null;
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
			if (mpEffect9 != null) {
				mpEffect9.stop();
				mpEffect9.release();
				mpEffect9 = null;
			}
			if (mpEffect10 != null) {
				mpEffect10.stop();
				mpEffect10.release();
				mpEffect10 = null;
			}
			if (mpEffect11 != null) {
				mpEffect11.stop();
				mpEffect11.release();
				mpEffect11 = null;
			}
			if (mpEffect12 != null) {
				mpEffect12.stop();
				mpEffect12.release();
				mpEffect12 = null;
			}
			if (mpEffect13 != null) {
				mpEffect13.stop();
				mpEffect13.release();
				mpEffect13 = null;
			}
			if (mpEffect14 != null) {
				mpEffect14.stop();
				mpEffect14.release();
				mpEffect14 = null;
			}
			if (mpEffect15 != null) {
				mpEffect15.stop();
				mpEffect15.release();
				mpEffect15 = null;
			}

		} catch (Exception e) {
		}
	}

	public static void sound1(int resource) {
		if (!setValue)
			return;
		try {
			if (mpEffect == null) {
				mpEffect = MediaPlayer.create(GameRenderer.mContext, resource);
			}
			if (!mpEffect.isPlaying())
				mpEffect.start();
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
		} catch (Exception e) {
		}
	}

	public static void sound3(int resource) {
		if (!setValue)
			return;
		try {
			if (setValue) {
				if (mpEffect3 == null)
					mpEffect3 = MediaPlayer.create(GameRenderer.mContext,
							resource);
				if (!mpEffect3.isPlaying()) {
					mpEffect3.start();
				} else
					sound5(R.drawable.jump);
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
			// else
			// sound5(GameRenderer.mContext,R.drawable.b5);
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

	public static void sound9(int resource) {
		if (!setValue)
			return;
		try {
			if (mpEffect9 == null) {
				mpEffect9 = MediaPlayer.create(GameRenderer.mContext, resource);
			}
			if (!mpEffect9.isPlaying() && setValue)
				mpEffect9.start();
			else
				sound10(R.drawable.landing);
		} catch (Exception e) {
		}
	}

	public static void sound10(int resource) {
		if (!setValue)
			return;
		try {
			if (mpEffect10 == null) {
				mpEffect10 = MediaPlayer
						.create(GameRenderer.mContext, resource);
			}
			if (!mpEffect10.isPlaying() && setValue)
				mpEffect10.start();
			else
				sound11(R.drawable.landing);
		} catch (Exception e) {
		}
	}

	public static void sound11(int resource) {
		if (!setValue)
			return;
		try {
			if (mpEffect11 == null) {
				mpEffect11 = MediaPlayer
						.create(GameRenderer.mContext, resource);
			}
			if (!mpEffect11.isPlaying() && setValue)
				mpEffect11.start();
			else
				sound12(R.drawable.landing);
		} catch (Exception e) {
		}
	}

	public static void sound12(int resource) {
		if (!setValue)
			return;
		try {
			if (mpEffect12 == null) {
				mpEffect12 = MediaPlayer
						.create(GameRenderer.mContext, resource);
			}
			if (!mpEffect12.isPlaying() && setValue)
				mpEffect12.start();
		} catch (Exception e) {
		}
	}

	public static void sound13(int resource) {
		if (!setValue)
			return;
		try {
			if (mpEffect13 == null) {
				mpEffect13 = MediaPlayer
						.create(GameRenderer.mContext, resource);
			}
			if (!mpEffect13.isPlaying() && setValue)
				mpEffect13.start();
		} catch (Exception e) {
		}
	}

	public static void sound14(int resource) {
		if (!setValue)
			return;
		try {
			if (mpEffect14 == null) {
				mpEffect14 = MediaPlayer
						.create(GameRenderer.mContext, resource);
			}
			if (!mpEffect14.isPlaying() && setValue)
				mpEffect14.start();
		} catch (Exception e) {
		}
	}

	public static void sound15(int resource) {
		if (!setValue)
			return;
		try {
			if (mpEffect15 == null) {
				mpEffect15 = MediaPlayer
						.create(GameRenderer.mContext, resource);
			}
			if (!mpEffect15.isPlaying() && setValue)
				mpEffect15.start();
		} catch (Exception e) {
		}
	}

	public static void playvoice() {
		switch (mRand.nextInt(3)) {
		case 1:
			sound13(R.drawable.kid1);
			break;
		case 2:
			sound14(R.drawable.kid2);
			break;
		default:
			sound15(R.drawable.kid3);
			break;
		}

	}

}
