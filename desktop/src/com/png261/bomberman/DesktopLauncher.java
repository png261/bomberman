package com.png261.bomberman;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM
// argument
public class DesktopLauncher
{
    public static void main(String[] arg)
    {
        com.png261.bomberman.Game game = com.png261.bomberman.Game.getInstance();

        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setTitle("Bomberman");
        config.setWindowedMode(game.width(), game.height());
        new Lwjgl3Application(game, config);
    }
}
