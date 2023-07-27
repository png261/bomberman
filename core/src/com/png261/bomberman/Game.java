package com.png261.bomberman;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.png261.bomberman.level.Level;
import com.png261.bomberman.screen.MainMenuScreen;

public final class Game extends com.badlogic.gdx.Game {
    private static volatile Game instance;

    private final int WIDTH = 1280;
    private final int HEIGHT = 768;

    private SpriteBatch batch;
    private Level level;

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        this.setScreen(new MainMenuScreen());
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    public SpriteBatch batch() {
        return batch;
    }

    public int width() {
        return WIDTH;
    }

    public int height() {
        return HEIGHT;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Level level() {
        return level;
    }
}
