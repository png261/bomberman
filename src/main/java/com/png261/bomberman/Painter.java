package com.png261.bomberman;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public final class Painter
{
    private static volatile Painter instance;
    private GraphicsContext gc;
    private Canvas canvas;
    private final Map<String, Image> imageMap;

    private Painter() { this.imageMap = new ConcurrentHashMap<>(); }
    public static Painter getInstance()
    {
        if (instance == null) {
            instance = new Painter();
        }
        return instance;
    }

    public void setCanvas(Canvas canvas)
    {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
    }
    public void drawRectangle(int x, int y, int width, int height)
    {
        gc.setFill(Color.RED);
        gc.fillRect(x, y, width, height);
    }
    public void loadImage(String id, String path)
    {
        Image image = new Image(getClass().getResource(path).toExternalForm());
        imageMap.put(id, image);
    }
    public void drawImage(String id, double x, double y, int width, int height)
    {
        Image image = imageMap.get(id);
        if (image == null) {
            return;
        }

        this.gc.drawImage(image, x, y, width, height);
    }
    public void clear() { gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); }
}
