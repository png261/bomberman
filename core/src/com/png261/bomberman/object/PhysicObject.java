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
import com.png261.bomberman.physic.PhysicManager;
import com.png261.bomberman.utils.Unit;

public abstract class PhysicObject implements Disposable {
    protected Body body;
    protected BodyDef bodyDef;
    protected Fixture fixture;
    protected FixtureDef fixtureDef;

    PhysicObject() {
        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();
    }

    @Override
    public void dispose() {
        PhysicManager.getInstance().world().destroyBody(body);
    }

    public abstract void createBody();

    protected void createCircleBody(final Circle circle) {
        createCircleBody(circle.x, circle.y, circle.radius);
    }

    protected void createCircleBody(final Vector2 position, final float radius) {
        createCircleBody(position.x, position.y, radius);
    }

    protected void createCircleBody(final float x, final float y, final float radius) {
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(Unit.pixelToMeter(Unit.screenToBox2D(x, y, radius)));
        body = PhysicManager.getInstance().world().createBody(bodyDef);

        final CircleShape shape = new CircleShape();
        shape.setRadius(Unit.pixelToMeter(radius));

        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        shape.dispose();
    }

    protected void createRectangleBody(final Rectangle bounds) {
        createRectangleBody(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
    }

    protected void createRectangleBody(final Vector2 position, final float width, final float height) {
        createRectangleBody(position.x, position.y, width, height);
    }

    protected void createRectangleBody(final float x, final float y, final float width, final float height) {
        final BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(Unit.pixelToMeter(Unit.screenToBox2D(x, y, width, height)));
        body = PhysicManager.getInstance().world().createBody(bodyDef);

        final PolygonShape shape = new PolygonShape();
        shape.setAsBox(Unit.pixelToMeter(width / 2), Unit.pixelToMeter(height / 2));

        final FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        shape.dispose();
    }

    protected void setCollisionFilter(final short categoryBit, final short maskBits) {
        final Filter filter = new Filter();
        filter.categoryBits = categoryBit;
        filter.maskBits = maskBits;
        fixture.setFilterData(filter);
    }

    public void setSensor(final boolean isSensor) {
        fixture.setSensor(isSensor);
    }

    protected void setBodyToStatic() {
        body.setType(BodyDef.BodyType.StaticBody);
    }

    protected void stopMovement() {
        body.setLinearVelocity(0, 0);
    }

    public Body getBody() {
        return body;
    }
}
