package com.png261.bomberman.object.tile;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.png261.bomberman.physic.PhysicManager;
import com.png261.bomberman.utils.Unit;

public abstract class Tile
{
    public Body body;
    protected Fixture fixture;
    public Rectangle bounds;

    public Tile(Rectangle bounds)
    {
        this.bounds = bounds;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(Unit.coordPixelsToMeters(
            Unit.screenToBox2D(bounds.getX(), bounds.getWidth()),
            Unit.screenToBox2D(bounds.getY(), bounds.getHeight())));
        body = PhysicManager.getInstance().getWorld().createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(
            Unit.pixelsToMeters(bounds.getWidth() / 2),
            Unit.pixelsToMeters(bounds.getHeight() / 2));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        shape.dispose();
    }

    protected void setCollisionFilter(short categoryBit, short maskBits)
    {
        Filter filter = new Filter();
        filter.categoryBits = categoryBit;
        filter.maskBits = maskBits;
        fixture.setFilterData(filter);
    }

    public Rectangle getRect() { return bounds; }
}
