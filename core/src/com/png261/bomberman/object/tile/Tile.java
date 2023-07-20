package com.png261.bomberman.object.tile;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.png261.bomberman.Game;
import com.png261.bomberman.object.Object;
import com.png261.bomberman.physic.PhysicManager;
import com.png261.bomberman.utils.Unit;

public abstract class Tile extends Object
{
    private Rectangle bounds;
    public Tile(Rectangle bounds)
    {
        super();
        this.bounds = bounds;
        createRectangleBody(bounds);
        setBodyToStatic();
    }

    public void load(Vector2 position) {}

    @Override public void update(float delta) {}
    @Override public void render() {}

    public Rectangle getBounds() { return bounds; }

    public TiledMapTileLayer.Cell getCell()
    {
        TiledMapTileLayer layer =
            (TiledMapTileLayer)Game.getInstance().getLevel().getMap().getLayers().get("tile");
        return layer.getCell((int)body.getPosition().x, (int)body.getPosition().y);
    }

    public void emptyCell()
    {
        TiledMapTileLayer.Cell cell = getCell();
        if (cell != null) {
            getCell().setTile(null);
        }
    }
}
