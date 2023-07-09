package com.png261.bomberman;

import com.png261.bomberman.game.Game;
import javax.swing.*;

public class Main
{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> {
            Game.getInstance().init(600, 600, "bomberman");
            Game.getInstance().start();
        });
    }
}
