package com.png261.bomberman;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public final class InputManager
{
    private static volatile InputManager instance;

    private Scene scene;
    private boolean[] keys;
    private boolean[] mouseButtons;
    private double mouseX;
    private double mouseY;

    private InputManager()
    {
        keys = new boolean[256];
        mouseButtons = new boolean[5];
    }

    public static InputManager getInstance()
    {
        if (instance == null) {
            instance = new InputManager();
        }
        return instance;
    }

    public boolean isKeyDown(KeyCode key) { return keys[key.ordinal()]; }

    public boolean isMouseButtonDown(int button) { return mouseButtons[button]; }
    public double getMouseX() { return mouseX; }
    public double getMouseY() { return mouseY; }

    public void onMouseReleased(MouseEvent e)
    {
        int button = e.getButton().ordinal();
        mouseButtons[button] = false;
    }
    public void onMousePressed(MouseEvent e)
    {
        int button = e.getButton().ordinal();
        mouseButtons[button] = true;
    }
    public void onMouseMoved(MouseEvent e)
    {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    public void onKeyReleased(KeyEvent e)
    {
        int keyCode = e.getCode().ordinal();
        if (keyCode <= 256) {
            keys[e.getCode().ordinal()] = false;
        }
    }
    public void onKeyPressed(KeyEvent e)
    {
        int keyCode = e.getCode().ordinal();
        if (keyCode <= 256) {
            keys[e.getCode().ordinal()] = true;
        }
    }
    public void bindingListener(Scene scene)
    {
        scene.addEventFilter(KeyEvent.KEY_PRESSED, this::onKeyPressed);
        scene.addEventFilter(KeyEvent.KEY_RELEASED, this::onKeyReleased);
        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, this::onMousePressed);
        scene.addEventFilter(MouseEvent.MOUSE_RELEASED, this::onMouseReleased);
        scene.addEventFilter(MouseEvent.MOUSE_MOVED, this::onMouseMoved);
    }
}
