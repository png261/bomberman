package com.png261.bomberman;

import com.png261.bomberman.*;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public final class Game
{
    private static volatile Game instance;

    private Stage stage;

    private Game() {}
    public static Game getInstance()
    {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public void init(Stage stage, String title)
    {
        this.stage = stage;
        stage.setTitle(title);
        stage.setResizable(false);
        stage.setScene(SceneManager.getInstance().getScene());
        SceneManager.getInstance().getScene().getStylesheets().add("/css/style.css");
        stage.show();

        InputManager.getInstance().bindingListener(SceneManager.getInstance().getScene());

        SceneManager.getInstance().load("main_menu_state", "/scenes/main_menu.fxml");
        SceneManager.getInstance().load("play_state", "/scenes/play.fxml");
        SceneManager.getInstance().load("help_state", "/scenes/help.fxml");

        GameStateManager.getInstance().pushState(new MainMenuState());
    }

    public void update() { GameStateManager.getInstance().update(); }
    public void render() { GameStateManager.getInstance().render(); }
}
