package com.png261.bomberman.game;

import com.png261.bomberman.*;
import com.png261.bomberman.gameObject.*;
import com.png261.bomberman.gameObject.person.*;
import com.png261.bomberman.graphic.*;
import com.png261.bomberman.input.*;
import com.png261.bomberman.manager.*;
import com.png261.bomberman.util.*;

public final class Game extends Thread
{
    private static volatile Game instance;

    private boolean isRunning = true;
    private Window window;
    private final Stopwatch framerateStopwatch;
    private final long frameDelay = 0;
    private final Cursor cursor;

    private Player player;

    private Game()
    {
        framerateStopwatch = new Stopwatch();
        cursor = new Cursor();
        player = new Player();
        player.load(new LoaderParams(new Vector2D(0f, 0f), 100, 100));
    }

    public static Game getInstance()
    {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public void init(int width, int height, String title)
    {
        this.window = new Window(width, height, title);
        InputManager.getInstance().init(window.getPanel());
        TextureManager.getInstance().init(window);
        TextureManager.getInstance().load("test", "images/cat.jpg");
        SoundManager.getInstance().loadMusic("background", "sounds/background.wav");
        SoundManager.getInstance().playMusic("background");
    }

    @Override public void run()
    {
        while (this.isRunning) {
            this.update();
            this.handleEvents();
            this.render();
            this.handleFPS();
        }
        this.clean();
    }

    public void update() { player.update(); }

    public void handleEvents()
    {
        if (InputManager.getInstance().isKeyDown(KeyDefs.KEY_Q)) {
            Log.info("q");
            quit();
        }
    }
    public void render()
    {
        this.window.clear();

        TextureManager.getInstance().draw("test", 10, 100, 50, 50);
        player.draw();
        cursor.draw();

        this.window.refresh();
    }

    public void quit() { this.isRunning = false; }
    public void clean() { System.exit(0); }
    public Window getWindow() { return this.window; }

    private void handleFPS()
    {
        final long delta = framerateStopwatch.delta();
        if (delta < frameDelay) {
            try {
                Thread.sleep(frameDelay - delta);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        framerateStopwatch.restart();
    }
}
