package com.png261.bomberman.object.tile;

import com.png261.bomberman.physic.BitCollision;
import com.png261.bomberman.utils.Unit;
import com.png261.bomberman.Game;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.png261.bomberman.object.GameObject;
import com.png261.bomberman.object.LoaderParams;
import com.png261.bomberman.object.ObjectFactory;
import com.png261.bomberman.object.item.Item;

public class Brick extends Tile {
    private boolean isBroken;

    @Override
    public void createBody() {
        super.createBody();
        setCollisionFilter(BitCollision.BRICK, BitCollision.ALL);
    }

    @Override
    public void update(final float delta) {
        if (isBroken) {
            bonus();
        }
    }

    public void broken() {
        isBroken = true;
    }

    public void bonus() {
        spawnItem();

        emptyCell();
        disappear();
    }

    public boolean isBroken() {
        return isBroken;
    }

    public void setBroken(final boolean isBroken) {
        this.isBroken = isBroken;
    }

    public void spawnItem() {
        final float SPAWN_PROBABILITY = 0.3f;
        if (MathUtils.random() > SPAWN_PROBABILITY) {
            return;
        }

        final Item.ItemType[] itemTypes = Item.ItemType.values();

        final String randomType = itemTypes[MathUtils.random(itemTypes.length - 1)].value();
        final GameObject item = ObjectFactory.getInstance().create(randomType);

        Vector2 position = body.getPosition();
        position = Unit.box2DToScreen(Unit.meterToPixel(position),
                16, 16);
        item.load(new LoaderParams(position, 16, 16));
        Game.getInstance().level().objectManager().add(item);
    }
}
