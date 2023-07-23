package com.png261.bomberman.object.person;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.png261.bomberman.Game;
import com.png261.bomberman.animation.AnimationHandle;
import com.png261.bomberman.object.GameObject;
import com.png261.bomberman.physic.PhysicManager;
import com.png261.bomberman.utils.Unit;
import com.png261.bomberman.object.LoaderParams;

public abstract class Person extends GameObject {
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
        PhysicManager.getInstance().world().destroyBody(body);
    }

    public void dead() {
        isDead = true;
    }

    public boolean isDead() {
        return isDead;
    }

    public void damage() {
        health = health - 1;
        if (health <= 0) {
            dead();
        }
    }

    public void moveUp() {
        this.body.setLinearVelocity(new Vector2(0, speed));
    }

    public void moveDown() {
        this.body.setLinearVelocity(new Vector2(0, -speed));
    }

    public void moveRight() {
        this.body.setLinearVelocity(new Vector2(speed, 0));
    }

    public void moveLeft() {
        this.body.setLinearVelocity(new Vector2(-speed, 0));
    }

    protected void updateSprite() {
        float x = body.getPosition().x - Unit.pixelsToMeters(BODY_RADIUS);
        float y = body.getPosition().y - Unit.pixelsToMeters(BODY_RADIUS);

        sprite.setBounds(x, y, Unit.pixelsToMeters(animationHandle.getCurrentFrame().getRegionWidth()),
                Unit.pixelsToMeters(animationHandle.getCurrentFrame().getRegionHeight()));
        sprite.setRegion(animationHandle.getCurrentFrame());
    }
}
