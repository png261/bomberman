package com.png261.bomberman.object.item;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Filter;
import com.png261.bomberman.object.person.Bomberman;
import com.png261.bomberman.physic.BitCollision;

public class ItemBombUp extends Item
{
    public ItemBombUp(Rectangle rect) { super(rect); }
    @Override public void bonus(Bomberman bomberman) { bomberman.bombUp(1); }
}
