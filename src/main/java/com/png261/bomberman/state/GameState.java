package com.png261.bomberman.state;

public abstract class GameState
{
    public abstract void enter();
    public abstract void exit();
    public abstract void pause();
    public abstract void resume();

    public abstract void update();
    public abstract void render();
}
