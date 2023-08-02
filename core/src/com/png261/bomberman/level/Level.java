package com.png261.bomberman.level;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Disposable;
import com.png261.bomberman.manager.MapManager;
import com.png261.bomberman.object.ObjectManager;
import com.png261.bomberman.physic.PhysicManager;

public final class Level implements Disposable {
    private final MapManager mapManager;
    private final ObjectManager objectManager;

    public Level() {
        PhysicManager.getInstance().setDebug(true);
        mapManager = new MapManager();
        objectManager = new ObjectManager();
    }

    public void load(final String mapFile) {
        mapManager.load(mapFile);
        objectManager.load(mapManager.map());
    }

    public TiledMap map() {
        return mapManager.map();
    }

    public void update(final float delta) {
        PhysicManager.getInstance().update();
        objectManager.update(delta);
    }

    public void renderMap(final OrthographicCamera camera) {
        mapManager.render(camera);
        PhysicManager.getInstance().debugDraw(camera);
    }

    public void renderObject() {
        objectManager.render();
    }

    @Override
    public void dispose() {
        objectManager.dispose();
        mapManager.dispose();
    }

    public MapManager mapManager() {
        return mapManager;
    }

    public ObjectManager objectManager() {
        return objectManager;
    }
}
