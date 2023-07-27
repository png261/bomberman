package com.png261.bomberman.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.png261.bomberman.Game;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class GameLobby implements Screen {
    private class Client {

        public String id;
        public String name;
        public boolean ready;

        Client(String id, String name, boolean ready) {
            this.id = id;
            this.name = name;
            this.ready = ready;
        }
    }

    private Socket socket;

    private String clientId;
    private Stage stage;

    private Skin skin;
    private Array<Client> clients;

    private List<String> clientsList;

    private Table rootTable;

    private Table mapTable;
    private Table gameInfoTable;

    private Table chatTable;
    private Label chatLabel;
    private ScrollPane chatScroll;
    private TextField messageField;

    private Button readyButton;

    private float waitTime = 5;
    private float time;
    private boolean waiting;

    private Window startingWindow;
    private Label timeIndicator;
    private TextButton cancelButton;

    public GameLobby(Socket socket, String clientId) {
        this.socket = socket;
        this.clientId = clientId;
        clients = new Array<>();
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("uiskin/uiskin.json"));
        rootTable = new Table();

        gameInfoTable = buildGameInfoTable();
        chatTable = buildChatTable();

        handleSocketEvents();

        rootTable.setFillParent(true);

        rootTable.add(new Label("GAME LOBBY", skin)).colspan(5);
        rootTable.add(new Label("CHAT ROOM", skin)).colspan(5);
        rootTable.row();

        rootTable.add(gameInfoTable).colspan(5);
        rootTable.add(chatTable).colspan(5);
        rootTable.row();
        rootTable.add(readyButton);

        stage.addActor(rootTable);
    }

    public void handleSocketEvents() {
        socket.on("updateClients", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {
                    clients = new Array<>();
                    JSONArray updatedList = (JSONArray) args[0];
                    for (int i = 0; i < updatedList.length(); ++i) {
                        JSONObject jsonClient = updatedList.getJSONObject(i);
                        String id = jsonClient.getString("id");
                        String name = jsonClient.getString("name");
                        Boolean ready = jsonClient.getBoolean("ready");
                        clients.add(new Client(id, name, ready));
                    }
                    updateClientList();
                } catch (Exception e) {
                }

            }
        });

        socket.on("newMessage", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                String name = (String) args[0];
                String message = (String) args[1];

                chatLabel.setText(chatLabel.getText() + "<" + name + "> : " + message);
            }
        });

        socket.on("gameReady", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                waiting = true;
                startingWindow = new Window("Starting Game", skin);
                timeIndicator = new Label("Starting Game In: " + (5 - (int) time), skin);
                cancelButton = new TextButton("Cancel", skin);

                startingWindow.getTitleLabel().setAlignment(Align.center);
                startingWindow.add(timeIndicator).width(250f).height(50f).center().padLeft(10f);
                startingWindow.row();
                startingWindow.add(cancelButton);
                startingWindow.pack();
                startingWindow.setPosition(Game.getInstance().width() / 2 - startingWindow.getWidth() / 2,
                        Game.getInstance().height() / 2 - startingWindow.getHeight() / 2);

                stage.addActor(startingWindow);

                cancelButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        socket.emit("toggleReady");
                    }
                });

            }
        });

        socket.on("gameCanceled", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                if (waiting) {
                    waiting = false;
                    time = 0;
                    startingWindow.remove();
                }
            }
        });
    }

    public Table buildGameInfoTable() {
        Table table = new Table();

        clientsList = new List<String>(skin);

        readyButton = new TextButton("READY", skin);
        readyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                socket.emit("toggleReady");
            }
        });

        updateClientList();

        Label titles = new Label(" Player                      State", skin);
        titles.setColor(Color.RED);

        table.add(new ScrollPane(titles, skin)).align(Align.left).width(220);
        table.row();
        table.add(new ScrollPane(clientsList, skin)).width(220).height(210);

        return table;
    }

    public void updateClientList() {
        Array<String> list = new Array<String>();

        for (int i = 0; i < clients.size; ++i) {
            Client client = clients.get(i);

            int length = 24 - client.name.length();
            String name = client.name;
            String space = " ".repeat(length);
            String ready = (client.ready ? "Ready" : "Not Ready");
            list.add(name + space + ready);

            if (client.id.equals(clientId)) {
                clientsList.setSelectedIndex(i);
            }
        }

        clientsList.setItems(list);
    }

    public Table buildChatTable() {
        Table table = new Table();

        chatLabel = new Label("", skin);
        chatLabel.setWrap(true);
        chatLabel.setAlignment(Align.topLeft);

        chatScroll = new ScrollPane(chatLabel, skin);
        chatScroll.setFadeScrollBars(false);
        chatScroll.pack();

        messageField = new TextField("", skin);
        messageField.setMessageText("Message");
        messageField.setColor(0.2f, 0.4f, 0.3f, 1f);
        messageField.getStyle().fontColor = Color.WHITE;

        messageField.addListener(new InputListener() {
            @Override
            public boolean keyUp(InputEvent event, int keycode) {

                if (keycode == Input.Keys.ENTER && !messageField.getText().isEmpty()) {
                    socket.emit("message", messageField.getText() + "\n");
                    messageField.setText("");
                }

                return true;
            }
        });

        table.add(chatScroll).width(320).height(220);
        table.row();
        table.add(messageField).width(320).height(20);

        return table;
    }

    @Override
    public void render(float delta) {
        if (waiting) {
            time += delta;
            if (time > 1) {
                timeIndicator.setText("Starting Game In: " + (5 - (int) time));
            }

            if (time > waitTime) {

                // for (Client p : clients)
                // p.ready = false;

                // socket.off();
                // // playScreen.start_multiplayer_game(socket, clients, mapIndex);

                // waitTime = 0;
                // waiting = false;

            }
        }

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
