package com.png261.bomberman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.png261.bomberman.level.Level;
import com.png261.bomberman.manager.GameStateManager;
import com.png261.bomberman.states.MainMenuState;

public final class Game extends com.badlogic.gdx.Game {
    private static volatile Game instance;

    private final int WIDTH = 1280;
    private final int HEIGHT = 768;

    private SpriteBatch batch;
    private Level level;
    private Cursor cursor;

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public void create() {
        batch = new SpriteBatch();

        final Pixmap pixmap = new Pixmap(Gdx.files.internal("image/cursor/cursor-normal.png"));
        cursor = Gdx.graphics.newCursor(pixmap, 15, 15);
        pixmap.dispose();
        Gdx.graphics.setCursor(cursor);

        GameStateManager.getInstance().init(this);
        GameStateManager.getInstance().changeState(new MainMenuState());
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

    public void setLevel(final Level level) {
        this.level = level;
    }

    public Level level() {
        return level;
    }
}
