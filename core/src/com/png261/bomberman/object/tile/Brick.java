package com.png261.bomberman.object.tile;

import com.png261.bomberman.physic.BitCollision;
import com.png261.bomberman.object.LoaderParams;

public class Brick extends Tile {
    public Brick() {
        super();
    }

    @Override
    public void load(LoaderParams params) {
        super.load(params);
        setCollisionFilter(BitCollision.BRICK, BitCollision.orOperation(BitCollision.BOMBERMAN, BitCollision.BOMB,
                BitCollision.FLAME, BitCollision.ENEMY));
    }

    public void bonus() {
        System.out.println("brick bonus");
        emptyCell();

        // ItemSpeedUp item = new ItemSpeedUp();
        // Vector2 position = body.getPosition();
        // item.load(position);
        // Game.getInstance().getLevel().spawnObject(item);

        disappear();
    }
}
