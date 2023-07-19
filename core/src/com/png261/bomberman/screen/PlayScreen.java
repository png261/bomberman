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
import com.png261.bomberman.manager.MapManager;
import com.png261.bomberman.physic.PhysicManager;
import com.png261.bomerberman.object.ObjectManager;

public class PlayScreen implements Screen
{
    private OrthographicCamera camera;
    private Viewport viewport;
    private PhysicManager PhysicManager;
    private MapManager mapManager;
    private ObjectManager objectManager;

    @Override public void show()
    {
        PhysicManager.getInstance().setDebug(true);

        camera = new OrthographicCamera();
        viewport = new FitViewport(17, 13, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        mapManager = new MapManager();
        mapManager.load("map1.tmx");

        objectManager = new ObjectManager();
        objectManager.load(mapManager.getMap());
    }

    public void update(final float delta)
    {
        camera.update();
        PhysicManager.getInstance().update();
        objectManager.update(delta);
    }

    @Override public void render(final float delta)
    {
        update(delta);

        ScreenUtils.clear(Color.BLACK);
        mapManager.render(camera);
        PhysicManager.getInstance().debugDraw(camera);

        Game.getInstance().getBatch().setProjectionMatrix(camera.combined);
        Game.getInstance().getBatch().begin();
        objectManager.render();
        Game.getInstance().getBatch().end();
    }

    @Override public void resize(int width, int height) { viewport.update(width, height); }

    @Override public void dispose() {}
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}
}
