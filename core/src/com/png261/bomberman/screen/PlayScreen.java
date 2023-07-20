package com.png261.bomberman.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.png261.bomberman.Game;
import com.png261.bomberman.level.Level;
import com.png261.bomberman.manager.MapManager;

public final class PlayScreen implements Screen
{
    private final int MAP_WIDTH = 17;
    private final int MAP_HEIGHT = 13;

    private OrthographicCamera camera;
    private Viewport viewport;
    private Level level;

    @Override public void show()
    {
        camera = new OrthographicCamera();
        viewport = new FitViewport(MAP_WIDTH, MAP_HEIGHT, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        level = new Level();
        Game.getInstance().setLevel(level);
        level.load("map1.tmx");
    }

    public void update(final float delta)
    {
        camera.update();
        level.update(delta);
    }

    @Override public void render(final float delta)
    {
        ScreenUtils.clear(Color.BLACK);

        update(delta);
        level.renderMap(camera);

        Game.getInstance().getBatch().setProjectionMatrix(camera.combined);
        Game.getInstance().getBatch().begin();

        level.renderObject();

        Game.getInstance().getBatch().end();
    }

    @Override public void resize(int width, int height) { viewport.update(width, height); }

    @Override public void dispose() {}

    @Override public void hide() {}

    @Override public void pause() {}

    @Override public void resume() {}
}
