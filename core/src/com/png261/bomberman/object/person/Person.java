package com.png261.bomberman.object.person;

import com.badlogic.gdx.graphics.g2d.Sprite;
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
import com.png261.bomberman.physic.PhysicManager;
import com.png261.bomberman.utils.Unit;

public abstract class Person extends Object
{
    protected final float BODY_DIAMETER = 0.875f;
    protected final float FRAME_TIME = 0.6f;

    protected boolean isDead;
    protected float speed = 2.5f;
    protected AnimationHandle animationHandle;
    protected Sprite sprite;

    public Person() { super(); }

    @Override public void load(Vector2 position)
    {
        animationHandle = new AnimationHandle();
        createCircleBody(new Circle(position, BODY_DIAMETER / 2), false);
    }

    public boolean isDead() { return isDead; }

    public void moveUp() { this.body.setLinearVelocity(new Vector2(0, speed)); }

    public void moveDown() { this.body.setLinearVelocity(new Vector2(0, -speed)); }

    public void moveRight() { this.body.setLinearVelocity(new Vector2(speed, 0)); }

    public void moveLeft() { this.body.setLinearVelocity(new Vector2(-speed, 0)); }

    public void updateSprite()
    {
        sprite.setBounds(
            Unit.box2DToScreen(body.getPosition().x, BODY_DIAMETER),
            Unit.box2DToScreen(body.getPosition().y, BODY_DIAMETER),
            Unit.pixelsToMeters(animationHandle.getCurrentFrame().getRegionWidth()),
            Unit.pixelsToMeters(animationHandle.getCurrentFrame().getRegionHeight()));

        sprite.setRegion(animationHandle.getCurrentFrame());
    }

    @Override public void update(float delta) { updateSprite(); };

    @Override public void render() { sprite.draw(Game.getInstance().getBatch()); }

    @Override public void dispose()
    {
        super.dispose();
        PhysicManager.getInstance().getWorld().destroyBody(body);
    }
}
