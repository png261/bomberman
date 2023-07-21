package com.png261.bomberman.object.person.enemy;

import com.png261.bomberman.object.person.Person;
import com.png261.bomberman.object.LoaderParams;
import com.png261.bomberman.physic.BitCollision;

public abstract class Enemy extends Person {
    public Enemy() {
        super();
    }

    public void load(LoaderParams params) {
        super.load(params);

        setCollisionFilter(BitCollision.ENEMY, BitCollision.orOperation(BitCollision.WALL, BitCollision.BRICK,
                BitCollision.BOMB, BitCollision.FLAME, BitCollision.BOMBERMAN));
    }
}
