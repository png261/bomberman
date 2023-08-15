package com.png261.bomberman.object.item;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.png261.bomberman.Game;
import com.png261.bomberman.object.GameObject;
import com.png261.bomberman.object.LoaderParams;
import com.png261.bomberman.object.bomberman.Bomberman;
import com.png261.bomberman.physic.BitCollision;

public abstract class Item extends GameObject {
    protected Sprite sprite;
    protected LoaderParams params;

    public Item() {
        super();
        sprite = new Sprite();
    }

    public static enum ItemType {
        ITEM_SPEED_UP("ItemSpeedUp"), ITEM_FLAME_UP("ItemFlameUp"), ITEM_BOMB_UP("ItemBombUp");

        String value;

        private ItemType(final String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }
    }

    @Override
    public void load(final LoaderParams params) {
        this.params = params;
    }

    @Override
    public void createBody() {
        createRectangleBody(params.x(), params.y(), params.width(), params.height());
        setSensor(true);
        setCollisionFilter(BitCollision.ITEM, BitCollision.BOMBERMAN);
    }

    public void bonus(final Bomberman bomberman) {
        disappear();
    };

    public void update(final float delta) {
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
