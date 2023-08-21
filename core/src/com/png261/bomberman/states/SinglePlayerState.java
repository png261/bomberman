package com.png261.bomberman.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.png261.bomberman.Game;
import com.png261.bomberman.level.Level;
import com.png261.bomberman.manager.GameStateManager;
import com.png261.bomberman.object.Bomberman;

public final class SinglePlayerState extends GameState {
    private final int MAP_WIDTH = 17;
    private final int MAP_HEIGHT = 13;

    private OrthographicCamera camera;
    private Viewport viewport;

    private Level level;
    private Bomberman bomberman;
    private boolean nextLevel = false;
    private Array<String> maps;
    private boolean hasKey = false;
    private int mapIndex;

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(MAP_WIDTH, MAP_HEIGHT, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        maps = new Array<>();
        maps.add("map/map1.tmx");
        maps.add("map/map2.tmx");
        mapIndex = 0;

        level = new Level("map/map1.tmx");
        Game.getInstance().setLevel(level);
        bomberman = level.objectManager().getBombermans().get(0);
    }

    public void update(final float delta) {
        if (!bomberman.exist()) {
            GameStateManager.getInstance().changeState(new GameOverState());
            return;
        }

        if (nextLevel) {
            if (hasKey && mapIndex == maps.size - 1) {
                GameStateManager.getInstance().changeState(new WinState());
                return;
            }

            if (hasKey && mapIndex < maps.size - 1) {
                mapIndex += 1;
                loadLevel();
            }
            nextLevel = false;
        }

        camera.update();
        level.update(delta);

        handleInput();
        bomberman.update(delta);
    }

    public void loadLevel() {
        Game.getInstance().level().dispose();
        level = new Level(maps.get(mapIndex));
        Game.getInstance().setLevel(level);
        bomberman = level.objectManager().getBombermans().get(0);
        hasKey = false;
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

        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            nextLevel();
        }
    }

    public void nextLevel() {
        nextLevel = true;
    }

    public void receiveKey() {
        hasKey = true;
    }

    @Override
    public void render(final float delta) {
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
    public void resize(final int width, final int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        level.dispose();
    }

    @Override
    public void hide() {
        level.dispose();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
