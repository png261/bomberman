package com.png261.bomberman.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.png261.bomberman.Game;
import com.png261.bomberman.level.Level;
import com.png261.bomberman.object.bomberman.Bomberman;

public final class PlayState extends GameState {
    private final int MAP_WIDTH = 17;
    private final int MAP_HEIGHT = 13;

    private OrthographicCamera camera;
    private Viewport viewport;

    private Level level;
    private Bomberman bomberman;

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(MAP_WIDTH, MAP_HEIGHT, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        level = new Level();
        Game.getInstance().setLevel(level);
        level.load("map1.tmx");
        bomberman = level.getBombermans().get(0);
    }

    public void update(float delta) {
        camera.update();
        level.update(delta);

        handleInput();
        bomberman.update(delta);
    }

    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            bomberman.placeBomb();
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            bomberman.moveUp();
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            bomberman.moveDown();
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            bomberman.moveLeft();
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            bomberman.moveRight();
        } else {
            bomberman.idle();
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        update(delta);
        level.renderMap(camera);

        Game.getInstance().batch().setProjectionMatrix(camera.combined);
        Game.getInstance().batch().begin();

        level.renderObject();
        bomberman.render();

        Game.getInstance().batch().end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
        System.out.println("play state pause");
    }

    @Override
    public void resume() {
    }
}
