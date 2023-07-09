package com.png261.bomberman.gameObject;

import com.png261.bomberman.util.*;

public abstract class GameObject
{
    protected Vector2D position;
    protected Vector2D veloctiy;

    protected int width;
    protected int height;

    public void load(LoaderParams params)
    {
        position = params.getPosition();
        width = params.getWidth();
        height = params.getHeight();
    }
    abstract public void update();
    abstract public void draw();
}
