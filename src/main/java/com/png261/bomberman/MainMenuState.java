package com.png261.bomberman;

import com.png261.bomberman.*;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;

public final class MainMenuState extends GameState
{
    @Override public void enter() { SceneManager.getInstance().switchScene("main_menu_state"); }
    @Override public void exit() {}
    @Override public void pause() {}
    @Override public void resume() {}

    @Override public void update() {}
    @Override public void render() {}
}
