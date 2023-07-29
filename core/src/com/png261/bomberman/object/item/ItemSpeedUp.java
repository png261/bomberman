package com.png261.bomberman.object.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.png261.bomberman.object.LoaderParams;
import com.png261.bomberman.object.bomberman.Bomberman;
import com.png261.bomberman.utils.Unit;

public class ItemSpeedUp extends Item {
    public ItemSpeedUp() {
        super();
    }

    @Override
    public void load(LoaderParams params) {
        super.load(params);

        sprite = new Sprite(new Texture("speedup-sticker.png"));
        sprite.setBounds(
                Unit.pixelToMeter(params.x()),
                Unit.pixelToMeter(params.y()),
                Unit.pixelToMeter(params.width()),
                Unit.pixelToMeter(params.height()));
    }

    @Override
    public void bonus(Bomberman bomberman) {
        super.bonus(bomberman);
        bomberman.speedUp(1);
    }
}
