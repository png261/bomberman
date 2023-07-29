package com.png261.bomberman.object.tile;

import com.png261.bomberman.physic.BitCollision;
import com.png261.bomberman.object.LoaderParams;

public class Wall extends Tile {
    public Wall() {
        super();
    }

    @Override
    public void load(LoaderParams params) {
        super.load(params);
        setCollisionFilter(BitCollision.WALL, BitCollision.ALL);
    }
}
