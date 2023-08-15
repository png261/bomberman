package com.png261.bomberman.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.png261.bomberman.Game;
import com.png261.bomberman.level.Level;
import com.png261.bomberman.networking.Client;
import com.png261.bomberman.object.bomberman.Bomberman;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public final class MultiPlayerState extends GameState {
    private final int MAP_WIDTH = 17;
    private final int MAP_HEIGHT = 13;

    private final Socket socket;
    private final Array<Client> clients;
    private Array<Bomberman> players;

    private OrthographicCamera camera;
    private Viewport viewport;
    private Level level;
    private Array<Bomberman> bombermans;
    private ObjectMap<String, Bomberman> mapBomberman;

    public MultiPlayerState(final Socket socket, final Array<Client> clients) {
        this.socket = socket;
        this.clients = clients;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(MAP_WIDTH, MAP_HEIGHT, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        level = new Level();
        Game.getInstance().setLevel(level);
        level.load("multiplayer.tmx");
        bombermans = level.objectManager().getBombermans();

        mapBomberman = new ObjectMap<>();
        for (int i = 0; i < clients.size; ++i) {
            mapBomberman.put(clients.get(i).id, bombermans.get(i));
        }

        handleSocketEvent();
    }

    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            socket.emit("placeBomb");
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            socket.emit("moveUp");
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            socket.emit("moveDown");
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            socket.emit("moveLeft");
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            socket.emit("moveRight");
        } else {
            socket.emit("idle");
        }
    }

    public void handleSocketEvent() {
        socket.on("placeBomb", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                final String id = (String) args[0];
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        mapBomberman.get(id).placeBomb();
                    }
                });
            }
        });

        socket.on("moveUp", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                final String id = (String) args[0];
                mapBomberman.get(id).moveUp();
            }
        });

        socket.on("moveDown", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                final String id = (String) args[0];
                mapBomberman.get(id).moveDown();
            }
        });

        socket.on("moveRight", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                final String id = (String) args[0];
                mapBomberman.get(id).moveRight();
            }
        });

        socket.on("moveLeft", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                final String id = (String) args[0];
                mapBomberman.get(id).moveLeft();
            }
        });

        socket.on("idle", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                final String id = (String) args[0];
                mapBomberman.get(id).idle();
            }
        });
    }

    public void update(final float delta) {
        camera.update();
        level.update(delta);
        for (final ObjectMap.Entry<String, Bomberman> item : mapBomberman) {
            final String id = item.key;
            final Bomberman bomberman = item.value;
            bomberman.update(delta);
        }
    }

    @Override
    public void render(final float delta) {
        handleInput();

        update(delta);

        ScreenUtils.clear(Color.BLACK);
        level.renderMap(camera);

        Game.getInstance().batch().setProjectionMatrix(camera.combined);
        Game.getInstance().batch().begin();
        level.renderObject();

        for (final ObjectMap.Entry<String, Bomberman> item : mapBomberman) {
            final String id = item.key;
            final Bomberman bomberman = item.value;
            bomberman.render();
        }
        Game.getInstance().batch().end();
    }

    @Override
    public void resize(final int width, final int height) {
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
    }

    @Override
    public void resume() {
    }
}
