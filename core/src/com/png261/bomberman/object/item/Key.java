package com.png261.bomberman.object.item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.png261.bomberman.Game;
import com.png261.bomberman.animation.AnimationHandle;
import com.png261.bomberman.object.LoaderParams;
import com.png261.bomberman.object.bomberman.Bomberman;
import com.png261.bomberman.object.item.Key;
import com.png261.bomberman.utils.Unit;

public class Key extends Item {
    private final float FRAME_TIME = 0.6f;
    private TextureAtlas atlas;
    private AnimationHandle animationHandle;

    private enum State {
        NORMAL("normal"), EMPTY("empty");

        String value;

        private State(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public Key() {
        sprite = new Sprite();
        animationHandle = new AnimationHandle();
    }

    @Override
    public void load(LoaderParams params) {
        super.load(params);

        atlas = new TextureAtlas(Gdx.files.internal("image/atlas/key.atlas"));
        animationHandle.addAnimation(State.NORMAL.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, atlas.findRegions(State.NORMAL.getValue())));
        animationHandle.addAnimation(State.EMPTY.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, atlas.findRegions(State.EMPTY.getValue())));

        animationHandle.setCurrentAnimation(State.NORMAL.getValue());
    }

    @Override
    public void update(float delta) {
        updateSprite();
    }

    @Override
    public void render() {
        sprite.draw(Game.getInstance().batch());
    }

    private void updateSprite() {
        float x = body.getPosition().x - Unit.pixelToMeter(params.width() / 2);
        float y = body.getPosition().y - Unit.pixelToMeter(params.height() / 2);

        sprite.setBounds(x, y, Unit.pixelToMeter(animationHandle.getCurrentFrame().getRegionWidth()),
                Unit.pixelToMeter(animationHandle.getCurrentFrame().getRegionHeight()));
        sprite.setRegion(animationHandle.getCurrentFrame());
    }

    @Override
    public void bonus(Bomberman bomberman) {
        bomberman.recivedKey();
        animationHandle.setCurrentAnimation(State.EMPTY.getValue());
    }
}
