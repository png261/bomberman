package com.png261.bomberman.manager;

import java.util.Stack;

import com.png261.bomberman.Game;
import com.png261.bomberman.states.GameState;

public class GameStateManager {
    private Game game;
    private static volatile GameStateManager INSTANCE;

    private final Stack<GameState> states;

    private GameStateManager() {
        states = new Stack<>();
    }

    public static GameStateManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GameStateManager();
        }

        return INSTANCE;
    }

    public void init(final Game game) {
        this.game = game;
    }

    public void pushState(final GameState state) {
        states.push(state);
        game.setScreen(state);
    }

    public void popState() {
        if (!states.empty()) {
            states.pop();
        }
    }

    public void changeState(final GameState state) {
        popState();
        pushState(state);
    }

    public GameState currentState() {
        return states.peek();
    }
}
