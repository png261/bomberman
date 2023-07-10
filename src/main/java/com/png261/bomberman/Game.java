package com.png261.bomberman;

import com.png261.bomberman.*;
import javafx.event.EventHandler;
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
    private String title;
    private Window window;
    private Cursor cursor;

    private Game() {}
    public static Game getInstance()
    {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public void init(Stage stage, int width, int height, String title)
    {
        this.stage = stage;
        this.title = title;
        this.window = new Window(width, height);
        this.window.setBackground(Color.BLUE);

        stage.setTitle(this.title);
        final StackPane root = new StackPane();
        root.getChildren().add(window.getCanvas());

        final Scene scene = new Scene(root);
        scene.setCursor(javafx.scene.Cursor.NONE);

        InputManager.getInstance().init(scene);
        this.cursor = new Cursor();
        TextureManager.getInstance().init(window);

        stage.setScene(scene);
        stage.show();
    }

    public void update(){};
    public void render()
    {
        this.window.clear();
        this.cursor.draw();
    };
    public void handleEvents(){};
    public void clean(){};
    public Window getWindow() { return window; }
}
