package com.png261.bomberman.object;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Disposable;
import com.png261.bomberman.animation.AnimationHandle;
import com.png261.bomberman.physic.PhysicManager;
import com.png261.bomberman.utils.Unit;

public abstract class Object implements Disposable
{
    protected Body body;
    protected Fixture fixture;
    protected BodyDef bodyDef;
    protected FixtureDef fixtureDef;
    protected Boolean isExist = true;

    public Object()
    {
        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();
    }

    public abstract void load(Vector2 position);
    public abstract void update(float delta);
    public abstract void render();

    public void createCircleBody(Circle circle, boolean isStatic)
    {
        bodyDef.type = isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(Unit.coordScreenToBox2D(circle.x, circle.y, circle.radius));
        body = PhysicManager.getInstance().getWorld().createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(circle.radius);

        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        shape.dispose();
    }

    public void createRectangleBody(Rectangle bounds, boolean isStatic)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(Unit.coordPixelsToMeters(
            Unit.screenToBox2D(bounds.getX(), bounds.getWidth()),
            Unit.screenToBox2D(bounds.getY(), bounds.getHeight())));
        body = PhysicManager.getInstance().getWorld().createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(
            Unit.pixelsToMeters(bounds.getWidth() / 2),
            Unit.pixelsToMeters(bounds.getHeight() / 2));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        shape.dispose();
    }

    public void setCollisionFilter(short categoryBit, short maskBits)
    {
        Filter filter = new Filter();
        filter.categoryBits = categoryBit;
        filter.maskBits = maskBits;
        fixture.setFilterData(filter);
    }

    public boolean isExist() { return isExist; }
    @Override public void dispose() { PhysicManager.getInstance().getWorld().destroyBody(body); }
}
