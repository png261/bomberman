package com.png261.bomberman.object.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.png261.bomberman.object.LoaderParams;

public class Bulb extends Enemy {
    private final TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("atlas/bulb.atlas"));

    private enum State {
        IDLE("idle"), DEAD("dead");

        private String value;

        private State(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public Bulb() {
        super();
    }

    @Override
    public void load(LoaderParams params) {
        super.load(params);
        setupAnimation();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (isDead()) {
            if (animationHandle.isCurrentAnimation(State.DEAD.getValue()) && animationHandle.isFinished()) {
                disappear();
            }
            return;
        }
    }

    private void setupAnimation() {
        animationHandle.addAnimation(State.IDLE.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, textureAtlas.findRegions(State.IDLE.getValue())));
        animationHandle.addAnimation(State.DEAD.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, textureAtlas.findRegions(State.DEAD.getValue())));

        animationHandle.setCurrentAnimation(State.IDLE.getValue(), false);
    }

    @Override
    public void dead() {
        super.dead();
        stopMovement();
        animationHandle.setCurrentAnimation(State.DEAD.getValue());
    }
}
