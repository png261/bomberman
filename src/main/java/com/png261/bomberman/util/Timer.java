package com.png261.bomberman.util;

import com.png261.bomberman.util.Stopwatch;

public final class Timer
{
    private long time;
    private boolean isDone;
    private final Stopwatch stopwatch;

    public Timer()
    {
        time = 0;
        isDone = false;
        stopwatch = new Stopwatch();
    }

    public Timer(long time)
    {
        this.time = time;
        isDone = false;
        stopwatch = new Stopwatch();
    }

    public void setTime(long time) { this.time = time; }

    public void start() { stopwatch.start(); }

    public void restart() { stopwatch.restart(); }

    public boolean isDone()
    {
        if (stopwatch.delta() >= time) {
            stopwatch.stop();
            return true;
        }
        return false;
    }

    public boolean isRunning() { return stopwatch.isRunning(); }
}
