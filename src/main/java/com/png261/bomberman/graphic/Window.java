package com.png261.bomberman.graphic;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public final class Window extends JFrame
{
    private String title;
    private int width;
    private int height;
    private JPanel panel;
    private BufferedImage buffer;
    private Color backgroundColor = Color.red;
    private Graphics2D dbg2D;

    private final class MainPanel extends JPanel
    {
        public MainPanel()
        {
            this.setPreferredSize(new Dimension(getWidth(), getHeight()));
            this.setFocusable(true);
            this.requestFocus();
        }

        @Override protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D)g;

            g2d.drawImage(buffer, 0, 0, null);
            g2d.dispose();
        }
    }

    public Window(int width, int height, String title)
    {
        this.width = width;
        this.height = height;
        this.title = title;

        this.buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        this.dbg2D = (Graphics2D)buffer.createGraphics();

        this.panel = new MainPanel();
        this.add(panel);

        this.setTitle(this.title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.hideCursor();

        this.pack();
        this.setSize(width, height);
        this.setVisible(true);
    }

    public void drawImage(BufferedImage image, int x, int y, int width, int height)
    {
        dbg2D.drawImage(image, x, y, width, height, null);
    }

    public void fill(Color color)
    {
        this.backgroundColor = color;
        dbg2D.setColor(backgroundColor);
        dbg2D.fillRect(0, 0, getWidth(), getHeight());
    }

    public void clear()
    {
        dbg2D.dispose();
        dbg2D = buffer.createGraphics();

        fill(this.backgroundColor);
    }

    public int getWidth() { return this.width; }

    public int getHeight() { return this.height; }

    public void refresh()
    {
        this.panel.repaint();
        Toolkit.getDefaultToolkit().sync();
    }

    public void hideCursor()
    {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image blankCursorImage = toolkit.createImage(new byte[] {0});
        java.awt.Cursor blankCursor =
            toolkit.createCustomCursor(blankCursorImage, new Point(0, 0), "blankCursor");
        this.setCursor(blankCursor);
    }

    public JPanel getPanel() { return panel; }
}
