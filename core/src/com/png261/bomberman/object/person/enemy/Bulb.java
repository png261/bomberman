package com.png261.bomberman.object.person.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.png261.bomberman.Game;
import com.png261.bomberman.animation.AnimationHandle;
import com.png261.bomberman.object.person.Person;
import com.png261.bomberman.physic.BitCollision;
import com.png261.bomberman.utils.Unit;

public class Bulb extends Enemy
{
    private static final TextureAtlas textureAtlas = new TextureAtlas("bulb.atlas");

    private Sprite sprite;

    private enum State {
        BULB_IDLE("bulb_idle"),
        BULB_DEAD("bulb_dead");

        private String value;

        private State(String value) { this.value = value; }

        public String getValue() { return value; }
    }

    public Bulb() { super(); }

    @Override public void load(Vector2 position)
    {
        super.load(position);
        setupAnimation();
    }

    private void setupAnimation()
    {
        animationHandle.addAnimation(
            State.BULB_DEAD.getValue(),
            new Animation<TextureRegion>(
                FRAME_TIME,
                textureAtlas.findRegions(State.BULB_DEAD.getValue())));
        animationHandle.addAnimation(
            State.BULB_IDLE.getValue(),
            new Animation<TextureRegion>(
                FRAME_TIME,
                textureAtlas.findRegions(State.BULB_IDLE.getValue())));
        animationHandle.setCurrentAnimation(State.BULB_IDLE.getValue(), false);
    }
}
