package com.png261.bomberman.util;

public final class Stopwatch
{
    private long startMark;
    private long stopMark;
    private long pausedMark;
    private boolean isRunning;
    private boolean isPaused;

    public Stopwatch()
    {
        startMark = 0;
        stopMark = 0;
        pausedMark = 0;
        isRunning = false;
        isPaused = false;
    }

    public void start()
    {
        if (isRunning()) {
            return;
        }

        startMark = System.nanoTime();
        stopMark = 0;
        pausedMark = 0;
        isRunning = true;
        isPaused = false;
    }

    public void stop()
    {
        if (!isRunning()) {
            return;
        }

        stopMark = System.nanoTime();
        isRunning = false;
        isPaused = false;
    }

    public void restart()
    {
        stop();
        start();
    }

    public void pause()
    {
        if (!isRunning() || isPaused()) {
            return;
        }

        isRunning = false;
        isPaused = true;
        pausedMark = (System.nanoTime() - startMark);
    }

    public void resume()
    {
        if (isRunning() || !isPaused()) {
            return;
        }

        isRunning = true;
        isPaused = false;
        startMark = System.nanoTime() - pausedMark;
        pausedMark = 0;
    }

    public boolean isRunning() { return isRunning; }

    public boolean isPaused() { return isPaused; }

    public long currentTime() { return System.nanoTime() - startMark; }

    public long delta()
    {
        if (isRunning()) {
            return currentTime();
        }

        if (isPaused()) {
            return pausedMark;
        }

        if (startMark == 0) {
            return 0;
        }

        return stopMark - startMark;
    }
}
