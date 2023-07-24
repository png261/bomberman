package com.png261.bomberman.object.person;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.png261.bomberman.Game;
import com.png261.bomberman.animation.AnimationHandle;
import com.png261.bomberman.object.GameObject;
import com.png261.bomberman.object.ControllableObject;
import com.png261.bomberman.object.DamageableObject;
import com.png261.bomberman.physic.PhysicManager;
import com.png261.bomberman.utils.Unit;
import com.png261.bomberman.object.LoaderParams;

public abstract class Person extends GameObject implements DamageableObject, ControllableObject {
    protected final float BODY_DIAMETER = 12;
    protected final float BODY_RADIUS = 6;
    protected final float FRAME_TIME = 0.6f;

    protected int health = 1;
    protected boolean isDead = false;

    protected float speed = 2.5f;
    protected AnimationHandle animationHandle;
    protected Sprite sprite;

    public Person() {
        super();
        animationHandle = new AnimationHandle();
        sprite = new Sprite();
    }

    @Override
    public void load(LoaderParams params) {
        createCircleBody(params.position(), BODY_RADIUS);
    }

    @Override
    public void update(float delta) {
        updateSprite();
    };

    @Override
    public void render() {
        sprite.draw(Game.getInstance().batch());
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void dead() {
        isDead = true;
    }

    @Override
    public boolean isDead() {
        return isDead;
    }

    @Override
    public void damage(int damage) {
        health = health - damage;
        if (health <= 0) {
            dead();
        }
    }

    protected void updateSprite() {
        float x = body.getPosition().x - Unit.pixelToMeter(BODY_RADIUS);
        float y = body.getPosition().y - Unit.pixelToMeter(BODY_RADIUS);

        sprite.setBounds(x, y, Unit.pixelToMeter(animationHandle.getCurrentFrame().getRegionWidth()),
                Unit.pixelToMeter(animationHandle.getCurrentFrame().getRegionHeight()));
        sprite.setRegion(animationHandle.getCurrentFrame());
    }

    @Override
    public void moveUp() {
        this.body.setLinearVelocity(new Vector2(0, speed));
    }

    @Override
    public void moveDown() {
        this.body.setLinearVelocity(new Vector2(0, -speed));
    }

    @Override
    public void moveRight() {
        this.body.setLinearVelocity(new Vector2(speed, 0));
    }

    @Override
    public void moveLeft() {
        this.body.setLinearVelocity(new Vector2(-speed, 0));
    }
}
