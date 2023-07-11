package com.png261.bomberman.controller;

import com.png261.bomberman.*;
import javafx.fxml.FXML;

public final class HelpStateController
{
    @FXML public void backToMainMenuState()
    {
        GameStateManager.getInstance().changeState(new MainMenuState());
    }
}
