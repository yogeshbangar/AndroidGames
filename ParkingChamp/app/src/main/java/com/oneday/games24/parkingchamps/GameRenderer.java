package com.oneday.games24.parkingchamps;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.google.android.gms.ads.AdView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class GameRenderer implements Renderer {
    final Group root;
    static Context mContext;
    public static Start mStart;
    long startTime = System.currentTimeMillis();
    int resumeCounter = 0;
    int mSel = 0;

    SimplePlane mTex_Logo, mTex_GameBG, mTex_8x8, mTex_Road, mTex_Wheel, mTex_CarLight, mTex_Glow;
    SimplePlane[] mTex_Opp, mTex_Char, mTex_Meter, mTex_Font, mTex_Font2, mTex_Car, mTex_Blast, mTex_Sound, mTex_Vibrate;
    SimplePlane mTex_AboutUs, mTex_Back, mTex_Exit, mTex_Help, mTex_Level_next, mTex_Menu, mTex_More;
    SimplePlane mTex_Next, mTex_Paused, mTex_PC, mTex_Rate_us, mTex_Retry, mTex_HelpScr, mTex_TimeScore, mTex_GPPlus;
    SimplePlane mTex_Setting, mTex_LevelBox, mTex_LevelLock, mTex_LevelSStar, mTex_Loading, mTex_Facebook;
    SimplePlane mTex_About_SCR, mTex_AboutUs_SCR, mTex_Board, mTex_Congratulation, mTex_GameOver, mTex_GamePaused;
    SimplePlane mTex_LevelComplete, mTex_LevelStar, mTex_SetTex, mTex_Starboard, mTex_String, mTex_Speed, mTex_Splash;
    SimplePlane mTex_Pointer, mTex_HighBar, mTex_Skip; //AdHouse

    int mLevel = 1;
    int unLock = 6;
    int unStar[] = new int[18];
    int mPage = 0;
    float levelWinS1 = 5;
    float levelWinS2 = 5;
    float levelWinS3 = 5;

    long GameTime = System.currentTimeMillis();

    Pixel mPixel[];
    Pixel mRPixel[];
    Animation ani[];//christmasChange

    Player mPlayer;
    Opponent[] mOpponent;

    public GameRenderer(Context context) {
        mContext = context;
        mStart = (Start) mContext;
        root = new Group(this);
        init();
    }

    void init() {
        try {
            mTex_Pointer = add("pointer.png");//AdHouse
            mTex_HighBar = add("hightbar0.png");//AdHouse
            mTex_Skip = add("exit_icon.png");//AdHouse

            mTex_GameBG = add("levels/bg/l11.png");
            mTex_Logo = add("logo.png");
            mTex_Road = add("levels/road.png");
            mTex_8x8 = add("smoke2.png");
            mTex_Wheel = addRotate("pahiye.png");
            mTex_AboutUs = add("UI/button/aboutus.png");
            mTex_Back = add("UI/button/back.png");
            mTex_Exit = add("UI/button/exit.png");
            mTex_Help = add("UI/button/help.png");
            mTex_Facebook = add("UI/button/social_facebook_box_blue.png");
            mTex_GPPlus = add("UI/button/google-plus-icon.png");

            mTex_HelpScr = add("UI/Help.png");
            mTex_TimeScore = add("UI/time-n-score.png");
//			mTex_Home			= add("UI/button/home.png");
            mTex_Level_next = addRotate("UI/button/level_next.png");
            mTex_Menu = add("UI/button/menu.png");
            mTex_More = add("UI/button/more.png");
            mTex_Vibrate = new SimplePlane[2];
            mTex_Vibrate[0] = add("UI/button/vibration-off.png");
            mTex_Vibrate[1] = add("UI/button/vibration.png");
            mTex_Next = add("UI/button/next.png");
            mTex_Paused = add("UI/button/pausad.png");
            mTex_PC = add("UI/button/play_continue.png");
            mTex_Rate_us = add("UI/button/rate-us.png");
            mTex_Retry = add("UI/button/retry.png");
            mTex_Setting = add("UI/button/Setting.png");
//			mTex_Shere			= add("UI/button/shere.png");
            mTex_Sound = new SimplePlane[2];
            mTex_Sound[0] = add("UI/button/sound-off.png");
            mTex_Sound[1] = add("UI/button/sound-on.png");

            mTex_LevelBox = add("UI/levelselections/level-box.png");
            mTex_LevelLock = add("UI/levelselections/level-lock.png");
            mTex_LevelSStar = add("UI/levelselections/level_selection_star.png");
            mTex_Loading = add("UI/loading2.png");


            mTex_About_SCR = add("UI/menu/about.png");
            mTex_AboutUs_SCR = add("UI/menu/About-Us.png");
            mTex_Board = add("UI/menu/board.png");
            mTex_Congratulation = add("UI/menu/congratulation.png");
            mTex_GameOver = add("UI/menu/game-over.png");
            mTex_GamePaused = add("UI/menu/game-paused.png");
            mTex_LevelComplete = add("UI/menu/Level-Complete.png");
            mTex_LevelStar = add("UI/menu/Level-ster.png");
            mTex_SetTex = add("UI/menu/Setting.png");
            mTex_Starboard = add("UI/menu/star-board.png");
            mTex_Splash = add("UI/splesh.jpg");

            mTex_String = addRotate("box/steering.png");
            mTex_Speed = addRotate("box/gair.png");
            mTex_Car = new SimplePlane[4];
            for (int i = 0; i < mTex_Car.length; i++)
                mTex_Car[i] = addRotate("car/" + i + ".png");

            mTex_CarLight = add("car/carlight.png");
            mTex_Glow = addRotate("car/car_glow.png");

            mTex_Opp = new SimplePlane[4];
            for (int i = 0; i < mTex_Opp.length; i++)
                mTex_Opp[i] = addRotate("opp/" + i + ".png");


            mTex_Char = new SimplePlane[8];
            Bitmap b = LoadImgfromAsset("chereter.png");
            for (int i = 0; i < mTex_Char.length; i++)
                mTex_Char[i] = addBitRotate(Bitmap.createBitmap(b, i * b.getWidth() / mTex_Char.length, 0, b.getWidth() / mTex_Char.length, b.getHeight(), null, true));

            b.recycle();
            mTex_Blast = new SimplePlane[7];
            b = LoadImgfromAsset("Blast.png");
            for (int i = 0; i < mTex_Blast.length; i++)
                mTex_Blast[i] = addBitRotate(Bitmap.createBitmap(b, i * b.getWidth() / mTex_Blast.length, 0, b.getWidth() / mTex_Blast.length, b.getHeight(), null, true));


            mTex_Meter = new SimplePlane[8];
            for (int i = 0; i < mTex_Meter.length; i++)
                mTex_Meter[i] = add("meter/" + i + ".png");

            load_Font();
            mPlayer = new Player();
            mRPixel = new Pixel[4];
            for (int i = 0; i < mRPixel.length; i++)
                mRPixel[i] = new Pixel(0, 0);

            mOpponent = new Opponent[6];
            for (int i = 0; i < mOpponent.length; i++)
                mOpponent[i] = new Opponent();

            ani = new Animation[200];
            for (int i = 0; i < ani.length; i++) {
                ani[i] = new Animation();
            }
            for (int i = 0; i < unStar.length; i++) {
                unStar[i] = 0;
            }
//			gameReset();


        } catch (Exception e) {
        }

    }

    void loading() {
        M.GameScreen = M.GAMELOADING;
        root.sx = -1;
        root.Counter = 0;
    }

    void gameReset() {
//		mLevel = 10;
        M.GameScreen = M.GAMELOADING;
        for (int i = 0; i < mOpponent.length; i++)
            mOpponent[i].set(-100, -100, 0, 0, 0);
        switch (mLevel) {
            case 1:
                mPlayer.set(0, .4f, -90);
                break;
            case 2:
                mPlayer.set(0, .4f, -00);
                break;
            case 3:
                mPlayer.set(0.8f, .4f, 180);
                break;
            case 4:
                mPlayer.set(0.57f, -.7f, 90);
                break;
            case 5:
                mPlayer.set(0.92f, -.4f, 90);
                mOpponent[0].set(.43f, -.8f, 0, .01f, 1);
                break;
            case 6:
                mPlayer.set(-.40f, -.8f, 90);
                break;
            case 7:
                mPlayer.set(-.40f, -.70f, 00);
                mOpponent[0].set(-.83f, 0.48f, 0.01f, 0, 2);
                break;
            case 8:
                mPlayer.set(-.80f, 0.64f, 00);
                mOpponent[0].set(-.83f, 0.40f, -.01f, 0, 3);
//			mOpponent[1].set(-.83f,-.37f, 0.01f, 0, 2);
                break;
            case 9:
                mPlayer.set(-.80f, 0.40f, -90);
                mOpponent[0].set(0.70f, 0.40f, 0, 0.01f, 1);
                mOpponent[1].set(-.47f, 0.40f, 0, -.01f, 4);
                break;
            case 10:
                mPlayer.set(-.65f, 0.10f, -90);
//			mOpponent[0].set(0.70f,-.62f, -.01f, 0.00f, 3);
//			mOpponent[1].set(0.63f,0.40f, 0.00f, -.01f, 4);
                break;
            case 11:
                mPlayer.set(0.85f, 0.60f, 230);
//			mPlayer.set(0.30f, -.70f,0);
                mOpponent[0].set(0.70f, -.82f, -.01f, 0.00f, 3);
                mOpponent[1].set(0.18f, -.40f, 0.00f, -.01f, 5);
                break;
            case 12:
                mPlayer.set(0.45f, -.84f, 180);
                mOpponent[0].set(0.70f, -.60f, -.01f, 0.00f, 3);
                mOpponent[1].set(-.18f, 0.20f, 0.01f, -.00f, 6);
                mOpponent[2].set(-.68f, -.30f, 0.01f, -.00f, 7);
                break;
            case 13:
                mPlayer.set(0.85f, -.30f, 90);
                mOpponent[0].set(-.82f, -.60f, 0.00f, 0.01f, 1);
                mOpponent[1].set(0.70f, -.60f, 0.00f, -.01f, 4);
                mOpponent[2].set(-.50f, 0.70f, 0.005f, -.005f, 8);
                mOpponent[3].set(0.58f, -.70f, 0.005f, -.005f, 9);
                break;
            case 14:
                mPlayer.set(-.15f, 0.30f, 00);
                mOpponent[0].set(-.77f, -.60f, 0.00f, 0.01f, 1);
                mOpponent[1].set(0.67f, -.60f, 0.00f, -.01f, 4);
                mOpponent[2].set(0.30f, 0.30f, 0.005f, -.008f, 10);
                mOpponent[3].set(0.50f, -.20f, 0.005f, -.005f, 11);
                break;
            case 15:
                mPlayer.set(-.25f, 0.30f, 00);
//			mOpponent[0].set(-.77f,-.63f, 0.01f, 0.00f, 2);
                mOpponent[1].set(-.70f, 0.47f, 0.05f, -.00f, 12);
                mOpponent[2].set(-.70f, -.20f, -.05f, -.00f, 12);
                break;
            case 16:
                mPlayer.set(0.85f, 0.10f, 180);
//			mPlayer.set(0.35f, 0.80f,000);
                mOpponent[0].set(0.40f, -.00f, -.00f, -.005f, 13);
                break;
            case 17:
                mPlayer.set(-.85f, 0.50f, -90);
                mOpponent[0].set(-.73f, -.50f, 0.00f, 0.01f, 1);
                mOpponent[1].set(-.06f, 0.50f, 0.00f, -.01f, 4);
                mOpponent[2].set(0.06f, 0.50f, 0.00f, 0.01f, 1);
                mOpponent[3].set(0.73f, -.50f, 0.00f, -.01f, 4);
                mOpponent[4].set(-.30f, 0.40f, 0.005f, -.000f, 14);
                mOpponent[5].set(0.50f, -.40f, 0.005f, -.000f, 15);
                break;
            case 18:
                mPlayer.set(-.45f, 0.90f, 180);
//			mPlayer.set(-.0f, 0.80f,180);
                mOpponent[0].set(0.86f, 0.50f, 0.00f, -.01f, 4);
                mOpponent[1].set(-.60f, 0.20f, 0.000f, -.005f, 16);
                mOpponent[2].set(-.30f, -.40f, 0.005f, -.008f, 17);
                mOpponent[3].set(0.70f, -.00f, 0.005f, -.005f, 18);
                break;

        }
        getArr(mLevel);
        GameTime = System.currentTimeMillis();

        levelWinS1 = 5;
        levelWinS2 = 5;
        levelWinS3 = 5;

        if (ads % 3 == 0)
            mStart.load();
        ads++;
    }

    int ads = 0;


    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(50/255f, 52/255f, 100/255f, 0.0f);
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glClearDepthf(1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
    }

    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        root.draw(gl);
        long dt = System.currentTimeMillis() - startTime;
        if (dt < 33)
            try {
                Thread.sleep(33 - dt);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        startTime = System.currentTimeMillis();
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void onAccelerationChanged(float x, float y, float z) {

        //Log.d("----------------=>  "+x,y+"   -----------    "+z);
    }

    public void onShake(float force) {
    }

    SimplePlane add(String ID) {
        SimplePlane SP = null;
        Bitmap b = LoadImgfromAsset(ID);
        try {
            SP = new SimplePlane((b.getWidth() / M.mMaxX), (b.getHeight() / M.mMaxY));
            SP.loadBitmap(b);// R.drawable.jay
        } catch (Exception e) {
        }
        return SP;
    }

    SimplePlane addBitmap(Bitmap b) {
        SimplePlane SP = null;
        try {
            SP = new SimplePlane((b.getWidth() / M.mMaxX), (b.getHeight() / M.mMaxY));
            SP.loadBitmap(b);// R.drawable.jay
        } catch (Exception e) {
        }
        return SP;
    }

    Bitmap LoadImgfromAsset(String ID) {
        try {
            return BitmapFactory.decodeStream(mContext.getAssets().open(ID));
        } catch (Exception e) {
            //Log.d(""+ID,"!!!!!!!!!!!!!!!!!!!!!~~~~~~~~~~~~~!!!!!!!!!!!!!!!!!!!!!!!!!"+e.getMessage());
            return null;
        }
    }

    Bitmap resizeImg(Bitmap bitmapOrg, int newWidth, int newHeight) {
        int width = bitmapOrg.getWidth();
        int height = bitmapOrg.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        matrix.postRotate(0);
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, width, height, matrix, true);
        Log.d("resizeImg========", "newWidth [" + newWidth + "] newHeight [" + newHeight + "]");
        return resizedBitmap;
    }

    Bitmap FlipHorizontal(Bitmap bitmapOrg) {
        Matrix matrix = new Matrix();
        matrix.postScale(-1f, 1f);
        matrix.postRotate(0);
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, bitmapOrg.getWidth(), bitmapOrg.getHeight(), matrix, true);
        return resizedBitmap;
    }

    SimplePlane addRotate(String ID) {
        SimplePlane SP = null;
        Bitmap b = LoadImgfromAsset(ID);
        try {
            SP = new SimplePlane((b.getWidth() / M.mMaxY), (b.getHeight() / M.mMaxY));
            SP.loadBitmap(b);// R.drawable.jay
        } catch (Exception e) {
        }
        return SP;
    }

    SimplePlane addBitRotate(Bitmap b) {
        SimplePlane SP = null;
        try {
            SP = new SimplePlane((b.getWidth() / M.mMaxY), (b.getHeight() / M.mMaxY));
            SP.loadBitmap(b);// R.drawable.jay
        } catch (Exception e) {
        }
        return SP;
    }


    float getX(float a) {
        float c = ((a / M.mMaxX) - 0.5f) * 2;
        return c;
    }

    float getY(float a) {
        float c = ((a / M.mMaxY) - 0.5f) * (-2);
        return c;
    }

    void getArr(int i) {
        int x = 0, y = 0;
        int mTotalPixel = 0, mcolor;
        mTex_GameBG = add("levels/bg/l" + i + ".png");
        Bitmap img = LoadImgfromAsset("levels/boder/b" + i + ".png");
        mTotalPixel = getPixel(img);
        mPixel = new Pixel[mTotalPixel - 4];
        int ct = 0;
        mTotalPixel = 0;
        for (y = 0; y < img.getHeight(); y++) {
            for (x = 0; x < img.getWidth(); x++) {
                mcolor = img.getPixel(x, y);
                if (mcolor != 0) {
                    if (mcolor == -65536) {
                        if (mRPixel.length > ct) {
                            mRPixel[ct++].set(getX(x), getY(y));
//							System.out.println(x+"~~~~~~~~~~~~~~~~!!~~~~~~~~~~~~  "+y);
                        }
                    } else {
                        if (mPixel.length <= mTotalPixel)
                            break;
                        mPixel[mTotalPixel] = new Pixel(getX(x), getY(y));
                        mTotalPixel++;
                    }
                }
            }
        }
    }

    int getPixel(Bitmap img) {
        int x = 0, y = 0;
        int mcolor = 0;
        int mTotalPixel = 0;
        for (y = 0; y < img.getHeight(); y++) {
            for (x = 0; x < img.getWidth(); x++) {
                mcolor = img.getPixel(x, y);
                if (mcolor != 0) {
                    mTotalPixel++;
                }
            }
        }
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~  "+mTotalPixel);
        return mTotalPixel;
    }

    void load_Font() {
        mTex_Font = new SimplePlane[11];
        Bitmap b = LoadImgfromAsset("meter/fontstrip.png");
        for (int i = 0; i < mTex_Font.length; i++)
            mTex_Font[i] = addBitmap(Bitmap.createBitmap(b, i * b.getWidth() / mTex_Font.length, 0, b.getWidth() / mTex_Font.length, b.getHeight(), null, true));

        b.recycle();
        mTex_Font2 = new SimplePlane[11];
        b = LoadImgfromAsset("UI/levelselections/menu_Numbars.png");
        for (int i = 0; i < mTex_Font2.length; i++)
            mTex_Font2[i] = addBitmap(Bitmap.createBitmap(b, i * b.getWidth() / mTex_Font2.length, 0, b.getWidth() / mTex_Font2.length, b.getHeight(), null, true));
    }


}
