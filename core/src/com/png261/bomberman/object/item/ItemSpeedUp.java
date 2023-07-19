package com.png261.bomberman.object.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Filter;
import com.png261.bomberman.Game;
import com.png261.bomberman.object.person.bomberman.Bomberman;
import com.png261.bomberman.physic.BitCollision;
import com.png261.bomberman.utils.Unit;

public class ItemSpeedUp extends Item
{
    public ItemSpeedUp() { super(); }

    @Override public void load(Vector2 position)
    {
        super.load(position);

        sprite = new Sprite(new Texture("speedup-sticker.png"));
        sprite.setBounds(position.x, position.y, 1, 1);
    }

    @Override public void bonus(Bomberman bomberman)
    {
        super.bonus(bomberman);
        bomberman.speedUp(1);
    }
}
