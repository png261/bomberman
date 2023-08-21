package com.png261.bomberman.object.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.png261.bomberman.object.LoaderParams;

public class Bulb extends Enemy {
    private final TextureAtlas textureAtlas ;

    private enum State {
        IDLE("idle"), DEAD("dead");

        private String value;

        private State(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public Bulb() {
        super();
        textureAtlas = new TextureAtlas(Gdx.files.internal("image/atlas/bulb.atlas"));
    }

    @Override
    public void load(final LoaderParams params) {
        super.load(params);
        setupAnimation();
    }

    @Override
    public void update(final float delta) {
        super.update(delta);
        if (isDead()) {
            if (animationManager.isRunning(State.DEAD.getValue()) && animationManager.isFinished()) {
                disappear();
            }
            return;
        }
    }

    private void setupAnimation() {
        animationManager.add(State.IDLE.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, textureAtlas.findRegions(State.IDLE.getValue())));
        animationManager.add(State.DEAD.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, textureAtlas.findRegions(State.DEAD.getValue())));

        animationManager.run(State.IDLE.getValue(), false);
    }

    @Override
    public void dead() {
        super.dead();
        stopMovement();
        animationManager.run(State.DEAD.getValue());
    }
}
