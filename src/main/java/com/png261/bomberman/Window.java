package com.png261.bomberman;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public final class Window
{
    private Canvas canvas;
    private GraphicsContext gc;
    private int width;
    private int height;
    private Color background;

    Window(int width, int height)
    {
        this.width = width;
        this.height = height;
        this.width = width;
        this.canvas = new Canvas(this.width, this.height);
        this.gc = canvas.getGraphicsContext2D();
    }
    public GraphicsContext getGraphicsContenxt() { return gc; }
    public Canvas getCanvas() { return canvas; }
    public void drawRectangle(int x, int y, int width, int height)
    {
        gc.setFill(Color.RED);
        gc.fillRect(x, y, width, height);
    }
    public void setBackground(Color color) { this.background = color; }
    public void clear()
    {
        gc.clearRect(0, 0, width, height);
        fill(background);
    }
    public void fill(Color color)
    {
        gc.setFill(color);
        gc.fillRect(0, 0, width, width);
    }
    public void drawImage(Image image, double x, double y, int width, int height)
    {
        gc.drawImage(image, x, y, width, height);
    }
}
