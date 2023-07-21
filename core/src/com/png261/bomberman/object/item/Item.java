package com.png261.bomberman.object.item;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.png261.bomberman.Game;
import com.png261.bomberman.object.GameObject;
import com.png261.bomberman.object.LoaderParams;
import com.png261.bomberman.object.person.bomberman.Bomberman;
import com.png261.bomberman.physic.BitCollision;

public abstract class Item extends GameObject {
    protected Sprite sprite;

    public Item() {
        super();
    }

    @Override
    public void load(LoaderParams params) {
        createRectangleBody(params.x(), params.y(), 16, 16);
        setSensor(true);
        setCollisionFilter(BitCollision.ITEM, BitCollision.orOperation(BitCollision.BOMBERMAN));
    }

    public void bonus(Bomberman bomberman) {
        disappear();
    };

    public void update(float delta) {
    }

    public void render() {
        sprite.draw(Game.getInstance().batch());
    }

    @Override
    public void dispose() {
        super.dispose();
        sprite.getTexture().dispose();
    }
}
