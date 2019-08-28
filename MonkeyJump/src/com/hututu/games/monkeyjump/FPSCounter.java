package com.hututu.games.monkeyjump;

public class FPSCounter {
    long startTime = System.nanoTime();
    int frames = 0;

    public void logFrame() {
        frames++;
        if(System.nanoTime() - startTime >= 1000000000) {
            System.out.println("FPSCounter "+ "fps: " + frames);
            frames = 0;
            startTime = System.nanoTime();
        }
    }
}