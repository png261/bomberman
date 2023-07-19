package com.png261.bomberman.object.person.enemy;

import com.badlogic.gdx.math.Vector2;
import com.png261.bomberman.object.person.Person;
import com.png261.bomberman.physic.BitCollision;

public abstract class Enemy extends Person
{
    public Enemy() { super(); }

    public void load(Vector2 position)
    {
        super.load(position);

        setCollisionFilter(
            BitCollision.ENEMY,
            BitCollision.orOperation(
                BitCollision.WALL,
                BitCollision.BRICK,
                BitCollision.BOMB,
                BitCollision.FLAME,
                BitCollision.BOMBERMAN));
    }
}
