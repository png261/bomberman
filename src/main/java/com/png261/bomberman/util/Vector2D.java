package com.png261.bomberman.util;

public final class Vector2D
{
    private float x;
    private float y;

    public Vector2D(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public void add(Vector2D v2)
    {
        this.x += v2.x;
        this.y += v2.y;
    }
}
