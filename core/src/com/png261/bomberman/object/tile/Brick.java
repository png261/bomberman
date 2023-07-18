package com.png261.bomberman.object.tile;

import com.badlogic.gdx.math.Rectangle;
import com.png261.bomberman.physic.BitCollision;

public class Brick extends Tile
{
    public Brick(Rectangle rect)
    {
        super(rect);
        setCollisionFilter(
            BitCollision.BRICK,
            BitCollision.orOperation(
                BitCollision.BOMBERMAN,
                BitCollision.BOMB,
                BitCollision.FLAME,
                BitCollision.ENEMY));
    }
}
