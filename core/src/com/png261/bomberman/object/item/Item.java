package com.png261.bomberman.object.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
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
import com.png261.bomberman.object.person.bomberman.Bomberman;
import com.png261.bomberman.physic.BitCollision;
import com.png261.bomberman.physic.PhysicManager;
import com.png261.bomberman.utils.Unit;

public abstract class Item extends Object
{
    protected Sprite sprite;

    public Item() { super(); }

    @Override public void load(Vector2 position)
    {
        createRectangleBody(new Rectangle(position.x * 16, position.y * 16, 16, 16));
        setSensor(true);
        setCollisionFilter(BitCollision.ITEM, BitCollision.orOperation(BitCollision.BOMBERMAN));
    }

    public void bonus(Bomberman bomberman) { disappear(); };
    public void update(float delta) {}
    public void render() { sprite.draw(Game.getInstance().getBatch()); }

    @Override public void dispose()
    {
        super.dispose();
        sprite.getTexture().dispose();
    }
}
