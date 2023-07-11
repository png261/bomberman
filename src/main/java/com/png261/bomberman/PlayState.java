package com.png261.bomberman;

import com.png261.bomberman.*;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;

public final class PlayState extends GameState
{
    @Override public void enter()
    {
        Parent root = SceneManager.getInstance().switchScene("play_state");
        Canvas canvas = (Canvas)root.lookup("#canvas");
        Painter.getInstance().setCanvas(canvas);

        Painter.getInstance().loadImage("playstate background", "/images/background.jpg");
    }
    @Override public void exit() {}
    @Override public void pause() {}
    @Override public void resume() {}

    @Override public void update() {}
    @Override public void render()
    {
        Painter.getInstance().drawImage("playstate background", 0, 0, 100, 100);
    }
}
