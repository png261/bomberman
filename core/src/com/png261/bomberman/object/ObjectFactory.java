package com.png261.bomerberman.object;

import com.png261.bomberman.object.*;
import com.png261.bomberman.object.Object;
import com.png261.bomberman.object.item.*;
import com.png261.bomberman.object.person.bomberman.*;
import com.png261.bomberman.object.person.enemy.*;
import com.png261.bomberman.object.tile.*;

public final class ObjectFactory
{
    public static volatile ObjectFactory instance;

    private ObjectFactory() {}
    public static ObjectFactory getInstance()
    {
        if (instance == null) {
            instance = new ObjectFactory();
        }
        return instance;
    }

    public Object create(String id)
    {
        if (id.equals("Bomb")) {
            return new Bomb();
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
        }
        return null;
    }
}
