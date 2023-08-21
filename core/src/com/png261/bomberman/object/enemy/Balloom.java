package com.png261.bomberman.object.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.png261.bomberman.object.LoaderParams;
import com.png261.bomberman.utils.Util;

public class Balloom extends Enemy {
    private final TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("image/atlas/balloom.atlas"));
    private float timeMove;

    private enum State {
        DOWN("down"), UP("up"), LEFT("left"),
        RIGHT("right"), DEAD("dead");

        private String value;

        private State(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public Balloom() {
        super();
    }

    @Override
    public void load(final LoaderParams params) {
        super.load(params);
        setupAnimation();
    }

    public void setupAnimation() {
        animationManager.add(State.UP.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, textureAtlas.findRegions(State.UP.getValue())));
        animationManager.add(State.DEAD.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, textureAtlas.findRegions(State.DEAD.getValue())));
        animationManager.add(State.DOWN.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, textureAtlas.findRegions(State.DOWN.getValue())));
        animationManager.add(State.RIGHT.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, textureAtlas.findRegions(State.RIGHT.getValue())));
        animationManager.add(State.LEFT.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, textureAtlas.findRegions(State.LEFT.getValue())));
        animationManager.run(State.DOWN.getValue());
        animationManager.add(State.DEAD.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, textureAtlas.findRegions(State.DEAD.getValue())));
    }

    private void randomMove(final float delta) {
        timeMove -= delta;
        if (timeMove <= 0) {
            final int random = Util.getRandomInRange(1, 5);
            switch (random) {
                case 1:
                    animationManager.run(State.RIGHT.getValue());
                    moveRight();
                    break;
                case 2:
                    animationManager.run(State.LEFT.getValue());
                    moveLeft();
                    break;
                case 3:
                    animationManager.run(State.UP.getValue());
                    moveUp();
                    break;
                case 4:
                    animationManager.run(State.DOWN.getValue());
                    moveDown();
                    break;
            }
            timeMove = 3f;
        }
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
        randomMove(delta);
    }

    @Override
    public void damage(final int damage) {
        super.damage(damage);
    }

    @Override
    public void dead() {
        super.dead();
        stopMovement();
        animationManager.run(State.DEAD.getValue());
    }
}
