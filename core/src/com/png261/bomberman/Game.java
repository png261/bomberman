package com.png261.bomberman;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.png261.bomberman.level.Level;
import com.png261.bomberman.screen.MainMenuScreen;
import com.png261.bomberman.screen.PlayScreen;

public final class Game extends com.badlogic.gdx.Game
{
    public static volatile Game instance;

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 768;

    private SpriteBatch batch;
    private Level level;

    public static Game getInstance()
    {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    @Override public void create()
    {
        batch = new SpriteBatch();
        this.setScreen(new PlayScreen());
    }

    @Override public void dispose() { super.dispose(); }

    public SpriteBatch getBatch() { return batch; }

    public int getWidth() { return WIDTH; }

    public int getHeight() { return HEIGHT; }

    public void setLevel(Level level) { this.level = level; }

    public Level getLevel() { return level; }
}
