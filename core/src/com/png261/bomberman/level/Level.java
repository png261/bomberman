package com.png261.bomberman.level;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.png261.bomberman.manager.MapManager;
import com.png261.bomberman.object.Object;
import com.png261.bomberman.physic.PhysicManager;
import com.png261.bomerberman.object.ObjectManager;
import java.util.ArrayList;

public final class Level
{
    private MapManager mapManager;
    private ObjectManager objectManager;
    private ArrayList<Object> spawnObjects;

    public Level()
    {
        PhysicManager.getInstance().setDebug(true);
        mapManager = new MapManager();
        objectManager = new ObjectManager();
        spawnObjects = new ArrayList<>();
    }

    public void load(String mapFile)
    {
        mapManager.load(mapFile);
        objectManager.load(mapManager.getMap());
    }

    public TiledMap getMap() { return mapManager.getMap(); }

    public void update(float delta)
    {
        PhysicManager.getInstance().update();

        ArrayList<Object> trashObjects = new ArrayList<Object>();
        for (Object object : spawnObjects) {
            if (object.isExist()) {
                object.update(delta);
            } else {
                trashObjects.add(object);
            }
        }

        for (Object object : trashObjects) {
            spawnObjects.remove(object);
            object.dispose();
        }

        objectManager.update(delta);
    }

    public void renderMap(final OrthographicCamera camera)
    {
        mapManager.render(camera);
        PhysicManager.getInstance().debugDraw(camera);
    }

    public void renderObject()
    {
        for (Object object : spawnObjects) {
            object.render();
        }
        objectManager.render();
    }

    public boolean isWall(Vector2 position) { return objectManager.isWall(position); }

    public boolean isBrick(Vector2 position) { return objectManager.isBrick(position); }

    public void spawnObject(Object object) { spawnObjects.add(object); }
}
