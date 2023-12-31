package com.png261.bomberman.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.png261.bomberman.manager.GameStateManager;

public final class MainMenuState extends GameState {
    private Stage stage;
    private Skin skin;

    private Table rootTable;

    private TextButton singleButton;
    private TextButton multipleButton;

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("uiskin/uiskin.json"));

        rootTable = new Table();

        singleButton = new TextButton("Single Player", skin);
        singleButton.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                GameStateManager.getInstance().changeState(new SinglePlayerState());
            }

        });

        multipleButton = new TextButton("Multiple Player", skin);
        multipleButton.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                GameStateManager.getInstance().changeState(new ConnectState());
            }

        });

        rootTable.add(singleButton).padBottom(10).width(200);
        rootTable.row();
        rootTable.add(multipleButton).width(200);
        rootTable.setFillParent(true);

        stage.addActor(rootTable);
    }

    @Override
    public void render(final float delta) {
        ScreenUtils.clear(Color.BLACK);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(final int width, final int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
