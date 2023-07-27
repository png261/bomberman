package com.png261.bomberman.level;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.png261.bomberman.manager.MapManager;
import com.png261.bomberman.object.GameObject;
import com.png261.bomberman.object.ObjectManager;
import com.png261.bomberman.object.person.bomberman.Bomberman;
import com.png261.bomberman.physic.PhysicManager;

public final class Level {
    private final MapManager mapManager;
    private final ObjectManager objectManager;

    public Level() {
        PhysicManager.getInstance().setDebug(true);
        mapManager = new MapManager();
        objectManager = new ObjectManager();
    }

    public void load(String mapFile) {
        mapManager.load(mapFile);
        objectManager.load(mapManager.map());
    }

    public TiledMap map() {
        return mapManager.map();
    }

    public void update(float delta) {
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

    public boolean isPositionOnWall(Vector2 position) {
        return objectManager.isPositionOnWall(position);
    }

    public boolean isPositionOnBrick(Vector2 position) {
        return objectManager.isPositionOnBrick(position);
    }

    public void addObject(GameObject object) {
        objectManager.add(object);
    }

    public Array<Bomberman> getBombermans() {
        return objectManager.getBombermans();
    }
}
