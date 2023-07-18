package com.png261.bomberman.physic;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

public class PhysicManager implements Disposable
{
    public static volatile PhysicManager instance;

    private Vector2 gravity = new Vector2(0, 0);
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private boolean isDebug = false;

    private PhysicManager()
    {
        world = new World(gravity, true);
        world.setContactListener(new PhysicContactListener());
        debugRenderer = new Box2DDebugRenderer();
    }

    public static PhysicManager getInstance()
    {
        if (instance == null) {
            instance = new PhysicManager();
        }
        return instance;
    }

    public void update()
    {
        world.step(1 / 60f, 6, 2);
        int i = 0;
    }
    public void debugDraw(Camera camera)
    {
        if (isDebug) {
            debugRenderer.render(world, camera.combined);
        }
    }
    public void setDebug(final boolean isDebug) { this.isDebug = isDebug; }
    public boolean isDebug() { return isDebug; }
    public World getWorld() { return world; }
    @Override public void dispose() {}
}
