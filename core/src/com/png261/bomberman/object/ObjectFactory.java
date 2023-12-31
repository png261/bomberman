package com.png261.bomberman.object;

import com.png261.bomberman.object.item.ItemBombUp;
import com.png261.bomberman.object.item.ItemFlameUp;
import com.png261.bomberman.object.item.ItemSpeedUp;
import com.png261.bomberman.object.item.Key;
import com.png261.bomberman.object.Bomberman;
import com.png261.bomberman.object.enemy.Balloom;
import com.png261.bomberman.object.enemy.Bulb;
import com.png261.bomberman.object.tile.Brick;
import com.png261.bomberman.object.tile.Wall;

public final class ObjectFactory {
    private static volatile ObjectFactory instance;

    private ObjectFactory() {
    }

    public static ObjectFactory getInstance() {
        if (instance == null) {
            instance = new ObjectFactory();
        }
        return instance;
    }

    public GameObject create(final String id) {
        if (id.equals("Wall")) {
            return new Wall();
        } else if (id.equals("Brick")) {
            return new Brick();
        } else if (id.equals("Bomb")) {
            return new Bomb(1);
        } else if (id.equals("Bomberman")) {
            return new Bomberman();
        } else if (id.equals("Balloom")) {
            return new Balloom();
        } else if (id.equals("Bulb")) {
            return new Bulb();
        } else if (id.equals("ItemSpeedUp")) {
            return new ItemSpeedUp();
        } else if (id.equals("ItemFlameUp")) {
            return new ItemFlameUp();
        } else if (id.equals("ItemBombUp")) {
            return new ItemBombUp();
        } else if (id.equals("Key")) {
            return new Key();
        } else if (id.equals("Door")) {
            return new Door();
        }

        return null;
    }
}
