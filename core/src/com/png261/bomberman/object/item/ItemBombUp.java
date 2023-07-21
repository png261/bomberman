package com.png261.bomberman.object.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.png261.bomberman.object.LoaderParams;
import com.png261.bomberman.object.person.bomberman.Bomberman;

public class ItemBombUp extends Item {
    public ItemBombUp() {
        super();
    }

    @Override
    public void load(LoaderParams params) {
        super.load(params);

        sprite = new Sprite(new Texture("bombup-sticker.png"));
        sprite.setBounds(params.x(), params.y(), params.width(), params.height());
    }

    @Override
    public void bonus(Bomberman bomberman) {
        super.bonus(bomberman);
        bomberman.bombUp(1);
    }
}
