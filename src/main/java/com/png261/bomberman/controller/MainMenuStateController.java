package com.png261.bomberman.controller;

import com.png261.bomberman.*;
import javafx.fxml.FXML;

public final class MainMenuStateController
{
    @FXML public void enterPlayState()
    {
        GameStateManager.getInstance().changeState(new PlayState());
    }
    @FXML public void enterHelpState()
    {
        GameStateManager.getInstance().changeState(new HelpState());
    }
}
