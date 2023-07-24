package com.png261.bomberman.object;

import java.util.ArrayList;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.png261.bomberman.object.tile.Brick;
import com.png261.bomberman.object.tile.Wall;

public final class ObjectManager implements Disposable {

    private TiledMap map;
    private ArrayList<Wall> walls;

    private ArrayList<Brick> bricks;
    private ArrayList<GameObject> objects;
    private ArrayList<GameObject> newObjects;
    private ArrayList<GameObject> trashObjects;

    public ObjectManager() {
        walls = new ArrayList<>();
        bricks = new ArrayList<>();
        objects = new ArrayList<>();
        newObjects = new ArrayList<>();
        trashObjects = new ArrayList<GameObject>();
    }

    public void load(TiledMap map) {
        this.map = map;

        createObject();
    }

    public void add(GameObject object) {
        newObjects.add(object);
    }

    public Array<RectangleMapObject> getRectangleMapObjectsFromLayer(String layer) {
        return map.getLayers().get(layer).getObjects().getByType(RectangleMapObject.class);
    }

    private void createObject() {
        for (MapLayer layer : map.getLayers()) {
            for (MapObject object : layer.getObjects()) {
                float x = object.getProperties().get("x", float.class);
                float y = object.getProperties().get("y", float.class);
                float width = object.getProperties().get("width", float.class);
                float height = object.getProperties().get("height", float.class);

                LoaderParams params = new LoaderParams(x, y, width, height);

                String type = object.getProperties().get("type", String.class);
                if (type != null) {
                    GameObject newObject = ObjectFactory.getInstance().create(type);
                    if (newObject != null) {
                        newObject.load(params);
                        objects.add(newObject);

                        if (type.equals("Wall")) {
                            walls.add((Wall) newObject);
                        } else if (type.equals("Brick")) {
                            bricks.add((Brick) newObject);
                        }
                    }
                }
            }
        }
    }

    public void update(float delta) {
        for (GameObject object : newObjects) {
            objects.add(object);
        }
        newObjects.clear();

        for (GameObject object : objects) {
            if (object.exist()) {
                object.update(delta);
            } else {
                trashObjects.add(object);
            }
        }

        for (GameObject object : trashObjects) {
            object.dispose();
            objects.remove(object);
        }
        trashObjects.clear();
    }

    public void render() {
        for (int i = objects.size() - 1; i >= 0; --i) {
            GameObject object = objects.get(i);
            if (object.exist()) {
                object.render();
            }
        }
    }

    public boolean isPositionOnWall(Vector2 position) {
        for (Wall wall : walls) {
            if (wall.getBounds().contains(position.x, position.y)) {
                return true;
            }
        }

        return false;
    }

    public boolean isPositionOnBrick(Vector2 position) {
        for (Brick brick : bricks) {
            if (brick.getBounds().contains(position.x, position.y)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void dispose() {
    }
}
