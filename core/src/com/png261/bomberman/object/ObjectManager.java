package com.png261.bomberman.object;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.png261.bomberman.object.bomberman.Bomberman;
import com.png261.bomberman.object.tile.Brick;
import com.png261.bomberman.object.tile.Wall;

public final class ObjectManager implements Disposable {

    private TiledMap map;
    private Array<Wall> walls;

    private Array<Brick> bricks;
    private Array<GameObject> objects;
    private Array<GameObject> newObjects;
    private Array<Bomberman> bombermans;

    public ObjectManager() {
        walls = new Array<>();
        bricks = new Array<>();
        objects = new Array<>();
        newObjects = new Array<>();
        bombermans = new Array<>();
    }

    public void load(TiledMap map) {
        this.map = map;

        createObject();
    }

    public void add(GameObject object) {
        newObjects.add(object);
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
                        newObject.createBody();

                        if (type.equals("Bomberman")) {
                            bombermans.add((Bomberman) newObject);
                        } else {
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
    }

    public void update(float delta) {
        for (GameObject object : newObjects) {
            objects.add(object);
            object.createBody();
        }
        newObjects.clear();

        for (int i = 0; i < objects.size; ++i) {
            GameObject object = objects.get(i);
            if (object.exist()) {
                object.update(delta);
            } else {
                object.dispose();
                objects.removeIndex(i);
            }
        }
    }

    public void render() {
        for (int i = objects.size - 1; i >= 0; --i) {
            GameObject object = objects.get(i);
            if (object.exist()) {
                object.render();
            }
        }
    }

    public Array<Bomberman> getBombermans() {
        return bombermans;
    }

    public boolean hasWallAtPosition(Vector2 position) {
        for (Wall wall : walls) {
            if (wall.contains(position)) {
                return true;
            }
        }

        return false;
    }

    public boolean hasBrickAtPosition(Vector2 position) {
        for (Brick brick : bricks) {
            if (brick.contains(position)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void dispose() {
        for (GameObject object : objects) {
            object.dispose();
        }

        for (GameObject object : bombermans) {
            object.dispose();
        }

        objects.clear();
        bombermans.clear();
    }

    public boolean hasBombAtPosition(Vector2 position) {
        for (Object object : objects) {
            if (object instanceof Bomb) {
                Bomb bomb = (Bomb) object;
                return bomb.contains(position);
            }
        }
        return false;
    }
}
