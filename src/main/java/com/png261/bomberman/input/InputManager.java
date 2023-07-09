package com.png261.bomberman.input;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;

public final class InputManager
{
    private static volatile InputManager instance;

    private boolean[] keys;
    private boolean[] mouseButtons;
    private Point mousePosition;

    private InputManager() {}

    public static InputManager getInstance()
    {
        if (instance == null) {
            instance = new InputManager();
        }
        return instance;
    }


    public void init(JPanel panel)
    {
        keys = new boolean[256];

        mouseButtons = new boolean[5];
        mousePosition = new Point();

        bindingListener(panel);
    }

    public boolean isKeyDown(int keycode) { return keys[keycode]; }

    public boolean isMouseButtonDown(int button) { return mouseButtons[button]; }
    public int getMouseX() { return mousePosition.x; }
    public int getMouseY() { return mousePosition.y; }

    private class MouseEventListener implements MouseMotionListener, MouseListener
    {
        @Override public void mouseDragged(MouseEvent e) {}
        @Override public void mouseEntered(MouseEvent e) {}
        @Override public void mouseReleased(MouseEvent e)
        {
            int button = e.getButton();
            mouseButtons[button] = false;
        }
        @Override public void mousePressed(MouseEvent e)
        {
            int button = e.getButton();
            mouseButtons[button] = true;
        }
        @Override public void mouseClicked(MouseEvent e) {}
        @Override public void mouseExited(MouseEvent e) {}
        @Override public void mouseMoved(MouseEvent e) { mousePosition = e.getPoint(); }
    }

    private class KeyboardEventListener implements KeyListener
    {
        @Override public void keyReleased(KeyEvent e)
        {
            int keyCode = e.getKeyCode();
            if (keyCode <= 256) {
                keys[e.getKeyCode()] = false;
            }
        }
        @Override public void keyPressed(KeyEvent e)
        {
            int keyCode = e.getKeyCode();
            if (keyCode <= 256) {
                keys[e.getKeyCode()] = true;
            }
        }
        @Override public void keyTyped(KeyEvent e) {}
    }

    public void bindingListener(JPanel panel)
    {
        panel.addKeyListener(new KeyboardEventListener());
        panel.addMouseListener(new MouseEventListener());
        panel.addMouseMotionListener(new MouseEventListener());
    }
}
