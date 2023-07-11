package com.png261.bomberman;

import com.png261.bomberman.*;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;

public final class HelpState extends GameState
{
    @Override public void enter() { SceneManager.getInstance().switchScene("help_state"); }
    @Override public void exit() {}
    @Override public void pause() {}
    @Override public void resume() {}

    @Override public void update() {}
    @Override public void render() {}
}
