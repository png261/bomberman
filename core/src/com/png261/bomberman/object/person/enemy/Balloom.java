package com.png261.bomberman.object.person.enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.png261.bomberman.object.LoaderParams;
import com.png261.bomberman.utils.Util;

public class Balloom extends Enemy {
    private static final TextureAtlas textureAtlas = new TextureAtlas("balloom.atlas");
    private float timeMove;

    private enum State {
        BALLOOM_DOWN("balloom_down"), BALLOOM_UP("balloom_up"), BALLOOM_LEFT("balloom_left"),
        BALLOOM_RIGHT("balloom_right"), BALLOOM_DEAD("balloom_dead");

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
        animationHandle.addAnimation(State.BALLOOM_UP.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, textureAtlas.findRegions(State.BALLOOM_UP.getValue())));
        animationHandle.addAnimation(State.BALLOOM_DEAD.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, textureAtlas.findRegions(State.BALLOOM_DEAD.getValue())));
        animationHandle.addAnimation(State.BALLOOM_DOWN.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, textureAtlas.findRegions(State.BALLOOM_DOWN.getValue())));
        animationHandle.addAnimation(State.BALLOOM_RIGHT.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, textureAtlas.findRegions(State.BALLOOM_RIGHT.getValue())));
        animationHandle.addAnimation(State.BALLOOM_LEFT.getValue(),
                new Animation<TextureRegion>(FRAME_TIME, textureAtlas.findRegions(State.BALLOOM_LEFT.getValue())));
        animationHandle.setCurrentAnimation(State.BALLOOM_DOWN.getValue());
    }

    private void randomMove(float delta) {
        timeMove -= delta;
        if (timeMove <= 0) {
            int random = Util.getRandomInRange(1, 5);
            switch (random) {
                case 1:
                    animationHandle.setCurrentAnimation(State.BALLOOM_RIGHT.getValue());
                    moveRight();
                    break;
                case 2:
                    animationHandle.setCurrentAnimation(State.BALLOOM_LEFT.getValue());
                    moveLeft();
                    break;
                case 3:
                    animationHandle.setCurrentAnimation(State.BALLOOM_UP.getValue());
                    moveUp();
                    break;
                case 4:
                    animationHandle.setCurrentAnimation(State.BALLOOM_DOWN.getValue());
                    moveDown();
                    break;
            }
            timeMove = 3f;
        }
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        randomMove(delta);
    }
}
