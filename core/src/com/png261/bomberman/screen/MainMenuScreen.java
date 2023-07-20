package com.png261.bomberman.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.png261.bomberman.Game;
import com.png261.bomberman.manager.*;

public final class MainMenuScreen implements Screen
{
    private Viewport viewport;
    private Stage stage;
    private SpriteBatch batch;

    public MainMenuScreen() {}

    @Override public void show()
    {
        batch = new SpriteBatch();
        viewport = new FitViewport(Game.getInstance().getWidth(), Game.getInstance().getHeight());
        stage = new Stage(viewport, batch);

        Texture texture = new Texture("badlogic.jpg");
        Image image1 = new Image(texture);
        image1.setPosition(
            Gdx.graphics.getWidth() / 3 - image1.getWidth() / 2,
            Gdx.graphics.getHeight() * 2 / 3 - image1.getHeight() / 2);
        stage.addActor(image1);
    }

    @Override public void resize(int width, int height)
    {
        stage.getViewport().update(width, height, true);
    }

    public void handleEvents()
    {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            Game.getInstance().setScreen(new PlayScreen());
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            SoundManager.getInstance().stopMusic("test");
        }
    }

    @Override public void render(float delta)
    {
        handleEvents();
        ScreenUtils.clear(Color.WHITE);
        stage.draw();
        stage.act(delta);
    }
    @Override public void dispose() {}
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}
}
