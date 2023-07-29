package com.png261.bomberman.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.png261.bomberman.manager.GameStateManager;

public class GameOverState extends GameState {

    private FitViewport viewport;
    private Stage stage;

    private BitmapFont font;

    @Override
    public void show() {
        viewport = new FitViewport(640, 480);
        stage = new Stage(viewport);

        font = new BitmapFont(Gdx.files.internal("fonts/foo.fnt"));

        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        Label gameOverLabel = new Label("Game Over", labelStyle);
        gameOverLabel.setPosition((640 - gameOverLabel.getWidth()) / 2, 226f);

        stage.addActor(gameOverLabel);

        stage.addAction(Actions.sequence(
                Actions.delay(1f),
                Actions.fadeOut(2f),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        GameStateManager.getInstance().changeState(new MainMenuState());
                    }
                })));

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
        font.dispose();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
