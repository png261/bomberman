package com.png261.bomberman;

import com.png261.bomberman.*;
import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javafx.scene.image.Image;

public final class TextureManager
{
    private static volatile TextureManager instance;
    private final Map<String, Image> imageMap;
    private Window window;

    private TextureManager() { imageMap = new ConcurrentHashMap<>(); }

    public void init(Window window) { this.window = window; }

    public static TextureManager getInstance()
    {
        if (instance == null) {
            instance = new TextureManager();
        }
        return instance;
    }

    public void load(String id, String path)
    {
        Image image = new Image(getClass().getResource(path).toExternalForm());
        imageMap.put(id, image);
    }

    public Image getImage(String id) { return imageMap.get(id); }

    public void draw(String id, double x, double y, int width, int height)
    {
        Image image = this.getImage(id);
        if (image == null) {
            return;
        }

        window.drawImage(image, x, y, width, height);
    }
}
