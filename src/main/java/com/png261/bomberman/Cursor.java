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
        Painter.getInstance().loadImage("cursor_normal", "/images/cursor/normal.png");
        Painter.getInstance().loadImage("cursor_hover", "/images/cursor/hover.png");
    }

    public void draw()
    {
        Painter.getInstance().drawImage(
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
