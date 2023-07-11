package com.png261.bomberman.state;

import com.png261.bomberman.*;
import com.png261.bomberman.manager.*;
import javafx.fxml.FXML;
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

    @FXML public void backToMainMenuState()
    {
        GameStateManager.getInstance().changeState(new MainMenuState());
    }
}
