package com.png261.bomberman.object.person;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.png261.bomberman.animation.AnimationHandle;
import com.png261.bomberman.physic.PhysicManager;
import com.png261.bomberman.utils.Unit;

public abstract class Person
{
    protected boolean isDead;
    protected float speed = 2.5f;
    protected final AnimationHandle animationHandle;
    protected final float bodyDiameter = 0.875f;
    protected Body body;
    protected Fixture fixture;
    protected BodyDef bodyDef;
    protected FixtureDef fixtureDef;
    protected float FRAME_TIME = 0.6f;

    public Person(Vector2 position)
    {
        animationHandle = new AnimationHandle();

        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();

        defineBody(position);
    }

    public void defineBody(Vector2 position)
    {
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(Unit.coordScreenToBox2D(position.x, position.y, bodyDiameter / 2));
        body = PhysicManager.getInstance().getWorld().createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(bodyDiameter / 2);

        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        shape.dispose();
    }

    protected void setCollisionFilter(short categoryBit, short maskBits)
    {
        Filter filter = new Filter();
        filter.categoryBits = categoryBit;
        filter.maskBits = maskBits;
        fixture.setFilterData(filter);
    }

    public boolean isDead() { return isDead; }

    public void moveUp() { this.body.setLinearVelocity(new Vector2(0, speed)); }

    public void moveDown() { this.body.setLinearVelocity(new Vector2(0, -speed)); }

    public void moveRight() { this.body.setLinearVelocity(new Vector2(speed, 0)); }

    public void moveLeft() { this.body.setLinearVelocity(new Vector2(-speed, 0)); }

    public abstract void update(float delta);
    public abstract void render();
}
