package com.png261.bomberman.object;

import com.badlogic.gdx.math.Vector2;

public class LoaderParams {
    private final float x;
    private final float y;
    private final float width;
    private final float height;

    public LoaderParams(final Vector2 position, final float width, final float height) {
        this(position.x, position.y, width, height);
    }

    public LoaderParams(final Vector2 position) {
        this(position.x, position.y, 0, 0);
    }

    public LoaderParams(final float x, final float y, final float width, final float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }

    public Vector2 position() {
        return new Vector2(x, y);
    }

    public float width() {
        return width;
    }

    public float height() {
        return height;
    }

}
