package com.png261.bomberman.manager;

import com.png261.bomberman.*;
import com.png261.bomberman.graphic.Window;
import com.png261.bomberman.util.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.swing.*;

public final class TextureManager
{
    private static volatile TextureManager instance;
    private final Map<String, BufferedImage> imageMap;
    Window window;

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
        try (
            final InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path)) {
            if (inputStream != null) {
                final BufferedImage image = ImageIO.read(inputStream);
                imageMap.put(id, image);
            } else {
                Log.error("Failed to load image: " + path);
            }
        } catch (IOException e) {
            Log.error("Failed to load image: " + path);
            e.printStackTrace();
        }
    }

    public BufferedImage getImage(String id) { return imageMap.get(id); }

    public void draw(String id, int x, int y, int width, int height)
    {
        BufferedImage image = this.getImage(id);
        if (image == null) {
            return;
        }

        window.drawImage(image, x, y, width, height);
    }
}
