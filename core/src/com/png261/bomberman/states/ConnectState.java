package com.png261.bomberman.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.png261.bomberman.Game;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ConnectState extends GameState {
    private Stage stage;
    private Skin skin;

    private String clientId;
    private Socket socket;

    private Table rootTable;

    private Label nameLabel;
    private TextField nameField;

    private Label serverUrlLabel;
    private TextField serverUrlField;

    private TextButton submitButton;

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("uiskin/uiskin.json"));

        nameLabel = new Label("Name:", skin);
        nameField = new TextField("png261", skin);
        serverUrlLabel = new Label("Server:", skin);
        serverUrlField = new TextField("http://localhost:3000", skin);
        submitButton = new TextButton("Submit", skin);
        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String serverUrl = serverUrlField.getText();
                String name = nameField.getText();
                connectServer(serverUrl, name);
                if (socket != null) {
                    Game.getInstance().setScreen(new GameLobbyState(socket, clientId));
                }
            }
        });

        rootTable = new Table();
        rootTable.add(nameLabel);
        rootTable.add(nameField).width(100);
        rootTable.row();
        rootTable.add(serverUrlLabel);
        rootTable.add(serverUrlField).width(100);
        rootTable.row();
        rootTable.add(submitButton).width(100);
        rootTable.setFillParent(true);
        stage.addActor(rootTable);
    }

    public void connectServer(String serverUrl, final String name) {
        try {
            socket = IO.socket(serverUrl);
            socket.connect();
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    socket.emit("join", name);
                }
            });
            socket.on("clientId", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    clientId = (String) args[0];
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
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
