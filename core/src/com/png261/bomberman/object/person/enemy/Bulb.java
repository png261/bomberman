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
    private TextureAtlas textureAtlas;
    private Sprite sprite;

    private enum State {
        BULB_IDLE("bulb_idle"),
        BULB_DEAD("bulb_dead");

        private String value;

        private State(String value) { this.value = value; }

        public String getValue() { return value; }
    }

    public Bulb(Vector2 position)
    {
        super(position);
        setupAnimation();
    }

    public void setupAnimation()
    {
        textureAtlas = new TextureAtlas(Gdx.files.internal("bulb.atlas"));

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
        sprite = new Sprite(animationHandle.getCurrentFrame());
    }

    public void updateSprite()
    {
        sprite.setBounds(
            Unit.box2DToScreen(body.getPosition().x, bodyDiameter),
            Unit.box2DToScreen(body.getPosition().y, bodyDiameter),
            Unit.pixelsToMeters(animationHandle.getCurrentFrame().getRegionWidth()),
            Unit.pixelsToMeters(animationHandle.getCurrentFrame().getRegionHeight()));

        sprite.setPosition(
            Unit.box2DToScreen(body.getPosition().x, bodyDiameter),
            Unit.box2DToScreen(body.getPosition().y, bodyDiameter));

        sprite.setRegion(animationHandle.getCurrentFrame());
    }

    @Override public void update(float delta) { updateSprite(); }

    @Override public void render() { sprite.draw(Game.getInstance().getBatch()); }
}
