package com.png261.bomberman.manager;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public final class MapManager {
    private final float UNIT_SCALE = 1 / 16f;

    private TmxMapLoader loader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    public MapManager() {
        loader = new TmxMapLoader();
    }

    public void load(String filePath) {
        map = loader.load(filePath);
        renderer = new OrthogonalTiledMapRenderer(map, UNIT_SCALE);
    }

    public void render(OrthographicCamera camera) {
        renderer.setView(camera);
        renderer.render();
    }

    public TiledMap map() {
        return map;
    }
}
