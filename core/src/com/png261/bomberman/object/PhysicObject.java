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

    protected void createCircleBody(Circle circle) {
        createCircleBody(circle.x, circle.y, circle.radius);
    }

    protected void createCircleBody(Vector2 position, float radius) {
        createCircleBody(position.x, position.y, radius);
    }

    protected void createCircleBody(float x, float y, float radius) {
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(Unit.pixelToMeter(Unit.screenToBox2D(x, y, radius)));
        body = PhysicManager.getInstance().world().createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(Unit.pixelToMeter(radius));

        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        shape.dispose();
    }

    protected void createRectangleBody(Rectangle bounds) {
        createRectangleBody(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
    }

    protected void createRectangleBody(Vector2 position, float width, float height) {
        createRectangleBody(position.x, position.y, width, height);
    }

    protected void createRectangleBody(float x, float y, float width, float height) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(Unit.pixelToMeter(Unit.screenToBox2D(x, y, width, height)));
        body = PhysicManager.getInstance().world().createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Unit.pixelToMeter(width / 2), Unit.pixelToMeter(height / 2));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        shape.dispose();
    }

    protected void setCollisionFilter(short categoryBit, short maskBits) {
        Filter filter = new Filter();
        filter.categoryBits = categoryBit;
        filter.maskBits = maskBits;
        fixture.setFilterData(filter);
    }

    protected void setSensor(boolean isSensor) {
        fixture.setSensor(isSensor);
    }

    protected void setBodyToStatic() {
        body.setType(BodyDef.BodyType.StaticBody);
    }

    protected void stopMovement() {
        body.setLinearVelocity(0, 0);
    }
}
