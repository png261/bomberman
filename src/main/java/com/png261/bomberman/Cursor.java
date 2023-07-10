package com.png261.bomberman;

import com.png261.bomberman.*;

public final class Cursor
{
    private final int size;
    private boolean isHover;

    public Cursor()
    {
        size = 32;
        isHover = false;
        TextureManager.getInstance().load("cursor_normal", "/images/cursor/normal.png");
        TextureManager.getInstance().load("cursor_hover", "/images/cursor/hover.png");
    }

    public void draw()
    {
        TextureManager.getInstance().draw(
            isHover ? "cursor_hover" : "cursor_normal",
            InputManager.getInstance().getMouseX(),
            InputManager.getInstance().getMouseY(),
            size,
            size);
    }

    public void reset() { isHover = false; }

    public void hover() { isHover = true; }

    public boolean isHover() { return isHover; }
}
