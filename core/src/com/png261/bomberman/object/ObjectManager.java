package com.png261.bomerberman.object;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.png261.bomberman.object.Object;
import com.png261.bomberman.object.person.bomberman.Bomberman;
import com.png261.bomberman.object.tile.Brick;
import com.png261.bomberman.object.tile.Wall;
import com.png261.bomberman.utils.Unit;
import java.util.ArrayList;

public final class ObjectManager implements Disposable
{
    private TiledMap map;

    private ArrayList<Wall> walls;
    private ArrayList<Brick> bricks;
    private ArrayList<Object> objects;

    private static final String tiledBrickLayer = "brick";
    private static final String tiledWallsLayer = "wall";

    public ObjectManager()
    {
        walls = new ArrayList<>();
        bricks = new ArrayList<>();
        objects = new ArrayList<>();
    }

    public void load(TiledMap map)
    {
        this.map = map;

        createWall();
        createBrick();
        createObject();
    }

    public Array<RectangleMapObject> getRectangleMapObjectsFromLayer(String layer)
    {
        return map.getLayers().get(layer).getObjects().getByType(RectangleMapObject.class);
    }

    public void createWall()
    {
        for (RectangleMapObject object : getRectangleMapObjectsFromLayer(tiledWallsLayer)) {
            walls.add(new Wall(object.getRectangle()));
        }
    }

    public void createBrick()
    {
        for (RectangleMapObject object : getRectangleMapObjectsFromLayer(tiledBrickLayer)) {
            Brick brick = new Brick(object.getRectangle());
            objects.add(brick);
            bricks.add(brick);
        }
    }

    public void createObject()
    {
        for (MapLayer layer : map.getLayers()) {
            for (MapObject object : layer.getObjects()) {
                float x = object.getProperties().get("x", float.class);
                float y = object.getProperties().get("y", float.class);
                Vector2 position = Unit.coordPixelsToMeters(x, y);
                String type = object.getProperties().get("type", String.class);
                if (type != null) {
                    Object newObject = ObjectFactory.getInstance().create(type);
                    if (newObject != null) {
                        newObject.load(position);
                        objects.add(newObject);
                    }
                }
            }
        }
    }

    public void update(float delta)
    {
        ArrayList<Object> trashObjects = new ArrayList<Object>();

        for (Object object : objects) {
            if (object.isExist()) {
                object.update(delta);
            } else {
                trashObjects.add(object);
            }
        }

        for (Object object : trashObjects) {
            objects.remove(object);
            object.dispose();
        }
    }

    public void render()
    {
        for (Object object : objects) {
            if (object.isExist()) {
                object.render();
            }
        }
    }

    public boolean isWall(Vector2 position)
    {
        for (Wall wall : walls) {
            if (wall.getBounds().contains(position.x, position.y)) {
                return true;
            }
        }

        return false;
    }

    public boolean isBrick(Vector2 position)
    {
        for (Brick brick : bricks) {
            if (brick.getBounds().contains(position.x, position.y)) {
                return true;
            }
        }
        return false;
    }

    @Override public void dispose() {}
}
