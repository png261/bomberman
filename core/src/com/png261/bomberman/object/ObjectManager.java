package com.png261.bomberman.object;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.png261.bomberman.object.Bomberman;
import com.png261.bomberman.object.tile.Brick;
import com.png261.bomberman.object.tile.Wall;

public final class ObjectManager implements Disposable {

    private TiledMap map;
    private final Array<Wall> walls;

    private final Array<Brick> bricks;
    private final Array<GameObject> objects;
    private final Array<GameObject> newObjects;
    private final Array<Bomberman> bombermans;

    public ObjectManager(final TiledMap map) {
        walls = new Array<>();
        bricks = new Array<>();
        objects = new Array<>();
        newObjects = new Array<>();
        bombermans = new Array<>();

        this.map = map;
        createObject();
    }

    public void add(final GameObject object) {
        newObjects.add(object);
    }

    private void createObject() {
        for (final MapLayer layer : map.getLayers()) {
            for (final MapObject object : layer.getObjects()) {
                final float x = object.getProperties().get("x", float.class);
                final float y = object.getProperties().get("y", float.class);
                final float width = object.getProperties().get("width", float.class);
                final float height = object.getProperties().get("height", float.class);

                final LoaderParams params = new LoaderParams(x, y, width, height);

                final String type = object.getProperties().get("type", String.class);
                if (type != null) {
                    final GameObject newObject = ObjectFactory.getInstance().create(type);
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

    public void update(final float delta) {
        for (final GameObject object : newObjects) {
            objects.add(object);
            object.createBody();
        }
        newObjects.clear();

        for (int i = 0; i < objects.size; ++i) {
            final GameObject object = objects.get(i);
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
            final GameObject object = objects.get(i);
            if (object.exist()) {
                object.render();
            }
        }
    }

    public Array<Bomberman> getBombermans() {
        return bombermans;
    }

    public boolean hasWallAtPosition(final Vector2 position) {
        for (final Wall wall : walls) {
            if (wall.contains(position)) {
                return true;
            }
        }

        return false;
    }

    public boolean hasBrickAtPosition(final Vector2 position) {
        for (final Brick brick : bricks) {
            if (brick.contains(position)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void dispose() {
        for (final GameObject object : objects) {
            object.dispose();
        }

        for (final GameObject object : bombermans) {
            object.dispose();
        }

        objects.clear();
        bombermans.clear();
    }

    public boolean hasBombAtPosition(final Vector2 position) {
        for (final Object object : objects) {
            if (object instanceof Bomb) {
                final Bomb bomb = (Bomb) object;
                return bomb.contains(position);
            }
        }
        return false;
    }
}
