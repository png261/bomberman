package com.png261.bomberman.util;

import com.png261.bomberman.util.*;

public final class LoaderParams
{
    Vector2D position;
    int width;
    int height;

    public LoaderParams(Vector2D position, int width, int height)
    {
        this.position = position;
        this.width = width;
        this.height = height;
    }

    public Vector2D getPosition() { return position; }

    public int getWidth() { return width; }

    public int getHeight() { return height; }
}
