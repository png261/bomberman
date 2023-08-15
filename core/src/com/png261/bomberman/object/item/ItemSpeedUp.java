package com.png261.bomberman.object.item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.png261.bomberman.object.LoaderParams;
import com.png261.bomberman.object.bomberman.Bomberman;
import com.png261.bomberman.utils.Unit;

public class ItemSpeedUp extends Item {
    public ItemSpeedUp() {
        super();
    }

    @Override
    public void load(final LoaderParams params) {
        super.load(params);

        sprite.setRegion(new Texture(Gdx.files.internal("image/item/speedup.png")));
        sprite.setBounds(
                Unit.pixelToMeter(params.x()),
                Unit.pixelToMeter(params.y()),
                Unit.pixelToMeter(params.width()),
                Unit.pixelToMeter(params.height()));
    }

    @Override
    public void bonus(final Bomberman bomberman) {
        super.bonus(bomberman);
        bomberman.speedUp(1);
    }
}
