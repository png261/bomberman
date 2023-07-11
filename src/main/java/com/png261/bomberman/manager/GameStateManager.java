package com.png261.bomberman.manager;

import com.png261.bomberman.*;
import com.png261.bomberman.state.*;
import java.util.Stack;

public class GameStateManager
{
    private static volatile GameStateManager instance;
    private Stack<GameState> stateStack;
    private GameStateManager() { stateStack = new Stack<>(); }
    public static GameStateManager getInstance()
    {
        if (instance == null) {
            instance = new GameStateManager();
        }
        return instance;
    }
    public void pushState(GameState state)
    {
        state.enter();
        stateStack.push(state);
    }
    public void popState() { stateStack.pop(); }
    public void changeState(GameState state)
    {
        state.enter();
        getCurrentState().exit();
        popState();
        pushState(state);
    }

    public void update()
    {
        if (stateStack.empty()) {
            return;
        }
        getCurrentState().update();
    }
    public void render()
    {
        if (stateStack.empty()) {
            return;
        }
        getCurrentState().render();
    }
    public GameState getCurrentState() { return stateStack.lastElement(); }
}
