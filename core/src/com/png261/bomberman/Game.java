package com.png261.bomberman;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.png261.bomberman.screen.*;

public class Game extends com.badlogic.gdx.Game
{
    public static volatile Game instance;

    private final int width = 1280;
    private final int height = 768;
    private SpriteBatch batch;

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
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
