package com.png261.bomerberman.object;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import com.png261.bomberman.object.tile.*;
import com.png261.bomberman.utils.Unit;
import java.util.ArrayList;

public class ObjectManager implements Disposable
{
    private TiledMap map;

    private ArrayList<Wall> walls;
    private ArrayList<Brick> bricks;
    private ArrayList<Item> items;
    private ArrayList<Person> personList;

    private static final String tileset_All = "tileset_all";
    private static final String tiledBackgroundLayer = "background";
    private static final String tiledWallsLayer = "wall";
    private static final String tiledBrickLayer = "brick";
    private static final String tiledSpeedUpLayer = "item_speed_up";
    private static final String tiledFlameUpLayer = "item_flame_up";
    private static final String tiledBombUpLayer = "item_bomb_up";
    private static final String tiledBombermanLayer = "bomberman";
    private static final String tiledBalloomLayer = "balloom";
    private static final String tiledBulbLayer = "bulb";
    private static final String tiledKeysLayer = "keys";
    private static final String tiledKey = "key";
    private static final String tiledPortal = "portal";

    public ObjectManager()
    {
        walls = new ArrayList<>();
        bricks = new ArrayList<>();
        items = new ArrayList<>();
        personList = new ArrayList<>();
    }

    public void load(TiledMap map)
    {
        this.map = map;

        createWall();
        createBrick();
        createItem();
        createBomberman();
    }

    public Array<RectangleMapObject> getRectangleMapObjectsFromLayer(String layer)
    {
        return map.getLayers().get(layer).getObjects().getByType(RectangleMapObject.class);
    }

    public Array<EllipseMapObject> getEllipseMapObjectsFromLayer(String layer)
    {
        return map.getLayers().get(layer).getObjects().getByType(EllipseMapObject.class);
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

    public void createItem()
    {
        createItemSpeedUp();
        createItemFlameUp();
        createItemBombUp();
    }

    public void createItemSpeedUp()
    {
        for (RectangleMapObject object : getRectangleMapObjectsFromLayer(tiledSpeedUpLayer)) {
            items.add(new ItemSpeedUp(object.getRectangle()));
        }
    }


    public void createItemFlameUp()
    {
        for (RectangleMapObject object : getRectangleMapObjectsFromLayer(tiledFlameUpLayer)) {
            items.add(new ItemFlameUp(object.getRectangle()));
        }
    }


    public void createItemBombUp()
    {
        for (RectangleMapObject object : getRectangleMapObjectsFromLayer(tiledBombUpLayer)) {
            items.add(new ItemBombUp(object.getRectangle()));
        }
    }

    public void createBomberman()
    {
        for (EllipseMapObject object : getEllipseMapObjectsFromLayer(tiledBombermanLayer)) {
            Ellipse ellipse = object.getEllipse();
            personList.add(new Bomberman(Unit.coordPixelsToMeters(ellipse.x, ellipse.y)));
        }
    }

    public void handleEvents()
    {
        for (Person person : personList) {
            person.handleEvents();
        }
    }

    public void update()
    {
        for (Person person : personList) {
            person.update();
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
