package com.png261.bomberman.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
import com.png261.bomberman.networking.Client;

import org.json.JSONArray;
import org.json.JSONObject;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class GameLobbyState extends GameState {
    private final Socket socket;

    private final String clientId;
    private Stage stage;

    private Skin skin;
    private Array<Client> clients;

    private List<String> clientsList;

    private Table rootTable;

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

    public GameLobbyState(final Socket socket, final String clientId) {
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
            public void call(final Object... args) {
                try {
                    clients = new Array<>();
                    final JSONArray updatedList = (JSONArray) args[0];
                    for (int i = 0; i < updatedList.length(); ++i) {
                        final JSONObject jsonClient = updatedList.getJSONObject(i);
                        final String id = jsonClient.getString("id");
                        final String name = jsonClient.getString("name");
                        final Boolean ready = jsonClient.getBoolean("ready");
                        clients.add(new Client(id, name, ready));
                    }
                    updateClientList();
                } catch (final Exception e) {
                }

            }
        });

        socket.on("newMessage", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                final String name = (String) args[0];
                final String message = (String) args[1];

                chatLabel.setText(chatLabel.getText() + "<" + name + "> : " + message);
            }
        });

        socket.on("gameReady", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
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
                    public void clicked(final InputEvent event, final float x, final float y) {
                        socket.emit("toggleReady");
                    }
                });

            }
        });

        socket.on("gameCanceled", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                if (waiting) {
                    waiting = false;
                    time = 0;
                    startingWindow.remove();
                }
            }
        });
    }

    public Table buildGameInfoTable() {
        final Table table = new Table();

        clientsList = new List<String>(skin);

        readyButton = new TextButton("READY", skin);
        readyButton.addListener(new ClickListener() {
            @Override
            public void clicked(final InputEvent event, final float x, final float y) {
                socket.emit("toggleReady");
            }
        });

        updateClientList();

        final Label titles = new Label(" Player                      State", skin);
        titles.setColor(Color.RED);

        table.add(new ScrollPane(titles, skin)).align(Align.left).width(220);
        table.row();
        table.add(new ScrollPane(clientsList, skin)).width(220).height(210);

        return table;
    }

    public void updateClientList() {
        final Array<String> list = new Array<String>();

        for (int i = 0; i < clients.size; ++i) {
            final Client client = clients.get(i);

            final int length = 24 - client.name.length();
            final String name = client.name;
            final String space = " ".repeat(length);
            final String ready = (client.ready ? "Ready" : "Not Ready");
            list.add(name + space + ready);

            if (client.id.equals(clientId)) {
                clientsList.setSelectedIndex(i);
            }
        }

        clientsList.setItems(list);
    }

    public Table buildChatTable() {
        final Table table = new Table();

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
            public boolean keyUp(final InputEvent event, final int keycode) {

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
    public void render(final float delta) {
        if (waiting) {
            time += delta;
            if (time > 1) {
                timeIndicator.setText("Starting Game In: " + (5 - (int) time));
            }

            if (time > waitTime) {

                for (final Client p : clients)
                    p.ready = false;

                socket.off();
                Game.getInstance().setScreen(new MultiPlayerState(socket, clients));

                waitTime = 0;
                waiting = false;

            }
        }

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
