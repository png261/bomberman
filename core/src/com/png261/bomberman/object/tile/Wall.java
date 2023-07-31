package com.png261.bomberman.object.tile;

import com.png261.bomberman.physic.BitCollision;

public class Wall extends Tile {
    @Override
    public void createBody() {
        super.createBody();
        setCollisionFilter(BitCollision.WALL, BitCollision.ALL);
    }
}
