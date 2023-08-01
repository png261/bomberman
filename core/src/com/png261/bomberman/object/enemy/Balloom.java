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

        private State(String value) {
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
    public void load(LoaderParams params) {
        super.load(params);
        setupAnimation();
    }

    public void setupAnimation() {
        animationHandle.addAnimation(State.UP.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, textureAtlas.findRegions(State.UP.getValue())));
        animationHandle.addAnimation(State.DEAD.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, textureAtlas.findRegions(State.DEAD.getValue())));
        animationHandle.addAnimation(State.DOWN.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, textureAtlas.findRegions(State.DOWN.getValue())));
        animationHandle.addAnimation(State.RIGHT.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, textureAtlas.findRegions(State.RIGHT.getValue())));
        animationHandle.addAnimation(State.LEFT.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, textureAtlas.findRegions(State.LEFT.getValue())));
        animationHandle.setCurrentAnimation(State.DOWN.getValue());
        animationHandle.addAnimation(State.DEAD.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, textureAtlas.findRegions(State.DEAD.getValue())));
    }

    private void randomMove(float delta) {
        timeMove -= delta;
        if (timeMove <= 0) {
            int random = Util.getRandomInRange(1, 5);
            switch (random) {
                case 1:
                    animationHandle.setCurrentAnimation(State.RIGHT.getValue());
                    moveRight();
                    break;
                case 2:
                    animationHandle.setCurrentAnimation(State.LEFT.getValue());
                    moveLeft();
                    break;
                case 3:
                    animationHandle.setCurrentAnimation(State.UP.getValue());
                    moveUp();
                    break;
                case 4:
                    animationHandle.setCurrentAnimation(State.DOWN.getValue());
                    moveDown();
                    break;
            }
            timeMove = 3f;
        }
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
        randomMove(delta);
    }

    @Override
    public void damage(int damage) {
        super.damage(damage);
    }

    @Override
    public void dead() {
        super.dead();
        stopMovement();
        animationHandle.setCurrentAnimation(State.DEAD.getValue());
    }
}
