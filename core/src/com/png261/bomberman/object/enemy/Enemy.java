package com.png261.bomberman.object.enemy;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.png261.bomberman.Game;
import com.png261.bomberman.animation.AnimationHandle;
import com.png261.bomberman.object.ControllableObject;
import com.png261.bomberman.object.DamageableObject;
import com.png261.bomberman.object.GameObject;
import com.png261.bomberman.object.LoaderParams;
import com.png261.bomberman.physic.BitCollision;
import com.png261.bomberman.utils.Unit;

public abstract class Enemy extends GameObject implements DamageableObject, ControllableObject {
    protected final float BODY_DIAMETER = 12;
    protected final float BODY_RADIUS = 6;
    protected final float FRAME_TIME = 0.6f;

    protected int health = 1;
    protected boolean isDead = false;

    protected float speed = 2.5f;
    protected AnimationHandle animationHandle;
    protected Sprite sprite;
    private LoaderParams params;

    public Enemy() {
        super();
        animationHandle = new AnimationHandle();
        sprite = new Sprite();
    }

    public void load(final LoaderParams params) {
        this.params = params;
    }

    @Override
    public void createBody() {
        createCircleBody(params.position(), BODY_RADIUS);
        setCollisionFilter(BitCollision.ENEMY, BitCollision.ALL);
    }

    @Override
    public void update(final float delta) {
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
    public void damage(final int damage) {
        health = health - damage;
        if (health <= 0) {
            dead();
        }
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

    protected void updateSprite() {
        final float x = body.getPosition().x - Unit.pixelToMeter(BODY_RADIUS);
        final float y = body.getPosition().y - Unit.pixelToMeter(BODY_RADIUS);

        sprite.setBounds(x, y, Unit.pixelToMeter(animationHandle.getCurrentFrame().getRegionWidth()),
                Unit.pixelToMeter(animationHandle.getCurrentFrame().getRegionHeight()));
        sprite.setRegion(animationHandle.getCurrentFrame());
    }

}
