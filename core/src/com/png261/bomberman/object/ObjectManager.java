package com.png261.bomerberman.object;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.png261.bomberman.object.item.*;
import com.png261.bomberman.object.person.*;
import com.png261.bomberman.object.person.bomberman.*;
import com.png261.bomberman.object.person.enemy.*;
import com.png261.bomberman.object.tile.*;
import com.png261.bomberman.utils.Unit;
import java.util.ArrayList;

public class ObjectManager implements Disposable
{
    private TiledMap map;

    private ArrayList<Wall> walls;
    private ArrayList<Brick> bricks;
    private ArrayList<Person> personList;

    private static final String tiledWallsLayer = "wall";
    private static final String tiledBrickLayer = "brick";
    private static final String tiledBombermanLayer = "bomberman";
    private static final String tiledEnemyLayer = "enemy";
    private static final String tiledKeysLayer = "keys";
    private static final String tiledKey = "key";
    private static final String tiledPortal = "portal";

    public ObjectManager()
    {
        walls = new ArrayList<>();
        bricks = new ArrayList<>();
        personList = new ArrayList<>();
    }

    public void load(TiledMap map)
    {
        this.map = map;

        createWall();
        createBrick();
        createEnemy();
        createBomberman();
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
            bricks.add(new Brick(object.getRectangle()));
        }
    }

    public void createBomberman()
    {
        for (MapObject object : map.getLayers().get(tiledBombermanLayer).getObjects()) {
            float x = object.getProperties().get("x", float.class);
            float y = object.getProperties().get("y", float.class);
            Vector2 position = Unit.coordPixelsToMeters(x, y);
            String type = object.getProperties().get("type", String.class);

            personList.add(new Bomberman(position));
        }
    }

    public void createEnemy()
    {
        for (MapObject object : map.getLayers().get(tiledEnemyLayer).getObjects()) {
            float x = object.getProperties().get("x", float.class);
            float y = object.getProperties().get("y", float.class);
            Vector2 position = Unit.coordPixelsToMeters(x, y);
            String type = object.getProperties().get("type", String.class);

            if (type.equals("Balloom")) {
                personList.add(new Balloom(position));
            } else if (type.equals("Bulb")) {
                personList.add(new Bulb(position));
            }
        }
    }

    public void update(float delta)
    {
        for (Person person : personList) {
            person.update(delta);
        }
    }

    public void render()
    {
        for (Person person : personList) {
            person.render();
        }
    }

    @Override public void dispose() {}
}
