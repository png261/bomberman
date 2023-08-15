package com.png261.bomberman.object.item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.png261.bomberman.Game;
import com.png261.bomberman.manager.AnimationManager;
import com.png261.bomberman.object.LoaderParams;
import com.png261.bomberman.object.bomberman.Bomberman;
import com.png261.bomberman.utils.Unit;

public class Key extends Item {
    private final float FRAME_TIME = 0.6f;
    private TextureAtlas atlas;
    private final AnimationManager animationManager;

    private enum State {
        NORMAL("normal"), EMPTY("empty");

        String value;

        private State(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public Key() {
        sprite = new Sprite();
        animationManager = new AnimationManager();
    }

    @Override
    public void load(final LoaderParams params) {
        super.load(params);

        atlas = new TextureAtlas(Gdx.files.internal("image/atlas/key.atlas"));
        animationManager.addAnimation(State.NORMAL.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, atlas.findRegions(State.NORMAL.getValue())));
        animationManager.addAnimation(State.EMPTY.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, atlas.findRegions(State.EMPTY.getValue())));

        animationManager.setCurrentAnimation(State.NORMAL.getValue());
    }

    @Override
    public void update(final float delta) {
        updateSprite();
    }

    @Override
    public void render() {
        sprite.draw(Game.getInstance().batch());
    }

    private void updateSprite() {
        final float x = body.getPosition().x - Unit.pixelToMeter(params.width() / 2);
        final float y = body.getPosition().y - Unit.pixelToMeter(params.height() / 2);

        sprite.setBounds(x, y, Unit.pixelToMeter(animationManager.getCurrentFrame().getRegionWidth()),
                Unit.pixelToMeter(animationManager.getCurrentFrame().getRegionHeight()));
        sprite.setRegion(animationManager.getCurrentFrame());
    }

    @Override
    public void bonus(final Bomberman bomberman) {
        bomberman.recivedKey();
        animationManager.setCurrentAnimation(State.EMPTY.getValue());
    }
}
