package com.png261.bomberman.object;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.png261.bomberman.Game;
import com.png261.bomberman.animation.AnimationHandle;
import com.png261.bomberman.object.Object;
import com.png261.bomberman.object.person.bomberman.Bomberman;
import com.png261.bomberman.physic.*;
import com.png261.bomberman.utils.Unit;
import java.util.ArrayList;

public class Bomb extends Object
{
    private int flameLength;
    // private ArrayList<Flame> flames;
    private AnimationHandle animationHandle;
    private float FRAME_TIME = 0.6f;
    private Sprite sprite;
    public float countDown = 1.5f;
    private float bodyDiameter = 0.95f;
    private Bomberman bombOwner;
    public float timeRemove;
    public boolean canMoe = true;
    public boolean canKick = false;

    private enum State {
        BOMB_IDLE("bomb_idle"),
        BOMB_EXPLODE("bomb_explode");

        String value;

        private State(String value) { this.value = value; }

        public String getValue() { return value; }
    }

    public Bomb()
    {
        // flames = new ArrayList<Flame>();
        TextureAtlas atlas = new TextureAtlas("bomb.atlas");
        animationHandle = new AnimationHandle();
        animationHandle.addAnimation(
            State.BOMB_IDLE.getValue(),
            new Animation<TextureRegion>(
                FRAME_TIME,
                atlas.findRegions(State.BOMB_IDLE.getValue())));
        animationHandle.addAnimation(
            State.BOMB_EXPLODE.getValue(),
            new Animation<TextureRegion>(
                FRAME_TIME,
                atlas.findRegions(State.BOMB_EXPLODE.getValue())));
        animationHandle.setCurrentAnimation(State.BOMB_IDLE.getValue());
        sprite = new Sprite(animationHandle.getCurrentFrame());
    }

    public void load(Vector2 position)
    {
        createCircleBody(new Circle(position, bodyDiameter / 2), true);
        setCollisionFilter(
            BitCollision.BOMB,
            BitCollision.orOperation(
                BitCollision.BOMBERMAN,
                BitCollision.WALL,
                BitCollision.BRICK,
                BitCollision.FLAME,
                BitCollision.ENEMY,
                BitCollision.BOMB));
    }

    public void updateSprite()
    {
        sprite.setBounds(
            Unit.box2DToScreen(body.getPosition().x, bodyDiameter),
            Unit.box2DToScreen(body.getPosition().y, bodyDiameter),
            Unit.pixelsToMeters(animationHandle.getCurrentFrame().getRegionWidth()),
            Unit.pixelsToMeters(animationHandle.getCurrentFrame().getRegionHeight()));

        sprite.setRegion(animationHandle.getCurrentFrame());
    }
    @Override public void update(float delta) { updateSprite(); }
    @Override public void render() { sprite.draw(Game.getInstance().getBatch()); }
}
