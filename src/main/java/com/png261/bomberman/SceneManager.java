package com.png261.bomberman;
import com.png261.bomberman.*;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public final class SceneManager
{
    private static volatile SceneManager instance;
    private final Map<String, Parent> rootMap;
    private Scene scene;

    private SceneManager()
    {
        rootMap = new ConcurrentHashMap<>();
        this.scene = new Scene(new StackPane(), 1280, 768);
    }
    public static SceneManager getInstance()
    {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    public void load(String id, String path)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();

            rootMap.put(id, root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Scene getScene() { return this.scene; }

    public Parent switchScene(String id)
    {
        Parent root = rootMap.get(id);
        if (root == null) {
            return null;
        }
        this.scene.setRoot(root);
        return root;
    }
}
