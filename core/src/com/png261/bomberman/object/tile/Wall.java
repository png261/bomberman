package com.png261.bomberman.object.tile;

import com.badlogic.gdx.math.Rectangle;
import com.png261.bomberman.physic.BitCollision;

public class Wall extends Tile
{
    public Wall(Rectangle bounds)
    {
        super(bounds);
        setCollisionFilter(
            BitCollision.WALL,
            BitCollision.orOperation(
                BitCollision.BOMBERMAN,
                BitCollision.BOMB,
                BitCollision.FLAME,
                BitCollision.ENEMY));
    }
}
