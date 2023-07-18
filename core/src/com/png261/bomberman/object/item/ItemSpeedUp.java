package com.png261.bomberman.object.item;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Filter;
import com.png261.bomberman.physic.BitCollision;
import com.png261.bomberman.object.person.Bomberman;

public class ItemSpeedUp extends Item
{
    public ItemSpeedUp(Rectangle rect) { super(rect); }
    @Override public void bonus(Bomberman bomberman) { bomberman.speedUp(1); }
}
