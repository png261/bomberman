package com.png261.bomberman.manager;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Disposable;

public final class MapManager implements Disposable {
    private final float UNIT_SCALE = 1 / 16f;

    private final TmxMapLoader loader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    public MapManager() {
        loader = new TmxMapLoader();
    }

    public void load(final String filePath) {
        map = loader.load(filePath);
        renderer = new OrthogonalTiledMapRenderer(map, UNIT_SCALE);
    }

    public void render(final OrthographicCamera camera) {
        renderer.setView(camera);
        renderer.render();
    }

    public TiledMap map() {
        return map;
    }

    @Override
    public void dispose() {
    }
}
