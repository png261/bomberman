package com.png261.bomberman;

import com.png261.bomberman.Game;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;

public final class Main extends Application
{
    public static void main(String[] args) { launch(args); }
    @Override public void start(Stage stage)
    {
        Game game = Game.getInstance();
        game.init(stage, "Bomerman");
        new AnimationTimer() {
            @Override public void handle(long now)
            {
                game.update();
                game.render();
            }
        }.start();
    }
}
