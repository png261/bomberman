package com.png261.bomberman.state;

import com.png261.bomberman.*;
import com.png261.bomberman.manager.*;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;

public final class PlayState extends GameState
{
    private Label timeLabel;
    private int time;

    @Override public void enter()
    {
        Parent root = SceneManager.getInstance().switchScene("play_state");
        Canvas canvas = (Canvas)root.lookup("#canvas");
        Painter.getInstance().setCanvas(canvas);

        this.timeLabel = (Label)root.lookup("#timeLabel");
        Painter.getInstance().loadImage("playstate background", "/images/background.jpg");
    }
    @Override public void exit() {}
    @Override public void pause() {}
    @Override public void resume() {}

    @Override public void update() { timeLabel.setText(Integer.toString(time++)); }
    @Override public void render() {}
}
