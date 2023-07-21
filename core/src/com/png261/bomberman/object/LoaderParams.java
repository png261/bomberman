package com.png261.bomberman.object;

import com.badlogic.gdx.math.Vector2;

public class LoaderParams {
    private float x;
    private float y;
    private float width;
    private float height;

    public LoaderParams(Vector2 position, float width, float height) {
        this(position.x, position.y, width, height);
    }

    public LoaderParams(float x, float y, float width, float height) {
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
