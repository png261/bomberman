package com.png261.bomberman.physic;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import box2dLight.RayHandler;

public final class PhysicManager implements Disposable {
    private static volatile PhysicManager instance;

    private final Vector2 GRAVITY = new Vector2(0, 0);
    private final World world;
    private final Box2DDebugRenderer debugRenderer;
    private boolean isDebug = false;
    private RayHandler rayHandler;

    private PhysicManager() {
        world = new World(GRAVITY, true);
        world.setContactListener(new PhysicContactListener());
        debugRenderer = new Box2DDebugRenderer();
        rayHandler = new RayHandler(world);
    }

    public static PhysicManager getInstance() {
        if (instance == null) {
            instance = new PhysicManager();
        }
        return instance;
    }

    public void update() {
        world.step(1 / 60f, 6, 2);
    }

    public void debugDraw(final Camera camera) {
        if (isDebug) {
            debugRenderer.render(world, camera.combined);
        }
    }

    public void setDebug(final boolean isDebug) {
        this.isDebug = isDebug;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public World world() {
        return world;
    }

    @Override
    public void dispose() {
    }

    public RayHandler getRayHandler() {
        return rayHandler;
    }
}
